<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lottemart.epc.common.util.SecureUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String prodCd = SecureUtil.stripXSS((String) request.getParameter("prodCd"));
	String vendorId = SecureUtil.stripXSS((String) request.getParameter("vendorId"));
	String status = SecureUtil.stripXSS((String) request.getAttribute("status"));
	String ment = SecureUtil.stripXSS((String) request.getAttribute("ment"));
	String mode = SecureUtil.stripXSS((String) request.getAttribute("mode"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Cache-Control" content="No-Cache" />
<meta http-equiv="Pragma" content="No-Cache" />
<c:import url="/common/commonHeadPopup.do" />
<script language="javascript" type="text/javascript">

	$(document).ready(function() {
		//input enter 막기
		$("*").keypress(function(e) {
			if (e.keyCode == 13) {return false;}
		});
<%
    if("updateOne".equals(mode)) {
%>
        alert("<%=ment%>");
        top.opener.document.form2.submit();
        top.close();
<%
    } else {
%>
        alert("<%=ment%>");
        document.form2.submit();
<%
    }
%>
	});
</script>
</head>
<body>
<form name="form2" id="form2" action="<c:url value="/product/selectProductImageForm.do"/>" method="post" target="_parent">
    <input type="hidden" id="prodCd"   name="prodCd"   value="<%=prodCd%>"  />
    <input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>
</body>
</html>