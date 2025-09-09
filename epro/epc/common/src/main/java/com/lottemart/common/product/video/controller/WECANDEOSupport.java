package com.lottemart.common.product.video.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import lcn.module.framework.property.ConfigurationService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 *  
 * @Class Name : WECANDEOSupport.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일          수정자	     수정내용
 *  -------         --------    ---------------------------
 * 2016. 7. 13. 오전 10:49:32   hyunjin
 * 
 * @Copyright (C) 2016 ~ 2016 lottemart All right reserved.
 */
@Repository("wecandeoSupport")
public class WECANDEOSupport {

	private static final Logger logger = LoggerFactory.getLogger(WECANDEOSupport.class);
	
	@Autowired
	private ConfigurationService configs;
	
	private String uploadUrl = "";
	private String uploadCancelUrl = "";
	private String adUploadUrl = "";
	private String errorCode = "";
	private String errorMessage = "";
	private String token = "";
	
	@SuppressWarnings("unused")
	public Map<Object, Object> sendGet(String URL) throws Exception{
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		/* URL 설정*/
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();		
		
		resultMap.put("CODE", responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while (true) {//(inputLine = in.readLine()) != null
				inputLine = in.readLine();
				if(inputLine != null) {
				response.append(inputLine);
				}else {
					break;
				}
			}
			in.close();

			String jsonData = response.toString();
			
			logger.debug("callURL =>" + obj.getPath() + " result=>"+jsonData);
			
			resultMap.put("jsonData", jsonData);
			
		} else {
			resultMap.put("jsonData", "");
		}
		return resultMap;
	}
	
	@SuppressWarnings("unused")
	public Map<Object, Object> sendPost(String URL, String Parameter) throws Exception{
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		/* URL 설정*/
		URL obj = new URL(URL);
		logger.debug("callURL ====================>" + obj.getPath());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write((Parameter).getBytes());
		os.flush();
		os.close();
		// For POST only - END
		
		int responseCode = con.getResponseCode();		
		
		resultMap.put("CODE", responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while (true) {//(inputLine = in.readLine()) != null
				inputLine = in.readLine();
				if(inputLine != null) {
				response.append(inputLine);
				}else {
					break;
				}
			}
			in.close();

			String jsonData = response.toString();
			logger.debug("callURL =>" + obj.getPath() + " result=>"+jsonData);
			
			resultMap.put("jsonData", jsonData);
		} else {
			resultMap.put("jsonData", "");
		}
		return resultMap;
	}
	
	@SuppressWarnings("unused")
	public Map<Object, Object> uploadState() throws Exception{
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		/* URL 설정*/
		URL obj = new URL(this.uploadUrl+"/status.json?token="+this.token);
		logger.debug("callURL ====================>" + obj.getPath());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();		
		
		resultMap.put("CODE", responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while (true) {//(inputLine = in.readLine()) != null
				inputLine = in.readLine();
				if(inputLine != null) {
				response.append(inputLine);
				}else {
					break;
				}
			}
			in.close();

			// print result
			String jsonData = response.toString();
			logger.debug("callURL =>" + obj.getPath() + " result=>"+jsonData);
			
			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			
			String state = (String) jsonObject.get("status");
			
			resultMap.put("state", state);
			resultMap.put("jsonData", jsonData);
		} else {
			resultMap.put("jsonData", "");
		}
		return resultMap;
	}
	
	@SuppressWarnings("unused")
	public Map<Object, Object> uploadProcess() throws Exception{
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		/* URL 설정*/
		URL obj = new URL(this.uploadUrl+"/uploadStatus.json?token="+this.token);
		logger.debug("callURL ====================>" + obj.getPath());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();		
		
		resultMap.put("CODE", responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while (true) {//(inputLine = in.readLine()) != null
				inputLine = in.readLine();
				if(inputLine != null) {
				response.append(inputLine);
				}else {
					break;
				}
			}
			in.close();
			
			// print result
			String jsonData = response.toString();
			logger.debug("callURL =>" + obj.getPath() + " result=>"+jsonData);
			
			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			
			String process = (String) jsonObject.get("process");
			Long readKByte = (Long) jsonObject.get("readKByte");
			Long chunkedReadByte = (Long) jsonObject.get("chunkedReadByte");
			Long readByte = (Long) jsonObject.get("readByte");
			
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put("process", process);
			resultData.put("readKByte", readKByte);
			resultData.put("chunkedReadByte", chunkedReadByte);
			resultData.put("readByte", readByte);
			
			resultMap.put("processMap", resultData);
			resultMap.put("jsonData", jsonData);
		} else {
			resultMap.put("jsonData", "");
		}
		return resultMap;
	}

	@SuppressWarnings("unused")
	public Map<Object, Object> getUpdateToken() throws Exception{
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		String URL = "http://api.wecandeo.com/web/v4/uploadToken.json?key="+configs.getString("wecandeo.licenseKey");
		/* URL 설정*/
		URL obj = new URL(URL);
		logger.debug("callURL ====================>" + obj.getPath());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();		
		
		resultMap.put("CODE", responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while (true) {//(inputLine = in.readLine()) != null
				inputLine = in.readLine();
				if(inputLine != null) {
				response.append(inputLine);
				}else {
					break;
				}
			}
			in.close();

			String jsonData = response.toString();
			logger.debug("callURL =>" + obj.getPath() + " result=>"+jsonData);
			
			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			
			Map<Object, Object> uploadInfo = new HashMap<Object, Object>();
			uploadInfo = (Map<Object, Object>) jsonObject.get("uploadInfo");
			
			String uploadUrl = (String) uploadInfo.get("uploadUrl");
			String uploadCancelUrl = (String) uploadInfo.get("uploadCancelUrl");
			String adUploadUrl = (String) uploadInfo.get("adUploadUrl");
			String thumbnailUploadUrl = (String) uploadInfo.get("thumbnailUploadUrl");
			
			Map<Object, Object> errorInfo = new HashMap<Object, Object>();
			errorInfo = (Map<Object, Object>) uploadInfo.get("errorInfo");
			String errorCode = (String) errorInfo.get("errorCode");
			String errorMessage = (String) errorInfo.get("errorMessage");
			
			String token = (String) uploadInfo.get("token");
			
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put("uploadUrl", uploadUrl);
			resultData.put("uploadCancelUrl", uploadCancelUrl);
			resultData.put("adUploadUrl", adUploadUrl);
			resultData.put("thumbnailUploadUrl", thumbnailUploadUrl);
			resultData.put("errorCode", errorCode);
			resultData.put("errorMessage", errorMessage);
			resultData.put("token", token);
			
			resultMap.put("dataMap", resultData);
			resultMap.put("jsonData", jsonData);
		} else {
			resultMap.put("jsonData", "");
		}
		return resultMap;
	}
	
	@SuppressWarnings("unused")
	public void setData(Map<Object, Object> paramMap){
		this.uploadUrl       = (String) paramMap.get("uploadUrl");
		this.uploadCancelUrl = (String) paramMap.get("uploadCancelUrl");
		this.adUploadUrl     = (String) paramMap.get("adUploadUrl");
		this.errorCode       = (String) paramMap.get("errorCode");
		this.errorMessage    = (String) paramMap.get("errorMessage");
		this.token           = (String) paramMap.get("token");
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public String getUploadCancelUrl() {
		return uploadCancelUrl;
	}

	public String getAdUploadUrl() {
		return adUploadUrl;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getToken() {
		return token;
	}
	
}
