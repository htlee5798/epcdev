package com.lottemart.epc.edi.consult.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PEDMSCT0005Service {
	public List selectVenCd(Map<String,Object> map ) throws Exception;
	public List alertPageInsertPageSelect(Map<String,Object> map ) throws Exception;
	public Integer ajaxEmailCk(Map<String,Object> map) throws Exception;
	public Integer ajaxEmailCkUP(Map<String,Object> map) throws Exception;
	public Integer ajaxCellCk(Map<String,Object> map) throws Exception;
	public Integer ajaxCellCkUP(Map<String,Object> map) throws Exception;
	public List ajaxVendor(Map<String,Object> map) throws Exception;
	public List ajaxVendorCK(Map<String,Object> map) throws Exception;
	public String alertPageInsert(Map<String,Object> map ) throws Exception;
	
	public HashMap alertPageUpdatePage(Map<String,Object> map ) throws Exception;
	public void alertPageUpdate(Map<String,Object> map ) throws Exception;
	public void alertPageDelete(Map<String,Object> map ) throws Exception;
	
}
