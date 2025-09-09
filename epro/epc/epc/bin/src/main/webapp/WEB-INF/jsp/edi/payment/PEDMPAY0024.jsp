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
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];
		
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/payment/PEDMPAY0024Select.do'/>";
		form.submit();	
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

		form.staticTableBodyValue.value = "<CAPTION>결산정보 잔액조회 현황표<br>"+
		"[조회기간 : "+date+"] [업체 : "+tmp+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() + tbody2.parent().html();
	
		form.name.value = "statics";
		form.action="<c:url value='/edi/comm/PEDPCOM0003.do'/>";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="";
	}	

	function printText(){
		var form = document.forms[0];
		var date=form.startDate.value+"~"+form.endDate.value;

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}		
	
		form.text_data.value="[조회기간 : "+date+"] [업체 : "+tmp+"]";
	
		form.action  = "<c:url value='/edi/payment/PEDMPAY0024Text.do'/>";
		form.submit();	
	
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
							
						<th>협력업체 코드</th>
						<td colspan="3">
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form"  defName="전체" />
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
					
					<div style="width:100%; height:485px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; table-layout:fixed;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width=985 bgcolor=efefef  id="testTable2">
					
					<colgroup>
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
						<col style="width:120px;" />
					</colgroup>
					<tr bgcolor="#e4e4e4">
						<th rowspan="2">납품번호</th>
						<th rowspan="2">정기지급일</th>
						<th colspan="2">전일누적 통보금액</th>
						<th colspan="5">당일 은행전송 대상액 산정</th>
						<th colspan="2">당일 실은행통보금액</th>
						<th colspan="2">당일 누적통보금액</th>
					</tr>
					<tr bgcolor="#e4e4e4">
						<th>은행통보금액</th>
						<th>대출가능액</th>
						<th>매입/매출금액</th>
						<th>물류비</th>
						<th>장려금</th>
						<th>공제예상액</th>
						<th>당일전송대상액</th>
						<th>통보금액</th>
						<th>대출가능액</th>
						<th>통보금액</th>
						<th>대출가능액</th>
					</tr>
					<c:set var="row_cnt"  value="1" />
					<c:if test="${fn:length(paymentList) > 0 }">
					<c:forEach items="${paymentList}" var="list" varStatus="index" >
						<tr class="r1" bgcolor=ffffff>
							<td align="center">${list.SLIP_NO }</td>
							<td align="center">${list.PAY_DAY }</td>
							<td align="right"><fmt:formatNumber value="${list.SEND_AMT }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.ABLE_AMT }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.SPLY_AMT }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.LOGI_AMT }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.SALE_PROMO_AMT }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.RTN_AMT }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.PAY_AMT }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.AAA }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.BBB }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.CCC }" type="number" currencySymbol="" /></td>
							<td align="right"><fmt:formatNumber value="${list.DDD }" type="number" currencySymbol="" /></td>
							
							
						</tr>
						<c:set var="row_cnt" value="${row_cnt+1 }" />
					</c:forEach>
					<c:forEach begin="${row_cnt}" end ="15">
					      <tr class="r1"  bgcolor=ffffff>
						    <td>&nbsp;</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>	
					 </c:forEach>
					 </c:if>
					<c:if test="${empty paymentList }">
						<tr><td colspan="13" bgcolor=ffffff align=center>Data가 없습니다.</td></tr>
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
					<li>거래실적정보</li>
					<li class="last">패밀리론</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>

