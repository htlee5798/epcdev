<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<script type="text/javascript" src="<c:url value="/js/jquery/jquery.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/common/Ui_common.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/common/common.js"/>"></script>

<script language="JavaScript">
     function forwardValue(val){
    	 opener.searchForm.productVal.value = val;
		 window.close();
     }


     function doSearch() {

 		var form = document.form1;
 		
 		form.action  = "<c:url value='/edi/comm/PEDPCOM0002Search.do'/>";
 		form.submit();	
 	}
</script>

</head>

<body>
			
<form id="form1" name="form1" method="post" action="#">

	코드, 상품명 입력 : <input type="text" name="inputProduct" />
	<input type="button" value="검색" onclick="javascript:doSearch();"/>
	
	<table border="1">
		<tr>
			<td>코드</td>
			
			<td>상품명</td>
		</tr>
		
		
		<c:if test="${not empty productList }">
			<c:forEach items="${productList}" var="list" varStatus="index" >
				<tr>
					<td><a href="javascript:forwardValue('${list.PROD_CD }');">${list.PROD_CD }</a> </td>
					<td>${list.PROD_NM } </td>
				</tr>
			</c:forEach>
		</c:if>
		
		<c:if test="${empty productList }">
			<tr><td colspan="2">데이타가 없습니다.</td></tr>
		</c:if>
	</table>
</form>	
				
</body>
</html>
