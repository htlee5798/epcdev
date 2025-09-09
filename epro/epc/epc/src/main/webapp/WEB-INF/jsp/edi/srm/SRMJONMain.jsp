<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMJONMain.jsp
	Description : 입점상담 메인화면
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.10.25  	SHIN SE JIN		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code='text.srm.field.VendorConsult1' /></title>	<%--입점상담 --%>
</head>

<body>
	<!--Container-->
	<div id="container" class="main">
		<div class="m_txt">
			<h2><img src="/images/epc/edi/srm/main/m_thanks.png" alt="롯데마트 입점 상담신청에 관심을 가져주셔서 대단히 감사합니다."></h2>
			<p class="mt25"><spring:message code='text.srm.field.srmjonMainNotice1' /></p>	<%--롯데마트는 개방적인 입점품평회를 실시하여... --%>
			<p class="mt30">
				<a href="<c:url value="/edi/srm/SRMJON0020.do" />" class="m_btn"><spring:message code='text.srm.field.VendorConsult2'/><img src="/images/epc/edi/srm/main/icon_apply.png" alt=""></a>
				<a href="<c:url value="/edi/srm/SRMSTP0010.do" />" class="m_btn"><spring:message code='text.srm.filed.step.process'/><img src="/images/epc/edi/srm/main/icon_process.png" alt=""></a>
<%--				<br><br><a href="#" class="m_btn" onClick="alert('푸드코트/임대매장 입점상담은 tenant1@lottemart.com로 메일 문의 바랍니다.')"><spring:message code='text.srm.field.step.process10'/><img src="/images/epc/edi/srm/main/icon_apply.png" alt=""></a>--%>
<%--				<br><br><a href="<c:url value="/edi/srm/SRMQNA0010.do" />" class="m_btn"><spring:message code='text.srm.field.step.process10'/><img src="/images/epc/edi/srm/main/icon_apply.png" alt=""></a>--%>
				<br><br><a href="<c:url value="/edi/srm/SRMQNA0010.do" />" class="m_btn">패션/F&B/리빙/헬스 테넌트 입점상담문의<img src="/images/epc/edi/srm/main/icon_apply.png" alt=""></a>
			</p>
		</div>
	</div><!--END Container-->
</body>
</html>