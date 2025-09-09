<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String)request.getAttribute("msg");
	String vandorId = (String)request.getAttribute("vandorId");
%>
<script type="text/javascript" >
alert('<%=msg%>');
parent.doSearch();
</script>