<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/css/epc/epcLogin.css">
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<title>관리자 로그인</title>
<script language="javascript">
$(function() {

	if (document.location.protocol == 'http:') {
		document.location.href = document.location.href.replace('http:', 'https:');
		return;
	}
	
	var status = '<c:out value="${status}" />';

	if (status != "0") {
		alert("비정상적인 접근입니다.");
		self.close();
	} else {
		popupVendorList();
	}
	//popupVendorList();
});

function checkKey(seq) {

	var form = document.form1;

	ieKey = window.event.keyCode;
	if ( ieKey != 13 ) {
		return;
	}

	if ( seq == 1 ) {

		if ( ieKey == 9 ) {
			form.password.select();
			form.password.focus();
			retrun;
		}

		if (form.loginId.value == "" ||form.loginId.value == null ) {
			alert("회원ID를 확인하세요.");
			form.loginId.select();
			form.loginId.focus();
			return;
		}

		form.password.select();
		form.password.focus();
		return;
	}

	if ( seq == 3 ) {
		if ( form.cono.value == "" ||form.cono.value == null ) {
			alert("사업자번호를 확인하세요.");
			form.cono.select();
			form.cono.focus();
			return;
		}
		login();
	}

}

// onClick시 id or password 지우기(class 삭제)
function fRemoveClass(val) {
	if( val == 1 ) {
		$("input[name=loginId]",$('#form1')).attr('class','' );
	} else {
		$("input[name=cono]",$('#form1')).attr('class','' );
	}
}

function login() {
	var form = document.form1;

	if(form.loginId.value == "") {
		alert("관리자 ID를 입력하세요");
		form.loginId.focus();
		return;
	}

	if(form.cono.value == "") {
		alert("사업자번호를 입력하세요");
		form.cono.focus();
		return;
	}

	var surl = "${lfn:getString('login.sdomain.url')}";
	surl += '/common/epcSSOAdmLogin.do';	
	form.action=surl;
	form.submit();
}

function init() {
	//document.form1.loginId.focus();
}

//협력사조회 Popup
function popupVendorList() {
	var form = document.form1;

	if(form.loginId.value == "") {
		alert("관리자 ID를 입력하세요");
		form.loginId.focus();
		return;
	}

	var targetUrl = '<c:url value="/common/selectSSOPartnerFirmsPopupSSL.do"/>'+'?gubn=CONO&loginId='+form.loginId.value;
	Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 550});
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#cono").val(strCono);
	$("#repVendorId").val(strVendorId);
	login();
}

</script>
</head>
<body>
	<form id="form1" name="form1" method="POST">
	<input type="hidden" name="sysDivnCd" value="01" />
	<input type="hidden" name="repVendorId"  id="repVendorId" value="" />
		<div id="wrap_login2">
			<div class="login_top">
				<h1><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/txt_join_admin_system.gif" alt="협력사 관리시스템" /></h1>
				<span><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/logo_login.gif" alt="행복드림 LOTTE MART" /></span>
			</div>
			<dl class="login_cnt">
				<dt>아이디</dt>
				<dd style="margin-right:250px;"><input type="text" class="txt_id" style="width:157px;" onClick="fRemoveClass(1);" OnKeyPress="javascript:checkKey(1);" name="loginId" value="<c:out value='${loginId}' />" readonly="readonly" /></dd>				
				<dt class="mt19">사업자번호</dt>
				<dd class="mt10"><input type="text" id="cono" class="business_nm" style="width:157px;" onClick="fRemoveClass(3);" OnKeyPress="javascript:checkKey(3);" name="cono" readonly="readonly" />
					<a href="#"><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/btn_inquiry.gif" onClick="popupVendorList();" alt="조회"></a>
				</dd>
			</dl>
			<div class="btn_login"><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/btn_login.gif" alt="Login" onClick="login();"/></div>
		</div>
	</form>
</body>
</html>