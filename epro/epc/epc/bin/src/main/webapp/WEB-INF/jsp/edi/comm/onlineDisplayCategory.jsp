<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript">
$(document).ready(function()
  {
    
	
      $('#search').click(function() {
      	doSearch();
      });
      $('#close').click(function() {
      	top.close();
      });
  });

function doSearch() {
	$("#dataForm").submit();
}
function selectOnlineDisplayCategory(categoryId, categoryName, displayFlag,fullCategoryNm) {
	opener.setCategoryInto(categoryId, categoryName, displayFlag, fullCategoryNm);

	
	if("<c:out value = '${param.closeFlag}'/>" == null){
		this.close();	
	}else{
		window.focus();
	}
}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<form name="dataForm" id="dataForm" action="${ctx }/edi/product/onlineDisplayCategory.do" method="post">
<input type="hidden" id="closeFlag"  name="closeFlag" value="<c:out escapeXml='false' value='${param.closeFlag }'/>"/>

<input type="hidden" id="catCd"  name="catCd"  value="<c:out escapeXml='false' value='${param.catCd}'/>"/>

<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>온라인 전시 카테고리 조회</h1>
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
	<div class="bbs_search2" style="width:100%;">
            <ul class="tit">
            <li class="tit">[카테고리]</li>
            <li class="btn">
                <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
            </li>
        </ul>
        <!------------------------------------------------------------------ -->
        <!--    table -->
        <!------------------------------------------------------------------ -->
        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
            <colgroup>
                <col width="15%">
                <col width="30%">
                <col width="55%">
            </colgroup>
            <!-- row 1 ------------------------------------->
            <c:if test="${param.closeFlag == null}">
            <tr>
                <th>대분류</th>
                <td>
                    <select name="categoryIdDepth2" id="categoryIdDepth2" style="width:85px;" class="select">
                        <option value="">---전체---</option>
<c:forEach items="${selectCategoryListDepth2}" var="cat" varStatus="status">
                        <option value="${cat.CATEGORY_ID}"
                        <c:if test="${cat.CATEGORY_ID == param.categoryIdDepth2 }">
                        	selected
                        </c:if>
                        >${cat.CATEGORY_NM}</option>
</c:forEach>
                    </select>
                </td>
                <td></td>
            </tr>
            </c:if>
        </table>
        <!---------------------------------------------------- end of table -- -->
    </div>
	
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">온라인 전시카테고리</li>
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
							<c:forEach items="${onlineDisplayCategoryList}" var="onlineDisplayCategory" varStatus="index" >
								<tr class="r1" >
									<td align="center">${index.count }</td>
									<td align="left" style="padding-left:5px">
										<c:if test="${ onlineDisplayCategory.displayFlag == 'Y' && 
												onlineDisplayCategory.depth == '4'}">
										<a href="javascript:selectOnlineDisplayCategory('${onlineDisplayCategory.categoryId}', '${onlineDisplayCategory.categoryNm}', '${onlineDisplayCategory.displayFlag}','${onlineDisplayCategory.fullCategoryNm}')">
											${onlineDisplayCategory.categoryId}
										</a>
										</c:if>
										<c:if test="${ onlineDisplayCategory.displayFlag != 'Y' || 
												onlineDisplayCategory.depth != '4'}">
											<!-- 기업회원 전용 전문관 3뎁스 카테고리 할당을 위한 분기 -->
											<c:choose>
												<c:when test="${fn:startsWith(onlineDisplayCategory.categoryId,'C2020042') }">
													<a href="javascript:selectOnlineDisplayCategory('${onlineDisplayCategory.categoryId}', '${onlineDisplayCategory.categoryNm}', '${onlineDisplayCategory.displayFlag}','${onlineDisplayCategory.fullCategoryNm}')"/>
													${onlineDisplayCategory.categoryId}
												</c:when>
												<c:otherwise>
													${onlineDisplayCategory.categoryId}
												</c:otherwise>
											</c:choose>
										</c:if>
									</td>
									<!-- 기업회원 전용 전문관 카테고리 명 구분 -->	
									<c:choose>
										<c:when test="${fn:startsWith(onlineDisplayCategory.categoryId , 'C2020042')}">
											<td align="left" style="padding-left:${onlineDisplayCategory.depth + 1}0px">&nbsp; ${onlineDisplayCategory.fullCategoryNm }</td>
										</c:when>
										<c:otherwise>
											<td align="left" style="padding-left:${onlineDisplayCategory.depth}0px">&nbsp; ${onlineDisplayCategory.categoryNm }</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
							<c:if test="${empty onlineDisplayCategoryList }">
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