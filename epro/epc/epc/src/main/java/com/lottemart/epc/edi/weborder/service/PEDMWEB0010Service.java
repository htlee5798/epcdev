package com.lottemart.epc.edi.weborder.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedOrdProcess010VO;
import com.lottemart.epc.edi.weborder.model.TedPoOrdMstVO;


public interface PEDMWEB0010Service {
	
	public Map<String,Object> selectVenOrdInfo(SearchWebOrder swo , HttpServletRequest request) throws Exception;
	
	/**
	 * Desc : 점포별 발주 상세 펼침 조회
	 * @Method Name : StrCdOrdList
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectStrCdList(SearchWebOrder swo) throws Exception;
	
	/**
	 * Desc : 점포별 발주 삭제, 합계 update
	 * @Method Name : updateStrCd
	 * @param updateStrCd
	 * @return int
	 */
	public int updateStrCd(TedPoOrdMstVO vo,HttpServletRequest request) throws Exception;
	
	/**
	 * Desc : 점포별,상품 발주 삭제, 합계 update
	 * @Method Name : updateStrCd
	 * @param updateStrCd
	 * @return int
	 */
	public void updateStrCdProd(TedOrdProcess010VO vo,HttpServletRequest request) throws Exception;
	
	/**
	 * Desc : 점포별 발주상품 상세 조회
	 * @Method Name : selectVenOrdProdInfo
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectVenOrdProdInfo(SearchWebOrder swo ) throws Exception;


	/**
	 *  당일 점포,상품별 발주정보 변경
	 * @param TedOrdProcess010VO
	 * @param request
	 * @return void
	 * @throws SQLException
	 */
	public void updateStCdProd(TedOrdProcess010VO vo,HttpServletRequest request) throws Exception;
	
	
	

	/**
	 * TODAY 협력사코드별  발주자료 MD전송처리
	 * @Method Name : insertSendProdData
	 * @param SearchWebOrder
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> insertSendProdData(TedOrdProcess010VO vo, HttpServletRequest request)  throws Exception;
	

	


}

