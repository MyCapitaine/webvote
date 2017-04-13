package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.UserRegisterServiceException;
import com.example.vo.ModifyLoginPasswordVO;

import java.util.List;

/**
 * Created by hasee on 2017/3/29.
 */
public interface UserRegisterService {

    public ServiceResult register(UserRegister ur) throws UserRegisterServiceException;
    public boolean isLoginNameUsed(String loginName);
    public boolean isEmailBinding(String bindingEmail);

    public ServiceResult delete(UserRegister ur);

    public void release(int id);
    public void ban(int id);
    public ServiceResult modifyLoginPassword(ModifyLoginPasswordVO form);
    public ServiceResult modifyBindingEmail(UserRegister ur);

    public boolean isBanned(UserRegister ur);
    public List<UserRegister> getURByName(String login_name);
    public ServiceResult login(String login_name, String md5);

    public ServiceResult findByLoginName(String login_name);
    public ServiceResult findByBindEmail(String bind_email);
}
