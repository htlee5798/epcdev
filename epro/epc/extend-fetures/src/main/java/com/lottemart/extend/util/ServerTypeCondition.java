package com.lottemart.extend.util;

import java.util.Map;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * 서버 타입 평가 (대소문 무시)
 * @author pat
 *
 */
public class ServerTypeCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object>	attributes;
		String				serverType;
		String[]			targetTypes;
		
		attributes	= metadata.getAnnotationAttributes("com.lottemart.extend.annontation.ServerType");
		targetTypes	= (String[])attributes.get("value");
		serverType	= System.getProperty("server.type");
		
		if (StringUtils.isEmpty(serverType)) {
			if (targetTypes == null) {
				return true;
			}
			for (String targetType : targetTypes) {
				if (StringUtils.isEmpty(targetType)) {
					return true;
				}
			}
			return false;
		}
		
		if (targetTypes == null) {
			return false;
		}
		for (String targetType : targetTypes) {
			if (serverType.equalsIgnoreCase(targetType)) {
				return true;
			}
		}
		return false;
	}
}
