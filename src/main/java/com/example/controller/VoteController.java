package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
			@ModelAttribute(name = "currentUser")UserRegister ur) {
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
	public String voteOverview() {
		
		
		return "";
	}
	/**
	 * 投票页面
	 */
	@RequestMapping(value = "/votepage/{voteId}", method = RequestMethod.GET)
	public String votePage(ModelMap modelMap, 
			HttpServletRequest request,
			@ModelAttribute(name = "currentUser")UserRegister ur,
			@PathVariable int voteId) {
		String ip = IpAddress.getIpAddr(request);
		
		
		boolean isManager = ur.getAuthority() == 0;
		
		VotesEntity voteEntity = voteService.findVoteById(voteId).getData();
		boolean isVoteOwner = voteEntity.getUid() == ur.getId();
		
		List<VoteOptionsEntity> optionList = voteService.findVoteOptionsByVid(voteEntity.getId()).getData();
		List<MsgsEntity> msgs = guestService.getMsgsByVid(voteEntity.getId()).getData();
		boolean isVoted = guestService.isIpVoted(ip, voteId).isSuccess();
		boolean isMsged = guestService.isIpMsg(ip, voteId).isSuccess();
		

		modelMap.addAttribute("voteEntity", voteEntity);
		modelMap.addAttribute("optionList", optionList);
		modelMap.addAttribute("msgList", msgs);
		modelMap.addAttribute("isMsged", isMsged);
		modelMap.addAttribute("isVoted", isVoted);
		modelMap.addAttribute("isManager", isManager);
		modelMap.addAttribute("isVoteOwner", isVoteOwner);
		
		
		
		return "vote";
	}

	/**
	 * 投票更新页面
	 */
	@RequestMapping(value = "/updatevote/{voteId}", method = RequestMethod.GET)
	public String updVotePage(ModelMap modelMap,
			@ModelAttribute(name = "currentUser")UserRegister ur,
			@PathVariable int voteId) {

		VotesEntity voteEntity = (VotesEntity)voteService.findVoteById(voteId).getData();
		List<VoteOptionsEntity> optionList = (List<VoteOptionsEntity>)voteService.findVoteOptionsByVid(voteEntity.getId()).getData();
		
		modelMap.addAttribute("voteEntity", voteEntity);
		modelMap.addAttribute("optionList", optionList);
		return "upd_vote";
	}
	/**
	 * 投票更新处理
	 */
	@RequestMapping(value = "/updatevote/{voteId}", method = RequestMethod.POST)
	public String updVote(@PathVariable int voteId) {
		return null;
	}
	/**
	 * 删除投票
	 */
	@RequestMapping(value = "/deletevote/{voteId}", method = RequestMethod.GET)
	public String delVote(@PathVariable int voteId) {
		return null;
	}
	/**
	 * 投票封禁处理
	 */
	@RequestMapping("/banvote/{voteId}")
	public String banVote(@PathVariable int voteId) {
		return null;
	}
	/**
	 * 投票解封处理
	 */
	@RequestMapping("/unbanvote/{voteId}")
	public String unbanvote(@PathVariable int voteId) {
		return null;
	}
	
	/**
	 * 投票结果页面
	 */
	@RequestMapping("/voteresult/{voteId}")
	public String voteResult(@PathVariable int voteId) {
		return null;
	}
	
}
