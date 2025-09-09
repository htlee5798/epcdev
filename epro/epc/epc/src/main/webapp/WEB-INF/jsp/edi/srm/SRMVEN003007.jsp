<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMVEN003007.jsp
	Description : SRM모니터링 > 개선조치 팝업
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

	<title><spring:message code="text.srm.field.srmven003007.title"/></title><%--spring:message : 개선요청--%>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
			if("<c:out value="${data.vendorCode}"/>" == "" && "<c:out value="${result.message}"/>" != "success"){
				alert("<spring:message code="msg.srm.alert.srmjon0030.notListData"/>");
				window.close();
			}
			if("<c:out value="${result.message}"/>" == "success" ){
				alert("저장되었습니다.");
				$("#MyForm").attr("action", "<c:url value='/edi/ven/selectSRMmoniteringDetailImpReqPopup.do'/>");
				$("#MyForm").submit();
			}
		});

		/*저장*/
		function func_save(tempYn){
			if(!validation(tempYn))return;

			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/
			$('#MyForm input[name=tempYn]').val(tempYn);
			$('#MyForm input[name=impPlanDueDate]').removeAttr("disabled");
			$('#MyForm input[name=impPlanDate]').removeAttr("disabled");
			$("#MyForm").attr("action", "<c:url value='/edi/ven/updateSRMmoniteringDetailImpReq.do'/>");
			$("#MyForm").submit();

//			if(tempYn == 'S'){
//				window.close();
//			}
		}

		/*validation*/
		function validation(tempYn){
			var target = "";

			target = "<spring:message code="text.srm.field.impPlanDueDate"/>";
			if(!$.trim($('#MyForm input[name=impPlanDueDate]').val())){
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 개선완료 예정일을(를) 입력하세요. */
				return false;
			}
			if(tempYn == 'S'){
				target = "<spring:message code="text.srm.field.impPlanDate"/>";
				if(!$.trim($('#MyForm input[name=impPlanDate]').val())){
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 개선완료일을(를) 입력하세요. */
					return false;
				}

				target = "<spring:message code="text.srm.field.ownerMd"/>";
				if(!$.trim($('#MyForm input[name=deptUser]').val())){
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 담당자을(를) 입력하세요. */
					return false;
				}

				target = "<spring:message code="text.srm.field.impPlanMemo"/>";
				if(!$.trim($('#MyForm textarea[name=impPlanMemo]').val())){
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 개선완료 내용을(를) 입력하세요. */
					return false;
				}
			}



			target = "<spring:message code="text.srm.field.ownerMd"/>";
			if (!cal_3byte($('input[name="deptUser"]').val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
				$('input[name="deptUser"]').focus();
				return false;
			}

			target = "<spring:message code="text.srm.field.impPlanMemo"/>";
			if (!cal_3byte($('textarea[name="impPlanMemo"]').val(), '4000', setPermitMsg(target), setByteMsg(target, '4000'))) {
				$('textarea[name="impPlanMemo"]').focus();
				return false;
			}

			$('#attachFileTbody tr').each(function(){
				var file = $(this).find('#attachFileNoFile').val();
				var fileExt = file.substring(file.lastIndexOf('.')+1);
				var fileName = file.substring(file.lastIndexOf('\\')+1);

				if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.image.ext'/>", fileExt)){
					var target1 = "<spring:message code="text.srm.field.attachNo"/>";
					var target2 = "<spring:message code='edi.srm.file.image.ext'/>";
					alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/* 첨부파일은(는) {1}파일만 업로드 할 수 있습니다.*/
					fileCheck = false;
					return false;
				}

				target = "<spring:message code='text.srm.field.fileName' />";	// 첨부파일이름
				if(!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
					fileCheck = false;
					return false;
				}
			});

			return true;
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

		//첨부파일 행 추가
		function addAttachFileRow(){
			$('#addAttachFileTemplate').tmpl().appendTo("#attachFileTbody");
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
			$('#fileForm').attr("action", '<c:url value="/edi/ven/fileDown.do"/>');
			$('#fileForm').submit();
		}

	</script>

<script id="addAttachFileTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td>
			<input type="hidden" id="attachFileName" name="attachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
			<input type="hidden" id="docNo" name="docNo"/>
			<input type="hidden" id="docSeq" name="docSeq"/>
			<input type="file" id="attachFileNoFile" name="attachFileNoFile" title="" onchange="javascript:fileUpload(this, 'attachFileName');"/>
			<btn>
				<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'attachFileNoFile','attachFileName')"/><%--spring:message : 취소--%>
				<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
			<btn>
		</td>
	</tr>
</script>
</head>


<body style="width:100%;height:100%; overflow-X:hidden;">

<form name="MyForm" id="MyForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="evEiNo" name="evEiNo" value="<c:out value="${data.evEiNo}"/>"/>
	<input type="hidden" id="attachNo" name="attachNo" value="<c:out value="${data.attachNo}"/>"/>
	<input type="hidden" id="vendorCode" name="vendorCode" value="<c:out value='${epcLoginSession.repVendorId}'/>"/>
	<input type="hidden" id="tempYn" name="tempYn"/>
	<input type="hidden" id="countYear" name="countYear" value="<c:out value="${data.countYear}"/>"/>
	<input type="hidden" id="countMonth" name="countMonth" value="<c:out value="${data.countMonth}"/>"/>
	<input type="hidden" id="channelCode" name="channelCode" value="<c:out value="${data.evChannelCode}"/>"/>
	<input type="hidden" id="egNo" name="egNo" value="<c:out value="${data.egNo}"/>"/>
	<input type="hidden" id="evTplNo" name="evTplNo" value="<c:out value="${data.evTplNo}"/>"/>
	<input type="hidden" id="catLv2Code" name="catLv2Code" value="<c:out value="${data.catLv2Code}"/>"/>


	<div class="con_area" style="width:550px;">
		<div id="p_title1">
			<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
		</div>
		<p style="margin-top: 10px;" />
		<div class="div_tit_dot">
			<div class="div_tit">
				<tt><spring:message code="text.srm.field.srmven003007.sub.title1"/></tt><%--spring:message : 개선요청--%>
				<c:if test="${data.progressCode ne '3'}">
					<btn>
						<input type="button" id="" name="" value="<spring:message code='button.srm.tempSave'/>" class="btn_normal btn_black" onclick="func_save('T');" /><%--spring:message : 저장--%>
						<input type="button" id="" name="" value="<spring:message code='button.srm.insert'/>" class="btn_normal btn_blue" onclick="func_save('S');" /><%--spring:message : 저장--%>
						<input type="button" id="btnAddFile" name="btnAddFile" value="<spring:message code='button.srm.addFile'/>" class="btn_normal btn_black" onclick="javascript:addAttachFileRow();"/><%--spring:message : 첨부파일추가--%>
					</btn>
				</c:if>
			</div>
		</div>
		<div class="tbl_default">
			<table>
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
							<input type="text" class="input_txt_default" id="impPlanDueDate" name="impPlanDueDate" disabled="disabled" readonly="readonly" value="<c:out value="${data.impPlanDueDate}"/>" style="width: 80px;">
							<c:if test="${data.progressCode ne '3'}">
								<img id="calImg" src="/images/epc/layout/btn_cal.gif" align="top" class="middle" onClick="openCal('MyForm.impPlanDueDate');" style="cursor: pointer;" />
							</c:if>
						</td>
					</tr>

					<tr>
						<th><spring:message code="text.srm.field.impPlanDate"/></th><%--spring:message : 개선완료일--%>
						<td>
							<input type="text" class="input_txt_default" id="impPlanDate" name="impPlanDate" disabled="disabled" readonly="readonly" value="<c:out value="${data.impPlanDate}"/>" style="width: 80px;">
							<c:if test="${data.progressCode ne '3'}">
								<img id="calImg" src="/images/epc/layout/btn_cal.gif" align="top" class="middle" onClick="openCal('MyForm.impPlanDate');" style="cursor: pointer;" />
							</c:if>
						</td>
					</tr>

					<tr>
						<th><spring:message code="text.srm.field.ownerMd"/></th><%--spring:message : 담당자--%>
						<td>
							<input type="text" id="deptUser" name="deptUser" class="input_txt_default" value="<c:out value="${data.deptUser}"/>" <c:if test="${data.progressCode eq '3'}">disabled="disabled"</c:if>/>
						</td>
					</tr>

					<tr>
						<th><spring:message code="text.srm.field.impPlanMemo"/></th><%--spring:message : 개선완료 내용--%>
						<td>
							<textarea id="impPlanMemo" name="impPlanMemo" class="input_textarea_default" style="width:98%;height:100px;" <c:if test="${data.progressCode eq '3'}">readonly="readonly"</c:if>><c:out value="${data.impPlanMemo}"/></textarea>
						</td>
					</tr>
					<tr>
						<th><spring:message code="text.srm.field.attachNo"/></th><%--spring:message : 첨부파일--%>
						<td>
							<table>
								<tbody id="attachFileTbody">
									<c:if test="${empty fileList}">
										<tr>
											<td>
												<input type="hidden" id="attachFileName" name="attachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
												<input type="hidden" id="docNo" name="docNo"/>
												<input type="hidden" id="docSeq" name="docSeq"/>
												<input type="file" id="attachFileNoFile" name="attachFileNoFile" title="" onchange="javascript:fileUpload(this, 'attachFileName');"/>
												<btn>
													<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'attachFileNoFile','attachFileName')"/><%--spring:message : 취소--%>
													<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
												</btn>
											</td>
										</tr>
									</c:if>
									<c:if test="${not empty fileList}">
										<c:forEach var="list" items="${fileList}" varStatus="status">
											<c:if test="${data.progressCode ne '3'}">
												<tr>
													<td>
														<input type="hidden" id="attachFileName" name="attachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
														<input type="hidden" id="docNo" name="docNo" value="<c:out value="${list.fileId}"/>"/>
														<input type="hidden" id="docSeq" name="docSeq"  value="<c:out value="${list.fileSeq}"/>"/>
														<input type="file" id="attachFileNoFile" name="attachFileNoFile" title="" onchange="javascript:fileUpload(this, 'attachFileName');"/>
														<btn>
															<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'attachFileNoFile','attachFileName')"/><%--spring:message : 취소--%>
															<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
														</btn>
														<div>
															<a id="" name="" href="#" onclick="javascript:downloadFile(<c:out value="${list.fileId}"/>,<c:out value="${list.fileSeq}"/>);"><c:out value="${list.fileNmae}"/></a>
														</div>
													</td>
												</tr>
											</c:if>
											<c:if test="${data.progressCode eq '3'}">
												<tr>
													<td>
														<div>
															<a id="" name="" href="#" onclick="javascript:downloadFile(<c:out value="${list.fileId}"/>,<c:out value="${list.fileSeq}"/>);"><c:out value="${list.fileNmae}"/></a>
														</div>
													</td>
												</tr>
											</c:if>

										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

</form>
<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>