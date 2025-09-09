<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.staticssl.path')}/css/epc/epcLogin.css">
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.jsonp-2.4.0.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/common/json2.js"></script>
<script type="text/javascript" src="/js/common/cip_aes.js"></script>
<script type="text/javascript" src="/js/common/cip_io.js"></script>
<script type="text/javascript" src="/js/common/cip_map.js"></script>
<script type="text/javascript" src="/js/common/cip.js"></script>
<title>관리자 로그인</title>
<script language="javascript">
$(document).ready(function($){
	init();
});

<!--
function checkKey(seq)
{
	var form = document.form1;
	
	ieKey = window.event.keyCode;
	
	if ( ieKey != 13 )
	{
		return;
	}
	if ( seq == 1 )
	{
		
		if ( ieKey == 9 ) {
			form.password.select();
			form.password.focus();
			retrun;
		}

		if (form.loginId.value =="" ||form.loginId.value ==null )
		{
			alert("회원ID를 확인하세요.");
			form.loginId.select();
			form.loginId.focus();
			return;
		}

		form.password.select();
		form.password.focus();
		return;
	}
	if ( seq == 2 )
	{
		if ( ieKey == 9 ) {
			form.cono.select();
			form.cono.focus();
			retrun;
		}		
		
		if ( form.password.value =="" ||form.password.value ==null )
		{
			alert("비밀번호를 확인하세요.");
			form.password.select();
			form.password.focus();
			return;
		}

        form.cono.select();
        form.cono.focus();
        return;
	}

	if ( seq == 3 )
	{
		if ( form.cono.value =="" ||form.cono.value ==null )
		{
			alert("사업자번호를 확인하세요.");
			form.cono.select();
			form.cono.focus();
			return;
		}
		login();

	}
	
	if ( seq == 4 )
	{
		if ( form.otpNo.value =="" ||form.otpNo.value ==null )
		{
			alert("OTP번호를 확인하세요.");
			form.otpNo.select();
			form.otpNo.focus();
			return;
		}
		login();

	}
}

// onClick시 id or password 지우기(class 삭제)
function fRemoveClass(val){
	if( val == 1 ){
		$("input[name=loginId]",$('#form1')).attr('class','' );
	}else if( val == 2 ){
		$("input[name=password]",$('#form1')).attr('class','' );
	}else {
		$("input[name=cono]",$('#form1')).attr('class','' );
	}
		
	
}
function login()
{
	var form = document.form1;
	var loginFlag = $("input[id='loginFlagVal']:checked").val();
	
	if(form.loginId.value==""){
		alert("관리자 ID를 입력하세요");
		form.loginId.focus();
		return;
	} 

	if(loginFlag == "1" && form.password.value==""){
		alert("Password를 입력하세요");
		form.password.focus();
		return;
	}
	
	if(form.cono.value==""){
		alert("사업자번호를 입력하세요");
		form.cono.focus();
		return;
	}	
	
	$("#loginFlag").val(loginFlag);
	
	var surl = "${lfn:getString('login.sdomain.url')}";
	
	if(loginFlag == "1"){
		surl += '/common/epcAdmLogin.do';	
	}else{
		surl += '/common/epcAdmLoginOtp.do';
	}
	form.action=surl;
	//alert(surl);
	form.submit();
}

function init()
{
	radioChk($("input[id='loginFlagVal']"));
	
	$("input[id='loginFlagVal']").click(function(){
		$("#password").val("");
		$("#otpNo").val("");
		
		radioChk($(this));
	});
}

function radioChk(arg){
	var chk = arg.is(":checked");
	
	if(chk){
		var val = arg.val();
		
		if(val == "1"){
			$("#dtTitle").text("비밀번호");
			$("#spPw").show();
			$("#spOtp").hide();
			$("#spCip").hide();
		}else if(val == "2"){
			$("#dtTitle").text("OTP");
			$("#spPw").hide();
			$("#spOtp").show();
			$("#spCip").hide();
		}else if(val == "3"){
			$("#dtTitle").text("공인인증서");
			$("#spPw").hide();
			$("#spOtp").hide();
			$("#spCip").show();
		}
		
		$("input[id='loginFlagVal'][value='"+val+"']").attr("checked",true);
	}
}

//협력사조회 Popup
function popupVendorList()
{
	var form = document.form1;
	var loginFlag = $("input[id='loginFlagVal']:checked").val();
	
	if(form.loginId.value==""){
		alert("관리자 ID를 입력하세요");
		form.loginId.focus();
		return;
	} 

	if(loginFlag == "1" && form.password.value==""){
		alert("Password를 입력하세요");
		form.password.focus();
		return;
	}

	var targetUrl = '<c:url value="/common/selectPartnerFirmsPopupSSL.do"/>'+'?gubn=CONO&loginId='+form.loginId.value+'&pwd='+form.password.value+"&loginFlag="+loginFlag;
	Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 550});
	
	/* var surl = "${lfn:getString('login.sdomain.url')}";
	surl += '/common/selectPartnerFirmsPopupSSL.do'+'?gubn=CONO&loginId='+form.loginId.value+'&pwd='+form.password.value;	
	alert(surl);
	Common.centerPopupWindow(surl, 'popup', {width : 800, height : 550}); */
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#cono").val(strCono);
	$("#repVendorId").val(strVendorId);
}

function SelectCertificateLogin()
{
	var form = document.form1;
	
	if(form.loginId.value==""){
		alert("관리자 ID를 입력하세요");
		form.loginId.focus();
		return;
	} 
	
	if(form.cono.value==""){
		alert("사업자번호를 입력하세요");
		form.cono.focus();
		return;
	}
	
	var userId = form.loginId.value;

	cipToolkit.setParameters("userId", userId);
	cipToolkit.setParameters("action", "CertLogin");
	cipToolkit.setCallback(myCallbackFunc);
	cipToolkit.SelectCertificate();
	
}

function myCallbackFunc(OutDataNum, OutData)
{
	if(OutDataNum == "0"){
		$("#cipYn").val("Y");
		login();
	}else{
		$("#cipYn").val("N");
	}
}
</script>
</head>

<body>
<form id="form1" name="form1" method="POST">
<input type="hidden" name="sysDivnCd" value="01"/>
<input type="hidden" name="repVendorId"  id="repVendorId" value=""/>
<input type="hidden" name="loginFlag"  id="loginFlag"/>
<input type="hidden" name="cipYn"  id="cipYn" value="N"/>
	<div id="wrap_login2">
		<div class="login_top">
			<h1><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/txt_join_admin_system.gif" alt="협력사 관리시스템" /></h1>
			<span><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/logo_login.gif" alt="행복드림 LOTTE MART" /></span>
		</div>
		<dl>
			<dt>
				<input type="radio" name="loginFlagVal" id="loginFlagVal" value="1" checked="checked">일반
				<input type="radio" name="loginFlagVal" id="loginFlagVal" value="2">OTP
				<input type="radio" name="loginFlagVal" id="loginFlagVal" value="3">공인인증
			</dt>
		</dl>
		<dl class="login_cnt">
			<dt>아이디</dt>
			<dd class="mr20"><input type="text" class="txt_id" style="width:157px;" onClick="fRemoveClass(1);"  OnKeyPress="javascript:checkKey(1);" name="loginId" /></dd>
			<dt id="dtTitle">비밀번호</dt>
			<dd>
				<span id="spPw">
					<input type="password" class="txt_pwd" style="width:157px;" onClick="fRemoveClass(2);" OnKeyPress="javascript:checkKey(2);" name="password" />
				</span>
				<span id="spOtp"  style="display:none;">
					<input type="text" id="otpNo" class="business_nm" style="width:157px;"  onClick="fRemoveClass(4);" OnKeyPress="javascript:checkKey(4);" name="otpNo"/>
				</span>
				<span id="spCip" style="display:none;">
					<img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/btn_inquiry.gif" onClick="SelectCertificateLogin();" alt="조회">
				</span>
			</dd>	
            <dt class="mt19">사업자번호</dt>
            <dd class="mt10">
            	<input type="text" id="cono" class="business_nm" style="width:157px;" readonly onClick="fRemoveClass(3);" OnKeyPress="javascript:checkKey(3);" name="cono"/>
	            <a href="#"><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/btn_inquiry.gif" onClick="popupVendorList();" alt="조회"></a>
            </dd>
		</dl>
	 	<div class="btn_login"><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/btn_login.gif" alt="Login" onClick="login();"/></div>
	 
	</div>
</form>
</body>
</html>