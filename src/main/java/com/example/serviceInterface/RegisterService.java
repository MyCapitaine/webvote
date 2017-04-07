package com.example.serviceInterface;

import com.example.entity.JsonResult;
import com.example.entity.UserRegister;

import java.util.List;

/**
 * Created by hasee on 2017/3/29.
 */
public interface RegisterService {
    public void release(int id);
    public void ban(int id);
    public boolean isBanned(UserRegister ur);
    public JsonResult login(String login_name,String md5);
    public List<UserRegister> getURByName(String login_name);
    public JsonResult register(UserRegister ur);
    public boolean isNameUsed(String login_name);
    public boolean isEmailBinded(String bind_email);
    public JsonResult findByLoginName(String login_name);
    public JsonResult findByBindEmail(String bind_email);
}
