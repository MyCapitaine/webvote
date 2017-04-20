package com.example.dao;

import com.example.entity.LoginRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by hasee on 2017/4/19.
 */
public interface LoginRecordDao extends JpaRepository<LoginRecord, Integer> {
    Page findByUserId(int id, Pageable page);


    @Query(" from LoginRecord lr where lr.id=:id")
    Page findCommentById(int id, Pageable page);
}
