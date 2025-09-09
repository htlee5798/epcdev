<%--
- Author(s): lth
- Created Date: 2016. 05. 02
- Version : 1.0
- Description : 대표상품코드관리

--%>
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
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<%@ include file="/common/scm/scmCommon.jsp" %>

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<!-- PSCMPRD0021 -->


<script type="text/javascript" >

$(document).ready(function(){
	//달력셋팅
	$("#startDate, #endDate").attr("readonly", "readonly");
	$('#btnStartDate, #startDate').click(function() {
		openCal('form1.startDate');
	});
	$('#btnEndDate, #endDate').click(function() {
		openCal('form1.endDate');
	});
	
	//input enter 막기
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
	});
	
	initInputData();

	//초기달력값 셋팅
	$("#endDate").val('${endDate}');
	$("#startDate").val('${startDate}');
	
	// 조회버튼 클릭
	$('#search').click(function() {
		doSearch();	
	});
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번",		       	Type:"Int" ,		SaveName:"NUM", 		          	Align:"Center", Width:40, Edit:0}
	  , {Header:"상품코드", 	    Type:"Text", 		SaveName:"PROD_CD", 	          	Align:"Center", Width:100, Edit:0}
	  , {Header:"상품명", 	       	Type:"Text", 		SaveName:"PROD_NM", 	    		Align:"Left", 	  Width:180, Edit:0}
	  , {Header:"적용시작일자", 	Type:"Text", 		SaveName:"APPLY_START_DY", 	Align:"Center", 	  Width:80, Edit:0}
	  , {Header:"적용끝일자", 		Type:"Text", 		SaveName:"APPLY_END_DY",       Align:"Center",     Width:80, Edit:0}
	  , {Header:"대표상품코드", 	Type:"Text", 		SaveName:"REP_PROD_CD",        Align:"Center", Width:100, Edit:0}
	  , {Header:"대표판매코드", 	Type:"Text", 		SaveName:"CAT_NM",  				Align:"Center", Width:100, Edit:0}
	  , {Header:"원가", 					Type:"Int", 			SaveName:"BUY_PRC",  				Align:"Right", Width:70, Edit:0}
	  , {Header:"매가",     				Type:"Int", 			SaveName:"SELL_PRC", 	          	Align:"Right", Width:70, Edit:0}
	  , {Header:"판매가",        		Type:"Int", 			SaveName:"CURR_SELL_PRC", 	    Align:"Right",     Width:70, Edit:0}
	  , {Header:"이익율", 	    		Type:"Text", 		SaveName:"PROFIT_RATE", 	      	Align:"Right", Width:70, Edit:0}
	  , {Header:"적용여부", 	       	Type:"Text", 		SaveName:"APPLY_YN",      			Align:"Center", Width:50, Edit:0}
	  , {Header:"등록일자", 	    	Type:"Text", 		SaveName:"REG_DATE", 	            Align:"Center", Width:80, Edit:0}
	  , {Header:"프로모션 개수", 	Type:"Text", 		SaveName:"PROMO_CNT", 			Align:"Center", Width:60, Edit:0, Hidden:true}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	//mySheet.FitColWidth();
	
	

}); // end of ready


/** ********************************************************
 * input 속성&이벤트 셋팅
 prodCd			인터넷상품코드
 prodNm			상품명
 ******************************************************** */
function initInputData() {
	
	$("[id='prodCd'],[id='prodNm']").keypress(function(){
		enterKey();
	});
}

/** ********************************************************
 * 엔터키 처리 함수
 ******************************************************** */
function enterKey() {
	if (event.keyCode == 13) {
		goPage('1');
	}
};


/** ********************************************************
 * 대표상품코드 목록 조회
 ******************************************************** */
function goPage(currentPage){
	
	var url = '<c:url value="/product/selectRepProdCdList.do"/>';
	var param = new Object();
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if ($("#vendorId").val() == "")
	    {
	        alert('업체선택은 필수입니다.');
	        $("#vendorId").focus();
	        return;
	    }
		</c:when>
	</c:choose>
	
	var startDate = $('#startDate').val().replace( /-/gi, '' );
 	var endDate  = $('#endDate').val().replace( /-/gi, '' );
 	
 	if(startDate > endDate){
 		alert('시작일자가 종료일자보다 클 수 없습니다.');
 		return;
 	}
	
	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.startDate = $('#startDate').val();
	param.endDate = $('#endDate').val();
	param.mode = 'search';
	
	param.prodCd =  $("#prodCd").val();
	param.prodNm = $("#prodNm").val();
	param.vendorId = $("#vendorId").val();
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
}

function doSearch() {
	goPage('1');
}

/**********************************************************
 * 재조회
 *********************************************************/
function refreshSearch() {
	doSearch();
}


function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}


//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd(cd,msg) {
	
	/**********************************************************
	 * 프로모션이 걸린 상품에 별색을 준다
	 *********************************************************/
	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		if( mySheet.ColSaveName(Col) == 'PROMO_CNT' > 0 ){
		mySheet.SetCellBackColor(i, "PROD_CD", '<spring:message code="config.common.grid.promotionColor"/>');
		mySheet.SetCellBackColor(i, "dispYn", '#f0fff0');
		mySheet.SetCellFontColor(i, "PROD_CD", '#1E90FF');
// 		mySheet.SetCellFontColor(i, "PROD_CD", '255|255|255');
		}
	}
}

function mySheet_OnResize(Width, Height) {
	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
	mySheet.FitColWidth();
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="form1" id="form1">
<div id="wrap_menu">
			<!-- 조회조건 -->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code="text.common.title.searchCondition"/></li>
						<li class="btn">
							<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:20%" />
							<col style="width:30%" />
							<col style="width:20%" />
							<col style="width:30%" />
						</colgroup>
						<tr>
							<th class="fst"><span class="star">*&nbsp</span>등록일자</th>
							<td class="text">
								<input type="text" id="startDate" name="startDate" style="width:31%;" readonly class="day" />
								<a href="#" id="btnStartDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>~
								<input type="text" id="endDate" name="endDate" style="width:31%;" readonly class="day" />
								<a href="#" id="btnEndDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>
							</td>
							<th class="fst">인터넷상품코드</th>
							<td>
								<input type="text" name="prodCd" id="prodCd" class="input" >
							</td>
						</tr>
						<tr>
							<th class="fst">상품명</th>
							<td>
								<input type="text" name="prodNm" id="prodNm" class="input" >
							</td>
							<th>협력업체코드</th>
		                    <td>
		                        <c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select">
											<option value="">전체</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
					                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
										</c:forEach>
										</select>
									</c:when>
					</c:choose>
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
					<li class="last">대표상품코드관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>