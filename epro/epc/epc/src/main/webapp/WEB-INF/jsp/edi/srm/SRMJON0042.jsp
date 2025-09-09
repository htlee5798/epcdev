<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<%--
	Page Name 	: SRMJON0042.jsp
	Description : 입점상담신청[인증/신용평가 정보]
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code='text.srm.field.srmjon0040.title'/></title><%--spring:message : 입점상담 신청하기--%>
 
<script language="JavaScript">
	$(document).ready(function() {
		//페이지 이동일경우
		var url = "<c:out value="${url}"/>";

		if(url == "2") {
			alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/
		}
		
		switch (url){
			case '0': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0040.do'/>");break;
			case '1': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0041.do'/>");break;
			case '2': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0042.do'/>");break;
			case '3': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0043.do'/>");break;
		}
		if(url != "") {
			$("#hiddenForm").submit();
		}

		<%--$("input[name=creditAttachNoFile]").live("change", function () {--%>
			<%--var file = this.files[0];--%>
			<%--var fileSize = file.size;--%>

			<%--if (fileSize > 52428800) {--%>
				<%--alert("<spring:message code='msg.srm.alert.validation.filesize'/>");/*spring:message : 업로드 파일 크기의 합은 20M 이상 첨부할 수 없습니다.*/--%>
				<%--$(this).val("");--%>
			<%--}--%>
		<%--});--%>
		//커스텀태그 defName Set
		//선택
		var option = "<option value= \"\"><spring:message code='text.srm.field.select' /></option>";
		$(option).prependTo("#MyForm select[name=creditCompanyCode]");

		//커스텀태그 값SET
		$('#MyForm select[name=creditCompanyCode]').val("<c:out value="${srmComp.creditCompanyCode}"/>");
	});


	//탭이동
	function tab_page(page) {
		if(!confirm("<spring:message code='msg.srm.alert.moveTab'/>")) {/*spring:message : 페이지 이동시 해당 내용이 저장됩니다. 저장하시겠습니까?*/
			switch (page){
				case '0': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0040.do'/>");break;
				case '1': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0041.do'/>");break;
				case '2': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0042.do'/>");break;
				case '3': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0043.do'/>");break;
			}
			$("#hiddenForm").submit();
			return;
		}

		if(!validation()) return;
		
		switch (page){
			case '0': $('#MyForm input[name=url]').val("<c:url value='0'/>");break;
			case '1': $('#MyForm input[name=url]').val("<c:url value='1'/>");break;
			case '2': $('#MyForm input[name=url]').val("<c:url value='2'/>");break;
			case '3': $('#MyForm input[name=url]').val("<c:url value='3'/>");break;
		}
		$('#MyForm input[name=creditBasicDate]').removeAttr("disabled");
		$("#MyForm").attr("action", "<c:url value='/edi/srm/updateHiddenCompCreditInfo.do'/>");
		$("#MyForm").submit();
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
	function validation() {
		if ("<c:out value='${srmComp.channelCode}'/>" == '06' || "<c:out value='${srmComp.channelCode}'/>" == '99') {
			return true;
		}
		//Global은 선택사항 이며 Domestic은 필수
		if ("<c:out value='${srmComp.shipperType}'/>" == '1') {
			<%--if (!$.trim($('#MyForm input[name=creditNo]').val())) {--%>
				<%--var target = "<spring:message code='text.srm.field.creditNo'/>";--%>
				<%--alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 평가번호(DNA)을(를) 입력하세요. */--%>
				<%--$('#MyForm input[name=creditNo]').focus();--%>
				<%--return false;--%>
			<%--}--%>
			
			var target = "<spring:message code='checkbox.srm.zzqcFg12'/>";	// 기타인증
			if (!cal_3byte($('#MyForm input[name=zzqcFg12]').val(), '150', setPermitMsg(target), setByteMsg(target, '150'))) {
				$('#MyForm input[name=zzqcFg12]').focus();
				return false;
			}
			
			<%--target = "<spring:message code='text.srm.field.creditNo'/>";	// 평가번호(DNA)--%>
			<%--if (!cal_3byte($('#MyForm input[name=creditNo]').val(),'50', setPermitMsg(target), setByteMsg(target, '50'))) {--%>
				<%--$('#MyForm input[name=creditNo]').focus();--%>
				<%--return false;--%>
			<%--}--%>
			
			target = "<spring:message code='text.srm.field.creditCompanyCode'/>";	// 신용평가사
			if (!$.trim($('#MyForm select[name=creditCompanyCode]').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 신용평가사을(를) 선택하세요. */
				$('#MyForm select[name=creditCompanyCode]').focus();
				return false;
			}
			
			target = "<spring:message code='text.srm.field.creditRating'/>";	// 신용등급
			if(!$.trim($('#MyForm input[name=creditRating]').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 신용등급을(를) 입력하세요. */
				$('#MyForm input[name=creditRating]').focus();
				return false;
			}
			
			if (!cal_3byte($('#MyForm input[name=creditRating]').val(),'10', setPermitMsg(target), setByteMsg(target, '10'))) {
				$('#MyForm input[name=creditRating]').focus();
				return false;
			}
			
			target = "<spring:message code='text.srm.field.creditBasicDate'/>";	// 신용평가 기준일자
			if (!$.trim($('#MyForm input[name=creditBasicDate]').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 신용평가 기준일자을(를) 입력하세요. */
				$('#MyForm input[name=creditBasicDate]').focus();
				return false;
			}
			
			if (!cal_3byte($('#MyForm input[name=creditBasicDate]').val().replaceAll("-", ""),'8', setPermitMsg(target), setByteMsg(target, '8'))) {
				$('#MyForm input[name=creditBasicDate]').focus();
				return false;
			}
			
			target = "<spring:message code='text.srm.field.creditAttachNo'/>";	// 신용평가 사본
			if (!$.trim($('#MyForm input[name=creditAttachNoFile]').val()) && !$.trim($('#MyForm input[name=creditAttachNo]').val())) {
				alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");/* spring:message : 신용평가 사본을(를) 첨부하세요. */
				return false;
			}
			
			var file = $('#MyForm input[name=creditAttachNoFile]').val();
			var fileExt = file.substring(file.lastIndexOf('.')+1);
			var fileName = file.substring(file.lastIndexOf('\\')+1);
			
			if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.all.ext'/>", fileExt)){
				var target1 = "<spring:message code='text.srm.field.creditAttachNo'/>";
				var target2 = "<spring:message code='edi.srm.file.all.ext'/>";
				alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*신용평가 사본은(는) {1}파일만 업로드 할 수 있습니다.*/
				return false;
			}
			
			target = "<spring:message code='text.srm.field.fileName'/>";	// 첨부파일이름
			if (!cal_3byte(fileName,'192', setPermitMsg(target), setByteMsg(target, '192'))) {
				$('#MyForm input[name=creditAttachNoFile]').focus();
				return false;
			}
			
			// 신용평가정보 비교
			if (!doCreditInfo()) {
				return false;
			}
			
		}
		return true;
	}
	
	//임시저장
	function doTempSave() {
		if(!validation()) return;
		
		if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/
		$('#MyForm input[name=creditBasicDate]').removeAttr("disabled");
		$('#MyForm input[name=url]').val("<c:url value='2'/>");
		$("#MyForm").attr("action", "<c:url value='/edi/srm/updateHiddenCompCreditInfo.do'/>");
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

	function downloadFile(fileId, fileSeq) {
		$('#fileForm input[name=atchFileId]').val(fileId);
		$('#fileForm input[name=fileSn]').val(fileSeq);
		$('#fileForm').attr("action", '<c:url value="/edi/srm/FileDown.do"/>')
		$('#fileForm').submit();
	}

	//입점상담신청 취소
	function doCancel() {
		if(!confirm("<spring:message code="msg.srm.alert.vendorConsultReqCancel"/>")) return;/*spring:message :입력하신 내용은 삭제됩니다. 입점상담신청을 취소 하시겠습니까?*/

		var saveInfo = {};
		saveInfo["houseCode"] = "<c:out value='${srmComp.houseCode}'/>";
		saveInfo["sellerCode"] = "<c:out value='${srmComp.sellerCode}'/>";
		saveInfo["reqSeq"] = "<c:out value='${srmComp.reqSeq}'/>";

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/updateHiddenCompReqCancel.json"/>',
			data : JSON.stringify(saveInfo),
			success : function(data) {
				if (data.message == "SUCCESS") {
					alert('<spring:message code="msg.srm.alert.vendorConsultReqCancelConplete"/>');/*spring:message : 입점상담신청 취소가 정상적으로 처리 되었습니다.*/
					$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON002001.do'/>");
					$("#hiddenForm").submit();
				}  else {
					alert('<spring:message code="msg.common.fail.request"/>');/*spring:message : 요청처리를 실패하였습니다.*/
				}
			}
		});
	}
	
	/* 신용평가기관 정보 조회 */
	function doCreditInfo() {
		var result = false;
		var searchInfo = {};
		
		searchInfo["creditCompanyCode"] 	= $("#MyForm select[name='creditCompanyCode']").val();	// 신용평가사
		searchInfo["creditBasicDate"] 		= $("#MyForm input[name='creditBasicDate']").val().replaceAll("-", "");		// 신용평가 기준일자
		searchInfo["creditRating"] 			= $("#MyForm input[name='creditRating']").val();							// 신용평가등급

		searchInfo["companyType"] = "<c:out value="${srmComp.companyType}"/>";
		searchInfo["companyRegNo"] = "<c:out value="${srmComp.companyRegNo}"/>";
		//console.log(searchInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/selectCreditInfo.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				
				if (data.retMsg == "success") {
					result = true;
				} else {
					if (data.retMsg == "NO_COMPANY_INFO") {
						alert("<spring:message code="msg.srm.alert.validation.NO_COMPANY_INFO"/>");/*spring:message 신용평가기관을 확인해주세요.*/
					} else if (data.retMsg == "MISS_MATCH_INFO") {
						alert("<spring:message code="msg.srm.alert.validation.MISS_MATCH_INFO"/>");/*spring:message 신용평가정보가 일치하지 않습니다.*/
					} else if (data.retMsg == "NO_CREDIT_INFO") {
						alert("<spring:message code="msg.srm.alert.validation.NO_CREDIT_INFO"/>");/*spring:message 일치하는 신용평가 정보가 없습니다.*/
					} else if (data.retMsg == "END_CREDIT_DATE") {
						alert("<spring:message code="msg.srm.alert.validation.END_CREDIT_DATE"/>");/*spring:message 신용평가 정보는 유효기간이 한달이상 남아있어야 합니다.\\n신용평가기관에 재 신청 후 입점상담 신청을 진행해 주세요.*/
					}
					result = false;
				}
			}
		});
		return result;
	}

	function onlyEng(obj) {
		if ($(obj).val() != null && $(obj).val() != '') {
			var tmps = $(obj).val().replace(/[^A-Z0-9+-]/gi, '');
			$(obj).val(tmps.toUpperCase());
		}
	}
	
	/* 신용평가별 정보조회 팝업창 */
	function fnSearchCerdiInfo() {
		var cw = 700;
		var ch = 500;
		
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);
		
		window.open("","popup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");
		var form = document.MyForm;
		form.action = "<c:url value='/edi/srm/selectCreditAllInfoPopup.do'/>";
		form.target = "popup";
		form.submit();
	}
	
	/* 신용평가정보 받아오기 */
	function receiveValue(creditRating, creditBasicDate, creditCompanyCode) {
		$('#MyForm input[name=creditRating]').val(creditRating);
		$('#MyForm input[name=creditBasicDate]').val(creditBasicDate);
		$('#MyForm select[name=creditCompanyCode]').val(creditCompanyCode);
	}
	
</script>


</head>


<body>
<form id="MyForm" name="MyForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value='${srmComp.houseCode}'/>"/>
	<input type="hidden" id="sellerCode" name="sellerCode" value="<c:out value='${srmComp.sellerCode}'/>"/>
	<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value='${srmComp.reqSeq}'/>"/>
	<input type="hidden" id="url" name="url"/>

	<input type="hidden" id="companyType" name="companyType" value="<c:out value="${srmComp.companyType}"/>"/>
	<input type="hidden" id="companyRegNo" name="companyRegNo" value="<c:out value="${srmComp.companyRegNo}"/>"/>

	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<ul class="inner sub_wrap">

			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>
				<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span> <span><spring:message code='text.srm.field.srmjon0040.title' /></span></p>
			</div><!-- END 서브상단 -->

			<!-- 알림 -->
			<div class="noti_box">
				<ul class="noti_list">
					<li class="txt_l"><em><spring:message code="text.srm.field.srmjon0040Notice1"/></em></li>	<%-- 정보 누락 및 불일치 시 입점 상담 등의 절차가 지연되거나 중단 혹은 취소될 수 있습니다. --%>
					<li class="txt_l"><em><spring:message code="text.srm.field.srmjon0040Notice2"/></em></li>	<%--spring:message : ※신용평가기관에 롯데마트 정보동의를 요청하셔야 정보확인이 가능합니다.(요청 후 익일 확인가능)--%>
					<%--<li class="txt_l"><em><spring:message code="text.srm.field.srmjon0040Notice3"/></em></li>	&lt;%&ndash; 신용평가기관에 롯데마트 정보동의를 요청하셔야 정보확인이 가능합니다.(요청 후 익일 확인가능) &ndash;%&gt;--%>
				</ul>
			</div><!-- END 알림 -->

			<!-- 정보 입력 유형 탭 : 선택된 li에 클래스 on을 붙입니다.-->
			<ul class="sub_tab col4">
				<li rel="tab1"><a href="#" onclick="tab_page('0');"><spring:message code='tab.srm.srmjon0030.tab1'/></a></li>					<%-- 기본정보--%>
				<li rel="tab2"><a href="#" onClick="tab_page('1');"><spring:message code='tab.srm.srmjon0030.tab2'/></a></li>	<%-- 상세정보--%>
				<li class="on" rel="tab3"><a href="#" onClick=""><spring:message code='tab.srm.srmjon0030.tab3'/></a></li>	<%-- 인증/신용평가 정보--%>
				<li rel="tab4"><a href="#" onClick="tab_page('3');"><spring:message code='tab.srm.srmjon0030.tab4'/></a></li>	<%-- 정보확인--%>
			</ul><!-- END 정보 입력 유형 탭 -->

			<%----- 인증정보 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0042.sub.title1'/></h3>	<%-- 운영현황--%>
				<div class="right_btns">
					<button type="button" class="btn_normal btn_blue" onclick="javascript:doTempSave();"><spring:message code='button.srm.tempSave'/></button>	<%-- 임시저장--%>
					<button type="button" class="btn_normal btn_red" onclick="javascript:doCancel();"><spring:message code='button.srm.vendorConsultReqCancel'/></button>	<%-- 입점상담신청 취소--%>
				</div>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<!-- 정보 입력폼 -->
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
				</colgroup>
				<tbody>
					<tr>
						<td>
							<input type="checkbox" id="zzqcFg1" name="zzqcFg1" title="<spring:message code='checkbox.srm.zzqcFg1'/>" <c:if test="${srmComp.zzqcFg1 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg1"><spring:message code='checkbox.srm.zzqcFg1'/></label><%--spring:message : HACCP--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg2" name="zzqcFg2" title="<spring:message code='checkbox.srm.zzqcFg2'/>" <c:if test="${srmComp.zzqcFg2 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg2"><spring:message code='checkbox.srm.zzqcFg2'/></label><%--spring:message : FSSC22000--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg3" name="zzqcFg3" title="<spring:message code='checkbox.srm.zzqcFg3'/>" <c:if test="${srmComp.zzqcFg3 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg3"><spring:message code='checkbox.srm.zzqcFg3'/></label><%--spring:message : ISO 22000--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg4" name="zzqcFg4" title="<spring:message code='checkbox.srm.zzqcFg4'/>" <c:if test="${srmComp.zzqcFg4 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg4"><spring:message code='checkbox.srm.zzqcFg4'/></label><%--spring:message : GMP인증--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg5" name="zzqcFg5" title="<spring:message code='checkbox.srm.zzqcFg5'/>" <c:if test="${srmComp.zzqcFg5 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg5"><spring:message code='checkbox.srm.zzqcFg5'/></label><%--spring:message : KS인증--%>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="zzqcFg6" name="zzqcFg6" title="<spring:message code='checkbox.srm.zzqcFg6'/>" <c:if test="${srmComp.zzqcFg6 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg6"><spring:message code='checkbox.srm.zzqcFg6'/></label><%--spring:message : 우수농산품(GAP)인증--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg7" name="zzqcFg7" title="<spring:message code='checkbox.srm.zzqcFg7'/>" <c:if test="${srmComp.zzqcFg7 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg7"><spring:message code='checkbox.srm.zzqcFg7'/></label><%--spring:message : 유기농식품인증--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg8" name="zzqcFg8" title="<spring:message code='checkbox.srm.zzqcFg8'/>" <c:if test="${srmComp.zzqcFg8 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg8"><spring:message code='checkbox.srm.zzqcFg8'/></label><%--spring:message : 전통식품품질인증--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg9" name="zzqcFg9" title="<spring:message code='checkbox.srm.zzqcFg9'/>" <c:if test="${srmComp.zzqcFg9 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg9"><spring:message code='checkbox.srm.zzqcFg9'/></label><%--spring:message : ISO 9001--%>
						</td>
						<td>
							<input type="checkbox" id="zzqcFg10" name="zzqcFg10" title="<spring:message code='checkbox.srm.zzqcFg10'/>" <c:if test="${srmComp.zzqcFg10 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg10"><spring:message code='checkbox.srm.zzqcFg10'/></label><%--spring:message : 수산물품질인증--%>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="zzqcFg11" name="zzqcFg11" title="<spring:message code='checkbox.srm.zzqcFg11'/>" <c:if test="${srmComp.zzqcFg11 eq 'X'}">checked</c:if>/>
							<label for="zzqcFg11"><spring:message code='checkbox.srm.zzqcFg11'/></label><%--spring:message : PAS 220--%>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="5">
							<spring:message code='checkbox.srm.zzqcFg12'/> :
							<input type="text" id="zzqcFg12" name="zzqcFg12" title="<spring:message code='checkbox.srm.zzqcFg12'/>" class="input_txt_default" value="<c:out value='${srmComp.zzqcFg12}'/>" style="width: 90%;" /><%--spring:message : 기타인증--%>
						</td>
					</tr>
				</tbody>
			</table>
			<%----- 인증정보 End -------------------------%>
			
			
			<%----- 신용평가정보 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star">
					<spring:message code='text.srm.field.srmjon0042.sub.title2'/>
						(
						<span style="font-weight: bold;color:red;">
							<%--온라인--%>
							<c:if test="${srmComp.channelCode eq '06'}">
								<spring:message code='text.srm.field.srmjon0042.sub.title4'/><%--spring:message : 신용평가정보는 선택사항입니다.(롯데마트몰 限)--%>
							</c:if>
							
							<c:if test="${srmComp.channelCode eq '99'}">
								<spring:message code='text.srm.field.srmjon0042.sub.title5'/><%--spring:message : 신용평가정보는 선택사항입니다.(임대매장에 한함)--%>
							</c:if>

							<c:if test="${srmComp.channelCode ne '06' && srmComp.channelCode ne '99'}">
								<spring:message code='text.srm.field.srmjon0042.sub.title3'/><%--spring:message : Global은 선택사항 이며 Domestic은 필수--%>
							</c:if>
						</span>
						)

				</h3>	<%-- 운영현황--%>
				<div class="right_btns">
					<input type="button" class="btn_normal btn_blue" value="<spring:message code='button.srm.selectCredit'/>" onclick="javascript:fnSearchCerdiInfo();"><%--spring:message : 신용평가정보확인--%>
				</div>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<%--<th><label for="creditNo"><spring:message code='text.srm.field.creditNo'/></label></th>&lt;%&ndash;spring:message : 평가번호(DNA)&ndash;%&gt;--%>
						<%--<td>--%>
							<%--<input type="text" id="creditNo" name="creditNo" title="<spring:message code='text.srm.field.creditNo'/>" class="input_txt_default" value="<c:out value='${srmComp.creditNo}'/>"/>--%>
						<%--</td>--%>
						<th><label for="creditRating"><spring:message code='text.srm.field.creditRating'/></label></th><%--spring:message : 신용등급--%>
						<td>
							<input type="text" id="creditRating" name="creditRating" title="<spring:message code='text.srm.field.creditRating'/>" class="input_txt_default" onkeyup="onlyEng(this)" value="<c:out value='${srmComp.creditRating}'/>"/>
						</td>
						<th><label for="creditCompanyCode"><spring:message code='text.srm.field.creditCompanyCode'/></label></th><%--spring:message : 신용평가사--%>
						<td>
							<srm:codeTag comType="SELECT" objId="creditCompanyCode" objName="creditCompanyCode" formName="" parentCode="SRM039"  width="100px"/>
						</td>
					</tr>
					<tr>
						<th><label for="creditBasicDate"><spring:message code='text.srm.field.creditBasicDate'/></label></th><%--spring:message : 신용평가 기준일자--%>
						<td colspan="3">
							<input type="text" class="input_txt_default" id="creditBasicDate" name="creditBasicDate" title="<spring:message code='text.srm.field.creditBasicDate'/>" disabled="disabled" readonly="readonly" value="<c:out value='${srmComp.creditBasicDateHypen}'/>" style="width: 80px;">
							<button type="button" class="plain btn_cal" onclick="openCal('MyForm.creditBasicDate');"style="cursor: pointer;" ><img src="/images/epc/edi/srm/sub/icon_cal.png"></button>
						</td>
					</tr>
					<tr>
						<th><label for="creditAttachNo"><spring:message code='text.srm.field.creditAttachNo'/></label></th><%--spring:message : 신용평가 사본--%>
						<td colspan="3">
							<input type="hidden" id="creditAttachNoFileName" name="creditAttachNoFileName" title="<spring:message code='text.srm.field.creditAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
							<input type="file" id="creditAttachNoFile" name="creditAttachNoFile" title="<spring:message code='text.srm.field.creditAttachNo'/>" onchange="javascript:fileUpload(this, 'creditAttachNoFileName');"/>
							<btn>
								<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('creditAttachNoFile','creditAttachNoFileName')"/><%--spring:message : 취소--%>
								<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('creditAttachNo', 'creditAttachNoA');"/><%--spring:message : 삭제--%>
							<btn>
							<c:if test="${not empty srmComp.creditAttachNo}">
								<div>
									<input type="hidden" id="creditAttachNo" name="creditAttachNo" value="<c:out value="${srmComp.creditAttachNo}"/>"/>
									<a id="creditAttachNoA" name="creditAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.creditAttachNo}"/>,'1');"><c:out value="${srmComp.creditAttachNoName}"/></a>
								</div>
							</c:if>
							<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div><%--spring:message : ※ Notice : JPG 이미지 파일로 업로드 하세요!--%>
						</td>
					</tr>
				</tbody>
			</table>
			<%----- 신용평가정보 End -------------------------%>
			
		</div>
	</div>

</form>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

<form id="hiddenForm" name="hiddenForm" method="post">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value='${srmComp.houseCode}'/>"/>
	<input type="hidden" id="sellerCode" name="sellerCode" value="<c:out value='${srmComp.sellerCode}'/>"/>
	<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value='${srmComp.reqSeq}'/>"/>
</form>
</body>
</html>