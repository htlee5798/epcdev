<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
</head>
<script>
	function doEdi()
	{

		parent.top.lnbFram.location.href="leftedi.jsp";
		parent.top.contentFram.location.href="/edi/order/PEDMORD00101.do";
		location.href="topedi.jsp";
	}
</script>

<body>
	<!-- header -->
	<div id="header">
		<div class="header_box">
			<h1 class="hlogo"><a href="#"><img width="136" height="25" src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/logo_scm.gif" alt="LOTTE MART Back Office System" /></a></h1>
			<!-- 00 : util -->
			<div class="hutil">
				<ul>
					<li class="on"><a href="#">SCM</a></li>
					<li><a href="#" onClick="doEdi();">EDI</a></li>
				</ul>
			</div>
			<!-- 00 : util // -->
			<div class="hname">
				<strong>SCM</strong>님이 로그인 하셨습니다.
				<a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/header_btn_logout.gif" alt="로그아웃" /></a>
			</div>
			<!-- 01 : menu -->
			<div class="hmenu">
				<ul>
					<li><a href="#">상품관리</a></li>
					<li><a href="#">배송관리</a></li>
					<li><a href="#">게시판관리</a></li>
					<li><a href="#">정산관리</a></li>
					<li><a href="#">주문관리</a></li>
					<li><a href="#">시스템관리</a></li>
					<li><a href="#">통계</a></li>
				</ul>
			</div>
			<!-- 01 : menu // -->
		</div>
	</div>


</body>
</html>