package com.example.serviceInterface;


import com.example.entity.ServiceResult;
import com.example.entity.SetPasswordValidate;

public interface SetPasswordValidateService {
	ServiceResult<SetPasswordValidate> validate(int id,String validateCode);
    ServiceResult<SetPasswordValidate> add(SetPasswordValidate spv);
    //void delete(int id);

}
