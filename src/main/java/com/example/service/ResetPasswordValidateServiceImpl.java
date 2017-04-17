package com.example.service;

import com.example.dao.ResetPasswordValidateDao;
import com.example.entity.ActiveValidate;
import com.example.entity.ResetPasswordValidate;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;
import com.example.serviceInterface.ResetPasswordValidateService;
import com.example.util.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2017/4/14.
 */
@Service
public class ResetPasswordValidateServiceImpl implements ResetPasswordValidateService{

    @Autowired
    ResetPasswordValidateDao resetPasswordValidateDao;

    @Override
    public ServiceResult validate(String validateCode) {
        Date now = new Date();

        ServiceResult sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("validate failed");
        sr.setSuccess(false);

        List<ResetPasswordValidate> rpvs = resetPasswordValidateDao.findByValidateCode(validateCode);
        if(rpvs.size()>0){
            for(ResetPasswordValidate rpv : rpvs){
                Date deadline = rpv.getDeadline();
                if(now.before(deadline)){
                    sr.setData(rpv);
                    sr.setSuccess(true);
                    sr.setMessage("validate success");
                    delete(rpv.getId());
                }
                else{
                    sr.setMessage("link overdue");
                }
            }
        }
        else{
            sr.setMessage("link lose efficacy");
        }

        return sr;
    }

    @Override
    public ServiceResult add(UserRegister ur) throws ActiveValidateServiceException {
        ServiceResult sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("create validateCode failed");
        sr.setSuccess(false);

        try{
            String validateCode= Code.MD5Encoder(ur.getBindingEmail(),"utf-8");
            if(resetPasswordValidateDao.findByValidateCode(validateCode).size()!=0){
                validateCode=""+ur.getId();
            }
            ResetPasswordValidate rpv=new ResetPasswordValidate(ur.getId());
            rpv.setValidateCode(validateCode);
            resetPasswordValidateDao.save(rpv);

            sr.setData(rpv);
            sr.setMessage("create validator success");
            sr.setSuccess(true);
        } catch (Exception e) {
            throw new ActiveValidateServiceException(e.getMessage());
        }
        return sr;
    }

    @Override
    public void delete(int id) {
        ResetPasswordValidate rpv = resetPasswordValidateDao.findOne(id);
        if(rpv!=null){
            resetPasswordValidateDao.delete(id);
        }
    }
}
