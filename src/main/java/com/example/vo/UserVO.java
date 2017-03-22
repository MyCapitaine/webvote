package com.example.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hasee on 2017/3/15.
 */
public class UserVO {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public float getFloatprice() {
        return floatprice;
    }

    public void setFloatprice(float floatprice) {
        this.floatprice = floatprice;
    }

    public double getDoubleprice() {
        return doubleprice;
    }

    public void setDoubleprice(double doubleprice) {
        this.doubleprice = doubleprice;
    }

    private int id;
    private String name;
    private int height;
    private char sex;
    private Date birthday;
    private Date sendtime; // 日期类型，格式：yyyy-MM-dd HH:mm:ss
    private BigDecimal price;
    private float floatprice;
    private double doubleprice;
}
