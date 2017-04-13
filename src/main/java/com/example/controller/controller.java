package com.example.controller;

import com.example.entity.ServiceResult;
import com.example.serviceInterface.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hasee on 2017/3/4.
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage"})//
public class controller {
    @RequestMapping("/")
    public String index(ModelMap model){
        return "index";
    }

    @RequestMapping("/index")
    public String index2(ModelMap model){
        return "index";
    }

    @RequestMapping("/logged")
    public String logged(){
        return "logged";
    }

    @RequestMapping("/page")
    public String page(ModelMap model, @RequestParam(value = "page_index",defaultValue = "1")int index){
        model.addAttribute("page_index",index);
        return "page";
    }

    @RequestMapping("/find")
    public String find(){
        return "find";
    }

    //返回登录界面
    @RequestMapping("/signin")
    public String signin(){
        return "signin";
    }

    //退出登录，返回message界面
    @RequestMapping("/signout")
    public String signout(ModelMap model){//SessionStatus sessionStatus,,@ModelAttribute(value = "previousPage")String previous
        //model.addAttribute("redirectTo",previous);
        model.addAttribute("message","退出登录");
        //sessionStatus.setComplete();
        return "message";
    }

    //返回注册界面
    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }

    @RequestMapping("/random")
    public String random( ModelMap model){
        return "random";
    }

    //验证
    @Autowired
    ValidateService validator;
    @RequestMapping(value="/validate")
    public String validate(String token,ModelMap model){
        model.addAttribute("validate",token);
        ServiceResult sr=validator.validate(token);
        if(sr.isSuccess()){
            model.addAttribute("currentUser",sr.getData());
            model.addAttribute("message","成功激活，将跳转到首页");
            model.addAttribute("redirectTo","/index");
            return "/message";
        }
        else if(sr.getMessage().equals("已经激活")){
            model.addAttribute("message","已经激活，请登录");
            model.addAttribute("previousPage","index");
            model.addAttribute("redirectTo","/signin");
            return "/message";
        }
        else{
            model.addAttribute("message","验证码已过期！请查看新邮件激活");
            model.addAttribute("redirectTo","/index");
            return "/message";
        }
    }

    //message界面
    @RequestMapping("/message")
    public String message(ModelMap model,HttpServletRequest httpServletRequest){//, @ModelAttribute("message") String message,@ModelAttribute("redirectTo") String redirectTo
//        model.addAttribute("redirectTo",redirectTo);
//        model.addAttribute("message",message);
        System.out.println("function message out:"+httpServletRequest.getHeader("Referer"));
        return "message";
    }
}
