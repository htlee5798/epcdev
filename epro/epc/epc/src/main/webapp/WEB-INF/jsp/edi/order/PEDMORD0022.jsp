<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
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
		
		
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];
		//alert(rcon.style.width);
		
		if(dateValid(form)){
			loadingMaskFixPos();
			form.action  = "<c:url value='/edi/order/PEDMORD0022Select.do'/>";
			form.submit();	
		}	
	}


	function doUpdate() {
		var form = document.forms[0];
		var tmpOrd_no = "";
		var tmpProd = "";
		var tmpVen = "";
		var tmpNegoprc = "";
		var tmpOrigin = "";

		if(confirm("신선매입정보를 변경합니까?")){

			if(form.firstList.value=="none"){
				alert("조회된 신선매입정보가  없습니다.");
				return;
			}
			
			if(form.ord_no.length){
				for(var i=0;i<form.ord_no.length;i++){
					tmpOrd_no += form.ord_no[i].value + "-";
					tmpProd += form.prod[i].value + "-";
					tmpVen += form.ven[i].value + "-";
					tmpNegoprc += form.negoprc2[i].value + "-";
					tmpOrigin += form.origin2[i].value + "-";

					if(form.negoprc2[form.ord_no.length-1].value==""){
						tmpNegoprc = tmpNegoprc.substr(0,form.ord_no.length-1);
						tmpNegoprc += "0"+"-"; 
					}
					if(form.origin2[form.ord_no.length-1].value==""){
						tmpOrigin = tmpOrigin.substr(0,form.ord_no.length-1);
						tmpOrigin += "none"+"-"; 
					}
				}
			}else{
				tmpOrd_no = form.ord_no.value + "-";
				tmpProd = form.prod.value + "-";
				tmpVen = form.ven.value + "-";
				tmpNegoprc = form.negoprc2.value + "-";
				tmpOrigin = form.origin2.value + "-";

				if(form.negoprc2.value==""){
					tmpOrigin = "0"+"-"; 
				}

				if(form.origin2.value==""){
					tmpOrigin = "none"+"-"; 
				}
			}
			form.forward_ordno.value = tmpOrd_no;
			form.forward_prod.value = tmpProd;
			form.forward_ven.value = tmpVen;
			form.forward_nego.value = tmpNegoprc;
			form.forward_origin.value = tmpOrigin;

			form.action  = "<c:url value='/edi/order/PEDMORD0022Update.do'/>";			
			form.submit();		
				
		}
		
		
	}

	function storePopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0001.do'/>");
	}

	function productPopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0002.do'/>");
	}
	
	function forwardValue(){
		var form = document.forms[0];

		$("td.compare_prod").each(function(i){
			if($(this).text(tmp[i])==form.pr_code.value){
				form.negoprc2[i].value=form.negoprc1.value;
				form.origin2[i].value=form.origin1.value;
			}
		});
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
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<%-- <a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save"/></span></a> --%>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
					<input type="hidden" id="productVal" name="productVal" />
					
					<input type="hidden" id="forward_ordno" name="forward_ordno" />
					<input type="hidden" id="forward_prod" name="forward_prod" />
					<input type="hidden" id="forward_ven" name="forward_ven" />
					<input type="hidden" id="forward_nego" name="forward_nego" />
					<input type="hidden" id="forward_origin" name="forward_origin" />
					
					<input type="hidden" id="storeName" name="storeName" />
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 조회기간 </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> 점포선택</th>
						<td>
							<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if>  onclick="javascript:storeClear();"/>전점조회
							<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/>점포선택
						</td>
					</tr>
					<tr>
						<th>협력업체 코드</th>
						<td colspan="3">
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" />
						</td >
						<%-- <th>상품코드</th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.productVal }"> Checked</c:if>  onclick="javascript:productClear();"/>전상품조회   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.productVal }"> Checked</c:if> onclick="javascript:productPopUp();"/>상품선택
						</td> --%>
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
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<c:if test="${not empty orderList }">
						<input type="hidden" name="firstList" value="exist"/>
							<colgroup>
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%"  />
								<col style="width:9%" />
								<col style="width:9%"  />
								<col style="width:9%"  />
								<col style="width:9%"  />
							</colgroup>
							
							<tr>
								<td colspan="10">
									<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
									<colgroup>
										<col style="width:25%;" />
										<col style="width:25%;" />
										<col style="width:25%;" />
										<col style="width:25%;"   />
									</colgroup>
									<tr>
										<th>상품코드</th>
										<th>매입원가</th>
										<th id="tmp">원산지</th>
										<th rowspan="2">
											<a href="#" class="btn" onclick="javascript:forwardValue();"><span><spring:message code="button.common.setall"/></span></a> <%-- 일괄적용 --%>
										</th>
									</tr>
									<tr >
										<td align=center><input type="text" id="pr_code" name="pr_code"></td>
										<td align=center><input type="text" id="negoprc1" name="negoprc1" onkeyup="javascript:keyValid(this);"></td>
										<td align=center><input type="text" id="origin1" name="origin1"></td>
									</tr>
									
									</table>
								</td>
							</tr>
							<tr>
								<th>상품코드</th>
								<th>판매코드</th>
								<th>상품명</th>
								<th>규격</th>
								<th>점포명</th>
								<th>발주수량</th>
								<th>납품가능<br>수량</th>
								<th>발주원가</th>
								<th>매입원가</th>
								<th>원산지</th>
							</tr>
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<input type="hidden" name="ord_no" value="${list.ORD_SLIP_NO }"/>
								<input type="hidden" name="prod" value="${list.PROD_CD }"/>
								<input type="hidden" name="ven" value="${list.VEN_CD }"/>
								<tr class="r1">
									<td class="compare_prod" align=center>${list.PROD_CD }</td>
									<td align=center>${list.SRCMK_CD  }</td>
									<td align="left">&nbsp;${list.PROD_NM }</td>
									<td align=center>${list.PROD_STD  }</td>
									<td align=center>${list.STR_NM  }</td>
									<td align="right"><fmt:formatNumber value="${list.ORD_QTY }" type="number" currencySymbol="" /></td>
									<td align="center"><input type="text" size="7" id="sply_qty" name="sply_qty" value="${list.SPLY_ABLE_QTY }"  readonly="readonly" maxlength="9" onkeyup="javascript:keyValid(this);"></td>
									<td align="right"><fmt:formatNumber value="${list.NEGO_BUY_PRC }" type="number" currencySymbol=""/></td>
									<td align="center"><input type="text" size="7" id="negoprc2" name="negoprc2" value="${list.NEGO_BUY_PRC }" maxlength="12" onkeyup="javascript:keyValid(this);"></td>
									<td align="center"><input type="text" size="7" id="origin2" name="origin2" maxlength="15" value="${list.PRODUCER }"></td>
								</tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty orderList }">
						<input type="hidden" name="firstList" value="none"/>
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
					<li>발주정보</li>
					<li>주문응답서</li>
					<li class="last">신선매입정보변경</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
