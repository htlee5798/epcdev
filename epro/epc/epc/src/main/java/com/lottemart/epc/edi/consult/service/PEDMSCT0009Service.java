package com.lottemart.epc.edi.consult.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PEDMSCT0009Service {
	public List orderStopSelect(Map<String,Object> map ) throws Exception;
	public void orderStopInsert(String val) throws Exception;

}
