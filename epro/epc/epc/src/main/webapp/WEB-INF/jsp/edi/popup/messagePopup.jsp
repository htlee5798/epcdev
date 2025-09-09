<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
/*
	strTitle = Request.QueryString("strTitle")
	strMsg = Replace(Request.QueryString("strMsg"),"//","<br>")	
	objFocus = Request.QueryString("objFocus")
	
	if (Len(objFocus) > 0) Then
		'focusWindow = "opener." & objFocus & ".focus();"
		focusWindow = objFocus & ".focus();"
	End if	
*/	
%>
<html>
	<head>
		<title></title>
		<%@ include file="/common/edi/meta.jsp" %>
		<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
		<!-- link href="include/style.css" rel="stylesheet" type="text/css" -->
		<link href="<c:url value='/css/epc/edi/consult/style.css'/>" type="text/css" rel="stylesheet">
		<script language="javascript">
		//var rgParam = new Array();
		//rgParam[0] = "exit";
		//rgParam[1] = 
		//window.returnValue = rgParam; 
		

		
		function fnBtnConfirm(fc)
		{
			var objOpener = dialogArguments;
			objOpener.setFocus(fc);
 
			
			//rgParam[0] = "리턴하는 값1";
            //rgParam[1] = "리턴하는 값2";
            //window.returnValue = rgParam;

			//var data = dialogArguments;				
			//data.result = fc;
			//alert(fc);
			window.close();
		}
		</script>

	</head>
<form name="MyForm" action="#" method="post"  enctype="multipart/form-data" ID="Form1">
	<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" onLoad="">
		
		<table width="280" border="0" cellspacing="0" cellpadding="0" ID="Table1">
			<tr>
				<td background="/images/epc/edi/consult/popup_message_top.gif" width="280" height="73"></td>
			</tr>
			<tr>
				<td background="/images/epc/edi/consult/popup_message_bg.gif">
					<table width="232" height="70" border="0" cellspacing="0" cellpadding="0" align="center" ID="Table2">
						<tr>
							<td height="28" align="center">
								<b><font color="#6D92CC">${strMsg}</font></b>
							</td>
						</tr>
						<tr>
							<td align="center">
								<a href='#' onclick='fnBtnConfirm("${objFocus}")'><img src="/images/epc/edi/consult/confirm_b.gif" width="60" height="22" border=0></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td background="/images/epc/edi/consult/popup_message_bottom.gif" width="280" height="28"></td>
			</tr>
		</table>
		<input type="hidden" name="parent_focus">
		
	</body>
	</form>
</html>
