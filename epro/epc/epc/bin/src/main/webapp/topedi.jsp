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
	
	<script>
		function doAction(code,name)
		{
			var form = document.forms[0];

			
			form.pgm_code.value=code;
			form.pgm_name.value=name;
			form.submit();
		}

		function doScm()
		{
			var url = '<c:url value="/main/viewScmMain.do"/>';
			parent.location.href=url;
		}
	</script>	
	
</head>


<body>
<form name="frm" target="lnbFram" action="leftedi.jsp">
	<input type="hidden" name="pgm_code">
	<input type="hidden" name="pgm_name">
</form>
	<!-- header -->
	<div id="header">
		<div class="header_box">
			<h1 class="hlogo"><a href="#"><img width="136" height="25" src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/logo_scm.gif" alt="LOTTE MART Back Office System" /></a></h1>
			<!-- 00 : util -->
			<div class="hutil">
				<ul>
					<li><a href="#" onClick="doScm();">SCM</a></li>
					<li class="on"><a href="#">EDI</a></li>
				</ul>
			</div>
			<!-- 00 : util // -->
			<div class="hname">
				<strong>EDI</strong>님이 로그인 하셨습니다.
				<a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/header_btn_logout.gif" alt="로그아웃" /></a>
			</div>
			<!-- 01 : menu -->
			<div class="hmenu">
				<ul>
					<li><a href="#" onClick="doAction('ORD','발주정보');">발주정보</a></li>
					<li><a href="#" onClick="doAction('BUY','매입정보');">매입정보</a></li>
					<li><a href="#" onClick="doAction('USP','미납정보');">미납정보</a></li>
					<li><a href="#" onClick="doAction('SAL','매출정보');">매출정보</a></li>
					<li><a href="#" onClick="doAction('INV','재고정보');">재고정보</a></li>
					<li><a href="#" onClick="doAction('PAY','결산정보');">결산정보</a></li>
					<li><a href="#" onClick="doAction('DLY','배달정보');">배달정보</a></li>
					<li><a href="#" onClick="doAction('PRO','상품');">상품</a></li>
					<li><a href="#" onClick="doAction('CST','협업');">협업</a></li>
				</ul>
			</div>
			<!-- 01 : menu // -->
		</div>
	</div>


</body>
</html>