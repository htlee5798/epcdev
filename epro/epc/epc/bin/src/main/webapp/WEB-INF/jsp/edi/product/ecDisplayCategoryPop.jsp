<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do"/>


<script language="javascript">
$(document).ready(function()
{
	$('#close').click(function() {
		top.close();
	});
});

function selectEcDisplayCategory(dispCatCd, dispCatNm, dispYn) {
	var isMart = $('#isMart').val();
	opener.setEcDisplayCategory(dispCatCd, dispCatNm, dispYn, isMart);
}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<input type="hidden" id="isMart"  name="isMart" value="<c:out escapeXml='false' value='${isMart}'/>"/>


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>EC 롯데ON 전시카테고리 조회</h1>
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

	<div class="bbs_search2" style="width:100%;">
		<ul class="tit">
            <li class="tit">[카테고리]</li>
            <li class="btn">
                <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
            </li>
        </ul>
    </div>
	
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">EC 롯데ON 전시카테고리</li>
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
								<th>카테고리 코드</th>
								<th>카테고리 명</th>
							</tr>
						</table>
						<div style="width:100%; height:190px; overflow:auto;">
						<table  class="bbs_list2" cellpadding="0" cellspacing="0" border="0" width="100%" >	
							<colgroup>
								<col style="width:35px;" />
								<col style="width:100px;" />
								<col  />
							</colgroup>
							<c:forEach items="${ecDisplayCategoryList}" var="ecDisplayCategoryList" varStatus="index" >
								<tr class="r1" >
									<td align="center">${index.count }</td>
									<td align="center">
										<a href="javascript:selectEcDisplayCategory('${ecDisplayCategoryList.DISP_CAT_CD}', '${ecDisplayCategoryList.DISP_CAT_NM}', '${ecDisplayCategoryList.DISP_YN}')">
											${ecDisplayCategoryList.DISP_CAT_CD}
										</a>
									</td>
									<td align="left" style="padding-left:5px">
											${ecDisplayCategoryList.DISP_CAT_NM}
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty ecDisplayCategoryList }">
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