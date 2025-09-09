
<%--
 -24_NBM
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String course_no = request.getParameter("cousrse_no");
	String cls_cd = request.getParameter("cls_cd");
	String jsession_id = request.getParameter("jsession_id");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
</head>
<body>
  <form action="${url}" id="paymentResultRedirect" method="get">
  	<input type="hidden"  id="course_no" value="${course_no}"/>
  	<input type="hidden"  id="cls_cd" value="${cls_cd}"/>
  	<input type="hidden"  id="jsession_id" value="${jsession_id}"/>
  </form>

  <c:set var="url" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
  <c:set var="pullUrl" value="${url}/cu/meb/register/paymentResultRedirect.jsp?course_no=${crtvo.course_no}-${crtvo.from_fg}-${crtvo.str_cd}&cls_cd=${crtvo.cls_cd}&jsessionid=${sessionId}"/>
  <script>
	  document.addEventListener("DOMContentLoaded", () => {
		  document.getElementById("paymentResultRedirect").submit();
		  return false;
	  });
  </script>
</body>
</html>
