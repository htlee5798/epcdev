<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title><spring:message code='text.consult.field.resetPwd'/></title>
		<meta http-equiv="Content-Type" content="text/html;charset=EUC-KR">
		<link href="/css/epc/edi/consult/style.css'" type="text/css" rel="stylesheet">	
		<link rel="stylesheet" href="/css/epc/edi/consult/base.css" type="text/css">	
		<link rel="stylesheet" href="/css/epc/edi/consult/popup.css" type="text/css">
		<script src="/js/jquery/jquery-1.6.2.min.js" type="text/javascript"></script>
		<SCRIPT LANGUAGE="JavaScript">
		function check_input()
		{
			if ($("#pwForm #newPwd").val() == "")
			{	
				alert("<spring:message code='msg.consult.require.newPwd'/>");
				$("#pwForm #newPwd").focus();
				return false;
			}
			
			if ($("#pwForm #reNewPwd").val() == "")
			{	
				alert("<spring:message code='msg.consult.require.reNewPwd'/>");
				$("#pwForm #reNewPwd").focus();
				return false;
			}
			
			if($("#pwForm #newPwd").val() != $("#pwForm #reNewPwd").val())
			{
				alert("<spring:message code='msg.consult.require.newPwdNeReNewPwd'/>");
				$("#pwForm #newPwd").val("");
				$("#pwForm #reNewPwd").val("");
				$("#pwForm #newPwd").focus();
				return false;					
			}
				
			return true;		
		}
		
		function submitForm()
		{
			if (check_input())
			{					
				$("#pwForm #password").val($("#pwForm #newPwd").val());	
				$("#pwForm").submit();
			}
			return false;
		}
		</SCRIPT>
	</head>
	<body BGCOLOR="#ffffff" topmargin="0" leftmargin="0" onload="jacascript:document.pwForm.newPwd.focus();">
	
	<form name="pwForm" id="pwForm" method="POST" action="<c:url value='/epc/edi/consult/NEDMCST0310initPassword.do'/>">
		<input type="hidden" name="businessNo" id="businessNo" value="<c:out value='${param.businessNo }'/>" />
		<input type="hidden" name="password"   id="password"   value="" />
			
			
			
			
		
		
		

	<div id="pop_wrap" class="pop_w_500">
	
		<div id="pop_head">
			<h1><img src="/images/epc/edi/consult/h1-password-search.gif" alt="비밀번호 변경"></h1>
		</div>
	
		<div id="pop_contents">
			<p class="t_default lh18"><spring:message code='text.consult.field.inputNewPwd'/></p>
			<div class="box_gray_lt box_w_440 mt15"><div class="box_gray_rb"><div class="box_gray_rt"><div class="box_gray_lb">
				<fieldset>
					<legend><spring:message code='text.consult.field.inputPwd'/></legend>
					<dl class="input_info">
						<dt><spring:message code='text.consult.field.newPwd'/></dt>
						<dd>
							<span class="input_txt"><span><input type="password" name="newPwd" id="newPwd"  class="txt" value="" style="width:150px;" maxlength="20" ></span></span>
						</dd>
						<dt><spring:message code='text.consult.field.reNewPwd'/></dt>
						<dd class="end">
							<span class="input_txt"><span><input type="password" name="reNewPwd" id="reNewPwd" class="txt" value="" style="width:150px;" maxlength="20" ></span></span>
						</dd>
					</dl>
				</fieldset>
			</div></div></div></div>
	
			<!-- button -->
			<div class="btn_c_wrap mt30">
				<span class="btn_red btn_r_s"><span><a href="#" onclick="return submitForm();"><spring:message code='button.common.confirm'/></a></span></span>
			</div>
			<!-- //button -->
	
		</div>
	
	</div>

		
		
						
	</form>	
		
	</body>
</html>
