package com.example.service;

import com.example.dao.ResetBindingEmailValidateDao;
import com.example.dao.ResetPasswordValidateDao;
import com.example.entity.ResetBindingEmailValidate;
import com.example.entity.ResetPasswordValidate;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;
import com.example.serviceInterface.ResetBindingEmailValidateService;
import com.example.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by hasee on 2017/5/9.
 */
@Service
public class ResetBindingEmailValidateServiceImpl implements ResetBindingEmailValidateService {
    @Autowired
    private ResetBindingEmailValidateDao resetBindingEmailValidateDao;
    @Override
    public ServiceResult<ResetBindingEmailValidate> validate(int id, String validateCode) {
        Date now = new Date();
        ServiceResult<ResetBindingEmailValidate> sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("validate failed");
        sr.setSuccess(false);
        ResetBindingEmailValidate rbev = resetBindingEmailValidateDao.validate(id,validateCode);
        System.out.println(validateCode);
        System.out.println(resetBindingEmailValidateDao.findOne(id).getValidateCode());
        if(rbev!=null){
            Date deadline = rbev.getDeadline();
            if(now.before(deadline)){
                sr.setData(rbev);
                sr.setSuccess(true);
                sr.setMessage("validate success ");
                delete(rbev.getId());
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

    @Override
    public ServiceResult<ResetBindingEmailValidate> add(int id,String email) throws ActiveValidateServiceException {
        ServiceResult<ResetBindingEmailValidate> sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("create validateCode failed");
        sr.setSuccess(false);

        try{
            String validateCode= Code.MD5Encoder(email,"utf-8");
            ResetBindingEmailValidate rbev=new ResetBindingEmailValidate(id);
            rbev.setValidateCode(validateCode);
            resetBindingEmailValidateDao.save(rbev);

            sr.setData(rbev);
            sr.setMessage("create validator success");
            sr.setSuccess(true);
        } catch (Exception e) {
            throw new ActiveValidateServiceException(e.getMessage());
        }
        return sr;
    }

    private void delete(int id){
        ResetBindingEmailValidate rpv = resetBindingEmailValidateDao.findOne(id);
        if(rpv!=null){
            resetBindingEmailValidateDao.delete(id);
        }
    }
}
