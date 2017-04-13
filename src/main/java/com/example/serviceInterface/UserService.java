package com.example.serviceInterface;

import com.example.entity.JsonResult;

/**
 * Created by hasee on 2017/3/7.
 */
public interface UserService {
    public JsonResult<Object[]> findByName(String name);
    public JsonResult<Object[]> findById(int id);
    public JsonResult<Object[]> initPage(int page_index,int page_size);
    public JsonResult<Object[]> delete(int[] id,int page_index,int page_size);
}
