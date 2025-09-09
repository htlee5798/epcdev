package com.lottemart.epc.edi.consult.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lcn.module.common.util.HashBox;

public interface PEDMSCT0006Service {


	public HashBox selectConsultVendor(Map<String,Object> map ) throws Exception;
	public void updateConsultVendorPass(Map<String,Object> map) throws Exception;
	public void updateConsultVendorConsult(Map<String,Object> map) throws Exception;

}
