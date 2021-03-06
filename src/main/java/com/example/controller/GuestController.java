package com.example.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.entity.MsgsEntity;
import com.example.entity.UserRegister;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VotesEntity;
import com.example.serviceInterface.GuestService;
import com.example.serviceInterface.VoteService;
import com.example.util.IpAddress;
import com.example.vo.VoteResultVO;

/**
 * 游客控制器
 * @author MyCapitaine
 *
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage"})
public class GuestController {
	@Autowired
	GuestService guestService;
	@Autowired
	VoteService voteService;
	
	/**
	 * 进行投票
	 */
	@RequestMapping(value = "/dovote", method = RequestMethod.POST)
	public String doVote(ModelMap modelMap, 
			HttpServletRequest request, String vidstr, String[] optionIds) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		String ip = IpAddress.getIpAddr(request);
		int vid;
		try {
			vid = Integer.parseInt(vidstr);
		}catch(Exception e) {
			return "error";
		}
		
		if(optionIds == null || optionIds.length == 0) return "error";
		
		for(String optionId : optionIds) {
			VoteActivitiesEntity vae = new VoteActivitiesEntity();
			vae.setIp(ip);
			vae.setVid(vid);
			try {
				vae.setOptionId(Integer.parseInt(optionId));
			}catch(Exception e) {
				return "error";
			}
			vae.setVoteTime(new Date());
			if(!guestService.doVote(vae))
				return "error";
		}
		boolean isVoted = guestService.isIpVoted(ip, vid).isSuccess();
		VotesEntity voteEntity = voteService.findVoteById(vid).getData();
		boolean isVoteOwner = ur != null && voteEntity.getUid() == ur.getId();
		boolean isManager = ur != null && ur.getAuthority() == 0;
		boolean canCheckResult = voteEntity.getResultAuthority() == 1 || isVoteOwner || isManager;

		modelMap.addAttribute("isVoted", isVoted);
		modelMap.addAttribute("canCheckResult", canCheckResult);
		return "vote_success";
	}
	
	/**
	 * 进行留言
	 */
	@RequestMapping(value = "/domsg", method = RequestMethod.POST)
	public String doMsg(ModelMap modelMap, 
			HttpServletRequest request, String vidstr, String msg) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		String ip = IpAddress.getIpAddr(request);
		int voteId;
		try {
			voteId = Integer.parseInt(vidstr);
		}catch(Exception e) {
			return "refresh_msg";
		}
		boolean isMsged = guestService.isIpMsg(ip, voteId).isSuccess();
		if(isMsged) return "refresh_msg";
		
		MsgsEntity msgEntity = new MsgsEntity();
		msgEntity.setIp(ip);
		msgEntity.setMsgText(msg);
		msgEntity.setVid(voteId);
		msgEntity.setMsgTime(new Date());
		guestService.doMsg(msgEntity);
		
		fillInRefreshMsg(modelMap, ip, voteId, ur);
		
		return "refresh_msg";
	}
	
	private void fillInRefreshMsg(ModelMap modelMap, String ip, int voteId, UserRegister ur) {
		VotesEntity voteEntity = voteService.findVoteById(voteId).getData();
		List<MsgsEntity> msgs = guestService.getMsgsByVid(voteEntity.getId()).getData();
		boolean isMsged = guestService.isIpMsg(ip, voteId).isSuccess();
		boolean canDelMsg = ur != null && ((ur.getAuthority() == 0) || (voteEntity.getUid() == ur.getId()));
		modelMap.addAttribute("msgList", msgs);
		modelMap.addAttribute("isMsged", isMsged);
		modelMap.addAttribute("canDelMsg", canDelMsg);
		
		//热评
		boolean hasHotMsg = false;
		MsgsEntity hotMsg = new MsgsEntity();
		hotMsg.setBumpNum(0);
		for(MsgsEntity msg : msgs) {
			if(msg.getBumpNum() > hotMsg.getBumpNum()) 
				hotMsg = msg;
		}
		if(hotMsg.getBumpNum() >= 3) hasHotMsg = true;
		modelMap.addAttribute("hasHotMsg", hasHotMsg);
		modelMap.addAttribute("hotMsg", hotMsg);
	}
	
	/**
	 * 删除留言
	 */
	@RequestMapping(value = "/delmsg", method = RequestMethod.POST)
	public String delMsg(ModelMap modelMap,
			HttpServletRequest request, String mid, String vid) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		int msgId, voteId;
		try {
			msgId = Integer.parseInt(mid);
			voteId = Integer.parseInt(vid);
		}catch(Exception e) {
			return "refresh_msg";
		}
		MsgsEntity msgEntity = new MsgsEntity();
		msgEntity.setId(msgId);
		guestService.delMsg(msgEntity);
		
		fillInRefreshMsg(modelMap, IpAddress.getIpAddr(request), voteId, ur);
		
		return "refresh_msg";
	}
	/**
	 * 顶留言
	 */
	@RequestMapping(value = "/bumpMsg", method = RequestMethod.POST)
	public String bumpMsg(ModelMap modelMap,
			HttpServletRequest request, String mid, String vid) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		String ip = IpAddress.getIpAddr(request);
		
		int msgId, voteId;
		try {
			msgId = Integer.parseInt(mid);
			voteId = Integer.parseInt(vid);
		}catch(Exception e) {
			return "refresh_msg";
		}
		
		guestService.bumpMsg(msgId, ip, voteId);
		
		fillInRefreshMsg(modelMap, IpAddress.getIpAddr(request), voteId, ur);
		
		return "refresh_msg";
	}
	/**
	 * 踩留言
	 */
	@RequestMapping(value = "/treadMsg", method = RequestMethod.POST)
	public String treadMsg(ModelMap modelMap,
			HttpServletRequest request, String mid, String vid) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		String ip = IpAddress.getIpAddr(request);
		
		int msgId, voteId;
		try {
			msgId = Integer.parseInt(mid);
			voteId = Integer.parseInt(vid);
		}catch(Exception e) {
			return "refresh_msg";
		}

		guestService.treadMsg(msgId, ip, voteId);
		
		fillInRefreshMsg(modelMap, ip, voteId, ur);
		
		return "refresh_msg";
	}
	
	
	/**
	 * 投票结果页面
	 */
	@RequestMapping(value = "/voteresult/{voteId}", method = RequestMethod.GET)
	public String voteResult(ModelMap modelMap, @PathVariable int voteId,
			HttpServletRequest request) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		VotesEntity voteEntity = voteService.findVoteById(voteId).getData();
		if(voteEntity == null) return "no_vote";
		
		boolean hasAuthority = voteEntity.getResultAuthority() == 1 //开放结果
				|| (ur != null && ur.getAuthority() == 0) //管理员
				|| (ur != null && ur.getId() == voteEntity.getUid()); //投票发布者
		if(!hasAuthority) return "no_result_authority";
		List<VoteResultVO> results = guestService.voteResult(voteId).getData();
		
		modelMap.addAttribute("voteEntity", voteEntity);
		modelMap.addAttribute("results", results);
		
		return "vote_result";
	}
	
	/**
	 * 推送投票部分
	 */
	@RequestMapping(value = "/recommandvote/{voteId}", method = RequestMethod.GET)
	public String recommandVotes(ModelMap modelMap, @PathVariable int voteId) {
		List<VotesEntity> recommandVotes = guestService.getRecommandVotes(voteId).getData();
		modelMap.addAttribute("recommandVotes", recommandVotes);
		return "recommand_vote";
	}
	
	
	
	
}
