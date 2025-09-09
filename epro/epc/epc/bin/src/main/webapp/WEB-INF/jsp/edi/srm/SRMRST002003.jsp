<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002003.jsp
	Description : 입점상담 결과확인 > 진행현황 > 이행보증증권 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code='text.srm.field.srmrst002003.title1'/></title><%--spring:message : 이행보증증권--%>

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {

			if("<c:out value="${save}"/>" == "Y") {
				alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/
				$("#MyForm").attr("action", "<c:url value='/edi/srm/selectCompInsInfoPopup.do' />");
				$("#MyForm").submit();
			}

			$("input[name=insAttachNoFile]").live("change", function () {
				<%--var file = this.files[0];--%>
				<%--var fileSize = file.size;--%>

				<%--if (fileSize > 52428800) {--%>
					<%--alert("<spring:message code='msg.srm.alert.validation.filesize'/>");/*spring:message : 파일 사이즈는 50M 첨부할 수 없습니다.*/--%>
					<%--$(this).val("");--%>
				<%--}--%>
			});
		});
		
		// 허용문자체크 메시지
		function setPermitMsg(target) {	
			var permitMsg = "<spring:message code='text.srm.alert.permitCheck' arguments='"+target+"'/>";
			return permitMsg;
		}
		
		// Byte체크 메시지
		function setByteMsg(target, size) {	
			var byteMsg = "<spring:message code='text.srm.alert.byteCheck' arguments='"+target+","+size+"'/>";
			return byteMsg;
		}

		//validation
		function validation() {
			
			var target = "<spring:message code='text.srm.field.insNo' />";	// 보증증권번호
			if (!$.trim($('#MyForm input[name=insNo]').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 보증증권번호을(를) 입력하세요.*/
				$('#MyForm input[name=insNo]').focus();
				return false;
			}
			
			if (!cal_3byte($('#MyForm input[name=insNo]').val(), '50', setPermitMsg(target), setByteMsg(target, '50'))) {
				$('#MyForm input[name=insNo]').focus();
				return false;
			}
			target = "<spring:message code='text.srm.field.insCompanyName' />";	// 보증보험사
			if (!$.trim($('#MyForm input[name=insCompanyName]').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 보증보험사을(를) 입력하세요.*/
				$('#MyForm input[name=insCompanyName]').focus();
				return false;
			}
			if (!cal_3byte($('#MyForm input[name=insCompanyName]').val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
				$('#MyForm input[name=insCompanyName]').focus();
				return false;
			}
			
			target = "<spring:message code='text.srm.field.insAttachNo' />";	// 보증서
			if (!$.trim($('#MyForm input[name=insAttachNoFile]').val()) && !$.trim($('#MyForm input[name=insAttachNo]').val())) {
				alert("<spring:message code='msg.srm.alert.file' arguments='" + target + "' />");/*spring:message : 보증서을(를) 첨부하세요.*/
				$('#MyForm input[name=insAttachNoFile]').focus();
				return false;
			}
			
			var file = $('#MyForm input[name=insAttachNoFile]').val();
			var fileExt = file.substring(file.lastIndexOf('.')+1);
			var fileName = file.substring(file.lastIndexOf('\\')+1);

			if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.image.ext'/>", fileExt)){
				var target1 = "<spring:message code='text.srm.field.insAttachNo' />";
				var target2 = "<spring:message code='edi.srm.file.image.ext'/>";
				alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*보증서은(는) {1}파일만 업로드 할 수 있습니다.*/
				return false;
			}
			
			target = "<spring:message code='text.srm.field.fileName' />";	// 첨부파일이름
			if (!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
				$('#MyForm input[name=insAttachNoFile]').focus();
				return false;
			}
			return true;
		}

		//등록
		function doSave(tempYn) {
			if(!validation()) return;
			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/
			$('#MyForm input[name=tempYn]').val(tempYn);
			$("#MyForm").attr("action", "<c:url value='/edi/srm/updateCompInsPopup.do'/>");
			$("#MyForm").submit();
		}

		//파일선택시
		function fileUpload(obj, inputName) {
			$('#'+inputName).val($(obj).val());
		}

		//파일초기화
		function fileClear(file, inputName) {
			$('#'+inputName).val("");
			$('#'+file).val("");
		}

		//파일삭제
		function fileDelete(file, aFile) {
			$('#'+file).val("");
			$('#'+aFile).html("");
		}

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
<form name="MyForm"  id="MyForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="seq" name="seq" value="<c:url value="${srmCompIns.seq}"/>"/>
	<input type="hidden" id="tempYn" name="tempYn"/>
	<!-- popup wrap -->
	<div id="popup_wrap">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002003.sub.title1'/></h2>	<%--요청정보 --%>
		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody>
			
				<tr>
					<th><spring:message code='text.srm.field.insReqDate'/></th>	<%--요청일 --%>
					<td>
						<c:out value="${fn:substring(srmCompIns.insReqDate,0,10)}"/>
					</td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.insReqRemark'/></th><%--요청사유(비고) --%>
					<td>
						<div style="height: 100px; overflow-y: scroll;">
							<pre style="white-space: pre-wrap;"><c:out value="${srmCompIns.insReqRemark}"/></pre>
						</div>
					</td>
				</tr>
				
			</tbody>
		</table>
		
		<%----- 이행보증보험 등록 Start -------------------------%>

		<!-- 상단 버튼이 있을 경우 타이틀 -->
		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002003.sub.title2'/></h2>	<%--이행보증보험 등록 --%>
			<c:if test="${srmCompIns.reqStatus eq 'F01'}">
				<div class="right_btns">
					<button type="button" class="btn_normal btn_black" onclick="javascript:doSave('N');"><spring:message code='button.srm.tempSave'/></button><%--신청취소 --%>
					<button type="button" class="btn_normal btn_blue" onclick="javascript:doSave('Y');"><spring:message code='button.srm.insert'/></button><%--신청삭제 --%>
				</div>
			</c:if>
		</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody>
			
				<tr>
					<th><label for="insNo"><spring:message code='text.srm.field.insNo'/></label></th>	<%--보증증권번호 --%>
					<td>
						<c:if test="${srmCompIns.reqStatus eq 'F01' || srmCompIns.reqStatus eq 'F02'}">
							<input type="text" id="insNo" name="insNo" value="<c:out value="${srmCompIns.insNo}"/>"/>
						</c:if>
						<c:if test="${srmCompIns.reqStatus eq 'F03' || srmCompIns.reqStatus eq 'F04'}">
							<c:out value="${srmCompIns.insNo}"/>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<th><label for="insCompanyName"><spring:message code='text.srm.field.insCompanyName'/></label></th>	<%--보증보험사 --%>
					<td>
						<c:if test="${srmCompIns.reqStatus eq 'F01' || srmCompIns.reqStatus eq 'F02'}">
							<input type="text" id="insCompanyName" name="insCompanyName" value="<c:out value="${srmCompIns.insCompanyName}"/>"/>
						</c:if>
						<c:if test="${srmCompIns.reqStatus eq 'F03' || srmCompIns.reqStatus eq 'F04'}">
							<c:out value="${srmCompIns.insCompanyName}"/>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<th><spring:message code='text.srm.field.insAttachNo'/></th>	<%--보증서 --%>
					<td>
						<c:if test="${srmCompIns.reqStatus eq 'F01' || srmCompIns.reqStatus eq 'F02'}">
							<input type="hidden" id="insAttachNoFileName" name="insAttachNoFileName" title="" disabled="disabled" readonly="readonly"/>
							<input type="file" id="insAttachNoFile" name="insAttachNoFile" title="" onchange="javascript:fileUpload(this, 'insAttachNoFileName');"/>
							
							<button type="button" class="btn_normal ml5" onclick="javascript:fileClear('insAttachNoFile','insAttachNoFileName')" ><spring:message code='button.srm.cancel'/></button>	<%--취소 --%>
							<button type="button" class="btn_normal btn_red ml5" onclick="javascript:fileDelete('insAttachNo', 'insAttachNoA');"><spring:message code='button.srm.delete'/></button>	<%--삭제 --%>
							
							<c:if test="${not empty srmCompIns.insAttachNo}">
								<div>
									<input type="hidden" id="insAttachNo" name="insAttachNo" value="<c:out value="${srmCompIns.insAttachNo}"/>"/>
									<a id="insAttachNoA" name="insAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmCompIns.insAttachNo}"/>, '1');"><c:out value="${srmCompIns.insAttachNoName}"/></a>
								</div>
							</c:if>
							
							<div style="color:red;"><spring:message code='text.srm.field.srmjon0042Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 업로드 하세요!--%>
						</c:if>
						
						<c:if test="${srmCompIns.reqStatus eq 'F03' || srmCompIns.reqStatus eq 'F04'}">
							<c:if test="${not empty srmCompIns.insAttachNo}">
							<div>
								<input type="hidden" id="insAttachNo" name="insAttachNo" value="<c:out value="${srmCompIns.insAttachNo}"/>"/>
								<a id="insAttachNoA" name="insAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmCompIns.insAttachNo}"/>, '1');"><c:out value="${srmCompIns.insAttachNoName}"/></a>
							</div>
							</c:if>
						</c:if>
					</td>
				</tr>
				
			</tbody>
		</table>
		<%----- 이행보증보험 등록 End -------------------------%>
		<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
	</div><!-- END popup wrap -->
</form>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>