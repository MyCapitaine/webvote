package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserInformation;
import com.example.entity.UserRegister;
import com.example.exception.UserInformationServiceException;

/**
 * Created by hasee on 2017/4/13.
 */

public interface UserInformationService {
    ServiceResult register(UserInformation ui) throws UserInformationServiceException;

    //ServiceResult delete(UserRegister ur);

    boolean isNickNameUsed(String nickName);
    boolean isNickNameUsed(int id,String nickName);
    //boolean isEmailBinding(String bindingEmail);
    ServiceResult modify(UserInformation ui);

    ServiceResult findById(int id);
    ServiceResult findByNickName(String name);

}
