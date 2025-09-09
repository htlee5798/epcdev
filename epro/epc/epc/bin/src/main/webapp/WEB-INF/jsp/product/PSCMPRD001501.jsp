<%--
- Author(s): dhyun
- Created Date: 2016. 05. 04
- History : 2016.08.10	운영(SVN Revision 60513) Merge작업
- Version : 1.0
- Description : 대표상품코드 등록 팝업화면
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="lcn.module.common.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- PBOMPRD003401 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>

<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
		
	$(document).on("keyup","INPUT[NAME='SELL_PRC'], INPUT[NAME='CURR_SELL_PRC']", function(){
		$(this).val( $(this).val().replace(/[^0-9,]/gi,"") );
	});
	$(document).on("keyup","INPUT[NAME='PROFIT_RATE']", function(){
		$(this).val( $(this).val().replace(/[^0-9.]/gi,"") );
	});
	
	initInputData();
		
	//상세보기의 경우 
	$('#taxatDivnCd').val('${repProdCdList[0].TAXAT_DIVN_CD}');
	$('#vendorId').val('${repProdCdList[0].VENDOR_ID}');
	vendorChange();
	$('#repProdCd').val('${repProdCdList[0].REP_PROD_CD}');
	
	//input enter 막기
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
	});
    
	// 등록의 경우만 달력팝업,상품조회 팝업 연동
	if ('${IUFlag}' == 'insert') {
		
		// 타이틀 초기화
		$("div #p_title1 h1").text("대표상품코드 등록");
		
		$('#endDate').val('9999-12-31'); 
		
		//달력셋팅		
		$("#startDate, #endDate").attr("readonly", "readonly");	
		$('#btnStartDate, #startDate').click(function() {
			openCal('dataForm.startDate');
		});
		$('#btnEndDate, #endDate').click(function() {
			openCal('dataForm.endDate');
		});
			// 상품조회 팝업 연동
		$("#prodPopup, #prodCd, #prodNm").click(function() {
			// onOffYn : 01 = 온오프('Y') , 02,03 = 온라인('N')
			openProductOnOffNotDeal("S","N") ;
		});
		
	} else /* 수정 */{
		// 타이틀 초기화
		$("div #p_title1 h1").text("대표상품코드 수정");
		// 달력팝업버튼 비표시
		$("#btnStartDate").css('display','none');
		$("#btnEndDate").css('display','none');
		
		//적용일자 선택안되게 기본 셋팅
		$("#startDate").focus(function(){
			retrun;
		});
		$("#endDate").focus(function(){
			retrun;
		});
		
		// 링크태그 삭제
		$("#prodPopup").replaceWith($("#prodPopup").text());
	}
	
	// 대표판매코드 변경 이벤트
	$("#repProdCd").change(function() {
		setProfitRt($("#repProdCd > option:selected").text());
	});
	
	// 정상매가, 판매가 keyup 이벤트
	$("INPUT[NAME='SELL_PRC']").keyup(function(){
		var idx = $("INPUT[NAME='SELL_PRC']").index(this);
		addComma(idx);
	});
	$("INPUT[NAME='CURR_SELL_PRC']").keyup(function(){
		var idx = $("INPUT[NAME='CURR_SELL_PRC']").index(this);
		addComma(idx);
	});
	
	// 등록
	$('#save').click(function() { 
		doUpdate();	
	});

	// 닫기
	$('#close').click(function() {
		window.close();		
	});
	
	// 수정,등록이 실패했을 경우 다시 ,표시를 해주기 위해 사용
	$('#sellPrc').ajaxStart(function(){
		$("INPUT[NAME='SELL_PRC']").each(function(i){
			addComma(i);
		});
	});
	
	$(document).on("focusout","INPUT[NAME='CURR_SELL_PRC'], INPUT[NAME='SELL_PRC'] SELL_PRC",function(){
		var idx = $("INPUT[NAME='CURR_SELL_PRC']").index(this);
		checkPrice('curr',idx);
	});
	
	
}); // end of ready

/** ********************************************************
 * input 속성 셋팅
 sellPrc				정상매가
 currSellPrc			판매가
 ******************************************************** */
function initInputData() {
	$("input[name='SELL_PRC']").attr("maxlength", "10");
	$("input[name='CURR_SELL_PRC']").attr("maxlength", "10");
}

/** ********************************************************
 * 이익률 셋팅
 ******************************************************** */
function setProfitRt(data) {
	
	var tempStr = data.split("/");
	var profitRt =  jQuery.trim(tempStr[tempStr.length-1]);
	
	$("INPUT[NAME='PROFIT_RATE']").val(profitRt);
}

/** ********************************************************
 * 인터넷상품코드값 변경시 협력사값 변경
 ******************************************************** */
 function vendorChange() {
	var formQueryString = $('*', '#dataForm').fieldSerialize();
	var targetUrl = "";
	
	targetUrl = '<c:url value="/product/repProdCd/selectRepProdCdComboList.do"/>';
	
	$.ajax({
		async: false,
		type: 'POST',
		url: targetUrl,
		data: formQueryString,
		success: function(data) {
			try {
				comboCall( document.dataForm.repProdCd, data.repProdCdComboList);
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
} 
/** ********************************************************
 * 인터넷상품코드값 변경시 '변경전가격' 업데이트
 ******************************************************** */
function viewBeforeChange(){
	var formQueryString = $('*', '#dataForm').fieldSerialize();
	var targetUrl = "";
	
	targetUrl = '<c:url value="/product/repProdCd/selectOriginalData.do"/>';
	
	$.ajax({
		async: false,
		type: 'POST',
		url: targetUrl,
		data: formQueryString,
		success: function(data) {
			try {
				for( var i = 0 ; i < data.beforeChangePrc.length; i++ ){
					//---------------
					// 판매가
					//---------------
	// 				INPUT[NAME='SELL_PRC'], INPUT[NAME='CURR_SELL_PRC']
					$("INPUT[NAME='BEFORE_CURR_SELL_PRC']").eq(i).val(data.beforeChangePrc[i].CURR_SELL_PRC);
	// 				$("#before_CurrSellPrc").text(data.beforeChangePrc.CURR_SELL_PRC);
					//---------------
					// 이익률
					//---------------
					$("INPUT[NAME='BEFORE_PROFIT_RT']").eq(i).val(data.beforeChangePrc[i].PROFIT_RATE);
	// 				$("#before_profitRt").text(data.beforeChangePrc.PROFIT_RATE);
					//---------------
					// 프로모션 개수
					//  - 프로모션이 걸려 있는 상품은 글씨는 별색으로 바꾼다
					//---------------
					var promoCnt = data.beforeChangePrc[i].PROMO_CNT;
					
					if(promoCnt > 0 )
						$('#prodCd').attr('style','background-color:red;color:white');
				}				
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
	
}


// 정상매가, 판매가 3자리당 콤마 표시
function addComma(idx) {
	$("input[name='SELL_PRC']").eq(idx).val(addTxtComma($("input[name='SELL_PRC']").eq(idx).val()));
	$("input[name='CURR_SELL_PRC']").eq(idx).val(addTxtComma($("input[name='CURR_SELL_PRC']").eq(idx).val()));
}
//정상매가, 판매가 콤마 표시 제거
function removeAllComma() {
	$("input[name='SELL_PRC']").each(function(i){
		$("input[name='SELL_PRC']").eq(i).val(removeComma($("input[name='SELL_PRC']").eq(i).val()));
	});
	$("input[name='CURR_SELL_PRC']").each(function(j){
		$("input[name='CURR_SELL_PRC']").eq(j).val(removeComma($("input[name='CURR_SELL_PRC']").eq(j).val()));
	});
}

/** ********************************************************
 * 저장
 ******************************************************** */
function doUpdate() {
	var form = document.dataForm;
	
	if ('${IUFlag}' == 'update') /* 수정 */{
		if (!checkUpdateYn()) {
			return;
		}
	}
	
	if (!checkInput()) {
		return;
	}
	
	if ('${IUFlag}' == 'insert') /* 등록 */{
		if (!confirm('<spring:message code="msg.common.confirm.regist"/>')) {
			return;
		}

		// 금액 ,표시 제거
		removeAllComma();
		
		loadingMask();
		
		callAjaxByInsert('#dataForm', '<c:url value="/product/repProdCd/insertRepProdCd.do"/>', '#dataForm', 'POST');
	} else if ('${IUFlag}' == 'update') /* 수정 */{
		if (!confirm('<spring:message code="msg.common.confirm.save"/>')) {
			return;
		}
		
		// 금액 ,표시 제거
		removeAllComma();
		
		loadingMask();
		
		callAjaxByUpdate('#dataForm', '<c:url value="/product/repProdCd/updateRepProdCd.do"/>', '#dataForm', 'POST');
	}
}

function callAjaxByInsert(form, url, target, Type) {

	var formQueryString = $('*', '#dataForm').fieldSerialize();
	$.ajax({
		type: Type,
		url: url,
		data: formQueryString,
		success: function(json) {
			try {
				
				hideLoadingMask();
				
				// 권한에러 메시지 처리 조건문 
				if(jQuery.trim(json) == "accessAlertFail") {
					alert('<spring:message code="msg.common.error.noAuth"/>');
				} else {
					if(jQuery.trim(json) == "fail") {
						alert("등록이 실패하였습니다.");
					} else if(jQuery.trim(json) == "success") {
						alert("정상적으로 등록되었습니다.");
						opener.refreshSearch();
						window.close();
					}  else if(jQuery.trim(json) == "alert") {
						alert('상품의 행사기간 이후 원복 대표상품코드가 유효하지않습니다. 재지정해주세요.');
						opener.refreshSearch();
						window.close();
					} else {
						alert(jQuery.trim(json));
					}
				}
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
}

function callAjaxByUpdate(form, url, target, Type) {
	
	var formQueryString = $('*', '#dataForm').fieldSerialize();
	$.ajax({
		type: Type,
		url: url,
		data: formQueryString,
		success: function(json) {
			try {
				
				hideLoadingMask();
				
				// 권한에러 메시지 처리 조건문 
				if(jQuery.trim(json) == "accessAlertFail") {
					alert('<spring:message code="msg.common.error.noAuth"/>');
				} else {
					if(jQuery.trim(json) == "fail") {
						alert("저장이 실패하였습니다.");
					} else if(jQuery.trim(json) == "success") {
						alert("정상적으로 저장되었습니다.");
						opener.refreshSearch();
						window.close();
					} else {
						alert(jQuery.trim(json));
					}
				}
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
}

/** ********************************************************
 * 입력값체크
 ******************************************************** */
function checkUpdateYn() {
	
	var date = new Date();
	var yyyy = date.getFullYear().toString();
	var mm = (date.getMonth()+1).toString().length < 2 ? "0"+(date.getMonth()+1) : (date.getMonth()+1).toString();
	var dd = date.getDate().toString().length < 2 ? "0"+date.getDate() : date.getDate().toString();
	var today = yyyy+mm+dd;
	var applyStartDy ;
	
	// 적용시작일자가 현재날짜 전이거나 같은 경우, 또는 적용여부 Y인 경우는 삭제가 불가
 	 if (today >= ($('#startDate').val()).replaceAll("-", "")) {
		alert("적용시작일자가 현재일자 이전이거나 같은 경우 수정할 수 없습니다.");
		return;
	}  
	
	if ('${repProdCdList[0].APPLY_YN}' == 'Y') {
		alert("적용중인 대표상품코드는 수정할 수 없습니다.");
		return;
	}

	return true;
}

/** ********************************************************
 * 입력값체크
 ******************************************************** */
function checkInput() {
	
	 if(Common.isEmpty($.trim($('#prodCd').val())) || Common.isEmpty($.trim($('#prodNm').val()))){
		alert('<spring:message code="msg.common.error.required" arguments="인터넷상품코드"/>');
		return;
	} 
	 if (Common.isEmpty($.trim($('#repProdCd > option:selected').val()))) {
		alert("대표판매코드가 선택되지 않았습니다.");
		return;
	}
	/*
	 if(Common.isEmpty($.trim($('#sellPrc').val()))){
		alert('<spring:message code="msg.common.error.required" arguments="정상매가"/>');
		return;
	}
	*/ 
	   
	if(Common.isEmpty($.trim($("input[name='REQ_REASON_CONTENT']").val()))){
		alert('변경사유는 필수 항목입니다.');
		return;
	}
	
	if(Common.isEmpty($.trim($("input[name='CURR_SELL_PRC']").val()))){
		alert('<spring:message code="msg.common.error.required" arguments="판매가"/>');
		return;
	}
	
	if (Common.isEmpty($.trim($('#startDate').val()))) {
		alert("<spring:message code="msg.common.fail.nocalendar"/>");
		return false;
	}
	
	return true;
}

/**********************************************************
 * 시작일자 및 종료일자 유효성 체크
 ******************************************************** */
function checkFeday(startDt, endDt){
	
	var arrStartDt = new Array();
	arrStartDt[0] = startDt.substring(0,4);
	arrStartDt[1] = startDt.substring(4,6);
	arrStartDt[2] = startDt.substring(6,9);
	
	var arrEndDt = new Array();
	arrEndDt[0] = endDt.substring(0,4);
	arrEndDt[1] = endDt.substring(4,6);
	arrEndDt[2] = endDt.substring(6,9);
	
	arrStartDt[1] = (Number(arrStartDt[1]) - 1) + "";
	arrEndDt[1]   = (Number(arrEndDt[1]) - 1) + "";
	
	var sDate = new Date(arrStartDt[0], arrStartDt[1], arrStartDt[2]);
	var eDate = new Date(arrEndDt[0], arrEndDt[1], arrEndDt[2]);
	
	if ( sDate > eDate) {
		return false;
	}
	return true;
}

/**##################################################################################
 *	숫자만 입력 가능
 *	ex) style="ime-mode:disabled" onKeyPress="keyInNumberOnly(this);"
 * 
 **************************************************************************************
 */	    
function keyInNumberOnly(input) {
	var ime=input.style.imeMode;
	var strNow=ime;

	if(strNow != "disabled"){
		ime = "disabled";
	}

	if ((event.keyCode<48)||(event.keyCode>57)) {
		event.returnValue=false;
	}
}
 
function checkPrice(val,idx) {
	var sPrc	= parseInt(removeComma($("INPUT[name='SELL_PRC']").eq(idx).val()));				// 정상매가
	var bcPrc	= parseInt(removeComma($("INPUT[name='BEFORE_CURR_SELL_PRC']").eq(idx).val()));				// 정상매가
	var cPrc	= parseInt(removeComma($("INPUT[name='CURR_SELL_PRC']").eq(idx).val()));				// 정상매가
	
	var saleRate = 0;
	if (sPrc > 0 && cPrc > 0) {
		saleRate = Math.round((sPrc - cPrc) / sPrc * 100);
	}	
	
	$("INPUT[name='SALE_RATE']").eq(idx).val(saleRate + "%");
// 	$("#saleRate").text(saleRate + "%");
	
	if (val == "sell") {
		// 정상매가 입력
		if (sPrc != bcPrc) {
			alert("입력한 [정상매가]와 [변경전 판매가]가 상이합니다.");
			return;
		}		
	} else if (val == "curr") {
		// 판매가 입력
		saleRate = Math.round((bcPrc - cPrc) / bcPrc * 100);
		if (saleRate > 50) {
			alert("입력한 [판매가]가 [변경전 판매가] 대비 "+saleRate+"% 이상 할인된 가격입니다.");
			return;
		}
	}
}
//상점 팝업을 호출하기 위해서 필수로 필요하다.
function fnSetProduct(data) {
		
	// M이 아닌 모드
	$("#prodCd"    ).val(data.prodCdArr[0]);
	$("#prodNm"    ).val(data.prodNmArr[0]);
	$("#optnProdPrcMgrYn"    ).val(data.optnProdPrcMgrYnArr[0]);
	if( $("#optnProdPrcMgrYn"    ).val() == "Y" ){
		$("#msgs"    ).text("해당상품은 옵션별 가격관리를 하는 상품입니다.");
	}else{
		$("#msgs"    ).text("해당상품은 옵션별 가격관리를 하지 않는 상품입니다.");
	}
	$('#taxatDivnCd').val(data.taxatDivnCdArray[0]);
	$('#vendorId').val(data.vendorIdArr[0]);
//	$('#reqReasonContent').val(data.reqReasonContent[0]);
	tprStoreItemPrice();
	vendorChange();
	viewBeforeChange();
}

function tprStoreItemPrice(){
	var formQueryString = $('*', '#dataForm').fieldSerialize();
	var targetUrl = "";
	
	targetUrl = '<c:url value="/product/repProdCd/selectTprStoreItemPriceList.do"/>';
	
	$.ajax({
		async: false,
		type: 'POST',
		url: targetUrl,
		data: formQueryString,
		success: function(data) {
			try {
				createTableTag(data.tprStoreItemPriceList);
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});	
}
function createTableTag(data){
	$("#innerHtml").children().remove();
	innerHtmls = "";
	for( var i = 0 ; i < data.length; i++ ){
		if( i == 0 ){
			innerHtmls +="<tr>";
// 			innerHtmls +="	<td><input type='checkbox' name='ITEM_CD' value='" + data[i].ITEM_CD + "'></td>";
			innerHtmls +="	<td><input type='hidden' name='ITEM_CD' value='" + data[i].ITEM_CD + "'>"+data[i].PROD_NM+"</td>";
			innerHtmls +="	<td rowspan='"+data.length+"'>";
			
			innerHtmls +="       <input type='text' id='startDate' name='startDate' value='${repProdCdList[0].APPLY_START_DY}' style='width:35%;' class='day'  readonly />";
			innerHtmls +="       <a href='#' id='btnStartDate'><img src='${lfn:getString('"+system.cdn.static.path+"')}/images/epc/layout/btn_cal.gif' alt='' class='middle' /></a> ~ ";
			
													
			<c:set var="applyEndDy"/>
			<c:choose>
				<c:when test="${ empty repProdCdList[0].APPLY_END_DY }">
					<c:set var="applyEndDy" value="99991231"/>
				</c:when>
				<c:otherwise>
					<c:set var="applyEndDy" value="${repProdCdList[0].APPLY_END_DY}"/>
				</c:otherwise>
			</c:choose>
			innerHtmls +=" <input type='text' id='endDate' name='endDate' value='${applyEndDy}' style='width:35%;' class='day'  readonly />";
			innerHtmls +=" <a href='#' id='btnEndDate'><img src='${lfn:getString('"+system.cdn.static.path+"')}/images/epc/layout/btn_cal.gif' alt='' class='middle' /></a>";			
			innerHtmls +="	</td>";
			innerHtmls +="	<td><input type='text' name='REQ_REASON_CONTENT'  style='width:98%' value='' ></td>";
			innerHtmls +="	<td><input type='text' name='SELL_PRC'  style='width:98%' readonly value='"+data[i].SELL_PRC+"' ></td>";
			innerHtmls +="	<td><input type='text' name='CURR_SELL_PRC'  style='width:98%' value='"+data[i].CURR_SELL_PRC+"' ></td>";
			innerHtmls +="	<td><input type='text' name='SALE_RATE'  style='width:98%' value='' readonly></td>";
			innerHtmls +="	<td><input type='text' name='APPLY_YN'  style='width:98%' value='${repProdCdList[0].APPLY_YN}' readonly></td>";
			innerHtmls +="	<td><input type='text' name='PROFIT_RATE'  style='width:98%' value='"+data[i].PROFIT_RATE+"' readonly></td>";
			innerHtmls +="	<td><input type='text' name='BEFORE_CURR_SELL_PRC'  style='width:98%' value='' readonly></td>";
			innerHtmls +="	<td><input type='text' name='BEFORE_PROFIT_RT' style='width:98%' value='' readonly></td>";
			innerHtmls +="</tr>";
		}else{
			innerHtmls +="<tr>";
			innerHtmls +="	<td><input type='hidden' name='ITEM_CD' value='" + data[i].ITEM_CD + "'>"+data[i].PROD_NM+"</td>";
			innerHtmls +="	<td><input type='text' name='REQ_REASON_CONTENT'  style='width:98%' value='' ></td>";
			innerHtmls +="	<td><input type='text' name='SELL_PRC'  style='width:98%' readonly value='"+data[i].SELL_PRC+"' ></td>";
			innerHtmls +="	<td><input type='text' name='CURR_SELL_PRC'  style='width:98%' value='"+data[i].CURR_SELL_PRC+"' ></td>";
			innerHtmls +="	<td><input type='text' name='SALE_RATE'  style='width:98%' readonly></td>";
			innerHtmls +="	<td><input type='text' name='APPLY_YN'  style='width:98%' value='${repProdCdList[0].APPLY_YN}' readonly></td>";
			innerHtmls +="	<td><input type='text' name='PROFIT_RATE'  style='width:98%' value='"+data[i].PROFIT_RATE+"' ></td>";
			innerHtmls +="	<td><input type='text' name='BEFORE_CURR_SELL_PRC'  style='width:98%' readonly></td>";
			innerHtmls +="	<td><input type='text' name='BEFORE_PROFIT_RT' style='width:98%' readonly></td>";
			innerHtmls +="</tr>";		
		}
	}
	$("#innerHtml").append(innerHtmls);
	
/* 	$(".day").each(function(i){
		id = $(".day").eq(i).attr("id");
		openCals(id);
		$("#"+id).inputmask({
			mask: "y-1-2", 
			placeholder: "YYYY-MM-DD",
			leapday: "-02-29", 
			separator: "-", 
			alias: "yyyy/mm/dd"
		});
	}); */
		
	//달력셋팅		
	$('#btnStartDate').click(function() {
		openCal('dataForm.startDate');
	});
	
	$('#btnEndDate').click(function() {
		openCal('dataForm.endDate');
	});
}
</script>

</head>
<body>
<form name="dataForm" id="dataForm" >
<!-- hidden value -->
<c:set var="optnProdPrcMgrYn" value="${ IUFlag eq 'update' ? repProdCdList[0].OPTN_PROD_PRC_MGR_YN : '' }" />
<input type="hidden" id="optnProdPrcMgrYn" name="optnProdPrcMgrYn" value="${ optnProdPrcMgrYn }"/>
<input type="hidden" id="vendorId" name="vendorId"/>
<input type="hidden" id="taxatDivnCd" name="taxatDivnCd"/>

	<div id="wrap_menu">
		<!--	@ 검색조건	-->
	<div class="wrap_search">
	<!--  @title  -->
     <div id="p_title1">
		<h1>대표상품코드 등록/수정</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
		
		<div class="bbs_search">
    	<ul class="tit">
				<li class="tit">대표상품코드 등록/수정</li>
					<li class="btn">
					<span><font color="red" id="msgs">
						<c:choose>
							<c:when test="${ repProdCdList[0].OPTN_PROD_PRC_MGR_YN eq 'Y' }">
							해당상품은 옵션별 가격관리를 하는 상품입니다.
							</c:when>
							<c:when test="${ repProdCdList[0].OPTN_PROD_PRC_MGR_YN eq 'N' }">
							해당상품은 옵션별 가격관리를 하지 않는 상품입니다.
							</c:when>
							<c:otherwise></c:otherwise>					
						</c:choose>
						</font></span>
					<a href="#" class="btn" id="save"><span><spring:message code="button.common.save"/></span></a>
					<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
		</ul>
		
			<table border="0" cellpadding="0" cellspacing="0" class="bbs_search" summary="신상품코드검증" >
		        <col width="13%" />
		        <col width="40%" />
		        <col width="13%" />
		        <col width=" " />
		        <tr>
				  <th align="center"><label for="prodCd">인터넷상품코드</label></th>
				  <td>
				    <input type="text" name="prodCd" id="prodCd" class="validcheck"  value="${repProdCdList[0].PROD_CD}" class="inputRead"  size="15" readonly >
				    <input type="text" name="prodNm" id="prodNm" value="${repProdCdList[0].PROD_NM}" class="inputRead" size="35" readonly style="width:70%;">
				  </td>
				  <th align="center">대표판매코드</th>
				  <td>
						<select name="repProdCd" id="repProdCd" class="select" >
							<option value="">선택</option>
						</select> 
				  </td>				  
			  </tr>		
			  </table>
			  </div>
	<div class="wrap_con">
	<div class="bbs_list">
			  	<table border="0" cellpadding="0" cellspacing="0" class="bbs_search" summary="" >
			  		<colgroup>
<%-- 				  		<col width="4%" /> --%>
				  		<col width="*" />
				  		<col width="20%" />
				  		<col width="18%" />
				  		<col width="8%" />
				  		<col width="8%" />
				  		<col width="5%" />
				  		<col width="5%" />
				  		<col width="5%" />
				  		<col width="8%" />
				  		<col width="8%" />
			  		</colgroup>
			  		<thead>
			  			<tr>
<!-- 			  				<th>선택</th> -->
			  				<th>옵션상품</th>
			  				<th>적용시작일-적용종료일자</th>
			  				<th>변경요청사유 및 비고</th>
			  				<th>정상매가</th>
			  				<th>판매가</th>
			  				<th>할인율</th>
			  				<th>적용여부</th>
			  				<th>이익율</th>
			  				<th>변경전판매가</th>
			  				<th>변경전이익율</th>
			  			</tr>
			  		</thead>
			  		<tbody id="innerHtml">
			  			<c:choose>
			  				<c:when test="${ empty repProdCdList or fn:length(repProdCdList) > 0 }">
			  					<c:forEach items="${ repProdCdList }" var="repProdCdInfo" varStatus="idx">
			  						<c:choose>
				  						<c:when test="${ idx.index == 0 }">
											<tr>
											   <td><input type='hidden' name='ITEM_CD' value="${ repProdCdInfo.ITEM_CD }">${ repProdCdInfo.PROD_NM }</td>
											   <td rowspan="${ fn:length(repProdCdList) }">
											       <input type='text' id='startDate' name='startDate' value='${repProdCdInfo.APPLY_START_DY}' style='width:35%;' class='day'  readonly />
											       <a href='#' id='btnStartDate'><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> ~
				       							   <input type='text' id='endDate' name='endDate' value='${repProdCdInfo.APPLY_END_DY}' style='width:35%;' class='day'  readonly />
				       							   <a href='#' id='btnEndDate'><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
											   </td>
											   <td><input type='text' name='REQ_REASON_CONTENT'  style='width:98%' value="${ repProdCdInfo.REQ_REASON_CONTENT }"></td>
												<td><input type='text' name='SELL_PRC'  style='width:98%' value="${ repProdCdInfo.SELL_PRC }"></td>
												<td><input type='text' name='CURR_SELL_PRC'  style='width:98%' value="${ repProdCdInfo.CURR_SELL_PRC }"></td>
												<td><input type='text' name='SALE_RATE'  style='width:98%' value="" readonly></td>
												<td><input type='text' name='APPLY_YN'  style='width:98%' value="${repProdCdInfo.APPLY_YN}" readonly></td>
												<td><input type='text' name='PROFIT_RATE'  style='width:98%' value="${ repProdCdInfo.PROFIT_RATE }" readonly></td>
												<td><input type='text' name='BEFORE_CURR_SELL_PRC'  style='width:98%' value="" readonly></td>
												<td><input type='text' name='BEFORE_PROFIT_RT' style='width:98%' value="" readonly></td>
											</tr>
				  						</c:when>
				  						<c:otherwise>
											<tr>
												<td><input type='hidden' name='ITEM_CD' value="${ repProdCdInfo.ITEM_CD }">${ repProdCdInfo.PROD_NM }</td>
												<td><input type='text' name='REQ_REASON_CONTENT'  style='width:98%' value="${ repProdCdInfo.REQ_REASON_CONTENT }"></td>
												<td><input type='text' name='SELL_PRC'  style='width:98%' value="${ repProdCdInfo.SELL_PRC }"></td>
												<td><input type='text' name='CURR_SELL_PRC'  style='width:98%' value="${ repProdCdInfo.CURR_SELL_PRC }"></td>
												<td><input type='text' name='SALE_RATE'  style='width:98%' value="" readonly></td>
												<td><input type='text' name='APPLY_YN'  style='width:98%' value="${repProdCdInfo.APPLY_YN}" readonly></td>
												<td><input type='text' name='PROFIT_RATE'  style='width:98%' value="${ repProdCdInfo.PROFIT_RATE }" readonly></td>
												<td><input type='text' name='BEFORE_CURR_SELL_PRC'  style='width:98%' value="" readonly></td>
												<td><input type='text' name='BEFORE_PROFIT_RT' style='width:98%' value="" readonly></td>
											</tr>	
										</c:otherwise>
									</c:choose>
								</c:forEach>
			  				</c:when>
			  				<c:otherwise>
					  			<tr>
					  				<td colspan="9" style="text-align: center">상품을 선택하시기 바랍니다.</td>
					  			</tr>
			  				</c:otherwise>
			  			</c:choose>
			  		</tbody>
			  	</table>
			  	</div>
			  </div>
		</div>
	</div>
	
</form>
</body>
</html>