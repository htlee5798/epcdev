package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMJON0010VO;

/**
 * 입점상담 / 입점상담신청  / 개인정보 수집 동의 Service
 * 
 * @author SHIN SE JIN
 * @since 2016.07.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.06  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
public interface SRMJON0010Service {
	
	/**
	 * 정보동의 insert
	 * @param SRMJON0020VO
	 * @throws Exception
	 */
	public void insertCounselInfo(SRMJON0010VO vo) throws Exception;
	
}
