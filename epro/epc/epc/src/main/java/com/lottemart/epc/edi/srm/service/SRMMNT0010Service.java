package com.lottemart.epc.edi.srm.service;

import com.lottemart.epc.edi.srm.model.SRMMNT0010VO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 대표자 SRM 모니터링 Service
 *
 * @author LEE HYOUNG TAK
 * @since 2016.08.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.08.25  	LEE HYOUNG TAK		 최초 생성
 *
 * </pre>
 */
public interface SRMMNT0010Service {
	/**
	 * CEO로그인 및 MAX로그인수 조회
	 * @param  vo
	 * @return SRMMNT0010VO
	 * @throws Exception
	 */
	public HashMap<String, Object> selectCEOSRMmoniteringLogin(SRMMNT0010VO vo, HttpServletRequest request) throws Exception;
}
