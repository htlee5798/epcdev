	<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	$(function(){
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

	function doSearch() { // 
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
		searchInfo["searchSrcmkVal"] 	= $("#searchForm input[name='searchSrcmkVal']").val();
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/usply/NEDMUSP0040Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});	
		}
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.usplyList;
			var tempData = [];
			
			for(var i = 0;i<data.length;i++){
				tempData[i] =  data[i];
				
			}
				
			setTbodyInit("dataListbody");	// dataList 초기화
			
			if (data.length > 0) {
				var ordIpsu 	=0;
				var ordQty 	=0;
				var ordBuyAmt 	=0;
				var buyQty 	=0;
				var buyBuyAmt 	=0;
				var usplyQty 	=0;
				var usplyBuyAmt	=0;
				
				
				
				// 금액 및 수량 comma 및 합계
				for (var i = 0; i < data.length; i++) {
					ordIpsu 	  = data[i]["ordIpsu"];
					ordQty 	  = data[i]["ordQty"];
					ordBuyAmt 	  = data[i]["ordBuyAmt"];
					buyQty 	  = data[i]["buyQty"];
					buyBuyAmt 	  = data[i]["buyBuyAmt"];
					usplyQty 	     = data[i]["usplyQty"];
					usplyBuyAmt     = data[i]["usplyBuyAmt"];
					
					// Comma 설정
					data[i]["ordIpsu"] 	= setComma(ordIpsu);
					data[i]["ordQty"] 	= setComma(ordQty);
					data[i]["ordBuyAmt"] 	= setComma(ordBuyAmt);
					data[i]["buyQty"] 	= setComma(buyQty);
					data[i]["buyBuyAmt"] 	= setComma(buyBuyAmt);
					data[i]["usplyQty"] 	= setComma(usplyQty);
					data[i]["usplyBuyAmt"] 	= setComma(usplyBuyAmt);
				}
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
			} else { 
				setTbodyNoResult("dataListbody", 9);
			}
		}
		return;	
	}


	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(){

		var startDate = $("#searchForm input[name='srchStartDate']").val();
		var endDate = $("#searchForm input[name='srchEndDate']").val();
		
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

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();


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

		var bodyValue = "";
		bodyValue += "<CAPTION>미납정보 상품상세별 현황표<br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[<spring:message code='epc.ord.product'/> : " + product + "]";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		bodyValue += tbody2.parent().html();
		$("#searchForm input[id='staticTableBodyValue']").val(bodyValue);
			
		$("#searchForm input[id='name']").val("statics");
		$("#searchForm").attr("target", "_blank");
		$("#searchForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
		$("#searchForm").submit();
		
		$("#searchForm").attr("target", "");
		$("#searchForm").attr("action", "");
	}	

	function storeClear(){
		$("#searchForm input[id='storeVal']").val("");
		$("#searchForm input[id='storeName']").val("");
	}
	function productClear(){
		$("#searchForm input[id='productVal']").val("");
		$("#searchForm input[id='searchSrcmkVal']").val("");
	}
	// 숫자에 콤마 추가하기 (금액단위)
	function setComma(n) {
	    var reg = /(^[+-]?\d+)(\d{3})/;
	    n += "";
	    while (reg.test(n)) n = n.replace(reg, "$1" + "," + "$2");
	    return n;
	}
	
	/* txt파일 생성 */
	function printText() {
		if (!dateValid()) {			
			return;
		}
		
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
		var vencd 		= $("#searchForm select[name='entp_cd']").val();
		
		
		if (store == "") {
			store = "전체";
		}
		
		if (product == "") {
			product = "전체";
		}
		
		var tmp = "";
		if (vencd == "") {
			tmp = "전체";
		} else {
			tmp = vencd;
		}
		
		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.store'/> : " + store + "]"
		textData += "[<spring:message code='epc.ord.product'/> : " + product + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		
		//console.log(textData);
		
		$("#searchForm input[name='textData']").val(textData);		
		$("#searchForm input[name='searchProductVal']").val($("#searchForm input[name='productVal']").val());		
		$("#searchForm input[name='searchEntpCd']").val($("#searchForm select[name='entp_cd']").val());
		
		$("#searchForm").attr("action", "<c:url value='/edi/usply/NEDMUSP0040Text.do'/>");
		$("#searchForm").submit();		
	}
	
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center" style="mso-number-format:'\@'"><c:out value="\${srcmkCd }"/></td>
	<td >&nbsp;<c:out value="\${prodNm }"/></td>
	<td align="right"><c:out value="\${ordIpsu }"/></td>
	<td align="center"><c:out value="\${ordUnit }"/></td>
	<td >&nbsp;<c:out value="\${strNm }"/></td>
	<td align=center style="display:none;"><c:out value="\${strCd }"/></td>
	<td align="right"><c:out value="\${ordQty }"/></td>
	<td align="right"><c:out value="\${ordBuyAmt }"/></td>
	<td align="right"><c:out value="\${buyQty }"/></td>
	<td align="right"><c:out value="\${buyBuyAmt }"/></td>
	<td align="right"><c:out value="\${usplyQty }"/></td>
	<td align="right"><c:out value="\${usplyBuyAmt }"/></td>
</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		
		<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
		<input type="hidden" id="storeName" name="storeName" />		
		<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
		<input type="hidden" name="name">		
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >
		<input type="hidden"	name="textData"				id="textData"				/>
		<input type="hidden"	name="searchProductVal"		id="searchProductVal"		/>
		<input type="hidden"	name="searchEntpCd"			id="searchEntpCd"			/>
				
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.usp.search.condition'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a>
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value="${param.storeVal }" />" />
					<input type="hidden" id="productVal" name="productVal" value="<c:out value="${param.productVal }" />" />
					<input type="hidden" id="searchSrcmkVal" name="searchSrcmkVal" value="<c:out value="${param.searchSrcmkVal }" />"/>
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='epc.usp.search.searchPeriod'/> </th>
						<td>
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.srchStartDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.srchEndDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> <spring:message code='epc.usp.search.storeSelect'/></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='epc.usp.search.allStore'/>
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='epc.usp.search.storeSelect'/>
						</td>
					</tr>
					<tr>
						<th><spring:message code='epc.usp.search.entpCd'/></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<th><spring:message code='epc.usp.search.prodCd'/></th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message code='epc.usp.search.allProd'/>   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productClear();productPopUp();"/><spring:message code='epc.usp.search.prodSelect'/>
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
						<li class="tit"><spring:message code='epc.usp.search.result'/></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:100px;" />
						<col  />
						<col style="width:30px;" />
						<col style="width:40px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:90px;" />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th><spring:message code='epc.usp.header.prodCd'/></th>
						<th><spring:message code='epc.usp.header.prodNm'/></th>
						<th><spring:message code='epc.usp.header.ordIpsu'/></th>
						<th><spring:message code='epc.usp.header.unit'/></th>
						<th><spring:message code='epc.usp.header.strNm'/></th>
						<th style="display:none;"><spring:message code='epc.usp.header.strCd'/></th>
						<th><spring:message code='epc.usp.header.ordQty'/></th>
						<th><spring:message code='epc.usp.header.ordBuyAmt'/></th>
						<th><spring:message code='epc.usp.header.buyQty'/></th>
						<th><spring:message code='epc.usp.header.buyBuyAmt'/></th>
						<th><spring:message code='epc.usp.header.usplyQty'/></th>
						<th><spring:message code='epc.usp.header.usplyBuyAmt'/></th>
						<th>&nbsp;</th>
					</tr>
					</table>
					<div class="datagrade_scroll">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:100px;" />
								<col  />
								<col style="width:30px;" />
								<col style="width:40px;" />
								<col style="width:70px;" />
								<col style="width:60px;" />
								<col style="width:70px;" />
								<col style="width:60px;" />
								<col style="width:70px;" />
								<col style="width:60px;" />
								<col style="width:90px;" />
							</colgroup>
							<!-- Data List Body Start ------------------------------------------------------------------------------>
							<tbody id="dataListbody" />
							<!-- Data List Body End   ------------------------------------------------------------------------------>
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
					<li><spring:message code='epc.usp.home'/></li>
					<li><spring:message code='epc.usp.usplyInfo'/></li>
					<li><spring:message code='epc.usp.dateInfo'/></li>
					<li class="last"><spring:message code='epc.usp.prodDetail'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
