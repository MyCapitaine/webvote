package com.example.controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by hasee on 2017/4/10.
 */
public class TestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("----------preHandle----------");
        Cookie[] cookies=httpServletRequest.getCookies();
        if(cookies!=null){
            System.out.println("******cookie exists******");
            for(Cookie cookie:cookies){
                System.out.println("cookiename:"+cookie.getName());
                if(cookie.getName().equals("currentUser")){
                    System.out.println("currentUser:"+cookie.getValue());
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        String[] login_name=httpServletRequest.getParameterValues("login_name");
//        String[] login_pwd=httpServletRequest.getParameterValues("login_pwd");
//        String[] remember=httpServletRequest.getParameterValues("remember");
//        HttpSession session=httpServletRequest.getSession();
//        if(login_name!=null
//                &&login_pwd!=null
//                &&remember!=null
//                &&session.getAttribute("currentUser")!=null){
//
//            if(remember[0].equals("true")){
//                System.out.println("remember:"+remember[0]);
//                String cv=login_name[0]+":"+login_pwd[0];
//                Cookie cookie=new Cookie("currentUser",cv);
//                cookie.setMaxAge(1000*60*60*24*30);
//                cookie.setPath("/");
//                httpServletResponse.addCookie(cookie);
//
//            }
//        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
