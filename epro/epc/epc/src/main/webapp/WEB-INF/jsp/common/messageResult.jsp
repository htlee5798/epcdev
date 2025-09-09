<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = (String)request.getAttribute("msg");
	
	if (msg == null) msg = "";

	// Java에서 자바스크립트에 안전하게 쓸 수 있도록 이스케이프 처리
	msg = msg.replace("\\", "\\\\") // 백슬래시
		.replace("'", "\\'") 		// 작은따옴표
		.replace("\"", "\\\"") 		// 큰따옴표
		.replace("\r", "") 			// 캐리지 리턴
		.replace("\n", "\\n") 		// 줄바꿈
		.replace("</", "<\\/"); 	// </script> 방지
%>
<script type="text/javascript" >
var msg = '<%=msg%>';
alert(msg);
opener.doSearch();
window.close();
</script>