package com.example.serviceInterface;

import com.example.entity.ResetBindingEmailValidate;
import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;

/**
 * Created by hasee on 2017/5/9.
 */
public interface ResetBindingEmailValidateService {
    ServiceResult<ResetBindingEmailValidate> validate(int id, String validateCode);
    ServiceResult<ResetBindingEmailValidate> add(int id,String email) throws ActiveValidateServiceException;
}
