package com.example.service;

import com.example.dao.ActiveValidateDao;
import com.example.dao.UserInformationDao;
import com.example.entity.ActiveValidate;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;
import com.example.exception.SendEmailException;
import com.example.serviceInterface.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2017/4/11.
 */
@Service
public class ActiveValidateServiceImpl implements ValidateService {
    @Autowired
    private ActiveValidateDao activeValidateDao;

    @Autowired
    private UserInformationDao userInformationDao;

    public ServiceResult validate(String validator){
        Date now=new Date();

        ServiceResult sr=new ServiceResult();
        sr.setData(null);
        sr.setMessage("validate failed");
        sr.setSuccess(false);

        List<ActiveValidate> avs=activeValidateDao.findByValidator(validator);
        System.out.println("size:"+avs.size());
        if(avs.size()!=0){
            for(ActiveValidate av:avs){
                if(now.before(av.getDeadline())){
                    sr.setData(userInformationDao.findOne(av.getId()));
                    activeValidateDao.delete(av.getId());
                    sr.setSuccess(true);
                    sr.setMessage("validate success");
                }
                else{
                    try {
                        validateAgain(av);
                    } catch (SendEmailException e) {
                        sr.setMessage(e.getMessage());
                        return sr;
                    }
                    sr.setMessage("验证码已过期！请查看新邮件激活");
                }
            }
        }
        else{
            sr.setMessage("已经激活");
        }
        return sr;
    }

    public ServiceResult getValidator(UserRegister ur) throws ActiveValidateServiceException{
        ServiceResult sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("create validator failed");
        sr.setSuccess(false);

        ActiveValidate av = null;
        try{
            String validator=Code.MD5Encoder(ur.getBindingEmail(),"utf-8");
            if(activeValidateDao.findByValidator(validator).size()!=0){
                validator=randomValidator(ur);
                if(activeValidateDao.findByValidator(validator).size()!=0){
                    validator=""+ur.getId();
                }
            }
            av=new ActiveValidate(ur);
            av.setValidator(validator);
            activeValidateDao.save(av);
        } catch (Exception e) {
            throw new ActiveValidateServiceException(e.getMessage());
        }

        sr.setData(av);
        sr.setMessage("create validator success");
        sr.setSuccess(true);

        return sr;
    }

    public void deleteValidator(UserRegister ur){
        if(activeValidateDao.findOne(ur.getId())!=null)
            activeValidateDao.delete(ur.getId());
    }

    private String randomValidator(UserRegister ur) throws Exception {
        String validator=Code.MD5Encoder(ur.getLoginName()+ur.getBindingEmail(),"utf-8");
        return validator;
    }

    private String validateAgain(ActiveValidate av) throws SendEmailException{
        long now=new Date().getTime();
        Date deadline=new Date(now+1000*60*60*24*3);
        av.setDeadline(deadline);
        activeValidateDao.save(av);
        SendEmail.sendValidateEmail(av.getBindingEmail(),av.getValidator());
        return av.getValidator();
    }
}
