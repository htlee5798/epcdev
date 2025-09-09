package com.lottemart.epc.edi.consult.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PEDMSCT0004Service {


	
	public List estimationMainSelect(Map<String,Object> map ) throws Exception;
	public HashMap estimationMainSelectDetailTop(String pid ) throws Exception;
	public List estimationMainSelectDetailBottom(String pid ) throws Exception;
	
	public void estimationMainDelete(String pid ) throws Exception;
	public void estimationMainDetailDelete(String pid ) throws Exception;
	
	public void estimationUpdate(Map<String,Object> map,String pid) throws Exception;
}
