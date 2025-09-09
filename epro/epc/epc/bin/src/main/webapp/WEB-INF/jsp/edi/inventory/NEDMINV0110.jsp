<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	/* 조회 */
	function _eventSearch() {
		
		if (!dateValid()) {
			return;
		}
		
		if ($('#entp_cd option').length < 2) {
			alert('<spring:message code='text.buy.alert.noVenCd'/>');
			return ;
		}
		
		var dataInfo = {};
		dataInfo["START_DATE"] 	= $('#startDate').val().replaceAll('-', '');
		dataInfo["END_DATE"] 	= $('#endDate').val().replaceAll('-', '');
		
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

			var tabInfo2 = {};
		  tabInfo2["MEASURE"] = "1";
		  TAB2.push(tabInfo2);

      var tabInfo2 = {};
      tabInfo2["MEASURE"] = "2";
      TAB2.push(tabInfo2);

      var tabInfo2 = {};
      tabInfo2["MEASURE"] = "3";
      TAB2.push(tabInfo2);

      var tabInfo2 = {};
      tabInfo2["MEASURE"] = "4";
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
		rfcCall("INV0600" , dataInfo);
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
	
	function productDetailPopup(docNm, truFg){	
		var popInfo = window.open('','_blankPop','top=0, left=0, width=900, height=450, toolbar=no, status=yes, scrollbars=yse');

		popInfo.focus();
		
		$("#frmDetail").attr("action", "<c:url value='/edi/inventory/NEDMINV0111.do'/>");

		// truFg의 값에 따라 분기
		// 고백 제거
		truFg = truFg.replace(/ /g, '');
		if (truFg === "X") {
		  $("#frmDetail").attr("action", "<c:url value='/edi/inventory/NEDMINV0112.do'/>");
		}
		
		$("#frmDetail").attr("target", "_blankPop");
		$("#frmDetail input[name='DOC_NO']").val(docNm);
		$("#frmDetail").submit();
		
		
		//$form.submit();

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
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class=r1>
		<td align="center"><c:out value="\${SEND_DATE}" /></td>
		<td align="center" style="mso-number-format: '\@'"><c:out value="\${PROD_CD}" /></td>
		<td align="left">&nbsp; <a href="javascript:productDetailPopup('<c:out value="\${DOC_NO}" />', '<c:out value="\${TRU_FG}" />');"><c:out value="\${PROD_NM}" /></a></td>
		<td align="center"><c:out value="\${OCCUR_DY}" /></td>
		<td align="center"><c:out value="\${STR_NM}" /></td>
		<td align="center"><c:out value="\${BAD_L1_NM}" /></td>
		<td align="center"><c:out value="\${BAD_CAT_NM}" /></td>
		{%if SEND_FG == "C "%}
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
				<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }">
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message code='text.buy.field.searchCondition'/></li>
								<li class="btn">
									<a href="#" class="btn" onclick="_eventSearch();"><span><spring:message code="button.common.inquire" /></span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 10%" />
									<col style="" />
								</colgroup>
								<tr>
									<th><spring:message code='text.buy.field.venCd'/></th>
									<td>
										<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
									</td>
									<th></th>
									<td></td>
								</tr>
								<tr>
									<th>
										<span class="star">*</span> <spring:message code='text.buy.field.period'/>
									</th>
									<td>
										<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.startDate}" />" style="width: 80px;" />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor: hand;" />
										 ~ 
										<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.endDate}" />" style="width: 80px;" /> 
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
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:85px;" />
									<col style="width:85px;" />
									<col style="width:200px;" />
									<col style="width:85px;" />
									<col style="width:85px;" />
									<col style="width:85px;" />
									<col style="width:85px;" />
									<col  />
								</colgroup>
								<tr class="r1">
									<th><spring:message code='epc.inv.header.0111'/></th>
									<th><spring:message code='epc.inv.header.0112'/></th>
									<th><spring:message code='epc.inv.header.0113'/></th>
									<th><spring:message code='epc.inv.header.0114'/></th>
									<th><spring:message code='epc.inv.header.0115'/></th>
									<th><spring:message code='epc.inv.header.0116'/></th>
									<th><spring:message code='epc.inv.header.0117'/></th>
									<th><spring:message code='epc.inv.header.0118'/></th>
								</tr>
							</table>

							<div class="datagrade_scroll_all">
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
									<colgroup>
										<col style="width:85px;" />
										<col style="width:85px;" />
										<col style="width:200px;" />
										<col style="width:85px;" />
										<col style="width:85px;" />
										<col style="width:85px;" />
										<col style="width:85px;" />
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
						<li><spring:message code='epc.inv.menu.lvl1'/></li>
						<li><spring:message code='epc.inv.menu.lvl2'/></li>
						<li><spring:message code='epc.inv.menu.lvl3_1'/></li>
						<li class="last"><spring:message code='epc.inv.menu.errorProd'/></li>
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

