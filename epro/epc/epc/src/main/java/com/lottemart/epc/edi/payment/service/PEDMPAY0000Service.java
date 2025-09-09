package com.lottemart.epc.edi.payment.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface PEDMPAY0000Service {
	
	public List selectCominforInfo(Map<String,Object> map ) throws Exception;
	public List selectPaymentDayInfo(Map<String,Object> map ) throws Exception;
	public void createTextPaymentDay(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectPaymentStoreInfo(Map<String,Object> map ) throws Exception;
	public void createTextPaymentStore(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectCredAggInfo(Map<String,Object> map ) throws Exception;
	public void createTextCredAgg(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectLogiAmtInfo(Map<String,Object> map ) throws Exception;
	public void createTextLogiAmt(Map<String,Object> map ,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectPromoSaleInfo(Map<String,Object> map ) throws Exception;
	public List selectPromoNewSaleInfo(Map<String,Object> map ) throws Exception;
	
}


