<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002003.jsp
	Description : 입점상담 결과확인 > 진행현황 > 품질경영평가 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code='text.srm.field.srmrst002005.title1'/></title><%--spring:message : 평가기관 정보--%>

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
		});


		//닫기
		function func_ok() {
			window.close();
		}
	</script>

</head>


<body>
<form name="MyForm"  id="MyForm" method="post">
	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002005.sub.title1'/></h2>	<%--품질경영평가 기관 정보 --%>
		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody>
			
				<tr>
					<th><spring:message code='text.srm.field.evlCompNm'/></th>	<%--평가기관명 --%>
					<td><c:out value="${evlComp.sellerNameLoc}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.vMainName'/></th>	<%--담당자명 --%>
					<td><c:out value="${evlComp.evCtrlName}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.vPhone1'/></th>	<%--대표전화 --%>
					<td><c:out value="${evlComp.evCtrlPhone}"/></td>
				</tr>
				
				<c:if test="${not empty evlComp.checkDate}">
					<tr>
						<th><spring:message code='text.srm.field.checkDate'/></th><%--대표번호 --%>
						<td>
							${fn:substring(evlComp.checkDate,0,4)}-${fn:substring(evlComp.checkDate,4,6)}-${fn:substring(evlComp.checkDate,6,8)} <c:out value="${evlComp.checkTime}"/>:00
						</td>
					</tr>
				</c:if>
				
			</tbody>
		</table>

		<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
	</div><!-- END popup wrap -->
</form>
</body>
</html>