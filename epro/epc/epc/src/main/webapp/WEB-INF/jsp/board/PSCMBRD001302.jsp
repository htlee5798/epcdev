<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- board/PSCMBRD0013 -->

<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){

		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 			Type:"Int", 	SaveName:"num", 		  Align:"Center", Width:30,	 Edit:0}
		  , {Header:"글번호",			Type:"Text", 	SaveName:"recommSeq",	  Align:"Center", Width:100, Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
		  , {Header:"인터넷상품코드",	Type:"Text", 	SaveName:"prodCd",	 	  Align:"Center", Width:110, Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
		  , {Header:"상품명",			Type:"Text", 	SaveName:"prodName",	  Align:"left",   Width:140, Edit:0, Wrap:1}
		  , {Header:"후기 내용",		Type:"Text", 	SaveName:"recommContent", Align:"left",   Width:140, Edit:0}
		  , {Header:"사용여부",		Type:"Combo", 	SaveName:"delYN",  		  Align:"Center", Width:60,	 Edit:0, ComboCode:"N|Y", ComboText:"사용|미사용"}
		  , {Header:"별점",			Type:"Text", 	SaveName:"recommPnt",	  Align:"Center", Width:40,	 Edit:0}
		  , {Header:"이미지여부",		Type:"Text", 	SaveName:"atchFileYN",	  Align:"Center", Width:65,	 Edit:0}
		  , {Header:"회원번호",		Type:"Text", 	SaveName:"memberNo",	  Align:"Center", Width:100, Edit:0}
		  , {Header:"등록일자",		Type:"Text", 	SaveName:"ntceDy",	 	  Align:"Center", Width:80,	 Edit:0}
 		  , {Header:"몰구분",		 	Type:"Text", 	SaveName:"mallDivnCd",	  Align:"Center", Width:80,	 Edit:0, Hidden:true }
 		  
		];

		
		IBS_InitSheet(mySheet, ibdata);
		
		mySheet.SetEllipsis(1);	//말줄임
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.FitColWidth();
	}); // end of ready
	
	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() 
	{
		var form = document.adminForm;
		//조회기간 체크
	    if(!doDateCheck()) 
	    {
	        return false;
	    }
		
		//특수문자체크
		if(!fnChkSpcCharByte()) 
		{
			return false;
		}
		goPage('1');
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(currentPage){
		var url = '<c:url value="/board/exprSearch.do"/>';
		
		loadIBSheetData(mySheet, url, currentPage, '#adminForm', null);
	}
	
	//셀 클릭 시 팝업 호출
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		// 상세정보 함수
		if (Row < 1) return;
		
		var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
		
		if(colNm == 'recommSeq'){ // 체험형 상품평 상세보기
			var recommSeq	 = mySheet.GetCellValue(Row, 'recommSeq');
			var mallDivnCd = mySheet.GetCellValue(Row, 'mallDivnCd');
			popupDetailView(recommSeq, mallDivnCd);			
		}
		
		if (colNm == 'prodCd') { // 상품 상세보기
			var prod_cd	      = mySheet.GetCellValue(Row, 'prodCd');
			var mail_divn_cd  = mySheet.GetCellValue(Row, 'mallDivnCd');
			prodView(prod_cd, mail_divn_cd);
		}
	}
	
	//상세팝업
	function popupDetailView(recommSeq, mallDivnCd) {
		var targetUrl = '<c:url value="/board/exprView.do"/>'+'?recommSeq='+recommSeq;
		var wSize = 0;
		
		//2014.09.30 박지혜 수정
		if(mallDivnCd == '00002') {
			Common.centerPopupWindow(targetUrl, 'product', {width : 800, height : 550,scrollBars : 'YES'});
		} else {
			Common.centerPopupWindow(targetUrl, 'product', {width : 800, height : 420,scrollBars : 'YES'});
		}	
	}

	//인터넷상품 상세팝업  2014.09.22 박지혜 수정
	function prodView(no, mallDivnCd) {

		var targetUrl = '';
		if(mallDivnCd == "00002") { //TRU몰
			targetUrl = '<c:url value="http://toysrus.lottemart.com/product/ProductDetailAdmin.do"/>'+'?ProductCD='+no+'&strCd=307&approvalGbn=N';
		}else{
			targetUrl = '<c:url value="http://www.lottemart.com/product/ProductDetail.do"/>'+'?ProductCD='+no+'&strCd=307&approvalGbn=N&previewYN=Y';
		}
		Common.centerPopupWindow(targetUrl, 'product', {width : 970, height : 650,scrollBars : 'YES'});
	}

	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		// 조회된 데이터가 승인완료인 경우는 체크 금지
	 	var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
		    mySheet.SetCellFontColor(i, 'recommSeq', '#0000FF');
		    mySheet.SetCellFontColor(i, 'prodCd', '#0000FF');
		    mySheet.SetCellFontUnderline(i, 'recommSeq', 1); 
		    mySheet.SetCellFontUnderline(i, 'prodCd', 1); 
		}
	}	
	
	/**********************************************************
	 * 조회기간 체크
	 ******************************************************** */
	function doDateCheck()
	{
	    var form = document.adminForm;
	
	    if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' ))
	    {
	        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
	        return false;
	    }
	    
	    return true;
	}
	
	/**********************************************************
	 * 엑셀 팝업링크
	 ******************************************************** */
	function doExcel()
	{
		var today = '${endDate}'.replace(/-/g, '');
		var xlsUrl = '<c:url value="/board/exportPSCMBRD001302Excel.do"/>';
		var hideCols = 'chk';

		directExcelDown(mySheet, '체험형 상품평게시판 엑셀_'+today, xlsUrl, '#adminForm', null, hideCols); // 전체 다운로드 
	}
	
	/**********************************************************
	 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
	 ******************************************************** */
	function fnChkSpcCharByte()
	{
        var str1 = document.adminForm.recommSeq.value;
        var str2 = document.adminForm.prodSrchNm.value;
        var len = 0;
        var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

        if (str1.search(exp) != -1 && str2.search(exp) != -1)
        {
            alert("검색항목에는 특수문자를 사용할수 없습니다!");
            return false;
        }

        return true;
	}
	
	/**********************************************************
	 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
	 ******************************************************** */
	function keyCode(e)
    {
        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
        
        if (code >  32 && code <  48) keyResult(e);
        if (code >  57 && code <  65) keyResult(e);
        if (code >  90 && code <  97) keyResult(e);
        if (code > 122 && code < 127) keyResult(e);
    }
    function keyResult(e)
    {
        alert("검색항목에는 특수문자를 사용할수 없습니다!");
        
        if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
        }
        else {
            e.preventDefault(); //FF - Chrome both
        }
    }
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<div id="content_wrap">
	
	<!-- div class="content_scroll"-->
	<div>
	<form name="adminForm" id="adminForm">
	<div id="wrap_menu">
	    <div class="wrap_search">
	        <!-- 01 : search -->
	        <div class="bbs_search">
	            <ul class="tit">
	                <li class="tit">조회조건</li>
	                <li class="btn">
	                    <a href="javascript:doSearch();" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
	                    <a href="javascript:doExcel();" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
	                </li>
	            </ul>
	            <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	            <colgroup>
	                <col width="15%">
	                <col width="35%">
	                <col width="15%">
	                <col width="35%">
	            </colgroup>
				<TR> 
	               <th>글번호</th>
	               <td> 
					   <input type="text" name="recommSeq" id='recommSeq' value="${recommSeq}" maxlength="150" size="41" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" style="vertical-align:middle;"/> 
	               </td>
	                <th>등록일</th>
	                <td>
	                   <input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
						~
						<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
	                </td> 
				</TR>
	            <tr>
	                <th>상품검색</th>
	                <td>
	                    <select name="prodSrch" id="prodSrch" style="width:70px;">
							<option value="1">상품명</option>
							<option value="2">상품코드</option>
						</select> 
					   <input type="text" name="prodSrchNm" id='prodSrchNm' value="${prodSrchNm}" maxlength="100" size="28" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" style="vertical-align:middle;"/> 
	                </td>
	                <th>협력업체코드</th>
	                <td>    
						<select name="searchVendorId" id="searchVendorId" class="select" style="width:31%;">
							<option value="">전체</option>
						<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
						
							<c:if test="${not empty searchVO.searchVendorId}">
								<option value="${venArr}" <c:if test="${venArr eq searchVO.searchVendorId}">selected</c:if>>${venArr}</option>
							</c:if>	
							<c:if test="${empty searchVO.searchVendorId}">
								<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
							</c:if>	
	                        
						</c:forEach>
						</select>
		            </td>
	            </tr>
	            <tr>
	                <th>사용여부</th>
	                <td>
	                    <select name="delYnSrch" id="delYnSrch" style="width:100px;">
							<option value="">전체</option>
							<option value="N">사용</option>
							<option value="Y">미사용</option>
						</select> 
	                </td>
	                <!-- 기존 소스 주석처리 
	                <td colspan="2">
						&nbsp;&nbsp;<b>상품평구분</b>&nbsp; 
	                    <select name="recommDivnSrch" id="recommDivnSrch" style="width:100px;">
							<option value="">전체</option>
							<option value="03">일반상품평</option>
							<option value="04">포토상품평</option>
						</select>  
		            </td>
		             -->
		            <th>상품평구분</th>
		            <td>
		            	<select name="recommDivnSrch" id="recommDivnSrch" style="width:100px;">
							<option value="">전체</option>
							<option value="03">일반상품평</option>
							<option value="04">포토상품평</option>
						</select>
		            </td>
	            </tr>
	            <tr>
	            	<th>몰구분</th>
	            	<td colspan="3">
	                    <select name="mallDivnCd" id="mallDivnCd" style="width:100px;">
							<option value="">전체</option>
							<option value="00001">롯데마트몰</option>
							<option value="00002">토이저러스몰</option>
						</select>
					</td>
	            </tr>
	            </table>
	            
	        </div>
	        <!-- 1검색조건 // -->
	    </div>
	    
	    <!-- 2 검색내역  -->
	    <div class="wrap_con">
	        <!-- list -->
	        <div class="bbs_list">
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
	
	</div>
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="location">
					<ul>
						<li>홈</li>
						<li>게시판관리</li>
						<li class="last">상품평게시판</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>