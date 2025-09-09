<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>

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
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		// 상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();
		
		searchInfo["searchOrdering"]	=	$("#searchForm select[name='ordering']").val();
		
		if (dateValid()) {
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'html',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/order/NEDMORD0110Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$('#testTable1').html(data);
				}
			});	
		}
	}

	/* 저장 */
	function doUpdate() {
		if (confirm("<spring:message code='epc.ord.confirm21.update.msg'/>")) {
			if ($('#searchForm input[name=firstList]').val() == "none") {
				alert("<spring:message code='epc.ord.alert21.noResult.msg'/>");
				return;
			}
			
			var updateParam = {};
			var arrNEDMORD0110VO = new Array();
			
			$('.splyTm').each(function() {
				var NEDMORD0110VO = {};
				NEDMORD0110VO["ordSlipNo"] = $(this).data('ordSlipNo');
				
				var hour 	= $(this).find('#hour').val();
				var min 	= $(this).find('#min').val();
				
				// 시간을 2자리로 설정
				if (hour.length > 0) {
					hour = strLpad(hour, '0', 2);
				}
				
				// 분을 2자리로 설정
				if (hour.length > 0) {
					min = strLpad(min, '0', 2);
				}
				
				NEDMORD0110VO["hour"] 	= hour;
				NEDMORD0110VO["min"] 	= min;
				arrNEDMORD0110VO.push(NEDMORD0110VO);
			});
			updateParam['arrParam'] = arrNEDMORD0110VO;
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',	
				type: "post",
				dataType: "json",
				url: "<c:url value='/edi/order/NEDMORD0110Update.json'/>",
				data: JSON.stringify(updateParam),
				success: function(data) {
					alert("<spring:message code='epc.ord.msg.save'/>");
					doSearch();
				}
			});
		}
	}

	/* 숫자체크 */
	function fnOnlyNumber(event) {
		if (event.keyCode < 48 || event.keyCode > 57) {
			event.keyCode = null;
		}
	}
	
	/* 조회기간 체크 */
	function dateValid() {
		var startDate 	= $("#searchForm input[name='srchStartDate']").val();
		var endDate 	= $("#searchForm input[name='srchEndDate']").val();
		
		var rangeDate = 0;
		
		if (startDate == "" || endDate == "") {
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
		
		/*
		if (rangeDate>30) {
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}
		*/
		
		return true;
	}
	
	/* 점포선택 초기화 */
	function storeClear() {
		$("#searchForm input[id='storeVal']").val("");
	}
	
	/* 상품선택 초기화 */
	function productClear() {
		$("#searchForm input[id='productVal']").val("");
	}

	function keyValid(obj){
		for (var i = 0; i < obj.value.length; i++) {
			if (obj.value.charAt(i) < "0" || obj.value.charAt(i) > "9") {
				alert("<spring:message code='msg.common.error.noNum'/>");
				obj.focus();
				obj.value = "0";
				return;
			}
		}
	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
		<div>
			<form id="searchForm" name="searchForm" method="post" action="#">
			<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" > 
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"><spring:message code='epc.ord.searchCondition'/></li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
								<a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save"/></span></a>
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value="${param.storeVal }" />" />
						<input type="hidden" id="storeName" name="storeName" />
						<input type="hidden" id="forward_hour" name="forward_hour">
						<input type="hidden" id="forward_min" name="forward_min">
						<input type="hidden" id="forward_ordno" name="forward_ordno">
						<input type="hidden" id="forward_prodno" name="forward_prodno">
						
						<colgroup>
							<col style="width:15%" />
							<col style="width:30%" />
							<col style="width:15%" />
							<col style="*" />
						</colgroup>
						<tr>
							<th><span class="star">*</span> <spring:message code='epc.ord.period'/> </th>
							<td>
								<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
							</td>
							<th><span class="star">*</span> <spring:message code='epc.ord.strCdSelect'/></th>
							<td>
								<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='epc.ord.allStore'/>
								<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='epc.ord.strCdSelect'/>
							</td>
						</tr>
						<tr>
							<th><spring:message code='epc.ord.venCd'/></th>
							<td>
								<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" />
							</td>
							<th></th><td></td>
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
						<!-- div class="datagrade_nontitle_scroll" -->
						<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
						
						<table id="testTable1" class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<c:if test="${not empty orderList }">
						<input type="hidden" name="firstList" value="exist"/>
							<colgroup>
								<col style="width:12%" />
								<col style="width:19%" />
								<col style="width:7%" />
								<col style="width:12%" />
								<col style="width:12%" />
								<col style="width:12%"  />
								<col style="width:14%" />
								<col style="width:12%"  />
							</colgroup>
							
							<c:set var="tmp"  value="empty" />
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<c:if test="${list.ordSlipNo != tmp }">
									<tr>
										<th><spring:message code='epc.ord.ordDy'/></th>
										<th colspan="2"><spring:message code='epc.ord.ordSlipNo'/></th>
										<th><spring:message code='epc.ord.ordQty'/></th>
										<th><spring:message code='epc.ord.buyPrc'/></th>
										<th colspan="2"><spring:message code='epc.ord.ableDeleveryTime'/></th>
										<th><spring:message code='epc.ord.userHit'/></th>
									</tr>
									<tr  class="r1">
										<input type="hidden" id="ord_no" name="ord_no" value="<c:out value="${list.ordSlipNo }" />">
										<input type="hidden" id="ven" name="ven" value="<c:out value="${list.venCd }" />">
										<td align="center"><c:out value="${list.ordDy }" /></td>
										<td colspan="2" align="center"><c:out value="${list.ordSlipNo  }" /></td>
										<td align="right"><fmt:formatNumber value="${list.totQty }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.totPrc }" type="number" currencySymbol="" /></td>
										<td colspan="2" align="center" class="splyTm" data-ord-slip-no = "<c:out value="${list.ordSlipNo }" />"><input type="text" size="3" value="<c:out value="${list.hour }" />" id="hour" name="hour" maxlength="2" onkeyup="javascript:keyValid(this);"><spring:message code='epc.ord.hour'/><input type="text" size="3" value="<c:out value="${list.min }" />" id="min" name="min" maxlength="2" onkeyup="javascript:keyValid(this);"><spring:message code='epc.ord.min'/></td>
										<td align=center><c:out value="${list.userHit }" /></td>
								</tr>
								</c:if>
								<c:if test="${list.ordSlipNo != tmp }">
								<tr>
									<th><spring:message code='epc.ord.prodCd'/></th>
									<th><spring:message code='epc.ord.prodNm'/></th>
									<th><spring:message code='epc.ord.prodStd'/></th>
									<th><spring:message code='epc.ord.ordIpsu'/></th>
									<th><spring:message code='epc.ord.srcmkCd'/></th>
									<th><spring:message code='epc.ord.ordQty'/></th>
									<th><spring:message code='epc.ord.buyPrc'/></th>
									<th><spring:message code='epc.ord.splyAbleQty'/></th>
								</tr>
								</c:if>
								<tr  class="r1">
									<input type="hidden" id="prod_no" name="prod_no" value="<c:out value="${list.prodCd }" />">
									<td align=center><c:out value="${list.prodCd }" /></td>
									<td align="left">&nbsp; <c:out value="${list.prodNm }" /></td>
									<td align=center><c:out value="${list.ordUnit }" /></td>
									<td align="right"><fmt:formatNumber value="${list.ordIpsu }" type="number" currencySymbol="" /></td>
									<td align=center><c:out value="${list.ordSlipNo }" /></td>
									<input type="hidden" name="ordqty" value="<c:out value="${list.ordQty }" />"/>
									<td align="right"><fmt:formatNumber value="${list.ordQty }" type="number" currencySymbol="" /></td>
									<td align="right"><fmt:formatNumber value="${list.buyPrc }" type="number" currencySymbol="" /></td>
									<td align="center"><input type="text" size="5" value="<c:out value="${list.splyAbleQty }" />" id="sply_qty" name="sply_qty" maxlength="9" readonly="readonly" onkeyup="javascript:keyValid(this);"></td>
								</tr>
								<c:set var="tmp" value="${list.ordSlipNo }" />
							</c:forEach>
						</c:if>
						
						<c:if test="${empty orderList }">
							<input type="hidden" name="firstList" value="none"/>
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
						<li><spring:message code='epc.ord.orderRes'/></li>
						<li class="last"><spring:message code='epc.ord.ableDeleveryInfo'/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>
