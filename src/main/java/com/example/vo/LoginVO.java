package com.example.vo;

/**
 * Created by hasee on 2017/4/6.
 */
public class LoginVO {

    private String login_name;
    private String login_pwd;
    private boolean remember;

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getLogin_pwd() {
        return login_pwd;
    }

    public void setLogin_pwd(String login_pwd) {
        this.login_pwd = login_pwd;
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
