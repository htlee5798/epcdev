<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%--
	Class Name : NEDMSAL0040.jsp
	Description : 발주정보 > 기간별 > 상품상세
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    -------------------------------------
	2015.11.13 		  최선길 		 최초생성
	
	author   : sun gil choi
	since    : 2015.11.13
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	$(function(){
		
		$("#searchForm #searchEntpCd").val('<c:out value="${paramMap.entp_cd}"/>');
		if('<c:out value ="${param.mode}" />' == 'select'){
			doSearch();
		}
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
		
		var nowHour	=	"<c:out value='${nowHour}'/>";
		if (nowHour.length > 0) {
			if (nowHour >= 4 && nowHour <= 6) {
				alert("<spring:message code='msg.select.notpermission.time'/> 까지 조회가 불가능합니다.");
				return;
			}
		}
		
		
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
		

		
		var tmpStart=$("#searchForm input[name='srchStartDate']").val().split("-");
		var tmpEnd=$("#searchForm input[name='srchEndDate']").val().split("-");
		
		if(tmpStart[0] != tmpEnd[0]){
			alert("<spring:message	code='epc.sal.alert.msg' />");
			return;
		}
		if(tmpStart[1] != tmpEnd[1]){
			alert("<spring:message	code='epc.sal.alert.msg' />");
			return;
		}
		
		if (dateValid()) {
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'html',
				async : false,
				url : '<c:url value="/edi/sale/NEDMSAL0040Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$('#resultWrap').html(data);
				}
			});
		}
	}

	/* _eventSearch() 후처리(data 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.saleList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			var total_qty = 0;	// 입수합계
			var total_amt = 0;	// 발주수량 합계
			   
			var saleQty = 0;
			var saleAmt = 0;
			
			// 금액 및 수량 comma 및 합계
			for (var i = 0; i < data.length; i++) {
				saleQty = parseFloat(data[i]["saleQty"]);
				saleAmt = data[i]["saleAmt"];
				
				// 합계
				total_qty 	+= parseFloat(saleQty);
				total_amt 	+= parseFloat(saleAmt.toFixed(2));
				
				// Comma 설정
				data[i]["saleQty"] 	= setComma(saleQty);
				data[i]["saleAmt"] 	= setComma(saleAmt);
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			
			$('#total_qty').text(setComma(total_qty));
			$('#total_amt').text(setComma(total_amt));
			
			$('#sumRow').show();
		} else {
			setTbodyNoResult("dataListbody", 9);
			$('#sumRow').hide();
			$('#emptyRow').show();
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


	function dateValid(){
		
		var startDate = $('#searchForm #srchStartDate').val();
		var endDate = $('#searchForm #srchEndDate').val();
		var rangeDate = 0;
		
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
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

	function popupSearch(tbName1, tbName2, tbName3){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');
		var tbody3 = $('#' + tbName3 + ' tbody');	

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();


		var tmp="";

		if(store==""){
			store="<spring:message	code='epc.sal.header.all' />";
		}
		if(product==""){
			product="<spring:message	code='epc.sal.header.all' />";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}

		
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message	code='epc.sal.dash.prodDetail' /><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[<spring:message code='epc.ord.product'/> : " + product + "]";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		bodyValue += tbody2.parent().html();
		bodyValue += tbody3.parent().html();
		$("#searchForm input[id='staticTableBodyValue']").val(bodyValue);
			
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


	function printText(){

		var nowHour	=	"<c:out value='${nowHour}'/>";
		if (nowHour.length > 0) {
			if (nowHour >= 4 && nowHour <= 6) {
				alert("<spring:message code='msg.select.notpermission.time'/> 까지 파일 생성이 불가능합니다.");
				return;
			}
		}
		
		if(dateValid()){
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
			
			if(store==""){
				store="<spring:message	code='epc.sal.header.all' />";
			}
			if(product==""){
				product="<spring:message	code='epc.sal.header.all' />";
			}
			
			var tmp = "";
			if(vencd==""){
				tmp=vendor;
			}else{
				tmp=vencd;
			}

			var textData = "";
			textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
			textData += "[<spring:message code='epc.ord.store'/> : " + store + "]"
			textData += "[<spring:message code='epc.ord.product'/> : " + product + "]"
			textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
			$("#searchForm input[name='textData']").val(textData);
			$("#searchForm input[name='searchProductVal']").val( $("#searchForm input[name='productVal']").val());
			
			$("#searchForm").attr("action", "<c:url value='/edi/sale/NEDMSAL0040Text.do'/>");
			$("#searchForm").submit();

		}	
	
	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" name="text_data" />
		<input type="hidden" name="textData" id="textData" />
		<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
		<input type="hidden" id="storeName" name="storeName" />
		<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >
		
		<input type="hidden" id="searchProductVal" name="searchProductVal" />
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message	code='epc.sal.search.condition' /><!-- <strong>&nbsp;<font color="red">※현재 매출 데이터 오류현상이 발생하고 있으니 참고하시기 바랍니다.</font></strong> --></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2','testTable3');"><span><spring:message code="button.common.excel"/></span></a> 
							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value="${param.storeVal }" />" />
					<input type="hidden" id="productVal" name="productVal" value="<c:out value="${param.productVal }" />" />
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message	code='epc.sal.search.searchPeriod' /> </th>
						<td>
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> <spring:message	code='epc.sal.search.allStore' /></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" /><spring:message	code='epc.sal.search.allStore' />
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message	code='epc.sal.search.storeSelect' />
						</td>
					</tr>
					<tr>
						<th><spring:message	code='epc.sal.search.entpCd' /></th>
						<td>
							<html:codeTag objId="searchEntpCd" objName="searchEntpCd" width="150px;" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<th><spring:message	code='epc.sal.search.prodCd' /></th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message	code='epc.sal.search.allProd' />   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/><spring:message	code='epc.sal.search.prodSelect' />
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list" id = "resultWrap">
					<ul class="tit">
						<li class="tit"><spring:message	code='epc.sal.search.result' /></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<colgroup>
							<col style="width:100px;" />
							<col style="width:60px;" />
							<col style="width:80px;" />
							<col />
							<col style="width:100px;" />
							<col style="width:100px;" />
							<col style="width:130px;" />
							<col style="width:17px;" />
						</colgroup>
						<tr>
							<th><spring:message	code='epc.sal.header.strNm' /></th>
							<th><spring:message	code='epc.sal.header.strCd' /></th>
							<th><spring:message	code='epc.sal.header.prodCd' /></th>
							<th><spring:message	code='epc.sal.header.prodNm' /></th>
							<th><spring:message	code='epc.sal.header.srcmkCd' /></th>
							<th><spring:message	code='epc.sal.header.saleQty' /></th>
							<th><spring:message	code='epc.sal.header.saleAmt' /></th>
							<th>&nbsp;</th>
						</tr>
					</table>
					<div class="datagrade_scroll_sum">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:100px;" />
								<col style="width:60px;" />
								<col style="width:80px;" />
								<col />
								<col style="width:100px;" />
								<col style="width:100px;" />
								<col style="width:130px;" />
							</colgroup>
							<c:if test="${not empty saleList }">
								<c:set var="tmp"  value="empty" />
								<c:set var="total_qty"  value="0" />
								<c:set var="total_amt"  value="0" />
								<c:set var="total_qty_sum"  value="0" />
								<c:set var="total_amt_sum"  value="0" />
								<c:forEach items="${saleList}" var="list" varStatus="index" >
									<c:if test="${list.strCd != tmp and index.count > 1}">
									<tr>
										<th colspan="5" class="fst" align=center>소계</th>
										<th align=right><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></th>
										<th align=right><fmt:formatNumber value="${total_amt }" type="number" currencySymbol="" /></th>
										<c:set var="total_qty" value="${0 }" />
										<c:set var="total_amt" value="${0 }" />
									</tr>
									</c:if>
									<tr class="r1">
										<td align="center"><c:out value="${list.strNm}" /></td>
										<td align="center"><c:out value="${list.strCd }" /></td>
										<td align="center" style="mso-number-format:'\@'"><c:out value="${list.prodCd }" /></td>
										<td align="left">&nbsp; <c:out value="${list.prodNm}" /></td>
										<td align="center" style="mso-number-format:'\@'"><c:out value="${list.srcmkCd }" /></td>
										<td align="right"><fmt:formatNumber value="${list.saleQty }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.saleSaleAmt }" type="number" currencySymbol="" /></td>
									</tr>
									
									<c:set var="total_qty" value="${total_qty + list.saleQty }" />
									<c:set var="total_amt" value="${total_amt + list.saleSaleAmt }" />
									<c:set var="total_qty_sum" value="${total_qty_sum + list.saleQty }" />
									<c:set var="total_amt_sum" value="${total_amt_sum + list.saleSaleAmt }" />
									
									<c:if test="${index.last }">
									<tr>
										<th colspan="5" class="fst" align=center><spring:message	code='epc.sal.header.summary' /></th>
										<th align=right><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></th>
										<th align=right><fmt:formatNumber value="${total_amt }" type="number" currencySymbol="" /></th>
									</tr>
									</c:if>
									
									<c:set var="tmp" value="${list.strCd }" />
								</c:forEach>
								
							</c:if>
							<c:if test="${empty saleList }">
								<tr><td colspan="7" align=center><spring:message	code='epc.ord.emptySearchResult' /></td></tr>
							</c:if>
						</table>
					</div>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
						<colgroup>
							<col style="width:100px;" />
							<col style="width:60px;" />
							<col style="width:80px;" />
							<col />
							<col style="width:100px;" />
							<col style="width:100px;" />
							<col style="width:130px;" />
							<col style="width:17px;" />
						</colgroup>
						<c:if test="${not empty saleList }">
						<tr class="r1">
							<th colspan="5" class="fst" align=center><b><spring:message	code='epc.sal.header.sum' /></b></th>
							<th align=right><b><fmt:formatNumber value="${total_qty_sum }" type="number" currencySymbol="" /></b></th>
							<th align=right><b><fmt:formatNumber value="${total_amt_sum }" type="number" currencySymbol="" /></b></th>
							<th>&nbsp;</th>
						</tr>
						</c:if>
						<c:if test="${empty saleList }"><tr><td colspan=7>&nbsp;</td><th width=17>&nbsp;</th></tr></c:if>
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
					<li><spring:message	code='epc.sal.home' /></li>
					<li><spring:message	code='epc.sal.salInfo' /></li>
					<li><spring:message	code='epc.sal.dateInfo' /></li>
					<li class="last"><spring:message	code='epc.sal.prodDetail' /></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
