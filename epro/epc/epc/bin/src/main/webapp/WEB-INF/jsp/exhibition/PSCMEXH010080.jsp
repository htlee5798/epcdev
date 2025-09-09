<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String imgUrl = request.getAttribute("imgPath").toString();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
function fnResize()
{
  self.resizeTo(img.width + 30, img.height + 60)
}
</script>
<body topMargin="0" leftMargin="0" onLoad="fnResize();">

<!-- 이미지   미리보기 -->
<img id="img" src="${epc_image_path}<%=imgUrl %>" alt="이미지 미리보기" />

</body>
</html>