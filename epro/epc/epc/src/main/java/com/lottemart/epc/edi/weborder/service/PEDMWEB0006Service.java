package com.lottemart.epc.edi.weborder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.weborder.model.EdiRtnPackListVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnPackVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdProcessVO;
import com.lottemart.epc.edi.weborder.model.EdiRtnProdVO;
import com.lottemart.epc.edi.weborder.model.SearchWebOrder;
import com.lottemart.epc.edi.weborder.model.TedPoOrdPackVO;




public interface PEDMWEB0006Service {


	
	/**
     * 반품상품 저장
     * @param EdiRtnProdVO
     * @return HashMap<String, String> 
     * @throws Exception
    */
	public HashMap<String, String> insertReturnProdData(EdiRtnProdVO vo, HttpServletRequest request) throws Exception, RuntimeException;
	
	
	/**
	 *  당일 반품등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public Map<String,Object> selectDayRtnProdList(SearchWebOrder vo,HttpServletRequest request)  throws Exception ;
	
	
	/**
	 *  당일 반품등록 내역 조회(합계포함)
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public List<EdiRtnProdVO> selectDayRtnProdSumList(SearchWebOrder vo, HttpServletRequest request)  throws Exception ;
	
	
	/**
	 * TODAY 협력사 반품등록 삭제 리스트 처리
	 * @Method Name : deleteReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> deleteReturnProdData(EdiRtnProdProcessVO vo, HttpServletRequest request)  throws Exception;
	
	
	/**
	 * TODAY 협력사코드별  반품자료 MD전송처리
	 * @Method Name : sendReturnProdData
	 * @param EdiRtnProdProcessVO
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> insertSendReturnProdData(SearchWebOrder vo, HttpServletRequest request)  throws Exception;
	

	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 반품 등록 내역 조회
	 * @param SearchWebOrder
	 * @return List<EdiRtnProdVO>
	 * @throws SQLException
	 */
	public Map<String,Object> selectDayRtnList(SearchWebOrder vo,HttpServletRequest request)  throws Exception ;
	
	
	
	
	
	
	/**
	 * 반품 일괄 등록 저장
	 * @param EdiRtnPackListVO
	 * @return String
	 * @throws SQLException
	 */
	Map<String,Object> selectRtnPackInfo(SearchWebOrder vo);
	
	/**
	 * 반품 일괄 등록 저장
	 * @param EdiRtnPackListVO
	 * @return String
	 * @throws SQLException
	 */
	public String insertRtnPackInfo(EdiRtnPackListVO vo, HttpServletRequest request, String ordVenCd) throws Exception;
	
	/**
	 * 반품 일괄등록 정보 삭제
	 * @param EdiRtnPackVO
	 * @return String
	 * @throws SQLException
	 */
	public String deleteExcelRtnInfo (EdiRtnPackVO vo) throws Exception;
	
	/**
	 * 반품 데이터 저장 : MST, PROD
	 * @param EdiRtnPackVO
	 * @return String
	 * @throws SQLException
	 */
	public String insertExcelRtnInfo(SearchWebOrder vo, HttpServletRequest request) throws Exception;
	

}
