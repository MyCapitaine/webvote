package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Table;


/**
 * Created by hasee on 2017/4/11.
 */
@Entity
@Table(name = "active_validate")
public class BindingEmailValidate {
    @Id
    private int id;

    @Column(name = "binding_mail",nullable = false)
    private String bindingEmail;//绑定邮箱

    @Column(name = "validator",nullable = false)
    private String validator;//验证码

    @Column(name = "deadline",nullable = false)
    private Date deadline;//默认有效期三分钟

    public BindingEmailValidate() {
    }

    public BindingEmailValidate(UserRegister ur) {
        this.id = ur.getId();
        long time = ur.getRegisterTime().getTime() + 1000 * 60 * 3;
        this.deadline = new Date(time);
        this.bindingEmail = ur.getBindingEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    public String getBindingEmail() {
        return bindingEmail;
    }

    public void setBindingEmail(String bindingEmail) {
        this.bindingEmail = bindingEmail;
    }

}
