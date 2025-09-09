package com.lottemart.epc.etc.service;

import com.lottemart.epc.etc.model.PSCMETC0001VO;

public interface PSCPETC0002Service {

	public PSCMETC0001VO selectCodePopup(PSCMETC0001VO vo) throws Exception;
	
	public void insertCodePopup(PSCMETC0001VO vo) throws Exception;

	public void updateCodePopup(PSCMETC0001VO vo) throws Exception;

	public void deleteCodePopup(PSCMETC0001VO vo) throws Exception;

}
