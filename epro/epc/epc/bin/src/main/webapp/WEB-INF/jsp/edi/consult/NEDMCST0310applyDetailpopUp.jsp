<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

</head>

<!-- 500*440 -->
<body id="popup">
<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
	<tr>
		<td bgcolor=ffffff>
			<table cellspacing=0 cellpadding=0 border=0 width=100%>
				<tr height=30>
					<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>옥션상품 공지</b></td>
					<td width=50><a href="#" class="btn" onclick="window.close();"><span><spring:message code="button.common.close"/></span></a></td>
				</tr>
				<tr><td height=2 bgcolor="f4383f" colspan=2></td></tr>
			</table>
			
			
				<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:50px;" />
						<col  />
					</colgroup>
					<tr>
						<th><b>No</b></th>
						<th><b>옥션일</b></th>
						<th><b>대분류명</b></th>
						<th><b>부서팀</b></th>
						<th><b>상품명</b></th>
						<th><b>시작가</b></th>
						<th><b>개수</b></th>
						<th><b>단위</b></th>
					</tr>
				</table>
				<div style="width:100%; height:500px; overflow:auto;">
				<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:50px;" />
						<col  />
					</colgroup>
					<c:forEach items="${codeList}" var="list" varStatus="index" >
						<tr class="r1">
							<td align=center><c:out value='${index.count}'/></td>
							<td align=center><c:out value='${list.AU_DY}'/></td>
							<td align=center><c:out value='${list.L1_NM}'/></td>
							<td align=center><c:out value='${list.TEAM_NM}'/></td>							
							<td align=center><c:out value='${list.PROD_NM}'/></td>
							<td align=right><c:out value='${list.START_PRC}'/></td>
							<td align=right><c:out value='${list.QTY}'/></td>
							<td align=center><c:out value='${list.UNIT}'/></td>
						</tr>
					</c:forEach>
				</table>	
				</div>
			</td>
		</tr>
	</table>			

</body>			
			
