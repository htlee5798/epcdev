<%--
	Page Name 	: NEDMINVO0020.jsp
	Description : 재고정보 현재고(상품) 조회 페이지 
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2015.11.18 		O YEUN KWON	 		최초생성
	
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
		<td rowspan="2" align="center" style="mso-number-format:'\@'"><c:out value="\${srcmkCd }"/></td>
		<td rowspan="2" align="center" style="mso-number-format:'\@'"><c:out value="\${prodCd }"/></td>
		<td rowspan="2" align="center"><c:out value="\${prodNm }"/></td>
		<td align="center"><spring:message	code="epc.inv.header.qty" /></td>
		<td align="right"><c:out value="\${bookFwdQty }"/></td>
		<td align="right"><c:out value="\${buyQty }"/></td>
		<td align="right"><c:out value="\${rtnQty }"/></td>
		<td align="right"><c:out value="\${strioQty }"/></td>
		<td></td>
		<td align="right"><c:out value="\${saleQty }"/></td>
		<td align="right"><c:out value="\${finalQty }"/></td>
	</tr>
	<tr class="r1">
		<td align="center"><spring:message	code="epc.inv.header.amt" /></td>
		<td align="right"><c:out value="\${bookFwdSale }"/></td>
		<td align="right"><c:out value="\${buySaleAmt }"/></td>
		<td align="right"><c:out value="\${rtnSaleAmt }"/></td>
		<td align="right"><c:out value="\${strioSaleAmt }"/></td>
		<td align="right"><c:out value="\${salePrcUpdownAmt }"/></td>
		<td align="right"><c:out value="\${saleSaleAmt }"/></td>
		<td align="right"><c:out value="\${finalAmt }"/></td>
	</tr>
</script>
<script>

	$(document).ready(function($) {
		
		<c:if test="${param.isForwarding == 'Y' }">	
			$("#searchForm select[name='srchEntpCode']").val("<c:out value='${param.srchEntpCode}' />");
			doSearch();
		</c:if>
	});


	
	function doSearch() {
		
		var nowHour	=	"<c:out value='${nowHour}'/>";
		if (nowHour.length > 0) {
			if (nowHour >= 4 && nowHour <= 8) {
				alert("<spring:message code='msg.select.notpermission.time'/> 까지 조회가 불가능합니다.");
				return;
			}
		}
		
		var searchInfo = {};
		
		searchInfo["srchStartDate"]		=	$("#searchForm input[name='srchStartDate']").val().replaceAll("-", "");		// 조회기간 from                                       
		//searchInfo["srchEndDate"]		=	$("#searchForm input[name='srchEndDate']").val().replaceAll("-", "");       // 조회기간 to
		searchInfo["srchEndDate"]		=	"";
		//searchInfo["srchStkMm"]			=	$("#searchForm input[name='srchEndDate']").val().replaceAll("-", "").substring(0, 6);
		searchInfo["srchStkMm"]			=	$("#searchForm input[name='srchStartDate']").val().replaceAll("-", "").substring(0, 6);
		searchInfo["srchStoreVal"]		=	storeValArrList($("#searchForm input[name='storeVal']").val());         	// 점포선택                                            
		searchInfo["srchEntpCode"]		=	$("#searchForm select[name='srchEntpCode']").val();                         // 협력업체코드                                          
		searchInfo["srchProductVal"]	=	$("#searchForm input[name='productVal']").val();                         	// 상품코드                                            
		/* if($("#searchForm input[name='srchStartDate']").val().replaceAll("-", "").substring(0, 6) != $("#searchForm input[name='srchEndDate']").val().replaceAll("-", "").substring(0, 6)){
			alert("동일한 년월로 조회하세요.");
			return;
		} */
		
		//if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/inventory/NEDMINV0020Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);			
				}
			});					
		//}	
			
	}

	function _setTbodyMasterValue(json) { 
		var data = json.resultList;
		setTbodyInit("dataListbody");	// dataList 초기화			
		
		if (data.length > 0) {
			
			for (var i = 0; i < data.length; i++) {
			
				var nowStkQty = 0;
				var nowStkAmt = 0;
			
				nowStkQty = parseInt(data[i]["bookFwdQty"]) + parseInt(data[i]["buyQty"]) - parseInt(data[i]["rtnQty"]) + parseInt(data[i]["strioQty"]) - parseInt(data[i]["saleQty"]) - parseInt(data[i]["stkAdjQty"]);
				nowStkAmt = parseInt(data[i]["bookFwdSale"])  + parseInt(data[i]["buySaleAmt"]) - parseInt(data[i]["rtnSaleAmt"]) + parseInt(data[i]["strioSaleAmt"]) + parseInt(data[i]["salePrcUpdownAmt"]) - parseInt(data[i]["saleSaleAmt"]) - parseInt(data[i]["stkAdjSaleAmt"]);
				
				data[i]["nowStkQty"] = setComma(nowStkQty);
				data[i]["nowStkAmt"] = setComma(nowStkAmt);
				
				data[i]["bookFwdQty"] = setComma(data[i]["bookFwdQty"]);
				data[i]["buyQty"] = setComma(data[i]["buyQty"]);
				data[i]["rtnQty"] = setComma(data[i]["rtnQty"]);
				data[i]["strioQty"] = setComma(data[i]["strioQty"]);
				data[i]["saleQty"] = setComma(data[i]["saleQty"]);
				data[i]["bookFwdSale"] = setComma(data[i]["bookFwdSale"]);
				data[i]["buySaleAmt"] = setComma(data[i]["buySaleAmt"]);
				data[i]["rtnSaleAmt"] = setComma(data[i]["rtnSaleAmt"]);
				data[i]["strioSaleAmt"] = setComma(data[i]["strioSaleAmt"]);
				data[i]["salePrcUpdownAmt"] = setComma(data[i]["salePrcUpdownAmt"]);
				data[i]["saleSaleAmt"] = setComma(data[i]["saleSaleAmt"]);
				data[i]["finalAmt"] = setComma(data[i]["finalAmt"]);
				data[i]["finalQty"] = setComma(data[i]["finalQty"]);
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");			
		} else {
			setTbodyNoResult("dataListbody", 11);	
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

		//var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var date 		= $("#searchForm input[name='srchStartDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='srchEntpCode']").val();

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
		bodyValue += "<CAPTION><spring:message code='text.inv.field.invPrdCdList'/><br>";
		bodyValue += "[<spring:message code='text.buy.field.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='text.buy.field.strCd'/> : " + store + "]";
		bodyValue += "[<spring:message code='text.buy.field.prod'/> : " + product + "]";
		bodyValue += "[<spring:message code='text.buy.field.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		bodyValue += tbody2.parent().html();
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

	function printText(){
		
		var nowHour	=	"<c:out value='${nowHour}'/>";
		if (nowHour.length > 0) {
			if (nowHour >= 4 && nowHour <= 6) {
				alert("<spring:message code='msg.select.notpermission.time'/> 까지 조회가 불가능합니다.");
				return;
			}
		}
		
		//var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var date 		= $("#searchForm input[name='srchStartDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='srchEntpCode']").val();

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

		var textData = "";
		textData += "[<spring:message code='text.inv.field.period'/> : " + date + "]"
		textData += "[<spring:message code='text.inv.field.strCd'/> : " + store + "]"
		textData += "[<spring:message code='text.inv.field.prod'/> : " + product + "]"
		textData += "[<spring:message code='text.inv.field.vendor'/> : " + tmp + "]"

		$("#searchForm input[name='textData']").val(textData);
		$("#searchForm input[name='srchProductVal']").val($("#searchForm input[name='productVal']").val());
		//$("#searchForm input[name='srchStkMm']").val($("#searchForm input[name='srchEndDate']").val().replaceAll("-", "").substring(0, 6));
		$("#searchForm input[name='srchStkMm']").val($("#searchForm input[name='srchStartDate']").val().replaceAll("-", "").substring(0, 6));
		
		//if(dateValid()){
			$("#searchForm").attr("action", "<c:url value='/edi/inventory/NEDMINV0020Text.do'/>");
			$("#searchForm").submit();
		//}



	}
	
	function printText2(){
		
		if($("#searchForm select[name='srchEntpCode']").val() == ""){
			alert("<spring:message code='msg.inv.error.srchEntpCode' />");
			return;
		}
		
		if (!confirm("<spring:message code='msg.inv.error.more1min' />")) {
            return;
        }

		//var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var date 		= $("#searchForm input[name='srchStartDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='srchEntpCode']").val();

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

		var textData = "";
		textData += "[<spring:message code='text.inv.field.period'/> : " + date + "]"
		textData += "[<spring:message code='text.inv.field.strCd'/> : " + store + "]"
		textData += "[<spring:message code='text.inv.field.prod'/> : " + product + "]"
		textData += "[<spring:message code='text.inv.field.vendor'/> : " + tmp + "]"

		$("#searchForm input[name='textData']").val(textData);
		//$("#searchForm input[name='srchStkMm']").val($("#searchForm input[name='srchEndDate']").val().replaceAll("-", "").substring(0, 6));
		$("#searchForm input[name='srchStkMm']").val($("#searchForm input[name='srchStartDate']").val().replaceAll("-", "").substring(0, 6));
		$("#searchForm input[name='srchProductVal']").val($("#searchForm input[name='productVal']").val());
		
		//if(dateValid()){
			$("#searchForm").attr("action", "<c:url value='/edi/inventory/NEDMINV0020Text2.do'/>");
			$("#searchForm").submit();

		//}	
		

	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value='${param.widthSize }'/></c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm"	id="searchForm" method="post" action="#">
		<input type="hidden" name="textData" id="textData" />
		<input type="hidden" name="vendor" id="vendor" value="<c:out value='${paramMap.ven }'/>">
		<input type="hidden" name="storeName" id="storeName"  />
			
		<input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue">
		<input type="hidden" name="name" 	  id="name">
		<input type="hidden" name="srchStkMm" id="srchStkMm">
		<input type="hidden" name="widthSize" id="widthSize" value="<c:out value='${param.widthSize }'/>" >
		
		<input type="hidden" name="srchProductVal" id="srchProductVal" />
				
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message	code="text.inv.field.searchCondition" /></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="doExcel('headTbl','listTbl');"><span><spring:message code="button.common.excel"/></span></a> 
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a>
 							<a href="#" class="btn" onclick="javascript:printText2();"><span>점포별 텍스트파일</span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<input type="hidden" name="storeVal"   id="storeVal"   value="<c:out value='${param.storeVal }'/>" />
						<input type="hidden" name="productVal" id="productVal" value="<c:out value='${param.productVal }'/>"/>
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message	code="text.inv.field.period" /> </th>
						<td>
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value='${paramMap.srchEndDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');"  style="cursor:hand;" />
							<%-- ~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value='${paramMap.srchEndDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" /> --%>
						</td>
						<th><span class="star">*</span> <spring:message	code="text.inv.field.strCdSelect" /> </th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message	code="text.inv.field.allStore" />
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code="text.inv.field.strCdSelect" />
						</td>
					</tr>
					<tr>
						<th><spring:message	code="text.inv.field.venCd" /></th>
						<td>
							<html:codeTag objId="srchEntpCode" objName="srchEntpCode" width="150px;" selectParam="<c:out value='${param.srchEntpCode}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<th><spring:message	code="text.inv.field.prodCd" /></th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message code="text.inv.field.allProd" />   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/><spring:message code="text.inv.field.prodSelect" />
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
						<li class="tit"><spring:message code="text.inv.field.result" /></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:20px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th rowspan="2"><spring:message code="epc.inv.header.srcmkCd" /></th>
						<th rowspan="2"><spring:message code="epc.inv.header.prodCd" /></th>
						<th rowspan="2"><spring:message code="epc.inv.header.prodNm" /></th>
						<th rowspan="2" colspan="2"><spring:message code="epc.inv.header.bookFwdQty" /></th>
						<th colspan="4"><spring:message code="epc.inv.header.totalQty" /></th>
						<th rowspan="2"><spring:message code="epc.inv.header.sale" /></th>
						<th rowspan="2"><spring:message code="epc.inv.header.skt" /></th>
						<th rowspan="2">&nbsp;</th>
					</tr>
					<tr>
						<th><spring:message code="epc.inv.header.buyQty" /></th>
						<th><spring:message code="epc.inv.header.rtnQty" /></th>
						<th><spring:message code="epc.inv.header.strioQty" /></th>
						<th><spring:message code="epc.inv.header.saleSaleAmt" /></th>
					</tr>
					</table>
					<div class="datagrade_scroll_sum">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="listTbl">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:20px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col  />
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
					<li><spring:message	code="epc.inv.menu.lvl1" /></li>
					<li><spring:message	code="epc.inv.menu.lvl2" /></li>
					<li><spring:message	code="epc.inv.menu.lvl3" /></li>
					<li class="last"><spring:message code="epc.inv.menu.prdCd" /></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>