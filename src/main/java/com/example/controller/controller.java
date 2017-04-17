package com.example.controller;

import com.example.serviceInterface.ActiveValidateService;
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

    //message界面
    @RequestMapping("/message")
    public String message(ModelMap model,HttpServletRequest httpServletRequest){//, @ModelAttribute("message") String message,@ModelAttribute("redirectTo") String redirectTo
//        model.addAttribute("redirectTo",redirectTo);
//        model.addAttribute("message",message);
        System.out.println("function message out:"+httpServletRequest.getHeader("Referer"));
        return "message";
    }
}
