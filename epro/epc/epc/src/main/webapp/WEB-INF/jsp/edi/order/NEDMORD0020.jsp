<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%--
	Class Name : NEDMORD0020.jsp
	Description : 발주정보 > 기간별 > 전표별
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    -------------------------------------
	2015.11.13 		 최선길		 최초생성
	
	author   : sun gil choi
	since    : 2015.11.13
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	$(document).ready(function() {
		if('<c:out value ="${param.mode}" />' == 'select'){
			$("#searchForm select[name='searchEntpCd']").val("<c:out value='${param.searchEntpCd}' />");
			doSearch();
		}
	});

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
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/order/NEDMORD0020Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
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
	
	/* _eventSearch() 후처리(data  객체 그리기) */
	function _setTbodyMasterValue(json) { 
		var data = json.orderList;
		var tempData = [];
		
		for(var i = 0;i<data.length;i++){
			tempData[i] =  data[i];
			
		}
			
		setTbodyInit("dataListbody");	// dataList 초기화
		
		if (data.length > 0) {
			var totalTotQty = 0;
			var totalTotPrc = 0;
			
			var totQty = 0;
			var totPrc = 0;
			// 금액 및 수량 comma 및 합계
			for (var i = 0; i < data.length; i++) {
				totQty = data[i]["totQty"];
				totPrc = data[i]["totPrc"];
				
				// 합계
				totalTotQty 	+= parseInt(totQty);
				totalTotPrc 	+= parseInt(totPrc);
				
				// Comma 설정
				data[i]["totQty"] 	= setComma(totQty);
				data[i]["totPrc"] 	= setComma(totPrc);
			}
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
			
			$('#totalTotQty').text(setComma(totalTotQty));
			$('#totalTotPrc').text(setComma(totalTotPrc));
			
			$('#sumRow').show();
		} else { 
			setTbodyNoResult("dataListbody", 9);
			$('#sumRow').hide();
			$('#emptyRow').show();
		}
				
	}

	/* Excel */
	function doExcel(tbName1, tbName2){		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();

		var tmp = "";

		if (store == "") {
			store="<spring:message code='epc.ord.all'/>";
		}
		
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}

		var ordering="";
		ordering = $("#searchForm select[name='searchOrdering'] option:selected").text(); 
		
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.ord.orderDashBoardPerordSlipNo'/><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[<spring:message code='epc.ord.ordering'/> : " + ordering + "]";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		bodyValue += tbody2.parent().html();
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

	function forwarding(val,val2) {
		$("#searchForm input[id='mode']").val("select");
		$("#searchForm input[id='storeVal']").val(val);
		$("#searchForm input[id='srchStartDate']").val(val2);
		$("#searchForm input[id='srchEndDate']").val(val2);
		
		//if(dateValid()){
		$("#searchForm").attr("action", "<c:url value='/edi/order/NEDMORD0030.do'/>");
		$("#searchForm").submit();
		//}
	}
	
	/* 점포선택 초기화 */
	function storeClear() {
		$("#searchForm input[id='storeVal']").val("");
		$("#searchForm input[id='storeName']").val("");
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

		if (store == "") {
			store = "<spring:message code='epc.ord.all'/>";
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
		textData += "[<spring:message code='epc.ord.ordering'/> : " + ordering + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		
		if (dateValid()) {
			//loadingMaskFixPos();
			$("#searchForm input[name='textData']").val(textData);
			
			$("#searchForm").attr("action", "<c:url value='/edi/order/NEDMORD0020Text.do'/>");
			$("#searchForm").submit();
		}	
	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	
<tr class="r1">
	<td align="left"><c:out value="\${ordDy}"/></td>
	<td align="center"><a href="javascript:forwarding('<c:out value="\${strCd}"/>','<c:out value="\${ordDy}"/>');"><c:out value="\${strNm}"/></a></td>
	<td align="center" style="display:none;"><c:out value="\${strCd}"/></td>
	<td align="center" style="mso-number-format:'\@'"><c:out value="\${ordSlipNo}"/></td>
	<td align="center"><c:out value="\${ordFgNm}"/></td>
	<td align="right" class="totQty"><c:out value="\${totQty}"/></td>
	<td align="right" class="totPrc"><c:out value="\${totPrc}"/></td>
	<td align="center"><c:out value="\${ctrArrDy}"/></td>
	<td align="center"><c:out value="\${splyDy}"/></td>
	<td align="center"><c:out value="\${userHit}"/></td>
</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
		<div>
			<form id="searchForm" name="searchForm" method="post" action="#">
			<input type="hidden" id="mode" name ="mode" />
			<input type="hidden" name="text_data" />
			<input type="hidden" id="textData" name="textData" />
			<input type="hidden" name="vendor" value="<c:out value ="${paramMap.ven }" />">
			<input type="hidden" id="storeName" name="storeName" />
			<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
			<input type="hidden" name="name">
			<input type="hidden" id="widthSize" name="widthSize" value="<c:out value ="${param.widthSize }" />" >
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"><spring:message code='epc.ord.searchCondition'/><!-- <strong>&nbsp;<font color="red">※현재 발주 데이터 오류현상이 발생하고 있으니 참고하시기 바랍니다.</font></strong> --></li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
								<a href="#" class="btn" onclick="doExcel('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a> 
								<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" id="storeVal" name="storeVal"  value="<c:out value ="${param.storeVal }" />" />
						
						<colgroup>
							<col style="width:15%" />
							<col style="width:30%" />
							<col style="width:10%" />
							<col style="*" />
						</colgroup>
						<tr>
							<th><span class="star">*</span> <spring:message code='epc.ord.period'/> </th>
							<td>
								<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value ="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value ="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
							</td>
							<th><span class="star">*</span> <spring:message code='epc.ord.strCd'/></th>
							<td>
								<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='epc.ord.allStore'/>
								<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='epc.ord.strCd'/>
							</td>
						</tr>
						<tr>
							<th><spring:message code='epc.ord.venCd'/></th>
							<td>
								<html:codeTag objId="searchEntpCd" objName="searchEntpCd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
							</td>
							<th><spring:message code='epc.ord.ordering'/></th>
							<td class="bal">
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
			
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<colgroup>
							<col style="width:80px;" />
							<col  />
							<col style="width:110px;" />
							<col style="width:55px;" />
							<col style="width:90px;" />
							<col style="width:130px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;"/>
							<col style="width:17px;" />
						</colgroup>
						<tr>
							<th><spring:message code='epc.ord.ordDy'/></th>
							<th><spring:message code='epc.ord.store'/></th>
							<th style="display:none;"><spring:message code='epc.ord.strCd'/></th>
							<th><spring:message code='epc.ord.ordSlipNo'/></th>
							<th><spring:message code='epc.ord.ordering'/></th>
							<th><spring:message code='epc.ord.ordQty'/></th>
							<th><spring:message code='epc.ord.buyPrc'/></th>
							<th><spring:message code='epc.ord.ctrArrDy'/></th>
							<th><spring:message code='epc.ord.splyDy'/></th>
							<th><spring:message code='epc.ord.userHit'/></th>
							<th>&nbsp;</th>
						</tr>
						</table>
						
						<div class="datagrade_scroll_sum">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
							<col style="width:80px;" />
							<col  />
							<col style="width:110px;" />
							<col style="width:55px;" />
							<col style="width:90px;" />
							<col style="width:130px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;"/>
						</colgroup>
							<!-- Data List Body Start ------------------------------------------------------------------------------>
							<tbody id="dataListbody" />
							<!-- Data List Body End   ------------------------------------------------------------------------------>
							</table>
						</div>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTabl3">
							<colgroup>
								<col style="width:80px;" />
								<col  />
								<col style="width:110px;" />
								<col style="width:55px;" />
								<col style="width:90px;" />
								<col style="width:130px;" />
								<col style="width:80px;" />
								<col style="width:80px;" />
								<col style="width:80px;"/>
								<col style="width:17px;" />
							</colgroup>
								<tr id = "sumRow" class="r1" style="display:none;">
									<th colspan="4" class="fst" align=center><b><spring:message code='epc.ord.total'/></b></th>
									<th align=right><b id="totalTotQty"></b></th>
									<th align=right><b id="totalTotPrc"></b></th>
									<th colspan=3>&nbsp;</th>
									 
									<th >&nbsp;</th>
								</tr>
								<tr id="emptyRow" style="display:none;">
									<td colspan="9" > &nbsp;</td><th width=17>&nbsp;</th>
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
						<li><spring:message code='epc.ord.home'/></li>
						<li><spring:message code='epc.ord.orderInfo'/></li>
						<li><spring:message code='epc.ord.dateInfo'/></li>
						<li class="last"><spring:message code='epc.ord.perStore2'/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

</body>
</html>
