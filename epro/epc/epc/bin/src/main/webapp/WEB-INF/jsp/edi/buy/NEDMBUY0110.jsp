<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	
	$(document).ready(function($) {
		$("#searchForm select[name='entp_cd']").val("<c:out value="${param.entp_cd}" />");
	});
	
	function _eventSearch() {
		
		if(!dateValid()){
			return;
		}
		if($('#entp_cd option').length < 2){
			alert('<spring:message code='text.buy.alert.noVenCd'/>');
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
		var measure = $("input[name='measure']:checked").val();
		if (measure == "1") {	// 미조치
			var tabInfo2 = {};
			tabInfo2["MEASURE"] = "A";
			TAB2.push(tabInfo2);
			
			var tabInfo2 = {};
			tabInfo2["MEASURE"] = "B";
			TAB2.push(tabInfo2);
		} else {				// 조치
			var tabInfo2 = {};
			tabInfo2["MEASURE"] = "C";
			TAB2.push(tabInfo2);
		}
		
		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon(); 
		//console.log(dataInfo);
		
		//rfc펑션 콜후 콜백 펑션 호출 (콜백 펑션 구현rfcCallBack)
		rfcCall("INV0570" , dataInfo);
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
	
	function productDetailPopup(docNm){
		var popInfo = window.open('','_blankPop','top=0, left=0, width=900, height=450, toolbar=no, status=yes, scrollbars=yse');

		popInfo.focus();
		
		$("#frmDetail").attr("action", "<c:url value='/edi/buy/NEDMBUY0111.do'/>");
		$("#frmDetail").attr("target", "_blankPop");
		$("#frmDetail input[name='DOC_NO']").val(docNm);
		$("#frmDetail").submit();
	}

	/* 조회기간 체크 */
	function dateValid() {
		var startDate 	= $('#startDate').val();
		var endDate 	= $('#endDate').val();
		
		if (!fnDateValid(startDate, endDate, "30")) {
			return false;
		}
		
		return true;
	}
	
	/* Excel */
	function doExcel(tbName1, tbName2){	
		
		if(!dateValid()){
			return;
		}
		
		var tBodyLen	=	$("#dataListbody tr").length;
		//console.log(tBodyLen);
		
		if (tBodyLen <= 1) {
			alert("조회결과가 없습니다.");
			return;
		}
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');

		var date 		= $("#searchForm input[name='startDate']").val() + "~" + $("#searchForm input[name='endDate']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();
		var measure		= $(":radio[name='measure']:checked").val();
		var measureTxt  = "";

		var tmp="";
	
		if(vencd==""){
			tmp="전체";
		}else{
			tmp=vencd;
		}
		
		if (measure == "1") {
			measureTxt = "미조치";
		} else {
			measureTxt = "조치";
		}
		
		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.buy.menu.rejectProd'/><br>";
		bodyValue += "[<spring:message code='text.buy.field.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='text.buy.field.vendor'/> : " + tmp + "]";
		bodyValue += "[<spring:message code='epc.buy.menu.measure'/> : " + measureTxt + "]";
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
	
	/* txt파일 생성 */	
	function printText() {
		if(!dateValid()){
			return;
		}
		if($('#entp_cd option').length < 2){
			alert('<spring:message code='text.buy.alert.noVenCd'/>');
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
		var measure = $("input[name='measure']:checked").val();
		if (measure == "1") {	// 미조치
			var tabInfo2 = {};
			tabInfo2["MEASURE"] = "A";
			TAB2.push(tabInfo2);
			
			var tabInfo2 = {};
			tabInfo2["MEASURE"] = "B";
			TAB2.push(tabInfo2);
		} else {				// 조치
			var tabInfo2 = {};
			tabInfo2["MEASURE"] = "C";
			TAB2.push(tabInfo2);
		}
		
		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon(); 
		//console.log(dataInfo);		
		
		var date 		= $("#searchForm input[name='startDate']").val() + "~" + $("#searchForm input[name='endDate']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();
		var measure		= $(":radio[name='measure']:checked").val();
		var measureTxt  = "";

		var tmp="";
	
		if(vencd==""){
			tmp="전체";
		}else{
			tmp=vencd;
		}
		
		if (measure == "1") {
			measureTxt = "미조치";
		} else {
			measureTxt = "조치";
		}
		
		var textData = "";
		textData += "[<spring:message code='text.buy.field.period'/> : " + date + "]"
		textData += "[<spring:message code='text.buy.field.vendor'/> : " + tmp + "]"
		textData += "[<spring:message code='epc.buy.menu.measure'/> : " + measureTxt + "]"
		
		//console.log(textData);
				
		$("#searchForm input[name='proxyNm']").val("INV0570");
		$("#searchForm input[name='param']").val(JSON.stringify(dataInfo));
		$("#searchForm input[name='textData']").val(textData);
		
		$("#searchForm").attr("method", "post");
		$("#searchForm").attr("action", "<c:url value='/edi/buy/NEDMBUY0110Text.do'/>");
		$("#searchForm").submit();
	}
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class=r1>
		<td align="center">
				<c:out value="\${strToDateFormat(SEND_DATE)}" />
		</td>
		<td align="center" style="mso-number-format: '\@'"><c:out value="\${PROD_CD}" /></td>
		<td align="left">&nbsp; <a href="javascript:productDetailPopup('<c:out value="\${DOC_NO}" />');"><c:out value="\${PROD_NM}" /></a></td>
		<td align="center"><c:out value="\${strToDateFormat(REG_DY)}" /></td>
		<td align="center"><c:out value="\${STR_NM}" /></td>
		<td align="center"><c:out value="\${DENY_FG}" /></td>

		{%if SEND_FG.trim() == "C"%}
			<td align="center"><input type="checkbox" checked disabled="true"></td>
		{%else%}
			<td align="center"><input type="checkbox" disabled="true"></td>
		{%/if%}
	</tr>
</script>
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>>
		<div>
			<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post" action="#">
				<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize}" />">
				<input type="hidden" id="p_str_cd" name="p_str_cd">
				<input type="hidden" id="p_occur_dy" name="p_occur_dy">
				<input type="hidden" id="p_prod_cd" name="p_prod_cd">
				<input type="hidden" id="p_send_fg" name="p_send_fg">				
				<!-- Excel 다운로드 위해 선언 -->
				<input type="hidden" name="staticTableBodyValue" 	id="staticTableBodyValue"	/>
				<input type="hidden" name="name" 					id="name"					/>
				<!-- Txt파일 생성을 위해 선언 -->
				<input type="hidden" name="proxyNm" 	id="proxyNm" 	/> 
				<input type="hidden" name="param" 		id="param" 		/> 
				<input type="hidden" name="textData" 	id="textData" 	/>
				
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message code='text.buy.field.searchCondition'/></li>
								<li class="btn">
									<a href="#" class="btn" onclick="_eventSearch();"><span><spring:message code="button.common.inquire" /></span></a>									
									<a href="#" class="btn" onclick="doExcel('headTbl','listTbl');"><span><spring:message code="button.common.excel"/></span></a>
 									<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a>									
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0"	>
								<colgroup>
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 10%" />
									<col style="" />
								</colgroup>
								<tr>
									<th><spring:message code='text.buy.field.venCd'/></th>
									<td>
										<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" dataType="CP" comType="SELECT" formName="form" defName="전체" />
									</td>
									<th></th>
									<td></td>
								</tr>
								<tr>
									<th>
										<span class="star">*</span> <spring:message code='text.buy.field.period'/>
									</th>
									<td>
										<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.startDate}" />" style="width: 80px;" />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor: hand;" />
										 ~ 
										<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber(event);" maxlength="10" readonly value="<c:out value="${paramMap.endDate}" />" style="width: 80px;" /> 
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');" style="cursor: hand;" />
									</td>

									<th>
										<span class="star">*</span> <spring:message code='epc.buy.header.messure'/>
									</th>
									<td>
										<input type="Radio" name="measure" value="1" <c:if test="${paramMap.measure == 1}"> Checked</c:if> /><spring:message code='epc.buy.header.messureN'/> 
										<input type="Radio" name="measure" value="2" <c:if test="${paramMap.measure == 2}"> Checked</c:if> /><spring:message code='epc.buy.header.messureY'/>
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
								<li class="tit"><spring:message code='text.buy.field.result'/></li>
							</ul>
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"	id="headTbl">
								<colgroup>
									<col style="width: 85px;" />
									<col style="width: 110px;" />
									<col style="width: 281px;" />
									<col style="width: 85px;" />
									<col style="width: 85px;" />
									<col style="width: 85px;" />
									<col style="width: 85px;" />
									<col style="width: 17px;" />
									<col />
								</colgroup>
								<tr class="r1">
									<th><spring:message code='epc.buy.header.sendDt'/></th>
									<th><spring:message code='epc.buy.header.prodCd'/></th>
									<th><spring:message code='epc.buy.header.prodNm'/></th>
									<th><spring:message code='epc.buy.header.occureDt'/></th>
									<th><spring:message code='epc.buy.header.occureLc'/></th>
									<th><spring:message code='epc.buy.header.occureType'/></th>
									<!-- <th>발생내역</th> -->
									<th><spring:message code='epc.buy.header.messure'/></th>
									<th>&nbsp;</th>
								</tr>
							</table>

							<div class="datagrade_scroll_all">
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"	id="listTbl">
									<colgroup>
										<col style="width: 85px;" />
										<col style="width: 110px;" />
										<col style="width: 281px;" />
										<col style="width: 85px;" />
										<col style="width: 85px;" />
										<col style="width: 85px;" />
										<col style="width: 85px;" />
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
						<li class="last"><spring:message code='epc.buy.menu.errorProd'/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>

<form id="frmDetail" name="frmDetail">
<input type="hidden" id="DOC_NO" name="DOC_NO" />
</form>

</html>

