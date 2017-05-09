package com.example.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.entity.MsgsEntity;
import com.example.entity.UserRegister;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VotesEntity;
import com.example.serviceInterface.GuestService;
import com.example.serviceInterface.VoteService;
import com.example.util.IpAddress;

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
	@ResponseBody
	public String doVote(HttpServletRequest request, String vidstr, String[] optionIds) {
		String ip = IpAddress.getIpAddr(request);
		int vid;
		try {
			vid = Integer.parseInt(vidstr);
		}catch(Exception e) {
			return "fail, vid should be integer";
		}
		
		if(optionIds == null || optionIds.length == 0) return "fail, should choose option";
		
		for(String optionId : optionIds) {
			VoteActivitiesEntity vae = new VoteActivitiesEntity();
			vae.setIp(ip);
			vae.setVid(vid);
			try {
				vae.setOptionId(Integer.parseInt(optionId));
			}catch(Exception e) {
				return "fail, option id should be integer";
			}
			vae.setVoteTime(new Date());
			if(!guestService.doVote(vae))
				return "fail, do vote fail";
		}
		return "success";
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
		
		VotesEntity voteEntity = voteService.findVoteById(voteId).getData();
		List<MsgsEntity> msgs = guestService.getMsgsByVid(voteEntity.getId()).getData();
		isMsged = guestService.isIpMsg(ip, voteId).isSuccess();
		boolean canDelMsg = ur != null && (ur.getAuthority() == 0) || (voteEntity.getUid() == ur.getId());
		modelMap.addAttribute("msgList", msgs);
		modelMap.addAttribute("isMsged", isMsged);
		modelMap.addAttribute("canDelMsg", canDelMsg);
		
		return "refresh_msg";
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
		
		VotesEntity voteEntity = voteService.findVoteById(voteId).getData();
		List<MsgsEntity> msgs = guestService.getMsgsByVid(voteEntity.getId()).getData();
		boolean isMsged = guestService.isIpMsg(IpAddress.getIpAddr(request), voteId).isSuccess();
		boolean canDelMsg = ur != null && (ur.getAuthority() == 0) || (voteEntity.getUid() == ur.getId());
		modelMap.addAttribute("msgList", msgs);
		modelMap.addAttribute("isMsged", isMsged);
		modelMap.addAttribute("canDelMsg", canDelMsg);
		
		return "refresh_msg";
	}
}
