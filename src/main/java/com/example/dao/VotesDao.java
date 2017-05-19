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
	@Query("select v from VotesEntity v where v.banned = 0")
	Page<VotesEntity> findAllVote(Pageable pageable);
	
	/**
	 * 通过id查找(删除的不包含在内)
	 */
	@Query(" from VotesEntity where id = ?1 and banned = 0")
	VotesEntity findById(int id);
	
	/**
	 * 通过uid查找(删除的不包含在内)
	 */
	@Query("select v from VotesEntity v where v.uid = ?1 and v.banned = 0")
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
	@Query("update VotesEntity v set v.vname = ?2, v.vinfo = ?3, v.vtype = ?4, v.beginTime = ?5, v.deadLine = ?6, v.resultAuthority = ?7 where v.id = ?1")
	int updateVotes(int id, String vname, String vinfo, int vtype, Date beginTime, Date deadLine, int resultAuthority);

	/**
	 * 根据id查找投票
	 */
	@Query("from VotesEntity where id in ?1 and banned = 0")
	List<VotesEntity> findByVid(List<Integer> ids);
	/**
	 * 根据投票名和投票简介是否包含关键字查找投票
	 */
	@Query("select v from VotesEntity v where v.vname like %?1% or v.vinfo like %?1% and v.banned = 0")
	Page<VotesEntity> research(String keyword, Pageable pageable);
	
}
