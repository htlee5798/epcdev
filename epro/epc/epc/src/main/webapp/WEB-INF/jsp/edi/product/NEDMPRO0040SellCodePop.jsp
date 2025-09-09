<%--
	Page Name 	: NEDMPRO0041Pop.jsp
	Description : 임시보관함에서 판매코드조회 화면 팝업 JSP
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.12.01		SONG MIN KYO 	최초생성
--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	
	<script>	
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {
			
		});	
	</script>

</head>

<body>
	<div id="popup">
   
	    <div id="p_title1">
	        <h1>판매코드조회</h1>
	        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	    </div>
    
    	<br>
   
		<div class="popup_contents">

	
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit"></li>
				<li class="btn">
					<a href="#" class="btn" onclick="window.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>
			
		</div>

		<div class="wrap_con">
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">검색내역</li>
				</ul>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td>
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width="100%" >
								<colgroup>									
									<col style="width:150px;" />
									<col  />
								</colgroup>
								<tr>
									<th>판매(88)코드</th>
									<th>속성</th>
								</tr>
							</table>
							<div style="width:100%; height:190px; overflow:auto;">
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef>
								<colgroup>
									<col style="width:150px;" />									
									<col  />
								</colgroup>
								<c:forEach items="${colorSizeList}" var="list" varStatus="index" >
									<tr class="r1" bgcolor=ffffff>
										<td align="center"><c:out value='${list.sellCd}'/></td>
										<td >&nbsp; <c:out value='${list.attNm }'/></td>
									</tr>
								</c:forEach>
								<c:if test="${empty colorSizeList }">
									<tr><td colspan="4" bgcolor=ffffff align=center>Data가 없습니다.</td></tr>
								</c:if>
							</table>
							</div>	
						</td>
					</tr>
				</table>
			</div>
		</div>	
    </div>
    
    <br/>

	<!-- -------------------------------------------------------- -->
	<!--    footer  -->
	<!-- -------------------------------------------------------- -->
	<div id="footer">
	    <div id="footbox">
	        <div class="msg" id="resultMsg"></div>
	    </div>
	</div>
	<!---------------------------------------------end of footer -->

</div>

</body>
</html>
