package com.example.service;

import com.example.dao.UserInformationDao;
import com.example.entity.ServiceResult;
import com.example.entity.UserInformation;
import com.example.exception.UserInformationServiceException;
import com.example.serviceInterface.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


//    @Override
//    public ServiceResult delete(UserRegister ur) {
//
//        ServiceResult sr = new ServiceResult();
//        sr.setData(null);
//        sr.setMessage("UserInformation delete failed");
//        sr.setSuccess(false);
//
//        if(userInformationDao.findOne(ur.getId())!=null){
//            userInformationDao.delete(ur.getId());
//            sr.setMessage("UserInformation delete success");
//            sr.setSuccess(true);
//        }
//        return null;
//    }

    //注册时检测昵称是否被占用
    @Override
    public boolean isNickNameUsed(String nickName) {
        return userInformationDao.findByNickName(nickName)!=null?true:false;
    }

    //在个人中心修改信息时检测昵称是否被占用
    @Override
    public boolean isNickNameUsed(int id,String nickName) {
        UserInformation ui=userInformationDao.findByNickName(nickName);
        if(ui==null){
            return false;
        }
        else if(ui!=null&&ui.getId()==id){
            return false;
        }
        else{
            return true;
        }
    }

//    @Override
//    public boolean isEmailBinding(String bindingEmail) {
//        return (userInformationDao.findByBindingEmail(bindingEmail)!=null)?true:false;
//    }

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
        	e.printStackTrace();
        }

        return sr;
    }

    @Override
    public ServiceResult findAll(Pageable page) {
        ServiceResult sr = new ServiceResult();
        Page result = userInformationDao.findAll(page);
        sr.setData(result);
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
            UserInformation ui=userInformationDao.findByNickName(name);
            sr.setData(ui);
            sr.setMessage("find success");
            sr.setSuccess(true);
        }catch (Exception e){

        }
        return sr;
    }
}
