package com.example.service;

import com.example.dao.RegisterDao;
import com.example.entity.JsonResult;
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
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;
    /**
     * @param login_name cookie中保存的登录名
     * @param  md5pwd cookie中保存的md5加密后的信息，加密前信息由登录名和登录密码拼接而成
     * @return 返回一个JsonResult
     */
    @Override
    public JsonResult login(String login_name, String md5pwd) {
        JsonResult js=new JsonResult();
        js.setData(null);
        js.setSuccess(false);
        js.setMessage("log in failed");

        List<UserRegister> urs=getUR(login_name);
            //将实例的登录名和登录密码拼接后进行md5加密，将前者和cookie中的md5加密信息进行比较
        for(UserRegister ur :urs){
            String info=ur.getLoginname()+ur.getLoginpwd();
            String md5info=Code.MD5Encoder(info,"utf-8");
            System.out.println("md5info:"+md5info);
            if(md5info.equals(md5pwd)){
                js.setData(ur);
                js.setMessage("log in success");
                js.setSuccess(true);
                System.out.println("log in success");
            }
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

    @Override
    public List<UserRegister> getUR(String login_name) {
        return registerDao.findByLoginname(login_name);
    }

    @Override
    public JsonResult register(UserRegister ur) {
        //if(!isEmailBinded(ur.getBindemail())&&!isNameUsed(ur.getLoginname()))
        try{
            registerDao.save(ur);
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
            return null;
        }

        return null;
    }

    @Override
    public boolean isNameUsed(String login_name) {
        return (registerDao.findByLoginname(login_name).size()>0)?true:false;
    }

    @Override
    public boolean isEmailBinded(String bind_email) {
        return (registerDao.findByBindemail(bind_email).size()>0)?true:false;
    }

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
