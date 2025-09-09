<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String alertMessage = StringUtils.trimToEmpty((String)request.getAttribute("alertMessage"));
%>
<script>
var alertMessage = '<%=alertMessage%>';
if (alertMessage != '') {
	alert(alertMessage);
}
history.back(-1);
</script>