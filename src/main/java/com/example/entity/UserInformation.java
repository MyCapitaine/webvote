package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hasee on 2017/4/7.
 */
@Entity
@Table(name = "user_information")
public class UserInformation {

    public static String nick="用户";//用户昵称前缀

    @Id
    private int id;//用户id

    @Column(name="nick_name",nullable = false,unique = true)
    private String nickName;//用户昵称

    @Column(name="portrait",nullable = false)
    private String portrait;//头像url路径，默认：

    @Column(name="sign",nullable = false)
    private String sign;//个性签名，默认：这个人很懒，什么也没留下

    @Column(name="level",nullable = false)
    private int level;//个人等级，初始为1

    @Column(name="experience",nullable = false)
    private int experience;//经验值，初始为0

    @Column(name="binding_email",nullable = false,unique = true)
    private String bindingEmail;//绑定邮箱

    @Column(name="register_time",nullable = false)
    private Date registerTime;//注册时间，格式：yyyy-MM-dd HH:mm:ss

    public UserInformation(UserRegister ur) {
        this.id=ur.getId();
        this.nickName=nick+id;
        this.bindingEmail =ur.getBindingEmail();
        this.registerTime=ur.getRegisterTime();
        this.sign="这个人很懒，什么也没留下";
        this.level=1;
        this.experience=0;
        this.portrait="";
    }
    public UserInformation() {

    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBindingEmail() {
        return bindingEmail;
    }

    public void setBindingEmail(String bindingEmail) {
        this.bindingEmail = bindingEmail;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
