package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 投票留言
 * @author MyCapitaine
 *
 */
@Entity
@Table(name = "msgs")
public class MsgsEntity {

	public MsgsEntity() {
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "vid", nullable = false)
	private int vid;
	
	@Column(name = "msgtext", nullable = false)
	private String msgText;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date msgTime;
	
	@Column(name = "ip", nullable = false)
	private String ip;
	
    @Column(name = "banned", nullable = false, length = 1)
    private int banned = 0;
    
    @Column(name = "bumpnum")
    private int bumpNum = 0;
    
    @Column(name = "treadnum")
    private int treadNum = 0;
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public Date getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getBanned() {
		return banned;
	}

	public void setBanned(int banned) {
		this.banned = banned;
	}

	public int getBumpNum() {
		return bumpNum;
	}

	public void setBumpNum(int bumpNum) {
		this.bumpNum = bumpNum;
	}

	public int getTreadNum() {
		return treadNum;
	}

	public void setTreadNum(int treadNum) {
		this.treadNum = treadNum;
	}

	
}
