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
		
		loadingMaskFixPos();
		form.action  = "<c:url value='/edi/payment/PEDMPAY0001Select.do'/>";
		form.submit();	
	}

	function popupSearch(tbName1, tbName2){		
		
		var tbody1 = $('#' + tbName1 + ' tbody');
		var tbody2 = $('#' + tbName2 + ' tbody');		

		var form=document.forms[0];	

		var vendor=form.vendor.value;
		var vencd=form.entp_cd.value;

		var tmp="";
		
		if(vencd==""){
			tmp=vendor;
		}else{
			tmp=vencd;
		}	

		form.staticTableBodyValue.value = "<CAPTION>결산정보 사업자 등록정보 현황표<br>"+
		"[업체 : "+tmp+"]<br>"+
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

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		
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
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0" >
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th>협력업체 코드</th>
						<td colspan="3">
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form"  />
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
						<col style="width:90px;" />
						<col style="width:55px;" />
						<col style="width:90px;" />
						<col style="width:200px;"/>
						<col style="width:60px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col  />
					</colgroup>
					<tr>
						<th>점포</th>
						<th>점포코드</th>
						<th>사업자등록번호</th>
						<th>주소</th>
						<th>대표자</th>
						<th>전화번호</th>
						<th>업태</th>
						<th>종목</th>
					</tr>
					</table>
					
					<div style="width:100%; height:461px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
					<colgroup>
						<col style="width:90px;" />
						<col style="width:55px;" />
						<col style="width:90px;" />
						<col style="width:200px;"/>
						<col style="width:60px;" />
						<col style="width:90px;" />
						<col style="width:90px;" />
						<col  />
					</colgroup>
					<c:if test="${not empty paymentList }">
						<c:set var="total_qty"  value="0" />
						<c:set var="total_prc"  value="0" />
						<c:forEach items="${paymentList}" var="list" varStatus="index" >
							<tr class="r1">
								<td align="center">${list.STR_NM }</td>
								<td align="center">${list.STR_CD }</td>
								<td align="center">${list.BMAN_NO }</td>
								<td >&nbsp;${list.ADDR }</td>
								<td align="center">${list.CEO_NM }</td>
								<td align="center">${list.REP_TEL_NO }</td>
								<td align="center">${list.BTYP }</td>
								<td align="center">${list.BKIND }</td>
							</tr>
							
						</c:forEach>
					</c:if>
					<c:if test="${empty paymentList }">
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
					<li>결산정보</li>
					<li>기간정보</li>
					<li class="last">사업자 등록정보</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>

