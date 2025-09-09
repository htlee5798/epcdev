package com.lottemart.epc.restApi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.google.gson.Gson;

import lcn.module.framework.property.PropertyService;

public class RestAPIUtil {
	private static final Logger logger = LoggerFactory.getLogger(RestAPIUtil.class);
	@Resource(name = "propertiesService")
	protected PropertyService propertyService;

	/**
	 * @Method Name : sendRestCall
	 * @Date : 2019. 11. 4.
	 * @Author : 하두현
	 * @Description : Http Call
	 *
	 * @param apiAddress
	 * @param httpMethodType
	 * @param requestParamObj
	 * @return
	 * @throws Exception
	 */
	public String sendRestCall(String apiAddress, HttpMethod httpMethodType, Object requestParamObj) throws Exception {
		return sendRestCall(apiAddress, httpMethodType, requestParamObj, RestConst.READ_TIME_OUT, false);
	}

	/**
	 * @Method Name : sendRestCall
	 * @Date : 2019. 11. 4.
	 * @Author : 하두현
	 * @Description : Http Call
	 *
	 * @param apiAddress
	 * @param httpMethodType
	 * @param requestParamObj
	 * @return
	 * @throws Exception
	 */
	public String sendRestCall(String apiAddress, HttpMethod httpMethodType, Object requestParamObj, boolean isJSONContent) throws Exception {
		return sendRestCall(apiAddress, httpMethodType, requestParamObj, RestConst.READ_TIME_OUT, isJSONContent);
	}

	/**
	 * @Method Name : sendRestCall
	 * @Date : 2019. 11. 4.
	 * @Author : 하두현
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

		final String domain = "http://oneapp.lottemart.com";
		//final String key = "NoKey";// propertyService.getString("oneapp.api.key");

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
	 * @작성일 : 2019. 10. 1.
	 * @작성자 : hadh
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
			httpConnection = connectionSetting(httpConnection, fullAddress, serviceReadTime);
			httpConnection.setRequestMethod(RestConst.REQUEST_METHOD_GET);
			InputStream is = httpConnection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			responseJsonString = checkResponseCode(httpConnection); // 응답코드 체크

		} catch (MalformedURLException e) {
			StringWriter errors = new StringWriter();
			logger.error(errors.toString());
		} catch (IOException e) {
			StringWriter errors = new StringWriter();
			logger.error(errors.toString());
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
	 * @작성일 : 2019. 10. 1.
	 * @작성자 : hadh
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
			httpConnection = connectionSetting(httpConnection, address, serviceReadTime);
			if (isJSONContent) {
				Gson gson = new Gson();
				String json = gson.toJson(paramMap);
				postDataBytes = json.getBytes();
				httpConnection.setRequestProperty("Content-Type", RestConst.CONTEXT_TYPE_JSON);
			} else {
				postDataBytes = makeParamter(paramMap, HttpMethod.POST).getBytes("UTF-8");
				httpConnection.setRequestProperty("Content-Type", RestConst.CONTEXT_TYPE_XFORM);
			}

			httpConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			httpConnection.setRequestMethod(RestConst.REQUEST_METHOD_POST);

			outputStream = httpConnection.getOutputStream();
			outputStream.write(postDataBytes);
			outputStream.flush();
			responseJsonString = checkResponseCode(httpConnection); // 응답코드 체크

		} catch (MalformedURLException e) {
			StringWriter errors = new StringWriter();
			logger.error(errors.toString());
		} catch (IOException e) {
			StringWriter errors = new StringWriter();
			logger.error(errors.toString());
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

	private HttpURLConnection connectionSetting(HttpURLConnection httpURLConnection, String address, int serviceReadTime) throws Exception {

		HttpURLConnection http = httpURLConnection;
		URL url = new URL(address);
		http = (HttpURLConnection) url.openConnection();
		//httpConnection.setRequestProperty("api-key", apiKey);
		http.setReadTimeout(serviceReadTime);
		http.setDoOutput(true);
		return http;
	}

	/**
	 * @함수명 : checkResponseCode
	 * @작성일 : 2019. 10. 1.
	 * @작성자 : hadh
	 * @설명 : 응답코드 체크
	 *
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private String checkResponseCode(HttpURLConnection conn) throws Exception {

		HttpURLConnection http = conn;
		String responseJsonString = null;

		if (http == null) {
			return "";
		}

		if (http.getResponseCode() != 200) { // 정상 코드
			if (http != null) {
				http.disconnect();
			}
			throw new IOException("http response code error(" + http.getResponseCode() + ")");
		} else {
			InputStream is = http.getInputStream();
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
			http.disconnect();
			http = null;
		}

		return responseJsonString;
	}

}
