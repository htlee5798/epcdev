package com.lottemart.epc.edi.srm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.srm.dao.SRMRST0010Dao;
import com.lottemart.epc.edi.srm.model.SRMRST0010VO;
import com.lottemart.epc.edi.srm.service.SRMRST0010Service;

/**
 * 입점상담 > 입점상담결과 확인 로그인 ServiceImpl
 * 
 * @author SHIN SE JIN
 * @since 2016.07.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      			수정자           		수정내용
 *  -----------    	------------    ------------------
 *   2016.07.25  	SHIN SE JIN		 최초 생성
 *
 * </pre>
 */
@Service("srmrst0010Service")
public class SRMRST0010ServiceImpl implements SRMRST0010Service {

	@Autowired
	private SRMRST0010Dao srmrst0010Dao;
	
	/**
	 * 비밀번호 체크
	 * @param SRMRST0010VO
	 * @return SRMRST0010VO
	 * @throws Exception
	 */
	public SRMRST0010VO selectPasswdCheck(SRMRST0010VO vo) throws Exception {
		return srmrst0010Dao.selectPasswdCheck(vo);
	}
	
}
