<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<c:import url="/common/commonHead.do" />
<script type="text/javascript" >
var headerNm = "";
var saveNm = "";

$(document).ready(function(){
	var prodFlag = $("#prodFlag").val();
	var onlineProdTypeCd = $("#onlineProdTypeCd").val();
	var prodNmTitle = "";

	if(onlineProdTypeCd == "06"){
		prodNmTitle = "추가구성 ";
	}

	if(prodFlag == "1") {
		headerNm = "인터넷상품코드";
		saveNm = "PROD_CD";
	} else if(prodFlag == "2") {
		headerNm = "상품코드";
		saveNm = "MD_PROD_CD";
	} else if(prodFlag == "3") {
		headerNm = "판매코드";
		saveNm = "MD_SRCMK_CD";
	} else {
		headerNm = "인터넷상품코드";
		saveNm = "PROD_CD";
	}

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "300px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(false);

	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬,   ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"",					Type:"CheckBox",	SaveName:"SELECTED",		Align:"Center", Width:25,  Sort:false}
	  , {Header:headerNm,			Type:"Text",		SaveName:saveNm,			Align:"Center", Width:120, Edit:0}
	  , {Header:prodNmTitle+"상품명",	Type:"Text",		SaveName:"PROD_NM",			Align:"Left"  , Width:450, Edit:0}
	  , {Header:"대표상품코드",			Type:"Text",		SaveName:"REP_PROD_CD",		Align:"Center", Width:110, Edit:0}
	  
	  , {Header:"재고수량",			Type:"Text",		SaveName:"RSERV_STK_QTY",	Hidden:true}
	  , {Header:"원가",				Type:"Text",		SaveName:"BUY_PRC",			Hidden:true}
	  , {Header:"매가",				Type:"Text",		SaveName:"SELL_PRC",		Hidden:true}
	  , {Header:"판매가",				Type:"Text",		SaveName:"CURR_SELL_PRC",	Hidden:true}
	  , {Header:"판매상태",			Type:"Text",		SaveName:"SELL_FLAG",		Hidden:true}
	  , {Header:"전시여부",			Type:"Text",		SaveName:"DISP_YN",			Hidden:true}
	  , {Header:"제조사",				Type:"Text",		SaveName:"VENDOR_NM",		Hidden:true}
	  
	];

	IBS_InitSheet(mySheet, ibdata);

	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.SetComboOpenMode(1); // 원클릭으로 ComboBox Open

	$('#excelDown').click(function() {
		exeExcelFile();
	});

	$('#excelInsert').click(function() {
		doExcelInsert();
	});

	//excelRemove초기화
	$('#excelRemove').click(function() {
		mySheet.RemoveAll();
		$('#createFile').attr({ value: '' });
		prodCdArr = new  Array();
	});

});

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}

/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(currentPage) {
	var url = '<c:url value="/common/selectPopupProductList.do"/>';
	loadIBSheetData(mySheet, url, currentPage, '#vendorPopUp', null);
}

function goSearch() {
	var prodFlag = $("#prodFlag").val();
	var iCnt = mySheet.RowCount() + 1;
	var rtnVal = "";

	if(prodFlag == "") {
		var prodCdArray = new Array(); // (인터넷상품코드)
		var prodNmArray = new Array(); // (인터넷상품명)
		var buyPrcArray = new Array(); // (원가)
		var sellPrcArray = new Array(); // (매가)
		var currSellPrcArray = new Array(); // (판매가)
		var sellFlagArray = new Array(); // (판매상태)
		var stockQtyArray = new Array(); // (재고수량)
		var dispYnArray = new Array(); // (전시여부)
		var venDorNmArray = new Array(); // (제조사)
		var venDorIdArray = new Array(); // (협력업체)

		rtnVal = new Object();

		for (var i = 1 ; i < iCnt; i++) {
			var selected = mySheet.GetCellValue(i, "SELECTED");
			if (selected) {
				prodCdArray.push(mySheet.GetCellValue(i, "PROD_CD"));
				prodNmArray.push(mySheet.GetCellValue(i, "PROD_NM"));
				buyPrcArray.push(mySheet.GetCellValue(i, "BUY_PRC"));
				sellPrcArray.push(mySheet.GetCellValue(i, "SELL_PRC"));
				currSellPrcArray.push(mySheet.GetCellValue(i, "CURR_SELL_PRC"));
				sellFlagArray.push(mySheet.GetCellValue(i, "SELL_FLAG"));
				stockQtyArray.push(mySheet.GetCellValue(i, "RSERV_STK_QTY"));
				dispYnArray.push(mySheet.GetCellValue(i, "DISP_YN"));
				venDorNmArray.push(mySheet.GetCellValue(i, "VENDOR_NM"));
				venDorIdArray.push($("#vendorId").val());
			}
		}

		rtnVal.prodCdArr = prodCdArray; // (인터넷상품코드)
		rtnVal.prodNmArr = prodNmArray; // (인터넷상품명)
		rtnVal.buyPrcArr = buyPrcArray; // (원가)
		rtnVal.sellPrcArr = sellPrcArray; // (매가)
		rtnVal.currSellPrcArr = currSellPrcArray; // (판매가)
		rtnVal.sellFlagArr = sellFlagArray; // (판매상태)
		rtnVal.stockQtyArr = stockQtyArray; // (재고수량)
		rtnVal.dispYnArr = dispYnArray; // (전시여부)
		rtnVal.venDorNmArr = venDorNmArray; // (제조사)
		rtnVal.venDorIdArray = venDorIdArray; // (제조사)
	} else {
		for (var i = 1 ; i < iCnt; i++) {
			var selected = mySheet.GetCellValue(i, "SELECTED");
			if (selected) {
				rtnVal += ","+mySheet.GetCellValue(i, saveNm);
			}
		}

		rtnVal = rtnVal.substring(1,rtnVal.length);
	}

	opener.popupReturn(rtnVal);
	window.close();
}

/**********************************************************
 * 일괄업로드용 양식 다운
 **********************************************************/
function exeExcelFile() {
	var form = document.vendorPopUp;

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
function doExcelInsert() {
	var form = document.vendorPopUp;

	var fName = $('#createFile').val();
	if (fName.length < 3) {
		alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
		return;
	}

	if (!confirm('엑셀 양식을 업로드 하시겠습니까?')) {
		return;
	}

	loadingMask();

	form.target = "frameForExcel";
	form.action = '<c:url value="/common/productExcelUpload.do"/>';
	form.submit();
}

/* function uploadReturn(rtnVal){
	$("#createFile").val("");
	
	hideLoadingMask();
	opener.popupReturn(rtnVal);
} */
function uploadRe(rtnVal) {
	$("#createFile").val("");

	//console.log(rtnVal.prodCdArr);
	if (rtnVal.prodCdArr != null) {
		for (var i = 0; i < rtnVal.prodCdArr.length; i++) {
			var rowIdx = mySheet.DataInsert(0);

			mySheet.SetCellValue(rowIdx, "CHK", "1");
			mySheet.SetCellValue(rowIdx, "PROD_CD", rtnVal.prodCdArr[i]);
			mySheet.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);

			mySheet.SetCellValue(rowIdx, "REP_PROD_CD", rtnVal.repProdCdArr[i]);
			mySheet.SetCellValue(rowIdx, "RSERV_STK_QTY", rtnVal.stockQtyArr[i]);
			mySheet.SetCellValue(rowIdx, "BUY_PRC", rtnVal.buyPrcArr[i]);
			mySheet.SetCellValue(rowIdx, "SELL_PRC", rtnVal.sellPrcArr[i]);

			mySheet.SetCellValue(rowIdx, "CURR_SELL_PRC", rtnVal.currSellPrcArr[i]);
			mySheet.SetCellValue(rowIdx, "SELL_FLAG", rtnVal.sellFlagArr[i]);
			mySheet.SetCellValue(rowIdx, "DISP_YN", rtnVal.dispYnArr[i]);
			mySheet.SetCellValue(rowIdx, "VENDOR_NM", rtnVal.venDorNmArr[i]);
		}
	} else {
		alert("데이터가 존재하지 않거나 양식이 잘못 되었습니다.");
	}

	hideLoadingMask();
}

</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
	<form name="vendorPopUp" id="vendorPopUp" method="post" enctype="multipart/form-data">
	<iframe name="frameForExcel" style="display:none;"></iframe>
	<input type="hidden" id="prodFlag" name="prodFlag" value="<c:out escapeXml='true' value='${param.prodFlag}'/>" />
	<input type="hidden" id="notInVal" name="notInVal" value="<c:out escapeXml='true' value='${param.notInVal}'/>" /> <!-- 제외상품 조건 파라미터 -->
	<input type="hidden" id="onlineProdTypeCd" name="onlineProdTypeCd" value="<c:out escapeXml='true' value='${param.onlineProdTypeCd}'/>" /> <!-- 온라인상품유형코드 조건 파라미터 -->
	<input type="hidden" id="prodDivnCd" name="prodDivnCd" value="<c:out escapeXml='true' value='${param.prodDivnCd}'/>" /> <!-- 온오프/온라인 상품구분 -->
	<input type="hidden" id="aprvYn" name="aprvYn" value="Y" /> <!-- 온오프/온라인 상품구분 -->

		<div id="popup">

			<div id="p_title1">
				<h1><c:if test="${param.onlineProdTypeCd eq '06'}">추가구성 </c:if>상품정보</h1>
				<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>
			<br/>
			<div class="popup_contents">
				<!-- 1검색조건 -->
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" onclick="goSearch();"><span>적용</span></a>
							<a href="#" class="btn" onclick="javascript:window.close();"><span><spring:message code="button.common.close"/></span></a>
						</li>
					</ul>
					<table class="bbs_grid2" cellspacing="0" border="0">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tr>
							<c:if test="${gubun eq 'Int'}">
							<th>협력업체</th>
							<td>
								<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:50%;" />
										<a href="#" class="btn" id="search" onclick="popupVendorList();"></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select" style="width:50%;">
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
											<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
										</c:forEach>
										</select>
									</c:when>
								</c:choose>
							</td>
							</c:if>

							<c:if test="${gubun ne 'Int'}">
							<th>
								<span class="star">협력업체</span>
							</th>
							<td>
								<c:set var="vedorIdVal" value="${param.vendorId}"/>
								<c:choose>
									<c:when test="${vedorIdVal eq ''}">
										전체
										<input type="hidden" id="vendorId" name="vendorId" value="" />
									</c:when>
									<c:otherwise>
										<input type="text" id="vendorId" name="vendorId" value="<c:out escapeXml='true' value='${vedorIdVal}'/>" readonly />
									</c:otherwise>
								</c:choose>
							</td>
							</c:if>

							<th><span class="star">
								<select id="condition1" name="condition1">
									<option value="prod_nm">상품명</option>
									<c:choose>
										<c:when test="${param.prodFlag eq '1' || param.prodFlag == null}">
										<option value="prod_cd">인터넷상품코드</option>
										</c:when>
										<c:when test="${param.prodFlag eq '2'}">
										<option value="md_prod_cd">상품코드</option>
										</c:when>
										<c:when test="${param.prodFlag eq '3'}">
										<option value="md_srcmk_cd">판매코드</option>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
									<!-- option value="rep_prod_cd">대표상품코드</option>  -->
								</select>
							</span></th>
							<td><input type="text" id="condition2" name="condition2" value="${searchVO.condition2}"></td>
						</tr>
					<tr>
						<th>상품일괄조회</th>
						<td colspan="3">
							<input type="file" name="createFile" id="createFile" style="width:345px;" value="파일이름" />
							<a href="#" id="excelDown" class="btn"><span>양식</span></a>
							<a href="#" id="excelInsert" class="btn"><span>업로드</span></a>
							<a href="#" id="excelRemove" class="btn"><span>초기화</span></a>
						</td>
				 	</tr>
					</table>
					
				</div>
				<!-- 	1검색조건 // -->
				<!-- 	2검색내역 -->
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">조회내역</li>
					</ul>

					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<tr>
		 					<td><div id="ibsheet1"></div></td>
						</tr>
					</table>
				</div>
			</div>

			<!-- 페이징 DIV -->
			<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
				<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
			</div>
		</div>
	</form>
</body>
</html>