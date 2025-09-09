<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>
<%--@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"--%>
<%--@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" --%>
<%--@ taglib prefix="spring" uri="http://www.springframework.org/tags"--%>
<%
	String message = (String)request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일없음</title>
<script>
	alert("<%= message %>");
	window.close();
</script>
</head>
</html>