package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xlib.cmc.GridData;

import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.common.util.DataMap;

public interface PSCPPRD0020Service {
	
	/**
	 * 추천상품 목록
	 * @return PSCPPRD0020VO
	 * @throws Exception
	 */
	public List<PSCPPRD0020VO> selectPrdCommerceList(Map<String, String> paramMap) throws Exception;
	
	public List<DataMap> selectPrdCommerceCatCdList(Map<String, String> paramMap) throws Exception;

	public DataMap selectPrdCommerceCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추천할 상품 목록 총카운트
	 * @return int
	 * @throws Exception
	 */
	public int deletePrdCommerce(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추천상품 입력 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdCommerce(List<PSCPPRD0020VO> pscpprd0020VOList) throws Exception;
	                                  
	public DataMap selectPrdCommerceTemp(Map<String, String> paramMap) throws Exception;
	
	public DataMap prodCommerceUpdateInfo(Map<String, String> paramMap) throws Exception;

	public int updatePrdCommerce(GridData gdReq) throws Exception;
	
	public int updatePrdCommerceHist(HttpServletRequest request) throws Exception;	

	public int deletePrdCommerceHist(Map<String, String> paramMap) throws Exception;
	
	public int insertPrdCommerceHist(List<PSCPPRD0020VO> pscpprd0020VOList) throws Exception;

}

