<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>상온센터 납품 편수 변경 공지</title>


<script>

	function closeWin(cookiename)  {
	    setCookieToday(cookiename, 'popcookie20170125' , 1);        
	    top.close();
	}
	
	function setCookieToday( name, value, expiredays ){
	    var todayDate = new Date();
		todayDate.setDate(todayDate.getDate() + expiredays );

		//쿠키 expire 날짜 세팅 
		var expireDate =new Date(todayDate.getFullYear(),todayDate.getMonth(),todayDate.getDate());
	    document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + expireDate.toGMTString()+ ';' 
	}

</script>


</head>
<form name="AdminForm" method="post">

<!-- 480*230 -->
<body id="popup">
<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
	<tr>
		<td bgcolor=ffffff>
			<table cellspacing=0 cellpadding=0 border=0 width=100%>
				<TR>
				 	<img alt="" src="/images/epc/popup/20170125popUp.png" width="440" height="690">
				</td>			
			</TR>
			<TR>
			<td>
				<input type="checkbox" id="user_chk" name="user_chk" value="" onclick="closeWin('popcookie20170125');"> 이 메세지를 더이상 표시하지 않음.
			</td>
			</TR>
			</table>
			</td>
		</tr>
	</table>			
</form>
</body>			
			
