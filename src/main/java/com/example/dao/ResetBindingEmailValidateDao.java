package com.example.dao;
import com.example.entity.ResetBindingEmailValidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by hasee on 2017/5/9.
 */
public interface ResetBindingEmailValidateDao extends JpaRepository<ResetBindingEmailValidate,Integer>{
    @Query("from ResetBindingEmailValidate where id=?1 and validator=?2" )
    ResetBindingEmailValidate validate(int id, String validateCode);
}
