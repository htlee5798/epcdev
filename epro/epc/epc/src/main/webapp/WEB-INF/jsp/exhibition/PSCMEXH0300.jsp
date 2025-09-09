<%--
- Author(s): jdj
- Created Date: 2016.04.11
- Version : 1.0
- Description : 기획전관리   메인화면 for IBSheet
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%-- 버튼권한처리 태그 라이브러리 제거 --%>
<%@page import="java.util.Map"%>
<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lcnjf.util.StringUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    String toDate = DateUtil.formatDate(DateUtil.getToday(),"-");

    String BEFORE_DATE_7DAY  =  DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday(),-7 ) ,"-");
    String BEFORE_DATE_15DAY =  DateUtil.formatDate(DateUtil.addDay(DateUtil.getToday(),-15) ,"-");
    
    //1개월, 3개월
    String BEFORE_DATE_1MONTH =  DateUtil.formatDate(DateUtil.addMonth(DateUtil.getToday(),-1) ,"-");    
    String BEFORE_DATE_3MONTH =  DateUtil.formatDate(DateUtil.addMonth(DateUtil.getToday(),-3) ,"-");
    
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />

<%@ include file="/common/scm/scmCommon.jsp" %>

<script language="javascript" type="text/javascript">
 
$(document).ready(function() {

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "480px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	ibdata.Cols = [
        {Header:"상태",               	Type:"Status",        SaveName:"STATUS",                     	 	Align:"Center", Width:30,  Edit:0, Hidden:true}
	  , {Header:"", 			 	  		Type:"CheckBox",	SaveName:"CHK", 								Align:"Center", Width:25, Hidden:true} // default=1(수정가능)
	  , {Header:"순번", 		 	  		Type:"Int", 		  		SaveName:"NUM", 				 			Align:"Center", Width:45,  Edit:0}
	  , {Header:"점포", 					Type:"Text", 		  	SaveName:"STR_NM", 				 		Align:"Center", Width:80,  Edit:0}
	  , {Header:"기획전번호", 		Type:"Text", 		  	SaveName:"MKDP_SEQ", 				 	Align:"Center", Width:90,  Edit:0, FontColor:"#0000FF", Cursor:"Pointer"}
       
	  , {Header:"기획전명", 	 	  	Type:"Html", 			SaveName:"MKDP_NM", 	 			    Align:"Left"  , Width:200, Edit:0, FontColor:"#0000FF", Cursor:"Pointer"}	  	  
// 	  , {Header:"승인상태",           	Type:"Text", 			SaveName:"APRV_STS_NM",              	Align:"Center", Width:70,Edit:0}	  
	  , {Header:"전시상태",           	Type:"Text", 			SaveName:"DISP_NM",         	        	Align:"Center", Width:70,Edit:0}
	  , {Header:"전시카테고리",		Type:"Text", 			SaveName:"CATEGORY_NM",         	    Align:"Left", Width:200,Edit:0}
	  , {Header:"시작일", 	 		  	Type:"Text", 			SaveName:"START_DATE", 					Align:"Center", Width:80, Edit:0}
	  , {Header:"종료일", 	 	 	  	Type:"Text", 			SaveName:"END_DATE", 					Align:"Center", Width:80, Edit:0}	  
	  , {Header:"등록일", 	 	 	  	Type:"Text", 			SaveName:"REG_DATE", 						Align:"Center", Width:80, Edit:0}
// 	  , {Header:"승인요청일",  		Type:"Text", 			SaveName:"REQ_APRV_DATE",				Align:"Center"  , Width:80, Edit:0}
	  
	  , {Header:"카테고리ID",         Type:"Text", 			SaveName:"CATEGORY_ID",      	        Hidden:true }	  
// 	  , {Header:"대카테고리ID",       Type:"Text", 			SaveName:"CATEGORY_PARENT_ID",          Hidden:true }
// 	  , {Header:"소카테고리ID",       Type:"Text", 			SaveName:"CATEGORY_CHILD_ID",           Hidden:true }	  
	  , {Header:"승인여부",           	Type:"Text", 			SaveName:"APRV_STS_CD",                 Hidden:true }
	  , {Header:"START_DATE_POP",    	Type:"Text", 			SaveName:"START_DATE_POP",              Hidden:true }
	  , {Header:"END_DATE_POP",        	Type:"Text", 			SaveName:"END_DATE_POP",                 Hidden:true }
	  , {Header:"strCd",           		Type:"Text", 			SaveName:"STR_CD",                 			Hidden:true }
	  , {Header:"전시유형",           	Type:"Text", 			SaveName:"DISP_TYPE_CD",                 	Hidden:true }
	  , {Header:"vendorId",           	Type:"Text", 			SaveName:"VENDOR_ID",                 	Hidden:true }
	  , {Header:"CATEGORY_PARENT_ID",           	Type:"Text", 			SaveName:"CATEGORY_PARENT_ID",                 	Hidden:true }
	  , {Header:"CATEGORY_CHILD_ID",           	Type:"Text", 			SaveName:"CATEGORY_CHILD_ID",                 	Hidden:true }
	];

	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
	
	//*------------------------------------------------------------
	//* 조회 버튼
	//*------------------------------------------------------------
	$("#searchDiffDate").change(function() {
		//console.log('searchDiffDate change');		
		$("#endDate").val("<%=toDate%>");
		
		var diffDate = this.value;
		if(diffDate=='1'){
			$("#startDate").val("<%=BEFORE_DATE_7DAY%>");
			
		}else if(diffDate=='2'){
			$("#startDate").val("<%=BEFORE_DATE_15DAY%>");
			
		}else if(diffDate=='3'){
			$("#startDate").val("<%=BEFORE_DATE_1MONTH%>");
			
		}else if(diffDate=='4'){
			$("#startDate").val("<%=BEFORE_DATE_3MONTH%>");
			
		}
	});

	//디폴트 셋팅
	$("#startDate").val("<%=BEFORE_DATE_7DAY%>");
	$("#endDate").val("<%=toDate%>");

    $('#searchEndDate7Day').click(function() {
        if ($(this).is(':checked')) {
        	$("#searchTargetDiff").val("2");
    		$("#endDate").val("<%=toDate%>");
    		$("#startDate").val("<%=BEFORE_DATE_7DAY%>");
    		
    		$("#searchDiffDate").val("1");
        }
    });
	
});

// //셀 변경시 표시 
// function mySheet_OnChange(Row, Col, Value, OldValue) {
// 	if (Row == 0) return;
// 	//if (Col != 14) return;
	
// 	var chk = mySheet.GetCellProperty(Row, Col, "SaveName"); // SaveName	
// 	//alert(chk);
	
// 	/* if(chk == "CHK") {
// 		if(Value == "1") {
// 			//승인, 반려건인 선택할수 없다.
// 			var aprvStsCd = mySheet.GetCellValue(Row,'APRV_STS_CD');
			
// 			if(aprvStsCd=='03' || aprvStsCd=='02'){
// 				alert("승인이나 반려건은 선택할수 없습니다.");
// 				mySheet.SetCellValue(Row, 'CHK', false);
// 				return false;
// 			}
// 		}
// 	} */

// }

function doSearch() {
	goPage('1');
}

// 기획전 조회
function goPage(currentPage) {
	
	var url = '<c:url value="/exhibition/exhibitionIntSelectSearch.do"/>';
	var param = new Object();
	
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
		
	param.mkdpGubn = $('#mkdpGubn').val();
	param.mkdpSeq = $('#mkdpSeq').val();
	param.mkdpDate = $('#mkdpDate').val();
	param.dispYn = $('#dispYn').val();
	param.mkdpNm = $('#mkdpNm').val();
	
	param.vendorId = $('#vendorId').val();
	param.aprvStsCd = $('#aprvStsCd').val();
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
	
// 	//var url = '<c:url value="/newTemplate/selectTemplateList.do"/>';
// 	var url = '<c:url value="/exhibition/exhibitionSelect.do"/>';
// 	loadIBSheetData(mySheet, url, currentPage, '#dataForm', null);
}


//  셀 클릭시 팝업 호출 
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if (Row < 1) return;
	
	var categoryId = mySheet.GetCellValue(Row, 'CATEGORY_ID');
		var mkdpSeq = mySheet.GetCellValue(Row, 'MKDP_SEQ');
		var mkdpNm = mySheet.GetCellValue(Row, 'MKDP_NM');
		var vendorId = mySheet.GetCellValue(Row, 'VENDOR_ID');
		var startDatePop = mySheet.GetCellValue(Row, 'START_DATE_POP');
		var endDatePop = mySheet.GetCellValue(Row, 'END_DATE_POP');

		var categoryParentId = mySheet.GetCellValue(Row, 'CATEGORY_PARENT_ID');
		var categoryChildId = mySheet.GetCellValue(Row, 'CATEGORY_CHILD_ID');

		//전시유형
		var dispTypeCd = mySheet.GetCellValue(Row, 'DISP_TYPE_CD');

		//기획전 미리보기
		if (mySheet.ColSaveName(Col) == 'MKDP_SEQ') {

			//모바일 일경우 "모바일 미리보기는 지원하지 않습니다."
			if (dispTypeCd == "00003" || dispTypeCd == "00007") {
				alert("모바일 미리보기는 지원하지 않습니다.");
				return;
			}

			window
					.open(
							"http://www.lottemart.com/plan/planDetail.do?CategoryID="
									+ categoryChildId + "&MkdpSeq=" + mkdpSeq
									+ "&previewYN=Y", "preView",
							"width=1100,height=700,toolbar=no,scrollbars=yes, resizeable=yes");
			return;
		}

		if (mySheet.ColSaveName(Col) == 'MKDP_NM') {
			popupDetailView(categoryId, mkdpSeq, mkdpNm, startDatePop, endDatePop, vendorId);
		}
	
}

function popupDetailView(categoryId, mkdpSeq, mkdpNm, startDatePop, endDatePop, vendorId) {
	var nm = mkdpNm;
	var targetUrl = '<c:url value="/exhibition/selectExhibitionProduct.do"/>'
				+ '?mdiCategoryId='
				+ categoryId
				+ '&mdiMkdpSeq='
				+ mkdpSeq
				+ '&mdiMkdpNm='
				+ nm
				+ '&startDatePop='
				+ startDatePop
				+ '&endDatePop=' + endDatePop + '&mdiVendorId=' + vendorId;
		// 	var url = '<c:url value="/exhibition/basicExhibition.do"/>'+'?gubun=U&mdiCategoryId='+categoryId+'&mdiMkdpSeq='+mkdpSeq+'&mdiVendorId='+vendorId;
		var wSize = 0;

		Common.centerPopupWindow(targetUrl, 'exhibitionProduct', {
			width : 1200,
			height : 690,
			scrollBars : 'YES'
		});
}


$(window).resize( function() {
	$("#ibsheet1").css("width", $(window).width()-28);
// 	mySheet.FitColWidth();
});

</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<div id="content_wrap">
	<div>
		<form name="dataForm" id="dataForm" >
	  <!--   <div id="wrap_menu">      -->
				<!-- 조회조건 6칸 -->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit">기획전조회</li>
							<li class="btn">
	                        	<a href="javascript:doSearch();" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
							</li>
						</ul>            
	
			            <ul>
					            <table class="bbs_search" width="99.5%" cellpadding="0" cellspacing="1" border="0" summary="전시템플릿관리" >
			                    <col width="15%" />
			                    <col width="35%" />
			                    <col width="15%" />
			                    <col width="35%" />
			                    <tr>
			                        <th>기획전 명</th>
			                        <td>
			                      		<input name="mkdpNm" type="text" id="mkdpNm" title="기획전명" style="width: 95%">
			                        </td>                        
			                        <th>
			                        	<select name="mkdpGubn" id="mkdpGubn" style="width:95%" title="전시시작일" onChange="javascript:selectCategoryParentIdChange(this, false)" >
			                          		<option value="1">승인 요청일</option>
			                          		<option value="2">전시 시작일</option>
			                       		</select>
			                        </th>
			                        <td>             
			                        	<input class="day" name="startDate" type="text" id="startDate" style="width:80px" title="년월일"
				                    	 onclick="javascript:openCal('dataForm.startDate');">
				                    	
			                        	<a href="javascript:openCal('dataForm.startDate');">
			                        		<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" />
			                        	</a>
			                        	
			                        	~
				                        <input class="day" name="endDate" type="text" id="endDate" style="width:80px" title="년월일"
				                         onclick="javascript:openCal('dataForm.endDate');">
				                         
				                        <a href="javascript:openCal('dataForm.endDate');">
				                        	<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" />
				                        </a>
			                        </td>
			                    </tr>
			                    <tr>
				                    <th>기획전 번호</th>
				                    <td>
				                      	<input name="mkdpSeq" type="text" id="mkdpSeq" title="기획전번호" style="width: 95%">
				                    </td>
<!-- 				                    <th>협력사</th> -->
<!-- 				                    <td> -->
<%-- 				                      	<c:choose> --%>
<%-- 											<c:when test="${epcLoginVO.vendorTypeCd eq '06'}"> --%>
<%-- 												<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/> --%>
<!-- 												<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a> -->
<%-- 											</c:when> --%>
<%-- 											<c:when test="${epcLoginVO.vendorTypeCd != '06'}"> --%>
<!-- 												<select name="vendorId" id="vendorId" class="select"> -->
<!-- 													<option value="">전체</option> -->
<%-- 												<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0"> --%>
<%-- 							                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option> --%>
<%-- 												</c:forEach> --%>
<!-- 												</select> -->
<%-- 											</c:when> --%>
<%-- 										</c:choose> --%>
<!-- 				                    </td>                -->
									<th></th>
									<td></td>
			                    </tr>
			                    
			                    <tr>
				                   <th>전시상태</th>  
						          	<td>
					                    <select id="dispYn" name="dispYn" class="required" style="width:95%;" title="전시상태">
											<option value="">전체</option>
											<option value="Y">전시</option>
											<option value="N">비전시</option>
										</select>
						          	</td>	  
						        	
						          	                      
				                   <th>승인상태</th>  
						          	<td>
					                    <select id="aprvStsCd" name="aprvStsCd" style="width:95%;" title="승인상태">
											<option value="">전체</option>
											<c:forEach items="${applyCdList}" var="applyCdList" varStatus="index" >
												<option value="${applyCdList.MINOR_CD}">${applyCdList.CD_NM}</option> 
											</c:forEach>
										</select>
						          	</td>	  
						        	
			                    </tr>
			                    
			                  </table>
			            </ul>
	            	</div>
	
	            </div>
	            <!-- 1검색조건 // -->
	            
	            <!-- content_01 -->
	            <div class="wrap_con">
	            
			        <!-- list -->
			        <div class="bbs_list">
			            <ul class="tit">
			                <li class="tit">통합기획전 목록</li>
		<%-- 	                <li class="btn">
			                    <a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel"/></span></a>
			                </li> --%>
			            </ul>
			            <ul>
			              <%-- 건수와 정렬개수를 세팅하기 위해 li의 ID는 페이징 div 부분의 ID에 Cnt를 붙여서 부여한다. --%>
			                <li class="lp" id="pagingDivCnt"></li>
			            </ul>			            
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
			                <tr>
			                    <div id="ibsheet1"></div>
			                </tr>
			            </table>
					    <div id="pagingDiv" class="pagingbox1" style="width: 100%;">
				            <script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script> <!-- 페이징 -->
					   </div>
	            
					</div>
	            </div>
	<!--		</div>  menu -->
		</form>
	</div> 
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>기획전관리</li>
					<li class="last">기획전관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>		
</body>
</html>