package com.lottemart.epc.edi.payment.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface PEDMPAY0020Service {
	
	public List selectCredLedInfo(Map<String,Object> map ) throws Exception;
	public void createTextCredLed(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectCredLedStoreDetail(Map<String,Object> map ) throws Exception;
	public void createTextCredLedStoreDetail(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectCredLedStoreInfo(Map<String,Object> map ) throws Exception;
	public void createTextCredLedStore(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectFamilyLoan(Map<String,Object> map ) throws Exception;
	public void createTextFamilyLoan(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	
}


