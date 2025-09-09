/**
 * <br>프로젝트   	: 롯데마트 인터넷 쇼핑몰
 * <br>프로그램명	: SecureUtil
 * <br>설명		: 공통함수
 * <br>작성일자 	: 2016/09/20
 * <br>수정일자	:             수정자 :
 * <br>수정내용	:
 * <br>수정상세	:
 */

package com.lottemart.epc.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

public class SecureUtil
{
	
	public static String sqlValid(String paramString){
		String tmpParamString = paramString;
		if(tmpParamString == null || "".equals(tmpParamString)) {
			return tmpParamString;
		} else {
			String[] blacklist = { "--",  ";", "union", "drop", "update", "delete", "join", "user_tables", "user_table_columns"};
			
			for (String blackKeyword : blacklist) {
				tmpParamString = tmpParamString.replaceAll("(?i)"+blackKeyword, "");
			}
			return tmpParamString;
		}
	}
	
	public static String[] sqlValidArray(String[] arrayStrParam){
		if(arrayStrParam == null || arrayStrParam.length == 0) {
			return arrayStrParam;
		} else {
			String[] blacklist = { "--",  ";", "union", "drop", "update", "delete", "join", "user_tables", "user_table_columns"};
			
			for (String blackKeyword : blacklist) {
				for (int i = 0 ; i < arrayStrParam.length ; i++) {
					if(arrayStrParam[i] != null && !"".equals(arrayStrParam[i])) {
						arrayStrParam[i] = arrayStrParam[i].replaceAll("(?i)"+blackKeyword, "");
					}
				}
			}
			
			return arrayStrParam;
		}
	}	
	
    /**
     * 파일 확장자 존재 여부 검사 루틴 추가 
     */
	public boolean includeFileExtension(String val){
		boolean ret = false;
		  if( (-1) == val.lastIndexOf(".") )
		  {
		    ret = true;
		  }
		  return ret;

	}
	
	public static boolean pathFilterForImg(String str){
		String whiteList[] = {"bmp","jpg","jpeg","gif","png","psd"};

		for(String val : whiteList){
			if(str.toLowerCase().substring(str.lastIndexOf(".")+1).equals(val)){
				return true;
			}
		}
		return false;
	}
	
	public static String splittingFilter(String str){
		String val = str.replaceAll("[.]", "");
		return val;
	}
	
	
	public static String splittingFilterAll(String str){
		String val = str.replaceAll("[.\\\"]", "");
		return val;
	}
	
	
	public static String removeCRLF(String str){
		String val = str.replaceAll("\r", "").replaceAll("\n", "");
		return val;
	}
	
	public static String stripXSS(String value) {
		 String tmpValue = value;
		
	      if (value != null) {
	          // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
	          // avoid encoded attacks.
	          // value = ESAPI.encoder().canonicalize(value);
	          // Avoid null characters
	    	  tmpValue = tmpValue.replaceAll("", "");
	          // Avoid anything between script tags
	          Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");
	          
	          // Avoid anything in a src='...' type of expression
	          scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          // Remove any lonesome </script> tag
	          scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          // Remove any lonesome <script ...> tag
	          scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          // Avoid eval(...) expressions
	          scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          // Avoid expression(...) expressions
	          scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          // Avoid javascript:... expressions
	          scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          // Avoid vbscript:... expressions
	          scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

	          // Avoid onload= expressions
	          scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	          tmpValue = scriptPattern.matcher(tmpValue).replaceAll("");

			  	// HTML transformation characters
	          tmpValue = org.springframework.web.util.HtmlUtils.htmlEscape(tmpValue);
			  	// SQL injection characters
	          tmpValue = StringEscapeUtils.escapeSql(tmpValue);
			  	//System.out.println("before=========" + value);
	          tmpValue = tmpValue.replaceAll("&amp;", "&");
	          tmpValue = tmpValue.replaceAll("&quot;", "\"");
	          tmpValue = tmpValue.replaceAll("&#39;", "\'");
			  	//System.out.println("after=========" + value);
			  	
	      }
	      return tmpValue;
	  }
	
	
	
	public static String[] stripXSSArray(String[] arrayStrParam){
		if(arrayStrParam == null ||arrayStrParam.length == 0 ){
			return arrayStrParam;	
		}else{
			for(int i=0; i<arrayStrParam.length ; i++){
				if(arrayStrParam[i] != null && !"".equals(arrayStrParam[i])){
					arrayStrParam[i] = stripXSS(arrayStrParam[i]);
				}
			}
			return arrayStrParam;				
		}
		
	}
	
}
