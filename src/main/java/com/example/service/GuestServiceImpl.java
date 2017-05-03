package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.example.dao.MsgsDao;
import com.example.dao.VoteActivitiesDao;
import com.example.dao.VoteOptionsDao;
import com.example.entity.MsgsEntity;
import com.example.entity.ServiceResult;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VoteOptionsEntity;
import com.example.serviceInterface.GuestService;

/**
 * 游客接口实现
 * @author MyCapitaine
 *
 */
@Service
public class GuestServiceImpl implements GuestService {
	@Autowired
	VoteActivitiesDao voteActivitiesDao;
	@Autowired
	MsgsDao msgsDao;
	@Autowired
	VoteOptionsDao voteOptionsDao;
	
	@Override
	public ServiceResult<List<MsgsEntity>> getMsgsByVid(int vid) {
		ServiceResult<List<MsgsEntity>> sr = new ServiceResult<List<MsgsEntity>>();
		List<MsgsEntity> msgs = msgsDao.findByVid(vid);
		sr.setData(msgs);
		sr.setSuccess(msgs != null);
		return sr;
	}
	@Override
	public ServiceResult<List<MsgsEntity>> getunbannedMsgsByVid(int vid) {
		ServiceResult<List<MsgsEntity>> sr = new ServiceResult<List<MsgsEntity>>();
		List<MsgsEntity> msgs = msgsDao.findUnbanMsgByVid(vid);
		sr.setData(msgs);
		sr.setSuccess(msgs != null);
		return sr;
	}
	@Override
	public boolean doVote(VoteActivitiesEntity vae) {
		return voteActivitiesDao.save(vae) != null;
	}
	@Override
	public ServiceResult<VoteActivitiesEntity> isIpVoted(String ip) {
		ServiceResult<VoteActivitiesEntity> sr = new ServiceResult<VoteActivitiesEntity>();
		VoteActivitiesEntity va = voteActivitiesDao.findByIp(ip);
		sr.setData(va);
		sr.setSuccess(va != null);
		return sr;
	}
	@Override
	public boolean doMsg(MsgsEntity me) {
		return msgsDao.save(me) != null;
	}
	@Override
	public ServiceResult<MsgsEntity> isIpMsg(String ip) {
		ServiceResult<MsgsEntity> sr = new ServiceResult<MsgsEntity>();
		MsgsEntity me = msgsDao.findByIp(ip);
		sr.setData(me);
		sr.setSuccess(me != null);
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
	@Override
	public int banMsg(int mid) {
		return msgsDao.banMsg(mid);
	}
	@Override
	public int unbanMsg(int mid) {
		return msgsDao.unbanMsg(mid);
	}
	@Override
	public int updateVote(VoteActivitiesEntity vae) {
		return voteActivitiesDao.updateVoteActivity(vae.getId(), vae.getOptionId());
	}
	@Override
	public boolean delVote(VoteActivitiesEntity vae) {
		voteActivitiesDao.delete(vae.getId());
		return true;
	}
	@Override
	public int updateMsg(MsgsEntity me) {
		return msgsDao.updateMsg(me.getId(), me.getMsgText());
	}
	@Override
	public boolean delMsg(MsgsEntity me) {
		msgsDao.delete(me.getId());
		return true;
	}
	
	
	
}