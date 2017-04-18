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
        import com.example.util.SendActiveValidateEmail;
        import com.example.util.SendEmailFactory;
        import com.example.util.SendResetPasswordEmail;
        import com.example.vo.LoginVO;
        import com.example.vo.RegisterVO;
import com.example.vo.SetPasswordVO;

import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
        import org.springframework.web.bind.annotation.*;


        import javax.servlet.http.Cookie;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.util.Date;

@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage"})//
public class UserRegisterController {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private ActiveValidateService activeValidateService;
    @Autowired
    private ResetPasswordValidateService resetPasswordValidateService;
    @Autowired
    private SetPasswordValidateService setPasswordValidateService;



    /************************用户登录***************************/
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

    //登录验证
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult<UserInformation> login(LoginVO form, ModelMap model,HttpSession session,HttpServletResponse httpServletResponse){
        System.out.println("loginName: "+form.getLoginName()+" pwd: "+form.getLoginPassword());
        JsonResult<UserInformation> jr=new JsonResult<UserInformation>();
        jr.setData(null);
        jr.setMessage("log in failed");
        jr.setSuccess(false);

        ServiceResult<?> ursr= userRegisterService.login(form.getLoginName(),form.getLoginPassword());
        jr.setMessage(ursr.getMessage());
        jr.setSuccess(ursr.isSuccess());
        if(ursr.isSuccess()){
            UserRegister ur = (UserRegister)ursr.getData();
            ServiceResult<?> uisr = userInformationService.findById(ur.getId());
            UserInformation ui = (UserInformation)uisr.getData();
            
            jr.setData(ui);
            jr.setMessage(ursr.getMessage());
            jr.setSuccess(ursr.isSuccess());
            model.addAttribute("currentUser",ui);
            
            if(form.getRemember()){
                String cu=form.getLoginName()+":"+form.getLoginPassword();
                Cookie cookie=new Cookie("currentUser",cu);
                cookie.setMaxAge(60*60*24*30);
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
            }
            
        }
        return jr;
    }

    /************************注册用户***************************/
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

    //同上，绑定邮箱是否被占用，被占用则返回"false"
    @RequestMapping("/isBindingEmailUsed")
    @ResponseBody
    public String isBindingEmailUsed(String bindingEmail){return !userRegisterService.isEmailBinding(bindingEmail)+"";}

    @RequestMapping("/register")
    @ResponseBody
    public JsonResult<UserInformation> register(RegisterVO form , ModelMap model){
        JsonResult<UserInformation> js = new JsonResult<UserInformation>();
        js.setData(null);
        js.setMessage("register failed");
        js.setSuccess(false);

        UserRegister ur = new UserRegister();
        ur.setLoginPassword(form.getLoginPassword());
        ur.setBindingEmail(form.getBindingEmail());
        ur.setLogin_name(form.getLoginName());
        ur.setRegisterTime(new Date());

        try{
            ServiceResult<UserRegister> ursr = userRegisterService.register(ur);
            ur = (UserRegister) ursr.getData();

            UserInformation ui = new UserInformation(ur);
            ui.setNickName(form.getNickName());
            ServiceResult<?> uisr = userInformationService.register(ui);
            ui = (UserInformation) uisr.getData();

            ServiceResult<?> avsr = activeValidateService.add(ur);
            ActiveValidate av = (ActiveValidate)avsr.getData();
            String validator = av.getValidator() ;

            SendEmail se = SendEmailFactory.getInstance(SendActiveValidateEmail.class);
            se.send(av.getBindingEmail(),validator);

            model.addAttribute("currentUser",ui);
            model.addAttribute("redirectTo","index");
            model.addAttribute("message","注册成功，请在72小时内查看邮件激活！");
            js.setMessage("register success");
            js.setSuccess(true);
            js.setData(ui);
        }
        catch (UserRegisterServiceException e) {
            js.setMessage(e.getMessage());
//            return js;
        }
        catch (UserInformationServiceException e){
            userRegisterService.delete(ur);
            js.setMessage(e.getMessage());
        }
        catch (ActiveValidateServiceException e){
            userRegisterService.delete(ur);
            userInformationService.delete(ur);
            js.setMessage(e.getMessage());
        }
        catch(SendEmailException e){
            userRegisterService.delete(ur);
            userInformationService.delete(ur);
            activeValidateService.delete(ur);
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
//                ServiceResult avsr = activeValidateService.add(ur);
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

    @RequestMapping(value="/activeValidate")
    public String activeValidate(String token,ModelMap model){
        model.addAttribute("validate",token);
        ServiceResult<?> avsr= activeValidateService.validate(token);
        if(avsr.isSuccess()){
            ActiveValidate av = (ActiveValidate)avsr.getData();
            ServiceResult<?> uisr = userInformationService.findById(av.getId());
            UserInformation ui = (UserInformation)uisr.getData();
            model.addAttribute("currentUser",ui);
            model.addAttribute("message","成功激活，将跳转到首页");
            model.addAttribute("redirectTo","/index");
            return "/message";
        }
        else if(avsr.getMessage().equals("已经激活")){
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

        ServiceResult<?> ursr = userRegisterService.findByBindEmail(email);
        UserRegister ur = (UserRegister) ursr.getData();

        if(ur!=null){
            try{

                ServiceResult<?> sr = resetPasswordValidateService.add(ur);                   
                ResetPasswordValidate rpv = (ResetPasswordValidate)sr.getData();                    
                String validateCode = rpv.getValidateCode();                  
                int id=rpv.getId();
                
                StringBuilder url = new StringBuilder("token=");
                url.append(validateCode);
                url.append("&id=");
                url.append(id);

                SendEmail se = SendEmailFactory.getInstance(SendResetPasswordEmail.class);
                se.send(email,url.toString());

                model.addAttribute("redirectTo","index");
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
    public String resetPasswordValidate(@RequestParam(value = "token")String validateCode,int id,ModelMap modelMap,Model model){

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
        	modelMap.addAttribute("redirectTo","forgetPassword");
        	modelMap.addAttribute("message","链接过期");
        }
        else if(rpvsr.getMessage().equals("link lose efficacy")){
        	modelMap.addAttribute("redirectTo","forgetPassword");
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
    		System.out.println("validate success");
    		SetPasswordValidate spv = (SetPasswordValidate) spvsr.getData();
    		ServiceResult<UserRegister> ursr = userRegisterService.findById(spv.getId());
    		UserRegister ur = (UserRegister) ursr.getData();
    		ur.setLoginPassword(password);
    		userRegisterService.modify(ur);
    		
    		jr.setMessage("reset success");
    		jr.setSuccess(true);
    		
    		model.addAttribute("redirectTo","signin");
    		model.addAttribute("message","重置密码成功，请登录");
    	}
    	else{
    		System.out.println("validate failed");
    		model.addAttribute("redirectTo","forgetPassword");
    		model.addAttribute("message","验证错误，请重新发送邮件");
    	}
    	
    	return jr;
    }
    

}
