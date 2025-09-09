<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	function PopupWindow(pageName) {
		var cw=400;
		var ch=300;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	function doSearch() {
		var srchStartDate = $("#searchForm select[name='startDate_year']").val()+"-"+$("#searchForm select[name='startDate_month']").val();
		$('#srchStartDate').val(srchStartDate);
		
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= srchStartDate;
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			//url : '${ctx}/edi/product/test.json',
			url : '<c:url value="/edi/payment/NEDMPAY0061Select.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				
				//json 으로 호출된 결과값을 화면에 Setting 
				_setTbodyMasterValue(data);
			}
		});	
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.paymentList;
				
			setTbodyInit("dataListbody");	// dataList 초기화
			
			if (data.length > 0) {
				// 금액 및 수량 comma 및 합계
				for (var i = 0; i < data.length; i++) {
					var totBuyAmt = 0;
					var newProdPromo = 0;
					var standPromoAmtAuto = 0;
					var overPromoAmtAuto = 0;
					var newProdPromoAmtAuto = 0;
					var newProdPromoAmtMan = 0;
					var standPromoAmtMan = 0;
					var overPromoAmtMan = 0;
					var newProdPromoAmtMan= 0; 
					var sum = 0;
					
					var totBuyAmt = data[i]["totBuyAmt"];
					var newProdPromo = data[i]["newProdPromo"];
					var standPromoAmtAuto =data[i]["standPromoAmtAuto"];
					var overPromoAmtAuto = data[i]["overPromoAmtAuto"];
					var newProdPromoAmtAuto = data[i]["newProdPromoAmtAuto"];
					var newProdPromoAmtMan = data[i]["newProdPromoAmtMan"];
					var standPromoAmtMan = data[i]["standPromoAmtMan"];
					var overPromoAmtMan = data[i]["overPromoAmtMan"];
					var newProdPromoAmtMan= data[i]["newProdPromoAmtMan"]; 
					var sum = data[i]["sum"];
					// Comma 설정
					data[i]["totBuyAmt"]= setComma(totBuyAmt);
					data[i]["newProdPromo"]= setComma(newProdPromo);
					data[i]["standPromoAmtAuto"]= setComma(standPromoAmtAuto);
					data[i]["overPromoAmtAuto"]= setComma(overPromoAmtAuto);
					data[i]["newProdPromoAmtAuto"]= setComma(newProdPromoAmtAuto);
					data[i]["newProdPromoAmtMan"]= setComma(newProdPromoAmtMan);
					data[i]["standPromoAmtMan"]= setComma(standPromoAmtMan);
					data[i]["overPromoAmtMan"]= setComma(overPromoAmtMan);
					data[i]["newProdPromoAmtMan"]= setComma(newProdPromoAmtMan); 
					data[i]["sum"]= setComma(sum);
				}
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
				
			} else { 
				setTbodyNoResult("dataListbody", 9);
			}
		}
	}
	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}



	function popupSearch(tbName1, tbName2){		
		

		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		

		var srchStartDate = $("#searchForm select[name='startDate_year']").val()+"-"+$("#searchForm select[name='startDate_month']").val();
		$('#srchStartDate').val(srchStartDate);
		var date=srchStartDate;
		var store=$("#searchForm input[name='storeName']").val();

		// 협력업체 코드(배열)
		var vendor 		= $("#searchForm input[name='vendor']").val();
		// 협력업체 코드(단독)
		var vencd 		= $("#searchForm select[name='entp_cd']").val();

		var tmp="";

		if(store==""){
			store="전체";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	

	
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message	code='epc.pay.dash.incentive' /><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
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

	/* 점포선택 초기화 */
	function storeClear() {
		$("#searchForm input[id='storeVal']").val("");
	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center" style="display:none;"><c:out value="\${strCd}"/></td>
	<td align="center" ><c:out value="\${strNm}"/></td>
	<td align=right><c:out value="\${totBuyAmt}"/></td>
	<td align=right><c:out value="\${newProdPromo}"/></td>
	<td align=right><c:out value="\${standPromoAmtAuto}"/></td>
	<td align=right><c:out value="\${overPromoAmtAuto}"/></td>
	<td align=right><c:out value="\${newProdPromoAmtAuto}"/></td>
	<td align=right><c:out value="\${newProdPromoAmtMan}"/></td>
	<td align=right><c:out value="\${standPromoAmtMan}"/></td>
	<td align=right><c:out value="\${overPromoAmtMan}"/></td>
	<td align=right><c:out value="\${newProdPromoAmtMan}"/></td>
	<td align=right><c:out value="\${sum}"/></td>
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
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.pay.search.condition'/> &nbsp;<font color="blue"><spring:message code='epc.pay.search.newPage'/></font></li>
		
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value="${param.storeVal }" />" />
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='epc.pay.search.searchPeriod'/> </th>
						<td>
							<html:codeTag objId="startDate_year" objName="startDate_year" selectParam="<c:out value='${paramMap.startDate_year}'/>" dataType="YEAR" subCode="5" comType="SELECT" formName="form"  />
							<html:codeTag objId="startDate_month" objName="startDate_month" selectParam="<c:out value='${paramMap.startDate_month}'/>" dataType="MONTH" comType="SELECT" formName="form"  />
						
							<input type="hidden" class="day" id="srchStartDate" name="srchStartDate">
						 
						</td>
						<th><span class="star">*</span> <spring:message code='epc.pay.search.storeSelect'/></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" /><spring:message code='epc.pay.search.allStore'/>
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='epc.pay.search.storeSelect'/>
						</td>
					</tr>
					<tr>
						<th><spring:message code='epc.pay.search.entpCd'/></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
							
						<th><span class="star"></span>
						<td></td>
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
						<li class="tit"><spring:message code='epc.pay.search.result'/></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:90px;" />
					
						<col/>
					</colgroup>
					<tr>
						<th rowspan="2" style="display:none;">
						<spring:message code='epc.pay.header.strCd'/>
						</th>
						<th rowspan="2"><spring:message code='epc.pay.header.strNm'/></th>
						<th rowspan="2"><spring:message code='epc.pay.header.totBuyAmt'/></th>
						<th colspan="3"><spring:message code='epc.pay.header.incentive'/></th>
						<th rowspan="2"><spring:message code='epc.pay.header.ge'/></th>
						<th colspan="3"><spring:message code='epc.pay.header.incentiveControl'/></th>
						<th rowspan="2"><spring:message code='epc.pay.header.ge'/></th>
						<th rowspan="2"><spring:message code='epc.pay.header.totalSum'/></th>
					</tr>
					<tr>
						<th><spring:message code='epc.pay.header.newProdPromoAmtAuto'/></th>
						<th><spring:message code='epc.pay.header.newProdPromoAmtMan'/></th>
						<th><spring:message code='epc.pay.header.standPromoAmtMan'/></th>
						<th><spring:message code='epc.pay.header.newProdPromoAmtAuto'/></th>
						<th><spring:message code='epc.pay.header.newProdPromoAmtMan'/></th>
						<th><spring:message code='epc.pay.header.standPromoAmtMan'/></th>
					</tr>
					</table>
					
					<div style="width:100%; height:395px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:80px;" />
								<col style="width:80px;" />
								<col style="width:70px;" />
								<col style="width:70px;" />
								<col style="width:70px;" />
								<col style="width:70px;" />
								<col style="width:70px;" />
								<col style="width:80px;" />
								<col style="width:70px;" />
								<col style="width:80px;" />
								<col style="width:70px;" />
								<col/>
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
					<li><spring:message code='epc.pay.home'/></li>
					<li><spring:message code='epc.pay.salInfo'/></li>
					<li><spring:message code='epc.pay.dateInfo'/></li>
					<li class="last"><spring:message code='epc.pay.saleIncentive'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
