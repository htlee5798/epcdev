<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd"
	"http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
<html>
<head>

<title>DB 암호화</title>
<script>
</script>
</head>
<body>
<form>
<table border="1">
	<tr>        	
		<td>Key:</td>
		<td><c:out value="${dbVo.key}"/></td>
	</tr>
	<tr>        	
		<td>Pattern 7 encryption:</td>
		<td><c:out value="${dbVo.encP7}"/></td>
	</tr>
	<tr>        	
		<td>Pattern 7 decryption:</td>
		<td><c:out value="${dbVo.decP7}"/></td>
	</tr>
	<tr>        	
		<td>Normal encryption:</td>
		<td><c:out value="${dbVo.encNor}"/></td>
	</tr>
	<tr>        	
		<td>Normal decryption:</td>
		<td><c:out value="${dbVo.decNor}"/></td>
	</tr>
	<tr>        	
		<td>Hash :</td>
		<td><c:out value="${dbVo.encHash}"/></td>
	</tr>
	<tr>        	
		<td>sha256 encryption:</td>
		<td><c:out value="${dbVo.encSha256}"/></td>
	</tr>
</table>
</form>
</body>
</html>