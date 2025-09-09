<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>[TEST] LOTTE MART EPC System</title>
	<link rel="stylesheet" href="/css/style.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
</head>
<script type="text/javascript">

</script>
<frameset rows="69px,*" frameborder="0" framespacing="0">
	<frame src="<c:url value="/edi/test/viewEdiTop.do"/>" name="gnbFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">
	<frameset cols="190px,850px,*" id="widthFram" name="widthFram" frameborder="0" framespacing="0">
		<frame src="<c:url value="/edi/test/viewEdiLeft.do"/>?pgm_code=TST" id="lnbFram" name="lnbFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">
		<frameset rows="200px,*" id="widthFram" name="widthFram" frameborder="0" framespacing="0">
			<frame src="<c:url value="/edi/test/viewEdiInfo.do"/>" id="infoFram" name="infoFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">	
			<frame src="<c:url value="/edi/test/NEDMTST0010.do"/>" id="contentFram" name="contentFram"  border="0" marginheight="0" marginwidth="0" scrolling="yes">
		</frameset>
		<frame src="<c:url value="/edi/test/NEDMTST0011.do"/>"  id="multiContentFram" name="multiContentFram"  border="0" marginheight="0" marginwidth="0" scrolling="yes">
	</frameset>
</frameset>

<body>  
</body>
</html>