package com.lottemart.epc.edi.imgagong.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.imgagong.model.NEDMIGG0010VO;

/**
 * @Class Name : NEDMIGG0110Service
 * @Description : 임가공 입고 관리 Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 * 수정일			수정자          		수정내용
 * ----------	-----------		---------------------------
 * 2018.11.22	SHIN SE JIN		최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public interface NEDMIGG0110Service {
	
	/**
	 * 임가공 입고정보 삭제 처리
	 * @param NEDMIGG0010VO, HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> imgagongGrDataDelete(NEDMIGG0010VO paramVO, HttpServletRequest request) throws Exception;
	
}

