<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Class Name : NEDMORD0010.jsp    
	Description : 발주정보 > 기간별 > 상품별
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    -------------------------------------
	2015.11.13 		  안태경 		 최초생성
	
	author   : an tae kyung
	since    : 2015.11.13
--%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title></title>

<script>
	$(document).ready(function() {

	});
	
	/* 조회 */
	function doSearch() {
		var searchInfo = {};
		
		//if (!dateValid()) {			
		//	return;
		//}
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val().replaceAll("-", "");
		// 조회기간(To)
		//searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val().replaceAll("-", "");
		// 점포 Array
		//searchInfo["searchStoreAl"] 	= storeValArrList($("#searchForm input[name='storeVal']").val());
		// 협력업체(개별)
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='searchEntpCd']").val();
		// 상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();
		
		var startDate = $("#searchForm input[name='srchStartDate']").val();
		var date = new Date(startDate);	//new Date();
		var day = date.getDay();
		
		if(!day){
			var toDate = startDate.replace(/-/gi,"");
			day = checkMonday(toDate);
		}
		
		//if (fnDateValid(searchInfo["srchStartDate"], searchInfo["srchEndDate"], "15")) {
		if (day == 1) { //월요일이면
			loadingMaskFixPos();
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/NEDMPRO0140.json"/>',
				                    
				
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});
		}else{
			alert("월요일만 선택 가능합니다.");
		};
		
	
		
		hideLoadingMask();
	}
	
	
	
	/*	
	function dateValid(){

		var startDate = $("#searchForm input[name='srchStartDate']").val();
		//var endDate = $("#searchForm input[name='srchEndDate']").val();
		var productVal=$("#searchForm input[name='productVal']").val();
		var rangeDate = 0;
		
		    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
		    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

		   var intStartDate = parseInt(startDate);
		   var intEndDate = parseInt(endDate);
		   
		   if (intStartDate > intEndDate) {
		        alert("<spring:message code='msg.common.fail.calendar'/>");
		        $("#startDate").focus();
		        return false;
		    }
			
		    intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
		    //endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 

		    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
		    rangeDate=Math.ceil(rangeDate/24/60/60/1000);
	    
			if(rangeDate>6){				
				if (productVal==""){//상품이 없으면		
					alert("상품선택이 없을시 6일을 넘을 수 없습니다. 상품을 선택하시고 기간을 늘리던지 아니면 전상품조회를 하시고 상품선택을 하시던지 하시면 됩니다.");
					$("#startDate").focus();
					return false;
				}
				else{
				
					//ok
				} 			
			}	
			return true;			
		}
	*/	
		

	/* _eventSearch() 후처리(data 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.plcProductList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");		
		
		} else {
			setTbodyNoResult("dataListbody", 9);		
		}
	}
	
	/* Excel */
	function doExcel(tbName1, tbName2, tbName3) {
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');
		var tbody3 = $('#' + tbName3 + ' tbody');

		var date 		= $("#searchForm input[name='srchStartDate']").val() //+ "~" + $("#searchForm input[name='srchEndDate']").val();
		//var store 		= $("#searchForm input[name='storeName']").val();
		var product 	= $("#searchForm input[name='productVal']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='searchEntpCd']").val();

		var tmp = "";

// 		if (store == "") {
// 			store = "전체";
// 		}

		if (product == "") {
			product = "전체";
		}
		
		if (vencd == "") {
			tmp = vendor;
		} else {
			tmp = vencd;
		}

		var bodyValue = "";
		bodyValue += "<CAPTION><spring:message code='epc.plc.plcNo'/><br>";
		bodyValue += "[<spring:message code='epc.plc.sumDy'/> : " + date + "]";
	
		bodyValue += "[<spring:message code='epc.plc.prodNm'/> : " + product + "]";
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


	
	/* 상품선택 초기화 */
	function productClear() {
		$("#searchForm input[id='productVal']").val("");
	}
	
	/* 날짜 달마다 날짜 변경 */
	function changeDayByMonth(toDate){
      	var year  = toDate.substring(0,4);
      	var month = toDate.substring(4,6);
      	var day   = toDate.substring(6,8);

      	if( ( month == '01' || month == '03' || month == '05' || month == '07' || month == '08' ||
             month == '10' || month == '12') && day > 31 ){
      		day   = Number(day) - 31;
      		month = Number(month) + 1;
          if(month==13){
            year = Number(year) + 1;
            month=1;
          }
      	}
      	else if( (month == '04' || month == '06' || month == '09' || month == '11') && day>30){
      		day   = Number(day) - 30;
          month = Number(month) + 1;

      	}
        else if( month == '02' && day>28){

          if( Number(year)%4 == 0 && day>29){
            day = Number(day) - 29;
            month = Number(month) + 1;
          }
          else if( Number(year)%4 != 0 ){
            day = Number(day) - 28;
            month = Number(month) + 1;
          }
        }
        year = year + '';
        if(typeof(day)=='number' && day<10)day = '0' + day;
        if(typeof(month)=='number' && month<10)month = '0' + month;

        return year + month + day;
    }
	/* 날짜 달마다 날짜 변경 */
	  function changeDayByMonthMinus(toDate){
	      var year  = toDate.substring(0,4);
	      var month = toDate.substring(4,6);
	      var day   = toDate.substring(6,8);
	
	      if(Number(day)==0){
	          month = month*1 -1;
	          if(month==0){
	            month=12;
	            year = year*1 - 1;
	          }
	
	          if(month == '1' || month == '3' || month == '5' || month == '7' || month == '8' ||
	            month == '10' || month == '12' ){
	              day = 31;
	          }
	          else if(month == '4' || month == '6' || month == '9' || month == '11'){
	            day = 30;
	          }
	          else {
	            if(Number(year)%4 == 0)day = 29;
	            else day = 28;
	          }
	          year  = year + '';
	          if(typeof(month)=='number' && month<10)month = '0' + month;
	          day   = day + '';
	
	          return year+month+day;
	      }
	      else{
	          return toDate;
	      }
	    //  if(day == '00' )
	  }
	  
	  /* 월요일인지 체크 */
	  function checkMonday(toDate)
	  {
		  var checkDay = Number(toDate);
	      var stdDate = Number('20200629');
	      var checkMondayIdx = 0;
	      
	      while(stdDate != checkDay)
	      {
	        if(stdDate<checkDay)
	        {
	          stdDate++;
	          stdDate = Number(changeDayByMonth(stdDate+''));
	        }
	        else
	        {
	          stdDate--;
	          stdDate = Number(changeDayByMonthMinus(stdDate+''));
	        }
	
	        checkMondayIdx++;
	      }
	
	      if(checkMondayIdx%7==0){
	        return true;
	      }
	      else {
	        return false;
	      }
	}
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1">
		<td align="center"><c:out value="\${sumDy}"/></td>
		<td align="center"><c:out value="\${sumWk}"/></td>
		<td align="center" style="mso-number-format:'\@'"><c:out value="\${prodCd}"/></a></td>	
		<td align="center" style="mso-number-format:'\@'"><c:out value="\${srcmkCd}"/></a></td>	
		<td align="center"><c:out value="\${l1Nm}"/></td>
		<td align="center"><c:out value="\${prodNm}"/></td>	
		<td align="center"><c:out value="\${gradeNm}"/></td>
		<td align="center"><c:out value="\${zzekorg}"/></td>
		<td align="center"><c:out value="\${plcFg}"/></td>
	</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>>
		<div>
			<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post" action="#">
				<input type="hidden" id="mode" name="mode" />
				<input type="hidden" name="text_data" />
				<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven}" />">
		
				<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
				<input type="hidden" name="name">
				<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize}" />">
				<input type="hidden" id="textData" name="textData" />
				<input type="hidden" id="searchProductVal" name="searchProductVal" />
				<%-- <input type="hidden" name="textList" value="$<c:out value="{orderList}" />" /> --%>
				
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message code='epc.plc.searchCondition' /><!-- <strong>&nbsp;<font color="red">※현재 발주 데이터 오류현상이 발생하고 있으니 참고하시기 바랍니다.</font></strong> --></li>
								<li class="btn">
									<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire" /></span></a>
									<a href="#" class="btn" onclick="doExcel('headTbl','listTbl','sumTbl');"><span><spring:message code="button.common.excel" /></span></a>
									
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">

								
								<input type="hidden" id="productVal" name="productVal" value="<c:out value="${param.productVal }" />" />

								<colgroup>
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 10%" />
									<col style="" />
								</colgroup>
								<tr>
									<th>
										조회일
										<td colspan=3>									
										<input type="text" class="day" id="srchStartDate" name="srchStartDate" value="<c:out value="${paramMap.srchStartDate}" />" style="width: 80px;"> 
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor: hand;" />
									<!-- 	 ~ 
										<input type="text" class="day" id="srchEndDate" name="srchEndDate" value="<c:out value="${paramMap.srchEndDate}" />" style="width: 80px;"> 
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');" style="cursor: hand;" />
										 -->
									</td>
								</tr>
								<tr>
									<th>
										<spring:message code="epc.plc.venCd" arguments="" />
									</th>
									<td>
										<html:codeTag objId="searchEntpCd" objName="searchEntpCd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
									</td>
									<th>
										<spring:message code="epc.plc.prodCd" arguments="" />
									</th>
									<td>
										<input type="Radio" name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if> onclick="javascript:productClear();" />전상품조회 
										<input type="Radio" name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();" />상품선택
									</td>
								</tr>
								<tr>
								<td>
								
								</td>
								</tr>
								
							</table>
							<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
							<tr>
								<td colspan="6" bgcolor=ffffff>
									<font color="red">
									&nbsp;※선택한 월요일의 전주 데이터가 조회됩니다.
									</font>
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
								<li class="tit"><spring:message code="epc.plc.search"
										arguments="" /></li>
							</ul>
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
								<colgroup>
									<col style="width: 70px;" />
										<col style="width: 50px;" />
										<col style="width: 80px;" />
										<col style="width: 100px;" />
										<col style="width: 80px;" />
										<col style="width: 200px;" />
										<col style="width: 60px;" />
										<col style="width: 100px;" />
									<col  />
								
								</colgroup>
								<tr>
									<th><spring:message code="epc.plc.sumDy" arguments="" /></th>
									<th><spring:message code="epc.plc.sumWk" arguments="" /></th>
									<th><spring:message code="epc.plc.prodCd" arguments="" /></th>
									<th><spring:message code="epc.plc.srcmkCd" arguments="" /></th>
									<th><spring:message code="epc.plc.l1Nm" arguments="" /></th>
									<th><spring:message code="epc.plc.prodNm" arguments="" /></th>	
									<th><spring:message code="epc.plc.gradeNm" arguments="" /></th>
									<th><spring:message code="epc.plc.zzekorg" arguments="" /></th>
									<th><spring:message code="epc.plc.plcFg" arguments="" /></th>
									
									
							</tr>
							</table>
							
							<div class="datagrade_scroll_sum">
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="listTbl">
									<colgroup>
										<col style="width: 70px;" />
										<col style="width: 50px;" />
										<col style="width: 80px;" />
										<col style="width: 100px;" />
										<col style="width: 80px;" />
										<col style="width: 200px;" />
										<col style="width: 60px;" />
										<col style="width: 100px;" />
										<col />
									</colgroup>
									<!-- Data List Body Start ------------------------------------------------------------------------------>
									<tbody id="dataListbody" />
									<!-- Data List Body End   ------------------------------------------------------------------------------>
								</table>
							</div>
							
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="sumTbl">
								<colgroup>
										<col style="width: 70px;" />
										<col style="width: 50px;" />
										<col style="width: 80px;" />
										<col style="width: 100px;" />
										<col style="width: 80px;" />
										<col style="width: 200px;" />
										<col style="width: 60px;" />
										<col style="width: 100px;" />
										<col />
								</colgroup>							
								<tr id="emptyRow" style="display: none;">
									<td colspan=9>&nbsp;</td>
									<th width=17>&nbsp;</th>
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
				<div class="notice">※ 처분진행 중인 상품은 리스트에서 제외됩니다.</div>
				<div class="location">
					
					<ul>
						<li>홈</li>
						<li>상품</li>
						<li>상품현황관리</li>
						<li class="last">PLC등급 조회</li>
					</ul>
				
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>
