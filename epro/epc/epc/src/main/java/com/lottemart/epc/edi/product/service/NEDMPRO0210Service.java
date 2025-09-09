package com.lottemart.epc.edi.product.service;

import java.util.List;
import java.util.Map;



public interface NEDMPRO0210Service {

	/**
	 * 물류바코드 관리 - > 물류바코드 현황 조회
	 * @param map
	 * @return
	 */
	public List selectBarcodeList(Map<String,Object> map);
}
