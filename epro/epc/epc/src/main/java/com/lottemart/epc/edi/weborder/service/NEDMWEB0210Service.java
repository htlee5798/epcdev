package com.lottemart.epc.edi.weborder.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.NEDMWEB0210VO;

/**
 * @Class Name : NEDMWEB0210Service
 * @Description : 발주승인관리 Service Class
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		수정자          		수정내용
 *  -------    --------    ---------------------------
 * 2015.12.08	O YEUN KWON	  최초생성
 * 
 * @Copyright (C) 2000 ~ 2015 lottemart All right reserved.
 * </pre>
 */

public interface NEDMWEB0210Service {
	
	public Map<String,Object> selectVenOrdInfo(NEDMWEB0210VO vo , HttpServletRequest request) throws Exception;
	
	/**
	 * Desc : 점포별 발주 상세 펼침 조회
	 * @Method Name : StrCdOrdList
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectStrCdList(NEDMWEB0210VO vo) throws Exception;
	
	/**
	 * Desc : 점포별 발주 삭제, 합계 update
	 * @Method Name : updateStrCd
	 * @param updateStrCd
	 * @return int
	 */
	public int updateStrCd(NEDMWEB0210VO vo,HttpServletRequest request) throws Exception;
	
	/**
	 * Desc : 점포별,상품 발주 삭제, 합계 update
	 * @Method Name : updateStrCd
	 * @param updateStrCd
	 * @return int
	 */
	public void updateStrCdProd(NEDMWEB0210VO vo,HttpServletRequest request) throws Exception;
	
	/**
	 * Desc : 점포별 발주상품 상세 조회
	 * @Method Name : selectVenOrdProdInfo
	 * @param SearchWebOrder
	 * @return Map<String,Object>
	 */
	public Map<String,Object> selectVenOrdProdInfo(NEDMWEB0210VO vo ) throws Exception;


	/**
	 *  당일 점포,상품별 발주정보 변경
	 * @param TedOrdProcess010VO
	 * @param request
	 * @return void
	 * @throws SQLException
	 */
	public void updateStCdProd(NEDMWEB0210VO vo,HttpServletRequest request) throws Exception;
	
	
	

	/**
	 * TODAY 협력사코드별  발주자료 MD전송처리
	 * @Method Name : insertSendProdData
	 * @param SearchWebOrder
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> insertSendProdData(NEDMWEB0210VO vo, HttpServletRequest request)  throws Exception;
	

	


}

