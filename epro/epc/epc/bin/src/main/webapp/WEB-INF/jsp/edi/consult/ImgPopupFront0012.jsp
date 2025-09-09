<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>파트너</title>


<script>

	function closeWin(cookiename)  {
	    setCookieToday(cookiename, 'popcookie20151203' , 1);        
	    top.close();
	}
	
	function setCookieToday( name, value, expiredays ){
	    var todayDate = new Date();
		todayDate.setDate(todayDate.getDate() + expiredays );

		//쿠키 expire 날짜 세팅 
		var expireDate =new Date(todayDate.getFullYear(),todayDate.getMonth(),todayDate.getDate());
	    document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + expireDate.toGMTString()+ ';' 
	}
	
	function linkPopup(){
		NewWin = window.open("http://bitly.com/lottemart_1602");
		NewWin.focus();
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
				 	<a href="javascript:linkPopup();" ><img alt="" src="/images/epc/popup/20160222popUp.PNG" usemap="#map_popUp_image" ></a>
				</td>			
				<!-- <map name="map_popUp_image" id="map_popUp_image">
					<area shape="rect" coords="10,105,75,130" href="javascript:linkPopup();" alt="" />
				</map> -->
			</TR>
			<TR>
			<td>
				<input type="checkbox" id="user_chk" name="user_chk" value="" onclick="closeWin('popcookie20151203');"> 이 메세지를 더이상 표시하지 않음.
			</td>
			</TR>
			</table>
			</td>
		</tr>
	</table>			
</form>
</body>			
			
