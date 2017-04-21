package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;

/**
 * Created by hasee on 2017/4/11.
 */
public interface ActiveValidateService {
    ServiceResult validate(String validator);
    ServiceResult add(UserRegister ur) throws ActiveValidateServiceException;
    void delete(UserRegister ur);
}
