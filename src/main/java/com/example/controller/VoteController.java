package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.serviceInterface.VoteService;

@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage"})
public class VoteController {
	@Autowired
	VoteService voteService;
	
	/**
	 * 投票管理页面
	 */
	public String voteManage() {
		return null;
	}
	/**
	 * 投票发布页面
	 */
	public String votePublish() {
		return null;
	}
	/**
	 * 投票页面
	 */
	public String vote() {
		return null;
	}

	/**
	 * 投票封禁管理页面
	 */
	public String voteBanManage() {
		return null;
	}
	/**
	 * 投票结果页面
	 */
	public String voteResult() {
		return null;
	}
	
}
