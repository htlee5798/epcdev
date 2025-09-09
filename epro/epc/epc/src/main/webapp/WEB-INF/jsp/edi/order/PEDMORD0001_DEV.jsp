<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	$(function() {

	});
	
	/* 조회 */
	function doSearch() {
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["searchStartDate"] 	= $("#searchForm input[name='startDate']").val().replaceAll("-", "");
		// 조회기간(To)
		searchInfo["searchEndDate"] 	= $("#searchForm input[name='endDate']").val().replaceAll("-", "");
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		// 상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();
		
		if (dateValid()) {
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/order/PEDMORD0001Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});
		}
	}

	/* _eventSearch() 후처리(data 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.orderList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			var totalOrdIpsu = 0;	// 입수합계
			var totalOrdQty = 0;	// 발주수량 합계
			var totalBuyPrc = 0;	// 발주금액 합계
			
			var ordIpsu = 0;
			var ordQty = 0;
			var buyPrc = 0;
			
			// 금액 및 수량 comma 및 합계
			for (var i = 0; i < data.length; i++) {
				ordIpsu = data[i]["ordIpsu"];
				ordQty = data[i]["ordQty"];
				buyPrc = data[i]["buyPrc"];
				
				// 합계
				totalOrdIpsu 	+= parseInt(ordIpsu);
				totalOrdQty 	+= parseInt(ordQty);
				totalBuyPrc 	+= parseInt(buyPrc);
				
				// Comma 설정
				data[i]["ordIpsu"] 	= setComma(ordIpsu);
				data[i]["ordQty"] 	= setComma(ordQty);
				data[i]["buyPrc"] 	= setComma(buyPrc);
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			
			$('#totalOrdIpsu').text(setComma(totalOrdIpsu));
			$('#totalOrdQty').text(setComma(totalOrdQty));
			$('#totalBuyPrc').text(setComma(totalBuyPrc));
			
			$('#sumRow').show();
		} else {
			setTbodyNoResult("dataListbody", 9);
			$('#sumRow').hide();
			$('#emptyRow').show();
		}
	}
	
	/* 조회값 체크 */
	function dateValid() {
		var startDate 	= $("#searchForm input[name='startDate']").val();
		var endDate 	= $("#searchForm input[name='endDate']").val();
		
		var rangeDate = 0;

		if (startDate == "" || endDate == "") {
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			//form.startDate.focus();
			return false;
		}

		// startDate, endDate 는 yyyy-mm-dd 형식
		startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
		endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);
		//console.log(startDate + "::" + endDate);
		
		var intStartDate = parseInt(startDate);
		var intEndDate = parseInt(endDate);

		if (intStartDate > intEndDate) {
			alert("<spring:message code='msg.common.fail.calendar'/>");
			form.startDate.focus();
			return false;
		}

		intStartDate = new Date(startDate.substring(0, 4), startDate.substring(4, 6), startDate.substring(6, 8), 0, 0, 0);
		endDate = new Date(endDate.substring(0, 4), endDate.substring(4, 6), endDate.substring(6, 8), 0, 0, 0);

		rangeDate = Date.parse(endDate) - Date.parse(intStartDate);
		rangeDate = Math.ceil(rangeDate / 24 / 60 / 60 / 1000);

		if (rangeDate > 15) {
			alert("<spring:message code='msg.common.fail.rangecalendar_15'/>");
			form.startDate.focus();
			return false;
		}

		return true;
	}

	/* Excel */
	function doExcel(tbName1, tbName2, tbName3) {
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');
		var tbody3 = $('#' + tbName3 + ' tbody');

		var form = document.forms[0];

		var date = $("#searchForm input[name='startDate']").val() + "~" + $("#searchForm input[name='endDate']").val();
		
		var store = $("#searchForm input[name='storeName']").val();
		var productVal = $("#searchForm input[name='productVal']").val();
		
		var vendor = $("#searchForm input[name='vendor']").val();
		var vencd = $("#searchForm select[name='entp_cd']").val();

		var tmp = "";

		if (store == "") {
			store = "전체";
		}

		if (product == "") {
			product = "전체";
		}
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}

		var bodyValue = "<CAPTION><spring:message code='epc.ord.orderDashBoardPerordSlipNo'/><br>"
				+ "[<spring:message code='epc.ord.period'/> : " + date + "]"
				+ "[<spring:message code='epc.ord.store'/> : " + store + "]"
				+ "[<spring:message code='epc.ord.product'/> : " + product + "]"
				+ "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
				+ "<br>"
				+ "</CAPTION>"
				+ tbody1.parent().html()
				+ tbody2.parent().html() + tbody3.parent().html();
		
		$("staticTableBodyValue").val(bodyValue);
		
		$("#searchForm input[id='name']").val("statics");
		$("#searchForm").attr("action", "<c:url value='/edi/comm/PEDPCOM0003.do'/>");
		
		form.name.value = "statics";
		form.target = "_blank";
		form.submit();
		form.action = "";
		form.target = "";
	}

	/* 전표별 화면으로 분기 */
	function forwarding(val) {
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];

		form.mode.value = "select";
		form.startDate.value = val;
		form.endDate.value = val;

		loadingMaskFixPos();
		//form.action  = "<c:url value='/edi/order/PEDMORD0002Select.do'/>";
		form.action = "<c:url value='/edi/order/PEDMORD0002.do'/>";
		form.submit();
	}

	/* 점포별 화면으로 분기 */
	function forwarding2(val, val2) {

		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];
		form.mode.value = "select";
		form.productVal.value = val;
		form.startDate.value = val2;
		form.endDate.value = val2;

		if (dateValid(form)) {
			loadingMaskFixPos();
			form.action = "<c:url value='/edi/order/PEDMORD0004.do'/>";
			form.submit();
		}
	}

	function removeChar(orgChar, rmChar) {
		return replace(orgChar, rmChar, "");
	}

	function storeClear() {
		var form = document.forms[0];
		form.storeVal.value = "";
	}

	function productClear() {
		var form = document.forms[0];
		form.productVal.value = "";
	}

	function printText() {
		var form = document.forms[0];
		var date = form.startDate.value + "~" + form.endDate.value;
		var store = form.storeName.value;
		var product = form.productVal.value;

		var vendor = form.vendor.value;
		var vencd = form.entp_cd.value;

		var tmp = "";

		if (store == "") {
			store = "전체";
		}
		if (product == "") {
			product = "전체";
		}
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}
		form.text_data.value = "[<spring:message code='epc.ord.period'/> : "
				+ date + "] [<spring:message code='epc.ord.store'/> : " + store
				+ "] [<spring:message code='epc.ord.product'/> : " + product
				+ "] [<spring:message code='epc.ord.vendor'/> : " + tmp + "]";

		if (dateValid(form)) {
			//loadingMaskFixPos();
			form.action = "<c:url value='/edi/order/PEDMORD0001Text.do'/>";
			form.submit();
		}

	}
	
	/**
	 * 숫자체크 함수
	 */
	function fnOnlyNumber(event) {
		alert(event.keyCode);
		if (event.keyCode < 48 || event.keyCode > 57) event.keyCode = null;
	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1">
		<td align="center"><a href="javascript:forwarding('<c:out value="\${ordDy}"/>');"><c:out value="\${ordDy}"/></a></td>
		<td align="center" style="mso-number-format:'\@'"><a href="javascript:forwarding2('<c:out value="\${prodCd}"/>','<c:out value="\${ordDy}"/>');"><c:out value="\${prodCd}"/></a></td>	
		<td align="left"><c:out value="\${prodNm}"/></td>
		<td align="right" class="ordIpsu"><c:out value="\${ordIpsu}"/></td>
		<td align="center"><c:out value="\${purUnitCdNm}"/></td>
		<td align="right" class="ordQty"><c:out value="\${ordQty}"/></td>
		<td align="right" class="buyPrc"><c:out value="\${buyPrc}"/></td>
	</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>>
		<div>
			<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post" action="#">
				<input type="hidden" name="mode" />
				<input type="hidden" name="text_data" />
				<input type="hidden" name="vendor" value="${paramMap.ven }">
				<input type="hidden" id="storeName" name="storeName" />
				<input type="hidden" name="staticTableBodyValue">
				<input type="hidden" name="name">
				<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }">
				<input type="hidden" name="textList" value="${orderList }" />
				
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message code='epc.ord.searchCondition' /></li>
								<li class="btn">
									<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire" /></span></a>
									<a href="#" class="btn" onclick="doExcel('testTable1','testTable2','testTable3');"><span><spring:message code="button.common.excel" /></span></a>
									<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">

								<input type="hidden" id="storeVal" name="storeVal" value="${param.storeVal}" />
								<input type="hidden" id="productVal" name="productVal" value="${param.productVal }" />

								<colgroup>
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 10%" />
									<col style="" />
								</colgroup>
								<tr>
									<th>
										<span class="star">*</span>
										<spring:message code="epc.ord.period" arguments="" />
									</th>
									<td>
										<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" value="${paramMap.startDate}" style="width: 80px;" />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor: hand;" />
										&nbsp;~&nbsp;
										<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" value="${paramMap.endDate}" style="width: 80px;" />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');" style="cursor: hand;" />
									</td>
									<th>
										<span class="star">*</span>
										<spring:message code="epc.ord.strCdSelect" arguments="" />
									</th>
									<td>
										<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" /> 
										<spring:message code="epc.ord.allStore" arguments="" />
										<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();" />
										<spring:message code="epc.ord.strCdSelect" arguments="" />
									</td>
								</tr>
								<tr>
									<th>
										<spring:message code="epc.ord.venCd" arguments="" />
									</th>
									<td>
										<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entp_cd}" dataType="CP" comType="SELECT" formName="form" defName="전체" />
									</td>
									<th>
										<spring:message code="epc.ord.prodCd" arguments="" />
									</th>
									<td>
										<input type="Radio" name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if> onclick="javascript:productClear();" />전상품조회 
										<input type="Radio" name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();" />상품선택
									</td>
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
								<li class="tit"><spring:message code="epc.ord.search"
										arguments="" /></li>
							</ul>
							<table class="bbs_list" cellpadding="0" cellspacing="0"
								border="0" id="testTable1">
								<colgroup>
									<col style="width: 80px;" />
									<col style="width: 80px;" />
									<col />
									<col style="width: 100px;" />
									<col style="width: 110px;" />
									<col style="width: 120px;" />
									<col style="width: 120px;" />
									<col style="width: 17px;" />
								</colgroup>
								<tr>
									<th><spring:message code="epc.ord.ordDy" arguments="" /></th>
									<th><spring:message code="epc.ord.prodCd" arguments="" /></th>
									<th><spring:message code="epc.ord.prodNm" arguments="" /></th>
									<th><spring:message code="epc.ord.ordIpsu" arguments="" /></th>
									<th><spring:message code="epc.ord.purUnitCdNm" arguments="" /></th>
									<th><spring:message code="epc.ord.ordQty" arguments="" /></th>
									<th><spring:message code="epc.ord.buyPrc" arguments="" /></th>
									<th>&nbsp;</th>
								</tr>
							</table>
							<div class="datagrade_scroll_sum">
								<table class="bbs_list" cellpadding="0" cellspacing="0"
									border="0" id="testTable2">
									<colgroup>
										<col style="width: 80px;" />
										<col style="width: 80px;" />
										<col />
										<col style="width: 100px;" />
										<col style="width: 110px;" />
										<col style="width: 120px;" />
										<col style="width: 120px;" />
									</colgroup>
									<!-- Data List Body Start ------------------------------------------------------------------------------>
									<tbody id="dataListbody" />
									<!-- Data List Body End   ------------------------------------------------------------------------------>
								</table>
							</div>
							<table class="bbs_list" cellpadding="0" cellspacing="0"
								border="0" id="testTable3">
								<colgroup>
									<col style="width: 80px;" />
									<col style="width: 80px;" />
									<col />
									<col style="width: 100px;" />
									<col style="width: 110px;" />
									<col style="width: 120px;" />
									<col style="width: 120px;" />
									<col style="width: 17px;" />
								</colgroup>
								<tr class="r1" id="sumRow" style="display: none;">
									<th colspan="3" class="fst" align=center><b><spring:message code="epc.ord.total" arguments="" /></b></th>
									<th align=right><b id="totalOrdIpsu"></b></th>
									<th></th>
									<th align=right><b id="totalOrdQty"></b></th>
									<th align=right><b id="totalBuyPrc"></b></th>
									<th>&nbsp;</th>
								</tr>
								<tr id="emptyRow" style="display: none;">
									<td colspan=7>&nbsp;</td>
									<th width=17>&nbsp;</th>
								</tr>
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
						<li><spring:message code="epc.ord.home" arguments="" /></li>
						<li><spring:message code="epc.ord.orderInfo" arguments="" /></li>
						<li><spring:message code="epc.ord.dateInfo" arguments="" /></li>
						<li class="last"><spring:message code="epc.ord.perProd" arguments="" /></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>
