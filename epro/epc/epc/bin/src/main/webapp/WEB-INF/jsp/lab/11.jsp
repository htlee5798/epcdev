<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.math.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.sql.Clob"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="java.math.BigDecimal" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<title>EPC Was 체크</title>

	<script language="javascript">
		$(document).ready(function() {
			/* alert(typeof(Storage)); */
			if (typeof(Storage) != "undefined") {
			    // Store
			    localStorage.setItem("key1", "LocalStorage OK!");
			    // Retrieve
			    document.getElementById("result").innerHTML = localStorage.getItem("key1");
			} else {
			    document.getElementById("result").innerHTML = "Sorry, Not support Web Storage...";
			}
		});
		
		function setCookie(was, name, value) {
			alert(was+'번 서버로 접속됩니다. ');
			document.cookie = name + "=" + escape(value) + "; path=/; domain=.partner.lottemart.com";
			location.reload();
		}

	</script>
</head>

<body>
	<br><br><br>
	<br><br><br>
	<a href="http://partner.lottemart.com/epc/main/vendorIntro.do">*** 홈으로 이동하기 ***</a>
	<br>
	<div id="result"></div>

	
	<br><br><br>
	<a href="javascript:setCookie('1', 'JSESSECID', '1111111111111111111111111111111111111111111111111111111111111111.lmsscap01_servlet_engine7')">@@@1번기@@@</a>
	<br><br><br>
	<a href="javascript:setCookie('2', 'JSESSECID', '1111111111111111111111111111111111111111111111111111111111111111.lmsscap02_servlet_engine7)">@@@2번기@@@</a>
	<br><br><br>

	
</body>
</html>