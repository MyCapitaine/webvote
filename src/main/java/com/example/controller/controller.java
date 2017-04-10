package com.example.controller;

import com.example.dao.RegisterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by hasee on 2017/3/4.
 */
@Controller
@SessionAttributes({"currentUser","validate"})
public class controller {
    @RequestMapping("/")
    public String index(ModelMap model){
        return "index";
    }

//    @RequestMapping("/login")
//    public String login(){
//        return "index";
//    }

    @RequestMapping("/page")
    public String page(){
        return "page";
    }

    @RequestMapping("/find")
    public String find(){
        return "find";
    }

    @RequestMapping("/signin")
    public String signin(){
        return "signin";
    }

    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }

    @Autowired
    private RegisterDao registerDao;
    @RequestMapping("/random")
    public String random( ModelMap model){
//        int random=(int)(Math.random()*9);
//        model.addAttribute("number",registerDao.findOne(random));
        return "random";
    }

//    @RequestMapping(value="/random", method = RequestMethod.POST)
//    public String postRandom(Model model){
//
//        System.out.print("post random ");
//        int random=(int)(Math.random()*10);
//        model.addAttribute("number",random);
//        return "random";
//    }

    @RequestMapping(value="/validate")
    public String validate(String token,ModelMap model){
        model.addAttribute("validate","validate");

        if(token.equals("pass"))
            return "redirect:/random";
        return "redirect:/register";
    }
}
