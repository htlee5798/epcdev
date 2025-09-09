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
		
		form.startDate.value = form.startDate_year.value+"-"+form.startDate_month.value ;
		form.endDate.value   = form.endDate_year.value+"-"+form.endDate_month.value ;
		
		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/payment/PEDMPAY0022Select.do'/>";
			form.submit();	
			
		}
	
			
			
	}

	function storePopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0001.do'/>");
	}

	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(form){

		var startDate = form.startDate.value+"-01";
		var endDate = form.endDate.value+"-01";
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
	        form.startDate_year.focus();
	        return false;
	    }
		

		
	    intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
	    endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);


	    /*
		if(rangeDate>90){
			alert("<spring:message code='msg.common.fail.rangecalendar_90'/>");
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

		var form=document.forms[0];	
		form.startDate.value = form.startDate_year.value+"-"+form.startDate_month.value ;
		form.endDate.value   = form.endDate_year.value+"-"+form.endDate_month.value ;
		var date=form.startDate.value+"~"+form.endDate.value;
		var store=form.storeName.value;

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";

		if(store==""){
			store="전체";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	

		form.staticTableBodyValue.value = "<CAPTION>결산정보 점포별 거래실적 현황표<br>"+
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
		form.action  = "<c:url value='/edi/payment/PEDMPAY0023Select.do'/>";
		form.submit();	
	}

	function printText(){
		var form = document.forms[0];
		form.startDate.value = form.startDate_year.value+"-"+form.startDate_month.value ;
		form.endDate.value   = form.endDate_year.value+"-"+form.endDate_month.value ;
		var date=form.startDate.value+"~"+form.endDate.value;
		var store=form.storeName.value;
		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;
		var tmp="";

		if(store==""){
			store="전체";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	

		form.text_data.value="[조회기간 : "+date+"] [점포 : "+store+"] [업체 : "+tmp+"]";

		form.action  = "<c:url value='/edi/payment/PEDMPAY0022Text.do'/>";
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
							<html:codeTag objId="startDate_year" objName="startDate_year" selectParam="${paramMap.startDate_year}" dataType="YEAR" subCode="5" comType="SELECT" formName="form"  />
							<html:codeTag objId="startDate_month" objName="startDate_month" selectParam="${paramMap.startDate_month}" dataType="MONTH" comType="SELECT" formName="form"  />
							~
							<html:codeTag objId="endDate_year" objName="endDate_year" selectParam="${paramMap.endDate_year}" dataType="YEAR" subCode="5" comType="SELECT" formName="form"  />
							<html:codeTag objId="endDate_month" objName="endDate_month" selectParam="${paramMap.endDate_month}" dataType="MONTH" comType="SELECT" formName="form"  />
						
							<input type="hidden" class="day" id="startDate" name="startDate">
							<input type="hidden" class="day" id="endDate"   name="endDate" >
						 
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
						<col style="width:60px;" />
						<col style="width:90px;" />
						<col style="width:40px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th>월</th>
						<th colspan="2">점포</th>
						<th>전월이월</th>
						<th>당월매입</th>
						<th>당월지급</th>
						<th>잔액</th>
						<th>잔액합계</th>
						<th>비고</th>
						<th>&nbsp;</th>
					</tr>
					</table>
					<div style="width:100%; height:371px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
						<col style="width:60px;" />
						<col style="width:90px;" />
						<col style="width:40px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col  />
					</colgroup>
					<c:if test="${not empty paymentList }">
						<c:set var="tot_top1"  value="0" />
						<c:set var="tot_top2"  value="0" />
						<c:set var="tot_top3"  value="0" />
						<c:set var="tot_top4"  value="0" />
						<c:set var="tot_bottom1"  value="0" />
						<c:set var="tot_bottom2"  value="0" />
						<c:set var="tot_bottom3"  value="0" />
						<c:set var="tot_bottom4"  value="0" />
						<c:set var="tot_sum"  value="0" />
					<c:forEach items="${paymentList}" var="list" varStatus="index" >
							<tr class="r1">
								<td rowspan="2" align="center"><a href="javascript:forwarding('${list.BUY_YM }');">${list.BUY_YM }</a></td>
								<td rowspan="2" align="center">${list.STR_NM }</td>
								<td align="center">공급<br>가액</td>
								<td align="right"><fmt:formatNumber value="${list.TRANS_BUY_AMT }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.BUY_AMT }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.PAY_BUY_AMT }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.REM_BUY_AMT }" type="number" currencySymbol="" /></td>
								<td rowspan="2" align="right"><fmt:formatNumber value="${list.REM_SUM}" type="number" currencySymbol="" /></td>
								<td rowspan="2" align="center">${list.BORU }</td>
							</tr>
							<tr class="r1">
								<td align="center">세액</td>
								<td align="right"><fmt:formatNumber value="${list.TRANS_VAT }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.VAT }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.PAY_VAT }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.REM_VAT }" type="number" currencySymbol="" /></td>
							</tr>
							
							<c:set var="tot_top1"  value="${tot_top1 + list.TRANS_BUY_AMT}" />
							<c:set var="tot_top2"  value="${tot_top2 + list.BUY_AMT}" />
							<c:set var="tot_top3"  value="${tot_top3 + list.PAY_BUY_AMT}" />
							<c:set var="tot_top4"  value="${tot_top4 + list.REM_BUY_AMT}" />
							<c:set var="tot_bottom1"  value="${tot_bottom1 + list.TRANS_VAT}" />
							<c:set var="tot_bottom2"  value="${tot_bottom2 + list.VAT}" />
							<c:set var="tot_bottom3"  value="${tot_bottom3 + list.PAY_VAT}" />
							<c:set var="tot_bottom4"  value="${tot_bottom4 + list.REM_VAT}" />
							<c:set var="tot_sum"  value="${tot_sum + list.REM_SUM}" />
						</c:forEach>
						
					</c:if>
					<c:if test="${empty paymentList }">
						<tr><td colspan="8" align=center>Data가 없습니다.</td></tr>
					</c:if>
					</table>
					</div>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
					<colgroup>
						<col style="width:60px;" />
						<col style="width:90px;" />
						<col style="width:40px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<c:if test="${not empty paymentList }">
						<tr class="r1">
							<th rowspan="2" colspan="2" align="center"><b>합계</b></td>
							<th align="center"><b>공급<br>가액</b></td>
							<th align="right"><b><fmt:formatNumber value="${tot_top1 }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${tot_top2 }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${tot_top3 }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${tot_top4 }" type="number" currencySymbol="" /></b></th>
							<th rowspan="2" align="right"><b><fmt:formatNumber value="${tot_sum }" type="number" currencySymbol="" /></b></th>
							<th rowspan="2"></td>
							<th rowspan="2">&nbsp;</td>
						</tr>
						<tr class="r1">
							<th align="center"><b>세액</b></td>
							<th align="right"><b><fmt:formatNumber value="${tot_bottom1 }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${tot_bottom2 }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${tot_bottom3 }" type="number" currencySymbol="" /></b></th>
							<th align="right"><b><fmt:formatNumber value="${tot_bottom4 }" type="number" currencySymbol="" /></b></th>
						</tr>
					</c:if>
					<c:if test="${empty paymentList }">
						<tr><td colspan=9>&nbsp;<br>&nbsp;</td><th width=17 rowspan=2>&nbsp;</th></tr>
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
					<li>거래실적정보</li>
					<li class="last">점포별거래실적</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
