<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<%@ include file="/common/scm/scmCommon.jsp"%>
<!-- board/PSCMBRD0014 -->

<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){

		//달력셋팅
		$("#startDate, #endDate").attr("readonly", "readonly");
		$('#btnStartDate, #startDate').click(function() {
			openCal('adminForm.startDate');
		});
		$('#btnEndDate, #endDate').click(function() {
			openCal('adminForm.endDate');
		});

		//초기달력값 셋팅
		$("#endDate").val('${endDate}');
		$("#startDate").val('${startDate}');

		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(true);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 		Type:"Text", 	SaveName:"num", 		Align:"Center",		Width:50,	Edit:0}
		  , {Header:"답변", 		Type:"Button",	SaveName:"button",		Align:"Center",		Width:70,	Edit:0, Text:"TEST"}
		  , {Header:"상품코드",	Type:"Text", 	SaveName:"prodCd",	  	Align:"Center",		Width:100,	Edit:0}
		  , {Header:"단품코드",	Type:"Text", 	SaveName:"itemCd",	  	Align:"Center",		Width:50,	Edit:0, Hidden:true}
		  , {Header:"상품명",		Type:"Text", 	SaveName:"prodNm",	  	Align:"Center",		Width:200,	Edit:0, FontColor:"#0000FF"}
		  , {Header:"문의유형",	Type:"Text", 	SaveName:"qstTypeNm",	Align:"Center",		Width:60,	Edit:0}
		  , {Header:"문의코드",	Type:"Text", 	SaveName:"qstTypeCd",	Align:"Center",		Width:60,	Edit:0,	Hidden:true}
		  , {Header:"제목",		Type:"Text", 	SaveName:"title", 		Align:"left",		Width:150,	Edit:0}
		  , {Header:"문의내용",	Type:"Text", 	SaveName:"qstContent",	Align:"left",		Width:200,	Edit:0}
		  , {Header:"처리여부",	Type:"Text", 	SaveName:"procYn",		Align:"Center", 	Width:70,	Edit:0}
		  , {Header:"회원ID",		Type:"Text", 	SaveName:"ecMemberId",		Align:"Center",	Width:110,	Edit:0}
		  , {Header:"회원명",		Type:"Text", 	SaveName:"ecMemberNm",		Align:"Center",	Width:100,	Edit:0}
		  , {Header:"작성자",		Type:"Text", 	SaveName:"regId", 		Align:"Center",		Width:100,	Edit:0}
		  , {Header:"문의일시",	Type:"Text", 	SaveName:"regDate",		Align:"Center",		Width:100,	Edit:0, Format:"Ymd"}
		  , {Header:"처리일시",	Type:"Text", 	SaveName:"ansDate",		Align:"Center",		Width:100,	Edit:0, Format:"Ymd"}
		  , {Header:"상품순번", 	Type:"Text", 	SaveName:"prodQnaSeq",	Align:"Center",		Width:100,	Edit:0 ,	Hidden:true }
		  , {Header:"공개여부",	Type:"Text", 	SaveName:"npblYn",		Align:"Center",		Width:100,	Edit:0 }
		  , {Header:"몰구분",		Type:"Text", 	SaveName:"mallDivnCd",	Align:"Center",		Width:80,	Edit:0,Hidden:true }
		  ];
		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetEllipsis(1);	//말줄임
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

	}); // end of ready

	/**********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		var form = document.adminForm;
		//조회기간 체크
	    if(!doDateCheck()) {
	        return ;
	    }
		
		//특수문자체크
		if(!fnChkSpcCharByte()) {
			return ;
		}
		goPage('1');
	}

	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(currentPage){
		var url = '<c:url value="/board/selectQnaSearch.do"/>';
		
		loadIBSheetData(mySheet, url, currentPage, '#adminForm', null);
	}

	/** ********************************************************
	 * 팝업창
	 ******************************************************** */
		function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if (Row < 1) return;
		
		var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
		if(colNm == 'button'){ // 상품 Q&A 상세보기
			var recommSeq	 = mySheet.GetCellValue(Row, "prodQnaSeq");
			popupDetailView(recommSeq);			
		}
		if (colNm == 'prodNm') { //상품 상세보기
			var prod_cd	 = mySheet.GetCellValue(Row, "prodCd");
			var mail_divn_cd  = mySheet.GetCellValue(Row, 'mallDivnCd');
			prodView(prod_cd, mail_divn_cd);
		}
	}

	/** ********************************************************
	 *인터넷상품 상세팝업
	 ******************************************************** */
	function prodView(prod_cd, mail_divn_cd) {

		var targetUrl = '';
		targetUrl= "https://www.lotteon.com/p/product/LM"+ prod_cd;
		Common.centerPopupWindow(targetUrl, 'product', {width : 970, height : 650,scrollBars : 'YES'});
	}

	/** ********************************************************
	* 상세 팝업창 
	******************************************************** */
	function popupDetailView(recommSeq) {
		var targetUrl = '<c:url value="/board/qnatView.do"/>'+'?ProdQnaSeq='+recommSeq;
		var wSize = 0;
		
		Common.centerPopupWindow(targetUrl, 'product', {width : 800, height : 600,scrollBars : 'YES'});
	}

	/**********************************************************
	 * 조회기간 체크
	 ******************************************************** */
	function doDateCheck() {
	    var form = document.adminForm;

	    if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' )) {
	        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
	        return false;
	    }
	    
	    return true;
	}

	/**********************************************************
	 * 엑셀 팝업링크
	 ******************************************************** */
	function doExcel() {
		var today = '${endDate}'.replace(/-/g, '');
		var xlsUrl = '<c:url value="/board/exportPSCMBRD0014Excel.do"/>';
		var hideCols = 'chk';
		
		directExcelDown(mySheet, '상품Q&A게시판엑셀_'+today, xlsUrl, '#adminForm', null, hideCols); // 전체 다운로드 
	}

	/**********************************************************
	 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
	 ******************************************************** */
	function fnChkSpcCharByte() {
        var str2 = document.adminForm.prodSrchNm.value;
        var len = 0;
        var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

        if (str2.search(exp) != -1) {
            alert("검색항목에는 특수문자를 사용할수 없습니다!");
            return false;
        }

        return true;
	}

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
                <col width="15%" />
                <col width="35%" />
                <col width="15%" />
                <col width="35%" />
            </colgroup>
			<tr>
				<th rowspan="2">
					<select name="prodSrch" id="prodSrch" style="width:70px;" class="select">
						<option value="1">상품명</option>
						<option value="2">상품코드</option>
						<option value="3">회원ID</option>
					</select>
				</th>
                <td rowspan="2">
				   <input type="text" name="prodSrchNm" id='prodSrchNm' maxlength="100" size="28" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" style="vertical-align:middle;" /> 
                </td>
                <th>등록일</th>
                <td class="text">
					<input type="text" id="startDate" name="startDate" style="width:31%;" readonly="readonly" class="day" />
					<a href="javascript:void(0)" id="btnStartDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>~
					<input type="text" id="endDate" name="endDate" style="width:31%;" readonly="readonly" class="day" />
					<a href="javascript:void(0)" id="btnEndDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>
				</td>
			</tr>
            <tr>
                <td colspan="2">
                	문의유형 : <select name="qstTypeSrch" id="qstTypeSrch" readonly style="width:18%;" class="select">
					<option value="">전체</option>
					<c:forEach items="${codeList}" var="QA030"  begin="0">
						<option value="${QA030.MINOR_CD}" >${QA030.CD_NM}</option>
					</c:forEach>
					</select> 

					처리상태 : <select name="procSrchYn" id="procSrchYn"  readonly style="width:18%;" class="select">
						<option value="">전체</option>
						<option value="Y">처리</option>
						<option value="N">미처리</option>
					</select>

					협력사 : <select name="searchVendorId" id="searchVendorId" class="select"  readonly style="width:18%;">
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
          </table>
        </div>
        <!-- 1검색조건 // -->
    </div>
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">조회결과</li>
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

</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>게시판관리</li>
					<li class="last">상품Q&amp;A 게시판</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>