package com.example.controller;

/**
 * Created by hasee on 2017/3/6.
 */
import com.example.entity.*;
import com.example.exception.ActiveValidateServiceException;
import com.example.exception.SendEmailException;
import com.example.exception.UserInformationServiceException;
import com.example.exception.UserRegisterServiceException;
import com.example.serviceInterface.*;
import com.example.util.*;
import com.example.vo.LoginVO;
import com.example.vo.RegisterVO;
import com.example.vo.SetPasswordVO;
import com.example.vo.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage","UserInformation"})//
public class UserRegisterController {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private BindingEmailValidateService bindingEmailValidateService;
    @Autowired
    private ResetPasswordValidateService resetPasswordValidateService;
    @Autowired
    private SetPasswordValidateService setPasswordValidateService;
    @Autowired
    private LoginRecordService loginRecordService;


    /************************用户登录***************************/
    //返回登录界面
    @RequestMapping("/signin")
    public String signin(ModelMap model){
        return "signin";
    }

    //退出登录，返回message界面
    @RequestMapping("/signout")
    public String signout(ModelMap model,SessionStatus sessionStatus){//SessionStatus sessionStatus,,@ModelAttribute(value = "previousPage")String previous
        model.addAttribute("message","退出登录");
        sessionStatus.setComplete();
        return "message";
    }

    //登录验证
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult<UserInformation> login(LoginVO form, ModelMap model,
                                             HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        JsonResult<UserInformation> jr = new JsonResult<UserInformation>();
        jr.setData(null);
        jr.setMessage("log in failed");
        jr.setSuccess(false);
        //登录前获取上一次登录时间
        ServiceResult<UserRegister> usersr = null;
        ServiceResult<UserRegister> usersr2 = null;
        if (form.getLoginName() != null) {
            usersr = userRegisterService.findByLoginName(form.getLoginName());
            usersr2 = userRegisterService.findByBindEmail(form.getLoginName());
        }
        UserRegister userr = null;
        long last = 0;
        if (usersr != null && usersr.getData() != null) {
            userr = usersr.getData();
            last = userr.getLastLoginTime().getTime();
        } else if (usersr2!= null && usersr2.getData() != null) {
            userr = usersr2.getData();
            last = userr.getLastLoginTime().getTime();
        } else {
            jr.setMessage("User is not exist");
            jr.setSuccess(false);
            return jr;
        }
        //登录后上一次登录时间更新
        ServiceResult<UserRegister> ursr = userRegisterService.login(form.getLoginName(), form.getLoginPassword());
        jr.setMessage(ursr.getMessage());
        jr.setSuccess(ursr.isSuccess());
        if (ursr.isSuccess()) {
            //登录后获取最近的的登录时间
            UserRegister ur = ursr.getData();
            long now = ur.getLastLoginTime().getTime();
            //获取UserInformation放入session
            ServiceResult<UserInformation> uisr = userInformationService.findById(ur.getId());
            UserInformation ui = uisr.getData();
            UserInformationVO uivo = new UserInformationVO(ui);
            //uivo.setBindingEmail(Encrypt.encryptEmailPrefix(uivo.getBindingEmail()));

            jr.setData(ui);
            jr.setMessage(ursr.getMessage());
            jr.setSuccess(ursr.isSuccess());
            model.addAttribute("currentUser", ur);
            model.addAttribute("UserInformation", uivo);
            if (now - last >= 1000 * 60 * 60) {
                LoginRecord lr = new LoginRecord(ur);
                lr.setIp(IpAddress.getIpAddr(httpServletRequest));
                ui.setLatestIP(IpAddress.getIpAddr(httpServletRequest));
                userInformationService.modify(ui);
                loginRecordService.add(lr);
                System.out.println("***********controller登录记录**********");
            }
            //remember me 利用cookie实现自动登录
            if (form.getRemember()) {
                String cu = form.getLoginName() + ":" + form.getLoginPassword();
                Cookie cookie = new Cookie("currentUser", cu);
                cookie.setMaxAge(60 * 60 * 24 * 30);
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
            }
        }
        return jr;
    }

    /************************注册用户*****************  **********/
    //返回注册界面
    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }

    //检测昵称是否被占用。被占用返回"false"
    @RequestMapping("/isNickNameUsed")
    @ResponseBody
    public String isNickNameUsed(String nickName){
        return !userInformationService.isNickNameUsed(nickName)+"";
    }

    //注册时 validate remote访问方法，用户名是否被占用，被占用返回"false"
    @RequestMapping("/isLoginNameUsed")
    @ResponseBody
    public String isLoginNameUsed(String loginName){
        return !userRegisterService.isLoginNameUsed(loginName)+"";
    }


    @RequestMapping("/register")
    @ResponseBody
    public JsonResult<UserInformation> register(RegisterVO form , ModelMap model,HttpServletRequest httpServletRequest){
        Date registerTime = new Date();
        JsonResult<UserInformation> js = new JsonResult<UserInformation>();
        js.setData(null);
        js.setMessage("register failed");
        js.setSuccess(false);

        UserRegister ur = new UserRegister();
        //ur.setAuthority(1);//注册用户默认权限
        ur.setLoginPassword(form.getLoginPassword());
        ur.setBindingEmail(form.getBindingEmail());
        ur.setLoginName(form.getLoginName());
        ur.setRegisterTime(registerTime);
        ur.setLastLoginTime(registerTime);

        try{
            ServiceResult<UserRegister> ursr = userRegisterService.register(ur);
            ur = ursr.getData();
            UserInformation ui = new UserInformation(ur);
            ui.setNickName(form.getNickName());
            ui.setLatestIP(IpAddress.getIpAddr(httpServletRequest));
            ServiceResult<UserInformation> uisr = userInformationService.register(ui);
            ui = uisr.getData();
            UserInformationVO uivo = new UserInformationVO(ui);

//            ServiceResult<?> avsr = bindingEmailValidateService.add(ur);
//            BindingEmailValidate av = (BindingEmailValidate)avsr.getData();
//            String validator = av.getValidator() ;
//
//            SendEmail se = SendEmailFactory.getInstance(SendValidateEmailForBindingEmail.class);
//            se.send(av.getBindingEmail(),validator);

            model.addAttribute("currentUser",ur);
            model.addAttribute("UserInformation",uivo);
            model.addAttribute("redirectTo","index");
            model.addAttribute("message","注册成功！");
//            model.addAttribute("message","注册成功，请在72小时内查看邮件激活！");
            js.setMessage("register success");
            js.setSuccess(true);
            js.setData(ui);
            LoginRecord lr = new LoginRecord(ur);
            lr.setIp(IpAddress.getIpAddr(httpServletRequest));
            loginRecordService.add(lr);
        }
        catch (UserRegisterServiceException e) {
            js.setMessage(e.getMessage());
//            return js;
        }
        catch (UserInformationServiceException e){
            userRegisterService.delete(ur);
            js.setMessage(e.getMessage());
        }

//        ServiceResult ursr = userRegisterService.register(ur);
//        ur = (UserRegister) ursr.getData();
//        js.setMessage(ursr.getMessage());
//        js.setSuccess(ursr.isSuccess());
//        //ServiceResult uisr = null;
//        //注册登录信息成功
//        if(ursr.isSuccess()){
//            UserInformation ui = new UserInformation(ur);
//            ServiceResult uisr = userInformationService.register(ui);
//            js.setMessage(uisr.getMessage());
//            js.setSuccess(uisr.isSuccess());
//            //注册用户信息成功
//            if(uisr.isSuccess()){
//                ServiceResult avsr = bindingEmailValidateService.add(ur);
//                js.setMessage(avsr.getMessage());
//                js.setSuccess(avsr.isSuccess());
//                if(avsr.isSuccess()){
//                    String validator = (String) avsr.getData();
//                    try {
//                        SendEmailServiceImpl.sendValidateEmail(ur.getBindingEmail(),validator);
//                        js.setData(ui);
//                    } catch (SendEmailException e) {
//                        js.setMessage(e.getMessage());
//                        js.setSuccess(false);
//                    }
//                }
//            }
//        }
        return js;
    }

    /************************绑定邮箱***************************/
    @RequestMapping("/bindEmail")
    public String bindEmail(Model model){
        return "bind_email";
    }

    //同上，绑定邮箱是否被占用，被占用则返回"false"
    @RequestMapping("/isBindingEmailUsed")
    @ResponseBody
    public String isBindingEmailUsed(String bindingEmail){return !userRegisterService.isEmailBinding(bindingEmail)+"";}

    @RequestMapping("/sendEmailForBindingEmail")
    @ResponseBody
    public JsonResult sendEmailForBindingEmail(int id,String email,ModelMap model){
        JsonResult jr = new JsonResult();
        jr.setMessage("failed");
        jr.setSuccess(false);

        try {
            ServiceResult sr = bindingEmailValidateService.add(id,email);
            BindingEmailValidate bev = (BindingEmailValidate) sr.getData();
            String validateCode = bev.getValidator();

            StringBuilder url = new StringBuilder("token=");
            url.append(validateCode);
            url.append("&id=");
            url.append(id);

            SendEmail se = SendEmailFactory.getInstance(SendValidateEmailForBindingEmail.class);
            se.send(email,url.toString());

            model.addAttribute("redirectTo","index");
            model.addAttribute("message","邮件发送成功，请在3分钟内查看邮件完成绑定邮箱操作");

            jr.setMessage("Send email to binding email success");
            jr.setSuccess(true);

        } catch (ActiveValidateServiceException e) {
            jr.setMessage(e.getMessage());
        } catch (SendEmailException e) {
            jr.setMessage(e.getMessage());
        }

        return jr;
    }

    @RequestMapping(value="/bindingEmailValidate")
    public String activeValidate(@RequestParam(value = "token")String validateCode,int id,ModelMap model){
        System.out.println("code:"+validateCode+"id:"+id);
        model.addAttribute("validate",validateCode);
        ServiceResult<?> avsr= bindingEmailValidateService.validate(id,validateCode);
        if(avsr.isSuccess()){
            BindingEmailValidate av = (BindingEmailValidate)avsr.getData();

            ServiceResult ursr = userRegisterService.findById(av.getId());
            UserRegister ur = (UserRegister) ursr.getData();
            ur.setBindingEmail(av.getBindingEmail());
            userRegisterService.modify(ur);

            ServiceResult<?> uisr = userInformationService.findById(av.getId());
            UserInformation ui = (UserInformation)uisr.getData();
            ui.setBindingEmail(av.getBindingEmail());
            userInformationService.modify(ui);

            UserInformationVO uivo = new UserInformationVO(ui);
            model.addAttribute("currentUser",ur);
            model.addAttribute("UserInformation",uivo);
            model.addAttribute("message","绑定成功，将跳转到首页");
            model.addAttribute("redirectTo","/index");
        }
        else if(avsr.getMessage().equals("link overdue")){
            model.addAttribute("message","连接过期，请重新发送邮件");
            //model.addAttribute("previousPage","index");
            model.addAttribute("redirectTo","/bindEmail");

        }
        else if(avsr.getMessage().equals("link lose efficacy")){
            model.addAttribute("redirectTo","/bindEmail");
            model.addAttribute("message","链接失效，请重新发送邮件");
        }
        return "/message";
    }



    /************************找回密码***************************/
    @RequestMapping("/forgetPassword")
    public String forgetPassword(Model model){
        return "sendEmail";
    }

    //找回密码时 validate remote访问方法，邮箱是否被绑定，被绑定则返回"true"
    @RequestMapping("/isEmailBinding")
    @ResponseBody
    public String isEmailBinding(String bindingEmail){
        return userRegisterService.isEmailBinding(bindingEmail)+"";
    }

    @RequestMapping("/sendResetPasswordEmail")
    @ResponseBody
    public JsonResult<ResetPasswordValidate> sendResetPasswordEmail(String email,ModelMap model){
        JsonResult<ResetPasswordValidate> jr = new JsonResult<ResetPasswordValidate>();
        jr.setData(null);
        jr.setMessage("Email is not binding");
        jr.setSuccess(false);

        ServiceResult<UserRegister> ursr = userRegisterService.findByBindEmail(email);
        UserRegister ur = ursr.getData();

        if(ur!=null){
            try{

                ServiceResult<ResetPasswordValidate> sr = resetPasswordValidateService.add(ur);
                ResetPasswordValidate rpv = sr.getData();
                String validateCode = rpv.getValidateCode();                  
                int id=rpv.getId();
                
                StringBuilder url = new StringBuilder("token=");
                url.append(validateCode);
                url.append("&id=");
                url.append(id);
                SendEmail se = SendEmailFactory.getInstance(SendResetPasswordEmail.class);
                se.send(email,url.toString());

                model.addAttribute("redirectTo","/index");
                model.addAttribute("message","邮件发送成功，请在3分钟内查看邮件完成重置密码操作");

                jr.setMessage("Send email to reset password success");
                jr.setSuccess(true);

            } catch (ActiveValidateServiceException e) {
                jr.setMessage(e.getMessage());
            } catch (SendEmailException e) {
                jr.setMessage(e.getMessage());
            }
        }

        return jr;
    }

    @RequestMapping("/resetPasswordValidate")
    public String resetPasswordValidate(@RequestParam(value = "token")String validateCode,@RequestParam(value = "id")int id,ModelMap modelMap,Model model){

        ServiceResult<?> rpvsr = resetPasswordValidateService.validate(id,validateCode);        
        if(rpvsr.isSuccess()){
        	ResetPasswordValidate rpv = (ResetPasswordValidate)rpvsr.getData();
        	SetPasswordValidate spv = new SetPasswordValidate(rpv);
        	setPasswordValidateService.add(spv);
        	
        	model.addAttribute("id",rpv.getId());
        	model.addAttribute("validateCode", rpv.getValidateCode());        	
            return "resetPassword";
        }
        else if(rpvsr.getMessage().equals("link overdue")){
        	modelMap.addAttribute("redirectTo","/forgetPassword");
        	modelMap.addAttribute("message","链接过期");
        }
        else if(rpvsr.getMessage().equals("link lose efficacy")){
        	modelMap.addAttribute("redirectTo","/forgetPassword");
        	modelMap.addAttribute("message","链接已使用");
        }
        return "message";
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public JsonResult<Object> resetPassword(SetPasswordVO spvo,ModelMap model){
    	JsonResult<Object> jr = new JsonResult<Object>();
    	jr.setData(null);
    	jr.setMessage("reset failed");
    	jr.setSuccess(false);
    	int id = spvo.getId();
    	String validateCode = spvo.getValidateCode();
    	String password = spvo.getPassword();
    	
    	ServiceResult<SetPasswordValidate> spvsr = setPasswordValidateService.validate(id, validateCode);
    	if(spvsr.isSuccess()){
    		SetPasswordValidate spv = spvsr.getData();
    		ServiceResult<UserRegister> ursr = userRegisterService.findById(spv.getId());
    		UserRegister ur =  ursr.getData();
    		ur.setLoginPassword(password);
    		userRegisterService.modify(ur);
    		
    		jr.setMessage("reset success");
    		jr.setSuccess(true);
    		
    		model.addAttribute("redirectTo","/signin");
    		model.addAttribute("message","重置密码成功，请登录");
    	}
    	else{
    		model.addAttribute("redirectTo","/forgetPassword");
    		model.addAttribute("message","验证错误，请重新发送邮件");
    	}
    	
    	return jr;
    }
}
