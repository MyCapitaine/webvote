package com.example.controller;

import com.example.entity.JsonResult;
import com.example.entity.ServiceResult;
import com.example.serviceInterface.IpService;
import com.example.serviceInterface.UserInformationService;
import com.example.serviceInterface.UserRegisterService;
import com.example.serviceInterface.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * Created by hasee on 2017/5/6.
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage","UserInformation"})
public class AdminController {
    /**自动注入UserRegisterService，管理用户账号.**/
    @Autowired
    private UserRegisterService userRegisterService;
    /**自动注入UserInformationService，管理用户信息.**/
    @Autowired
    private UserInformationService userInformationService;
    /**自动注入IpService，管理ip.**/
    @Autowired
    private IpService ipService;
    /**自动注入VoteService，管理投票.**/
    @Autowired
    private VoteService voteService;

    @RequestMapping("/admin")
    public String adminIndex(ModelMap model) {
        model.addAttribute("pageIndex",1);
        return "admin_ban_user";
    }
    /**封禁用户或IP.
     *
     */

    @RequestMapping("/admin/banUser")
    public String ban(ModelMap model,
                      @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex) {
        model.addAttribute("pageIndex",pageIndex);
        return "/admin_ban_user";
    }
    /*地址栏*/
//    @RequestMapping("/admin/banUser/allUser")
//    public String getNormalUser(ModelMap model,
//                            @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
//        model.addAttribute("pageIndex",pageIndex);
//        return "/admin_ban_user";
//    }
    /*ajax*/

    @RequestMapping("/admin/banUser/getAllUser")
    @ResponseBody
    public JsonResult getAllUserToBan(int pageIndex) {
        int pageSize = 2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllNormal(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
    /*封禁用户*/

    @RequestMapping("/admin/banUser/ban")
    @ResponseBody
    public JsonResult banUser(@RequestParam(value = "idArray") List<Integer> users,int pageIndex) {
        for (int id :users) {
            userInformationService.ban(id);
            userRegisterService.ban(id);
        }
        int pageSize = 2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllNormal(page);
        return new JsonResult(sr.getData());
    }

    /*封禁IP*/
    @RequestMapping("/admin/banIp")
    @ResponseBody
    public JsonResult banIp(@RequestParam(value = "idArray") List<String> ips,int pageIndex) {
        for (String ip :ips) {
            if (!ipService.isBanned(ip)) {
                ipService.ban(ip);
            }
        }
        int pageSize = 2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllNormal(page);
        return new JsonResult(sr.getData());
    }

    /**********解封用户**********/
    @RequestMapping("/admin/releaseUser")
    public String release(ModelMap model,
                          @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex) {
        model.addAttribute("pageIndex",pageIndex);
        return "admin_release_user";
    }

    /*地址栏*/
//    @RequestMapping("/admin/releaseUser/allUser")
//    public String getBanningUser(ModelMap model,
//                            @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
//        model.addAttribute("pageIndex",pageIndex);
//        return "/admin_release_user";
//    }
    /*ajax*/
    @RequestMapping("/admin/releaseUser/getAllUser")
    @ResponseBody
    public JsonResult getAllUserToRelease(int pageIndex) {
        int pageSize = 2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllBanning(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
    /*封禁*/

    @RequestMapping("/admin/releaseUser/release")
    @ResponseBody
    public JsonResult releaseUser(@RequestParam(value = "idArray") List<Integer> users,int pageIndex) {
        for (int id :users) {
            userInformationService.release(id);
            userRegisterService.release(id);
        }
        int pageSize = 2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllBanning(page);
        return new JsonResult(sr.getData());
    }

    /**********解封IP**********/
    @RequestMapping("/admin/ip")
    public String adminIp(ModelMap model,
                            @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex) {
        model.addAttribute("pageIndex",pageIndex);
        return "admin_ip";
    }

    /*地址栏*/
//    @RequestMapping("/admin/ip/allUser")
//    public String getBanningIp(ModelMap model,
//                               @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
//        model.addAttribute("pageIndex",pageIndex);
//        return "/admin_release_user";
//    }
    /*ajax*/
    @RequestMapping("/admin/ip/getAllIp")
    @ResponseBody
    public JsonResult getAllIpToRelease(int pageIndex) {
        int pageSize = 2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = ipService.findAll(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
    /*解封ip*/
    @RequestMapping("/admin/ip/release")
    @ResponseBody
    public JsonResult releaseIp(@RequestParam(value = "idArray") List<Integer> ids,int pageIndex) {
        for (int id :ids) {
            if (ipService.isBanned(id)) {
                ipService.release(id);
            }
        }
        int pageSize = 2;
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = ipService.findAll(page);
        return new JsonResult(sr.getData());
    }

    /**********投票管理**********/
    @RequestMapping("/admin/vote")
    public String vote(ModelMap model,
                       @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex) {
        model.addAttribute("pageIndex",pageIndex);
        return "admin_vote";
    }
    /*ajax*/
    @RequestMapping("/admin/vote/getAllVotes")
    @ResponseBody
    public JsonResult getAllVotes(int pageIndex) {
        int pageSize = 10;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = voteService.findAllVote(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
    /*删除投票*/
    @RequestMapping("/admin/vote/delete")
    @ResponseBody
    public JsonResult deleteVotes(@RequestParam(value = "idArray") List<Integer> ids,int pageIndex) {
        for (int id :ids) {
            voteService.banVote(id);
        }
        //10条一页
        int pageSize = 10;
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = voteService.findAllVote(page);
        return new JsonResult(sr.getData());
    }

    /**********留言管理**********/
    @RequestMapping("/admin/comment")
    public String comment(ModelMap model,
                       @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex) {
        model.addAttribute("pageIndex",pageIndex);
        return "admin_comment";
    }
    /*ajax*/
    @RequestMapping("/admin/comment/getAllComments")
    @ResponseBody
    public JsonResult getAllComments(int pageIndex) {
        int pageSize = 10;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = voteService.findAllVote(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
    /*删除投票*/
    @RequestMapping("/admin/comment/delete")
    @ResponseBody
    public JsonResult deleteComments(@RequestParam(value = "idArray") List<Integer> ids,int pageIndex) {
//        for(int id :ids){
//            voteService.banVote(id);
//        }
//        int pageSize=10;
//        Pageable page = new PageRequest(pageIndex, pageSize);
//        ServiceResult sr = voteService.findAllVote(page);
//        return new JsonResult(sr.getData());
        return new JsonResult();
    }
}
