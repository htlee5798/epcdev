<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<script>
	$(document).ready(function() {

		var msg = "<c:out value="${param.msg}" />";
        if( msg === '1' )alert("배달정보 이용하기 위해서 인증이 필요합니다.");
        else if( msg === '2' )alert("배달정보 인증 유효시간이 지났습니다. 다시 인증해주세요.");
	});
	
	function generateCode ()
	{
    // 이메일 생성 및 코드 생성
    var paramInfo = {};

    $.ajax({
      contentType : 'application/json; charset=utf-8',
      type : 'post',
      dataType : 'json',
      async : false,
      url : '<c:url value="/edi/delivery/auth0001.json"/>',
      data : paramInfo,
      success : function(data) {
        console.log(data);
        if (data.verifyingCode != null) {
             alert(data.verifyingCode);
             document.getElementById("hashValue").value = data.hashValue;
        }
        else {
             alert("fail");
        }
      }
    });
	 }

   function checkCode ()
   {
      // 이메일 생성 및 코드 생성
      var paramInfo = {};
      paramInfo["verifyingCode"] = document.getElementById("verifyingCode").value;
      paramInfo["hashValue"] = document.getElementById("hashValue").value;

      $.ajax({
        contentType : 'application/json; charset=utf-8',
        type : 'post',
        dataType : 'json',
        async : false,
        url : '<c:url value="/edi/delivery/auth0002.json"/>',
        data : JSON.stringify(paramInfo),
        success : function(data) {
          if (data.result == "success") {
               alert("checkSucess")
          }
          else {
               alert("fail");
          }
        }
      });
    }
</script>

</head>

<body>
    
    <input type="button" name="sendEmailBtn" onClick="generateCode()" value = "인증코드 생성"> 

    인증코드 입력창
    <input type="text" id="verifyingCode" name="verifyingCode">
    <input type="button" name="sendCodeBtn" onClick="checkCode()" value="인증코드 전송">

    <!-- hidden Form -->
    <form name="hiddenForm" id="hiddenForm">
        <input type="hidden" name="hashValue" id="hashValue" />
    </form>
</body>
</html>