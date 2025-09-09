package com.lottemart.epc.etc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lottemart.epc.etc.dao.PSCPETC0002Dao;
import com.lottemart.epc.etc.model.PSCMETC0001VO;
import com.lottemart.epc.etc.service.PSCPETC0002Service;

@Service("PSCPETC0002Service")
public class PSCPETC0002ServiceImpl implements PSCPETC0002Service {

	@Autowired
	private PSCPETC0002Dao samplePopupDao;
	
	public PSCMETC0001VO selectCodePopup(PSCMETC0001VO vo) throws Exception {
		return samplePopupDao.selectCodePopup(vo);
	}
	
	public void insertCodePopup(PSCMETC0001VO vo) throws Exception {
		samplePopupDao.insertCodePopup(vo);
	}

	public void updateCodePopup(PSCMETC0001VO vo) throws Exception {
		samplePopupDao.updateCodePopup(vo);
	}

	public void deleteCodePopup(PSCMETC0001VO vo) throws Exception {
		samplePopupDao.deleteCodePopup(vo);
	}
	
}
