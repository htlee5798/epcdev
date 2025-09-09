<%@page pageEncoding="utf-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="ko">
<head>
	<%@ include file="/common/edi/meta.jsp" %>
	<title><decorator:title/> | <fmt:message key="title.supply.consult"/></title>
	<link href='/css/epc/edi/consult/style.css' type="text/css" rel="stylesheet">
	<script language="JavaScript" src='/js/jquery/jquery-1.6.1.js'></script>
	<script type="text/javascript" language="javascript" src='/js/jquery/jquery-1.6.4.min.js'></script>
	<script language="JavaScript" src='/js/epc/edi/consult/etc.js'></script>
	<script language="JavaScript" src='/js/epc/edi/consult/common.js' ></script>
	<link href='/css/epc/edi/consult/popup.css' type="text/css" rel="stylesheet">
	<link href='/css/epc/edi/consult/base.css' type="text/css" rel="stylesheet">
	<decorator:head />
</head>
<body>
	<!-- Header -->
		<jsp:include page="/common/edi/consult/header.jsp"/>
	<!-- //Header -->

	<!-- Body -->
		<!-- c:set var="currentMenu" scope="request" --><!-- decorator:getProperty property="meta.menu" / --><!-- /c:set -->
		<decorator:body />
	<!-- //Body -->

	<!-- Footer -->
		<jsp:include page="/common/edi/consult/footer.jsp"/>
	<!-- //Footer -->
</body>
</html>
