package com.lottemart.epc.edi.comm.service;
import java.util.HashMap;
import java.util.Map;

public interface sendEmsSmsService {
	
	public HashMap sendEMS(Map<String,Object> map ) throws Exception;
	public HashMap sendSMS(Map<String,Object> map ) throws Exception;
}
