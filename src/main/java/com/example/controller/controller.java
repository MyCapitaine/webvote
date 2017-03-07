package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hasee on 2017/3/4.
 */
@Controller
public class controller {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
