package com.lottemart.epc.edi.zip.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lottemart.bos.epc.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;
import com.lottemart.epc.edi.zip.dao.PEDPZIP0001Dao;
import com.lottemart.epc.edi.zip.service.PEDPZIP0001Service;




@Service("pedpzip0001Service")
public class PEDPZIP0001ServiceImpl implements PEDPZIP0001Service{
	@Autowired
	private PEDPZIP0001Dao pedpzip0001Dao;
	
	
	public List selectZipCodeList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return pedpzip0001Dao.selectZipCodeList(map);
	}	
	public List selectstreetCodeList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return pedpzip0001Dao.selectStreetCodeList(map);
	}	
		public List<EdiCommonCode> selectCityList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		
		return pedpzip0001Dao.selectCityList(searchParam);
	}	
	public List<EdiCommonCode> getSelectedGuNmList(SearchParam searchParam) {
		// TODO Auto-generated method stub
		
		return pedpzip0001Dao.getSelectedGuNmList(searchParam);
	}	
}
