<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>자동 로그아웃 안내</title>
<style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'SUIT', sans-serif;
      background-color: #f2f2f2;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .card {
      background-color: white;
      padding: 50px;
      border-radius: 16px;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
      text-align: center;
      max-width: 420px;
      width: 90%;
    }

    .icon {
      font-size: 48px;
      color: #f81802;
      margin-bottom: 20px;
    }

    h1 {
      font-size: 24px;
      color: #333;
      margin-bottom: 16px;
    }

    p {
      font-size: 16px;
      color: #666;
      line-height: 1.6;
    }
  </style>
  
<script>
window.onload = function(){
	if(opener){	//opener 있을 경우, opener로 화면 redirection 후 본인 page close
		opener.parent.location.href = location.href;
		self.close();
	}else if (parent.location.href !== location.href) {	//top page 아닌 경우, top page redircetion
		parent.location.href = location.href;
	}else{		//그 외 session out page show
		document.getElementById("_sessionOutBody").style.display = "";
	}
}
</script>
</head>
<body id="_sessionOutBody" style="display:none;">
  <div class="card">
    <div class="icon">🔒</div>
    <h1>세션이 만료되었습니다</h1>
    <p>일정 시간 동안 활동이 없어<br>자동으로 로그아웃되었습니다.<br>보안을 위해 다시 로그인해주세요.</p>
  </div>
</body>
</html>