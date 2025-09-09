package com.lottemart.common.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseResponse implements Serializable {
	
	private static final long serialVersionUID = 4186101332651040886L;

	private Map<String, Object> data;
	
	private BaseError error;
	
	public BaseResponse(){
		
	}
	
	public BaseResponse(BaseError error){
		this.error = error;
	}
	
	public void putData(String key, Object value) {
		if(data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
	}

	public boolean getIsSucceed() {
		return this.getError() == null;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public BaseError getError() {
		return error;
	}

	public void setError(BaseError error) {
		this.error = error;
	}

}
