<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd"
	"http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">
<html>
<head>

<title>파일 다운로드</title>
<script>
	function action(atchFileId,streFileNm,fileStreCours,orignlFileNm) {

		document.getElementById('atchFileId').value = atchFileId;
		document.getElementById('streFileNm').value = streFileNm;
		document.getElementById('orignlFileNm').value = orignlFileNm;
		document.getElementById('fileStreCours').value = fileStreCours;
		if ((streFileNm.value == "" )|| (orignlFileNm.value = "") ) {
			alert('오류입니다.');
			return;
		}
		frm.submit();
	}
</script>
</head>
<body>
<form name="frm" action ="./FileDown.do" method="post">
<input type="hidden"  name="streFileNm" id="streFileNm"/>
<input type="hidden"  name="orignlFileNm" id="orignlFileNm"/>
<input type="hidden"  name="atchFileId" id="atchFileId"/>
<input type="hidden"  name="fileStreCours" id="fileStreCours"/>

<table border="1">
<c:forEach var="fileVO" items="${fileList}" varStatus="status">
	<tr>        	
		<td>FileName:</td>
		<td><a href="javascript:action('<c:out value="${fileVO.atchFileId}"/>','<c:out value="${fileVO.streFileNm}"/>','<c:out value="${fileVO.fileStreCours}"/>','<c:out value="${fileVO.orignlFileNm}"/>')"><c:out value="${fileVO.orignlFileNm}"/></a></td>
	</tr>
</c:forEach>
</table>
</form>

<% if (request.getAttribute("message") != null) { %>
<script>
	alert('<%= request.getAttribute("message") %>');
</script>
<% } %>
</body>
</html>