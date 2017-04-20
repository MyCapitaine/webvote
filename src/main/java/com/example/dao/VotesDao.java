package com.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.VotesEntity;
/**
 * 投票信息DAO
 * @author MyCapitaine
 *
 */
public interface VotesDao extends JpaRepository<VotesEntity, Integer> {
	/**
	 * 通过id查找
	 */
	VotesEntity findById(int id);
	/**
	 * 通过uid查找
	 */
	List<VotesEntity> findByUid(int uid);
	/**
	 * 查询封禁列表
	 */
	@Query("select * from votes where banned = 1")
	List<VotesEntity> findBanList();
	/**
	 * 查询投票是否被封禁
	 */
	@Query("select banned from votes where id = ?1")
	int isBanned(int id);
	/**
	 * 封禁投票
	 */
	@Query("update votes set banned = 1 where id = ?1")
	int ban(int id);
	/**
	 * 解封投票
	 */
	@Query("update votes set banned = 0 where id = ?1")
	int unban(int id);
	/**
	 * 更新
	 */
	@Query("update votes set vname = ?2, vinfo = ?3, createTime = ?4, deadLine = ?5, resultauthority = ?6 where id = ?1")
	int updateVotes(int id, String vname, String vinfo, Date createTime, Date deadLine,
			int resultAuthority);
	
	
}
