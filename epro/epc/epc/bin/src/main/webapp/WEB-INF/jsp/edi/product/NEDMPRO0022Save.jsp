<%--
	Page Name	: NEDMPRO0022Save.jsp
	Description : 온라인 이미지 저장 메시지창
	Modification Information

	수정일			수정자			수정내용
	----------		---------		-------------------------------------
	2019.07.08		한주형 			최초생성
--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>온라인 이미지 저장 메시지</title>
<script>

	/* dom이 생성되면 ready method 실행 */
	$(document).ready(function() {
		// 이미지 저장후 넘어오는 result 처리
		var result = "<c:out value='${imgMsg}'/>";

		if (result != null && result != "") {
			alert(result);
		}

		location.href = "<c:url value='/edi/product/NEDMPRO0032.do?pgmId=${pgmId}&mode=${mode}'/>";
	});
</script>
</head>
</html>