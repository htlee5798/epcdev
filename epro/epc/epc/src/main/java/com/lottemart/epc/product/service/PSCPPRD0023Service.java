package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.common.util.DataMap;

public interface PSCPPRD0023Service {
	
	/**
	 * 추가구성품 조회
	 * @param DataMap
	 * @return
	 * @throws Exception
	 */
	public List<DataMap> selectProdComponentList(DataMap paramMap) throws Exception;
	
	/**
	 * 추가구성품, 연관상품 등록
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int saveProdComponent(HttpServletRequest request) throws Exception;
}

