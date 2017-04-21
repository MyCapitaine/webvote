package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.MsgEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 留言DAO
 * @author MyCapitaine
 *
 */
public interface MsgDao extends JpaRepository<MsgEntity, Integer> {
	/**
	 * 通过投票id查询
	 */
	List<MsgEntity> findByVid(int id);
	/**
	 * 更新留言
	 */
//	@Modifying
//	@Transactional
//	@Query("update Msgs set inner = ?2 where id = ?1")
//	int updateMsg(int id, String inner);
	/**
	 * 封禁留言
	 */
//	@Modifying
//	@Transactional
//	@Query("update Msgs set banned = 1 where id = ?1")
//	int banMsg(int id);
	/**
	 * 解封留言
	 */
//	@Modifying
//	@Transactional
//	@Query("update Msgs set banned = 0 where id = ?1")
//	int unbanMsg(int id);
	
}
