package com.lottemart.epc.edi.main.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.main.model.MainDashBoardVO;

public interface MainDashBoardService {
	
	/**
	 * Main → 파트너사 실적 조회 
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectVenPrfrList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * Main → 파트너사 매입액 조회 
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectVenBuyList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * Main → 신상품 실적 조회 	
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectNewProdPrfrList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	
	/**
	 * Main → 신상품 입점 제안 조회 	
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectProdNewPropList(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * Main 원가변경요청 내역 및 상태 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectMainTpcProdChgCostItem(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * Main 데이터 일괄 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectMainDashBoardAll(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * Main 미납내역 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectMainNonPayItems(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * Main 파트너사별 SKU 현황 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectMainVenSku(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
	
	/**
	 * Main 입고 거부 상품 미조치 내역 조회
	 * @param paramVo
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> selectMainInboundRejects(MainDashBoardVO paramVo, HttpServletRequest request) throws Exception;
}

