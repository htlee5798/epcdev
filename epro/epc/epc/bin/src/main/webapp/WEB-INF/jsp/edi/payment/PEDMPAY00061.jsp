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
		
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/payment/PEDMPAY00061Select.do'/>";
		form.submit();	
		
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

	    /*
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}	
		*/
		return true;
	}

	function popupSearch(tbName1, tbName2){		
		
		var form=document.forms[0];	

		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		

		var form=document.forms[0];	
		form.startDate.value = form.startDate_year.value+"-"+form.startDate_month.value ;
		var date=form.startDate.value;
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

		form.staticTableBodyValue.value = "<CAPTION>결산정보  판매장려금 현황표<br>"+
		"[조회기간 : "+date+"][점포 : "+store+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html();
	
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
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
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
						<li class="tit">검색조건 &nbsp;<font color="blue">(2014년 4월 이후 조회페이지)</font></li>
		
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
 							<a href="#" class="btn" onclick="popupSearch('testTable1','testTable2');"><span><spring:message code="button.common.excel"/></span></a> 
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
						
							<input type="hidden" class="day" id="startDate" name="startDate">
						 
						</td>
						<th><span class="star">*</span> 점포선택</th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();" />전점조회
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
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:90px;" />
					
						<col/>
					</colgroup>
					<tr>
						<th rowspan="2" style="display:none;">점포코드</th>
						<th rowspan="2">점포명</th>
						<th rowspan="2">총 매입금액</th>
						<th colspan="3">허용장려금</th>
						<th rowspan="2">계</th>
						<th colspan="3">장려금조정</th>
						<th rowspan="2">계</th>
						<th rowspan="2">합계</th>
					</tr>
					<tr>
						<th>신상품</th>
						<th>매대</th>
						<th>성장</th>
						<th>신상품</th>
						<th>매대</th>
						<th>성장</th>
						
						
					</tr>
					</table>
					
					<div style="width:100%; height:395px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
				
						<col/>
					</colgroup>
					<c:if test="${not empty paymentList }">
						<c:forEach items="${paymentList}" var="list" varStatus="index" >
							<tr class="r1">
							
								<td align="center" style="display:none;">${list.STR_CD }</td>
								<td align="center" >${list.STR_NM }</td>
								<td align=right><fmt:formatNumber value="${list.TOT_BUY_AMT }" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.NEW_PROD_PROMO}" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.STAND_PROMO_AMT_AUTO }" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.OVER_PROMO_AMT_AUTO}" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.NEW_PROD_PROMO_AMT_AUTO}" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.NEW_PROD_PROMO_AMT_MAN}" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.STAND_PROMO_AMT_MAN}" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.OVER_PROMO_AMT_MAN}" type="number" currencySymbol="" /></td>
								<td align=right><fmt:formatNumber value="${list.NEW_PROD_PROMO_AMT_MAN}" type="number" currencySymbol="" /></td>
							    <td align=right><fmt:formatNumber value="${list.SUM}" type="number" currencySymbol="" /></td>
						
						
							</tr>
						</c:forEach>
					</c:if>
					
					
					<c:if test="${empty paymentList }">
						<tr><td colspan="11" align=center>Data가 없습니다.</td></tr>
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
					<li>결산정보</li>
					<li>기간정보</li>
					<li class="last">판매장려금 정보</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
