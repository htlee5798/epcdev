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

<title>파일 찾기</title>
<script type="text/javascript">
function submitform()
{
  document.frm.submit();
}
</script>
</head>
<body>
<form name="frm" action ="./selectFileInfs.do" method="post">

<table border="1">
	<tr>        	
		<td>atchFileId:</td>
		<td><input name="atchFileId" id="atchFileId" value=""/><a href="javascript:submitform()"><input type = "button" value="실행!"></a></td>
	</tr>
</table>
</form>

<% if (request.getAttribute("message") != null) { %>
<script>
	alert('<%= request.getAttribute("message") %>');
</script>
<% } %>
</body>
</html>