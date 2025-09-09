package com.lottemart.epc.edi.sale.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.sale.model.PEDMSAL0000VO;


public interface PEDMSAL0000Service {
	
	public List<PEDMSAL0000VO> selectDayInfo(Map<String,Object> map ) throws Exception;
	public void createTextDay(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List<PEDMSAL0000VO> selectStoreInfo(Map<String,Object> map ) throws Exception;
	public void createTextStore(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List<PEDMSAL0000VO> selectProductInfo(Map<String,Object> map ) throws Exception;
	public void createTextProduct(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectProductDetailInfo(Map<String,Object> map ) throws Exception;
	public void createTextProductDetail(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
}

