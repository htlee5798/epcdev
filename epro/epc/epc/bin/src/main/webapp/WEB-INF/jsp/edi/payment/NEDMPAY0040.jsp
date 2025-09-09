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
		
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val();
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='searchEntpCd']").val();
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 과세구분
		searchInfo["searchTax"] 		= $("#searchForm select[name='searchTax']").val();
		
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/payment/NEDMPAY0040Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});	
		}
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.paymentList;
			var tempData = [];
			
			for(var i = 0;i<data.length;i++){
				tempData[i] =  data[i];
				
			}
				
			setTbodyInit("dataListbody");	// dataList 초기화
			
			if (data.length > 0) {
				var buy_amt = 0  		
				var vat = 0  			
				var total = 0 		
				
				var buy_amt_sum = 0  		
				var vat_sum = 0  			
				var total_sum = 0 		
								
				// 금액 및 수량 comma 및 합계
				for (var i = 0; i < data.length; i++) {
					buy_amt = isEmpty(data[i]["buyAmt"]);
					vat = isEmpty(data[i]["vat"]);
					total = isEmpty(data[i]["total"]);
					
					if (buy_amt == "") buy_amt = 0;
					if (vat == "") vat = 0;
					if (total == "") total = 0;
					
					// 합계
					buy_amt_sum += parseInt(buy_amt);
					vat_sum += parseInt(vat);
					total_sum += Number(buy_amt) + Number(vat);
					//total_sum += parseInt(total);
					
					// Comma 설정
					data[i]["buyAmt"]= setComma(buy_amt);
					data[i]["vat"]= setComma(vat);
					data[i]["total"]= setComma(Number(buy_amt) + Number(vat));
					
				}
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
				
				 $('#buy_amt_sum').text(setComma(buy_amt_sum));
				 $('#vat_sum').text(setComma(vat_sum));
				 $('#total_sum').text(setComma(total_sum));
				
				$('.sum').show();
				$('.empty').hide();
			} else { 
				setTbodyNoResult("dataListbody", 9);
				$('.sum').hide();
				$('.empty').show();
			}
					
		}
			
	}

	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(form){

		var startDate 	= $("#searchForm input[name='srchStartDate']").val();
		var endDate 	= $("#searchForm input[name='srchEndDate']").val();
		
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

	    /*
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}
		*/	

		return true;
	}

	function popupSearch(tbName1, tbName2, tbName3){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');	
		var tbody3 = $('#' + tbName3 + ' tbody');		

		var form=document.forms[0];	

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();
		var tax 		= $("#searchForm select[name='searchTax']  option:selected").text();
	
		var tmp="";

		if(store==""){
			store="<spring:message code='epc.ord.all'/>";
		}
		
		if(tax==""){
			tax="<spring:message code='epc.ord.all'/>";
		}
		
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	
		
		
		
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.pay.dash.tax'/><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[<spring:message code='epc.pay.header.taxgubun'/> : " + tax + "]";
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

	/* 점포선택 초기화 */
	function storeClear() {
		$("#searchForm input[id='storeVal']").val("");
		$("#searchForm input[id='storeName']").val("");
	}

	function printText(){
		var form = document.forms[0];

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();
		var tax 		= $("#searchForm select[name='searchTax'] option:selected").text();


		var tmp="";

		if(store==""){
			store="<spring:message code='epc.ord.all'/>";
		}
		if(tax==""){
			tax="<spring:message code='epc.ord.all'/>";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	


		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		textData += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		textData += "[<spring:message code='epc.pay.header.taxgubun'/>  : "+tax+"]" ;
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		$("#searchForm input[name='textData']").val(textData);
		$("#searchForm").attr("action", "<c:url value='/edi/payment/NEDMPAY0040Text.do'/>");
		$("#searchForm").submit();

	}
	
	function isEmpty(str) {
		return str == null ? "" : $.trim(str);
	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center"><c:out value="\${strNm}"/></td>
	<td align="center"><c:out value="\${taxFg}"/></td>
	<td align=right><c:out value="\${buyAmt}"/></td>
	<td align=right><c:out value="\${vat}"/></td>
	<td align=right><c:out value="\${total}"/></td>
</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" name="textData" />
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
						<li class="tit"><spring:message code='epc.pay.search.condition'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2','testTable3');"><span><spring:message code="button.common.excel"/></span></a> 
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
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
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> <spring:message code='epc.pay.search.storeSelect'/></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='epc.pay.search.allStore'/>
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='epc.pay.search.storeSelect'/>
						</td>
					</tr>
					<tr>
						<th><spring:message code='epc.pay.search.entpCd'/></th>
						<td>
							<html:codeTag objId="searchEntpCd" objName="searchEntpCd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
							
						<th><spring:message code='epc.pay.header.taxgubun'/></th>
						<td >
							<html:codeTag objId="searchTax" objName="searchTax" comType="SELECT" selectParam="<c:out value='${param.tax}'/>" formName="form" parentCode="TAX02" subCode="1" defName="전체" ></html:codeTag>
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
						<li class="tit"><spring:message code='epc.pay.search.result'/></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:120px;" />
						<col style="width:150px;" />
						<col style="width:150px;" />
						<col style="width:150px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th><spring:message code='epc.pay.header.strNm'/></th>
						<th><spring:message code='epc.pay.header.taxgubun'/></th>
						<th><spring:message code='epc.pay.header.buyAmt'/></th>
						<th><spring:message code='epc.pay.header.vat'/></th>
						<th><spring:message code='epc.pay.header.totalSum'/></th>
					</tr>
					</table>
					
					<div style="width:100%; height:410px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:120px;" />
								<col style="width:150px;" />
								<col style="width:150px;" />
								<col style="width:150px;" />
								<col  />
								
							</colgroup>
							<!-- Data List Body Start ------------------------------------------------------------------------------>
							<tbody id="dataListbody" />
							<!-- Data List Body End   ------------------------------------------------------------------------------>
						</table>
					</div>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
					<colgroup>
						<col style="width:120px;" />
						<col style="width:150px;" />
						<col style="width:150px;" />
						<col style="width:150px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
						<tr style="display:none;" class="r1 sum">
							<th colspan="2" class="fst" align=center><b><spring:message code='epc.pay.header.totalSum'/></b></th>
							<th align=right><b id="buy_amt_sum"></b></th>
							<th align=right><b id="vat_sum"></b></th>
							<th align=right><b id="total_sum"></b></th>
							<th>&nbsp;</th>
						</tr>
						<tr style="display:none;" class="empty"><td colspan=5>&nbsp;</td><th width=17>&nbsp;</th>
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
					<li><spring:message code='epc.pay.home'/></li>
					<li><spring:message code='epc.pay.salInfo'/></li>
					<li><spring:message code='epc.pay.dateInfo'/></li>
					<li class="last"><spring:message code='epc.pay.texInfo'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
