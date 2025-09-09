<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd"
	"http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
<html>
<head>
<title>File Upload</title>

</head>
<script type="text/javascript">
	function process() {
		var frm = document.frm;

		if (frm.file.value == '') {
			alert('파일을 선택해 주세요.');
			return;
		}
		
		frm.submit();
	}
</script>
<body>
<form name="frm" action ="<c:url value='/FileUp.do'/>" method="post" enctype="multipart/form-data">
<table border="1">
	<tr>        	
		<td> 
			<input type = "file" name="file">
		</td>	
		<td>
			<input type = "button" value="실행!" onclick="process()">
		</td>
	</tr> 
</table>
<br>
<% 
if (request.getAttribute("fileList") != null) {
%>
<table border="1">
<c:forEach var="fileVO" items="${fileList}" varStatus="status">
	<tr>        	
		<td>originalFileName:</td>
		<td><c:out value="${fileVO.orignlFileNm}"/></td>
	</tr>
	
	<tr>        	
		<td>uploadFileName:<br>(다운로드 테스트시 필요)</td>
		<td><c:out value="${fileVO.streFileNm}"/></td>
	</tr>
	<tr>        	
		<td>fileExtension:</td>
		<td><c:out value="${fileVO.fileExtsn}"/></td>
	</tr>
	<tr>        	
		<td>filePath:</td>
		<td><c:out value="${fileVO.fileStreCours}"/></td>
	</tr>
	<tr>        	
		<td>fileSize:</td>
		<td><c:out value="${fileVO.fileMg}"/></td>
	</tr>
</c:forEach>
</table>
<% 
}
%>
</form>
</body>
</html>