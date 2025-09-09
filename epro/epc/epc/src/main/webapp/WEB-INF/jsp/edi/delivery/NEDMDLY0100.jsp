<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<script>
	$(document).ready(function() {

		var divEmailList = document.getElementById("div_email_list");
		divEmailList.style.display = 'none';

		var msg = "<c:out value="${param.msg}" />";
        if( msg === '001' )alert("배달정보 이용하기 위해서 인증이 필요합니다.");
        else if( msg === '002' )alert("배달정보 인증 유효시간이 지났습니다. 다시 인증해주세요.");
	});


	function sendAuthInfoToMail () {

		var checkedYn = $('input:radio[name="emailChkBtn"]').is(':checked');
		if( !checkedYn ) {
			alert("보내실 정보를 선택하셔야합니다.");
			return ; 
		}
		var paramInfo = {};
		var checkedIdx = $('input[name="emailChkBtn"]:checked').val();
		var nameSelected = $('#name_'+checkedIdx).val();
		var emailSelected = $('#email_'+checkedIdx).val();

		paramInfo["name"] = nameSelected;
		paramInfo["email"] = emailSelected;
		
		$.ajax({
		      contentType : 'application/json; charset=utf-8',
		      type : 'post',
		      dataType : 'json',
		      async : false,
		      url : '<c:url value="/edi/delivery/auth0003.json"/>',
		      data : JSON.stringify(paramInfo),
		      success : function(data) {
		        if (data.verifyingCode != null) {
		             alert(data.verifyingCode);
		             document.getElementById("hashValue").value = data.hashValue;

		             alert("이메일 전송이 완료됐습니다.");
		        }
		        else {
		             alert("fail");
		        }
		      }
		    });
	}

    function showEmailList () {

    	var paramInfo = {};

    	$.ajax({
  	      contentType : 'application/json; charset=utf-8',
  	      type : 'post',
  	      dataType : 'json',
  	      async : false,
  	      url : '<c:url value="/edi/delivery/selectAuthEmailInfo.json"/>',
  	      data : paramInfo,
  	      success : function(data) {
  	        if (data.emailInfos != null) {
  	             console.log(data.emailInfos);
								 var divEmailList = document.getElementById("div_email_list");
						 		divEmailList.style.display = 'block';
								 var emailListHtml = "";
								 for (var idx = 0; idx < data.emailInfos.length; idx++) {
								     emailListHtml =  emailListHtml +
										 '<tr><td><input type="radio" name="emailChkBtn" value = "'+idx+'"></td>'+
										 '<td><input type="hidden" id="name_'+idx+'" value="'+data.emailInfos[idx].name+'">'+data.emailInfos[idx].name+'</td>'+
										 '<td><input type="hidden" id="email_'+idx+'" value="'+data.emailInfos[idx].email+'">'+data.emailInfos[idx].email+'</td></tr>';
								 }

								 $("#emailList").append(emailListHtml);
							
  	        }
  	        else {
  	             alert("fail");
  	        }
  	      }
  	    });
    }

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
               alert("인증코드 일치합니다.")
          }
          else {
               alert("인증코드 불일치합니다.");
          }
        }
      });
    }
</script>

  <br/> <br/>
  <p style="color:blue;">
    <font size="6em">
    배달정보 인증
    </font>
  </p>
  <br/><br/>

</head>

<body>

    <input type="button" name="sendEmailBtn" onClick="generateCode()" value = "인증코드 생성">

    인증코드 입력창
    <input type="text" id="verifyingCode" name="verifyingCode">
    <input type="button" name="sendCodeBtn" onClick="checkCode()" value="인증코드 확인">

    <br/><br/>

    <input type="button" name="showEmailList" onClick="showEmailList()" value="이메일 조회">
    <input type="button" name="sendCodeBtnForEmail" onClick="sendAuthInfoToMail()" value="이메일을 통한 인증코드 전송">

		<br/><br/>

		<div id="div_email_list">
			<table width="240" cellpadding="0" cellspacing="0" border="0" id="testTable1">
			<colgroup>
				<col style="width:40px;" />
				<col style="width:100px;" />
				<col style="width:100px;" />
			</colgroup>
			<tr>
				<th>체크</th>
				<th>이름</th>
				<th>이메일</th>
			</tr>
			<tbody id = "emailList"/>
			</table>
		</div>

    <!-- hidden Form -->
    <form name="hiddenForm" id="hiddenForm">
        <input type="hidden" name="hashValue" id="hashValue" />
    </form>
</body>
</html>
