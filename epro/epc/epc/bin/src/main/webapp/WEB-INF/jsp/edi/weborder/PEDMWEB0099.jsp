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
		
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">알 림</li>
					</ul>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<tr style="height: 568px;">
						<td  style="text-align: center;">
							 <span style="font-weight: bold; font-size: 30px;">
							 	반품 등록 가능 시간은 <span style="color: red; font-family: cursive;"><spring:message code="msg.weborder.vendor.send.time.rtn"/></span> 입니다.</span>
						</td>
					</tr>
					</table>
				</div>
			</div>
					
		</div>
		</form>
	</div>


	
</div>

</body>
</html>
