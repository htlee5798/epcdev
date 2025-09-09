<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String)request.getAttribute("msg");
%>
<script type="text/javascript" >
alert('<%=msg%>');
self.close();
</script>