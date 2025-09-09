<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMMNT002006.jsp
	Description : SRM모니터링 상세 > 불량등록리스트 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code="text.srm.field.srmmnt002006.title"/> </title><%--spring:message : 불량등록리스트--%>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">

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
	<!-- popup wrap -->
	<div id="popup_wrap_full">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code="text.srm.field.srmmnt002006.sub.title1"/> </h2>	<%-- 불량등록리스트--%>
		</div>

		<div style="width:100%; height:350px;overflow-x:hidden; table-layout:fixed; word-break:break-all;">
			<table class="tbl_st1">
				<colgroup>
					<col width="15%" />
					<col width="15%" />
					<col width="20%" />
					<col width="10%" />
					<col width="10%" />
					<col width="*" />
				</colgroup>
				<thead>
				<tr>
					<th><spring:message code="table.srm.srmmst002006.colum.title2"/> </th>
					<th><spring:message code="table.srm.srmmst002006.colum.title3"/> </th>
					<th><spring:message code="table.srm.srmmst002006.colum.title4"/> </th>
					<th><spring:message code="table.srm.srmmst002006.colum.title5"/> </th>
					<th><spring:message code="table.srm.srmmst002006.colum.title6"/> </th>
					<th><spring:message code="table.srm.srmmst002006.colum.title7"/> </th>
				</tr>
				</thead>
				<tbody>
					<c:if test="${not empty list}">
						<c:forEach var="list" items="${list}" varStatus="status">
							<tr>
								<td style="text-align: center;"><c:out value="${list.docno}"/></td>
								<td style="text-align: center;"><c:out value="${list.skuNumber}"/></td>
								<td><c:out value="${list.skuName}"/></td>
								<td style="text-align: center;"><c:out value="${list.statsText}"/></td>
								<td style="text-align: center;"><c:out value="${list.prownText}"/></td>
								<td><c:out value="${list.ldesc}"/></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty list}">
						<tr><td colspan="6"><spring:message code="msg.srm.alert.srmjon0030.notListData"/> </td></tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<!-- END 정보 입력폼 -->
		<p class="align-c mt10"><input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%></p>
	</div><!-- END popup wrap -->


</body>
</html>