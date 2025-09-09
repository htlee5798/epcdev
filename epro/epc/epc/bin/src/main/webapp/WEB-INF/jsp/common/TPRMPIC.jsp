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
	String tabNo = "14";
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PBOMPRD0039 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
$(document).ready(function(){

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "430px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
   	    {SaveName:"SELECTED"          , Header:"선택"		       , Type:"CheckBox",   Align:"Center", Width:35,  	Edit:1}
   	  , {SaveName:"MPIC_SEQ"          , Header:"동영상순번"        , Type:"Text", 		Align:"Center", Width:35,  	Edit:0}
   	  , {SaveName:"DURATION"          , Header:"재생길이"          , Type:"Text", 		Align:"Center", Width:35,  	Edit:0}
   	  , {SaveName:"VIDEO_HEIGHT"      , Header:"세로"              , Type:"Text", 		Align:"Center", Width:35,  	Edit:0}
   	  , {SaveName:"VIDEO_WIDTH"       , Header:"가로"              , Type:"Text", 		Align:"Center", Width:35,  	Edit:0}
   	  , {SaveName:"VIDEO_FRAMERATE"   , Header:"프레임 수"         , Type:"Text", 		Align:"Center", Width:35,  	Edit:0}
   	  , {SaveName:"USE_YN"            , Header:"사용여부"          , Type:"Combo", 		Align:"Center", Width:35,  	Edit:1,  ComboText:"Y|N",ComboCode:"Y|N"}
   	  , {SaveName:"REG_ID"            , Header:"등록자"            , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"REG_DATE"          , Header:"등록일시"          , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"MOD_ID"            , Header:"수정자"            , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"MOD_DATE"          , Header:"수정일시"          , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"PREV_VIEW"         , Header:"미리보기"          , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  DefaultValue:"미리보기"}
   	  , {SaveName:"PROD_CD"           , Header:"PROD_CD"           , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"FOLDER"            , Header:"FOLDER"            , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"PKG"               , Header:"PKG"               , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"CID"               , Header:"CID"               , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"MPIC_URL"          , Header:"플레이키"          , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	  , {SaveName:"ACCESS_KEY"        , Header:""                  , Type:"Text", 		Align:"Center", Width:35,  	Edit:0,  Hidden:true}
   	];
	
	
	IBS_InitSheet(mySheet, ibdata);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
	
	//input enter 막기
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
	});
	
	$("#search").click(function(){
		doSearch();
	});
	
	$("#save").click(function(){  
		var prodCd       = $('#mainProdCd').val();
		Common.centerPopupWindow('<c:url value="/product/insertTprMpicInfo.do"/>?prodCd='+prodCd, '동영상 등록', {width:555,height:383,scrollbars:'no'});
	});
	$("#delete").click(function(){
		fnDelete()
	});
	doSearch();
});

function fnDelete(){
	
	if( mySheet.CheckedRows("SELECTED") == 0){
		alert("선택된 동영상 정보가 없습니다.");
		return;
	}
	
	if( !confirm("수정을 하시겠습니까?")){
		return;
	}
	var prodCd       = $('#mainProdCd').val();
	var params = "prodCd="+prodCd;
// 	$.ajaxSettings.traditional = true;
	mySheet.DoSave( '<c:url value="/product/selectUpdateTprMpicInfo.do"/>', {Quest:0, Param: params, Col:0});	//Quest : IBSheet에서 확인메세지 출력여부

}

function mySheet_OnSaveEnd(code, msg) {
    if(code >= 0) {
        alert(msg);  // 저장 성공 메시지
    	if( "<c:out value="${mdTalkSeq}"/>" != "" ){
    		doSearch();
    	}else{
    		self.close();
    	}
    } else {
        alert(msg); // 저장 실패 메시지
    }
}

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}
function goPage(currentPage){
	var url = '<c:url value="/product/selectTprMpicList.do"/>';
	loadIBSheetData(mySheet, url, currentPage, '#dataForm', null);
}

function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if (Row < 1) return;
	
	var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
	
	if( colNm == "PREV_VIEW"){
		var MPIC_URL =  mySheet.GetCellValue(Row, "MPIC_URL") ;
		if( MPIC_URL == "" ){
			alert("배포가 완료되지 않은 컨텐츠 입니다.");
		}else{
			videoPrevView(MPIC_URL);
		}
	}else if( !(colNm == "SELECTED" || colNm == "USE_YN") ){
		var prodCd =  mySheet.GetCellValue(Row, "PROD_CD") ;
		var mpicSeq =  mySheet.GetCellValue(Row, "MPIC_SEQ") ;
		Common.centerPopupWindow('<c:url value="/product/updateTprMpicInfo.do"/>?prodCd='+prodCd+'&mpicSeq='+mpicSeq, '동영상 수정', {width:555,height:383,scrollbars:'no'});
	}
}
function videoPrevView(key){
	Common.centerPopupWindow('<c:url value="/product/tprMpicPrevView.do"/>?key='+key, '동영상 미리보기', {width:555,height:383,scrollbars:'no'});
}


</script>
</head>
<body>


	
<div id="wrap_menu" style="width:1000px;">
	<!-- 상품 상세보기 하단 탭 -->
	<c:set var="tabNo" value="<%=tabNo%>" />      
	<c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
		<c:param name="tabNo" value="${tabNo}" />
		<c:param name="prodCd" value="<%=prodCd%>" />
	</c:import>
	
<form name="dataForm" id="dataForm" method="post" >
<input type="hidden" id="mainProdCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>	
	 <!-- 2 검색내역  -->
    <div class="wrap_con">
    	<div class="bbs_list">
            <ul class="tit">
                <li class="tit">동영상정보</li>
                <li class="btn">
                    <a href="#" class="btn" id="save"><span>등록</span></a>
                    <a href="#" class="btn" id="delete"><span>수정</span></a>
                    <a href="#" class="btn" id="search"><span>조회</span></a>
                </li>
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
	    
</form>
<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>
</body>
</html>