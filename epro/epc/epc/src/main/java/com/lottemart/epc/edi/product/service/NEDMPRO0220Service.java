package com.lottemart.epc.edi.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.product.model.NEDMPRO0220VO;
import com.lottemart.epc.edi.product.model.NEDMPRO0221VO;


/**
 * @Class Name : NEDMPRO0220Service
 * @Description : 물류바코드 등록 SERVICE
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	2015.11.30		SONG MIN KYO	최초생성
 * </pre>
 */

public interface NEDMPRO0220Service {	
	
	/**
	 *  임시보관함에서 바코드 등록을 위한 기본 정보 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public HashMap selectNewBarcodeRegistTmp(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 임시보관함에서 바코드 등록을 위한 등록된 상품의 속성 리스트 카운트
	 * @param pgmId
	 * @return
	 * @throws Exception
	 */
	public int selectImsiProdClassListCnt(String pgmId) throws Exception;
	
	
	/**
	 * [임시보관함 바코드 등록 팝업] 해당상품의 등록된 속성이 있을경우 등록된 속성의 물류바코드 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectImsiProdClassToLogiBcdInfo(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * [임시보관함] 신상품 물류바코드 저장
	 * @param nEDMPRO0220VO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertImsiProdLogiBcd(NEDMPRO0220VO nEDMPRO0220VO, HttpServletRequest request) throws Exception;
	
	/**
	 * 믈류바코드 중복체크 카운트[TPC_LOGI_BCD TABLE 활용]
	 * @param nEDMPRO0220VO
	 * @return
	 * @throws Exception
	 */
	public int selectLogiBcdDuplChkCnt(NEDMPRO0220VO nEDMPRO0220VO) throws Exception;
	
	
	
	////////////////////////////////
	
	public HashMap selectBarCodeProductInfo(NEDMPRO0221VO searchParam);
	
	public List selectBarcodeList(NEDMPRO0221VO searchParam);
	
	//public void insertNewBarcode(NEDMPRO0221VO nedmpro0221VO) throws Exception;
	
	/**
	 * 물류바코드 등록
	 * @param vo
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	public String insertNewbarcode(NEDMPRO0220VO vo, HttpServletRequest request) throws Exception;
	
	
	/**
	 * 신상품등록현황 조회에서 해당 변형속성이 있는 상품의 물류바코드 등록시 해당 변형순번의 변형명칭 조회
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public String selectVarAttNmInfo(Map<String, Object> paramMap) throws Exception;	
} 
