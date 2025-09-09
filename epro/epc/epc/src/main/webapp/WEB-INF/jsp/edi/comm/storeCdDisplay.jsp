<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../common.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript">
$(document).ready(function(){
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

//점포코드 콜백
/* function selectStoreCdDisplay(storeCd,storeNm) {
	var rtnData = {};
	
	rtnData["trNum"] = $('input[name=trNum]').val();
	rtnData['strCd'] = storeCd;
	rtnData['strNm'] = storeNm;
	opener.setStoreCd(rtnData);
	top.close();
} */
function selectStoreCdDisplay(obj) {
	var rtnData = {};
	
	$(obj).closest("tr").find("input").each(function(){
		console.log(this.name);
		if(this.name != undefined && this.name != null && this.name != ""){
			rtnData[this.name] = $.trim($(this).val());
		}
	});
	
	rtnData["trNum"] = $('input[name=trNum]').val();
	opener.setStoreCd(rtnData);
	top.close();
}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<form name="dataForm" id="dataForm" action="<c:out value='${ctx}'/>/edi/product/selStoreCdList.do" method="post">
<input type="hidden" id="closeFlag"  name="closeFlag" value="<c:out value='${param.closeFlag}'/>"/>
<input type="hidden" id="pageIndex" name="pageIndex" value="1" />
<input type="hidden" id="trNum" name="trNum" value="<c:out value="${trNum}" />" />

<div id="storePopup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <br>
	<div class="popup_contents">

	<!------------------------------------------------------------------ -->
	<!-- 	검색조건 -->
	<!------------------------------------------------------------------ -->
	<div class="bbs_search2" style="width:100%;">
         <ul class="tit">
            <li class="tit">점포코드</li>
            <li class="btn">
                <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
            </li>
        </ul>
        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
        </table>
        <!------------------------------------------------------------------ -->
        <!--    table -->
        <!------------------------------------------------------------------ -->
        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
            <colgroup>
                <col width="15%">
                <col width="40%">
                <col width="15%">
                <col width="40%">
            </colgroup>
            <!-- row 1 ------------------------------------->
            <tr>
            	<th>점포코드</th>
            	<td>
            		<input type="text" name="srchStoreCd" id="srchStoreCd"  style="width:40%;" />
				</td>
                <th>점포명</th>
                <td>
                    <input type="text" name="srchStoreNm" id="srchStoreNm"  style="width:40%;" />
                </td>
            </tr>
            <tr>
            	<th>사업자번호</th>
                <td>
                	<input type="text" name="srchBmanNo" id="srchBmanNo"  style="width:40%;" />
                </td>
                <th>상호명</th>
                <td>
                    <input type="text" name="srchComNm" id="srchComNm"  style="width:40%;" />
                </td>
            </tr>
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
				<li class="tit">점포코드 조회</li>
			</ul>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width="100%" >
							<colgroup>
								<col style="width:35px;" />
								<col style="width:100px;" />
								<col style="width:200px;" />
								<col style="width:300px;" />
								<col style="width:140px;" />
							</colgroup>
							<tr>
								<th>No.</th>
								<th>점포코드</th>
								<th>점포명</th>
								<th>상호명</th>
								<th>사업자번호</th>
							</tr>
							<c:forEach items="${selectStrCdList}" var="storeCdDisplay" varStatus="index" >
								<tr class="r1" >
									<td align="center"><c:out value='${index.count }'/></td>
									<td align="center" style="padding-left:5px">
										<a href="javascript:void(0);" onclick="selectStoreCdDisplay(this);">
											<c:out value='${storeCdDisplay.strCd}'/>
										</a>
										<input type="hidden" name="strCd" value="<c:out value='${storeCdDisplay.strCd}'/>"/>
									</td>
									<td align="center">
										<c:out value="${storeCdDisplay.strNm}"></c:out>
										<input type="hidden" name="strNm" value="<c:out value='${storeCdDisplay.strNm}'/>"/>
									</td>
									<td align="center">
										<c:out value="${storeCdDisplay.comNm}"></c:out>
									</td>
									<td align="center">
										<c:out value="${storeCdDisplay.bmanNo}"></c:out>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty selectStrCdList }">
								<tr><td colspan="11" bgcolor=ffffff align=center>Data가 없습니다.</td></tr>
							</c:if>
						</table>
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
	<div class="paging">
	    <c:if test="${paginationInfo.currentPageNo > 1}">
	        <a href="javascript:goPage(1)">처음</a>
	        <a href="javascript:goPage(${paginationInfo.currentPageNo - 1})">이전</a>
	    </c:if>
	
	    <c:forEach var="page" begin="${paginationInfo.firstPageNoOnPageList}" end="${paginationInfo.lastPageNoOnPageList}">
	        <c:choose>
	            <c:when test="${page == paginationInfo.currentPageNo}">
	                <strong>${page}</strong>
	            </c:when>
	            <c:otherwise>
	                <a href="javascript:goPage(${page})">${page}</a>
	            </c:otherwise>
	        </c:choose>
	    </c:forEach>
	
	    <c:if test="${paginationInfo.currentPageNo < paginationInfo.totalPageCount}">
	        <a href="javascript:goPage(${paginationInfo.currentPageNo + 1})">다음</a>
	        <a href="javascript:goPage(<c:out value='${paginationInfo.totalPageCount}'/>)">마지막</a>
	    </c:if>
	</div>
	
	<script type="text/javascript">
	    function goPage(pageNo) {
	        document.getElementById('pageIndex').value = pageNo;
	        document.forms['dataForm'].submit();
	    }
	</script>
	<!---------------------------------------------end of footer -->

</div><!-- id popup// -->
</form>

</body>
</html>