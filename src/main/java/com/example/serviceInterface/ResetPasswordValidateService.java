package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;

/**
 * Created by hasee on 2017/4/14.
 */
public interface ResetPasswordValidateService {
    public ServiceResult validate(String validatCode);
    public ServiceResult add(UserRegister ur) throws ActiveValidateServiceException;
    public void delete(int id);
}
