package com.lottemart.epc.edi.consult.service;
import java.util.List;

import com.lottemart.epc.common.model.EpcLoginVO;
import com.lottemart.epc.edi.consult.model.PEDMSCT0099VO;

public interface PEDMSCT0099Service {
	
	/**
	 * 사업자 List
	 * @param epcLoginVO
	 * @return
	 * @throws Exception
	 */
	public List<PEDMSCT0099VO> selectBmanNoZipList(EpcLoginVO epcLoginVO) throws Exception;
	
	/**
	 * 우편번호 검색
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	public List<PEDMSCT0099VO> selectZipList(PEDMSCT0099VO paramVO) throws Exception;
	
	/**
	 * 우편번호 저장
	 * @param paramVO
	 * @throws Exception
	 */
	public void saveZip(PEDMSCT0099VO paramVO) throws Exception;

}
