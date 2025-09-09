<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>LOTTE MART Back Office System</title>
<!-- CSS URL -->
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/epc/main.css" />
<style type="text/css">
body {
    font: 15px malgun, sans-serif;
    color: #666;
    letter-spacing: 0px;
    line-height: 120%;
}
h2 {
    font: 17px malgun, sans-serif;
}
</style>
</head>
<body>
<div style="margin-top:20px; margin-left:20px;margin-right:20px; margin-bottom:20px;">
	<h2><strong>■ 개인정보 수집·이용 동의</strong></h2><br />
	롯데쇼핑㈜ 롯데마트사업부(이하'회사'라 함)는 「개인정보 보호법」등 관련 법규에 따라 파트너사의 파트너 시스템(EPC) 접속 서비스 제공을 위해 아래와 같이 개인별 정보를 수집∙이용하고자 합니다. 아래의 내용을 충분히 숙지하시고, 동의 여부를 결정하여 주시기 바랍니다.<br /><br />
	<strong>▶ <font style="color:blue;">[필수]</font> 개인정보 수집 및 이용에 대한 동의</strong><br /><br />
	<table class="nm_bbs_list" cellspacing="0" border="0">
		<colgroup>
			<col width="34%">
			<col width="33%">
			<col width="33%">
		</colgroup>
		<tbody><tr>
			<th><span class="star"></span>수집항목</th>
			<th><span class="star"></span>수집 및 이용 목적</th>
			<th><span class="star"></span>보유 및 이용기간</th>
		</tr>
		<tr>
			<td>성명, 휴대전화번호, 이메일</td>
			<td>파트너 시스템(EPC)<br>2차 인증 진행</td>
			<td><strong style="color:red;">파트너사 계약 만료일까지</strong></td>
		</tr>
		</tbody>
	</table>
	<br>
	※ 상기 목적에 필요한 개인정보 수집∙이용에 동의하지 않을 수 있습니다.<br />
	&nbsp;&nbsp;&nbsp;그러나 <font style="color:blue;">동의를 거부할 경우 파트너 시스템(EPC) 접속이 불가능한 불이익을 받을 수 있습니다.</font><br>
</div>
</body>
</html>
