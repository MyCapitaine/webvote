package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hasee on 2017/5/9.
 */
@Entity
@Table(name = "reset_binding_emial_validate")
public class ResetBindingEmailValidate {
    @Id
    private int id;//用户id

    @Column(name="validator",nullable = false)
    private String validateCode;

    @Column(name="deadline",nullable = false)
    private Date deadline;//默认有效期三分钟

    public ResetBindingEmailValidate() {
    }

    public ResetBindingEmailValidate(int id) {
        this.id=id;
        long time=new Date().getTime()+1000*60*3;
        this.deadline=new Date(time);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
