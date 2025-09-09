<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head><title>SSO 통합인증 테스트 사이트</title></head>
<%
Enumeration<String> eHeader = request.getHeaderNames();
while (eHeader.hasMoreElements()) {
	String hName = (String)eHeader.nextElement();
	String hValue = request.getHeader(hName); 
	out.println(hName + " : " + hValue + "</br>");
}
%>
<body>
</body>
</html>
