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
		
		var form = document.forms[0];

		var tmp="";

		if(form.proCode1.value=="" && form.proCode2.value=="" && form.proCode3.value=="" && form.proCode4.value==""){
			alert("<spring:message code='msg.consult.orderstop.product'/>");
			return;
		}

		if(form.proCode1.value != "" ){
			tmp += form.proCode1.value+";";
		}
		if(form.proCode2.value != "" ){
			tmp += form.proCode2.value+";";
		}
		if(form.proCode3.value != "" ){
			tmp += form.proCode3.value+";";
		}
		if(form.proCode4.value != "" ){
			tmp += form.proCode4.value+";";
		}
		if(form.proCode5.value != "" ){
			tmp += form.proCode5.value+";";
		}
		if(form.proCode6.value != "" ){
			tmp += form.proCode6.value+";";
		}
		
		loadingMaskFixPos();

		form.productVal.value=tmp;

		form.action  = "<c:url value='/edi/consult/PEDMCST0010Select.do'/>";
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

		if(rangeDate>180){
			alert("<spring:message code='msg.common.fail.rangecalendar_180'/>");
			form.startDate.focus();
			return false;
		}	
		return true;
		
	}


	function storeClear(){
		var form = document.forms[0];
		form.storeVal.value="";
	}

	function keyValid(obj){
		for( i=0 ; i<obj.value.length; i++){
			if(obj.value.charAt(i) <"0" || obj.value.charAt(i)>"9"){
				alert("<spring:message code='msg.common.error.noNum'/>");
				obj.focus();
				obj.value="";
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
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						
						<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
						<input type="hidden" id="productVal" name="productVal"  />
						
						<colgroup>
							<col style="width:100px;" />
							<col style="width:200px;" />
							<col style="width:100px;" />
							<col style="width:200px;" />
						</colgroup>
						<tr>
							<th>협력업체 코드</th>
							<td>
								<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
							</td>
							<th><span class="star">*</span> 점포선택</th>
							<td>
								<input type="Radio" name="strCdType" <c:if test="${empty param.storeVal }"> Checked</c:if> onclick="javascript:storeClear();"/>전점조회
								<input type="Radio" name="strCdType" <c:if test="${not empty param.storeVal }"> Checked</c:if> onclick="javascript:storePopUp();"/>점포선택
							</td>
							
						</tr>
						<tr>
							<th>상품코드</th>
							<td colspan="3">
								1)&nbsp;<input type="text" name="proCode1" maxlength="10" value="${paramMap.proCode1}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								2)&nbsp;<input type="text" name="proCode2" maxlength="10" value="${paramMap.proCode2}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								3)&nbsp;<input type="text" name="proCode3" maxlength="10" value="${paramMap.proCode3}" onkeyup="javsscript:keyValid(this);"/><br>
								4)&nbsp;<input type="text" name="proCode4" maxlength="10" value="${paramMap.proCode4}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								5)&nbsp;<input type="text" name="proCode5" maxlength="10" value="${paramMap.proCode5}" onkeyup="javsscript:keyValid(this);"/>&nbsp;&nbsp;
								6)&nbsp;<input type="text" name="proCode6" maxlength="10" value="${paramMap.proCode6}" onkeyup="javsscript:keyValid(this);"/>
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
							<col style="width:30px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col  />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
						</colgroup>
						<tr>
							<th>NO.</th>
							<th>등록일자</th>
							<th>중단점포</th>
							<th>상품코드</th>
							<th>상품명</th>
							<th>등록상태</th>
							<th>중단여부</th>
							<th>중단확정일</th>
						</tr>
						</table>
						<div class="datagrade_scroll_sum">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:30px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col  />
							<col style="width:80px;" />
							<col style="width:80px;" />
							<col style="width:80px;" />
						</colgroup>
						<c:if test="${not empty conList }">
							<c:set var="num"  value="1" />
							<c:forEach items="${conList}" var="list" varStatus="index" >
								<tr class="r1">
									<td align="center">${num }</td>
									<td align="center">${list.REG_DY }</td>
									<td align="center">${list.STR_NM }</td>
									<td align="center">${list.PROD_CD }</td>
									<td align="left">&nbsp;&nbsp;${list.PROD_NM }</td>
									<c:choose>
										<c:when test="${list.CFM_FG eq '0'}">
											<td align="center">심사중</td>
										</c:when>
										<c:when test="${list.CFM_FG eq '1'}">
											<td align="center"><font color="red">MD확정</font></td>
										</c:when>
										<c:otherwise>
											<td align="center"><font color="blue">MD반려</font></td>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test="${list.STOP_FG eq '0'}">
											<td align="center">정상</td>
										</c:when>
										<c:otherwise>
											<td align="center">발주중단</td>
										</c:otherwise>
									</c:choose>
									<td align="center">${list.ORD_END_DY }</td>
								</tr>
								<c:set var="num" value="${num + 1 }" />
							</c:forEach>
						</c:if>
						<c:if test="${empty conList }">
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
						<li>협업</li>
						<li>협업정보</li>
						<li class="last">결과조회</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->		
	</div>

</body>
</html>

