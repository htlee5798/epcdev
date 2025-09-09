<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	var flag = false;

	/* 조회 */
	function doSearch() {
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val();
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='searchEntpCd']").val();
		// 상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();
		// 발주구분
		searchInfo["searchOrdering"]		=	$("#searchForm select[name='searchOrdering']").val();
		
		if (dateValid()) {
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'html',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/order/NEDMORD0050Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$('#testTable1').html(data);
				}
			});	
		}
	}
	
	/* 조회기간 체크 */
	function dateValid() {
		var srchStartDate 	= $("#searchForm input[name='srchStartDate']").val();
		var srchEndDate 	= $("#searchForm input[name='srchEndDate']").val();
		
		if (!fnDateValid(srchStartDate, srchEndDate, "15")) {
			return false;
		}
		
		return true;
	}
	
	/* Excel */
	function doExcel(tbName1) {
		var tbody1 = $('#' + tbName1 + ' tbody');

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();
		
		var tmp = "";

		if (store == "") {
			store = "<spring:message code='epc.ord.all'/>";
		}
		
		if (product == "") {
			product="<spring:message code='epc.ord.all'/>";
		}
		
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}

		var ordering="";
		ordering = $("#searchForm select[name='searchOrdering'] option:selected").text(); 
		
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.ord.orderDashBoardPdcOrdSlipNoDetail'/><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[<spring:message code='epc.ord.product'/> : " + product + "]";
		bodyValue += "[<spring:message code='epc.ord.ordering'/> : " + ordering + "]";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		
		if (tbody1.parent().html() != null) {
			bodyValue += tbody1.parent().html();	
		}
		
		$("#searchForm input[id='staticTableBodyValue']").val(bodyValue);
		//console.log($("#searchForm input[id='staticTableBodyValue']").val());
		$("#searchForm input[id='name']").val("statics");
		$("#searchForm").attr("target", "_blank");
		$("#searchForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
		$("#searchForm").submit();
		
		$("#searchForm").attr("target", "");
		$("#searchForm").attr("action", "");	
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

	/* 텍스트파일 */
	function printText(){
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
		
		var tmp = "";

		if (store=="") {
			store = "<spring:message code='epc.ord.all'/>";
		}
		
		if (product == "") {
			product = "<spring:message code='epc.ord.all'/>";
		}
		
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}
 
		var ordering="";
		ordering = $("#searchForm select[name='searchOrdering'] option:selected").text();

		var textData = "";
		
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.store'/> : " + store + "]"
		textData += "[<spring:message code='epc.ord.product'/> : " + product + "]"
		textData += "[<spring:message code='epc.ord.ordering'/> : " + ordering + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		
		if (dateValid()) {
			//loadingMaskFixPos();
			$("#searchForm input[name='textData']").val(textData);
			$("#searchForm input[name='searchProductVal']").val($("#searchForm input[name='productVal']").val());
			$("#searchForm").attr("action", "<c:url value='/edi/order/NEDMORD0050Text.do'/>");
			$("#searchForm").submit();
		}	
	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
		<div>
			<form id="searchForm" name="searchForm" method="post" action="#">
			<input type="hidden" name="text_data" />
			<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
			<input type="hidden" id="storeName" name="storeName" />
			<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
			<input type="hidden" name="name">
			<input type="hidden" id="textData" name="textData" />
			<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >			
			<input type="hidden" id="searchProductVal" name="searchProductVal" />
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"><spring:message code='epc.ord.searchCondition'/><!-- <strong>&nbsp;<font color="red">※현재 발주 데이터 오류현상이 발생하고 있으니 참고하시기 바랍니다.</font></strong> --></li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 								<a href="#" class="btn" onclick="doExcel('testTable1');"><span><spring:message code="button.common.excel"/></span></a> 
 								<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value="${param.storeVal }" />" />
						<input type="hidden" id="productVal" name="productVal" value="<c:out value="${param.productVal }" />" />
						
						<colgroup>
							<col style="width:100px;" />
							<col style="width:230px;" />
							<col style="width:100px;" />
							<col style="width:160px;" />
							<col style="width:100px;" />
							<col style="width:70px;" />
						</colgroup>
						<tr>
							<th><span class="star">*</span> <spring:message code='epc.ord.period'/> </th>
							<td>
								<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
							</td>
							<th><span class="star">*</span> <spring:message code='epc.ord.prodCdSelect'/></th>
							<td>
								<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='epc.ord.allStore'/>
								<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='epc.ord.strCdSelect'/>
							</td>
							<th></th><td></td>
						</tr>
						<tr>
							<th><spring:message code='epc.ord.venCd'/></th>
							<td>
								<html:codeTag objId="searchEntpCd" objName="searchEntpCd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
							</td>
							
							<th><spring:message code='epc.ord.prodCd'/></th>
							<td>
								<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message code='epc.ord.allProd'/>
								<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/><spring:message code='epc.ord.prodCdSelect'/>
							</td>
							
							<th><spring:message code='epc.ord.ordering'/></th>
							<td >
								<html:codeTag objId="searchOrdering" objName="searchOrdering" comType="SELECT" selectParam="<c:out value='${param.ordering}'/>" formName="form" parentCode="ORD01" subCode="1" defName="전체" ></html:codeTag>
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
							<li class="tit"><spring:message code='epc.ord.search'/></li>
						</ul>
						<div style="width:100%; height:455px; overflow-y:scroll; ">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<c:if test="${not empty orderList }">
							<colgroup>
								<col style="width:12%" />
								<col style="width:14%" />
								<col style="width:12%" />
								<col style="width:12%" />
								<col style="width:12%" />
								<col style="width:12%"  />
								<col style="width:14%" />
								<col style="width:12%"  />
							</colgroup>
							
							<c:set var="tmp"  value="empty" />
							
							
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<c:if test="${list.ordSlipNo != tmp }">
									<tr class="r1">
										<th><spring:message code='epc.ord.centerNm'/></th>
										<th><spring:message code='epc.ord.storeNm'/></th>
										<th style="display:none;"><spring:message code='epc.ord.strCd'/></th>
										<th><spring:message code='epc.ord.ordDy'/></th>
										<th><spring:message code='epc.ord.ordSlipNo'/></th>
										<th><spring:message code='epc.ord.ordQty'/></th>
										<th><spring:message code='epc.ord.buyPrc'/></th>
										<th><spring:message code='epc.ord.ctrArrDy'/></th>
										<th><spring:message code='epc.ord.splyDy'/></th>
									</tr>
									<tr class="r1">
										<td align=center><c:out value="${list.ctnNm }" /></td>
										<td align=center><c:out value="${list.strNm }" /></td>
										<td align=center style="display:none;"><c:out value="${list.strCd }" /></td>
										<td align=center><c:out value="${list.ordDy }" /></td>
										<td align=center style="mso-number-format:'\@'"><c:out value="${list.ordSlipNo }" /></td>
										<td align=center><c:out value="${list.totQty }" /></td>
										<td align="right"><fmt:formatNumber value="${list.totPrc }" type="number" currencySymbol="" /></td>
										<td align=center><c:out value="${list.ctrArrDy }" /></td>
									<td align=center><c:out value="${list.splyDy }" /></td>
								</tr>
								</c:if>
								<c:if test="${list.ordSlipNo != tmp }">
								<tr class="r1">
									<th><spring:message code='epc.ord.prodCd'/></th>
									<th><spring:message code='epc.ord.srcmkCd'/></th>
									<th colspan="2"><spring:message code='epc.ord.prodNm'/></th>
									<th><spring:message code='epc.ord.ordIpsu'/></th>
									<th><spring:message code='epc.ord.purUnitCdNm'/></th>
									<th><spring:message code='epc.ord.ordQty'/></th>
									<th><spring:message code='epc.ord.buyPrc'/></th>
								</tr>
								</c:if>
								<tr class="r1">
									<td align=center style="mso-number-format:'\@'"><c:out value="${list.prodCd }" /></td>
									<td align=center style="mso-number-format:'\@'"><c:out value="${list.srcmkCd }" /></td>
									<td colspan="2" align="left">&nbsp;<c:out value="${list.prodNm }" /></td>
									<td align="right"><fmt:formatNumber value="${list.ordIpsu }" type="number" currencySymbol="" /></td>
									<td align=center><c:out value="${list.purUnitCdNm }" /></td>
									<td align="right"><fmt:formatNumber value="${list.ordQty }" type="number" currencySymbol="" /></td>
									<td align="right"><fmt:formatNumber value="${list.buyPrc }" type="number" currencySymbol="" /></td>
									<td align=center style="display:none;">&nbsp;</td>
								</tr>
								<c:set var="tmp" value="${list.ordSlipNo }" />
							</c:forEach>
						</c:if>
						
						<c:if test="${empty orderList }">
							<tr><td colspan="8" align=center><spring:message code='epc.ord.emptySearchResult'/></td></tr>
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
						<li><spring:message code='epc.ord.home'/></li>
						<li><spring:message code='epc.ord.orderInfo'/></li>
						<li><spring:message code='epc.ord.dateInfo'/></li>
						<li class="last"><spring:message code='epc.ord.pdcordSlipNoDetail'/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>
