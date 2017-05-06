package com.example.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import com.example.entity.ServiceResult;
import com.example.entity.VoteOptionsEntity;
import com.example.entity.VotesEntity;
/**
 * 投票服务接口
 * @author MyCapitaine
 *
 */
public interface VoteService {
	/**
	 * 查找所有投票
	 */
	ServiceResult<Page<VotesEntity>> findAllVote(Pageable pageable);
	/**
	 * 根据id查找投票
	 */
	ServiceResult<VotesEntity> findVoteById(int id);
	/**
	 * 根据id查找投票选项
	 */
	ServiceResult<List<VoteOptionsEntity>> findVoteOptionsByVid(int vid);
	/**
	 * 根据uid查找投票
	 */
	ServiceResult<Page<VotesEntity>> findVoteByUid(int uid, Pageable pageable);
	/**
	 * 发布投票
	 */
	boolean addVote(VotesEntity ve, List<VoteOptionsEntity> vos);
	/**
	 * 更新投票
	 */
	int updateVote(VotesEntity ve);
	/**
	 * 投票是否被删除
	 */
	@Deprecated
	boolean isBanned(int id);
	/**
	 * 删除投票
	 */
	int banVote(int id);
	/**
	 * 恢复投票
	 */
	@Deprecated
	int unbanVote(int id);
	/**
	 * 获取封禁投票列表
	 */
	@Deprecated
	ServiceResult<List<VotesEntity>> findBanList();
	/**
	 * 投票结果
	 */
	ServiceResult<List<Pair<VoteOptionsEntity, Integer>>> voteResult(int vid);

	
	
	
	
}
