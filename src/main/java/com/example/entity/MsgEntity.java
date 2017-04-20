package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class MsgEntity {
	@Id
	private int id;
	
	@Column(name = "vid", nullable = false)
	private int vid;
	
	@Column(name = "inner", nullable = false)
	private String inner;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date msgTime;
	
	@Column(name = "ip", nullable = false, unique = true)
	private String ip;
	
    @Column(name = "banned", nullable = false, length = 1)
    private int banned = 0;

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

	public String getInner() {
		return inner;
	}

	public void setInner(String inner) {
		this.inner = inner;
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
    
    
    
	
}
