package com.example.serviceInterface;

import com.example.entity.ServiceResult;

/**
 * Created by hasee on 2017/5/8.
 */
public interface IpService {
    boolean isBanned(String ip);
    ServiceResult ban(String ip);
    void release(int id);
}
