package com.example.serviceInterface;

import com.example.entity.ServiceResult;
import org.springframework.data.domain.Pageable;

/**
 * Created by hasee on 2017/5/8.
 */
public interface IpService {

    ServiceResult findAll(Pageable page);
    boolean isBanned(int id);
    boolean isBanned(String ip);
    ServiceResult ban(String ip);
    void release(int id);
}
