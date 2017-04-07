package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.JsonResult;
import com.example.entity.User;
import com.example.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by hasee on 2017/3/7.
 */
//@Transactional
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    public JsonResult<Object[]> findByName(String name) {

        List<User> userList = userDao.findByName(name);
        if (userList != null && userList.size()!=0) {
            return  new JsonResult(userList);
        }
        return null;
    }

    @Override
    public JsonResult<Object[]> findById(int id) {
        User user=userDao.findOne(id);
        if(user!=null)
            return new JsonResult(user);
        return null;
    }

    @Override
    public JsonResult<Object[]> initPage(int page_index,int page_size) {
        Pageable pageable =new PageRequest(page_index, page_size);
        Page<User> datas = userDao.findAll(pageable);
        if(datas!=null){
            return new JsonResult(datas);
        }
        return null;
    }

    @Override
    public JsonResult<Object[]> delete(int[] ids ,int page_index,int page_size) {
        for(int id:ids){
            userDao.delete(id);
        }
        Pageable pageable =new PageRequest(page_index, page_size);
        Page<User> datas = userDao.findAll(pageable);
        JsonResult<Object[]> result=new JsonResult(datas);
        result.setSuccess(true);
        return result;
    }

}
