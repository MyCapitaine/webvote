package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.serviceInterface.VoteService;
/**
 * 投票控制器
 * @author MyCapitaine
 *
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage"})
public class VoteController {
	@Autowired
	VoteService voteService;
	
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
	public String addVote() {
		
		
		return null;
	}
	/**
	 * 用户(发布的所有)投票概览
	 */
	@RequestMapping("/votesoverview/*")
	public String voteOverview() {
		
		
		return "votesoverview";
	}
	/**
	 * 投票页面
	 */
	@RequestMapping(value = "/votepage/*", method = RequestMethod.GET)
	public String votePage() {
		return null;
	}
	/**
	 * 投票处理
	 */
	@RequestMapping(value = "/dovote/*", method = RequestMethod.POST)
	public String doVote() {
		return null;
	}
	/**
	 * 投票更新处理
	 */
	@RequestMapping(value = "/updatevote/*", method = RequestMethod.POST)
	public String updVote() {
		return null;
	}
	/**
	 * 删除投票
	 */
	@RequestMapping(value = "/deletevote/*", method = RequestMethod.GET)
	public String delVote() {
		return null;
	}
	/**
	 * 投票封禁处理
	 */
	@RequestMapping("/banvote/*")
	public String banVote() {
		return null;
	}
	/**
	 * 投票解封处理
	 */
	@RequestMapping("/unbanvote/*")
	public String unbanvote() {
		return null;
	}
	
	/**
	 * 投票结果页面
	 */
	@RequestMapping("/voteresult/*")
	public String voteResult() {
		return null;
	}
	
}
