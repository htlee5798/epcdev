<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>접근 권한 없음</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'SUIT', sans-serif;
      background: linear-gradient(135deg, #eef2ff, #dfe7ff);
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .error-card {
      background-color: white;
      padding: 50px;
      border-radius: 16px;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
      text-align: center;
      max-width: 420px;
      width: 90%;
    }

    .code {
      font-size: 64px;
      font-weight: bold;
      color: #6c63ff;
      margin-bottom: 10px;
    }

    .message {
      font-size: 20px;
      color: #333;
      margin-bottom: 16px;
    }

    .description {
      font-size: 16px;
      color: #666;
      line-height: 1.6;
    }

    .btn {
      display: inline-block;
      margin-top: 30px;
      padding: 12px 28px;
      background-color: #6c63ff;
      color: white;
      border: none;
      border-radius: 8px;
      text-decoration: none;
      font-weight: 600;
      transition: background-color 0.3s ease;
    }

    .btn:hover {
      background-color: #5548d9;
    }
  </style>
  
  <script>
  	//뒤로가기
  	function fncMoveToBack(){
  		history.back(-2);
  	}
  </script>
</head>
<body>
  <div class="error-card">
    <div class="code">403</div>
    <div class="message">접근 권한 없음</div>
    <div class="description">
      요청하신 페이지에 대한 접근 권한이 없습니다.
    </div>
    <a href="javascript:void(0);" onclick="fncMoveToBack()" class="btn">뒤로가기</a>
  </div>
</body>
</html>