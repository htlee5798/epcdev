<%-- re
- Author(s): ksjeong
- Created Date: 2011. 09. 28
- Version : 1.0
- Description : 협력사 게시판(팝업 - 작성자리스트)
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- <%@ taglib prefix="ajax" uri="http://ajaxtags.sourceforge.net/tags/ajaxtags" %> --%>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<!-- PBOMBRD001405 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
	<c:import url="/common/commonHead.do" />
	<!-- product/PSCMPRD004002 -->

<script language="javascript" type="text/javascript">
 
	$(document).ready(function() {
	
		var pageheightoffset = 250;
		// IBSheet 설정 시작
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "315px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = { SearchMode:smLazyLoad, MergeSheet:msHeaderOnly}; 
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1,  HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{SaveName:"NUM",          				Header:"순번",             	Type:"Int",           	Align:"Center",  	Width:40, 		Edit:0}
      , {SaveName:"ADMIN_NM", 				Header:"성명",             	Type:"Text",   		Align:"Center",   Width:80,  		Cursor:'pointer', Color:'blue', FontUnderline:true} 
	  , {SaveName:"ADMIN_TYPE_NAME",  	Header:"관리자유형",   	Type:"Text",          	Align:"Center",   Width:200,  	Edit:0} 
	  , {SaveName:"STR_NAME",           		Header:"소속점포명",   	Type:"Text",         	Align:"Center",   Width:200, 	Edit:0}
	  , {SaveName:"ADMIN_ID",   	 			Header:"",       				Type:"Text",       	Align:"Center",   Width:0,			Edit:0, Hidden:true}
	  , {SaveName:"LOGIN_ID",  				Header:"",                    	Type:"Text",        	Align:"Center",   Width:0,	 		Edit:0, Hidden:true}
	];
	
	IBS_InitSheet(mySheet, ibdata);
	
	mySheet.FitColWidth();
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	
	//팝업 높이 설정
	$(".pop_box_01").css("height", 645);
	
	//input enter 막기
	$("*").keypress(function(e) {
        if (e.keyCode==13) return false;
	});
});

//조회 버튼 클릭시
function doSearch() {
	goPage('1');
}

// 템플릿 조회
function goPage(currentPage) {
	var url = '<c:url value="/exhibition/adminSearch.do"/>';
	
	var param = new Object();
	param.rowsPerPage 		= $("#rowsPerPage").val();
	param.adminTypeCd 		= $('#adminTypeCd').val();
	param.adminName		= $('#adminName').val();
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
}

//셀 클릭시 발생하는 이벤트(게시물 제목을 클릭하면 게시물 상세조회 팝업창이 열린다.)
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

	//특정 열을 클릭했을 때 다른 페이지로 이동하도록 처리 (상세팝업)
	if( Row > 0 &&  mySheet.ColSaveName(Col) == "ADMIN_NM"){	//제목을 클릭했을 때(Row가 0보다 큰 조건은 Header를 클릭했을 때는 상세팝업으로 이동하지 않도록 하기 위함이다.)
		$('#utakMd',opener.document).val(mySheet.GetCellValue(Row, "ADMIN_ID"));
		$('#utakMdNm',opener.document).val(mySheet.GetCellValue(Row, "ADMIN_NM"));
		self.close();
	}
}

$(window).resize( function() {
	//$("#ibsheet1").css("width", $(window).width()-28);
	$("#ibsheet1").css("width", $(window).width()-47);  //sheet 크기 변경
	mySheet.FitColWidth();
});
</script>
</head>

<body>
<form name="dataForm" id="dataForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="content" id="content">
<%-- <input type="hidden" name="boardSeq" id="boardSeq" value="<%=request.getParameter("boardSeq")%>"> --%>
 <div id="popup">
			<!--  @title  -->
			<div id="p_title1">
				<h1>관리자검색</h1>
				<span class="logo"><img
					src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif"
					alt="LOTTE MART" /></span>
			</div>
			<!--  @title  //-->
			<!--  @process  -->
			<div id="process1">
				<ul>
					<li>홈</li>
					<li>시스템관리</li>
					<li>관리자</li>
					<li class="last">관리자검색</li>
				</ul>
			</div>
		</div>
 		
 		<div class="popup_contents">

			<div class="bbs_search3">
				<ul class="tit">	
					<li class="tit">조회조건</li>
					<li class="btn">
						<a href="#" class="btn" onclick="doSearch();"><span>조회</span></a>
					</li>
				</ul>

				<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
					<col width="20%">
					<col width="30%">
					<col width="20%">
					<col width="30%">
					</colgroup>
				<tr>
					<th scope="col">관리자유형</th>
					<td>
						<select name="adminTypeCd" id="adminTypeCd" style="width:95%"  title="관리자유형">	
	 						<option value="">전체</option>
							<c:forEach items="${codeList}" var="code" begin="0">
								<option value="${code.MINOR_CD}" <c:if test="${adminTypeCd eq code.MINOR_CD}">selected</c:if>> ${code.CD_NM }</option>
							</c:forEach>
						</select>
						
					</td>
					<th scope="col">성명</th>
					<td><input type="text" name="adminName" id="adminName" style="width:95%;"  onKeyPress= "if(event.keyCode == 13){doSearch();}" title="성명"/></td>
				</tr>
			</table>
   		</div>
		<br/>
		
		<div class="bbs_search3">

			<ul class="tit">
				<li class="tit">조회결과</li>
				<!-- 건색 조건 -->
        	</ul>
        	<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
        	<%-- 건수와 정렬개수를 세팅하기 위해 li의 ID는 페이징 div 부분의 ID에 Cnt를 붙여서 부여한다. --%>
           	<li class="lp" id="pagingDivCnt">
           	</li>
                	
            	<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="조회결과">
                	<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
			</table>
		</div>        
        <div id="pagingDiv" class="pagingbox1" style="width: 100%;">
     		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script> <!-- 페이징 -->	
     	</div>
   	</div>
</form>  
<!-- <iframe name="_if_save" src="/html/bos/blank.html" style="display:none;"></iframe> -->
</body>
</html>