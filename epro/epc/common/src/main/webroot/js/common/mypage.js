$(function() {
	
	if($('#auth').val() == '9'){
		$(".adminDiv").show();
		$(".userDiv").hide();
	}else{
		$(".userDiv").show();
		$(".adminDiv").hide();
	}
	
	if($('#auth').val() == '8'){
		$("#regiPhoneNoCertiBtn").show();
	}else{
		$("#regiPhoneNoCertiBtn").hide();
	}
	
	//PE 비밀번호 변경
	$('.pePwChgBtn').click(function() {
		clearText();
		$('#mdn').val(this.value);
		$('#divPePwChg').dialog('open');
	});
	//대표번호 설정
	$('.presentPhoneBtn').click(function() {
		Common.choiceDialog("<br/><br/>대표번호로 설정하시겠습니까?", 'setPresent('+this.value+')');
	});
	//MDN 삭제
	$('.phoneDeleteBtn').click(function() {
		Common.choiceDialog("<br/><br/>"+this.value+" 관련 정보를 삭제 하시겠습니까?", 'deleteMdn("'+this.value+'")');
	});
    //휴대폰 번호 변경
	$('.phoneNumChgBtn').click(function() {
		clearText();
		$('#updateFlg').val("Y");
		$('#oriMdn').val(this.value);
		$('#npiTitle').append(
				'번호 변경'
		);
		$('#divNewPhoneIns').dialog('open');
	});
	//기기 변경 프로세스 변경 USIM 기변 가능 ,동일번호 다른 기종으로 변경 
	$('.phoneModelChgBtn').click(function() {
		clearText();
		$('#updateFlg').val("Y");
		var mdn = this.value;
		var phoneTx1 = mdn.substring(0,3);
		var phoneTx2 = mdn.substring(3,7);
		var phoneTx3 = mdn.substring(7,11);
		$('#mdn').val(mdn);
		$('#mcPhoneTx1').val(phoneTx1);
		$('#mcPhoneTx2').val(phoneTx2);
		$('#mcPhoneTx3').val(phoneTx3);
		$('#divModelChange').dialog('open');
	});

	//새 휴대폰 등록
	$('.regNewPhoneBtn').click(function() {
		clearText();
		$('#updateFlg').val("N");
		$('#npiTitleName').val('새 휴대폰 등록');
		$('#npiTitle').append(
				'새 휴대폰 등록'
		);
		$('#divNewPhoneIns').dialog('open');
	});
	
	//무인증 휴대폰 등록
	$('#regiPhoneNoCertiBtn').click(function() {
		clearText();
		$.ajax({
			type: "GET",
			cache: false,
			url: $('#rootPath').val()+"/mypage/ajaxDeviceList.do",
			dataType: "json",
			success: function(json) {
				$("#phoneModel").empty().append(json.options);
				$("#plats").append(json.plats);
			}
		});
		$('#divNoCertiNewPhoneIns').dialog('open');
	});

	//인증번호 받기
	$('.sndSms').click(function() {
		var mobileNo = $('#mdn').val();
		$.ajax({
			type: "GET",
			cache: false,
			url: $('#rootPath').val()+"/login/ajaxSendAuthSMS.do",
			data: {mobileNo:mobileNo},
			dataType: "json",
		    //beforeSend: function() {
			//	Common.loadingDialog();
			// },
			success: function(json) {
				Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message, "sendSMS(" + json.success+",'"+json.authNo+"','"+json.modelName+"', '"+mobileNo+"', '"+json.osName+"')");
			}
		});
	});

	//닫기 버튼 설정
	$('#closeDivCoreDwnBtn').click(function() {
		$('#divCoreDwn').dialog('close');
	});

	
});
//******************************************************************************************
function clearText() {
	$('#mdn').val('');
	$('#osName').val('');
	$('#model').val('');
	$('#authNo').val('');
	$('#oriMdn').val('');
	$('#updateFlg').val("N");
}

function sendPassAuthSMS(mobileNo) {
	$.ajax({
		type: "GET",
		cache: false,
		url: $('#rootPath').val()+"/login/ajaxSendPassAuthSMS.do",
		data: {mobileNo:mobileNo},
		dataType: "json",
		success: function(json) {
			//Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message, "setSMSData(" + json.success+",'"+json.authNo+"','"+json.modelName+"', '"+mobileNo+"', '"+json.osName+"')");
		}
	});
}

function sendAuthSMS(mobileNo) {
	$.ajax({
		type: "GET",
		cache: false,
		url: $('#rootPath').val()+"/login/ajaxSendAuthSMS.do",
		data: {mobileNo:mobileNo},
		dataType: "json",
	    //beforeSend: function() {
		//	Common.loadingDialog();
		// },
		success: function(json) {
			//Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message, "setSMSData(" + json.success+",'"+json.authNo+"','"+json.modelName+"', '"+mobileNo+"', '"+json.osName+"')");
		}
	});
}

function sendChkMdnAuthSMS(mobileNo) {
	$.ajax({
		type: "GET",
		cache: false,
		url: $('#rootPath').val()+"/login/ajaxSendAuthSMS.do",
		data: {mobileNo:mobileNo},
		dataType: "json",
		success: function(json) {
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message, "setSMSData(" + json.success+",'"+json.authNo+"','"+json.modelName+"', '"+mobileNo+"', '"+json.osName+"')");
		}
	});
}

function setSMSData(success, authNo, modelName, mobileNo,osName) {
	if(success){
		$('#authNo').val(authNo);
		$('#model').val(modelName);
		$('#osName').val(osName);
	}
}

//비밀 번호 변경 AJAX 처리 (SSS)
function ppcConfirmPePwChg(){
		$.ajax({
			type: "GET",
			cache: false,
			url: $('#rootPath').val()+"/mypage/ajaxMemberDevicePePwChg.do",
			data: {pePassword:$('#ppcNewPePw').val(),oriMdn:$('#mdn').val(),oriPePassword:$('#ppcOriPePw').val()},
			dataType: "json",
			success: function(json) {
				Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message, "ppcResult(" + json.success + ")");
			}
		});
}

//비밀 번호 변경 결과 처리 (SSS)
function ppcResult(success) {
	if(success){
		$('#divPePwChg').dialog('close');
		goList();
	}
}

function ppiResult(success) {
	var osName = $('#osName').val();
	$('#divPePwIns').dialog('close');
	if(success){
		if(osName == 'Android' || osName == '001'){
			$('#divAdroidCoreDwn').dialog('open');
		}else{
			$('#divCoreDwn').dialog('open');
		}
	}
}

function pncResult(success) {
	$('#divPhoneNumChg').dialog('close');
	goList();
}

function tempAndroidMessage(){
	alert("안드로이드 준비중 입니다.");
}

function sendSMS(success, authNo, modelName, mobileNo,osName) {
	if(success){
		$('#authNo').val(authNo);
		$('#model').val(modelName);
		$('#mdn').val(mobileNo);
		$('#osName').val(osName);
	}else{
		
	}
}

function ssiCheckSMS(success) {
	if(success){
		$('#pncMdn').val($('#ssiMdn').val());
		$('#ddPncMdn').append('<strong class="txt">'+Common.makeTelTxt($('#ssiMdn'))+'</strong>');
		$('#divSmsCerti').dialog('close');
		$('#divPhoneNumChg').dialog('open');
	}else{

	}
}

//기기변경에서 PE PW설정으로 데이터를 넘기는 경우 
function setPePwData(success) {
	if(success){
		$('#oriMdn').val($('#mdn').val());
		$('#divModelChange').dialog('close');
		$('#divPePwIns').dialog('open');
	}else{
	}
}
//번호변경,새폰등록경우 PE PW설정으로 데이터를 넘기는 경우 
function npiCheckSMS(success) {
	if(success){
		$('#divNewPhoneIns').dialog('close');
		$('#divPePwIns').dialog('open');
	}else{
	}
}

function setPresent(memberDeviceId) {
	$.ajax({
		type: "GET",
		cache: false,
		url: $('#rootPath').val()+"/mypage/ajaxMemberDevicePresent.do",
		data: {memberDeviceId:memberDeviceId},
		dataType: "json",
		success: function(json) {
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message, "pncResult(" + json.success + ")");
		}
	});
}

function deleteMdn(memberDeviceId) {
	$.ajax({
		type: "GET",
		cache: false,
		url: $('#rootPath').val()+"/mypage/ajaxMemberDeviceDelete.do",
		data: {mdn:memberDeviceId},
		dataType: "json",
		success: function(json) {
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message, "pncResult(" + json.success + ")");
		}
	});
}

function goList(){
	var f = document.frm;
	f.action = $('#rootPath').val()+"/mypage/memInfoMdy.do?upperMenuId=400000&menuId=402000";
	f.method = "post";
	f.submit();
}

function goAndroidCoreDownLoad(appDiv){
	var vMdn = $('#mdn').val();
	var empNo = $('#empNo').val();
	if(appDiv == 'z001' || appDiv == 'z002' || appDiv == 'z003' || appDiv == 'z004' || appDiv == 'z005'){
		document.location.href = $('#rootPath').val()+"/common/appCoreFileDownload.do?core="+appDiv+"&empNo="+empNo+"&mdn="+vMdn;
	} else {
		document.location.href = $('#rootPath').val()+"/app/appFileDownload.do?appId="+appDiv+"&empNo="+empNo+"&mdn="+vMdn;
	}
}

function goWMCoreDownLoad (appDiv){
	var vMdn = $('#mdn').val();
	var empNo = $('#empNo').val();
	if(appDiv == "core"){
		document.location.href = $('#rootPath').val()+"/common/appCoreFileDownload.do?core=core&empNo="+empNo+"&mdn="+vMdn;
	} else if(appDiv == "player"){
		document.location.href = $('#rootPath').val()+"/common/appCoreFileDownload.do?core=player&empNo="+empNo+"&mdn="+vMdn;
	}
	$('#popCoreInstall').dialog('close');
}
