<%@ page 
    pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/css/epc/edi/consult/base.css" type="text/css">
<link rel="stylesheet" href="/css/epc/edi/consult/popup.css" type="text/css">
<script src="/js/jquery/jquery-1.6.2.min.js" type="text/javascript"></script>
<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>


	<SCRIPT LANGUAGE="JavaScript">
		function check_input()
		{
				
				if ($("#FindFrm #ceoName").val() == "")
				{		alert("<spring:message code='msg.consult.require.ceoNm'/>");
						$("#FindFrm #ceoName").focus();
						return false;
				}
				
				if ($("#FindFrm #email").val() == "")
				{		alert("<spring:message code='msg.consult.require.email'/>");
						$("#FindFrm #email").focus();
						return false;
				}
				
				if ($("#FindFrm #businessNo1").val() == "" || $("#FindFrm #businessNo2").val() == "" || $("#FindFrm #businessNo3").val() == ""	 )
				{		alert("<spring:message code='msg.consult.require.bmanNo'/>");
						
						if($("#FindFrm #businessNo1").val() == ""){
							$("#FindFrm #businessNo1").focus();
							return;
						}
						if($("#FindFrm #businessNo2").val() == ""){
							$("#FindFrm #businessNo2").focus();
							return;
						}
						if($("#FindFrm #businessNo3").val() == ""){
							$("#FindFrm #businessNo3").focus() 
							return;
						}
						return false;
				}
				
				
				$("#FindFrm #businessNo").val($("#FindFrm #businessNo1").val() + $("#FindFrm #businessNo2").val() + $("#FindFrm #businessNo3").val());
				
			return true;		
		}
		
		function submitForm()
		{
			if (check_input())
			{
				//document.FindFrm.comp_nm_val.value = document.FindFrm.comp_nm.value;				
				//document.FindFrm.biz_no_val.value = document.FindFrm.biz_no.value;				
				$("#FindFrm").submit();
				
			}
			return false;
		}
		<c:if test="${not empty resultMessage }">
			alert('${resultMessage}');
		</c:if>
		</SCRIPT>



</head>

<!-- 500*434 -->
<body id="popup">

<form name="FindFrm" id="FindFrm" method="POST" action="<c:url value='/epc/edi/consult/NEDMCST0310findPassword.do'/>">
<input type="hidden" name="businessNo" id="businessNo" >
<div id="pop_wrap" class="pop_w_500">

	<div id="pop_head">
		<h1><img src="/images/epc/edi/consult/h1-password-search.gif" alt="비밀번호 찾기"></h1>
	</div>

	<div id="pop_contents">
		<p class="t_default lh18"><spring:message code='text.consult.field.passwordInfo'/></p>
		<div class="box_gray_lt box_w_440 mt15"><div class="box_gray_rb"><div class="box_gray_rt"><div class="box_gray_lb">
			<fieldset>
				<legend><spring:message code='text.consult.field.insertFindPwd'/></legend>
				<dl class="input_info clearfloat">
				
					<dt><spring:message code='text.consult.field.ceoNm'/></dt>
					<dd>
						<span class="input_txt"><span><input type="text" name="ceoName" id="ceoName" class="txt" value="" style="width:287px;" ></span></span>
					</dd>
					<dt><spring:message code='text.consult.field.email'/></dt>
					<dd>
						<span class="input_txt"><span><input type="text" name="email" id="email" class="txt" value="" style="width:287px;" ></span></span>
					</dd>
				
					<dt><spring:message code='text.consult.field.bmanNo'/></dt>
					<dd class="end">
						<span class="input_txt"><span><input type="text" name="businessNo1" id="businessNo1" class="txt" style="width:75px;" maxlength="3"  ></span></span> 
						<span class="t_default">-</span> 
						<span class="input_txt"><span><input type="text" name="businessNo2" id="businessNo2" class="txt" style="width:75px;" maxlength="2" ></span></span>
						<span class="t_default">-</span>
						<span class="input_txt"><span><input type="text" name="businessNo3" id="businessNo3" class="txt" style="width:75px;" maxlength="5"></span></span>
						
					</dd>
					
				</dl>
			</fieldset>
		</div></div></div></div>

		<!-- button -->
		<div class="btn_c_wrap mt30">
			<span class="btn_red btn_r_s"><span><a href="#" onclick="return submitForm();"><spring:message code='button.common.inquire'/></a></span></span>
		</div>
		<!-- //button -->

	</div>

</div>
<!--
<script type="text/javascript" language="javascript" src="/js/front/front_input.js"></script>
<script type="text/javascript" language="javascript" src="/js/front/front_common.js"></script>

<script type="text/javascript">
    jQuery(document).ready(function(){
		popupResize(jQuery(this));
	});
</script>
-->

</form>
</body>
</html>