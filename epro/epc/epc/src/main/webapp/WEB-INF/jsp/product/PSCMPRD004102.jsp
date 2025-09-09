<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="lcn.module.common.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String Date  =DateUtil.formatDate(DateUtil.getToday(),"-").replace("-", "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<!-- 신규 공통 css 및 js 파일 INCLUDE -->
	<c:import url="/common/commonHead.do" />
	<%@ include file="/common/scm/scmCommon.jsp"%>
	<!-- product/PSCMPRD004101 -->

	<!-- 공통 코드 세팅 -->
	<c:set var="sm341Cd" />
	<c:set var="sm341Nm" />
	<c:forEach items="${codeList}" var="sm341Code" varStatus="idx2">
		<c:choose>
			<c:when test="${ fn:length(codeList) eq idx2.index+1  }">
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.MINOR_CD }"/>
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.CD_NM  }"/>
			</c:when>
			<c:otherwise>
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.MINOR_CD }|"/>
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.CD_NM  }|"/>
			</c:otherwise>
		</c:choose>
	</c:forEach>

<script type="text/javascript">
	$(document).ready( function() {

		//excel 관련 변수
		var obj = new Object();
		applyToTypeCdList = new  Array();
		applyToCdList = new  Array();
		applyToNameList = new  Array();

		//달력셋팅
		var date = new Date();
		var time = date.getHours();
		if(time < 10){
			time = '0' + time
		}
		var nowDy = <%=Date%> + String(time);
		var announStartDy = '${data.announStartDy}';
		announStartDy = announStartDy.replace(/-/gi,'') + '${data.startTime}';

		if($('#useYn').val() == 'Y' && announStartDy <= nowDy ){
			alert('게시가 시작된 공지면 [공지기간 시작일]을 수정 할 수 없습니다.');
		}

		$("#startDate, #endDate").attr("readonly", "readonly");
		
		if(announStartDy > nowDy ){
			$('#btnStartDate, #startDate').click(function() {
				openCal('dataForm.startDate');
			});
		}
		$('#btnEndDate, #endDate').click(function() {
			openCal('dataForm.endDate');
		});

		//초기달력값 셋팅
		$("#startDate").val('${data.announStartDy}');
		$("#endDate").val('${data.announEndDy}');

		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "170px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

		var ibdata = {};

		// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
		ibdata.Cfg = { SizeMode : sizeAuto, SearchMode : smGeneral, MergeSheet : msHeaderOnly }; // 10 row씩 Load

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = { Sort : 1, ColMove : 0, ColResize : 1, HeaderCheck : 1 	};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [ 
		  {	Header : "", 	Type : "CheckBox", 	SaveName : "chk", Align : "Center", 	Width : 50, Edit : 1, Sort : false }
		, { Header : "순번", Type : "Seq", 	SaveName : "seq", Align : "Center", Width : 50, 	Edit : 0 }
		, { Header : "적용대상유형명", Type : "Combo", 	SaveName : "applyToTypeCd", Align : "Center", Width : 150, 	Edit : 0 , ComboText:"<c:out value="${ sm341Nm }"/>",ComboCode:"<c:out value="${ sm341Cd }"/>" 	}
		, { Header : "적용대상코드", Type : "Text", SaveName : "applyToCd", Align : "Center", 	Width : 150, 	Edit : 0 }
		, { Header : "적용대상명", Type : "Text", SaveName : "applyToNm", Align : "Center", Width : 350, Edit : 0 }
		, { Header : "확인", Type : "Status", SaveName : "S_STATUS", Align : "Center", 	Width : 50, Edit : 0, Hidden : true } 
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetEllipsis(1);	//말줄임
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

		//exeExcel양식다운
		$('#createExcelForm').click( function() {
			if (!confirm('엑셀 양식을 다운로드하시겠습니까?')) {
				return;
			}
			var hideCols = 'chk|num';
			excelFormDown(mySheet, '협력사 할당_양식', 1, hideCols);
			//fnMove();
		});

		//찾아보기
		$('#findFile').click(function() {
			$('#file').click();
			msgPath = "파일이 업로드 되었습니다 업로드 버튼을 눌러주세요";
			if ($('#file').val().length > 0) { // 파일 선택 시
				$("#excelOp").val(msgPath);
			} else {
				$("#excelOp").val("");
			}
			//fnMove();
		});

		// 엑셀파일 업로드 (서버 통신 필요)
		$('#uploadExcel') .click( function() {
							if ($("#file").val() == "") {
								alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
								return;
							}
							$("#save").attr("src","load");
							$("#dataForm").attr("enctype","multipart/form-data");
							document.dataForm.target = "save";
							document.dataForm.action = '<c:url value="/excelLoad/IBSheetExcelLoad.do"/>';
							document.dataForm.submit();
							$("#dataForm").attr("enctype","");
							//fnMove();
						});
		
		
		$('#save').load( function(){
			if( $("#save").attr("src") != ""){
				console.log("=============");
				obj.applyToTypeCdList = applyToTypeCdList;
				obj.applyToCdList = applyToCdList;
				obj.applyToNameList = applyToNameList;
				setProdArray(obj);
			}
		});

		//excelupload 일괄 저장
		$('#excelInsert').click(function() {
			if ($("#file").val() == "") {
				alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
				return;
			}
			doInsert();
		});

		// 컬럼삭제
		$('#deleteCol').click(function() {
			deleteCol();
		});

		// 컬럼추가
		$('#addCol').click(function() {
			addCol();
		});
		
		//ibsheet 세팅
		seachDtl();
		
		//에디터 1 세팅
		CrossEditor1.SetBodyValue(document .getElementById("pcContent").value);
		
		//에디터 2 세팅 삭제
		//CrossEditor2.SetBodyValue(document 	.getElementById("moblContent").value);
		
		//바이트 초기 세팅
		var text =   $('#title').val().getBytes1("UTF-8");
		document.getElementById('byteInfo1').innerText = text;
		
	}); // end of ready

	/**********************************************************
	 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
	 ******************************************************** */
	function keyCode(e) {
        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
        
        if (code >  32 && code <  48) keyResult(e);
        if (code >  57 && code <  65) keyResult(e);
        if (code >  90 && code <  97) keyResult(e);
        if (code > 122 && code < 127) keyResult(e);
    }

	function keyResult(e) {
	    alert("특수문자를 사용할수 없습니다!");

	    if (navigator.appName != "Netscape") {
	        event.returnValue = false;  //IE - Chrome both
	    } else {
	        e.preventDefault(); //FF - Chrome both
	    }
	}

	/**********************************************************
	 * 글자 바이트 체크
	 ******************************************************** */
	String.prototype.getBytes1 = function() {
		var size = 0;
		for(i=0; i<this.length; i++) {
			var temp = this.charAt(i);
			if(escape(temp) == '%0D') continue;
			if(escape(temp).indexOf("%u") != -1) {
				size += 3;
			}else {
				size++;
			}
		}
		return size;
	}

	/** ********************************************************
	 *포커스 이동
	 ******************************************************** */
	 function fnMove(){
	    var offset = $("#div1").offset();
	    $('html, body').animate({scrollTop : offset.top}, 1);
	}

	/** ********************************************************
	 * 셀링포인트 글자제한
	 ******************************************************** */
	function fnChkByte(obj, maxByte , check){
		var str = obj.value;
		var str_len = str.length;

		var rbyte = 0;
		var rlen = 0;
		var one_char = "";
		var str2 = "";

		for(var i=0; i<str_len; i++){
		one_char = str.charAt(i);
		if(escape(one_char).length > 4){
			  rbyte += 3;   
		}else{
		    rbyte++; //영문 등 나머지 1Byte
		}

		if(rbyte <= maxByte){
		    rlen = i+1; //return할 문자열 갯수
		}
		}

		if(rbyte > maxByte){
		    alert("한글 "+parseInt(maxByte/3)+"자 / 영문 "+maxByte+"자를 초과 입력할 수 없습니다.");
		    str2 = str.substr(0,rlen); //문자열 자르기
		    obj.value = str2;
		    fnChkByte(obj, maxByte);
		}

		//바이트 체크
		var text =   $(obj).val().getBytes1("UTF-8");
		document.getElementById(check).innerText = text;
		}


		/** ********************************************************
		 * ibsheet 세팅
		******************************************************** */
		function seachDtl() {
			
			var url = '<c:url value="/product/selectSheet.do"/>';
			
			loadIBSheetData(mySheet, url, 1, '#dataForm', null);
		}

		/** ********************************************************
		 * 컬럼 업로드
		 ******************************************************** */
		function setData(applyToTypeCd ,applyToCd, applyToNm) {
			applyToCdList.push(applyToCd);
			applyToTypeCdList.push(applyToTypeCd);
			applyToNameList.push(applyToNm);
		
		}

		 /** ********************************************************
		 * 컬럼 업로드
		 ******************************************************** */	
		 function setProdArray(list){
				//중복 제거
			var msg = "";
			
		 	for (var c = 0; c <= mySheet.LastRow(); c++) {
		 		for(var i = 0; i <list.applyToCdList.length; i++){
					if(mySheet.GetCellValue(c,3)==list.applyToCdList[i] ){
						msg += '적용대상 코드 '+list.applyToCdList[i]+' 은 중복된 데이터 입니다. \n';
						 alert(msg); 
						return;
					}
		 		}
			}
		
			for(var i = 0; i <list.applyToCdList.length; i++){
			
				if(list.applyToTypeCdList[i]=='상품코드' ||  list.applyToTypeCdList[i]=='카테고리코드' || list.applyToTypeCdList[i]=='업체ID' ){
					if(list.applyToTypeCdList[i]=='상품코드'){
						list.applyToTypeCdList[i] = 10;
					}else if(list.applyToTypeCdList[i]=='카테고리코드'){
						list.applyToTypeCdList[i] = 20;
					}else if(list.applyToTypeCdList[i]=='업체ID'){
						list.applyToTypeCdList[i] = 30;
					} 
				
					var rowIdx = mySheet.DataInsert(0);
					mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq()); // 순번 자동 입력
					mySheet.SetCellValue(rowIdx, 'applyToCd', list.applyToCdList[i]);
					mySheet.SetCellValue(rowIdx, 'applyToTypeCd', list.applyToTypeCdList[i]);
					mySheet.SetCellValue(rowIdx, 'applyToNm', list.applyToNameList[i]);
				
			}else{
				msg +='적용대상 유형 '+ list.applyToTypeCdList[i]+'는 잘못된 데이터 입니다. \n';
			}
		}
		if(msg!=""){
			 alert(msg); 
		}
	}

	/** ********************************************************
	 * 일괄저장 (삭제 예정)
	 ******************************************************** */
/* 		function doInsert() {
		if (mySheet.CheckedRows("chk") == 0) {
			alert("선택된 데이터가 없습니다.");
		} else {
			var inChk = mySheet.FindCheckedRow("chk");
			var inChkArr = inChk.split("|");
			var announSeq = $("#announSeq").val(); //seq
			var applyToTypeCd_chkVal = "";
			var applyToCd_chkVal = "";

			if (inChkArr.length == 1) {
				applyToTypeCd_chkVal = mySheet.GetCellValue(inChkArr[0],
						'applyToTypeCd');
				applyToCd_chkVal = mySheet.GetCellValue(inChkArr[0],
						'applyToCd');
			} else {
				for (var i = 0; i < inChkArr.length; i++) {
					if (i == inChkArr.length - 1) {
						applyToTypeCd_chkVal += mySheet.GetCellValue(
								inChkArr[i], 'applyToTypeCd');
						applyToCd_chkVal += mySheet.GetCellValue(
								inChkArr[i], 'applyToCd');
					} else {
						applyToTypeCd_chkVal += mySheet.GetCellValue(
								inChkArr[i], 'applyToTypeCd')
								+ "/";
						applyToCd_chkVal += mySheet.GetCellValue(
								inChkArr[i], 'applyToCd')
								+ "/";
					}
				}
			}

			var url = '<c:url value="/product/insertAllPoint.do"/>';
			mySheet.DoSave(url, {
				Param : 'applyToTypeCd_chkVal=' + applyToTypeCd_chkVal
						+ '&applyToCd_chkVal=' + applyToCd_chkVal
						+ '&announSeq=' + announSeq,
				Quest : false
			});
		}
	} */

/**********************************************************
 * 엑셀 팝업링크
 ******************************************************** */
function doExcel() {
	var dateObj = new Date();
	var year = dateObj.getFullYear();
	var month = dateObj.getMonth()+1;
	var day = dateObj.getDate();
	var today = year + "-" + month + "-" + day;
	
	var xlsUrl = '<c:url value="/product/exportPSCMPRD0041Excel.do"/>';
	var hideCols = 'chk';
	
	directExcelDown(mySheet, '협력사공지관리상세엑셀_'+today, xlsUrl, '#dataForm', null, hideCols); // 전체 다운로드 
}

/** ********************************************************
 * 컬럼 삭제
 ******************************************************** */
function deleteCol() {
	if (mySheet.CheckedRows("chk") == 0) {
		alert("선택된 데이터가 없습니다.");
	} else {
		var upChk = mySheet.FindCheckedRow("chk");
		mySheet.RowDelete(upChk);
	}
	//fnMove();
}

/** ********************************************************
 * 메세지 창
 ******************************************************** */
function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
	alert(Msg);
	opener.doSearch();
	top.close();
}

/** ********************************************************
 * 협력사 공지게시판 수정
 ******************************************************** */
function doUpdate() {
    var form = document.dataForm;
    var newProdDescBodyVal	=	CrossEditor1.GetBodyValue();
	var newProdDescTxtVal	=	CrossEditor1.GetTextValue();				
	newProdDescTxtVal		=	newProdDescTxtVal.replace(/^\s*/,'').replace(/\s*$/, ''); 
	
	if($('#useYn').val() == 'N'){
		alert("사용중지가 된 공지는 수정할 수 없습니다.");
		return;	
	}
	
	if(form.title.value == ""){
		alert("제목을 입력해주세요.");
		form.title.focus();
		return;
	}
	
	if(newProdDescTxtVal == "" && newProdDescBodyVal.indexOf('<IMG')=='-1' && newProdDescBodyVal.indexOf('<img')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<IFRAME')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<iframe')=='-1'  && newProdDescBodyVal.toUpperCase().indexOf('<EMBED')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<embed')=='-1'){
		alert('내용을 입력해주세요!');
		return;
	}
	
	if(mySheet.LastRow()=="0"){
		alert("적용대상을 등록하세요");
		return;
	}
	
	for(var i=1; i<=mySheet.LastRow(); i++){
		if(mySheet.GetCellValue([i],2) !='10'){
			alert("적용대상유형은 상품코드만 등록가능합니다.");
			return;
		}
	}
	
	var date = new Date();
	var nowTime = date.getHours();
	if(nowTime < 10){
		nowTime = '0' + nowTime
	}
	var nowDy = <%=Date%>+String(nowTime);
	var announStartDy = '${data.announStartDy}' ;
	announStartDy = announStartDy.replace(/-/gi,'')+'${data.startTime}';
	
	if($('#useYn').val() == 'Y' && announStartDy > nowDy ){
		if(form.startDate.value.replace( /-/gi, '' )+form.startTime.value <= nowDy){
			alert("현재날짜/시각 이전으로 등록할 수 없습니다.");
			return;
		}
	}
	
	if (form.startDate.value.replace( /-/gi, '' ) >= form.endDate.value.replace( /-/gi, '' )){
        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
        return ;
    }
	
	if (!confirm('수정 하시겠습니까?')) {
		return;
	}

		//데이터 추가
		var pcContent = escape(encodeURIComponent(CrossEditor1.GetBodyValue()));
		// 모바일컨텐츠 삭제
		//var moblContent =  escape(encodeURIComponent(CrossEditor2.GetBodyValue()));				
		var startDate = $('#startDate').val();
		var startTime  = $('#startTime').val();
		var endDate  = $('#endDate').val();
		var endTime  = $('#endTime').val();
		var announSeq  = $("#announSeq").val();
	
		var title = escape(encodeURIComponent($('#title').val()));
			
		var url = '<c:url value="/product/saveVondorMgr.do"/>';
		mySheet.DoAllSave(url, {
			Param : 'startDate=' + startDate
			+ '&startTime=' + startTime
			+ '&endDate=' + endDate
			+ '&endTime=' + endTime
			+ '&title=' + title
			+ '&pcContent=' + pcContent
			+ '&announSeq=' + announSeq,
			Quest : 0
			});	
		
	}

	/** ********************************************************
	 * 적용대상 팝업 
	 ******************************************************** */
	function addCol() {
		var searchType = $('#searchType').val();
		var vendorId = $('#regId').val(); 
		var targetUrl ="";
		var msg = ""
		if (searchType == '10') {
			 targetUrl = '<c:url value="/common/viewPopupProductList2.do"/>'+'?gubun=Int';
			 msg = 'prd';	
		} else if (searchType == '20') {
			 targetUrl ='<c:url value="/common/selectCategoryPopUpView.do"/>';
			 msg ='popup';
		} else if (searchType == '30') {
			 targetUrl = '<c:url value="/common/selectPartnerSmallPopup.do"/>';
			 msg ='popup';
		}
		Common.centerPopupWindow(targetUrl, msg, {width : 750, height : 500});
		//fnMove();
	}
	
	//상품 정보 받아오는 펑션
	 function popupReturn(rtnVal){
	    	for(var i=0; i<rtnVal.prodCdArr.length; i++){
	    		for (var c = 0; c <= mySheet.LastRow(); c++) {
					if(mySheet.GetCellValue(c,3)==rtnVal.prodCdArr[i]){
							alert(rtnVal.prodNmArr[i]+"는 중복된 데이터 입니다");
							//fnMove();
							return;
					}
				}
	    		var rowIdx = mySheet.DataInsert(0);
	    		mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq());
	        	mySheet.SetCellValue(rowIdx, "applyToCd", rtnVal.prodCdArr[i]);
	        	mySheet.SetCellValue(rowIdx, "applyToNm", rtnVal.prodNmArr[i]);
	        	mySheet.SetCellValue(rowIdx, "applyToTypeCd", $('#searchType').val());
	    	}
	    	//fnMove();
	    }
	
	//협력 업체 받아오는 펑션
	 function setVendorInto(strVendorId, strVendorNm, strCono) {
			 for (var c = 0; c <= mySheet.LastRow(); c++) {
					if(mySheet.GetCellValue(c,3)==strVendorId ){
						alert(strVendorNm+"는 중복된 데이터 입니다");
							return;
					}
				}
				var rowIdx = mySheet.DataInsert(0);
	    		mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq());
	        	mySheet.SetCellValue(rowIdx, "applyToCd", strVendorId);
	        	mySheet.SetCellValue(rowIdx, "applyToNm", strVendorNm);
	        	mySheet.SetCellValue(rowIdx, "applyToTypeCd", $('#searchType').val());
	        	//fnMove();
		}
	
	//카테고리 받아오는 펑션
	 function setCategoryInto(categoryId, categoryNm) { 
		 for (var c = 0; c <= mySheet.LastRow(); c++) {
				if(mySheet.GetCellValue(c,3)==categoryId ){
					alert(categoryNm+"는 중복된 데이터 입니다");
						return;
				}
			}
		 var rowIdx = mySheet.DataInsert(0);
    		mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq());
        	mySheet.SetCellValue(rowIdx, "applyToCd", categoryId);
        	mySheet.SetCellValue(rowIdx, "applyToNm", categoryNm);
        	mySheet.SetCellValue(rowIdx, "applyToTypeCd", $('#searchType').val());
        	//fnMove();
		}
</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>

	<form name="dataForm" id="dataForm" method="post" enctype="multipart/form-data">
		<input  type="hidden" value="${announSeq}" name="announSeq"  id="announSeq"/>
		<input  type="hidden" value="${regId}" name="regId"  id="regId"/>
		<div id="popup">
			<!--  @title  -->
			<div id="p_title1">
				<h1>협력사 공지사항 수정</h1>
				<span class="logo"> <img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" 	alt="LOTTE MART" /></span>
			</div>
			<!--  @title  //-->
			<!--  @process  -->
			<div id="process1">
				<ul>
					<li>홈</li>
					<li>상품관리</li>
					<li>상품정보관리</li>
					<li class="last">협력사공지관리</li>
				</ul>
			</div>
		</div>
		<!--  @process  //-->
		<div class="popup_contents">
			<!-- 셀링포인트 내용-->
			<div class="bbs_search3">
				<ul class="tit">
					<li class="btn">
						<a href="#" class="btn" onclick="doUpdate()"><span><spring:message code="button.common.update" /></span></a>
						<a href="#" class="btn" onclick="top.close();"><span><spring:message code="button.common.close" /></span></a>
					</li>
				</ul>

				<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="15%">
							<col width="85%">
					</colgroup>

					<tr>
						<th><span class="star">*&nbsp</span>제목  </th>
						<td colspan="3">
							<input type="text" id="title" name="title" size="88"  value="${data.title }" onKeyUp="javascript:fnChkByte(this,'200','byteInfo1')" onKeyPress="keyCode(event)" /> <b> (<span id="byteInfo1">0</span>/200Byte) </b>
						</td>
					</tr>	
					<tr>
						<th class="fst"><span class="star">*&nbsp</span>공지기간</th>
						<td class="text"><input type="text" id="startDate" name="startDate" style="width: 15%;" readonly class="day" /> 
							<select id="startTime" name="startTime"  class="select">
								<script>
								var date = new Date();
								var time = date.getHours();
								if(time < 10){
									time = '0' + time
								}
								var nowDy = <%=Date%> + String(time);
								var announStartDy = '${data.announStartDy}';
								announStartDy = announStartDy.replace(/-/gi,'') + '${data.startTime}';
								
								if(announStartDy <= nowDy) {
									document.write("<option value="+'${data.startTime}'+" selected >"+'${data.startTime}'+"시"+"</option>");
								}else{
									for(i = 0;i<24;i++){
										var data = i;
										if(data < 10){
											data = "0"+i;
										}
										if('${data.startTime}'==i){
											document.write("<option value="+data+" selected >"+data+"시"+"</option>");
										}else {
										document.write("<option value="+data+">"+data+"시"+"</option>");
										}
									}
								}
							</script>
						</select>
						</select> <a href="#" id="btnStartDate"><img
								src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif"
								alt="absmiddle" class="middle" /></a>~ <input type="text"
							id="endDate" name="endDate" style="width: 15%;" readonly
							class="day" />
							 <select id="endTime" name="endTime" class="select">
								<script>
									for(i = 0;i<24;i++){
										var data = i;
										if(data < 10){
											data = "0"+i;
										}
										if('${data.endTime}'==i){
											document.write("<option value="+data+" selected >"+data+"시"+"</option>");
										}else {
										document.write("<option value="+data+">"+data+"시"+"</option>");
										}
									}
							</script>
						</select> <a href="#" id="btnEndDate"><img
								src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif"
								alt="absmiddle" class="middle" /></a></td>
					</tr>
					<tr style="height: 150px">
						<th><span class="star">*&nbsp</span>PC/모바일</th>
						<td>
							<!-- editor 시작 ----------------------------------------------------------------------->
							<div class="bbs_list">
								<table class="bbs_grid3" cellpadding="0" cellspacing="0"
									border="0">
									<textarea id="pcContent" name="pcContent" alt="pcContent"
										style="display: none">${data.pcContent}</textarea>
									<script type="text/javascript" language="javascript">
										var CrossEditor1 = new NamoSE('pe_agt1');

										CrossEditor1.params.Width = "100%";
										CrossEditor1.params.Height = "300";
										CrossEditor1.params.UserLang = "auto";
										CrossEditor1.params.FullScreen = false;
										CrossEditor1.params.ImageSavePath = "edi";
										CrossEditor1.params.SetFocus = false; // 에디터 포커스 해제
										CrossEditor1.EditorStart();
									</script>

								</table>
							</div> <!-- editor 끝 ----------------------------------------------------------------------->

						</td>
					</tr>
					<!-- 2020.06.08 협력사공지사항 모바일 등록 제거 -->
					<!-- 
					<tr style="height: 150px">
						<th>모바일</th>
						<td width="10%" height="10%">		
							<div class="bbs_list">
								<table class="bbs_grid3" cellpadding="0" cellspacing="0"
									border="0">
									<textarea id="moblContent" name="moblContent" alt="moblContent"
										style="display: none" >${data.moblContent}</textarea>
									<script type="text/javascript" language="javascript">
										var CrossEditor2 = new NamoSE('pe_agt2');
										CrossEditor2.params.Width = "100%";
										CrossEditor2.params.Height = "300";
										CrossEditor2.params.UserLang = "auto";
										CrossEditor2.params.FullScreen = false;
										CrossEditor2.params.ImageSavePath = "edi";
										CrossEditor2.params.SetFocus = false; // 에디터 포커스 해제
										CrossEditor2.EditorStart();
									</script>

								</table>
							</div>

						</td>
					</tr>
					-->
					 <tr style="display:none;">
						<th><span class="star">*&nbsp</span>사용여부</th>

						<td colspan="3"><select id="useYn" name="useYn"">
								<option value="N" ${data.useYn eq 'N' ? 'selected' : ''}>미사용</option>
								<option value="Y" ${data.useYn eq 'Y' ? 'selected' : ''}>사용</option>
						</select></td>
					</tr>
					

				</table>
	    <div class="wrap_con">

			<div class="bbs_list">
			<ul class="tit">
				<li class="tit">적용대상</li>

				<!-- 건색 조건 -->
				<li class="tit">

				<select name="searchType" id="searchType" class="select" >
						<c:forEach items="${codeList}" var="SM341" begin="0">
							<c:if test="${SM341.MINOR_CD == '10'}">
								<option value="${SM341.MINOR_CD}">${SM341.CD_NM}</option>
							</c:if>	
						</c:forEach>
				</select>
				
					<li class="btn">
					<a href="javascript:void(0)" class="btn" id="addCol"> <span><spring:message code="button.common.add" /></span> </a>
					<a href="javascript:void(0)" class="btn" id="deleteCol"> <span><spring:message code="button.common.delete" /></span> </a>
					</li>
				</li>	
				</ul>
					<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col width="13.5%">
							<col width="61.5%">
							<col width="25%">
						</colgroup>
						<tr>
							<th><span class="star">*&nbsp</span>일괄업로드</th>
							<td>
								<input type="text" id="excelOp" name="excelOp" size="70" readonly="readonly" /> 
								<input type="file" name="file" id="file" class="text" style="display: none;" value="" /> 
								<input type="hidden" name="colNms" size="35" value="applyToCd^applyToTypeCd^applyToName" /> <!-- IBSheet 칼럼명 (구분자 ^, 필수 파라미터) -->
								<input type="hidden" name="func" value="setData" /> <!-- 실행 자바스크립트 명 ( 필수 파리미터) -->
								<input type="hidden" name="sheetNm" value="mySheet" /> <!-- IBSheet 명 (필수 파라미터)-->
								<input type="hidden" name="sheetRemoveAll" value="N" />
								<input type="hidden" name="hdRow" value="1" /></td>
							<td>
									<a href="#" class="btn" id="createExcelForm"><span>양식</span></a>
									<a href="#" class="btn" id="findFile"><span>찾아보기</span></a> 
									<a href="#" class="btn" id="uploadExcel"> <span> 업로드 </span> </a>
							</td>
						</tr>
					</table>
					
			<!-- IBSHEET  -->
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<div id="div1">
						<td><div id="ibsheet1"></div></td>
					</div>
				</tr>
			</table>
			<iframe name="save" id="save"  style="display: none;"></iframe>
			</div></div></div></div>
	</form>
	<br></br>
</body>
</html>