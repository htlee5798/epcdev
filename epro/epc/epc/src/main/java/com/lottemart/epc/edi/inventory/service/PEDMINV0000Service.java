package com.lottemart.epc.edi.inventory.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lottemart.epc.edi.inventory.model.PEDMINV0000VO;


public interface PEDMINV0000Service {
	
	public List<PEDMINV0000VO> selectStoreInfo(Map<String,Object> map ) throws Exception;
	public void createTextPeriod(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List<PEDMINV0000VO> selectProductInfo(Map<String,Object> map ) throws Exception;
	public void createTextProduct(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void createTextProductText(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List<PEDMINV0000VO> selectCenterStoreInfo(Map<String,Object> map ) throws Exception;
	public void createTextCenterStore(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List<PEDMINV0000VO> selectCenterStoreDetailInfo(Map<String,Object> map ) throws Exception;
	public void createTextCenterStoreDetail(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response) throws Exception;

	
}

