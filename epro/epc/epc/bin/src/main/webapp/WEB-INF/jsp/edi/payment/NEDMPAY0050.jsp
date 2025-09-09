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
		var srchEndDate =  $("#searchForm select[name='endDate_year']").val()+"-"+$("#searchForm select[name='endDate_month']").val();
		$('#srchStartDate').val(srchStartDate);
		$('#srchEndDate').val(srchEndDate);
		
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= srchStartDate;
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= srchEndDate
		// 점포 Array
		searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/payment/NEDMPAY0050Select.json"/>',
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
				var str_sply_amt_sum = 0;
				var sub_amt_sum = 0;
				var vat_sum = 0;
				var sub_sum_sum = 0;
				
				var strSplyAmt = 0;
				var subAmt = 0;
				var vat = 0;
				var subSum = 0;
				
				// 금액 및 수량 comma 및 합계
				for (var i = 0; i < data.length; i++) {
					strSplyAmt = data[i]["strSplyAmt"];
					subAmt = data[i]["subAmt"];
					vat = data[i]["vat"];
					subSum = data[i]["subSum"];
					
					// 합계
					str_sply_amt_sum 	+= parseInt(strSplyAmt);
					sub_amt_sum 	+= parseInt(subAmt);
					vat_sum 	+= parseInt(vat);
					sub_sum_sum 	+= parseInt(subSum);
					
					// Comma 설정
					data[i]["strSplyAmt"] 	= setComma(strSplyAmt);
					data[i]["subAmt"] 	= setComma(subAmt);
					data[i]["vat"] 	= setComma(vat);
					data[i]["subSum"] 	= setComma(subSum);
				}
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
				
				$('#str_sply_amt_sum').text(setComma(str_sply_amt_sum));
				$('#sub_amt_sum').text(setComma(sub_amt_sum));
				$('#vat_sum').text(setComma(vat_sum));
				$('#sub_sum_sum').text(setComma(sub_sum_sum));
				
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


	function dateValid(){

		var startDate = $('#srchStartDate').val()+"01";
		var endDate = $('#srchEndDate').val()+"01"
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

	function popupSearch(tbName1, tbName2, tbName3){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');	
		var tbody3 = $('#' + tbName3 + ' tbody');		


		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var store 		= $("#searchForm input[name='storeName']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
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
		bodyValue += "<CAPTION><spring:message code='epc.pay.dash.mulu'/><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.store'/> : " + store + "]";
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
		var srchStartDate = $("#searchForm select[name='startDate_year']").val()+"-"+$("#searchForm select[name='startDate_month']").val();
		var srchEndDate =  $("#searchForm select[name='endDate_year']").val()+"-"+$("#searchForm select[name='endDate_month']").val();

		// 조회기간 설정
		var date = srchStartDate + "~" + srchEndDate;
		// 점포명
		var store 		= $("#searchForm input[name='storeName']").val();
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

		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.store'/> : " + store + "]"
		textData += "[업체 : "+tmp+"]";
		$("#searchForm input[name='textData']").val(textData);
		$("#searchForm input[name='searchEntpCd']").val(vencd);
		
		$("#searchForm").attr("action", "<c:url value='/edi/payment/NEDMPAY0050Text.do'/>");
		$("#searchForm").submit();
	

	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center"><c:out value="\${subMm}"/></td>
	<td align="center"><c:out value="\${strNm}"/></td>
	<td align=right><c:out value="\${strSplyAmt}"/></td>
	<td align=right><c:out value="\${subAmt}"/></td>
	<td align=right><c:out value="\${vat}"/></td>
	<td align=right><c:out value="\${subSum}"/></td>
	<td align=right><c:out value="\${rate}"/></td>
</tr>
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" id="textData" name="textData" />
		<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
		<input type="hidden" id="storeName" name="storeName" />
		<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >
		
		<input type="hidden"	id="searchEntpCd"	name="searchEntpCd"/>
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.pay.search.condition'/>검색조건</li>
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
							<html:codeTag objId="startDate_year" objName="startDate_year" selectParam="<c:out value='${paramMap.startDate_year}'/>" dataType="YEAR" subCode="5" comType="SELECT" formName="form"  />
							<html:codeTag objId="startDate_month" objName="startDate_month" selectParam="<c:out value='${paramMap.startDate_month}'/>" dataType="MONTH" comType="SELECT" formName="form"  />
							~
							<html:codeTag objId="endDate_year" objName="endDate_year" selectParam="<c:out value='${paramMap.endDate_year}'/>" dataType="YEAR" subCode="5" comType="SELECT" formName="form"  />
							<html:codeTag objId="endDate_month" objName="endDate_month" selectParam="<c:out value='${paramMap.endDate_month}'/>" dataType="MONTH" comType="SELECT" formName="form"  />
							
							<input type="hidden" class="day" id="srchStartDate" name="srchStartDate">
							<input type="hidden" class="day" id="srchEndDate" name="srchEndDate">
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
						<col style="width:90px;" />
						<col style="width:100px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th><spring:message code='epc.pay.header.subMm'/></th>
						<th><spring:message code='epc.pay.header.strNm'/></th>
						<th><spring:message code='epc.pay.header.strSplyAmt'/></th>
						<th><spring:message code='epc.pay.header.subAmt'/></th>
						<th><spring:message code='epc.pay.header.vat'/></th>
						<th><spring:message code='epc.pay.header.totalSum'/></th>
						<th><spring:message code='epc.pay.header.rate'/></th>
						<th>&nbsp;</th>
					</tr>
					</table>
					
					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					<div style="width:100%; height:410px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:90px;" />
								<col style="width:100px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col style="width:120px;" />
								<col  />
							</colgroup>
							<!-- Data List Body Start ------------------------------------------------------------------------------>
							<tbody id="dataListbody" />
							<!-- Data List Body End   ------------------------------------------------------------------------------>
						</table>
					</div>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:100px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
						<tr style="display:none;" class="r1 sum">
							<th colspan="2" class="fst" align=center><b><spring:message code='epc.pay.header.totalSum'/></b></td>
							<th align=right><b id="str_sply_amt_sum"></b></th>
							<th align=right><b id="sub_amt_sum"></b></th>
							<th align=right><b id="vat_sum"></b></th>
							<th align=right><b id="sub_sum_sum"></b></th>
							<th></th>
							<th>&nbsp;</th>
						</tr>
						<tr style="display:none;" class="empty"><td colspan=7>&nbsp;</td><th width=17>&nbsp;</th></tr>
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
					<li class="last"><spring:message code='epc.pay.muluInfo'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
