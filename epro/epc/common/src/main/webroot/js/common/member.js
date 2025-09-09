/**
 * 개발자POC 회원에서 쓰는 JS
 */
var env = {
	contextPath : "",
	serviceName : "",
	enable : ""
};

function setContextPath(paramContextPath){
	env.contextPath = paramContextPath;
}
function setServiceName(serviceName) {
	env.serviceName = serviceName;
}
function setEnable(yn) {
	env.enable = yn;
}
/**
 * 결제창 띄우기
 * @return
 */
function popPayment(mbrNo, mbrNm){
	
	var eMbrNm = fncEncode(mbrNm);
	var eGoodNm = fncEncode("연회비");
	
	
	var param = "?mbrNo=" + mbrNo;
	param += "&price=100000";
	param += "&userNm=" + eMbrNm;
	param += "&goodNm=" + eGoodNm;
	
	var url = env.contextPath + "/member/popPayment.omp"+param;
	
	var props = {
			childWidth : 600,
			childHeight : 500,
			scrollBars : "NO"
		};
	centerPopupWindow("", "popPayment", props);
	
	var frm = document.paymentForm;
	frm.target = "popPayment";
	frm.action = url;
	frm.submit();
}


function fncEncode(param)
{
	var encode = '';

	for(i=0; i<param.length; i++)
	{
		var len  = ''+param.charCodeAt(i);
		var token = '' + len.length;
		encode  += token + param.charCodeAt(i);
	}

	return encode;
}

/**
 * ID중복체크
 * @param mbrIdVal
 * @param pwd1Val
 * @return
 */
function popDuplicateIdCheck(mbrIdVal, pwd1Val, ScreenHeight){
//function popDuplicateIdCheck(mbrIdVal, exposureNmVal, ScreenHeight){
	if (!requiredFiledById("user_id", "아이디를")) return;
	var params = {
			mbrIdVal : isNull(mbrIdVal) ? 'mbrIdVal' : mbrIdVal,
			pwd1Val : isNull(pwd1Val) ? 'pwd1Val' : pwd1Val,
			mbrId : $("#user_id").val()
		};
	var props = {
		type : "POST",
		url : env.contextPath + "/member/popDuplicateIdCheck.omp",
		param : params,
		layerId : "popDuplicateIdCheck",
		parentLayerId : "container"
	};
	if (ScreenHeight != undefined && ScreenHeight != "") {
		popLayerAjaxCall(props, ScreenHeight);
	} else {
		popLayerAjaxCall(props);
	}
}

/**
 * 닉네임 중복체크
 * @param exposureNm
 * @return
 */
function popDuplicateNmCheck(exposureNmVal,pwd1Val, ScreenHeight){
	if (!requiredFiledById("exposureNm", "닉네임을")) return;
	var exNm = document.getElementById(exposureNmVal).value;
	if(exNm.getByteLength() > 16 || exNm.getByteLength() < 3){
		alert("한글 3~8자의 한글 또는 3~13자의 영문 소문자만 입력이됩니다 ");
		exposureNmVal.value = "";
		return;
	}
	var params = {
			exposureNmVal : isNull(exposureNmVal) ? 'exposureNmVal' : exposureNmVal,
			pwd1Val : isNull(pwd1Val) ? 'pwd1Val' : pwd1Val,
			exposureNm : $("#exposureNm").val()
		};
	
	var props = {
		type : "POST",
		url : env.contextPath + "/member/popDuplicateNmCheck.omp",
		param : params,
		layerId : "popDuplicateNmCheck",
		parentLayerId : "container"
	};
	if (ScreenHeight != undefined && ScreenHeight != "") {
		popLayerAjaxCall(props, ScreenHeight);
	} else {
		popLayerAjaxCall(props);
	}
}
/**
 * 닉네임 중복체크 회원정보 수정시에
 * @param exposureNm
 * @return
 */
function popDuplicateNmCheckRe(exposureNmVal, ScreenHeight){
	if (!requiredFiledById("exposureNm", "닉네임을")) return;
	var exNm = document.getElementById(exposureNmVal).value;
	if(exNm.getByteLength() > 16 || exNm.getByteLength() < 3 ){
		alert("한글 3~8자의 한글 또는 3~13자의 영문 소문자만 입력이됩니다 ");
		exposureNmVal.value = "";
		return;
	}
	var params = {
			exposureNmVal : isNull(exposureNmVal) ? 'exposureNmVal' : exposureNmVal,
			exposureNm : $("#exposureNm").val()
		};
	var props = {
		type : "POST",
		url : env.contextPath + "/member/popDuplicateNmCheckRe.omp",
		param : params,
		layerId : "popDuplicateNmCheckRe",
		parentLayerId : "container"
	};
	if (ScreenHeight != undefined && ScreenHeight != "") {
		popLayerAjaxCall(props, ScreenHeight);
	} else {
		popLayerAjaxCall(props);
	}
}


/**
 * e-mail 중복체크
 * @param email1IdVal
 * @param email2IdVal
 * @return
 */
function popDuplicateEmailCheck(email1IdVal, email2IdVal, ScreenHeight){
	if (!requiredFiledById("email1", "이메일을")) return;
	if (!requiredFiledById("email2", "이메일을")) return;
	var params = {
			email1IdVal : isNull(email1IdVal) ? '' : email1IdVal,
			email2IdVal : isNull(email2IdVal) ? '' : email2IdVal,
			email : $("#emailAddr").val()
		};
	var props = {
		type : "POST",
		url : env.contextPath + "/member/popDuplicateEmailCheck.omp",
		param : params,
		layerId : "popDuplicateEmailCheck",
		parentLayerId : "container"
	};
	if (ScreenHeight != undefined && ScreenHeight != "") {
		popLayerAjaxCall(props, ScreenHeight);
	} else {
		popLayerAjaxCall(props);
	}
}

/**
 * 실명인증
 * @return
 */
function popAuthRealNameCheck(ScreenHeight){
	var params = {
			userNameIdVal : "user_name",
			realNmAuthYnIdVal : "is_rname_auth",
			norealNmDiv : "norealNm",
			realNmDiv : "realNm"
		};
	var props = {
		type : "POST",
		url : env.contextPath + "/member/popAuthRealNameInput.omp",
		param : params,
		layerId : "popAuthRealNameCheck",
		parentLayerId : "container"
	};
	if (ScreenHeight != undefined && ScreenHeight != "") {
		popLayerAjaxCall(props, ScreenHeight);
	} else {
		popLayerAjaxCall(props);
	}
}

/**
 * 실명인증 완료 후 세팅
 * @param success
 * @param name
 * @param ssn
 * @return
 */
function setRealNamee(success, name, ssn, sakey) {
	// IDP에서 주민번호가 *로 오므로, 실명인증 완료 시에만 실명인증정보 필드 생성하도록 수정. 20100222 soohee
	$("#registMemberForm").append('<input type="hidden" id="user_name" name="user_name" value="'+name+'" />');
	$("#registMemberForm").append('<input type="hidden" id="user_social_number" name="user_social_number" value="'+ssn+'" />');
	$("#registMemberForm").append('<input type="hidden" id="sn_auth_key" name="sn_auth_key" value="'+sakey+'" />');
	
	if ($("#is_rname_auth").val() == null || $("#is_rname_auth") == "undefinde") {
		$("#registMemberForm").append('<input type="hidden" id="is_rname_auth" name="is_rname_auth" value="Y" />');
	} else {
		$("#is_rname_auth").val("Y");
	}
	
	if (success == 'true') {
		$("#user_name2").append(name);
		$("#socialDate").val(ssn.substring(0,7));
		$("#user_social_number2").append(ssn.substring(0,6));
		$("#norealNm").hide();
		if($("#norealNm1") != null)$("#norealNm1").hide();  //2차 고도화 추가
		$("#realNm").show();
		if($("#realNm1") != null){
			$("#realNm1").show();      //2차 고도화 추가
		
			var sNum = ssn.substring(6,7);
			var birth1;
			if('1' == sNum)birth1 = '19' + ssn.substring(0,2);
			if('3' == sNum)birth1 = '20' + ssn.substring(0,2);
			var birth2 = ssn.substring(2,4);
            var birth3 = ssn.substring(4,6);
			$("#birth1 > option[value="+birth1+"]").attr("selected", true);
			$("#birth2 > option[value="+birth2+"]").attr("selected", true);
			$("#birth3 > option[value="+birth3+"]").attr("selected", true);
		}
	}
	
//	var param = {
//			mbrNm : name,
//			ssn : ssn
//		};
//	$.postJSON(
//			env.contextPath + "/member/ajaxMakeSnAuthKey.omp",
//			param,
//			function(json){
//				if (json.resultCode == 1){
//					$("#sn_auth_key").val(json.snAuthKey);
//				} else {
//					alert(json.resultMessage);
//					return;
//				}
//			}
//		);
	
	closePopupLayer("popAuthRealNameCheck");
}

function idCheckAgain() {
	$("#duplicateIdCheck").val("N");
	$("#nateIdUseYn").val("N");
}

function exposureCheckAgain() {
	$("#duplicateExposureCheck").val("N");
}

function emailCheckAgain() {
	$("#duplicateEmailCheck").val("N");
}
function acctCheckAgain() {
	$("#authYn").val("N");
}
function authCheckAgain() {
	$("#smsAuthYn").val("N");
}

// 계좌번호 인증
function authAccount(frm, imgObj) {
	if (frm.acctNm.value == "") {
		alert("예금주를 입력해 주세요.");
		frm.acctNm.focus();
		return;
	}
	if (frm.acctNo.value == "") {
		alert("계좌번호를 입력해 주세요.");
		frm.acctNo.focus();
		return;
	}
	$.postJSON(
			env.contextPath + "/member/popAccount.omp",
		{ compNm: $("#acctNm").val()
		  , bankCd: $("#bank_select").val()
		  , acctNo: $("#acctNo").val() },
		function(data) {
			if ((data.resultCode == 0 || data.accountAuthKey == null) && data.errorMsg != null) {
				alert("계좌정보가 일치하지 않습니다. \n\n다시 시도해 주세요.");
				return;
			} else {
				alert("계좌번호 인증 되었습니다.");
				$("#authYn").val("Y");
				$("#accountAuthKey").val(data.accountAuthKey);
				imgObj.style.visibility = "hidden";
			}
		}
	);
}

/**
 * 핸드폰 추가 레이어
 * @param cellPhoneCount
 * @return
 */
function popCellphoneCertify(ScreenHeight){
	var dls = $("#cellPhoneArea dl");
	if (dls.length >= 5 || $("#mobileCnt").val() >= 5) {
		alert("휴대폰은 최대 5개까지 등록할 수 있습니다.");
		return;
	}
	
	var props = {
		type : "POST",
		url : env.contextPath + "/member/popCellphoneCertify.omp",
		layerId : "popCellphoneCertify",
		parentLayerId : "container"
	};
	if (ScreenHeight != undefined && ScreenHeight != "") {
		popLayerAjaxCall(props, ScreenHeight);
	} else {
		popLayerAjaxCall(props);
	}
}

/**
 * 핸드폰 삭제
 * @param index 삭제할 핸드폰 index
 * @return
 */
function delCellPhone(index){
	var mCnt = 0;
	$("#cellPhoneArea dl").each(function () {
		mCnt++;
	});
	
	if (mCnt !=0 && mCnt > 1 && $("#repHpYn"+index).attr("checked")) {
		alert ("대표폰은 삭제 할 수 없습니다. \n 다른 폰을 대표폰 설정 후 삭제 가능합니다.");
	} else {
		$("#dl"+index).remove();
	}	
	
	if ($("#cellPhoneArea dl").length < 5) {
		$("#btnPhoneAdd").show();
	}
}

/**
 * 핸드폰 추가
 * @param modelNm 모델명
 * @param mnoCd 통신사코드
 * @param hpNo 휴대폰번호
 * @param hpNm 휴대폰 이름
 * @param dtlImgPos 이미지경로
 * @param svcMngNum 서비스번호
 * @param uaCode UA코드
 * @return
 */
function addCellPhone(modelNm, mnoCd, hpNo, hpNm, dtlImgPos, svcMngNum, uaCode, phoneMetaAuthKey){
	var index = 0; // 휴대폰 노출 순서대로,1~5.
	var repCheck = "checked=checked"; // 대표폰 체크 여부.
	var telecom = ""; // 이통사정보
	var mCnt = 0;
	$("#cellPhoneArea dl").each(function () {
		mCnt++;
        var dlIdNum = $(this).attr("id").substring(2);
        if (index <= dlIdNum)
        	index = dlIdNum;
        });
	index++;
	
	if (mCnt > 0 || index > 1) {
		repCheck = "";
	}
	
	modelNm = replaceAllRemoveSpecial(modelNm);
	mnoCd = replaceAllRemoveSpecial(mnoCd);
	hpNo = replaceAllRemoveSpecial(hpNo);
	hpNm = replaceAllRemoveSpecial(hpNm);
	dtlImgPos = replaceAllRemoveSpecial(dtlImgPos);
	svcMngNum = replaceAllRemoveSpecial(svcMngNum);
	uaCode = replaceAllRemoveSpecial(uaCode);
	
	if (env.enable == "Y") {
		telecom = ',' + mnoCd;
	}
	$("#cellPhoneArea").append(
		'<dl id="dl' + index + '" class="cellphone"><dt><img width="68" height="68" src="/common/phone/' + dtlImgPos + '"/></dt><dd><ul>'
		+ '<li><span class="txt"><strong>모델명</strong> ' + modelNm + '</span>'
		+ '<span class="btn">&nbsp;&nbsp;[' + hpNm + '] &nbsp;&nbsp;'
		+ '<a href="#"><img src="' + env.contextPath + '/images/button/btn_delete1.gif" alt="삭제" onclick="delCellPhone(' + index + '); return false;"/></a></span></li>'
		+ '<li class="taR"><span class="txt"><strong>휴대폰번호</strong> ' + hpNo + '</span>'
		+ '<input type="radio" id="repHpYn' + index + '" name="repHpYn" class="chk" value="' + hpNo + '" ' + repCheck +'/><img src="' + env.contextPath + '/images/member/txt_03.gif" alt="대표기기" /></li>'
		+ '<li class="last"><input type="checkbox" name="smsYn" class="chk" value="' + hpNo + '" checked="checked"/>이 휴대폰 번호로 '+ env.serviceName +'의 이벤트 소식을 SMS로 받기</li> '
		+ '<input type="hidden" name="modelNm" value="' + modelNm + '" />'
		+ '<input type="hidden" name="hpNm" value="' + hpNm + '" />'
		+ '<input type="hidden" name="hpNo" value="' + hpNo + '" />'
		+ '<input type="hidden" name="mnoCd" value="' + mnoCd + '" />'
		+ '<input type="hidden" name="phoneMeta" value="' + hpNo + ',' + svcMngNum + ',' + uaCode + telecom + '" />'
		+ '<input type="hidden" name="svcMngNum" value="'  + svcMngNum + '" />'
		+ '<input type="hidden" name="uaCd" value="'  + uaCode + '" />'
		+ '<input type="hidden" name="phoneMetaAuthKey" value="'  + phoneMetaAuthKey + '" />'
		+ '</ul></dd></dl>');
	
	if ($("#cellPhoneArea dl").length >= 5) {
		$("#btnPhoneAdd").hide();
	}
}

/**
 * 휴대폰 보안키 생성
 * 보안키 생성 후 IDP로 redirect 되기 때문에, 동기 방식으로 서버와 통신하기 위해 frame 을 사용함.
 * 필수 객체 : dummyForm, dummyFrame 
 * @return
 */
function makeSnAuthKey() {
	$("#phoneMeta").val("");
	$("#nextCallFn").val("");
	$("#phoneMetaAuthKey").val("");
	
	var phoneStr = "";
	var phoneKeyStr = "";
	$("#mobileCnt").val($("#cellPhoneArea dl").length + $(".cellphone").length);
	if ($("#mobileCnt").val() > 0) {
	  	// 핸드폰정보 만들기 
	  	$("input[name=phoneMeta]").each(function () {
	      	phoneStr += "|" + $(this).val();
	          });
	  	
	  	if (phoneStr.lastIndexOf("|") == phoneStr.length-1) {
	  		phoneStr = phoneStr.substring(0, phoneStr.lastIndexOf("|"));
	  	}
	  	$("#user_phone").val(phoneStr.substring(1));
	  	
	  	// 휴대폰 암호화 정보 전달
	  	$("input[name=phoneMetaAuthKey]").each(function () {
	  		phoneKeyStr += "|" + $(this).val();
	  		  });
	  	
	  	if (phoneKeyStr.lastIndexOf("|") == phoneKeyStr.length-1) {
	  		phoneKeyStr = phoneKeyStr.substring(0, phoneKeyStr.lastIndexOf("|"));
	  	}
	  	
  		var dummy = document.dummyForm;
  		$("#phoneMeta").val($("#user_phone").val());
  		$("#phoneMetaAuthKey").val(phoneKeyStr.substring(1));
  		$("#nextCallFn").val("doSubmit");
  		dummy.action = env.contextPath + "/member/makePhoneAuthKey.omp";
  		dummy.target = "dummyFrame";
  		dummy.submit();
    } else {
        doSubmit('');
    }
}

/**
 * 워터마크 이미지 새로받기
 * @param imgId
 * @param imageUrlId
 * @param imageSignId
 * @param signDataId
 * @return
 */
function newAutoImg(imgId, imageUrlId, imageSignId, signDataId) {
	$.postJSON(
		env.contextPath + "/member/ajaxAutoImage.omp",
		"",
		function(json){
			if (json.resultCode == 1) {
				//alert("autoImg:" + json.autoImg + ", imageSign:" + json.imageSign + ", signData:" + json.signData);
				$("#" + imgId).attr("src", json.autoImg);
				$("#" + imageUrlId).val(json.autoImg);
				$("#" + imageSignId).val(json.imageSign);
				$("#" + signDataId).val(json.signData);
			} else {
				alert(json.resultMessage);
			}
		}
	);
}

/**
 * 네이트ID사용
 * @return
 */
function popNateId(){
	var url = env.contextPath + "/member/pupNateId.omp";
	var props = {
			childWidth : 470,
			childHeight : 250,
			scrollBars : "NO"
		};
	centerPopupWindow(url, "popNateId", props);
}