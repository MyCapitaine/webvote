package com.example.dao;

import java.util.Date;
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
	 * 通过投票id查找一段时间投过的IP
	 */
	@Query("select distinct ip from VoteActivitiesEntity where vid = ?1 and voteTime > ?2 and voteTime < ?3")
	List<String> findIpByVid(int vid, Date time1, Date time2);
	/**
	 * 通过IP查找一段时间这些IP进行的投票(未被删除的)
	 */
	@Query(" select distinct va.ip, va.vid from VoteActivitiesEntity va, VotesEntity v where va.ip in ?1 and va.voteTime > ?2 and va.voteTime < ?3 and va.vid = v.id and v.banned = 0")
	List<Object[]> findByIps(List<String> ip, Date time1, Date time2);
	
	/**
	 * 更新投票活动
	 */
	@Modifying
	@Transactional
	@Deprecated
	@Query("update VoteActivitiesEntity set oid = ?2 where id = ?1")
	int updateVoteActivity(int id, int oid);
	
}
