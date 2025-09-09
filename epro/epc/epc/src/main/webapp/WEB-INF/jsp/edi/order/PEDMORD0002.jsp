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
		$("select[name='ordering']").val("<c:out value='${param.ordering}'/>");	// 협력업체 선택값 세팅		
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
			form.action  = "<c:url value='/edi/order/PEDMORD0002Select.do'/>";
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

		if(rangeDate>15){
			alert("<spring:message code='msg.common.fail.rangecalendar_15'/>");
			form.startDate.focus();
			return false;
		}	

		return true;
	}

	function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		

		var form=document.forms[0];

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

		var ordering="";
		for(var i=0;i<form.ordering.options.length;i++){
			if(form.ordering.options[i].selected == true){
				ordering=form.ordering.options[i].text;
           }
		}

		form.staticTableBodyValue.value = "<CAPTION>발주정보 전표별 현황표<br>"+
		"[조회기간 : "+date+"] [점포 : "+store+"] [발주구분 : "+ordering+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html();
	
		form.name.value = "statics";
		form.action="<c:url value='/edi/comm/PEDPCOM0003.do'/>";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="";
	}

	function forwarding(val,val2) {
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];

		form.storeVal.value=val;
		form.startDate.value=val2;
		form.endDate.value=val2;

		if(dateValid(form)){
			form.action  = "<c:url value='/edi/order/PEDMORD0003Select.do'/>";
			form.submit();	
		}	
	}
	
	function storeClear(){
		var form = document.forms[0];
		form.storeVal.value="";
	}

	function printText(){
		var form = document.forms[0];

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

		var ordering="";
		for(var i=0;i<form.ordering.options.length;i++){
			if(form.ordering.options[i].selected == true){
				ordering=form.ordering.options[i].text;
           }
		}

		form.text_data.value="[조회기간 : "+date+"] [점포 : "+store+"] [발주구분 : "+ordering+"] [업체 : "+tmp+"]";

		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/order/PEDMORD0002Text.do'/>";
			form.submit();	
		}	
	

	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
		<div>
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
								<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a> 
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
							<th>발주구분</th>
							<td class="bal">
								<html:codeTag objId="ordering" objName="ordering" comType="SELECT" selectParam="" formName="form" parentCode="ORD01" subCode="1" defName="전체" ></html:codeTag>
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
			
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<colgroup>
							<col style="width:80px;" />
							<col  />
							<col style="width:110px;" />
							<col style="width:55px;" />
							<col style="width:90px;" />
							<col style="width:130px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;"/>
							<col style="width:17px;" />
						</colgroup>
						<tr>
							<th>발주일자</th>
							<th>점포</th>
							<th style="display:none;">점포코드</th>
							<th>전표번호</th>
							<th>발주구분</th>
							<th>발주수량</th>
							<th>발주금액</th>
							<th>센터입하일</th>
							<th>점입하일</th>
							<th>조회여부</th>
							<th>&nbsp;</th>
						</tr>
						</table>
						
						<div class="datagrade_scroll_sum">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
							<col style="width:80px;" />
							<col  />
							<col style="width:110px;" />
							<col style="width:55px;" />
							<col style="width:90px;" />
							<col style="width:130px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;"/>
						</colgroup>
						<c:if test="${not empty orderList }">
							<c:set var="total_qty"  value="0" />
							<c:set var="total_prc"  value="0" />
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<tr class="r1">
									<td align=center>${list.ORD_DY }</td>
									<td align=center><a href="javascript:forwarding('${list.STR_CD }','${list.ORD_DY }');">${list.STR_NM }</a></td>
									<td align=center style="display:none;">${list.STR_CD }</td>
									<td align=center style="mso-number-format:'\@'">${list.ORD_SLIP_NO }</td>
									<td align=center>${list.ORD_FG_NM }</td>
									<td align=right><fmt:formatNumber value="${list.TOT_QTY }" type="number" currencySymbol="" /></td>
									<td align=right><fmt:formatNumber value="${list.TOT_PRC }" type="number" currencySymbol="" /></td>
									<td align=center>${list.CTR_ARR_DY }</td>
									<td align=center>${list.SPLY_DY }</td>
									<td align=center>${list.USER_HIT }</td>
								</tr>
								
								<c:set var="total_qty" value="${total_qty + list.TOT_QTY }" />
								<c:set var="total_prc" value="${total_prc + list.TOT_PRC }" />
							</c:forEach>
							
						</c:if>
						<c:if test="${empty orderList }">
							<tr><td colspan="9" align=center>Data가 없습니다.</td></tr>
						</c:if>
						</table>
						</div>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTabl3">
							<colgroup>
								<col style="width:80px;" />
								<col  />
								<col style="width:110px;" />
								<col style="width:55px;" />
								<col style="width:90px;" />
								<col style="width:130px;" />
								<col style="width:80px;" />
								<col style="width:80px;" />
								<col style="width:80px;"/>
								<col style="width:17px;" />
							</colgroup>
							<c:if test="${not empty orderList }">
								<tr class="r1">
									<th colspan="4" class="fst" align=center><b>합계</b></th>
									<th align=right><b><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></b></th>
									<th align=right><b><fmt:formatNumber value="${total_prc }" type="number" currencySymbol="" /></b></th>
									<th colspan=3>&nbsp;</th>
									
									<th >&nbsp;</th>
								</tr>
							</c:if>
							<c:if test="${empty orderList }"><tr><td colspan=9> &nbsp;</td><th width=17>&nbsp;</th></tr></c:if>
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
						<li>발주정보</li>
						<li>기간정보</li>
						<li class="last">전표별</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

</body>
</html>
