package com.example.dao;

import com.example.entity.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hasee on 2017/4/19.
 */
public interface LoginRecordDao extends JpaRepository<LoginRecord, Integer> {
}
