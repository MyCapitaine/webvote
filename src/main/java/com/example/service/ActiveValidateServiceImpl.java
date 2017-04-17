package com.example.service;

import com.example.dao.ActiveValidateDao;
import com.example.dao.UserInformationDao;
import com.example.entity.ActiveValidate;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;
import com.example.exception.SendEmailException;
import com.example.serviceInterface.ActiveValidateService;
import com.example.serviceInterface.SendEmail;
import com.example.util.Code;
import com.example.util.SendActiveValidateEmail;
import com.example.util.SendEmailFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2017/4/11.
 */
@Service
public class ActiveValidateServiceImpl implements ActiveValidateService {
    @Autowired
    private ActiveValidateDao activeValidateDao;

    public ServiceResult validate(String validator){
        Date now=new Date();

        ServiceResult sr=new ServiceResult();
        sr.setData(null);
        sr.setMessage("validate failed");
        sr.setSuccess(false);

        List<ActiveValidate> avs=activeValidateDao.findByValidator(validator);
        if(avs.size()!=0){
            for(ActiveValidate av:avs){
                if(now.before(av.getDeadline())){
                    sr.setData(av);
                    sr.setSuccess(true);
                    sr.setMessage("validate success");
                    activeValidateDao.delete(av.getId());
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

    public ServiceResult add(UserRegister ur) throws ActiveValidateServiceException{
        ServiceResult sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("create validator failed");
        sr.setSuccess(false);

        try{
            String validator= Code.MD5Encoder(ur.getBindingEmail(),"utf-8");
            if(activeValidateDao.findByValidator(validator).size()!=0){
                validator=randomValidator(ur);
                if(activeValidateDao.findByValidator(validator).size()!=0){
                    validator=""+ur.getId();
                }
            }
            ActiveValidate av=new ActiveValidate(ur);
            av.setValidator(validator);
            activeValidateDao.save(av);

            sr.setData(av);
            sr.setMessage("create validator success");
            sr.setSuccess(true);
        } catch (Exception e) {
            throw new ActiveValidateServiceException(e.getMessage());
        }
        return sr;
    }

    public void delete(UserRegister ur){
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
        SendEmail se = SendEmailFactory.getInstance(SendActiveValidateEmail.class);
        se.send(av.getBindingEmail(),av.getValidator());
        return av.getValidator();
    }
}
