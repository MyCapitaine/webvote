package com.example.controller;

/**
 * Created by hasee on 2017/3/6.
 */
        import com.example.dao.UserDao;
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
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.ModelMap;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.context.request.RequestContextHolder;
        import org.springframework.web.context.request.ServletRequestAttributes;


        import javax.servlet.http.Cookie;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.math.BigDecimal;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.List;
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage"})//
public class userController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private ActiveValidateService activeValidateService;
    @Autowired
    private ResetPasswordValidateService resetPasswordValidateService;

    private int page_size=10;

    /************************用户登录***************************/
    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(LoginVO form, ModelMap model,HttpSession session,HttpServletResponse httpServletResponse){
        System.out.println("loginName: "+form.getLoginName()+" pwd: "+form.getLoginPassword());
        JsonResult jr=new JsonResult();
        jr.setData(null);
        jr.setMessage("log in failed");
        jr.setSuccess(false);

        ServiceResult ursr= userRegisterService.login(form.getLoginName(),form.getLoginPassword());
        jr.setMessage(ursr.getMessage());
        jr.setSuccess(ursr.isSuccess());
        if(ursr.isSuccess()){
            UserRegister ur = (UserRegister)ursr.getData();
            ServiceResult uisr = userInformationService.findById(ur.getId());
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
    public JsonResult register(RegisterVO form , ModelMap model){
        JsonResult js = new JsonResult();
        js.setData(null);
        js.setMessage("register failed");
        js.setSuccess(false);

        UserRegister ur = new UserRegister();
        ur.setLoginPassword(form.getLoginPassword());
        ur.setBindingEmail(form.getBindingEmail());
        ur.setLogin_name(form.getLoginName());
        ur.setRegisterTime(new Date());

        try{
            ServiceResult ursr = userRegisterService.register(ur);
            ur = (UserRegister) ursr.getData();

            UserInformation ui = new UserInformation(ur);
            ServiceResult uisr = userInformationService.register(ui);

            ServiceResult avsr = activeValidateService.add(ur);
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
        ServiceResult avsr= activeValidateService.validate(token);
        if(avsr.isSuccess()){
            ActiveValidate av = (ActiveValidate)avsr.getData();
            ServiceResult uisr = userInformationService.findById(av.getId());
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
    public String resetPassword(){
        return "resetPassword";
    }
    //找回密码时 validate remote访问方法，邮箱是否被绑定，被绑定则返回"true"
    @RequestMapping("/isEmailBinding")
    @ResponseBody
    public String isEmailBinding(String bindingEmail){
        return userRegisterService.isEmailBinding(bindingEmail)+"";
    }
    @RequestMapping("/sendResetPasswordEmail")
    @ResponseBody
    public JsonResult sendResetPasswordEmail(String email,ModelMap model){
        JsonResult jr = new JsonResult();
        jr.setData(null);
        jr.setMessage("Email is not binding");
        jr.setSuccess(false);

        ServiceResult ursr = userRegisterService.findByBindEmail(email);
        List<UserRegister> urs = (List) ursr.getData();

        if(urs!=null){
            try{
                for(UserRegister ur : urs){
                    ServiceResult sr = resetPasswordValidateService.add(ur);
                    ResetPasswordValidate rpv = (ResetPasswordValidate)sr.getData();
                    String validateCode = rpv.getValidateCode();

                    SendEmail se = SendEmailFactory.getInstance(SendResetPasswordEmail.class);
                    se.send(email,validateCode);

                    model.addAttribute("redirectTo","index");
                    model.addAttribute("message","邮件发送成功，请在3分钟内查看邮件完成重置密码操作");

                    jr.setMessage("Send email to reset password success");
                    jr.setSuccess(true);
                }
            } catch (ActiveValidateServiceException e) {
                jr.setMessage(e.getMessage());
            } catch (SendEmailException e) {
                jr.setMessage(e.getMessage());
            }
        }

        return jr;
    }
    @RequestMapping("/resetPasswordValidate")
    public String resetPasswordValidate(@RequestParam(value = "token")String validateCode,ModelMap model){

        ServiceResult rpvsr = resetPasswordValidateService.validate(validateCode);
        if(rpvsr.isSuccess()){

            return "setPassword";
        }
        else if(rpvsr.getMessage().equals("link overdue")){
            model.addAttribute("redirectTo","forgetPassword");
            model.addAttribute("message","链接过期");
        }
        else if(rpvsr.getMessage().equals("link lose efficacy")){
            model.addAttribute("redirectTo","forgetPassword");
            model.addAttribute("message","链接已使用");
        }
        return "message";
    }



    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult<Object[]> delete(int[] id_array,int page_index){
        System.out.println("ids.size is"+id_array);
        JsonResult delete = userService.delete(id_array,page_index,page_size);
        if (delete != null ) {
            return  delete;
        }
        return null;
    }
    @RequestMapping("/initPage")
    @ResponseBody
    public JsonResult<Object[]> initPage(int  page_index){
        System.out.println("initpage:"+page_index);
        JsonResult page = userService.initPage(page_index,page_size);
        if (page != null ) {
            return  page;
        }
        return null;
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public JsonResult<Object[]> findById(String id){
        JsonResult user = userService.findById(Integer.parseInt(id));
        if (user != null ) {
            return  user;
        }
        return null;
    }
    @RequestMapping("/getName")
    @ResponseBody
    public JsonResult<Object[]> getByName(String name) {
        List<User> userList = userDao.findByName(name);
        if (userList != null && userList.size()!=0) {
            //Gson gson = new Gson();
            //String jsonArray = gson.toJson(userList);
            //return userList;
           // return  userList;
            return  new JsonResult(userList);
           // return jsonArray;
            //return "The user length is: " + userList.size();
        }
        return null;
        //return new ServiceResult("");
        //return "user " + name + " is not exist.";
    }
    @RequestMapping("/findName")
    @ResponseBody
    public JsonResult<Object[]> findByName(String name) {
        JsonResult<Object[]> userList = userService.findByName(name);
        if (userList != null ) {
            return  userList;
        }
        return null;
    }

    @RequestMapping("/getSex")
    @ResponseBody
    public String getBySex(char sex) {
        List<User> userList = userDao.findBySex(sex);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + sex + " is not exist.";
    }

    @RequestMapping("/getBirthday")
    @ResponseBody
    public String findByBirthday(String birthday) {
        System.out.println("birthday:"+birthday);
        SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
        List<User> userList = null;
        try {
            userList = userDao.findByBirthday(formate.parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + birthday + " is not exist.";
    }

    @RequestMapping("/getSendTime")
    @ResponseBody
    public JsonResult<Object[]> findBySendtime(String sendTime) {
        System.out.println("sendTime:"+sendTime);

        if(sendTime!=null&&sendTime!=""){
            SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<User> userList = null;
            try {
                userList = userDao.findBySendtime(formate.parse(sendTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (userList != null && userList.size()!=0) {
                return  new JsonResult(userList);
            }
        }
        return null;
    }

    @RequestMapping("/getPrice")
    @ResponseBody
    public String findByPrice(BigDecimal price) {
        List<User> userList = null;
        userList = userDao.findByPrice(price);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + price + " is not exist.";
    }

    @RequestMapping("/getFloatprice")
    @ResponseBody
    public String findFloatprice(float floatprice) {
        List<User> userList = null;
        userList = userDao.findByFloatprice(floatprice);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + floatprice + " is not exist.";
    }

    @RequestMapping("/getDoubleprice")
    @ResponseBody
    public String findByPrice(double doubleprice) {
        List<User> userList = null;
        userList = userDao.findByDoubleprice(doubleprice);
        if (userList != null && userList.size()!=0) {
            return "The user length is: " + userList.size();
        }
        return "user " + doubleprice + " is not exist.";
    }

    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {}
        return session;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getRequest();
    }
}
