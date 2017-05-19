package com.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dao.VoteActivitiesDao;
import com.example.dao.VoteOptionsDao;
import com.example.dao.VotesDao;
import com.example.entity.ServiceResult;
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
	public ServiceResult<Page<VotesEntity>> findAllVote(Pageable pageable) {
		ServiceResult<Page<VotesEntity>> sr = new ServiceResult<Page<VotesEntity>>();
		Page<VotesEntity> page = votesDao.findAllVote(pageable);
		sr.setData(page);
		sr.setSuccess(page != null);
		return sr;
	}
	
	@Override
	public ServiceResult<VotesEntity> findVoteById(int id) {
		ServiceResult<VotesEntity> sr = new ServiceResult<VotesEntity>();
		VotesEntity ve = votesDao.findById(id);
		sr.setData(ve);
		sr.setSuccess(ve != null);
		return sr;
	}

	@Override
	public ServiceResult<Page<VotesEntity>> findVoteByUid(int uid, Pageable pageable) {
		ServiceResult<Page<VotesEntity>> sr = new ServiceResult<Page<VotesEntity>>();
		Page<VotesEntity> veList = votesDao.findByUid(uid, pageable);
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
		return votesDao.updateVotes(ve.getId(), ve.getVname(), ve.getVinfo(),
				ve.getVtype(), ve.getBeginTime(), ve.getDeadLine(), ve.getResultAuthority());
	}

	@Override
	public boolean isBanned(int id) {
//		return votesDao.isBanned(id) == 0 ? false : true;
		throw new RuntimeException("not support");
	}
	
	@Override
	public int banVote(int id) {
		return votesDao.ban(id);
	}

	@Override
	public int unbanVote(int id) {
//		return votesDao.unban(id);
		throw new RuntimeException("not support");
	}

	@Override
	public ServiceResult<List<VotesEntity>> findBanList() {
//		ServiceResult<List<VotesEntity>> sr = new ServiceResult<List<VotesEntity>>();
//		List<VotesEntity> ves = votesDao.findBanList();
//		sr.setData(ves);
//		sr.setSuccess(ves != null);
//		return sr;
		throw new RuntimeException("not support");
	}

	@Override
	public ServiceResult<List<VoteOptionsEntity>> findVoteOptionsByVid(int vid) {
		ServiceResult<List<VoteOptionsEntity>> sr = new ServiceResult<List<VoteOptionsEntity>>();
		List<VoteOptionsEntity> options = voteOptionsDao.findByVid(vid);
		sr.setData(options);
		sr.setSuccess(options != null);
		return sr;
	}
	
	private static final long HOT__TIME = 7 * 24 * 3600 * 1000;
	private static final int HOT_NUM = 3;
	@Override
	public ServiceResult<List<VotesEntity>> findHotVotes() {
		ServiceResult<List<VotesEntity>> sr = new ServiceResult<List<VotesEntity>>();
		
		Date now = new Date();
		Date aWeekAgo =  new Date(now.getTime() - HOT__TIME);
		List<Object[]> voteActivities = voteActivitiesDao.findByTime(aWeekAgo, now);
		//统计
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(Object[] vce : voteActivities) {
			Integer num = map.get(vce[1]);
			if(num == null) {
				num = 0;
			}
			map.put((Integer)vce[1], num + 1);
		}
		//排序
		List<Entry<Integer, Integer>> mapList = new ArrayList<Entry<Integer, Integer>>(map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue() - o2.getValue();
			}
		}.reversed());
		List<Integer> vids = new ArrayList<Integer>();
		for(int i = 0; i < HOT_NUM && i < mapList.size(); i ++) {
			vids.add(mapList.get(i).getKey());
		}
		List<VotesEntity> votes = vids.size() == 0 ? 
				new ArrayList<VotesEntity>() : votesDao.findByVid(vids);
		sr.setData(votes);
		sr.setSuccess(votes != null && votes.size() != 0);
		return sr;
	}

	@Override
	public ServiceResult<Page<VotesEntity>> researchVotes(Pageable pageable, String keywords) {
		
		
		
		
		return null;
	}



}
