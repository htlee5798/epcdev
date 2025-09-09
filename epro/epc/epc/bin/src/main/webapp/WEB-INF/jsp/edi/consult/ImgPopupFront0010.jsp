<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>장년인턴제</title>


<script>

	function closeWin(cookiename)  {
	    setCookieToday(cookiename, 'popcookie201309290' , 1);        
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

<!-- 500*440 -->
<body id="popup">

	<map name ="aa">
		<area shape="rect" coords="0, 0, 460, 540" href="http://www.smjob.or.kr/bbsplus/view.asp?mnuflag=1&code=tbl_bbs_01319322&bd_gubn=1&no=18&page=1&sub_id=sub0401" target="_blank">
	</map>
	
	
	
	
<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
	<tr>
		<td bgcolor=ffffff>
			<table cellspacing=0 cellpadding=0 border=0 width=100%>
				<TR>
				<img src="/images/epc/popup/201309290popUp.jpg" usemap="#aa"  border="0" >				
				</td>			
			</TR>
			<TR>
			<td>
				<input type="checkbox" id="user_chk"  value="" onclick="closeWin('popcookie201309290');"> 이 메세지를 더이상 표시하지 않음.
			</td>
			</TR>
			</table>
			</td>
		</tr>
	</table>			
</form>
</body>			
			
