package com.example.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hasee on 2017/3/22.
 */
@Entity
@Table(name = "userregister")
public class UserRegister {

    @Column(name = "banned")
    private int banned;//是否被封禁
    @Column(name = "loginname", length = 45)
    private String loginname;//登录名
    @Column(name = "loginpwd", length = 45)
    private String loginpwd;//登录密码
    @Column(name = "bindemail", length = 45)
    private String bindemail;//绑定邮箱
    @Temporal(TemporalType.TIMESTAMP)
    private Date registertime; // 注册时间，格式：yyyy-MM-dd HH:mm:ss
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;//用户id，注册后自动生成

    public UserRegister() { }

    public String getLoginpwd() {

        return loginpwd;
    }

    public void setLoginpwd(String loginpwd) {

        this.loginpwd = loginpwd;
    }

    public String getBindemail() {

        return bindemail;
    }

    public void setBindemail(String bindemail) {

        this.bindemail = bindemail;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getLoginname() {

        return loginname;
    }

    public void setLogin_name(String login_name) {

        this.loginname = login_name;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }


    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }


}
