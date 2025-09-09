<%--
- Author(s): 
- Created Date: 2014. 08. 04
- Version : 1.0
- Description : 상품별 반품등록 탭화면

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
	

		/* 관리자 로그인 아닌경우 세션 종류*/
		<c:if test="${empty epcLoginVO.adminId}">	
			 doLogout();
		</c:if>
		

		_init();
		
		//버튼 CLICK EVNET ---------------------------------------------------------------
		$('#btnEmplSearch').unbind().click(null, 	_eventEmplSearch);		// 담당자     조회
		$('#btnEmplVenDelete').unbind().click(null,	_eventEmplVenDelete);	// 선택협력사 삭제
		$('#btnEmplVendEdit').unbind().click(null,	_eventEmplVendEdit);	// 선택협력사 이동/복사
		
		$('#btnVendCdfind').unbind().click(null, 	doVendFind);			// 업체코드찾기
		$('#btnVendAddEmpl').unbind().click(null, 	doVendAddEmpl);			// 조회협력사 해당 담당자로 등록
		$('#btnVendCdDel').unbind().click(null, 	delVendorInto);			// 업체코드지움
		//--------------------------------------------------------------------------------
		
		//입력필드(MDer) ENTER KEY EVENT -------------------------------------------------
		$('#empNo_search,#empNm_search').unbind().keydown(function(e) {	// 사번,담당자명
			switch(e.which) {
	    		case 13 : _eventEmplSearch(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
		
		$('#vend_cd').unbind().keydown(function(e) { // 업체코드
			switch(e.which) {
	    		case 13 : _eventSearchEmpVendor(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
		
		
		//-------------------------------------------------------------------------------
		
		// 삭제 체크박스 전체 선택 ---------------------------------------------------------
		$('#allCheckStr').click(function(){
			if ($("#allCheckStr").is(":checked")) { 
				$('input:checkbox[id^=strBox]:not(checked)').attr("checked", true); 
			} else { 
				$('input:checkbox[id^=strBox]:checked').attr("checked", false); 
			} 
		});
		//-------------------------------------------------------------------------------
		
		
		/* 담당자 목록 Click Event */
		$('#datalistEmpl tr').live('click', function(e){
			var obj = $(this).children().last();		// last-td
			var empNos = $(obj).children("input[name='empNos']").val();
			var empNms = $(obj).children("input[name='empNms']").val();
			
			if(empNos){
				$("#datalistEmpl tr").removeClass('tr-selected');
				$(this).addClass('tr-selected');
				
				$("#empNo").text(empNos);
				$("#empNm").text(empNms);
				
				_eventEmplVenSearch(empNos);
			}else{
				return false;
			}
		});
		
	 	
	});
	
	
	
	
	/* 로그아웃 */
	function doLogout()
	{
		var form = document.forms[0];
		var url = '<c:url value="/common/epcLogout.do"/>';
		form.target="_parent";
		form.action=url;
		form.submit();
	}
		
	
	
	/* 초기 설정 */
	function _init(){
		/* 관리자 로그인 아닌경우 세션 종류*/
		<c:if test="${empty epcLoginVO.adminId}">	
			 doLogout();
		</c:if>
		
		
		_setTbodyNoResult($("#datalistEmpl"), 	5, '사번 또는 담당자명을 입력하여 [검색] 하세요!');	// MDer tBody 설정
		_setTbodyNoResult($("#datalistVendor"), 5, '사원정보를 선택 하세요!');						// MDer VEN tBody 설정
	}
	
	/* MDer 담당자 조회 */
	function _eventEmplSearch(){
		
/* 		
		if(!isNumber($('#empNo_search').val())) {
			alert('정상적인 사번을 입력하세요!');
			
			$('#empNo_search').val('');
			$('#empNo_search').focus();
			return false;
		} */
		
		
		
		var str = {  "empNo" 	: $("#empNo_search").val()	//검색조건 사번 
		    		,"empNm"	: $("#empNm_search").val() 	//검색조건 성명
		      	  };
		
		loadingMaskFixPos();
		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
		$.post(
				"<c:url value='/edi/weborder/PEDMWEB0020SearchEmpl.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state != "0"){
						alert("MDer목록 검색중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");	
					}
					else  _setTbodyEmpListValue(data.empList); 
					
					hideLoadingMask();
				},	"json"
		);
	}
	
	function _setTbodyEmpListValue(json){
		
		$("#datalistEmpl tr").remove();	//  TBODY 목록 초기화
		
		$("#empNo").text('');
		$("#empNm").text('');
		
		if(json == null || json.length <=0) {
			_setTbodyNoResult($("#datalistEmpl"), 4, null );
			return;
		}
		
		var sz = json.length;
		var data = json, eleHtml = [], h = -1, pagHtml = [], j = -1;
		
		for ( var k = 0; k < sz; k++) {
			
			eleHtml[++h] = "<tr bgcolor=ffffff  style='cursor: pointer;'>	\n";
			eleHtml[++h] = " <td align='center'>"+(k+1)+"</td>				\n";
			eleHtml[++h] = " <td align='center'>"+data[k].empNo+"</td>		\n";
			eleHtml[++h] = " <td align='center' style='color:#516dc0;'>"+data[k].empNm+"</td>		\n";
			eleHtml[++h] = " <td class='dot_web0020_02 through_tr'>			\n";
			eleHtml[++h] = "   <span title='"+data[k].jobNm+"' >"+data[k].jobNm+"</span> \n";
			eleHtml[++h] = " </td>											\n";
			eleHtml[++h] = " <td align='center'>"+data[k].chgFg+"			\n";
			eleHtml[++h] = " <input type='hidden' name='empNos' value='"+data[k].empNo+"'> \n";
			eleHtml[++h] = " <input type='hidden' name='empNms' value='"+data[k].empNm+"'> \n";
			eleHtml[++h] = " </td>											\n";
			eleHtml[++h] = "</tr>											\n";
		}
		$("#datalistEmpl").append(eleHtml.join(''));
		
	}
	
	
	/* MDer 담당자별 협력사 조회 */
	function _eventEmplVenSearch(empNo){
		
		var str = {  "empNo" 	: empNo		//검색조건 사번 
		    	  };
		
		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
		$.post(
				"<c:url value='/edi/weborder/PEDMWEB0020SearchEmplVen.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state != "0"){
						alert("MDer 별 협력사 목록 검색중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");	
					}
					else  {
						_setTbodyEmpVenListValue(data.venList); 
					}
						
				},	"json"
		);
	}
	
	function _setTbodyEmpVenListValue(json){
		
		$("#datalistVendor tr").remove();	//  TBODY 목록 초기화
		
		if(json == null || json.length <=0) {
			_setTbodyNoResult($("#datalistVendor"), 5, null );
			return;
		}
		
		var sz = json.length;
		var data = json, eleHtml = [], h = -1, pagHtml = [], j = -1;
		
		for ( var k = 0; k < sz; k++) {
			
			eleHtml[++h] = "<tr bgcolor=ffffff>							\n";
			eleHtml[++h] = " <td align='center'>"+(k+1)+"</td>			\n";
			eleHtml[++h] = " <td align='center'>						\n";
			eleHtml[++h] = " <input type='checkbox' id='strBox' name='strBox' onclick='javascript:chkAllStrBox();'> \n";
			eleHtml[++h] = "</td>										\n";
			eleHtml[++h] = " <td align='center'>"+data[k].venCd+"</td>	\n";
			eleHtml[++h] = " <td class='dot_web0020_03 through_tr'>			\n";
			eleHtml[++h] = "   <span title='"+data[k].venNm+"' style='color:#516dc0;'>"+data[k].venNm+"</span> \n";
			eleHtml[++h] = " </td>											\n";
			eleHtml[++h] = " <td align='center'>"+data[k].regDt+"		\n";
			eleHtml[++h] = "   <input type='hidden' name='empNos' value='"+data[k].empNo+"'> \n";
			eleHtml[++h] = "   <input type='hidden' name='venCds' value='"+data[k].venCd+"'> \n";
			eleHtml[++h] = " </td>										\n";
			eleHtml[++h] = "</tr>										\n";
		}
		$("#datalistVendor").append(eleHtml.join(''));
	}
	
	
	
	<%-- 담당자 협력업체 삭제 --%>
	function _eventEmplVenDelete(){
		
		var empNo = $("#empNo").text(); 
		var venCd = "";
		
		if(!empNo){
			alert('선택된 사원 정보가 없습니다.');
			return;
		}
		/*선택한 협력업체 변수 설정 -------------------------*/
		$("#datalistVendor tr").each(function (index){
			$(this).find('input').map(function() {
				if(this.name == 'strBox'){
					if($(this).is(":checked")){
						if(venCd) venCd +=",";
						venCd += $(this).parent().parent().children('td:last').children('input[name="venCds"]').attr('value');
					}
				}
			});
		});
		if(!venCd){
			alert('선택된 협력업체 정보가 없습니다.');
			return;
		}
		var venCds = venCd.split(",");
		/*------------------------------------------------*/
		
		
		/*삭제 조건*/
		var str = {   "empNo" 	: empNo		// 사번 
					 ,"venCds"  : venCds	// 선택한 협력업체 목록
  	  	};
		
		loadingMaskFixPos();
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
	 	$.post("<c:url value='/edi/weborder/PEDMWEB0020DeleteEmplVenList.do'/>"
	  			,JSON.stringify(str)
	  			,function(data){				
	  				if(data == null || data.state !="0"){
	  					alert("삭제처리 중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");		// system exception 또는 기타오류
	  				}
	  				else{
	  					alert("정상적으로 삭제처리 되었습니다.");
	  					_setTbodyEmpVenListValue(data.venList); 	//업체목록 다시 셋팅 
	  				}
	  				hideLoadingMask();
	  			}, 'json');
	 	
	}
	
	/* 조회된 협력업체 선택한 담당자에게 설정*/
	function doVendAddEmpl(){
		var empNo = $("#empNo").text(); 
		var venCd = $("#vend_cd_org").val();
		
		if(!empNo) {
			alert('선택된 담당자정보가 없습니다.');
			return;
		}
		
		if(!venCd) {
			alert('조회된 협력사 정보가 없습니다.');
			return;
		}
		
		
		/*등록 조건*/
		var str = {   "empNo" 	: empNo		// 선택된 사번 
					 ,"venCd"   : venCd		// 조회된 협력업체
  	  	};
		
		loadingMaskFixPos();
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
	 	$.post("<c:url value='/edi/weborder/PEDMWEB0020AddEmplVen.do'/>"
	  			,JSON.stringify(str)
	  			,function(data){				
	  				if(data == null || data.state !="0"){
	  					
	  					if(data.state =="1") alert("이미등록된 협력업체입니다.");					// system exception 또는 기타오류
	  					else alert("협력업체 등록중 중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");	// system exception 또는 기타오류
	  				}
	  				else{
	  					alert("정상적으로 등록처리 되었습니다.");
	  					_eventEmplVenSearch(empNo); 	//업체목록 다시 셋팅
	  					
	  					hideLoadingMask();
	  				}
	  			}, 'json');
		
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
	
	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "조회된 데이터가 없습니다.";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
	

	// 전체 체크박스 해제
	function chkAllStrBox(){
		$("input:checkbox[name=allCheckStr]").attr("checked", false);
	}
	
	
	 //검색된 업체코드 값 지움
	 function delVendorInto(){
		 $("#vend_cd").val('');
		 $("#vend_cd_org").val('');
		 $("#vend_nm").val('');
		 
		 $("#vend_cd").focus();
	 }
	
	
	 
	//업체코드 검색 팝업 호출 
	 function doVendFind(){
		
		 var param = new Object()
	 	, site = "<c:url value='/common/PEDMCOM0008.do'/>"
		, style ="dialogWidth:700px;dialogHeight:450px;resizable:yes;";	

		param.findType  = "02";	//01:전체조회, 02:관리협력사만
		param.venCd = "";
		param.venNm = "";

		window.showModalDialog(site, param, style);
		
		if(param && param.venCd){ 
		
			$('#vend_cd').val(param.venCd);
			$('#vend_cd_org').val(param.venCd);
			$('#vend_nm').val(param.venNm);
		}
		
		return false;
		
	 }

	
	
	//복사,변경 팝업 호출 
	 function _eventEmplVendEdit(){
		 var empNo = $("#empNo").text();
		 if(!empNo) {
			 alert('선택된 사원 정보가 없습니다.');
			return;
		 }
		
		 var param = new Object()
	 	, site = "<c:url value='/edi/weborder/PEDMWEB0020ModEmpl.do'/>"
		, style ="dialogWidth:400px;dialogHeight:260px;resizable:yes;";	

		param.frEmpNo = empNo;
		param.toEmpNo = "";

		window.showModalDialog(site, param, style);
		
		
		return false;
			
	 }
	
	
	 /*회사코드 찾기 공통*/
	function _eventSearchEmpVendor(){
		
		var venCd  =  $("#vend_cd").val();  // 업체코드 조건
		
		if(!$.trim(venCd) || venCd.length != 6 ){
			alert("업체코드 6자리를 정확히 입력하세요!");
			$("#vend_cd").focus();
			return false;
		}
		
		if(!isNumber(venCd) ) {
			alert('정상적인 협력업체코드를 입력하세요!');
			
			$('#vend_cd').val('');
			$('#vend_cd').focus();
			return false;
		}
		
		delVendorInto();
		
	
		
		var str = {  "venCd" 	: venCd	//검색조건  
				   , "findType" : "02"
			      };
		loadingMaskFixPos();
			$.ajaxSetup({
		  		contentType: "application/json; charset=utf-8" 
				});
			$.post(
					"<c:url value='/edi/weborder/PEDMWEB0099SearchEmpVendor.do'/>",
					JSON.stringify(str),
					function(data){
						
						if(data == null || data.state !="0"){
							alert("업체코드 검색중 오류가 발생하였습니다.[ CODE:"+data.state+" ]");
							hideLoadingMask();
						 	return;
						}
						else
						{
							var venData = data.venData;
							
							if(venData ==null || !venData.venCd) {
								alert("검색된 업체정보가 없습니다.");
								hideLoadingMask();
							 	return;
							}
							
							$('#vend_cd'  	  ).val(venData.venCd);
							$('#vend_cd_org'  ).val(venData.venCd);
							$('#vend_nm'  	  ).val(venData.venNm);
						
						}
						hideLoadingMask();
					},	"json"
			);
	
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

.dot_web0020_01 span{width:100px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}
.dot_web0020_02 span{width:140px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}
.dot_web0020_03 span{width:160px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}

/* SELECTED ROW STYLE*/
.tr-selected { background: #DDDFF1; color: #5860a1; font-weight: bold; }
</style>

		
</head>
<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<input type="hidden" name="searchFlow" value="yes" />
		<input type="hidden" name="staticTableBodyValue">
	
		
		<div id="wrap_menu">
		
		<!--	@ 검색조건	-->
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">협력사별 담당자 설정 관리</li>
					<li class="btn">
						<a href="#" class="btn" id="btnEmplVenDelete" ><span>삭제</span></a>
						<a href="#" class="btn" id="btnEmplVendEdit"  ><span>담당자변경/복사</span></a>
					</li>
				</ul>
				<table class="bbs_search" >
					<tr height="50">
						<td style="background-color: #ffffff;">
							<p style="margin-left: 10px;">
								<span style="color: #656565; font-size: 1.0em;">
										<strong>1.</strong> <span style='color: #5470c0; font-weight: bolder;' >MDer 담당자 목록</span>을 <span style='color: #5470c0'>'사번, 담당자명'</span> 검색조건을 통해 조회 합니다. 
									<br><strong>2.</strong> 조회된 담당자를 선택 하면 <span style='color: #5470c0'>상세 협력사 목록</span>을 확인 할 수 있습니다. 
								 	<br><strong>3.</strong> 선택된 담당자의 <span style='color: #5470c0; font-weight: bolder;' >협력사 추가 및 삭제, 담당자 변경/복사</span> 기능을 통해 관리 할 수 있습니다. 
								</span>
							</p> 
						</td>
					</tr>
					
				</table>
			</div>
			<table cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="45%"/>
					<col width="2px"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<td valign="top">
						<div class="bbs_search" style="margin-top: 2px;">	
							<table class="bbs_search" >
								<colgroup>
									<col style="width:50px" />
									<col style="width:100px" />
									<col style="width:70px" />
									<col style="*" />
									<col style="width:55px" />
								</colgroup>
								
								<tr>
									<th>사 번</th>
									<td style="border-bottom-color: #ffffff; "> <input type=text id="empNo_search" maxlength="9" style="width: 90%; ime-mode:disabled;" ></td>
									<th>담당자명</th>
									<td style="border-bottom-color: #ffffff; "> <input type=text id="empNm_search" style="width: 90%;"></td>
									<td style="border-bottom-color: #ffffff; "> <a href="#" class="btn" id="btnEmplSearch" ><span>검색</span></a></td>
								</tr>
							
							</table>
						</div>
						
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">MDer 담당자 목록 </li>
								<li class="btn"></li>
							</ul>
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
		                        <colgroup>
									<col width="31px"/>
									<col width="65px"/>
									<col width="70px"/>
									<col width="*"/>
									<col width="30px"/>
									<col width="18px"/>
								</colgroup>
								<thead>
		                        	<tr bgcolor="#e4e4e4" align=center height="25"> 
		                          		<th>No.</th>
		                          		<th>사번</th>
		                          		<th>담당자명</th>
		                          		<th>직무</th>
		                          		<th>구분</th>
		                          		<th></th>
		                          	</tr>
		                      	</thead>
		                        	 <tr> 
				                     	<td colspan=6>   
				                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:400px; overflow-y: scroll; overflow-x: hidden">
				                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
				                     			<colgroup>
													<col width="29px"/>
													<col width="65px"/>
													<col width="70px"/>
													<col width="*"/>
													<col width="30px"/>
												</colgroup>
												<tbody id="datalistEmpl" />
				                     			</table>
				                     		</div>
				                     	</td>
				                    </tr>
							</table>
						</div>
								
					</td>
					
					<td style="border-left-color: red;">&nbsp;</td>
					
					<td valign="top">
						<div class="bbs_search" style="margin-top: 2px;">	
							<table class="bbs_search">
								<colgroup>
									<col style="width:80px;" />
									<col style="width:51px;" />
									<col style="width:100%;" />
									<col style="width:55px;" />
									<col style="width:20px;" />
									<col style="width:30px;" />
									
								</colgroup>
								<tr> 
									<th>협력사 등록</th>
									<td>
										<input type="text" 	 id="vend_cd" 	name="vend_cd" 	maxlength="6"	style="width: 50px; ime-mode:disabled;" onkeypress="onlyNumber();">
										<input type="hidden" id="vend_cd_org" name="vend_cd_org">
									</td>
									<td><input type="text" id="vend_nm" name="vend_nm" style="width: 100%; " class="inputReadOnly" readonly="readonly" ></td>
									<td><a href="#" class="btn" id="btnVendAddEmpl"  title="협력업체 추가" ><span>추가</span></a></td>
									<td><span id="btnVendCdDel"> <img src="/images/epc/btn/icon_01.png" class="middle" style="cursor:pointer;" title="삭제" /></span></td>
									<td><span id="btnVendCdfind"><img src="/images/epc/btn/icon_02.png" class="middle" style="cursor:pointer;" title="협력사찾기팝업" /></span></td>
								</tr>
							</table>
						</div>
						
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">MDer 담당자 별 협력사  </li>
								<li class="btn">
									<span style="font-weight: bolder;">담당자 : </span><span id='empNm'></span>(<span id='empNo'></span>)
								</li>
							</ul>
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
		                        <colgroup>
									<col width="31px"/>
									<col width="50px"/>
									<col width="70px"/>
									<col width="*"/>
									<col width="100px"/>
									<col width="18px"/>
								</colgroup>
								<thead>
		                        	<tr bgcolor="#e4e4e4" align=center height="25"> 
		                          		<th>No.</th>
		                          		<th>삭제<input type="checkbox" id="allCheckStr" name="allCheckStr"></th>
		                          		<th>업체코드</th>
		                          		<th>업체명</th>
		                          		<th>등록일</th>
		                          		<th></th>
		                          	</tr>
		                      	</thead>
		                        	 <tr> 
				                     	<td colspan=6>   
				                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:400px; overflow-y: scroll; overflow-x: hidden">
				                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
				                     			<colgroup>
													<col width="29px"/>
													<col width="50px"/>
													<col width="70px"/>
													<col width="*"/>
													<col width="100px"/>
												</colgroup>
												<tbody id="datalistVendor" />
												
												</table>
				                     		</div>
				                     	</td>
				                    </tr>
				                 
							</table>
						</div>
							
					</td>
				</tr>
				<tr><td colspan="3" height="2"></td></tr>
			</table>
			
			
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
					<li>웹발주</li>
					<li>MDer</li>
					<li class="last">MDer 협력업체 설정</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
