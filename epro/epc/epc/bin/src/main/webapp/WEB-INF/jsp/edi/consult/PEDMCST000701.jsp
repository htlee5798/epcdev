<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
</script>
 
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post">
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">Loan</li>
						<li class="btn">
							<a href='<c:url value="/edi/consult/PEDMCST0007.do"/>' class="btn"><span><spring:message code="button.consult.back"/></span></a>
						</li>
					</ul>
				</div>
			</div>
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
							<li class="tit">협력업체 자금지원제도 운영</li>
					</ul>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/collaboration_01.gif" ></h1>
							</div><br>
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/jagumjiwon_1.bmp" ></h1>
							</div><br>
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/jagumjiwon_1_1.bmp" ></h1>
							</div><br>
						</td>
					</tr>
					</table>
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
					<li class="last">Loan</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
