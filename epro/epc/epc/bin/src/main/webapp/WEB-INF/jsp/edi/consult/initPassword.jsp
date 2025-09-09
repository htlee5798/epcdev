<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>비밀번호 초기화!!</title>
		<meta http-equiv="Content-Type" content="text/html;charset=EUC-KR">
		<link href="<c:url value='/css/epc/edi/consult/style.css'/>" type="text/css" rel="stylesheet">	
		<link rel="stylesheet" href="/css/epc/edi/consult/base.css" type="text/css">
<link rel="stylesheet" href="/css/epc/edi/consult/popup.css" type="text/css">
		<SCRIPT LANGUAGE="JavaScript">
		function check_input()
		{
    		if (document.NewPwd.new_pwd.value == '')
			{		alert("새 비밀번호를 입력하세요!");
					document.NewPwd.new_pwd.focus();
					return false;
			}
			
			if (document.NewPwd.re_new_pwd.value == '')
			{		alert("새 비밀번호 확인을 입력하세요!");
					document.NewPwd.re_new_pwd.focus();
					return false;
			}
			if(document.NewPwd.new_pwd.value != document.NewPwd.re_new_pwd.value)
			{
				alert('두 값이 일치 하지 않습니다!!');
				document.NewPwd.new_pwd.value = '';
				document.NewPwd.re_new_pwd.value = '';
				document.NewPwd.new_pwd.focus();
				return false;					
			}
				
			return true;		
		}
		
		function submitForm()
		{
			if (check_input())
			{					
				document.NewPwd.password.value = document.NewPwd.new_pwd.value; 	
				document.NewPwd.submit();
			}
			return false;
		}
		</SCRIPT>
	</head>
	<body BGCOLOR="#ffffff" topmargin="0" leftmargin="0" onload="jacascript:document.NewPwd.new_pwd.focus();">
		<form Name="NewPwd" METHOD="POST" ACTION="initPassword.do">
<input type="hidden" name="businessNo" value="${param.businessNo }" />
<input type="hidden" name="password" value="" />
			
			
			
			
		
		
		

<div id="pop_wrap" class="pop_w_500">

	<div id="pop_head">
		<h1><img src="/images/epc/edi/consult/h1-password-search.gif" alt="비밀번호 변경"></h1>
	</div>

	<div id="pop_contents">
		<p class="t_default lh18">새로운 비밀번호를  입력하세요.</p>
		<div class="box_gray_lt box_w_440 mt15"><div class="box_gray_rb"><div class="box_gray_rt"><div class="box_gray_lb">
			<fieldset>
				<legend>비밀번호  등록</legend>
				<dl class="input_info">
					<dt>비밀번호</dt>
					<dd>
						<span class="input_txt"><span><input type="password" name="new_pwd"  class="txt" value="" style="width:150px;" maxlength="20"  ID="Text2"></span></span>
					</dd>
					<dt>비밀번호 확인</dt>
					<dd class="end">
						<span class="input_txt"><span><input type="password" name="re_new_pwd" class="txt" value="" style="width:150px;" maxlength="20"  ID="Text2"></span></span>
					</dd>
				</dl>
			</fieldset>
		</div></div></div></div>

		<!-- button -->
		<div class="btn_c_wrap mt30">
			<span class="btn_red btn_r_s"><span><a href="#" onclick="return submitForm();">확인</a></span></span>
		</div>
		<!-- //button -->

	</div>

</div>

		
		
						
			</form>	
		
	</body>
</html>
