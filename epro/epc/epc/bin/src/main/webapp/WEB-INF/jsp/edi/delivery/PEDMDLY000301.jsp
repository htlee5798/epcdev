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
		//alert(rcon.style.width);
		
		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/delivery/PEDMDLY0003Select.do'/>";
			form.submit();		
		}	
		
		
	}

	function storePopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0001.do'/>");
	}
	
	function doUpdateSearch(){
		var form = document.forms[0];
		form.action  = "<c:url value='/edi/delivery/PEDMDLY0003UpdateSelect.do'/>";
		form.submit();	
	}

	function changeSB(val, val2){
		var form = document.forms[0];
		
		var newHtml1 = "<select name='udeli_reason'>"+
						   "<option value='1'>상품재고부족</option>"+
						   "<option value='2'>고객인수거부</option>"+
						   "<option value='5'>착하불량</option>"+
						   "<option value='9'>기타</option>"+
					   "</select>";
					   
		var newHtml2 = "<select name='udeli_reason'>"+
						   "<option value='3'>고객주소불명</option>"+
						   "<option value='4'>고객부재</option>"+
						   "<option value='9'>기타</option>"+
					   "</select>";
					   
		var newHtml3 = "<select name='udeli_reason'>"+
					       "<option value='10'>=======</option>"+
					   "</select>";

		if(form.ckTmp.length){
			if(form.deli_status[val].value == "2"){
				document.getElementById("sbox"+val).innerHTML=newHtml1;
				document.getElementById("chooseDate"+val).disabled=false;
			}else if(form.deli_status[val].value == "3"){
				document.getElementById("sbox"+val).innerHTML=newHtml2;
				document.getElementById("chooseDate"+val).disabled=false;
			}else{
				document.getElementById("sbox"+val).innerHTML=newHtml3;
				document.getElementById("chooseDate"+val).disabled=true;
				document.getElementById("chooseDate"+val).value=val2;
			}
		}
		else{
			if(form.deli_status.value == "2"){
				document.getElementById("sbox0").innerHTML=newHtml1;
				form.chooseDate0.disabled=false;
			}else if(form.deli_status.value == "3") {
				document.getElementById("sbox0").innerHTML=newHtml2;
				form.chooseDate0.disabled=false;
			}else{
				document.getElementById("sbox0").innerHTML=newHtml3;
				form.chooseDate0.disabled=true;
				document.getElementById("chooseDate0").value=val2;
			}
		}
	}

	function doUpdate(){
		var form = document.forms[0];

		if(form.firstList.value=="none"){
			alert("조회된 배달 정보가  없습니다.");
			return;
		}
		
		var tmp="";
		var tmp2="";
		var tmpVal="";

		for(var i=0;i<form.forUpdateVal.length;i++){
			tmpVal += form.forUpdateVal[i].value+";";
		}

		var tmpValSplit=tmpVal.split(";");
		
		if(form.forUpdate.length){
			for(var i=0;i<tmpValSplit.length-1;i++){
				tmp += form.forUpdate[i].value;
				tmp2 += form.deli_status[i].value+";";
				tmp2 += form.udeli_reason[i].value+";";
				tmp2 += document.getElementById("chooseDate"+tmpValSplit[i]).value+"@";
			}
		}else{
				tmp = form.forUpdate.value;
				tmp2 += form.deli_status.value+";";
				tmp2 += form.udeli_reason.value+";";
				tmp2 += document.getElementById("chooseDate0").value+"@";
		}

		form.forwardValue.value=tmp;
		form.forwardValue2.value=tmp2;
		
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/delivery/PEDMDLY0003Update.do'/>";
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
</script>

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
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
					
					<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
					<input type="hidden" id="forwardValue" name="forwardValue"   />
					<input type="hidden" id="forwardValue2" name="forwardValue2"   />
					
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
						<th>진행구분</th>
						<td>
							<select id="hangmok" name="hangmok">
								<option value="all" <c:if test="${param.hangmok == 'all' }"> selected </c:if>>전체</option>
								<option value="0" <c:if test="${param.hangmok == '0' }"> selected </c:if>>진행중</option>
								<option value="1" <c:if test="${param.hangmok == '1' }"> selected </c:if>>배달완료</option>
								<option value="2" <c:if test="${param.hangmok == '2' }"> selected </c:if>>배달실패</option>
								<option value="3" <c:if test="${param.hangmok == '3' }"> selected </c:if>>배달연기</option>
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
						<li class="tit">검색내역</li>
						
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:160px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col  />
					</colgroup>
					<tr>
						<th rowspan="3">전표번호</th>
						<th>의뢰점포</th>
						<th>판매코드</th>
						<th>상품명</th>
						<th>모델명</th>
						<th>수량</th>
						<th rowspan="3">진행등록</th>
						<th rowspan="3">사유</th>
					</tr>
					<tr>
						<th>판매일자</th>
						<th rowspan="2">의뢰고객</th>
						<th rowspan="2">의뢰주소</th>
						<th rowspan="2">의뢰전화1</th>
						<th rowspan="2">의뢰전화2</th>
					</tr>
					<tr>
						<th>접수일자</th>
					</tr>
					<tr>
						<th>진행상태</th>
						<th>요청일자</th>
						<th>인수고객</th>
						<th>인수주소</th>
						<th>인수전화1</th>
						<th>인수전화2</th>
						<th colspan="2">일자</th>
					</tr>
					<tr>
						<th colspan="8">특이사항</th>
					</tr>
					</table>
					
					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					<div style="width:100%; height:343px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; table-layout:fixed;">
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:160px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col  />
					</colgroup>
					<c:if test="${not empty deliveryList }">
					<input type="hidden" name="firstList" value="exist"/>
						<c:set var="idx" value="0" />
						<c:forEach items="${deliveryList}" var="list" varStatus="index" >
							<tr class="r1">
								<td align="center" rowspan="3">${list.DELI_SLIP_NO }</td>
								<td align="center">${list.STR_NM }</td>
								<td align="center">${list.SRCMK_CD }</td>
								<td align="left">&nbsp;${list.PROD_NM }</td>
								<td align="left">&nbsp;${list.MODEL_NM }</td>
								<td align="right"><fmt:formatNumber value="${list.DELI_SALE_QTY }" type="number" currencySymbol="" /></td>
							    <td align="left" rowspan="3">
							    	<select  name="deli_status" onchange="javascript:changeSB('${idx}','${list.DELI_STATUS_DY}');">
										<option value="0" <c:if test="${list.DELI_END_FG == 0 }"> selected </c:if>>진행중</option>
										<option value="1" <c:if test="${list.DELI_END_FG == 1 }"> selected </c:if>>배달완료</option>
										<option value="2" <c:if test="${list.DELI_END_FG == 2 }"> selected </c:if>>배달실패</option>
										<option value="3" <c:if test="${list.DELI_END_FG == 3 }"> selected </c:if>>배달연기</option>
									</select>
							    </td>
							    <td align="left" rowspan="3">
							    	<div id="sbox${idx }" >
							    		<c:if test="${list.DELI_END_FG == 0 }">
							    			<select  name="udeli_reason">
								    			<option value="10">=======</option>
								    		</select>
							    		</c:if>
							    		<c:if test="${list.DELI_END_FG == 1 }">
							    			<select  name="udeli_reason">
								    			<option value="10">=======</option>
								    		</select>
							    		</c:if>
				
							    		<c:if test="${list.DELI_END_FG == 2 }">
							    			<select  name="udeli_reason">
								    			<option value="1">상품재고부족</option>
								    			<option value="2">고객인수거부</option>
								    			<option value="5">착하불량</option>
								    			<option value="9">기타</option>
								    		</select>
							    		</c:if>
							    		<c:if test="${list.DELI_END_FG == 3 }">
							    			<select  name="udeli_reason">
								    			<option value="3">고객주소불명</option>
								    			<option value="4">고객부재</option>
								    			<option value="9">기타</option>
								    		</select>
							    		</c:if>
							    	</div>
							    </td>
							    
							</tr>
							<tr class="r1">
								<td align="center">${list.SALE_DY }</td>
								<td rowspan="2" align="center">${list.CUST_NM }</td>
								<td rowspan="2" align="left">&nbsp;${list.CUST_ADD }</td>
								<td rowspan="2" align="center">${list.CUST_TEL_NO1 }</td>
								<td rowspan="2" align="center">${list.CUST_TEL_NO2 }</td>
							</tr>
							<tr class="r1">
								<td align="center">${list.ACCEPT_DY }</td>
							</tr>
							<tr class="r1">
								<c:choose>
								     <c:when test="${list.ACCEPT_FG == 0}">
								      <td align="center">미접수</td>
								     </c:when>
								     <c:when test="${list.ACCEPT_FG == 1}">
								      <td align="center">접수확인</td>
								     </c:when>
								     <c:when test="${list.ACCEPT_FG == 2}">
								      <td align="center">취소</td>
								     </c:when>
								     <c:when test="${list.CHG_FG eq U}">
								      <td align="center">배달변경</td>
								     </c:when>
								     <c:when test="${list.CHG_FG eq D}">
								      <td align="center">삭제</td>
								     </c:when>
								     <c:when test="${list.CHG_FG2 eq U}">
								      <td align="center">정보변경</td>
								     </c:when>
								     <c:when test="${list.CHG_FG2 eq D}">
								      <td align="center">삭제</td>
								     </c:when>
								     <c:otherwise>
								      <td align="center" ></td>
								     </c:otherwise>
							    </c:choose>
								<td align="center">${list.DELI_REQ_DY }</td>
								<td align="center">${list.RECV_NM }</td>
								<td align="left">&nbsp;${list.RECV_ADDR }</td>
								<td align="center">${list.RECV_TEL_NO1 }</td>
								<td align="center">${list.RECV_TEL_NO2 }</td>
								
								<td align="center" colspan="2">
									<input type="text" class="day" id="chooseDate${idx }" name="chooseDate${idx }" readonly value="${list.DELI_STATUS_DY}" style="width:80px;" onClick="openCal('searchForm.chooseDate${idx }');" />
								</td>
							</tr>
							<tr class="r1">
								<td align="center" colspan="8">${list.MAP } &nbsp;</td>
							</tr>
							<input type="hidden" name="forUpdate" value="${list.SALE_DY};${list.STR_CD};${list.POS_NO};${list.TRD_NO};${list.SALE_PROD_SEQ}@">
							<input type="hidden" name="forUpdateVal" value="${idx}">
							
							<c:set var="idx" value="${idx + 1 }" />
							<input type="hidden" name="ckTmp" />
						</c:forEach>
					</c:if>
					<c:if test="${empty deliveryList }">
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
					<li>배달정보</li>
					<li class="last">완료등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
