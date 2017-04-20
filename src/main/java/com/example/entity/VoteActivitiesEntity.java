package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 每次投票行为
 * @author MyCapitaine
 *
 */
@Entity
@Table(name = "vote_activities")
public class VoteActivitiesEntity {
	
	@Id
	private int id;
	
	@Column(name = "oid", nullable = false)
	private int optionId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date voteTime;
	
	@Column(name = "ip", nullable = false, unique = true)
	private String ip;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public Date getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
