<%@page pageEncoding="utf-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0); 
%>

<!doctype html>
<html lang="ko">
<head>
	<title><spring:message code='text.srm.field.VendorConsult1' /></title>	<%--입점상담 --%>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="viewport" content="width=1100">
	
	<meta name="Author" content="Design hub">
	<meta name="Author" content="Wookju Choi/wookchu@designhub.kr">
	<meta name="Author" content="LOTTE MART">
	<meta name="designer" content="Miso Choi/yomaspace@designhub.kr">
	
	<%-- <meta name="description" content="사이트설명">
	<meta property="og:description" content="사이트설명">
	<meta property="og:type" content="website">
	<meta property="og:title" content="타이틀">
	<meta property="og:image" content="대표이미지주소(full_url)">
	<meta property="og:url" content="웹사이트url">
	<meta name="url" content="웹사이트url"> --%>
	<%-- <meta name="robots" content="noindex"> --%><%-- 오픈 전 삭제 부탁합니다!!! : 검색엔진로봇 수집을 막음 --%>
	
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	<%-- <link type="text/css" rel="stylesheet" href="/css/epc/edi/srm/@default.css" /> --%>
	
</head>

<body>

	<!--Header-->
	<div id="header">
		<div class="inner">
			<h1 id="logo"><a href="<c:url value="/edi/srm/SRMJONMain.do" />"><img src="/images/epc/edi/srm/common/logo.png" alt="롯데마트 입점상담"></a></h1>
			
			<!-- nav -->
			<div class="gnb_wrap">
				<ul class="gnb">
					<li><a href="<c:url value="/edi/srm/SRMSTP0010.do" />"><spring:message code="header.menu.text1" /></a></li>	<%-- 입점상담 절차안내 --%>
				
					<c:if test="${srmLoginSession != null}">
						<li><a href="<c:url value="/edi/srm/SRMJON002001.do" />"><spring:message code="header.menu.text2" /></a></li>	<%-- 입점상담 신청 --%>
						<li><a href="<c:url value="/edi/srm/SRMRST0020.do" />"><spring:message code="header.menu.text3" /></a></li>		<%-- 입점상담 결과 --%>
					</c:if>
					
					<c:if test="${srmLoginSession == null}">
						<li><a href="<c:url value="/edi/srm/SRMJON0020.do" />"><spring:message code="header.menu.text2" /></a></li>		<%-- 입점상담 신청 --%>
						<li><a href="<c:url value="/edi/srm/SRMRST0010.do" />"><spring:message code="header.menu.text3" /></a></li>		<%-- 입점상담 결과 --%>
					</c:if>
					
					<li><a href="<c:url value="/edi/srm/SRMSPW0010.do" />"><spring:message code="header.menu.text4" /></a></li>			<%-- 비밀번호 변경 --%>
					<%-- <li><a href="<c:url value="/edi/srm/SRMFAQ0010.do" />"><spring:message code="header.menu.text5" /></a></li> --%>			<%-- FAQ --%>
				</ul>
			</div>
			<!-- nav -->
			
			<!-- quick -->
			<c:if test="${srmLoginSession == null}">	<%-- Session이 없는 경우 --%>
				<div class="h_quick">
					<ul class="tnb">
			</c:if>
			
			<c:if test="${srmLoginSession != null}">	<%-- Session이 있는 경우 --%>
				<div class="h_quick2">
					<ul class="tnb2">
			</c:if>
			
			<%-- 
			<div class="h_quick">
				<ul class="tnb"> 
			--%>
					<li><a href="<c:url value="/edi/srm/SRMJONMain.do" />">HOME</a></li>
					<li><a href="<c:url value="/edi/srm/SRMFAQ0010.do" />"><spring:message code="header.menu.text5" /></a></li><%--FAQ --%>
					<%-- <li><img src="/images/epc/edi/srm/common/icon_lang.png" alt="" class="img-mid"> KOR</li>
			
					<c:set var="localeLanguage" value="${sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'].language}" />
					 --%>
					 <%--
					<c:if test="${localeLanguage == 'ko' || localeLanguage == '' || localeLanguage == null}"> --%>
						<%--<li><img src="/images/epc/edi/srm/common/icon_lang.png" alt="" class="img-mid"> <a href="<c:url value="/edi/srm/SRMJONMain.do?lang=en" />">ENG</a></li>--%>
						<%--<li><img src="/images/epc/edi/srm/common/icon_lang.png" alt="" class="img-mid"> <a href="<c:url value="#" />">ENG</a></li>
					</c:if>
					
					<c:if test="${localeLanguage == 'en'}">
						<li><img src="/images/epc/edi/srm/common/icon_lang.png" alt="" class="img-mid"> <a href="<c:url value="/edi/srm/SRMJONMain.do?lang=ko" />">KOR</a></li>
					</c:if>
					 --%>
					<%-- Session이 있는 경우 --%>
					<c:if test="${srmLoginSession != null}">
						<li><a href="<c:url value="/edi/srm/SRMJONLogout.do" />"><spring:message code='text.srm.field.logout' /></a></li>
					</c:if>
					
				</ul>
			</div>
			<!-- END quick -->
		</div>
		<!-- END Inner -->
	</div>
	<!--END Header-->
