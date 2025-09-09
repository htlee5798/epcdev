package com.lottemart.epc.edi.comm.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lottemart.epc.edi.comm.model.ScheduledVO;
import com.lottemart.epc.edi.product.model.NEDMPRO0040VO;

/**
 * @Class Name : CommonService.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      		  	  수정자           		수정내용
 *  -------    		---------------    ---------------------------
 * 2025. 04. 09. 	 park jong gyu				최초생성
 *
 * </pre>
 */
public interface ScheduledService {
	
	/**
	 * 행사 마스터 데이터 계약 진행상태(계약완료) update!
	 * @throws Exception
	 */
	public void updateProdEvntDoc( ScheduledVO scheduledVO ) throws Exception;
	
	/**
	 * 행사 마스터 데이터 계약 진행상태(계약삭제 / 계약폐기) update!
	 * @param scheduledVO
	 * @throws Exception
	 */
	public void updateProdEvntDelDoc( ScheduledVO scheduledVO ) throws Exception;
	
	/**
	 * 원가변경요청 계약번호 업데이트
	 * @throws Exception
	 */
	public void updateProdChgCostDcNum(ScheduledVO scheduledVO) throws Exception;
	
	/**
	 * 신상품확정요청_BOS 미처리 건 ERP 자동발송
	 * @param nEDMPRO0040VO
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 */
	public Map<String,Object> updateImsiProductListFixAutoSend(NEDMPRO0040VO nEDMPRO0040VO, HttpServletRequest request) throws Exception;
	
	/**
	 * google cloud storage 이용해서 Parquet 파일 가져오기
	 * @param scheduledVO
	 * @throws Exception
	 */
	public void updateGcsGetParquet( ScheduledVO scheduledVO ) throws Exception;
	
	/**
	 * 휴일 및 공휴일 등록
	 * @param paramMap
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> insertTpcHoliday(Map<String, Object> paramMap) throws Exception;
}
