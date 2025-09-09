package com.lottemart.epc.edi.imgagong.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.imgagong.model.NEDMIGG0010VO;

/**
 * @Class Name : NEDMIGG0010Service
 * @Description : 임가공 출고 관리 Service
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 * 수정일			수정자          		수정내용
 * ----------	-----------		---------------------------
 * 2018.11.20	SHIN SE JIN		최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public interface NEDMIGG0010Service {
	
	/**
	 * 임가공 입고 처리
	 * @param NEDMIGG0010VO, HttpServletRequest
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> imgagongGrDataSave(NEDMIGG0010VO paramVO, HttpServletRequest request) throws Exception;
}

