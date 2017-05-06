package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.VoteActivitiesEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 投票活动DAO
 * @author MyCapitaine
 *
 */
public interface VoteActivitiesDao extends JpaRepository<VoteActivitiesEntity, Integer> {
	/**
	 * 通过投票id查找
	 */
	List<VoteActivitiesEntity> findByVid(int vid);
	/**
	 * 通过选项id查找
	 */
	List<VoteActivitiesEntity> findByOptionId(int oid);
	/**
	 * 通过ip查找
	 */
	@Query(" from VoteActivitiesEntity where ip = ?1 and vid = ?2 ")
	List<VoteActivitiesEntity> findByIp(String ip, int vid);
	/**
	 * 更新投票活动
	 */
	@Modifying
	@Transactional
	@Deprecated
	@Query("update VoteActivitiesEntity set oid = ?2 where id = ?1")
	int updateVoteActivity(int id, int oid);
	
}
