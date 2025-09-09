<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	/* 조회 */
	function doSearch() {
		if(!dateValid()){
			return;
		}

		if($('#entp_cd option').length < 2){
			alert("<spring:message code='text.buy.alert.noVenCd'/>");
			return ;
		}

		var dataInfo = {};
		//20151117
		dataInfo["STARTDATE"] 	= $('#startDate').val().replaceAll('-','');
		dataInfo["ENDDATE"] 	= $('#endDate').val().replaceAll('-','');

		var TAB1 = new Array();
		if($('#hangmok').val() == 'all'){
			$('#hangmok option').each(function(index){
				if($(this).eq(index).val() == 'all'){

				}else{
					var HANGMOK ={};
					HANGMOK['HANGMOK'] = $(this).val();
					TAB1.push(HANGMOK);
				}
			})
		}else{
			var HANGMOK ={};
			HANGMOK['HANGMOK'] = $('#hangmok').val();
			TAB1.push(HANGMOK);
		}


		var TAB2 = new Array(); //조치 미조치
		if ($("#entp_cd option:selected").val() == '') {
			var selLen = $("#entp_cd option").size();
			 for (var i = 1; i < selLen; i++) {
				 var info = {};
				//console.log($("#entp_cd option:eq(" + i + ")").val());

				var selVal = $("#entp_cd option:eq(" + i + ")").val();
				info["VENCDS"] = selVal;

				TAB2.push(info);
			}
		} else {
			var info = {};
			info["VENCDS"] = $("#entp_cd option:selected").val();
			TAB2.push(info);
		}


		var TAB3 = new Array(); //조치 미조치
		var stores = storeValArrList($("#searchForm input[name='storeVal']").val());
		for(var i = 0 ; i < stores.length ; i++){
			var STR_CD = {};
			STR_CD["STOREVAL"] = stores[i];
			TAB3.push(STR_CD);

		}

		// [210128 마스킹작업1] S

		for(var i=0; i<TAB1.length;i++)
		{
			if(i==0){
				if($('#formHanmok').length > 1)break ;
				$("#openDataClicked input[name='formHangmok']").val(TAB1[i].HANGMOK);
			}
			else{
				var tagAppending = document.createElement("input");
				tagAppending.type ="hidden";
				tagAppending.name ="formHangmok";
				tagAppending.id		="formHangmok";
				tagAppending.value = TAB1[i].HANGMOK;
				document.openDataClicked.appendChild(tagAppending);
			}
		}

		for(var i=0; i<TAB2.length;i++)
		{
			if(i==0){
				if($('#formEntpCd').length > 1)break ;
				$("#openDataClicked input[name='formEntpCd']").val(TAB2[i].VENCDS);
			}
			else{
				var tagAppending = document.createElement("input");
				tagAppending.type ="hidden";
				tagAppending.name ="formEntpCd";
				tagAppending.id		="formEntpCd";
				tagAppending.value = TAB2[i].VENCDS;
				document.openDataClicked.appendChild(tagAppending);
			}
		}

		for(var i=0; i<TAB3.length;i++)
		{
			if(i==0){
				if($('#formStrCd').length > 1)break ;
				$("#openDataClicked input[name='formStrCd']").val(TAB3[i].STOREVAL);
			}
			else{
				var tagAppending = document.createElement("input");
				tagAppending.type ="hidden";
				tagAppending.name ="formStrCd";
				tagAppending.id		="formStrCd";
				tagAppending.value = TAB3[i].STOREVAL;
				document.openDataClicked.appendChild(tagAppending);
			}
		}

		// [210128 마스킹작업1] E

		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		dataInfo["TAB3"] = TAB3;
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon();
		//console.log(dataInfo);

		//rfc펑션 콜후 콜백 펑션 호출 (콜백 펑션 구현rfcCallBack)
		rfcCallForSearch("INV0660" , dataInfo);
	}

	function _setTbodyMasterValue(data) {
		setTbodyInit("dataListbody");	// dataList 초기화
		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
	}

	//rfc 통신 서비스가 여러개일 경우 전역변수 설정후 분기 태우시면 됩니다.
	function rfcCallBack(data){
		 var rows = data.result.RESPCOMMON.ZPOROWS;
		var result = data.result;
		setTbodyInit("dataListbody");
		 if (rows != 0) {
			_setTbodyMasterValue(result.TAB);
		} else {
			setTbodyNoResult("dataListbody", 10);
		}
	}

	/* 등록화면 이동 */
	function doUpdateSearch() {
		$("#searchForm").attr("method", "POST");
		$("#searchForm").attr("action", "<c:url value='/edi/delivery/NEDMDLY0131.do'/>");
		$("#searchForm").submit();
	}

	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}

	/* 필수조회항목 체크 */
	function dateValid(){

		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
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

	/* 점포선택 초기화 */
	function storeClear() {
		$("#searchForm input[id='storeVal']").val("");
		$("#searchForm input[id='storeName']").val("");
	}

	function popupSearch(tbName1, tbName2){

		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');


		var date 		= $("#searchForm input[name='startDate']").val() + "~" + $("#searchForm input[name='endDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();


		var tmp="";

		if(store==""){
			store="<spring:message code='epc.ord.all'/>";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}

		var hangmok="";
		hangmok = $('#hangmok option:selected').text();

		var bodyValue = "";
		bodyValue += "<CAPTION>TOY배달정보  완료등록 현황표<br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
		bodyValue += "[항목 : " + hangmok + "]";
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

	function printText() {
		if(!dateValid()){
			return;
		}

		if($('#entp_cd option').length < 2){
			alert("<spring:message code='text.buy.alert.noVenCd'/>");
			return ;
		}

		var dataInfo = {};
		//20151117
		dataInfo["STARTDATE"] 	= $('#startDate').val().replaceAll('-','');
		dataInfo["ENDDATE"] 	= $('#endDate').val().replaceAll('-','');

		var TAB1 = new Array();
		if($('#hangmok').val() == 'all'){
			$('#hangmok option').each(function(index){
				if($(this).eq(index).val() == 'all'){

				}else{
					var HANGMOK ={};
					HANGMOK['HANGMOK'] = $(this).val();
					TAB1.push(HANGMOK);
				}
			})
		}else{
			var HANGMOK ={};
			HANGMOK['HANGMOK'] = $('#hangmok').val();
			TAB1.push(HANGMOK);
		}


		var TAB2 = new Array(); //조치 미조치
		if ($("#entp_cd option:selected").val() == '') {
			var selLen = $("#entp_cd option").size();
			 for (var i = 1; i < selLen; i++) {
				 var info = {};
				//console.log($("#entp_cd option:eq(" + i + ")").val());

				var selVal = $("#entp_cd option:eq(" + i + ")").val();
				info["VENCDS"] = selVal;

				TAB2.push(info);
			}
		} else {
			var info = {};
			info["VENCDS"] = $("#entp_cd option:selected").val();
			TAB2.push(info);
		}


		var TAB3 = new Array(); //조치 미조치
		var stores = storeValArrList($("#searchForm input[name='storeVal']").val());
		for(var i = 0 ; i < stores.length ; i++){
			var STR_CD = {};
			STR_CD["STOREVAL"] = stores[i];
			TAB3.push(STR_CD);

		}

		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		dataInfo["TAB3"] = TAB3;
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon();
		//console.log(dataInfo);

		var date = dataInfo["STARTDATE"] + "~" + dataInfo["ENDDATE"];

		// 점포명
		var store = $("#searchForm input[name='storeName']").val();
		// 협력업체 코드(배열)
		var vendor 		= $("#searchForm input[name='vendor']").val();
		// 협력업체 코드(단독)
		var vencd 		= $("#searchForm select[name='entp_cd']").val();

		if (store == "") {
			store = "전체";
		}

		var tmp = "";
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}

		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.store'/> : " + store + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"

		$("#searchForm input[name='proxyNm']").val("INV0660");
		$("#searchForm input[name='param']").val(JSON.stringify(dataInfo));
		$("#searchForm input[name='textData']").val(textData);

		$("#searchForm").attr("methos", "post");
		$("#searchForm").attr("action", "<c:url value='/edi/delivery/NEDMDLY0130Text.do' />");
		$("#searchForm").submit();
	}

	function clickSlipNo(slipNo,srcmkCd){

		var popInfo = window.open("",'_blankPop','top=0, left=0, width=950, height=350, toolbar=no, status=no');

		popInfo.focus();

		console.log($("#formEntpCd").val());

		$("#openDataClicked").attr("action", "<c:url value='/edi/delivery/NEDMDLY0132.do'/>");
		$("#openDataClicked").attr("target", "_blankPop");

		var startDate=$('#startDate').val().replaceAll('-','');
		var endDate=$('#endDate').val().replaceAll('-','');

		$("#openDataClicked input[name='formStartDate']").val(startDate);
		$("#openDataClicked input[name='formEndDate']").val(endDate);
		$("#openDataClicked input[name='slipNo']").val(slipNo);
		$("#openDataClicked input[name='srcmkCd']").val(srcmkCd);

		$("#openDataClicked").submit();

	}
	
	function rfcCallForSearch(proxyNm, param) {
		if (proxyNm == "") {
			//alert("PROXYëªì íì¸í´ì£¼ì¸ì.");
			return;
		}

		var requestParam = {};
		requestParam["param"] 	= JSON.stringify(param);
		requestParam["proxyNm"] = proxyNm;
		//console.log(requestParam);

		console.log(requestParam);

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/comm/rfcCallDlyProdMasking.json" />',
			//url : '<c:url value="/edi/comm/rfcCall.json" />',
			data : JSON.stringify(requestParam),
			success : function(data) {
				rfcCallBack(data);
			}
		});
	}
</script>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center" rowspan="3">
		<a href="javascript:clickSlipNo('<c:out value='\${SLIP_NO}' />','<c:out value='\${SRCMK_CD}'/>')">
			<c:out value="\${SLIP_NO}" />
		</a>
	</td>
	<td align="center"><c:out value="\${STR_NM}" /></td>
	<td align="center"><c:out value="\${SRCMK_CD}" /></td>
	<td align="left">&nbsp;<c:out value="\${PROD_NM}" /></td>
	<td align="center"><c:out value="\${PROM_DY}" /></td>
	<td align="right"><c:out value="\${setComma(QTY)}" /></td>
	{%if ACCEPT_FG == 0 %}
	 	<td align="center" rowspan="2"><spring:message code='text.dly.field.notRegisted' /></td>
	{%elif ACCEPT_FG == 1 %}
		<td align="center" rowspan="2"><spring:message code='text.dly.field.hangmok1' /></td>
	{%elif ACCEPT_FG == 2 %}
		<td align="center" rowspan="2"><spring:message code='text.dly.field.hangmok2' /></td>
	{%elif ACCEPT_FG == 3 %}
		<td align="center" rowspan="2"><spring:message code='text.dly.field.hangmok3' /></td>
	{%elif ACCEPT_FG == 4 %}
		 <td align="center" rowspan="2"><spring:message code='text.dly.field.hangmok4' /></td>
	{%else%}
		<td align="center" rowspan="2"></td>
	{%/if%}

    {%if UDELI_REASON_FG == 1 %}
		 <td align="center" rowspan="2"><spring:message code='text.dly.field.udeliReason1' /></td>
	{%elif UDELI_REASON_FG == 2 %}
		<td align="center" rowspan="2"><spring:message code='text.dly.field.udeliReason2' /></td>
	{%elif UDELI_REASON_FG == 3 %}
		 <td align="center" rowspan="2"><spring:message code='text.dly.field.udeliReason3' /></td>
	{%elif UDELI_REASON_FG == 4 %}
		<td align="center" rowspan="2"><spring:message code='text.dly.field.udeliReason4' /></td>
	{%elif UDELI_REASON_FG == 5 %}
		<td align="center" rowspan="2"><spring:message code='text.dly.field.udeliReason5' /></td>
	{%elif UDELI_REASON_FG == 6 %}
		<td align="center" rowspan="2"><spring:message code='text.dly.field.udeliReason6' /></td>
	{%else%}
		<td align="center" rowspan="2"></td>
	{%/if%}
</tr>
<tr class="r1">
	<td align="center"><c:out value="\${SALE_DY}" /></td>
	<td align="center"><c:out value="\${CUST_NM}" /></td>
	<td align="left">&nbsp;<c:out value="\${CUST_ADD}" /></td>
	<td align="center"><c:out value="\${CUST_TEL_NO1}" /></td>
	<td align="center"><c:out value="\${CUST_TEL_NO2}" /></td>
</tr>
<tr class="r1">
	<td align="center"><c:out value="\${ACCEPT_DY}" /></td>
	<td align="center"><c:out value="\${RECV_NM}" /></td>
	<td align="left">&nbsp;<c:out value="\${RECV_ADDR}" /></td>
	<td align="center"><c:out value="\${RECV_TEL_NO1}" /></td>
	<td align="center"><c:out value="\${RECV_TEL_NO2}" /></td>
	<td align="center" colspan="2">
		{%if DELI_END_DY.trim() != "00000000" %}
			<c:out value="\${strToDateFormat(DELI_END_DY)}" />
		{%/if%}
	</td>
</tr>
<tr class="r1">
	<td align="center" colspan="8">&nbsp; <c:out value="\${REMARK}" /></td>
</tr>
</script>
</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!-- [210128 마스킹작업2] S -->
		<form id="openDataClicked" name="openDataClicked" method="post"  action="/epc/edi/delivery/NEDMDLY0132.do"  target="iframe1">
			<input type="hidden" id="formHangmok" name ="formHangmok" />
			<input type="hidden" id="formEntpCd" name ="formEntpCd" />
			<input type="hidden" id="formStrCd" name ="formStrCd" />
			<input type="hidden" id="formStartDate" name ="formStartDate" />
			<input type="hidden" id="formEndDate" name ="formEndDate" />

			<input type="hidden" id="slipNo" name ="slipNo" />
			<input type="hidden" id="srcmkCd" name ="srcmkCd" />
			<!--
			<input type="hidden" id="strNm" name ="strNm" />
			<input type="hidden" id="srcmkCd" name ="srcmkCd" />
			<input type="hidden" id="prodNm" name ="prodNm" />
			<input type="hidden" id="promDy" name ="promDy" />
			<input type="hidden" id="qty" name ="qty" />
			<input type="hidden" id="saleDy" name ="saleDy" />
			<input type="hidden" id="custNm" name ="custNm" />
			<input type="hidden" id="custTelNo1" name ="custTelNo1" />
			<input type="hidden" id="custAddr" name ="custAddr" />
			<input type="hidden" id="custTelNo2" name ="custTelNo2" />
			<input type="hidden" id="acceptDy" name ="acceptDy" />
			<input type="hidden" id="recvNm" name ="recvNm" />
			<input type="hidden" id="recvTelNo1" name ="recvTelNo1" />
			<input type="hidden" id="recvAddr" name ="recvAddr" />
			<input type="hidden" id="recvTelNo2" name ="recvTelNo2" />
			<input type="hidden" id="remark" name ="remark" /> -->
		</form>
		<!-- [210128 마스킹작업2] E -->
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" name="text_data" />
		<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
		<input type="hidden" id="storeName" name="storeName" />
		<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >

		<input type="hidden" id="proxyNm" name="proxyNm" />
		<input type="hidden" id="param" name="param" />
		<input type="hidden" id="textData" name="textData" />

		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='text.dly.field.searchCondition' /></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" onclick="doUpdateSearch();"><span><spring:message code="button.common.create"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a>
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">

					<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
					<input type="hidden" id="forwardValue" name="forwardValue"   />

					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='text.dly.field.period' /> </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> <spring:message code='text.dly.field.strCdSelect' /></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> /><spring:message code='text.dly.field.allStore' />
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='text.dly.field.strCdSelect' />
						</td>
					</tr>
					<tr>
						<th><spring:message code='text.dly.field.venCd' /></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entp_cd}" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<th><spring:message code='text.dly.field.ingGubun' /></th>
						<td>
							<select id="hangmok" name="hangmok">
								<option value="all" <c:if test="${param.hangmok == 'all' }"> selected </c:if>><spring:message code='text.dly.field.all' /></option>
								<option value="1" <c:if test="${param.hangmok == '1' }"> selected </c:if>><spring:message code='text.dly.field.hangmok1' /></option>
								<option value="2" <c:if test="${param.hangmok == '2' }"> selected </c:if>><spring:message code='text.dly.field.hangmok2' /></option>
								<option value="3" <c:if test="${param.hangmok == '3' }"> selected </c:if>><spring:message code='text.dly.field.hangmok3' /></option>
								<option value="4" <c:if test="${param.hangmok == '4' }"> selected </c:if>><spring:message code='text.dly.field.hangmok4' /></option>
							</select>
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			
			<br/><font color="blue">※배달정보 상세내역은 전표번호를 클릭해주세요. </font>
			
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='text.dly.field.result' /></li>

					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:160px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col  />
					</colgroup>
					<tr>
						<th rowspan="3"><spring:message code='epc.dly.header.h01301' /></th>
						<th><spring:message code='epc.dly.header.h01302' /></th>
						<th><spring:message code='epc.dly.header.h01303' /></th>
						<th><spring:message code='epc.dly.header.h01304' /></th>
						<th><spring:message code='epc.dly.header.h01305' /></th>
						<th><spring:message code='epc.dly.header.h01306' /></th>
						<th rowspan="2"><spring:message code='epc.dly.header.h01307' /></th>
						<th rowspan="2"><spring:message code='epc.dly.header.h01308' /></th>
					</tr>
					<tr>
						<th><spring:message code='epc.dly.header.h01309' /></th>
						<th><spring:message code='epc.dly.header.h01310' /></th>
						<th><spring:message code='epc.dly.header.h01311' /></th>
						<th><spring:message code='epc.dly.header.h01312' /></th>
						<th><spring:message code='epc.dly.header.h01313' /></th>
					</tr>
					<tr>
						<th><spring:message code='epc.dly.header.h01314' /></th>
						<th><spring:message code='epc.dly.header.h01315' /></th>
						<th><spring:message code='epc.dly.header.h01316' /></th>
						<th><spring:message code='epc.dly.header.h01317' /></th>
						<th><spring:message code='epc.dly.header.h01318' /></th>
						<th colspan="2"><spring:message code='epc.dly.header.h01319' /></th>

					</tr>
					<tr>
						<th colspan="8"><spring:message code='epc.dly.header.h01320' /></th>
					</tr>
					</table>

					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					<div style="width:100%; height:365px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; table-layout:fixed;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:90px;" />
								<col style="width:90px;" />
								<col style="width:90px;" />
								<col style="width:160px;" />
								<col style="width:90px;" />
								<col style="width:90px;" />
								<col style="width:90px;" />
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
					<li><spring:message code='epc.dly.menu.lvl1' /></li>
					<li><spring:message code='epc.dly.menu.lvl2' /></li>
					<li class="last"><spring:message code='epc.dly.menu.completeCreate' /></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
