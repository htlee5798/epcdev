<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002003.jsp
	Description : 입점상담 결과확인 > 진행현황 > 품질경영평가 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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

<form name="MyForm"  id="MyForm" method="post">
	<div class="con_area" style="width:650px;">
		<div id="p_title1">
			<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
		</div>
		<p style="margin-top: 10px;" />
		<div class="div_tit_dot">
			<div class="div_tit">
				<tt><spring:message code="text.srm.field.grade"/></tt><%--spring:message : 등급--%>
			</div>
		</div>

		<div class="tbl_default">
			<table>
				<colgroup>
					<col width="10%" />
					<c:forEach var="list" items="${data}" varStatus="status">
						<col width="*" />
					</c:forEach>
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code="text.srm.field.grade"/></th><%--spring:message : 등급--%>
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
			</table>
		</div>

		<div class="div_btn">
			<input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%>
		</div>
	</div>

</form>

</body>
</html>