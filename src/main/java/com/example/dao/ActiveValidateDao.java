package com.example.dao;

import com.example.entity.ActiveValidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hasee on 2017/4/11.
 */
public interface ActiveValidateDao extends JpaRepository<ActiveValidate, Integer> {

    List<ActiveValidate> findByValidator(String validate);
}
