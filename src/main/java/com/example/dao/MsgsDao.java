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
	 * 通过ip查找某投票的留言
	 */
	@Query(" from MsgsEntity where ip = ?1 and vid = ?2 ")
	MsgsEntity findByIp(String ip, int vid);
	/**
	 * 通过投票id查询所有留言
	 */
	List<MsgsEntity> findByVid(int id);
	
	/**
	 * 顶留言，留言顶数+1
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set bumpnum = bumpnum + 1 where id = ?1 and ip = ?2")
	int bumpMsg(int mid, String ip);
	/**
	 * 取消顶留言，留言顶数-1
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set bumpnum = bumpnum - 1 where id = ?1 and ip = ?2")
	int unBumpMsg(int mid, String ip);
	/**
	 * 转为顶留言，留言踩数-1，顶数+1
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set bumpnum = bumpnum + 1, treadnum = treadnum - 1 where id = ?1 and ip = ?2")
	int changeToBumpMsg(int mid, String ip);
	/**
	 * 踩留言，踩留言数+1
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set treadnum = treadnum + 1 where id = ?1 and ip = ?2")
	int treadMsg(int mid, String ip);
	/**
	 * 取消踩留言，踩留言数-1
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set treadnum = treadnum - 1 where id = ?1 and ip = ?2")
	int unTreadMsg(int mid, String ip);
	/**
	 * 转为踩留言，顶留言数-1，踩留言数+1
	 */
	@Modifying
	@Transactional
	@Query("update MsgsEntity set bumpnum = bumpnum - 1, treadnum = treadnum + 1 where id = ?1 and ip = ?2")
	int changeToTreadMsg(int mid, String ip);
	
	
	/**
	 * 通过投票id查询未封禁的留言
	 */
	@Deprecated
	@Query("select m from MsgsEntity m where vid = ?1 and banned = 0")
	List<MsgsEntity> findUnbanMsgByVid(int id);
	/**
	 * 更新留言
	 */
	@Modifying
	@Transactional
	@Deprecated
	@Query("update MsgsEntity set msgtext = ?2 where id = ?1")
	int updateMsg(int id, String msgtext);
	/**
	 * 封禁留言
	 */
	@Modifying
	@Transactional
	@Deprecated
	@Query("update MsgsEntity set banned = 1 where id = ?1")
	int banMsg(int id);
	/**
	 * 解封留言
	 */
	@Modifying
	@Transactional
	@Deprecated
	@Query("update MsgsEntity set banned = 0 where id = ?1")
	int unbanMsg(int id);
	
}
