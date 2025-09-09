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
		$("select[name='delivery']").val("<c:out value='${param.delivery}'/>");
	});


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

		
		var form = document.forms[0];
		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/buy/PEDMBUY0007Select.do'/>";
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

		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}	

		return true;
		
	}

	function storeClear(){
		var form = document.forms[0];
		form.storeVal.value="";
	}
	function productClear(){
		var form = document.forms[0];
		form.productVal.value="";
	}

	function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		

		var form=document.forms[0];	

		var date=form.startDate.value+"~"+form.endDate.value;
		var store=form.storeName.value;
		var product=form.productVal.value;

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";

		if(store==""){
			store="전체";
		}
		if(product==""){
			product="전체";
		}
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}

		var buy_cen_fg="";
		for(var i=0;i<form.buy_cen_fg.options.length;i++){
			if(form.buy_cen_fg.options[i].selected == true){
				buy_cen_fg=form.buy_cen_fg.options[i].text;
           }
		}

		var delivery="";
		for(var i=0;i<form.delivery.options.length;i++){
			if(form.delivery.options[i].selected == true){
				delivery=form.delivery.options[i].text;
           }
		}
		

		form.staticTableBodyValue.value = "<CAPTION>매입정보 점포별가매입 현황표<br>"+
		"[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [납품구분 : "+buy_cen_fg+"] [납품유형 : "+delivery+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html();
	
		form.name.value = "statics";
		form.action="<c:url value='/edi/comm/PEDPCOM0003.do'/>";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="";
	}	
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		
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
							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a> 
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			
					<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
					<input type="hidden" id="productVal" name="productVal"  value="${param.productVal }"/>
					
					<colgroup>
						<col style="width:100px;" />
						<col style="width:230px;" />
						<col style="width:100px;" />
						<col style="width:160px;" />
						<col style="width:100px;" />
						<col style="width:90px;" />
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
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();"/>전점조회
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/>점포선택
						</td>
						
						<th>납품구분</th>
						<td >
							<select name="buy_cen_fg" >
								<option value="C" <c:if test="${paramMap.buy_cen_fg eq 'C' }"> selected</c:if>>센터</option>
								<option value="S" <c:if test="${paramMap.buy_cen_fg eq 'S' }"> selected</c:if>>점포</option>
								<option value="A" <c:if test="${paramMap.buy_cen_fg eq 'A' }"> selected</c:if>>전체</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						
						<%-- <th>상품코드</th>
						<td>
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/>전상품조회   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/>상품선택
						</td> --%>
						
						<th>납품유형</th>
						<td colspan="3">
							<html:codeTag objId="delivery" objName="delivery" comType="SELECT" formName="form" selectParam="" parentCode="ASN01"  defName="전체"></html:codeTag>
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
						<li class="tit">검색내역</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"  id="testTable1">
					<colgroup>
							<col style="width:40px" />
							<col style="width:40px" />
							<col style="width:90px" />
							<col style="width:70px" />
							<col style="width:90px" />
							<col  />
							<col style="width:40px" />
							<col style="width:40px" />
							<col style="width:40px" />
							<col style="width:30px" />
							<col style="width:30px" />
							<col style="width:60px" />
							<col style="width:30px" />
							<col style="width:80px"/>
							<col style="width:17px" />
						</colgroup>
						<tr>
							<th>센터명</th>
							<th>점포명</th>
							<th>물류바코드</th>
							<th>상품코드</th>
							<th>판매코드</th>
							<th>상품명</th>
							<th>규격</th>
							<th>입수</th>
							<th>단위</th>
							<th>납품유형</th>
							<th>발주수량</th>
							<th>발주금액</th>
							<th>매입수량</th>
							<th>매입금액</th>
							<th>&nbsp;</th>
						</tr>
					</table>
					
					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					
					<div style="width:100%; height:394px;overflow-x:hidden; overflow-y:scroll;  table-layout:fixed;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
							<col style="width:40px" />
							<col style="width:40px" />
							<col style="width:90px" />
							<col style="width:70px" />
							<col style="width:90px" />
							<col  />
							<col style="width:40px" />
							<col style="width:40px" />
							<col style="width:40px" />
							<col style="width:30px" />
							<col style="width:30px" />
							<col style="width:60px" />
							<col style="width:30px" />
							<col style="width:80px"/>
					</colgroup>
					<c:if test="${not empty buyList }">
							<c:set var="tot_sum1"  value="0" />
							<c:set var="tot_sum2"  value="0" />
							<c:set var="tot_sum3"  value="0" />
							<c:set var="tot_sum4"  value="0" />
						<c:forEach items="${buyList}" var="list" varStatus="index" >
							
							<tr class="r1">
								<td align="center">${list.CTR_NM }</td>
								<td align="center">${list.STR_NM }</td>
								<td align="center">${list.LOGI_BCD }</td>
								<td align="left" style="mso-number-format:'\@'">&nbsp;${list.PROD_CD }</td>
								<td align="center">${list.SRCMK_CD }</td>
								<td align="center">${list.PROD_NM }</td>
								<td align="center">${list.PROD_STD }</td>
								<td align="center">${list.ORD_IPSU }</td>
								<td align="center">${list.DETAIL_NM }</td>
								<td align="center">${list.BUY_FG }</td>
								<td align=right><fmt:formatNumber value="${list.ORD_QTY }" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.ORD_AMT }" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.BUY_QTY }" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.BUY_AMT }" type="number" currencySymbol="" /></td>
							</tr>
							<c:set var="tot_sum1" value="${tot_sum1 + list.ORD_QTY }" />
							<c:set var="tot_sum2" value="${tot_sum2 + list.ORD_AMT }" />
							<c:set var="tot_sum3" value="${tot_sum3 + list.BUY_QTY }" />
							<c:set var="tot_sum4" value="${tot_sum4 + list.BUY_AMT }" />
						</c:forEach>
						
					</c:if>
					<c:if test="${empty buyList }">
						<tr><td colspan="14" align=center>Data가 없습니다.</td></tr>
					</c:if>
					</table>
					</div>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
					<c:if test="${not empty buyList }">
						<colgroup>
								<col style="width:40px" />
								<col style="width:40px" />
								<col style="width:90px" />
								<col style="width:70px" />
								<col style="width:90px" />
								<col  />
								<col style="width:40px" />
								<col style="width:40px" />
								<col style="width:40px" />
								<col style="width:30px" />
								<col style="width:30px" />
								<col style="width:60px" />
								<col style="width:30px" />
								<col style="width:80px"/>
								<col style="width:17px" />
						</colgroup>
						<tr class="r1">
							<th colspan="6" rowspan="2">합계</th>
							<th colspan="2">매입금액</th>
							<th colspan="3" align=right><fmt:formatNumber value="${tot_sum4 }" type="number" currencySymbol="" /></th>
							<th>매입수량</th>
							<th colspan="2" align=right><fmt:formatNumber value="${tot_sum3 }" type="number" currencySymbol="" /></th>
							<th rowspan=2>&nbsp;</th>
						</tr>
						<tr class="r1">
							<th colspan="2">발주금액</th>
							<th colspan="3" align=right><fmt:formatNumber value="${tot_sum2 }" type="number" currencySymbol="" /></th>
							<th>발주수량</th>
							<th colspan="2" align=right><fmt:formatNumber value="${tot_sum1 }" type="number" currencySymbol="" /></th>
						</tr>
					</c:if>
					<c:if test="${empty buyList }"><tr><td colspan=14>&nbsp;</td><th width=17>&nbsp;</th></tr></c:if>
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
					<li>매입정보</li>
					<li>기간정보</li>
					<li class="last">점포별 가매입</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>

</html>
