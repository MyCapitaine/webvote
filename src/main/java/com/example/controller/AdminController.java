package com.example.controller;

import com.example.entity.JsonResult;
import com.example.entity.ServiceResult;
import com.example.serviceInterface.IpService;
import com.example.serviceInterface.UserInformationService;
import com.example.serviceInterface.UserRegisterService;
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
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    IpService ipService;

    @RequestMapping("/admin")
    public String adminIndex(ModelMap model){
        model.addAttribute("pageIndex",1);
        return "/admin_ban_user";
    }
    /**********封禁用户或IP**********/
    @RequestMapping("/admin/banUser")
    public String ban(ModelMap model){
        model.addAttribute("pageIndex",1);
        return "/admin_ban_user";
    }
    /*地址栏*/
    @RequestMapping("/admin/banUser/allUser")
    public String getNormalUser(ModelMap model,
                            @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
        model.addAttribute("pageIndex",pageIndex);
        return "/admin_ban_user";
    }
    /*ajax*/
    @RequestMapping("/admin/banUser/getAllUser")
    @ResponseBody
    public JsonResult getAllUserToBan(int pageIndex){
        int pageSize=2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllNormal(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
    /*封禁用户*/
    @RequestMapping("/admin/banUser/ban")
    @ResponseBody
    public JsonResult banUser(@RequestParam(value = "idArray") List<Integer> users,int pageIndex){
        for(int id :users){
            userInformationService.ban(id);
            userRegisterService.ban(id);
        }
        int pageSize=2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllNormal(page);
        return new JsonResult(sr.getData());
    }

    /*封禁IP*/
    @RequestMapping("/admin/banIp")
    @ResponseBody
    public JsonResult banIp(@RequestParam(value = "idArray") List<String> ips,int pageIndex){
        for(String ip :ips){
            if(!ipService.isBanned(ip)){
                ipService.ban(ip);
            }
        }
        int pageSize=2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllNormal(page);
        return new JsonResult(sr.getData());
    }

    /**********解封用户**********/
    @RequestMapping("/admin/releaseUser")
    public String release(ModelMap model){
        model.addAttribute("pageIndex",1);
        return "/admin_release_user";
    }

    /*地址栏*/
    @RequestMapping("/admin/releaseUser/allUser")
    public String getBanningUser(ModelMap model,
                            @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
        model.addAttribute("pageIndex",pageIndex);
        return "/admin_release_user";
    }
    /*ajax*/
    @RequestMapping("/admin/releaseUser/getAllUser")
    @ResponseBody
    public JsonResult getAllUserToRelease(int pageIndex){
        int pageSize=2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllBanning(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
    /*封禁*/
    @RequestMapping("/admin/releaseUser/release")
    @ResponseBody
    public JsonResult releaseUser(@RequestParam(value = "idArray") List<Integer> users,int pageIndex){
        for(int id :users){
            userInformationService.release(id);
            userRegisterService.release(id);
        }
        int pageSize=2;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult sr = userInformationService.findAllBanning(page);
        return new JsonResult(sr.getData());
    }



}
