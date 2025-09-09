package com.lottemart.epc.edi.zip.service;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;

public interface PEDPZIP0001Service {
	
	public List selectZipCodeList(Map<String,Object> map ) throws Exception;
	
	public List selectstreetCodeList(Map<String,Object> map ) throws Exception;
		
	public List<EdiCommonCode> selectCityList(SearchParam searchParam);	
	public List<EdiCommonCode> getSelectedGuNmList(SearchParam searchParam);
	
}

