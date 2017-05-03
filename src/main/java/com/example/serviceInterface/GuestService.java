package com.example.serviceInterface;

import java.util.List;

import org.springframework.data.util.Pair;

import com.example.entity.MsgsEntity;
import com.example.entity.ServiceResult;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VoteOptionsEntity;

/**
 * 游客接口
 * @author MyCapitaine
 *
 */
public interface GuestService {
	/**
	 * 通过投票id获取投票的留言列表
	 */
	ServiceResult<List<MsgsEntity>> getMsgsByVid(int vid);
	/**
	 * 通过投票id获取未被封禁的投票的留言列表
	 */
	ServiceResult<List<MsgsEntity>> getunbannedMsgsByVid(int vid);
	/**
	 * 游客投票
	 */
	boolean doVote(VoteActivitiesEntity vae);
	/**
	 * 更改投票
	 */
	int updateVote(VoteActivitiesEntity vae);
	/**
	 * 删除投票
	 */
	boolean delVote(VoteActivitiesEntity vae);
	/**
	 * 游客是否已进行过投票
	 * 以及投的是什么
	 */
	ServiceResult<VoteActivitiesEntity> isIpVoted(String ip);
	/**
	 * 游客留言
	 */
	boolean doMsg(MsgsEntity me);
	/**
	 * 更改留言
	 */
	int updateMsg(MsgsEntity me);
	/**
	 * 删除留言
	 */
	boolean delMsg(MsgsEntity me);
	/**
	 * 游客是否已进行过留言
	 */
	ServiceResult<MsgsEntity> isIpMsg(String ip);
	/**
	 * 投票结果
	 */
	ServiceResult<List<Pair<VoteOptionsEntity, Integer>>> voteResult(int vid);
	/**
	 * 封禁投票
	 */
	int banMsg(int mid);
	/**
	 * 解封投票
	 */
	int unbanMsg(int mid);
	
}
