package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.MsgsEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 留言DAO
 * @author MyCapitaine
 *
 */
public interface MsgsDao extends JpaRepository<MsgsEntity, Integer> {
	/**
	 * 通过ip查找
	 */
	@Query(" from MsgsEntity where ip = ?1 and vid = ?2 ")
	MsgsEntity findByIp(String ip, int vid);
	/**
	 * 通过投票id查询所有留言
	 */
	List<MsgsEntity> findByVid(int id);
	/**
	 * 通过投票id查询未封禁的留言
	 */
	@Query("select m from MsgsEntity m where vid = ?1 and banned = 0")
	List<MsgsEntity> findUnbanMsgByVid(int id);
	/**
	 * 更新留言
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set msgtext = ?2 where id = ?1")
	int updateMsg(int id, String msgtext);
	/**
	 * 封禁留言
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set banned = 1 where id = ?1")
	int banMsg(int id);
	/**
	 * 解封留言
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set banned = 0 where id = ?1")
	int unbanMsg(int id);
	
}
