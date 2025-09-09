package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMVEN005001VO;
import com.lottemart.epc.edi.srm.model.SRMVEN0050VO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * SRM 모니터링 Service
 *
 * @author LEE SANG GU
 * @since 2018.11.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2018.11.05  	LEE SANG GU			 최초 생성
 *
 * </pre>
 */
public interface SRMVEN0050Service {
	
	/**
	 * SRM 성과평가 LIST
	 * @param SRMVEN0050VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */	
	public Map<String, Object> selectSrmEvalRes(SRMVEN0050VO vo,HttpServletRequest request) throws Exception;


	/**
	 * SRM 성과평가 상세조회 팝업
	 * @param SRMVEN0050VO
	 * @param HttpServletRequest
	 * @return Map<String,Object>
	 * @throws Exception
	 */	
	public List<SRMVEN005001VO> selectSrmEvalResDetailPopup(SRMVEN005001VO vo,HttpServletRequest request) throws Exception;

}
