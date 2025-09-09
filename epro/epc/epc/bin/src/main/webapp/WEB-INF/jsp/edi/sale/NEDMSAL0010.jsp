<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%--
	Class Name : NEDMSAL0010.jsp
	Description : 매출정보 > 기간별 매출정보 > 일자별
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    -------------------------------------
	2015.11.16 		  최선길 		 최초생성
	
	author   : sun gil choi
	since    : 2015.11.16
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	$(function() {
	
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
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='startDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='endDate']").val();
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='searchEntpCd']").val();
		// 상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();
//     이동빈 체크로직중 시작		
// 		var searchEntpCd =searchInfo["searchEntpCd"] ;
// 		var searchStoreAl =searchInfo["searchStoreAl"] ;
// 		var searchProductVal =searchInfo["searchProductVal"]  ;

// 		if (searchEntpCd == "" && searchStoreAl == "" && searchProductVal == "") {
		
// 			// 카운트 조회
// 			$.ajax({
// 				contentType : 'application/json; charset=utf-8',
// 				type : 'post',
// 				dataType : 'json',
// 				async : false,
// 				url : '<c:url value="/edi/sale/NEDMSAL0010SelectCount.json"/>',
// 				data : JSON.stringify(searchInfo),
// 				success : function(data) {								
// 					if (data.cntSum > parseInt(10000)) {
// 						alert("데이터가 많아 업체코드가 전체가 아닌 한개를 선택하세요.");
// 						return;
// 					} else {
// 						doSearch2(searchInfo);
// 					}
// 				}
// 			});
// 		} else {
// 			doSearch2(searchInfo);
// 		}
// 	}
	
// 	function doSearch2(searchInfo) {
//     이동빈 체크로직중 끝		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/sale/NEDMSAL0010Select.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {								
				//json 으로 호출된 결과값을 화면에 Setting 
				_setTbodyMasterValue(data);
			}
		});
	}
	
	function money_format(num) {
		var comma = new RegExp("([0-9])([0-9][0-9][0-9][,.])");
		var data = String(num).split(".");
		
		data[0] += ".";
		
		do {
			data[0] = data[0].replace(comma, "$1,$2");
		}
		while (comma.test(data[0]));
		
		if (data.length > 1)
			return data.join("");
		else
			return data[0].split(".")[0];
	}

	function numberWithCommas(n) {
		var parts = n.toString().split(".");
		return rtnValue = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1]: "");
	}
	
	/* _eventSearch() 후처리(data 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.saleList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			var total_qty = 0;	// 
			var total_amt = 0;	// 
			 
			var saleQty = 0;
			var saleAmt = 0;
			
			// 금액 및 수량 comma 및 합계
			for (var i = 0; i < data.length; i++) {
				saleQty = parseFloat(data[i]["saleQty"]);
				saleAmt = parseFloat(data[i]["saleAmt"]);
				
				// 합계
				total_qty 	+= parseFloat(saleQty);
				total_amt 	+= parseFloat(saleAmt);
				//console.log(money_format(total_qty) + "::" + money_format(total_amt));
				
				data[i]["total_qty"] = numberWithCommas(total_qty.toFixed(2));
				data[i]["total_amt"] = numberWithCommas(total_amt);
				// Comma 설정
				data[i]["saleQty"] 	= numberWithCommas(saleQty);
				data[i]["saleAmt"] 	= numberWithCommas(saleAmt);
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			
			//$('#sumRow').show();
		} else {
			setTbodyNoResult("dataListbody", 9);
			/* $('#sumRow').hide();
			$('#emptyRow').show(); */
		}
	}

	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}

	function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		


		var date 		= $("#searchForm input[name='startDate']").val() + "~" + $("#searchForm input[name='endDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();

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
		bodyValue += "<CAPTION><spring:message	code='epc.sal.dash.store' /><br>";
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

	function forwarding(val) {
		
		var nowHour	=	"<c:out value='${nowHour}'/>";
		if (nowHour.length > 0) {
			if (nowHour >= 4 && nowHour <= 6) {
				alert("<spring:message code='msg.select.notpermission.time'/> 까지 조회가 불가능합니다.");
				return;
			}
		}

		loadingMaskFixPos();
		$("#searchForm input[id='endDate']").val(val);
		$("#searchForm input[id='mode']").val("select");
		$("#searchForm").attr("action", "<c:url value='/edi/sale/NEDMSAL0020.do'/>");
		$("#searchForm").submit();	
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
		
		// 조회 시작일자
		var srchStartDate 	= $("#searchForm input[name='startDate']").val();
		
		// 조회 종료일자
		var srchEndDate 	= $("#searchForm input[name='endDate']").val();
		// 조회기간 설정
		//var date = srchEndDate;
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
			store="전체";
		}
		if(product==""){
			product="전체";
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
		$("#searchForm input[name='searchProductVal']").val($("#searchForm input[name='productVal']").val());
		$("#searchForm input[name='srchStartDate']").val(srchStartDate);
		$("#searchForm input[name='srchEndDate']").val(srchEndDate);
		
		$("#searchForm").attr("action", "<c:url value='/edi/sale/NEDMSAL0010Text.do'/>");
		$("#searchForm").submit();

	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1">    
		<td align="center"><a href="javascript:forwarding('<c:out value="\${stkDy}"/>');"><c:out value="\${stkDy}"/></a></td>
		<td align="right"><c:out value="\${saleQty}"/></td>	
		<td align="right"><c:out value="\${saleAmt}"/></td>
		<td align="right"><c:out value="\${total_qty}"/></td>
		<td align="right"><c:out value="\${total_amt}"/></td>
	</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap"
		<c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>>
		<div>
			<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post" action="#">
			<input type="hidden" id="mode" name="mode" />
			<input type="hidden" name="text_data" />
			<input type="hidden" name="textData" id ="textData" />
			<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
			<input type="hidden" id="storeName" name="storeName" />
			<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue"> 
			<input type="hidden" name="name"> 
			<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />">
			
			<input type="hidden" id="searchProductVal" name="searchProductVal" />
			<input type="hidden" id="srchStartDate" name="srchStartDate" />
			<input type="hidden" id="srchEndDate" name="srchEndDate" />
			
			
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message	code="epc.sal.search.condition" /><!-- <strong>&nbsp;<font color="red">※현재 매출 데이터 오류현상이 발생하고 있으니 참고하시기 바랍니다.</font></strong> --></li>
								<li class="btn">
									<a href="#" class="btn"	onclick="doSearch();"><span><spring:message	code="button.common.inquire" /></span></a>
									<a href="#" class="btn"	onclick="popupSearch('testTable1','testTable2');"><span><spring:message	code="button.common.excel" /></span></a> 
									<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">

								<input type="hidden" id="storeVal" name="storeVal" value="<c:out value="${param.storeVal }" />" />
								<input type="hidden" id="productVal" name="productVal" value="<c:out value="${param.productVal }" />" />

								<colgroup>
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 10%" />
									<col style="" />
								</colgroup>
								<tr>
									<th><span class="star">*</span> <spring:message	code="epc.sal.search.searchPeriod" /></th>
									<td>
										<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.srchStartDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
										~
										<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly 	value="${paramMap.srchEndDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
									</td>
									<th><span class="star">*</span> <spring:message	code="epc.sal.search.storeSelect" /></th>
									<td><input type="Radio" name="strCdType"
										<c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" /><spring:message	code="epc.sal.search.allStore" />
										<input type="Radio" name="strCdType"
										<c:if test="${not empty param.storeVal }"> Checked</c:if>
										onclick="javascript:storePopUp();" /><spring:message	code="epc.sal.search.storeSelect" /></td>
								</tr>
								<tr>
									<th><spring:message	code="epc.sal.search.entpCd" /></th>
									<td><html:codeTag objId="searchEntpCd" objName="searchEntpCd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP"
											comType="SELECT" formName="form"  defName="전체" />
									</td>
									<th><spring:message	code="epc.sal.search.prodCd" /></th>
									<td><input type="Radio" name="prodCdType"
										<c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message	code="epc.sal.search.allProd" />
										<input type="Radio" name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();" /><spring:message	code="epc.sal.search.prodSelect" />
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
								<li class="tit"><spring:message	code="epc.sal.search.result" /></li>
							</ul>
							<table class="bbs_list" cellpadding="0" cellspacing="0"
								border="0" id="testTable1">
								<colgroup>
									<col style="width: 120px;" />
									<col style="width: 180px;" />
									<col style="width: 180px;" />
									<col style="width: 180px;" />
									<col  />
									<col style="width: 17px;" />
								</colgroup>
								<tr>
									<th><spring:message	code="epc.sal.header.date" /></th>
									<th><spring:message	code="epc.sal.header.saleQty" /></th>
									<th><spring:message	code="epc.sal.header.saleAmt" /></th>
									<th><spring:message	code="epc.sal.header.total_qty" /></th>
									<th><spring:message	code="epc.sal.header.total_amt" /></th>
									<th>&nbsp;</th>
								</tr>
							</table>
							<div class="datagrade_scroll">
								<table class="bbs_list" cellpadding="0" cellspacing="0"
									border="0" id="testTable2">
									<colgroup>
										<col style="width: 120px;" />
										<col style="width: 180px;" />
										<col style="width: 180px;" />
										<col style="width: 180px;" />
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
						<li><spring:message	code="epc.sal.home" /></li>
						<li><spring:message	code="epc.sal.salInfo" /></li>
						<li><spring:message	code="epc.sal.dateInfo" /></li>
						<li class="last"><spring:message code="epc.sal.perDate" /></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>
