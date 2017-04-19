package com.example.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hasee on 2017/4/19.
 */
@Entity
@Table(name="login_record")
public class LoginRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id",nullable = false)
    private int userId;

    @Column(name = "ip",nullable = false)
    private String ip;

    @Column(name = "login_time",nullable = false)
    private Date loginTime;

    public LoginRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
