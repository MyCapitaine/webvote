package com.example.vo;

import com.example.entity.UserInformation;
import com.example.entity.UserRegister;
import com.example.util.Encrypt;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by hasee on 2017/5/3.
 */
public class UserInformationVO {
    private int id;//用户id

    private Date birthday;//出生日期

    private String nickName;//用户昵称

    private String portrait;//头像url路径，默认：

    private String sign;//个性签名，默认：这个人很懒，什么也没留下

    private String sex;//个性签名，默认：这个人很懒，什么也没留下

    private int level;//个人等级，初始为1

    private int experience;//经验值，初始为0

    private String bindingEmail;//绑定邮箱

    private Date registerTime;//注册时间，格式：yyyy-MM-dd HH:mm:ss

    public UserInformationVO() {

    }

    public UserInformationVO(UserInformation ui) {
        this.id=ui.getId();
        this.bindingEmail = Encrypt.encryptEmailPrefix(ui.getBindingEmail());
        this.registerTime=ui.getRegisterTime();
        this.sign=ui.getSign();
        this.level=ui.getLevel();
        this.experience=ui.getExperience();
        this.portrait=ui.getPortrait();
        this.birthday=ui.getBirthday();
        this.sex=ui.getSex();
        this.nickName=ui.getNickName();
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
