<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
$(document).ready(function($){
	bmanSet();
});

function bmanSet(){
	var form = document.forms[0];

	var tmp = form.bman.value.split(";");

	for(var i=0;i<tmp.length-1;i++){
		
		form.sele_bman.options.add(new Option(tmp[i], tmp[i]));
		
	}
	
	form.sele_bman.value = "${paramMap.sele_bman}";
}

function doSearch(){
	var form = document.forms[0];

	loadingMaskFixPos();
	form.action  = "<c:url value='/edi/consult/PEDMCST0005select.do'/>";
	form.submit();		
}

function doInsertPage(){
	var form = document.forms[0];

	loadingMaskFixPos();
	form.action  = "<c:url value='/edi/consult/forwardInsertPage.do'/>";
	form.submit();
}

function doUpdatePage(val,val2,val3){
	var form = document.forms[0];

	form.seq_no.value=val;
	form.up_bman.value=val2;
	form.up_anx.value=val3;

	loadingMaskFixPos();
	form.action  = "<c:url value='/edi/consult/forwardUpdatePage.do'/>";
	form.submit();
}
</script>
 
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post">
		
		<input type="hidden" name="bman" value="${bman }"/>
		<input type="hidden" name="seq_no" />
		<input type="hidden" name="up_bman" />
		<input type="hidden" name="up_anx" />
		
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">알리미 서비스</li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doSearch();"><span>조회</span></a>
							<!-- <a href="#" class="btn" onclick="javascript:doInsertPage();"><span>등록</span></a> -->
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:100px;" />
						<col />
						<col style="width:100px;" />
						<col />
					</colgroup>
					<tr>
						<th>사업자 번호</th>
						<td>	
							<select name="sele_bman">
								<option value="all">전체</option>
							</select>
						</td>
						<th>서비스 선택</th>
						<td>
							<select name="sele_service_main">
								<option value="all">전체</option>
								<option value="ALM" <c:if test="${paramMap.sele_service_main eq 'ALM' }">selected</c:if>>발주 및 요약정보</option>
								<option value="PAY" <c:if test="${paramMap.sele_service_main eq 'PAY' }">selected</c:if>>대금 및 계산서</option>
							</select>&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					</table>
					
				</div>
			</div>
			
			<div class="wrap_con">
					<!-- list -->
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">검색내역</li>
						</ul>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<colgroup>
							<col style="width:120px;" />
							<col style="width:120px;" />
							<col style="width:150px;" />
							<col />
							<col style="width:100px;" />
							<col style="width:17px;" />
						</colgroup>
						<tr>
							<th>업체명</th>
							<th>서비스</th>
							<th>핸드폰</th>
							<th>이메일</th>
							<th>수정</th>
							<th>&nbsp;</th>
						</tr>
						</table>
						<div style="width:100%; height:461px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; table-layout:fixed;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
						<colgroup>
							<col style="width:120px;" />
							<col style="width:120px;" />
							<col style="width:150px;" />
							<col />
							<col style="width:100px;" />
						</colgroup>
						<c:if test="${not empty alertList }">
							<c:forEach items="${alertList}" var="list" varStatus="index" >
								<tr class="r1">
									<td align="center">${business }</td>
									<td align="center">
										<c:choose>
											<c:when test="${list.ANX_INFO_CD eq 'ALM'}">
												발주 및 요약정보
											</c:when>
											<c:otherwise>
												대금 및 계산서
											</c:otherwise>
										</c:choose>
									</td>
									<td align="center">${list.TELNO_NM }</td>
									<td align="center">${list.EMAIL }</td>
									<td align="center">
										-
										<%-- <a href="javascript:doUpdatePage('${list.SVC_SEQ }','${list.BMAN_NO }','${list.ANX_INFO_CD }');" onClick="" class="btn"><span>수정</span></a> --%>
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty alertList }">
							<tr><td colspan="4" align=center>Data가 없습니다.</td></tr>
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
					<li class="last">알리미 서비스</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
