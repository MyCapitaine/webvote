package com.example.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hasee on 2017/3/22.
 */
@Entity
@Table(name = "userregister")
public class UserRegister {
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

    @Column(name = "loginname", length = 45)
    private String loginname;
    @Column(name = "loginpwd", length = 45)
    private String loginpwd;
    @Column(name = "bindemail", length = 45)
    private String bindemail;
    @Temporal(TemporalType.TIMESTAMP)
    private Date registertime; // 日期类型，格式：yyyy-MM-dd HH:mm:ss
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;



}
