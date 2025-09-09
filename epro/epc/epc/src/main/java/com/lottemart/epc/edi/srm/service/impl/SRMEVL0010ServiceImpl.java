package com.lottemart.epc.edi.srm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.srm.dao.SRMEVL0010Dao;
import com.lottemart.epc.edi.srm.model.SRMEVL0010VO;
import com.lottemart.epc.edi.srm.service.SRMEVL0010Service;

/**
 * 품질경영평가 / 품질경영평가 로그인 ServiceImpl
 * 
 * @author SHIN SE JIN
 * @since 2016.07.08
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.08  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Service("srmevl0010Service")
public class SRMEVL0010ServiceImpl implements SRMEVL0010Service {

	@Autowired
	private SRMEVL0010Dao srmevl0010Dao;
	
	/**
	 * 품질평가로그인 기 등록 확인
	 */
	public SRMEVL0010VO selectSRMEVLLogin(SRMEVL0010VO vo) throws Exception {
		return srmevl0010Dao.selectSRMEVLLogin(vo);
	}
	
	/**
	 * 로그인 실패 시 카운트 증가
	 */
	public void updatePassCheckCnt(SRMEVL0010VO vo) throws Exception {
		srmevl0010Dao.updatePassCheckCnt(vo);
	}
	
	/**
	 * 로그인 성공 시 카운트 초기화
	 */
	public void updatePassCheckCntReset(SRMEVL0010VO vo) throws Exception {
		srmevl0010Dao.updatePassCheckCntReset(vo);
	}

}
