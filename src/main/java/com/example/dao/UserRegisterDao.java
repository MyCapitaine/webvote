package com.example.dao;

/**
 * Created by hasee on 2017/3/6.
 */

import com.example.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

@Transactional
public interface UserRegisterDao extends JpaRepository<UserRegister, Integer> {
    //public User findOne(Integer id);
    UserRegister findByLoginName(String loginname);
    UserRegister findByBindingEmail(String bindemail);
}
