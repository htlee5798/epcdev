<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%
	String tabNo = "1";
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<!-- 그리드 헤더처리 -->
<script type="text/javascript">
	$(document).ready(function(){
		//input enter 막기
		$("*").keypress(function(e){
			if(e.keyCode==13) return false;
		});

		setHeader();

		//목록 자동 로딩
		goPage();
	});

	function setHeader() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "200px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번|순번", 			Type:"Seq",		SaveName:"num",			Align:"Center", 	Width:90,	Edit:0}
		  , {Header:"마트속성|단품코드", 		Type:"Text", 	SaveName:"itemCd",		Align:"Center", 	Width:90,	Edit:0}
		  , {Header:"마트속성|옵션", 		Type:"Text", 	SaveName:"attrsNm",		Align:"Center", 	Width:200,	Edit:0}
		  , {Header:"EC 속성|속성유형명",	Type:"Text", 	SaveName:"attrNm",		Align:"Center", 	Width:270,	Edit:0}
		  , {Header:"EC 속성|속성값",   	Type:"Text", 	SaveName:"attrValNm",	Align:"Center", 	Width:270,	Edit:0}
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	}

	function goPage() {
		var url = '<c:url value="/product/selectProductItem.do"/>';
		loadIBSheetData(mySheet, url, null, '#dataForm', null);
	}

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage();
	}
</script>
</head>
<body>
<form name="dataForm" id="dataForm">
<input type="hidden" id="strCd" name="strCd">
<input type="hidden" id="prodCd" name="prodCd" value="<c:out escapeXml='false'  value ='${param.prodCd}' />" />
<div id="content_wrap" style="width:990px; height:200px;">
	<div id="wrap_menu" style="width:990px;">
	<!--	@ 검색조건  -->
		<!-- 상품 상세보기 하단 탭 -->
		<c:set var="tabNo" value="<%=tabNo%>" />
		<c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="<%=prodCd%>" />
		</c:import>
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">단품정보</li>
				</ul>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" heigth="200px">
					<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
</form>
<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<c:out escapeXml='false'  value ='${param.prodCd}' />" />
<input type="hidden" id="vendorId" name="vendorId" value= "<c:out escapeXml='false'  value = '${param.vendorId}' />"/>
</form>
</body>
</html>