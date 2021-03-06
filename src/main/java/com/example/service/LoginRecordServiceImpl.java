package com.example.service;

import com.example.dao.LoginRecordDao;
import com.example.entity.LoginRecord;
import com.example.entity.ServiceResult;
import com.example.serviceInterface.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by hasee on 2017/4/19.
 */
@Service
public class LoginRecordServiceImpl implements LoginRecordService{
    @Autowired
    LoginRecordDao loginRecordDao;
    @Override
    public ServiceResult add(LoginRecord loginRecord) {
        ServiceResult lrsr = new ServiceResult();
        lrsr.setData(null);
        lrsr.setMessage("Add login record failed");
        lrsr.setSuccess(false);
        loginRecord = loginRecordDao.save(loginRecord);
        lrsr.setData(loginRecord);
        lrsr.setMessage("Add login record success");
        lrsr.setSuccess(true);

        return lrsr;
    }

    @Override
    public ServiceResult find(int id,Pageable pageable) {
        ServiceResult sr = new ServiceResult();
        sr.setData(null);
        sr.setMessage("failed");
        sr.setSuccess(false);
        Page<LoginRecord> page = loginRecordDao.findByUserId(id,pageable);
        sr.setData(page);
        sr.setMessage("success");
        sr.setSuccess(true);
        return sr;
    }
}
