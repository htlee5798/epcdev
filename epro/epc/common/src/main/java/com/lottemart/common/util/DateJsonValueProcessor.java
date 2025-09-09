package com.lottemart.common.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {

	private final SimpleDateFormat transFormat;

	public DateJsonValueProcessor() {
		transFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.KOREA);
	}

	@Override
	public Object processArrayValue(Object param, JsonConfig config) {
		return param == null ? "" : transFormat.format(param);
	}

	@Override
	public Object processObjectValue(String pString, Object param, JsonConfig config) {
		return processArrayValue(param, config);
	}

}