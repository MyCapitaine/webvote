package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.MsgReactionsEntity;

/**
 * 留言回执DAO
 * @author MyCapitaine
 *
 */
public interface MsgReactionsDao extends JpaRepository<MsgReactionsEntity, Integer> {
	/**
	 * 通过ip和留言id获取回执
	 */
	MsgReactionsEntity findByMidAndIp(int mid, String ip);
	
	/**
	 * (踩)转为顶留言
	 */
	@Modifying
	@Transactional
	@Query("update MsgReactionsEntity set rtype = 0 where mid = ?1 and ip = ?2")
	int changeToBumpMsg(int mid, String ip);
	/**
	 * (顶)转为踩留言
	 */
	@Modifying
	@Transactional
	@Query("update MsgReactionsEntity set rtype = 1 where mid = ?1 and ip = ?2")
	int changeToTreadMsg(int mid, String ip);

}
