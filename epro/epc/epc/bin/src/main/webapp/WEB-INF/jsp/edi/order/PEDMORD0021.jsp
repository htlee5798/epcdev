<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
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
			form.action  = "<c:url value='/edi/order/PEDMORD0021Select.do'/>";
			form.submit();	
		}	
		
		
	}

	function doUpdate() {

		var form = document.forms[0];

		var tmpHour = "";
		var tmpMin = "";
		var tmpOrd_no = "";
		var tmpProd_no = "";

		if(confirm("납품가능정보를 변경합니까?")){

			if(form.firstList.value=="none"){
				alert("조회된 납품가능정보가 없습니다.");
				return;
			}

			if(form.hour.length){
				for(var i=0;i<form.hour.length;i++){
					tmpHour += form.hour[i].value + "-";
					tmpMin += form.min[i].value + "-";
					tmpOrd_no += form.ord_no[i].value + "-";
				}

				for(var i=0;i<form.prod_no.length;i++){
					tmpProd_no += form.prod_no[i].value + "-";
				}
			}else{
				tmpHour += form.hour.value + "-";
				tmpMin += form.min.value + "-";
				tmpOrd_no += form.ord_no.value + "-";
				tmpProd_no += form.prod_no.value + "-";
			}

			form.forward_hour.value = tmpHour;
			form.forward_min.value = tmpMin;
			form.forward_ordno.value = tmpOrd_no;
			form.forward_prodno.value = tmpProd_no;

			loadingMaskFixPos();
			form.action  = "/edi/order/PEDMORD0021Update.do";
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

	    /*
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}	
		*/

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

	function keyValid(obj){
		for( i=0 ; i<obj.value.length; i++){
			if(obj.value.charAt(i) <"0" || obj.value.charAt(i)>"9"){
				alert("<spring:message code='msg.common.error.noNum'/>");
				obj.focus();
				obj.value="0";
				return;
			}
		}
	}
</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
		<div>
			<form name="searchForm" method="post" action="#">
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
								<%-- <a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save"/></span></a> --%>
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
						
						<input type="hidden" id="forward_hour" name="forward_hour">
						<input type="hidden" id="forward_min" name="forward_min">
						<input type="hidden" id="forward_ordno" name="forward_ordno">
						<input type="hidden" id="forward_prodno" name="forward_prodno">
						
						<colgroup>
							<col style="width:15%" />
							<col style="width:30%" />
							<col style="width:15%" />
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
								<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" />
							</td>
							<th></th><td></td>
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
						<!-- div class="datagrade_nontitle_scroll" -->
						<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
						
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<c:if test="${not empty orderList }">
						<input type="hidden" name="firstList" value="exist"/>
							<colgroup>
								<col style="width:12%" />
								<col style="width:19%" />
								<col style="width:7%" />
								<col style="width:12%" />
								<col style="width:12%" />
								<col style="width:12%"  />
								<col style="width:14%" />
								<col style="width:12%"  />
							</colgroup>
							
							<c:set var="tmp"  value="empty" />
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<c:if test="${list.ORD_SLIP_NO != tmp }">
									<tr>
										<th>발주일</th>
										<th colspan="2">전표번호</th>
										<th>발주수량</th>
										<th>발주금액</th>
										<th colspan="2">납품가능시간</th>
										<th>확인여부</th>
									</tr>
									<tr  class="r1">
										<input type="hidden" id="ord_no" name="ord_no" value="${list.ORD_SLIP_NO }">
										<input type="hidden" id="ven" name="ven" value="${list.VEN_CD }">
										<td align=center>${list.ORD_DY }</td>
										<td colspan="2" align=center>${list.ORD_SLIP_NO  }</td>
										<td align="right"><fmt:formatNumber value="${list.TOT_QTY }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.TOT_PRC }" type="number" currencySymbol="" /></td>
										<td colspan="2" align=center><input type="text" size="1" value="${list.HOUR }" id="hour" name="hour" maxlength="2" onkeyup="javascript:keyValid(this);">시<input type="text" size="1" value="${list.MIN }" id="min" name="min" maxlength="2" onkeyup="javascript:keyValid(this);">분</td>
										<td align=center>${list.USER_HIT }</td>
										
										
								</tr>
								</c:if>
								<c:if test="${list.ORD_SLIP_NO != tmp }">
								<tr>
									<th>상품코드</th>
									<th>상품명</th>
									<th>규격</th>
									<th>입수</th>
									<th>판매코드</th>
									<th>발주수량</th>
									<th>발주금액</th>
									<th>납품가능수량</th>
								</tr>
								</c:if>
								<tr  class="r1">
									<input type="hidden" id="prod_no" name="prod_no" value="${list.PROD_CD }">
									<td align=center>${list.PROD_CD }</td>
									<td align="left">&nbsp; ${list.PROD_NM }</td>
									<td align=center>${list.ORD_UNIT }</td>
									<td align="right"><fmt:formatNumber value="${list.ORD_IPSU }" type="number" currencySymbol="" /></td>
									<td align=center>${list.ORD_SLIP_NO }</td>
									<input type="hidden" name="ordqty" value="${list.ORD_QTY }"/>
									<td align="right"><fmt:formatNumber value="${list.ORD_QTY }" type="number" currencySymbol="" /></td>
									<td align="right"><fmt:formatNumber value="${list.BUY_PRC }" type="number" currencySymbol="" /></td>
									<td align="center"><input type="text" size="5" value="${list.SPLY_ABLE_QTY }" id="sply_qty" name="sply_qty" maxlength="9" readonly="readonly" onkeyup="javascript:keyValid(this);"></td>
								</tr>
								<c:set var="tmp" value="${list.ORD_SLIP_NO }" />
							</c:forEach>
						</c:if>
						
						<c:if test="${empty orderList }">
							<input type="hidden" name="firstList" value="none"/>
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
						<li>주문응답서</li>
						<li class="last">납품가능정보</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>



</body>
</html>
