<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMVEN003006.jsp
	Description : SRM모니터링 상세 > 불량등록리스트 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.9.30		LEE HYOUNG TAK	최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>

	<title>불량등록리스트</title><%--spring:message : 불량등록리스트--%>
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
	<div class="con_area" style="width:830px;">
		<div id="p_title1">
			<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
		</div>
		<p style="margin-top: 10px;" />
		<div class="div_tit_dot">
			<div class="div_tit">
				<tt>불량등록리스트</tt><%--spring:message : 불량등록리스트--%>
			</div>
		</div>
		<div style="width:100%; height:350px;overflow-x:hidden; table-layout:fixed; word-break:break-all;">
		<div class="tbl_default">
			<table>
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
						<th>관리번호</th>
						<th>상품코드</th>
						<th>상품명</th>
						<th>상태</th>
						<th>귀책구분</th>
						<th>상세내역</th>
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
		</div>

		<div class="div_btn">
			<input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%>
		</div>
	</div>

</form>

</body>
</html>