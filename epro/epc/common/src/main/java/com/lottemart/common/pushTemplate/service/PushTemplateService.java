package com.lottemart.common.pushTemplate.service;

import javax.servlet.http.HttpServletRequest;

public interface PushTemplateService {
	
	/**
	 * BOS 알림톡 단건 발송 테스트 저장
	 * @param request
	 * @return
	 * @throws Exception
	 */
	int insertTemplateOneSendInsert(HttpServletRequest request) throws Exception;

}
