<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>
<%--@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"--%>
<%--@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" --%>
<%--@ taglib prefix="spring" uri="http://www.springframework.org/tags"--%>
<%
	String message = (String)request.getAttribute("message");
	
	
	// Java에서 자바스크립트에 안전하게 쓸 수 있도록 이스케이프 처리
	message = message.replace("\\", "\\\\") // 백슬래시
		.replace("'", "\\'") 				// 작은따옴표
		.replace("\"", "\\\"") 				// 큰따옴표
		.replace("\r", "") 					// 캐리지 리턴
		.replace("\n", "\\n") 				// 줄바꿈
		.replace("</", "<\\/"); 			// </script> 방지
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일없음</title>
<script>
	alert('<%=message%>');
	window.close();
</script>
</head>
</html>