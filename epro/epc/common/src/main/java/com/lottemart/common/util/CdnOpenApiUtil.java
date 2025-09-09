package com.lottemart.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
//import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
//import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SimpleTimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;


public class CdnOpenApiUtil {

	final static Logger logger = LoggerFactory.getLogger(CdnOpenApiUtil.class);

	public String cdnPurge(String cdnPurgeReqUrl, String cdnUserId, String cdnApiKey, List<String> urls, List<String> dirs) {

		String rtnMsg = "";
		try {
			//header
			Map<String, String> header = new HashMap<String, String>();
			header.put("Accept", "application/json");
			header.put("Content-Type", "application/json");
			//username
			//apikey
			//get param
			//Map<String, String> param = new HashMap<String, String>();
			//param.put("datefrom", "2025-05-28T16:29:00+08:00");
			//param.put("dateto", "2025-05-28T16:30:59+08:00");
			//param.put("type", "fiveminutes");
			//String paramStr = "";
			//if (param != null && param.size() > 0) {
			//	for (String key : param.keySet()) {
			//		if (!"".equals(paramStr)) {
			//			paramStr += "&";
			//		}
			//		paramStr += key + "=" + URLEncoder.encode(param.get(key), "UTF-8");
			//	}
			//}

			//String body = "{\"urls\": [\"https://simage.lottemart.com/lim_stg/static_root/images/prodimg/04003/0400353080009_1_1200.jpg\"],\"urlAction\":\"delete\",\"dirs\": [\"https://simage.lottemart.com/lim_stg/static_root/images/prodimg/04003/\"],\"dirAction\":\"expire\"}"; //body参数，按实际接口给，比如XML
			String jsonBody = makeJsonData(urls, dirs);
			//cdnPurgeReqUrl = cdnPurgeReqUrl + "?";// + paramStr;

			rtnMsg = this.post(cdnPurgeReqUrl, cdnUserId, cdnApiKey, header, jsonBody.getBytes());
		//} catch(UnsupportedEncodingException e) {
		} catch (URISyntaxException e) {
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw));
			logger.error("Cdn Purge Error: " + e.toString());
		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw));
			logger.error(e.toString());
		}
		return rtnMsg;
	}

	public String makeJsonData(List<String> urls, List<String> dirs) {

		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		Gson gson = new Gson();
		String paramString = "";

		try {
			paramMap.put("urls", urls);
			paramMap.put("urlAction", "delete");
			paramMap.put("dirs", dirs);
			paramMap.put("dirAction", "expire");
			paramString = gson.toJson(paramMap);
			logger.debug("paramString: " + paramString);
		} catch (JsonIOException e) {
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw));
			logger.error("Cdn makeJsonData Error: " + e.toString());
		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw));
			logger.error(e.toString());
		}

		return paramString;
	}

	/**
	 * GET
	 */
	public String get(String url, String username, String apiKey, Map<String, String> header) throws URISyntaxException {
		HttpGet get = new HttpGet();
		get.setURI(new URI(url));
		return call(get, username, apiKey, header);
	}

	/**
	 * POST
	 */
	public String post(String url, String username, String apiKey, Map<String, String> header, byte[] body) throws URISyntaxException {
		HttpPost post = new HttpPost();
		post.setURI(new URI(url));
		if (body != null) {
			ByteArrayEntity se = new ByteArrayEntity(body);
			post.setEntity(se);
		}
		return call(post, username, apiKey, header);
	}

	/**
	 * PUT
	 */
	public String put(String url, String username, String apiKey, Map<String, String> header) throws URISyntaxException {
		HttpPut get = new HttpPut();
		get.setURI(new URI(url));
		return call(get, username, apiKey, header);
	}

	/**
	 * DELETE
	 */
	public String delete(String url, String username, String apiKey, Map<String, String> header) throws URISyntaxException {
		HttpDelete get = new HttpDelete();
		get.setURI(new URI(url));
		return call(get, username, apiKey, header);
	}

	/**
	 * http call
	 */
	private String call(HttpRequestBase method, String username, String apikey, Map<String, String> header) {
		DefaultHttpClient client = null;
		String rtnMsg = "";
		try {
			client = new DefaultHttpClient();
			addHeader(method, username, apikey, header);
			HttpResponse response = client.execute(method);
			if (response.getStatusLine().getStatusCode() != 200) {
				method.abort();
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				rtnMsg = EntityUtils.toString(entity);
				logger.info(rtnMsg);
			}
		} catch (Exception e) {
			method.abort();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.error("Cdn call Error: " + sw.toString());
		}
		return rtnMsg;
	}

	private void addHeader(AbstractHttpMessage client, String username, String apikey, Map<String, String> headers) throws Exception {
		if (headers != null) {
			for (Iterator<Entry<String, String>> it = headers.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				client.addHeader(entry.getKey(), entry.getValue());
			}
		}
		Date date = new Date();
		String dateString = getDate(date);
		String authoriztion = encode(dateString, username, apikey);
		client.addHeader("Date", dateString);
		client.addHeader("Authorization", "Basic " + authoriztion);
	}

	private String getDate(Date date) {
		SimpleDateFormat rfc822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		rfc822DateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
		String dateString = rfc822DateFormat.format(date);
		return dateString;
	}

	private String encode(String dateString, String username, String apikey) throws Exception {
		String signature = signAndBase64Encode(dateString.getBytes(), apikey);
		String userAndPwd = username + ":" + signature;
		return new String(Base64.encodeBase64(userAndPwd.getBytes("UTF-8")));
	}

	private String signAndBase64Encode(byte[] data, String apikey) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA256"); // HmacSHA256
		mac.init(new SecretKeySpec(apikey.getBytes(), "HmacSHA256"));// HmacSHA256
		byte[] signature = mac.doFinal(data);
		return new String(Base64.encodeBase64(signature));
	}

}