package com.lottemart.epc.delivery.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0005VO;

public interface PSCMDLV0005Service {

	public List<DataMap> selectPartnerFirmsStatus_short(PSCMDLV0005VO vo) throws Exception;
	
	public List<DataMap> selectPartnerFirmsStatus_holy(PSCMDLV0005VO vo) throws Exception;
	
	public int selectTotalOrderCnt(PSCMDLV0005VO vo) throws Exception;
	
	public List<DataMap> selectPartnerFirmsStatus_All(PSCMDLV0005VO vo) throws Exception;
}
