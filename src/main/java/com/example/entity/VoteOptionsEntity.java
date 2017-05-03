package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 投票选项
 * @author MyCapitaine
 *
 */
@Entity
@Table(name = "options")
public class VoteOptionsEntity {
	@Id
	private int id;

	public VoteOptionsEntity() {
	}

	@Column(name = "vid", nullable = false)
	private int vid;
	
	@Column(name = "optiontext", nullable = false)
	private String optionText;

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

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}


}
