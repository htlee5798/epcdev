<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	
	function doPaySend(){
		
		var form = document.forms[0];
		
		if(!valCheck()) return;
		
		var url ="<c:url value='/edi/payment/PEDMPAY00301State.do'/>";
		
		form.payYm.value = form.startDate_year.value+form.startDate_month.value; 
		$.getJSON(url,
				{
				payYm: $("input[name=payYm]").val(),
				splyCycle: $("select[name=splyCycle]").val(),
				splySeq: $("select[name=splySeq]").val()
				}, function(j){
	     
		  if(j == "T")
		  {
			    if(confirm("<spring:message code='msg.common.confirm.send'/>") == false) return false;
			    
			    loadingMaskFixPos();
				form.action  = "<c:url value='/edi/payment/PEDMPAY00301Send.do'/>";
				form.submit();	
			}
		  else 
		  {
			  alert("<spring:message code='msg.common.success.stay'/>");
			  return;
		  }
					
	    });
		
		
			
		
	}
	
	function doSearch() {
		
		var form = document.forms[0];
		
		
		form.payYm.value = form.startDate_year.value+form.startDate_month.value; 
		
		if(!valCheck()) return;
		
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/payment/PEDMPAY00301Select.do'/>";
		form.submit();	
	}
	
	function valCheck() {
		
		
		var form = document.forms[0];
		
		if(!form.startDate_month.value.trim() || !form.startDate_year.value.trim()) {
			
			alert('<spring:message code='msg.payment.pay.pay_ym'/>');
			form.payYm.focus();
			return false;
		}
		
		if(!form.splyCycle.value.trim()) {
			
			alert('<spring:message code='msg.payment.pay.sply_cycle'/>');
			form.splyCycle.focus();
			return false;
		}
		
		if(!form.splySeq.value.trim()) {
			alert('<spring:message code='msg.payment.pay.sply_seq'/>');
			form.splySeq.focus();
			return false;
		}
		
		
		return true;
	}
	
	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}
	
	function initPage() {
		<c:if test="${not empty resultMessage }">
			alert('${resultMessage}');
		</c:if>
	}
</script>

</head>

<body onLoad="initPage();">
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		
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
							<a href="#" class="btn" onclick="doPaySend();"><span><spring:message code="button.payment.pay.send"/></span></a>
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.payment.pay.count"/></span></a>
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
						<th><span class="star">*</span> 매입월 </th>
						<td>
							<html:codeTag objId="startDate_year" objName="startDate_year" selectParam="${paramMap.startDate_year}" dataType="YEAR" subCode="2" comType="SELECT" formName="form"  />년&nbsp;
							<html:codeTag objId="startDate_month" objName="startDate_month" selectParam="${paramMap.startDate_month}" dataType="MONTH" comType="SELECT" formName="form"  />월
							
							<input type="hidden" id="payYm" name="payYm" />
						</td>
						<th><span class="star">*</span> 주기</th>
						<td>
						
							<select name="splyCycle">
								<option value="">선택</option>
								<option value="1" <c:if test="${paramMap.splyCycle eq '1' }">selected</c:if>>1</option>
								<option value="2" <c:if test="${paramMap.splyCycle eq '2' }">selected</c:if>>2</option>
								<option value="3" <c:if test="${paramMap.splyCycle eq '3' }">selected</c:if>>3</option>
								<option value="4" <c:if test="${paramMap.splyCycle eq '4' }">selected</c:if>>4</option>
								<option value="5" <c:if test="${paramMap.splyCycle eq '5' }">selected</c:if>>5</option>
							</select>
						</td>
						<th><span class="star">*</span> 회차</th>
						<td>
							<select name="splySeq">
								<option value="">선택</option>
								<option value="1" <c:if test="${paramMap.splySeq eq '1' }">selected</c:if>>1</option>
								<option value="2" <c:if test="${paramMap.splySeq eq '2' }">selected</c:if>>2</option>
								<option value="3" <c:if test="${paramMap.splySeq eq '3' }">selected</c:if>>3</option>
							</select>
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
						<li class="tit">카운트체크 결과조회</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
					<colgroup>
						<col style="width:80px;" />
						<col style="width:35px;" />
						<col style="width:35px;" />
						
						<col style="width:35px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:100px;" />
						<col  />
					</colgroup>
					<tr>
						
						<th>매입월</th>
						<th>주기</th>
						<th>회차</th>
						
						<th>전송<br>횟수</th>
						<th>대금본부</th>
						<th>대금EDI</th>
						<th>대금<br>정상여부</th>
						<th>공제본부</th>
						<th>공제EDI</th>
						<th>공재<br>정상여부</th>
						<th>전송일자</th>
						<th>진행여부</th>
					</tr>
					</table>
					<div style="width:100%; height:443px;overflow-x:hidden; overflow-y:scroll;  table-layout:fixed;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
					<col style="width:80px;" />
						<col style="width:35px;" />
						<col style="width:35px;" />
						
						<col style="width:35px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:70px;" />
						<col style="width:70px;" />
						<col style="width:80px;" />
						<col style="width:100px;" />
						<col  />
					</colgroup>
					<c:if test="${not empty paymentList }">
					
					
					<c:forEach items="${paymentList}" var="list" varStatus="index" >
					<tr class="r1">
						<td align="center">${list.PAY_YM}</td>
						<td align="center">${list.SPLY_CYCLE}</td>
						<td align="center">${list.SPLY_SEQ}</td>
						
						<td align="center">${list.SEQ}</td>
						<td align="right"><fmt:formatNumber value="${list.MD_PAYMENT_CNT }" type="number" currencySymbol="" />&nbsp;</td>
						<td align="right"><fmt:formatNumber value="${list.ED_PAYMENT_CNT }" type="number" currencySymbol="" />&nbsp;</td>
						<td align=center>
							<c:if test="${not empty list.MD_PAYMENT_CNT }">
								<c:choose>
									<c:when test="${list.PAY_STATE eq 'T' }"><font color=blue>정상</font></c:when>
									<c:otherwise><font color=red>비정상</font></c:otherwise>
								</c:choose>
							</c:if>
							&nbsp;
						</td>
						<td align="right"><fmt:formatNumber value="${list.MD_SUB_AGG_COUNT }" type="number" currencySymbol="" />&nbsp;</td>
						<td align="right"><fmt:formatNumber value="${list.ED_SUB_AGG_CNT }"     type="number" currencySymbol="" />&nbsp;</td>
						<td align=center>
							<c:if test="${not empty list.MD_PAYMENT_CNT }">
								<c:choose>
									<c:when test="${list.SUB_AGG_STATE eq 'T' }"><font color=blue>정상</font></c:when>
									<c:otherwise><font color=red>비정상</font></c:otherwise>
								</c:choose>
							</c:if>
							&nbsp;
						</td>
						<td align=center>${list.LST_CHG_DT}</td>
						<td align=center>
							<c:choose>
									<c:when test="${empty list.MD_PAYMENT_CNT }"><font color=red>전송중</font></c:when>
									<c:otherwise><font color=blue>전송완료</font></c:otherwise>
							</c:choose>
						</td>
					</tr>
					</c:forEach>
					</c:if>
					<c:if test="${empty paymentList }">
						<tr><td colspan="9" align=center>Data가 없습니다.</td></tr>
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
					<li>재무팀전송</li>
					<li class="last">대금결제전송</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
