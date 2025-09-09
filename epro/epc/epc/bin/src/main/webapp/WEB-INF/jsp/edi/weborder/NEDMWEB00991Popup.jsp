<%--
- Author(s): 
- Created Date: 2014. 08. 25
- Version : 1.0
- Description :HELP PAGEㅇ

--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>

		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">

		
		<div class="popup_contents" style="width: 354px;">
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit"><spring:message code='text.web.field.excelUploadInfo'/> </li>
				</ul>
				<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor=efefef>
				<tr>
					<td><span style="font-weight: bold;"><spring:message code='text.web.field.excelUploadInfo1'/></span></td>
				</tr>
				<tr>
					<td><span style="font-weight: bold;"><spring:message code='text.web.field.excelUploadInfo2'/></span></td>
				</tr>
				<tr>
					<td><span style="font-weight: bold;"><spring:message code='text.web.field.excelUploadInfo3'/></span></td>
				</tr>
				<tr>
					<td>
						<img src="/images/epc/popup/excel_sample.gif" alt="Notice" align="middle" style="width: 350px; height: 220px;"/>
					</td>
				</tr>
				</table>	 
			</div>
		</div>
		
		</form>
		
</body>
<font color='white'><b>PEDMWEB00991Popup.jsp</b></font>

</html>
