<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lottemart.epc.common.util.SecureUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
	String prodLinkKindCd = SecureUtil.stripXSS(request.getParameter("prodLinkKindCd"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "253px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 		Type:"Text", 	SaveName:"num", 	  Align:"Center", Width:60,  Edit:0}
		  , {Header:"선택",		Type:"CheckBox", SaveName:"CHK", 	  Align:"Center", Width:40,  Edit:1}
		  , {Header:"상품코드",	Type:"Text", 	SaveName:"assoCd", 	  Align:"Center", Width:80,  Edit:0}
		  , {Header:"상품명",		  Type:"Text", 	SaveName:"assoNm", 	  Align:"Center", Width:60,  Edit:0, Hidden:1}
		  , {Header:"인터넷상품코드",  Type:"Text", 	SaveName:"prodCd", 	  Align:"Center", Width:80,  Edit:0}
		  , {Header:"이미지",		  Type:"Text", 	SaveName:"imgPath",   Align:"Center", Width:80,  Edit:0, Hidden:1}
		  , {Header:"상품상세설명",	Type:"Text", 	SaveName:"prodDesc",  Align:"Center", Width:80,  Edit:0, Hidden:1}
		  , {Header:"",			Type:"Text", 	SaveName:"existChk",  Align:"Center", Width:200, Edit:0, Hidden:1}
		  , {Header:"Status",	Type:"Status", 	SaveName:"Status", 	  Align:"Center", Width:0,   Edit:0, Hidden:1}
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.FitColWidth();
		mySheet.SetComboOpenMode(1);

		//input enter 막기
		$("*").keypress(function(e){
			if (e.keyCode == 13) return false;
		});
		$('#search').click(function() {
			doSearch();
		});
		$('#save').click(function() {
			doUpdate();
		});
		$('#close').click(function() {
			top.close();
		});
	});

	//셀 클릭 시 팝업 호출
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		// 상세정보 함수
		if( Col == 2 ){
			imgPath =  mySheet.GetCellValue(Row, "imgPath");
			$("#imgPath").html("<img src='"+imgPath+"'>");
			prodDesc =  mySheet.GetCellValue(Row, "prodDesc");
			$("#prodDesc").html(prodDesc);
		
		}
	}

	function mySheet_OnSearchEnd() {
		// 조회된 데이터가 승인완료인 경우는 체크 금지
		var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
			var existChk = mySheet.GetCellValue(i, "existChk");
			if ( existChk == "01" ){
				// 체크박스 비활성화
				mySheet.SetCellEditable(i, 'chk', false);
			}
		}
	}

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	//페이지 이동시
	function goPage(currentPage) {
		var url = "<c:url value='/product/selectProductRecommendCrossSearch.do'/>";
		loadIBSheetData(mySheet, url, currentPage, "#dataForm", null);
	}

	//리스트 수정 (승인처리)
	function doUpdate() {
		if (mySheet.CheckedRows("CHK") == 0) {
			alert("선택된 상품이 없습니다.");
			return;
		}

		if (!confirm("수정하시겠습니까?")) {
			return;
		}
		
		mySheet.DoSave( '<c:url value="/product/insertProdutRecommend.do"/>', {Quest:0, Param:"dispYn=Y&vendorId=<%=vendorId%>&prodLinkKindCd=<%=prodLinkKindCd%>" });	//Quest : IBSheet에서 확인메세지 출력여부
	}

	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
	}
</script>
</head>
<body>
<form name="dataForm" id="dataForm">
<input type="hidden" name="dispYn" id="dispYn" value="Y" />
<input type="hidden" name="prodCd" id="prodCd" value="<%=prodCd%>" />
<input type="hidden" name="vendorId" id="vendorId" value="<%=vendorId%>" />
<input type="hidden" name="prodLinkKindCd" id="prodLinkKindCd" value="<%=prodLinkKindCd%>" />
<div id="popup">
	<!--  @title  -->
	<div id="p_title1">
		<h1>추천상품</h1>
		<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	</div>
	<!--  @title  //-->
	<!--  @process  -->
	<div id="process1">
		<ul>
			<li>홈</li>
			<li>상품관리</li>
			<li class="last">인터넷상품관리</li>
		</ul>
	</div>
	<!--  @process  //-->
	<div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품조회</li>
				<li class="btn">
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
					<a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
				</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tr>
					<th><span class="star"></span >인터넷상품코드</th>
					<td>
						<input type="text" name="assoCd" id="assoCd" value="" style="width:70%;" />
					</td>
						<th><span class="star"></span >상품명</th>
					<td>
						<input type="text" name="assoNm" id="assoNm" value="" style="width:70%;" />
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품정보</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="30%">
					<col width="70%">
				</colgroup>
				<tr>
					<th><span class="star"></span ><div id="imgPath" name="imgPath"></div></th>
					<td>
						<textarea id="prodDesc" name="prodDesc" cols=85 rows=5></textarea>
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<!-- list -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품목록</li>
				<li class="btn">
					<a href="#" class="btn" id="save"><span><spring:message code="button.common.save"/></span></a>
				</li>
			</ul>

			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
				   <td><div id="ibsheet1"></div></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="pagingDiv" class="pagingbox2" style="width: 98%;">
		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
	</div>

</div>
<iframe id="filefrm" name="filefrm" src="" marginwidth="0" marginheight="0" frameborder="3" bordercolor="red" width="0" height="0" scrolling="yes"></iframe>
</form>
</body>
</html>