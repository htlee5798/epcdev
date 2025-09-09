<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@page import="lcn.module.common.util.DateUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />
<!-- system/PSCMSYS0004 -->

<script type="text/javascript">

	/**********************************************************
	 * jQeury 초기화
	 *********************************************************/
	$(document).ready(function(){

		initIBSheetGrid(); //그리드 초기화
		// 업체기준배송비조회
		selectVendorDeliAmtList();

	}); // end of ready

	/**********************************************************
	 * ibsheet 초기화
	 *********************************************************/
	function initIBSheetGrid(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet"), "mySheet", "100%", "180px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
		  	{Header:"순번", 		 		Type:"Int", 	SaveName:"num", 		  	Align:"Center", Width:30,  	Edit:0}
		  , {Header:"거래처ID",				Type:"Text", 	SaveName:"VENDOR_ID",	  	Align:"Center", Width:70, 	Edit:0,   Hidden:true}
		  , {Header:"적용시작일자",			Type:"Date", 	SaveName:"APPLY_START_DY",  Align:"Center", Width:90, 	Edit:0}
		  , {Header:"적용종료일자",			Type:"Date", 	SaveName:"APPLY_END_DY",	Align:"Center", Width:90, 	Edit:0}
		  , {Header:"기준최소금액",		 	Type:"Int", 	SaveName:"DELI_BASE_MIN_AMT",Align:"Right", Width:90,	Edit:0}
		  , {Header:"기준최대금액",		 	Type:"Int", 	SaveName:"DELI_BASE_MAX_AMT",Align:"Right", Width:90,	Edit:0}
		  , {Header:"배송비",		 		Type:"Int", 	SaveName:"DELIVERY_AMT",    Align:"Right",  Width:90,	Edit:0}
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.FitColWidth();

	}

	// 업체기준배송비조회
	function selectVendorDeliAmtList(){
		var url = '<c:url value="/system/selectVendorDeliAmtList.do"/>';
		var param = new Object();
		param.vendorId = "${vendorId}";
		param.deliDivnCd = "${deliDivnCd}";

		loadIBSheetData(mySheet, url, null, null, param);
	}

	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {

	}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<form name="adminForm" id="adminForm" >
<div class="popup">
	<!--  @title  -->
	<div id="p_title1">
		<h1>배송비변경이력</h1>
		<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	</div>
	<!--  @title  //-->
	<!--  @process  -->
	<div id="process1">
		<ul>
			<li>홈</li>
			<li>시스템관리</li>
			<li>업체정보관리</li>
			<li class="last">배송비변경이력</li>
		</ul>
	</div>
	<!--  @process  //-->
	<!-- list -->
    <div class="popup_contents">
		<div class="bbs_search3">
            <ul class="tit">
                <li class="tit">배송정책관리</li>
				<li class="btn">
                    <a href="#" class="btn" id="deliClose" onclick="top.close();"><span><spring:message code="button.common.close" /></span></a>
				</li>
            </ul>
            <table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
 					<td><div id="ibsheet"></div></td>
				</tr>
			</table>
		</div>
	</div>
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>