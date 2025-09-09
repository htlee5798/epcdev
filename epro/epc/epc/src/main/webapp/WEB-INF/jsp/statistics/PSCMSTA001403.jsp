<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=euc-kr"> -->
<meta http-equiv="Cache-Control" content="No-Cache">
<meta http-equiv="Pragma" content="No-Cache">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 9"> 
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<%
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMSTA001403.xls;") ;
	  response.setContentType("application/vnd.ms-excel;charset=UTF-8") ;
	  response.setHeader("Content-Transfer-Encoding", "binary;");
	  response.setHeader("Pragma", "no-cache;");
	  response.setHeader("Expires", "-1;");	  
%>  
</head>


<body>

<div id="content_wrap">

	<div class="content_scroll">
	
		<div id="wrap_menu">
		
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1">
					<colgroup>
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
					</colgroup>
					<tr>
						<th class="fst" bgcolor="cccccc" style="width: 50px;">일자</th>
						<th bgcolor="cccccc" style="width: 70px;">접수시간</th>
						<th bgcolor="cccccc" style="width: 70px;">주문번호</th>
						<th bgcolor="cccccc" style="width: 60px;">주문자</th>
						<th bgcolor="cccccc" style="width: 40px;">구분</th>
						<th bgcolor="cccccc" style="width: 70px;">상품명</th>
						<th bgcolor="cccccc" style="width: 40px;">수량</th>
						<th bgcolor="cccccc" style="width: 200px;">사유</th>
						<th bgcolor="cccccc" style="width: 70px;">접수자</th>
						<th bgcolor="cccccc" style="width: 90px;">고객센터 전달</th>
						<th bgcolor="cccccc" style="width: 70px;">처리여부</th>
					</tr>
					<tr class="r1">
						<td class="fst" style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
						<td style='text-align: center'> </td>
					</table>
				</div>
		
			</div>
			<!-- 2검색내역 // -->
		
		</div>

	</div>

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>