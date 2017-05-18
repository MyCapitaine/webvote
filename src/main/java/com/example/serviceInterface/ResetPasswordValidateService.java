package com.example.serviceInterface;

import com.example.entity.ResetPasswordValidate;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;

/**
 * Created by hasee on 2017/4/14.
 */
public interface ResetPasswordValidateService {
    ServiceResult<ResetPasswordValidate> validate(int id, String validateCode);
    ServiceResult<ResetPasswordValidate> add(UserRegister ur) throws ActiveValidateServiceException;
    //void delete(int id);
}
