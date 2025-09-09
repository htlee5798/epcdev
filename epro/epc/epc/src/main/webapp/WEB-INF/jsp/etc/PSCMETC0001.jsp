<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<script type="text/javascript" for="WG1" event="Initialize()">
/** *****************************************************
 *  WiseGrid 초기화
 ****************************************************** */
setWiseGridProperty(WG1);
setHeader();
</script>

<script type="text/javascript" for="WG1" event="CellClick(strColumnKey, nRow)">
/** *****************************************************
 * cell click 이벤트 처리
 ****************************************************** */
if(strColumnKey == "minorCd" && GetCellValue('crud',nRow)==''){	// 신규버튼으로 생성된 row는 파업을 띄우지 않는다.
	popupDetailForm(nRow);
}
</script> 

<!--  	서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WG1" event="EndQuery()">
	GridEndQuery();
	
	/** ****************************************************
	 * Paging 처리
	 **************************************************** */
	var totalCnt = "0";
	var rowsPerPage = "0";
	var curPage = "0";
	try {
		totalCnt = WG1.getParam("totalCount");
		rowsPerPage = WG1.getParam("rowsPerPage");
		curPage = WG1.getParam("currentPage");
	} catch (e) {}
	
	setLMPaging(totalCnt,rowsPerPage ,curPage ,'goPage','pagingDiv');
</script>


<!--	 WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WG1" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
	GridChangeCell(strColumnKey, nRow);
</script>


<script type="text/javascript">
/* 서버와의 통신이 정상적으로 완료되면 발생한다. */
function GridEndQuery() {
	var GridObj = document.WG1;
	
	//서버에서 mode로 셋팅한 파라미터를 가져온다.
	var mode = GridObj.GetParam("mode");

	if(mode == "search") { 
		if(GridObj.GetStatus() != "true") 	{// 서버에서 전송한 상태코드를 가져온다.
			var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
			
			if(error_msg!=''){	// error	
				$('#resultMsg').text('msg.common.fail.request');
			}else{				// 0건
				$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
			}
			
		}else{
			$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
		}

	}else if(mode == "save"||mode == "delete") {
		if(GridObj.GetStatus() == "true") 	{// 서버에서 전송한 상태코드를 가져온다.
			//서버에서 saveData 셋팅한 파라미터를 가져온다.
			var saveData = GridObj.GetParam("saveData");
			alert(saveData);
			doSearch();
		} else	{
			var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
			alert(error_msg);
			
		}
	}	
}

/** ********************************************************
 * 그리드 헤더 처리 함수
 ********************************************************* */
function setHeader()	
{
 	var gridObj = document.WG1;
 	
 	gridObj.AddHeader("crud",   	"구분",         "t_text" ,      300,    50,     false);	// 저장시 등록, 수정을 구분하기위한 flag
 	gridObj.AddHeader("selected",   "선택",         "t_checkbox" ,  300,    50,     true);
    gridObj.AddHeader("majorCd", 	"코드그룹ID", 	"t_text", 		5, 		100, 	false);
    gridObj.AddHeader("minorCd", 	"MINOR코드", 	"t_text", 		5, 		100, 	false);
    gridObj.AddHeader("cdNm", 		"코드명",		"t_text", 		100, 	100, 	true);
    gridObj.AddHeader("cdDesc", 	"설명", 			"t_text", 		200,	300, 	true);
    gridObj.AddHeader("orderSeq", 	"정렬순서", 		"t_text", 		15,	80, 	true);
    gridObj.AddHeader("regDivnCd", 	"코드등록구분", 	"t_combo", 		5,	100, 	true);
    gridObj.AddHeader("useYn", 	     "사용여부", 	"t_combo", 		5,	70, 	true);
    
    gridObj.BoundHeader();


	//저장모드를 사용해 서버사이드와 통신한다. 	
	//gridObj.SetCRUDMode("crud", "생성", "수정", "삭제");
	
    // 히든컬럼
    gridObj.SetColHide("crud", true);	
	
    // 링크 디자인 적용
    gridObj.SetColCellFgColor('minorCd','0|0|255');
    gridObj.SetColCellFontULine("minorCd", "true");
    gridObj.SetColCellCursor('minorCd', 'hand');
    
	//t_combo 타입의 컬럼에 Combo List 를 추가한다. 
	gridObj.AddComboListValue("useYn", "사용", "Y"); 
	gridObj.AddComboListValue("useYn", "미사용", "N"); 

	gridObj.AddComboListValue("regDivnCd", "Online", "01"); 
	gridObj.AddComboListValue("regDivnCd", "MD시스템", "02");    
    
    // 셀 정렬 적용
    gridObj.SetColCellAlign('majorCd', 'center');
    gridObj.SetColCellAlign('minorCd', 'center');
    gridObj.SetColCellAlign('cdNm', 'left');
    gridObj.SetColCellAlign('cdDesc', 'left');
    gridObj.SetColCellAlign('orderSeq', 'center');
    gridObj.SetColCellAlign('useYn', 'center');

	// 헤더에 체크박스표시
	gridObj.SetColHDCheckBoxVisible("selected", true);
	
}


/* WiseGrid의 셀의 내용이 변경되었을때 발생한다. */
function GridChangeCell(strColumnKey, nRow) {
	var GridObj = document.WG1;

	if(strColumnKey != "selected"){
		GridObj.SetCellValue("selected", nRow, "1");
		if(GridObj.GetCellValue("crud", nRow)!="I"){		// 신규가 아닌경우만 업데이트
			GridObj.SetCellValue("crud", nRow, "U");	
		}
	}
}


/** ********************************************************
 * 신규버튼 클릭
 ******************************************************** */
function GridAddRow(){
	
	var gridObj = document.WG1;
	gridObj.AddRow();
	
	gridObj.SetCellValue('selected', gridObj.GetActiveRowIndex(), '1');	// 체크
	gridObj.SetCellValue('crud', gridObj.GetActiveRowIndex(), "I");		// 신규 I 셋팅
	gridObj.SetCellActivation('majorCd',  gridObj.GetActiveRowIndex(), 'edit');
	gridObj.SetCellActivation("minorCd", gridObj.GetActiveRowIndex(), "edit");
}

/** ********************************************************
 * 등록
 ******************************************************** */
function GridInsertRow(){
	
	var gridObj = document.WG1;

	//등록 가능 체크
	if(!checkInsertRows())
		return;	
	
	//필수 입력체크
	if(!condition())
		return;	
	
	gridObj.SetParam("mode", "insert");

	var url = '<c:url value="/etc/saveCodeMainList.do"/>';
	gridObj.DoQuery(url, 'selected');	

}


/** ********************************************************
 * 저장
 ******************************************************** */
function GridSaveRow(){
	
	var gridObj = document.WG1;
	
	//필수 입력체크
	if(!condition())
		return;	
	
	gridObj.SetParam("mode", "save");

	var url = '<c:url value="/etc/saveCodeMainList.do"/>';
	gridObj.DoQuery(url, 'selected');	

}


/** ********************************************************
 * 삭제
 ******************************************************** */
function GridDeleteRow(){
	
	var gridObj = document.WG1;
	
	if(!checkRows())
		return;
	
	gridObj.SetParam("mode", "delete");

	var url = '<c:url value="/etc/saveCodeMainList.do"/>';
	gridObj.DoQuery(url, 'selected');	

}


/**********************************************************
 * grid 필수 입력 체크
 ******************************************************** */
function condition()
{
	var GridObj = document.WG1;
	
	for(i = 0; i < GridObj.GetRowCount(); i++)
	{
		if(GridObj.GetCellValue("majorCd", i) == ''){
			alert('코드그룹ID를 입력하십시오.');
			return false;
		}
	
		if(GridObj.GetCellValue("minorCd", i) == ''){
			alert('MINOR코드를 입력하십시오.');
			return false;
		}
	}
	return true;
}


/**********************************************************
 * insert 로우 체크
 *********************************************************/
function checkInsertRows()
{
	var GridObj = document.WG1;
	
	for(i = 0; i < GridObj.GetRowCount(); i++)
	{
		if((GridObj.GetCellValue("selected", i) == 1)&&(GridObj.GetCellValue("crud", i) == 'I'))
			return true;	
	}
	
	alert("등록가능한 로우가 없습니다.");
	return false;
}


/**********************************************************
 * 선택 로우 체크
 *********************************************************/
function checkRows()
{
	var GridObj = document.WG1;
	
	for(i = 0; i < GridObj.GetRowCount(); i++)
	{
		if(GridObj.GetCellValue("selected", i) == 1)
			return true;	
	}
	
	alert("선택된 로우가 없습니다.");
	return false;
}


/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	$('#insertForm').click(function() {
		popupInsertForm();	
	});

	$('#search').click(function() {
		doSearch();
	});
	
}); // end of ready

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}


/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(currentPage){
	var form = document.searchForm;
	var gridObj = document.WG1;
	
	var url = '<c:url value="/etc/selectCodeMainList.do"/>';
	gridObj.setParam('currentPage', currentPage);
	gridObj.setParam('rowsPerPage', $("#rowsPerPage").val());
	gridObj.setParam('majorCd', form.major_cd.value);
	gridObj.setParam('minorCd', form.minor_cd.value);
	gridObj.setParam('cdNm', form.cd_nm.value);
	gridObj.setParam('mode', 'search');
	
	gridObj.DoQuery(url);
}


/** ********************************************************
 * 등록 팝업 함수
 ******************************************************** */
function popupInsertForm(){
	var targetUrl = '<c:url value="/etc/insertCodePopupForm.do"/>';
	Common.centerPopupWindow(targetUrl, 'insert', {width : 800, height : 180});
}

/** ********************************************************
 * 상세 팝업 함수
 ******************************************************** */
function popupDetailForm(nRow) {
	var gridObj = document.WG1;
	var targetUrl = '<c:url value="/etc/selectCodePopup.do"/>?majorCd=' + gridObj.getCellValue('majorCd', nRow) + '&minorCd=' + gridObj.getCellValue('minorCd', nRow);
	Common.centerPopupWindow(targetUrl, 'detail', {width : 800, height : 180});
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

	<div class="content_scroll">
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" >
		<div id="wrap_menu">

			<!--	@ 현재위치	// -->
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="javascript: GridDeleteRow();" class="btn"><span><spring:message code="button.common.delete"/></span></a>
							<a href="javascript: GridSaveRow();" class="btn"><span><spring:message code="button.common.save"/></span></a>
							<a href="#" class="btn" id="insertForm"><span>팝업신규</span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			  			<colgroup>
							<col style="width:10%" />
							<col style="width:20%" />
							<col style="width:10%" />
							<col style="width:20%" />
							<col style="width:10%" />
							<col style="width:20%" />
			  			</colgroup>
			  			<tr>
							<th>코드그룹ID</th>
							<td class="text">
								<input type="text" class="inputRead" name="major_cd" style="width:80%;" value="" />
							</td>
							<th>MINOR 코드</th>
							<td class="text">
								<input type="text" name="minor_cd" style="width:80%;" value="" />
							</td>
							<th>코드명</th>
							<td class="text">
								<input type="text" name="cd_nm" style="width:80%;" value="" />
							</td>
						</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
			<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">조회내역</li>
						<li class="btn">
							<a href="javascript: GridAddRow();" class="btn" ><span><spring:message code="button.common.create"/></span></a>
						</li>
					</ul>
 
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<tr>
 							<td><script type="text/javascript">initWiseGrid("WG1", "100%", "590");</script></td>
						</tr>
					</table>
				</div>
				<!-- 2검색내역 // -->
			</div>
			<!-- paging -->
 			<!-- 페이징 DIV -->
			<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
				<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
			</div>		
 
		</div>
		<!--	@ BODY WRAP  END  	// -->
	
		</form>
		
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice">최근공지게시판 제목만 노출</div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>시스템관리</li>
					<li class="last">관리자관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>	
</body>
</html>