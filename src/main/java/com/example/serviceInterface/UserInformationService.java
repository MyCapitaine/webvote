package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserInformation;
import com.example.exception.UserInformationServiceException;
import org.springframework.data.domain.Pageable;


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
    void release(int id);
    void ban(int id);

    ServiceResult findAll(Pageable page);
    ServiceResult findById(int id);
    ServiceResult findByNickName(String name);

}
