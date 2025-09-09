package com.lottemart.epc.edi.srm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.epc.edi.srm.dao.SRMJON0010Dao;
import com.lottemart.epc.edi.srm.model.SRMJON0010VO;
import com.lottemart.epc.edi.srm.service.SRMJON0010Service;

/**
 * 입점상담 / 입점상담신청  / 개인정보 수집 동의 ServiceImpl
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
@Service("srmjon0010Service")
public class SRMJON0010ServiceImpl implements SRMJON0010Service {
	
	@Autowired
	private SRMJON0010Dao srmjon0010Dao;

	/**
	 * 정보동의 insert
	 */
	public void insertCounselInfo(SRMJON0010VO vo) throws Exception {
		srmjon0010Dao.insertCounselInfo(vo);
	}
}
