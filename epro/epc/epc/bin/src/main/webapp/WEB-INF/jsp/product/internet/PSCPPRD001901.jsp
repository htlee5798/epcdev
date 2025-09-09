<%--
- Author(s): jib
- Created Date: 2011. 09. 7
- Version : 1.0
- Description : 추가속성 화면
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%@page import="lcn.module.common.util.DateUtil"%>
<% 
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String categoryId = SecureUtil.stripXSS((String)request.getAttribute("categoryId"));
	String vendorId    = SecureUtil.stripXSS((String)request.getParameter("vendorId"   ));
	
	String tabNo = "8";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PBOMPRD003101 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
		ibdata.Cols = [
			{SaveName:"rnum"	 	, Header:"순번"		 	, Type:"Int"	, Align:"Center", Width:40, 	Edit:0}
		  , {SaveName:"CHK"			, Header:""				,Type:"CheckBox", Align:"Center", Width:25, 	Edit:0}
		  , {SaveName:"categoryId"	, Header:"카테고리ID"	, Type:"Text"	, Align:"Center", Width:130,	Edit:0}
		  , {SaveName:"categoryNm" 	, Header:"카테고리명"	, Type:"Text"	, Align:"Center", Width:180, 	Edit:0}
		  , {SaveName:"colNm"		, Header:"항목명"		, Type:"Text"	, Align:"Center", Width:200,	Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
		  , {SaveName:"colVal"		, Header:"항목값"		, Type:"Text"	, Align:"Center", Width:215,	Edit:0}
		  , {SaveName:"useYn"		, Header:"사용여부"		, Type:"Text"	, Align:"Center", Width:80,	Edit:0}
		  , {SaveName:"condSearchYn", Header:"조건검색여부"	, Type:"Text"	, Align:"Center", Width:120,	Edit:0}
		  , {SaveName:"addColSeq" 	, Header:"항목순번"		, Type:"Text"	, Align:"Center", Width:10,	Hidden:true}
		  , {SaveName:"addColValSeq", Header:"항목값순번"	, Type:"Text"	, Align:"Center", Width:10,	Hidden:true}
		];
		
		IBS_InitSheet(mySheet, ibdata);
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		
		//input enter 막기
		$("*").keypress(function(e){
	        if(e.keyCode==13) return false;
		});
		
		doSearch();
	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	// 검색
	function goPage(currentPage){
		var url = '<c:url value="/product/selectProductAttributeSearch.do"/>';
		var param = new Object();
		param.prodCd = $('#prodCd').val();
		loadIBSheetData(mySheet, url, null, null, param);
	}
	
	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		// 조회된 데이터가 승인완료인 경우는 체크 금지
	 	var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
		    mySheet.SetCellEditable(i, 1, true); 
		}
	}
	
	// 셀 클릭 시...
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		
		if (Row < 1) return;

		if (mySheet.ColSaveName(Col) == 'colNm') {
			var prodCd = $('#prodCd').val();
			var condSearchYn = mySheet.GetCellValue(Row, 'condSearchYn');
			var categoryId 	 = mySheet.GetCellValue(Row, 'categoryId');
			var addColSeq 	 = mySheet.GetCellValue(Row, 'addColSeq');
			var addColValSeq = mySheet.GetCellValue(Row, 'addColValSeq');
			
			if (condSearchYn == 'N') {
				var targetUrl = '<c:url value="/product/prdAttributeCategoryInsertForm.do"/>?mode=update&prodCd='+prodCd+'&categoryId='+categoryId+'&addColSeq='+addColSeq+'&addColValSeq='+addColValSeq;
				Common.centerPopupWindow(targetUrl, 'prdAttributeCategory', {width : 800, height : 270});
			} else {
				alert("조건검색여부가 N이 아닌 경우에는 항목값을 수정할 수 없습니다.");
			}
		}
	}
	
	//등록 팝업
	function popupInsert(){

		if(Common.isEmpty($.trim($('#categoryId').val())) || $('#categoryId').val()=="null"){
			alert('상품카테고리가 등록되지 않은 상품은 추가 속성을 등록 하실 수 없습니다.');
			return;
		}
		
		var targetUrl = '<c:url value="/product/selectProductAttributeInsertForm.do"/>?prodCd=<%=prodCd%>&categoryId=<%=categoryId%>&vendorId=<%=vendorId%>';
			
		Common.centerPopupWindow(targetUrl, 'selectProductAttribute', {width : 800, height : 500});
	}

	//리스트 삭제
	function doDelete(){
		if(!checkRows())
			return;

		if( confirm("선택된 추가속성을 삭제 하시겠습니까?") ){
			var prodCd = $('#prodCd').val();
			var vendorId = "<%=vendorId%>";
		    var sUrl = '<c:url value="/product/selectProductAttributeListUpdate.do"/>';
		    
		    mySheet.DoSave(sUrl, {Param:'mode=delete&prodCd=' + prodCd +'&vendorId='+vendorId, Quest:false, Col:1, Sync:2});
		}
	}
	
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) doSearch();
	}
	
	function checkRows() {
		var rowCount = mySheet.RowCount() + 1;
		for (var i=0; i<rowCount; i++) {
			if (mySheet.GetCellValue(i, "CHK") == 1)
				return true;	
		}
		alert("선택된 추가속성이 없습니다.");
		return false;
	}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<form name="dataForm" id="dataForm">
<input type="hidden" name="prodCd" id="prodCd" value="<%=prodCd%>">
<input type="hidden" name="categoryId" id="categoryId" value="<%=categoryId%>">

<div id="content_wrap" style="width:990px;height:50px">

	<div id="wrap_menu" style="width:990px;">
		<!--	@ 검색조건	-->

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
					<li class="tit">추가속성&nbsp;&nbsp;
					</li>

					<li class="btn">
						<a href="javascript:doSearch();" class="btn" ><span>조회</span></a>
						<a href="javascript:popupInsert();" class="btn" ><span>등록</span></a>
						<a href="javascript:doDelete();" class="btn" ><span>삭제</span></a>
					</li>
				</ul>
	
				<table cellpadding="0" cellspacing="0" border="0" width="990px;">
					<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
			</div>
		</div>
	 	
	</div>
	<!-- footer -->
	
</div>	
</form>

<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>

</body>
</html>