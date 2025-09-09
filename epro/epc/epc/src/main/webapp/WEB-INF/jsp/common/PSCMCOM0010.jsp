<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="/css/epc/edi/style_1024.css" />
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>

<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script language="JavaScript">
//     function forwardValue(val,valName){
   	 function forwardValue(val){
    	 opener.searchForm.productVal.value = val;
		 window.close();
     }


     function doSearch() {

 		var form = document.form1;

 		if(form.inputProduct.value==""){

 	 		alert("코드, 상품명을 입력하세요.");
 	 		return;
 		}

	    loadingMask(); 		
 		form.action  = "<c:url value='/common/PSCMCOM0010Search.do'/>";
 		form.submit();	
 	}
     
     
 	function doKeyCheck()
	{

		if( event.keyCode == 13 ) doSearch();
		
	}
     
</script>

</head>

<body onload="window.focus();">
			
<form id="form1" name="form1" method="post" action="#">


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>상품검색</h1>
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
				<a href="javascript:doSearch();"  class="btn"><span><spring:message code="button.common.inquire"/></span></a>
                <a href="javascript:window.close();" class="btn"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
		<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<col style="width:80px;" />
			<col style="width:100px;"/>
			<col  />
			<tr>
				<td>
					<input type="Radio" name="productVal" value="1"  checked />상품코드
					<input type="Radio" name="productVal" value="2" <c:if test="${ param.productVal eq '2' }"> Checked</c:if> />상품명
				</td>
				<th><span class="star">*</span>코드,상품명</th>
				<td><input type="text" name="inputProduct" value="<c:out value='${param.inputProduct }'/>" style="width:150px;" onKeyDown="doKeyCheck();" />
					<input type="text" name="__blank" style="height:1px; width:1px;">
				</td>
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
								<th>코드</th>
								<th>상품명</th>
							</tr>
						</table>
						<div style="width:100%; height:230px; overflow:auto;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
							<colgroup>
								<col style="width:35px;" />
								<col style="width:100px;" />
								<col  />
							</colgroup>	
							<c:if test="${not empty productList }">
								<c:set var="row_cnt"  value="1" />
								<c:forEach items="${productList}" var="list" varStatus="index" >
									<tr class="r1">
										<td align="center"><c:out value="${index.count }" /></td>
										<td align=center><a href="javascript:forwardValue('<c:out value="${list.PROD_CD }" />');"><c:out value="${list.PROD_CD }" /></a> </td>
										<td>&nbsp; <c:out value="${list.PROD_NM }" /> </td>
									</tr>
									<c:set var="row_cnt" value="${row_cnt+1 }" />
								</c:forEach>
								<c:forEach begin="${row_cnt}" end ="10">
					      			<tr class="r1"  bgcolor=ffffff>
						    			<td>&nbsp;</td>
						    			<td>&nbsp;</td>
						    			<td>&nbsp;</td>
						    		</tr>
						    	</c:forEach>		
							</c:if>
							<c:if test="${empty productList }">
								<tr class="r1"><td colspan="3" align="center">데이타가 없습니다.</td></tr>
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

	
</div><!-- id popup// -->
</form>	
</body>
</html>
