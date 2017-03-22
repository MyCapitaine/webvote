package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by hasee on 2017/3/4.
 */
@Controller
public class controller {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/page")
    public String page(){
        return "page";
    }
    @RequestMapping("/find")
    public String find(){
        return "find";
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping("/random")
    public String random(Model model){
        int random=(int)(Math.random()*10);
        model.addAttribute("number",random);
        return "random";
    }
    @RequestMapping(value="/random", method = RequestMethod.POST)
    public String postRandom(Model model){

        System.out.print("post random ");
        int random=(int)(Math.random()*10);
        model.addAttribute("number",random);
        return "random";
    }
    @RequestMapping(value="/validate")
    public String validate(String token){
        if(token.equals("pass"))
            return "redirect:/random";
        return "redirect:/register";
    }
}
