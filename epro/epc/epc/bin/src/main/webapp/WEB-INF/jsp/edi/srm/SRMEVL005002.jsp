<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMEVL005002.jsp
	Description : 품질경영평가 결과보고서
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.08.02 		LEE HYOUNG TAK 	최초생성
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="text.srm.field.srmevl005002.title1"/></title><%--spring:message : 평가결과보고서--%>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">
	
	<script>
		$(document).ready(function() {
		});
	</script>
	<style>
		pre{
			padding: 10px;
			white-space: pre-wrap; /* CSS3*/
			white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
			white-space: -pre-wrap; /* Opera 4-6 */
			white-space: -o-pre-wrap; /* Opera 7 */
			word-wrap: break-all; /* Internet Explorer 5.5+ */
		}
	</style>
</head>

<body>
<form id="MyForm" name="MyForm" method="post">
<div id="wrap" style="padding-top: 20px; padding-bottom: 20px;min-width: 0px;">
	<div id="con_wrap" style="border:1px solid #000000;">
		<div class="con_area" style="margin-top: 10px;">
			<div>
				<strong>No.<c:out value="${vo.evNo}"/>-<c:out value="${vo.visitSeq}"/></strong>
			</div>
			<div style="padding: 10px; text-align: center; font-size: 20px; font-weight: bold; text-decoration:underline;">
				<spring:message code="text.srm.field.srmevl005002.title1"/><%--spring:message : 품질경영평가 결과 보고서--%>
			</div>

			<div style="margin-top: 10px;"><strong>1. <spring:message code="text.srm.field.srmevl005002.sub.title1"/></strong></div><%--spring:message : 공급업체정보--%>
			<div class="edit_default">
				<table>
					<colgroup>
						<col width="20%"/>
						<col width="*"/>
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="text.srm.field.srmevl005002.sellerNameLoc"/></th><%--spring:message : 업체명--%>
							<td><c:out value="${siteVisit1.sellerNameLoc}"/></td>
						</tr>
						<tr>
							<th><spring:message code="text.srm.field.address"/></th><%--spring:message : 주소--%>
							<td><c:out value="${siteVisit1.sellerAddress}"/></td>
						</tr>
						<tr>
							<th><spring:message code="text.srm.field.srmevl005002.industryType"/></th><%--spring:message : 영업의종류--%>
							<td><c:out value="${siteVisit1.industryType}"/></td>
						</tr>
						<tr>
							<th><spring:message code="text.srm.field.srmevl005002.catLv1CodeName"/></th><%--spring:message : 생산품목--%>
							<td><c:out value="${siteVisit1.catLv1CodeName}"/></td>
						</tr>
						<tr>
							<th><spring:message code="text.srm.field.srmevl005002.mainProduct"/></th><%--spring:message : LotteMart 공급제품--%>
							<td><c:out value="${siteVisit1.mainProduct}"/></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div style="margin-top: 10px;"><strong>2. <spring:message code="text.srm.field.srmevl005002.sub.title2"/></strong></div><%--spring:message : 전체총점 & 감사/감사자 정보--%>
			<div class="edit_default">
				<table>
					<colgroup>
						<col width="20%"/>
						<col width="*"/>
						<col width="20%"/>
						<col width="*"/>
					</colgroup>
					<tbody>
						<tr>
							<th><spring:message code="text.srm.field.evTotScore"/></th><%--spring:message : 획득총점--%>
							<td>
								<%-- <fmt:formatNumber var="totPer" value="${siteVisit1.evScore/siteVisit1.evTotScore}" pattern=".00"/>
								<fmt:formatNumber value="${totPer*100}" pattern="###"/>% --%>
								
								<fmt:formatNumber value="${siteVisit1.evScorePer}" pattern="###"/>%
							</td>
							<th><spring:message code="text.srm.field.evGrade"/></th><%--spring:message : 공급업체등급--%>
							<td><c:out value="${siteVisit1.evGrade}"/></td>
						</tr>
						<tr>
							<th><spring:message code="text.srm.field.evCtrlDate"/></th><%--spring:message : 감사일--%>
							<td>
								<c:out value="${fn:substring(siteVisit1.evCtrlDate,0,4)}"/>-<c:out value="${fn:substring(siteVisit1.evCtrlDate,4,6)}"/>-<c:out value="${fn:substring(siteVisit1.evCtrlDate,6,8)}"/>
							</td>
							<th><spring:message code="text.srm.field.srmevl005002.evalSellerNameLoc"/></th><%--spring:message : 심사업체--%>
							<td><c:out value="${siteVisit1.evalSellerNameLoc}"/></td>
						</tr>
						<tr>
							<th><spring:message code="text.srm.field.evCtrlName"/></th><%--spring:message : 심사원 성명--%>
							<td><c:out value="${siteVisit1.evCtrlName}"/></td>
							<th><spring:message code="text.srm.field.evCtrlPhone"/></th><%--spring:message : 심사원 연락처--%>
							<td>
								<c:out value="${siteVisit1.evCtrlPhone}"/>
								<%--<c:out value="${siteVisit1.evCtrlPhone1}"/>--%>
								<%-----%>
								<%--<c:out value="${siteVisit1.evCtrlPhone2}"/>--%>
								<%-----%>
								<%--<c:out value="${siteVisit1.evCtrlPhone3}"/>--%>
							</td>
						</tr>
						<tr>
							<%--<th>심사원 휴대전화</th>--%>
							<%--<td><c:out value="${siteVisit1.mainProduct}"/></td>--%>
							<th><spring:message code="text.srm.field.evCtrlEmail"/></th><%--spring:message : 심사원 E-mail--%>
							<td colspan="3"><c:out value="${siteVisit1.evCtrlEmail}"/></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div style="margin-top: 10px;"><strong>3. <spring:message code="text.srm.field.srmevl005002.sub.title3"/></strong></div><%--spring:message : 참석자--%>
			<div class="edit_default">
				<table>
					<colgroup>
						<col width="20%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="20%"/>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th><spring:message code="table.srm.srmevl0050.colum.title2"/></th><%--spring:message : 성명--%>
							<th><spring:message code="table.srm.srmevl0050.colum.title3"/></th><%--spring:message : 부서--%>
							<th><spring:message code="table.srm.srmevl0050.colum.title4"/></th><%--spring:message : 직위--%>
							<th><spring:message code="table.srm.srmevl0050.colum.title5"/></th><%--spring:message : 전화번호--%>
							<th><spring:message code="table.srm.srmevl0050.colum.title6"/></th><%--spring:message : E-mail--%>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${siteVisit2List}" varStatus="status">
							<tr>
								<td><c:out value="${list.evPartName}"/></td>
								<td><c:out value="${list.evPartDept}"/></td>
								<td><c:out value="${list.evPartPostion}"/></td>
								<td>
									<c:out value="${list.evPartPhone}"/>
									<%--<c:out value="${list.evPartPhone1}"/>--%>
									<%-----%>
									<%--<c:out value="${list.evPartPhone2}"/>--%>
									<%-----%>
									<%--<c:out value="${list.evPartPhone3}"/>--%>
								</td>
								<td><c:out value="${list.evPartEmail}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div style="margin-top: 10px;"><strong>4. <spring:message code="text.srm.field.srmevl005002.sub.title4"/></strong></div><%--spring:message : 평가결과--%>
			<div class="edit_default">
				<table>
					<colgroup>
						<col width="*"/>
						<col width="20%"/>
						<col width="20%"/>
						<col width="20%"/>
					</colgroup>
					<thead>
						<tr>
							<th><spring:message code="table.srm.srmevl005002.colum.title2"/></th><%--spring:message : 대분류 항목--%>
							<th><spring:message code="table.srm.srmevl005002.colum.title3"/></th><%--spring:message : 항목수--%>
							<th><spring:message code="table.srm.srmevl005002.colum.title4"/></th><%--spring:message : 획득점수--%>
							<th><spring:message code="table.srm.srmevl005002.colum.title5"/></th><%--spring:message : 부적합수--%>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${siteVisitResultList}" varStatus="status">
							<tr>
								<td><c:out value="${status.count}"/>. <c:out value="${list.evItemType1CodeName}"/></td>
								<td style="text-align: center"><c:out value="${list.rowCnt}"/></td>
								<td style="text-align: center"><c:out value="${list.evIdScoreVal}"/></td>
								<%-- 감점이 되는 경우는 %를 붙힌다. 
								<c:if test="${list.evIdScoreVal >=0}">
									<td style="text-align: center"><c:out value="${list.evIdScoreVal}"/></td>
								</c:if>
								<c:if test="${list.evIdScoreVal <0}">
									<td style="text-align: center"><c:out value="${list.evIdScoreVal}"/>%</td>
								</c:if> --%>								
								<td style="text-align: center"><c:out value="${list.incongruityCnt}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div style="margin-top: 10px;"><strong>5. <spring:message code="text.srm.field.srmevl005002.sub.title5"/></strong></div><%--spring:message : 중요 부적합 사항--%>
			<div style="margin-top: 5px; border: 1px solid #ddd; padding: 10 10 10 10;"><pre><c:out value="${siteVisit1.evInconRemark}"/></pre></div>

			<div style="margin-top: 10px;"><strong>6. <spring:message code="text.srm.field.srmevl005002.sub.title6"/></strong></div><%--spring:message : 시정조치--%>
			<div class="edit_default">
				<table>
					<colgroup>
						<col width="30%"/>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th><spring:message code="table.srm.srmevl005002.colum.title6"/></th><%--spring:message : 심사항목--%>
							<th><spring:message code="table.srm.srmevl005002.colum.title7"/></th><%--spring:message : 부적합사항--%>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${siteVisit3List}" varStatus="status">
							<tr>
								<td><c:out value="${list.evItemType1CodeName}"/></td>
								<td><pre><c:out value="${list.impReqRemark}"/></pre></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div style="margin-top: 10px;"><strong>7. <spring:message code="text.srm.field.srmevl005002.sub.title7"/></strong></div><%--spring:message : 심사총평--%>
			<div style="margin-top: 5px; border: 1px solid #ddd; padding: 10 10 10 10"><pre><c:out value="${siteVisit1.evGenView}"/></pre></div>

		</div>
	</div>
</div>
</form>

</body>
</html>
