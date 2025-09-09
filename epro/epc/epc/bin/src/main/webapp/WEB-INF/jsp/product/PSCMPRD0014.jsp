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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<!-- product/PSCMPRD0014 -->
<script type="text/javascript">
/** ********************************************************
 * 그리드 헤더 처리 함수
 ********************************************************* */
function setHeader() {
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "350px");
	mySheet.Reset();
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(false);
	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"", 			Type:"CheckBox", SaveName:"SELECTED",				Align:"Center", Width:25,  Sort:false}
	  , {Header:"순번", 			Type:"Text",	SaveName:"NUM", 					Align:"Center", Width:40,  Edit:0}
	  , {Header:"대분류",			Type:"Text", 	SaveName:"L1_NM", 					Align:"Center", Width:80,  Edit:0, Ellipsis:true}
	  , {Header:"중분류", 	    	Type:"Text",	SaveName:"L2_NM",					Align:"Center", Width:80,  Edit:0, Ellipsis:true}
	  , {Header:"소분류", 	 		Type:"Text",	SaveName:"L3_NM",					Align:"Center", Width:80,  Edit:0, Ellipsis:true}
	  , {Header:"인터넷상품코드",	Type:"Text",	SaveName:"PROD_CD",					Align:"Center", Width:100, Edit:0}
	  , {Header:"단품코드", 		Type:"Text",	SaveName:"ITEM_CD",					Align:"Center", Width:80,  Edit:0}
	  , {Header:"상품명", 	  		Type:"Text",	SaveName:"PROD_NM", 				Align:"Left",   Width:280, Edit:0, Ellipsis:true}
	  , {Header:"변경 후 상품명",		Type:"Text",	SaveName:"CHG_PROD_NM",				Align:"Left",   Width:280, Edit:0, Ellipsis:true}
	  , {Header:"판매상태",  		Type:"Text",	SaveName:"SELL_STATE", 				Align:"Center", Width:70,  Edit:0}
	  , {Header:"전시여부",  		Type:"Text",	SaveName:"DISP_YN",					Align:"Center", Width:70,  Edit:0}
	  , {Header:"전상법여부",		Type:"Text",	SaveName:"ECOM_YN",					Align:"Center", Width:70,  Edit:0}
	  , {Header:"전상법승인",		Type:"Text",	SaveName:"ECOM_YN_APPROVE",			Align:"Center", Width:70,  Edit:0}
	  , {Header:"업체공통조건사용",	Type:"Text",	SaveName:"ENTP_PROD_COND_DELI_YN",	Align:"Center", Width:70,  Edit:0}
	  , {Header:"무료배송여부",		Type:"Text",	SaveName:"NOCH_DELI_YN",			Align:"Center", Width:70,  Edit:0}
	  , {Header:"재고수량",		Type:"Int",		SaveName:"RSERV_STK_QTY",			Align:"Right",  Width:80,  Edit:0}
	  , {Header:"점포코드",		Type:"Text",	SaveName:"STR_CD", Hidden:true}
	  , {Header:"협렵업체코드",		Type:"Text",	SaveName:"VENDOR_ID", Hidden:true}
	  , {Header:"판매상태코드",		Type:"Text",	SaveName:"SELL_STATE_CD", Hidden:true}
	];

	IBS_InitSheet(mySheet, ibdata);

	mySheet.SetFrozenCol(1);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
}

$(document).ready(function(){
	setHeader();

	//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정 (배송비 업체공통 조건 문구가 보여서 추가 처리)
	$("#entpProdCondDeli").hide();

	$("#chgFlag").change(function() {
		var val = $(this).val();

		$("#prodChgVal").val("");
		$("#stkQtyChgVal").val("");
		$("#prodChgVal").hide();
		$("#dispChgVal").hide();
		$("#entpProdCondVal").hide();
		$("#stkQtyChgVal").hide();
		$("#sellState").hide();
		$("#plusBtn").hide();
		$("#minusBtn").hide();
		$("#entpProdCondDeli").hide();

		$("#chgBtn").show();

		if(val == "01"){
			$("#prodChgVal").show();
			$("#plusBtn").show();
			$("#minusBtn").show();
			$("#chgBtn").hide();
		}else if(val == "02"){
			$("#sellState").show();
		}else if(val == "03"){
			$("#dispChgVal").show();
		}else if(val == "04"){
			$("#stkQtyChgVal").show();
		}else if(val == "05"){
			$("#dispChgVal").show();
			/* $("#entpProdCondVal").show(); */
			$("#entpProdCondDeli").show();
		}
	});

	$('#search').click(function() {
		$("#prodInVal").val("");
		doSearch();
	});

	$('#chgBtn').click(function() {
		doChange('1');
	});

	$('#chgSave').click(function() {
		doSave();
	});

	$('#plusBtn').click(function() {
		doChange('1');
	});

	$('#minusBtn').click(function() {
		doChange('2');
	});

	$('#excel').click(function() {
		doExcel();
	});

	$('#excelDown').click(function() {
		exeExcelFile();
    });

	$('#excelInsert').click(function() {
		doExcelInsert();
	});
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

	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "") {
	        alert('업체선택은 필수입니다.');
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

    if ($('#chkVal').is(':checked')) {
        $('#chkVal').val('Y');

        var startDate = form.startDate.value.replace( /-/gi, '' );
    	var endDate   = form.endDate.value.replace( /-/gi, '' );

    	if(startDate > endDate){
    		alert('시작일자가 종료일자보다 클 수 없습니다.');
    		return;
    	}
    } else {
        $('#chkVal').val('N');
    }

    var url = '<c:url value="/product/selectOutOfStockList.do"/>';
    loadIBSheetData(mySheet, url, currentPage, '#adminForm', null);
}

//카테고리 팝업
function popupCategory(){
	var targetUrl = '<c:url value="/common/selectCategoryPopUpView.do"/>?categoryTypeCd=01';//01:상품
	Common.centerPopupWindow(targetUrl, 'prd', {width : 560, height : 485});
}

//카테고리 검색창으로 부터 받은 카테고리 정보 입력
function setCategoryInto(categoryId, categoryNm) { // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
	var form = document.adminForm;
	form.categoryId.value = categoryId;
	form.categoryNm.value = categoryNm;
}

//카테고리 입력 정보 삭제
function deleteCategory() {
	var form = document.adminForm;
	form.categoryId.value = "";
	form.categoryNm.value = "";
}

/** ********************************************************
 * excel
 ******************************************************** */
function doExcel(){
	var form = document.adminForm;

	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "") {
	        alert('업체선택은 필수입니다.');
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );

	if(startDate > endDate){
		alert('시작일자가 종료일자보다 클 수 없습니다.');
		return;
	}

	var url = '<c:url value="/product/selectOutOfStockListExcel.do"/>';
	form.action = url;
	form.submit();
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}

/**********************************************************
 * 일괄업로드용 양식 다운
 **********************************************************/
function exeExcelFile(){
	var form = document.adminForm;

	if (!confirm('엑셀 양식을 다운로드 하시겠습니까?')) {
		return;
	}

	form.target = "frameForExcel";
	form.action = '<c:url value="/common/productExcelDown.do"/>';
	form.submit();
}

/**********************************************************
 * 일괄업로드
 **********************************************************/
function doExcelInsert(){
	var form = document.adminForm;

	var fName = $('#createFile').val();

	if (fName.length < 3) {
		alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
		return;
	}

	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "") {
	        alert('업체선택은 필수입니다.');
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

	loadingMask();

	form.target = "frameForExcel";
	form.action = '<c:url value="/product/selectOutOfStockUploadList.do"/>';
	form.submit();
}

/**********************************************************
 * 일괄적용
 **********************************************************/
var chkChg = false;

function doChange(arg){
	var chgFlag = $("#chgFlag").val();
	var selFlag = false;
	var valFlag = false;
	var valArg;

	if(mySheet.RowCount() == 0){
		alert('조회된 데이터가 없습니다.');
		return;
	}

	for(var i=1; i<mySheet.RowCount()+1; i++){
		if(mySheet.GetCellValue(i,"SELECTED") == 1){
			selFlag = true;

			if(chgFlag == "01"){
				if(arg == "2"){
					mySheet.SetCellValue(i,"CHG_PROD_NM",mySheet.GetCellValue(i,"PROD_NM"));
				}

				if($.trim($("#prodChgVal").val()) == "" && arg == "1"){
					valFlag = true;
					valArg = $("#prodChgVal");
					break;
				}

				if(arg == "1"){
					mySheet.SetCellValue(i,"CHG_PROD_NM",$("#prodChgVal").val()+" "+mySheet.GetCellValue(i,"PROD_NM"));
				}
			}else if(chgFlag == "02"){
				mySheet.SetCellValue(i,"SELL_STATE",$("#sellState option:selected").text());
				mySheet.SetCellValue(i,"SELL_STATE_CD",$("#sellState option:selected").val());
			}else if(chgFlag == "03"){
				mySheet.SetCellValue(i,"DISP_YN",$("#dispChgVal option:selected").val());
			}else if(chgFlag == "04"){
				if($.trim($("#stkQtyChgVal").val()) == ""){
					valFlag = true;
					valArg = $("#stkQtyChgVal");
					break;
				}
				mySheet.SetCellValue(i,"RSERV_STK_QTY",$("#stkQtyChgVal").val());
			}else if(chgFlag == "05"){
				if($("#dispChgVal option:selected").val()=='Y'){	
				mySheet.SetCellValue(i,"ENTP_PROD_COND_DELI_YN","Y");	
				 mySheet.SetCellValue(i,"NOCH_DELI_YN","N"); 
				/* mySheet.SetCellValue(i,"ENTP_PROD_COND_DELI_YN",$("#entpProdCondVal option:selected").val()); */
				}else{
					mySheet.SetCellValue(i,"ENTP_PROD_COND_DELI_YN","N");
					mySheet.SetCellValue(i,"NOCH_DELI_YN","Y"); 
				}
			}
		}
	}

	if(!selFlag){
		alert("적용할 상품을 선택 하세요.");
		chkChg = false;
		return;
	}
	
	if(valFlag && arg == "1"){
		alert("적용할 항목을 입력 하세요.");
		valArg.focus();
		chkChg = false;
		return;
	}

	chkChg = true;
}

function doSave(){
	var selFlag = false;

	if(mySheet.RowCount() == 0){
		alert('조회된 데이터가 없습니다.');
		return;
	}

	//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정 (배송비 업체공통 조건 문구가 보여서 추가 처리)
	var vendorAry = new Array();

	<c:forEach items="${deliVendorList}" var="list" varStatus="status">
		vendorAry['<c:out value="${status.index}" />'] = '<c:out value="${list.VENDOR_ID}" />';
	</c:forEach>

	var chkChgFlag = $("#chgFlag").val();

	for(var i=1; i<mySheet.RowCount()+1; i++){
		if(mySheet.GetCellValue(i,"SELECTED") == 1){
			selFlag = true;

			//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정 (배송비 업체공통 조건 문구가 보여서 추가 처리)
			if(vendorAry.indexOf(mySheet.GetCellValue(i,"VENDOR_ID")) == -1 && mySheet.GetCellValue(i,"ENTP_PROD_COND_DELI_YN") == "Y" && chkChgFlag == "05") {
				alert(mySheet.GetCellValue(i,"NUM") + "행 값은 업체 공통배송조건 사용 불가 ([SCM]시스템관리→업체정보관리→주문배송비관리 미등록)");
				return;
			}
		}
	}

	if(!selFlag){
		alert("저장할 상품을 선택 하세요.");
		return;
	}

	if(!chkChg){
		alert("저장할 항목입력(선택) 적용 후 일괄 저장 하세요.\n\n* 상품명 일괄적용은 + 버튼으로 적용");
		return;
	}

	var param = new Object();
	param.chgFlag = $("#chgFlag option:selected").val();

	var udtUrl = '<c:url value="/product/updateOutOfStock.do"/>';
	mySheet.DoSave(udtUrl, {Param:$.param(param), Col:0, Sync:2});

	chkChg = false;
}

function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	alert(Msg);
	goPage('1');
}

function onlyNumber(event,arg) { // 8 백스페이스 , 9 탭 , 37 왼쪽이동, 39 오른쪽이동, 46 delete 
	var key; 
	if(event.which) { // ie9 firefox chrome opera safari 
		key = event.which; 
	} else if(window.event) {  // ie8 and old 
		key = event.keyCode; 
	} 
	if(!( key==8 || key==9 || key==37 || key==39 || key==46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105) )) {
		alert("숫자만 입력해 주세요");
		if(event.preventDefault){
			event.preventDefault();
			arg.value = "";
	    } else {
			event.returnValue = false;
			arg.value = "";
	    }
	} else {
		event.returnValue = true; 
	}
}
</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<div id="content_wrap">
<form name="adminForm" id="adminForm" method="post" enctype="multipart/form-data">
<iframe name="frameForExcel" style="display:none;"></iframe>
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
					<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>	
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>				
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup> 
			<tr>
				<th><span class="star">*</span> 일자검색조건</th>
				<td>
					<select name="searchDateType" class="select">
						<option value="01">온라인등록일</option>
						<option value="02">승인일</option>
						<option value="03">MD등록일</option>
					</select>
				</td>
				<th><input type="checkbox" name="chkVal" id="chkVal" value="N" align="center"> 조회일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
			</tr>
			<tr>
				<th>카테고리</th>
				<td>
					<input type="text" name="categoryId"  readonly style="width:37%;" />
					<input type="text" name="categoryNm" readonly style="width:37%;" />
					<a href="javascript:popupCategory();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
					<a href="javascript:deleteCategory();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>
				</td>
				<th>전시여부</th>
				<td>
					<select name="dispYn" class="select">
						<option value="">전체</option>
                        <option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>인터넷상품코드</th>
				<td>
					<input type="text" name="prodCd"  style="width:95%;" maxlength="13" />
				</td>
				<th>인터넷상품명</th>
				<td>
					<input type="text" name="prodNm" style="width:95%;" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th>품절여부</th>
				<td>
					<select name="soutYn" class="select">
						<option value="">전체</option>
                        <option value="Y">Y</option>
						<option value="N">N</option>
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
				<th>상품일괄조회</th>
				<td colspan="3">
					<input type="file" name="createFile" id="createFile" style="width:545px;" value="파일이름"  />
					<a href="#" id="excelDown" class="btn"><span>양식</span></a>
					<a href="#" id="excelInsert" class="btn"><span>업로드</span></a>
				</td>
			</tr>
			<tr>
				<th>일괄변경</th>
				<td colspan="3">
					<select name="chgFlag" id="chgFlag" class="select" style="width:80px;">
					<option value="01" selected="selected">상품명</option>
					<option value="02">판매상태</option>
					<option value="03">전시상태</option>
					<option value="04">재고수량</option>
					<option value="05">업체공통</option>
					</select>
					<input type="text" name="prodChgVal"  id="prodChgVal"  style="width:100px;" />
					<img src="${lfn:getString('system.cdn.static.path')}/images/common/layout/lnb_plus.gif" alt="" class="middle" id="plusBtn"/>
					<img src="${lfn:getString('system.cdn.static.path')}/images/common/layout/lnb_minus.gif" alt="" class="middle" id="minusBtn"/>
					<select name="sellState" id="sellState" class="select" style="width:80px; display:none;">
					<option value="01" selected="selected">판매진행</option>
					<option value="02">품절</option>
					<option value="03">판매종료</option>
					</select>
					<select name="dispChgVal" id="dispChgVal" class="select" style="width:50px; display:none;">
					<option value="Y" selected="selected">Y</option>
					<option value="N">N</option>
					</select>
					<!-- <select name="entpProdCondVal" id="entpProdCondVal" class="select" style="width:50px; display:none;">
					<option value="Y" selected="selected">Y</option>
					<option value="N">N</option>
					</select> -->
					<input type="text" name="stkQtyChgVal"  id="stkQtyChgVal"  onkeydown='onlyNumber(event,this)' style="width:100px; display:none;" />
					<a href="#"  id="chgBtn" class="btn" style="display:none;"><span>적용</span></a>
					<a href="#"  id="chgSave" class="btn"><span>일괄저장</span></a>
					<span id="entpProdCondDeli"> * 배송비 업체공통 조건</span> 
				</td>
			</tr>
			</table>
		</div>
		<!-- 1검색조건 // -->
	</div>

	<!-- 2 검색내역 -->
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">조회내역</li>
			</ul>

			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><div id="ibsheet1"></div></td>
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

	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품관리</li>
					<li class="last">상품정보관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>

<!-- @ BODY WRAP  END // -->
</body>
</html>