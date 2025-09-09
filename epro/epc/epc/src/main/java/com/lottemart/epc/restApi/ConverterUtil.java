package com.lottemart.epc.restApi;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottemart.epc.product.service.impl.PSCPPRD0019ServiceImpl;

public class ConverterUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConverterUtil.class);
	/**
	 * @함수명 : ConverObjectToMap
	 * @작성일 : 2019. 9. 25.
	 * @작성자 : hadh
	 * @설명 : VO, DTO같은 객체를 맵으로 변경
	 *
	 * @param obj
	 * @return
	 */
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
			logger.debug("error : " + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.debug("error : " + e.getMessage());
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
}
