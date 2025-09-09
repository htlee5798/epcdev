package com.lottemart.epc.edi.delivery.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;


public interface PEDMDLY0000Service {
	
	public List selectStatusInfo(Map<String,Object> map ) throws Exception;
	public List selectDeliverAcceptInfo(Map<String,Object> map ) throws Exception;
	public void createTextDeliverAccept(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	public List selectDeliverRegInfo(Map<String,Object> map ) throws Exception;
	public void createTextDeliverReg(@RequestParam Map<String,Object> map,HttpServletRequest request, HttpServletResponse response ) throws Exception;
	
	public void updateDeliverAcceptInfo(String obj ) throws Exception;
	public void updateDeliverRegInfo(String obj, String obj2 ) throws Exception;
	
	public HashMap cancelDeliverAccept(Map<String,Object> map ) throws Exception;
}

