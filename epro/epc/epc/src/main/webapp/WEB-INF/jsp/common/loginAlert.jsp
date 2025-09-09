<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String alertMessage = StringUtils.trimToEmpty((String)request.getAttribute("alertMessage"));
	
	if (alertMessage == null) alertMessage = "";

	// Java에서 자바스크립트에 안전하게 쓸 수 있도록 이스케이프 처리
	alertMessage = alertMessage.replace("\\", "\\\\") 	// 백슬래시
		.replace("'", "\\'") 						  	// 작은따옴표
		.replace("\"", "\\\"") 							// 큰따옴표
		.replace("\r", "") 								// 캐리지 리턴
		.replace("\n", "\\n") 							// 줄바꿈
		.replace("</", "<\\/"); 						// </script> 방지
%>
<script>
var alertMessage = '<%=alertMessage%>';
if (alertMessage != '') {
	alert(alertMessage);
}

if(opener){
	window.close();
}else{
	history.back(-1);	
}
</script>