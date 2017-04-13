package com.example.vo;

/**
 * Created by hasee on 2017/4/6.
 */
public class LoginVO {

    private String loginName;
    private String loginPassword;
    private boolean remember;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean getRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public LoginVO() {
    }
}
