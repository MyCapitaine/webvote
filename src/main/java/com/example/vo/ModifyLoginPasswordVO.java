package com.example.vo;

/**
 * Created by hasee on 2017/4/13.
 */
public class ModifyLoginPasswordVO {
    private int id;
    private String oldLoginPassword;
    private String newLoginPassword;
    private String secondNewLoginPassword;

    public ModifyLoginPasswordVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldLoginPassword() {
        return oldLoginPassword;
    }

    public void setOldLoginPassword(String oldLoginPassword) {
        this.oldLoginPassword = oldLoginPassword;
    }

    public String getNewLoginPassword() {
        return newLoginPassword;
    }

    public void setNewLoginPassword(String newLoginPassword) {
        this.newLoginPassword = newLoginPassword;
    }

    public String getSecondNewLoginPassword() {
        return secondNewLoginPassword;
    }

    public void setSecondNewLoginPassword(String secondNewLoginPassword) {
        this.secondNewLoginPassword = secondNewLoginPassword;
    }
}
