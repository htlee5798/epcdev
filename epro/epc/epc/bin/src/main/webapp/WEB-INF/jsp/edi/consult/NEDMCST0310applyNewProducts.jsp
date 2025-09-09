<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>
<script type="text/javascript" src="/js/epc/showModalDialog.js"></script>
<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
<script language="javascript">

	$(document).ready(function() {	
	
		if("<c:out value='${errMsg}'/>"){
			alert("<c:out value='${errMsg}'/>");
			history.back(-1);
			return false;
		}
		
	
		$("#MyForm input.imageUpload").change(function() {
			var checkReturn = true;
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			var files = $( "#MyForm #"+this.name  );			// file 명 
			
			//if (fileExtension != "gif" & fileExtension != "jpg") {
			if(fileExtension != "jpg") {
				alert("<spring:message code='msg.consult.require.fileExtensionJpg'/>");
				checkReturn = false;
				
			} else { 

				var iSize = document.getElementById(this.name).files[0].size;
				
				if ( iSize > ( 1024 * 1024 ) * 1) {
					iSize = (Math.round((iSize / 1024) * 100) / 100);
					alert("<spring:message code='msg.consult.require.maxSize1m'/>"); /* 첨부가능한 파일의 용량은 1MB 입니다.  */	
					checkReturn = false;
				}
						
			}
			
			/* 파일삭제 */
			if(!checkReturn){
				files.replaceWith( files.val('').clone(true)); // 잘못된 확장자 들어올경우 초기화
			}
			
			return checkReturn;
			
		});

		
		$( "#MyForm input[name='vendorAttachFile']" ).change( function() {
			var checkReturn = true;
			
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			var files = $( "#MyForm input[name='vendorAttachFile']" );			// file 명 
			
			if(fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" ) {
				alert("<spring:message code='msg.consult.require.fileExtensionPptPptxPdf'/>");
				checkReturn = false;
				
			} else { 
				var iSize = document.getElementById(this.name).files[0].size;
				
				if ( iSize > ( 1024 * 1024 ) * 2) {
					iSize = (Math.round((iSize / 1024) * 100) / 100);
					alert("<spring:message code='msg.consult.require.maxSize2m'/>"); /* 첨부가능한 파일의 용량은 5MB 입니다.  */	
					checkReturn = false;
				}
						
			}
			
			if(!checkReturn){
				files.replaceWith( files.val('').clone(true)); // 잘못된 확장자 들어올경우 초기화
			}
			
			return checkReturn;
			
		});
		

	});


	
	$(function() {
	//팀
//	$("#teamCd").change(selectL1ListApply);
	

		$("#MyForm #teamCd").change(function() {
			
			$.getJSON("<c:url value='/edi/consult/NEDMCST0310selectL1ListApply.do'/>",
			{
				groupCode: $(this).val() 
			},function(j){
		      	var options = '';

		      if(j.length=='0'){
		    	  options = '<option value=all>선택</option>';
		      }else{
		    	  for (var i = 0; i < j.length; i++) {
					    if(i == 0) {
					    	options += '<option value=all>선택</option>';
						}  
				        options += '<option value="' + j[i].l1Cd + '">' + j[i].l1Nm + '</option>';
			      }
		      }
		      

		      $("#MyForm #l1GroupCode option").remove();
		      $("#MyForm #l1GroupCode").html(options);
		    });
		});
		
	});
	


	// 파일 사이즈 체크 
	// ObjectType
	function checkStep3Submit() {

		var prodArray = "";
		$("#MyForm input:checkbox[name='prodArraySeq']:checked").each(function() {

			prodArray = prodArray + $(this).val() + "/";

		});

		$("#MyForm input[name=prodArray]").val(prodArray);

		var email1 = $("#MyForm input[name='email1']").val();
		var email2 = $("#MyForm input[name='email2']").val();
		

		var email = email1 + "@" + email2;
		$("#MyForm input[name=email]").val(email);

				
		var l1Cd = $("#MyForm select[name='l1GroupCode']").val();
		$("#MyForm input[name='l1Cd']").val(l1Cd);
		
		
		
		if (!check_submit()) {
			return;
		}

		//	if(check_submit_4()) {
		//	alert(document.MyForm.prodArray.value);
		//	return;

		//alert("상품이미지와 첨부문서가 제대로 나오는지 반드시 확인해주세요. 이미지가 나오지 않는 경우 신청을 누르고 다시 작성해주세요 문의는 2145-8426 입점안내입니다.");
		
		//-----특이사항 2000byte 초과시 return[최대 공백없이 한글 100자 입력 가능 스크립트에서는 한글을 2byte로 체크하나 DB에서는 3byte로 인식]
		if(!calByteProd( MyForm.content,	2000,	'<spring:message code="text.consult.field.uniqueness"/>',	false)) 		return;
		
		$("#MyForm").submit();

	}

	function openPopup(openUrl, popTitle) {
		winstyle = "dialogWidth=300px; dialogHeight:220px; center:yes; status: no; scroll: yes; help: no";
		result = window.showModalDialog(openUrl, window, winstyle);
	}

	function img_view(imgurl, imgpath) {

		var w = "600";
		var h = "600";
		var width = "600";
		var height = "600";

		var path = "upload/" + imgurl;

		var openWidth = "650";
		var openHeight = "600";

		if (openWidth > 800)			openWidth = 800;
		if (openHeight > 800)			openHeight = 800;

		window.open("<c:url value='/edi/consult/NEDMCST0310step3ImagePopup.do?img='/>" + imgurl
				+ "&imgpath=" + imgpath + "&h=" + h, "imgwin", "width="
				+ openWidth + ", height=" + openHeight
				+ ", scrollbars=yes, resizable=yes");
	}

	

	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open(
				"<c:url value='/edi/consult/NEDMCST0310step3DocumnetPopup.do?vendorBusinessNo='/>"
						+ vendorBusinessNo, "docwin",
				"width=200, height=300, scrollbars=yes, resizable=yes");
	}

	
	function check_submit() {

		if ($("#MyForm input[name='hndNm']").val().replace(/\s/gi, '') == "") {
			/* strMsg = "msg.no.company.name";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=hndNm";
			openPopup(popUrl, 'ERROR');

			return false; */
			alert("상호명을 입력해주세요");
			$("#MyForm input[name='hndNm']").focus();
			return false;
		}
		
		if ($("#MyForm select[name='teamCd']").val().replace(/\s/gi, '') == "") {
			/* strMsg = "msg.teamnm";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=teamCd";
			openPopup(popUrl, 'ID');

			return false; */
			alert("희망부서를 선택해주세요");
			$("#MyForm select[name='teamCd']").focus();
			return false;
		}
		
		if ($("#MyForm select[name='l1GroupCode']").val() == "all" || $("#MyForm select[name='l1GroupCode']").val().replace(/\s/gi, '') == "" ) {
			/* strMsg = "msg.l1Nm";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=l1GroupCode";
			openPopup(popUrl, 'ID');

			return false; */
			alert("대분류를 선택해주세요");
			$("#MyForm select[name='l1GroupCode']").focus();
			return false;
		}
		
		//대표자명
		if ($("#MyForm input[name='ceoNm']").val().replace(/\s/gi, '') == "") {
			/* strMsg = "msg.ceo.name";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=ceoNm";
			openPopup(popUrl, 'ID');
			return false; */
			alert("대표자명을 입력해주세요");
			$("#MyForm input[name='ceoNm']").focus();
			return false;
		}
	
		//대표전화번호
		/* if ($("#MyForm input[name='officetel1']").val().length == 0 || $("#MyForm input[name='officetel2']").val().length == 0 || $("#MyForm input[name='officetel3']").val().length == 0){
			var targetObject = "";
			if($("#MyForm input[name='officetel1']").val().length == 0){
				targetObject = "officetel1";
			} else if($("#MyForm input[name='officetel2']").val().length == 0){
				targetObject = "officetel2";
			} else if($("#MyForm input[name='officetel3']").val().length == 0){
				targetObject = "officetel3";
			} else {
			}

			strMsg = "msg.office.telephone";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=" + targetObject;
			openPopup(popUrl, 'ID');

			return false;
		} */
		
		if ($("#MyForm input[name='officetel1']").val().replace(/\s/gi, '') == "") {
			alert("연락처 입력을 정확히 해주세요.");
			$("#MyForm input[name='officetel1']").focus();
			return false;
		}
		
		if ($("#MyForm input[name='officetel2']").val().replace(/\s/gi, '') == "") {
			alert("연락처 입력을 정확히 해주세요.");
			$("#MyForm input[name='officetel2']").focus();
			return false;
		}
		
		if ($("#MyForm input[name='officetel3']").val().replace(/\s/gi, '') == "") {
			alert("연락처 입력을 정확히 해주세요.");
			$("#MyForm input[name='officetel3']").focus();
			return false;
		}
		
		if ($("#MyForm input[name='email1']").val().replace(/\s/gi, '') == "") {
			alert("이메일 주소를 입력해주세요.");
			$("#MyForm input[name='email1']").focus();
			return false;
		}
			
		if ($("#MyForm input[name='email2']").val().replace(/\s/gi, '') == "") {
			alert("이메일 주소를 입력해주세요.");
			$("#MyForm input[name='email2']").focus();
			return false;
		}

		/* if ($("#MyForm input[name='email1']").val().length == 0 || $("#MyForm input[name='email2']").val().length == 0) {
			var targetObject = "";

			if ($("#MyForm input[name='email1']").val().length == 0) {
				targetObject = "email1";
			}else if ($("#MyForm input[name='email2']").val().length == 0) {
				targetObject = "email2";
			} else {
			}

			strMsg = "msg.email";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=" + targetObject;
			openPopup(popUrl, 'ID');

			return false;
		} */
		
		
		if ($("#MyForm input[name='attachFile1']").val().length != 0){
			
			var fileExtension = getFileExtension($("#MyForm input[name='attachFile1']").val()).toLowerCase();
			//if (fileExtension != "gif" & fileExtension != "jpg") {
			if (fileExtension != "jpg") {				
				alert("<spring:message code='msg.consult.require.fileExtensionJpgImg1'/>");
				return false;
			}
		}
			
		if ($("#MyForm input[name='attachFile2']").val().length != 0){
			var fileExtension = getFileExtension($("#MyForm input[name='attachFile2']").val()).toLowerCase();
			//if (fileExtension != "gif" & fileExtension != "jpg") {
			if (fileExtension != "jpg") {				
				alert("<spring:message code='msg.consult.require.fileExtensionJpgImg2'/>");
				return false;
			}
		}
			
		if ($("#MyForm input[name='attachFile3']").val().length != 0){
			var fileExtension = getFileExtension($("#MyForm input[name='attachFile3']").val()).toLowerCase();
			//if (fileExtension != "gif" & fileExtension != "jpg") {
			if (fileExtension != "jpg") {				
				alert("<spring:message code='msg.consult.require.fileExtensionJpgImg3'/>!!");
				return false;
			}
		}
			
		if ($("#MyForm input[name='vendorAttachFile']").val().length != 0){
			var fileExtension = getFileExtension($("#MyForm input[name='vendorAttachFile']").val()).toLowerCase();
			if(fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" ) {
				alert("<spring:message code='msg.consult.require.fileExtensionPptPptxPdf'/>");
				return false;
			}
		}
		
		return true;
	}

	function Nextfocus(lenth, name, a) {
		if (name == "tel1") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 3) {
				document.MyForm.tel2.focus();
			}
		} else if (name == "tel2") {

			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				document.MyForm.tel3.focus();
			}
		} else if (name == "tel3") {

			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				//document.MyForm.fax_no1.focus();
			}

		} else if (name == "officetel1") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 3) {
				document.MyForm.officetel2.focus();
			}
		} else if (name == "officetel2") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				document.MyForm.officetel3.focus();
			}
		} else if (name == "officetel3") {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value = "";
					return;
				}
			}
			if (lenth == 4) {
				//document.MyForm.home_addr.focus();
			}
		}
	}
	
	function deleteBusinessIntroduction(bmanNo) {
		
		if(confirm("<spring:message code='msg.common.confirm.delete'/>")){
			var str = {"bmanNo" 		: bmanNo };
					
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : "<c:url value='/edi/consult/NEDMCST0310deleteBusinessIntroduction.do'/>",
				data : JSON.stringify(str),
				success : function(data) {	
					if(data>0){
						alert("<spring:message code='msg.common.success.delete'/>");
						location.reload();
					}else{
						
					}
				}
			});			
		}
	}
	
	function deleteImg(bmanNo, seq) {
		
		if(confirm("<spring:message code='msg.common.confirm.delete'/>")){
						
			var str = { 
					"bmanNo" 		: bmanNo, 
					"seq" 		: seq
			};

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : "<c:url value='/edi/consult/NEDMCST0310deleteImg.do'/>",
				data : JSON.stringify(str),
				success : function(data) {	
					if(data>0){
						alert("<spring:message code='msg.common.success.delete'/>");
						location.reload();
					}else{
						
					}
				}
			});			
		}
	}
</script>

</head>

<body>

	<form name="MyForm"	action="<c:url value='/epc/edi/consult/NEDMCST0310insertApplyConfirm.do'/>" method="post" enctype="multipart/form-data" id="MyForm">		
			<input type="hidden" name="bmanNo" 		id="bmanNo"		value="<c:out value='${vendorSession.vendor.bmanNo}'/>" /> 
			<input type="hidden" name="gubun"  		id="gubun"  	value="<c:out value='${gubun }'/>" /> 
			<input type="hidden" name="prodArray" 	id="prodArray"  value="" /> 
			<input type="hidden" name="email" 		id="email"		value="" />
			<input type="hidden" name="l1Cd" 		id="l1Cd"		value="" />
		<div id="wrap">
			<div id="con_wrap">
				<div class="con_area">
					<br>
					
					<div class="s_title">
						<h2><spring:message code='text.consult.field.consultHopeDivision'/></h2>						
					</div>
					
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
								<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">
							</colgroup>
							<tbody>			
							<tr>
							<td colspan="4" align="right">
										<spring:message code='text.consult.field.consultHopeDivisionInfo'/>
									</td>
									
							</tr>				
						<tr>
							<th><p class="check"><spring:message code='text.consult.field.hopeDivision'/></p></th>
							<td>
								<div class="td_p">

									<select id="teamCd" name="teamCd">
										<option value="">선택</option>
										<c:forEach items="${consultTeamList}" var="code" begin="0">
											<option value="<c:out value='${code.teamCd}'/>"
												<c:if test="${vendor.teamCd eq code.teamCd }"> selected</c:if>><c:out value='${code.teamNm}'/></option>
										</c:forEach>
									</select>
								</div>
							</td>
				
			
					
							<th><p class="check"><spring:message code='text.consult.field.mainCat'/></p></th>
							<td >
								<select id="l1GroupCode" name="l1GroupCode" >
									<c:if test="${com == 'none' || empty l1GroupList}">
										<option value="all">선택</option>
									</c:if>
									<c:forEach items="${l1GroupList}" var="l1Group" varStatus="indexL1" >
										<c:if test="${indexL1.index == 0 }">
											<option value=all <c:if test="${l1Group.l1Cd eq ''}"> selected</c:if>>선택</option>
										</c:if>
										<option value="<c:out value='${l1Group.l1Cd}'/>" <c:if test="${l1Group.l1Cd eq vendor.l1Cd }"> selected</c:if> ><c:out value='${l1Group.l1Nm}'/> </option> 
									</c:forEach>
								</select>
							</td>							
						</tr>
						
						</tbody>
					</table>
					</div>
					
					<br><br>						
					<div class="s_title">
						<h2><spring:message code='text.consult.field.consultApplicantList'/></h2>							
					</div>					
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							
							<colgroup>
								<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">
							</colgroup>
							<tbody>
								<tr>
							
									<th ><p><spring:message code='text.consult.field.bmanNo'/></p></th>
									<td ><div class="td_p">
											<span class="t_gray t_12">
											<input type="text" name="bmanNo" class="txt"	value="<c:out value='${ vendorSession.vendor.bmanNo }'/>" id="bmanNo" readonly	style="width: 232px;"></span>
											</div>
									</td>
								</tr>
								
								<tr>
								
								<th width="130px"><p class="check"><spring:message code='text.consult.field.hndNm'/></p></th>
									<td width="300px">
										<div class="td_p">
											<span class="input_txt"><span>
											<input	maxlength="15" type="text" class="txt" name="hndNm"	id="hndNm" value="<c:out value='${vendor.hndNm}'/>" style="width: 232px;"	onkeyup="javascript:cal_byte(this, '50', '<spring:message code="text.consult.field.hndNm"/>');"></span></span>
										</div>
									</td>
									
									<th><p class="check"><spring:message code='text.consult.field.ceoNm'/></p></th>
									<td><div class="td_p">
											<span class="input_txt">
												<span><input maxlength="15" type="text" name="ceoNm" id="ceoNm" class="txt" value="<c:out value='${vendor.ceoNm}'/>" style="width: 232px;" onkeyup="javascript:cal_byte(this, '50', '<spring:message code="text.consult.field.ceoNm"/>');"></span></span>
										</div>
									</td>
								</tr>

								<tr>
									

<!-- 
											<th><p class="check">분류</p></th>
											<td>
												<div class="td_p">
													<select class="select" name="L1Cd">
														<option value="">선택</option>
														<c:forEach items="${L1cdData}" var="L1cdData" begin="0">
															<option value="${L1cdData.L1_CD}">${L1cdData.L1_NM}</option>
														</c:forEach>
													</select>

												</div>
											</td>
-->
									</td>

								</tr>
								<tr>
								
								</tr>
								<tr>
									<th><p class="check"><spring:message code='text.consult.field.officetel'/></p></th>
									<td>
										<div class="td_p">
											<span class="input_txt"><span>
												<input	type="text" name="officetel1" id="officetel1" class="txt"	value="<c:out value='${vendor.officetel1}'/>" maxlength="3"	onKeyUp="Nextfocus(this.value.length,this.name,this);"	style="width: 54px;"></span></span> - <span class="input_txt"><span>
												<input	type="text" name="officetel2" id="officetel2" class="txt"   value="<c:out value='${vendor.officetel2}'/>" maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);"	style="width: 54px;"></span></span> - <span class="input_txt"><span>
												<input	type="text" name="officetel3" id="officetel3" class="txt"	value="<c:out value='${vendor.officetel3}'/>" maxlength="4"	onKeyUp="Nextfocus(this.value.length,this.name,this);"	style="width: 54px;"></span></span>
										</div>
									</td>
									<th><p class="check"><spring:message code='text.consult.field.email'/></p></th>
									<td>
										<div class="td_p">
											<span class="input_txt"><span>											
											<input type="text" class="txt" name="email1" id="email1" value="<c:out value='${vendor.email1}'/>" style="width: 90px;" onkeyup="javascript:cal_byte(this, '22', '<spring:message code="text.consult.field.email"/>');"></span></span> @ <span class="input_txt"><span>
											<input type="text" class="txt" name="email2" id="email2" value="<c:out value='${vendor.email2}'/>" style="width: 110px;" onkeyup="javascript:cal_byte(this, '20', '<spring:message code="text.consult.field.email"/>');"></span></span>
										</div>
									</td>				
								</tr>
							</tbody>
							</table>
								
						<br><br>
						
					<div class="s_title">
						<h2><spring:message code='text.consult.field.consultProdList'/></h2>						
					</div>
				
					<div class="tb_form_comm">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col width="150px">
							<col width="150px">
							<col width="150px">
							<col width="150px">
							<col width="150px">
						</colgroup>
						<tr>
							<th class="t_center num"><spring:message code='text.consult.header.bunho'/></th>
							<th class="t_center num"><spring:message code='text.consult.field.productName'/></th>
							<th colspan="4" class="t_center num"><spring:message code='text.consult.header.imgAttachFile'/>
							
						 <br> <font color="red"><spring:message code='text.consult.field.1mjpg'/></font>
							
							</th>
						<th class="t_center num"></th>

						</tr>
							<c:if test="${fn:length(vendorProductList) > 0 }">
								<c:forEach var="vendorProduct" items="${vendorProductList}"	varStatus="status">
								<tr>
								<td class="t_center num"><span class="t_12"><c:out value='${vendorProduct.seq }'/></span></td>
									<input type="hidden" name="attachSeq" id="attachSeq" value="<c:out value='${vendorProduct.seq }'/>">
								<td class="t_center no_b_line"><span class="input_txt"><span>
									<input	type="text" name="AttachProductName" id="AttachProductName"	class="txt field_<c:out value='${vendorProduct.seq }'/>"	value="<c:out value='${vendorProduct.productName }'/>" maxlength="20" style="width: 130px;"></span></span>
								</td>
								<td colspan="4" align="left"><input type="hidden"	name="uploadFile<c:out value='${status.count }'/>"	class="field_<c:out value='${vendorProduct.seq }'/>"	value="<c:out value='${vendorProduct.attachFileCode }'/>" /> 
									&nbsp;&nbsp;&nbsp;
									<input	type="file" name="attachFile<c:out value='${status.count }'/>"	class="imageUpload" value="" style="width: 250px; height: 18px;"> 
									<c:if	test="${ not empty vendorProduct.attachFileCode }">
										<a href="javascript:img_view('<c:out value="${vendorProduct.attachFileCode }"/>', '<c:out value="${vendorProduct.atchFileFolder }"/>')"	class="btn">										
										<spring:message code='text.consult.field.viewImg'/>
										<span style="margin-left: 20px"></span>&nbsp;<a	href="javascript:deleteImg('<c:out value="${vendor.bmanNo }"/>','<c:out value="${vendorProduct.seq }"/>')"><spring:message code='text.consult.field.docDelete'/></a> 	
										</a>
									</c:if>
								</td>
								</tr>
								</c:forEach>
						 	</c:if>



							<c:if test="${fn:length(vendorProductList) == 0 }">
								<tr>
									<td class="t_center num"><span class="t_12">1</span></td>
										<input type="hidden" name="attachSeq" id="attachSeq" value="1"/>
									<td class="t_center no_b_line"><span class="input_txt"><span>
										<input type="text" name="AttachProductName" id="AttachProductName" class="txt field_1"	value="" maxlength="20" style="width: 130px;"></span></span>
									</td>
									<td class="t_center no_b_line" colspan="3">
										<input	type="hidden" name="uploadFile1" id="uploadFile1" class="field_1" value="" />
										<input type="file" name="attachFile1" id="attachFile1" value=""	class="imageUpload" style="width: 250px; height: 18px;">
									</td>
								</tr>
							</c:if>
							<c:if	test="${fn:length(vendorProductList) == 0 || fn:length(vendorProductList) == 1   }">
								<tr>
									<td class="t_center num"><span class="t_12">2</span></td>
									<input type="hidden" name="attachSeq" id="attachSeq" value="2" />
									<td class="t_center no_b_line"><span class="input_txt"><span>
									<input	type="text" name="AttachProductName" id="AttachProductName" class="txt field_2"	value="" maxlength="20" style="width: 130px;"></span></span></td>
									<td class="t_center no_b_line" colspan="3">
									<input type="hidden" name="uploadFile2" id="uploadFile2" value="" class="field_2" />
									<input type="file" name="attachFile2" id="attachFile2" value=""	class="imageUpload" style="width: 250px; height: 18px;">
									</td>
								</tr>
							</c:if>

							<c:if	test="${ fn:length(vendorProductList) == 0 || fn:length(vendorProductList) == 1  || fn:length(vendorProductList) == 2 }">
							<tr>
								<td class="t_center num"><span class="t_12">3</span></td>
										<input type="hidden" name="attachSeq" id="attachSeq" value="3">
								<td class="t_center no_b_line">
									<span class="input_txt"><span>
										<input type="text" name="AttachProductName" id="AttachProductName" class="txt field_3" value="" maxlength="20" style="width: 130px;">
									</span></span>
								</td>
								<td class="t_center no_b_line" colspan="3">
									<input type="hidden" name="uploadFile3" id="uploadFile3" class="field_3"     value="" />
									<input type="file"   name="attachFile3" id="attachFile3" class="imageUpload" value="" style="width: 250px; height: 18px;">
								</td>
								</tr>
							</c:if>
						</table>								
					</div>
								
					<br><br>
					<div class="s_title mt20">
						<h2><spring:message code='text.consult.field.businessIntroduction'/></h2>
					</div>			
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">						
						</colgroup>
						<tbody>			
							<tr>					
								<th width="140px"><p><spring:message code='text.consult.field.businessIntroduction'/></p></th>									
								<td colspan="3">
									<div class="td_p">
										 								
											<input type="hidden" name="vendorFile" id="vendorFile" value="<c:out value='${vendor.attachFileCode }'/>" /> 	
											<input type="file" name="vendorAttachFile" id="vendorAttachFile" class="docUpload" value="" style="width: 250px; height: 18px;">
										<c:if test="${not empty vendor.attachFileCode }">
											<span style="margin-left: 50px"></span>&nbsp;<a	href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo }"/>')"><spring:message code='text.consult.field.docDownload'/></a>
											<span style="margin-left: 50px"></span>&nbsp;<a	href="javascript:deleteBusinessIntroduction('<c:out value="${vendor.bmanNo }"/>')"><spring:message code='text.consult.field.docDelete'/></a>
										</c:if>
										<br>
										<font color="red"><spring:message code='text.consult.field.2mppt'/></font>	
									</div>
								</td>							
							</tr>
						</tbody>
					</table>
					</div>	
								
					<br><br>
					
					<div class="s_title mt20">
						<h2><spring:message code='text.consult.field.uniqueness'/></h2>
					</div>	
							
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">						
						</colgroup>
						<tbody>			
						<tr>					
						
						<td colspan="4" rowspan="5">
						<div class="box_etc_input">
							<textarea cols="122" rows="5" name="content" id="content"	style="width: 828px; height: 40px;"><c:out value='${vendor.pecuSubjectContent}'/></textarea>
						</div>						
						</tr>
					
						</tbody>
						</table>
					</div>

					<!-- button -->
					<div class="btn_c_wrap mt30">
						<span class="btn_red"><span>
						<a	href="javascript:checkStep3Submit();"><spring:message code='button.common.confirm' /></a></span></span>
					</div>
					<!--// button -->

	<script>
	<c:if test="${not empty vendor.l1Cd}">
	//selectL1ListApply();
	</c:if>
	</script>

	</form>
</body>
</html>