<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<title>LOTTE MART EPC System</title>
	<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
</head>
<frameset rows="69px,*" frameborder="0" framespacing="0">
	<frame src="<c:url value="/main/viewScmTop.do"/>" name="gnbFram" border="0" marginheight="0" marginwidth="0" scrolling="no">
	<frameset cols="190px,*" id="widthFram" name="widthFram" frameborder="0" framespacing="0">
<c:choose>
	<c:when test="${epcLoginVO.happyGubn}">
		<frame src="<c:url value="/main/viewScmLeft.do?menuGb=2"/>" name="lnbFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">
			<frame src="<c:url value="/main/viewScmContent.do"><c:param name="initUrl" value="/delivery/PDCMHPC0001.do" /><c:param name="initTitle"  value="1" /></c:url>" name="contentFram" border="0" marginheight="0" marginwidth="0" scrolling="yes">
	</c:when>
	<c:when test="${epcLoginVO.allianceGubn}">
		<frame src="<c:url value="/main/viewScmLeft.do?menuGb=7"/>" name="lnbFram" border="0" marginheight="0" marginwidth="0" scrolling="no">
		<frame  name="contentFram" src="<c:url value="/main/viewScmContent.do"/>" border="0" marginheight="0" marginwidth="0" scrolling="yes">
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${param.vndInfo eq 'V'}">
		<frame src="<c:url value="/main/viewScmLeft.do?menuGb=6"/>" name="lnbFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">
		<frame src="<c:url value="/main/viewScmContent.do"><c:param name="initUrl"  value="/system/selectVendorInfoMgr.do" /><c:param name="initTitle"  value="3" /></c:url>" name="contentFram" border="0" marginheight="0" marginwidth="0" scrolling="yes">
			</c:when>
			<c:otherwise>
		<frame src="<c:url value="/main/viewScmLeft.do"/>" name="lnbFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">
		<frame src="<c:url value="/main/viewScmContent.do"><c:param name="initUrl"  value="/product/selectProduct.do" /><c:param name="initTitle"  value="2" /></c:url>" name="contentFram" border="0" marginheight="0" marginwidth="0" scrolling="yes">
			</c:otherwise>
		</c:choose>

	</c:otherwise>
</c:choose>

	</frameset>
</frameset>

<body>
</body>
</html>