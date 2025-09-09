<%--
- Author(s): 
- Created Date: 2015. 12. 10
- Version : 1.0
- Description : 반품 전체 현황

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
	
	

	/*-------------------------- MDer설정 */
	$('#btnEmpClear').unbind().click(null, _eventClearMder);
	
	$('#findEmpNo').unbind().keydown(function(e) {
		switch(e.which) {
	   	case 13 : _eventMDerFind(this); break; // enter
	   	default : return true;
	   	}
    	e.preventDefault();
	});
	/*----------------------------------*/
	
	
	//버튼 CLICK EVNET ---------------------------------------------------------------
	$('#btnSearch').unbind().click(null, doSearch);	// 반품조회
	
	
	/*-------------------------- 업체정보찾기 */
	$('#btnVendCdfind').unbind().click(null, doVendFind);	// 업체코드찾기
	$('#btnVendCdDel').unbind().click(null, delVendorInto);	// 업체코드지움
	/*----------------------------------*/
	
	
	//-------------------------------------------------------------------------------
	
    // 상품코드 enter key이벤트 --------------
	$('#startDate,#endDate,#areaCd,#strCd,#vendNm,#prodCd, input[name=pageRowCount]').unbind().keydown(function(e) {
		switch(e.which) {
	   	case 13 : doSearch(this); break; // enter
	   	default : return true;
	   	}
	   	e.preventDefault();
	});
		//---
		
		
	$('#vendCd').unbind().keydown(function(e) { // 업체코드
		switch(e.which) {
    		case 13 : doVendCdFind(this); break; // enter
    		default : return true;
    	}
    	e.preventDefault();
   	});
	
		
	/*-------------------------- 업체정보찾기 */
	/* 업체코드 포커스를 잃었을 경우 업체코드가 공백이면 업체코드 삭제 */
 	$('#vendCd').live('blur', function(e) {
 		if(!$.trim($("#vendCd").val())){
 			$("#vendNm").val('');
		}
	}); 
	
 	/*--------------------------------------*/

	
	
	/* 페이지번호 Click Event 발생시 조회함수 호출하다. */
	$('#paging a').live('click', function(e) {
		// #page : 서버로 보내는 Hidden Input Value 
		$('#page').val($(this).attr('link'));
		// 개발자가 만든 조회 함수
		doSearch();
	});
	
	
	
	// 권역 셀렉트 박스 변경시 점포 코드 변경
	$("#areaCd").change(function () {
		var _majorCD = $("#areaCd").val();
		_storeSelectCodeOptionTag(_majorCD, "#strCd", "전체");
		
	});
		
	
	$('#startDate').val(caldate(1));
    $('#endDate').val(caldate(0));
    
    
    /*-------------------------- 업체정보찾기 */
    /* 업체코드 포커스를 잃었을 경우 업체코드가 공백이면 업체코드 삭제 */
    $('#vendCd').live('blur', function(e) {
        if(!$.trim($("#vendCd").val())){
    		$("#vendNm").val('');
    	}
    }); 
    /*--------------------------------------*/

	
});


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
	_setTbodyNoResult($("#datalist"), 	11, '<spring:message code="text.web.field.srchInit16"/>');	// prod tBody 설정
}	

/*-- 업체정보찾기 */
/*------------------------------------------------------------------------------------*/

function doVendCdFind(){
		<%-- 조회조건 검증 ============================= --%>
		var venCd  =  $("#vendCd").val();  // 업체코드 조건
		
		if(!$.trim(venCd) || venCd.length != 6 ){
			alert("<spring:message code='msg.web.error.venCdMin6'/>");
			$("#vendCd").focus();
			return false;
		}
		
		if(!isNumber(venCd)) {
			alert("<spring:message code='msg.web.error.valVenCd'/>");
			$('#vendCd').val('');
			$('#vendCd').focus();
			return false;
		}
		
		

		delVendorInto();
		

		 if(!$('#empNo').val()) {
				alert("<spring:message code='msg.web.error.mder'/>");
				$('#findEmpNo').focus();
				return false;
		 }	

		
		var str = {  "venCd" 	: venCd,	//검색조건  
			     	 "empNo"    :  $("#empNo").val() 
	      };
		
		//loadingMaskFixPos();
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/NEDMWEB0099SearchEmpVendor.json'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state !="0"){
						alert('<spring:message code="msg.web.error.venCd" arguments="' + data.state + '"/>');
						//hideLoadingMask();
					 	return;
					}
					else
					{
						var venData = data.venData;
						
						if(venData == null || !venData.venCd) {
							
							alert("<spring:message code='msg.web.error.srchVenCd'/>");
							doVendFind();
							//$('#btnVendCdfind').trigger('click');
							//hideLoadingMask();
						} else {
							$('#vendCd').val(venData.venCd);
							$('#vendCdOrg').val(venData.venCd);
							$('#vendNm').val(venData.venNm);
						}

							
							//hideLoadingMask();
					}
					//hideLoadingMask();
					
				},	"json"
			);

	} 
	

// 업체코드 팝업
function PopupVenCdWindow(pageName,vendCd) {
	
	var cw=500;
	var ch=640;
	var sw=screen.availWidth;
	var sh=screen.availHeight;
	var px=Math.round((sw-cw)/2);
	var py=Math.round((sh-ch)/2);
	window.open(pageName+"?vendCd="+vendCd+"" ,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=no");
}
	
	
//업체코드 검색 팝업 호출 
 function doVendFind(){
	 var param = new Object()
	 	, site = "<c:url value='/common/PEDMCOM0008.do'/>"
		, style ="dialogWidth:700px;dialogHeight:450px;resizable:yes;";	

		
		 if(!$('#empNo').val()) {
			 	alert("<spring:message code='msg.web.error.mder'/>");
				$('#findEmpNo').focus();
				return false;
		 }	

		
		param.venCd = "";
		param.venNm = "";
		param.empNo = $('#empNo').val();
		
		window.showModalDialog(site, param, style);
		
		if(param && param.venCd){ 
		
			$('#vendCd').val(param.venCd);
			$('#vendCdOrg').val(param.venCd);
			$('#vendNm').val(param.venNm);
		}
		
		return false;
	
	
 }	
	
	
 //검색된 업체코드 값 설정
 function setVendorInto(vendorId,vendorName){
	 
	 $("#vendCd").val(vendorId);
	 $("#vendNm").val(vendorName);
 }
 
 
 //검색된 업체코드 값 지움
 function delVendorInto(){
	 
	 $("#vendCd").val('');
	 $("#vendCdOrg").val('');
	 $("#vendNm").val('');
 }
		
		
/*----------------------------------------------------------------------------------------------*/




//날짜를 입력 하면 오늘 날짜로부터 숫자만큼 전날의 날짜를 mm/dd/yyyy 형식으로 돌려 준다.
function caldate(day){

	var caledmonth, caledday, caledYear;
	var loadDt = new Date();
	var v = new Date(Date.parse(loadDt) - day*1000*60*60*24);

	caledYear = v.getFullYear();

	if( v.getMonth() < 10 ){
		caledmonth = '0'+(v.getMonth()+1);
	}else{
		caledmonth = v.getMonth()+1;
	}

	if( v.getDate() < 10 ){
		caledday = '0'+v.getDate();
	}else{
		caledday = v.getDate();
	}
	return caledYear+'-'+caledmonth+'-'+caledday;

}



 
 //업체 조회
 function doSearch(){

	 if(!dateValid()) return;


	 if(!$.trim($('#vendCd').val())){
		 var name = "<spring:message code='text.web.field.venCd'/>";
	     alert('<spring:message code="msg.common.error.required" arguments="' + name + '"/>');
	     
	     $("#vendCd").focus();
		 return;
	 }  


	 var ven_cd= $("#vendCd").val(); 
	 var startDate =$("#startDate").val();
	 var endDate =$("#endDate").val();
	 

	 
	 startDate=startDate.replace(/-/g, '');
	 endDate=endDate.replace(/-/g,'');
	 
	 
	 
	 
	 if(!$('#empNo').val()) {
			alert("<spring:message code='msg.web.error.mder'/>");
			$('#findEmpNo').focus();
			return false;
	 }	

	 
	 var str = {"venCd": ven_cd,
			    "empNo"		:   $("#empNo").val(),
			    "startDate":startDate ,
			    "endDate":endDate , 
			    "prodCd":$("#prodCd").val(),
			    "strCd": $("#strCd").val(),
			    "areaCd"	:   $("#areaCd").val(),
			    "page"	: $("#page").val(),
			    "pageRowCount"	: $('input[name="pageRowCount"]:radio:checked').val()
	  		  };
	 

	//loadingMaskFixPos();
		 
	$.ajaxSetup({
 		contentType: "application/json; charset=utf-8" 
		});
	$.post(
			"<c:url value='/edi/weborder/NEDMWEB0230Select.do'/>",
			JSON.stringify(str),
			function(data){
				if(data == null || data.state != "0"){
					alert("<spring:message code='msg.web.error.srchError' arguments='"+data.state+"'/>");	 
					//hideLoadingMask();
					return;
				}else{
					_setTbodyMasterValue(data);
				}
				//hideLoadingMask();
			},	"json"
		); 
	} 
	
 
 

	function dateValid(){
		
		var rangeDate = 0;
		var resultFlag = true;
		
		var startDate =	$('#startDate').val();
		var endDate  = $('#endDate').val();
 
	    var sd =new Date(startDate);
	    var ed =new Date(endDate);

  
	     //날짜 필수값 체크
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			resultFlag =  false;
		}

		startDate=startDate.replace(/-/g, '');
		endDate=endDate.replace(/-/g,'');

		var intStartDate = parseInt(startDate);
	    var intEndDate = parseInt(endDate);
				
   
	    //시작일보다 작은지 여부 체크
	     if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        resultFlag =  false;
	    } 

	    
	

	     rangeDate=Date.parse(ed)-Date.parse(sd);
		 rangeDate=Math.ceil(rangeDate/24/60/60/1000);
	
		//검색기간이 30일 넘는지 여부 체크
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			resultFlag =  false ;
		}	
	    
	
		
		//미안함도 잊고 그댄 행복에 살텐데~~ 워오워오워오
		return resultFlag;
	}
	
	
 
	//업체view
 	function _setTbodyMasterValue(json){
	
 		_setTbodyInit();
 		var data = json.listData, eleHtml = [], h = -1, pagHtml = [], j = -1,eleHtmlSum=[];

 		if(data != null){
			var sz = json.listData.length;
			if (sz > 0) {
				var cnt=0;
				for ( var k = 0; k < sz; k++) {
					eleHtml[++h] = '<tr bgcolor=ffffff >' + "\n";
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;"  rowspan=2 align="center">'+ ++cnt +'</td>' + "\n";						
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center"><span title="'+data[k].regStsCdDetail+'" style=color:blue;>'+data[k].regStsCdNm+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td >&nbsp;['+data[k].strCd+'] '+data[k].strNm+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td >&nbsp;'+data[k].prodCd+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td  align="center">&nbsp;'+data[k].srcmkCd+'</td>' + "\n";
					
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="center">&nbsp;'+data[k].prodStd+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="right">&nbsp;'+data[k].ordIpsu+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="right">&nbsp;'+amtComma(data[k].rrlQty)+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" rowspan=2 align="right">&nbsp;<span style="color:red;">'+amtComma(data[k].stdProdPrc)+'</span>'+ "\n";
					eleHtml[++h] = "\t" + '<input type="hidden" id="regStsCd" name="regStsCd" value="'+data[k].regStsCd+'"></td>' + "\n";
					eleHtml[++h] = "\t" + '</tr>' + "\n";
					
					
					eleHtml[++h] = '<tr bgcolor=ffffff  >' + "\n";
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" align="left">&nbsp;['+data[k].venCd+'] '+data[k].venNm+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td style="border-bottom-style:double; border-color:#c0c0c0;" align="left" colspan=2 align="left"  class="dot_web0001"><span title="'+data[k].prodNm+'">&nbsp;'+data[k].prodNm+'</span></td>' + "\n";
					eleHtml[++h] = "\t" + '</tr>' + "\n";
					
				}	
			
				$("#datalist").append(eleHtml.join(''));
			}
		}else {
			_setTbodyNoResult($("#datalist"), 11, null );
		}
		
		
 		
 		var page =  json.paging.list;
		var pageSz = json.paging.list.length;
		
		if(pageSz > 0){
			for ( var m = 0; m < pageSz; m++) {
				if (page[m].pageNumber == '<<'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_prevend.gif" alt="처음" /></a>' + "\n";
				} else if (page[m].pageNumber == '<'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_prev.gif" alt="이전" /></a>' + "\n";
				}else if(page[m].pageNumber == '>'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_next.gif" alt="다음" /></a>' + "\n";
				}else if(page[m].pageNumber == '>>'){
					pagHtml[++j] = '<a href="javascript:;" class="btn" link="'+page[m].linkPageNumber+'"><img src="/images/page/icon_nextend.gif"  alt="마지막" /></a>' + "\n";
				}else{
					pagHtml[++j] = '<a href="javascript:;" class="'+page[m].cl+'" link="'+page[m].linkPageNumber+'" title="'+page[m].pageNumber+'">'+page[m].pageNumber+'</a>' + "\n";
				}
			}
			$("#paging").append(pagHtml.join(''));
		}
	}
	
 	

 	
 	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "<spring:message code='text.web.field.srchNodData'/>";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
	
 
	
	/* 목록 초기화 */
	function _setTbodyInit() {
		$("#datalist tr").remove();
		$("#paging").empty();
	}
	
	
	
	/* 권역별 점포코드 세팅 */
	function _storeSelectCodeOptionTag(commonMajorCode, toSelectTagID, firstOptionMessage, finallyMethod) {
		var str = { "majorCD": commonMajorCode };
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/CommonCodeHelperController/storeSelectList.do'/>",
				JSON.stringify(str),
				function(data){
					var json = _jsonParseCheck(data);
					if(json==null)
					{
						return;
					}
					$(toSelectTagID + " option").remove();
					if(firstOptionMessage == "none" || firstOptionMessage == null || firstOptionMessage == "null")
					{
						$(toSelectTagID).append(json);
					}
					else
					{
						$(toSelectTagID).append("<option value=''>" + firstOptionMessage + "</option>").append(json);
					}
					if(finallyMethod != null)
					{
						finallyMethod();
					}
				},	"text"
			);
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


	// 등록구분 상세조회
	function _viewMdState(obj){
		
		
		var message ="";
		 
		var regStsCd = $(obj).parent().parent().children('td:last').children('input[name="regStsCd"]').attr('value');
		
		if(regStsCd != '1') {
			message = "ERROR";
		}
		alert(message); 
		

		/* var message="";
		
		//alert("!!!"   +   $(obj).parent().attr('value'));
		var mdModCd =  $(obj).parent().parent().children('td:last').children('input[name="mdModCd"]').attr('value');	
		var ordCfmQty = $(obj).parent().parent().children('td:last').children('input[name="hdOrdCfmQty"]').attr('value');
		var ordQty = $(obj).parent().parent().children('td:last').children('input[name="hdOrdQty"]').attr('value');
		
		
		if(mdModCd == '01') {
			message = "발주승인 처리 중 조정(01)되었습니다.\n"+"- Mder 승인 전 발주량 변경(요청 : "+ordQty+"  조정 : "+ordCfmQty+")";
		} else if(mdModCd == '02') { 
			message = "발주승인 처리 중 삭제(02)되었습니다.\n"+"내용1 : Mder 승인 전 삭제 처리 후 발주확정\n"+"내용2 : 당일발주 변경에 의해 제외 처리 되었습니다.";
		} else if(mdModCd=='03'){
			message = "발주가 중단(03)된 상품입니다.\n"+"- 당일발주 변경에 의해 제외 처리 되었습니다.";
		}
		
		
		alert(message); */

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
			
			if(event.preventDefault){
				//  IE  
	        	event.preventDefault();
	    	} else {
	    		//  표준 브라우저(IE9, 파이어폭스, 오페라, 사파리, 크롬)
	        	event.returnValue = false;
			}
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
	
	
	// 로딩바 감추기
	function hideLoadingMask(){
		$('#loadingLayer').remove();
		$('#loadingLayerBg').remove();
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
	
	
	
	
	function _eventClearMder(){
		$('#findEmpNo').val('');
		$('#findEmpNm').val('');
		$('#empNo'  ).val('');
	}
	//MDer find
	function _eventMDerFind(){
		var empNo = $("#findEmpNo").val();	 // 검색사번
	
		<%-- 조회조건 검증 ============================= --%>
	/* 	if(!$.trim(empNo) || empNo.length != 9 ){
			alert("사번 9자리를 정확히 입력하세요!");
			$("#findEmpNo").focus();
			return false;
		} */
		if(!$.trim(empNo)){
			alert("<spring:message code='msg.web.error.empNo'/>");
			$("#findEmpNo").focus();
			return false;
		}
		<%-- ----------------------------------------- --%>
		
		$('#findEmpNm').val('');
		$('#empNo'  ).val('');
		
		//loadingMaskFixPos();
		
		
		var str = {  "empNo" 	: empNo	//검색조건  
			      };
	
			$.ajaxSetup({
		  		contentType: "application/json; charset=utf-8" 
				});
			$.post(
					"<c:url value='/edi/weborder/NEDMWEB0099SearchEmplPool.json'/>",
					JSON.stringify(str),
					function(data){
						
						if(data == null || data.state !="0"){
							alert("<spring:message code='msg.web.error.empNoError' arguments='"+data.state+"'/>");
							//hideLoadingMask();
						 	return;
						}
						else
						{
							var empPool = data.empPool;
							
							if(empPool ==null || !empPool.empNo) {
								alert("<spring:message code='msg.web.error.noDataEmpNo'/>");
								//hideLoadingMask();
							 	return;
							}
							
							$('#findEmpNm').val(empPool.empNm);
							$('#empNo'  ).val(empPool.empNo);
							
							
						}
						//hideLoadingMask();
					},	"json"
			);
	
	}

</script>
<style>
.dot_web0001 span{width:135px; height:18px; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden;}

.page { float:right;  margin-top: 0px; padding:0 0 0 0; text-align:center; }
.page img { vertical-align:middle;}
.page a { display:inline-block; width:15px; height:11px; padding:4px 0 0; text-align:center; border:1px solid #efefef; background:#fff; vertical-align:top; color:#8f8f8f; line-height:11px;}
.page a.btn,
.page a.btn:hover { width:auto; height:auto; padding:0; border:0; background:none;}
.page a.on,
.page a:hover {background:#518aac; font-weight:bold; color:#fff;}
</style>
</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value='${param.widthSize }'/></c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value='${param.widthSize }'/>" > 
		<input type="hidden" name="page" id="page" value="1" />
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">	
						<li class="tit"><spring:message code='text.web.field.searchCondition'/></li>
						<li class="btn">
						<span style="margin-right: 20px;">
						  <span style="color: #c84545; font-size:0.95em; font-weight:"><spring:message code='text.web.field.confMder'/></span>
						  <input type="text" 	id="findEmpNo" maxlength="9"  style="border:1px solid #E0E0E0; font-size:1.0em; color:#488ce7; height: 18px; width: 60px; ime-mode:disabled;">
						  <input type="text" disabled="disabled" id="findEmpNm"  style="width:60px; border:1px solid #E0E0E0; color:4A4A4A; background-color:#EEECEA;">
						  <span id="btnEmpClear"> <img src="/images/epc/btn/icon_01.png" class="middle" style="cursor:pointer;" title="삭제" /></span>
						  <input type="hidden"  id="empNo">
						</span>				
						<a href="#" class="btn" id="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>					 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				
					<colgroup>
		
				
						<col style="width:15%" />
						<col style="width:35%" />	
						
						<col style="width:15%" />
						<col style="width:6.2%" />
						<col style="width:16%" />
						<col style="width:2%" />
						<col style="width:4%" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='text.web.field.rtnProcDt'/> </th>
						<td >
							<input type="text" class="day" id="startDate" name="startDate" readonly value="" style="width:80px;" ><img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate"   name="endDate" readonly value="" style="width:80px;" ><img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						
										
					
					 <th><span class="star">*</span> <spring:message code='text.web.field.venCd'/></th>
					 <td><input type="text" 	 id="vendCd" 	name="vendCd" 	maxlength="6" style="width: 50px; " onkeypress="onlyNumber();">
				 		 <input type="hidden" id="vendCdOrg" name="vendCdOrg">
				   	 </td>
					 <td><input type="text" id="vendNm" name="vendNm" style="width: 100%;" class="inputReadOnly" readonly="readonly" ></td>
					 <td><span id="btnVendCdDel"> <img src="/images/epc/btn/icon_01.png" class="middle" style="cursor:pointer;" title="삭제" /></span></td>
					 <td><span id="btnVendCdfind"><img src="/images/epc/btn/icon_02.png" class="middle" style="cursor:pointer;" title="협력사찾기팝업" /></span></td>
							
		
					
					<tr>
						<th><spring:message code='text.web.field.areaCd'/></th>
						<td>
							<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="전체"  />
							<select name="strCd" id="strCd" style="width:120px">
								<option value=""><spring:message code='text.web.field.searchALL'/></option>
							</select>
						</td>
						<th><spring:message code='text.web.field.prodCd'/></th>
						<td colspan=4>
							<input type="text" id="prodCd" name="prodCd" value="<c:out value='${paramMap.prodCd}'/>">
						</td>		
					
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			
			
		
			<!--	2 검색내역 	-->		
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='text.web.field.rtnList'/> </li>
					</ul>
					
			
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
                   	   <colgroup>
							<col width="31px"/>
							<col width="60px"/>	
							<col width="180px"/>
							<col width="100px"/>
							<col width="*"/>			
							<col width="50px"/>
							<col width="50px"/>
							<col width="80px"/>
							<col width="100px"/>
							<col width="18/">
						</colgroup>	
						
						<thead>
                        <tr bgcolor="#e4e4e4" align=center> 
                          <th rowspan=2 ><spring:message code='epc.web.header.No'/></th>
                          <th rowspan=2><spring:message code='epc.web.header.gb'/></th>
	                      <th ><spring:message code='epc.web.header.strNm'/></th>
	                      
	                      <th><spring:message code='epc.web.header.prdCd'/></th>
                          <th><spring:message code='epc.web.header.buyCd'/></th>
                          <th rowspan=2><spring:message code='epc.web.header.standard'/></th>
                          <th rowspan=2><spring:message code='epc.web.header.available'/></th>
                          <th rowspan=2><spring:message code='epc.web.header.qty'/></th>
                          <th rowspan=2><spring:message code='epc.web.header.amt'/></th>
	                      <th rowspan=2></th>
                        </tr>
                        <tr bgcolor="#e4e4e4" align=center> 
                          <th ><spring:message code='text.web.field.venNm'/></th>
	                      <th colspan=2><spring:message code='text.web.field.prodNm'/></th>
                        </tr>
                        </thead>
                        
                        
                         <tr> 
                         <td colspan=12>   
                         <div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:384px; overflow-y: scroll; overflow-x: hidden">
		                 <table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
                        	<colgroup>
                        	<col width="30px"/>
							<col width="60px"/>	
							<col width="180px"/>
							<col width="100px"/>
							<col width="*"/>			
							<col width="50px"/>
							<col width="50px"/>
							<col width="80px"/>
							<col width="100px"/>
							</colgroup>	
                        	<tbody id="datalist" />
                        </table>
                        </div>
                        </td>
                        </tr>
                        
                        
					</table>

			</div>		
		</div>
		
		<!-- Paging start ----------------------------------------------------->
		<div class="bbs_search" style="margin-top: 1px;">
		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
		<tr><td height="20">
		<span><strong>PageRow : </strong>
			<input type="radio" name="pageRowCount" id="pageRowCount" value="20" checked="checked">20
			<input type="radio" name="pageRowCount" id="pageRowCount" value="40">40
			<input type="radio" name="pageRowCount" id="pageRowCount" value="60">60
			<input type="radio" name="pageRowCount" id="pageRowCount" value="80">80
			<input type="radio" name="pageRowCount" id="pageRowCount" value="100">100
		</span>
		<div  align="center" style="margin-right: 50px; font-weight: bold;" id="paging" class="page"></div>
		</td></tr>
		</table>
		</div>
		<!-- Paging end ----------------------------------------------------->
		
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
					<li><spring:message code='epc.web.menu.lvl1'/></li>
					<li><spring:message code='epc.web.menu.lvl2'/></li>
					<li><spring:message code='epc.web.menu.mder'/></li>
					<li class="last"><spring:message code='epc.web.menu.rtnList'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
<font color='white'><b>PEDMPRO0003.jsp</b></font>

</html>
