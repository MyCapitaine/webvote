package com.example.controller;

import com.example.entity.*;
import com.example.serviceInterface.IpService;
import com.example.serviceInterface.LoginRecordService;
import com.example.serviceInterface.UserInformationService;
import com.example.serviceInterface.UserRegisterService;
import com.example.util.IpAddress;
import com.example.vo.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private IpService ipService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("----------preHandle----------");
        String ip = IpAddress.getIpAddr(httpServletRequest);
        //System.out.println("ip address:" + ip);
        String servlet = httpServletRequest.getServletPath();
        System.out.println("request service:" + servlet);
        String referer = httpServletRequest.getHeader("Referer");
        //System.out.println("referer:" + referer);
        Cookie[] cookies = httpServletRequest.getCookies();
        HttpSession session = httpServletRequest.getSession();

        if(servlet.contains("/null")){
            httpServletRequest.getRequestDispatcher("/index").forward(httpServletRequest,httpServletResponse);
            return false;
        }
        //如果ip被封
        if(ipService.isBanned(ip)){
            return false;
        }
        //如果用户登录后被封
        if(session.getAttribute("currentUser") != null){
            UserRegister ur = (UserRegister) session.getAttribute("currentUser");
            if(userRegisterService.isBanned(ur)){
                for (Cookie cookie:cookies){
                    if (cookie.getName().equals("currentUser")){
                        cookie.setMaxAge(0);
                        httpServletResponse.addCookie(cookie);
                    }
                }
                session.setAttribute("currentUser",null);
                session.setAttribute("UserInformation",null);
                session.setAttribute("message", "用户被封");
                session.setAttribute("redirectTo", "/signin");
                httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest, httpServletResponse);
                return false;
            }
        }

        if(servlet.contains("message")){
            return true;
        }

        if (referer != null
        		&& referer.indexOf("local")>=0
                && referer.indexOf("sign") < 0
                && referer.indexOf("alidate") < 0
                && referer.indexOf("log") < 0
                && referer.indexOf("forget") < 0
                && referer.indexOf("message") < 0
                ) { //&&referer.indexOf("signin")<0&&referer.indexOf("signup")<0
            session.setAttribute("previousPage", referer);
        }
        //System.out.println("previousPage:"+session.getAttribute("previousPage"));
        if (servlet.indexOf("signout") >= 0){
            //session.invalidate();
            if (cookies != null){
                for (Cookie cookie:cookies){
                    if (cookie.getName().equals("currentUser")){
                        cookie.setMaxAge(0);
                        httpServletResponse.addCookie(cookie);
                    }
                }
            }

            if (session.getAttribute("currentUser") != null) {
                //referer = referer==null?"/signin":referer;
                session.removeAttribute("currentUser");
                session.setAttribute("redirectTo", session.getAttribute("previousPage"));
                return true;
            }
            else{
                session.setAttribute("message", "请先登录");
                session.setAttribute("redirectTo", "/signin");
                httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest, httpServletResponse);
                return false;
            }
        }

        //自动登录
        /*

         */
        if (session.getAttribute("currentUser") == null && cookies != null){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("currentUser")){
                    String userinfo = cookie.getValue();
                    String[] infos = userinfo.split(":");
                    //登录前获取上一次登录时间
                    if(infos.length==2){
                        ServiceResult ursr = userRegisterService.findByLoginName(infos[0]);
                        UserRegister user = (UserRegister) ursr.getData();
                        long last = 0;
                        if (user!=null){
                            last = user.getLastLoginTime().getTime();
                        }
                        //登录后上一次登录时间更新
                        ServiceResult sr= userRegisterService.login(infos[0], infos[1]);
                        if (sr.isSuccess()){
                            //登录后获取最近的的登录时间
                            UserRegister ur = (UserRegister) sr.getData();
                            long now = ur.getLastLoginTime().getTime();
                            //获取UserInformation放入session
                            ServiceResult uisr = userInformationService.findById(ur.getId());
                            UserInformation ui = (UserInformation) uisr.getData();
                            UserInformationVO uivo = new UserInformationVO(ui);

                            session.setAttribute("currentUser", ur);
                            session.setAttribute("UserInformation",uivo);
                            //更新coolie
                            cookie.setMaxAge(60 * 60 * 24 * 30);
                            cookie.setPath("/");
                            httpServletResponse.addCookie(cookie);
                            if (now - last >= 1000*60*60){
                                LoginRecord lr = new LoginRecord(ur);
//                            lr.setIp(IpAddress.getIpAddr(httpServletRequest));
                                lr.setIp(ip);
                                ui.setLatestIP(ip);
                                userInformationService.modify(ui);
                                loginRecordService.add(lr);
                                System.out.println("**********interceptor登录记录**********");
                            }
                            return true;
                        }
                        else if(sr.getMessage().equals("User is banned")){
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            httpServletResponse.addCookie(cookie);
                            session.setAttribute("message", "用户被封");
                            session.setAttribute("redirectTo", "/signin");
                            httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest, httpServletResponse);
                            return false;
                        }
                        else{
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            httpServletResponse.addCookie(cookie);
                            session.setAttribute("currentUser",null);
                            session.setAttribute("message", "cookie失效");
                            session.setAttribute("redirectTo", "/signin");
                            httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest, httpServletResponse);
                            return false;
                        }
                    }
                }
            }
        }

        //如果已经登录，则不再访问登录界面、请求登录服务或者访问注册界面
        if(servlet.indexOf("signin")>=0||servlet.indexOf("signup")>=0){
            if(session.getAttribute("currentUser")!=null){
                session.setAttribute("message","已经登录");
                session.setAttribute("redirectTo",session.getAttribute("previousPage"));
                httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest,httpServletResponse);
                return false;
            }
            else{
                session.setAttribute("redirectTo",session.getAttribute("previousPage"));
                return true;
            }

        }
        //个人中心需要登录后才能访问
        if(servlet.indexOf("home")>=0||servlet.indexOf("bindEmail")>=0||servlet.contains("addvote")){
            if(session.getAttribute("currentUser")==null){
                session.setAttribute("message","请先登录");
                session.setAttribute("redirectTo","/signin");
                httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest,httpServletResponse);
                //httpServletResponse.sendRedirect("/signin");
                return false;
            }
            else {
                return true;
            }
        }
        //管理中心需要管理员权限才能访问
        if(servlet.indexOf("admin")>=0){
            //没有登录，不能访问，重定向到登录界面
            if(session.getAttribute("currentUser")==null){
                session.setAttribute("message","请先登录");
                session.setAttribute("redirectTo","/signin");
                httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest,httpServletResponse);
                return false;
            }
            //已经登录则检查权限
            else if(session.getAttribute("currentUser")!=null){
                UserRegister ur= (UserRegister) session.getAttribute("currentUser");
                //没有权限，不能访问
                if(ur.getAuthority()!=0){
                    session.setAttribute("message","没有权限");
                    session.setAttribute("redirectTo","/index");
                    httpServletRequest.getRequestDispatcher("/message").forward(httpServletRequest,httpServletResponse);
                    //httpServletResponse.sendRedirect("/index");
                    return false;
                }
                else{
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("----------postHandle----------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
