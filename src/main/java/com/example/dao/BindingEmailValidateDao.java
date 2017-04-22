package com.example.dao;

import com.example.entity.BindingEmailValidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/4/11.
 */
public interface BindingEmailValidateDao extends JpaRepository<BindingEmailValidate, Integer> {

    BindingEmailValidate findById(int id);

    @Query("from BindingEmailValidate where id=?1 and validator=?2" )
    BindingEmailValidate validate(int id,String validateCode);

}
