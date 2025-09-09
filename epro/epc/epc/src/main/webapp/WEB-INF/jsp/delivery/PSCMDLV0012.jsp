<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ include file="/common/scm/scmCommon.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:import url="/common/commonHead.do" />
<!-- delivery/PSCMDLV0006 -->


<script type="text/javascript">
<%
DecimalFormat df = new DecimalFormat("00");
Calendar currentCalendar = Calendar.getInstance();

currentCalendar.add(currentCalendar.DATE, -90);
String strYear31   = Integer.toString(currentCalendar.get(Calendar.YEAR));
String strMonth31  = df.format(currentCalendar.get(Calendar.MONTH) + 1);
String strDay31   = df.format(currentCalendar.get(Calendar.DATE));
String strDate31 = strYear31 + strMonth31 + strDay31;

%>


/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function()
{
	$('#search').click(function() {
		doSearch();
	});

	$('#excel').click(function() {
		doExcel();
	});			
	
	$('#startDate').click(function() {
		openCal('searchForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('searchForm.endDate');
	});
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"주문번호",		Type:"Text" ,			SaveName:"ORDER_ID", 			Align:"Center", Width:100, Edit:0}
	  , {Header:"주문일자",		Type:"Text", 			SaveName:"ORD_DY",				Align:"Center", Width:100, Edit:0}
	  , {Header:"주문상태", 		Type:"Text", 			SaveName:"ORD_STS_NM",			Align:"Center", Width:100, Edit:1}
	  , {Header:"업체코드", 		Type:"Text", 			SaveName:"STR_VENDOR_ID",		Align:"Center", Width:100, Edit:0}
	  , {Header:"상품유형", 		Type:"Text", 			SaveName:"ONLINE_PROD_TYPE_NM",	Align:"Center", Width:100, Edit:0}
	  , {Header:"판매코드", 		Type:"Text", 			SaveName:"PROD_CD",				Align:"Center", Width:100, Edit:0}
	  , {Header:"상품명",		Type:"Text", 			SaveName:"PROD_NM", 			Align:"Left",	Width:100, Edit:0}
	  , {Header:"배송상태",		Type:"Text", 			SaveName:"VEN_DELI_STATUS_NM",	Align:"Center", Width:100, Edit:0}
	  , {Header:"택배사", 		Type:"Text", 			SaveName:"HODECO_NM",			Align:"Center", Width:80,  Edit:0}
	  , {Header:"송장번호",		Type:"Text", 			SaveName:"HODECO_INVOICE_NO",	Align:"Center", Width:110, Edit:0}
	  , {Header:"매출 미확정 사유",Type:"Text", 			SaveName:"ERROR_REASON",		Align:"Center", Width:100, Edit:0}
	  , {Header:"주문일 기준\n매출 미확정 기간",Type:"Text",SaveName:"NO_FIIRMS_DAY",		Align:"Center", Width:100, Edit:0}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
//	mySheet.SetColHidden("SALE_DY", 1);
}); // end of ready
	
	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		
		var f = document.searchForm;
		
		var getDiff = getDateDiff(f.startDate.value,f.endDate.value);
		
		var startDate = f.startDate.value.replace( /-/gi, '' );
		var endDate   = f.endDate.value.replace( /-/gi, '' );
		
	
	    <c:choose>
			<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
			if ($("#vendorId").val() == "")
		    {
		        alert('업체선택은 필수입니다.');
		        $("#vendorId").focus();
		        return;
		    }
			</c:when>
		</c:choose>
		
		if(startDate>endDate){
			alert('시작일자가 종료일자보다 클수 없습니다.');
			return;
		}			
		
		if(getDiff>90){
			alert('시작일자보다 종료일자가 90일 이상 클수 없습니다.');
			return;
		}  
				
		goPage('1');
	}
	
	function goPage(currentPage)
	{
		var url = '<c:url value="/delivery/selectPartnerNoFirmsOrderList.do"/>';
		var param = new Object();
	
		param.rowsPerPage 	= $("#rowsPerPage").val();
		param.startDate = $("#startDate").val();
		param.endDate = $("#endDate").val();
		param.vendorId = $("#vendorId").val();
		param.orderId =   $("#orderId").val();
		param.prodTypeCd =   $("#prodTypeCd").val();
		param.ordStsCd =  $("#ordStsCd").val();
		param.strCd =  $("#strCd").val();
		param.saleStsCd =  $("#saleStsCd").val();  
		param.deliStatusCode =  $("#deliStatusCode").val();  
		
		param.mode = 'search';
		
		loadIBSheetData(mySheet, url, currentPage, null, param);
	    
	}	

	function doExcel()
	{
		if(mySheet.GetTotalRows() == 0){
			alert('조회할 데이터가 없습니다.');
			return;
		}
	
		if(confirm('엑셀 다운로드 하시겠습니까?')){
			
			var paramInfo = {};
			paramInfo["startDate"]	=	 $("#startDate").val();
			paramInfo["endDate"]	=	$("#endDate").val();
			paramInfo["vendorId"]	=	$("#vendorId").val();
			paramInfo["orderId"]	=	$("#orderId").val();
			paramInfo["prodTypeCd"]	=	$("#prodTypeCd").val();
			paramInfo["ordStsCd"]	=	$("#ordStsCd").val();
			paramInfo["saleStsCd"]	=	$("#saleStsCd").val();
			paramInfo["deliStatusCode"]	=	$("#deliStatusCode").val();
			
			var hideCols = '';
			var xlsUrl = '<c:url value="/delivery/selectPartnerNoFirmsOrderExcel.do"/>';
			var param = new Object();
			
	    	param.startDate = $("#startDate").val();
	    	param.endDate = $("#endDate").val();
	    	param.vendorId = $("#vendorId").val();
	    	param.orderId =   $("#orderId").val();
	    	param.ordStsCd =  $("#ordStsCd").val();
	    	param.setlTypeCd =  $("#setlTypeCd").val();
	    	param.saleStsCd =  $("#saleStsCd").val();  
	    	param.searchType =  $("#searchType").val();          
			
			directExcelDown(mySheet, '미확정배송리스트_' + new Date().yyyymmdd(), xlsUrl, '#searchForm', param, hideCols); // 전체 다운로드
		
		}
	}
	
	function excelDown() {
		$("#rowsPerPage > option:selected").val('65000');
		doSearch();
	}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<c:set var="startDate" value='<%=strDate31%>' />
<div id="content_wrap">

	<div class="content_scroll">
	
	<form id="searchForm" name="searchForm" method="post">
		
		<%-- <input type="hidden" name="codeList">
		<input type="hidden" name="saveDeliTypeCd" value="">
		<input type="hidden" name="strCd" value=""> --%>
		
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="javascript:doExcel('down');" class="btn" ><span><spring:message code="button.common.excel" /></span></a>							
							<a href="javascript:doSearch();" class="btn" ><span><spring:message code="button.common.inquire"/></span></a>
						</li> 
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="10%">
						<col width="30%">
						<col width="10%">
						<col width="20%">
						<col width="10%">
						<col width="20%">
					</colgroup>
					<tr>
						<th>주문일
							<%-- <select name="dateGbn" class="select" >
								<option value="1" <c:if test="${'1' eq searchVO.dateGbn }">selected="selected"</c:if>>주문일</option>
								<option value="2" <c:if test="${'2' eq searchVO.dateGbn}">selected="selected"</c:if>>발송완료일</option>
								<option value="3" <c:if test="${'3' eq searchVO.dateGbn}">selected="selected"</c:if>>배송희망일</option>
								<option value="3" <c:if test="${'3' eq searchVO.dateGbn}">selected="selected"</c:if>>발송예정일</option>
							</select> --%>
						</th>
						<td>
							<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="${searchVO.startDate}" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
							~
							<input type="text" name="endDate" id="endDate" class="day" readonly style="width:33%;" value="${searchVO.endDate}" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
						</td>
						<th>주문번호
							<%-- <select name="searchType" class="select">
								<option value="%" <c:if test="${'%' eq searchVO.searchType}">selected="selected"</c:if>>선택</option>
								<option value="1" <c:if test="${'1' eq searchVO.searchType}">selected="selected"</c:if>>주문번호</option>
			 					<option value="2" <c:if test="${'2' eq searchVO.searchType}">selected="selected"</c:if>>로그인ID</option>
								<option value="3" <c:if test="${'3' eq searchVO.searchType}">selected="selected"</c:if>>보내는분</option>
								<option value="4" <c:if test="${'4' eq searchVO.searchType}">selected="selected"</c:if>>받는분</option>
								<option value="5" <c:if test="${'5' eq searchVO.searchType}">selected="selected"</c:if>>상품코드</option>
								<option value="6" <c:if test="${'6' eq searchVO.searchType}">selected="selected"</c:if>>판매코드</option>
								<option value="7" <c:if test="${'7' eq searchVO.searchType}">selected="selected"</c:if>>원주문번호</option>
							</select>	 --%>					
						</th>
						<td class="text"><input type="text" id="orderId" name="orderId" value="${orderId }"/></td>
						<th>협력업체코드</th>
						<td>
							<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${vendorId}" style="width:40%;"/>
										</c:if>	
										<c:if test="${empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>	
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select">
											<option value="">전체</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
										
											<c:if test="${not empty vendorId}">
												<option value="${venArr}" <c:if test="${venArr eq vendorId}">selected</c:if>>${venArr}</option>
											</c:if>	
											<c:if test="${empty vendorId}">
												<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
											</c:if>	
					                        
										</c:forEach>
										</select>
									</c:when>
						</c:choose>
						</td>						
					</tr>
					<tr>
						<th>상품유형</th>
						<td>
							<select id="prodTypeCd" name="prodTypeCd" class="select">
								<option value="" selected="selected">전체</option>
								<c:forEach items="${SM335List}" var="codeList">
								<option value="${codeList.CD}" <c:if test="${codeList.CD eq searchVO.prodTypeCd}">selected="selected"</c:if>>${codeList.NM}</option>								
								</c:forEach>								
							</select>
						</td>
						<th>주문상태</th>
						<td>
							<select id="saleStsCd" name="saleStsCd" class="select">
								<option value="" selected="selected">전체</option>
								<c:forEach items="${OR002List}" var="codeList">
								<option value="${codeList.CD}" <c:if test="${codeList.CD eq searchVO.saleStsCd}">selected="selected"</c:if>>${codeList.NM}</option>								
								</c:forEach>								
							</select>								
						</td>
						<th>배송상태</th>
						<td>
							<select id="deliStatusCode" name="deliStatusCode" class="select">
								<option value="" <c:if test="${codeList.CD eq searchVO.deliStatusCode}">selected="selected"</c:if>>전체</option>
								<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.CD}" <c:if test="${codeList.MINOR_CD eq searchVO.deliStatusCode}">selected="selected"</c:if>>${codeList.NM}</option>								
								</c:forEach>
							</select>							
						</td>
					</tr>
					<tr>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
				
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">조회내역</li>
					</ul>

					<table class="bbs_list" cellpadding="0" cellspacing="0"
						border="0">
						<tr>
<!-- 									<td><script type="text/javascript">initWiseGrid("WG1", "100%", "320");	</script></td> -->
							<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
						</tr>
					</table>
				</div>
			</div>
			<!-- 2검색내역 // -->
			<!-- 페이징 DIV -->
			<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
				<script>
					setLMPaging("0", "0", "0", "goPage", "pagingDiv");	
				</script>
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
					<li>배송관리</li>
					<li class="last">협력업체배송리스트</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>
<form name="doUpdate" action="post">

</form>
<!--	@ BODY WRAP  END  	// -->
</body>
</html>