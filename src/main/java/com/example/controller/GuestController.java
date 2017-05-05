package com.example.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.entity.MsgsEntity;
import com.example.entity.VoteActivitiesEntity;
import com.example.serviceInterface.GuestService;
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
	 * 获取留言
	 */
	
	/**
	 * 进行留言
	 */
	@RequestMapping(value = "/domsg", method = RequestMethod.POST)
	@ResponseBody
	public String doMsg(HttpServletRequest request, String vidstr, String msg) {
		String ip = IpAddress.getIpAddr(request);
		int vid;
		try {
			vid = Integer.parseInt(vidstr);
		}catch(Exception e) {
			return "fail, vid should be integer";
		}
		
		MsgsEntity msgEntity = new MsgsEntity();
		msgEntity.setIp(ip);
		msgEntity.setMsgText(msg);
		msgEntity.setVid(vid);
		msgEntity.setMsgTime(new Date());
		if(!guestService.doMsg(msgEntity))
			return "fail, do msg fail";
		return "success";
	}
	/**
	 * 删除留言
	 */
	
}
