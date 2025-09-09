<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script language="JavaScript">
     
</script>

</head>

<body onload="window.focus();">
			
<form id="form1" name="form1" method="post" action="#">

<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>원전표</h1>
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <!------------------------------------------------------------------ -->
    <!--    process -->
    <!------------------------------------------------------------------ -->
    <!--    process 없음 -->
    <br>
    <!------------------------------------------------ end of process -- -->
	<div class="popup_contents">

	
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">전표내역</li>
			</ul>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" >
				<tr> 
	                <th rowspan="2">전표번호</th>
	                <th colspan="2">의뢰점포</th>
	                <th>판매코드</th>
	                <th>상품명</th>
	                <th>모델명</th>
	                <th>수량</th>
	                <th rowspan="3">접수</th>
	             </tr>
	             <tr>
	             	<th>판매일자</th>
	             	<th>등록일자</th>
	             	<th>의뢰고객</th>
	             	<th>의뢰주소</th>
	             	<th>의뢰전화1</th>
	             	<th>의뢰전화2</th>
	             </tr>
	             <tr>
	             	<th>진행상태</th>
	             	<th colspan="2">요청일자</th>
	             	<th>인수고객</th>
	             	<th>인수주소</th>
	             	<th>인수전화1</th>
	             	<th>인수전화2</th>
	             </tr>
	              <tr>
	              	<th colspan="8">비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고</th> 
	              </tr>
	              
	              
			</table>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" >
				<tr class="r1"> 
	                <td align="center" rowspan="2">${deliveryList.DELI_SLIP_NO }</td>
	                <td align="center" colspan="2">${deliveryList.STR_NM }</td>
	                <td align="center">${deliveryList.PROD_CD }</td>
	                <td align="center">${deliveryList.PROD_NM }</td>
	                <td align="center">${deliveryList.MODEL_NM }</td>
	                <td align="center">${deliveryList.DELI_SALE_QTY }</td>
	                
	                <c:choose>
	                	<c:when test="${deliveryList.ACCEPT_FG eq '0'}">
	                		<td align="center" rowspan="3">미접수</td>
	                	</c:when>
	                	<c:otherwise>
	                		<td align="center" rowspan="3">접수</td>
	                	</c:otherwise>
	                </c:choose>
	                
	             </tr>
	             
	             <tr class="r1">
	             	<td align="center">${deliveryList.SALE_DY }</td>
	             	<td align="center">${deliveryList.ACCEPT_DY }</td>
	             	<td align="center">${deliveryList.CUST_NM }</td>
	             	<td align="center">${deliveryList.CUST_ADDR }</td>
	             	<td align="center">${deliveryList.CUST_TEL_NO1 }</td>
	             	<td align="center">${deliveryList.CUST_TEL_NO2 }</td>
	             </tr> 
	             
	             <tr class="r1">
	             	<td align="center">${deliveryList.ACCEPT_STR }</td>
	             	<td align="center" colspan="2">${deliveryList.DELI_REQ_DY }</td>
	             	<td align="center">${deliveryList.RECV_NM }</td>
	             	<td align="center">${deliveryList.RECV_ADDR}</td>
	             	<td align="center">${deliveryList.RECV_TEL_NO1}</td>
	             	<td align="center">${deliveryList.RECV_TEL_NO2}</td>
	             </tr>
	              <tr class="r1">
	              	<td align="center" colspan="8">${deliveryList.MAP} &nbsp;</td> 
	              </tr>
			</table>
		</div>
	</div>
	<!-----------------------------------------------end of 검색내역  -->
    </div><!-- class popup_contents// -->
    
    <br/>

	
</div><!-- id popup// -->
</form>	
</body>
</html>
