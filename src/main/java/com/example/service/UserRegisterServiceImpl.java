package com.example.service;

import com.example.dao.UserRegisterDao;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.UserRegisterServiceException;
import com.example.serviceInterface.UserRegisterService;
import com.example.util.Code;
import com.example.vo.ModifyLoginPasswordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by hasee on 2017/3/29.
 * 负责用户注册、登录、封禁
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserRegisterDao userRegisterDao;

    /**
     * register 检测登录名是否已经被注册
     * @param login_name 表单中的待注册用户的登录名
     * @return boolean
     */
    @Override
    public boolean isLoginNameUsed(String login_name) {
        return (userRegisterDao.findByLoginName(login_name)!=null)?true:false;
    }

    /**
     * register 检测邮箱是否已经被绑定
     * @param bind_email 表单中的待注册用户的绑定邮箱
     * @return boolean
     */
    @Override
    public boolean isEmailBinding(String bind_email) {
        return (userRegisterDao.findByBindingEmail(bind_email)!=null)?true:false;
    }

    /**
     * register 注册功能的实现
     * @param ur 表单中的待注册用户信息
     * @return 返回一个JsonResult
     * todo 发送验证邮件
     */
    @Override
    public ServiceResult<UserRegister> register(UserRegister ur) throws UserRegisterServiceException {
        ServiceResult<UserRegister> sr=new ServiceResult<UserRegister>();
        sr.setData(null);
        sr.setMessage("UserRegister register failed");
        sr.setSuccess(false);

        if(isLoginNameUsed(ur.getLoginName())){//userRegisterDao.findByLoginName(ur.getLoginName()).size()!=0
            sr.setMessage("Login name has been used.");
            throw new UserRegisterServiceException("Login name has been used.");
        }
        else if(isEmailBinding(ur.getBindingEmail())){//userRegisterDao.findByBindingEmail(ur.getBindingEmail()).size()!=0
            sr.setMessage("Email has been binding");
            throw new UserRegisterServiceException("Email has been binding");
        }
        else{
            try{
                //写入注册表
                ur = userRegisterDao.save(ur);
                sr.setData(ur);
                sr.setMessage("UserRegister register success");
                sr.setSuccess(true);
            }catch(Exception e){
                throw new UserRegisterServiceException(e.getMessage());
                //return sr;
            }
//            catch(RuntimeException re){
//
//                new DaoTest() {
//                    private void showTraces(Throwable t) {
//                        System.out.println(t.getMessage());
//                        Throwable next = t.getCause();
//                        if(next!=null)
//                            showTraces(next);
//                    }
//                }.showTraces(re);
//                if(registerDao.findOne(ur.getId())!=null){
//                    registerDao.delete(ur.getId());
//                }
//                if(ui!=null&&informationDao.findOne(ui.getId())!=null){
//                    informationDao.delete(ui.getId());
//                }
//                System.out.println("注册异常");
//                js.setMessage("register error,wait a moment.");
//                return js;
//            }
        }
        return sr;
    }

    @Override
    public ServiceResult<UserRegister> delete(UserRegister ur) {

        ServiceResult<UserRegister> sr = new ServiceResult<UserRegister>();
        sr.setData(null);
        sr.setMessage("UserRegister delete failed");
        sr.setSuccess(false);

        if(userRegisterDao.findOne(ur.getId())!=null){
            userRegisterDao.delete(ur.getId());
            sr.setMessage("UserRegister delete success");
            sr.setSuccess(true);
        }

        return sr;
    }
    
    @Override
	public ServiceResult<UserRegister> modify(UserRegister ur) {
		// TODO Auto-generated method stub
		ServiceResult<UserRegister> sr = new ServiceResult<UserRegister>();
		sr.setData(null);
		sr.setMessage("modify failed");
		sr.setSuccess(false);
		
		userRegisterDao.save(ur);
		
		sr.setData(ur);
		sr.setMessage("modify success");
		sr.setSuccess(true);
		
		return null;
	}

    /**
     * ban 根据id解封账号
     * @param id 账号id
     *           视情况改变返回值
     */
    public void release(int id){
        UserRegister ur = userRegisterDao.findOne(id);
        if(ur!=null){
            ur.setBanned(0);
            userRegisterDao.save(ur);
        }
    }

    /**
     * ban 根据id封禁账号
     * @param id 账号id
     * todo 增加参数int day决定封禁时间(单位天)，增加参数String reason封禁理由
     *           视情况改变返回值
     */
    public void ban(int id){
        UserRegister ur = userRegisterDao.findOne(id);
        if(ur!=null){
            ur.setBanned(1);
            userRegisterDao.save(ur);
        }
    }

    @Override
    public ServiceResult<UserRegister> modifyLoginPassword(ModifyLoginPasswordVO form) {

        ServiceResult<UserRegister> sr = new ServiceResult<UserRegister>();
        sr.setData(null);
        sr.setMessage("modify password failed");
        sr.setSuccess(false);

        UserRegister ur = userRegisterDao.findOne(form.getId());
        if(ur!=null){
            if(form.getOldLoginPassword().equals(ur.getLoginPassword())){
                if(form.getNewLoginPassword().equals(form.getSecondNewLoginPassword())){
                    ur.setLoginPassword(form.getNewLoginPassword());
                    userRegisterDao.save(ur);

                    sr.setData(ur);
                    sr.setMessage("modify password success");
                    sr.setSuccess(true);
                }
                else{
                    sr.setMessage("New passwords are not equal");
                }
            }
            else{
                sr.setMessage("Old password is wrong");
            }
        }
        else {
            sr.setMessage("User is not exist");
        }
//        if(form.getOldLoginPassword().equals(ur.getLoginPassword())
//                &&form.getNewLoginPassword().equals(form.getSecondNewLoginPassword())){
//            ur.setLoginPassword(form.getNewLoginPassword());
//            userRegisterDao.save(ur);
//
//            sr.setMessage("modify password success");
//            sr.setSuccess(true);
//        }
        return sr;
    }

    @Override
    public ServiceResult<UserRegister> modifyBindingEmail(UserRegister ur) {

        return null;
    }

    /**
     * isBanned 检测账号是否被封禁
     * @param ur 一个UserRegister实例
     * @return boolean
     */
    public boolean isBanned(UserRegister ur){
        return ur.getBanned()==0?false:true;
        //return true;
    }

    /**
     * getURByName 根据登录名查找userregister表中的信息
     * @param login_name 登录名
     * @return 返回一个List<UserRegister>
     */
    @Override
    public UserRegister getURByName(String login_name) {
        return userRegisterDao.findByLoginName(login_name);
    }

    /**
     * getURByEmail 根据登录名查找userregister表中的信息
     * @param bindingEmail 登录名
     * @return 返回一个List<UserRegister>
     */
    @Override
    public UserRegister getURByEmail(String bindingEmail) {
        return userRegisterDao.findByBindingEmail(bindingEmail);
    }
    /**
     * login 登录功能的实现
     * @param login_name cookie或表单中保存的登录名或者绑定邮箱
     * @param  md5pwd cookie或表单中保存的md5加密后的信息，加密前信息由登录名和登录密码拼接而成
     * @return 返回一个JsonResult
     * todo 检测账号是否被封禁
     */
    @Override
    public ServiceResult<UserRegister> login(String login_name, String md5pwd) {
        ServiceResult<UserRegister> sr=new ServiceResult<UserRegister>();
        sr.setData(null);
        sr.setSuccess(false);
        sr.setMessage("log in failed");

        UserRegister ur=getURByName(login_name);
        if(ur==null){
            ur=getURByEmail(login_name);
        }
        //将实例的登录名和登录密码拼接后进行md5加密，将前者和cookie中的md5加密信息进行比较
        if(ur!=null){
                //todo 检测账号是否被封禁
                if(!isBanned(ur)){
                    String info=ur.getLoginName()+ur.getLoginPassword();
                    String info2 = ur.getBindingEmail()+ur.getLoginPassword();
                    String md5info= null;
                    String md5info2= null;
                    try {
                        md5info = Code.MD5Encoder(info,"utf-8");
                        md5info2 = Code.MD5Encoder(info2,"utf-8");
                    } catch (Exception e) {
                        sr.setMessage("Some errors occurred,wait a moment.");
                        return sr;
                    }
                    if(md5info.equals(md5pwd)
                            ||md5info2.equals(md5pwd)){
                        Date loginTime = new Date();
                        ur.setLastLoginTime(loginTime);
                        userRegisterDao.save(ur);
                        sr.setData(ur);
                        sr.setMessage("log in success");
                        sr.setSuccess(true);
                    }
                    else{
                        sr.setMessage("name or pwd wrong.");
                    }
                }
                else {
                    sr.setMessage("User is banned");
                }
        }
        else{
            sr.setMessage("User is not exist");
        }

        return sr;
    }



    /**
     * 下面两个方法似乎没啥用，先放着
     */
    @Override
    public ServiceResult<UserRegister> findByLoginName(String login_name) {
        return new ServiceResult<UserRegister>(userRegisterDao.findByLoginName(login_name));
    }

    @Override
    public ServiceResult<UserRegister> findByBindEmail(String bind_email) {
        return new ServiceResult<UserRegister>(userRegisterDao.findByBindingEmail(bind_email));
    }
	@Override
	public ServiceResult<UserRegister> findById(int id) {
		// TODO Auto-generated method stub
		return new ServiceResult<UserRegister>(userRegisterDao.findOne(id));
	}

	
}
