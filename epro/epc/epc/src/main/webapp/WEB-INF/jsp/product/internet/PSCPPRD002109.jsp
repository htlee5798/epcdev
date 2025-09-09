<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
$(document).ready(function() {
	// 엔터키 막기
	$("*").keypress(function(e) {
        if(e.keyCode==13) return false;
	});

	initIBSheetGrid1();
	
	initIBSheetGrid2();
	
	goPage('${prodInfo.PROD_CD}','',mySheet);
	
	goPage('','${prodInfo.SEQ}',mySheet2);
});

// 상품검색어 이력 수정 전/후 조회
function goPage(prodCd, seq, arg) {
	var url = "<c:url value='/product/keywordChgDetailList.do'/>";
    
    var param = new Object();
    
    param.prodCd = prodCd;
    
    param.seq = seq;
    
    loadIBSheetData(arg, url, null, null, param);
}

// 상품검색어 이력 수정 전 그리드 초기화
function initIBSheetGrid1(){
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "400px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(false);
	
	var ibdata = {};
	
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"순번",				Type:"Text", 			SaveName:"NUM", 					Align:"Center",		Width:50, 		Edit:0}
	  , {Header:"검색어",				Type:"Text", 			SaveName:"SEARCH_KYWRD", 	Align:"Left",   		Width:450, 		Edit:0}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
}

// 상품검색어 이력 수정 후 그리드 초기화
function initIBSheetGrid2(){
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "400px");
	mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet2.SetDataAutoTrim(false);
	
	var ibdata = {};
	
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"순번",				Type:"Text", 			SaveName:"NUM", 					Align:"Center",		Width:50, 		Edit:0}
	  , {Header:"검색어",				Type:"Text", 			SaveName:"SEARCH_KYWRD", 	Align:"Left",   		Width:450, 		Edit:0}
	];
	
	IBS_InitSheet(mySheet2, ibdata);
		
	mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet2.SetHeaderRowHeight(Ibs.HeaderHeight);
}

// 현재창 닫기
function doClose(){
	top.close();
}		

// 재요청
function doAprv(){
	if (!confirm('재요청하시겠습니까?')) {
		return;
	}
	// 로딩바
	callAjaxByUpdate('#bosform', '<c:url value="/product/prodAprv.do"/>', '#bosform', 'POST');
}

function callAjaxByUpdate(form, url, target, Type) {
	var formQueryString = $('*', '#bosform').fieldSerialize();
	$.ajax({
		type: Type,
		url: url,
		data: formQueryString,
		success: function(json) {
			try {
				// 권한에러 메시지 처리 조건문 
				if(jQuery.trim(json) == "accessAlertFail") {
					alert('<spring:message code="msg.common.error.noAuth"/>');
				} else {
					if(jQuery.trim(json) == "fail") {
						alert("저장 실패하였습니다.");
					} else if(jQuery.trim(json) == "success") {
						alert("정상적으로 처리되었습니다.");
						top.opener.doSearch();
						top.close();
					} else {
						alert(jQuery.trim(json));
					}
				}
			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<form name="bosform" id="bosform" method="post">
		<div id="popup">
			<div id="p_title1">
				<span class="logo"><img width="136" height="25" src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/logo_scm.gif" alt="LOTTE MART" /></span>
			</div>
			<div id="process1">
				<ul>
					<li>홈</li>
					<li class="last">검색어</li>			
				</ul>
			</div>
			<div class="popup_contents" >
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">검색어 조회</li>
						<li class="btn">
							<c:if test="${prodInfo.APRV_CD == '002'}">
								<a href="javascript:doAprv();"  class="btn" ><span>재요청</span></a>
							</c:if>										
							<a href="javascript:doClose();" class="btn" ><span>닫기</span></a>
						</li>
					</ul>
					<table width="100%" cellspacing="1" cellpadding="1" bgcolor=dddddd border=0 align=center>
						<input type="hidden" name="seq" id="seq" value="${prodInfo.SEQ}"/>
						<input type="hidden" name="prodCd" id="prodCd" value="${prodInfo.PROD_CD}"/>
						<input type="hidden" name="typeCd" id="typeCd" value="${prodInfo.TYPE_CD}"/>
						<tr>
							<td>현재 검색어</td>
							<td>수정요청 검색어</td>
						</tr>
						<tr bgcolor=white>
							<td align=center valign=center >
								<div style="height:500px;width:500px;">
									<div id="ibsheet1"></div>
								</div>
							</td>
							<td align=center valign=center>
								<div style="height:500px;width:500px;">
									<div id="ibsheet2"></div>
								</div>
							</td>
						</tr>
					</table>
				</div>				
			</div>
		</div>
	</form>
</body>
</html>