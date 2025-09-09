<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002004.jsp
	Description : 입점상담 결과확인 > 진행현황 > 조치내역 팝업
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

	<title><spring:message code='text.srm.field.srmrst002004.title1'/></title><%--spring:message : 조치내역--%>

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
			//저장인경우
			if("<c:out value="${save}"/>" == "Y") {
				alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/
				$('#MyForm input[name=evNo]').val("<c:url value='${evNo}'/>");
				$('#MyForm input[name=seq]').val("<c:url value='${seq}'/>");
				$("#MyForm").attr("action", "<c:url value='/edi/srm/selectCompSiteVisitCover3Popup.do'/>");
				$("#MyForm").submit();
			}
		});

		//조치 리스트 선택
		function rowSelect(idx){
			$('#srmCompCorver3').find("tr").each(function(){
				$(this).attr("bgcolor","#ffffff");
			});

			$('#srmCompCorver3').find("tr").eq(idx-1).attr("bgcolor","#efefef");

			//선택된 로우 상세 검색
			rowSelectDetail(idx);
		}

		//선택된 로우 상세 검색
		function rowSelectDetail(idx) {

			//data init
			$('#MyForm input[name=evNo]').val("");
			$('#MyForm input[name=seq]').val("");
			$('#MyForm input[name=impSeq]').val("");

			$('#MyForm input[name=impResDate]').val("");

			$('#MyForm textarea[name=impResRemark]').text("");

			$('#MyForm input[name=impAttachFileNo]').val("");

			var saveInfo = {};
			saveInfo["evNo"] = $('#srmCompCorver3').find("#evNo").eq(idx-1).val();
			saveInfo["seq"] = $('#srmCompCorver3').find("#seq").eq(idx-1).val();
			saveInfo["impSeq"] = idx;
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/selectCompSiteVisitCover3Detail.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(data) {
					//data Set
					$('#MyForm input[name=evNo]').val(data.evNo);
					$('#MyForm input[name=seq]').val(data.seq);
					$('#MyForm input[name=impSeq]').val(data.impSeq);

					if(data.impResDate != null) {
						if(data.impStatus == "3") {
							$('#MyForm span[id=impResDateSpan]').text(data.impResDate.substring(0, 4) + "-" + data.impResDate.substring(4, 6) + "-" + data.impResDate.substring(6, 8));
						} else {
							$('#MyForm input[name=impResDate]').val(data.impResDate.substring(0, 4) + "-" + data.impResDate.substring(4, 6) + "-" + data.impResDate.substring(6, 8));
						}
					}

					$('#MyForm textarea[name=impResRemark]').val(data.impResRemark);

					$('#MyForm input[name=impAttachFileNo]').val(data.impAttachFileNo);


					$('#attachFileTbody').empty();
					//조치완료일때
					if(data.impStatus == "3") {
						$('#btnSave').hide();
						$('#btnTempSave').hide();
						$('#btnAddFile').hide();
						$('#calImg').hide();
						$('#btnDelete').hide();
						$('#MyForm input[name=impResDate]').hide();
						$('#MyForm span[id=impResDateSpan]').show();

						$('#MyForm textarea[name=impResRemark]').attr("disabled", "disabled");
						$('#addAttachFileTemplate3').tmpl(data.impAttachFileList).appendTo("#attachFileTbody");
					} else {
						$('#btnSave').show();
						$('#btnTempSave').show();
						$('#btnAddFile').show();
						$('#calImg').show();
						$('#MyForm input[name=impResDate]').show();
						$('#MyForm span[id=impResDateSpan]').hide();

						$('#MyForm textarea[name=impResRemark]').removeAttr("disabled");
						$('#addAttachFileTemplate2').tmpl(data.impAttachFileList).appendTo("#attachFileTbody");

						if(data.impResDate == null && data.impResRemark == null && data.impAttachFileNo == null){
							$('#btnDelete').hide();
						} else {
							$('#btnDelete').show();
						}
					}
				}
			});
		}
		
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
		function validation(){
			
			var target = "<spring:message code='text.srm.field.impResDate' />";	// 시정조치완료일
			if(!$.trim($('#MyForm input[name=impResDate]').val())){
				alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 시정조치완료일을(를) 입력하세요.*/
				$('#MyForm input[name=impResDate]').focus();
				return false;
			}
			
			target = "<spring:message code='text.srm.field.impResRemark' />";	// 시정조치내용
			if(!$.trim($('#MyForm textarea[name=impResRemark]').val())){
				alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 시정조치내용을(를) 입력하세요.*/
				$('#MyForm input[name=impResRemark]').focus();
				return false;
			}
			
			if (!cal_3byte($('#MyForm textarea[name=impResRemark]').val(), '1000', setPermitMsg(target), setByteMsg(target, '1000'))) {
				$('#MyForm textarea[name=impResRemark]').focus();
				return false;
			}
			if($('#MyForm #attachFileTbody tr').length == 0){
				alert("<spring:message code="msg.srm.alert.validation.impAttachFileNoFileLength"/>");/*spring:message : 파일은 최소 1개 이상 등록 하세요.*/
				return false;
			}
			var fileCheck = true;
			$('#MyForm #attachFileTbody tr').each(function(){
				if(!$.trim($(this).find('#impAttachFileNoFile').val()) && !$.trim($(this).find('#docNo').val())){
					fileCheck = false;
					return false;
				}
			});
			
			target = "<spring:message code='text.srm.field.attachNo' />";	// 첨부파일
			if(!fileCheck){
				alert("<spring:message code='msg.srm.alert.file' arguments='" + target + "' />");/*spring:message : 첨부파일을(를) 첨부하세요.*/
				return false;
			}

			var fileCheck = true;
			
			$('#MyForm #attachFileTbody tr').each(function(){
				var file = $(this).find('#impAttachFileNoFile').val();
				var fileExt = file.substring(file.lastIndexOf('.')+1);
				var fileName = file.substring(file.lastIndexOf('\\')+1);
				
				if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.image.ext'/>", fileExt)){
					var target1 = "<spring:message code='text.srm.field.attachNo' />";
					var target2 = "<spring:message code='edi.srm.file.image.ext'/>";
					alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*첨부파일은(는) {1}파일만 업로드 할 수 있습니다.*/
					fileCheck = false;
					return false;
				}
				
				target = "<spring:message code='text.srm.field.fileName' />";	// 첨부파일이름
				if (!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
					fileCheck = false;
					return false;
				}
			});

			if (!fileCheck) return false;

			return true;
		}

		//등록
		function doSave(impStatus) {
			if(!clickCheck()){
				var target = "<spring:message code='text.srm.field.srmrst002004.sub.title1' />";
				alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "' />");/*spring:message : 조치리스트을(를) 선택하세요.*/
				return;
			}

			if(!validation())return;

			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/

			$('#MyForm input[name=impResDate]').removeAttr("disabled");
			$('#MyForm input[name=impStatus]').val(impStatus);
			$("#MyForm").attr("action", "<c:url value='/edi/srm/updateCompSiteVisitCover3DetailPopup.do'/>");
			$("#MyForm").submit();
		}
		//삭제
		function doDelete() {
			if(!clickCheck()){
				var target = "<spring:message code='text.srm.field.srmrst002004.sub.title1' />";
				alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "' />");/*spring:message : 조치리스트을(를) 선택하세요.*/
				return;
			}

			if(!confirm("<spring:message code='msg.srm.alert.confirmDelete'/>")) return;/*spring:message : 삭제하시겠습니까?*/

			var deleteInfo = {};
			deleteInfo["evNo"] = $('#MyForm input[name=evNo]').val();
			deleteInfo["seq"] = $('#MyForm input[name=seq]').val();
			deleteInfo["impSeq"] = $('#MyForm input[name=impSeq]').val();
			deleteInfo["impAttachFileNo"] = $('#MyForm input[name=impAttachFileNo]').val();
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/updateCompSiteVisitCover3Detaildel.json"/>',
				data : JSON.stringify(deleteInfo),
				success : function(data) {

					alert("<spring:message code='msg.srm.alert.ok'/>");/*spring:message : 정상적으로 처리 되었습니다.*/
					$("#MyForm").attr("action", "<c:url value='/edi/srm/selectCompSiteVisitCover3Popup.do'/>");
					$("#MyForm").submit();
				}
			});
		}

		//첨부파일 행 추가
		function addAttachFileRow(){
			if(!clickCheck()){
				var target = "<spring:message code='text.srm.field.srmrst002004.sub.title1' />";
				alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "' />");/*spring:message : 조치리스트을(를) 선택하세요.*/
				return;
			}
			$('#addAttachFileTemplate1').tmpl().appendTo("#attachFileTbody");
		}

		//파일선택시
		function fileUpload(obj, inputName) {
			$(obj).parent().find("#"+inputName).val($(obj).val());
			$(obj).parent().parent().find("#docSeq").val("");
		}

		//파일초기화
		function fileClear(obj, file, fileNm) {
			$(obj).parent().parent().find("#"+file).val("");
			$(obj).parent().parent().find("#"+fileNm).val("");
		}

		//파일삭제
		function fileDelete(obj) {
			$(obj).parent().parent().parent().remove();
			if($("#attachFileTbody tr").length == 0 ) {
				addAttachFileRow();
			};
		}

		//파일 다운로드
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

		//선택 여부 체크
		function clickCheck(){
			var chk = false;
			$('#srmCompCorver3').find("tr").each(function(){
				if($(this).attr("bgcolor") == "#efefef"){
					chk = true;
					return false;
				}
			});
			return chk;
		}
	</script>

<script id="addAttachFileTemplate1" type="text/x-jquery-tmpl">
	<tr>
		<td style="border: none;">
			<input type="hidden" id="impAttachFileName" name="impAttachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
			<input type="hidden" id="docNo" name="docNo" value=""/>
			<input type="hidden" id="docSeq" name="docSeq" value=""/>
			<input type="file" id="impAttachFileNoFile" name="impAttachFileNoFile" title="" onchange="javascript:fileUpload(this, 'impAttachFileName');"/>
			<btn>
				<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'impAttachFileNoFile','impAttachFileName')"/><%--spring:message : 취소--%>
				<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
			<btn>
		</td>
	</tr>
</script>

<script id="addAttachFileTemplate2" type="text/x-jquery-tmpl">
	<tr>
		<td style="border: none;">
			<input type="hidden" id="impAttachFileName" name="impAttachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
			<input type="hidden" id="docNo" name="docNo" value="\${fileId}"/>
			<input type="hidden" id="docSeq" name="docSeq" value="\${fileSeq}"/>
			<input type="file" id="impAttachFileNoFile" name="impAttachFileNoFile" title="" onchange="javascript:fileUpload(this, 'impAttachFileName');"/>
			<btn>
				<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'impAttachFileNoFile','impAttachFileName')"/><%--spring:message : 취소--%>
				<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
			<btn>

			<div>
				<a id="impAttachFileNoA" name="impAttachFileNoA" href="#" onclick="javascript:downloadFile('<c:out value="\${fileId}"/>', '<c:out value="\${fileSeq}"/>');"><c:out value="\${fileNmae}"/></a>
			</div>
		</td>
	</tr>
</script>

<script id="addAttachFileTemplate3" type="text/x-jquery-tmpl">
	<tr>
		<td style="border: none;">
			<div>
			<a id="impAttachFileNoA" name="impAttachFileNoA" href="#" onclick="javascript:downloadFile('<c:out value="\${fileId}"/>', '<c:out value="\${fileSeq}"/>');"><c:out value="\${fileNmae}"/></a>
			</div>
		</td>
	</tr>
</script>

</head>


<body>
<!-- popup wrap -->
<div id="popup_wrap">
	<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

	<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002004.sub.title1'/></h2>	<%--조치리스트 --%>
	
	<div style="height: 200px; overflow-y: scroll;">
		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:5%;">
				<col style="width:33%;">
				<col style="width:50%;">
				<col style="width:12%;">
			</colgroup>
			
			<thead>
				<tr>
					<th><spring:message code="table.srm.colum.title.no"/></th>				<%--No --%>
					<th><spring:message code="table.srm.srmrst002004.colum.title2"/></th>	<%--심사항목 --%>
					<th><spring:message code="table.srm.srmrst002004.colum.title3"/></th>	<%--부적합사항 --%>
					<th><spring:message code="table.srm.srmrst002004.colum.title4"/></th>	<%--상태 --%>
				</tr>
			</thead>
			
			<tbody class="align-c" id="srmCompCorver3">
			
				<c:if test="${not empty srmCompCorver3}">
					<c:forEach var="list" items="${srmCompCorver3}" varStatus="status">
						<tr onclick="javascript:rowSelect('<c:out value="${status.count}"/>');" style="cursor: pointer">
							<td style="text-align: center"><c:out value="${list.impSeq}"/></td>
							<td>
								<c:out value="${list.evItemType1CodeName}"/>
							</td>
							<td>
								<div style="max-height: 100px; min-height: 15px; overflow-y: scroll;">
									<pre style="white-space: pre-wrap;"><c:out value="${list.impReqRemark}"/></pre>
								</div>
							</td>
							<td style="text-align: center">
								<c:choose>
									<c:when test="${list.impStatus eq '0'}">
										<spring:message code="text.srm.field.srmrst002004.status1"/> <%--등록--%>
									</c:when>
									<c:when test="${list.impStatus eq '1'}">
										<spring:message code="text.srm.field.srmrst002004.status2"/> <%--요청--%>
									</c:when>
									<c:when test="${list.impStatus eq '2'}">
										<spring:message code="text.srm.field.srmrst002004.status3"/> <%--임시저장--%>
									</c:when>
									<c:when test="${list.impStatus eq '3'}">
										<spring:message code="text.srm.field.srmrst002004.status4"/> <%--완료--%>
									</c:when>
									<c:otherwise>
										<spring:message code="text.srm.field.srmrst002004.status5"/> <%--미등록--%>
									</c:otherwise>
								</c:choose>
								<input type="hidden" id="evNo" name="evNo" value="<c:out value="${list.evNo}"/>"/>
								<input type="hidden" id="seq" name="seq" value="<c:out value="${list.seq}"/>"/>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				
				<c:if test="${empty srmCompCorver3}">
					<tr>
						<td colspan="4" style="text-align: center"> <spring:message code="text.srm.field.srmrst002004Notice1"/> </td><%--spring:message : 조치내역이 없습니다.--%>
					</tr>
				</c:if>
				
			</tbody>
		</table>
	</div>
	<div class="tit_btns">
		<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002004.sub.title2'/></h2>	<%--조치등록 --%>
		<div class="right_btns">
			<button type="button" id="btnTempSave" 	name="btnTempSave"	class="btn_normal" 			onclick="javascript:doSave('2');" ><spring:message code='button.srm.tempSave'/></button>	<%--임시저장 --%>
			<button type="button" id="btnDelete" 	name="btnDelete"	class="btn_normal btn_red" 	onclick="javascript:doDelete();" ><spring:message code='button.srm.delete'/></button>	<%--삭제 --%>
			<button type="button" id="btnSave" 		name="btnSave" 		class="btn_normal btn_blue" onclick="javascript:doSave('3');" ><spring:message code='button.srm.impResComplete'/></button>	<%--조치완료 --%>
			<button type="button" id="btnAddFile" 	name="btnAddFile"	class="btn_normal"			onclick="javascript:addAttachFileRow();" ><spring:message code='button.srm.addFile'/></button>	<%--첨부파일추가 --%>
		</div>
	</div>
	
	<%----- 조치 등록 Start -------------------------%>
	
	<form name="MyForm"  id="MyForm" method="post" enctype="multipart/form-data">
		<input type="hidden" id="impStatus" name="impStatus"/>
		<input type="hidden" id="evNo" name="evNo" value=""/>
		<input type="hidden" id="seq" name="seq"/>
		<input type="hidden" id="impSeq" name="impSeq"/>
		<input type="hidden" id="impAttachFileNo" name="impAttachFileNo"/>
	
	<table class="tbl_st1 form_style">
		<colgroup>
			<col style="width:150px;">
			<col>
		</colgroup>
		<tbody>
		
			<tr>
				<th><label for=""><spring:message code='text.srm.field.impResDate'/></label></th>	<%--시정조치 완료일 --%>
				<td>
					<input type="text" id="impResDate" name="impResDate" title="<spring:message code='text.srm.field.impResDate'/>" disabled="disabled" readonly="readonly" value="" >
					<button type="button" id="calImg" class="plain btn_cal" onClick="openCal('MyForm.impResDate');" style="cursor: pointer;" ><img src="/images/epc/edi/srm/sub/icon_cal.png"></button>
					<span id="impResDateSpan"/>
				</td>
			</tr>
			
			<tr>
				<th><label for="impResRemark"><spring:message code='text.srm.field.impResRemark'/></label></th>	<%--시정조치내용 --%>
				<td>
					<textarea id="impResRemark" name="impResRemark" style="width:98%;height:100px;"></textarea>
				</td>
			</tr>
			<tr>
				<th><label for=""><spring:message code='text.srm.field.attachNo'/></label></th>	<%--첨부파일 --%>
				<td>
					<table>
						<tbody id="attachFileTbody">
							<tr>
								<td style="border: none;">
									<input type="hidden" id="impAttachFileName" name="impAttachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
									<input type="hidden" id="docNo" name="docNo"/>
									<input type="hidden" id="docSeq" name="docSeq"/>
									<input type="file" id="impAttachFileNoFile" name="impAttachFileNoFile" title="" onchange="javascript:fileUpload(this, 'impAttachFileName');"/>
									<btn>
										<button type="button" id="" name="" class="btn_normal btn_gray ml5" onclick="javascript:fileClear(this, 'impAttachFileNoFile','impAttachFileName')" ><spring:message code='button.srm.cancel'/></button>	<%--취소 --%>
										<button type="button" id="" name="" class="btn_normal btn_red ml5"  onclick="javascript:fileDelete(this);" ><spring:message code='button.srm.delete'/>	<%--삭제 --%>
									</btn>
								</td>
							</tr>
						</tbody>
					</table>
					<div style="color:red;"><spring:message code='text.srm.field.srmjon0042Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 업로드 하세요!--%>
				</td>
			</tr>
		
		</tbody>
	</table>

	<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
</div><!-- END popup wrap -->
</form>
<%----- 조치내역 등록 End -------------------------%>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>