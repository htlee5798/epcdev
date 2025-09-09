<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" 	uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" 		uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   	uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<jsp:useBean id="now" class="java.util.Date"/>

<c:set var="localeDatePattern"		value="yyyy-MM-dd"			scope="page"/>
<c:set var="localeMonthDatePattern"	value="yyyy-MM"				scope="page"/>
<c:set var="localeDateTimePattern"	value="yyyy-MM-dd HH:mm:ss" scope="page"/>
