<%@ page pageEncoding="UTF-8"%>


<%--
	Page Name 	: SRMJON002003.jsp
	Description : 임접심사 안내 문구 팝업창 
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2021.03.25  	PARK IL YOUNG   최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ include file="/common/edi/taglibs.jsp"%>

<title><spring:message code='text.srm.field.message' /></title><%--Message --%>

<script language="JavaScript">
	/* 화면 초기화 */
	
	$(document).ready(function() {
		
	
	});
</script>


</head>
<body>
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>
				<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span> <span><spring:message code='text.srm.field.consultList' /></span></p>
			</div><!-- END 서브상단 -->
			

			
			<!-- Paging 영역 start --------------------------->
			<div id="pages">
				<div id="paging" class="board-pager mt30"></div>
			</div>
			<!-- Paging 영역 end --------------------------->
		
		</div><!-- END Sub Wrap -->
	</div><!--END Container-->

	<form name="hiddenForm" id="hiddenForm" method="post">
	
	</form>
</body>
</html>