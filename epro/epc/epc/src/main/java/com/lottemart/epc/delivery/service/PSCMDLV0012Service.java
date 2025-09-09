package com.lottemart.epc.delivery.service;

import java.util.List;

import com.lottemart.common.util.DataMap;

public interface PSCMDLV0012Service {

	public List<DataMap> selectPartnerNoFirmsOrderList(DataMap param) throws Exception;

}
