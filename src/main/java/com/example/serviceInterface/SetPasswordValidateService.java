package com.example.serviceInterface;


import com.example.entity.ServiceResult;
import com.example.entity.SetPasswordValidate;

public interface SetPasswordValidateService {
	public ServiceResult<SetPasswordValidate> validate(int id,String validateCode);
    public ServiceResult<SetPasswordValidate> add(SetPasswordValidate spv);
    public void delete(int id);

}
