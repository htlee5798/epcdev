<%@ page 
    pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/css/epc/edi/consult/base.css" type="text/css">
<link rel="stylesheet" href="/css/epc/edi/consult/popup.css" type="text/css">
<script src="<c:url value='/js/jquery/jquery-1.6.2.min.js'/>" type="text/javascript"></script>
<title>롯데마트 입점상담</title>


	<SCRIPT LANGUAGE="JavaScript">
		function check_input()
		{
				
				if (document.FindFrm.ceoName.value == '')
				{		alert("대표자명을 입력하세요!");
						document.FindFrm.ceoName.focus();
						return false;
				}
				
				if (document.FindFrm.email.value == '')
				{		alert("담당자 Email을 입력하세요!");
						document.FindFrm.email.focus();
						return false;
				}
				
				if (document.FindFrm.businessNo1.value == '' || document.FindFrm.businessNo2.value =='' || document.FindFrm.businessNo3.value ==''	 )
				{		alert("사업자 번호를 입력하세요!");
						
						if(document.FindFrm.businessNo1.value == ''){
							document.FindFrm.businessNo1.focus();
							return;
						}
						if(document.FindFrm.businessNo2.value == ''){
							document.FindFrm.businessNo2.focus();
							return;
						}
						if(document.FindFrm.businessNo3.value == ''){
							document.FindFrm.businessNo3.focus() 
							return;
						}
						return false;
				}
				
				
				
				document.FindFrm.businessNo.value = document.FindFrm.businessNo1.value+document.FindFrm.businessNo2.value+document.FindFrm.businessNo3.value;
				
			return true;		
		}
		
		function submitForm()
		{
			if (check_input())
			{
				//document.FindFrm.comp_nm_val.value = document.FindFrm.comp_nm.value;				
				//document.FindFrm.biz_no_val.value = document.FindFrm.biz_no.value;				
				document.FindFrm.submit();
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

<form Name="FindFrm" METHOD="POST" ACTION="findPassword.do" ID="Form1">

<div id="pop_wrap" class="pop_w_500">

	<div id="pop_head">
		<h1><img src="/images/epc/edi/consult/h1-password-search.gif" alt="비밀번호 찾기"></h1>
	</div>

	<div id="pop_contents">
		<p class="t_default lh18">비밀번호가 기억나지 않으세요? <span class="t_block">상호명과 사업자번호</span>를 입력하세요.</p>
		<div class="box_gray_lt box_w_440 mt15"><div class="box_gray_rb"><div class="box_gray_rt"><div class="box_gray_lb">
			<fieldset>
				<legend>비밀번호 찾기 등록</legend>
				<dl class="input_info clearfloat">
				
					<dt>대표자명</dt>
					<dd>
						<span class="input_txt"><span><input type="text" name="ceoName" class="txt" value="" style="width:287px;"  ID="Text3"></span></span>
					</dd>
					<dt>담당자 E-Mail</dt>
					<dd>
						<span class="input_txt"><span><input type="text" name="email" class="txt" value="" style="width:287px;"  ID="Text3"></span></span>
					</dd>
				
					<dt>사업자번호</dt>
					<dd class="end">
						<span class="input_txt"><span><input type="text" name="businessNo1" class="txt" style="width:75px;" maxlength="3"  ID="Text3"></span></span> 
						<span class="t_default">-</span> 
						<span class="input_txt"><span><input type="text" name="businessNo2" class="txt" style="width:75px;" maxlength="2" ></span></span>
						<span class="t_default">-</span>
						<span class="input_txt"><span><input type="text" name="businessNo3" class="txt" style="width:75px;" maxlength="5"></span></span>
						<input type="hidden" name="businessNo" >
					</dd>
					
				</dl>
			</fieldset>
		</div></div></div></div>

		<!-- button -->
		<div class="btn_c_wrap mt30">
			<span class="btn_red btn_r_s"><span><a href="#" onclick="return submitForm();">조회</a></span></span>
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