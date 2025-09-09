<%--
- Author(s): lth
- Created Date: 2016. 05. 10
- Version : 1.0
- Description : 해피콜 관리
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%
DecimalFormat df = new DecimalFormat("00");
Calendar currentCalendar = Calendar.getInstance();

currentCalendar.add(currentCalendar.DATE, -30);
String strYear31   = Integer.toString(currentCalendar.get(Calendar.YEAR));
String strMonth31  = df.format(currentCalendar.get(Calendar.MONTH) + 1);
String strDay31   = df.format(currentCalendar.get(Calendar.DATE));
String strDate31 = strYear31 + strMonth31 + strDay31;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<head>
<style type="text/css">
.idtit{ float:right; padding:6px 0 0 13px; color:#F2F2F2; font-weight:bold;}
</style>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- delivery/PDCMHPC0001 -->
<script type="text/javascript">

$(document).ready(function(){
	
	if(!$("#loginSession").val()){
		alert($("#loginSession").val());
	}

	if($("#loginSession").val()=='false'){
		alert('<spring:message code="msg.login.necessary"/>');
		//메인 화면으로 이동
		var targetUrl = '<c:url value="/main/intro.do"/>';
		self.window.open('','_self'); 
		self.window.open(targetUrl,'_top');
		return;
	}
	
	$('#sale_str_cd').focus();
	
	//기본 조회 조건 setting
	makeStepStsList();
	$("#sc_deli_prgs_step_sts_cd option").each( function(){	
		if( $(this).val() == '13') {	//배송진행단계상태 : 접수등록완료
			$(this).attr('selected', true);
		}
	});
	
	//입력폼에서 엔터 시 조회처리
	$("input").keypress( function( e){
		if (e.which == 13) {
			doSearch();
		}
	});
	
	//선택항목에서 엔터 시 조회처리
	$("select").keypress( function( e){
		if (e.which == 13) {
			doSearch();
		}
	});
	
	//검색
    $('#search').click(function() {
        doSearch();
    });
  //검색
    $('#excelDown').click(function() {
    	doExcelDown();
    });
    
	//배송진행단계 따른 상태 그루핑
	$('#sc_deli_prgs_step_cd').change(function(){
		makeStepStsList();
	});
	
	//해피콜 메시지 설정
	$(getHpcMessage()).appendTo('#hpclContent');
	
	//해피콜 아이디 할당
	$('#save').click(function(){
		
		if($("#saveHpcUtakmnId").val()==''){
			alert("할당할 해피콜 아이디를 선택해 주세요.");
			return;
		}
		
		doUpdate();	
	});
	
	var loginId = "${loginId}";

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "350px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번|순번",		        		Type:"Text" ,	SaveName:"NUM", 		        Align:"Center", MinWidth:40, Edit:0}
	  , {Header:"", 	        				Type:"CheckBox",SaveName:"chk",					Align:"Center", MinWidth:40, Sort:false, Hidden:false}
	  , {Header:"접수번호|접수번호", 					Type:"Text", 	SaveName:"ACCEPT_NO",        	Align:"Center", MinWidth:130, Edit:0, Hidden:false}
	  , {Header:"운송장번호|운송장번호", 				Type:"Text", 	SaveName:"HODECO_INVOICE_NO",  	Align:"Center", MinWidth:100, Edit:0, Cursor:'pointer' }
	  , {Header:"출고일|출고일", 					Type:"Text", 	SaveName:"DELI_ORDER_DY",  		Align:"Center", MinWidth:100, Edit:0 }
	  , {Header:"해피콜메시지|해피콜메시지", 			Type:"Text", 	SaveName:"HPCL_CONTENT", 		Align:"Left", MinWidth:170, Edit:0}
	  , {Header:"접수일자|접수일자", 	        		Type:"Text", 	SaveName:"ACCEPT_DATE", 	    Align:"Center", MinWidth:100, Edit:0, Hidden:true}
	//  , {Header:"배송점포|배송점포", 				Type:"Text", 	SaveName:"DELI_STR_NM", 		Align:"Center", MinWidth:60, Edit:0, Hidden:true}
	  , {Header:"접수구분|접수구분", 					Type:"Text", 	SaveName:"ACCEPT_DIVN_NM",      Align:"Center", MinWidth:100, Edit:0, Hidden:true}
	  , {Header:"운송장번호|운송장번호", 				Type:"Text", 	SaveName:"INVOICE_NO",  		Align:"Center", MinWidth:120, Edit:0, Hidden:true}
	
	  , {Header:"배송메세지|배송메세지", 				Type:"Text", 	SaveName:"DELI_MSG", 			Align:"Left", MinWidth:300, Edit:0}
	  , {Header:"배송진행\n단계|배송진행\n단계",      	Type:"Text", 	SaveName:"DELI_PRGS_STEP_NM", 	Align:"Right",   MinWidth:70, Edit:0, Hidden:true}
	  , {Header:"상품명|상품명",     				Type:"Text", 	SaveName:"PROD_NM", 	        Align:"Left",   MinWidth:200, Edit:0, Hidden:false}
	  , {Header:"유형|유형",     					Type:"Text", 	SaveName:"FEDAY_MALL_PROD_DIVN_NM",	Align:"Center",   MinWidth:60, Edit:0, Hidden:false}
	  , {Header:"배송진행\n단계상태|배송진행\n단계상태", 	Type:"Text", 	SaveName:"DELI_PRGS_STEP_STS_NM", Align:"Right",   MinWidth:90, Edit:0, Hidden:true}
	  , {Header:"최종해피콜\n일시|최종해피콜\n일시", 		Type:"Text", 	SaveName:"HAPPY_MOD_DATE",      Align:"Center", MinWidth:105, Edit:0, Hidden:true}
	  , {Header:"해피콜상태|해피콜상태", 	    		Type:"Text", 	SaveName:"HPCL_RSLT_NM", 	    Align:"Center", MinWidth:105, Edit:0, Hidden:true}
	  , {Header:"수정자|수정자", 					Type:"Text", 	SaveName:"MOD_ID", 				Align:"Center", MinWidth:105, Edit:0, Hidden:true}
	  
	  , {Header:"점포명|점포명", 	        		Type:"Text", 	SaveName:"SALE_STR_NM", 	    Align:"Center", MinWidth:60, Edit:0, Hidden:false}
	  , {Header:"담당자이름|담당자이름", 				Type:"Text", 	SaveName:"STR_UTAKMN_NM_1"	,   Align:"Center"			, MinWidth:80, Edit:0}
	  , {Header:"연락처|연락처"	, 					Type:"Text",	SaveName:"STR_UTAKMN_CELL_NO_1",Align:"Center"			, MinWidth:80, Edit:0}
	//  , {Header:"받으실분|이름", 		Type:"Text", 		SaveName:"RECV_PSN_NM", 				Align:"Left", MinWidth:70, Edit:0}
	//  , {Header:"받으실분|휴대폰", 	Type:"Text", 		SaveName:"RECV_PSN_CELL_NO", 			Align:"Center", MinWidth:100, Edit:0}
	//  , {Header:"받으실분|전화", 		Type:"Text", 		SaveName:"RECV_PSN_TEL_NO", 			Align:"Center", MinWidth:100, Edit:0}
	//  , {Header:"받으실분|주소", 		Type:"Text", 		SaveName:"RECV_PSN_ADDR", 				Align:"Left", MinWidth:400, Edit:0}
	//  , {Header:"보내는분|이름", 		Type:"Text", 		SaveName:"SEND_PSN_NM", 				Align:"Left", MinWidth:70, Edit:0}
	//  , {Header:"보내는분|휴대폰", 	Type:"Text", 		SaveName:"SEND_PSN_CELL_NO", 			Align:"Center", MinWidth:100, Edit:0}
	//  , {Header:"보내는분|전화번호", 	Type:"Text", 		SaveName:"SEND_PSN_TEL_NO", 			Align:"Center", MinWidth:100, Edit:0}
	  
	  , {Header:"해피콜아이디|해피콜아이디", 					Type:"Text", 		SaveName:"HPCL_UTAKMN_ID", 			Align:"Center", MinWidth:100, Edit:0}
	  , {Header:"발주반영|발주반영", 							Type:"Text", 		SaveName:"ORDER_APPLY_YN", 			Align:"Center", MinWidth:60, Edit:0}
	];
	

	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.SetFrozenCol(4);
	//mySheet.FitColWidth();
	
	if (loginId != "hpc1" && loginId != "hpc2" && loginId != "hpc3" && loginId != "hpc4" && loginId != "hpc5") {
		mySheet.SetColHidden("chk", 1);
	}

}); // end of ready

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	/**********************************************************
	 * 페이지 이동 시
	 ******************************************************** */
	function goPage(currentPage) 
	{
		hpcUtakmnLoginId();
		var form = document.hpclForm;
		var url = '<c:url value="/delivery/selectHappyCallList.do"/>';
// 		var param = new Object();
		
		//폼변수 자동넘기기
// 		$.each($(form).serializeArray(), function() {
// 			gridObj.setParam(this.name, this.value);
// 		});
		
// 		param.rowsPerPage 	= $("#rowsPerPage").val();
// 		param.startDate = $('#startDate').val();
// 		param.endDate = $('#endDate').val();
// 		param.mode = 'search';
		
// 		param.prodCd =  $("#prodCd").val();
// 		param.prodNm = $("#prodNm").val();
		
// 		loadIBSheetData(mySheet, url, currentPage, null, param);

		if($('input[name=fedayMallProdDivnNm]:checked').length == 0){
	    	alert("상품유형을 선택해 주세요.");
	    	return;
	    }else{
			var tempProdDivnNm = "";
			var sc = $('#hpclForm').serializeArray();
	    	for(var i=0;i<sc.length;i++){
	    		if(sc[i].name == 'fedayMallProdDivnNm'){
	    			tempProdDivnNm = tempProdDivnNm + sc[i].value + "/";
	    		}
	    	}
	    	$("#tempProdDivnNmArr").val(tempProdDivnNm); 
	    }
		
		loadIBSheetData(mySheet, url, currentPage, '#hpclForm', null);
	}

	function doExcelDown(){
		if( mySheet.RowCount() == 0) {
 			alert("조회된 데이터가 없습니다. 조회 후 Excel 버튼을 눌러주세요.");
 			return;
		}else{
			excelDown();
		}
	}
	
	function excelDown(){
		var excelFileName = "해피콜관리";
	    var hideCols = "NUM|chk";
	    excelDataDown(mySheet, excelFileName, hideCols);
	}
	
	function makeStepStsList(){
		var groupValue = $('#sc_deli_prgs_step_cd').val().substring(0,1);
		var selObj = $('#sc_deli_prgs_step_sts_cd').html('<option value="">전체</option>');
		selObj.width("125");  

		$('#sc_deli_prgs_step_sts_cd_list option').each(function(){
			var grpCd = $(this).val().substring(0,1);
			if(grpCd == groupValue){
				$('<option></option>'
						).html(
							$(this).html()
						).val(
							$(this).val()
						).appendTo(
							selObj
						);
			}
		});
	}

	

	/** ********************************************************
	 * 운송장번호를 클릭한 경우 팝업 띄움
	 ******************************************************** */
	function popupInvoiceDetailForm(invoiceNo, nRow){
		var targetUrl = '<c:url value="/delivery/PDCPHPC0001.do"/>?invoiceNo='+invoiceNo;
    	Common.centerPopupWindow(targetUrl, 'win', {width : 800, height : 650, scrollBars:"YES"});
	}
	
	/*
	 * 로딩중 표시
	 */ 
	function loadingMask2(){
		var childTop    = (document.body.clientHeight) / 2.5;
		var childLeft   = (document.body.clientWidth)  / 3;

		var loadingDiv = $('<span id="loadingLayer" style="position:absolute; left:'+childLeft+'px; top:'+childTop+'px; width:100%; height:100%; z-index:10001"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></span>').appendTo($("body"));
		loadingDiv.show();
		var loadingDivBg = $('<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>').appendTo($("body"));
		loadingDivBg.show();
	}
	function hpcUtakmnLoginId(){
		 var hpcUtakmnIdTot='';  
		 var hpcUtakmnIdTem;
		for (var i = 0; i < document.hpclForm.hpcUtakmnId.options.length; i++) {
			hpcUtakmnIdTem = document.hpclForm.hpcUtakmnId.options[i].text;
			if(hpcUtakmnIdTem != '전체' && hpcUtakmnIdTem != '미할당'){
				hpcUtakmnIdTot += hpcUtakmnIdTem;
				if(i != document.hpclForm.hpcUtakmnId.options.length-1){
					hpcUtakmnIdTot += ',';
				}
			}
		}
		$('#hpcIdsLoginId').val(hpcUtakmnIdTot);
		//console.log(a);
	}

	/*******************************
	 * 해피콜 메시지 List
	 *******************************/
	function getHpcMessage(){
		var message = '';
		
		$('#hpclContentNm').val('해피콜완료'+
				                '/해피콜완료(주소수정)'+
				                '/부재'+
				                '/장기부재'+
				                '/회사주소(미확정)'+
				                '/결번'+
				                '/타인'+
				                '/로밍'+
				                '/전원꺼짐'+
				                '/착신정지'+
				                '/수취거절'+
				                '/수하인콜진행요청'+
				                '/송하인택배요청건'+
				                '/점포택배요청건');	
	
		message = message +
				'<option value="해피콜완료">해피콜완료</option>'+
				'<option value="해피콜완료(주소수정)">해피콜완료(주소수정)</option>'+
				'<option value="부재">부재</option>'+
				'<option value="장기부재">장기부재</option>'+
				'<option value="회사주소(미확정)">회사주소(미확정)</option>'+
				'<option value="결번">결번</option>'+
				'<option value="타인">타인</option>'+			
				'<option value="로밍">로밍</option>'+
				'<option value="전원꺼짐">전원꺼짐</option>'+
				'<option value="착신정지">착신정지</option>'+
				'<option value="수취거절">수취거절</option>'+
				'<option value="수하인콜진행요청">수하인콜진행요청</option>'+
				'<option value="송하인택배요청건">송하인택배요청건</option>'+
				'<option value="점포택배요청건">점포택배요청건</option>'+
				'<option value="0">기타(텍스트입력)</option>';
				
		return message;
	}

	/**
	 * 페이지 인덱스 렌더링
	 */
	function getRenderHtml(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName, rowsPerPage){
		var htmlString = "";

		/* 20180129 이재하 담당 요청으로 홍진옥 단위 수정 */
		var pageRow = '50';
		var nextRowTerm = '100';
		var nextRowTerm2 = '200';
		var nextRowTerm3 = '300';
		var nextRowTerm4 = '400';
		var nextRowTerm5 = '500';
		var nextRowTerm6 = '1000';

		htmlString += "<p class=\"total\">[총건수 <span>";
		if(totalCnt == 0){
			htmlString += "0 </span> ]</p>";
		}else{
			htmlString += totalCnt + "</span> ]</p>";
		}

		htmlString += "<div class=\"paging1\">";

	    // 첫 페이지
	    if (curPage == 1) {
	    	htmlString += "<span class=\"fst\" ></span>";
	    } else {
	    	htmlString += "<span class=\"fst\" onclick=\"javascript:" + funcName + "(1);\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_first.gif\" alt=\"첫페이지\" class=\"middle\" /></a></span>";
	    }

	    // 이전 페이지 출력
	    if(previousPage !='1'){
	    	htmlString += "<span class=\"pre\" onclick=\"javascript:" + funcName + "('" + previousPage + "');\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_prev.gif\" alt=\"이전페이지\" class=\"middle\" /></a></span>";
	    }else{
	    	htmlString += "<span class=\"pre\" ></span>";
	    }

	    // 페이지 인덱스
	    for (var i=fromPageNo; i<=toPageNo; i++) {
	    	var css = "";
	    	if(i > fromPageNo){
	    		css = "bar";
	    	}
	    	
	    	if (i == curPage){
	    		htmlString += "<span class=\""+css+" bar2\" ><a href=\"#\">" + i + "</a></span>";
	    	}else{
	    		htmlString += "<span class=\""+css+"\" onclick=\"javascript:" + funcName + "('" + i + "');\" ><a href=\"#\">" + i + "</a></span>";
	    	}
	    }

	    // 이후 페이지 출력
	    if (curPage == nextPage) {
	        htmlString += "<span class=\"nxt\" ></span>";
	    } else {
	    	htmlString += "<span class=\"nxt\" onclick=\"javascript:" + funcName + "('" + nextPage + "');\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_next.gif\" alt=\"다음페이지\" class=\"middle\" /></a></span>";
	    }

	    // 마지막 페이지 출력
	    if (curPage == lastPageNo) {
	        htmlString += "<span class=\"end\" ></span>";
	    } else {
	    	htmlString += "<span class=\"end\" onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_end.gif\" alt=\"끝페이지\" class=\"middle\" /></a></span>";
	    }
	    
	    htmlString += "</div>";
	    htmlString += "<p class=\"listCnt\"><span>";
	    htmlString += "<select name=\"rowsPerPage\" id=\"rowsPerPage\" onChange=\"javascript:doSearch();\" class=\"select\" align=\"right\">";
        htmlString += "	<option value=\""+pageRow+"\" "+(rowsPerPage == pageRow ? 'selected':'')+">"+pageRow+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm+"\" "+(rowsPerPage == nextRowTerm ? 'selected':'')+">"+nextRowTerm+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm2+"\" "+(rowsPerPage == nextRowTerm2 ? 'selected':'')+">"+nextRowTerm2+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm3+"\" "+(rowsPerPage == nextRowTerm3 ? 'selected':'')+">"+nextRowTerm3+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm4+"\" "+(rowsPerPage == nextRowTerm4 ? 'selected':'')+">"+nextRowTerm4+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm5+"\" "+(rowsPerPage == nextRowTerm5 ? 'selected':'')+">"+nextRowTerm5+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm6+"\" "+(rowsPerPage == nextRowTerm6 ? 'selected':'')+">"+nextRowTerm6+"건</option>";
	    htmlString += "</select>";
	    htmlString += "</span></p>";

	    return htmlString;
	}

	//셀 클릭 시...
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

		if(mySheet.ColSaveName(Col) == 'HODECO_INVOICE_NO'){
			if(Row > 1 && mySheet.GetCellValue(Row, "INVOICE_NO") != "") {
				popupInvoiceDetailForm( mySheet.GetCellValue(Row, "INVOICE_NO"));
			}
		}
	}

	function mySheet_OnResize(Width, Height) {
		//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
		//mySheet.FitColWidth();
	}

	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd(cd,msg) {

		if(RETURN_IBS_OBJ.mode == "NOSESSION"){	//세션이 종료되었으면
			alert("로그인이 필요합니다.");
			//메인 화면으로 이동
			var targetUrl = '<c:url value="/main/intro.do"/>';
			window.open(targetUrl,'_top');  
			return;
		}

		if( RETURN_IBS_OBJ.loginId == "hpc1" && RETURN_IBS_OBJ.loginId == "hpc2" && RETURN_IBS_OBJ.loginId == "hpc3" && RETURN_IBS_OBJ.loginId == "hpc4" && RETURN_IBS_OBJ.loginId == "hpc5" ){

			// col hidden  
			// 0 숨김
			// 1 
			mySheet.SetColHidden("DELI_STR_NM", 1);
			mySheet.SetColHidden("ACCEPT_DIVN_NM", 1);
			mySheet.SetColHidden("DELI_PRGS_STEP_NM", 1);
			mySheet.SetColHidden("DELI_PRGS_STEP_STS_NM", 1);
			mySheet.SetColHidden("ACCEPT_DATE", 1);
			mySheet.SetColHidden("SALE_STR_NM", 1);
			mySheet.SetColHidden("PROD_NM", 1);
			mySheet.SetColHidden("HPCL_RSLT_NM", 1);
			mySheet.SetColHidden("HAPPY_MOD_DATE", 1);
			mySheet.SetColHidden("MOD_ID", 1);
		}

		mySheet.FitColWidth();

		for(var i = 2 ; i<=mySheet.LastRow() ;i++){

			mySheet.SetCellFontColor(i, "HODECO_INVOICE_NO" , "#0000FF");
		}

	}

	//해피콜 아이디 할당
	function doUpdate(loginId){//HttpServletRequest request
		var rowCount = mySheet.RowCount()+2;
		var cnt = 0;
		var url = '<c:url value="/delivery/updateHappyCallUtakmnId.do"/>';
		var utakmnId = $("#saveHpcUtakmnId").val();
		
		// 그리드 로우만큼 반복
		for (var i=2; i<rowCount; i++) {
			var chkVal = mySheet.GetCellValue(i, "chk");
			
			if( chkVal > 0 ) {	//0:non checked, 1:checked
				cnt = cnt+1;
			}
		}
		
		if( cnt < 1 ){
			alert("선택된 로우가 없습니다");
			return;
		}
		
		if(!confirm('<spring:message code="msg.common.confirm.update" />')){
			return;
		}
		
		mySheet.DoSave(url, {Param:'utakmnId='+utakmnId, Col:'chk', Quest:false});
	
	}
	
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) doSearch();
	}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<c:set var="startDate" value='<%=strDate31%>' />
<form name="hpclForm" id="hpclForm" method="post">
<input type="hidden" name="tempProdDivnNmArr" id="tempProdDivnNmArr" value="">
<div id="content_wrap">
	<div class="content_scroll">
		<div id="wrap_menu">
		    <div class="wrap_search">
		        <div class="bbs_search">
		            <ul class="tit">
		                <li class="tit">해피콜관리</li>
		                <li class="btn">
		                	<a href="#" class="btn" id="excelDown"><span><spring:message code="button.common.excel" /></span></a>
		                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
		                </li>
		            </ul>
		            <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:9%" />
						<col style="width:15%" />
						<col style="width:9%" />
						<col style="width:18%" />
						<col style="width:9%" />
						<col style="width:12%" />
						<col style="width:9%" />
						<col style="width:19%" />
					</colgroup>
					<tr>
						<th>매출점포</th>
						<td class="text">
							<select id="sale_str_cd" name="sale_str_cd" class="select">
								<option value="">전체</option>
								<c:forEach items="${storeList}" var="store">
			 						<option value="${store.STR_CD}">${store.STR_NM }</option>
			 					</c:forEach>
							</select>
						</td>
						<th><span class="star">*</span> 접수기간</th>
						<td colspan="3">
							<input type="text" name="rtnAcceptFromDt" id="rtnAcceptFromDt" value="<c:out value="${fn:substring(startDate,0,4)}-${fn:substring(startDate,4,6)}-${fn:substring(startDate,6,8)}" />" style="width:30%;" class="day" <%-- onclick="javascript:openCal('hpclForm.rtnAcceptFromDt')"--%> readOnly />
							<a href="javascript:openCal('hpclForm.rtnAcceptFromDt')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>   
							~
							<input type="text" name="rtnAcceptToDt" id="rtnAcceptToDt" value="<c:out value="${confInfo.currentDate}" />" style="width:30%;" class="day" <%--onclick="javascript:openCal('hpclForm.rtnAcceptToDt')"--%> readOnly />
							<a href="javascript:openCal('hpclForm.rtnAcceptToDt')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>  
						</td>
						<th>접수번호</th>
						<td>
							<input type="text" name="acceptNo" id="acceptNo" value="" style="width:92%;" maxlength="20"/>
						</td>
					</tr>
					<tr>
						<th>운송장번호</th>
						<td>
							<input type="text" name="hodecoInvoiceNo" id="hodecoInvoiceNo" value="" style="width:92%;" maxlength="20"/>
						</td>
						<th>해피콜상태</th>
						<td>
							<select name="happyCallState" id="happyCallState" class="select" >
								<option value="">전체</option>
								<c:forEach items="${happyCallList}" var="happyCallList" begin="0">
									<!-- 20180129 이재하 담당 요청으로 홍진옥 사후해피콜 확정, 미확정, 최소요청 비활성화 -->
									<c:if test="${happyCallList.MINOR_CD ne '40' && happyCallList.MINOR_CD ne '50' && happyCallList.MINOR_CD ne '60'}">
		 							<option value="${happyCallList.MINOR_CD }" <c:if test="${happyCallList.MINOR_CD == '20'}">selected</c:if>>${happyCallList.CD_NM }</option>
		 							</c:if>
		 						</c:forEach>
							</select>
						</td>
						<th>해피콜 메시지 </th>
						<td colspan="3">
							<select id="hpclContent" name=hpclContent  style="width:100%" >
								<option value="">전체</option>
							</select>
							<input type="hidden" id="hpclContentNm" name="hpclContentNm"/>
						</td>
					</tr>
					<tr>
						<th>배송진행단계</th>
						<td class="text">
							<select id="sc_deli_prgs_step_cd" name="sc_deli_prgs_step_cd" class="select" >
								<option value="">전체</option>
								<c:forEach items="${cdHO04}" var="cd">
			 						<option value="${cd.MINOR_CD}" <c:if test="${cd.MINOR_CD == '10'}">selected</c:if>>${cd.CD_NM}</option>
			 					</c:forEach>
							</select>
						</td>
						<th>배송진행상태</th>
						<td class="text">
							<select id="sc_deli_prgs_step_sts_cd" name="sc_deli_prgs_step_sts_cd" class="select">
								<option value="">전체</option>
							</select>
							<select id="sc_deli_prgs_step_sts_cd_list" style="display:none;" >
								<c:forEach items="${cdHO05}" var="cd">
		 						<option value="${cd.MINOR_CD}" >${cd.CD_NM}</option>
		 						</c:forEach>
							</select>
						</td>
						<th>보내는 분</th>
						<td>
							<input type="text" name="sendPsnNm" id="sendPsnNm" value="" style="width:92%;" maxlength="15"/>
						</td>
						<th>받으실 분</th>
						<td>
							<input type="text" name="recvPsnNm" id="recvPsnNm" value="" style="width:92%;" maxlength="15"/>
						</td>
						<input type="hidden" name="loginSession" id="loginSession" value="${loginSession}"/>
					</tr>
					<tr>
						<th>해피콜 ID</th>
						<td >
							<select id ="hpcUtakmnId" name="hpcUtakmnId" class="select">
								<option value="">전체</option>
								<option value="0">미할당</option>
								<c:forEach items="${activeId}" var="hpcIds" >
									<option value="${hpcIds.LOGIN_ID }" >${hpcIds.LOGIN_ID }</option>
								</c:forEach>
							    <%-- <input type="hidden" id="hpcIdsLoginId" name="hpcIdsLoginId" value="${hpcIds.LOGIN_ID }"/> --%>
							    <input type="hidden" id="hpcIdsLoginId" name="hpcIdsLoginId" />
							</select>
						</td>
						<th><input type="checkbox" name="chkVal" id="chkVal" value="Y" align="center"> 출고일</th>
						<td>
							<input type="text" name="deliOrderDy" id="deliOrderDy" value="" class="day" readOnly />
							<a href="javascript:openCal('hpclForm.deliOrderDy')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>   
						</td>
						<th>유형</th>
						<td>
							<!-- <select name="fedayMallProdDivnNm" id="fedayMallProdDivnNm">
								<option value="">선택</option>
								<option value="Null">Null</option>
								<option value="냉장">냉장</option>
								<option value="냉동">냉동</option>
							</select> -->
							
							<input type="checkbox" name="fedayMallProdDivnNm" id="fedayMallProdDivnNm1" value="NULL" style="vertical-align:middle" checked> NULL
							<input type="checkbox" name="fedayMallProdDivnNm" id="fedayMallProdDivnNm2" value="냉장" style="vertical-align:middle" checked> 냉장
							<input type="checkbox" name="fedayMallProdDivnNm" id="fedayMallProdDivnNm3" value="냉동" style="vertical-align:middle" checked> 냉동
						</td>
						<th>전화번호</th>
						<td>
							<input type="text" name="cellNo" id="cellNo" value="" style="width:92%;" maxlength="30"/>
						</td>
					</tr>
					</table>
		        </div>
		    </div>
		    <div class="wrap_con">
		        <div class="bbs_list">
		            <ul class="tit">
		                <li class="tit">검색결과</li>
		                <c:if test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3'  or loginId eq 'hpc4'  or loginId eq 'hpc5' }">
		                <li>
		                	<table cellpadding="0" cellspacing="0" border="0" align="right">
		                		<tr>
		                			<td class="idtit">할당할 아이디&nbsp;&nbsp;</td>
			                		<td>
			                			<select id ="saveHpcUtakmnId" name="saveHpcUtakmnId" class="select">
											<option value="">선택</option>
											<c:forEach items="${activeId}" var="hpcIds" >
												<option value="${hpcIds.LOGIN_ID }">${hpcIds.LOGIN_ID }</option>
											</c:forEach>
										</select>
									</td>
			                		<td style="padding-left: 5px;">
			                			<li class="btn">
						                    <a href="#" class="btn" id="save"><span><spring:message code="button.common.save"/></span></a>
						                </li>
			                		</td>
		                		</tr>
		                	</table>
		                </li>
		                </c:if>
		            </ul>
		            
		            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
		                <tr>
<!-- 		                    <td><script type="text/javascript">initWiseGrid("WG1", "100%", "317");</script></td> -->
		                    <td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
		                </tr>
		            </table>
		        </div>
		    </div>
		
			<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
		 		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
		 	</div>
		</div>
	</div>
	<div id="footer">
	    <div id="footbox">
	        <div class="msg" id="resultMsg"></div>
	        <div class="location">
	            <ul>
	                <li>홈</li>
	                <li>배송관리</li>
	                <li class="last">해피콜관리</li>
	            </ul>
	        </div>
	    </div>
	</div>
</div>
</form>
</body>
</html>