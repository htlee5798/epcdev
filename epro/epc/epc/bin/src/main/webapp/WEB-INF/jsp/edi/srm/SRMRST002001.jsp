<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002001.jsp
	Description : 입점상담 결과확인 > 진행현황 > 상담이력 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
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

		//파일다운로드
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
<form name="MyForm"  id="MyForm" method="post">
<!-- <form name="MyForm"  id="MyForm" method="post" enctype="multipart/form-data"> -->
	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002001.sub.title1'/></h2>	<%--상담요청 상세 --%>
		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th><spring:message code='text.srm.field.expectedDate'/></th>	<%--예정일 --%>
					<td>
						<c:out value="${fn:substring(srmCompCounsel.expectedDate,0,4)}"/>-<c:out value="${fn:substring(srmCompCounsel.expectedDate,4,6)}"/>-<c:out value="${fn:substring(srmCompCounsel.expectedDate,6,8)}"/>&nbsp;<c:out value="${fn:substring(srmCompCounsel.expectedTime,0,2)}"/>:00
					</td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.expectedPlace'/></th>	<%--장소 --%>
					<td><c:out value="${srmCompCounsel.expectedPlace}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.ownerMd'/></th>	<%--담당자 --%>
					<td><c:out value="${srmCompCounsel.ownerMd}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.phone'/></th>	<%--전화번호 --%>
					<td><c:out value="${srmCompCounsel.phone}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.expectedRemark'/></th>	<%--요청내용 --%>
					<td>
						<div style="height: 70px; overflow-y: scroll; padding-top: 5px;">
							<pre style="white-space: pre-wrap;"><c:out value="${srmCompCounsel.expectedRemark}"/></pre>
						</div>
					</td>
					<!-- <td><textarea name="" id="" cols="30" rows="5"></textarea></td> -->
				</tr>
				
			</tbody>
		</table>
		
		<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002001.sub.title2'/></h2>	<%--상담결과 --%>
		<table class="tbl_st1">
			<colgroup>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody>
			
				<tr>
					<th><spring:message code='text.srm.field.changeDate'/></th>	<%--처리일시 --%>
					<td colspan="3"><c:out value="${srmCompCounsel.changeDate}"/></td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.counselStatusName'/></th>	<%--처리결과 --%>
					<td colspan="3"><c:out value="${srmCompCounsel.counselStatusName}"/></td>
				</tr>
				
				<c:if test="${srmCompCounsel.counselStatus eq 'C02'}">
					<tr>
						<th><spring:message code='text.srm.field.rejectReason'/></th>	<%--거절사유 --%>
						<td colspan="3">
							<div style="height: 70px; overflow-y: scroll; padding-top: 5px;">
								<pre style="white-space: pre-wrap;"><c:out value="${srmCompCounsel.rejectReason}"/></pre>
							</div>
						</td>
					</tr>
				</c:if>
				
				<%--<tr>--%>
					<%--<th><spring:message code='text.srm.field.attachNo'/></th>&lt;%&ndash;spring:message : 첨부파일&ndash;%&gt;--%>
					<%--<td colspan="3">--%>
						<%--<div style="max-height: 100px; min-height: 15px;overflow-y: scroll">--%>
							<%--<c:forEach var="list" items="${srmCompCounselFileList}" varStatus="status">--%>
								<%--<div><a href="#" onclick="javascript:downloadFile(<c:out value="${list.fileId}"/>, <c:out value="${list.fileSeq}"/>);"><c:out value="${list.fileNmae}"/></a></div>--%>
							<%--</c:forEach>--%>
						<%--</div>--%>
					<%--</td>--%>
				<%--</tr--%>
				
			</tbody>
		</table>

		<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
	</div><!-- END popup wrap -->
</form>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>