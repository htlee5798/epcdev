<%--
- Author(s): 
- Created Date: 2014. 08. 22
- Version : 1.0
- Description : 협력사 담당자 복사/이동

--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script  type="text/javascript" >
	
	
	$(function() {
		
		pageOnloadInit();
		
		
		//입력필드(MDer) ENTER KEY EVENT -------------------------------------------------
		$('#btnSearchEmpPool').unbind().click(null, 	_eventSearchEmpPool);			 // 담당자     조회
		
		$('#btnChangeEmpPool').click( function () { _eventCopyAndMoveEmpPool('M'); });   // 담당자     변경
		$('#btnCopyEmpPool').click( function ()   { _eventCopyAndMoveEmpPool('C'); });   // 담당자     복사
				
		
		//입력필드(MDer) ENTER KEY EVENT -------------------------------------------------
		$('#empNo_search').unbind().keydown(function(e) {				// 사번
			
			switch(e.which) {
	    		case 13 : _eventSearchEmpPool(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
	});
	
	
	var opener;
	
	/*넘어온 사번 값 설정*/
	function pageOnloadInit(){
	
		opener = window.dialogArguments;
		
		if(opener && opener.frEmpNo ) {
			$('#fromEmpNo').text(opener.frEmpNo);
			$("#empNo_search").focus();
		}
		else{
			alert('선택된 대상 사번이 누락되었습니다. 다시 선택하세요!');
			self.close();
		}
		
	}
	
	
	
	/*선택된 담당자 정보를 조회한 담당자로 복사,이동*/
	function _eventCopyAndMoveEmpPool(types){
		
		var toEmpNo  =  $("#empNo_view").text();  // 작업 대상 사번
		var frEmpNo  =  $("#fromEmpNo").text();   // 대상 사번
		
		if(!doCheckVal(types)) return
	
		var typeName = "복사";
		if(types == "M")  typeName = "변경";
		
		loadingMaskFixPos();
	
		var str = {   "toEmpNo" 	: toEmpNo	//작업 대상 사번  
					 ,"frEmpNo" 	: frEmpNo	//대상 사번
					 ,"eventType"	: types		// M:이동, C:복사 
	      		  };
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value="/edi/weborder/PEDMWEB0020MoveAndCopyEmplPool.do"/>",
				JSON.stringify(str),
				function(data){
					
					if(data == null || data.state !="0"){
						
						if(data.state =="1") {
							alert(typeName+" 정보가 한건도 없습니다.[ CODE:"+data.state+" ]");
						}
						else alert("사원"+typeName+" 작업중 오류가 발생하였습니다.[ CODE:"+data.state+" ]");{
							hideLoadingMask();
							return;
						}
						hideLoadingMask();
					}
					else
					{
						alert("사원정보가 정상적으로 "+typeName+"되었습니다.");
						setClearData(true);
					}
					hideLoadingMask();
				},	"json"
		);
	
	
	}
	
	
	/*복사,이동 전송전 검사*/
	function doCheckVal(type){
		
		var toEmpNo  =  $("#empNo_view").text();  // 작업 대상 사번
		var frEmpNo  =  $("#fromEmpNo").text();  // 복사 대상 사번
		
		var typeName = "복사";
		if(type == "M")  typeName = "변경";
		
		if(!frEmpNo){
			alert("선택된 "+typeName+" 대상 사번정보가 없습니다. 다시 선택 하세요!");
			return false;
			self.close();
		}
		
		if(!toEmpNo){
			alert(typeName+" 적용사번을 조회하세요!");
			$("#empNo_search").focus();
			return false;
		}
		return true;
	}
	
	
	/*담당자 사번 조회*/
	function _eventSearchEmpPool(){
		
		
		var toEmpNo = $("#empNo_search").val();	 // 검색사번
		var frEmpNo  =  $("#fromEmpNo").text();  // 복사 대상 사번
		
		<%-- 조회조건 검증 ============================= --%>
		if(!$.trim(toEmpNo) || toEmpNo.length != 9 ){
			alert("사번 9자리를 정확히 입력하세요!");
			$("#empNo_search").focus();
			return false;
		}
		
		if(toEmpNo == frEmpNo) {
			alert("대상 사번과 적용사번은 동일 할 수 없습니다.");
			$("#empNo_search").val('');
			$("#empNo_search").focus();
			return false;
		}
		<%-- ----------------------------------------- --%>
		
		setClearData(false);
		
		var str = {  "empNo" 	: toEmpNo	//검색조건  
			      };
	
		loadingMaskFixPos();
		
			$.ajaxSetup({
		  		contentType: "application/json; charset=utf-8" 
				});
			$.post(
					"<c:url value="/edi/weborder/PEDMWEB0099SearchEmplPool.do"/>",
					JSON.stringify(str),
					function(data){
						
						if(data == null || data.state !="0"){
							alert("사원검색중 오류가 발생하였습니다.[ CODE:"+data.state+" ]");
							hideLoadingMask();
						 	return;
						}
						else
						{
							var empPool = data.empPool;
							
							if(empPool ==null || !empPool.empNo) {
								alert("검색된 사원정보가 없습니다.");
								hideLoadingMask();
							 	return;
							}
							
							$('#empNm_view'  ).text(empPool.empNm);
							$('#empNo_view'  ).text(empPool.empNo);
							$('#orgNm_view'  ).text(empPool.orgNm);
							$('#jobNm_view'  ).text(empPool.jobNm);
						}
						hideLoadingMask();
					},	"json"
			);
		
	}

	function setClearData(state){
		
		if(state) $('#empNo_search'  ).val('');  //검색조건까지 초기화
		
		$('#empNm_view'  ).text('');
		$('#empNo_view'  ).text('');
		$('#orgNm_view'  ).text('');
		$('#jobNm_view'  ).text('');
	}
	
	/*  JSON 파싱 체크*/
	function _jsonParseCheck(jsonStr){
		//jsonParseCheck(jsonStr);
		
		//alert("jsonStr : " + jsonStr);
		if(jsonStr == null || jsonStr == "") {
			return null;
		}
	
		var json = JSON.parse(jsonStr);
		
		if(json==null){
			return null;
		}
		
		var resultCd = json.__RESULT__;
		if(resultCd==null || resultCd!="NG"){
			return json;
			
		}else{
			var errCd = json.__ERR_CD__;
			var errMsg = json.__ERR_MSG__;
			
			alert("[ 에 러 ]\n" + errMsg + ", Code : " + errCd);
			return null;
		}
		
	}

	
	//-----------------------------------------------------------------------
	//숫자만 입력가능하도록
	//-----------------------------------------------------------------------
	//예)
	//html : <input type='text' name='test' onkeypress="onlyNumber();">
	//-----------------------------------------------------------------------
	function onlyNumber()
	{

		if((event.keyCode<48) || (event.keyCode>57))
		{
			event.returnValue=false;
		}
	}
	
	//금액 콤마 - value 계산용
	function amtComma(amt) {
	    var num = amt + '';
	    for(var regx = /(\d+)(\d{3})/;regx.test(num); num = num.replace(regx, '$1' + ',' + '$2'));
	    return num;
	}

	//금액 콤마 - onkeyup용 
	function amtFormat(obj) {
	    var num = obj.value + '';
	    for(var regx = /(\d+)(\d{3})/;regx.test(num); num = num.replace(regx, '$1' + ',' + '$2'));
	    obj.value = num;
	}
	
	//금액 콤마 제거
	function unNumberFormat(obj) {
		var num = obj.value;
		obj.value =  (num.replace(/\,/g,""));
	}
	
	//숫자검사
	function isNumber(str) {
		var chars = "0123456789";
		return containsCharsOnly(str, chars);
	}
	//Chars 검사
	function containsCharsOnly(input,chars) {
	   for (var inx = 0; inx < input.length; inx++) {
	       if (chars.indexOf(input.charAt(inx)) == -1)
	           return false;
	    }
	    return true;
	}
</script>	

<style>
.dot_web0001 span{width:160px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}

/* tab + button group */
.nav-tabs-group {margin-bottom: 0px;margin-left: 0;list-style: none;}
.nav-tabs-group > li > a {display: block; text-decoration: none; font-weight: bold;}
.nav-tabs-group > li > a:hover {background-color: #eeeeee;}
.nav-tabs{*zoom: 1;}
.nav-tabs:before,.nav-tabs:after{display: table;line-height: 0;content: "";}
.nav-tabs:after{clear: both;}
.nav-tabs > li.active, .nav-tabs > li.inactive {float: left; margin-bottom: -1px;}
.nav-tabs > li > a{
	color: #dcdcdc;
	padding-right: 12px;
	padding-left: 12px;
	margin-right: 2px;
	line-height: 14px;
	text-decoration: none;
  	background-color: #c1c1c1;
  	padding-top: 4px;
	padding-bottom: 4px;
	line-height: 20px;
	border-color: #eeeeee #eeeeee #a9a9a9;
	border-width:1px;
	border-style: solid;
	-webkit-border-radius: 4px 4px 0 0;
	   -moz-border-radius: 4px 4px 0 0;
	        border-radius: 4px 4px 0 0;}
.nav-tabs {border-bottom: 1px solid #a9a9a9;}
.nav-tabs > li.inactive > a:hover {}
.nav-tabs > .active > a, .nav-tabs > .active > a:hover {
  cursor: default;
  color: #FFFFFF;
  background-color: #c28181;
  border: 1px solid #a9a9a9;
  border-bottom-color: transparent;
}
.nav-tabs-btn {float:right; width:auto;margin-top:5px;}
.nav-tabs-box {width:auto; padding: 10px; border-left:1px solid #a9a9a9;border-bottom:1px solid #a9a9a9;border-right:1px solid #a9a9a9; }
.btn_red_basket a	{display:inline-block; min-width:61px; _width:61px; line-height:20px; font-weight:bold; white-space:nowrap; text-align:center;}

</style>

<base target="_self"/>
</head>
<body>

<div id="popup">
		
			<div id="p_title1">
				<h1>담당자 변경/복사 </h1>
				<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>
			<div class="popup_contents">
				
				
				<div class="bbs_list" style="margin-top: 10px;">
					<ul class="tit">
						<li class="tit">담당자 변경 및 복사 관리</li>
						<li class="btn">
							<a href="#" class="btn" id="btnChangeEmpPool"><span>담당자 변경</span></a>
							<a href="#" class="btn" id="btnCopyEmpPool"  ><span>담당자 복사</span></a>
						</li>
					</ul>
					<table class="bbs_search" >
						<colgroup>
							<col width="150px;">
							<col width="*">
						</colgroup>
						<tr>
							<th>대상사번</th>
							<td><span id="fromEmpNo"></span></td>
						</tr>
						<tr height="30">
							<th>적용사번조회</th>
							<td>
								<input type="text" id="empNo_search" name="empNo_search"  maxlength="9" onkeypress="onlyNumber();" style='width:90px; font-size:1.1em; ime-mode:disabled'>
								<a href="#" class="btn" id="btnSearchEmpPool" ><span>검색</span></a>
							</td>
						</tr>
					</table>
				</div>
				<div class=bbs_search style="margin-top: 2px;">
					<ul class="tit">
						<li class="tit">적용사번 조회 결과</li>
						<li class="btn"></li>
					</ul>
					<table class="bbs_search" >
						<tr>
							<td style="text-align: center;">성 &nbsp;명</td>
							<td style="background-color: #ffffff;">
								<span id="empNm_view" style="color: #5c66c4;"></span>
							</td>
							<td style="text-align: center;">사 &nbsp;번</td>
							<td style="background-color: #ffffff;">
								<span id="empNo_view" style="color: #5c66c4;"></span>
							</td>
						</tr>
						<tr>
							<td style="text-align: center;">부서명</td>
							<td colspan=3 style="background-color: #ffffff;">
								<span id="orgNm_view" style="color: #5c66c4"></span>
							</td>
						</tr>
						<tr>	
							<td style="text-align: center;">직무명</td>
							<td colspan=3 style="background-color: #ffffff;">
								<span id="jobNm_view" style="color: #5c66c4"></span>
							</td>
						</tr>
					</table>
				</div>			
	
			</div>
			
		</div>
	
</body>
</html>
