<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
    alert('세션이 종료되었습니다. \n초기화면으로 이동합니다.');
    
    if(typeof(opener) != "undefined") {
		opener.parent.location.href="<c:url value='/main/intro.do'/>"
		self.close();
	}
	else
		parent.location.href="<c:url value='/main/intro.do'/>"


</script>