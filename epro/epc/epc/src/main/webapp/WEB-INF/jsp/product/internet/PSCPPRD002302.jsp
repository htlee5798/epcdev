<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%@page import="lcn.module.common.util.DateUtil"%>
<% 
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String tabNo = "12";
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- PBOMPRD0039 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- WISE 그리드 초기화 -->
<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){

		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "280px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 		 	    			Type:"Int", 				SaveName:"NUM", 		 	  				Align:"Center", Width:28,     Edit:0}
		  , {Header:"", 								Type:"CheckBox", 	SaveName:"SELECTED", 	 	  				Align:"Center", Width:25,     Sort:false}
		  , {Header:"상품코드", 	 				Type:"Text", 			SaveName:"ASSO_CD", 						Align:"Center", Width:120,   Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
		  , {Header:"상품명",	 	 				Type:"Text", 			SaveName:"ASSO_NM", 	 	 	  			Align:"Left",   	  Width:220,   Edit:0, Ellipsis:true}
		  , {Header:"원가", 	 	 					Type:"Int", 				SaveName:"BUY_PRC", 	 	  				Align:"Right",   Width:100,	Edit:0}
		  , {Header:"매가",		 					Type:"Int", 				SaveName:"SELL_PRC",	 	  				Align:"Right",   Width:100,	Edit:0}
		  , {Header:"판매가",		 				Type:"Int", 				SaveName:"CURR_SELL_PRC", 				Align:"Right",   Width:100,   Edit:0}
		  , {Header:"제조사",	 					Type:"Text", 	    	SaveName:"VENDOR_NM",	  				Align:"Center", Width:150,	Edit:0}
		  , {Header:"사용여부",	 				Type:"Text", 			SaveName:"DISP_YN", 			  			Align:"Center", Width:80,	    Edit:0}
		  , {Header:"이미지",						Type:"Text", 			SaveName:"IMG",								Align:"Center", Width:70,	    Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
		  
		  , {Header:"인터넷상품코드",  	Type:"Text", 	SaveName:"PROD_CD",		Edit:0, Hidden:true }
		];

		
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		
		
	    $('#search').click(function() {
	        doSearch();
	    });
	    
	    $('#add').click(function() {
	    	goBoardPopup();
	    });
	    
	    $('#delete').click(function() {
	    	productDel();
	    });
	    
	    $('#save').click(function() {
	    	doSave();
	    });
	    
	    doSearch();
	}); // end of ready
	
	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() 
	{
		goPage('1');
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(currentPage){
		var url = '<c:url value="/product/selectComponentSearch.do"/>';
		
		loadIBSheetData(mySheet, url, currentPage, '#detailForm', null);
	}
	
	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		
	}	
	
	//데이터 저장 후 이벤트
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
    	alert(Msg);
    	doSearch();
    }
	
	/* 상품등록 */
	function doSave(){
		var param = new Object();
		param.mode = 'update';
		param.prodCd = $("#detailForm #prodCd").val();
		param.vendorId = $("#detailForm #vendorId").val();
		param.prodLinkKindCd = $("#detailForm #prodLinkKindCd").val();
		
	    var sUrl = '<c:url value="/product/saveProdComponent.do"/>';
	    mySheet.DoSave(sUrl, {Param:$.param(param), Col:2, Sync:2});
	
	}
	
	/** ********************************************************
	 * 상품추가 팝업링크
	 ******************************************************** */
	function goBoardPopup(){
		var notInVal = document.detailForm.prodCd.value;
		
		for(var i=1; i<mySheet.RowCount()+1; i++){
			notInVal += ","+mySheet.GetCellValue(i,"ASSO_CD");
		}
		
		var targetUrl = '<c:url value="/common/viewPopupProductList.do"/>?vendorId='+$("#detailForm #vendorId").val()+"&notInVal="+notInVal;
   			
   		Common.centerPopupWindow(targetUrl, 'prd', {width : 750, height : 500});	
	}
	
	function popupReturn(rtnVal){
		var prodCd = $("#detailForm #prodCd").val();
		
	   	for(var i=0; i<rtnVal.prodCdArr.length; i++){
	   		var rowIdx = mySheet.DataInsert(0);
	   	
	   		mySheet.SetCellValue(rowIdx, "PROD_CD", prodCd);
	   		mySheet.SetCellValue(rowIdx, "ASSO_CD", rtnVal.prodCdArr[i]);
	       	mySheet.SetCellValue(rowIdx, "ASSO_NM", rtnVal.prodNmArr[i]);
	       	mySheet.SetCellValue(rowIdx, "BUY_PRC", rtnVal.buyPrcArr[i]);
	       	mySheet.SetCellValue(rowIdx, "SELL_PRC", rtnVal.sellPrcArr[i]);
	       	mySheet.SetCellValue(rowIdx, "CURR_SELL_PRC", rtnVal.currSellPrcArr[i]);
	       	mySheet.SetCellValue(rowIdx, "DISP_YN", rtnVal.dispYnArr[i]);
	       	mySheet.SetCellValue(rowIdx, "VENDOR_NM", rtnVal.venDorNmArr[i]);
	       	mySheet.SetCellValue(rowIdx, "IMG", "Image");
	   	}
	}
	
	/* 상품삭제 */
	function productDel(){
		if(!confirm("삭제 하시겠습니까?")){
			return;
		}
		
		var chkRow = "";
		var chkRow2 = false;
		
		for(var i=1; i<mySheet.RowCount()+1; i++){
			if(mySheet.GetCellValue(i,"SELECTED") == 1 && mySheet.GetCellValue(i,"NUM") == ""){
				chkRow += "|"+i;
			}
			
			if(mySheet.GetCellValue(i,"SELECTED") == 1){
				chkRow2= true;
			}
		}
		
		if(chkRow != ""){
			chkRow = chkRow.substring(1,chkRow.length);
			
			mySheet.RowDelete(chkRow);
		}
		
		if(chkRow == "" && !chkRow2){
			alert("삭제할 항목을 선택하세요.");
			return;
		}
		
		if(mySheet.RowCount() > 0){
			var param = new Object();
			param.mode = 'delete';
			param.prodCd = $("#detailForm #prodCd").val();
			param.vendorId = $("#detailForm #vendorId").val();
			param.prodLinkKindCd = $("#detailForm #prodLinkKindCd").val();
			
		    var sUrl = '<c:url value="/product/saveProdComponent.do"/>';
		    mySheet.DoSave(sUrl, {Param:$.param(param), Col:1, Quest:false, Sync:2});
		}
	}
</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>

<form name="detailForm" id="detailForm">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
<input type="hidden" id="prodLinkKindCd" name="prodLinkKindCd" value="05" />

<div id="wrap_menu" style="width:1000px;">
	<!-- 상품 상세보기 하단 탭 -->
	<c:set var="tabNo" value="<%=tabNo%>" />      
	<c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
		<c:param name="tabNo" value="${tabNo}" />
		<c:param name="prodCd" value="<%=prodCd%>" />
	</c:import>
    
    <!-- 2 검색내역  -->
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">연관상품목록</li>
                <li class="btn">
                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                    <a href="#" class="btn" id="delete"><span><spring:message code="button.common.delete"/></span></a>
                    <a href="#" class="btn" id="add"><span>추가</span></a>
                    <a href="#" class="btn" id="save"><span><spring:message code="button.common.save" /></span></a>
                </li>
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

<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="infoGrpCd" name="infoGrpCd" value="" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>

</body>
</html>