<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="/css/style.css" />
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
</head>
<script>
function goForm(gubn)
{
	var form = document.tForm;
	var url;
	if( gubn == 'A')
		url = '<c:url value="common/viewEpcAdmLoginForm.do"/>';
	else if( gubn == 'C' )
		url = '<c:url value="common/epcVendorLogin.do"/>';
	else if( gubn == 'S' )
		url = '<c:url value="/index.jsp"/>';
	else if( gubn == 'L' )
			url = 'http://grx.bizmeka.com/pine/html/test.jsp'
	else
		url = '<c:url value="/indexs.jsp"/>';

	form.action=url;
	form.submit();		
		
}

function goLcnForm()
{
	window.open	('http://grx.bizmeka.com/pine/html/test.jsp','','width=1024px,height=7680px'); 
}
</script>
<body> 
<form name="tForm" method="post"> 
<br/>
<br/>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;◆<a href="#" onClick="goForm('A');">관리자 계정 로그인화면</a>
<br/>
<br/>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;<br/>
&nbsp;&nbsp;&nbsp;&nbsp;0008000017;1078206859  1388103238<br/>
  &nbsp;&nbsp;&nbsp;&nbsp;◆
   <!--  <input type="text" name="conoArrStr" size="50" maxlength="100"/>&nbsp;<input type="button" value="협력사로그인" onClick="goForm('C');"/>
-->

<br/>
<br/>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;◆<input type="button" value="LCN 로그인" onClick="goForm('L');"/>
<br/>
<br/>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;◆<a href="#" onClick="goForm('S');">SCM Home</a> ==> 삭제예정
<br/>
<br/>

</form>
</body>
</html>