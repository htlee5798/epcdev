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

	function storePopUp(){
		PopupWindow("<c:url value='/edi/comm/PEDPCOM0001.do'/>");
	}
	
	function doUpdateSearch(){
		loadingMaskFixPos();
		var form = document.forms[0];
		form.action  = "<c:url value='/edi/delivery/PEDMDLY0023UpdateSelect.do'/>";
		form.submit();	
	}

	function forward(val,val2){
		var form = document.forms[0];
		

		var newHtml1 = "<select name='forward_udeli'>"+
						   "<option value='1'>재고부족</option>"+
						   "<option value='2'>인수거부</option>"+
						   "<option value='3'>주소불명</option>"+
						   "<option value='4'>고객부재</option>"+
						   "<option value='5'>착하불량</option>"+
						   "<option value='6'>기타</option>"+
					   "</select>";
		  
		var newHtml2 = "<select name='forward_udeli'>"+
					       "<option value='7'>=======</option>"+
					   "</select>";


		if(form.ckTmp.length){
			if(form.forward_accept[val].value == "3"){
				document.getElementById("udeli_select"+val).innerHTML=newHtml1;
				document.getElementById("chooseDate"+val).disabled=false;
			}else{
				document.getElementById("udeli_select"+val).innerHTML=newHtml2;
				document.getElementById("chooseDate"+val).disabled=true;
				document.getElementById("chooseDate"+val).value=val2;
			}
		}else{
			if(form.forward_accept.value == "3"){
				document.getElementById("udeli_select0").innerHTML=newHtml1;
				form.chooseDate0.disabled=false;
			}else{
				document.getElementById("udeli_select0").innerHTML=newHtml2;
				form.chooseDate0.disabled=true;
				document.getElementById("chooseDate"+val).value=val2;
			}
		}
	}

	
	function doUpdate(){	
		
		var form = document.forms[0];
		var tmp="";
		var tmp2="";
		var tmpVal = "";	
		
		if(form.firstList.value == "none"){
			alert("조회된 배달 정보가  없습니다.");
			return;
		}

		for(var i=0;i<form.forUpdateVal.length;i++){
			tmpVal += form.forUpdateVal[i].value+";";
		}
		var tmpValSplit=tmpVal.split(";");
		
//	alert("길이:"+form.forUpdate.length);

		if(form.forUpdate.length){
		//	alert("11111111111111111");
			
			for(var i=0;i<tmpValSplit.length-1;i++){
				
				tmp += form.forUpdate[i].value;
		//		alert(tmp);
				tmp2 += form.forward_accept[i].value+";";
		//		alert(tmp2);
				tmp2 += form.forward_udeli[i].value+";";
	//			alert(tmp2);
				tmp2 += document.getElementById("chooseDate"+i).value+"@";
		//		alert(tmp2);
			}
			
		}else{
		//	alert("222222222222222222");
				tmp += form.forUpdate.value;
		//		alert(tmp);
				tmp2 += form.forward_accept.value+";";
		//		alert(tmp2);//2
				tmp2 += form.forward_udeli.value+";";
		//		alert(tmp2);//7
				tmp2 += document.getElementById("chooseDate0").value+"@";
		//		alert(tmp2);//--@
		}

	//	alert("forwardValue:"+form.forwardValue.value);
	//	alert("forwardValue2:"+form.forwardValue2.value);
		
		
		form.forwardValue.value=tmp;
		form.forwardValue2.value=tmp2;
		
		loadingMaskFixPos();		
		form.action  = "<c:url value='/edi/delivery/PEDMDLY0023Update.do'/>";		
		form.submit();

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
								<option value="1" <c:if test="${param.hangmok == '1' }"> selected </c:if>>접수확인</option>
								<option value="2" <c:if test="${param.hangmok == '2' }"> selected </c:if>>배달완료</option>
								<option value="3" <c:if test="${param.hangmok == '3' }"> selected </c:if>>배달연기</option>
								<option value="4" <c:if test="${param.hangmok == '4' }"> selected </c:if>>배달실패</option>
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
						<th>점포명</th>
						<th>판매코드</th>
						<th>상품명</th>
						<th>희망배송일</th>
						<th>수량</th>
						<th rowspan="2">진행등록</th>
						<th rowspan="2">사유</th>
					</tr>
					<tr>
						<th>판매일자</th>
						<th>의뢰고객명</th>
						<th>의뢰주소</th>
						<th>의뢰전화1</th>
						<th>의뢰전화2</th>
					</tr>
					<tr>
						<th>접수일자</th>
						<th>인수고객</th>
						<th>인수주소</th>
						<th>인수전화1</th>
						<th>인수전화2</th>
						<th colspan="2">배달일자</th>
						
					</tr>
					<tr>
						<th colspan="8">특이사항</th>
					</tr>
					</table>
					
					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					<div style="width:100%; height:365px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; table-layout:fixed;">
					
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
								<td align="center" rowspan="3">${list.SLIP_NO }</td>
								<td align="center">${list.STR_NM }</td>
								<td align="center">${list.SRCMK_CD }</td>
								<td align="left">&nbsp;${list.PROD_NM }</td>
								<td align="center">${list.PROM_DY}</td>								
								<td align="right"><fmt:formatNumber value="${list.QTY }" type="number" currencySymbol="" /></td>
							    <td align="center" rowspan="2">
							    	<select name="forward_accept" onchange="javascript:forward('${idx }','${list.DELI_END_DY }');">
								     	<option value="1" <c:if test="${list.ACCEPT_FG == '1' }"> selected </c:if>>접수확인</option>
								     	<option value="2" <c:if test="${list.ACCEPT_FG == '2' }"> selected </c:if>>배달완료</option>
								      	<option value="3" <c:if test="${list.ACCEPT_FG == '3' }"> selected </c:if>>배달연기</option>
								      	<option value="4" <c:if test="${list.ACCEPT_FG == '4' }"> selected </c:if>>배달실패</option>
							      	</select>
							    </td>
							    
							    <td align="center" rowspan="2">
							    	<div id="udeli_select${idx }">
							    		<c:choose>
											<c:when test="${list.ACCEPT_FG == '3'}">
												<select name="forward_udeli">
											     	<option value="1">재고부족</option>
											     	<option value="2">인수거부</option>
											      	<option value="3">주소불명</option>
											      	<option value="4">고객부재</option>
											      	<option value="5">착하불량</option>
											      	<option value="6">기타</option>
										      	</select>
											</c:when>
											<c:otherwise>
												<select name="forward_udeli">
											     	<option value="7">=======</option>
										      	</select>
										   	</c:otherwise>
							   			</c:choose>
							      	</div>
							    </td>
							</tr>
							<tr class="r1">
								<td align="center">${list.SALE_DY }</td>
								<td align="center">${list.CUST_NM }</td>
								<td align="left">&nbsp;${list.CUST_ADD }</td>
								<td align="center">${list.CUST_TEL_NO1 }</td>
								<td align="center">${list.CUST_TEL_NO2 }</td>
							</tr>
							<tr class="r1">
								<td align="center">${list.ACCEPT_DY }</td>
								<td align="center">${list.RECV_NM }</td>
								<td align="left">&nbsp;${list.RECV_ADDR }</td>
								<td align="center">${list.RECV_TEL_NO1 }</td>
								<td align="center">${list.RECV_TEL_NO2 }</td>
								<td align="center" colspan="2">
									<input type="text" class="day" id="chooseDate${idx }" name="chooseDate${idx }" readonly value="${list.DELI_END_DY }" style="width:80px;" onClick="openCal('searchForm.chooseDate${idx }');" <c:if test="${list.ACCEPT_FG != 3}">disabled="true"</c:if>/>
								</td>
							</tr>
							<tr class="r1">
								<td align="center" colspan="8">&nbsp; ${list.REMARK }</td>
							</tr>
							
							<input type="hidden" name="forUpdate" value="${list.SALE_DY};${list.STR_CD};${list.POS_NO};${list.TRD_NO};${list.SRCMK_CD};${list.RECV_SEQ}@">
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
					<li>TOY 배달정보</li>
					<li class="last">완료등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
