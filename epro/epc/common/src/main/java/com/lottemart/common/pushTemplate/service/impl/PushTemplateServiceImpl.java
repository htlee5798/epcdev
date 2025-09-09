package com.lottemart.common.pushTemplate.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.lottemart.common.pushTemplate.service.PushTemplateService;

@Service("pushTemplateService")
public class PushTemplateServiceImpl implements PushTemplateService{
	
	/**
	 * BOS 알림톡 단건 발송테스트 저장
	 */
	@Override
	public int insertTemplateOneSendInsert(HttpServletRequest request) throws Exception {
		return 0;
	}

}
