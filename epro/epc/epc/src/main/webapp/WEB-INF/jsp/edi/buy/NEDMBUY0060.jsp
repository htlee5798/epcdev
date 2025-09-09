<%--
	Page Name 	: NEDMBUYO0060.jsp
	Description : 매입정보 점포상품별 조회 페이지 
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2015.11.17 		O YEUN KWON	 		최초생성
	
--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1">
		<td align="center" style="mso-number-format:'\@'"><c:out value="\${srcmkCd }"/></td>
		<td>&nbsp;<c:out value="\${prodNm }"/></td>
		<td align="center" style="mso-number-format:'\@'"><c:out value="\${prodStd }"/></td>
		<td align="center"><c:out value="\${ordIpsu }"/></td>
		<td align="center"><c:out value="\${danwi }"/></td>
		<td align="center"><c:out value="\${strNm }"/></td>
		<td align=center style="display:none;"><c:out value="\${strCd }"/></td>
		<td align="right"><c:out value="\${totQty }"/></td>
		<td align="right"><c:out value="\${totPrc }"/></td>
		<td align="center"><c:out value="\${buyRtnNm }"/></td>
		<td align="right"><c:out value="\${buyBoxQty }"/></td>
		<td align="right"><c:out value="\${buyQty }"/></td>
		<td align="right"><c:out value="\${buyBuyPrc }"/></td>
	</tr>
								
							
</script>
<script>
	var flag = false;
	$(document).ready(function($) {
		
		$("#searchForm #srchEntpCode").val("<c:out value='${param.srchEntpCode}'/>");	// 협력업체 선택값 세팅	
		$("#searchForm #srchBuying").val("<c:out value='${param.srchBuying}'/>");		// 매입구분 선택값 세팅
		
		<c:if test="${paramMap.isForwarding == 'Y' }">
			doSearch();
		</c:if>
		
	});
	
	/* 조회 */
	function doSearch() {
		goPage("1");
	}
	
	function _setTbodyMasterValue(json) { 
		var data = json.resultList;
		
		setTbodyInit("dataListbody");	// dataList 초기화			
		
		if (data.length > 0) {
			flag = true;
			
			var totPrc		= 0;
			var buyBuyPrc   = 0;
			
			// 금액 및 수량 comma 및 합계
			for (var i = 0; i < data.length; i++) {				
				
				var totPrc 		= data[i]["totPrc"];
				var buyBuyPrc 	= data[i]["buyBuyPrc"];
				
				data[i]["totPrc"] 		= setComma(totPrc);
				data[i]["buyBuyPrc"] 	= setComma(buyBuyPrc);
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging-ul").html(json.contents);
		} else {
			flag = false;
			setTbodyNoResult("dataListbody", 12);	
			$("#paging-ul").html("");
		}
				
	}

	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid() {
		var srchStartDate 	= $("#searchForm input[name='srchStartDate']").val();
		var srchEndDate 	= $("#searchForm input[name='srchEndDate']").val();
		
		var rangeDate = 0;

		if (srchStartDate == "" || srchEndDate == "") {
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			$("#srchStartDate").focus();
			return false;
		}

		// srchStartDate, srchEndDate 는 yyyy-mm-dd 형식
		srchStartDate 	= srchStartDate.substring(0, 4) + srchStartDate.substring(5, 7) + srchStartDate.substring(8, 10);
		srchEndDate 	= srchEndDate.substring(0, 4) + srchEndDate.substring(5, 7) + srchEndDate.substring(8, 10);
		//console.log(srchStartDate + "::" + srchEndDate);
		
		var intStartDate 	= parseInt(srchStartDate);
		var intEndDate 		= parseInt(srchEndDate);

		if (intStartDate > intEndDate) {
			alert("<spring:message code='msg.common.fail.calendar'/>");
			$("#srchStartDate").focus();
			return false;
		}

		intStartDate 	= new Date(srchStartDate.substring(0, 4), srchStartDate.substring(4, 6), srchStartDate.substring(6, 8), 0, 0, 0);
		intEndDate 		= new Date(srchEndDate.substring(0, 4), srchEndDate.substring(4, 6), srchEndDate.substring(6, 8), 0, 0, 0);

		rangeDate = Date.parse(intEndDate) - Date.parse(intStartDate);
		rangeDate = Math.ceil(rangeDate / 24 / 60 / 60 / 1000);

		if (rangeDate > 30) {
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			$("#srchStartDate").focus();
			return false;
		}

		return true;
	}

	function doExcel(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='srchEntpCode']").val();
		var buying  	= $("#searchForm select[name='srchBuying'] option:selected").text();

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
		bodyValue += "<CAPTION><spring:message code='text.buy.field.buyStrPrdList'/><br>";
		bodyValue += "[<spring:message code='text.buy.field.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='text.buy.field.strCd'/> : " + store + "]";
		bodyValue += "[<spring:message code='text.buy.field.prod'/> : " + product + "]";
		bodyValue += "[<spring:message code='text.buy.field.buyKind'/> : " + buying + "]";
		bodyValue += "[<spring:message code='text.buy.field.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		
		if (flag) {			
			bodyValue += tbody2.parent().html();			
		}

		//console.log(bodyValue);
		
		$("#searchForm input[id='staticTableBodyValue']").val(bodyValue);
		
		$("#searchForm input[id='name']").val("statics");
		$("#searchForm").attr("target", "_blank");
		$("#searchForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
		$("#searchForm").submit();
		
		$("#searchForm").attr("target", "");
		$("#searchForm").attr("action", "");
	}


	function storeClear(){
		$("#searchForm #storeVal").val("");
		$("#searchForm #storeName").val("");
	}
	function productClear(){
		$("#searchForm #productVal").val("");
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
		var vencd 		= $("#searchForm select[name='srchEntpCode']").val();
		// 매입구분[0:매입, 1:반품]
		var buying 		= $("#searchForm select[name='srchBuying']").val();
		
		
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
		
		if (buying == "") {
			buying = "전체"
		} else if (buying == "0") {
			buying = "매입"
		} else if (buying == "1") {
			buying = "반품"
		}		
		
		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.store'/> : " + store + "]"
		textData += "[<spring:message code='epc.ord.product'/> : " + product + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		textData += "[<spring:message code='text.buy.field.buyKind' /> : " + buying + "]"
		
		//console.log(textData);
		
		if (!dateValid()) {			
			return;
		}
		
		$("#searchForm input[name='textData']").val(textData);		
		$("#searchForm input[name='srchProductVal']").val($("#searchForm input[name='productVal']").val());
		$("#searchForm input[name='pageIndex']").val($("#searchForm input[name='pageIndex']").val());
		
		$("#searchForm").attr("action", "<c:url value='/edi/buy/NEDMBUY0060Text.do'/>");
		$("#searchForm").submit();
	}
	
	/* 페이징 처리를 위한 조회 함수 */
	function goPage(pageIndex) {
		//console.log("pageIndex ==" + pageIndex);
		
		$("#searchForm input[name='pageIndex']").val(pageIndex);
		
		var searchInfo = {};		
		
		searchInfo["srchStartDate"]		=	$("#searchForm input[name='srchStartDate']").val().replaceAll("-", "");		// 조회기간 from                                       
		searchInfo["srchEndDate"]		=	$("#searchForm input[name='srchEndDate']").val().replaceAll("-", "");       // 조회기간 to                                         
		searchInfo["srchStoreVal"]		=	storeValArrList($("#searchForm input[name='storeVal']").val());         	// 점포선택                                            
		searchInfo["srchEntpCode"]		=	$("#searchForm select[name='srchEntpCode']").val();                         // 협력업체코드                                          
		searchInfo["srchProductVal"]	=	$("#searchForm input[name='productVal']").val()                         	// 상품코드                                            
		searchInfo["srchBuying"]		=	$("#searchForm select[name='srchBuying']").val();                           // 매입구분[ex: 1:반품, 2:매입, 3:반품정정, 4:매입정정]
		searchInfo["recordCnt"]			=	$("#searchForm select[name='recordCnt']").val();                            // 한페이지에 보여줄 게시물 건수
		searchInfo["pageIndex"]			=	pageIndex;																	// 페이지 번호
		
		if (dateValid()) {
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/buy/NEDMBUY0060Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);			
				}
			});			
		}
	}	
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value='${param.widthSize }'/></c:if> >
		<div>
			<form name="searchForm"	id="searchForm" method="post" action="#">
			
			<input type="hidden" name="vendor" id="vendor" value="<c:out value='${paramMap.ven }'/>">
			<input type="hidden" name="storeName" id="storeName"  />			
			<input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue">
			<input type="hidden" name="name" id="name">		
			<input type="hidden" name="widthSize" id="widthSize" value="<c:out value='${param.widthSize }'/>" >			
			<input type="hidden" name="isForwarding" id="isForwarding" value="">
			<input type="hidden" name="textData"				id="textData"				/>
			<input type="hidden" name="srchProductVal"			id="srchProductVal"			/>
			<input type="hidden" name="pageIndex"				id="pageIndex"				/>
			 
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : srch -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"><spring:message code='text.buy.field.searchCondition' /></li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 								<a href="#" class="btn" onclick="doExcel('headTbl','listTbl');"><span><spring:message code="button.common.excel"/></span></a>
 								<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a> 
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" name="storeVal"   id="storeVal"   value="<c:out value='${param.storeVal }'/>" />
						<input type="hidden" name="productVal" id="productVal" value="<c:out value='${param.productVal }'/>"/>
						
						<colgroup>
							<col style="width:100px;" />
							<col style="width:230px;" />
							<col style="width:100px;" />
							<col style="width:160px;" />
							<col style="width:100px;" />
							<col style="width:90px;" />
						</colgroup>
						<tr>
							<th><span class="star">*</span> <spring:message code='text.buy.field.period' /> </th>
							<td>
								<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value='${paramMap.srchStartDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value='${paramMap.srchEndDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
							</td>
							<th><span class="star">*</span> <spring:message code='text.buy.field.strCdSelect' /></th>
							<td>
								<input type="Radio" name="strCdType" id="strCdTypeAll" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='text.buy.field.allStore' />
								<input type="Radio" name="strCdType" id="strCdTypeCho" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='text.buy.field.strCdSelect' />
							</td>						
							
							<th>게시물 건수</th>
							<td>
								<select id="recordCnt" name="recordCnt">
									<option value="1000">1000</option>
									<option value="2000">2000</option>
									<option value="3000">3000</option> 
								</select>
							</td>	
						</tr>
						<tr>
							<th><spring:message code='text.buy.field.venCd' /></th>
							<td>
								<html:codeTag objId="srchEntpCode" objName="srchEntpCode" width="150px;"  dataType="CP" comType="SELECT" formName="form" defName="전체" />
							</td>
							
							<th><spring:message code='text.buy.field.prodCd' /></th>
							<td>
								<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if> onclick="javascript:productClear();"/><spring:message code='text.buy.field.allProd' />
								<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/><spring:message code='text.buy.field.prodSelect' />
							</td>
							
							<th><spring:message code='text.buy.field.buyKind' /></th>
							<td >
									<html:codeTag objId="srchBuying" objName="srchBuying" comType="SELECT" formName="form"  parentCode="SPP01" defName="전체"></html:codeTag>
							
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
							<li class="tit"><spring:message code='text.buy.field.result' /></li>
						</ul>
			
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
						<colgroup>
							<col style="width:85px;" />
							<col />
							<col style="width:50px;" />
							<col style="width:40px;" />
							<col style="width:50px;" />
							<col style="width:80px;" />
							<col style="width:70px;" />
							<col style="width:50px;" />
							<col style="width:70px;" />
							<col style="width:50px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:17px;" />
						</colgroup>
						<tr>
							<th><spring:message code='epc.buy.header.srcmkCd' /></th>
							<th><spring:message code='epc.buy.header.prodNm' /></th>
							<th><spring:message code='epc.buy.header.prodStd' /></th>
							<th><spring:message code='epc.buy.header.ordIpsu' /></th>
							<th><spring:message code='epc.buy.header.danwi' /></th>
							<th><spring:message code='epc.buy.header.strNm' /></th>
							<th style="display:none;"><spring:message code='epc.buy.header.strCd' /></th>
							<th><spring:message code='epc.buy.header.totQty' /></th>
							<th><spring:message code='epc.buy.header.totPrc' /></th>
							<th><spring:message code='epc.buy.header.buyRtnNm' /></th>
							<th><spring:message code='epc.buy.header.buyBoxQty' /></th>
							<th><spring:message code='epc.buy.header.buyQty' /></th>
							<th><spring:message code='epc.buy.header.buyBuyPrc' /></th>
							<th>&nbsp;</th>
						</tr>
						</table>
						<div class="datagrade_scroll">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="listTbl">
						<colgroup>
							<col style="width:85px;" />
							<col />
							<col style="width:50px;" />
							<col style="width:40px;" />
							<col style="width:50px;" />
							<col style="width:80px;" />
							<col style="width:70px;" />
							<col style="width:50px;" />
							<col style="width:70px;" />
							<col style="width:50px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
						</colgroup>
						<!-- Data List Body Start ------------------------------------------------------------------------------>
						<tbody id="dataListbody" />
						<!-- Data List Body End   ------------------------------------------------------------------------------>
						</table>
						</div>
					</div>
				</div>
			</div>
			
			
			<!-- Pagging Start ---------->			
			<div id="paging_div">
		        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
			</div>
			<!-- Pagging End ---------->
			
			
			</form>
		</div>
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li><spring:message code='epc.buy.menu.lvl1' /></li>
						<li><spring:message code='epc.buy.menu.lvl2' /></li>
						<li><spring:message code='epc.buy.menu.lvl3' /></li>
						<li class="last"><spring:message code='epc.buy.menu.strPrd' /></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>


