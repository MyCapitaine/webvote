package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.serviceInterface.VoteService;
/**
 * 
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
	@RequestMapping(value = "/votepublish", method = RequestMethod.GET)
	public String votePublishPage(ModelMap modelMap) {
		return null;
	}
	/**
	 * 投票发布处理
	 */
	@RequestMapping(value = "/votepublish", method = RequestMethod.POST)
	public String votePublish() {
		return null;
	}
	/**
	 * 投票管理页面
	 */
	@RequestMapping(value = "/votemanage/*", method = RequestMethod.GET)
	public String voteManagePage() {
		return null;
	}
	/**
	 * 投票管理处理
	 */
	@RequestMapping(value = "/votemanage/*", method = RequestMethod.POST)
	public String voteManage() {
		return null;
	}
	/**
	 * 投票页面
	 */
	@RequestMapping(value = "/voteactivity/*", method = RequestMethod.GET)
	public String votePage() {
		return null;
	}
	/**
	 * 投票处理
	 */
	@RequestMapping(value = "/voteactivity/*", method = RequestMethod.POST)
	public String vote() {
		return null;
	}
	/**
	 * 投票更新处理
	 */
	@RequestMapping(value = "/voteactivity/*", method = RequestMethod.PUT)
	public String voteUpd() {
		return null;
	}
	/**
	 * 投票封禁管理页面
	 */
	@RequestMapping("/banmanage")
	public String voteBanManagePage() {
		return null;
	}
	/**
	 * 投票封禁处理
	 */
	@RequestMapping("/banaction/*")
	public String voteBanManage() {
		return null;
	}
	/**
	 * 投票解封处理
	 */
	@RequestMapping("/unbanaction/*")
	public String voteUnbanManage() {
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
