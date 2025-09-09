<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
$(document).ready(function($){
	bmanSet();

	venSetting();
});


function venSetting() {
	var url ="<c:url value='/edi/consult/vendorSetting.json'/>";
	var newHtml = "";
	var newHtmlTop = "<table class='bbs_grid3' width='100%' cellpadding='0' cellspacing='0' border='0'>";
	var newHtmlBottom = "</table>";

	$.getJSON(url,
			{
				ajax_bman: $("select[name=bmanNo]").val()
			}, function(j){


		var tmp = j.split(";");
		

		for(var idx=0;idx<tmp.length-1;idx++){
			
			if(idx%10 == 0){
				newHtml += 	"<tr class='r1'>";
			}
			newHtml += "<td><input type='checkbox' name='venCds' value='"+tmp[idx]+"'/>&nbsp;"+tmp[idx]+"</td>";
			if(idx%10 == 9 || tmp.length-1 == idx){
				newHtml += 	"</tr>";
			}
			
			//newHtml += "<tr class='r1'><td><input type='checkbox' name='ck' value='"+tmp[idx]+"'/>&nbsp;"+tmp[idx]+"</td></tr>";
		}
		$('#venCreate').html(newHtmlTop+newHtml+newHtmlBottom);
		
    });

}

function Nextfocus(lenth,name,a)  
{
	var form = document.forms[0];
	var cell = document.getElementsByName("cell2");

	var cellTrueHtml = "<font color='blue'>&nbsp;&nbsp;&nbsp;<spring:message code='epc.cst.text.text1'/></font>";
	var cellFalseHtml = "<font color='red'>&nbsp;&nbsp;&nbsp;<spring:message code='epc.cst.text.text2'/></font>";
	var url ="<c:url value='/edi/consult/getCellCount.json'/>";
		
	
	if (name=="cell2")
	{
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth<3){   
			alert("<spring:message code='epc.cst.text.text16'/>"); 			// 정확한 전화번호를 입력해주세요.
			$("input[name=cell2]").val("");
			return;
		} 
		
	}else if (name=="cell3"){
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
			
		}
		if(lenth<4){
			alert("<spring:message code='epc.cst.text.text16'/>");			// 정확한 전화번호를 입력해주세요.
			$("input[name=cell3]").val("");
			 return;
		}

		$.getJSON(url,
				{
					ajax_cell1: $("select[name=cell1]").val(),
					ajax_cell2: $("input[name=cell2]").val(),
					ajax_cell3: $("input[name=cell3]").val(),
					ajax_service: $("select[name=anxInfoCd]").val(),
					ajax_bman_no: $("select[name=bmanNo]").val()
				}, function(j){
	     
		  if(j == "T") {
			  document.getElementById("cellCK").innerHTML=cellTrueHtml;
			  form.valid_tel.value="yes";
		  }else {
			  document.getElementById("cellCK").innerHTML=cellFalseHtml;
			  form.valid_tel.value="no";
		  }
					
	    });
		
	}
}

function bmanSet(){
	var form = document.forms[0];

	var tmp = form.bman.value.split(";");

	for(var i=0;i<tmp.length-1;i++){
		form.bmanNo.options.add(new Option(tmp[i], tmp[i]));
	}
}

function valid(){

	var $venCheckBox = $('#searchForm input[name=venCds]:checked');
	
	var $cell2 = $('#searchForm input[name=cell2]');
	var $cell3 = $('#searchForm input[name=cell3]');
	var $email = $('#searchForm input[name=email]');
	
	if($cell2.val() ==""){
		alert("<spring:message code='epc.cst.alert.msg14'/>");
		$cell2.focus();
		return;
	}
	if($cell3.val() ==""){
		alert("<spring:message code='epc.cst.alert.msg15'/>");
		$cell3.focus();
		return;
	}
	if($email.val() ==""){
		alert("<spring:message code='epc.cst.alert.msg16'/>");
		$email.focus();
		return;
	}
	if($venCheckBox.length > 0){
		
	}else{
		alert("<spring:message code='epc.cst.alert.msg17'/>");
		return;
	}
	
	
	var $valid_tel = $('#searchForm input[name=valid_tel]');
	var $valid_email = $('#searchForm input[name=valid_email]');

	if($valid_tel.val() =="no"){
		alert("<spring:message code='epc.cst.alert.msg18'/>");
		return;
	}
	if($valid_email.val() =="no"){
		alert("<spring:message code='epc.cst.alert.msg19'/>");
		return;
	}
	
	if(confirm("<spring:message code='epc.cst.alert.msg20'/>")){
		
		loadingMaskFixPos();
		$('#searchForm').attr('action',"<c:url value='/edi/consult/NEDMCST0032Insert.do'/>");
		$('#searchForm').submit();
	}
}

function emailCheck(){
	
	var form = document.forms[0];
	var emailTrueHtml = "<font color='blue'>&nbsp;&nbsp;&nbsp;<spring:message code='epc.cst.text.text3'/></font>";
	var emailFalseHtml = "<font color='red'>&nbsp;&nbsp;&nbsp;<spring:message code='epc.cst.text.text4'/></font>";
	var invalidChars = "\"|&;<>!*\'\\)(][}{^?$%# ";	
	var emailCheck = "<spring:message code='epc.cst.check.email'/>"; 	//["hanmail.net","daum.net","korea.com", "hotmail.com"];
	emailCheck = emailCheck.split("||");

	if( $("input[name=email]").val() != null && $("input[name=email]").val() != "" ) {
		for( var i = 0 ; i < emailCheck.length ; i++ ) {
			// 이메일 사용불가 도메인 체크
			if( $("input[name=email]").val().indexOf(emailCheck[i]) >= 0 ) {
				alert("<spring:message code='epc.cst.text.text6'/>"+"<spring:message code='epc.cst.text.text7'/>");	//사용불가한이메일입니다. 
				$("input[name=email]").val("");
				$("input[name=email]").focus();
				document.getElementById("emailCK").innerHTML="";
				return false;
			}
			
		}

		// 이메일 형식 체크(특수문자 및 공백)
		for(var i = 0; i<$("input[name=email]").val().length; i++) {
			for(var j = 0; j<invalidChars.length; j++){
				if($("input[name=email]").val().charAt(i) == invalidChars.charAt(j)){
					alert("<spring:message code='epc.cst.text.text15'/>");											//잘못된 이메일 주소입니다. 사용하지 않는 문자가 들어가 있습니다.
				    $("input[name=email]").val("");
				    $("input[name=email]").focus();
				    document.getElementById("emailCK").innerHTML="";
					return false;
				}							
			}													
		}
		
		// 이메일 형식 체크('@','.' 여부 확인)
		if($("input[name=email]").val().indexOf("@") == -1 || $("input[name=email]").val().indexOf(".")<3){
		    alert("<spring:message code='epc.cst.text.text13'/>");													//이메일의 형식이 잘못되었습니다.
		    $("input[name=email]").val("");
		    $("input[name=email]").focus();
		    document.getElementById("emailCK").innerHTML="";
		   	return false;	
		 }
		
		var url ="<c:url value='/edi/consult/getEmailCount.json'/>";
		$.getJSON(url,
				{
					ajax_email: $("input[name=email]").val(),
					ajax_bman_no: $("select[name=bmanNo]").val(),
					ajax_service: $("select[name=anxInfoCd]").val()
				}, function(j){
	     
		  if(j == "T") {
			  document.getElementById("emailCK").innerHTML=emailTrueHtml;
			  form.valid_email.value="yes";
		  }else {
			  document.getElementById("emailCK").innerHTML=emailFalseHtml;
			  form.valid_email.value="no";
		  }
					
	    });
	}
}

</script>
 
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post">
		<input type="hidden" name="bman" value="${bman }"/>
		
		
		<input type="hidden" name="vens"/>
		<input type="hidden" name="valid_tel" />
		<input type="hidden" name="valid_email" />
		
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.alertService'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:valid();"><span><spring:message code='ecp.cst.button.create'/></span></a>
						</li>
					</ul>
					<ul>
						<li style="height:80px;">
							<br>
							 &nbsp; <spring:message code='epc.cst.text.text5'/> 
							 <br> 
							  &nbsp; <b><spring:message code='epc.cst.text.text6'/></b><spring:message code='epc.cst.text.text7'/>
							 <br>
							 &nbsp;  <spring:message code='epc.cst.text.text8'/> <font color="red"><spring:message code='epc.cst.text.text9'/></font>
						</li>
					</ul>
				</div>
				
			</div>
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.text.text10'/></li>
					</ul>
					<div style="width:100%; height:400px; overflow-x:hidden; overflow-y:hidden; table-layout:fixed;" >
						<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0" id="addTable">
							<colgroup>
								<col style="width:100px;" />
								<col />
							</colgroup>
							<tr class="r1">
								<th><spring:message code='epc.cst.text.text11'/></th>
								<td>
									<select name="bmanNo" onchange="javascript:venSetting()"></select>
								</td>
							</tr>
							<tr class="r1">
								<th><spring:message code='epc.cst.text.text12'/></th>
								<td>
									<select name="anxInfoCd">
										<option value="ALM"><spring:message code='epc.cst.codeNm1'/></option>
										<option value="PAY"><spring:message code='epc.cst.codeNm2'/></option>
									</select>
								</td>
							</tr>
							<tr class="r1">
								<th><spring:message code='epc.cst.header.hCode00313'/></th>
								<td align="center"> 
									<select name="cell1">
										<option value="010">010</option>
										<option value="011">011</option>
										<option value="016">016</option>
										<option value="017">017</option>
										<option value="018">018</option>
										<option value="019">019</option>
									</select>&nbsp;-&nbsp;
									<input type="text" name="cell2" size="4" maxlength="4" onblur="Nextfocus(this.value.length,this.name,this);"/>&nbsp;-&nbsp;
									<input type="text" name="cell3" size="4" maxlength="4" onblur="Nextfocus(this.value.length,this.name,this);"/>
									
									<span id="cellCK"></span>
								</td>
							</tr>
							
							<tr class="r1">
								<th><spring:message code='epc.cst.header.hCode00314'/></th>
								<td align="center">
									<input type="text" name="email"  maxlength="30" style="width:345px;" onblur="javascript:emailCheck();"/>
									
									<span id="emailCK"></span>
								</td>
							</tr>
							<tr class="r1">
								<th><spring:message code='epc.cst.search.entpCd'/></th>
								<td>	
									<span id="venCreate"></span>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
					
		</div>
		</form>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li><spring:message code='epc.cst.home'/></li>
					<li><spring:message code='epc.cst.cola'/></li>
					<li><spring:message code='epc.cst.colaInfo'/></li>
					<li class="last"><spring:message code='epc.cst.alertService'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
