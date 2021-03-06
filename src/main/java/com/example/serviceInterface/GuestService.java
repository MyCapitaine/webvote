package com.example.serviceInterface;

import java.util.List;

import com.example.entity.MsgsEntity;
import com.example.entity.ServiceResult;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VotesEntity;
import com.example.vo.VoteResultVO;

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
	@Deprecated
	ServiceResult<List<MsgsEntity>> getunbannedMsgsByVid(int vid);
	/**
	 * 游客投票
	 */
	boolean doVote(VoteActivitiesEntity vae);
	/**
	 * 更改投票
	 */
	@Deprecated
	int updateVote(VoteActivitiesEntity vae);
	/**
	 * 删除投票
	 */
	@Deprecated
	boolean delVote(List<VoteActivitiesEntity> vae);
	/**
	 * 游客是否已进行过投票
	 * 以及投的是什么
	 */
	ServiceResult<List<VoteActivitiesEntity>> isIpVoted(String ip, int vid);
	/**
	 * 游客留言
	 */
	boolean doMsg(MsgsEntity me);
	/**
	 * 更改留言
	 */
	@Deprecated
	int updateMsg(MsgsEntity me);
	/**
	 * 删除留言
	 */
	boolean delMsg(MsgsEntity me);
	/**
	 * 游客是否已进行过留言
	 * 以及留的是什么
	 */
	ServiceResult<MsgsEntity> isIpMsg(String ip, int vid);
	/**
	 * 投票结果
	 */
	ServiceResult<List<VoteResultVO>> voteResult(int vid);
	/**
	 * 封禁留言
	 */
	@Deprecated
	int banMsg(int mid);
	/**
	 * 解封留言
	 */
	@Deprecated
	int unbanMsg(int mid);
	
	/**
	 * 顶留言
	 */
	int bumpMsg(int mid, String ip, int vid);
	/**
	 * 踩留言
	 */
	int treadMsg(int mid, String ip, int vid);
	/**
	 * 获取推荐投票(一段时间内，投了该投票的其他人也在投)
	 */
	ServiceResult<List<VotesEntity>> getRecommandVotes(int vid);
}
