package com.lottemart.epc.edi.consult.service;
import java.util.List;
import java.util.Map;

public interface PEDMSCT0003Service {


	public void estimationInsert(Map<String,Object> map, String pid ) throws Exception;
	
	public void estimationSheetInsert(String pid,String obj ) throws Exception;
	
	public String estimationSelectCount(String kkk ) throws Exception;

}
