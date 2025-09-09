<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<%--
	Page Name 	: SRMJON0041.jsp
	Description : 입점상담신청[상세정보]
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

		if(url == "1") {
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


		$('.numberic').css('imeMode','disabled').keydown(function(){
			var e = window.event; //윈도우의 event를 잡	는것입니다.
			//입력 허용 키
			if ( (e.keyCode >= 48 && e.keyCode <= 57)				//숫자키
					|| (e.keyCode >= 96 && e.keyCode <= 105)			//키패드 숫자키
					|| (e.keyCode >= 112 && e.keyCode <= 123)       //F1 - F12
					|| e.keyCode == 8           //Backspace
					|| e.keyCode == 9           //Tab
					|| e.keyCode == 16           //Shift
					|| e.keyCode == 17           //Ctrl
					|| e.keyCode == 18           //Alt
					|| e.keyCode == 46          //Delete
					|| e.keyCode == 35          //End
					|| e.keyCode == 36          //Home
					|| e.keyCode == 37          //왼쪽 화살표
					|| e.keyCode == 39 ){       //오른쪽 화살표
				return true;
			} else {
				return false;
			}
		}).blur(function(){
			if( $(this).val() != null && $(this).val() != '' ) {
				addComma($(this));
			}
		}).focus(function(){
			var tmps = $(this).val().replace(/[^0-9]/g, '');
			$(this).val(tmps);
			this.selectionStart = this.selectionEnd = this.value.length;
		});
		
		$('.numberic2').css('imeMode','disabled').keydown(function(){
			var e = window.event; //윈도우의 event를 잡	는것입니다.
			//입력 허용 키
			if ( (e.keyCode >= 48 && e.keyCode <= 57)				//숫자키
					|| (e.keyCode >= 96 && e.keyCode <= 105)			//키패드 숫자키
					|| (e.keyCode >= 112 && e.keyCode <= 123)       //F1 - F12
					|| e.keyCode == 8           //Backspace
					|| e.keyCode == 9           //Tab
					|| e.keyCode == 16           //Shift
					|| e.keyCode == 17           //Ctrl
					|| e.keyCode == 18           //Alt
					|| e.keyCode == 46          //Delete
					|| e.keyCode == 35          //End
					|| e.keyCode == 36          //Home
					|| e.keyCode == 37          //왼쪽 화살표
					|| e.keyCode == 39 ){       //오른쪽 화살표
				return true;
			} else {
				return false;
			}
		}).blur(function(){
			if( $(this).val() != null && $(this).val() != '' ) {
				addComma2($(this));
			}
		}).focus(function(){
			var tmps = $(this).val().replace(/[^0-9]/g, '');
			$(this).val(tmps);
			this.selectionStart = this.selectionEnd = this.value.length;
		});

		//숫자 콤마 SET
		addComma2($('#MyForm input[name=productPrice]'));
		addComma2($('#MyForm input[name=basicAmt]'));
		addComma2($('#MyForm input[name=salesAmt]'));
		addComma2($('#MyForm input[name=empCount]'));

		<%--if ("<c:out value="${srmComp.foundationDate}"/>" != "") {--%>
			<%--dp_makeYearCombo($('#MyForm select[name=foundationDate]'), "<c:out value="${srmComp.foundationDate}"/>");--%>
		<%--}--%>


		<%--$('#MyForm select[name=foundationDate]').live('change',function(){--%>
			<%--dp_makeYearCombo($(this), $(this).val());--%>
		<%--});--%>

		$('#plantOwnType').live('change', function(){
			if($(this).val() == "X"){
				$('#plantRoleType').removeAttr("disabled");
				$('#plantRoleType').val("");
			} else {
				$('#plantRoleType').attr("disabled","disabled");
				$('#plantRoleType').val("");
			}
		});
		if("<c:out value="${srmComp.plantOwnType}"/>" == ""){
			$('#plantRoleType').attr("disabled","disabled");
			$('#plantRoleType').val("");
		}

		//커스텀태그 defName Set
		//선택
		var option = "<option value= \"\"><spring:message code='text.srm.field.select' /></option>";
		$(option).prependTo("#MyForm select[name=aboardChannelText]");
		$(option).prependTo("#MyForm select[name=plantRoleType]");
		$(option).prependTo("#MyForm select[name=cur]");

		//커스텀태그 값 SET
		$('#MyForm select[name=aboardChannelText]').val("<c:out value="${srmComp.aboardChannelText}"/>");
		$('#MyForm select[name=plantRoleType]').val("<c:out value="${srmComp.plantRoleType}"/>");
		$('#MyForm select[name=cur]').val("<c:out value="${srmComp.cur}"/>");
	});

	/**
	 * 현재년도부터 -5년까지의 콤보 생성
	 *
	 * @param obj
	 * @param year
	 */
	function dp_makeYearCombo(obj, year) {
		if ( obj == null ) return;
		$(obj).html("");
		var html = "";
		html += "<option value=''><spring:message code='text.srm.field.select'/></option>";/*spring:message : 선택*/

		for( var i = year-5; i < year; i++ ) {
			html+="<option value=" + i +">" + i + "</option>"
		}

		for( var i = year; i < Number(year)+5; i++ ) {
			html+="<option value=" + i +">" + i + "</option>"
		}

		$(html).appendTo(obj);
		$(obj).val(year);
	}

	//금액 콤마
	function addComma(obj) {
		var tmps = $(obj).val().replace(/[^0-9]/g, '');
		var tmps2 = tmps.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
		$(obj).val(tmps2);
	}
	
	//금액 콤마(소숫점 포함)
	function addComma2(obj) {
		var tmps1;
		var tmps2;
		var tmps3;
		var tmps4;
		
		if ($(obj).val().indexOf(".") > -1) {
			
			tmps1 = $(obj).val().split(".");
			tmps2 = tmps1[0].replace(/[^0-9]/g, '');
			tmps3 = tmps2.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
			tmps4 = tmps1[1].replace(/[^0-9]/g, '');
			$(obj).val(tmps3 + "." + tmps4);
			
		} else {
			
			tmps1 = $(obj).val().replace(/[^0-9]/g, '');
			tmps2 = tmps1.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
			$(obj).val(tmps2);
			
		}
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
		
		var target = "<spring:message code='text.srm.field.foundationDate'/>";	// 설립년도
		if(!$.trim($('#MyForm select[name=foundationDate]').val())){
			alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 설립년도을(를) 선택하세요. */
			$('#MyForm select[name=foundationDate]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.basicAmt'/>";	// 자본금
		if(!$.trim($('#MyForm input[name=basicAmt]').val())){
			alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 자본금을(를) 입력하세요. */
			$('#MyForm input[name=basicAmt]').focus();
			return false;
		}
		
		if (!cal_3byte($('#MyForm input[name=basicAmt]').val().replaceAll(",",""), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
			$('#MyForm input[name=basicAmt]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.salesAmt'/>";	// 연간 매출액
		if(!$.trim($('#MyForm input[name=salesAmt]').val())){
			alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 연간 매출액을(를) 입력하세요. */
			$('#MyForm input[name=salesAmt]').focus();
			return false;
		}
		
		if (!cal_3byte($('#MyForm input[name=salesAmt]').val().replaceAll(",",""), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
			$('#MyForm input[name=salesAmt]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.empCount'/>";	// 종업원수
		if(!$.trim($('#MyForm input[name=empCount]').val())){
			alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 종업원수을(를) 입력하세요. */
			$('#MyForm input[name=empCount]').focus();
			return false;
		}
		
		if (!cal_3byte($('#MyForm input[name=empCount]').val().replaceAll(",",""), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
			$('#MyForm input[name=empCount]').focus();
			return false;
		}
		
		//if(!$.trim($('#MyForm input[name=plantOwnType]:checked').val())){
		//	var target = "<spring:message code='text.srm.field.plantOwnType'/>";
		//	alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 생산공장 유무을(를) 선택하세요. */
		//	$('#MyForm select[name=plantOwnType]').focus();
		//	return false;
		//}
		
		target = "<spring:message code='text.srm.field.plantRoleType'/>";	// 공장운영형태
		if($('#MyForm input[name=plantOwnType]:checked').val() == "X"){
			if(!$.trim($('#MyForm select[name=plantRoleType]').val())){
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 공장운영형태을(를) 선택하세요. */
				$('#MyForm select[name=plantRoleType]').focus();
				return false;
			}
		}
		
		target = "<spring:message code='text.srm.field.mainCustomer'/>".replaceAll("<br>","");	// 동업계 입점현황
		/*
		if(!$.trim($('#MyForm input[name=mainCustomer]').val())){
			alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>"); spring:message : 동업계 입점현황을(를) 입력하세요. 
			$('#MyForm input[name=mainCustomer]').focus();
			return false;
		}
		*/
		if (!cal_3byte($('#MyForm input[name=mainCustomer]').val(), '200', setPermitMsg(target), setByteMsg(target, '200'))) {
			$('#MyForm input[name=mainCustomer]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.aboardChannelText'/>";	// 롯데마트 기 진출 채널
		if(!$.trim($('#MyForm select[name=aboardChannelText]').val())){
			alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 롯데마트 기 진출 채널을(를) 선택하세요. */
			$('#MyForm select[name=aboardChannelText]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.aboardCountryText'/>";	// 롯데마트 기 진출 국가
		if (!cal_3byte($('#MyForm input[name=aboardCountryText]').val(), '500', setPermitMsg(target), setByteMsg(target, '500'))) {
			$('#MyForm input[name=aboardCountryText]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.productName'/>";	// 대표상품명
		if (!cal_3byte($('#MyForm input[name=productName]').val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
			$('#MyForm input[name=productName]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.productPrice'/>";	// 공급가
		if (!cal_numberCheck($('#MyForm input[name=productPrice]').val().replaceAll(",",""),'22','3')) {
			alert("<spring:message code='text.srm.alert.numberCheck' arguments='"+target+", 3, 22'/>");/* spring:message : 공급가은(는) 소숫점 3자리 이상을 초과할 수 없습니다.*/
			$('#MyForm input[name=productPrice]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.mainProduct'/>";	// 주요상품
		if (!cal_3byte($('#MyForm input[name=mainProduct]').val(), '400', setPermitMsg(target), setByteMsg(target, '400'))) {
			$('#MyForm input[name=mainProduct]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.dealingBrandProduct'/>";	// 주사용 브랜드
		if (!cal_3byte($('#MyForm input[name=dealingBrandProduct]').val(), '60', setPermitMsg(target), setByteMsg(target, '60'))) {
			$('#MyForm input[name=dealingBrandProduct]').focus();
			return false;
		}
		
		var file;
		var fileExt;
		var fileName;
		file = $('#MyForm input[name=productImgPathFile]').val();
		fileExt = file.substring(file.lastIndexOf('.')+1);
		fileName = file.substring(file.lastIndexOf('\\')+1);

		<%--target = "<spring:message code='text.srm.field.productImgPath'/>";	// 상품이미지--%>
		<%--if (!$.trim($('#MyForm input[name=productImgPathFile]').val()) && !$.trim($('#MyForm input[name=productImgPath]').val())) {--%>
			<%--alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");/* spring:message : 상품이미지을(를) 첨부하세요. */--%>
			<%--return false;--%>
		<%--}--%>
		
		if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.image.ext'/>", fileExt)){
			var target1 = "<spring:message code='text.srm.field.productImgPath'/>";
			var target2 = "<spring:message code='edi.srm.file.image.ext'/>";
			alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*상품이미지은(는) {1}파일만 업로드 할 수 있습니다.*/
			return false;
		}
		
		target = "<spring:message code='text.srm.field.fileName' />";	// 첨부파일이름
		if (!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
			$('#MyForm input[name=productImgPathFile]').focus();
			return false;
		}

		file = $('#MyForm input[name=productIntroAttachNoFile]').val();
		fileExt = file.substring(file.lastIndexOf('.')+1);
		fileName = file.substring(file.lastIndexOf('\\')+1);


		<%--target = "<spring:message code='text.srm.field.productIntroAttachNo'/>";	// 상품소개서--%>
		<%--if (!$.trim($('#MyForm input[name=productIntroAttachNoFile]').val()) && !$.trim($('#MyForm input[name=productIntroAttachNo]').val())) {--%>
			<%--alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");/* spring:message : 상품소개서을(를) 첨부하세요. */--%>
			<%--return false;--%>
		<%--}--%>
		
		if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.all.ext'/>", fileExt)){
			var target1 = "<spring:message code='text.srm.field.productIntroAttachNo'/>";
			var target2 = "<spring:message code='edi.srm.file.all.ext'/>";
			alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*상품소개서은(는) {1}파일만 업로드 할 수 있습니다.*/
			return false;
		}
		
		if (!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
			$('#MyForm input[name=productIntroAttachNoFile]').focus();
			return false;
		}

		file = $('#MyForm input[name=companyIntroAttachNoFile]').val();
		fileExt = file.substring(file.lastIndexOf('.')+1);
		fileName = file.substring(file.lastIndexOf('\\')+1);


		<%--target = "<spring:message code='text.srm.field.companyIntroAttachNo'/>";	// 사업설명서--%>
		<%--if (!$.trim($('#MyForm input[name=companyIntroAttachNoFile]').val()) && !$.trim($('#MyForm input[name=companyIntroAttachNo]').val())) {--%>
			<%--alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");/* spring:message : 사업설명서을(를) 첨부하세요. */--%>
			<%--return false;--%>
		<%--}--%>
		
		if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.all.ext'/>", fileExt)){
			var target1 = "<spring:message code='text.srm.field.companyIntroAttachNo'/>";
			var target2 = "<spring:message code='edi.srm.file.all.ext'/>";
			alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*사업설명서은(는) {1}파일만 업로드 할 수 있습니다.*/
			return false;
		}
		
		if (!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
			$('#MyForm input[name=companyIntroAttachNoFile]').focus();
			return false;
		}

		<%----%>
		<%--var totFleSize = 0;--%>
		<%--var agent = navigator.userAgent.toLowerCase();--%>
		<%--if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {--%>

		<%--} else {--%>
			<%--//IE 외 브라우저에서 파일 크기 채크--%>
			<%--$("input[name=productImgPathFile],input[name=productIntroAttachNoFile],input[name=companyIntroAttachNoFile]").each(function(){--%>
				<%--if($(this).val() != "" && $(this).val() != null){--%>
					<%--totFleSize += this.files[0].size;--%>

				<%--}--%>
			<%--});--%>

			<%--if(totFleSize >= 20971520){//20971520 : 20M--%>
				<%--alert("<spring:message code='msg.srm.alert.validation.filesize'/>");/*spring:message : 업로드 파일 크기의 합은 20M 이상 첨부할 수 없습니다.*/--%>
				<%--return false;--%>
			<%--}--%>

		<%--}--%>

		file = $('#MyForm input[name=smAttachNoFile]').val();
		fileExt = file.substring(file.lastIndexOf('.')+1);
		fileName = file.substring(file.lastIndexOf('\\')+1);


		<%--target = "<spring:message code='text.srm.field.smAttachNo'/>";	// 중소기업확인증--%>
		<%--if (!$.trim($('#MyForm input[name=smAttachNoFile]').val()) && !$.trim($('#MyForm input[name=smAttachNo]').val())) {--%>
			<%--alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");/* spring:message : 중소기업확인증을(를) 첨부하세요. */--%>
			<%--return false;--%>
		<%--}--%>
		
		if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.all.ext'/>", fileExt)){
			var target1 = "<spring:message code='text.srm.field.smAttachNo'/>";
			var target2 = "<spring:message code='edi.srm.file.all.ext'/>";
			alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*중소기업확인증은(는) {1}파일만 업로드 할 수 있습니다.*/
			return false;
		}
		
		if (!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
			$('#MyForm input[name=smAttachNoFile]').focus();
			return false;
		}

		return true;
	}
	
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
		$("#MyForm").attr("action", "<c:url value='/edi/srm/updateHiddenCompDetailInfo.do'/>");
		$("#MyForm").submit();
	}

	//임시저장
	function doTempSave() {
		if(!validation()) return;
		if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/
		$('#MyForm input[name=url]').val("<c:url value='1'/>");
		$("#MyForm").attr("action", "<c:url value='/edi/srm/updateHiddenCompDetailInfo.do'/>");
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
</script>


</head>


<body>
<form id="MyForm" name="MyForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value='${srmComp.houseCode}'/>"/>
	<input type="hidden" id="sellerCode" name="sellerCode" value="<c:out value='${srmComp.sellerCode}'/>"/>
	<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value='${srmComp.reqSeq}'/>"/>
	<input type="hidden" id="url" name="url"/>
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
					<%--<li class="txt_l"><em><spring:message code="text.srm.field.srmjon0040Notice3"/></em></li>	&lt;%&ndash; 신용평가기관에 롯데마트 정보동의를 요청하셔야 정보확인이 가능합니다.(요청 후 익일 확인가능) &ndash;%&gt;--%>
				</ul>
			</div><!-- END 알림 -->

			<!-- 정보 입력 유형 탭 : 선택된 li에 클래스 on을 붙입니다.-->
			<ul class="sub_tab col4">
				<li rel="tab1"><a href="#" onclick="tab_page('0');"><spring:message code='tab.srm.srmjon0030.tab1'/></a></li>					<%-- 기본정보--%>
				<li class="on" rel="tab2"><a href="#" onClick=""><spring:message code='tab.srm.srmjon0030.tab2'/></a></li>	<%-- 상세정보--%>
				<li rel="tab3"><a href="#" onClick="tab_page('2');"><spring:message code='tab.srm.srmjon0030.tab3'/></a></li>	<%-- 인증/신용평가 정보--%>
				<li rel="tab4"><a href="#" onClick="tab_page('3');"><spring:message code='tab.srm.srmjon0030.tab4'/></a></li>	<%-- 정보확인--%>
			</ul><!-- END 정보 입력 유형 탭 -->

			<%----- 운영현황 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0041.sub.title1'/></h3>	<%-- 운영현황--%>
				<div class="right_btns">
					<button type="button" class="btn_normal btn_blue" onclick="javascript:doTempSave();"><spring:message code='button.srm.tempSave'/></button>	<%-- 임시저장--%>
					<button type="button" class="btn_normal btn_red" onclick="javascript:doCancel();"><spring:message code='button.srm.vendorConsultReqCancel'/></button>	<%-- 입점상담신청 취소--%>
				</div>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<!-- 정보 입력폼 -->
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th><label for="foundationDate">* <spring:message code='text.srm.field.foundationDate'/></label></th><%--spring:message : 설립년도--%>
						<td>
							<c:set var="fromYear" value="1900"/>
							<c:set var="toYear" value="2100"/>

							<select id="foundationDate" name="foundationDate" style="width:100px;">
								<option value=""><spring:message code='text.srm.field.select'/></option>
								<c:forEach var="year" begin="${fromYear}" end="${toYear}" varStatus="status">
									<option value="<c:out value="${year}"/>" <c:if test="${year eq srmComp.foundationDate}"> selected</c:if>> <c:out value="${year}"/></option>
								</c:forEach>
							</select>
						</td>
						<th><label for="basicAmt">* <spring:message code='text.srm.field.basicAmt'/></label></th><%--spring:message : 자본금--%>
						<td>
							<input type="text" id="basicAmt" name="basicAmt" title="<spring:message code='text.srm.field.basicAmt'/>" value="<c:out value='${srmComp.basicAmt}'/>" class="input_txt_default numberic" style="text-align: right;" /> <span class="ml10"><spring:message code='text.srm.field.unit'/></span><%--spring:message : (단위:만원)--%>
						</td>
					</tr>
					<tr>
						<th><label for="salesAmt">* <spring:message code='text.srm.field.salesAmt'/></label></th><%--spring:message : 연간 매출액(최근 3년)--%>
						<td>
							<input type="text" id="salesAmt" name="salesAmt" title="<spring:message code='text.srm.field.salesAmt'/>" value="<c:out value='${srmComp.salesAmt}'/>" class="input_txt_default numberic" style="text-align: right;" /> <span class="ml10"><spring:message code='text.srm.field.unit'/></span><%--spring:message : (단위:만원)--%>
						</td>
						<th><label for="empCount">* <spring:message code='text.srm.field.empCount'/></label></th><%--spring:message : 종업원 수(정규직)--%>
						<td>
							<input type="text" id="empCount" name="empCount" title="<spring:message code='text.srm.field.empCount'/>" value="<c:out value='${srmComp.empCount}'/>" class="input_txt_default numberic" style="text-align: right;" /> <spring:message code='text.srm.field.persons'/><%--spring:message : 명--%>
						</td>
					</tr>
					<tr>
						<th><label for="plantOwnType">* <spring:message code='text.srm.field.plantOwnType'/></label></th><%--spring:message : 생산공장유무--%>
						<td>
							<input type="radio" id="plantOwnType" name="plantOwnType" title="<spring:message code='text.srm.field.plantOwnType'/>" value="X" <c:if test="${srmComp.plantOwnType eq 'X'}">checked</c:if>/> Yes
							<input type="radio" id="plantOwnType" name="plantOwnType" title="<spring:message code='text.srm.field.plantOwnType'/>" value="" <c:if test="${srmComp.plantOwnType ne 'X'}">checked</c:if>/> No
						</td>
						<th><label for="plantRoleType"><spring:message code='text.srm.field.plantRoleType'/></label></th><%--spring:message : 공장운영형태--%>
						<td>
							<srm:codeTag comType="SELECT" objId="plantRoleType" objName="plantRoleType" formName="" parentCode="M729" width="100px"/>
						</td>
					</tr>
					<tr>
						<th><label for="smFlag"><spring:message code='text.srm.field.smFlag'/></label></th><%--spring:message : 중소기업여부--%>
						<td colspan="3">
							<input type="radio" id="smFlag" name="smFlag" title="<spring:message code='text.srm.field.smFlag'/>" value="Y" <c:if test="${srmComp.smFlag eq 'Y'}">checked</c:if>/> Yes
							<input type="radio" id="smFlag" name="smFlag" title="<spring:message code='text.srm.field.smFlag'/>" value="N" <c:if test="${srmComp.smFlag ne 'Y'}">checked</c:if>/> No
						</td>
					</tr>
					<tr>
						<th><label for="smFlag"><spring:message code='text.srm.field.smAttachNo'/></label></th><%--spring:message : 중소기업확인증--%>
						<td colspan="3">
							<input type="hidden" id="smAttachNoFileName" name="smAttachNoFileName" title="<spring:message code='text.srm.field.smAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
							<input type="file" id="smAttachNoFile" name="smAttachNoFile" title="<spring:message code='text.srm.field.smAttachNo'/>" onchange="javascript:fileUpload(this, 'smAttachNoFileName');"/>
							<btn>
								<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('smAttachNoFile','smAttachNoFileName')"/><%--spring:message : 취소--%>
								<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('smAttachNo', 'smAttachNoA');"/><%--spring:message : 삭제--%>
							<btn>
							<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div><%--spring:message : ※ Notice : JPG 이미지 파일로 업로드 하세요!--%>
							<c:if test="${not empty srmComp.smAttachNo}">
								<div>
									<input type="hidden" id="smAttachNo" name="smAttachNo" value="<c:out value="${srmComp.smAttachNo}"/>"/>
									<a id="smAttachNoA" name="smAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.smAttachNo}"/>,'1');"><c:out value="${srmComp.smAttachNoName}"/></a>
								</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<th><label for="mainCustomer"><spring:message code='text.srm.field.mainCustomer'/></label></th><%--spring:message : 동업계 입점현황<br>(주요거래 업체)--%>
						<td colspan="3">
							<input type="text" id="mainCustomer" name="mainCustomer" title="<spring:message code='text.srm.field.mainCustomer'/>" value="<c:out value='${srmComp.mainCustomer}'/>" class="input_txt_default" style="width:90%"/>
						</td>
					</tr>
					<tr>
						<th><label for="aboardChannelText">* <spring:message code='text.srm.field.aboardChannelText'/></label></th><%--spring:message : 롯데마트 기 진출 채널--%>
						<td>
							<srm:codeTag objId="aboardChannelText" comType="SELECT" objName="aboardChannelText" formName="" parentCode="SRM061" width="100px"/>
							<%--<input type="text" id="aboardChannelText" name="aboardChannelText" title="<spring:message code='text.srm.field.aboardChannelText'/>" class="input_txt_default" value="<c:out value='${srmComp.aboardChannelText}'/>"/>--%>
						</td>
						<th><label for="aboardCountryText"><spring:message code='text.srm.field.aboardCountryText'/></label></th><%--spring:message : 롯데마트 기 진출 국가--%>
						<td>
							<input type="text" id="aboardCountryText" name="aboardCountryText" title="<spring:message code='text.srm.field.aboardCountryText'/>" class="input_txt_default" value="<c:out value='${srmComp.aboardCountryText}'/>"/>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							※ 롯데마트는 파트너사에 입점절차를 위해 필요한 정보와 무관하거나 경영정보 제공을 강요하는 행위를 금지하고 있습니다. 
							<br>&nbsp;&nbsp;상기 정보 외 부당하게 경영정보 제공을 요구 받았을 경우 당사 
							<a href="http://company.lottemart.com/bc/lmt/PbcmLmt0005/main.do?menuCd=BM090208" target="_blank"><font color="red">준법경영상담실</font></a>로 신고하여주시기 바랍니다.
							<br>&nbsp;&nbsp;▶ 경영정보  예시
   <br>&nbsp;&nbsp;&nbsp;&nbsp;· 타거래사에 납품하는 상품의 매출액, 기간별 판매량
   <br>&nbsp;&nbsp;&nbsp;&nbsp;· 타거래사 납품하는 상품의 원가
						</td>
					</tr>
				</tbody>
			</table><!-- END 정보 입력폼 -->
			<%----- 운영현황 End -------------------------%>


			<%----- 상품현황 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0041.sub.title2'/></h3>	<%-- 운영현황--%>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<th><label for="productName"><spring:message code='text.srm.field.productName'/></label></th><%--spring:message : 대표상품명--%>
					<td>
						<input type="text" id="productName" name="productName" title="<spring:message code='text.srm.field.productName'/>" class="input_txt_default" value="<c:out value='${srmComp.productName}'/>" style="width: 90%;" />
					</td>
					<th><label for="productPrice"><spring:message code='text.srm.field.productPrice'/>/<spring:message code='text.srm.field.cur'/></label></th><%--spring:message : 납품가/통화--%>
					<td>
						<input type="text" id="productPrice" name="productPrice" title="<spring:message code='text.srm.field.productPrice'/>/<spring:message code='text.srm.field.cur'/>" class="input_txt_default numberic2" value="<c:out value='${srmComp.productPrice}'/>"  style="text-align: right;" />

						<c:set var="cur"><c:out value="${srmComp.cur}"/></c:set>
						<c:if test="${empty cur}">
							<c:set var="cur" value="KRW"></c:set>
						</c:if>

						<srm:codeTag comType="SELECT" objId="cur" objName="cur" formName="" parentCode="M002" attr="style='width:100px;'" sortStd="NAME"/>
					</td>
				</tr>
				<tr>
					<th colspan="4">
						<spring:message code="text.srm.field.srmjon0041Notice2"/><%--spring:message : 첨부파일은 용량 총 합이 20MB 이하로 첨부해주세요.--%>
					</th>
				</tr>

				<tr>
					<th><label for="productImgPath"><spring:message code='text.srm.field.productImgPath'/></label></th><%--spring:message : 상품이미지--%>
					<td colspan="3">
						<input type="hidden" id="productImgPathFileName" name="productImgPathFileName" title="<spring:message code='text.srm.field.productImgPath'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="productImgPathFile" name="productImgPathFile" title="<spring:message code='text.srm.field.productImgPath'/>" onchange="javascript:fileUpload(this, 'productImgPathFileName');" accept="image/*"/>
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('productImgPathFile','productImgPathFileName')"/><%--spring:message : 취소--%>
							<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('productImgPath', 'productImgPathA');"/><%--spring:message : 삭제--%>
						<btn>
							<div style="color:red;"><spring:message code='text.srm.field.srmjon0042Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 올려주세요.--%>
						<c:if test="${not empty srmComp.productImgPath}">
							<div>
								<input type="hidden" id="productImgPath" name="productImgPath" value="<c:out value="${srmComp.productImgPath}"/>"/>
								<a id="productImgPathA" name="productImgPathA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.productImgPath}"/>,'1');"><c:out value="${srmComp.productImgPathName}"/></a>
							</div>
						</c:if>
					</td>
				</tr>
				<tr>
					<th><label for="productIntroAttachNo"><spring:message code='text.srm.field.productIntroAttachNo'/></label></th><%--spring:message : 상품소개서--%>
					<td colspan="3">
						<input type="hidden" id="productIntroAttachNoFileName" name="productIntroAttachNoFileName" title="<spring:message code='text.srm.field.productIntroAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="productIntroAttachNoFile" name="productIntroAttachNoFile" title="<spring:message code='text.srm.field.productIntroAttachNo'/>" onchange="javascript:fileUpload(this, 'productIntroAttachNoFileName');"/>
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('productIntroAttachNoFile','productIntroAttachNoFileName')"/><%--spring:message : 취소--%>
							<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('productIntroAttachNo', 'productIntroAttachNoA');"/><%--spring:message : 삭제--%>
						<btn>
						<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 올려주세요.--%>
						<c:if test="${not empty srmComp.productIntroAttachNo}">
							<div>
								<input type="hidden" id="productIntroAttachNo" name="productIntroAttachNo" value="<c:out value="${srmComp.productIntroAttachNo}"/>"/>
								<a id="productIntroAttachNoA" name="productIntroAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.productIntroAttachNo}"/>,'1');"><c:out value="${srmComp.productIntroAttachNoName}"/></a>
							</div>
						</c:if>
					</td>
				</tr>
				<tr>
					<th><label for="companyIntroAttachNo"><spring:message code='text.srm.field.companyIntroAttachNo'/></label></th><%--spring:message : 사업설명서--%>
					<td colspan="3">
						<input type="hidden" id="companyIntroAttachNoFileName" name="companyIntroAttachNoFileName" title="<spring:message code='text.srm.field.companyIntroAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="companyIntroAttachNoFile" name="companyIntroAttachNoFile" title="<spring:message code='text.srm.field.companyIntroAttachNo'/>" onchange="javascript:fileUpload(this, 'companyIntroAttachNoFileName');"/>
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('companyIntroAttachNoFile','companyIntroAttachNoFileName')"/><%--spring:message : 취소--%>
							<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('companyIntroAttachNo', 'companyIntroAttachNoA');"/><%--spring:message : 삭제--%>
						<btn>
						<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 올려주세요.--%>
						<c:if test="${not empty srmComp.companyIntroAttachNo}">
							<div>
								<input type="hidden" id="companyIntroAttachNo" name="companyIntroAttachNo" value="<c:out value="${srmComp.companyIntroAttachNo}"/>"/>
								<a id="companyIntroAttachNoA" name="companyIntroAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.companyIntroAttachNo}"/>,'1');"><c:out value="${srmComp.companyIntroAttachNoName}"/></a>
							</div>
						</c:if>
					</td>
				</tr>
				<tr>
					<th><label for="mainProduct"><spring:message code='text.srm.field.mainProduct'/></label></th><%--spring:message : 주요상품--%>
					<td colspan="3">
						<input type="text" id="mainProduct" name="mainProduct" title="<spring:message code='text.srm.field.mainProduct'/>" class="input_txt_default" value="<c:out value='${srmComp.mainProduct}'/>" style="width: 90%;" />
					</td>
				</tr>
				<tr>
					<th><label for="dealingBrandProduct"><spring:message code='text.srm.field.dealingBrandProduct'/></label></th><%--spring:message : 주사용 브랜드--%>
					<td colspan="3">
						<input type="text" id="dealingBrandProduct" name="dealingBrandProduct" title="<spring:message code='text.srm.field.dealingBrandProduct'/>" class="input_txt_default" value="<c:out value='${srmComp.dealingBrandProduct}'/>" style="width: 90%;" />
					</td>
				</tr>
		</table>
		<%----- 상품현황 End -------------------------%>
			
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