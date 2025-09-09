<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"%>
<%@ include file="/common/scm/scmCommon.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:import url="/common/commonHead.do" />
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>
<!-- order/PSCMORD0006 -->
<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	$('#search').click(function() {
		doSearch();
	});

	$('#excel').click(function() {
		doExcel();
	});

}); // end of ready

/** ********************************************************
 * execl
 ******************************************************** */
function doExcel(){
	var form = document.searchForm;
	var tmpStart = form.startDate.value.split("-");
	var tmpEnd = form.endDate.value.split("-");
	//var endDate = form.endDate.value;

	if(tmpStart[0] != tmpEnd[0]){
		alert("동일한 년월로 조회하세요.");
		return;
	}
	if(tmpStart[1] != tmpEnd[1]){
		alert("동일한 년월로 조회하세요.");
		return;
	}
	/* if(endDate == ""){
		alert("<spring:message code='msg.common.fail.nocalendar'/>");
		form.endDate.focus();
		return false;
	} */

	if(dateValid(form)){
		form.action = '<c:url value="/order/selectSalesInfobyStoreExcel.do"/>';
		form.submit();
	}
}

function doSearch() {
	var form = document.searchForm;
	var tmpStart = form.startDate.value.split("-");
	var tmpEnd = form.endDate.value.split("-");
	//var endDate = form.endDate.value;

	if(tmpStart[0] != tmpEnd[0]){
		alert("동일한 년월로 조회하세요.");
		return;
	}
	if(tmpStart[1] != tmpEnd[1]){
		alert("동일한 년월로 조회하세요.");
		return;
	}
	/* if(endDate == ""  ){
		alert("<spring:message code='msg.common.fail.nocalendar'/>");
		form.endDate.focus();
		return false;
	} */
	if(dateValid(form)){
		loadingMaskFixPos();
		form.action  = "<c:url value='/order/selectSalesInfobyStore.do'/>";
		form.submit();
	}
}

function dateValid(form){

	var startDate = form.startDate.value;
	var endDate = form.endDate.value;
	var rangeDate = 0;

	if(startDate == "" || endDate == ""  ){
		alert("<spring:message code='msg.common.fail.nocalendar'/>");
		form.startDate.focus();
		return false;
	}
	// startDate, endDate 는 yyyy-mm-dd 형식

	startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	var intStartDate = parseInt(startDate);
	var intEndDate = parseInt(endDate);

	if (intStartDate > intEndDate) {
		alert("<spring:message code='msg.common.fail.calendar'/>");
		form.startDate.focus();
		return false;
	}

	intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
	endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 

	rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	rangeDate=Math.ceil(rangeDate/24/60/60/1000);

	if(rangeDate > 30){
		alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
		form.startDate.focus();
		return false;
	}

	return true;
}

function storePopUp(){
	PopupWindow("<c:url value='/common/PSCMCOM0009.do'/>");
}

function productPopUp(){
	PopupWindow("<c:url value='/common/PSCMCOM0010.do'/>");
}


function storeClear(){
	var form = document.forms[0];
	form.storeVal.value="";
}

function productClear(){
	var form = document.forms[0];
	form.productVal.value="";
}

function fnOnlyNumber(){
	if(event.keyCode < 48 || event.keyCode > 57)
	event.keyCode = null;
}

function PopupWindow(pageName) {
	var cw = 400;
	var ch = 440;
	var sw = screen.availWidth;
	var sh = screen.availHeight;
	var px = Math.round((sw-cw)/2);
	var py = Math.round((sh-ch)/2);
	window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}

function forwarding(val) {
	var form = document.searchForm;
	form.storeVal.value=val;
	form.startDate.value=form.endDate.value;
	form.action = "<c:url value='/order/viewSaleProductListSelect.do'/>";
	form.submit();
}

function printText(){
	var form = document.forms[0];

	var date = form.endDate.value;
	var store = form.storeName.value;
	var product = form.productVal.value;

	var vendor = form.vendor.value;
	var vencd = form.entp_cd.value;

	var tmp = "";

	if(store == ""){
		store = "전체";
	}
	if(product == ""){
		product = "전체";
	}
	if(vencd == ""){
		tmp = vendor;
	}else{
		tmp = vencd;
	}

	form.text_data.value = "[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [업체 : "+tmp+"]";

	form.action = "<c:url value='/order/selectSalesInfobyStoreText.do'/>";
	form.submit();
}
</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<div id="content_wrap">
<div>
<div id="wrap_menu">
<form name="searchForm" method="post">
	<input type="hidden" name="storeName" id="storeName" value="" />
	<input type="hidden" name="text_data" value="" />
	<input type="hidden" name="vendor" value="${paramMap.ven }">
	<input type="hidden" name="storeVal" id="storeVal" value="${param.storeVal }" />
	<input type="hidden" name="productVal" id="productVal" value="${param.productVal }" />
	<!--	@ 검색조건	-->
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel"/></span></a>
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
					<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="10%" />
				<col width="25%" />
				<col width="10%" />
				<col width="20%" />
			</colgroup>
			<tr> 
				<th><span class="star">*</span> 조회기간</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" onKeyPress="fnOnlyNumber();" readonly style="width:80px;" value="${paramMap.startDate}" maxlength="10" /> <a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" class="middle" /></a>
					~
					<input type="text" name="endDate" id="endDate" class="day" onKeyPress="fnOnlyNumber();" readonly style="width:80px;" value="${paramMap.endDate}" maxlength="10" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th><span class="star">*</span> 점포선택</th>
				<td>
					<input type="Radio" name="strCdType"
					<c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" />전점조회
					<input type="Radio" name="strCdType"
					<c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();" />점포선택
				</td>
			</tr>
			<tr>
				<th>협력업체코드 </th>
				<td>
					<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out escapeXml='false' value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="searchForm" defName="전체" />
				</td>
				<th>상품코드</th>
				<td>
					<input type="Radio" name="prodCdType"
					<c:if test="${empty param.productVal }"> Checked</c:if> onclick="javascript:productClear();" />전상품조회
					<input type="Radio" name="prodCdType"
					<c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();" />상품선택
				</td>
			</tr>
			<tr>
				<td colspan="4" style="color:red;">한번에 다량의 데이터가 조회되는 경우 조회 실패가 발생할 수 있습니다. 조회조건을 설정하여 조회하시기 바랍니다.<br/>(예: 조회기간을 단기간으로 설정하시고 협력업체코드를 지정하여 조회)</td>
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
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
				<colgroup>
					<col style="width: 165px;" />
					<col style="width: 165px;" />
					<col style="width: 185px;" />
					<col style="width: 185px;" />
					<col style="width: 185px;" />
					<col style="width:17px;" />
				</colgroup>
				<tr>
					<th>점포명</th>
					<th>점포코드</th>
					<th>매출수량</th>
					<th>매출금액</th>
					<th>판매금액</th>
					<th>&nbsp;</th>
				</tr>
			</table>
			<div class="datagrade_scroll_sum" style="height:350px;">
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
				<colgroup>
					<col style="width: 165px;" />
					<col style="width: 165px;" />
					<col style="width: 185px;" />
					<col style="width: 185px;" />
					<col style="width: 185px;" />
					<col style="width: 7px;" />
				</colgroup>
			<c:set var="total_qty" value="0" />
			<c:set var="total_amt" value="0" />
			<c:set var="total_sell_amt" value="0" />
			<c:if test="${not empty saleList}">
			<c:forEach var="result" items="${saleList}" varStatus="status">
				<tr class="r1">
					<td align="center"><a href="javascript:forwarding('${result.STR_CD }');">${result.STR_NM }</a></td>
					<td align="center">${result.STR_CD }</td>
					<td style="text-align: right"><fmt:formatNumber value="${result.SALE_QTY }" pattern="#,##0" /></td>
					<td style="text-align: right"><fmt:formatNumber value="${result.SALE_AMT }" pattern="#,##0" /></td>
					<td style="text-align: right"><fmt:formatNumber value="${result.TOT_SELL_AMT }" pattern="#,##0" /></td>
				</tr>
					<c:set var="total_qty" value="${total_qty + result.SALE_QTY }" />
					<c:set var="total_amt" value="${total_amt + result.SALE_AMT }" />
					<c:set var="total_sell_amt" value="${total_sell_amt + result.TOT_SELL_AMT }" />
			</c:forEach>
			</c:if>
			<c:if test="${empty saleList}">
				<tr>
					<td colspan="5" align="center">${resultMsg }</td>
				</tr>
			</c:if>
			</table>
			</div>
			</table>
				<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
					<colgroup>
						<col style="width: 165px;" />
						<col style="width: 165px;" />
						<col style="width: 185px;" />
						<col style="width: 185px;" />
						<col style="width: 185px;" />
						<col style="width:17px;" />
					</colgroup>
					<c:if test="${not empty saleList }">
					<tr class="r1">
						<th align="center" colspan="2"><b>합 계</b></th>
						<th align="right"><b><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></b></th>
						<th align="right"><b><fmt:formatNumber value="${total_amt }" type="number" currencySymbol="" /></b></th>
						<th align="right"><b><fmt:formatNumber value="${total_sell_amt }" type="number" currencySymbol="" /></b></th>
						<th>&nbsp;</th>
					</tr>
					</c:if>
					<c:if test="${empty saleList }"><tr><td colspan="5">&nbsp;</td><td width="17">&nbsp;</td></tr></tr></c:if>
				</table>
		</div>
		<!-- 2검색내역 // -->
	</div>
</form>
</div>
</div>
<!-- footer -->
<div id="footer">
	<div id="footbox">
		<div class="msg" id="resultMsg"></div>
		<div class="location">
			<ul>
				<li>홈</li>
				<li>매출정보</li>
				<li class="last">점포별</li>
			</ul>
		</div>
	</div>
</div>
<!-- footer //-->
</div>
<!--	@ BODY WRAP  END  	// -->
</body>
</html>