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
function searchBrand() {
	if( 
		$("input[name=brandName]").val() != '' && 
		$("input[name=brandName]").val().length > 0 ) 
	   {
			$("form[name=dataForm]").submit();
	   }
}

function selectBrand(code, name) {
	$("#brandCode", opener.document).val(code);
	$("#brandName", opener.document).val(name);
	
	var json = {"result":"Y", "brandCode": code, "brandName":name};
	if(opener._fnCallbackBrand) opener._fnCallbackBrand(json);
	
	this.close();
}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<form name="dataForm" id="dataForm" action="<c:out value='${ctx}'/>/edi/product/brand.do" method="post">

<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>브랜드 조회</h1>
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
				<a href="javascript:searchBrand();"  class="btn"><span><spring:message code="button.common.inquire"/></span></a>
                <a href="javascript:window.close();" class="btn"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
		<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<col style="width:100px;" />
			<col  />
			<tr>
				<th><span class="star">*</span> 브랜드명 </th>
				<td><input type="text" name="brandName" value="<c:out value='${param.brandName }'/>" style="width:150px;" /></td>
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
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
							<colgroup>
								<col style="width:35px;" />
								<col style="width:100px;" />
								<col  />
							</colgroup>
							<tr>
								<th>No.</th>
								<th>브랜드 코드</th>
								<th>브랜드명</th>
							</tr>
						</table>
						<div style="width:100%; height:190px; overflow:auto;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:35px;" />
								<col style="width:100px;" />
								<col  />
							</colgroup>	
							<c:forEach items="${brandList}" var="brand" varStatus="index" >
								<tr class="r1" >
									<td align="center"><c:out value='${index.count }'/></td>
									<td  align="center">
										<a href="javascript:selectBrand('<c:out value="${brand.categoryCode}"/>',  '<c:out value="${brand.categoryName}"/>')">
											<c:out value="${brand.categoryCode}"/>
										</a>
									</td>
									<td aling=left>&nbsp; <c:out value=" ${brand.categoryName }"/></td>
								</tr>
							</c:forEach>
							<c:if test="${empty brandList }">
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