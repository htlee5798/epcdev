<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLClassLoader" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ClassLoader cl = ClassLoader.getSystemClassLoader();
URL[] urls = ((URLClassLoader)cl).getURLs();
%>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
</head>
<body>
<%
for(URL url: urls){
%>
<%=url.toString()%><br />
<%
}
%>
<br />
filepath:<%= cl.getResource("").getPath() %>
</body>
</html>
