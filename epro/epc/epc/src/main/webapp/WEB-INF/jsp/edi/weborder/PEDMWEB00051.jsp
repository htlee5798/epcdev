<%--
- Author(s): 
- Created Date: 2014. 08. 21
- Version : 1.0
- Description : 반품 일괄 등록

--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script  type="text/javascript" >

		/* 폼로드 */
		$(document).ready(function($) {		
			$("select[name='entp_cd']").val("<c:out value='${param.entp_cd}'/>");	// 협력업체 선택값 세팅
		});

	$(function() {
		<%-- 엑셀 에러 message 설정 --%>
		<c:if test="${stateCode eq 'fail-001'}">
			alert("숫자가 아닌 문자가 입력된 항목이 있습니다.\n수정 후 다시 업로드 하여 주십시요.");
		</c:if>
		<c:if test="${stateCode eq 'fail-002'}">
			alert("점포코드가 3자리를 초과 했습니다.\n수정 후 다시 업로드 하여 주십시요.");
		</c:if>
		<c:if test="${stateCode eq 'fail-003'}">
			alert("상품코드가 10자리를 초과 했습니다.\n수정 후 다시 업로드 하여 주십시요.");
		</c:if>
		<c:if test="${stateCode eq 'fail-004'}">
			alert("수량이 10자리를 초과 했습니다.\n수정 후 다시 업로드 하여 주십시요.");
		</c:if>
		<c:if test="${stateCode eq 'fail-005'}">
			alert("입력된 값이 없는 항목이 있습니다.\n확인 후 다시 업로드 하여 주시기 바랍니다.");
		</c:if>
		<c:if test="${stateCode eq 'fail-006'}">
			alert("엑셀 데이터가 10000건을 초과 하였습니다.\n확인 후 다시 업로드 하여 주시기 바랍니다.");
		</c:if>
		
		<%-- 저장 후 message 설정 --%>
		<c:if test="${stateCode eq 'suc'}">
			alert("정상적으로 저장 되었습니다.");
			_eventSearch();
		</c:if>
		<c:if test="${stateCode eq 'insert-fail'}">
			alert("저장중 오류가 발생 하였습니다.");
		</c:if>
		<c:if test="${stateCode eq 'data-error'}">
			alert("잘못된 형식의 파일 또는 파일을 찾을 수가 없습니다.\n엑셀 2007 이상에서 작업하신 경우에는 확장자를 xlsx로 바꾸신 후 업로드 하여 주시기 바랍니다.\n파일을 확인 한 후 다시 등록 하여 주십시요. ");
		</c:if>
		
		_init();
		
		/* BUTTON CLICK 이벤트 처리 ----------------------------------------------------------------------------*/
		$('#btnExcelUpload').unbind().click(null,	_eventExcelUpload);  	// 엑셀 업로드 이벤트
		$('#btnSearch').unbind().click(null,		_eventSearch); 			// 조회 이벤트
		$('#btnDelete').unbind().click(null,		_eventDelete); 			// 삭제 이벤트
		$('#btnRtnReq').unbind().click(null,		_eventRtnReq); 			// 반품요청 이벤트
		
		
		/*-----------------------------------------------------------------------------------------------------*/
		
		// 권역구분, 점포코드 , 협력업체코드, 작업구분 enter key이벤트 --------------
		$('#areaCd,, #strCd, #entp_cd, #prodCd, #fileGrpCd, input[name=uploadGb], input[name=pageRowCount]').unbind().keydown(function(e) {
			switch(e.which) {
	    	case 13 : $('#page').val("1"); _eventSearch(this); break; // enter
	    	default : return true;
	    	}
	    	e.preventDefault();
	   	});
		//-----------------------------------------------------------------
		
		// 권역 셀렉트 박스 변경시 점포 코드 변경
		$("#areaCd").change(function () {
			var _majorCD = $("#areaCd").val();
			_storeSelectCodeOptionTag(_majorCD, "#strCd", "전체");
			
		});
		
		// 협력업체 코드 변경시 업로드 파일 조회
		$("#entp_cd").change(function () {
			var _majorCD = $("#entp_cd").val();
			_excelRtnSelectCodeOptionTag(_majorCD, "#fileGrpCd", "선택");
			
		});
		
		
		/* 페이지번호 Click Event 발생시 조회함수 호출하다. */
		$('#paging a').live('click', function(e) {
			// #page : 서버로 보내는 Hidden Input Value 
			$('#page').val($(this).attr('link'));
			// 개발자가 만든 조회 함수
			_eventSearch();
		});
		
		// 엑셀 헬프 아이콘 클릭시 헬프 팝업 
		$( "#excelHelp" ).click(function() {
			_goHelpPopUp();
		});
		
		_excelRtnSelectCodeOptionTag($('#entp_cd').val(), "#fileGrpCd", "선택");
	});
	
	// 엑셀 헬프 팝업 호출
	function _goHelpPopUp(){
		PopupWindow("${ctx}/edi/weborder/PEDMWEB00991Popup.do");
	}
	
	function PopupWindow(pageName) {
		var cw=374;
		var ch=300;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=no");
	}
	
	/* 초기 설정 */
	function _init(){
		_setTbodyNoResult($("#datalist"), 	11, '지역/점포, 협력업체 코드, 상품코드, 업로드 파일/상태를  선택 또는 입력하여 [검색] 하세요!');	// prod tBody 설정
	}
	
	/* 반품 요청 : MARTNIS 전송하기*/
	function _eventRtnReq(){
		var webOrdFrDt = $('#vendorWebOrdFrDt').val();
		var webOrdtoDt = $('#vendorWebOrdToDt').val(); 
		
		var nowTime = getCurrentTime();
		
		if( Number(nowTime) < Number(webOrdFrDt) || Number(nowTime) > Number(webOrdtoDt) ){
			alert("발주/반품 가능 시간은"+'<spring:message code="msg.weborder.vendor.send.time"/>'+"까지 입니다.");
			return;
		}
		
		loadingMaskFixPos();
		var str = { "venCd" 	: $("#entp_cd").val() };
		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
		$.post(
				"<c:url value='/edi/weborder/tedRtnPackRequest.do'/>",
				JSON.stringify(str),
				function(data){
					if(data == null || data.state != "0"){
						if(data.message != null){
							alert(data.message);
						}else if(data.state == "1")
							alert("반품승인 전송대상(미전송, 오류) 상품정보가 없습니다.\n[CODE:"+data.state+"]");
						else
							alert("반품목록 승인요청 중 오류가 발생하였습니다.\n[CODE:"+data.state+"]");	
					}
					else  {
						
						alert("정상적으로 승인요청 처리 되었습니다.\n[ 정상:"+data.successCnt+" 오류:"+data.fallCnt+" 전체:"+data.totalCnt+" ]\n요청내역은 반품전체현황에서 확인 하실 수 있습니다.");
						_eventSearch();	//반품등록 현황 다시 조회
					} 
					hideLoadingMask();
				},	"json"
		);
	}
	
	// 저장
	function _eventSave(){
		if (!confirm('<spring:message code="msg.common.confirm.save"/>')) {
            return;
        }
		
		var venCd;
		
		if($('#schVenCd').val() == null || $('#schVenCd').val() == ""){
			venCd = $('#entp_cd').val();
		}else {
			venCd = $('#schVenCd').val();
		}
		
		var str = { "venCd"	: 	venCd };
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedOrdPackSave.do'/>",
				JSON.stringify(str),
				function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.save"/>\n'+"저장된 정보는 반품 전체 현황에서 확인 하실 수 있습니다.");
						_eventSearch();
					}else{
						alert(json);
					}
					hideLoadingMask();
	  			}, 'json'
			);
	}
	
	// 업로드파일 삭제
	function _eventDelete(){
		var packDivnCd = $('#fileGrpCd').val();
		
		if(packDivnCd == null || packDivnCd == ""){
			alert("삭제할 파일을 선택하여 주십시요.");	
			return;
		}
		
		if (!confirm('<spring:message code="msg.common.confirm.delete"/>')) {
            return;
        }
		
		var str = { "packDivnCd"	: 	packDivnCd };
	
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedRtnPackDelete.do'/>",
				JSON.stringify(str),
				function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.delete"/>');
						$('#fileGrpCd').val("");
						_eventSearch();
						_excelRtnSelectCodeOptionTag($('#entp_cd').val(), "#fileGrpCd", "선택");
					}else{
						alert(json);
					}
					hideLoadingMask();
	  			}, 'json'
			);
	}
	
	// Excel File Upload
	function _eventExcelUpload(){
		var webOrdFrDt = $('#vendorWebOrdFrDt').val();
		var webOrdtoDt = $('#vendorWebOrdToDt').val(); 
		
		var nowTime = getCurrentTime();
		
		if( Number(nowTime) < Number(webOrdFrDt) || Number(nowTime) > Number(webOrdtoDt) ){
			alert("발주/반품 가능 시간은"+'<spring:message code="msg.weborder.vendor.send.time"/>'+"까지 입니다.");
			return;
		}
		
		var form = document.excel;
		if($('#file').val() == null || $('#file').val() == ""){
			alert("등록할 파일을 선택하여 주십시요.");
			return;
		}
		
		if(fileValueCheck($('#file').val())){
			loadingMaskFixPos();
			form.action  = '<c:url value="/edi/weborder/prlExcelUpload.do"/>';
			form.submit();
		}
	}
	
	// 파일 확장자 체크
	function fileValueCheck(val){
	    var filename = val;
	    var filetype = filename.substring(filename.lastIndexOf(".") + 1);
	    
	    filetype = filetype.toUpperCase();
	    var filterStr = new Array("XLS", "XLSX");
	    for (var i = 0; i < filterStr.length; i++) {
	        if (filetype == filterStr[i]) {
	            return true;
	        }
	    }
	    alert("해당 파일의 확장자 " + filetype + "은(는) 업로드가 불가능합니다.");
	    return false;
	}
	
	// 반품 일괄등록 정보 조회
	function _eventSearch(){
		var str = { "venCd" 		: $("#entp_cd").val(), 
				    "strCd"			: $("#strCd").val(), 
				    "areaCd"		: $("#areaCd").val(), 
				    "prodCd"		: $("#prodCd").val(), 
				    "page" 			: $("#page").val(),
				    "packDivnCd"	: $('#fileGrpCd').val(),
				    "uploadGb"		: $('input[name="uploadGb"]:radio:checked').val(),
				    "pageRowCount"	: $('input[name="pageRowCount"]:radio:checked').val()
		};
	
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/tedRtnPackSelect.do'/>",
				JSON.stringify(str),
				function(data){
					_setTbodyStoreOrdValue(data);
					//_excelRtnSelectCodeOptionTag($('#entp_cd').val(), "#fileGrpCd", "선택");
					hideLoadingMask();
				},	"json"
		);
	} 
	
	// 반품 가능 점포 목록 리스트에 뷰
	function _setTbodyStoreOrdValue(json) {
		_setTbodyInit();

		var data = json.list, h = -1, eleHtml = [], rtnCntSum = json.rtnCnt; pagHtml = [], j = -1;
	    
		if(data != null){
			var sz = json.list.length;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					var cnt = k+1;
					eleHtml[++h] = '<tr bgcolor=ffffff >' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+cnt+'</td>' + "\n";
					eleHtml[++h] = "\t" + '<td align="center">'+data[k].fileGrpCd+'</td>' + "\n";
					if(data[k].regStsCd == '00'){
						eleHtml[++h] = "\t" + '<td align="center">정상</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="dot_web0005_01"><span title="['+data[k].strCd+']'+data[k].strNm+'">['+data[k].strCd+']'+data[k].strNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].prodCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].srcmkCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="left" class="dot_web0005_02"><span title="'+data[k].prodNm+'">'+data[k].prodNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right">'+data[k].ordIpsu+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="font-weight: bold;">'+amtComma(data[k].rrlQty)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="color:red; font-weight: bold;">'+amtComma(data[k].ordTotPrcSum)+'&nbsp;</td>' + "\n";
					}else{
						eleHtml[++h] = "\t" + '<td align="center" ><a href="javascript:;" onClick="_viewMdState()">오류</a></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="dot_web0005_01 through_tr"><span title="['+data[k].strCd+']'+data[k].strNm+'">['+data[k].strCd+']'+data[k].strNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="through_tr">'+data[k].prodCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" class="through_tr">'+amtComma(data[k].rrlQty)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
					}
					
					eleHtml[++h] = "\t" + '</tr>' + "\n";
					
					cnt++;
				}
				
				for (var j=sz; j<13; j++) {
					eleHtml[++h] = '<tr bgcolor=ffffff style="height:20px;"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' + "\n";
				}
				
				$("#datalist").append(eleHtml.join(''));
			}
			
		}else {
			_setTbodyNoResult($("#datalist"), 11, null );
		};
		
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
		
		$("#strCnt").text(amtComma(rtnCntSum.strCnt));
		$("#prodCnt").text(amtComma(rtnCntSum.prodCnt));
		
		if(rtnCntSum.ordTotAllQtySum == null) $("#ordTotQtySum").text("");
		else $("#ordTotAllQtySum").text(amtComma(rtnCntSum.ordTotAllQtySum));
		
		if(rtnCntSum.ordTotPrcSum == null) $("#ordTotQtySum").text("");
		else $("#ordTotPrcSum").text(amtComma(rtnCntSum.ordTotPrcSum));
	}
	
	/*목록 검색 결과 없을시  */
	function _setTbodyNoResult(objBody, col, msg) {
		if(!msg) msg = "조회된 데이터가 없습니다.";
		objBody.append("<tr><td bgcolor='#ffffff' colspan='"+col+"' align=center>"+msg+"</td></tr>");
	}
	
	/* 목록 초기화 */
	function _setTbodyInit() {
		$("#datalist tr").remove();
		$("#paging").empty();
		$("#ordTotAllQtySum").text("");
		$("#ordTotPrcSum").text("");
	}

	// 등록구분 상세조회
	function _viewMdState(){
		var message = "";
		
		message = "반품 불가상품 또는 상품(점포코드) 확인이 필요한 데이터 입니다.\n수정 후 해당 데이터건을 삭제 하신 후 다시 업로드 하여 주십시요.";
		alert(message);
	}
	
	/* 협력 업체별 업로드 파일 검색*/
	function _excelRtnSelectCodeOptionTag(commonMajorCode, toSelectTagID, firstOptionMessage, finallyMethod) {
		var str = { "majorCD": $('#entp_cd').val() };
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/CommonCodeHelperController/excelRtnUploadInfoList.do'/>",
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
	
	//금액 콤마 - value 계산용
	function amtComma(amt) {
	    var num = amt + '';
	    for(var regx = /(\d+)(\d{3})/;regx.test(num); num = num.replace(regx, '$1' + ',' + '$2'));
	    return num;
	}

	// 로딩바 감추기
	function hideLoadingMask(){
		$('#loadingLayer').remove();
		$('#loadingLayerBg').remove();
	}
	
	// 현재 시간 조회
	function getCurrentTime(){
		today = new Date(); 
		
		var newToDay;
		var hours = today.getHours();
		var minutes = today.getMinutes();
		
		if (hours == 0) hours = 24;
		if (hours <= 9) hours = "0" + hours;
		if (minutes <= 9) minutes = "0" + minutes;
		
		newToDay = hours+""+minutes;
		
		return newToDay;
	}
</script>

<style>
.dot_web0005_01 span{display:block;overflow:hidden;width:70px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}
.dot_web0005_02 span{display:block;overflow:hidden;width:112px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}

/* 오류 데이터 라인 + 글씨 Color 회색*/
.through_tr { font-style:italic; text-decoration:line-through; color:gray }


.btn_red_basket a	{display:inline-block; min-width:61px; _width:61px; line-height:20px; font-weight:bold; white-space:nowrap; text-align:center;}

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

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<form id="excel" name="excel" method="post" enctype="multipart/form-data">
			<div class="wrap_search" style="padding-bottom: 10px;">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">
							Excel 반품등록<span style="font-weight: normal; color: #414fbb; margin-left: 25px;"><span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span>&nbsp; 반품서를 Excel File로 업로드 할 경우 <span style="color: red;">점포코드/상품코드/발주량</span> 순으로 작성하여 업로드 하세요!</span>
							<img src="/images/epc/btn/icon_03.png" alt="Notice" alt="Notice" id="excelHelp" style="cursor: pointer; " title="Help Page" /> 
							
						</li>
						<li class="btn">
							<%-- <a href="#" class="btn" id="btnExcelUpload"><span><spring:message code="button.common.create"/></span></a> --%>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:35%" />
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" formName="form" selectParam="" dataType="CP" comType="SELECT" />
						</td>
						<th>반품서 Upload File</th>
						<td>
							<input type="file" id="file" name="file"/>
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
		</form>
		
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<input type="hidden" name="searchFlow" value="yes" />
		<input type="hidden" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" name="new_prod_id">
		<input type="hidden" name="vencd">
		<input type="hidden" name="proGu" />
		<input type="hidden" name="page" id="page" value="1" />
		<input type="hidden" name="schVenCd" id="schVenCd" />
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">
							검색조건
							<span style="font-weight: normal; color: #414fbb; margin-left: 25px;"><span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span>&nbsp;업로드 파일단위 반품 Data를 삭제 합니다.</span>
							<span style="font-weight: normal; color: #414fbb; margin-left: 25px;"><img src="/images/epc/btn/icon_04.png" alt="Notice" />&nbsp; 반품가능 시간은 <span style="color: red;"><spring:message code="msg.weborder.vendor.send.time.rtn"/> </span>입니다.</span>
						</li>
						<li class="btn">
							<%-- <a href="#" class="btn" id="btnRtnReq"><span><spring:message code="button.weborder.return.request"/></span></a> --%>
							<a href="#" class="btn" id="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">

					<colgroup>
						<col style="width:15%" />
						<col style="width:35%" />
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th>지역/점포</th>
						<td>
							<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="전체"  />
							<select name="strCd" id="strCd" style="width:120px">
								<option value="">전체</option>
							</select>
						</td>
						<th>상품코드</th>
						<td>
							<input type="text" id="prodCd" name="prodCd" value="${paramMap.prodCd}">
						</td>
					</tr>
					<tr>
						<th>업로드 파일</th>
						<td>
							<%-- <select style="width: 200px;" id="fileGrpCd"></select>&nbsp;&nbsp;<a href="#" class="btn" id="btnDelete"><span><spring:message code="button.common.delete"/></span></a> --%> 
						</td>
						<th>업로드 상태</th>
						<td>
							<input type="Radio" name="uploadGb" value="1" <c:if test="${paramMap.uploadGb eq '1'}"> Checked</c:if> /> 전체
							<input type="Radio" name="uploadGb" value="2" <c:if test="${paramMap.uploadGb eq '2'}"> Checked</c:if> /> 정상
							<input type="Radio" name="uploadGb" value="3" <c:if test="${paramMap.uploadGb eq '3'}"> Checked</c:if> /> 오류
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">반품등록 내역(Excel) List</li>
					</ul>
					 
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef>
                        <colgroup>
							<col width="32px"/>
							<col width="55px"/>
							<col width="55px"/>
							<col width="80px"/>
							<col width="74px"/>
							<col width="100px"/>
							<col width="*"/>
							<col width="50px"/>
							<col width="80px"/>
							<col width="100px"/>
							<col width="18px"/>
						</colgroup>	
						<thead>
                        <tr bgcolor="#e4e4e4" align=center> 
                          <th>No</th>
	                      <th>파일구분</th>
	                      <th>등록상태</th>
						  <th>점포</th>
                          <th>상품코드</th>
                          <th>판매코드</th>
                          <th>상품명</th>
                          <th>입수</th>
                          <th>반품수량(EA)</th>
                          <th>금액</th>
                          <th rowspan="2"></th>
                        </tr>
                        <tr bgcolor="87CEFA" >
							<td align="center" colspan="3">합계</td>
							<td align="center">총 <span style="color: red; font-weight: bold;" id="strCnt"></span>개점</td>
							<td align="center" colspan="3">총 <span style="color: red; font-weight: bold;" id="prodCnt"></span>개 상품</td>
							<td align="center">-</td>
							<td align="right" style="font-weight: bold; font-size: 11px;"><span id="ordTotAllQtySum"></span>&nbsp;</td>
							<td align="right" style="font-weight: bold; font-size: 11px;"><span id="ordTotPrcSum"></span>&nbsp;</td>
                    	</tr>
                        </thead>
                        <tr> 
	                     	<td colspan=11>   
	                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:316px; overflow-y: scroll; overflow-x: hidden">
	                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
	                     			<colgroup>
										<col width="31px"/>
										<col width="55px"/>
										<col width="55px"/>
										<col width="80px"/>
										<col width="74px"/>
										<col width="100px"/>
										<col width="*"/>
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
					<li>반품등록</li>
					<li class="last">반품 일괄 등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
<font color='white'><b>PEDMWEB00051.jsp</b></font>

</html>
