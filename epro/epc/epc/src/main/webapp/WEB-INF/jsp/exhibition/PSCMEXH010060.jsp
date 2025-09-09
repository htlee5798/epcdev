<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- 신규 공통 css 및 js 파일 INCLUDE -->
	<c:import url="/common/commonHead.do" />
	
	<%@ include file="/common/scm/scmCommon.jsp"%>
	
	<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
	<script type="text/javascript" src="/js/epc/common.js"></script>
	<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js"></script>
	<script type="text/javascript" src="/js/epc/paging.js"></script>

	
	<!-- product/PSCMPRD004002 -->

<script type="text/javascript">
	$(document).ready(function() {
		
		var comboName = '';
		 var comboCode = '';
		 var i = 0;
		 <c:forEach items="${applyCdList}" var="item" varStatus="listIndex" begin="0">
		    if(i == 0){
		   		comboName += '${item.CD_NM}';
		       	comboCode += '${item.MINOR_CD}';	
		   	} else {
		   		comboName += '|' + '${item.CD_NM}';
		       	comboCode += '|' + '${item.MINOR_CD}';
		   	}
		   	i++;
		</c:forEach>
		
		
		// START of IBSheet   Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet1", "100%", "180px");
		mySheet1.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		
		var ibdata = {};
		// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
		    {Header:"순번",		        		Type:"Text" ,			SaveName:"NUM", 		        					Align:"Center", Width:45, Edit:0}
		  , {Header:"구분자명", 				Type:"Text", 			SaveName:"DIVN_NM", 	          					Align:"Center", Width:80, Edit:0}
		  , {Header:"인터넷상품코드", 		Type:"Text", 			SaveName:"PROD_CD", 	    						Align:"Center", Width:120, Edit:0, Cursor:'pointer', FontColor:"#0000FF", FontUnderline:true}
		  , {Header:"상품명", 					Type:"Text", 			SaveName:"PROD_NM",  							Align:"Left", 	  Width:200, Edit:0}
		  , {Header:"전시순서", 				Type:"Int", 				SaveName:"DISP_SEQ",  								Align:"Center", Width:70, Edit:0}
		  , {Header:"근거리전용",     		Type:"Text", 			SaveName:"CMBN_MALL_SELL_PSBT_YN", 	Align:"Center", Width:70, Edit:0}
		  , {Header:"협력업체",        		Type:"Text", 			SaveName:"VENDOR_NM", 						Align:"Center", Width:80, Edit:0}
		  , {Header:"판매가", 					Type:"Int", 				SaveName:"CURR_SELL_PRC", 	      				Align:"Right", 	  Width:70, Edit:0, Format:"#,###,###"}
		  , {Header:"전시여부", 	    		Type:"Combo", 		SaveName:"DISP_YN", 	      						Align:"Center", Width:70, Edit:0, ComboCode:"Y|N", ComboText:"전시|미전시"}
		  , {Header:"품절", 						Type:"Text", 			SaveName:"SOUT_YN", 	      						Align:"Center", Width:70, Edit:0}
		  , {Header:"이미지", 	    			Type:"Button", 		SaveName:"IMG_PATH_BTN", 	      				Align:"Center", Width:70, Edit:0, Text:"이미지"} 
		  , {Header:"등록일", 	    			Type:"Text", 			SaveName:"REG_DATE", 	      						Align:"Center", Width:90, Edit:0}
		  , {Header:"등록자", 					Type:"Text", 			SaveName:"REG_ID", 	      							Align:"Center", Width:90, Edit:0}
		  , {Header:"최종수정일", 			Type:"Text", 			SaveName:"MOD_DATE", 	      					Align:"Center", Width:90, Edit:0}
		  , {Header:"최종수정자", 	    	Type:"Text", 			SaveName:"MOD_ID", 	      						Align:"Center", Width:90, Edit:0}
		  
		  , {Header:"divnSeq", 				Type:"Text", 			SaveName:"DIVN_SEQ", 	          					Align:"Center", Width:80, Edit:0, Hidden:true}
		  , {Header:"imgPath", 				Type:"Text", 			SaveName:"IMG_PATH", 	          					Align:"Center", Width:80, Edit:0, Hidden:true}
		  , {Header:"img250", 					Type:"Text", 			SaveName:"IMG250", 	          						Align:"Center", Width:80, Edit:0, Hidden:true}
		  , {Header:"mdSrcmkCd", 			Type:"Text", 			SaveName:"MD_SRCMK_CD", 	          			Align:"Center", Width:80, Edit:0, Hidden:true}
		];
		
		IBS_InitSheet(mySheet1, ibdata);
		mySheet1.ShowFilterRow();
		
		mySheet1.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet1.SetHeaderRowHeight(Ibs.HeaderHeight);
		
		
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "180px");
		mySheet1.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		
		var ibdata = {};
		// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		
		ibdata.Cols = [
   		    {Header:"순번",		        		Type:"Text" ,			SaveName:"NUM", 		        					Align:"Center", Width:45, Edit:0}
   		  , {Header:"", 							Type:"CheckBox", 	SaveName:"CHK", 	          							Align:"Center", Width:45, Edit:1, Sort:false}
   		  , {Header:"구분자명", 				Type:"Text", 			SaveName:"DIVN_NM", 	          					Align:"Center", Width:80, Edit:0}
   		  , {Header:"인터넷상품코드", 		Type:"Text", 			SaveName:"PROD_CD", 	    						Align:"Center", Width:120, Edit:0}
   		  , {Header:"상품명", 					Type:"Text", 			SaveName:"PROD_NM",  							Align:"Left", 	  Width:200, Edit:0}
   		  , {Header:"판매가", 					Type:"Int", 				SaveName:"CURR_SELL_PRC", 	      				Align:"Right", 	  Width:70, Edit:0, Format:"#,###,###"}
   		  , {Header:"승인상태", 				Type:"Combo", 		SaveName:"STS_CD", 	      							Align:"Right", 	  Width:70, Edit:0, ComboText:comboName, ComboCode:comboCode}
   		  , {Header:"전시여부", 	    		Type:"Combo", 		SaveName:"DISP_YN", 	      						Align:"Center", Width:70, Edit:0, ComboCode:"Y|N", ComboText:"전시|미전시"}
   		  , {Header:"품절", 						Type:"Text", 			SaveName:"SOUT_YN", 	      						Align:"Center", Width:70, Edit:0}
   		  , {Header:"이미지", 	    			Type:"Button", 		SaveName:"IMG_PATH_BTN", 	      				Align:"Center", Width:70, Edit:0, Text:"이미지"} 
   		  , {Header:"등록일", 	    			Type:"Text", 			SaveName:"REG_DATE", 	      						Align:"Center", Width:90, Edit:0}
   		  , {Header:"등록자", 					Type:"Text", 			SaveName:"REG_ID", 	      							Align:"Center", Width:90, Edit:0}
   		  , {Header:"최종수정일", 			Type:"Text", 			SaveName:"MOD_DATE", 	      					Align:"Center", Width:90, Edit:0}
   		  , {Header:"최종수정자", 	    	Type:"Text", 			SaveName:"MOD_ID", 	      						Align:"Center", Width:90, Edit:0}
   		  
   		  , {Header:"divnSeq", 				Type:"Text", 			SaveName:"DIVN_SEQ", 	          				Align:"Center", Width:80, Edit:0, Hidden:true}
   		  , {Header:"imgPath", 				Type:"Text", 			SaveName:"IMG_PATH", 	          				Align:"Center", Width:80, Edit:0, Hidden:true}
   		  , {Header:"img250", 					Type:"Text", 			SaveName:"IMG250", 	          						Align:"Center", Width:80, Edit:0, Hidden:true}
		  , {Header:"mdSrcmkCd", 			Type:"Text", 			SaveName:"MD_SRCMK_CD", 	          			Align:"Center", Width:80, Edit:0, Hidden:true}
   		  , {Header:"상태",						Type:"Status" ,		SaveName:"S_STATUS", 							Align:"Center", Width:80, Edit:0, Hidden:true}
   		];
		
		IBS_InitSheet(mySheet2, ibdata);
		mySheet2.ShowFilterRow();
		
		mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet2.SetHeaderRowHeight(Ibs.HeaderHeight);
		
		fn_masterSearch();
		
		
		$("#divnSeq").change(function(){
			mySheet1.SetFilterValue("DIVN_SEQ", $("#divnSeq option:selected").val(), 1);
			mySheet2.SetFilterValue("DIVN_SEQ", $("#divnSeq option:selected").val(), 1);
// 			$("#rowTotalCount").text(mySheet1.FilteredRowCount());
		});
		
}); // end of ready

	//조회
	function fn_masterSearch() {
	// 	var divnSeq         = $("#divnSeq").val();
	// 	var prodOrdSetupVal = $("#prodOrdSetupVal").val();

	var sUrl1 = '<c:url value="/exhibition/selectExhibitionProdList1.do"/>';		
		loadIBSheetData(mySheet1, sUrl1, null, '#dataForm', null);
	}
	
	function fn_detailSearch() {
		var sUrl2 = '<c:url value="/exhibition/selectExhibitionProdList2.do"/>';		
		loadIBSheetData(mySheet2, sUrl2, null, '#dataForm', null);
	}
	
	
	/* 상품추가 */
    function productAdd(){
		
		if($("#divnSeq").val() == "" || $("#divnSeq").val() == null){
			alert("구분자가 없습니다.");
			return;
		}
		
		var notInVal = "";
//     	var targetUrl = '<c:url value="/common/viewPopupProductList2.do"/>';//01:상품
    	var targetUrl = '<c:url value="/common/viewPopupProductList2.do"/>?gubun=Int';//01:상품
   			
   		Common.centerPopupWindow(targetUrl, 'prd', {width : 750, height : 500});	
    }
 
	function popupReturn(rtnVal){
		var prodCdChk1 = "";
		var prodCdChk2 = "";
		
   		for(var i=0; i<rtnVal.prodCdArr.length; i++){
   			
   			prodCdChk2 = rtnVal.prodCdArr[i]
   			for(var j=2; j<= mySheet2.GetTotalRows()+1; j++){
   				prodCdChk1 = mySheet2.GetCellValue(j, "PROD_CD");
   				
		 		if( prodCdChk1 == prodCdChk2 ){
		 			alert("같은 상품이 존재합니다.");
		 			return;
		 		}
		 	}
   			
//    		var rowIdx = mySheet.DataInsert(0);
   			var rowIdx = mySheet2.DataInsert(mySheet2.FilteredRowCount()+2);
//    		var rowIdx = mySheet2.DataInsert(mySheet.FilteredRowCount());
   			var nRow = mySheet2.FilteredRowCount()-1;
   			
	       	mySheet2.SetCellValue(rowIdx, "NUM", nRow+1);
	       	mySheet2.SetCellValue(rowIdx, "CHK", "1");
	       	mySheet2.SetCellValue(rowIdx, "PROD_CD", rtnVal.prodCdArr[i]);
	       	mySheet2.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);
	       	mySheet2.SetCellValue(rowIdx, "CURR_SELL_PRC", rtnVal.currSellPrcArr[i]);
	       	mySheet2.SetCellValue(rowIdx, "DISP_YN", rtnVal.dispYnArr[i]);
	       	mySheet2.SetCellValue(rowIdx, "STS_CD", "00");
	       	mySheet2.SetCellValue(rowIdx, "REG_ID", rtnVal.venDorIdArray[i]);
	       	
	       	mySheet2.SetCellValue(rowIdx, "DIVN_SEQ", $("#divnSeq option:selected").val());
	       	mySheet2.SetCellValue(rowIdx, "DIVN_NM", $("#divnSeq option:selected").text());
       	
	   	}
	}

	
	// function viewImg(url, viewSize, size, nameSize, rowCnt, mdSrcmkCd) {
	function viewImg(url, mdSrcmkCd) {
		var targetUrl = '<c:url value="/exhibition/prdImageDetailForm.do"/>?mdSrcmkCd='+mdSrcmkCd+'&url='+url;
		Common.centerPopupWindow(targetUrl, 'prdPrice', {width : 800, height : 800});
	}
	
	//상품상세조회 팝업
	function popupPrdInfo(no)
	{
	    var targetUrl = '<c:url value="/product/selectProductFrame.do"/>'+'?prodCd='+no;
	    Common.centerPopupWindow(targetUrl, 'epcProductInfo', {width : 1024, height : 850});
	}
	
	//저장
	function fn_save() {
		var f = document.dataForm;
		
	 	//  트랜잭션 갯수 체크
		var tranInsCount = mySheet2.RowCount("I");
		var tranUpCount = mySheet2.RowCount("U");
		
		
		if(confirm("저장하시겠습니까?")){
	 		
	 		// 헤더컬럼 Row 0
			// 필터링 Row 1		
	 		var rowChk = mySheet2.ColValueDup("3");
	 	 	if(rowChk > 0 ){
	 	 		alert("상품코드가 중복되었습니다.");
	 	 		return;
	 	 	}

	 	 	var myProdCd1 = "";
		 	var myProdCd2 = "";
		 	for(var i=2; i<= mySheet1.GetTotalRows()+1; i++){
		 		myProdCd1 = mySheet1.GetCellValue(i, "PROD_CD");
		 		for(var j=2; j<=mySheet2.GetTotalRows()+1; j++){
		 			myProdCd2 = mySheet2.GetCellValue(j, "PROD_CD");
		 			
		 			if( mySheet2.GetCellValue(j, "CHK") == 1 ){
		 				if(myProdCd1 == myProdCd2){
							alert("MD승인된 상품코드입니다.");
							return;
			 			}	
		 			}
		 		}
		 	}
		 	
			var sUrl = '<c:url value="/exhibition/insertIntExhibitionProdInfo.do"/>';
			
			var categoryId  = "${categoryId }";
			var mkdpSeq     = "${mkdpSeq }";
			var vendorId     = "${vendorId }";
			
			sUrl += "?categoryId="         + categoryId;
			sUrl += "&mkdpSeq="      + mkdpSeq;
			sUrl += "&vendorId="      + vendorId;
			
			if(tranUpCount>0){
				alert("등록된 상품은 저장할 수 없습니다.");
				return;
				//수정건이 미존재시 AJAX로 처리
				
			}else{
				mySheet2.DoSave(sUrl, {Quest:false, col:1});
			var formQueryString = $('*', '#dataForm').fieldSerialize();	
			//Ajax 저장처리
// 			$.ajax({
// 				type: 'POST',
// 				url: '<c:url value="/exhibition/insertIntExhibitionProdInfo.do"/>',
// 				data: formQueryString,
// 				success: function(json) {
// 					try {
// 						alert(json.Result.Message);
// 						//if(json.resultCode > 0){
// 						//}						
						
// 					} catch (e) {}
// 				},
// 				error: function(e) {
// 					alert(e);
// 				}
// 			});
			
			}
		}
	}
	
	
	// 승인요청
	function fn_aprv() {
		
		for(var i=2; i<= mySheet2.GetTotalRows()+1; i++){
			var chk = mySheet2.GetCellValue(i, "CHK");
			
			if(chk == 1){
				if( mySheet2.GetCellValue(i, "STS_CD") == "01" ){
					alert("업체승인된 건은 승인요철을 할 수 없습니다.");
					return;
				}
				
				if( mySheet2.GetCellValue(i, "STS_CD") == "02" ){
					alert("MD승인된 건은 승인요청을 할 수 없습니다.");
					return;
				}
				
			}
	 	}
			
		
		if(confirm("기획전 할당 요청 하시겠습니까?")){
			var insertCount = mySheet2.RowCount("I");
			var updateCount = mySheet2.RowCount("U");
			
			if(insertCount > 0){
				alert("저장을 완료하고 기획전 할당 요청 해주십시요.");
				return;
			}
			
			if(updateCount <= 0){
				alert("선택된 상품이 없습니다.");
				return;
			}
			
			var sUrl = '<c:url value="/exhibition/updateStsCd.do"/>';

			var categoryId  = "${categoryId }";
			var mkdpSeq     = "${mkdpSeq }";
			var vendorId     = "${vendorId }";
			
			sUrl += "?categoryId="         + categoryId;
			sUrl += "&mkdpSeq="      + mkdpSeq;
			sUrl += "&vendorId="      + vendorId;
			
			mySheet2.DoSave(sUrl, {Quest:false, col:1});
		}
	}
	
	
	// 상품삭제
	function fn_productDel() {
		
		var delCnt = 0;
		var errCnt = 0;
		
		if(confirm("상품을 삭제 하시겠습니까?")){
			for(var i=mySheet2.GetTotalRows()+1; i>= 2; i--){
				var chk = mySheet2.GetCellValue(i, "CHK");
				
				if(chk == 1){					
					if( mySheet2.GetCellValue(i, "REG_DATE") == "" ){
						mySheet2.RowDelete(i, 0);
					}else{
						errCnt++;
						alert("업체승인전이 아닌 상품은 삭제 할 수 없습니다.");
						return;
					}
					delCnt++;
				}
			}
			
			if( delCnt == 0 ){
				alert("선택된 상품이 없습니다.");
				return;
			}
			
			
			
// 			var sUrl = '<c:url value="/exhibition/deleteProdCd.do"/>';

// 			var categoryId  = "${categoryId }";
// 			var mkdpSeq     = "${mkdpSeq }";
// 			var vendorId     = "${vendorId }";
			
// 			sUrl += "?categoryId="         + categoryId;
// 			sUrl += "&mkdpSeq="      + mkdpSeq;
// 			sUrl += "&vendorId="      + vendorId;
			
// 			mySheet2.DoSave(sUrl, {Quest:false, col:1});
		}
	}
	
	
	//셀 클릭 시...
	function mySheet1_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if (Row < 1) return;
		
		if(mySheet1.ColSaveName(Col) == 'IMG_PATH_BTN'){
			
//	 		var url = mySheet.GetCellValue(Row, "IMG250");
			var imgDir = $("#imgDir").val();
			var mdSrcmkCd = mySheet1.GetCellValue(Row, "MD_SRCMK_CD");
			var url 	= imgDir + "/" + mdSrcmkCd.substring(0, 5) + "/"+mdSrcmkCd+"_1_250.jpg";
// 			var url 	= imgDir + "1_1_250.jpg";

			viewImg(url, mdSrcmkCd);
		}
		
		if(mySheet1.ColSaveName(Col) == 'PROD_CD'){
			var prodCd = mySheet1.GetCellValue(Row, 'PROD_CD');
			popupPrdInfo(prodCd);
		}
		
		
	}
	
	//셀 클릭 시...
	function mySheet2_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

		if(mySheet2.ColSaveName(Col) == 'IMG_PATH_BTN'){
			
			var imgDir = $("#imgDir").val();
			var mdSrcmkCd = mySheet2.GetCellValue(Row, "MD_SRCMK_CD");
			var url 	= imgDir + "/" + mdSrcmkCd.substring(0, 5) + "/"+mdSrcmkCd+"_1_250.jpg";

			viewImg(url, mdSrcmkCd);
			
			
		}
	}
	
	/** ********************************************************
	 * 저장 메세지 창
	 ******************************************************** */
	function mySheet2_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) {		
			fn_masterSearch();
		}
	}
	
	//데이터 읽은 직후 이벤트
	function mySheet1_OnSearchEnd(cd,msg) {
		fn_detailSearch();
	}
	
	
	//데이터 읽은 직후 이벤트
	function mySheet2_OnSearchEnd(cd,msg) {
		$("#divnSeq option:eq(0)").attr("selected", "selected");
		$("#divnSeq").trigger("change");
	}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>

<form name="dataForm" id="dataForm">

<input type="hidden" name="categoryId" id="categoryId" value="${categoryId}">
<input type="hidden" name="mkdpSeq" id="mkdpSeq" value="${mkdpSeq}">
<input type="hidden" name="mkdpNm" id="mkdpNm" value="${mkdpNm}">
<input type="hidden" name="startDatePop" id="startDatePop" value="${startDatePop}">
<input type="hidden" name="endDatePop" id="endDatePop" value="${endDatePop}">
<%-- <input type="hidden" name="vendorId" id="vendorId" value="${vendorId}"> --%>
<input type="hidden" id="imgDir" name="imgDir" value="<c:out value="${imgDir }"/>"">
		<div id="popup">
			<!--  @title  -->
			<div id="p_title1">
				<h1>기획전 상품등록</h1>
				<span class="logo"><img
					src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif"
					alt="LOTTE MART" /></span>
			</div>
			<!--  @title  //-->
			<!--  @process  -->
			<div id="process1">
				<ul>
					<li>홈</li>
					<li>기획전관리</li>
					<li>통합기획전관리</li>
					<li class="last">기획전 상품등록</li>
				</ul>
			</div>
		</div>
		<!--  @process  //-->
		<div class="popup_contents">

			<div class="bbs_search3">
				<ul class="tit">	
					<li class="tit">기획전 기본정보</li>
<!-- 					<li class="btn"> -->
<!-- 						<a href="#" class="btn" onclick="doEdit()"><span>저장</span></a> -->
<%-- 						<a href="#" class="btn" onclick="top.close();"><span><spring:message code="button.common.close" /></span></a> --%>
<!-- 					</li> -->
				</ul>

				<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="15%">
						<col width="35%">
					</colgroup>

					<tr>
						<th>기획전 코드</th>
						<td>
							${categoryId}
						</td>
						<th>기획전명</th>
						<td>
						<c:set var="mkdpNm" value="${mkdpNm}" />
							${mkdpNm}
						</td>
					</tr>

					<tr>
						<th>기획전 기간</th>
						<td colspan="3">
							${startDatePop}
							~
							${endDatePop}
						</td>
						
					</tr>
				</table>
			</div>
		<br/>
		
				<div class="bbs_search3">

					<ul class="tit">
						<li class="tit">기획전 상품등록</li>
						<!-- 건색 조건 -->
						
						<li class="btn"><a href="#" class="btn"  onclick="javascript:fn_masterSearch();" ><span> 조회 </span></a></li>
					</ul>
					<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="15%">
						<col width=" ">
					</colgroup>
					<tr>
						<th>구분자명 ${fn:length(divnSeqlist)}</th>
						<td>
							<select name="divnSeq" id="divnSeq"  style="width: 150px">
		                      	<c:forEach items="${divnSeqlist }" var="value">
		                      		<option value="${value.DIVN_SEQ }" >${value.DIVN_NM }</option> 
		                      	</c:forEach> 
							</select>
						</td>
					</tr>
					<!-- IBSHEET  -->
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td><div id="ibsheet1"></div></td>
						</tr>
					</table>
					<br/>
					
					<ul class="tit">
						<li class="btn"><a href="#" class="btn" onclick="javascript:fn_save();" ><span> 저장 </span></a> </li>
						<li class="btn"><a href="#" class="btn" onclick="javascript:fn_aprv();" ><span> 기획전 할당 요청 </span></a> </li>
						<li class="btn"><a href="#" class="btn" onclick="javascript:fn_productDel();" ><span> 상품삭제 </span></a> </li>
						<li class="btn"><a href="#" class="btn" onclick="javascript:productAdd();"><span> 상품추가 </span></a> </li>
					</ul>
					<!-- IBSHEET  -->
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td><div id="ibsheet2"></div></td>
						</tr>
					</table>
				</table>
				</div>
		</div>
	</form>

</body>

</html>