<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript">

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<form name="dataForm" id="dataForm" action="${ctx }/edi/product/maker.do" method="post">


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>패션상품 COLOR/SIZE 별 판매코드 조회</h1>
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

	<!------------------------------------------------------------------ -->
	<!-- 	검색조건 -->
	<!------------------------------------------------------------------ -->
	
	<div class="bbs_search">
		<ul class="tit">
			<li class="tit"></li>
			<li class="btn">
				<a href="#" class="btn" onclick="window.close();"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
		
	</div>
	
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
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
								<col style="width:35px;" />
								<col style="width:150px;" />
								<col style="width:200px;" />
								<col  />
							</colgroup>
							<tr>
								<th>No.</th>
								<th>판매(88)코드</th>
								<th>컬러명</th>
								<th>사이즈명</th>
							</tr>
						</table>
						<div style="width:100%; height:190px; overflow:auto;">
						<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef>
							<colgroup>
								<col style="width:35px;" />
								<col style="width:150px;" />
								<col style="width:200px;" />
								<col  />
							</colgroup>
							<c:forEach items="${colorSizeList}" var="list" varStatus="index" >
								<tr class="r1" bgcolor=ffffff>
									<td align="center">${index.count }</td>
									<td align="center">${list.sellCode}</td>
									<td >&nbsp; ${list.colorName }</td>
									<td >&nbsp; ${list.szName }</td>
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
	<!-----------------------------------------------end of 검색내역  -->
    </div><!-- class popup_contents// -->
    
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

</div><!-- id popup// -->
</form>

</body>
</html>