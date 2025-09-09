package com.lottemart.epc.edi.comm.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcnjf.util.StringUtil;
import com.lottemart.common.exception.AlertException;
import com.lottemart.epc.common.util.EPCUtil;
import com.lottemart.epc.edi.comm.dao.EpcRestApiDao;
import com.lottemart.epc.edi.comm.service.EpcRestApiService;

/**
 * 
 * @Class Name : EpcRestApiServiceImpl.java
 * @Description : EPC REST API 공통  
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2025.06.05		yun				최초생성
 *               </pre>
 */
@Service(value="epcRestApiService")
public class EpcRestApiServiceImpl implements EpcRestApiService{
	
	private static final Logger logger = LoggerFactory.getLogger(EpcRestApiServiceImpl.class);
	private static final String OK_CODE = "S";
	private static final String NO_CODE = "F";
	
	@Autowired
	EpcRestApiDao epcRestApiDao;

	/**
	 * [RESTAPI > BOS] 신상품 승인상태 변경
	 */
	@Override
	public Map<String, Object> updateTpcNewProdStsCfrm(Map<String, Object> paramMap, HttpServletRequest req) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
		result.put("message", "처리 중 오류가 발생했습니다.");
		
		String pgmId = MapUtils.getString(paramMap, "PGM_ID");		//프로그램아이디(신상품코드)
		
		logger.info("epcRestApiService.updateTpcNewProdStsCfrm - PGM_ID :: " + pgmId);
		
		//신상품코드 없음
		if("".equals(pgmId)) {
			result.put("message", "신상품 코드가 누락되었습니다.");
			return result;
		}
		
		
		/* 1. 신상품등록 상태 확인 ---------------------- */
		Map<String,Object> prodStsMap = epcRestApiDao.selectNewProdRegSts(paramMap);
		if(prodStsMap == null || prodStsMap.isEmpty()) {
			result.put("message", "신상품등록 정보가 존재하지 않습니다.");
			return result;
		}
		
		String mdSendDivnCd		= MapUtils.getString(prodStsMap, "MD_SEND_DIVN_CD", "");		// MD전송구분
		String bosTgYn 			= MapUtils.getString(prodStsMap, "BOS_TG_YN", "");				// BOS 승인대상여부
		String bosCfrmDyAvailYn	= MapUtils.getString(prodStsMap, "BOS_CFRM_DY_AVAIL_YN", "");	// BOS 승인처리기간 유효여부
		String bosCfrmEndDy		= MapUtils.getString(prodStsMap, "BOS_CFRM_END_DY", "");		// BOS 승인마감일자
		String bosCfrmYn		= MapUtils.getString(prodStsMap, "BOS_CFRM_YN", "");			// BOS 승인여부
		String cfmFg			= MapUtils.getString(prodStsMap, "CFM_FG", "");					// 확정구분
		String autoSendFg		= MapUtils.getString(prodStsMap, "AUTO_SEND_FG", "");			// ERP 자동발송여부
		String autoSendDy		= MapUtils.getString(prodStsMap, "AUTO_SEND_DY", "");			// ERP 자동발송일자
		
		//전송되지 않은 상품
		if("".equals(mdSendDivnCd)) {
			result.put("message", "확정요청되지 않은 상품은 승인/반려 처리가 불가능합니다.");
			return result;
		}
		
		//BOS 승인처리 대상 상품이 아님
		if(!"Y".equals(bosTgYn)) {
			result.put("message", "BOS 승인/반려 대상이 아닙니다.");
			return result;
		}
		
		//이미 승인,반려 처리된 건
		if("Y".equals(bosCfrmYn)) {
			result.put("message", "이미 승인 또는 반려 처리된 상품입니다.");
			return result;
		}
		
		//BOS 승인마감일자가 지남
		if(!"Y".equals(bosCfrmDyAvailYn)) {
			result.put("message", "BOS 승인 마감기한이 만료된 상품입니다. (마감일자:"+bosCfrmEndDy+")");
			return result;
		}
		
		//ERP 자동발송여부 확인
		if("X".equalsIgnoreCase(autoSendFg)) {
			result.put("message", "이미 ERP 발송된 상품입니다. (발송일자:"+autoSendDy+")");
			return result;
		}
		
		//BOS, ERP 심사진행중 
		if(!"".equals(cfmFg) && "0".equals(cfmFg)) {	//이미 심사 진행중인 경우
			result.put("message", "이미 심사가 진행중이거나 완료된 상품입니다.");
			return result;
			
		}
		
		/* 2. BOS 승인,반려 처리결과 UPDATE	 ---------------------- */
		int updCfrm = 0;
		Map<String,Object> prodCfrmMap = MapUtils.getMap(paramMap, "NEW_PROD_STS_INFO");
		if(prodCfrmMap == null || prodCfrmMap.isEmpty()) {
			result.put("message", "승인/반려 처리정보가 존재하지 않습니다.");
			return result;
		}
		//BOS 승인 상태
		String prgsSts = MapUtils.getString(prodCfrmMap, "PRGS_STS");
		
		//온라인 확정일 경우,
		if("81".equals(prgsSts)) {
			String ospDpYn = paramMap.containsKey("OSP_DP_YN")?MapUtils.getString(paramMap, "OSP_DP_YN", ""):"";		//오카도 대표카테고리 여부
			String prodCd = paramMap.containsKey("PROD_CD")?MapUtils.getString(paramMap, "PROD_CD", ""):"";				//상품코드
			
			//추가 처리 항목 parameter
			Map<String,Object> prodCfrmExtMap = new HashMap<String,Object>(prodCfrmMap);
			prodCfrmExtMap.put("OSP_DP_YN", ospDpYn);
			prodCfrmExtMap.put("PROD_CD", prodCd);
			
			//소분류 맵핑되지 않는 그룹분석속성 제거
			epcRestApiDao.deleteNewProdGrpAtt(prodCfrmExtMap);
			
			//전상법 온라인 상품코드 update -- tpc_new_prod_reg 테이블 sell_cd 있을 경우에만 update
			epcRestApiDao.updateNewProdAddInfoVal(prodCfrmExtMap);
			
			//kc인증 온라인 상품코드 update -- tpc_new_prod_reg 테이블 sell_cd 있을 경우에만 update
			epcRestApiDao.updateNewProdCertInfoVal(prodCfrmExtMap);
			
			//오카도 전시카테고리 대표여부 update
			epcRestApiDao.updateTecOspCatProdMapping(prodCfrmExtMap);
		}
		
		//BOS 승인 상태 테이블 UPDATE
		updCfrm = epcRestApiDao.updateNewProdSts(prodCfrmMap);
		
		/* 3. MD 전송상태 구분 존재 시, UPDATE ---------------------- */
		int updMd = 0;
		Map<String,Object> mdSendDivnMap = MapUtils.getMap(paramMap, "NEW_PROD_MD_SEND_INFO");
		if(mdSendDivnMap != null && !mdSendDivnMap.isEmpty()) {
			//MD 전송상태 UPDATE
			updMd = epcRestApiDao.updateNewProdMdSendDivnCd(mdSendDivnMap);
		}
		
		result.put("msgCd", OK_CODE);
		result.put("message", "정상적으로 처리되었습니다.");
		
		return result;
	}

	/**
	 * [RESTAPI > 협력사로그인] 협력사 로그인 KEY발번
	 */
	@Override
	public Map<String, Object> updateMakeVendorLoginKey(Map<String, Object> paramMap, HttpServletRequest req) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("msgCd", NO_CODE);
		result.put("message", "로그인 키 생성 중 오류가 발생했습니다.");
		
		int reccnt = 0;	//영향받은 row 수
		
		/*
		 * 1. 데이터 validation
		 */
		//1-1) 암호화된 데이터 확인
		String encData	= MapUtils.getString(paramMap, "vendorInfo", "");	//암호화된 업체정보
		if("".equals(encData)) {
			result.put("message", "업체 정보가 존재하지 않습니다.");
			return result;
		}
		
		//1-2) 데이터 복호화
		byte[] encBytes = Base64.getDecoder().decode(encData.getBytes());
		String decData = new String(encBytes, StandardCharsets.UTF_8);
		
		String[] decDataArr = decData.split("_");	//사업자번호, 업체코드 분리
		
		//1-3) 복호화된 데이터 확인
		String bmanNoArrStr	= StringUtils.defaultString(decDataArr[0]);		//사업자번호 Array (;로구분)
		String venCd		= StringUtils.defaultString(decDataArr[1]);		//업체코드
		
		//사업자번호 누락
		if(null == bmanNoArrStr || bmanNoArrStr.length() < 10) {
			result.put("message", "올바르지 않은 사업자번호 입니다.");
			return result;
		}
		
		//업체코드 누락
		if("".equals(venCd)) {
			result.put("message", "올바르지 않은 업체코드 입니다.");
			return result;
		}
		
		//사업자번호 배열로 split
		String[] bmanNoArr = bmanNoArrStr.split(";");
		if(null == bmanNoArr || bmanNoArr.length < 1) {
			result.put("message", "사업자번호가 존재하지 않습니다.");
			return result;
		}
		
		//사업자번호 유효성체크
		for(String bmanNo : bmanNoArr) {
			//사업자번호 공백제거
			bmanNo = (bmanNo != null)? StringUtil.removeWhitespace(bmanNo) : "";
			
			//사업자번호 10자리가 아닌 경우
			if(bmanNo.length() != 10) {
				throw new AlertException("사업자번호의 길이가 올바르지 않습니다. (사업자번호 : "+bmanNo+")");
			}
		}
		
		/*
		 * 2. 사업자번호 유효성 체크
		 */
		//첫번째 사업자번호 -> lcn에서 일부 업체가 지점별로 사업자번호가 다른경우가 존재함 (부속사업자번호) ==> parameter로 넘어온 첫 번째 사업자번호를 기준으로 key 체크함
		String repBmanNo = bmanNoArr[0];
		
		Map<String,Object> venMap = new HashMap<String,Object>();
		venMap.put("VEN_CD", venCd);		//업체코드
		venMap.put("BMAN_NO", repBmanNo);	//사업자번호
		
		//EPC 협력사마스터(HQ_VEN)에 등록된 사업자 번호인지 체크
		int isEpcVendor = epcRestApiDao.selectChkVendorInHqVen(venMap);	
		
		//HQ_VEN에 없는 경우
		if(isEpcVendor == 0) {
			result.put("message", "등록되지 않은 사업자번호 입니다.");
			return result;
		}
		
		/*
		 * 3. 사업자번호 키 발번
		 */
		String loginKey = "";			//로그인용 key (랜덤 5자리 발번)
		String encLoginKey = "";		//인코딩된 로그인용 key
		
		//5자리 랜덤키 생성
		loginKey = StringUtil.getSecuredRandomKey(5);
		
		//랜덤키 생성 실패
		if("".equals(loginKey)) {
			result.put("message", "로그인 검증키 발번에 실패하였습니다.");
			return result;
		}
		
		//암호화된 랜덤키
		encLoginKey = EPCUtil.EncryptSHA256(loginKey);
		
		//parameter setting
		venMap.put("LOGIN_KEY", encLoginKey);	//로그인용 랜덤키 setting
		
		//협력사 로그인 key 등록 
		reccnt = epcRestApiDao.updateMakeVendorLoginKey(venMap);
		
		result.put("msgCd", OK_CODE);
		result.put("message", "정상적으로 처리되었습니다.");
		result.put("reccnt", reccnt);
		result.put("loginKey", encLoginKey);
		
		return result;
	}

}
