package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.SetPasswordValidateDao;
import com.example.entity.ServiceResult;
import com.example.entity.SetPasswordValidate;
import com.example.serviceInterface.SetPasswordValidateService;

@Service
public class SetPasswordValidateServiceImpl implements SetPasswordValidateService {

	@Autowired
	SetPasswordValidateDao setPasswordValidateDao;
	
	@Override
	public ServiceResult<SetPasswordValidate> validate(int id, String validateCode) {
		// TODO Auto-generated method stub
		ServiceResult<SetPasswordValidate> sr = new ServiceResult<SetPasswordValidate>();
		sr.setData(null);
		sr.setMessage("failed");
		sr.setSuccess(false);
		
		SetPasswordValidate spv = setPasswordValidateDao.findOne(id);
		if(spv!=null&&validateCode.equals(spv.getValidateCode())){
			sr.setData(spv);
			sr.setMessage("validate success");
			sr.setSuccess(true);
			delete(id);
		}
		
		return sr;
	}

	@Override
	public ServiceResult<SetPasswordValidate> add(SetPasswordValidate spv){
		// TODO Auto-generated method stub
		ServiceResult<SetPasswordValidate> sr = new ServiceResult<SetPasswordValidate>();
		sr.setData(null);
		sr.setMessage("failed");
		sr.setSuccess(false);
		
		setPasswordValidateDao.save(spv);
		
		sr.setData(spv);
		sr.setMessage("success");
		sr.setSuccess(true);
		
		return sr;
	}

	//@Override
	private void delete(int id) {
		// TODO Auto-generated method stub
		SetPasswordValidate spv = setPasswordValidateDao.findOne(id);
		if(spv!=null){
			setPasswordValidateDao.delete(id);
		}
	}

}
