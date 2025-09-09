<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:import url="/common/commonHead.do" />
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script>
function PopupWindow(pageName) {
	var cw = 400;
	var ch = 440;
	var sw = screen.availWidth;
	var sh = screen.availHeight;
	var px = Math.round((sw-cw)/2);
	var py = Math.round((sh-ch)/2);
	window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}

function doSearch() {

	var form = document.forms[0];
	var tmpStart = form.startDate.value.split("-");
	var tmpEnd = form.endDate.value.split("-");

	if(tmpStart[0] != tmpEnd[0]){
		alert("동일한 년월로 조회하세요.");
		return;
	}
	if(tmpStart[1] != tmpEnd[1]){
		alert("동일한 년월로 조회하세요.");
		return;
	}

	if(dateValid(form)){
		loadingMaskFixPos();
		form.action = "<c:url value='/order/viewSaleProductListSelect.do'/>";
		form.submit();
	}

}

function storePopUp(){
	PopupWindow("<c:url value='/common/PSCMCOM0009.do'/>");
}

function productPopUp(){
	PopupWindow("<c:url value='/common/PSCMCOM0010.do'/>");
}

function fnOnlyNumber(){
	if(event.keyCode < 48 || event.keyCode > 57)
	event.keyCode = null;
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

	rangeDate = Date.parse(endDate)-Date.parse(intStartDate);
	rangeDate = Math.ceil(rangeDate/24/60/60/1000);

	if(rangeDate > 30){
		alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
		form.startDate.focus();
		return false;
	}

	return true;
}

function popupSearch(tbName1, tbName2, tbName3){

	var tbody1 = $('#' + tbName1 + ' tbody');
	var tbody2 = $('#' + tbName2 + ' tbody');
	var tbody3 = $('#' + tbName3 + ' tbody');

	var form = document.forms[0];

	var date = form.startDate.value+"~"+form.endDate.value;
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

	form.staticTableBodyValue.value = "<CAPTION>매출정보 상품별 현황표<br>"+
	"[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [업체 : "+tmp+"]<br>"+
		"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html()+ tbody3.parent().html();

	form.name.value = "statics";
	form.action = "<c:url value='/edi/comm/PEDPCOM0003.do'/>";
	form.target = "_blank";
	form.submit();
	form.action = "";
	form.target = "";
}

function storeClear(){
	var form = document.forms[0];
	form.storeVal.value="";
}

function productClear(){
	var form = document.forms[0];
	form.productVal.value = "";
}

function forwarding(val) {

	var rcon = document.getElementById('content_wrap');
	var form = document.forms[0];

	loadingMaskFixPos();

	form.productVal.value = val;

	form.action = "<c:url value='/order/viewSaleDetailListSelect.do'/>";
	form.submit();
}

function printText(){
	var form = document.forms[0];

	var date = form.startDate.value+"~"+form.endDate.value;
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

	form.text_data.value="[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [업체 : "+tmp+"]";

	if(dateValid(form)){
		form.action = "<c:url value='/order/viewSaleProductListText.do'/>";
		form.submit();
	}
}
</script>
</head>
<body>
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" name="text_data" value="" />
		<input type="hidden" name="vendor" value="${paramMap.ven }">
		<input type="hidden" name="storeName" id="storeName" value="" />
		<input type="hidden" name="staticTableBodyValue" value="" />
		<input type="hidden" name="name" value="" />
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" />
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">검색조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2','testTable3');"><span><spring:message code="button.common.excel"/></span></a> 
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a> 
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
					<input type="hidden" id="productVal" name="productVal"  value="${param.productVal }"/>
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 조회기간 </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> 점포선택</th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" />전점조회
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/>점포선택
						</td>
					</tr>
					<tr>
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out escapeXml='false' value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="searchForm" defName="전체" />
						</td>
						<th>상품코드</th>
						<td>
							<input type="Radio" name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/>전상품조회   
							<input type="Radio" name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/>상품선택
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
						<li class="tit">검색내역</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:120px;" />
						<col />
						<col style="width:150px;" />
						<col style="width:150px;" />
						<col style="width:150px;" />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th>상품코드</th>
						<th>상품명</th>
						<th>매출수량</th>
						<th>매출금액</th>
						<th>판매금액</th>
						<th>&nbsp;</th>
					</tr>
					</table>
					<div class="datagrade_scroll_sum" style="height:350px;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
						<col style="width:120px;" />
						<col />
						<col style="width:150px;" />
						<col style="width:150px;" />
						<col style="width:150px;" />
					</colgroup>
					<c:if test="${not empty saleList }">
						<c:set var="total_qty" value="0" />
						<c:set var="total_amt" value="0" />
						<c:set var="total_sell_amt" value="0" />
						<c:forEach items="${saleList}" var="list" varStatus="index">
							<tr class="r1">
								<td align="center" style="mso-number-format:'\@'"><a href="javascript:forwarding('${list.prodCd }');">${list.prodCd }</a></td>
								<td align="left">&nbsp; ${list.prodNm }</td>
								<td align="right"><fmt:formatNumber value="${list.saleQty }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.saleAmt }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.totSellAmt }" type="number" currencySymbol="" /></td>
								<c:set var="total_qty" value="${total_qty + list.saleQty }" />
								<c:set var="total_amt" value="${total_amt + list.saleAmt }" />
								<c:set var="total_sell_amt"  value="${total_sell_amt + list.totSellAmt }" />
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty saleList }">
						<tr><td colspan="6" align="center">${resultMsg }</td></tr>
					</c:if>
					</table>
					</div>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
						<c:if test="${not empty saleList }">
						<tr class="r1">
							<th align="center" colspan="2"><b>합 계</b></th>
							<th align="right" width="150"><b><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></b></th>
							<th align="right" width="150"><b><fmt:formatNumber value="${total_amt }" type="number" currencySymbol="" /></b></th>
							<th align="right" width="150"><b><fmt:formatNumber value="${total_sell_amt }" type="number" currencySymbol="" /></b></th>
							<th width="17">&nbsp;</th>
						</tr>
						</c:if>
						<c:if test="${empty saleList }"><tr><td colspan="5">&nbsp;</td><th width="17">&nbsp;</th></tr></c:if>
					</table>
				</div>
			</div>
		</div>
		</form>
	</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>매출정보</li>
					<li>기간정보</li>
					<li class="last">상품별</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
