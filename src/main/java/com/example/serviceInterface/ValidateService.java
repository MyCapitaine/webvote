package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;

/**
 * Created by hasee on 2017/4/11.
 */
public interface ValidateService {
    public ServiceResult validate(String validator);
    public ServiceResult getValidator(UserRegister ur) throws ActiveValidateServiceException;
    public void deleteValidator(UserRegister ur);

}
