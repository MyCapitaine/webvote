package com.example.dao;

/**
 * Created by hasee on 2017/3/6.
 */

import com.example.entity.User;
import com.example.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Transactional
public interface RegisterDao extends JpaRepository<UserRegister, Integer> {
    //public User findOne(Integer id);
    public List<UserRegister> findByLoginname(String loginname);
    public List<UserRegister> findByBindemail(String bindemail);
}
