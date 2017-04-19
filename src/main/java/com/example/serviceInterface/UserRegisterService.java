package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.UserRegisterServiceException;
import com.example.vo.ModifyLoginPasswordVO;

/**
 * Created by hasee on 2017/3/29.
 */
public interface UserRegisterService {

    ServiceResult<UserRegister> register(UserRegister ur) throws UserRegisterServiceException;
    boolean isLoginNameUsed(String loginName);
    boolean isEmailBinding(String bindingEmail);

    ServiceResult<UserRegister> delete(UserRegister ur);

    ServiceResult<UserRegister> modify(UserRegister ur);
    void release(int id);
    void ban(int id);
    ServiceResult<UserRegister> modifyLoginPassword(ModifyLoginPasswordVO form);
    ServiceResult<UserRegister> modifyBindingEmail(UserRegister ur);

    boolean isBanned(UserRegister ur);
    UserRegister getURByName(String login_name);
    ServiceResult<UserRegister> login(String login_name, String md5);

    ServiceResult<UserRegister> findByLoginName(String login_name);
    ServiceResult<UserRegister> findByBindEmail(String bind_email);
    ServiceResult<UserRegister> findById(int id);
}
