package com.example.entity;

import javax.persistence.*;

/**
 * Created by hasee on 2017/5/8.
 */
@Entity
@Table(name = "ip")
public class Ip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "ip",nullable = false)
    private String ip;

    public Ip() {
    }
    public Ip(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
