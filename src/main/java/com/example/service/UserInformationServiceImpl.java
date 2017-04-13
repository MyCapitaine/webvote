package com.example.service;

import com.example.dao.UserInformationDao;
import com.example.entity.ServiceResult;
import com.example.entity.UserInformation;
import com.example.entity.UserRegister;
import com.example.exception.UserInformationServiceException;
import com.example.serviceInterface.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hasee on 2017/4/13.
 */
@Service
public class UserInformationServiceImpl implements UserInformationService {

    @Autowired
    UserInformationDao userInformationDao;

    @Override
    public ServiceResult register(UserInformation ui) throws UserInformationServiceException{

        ServiceResult sr=new ServiceResult();
        sr.setMessage("UserInformation register failed");
        sr.setData(null);
        sr.setSuccess(false);

        try{
            ui=userInformationDao.save(ui);
            sr.setData(ui);
            sr.setMessage("UserInformation register success");
            sr.setSuccess(true);
        }catch (Exception e){
            throw new UserInformationServiceException(e.getMessage());
        }

        return sr;
    }


    @Override
    public ServiceResult delete(UserInformation ui) {

        ServiceResult sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("UserInformation delete failed");
        sr.setSuccess(false);

        if(userInformationDao.findOne(ui.getId())!=null){
            userInformationDao.delete(ui.getId());
            sr.setMessage("UserInformation delete success");
            sr.setSuccess(true);
        }
        return null;
    }

    @Override
    public boolean isNickNameUsed(String nickName) {
        return (userInformationDao.findByNickName(nickName).size()>0)?true:false;
    }

    @Override
    public boolean isEmailBinded(String bindingEmail) {
        return (userInformationDao.findByBindingEmail(bindingEmail).size()>0)?true:false;
    }

    @Override
    public ServiceResult modify(UserInformation ui) {

        ServiceResult sr=new ServiceResult();
        sr.setMessage("modify failed");
        sr.setData(null);
        sr.setSuccess(false);

        try{
            ui=userInformationDao.save(ui);
            sr.setData(ui);
            sr.setMessage("modify success");
            sr.setSuccess(true);
        }catch (Exception e){

        }

        return sr;
    }

    @Override
    public ServiceResult findById(int id) {
        ServiceResult sr=new ServiceResult();
        sr.setMessage("not found");
        sr.setData(null);
        sr.setSuccess(false);
        try{
            UserInformation ui=userInformationDao.findOne(id);
            sr.setData(ui);
            sr.setMessage("find success");
            sr.setSuccess(true);
        }catch (Exception e){

        }
        return sr;
    }

    @Override
    public ServiceResult findByNickName(String name) {
        ServiceResult sr=new ServiceResult();
        sr.setMessage("not found");
        sr.setData(null);
        sr.setSuccess(false);
        try{
            List<UserInformation> uis=userInformationDao.findByNickName(name);
            sr.setData(uis);
            sr.setMessage("find success");
            sr.setSuccess(true);
        }catch (Exception e){

        }
        return sr;
    }
}
