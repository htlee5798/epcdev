<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	var command = 'R';
	
	/* onLoad or key event */
	$(document).ready(function($) {
		doUpdateSearch();
	});
	
	/* 조회 */
	function doUpdateSearch() {
		command = 'R';
		 
		if($('#entp_cd option').length < 2){
			alert("<spring:message code='text.buy.alert.noVenCd'/>");
			return ;
		}
		var dataInfo = {};
		//20151117
		dataInfo["STARTDATE"] = $('#startDate').val().replaceAll('-','');
		dataInfo["ENDDATE"] = $('#endDate').val().replaceAll('-','');
		
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
		for (var i = 0; i < data.length; i++) {
			data[i]["idx"] 	= i;
			data[i]["DELI_END_DY"] =data[i]["DELI_END_DY"].replaceAll('00000000','');
		}
		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
	}
	
	//rfc 통신 서비스가 여러개일 경우 전역변수 설정후 분기 태우시면 됩니다.
	function rfcCallBack(data){
		if(command == 'R'){
			gridRfc(data);
		}else{
			alert(data.result.ZMSG);
			doUpdateSearch();
		}
	}
	
	function gridRfc(data){
		var rows = data.result.RESPCOMMON.ZPOROWS;
		var result = data.result;
		setTbodyInit("dataListbody");
		if (rows != 0) {
			_setTbodyMasterValue(result.TAB);
		} else {
			setTbodyNoResult("dataListbody", 10);
		}
	}
	
	function forward(obj) {
		var form = document.forms[0];
		//udeliReasonFg
		var newHtml1 = "<select name='udeliReasonFg'>"
				+ "<option value='1'><spring:message code='text.dly.field.udeliReason1'/></option>"
				+ "<option value='2'><spring:message code='text.dly.field.udeliReason2'/></option>"
				+ "<option value='3'><spring:message code='text.dly.field.udeliReason3'/></option>"
				+ "<option value='4'><spring:message code='text.dly.field.udeliReason4'/></option>"
				+ "<option value='5'><spring:message code='text.dly.field.udeliReason5'/></option>"
				+ "<option value='6'><spring:message code='text.dly.field.udeliReason6'/></option>" + "</select>";

		var newHtml2 = "<select name='udeliReasonFg'>"
				+ "<option value=''>=======</option>" + "</select>";
				
		var $dataTr =$(obj).closest('tr');
		var idx = $dataTr.data('idx');
		if ($(obj).val() == "3") {
			$dataTr.find('.udeliReasonFg').html(newHtml1);
			$('#chooseDate' + idx).attr('disabled',false);
		} else {
			$dataTr.find('.udeliReasonFg').html(newHtml2);
			$('#chooseDate' + idx).attr('disabled','disabled');
		}
	}
	
	function doUpdate(){
		var $checkedArr = $('tr.dataTr');
		
		if ($checkedArr.length <= 0) {
			alert("<spring:message code='text.dly.alert.noData'/>");
			return;
		}
		
		if (!confirm("<spring:message code='msg.common.confirm.save' />")) {
			return;
		}
		
		//서버 날짜 가져오기
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/delivery/NEDMDLY0120ServerDate.json"/>',
			data : '',
			success : function(data) {
				//서버날짜 셋팅
				$('#nowDate').val(data.nowDate);
				doUpdateProcess();
			}
		});
	}

	function doUpdateProcess() {
		command = 'S';
		var $checkedArr = $('tr.dataTr');
		var dataInfo = {};
		//20151117
		
		
		var TAB = new Array();
		if($checkedArr.length > 0) {
			$checkedArr.each(function() {
				var $dataTd = $(this);
				
				//alert($(this).closest('td').data('saleDy'));
				var info = {};
				info["STR_CD"]				= $dataTd.data('strCd');
				info["SALE_DY"]				= $dataTd.data('saleDy');
				info["TRD_TYPE_DIVN_CD"]	= $dataTd.data('trdTypeDivnCd');
				info["POS_NO"]				= $dataTd.data('posNo');
				info["TRD_NO"]				= $dataTd.data('trdNo');
				info["SELL_PROD_CD"]		= $dataTd.data('sellProdCd');
				info["RECV_SEQ"]			= $dataTd.data('recvSeq');
				
				var acceptFg = $dataTd.find('select[name=acceptFg]').val();
				info["ACCEPT_FG"]			= acceptFg; //접수 SELECTED VAL 
				info["UDELI_REASON_FG"]		= $dataTd.find('select[name=udeliReasonFg]').val();
				
				if (acceptFg == '2') {
					info["DELI_END_DT"] = $('#nowDate').val();
				} else if(acceptFg == '3') {
					info["DELI_END_DT"]=$('#chooseDate' + $dataTd.data('idx')).val().replaceAll('-','');
				} else {
					info["DELI_END_DT"] = '';
				}
				
				info["LST_CHG_EMP_NO"]		= '111111111';
				TAB.push(info); 
			});
			dataInfo["TAB"] = TAB;
			
			// 공통 RequestCommon
			dataInfo["REQCOMMON"] = getReqCommon(); 
			//console.log(dataInfo);
			
			//rfc펑션 콜후 콜백 펑션 호출 (콜백 펑션 구현rfcCallBack)
			rfcCall("INV0670" , dataInfo);
		}
	}
	
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
	<tr class="r1 dataTr"
		data-str-cd        		="<c:out value="\${STR_CD     }" />"
		data-sale-dy      		="<c:out value="\${SALE_DY    }" />"
		data-trd-type-divn-cd   ="<c:out value="\${TRD_TYPE_DIVN_CD    }" />"
		data-pos-no      		="<c:out value="\${POS_NO    }" />"
		data-trd-no        		="<c:out value="\${TRD_NO     }" />"
		data-sell-prod-cd       ="<c:out value="\${SELL_PROD_CD     }" />"
		data-recv-seq      		="<c:out value="\${RECV_SEQ   }" />"
		data-accept-fg     		="<c:out value="\${ACCEPT_FG  }" />"
		data-lst-chg-emp   		="<c:out value="\${LST_CHG_EMP}" />"
		data-idx				="<c:out value="\${idx}" />"
	>
		<td align="center" rowspan="3">
			<a href="javascript:clickSlipNo('<c:out value='\${SLIP_NO}'/>','<c:out value='\${SRCMK_CD}'/>')">
				<c:out value="\${SLIP_NO}" />
			</a>
		</td>
		<td align="center"><c:out value="\${STR_NM}" /></td>
		<td align="center"><c:out value="\${SRCMK_CD}" /></td>
		<td align="left">&nbsp;<c:out value="\${PROD_NM}" /></td>
		<td align="center"><c:out value="\${PROM_DY}" /></td>
		<td align="right">setComma(<c:out value="\${QTY}" />);</td>
		<td align="center" rowspan="2">
			<select name="acceptFg" onchange="javascript:forward(this);">
				<option value="1" {%if ACCEPT_FG == 1 %} selected {%/if%}><spring:message code='text.dly.field.hangmok1'/></option>
				<option value="2" {%if ACCEPT_FG == 2 %} selected {%/if%}><spring:message code='text.dly.field.hangmok2'/></option>
				<option value="3" {%if ACCEPT_FG == 3 %} selected {%/if%}><spring:message code='text.dly.field.hangmok3'/></option>
				<option value="4" {%if ACCEPT_FG == 4 %} selected {%/if%}><spring:message code='text.dly.field.hangmok4'/></option>
			</select>
		</td> 
 		<td class="udeliReasonFg" align="center" rowspan="2">
			{%if ACCEPT_FG == 3 %}
				 <select name="udeliReasonFg">
					<option {%if UDELI_REASON_FG == 1 %} selected {%/if%} value="1"><spring:message code='text.dly.field.udeliReason1'/></option>
					<option {%if UDELI_REASON_FG == 2 %} selected {%/if%} value="2"><spring:message code='text.dly.field.udeliReason2'/></option>
					<option {%if UDELI_REASON_FG == 3 %} selected {%/if%} value="3"><spring:message code='text.dly.field.udeliReason3'/></option>
					<option {%if UDELI_REASON_FG == 4 %} selected {%/if%} value="4"><spring:message code='text.dly.field.udeliReason4'/></option>
					<option {%if UDELI_REASON_FG == 5 %} selected {%/if%} value="5"><spring:message code='text.dly.field.udeliReason5'/></option>
					<option {%if UDELI_REASON_FG == 6 %} selected {%/if%} value="6"><spring:message code='text.dly.field.udeliReason6'/></option>
				</select>
			{%else%}
				<select name="udeliReasonFg">
					<option value="">=======</option>
				</select>
			{%/if%}
		</td>
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
			<input type="text" class="day" id="chooseDate<c:out value="\${idx}" />" name="chooseDate<c:out value="\${idx}" />" readonly 
				value="{%if DELI_END_DY.trim() != '00000000'%}strToDateFormat(<c:out value="\${DELI_END_DY}" />);{%/if%}" 
				style="width:80px; text-align: center;" onClick="openCal('searchForm.chooseDate<c:out value="\${idx}" />');" {%if ACCEPT_FG != 3 %} disabled {%/if%} />
		</td>
	</tr>
	<tr class="r1">
		<td align="center" colspan="8">&nbsp; <c:out value="\${REMARK}" /></td>
	</tr>
</script>
</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>>
		<div>
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
			<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post" action="#">
				<input type="hidden" id = "nowDate" name="nowDate" />
				<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />">
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message code='text.dly.field.searchCondition'/></li>
								<li class="btn">
									<a href="#" class="btn" onclick="doUpdateSearch();"><span><spring:message code='text.dly.field.updateSearch'/></span></a> 
									<a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save" /></span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">

								<input type="hidden" id="storeVal" name="storeVal" value="<c:out value="${param.storeVal }" />" />
								<input type="hidden" id="forwardValue" name="forwardValue" />
								<input type="hidden" id="forwardValue2" name="forwardValue2" />

								<colgroup>
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 15%" />
									<col style="" />
								</colgroup>
								<tr>
									<th><span class="star">*</span> <spring:message code='text.dly.field.period'/></th>
									<td>
										<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.startDate}" />" style="width: 80px;" />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor: hand;" />
										 ~ 
										<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.endDate}" />" style="width: 80px;" /> 
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');" style="cursor: hand;" />
									</td>
									<th><span class="star">*</span> <spring:message code='text.dly.field.strCdSelect'/>점포선택</th>
									<td>
										<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" /><spring:message code='text.dly.field.allStore'/> 
										<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();" /><spring:message code='text.dly.field.strCdSelect'/>
									</td>
								</tr>
								<tr>
									<th><spring:message code='text.dly.field.venCd'/></th>
									<td>
										<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entp_cd}" dataType="CP" comType="SELECT" formName="form" defName="전체" />
									</td>
									<th><spring:message code='text.dly.field.ingGubun'/></th>
									<td>
										<select id="hangmok" name="hangmok">
											<option value="all" <c:if test="${param.hangmok == 'all' }"> selected </c:if>><spring:message code='text.dly.field.all'/></option>
											<option value="1" <c:if test="${param.hangmok == '1' }"> selected </c:if>><spring:message code='text.dly.field.hangmok1'/></option>
											<option value="2" <c:if test="${param.hangmok == '2' }"> selected </c:if>><spring:message code='text.dly.field.hangmok2'/></option>
											<option value="3" <c:if test="${param.hangmok == '3' }"> selected </c:if>><spring:message code='text.dly.field.hangmok3'/></option>
											<option value="4" <c:if test="${param.hangmok == '4' }"> selected </c:if>><spring:message code='text.dly.field.hangmok4'/></option>
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
								<li class="tit"><spring:message code='text.dly.field.result'/></li>
							</ul>
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width: 90px;" />
									<col style="width: 90px;" />
									<col style="width: 90px;" />
									<col style="width: 160px;" />
									<col style="width: 90px;" />
									<col style="width: 90px;" />
									<col style="width: 90px;" />
									<col />
								</colgroup>
								<tr>
									<th rowspan="3"><spring:message code='epc.dly.header.h01201'/></th>
									<th><spring:message code='epc.dly.header.h01202'/></th>
									<th><spring:message code='epc.dly.header.h01203'/></th>
									<th><spring:message code='epc.dly.header.h01204'/></th>
									<th><spring:message code='epc.dly.header.h01205'/></th>
									<th><spring:message code='epc.dly.header.h01206'/></th>
									<th rowspan="2"><spring:message code='epc.dly.header.h01307'/></th>
									<th rowspan="2"><spring:message code='epc.dly.header.h01308'/></th>
								</tr>
								<tr>
									<th><spring:message code='epc.dly.header.h01309'/></th>
									<th><spring:message code='epc.dly.header.h01310'/></th>
									<th><spring:message code='epc.dly.header.h01311'/></th>
									<th><spring:message code='epc.dly.header.h01312'/></th>
									<th><spring:message code='epc.dly.header.h01313'/></th>
								</tr>
								<tr>
									<th><spring:message code='epc.dly.header.h01314'/></th>
									<th><spring:message code='epc.dly.header.h01315'/></th>
									<th><spring:message code='epc.dly.header.h01316'/></th>
									<th><spring:message code='epc.dly.header.h01317'/></th>
									<th><spring:message code='epc.dly.header.h01318'/></th>
									<th colspan="2"><spring:message code='epc.dly.header.h01319'/></th>
								</tr>
								<tr>
									<th colspan="8"><spring:message code='epc.dly.header.h01320'/></th>
								</tr>
							</table>

							<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
							<div style="width: 100%; height: 365px; overflow-x: hidden; overflow-y: scroll; overflow-x: scroll; table-layout: fixed;">

								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
									<colgroup>
										<col style="width: 90px;" />
										<col style="width: 90px;" />
										<col style="width: 90px;" />
										<col style="width: 160px;" />
										<col style="width: 90px;" />
										<col style="width: 90px;" />
										<col style="width: 90px;" />
										<col />
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
						<li><spring:message code='epc.dly.menu.lvl1'/></li>
						<li><spring:message code='epc.dly.menu.lvl2'/></li>
						<li class="last"><spring:message code='epc.dly.menu.completeCreate'/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>
