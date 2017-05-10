package com.example.vo;

import com.example.entity.VoteOptionsEntity;

public class VoteResultVO {
	/**
	 * 投票选项
	 */
	private VoteOptionsEntity voteOption;
	/**
	 * 选项得票数
	 */
	private int voteNum;
	/**
	 * 得票比例
	 * 保留一位小数
	 * 例如 5.2%
	 */
	private String ratio;
	
	public VoteOptionsEntity getVoteOption() {
		return voteOption;
	}
	public void setVoteOption(VoteOptionsEntity voteOption) {
		this.voteOption = voteOption;
	}
	public int getVoteNum() {
		return voteNum;
	}
	public void setVoteNum(int voteNum) {
		this.voteNum = voteNum;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
}
