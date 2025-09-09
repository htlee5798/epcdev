<%@include file="./common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>

/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
		
	if("${mode}" == "search"){
		doSearch();
	}
}); // end of ready

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
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];

		//alert(form.storeVal.value);
		
// 		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/talk/memberTab/search.do'/>";
			form.submit();	
// 		}	
	//	hideLoadingMask();
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
			//form.startDate.focus();
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

		if(rangeDate>15){
			alert("<spring:message code='msg.common.fail.rangecalendar_15'/>");
			form.startDate.focus();
			return false;
		}	

		return true;
	}

	function popupSearch(tbName1, tbName2, tbName3){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');
		var tbody3 = $('#' + tbName3 + ' tbody');			

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

		form.staticTableBodyValue.value = "<CAPTION>발주정보 전표별 현황표<br>"+
		"[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html()+ tbody3.parent().html();
			
		form.name.value = "statics";
		form.action="<c:url value='/edi/comm/PEDPCOM0003.do'/>";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="";

	}	

	function forwarding(val) {
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];

		form.startDate.value=val;
		form.endDate.value=val;

			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/order/PEDMORD0002Select.do'/>";
			form.submit();	
	}

	function forwarding2(val,val2) {
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];

		form.productVal.value=val;
		form.startDate.value=val2;
		form.endDate.value=val2;

		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/order/PEDMORD0004Select.do'/>";
			form.submit();	
		}	
	}

	function removeChar(orgChar, rmChar){
	    return replace(orgChar,rmChar,"");
	}

	function storeClear(){
		var form = document.forms[0];
		form.storeVal.value="";
	}
	function productClear(){
		var form = document.forms[0];
		form.productVal.value="";
	}


</script>

</head>

<body>

<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" name="text_data" />
		<input type="hidden" name="vendor" value="${paramMap.ven }">
		<input type="hidden" id="storeName" name="storeName" />
		<input type="hidden" name="staticTableBodyValue">
		<input type="hidden" name="name">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<input type="hidden" name="textList" value="${memerList}"/>
		<input type="hidden" id="memberNo" name="memberNo" value="${memberNo}"/>
		<input type="hidden" id="mode" name="mode" value="${mode}"/>
		
		<div id="wrap_menu">
		
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">검색내역</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<colgroup>
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
						</colgroup>
					<tr>
						<th>회원명</th>
						<th>임직원구분</th>
						<th>회원번호</th>
						<th>마일리지</th>
						<th>예치금</th>
					</tr>
					</table>
					
					<div class="datagrade_scroll_sum">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
						</colgroup>
					<c:if test="${not empty memerList }">
						<c:forEach items="${memerList}" var="list" varStatus="index" >
							<tr class="r1">
								<td align="center">${list.MEMBER_NM}</td>
								<td align="center">${list.EMP_YN}</td>
								<td align="center">${list.MEMBER_NO}</td>
								<td align="right"><fmt:formatNumber value="${list.PNT_00001}" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.PNT_00003}" type="number" currencySymbol="" /></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty memerList }">
						<tr><td colspan="8" align=center>Data가 없습니다.</td></tr>
					</c:if>
					</table>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>


</div>
</body>
</html>
