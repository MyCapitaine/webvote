package com.example.service;

import com.example.dao.InformationDao;
import com.example.dao.RegisterDao;
import com.example.entity.JsonResult;
import com.example.entity.UserInformation;
import com.example.entity.UserRegister;
import com.example.serviceInterface.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hasee on 2017/3/29.
 * 负责用户注册、登录、封禁
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;
    @Autowired
    private InformationDao informationDao;

    /**
     * ban 根据id解封账号
     * @param id 账号id
     *           视情况改变返回值
     */
    public void release(int id){
        UserRegister ur = registerDao.findOne(id);
        if(ur!=null){
            ur.setBanned(0);
            registerDao.save(ur);
        }
    }

    /**
     * ban 根据id封禁账号
     * @param id 账号id
     * todo 增加参数决定封禁时间
     *           视情况改变返回值
     */
    public void ban(int id){
        UserRegister ur = registerDao.findOne(id);
        if(ur!=null){
            ur.setBanned(1);
            registerDao.save(ur);
        }
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
     * login 登录功能的实现
     * @param login_name cookie或表单中保存的登录名
     * @param  md5pwd cookie或表单中保存的md5加密后的信息，加密前信息由登录名和登录密码拼接而成
     * @return 返回一个JsonResult
     * todo 检测账号是否被封禁
     */
    @Override
    public JsonResult login(String login_name, String md5pwd) {
        JsonResult js=new JsonResult();
        js.setData(null);
        js.setSuccess(false);
        js.setMessage("log in failed");

        List<UserRegister> urs=getURByName(login_name);
            //将实例的登录名和登录密码拼接后进行md5加密，将前者和cookie中的md5加密信息进行比较
        if(urs.size()>0){
            for(UserRegister ur :urs){
                //todo 检测账号是否被封禁
                if(!isBanned(ur)){
                    String info=ur.getLoginname()+ur.getLoginpwd();
                    String md5info=Code.MD5Encoder(info,"utf-8");
                    System.out.println("md5info:"+md5info);
                    if(md5info.equals(md5pwd)){
                        js.setData(ur);
                        js.setMessage("log in success");
                        js.setSuccess(true);
                        System.out.println("log in success");
                    }
                    else if(md5info.equals("MD5Encoder error")){
                        js.setMessage("Some errors occurred,wait a moment.");
                    }
                    else{
                        js.setMessage("name or pwd wrong.");
                    }
                }
                else {
                    js.setMessage("User is banned");
                }
            }
        }
        else{
            js.setMessage("User is not exist");
        }
//        try{
//            MessageDigest md5= MessageDigest.getInstance("MD5");
//            //根据登录名查找UserRegister实例
//            List<UserRegister> urs=getUR(login_name);
//            //将实例的登录名和登录密码拼接后进行md5加密，将前者和cookie中的md5加密信息进行比较
//            for(UserRegister ur :urs){
//                String info=ur.getLoginname()+ur.getLoginpwd();
//                String md5info=md5.digest(info.getBytes("utf-8")).toString();
//                System.out.println("md5info:"+md5info);
//                if(md5info.equals(md5pwd)){
//                    js.setData(ur);
//                    js.setMessage("log in success");
//                    js.setSuccess(true);
//                    System.out.println("log in success");
//                }
//            }
//        } catch (NoSuchAlgorithmException e) {
//            js.setMessage("md5 error,wait a moment.");
//        } catch (UnsupportedEncodingException e) {
//            js.setMessage("encode error,wait a moment.");
//        }
        return js;
    }

    /**
     * getURByName 根据登录名查找userregister表中的信息
     * @param login_name 登录名
     * @return 返回一个List<UserRegister>
     */
    @Override
    public List<UserRegister> getURByName(String login_name) {
        return registerDao.findByLoginname(login_name);
    }

    /**
     * register 注册功能的实现
     * @param ur 表单中的待注册用户信息
     * @return 返回一个JsonResult
     * todo 创建一个相应的UserInfo并插入到userinfo表中
     */
    @Override
    public JsonResult register(UserRegister ur) {
        JsonResult js=new JsonResult();
        js.setData(null);
        js.setMessage("register failed");
        js.setSuccess(false);
        //if(!isEmailBinded(ur.getBindemail())&&!isNameUsed(ur.getLoginname()))
        try{
            registerDao.save(ur);
            //todo 创建一个相应的 UserInfo 实例；插入到 userinfo 表中；应返回 UserInfo 实例
            UserInformation ui=new UserInformation(ur);
            informationDao.save(ui);
            js.setData(ur);
            js.setMessage("register success");
            js.setSuccess(true);
        }catch(RuntimeException re){
            new DaoTest() {
                private void showTraces(Throwable t) {
                    System.out.println(t.getMessage());
                    Throwable next = t.getCause();
                    if(next!=null)
                        showTraces(next);
                }
            }.showTraces(re);
            System.out.println("注册异常");
            js.setMessage("register error,wait a moment.");
            return js;
        }
        return js;
    }

    /**
     * register 检测登录名是否已经被注册
     * @param login_name 表单中的待注册用户的登录名
     * @return boolean
     */
    @Override
    public boolean isNameUsed(String login_name) {
        return (registerDao.findByLoginname(login_name).size()>0)?true:false;
    }

    /**
     * register 检测邮箱是否已经被绑定
     * @param bind_email 表单中的待注册用户的绑定邮箱
     * @return boolean
     */
    @Override
    public boolean isEmailBinded(String bind_email) {
        return (registerDao.findByBindemail(bind_email).size()>0)?true:false;
    }

    /**
     * 下面两个方法似乎没啥用，先放着
     */
    @Override
    public JsonResult findByLoginName(String login_name) {
        return new JsonResult(registerDao.findByLoginname(login_name));
    }

    @Override
    public JsonResult findByBindEmail(String bind_email) {
        return new JsonResult(registerDao.findByBindemail(bind_email));
    }

    private class DaoTest {
    }
}
