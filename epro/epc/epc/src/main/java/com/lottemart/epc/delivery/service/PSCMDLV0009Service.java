package com.lottemart.epc.delivery.service;

import java.util.List;

import com.lottemart.common.util.DataMap;
import com.lottemart.epc.delivery.model.PSCMDLV0009VO;

public interface PSCMDLV0009Service {

	public List<DataMap> getTetCodeList(PSCMDLV0009VO vo) throws Exception;

	public List<DataMap> selectPartnerReturnList(DataMap paramMap) throws Exception;

	public int insertTsaBatchLog2(PSCMDLV0009VO vo) throws Exception;

	public int updateTorOrderItem(List<PSCMDLV0009VO> orderItemList) throws Exception;

	public int updateRtnComplate(DataMap paramMap) throws Exception;

}
