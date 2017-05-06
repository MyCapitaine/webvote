package com.example.controller;

import com.example.entity.JsonResult;
import com.example.entity.ServiceResult;
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

    @RequestMapping("/admin")
    public String admin(ModelMap model){
        model.addAttribute("pageIndex",1);
        return "/admin";
    }
    @RequestMapping("/admin/allUser")
    public String adminUser(ModelMap model,
                            @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
        model.addAttribute("pageIndex",pageIndex);
        return "/admin";
    }
    @RequestMapping("/admin/getAllUser")
    @ResponseBody
    public JsonResult getAllUser(int pageIndex){
        int page_size=5;
        //JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, page_size);
        ServiceResult sr = userInformationService.findAll(page);
        //jr.setData(sr.getData());
        return new JsonResult(sr.getData());
    }
}
