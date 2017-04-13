package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserInformation;
import com.example.entity.UserRegister;
import com.example.exception.UserInformationServiceException;

/**
 * Created by hasee on 2017/4/13.
 */

public interface UserInformationService {
    public ServiceResult register(UserInformation ui) throws UserInformationServiceException;

    public ServiceResult delete(UserInformation ui);

    public boolean isNickNameUsed(String nickName);
    public boolean isEmailBinded(String bindingEmail);
    public ServiceResult modify(UserInformation ui);

    public ServiceResult findById(int id);
    public ServiceResult findByNickName(String name);

}
