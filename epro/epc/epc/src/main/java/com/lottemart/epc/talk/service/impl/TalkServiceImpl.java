package com.lottemart.epc.talk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.talk.dao.TalkDao;
import com.lottemart.epc.talk.service.TalkService;

@Service("TalkService")
public class TalkServiceImpl implements TalkService {
	
	@Autowired
	private TalkDao talkDao;
	
	public List<DataMap> selectMemberInfo(Map<String,Object> map ) throws Exception{
		return talkDao.selectMemberInfo(map);
	}
	
	public List<DataMap> selectOrderInfo(Map<String,Object> map ) throws Exception{
		return talkDao.selectOrderInfo(map);
	}
}
