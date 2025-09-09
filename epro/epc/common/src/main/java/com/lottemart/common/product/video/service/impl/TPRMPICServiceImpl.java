package com.lottemart.common.product.video.service.impl;

import com.lottemart.common.product.video.controller.WECANDEOSupport;
import com.lottemart.common.product.video.dao.TPRMPICDao;
import com.lottemart.common.product.video.service.TPRMPICService;
import lcn.module.framework.property.ConfigurationService;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *  
 * @Class Name : TPRMPICServiceImpl.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일          수정자	     수정내용
 *  -------         --------    ---------------------------
 * 2016. 6. 15. 오후 3:25:08   hyunjin
 * 
 * @Copyright (C) 2016 ~ 2016 lottemart All right reserved.
 */
@Service("tprmpicService")
public class TPRMPICServiceImpl implements TPRMPICService {

	@Autowired
	private TPRMPICDao tprmpicDao;
	
	@Autowired
	private WECANDEOSupport WECANDEOSupport;
	
	@Autowired
	private ConfigurationService config;
	
	@Override
	public List<Map<Object, Object>> selectTPRMPICList(	Map<Object, Object> paramMap) throws Exception {
		return tprmpicDao.selectTPRMPICList(paramMap);
	}

	@Override
	public int updateTPRMPICList(Map<Object, Object> paramMap) throws Exception {

		int resultCnt = 0;
		Map<Object, Object> param = new HashMap<Object, Object>();
		
		// 마스터 정보 데이터
		String regId = (String) paramMap.get("regId");
		String modId = (String) paramMap.get("modId");
		String prodCd = (String) paramMap.get("prodCd");
		param.put("regId",     regId);
		param.put("modId",     modId);
		param.put("prodCd",    prodCd);
		
		
		// 상세 목록 데이터 리스트
		String[] mpicSeqArr        = (String[]) paramMap.get("mpicSeqArr");
		String[] accessKeyArr      = (String[]) paramMap.get("accessKeyArr");
		String[] useYnArr          = (String[]) paramMap.get("useYnArr");
		String[] cidArr            = (String[]) paramMap.get("cidArr");

		for( int i = 0; i < mpicSeqArr.length; i++ ){
			param.put("mpicSeq",  mpicSeqArr[i]);
			param.put("accessKey",  accessKeyArr[i]);
			param.put("useYn", useYnArr[i]);
			param.put("cid", cidArr[i]);
			// 선택된 값에 대해서 등록을 한다.
			resultCnt += tprmpicDao.updateTPRMPICinfo(param);
		}
		return resultCnt;
	}
	
	@Override
	public void updateTPRMPICinfo(Map<Object, Object> paramMap) throws Exception {

		Map<Object, Object> param = new HashMap<Object, Object>();
		
		// 마스터 정보 데이터
		String regId          = StringUtils.defaultString( (String) paramMap.get("regId"),"");
		String modId          = StringUtils.defaultString( (String) paramMap.get("modId"),"");
		String prodCd         = StringUtils.defaultString( (String) paramMap.get("prodCd"),"");
		String mpicSeq        = StringUtils.defaultString( (String) paramMap.get("mpicSeq"),"");
		String accessKey      = StringUtils.defaultString( (String) paramMap.get("accessKey"),"");
		String title          = StringUtils.defaultString( (String) paramMap.get("title"),"");
		String content        = StringUtils.defaultString( (String) paramMap.get("content"),"");
		
		String duration       = StringUtils.defaultString( (String) paramMap.get("duration"),"");
		String videoHeight    = StringUtils.defaultString( (String) paramMap.get("videoHeight"),"");
		String videoWidth     = StringUtils.defaultString( (String) paramMap.get("videoWidth"),"");
		String videoFramerate = StringUtils.defaultString( (String) paramMap.get("videoFramerate"),"");
		String cid            = StringUtils.defaultString( (String) paramMap.get("cid"),"");
		String status         = StringUtils.defaultString( (String) paramMap.get("status"),"");
		String mpicUrl        = StringUtils.defaultString( (String) paramMap.get("mpicUrl"),"");
		
		paramMap.put("useYn",              "Y");
		
		String useYn = (String) paramMap.get("useYn");
		param.put("regId",     regId);
		param.put("modId",     modId);
		param.put("prodCd",    prodCd);
		
		param.put("prodCd", prodCd); 
		param.put("mpicSeq", mpicSeq); 
		param.put("accessKey", accessKey); 
		param.put("title", title); 
		param.put("content", content); 
		param.put("useYn", useYn);
		
		param.put("duration", duration); 
		param.put("videoHeight", videoHeight); 
		param.put("videoWidth", videoWidth); 
		param.put("videoFramerate", videoFramerate); 
		param.put("cid", cid); 
		param.put("status", status); 
		param.put("mpicUrl", mpicUrl); 
		
		tprmpicDao.updateTPRMPICinfo(param);
	}

	public void updateTPRMPICinfo2(Map<Object, Object> paramMap) throws Exception {
		
		Map<Object, Object> param = new HashMap<Object, Object>();
		
		// 마스터 정보 데이터
		String regId = (String) paramMap.get("regId");
		String modId = (String) paramMap.get("modId");
		String prodCd = (String) paramMap.get("prodCd");
		String mpicSeq = (String) paramMap.get("mpicSeq");
		String accessKey = (String) paramMap.get("accessKey");
		String title = (String) paramMap.get("title");
		String content = (String) paramMap.get("content");
		String cid = (String) paramMap.get("cid");

		paramMap.put("useYn",              "Y");
		
		String useYn = (String) paramMap.get("useYn");
		param.put("regId",     regId);
		param.put("modId",     modId);
		param.put("prodCd",    prodCd);
		
		param.put("prodCd", prodCd); 
		param.put("mpicSeq", mpicSeq); 
		param.put("accessKey", accessKey); 
		param.put("title", title); 
		param.put("content", content); 
		param.put("useYn", useYn);
		param.put("cid", cid);
		
		tprmpicDao.updateTPRMPICinfo(param);
		
		String licenseKey = config.getString("wecandeo.licenseKey");
		String URL  = "http://api.wecandeo.com/info/v1/video/set/detail.json?key="+licenseKey;
		URL        += "&access_key="+accessKey;
		URL        += "&title="+ URLEncoder.encode(title,"UTF-8");
		URL        += "&content="+URLEncoder.encode(content,"UTF-8");
		
		String jsonData = (String) WECANDEOSupport.sendGet(URL).get("jsonData");
		
		JSONParser jsonParser = new JSONParser();
		
		JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
		
		String status = (String) jsonObject.get("status");
		if( !"Success".equals(status) ){
			new Exception("솔루션에서 제대로 수정되지 않았습니다.");
		}
	}
	
	public int insertTPRMPICinfo(Map<Object, Object> paramMap) throws Exception {
		return tprmpicDao.insertTPRMPICinfo(paramMap);
	}
	public Map<Object, Object> selectTPRMPICInfo(Map<Object, Object> paramMap) throws Exception{
		return tprmpicDao.selectTPRMPICinfo(paramMap);
	}
	
	public Map<Object, Object> selectTPRMPICInfo2(Map<Object, Object> paramMap) throws Exception{
		return tprmpicDao.selectTPRMPICInfo2(paramMap);
	}
	
}

