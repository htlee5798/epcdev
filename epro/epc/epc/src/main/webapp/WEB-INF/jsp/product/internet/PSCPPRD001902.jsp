<%--
- Author(s): jib
- Created Date: 2011. 09. 2
- Version : 1.0
- Description : 추가속성 추가 화면
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<% 
	String startDate = DateUtil.formatDate(DateUtil.getToday(),"-");
	String endDate = DateUtil.formatDate(DateUtil.getToday(),"-");
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String categoryId = SecureUtil.stripXSS(request.getParameter("categoryId"));//사용치 않음..
	String vendorId    =  SecureUtil.stripXSS((String)request.getParameter("vendorId"   ));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PBOMPRD003102 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
	var IS_CHANGED = false;

	$(document).ready(function(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "350px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
		ibdata.Cols = [
			{SaveName:"num"	 	, Header:"순번"		 	, Type:"Int"	, Align:"Center", Width:40, 	Edit:0}
		  , {SaveName:"CHK"			, Header:""				,Type:"CheckBox", Align:"Center", Width:25, 	Edit:0}
		  , {SaveName:"categoryId"	, Header:"카테고리ID"	, Type:"Text"	, Align:"Center", Width:130,	Edit:0}
		  , {SaveName:"categoryNm" 	, Header:"카테고리명"	, Type:"Text"	, Align:"Center", Width:130, 	Edit:0}
		  , {SaveName:"colNm"		, Header:"항목명"		, Type:"Text"	, Align:"Center", Width:110,	Edit:0, Cursor:'pointer', Color:'blue', FontUnderline:true}
		  , {SaveName:"colVal"		, Header:"항목값"		, Type:"Text"	, Align:"Center", Width:110,	Edit:0}
		  , {SaveName:"useYn"		, Header:"사용여부"		, Type:"Text"	, Align:"Center", Width:80,	Edit:0}
		  , {SaveName:"condSearchYn", Header:"조건검색여부"	, Type:"Text"	, Align:"Center", Width:110,	Edit:0}
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
		goPage('1');
	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	function goPage(currentPage) {
		var url = '<c:url value="/product/selectProductAttributeCategorySearch.do"/>';
		var param = new Object();
		param.mode	= 'search';
		param.prodCd = $('#prodCd').val();
		param.categoryId = $('#categoryId').val();
		loadIBSheetData(mySheet, url, null, null, param);
	}
	
	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		// 조회된 데이터가 승인완료인 경우는 체크 금지
	 	var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
		    mySheet.SetCellEditable(i, 1, 1); 
		}
	}
	
	//리스트 저장
	function doInsert(){
		if(!checkRows())
			return;

		if(!checkRowsValue())
			return;
		
		if( confirm("선택된 추가속성을 저장 하시겠습니까?") ){
		    var sUrl = '<c:url value="/product/selectProductAttributeListUpdate.do"/>';
		    mySheet.DoSave(sUrl, {Param:'mode=insert&vendorId=<%=vendorId%>&prodCd=' + $('#prodCd').val(), Quest:false, Col:1, Sync:2});
		}
	}
	
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) {
			doSearch();
			IS_CHANGED = true;
		}
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

	// 저장전에 값 체크
	function checkRowsValue() {
		
		var RowsValue = new Array(100);
		for (var k=0; k<100; k++) {
			RowsValue[k] = 0;
		}

		var rowCount = mySheet.RowCount() + 1;
		for (var i=0; i<rowCount; i++) {
			if (mySheet.GetCellValue(i, "CHK") == 1) {
				var tmp = mySheet.GetCellValue(i, "addColSeq");
				RowsValue[tmp] += 1;
				if (RowsValue[tmp] > 1) {
					alert("항목간에 중복 선택된 항목이 존재합니다. 항목명당 하나의 항목값만 선택하세요.");
					return false;
				}
			}
		}
		return true;
	}
	
	// 현재창 닫기
	function doClose(){
		top.close();
		if (IS_CHANGED) opener.doSearch();
	}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="dataForm" id="dataForm">

<input type="hidden" name="prodCd" id="prodCd" value="<%=prodCd%>">
<input type="hidden" name="categoryId" id="categoryId" value="<%=categoryId%>">


<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>추가속성</h1>
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


			<!-- list -->
			<div class="bbs_search3">
				<ul class="tit">
					<li class="tit">추가속성목록</li>
					<li class="btn">
						<a href="javascript:doInsert();" class="btn" ><span>저장</span></a>
						<a href="javascript:doClose();" class="btn" ><span>닫기</span></a>
					</li>
				</ul>
	
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
			</div>

	 </div>	
	<!-- /div-->
	
</div>	
</form>

</body>
</html>