<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<%@ include file="/common/scm/scmCommon.jsp" %>

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<!-- PSCMPRD0040 -->



<script type="text/javascript" >
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
	
	// 조회버튼 클릭
	$('#search').click(function() {
		doSearch();	
	});
	
	// 등록버튼 클릭
	$('#create').click(function() {
		doCreate();	
	});
	
	// 수정버튼 클릭
	$('#edite').click(function() {
		doEdite();	
	});
	
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(true);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"",										Type:"CheckBox" ,			 SaveName:"chk",					Align:"Center", 				Width:50,  		Edit:1 }
	  , {Header:"순번",								Type:"Text" ,					SaveName:"num", 		    Align:"Center",					Width:50, 		Edit:0}
	  , {Header:"확인",								  Type:"Status" ,				SaveName:"S_STATUS", 		    Align:"Center",			Width:50, 		Edit:0 ,Hidden:true } 
	  , {Header:"셀링포인트 코드", 	    Type:"Text", 					SaveName:"mdTalkSeq",     		Align:"Center",			Width:100, 	Edit:0}
	  , {Header:"제목", 	        					Type:"Text", 					SaveName:"title",   			Align:"left", 	 				Width:200, 	Edit:0, FontColor:"#0000FF"}
	  , {Header:"상태", 								Type:"Combo", 				SaveName:"useYn", 			Align:"Center", 	 				Width:70, 	Edit:1, ComboText:"미사용|사용",ComboCode:"N|Y"}
	  , {Header:"공지시작일", 				Type:"Text", 					SaveName:"announStartDy",	Align:"Center",   		Width:80, 	Edit:0,Format:"Ymd" }
	  , {Header:"공지종료일", 				Type:"Text", 					SaveName:"announEndDy",     Align:"Center", 			Width:80, 	Edit:0,Format:"Ymd"}
	  , {Header:"작성자", 						Type:"Text", 					SaveName:"regId",  			Align:"Center",					Width:70, 	Edit:0}
	  , {Header:"작성일", 						Type:"Text", 					SaveName:"regDate",  		Align:"Center", 					Width:70, 	Edit:0,Format:"Ymd"}
	  , {Header:"수정자",     					Type:"Text", 					SaveName:"modId", 	    Align:"Center", 					Width:100, 	Edit:0}
	  , {Header:"수정일",        					Type:"Text", 					SaveName:"modDate", 	Align:"Center",    			 	Width:70, 	Edit:0,Format:"Ymd"}
	  , {Header:"승인일시",						Type:"Text" ,					SaveName:"apryDate", 	Align:"Center",					Width:70, 	Edit:0,Format:"Ymd"}
	  , {Header:"승인자",							Type:"Text" ,					SaveName:"apryId", 		    Align:"Center",					Width:100, 	Edit:0}
	  , {Header:"승인여부",						Type:"Text" ,					SaveName:"apryYn", 		Align:"Center",					Width:70, 	Edit:0}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetEllipsis(1);	//말줄임
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	
}); // end of ready

/** ********************************************************
 * 팝업창 할당
 ******************************************************** */
	function mySheet_OnDblClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if (Row < 1) return;
		
		var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
		if(colNm == 'title'){	//셀링포인트 할당
			var recommSeq	 = mySheet.GetCellValue(Row, 3);
			popupDetailView(recommSeq);
			
		}
	}

/** ********************************************************
 * 셀링포인트 할당 팝업창
 ******************************************************** */
	function popupDetailView(recommSeq) {
		var targetUrl = '<c:url value="/product/selectSelPointView.do"/>'+'?mdTalkSeq='+recommSeq;
		var wSize = 0;
		
		Common.centerPopupWindow(targetUrl, 'product', {width : 800, height : 620,scrollBars : 'YES'});
	}
	
	/** ********************************************************
	 * alert 창
	 ******************************************************** */
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		doSearch();	
	}

	 /** ********************************************************
	  *  팝업 셀링포인트 수정
	  ******************************************************** */
	function doEdite(){
				var url = '<c:url value="/product/updateSelPont.do"/>';
				mySheet.DoSave(url, "useYn=1");
	}
	 
 
 /** ********************************************************
  *  팝업 셀링포인트 등록
  ******************************************************** */
 
  function doCreate(){
		var targetUrl = '<c:url value="/product/selCreatePop.do"/>' ;
		var wSize = 0;
		
		Common.centerPopupWindow(targetUrl, 'product', {width : 800, height : 400,scrollBars : 'YES'});
	}
	
/** ********************************************************
 * 셀링포인트 목록 조회
 ******************************************************** */
function goPage(currentPage){
	
	var url = '<c:url value="/product/selectSelPoint.do"/>';
	
	loadIBSheetData(mySheet, url, currentPage, '#adminForm', null);
}

function doSearch() {
	var form = document.adminForm;
	//조회기간 체크
	 if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' )){
	     alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
	     return ;
	  }
	goPage('1');
}
</script>
</head>

<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="adminForm" id="adminForm">
<div id="wrap_menu">
							<!-- 등록자 이름-->
<input type="hidden" name="regId" id="regId" value="${epcLoginVO}">
	<!-- 조회조건 -->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" id="search"  ><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" id="create"  ><span><spring:message code="button.common.create"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:15%" />
							<col style="width:35%" />
							<col style="width:15%" />
							<col style="width:35%" />
						</colgroup>
						<tr>
							<th class="fst"><span class="star">*&nbsp</span>사용기간</th>
							<td class="text" colspan="3">
								<input type="text" id="startDate" name="startDate" style="width:15%;" readonly class="day" />
								<a href="#" id="btnStartDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>~
								<input type="text" id="endDate" name="endDate" style="width:15%;" readonly class="day" />
								<a href="#" id="btnEndDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>
							</td>
							</tr>
							<tr>
							<th>
								 </span>적용대상</th>
							 </th>
							 <td>
							 <select name="seachType" id="seachType"  class="select" style="width:30%; " >
								<option value="pdNm">상품명</option>
								<option value="pdCd">상품코드</option>
								<option value="cgNm">카테고리명</option>
								<option value="vdId">협력사명</option>
								</select>
								&nbsp
							 	<input type="text" name="searchNm" id="searchNm"  size="20"  >
							 </td>
							<th>사용여부</th>
		                    <td>
		                        <select name="useYn" id="useYn"  class="select"  style="width:40%;">
								<option value="">전체</option>
								<option value="Y">사용</option>
								<option value="N">미사용</option>
								</select>
		                    </td>
						<tr>
							<th class="fst">
								제목
							</th>
							
							<td >
								<input type="text" name="titleNm" id="titleNm"  size="30">
							</td>
							
							<th class="fst">
								협력사
							</th>
							<td>
								<select name="searchVendorId" id="searchVendorId" class="select"  readonly style="width:35%;">
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
			</div>
			<!-- 조회조건 // -->
		
			<!-- 조회결과 -->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code="text.common.title.searchResult"/></li>
						<li class="btn"> <a href="#" class="btn" id="edite" ><span><spring:message code="button.common.save" /></span></a> </li>
					</ul>
		
					<tablecellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
						</tr>
					</table>
				</div>
			</div>
			<!-- 조회결과 //-->
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
					<li>상품관리</li>
					<li class="last">셀링포인트관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
<!--	@ BODY WRAP  END  	// -->
</body>
</html>