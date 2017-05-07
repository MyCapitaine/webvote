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
    //此语句同时返回ui和ur
    @Query("  from UserInformation ui, UserRegister ur  where ur.banned = 0 and ur.authority = 1 and ui.id = ur.id")
    //此语句返回三个字段数据
    //@Query("  select ui.id,ui.nickName,ui.latestIP from UserInformation ui, UserRegister ur  where ur.banned = 0 and ur.authority = 1 and ui.id = ur.id")
    Page findAll(Pageable page);
}
