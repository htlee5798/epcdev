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
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code="text.srm.field.grade"/></title><%--spring:message : 등급--%>
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
			<h2 class="tit_star"><spring:message code="text.srm.field.grade"/></h2>	<%-- 대분류 조회--%>
		</div>

		<table class="tbl_st1 form_style">
			<colgroup>
				<col width="10%" />
				<c:forEach var="list" items="${data}" varStatus="status">
					<col width="*" />
				</c:forEach>
			</colgroup>
			<tbody>
			<tr>
				<th><spring:message code="text.srm.field.grade"/></th> </th><%--spring:message : 등급--%>
				<c:forEach var="list" items="${data}" varStatus="status">
					<td style="text-align:center;">
						<c:if test="${grade eq list.grade}">
								<span style="font-weight: bold; color: red; font-size: 14px;">
									<c:out value="${list.grade}"/>
								</span>
						</c:if>
						<c:if test="${grade ne list.grade}">
							<c:out value="${list.grade}"/>
						</c:if>
					</td>
				</c:forEach>
			</tr>
			<tr>
				<th><spring:message code="text.srm.field.standard"/></th><%--spring:message : 기준--%>
				<c:forEach var="list" items="${data}" varStatus="status">
					<td style="text-align:center;">
						<c:out value="${list.evIdContents}"/>
					</td>
				</c:forEach>
			</tr>
			</tbody>
		</table><!-- END 정보 입력폼 -->
		<p class="align-c mt10"><input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%></p>
	</div><!-- END popup wrap -->

</body>
</html>