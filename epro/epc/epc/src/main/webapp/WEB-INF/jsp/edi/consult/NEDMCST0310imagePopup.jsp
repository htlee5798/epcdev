<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>


</head>

<body>
 <%-- <img src="<c:out value='${imagePath}'/>/consult/<c:out value='${param.imgpath}'/>/<c:out value='${param.img }'/>"/> --%>
 
 <img src="<c:out value='${imagePath}'/><c:out value='${imgPathSub}'/>/images/edi/consult/<c:out value='${param.imgpath}'/>/<c:out value='${param.img }'/>" >
</body>
</html>