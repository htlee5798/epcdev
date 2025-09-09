package com.lottemart.common.etc;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;

public final class RequestWrapper extends HttpServletRequestWrapper {

	private final String remoteAddr;
	private final String trueclientip;
    HashMap params;
    
	public RequestWrapper(HttpServletRequest request) {
		super(request);
		remoteAddr = request.getRemoteAddr();
		trueclientip = request.getHeader("TRUE-CLIENT-IP");
        this.params = new HashMap(request.getParameterMap());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getRemoteAddr() {
		try {
			String tcip = this.trueclientip;
	        if (tcip == null || tcip == "") {
	        	tcip = this.remoteAddr;
	        }
	        return tcip;
		} catch (Exception e) {
			return "";
		}
	}

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
     */
    public String getParameter(String name) {
        String returnValue = null;
        String[] paramArray = getParameterValues(name);
        if (paramArray != null && paramArray.length > 0){
          returnValue = paramArray[0];   
        }

		  if (returnValue == null){             
		   return null;         
		  }
		   
		  //return cleanXSS(value);
		  return stripXSS(returnValue);
    }
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterMap()
     */
    public Map getParameterMap() {
        return Collections.unmodifiableMap(params);
    }
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterNames()
     */
    public Enumeration getParameterNames() {
        return Collections.enumeration(params.keySet());        
    }
    /* (non-Javadoc)
     * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String name) {
    	String[] result = null;
//    	String[] temp = (String[])params.get(name);
    	String[] temp =   super.getParameterValues(name);
    	if (temp != null){
    		result = new String[temp.length];
    		System.arraycopy(temp, 0, result, 0, temp.length);    		
    	}

		  if (temp == null){             
		   return null;         
		  }
		   
		  int count = result.length;      
		  
		  String[] encodedValues = new String[count];      
		  
		  for (int i = 0; i < count; i++) {                
		   encodedValues[i] = stripXSS(result[i]);	//cleanXSS(values[i]);        
		  }       
		  
		  return encodedValues;  
    }
       
    
    /**
     * Sets the a single value for the parameter.  Overwrites any current values.
     * @param name Name of the parameter to set
     * @param value Value of the parameter.
     */
    public void setParameter(String name, String value){
      String[] oneParam = {value};
      setParameter(name, oneParam);
    }
    
    /**
     * Sets multiple values for a parameter.
     * Overwrites any current values.
     * @param name Name of the parameter to set
     * @param values String[] of values.
     */
    public void setParameter(String name, String[] values){
    	params.put(name, values);   
    }
    
    public String getHeader(String name) {         
    	String value = super.getHeader(name);         
		  
    	if (value == null){             
    		return null;         
    	}
		   
    	return stripXSS(value);
    }     
	 
	@SuppressWarnings("unused")
	private String cleanXSS(String value) {
		//You'll need to remove the spaces from the html entities below         
		String realValue = value;
		realValue = realValue.replaceAll( "<", "& lt;" ).replaceAll( ">", "& gt;" );
		realValue = realValue.replaceAll( "\\(", "& #40;" ).replaceAll( "\\)", "& #41;" );
		realValue = realValue.replaceAll( "'", "& #39;" );
		realValue = realValue.replaceAll( "eval\\((.*)\\)", "" );
		realValue = realValue.replaceAll( "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"" );
		realValue = realValue.replaceAll( "script", "" );
		
		// HTML transformation characters
		realValue = org.springframework.web.util.HtmlUtils.htmlEscape( realValue );
		
		// SQL injection characters
		realValue = StringEscapeUtils.escapeSql( realValue );
		
		return realValue;
	}
	 
	 public static String stripXSS(String value) {
        String realValue = value;
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
            realValue = value.replaceAll( "", "" );
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile( "<script>(.*?)</script>", Pattern.CASE_INSENSITIVE );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Avoid anything in a src='...' type of expression
//	          scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//	          value = scriptPattern.matcher(value).replaceAll("");
//
//	          scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//	          value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile( "</script>", Pattern.CASE_INSENSITIVE );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile( "<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile( "eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile( "expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile( "javascript:", Pattern.CASE_INSENSITIVE );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile( "vbscript:", Pattern.CASE_INSENSITIVE );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Avoid onload= expressions
            scriptPattern = Pattern.compile( "onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Avoid alert= expressions
            scriptPattern = Pattern.compile( "alert(.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Remove any lonesome </textarea> tag
            scriptPattern = Pattern.compile( "</textarea>", Pattern.CASE_INSENSITIVE );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // Remove any lonesome <textarea> tag
            scriptPattern = Pattern.compile( "<textarea(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL );
            realValue = scriptPattern.matcher( realValue ).replaceAll( "" );

            // HTML transformation characters
//			  	value = org.springframework.web.util.HtmlUtils.htmlEscape(value);
            // SQL injection characters
//			  	value = StringEscapeUtils.escapeSql(value);
            //System.out.println("before=========" + value);
            realValue = realValue.replaceAll( "&amp;", "&" );
            realValue = realValue.replaceAll( "&quot;", "\"" );
            realValue = realValue.replaceAll( "&#39;", "\'" );
            //System.out.println("after=========" + value);

        }

        return realValue;
    }

}
