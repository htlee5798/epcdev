<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- PSCMORD0010 -->

<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){
	
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(true);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 		 		Type:"Text", 	SaveName:"num", 						Align:"Center",	Width:50, 		 	Edit:0}
		  , {Header:"대분류",		 	Type:"Text", 	SaveName:"l1Nm",	  				Align:"left",	Width:100,		 	Edit:0}
		  , {Header:"중분류",		 	Type:"Text", 	SaveName:"l2Nm",	  				Align:"left",	Width:100,		 	Edit:0}
		  , {Header:"상품코드",		 		Type:"Text", 	SaveName:"prodCd",	  				Align:"Center", 	Width:100,			Edit:0}
		  , {Header:"상품명",		 	Type:"Text", 	SaveName:"prodNm",	 	  		Align:"left", 	Width:150,			Edit:0}
		  , {Header:"ITEM_CD",		 			Type:"Text", 	SaveName:"itemCd", 							Align:"Right", 	Width:80,			Edit:0}  
		  , {Header:"이익률",			Type:"Int", 	SaveName:"profitRate",	 	  		Align:"Right", 	Width:80,			Edit:0}	  
		  , {Header:"원가",		 	Type:"Int", 	SaveName:"buyPrc",	 	  				Align:"Right", 	Width:80,			Edit:0}
		  , {Header:"판매가",				Type:"Int", 	SaveName:"currSellPrc", 	  					Align:"Right",	Width:80,			Edit:0}
		  , {Header:"총판매수량",		 	Type:"Int", 	SaveName:"ordQty",	 	  			Align:"Right",	Width:80,	 		Edit:0}
		  , {Header:"총 판매 금액",		 	Type:"Int", 	SaveName:"ordAmt",	 	  			Align:"Right",	Width:80,	 		Edit:0}
		 ];
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetEllipsis(1);	//말줄임
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	
	}); // end of ready
	
	/**********************************************************
	 * 엑셀 팝업링크
	 ******************************************************** */
	function doExcel() {
		var dateObj = new Date();
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth()+1;
		var day = dateObj.getDate();
		var today = year + "-" + month + "-" + day;
		var xlsUrl = '<c:url value="/order/exportPSCMORD0010Excel.do"/>';
		var hideCols = 'chk';
		
		directExcelDown(mySheet, '카테고리판매순위엑셀_'+today, xlsUrl, '#adminForm', null, hideCols); // 전체 다운로드 
	}	
	
	/**********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch(){ 
		goPage('1');
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(currentPage){
		var url = '<c:url value="/order/searchCSRList.do"/>';
		
		loadIBSheetData(mySheet, url, currentPage, '#adminForm', null);
	}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<!-- div class="content_scroll"-->
<div>
<form name="adminForm" id="adminForm">
<div id="wrap_menu">
    <div class="wrap_search">
        <!-- 01 : search -->
        <div class="bbs_search">
            <ul class="tit">
                <li class="tit">조회조건</li>
                <li class="btn">
                    <a href="javascript:doSearch();" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
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
               <th>조회기간</th>
                <td >
                	<select name="year" id="year" class="select"  readonly style="width:25%;">
                	<script>
                					
                					for(i =0 ; i<=12; i++){
                						var d = new Date();
										var data = d.getFullYear()-i;
										if('${nowYear}'== data ){
											document.write("<option value="+data+" selected >"+data+"년"+"</option>");
										}else {
											document.write("<option value="+data+">"+data+"년"+"</option>");
										}
									}
							</script>
                	
                	</select>
                	
                	<select name="month" id="month" class="select"  readonly style="width:20%;">
                	<script>
									for(i = 1;i<13;i++){
										var data = i;
										if(data < 10){
											data = "0"+i;
										}
										if('${nowMonth}'==i){
											document.write("<option value="+data+" selected >"+data+"월"+"</option>");
										}else {
										document.write("<option value="+data+">"+data+"월"+"</option>");
										}
									}
							</script>
                	</select>
                 </td> 
			<th>협력사</th>
         	<td>
					<select name="searchVendorId" id="searchVendorId" class="select"  readonly style="width:40%;">
						<option value="">전체</option>
					<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
					
						<c:if test="${not empty searchVO.searchVendorId}">
							<option value="${venArr}" <c:if test="${venArr eq searchVO.searchVendorId}">selected</c:if>>${venArr}</option>
						</c:if>	
						<c:if test="${empty searchVO.searchVendorId}">
							<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
						</c:if>
					
                        
					</c:forEach>
					</select>
	            </td>
            </tr>
          </table>
            
        </div>
        <!-- 1검색조건 // -->
    </div>
    
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">조회결과</li>
                <li class="btn">
                    <a href="javascript:doExcel();" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
                </li>
            </ul>
 
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
 					<td><div id="ibsheet1"></div></td>
				</tr>
			</table>
		</div>

	</div>
	<!-- 페이징 삭제 -->
	<!-- 페이징 DIV  -->
<!-- 	<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
	</div>		 -->
 
</div>
</form>
<br></br>


</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>주문관리</li>
					<li>매출정보</li>
					<li class="last">카테고리별</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>