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
function searchMaker() {
	if( 
		$("input[name=makerName]").val() != '' && 
		$("input[name=makerName]").val().length > 0 ) 
	   {
			$("form[name=dataForm]").submit();
	   }
}


function selectMaker(code, name) {
	$("#makerCode", opener.document).val(code);
	$("#makerName", opener.document).val(name);
	this.close();
	
}
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
        <h1>메이커 조회</h1>
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
			<li class="tit">검색조건</li>
			<li class="btn">
				<a href="javascript:searchMaker();"  class="btn"><span><spring:message code="button.common.inquire"/></span></a>
                <a href="javascript:window.close();" class="btn"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
		<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<col style="width:100px;" />
			<col  />
			<tr>
				<th><span class="star">*</span> 메이커명 </th>
				<td><input type="text" name="makerName" value="${param.makerName }" style="width:150px;" /></td>
			</tr>	
        </table> 
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
								<col style="width:100px;" />
								<col  />
							</colgroup>
							<tr>
								<th>No.</th>
								<th>메이커 코드</th>
								<th>메이커명</th>
							</tr>
						</table>
						<div style="width:100%; height:190px; overflow:auto;">
						<table  class="bbs_list2" cellpadding="0" cellspacing="0" border="0" width="100%" >	
							<colgroup>
								<col style="width:35px;" />
								<col style="width:100px;" />
								<col  />
							</colgroup>
							<c:forEach items="${makerList}" var="brand" varStatus="index" >
								<tr class="r1" >
									<td align="center">${index.count }</td>
									<td align="center">
										<a href="javascript:selectMaker('${brand.categoryCode}', '${brand.categoryName}')">
											${brand.categoryCode}
										</a>
									</td>
									<td >&nbsp; ${brand.categoryName }</td>
								</tr>
							</c:forEach>
							<c:if test="${empty makerList }">
								<tr><td colspan="11" bgcolor=ffffff align=center>Data가 없습니다.</td></tr>
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