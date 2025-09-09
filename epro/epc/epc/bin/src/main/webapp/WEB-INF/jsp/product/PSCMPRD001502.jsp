<%--
- Author(s): cwj
- Created Date: 2019.12.13
- History : 
- Version : 1.0
- Description : 대표상품코드 등록 팝업화면
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
<%
String sCode = "";
String sName = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<!-- PSCMPRD001502 -->
<head> 
<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */

$(document).ready(function(){

	// IBSheet 설정 시작
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "332px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

	var ibdata = {};

	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류
	ibdata.Cfg = { SearchMode:smLazyLoad, MergeSheet:msHeaderOnly, VScrollMode:1};
	//ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};

	// IBSheet 컬럼 설정
	ibdata.Cols = [
		  {Header:"순번|순번",				SaveName:"SEQ",						Type:"Int",			Align:"Center", Width:40,	Edit:0}
		, {Header:"",					SaveName:"SELECTED",				Type:"CheckBox",	Align:"Center", Width:30,	Edit:1}
		, {Header:"상품코드|상품코드",		SaveName:"PROD_CD",					Type:"Text",		Align:"Center", Width:100,	Edit:0}
		, {Header:"단품코드|단품코드",		SaveName:"ITEM_CD",					Type:"Text",		Align:"Center", Width:60,  	Edit:0}
		, {Header:"상품명|상품명",			SaveName:"PROD_NM",					Type:"Text",		Align:"Left", 	Width:170,	Edit:0}
		, {Header:"변경전|적용시작일자",		SaveName:"BEFORE_APPLY_START_DY",	Type:"Text",		Align:"Center", Width:80,	Edit:0}
		, {Header:"변경전|이익율",			SaveName:"BEFORE_PROFIT_RATE",		Type:"Int",			Align:"Right", 	Width:50,	Edit:0}
		, {Header:"변경전|판매가",			SaveName:"BEFORE_CURR_SELL_PRC",	Type:"Int",			Align:"Right", 	Width:70,	Edit:0, Format:'#,###'}
		, {Header:"변경후|적용시작일자",		SaveName:"APPLY_START_DY",			Type:"Text",		Align:"Center", Width:80,	Edit:0, Format:'yyyy-MM-dd'}
		, {Header:"변경후|적용종료일자",		SaveName:"APPLY_END_DY",			Type:"Text",		Align:"Center", Width:80,	Edit:0, Format:'yyyy-MM-dd'}
		, {Header:"변경후|대표상품코드",		SaveName:"REP_PROD_CD",				Type:"Combo",		Align:"Center", Width:150,	Edit:1}
		, {Header:"변경후|변경요청사유 및 비고",	SaveName:"REQ_REASON_CONTENT",		Type:"Text",		Align:"Right", 	Width:150,	Edit:1}
		, {Header:"변경후|판매가",			SaveName:"CURR_SELL_PRC",			Type:"Int",			Align:"Right", 	Width:70,	Edit:1, Format:'#,###'}
		, {Header:"변경후|이익율",			SaveName:"PROFIT_RATE",				Type:"Int",			Align:"Right", 	Width:50,	Edit:0}
		, {Header:"프로모션개수|프로모션개수",	SaveName:"PROMO_CNT",				Type:"Int",			Align:"Right", 	Width:50,	Edit:0}
		, {Header:"상태",					SaveName:"S_STATUS",				Type:"Status",		Align:"Center", Hidden:true}
		, {Header:"과세구분코드",			SaveName:"TAXAT_DIVN_CD",			Type:"Text",		Align:"Center", Hidden:true}
		, {Header:"옵션연동여부",			SaveName:"OPTN_PROD_PRC_MGR_YN",	Type:"Text",		Align:"Center", Hidden:true}
	];

	// IBSheet 초기화
	IBS_InitSheet(mySheet, ibdata);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	//mySheet.FitColWidth();
	
	$(window).resize(function() {
		$("#ibsheet1").css("width", $(window).width() - 28);
		mySheet.FitColWidth();
	});
	
	// IBSheet 설정 시작 엑셀업로드 용
	createIBSheet2(document.getElementById("ibsheet2"), "mySheet1", "100%", "400px");
	mySheet1.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

	var ibdata1 = {};

	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류
	ibdata1.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata1.HeaderMode = {Sort:0, ColMove:0, ColResize:0, HeaderCheck:1};

	// IBSheet 컬럼 설정
	ibdata1.Cols = [
		  {Header:"상품코드",			SaveName:"PROD_CD",				Type:"Text",	Align:"Center",	Width:100}
		, {Header:"단품코드",			SaveName:"ITEM_CD",				Type:"Text",	Align:"Right", 	Width:70}
		, {Header:"변경요청사유 및 비고",	SaveName:"REQ_REASON_CONTENT",	Type:"Text",	Align:"Right", 	Width:130}
		, {Header:"판매가",			SaveName:"CURR_SELL_PRC",		Type:"Int",		Align:"Right", 	Width:70}
		, {Header:"상태",				SaveName:"S_STATUS",			Type:"Status",	Align:"Center", Hidden:true}
	];

	// IBSheet 초기화
	IBS_InitSheet(mySheet1, ibdata1);
	mySheet1.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet1.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet1.FitColWidth();
	
	//input enter 막기
	$("*").keypress(function(e){
         if(e.keyCode==13) return false;
	});
	
	//-----------------
	// 닫기
	//-----------------
    $('#close').click(function() {
    	opener.doSearch();
    	top.close();
    });

	// 대표상품코드
	setRepProdCdComboList();
	
	$("#dataForm select[name=vendorId]").change(function() {
		//setRepProdCdComboList($(this).val().replace(/\s/gi, ''));
		setRepProdCdComboList();
	});

	//-----------------
	// 찾아보기
	//-----------------
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
	
    // 엑셀파일 업로드 (서버 통신 필요)
	$('#uploadExcel').click(function () {
		excelImport();
	});

	//-----------------
	// 찾아보기
	//-----------------
	$('#createExcel').click(function () {
		excelImport();
	});
	//-----------------
	// 양식
	//-----------------
	$('#exeExcel').click(function() {
        exeExcelFile();
    });
	
	//-----------------
	// 업로드된 상품코드 그리드
	//-----------------
	$('#excelGrid').css('display', 'none');

	//달력셋팅
	$("#startDate, #endDate").attr("readonly");
	$('#btnStartDate, #startDate').click(function() {
		openCal('dataForm.startDate');
	});

	// startDate에 값이 변경이 된다면//event 적용안됨
	$("#startDate").change(function(){
		setApplyStartDy();
    });
	
	$('#btnEndDate, #endDate').click(function() {
		openCal('dataForm.endDate');
	});
	
	// endDate에 값이 변경이 된다면//event 적용안됨
	$("#endDate").change(function(){
		setApplyEndDy();
     });

	//-----------------
	// 대표상품코드
	//-----------------
	$('#input_repProdCd').change(function() {
		setRepProdCds(document.dataForm.input_repProdCd);
	});
	//-----------------
	// 변경요청사유
	//-----------------
	$('#input_reqReasonContent').focus(function() {
		//상품코드를 업로드 하지 않은경우
		if(document.dataForm.excelOp.value == ''){
			alert("상품코드를 먼저 입력해주세요.");
			$('#input_reqReasonContent').blur();
		}
		document.dataForm.input_reqReasonContent.value='';
	});
	//[변경요청사유] 포커스아웃 할 때 입력구분자를 보고 그리드에 값을 넣어줌
	$('#input_reqReasonContent').focusout(function() {
		if(document.dataForm.excelOp.value != '')
			setReqReasonContent(document.dataForm.input_reqReasonContent);
	});
	
	//[적용시작일자] 포커스아웃 할 때 입력구분자를 보고 그리드에 값을 넣어줌
	$('#startDate').focusout(function() {
		if(document.dataForm.startDate.value != '')
			setApplyStartDy();
	});
	
	//[적용끝일자] 포커스아웃 할 때 입력구분자를 보고 그리드에 값을 넣어줌
	$('#endDate').focusout(function() { 	
		if(document.dataForm.endDate.value != '')
			setApplyEndDy();
	});
	
	//-----------------
	// 판매가
	//-----------------
	$('#input_currSellPrc').focus(function() {
		//상품코드를 업로드 하지 않은경우
		if(document.dataForm.excelOp.value == ''){
			alert("상품코드를 먼저 입력해주세요.");
			$('#input_currSellPrc').blur();
		}
		document.dataForm.input_currSellPrc.value='';
	});
	//포커스아웃 할 때 입력구분자를 보고 그리드에 값을 넣어줌
	$('#input_currSellPrc').focusout(function() {
		if(document.dataForm.excelOp.value != ''){
			setCurrSellPrcs(document.dataForm.input_currSellPrc);
		}
	});
	
	//-----------------
	// 저장
	//-----------------
     $('#save').click(function() {
     	doInsert();
     });

     // 삭제 클릭
     $('#delete').click(function() {
     	doDelete();
     });

     //----------------testcase
 	//엑셀업로드 후 콜백함수    
 	$('#filefrm').load( function(){
 		doPreSearch();
 	});
 	//test_init();
 });

 	/** *****************************************************
 	 * 그리드 통신에러 발생 시
 	 ****************************************************** */
 	function WG1ErrorQuery(strSource, nCode, strMessage, strDescription) {
 		alert('ERROR MSG:'+strMessage );
 	};

 	function WG2ErrorQuery(strSource, nCode, strMessage, strDescription) {
 		alert('ERROR MSG:'+strMessage );
 	};

 	/** ********************************************************
 	 * 1. 협력사팝업
 	 ******************************************************** */
 	function popupVendor(){
 		var targetUrl = '<c:url value="/vendorPopUp/vendorPopUpView.do"/>';
 	
 	 	Common.centerPopupWindow(targetUrl, 'vendorPopUp', {width : 592, height : 500});
 	};

 	/** ********************************************************
 	 * 3. 협력사 정보를 가지고 대표상품코드 콩보박스 세팅
 	 ******************************************************** */
 	function setRepProdCdComboList(){
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
 					//var gridObj = document.WG1;
 					var obj	= document.dataForm.input_repProdCd;
 					obj.options[0] = new Option("선택","");
 					var repProdCd = data.repProdCdComboList;
 					comboReset(obj);
 					//gridObj.AddComboListValue("REP_PROD_CD", "", "");
 					//최소할부개월수
 					for(var i=0;i<repProdCd.length; i++){
 				 		//gridObj.AddComboListValue("REP_PROD_CD", repProdCd[i].name, repProdCd[i].code);
 				 		obj.options[i+1] = new Option(repProdCd[i].name, repProdCd[i].code);
 					}
 					
 					//그리드 콤보박스 셋팅처리 시작
 					var codeArr = new Array();
 					var nameArr = new Array();
 					codeArr.push("");
 					nameArr.push("");
 					for(var i=0;i<repProdCd.length; i++){
 				 		//gridObj.AddComboListValue("REP_PROD_CD", repProdCd[i].name, repProdCd[i].code);
 				 		//obj.options[i+1] = new Option(repProdCd[i].name, repProdCd[i].code);
 				 		codeArr.push(repProdCd[i].code);
 						nameArr.push(repProdCd[i].name);
 					}
 					var cInfo = {ComboCode:codeArr.join("|"), ComboText:nameArr.join("|")};
 					mySheet.SetColProperty(0, 'REP_PROD_CD', cInfo);
 					//그리드 콤보박스 셋팅처리 끝
 				} catch (e) {}
 			},
 			error: function(e) {
 				alert(e);
 			}
 		});
 	};

 	/** ********************************************************
 	 * 4. 상품코드 양식 다운로드
 	 ******************************************************** */
 	function exeExcelFile(){
 		
 		if (!confirm('엑셀 양식을 다운로드하시겠습니까?')) {
 			return;
 		}
 		excelFormDown(mySheet1, '대표상품코드일괄등록_양식', 2, 'Status');
 	};
 	
 	/** ********************************************************
 	 * 5. 상품코드 업로드
 	 ******************************************************** */
 	function excelImport() {
 	
 		if(dataForm.vendorId.value == "" || dataForm.vendorId.value == "전체"){
 			alert("협력사를 먼저 선택해주세요.");
 			return;
 		}
 	
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
 	};

 	//엑셀업로드 후 호출되는 함수
 	function doPreSearch(){
 		var dataForm = document.dataForm;
 		var rowCount = mySheet.RowCount();
 		
 		if( rowCount > 0){
 			if(rowCount>500){
 				alert("상품을 500개 이상 업로드 할 수 없습니다.");
 				mySheet.RemoveAll();
 				return;
 			}
 	
 			var prodCd = $('#mode').val();
 			var vendorId = $('#vendorId').val();
 			var url = '<c:url value="/product/repProdCd/selectProdInfo.do"/>';

 			//그리드의 인터넷상품코드 배열에 담기
 			prodCdArr = new  Array();
 			itemCdArr = new Array();
 			reqReasonContentArr = new Array();
 			currSellPrcArr = new Array();

 			var cnt = mySheet.RowCount()+2;

 			for(var i = 2 ; i < cnt; i++){
 				prodCdArr.push( mySheet.GetCellValue(i, "PROD_CD") ); // (인터넷상품코드)
 				itemCdArr.push( mySheet.GetCellValue(i, "ITEM_CD") ); // (단품코드)
 				reqReasonContentArr.push( mySheet.GetCellValue(i, "REQ_REASON_CONTENT") ); // (변경요청사유)
 				currSellPrcArr.push( mySheet.GetCellValue(i, "CURR_SELL_PRC") ); // (판매가)
 			}

 			var param = new Object();
 			param.rowsPerPage 	= $("#rowsPerPage").val();
 			param.prodCdArr = prodCdArr;
 			param.itemCdArr = itemCdArr;
 			param.reqReasonContentArr = reqReasonContentArr;
 			param.currSellPrcArr = currSellPrcArr;
 			param.vendorId = vendorId;
 			$.ajaxSettings.traditional = true;

 			loadIBSheetData(mySheet, url, "1", null, param);
 		}
 	};

 	/** ********************************************************
 	 * 상품 상세정보 팝업
 	 ******************************************************** */
 	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
 		mySheet.ShowCalendar(); //그리드 달력
 	};

 	//데이터 읽은 직후 이벤트
 	function mySheet_OnSearchEnd() {
 		
 		// 조회된 데이터가 승인완료인 경우는 체크 금지
 		var rowCount = mySheet.RowCount() + 2;
 	
 		for (var i=2; i<rowCount; i++) {
 			mySheet.SetCellValue( i, "APPLY_START_DY", "");
 			//mySheet.SetCellValue( i, "SELL_PRC", "");
 			mySheet.SetCellValue( i, "REQ_REASON_CONTENT", "");
 			mySheet.SetCellValue( i, "CURR_SELL_PRC", "");
 		}
 	
 		var rowCountTemp = mySheet1.RowCount()+1;
 		var rowCount	 = mySheet.RowCount()+2;
 	
 		if(rowCountTemp > 0){
 			for(var i=1;i<rowCountTemp;i++){
 				for(var j=2;j<rowCount;j++){
 					if(mySheet1.GetCellValue(i,"PROD_CD")== mySheet.GetCellValue(j,"PROD_CD") && mySheet1.GetCellValue(i,"ITEM_CD")== mySheet.GetCellValue(j,"ITEM_CD")){
 						mySheet.SetCellValue( j, "CURR_SELL_PRC",mySheet1.GetCellValue(i,"CURR_SELL_PRC"));
 						//mySheet.SetCellValue( j, "SELL_PRC",mySheet1.GetCellValue(i,"SELL_PRC"));
 						mySheet.SetCellValue( j, "REQ_REASON_CONTENT",mySheet1.GetCellValue(i,"REQ_REASON_CONTENT"));
 						break;
 					}	
 				}
 			}
 		}
 	
 		//프로모션 건수 있는것 색상변경처리
 		var rowCount3 = mySheet.RowCount() + 2;
 		for(var i=2;i<=rowCount3;i++){
 			if(mySheet.GetCellValue(i,'PROMO_CNT') > 0 ){
 				mySheet.SetCellBackColor(i, "PROMO_CNT", '#FE0202'); 	//빨간색
 				mySheet.SetCellFontColor(i, "PROMO_CNT", 'FEFDFD'); 	//흰색
 			}
 		}
 	
 	};

 	/** ********************************************************
 	 * 6. 업로드된 상품코드로 상품정보 조회
 	 ******************************************************** */
 	function doSearch() {
 		goPage('1');
 	};
 	
 	function goPage(currentPage){
 		var gridObj = document.WG1;
 	
 		//--------------------------------------
 		// 업로드한 상품코드에 대한 정보(판매가,이익률 등을 가져옴)
 		//--------------------------------------
 		var url = '<c:url value="/product/repProdCd/selectProdInfo.do"/>';
 		gridObj.SetParam('mode', 'search');
 		
 		gridObj.SetParam("vendorId",$('#vendorId').val());
 		gridObj.DoQuery(url, "WISEGRIDDATA_ALL");
 	};


 	/** ********************************************************
 	 * 7-1. 적용시작일자 일괄입력
 	 ******************************************************** */
 	function setApplyStartDy(){
		var date = $('#startDate').val();
 		var cnt = mySheet.RowCount()+2;
 		if(cnt > 0){
 			for (var i = 2; i < cnt; i++) {
 				mySheet.SetCellValue(i, "APPLY_START_DY",date);
 			}
 		}else{
 			alert("상품코드를 먼저 넣어주세요.");
 			$('#startDate').val('');
 		}
 	};
 	
 	function setApplyEndDy(){
		var date = $('#endDate').val();
 		var cnt = mySheet.RowCount()+2;
 		if(cnt > 0){
 			for (var i = 2; i < cnt; i++) {
 				mySheet.SetCellValue(i, "APPLY_END_DY",date);
 			}
 		}else{
 			alert("상품코드를 먼저 넣어주세요.");
 			$('#endDate').val('');
 		}
 	};

 	/** ********************************************************
 	 * 7-2. 대표상품코드 일괄입력
 	 ******************************************************** */
 	function setRepProdCds(input){
 		var data = $('#input_repProdCd').val();
 		var text = $("#input_repProdCd > option:selected").text();
 		var cnt = mySheet.RowCount()+2;
 		
 		var tempStr = text.split("/");
 		var profitRt =  jQuery.trim(tempStr[tempStr.length-1]);

 		for (var i = 2; i < cnt; i++) {
 			mySheet.SetCellValue(i, "REP_PROD_CD",data);
 			mySheet.SetCellValue(i, "PROFIT_RATE",profitRt);
 		}
 	};
 	 
 	/** ********************************************************
 	 * 7-3. 변경요청사유 입력
 	 ******************************************************** */
 	function setReqReasonContent(input){
 		var date = $('#input_reqReasonContent').val();
 		var cnt = mySheet.RowCount()+2;
 		for (var i = 2; i < cnt; i++) {
 			mySheet.SetCellValue(i, "REQ_REASON_CONTENT",date);
 		}
 	};

 	/** ********************************************************
 	 * 7-4. 판매가 입력
 	 ******************************************************** */
 	function setCurrSellPrcs(input){
 		var date = $('#input_currSellPrc').val();
 		var cnt = mySheet.RowCount()+2;
 		for (var i = 2; i < cnt; i++) {
 			mySheet.SetCellValue(i, "CURR_SELL_PRC",date);
 		}
 	};
 	
 	function onlyNumber(){
 		if((event.keyCode<48)||(event.keyCode>57))
 		event.returnValue=false;
 	};


 	/** ********************************************************
 	 * 8. 대표상품코드 일괄 등록 (저장)
 	 ******************************************************** */
 	function doInsert(){
 		
 		if(!checkRows())
 			return;
 		if(!checkInput())
 			return;
 	
 		if( dataForm.excelOp.value ='' ) {
 		    alert( "업로드 파일이 없습니다" );
 		    return;
 		}
 		
 		//nodata return
 		if(mySheet.RowCount() == 0){
 			alert('엑셀 파일을 업로드 자료가 잘못되었습니다');
 			$('#excelOp').val('');
 			return;
 		}
 	
 		if (!confirm('저장하시겠습니까?')) {
 			return;
 		}
 		
 		var vendorId = $("#vendorId").val();
 		mySheet.DoSave('<c:url value="/product/repProdCd/insertRepProdCdList.do"/>', { Quest:0 ,Param : "vendorId="+vendorId });	//Quest : IBSheet에서 확인메세지 출력여부
 	
 		dataForm.excelOp.value ='';
 	
 	};

 	//저장후 호출되는 이벤트
 	function mySheet_OnSaveEnd(code, msg) {
 		
 		if(code >= 0) {
 		    alert(msg);  // 저장 성공 메시지
 			opener.doSearch();
 			top.close();
 	
 		} else {
 		    alert(msg); // 저장 실패 메시지
 		}
 	}; 
 	
 	/**********************************************************
 	 * 로우 체크
 	 ******************************************************** */
 	function checkRows(){
 		if(  mySheet.CheckedRows("SELECTED") == 0){
 			alert("선택된 로우가 없습니다.");
 			return false;
 		}
 		return true;
 	};
 
 	/**********************************************************
 	 * 필수 값 체크
 	 ******************************************************** */
 	 function checkInput() {
 		var rowCount = mySheet.RowCount()+2;
 		
 		for(var i=2;i<rowCount;i++){
 			if(mySheet.GetCellValue(i,"SELECTED")==1){
 				
 				var seq = mySheet.GetCellValue(i,"SEQ");
 				//-----------------------------------------
 				// 상품코드
 				//-----------------------------------------
 				if(Common.isEmpty($.trim(mySheet.GetCellValue(i,"PROD_CD")))){
 					alert('<spring:message code="msg.common.error.required" arguments="상품코드"/>'
 							+ "\n[순번 "+seq+"] 을(를) 확인해주세요.");
 					return false;
 				}
 				//-----------------------------------------
 				// 적용시작일자
 				//-----------------------------------------
 				var today = getToday();
 				var before_applyStartDy = formatDate(mySheet.GetCellValue(i,"BEFORE_APPLY_START_DY"),"-");
 				if(before_applyStartDy==null)
 					before_applyStartDy = today;
 				
 				var before_applyStartDy_plus_one = Common.addDate(before_applyStartDy,"+1");
 				var applyStartDy = $.trim(mySheet.GetCellValue(i,"APPLY_START_DY"));
 				
 				// 1.필수값 
 				if(Common.isEmpty(applyStartDy)){
 					alert('적용시작일자' + "\n[순번 "+seq+"] 을(를) 확인해주세요.");
 					mySheet.SelectCell(i, 'APPLY_START_DY', {Focus:true});
 					//gridObj.SetCellFocus('APPLY_START_DY', i, false);
 					return false;
 				}
 				
 				// 2.적용시작일자는 금일자부터 등록 가능합니다.
 				before_applyStartDy = before_applyStartDy.replaceAll("-", "");
 				applyStartDy = applyStartDy.replaceAll("-", "");
 				today = today.replaceAll("-", "");
 				 
 				if(today  > applyStartDy ){
 					alert("적용시작일자가 현재일자 이전일 경우 등록할 수 없습니다."
 							+ "\n[순번 "+seq+"] 을(를) 확인해주세요.");
 					return false;
 				}
 				// 3.적용예정일 이 후부터 등록 가능 
 				if(before_applyStartDy > applyStartDy ){
 					alert("[순번 "+seq+"]은 "+before_applyStartDy_plus_one+" 부터 등록 가능합니다.");
 					return false;
 				}
 	
 				//-----------------------------------------
 				// 대표상품코드
 				//-----------------------------------------
 				if(Common.isEmpty($.trim(mySheet.GetCellValue(i,"REP_PROD_CD")))){
 					alert('대표상품코드' + "\n[순번 "+seq+"] 을(를) 확인해주세요.");
 					mySheet.SelectCell(i, 'REP_PROD_CD', {Focus:true});
 					//gridObj.SetCellFocus('REP_PROD_CD', i, true);
 					return false;
 				}
 				//-----------------------------------------
 				// 매가
 				//-----------------------------------------
 				//if(Common.isEmpty($.trim(gridObj.GetCellValue('SELL_PRC',i)))){
 				//	alert('<spring:message code="msg.common.error.required" arguments="정상매가"/>');
 				//	gridObj.SetCellFocus('SELL_PRC', i, true);
 				//	return false;
 				//}
 				//-----------------------------------------
 				// 변경요청사유
 				//-----------------------------------------
 				if(Common.isEmpty($.trim(mySheet.GetCellValue(i,"REQ_REASON_CONTENT")))){
 					alert('변경요청 사유는 필수 항목입니다.');
 					mySheet.SelectCell(i, 'REQ_REASON_CONTENT', {Focus:true});
 					//gridObj.SetCellFocus('CURR_SELL_PRC', i, true);
 					return false;
 				}
 				//-----------------------------------------
 				// 판매가
 				//-----------------------------------------
 				if(Common.isEmpty($.trim(mySheet.GetCellValue(i,"CURR_SELL_PRC")))){
 					alert('<spring:message code="msg.common.error.required" arguments="판매가"/>');
 					mySheet.SelectCell(i, 'CURR_SELL_PRC', {Focus:true});
 					//gridObj.SetCellFocus('CURR_SELL_PRC', i, true);
 					return false;
 				}
 				//-----------------------------------------
 				// 이익률
 				//-----------------------------------------
 				if(Common.isEmpty($.trim(mySheet.GetCellValue(i,"PROFIT_RATE")))){
 					alert('이익률 정보가 없습니다.'
 							+ "\n[순번 "+seq +"] 대표상품코드를 확인해주세요" );
 					mySheet.SelectCell(i, 'REP_PROD_CD', {Focus:true});
 					//gridObj.SetCellFocus('REP_PROD_CD', i, true);
 					return false;
 				}
 			}
 		}
 			
 		return true;
 	};

 	//그리드 값변경시 호출 이벤트
 	function mySheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {
 		if(Col == 10) {//대표상품코드 			
 			var data = getComboText(Row);
 			var tempStr = data.split("/");
 			var profitRt =  jQuery.trim(tempStr[tempStr.length-1]);
 			var before_profitRt = mySheet.GetCellValue(Row,"BEFORE_PROFIT_RATE");
 			
 			mySheet.SetCellValue(Row,"PROFIT_RATE", profitRt);
 			//if(mySheet.GetCellValue(Row,"SELECTED")  != "1") {
 			//선택박스 선택 및 빨간색 처리
 			mySheet.SetCellValue(Row,"SELECTED", "1");
 			mySheet.SetCellBackColor(Row, "REP_PROD_CD", '#FE0202'); 	//빨간색 	
 		}
 	};

 	function getComboText(Row){
 		var retDate;
 		//콤보코드와 텍스트를 가져온다.
 		var sText 	= mySheet.GetComboInfo(Row,10, 'Text');
 		var sCode 	= mySheet.GetComboInfo(Row,10,'Code') ;
 		
 		//각각 배열로 구성한다.
 		var arrText = sText.split('|');
 		var arrCode = sCode.split('|');

 		//2행의 2컬럼의 콤보 코드를 이용하여 콤보텍스트를 가져온다.
 		for(i=0; i<arrCode.length; i++) {
 		    	if(mySheet.GetCellValue(Row,10) == arrCode[i]) {
 		    		retDate = arrText[i];
 		        	break;
 		    	}
 		}
 		
 		return retDate;
 	};

 	/**********************************************************
 	 * 적용시작일자 클릭 이벤트
 	 ******************************************************** */
 	var nRowForGridOpenCal;
 	function GridOpenCalendar(strColumnKey, nRow){
 		nRowForGridOpenCal = nRow;
 		openCalWithCallback('dataForm.applyStartDy_tmp','callBackByOpenCal');
 	};
  
 	function callBackByOpenCal(){
 		var gridObj = document.WG1;
 		var before = gridObj.GetCellValue('APPLY_START_DY', nRowForGridOpenCal);
 		
 		gridObj.SetCellValue('APPLY_START_DY',nRowForGridOpenCal,dataForm.applyStartDy_tmp.value);
 		
 		var after = gridObj.GetCellValue('APPLY_START_DY', nRowForGridOpenCal);
 		
 		if(before != after)
 			GridChangeCell('APPLY_START_DY', nRowForGridOpenCal);
 	};
 	
 	/**********************************************************
 	 * 적용시작일자 클릭 이벤트
 	 ******************************************************** */
 	function GridCopyData(source, target, outOfList){
 		if(source ==null || target == null)
 			return;
 		
 	// 	var tIdx = 0; // target의 인덱스
 		var rowCount;
 	
 		target.RemoveAllData();
 		
 		//------------------
 		// 예외 리스트가 있는 경우 삭제 처리
 		//------------------
 		if(outOfList != null && outOfList.length !=0 ){
 			var length = outOfList.length;
 			var idx;
 			
 			for(var i=0;i<length;i++){
 				idx = GridGetIndexOfValue(source, outOfList[i]);
 				if(idx==-1)
 					alert("프로그램 동작 중 오류가 발생했습니다.");
 				source.deleteRow(idx);
 			}
 		}
 		//------------------
 		// source -> target 복사
 		//------------------
 		rowCount = source.GetRowCount();
 		for(var i=0;i<rowCount;i++){
 			target.AddRow();
 			target.SetCellValue('PROD_CD', i, source.GetCellValue('PROD_CD',i));
 		}
 	};
 	
 	function GridGetIndexOfValue(gridObj, value){
 		var rowCount = gridObj.getRowCount();
 		
 		for(var i=0; i<rowCount; i++){
 			if(gridObj.GetCellValue("PROD_CD",i) == value)
 				return i;
 		}
 		
 		return -1;
 	}
  
 	function setVendorInto(vendorId, vendorNm){
 		mySheet.RemoveAll();
 			
 	 	$("#vendorId").val(vendorId);
 	 	$("#vendorNm").val(vendorNm);
 	 	
 		setRepProdCdComboList();
 	};
 	
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
 	};

 	//엑셀업로드 콜백 함수
 	function setData( PROD_CD, ITEM_CD, REQ_REASON_CONTENT, CURR_SELL_PRC ){
 		var rowIdx = mySheet.DataInsert( -1);
 		mySheet.SetCellValue( rowIdx, "PROD_CD", PROD_CD);
 		mySheet.SetCellValue( rowIdx, "ITEM_CD", ITEM_CD);
 		mySheet.SetCellValue( rowIdx, "REQ_REASON_CONTENT", REQ_REASON_CONTENT);
 		mySheet.SetCellValue( rowIdx, "CURR_SELL_PRC",CURR_SELL_PRC);
 		//엑셀업로드 용
 		var rowIdx1 = mySheet1.DataInsert( -1);
 		mySheet1.SetCellValue( rowIdx1, "PROD_CD", PROD_CD);
 		mySheet1.SetCellValue( rowIdx1, "ITEM_CD", ITEM_CD);
 		mySheet1.SetCellValue( rowIdx1, "REQ_REASON_CONTENT", REQ_REASON_CONTENT);
 		mySheet1.SetCellValue( rowIdx1, "CURR_SELL_PRC",CURR_SELL_PRC);
 	};
 	
 	function getToday() {
		var FORMAT = "-";
		var date = new Date();
		var yyyy = date.getFullYear().toString();
		var mm = (date.getMonth() + 1).toString().length < 2 ? "0"
				+ (date.getMonth() + 1) : (date.getMonth() + 1).toString();
		var dd = date.getDate().toString().length < 2 ? "0" + date.getDate()
				: date.getDate().toString();

		return yyyy + FORMAT + mm + FORMAT + dd;
	};
 	
 	function formatDate(thisDate, format) {
		// 날짜 길이와 FORMAT이 있는지 체크
		if (thisDate.length != 8 || !isNumber(thisDate))
			return null;

		var yyyy = thisDate.substr(0, 4);
		var mm = thisDate.substr(4, 2);
		var dd = thisDate.substr(6, 2);

		return yyyy + format + mm + format + dd;
	};

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<div id="content_wrap">

<div class="content_scroll">

 <form name="dataForm" id="dataForm" method="post">
<!-- hidden value -->
<c:set var="optnProdPrcMgrYn" value="${ IUFlag eq 'update' ? repProdCdList[0].OPTN_PROD_PRC_MGR_YN : '' }" />
<input type="hidden" id="optnProdPrcMgrYn" name="optnProdPrcMgrYn" value="${ optnProdPrcMgrYn }"/>
<%-- <input type="hidden" id="repDropDnList" name="repDropDnList" value="${epcLoginVO.vendorId}"/> --%>
<input type="hidden" id="taxatDivnCd" name="taxatDivnCd"/>
<input type="hidden" id="rowsPerPage" name="rowsPerPage" value="50"/>
<input type="hidden" id="pagingDiv" name="pagingDiv" />
	
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
				<li class="tit">대표상품코드관리 엑셀업로드 파일</li>				
				
				<li class="btn">
					<span><font color="red" id="msgs">※ 옵션별 가격관리를 하지 않는 상품만 등록하셔야 합니다.</font></span>
					<a href="#" class="btn" id="save"><span><spring:message code="button.common.save"/></span></a>
					<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="15%" />
		        <col width="35%" />
		        <col width="10%" />
		        <col width=" 40%" />
			</colgroup>
			<tr>
				<th>협력업체코드</th>
                <td colspan="3">
					<c:choose>
						<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
							<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
							<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
						</c:when>
						<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
							<select name="vendorId" id="vendorId" class="select" style="width:20%">
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
				<th>상품코드</th>   
				<td  colspan="3">
						<input id="excelOp" name="excelOp" type="text" class="text" style="width:50%;" readonly="readonly" />
						<input type="file" name="file" id="file"  class="text" style="display:none;"  value=""  />
						<input type="hidden" name="colNms" size="35" value="PROD_CD^ITEM_CD^REQ_REASON_CONTENT^CURR_SELL_PRC" /><!-- IBSheet 칼럼명 (구분자 ^, 필수 파라미터) -->
						<input type="hidden" name="func" value="setData" /><!-- 실행 자바스크립트 명 ( 필수 파리미터) -->
						<input type="hidden" name="sheetNm" value="mySheet" /><!-- IBSheet 명 (필수 파라미터)-->
						<input type="hidden" name="sheetRemoveAll" value="Y" />
						<input type="hidden" name="hdRow" value="1" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="btn" id="uploadExcel"><span><spring:message code="button.rep.product.excelUpload"/></span></a>
						<a href="#" class="btn" id="findFile"><span><spring:message code="button.common.find"/></span></a>
						<a href="#" class="btn" id="exeExcel"><span><spring:message code="button.rep.product.exeExcel"/></span></a>							    		
	          		</td>
				</tr>
			</table>
		</div>	
		
		<div class="bbs_search">
    		<ul class="tit"><li class="tit">엑셀파일 정보</li></ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="15%" />
		        <col width="35%" />
		        <col width="15%" />
		        <col width=" 35%" />
			</colgroup>
			<tr>
				<th>적용일자</th>
                <td>
					<input type="text" id="startDate" name="startDate" style="width:35%;" class="day" />
					<a href="#" id="btnStartDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
					~
					<input type="text" id="endDate" name="endDate" style="width:35%;" class="day" />
					<a href="#" id="btnEndDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th>대표상품코드</th>
				<td>
					<select name="input_repProdCd" id="input_repProdCd">
							<option value="">선택</option>
						</select>		    		
	          	</td>
			</tr> 
			<tr> 
				<th>변경요청사유 및 비고</th>
					<td>
						<input type="text" id="input_reqReasonContent" class="input" value="변경요청사유" style="ime-mode:disabled" maxlength="30"/>
					</td>
					<th>판매가</th>
					<td>
						<input type="text" id="input_currSellPrc" class="input" value="판매가" style="ime-mode:disabled" onKeyPress="onlyNumber();" maxlength="10"/>
					</td>
			</tr>
		</table>
	</div>	
		
			<!--	2 검색내역 	-->
	<div class="wrap_con">
		<div class="bbs_list">
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"  id="sheetTbl">
				<tr>
 					<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
				</tr>
			</table>
		</div>
		
	    <!-- 엑셀업로드 용 그리드-->
	    <div class="bbs_list">
			<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_01" summary=" "  id="sheetTbl"  style="display:none;" >
				<col width="100%" />
				<tr>
					<td><div id="ibsheet2"></div></td><!-- IBSheet 위치 -->
				</tr>
			</table> 
	    </div>
	</div>
<iframe id="filefrm" name="filefrm" src="" marginwidth="0" marginheight="0" frameborder="3"  bordercolor="red" width="0" height="0" scrolling="yes"></iframe>	
		  
	  </div>
	</div>
</form>
</div>

</div>
</body>
</html>