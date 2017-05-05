package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.serviceInterface.GuestService;

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
	
	/**
	 * 进行投票
	 */
	@RequestMapping(value = "/dovote/*", method = RequestMethod.POST)
	public String doVote() {
		return null;
	}
	
	/**
	 * 获取留言
	 */
	
	
	/**
	 * 进行留言
	 */
	
	/**
	 * 删除留言
	 */
	
}
