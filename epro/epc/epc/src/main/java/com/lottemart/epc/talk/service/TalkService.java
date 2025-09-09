package com.lottemart.epc.talk.service;

import java.util.List;
import java.util.Map;

import com.lottemart.common.util.DataMap;

public interface TalkService {
	
	public List<DataMap> selectMemberInfo(Map<String,Object> map ) throws Exception;
	
	public List<DataMap> selectOrderInfo(Map<String,Object> map ) throws Exception;
	
} 
