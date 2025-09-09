<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="/css/style_1024.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
	<script type="text/javascript" src="/js/epc/common.js"></script>
	

	
	<script>
		function doAction(code,name,topUrl)
		{
			var form = document.forms[0];
			form.target="lnbFram";
			form.action="<c:url value='/edi/main/viewEdiLeftOld.do'/>";
			form.pgm_code.value=code;
			form.pgm_name.value=name;
			form.submit();
			
			
			parent.contentFram.location.href=topUrl;
			form.action = "";
			form.target = "";
			
			
		}

		function doScm()
		{
			var url = '<c:url value="/main/viewScmMain.do"/>';
			parent.location.href=url;
		}
		
		function doEdi()
		{
			var url = '<c:url value="/edi/main/viewEdiMain.do"/>';
			parent.location.href=url;
		}
		
		function doSrm()
		{
			var url = '<c:url value="/edi/main/viewSrmMain.do"/>';
			parent.location.href=url;
		}
		
		function doLogout()
		{
			var form = document.forms[0];
			var url = '<c:url value="/common/epcLogout.do"/>';
			form.target="_parent";
			form.action=url;
			form.submit();
		}
		
		function chgBman()
		{
	
			var targetUrl = '<c:url value="/common/selectPartnerFirmsPopupSSLADMIN.do"/>'
			Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 550});
			
			
		}
		
		function goHome(language)
		{
			var url = '<c:url value="/main/vendorIntro.do"/>?lang=' + language;
			parent.location.href=url;
		}	
		
		
	</script>	
	
</head>


<body>
<form name="frm" method="post" target="">
	<input type="hidden" name="pgm_code">
	<input type="hidden" name="pgm_name">
</form>
	<!-- header -->
	<div id="header">
		<div class="header_box">
			<h1 class="hlogo"><a href="#" onClick="goHome();"><img width="136" height="25" src="/images/epc/layout/logo_scm.gif" alt="LOTTE MART Back Office System" /></a></h1>
			<!-- 00 : util -->
			<div class="hutil" style="left:300px;">
				<ul>
					<li><a href="#" onClick="doScm();">SCM</a></li>
					<li><a href="#" onClick="doEdi();">EDI</a></li>
					<li class="on"><a href="#">구 EDI</a></li>					
					<li><a href="#" onClick="doSrm();">SRM</a></li>	
				</ul>
			</div>
			<!-- 00 : util // -->
			<div class="hname">
				<c:choose>			
					<c:when test="${not empty epcLoginVO.adminId}">			
								<strong>${epcLoginVO.adminId}(${epcLoginVO.loginNm})</strong>님이 로그인 하셨습니다.
					</c:when>
					<c:otherwise>
								<strong>${epcLoginVO.loginNm}</strong>님이 로그인 하셨습니다. 
					</c:otherwise>
				</c:choose>	

			<c:if test="${not empty epcLoginVO.adminId}">
						
				
					<a href="#"><img src="/images/epc/layout/top_admin_s.gif" onClick="chgBman();" alt="로그아웃" /></a>			
			</c:if>
			
				<a href="#"><img src="/images/epc/layout/header_btn_logout.gif" onClick="doLogout();" alt="로그아웃" /></a>
			</div>
			<!-- 01 : menu -->
			<div class="hmenu">
				<ul>
					<li><a href="#" onClick="doAction('ORD','발주정보','<c:url value="/edi/order/PEDMORD0001.do"/>');">발주정보</a></li>
					<li><a href="#" onClick="doAction('BUY','매입정보','<c:url value="/edi/buy/PEDMBUY0001.do"/>');">매입정보</a></li>
					<li><a href="#" onClick="doAction('USP','미납정보','<c:url value="/edi/usply/PEDMUSP0001.do"/>');">미납정보</a></li>
					<li><a href="#" onClick="doAction('SAL','매출정보','<c:url value="/edi/sale/PEDMSAL0001.do"/>');">매출정보</a></li>
					<li><a href="#" onClick="doAction('INV','재고정보','<c:url value="/edi/inventory/PEDMINV0001.do"/>');">재고정보</a></li>
					<li><a href="#" onClick="doAction('PAY','결산정보','<c:url value="/edi/payment/PEDMPAY0001.do"/>');">결산정보</a></li>
					<li><a href="#" onClick="doAction('DLY','배달정보','<c:url value="/edi/delivery/PEDMDLY0001.do"/>');">배달정보</a></li>
					<li><a href="#" onClick="doAction('PRO','상품','<c:url value="/edi/product/PEDMPRO0001.do"/>');">상품</a></li>
					<li><a href="#" onClick="doAction('CST','협업','<c:url value="/edi/consult/PEDMCST0007.do"/>');">협업</a></li>
				    <li><a href="#" onClick="doAction('WOD','웹발주','<c:url value="/edi/weborder/PEDMWEB0001.do"/>');">웹발주</a></li>
			
					
					<!-- <li><a href="#" onClick="goHome('ko');">KOR</a> | <a href="#" onClick="goHome('en');">ENG</a></li> -->
					<!--테스트  -->
				</ul>
			</div>
			<!-- 01 : menu // -->
		</div>
	</div>


</body>
</html>