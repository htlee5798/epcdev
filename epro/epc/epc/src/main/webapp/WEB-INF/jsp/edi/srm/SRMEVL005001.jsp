<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMEVL005001.jsp
	Description : 평가총평 상세보기 팝업
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.11 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="text.srm.field.srmevl005001.title"/></title><%--spring:message : 평가결과--%>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">
	
</head>

<body>
<form id="MyForm" name="MyForm" method="post">

	<!-- popup wrap -->
	<div id="popup_wrap_full">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code='text.srm.field.srmevl005001.sub.title1'/></h2>	<%-- 평가결과 상세--%>
		</div>
	
		<table class="tbl_st1 form_style">
			<colgroup>
				<col width="5%"/>
				<col width="30%"/>
				<col width="8%"/>
				<col width="*"/>
				<col width="13%"/>
			</colgroup>
			<thead>
				<tr>
					<th colspan="2"><spring:message code="table.srm.srmevl005001.colum.title2"/></th><%--spring:message : 점검항목별 획득점수(100점 기준)--%>
					<th><spring:message code="table.srm.colum.title.no"/></th><%--spring:message : No--%>
					<th><spring:message code="table.srm.srmevl005001.colum.title3"/></th><%--spring:message : 세부 항복별--%>
					<th><spring:message code="table.srm.srmevl005001.colum.title4"/></th><%--spring:message : 획득 %점수--%>
				</tr>
			</thead>
			<tbody>
				<c:set var="preEvItemType1CodeName" value="" />
				<c:set var="preCount" value="0" />
			<%-- 	<c:set var="evTotScoreVal" value="0" />
				<c:set var="evTotScore" value="0" /> --%>
				<c:forEach var="list" items="${list}" varStatus="status">
					<%-- <c:set var="evTotScoreVal" value="${evTotScoreVal + list.evIdScoreVal}" />
					<c:set var="evTotScore" value="${evTotScore + list.evIdScore}" /> --%>
					<fmt:parseNumber var="diffScore" type="number" value="${list.evIdScoreVal}" /> 
					<tr>
						<c:if test="${list.evItemType1CodeName ne preEvItemType1CodeName}">							
							<c:set var="preSubCount" value="1" />
							<c:set var="preCount" value="${preCount + 1}" />
							<td rowspan="<c:out value="${list.rowSpan}"/>" style="text-align:center;">
								<c:out value="${preCount}"/>
							</td>
							<td rowspan="<c:out value="${list.rowSpan}"/>" style="text-align:center;">
								<c:out value="${list.evItemType1CodeName}"/>
								  <!-- <br>
								100% -->
							</td>
							<td style="text-align:right;">
								<c:out value="${preCount}"/>.<c:out value="${preSubCount}"/>
							</td>
							<td>
								<c:out value="${list.evItemType2CodeName}"/>
							</td>
							<td style="text-align:right;">								
								<c:if test="${diffScore >=0}">
									<c:out value="${list.evScorePer}"/>%
								</c:if>
								<c:if test="${diffScore <0}">
									<c:out value="${list.evIdScoreVal}"/>%
								</c:if>
							</td>
						</c:if>
						<c:if test="${list.evItemType1CodeName eq preEvItemType1CodeName}">
							<c:set var="preSubCount" value="${preSubCount + 1}" />
							<td style="text-align:right;">
								<c:out value="${preCount}"/>.<c:out value="${preSubCount}"/>
							</td>
							<td>
								<c:out value="${list.evItemType2CodeName}"/>
							</td>
							<td style="text-align:right;">							
								<c:if test="${diffScore >=0}">
									<c:out value="${list.evScorePer}"/>%
								</c:if>
								<c:if test="${diffScore <0}">
									<c:out value="${list.evIdScoreVal}"/>%
								</c:if>
							</td>
						</c:if>
					</tr>
					<c:set var="preEvItemType1CodeName" value="${list.evItemType1CodeName}" />
				</c:forEach>
	
				<tr>
					<td colspan="3" rowspan="3" class="cell_darkest">
						<spring:message code="table.srm.srmevl005001.colum.title5"/><%--spring:message :본 감사의 총점 요약--%>
					</td>
					<td class="cell_gray">
						<spring:message code="table.srm.srmevl005001.colum.title6"/><%--spring:message : 본감사에서 얻은 총 점수--%>
					</td>
					<td class="cell_gray">
						<c:out value="${siteVisit1.evScore}"/>
					</td>
				</tr>
				<tr>
					<td class="cell_gray">
						<spring:message code="table.srm.srmevl005001.colum.title7"/><%--spring:message : 본감사에서 얻을 수 있는 최고 점수--%>
					</td>
					<td class="cell_gray">
						<c:out value="${siteVisit1.evTotScore}"/>
					</td>
				</tr>
				<tr>
					<td class="cell_darkest">
						<spring:message code="table.srm.srmevl005001.colum.title8"/><%--spring:message : Rating Achieved--%>
					</td>
					<td class="cell_darkest">
						<c:if test="${siteVisit1.evTotScore ne 0 }">
							<%-- <fmt:formatNumber var="totPer" value="${evTotScoreVal/evTotScore}" pattern=".00"/>
							<fmt:formatNumber value="${totPer*100}" pattern="###"/>%
							 --%>
							<fmt:formatNumber value="${siteVisit1.evScorePer}" pattern="###"/>%
							
						</c:if>
						<c:if test="${siteVisit1evTotScore eq 0 }">
							0%
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	
	</div>
				
</form>

</body>
</html>
