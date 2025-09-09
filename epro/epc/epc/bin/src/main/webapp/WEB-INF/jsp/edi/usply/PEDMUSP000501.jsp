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
		form.action  = "<c:url value='/edi/usply/PEDMUSP0005UpdateSelect.do'/>";
		form.submit();	
		}
	}

	var newHtm1 = "<select name='usp2'>"+
				  "<option value='11'>업체재고부족</option>"+
				  "<option value='13'>업체물류부족</option>"+
				  "<option value='14'>업체기타오류</option>"+
				  "</select>";

	var newHtm2 = "<select name='usp2'>"+
				  "<option value='21'>당사발주오류</option>"+
				  "<option value='23'>당사물류오류</option>"+
				  "<option value='24'>당사발주중지미조치</option>"+
				  "<option value='25'>당사기타오류</option>"+
				  "</select>";
	    
	function uspSwap(val){
					  
		var form = document.forms[0];

		if(form.rowcnt.length){
			if(form.usp1[val].value == "1"){
				document.getElementById("second_usp"+val).innerHTML=newHtm1;
			}else if(form.usp1[val].value == "2"){
				document.getElementById("second_usp"+val).innerHTML=newHtm2;
			}else if(form.usp1[val].value == "3"){
				document.getElementById("second_usp"+val).innerHTML=newHtm1;
			}
		}else{
			if(form.usp1.value == "1"){
				document.getElementById("second_usp0").innerHTML=newHtm1;
			}else if(form.usp1.value == "2"){
				document.getElementById("second_usp0").innerHTML=newHtm2;
			}else if(form.usp1.value == "3"){
				document.getElementById("second_usp0").innerHTML=newHtm1;
			}
		}
		
		
	}

	function uspTopSwap(){

		var newHtml1 = "<select name='usp_top2'>"+
					  "<option value='11'>업체재고부족</option>"+
					  "<option value='13'>업체물류부족</option>"+
					  "<option value='14'>업체기타오류</option>"+
					  "</select>";

		var newHtml2 = "<select name='usp_top2'>"+
					  "<option value='21'>당사발주오류</option>"+
					  "<option value='23'>당사물류오류</option>"+
					  "<option value='24'>당사발주중지미조치</option>"+
					  "<option value='25'>당사기타오류</option>"+
					  "</select>";
					  
		var form = document.forms[0];

		if(form.usp_top1.value == "1"){
			document.getElementById("usp_top_div").innerHTML=newHtml1;
		}else if(form.usp_top1.value == "2"){
			document.getElementById("usp_top_div").innerHTML=newHtml2;
		}
	}

	function batchSwap(){
		var form = document.forms[0];
		
		if(form.ckValid.value=="empty"){
			alert("조회된 Data가 없습니다.");
			return;
		}
		
		if(form.rowcnt.length){
			if(form.usp_top1.value == "1"){
				for(var i=0;i<form.usp1.length;i++){
					form.usp1[i].value=1;
					document.getElementById("second_usp"+i).innerHTML=newHtm1;
					form.usp2[i].value=form.usp_top2.value;
				}
			}else if(form.usp_top1.value == "2"){
				for(var i=0;i<form.usp1.length;i++){
					form.usp1[i].value=2;
					document.getElementById("second_usp"+i).innerHTML=newHtm2;
					form.usp2[i].value=form.usp_top2.value;
				}
			}
		}else{
			if(form.usp_top1.value == "1"){
					form.usp1.value=1;
					document.getElementById("second_usp0").innerHTML=newHtm1;
					form.usp2.value=form.usp_top2.value;
			}else if(form.usp_top1.value == "2"){
					form.usp1.value=2;
					document.getElementById("second_usp0").innerHTML=newHtm2;
					form.usp2.value=form.usp_top2.value;
			}
		}

		

	}

	function doUpdate() {

		var form = document.forms[0];
		
		if(form.ckValid.value=="empty"){
			alert("조회된 Data가 없습니다.");
			return;
		}

		var tmpUsp1 = "";
		var tmpUsp2 = "";
		var tmpProd_cd = "";
		var tmpOrd_slip_no = "";

		if(form.rowcnt.length){
			for(var i=0;i<form.usp1.length;i++){
				tmpUsp1 += form.usp1[i].value + "-";
				tmpUsp2 += form.usp2[i].value + "-";
				tmpProd_cd += form.usp_prod_cd[i].value + "-";
				tmpOrd_slip_no += form.usp_ord_slip_no[i].value + "-";
			}
				form.forwardUsp1.value = tmpUsp1;
				form.forwardUsp2.value = tmpUsp2;
				form.forwardProd_cd.value = tmpProd_cd;
				form.forwardOrd_slip_no.value = tmpOrd_slip_no;
		}else{
			tmpUsp1 = form.usp1.value + "-";
			tmpUsp2 = form.usp2.value + "-";
			tmpProd_cd = form.usp_prod_cd.value + "-";
			tmpOrd_slip_no = form.usp_ord_slip_no.value + "-";

			form.forwardUsp1.value = tmpUsp1;
			form.forwardUsp2.value = tmpUsp2;
			form.forwardProd_cd.value = tmpProd_cd;
			form.forwardOrd_slip_no.value = tmpOrd_slip_no;
		}
		
		form.action  = "<c:url value='/edi/usply/PEDMUSP0005Update.do'/>";
		form.submit();
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
	

</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
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
							<a href="#" class="btn" onclick="doUpdateSearch();"><span>등록조회</span></a>
							<a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save"/></span></a>
	
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
		
		
					<input type="hidden" id="forward_hour" name="forwardUsp1">
					<input type="hidden" id="forward_hour" name="forwardUsp2">
					<input type="hidden" id="forward_hour" name="forwardProd_cd">
					<input type="hidden" id="forward_hour" name="forwardOrd_slip_no">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
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
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
					</tr>
					<tr>	
						<th>미납사유</th>
						<td colspan="3">
							1 : <select name="usp_top1" onchange="javsscript:uspTopSwap();">
								
								<option value="1">협력업체</option>
								<option value="2">롯데마트</option>
							</select> &nbsp;&nbsp;
							2 :<span id="usp_top_div">
								<select name="usp_top2">
									<option value="11">업체재고부족</option>
									<option value="13">업체물류부족</option>
									<option value="14">업체기타오류</option>
								</select>
							</span>&nbsp;&nbsp;
							<a href="#" class="btn" onclick="batchSwap();"><span>일괄적용</span></a>
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
						<col style="width:150px;" />
						<col style="width:40px;" />
						<col style="width:40px;" />
						<col style="width:70px;" />
						<col style="width:60px;" />
						<col style="width:60px;" />
						<col style="width:80px;" />
						<col style="width:140px;" />
						<col  />
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
					</tr>
					</table>
					<div class="datagrade_scroll">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
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
					</colgroup>
					<c:if test="${not empty usplyList }">
						<input type="hidden" name="ckValid" value="exist"/>
						<c:set var="idx"  value="0" />
						
						<c:forEach items="${usplyList}" var="list" varStatus="index" >
							<input type="hidden" name="rowcnt"/>
							<input type="hidden" name="usp_prod_cd" value=${list.PROD_CD }>
							<input type="hidden" name="usp_ord_slip_no" value=${list.ORD_SLIP_NO }>
							
							<tr class="r1">
								<td align="center">${list.SRCMK_CD }</td>
								<td align="left">${list.PROD_NM }</td>
								<td align="right"><fmt:formatNumber value="${list.ORD_IPSU }" type="number" currencySymbol="" />&nbsp;</td>
								<td align="center">${list.ORD_UNIT }</td>
								<td align="center">${list.STR_NM }</td>
								<td align="right"><fmt:formatNumber value="${list.USPLY_QTY }" type="number" currencySymbol="" />&nbsp;</td>
								<td align="right"><fmt:formatNumber value="${list.USPLY_BUY_AMT }" type="number" currencySymbol="" />&nbsp;</td>
								
								<c:if test="${list.VEN_REASON_FG eq null }">
								<td align="center">
									<select name="usp1" onchange="javsscript:uspSwap('${idx }');">
										<option value="1">협력업체</option>
										<option value="2">롯데마트</option>
									</select>
								</td>
								<td align="center">
									<div id="second_usp${idx }">
										<select name="usp2">
											<option value="11">업체재고부족</option>
											<option value="13">업체물류부족</option>
											<option value="14">업체기타오류</option>
										</select>
									</div>
								</td>
								</c:if>
								
								<c:if test="${list.VEN_REASON_FG eq 1 }">
								
								<td align="center">
									<select name="usp1" onchange="javsscript:uspSwap('${idx }');">
										<option value="1" selected>협력업체</option>
										<option value="2">롯데마트</option>
									</select>
								</td>
								<td align="center">
									<div id="second_usp${idx }">
										<select name="usp2">
											<option value="11" <c:if test="${list.VEN_DETAIL_FG eq 11}">selected</c:if> >업체재고부족</option>
											<option value="13" <c:if test="${list.VEN_DETAIL_FG eq 13}">selected</c:if> >업체물류부족</option>
											<option value="14" <c:if test="${list.VEN_DETAIL_FG eq 14}">selected</c:if> >업체기타오류</option>
										</select>
									</div>
								</td>
								</c:if>
								
								<c:if test="${list.VEN_REASON_FG eq 2 }">
								<td align="center">
									<select name="usp1" onchange="javsscript:uspSwap('${idx }');">
										<option value="1" >협력업체</option>
										<option value="2" selected>롯데마트</option>
									</select>
								</td>
								<td align="center">
									<div id="second_usp${idx }">
										<select name="usp2">
											<option value='21' <c:if test="${list.VEN_DETAIL_FG eq 21}">selected</c:if> >당사발주오류</option>
											<option value='23' <c:if test="${list.VEN_DETAIL_FG eq 23}">selected</c:if> >당사물류오류</option>
											<option value='24' <c:if test="${list.VEN_DETAIL_FG eq 24}">selected</c:if> >당사발주중지미조치</option>
											<option value='25' <c:if test="${list.VEN_DETAIL_FG eq 25}">selected</c:if> >당사기타오류</option>
										</select>
									</div>
								</td>
								</c:if>
								<td></td>
							</tr>
							<c:set var="idx" value="${idx + 1 }" />
						</c:forEach>
					</c:if>
					<c:if test="${empty usplyList }">
						<input type="hidden" name="ckValid" value="empty"/>
						<tr><td colspan="10" align=center>Data가 없습니다.</td></tr>
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
