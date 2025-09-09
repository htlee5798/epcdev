package com.lottemart.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterUtil {

	/**
	 * @함수명 : ConverObjectToMap
	 * @작성일 : 2019. 9. 25.
	 * @작성자 : hadh
	 * @설명 : VO, DTO같은 객체를 맵으로 변경
	 *
	 * @param obj
	 * @return
	 */
	
	final static Logger logger = LoggerFactory.getLogger(ConverterUtil.class);
	
	public static Map<String, Object> converVOToMap(Object obj) {
		try {
			// Field[] fields = obj.getClass().getFields(); //private field는 나오지
			// 않음.
			Field[] fields = obj.getClass().getDeclaredFields();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for (int i = 0; i <= fields.length - 1; i++) {
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(obj));
			}
			return resultMap;
		} catch (IllegalArgumentException e) {
			logger.error(e.toString());
		} catch (IllegalAccessException e) {
			logger.error(e.toString());
		}
		return null;
	}
	
	/**
	 * @함수명 : convertJsonToMap
	 * @작성일 : 2019. 12. 27.
	 * @작성자 : mz.uje
	 * @설명 : String형태의 json 데이터를 Map<String, String> 형태로 변경
	 *
	 * @param String
	 * @return Map<String, String>
	 */
	public static Map<String, String> convertJsonToMap(String jsonString) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> resultMap = mapper.readValue(jsonString, new TypeReference<Map<String, String>>() {});
		
		return resultMap;
	}
	
	/**
	 * @함수명 : convertJsonToMapObject
	 * @작성일 : 2020. 04. 13.
	 * @작성자 : jilee
	 * @설명 : String형태의 json 데이터를 Map<String, Object> 형태로 변경 
	 *
	 * @param String
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> convertJsonToMapObject(String jsonString) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
		
		return resultMap;
	}
}
