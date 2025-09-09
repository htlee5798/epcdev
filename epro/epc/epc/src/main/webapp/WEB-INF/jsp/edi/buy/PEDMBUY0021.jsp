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

	function doSearch() {
		
		
		var form = document.forms[0];
		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/buy/PEDMBUY0021Select.do'/>";
			form.submit();
		}	
			
	}
	

	
	
	function productDetailPopup(val1,val2,val3,val4,val5){
		var form = document.forms[0];

		form.p_str_cd.value=val1;
		form.p_reg_dy.value=val2;
		form.p_prod_cd.value=val3;
		form.p_send_fg.value=val4;
		form.p_ven_cd.value=val5;

		form.target="_blankPop";
		form.action ="<c:url value='/edi/buy/PEDPBUY0021.do'/>";

		var popInfo = window.open('','_blankPop','top=0, left=0, width=900, height=450, toolbar=no, status=yes, scrollbars=yse');

		popInfo.focus();
	    form.submit();
		  
		form.target = "";
		form.action = "";

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

</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		
		<input type="hidden" id="p_str_cd" name="p_str_cd">
		<input type="hidden" id="p_reg_dy" name="p_reg_dy">
		<input type="hidden" id="p_prod_cd" name="p_prod_cd">
		<input type="hidden" id="p_send_fg" name="p_send_fg">
		<input type="hidden" id="p_ven_cd" name="p_ven_cd">
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">검색조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
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
							<th>협력업체 코드</th>
								<td>
									<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
								</td>
								
							
							<th></th>
								<td>
								</td>
							
						</tr>
						<tr>
							<th><span class="star">*</span> 조회일자 </th>
							<td>
								<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
							</td>
							
							<th><span class="star">*</span> 조치구분 </th>
							<td>
								<input type="Radio" name="measure" value="1" <c:if test="${paramMap.measure == 1}"> Checked</c:if> />미조치
								<input type="Radio" name="measure" value="2" <c:if test="${paramMap.measure == 2}"> Checked</c:if> />조치
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
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:100px;" />
						<col style="width:110px;" />
						<col />
						<col style="width:90px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:17px;" />
					</colgroup>
					<tr>
						<th>의뢰일자</th>
						<th>상품코드</th>
						<th>상품명</th>
						<th>발생일자</th>
						<th>발생지점</th>
						<th>발생유형</th>
						<th>조치구분</th>
						<th>&nbsp;</th>
					</tr>
					</table>
					
					<div class="datagrade_scroll_all">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:100px;" />
						<col style="width:110px;" />
						<col />
						<col style="width:90px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
						<col style="width:110px;" />
					</colgroup>
					
					<c:if test="${not empty buyList }">
					<c:forEach items="${buyList}" var="list" varStatus="index" >
						<tr class="r1">
							<td align="center">${list.SEND_DATE }</td>
							<td align="center">${list.PROD_CD }</td>
							<td >&nbsp; - <%-- <a href="javascript:productDetailPopup('${list.STR_CD}','${list.REG_DY}','${list.PROD_CD}','${list.SEND_FG}','${list.VEN_CD}');">${list.PROD_NM }</a> --%></td>
							<td align="center">${list.REG_DY }</td>
							<td align="center">${list.STR_NM }</td>
							<td align="center">${list.DENY_FG }</td>
							<c:if test="${list.SEND_FG eq false }">
								<td align="center"><input type="checkbox" disabled="true"></td>
							</c:if>
							<c:if test="${list.SEND_FG eq true }">
								<td align="center"><input type="checkbox" checked disabled="true" ></td>
							</c:if>
						</tr>
					</c:forEach>
					</c:if>
					<c:if test="${empty buyList }">
						<tr><td colspan="7" align=center>Data가 없습니다.</td></tr>
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
					<li>매입정보</li>
					<li>입고거부상품</li>
					<li class="last">센터입고거부상품</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>

</html>

