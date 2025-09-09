package com.lottemart.epc.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xlib.cmc.GridData;

import com.lottemart.epc.product.model.PSCPPRD0020VO;
import com.lottemart.common.util.DataMap;

public interface PSCPPRD0022Service {
	
	/**
	 * 추천상품 목록
	 * @return PSCPPRD0020VO
	 * @throws Exception
	 */
	public List<PSCPPRD0020VO> selectPrdCertList(Map<String, String> paramMap) throws Exception;
	
	public List<DataMap> selectPrdCertCatCdList(Map<String, String> paramMap) throws Exception;

	public DataMap selectPrdCertCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추천할 상품 목록 총카운트
	 * @return int
	 * @throws Exception
	 */
	public int deletePrdCert(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 추천상품 입력 처리
	 * @return int
	 * @throws Exception
	 */
	public int insertPrdCert(List<PSCPPRD0020VO> pscpprd0020VOList) throws Exception;
	                                  
	public DataMap selectPrdCertTemp(Map<String, String> paramMap) throws Exception;
	
	public DataMap prodCertUpdateInfo(Map<String, String> paramMap) throws Exception;

	public int updatePrdCert(HttpServletRequest request) throws Exception;	
}

