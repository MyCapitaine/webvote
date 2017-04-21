package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "set_password_validate")
public class SetPasswordValidate {
	@Id
    private int id;

    @Column(name="validator",nullable = false)
    private String validateCode;

    public SetPasswordValidate() {
    }

    public SetPasswordValidate(ResetPasswordValidate rpv) {
        this.id=rpv.getId();
        this.validateCode=rpv.getValidateCode();
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


}
