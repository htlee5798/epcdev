<%--
- Author(s): lth
- Created Date: 2016. 05. 03
- Version : 1.0
- Description : 가격변경 요청관리

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
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

$(document).ready(function(){
	
	$('#startDate').click(function() {
		openCal('adminForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('adminForm.endDate');
	});
	$('#search').click(function() {
		doSearch();
	});
	$('#excel').click(function() {
		doExcel();
	});	
	
	$("#input_changePrc").keyup(function(event){
        if (!(event.keyCode >=37 && event.keyCode<=40)) {
            var inputVal = $(this).val();
            $(this).val(inputVal.replace(/[^0-9]/gi,''));
        }
    });
	
	//-----------------
	// 변경요청판매가
	//-----------------
	$('#input_changePrc').focus(function() {
		document.adminForm.input_changePrc.value='';
	});
	
	//-----------------
	// 변경요청사유
	//-----------------
	$('#input_changeReason').focus(function() {
		document.adminForm.input_changeReason.value='';
	});
	
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "370px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"구분",		Type:"Text" ,	SaveName:"crud", 		    		Align:"Center",	Width:30,	Edit:0, Hidden:true}
	  , {Header:"수정/생성여부",Type:"Text", 	SaveName:"isUpd", 	      			Align:"Center",	Width:20,	Edit:0, Hidden:true}
	  , {Header:"", 		Type:"CheckBox",SaveName:"selected", 	    		Align:"Center",	Width:30,	Edit:1}
	  , {Header:"인터넷상품명", Type:"Text", 	SaveName:"prodNm", 					Align:"Left",	Width:200,	Edit:0}
	  , {Header:"인터넷상품코드", Type:"Text", 	SaveName:"prodCd",  				Align:"Center",	Width:100,	Edit:0, Cursor:'pointer', FontUnderline:true}
	  , {Header:"점포코드", 	Type:"Text", 	SaveName:"strCd",  					Align:"Center",	Width:70,	Edit:0, Hidden:true}
	  , {Header:"점포", 		Type:"Text", 	SaveName:"strNm",  					Align:"Center",	Width:70,	Edit:0}
	  , {Header:"단품코드",    Type:"Text", 	SaveName:"itemCd", 					Align:"Center",	Width:60,	Edit:0}
	  , {Header:"요청순번",    Type:"Text", 	SaveName:"reqSeq", 					Align:"Center",	Width:60,	Edit:0, Hidden:true}
	  , {Header:"협력업체코드", 	Type:"Text", 	SaveName:"vendorId", 				Align:"Center",	Width:70,	Edit:0}
	  , {Header:"변경전원가", 	Type:"Int", 	SaveName:"chgbBuyPrc", 	    		Align:"Right",	Width:80,	Edit:0}
	  , {Header:"변경요청원가", 	Type:"Int", 	SaveName:"chgbSellPrc",    			Align:"Right",	Width:80,	Edit:0, Hidden:true}
	  , {Header:"이익률(%)",  Type:"Int",		SaveName:"profitRate", 				Align:"Right",	Width:80,	Edit:0, Hidden:true}
	  , {Header:"이익률(%)",  Type:"Text", 	SaveName:"profitRateS",    			Align:"Left",	Width:80,	Edit:0}
	  , {Header:"변경요청원가", Type:"Int", 		SaveName:"chgaBuyPrc", 	  			Align:"Right",	Width:80,	Edit:0}
	  , {Header:"변경전판매가", Type:"Int", 		SaveName:"chgbCurrSellPrc", 	  	Align:"Right",	Width:80,	Edit:0}
	  , {Header:"면과세", 	 	Type:"Text", 	SaveName:"taxatDivnCd", 	      	Algn:"Left",	Width:80,	Edit:0, Hidden:true}
	  , {Header:"변경요청판매가",Type:"Int", 	SaveName:"chgaCurrSellPrc", 	  	Align:"Right",	Width:80,	Edit:0, MaximumValue:9999999999}
	  , {Header:"변경요청사유", Type:"Text", 	SaveName:"chgReqReasonContent", 	Align:"Left",	Width:80,	Edit:0}
	  , {Header:"요청일시", 	Type:"Text", 	SaveName:"reqDate", 	      		Align:"Center",	Width:80,	Edit:0}
	  , {Header:"승인일시", 	Type:"Text", 	SaveName:"aprvDate", 	      		Align:"Center",	Width:80,	Edit:0}
	  , {Header:"승인결과", 	Type:"Text", 	SaveName:"aprvNm", 	      			Align:"Center",	Width:60,	Edit:0}
	  , {Header:"반려사유", 	Type:"Text", 	SaveName:"retnReason", 	      		Align:"Left",	Width:120,	Edit:0}
	  , {Header:"상태",		Type:"Status",	SaveName:"s_status",              	Align:"Center",	Width:0,	Edit:0, Hidden:true}
	  , {Header:"승인여부", 	Type:"Text", 	SaveName:"aprvYn", 	      			Align:"Left",	Width:60,	Edit:0, Hidden:true}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	//mySheet.FitColWidth();
	
}); // end of ready

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}

/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(currentPage){
	var form = document.adminForm;
	var url = '<c:url value="/product/selectPriceChangeList.do"/>';
	var param = new Object();
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "")
	    {
	        alert('업체선택은 필수입니다.');
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

	var startDate = $('#startDate').val().replace( /-/gi, '' );
 	var endDate  = $('#endDate').val().replace( /-/gi, '' );
 	
 	if(startDate > endDate){
 		alert('시작일자가 종료일자보다 클 수 없습니다.');
 		return;
 	}
 	
	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.startDate = form.startDate.value;
	param.endDate = form.endDate.value;
	param.mode = 'search';
	
	param.prodCd =  $("#prodCd").val();
	param.prodNm = $("#prodNm").val();
	param.vendorId = form.vendorId.value;
	param.statusCd = $("#statusCd").val();
	param.aprvYn  = form.aprvYn.value;
	param.searchCondition =  form.searchCondition.value;
	param.searchWord = form.searchWord.value;
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
	

}

/** ********************************************************
 * 저장
 ******************************************************** */
function GridSaveRow(){
	var form = document.adminForm;
	//등록 가능 체크
//	alert(checkInsertRows());
	
	if(!checkInsertRows())
		return;	
	
	//필수 입력체크
	if(!condition())
		return;	
// 	gridObj.setParam('mode', 'save');
// 	var url = '<c:url value="/product/insertPriceChange.do"/>';

	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		if(RETURN_IBS_OBJ.mode == "choice"){
			if( mySheet.GetCellValue(i, "selected") == '1' ) {
				mySheet.SetCellValue(i, "crud", "I");
// 				mySheet.SetCellValue(i, "vendorId", form.vendorId.value);
			}
		}else{
			if( mySheet.GetCellValue(i, "selected") == '1' ) {
				mySheet.SetCellValue(i, "crud", "U");
			}
		}
		
	}
	
	mySheet.DoSave( '<c:url value="/product/insertPriceChange.do"/>', {Quest:0});

}

/**********************************************************
 * insert 로우 체크
 *********************************************************/
function checkInsertRows() {
	
	for(i = 1; i <= mySheet.GetTotalRows(); i++) {		
		if( mySheet.GetCellValue(i, "selected") == 1 ) {
			return true;
		}
	}
	
	alert("저장 가능한 로우가 없습니다.");
	return false;
}

/**********************************************************
 * grid 필수 입력 체크
 ******************************************************** */
function condition() {
	
	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		if( mySheet.GetCellValue(i, "selected") == 1){	
			if(mySheet.GetCellValue(i, "crud") == 'I') {
				if(mySheet.GetCellValue(i, "aprvYn") != 'N'){
					alert('이미 처리된 데이터는 수정할 수 없습니다.');
					return false;
				}
				if(mySheet.GetCellValue(i, "chgaCurrSellPrc") == '0') {
					alert('변경 요청 판매가가 0일수는 없습니다.');
					return false;
				}
				if(mySheet.GetCellValue(i, "chgaCurrSellPrc") == '') {
					alert('변경 요청 판매가가 0일수는 없습니다.');
					return false;
				}
				if(mySheet.GetCellValue(i, "chgbCurrSellPrc") == mySheet.GetCellValue(i, "chgaCurrSellPrc")) {
					alert('변경 전 판매가와 변경 요청 판매가가 동일합니다.');
					return false;
				}
				if(mySheet.GetCellValue(i, "chgReqReasonContent") == '') {
					alert('변경 요청 사유를 입력해주십시오.');
					return false;
				}
			}
		}
	}
	return true;
}

function goProductPopup() {
	
	var url = '<c:url value="/product/selectProductPopupView.do"/>';
	Common.centerPopupWindow(url, 'detail', {width : 800, height : 510});
}


function selectProductItemList(list) {	
	
	var form = document.adminForm;
	var param = new Object();
	var currentPage = 1;
	var url = '<c:url value="/product/selectProductItemList.do"/>';
	
	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.mode = 'choice';

	for(var i=0; i<list.prodCdList.length; i++){
		rtnProdCdList.push(list.prodCdList[i]);
		rtnCullSellPrcList.push(list.cullSellPrcList[i]);
	}

	param.selectedProdCd =  String(list.prodCdList);
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
}

/** ********************************************************
 * excel
 ******************************************************** */
function doExcel(){
	var form = document.adminForm;
	
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "")
	    {
	        alert('업체선택은 필수입니다.');
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

	if(startDate > endDate){
		alert('시작일자가 종료일자보다 클 수 없습니다.');
		return;
	}	

	var xlsUrl = '<c:url value="/product/selectPriceChangeListExcel.do"/>';
	var hideCols = 'crud|isUpd|selected|strCd|reqSeq|profitRate|profitRateS|taxatDivnCd|Status';
	directExcelDown(mySheet, '가격변경요청관리_' + new Date().yyyymmdd(), xlsUrl, '#adminForm', null, hideCols); // 전체 다운로드 
}

/** ********************************************************
 * 번경요청판매가 입력
 ******************************************************** */
function setChangePrc(input){
	var chkFlg = false;
	var rowCount = mySheet.GetTotalRows();
	var i;
	if(rowCount > 0){
		for(i=1; i<=rowCount; i++){
			
			if(mySheet.GetCellValue(i,"selected") == "1"){
				chkFlg = true;
				
				mySheet.SetCellValue(i, "chgaCurrSellPrc", input.value);
				mySheet.SetCellBackColor(i, "chgaCurrSellPrc", '#F6E5E2');

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
			if(mySheet.GetCellValue(i,"selected") == "1"){
				mySheet.SetCellValue(i, "chgReqReasonContent", input.value);
				mySheet.SetCellBackColor(i, "chgReqReasonContent", '#F6E5E2');
	
				mySheet.SetCellValue(i, "crud", "I");
			}
		}
	}
}

function goChangePrc(){

	var val1 = document.adminForm.input_changePrc.value;
	var val2 = document.adminForm.input_changeReason.value;
	if(val1 != '' && val1 != '변경요청판매가')
		setChangePrc(document.adminForm.input_changePrc);
	if(val2 != '' && val2 != '변경요청사유')
		setChangeReason(document.adminForm.input_changeReason);
}

function viewDesc(seq,prodCd) {
	var targetUrl = '<c:url value="/product/descDetailForm.do"/>?seq='+seq+'&prodCd='+prodCd;
	Common.centerPopupWindow(targetUrl, 'viewDesc', {width : 1100, height : 650, scrollBars : "YES"});
}

//상품상세조회 팝업
function popupPrdInfo(no){	
	var targetUrl = '<c:url value="/product/selectProductFrame.do"/>'+'?prodCd='+no;	
		
	Common.centerPopupWindow(targetUrl, 'prdInfo', {width : 1043, height : 800});
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}

//셀 클릭 시...
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

	if(mySheet.ColSaveName(Col) == 'selected'){
		if(mySheet.GetCellValue(Row, "selected") == "1") {
			mySheet.SetCellValue(Row, "crud", "I");
		} else {
			mySheet.SetCellValue(Row, "crud", "");			
		}
	}
	
}

//셀값 변경 시..
function mySheet_OnChange(Row, Col, Value, OldValue) {
	if (Row == 0) return;
	
	if(mySheet.ColSaveName(Col) == 'chgaCurrSellPrc' || mySheet.ColSaveName(Col) == 'chgReqReasonContent'){
		mySheet.SetCellValue(Row, "crud", "I");
		mySheet.SetCellValue(Row, "selected", "1");
	}
	
	
}


//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd(cd,msg) {
		if(RETURN_IBS_OBJ.mode == "choice"){
			for(i = 1; i <= mySheet.GetTotalRows(); i++) {				
				mySheet.SetCellEditable(i, "chgaCurrSellPrc", true);
				mySheet.SetCellEditable(i, "chgReqReasonContent", true);
				mySheet.SetCellBackColor(i, "chgaCurrSellPrc", '#DAFEE3');
				mySheet.SetCellBackColor(i, "chgReqReasonContent", '#DAFEE3');
				
				for(var j=0; j<rtnProdCdList.length; j++){
					if(mySheet.GetCellValue(i, "prodCd") == rtnProdCdList[j]){
						mySheet.SetCellValue(i, "chgaCurrSellPrc", rtnCullSellPrcList[j]);
					}
				}
			}
			
			if(RETURN_IBS_OBJ.dupCount > 0) {
				alert("이미 변경 요청한 데이터가 존재하니 확인하여 주시기 바랍니다.");
			}
		}else{
			for(i = 1; i <= mySheet.GetTotalRows(); i++) {
				if(mySheet.GetCellValue(i, "aprvYn") == 'N') {
					mySheet.SetCellEditable(i, "chgaCurrSellPrc", true);
					mySheet.SetCellEditable(i, "chgReqReasonContent", true);
					mySheet.SetCellBackColor(i, "chgaCurrSellPrc", '#f0fff0');
					mySheet.SetCellBackColor(i, "chgReqReasonContent", '#f0fff0');
				}
			}
		}
		
		//mySheet.FitColWidth();
}


function mySheet_OnResize(Width, Height) {
	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
	//mySheet.FitColWidth();
}

//저장후 자동으로 호출되는 이벤트(승인처리후 재조회 처리)
function mySheet_OnSaveEnd(code, Msg) {
	alert(Msg);
	doSearch();
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="adminForm" id="adminForm">
<div id="wrap_menu">

	<!--	@ 검색조건	-->
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>					
					<a href="javascript: GridSaveRow();" class="btn"><span><spring:message code="button.common.save"/></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="10%">
				<col width="25%">
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="20%">
			</colgroup> 
			<tr>
				<th><span class="star">*</span> 요청일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th>상태</th>
				<td>
					<select name="aprvYn" class="select">
						<option value="">전체</option>
						<option value="N">승인대기</option>
						<option value="Y">승인</option>
						<option value="R">반려</option>
					</select>
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
				<th>일반검색조건</th>
				<td>
					<select name="searchCondition" class="select">
						<option value="c">인터넷상품코드</option>
						<option value="n">인터넷상품명</option>
					</select>
				</td>
				<th>검색어</th>
				<td>
					<input type="text" name="searchWord" class="input" maxlength="20" />
				</td>
				<th></th>
				<td>
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
				<li class="tit" style="float:left;width:20%;">조회내역</li>
				<li class="tit" style="float:left;width:38%;"> 
					<!-- 1. 변경요청판매가 -->
					<input type="text" id="input_changePrc" class="input" value="변경요청판매가" style="ime-mode:disabled" maxlength="10"/>
					<!-- 2. 변경요청사유 -->
					<input type="text" id="input_changeReason" class="input" value="변경요청사유" />
				</li>
				<li class="btn" style="float:left;width:10%;">
					<a href="javascript:goChangePrc();" class="btn" ><span><spring:message code="button.common.setall"/></span></a>
				</li>	
				<li class="btn" style="float:right;width:10%;">
					<a href="javascript:goProductPopup();" class="btn" ><span><spring:message code="button.common.addproduct"/></span></a>
				</li> 
			</ul>
 
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
 					<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
				</tr>
			</table>
		</div>
		<!-- 2검색내역 // -->

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
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품관리</li>
					<li class="last">가격변경요청관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>