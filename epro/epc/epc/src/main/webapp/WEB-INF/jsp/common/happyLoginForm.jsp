<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.staticssl.path')}/css/login.css">
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<title>관리자 로그인</title>
<script language="javascript">
<!--


var fm;

function getCookieVal(offset)
{
  var endstr = document.cookie.indexOf (";", offset);
  if (endstr == -1) endstr = document.cookie.length;
  return unescape(document.cookie.substring(offset, endstr));
}

function GetCookie(name)
{
  var arg = name + "=";
  var alen = arg.length;
  var clen = document.cookie.length;
  var i = 0;
  while (i < clen) //while open
  {
      var j = i + alen;
      if (document.cookie.substring(i, j) == arg)
          return getCookieVal (j);
      i = document.cookie.indexOf(" ", i) + 1;
      if (i == 0) break;
  } //while close
  return null;
}

function SetCookie(name, value)
{

  var todayDate = new Date();
  // 쿠키가 저장될 기간을 설정 하루면 1을 입력.
  var expiredays = 365;

  todayDate.setDate( todayDate.getDate() + expiredays );
  var argv = SetCookie.arguments;
  var argc = SetCookie.arguments.length;
  var expires = (2 < argc) ? argv[2] : null;
  var path = (3 < argc) ? argv[3] : null;
  var domain = (4 < argc) ? argv[4] : null;
  var secure = (5 < argc) ? argv[5] : false;
  document.cookie = name + "=" + escape (value) +
      ((expires == null) ? "" :
      ("; expires=" + todayDate.toUTCString())) +
      ((path == null) ? "" : ("; path=" + path)) +
      ((domain == null) ? "" : ("; domain=" + domain)) +
      ((secure == true) ? "; secure" : "");
}




function fncSaveCookie()
{
  // 체크가 되어있으면
  if(fm.user_chk.checked)
  {
      // 값들을 쿠키에 저장
      SetCookie('user_chk',true);
      SetCookie('loginId',fm.loginId.value);
      SetCookie('password',fm.password.value)
  }
  else
  {
      // 값들을 쿠키에 제거
      SetCookie('user_chk',false);
      SetCookie('loginId','');
      SetCookie('password','');
  }
}

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
		if ( form.password.value =="" ||form.password.value ==null )
		{
			alert("비밀번호를 확인하세요.");
			form.password.select();
			form.password.focus();
			return;
		}

		login();
	}
}

// onClick시 id or password 지우기(class 삭제)
function fRemoveClass(val){
	if( val == 1 ){
		$("input[name=loginId]",$('#form1')).attr('class','' );
	}else{
		$("input[name=password]",$('#form1')).attr('class','' );
	}

}
function login()
{
	var form = document.form1;

	if(form.loginId.value==""){
		alert("회원ID를 입력하세요");
		form.loginId.focus();
		return;
	}

	if(form.password.value==""){
		alert("Password를 입력하세요");
		form.password.focus();
		return;
	}


	//로그인 아이디/비번 쿠키 저장.
	fncSaveCookie();

	
	var surl = "${lfn:getString('login.sdomain.url')}";
	surl += '/common/happyLogin.do';	
	form.action=surl;
	form.submit();
}

//화면이 로딩되는 시점
function init()
{
	//document.form1.loginId.focus();
  fm  = document.form1;
  // 체크여부 쿠키값 가져옴
  var user_chk = GetCookie('user_chk');
  // 아이디 쿠키값 가져옴
  var id  = GetCookie('loginId');
  // 비밀번호 쿠키값 가져옴
  var pwd = GetCookie('password');

  // 쿠키에 체크여부가 체크 되었을 경우
  if(user_chk=='true')
  {
      // 저장 되있던 쿠키값들을 적용
      fm.user_chk.checked             = user_chk;
      if(id!=null) fm.loginId.value   = id;
      if(pwd!=null) fm.password.value = pwd;
  }
}



//-->

</script>
</head>
<body onLoad="init();">
<form id="form1" name="form1" method="POST">
<input type="hidden" name="sysDivnCd" value="03"/>
	<div id="wrap_login">
		<div class="login_top">
			<h1><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/txt_login_bos.gif" alt="롯데마트 관리자시스템" /></h1>
			<span><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/logo_login.gif" alt="행복드림 LOTTE MART" /></span>
		</div>

		<dl class="login_cnt">
			<dt>아이디</dt>
			<dd class="mr20"><input type="text" class="txt_id" style="width:157px;" onClick="fRemoveClass(1);"  OnKeyPress="javascript:checkKey(1);" name="loginId" value="" maxlength="21" /></dd>
			<dt>비밀번호</dt>
			<dd><input type="password" class="txt_pwd" style="width:157px;" onClick="fRemoveClass(2);" OnKeyPress="javascript:checkKey(2);" name="password" value="" maxlength="20" /></dd>
		</dl>
		<div class="id_txt"><input type="checkbox" id="user_chk" name="user_chk" value="" onclick=''> 아이디 저장</div>
		<div class="btn_login"><img src="${lfn:getString('system.cdn.staticssl.path')}/images/login/btn_login.gif" alt="Login" onClick="login();"/></div>
	</div>
</form>
</body>
</html>