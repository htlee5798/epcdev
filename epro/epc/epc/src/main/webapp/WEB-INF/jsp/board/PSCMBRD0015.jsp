<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<%@ include file="/common/scm/scmCommon.jsp"%>
<!-- board/PSCMBRD0015 -->

<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){
		
		//달력셋팅
		$("#startDate, #endDate").attr("readonly", "readonly");
		$('#btnStartDate, #startDate').click(function() {
			openCal('form1.startDate');
		});
		$('#btnEndDate, #endDate').click(function() {
			openCal('form1.endDate');
		});
		
		//초기달력값 셋팅
		$("#endDate").val('${endDate}');
		$("#startDate").val('${startDate}');
		
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(true);
	
		var ibdata = {};
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral,MergeSheet:msHeaderOnly};
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		ibdata.Cols = [
			{Header:"순번", 			Type:"int",				SaveName:"num",				Width:15,	Align:"Center",	Edit:0}
		  , {Header:"판매자상품코드", 	Type:"Text", 			SaveName:"spdNo",			Width:70,	Align:"Center", Edit:0} 
		  , {Header:"리뷰유형코드", 	Type:"Text", 			SaveName:"rvTypCd", 		Width:30,	Align:"Center", Edit:0}
		  , {Header:"리뷰구분코드",	 	Type:"Text", 			SaveName:"rvDvsCd", 		Width:30,	Align:"Center", Edit:0}
		  , {Header:"리뷰내용", 		Type:"Text", 			SaveName:"rvCnts", 		 	Width:100,	Align:"Center", Edit:0,	Cursor:'pointer', Color:'blue', FontUnderline:true}
		  , {Header:"상품만족도값", 	Type:"Text", 			SaveName:"pdStfdVal", 		Width:30,	Align:"Center", Edit:0}
		  , {Header:"회원번호", 		Type:"Text", 			SaveName:"mbNo", 		 	Width:50,	Align:"Center", Edit:0}
		  , {Header:"리뷰등록일시", 	Type:"Date", 			SaveName:"rvRegDttm", 		Width:80,	Align:"Center", Edit:0,	Format:"yyyy-MM-dd HH:mm"}
		  , {Header:"리뷰컨텐츠정보", 	Type:"Text", 			SaveName:"rvCtntInfo", 		Width:80,	Align:"Center", Edit:0,	Hidden:true}
		  , {Header:"리뷰콘텐츠순번", 	Type:"Text", 			SaveName:"rvCtntSeq", 		Width:80,	Align:"Center", Edit:0,	Hidden:true}
		  , {Header:"리뷰콘텐츠유형코드", Type:"Text", 			SaveName:"rvCtntTypCd", 	Width:80,	Align:"Center", Edit:0,	Hidden:true}
		  , {Header:"리뷰콘텐츠경로명", 	Type:"Text", 			SaveName:"rvCtntRteNm", 	Width:80,	Align:"Center", Edit:0,	Hidden:true}
		  
		];
		
		IBS_InitSheet(mySheet, ibdata);

		mySheet.FitColWidth();
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.SetComboOpenMode(1);
	
	}); // end of ready
	
	function doSearch() {
		
		var form = document.form1;
		//조회기간 체크
	    if(!doDateCheck()) 
	    {
	        return ;
	    }

		goPage('1');
		
	}

 	function goPage(currentPage) {
		var url = '<c:url value="/board/ecReviewSearch.do"/>';
		var param = new Object();
		var oldStartDate = $("#startDate").val();
		var oldEndDate = $("#endDate").val();
		var startDate = oldStartDate.split("-").join("");
		var endDate = oldEndDate.split("-").join("");

		param.currentPage 		= currentPage;
		param.rowsPerPage 	= $("#rowsPerPage").val();
		param.spdNo 				= $("#spdNo").val();
		param.rvTypCd 			= $("#rvTypCd").val();
		param.rvDvsCd 			= $("#rvDvsCd").val();
		param.startDate 			= startDate;
		param.endDate 			= endDate;
		
		loadIBSheetData(mySheet, url, currentPage, '#form1', param);
	}
	
 	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if( Row > 0 &&  mySheet.ColSaveName(Col) == "rvCnts"){
			  
			var spdNo = mySheet.GetCellValue(Row, "spdNo");
			var rvTypCd = mySheet.GetCellValue(Row, "rvTypCd");
			var rvDvsCd = mySheet.GetCellValue(Row, "rvDvsCd");
			var rvCnts = mySheet.GetCellValue(Row, "rvCnts");
			var pdStfdVal = mySheet.GetCellValue(Row, "pdStfdVal");
			var mbNo = mySheet.GetCellValue(Row, "mbNo");
			var rvRegDttm = mySheet.GetCellValue(Row, "rvRegDttm");
			var rvCtntInfo = mySheet.GetCellValue(Row, "rvCtntInfo");
			var rvCtntSeq = mySheet.GetCellValue(Row, "rvCtntSeq");
			var rvCtntTypCd = mySheet.GetCellValue(Row, "rvCtntTypCd");
			var rvCtntRteNm = mySheet.GetCellValue(Row, "rvCtntRteNm");

			$("#SspdNo").val(spdNo);
			$("#SrvTypCd").val(rvTypCd);
			$("#SrvDvsCd").val(rvDvsCd);
			$("#SrvCnts").val(rvCnts);
			$("#SpdStfdVal").val(pdStfdVal);
			$("#SmbNo").val(mbNo);
			$("#SrvRegDttm").val(rvRegDttm);
			$("#SrvCtntInfo").val(rvCtntInfo);
			$("#SrvCtntSeq").val(rvCtntSeq);
			$("#SrvCtntTypCd").val(rvCtntTypCd);
			$("#SrvCtntRteNm").val(rvCtntRteNm);
				
	     var gsWin = window.open('','ecReview','width=800,height=460, scrollBars : YES');
	     var frm =document.reviewDetail;
	     frm.action =  '<c:url value="/board/ecReviewDetail.do"/>';
	     frm.target ="ecReview";
	     frm.method ="post";
	     frm.submit();

		}
	}
 	
	/**********************************************************
	 * 조회기간 체크
	 ******************************************************** */
	function doDateCheck()
	{
	    var form = document.form1;
	
	    if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' ))
	    {
	        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
	        return false;
	    }
	    
	    return true;
	}
	
	
	/**********************************************************
	 * 엑셀다운
	 *********************************************************/
	function doExcel() {
		var rowCount = mySheet.RowCount();

		if (rowCount == 0) {
			alert('엑셀로 변환 할 자료가 없습니다.');
			return;
		}

		excelConf = confirm('엑셀 다운로드 하시겠습니까?');

		if (excelConf == true) {
			excelDown();
		}
		if (excelConf == false) {
			return false;
		}
	}

	function excelDown() {
		var xlsUrl = '<c:url value="/board/ecReviewExcelDown.do"/>';
		var hideCols = 'rvCnts';
		
		directExcelDown(mySheet, '롯데ON 상품평 관리', xlsUrl, '#form1', null, hideCols); // 전체 다운로드
	}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<!-- div class="content_scroll"-->
<div>
	<form name="form1" id="form1">
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
						<col width="8%">
						<col width="20%">
						<col width="8%">
						<col width="13%">
						<col width="8%">
						<col width="15%">
						<col width="6%">
						<col width="22%">
		            </colgroup>
					<tr>
						<th>
							판매자상품번호
						</th>
		                <td>
						   <input type="text" name="spdNo" id='spdNo'  maxlength="100" size="28"  style="vertical-align:middle;"/> 
		                </td>
						<th>리뷰유형코드</th>
						<td>
							<select name="rvTypCd" id="rvTypCd"	class="select" style="width: 70%;">
								<option value="">전체</option>
								<c:forEach items="${rvTypCd}" var="cd">
		 							<option value="${cd.EC_MINOR_CD}" >${cd.EC_CD_NM}</option>
		 						</c:forEach>
							</select> 
						</td>
						<th>리뷰구분코드</th>
						<td>
							<select name="rvDvsCd" id="rvDvsCd"	class="select" style="width: 70%;">
								<option value="">전체</option>
								<c:forEach items="${rvDvsCd}" var="cd">
		 							<option value="${cd.EC_MINOR_CD}" >${cd.EC_CD_NM}</option>
		 						</c:forEach>
							</select> 
						</td>			
						<th>등록일</th>
		                <td class="text" >
							<input type="text" id="startDate" name="startDate" style="width:31%;" readonly class="day" />
							<a href="javascript:void(0)" id="btnStartDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>~
							<input type="text" id="endDate" name="endDate" style="width:31%;" readonly class="day" />
							<a href="javascript:void(0)" id="btnEndDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>
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
	
	<form  name='reviewDetail' action='' method ="post" >
	    <input type='hidden' name='SspdNo' id='SspdNo'>
	    <input type='hidden' name='SrvTypCd' id='SrvTypCd' >
	    <input type='hidden' name='SrvDvsCd' id='SrvDvsCd' >
	    <input type='hidden' name='SrvCnts' id='SrvCnts' >
	    <input type='hidden' name='SpdStfdVal' id='SpdStfdVal' >
	    <input type='hidden' name='SmbNo' id='SmbNo' >
	    <input type='hidden' name='SrvRegDttm' id='SrvRegDttm' >
	    <input type='hidden' name='SrvCtntInfo' id='SrvCtntInfo' >
	    <input type='hidden' name='SrvCtntSeq' id='SrvCtntSeq' >
	    <input type='hidden' name='SrvCtntTypCd' id='SrvCtntTypCd' >
	    <input type='hidden' name='SrvCtntRteNm' id='SrvCtntRteNm' >
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
					<li class="last">롯데ON 상품평 관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>