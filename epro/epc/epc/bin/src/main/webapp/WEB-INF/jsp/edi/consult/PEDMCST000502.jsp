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


function venSetting(){
	var form = document.forms[0];
	var url ="<c:url value='/edi/consult/vendorSetting.do'/>";
	var newHtml = "";
	var newHtmlTop = "<table class='bbs_grid3' width='100%' cellpadding='0' cellspacing='0' border='0'>";
	var newHtmlBottom = "</table>";

	$.getJSON(url,
			{
				ajax_bman: $("select[name=sele_bman]").val()
			}, function(j){


		var tmp = j.split(";");
		

		for(var idx=0;idx<tmp.length-1;idx++){
			
			if(idx%10 == 0){
				newHtml += 	"<tr class='r1'>";
			}
			newHtml += "<td><input type='checkbox' name='ck' value='"+tmp[idx]+"'/>&nbsp;"+tmp[idx]+"</td>";
			if(idx%10 == 9 || tmp.length-1 == idx){
				newHtml += 	"</tr>";
			}
			
			//newHtml += "<tr class='r1'><td><input type='checkbox' name='ck' value='"+tmp[idx]+"'/>&nbsp;"+tmp[idx]+"</td></tr>";
		}	
		document.getElementById("venCreate").innerHTML=newHtmlTop+newHtml+newHtmlBottom;	
		
    });

}

function Nextfocus(lenth,name,a)  
{
	var form = document.forms[0];
	var cell = document.getElementsByName("cell2");

	var cellTrueHtml = "<font color='blue'>&nbsp;&nbsp;&nbsp;사용할수 있는 핸드폰 번호입니다.</font>";
	var cellFalseHtml = "<font color='red'>&nbsp;&nbsp;&nbsp;이미 등록된 핸드폰 번호입니다.</font>";
	var url ="<c:url value='/edi/consult/getCellCount.do'/>";
		
	
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

		$.getJSON(url,
				{
					ajax_cell1: $("select[name=cell1]").val(),
					ajax_cell2: $("input[name=cell2]").val(),
					ajax_cell3: $("input[name=cell3]").val(),
					ajax_service: $("select[name=sele_service]").val(),
					ajax_bman_no: $("select[name=sele_bman]").val()
				}, function(j){
	     
		  if(j == "T") {
			  document.getElementById("cellCK").innerHTML=cellTrueHtml;
			  form.valid_tel.value="yes";
		  }else {
			  document.getElementById("cellCK").innerHTML=cellFalseHtml;
			  form.valid_tel.value="no";
		  }
					
	    });
	    
		if(lenth==4)
		{
			form.cell3.focus();
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

		$.getJSON(url,
				{
					ajax_cell1: $("select[name=cell1]").val(),
					ajax_cell2: $("input[name=cell2]").val(),
					ajax_cell3: $("input[name=cell3]").val(),
					ajax_service: $("select[name=sele_service]").val(),
					ajax_bman_no: $("select[name=sele_bman]").val()
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
		form.sele_bman.options.add(new Option(tmp[i], tmp[i]));
	}
	//form.sele_bman.value = "${paramMap.sele_bman}";
}

function valid(){
	var form = document.forms[0];

	var checkboxlist = form.ck;
	var forwardCk="";

	if(form.cell2.value==""){
		alert("핸드폰 번호를 입력하세요.");
		form.cell2.focus();
		return;
	}
	if(form.cell3.value==""){
		alert("핸드폰 번호를 입력하세요.");
		form.cell3.focus();
		return;
	}
	if(form.email.value==""){
		alert("이메일을 입력하세요.");
		form.email.focus();
		return;
	}

	if(checkboxlist.length){
		for(var idx = 0; idx< checkboxlist.length; idx++){
			if(checkboxlist[idx].checked==true){
				forwardCk+=checkboxlist[idx].value+";";
			}
		}
	}else{
		if(checkboxlist.checked==true){
			forwardCk=checkboxlist.value+";";
		}
	}

	if(forwardCk==""){
		alert("선택된 협력업체 코드가 없습니다.");
		return;
	}

	if(form.valid_tel.value=="no"){
		alert("이미 등록된 핸드폰 번호 입니다.");
		return;
	}
	if(form.valid_email.value=="no"){
		alert("이미 등록된 Email 입니다.");
		return;
	}
	
	form.vens.value=forwardCk;
	if(confirm("알리미를 등록합니까?")){
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/consult/PEDMCST000502Insert.do'/>";
		form.submit();
	}
}

function emailCheck(){
	var form = document.forms[0];

	var emailTrueHtml = "<font color='blue'>&nbsp;&nbsp;&nbsp;사용할수 있는 Email 입니다.</font>";
	var emailFalseHtml = "<font color='red'>&nbsp;&nbsp;&nbsp;이미 등록된 Email 입니다.</font>";

	var url ="<c:url value='/edi/consult/getEmailCount.do'/>";
	$.getJSON(url,
			{
				ajax_email: $("input[name=email]").val(),
				ajax_bman_no: $("select[name=sele_bman]").val(),
				ajax_service: $("select[name=sele_service]").val()
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

</script>
 
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post">
		<input type="hidden" name="bman" value="${bman }"/>
		
		<input type="hidden" name="bmanAjax" />
		<input type="hidden" name="emailAjax" />
		
		<input type="hidden" name="vens"/>
		<input type="hidden" name="valid_tel" />
		<input type="hidden" name="valid_email" />
		
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">알리미 서비스 </li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:valid();"><span>등록</span></a>
						</li>
					</ul>
					<ul>
						<li style="height:80px;">
							<br>
							 &nbsp; 서비스 받고자 하는 핸드폰 번호와 E-mail을 등록하세요. 
							 <br> 
							  &nbsp; <b>hanmail.net, daum.net, korea.com, hotmail.com</b>의 메일은 신청할 수 없습니다.
							 <br>
							 &nbsp;  협력업체선택 버튼에서 <font color="red">선택하신 협력업체의 정보만 수신할 수 있습니다.</font>
						</li>
					</ul>
				</div>
				
			</div>
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">SMS & Email 등록</li>
					</ul>
					<div style="width:100%; height:400px; overflow-x:hidden; overflow-y:hidden; table-layout:fixed;" >
						<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0" id="addTable">
							<colgroup>
								<col style="width:100px;" />
								<col />
							</colgroup>
							<tr class="r1">
								<th>사업자 번호</th>
								<td>
									<select name="sele_bman" onchange="javascript:venSetting()"></select>
								</td>
							</tr>
							<tr class="r1">
								<th>서비스 선택</th>
								<td>
									<select name="sele_service">
										<option value="ALM">발주 및 요약정보</option>
										<option value="PAY">대금 및 계산서</option>
									</select>
								</td>
							</tr>
							<tr class="r1">
								<th>핸드폰</th>
								<td align="center"> 
									<select name="cell1">
										<option value="010">010</option>
										<option value="011">011</option>
										<option value="016">016</option>
										<option value="017">017</option>
										<option value="018">018</option>
										<option value="019">019</option>
									</select>&nbsp;-&nbsp;
									<input type="text" name="cell2" size="4" maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);"/>&nbsp;-&nbsp;
									<input type="text" name="cell3" size="4" maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);"/>
									
									<span id="cellCK"></span>
								</td>
							</tr>
							
							<tr class="r1">
								<th>이메일</th>
								<td align="center">
									<input type="text" name="email"  maxlength="30" style="width:345px;" onkeyup="javascript:emailCheck();"/>
									
									<span id="emailCK"></span>
								</td>
							</tr>
							<tr class="r1">
								<th>협력업체코드</th>
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
					<li>홈</li>
					<li>협업</li>
					<li>협업정보</li>
					<li class="last">알리미 서비스</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
