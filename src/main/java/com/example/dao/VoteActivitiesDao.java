package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.VoteActivitiesEntity;
/**
 * 投票活动DAO
 * @author MyCapitaine
 *
 */
public interface VoteActivitiesDao extends JpaRepository<VoteActivitiesEntity, Integer> {
	/**
	 * 通过选项id查找
	 */
	List<VoteActivitiesEntity> findByOptionId(int oid);
	/**
	 * 通过ip查找
	 */
	VoteActivitiesEntity findbyIp();
	/**
	 * 更新投票活动
	 */
	@Query("update vote_activities set oid = ?2 where id = ?1")
	int updateVoteActivity(int id, int oid);
	
}
