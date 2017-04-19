package com.example.dao;

import com.example.entity.LoginRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by hasee on 2017/4/19.
 */
public interface LoginRecordDao extends JpaRepository<LoginRecord, Integer> {
    Page findByUserId(int id, Pageable page);
}
