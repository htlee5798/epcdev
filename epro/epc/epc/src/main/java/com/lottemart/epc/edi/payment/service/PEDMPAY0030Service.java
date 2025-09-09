package com.lottemart.epc.edi.payment.service;
import java.util.List;
import java.util.Map;


public interface PEDMPAY0030Service {
	
	public List selectPayCountData(Map<String,Object> map) throws Exception;
	public Integer selectPamentStayCount(Map<String,Object> map) throws Exception;
	public boolean sendExecuteCommand(Map<String,Object> map) throws Exception ;
	
}


