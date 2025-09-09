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
			form.action  = "<c:url value='/edi/usply/PEDMUSP0005Select.do'/>";
			form.submit();
		}	
			
	}

	function doUpdateSearch() {

		var form = document.forms[0];
		
		if(dateValid(form)){
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/usply/PEDMUSP0005UpdateSelect.do'/>";
		form.submit();	
		}	
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
			//alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			alert("검색일자가 범위가 15일을 넘을 수 없습니다.");
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

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;
		var tmp="";
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}

		form.staticTableBodyValue.value = "<CAPTION>미납정보 미납사유조회 현황표<br>"+
		"[조회기간 : "+date+"] [업체 : "+tmp+"]<br>"+
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
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		
		<input type="hidden" name="vendor" value="${paramMap.ven }">
		
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
							<!-- <a href="#" class="btn" onclick="doUpdateSearch();"><span>등록페이지</span></a> -->
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
						<th><span class="star">*</span> 조회일자 </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th>협력업체 코드</th>
						<td><!--defName="전체"  -->
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form"  />
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
						</td>
						<th></th>
						<td >
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
						<col style="width:100px;" />
						<col style="width:150px;" />
						<col style="width:40px;" />
						<col style="width:40px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:60px;" />
						<col style="width:80px;" />
						<col style="width:140px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th>상품코드</th>
						<th>상품명</th>
						<th>입수</th>
						<th>단위</th>
						<th>점포</th>
						<th>미납수량</th>
						<th>미납금액</th>
						<th>미납사유1</th>
						<th>미납사유2</th>
						<th>확정여부</th>
						<th>&nbsp;</th>
					</tr>
					</table>
					<div class="datagrade_scroll">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
						<col style="width:100px;" />
						<col style="width:150px;" />
						<col style="width:40px;" />
						<col style="width:40px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:60px;" />
						<col style="width:80px;" />
						<col style="width:140px;" />
						<col  />
						<col style="width:17px;" />
					</colgroup>
					<c:if test="${not empty usplyList }">
						<c:forEach items="${usplyList}" var="list" varStatus="index" >
							<tr class="r1">
								<td align="center" style="mso-number-format:'\@'">${list.SRCMK_CD }</td>
								<td align="left">${list.PROD_NM }</td>
								<td align="right"><fmt:formatNumber value="${list.ORD_IPSU }" type="number" currencySymbol="" /></td>
								<td align="center">${list.ORD_UNIT }</td>
								<td align="center">${list.STR_NM }</td>
								<td align="right"><fmt:formatNumber value="${list.USPLY_QTY }" type="number" currencySymbol="" /></td>
								<td align="right"><fmt:formatNumber value="${list.USPLY_BUY_AMT }" type="number" currencySymbol="" /></td>
								<td align="center">${list.VEN_REASON_NM }</td>
								<td align="center">${list.VEN_DETAIL_NM }</td>
								<c:if test="${list.REG_REASON_FG != list.CFM_REASON_FG or list.REG_DETAIL_FG != list.CFM.DETAIL_FG }">
									<td colspan="2" align="center">바이어<br>확정</td>
								</c:if>
								<c:if test="${list.REG_REASON_FG == list.CFM_REASON_FG or list.REG_DETAIL_FG == list.CFM.DETAIL_FG }">
									<td colspan="2" align="center">바이어<br>미확정</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty usplyList }">
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
					<li>미납정보</li>
					<li>기간정보</li>
					<li class="last">미납사유입력&조회</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
