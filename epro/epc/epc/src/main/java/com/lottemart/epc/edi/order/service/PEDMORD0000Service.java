package com.lottemart.epc.edi.order.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface PEDMORD0000Service {
	
	public List selectPeriodInfo(Map<String,Object> map ) throws Exception;
	public void createTextPeriod(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List selectJunpyoInfo(Map<String,Object> map ) throws Exception;
	public void createTextJunpyo(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List selectJunpyoDetailInfo(Map<String,Object> map ) throws Exception;
	public void createTextJunpyoDetail(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectStoreInfo(Map<String,Object> map ) throws Exception;
	public void createTextStore(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectJunpyoDetailInfoPDC(Map<String,Object> map ) throws Exception;
	public void createTextJunpyoDetailPDC(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}

