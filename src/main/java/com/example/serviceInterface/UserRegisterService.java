package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.UserRegisterServiceException;
import com.example.vo.ModifyLoginPasswordVO;

/**
 * Created by hasee on 2017/3/29.
 */
public interface UserRegisterService {

    public ServiceResult<UserRegister> register(UserRegister ur) throws UserRegisterServiceException;
    public boolean isLoginNameUsed(String loginName);
    public boolean isEmailBinding(String bindingEmail);

    public ServiceResult<UserRegister> delete(UserRegister ur);

    public ServiceResult<UserRegister> modify(UserRegister ur);
    public void release(int id);
    public void ban(int id);
    public ServiceResult<UserRegister> modifyLoginPassword(ModifyLoginPasswordVO form);
    public ServiceResult<UserRegister> modifyBindingEmail(UserRegister ur);

    public boolean isBanned(UserRegister ur);
    public UserRegister getURByName(String login_name);
    public ServiceResult<UserRegister> login(String login_name, String md5);

    public ServiceResult<UserRegister> findByLoginName(String login_name);
    public ServiceResult<UserRegister> findByBindEmail(String bind_email);
    public ServiceResult<UserRegister> findById(int id);
}
