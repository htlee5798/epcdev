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
			form.action  = "<c:url value='/edi/order/PEDMORD0003Select.do'/>";
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

		if(rangeDate>15){
			alert("<spring:message code='msg.common.fail.rangecalendar_15'/>");
			form.startDate.focus();
			return false;
		}	

		return true;
	}

	
	function popupSearch(tbName1){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');

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

		var ordering="";
		for(var i=0;i<form.ordering.options.length;i++){
			if(form.ordering.options[i].selected == true){
				ordering=form.ordering.options[i].text;
           }
		}

		form.staticTableBodyValue.value = "<CAPTION>발주정보 전표상세별 현황표<br>"+
		"[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [발주구분 : "+ordering+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html();
	
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
	function productClear(){
		var form = document.forms[0];
		form.productVal.value="";
	}

	function printText(){
		var form = document.forms[0];

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

		var ordering="";
		for(var i=0;i<form.ordering.options.length;i++){
			if(form.ordering.options[i].selected == true){
				ordering=form.ordering.options[i].text;
           }
		}

		form.text_data.value="[조회기간 : "+date+"] [점포 : "+store+"] [상품 : "+product+"] [발주구분 : "+ordering+"] [업체 : "+tmp+"]";
		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/order/PEDMORD0003Text.do'/>";
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
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit">검색조건</li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 								<a href="#" class="btn" onclick="popupSearch('testTable1');"><span><spring:message code="button.common.excel"/></span></a> 
 								<a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text"/></span></a> 
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
						<input type="hidden" id="productVal" name="productVal" value="${param.productVal }" />
						
						<colgroup>
							<col style="width:100px;" />
							<col style="width:230px;" />
							<col style="width:100px;" />
							<col style="width:160px;" />
							<col style="width:100px;" />
							<col style="width:70px;" />
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
							<th></th><td></td>
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
							
							<th>발주구분</th>
							<td colspan="3">
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
						<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<c:if test="${not empty orderList }">
							<colgroup>
								<col style="width:12%" />
								<col style="width:14%" />
								<col style="width:12%" />
								<col style="width:12%" />
								<col style="width:12%" />
								<col style="width:12%"  />
								<col style="width:14%" />
								<col style="width:12%"  />
							</colgroup>
							
							<c:set var="tmp"  value="empty" />
							
							
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<c:if test="${list.ORD_SLIP_NO != tmp }">
									<tr class="r1">
										<th>점포명</th>
										<th style="display:none;">점포코드</th>
										<th>발주일</th>
										<th>전표번호</th>
										<th>납품처</th>
										<th>발주수량</th>
										<th>발주금액</th>
										<th>센터입하일</th>
										<th>점입하일</th>
									</tr>
									<tr class="r1">
										<td align=center>${list.STR_NM }</td>
										<td align=center style="display:none;">${list.STR_CD }</td>
										<td align=center>${list.ORD_DY }</td>
										<td align=center style="mso-number-format:'\@'">${list.ORD_SLIP_NO }</td>
										<td align=center>${list.SUPPLY_NM }</td>
										<td align="right"><fmt:formatNumber value="${list.TOT_QTY }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.TOT_PRC }" type="number" currencySymbol="" /></td>
										<td align=center>${list.CTR_ARR_DY }</td>
									<td align=center>${list.SPLY_DY }</td>
								</tr>
								</c:if>
								<c:if test="${list.ORD_SLIP_NO != tmp }">
								<tr class="r1">
									<th>상품코드</th>
									<th>판매코드</th>
									<th colspan="2">상품명</th>
									<th>입수</th>
									<th>단위</th>
									<th>발주수량</th>
									<th>발주금액</th>
								</tr>
								</c:if>
								<tr class="r1">
									<td align=center style="mso-number-format:'\@'">${list.PROD_CD }</td>
									<td align=center style="mso-number-format:'\@'">${list.SRCMK_CD }</td>
									<td colspan="2" align="left">&nbsp; ${list.PROD_NM }</td>
									<td align="right"><fmt:formatNumber value="${list.ORD_IPSU }" type="number" currencySymbol="" /></td>
									<td align=center>${list.PUR_UNIT_CD_NM }</td>
									<td align="right"><fmt:formatNumber value="${list.ORD_QTY }" type="number" currencySymbol="" /></td>
									<td align="right"><fmt:formatNumber value="${list.BUY_PRC }" type="number" currencySymbol="" /></td>
									<td align=center style="display:none;">&nbsp;</td>
								</tr>
								<c:set var="tmp" value="${list.ORD_SLIP_NO }" />
							</c:forEach>
						</c:if>
						
						<c:if test="${empty orderList }">
							<tr><td colspan="8" align=center>Data가 없습니다.</td></tr>
						</c:if>
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
						<li>홈</li>
						<li>발주정보</li>
						<li>기간정보</li>
						<li class="last">전표상세별</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>



</body>
</html>
