package com.example.service;

import com.example.dao.BindingEmailValidateDao;
import com.example.entity.BindingEmailValidate;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;
import com.example.serviceInterface.BindingEmailValidateService;
import com.example.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2017/4/11.
 */
@Service
public class BindingEmailValidateServiceImpl implements BindingEmailValidateService {
    @Autowired
    private BindingEmailValidateDao bindingEmailValidateDao;

    public ServiceResult validate(int id,String validator){
        Date now=new Date();

        ServiceResult sr=new ServiceResult();
        sr.setData(null);
        sr.setMessage("validate failed");
        sr.setSuccess(false);

        BindingEmailValidate bev= bindingEmailValidateDao.validate(id,validator);
        if(bev!=null){
            if(now.before(bev.getDeadline())){
                sr.setData(bev);
                sr.setSuccess(true);
                sr.setMessage("validate success");
                delete(bev.getId());
            }
            else{
                sr.setMessage("link overdue");
            }

        }
        else{
            sr.setMessage("link lose efficacy");
        }
        return sr;
    }

    public ServiceResult add(int id,String email) throws ActiveValidateServiceException{
        Date now = new Date();
        ServiceResult sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("create validator failed");
        sr.setSuccess(false);

        try{
            String validator= Code.MD5Encoder(email,"utf-8");
            long deadline=now.getTime()+1000*60*3;
            BindingEmailValidate bev=new BindingEmailValidate();
            bev.setDeadline(new Date(deadline));
            bev.setBindingEmail(email);
            bev.setId(id);
            bev.setValidator(validator);
            bindingEmailValidateDao.save(bev);

            sr.setData(bev);
            sr.setMessage("create validator success");
            sr.setSuccess(true);
        } catch (Exception e) {
            throw new ActiveValidateServiceException(e.getMessage());
        }
        return sr;
    }

    private void delete(int id){
        if(bindingEmailValidateDao.findOne(id)!=null)
            bindingEmailValidateDao.delete(id);
    }
//
//    private String randomValidator(UserRegister ur) throws Exception {
//        String validator=Code.MD5Encoder(ur.getLoginName()+ur.getBindingEmail(),"utf-8");
//        return validator;
//    }

//    private String validateAgain(BindingEmailValidate av) throws SendEmailException{
//        long now=new Date().getTime();
//        Date deadline=new Date(now+1000*60*60*24*3);
//        av.setDeadline(deadline);
//        activeValidateDao.save(av);
//        SendEmail se = SendEmailFactory.getInstance(SendValidateEmailForBindingEmail.class);
//        se.send(av.getBindingEmail(),av.getValidator());
//        return av.getValidator();
//    }
}
