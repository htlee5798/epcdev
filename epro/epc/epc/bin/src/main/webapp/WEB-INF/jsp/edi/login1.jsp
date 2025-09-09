<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>      
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src='/js/jquery/jquery-1.5.2.js' type="text/javascript"></script>
<script src='/js/epc/edi/consult/checkValue.js' type="text/javascript"></script>

<title>롯데마트 입점상담</title>


<script language="JavaScript">
	$(document).ready(function(){
		$("#Password1").keypress(function(event) {
			if(event.keyCode  == 13) {
				confirm_submit();
			}	
		});
		$("#bmanNo3").keypress(function(event) {
			if(event.keyCode  == 13) {
				confirm_submit();
			}	
		});
	});
	
	
	<c:if test="${not empty resultMessage }">
	alert('${resultMessage }');
	</c:if>
	
	
	function confirm_submit()
	{
		
		if($("#MyForm #bmanNo1").val().length == 0)
		{
			strMsg ="<spring:message code='msg.business.number'/>";      
			alert(strMsg);   

			return;
		}
		
		if($("#MyForm #bmanNo1").val().length != 3)
		{
			strMsg ="<spring:message code='msg.business.number.format'/>";
			strMsg ="<spring:message code='msg.consult.require.bmanNo' arguments='1'/>"+"\n"+strMsg 
			alert(strMsg);   

		    return;
		}
		
		
		if($("#MyForm #bmanNo2").val().length == 0)
		{
			strMsg ="<spring:message code='msg.business.number'/>";         
			alert(strMsg);   
			return;
		}
		if($("#MyForm #bmanNo2").val().length != 2)
		{
			strMsg ="<spring:message code='msg.business.number.format'/>";         
			strMsg ="<spring:message code='msg.consult.require.bmanNo' arguments='2'/>"+"\n"+strMsg 
			alert(strMsg);   
			return;
		}
		
		
		if($("#MyForm #bmanNo3").val().length == 0)
		{
			strMsg ="<spring:message code='msg.business.number'/>";         
			alert(strMsg);   
			return;
		}
		if($("#MyForm #bmanNo3").val().length != 5)
		{
			strMsg ="<spring:message code='msg.business.number.format'/>";         
			strMsg ="<spring:message code='msg.consult.require.bmanNo' arguments='3'/>"+"\n"+strMsg 
			alert(strMsg);    
			return;
		}
	   
	
	
		var busienssNo = $("#MyForm #bmanNo1").val()+$("#MyForm #bmanNo2").val()+$("#MyForm #bmanNo3").val();
		$("#MyForm input[name=businessNo]").val(busienssNo);
		$("#MyForm").submit();
		return;
	}
	


	
	function next_BizNo2(Len, BizNo2, a)	
	{
		if (Len==3)
		{
			for(i=0; i<a.value.length; i++)
			{
				if(a.value.charAt(i) < "0" || a.value.charAt(i) > "9")
				{ 
						alert("<spring:message code='msg.number'/>");
						a.focus(); 
						a.value="";
					return; 
				} 
			} 
			BizNo2.focus();
		}
	}

	function next_BizNo3(Len, BizNo3, a)
	{
		if (Len==2)
		{
			for(i=0; i<a.value.length; i++)
			{
				if(a.value.charAt(i) < "0" || a.value.charAt(i) > "9")
				{ 
						alert("<spring:message code='msg.number'/>");
						a.focus(); 
						a.value="";
					return; 
				} 
			} 
				BizNo3.focus();
		}
	}
	
	function next_BizNo4(Len, BizNo4,a)
	{
// 		alert("Len:"+Len);
// 		alert("BizNo4:"+BizNo4);
// 		alert("a:"+a);
		
		if (Len==5)
		{
			for(i=0; i<a.value.length; i++)
			{
				if(a.value.charAt(i) < "0" || a.value.charAt(i) > "9")
				{ 
						alert("<spring:message code='msg.number'/>");
						a.focus(); 
						a.value="";
					return; 
				} 
			} 
		//	BizNo4.focus();
		}
	}


		
		

	</script>

</head>
<body >

<form name="MyForm"  id="MyForm" method="post"  action="<c:url value='/epc/edi/consult/checkVendorBusinessNo.do'/>">
<input type="hidden" name="businessNo" id="businessNo" value=""/>
<input type="hidden" name="authBizNo"  id="authBizNo"  value=""/>
<div id="wrap">
	
	<div id="con_wrap">

		<div class="con_area">
			
			<!-- title -->
			<div class="con_title">
		<!--  		<h1><img src="/images/epc/edi/consult/h1-login.gif" alt="로그인"></h1>
		-->	
		</div>
			<!--// title -->

			<div class="box_gray box_login">
				<p class="txt_btxt"><!-- 고객님의 --> <em>사업자번호를 입력해주세요.<em></p>

				<fieldset>
					<legend>로그인</legend>
					<!-- 로그인 입력 -->
					<div class="member_login">
						<dl class="input_info clearfloat">
							<dt>사업자번호</dt>
							<dd><span class="input_txt"><span><input type="text" name="bmanNo1" id="bmanNo1" maxlength="3"  onKeyUp="next_BizNo2(this.value.length,document.MyForm.bmanNo2, this)" class="txt" style="width:95px;" ></span></span>
							 - <span class="input_txt"><span><input type="text"  name="bmanNo2" id="bmanNo2" maxlength="2"  onKeyUp="next_BizNo3(this.value.length,document.MyForm.bmanNo3, this)" class="txt" style="width:95px;" ></span></span> 
							 - <span class="input_txt"><span><input type="text"  name="bmanNo3" id="bmanNo3" maxlength="5"  onKeyUp="next_BizNo4(this.value.length,document.MyForm.password, this)" class="txt" style="width:95px;" ></span></span></dd>
					<!-- 
							<dt>비밀번호</dt>
							<dd class="end"><span class="input_txt"><span><input type="password" name="password" maxlength="10" ID="Password1" class="txt" style="width:349px;"></span></span></dd>
					 -->
						</dl>
				<!-- 		<div class="btn_login"><img  src="/images/epc/edi/consult/btn-login.gif" onClick="confirm_submit();" style="cursor:hand;"></div>
				 -->
				 		<div class="btn_login"><img  src="/images/epc/edi/consult/btn-confirm_1.gif" onClick="confirm_submit();" style="cursor:hand;"></div>
				
					</div>
					<!-- //로그인 입력 -->
				</fieldset>
<!-- 
				<div class="pwd_find">
					비밀번호가 기억나지 않으세요? 비밀번호 찾기를 클릭하세요.
					<span class="btn_white btn_w_ls"><span><a href="javascript:FindPwd();"><spring:message code='button.common.findpwd'/></a></span></span>
				</div>
 -->

 
				
 	<div class="pwd_find">
		*사업자번호가 없을 시, Help Desk(02-2145-8000) 로 문의하시기 바랍니다.
	</div>
		

		

	</div>

</div>
</form>

<script type="text/javascript" language="javascript" src="/js/front/front_input.js"></script>



</body>
</html>