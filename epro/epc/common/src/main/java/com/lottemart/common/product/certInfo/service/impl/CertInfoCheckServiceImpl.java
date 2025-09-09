package com.lottemart.common.product.certInfo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottemart.common.product.certInfo.service.CertInfoCheckService;
import com.lottemart.common.util.RestAPIUtil;
import com.lottemart.common.util.RestConst;

/**
 * 
 * @Class Name : CertInfoCheckServiceImpl.java
 * @Description : KC인증 정보 검증 (BO API CALL) 
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 	
 *               </pre>
 */
@Service("certInfoCheckService")
public class CertInfoCheckServiceImpl implements CertInfoCheckService {

	private static final Logger logger = LoggerFactory.getLogger(CertInfoCheckService.class);
	
	@Override
	public Map<String, Object> certInfoCheckTransfer(Map<String, Object> condition) throws Exception {

		Map<String, Object> rtnMap = null;
		try {
			RestAPIUtil rest = new RestAPIUtil();
			String jsonString = rest.sendRestCall(RestConst.ECOLIFE, HttpMethod.POST, condition, RestConst.PD_API_TIME_OUT, true);
			//logger.debug("API CALL RESULT = " + jsonString);
			ObjectMapper mapper = new ObjectMapper();

			if (jsonString != null && StringUtils.isNotEmpty(jsonString)) {
				rtnMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
			} else {
				throw new NullPointerException("Json 결과 문자열이 없습니다.");
			}

		} catch (Exception e) {
			logger.error("error message --> " + e.getMessage());
			rtnMap = new HashMap<String, Object>();
			rtnMap.put("returnCode", "8888");
		}
		return rtnMap;
	}

}
