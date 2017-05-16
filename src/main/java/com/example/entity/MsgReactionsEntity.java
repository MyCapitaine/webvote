package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对留言的顶或踩
 * @author MyCapitaine
 *
 */
@Entity
@Table(name = "msgreactions")
public class MsgReactionsEntity {
	
	public MsgReactionsEntity() {
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * 0为顶 1为踩
	 */
	@Column(name = "rtype", nullable = false, length = 1)
	private int rtype;
	
	@Column(name = "mid", nullable = false)
	private int mid;
	
	@Column(name = "vid", nullable = false)
	private int vid;
	
	@Column(name = "ip", nullable = false)
	private String ip;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRtype() {
		return rtype;
	}

	public void setRtype(int rtype) {
		this.rtype = rtype;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}
	
	
	
	
}
