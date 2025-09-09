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
		var ch=440;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

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
		dataInfo["START_DATE"] = $('#startDate').val().replaceAll('-','');
		dataInfo["END_DATE"] = $('#endDate').val().replaceAll('-','');
		
		var TAB1 = new Array();
		
		if ($("#entp_cd option:selected").val() == '') {
			var selLen = $("#entp_cd option").size();
			for (var i = 1; i < selLen; i++) {
				 var info = {};
				//console.log($("#entp_cd option:eq(" + i + ")").val());
				
				var selVal = $("#entp_cd option:eq(" + i + ")").val();
				info["VEN_CD"] = selVal;
				
				TAB1.push(info); 
			}
		} else {
			var info = {};
			info["VEN_CD"] = $("#entp_cd option:selected").val();
			TAB1.push(info); 
		} 
		
		
		var TAB2 = new Array(); //조치 미조치
		var stores = storeValArrList($("#searchForm input[name='storeVal']").val());
		
		for(var i = 0 ; i < stores.length ; i++){
			var STR_CD = {};
			STR_CD["STR_CD"] = stores[i];
			TAB2.push(STR_CD);
			
		} 
		
		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon(); 
		//console.log(dataInfo);
		//rfc펑션 콜후 콜백 펑션 호출 (콜백 펑션 구현rfcCallBack)
		rfcCall("INV0560" , dataInfo);
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


	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(){
		 
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
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

	    //rangeDate = 0;
	    
		if(rangeDate > 30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}	

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

		var form=document.forms[0];	

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();

		var tmp = "";

		if (store == "") {
			store = "전체";
		}

		
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}
	
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='text.buy.field.giftConfirmList'/><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
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
	
	/* txt파일 생성 */
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
		dataInfo["START_DATE"] = $('#startDate').val().replaceAll('-','');
		dataInfo["END_DATE"] = $('#endDate').val().replaceAll('-','');
		
		var TAB1 = new Array();
		
		if ($("#entp_cd option:selected").val() == '') {
			var selLen = $("#entp_cd option").size();
			for (var i = 1; i < selLen; i++) {
				 var info = {};
				//console.log($("#entp_cd option:eq(" + i + ")").val());
				
				var selVal = $("#entp_cd option:eq(" + i + ")").val();
				info["VEN_CD"] = selVal;
				
				TAB1.push(info); 
			}
		} else {
			var info = {};
			info["VEN_CD"] = $("#entp_cd option:selected").val();
			TAB1.push(info); 
		} 
		
		
		var TAB2 = new Array(); //조치 미조치
		var stores = storeValArrList($("#searchForm input[name='storeVal']").val());
		
		for(var i = 0 ; i < stores.length ; i++){
			var STR_CD = {};
			STR_CD["STR_CD"] = stores[i];
			TAB2.push(STR_CD);
			
		} 
		
		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon(); 
		//console.log(dataInfo);
		
		// 조회시작일자
		var srchStartDate 	= $("#searchForm input[name='startDate']").val();
		// 조회 종료일자
		var srchEndDate 	= $("#searchForm input[name='endDate']").val();
		// 조회기간 설정
		var date = srchStartDate + "~" + srchEndDate;
		// 점포명
		var store 		= $("#searchForm input[name='storeName']").val();
		
		// 협력업체 코드(배열)
		var vendor 		= $("#searchForm input[name='vendor']").val();
		// 협력업체 코드(단독)
		var vencd 		= $("#searchForm select[name='entp_cd']").val();
		
		if (store == "") {
			store = "전체";
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
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		
		//console.log(textData);		
				
		$("#searchForm input[name='proxyNm']").val("INV0560");
		$("#searchForm input[name='param']").val(JSON.stringify(dataInfo));
		$("#searchForm input[name='textData']").val(textData);
		
		$("#searchForm").attr("method", "post");
		$("#searchForm").attr("action", "<c:url value='/edi/buy/NEDMBUY0080Text.do'/>");
		$("#searchForm").submit();
	}
</script>


<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center"><c:out value="\${CEN_NM}" />${list.CEN_NM }</td>
	<td align="center"><c:out value="\${COMPANY}" />${list.COMPANY }</td>
	<td align="center"><c:out value="\${RETAILSKU}" />${list.RETAILSKU }</td>
	<td align="left"><c:out value="\${DESCR}" />${list.DESCR }</td>
	<td align="right"><c:out value="\${setComma(PACKKEY)}" /></td>
	<td align="center"><c:out value="\${UOM}" /></td>
	<td align="right"><c:out value="\${setComma(parseInt(ORD_QTY))}" /></td>
	<td align="right"><c:out value="\${setComma(parseInt(BUY_QTY))}" /></td>
	<td align="center"><c:out value="\${TEMP}" /></td>
	<td align="center"><c:out value="\${ROUTE}" /></td>
</tr>
</script>
</head>



<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" name="vendor" id="vendor" value="${paramMap.ven }">
		<input type="hidden" name="storeName" id="storeName" />
		<input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue" >
		<input type="hidden" name="name" id="name">
		<input type="hidden" name="widthSize" 	id="widthSize" value="${param.widthSize }" > 
		<input type="hidden" name="proxyNm" 	id="proxyNm" /> 
		<input type="hidden" name="param" 		id="param" /> 
		<input type="hidden" name="textData" 	id="textData" /> 
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='text.buy.field.searchCondition'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a>
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='text.buy.field.period'/> </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value='${paramMap.startDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value='${paramMap.endDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> <spring:message code='text.buy.field.strCdSelect'/></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();"/><spring:message code='text.buy.field.allStore'/>
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='text.buy.field.strCdSelect'/>
						</td>
						
						
					</tr>
					<tr>
					
						<th><spring:message code='text.buy.field.venCd'/></th>
						<td colspan="3">
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<%-- 삭제
						<th>납품구분</th>
						<td >
							<select name="buy_cen_fg" >
								<option value="C" <c:if test="${paramMap.buy_cen_fg eq 'C' }"> selected</c:if>>센터</option>
								<option value="S" <c:if test="${paramMap.buy_cen_fg eq 'S' }"> selected</c:if>>점포</option>
								<option value="A" <c:if test="${paramMap.buy_cen_fg eq 'A' }"> selected</c:if>>전체</option>
							</select>
						</td> --%>
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
						<li class="tit"><spring:message code='text.buy.field.result'/></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"  id="testTable1">
					<colgroup>
							<col style="width:70px" />
							<col style="width:70px" />
							<col style="width:110px" />
							<col style="width:220px" />
							<col style="width:50px" />
							<col style="width:50px" />
							<col style="width:50px" />
							<col style="width:50px" />
							<col style="width:50px" />
							<col />
							<col style="width:17px" />
						</colgroup>
						<tr>
							<th><spring:message code='epc.buy.header.cenNm'/></th>
							<th><spring:message code='epc.buy.header.strNm'/></th>
							<th><spring:message code='epc.buy.header.retailsku'/></th>
							<th><spring:message code='epc.buy.header.descr'/></th>
							<th><spring:message code='epc.buy.header.ordIpsu'/></th>
							<th><spring:message code='epc.buy.header.danwi'/></th>
							<th><spring:message code='epc.buy.header.ordQty'/></th>
							<th><spring:message code='epc.buy.header.cfBuyQty'/></th>
							<th><spring:message code='epc.buy.header.temp'/></th>
							<th><spring:message code='epc.buy.header.route'/></th>
							<th>&nbsp;</th>
						</tr>
					</table>
					<div style="width:100%; height:417px;overflow-x:hidden; overflow-y:scroll;  table-layout:fixed;">
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
								<col style="width:70px" />
								<col style="width:70px" />
								<col style="width:110px" />
								<col style="width:220px" />
								<col style="width:50px" />
								<col style="width:50px" />
								<col style="width:50px" />
								<col style="width:50px" />
								<col style="width:50px" />
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
					<li><spring:message code='epc.buy.menu.lvl1'/></li>
					<li><spring:message code='epc.buy.menu.lvl2'/></li>
					<li><spring:message code='epc.buy.menu.lvl3'/></li>
					<li class="last"><spring:message code='epc.buy.menu.retailConfirm'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>

</html>


