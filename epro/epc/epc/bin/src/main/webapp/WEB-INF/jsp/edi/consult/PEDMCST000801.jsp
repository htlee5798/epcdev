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
						<li class="tit">협력회사 선정 및 운용</li>
					</ul>
				</div>
			</div>
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"></li>
					</ul>
					
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<a href='<c:url value="/edi/consult/PEDMCST0008.do"/>'><img src="/images/epc/edi/consult/vendor_1.gif" ></a>
								<a href='<c:url value="/edi/consult/PEDMCST000802.do"/>'><img src="/images/epc/edi/consult/salepromotion_2.gif" ></a>
								<a href='<c:url value="/edi/consult/PEDMCST000803.do"/>'><img src="/images/epc/edi/consult/location_02.gif" ></a>
							</div>
						</td>
					</tr>
					<tr class="r1">
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/vendor.jpg" ></h1>
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
					<li class="last">거래절차</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
