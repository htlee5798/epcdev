<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002006.jsp
	Description : 입점상담 결과확인 > 진행현황 > 정보확인 팝업(해외)
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.11.16		LEE HYOUNG TAK	최초생성
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
		
		function downloadFile(fileId, fileSeq) {
			$('#fileForm input[name=atchFileId]').val(fileId);
			$('#fileForm input[name=fileSn]').val(fileSeq);
			$('#fileForm').attr("action", '<c:url value="/edi/srm/FileDown.do"/>')
			$('#fileForm').submit();
		}

		//닫기
		function func_ok() {
			window.close();
		}
	</script>

</head>


<body>
<form id="MyForm" name="MyForm" method="post">
	<!-- popup wrap -->
	<div id="popup_wrap_full">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<h2 class="tit_star"><spring:message code='text.srm.field.srmjon0044.sub.title1'/></h2>	<%--요청정보 --%>

		<table class="tbl_st1 form_style">
			<colgroup>
				<col width="20%"/>
				<col width="*"/>
				<col width="20%"/>
				<col width="*"/>
			</colgroup>
			<tr>
				<th><spring:message code='text.srm.field.channelCode'/></th><%--spring:message : 채널--%>
				<td>
					<c:out value="${srmComp.channelCodeNm}"/>
				</td>
				<th><spring:message code='text.srm.field.catLv1Code'/></th><%--spring:message : 대분류--%>
				<td>
					<c:out value="${srmComp.catLv1CodeNm}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.shipperType'/></th><%--spring:message : 해외업체구분--%>
				<td>
					<c:out value="${srmComp.shipperTypeNm}"/>
				</td>
				<th><spring:message code='text.srm.field.irsNo'/></th><%--spring:message : 사업자 등록번호--%>
				<td>
					<c:out value="${srmComp.irsNo}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.sellerNameLoc'/></th><%--spring:message : 상호명--%>
				<td colspan="3">
					<c:out value="${srmComp.sellerNameLoc}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.sellerCeoName'/></th><%--spring:message : 대표자명--%>
				<td>
					<c:out value="${srmComp.sellerCeoName}"/>
				</td>
				<th><spring:message code='text.srm.field.vMainName'/></th><%--spring:message : 담당자명--%>
				<td>
					<c:out value="${srmComp.vMainName}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.vPhone1'/></th><%--spring:message : 대표전화--%>
				<td>
					<c:out value="${srmComp.vPhone1}"/>
				</td>
				<th><spring:message code='text.srm.field.vEmail'/></th><%--spring:message : E-Mail--%>
				<td>
					<c:out value="${srmComp.vEmail}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.faxFhone'/></th><%--spring:message : FAX--%>
				<td colspan="3">
					<c:out value="${srmComp.faxFhone}"/>
				</td>
				<%--<th><spring:message code='text.srm.field.phoneNo'/></th>&lt;%&ndash;spring:message : 담당자연락처&ndash;%&gt;--%>
				<%--<td>--%>
					<%--<c:out value='${srmComp.phoneNo}'/>--%>
				<%--</td>--%>

			</tr>
			<tr>
				<th><spring:message code='text.srm.field.address'/></th><%--spring:message : 주소--%>
				<td colspan="3">
					<c:out value="${srmComp.address1}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.country'/></th><%--spring:message : 국가--%><%--spring:message : 지역--%>
				<td colspan="3">
					<c:out value="${srmComp.countryNm}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.foundationDate'/></th><%--spring:message : 설립년도--%>
				<td>
					<c:out value="${srmComp.foundationDate}"/> <spring:message code='text.srm.field.year' /><%--spring:messge : 년--%>						</td>
				<th><spring:message code='text.srm.field.basicAmt'/></th><%--spring:message : 자본금--%>
				<td>
					<fmt:formatNumber value="${srmComp.basicAmt}" pattern="#,###" /><spring:message code='text.srm.field.unit'/><%--spring:message : (단위:백만원)--%>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.salesAmt'/></th><%--spring:message : 연간 매출액(최근 3년)--%>
				<td>
					<fmt:formatNumber value="${srmComp.salesAmt}" pattern="#,###" /><spring:message code='text.srm.field.unit'/><%--spring:message : (단위:백만원)--%>
				</td>
				<th><spring:message code='text.srm.field.empCount'/></th><%--spring:message : 종업원 수(정규직)--%>
				<td>
					<fmt:formatNumber value="${srmComp.empCount}" pattern="#,###" /><spring:message code='text.srm.field.persons'/><%--spring:message : 명--%>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.plantOwnType'/></th><%--spring:message : 생산공장유무--%>
				<td colspan="3">
					<c:if test="${srmComp.plantOwnType eq 'X'}"> <spring:message code='text.srm.field.srmjon0043.plantOwnType1' /></c:if><%--spring:message : 유--%>
					<c:if test="${srmComp.plantOwnType ne 'X'}"> <spring:message code='text.srm.field.srmjon0043.plantOwnType2' /></c:if><%--spring:message : 무--%>
				</td>
			</tr>
		</table>
		<%----- 기업정보 End -------------------------%>
			
		<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
	</div>
</form>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>