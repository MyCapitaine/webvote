package com.example.serviceInterface;

import java.util.List;

import org.springframework.data.util.Pair;

import com.example.entity.ServiceResult;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VoteOptionsEntity;
import com.example.entity.VotesEntity;
/**
 * 投票服务接口
 * @author MyCapitaine
 *
 */
public interface VoteService {
	/**
	 * 根据id查找投票
	 */
	ServiceResult<VotesEntity> findVoteById(int id);
	/**
	 * 根据uid查找投票
	 */
	ServiceResult<List<VotesEntity>> findVoteByUid(int uid);
	/**
	 * 发布投票
	 */
	boolean publishVote(VotesEntity ve, List<VoteOptionsEntity> vos);
	/**
	 * 更新投票
	 */
	int updateVote(VotesEntity ve);
	/**
	 * 投票是否被封禁
	 */
	boolean isBanned(int id);
	/**
	 * 封禁投票
	 */
	int banVote(int id);
	/**
	 * 解禁投票
	 */
	int unbanVote(int id);
	/**
	 * 获取封禁投票列表
	 */
	ServiceResult<List<VotesEntity>> findBanList();
	/**
	 * 游客投票
	 */
	boolean voteActivity(VoteActivitiesEntity va);
	/**
	 * 游客是否已进行过投票
	 * 以及投的是什么
	 */
	ServiceResult<VoteActivitiesEntity> isIpVoted(String ip);
	/**
	 * 投票更新
	 */
	int updateVote(VoteActivitiesEntity va);
	/**
	 * 投票结果
	 */
	ServiceResult<List<Pair<VoteOptionsEntity, Integer>>> voteResult(int vid);

	
	
	
	
}
