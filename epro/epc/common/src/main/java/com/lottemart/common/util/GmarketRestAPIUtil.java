package com.lottemart.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.google.gson.Gson;

import lcn.module.framework.property.PropertyService;

public class GmarketRestAPIUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(GmarketRestAPIUtil.class);

	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	/**
	 * @Method Name : sendRestCall
	 * @Date : 2021. 03. 05
	 * @Author : KSM
	 * @Description : Http Call
	 *
	 * @param apiAddress
	 * @param httpMethodType
	 * @param requestParamObj
	 * @return
	 * @throws Exception
	 */
	public String sendRestCall(String apiAddress, HttpMethod httpMethodType, Object requestParamObj) throws Exception {
		return sendRestCall(apiAddress, httpMethodType, requestParamObj, GmarketRestConst.READ_TIME_OUT, false);
	}

	/**
	 * @Method Name : sendRestCall
	 * @Date : 2021. 03. 05
	 * @Author : KSM
	 * @Description : Http Call
	 *
	 * @param apiAddress
	 * @param httpMethodType
	 * @param requestParamObj
	 * @return
	 * @throws Exception
	 */
	public String sendRestCall(String apiAddress, HttpMethod httpMethodType, Object requestParamObj, boolean isJSONContent) throws Exception {
		return sendRestCall(apiAddress, httpMethodType, requestParamObj, GmarketRestConst.READ_TIME_OUT, isJSONContent);
	}

	/**
	 * @Method Name : sendRestCall
	 * @Date : 2021. 03. 05
	 * @Author : KSM
	 * @Description : Http Call
	 *
	 * @param apiAddress
	 * @param httpMethodType
	 * @param requestParamObj
	 * @param serviceReadTime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String sendRestCall(String apiAddress, HttpMethod httpMethodType, Object requestParamObj, int serviceReadTime, boolean isJSONContent) throws Exception {

		final String domain = ConfigUtils.getString("gmarket.domain.url");
		logger.debug("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★domain:"+domain);
		Map<String, Object> requestParam = null;
		
		if(!ObjectUtil.isEmpty(requestParamObj)){
			if (requestParamObj instanceof Map) {
				requestParam = (Map<String, Object>) requestParamObj;
			} else {
				requestParam = ConverterUtil.converVOToMap(requestParamObj);
			}
		}
		
		if (HttpMethod.POST == httpMethodType) {
			return this.sendPost(domain + apiAddress, requestParam, serviceReadTime, isJSONContent);
		} else {
			return this.sendGet(domain + apiAddress, requestParam, serviceReadTime);
		}

	}

	/**
	 * @함수명 : sendGet
	 * @작성일 : 2021. 03. 05
	 * @작성자 : KSM
	 * @설명 : Call Rest API GET
	 *
	 * @param serviceAddress
	 * @param serviceJobId
	 * @param paramMap
	 * @param requestMethod
	 * @return
	 * @throws Exception
	 */
	private String sendGet(String address, Map<String, Object> paramMap, int serviceReadTime) throws Exception {
		String responseJsonString = "";
		BufferedReader br = null;
		HttpURLConnection httpConnection = null;
		String fullAddress = "";

		try {
			fullAddress = address + makeParamter(paramMap, HttpMethod.GET);
			httpConnection = connectionSetting(fullAddress, serviceReadTime);
			httpConnection.setRequestMethod(GmarketRestConst.REQUEST_METHOD_GET);
			InputStream is = httpConnection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			responseJsonString = checkResponseCode(httpConnection); // 응답코드 체크

		} catch (MalformedURLException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		} finally {
			if (br != null) {
				br.close();
			}
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}

		return responseJsonString;
	}

	/**
	 * @함수명 : sendPost
     * @작성일 : 2021. 03. 05
     * @작성자 : KSM
	 * @설명 : Call Rest API POST
	 *
	 * @param serviceAddress
	 * @param serviceJobId
	 * @param paramMap
	 * @param requestMethod
	 * @return
	 * @throws Exception
	 */
	private String sendPost(String address, Map<String, Object> paramMap, int serviceReadTime, boolean isJSONContent) throws Exception {
		HttpURLConnection httpConnection = null;
		OutputStream outputStream = null;
		String responseJsonString = "";

		try {
			byte[] postDataBytes = null;
			httpConnection = connectionSetting(address, serviceReadTime);
			if (isJSONContent) {
				Gson gson = new Gson();
				String json = gson.toJson(paramMap);
				logger.debug("json = " + json);
				postDataBytes = json.getBytes();
				httpConnection.setRequestProperty("Content-Type", GmarketRestConst.CONTEXT_TYPE_JSON);
			} else {
				postDataBytes = makeParamter(paramMap, HttpMethod.POST).getBytes("UTF-8");
				httpConnection.setRequestProperty("Content-Type", GmarketRestConst.CONTEXT_TYPE_XFORM);
			}

			httpConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			httpConnection.setRequestMethod(GmarketRestConst.REQUEST_METHOD_POST);
			outputStream = httpConnection.getOutputStream();
			outputStream.write(postDataBytes);
			outputStream.flush();
			responseJsonString = checkResponseCode(httpConnection); // 응답코드 체크

		} catch (MalformedURLException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}

		return responseJsonString;
	}

	private String makeParamter(Map<String, Object> paramMap, HttpMethod httpMethodType) throws UnsupportedEncodingException {
		StringBuilder paramSb = new StringBuilder();
		if (HttpMethod.GET == httpMethodType) {
			paramSb.append("?");
		}

		for (Map.Entry<String, Object> param : paramMap.entrySet()) {
			if (HttpMethod.POST == httpMethodType) {
				if (paramSb.length() != 0)
					paramSb.append('&');
				paramSb.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				paramSb.append('=');
				paramSb.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			} else {
				paramSb.append(param.getKey() + "=" + param.getValue() + "&");
			}
		}

		if (HttpMethod.GET == httpMethodType) {
			String paramStr = paramSb.toString();
			if (paramStr.endsWith("&")) {
				return paramStr.substring(0, paramStr.length() - 1);
			}
		}

		return paramSb.toString();
	}

	private HttpURLConnection connectionSetting(String address, int serviceReadTime) throws Exception {
		URL url = new URL(address);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		//httpConnection.setRequestProperty("api-key", apiKey);
		httpURLConnection.setReadTimeout(serviceReadTime);
		httpURLConnection.setDoOutput(true);
		return httpURLConnection;
	}

	/**
	 * @함수명 : checkResponseCode
     * @작성일 : 2021. 03. 05
     * @작성자 : KSM
	 * @설명 : 응답코드 체크
	 *
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private String checkResponseCode(HttpURLConnection conn) throws Exception {
		String responseJsonString = null;

		if (conn == null) {
			return "";
		}

		if (conn.getResponseCode() != 200) { // 정상 코드
			if (conn != null) {
				conn.disconnect();
			}
			throw new IOException("http response code error(" + conn.getResponseCode() + ")");
		} else {
			InputStream is = conn.getInputStream();
			byte[] buf = new byte[2048];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			while (true) {
				int n = is.read(buf, 0, 2048);
				if (n > 0) {
					baos.write(buf, 0, n);
				} else if (n == -1) {
					break;
				} else {
					break;
				}
			}
			baos.flush();

			if (baos.size() != 0) {
				responseJsonString = (new String(baos.toByteArray(), "UTF-8"));
			}

			baos.close();
			conn.disconnect();
		}

		return responseJsonString;
	}
	
	public boolean isSuccess(String jsonString) throws Exception {		
		return GmarketRestConst.API_RESULT_CD_0000.equals(getReturnCode(jsonString));
	}
	
	private String getReturnCode(String jsonString) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> value = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});
		String returnCode = (String) value.get("resultCode");
		
		return returnCode;
	}
	
}
