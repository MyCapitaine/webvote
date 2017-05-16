package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.MsgReactionsDao;
import com.example.dao.MsgsDao;
import com.example.dao.VoteActivitiesDao;
import com.example.dao.VoteOptionsDao;
import com.example.entity.MsgReactionsEntity;
import com.example.entity.MsgsEntity;
import com.example.entity.ServiceResult;
import com.example.entity.VoteActivitiesEntity;
import com.example.entity.VoteOptionsEntity;
import com.example.serviceInterface.GuestService;
import com.example.vo.VoteResultVO;

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
	MsgReactionsDao msgReactionsDao;
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
//		ServiceResult<List<MsgsEntity>> sr = new ServiceResult<List<MsgsEntity>>();
//		List<MsgsEntity> msgs = msgsDao.findUnbanMsgByVid(vid);
//		sr.setData(msgs);
//		sr.setSuccess(msgs != null);
//		return sr;
		throw new RuntimeException("not support");
	}
	@Override
	public boolean doVote(VoteActivitiesEntity vae) {
		return voteActivitiesDao.save(vae) != null;
	}
	@Override
	public ServiceResult<List<VoteActivitiesEntity>> isIpVoted(String ip, int vid) {
		ServiceResult<List<VoteActivitiesEntity>> sr = new ServiceResult<List<VoteActivitiesEntity>>();
		List<VoteActivitiesEntity> vaList = voteActivitiesDao.findByIp(ip, vid);
		sr.setData(vaList);
		sr.setSuccess(vaList != null && vaList.size() != 0);
		return sr;
	}
	@Override
	public boolean doMsg(MsgsEntity me) {
		return msgsDao.save(me) != null;
	}
	@Override
	public ServiceResult<MsgsEntity> isIpMsg(String ip, int vid) {
		ServiceResult<MsgsEntity> sr = new ServiceResult<MsgsEntity>();
		MsgsEntity me = msgsDao.findByIp(ip, vid);
		sr.setData(me);
		sr.setSuccess(me != null);
		return sr;
	}
	@Override
	public ServiceResult<List<VoteResultVO>> voteResult(int vid) {
		ServiceResult<List<VoteResultVO>> sr = new ServiceResult<List<VoteResultVO>>();
		List<VoteResultVO> vros = new ArrayList<VoteResultVO>();
		sr.setData(vros);
		List<VoteOptionsEntity> vos = voteOptionsDao.findByVid(vid);
		if(vos == null) {
			sr.setSuccess(false); 
			return sr;
		}
		int allVoteNum = 0;
		for(VoteOptionsEntity vo : vos) {
			List<VoteActivitiesEntity> vas = voteActivitiesDao.findByOptionId(vo.getId());
			if(vas == null){
				sr.setSuccess(false); 
				return sr;
			}
			VoteResultVO vro = new VoteResultVO();
			vro.setVoteNum(vas.size());
			vro.setVoteOption(vo);
			vros.add(vro);
			allVoteNum += vas.size();
		}
		for(VoteResultVO vro : vros) {
			if(allVoteNum == 0) {
				vro.setRatio("0%");
				continue;
			}
			vro.setRatio(Math.round((vro.getVoteNum() * 1000 / allVoteNum)) / 10 + "%");
		}
		
		sr.setSuccess(true);
		return sr;
	}
	@Override
	public int banMsg(int mid) {
//		return msgsDao.banMsg(mid);
		throw new RuntimeException("not support");
	}
	@Override
	public int unbanMsg(int mid) {
//		return msgsDao.unbanMsg(mid);
		throw new RuntimeException("not support");
	}
	@Override
	public int updateVote(VoteActivitiesEntity vae) {
//		return voteActivitiesDao.updateVoteActivity(vae.getId(), vae.getOptionId());
		throw new RuntimeException("not support");
	}
	@Override
	public boolean delVote(List<VoteActivitiesEntity> vaList) {
//		for(VoteActivitiesEntity va : vaList) {
//			voteActivitiesDao.delete(va.getId());
//		}
//		return true;
		throw new RuntimeException("not support");
	}
	@Override
	public int updateMsg(MsgsEntity me) {
//		return msgsDao.updateMsg(me.getId(), me.getMsgText());
		throw new RuntimeException("not support");
	}
	@Override
	public boolean delMsg(MsgsEntity me) {
		msgsDao.delete(me.getId());
		return true;
	}
	@Override
	public int bumpMsg(int mid, String ip, int vid) {
		MsgReactionsEntity reaction = msgReactionsDao.findByMidAndIp(mid, ip);
		MsgReactionsEntity bump = new MsgReactionsEntity();
		bump.setIp(ip);
		bump.setMid(mid);
		bump.setRtype(0);
		bump.setVid(vid);
		if(reaction == null) { //未进行过回执,顶
			msgReactionsDao.saveAndFlush(bump);
			msgsDao.bumpMsg(mid, ip);
			return 1;
		}
		if(reaction.getRtype() == 0) {//回执为顶,取消顶
			msgReactionsDao.delete(reaction.getId());
			msgsDao.unBumpMsg(mid, ip);
			return 1;
		}
		else{ //回执为踩,转为顶
			msgReactionsDao.changeToBumpMsg(mid, ip);
			msgsDao.changeToBumpMsg(mid, ip);
			return 1;
		}
	}
	@Override
	public int treadMsg(int mid, String ip, int vid) {
		MsgReactionsEntity reaction = msgReactionsDao.findByMidAndIp(mid, ip);
		MsgReactionsEntity bump = new MsgReactionsEntity();
		bump.setIp(ip);
		bump.setMid(mid);
		bump.setRtype(1);
		bump.setVid(vid);
		if(reaction == null) { //未进行过回执,踩
			msgReactionsDao.saveAndFlush(bump);
			msgsDao.treadMsg(mid, ip);
			return 1;
		}
		if(reaction.getRtype() == 1){ //回执为踩,取消踩
			msgReactionsDao.delete(reaction.getId());
			msgsDao.unTreadMsg(mid, ip);
			return 1;
		}
		else{ //回执为顶,转为踩
			msgReactionsDao.changeToTreadMsg(mid, ip);
			msgsDao.changeToTreadMsg(mid, ip);
			return 1;
		}
	}
	
	
	
}
