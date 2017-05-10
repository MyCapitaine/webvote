package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.entity.MsgsEntity;
import com.example.entity.UserRegister;
import com.example.entity.VoteOptionsEntity;
import com.example.entity.VotesEntity;
import com.example.serviceInterface.GuestService;
import com.example.serviceInterface.VoteService;
import com.example.util.IpAddress;
/**
 * 投票控制器
 * @author MyCapitaine
 *
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage","UserInformation"})
public class VoteController {
	@Autowired
	VoteService voteService;
	@Autowired
	GuestService guestService;
	
	/**
	 * 投票发布页面
	 */
	@RequestMapping(value = "/addvote", method = RequestMethod.GET)
	public String addVotePage(ModelMap modelMap) {
		return "add_vote";
	}
	/**
	 * 投票发布处理
	 */
	@RequestMapping(value = "/addvote", method = RequestMethod.POST)
	@ResponseBody
	public String addVote(VotesEntity votesEntity, String[] options,
			HttpServletRequest request) {
		
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		votesEntity.setUid(ur.getId());
		votesEntity.setCreateTime(new Date());
		
		List<VoteOptionsEntity> optionList = new ArrayList<VoteOptionsEntity>();
		for(String option : options) {
			VoteOptionsEntity optionEntity = new VoteOptionsEntity();
			optionEntity.setOptionText(option);
			optionList.add(optionEntity);
		}
		if(voteService.addVote(votesEntity, optionList))
			return "success";
		else
			return "fail";
	}
	/**
	 * 用户(发布的所有)投票列表
	 */
	@RequestMapping("/voteslist")
	@ResponseBody
	@Deprecated
	public String voteOverview() {
		return "error";
	}
	/**
	 * 投票页面
	 */
	@RequestMapping(value = "/votepage/{voteId}", method = RequestMethod.GET)
	public String votePage(ModelMap modelMap, 
			HttpServletRequest request,
			@PathVariable int voteId) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		String ip = IpAddress.getIpAddr(request);
		
		boolean isManager = ur != null && ur.getAuthority() == 0;
		
		VotesEntity voteEntity = voteService.findVoteById(voteId).getData();
		if(voteEntity == null) return "no_vote";
		boolean isVoteOwner = ur != null && voteEntity.getUid() == ur.getId();
		boolean canCheckResult = voteEntity.getResultAuthority() == 1 || isVoteOwner || isManager;
		
		List<VoteOptionsEntity> optionList = voteService.findVoteOptionsByVid(voteEntity.getId()).getData();
		List<MsgsEntity> msgs = guestService.getMsgsByVid(voteEntity.getId()).getData();
		boolean isVoted = guestService.isIpVoted(ip, voteId).isSuccess();
		boolean isMsged = guestService.isIpMsg(ip, voteId).isSuccess();

		boolean canDelVote = isManager || isVoteOwner;
		boolean canDelMsg = isManager || isVoteOwner;
		
		Date currentDate = new Date();
		boolean isVoteBegin = currentDate.after(voteEntity.getBeginTime());
		boolean isVoteOver = currentDate.after(voteEntity.getDeadLine());

		modelMap.addAttribute("voteEntity", voteEntity);
		modelMap.addAttribute("optionList", optionList);
		modelMap.addAttribute("msgList", msgs);
		modelMap.addAttribute("isMsged", isMsged);
		modelMap.addAttribute("isVoted", isVoted);
		modelMap.addAttribute("canDelVote", canDelVote);
		modelMap.addAttribute("canDelMsg", canDelMsg);
		modelMap.addAttribute("isVoteBegin", isVoteBegin);
		modelMap.addAttribute("isVoteOver", isVoteOver);
		modelMap.addAttribute("canCheckResult", canCheckResult);
		
		return "vote";
	}

	/**
	 * 投票更新页面
	 */
	@RequestMapping(value = "/updatevote/{voteId}", method = RequestMethod.GET)
	public String updVotePage(ModelMap modelMap,
			HttpServletRequest request,
			@PathVariable int voteId) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;

		VotesEntity voteEntity = voteService.findVoteById(voteId).getData();
		if(voteEntity == null) return "no_vote";
		List<VoteOptionsEntity> optionList = voteService.findVoteOptionsByVid(voteEntity.getId()).getData();
		boolean isVoteOwner = ur != null && voteEntity.getUid() == ur.getId();
		if(!isVoteOwner) return "error";
		
		modelMap.addAttribute("voteEntity", voteEntity);
		modelMap.addAttribute("optionList", optionList);
		return "upd_vote";
	}
	/**
	 * 投票更新处理
	 */
	@RequestMapping(value = "/updatevote", method = RequestMethod.POST)
	public String updVote(ModelMap modelMap, 
			VotesEntity votesEntity,
			HttpServletRequest request) {
		Object urObj = request.getSession(true).getAttribute("currentUser");
		UserRegister ur = urObj == null ? null : (UserRegister)urObj;
		
		VotesEntity origin = voteService.findVoteById(votesEntity.getId()).getData();
		if(origin == null) return "error";
		boolean isVoteOwner = ur != null && origin.getUid() == ur.getId();
		if(!isVoteOwner) return "error";
		voteService.updateVote(votesEntity);
		
		modelMap.addAttribute("voteEntity", votesEntity);
		return  "updated_vote_data";
	}
	/**
	 * 删除投票
	 */
	@RequestMapping(value = "/deletevote/{voteId}", method = RequestMethod.POST)
	@ResponseBody
	public String delVote(@PathVariable int voteId) {
		voteService.banVote(voteId);
		return "success";
	}

}
