<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Class Name : NEDMORD0010.jsp    
	Description : 발주정보 > 기간별 > 상품별
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    -------------------------------------
	2015.11.13 		  안태경 		 최초생성
	
	author   : an tae kyung
	since    : 2015.11.13
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	$(document).ready(function() {

	});
	
	/* 조회 */
	function doSearch() {
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val().replaceAll("-", "");
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val().replaceAll("-", "");
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='searchEntpCd']").val();
		// 상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();
		
		if (fnDateValid(searchInfo["srchStartDate"], searchInfo["srchEndDate"], "15")) {
			loadingMaskFixPos();
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/order/NEDMORD0010Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});
		}
		
		hideLoadingMask();
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
	
	/* Excel */
	function doExcel(tbName1, tbName2, tbName3) {
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');
		var tbody3 = $('#' + tbName3 + ' tbody');

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();

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

		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.ord.orderDashBoardPerordSlipNo'/><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[<spring:message code='epc.ord.product'/> : " + product + "]";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		bodyValue += tbody2.parent().html();
		bodyValue += tbody3.parent().html();
		//console.log(bodyValue);
		
		$("#searchForm input[id='staticTableBodyValue']").val(bodyValue);
		//console.log($("#searchForm input[id='staticTableBodyValue']").val());
		
		$("#searchForm input[id='name']").val("statics");
		$("#searchForm").attr("target", "_blank");
		$("#searchForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
		$("#searchForm").submit();
		
		$("#searchForm").attr("target", "");
		$("#searchForm").attr("action", "");
	}

	/* 전표별 화면으로 분기 */
	function forwarding(val) {
		$("#searchForm input[id='mode']").val("select");
		$("#searchForm input[id='srchStartDate']").val(val);
		$("#searchForm input[id='srchEndDate']").val(val);

		loadingMaskFixPos();
		
		$("#searchForm").attr("action", "<c:url value='/edi/order/NEDMORD0020.do'/>");
		$("#searchForm").submit();
	}

	/* 점포별 화면으로 분기 */
	function forwarding2(val, val2) {
		$("#searchForm input[id='mode']").val("select");
		$("#searchForm input[id='productVal']").val(val);
		$("#searchForm input[id='srchStartDate']").val(val2);
		$("#searchForm input[id='srchEndDate']").val(val2);
		
		//if (dateValid()) {
		//loadingMaskFixPos();
		
		$("#searchForm").attr("action", "<c:url value='/edi/order/NEDMORD0040.do'/>");
		$("#searchForm").submit();
		//}
	}
	
	/* 점포선택 초기화 */
	function storeClear() {
		$("#searchForm input[id='storeVal']").val("");
		$("#searchForm input[id='storeName']").val("");
	}
	
	/* 상품선택 초기화 */
	function productClear() {
		$("#searchForm input[id='productVal']").val("");
	}

	/* TEXT 파일 */
	function printText() {
		// 조회시작일자
		var srchStartDate 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회 종료일자
		var srchEndDate 	= $("#searchForm input[name='srchEndDate']").val();
		// 조회기간 설정
		var date = srchStartDate + "~" + srchEndDate;
		// 점포명
		var store 		= $("#searchForm input[name='storeName']").val();
		// 상품코드
		var product 	= $("#searchForm input[name='productVal']").val();
		// 협력업체 코드(배열)
		var vendor 		= $("#searchForm input[name='vendor']").val();
		// 협력업체 코드(단독)
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();
		
		if (store == "") {
			store = "전체";
		}
		
		if (product == "") {
			product = "전체";
		}
		
		var tmp = "";
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}
		
		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.store'/> : " + store + "]"
		textData += "[<spring:message code='epc.ord.product'/> : " + product + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		
		if (fnDateValid(srchStartDate, srchEndDate, "15")) {
			$("#searchForm input[name='textData']").val(textData);
			$("#searchForm input[name='searchProductVal']").val($("#searchForm input[name='productVal']").val());
			//console.log(textData);
			
			$("#searchForm").attr("action", "<c:url value='/edi/order/NEDMORD0010Text.do'/>");
			$("#searchForm").submit();
		}
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
				<input type="hidden" id="mode" name="mode" />
				<input type="hidden" name="text_data" />
				<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven}" />">
				<input type="hidden" id="storeName" name="storeName" />
				<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
				<input type="hidden" name="name">
				<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize}" />">
				<input type="hidden" id="textData" name="textData" />
				<input type="hidden" id="searchProductVal" name="searchProductVal" />
				<%-- <input type="hidden" name="textList" value="$<c:out value="{orderList}" />" /> --%>
				
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message code='epc.ord.searchCondition' /><!-- <strong>&nbsp;<font color="red">※현재 발주 데이터 오류현상이 발생하고 있으니 참고하시기 바랍니다.</font></strong> --></li>
								<li class="btn">
									<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire" /></span></a>
									<a href="#" class="btn" onclick="doExcel('headTbl','listTbl','sumTbl');"><span><spring:message code="button.common.excel" /></span></a>
									<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">

								<input type="hidden" id="storeVal" name="storeVal" value="<c:out value="${param.storeVal}" />" />
								<input type="hidden" id="productVal" name="productVal" value="<c:out value="${param.productVal }" />" />

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
										<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width: 80px;" />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor: hand;" />
										&nbsp;~&nbsp;
										<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width: 80px;" />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');" style="cursor: hand;" />
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
										<html:codeTag objId="searchEntpCd" objName="searchEntpCd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
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
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
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
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="listTbl">
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
							
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="sumTbl">
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
