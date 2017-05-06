package com.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.VotesEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * 投票信息DAO
 * @author MyCapitaine
 *
 */
public interface VotesDao extends JpaRepository<VotesEntity, Integer> {
	
	/**
	 * 查找所有投票(删除的不包含在内)
	 */
	@Query(" from VotesEntity where banned = 0")
	Page<VotesEntity> findAll(Pageable pageable);
	
	/**
	 * 通过id查找(删除的不包含在内)
	 */
	@Query(" from VotesEntity where id = ?1 and banned = 0")
	VotesEntity findById(int id);
	
	/**
	 * 通过uid查找(删除的不包含在内)
	 */
	@Query(" from VotesEntity where uid = ?1 and banned = 0")
	Page<VotesEntity> findByUid(int uid, Pageable pageable);
	
	/**
	 * 查询删除的投票列表
	 */
	@Query(" from VotesEntity where banned = 1")
	@Deprecated
	List<VotesEntity> findBanList();

	/**
	 * 查询投票是否被删除
	 */
	@Query("select v.banned from VotesEntity v where v.id = ?1")
	@Deprecated
	int isBanned(int id);
	
	/**
	 * 删除投票
	 */
    @Modifying
    @Transactional
	@Query("update VotesEntity set banned = 1 where id = ?1")
	int ban(int id);
	/**
	 * 恢复投票
	 */
    @Modifying
    @Transactional
    @Deprecated
	@Query("update VotesEntity set banned = 0 where id = ?1")
	int unban(int id);
	/**
	 * 更新投票
	 */
	@Modifying
    @Transactional
	@Query(value = "update VotesEntity v set v.vname = ?2, v.vinfo = ?3, v.beginTime = ?4, v.deadLine = ?5, v.resultAuthority = ?6 where v.id = ?1")
	int updateVotes(int id, String vname, String vinfo, Date beginTime, Date deadLine, int resultAuthority);

}
