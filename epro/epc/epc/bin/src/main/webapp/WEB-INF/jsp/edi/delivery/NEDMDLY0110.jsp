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
		rfcCall("INV0630" , dataInfo);
	}
	
	/*  */
	function _setTbodyMasterValue(data) { 
		setTbodyInit("dataListbody");	// dataList 초기화			
		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
	}
	
	/* rfc 통신 서비스가 여러개일 경우 전역변수 설정후 분기 태우시면 됩니다. */
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

	/* 필수항목 체크 */
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
		 
		var form=document.forms[0];	


		var form=document.forms[0];	

		var date 		= $("#searchForm input[name='startDate']").val() + "~" + $("#searchForm input[name='endDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
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

	
		var bodyValue = "";
		bodyValue += "<CAPTION>TOY배달정보  현황표<br>";
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
</script>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center"><c:out value="\${SALE_DY}" /></td>
	<td align="center"><c:out value="\${VEN_CD}" /></td>
	<td align="center"><c:out value="\${ACCEPT_FG0}" /></td>
	<td align="center"><c:out value="\${ACCEPT_FG1}" /></td>
	<td align="center"><c:out value="\${ACCEPT_FG2}" /></td>
	<td align="center"><c:out value="\${ACCEPT_FG3}" /></td>
	<td align="center"><c:out value="\${ACCEPT_FG4}" /></td>
</tr>
</script>

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
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
						<li class="tit"><spring:message code='text.dly.field.searchCondition' /></li>
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
						<th><span class="star">*</span> <spring:message code='text.dly.field.period' /> </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> <spring:message code='text.dly.field.strCdSelect' /></th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/><spring:message code='text.dly.field.allStore' />
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/><spring:message code='text.dly.field.strCdSelect' />
						</td>
					</tr>
					<tr>
						<th><spring:message code='text.dly.field.venCd' /></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entp_cd}" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<th></th><td></td>
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
						<li class="tit"><spring:message code='text.dly.field.result' /></li>
	
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col  />
					</colgroup>
					<tr>
						<th><spring:message code='epc.dly.header.h01101' /></th>
						<th><spring:message code='epc.dly.header.h01102' /></th>
						<th><spring:message code='epc.dly.header.h01103' /></th>
						<th><spring:message code='epc.dly.header.h01104' /></th>
						<th><spring:message code='epc.dly.header.h01105' /></th>
						<th><spring:message code='epc.dly.header.h01106' /></th>
						<th><spring:message code='epc.dly.header.h01107' /></th>
					</tr>
					</table>
					
					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					<div class="datagrade_scroll_sum">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:110px;" />
								<col style="width:110px;" />
								<col style="width:110px;" />
								<col style="width:110px;" />
								<col style="width:110px;" />
								<col style="width:110px;" />
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
					<li class="last"><spring:message code='epc.dly.menu.dash' /></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
