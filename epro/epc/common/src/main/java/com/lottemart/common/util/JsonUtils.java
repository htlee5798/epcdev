package com.lottemart.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class JsonUtils {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	// 현재 사용하지 않음.
	public static String list2JsonOld(List<Map> list) {
	    JSONArray json_arr = new JSONArray();
	    for (Map<String, Object> map : list) {
	        JSONObject json_obj=new JSONObject();
	        for (Map.Entry<String, Object> entry : map.entrySet()) {
	            String key = entry.getKey();
	            Object value = entry.getValue();
	            try {
	                json_obj.put(key,value);
	            } catch (JSONException e) {
	            	logger.error("list2JsonOld Error",e);
	            }
	        }
	        json_arr.add(json_obj);
	    }
	    return json_arr.toString();
	}

	/**
	 * Desc : 다양한 형태의 List를 JSON으로 변환
	 * @Method Name : convertList2Json
	 * @param totalCount( null:페이징이 없는 경우, -1:TOTAL_COUNT가 쿼리 내에 있는 경우.)
	 */
	public static Map<String, Object> convertList2Json(List<?> list, Integer totalCnt, String currPage) {

        Map<String, Object> maps = new HashMap<String, Object>();

        //조회된 데이터가 없는 경우
        if (list == null || list.isEmpty() ){
              maps.put("totalCount",  new Integer(0) );
              maps.put("pageIndex", currPage);
              maps.put("result", false);
              return maps;
        }

        Integer totalCntTmp =  new Integer(totalCnt == null? list.size():totalCnt.intValue());
        //쿼리 내에 TOTAL_COUNT가 있는 경우
        if (totalCnt != null && totalCnt == -1) {
             String totalCountStr = ((DataMap)list.get(0)).getString("TOTAL_COUNT");
             if(totalCountStr == null || "".equals(totalCountStr.trim()))totalCountStr="0";
             totalCntTmp =  new Integer(totalCountStr);
        }

        //조회된 데이터가 있을 경우
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("Data",list);
        maps.put("ibsList", net.sf.json.util.WebUtils.toString(jsonObj));
        maps.put("totalCount", totalCntTmp);
        maps.put("pageIndex", currPage);
        maps.put("result", true);

		return maps;
	}

	// IBSheet용 엑셀 다운로드 모듈
	public static void IbsExcelDownload(List<?> list, HttpServletRequest request, HttpServletResponse response) {
		String path = "/excel/DirectDown2Excel.do";

		if (list != null && !list.isEmpty()){
			  Object tmpObj = list.get(0);
			  if(tmpObj != null && tmpObj instanceof Map ||  tmpObj  instanceof HashMap  || tmpObj  instanceof DataMap  ){
				  request.setAttribute("SHEETDATA", list);
			  }else{
				    JsonConfig config = new JsonConfig();
				    config.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor());
			        config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
					request.setAttribute("SHEETDATA", (JSONArray)JSONSerializer.toJSON(list, config));
			  }
		}else{
			request.setAttribute("SHEETDATA", list);
		}

        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
               rd.forward(request, response);
       } catch (Exception e) {
           logger.error("IbsExcelDownload ERROR",e);
       }
	}

	// IBSheet에서 리턴받는 형식의 JSON
	public static JSONObject getResultJson(JSONObject jObj) {
		JSONObject mObj = new JSONObject();
		mObj.put("Result", jObj);
		mObj.put("Etc", jObj);
		return mObj;
	}

    public static String cleanXSS(String value) {
		String values = value;
		values = values.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		values = values.replaceAll("'", "&#39;");
		values = values.replaceAll("eval\\((.*)\\)", "");
		values = values.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		values = values.replaceAll("script", "");
		return values;
	}

    public static String convertLineFeedToBlank(String value) {
    	String values = value;
    	values = values.replaceAll("\n", " ").replaceAll("\r", " ");
    	values = values.replaceAll("\r\n", " ").replaceAll("\n\r", " ");
    	values = values.replaceAll("\'", "\"");
		return values;
	}

    public static String convertHtmlTagOld(String value) {
    	String values = value;
    	values = values.replaceAll("\n", "<br/>");
    	values = values.replaceAll("\r", "<br/>");
    	values = values.replaceAll("\r\n", "<br/>");
    	values = values.replaceAll("\n\r", "<br/>");

		return values;
	}

/* 미사용 (주석)  
 *  public static String replace(String src, String from, String to) {
        if (src == null)
            return null;
        if (from == null)
            return src;
        if (to == null)
            to = "";
        StringBuffer buf = new StringBuffer();
        for (int pos; (pos = src.indexOf(from)) >= 0;) {
            buf.append(src.substring(0, pos));
            buf.append(to);
            src = src.substring(pos + from.length());
        }
        buf.append(src);
        return buf.toString();
    }*/

	public static String ntb(Object anyObj){
        return (anyObj == null || String.valueOf(anyObj).trim().length()==0 || String.valueOf(anyObj).equals("null"))
        ? "" : String.valueOf(anyObj).trim();
    }

	/**
	 * */
	public static int sti(String tmpStr) {
		return sti(tmpStr, 0);
	}

	/**
	 * */
	public static int sti(String tmpStr, int retVal) {
		int tmpRetVal = retVal;
		try	{
			if(tmpStr != null && tmpStr.length() > 0) {
				tmpRetVal = Integer.parseInt(tmpStr);
			}
		}
		catch(Exception e) {
			logger.error("error --> " + e.getMessage());
		}

		return tmpRetVal;
	}

	// request 객체 전체 표시하기
	public static void dispReqNames(HttpServletRequest request) {

		int idx = 0;
		Enumeration params = request.getParameterNames();
		logger.debug("--------------------------------------");
		while (params.hasMoreElements()) {
		    String name = (String)params.nextElement();
		    logger.debug("idx ===============> " + idx);
		    logger.debug("name ==> " + name);
		    logger.debug("value ==> " + request.getParameter(name));
			idx++;
		}
		logger.debug("--------------------------------------");
	}

    public static String getXmlData(Map data ) {
		String rs = "";
		try {
		     if ( data != null ) {
		           rs = "<DATA>";
		           Iterator itr = data.keySet().iterator();
		           while (itr.hasNext()) {
		        	   String key = (String)itr.next();
		        	   rs += "<"+key+"><![CDATA[";
		        	   rs += "" + data.get(key);
		        	   rs += "]]></"+key+">";
		           }
		           rs += "</DATA>";
		     }
		} catch(Exception e) {
			logger.debug(e.getMessage());
		}
		return rs;
    }
}
