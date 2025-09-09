<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>

	/* 폼로드 */
	$(document).ready(function($) {		
		$("select[name='entp_cd']").val("<c:out value='${param.entp_cd}'/>");	// 협력업체 선택값 세팅
	});
	
	function PopupWindow(pageName) {
		var cw=400;
		var ch=440;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	function doSearch() {
		
		var form = document.forms[0];
		
		var tmpStart=form.startDate.value.split("-");
		var tmpEnd=form.endDate.value.split("-");
		
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
			form.action  = "<c:url value='/edi/inventory/PEDMINV0001Select.do'/>";
			form.submit();		
		}	
		
	}

	function storePopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0001.do'/>");
	}

	function productPopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0002.do'/>");
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


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);

		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}	

		return true;
	}
	
	function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		

		var form=document.forms[0];	

		var date=form.startDate.value+"~"+form.endDate.value;
		var store=form.storeName.value;
		var product=form.productVal.value;

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";

		if(store==""){
			store="전체";
		}
		if(product==""){
			product="전체";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}

		form.staticTableBodyValue.value = "<CAPTION>재고정보 현재고(점포) 현황표<br>"+
		"[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html();
	
		form.name.value = "statics";
		form.action="<c:url value='/edi/comm/PEDPCOM0003.do'/>";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="";
	}	

	function storeClear(){
		var form = document.forms[0];
		form.storeVal.value="";
	}
	function productClear(){
		var form = document.forms[0];
		form.productVal.value="";
	}	

	function forwarding(val) {
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];

		loadingMaskFixPos();

		form.storeVal.value=val;
		
		form.action  = "<c:url value='/edi/inventory/PEDMINV0002Select.do'/>";
		form.submit();	
	}

	function printText(){
		var form = document.forms[0];

		var date=form.startDate.value+"~"+form.endDate.value;
		var store=form.storeName.value;
		var product=form.productVal.value;

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";

		if(store==""){
			store="전체";
		}
		if(product==""){
			product="전체";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}

		form.text_data.value="[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [업체 : "+tmp+"]";

		
		if(dateValid(form)){
			loadingMaskFixPos();

			form.action  = "<c:url value='/edi/inventory/PEDMINV0001Text.do'/>";
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
		<input type="hidden" name="text_data" />
		<input type="hidden" name="vendor" value="${paramMap.ven }">
		<input type="hidden" id="storeName" name="storeName" />
		<input type="hidden" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" >
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">검색조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a> 
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
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');"  style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> 점포선택</th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/>전점조회
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/>점포선택
						</td>
					</tr>
					<tr>
						<th>협력업체 코드</th>
						<td colspan="3">
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<%-- <th>상품코드</th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/>전상품조회   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/>상품선택
						</td> --%>
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
						<col style="width:100px;" />
						<col style="width:30px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:100px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th rowspan="2">점포명</th>
						<th rowspan="2" colspan="2">기초재고</th>
						<th colspan="4">월중 거래 내역</th>
						<th rowspan="2">매출</th>
						<th rowspan="2">현재고</th>
						<th rowspan="2">&nbsp;</th>
					</tr>
					<tr>
						<th>매입</th>
						<th>반품</th>
						<th>점출입</th>
						<th>매가인상하</th>
					</tr>
					</table>
					<div class="datagrade_scroll_sum">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
						<col style="width:100px;" />
						<col style="width:30px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:100px;" />
						<col  />
					</colgroup>
					<c:if test="${not empty inventoryList }">
						<c:set var="now_stk_qty"  value="0" />
						<c:set var="now_stk_amt"  value="0" />
						<c:forEach items="${inventoryList}" var="list" varStatus="index" >
							
							<c:set var="now_stk_qty"  value="${list.bookFwdQty + list.buyQty - list.rtnQty + list.strioQty - list.saleQty + list.stkAdjQty }" />
							<c:set var="now_stk_amt"  value="${list.bookFwdSale + list.buySaleAmt - list.rtnSaleAmt + list.strioSaleAmt + list.salePrcUpdownAmt - list.saleSaleAmt + list.stkAdjSaleAmt}" />
							<tr class="r1">
								<td rowspan="2" align="center"><a href="javascript:forwarding('${list.strCd }');">${list.strNm }</a></td>
								<td align="center">수량</td>
								<td align="right"><fmt:formatNumber value="${list.bookFwdQty }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.buyQty }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.rtnQty }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.strioQty }" type="number" currencySymbol="" /></td>
								<td></td>
								<td align="right"><fmt:formatNumber value="${list.saleQty }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${now_stk_qty }" type="number" currencySymbol="" /></td>
							</tr>
							<tr class="r1">
								<td align="center">금액</td>
								<td align="right"><fmt:formatNumber value="${list.bookFwdSale }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.buySaleAmt }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.rtnSaleAmt }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.strioSaleAmt }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.salePrcUpdownAmt }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.saleSaleAmt }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${now_stk_amt }" type="number" currencySymbol="" /></td>
							</tr>
							<c:set var="now_stk_qty"  value="0" />
							<c:set var="now_stk_amt"  value="0" />
						</c:forEach>
					</c:if>
					<c:if test="${empty inventoryList }">
						<tr><td colspan="9" align=center>Data가 없습니다.</td></tr>
					</c:if>
					</table>
					</div>
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
					<li>재고정보</li>
					<li>기간정보</li>
					<li class="last">현재고(점포)</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
