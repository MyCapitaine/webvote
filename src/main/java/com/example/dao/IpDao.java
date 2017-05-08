package com.example.dao;

/**
 * Created by hasee on 2017/5/8.
 */

import com.example.entity.Ip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  IpDao extends JpaRepository<Ip,Integer>{
    Ip findByIp(String ip);
    Page findAll(Pageable page);
}
