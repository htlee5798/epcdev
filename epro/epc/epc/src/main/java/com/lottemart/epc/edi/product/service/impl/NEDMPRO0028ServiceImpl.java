package com.lottemart.epc.edi.product.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lcnjf.util.DateUtil;
import com.lcnjf.util.StringUtil;
import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.model.CommonFileVO;
import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.comm.service.RFCCommonService;
import com.lottemart.epc.edi.product.controller.NEDMPRO0020Controller;
import com.lottemart.epc.edi.product.dao.NEDMPRO0028Dao;
import com.lottemart.epc.edi.product.model.NEDMPRO0028VO;
import com.lottemart.epc.edi.product.service.NEDMPRO0028Service;

/**
 * 
 * @Class Name : NEDMPRO0028ServiceImpl.java
 * @Description : 온오프상품등록 > ESG Tab 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 
 *               </pre>
 */
@Service("nedmpro0028Service")
public class NEDMPRO0028ServiceImpl implements NEDMPRO0028Service {
	
	private static final Logger logger = LoggerFactory.getLogger(NEDMPRO0020Controller.class);
	
	@Autowired
	private NEDMPRO0028Dao nedmpro0028Dao;
	
	@Autowired
	private RFCCommonService rfcCommonService;
	
	/**
	 * ESG 등록 항목 조회
	 */
	public HashMap<String,Object> selectNewProdEsgList(NEDMPRO0028VO paramVo) throws Exception {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
		List<NEDMPRO0028VO> list = new ArrayList<NEDMPRO0028VO>();				//리스트 조회 결과 Vo
		
		int totalCount = 0;
		
		// ESG 등록 항목 카운터 조회
		totalCount = nedmpro0028Dao.selectNewProdEsgListCount(paramVo);

		if(totalCount > 0){	// ESG 등록 항목 리스트 조회
			list = nedmpro0028Dao.selectNewProdEsgList(paramVo);
		}
		
		returnMap.put("list", list);							//리스트 데이터
		returnMap.put("totalCount", totalCount);				//조회 결과 카운터
		
		return returnMap;
	}
	
	
	/** 
	 * 대분류(유형) 조회
	 */
	public List<NEDMPRO0028VO> selectEsgMstlList(NEDMPRO0028VO paramVo) throws Exception {
		return nedmpro0028Dao.selectEsgMstlList(paramVo);
	}
	
	/** 
	 * 중분류(인증유형) 조회
	 */
	public List<NEDMPRO0028VO> selectEsgMstMList(NEDMPRO0028VO paramVo) throws Exception {
		return nedmpro0028Dao.selectEsgMstMList(paramVo);
	}
	
	/** 
	 * 소분류(인증상세유형) 조회
	 */
	public List<NEDMPRO0028VO> selectEsgMstSList(NEDMPRO0028VO paramVo) throws Exception {
		return nedmpro0028Dao.selectEsgMstSList(paramVo);
	}
	
	
	/**
	 * ESG 항목 등록( ESG 상품 - 적용 )
	 */
	public HashMap<String,Object> insertNewProdEsg(MultipartHttpServletRequest  multipartRequest, NEDMPRO0028VO paramVo, HttpServletRequest request) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
        String uploadFilePath = ConfigUtils.getString("edi.prod.file.path");	//ESG 파일 업로드 경로
        
        //파일확장자 제한
        String fileExtLimitStr = ConfigUtils.getString("fileCheck.atchFile.ext.product.esg");
        String[] fileExtLimit = (fileExtLimitStr == null || "".equals(fileExtLimitStr))? null : fileExtLimitStr.split("\\|");
        List<String> extLimit = (fileExtLimit == null)? null : Arrays.asList(fileExtLimit); 
        
        //파일사이즈 제한
        long fileSizeLimit = Long.valueOf(ConfigUtils.getString("fileCheck.atchFile.size"));
        
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        MultipartFile mFile;
        
        Map<String,MultipartFile> multipartMap = new HashMap<String,MultipartFile>();
        List<MultipartFile> file = multipartRequest.getFiles("files");
        int ListSize = file.size();
        for(int i=0; i<ListSize; i++) {
        	mFile = file.get(i);
			multipartMap.put(String.valueOf(i), mFile);
		}
        
        // files.entrySet()의 요소를 읽어온다.
        Iterator < Entry < String, MultipartFile >> itr = multipartMap.entrySet().iterator();
        
        // 파일명이 중복되었을 경우, 사용할 스트링 객체
        String keyId = "";			// 첨부파일 ID
        String ordFileName = ""; 	// 파일명
        String fileExt = "";		// 확장자
        String saveName = "";		// 저장될 파일명
        String saveFilePath = "";	// 저장될 경로와 파일명
        String fileSize = "";		// 파일 사이즈
        int i = 0;
        boolean extChk = true;
        try {
        	
        	String jsonData = request.getParameter("jsonData");
			JSONArray jsonArray = new JSONArray( jsonData );
			
			//첫번재 row는 ESG 상품구분 마스터 데이터
			JSONObject mstObj = jsonArray.getJSONObject(0);
//			String workId = mstObj.getString("entpCd");
			
			NEDMPRO0028VO mstVo = new NEDMPRO0028VO();
			mstVo.setPgmId(mstObj.getString("pgmId"));
			mstVo.setEsgYn( mstObj.getString("esgYn") );
			mstVo.setEsgEarth( mstObj.getString("esgEarth") );
			mstVo.setWorkId(workId);
			
	        nedmpro0028Dao.updateNewProdReg(mstVo); // 상품마스터 update
			
			paramVo.setWorkId(workId);
			String pgmId, esgYn, esgEarth, delYn, ordFileNm, esgGbn, esgAuth, esgAuthDtl, esgFrDt, esgToDt;
			//두번째행부터 아이템 정보
			for(int j = 1; j< jsonArray.length(); j++) {
    			JSONObject obj = jsonArray.getJSONObject(j);
    			
    			pgmId = obj.has("pgmId")?obj.getString("pgmId"):"";
				esgYn = obj.has("esgYn")?obj.getString("esgYn"):"";
				esgEarth = obj.has("esgEarth")?obj.getString("esgEarth"):"";
				delYn = obj.has("delYn")?obj.getString("delYn"):"N";
				ordFileNm = obj.has("ordFileNm")?obj.getString("ordFileNm"):"";
				esgGbn = obj.has("esgGbn")?obj.getString("esgGbn"):"";
				esgAuth = obj.has("esgAuth")?obj.getString("esgAuth"):"";
				esgAuthDtl = obj.has("esgAuthDtl")?obj.getString("esgAuthDtl"):"";
				esgFrDt = obj.has("esgFrDt")?obj.getString("esgFrDt"):"";
				esgToDt = obj.has("esgToDt")?obj.getString("esgToDt"):"";
    			
    			paramVo.setPgmId(pgmId);
//				paramVo.setEsgYn( obj.getString("esgYn") );
//				paramVo.setEsgEarth( obj.getString("esgEarth") );
				paramVo.setDelYn(delYn);
				paramVo.setSaveEsgFileId( keyId );
				paramVo.setOrdFileNm(ordFileNm);
				paramVo.setEsgGbn(esgGbn);
				paramVo.setEsgAuth(esgAuth);
				paramVo.setEsgAuthDtl(esgAuthDtl);
				paramVo.setEsgFrDt(esgFrDt);
				paramVo.setEsgToDt(esgToDt);
    			if("".equals(ordFileNm)) {
    				nedmpro0028Dao.mergeNewProdEsg(paramVo);	// ESG 항목 등록 처리
    			}else {
    				Entry < String, MultipartFile > entry = itr.next();
    	            mFile = entry.getValue();
    	            
    	            if( "".equals( obj.getString("saveFileId") )) {
    					keyId = "ESG" + getTodayFormatYymmdd() + System.currentTimeMillis() + getRandomKey(2);
    				}else {
    					keyId = obj.getString("saveFileId");
    				}
    	            
    	            ordFileName = mFile.getOriginalFilename();
    				fileExt = ordFileName.substring(ordFileName.lastIndexOf(".") + 1);
    				
    				/***************************************/
    				if(!extLimit.contains(fileExt.toLowerCase())) {
    					extChk = false;
    					throw new IllegalArgumentException("업로드 불가능한 확장자입니다.");
    				}
    				/***************************************/
    				
//    				saveFilePath = uploadFilePath + "/" + year + "/" + month + "/" + day + "/";
    				saveFilePath = uploadFilePath+ "/" + pgmId + "/";
//    	            saveName = keyId + "." + fileExt;
    	            saveName = pgmId + "_" + esgGbn + "_" + esgAuth + "_" + esgAuthDtl + "." + fileExt;		//저장파일명 = 프로그램아이디_ESG유형_ESG인증유형_ESG인증유형상세
    				paramVo.setSaveEsgFileId( keyId );
    				
    				if(!"".equals( obj.getString("saveFileNm") )) {
    					//전체경로
    					String fullPath = saveFilePath + obj.getString("saveFileNm");
    					//전체경로 경로순회 문자열 및 종단문자 제거
    					fullPath = StringUtil.getCleanPath(fullPath, true);
    					
    					File serverSaveFile = new File(fullPath);
    					serverSaveFile.delete();
    				}
    	            
    	            // filePath에 해당되는 파일의 File 객체를 생성한다.
    	            File fileFolder = new File(StringUtil.getCleanPath(saveFilePath, true));
    	
    	            if (!fileFolder.exists()) {
    	                // 부모 폴더까지 포함하여 경로에 폴더를 만든다.
    	                if (fileFolder.mkdirs()) {
    	                    logger.info("[file.mkdirs] : Success");
    	                } else {
    	                    logger.error("[file.mkdirs] : Fail");
    	                }
    	            }
    	
    	            
    	          	//전체경로
					String saveFullPath = saveFilePath + saveName;
					//전체경로 경로순회 문자열 및 종단문자 제거
					saveFullPath = StringUtil.getCleanPath(saveFullPath, true);
    	            
    	            File saveFile = new File(saveFullPath);
    	            
                    mFile.transferTo(saveFile);
    	            
    	            long filesize = saveFile.length();
    				fileSize = Long.toString(filesize);
    				
    				// 파일 사이즈 체크
    		    	if(filesize > fileSizeLimit) {
    		    		throw new IllegalArgumentException("업로드 가능한 최대 용량을 초과하였습니다.");
    		    	}
    				
    				paramVo.setOrgFileNm( ordFileName );	// 파일명
    				paramVo.setSaveFileNm( saveName );	// 저장될 파일명
    				if( "".equals(fileSize) ) paramVo.setFileSize( null );	// 파일 사이즈
    				else paramVo.setFileSize( fileSize );		// 파일 사이즈
    				paramVo.setFilePath( saveFilePath );	// 파일경로
    				paramVo.setFileExt( fileExt );			// 파일확장자명
    				
    				nedmpro0028Dao.mergeProdEsgFile(paramVo);	// 첨부파일 등록
    	            
    				paramVo.setSaveEsgFileId( keyId );
    				paramVo.setOrdFileNm( obj.getString("ordFileNm") );
    				paramVo.setPgmId( obj.getString("pgmId") );
    				paramVo.setEsgGbn( obj.getString("esgGbn") );
    				paramVo.setEsgAuth( obj.getString("esgAuth") );
    				paramVo.setEsgAuthDtl( obj.getString("esgAuthDtl") );
    				paramVo.setEsgFrDt( obj.getString("esgFrDt") );
    				paramVo.setEsgToDt( obj.getString("esgToDt") );
    				nedmpro0028Dao.mergeNewProdEsg(paramVo);	// ESG 항목 등록 처리
    			}
    		}// end for

			returnMap.put("result", true);
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("errMsg", e.getMessage());
		} finally {
			returnMap.put("extChk", extChk);
		}
		
		return returnMap;
	}

	/**
	 * ESG 항목 등록( ESG 상품 - 미적용 )
	 */
	public HashMap<String,Object> updateNewProdEsg(NEDMPRO0028VO paramVo, HttpServletRequest request) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵

		try {
			
			String jsonData = request.getParameter("jsonData");
			JSONArray jsonArray = new JSONArray( jsonData );
			
			String pgmId, esgYn, esgEarth, delYn, ordFileNm, esgGbn, esgAuth, esgAuthDtl, esgFrDt, esgToDt;
//			String workId;
			
			for(int i = 0; i< jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				
				pgmId = obj.has("pgmId")?obj.getString("pgmId"):"";
				esgYn = obj.has("esgYn")?obj.getString("esgYn"):"";
				esgEarth = obj.has("esgEarth")?obj.getString("esgEarth"):"";
				delYn = obj.has("delYn")?obj.getString("delYn"):"Y";
				ordFileNm = obj.has("ordFileNm")?obj.getString("ordFileNm"):"";
				esgGbn = obj.has("esgGbn")?obj.getString("esgGbn"):"";
				esgAuth = obj.has("esgAuth")?obj.getString("esgAuth"):"";
				esgAuthDtl = obj.has("esgAuthDtl")?obj.getString("esgAuthDtl"):"";
				esgFrDt = obj.has("esgFrDt")?obj.getString("esgFrDt"):"";
				esgToDt = obj.has("esgToDt")?obj.getString("esgToDt"):"";
//				workId = obj.has("entpCd")?obj.getString("entpCd"):"";
				
				paramVo.setPgmId(pgmId);
				paramVo.setEsgYn(esgYn);
				paramVo.setEsgEarth(esgEarth);
				paramVo.setDelYn(delYn);
				paramVo.setWorkId(workId);
				paramVo.setPgmId(pgmId);
				paramVo.setOrdFileNm(ordFileNm);
				paramVo.setEsgGbn(esgGbn);
				paramVo.setEsgAuth(esgAuth);
				paramVo.setEsgAuthDtl(esgAuthDtl);
				paramVo.setEsgFrDt(esgFrDt);
				paramVo.setEsgToDt(esgToDt);
				nedmpro0028Dao.updateNewProdEsg(paramVo);	// ESG 항목 삭제 처리
			}
			
			nedmpro0028Dao.updateNewProdReg(paramVo); // 상품마스터 update
			
			returnMap.put("result", true);
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("errMsg", e.getMessage());
		}
		
		return returnMap;
	}
	
	/**
	 * ESG 항목 삭제
	 */
	public HashMap<String,Object> deleteNewProdEsg(NEDMPRO0028VO paramVo, HttpServletRequest request) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		int delOk = 0;
		
		/*
		 * 1. 필수데이터 확인 
		 */
		//삭제대상 list
		List<NEDMPRO0028VO> prodDataArr = paramVo.getProdDataArr();
		if(prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0) {
			result.put("errMsg", "삭제 대상을 선택해주세요.");
			return result;
		}
		
		/*
		 * 2. data validation
		 */
		String entpCd = StringUtils.defaultString(paramVo.getEntpCd());	//협력업체코드
//		String workId = entpCd;	//작업자코드
		
		String pgmId = "";			//신상품코드
		String esgGbn = "";			//ESG 유형
		String esgAuth = "";		//ESG 인증유형
		String esgAuthDtl = "";		//ESG 인증상세유형
		
		String delYn = "Y";			//삭제여부 Y
		
		for(NEDMPRO0028VO delVo : prodDataArr) {
			pgmId = StringUtils.defaultString(delVo.getPgmId());			//신상품코드
			esgGbn = StringUtils.defaultString(delVo.getEsgGbn());			//ESG 유형
			esgAuth = StringUtils.defaultString(delVo.getEsgAuth());		//ESG 인증유형
			esgAuthDtl = StringUtils.defaultString(delVo.getEsgAuthDtl());	//ESG 인증상세유형
			
			//신상품 코드 누락
			if("".equals(pgmId)) {
				result.put("errMsg", "신상품코드가 존재하지 않습니다.");
				return result;	
			}
			
			//유형코드 누락
			if("".equals(esgGbn)) {
				result.put("errMsg", "ESG 유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형 누락
			if("".equals(esgAuth)) {
				result.put("errMsg", "ESG 인증유형이 존재하지 않습니다.");
				return result;
			}
			//인증유형상세 누락
			if("".equals(esgAuthDtl)) {
				result.put("errMsg", "ESG 인증상세유형이 존재하지 않습니다.");
				return result;
			}
			
			//작업자 셋팅
			delVo.setWorkId(workId);
			delVo.setDelYn(delYn);		//삭제Flag setting
			nedmpro0028Dao.mergeNewProdEsg(delVo);	// ESG 항목 삭제 처리
			delOk ++;
		}
		
		result.put("msg", "success");
		result.put("okCnt", delOk);
		return result;
	}
	
	public String getTodayFormatYymmdd() throws Exception {
		StringBuffer sb = new StringBuffer();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		dateFormat.format(now, sb, new FieldPosition(0));
		return sb.toString();
	}
	
	public static String getRandomKey(int length) {
		String strReturn = "";
		if (length > 0) {
			strReturn = RandomStringUtils.random(length, 48, 91, true, true);
		}

	    return strReturn;
	 }
	
	/**
	 * 다운로드 파일 조회
	 */
	public CommonFileVO selectProdEsgFile(CommonFileVO paramVo) throws Exception {
		return nedmpro0028Dao.selectProdEsgFile(paramVo);
	}
	
	public HashMap<String,Object> insertNewProdEsg2(MultipartHttpServletRequest  multipartRequest, NEDMPRO0028VO paramVo, HttpServletRequest request) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		paramVo.setWorkId(workId);
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();		//반환할 맵
        String uploadFilePath = ConfigUtils.getString("edi.prod.file.path");
        
        //파일확장자 제한
        String fileExtLimitStr = ConfigUtils.getString("fileCheck.atchFile.ext.product.esg");
        String[] fileExtLimit = (fileExtLimitStr == null || "".equals(fileExtLimitStr))? null : fileExtLimitStr.split("\\|");
        List<String> extLimit = (fileExtLimit == null)? null : Arrays.asList(fileExtLimit); 
        
        //파일사이즈 제한
        long fileSizeLimit = Long.valueOf(ConfigUtils.getString("fileCheck.atchFile.size"));
        
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        MultipartFile mFile;
        
        Map<String,MultipartFile> multipartMap = new HashMap<String,MultipartFile>();
        List<MultipartFile> file = multipartRequest.getFiles("files");
        int ListSize = file.size();
        for(int i=0; i<ListSize; i++) {
        	mFile = file.get(i);
			multipartMap.put(String.valueOf(i), mFile);
		}
        
        // files.entrySet()의 요소를 읽어온다.
        Iterator < Entry < String, MultipartFile >> itr = multipartMap.entrySet().iterator();
        
        // 파일명이 중복되었을 경우, 사용할 스트링 객체
        String keyId = "";			// 첨부파일 ID
        String ordFileName = ""; 	// 파일명
        String fileExt = "";		// 확장자
        String saveName = "";		// 저장될 파일명
        String saveFilePath = "";	// 저장될 경로와 파일명
        String fileSize = "";		// 파일 사이즈
        int i = 0;
        boolean extChk = true;
        try {
        	
        	String jsonData = request.getParameter("jsonData");
			JSONArray jsonArray = new JSONArray( jsonData );
			
			//첫번재 row는 ESG 상품구분 마스터 데이터
			JSONObject mstObj = jsonArray.getJSONObject(0);
//			String workId = mstObj.getString("entpCd");
			
			NEDMPRO0028VO mstVo = new NEDMPRO0028VO();
			mstVo.setPgmId(mstObj.getString("pgmId"));
			mstVo.setEsgYn( mstObj.getString("esgYn") );
			mstVo.setEsgEarth( mstObj.getString("esgEarth") );
			mstVo.setWorkId(workId);
			
	        nedmpro0028Dao.updateNewProdReg(mstVo); // 상품마스터 update
			
			paramVo.setWorkId(workId);
			String pgmId, esgYn, esgEarth, delYn, ordFileNm, esgGbn, esgAuth, esgAuthDtl, esgFrDt, esgToDt;
			//두번째행부터 아이템 정보
			for(int j = 1; j< jsonArray.length(); j++) {
    			JSONObject obj = jsonArray.getJSONObject(j);
    			
    			pgmId = obj.has("pgmId")?obj.getString("pgmId"):"";
				esgYn = obj.has("esgYn")?obj.getString("esgYn"):"";
				esgEarth = obj.has("esgEarth")?obj.getString("esgEarth"):"";
				delYn = obj.has("delYn")?obj.getString("delYn"):"N";
				ordFileNm = obj.has("ordFileNm")?obj.getString("ordFileNm"):"";
				esgGbn = obj.has("esgGbn")?obj.getString("esgGbn"):"";
				esgAuth = obj.has("esgAuth")?obj.getString("esgAuth"):"";
				esgAuthDtl = obj.has("esgAuthDtl")?obj.getString("esgAuthDtl"):"";
				esgFrDt = obj.has("esgFrDt")?obj.getString("esgFrDt"):"";
				esgToDt = obj.has("esgToDt")?obj.getString("esgToDt"):"";
    			
    			paramVo.setPgmId(pgmId);
//				paramVo.setEsgYn( obj.getString("esgYn") );
//				paramVo.setEsgEarth( obj.getString("esgEarth") );
				paramVo.setDelYn(delYn);
				paramVo.setSaveEsgFileId( keyId );
				paramVo.setOrdFileNm(ordFileNm);
				paramVo.setEsgGbn(esgGbn);
				paramVo.setEsgAuth(esgAuth);
				paramVo.setEsgAuthDtl(esgAuthDtl);
				paramVo.setEsgFrDt(esgFrDt);
				paramVo.setEsgToDt(esgToDt);
    			if("".equals(ordFileNm)) {
    				nedmpro0028Dao.mergeNewProdEsg(paramVo);	// ESG 항목 등록 처리
    			}else {
    				Entry < String, MultipartFile > entry = itr.next();
    	            mFile = entry.getValue();
    	            
    	            if( "".equals( obj.getString("saveFileId") )) {
    					keyId = "ESG" + getTodayFormatYymmdd() + System.currentTimeMillis() + getRandomKey(2);
    				}else {
    					keyId = obj.getString("saveFileId");
    				}
    	            
    	            ordFileName = mFile.getOriginalFilename();
    				fileExt = ordFileName.substring(ordFileName.lastIndexOf(".") + 1);
    				
    				/***************************************/
    				if(!extLimit.contains(fileExt.toLowerCase())) {
    					extChk = false;
    					throw new IllegalArgumentException("업로드 불가능한 확장자입니다.");
    				}
    				/***************************************/
    				
    				
    				
//    				saveFilePath = uploadFilePath + "/" + year + "/" + month + "/" + day + "/";
    				saveFilePath = uploadFilePath+ "/" + pgmId + "/";
//    	            saveName = keyId + "." + fileExt;
    	            saveName = pgmId + "_" + esgGbn + "_" + esgAuth + "_" + esgAuthDtl + "." + fileExt;		//저장파일명 = 프로그램아이디_ESG유형_ESG인증유형_ESG인증유형상세
    				paramVo.setSaveEsgFileId( keyId );
    				
    				if(!"".equals( obj.getString("saveFileNm") )) {
    					//전체경로
    					String serverSaveFileFullPath = saveFilePath + obj.getString("saveFileNm");
    					//전체경로 경로순회 문자열 및 종단문자 제거
    					serverSaveFileFullPath = StringUtil.getCleanPath(serverSaveFileFullPath, true);
    					
    					File serverSaveFile = new File(serverSaveFileFullPath);
    					serverSaveFile.delete();
    				}
    	            
    	            // filePath에 해당되는 파일의 File 객체를 생성한다.
    	            File fileFolder = new File(StringUtil.getCleanPath(saveFilePath, true));
    	
    	            if (!fileFolder.exists()) {
    	                // 부모 폴더까지 포함하여 경로에 폴더를 만든다.
    	                if (fileFolder.mkdirs()) {
    	                    logger.info("[file.mkdirs] : Success");
    	                } else {
    	                    logger.error("[file.mkdirs] : Fail");
    	                }
    	            }
    	
    	            //전체경로
    	            String saveFileFullPath =  saveFilePath + saveName;
    	            //전체경로 경로순회 문자열 및 종단문자 제거
    	            saveFileFullPath = StringUtil.getCleanPath(saveFileFullPath, true);
    	            
    	            File saveFile = new File(saveFileFullPath);
    	            
                    mFile.transferTo(saveFile);
    	            
    	            long filesize = saveFile.length();
    				fileSize = Long.toString(filesize);
    				
    				// 파일 사이즈 체크
    		    	if(filesize > fileSizeLimit) {
    		    		throw new IllegalArgumentException("업로드 가능한 최대 용량을 초과하였습니다.");
    		    	}
    				
    				paramVo.setOrgFileNm( ordFileName );	// 파일명
    				paramVo.setSaveFileNm( saveName );	// 저장될 파일명
    				if( "".equals(fileSize) ) paramVo.setFileSize( null );	// 파일 사이즈
    				else paramVo.setFileSize( fileSize );		// 파일 사이즈
    				paramVo.setFilePath( saveFilePath );	// 파일경로
    				paramVo.setFileExt( fileExt );			// 파일확장자명
    				
    				nedmpro0028Dao.mergeProdEsgFile(paramVo);	// 첨부파일 등록
    	            
    				paramVo.setSaveEsgFileId( keyId );
    				paramVo.setOrdFileNm( obj.getString("ordFileNm") );
    				paramVo.setPgmId( obj.getString("pgmId") );
    				paramVo.setEsgGbn( obj.getString("esgGbn") );
    				paramVo.setEsgAuth( obj.getString("esgAuth") );
    				paramVo.setEsgAuthDtl( obj.getString("esgAuthDtl") );
    				paramVo.setEsgFrDt( obj.getString("esgFrDt") );
    				paramVo.setEsgToDt( obj.getString("esgToDt") );
    				nedmpro0028Dao.mergeNewProdEsg(paramVo);	// ESG 항목 등록 처리
    			}
    		}// end for

			returnMap.put("result", true);
			
		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			returnMap.put("result", false);
			returnMap.put("errMsg", e.getMessage());
		} finally {
			returnMap.put("extChk", extChk);
		}
		
		return returnMap;
	}
	
	/**
	 * ESG 인증서첨부 (단건)
	 */
	public Map<String,Object> updateProdEsgFileInfo(NEDMPRO0028VO nEDMPRO0028VO, HttpServletRequest request) throws Exception{
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		nEDMPRO0028VO.setWorkId(workId);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		//파일확장자 제한
        String fileExtLimitStr = ConfigUtils.getString("fileCheck.atchFile.ext.product.esg");
        String[] fileExtLimit = (fileExtLimitStr == null || "".equals(fileExtLimitStr))? null : fileExtLimitStr.split("\\|");
        List<String> extLimit = (fileExtLimit == null)? null : Arrays.asList(fileExtLimit); 
        
        //파일사이즈 제한
        long fileSizeLimit = Long.valueOf(ConfigUtils.getString("fileCheck.atchFile.size"));
        
		
		/*
		 * 1. 필수데이터 확인
		 */
		MultipartFile mFile = nEDMPRO0028VO.getEsgFile();		//ESG 첨부파일
		//첨부파일누락
		if(mFile == null) {
			result.put("errMsg", "업로드 파일 정보가 존재하지 않습니다.");
			return result;
		}
		
		//신상품코드 누락 (PGM_ID)
		String pgmId = StringUtils.defaultString(nEDMPRO0028VO.getPgmId());	//신상품코드
		if("".equals(pgmId)) {
			result.put("errMsg", "신상품코드가 존재하지 않습니다.");
			return result;
		}
		
//		String workId = StringUtils.defaultString(nEDMPRO0028VO.getWorkId());			//작업자아이디
		String esgGbn = StringUtils.defaultString(nEDMPRO0028VO.getEsgGbn());			//ESG유형
		String esgAuth = StringUtils.defaultString(nEDMPRO0028VO.getEsgAuth());			//ESG인증유형
		String esgAuthDtl = StringUtils.defaultString(nEDMPRO0028VO.getEsgAuthDtl());	//ESG인증상세유형
		
		/*
		 * 2. 파일업로드 진행
		 */
		String uploadFilePath = ConfigUtils.getString("edi.prod.file.path");		//ESG 파일업로드 ROOT PATH
		
		// 파일명이 중복되었을 경우, 사용할 스트링 객체
        String keyId = "";			// 첨부파일 ID
        String orgFileNm = ""; 		// 원본 파일명
        String fileExt = "";		// 확장자
        String saveFileNm = "";		// 저장될 파일명
        String saveFilePath = "";	// 저장될 경로
        String fullPath = "";		// 저장경로 + 파일명
        String fileSize = "";		// 파일 사이즈
        long fileSizeLong = 0;		// 첨부파일 사이즈 (long)
        
        keyId = StringUtils.defaultString(nEDMPRO0028VO.getEsgFileId());			//ESG 첨부파일 아이디
        
        /*** 서버에 등록된 파일 삭제 ***/
    	//기등록 첨부파일 조회
        NEDMPRO0028VO oldFileVo = nedmpro0028Dao.selectEsgFileInfo(nEDMPRO0028VO);
    	
    	//기등록 첨부파일 정보 존재 시,
    	if(oldFileVo != null) {
    		String oldFilePath = oldFileVo.getFilePath();		//기등록 파일업로드경로
    		String oldSaveFileNm = oldFileVo.getSaveFileNm();	//기등록 파일저장명
    		
    		//기등록파일 존재시, 파일 삭제
    		synchronized (this) {
                try {
                    File oldSaveFile = new File(oldFilePath+oldSaveFileNm);
                    if (oldSaveFile.exists()) {
                        if (!oldSaveFile.delete()) {
                            throw new IOException("파일 삭제 실패 : " + oldSaveFile.getPath());
                        } 
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    throw new IOException(e.getMessage());
                }
            }
    		//if(oldSaveFile.exists()) oldSaveFile.delete();
    	}
    	/*************************/
        
		//기등록 파일아이디가 없을경우, 새로생성
		if("".equals(keyId)) {
			keyId = "ESG" + getTodayFormatYymmdd() + System.currentTimeMillis() + getRandomKey(2);
		}
		
		orgFileNm = StringUtils.defaultString(mFile.getOriginalFilename());		//원본파일명
		orgFileNm = StringUtil.getCleanPath(orgFileNm, true);					//원본파일명-종단문자 및 경로순회문자열 제거
		
		fileExt = orgFileNm.substring(orgFileNm.lastIndexOf(".")+1);			//파일확장자명
		saveFilePath = uploadFilePath + File.separator + pgmId + File.separator;	//업로드 파일 path 
		saveFileNm = pgmId+"_"+esgGbn+"_"+esgAuth+"_"+esgAuthDtl+"."+fileExt;		//저장파일명
		fileSizeLong = mFile.getSize();	 				//파일사이즈(long)
    	fileSize = Long.toString(fileSizeLong);			//파일사이즈(string)
    	
    	// 파일 사이즈 없을 경우 exception
    	if(fileSizeLong == 0) {
    		throw new IllegalArgumentException("파일 내용이 없습니다.\n파일명:"+orgFileNm);
    	}
    	
    	// 파일 사이즈 체크
    	if(fileSizeLong > fileSizeLimit) {
    		throw new IllegalArgumentException("업로드 가능한 최대 용량을 초과하였습니다.\n파일명:"+orgFileNm);
    	}
    	
    	// 확장자 체크
    	if(!extLimit.contains(fileExt.toLowerCase())) {
    		throw new IllegalArgumentException("업로드 불가능한 확장자입니다.\n파일명:"+orgFileNm);
    	}
		
		// filePath에 해당되는 파일의 File 객체를 생성한다.
        File fileFolder = new File(saveFilePath);

        if (!fileFolder.exists()) {
            // 부모 폴더까지 포함하여 경로에 폴더를 만든다.
            if (fileFolder.mkdirs()) {
                logger.info("[file.mkdirs] : Success");
            } else {
                logger.error("[file.mkdirs] : Fail");
            }
        }
		
        //업로드 전체경로
        fullPath = saveFilePath + saveFileNm;
        //전체경로 경로순회 문자열 및 종단문자 제거
        fullPath = StringUtil.getCleanPath(fullPath, true);
		
        //파일업로드
        FileOutputStream fileStream = null;
        try {
        	fileStream = new FileOutputStream(fullPath);
        	FileCopyUtils.copy(mFile.getInputStream(), fileStream);
        }catch(Exception e) {
        	File delFile	=	new File(fullPath);
        	if(delFile.exists()) delFile.delete();
        	throw new IllegalArgumentException("파일 업로드 중 오류가 발생했습니다.\n파일명:"+orgFileNm);
        }finally {
        	if(fileStream != null) fileStream.close();
        	logger.info("orgFileNm:"+orgFileNm+", fileSize:"+fileSize);
        }
        
        //파일정보 setting
        nEDMPRO0028VO.setEsgFileId(keyId);			//첨부파일아이디
        nEDMPRO0028VO.setOrgFileNm(orgFileNm);		//원본파일명
        nEDMPRO0028VO.setSaveFileNm(saveFileNm);	//저장파일명
        nEDMPRO0028VO.setFileSize(fileSize);		//파일사이즈
        nEDMPRO0028VO.setFileExt(fileExt);			//파일확장자명
        nEDMPRO0028VO.setFilePath(saveFilePath);	//파일경로
        nEDMPRO0028VO.setWorkId(workId);			//작업자
    	
        //파일정보 merge
        nedmpro0028Dao.mergeProdEsgFile(nEDMPRO0028VO);	// 첨부파일 등록
		
        result.put("msg", "success");
        result.put("esgFileId", keyId);		//첨부파일아이디 반환
		return result;
	}
	
	/**
	 * ESG 인증정보 저장 (파일 업로드 동시에 진행)
	 * @param nEDMPRO0028VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String, Object> updateNewProdEsgInfoWithFile(NEDMPRO0028VO nEDMPRO0028VO, HttpServletRequest request) throws Exception {
		//작업자정보 setting (로그인세션에서 추출)
		EpcLoginVO epcLoginVO = (EpcLoginVO)request.getSession().getAttribute(ConfigUtils.getString("lottemart.epc.session.key"));
		String workId = epcLoginVO.getLoginWorkId();
		nEDMPRO0028VO.setWorkId(workId);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msg", "fail");
		
		/*
		 * 1. 필수 데이터 확인
		 */
		String pgmId = StringUtils.defaultString(nEDMPRO0028VO.getPgmId());			//신상품코드
		String esgYn =  StringUtils.defaultString(nEDMPRO0028VO.getEsgYn());		//ESG 적용여부
		String esgEarth = StringUtils.defaultString(nEDMPRO0028VO.getEsgEarth());	//ESG RE:EARTH 적용여부
		String delYn = StringUtils.defaultString(nEDMPRO0028VO.getDelYn(), "N");	//삭제여부
		String entpCd = StringUtils.defaultString(nEDMPRO0028VO.getEntpCd());		//협력사코드
		boolean isReqData = ("1".equals(esgYn))?true:false;							//인증정보 필수입력 여부 - (esgYn = 1;적용)일 경우 필수
		
//		//작업자 setting
//		String workId = entpCd;				//작업자코드_협력사코드
//		nEDMPRO0028VO.setWorkId(workId);
		
		//신상품코드 누락
		if("".equals(pgmId)) {
			result.put("errMsg", "신상품코드 정보가 없습니다.");
			return result;
		}
		
		//ESG 적용여부 미선택
		if("".equals(esgYn)) {
			result.put("errMsg", "ESG 적용여부를 선택해주세요.");
			return result;
		}
		
		//RE:EARTH 로고 사용여부
		if("".equals(esgYn)) {
			result.put("errMsg", "RE:EARTH 로고 사용여부 선택해주세요.");
			return result;
		}
		
		//협력사코드 누락
		if("".equals(entpCd)) {
			result.put("errMsg", "파트너사 코드가 누락되었습니다.");
			return result;
		}
		
		//변경대상 data
		List<NEDMPRO0028VO> prodDataArr = nEDMPRO0028VO.getProdDataArr();
		
		//ESG 적용구분 - '1:적용'일 경우, data list 입력 필수
		if(isReqData && (prodDataArr == null || prodDataArr.isEmpty() || prodDataArr.size() == 0)) {
			result.put("errMsg", "ESG 적용대상일 경우, ESG 인증정보를 1개 이상 선택해주세요.");
			return result;
		}
		
		/*
		 * 2. 데이터 리스트 있을 경우, ESG 인증정보 리스트 validation
		 */
		String errMsg = "";
		
		//삭제대상 아니면서, data list 있을 경우, 각 데이터 validation
		if(!"Y".equals(delYn)&&prodDataArr != null && !prodDataArr.isEmpty() && prodDataArr.size() > 0) {
			String esgGbn = "";			//유형코드
			String esgAuth = "";		//인증유형
			String esgAuthDtl = "";		//인증유형상세
			String esgFrDt = "";		//인증시작일
			String esgToDt = "";		//인증종료일
			String esgDtFg = "";		//ESG 인증시작/종료일 필수 Flag
			String chkEsgDate = "";
			
			for(NEDMPRO0028VO vo : prodDataArr) {
				esgGbn = StringUtils.defaultString(vo.getEsgGbn());			//유형코드
				esgAuth = StringUtils.defaultString(vo.getEsgAuth());		//인증유형
				esgAuthDtl = StringUtils.defaultString(vo.getEsgAuthDtl());	//인증유형상세
				esgFrDt = StringUtils.defaultString(vo.getEsgFrDt());		//인증시작일
				esgToDt = StringUtils.defaultString(vo.getEsgToDt());		//인증종료일
				
				//유형코드 누락
				if("".equals(esgGbn)) {
					result.put("errMsg", "ESG 유형이 존재하지 않습니다.");
					return result;
				}
				//인증유형 누락
				if("".equals(esgAuth)) {
					result.put("errMsg", "ESG 인증유형이 존재하지 않습니다.");
					return result;
				}
				//인증유형상세 누락
				if("".equals(esgAuthDtl)) {
					result.put("errMsg", "ESG 인증상세유형이 존재하지 않습니다.");
					return result;
				}
				
				//인증기간 필수입력 대상인지 확인
				esgDtFg = nedmpro0028Dao.selectChkEsgDtFg(vo);
				
				//인증기간 필수입력 대상이거나, 시작/종료일 중 하나라도 입력했을 경우 체크 
				if("X".equalsIgnoreCase(esgDtFg) || !"".equals(esgFrDt) || !"".equals(esgToDt)) {
					chkEsgDate = getEsgDateChk(esgFrDt, esgToDt);
					switch(chkEsgDate) {
						case "OK": break;
						case "EMPTY_FR_DT":
							result.put("errMsg", "ESG 인증시작일을 입력해주세요.");
							return result;
						case "EMPTY_TO_DT":
							result.put("errMsg", "ESG 인증종료일을 입력해주세요.");
							return result;
						case "FR_GREATER_THAN_TO":
							result.put("errMsg", "인증시작일이 인증종료일보다 이후입니다.");
							return result;
						case "ERR":
						default:
							result.put("errMsg", "처리 중 오류가 발생했습니다.");
							return result;
					}
				}
			}
		}
		
		
		/*
		 * 3. 신상품마스터 update
		 */
		NEDMPRO0028VO mstVo = new NEDMPRO0028VO();
		mstVo.setPgmId(pgmId);
		mstVo.setEsgYn(esgYn);
		mstVo.setEsgEarth(esgEarth);
		mstVo.setWorkId(workId);
		nedmpro0028Dao.updateNewProdReg(mstVo); // 상품마스터 update
		
		/*
		 * 4. 인증정보 리스트 저장 
		 */
		//삭제 대상이 아니면서, 데이터 리스트 있을 경우
		if(!"Y".equals(delYn) && prodDataArr != null && !prodDataArr.isEmpty() && prodDataArr.size() > 0) {
			MultipartFile mFile = null;
			int tot = prodDataArr.size();
			int ok = 0;
			
			Map<String,Object> fileResult = null;
			for(NEDMPRO0028VO saveVo : prodDataArr) {
				saveVo.setWorkId(workId);	//작업자 setting
				saveVo.setPgmId(pgmId);		//신상품코드
				saveVo.setDelYn(delYn);		//삭제여부 setting
				
				/** 첨부파일 저장 **/
				mFile = saveVo.getEsgFile();
				if(mFile != null) {
					fileResult = updateProdEsgFileInfo(saveVo, request);
					if(!"success".equals(fileResult.get("msg"))) {
						result.put("errMsg", "파일 업로드 중 오류가 발생했습니다.");
						return result;
					}
					
					//esgFileId 셋팅
					saveVo.setEsgFileId(MapUtils.getString(fileResult, "esgFileId"));
				}
				/***************/
				//항목 update
				nedmpro0028Dao.mergeNewProdEsg(saveVo);
				ok++;
			}
		}else {
		//삭제 대상인경우, 삭제처리
			nedmpro0028Dao.updateNewProdEsg(nEDMPRO0028VO);
		}
		
		//항목삭제된 ESG 정보 제거
		int delUnusedCtg = nedmpro0028Dao.updateCtgDelEsgInfo(nEDMPRO0028VO);
		logger.info("Remove information about deleted esg category - delCnt:"+delUnusedCtg);
		
		result.put("msg", "success");
		return result;
	}
	
	/**
	 * ESG 인증일자 체크
	 * @param esgBfToDt
	 * @param esgAfFrDt
	 * @param esgAfToDt
	 * @return String
	 */
	private String getEsgDateChk(String esgFrDt, String esgToDt) {
		try {
			
			Date esgFrDate = ("".equals(StringUtils.defaultString(esgFrDt)))?null:DateUtil.string2Date(esgFrDt, "yyyyMMdd");	//인증시작일
			Date esgToDate = ("".equals(StringUtils.defaultString(esgToDt)))?null:DateUtil.string2Date(esgToDt, "yyyyMMdd");	//인증시작일
			
			if(esgFrDate == null) {
				return "EMPTY_FR_DT";
			}
			
			if(esgToDate == null) {
				return "EMPTY_TO_DT";
			}
			
			//인증 시작일이 인증 종료일 이후일 수 없음
			if(esgFrDate.after(esgToDate)) {
				return "FR_GREATER_THAN_TO";
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			return "ERR";
		}
		
		return "OK";
	}
}
