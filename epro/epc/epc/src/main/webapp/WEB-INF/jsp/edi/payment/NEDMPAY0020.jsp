<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
<style>
	td.dedu_add{
		background-color="#E6E6E6";
	}
</style>
<script>
	
	$(function() {
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
		
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/payment/NEDMPAY0020Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
					/* var test_css = $('.dedu_odd');
					console.log(test_css);
					test_css.css("background-color","#FAFAFA"); */
				}
			});	
		}
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.paymentList;
			console.log(data);
			
			setTbodyInit("dataListbody");	// dataList 초기화

			if (data.length > 0) {
				var pay_buy = 0;
				
				var mul 			 = 0;
				var usply	 		 = 0;
				var infoAnlyFee	     = 0;
				var sinsang 		 = 0;
				var sinsang2		 = 0;
				var newProdIncentFee = 0;
				var ehbtIncentFee	 = 0;
				var pfrmIncentFee	 = 0;
				var facilityFee 	 = 0;
				var onlineAdFee		 = 0;
				var movStdFee 		 = 0;
				var bottDedu 		 = 0;
				var alcoBottDedu 	 = 0;
				var digitalAdFee 	 = 0;
				var etcDedu 	     = 0;
				
				var total 			 = 0;
				var occation_pay_buy = 0;
				var sub_buy 		 = 0;		
				var occation_sub_pay = 0;
				var realPayBuy 		 = 0;
				
				var pay_buy_sum 	 = 0;
				
				var mul_sum 			 = 0;
				var usply_sum	 		 = 0;
				var infoAnlyFee_sum	     = 0;
				var sinsang_sum 		 = 0;
				var sinsang2_sum		 = 0;
				var newProdIncentFee_sum = 0;
				var ehbtIncentFee_sum	 = 0;
				var pfrmIncentFee_sum	 = 0;
				var facilityFee_sum 	 = 0;
				var onlineAdFee_sum	 	 = 0;
				var movStdFee_sum 		 = 0;
				var bottDedu_sum 		 = 0;
				var alcoBottDedu_sum 	 = 0;
				var digitalAdFee_sum 	 = 0;
				var etcDedu_sum 	     = 0; 	
				
				var total_sum 			 = 0;
				var occation_pay_buy_sum = 0;
				var sub_buy_sum			 = 0;
				var occation_sub_pay_sum = 0;
				var realPayBuy_sum 		 = 0;
				
				//[PIY] 생략한 정보를 제외 한 나머지르 정보를 옮길 객체
				var tmpData = [0];
				tmpData.pop();
				
				// 금액 및 수량 comma 및 합계
				for (var i = 0; i < data.length; i++) {
					pay_buy = data[i]["payBuy"];
					
					mul			     = data[i]["mul"];
					usply 			 = data[i]["usply"];
					infoAnlyFee 	 = data[i]["infoAnlyFee"];
					sinsang 		 = data[i]["sinsang"];
					sinsang2 		 = data[i]["sinsang2"];
					newProdIncentFee = data[i]["newProdIncentFee"];
					ehbtIncentFee	 = data[i]["ehbtIncentFee"]
					pfrmIncentFee    = data[i]["pfrmIncentFee"];
					facilityFee 	 = data[i]["facilityFee"];
					onlineAdFee		 = data[i]["onlineAdFee"];
					movStdFee 		 = data[i]["movStdFee"];
					bottDedu 		 = data[i]["bottDedu"];
					alcoBottDedu 	 = data[i]["alcoBottDedu"];
					digitalAdFee 	 = data[i]["digitalAdFee"];
					etcDedu			 = data[i]["etcDedu"]
					
					total			 = data[i]["total"];
					occation_pay_buy = data[i]["occationPayBuy"];
					sub_buy 		 = data[i]["subBuy"];
					occation_sub_pay = data[i]["occationSubPay"];
					realPayBuy		 = data[i]["realPayBuy"];
					
					// 합계
					pay_buy_sum 		 += parseInt(pay_buy);
					
					mul_sum 	 		 += parseInt(mul);
					usply_sum 			 += parseInt(usply);
					infoAnlyFee_sum		 += parseInt(infoAnlyFee);
					sinsang_sum 		 += parseInt(sinsang);
					sinsang2_sum 		 += parseInt(sinsang2);
					newProdIncentFee_sum += parseInt(newProdIncentFee);
					ehbtIncentFee_sum 	 += parseInt(ehbtIncentFee);
					pfrmIncentFee_sum 	 += parseInt(pfrmIncentFee);
					facilityFee_sum 	 += parseInt(facilityFee);
					onlineAdFee_sum		 += parseInt(onlineAdFee);
					movStdFee_sum 		 += parseInt(movStdFee);
					bottDedu_sum 		 += parseInt(bottDedu);
					alcoBottDedu_sum 	 += parseInt(alcoBottDedu);
					digitalAdFee_sum 	 += parseInt(digitalAdFee); 	
					etcDedu_sum 		 += parseInt(etcDedu);
					
					total_sum 			 += parseInt(total);
					occation_pay_buy_sum += parseInt(occation_pay_buy);
					sub_buy_sum 		 += parseInt(sub_buy);
					occation_sub_pay_sum += parseInt(occation_sub_pay);
					//realPayBuy_sum += parseInt(Number(pay_buy) - Number(total) - Number(occation_sub_pay));
					realPayBuy_sum 		 += Number(realPayBuy);
					
					// Comma 설정
					data[i]["payBuy"]= setComma(pay_buy);
					
					data[i]["mul"]	    	   = setComma(mul);
					data[i]["usply"]		   = setComma(usply);
					data[i]["infoAnlyFee"]	   = setComma(infoAnlyFee);
					data[i]["sinsang"]		   = setComma(sinsang);
					data[i]["sinsang2"]		   = setComma(sinsang2);
					data[i]["newProdIncentFee"]= setComma(newProdIncentFee);
					data[i]["ehbtIncentFee"]   = setComma(ehbtIncentFee);
					data[i]["pfrmIncentFee"]   = setComma(pfrmIncentFee);
					data[i]["facilityFee"]     = setComma(facilityFee);
					data[i]["onlineAdFee"]	   = setComma(onlineAdFee);
					data[i]["movStdFee"]	   = setComma(movStdFee);
					data[i]["bottDedu"]		   = setComma(bottDedu);
					data[i]["alcoBottDedu"]    = setComma(alcoBottDedu);
					data[i]["digitalAdFee"]    = setComma(digitalAdFee);
					data[i]["etcDedu"]         = setComma(etcDedu);
					
					data[i]["total"]= setComma(total);
					data[i]["occationPayBuy"]= setComma(occation_pay_buy);
					data[i]["occation_pay_buy"]= setComma(occation_pay_buy);
					data[i]["subBuy"]= setComma(sub_buy);
					data[i]["occation_sub_pay"]= setComma(occation_sub_pay);
					//data[i]["realPayBuy"]= setComma(Number(pay_buy) - Number(total) - Number(occation_sub_pay));
					data[i]["realPayBuy"]= setComma(Number(realPayBuy));
					
					// [PIY] 인천터미널점 가진 정보 생략
					if(data[i]["strNm"]==='인천터미널점')continue;	
					tmpData.push(data[i]);		
				}
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
				 $('#pay_buy_sum').text(setComma(pay_buy_sum));
				
				 $('#mul_sum').text(setComma(mul_sum));
				 $('#usply_sum').text(setComma(usply_sum));
				 $('#infoAnlyFee_sum').text(setComma(infoAnlyFee_sum));
				 $('#sinsang_sum').text(setComma(sinsang_sum));
				 $('#sinsang2_sum').text(setComma(sinsang2_sum));
				 $('#newProdIncentFee_sum').text(setComma(newProdIncentFee_sum));
				 $('#ehbtIncentFee_sum').text(setComma(ehbtIncentFee_sum));
				 $('#pfrmIncentFee_sum').text(setComma(pfrmIncentFee_sum));
				 $('#facilityFee_sum').text(setComma(facilityFee_sum));
				 $('#onlineAdFee_sum').text(setComma(onlineAdFee_sum));
				 $('#movStdFee_sum').text(setComma(movStdFee_sum));
				 $('#bottDedu_sum').text(setComma(bottDedu_sum));
				 $('#alcoBottDedu_sum').text(setComma(alcoBottDedu_sum));
				 $('#digitalAdFee_sum').text(setComma(digitalAdFee_sum));
				 $('#etcDedu_sum').text(setComma(etcDedu_sum));
				 
				 $('#total_sum').text(setComma(total_sum));
				 $('#occation_pay_buy_sum').text(setComma(occation_pay_buy_sum));
				 $('#sub_buy_sum').text(setComma(sub_buy_sum));
				 $('#occation_sub_pay_sum').text(setComma(occation_sub_pay_sum));
				 $('#realPayBuy_sum').text(setComma(realPayBuy_sum));
				
				$('.sum').show();
				$('.empty').hide();
			} else { 
				setTbodyNoResult("dataListbody", 11);
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
		bodyValue += "<CAPTION><spring:message code='epc.pay.dash.payment'/><br>";
		bodyValue += "[<spring:message code='epc.ord.period'/> : " + date + "]";
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

	function forwarding(val) {
		loadingMaskFixPos();
		$("#searchForm input[id='srchStartDate']").val(val);
		$("#searchForm input[id='srchEndDate']").val(val);
		$('#searchForm #mode').val('select');
		$("#searchForm").attr("action", "<c:url value='/edi/payment/NEDMPAY0030.do'/>");
		$("#searchForm").submit();
	}

	function printText(){
		// 조회시작일자
		var srchStartDate 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회 종료일자
		var srchEndDate 	= $("#searchForm input[name='srchEndDate']").val();
		// 조회기간 설정
		var date = srchStartDate + "~" + srchEndDate;
		// 점포명
		var store 		= $("#searchForm input[name='storeName']").val();
		// 상품코드
		var product 	= $("#searchForm input[name='productVal']").val();
		// 협력업체 코드(배열)
		var vendor 		= $("#searchForm input[name='vendor']").val();
		// 협력업체 코드(단독)
		var vencd 		= $("#searchForm select[name='entp_cd']").val();
		

		var tmp="";
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	
		var textData = "";
		textData += "[<spring:message code='epc.ord.period'/> : " + date + "]"
		textData += "[<spring:message code='epc.ord.vendor'/> : " + tmp + "]"
		$("#searchForm input[name='textData']").val(textData);
		$("#searchForm input[name='searchEntpCd']").val(vencd);
		//console.log(textData);
		
		$("#searchForm").attr("action", "<c:url value='/edi/payment/NEDMPAY0020Text.do'/>");
		$("#searchForm").submit();


	}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	
<tr class="r1" >
	<td rowspan="3" align="center"><a href="javascript:forwarding('<c:out value="\${payDay}"/>');"><c:out value="\${payDay}"/></a></td>
	<td rowspan="3"  align="right"><c:out value="\${payBuy}"/></td>
	<td align="right" class = "dedu_odd"><c:out value="\${mul}"/></td>
	<td align="right"><c:out value="\${usply}"/></td>
	<td align="right" class = "dedu_odd"><c:out value="\${infoAnlyFee}"/></td>
	<td align="right"><c:out value="\${sinsang}"/></td>
	<td align="right" class = "dedu_odd"><c:out value="\${sinsang2}"/></td>
	<td rowspan="3" align="right"><c:out value="\${total}"/></td>
	<td rowspan="3" align="right"><c:out value="\${occationPayBuy}"/></td>
	<td rowspan="3"  align="right"><c:out value="\${occationSubPay}"/></td>
	<td rowspan="3"  align="right"><c:out value="\${realPayBuy}"/></td>
</tr>
<tr class="r1">
	<td align="right" class = "dedu_odd"><c:out value="\${newProdIncentFee}"/></td>
	<td align="right"><c:out value="\${ehbtIncentFee}"/></td>
	<td align="right" class = "dedu_odd"><c:out value="\${pfrmIncentFee}"/></td>
	<td align="right"><c:out value="\${facilityFee}"/></td>
	<td align="right" class = "dedu_odd"><c:out value="\${onlineAdFee}"/></td>
</tr>
<tr class="r1">
	<td align="right" class = "dedu_odd"><c:out value="\${movStdFee}"/></td>
	<td align="right"><c:out value="\${bottDedu}"/></td>
	<td align="right" class = "dedu_odd"><c:out value="\${alcoBottDedu}"/></td>
	<td align="right"><c:out value="\${digitalAdFee}"/></td>
	<td align="right" class = "dedu_odd"><c:out value="\${etcDedu}"/></td>
</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" id = "mode" name="mode" />
		<input type="hidden" name="textData" />
		<input type="hidden" name="vendor" value="<c:out value="${paramMap.ven }" />">
		<input type="hidden" id="storeName" name="storeName" />
		<input type="hidden" id="staticTableBodyValue" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" >
		
		<input type="hidden" id="searchEntpCd" name="searchEntpCd"  />
		<div id="wrap_menu" style="Width:920px">
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
							<a href='/epc/common/edi/LT_Product_Payment_FAQ_v1.0.pptx' class="btn" id="excel"><span>상품 대금 관련 FAQ</span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
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
						<th><spring:message code='epc.pay.search.entpCd'/></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!-- 1.1검색 내역 상단 문구 추가. -->
			<div>
				<br><span>&nbsp&nbsp▶지급대상액 = 공급가액 + 세액</span><br> &nbsp&nbsp* 세금계산서 4일 이후 승인시 세액 일월 지급(10일 18시 이후 승인 불가)</span>
			
				<!--  
				<br><span>[공제내역 설명]<span><br><span>1. 허용장려금 : 매대진열장려금 + 신상품장려금 + 성과장려금</span><br> 2. 기타공제 : 점간이동 + 시설물사용료 + 광고판촉분담금2 + 광고판촉분담금3</span><br>
				&nbsp;&nbsp;&nbsp;* 위탁경영점 서울역점(한화역사), 인천터미널점(인천개발) 내역 포함됨 -> 좌측 '점포별 대금결제 내역'과 비교 필요<br>
				-->
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
						<col style="width:70px;">
						<col style="width:90px;">					
                        <col style="width:85px;">
                        <col style="width:85px;">
                        <col style="width:85px;">
						<col style="width:85px;">
						<col style="width:85px;">
						<col style="width:80px;">
						<col style="width:70px;">
						<col style="width:80px;">					
					</colgroup>
					<tr>
					
						<th rowspan="4">지급일자</th>
						<th rowspan="4">지급대상액</th>
						<th colspan="6">공제내역</th>
						<th rowspan="4">패밀리론</th>
						<th rowspan="4">수시지급</th>
						<th rowspan="4">실지급액</th>
					
					</tr>
					<tr>
						<th>물류비</th>
						<th>미납패널티</th>
						<th>정보분석료</th>
						<th>광고판촉분담금1</th>
						<th>광고판촉분담금2</th>
						<th rowspan="3">계</th>
					</tr>
					<tr>
						<th>신상품장려금</th>
						<th>매대진열장려금</th>
						<th>성과장려금</th>
						<th>시설물사용료</th>
						<th>온라인광고료</th>
					</tr>
					<tr>
						<th>점(코드)간 이동</th>
						<th>공병수수료공제</th>
						<th>주류공병공제</th>
						<th>디지털광고료</th>
						<th>기타공제</th>
					</tr>
					</table>
					<div style="width:100%; height:345px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
							<col style="width:70px;">
							<col style="width:90px;">					
	                        <col style="width:85px;">
	                        <col style="width:85px;">
	                        <col style="width:85px;">
							<col style="width:85px;">
							<col style="width:85px;">
							<col style="width:80px;">
							<col style="width:70px;">
							<col style="width:80px;">	
						</colgroup>
						<!-- Data List Body Start ------------------------------------------------------------------------------>
						<tbody id="dataListbody" />
						<!-- Data List Body End   ------------------------------------------------------------------------------>
					</table>
					</div>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
					<colgroup>
						<col style="width:70px;">
						<col style="width:90px;">					
                        <col style="width:85px;">
                        <col style="width:85px;">
                        <col style="width:85px;">
						<col style="width:85px;">
						<col style="width:85px;">
						<col style="width:80px;">
						<col style="width:70px;">
						<col style="width:80px;">				
					</colgroup>
						<tr style="display:none;" class="r1 sum">
							<th rowspan="3" align="center"><b><spring:message code='epc.pay.header.totalSum'/></b></th>
							<th rowspan="3"  align="right"><b id="pay_buy_sum"></b></th>
							<th align="right"><b id="mul_sum"></b></th>
							<th align="right"><b id="usply_sum"></b></th>
							<th align="right"><b id="infoAnlyFee_sum"></b></th>
							<th align="right"><b id="sinsang_sum"></b></th>
							<th align="right"><b id="sinsang2_sum"></b></th>
							<th rowspan="3" align="right"><b id="total_sum"></b></th>
							<th rowspan="3" align="right"><b id="occation_pay_buy_sum"></b></th>
							<th rowspan="3" align="right"><b id="occation_sub_pay_sum"></b></th>
							<th rowspan="3"  align="right"><b id="realPayBuy_sum"></b></th>							
						</tr>
						<tr style="display:none;" class="r1 sum">
							<th align="right"><b id="newProdIncentFee_sum"></b></th>
							<th align="right"><b id="ehbtIncentFee_sum"></b></th>
							<th align="right"><b id="pfrmIncentFee_sum"></b></th>
							<th align="right"><b id="facilityFee_sum"></b></th>
							<th align="right"><b id="onlineAdFee_sum"></b></th>
						</tr>
						<tr style="display:none;" class="r1 sum">
							<th align="right"><b id="movStdFee_sum"></b></th>
							<th align="right"><b id="bottDedu_sum"></b></th>
							<th align="right"><b id="alcoBottDedu_sum"></b></th>
							<th align="right"><b id="digitalAdFee_sum"></b></th>
							<th align="right"><b id="etcDedu_sum"></b></th>
						</tr>
						<tr style="display:none;" class="empty">
							<td colspan=11>&nbsp;</td>
							<th rowspan=3 width=17>&nbsp;</th>
						</tr>
						<tr style="display:none;" class="empty"><td colspan=9>&nbsp;</td></tr>
						<tr style="display:none;" class="empty"><td colspan=9>&nbsp;</td></tr>
					</table>
				</div>
			</div>
		</div>
		</form>
	</div>


	<!-- footer -->
	<div id="footer" style="width:920px">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li><spring:message code='epc.pay.home'/></li>
					<li><spring:message code='epc.pay.salInfo'/></li>
					<li><spring:message code='epc.pay.dateInfo'/></li>
					<li class="last"><spring:message code='epc.pay.pay0020'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
