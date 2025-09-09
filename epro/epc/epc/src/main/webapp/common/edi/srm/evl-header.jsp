<%@page pageEncoding="utf-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0); 
%>

<script language="javascript">
	/* 로그인 알림 */
	function fnLoginAlert() {
		location.href = "<c:url value="/edi/evl/SRMEVL0010.do" />";
	}
	
	/* 품질경영평가 메뉴얼 다운로드 */
	function manualDown(fileNm) {
		$("#manualDownForm input[name='manulName']").val(fileNm);
		$('#manualDownForm').attr("action", '<c:url value="/edi/srm/manulDown.do"/>');
		$("#manualDownForm").submit();
	}

</script>

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
	
	<!-- <meta name="description" content="사이트설명">
	<meta property="og:description" content="사이트설명">
	<meta property="og:type" content="website">
	<meta property="og:title" content="타이틀">
	<meta property="og:image" content="대표이미지주소(full_url)">
	<meta property="og:url" content="웹사이트url">
	<meta name="url" content="웹사이트url"> -->
	<!-- <meta name="robots" content="noindex"> --><!-- 오픈 전 삭제 부탁합니다!!! : 검색엔진로봇 수집을 막음 -->
	
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	<!-- <link type="text/css" rel="stylesheet" href="/css/epc/edi/srm/@default.css" /> -->
	
</head>

	<!--Header-->
	<div id="header">
		<div class="inner">
			<h1 id="logo"><a href="<c:url value="/edi/evl/SRMEVL0030.do" />"><img src="/images/epc/edi/srm/common/logo.png" alt="롯데마트 입점상담"></a></h1>
			
			<!-- nav -->
			<div class="gnb_wrap">
				<ul class="gnb">
				
						<c:if test="${srmEvlLoginSession == null}">
							<li><a href="#" onClick="fnLoginAlert();"><spring:message code='evlHeader.menu.text2' /></a></li>	<%--품질평가 --%>
							<li><a href="<c:url value="/edi/evl/SRMEVL0060.do" />"><spring:message code='evlHeader.menu.text3' /></a></li>	<%--정기품질평가 --%>
						</c:if>
						
						<c:if test="${srmEvlLoginSession != null}">
							<li><a href="<c:url value="/edi/evl/SRMEVL0030.do" />"><spring:message code='evlHeader.menu.text2' /></a></li>	<%--신규품질평가 --%>
							<li><a href="<c:url value="/edi/evl/SRMEVL0060.do" />"><spring:message code='evlHeader.menu.text3' /></a></li>	<%--정기품질평가 --%>
						</c:if>
		
						<c:if test="${srmEvlLoginSession != null}">
							<li><a href="#" onClick="manualDown('2')"><spring:message code='text.srm.field.step.tab6' /></a></li>	<%--메뉴얼 --%>
						</c:if>
						
						<%--<c:if test="${srmLoginSession != null}">--%>
							<%--<li><a href="<c:url value="/edi/srm/SRMJONLogout.do" />"><spring:message code='text.srm.field.logout' /></a></li>--%>
						<%--</c:if>--%>
						
						<!-- <li style="float:right"><a href="#">ENGLISH</a></li> -->
				</ul>
			</div><!-- nav -->
			
			<!-- quick -->
			<c:if test="${srmEvlLoginSession != null}">
				<div class="h_quick" style="width: 100px;">
					<ul class="tnb">
						
						<%-- <c:if test="${localeLanguage == 'ko' || localeLanguage == '' || localeLanguage == null}">
							<li><img src="/images/epc/edi/srm/common/icon_lang.png" alt="" class="img-mid"> <a href="<c:url value="/edi/srm/SRMJONMain.do?lang=en" />">ENG</a></li>
						</c:if>
						
						<c:if test="${localeLanguage == 'en'}">
							<li><img src="/images/epc/edi/srm/common/icon_lang.png" alt="" class="img-mid"> <a href="<c:url value="/edi/srm/SRMJONMain.do?lang=ko" />">KOR</a></li>
						</c:if> --%>
						
						
							<li style="width: 100%;"><a href="<c:url value="/edi/evl/SRMEVLLogout.do" />"><spring:message code='text.srm.field.logout' /></a></li>
						
						
					</ul>
				</div>
			</c:if>
			<!-- END quick -->
		</div><!-- END Inner -->
	</div><!--END Header-->

