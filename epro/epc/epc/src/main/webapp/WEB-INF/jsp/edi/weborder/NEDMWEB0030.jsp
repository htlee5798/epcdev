<%--
- Author(s): 
- Created Date: 2014. 08. 14
- Version : 1.0
- Description : 발주 일괄 등록

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
		<%-- 엑셀 에러 message 설정 --%>
		<c:if test="${stateCode eq 'fail-001'}">
			alert("<spring:message code='msg.web.error.fail001'/>");
		</c:if>
		<c:if test="${stateCode eq 'fail-002'}">
			alert("<spring:message code='msg.web.error.fail002'/>");
		</c:if>
		<c:if test="${stateCode eq 'fail-003'}">
			("<spring:message code='msg.web.error.fail003'/>");
		</c:if>
		<c:if test="${stateCode eq 'fail-004'}">
			alert("<spring:message code='msg.web.error.fail004'/>");
		</c:if>
		<c:if test="${stateCode eq 'fail-005'}">
			alert("<spring:message code='msg.web.error.fail005'/>");
		</c:if>
		<c:if test="${stateCode eq 'fail-006'}">
			alert("<spring:message code='msg.web.error.fail006'/>");
		</c:if>
		<%-- 저장 후 message 설정 --%>
		<c:if test="${stateCode eq 'suc'}">
			alert("<spring:message code='msg.web.error.insertSuc'/>");
			_eventSearch();
		</c:if>
		<c:if test="${stateCode eq 'insert-fail'}">
			alert("<spring:message code='msg.web.error.insertFail'/>");
		</c:if>
		<c:if test="${stateCode eq 'data-error'}">
			alert("<spring:message code='msg.web.error.dataError'/>");
		</c:if>

		_init();
		
		/* BUTTON CLICK 이벤트 처리 ----------------------------------------------------------------------------*/
		$('#btnExcelUpload').unbind().click(null,	_eventExcelUpload);  	// 엑셀 업로드 이벤트
		$('#btnExcel').unbind().click(null,			_eventExcel);  			// 엑셀 다운로드 이벤트
		$('#btnSearch').unbind().click(null,		_eventSearch); 			// 조회 이벤트
		$('#btnDelete').unbind().click(null,		_eventDelete); 			// 삭제 이벤트
		$('#btnSave').unbind().click(null,		    _eventSave); 			// 저장 이벤트
		
		
		/*-----------------------------------------------------------------------------------------------------*/
		
		// 권역구분, 점포코드 , 협력업체코드, 작업구분 enter key이벤트 --------------
		$('#areaCd,, #strCd, #entpCd, #prodCd, #fileGrpCd, input[name=uploadGb], input[name=pageRowCount]').unbind().keydown(function(e) {
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
		$("#entpCd").change(function () {
			var _majorCD = $("#entpCd").val();
			_excelSelectCodeOptionTag(_majorCD, "#fileGrpCd", "선택");
			
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
		
		
		_excelSelectCodeOptionTag($('#entpCd').val(), "#fileGrpCd", "선택");
	});
	
	// 엑셀 헬프 팝업 호출
	function _goHelpPopUp(){
		PopupWindow("<c:url value='/edi/weborder/NEDMWEB00991Popup.do'/>");
		
		
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
		_setTbodyNoResult($("#datalist"), 	14, '<spring:message code="text.web.field.srchInit"/>');	// prod tBody 설정
	}
	
	
	// 저장
	function _eventSave(){
		var webOrdFrDt = $('#vendorWebOrdFrDt').val();
		var webOrdtoDt = $('#vendorWebOrdToDt').val(); 
		
		var nowTime = getCurrentTime();
		
		if( Number(nowTime) < Number(webOrdFrDt) || Number(nowTime) > Number(webOrdtoDt) ){
			alert("<spring:message code='msg.web.send.time2'/>");
			return;
		}
		
		if (!confirm('<spring:message code="msg.common.confirm.save"/>')) {
            return;
        }
		
		var venCd;
		
		if($('#schVenCd').val() == null || $('#schVenCd').val() == ""){
			venCd = $('#entpCd').val();
		}else {
			venCd = $('#schVenCd').val();
		}
		
		var str = { "venCd"	: 	venCd };
		
		loadingMaskFixPos();
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/edi/weborder/NEDMWEB0030tedOrdPackSave.json'/>",
				JSON.stringify(str),
				function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						hideLoadingMask();
						alert('<spring:message code="msg.common.success.save"/>\n'+"<spring:message code='msg.web.send.infoOrd'/>");
						_eventSearch();
					}else{
						alert(json);
						hideLoadingMask();
					}
	  			}, 'json'
			);
	}
	
	// 업로드파일 삭제
	function _eventDelete(){
		var packDivnCd = $('#fileGrpCd').val();
		
		if(packDivnCd == null || packDivnCd == ""){
			alert("<spring:message code='msg.web.error.selectDelFile'/>");	
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
				"<c:url value='/edi/weborder/NEDMWEB0030tedOrdPackDelete.json'/>",
				JSON.stringify(str),
				function(json){				
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.delete"/>');
						$('#fileGrpCd').val("");
						_eventSearch();
						_excelSelectCodeOptionTag($('#entpCd').val(), "#fileGrpCd", "선택");
					}else{
						alert(json);
					}
					hideLoadingMask();
	  			}, 'json'
			);
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
	
	// Excel File Upload
	function _eventExcelUpload(){
		var webOrdFrDt = $('#vendorWebOrdFrDt').val();
		var webOrdtoDt = $('#vendorWebOrdToDt').val(); 
		
		var nowTime = getCurrentTime();
		
		if( Number(nowTime) < Number(webOrdFrDt) || Number(nowTime) > Number(webOrdtoDt) ){
			alert("<spring:message code='msg.web.send.time2'/>");
			return;
		}
		
		var form = document.excel;
		if($('#file').val() == null || $('#file').val() == ""){
			alert("<spring:message code='msg.web.error.selectInsFile'/>");
			return;
		}
		
		if(fileValueCheck($('#file').val())){
			loadingMaskFixPos();
			form.action  = '<c:url value="/edi/weborder/NEDMWEB0030newOrdExcelUpload.do"/>';
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
	    alert("<spring:message code='msg.web.error.fileType' arguments='"+filetype+"'/>");
	    
	    return false;
	}
	
	// 발주 일괄등록 정보 조회
	function _eventSearch(){
		var str = { "venCd" 		: $("#entpCd").val(), 
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
				"<c:url value='/edi/weborder/NEDMWEB0030tedOrdPackSelect.json'/>",
				JSON.stringify(str),
				function(data){
					hideLoadingMask();
					_setTbodyStoreOrdValue(data);
					//_excelSelectCodeOptionTag($('#entpCd').val(), "#fileGrpCd", "선택");
				},	"json"
			);
		
		
	} 
	
	// 발주 가능 점포 목록 리스트에 뷰
	function _setTbodyStoreOrdValue(json) {
		_setTbodyInit();

		var data = json.list, h = -1, eleHtml = [], ordCntSum = json.ordCnt; pagHtml = [], j = -1;
	    
		if(data != null){
			$("#searchForm #searchFlow").val("yes");
			var sz = json.list.length;
			if (sz > 0) {
				for ( var k = 0; k < sz; k++) {
					var cnt = k+1;
					if(data[k].regStsCd == '00'){
						eleHtml[++h] = '<tr bgcolor=ffffff >' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+cnt+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].fileGrpCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">정상</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" style="display:none">'+data[k].strCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="dot_web0004_01"><span title="['+data[k].strCd+']'+data[k].strNm+'">['+data[k].strCd+']'+data[k].strNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].prodCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].srcmkCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="left" class="dot_web0004_02"><span title="'+data[k].prodNm+'">'+data[k].prodNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].prodStd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right">'+data[k].ordIpsu+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].ordUnit+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="font-weight: bold;">'+amtComma(data[k].ordQty)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="color:red; font-weight: bold;">'+amtComma(data[k].ordTotQtySum)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" style="color:red; font-weight: bold;">'+amtComma(data[k].ordTotPrcSum)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '</tr>' + "\n";
					}else{
						eleHtml[++h] = '<tr bgcolor=ffffff>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+cnt+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">'+data[k].fileGrpCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center"><a href="javascript:;" onClick="_viewMdState()">오류</a></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" style="display:none">'+data[k].strCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="dot_web0004_01 through_tr"><span title="['+data[k].strCd+']'+data[k].strNm+'">['+data[k].strCd+']'+data[k].strNm+'</span></td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center" class="through_tr">'+data[k].prodCd+'</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="right" class="through_tr">'+amtComma(data[k].ordQty)+'&nbsp;</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '<td align="center">-</td>' + "\n";
						eleHtml[++h] = "\t" + '</tr>' + "\n";
					}
					cnt++;
				}
				
				for (var j=sz; j<18; j++) {
					eleHtml[++h] = '<tr bgcolor=ffffff style="height:20px;"><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>' + "\n";
				}
				
				$("#datalist").append(eleHtml.join(''));
			}
			
		}else {
			$("#searchForm #searchFlow").val("no");
			_setTbodyNoResult($("#datalist"), 14, null );
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
		
		$("#strCnt").text(amtComma(ordCntSum.strCnt));
		$("#prodCnt").text(amtComma(ordCntSum.prodCnt));
		
		if(ordCntSum.ordTotQtySum == null) $("#ordTotQtySum").text("");
		else $("#ordTotQtySum").text(amtComma(ordCntSum.ordTotQtySum));
		
		if(ordCntSum.ordTotAllQtySum == null) $("#ordTotQtySum").text("");
		else $("#ordTotAllQtySum").text(amtComma(ordCntSum.ordTotAllQtySum));
		
		if(ordCntSum.ordTotPrcSum == null) $("#ordTotQtySum").text("");
		else $("#ordTotPrcSum").text(amtComma(ordCntSum.ordTotPrcSum));
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
		$("#ordTotQtySum").text("");
		$("#ordTotAllQtySum").text("");
		$("#ordTotPrcSum").text("");
	}

	// 등록구분 상세조회
	function _viewMdState(){
		var message = "";
		
		message = "<spring:message code='msg.web.error.confirmData'/>";
		alert(message);
	}
	
	/* 협력 업체별 업로드 파일 검색*/
	function _excelSelectCodeOptionTag(commonMajorCode, toSelectTagID, firstOptionMessage, finallyMethod) {
		var str = { "majorCD": $('#entpCd').val() };
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/CommonCodeHelperController/excelUploadInfoList.do'/>",
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
	
	function _eventExcel() {
		
		if($("#searchForm #searchFlow").val() == "no"){
			alert("<spring:message code='text.web.field.srchNodData'/>");
			return;
		}
		
		var bodyValue = "";
		
		$("#searchForm #datalist > tr").each(function(){
			if($(this).find("td").eq(3).html() && $(this).find("td").eq(5).html() && $(this).find("td").eq(11).html()){
				bodyValue += "<tr>";
				bodyValue += "<td>" + $(this).find("td").eq(3).html() + "</td>";
				bodyValue += "<td>" + $(this).find("td").eq(5).html() + "</td>";
				bodyValue += "<td>" + $(this).find("td").eq(11).html() + "</td>";
				bodyValue += "</tr>";
			}
		});
		
		//console.log(bodyValue);
		
		$("#searchForm input[id='staticTableBodyValue']").val(bodyValue);
		
		$("#searchForm input[id='name']").val("statics");
		$("#searchForm").attr("target", "_blank");
		$("#searchForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
		$("#searchForm").submit();
		
		$("#searchForm").attr("target", "");
		$("#searchForm").attr("action", "");
		
	}
</script>

<style>
.dot_web0004_01 span{display:block;overflow:hidden;width:70px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}
.dot_web0004_02 span{display:block;overflow:hidden;width:112px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}

/* 오류 데이터 라인 + 글씨 Color 회색*/
.through_tr { font-style:italic; text-decoration:line-through; font-weight: bold; color:gray }

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
		<form id="excel" name="excel" method="post" enctype="multipart/form-data">
			<div class="wrap_search" style="padding-bottom: 10px;">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">
							<spring:message code='text.web.field.uploadInfo'/>
							<img src="/images/epc/btn/icon_03.png" alt="Notice" alt="Notice" id="excelHelp" style="cursor: pointer; " title="Help Page" /> 
							
						</li>
						<li class="btn">
							<a href="#" class="btn" id="btnExcelUpload"><span><spring:message code="button.common.create"/></span></a>
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
						<th><spring:message code='text.web.field.venCd'/></th>
						<td>
							<html:codeTag objId="entpCd" objName="entpCd" width="150px;" formName="form" selectParam="<c:out value='${param.entpCd}'/>" dataType="CP" comType="SELECT" />
						</td>
						<th><spring:message code='text.web.field.ordUploadFile'/></th>
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
		<form name="searchForm" id="searchForm" method="post" action="#">
		<input type="hidden" name="widthSize" id="widthSize" value="<c:out value='${param.widthSize }'/>" > 
		<input type="hidden" name="searchFlow" id="searchFlow" value="no" />
		<input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue">
		<input type="hidden" name="name" id="name">
		<input type="hidden" name="new_prod_id" id="new_prod_id">
		<input type="hidden" name="vencd" id="vencd">
		<input type="hidden" name="proGu" id="proGu"/>
		<input type="hidden" name="page" id="page" value="1" />
		<input type="hidden" name="schVenCd" id="schVenCd" />
		<input type="hidden" name="vendorWebOrdFrDt" id="vendorWebOrdFrDt" value="<c:out value='${paramMap.vendorWebOrdFrDt}'/>"/>
		<input type="hidden" name="vendorWebOrdToDt" id="vendorWebOrdToDt" value="<c:out value='${paramMap.vendorWebOrdToDt}'/>"/>
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">
							<spring:message code='text.web.field.searchCondition'/>
							<span style="font-weight: normal; color: #414fbb; margin-left: 60px;"><span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span>  <spring:message code='text.web.field.uploadDataDel'/></span>
							<span style="font-weight: normal; color: #414fbb; margin-left: 25px;"><img src="/images/epc/btn/icon_04.png" alt="Notice" />&nbsp; <spring:message code='msg.web.send.time'/></span>
						</li>
						<li class="btn">
							<a href="#" class="btn" id="btnSave"><span><spring:message code="button.common.save"/></span></a>
							<a href="#" class="btn" id="btnSearch"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" id="btnExcel"><span><spring:message code="button.common.excel"/></span></a>
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
						<th><spring:message code='text.web.field.areaCd'/></th>
						<td>
							<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="전체"  />
							<select name="strCd" id="strCd" style="width:120px">
								<option value="">전체</option>
							</select>
						</td>
						<th><spring:message code='text.web.field.prodCd'/></th>
						<td>
							<input type="text" id="prodCd" name="prodCd" value="<c:out value='${paramMap.prodCd}'/>">
						</td>
					</tr>
					<tr>
						<th><spring:message code='text.web.field.uploadFile'/></th>
						<td>
							<select style="width: 200px;" id="fileGrpCd"></select>&nbsp;&nbsp;<a href="#" class="btn" id="btnDelete"><span><spring:message code="button.common.delete"/></span></a> 
						</td>
						<th><spring:message code='text.web.field.uploadGb'/></th>
						<td>
							<input type="Radio" name="uploadGb" value="1" <c:if test="${paramMap.uploadGb eq '1'}"> Checked</c:if> /> <spring:message code='text.web.field.srchInit01403'/>
							<input type="Radio" name="uploadGb" value="2" <c:if test="${paramMap.uploadGb eq '2'}"> Checked</c:if> /> <spring:message code='text.web.field.srchInit01404'/>
							<input type="Radio" name="uploadGb" value="3" <c:if test="${paramMap.uploadGb eq '3'}"> Checked</c:if> /> <spring:message code='text.web.field.srchInit01405'/>
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
						<li class="tit"><spring:message code='text.web.field.venOrdInfo'/></li>
					</ul>
					 
					<table id="dataTable" width="100%;" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef>
                        <colgroup>
							<col width="29px"/>
							<col width="37px"/>
							<col width="38px"/>
							<col width="75px"/>
							<col width="74px"/>
							<col width="100px"/>
							<col width="*"/>
							<col width="61px"/>
							<col width="48px"/>
							<col width="48px"/>
							<col width="48px"/>
							<col width="48px"/>
							<col width="76px"/>
							<col width="18px"/>
						</colgroup>	
						<thead>
                        <tr bgcolor="#e4e4e4" align=center> 
                          <th rowspan="2"><spring:message code='epc.web.header.No'/></th>
	                      <th rowspan="2"><spring:message code='epc.web.header.fileNgb'/></th>
	                      <th rowspan="2"><spring:message code='epc.web.header.insertNstate'/></th>
	                      <th colspan="7"><spring:message code='epc.web.header.prdInfo'/></th>
	                      <th colspan="3"><spring:message code='epc.web.header.ordInfo'/></th>
	                      <th rowspan="3"></th>
                        </tr>
                        <tr bgcolor="#e4e4e4" align=center>
						  <th><spring:message code='epc.web.header.strNm'/></th>
                          <th><spring:message code='epc.web.header.prdCd'/></th>
                          <th><spring:message code='epc.web.header.buyCd'/></th>
                          <th><spring:message code='epc.web.header.prdNm'/></th>
                          <th><spring:message code='epc.web.header.standard'/></th>
                          <th><spring:message code='epc.web.header.available'/></th>
                          <th><spring:message code='epc.web.header.unit'/></th>
                          <th><spring:message code='epc.web.header.unitQty'/><br></th>
                          <th><spring:message code='epc.web.header.ea'/></th>
                          <th><spring:message code='epc.web.header.amt'/></th>
                        </tr>
                        <tr bgcolor="87CEFA" >
							<td align="center" colspan="3"><spring:message code='epc.web.header.sum'/></td>
							<td align="center"><spring:message code='epc.web.header.strCnt'/></td>
							<td align="right" colspan="3"><spring:message code='epc.web.header.prodCnt'/></td>
							<td align="center">-</td>
							<td align="center">-</td>
							<td align="center">-</td>
							<td align="right"><span id="ordTotQtySum"></span>&nbsp;</td>
							<td align="right"><span id="ordTotAllQtySum"></span>&nbsp;</td>
							<td align="right"><span id="ordTotPrcSum"></span>&nbsp;</td>
                    	</tr>
                        </thead>
                        <tr> 
	                     	<td colspan=14>   
	                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:275px; overflow-y: scroll; overflow-x: hidden">
	                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
	                     			<colgroup>
										<col width="28px"/>
										<col width="37px"/>
										<col width="38px"/>
										<col width="75px"/>
										<col width="74px"/>
										<col width="100px"/>
										<col width="*"/>
										<col width="61px"/>
										<col width="48px"/>
										<col width="48px"/>
										<col width="48px"/>
										<col width="48px"/>
										<col width="76px"/>
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
					<li><spring:message code='epc.web.menu.lvl1'/></li>
					<li><spring:message code='epc.web.menu.lvl2'/></li>
					<li><spring:message code='epc.web.menu.lvl3'/></li>
					<li class="last"><spring:message code='epc.web.menu.ordBatch'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
<font color='white'><b>PEDMWEB0003.jsp</b></font>

</html>
