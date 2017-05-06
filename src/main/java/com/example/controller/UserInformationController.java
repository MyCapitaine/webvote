package com.example.controller;

import com.example.dao.LoginRecordDao;
import com.example.entity.*;
import com.example.serviceInterface.LoginRecordService;
import com.example.serviceInterface.UserInformationService;
import com.example.serviceInterface.UserRegisterService;
import com.example.serviceInterface.VoteService;
import com.example.util.Code;
import com.example.util.Encrypt;
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

    /************************我的账号***************************/
    @RequestMapping("/home/safe")
    public String safe(ModelMap model,@RequestParam(value = "page_index",defaultValue = "1")int page_index,
                       @ModelAttribute(name = "currentUser")UserRegister ur){
//        ServiceResult sr = userRegisterService.findById(ur.getId());
//        UserRegister ur = (UserRegister) sr.getData();
        ur.setBindingEmail(Encrypt.encryptEmailPrefix(ur.getBindingEmail()));
        ur.setLoginName(Encrypt.encrypt(ur.getLoginName()));
        ur.setLoginPassword(Encrypt.encrypt(ur.getLoginPassword()));

        model.addAttribute("userRegister",ur);
        model.addAttribute("page_index",page_index);
        return "home_safe";
    }

    @RequestMapping("/home/loginRecord")
    @ResponseBody
    public JsonResult loginRecord(@ModelAttribute(value = "currentUser")UserRegister ur,int page_index){
        int page_size= 5;
        JsonResult jr = new JsonResult();
        jr.setMessage("failed");
        jr.setSuccess(false);
        Pageable page = new PageRequest(page_index, page_size);
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
    public String comment(ModelMap model,@RequestParam(value = "page_index",defaultValue = "1")int page_index){
        model.addAttribute("page_index",page_index);
        return "home_comment";
    }
    /************************我的投票***************************/
    @RequestMapping("home/vote")
    public String vote(ModelMap model,
                       @RequestParam(value = "type",defaultValue = "publish")String type,
                       @RequestParam(value = "page_index",defaultValue = "1")int page_index){
        model.addAttribute("type",type);
        model.addAttribute("page_index",page_index);
        return "home_vote";
    }

    @Autowired
    LoginRecordDao loginRecordDao;
    @RequestMapping("home/joinVote")
    @ResponseBody
    public JsonResult joinVote(@ModelAttribute(value = "currentUser")UserRegister ur,int page_index){
        int page_size=5;
        JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(page_index, page_size);
        Page result = loginRecordDao.findByUserId(ur.getId(),page);
        jr.setData(result);
        jr.setSuccess(true);
        jr.setMessage("");
        return jr;
    }

    @RequestMapping("home/publishVote")
    @ResponseBody
    public JsonResult publishVote(@ModelAttribute(value = "currentUser")UserRegister ur,int page_index){
        int page_size=5;
        JsonResult jr=new JsonResult();
        Pageable page = new PageRequest(page_index, page_size);
        ServiceResult vsr = voteService.findVoteByUid(ur.getId(),page);
        //Page result = loginRecordDao.findByUserId2(ur.getId(),page);
        jr.setData(vsr.getData());
        jr.setSuccess(true);
        return jr;
    }

}
