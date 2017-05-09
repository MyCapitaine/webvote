package com.example.service;

import com.example.dao.IpDao;
import com.example.entity.Ip;
import com.example.entity.ServiceResult;
import com.example.serviceInterface.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by hasee on 2017/5/8.
 */
@Service
public class IpServiceImpl implements IpService{
    @Autowired
    IpDao ipDao;

    @Override
    public ServiceResult findAll(Pageable page) {
        return new ServiceResult(ipDao.findAll(page));
    }

    @Override
    public boolean isBanned(int id) {
        Ip i = ipDao.findOne(id);
        if(i!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isBanned(String ip) {
        Ip i = ipDao.findByIp(ip);
        if(i!=null){
            return true;
        }
        return false;
    }

    @Override
    public ServiceResult ban(String ip) {
        Ip i = new Ip(ip);
        i=ipDao.save(i);
        return new ServiceResult(i);
    }

    @Override
    public void release(int id) {
        ipDao.delete(id);
    }
}
