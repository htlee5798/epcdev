<%--
- Author(s): projectBOS32
- Created Date: 2016. 06. 07
- Version : 1.0
- Description : 증정품관리 목록 조회 폼
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<!-- board/PSCMPRD0010 -->

<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){

		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "325px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"", 							Type:"CheckBox", 	SaveName:"SELECTED", 	 		Align:"Center", Width:25,  Sort:false}
		  , {Header:"순번", 		 				Type:"Int", 				SaveName:"NUM", 		 	  	Align:"Center", Width:40,  	Edit:0}
		  , {Header:"인터넷 상품코드",   	Type:"Text", 			SaveName:"PROD_CD",	 	 	Align:"Center", Width:95,	 	Edit:0}
		  , {Header:"상품명",					Type:"Text", 			SaveName:"PROD_NM", 	 	 	Align:"Left",   	 Width:250, 	Edit:0, Ellipsis:true}
		  , {Header:"증정품",					Type:"Text", 			SaveName:"PEST_DESC", 	 	Align:"Left",   	 Width:250, 	Edit:0, Ellipsis:true}
		  , {Header:"증정기간",				Type:"Text", 			SaveName:"PEST_DY",				Align:"Center", Width:150,	Edit:0}
		  , {Header:"유형",						Type:"Text", 			SaveName:"PEST_TYPE_NM", 	Align:"Center", Width:80,	Edit:0}
		  
		  , {Header:"점포코드",				Type:"Text", 			SaveName:"STR_CD", 				Hidden:true}
		  , {Header:"유형코드",				Type:"Text", 			SaveName:"PEST_TYPE", 			Hidden:true}
		  , {Header:"증정기간(시작)",		Type:"Text", 			SaveName:"PEST_START_DY",	Hidden:true}
		  , {Header:"증정기간(종료)",		Type:"Text", 			SaveName:"PEST_END_DY",		Hidden:true}
		];

		
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.FitColWidth();
		
	    $('#search').click(function() {
	        doSearch();
	    });
	    
	    $('#add').click(function() {
	    	doAdd();
	    });
	    
		$('#save').click(function() {
	    	doSave();
	    });
	}); // end of ready
	
	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() 
	{
		goPage('1');
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(currentPage){
		var url = '<c:url value="/product/selectGiftList.do"/>';
		
		loadIBSheetData(mySheet, url, currentPage, '#viewForm', null);
	}
	
    /** ********************************************************
	 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
	 ******************************************************** */
	function fnChkSpcCharByte(val)
	{
        var str = val;
        var len = 0;
        var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

        if (str.search(exp) != -1)
        {
            alert("검색항목에는 특수문자를 사용할수 없습니다!");
            return false;
        }

        return true;
	}
	
	/**********************************************************
	 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
	 ******************************************************** */
	function keyCode(e)
    {
        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
        
        if (code >  32 && code <  48) keyResult(e);
        if (code >  57 && code <  65) keyResult(e);
        if (code >  90 && code <  97) keyResult(e);
        if (code > 122 && code < 127) keyResult(e);
    }
    function keyResult(e)
    {
        alert("특수문자를 사용할수 없습니다!");
        
        if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
        }
        else {
            e.preventDefault(); //FF - Chrome both
        }
    }
    
    function setVendorInto(vendorId, vendorNm){
    	$("#vendorId").val(vendorId);
    	$("#vendorNm").val(vendorNm);
    }
    
  //상품정보 팝업
    function popupProductList(){
    	var targetUrl = '<c:url value="/common/viewPopupProductList.do"/>?prodFlag='+$('#prodFlag').val()+"&vendorId="+$("#vendorId").val();//01:상품
   			
   		Common.centerPopupWindow(targetUrl, 'prd', {width : 750, height : 500});
    }
  
    function popupReturn(arg){
    	$("#prodCd").val(arg);
    }
    
    function doAdd(){
    	var selFlag = false;
    	var pestDesc = $("#pestDesc").val();
    	var pestStartDy = $("#pestStartDy").val();
    	var pestEndDy = $("#pestEndDy").val();
    	var prestType = $("select[name=prestType] option:selected").val();
    	var prestTypeNm = $("select[name=prestType] option:selected").text();
    	
    	if(pestDesc == ""){
    		alert("증정품 내용을 입력하세요.");
    		$("#pestDesc").focus();
    		
    		return;
    	}
    	
    	if(mySheet.RowCount() == 0){
    		alert('조회된 데이터가 없습니다.');
    		return;
    	}
    	
    	for(var i=1; i<mySheet.RowCount()+1; i++){
    		if(mySheet.GetCellValue(i,"SELECTED") == 1){
    			selFlag = true;
    			
    			mySheet.SetCellValue(i,"PEST_DESC",pestDesc);
    			mySheet.SetCellValue(i,"PEST_DY",pestStartDy+" ~ "+pestEndDy);
    			mySheet.SetCellValue(i,"PEST_START_DY",pestStartDy.replace(/-/gi, '' ));
    			mySheet.SetCellValue(i,"PEST_END_DY",pestEndDy.replace(/-/gi, '' ));
    			mySheet.SetCellValue(i,"PEST_TYPE_NM",prestTypeNm);
    			mySheet.SetCellValue(i,"PEST_TYPE",prestType);
    		}
    	}
    	
    	if(!selFlag){
    		alert("적용할 상품을 선택 하세요.");
    	}
    }
    
    function doSave(){
    	var selFlag = false;
    	
    	if(mySheet.RowCount() == 0){
    		alert('조회된 데이터가 없습니다.');
    		return;
    	}
    	
    	for(var i=1; i<mySheet.RowCount()+1; i++){
    		if(mySheet.GetCellValue(i,"SELECTED") == 1){
    			selFlag = true;
    		}
    	}
    	
    	if(!selFlag){
    		alert("저장할 상품을 선택 하세요.");
    		return;
    	}
    	
    	
    	var udtUrl = '<c:url value="/product/updateBatchGift.do"/>';
    	mySheet.DoSave(udtUrl, {Col:0, Sync:2});
    }
    
    function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
    	alert(Msg);
    	goPage('1');
    }
    
    function mySheet_OnSmartResize(Width, Height) {
    	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
    	mySheet.FitColWidth();
    }
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="viewForm" id="viewForm">
<div id="wrap_menu">
    <div class="wrap_search">
        
        <div class="bbs_search">
            <ul class="tit">
                <li class="tit">조회조건</li>
                <li class="btn">
                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                </li>
            </ul>
            <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	            <colgroup>
	                <col width="15%">
	                <col width="35%">
	                <col width="15%">
	                <col width="35%">
	            </colgroup>
	            <tr>
	               <th>
	               		상품검색
	               </th>
	               <td>
	               		<select name="prodFlag" id="prodFlag" style="width:104px;">
							<option value="1">인터넷상품코드</option>
							<option value="2">상품코드</option>
							<option value="3">판매코드</option>
						</select>
	               		<input type="text" style="width:50%;vertical-align:middle;" id="prodCd" name="prodCd" >
	               		<a href="javascript:popupProductList();" ><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
	               </td>
	               <th>협력사</th>
	               <td>
		               <c:choose>
							<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
								<input type="text" name="vendorNm" id="vendorNm" readonly="readonly" readonly="readonly" value="" style="width:40%;"/>	
								<input type="hidden" name="vendorId" id="vendorId" />																	
								<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
							</c:when>
							<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
								<html:codeTag objId="vendorId" objName="vendorId" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
							</c:when>
						</c:choose>
	               </td>
	            </tr>
            </table>
        </div>
        
        <br/>
        
        <div class="bbs_search">
            <ul class="tit">
                <li class="tit">증정내용</li>
                <li class="btn">
                    <a href="#" class="btn" id="add"><span>적용</span></a>
                </li>
            </ul>
            <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	            <colgroup>
	                <col width="10%">
	                <col width="23%">
	                <col width="10%">
	                <col width="27%">
	                <col width="10%">
	                <col width="20%">
	            </colgroup>
	            <tr>
	               <th>
	               		증정품
	               </th>
	               <td>
	               		<input type="text"  id="pestDesc" name="pestDesc" style="width:95%;" onKeyPress="keyCode(event)">
	               </td>
	               <th>증정기간</th>
	               <td>
		           		<input type="text" name="pestStartDy" id="pestStartDy" readonly style="width:35%;" value="${startDate}" />&nbsp;<a href="javascript:openCal('viewForm.pestStartDy')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
						~
						<input type="text" name="pestEndDy" id="pestEndDy"  readonly style="width:35%;" value="${endDate}" />&nbsp;<a href="javascript:openCal('viewForm.pestEndDy')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
	               </td>
	               <th>
	               		유형
	               </th>
	               <td>
	               		<select name="prestType" id="prestType" >
							<option value="001">증정</option>
							<option value="002">1+1</option>
						</select>
	               </td>
	            </tr>
            </table>
        </div>
    </div>
    
    <!-- 2 검색내역  -->
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">조회결과</li>
                <li class="btn">
                    <a href="#" class="btn" id="save"><span>저장</span></a>
                </li>
            </ul>
 
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
 					<td><div id="ibsheet1"></div></td>
				</tr>
			</table>
		</div>
		<!-- 2검색내역 // -->

	</div>
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
					<li>온라인전용상품관리</li>
					<li class="last">증정품관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>