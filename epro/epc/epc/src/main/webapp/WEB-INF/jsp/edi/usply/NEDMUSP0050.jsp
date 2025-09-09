<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	$(function(){
		if('<c:out value ="${param.mode}" />' == 'select'){
			doSearch();
		}
	});
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
		searchInfo["searchEntpCd"] 		= $("#searchForm select[name='entp_cd']").val();
		//판매코드
		searchInfo["srchSellCode"] 		= $("#srchSellCode").val();
		//상품코드
		searchInfo["searchProductVal"] 	= $("#searchForm input[name='productVal']").val();

		
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/usply/NEDMUSP0050Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});	
		}
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.usplyList;
			var tempData = [];
			
			for(var i = 0;i<data.length;i++){
				tempData[i] =  data[i];
				if(tempData[i].regReasonFg != tempData[i].cfmReasonFg || 
				tempData[i].regDetailFg != tempData[i].cfmDetailFg){
					data[i].buyerConfirm = '바이어\r\n확정';
				} else{
					data[i].buyerConfirm = '바이어\r\n미확정';
				}
			}
				
			setTbodyInit("dataListbody");	// dataList 초기화
			
			if (data.length > 0) {
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
				$('.currencySymbol').each(function(){
					var tempVal = $(this).text();
					$(this).text(setComma(tempVal));
				});
			} else { 
				setTbodyNoResult("dataListbody", 9);
			}
		}
		return;
			
	}

	function doUpdateSearch() {
		
	
		var url = "<c:url value='/edi/usply/NEDMUSP0050UpdateSelect.do'/>";
		var searchStartDate = $("#searchForm input[name='srchStartDate']").val();
		var searchEndDate = $("#searchForm input[name='srchEndDate']").val();
		var searchEntpCd = $("#searchForm select[name='entp_cd']").val();
		var srchSellCode = $("#searchForm input[name='srchSellCode']").val();
		var searchProductVal = $("#searchForm input[name='productVal']").val();		
	
		
		
		$('#doUpdateSearchForm input[name="srchStartDate"]').val(searchStartDate);
		$('#doUpdateSearchForm input[name="srchEndDate"]').val(searchEndDate);
		$('#doUpdateSearchForm input[name="searchEntpCd"]').val(searchEntpCd);
		$('#doUpdateSearchForm input[name="srchSellCode"]').val(srchSellCode);		
		$('#doUpdateSearchForm input[name="searchProductVal"]').val(searchProductVal);
		//alert(searchProductVal);
		
		$('#doUpdateSearchForm').attr('action' , url);
		
		if (dateValid()) {
			loadingMaskFixPos();
			$('#doUpdateSearchForm').submit();
		}	
	}


	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(){

		var startDate = $("#searchForm input[name='srchStartDate']").val();
		var endDate = $("#searchForm input[name='srchEndDate']").val();
		
		var rangeDate = 0;
		
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			$("#startDate").focus();
			return false;
		}


		// startDate, endDate 는 yyyy-mm-dd 형식

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
	    endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);

		if(rangeDate>15){
			//alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			alert("검색일자가 범위가 15일을 넘을 수 없습니다.");
			$("#startDate").focus();
			return false;
		}	
		return true;
		
	}
	
	
	function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		

		var date 		= $("#searchForm input[name='srchStartDate']").val() + "~" + $("#searchForm input[name='srchEndDate']").val();
		var vendor 		= $("#searchForm input[name='vendor']").val();
		var vencd 		= $("#searchForm select[name='entp_cd']").val();

		var tmp="";

		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}
		
		var bodyValue = "";
		bodyValue += "<CAPTION>미납정보 미납사유조회 현황표<br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
		bodyValue += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]";
		bodyValue += "<br>";
		bodyValue += "</CAPTION>";
		bodyValue += tbody1.parent().html();
		bodyValue += tbody2.parent().html();
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
		if (!dateValid()) {			
			return;
		}
		
		// 조회시작일자
		var srchStartDate 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회 종료일자
		var srchEndDate 	= $("#searchForm input[name='srchEndDate']").val();
		// 조회기간 설정
		var date = srchStartDate + "~" + srchEndDate;		
		// 협력업체 코드(배열)
		var vendor 		= $("#searchForm input[name='vendor']").val();
		// 협력업체 코드(단독)
		var vencd 		= $("#searchForm select[name='entp_cd']").val();
			
		var tmp = "";
		if (vencd == "") {
			tmp = "전체";
		} else {
			tmp = vencd;
		}
		
		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		
		//console.log(textData);
		
		$("#searchForm input[name='textData']").val(textData);		
		$("#searchForm input[name='searchEntpCd']").val($("#searchForm select[name='entp_cd']").val());
		
		$("#searchForm").attr("action", "<c:url value='/edi/usply/NEDMUSP0050Text.do'/>");
		$("#searchForm").submit();
	}
	
	function productClear(){
		$("#searchForm input[id='productVal']").val("");
		$("#searchForm input[id='searchSrcmkVal']").val("");
	}
	
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">

<tr class="r1">
	<td align="center"><c:out value="\${splyDy }"/><br></td>
   
	<td align="center" style="mso-number-format:'\@'"><c:out value="\${prodCd }"/><br><c:out value="\${srcmkCd }"/></td>
	<td align="left"><c:out value="\${prodNm }"/></td>
	<td align="center"><c:out value="\${strNm }"/></td>
	<td align="right"><c:out value="\${usplyQty }"/></td>
	<td align="right"><c:out value="\${usplyBuyAmt }"/></td>
	<td align="center"><c:out value="\${venReasonNm }"/></td>
	<td align="center"><c:out value="\${venDetailNm }"/></td>
	<td colspan="2" align="center">\${buyerConfirm}</td>
</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">		
		<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">		
		<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
		<input type="hidden" name="name">		
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >
		<input type="hidden"	name="textData"					id="textData"				/>
		<input type="hidden"	name="searchEntpCd"				id="searchEntpCd"			/>
		
		<input type="hidden" id="productVal" name="productVal" value="<c:out value="${param.productVal }" />" />
				
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.usp.search.condition'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a>
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a> 
							<a href="#" class="btn" onclick="doUpdateSearch();"><span><spring:message code='epc.usp.button.createPage'/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='epc.usp.search.searchPeriod'/> </th>
						<td>
							<input type="text" class="day" id="startDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
						
						 <th>&nbsp;</th>
						<td>
						</td>	
						
						
					</tr>
					<tr>
					<!-- 	<th>&nbsp;</th>
						<td>
						</td>
						<th>판매(88)코드</th>
								<td><input type="text" id="srchSellCode" name="srchSellCode" style="width:120px;" value="${param.srchSellCode}" />
								</td>
					 -->	
					 
						<th><spring:message code='epc.usp.search.entpCd'/></th>
						<td><!--defName="전체"  -->
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entp_cd}" dataType="CP" comType="SELECT" formName="form"  />
						</td>		
						<th><spring:message code='epc.usp.search.prodCd'/></th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message code='epc.usp.search.allProd'/>   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productClear();productPopUp();"/><spring:message code='epc.usp.search.prodSelect'/>
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
						<li class="tit"><spring:message code='epc.usp.search.result'/></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:70px;" />
					<!-- 	<col style="width:80px;" /> -->
						<col style="width:100px;" />
						<col style="width:150px;" />
					<!--	<col style="width:40px;" />
					  	<col style="width:40px;" />		-->
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:60px;" />
						<col style="width:80px;" />
						<col style="width:140px;" />
						<col  />
					<!-- 	<col style="width:17px;" />   -->
					</colgroup>
					<tr>
						<th><spring:message code='epc.usp.header.splyDy'/></th>
					<!--	<th><spring:message code='epc.usp.header.prodCd'/></th>  -->
					<!--	<th><spring:message code='epc.usp.header.srcmkCd'/></th> -->
						<th><spring:message code='epc.usp.header.prodCdSrcmkCd'/></th>
						
						<th><spring:message code='epc.usp.header.prodNm'/></th>
					<!--	<th><spring:message code='epc.usp.header.ordIpsu'/></th> 
					 	<th><spring:message code='epc.usp.header.unit'/></th>-->
						<th><spring:message code='epc.usp.header.strNm'/></th>
						<th><spring:message code='epc.usp.header.usplyQty'/></th>
						<th><spring:message code='epc.usp.header.usplyBuyAmt'/></th>
						<th><spring:message code='epc.usp.header.venReasonNm'/></th>
						<th><spring:message code='epc.usp.header.venDetailNm'/></th>
					  	<th><spring:message code='epc.usp.header.confirmYN'/></th>  					 
					 	<th>&nbsp;</th>
					</tr>
					</table>
					<div class="datagrade_scroll">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:70px;" />
						<!--		<col style="width:80px;" /> -->
								<col style="width:100px;" />
								<col style="width:150px;" />
						<!--		<col style="width:40px;" />
								<col style="width:40px;" />-->
								<col style="width:70px;" />
								<col style="width:60px;" />
								<col style="width:60px;" />
								<col style="width:80px;" />
								<col style="width:140px;" />
								<col  />
						<!-- 		<col style="width:17px;" />  -->
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
<form id="doUpdateSearchForm" name="doUpdateSearchForm" method="post" action="#">
	<input type = "hidden" id ="srchStartDate" name = "srchStartDate" />
	<input type = "hidden" id ="srchEndDate" name = "srchEndDate" />
	<input type = "hidden" id ="searchEntpCd" name = "searchEntpCd" /> 
	<input type = "hidden" id ="srchSellCode" name = "srchSellCode" /> 	
	<input type = "hidden" id ="searchProductVal" name = "searchProductVal" /> 	

</form>

	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li><spring:message code='epc.usp.home'/></li>
					<li><spring:message code='epc.usp.usplyInfo'/></li>
					<li><spring:message code='epc.usp.dateInfo'/></li>
					<li class="last"><spring:message code='epc.usp.venReasonInputAndInquiries'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
