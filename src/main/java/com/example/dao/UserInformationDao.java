package com.example.dao;

import com.example.entity.UserInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hasee on 2017/4/7.
 */
public interface UserInformationDao extends JpaRepository<UserInformation, Integer> {

    UserInformation findByNickName(String nickName);
    UserInformation findByBindingEmail(String bindingEmail);
    @Query(" from UserInformation ui where ui.banned = 0 and ui.authority = 1")
    Page findAll(Pageable page);
}
