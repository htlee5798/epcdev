<%@page pageEncoding="utf-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="ko">
<head>
	<%@ include file="/common/edi/meta.jsp" %>
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	
	<title><decorator:title/> | <fmt:message key="title.supply.consult"/></title>
	<!-- <link href='/css/epc/edi/consult/style.css' type="text/css" rel="stylesheet"> -->
	<!-- <link href='/css/epc/edi/consult/popup.css' type="text/css" rel="stylesheet"> -->
	<!-- <link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet"> -->
	<link type="text/css" rel="stylesheet" href="/css/epc/edi/srm/@default.css" />
	
	<script type="text/javascript" src='/js/epc/edi/consult/etc.js'></script>
	<script type="text/javascript" src='/js/epc/edi/consult/common.js' ></script>
	<script type="text/javascript" src='/js/epc/common.js' ></script>
	
	<script type="text/javascript" src='/js/jquery/jquery-1.6.1.js'></script>
	<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
	<!-- <script type="text/javascript" src='/js/jquery/jquery-1.6.4.min.js'></script> -->
	<script type="text/javascript" src="/js/jquery/jquery.tmpl.js"></script>
	<script type="text/javascript" src="/js/jquery/jquery.handler.js"></script>
	
	
	<decorator:head />
</head>

<body>

	<div id="wrap">
	
		<!-- Header -->
			<jsp:include page="/common/edi/srm/evl-header.jsp"/>
		<!-- //Header -->
	
		<!-- Body -->
			<!-- c:set var="currentMenu" scope="request" --><!-- decorator:getProperty property="meta.menu" / --><!-- /c:set -->
			<decorator:body />
		<!-- //Body -->
	
		<!-- Footer -->
			<jsp:include page="/common/edi/srm/evl-footer.jsp"/>
		<!-- //Footer -->
	
	</div>
	
</body>
</html>
