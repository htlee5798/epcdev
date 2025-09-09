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
});


<c:if test="${not empty resultMessage }">
alert('${resultMessage }');
</c:if>


function confirm_submit()
	{
	var form = document.MyForm;
	
		if(document.MyForm.bman_no1.value.length==0)
		{
			strMsg ="<spring:message code='msg.business.number'/>";      
			alert(strMsg);   
			//popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.bman_no1"
			//openPopup2(popUrl,'ID');   
		
			return;
		}
		
		if(document.MyForm.bman_no1.value.length!=3)
		{
			strMsg ="<spring:message code='msg.business.number.format'/>";
			strMsg="1번째 사업자항목 확인해주세요!"+strMsg 
			alert(strMsg);   
			//popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.bman_no1"
			//openPopup2(popUrl,'ID');   
		    return;
		}
		
		
		if(document.MyForm.bman_no2.value.length==0)
		{
			strMsg ="<spring:message code='msg.business.number'/>";         
			alert(strMsg);   
			return;
		}
		if(document.MyForm.bman_no2.value.length!=2)
		{
			strMsg ="<spring:message code='msg.business.number.format'/>";         
			strMsg="2번째 사업자항목 확인해주세요!"+strMsg
			alert(strMsg);   
			return;
		}
		
		
		if(document.MyForm.bman_no3.value.length==0)
		{
			strMsg ="<spring:message code='msg.business.number'/>";         
			alert(strMsg);   
			return;
		}
		if(document.MyForm.bman_no3.value.length!=5)
		{
			strMsg ="<spring:message code='msg.business.number.format'/>";         
			strMsg="3번째 사업자항목 확인해주세요!"+strMsg
			alert(strMsg);    
			return;
		}
	   


		var busienssNo = $("#Text1").val()+$("#Text2").val()+$("#Text3").val();
		$("input[name=businessNo]").val(busienssNo);
		document.MyForm.submit();
		return;
}
	
	function openBizNo() {
	    if(!checkbman_no3(MyForm.bman_no1, MyForm.bman_no2, MyForm.bman_no3))
		{ 
			// false가 리턴되는 경우
			return;
		}
		
		MyForm.authBizNo.value = '1';
		strMsg ="<spring:message code='msg.business.number.match'/>"; 
		popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.bman_no1"
		openPopup2(popUrl,'ID');
	}

	function checkbman_no3(obj1,obj2,obj3)          
	{          
		if(!(obj1.value)||!(obj2.value)||!(obj3.value)){
			strMsg ="<spring:message code='msg.business.number'/>";
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.bman_no1"
			openPopup2(popUrl,'ID');
					
			return false;
		}
		li_value = new Array(10);  
		
		if ( strLeng(obj1.value) == 3 && strLeng(obj2.value) == 2 && strLeng(obj3.value) == 5){          
	   		if ( ( isInteger(obj1.value)) && ( isInteger(obj2.value)) && ( isInteger(obj3.value))){          
	          li_value[0] = ( parseFloat(obj1.value.substring(0 ,1))  * 1 ) % 10;          
	          li_value[1] = ( parseFloat(obj1.value.substring(1 ,2))  * 3 ) % 10;          
	          li_value[2] = ( parseFloat(obj1.value.substring(2 ,3))  * 7 ) % 10;          
	          li_value[3] = ( parseFloat(obj2.value.substring(0 ,1))  * 1 ) % 10;          
	          li_value[4] = ( parseFloat(obj2.value.substring(1 ,2))  * 3 ) % 10;          
	          li_value[5] = ( parseFloat(obj3.value.substring(0 ,1))  * 7 ) % 10;          
	          li_value[6] = ( parseFloat(obj3.value.substring(1 ,2))  * 1 ) % 10;          
	          li_value[7] = ( parseFloat(obj3.value.substring(2 ,3))  * 3 ) % 10;          
	          li_temp = parseFloat(obj3.value.substring(3,4))  * 5 + "0";          
	          li_value[8] = parseFloat(li_temp.substring(0,1)) + parseFloat(li_temp.substring(1,2));          
	          li_value[9] =  parseFloat(obj3.value.substring(4,5));          
	          li_lastid = (10 - ( ( li_value[0] + li_value[1] + li_value[2] + li_value[3] + li_value[4] + li_value[5] + li_value[6] + li_value[7] + li_value[8] ) % 10 ) ) % 10;          
	          if (li_value[9] != li_lastid){                      
	        	  strMsg ="<spring:message code='msg.business.number.mismatch'/>";         
	         	popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.bman_no1"
	         	openPopup(popUrl,'ID',280,166)        
	         	                  
	            return false; 
	           // return true;         
	          }else          
	            return true;
	        }else          
	        	 strMsg ="<spring:message code='msg.business.number.format'/>"; 
	             popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.bman_no1"
	         	 openPopup(popUrl,'ID',280,166);           
	             // obj1.focus();          
	             // obj1.select();          
	             return false;          
	    }else {          
	    	strMsg ="<spring:message code='msg.business.number.format'/>";  
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.bman_no1"
			openPopup(popUrl,'ID',280,166);    
	  		//obj1.focus();          
	  		//obj1.select();          
	  		return false;          
	    } 
	
		return true;    	             
	}

	function FindPwd()
	{
		var popurl = "${ctx }/epc/edi/consult/findPassword.do";
		window.open(popurl,null, "height=390,width=500,status=no,toolbar=no,menubar=no,location=no");
	}
	
	
	function openPopup(openUrl, popTitle, WindowWidth, WindowHeight)
	{
		var WindowLeft = (screen.width - WindowWidth)/2;
		var WindowTop= (screen.height - WindowHeight)/2;
	
		NewWin = window.open(openUrl, popTitle, "titlebar=no, resizable=1, width="+WindowWidth+", height="+WindowHeight+", top="+WindowTop+", left="+WindowLeft);
		NewWin.focus();
	}     
	
	function openPopup2(openUrl, popTitle)
	{	
		var WindowWidth = 280;
		var WindowHeight = 166;
		var WindowLeft = (screen.width - WindowWidth)/2;
		var WindowTop= (screen.height - WindowHeight)/2;
	
		NewWin = window.open(openUrl, popTitle, "titlebar=no, resizable=1, width="+WindowWidth+", height="+WindowHeight+", top="+WindowTop+", left="+WindowLeft);
		NewWin.focus();
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
		
	function notAuthBizNo()
	{
		MyForm.authBizNo.value = '0';
	}
	

		
		

	</script>

</head>
<body >

<form name="MyForm" method="post"  action="${ctx }/epc/edi/consult/checkVendorBusinessNo.do" ID="Form1">
<input type="hidden" name="businessNo" />
<input type=hidden name=authBizNo value = '' ID="Hidden1">
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
							<dd><span class="input_txt"><span><input type="text" name="bman_no1"  maxlength="3" class="txt"  onKeyUp="next_BizNo2(this.value.length,document.MyForm.bman_no2, this)" style="width:95px;" ID="Text1"></span></span>
							 - <span class="input_txt"><span><input type="text"  name="bman_no2"  maxlength="2"  onKeyUp="next_BizNo3(this.value.length,document.MyForm.bman_no3, this)" class="txt" style="width:95px;" ID="Text2"></span></span> 
							 - <span class="input_txt"><span><input type="text"  name="bman_no3"  maxlength="5"  onKeyUp="next_BizNo4(this.value.length,document.MyForm.password, this)" class="txt" style="width:95px;" ID="Text3"></span></span></dd>
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