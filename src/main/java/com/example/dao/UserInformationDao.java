package com.example.dao;

import com.example.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hasee on 2017/4/7.
 */
public interface UserInformationDao extends JpaRepository<UserInformation, Integer> {

    UserInformation findByNickName(String nickName);
    UserInformation findByBindingEmail(String bindingEmail);
}
