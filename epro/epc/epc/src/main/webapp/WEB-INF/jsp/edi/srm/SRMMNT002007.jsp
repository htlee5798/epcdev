<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMMNT002007.jsp
	Description : SRM모니터링 > 개선조치 팝업
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

	<title><spring:message code="text.srm.field.srmmnt002007.title"/></title><%--spring:message : 개선요청--%>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
			if("<c:out value="${data.vendorCode}"/>" == "" && "<c:out value="${result.message}"/>" != "success"){
				alert("<spring:message code="msg.srm.alert.srmjon0030.notListData"/>");
				window.close();
			}
		});

		//파일 다운로드
		function downloadFile(fileId, fileSeq) {
			$('#fileForm input[name=atchFileId]').val(fileId);
			$('#fileForm input[name=fileSn]').val(fileSeq);
			$('#fileForm').attr("action", '<c:url value="/edi/ven/fileDown.do"/>');
			$('#fileForm').submit();
		}

	</script>

</head>


<body style="width:100%;height:100%; overflow-X:hidden;">

<form name="MyForm" id="MyForm" method="post" enctype="multipart/form-data">
	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code="text.srm.field.srmmnt002007.sub.title1"/></h2>	<%-- 대분류 조회--%>
		</div>

		<form id="secrchForm" name="secrchForm" method="post" onsubmit="return false;">
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%" />
					<col width="*" />
				</colgroup>
				<tbody>
				<tr>
					<th><spring:message code="text.srm.field.srmevl0040.sellerNameLoc"/></th><%--srping:message : 파트너사--%>
					<td>
						<c:out value="${data.vendorName}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code="text.srm.field.countDate"/></th><%--spring:message : 기준년월--%>
					<td>
						<c:out value="${data.countYear}"/>-<c:out value="${data.countMonth}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code="text.srm.field.impReqContent"/></th><%--spring:message : 저조영역--%>
					<td>
						<div style="height: 100px; overflow-y: scroll; padding-top: 5px;">
							<pre style="white-space: pre-wrap;"><c:out value="${data.impReqContent}"/></pre>
						</div>
					</td>
				</tr>
				<tr>
					<th><spring:message code="text.srm.field.impReqMemp"/></th><%--spring:message : 요청메모--%>
					<td>
						<div style="height: 100px; overflow-y: scroll; padding-top: 5px;">
							<pre style="white-space: pre-wrap;"><c:out value="${data.impReqMemo}"/></pre>
						</div>
					</td>
				</tr>

				<tr>
					<th><spring:message code="text.srm.field.impPlanDueDate"/></th><%--spring:message : 개선완료 예정일--%>
					<td>
						<c:out value="${data.impPlanDueDate}"/>
					</td>
				</tr>

				<tr>
					<th><spring:message code="text.srm.field.impPlanDate"/></th><%--spring:message : 개선완료일--%>
					<td>
						<c:out value="${data.impPlanDate}"/>
					</td>
				</tr>

				<tr>
					<th><spring:message code="text.srm.field.ownerMd"/></th><%--spring:message : 담당자--%>
					<td>
						<c:out value="${data.deptUser}"/>
					</td>
				</tr>

				<tr>
					<th><spring:message code="text.srm.field.impPlanMemo"/></th><%--spring:message : 개선완료 내용--%>
					<td>
						<div style="height: 100px; overflow-y: scroll; padding-top: 5px;">
							<pre style="white-space: pre-wrap;"><c:out value="${data.impPlanMemo}"/></pre>
						</div>
					</td>
				</tr>
				<tr>
					<th><spring:message code="text.srm.field.attachNo"/></th><%--spring:message : 첨부파일--%>
					<td>
						<c:if test="${not empty fileList}">
						<table>
							<tbody id="attachFileTbody">
								<c:forEach var="list" items="${fileList}" varStatus="status">
									<tr>
										<td>
											<div>
												<a id="" name="" href="#" onclick="javascript:downloadFile(<c:out value="${list.fileId}"/>,<c:out value="${list.fileSeq}"/>);"><c:out value="${list.fileNmae}"/></a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</c:if>
					</td>
				</tr>
				</tbody>
			</table><!-- END 정보 입력폼 -->
		</form>
	</div><!-- END popup wrap -->
</form>
<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>