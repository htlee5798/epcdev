<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002008.jsp
	Description : 입점상담 결과확인 > 진행현황 > 임대거절 사유 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2017.01.03		SHIN SE JIN		최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=1100">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code='text.srm.field.srmrst002001.title1'/></title><%--spring:message : 상담이력--%>
	
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
<!-- <form name="MyForm"  id="MyForm" method="post" enctype="multipart/form-data"> -->
	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002008.sub.title1'/></h2>	<%--입점거절 상세 --%>
		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th><spring:message code='text.srm.field.requestDate'/></th>	<%--접수일자 --%>
					<td>
						<c:out value="${reasonInfo.receiptDate}"/>
					</td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.ownerMd'/></th>	<%--담당자 --%>
					<td><c:out value="${reasonInfo.ownerMd}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.phone'/></th>	<%--전화번호 --%>
					<td><%-- <c:out value="${reasonInfo.phone}"/> --%></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.changeDate'/></th>	<%--처리일시 --%>
					<td colspan="3"><c:out value="${reasonInfo.changeDate}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.counselStatusName'/></th>	<%--처리결과 --%>
					<td colspan="3"><c:out value="${reasonInfo.processStatusName}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.rejectReason'/></th>	<%--거절사유 --%>
					<td colspan="3">
						<div style="height: 70px; overflow-y: scroll; padding-top: 5px;">
							<pre style="white-space: pre-wrap;"><c:out value="${reasonInfo.rejectReason}"/></pre>
						</div>
					</td>
				</tr>
				
			</tbody>
		</table>
		
		<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
	</div><!-- END popup wrap -->
</form>

</body>
</html>