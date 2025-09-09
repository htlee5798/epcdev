package com.lottemart.common.product.certInfo.service;

import java.util.Map;

/**
 * KC인증 정보 검증 (BO API CALL)
 */
public interface CertInfoCheckService {

	public Map<String, Object> certInfoCheckTransfer(Map<String, Object> condition) throws Exception;

}
