<%--
- Author(s): cwj
- Created Date: 2019. 12. 10
- Version : 1.0
- Description : 수수료율 변경요청
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
<%--@ taglib prefix="authutl" uri="/WEB-INF/tlds/authutl.tld" --%><%-- 버튼권한처리 태그 라이브러리 --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<% 
	String startDate  =DateUtil.formatDate(DateUtil.getToday(),"-");
	String endDate  =DateUtil.formatDate(DateUtil.getToday(),"-");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<!-- product/PSCMPRD0009 -->
<script type="text/javascript">

var rtnProdCdList = new Array();
var rtnCullSellPrcList = new Array();

//협력사 팝업
function popupVendor() {
	var targetUrl = '<c:url value="/vendorPopUp/vendorPopUpView.do"/>';
	Common.centerPopupWindow(targetUrl, 'prd', {width : 700, height : 500});
}
//협력업체 검색창으로 부터 받은 협력업체 정보 입력
function setVendorInto(vendorId, vendorName) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임
	$("#vendorId").val(vendorId);
	$("#vendorNm").val(vendorName);	   
}

/* function doExcel() {
	var today = Common.getToday().replace(/-/g, '');
	var xlsUrl = '<c:url value="/product/exportPBOMBRD0003Excel.do"/>';
	var hideCols = 'CHK|S_STATUS';
	directExcelDown(mySheet, '상품평게시판엑셀_'+today, xlsUrl, '#dataForm', null, hideCols); // 전체 다운로드 
}
 */
function ajaxSearchVendor() {
	
	var vendorId = $("#vendorId").val();
	if(vendorId.length < 6) return;
	
	var form = "#dataForm";
	var url = "<c:url value="/product/ajaxSearchVendor.do"/>";
	var target = "#dataForm";
	var Type = "POST";
	var formQueryString = $('*', form).fieldSerialize();
	
	$.ajax({
		type: Type,
		url: url,
		data: formQueryString,
		success: function(json) {
			try {
				//(json == null)? popupVendor() : $("#vendorNm").val(json);
				if(json == null){
					$("#vendorNm").val("");
					popupVendor();
				}
				else{
					$("#vendorNm").val(json);
				}
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
}	

 
 function ajaxSearchRepProductCd() {
		
		var vendorId = $("#vendorId").val();
		if(vendorId.length < 6) return;
		
		var form = "#dataForm";
		var url = "<c:url value='/product/repProdCd/selectRepProdCdComboList.do'/>";
		var target = "#dataForm";
		var Type = "POST";
		var formQueryString = $('*', form).fieldSerialize();
		
		$.ajax({
			type: Type,
			url: url,
			data: formQueryString,
			success: function(json) {
				try {
					//(json == null)? popupVendor() : $("#vendorNm").val(json);
					if(json == null){
						$("#vendorNm").val("");
						popupVendor();
					}
					else{
						$("#vendorNm").val(json);
					}
				} catch (e) {}
			},
			error: function(e) {
				alert(e);
			}
		});
	}	
 
	
function _eventSelectRepProdCd(val) {		
		
		if(val.length < 6) return;
		
		var form = "#dataForm";
		var url = "<c:url value='/product/repProdCd/selectRepProdCdComboList.do'/>";
		var target = "#dataForm";
		var Type = "POST";
		var formQueryString = $('*', form).fieldSerialize();
		
		$.ajax({
			type: Type,
			url: url,
			data: formQueryString,
			success: function(json) {
				var resultList	=	json.repProdCdComboList;
				if (resultList.length > 0) {
					
					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {		
						eleHtml[i] = "<option value='"+resultList[i].code+"'>"+resultList[i].name+"</option>"+"\n";				
					}	
					
					$("#l1Cd option").not("[value='']").remove();	//콤보박스 초기화 
					$("#l1Cd").append(eleHtml.join(''));
				} else {
					$("#l1Cd option").not("[value='']").remove();	//콤보박스 초기화 
				}			
			},
			error: function(e) {
				alert(e);
			}
		});
	}		
	
 
$(document).ready(function(){
	
	//----- 업체코드 변경시 이벤트
	$("#dataForm select[name=vendorId]").change(function() {
		_eventSelectRepProdCd($(this).val().replace(/\s/gi, ''));		
	});
	

    //달력셋팅
    $("#startDate, #endDate").attr("readonly", "readonly");
	$('#btnStartDate, #startDate').click(function() {
		openCal('dataForm.startDate');
	});
	$('#btnEndDate, #endDate').click(function() {
		openCal('dataForm.endDate');
	}); 

	//초기달력값 셋팅
	$("#endDate").val('${endDate}');
	$("#startDate").val('${startDate}');
	
	$("#chgApplySDy, #chgApplyEDy").attr("readonly", "readonly");
	$('#btnChgStartDate, #chgApplySDy').click(function() {
		openCal('dataForm.chgApplySDy');
	});
	$('#btnChgEndDate, #chgApplyEDy').click(function() {
		openCal('dataForm.chgApplyEDy');
	});
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "600px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번",					Type:"Int", 			SaveName:"NUM", 	 		 	 			Align:"Center", 	Width:33,	Edit:0}
	  , {Header:"",						Type:"CheckBox", 	SaveName:"CHK", 		         			Align:"Center", 	Width:33,	Edit:1}
	  //, {Header:"상품코드",			Type:"Text", 		SaveName:"prodCd", 	      	    Align:"Center", Width:80,	Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
	  , {Header:"상품코드",				Type:"Text", 			SaveName:"prodCd", 	      	    		Align:"Center", 	Width:80,	Edit:0}
	  , {Header:"단품코드",				Type:"Text", 			SaveName:"itemCd", 	            		Align:"Center", 	Width:50,	Edit:0}
	  , {Header:"상품명", 	        	Type:"Text", 			SaveName:"prodNm", 	            		Align:"Left",   		Width:140,	Edit:0} 
	  , {Header:"적용시작일자",			Type:"Text", 			SaveName:"applyStartDy",    			Align:"Center",	Width:80,	Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"적용끝일자",			Type:"Text", 			SaveName:"applyEndDy", 	  			Align:"Center",	Width:80,	Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"대표상품코드",			Type:"Text", 			SaveName:"repProdCd",    	 			Align:"Center", 	Width:80,	Edit:0}	  
	  , {Header:"대표상품코드명",			Type:"Text", 			SaveName:"repProdCdNm",    	 			Align:"Center", 	Width:80,	Edit:0}
	  //, {Header:"변경요청대표상품코드",	Type:"Text", 		SaveName:"chgbRepProdCd", 	  	Align:"Right",	Width:80,	Edit:0}
	  , {Header:"변경요청사유 및 비고", 			Type:"Text", 			SaveName:"reqReasonContent", 		Align:"Center",	Width:100,	Edit:0}
	  , {Header:"반려사유", 				Type:"Text", 			SaveName:"retnReason", 				Align:"Center",	Width:80,	Edit:0}
	  , {Header:"대분류",					Type:"Text",			SaveName:"l1Nm",							Align:"Center", 	Width:80,	Edit:0}
	  , {Header:"옵션별가격유무",		Type:"Text",			SaveName:"optnProdPrcMgrYn",		Align:"Center", 	Width:70,	Edit:0}
	  , {Header:"원가",					Type:"Int",				SaveName:"buyPrc",  	            		Align:"Right",		Width:60,	Edit:0,	Format:'#,###,###'}
	  , {Header:"매가",					Type:"Int",				SaveName:"sellPrc",         	    		Align:"Right",		Width:60,	Edit:0,	Format:'#,###,###'}
	  , {Header:"판매가",					Type:"Int",				SaveName:"currSellPrc",      				Align:"Right",		Width:60,	Edit:0,	Format:'#,###,###'}
	  , {Header:"이익율",					Type:"Int",				SaveName:"profitRate", 	      			Align:"Right",		Width:50,	Edit:0}
	  , {Header:"적용여부",				Type:"Text",			SaveName:"applyYn", 	      				Align:"Center", 	Width:50,	Edit:0,	Hidden: true}
	  , {Header:"승인여부",				Type:"Text",			SaveName:"admYn", 	      				Align:"Center", 	Width:50,	Edit:0}
	  ,	{Header:"등록일자",				Type:"Text",    		SaveName:"regDate", 		      			Align:"Center", 	Width:70,	Edit:0}
	  ,	{Header:"프로모션 개수",			Type:"Text",    		SaveName:"promoCnt", 		    		Align:"Center", 	Width:90,	Edit:0,	Hidden: true}
	  , {Header:"Status",				Type:"Status",		SaveName:"status", 		   				Align:"Center",	Width:50, 	Edit:0,	Hidden: true}
	  , {Header:"고유번호",				Type:"Int",				SaveName:"seqNo", 	      				Align:"Center", 	Width:33,	Edit:0,	Hidden: true}
	];
	
   
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();		

     // 등록버튼 클릭
    $('#create').click(function() {
	    popupWrite();     
     });      
   	
     //등록팝업
     function popupWrite(){    	
    	var targetUrl = '<c:url value="/product/repProdCd/viewInsertRepProdCdForm.do"/>';
    	Common.centerPopupWindow(targetUrl, '대표상품코드 등록화면', {width : 1300, height : 330});
    } 
	
});

     
//일괄등록팝업 
function popupGrpInsertForm() { 	 
	var targetUrl = '<c:url value="/product/repProdCd/viewGrpInsertForm.do"/>';
	Common.centerPopupWindow(targetUrl, 'viewGrpInsertForm', {
		width	: 1200,
		height	: 600,
		scrollBars : "NO"
	});
}


// 삭제 버튼 클릭 이벤트	
function deleteRow(){
	
	var rowCount = mySheet.GetTotalRows();
	var admYnChk = "";
	
	if(  mySheet.CheckedRows("CHK") == 0){
		alert("선택된 로우가 없습니다.");
		return;
	}
	
	if(rowCount > 0){
		for(var i=1; i<=rowCount; i++){
			if(mySheet.GetCellValue(i,"CHK") == "1"){
				admYnChk = mySheet.GetCellValue(i,"admYn");
				if(admYnChk == '승인' || admYnChk == '반려' || admYnChk == '삭제'){
					alert('승인전 상품코드만 삭제 가능합니다.');
					return;
				}
			}
		}
	}	

	if(!confirm("삭제하시겠습니까?")){
		return;
	} 
	
	var url = '<c:url value="/product/repProdCd/deleteRepProdCd.do"/>';
	
	mySheet.DoSave( url, {Quest:0});	
}


/**********************************************************
 * 재조회
 *********************************************************/
function refreshSearch() {
	doSearch();
}

 //삭제후 재 조회  
function mySheet_OnSaveEnd(Code, Msg) {
	alert(Msg);
	if (Code == 1) doSearch();
} 

//마우스 오버 이벤트(결과메세지:errorMsg) 인경우 링크처리
function mySheet_OnMouseMove(Button, Shift, X, Y) { 
	//마우스 위치가 2컬럼 일때만 마우스 손가락 모양
	if(mySheet.MouseCol() == 8) {
	    mySheet.SetMousePointer('Hand');
	} else {
	    mySheet.SetMousePointer('Default');
	}
}

function selectGrForm(){
	//초기달력값 셋팅
	$("#endDate").val('<%=startDate%>');
$("#startDate").val('<%=startDate%>');
	doSearch();
}

/** ********************************************************
 * 조회기간 체크
 ******************************************************** */
function doDateCheck()
{
    var form = document.dataForm;

    if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' ))
    {
        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
        return false;
    }
    
    return true;
}

function doSearch() {
	goPage('1');
}

// 페이징
function goPage(currentPage){
	
	//조회기간 체크
    if(!doDateCheck()) 
    {
        return;
    }

	var param = new Object();
	
	 // 로딩바 보이기
	/* var dataForm = document.dataForm; */
	var url = '<c:url value="/product/repProdCd/selectRepProdCdList.do"/>'; 

	// 검색조건
	param.startDate			= $('#startDate').val().replaceAll( '-', '');
	param.endDate			= $('#endDate').val().replaceAll( '-', '');	
	param.prodCd			= $('#prodCd').val();
	param.admYn				= $('#admYn').val();
	param.prodNm           = $('#prodNm').val();
	param.selDaeGoods	= $('#selDaeGoods').val();
	param.vendorId  		= $('#vendorId').val();		
	param.optnPrcYn  		= $('#optnPrcYn').val();
	
	// 페이지 변수
	param.rowsPerPage 	= $("#rowsPerPage").val();
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
	
}

// 데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd() {
	if(RETURN_IBS_OBJ.mode == "choice"){
		for(i = 1; i <= mySheet.GetTotalRows(); i++) {				
			mySheet.SetCellEditable(i, "chgbRepProdCd", true);
			mySheet.SetCellEditable(i, "chgReqReasonContent", true);
			mySheet.SetCellBackColor(i, "chgbRepProdCd", '#DAFEE3');
			mySheet.SetCellBackColor(i, "chgReqReasonContent", '#DAFEE3');
			
			for(var j=0; j<rtnProdCdList.length; j++){
				if(mySheet.GetCellValue(i, "prodCd") == rtnProdCdList[j]){
					mySheet.SetCellValue(i, "chgbRepProdCd", rtnCullSellPrcList[j]);
				}
			}
		}
		
		if(RETURN_IBS_OBJ.dupCount > 0) {
			alert("이미 변경 요청한 데이터가 존재하니 확인하여 주시기 바랍니다.");
		}
	}else{
		for(i = 1; i <= mySheet.GetTotalRows(); i++) {
			if(mySheet.GetCellValue(i, "aprvYn") == 'N') {
				mySheet.SetCellEditable(i, "chgbRepProdCd", true);
				mySheet.SetCellEditable(i, "chgReqReasonContent", true);
				mySheet.SetCellBackColor(i, "chgbRepProdCd", '#f0fff0');
				mySheet.SetCellBackColor(i, "chgReqReasonContent", '#f0fff0');
			}
		}
	}
	
	//조회 처리후 에러구분값으로 chk disable 처리
	//chkErrorCd();
	// 첫번째 상품의 RadioButton에 Check 헤드도 index잡힘 그래서 2임
	//mySheet.SetCellValue(2, "chk", true);
}

	//저장후 자동으로 호출되는 이벤트(승인처리후 재조회 처리)
	function mySheet_OnSaveEnd(code, Msg) {
		alert(Msg);
		doSearch();
	}

//상세조회 이벤트	
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	
		//특정 열을 클릭했을 때 다른 페이지로 이동하도록 처리
		//cwj 수정 팝업 기능 제한		
		/*if(Col==2){
			var prodCd =  mySheet.GetCellValue(Row, 2) ;
			var repProdCd =  mySheet.GetCellValue(Row, 7) ;
			var applyStartDy = mySheet.GetCellValue(Row,5);
			var applyEndDy = mySheet.GetCellValue(Row,6);
			var applyYn= mySheet.GetCellValue(Row,15);
			
			openDataWindows(prodCd, repProdCd, applyStartDy, applyEndDy, applyYn);
		} */
	}

//상세조회 팝업호출
	function openDataWindows(prodCd, repProdCd, applyStartDy, applyEndDy, applyYn){ 		
		var targetUrl = '<c:url value="/product/repProdCd/viewDetailRepProdCdForm.do"/>'+'?prodCd='+prodCd+
				'&repProdCd='+repProdCd+'&applyStartDy='+applyStartDy+'&applyEndDy='+applyEndDy+
				'&applyYn='+applyYn; 				

	if(applyYn == 'Y'){
		alert('담당자 승인이 되어 수정 할 수 없습니다.')
		return;
	}
	
		Common.centerPopupWindow(targetUrl, 'detail', {width : 1300, height : 330});
	}


	function goProductPopup() {
		
		var url = '<c:url value="/product/selectProductPopupView.do"/>';
		Common.centerPopupWindow(url, 'detail', {width : 800, height : 510});
	}
	
	function selectProductItemList(list) {	
		
		var form = document.dataForm;
		var param = new Object();
		var currentPage = 1;
		var url = '<c:url value="/product/selectProductItemList.do"/>';
		
		param.rowsPerPage 	= $("#rowsPerPage").val();
		param.mode = 'choice';

		alert('length : ' + list.prodCdList.length)
		for(var i=0; i<list.prodCdList.length; i++){
			rtnProdCdList.push(list.prodCdList[i]);
			rtnCullSellPrcList.push(list.cullSellPrcList[i]);
		}

		param.selectedProdCd =  String(list.prodCdList);
		
		loadIBSheetData(mySheet, url, currentPage, null, param);
	}
	
	function goChangePrc(){

		//var val1 = document.dataForm.l1Cd.value;
		var val1 = $("#l1Cd").val();
		var val2 = document.dataForm.input_changeReason.value;
		if(val1 != '' && val1 != '선택')
			setChangePrc(document.dataForm.l1Cd);
		if(val2 != '' && val2 != '변경요청사유')
			setChangeReason(document.dataForm.input_changeReason);
	}
	
	
	function setChangePrc(input){
		var chkFlg = false;
		var rowCount = mySheet.GetTotalRows();
		var i;
		if(rowCount > 0){
			for(i=1; i<=rowCount; i++){

				if(mySheet.GetCellValue(i,"CHK") == "1"){
					chkFlg = true;
					
					mySheet.SetCellValue(i, "chgbRepProdCd", input.value);
					mySheet.SetCellBackColor(i, "chgbRepProdCd", '#F6E5E2');

					mySheet.SetCellValue(i, "crud", "I");
				}
			}
		}
		
		if(!chkFlg){
			alert("일괄적용할 항목을 선택 하세요.");
		}
	}
	
	/** ********************************************************
	 * 번경요청사유 입력
	 ******************************************************** */
	function setChangeReason(input){

		var rowCount = mySheet.GetTotalRows();
		var i;
		if(rowCount > 0){
			for(i=1; i<=rowCount; i++){
				if(mySheet.GetCellValue(i,"CHK") == "1"){
					mySheet.SetCellValue(i, "chgReqReasonContent", input.value);
					mySheet.SetCellBackColor(i, "chgReqReasonContent", '#F6E5E2');
		
					mySheet.SetCellValue(i, "crud", "I");
				}
			}
		}
	}	
	
	
	function mySheet_OnChange(Row, Col, Value, OldValue) {
		if (Row == 0) return;
		
		if(mySheet.ColSaveName(Col) == 'chgbRepProdCd' || mySheet.ColSaveName(Col) == 'chgReqReasonContent'){
			mySheet.SetCellValue(Row, "crud", "I");
			mySheet.SetCellValue(Row, "CHK", "1");
		}
	}
</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">
<div class="content_scroll">
<form name="dataForm" id="dataForm">
<input type="hidden" name="parentFormName"  id="parentFormName"/>
<input type="hidden" name="prodInVal"  id="prodInVal"/>
<div id="wrap_menu">

	<!--	@ 검색조건	-->
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
				<!--당일등록조회 -->
					<a href='javascript:selectGrForm();' class='btn' id='tdySearch' ><span><spring:message code="button.rep.product.search"/></span></a>
					<a href='javascript:doSearch();'  class='btn' id='search' > <span><spring:message code="button.common.inquire"/></span></a>				
				</li>
			</ul>		

	        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	        <colgroup>
            	<col width="10%" />
            	<col width="24%" />
            	<col width="10%" />
            	<col width="24%" />
            	<col width="10%" />
            	<col width="24%" />
	        </colgroup>
          	<tr>
				<th>등록일자</th>
				<td>
					<input type="text" id="startDate" name="startDate" style="width:35%;" class="day" value="${startDate}" />
					<a href="#" id="btnStartDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
					~
					<input type="text" id="endDate" name="endDate" style="width:35%;" class="day" />
					<a href="#" id="btnEndDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>				
				<th>대분류</th>
				<td>
					<select class="select" name="selDaeGoods" id="selDaeGoods" style="width:150px;" onChange="javascript:fChange('dae');">
							<option value="">전체</option>
							<c:forEach items="${daeCdList}" var="code" begin="0">
 								<option value="${code.code }" ${code.code == daeCd ? "selected='selected'" : "" }>${code.name }</option>
 							</c:forEach>
						</select>
				</td>
				<th>승인여부</th>
				<td>
					<select name="admYn" id="admYn"  style="width:22%;">
						<option value="">전체</option>
						<option value="Y">승인</option>
						<option value="N">승인전</option>
						<option value="R">반려</option>
					</select>
				</td>		
			</tr>
			<tr>
			    <th>인터넷상품코드</th>
				<td>
					<input type="text" name="prodCd" id="prodCd" class="input"  style="width:35%;" >
				</td>
			    <th>상품명</th>
				<td>
					<input type="text" name="prodNm" id="prodNm" class="input"  style="width:38%;" >
				</td>
				<th>협력업체코드</th>
                <td>
					<c:choose>
						<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
							<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
							<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
						</c:when>
						<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
							<select name="vendorId" id="vendorId" class="select">
								<option value="">전체</option>
							<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
		                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
							</c:forEach>
							</select>
						</c:when>
					</c:choose>
				</td>
			</tr>
			<tr>
			    <th>옵션별개별가격유무</th>
				<td>
					<select name="optnPrcYn" id="optnPrcYn"  style="width:22%;">
						<option value="">전체</option>
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</td>
				<th></th>
                <td></td>			
				<th></th>
                <td></td>
			</tr>
          </table>
	    </div>
   	</div>   	
	<div class="wrap_con">
		<!-- list -->
	   	<div class="bbs_list">
	   		<ul class="tit">	      		
	      		<li class="tit" style="float:left;width:10%;">조회내역</li>
				<li class="btn" style="float:right;width:16%;">
					<a href='javascript:popupGrpInsertForm();' class="btn" ><span><spring:message code="button.rep.product.add"/></span></a>
					<a href=# class="btn" id="create" ><span><spring:message code="button.common.addproduct"/></span></a>					
					<a href='javascript:deleteRow();' class="btn" id="delete" ><span><spring:message code="button.common.delete"/></span></a>
				</li>	     
		  	</ul>
		  	<ul>
	         	<!-- 건수와 정렬개수를 세팅하기 위해 li의 ID는 페이징 div 부분의 ID에 Cnt를 붙여서 부여한다. -->
		    	<li class="lp" id="pagingDivCnt" />
		   	</ul>
			<ul> 
		  		<li class="lp">
				     <table class="bbs_list" border="0" cellpadding="0" cellspacing="0" id="sheetTbl">
			       		<tr>
			       			<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
			       		</tr>
			       	</table>
		       	</li>
	       	</ul> 
       </div>	
   </div>    
	   
   <!-- 페이징 DIV -->
   <div id="pagingDiv" class="pagingbox1" style="width: 100%;">
   		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
   </div>
   
</div>
</form>

</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location" style="width:300px">
				<ul>
					<li>홈</li>
					<li>온라인전용상품관리</li>
					<li class="last">수수료율 변경요청</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>