package com.lottemart.common.util;

import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.lottemart.extend.util.MiscUtils;

public class DataMap extends HashMap<Object, Object> {

	private static final long serialVersionUID = 1568324898795L;

	public DataMap() {
	}

	public DataMap(Map<Object, Object> initializeValues) {
        super(initializeValues);
    }

	public DataMap(HttpServletRequest request) throws Exception {

		Map map = request.getParameterMap();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			String[] value = (String[])pairs.getValue();
			if (value.length > 1 ){
				setHashMapToArray(pairs.getKey(), value);
			} else {
				put(pairs.getKey(), JsonUtils.cleanXSS(value[0]));
			}
		}
	}

	public DataMap(ServletRequest request) throws Exception {

		Map map = request.getParameterMap();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			String[] value = (String[])pairs.getValue();
			if (value.length > 1 ){
				setHashMapToArray(pairs.getKey(), value);
			} else {
				put(pairs.getKey(), JsonUtils.cleanXSS(value[0]));
			}
		}
	}

	private void setHashMapToArray(Object key, String[] value) {
    	ArrayList al = new ArrayList();
    	for (String val: value){
    		al.add(JsonUtils.cleanXSS(val));
    	}

    	put(key, al);
    }

	public String getSafeString(String key) {
		return getString(key);
	}

	public String getSafeString(String key, int byteLength) {
		return getString(key, byteLength);
	}

	public String getString(String key){
		return getString(key, "");
	}

	public String getString(String key, int byteLength){
		return getString(key, byteLength, "");
	}

	public String getString(String key, int byteLength, String nullValue){
		return byteCut(getString(key, nullValue), byteLength);
	}

	public int getInt(String key){
		Integer returnValue = getIntObject(key);

		if(returnValue == null) return 0;
		else return returnValue.intValue();
	}

	public long getLong(String key){
		Long returnValue = getLongObject(key);

		if(returnValue == null) return 0;
		else return returnValue.longValue();
	}

	public float getFloat(String key){
		Float returnValue = getFloatObject(key);

		if(returnValue == null) return 0;
		else return returnValue.floatValue();
	}

	public double getDouble(String key){
		Double returnValue = getDoubleObject(key);

		if(returnValue == null) return 0;
		else return returnValue.doubleValue();
	}

	public String getString(String key, String nullValue){
		Object value = this.get(key);

		try{
			return value.toString();
		}
		catch(Exception e){
			return nullValue;
		}
	}

	public Integer getIntObject(String key){
		Object value = this.get(key);

		try{
			return Integer.valueOf(value.toString(), 10);
		}
		catch(Exception e){
			return null;
		}
	}

	public Long getLongObject(String key){
		Object value = this.get(key);

		try{
			return Long.valueOf(value.toString(), 10);
		}
		catch(Exception e){
			return null;
		}
	}

	public Float getFloatObject(String key){
		Object value = this.get(key);

		try{
			return Float.valueOf(value.toString());
		}
		catch(Exception e){
			return null;
		}
	}

	public Double getDoubleObject(String key){
		Object value = this.get(key);

		try{
			return Double.valueOf(value.toString());
		}
		catch(Exception e){
			return null;
		}
	}

	/**
	 * 문자열 처리
	 * 2008년 6월 03일
	 * 작성자: 한상협
	 * <br>
	 * 문자열을 Byte단위로 짜른다.(UTF-8 환경에 맞추어서)
	 * DB 필드사이즈 오버방지용
	 *
	 * @param str - String 원본문자열
	 * @param bytelen - int 바이트 크기
	 * @return String
	*/
	public static String byteCut(String str, int bytelen){
		int i, lenCount=0;
		int strLen=str.length();

		for(i=0; i<strLen && lenCount<=bytelen ;i++){
			try{
				lenCount+=String.valueOf(str.charAt(i)).getBytes("UTF-8").length;
			}
			catch(UnsupportedEncodingException e){
				return str.substring(0, bytelen);
			}
		}

		if(lenCount>bytelen)
			return str.substring(0,i-1);
		else
			return str;
	}

	// @4UP 추가 CLOB 유입시 자동으로 String 변환
	@Override
	public Object put(Object key, Object value) {
		return super.put(key, (value instanceof Clob) ? MiscUtils.getGuessClob2String(value) : value);
	}

	// @4UP 추가 CLOB 유입시 자동으로 String 변환
	@Override
	public void putAll(Map<? extends Object, ? extends Object> m) {
		m.entrySet().forEach(ent -> DataMap.this.put(ent.getKey(), ent.getValue()));
	}
	
}
