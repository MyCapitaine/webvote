package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 投票本体
 * @author MyCapitaine
 *
 */
@Entity
@Table(name = "votes")
public class VotesEntity {
	@Id
	private int id;
	
	@Column(name = "uid", nullable = false)
	private int uid;
	
	@Column(name = "vname", nullable = false)
	private String vname;
	
	@Column(name = "vinfo", nullable = true)
	private String vinfo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date deadLine;
	
	@Column(name = "resultauthority", nullable = false)
	private int resultAuthority;
	
    @Column(name = "banned", nullable = false, length = 1)
    private int banned = 0; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	public String getVinfo() {
		return vinfo;
	}

	public void setVinfo(String vinfo) {
		this.vinfo = vinfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public int getResultAuthority() {
		return resultAuthority;
	}

	public void setResultAuthority(int resultAuthority) {
		this.resultAuthority = resultAuthority;
	}

	public int getBanned() {
		return banned;
	}

	public void setBanned(int banned) {
		this.banned = banned;
	}
	
    
    
}
