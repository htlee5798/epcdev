package com.lottemart.epc.edi.consult.service;
import java.util.List;
import java.util.Map;

import com.lottemart.epc.edi.comm.model.EdiCommonCode;
import com.lottemart.epc.edi.comm.model.SearchParam;

public interface PEDMSCT0002Service {


	public List consultAdminSelect(Map<String,Object> map ) throws Exception;
	public void papeUpdate1(Map<String,Object> map ) throws Exception;
	public void papeUpdate2(Map<String,Object> map ) throws Exception;
	public void cnslUpdate1(Map<String,Object> map ) throws Exception;
	public void cnslUpdate2(Map<String,Object> map ) throws Exception;
	public void entshpUpdate1(Map<String,Object> map ) throws Exception;
	public void entshpUpdate2(Map<String,Object> map ) throws Exception;
	public List consultAdminSelectDetail(String str ) throws Exception;
	public List consultAdminSelectDetailPast(String str ) throws Exception;
	public void historyInsert(Map<String,Object> map ) throws Exception;
	public List<EdiCommonCode> selectDistinctTeamList();
	public List<EdiCommonCode> selectL1List(SearchParam searchParam);
	public List<EdiCommonCode> selectL1ListApply(SearchParam searchParam);
	
	

}

