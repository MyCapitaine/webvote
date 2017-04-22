package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import com.example.entity.UserRegister;
import com.example.exception.ActiveValidateServiceException;

/**
 * Created by hasee on 2017/4/11.
 */
public interface BindingEmailValidateService {
    ServiceResult validate(int id, String validator);
    ServiceResult add(int id, String email) throws ActiveValidateServiceException;
    void delete(int id);
}
