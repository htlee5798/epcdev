package com.lottemart.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lcn.module.framework.property.ConfigurationService;

import org.apache.commons.lang.StringUtils;
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
@Repository("commonUrlSupport")
public class CommonUrlSupport {

	private static final Logger logger = LoggerFactory.getLogger(CommonUrlSupport.class);

	@Autowired
	private ConfigurationService configs;
	
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

			while (true) {
				inputLine = in.readLine();
				if (inputLine != null) {
					response.append(inputLine);
				} else {
					break;
				}
			}
			in.close();

			String jsonData = response.toString();
			
			logger.debug("callURL =>" + obj.getPath() + "result=>"+jsonData);
			
			resultMap.put("jsonData", jsonData);
			
		} else {
			resultMap.put("jsonData", "");
		}
		return resultMap;
	}
	
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

			while (true) {
				inputLine = in.readLine();
				if (inputLine != null) {
					response.append(inputLine);
				} else {
					break;
				}
			}
			in.close();

			String jsonData = response.toString();
			logger.debug("callURL =>" + obj.getPath() + "result=>"+jsonData);
			
			resultMap.put("jsonData", jsonData);
		} else {
			resultMap.put("jsonData", "");
		}
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Object, Object> sendPOSTJson(String prodCd) throws Exception {
		Map<Object, Object> resultMap = new HashMap<Object, Object>();
		
		URL obj = new URL( configs.getString("imageqs.request.url") );
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		
		Map<Object, Object> size = new HashMap<Object, Object>();
		Map<Object, Object> param = new HashMap<Object, Object>();
		ArrayList<Map<Object, Object>> sizeList = new ArrayList<Map<Object, Object>>();
		
		size.put("target", "800x0");
		size.put("unit", "0x300");
		sizeList.add(size);
		
		// L로 시작하는 상품 코드 때문에 발란스가 안맞아서 5자리/5자리 짤라서 폴더링 함! (다른 곳에서는 5자리로 자름)
		// ex) L000001427576_1 --> /L0000/01427/L000001427576_1
		String subDir1 = StringUtils.left(prodCd, 5);
		String subDir2 = StringUtils.mid(prodCd, 5, 5);

		param.put("saveName", prodCd+".jpg");
		param.put("display", "pc");
		param.put("size", sizeList );
		param.put("baseUrl", configs.getString("imageqc.root.url")+"/snapshot/product/" + subDir1 + "/" + subDir2 + "/");
		param.put("callback", "http://127.0.0.1:8080/imageqs-callback/callback.jsp");
		param.put("extra", "productCode="+prodCd);
		param.put("sourceUrl", configs.getString("lottemart.pc.domain") + "/iframe/ProductDesc.do?ProductCD="+prodCd);
		param.put("savePath", configs.getString("imageqc.save.root.dir")+"/snapshot/product/" + subDir1 + "/" + subDir2);
		param.put("quality", "100");

		JSONObject jObj = new JSONObject();
		jObj.put("request", param);
		
		String jsonDatas = jObj.toString();
		logger.debug( jsonDatas );
		// For POST only - START
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");
		
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(jsonDatas);
		wr.flush();
		
		int responseCode = con.getResponseCode();
		logger.debug( "POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while (true) {
				inputLine = in.readLine();
				if (inputLine != null) {
					response.append(inputLine);
				} else {
					break;
				}
			}
			in.close();

			// print result
			logger.debug( response.toString());
			String jsonData = response.toString();
			
			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			
			Long code = (Long) jsonObject.get("code");
			String status = (String) jsonObject.get("status");
			String message = (String) jsonObject.get("message");
			
			resultMap.put("result", "true");
			resultMap.put("code", code);
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		} else {
			resultMap.put("result", "false");
		}
		return resultMap;
	}
}
