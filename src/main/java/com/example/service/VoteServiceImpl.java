package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import com.example.dao.VoteActivitiesDao;
import com.example.dao.VoteOptionsDao;
import com.example.dao.VotesDao;
import com.example.entity.ServiceResult;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VoteOptionsEntity;
import com.example.entity.VotesEntity;
import com.example.serviceInterface.VoteService;
/**
 * 投票接口实现
 * @author MyCapitaine
 *
 */
@Service
public class VoteServiceImpl implements VoteService {
	@Autowired
	VotesDao votesDao;
	@Autowired
	VoteOptionsDao voteOptionsDao;
	@Autowired
	VoteActivitiesDao voteActivitiesDao;
	
	@Override
	public ServiceResult<VotesEntity> findVoteById(int id) {
		ServiceResult<VotesEntity> sr = new ServiceResult<VotesEntity>();
		VotesEntity ve = votesDao.findById(id);
		sr.setData(ve);
		sr.setSuccess(ve != null);
		return sr;
	}

	@Override
	public ServiceResult<List<VotesEntity>> findVoteByUid(int uid) {
		ServiceResult<List<VotesEntity>> sr = new ServiceResult<List<VotesEntity>>();
		List<VotesEntity> veList = votesDao.findByUid(uid);
		sr.setData(veList);
		sr.setSuccess(veList != null);
		return sr;
	}
	@Override
	public boolean addVote(VotesEntity ve, List<VoteOptionsEntity> vos) {
		VotesEntity vote = votesDao.saveAndFlush(ve);
		if(vote == null) 
			return false;
		for(VoteOptionsEntity option : vos) {
			option.setVid(vote.getId());
			if(voteOptionsDao.saveAndFlush(option) == null) 
				return false;
		}
		return true;
	}

	@Override
	public int updateVote(VotesEntity ve) {
		return votesDao.updateVotes(ve.getId(), ve.getVname(),
				ve.getVinfo(), ve.getCreateTime(), ve.getDeadLine(), ve.getResultAuthority());
	}

	@Override
	public boolean isBanned(int id) {
		return votesDao.isBanned(id) == 0 ? false : true;
	}
	
	@Override
	public int banVote(int id) {
		return votesDao.ban(id);
	}

	@Override
	public int unbanVote(int id) {
		return votesDao.unban(id);
	}

	@Override
	public ServiceResult<List<VotesEntity>> findBanList() {
		ServiceResult<List<VotesEntity>> sr = new ServiceResult<List<VotesEntity>>();
		List<VotesEntity> ves = votesDao.findBanList();
		sr.setData(ves);
		sr.setSuccess(ves != null);
		return sr;
	}

	@Override
	public ServiceResult<List<Pair<VoteOptionsEntity, Integer>>> voteResult(int vid) {
		ServiceResult<List<Pair<VoteOptionsEntity, Integer>>> sr = new ServiceResult<List<Pair<VoteOptionsEntity, Integer>>>();
		List<Pair<VoteOptionsEntity, Integer>> pairs = new ArrayList<Pair<VoteOptionsEntity, Integer>>();
		sr.setData(pairs);
		List<VoteOptionsEntity> vos = voteOptionsDao.findByVid(vid);
		if(vos == null) {
			sr.setSuccess(false); 
			return sr;
		}
		for(VoteOptionsEntity vo : vos) {
			List<VoteActivitiesEntity> vas = voteActivitiesDao.findByOptionId(vo.getId());
			if(vas == null){
				sr.setSuccess(false); 
				return sr;
			}
			pairs.add(Pair.of(vo, vas.size()));
		}
		sr.setSuccess(true);
		return sr;
	}


}
