package com.example.controller;

import com.example.dao.LoginRecordDao;
import com.example.entity.*;
import com.example.exception.ActiveValidateServiceException;
import com.example.exception.SendEmailException;
import com.example.serviceInterface.*;
import com.example.util.*;
import com.example.vo.ModifyInformationVO;
import com.example.vo.UserInformationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hasee on 2017/4/18.
 */
@Controller
@SessionAttributes({"currentUser","message","redirectTo","previousPage","UserInformation"})
public class UserInformationController {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private ResetBindingEmailValidateService resetBindingEmailValidateService;

    /************************个人中心***************************/
    @RequestMapping("/home")
    public String home(ModelMap model){
        return "home_information";
    }
    /************************修改头像***************************/
    @RequestMapping("/home/modifyPortrait")
    @ResponseBody
    public JsonResult modifyPortrait(@RequestParam("file") MultipartFile file,
                                     @RequestParam("id") int id,
                                     ModelMap model){
        JsonResult jr = new JsonResult();
        System.out.println(file.getOriginalFilename().lastIndexOf("."));
        System.out.println(file.getOriginalFilename().split("\\.")[0]);
        System.out.println(id);
        if(!file.isEmpty()){
            try{
                String fileName = file.getOriginalFilename();
                String[] fix = fileName.split("\\.");
                String codeFileName = Code.MD5Encoder(fix[0],"utf-8");

                File direction = new  File("F:/images/");
                if(!direction.exists()){
                    direction.mkdir();
                }
                String path = "F:/images/" +codeFileName+"."+fix[1];

                file.transferTo(new File(path));

                String portrait = "/portrait/"+codeFileName+"."+fix[1];
                ServiceResult uisr = userInformationService.findById(id);
                UserInformation ui = (UserInformation) uisr.getData();
                ui.setPortrait(portrait);
                userInformationService.modify(ui);

                UserInformationVO uivo = new UserInformationVO(ui);
                model.addAttribute("UserInformation",uivo);
                jr.setData(ui);
                jr.setSuccess(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return jr;
    }

    /************************修改基本信息***************************/
    //检测昵称是否被占用。被占用返回"false"
    @RequestMapping("/home/isNickNameUsed")
    @ResponseBody
    public String isNickNameUsed(int id,String nickName){
        return !userInformationService.isNickNameUsed(id, nickName)+"";
    }

    @RequestMapping("/home/modify")
    @ResponseBody
    public JsonResult modify(ModifyInformationVO form,ModelMap model){
        JsonResult jr = new JsonResult();
        jr.setData(null);
        jr.setSuccess(false);
        jr.setMessage("modify failed");

        ServiceResult<UserInformation> uisr = userInformationService.findById(form.getId());
        UserInformation ui =  uisr.getData();

        ui.setNickName(form.getNickName());
        ui.setSign(form.getSign());
        ui.setSex(form.getSex());
        ui.setBirthday(form.getBirthday());
//        String time = form.getBirthday();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        Date date=null;
//        try {
//            date = sdf.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        ui.setBirthday(date);
        userInformationService.modify(ui);

        UserInformationVO uivo = new UserInformationVO(ui);
        model.addAttribute("UserInformation",uivo);
        jr.setData(ui);
        jr.setMessage("modify success");
        jr.setSuccess(true);

        return jr;
    }
    /************************修改绑定邮箱***************************/
    @RequestMapping("/home/resetBE")
    public String resetBE(ModelMap model){
        return "sendEmailForResetBE";
    }

    @RequestMapping("/home/sendEmailForResetBE")
    @ResponseBody
    public JsonResult sendEmailForResetBE(int id,ModelMap model){
        JsonResult jr = new JsonResult();
        jr.setMessage("failed");
        jr.setSuccess(false);

        ServiceResult<UserRegister> ursr = userRegisterService.findById(id);
        UserRegister ur = ursr.getData();
        String email = ur.getBindingEmail();
        try {
            ServiceResult<ResetBindingEmailValidate> sr = resetBindingEmailValidateService.add(id,email);
            ResetBindingEmailValidate rbev =  sr.getData();
            String validateCode = rbev.getValidateCode();

            StringBuilder url = new StringBuilder("token=");
            url.append(validateCode);
            url.append("&id=");
            url.append(id);

            SendEmail se = SendEmailFactory.getInstance(SendValidateEmailForResetBE.class);
            se.send(email,url.toString());

            model.addAttribute("redirectTo","index");
            model.addAttribute("message","邮件发送成功，请在3分钟内查看邮件完成绑定操作");

            jr.setMessage("Send email to reset binding email success");
            jr.setSuccess(true);

        } catch (ActiveValidateServiceException e) {
            jr.setMessage(e.getMessage());
        } catch (SendEmailException e) {
            jr.setMessage(e.getMessage());
        }
        return jr;
    }
    @RequestMapping("/home/resetBindingEmailValidate")
    public String resetBindingEmail(int id,String token,ModelMap model){

        ServiceResult<ResetBindingEmailValidate> sr = resetBindingEmailValidateService.validate(id,token);
        if(sr.isSuccess()){
            model.addAttribute("message","验证通过");
            model.addAttribute("redirectTo","/bindEmail");
        }
        else if(sr.getMessage().equals("link overdue")){
            model.addAttribute("message","连接过期，请重新发送邮件");
            model.addAttribute("redirectTo","/home/resetBE");
        }
        else if(sr.getMessage().equals("link lose efficacy")){
            model.addAttribute("redirectTo","/home/resetBE");
            model.addAttribute("message","链接已使用");
        }
        return "message";
    }

    /************************我的账号***************************/
    @RequestMapping("/home/safe")
    public String safe(ModelMap model,@RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex,
                       @ModelAttribute(name = "currentUser")UserRegister ur){
//        ServiceResult sr = userRegisterService.findById(ur.getId());
//        UserRegister ur = (UserRegister) sr.getData();
        ur.setBindingEmail(Encrypt.encryptEmailPrefix(ur.getBindingEmail()));
        ur.setLoginName(Encrypt.encrypt(ur.getLoginName()));
        ur.setLoginPassword(Encrypt.encrypt(ur.getLoginPassword()));

        model.addAttribute("userRegister",ur);
        model.addAttribute("pageIndex",pageIndex);
        return "home_safe";
    }

    @RequestMapping("/home/loginRecord")
    @ResponseBody
    public JsonResult loginRecord(@ModelAttribute(value = "currentUser")UserRegister ur,int pageIndex){
        int pageSize= 5;
        JsonResult jr = new JsonResult();
        jr.setMessage("failed");
        jr.setSuccess(false);
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult lrsr = loginRecordService.find(ur.getId(),page);
        if(lrsr.isSuccess()){
            jr.setData(lrsr.getData());
            jr.setMessage("success");
            jr.setSuccess(true);
        }
        return jr;
    }
    /************************我的留言***************************/
    @RequestMapping("/home/comment")
    public String comment(ModelMap model,@RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
        model.addAttribute("pageIndex",pageIndex);
        return "home_comment";
    }
    /************************我的投票***************************/
    @RequestMapping("home/vote")
    public String vote(ModelMap model,
                       @RequestParam(value = "type",defaultValue = "publish")String type,
                       @RequestParam(value = "pageIndex",defaultValue = "1")int pageIndex){
        model.addAttribute("type",type);
        model.addAttribute("pageIndex",pageIndex);
        return "home_vote";
    }

    @Autowired
    LoginRecordDao loginRecordDao;
    @RequestMapping("home/joinVote")
    @ResponseBody
    public JsonResult joinVote(@ModelAttribute(value = "currentUser")UserRegister ur,int pageIndex){
        int pageSize=5;
        JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        Page result = loginRecordDao.findByUserId(ur.getId(),page);
        jr.setData(result);
        jr.setSuccess(true);
        jr.setMessage("");
        return jr;
    }

    @RequestMapping("home/publishVote")
    @ResponseBody
    public JsonResult publishVote(@ModelAttribute(value = "currentUser")UserRegister ur,int pageIndex){
        int pageSize=5;
        System.out.println("getVotes index:"+pageIndex);
        JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(pageIndex, pageSize);
        ServiceResult vsr = voteService.findVoteByUid(ur.getId(),page);
        //Page result = loginRecordDao.findByUserId2(ur.getId(),page);
        jr.setData(vsr.getData());
        jr.setSuccess(true);
        return jr;
    }

}
