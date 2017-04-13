package com.example.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hasee on 2017/3/22.
 */
@Entity
@Table(name = "user_register")
public class UserRegister {

    @Column(name = "banned",nullable = false,length = 1)
    private int banned=0;//是否被封禁;1表示被封禁

    @Column(name = "login_name",nullable = false,unique = true)
    private String loginName;//登录名

    @Column(name = "login_pwd",nullable = false)
    private String loginPassword;//登录密码

    @Column(name = "binding_mail",nullable = false,unique = true)
    private String bindingEmail;//绑定邮箱

    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTime; // 注册时间，格式：yyyy-MM-dd HH:mm:ss

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;//用户id，注册后自动生成

    public UserRegister() { }

    public String getLoginPassword() {

        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {

        this.loginPassword = loginPassword;
    }

    public String getBindingEmail() {

        return bindingEmail;
    }

    public void setBindingEmail(String bindingEmail) {

        this.bindingEmail = bindingEmail;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getLoginName() {

        return loginName;
    }

    public void setLogin_name(String login_name) {

        this.loginName = login_name;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }


    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }


}
