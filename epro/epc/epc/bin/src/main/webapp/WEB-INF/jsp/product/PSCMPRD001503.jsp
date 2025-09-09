<%--
- Author(s): cwj
- Created Date: 2019.12.15
- Version : 1.0
- Description : epc 상품 공통 팝업

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="lcn.module.common.util.DateUtil"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<script type="text/javascript">
$(document).ready(function(){

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "253px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

	var ibdata = {};
	// SizeMode:
	//ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:2, MergeSheet:msHeaderOnly}; // 10 row씩 Load

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	if( "<c:out value="${ mode }"/>" == "M" ){
		ibdata.Cols = [
		    {Header:"순번|순번", 		   Type:"Int", 	    SaveName:"SEQ", 		  Align:"Center", Width:33,  Height:18, Edit:0}
		  , {Header:"|", 			   Type:"CheckBox", SaveName:"chk", 		  Align:"Center", Width:33,  Height:18}
		  , {Header:"인터넷상품코드|인터넷상품코드",  Type:"Text", 	SaveName:"PROD_CD", 	  Align:"Center", Width:70,  Height:18, Edit:0, Cursor:'pointer', Color:'blue'}
		  , {Header:"상품코드|상품코드", 	   Type:"Text", 	SaveName:"REP_PROD_CD",   Align:"Center", Width:80,  Edit:0}
		  , {Header:"판매코드|판매코드", 	   Type:"Text", 	SaveName:"MD_SRCMK_CD",   Align:"Center", Width:80,  Edit:0}
		  , {Header:"인터넷상품명|인터넷상품명",    Type:"Text", 	SaveName:"PROD_NM", 	  Align:"Center", Width:60,  Edit:0}
		  , {Header:"변경사유|변경사유",    Type:"Text", 	SaveName:"REQ_REASON_CONTENT", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}

		  , {Header:"상품구분코드|상품구분코드",    Type:"Text", 	SaveName:"PROD_DIVN_CD",  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"거래처ID|거래처ID",        Type:"Text", 	SaveName:"VENDOR_ID", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"전시여부|전시여부",        Type:"Text", 	SaveName:"DISP_YN", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"원가|원가",            Type:"Text", 	SaveName:"BUY_PRC", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"매가|매가",            Type:"Text", 	SaveName:"SELL_PRC", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"판매가|판매가",          Type:"Text", 	SaveName:"CURR_SELL_PRC", Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"임직원할인가|임직원할인가",    Type:"Text", 	SaveName:"STAFF_DC_AMT",  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"승인여부|승인여부",        Type:"Text", 	SaveName:"APRV_YN",       Align:"Center", Width:40,  Edit:0, Hidden:0}
		  , {Header:"등록일|등록일",          Type:"Text", 	SaveName:"REG_DATE",      Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"판매상태|판매상태",        Type:"Text", 	SaveName:"SELL_FLAG",     Align:"Center", Width:40,  Edit:0, Hidden:1}
		  , {Header:"재고수량|재고수량",        Type:"Text", 	SaveName:"RSERV_STK_QTY",     Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"옵션상품가격관리여부|옵션상품가격관리여부", Type:"Text", SaveName:"OPTN_PROD_PRC_MGR_YN", Align:"Center", Width:60,  Edit:0, Hidden:1}

		  //20160802 공헌이익 추가
		  ,{Header:"공헌이익Ⅰ|목표",            Type:"Float",   SaveName:"TGT_CONTB_PRFT1", Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
	      ,{Header:"공헌이익Ⅰ|현재",             Type:"Float",   SaveName:"CONTB_PRFT_1",    Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
	      ,{Header:"공헌이익Ⅱ|목표",             Type:"Float",   SaveName:"TGT_CONTB_PRFT2", Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
	      ,{Header:"공헌이익Ⅱ|현재",             Type:"Float",   SaveName:"CONTB_PRFT_2",    Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
	      ,{Header:"과세구분코드|과세구분코드",       Type:"Text",   SaveName:"TAXAT_DIVN_CD",    Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
		];
	}else{
		ibdata.Cols = [
		    {Header:"순번|순번", 		   Type:"Int", 	    SaveName:"SEQ", 		  Align:"Center", Width:33,  Height:18, Edit:0}
		  , {Header:"인터넷상품코드|인터넷상품코드",  Type:"Text", 	SaveName:"PROD_CD", 	  Align:"Center", Width:70,  Height:18, Edit:0, Cursor:'pointer', Color:'blue'}
		  , {Header:"상품코드|상품코드", 	   Type:"Text", 	SaveName:"REP_PROD_CD",   Align:"Center", Width:80,  Edit:0}
		  , {Header:"판매코드|판매코드", 	   Type:"Text", 	SaveName:"MD_SRCMK_CD",   Align:"Center", Width:80,  Edit:0}
		  , {Header:"인터넷상품명|인터넷상품명",    Type:"Text", 	SaveName:"PROD_NM", 	  Align:"Center", Width:60,  Edit:0}
		  , {Header:"변경사유|변경사유",    Type:"Text", 	SaveName:"REQ_REASON_CONTENT", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}

		  , {Header:"상품구분코드|상품구분코드",    Type:"Text", 	SaveName:"PROD_DIVN_CD",  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"거래처ID|거래처ID",        Type:"Text", 	SaveName:"VENDOR_ID", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"전시여부|전시여부",        Type:"Text", 	SaveName:"DISP_YN", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"원가|원가",            Type:"Text", 	SaveName:"BUY_PRC", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"매가|매가",            Type:"Text", 	SaveName:"SELL_PRC", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"판매가|판매가",          Type:"Text", 	SaveName:"CURR_SELL_PRC", Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"임직원할인가|임직원할인가",    Type:"Text", 	SaveName:"STAFF_DC_AMT",  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"승인여부|승인여부",        Type:"Text", 	SaveName:"APRV_YN",       Align:"Center", Width:40,  Edit:0, Hidden:0}
		  , {Header:"등록일|등록일",          Type:"Text", 	SaveName:"REG_DATE",      Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"판매상태|판매상태",        Type:"Text", 	SaveName:"SELL_FLAG",     Align:"Center", Width:40,  Edit:0, Hidden:1}
		  , {Header:"재고수량|재고수량",        Type:"Text", 	SaveName:"RSERV_STK_QTY", Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"옵션상품가격관리여부|옵션상품가격관리여부", Type:"Text", SaveName:"OPTN_PROD_PRC_MGR_YN", Align:"Center", Width:60,  Edit:0, Hidden:1}

		  //20160802 공헌이익 추가
          ,{Header:"공헌이익Ⅰ|목표",            Type:"Float",   SaveName:"TGT_CONTB_PRFT1", Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
          ,{Header:"공헌이익Ⅰ|현재",             Type:"Float",   SaveName:"CONTB_PRFT_1",    Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
          ,{Header:"공헌이익Ⅱ|목표",             Type:"Float",   SaveName:"TGT_CONTB_PRFT2", Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
          ,{Header:"공헌이익Ⅱ|현재",             Type:"Float",   SaveName:"CONTB_PRFT_2",    Align:"Center", Width:40,  Edit:0, PointCount:2, Hidden:1}
          ,{Header:"과세구분코드|과세구분코드",       Type:"Text",   SaveName:"TAXAT_DIVN_CD",    Align:"Center", Width:40,  Edit:0, PointCount:2 ,Hidden:1}
		];
	}

	IBS_InitSheet(mySheet, ibdata);

	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.FitColWidth();


	// 선택버튼 클릭
	$(document).on("click","#selection",function(){

		if(  mySheet.CheckedRows("chk") == 0){
			alert("선택된 상품이 없습니다.");
			return;
		}else{

			//현재 이익율이 목표 공헌이익율보다 높은지 체크
			 fn_coutbChk();

		}

		prodCdArray = new Array();        // (인터넷상품코드)
		repProdCdArray = new Array();     // (대표상품코드)
		mdSrcmkCdArray = new Array();     // (MD판매코드)
		prodNmArray = new Array();        // (인터넷상품명)
		//reqReasonContentArray = new Array();        // (변경사유)

		prodDivnCdArray = new Array();    // (상품구분코드)
		vendorIdArray = new Array();      // (거래처ID)
		dispYnArray = new Array();        // (전시여부)
		buyPrcArray = new Array();        // (원가)
		sellPrcArray = new Array();       // (매가)
		currSellPrcArray = new Array();   // (판매가)
		staffDcAmtArray = new Array();    // (임직원할인가)

		sellFlagArray = new Array();      // (판매상태)
		rservStkQtyArray = new Array();      // (재고수량)
		optnProdPrcMgrYnArray  = new Array();      // (옵션상품가격관리여부)
		taxatDivnCdArray = new Array();   // (과세구분코드)

		productInfo = new Object();

		for(var i = 0 ; i < mySheet.RowCount(); i++){
   			if( "<c:out value="${ mode }"/>" == "M" ){
				if( mySheet.GetCellValue(i+2, 1) == 1 ){
	   				prodCdArray.push( mySheet.GetCellValue(i+2, "PROD_CD") );          // (인터넷상품코드)
	   				repProdCdArray.push( mySheet.GetCellValue(i+2, "REP_PROD_CD") );       // (대표상품코드)
	   				mdSrcmkCdArray.push( mySheet.GetCellValue(i+2, "MD_SRCMK_CD") );       // (MD판매코드)
	   				prodNmArray.push( mySheet.GetCellValue(i+2, "PROD_NM") );          // (인터넷상품명)
	   				//reqReasonContentArray.push( mySheet.GetCellValue(i+2, "REQ_REASON_CONTENT") ); // (변경사유)
	   				//reqReasonContentArray.push(""); // (변경사유)
	   				

	   				prodDivnCdArray.push( mySheet.GetCellValue(i+2,       "PROD_DIVN_CD") );      // (상품구분코드)
	   				vendorIdArray.push( mySheet.GetCellValue(i+2,         "VENDOR_ID") );        // (거래처ID)
	   				dispYnArray.push( mySheet.GetCellValue(i+2,           "DISP_YN") );          // (전시여부)
	   				buyPrcArray.push( mySheet.GetCellValue(i+2,           "BUY_PRC") );          // (원가)
	   				sellPrcArray.push( mySheet.GetCellValue(i+2,          "SELL_PRC") );        // (매가)
	   				currSellPrcArray.push( mySheet.GetCellValue(i+2,      "CURR_SELL_PRC") );    // (판매가)
	   				staffDcAmtArray.push( mySheet.GetCellValue(i+2,       "STAFF_DC_AMT") );     // (임직원할인가)

	   				sellFlagArray.push( mySheet.GetCellValue(i+2,         "SELL_FLAG") );     // (판매상태)
	   				rservStkQtyArray.push( mySheet.GetCellValue(i+2,      "RSERV_STK_QTY") );     // (재고수량)
	   				optnProdPrcMgrYnArray.push( mySheet.GetCellValue(i+2, "OPTN_PROD_PRC_MGR_YN") );     // (옵션상품가격관리여부)
	   				taxatDivnCdArray.push( mySheet.GetCellValue(i+2, "TAXAT_DIVN_CD") );     // (과세구분코드)
				}
   			}
		}
 		productInfo.prodCdArr        					= prodCdArray;          		// (인터넷상품코드)
		productInfo.repProdCdArr     					= repProdCdArray;       		// (대표상품코드)
		productInfo.mdSrcmkCdArr     				= mdSrcmkCdArray;       	// (MD판매코드)
		productInfo.prodNmArr        					= prodNmArray;          		// (인터넷상품명)
		//productInfo.reqReasonContentArray        = reqReasonContentArray;  // (변경사유)

		productInfo.prodDivnCdArr    = prodDivnCdArray;      // (상품구분코드)
		productInfo.vendorIdArr      = vendorIdArray;        // (거래처ID)
		productInfo.dispYnArr        = dispYnArray;          // (전시여부)
		productInfo.buyPrcArr        = buyPrcArray;          // (원가)
		productInfo.sellPrcArr       = sellPrcArray;         // (매가)
		productInfo.currSellPrcArr   = currSellPrcArray;     // (판매가)
		productInfo.staffDcAmtArr    = staffDcAmtArray;      // (임직원할인가)

		productInfo.sellFlagArr      = sellFlagArray;        // (판매상태)
		productInfo.rservStkQtyArr   = rservStkQtyArray;     // (재고수량)
		productInfo.optnProdPrcMgrYnArr   = optnProdPrcMgrYnArray;     // (옵션상품가격관리여부)
		productInfo.taxatDivnCdArray   = taxatDivnCdArray;     // (과세구분코드)

		opener.fnSetProduct(productInfo);
		self.close();

	});

	// 닫기버튼 클릭
    $('#close').click(function() {
		self.close();
    });

	//exeExcel양식다운
	$('#createExcelForm').click(function () {
		if (!confirm('엑셀 양식을 다운로드하시겠습니까?')) {
			return;
		}
	      
		hidenCol = "";
		if( "<c:out value="${ mode }"/>" == "M" ){
			hidenCol="SEQ|chk|REP_PROD_CD|MD_SRCMK_CD|PROD_NM|PROD_DIVN_CD|VENDOR_ID|DISP_YN|BUY_PRC|SELL_PRC|CURR_SELL_PRC|STAFF_DC_AMT|APRV_YN|SELL_FLAG|TGT_CONTB_PRFT1|TGT_CONTB_PRFT2|CONTB_PRFT_1|CONTB_PRFT_2";
		}else{
			hidenCol="SEQ|REP_PROD_CD|MD_SRCMK_CD|PROD_NM|PROD_DIVN_CD|VENDOR_ID|DISP_YN|BUY_PRC|SELL_PRC|CURR_SELL_PRC|STAFF_DC_AMT|APRV_YN|SELL_FLAG|TGT_CONTB_PRFT1|TGT_CONTB_PRFT2|CONTB_PRFT_1|CONTB_PRFT_2";
		}
		excelFormDown(mySheet, '상품조회_양식', 1, hidenCol);
	});

	//찾아보기
	$('#findFile').click(function() {
		$('#file').click();
    });

	$('#file').change(function() {
		msgPath = "파일이 업로드 되었습니다 업로드 버튼을 눌러주세요";
		if( $('#file').val().length > 0 ){ // 파일 선택 시
			$("#excelOp").val(msgPath);
		}else{
			$("#excelOp").val("");
		}
    });

	//excelRemove초기화
	$('#excelRemove').click(function() {
		mySheet.RemoveAll();
		$('#excelOp').attr({ value: '' });
		$('#file').attr({ value: '' });
		prodCdArr = new  Array();
    });

    // 엑셀파일 업로드 (서버 통신 필요)
	$('#uploadExcel').click(function () {
		prodCdArr = new  Array();
		if( $("#file").val()==""){
			alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
			return;
		}

		$("#filefrm").attr("src","load");
		$("#dataForm").attr("enctype","multipart/form-data");
		document.dataForm.action = '<c:url value="/excelLoad/IBSheetExcelLoad.do"/>';
		document.dataForm.target = "filefrm";
		document.dataForm.submit();
		$("#dataForm").attr("enctype","");
	});

	$('#filefrm').load( function(){
		if( $("#filefrm").attr("src") != ""){
			console.log("=============");
			doSearch();
		}
	});

});


// ibsheet에서 클릭시 작동
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	//특정 열을 클릭했을 때 다른 페이지로 이동하도록 처리
	if( "<c:out value="${ mode }"/>" == "M" ){
	}else{
		if( Row >= 2 ){
			if( mySheet.GetCellValue(Row, "APRV_YN") == 'Y' ){			
				setParentData(Row);
			}else{
				alert("승인되지 않은 데이터 입니다.");
			}
		}
	}
}

// 클릭시 mode 가 M이 아닌 경우에만 처리함
function setParentData(Row){

	productInfo  = new Object();

	prodCdArray = new Array();        // (인터넷상품코드)
	repProdCdArray = new Array();     // (대표상품코드)
	mdSrcmkCdArray = new Array();     // (MD판매코드)
	prodNmArray = new Array();        // (인터넷상품명)
	//reqReasonContentArray = new Array();        // (변경사유)

	prodDivnCdArray = new Array();    // (상품구분코드)
	vendorIdArray = new Array();      // (거래처ID)
	dispYnArray = new Array();        // (전시여부)
	buyPrcArray = new Array();        // (원가)
	sellPrcArray = new Array();       // (매가)
	currSellPrcArray = new Array();   // (판매가)
	staffDcAmtArray = new Array();    // (임직원할인가)

	sellFlagArray = new Array();      // (판매상태)
	rservStkQtyArray = new Array();   // (재고수량)
	optnProdPrcMgrYnArray = new Array();   // (옵션상품가격관리여부)
	taxatDivnCdArray = new Array();   // (과세구분코드)
		
	prodCdArray.push( mySheet.GetCellValue(Row,           "PROD_CD") );                    // (인터넷상품코드)
	repProdCdArray.push( mySheet.GetCellValue(Row,        "REP_PROD_CD") );                // (대표상품코드)
	mdSrcmkCdArray.push( mySheet.GetCellValue(Row,        "MD_SRCMK_CD") );                // (MD판매코드)
	prodNmArray.push( mySheet.GetCellValue(Row,           "PROD_NM") );                    // (인터넷상품명)
	//reqReasonContentArray.push( mySheet.GetCellValue(Row,           "REQ_REASON_CONTENT") );                    // (변경사유)
	//reqReasonContentArray.push("");                    // (변경사유)
	                                                                                    
	prodDivnCdArray.push( mySheet.GetCellValue(Row,       "PROD_DIVN_CD") );               // (상품구분코드)
	vendorIdArray.push( mySheet.GetCellValue(Row,         "VENDOR_ID") );                  // (거래처ID)
	dispYnArray.push( mySheet.GetCellValue(Row,           "DISP_YN") );                    // (전시여부)
	buyPrcArray.push( mySheet.GetCellValue(Row,           "BUY_PRC") );                    // (원가)
	sellPrcArray.push( mySheet.GetCellValue(Row,          "SELL_PRC") );                   // (매가)
	currSellPrcArray.push( mySheet.GetCellValue(Row,      "CURR_SELL_PRC") );              // (판매가)
	staffDcAmtArray.push( mySheet.GetCellValue(Row,       "STAFF_DC_AMT") );               // (임직원할인가)
                                                                                    
	sellFlagArray.push( mySheet.GetCellValue(Row,         "SELL_FLAG") );                  // (판매상태)
	rservStkQtyArray.push( mySheet.GetCellValue(Row,      "RSERV_STK_QTY") );              // (재고수량)
	optnProdPrcMgrYnArray.push( mySheet.GetCellValue(Row, "OPTN_PROD_PRC_MGR_YN") );       // (옵션상품가격관리여부)
	taxatDivnCdArray.push( mySheet.GetCellValue(Row, "TAXAT_DIVN_CD") );       // (과세구분코드)

	productInfo.prodCdArr        = prodCdArray;          // (인터넷상품코드)
	productInfo.repProdCdArr     = repProdCdArray;       // (대표상품코드)
	productInfo.mdSrcmkCdArr     = mdSrcmkCdArray;       // (MD판매코드)
	productInfo.prodNmArr        = prodNmArray;          // (인터넷상품명)
	//productInfo.reqReasonContentArray        = "";          // (변경사유)

	productInfo.prodDivnCdArr    = prodDivnCdArray;      // (상품구분코드)
	productInfo.vendorIdArr      = vendorIdArray;        // (거래처ID)
	productInfo.dispYnArr        = dispYnArray;          // (전시여부)
	productInfo.buyPrcArr        = buyPrcArray;          // (원가)
	productInfo.sellPrcArr       = sellPrcArray;         // (매가)
	productInfo.currSellPrcArr   = currSellPrcArray;     // (판매가)
	productInfo.staffDcAmtArr    = staffDcAmtArray;      // (임직원할인가)

	productInfo.sellFlagArr      = sellFlagArray;        // (판매상태)
	productInfo.rservStkQtyArr   = rservStkQtyArray;     // (재고수량)
	productInfo.optnProdPrcMgrYnArr   = optnProdPrcMgrYnArray;     // (옵션상품가격관리여부)
	productInfo.taxatDivnCdArray   = taxatDivnCdArray;     // (과세구분코드)

	opener.fnSetProduct(productInfo);
	self.close();
}

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}

//페이지 이동시
// 파일업로드시 데이터를 참조해서 처리 할 경우가 발생함
prodCdArr = new  Array();
function goPage(currentPage){
	$("#dataForm").attr("enctype","");
	var mode               		= $('#mode').val();
	var aprvYn             		= $('#aprvYn').val();
	var periDeli           			= $('#periDeli').val();
	var onOffYn            		= $('#onOffYn').val();
	var dealYn             		= $('#dealYn').val();
	var ctpdYn             		= $('#ctpdYn').val();
	var dealCtpdYn         		= $('#dealCtpdYn').val();
	var productType        		= $('#productType option:selected').val();
	var productSearValue   	= $('#productSearValue').val();
	var vendorId					= $('#vendorId').val();
	var url = '<c:url value="/product/repProdCd/selectRepChgProdCdSearch.do"/>';

	var param = new Object();
	param.mode             		= mode;
	param.aprvYn           		= aprvYn;
	param.periDeli         		= periDeli;
	param.onOffYn          		= onOffYn;
	param.dealYn           		= dealYn;
	param.ctpdYn           		= ctpdYn;
	param.dealCtpdYn       	= dealCtpdYn;
	param.productType      	= productType;
	param.productSearValue 	= productSearValue;
	param.rowsPerPage 	   	= $("#rowsPerPage").val();
	param.prodCdArr 	   		= prodCdArr;	
	param.vendorId 	   			= vendorId;	
	$.ajaxSettings.traditional = true;
	loadIBSheetData(mySheet, url, currentPage, null, param);
}


function setData( PROD_CD ){
	prodCdArr.push( PROD_CD );
}

//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd() {
	// 조회된 데이터가 승인완료인 경우는 체크 금지
 	var rowCount = mySheet.RowCount();
	for (var i=0; i<rowCount; i++) {

		if( "<c:out value="${ mode }"/>" == "M" ){
			if( "<c:out value="${ aprvYn }"/>" != "Y" ){
			// 승인
				if( mySheet.GetCellValue(i+2, "APRV_YN") != 'Y' ){
			    	mySheet.SetCellEditable(i+2, 'chk', false);
			    	setColor(i+2,'#FFFF00');
				}
			}
		}else{
			if( "<c:out value="${ aprvYn }"/>" != "Y" ){
			// 승인
				if( mySheet.GetCellValue(i+2, "APRV_YN") != 'Y' ){
			    	setColor(i+2,'#FFFF00');
				}
			}
		}
		if( mySheet.GetCellValue(i+2, "REG_DATE") == "N"  ){
			setColor(i+2,'#FF0000');
		}
	}

	//공헌이익율 컬럼 색 변경
	fn_contbColor();

}

function setColor(row,color){
	mySheet.SetCellBackColor(row, 1, color);
	mySheet.SetCellBackColor(row, 2, color);
	mySheet.SetCellBackColor(row, 3, color);
	mySheet.SetCellBackColor(row, 4, color);
	mySheet.SetCellBackColor(row, 5, color);
	mySheet.SetCellBackColor(row, 6, color);
	mySheet.SetCellBackColor(row, 7, color);
	mySheet.SetCellBackColor(row, 8, color);
	mySheet.SetCellBackColor(row, 9, color);
	mySheet.SetCellBackColor(row, 10, color);
	mySheet.SetCellBackColor(row, 11, color);
	mySheet.SetCellBackColor(row, 12, color);
	mySheet.SetCellBackColor(row, 13, color);
	mySheet.SetCellBackColor(row, 14, color);

	if(row > 1){ //공헌이익 항목
		mySheet.SetCellBackColor(row, "TGT_CONTB_PRFT1", color);
		mySheet.SetCellBackColor(row, "CONTB_PRFT_1", color);
		mySheet.SetCellBackColor(row, "TGT_CONTB_PRFT2", color);
		mySheet.SetCellBackColor(row, "CONTB_PRFT_2", color);
	}

	if( "<c:out value="${ mode }"/>" == "M" ){  // 15
    	mySheet.SetCellBackColor(row, 15, color);
	}
}

//공헌이익 컬럼 색상 변경
function fn_contbColor(){

	var rowCnt = mySheet.RowCount();
    var tgtContbPrft1 = ""; // 목표공헌이익 1
    var tgtContbPrft2 = ""; // 목표공헌이익 2
    var contbPrft1 = ""; // 현재공헌이익 1
    var contbPrft2 = ""; // 현재공헌이익 2

    var resultContbPrtf1 = 0; // 현재공헌이익1이 목표공헌이익1보다 낮은 경우 +1
    var resultContbPrtf2 = 0; // 현재공헌이익2이 목표공헌이익2보다 낮은 경우 +1

    for(var i = 0; i < rowCnt; i++){

        tgtContbPrft1 = mySheet.GetCellValue(i+2, "TGT_CONTB_PRFT1");
        tgtContbPrft2 = mySheet.GetCellValue(i+2, "TGT_CONTB_PRFT2");
        contbPrft1 = mySheet.GetCellValue(i+2, "CONTB_PRFT_1");
        contbPrft2 = mySheet.GetCellValue(i+2, "CONTB_PRFT_2");

        if( String(tgtContbPrft1).length > 0 && String(contbPrft1).length > 0 ){ //목표공헌이익1과 현재공헌이익1 비교
            if(Number(tgtContbPrft1) > Number(contbPrft1)){ //목표공헌이익보다 작으면
                mySheet.SetCellFontColor(i+2,"CONTB_PRFT_1","#FF0000") ; //글씨색 빨간색으로 표시
            }
        }
        if( String(tgtContbPrft2).length > 0 && String(contbPrft2).length > 0 ){ //목표공헌이익2과 현재공헌이익2 비교
            if(Number(tgtContbPrft2) > Number(contbPrft2)){ //목표공헌이익보다 작으면
                mySheet.SetCellFontColor(i+2,"CONTB_PRFT_2","#FF0000") ; //글씨색 빨간색으로 표시
            }
        }
    }

}

//상품 선택 시 공헌이익 체크
function fn_coutbChk(){

	var rowCnt = mySheet.GetTotalRows();
    var tgtContbPrft1 = ""; // 목표공헌이익 1
    var tgtContbPrft2 = ""; // 목표공헌이익 2
    var contbPrft1 = ""; // 현재공헌이익 1
    var contbPrft2 = ""; // 현재공헌이익 2

    var resultContbPrtf1 = 0; // 현재공헌이익1이 목표공헌이익1보다 낮은 경우 +1
    var resultContbPrtf2 = 0; // 현재공헌이익2이 목표공헌이익2보다 낮은 경우 +1

    var checkCnt = 0; //체크한 카테고리 수

    for(var i = 1; i < rowCnt +1; i++){

        if(mySheet.GetCellValue(i, "chk") == 1){
           tgtContbPrft1 = mySheet.GetCellValue(i, "TGT_CONTB_PRFT1");
           tgtContbPrft2 = mySheet.GetCellValue(i, "TGT_CONTB_PRFT2");
           contbPrft1 = mySheet.GetCellValue(i, "CONTB_PRFT_1");
           contbPrft2 = mySheet.GetCellValue(i, "CONTB_PRFT_2");

           if(tgtContbPrft1 != "" && contbPrft1 != ""){ //목표공헌이익1과 현재공헌이익1 비교
               if(eval(tgtContbPrft1) > eval(contbPrft1)){
                   resultContbPrtf1++;
               }
           }

           if(tgtContbPrft1 != "" && contbPrft1 != ""){ //목표공헌이익2과 현재공헌이익2 비교
               if(eval(tgtContbPrft2) > eval(contbPrft2)){
                  resultContbPrtf2++;
               }
           }
           checkCnt++;
        }
    }
}

</script>

</head>
<body>
<form name="dataForm" id="dataForm" method="post">
	<input type="hidden" name="mode"       id="mode"       value="<c:out value="${ mode }"/>"/>
	<input type="hidden" name="aprvYn"     id="aprvYn"     value="<c:out value="${ fn:toUpperCase(aprvYn) }"/>"/>
	<input type="hidden" name="periDeli"   id="periDeli"   value="<c:out value="${ fn:toUpperCase(periDeli) }"/>"/>
	<input type="hidden" name="onOffYn"    id="onOffYn"    value="<c:out value="${ fn:toUpperCase(onOffYn) }"/>"/>
	<input type="hidden" name="dealCtpdYn" id="dealCtpdYn" value="<c:out value="${ fn:toUpperCase(dealCtpdYn) }"/>"/>
	<input type="hidden" name="dealYn"     id="dealYn"     value="<c:out value="${ fn:toUpperCase(dealYn) }"/>"/>
	<input type="hidden" name="ctpdYn"     id="ctpdYn"     value="<c:out value="${ fn:toUpperCase(ctpdYn) }"/>"/>
	
	<div id="wrap_menu">
	<!--	@ 검색조건	-->
	<div class="wrap_search">	
	<!--  @title  -->
     <div id="p_title1">
		<h1>상품선택
			<c:choose>
				<c:when test="${ fn:toUpperCase(aprvYn) eq 'Y' }">(승인)</c:when>
				<c:when test="${ fn:toUpperCase(aprvYn) eq 'N' }">(미승인)</c:when>
				<c:otherwise>(전체)</c:otherwise>
			</c:choose>		
		</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
	
		<div class="bbs_search">
			<ul class="tit">				
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="javascript:doSearch();" class='btn' id='search'><span><spring:message code="button.common.inquire"/></span></a>
				<%--<c:if test="${ mode eq 'M' }">
						<a href="#" id="selection"></span><spring:message code="button.common.selecter"/></span></a>
					</c:if> --%>
					<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>

		<ul>
	
		  <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="bbs_search" summary="신상품코드검증" >
		    <col width="20%" />
		    <col width="80%" />
		    <col width="" />
		    <col width=" " />
		    <tr>
		    	<th>협력업체코드</th>
                <td>
					<c:choose>
						<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
							<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
							<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
						</c:when>
						<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
							<select name="vendorId" id="vendorId" class="select" style="width:150px;">
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
		       <th style="align:center;" scope="col">
					<select name="productType" id="productType">
						<option value="01">인터넷상품코드</option>
						<option value="02">MD상품코드</option>
						<option value="03">MD판매코드</option>
						<option value="04">상품명</option>
					</select>
	          </th>
		      <td scope="col" align="left">
		      	<input type="text" name="productSearValue" id="productSearValue"/>
				<input style="VISIBILITY: hidden; WIDTH: 0px"/>
	    	  </td>

		    </tr>
		    <tr>
		    	<th style="align:center;" scope="col">파일업로드조회</th>
		    	<td scope="col" align="left">
		    		<input id="excelOp" name="excelOp" type="text" class="text" style="width:35%;" />
					<input type="file" name="file" id="file"  class="text" style="display:none;"  value=""  />
					<input type="hidden" name="colNms" size="35" value="PROD_CD" /><!-- IBSheet 칼럼명 (구분자 ^, 필수 파라미터) -->
					<input type="hidden" name="hdRow" value="1" /><!-- IBSheet Header 행 수 (필수 파라미터) -->
					<input type="hidden" name="func" value="setData" /><!-- 실행 자바스크립트 명 ( 필수 파리미터) -->
					<input type="hidden" name="sheetNm" value="mySheet" /><!-- IBSheet 명 (필수 파라미터)-->
					<input type="hidden" name="sheetRemoveAll" value="Y" />

					<a href='#' class='btn' id='createExcelForm'><span>양식</span></a>
		    		<a href='#' class='btn' id='findFile'><span>파일찾기</span></a>
		    		<a href='#' class='btn' id='uploadExcel'><span>업로드</span></a>
		    		<a href='#' class='btn' id='excelRemove'><span>엑셀삭제</span></a>		    		
					<br/>
					* 엑셀파일 작성 시 셀서식의 표시형식을 반드시 [텍스트]로 설정해 주세요.
		    	</td>
		    </tr>
		  </table>
		  
		  </div>
		 </div>

		<div class="wrap_con">
				<!-- list -->
			   	<div class="bbs_list">
			   		<ul class="tit">	      		
			      		<li class="tit" style="float:left;width:10%;">조회결과</li>
			      	</ul>
			      	<ul>
			      		<li class="lp" id="pagingDivCnt">
			      	</ul>
					<ul>
					    <li class="lp">
							<table width="99.5%" border="0" cellpadding="0" cellspacing="1" class="tbl_01" summary=" "  id="sheetTbl">
								<col width="100%" />
								<tr>
									<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
								</tr>
							</table>
					    </li>
					</ul>
				</div>
		</div>

	    <div id="pagingDiv" class="pagingbox1"  style="width: 100%;">
	           <script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script> <!-- 페이징 -->
	    </div>	  
	</div>
</form>

<iframe id="filefrm" name="filefrm" src="" marginwidth="0" marginheight="0" frameborder="3"  bordercolor="red" width="0" height="0" scrolling="yes"></iframe>
</body>
</html>