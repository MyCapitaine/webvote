package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.VoteOptionsEntity;
/**
 * 投票选项DAO
 * @author MyCapitaine
 *
 */
public interface VoteOptionsDao extends JpaRepository<VoteOptionsEntity, Integer>  {
	/**
	 * 通过投票id查找
	 */
	List<VoteOptionsEntity> findByVid(int vid);
	
}
