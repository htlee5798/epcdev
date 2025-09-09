<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>

	/* 폼로드 */
	$(document).ready(function($) {		
		$("select[name='entp_cd']").val("<c:out value='${param.entp_cd}'/>");	// 협력업체 선택값 세팅			
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
		
		var form = document.forms[0];
		if(dateValid(form)){
			loadingMaskFixPos();
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/payment/PEDMPAY0003Select.do'/>";
			form.submit();	
		}	
			
	}

	function storePopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0001.do'/>");
	}

	function productPopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0002.do'/>");
	}


	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(form){

		var startDate = form.startDate.value;
		var endDate = form.endDate.value;
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

	function popupSearch(tbName1,tbName2, tbName3){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		
		var tbody3 = $('#' + tbName3 + ' tbody');	

		var form=document.forms[0];	

		var date=form.startDate.value+"~"+form.endDate.value;
		var store=form.storeName.value;

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";

		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	

		form.staticTableBodyValue.value = "<CAPTION>결산정보 점포별 대금결제 현황표<br>"+
		"[조회기간 : "+date+"] [점포 : "+store+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html()+ tbody3.parent().html();
	
		form.name.value = "statics";
		form.action="<c:url value='/edi/comm/PEDPCOM0003.do'/>";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="";
	}	

	function storeClear(){
		var form = document.forms[0];
		form.storeVal.value="";
	}

	function forwarding(val) {
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];

		loadingMaskFixPos();
		form.startDate.value=val;
		form.endDate.value=val;
		form.action  = "<c:url value='/edi/payment/PEDMPAY0002Select.do'/>";
		form.submit();	
	}

	function printText(){
		var form = document.forms[0];

		var date=form.startDate.value+"~"+form.endDate.value;
		var store=form.storeName.value;

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";

		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}

		form.text_data.value="[조회기간 : "+date+"] [점포 : "+store+"] [업체 : "+tmp+"]";

		form.action  = "<c:url value='/edi/payment/PEDMPAY0003Text.do'/>";
		form.submit();	

	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" name="text_data" />
		<input type="hidden" name="vendor" value="${paramMap.ven }">
		<input type="hidden" id="storeName" name="storeName" />
		<input type="hidden" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" >
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">검색조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2','testTable3');"><span><spring:message code="button.common.excel"/></span></a> 
 							<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
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
						<th><span class="star">*</span> 조회기간 </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> 점포선택</th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/>전점조회
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/>점포선택
						</td>
					</tr>
					<tr>
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
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
						<li class="tit">검색내역</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:70px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:70px;" />
						<col  />
			
					</colgroup>
					<tr>
						<th rowspan="3">지급일자</th>
						<th rowspan="3">점포</th>
						<th rowspan="3">지급대상액</th>
						<th colspan="4">공제내역</th>
						<th rowspan="3">네트워크론지급</th>
						<th rowspan="3">수시지급</th>
						<th rowspan="3">실지급액</th>
					
					</tr>
					<tr>
						<th>물류비</th>
						<th>허용장려금</th>
						<th>미납패널티</th>
						<th rowspan="2">계</th>
					</tr>
					<tr>
						<th>신상품촉진비</th>
						<th>장려금조정</th>
						<th>기타공제</th>
					</tr>
					</table>
					<div style="width:100%; height:319px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
							<col style="width:90px;" />
						<col style="width:70px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:70px;" />
						<col  />
					
					</colgroup>
					<c:if test="${not empty paymentList }">
						<c:set var="pay_buy_sum"  value="0" />
						<c:set var="mul_sum"  value="0" />
						<c:set var="panjang_sum"  value="0" />
						<c:set var="usply_sum"  value="0" />
						<c:set var="sinsang_sum"  value="0" />
						<c:set var="sugi_sum"  value="0" />
						<c:set var="gita_sum"  value="0" />
						<c:set var="newwork_sum"  value="0" />
						<c:set var="total_sum"  value="0" />
						<c:set var="sub_buy_sum"  value="0" />
						<c:set var="sub_pay_sum"  value="0" />
						<c:forEach items="${paymentList}" var="list" varStatus="index" >
							<c:set var="pay_buy_sum"  value="${pay_buy_sum + list.PAY_BUY }" />
							<c:set var="mul_sum"  value="${mul_sum + list.MUL }" />
							<c:set var="panjang_sum"  value="${panjang_sum + list.PANJANG}" />
							<c:set var="usply_sum"  value="${usply_sum + list.USPLY}" />
							<c:set var="sinsang_sum"  value="${sinsang_sum + list.SINSANG}" />
							<c:set var="sugi_sum"  value="${sugi_sum + list.SUGI  }" />
							<c:set var="gita_sum"  value="${gita_sum +  list.GITA }" />
							
							
							<c:set var="newwork_sum"  value="${newwork_sum +  list.OCCASION_PAY_BUY }" />				
							<c:set var="total_sum"  value="${total_sum +  list.TOTAL}" />
							<c:set var="sub_buy_sum"  value="${sub_buy_sum + list.SUB_BUY}" />
							<c:set var="sub_pay_sum"  value="${sub_pay_sum + list.OCCASION_SUB_PAY}" />
							
							<tr class="r1">
								<td rowspan="2" align="center"><a href="javascript:forwarding('${list.PAY_DAY }');">${list.PAY_DAY }</a></td>
								<td rowspan="2" align="center">${list.STR_NM }</td>
								<td rowspan="2"  align="right"><fmt:formatNumber value="${list.PAY_BUY }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.MUL }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.PANJANG }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.USPLY }" type="number" currencySymbol="" /></td>
								<td rowspan="2" align="right"><fmt:formatNumber value="${list.TOTAL }" type="number" currencySymbol="" /></td>
								<td rowspan="2" align="right"><fmt:formatNumber value="${list.OCCASION_PAY_BUY }" type="number" currencySymbol="" /></td>
								<td rowspan="2"  align="right"><fmt:formatNumber value="${list.OCCASION_SUB_PAY }" type="number" currencySymbol="" /></td>
								<td rowspan="2"  align="right"><fmt:formatNumber value="${list.SUB_BUY }" type="number" currencySymbol="" /></td>
								
							</tr>
							<tr class="r1">
								<td align="right"><fmt:formatNumber value="${list.SINSANG }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.SUGI }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.GITA }" type="number" currencySymbol="" /></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty paymentList }">
						<tr><td colspan="9" align=center>Data가 없습니다.</td></tr>
					</c:if>
					</table>
					</div>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:70px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:70px;" />
						<col  />
					
					</colgroup>
					<c:if test="${not empty paymentList }">
						<tr class="r1">
							<th rowspan="2" colspan="2" align="center"><b>합계</b></th>
							<th rowspan="2"  align="right"><b><fmt:formatNumber value="${pay_buy_sum }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${mul_sum }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${panjang_sum }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${usply_sum }" type="number" currencySymbol="" /></b></th>
							<th rowspan="2" align="right"><b><fmt:formatNumber value="${total_sum }" type="number" currencySymbol="" /></b></th>
							<th rowspan="2" align="right"><b><fmt:formatNumber value="${newwork_sum }" type="number" currencySymbol="" /></b></th>
							<th rowspan="2" align="right"><b><fmt:formatNumber value="${sub_pay_sum }" type="number" currencySymbol="" /></b></th>
							<th rowspan="2"  align="right"><b><fmt:formatNumber value="${sub_buy_sum }" type="number" currencySymbol="" /></b></th>
							
						</tr>
						<tr class="r1">
							<th align="right"><b><fmt:formatNumber value="${sinsang_sum }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${sugi_sum }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${gita_sum }" type="number" currencySymbol="" /></b></th>
						</tr>
					</c:if>
					<c:if test="${empty paymentList }">
						<tr><td colspan=9>&nbsp;</td><th rowspan=3 width=17>&nbsp;</th></tr>
						<tr><td colspan=9>&nbsp;</td></tr>
						<tr><td colspan=9>&nbsp;</td></tr>
					</c:if>
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
					<li>홈</li>
					<li>결산정보</li>
					<li>기간정보</li>
					<li class="last">점포별 대금결제</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
