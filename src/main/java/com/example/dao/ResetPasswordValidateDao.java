package com.example.dao;

import com.example.entity.ResetPasswordValidate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by hasee on 2017/4/14.
 */
public interface ResetPasswordValidateDao extends JpaRepository<ResetPasswordValidate, Integer> {
    List<ResetPasswordValidate> findByValidateCode(String validateCode);
}
