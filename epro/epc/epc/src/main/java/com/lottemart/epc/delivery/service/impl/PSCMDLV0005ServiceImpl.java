package com.lottemart.epc.delivery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.dao.PSCMDLV0005Dao;
import com.lottemart.epc.delivery.model.PSCMDLV0005VO;
import com.lottemart.epc.delivery.service.PSCMDLV0005Service;

@Service("PSCMDLV0005Service")
public class PSCMDLV0005ServiceImpl implements PSCMDLV0005Service {
	
	@Autowired
	private PSCMDLV0005Dao pscmdlv0005Dao;

	public List<DataMap> selectPartnerFirmsStatus_short(PSCMDLV0005VO vo) throws Exception {
		return pscmdlv0005Dao.selectPartnerFirmsStatus_short(vo);
	}
	
	public List<DataMap> selectPartnerFirmsStatus_holy(PSCMDLV0005VO vo) throws Exception {
		return pscmdlv0005Dao.selectPartnerFirmsStatus_holy(vo);
	}	

	public int selectTotalOrderCnt(PSCMDLV0005VO vo) throws Exception {
		return pscmdlv0005Dao.selectTotalOrderCnt(vo);
	}
	
	public List<DataMap> selectPartnerFirmsStatus_All(PSCMDLV0005VO vo) throws Exception {
		return pscmdlv0005Dao.selectPartnerFirmsStatus_All(vo);
	}	
}
