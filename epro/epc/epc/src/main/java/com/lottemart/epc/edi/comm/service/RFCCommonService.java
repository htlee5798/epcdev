package com.lottemart.epc.edi.comm.service;

import java.util.Map;

public interface RFCCommonService {

	/**
	 * RFC Function Call (No BatchId)
	 * @param proxyNm
	 * @param params
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> rfcCall(String proxyNm, String params,String adminId) throws Exception;
	
	/**
	 * RFC Function Call
	 * @param proxyNm
	 * @param params
	 * @param adminId
	 * @param batchId
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> rfcCall(String proxyNm, String params,String adminId, String batchId) throws Exception;
	
}
