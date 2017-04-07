package com.example.dao;

import com.example.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hasee on 2017/4/7.
 */
public interface InformationDao extends JpaRepository<UserInformation, Integer> {

}
