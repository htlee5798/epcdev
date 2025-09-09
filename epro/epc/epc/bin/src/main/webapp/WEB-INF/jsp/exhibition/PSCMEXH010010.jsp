<%--
- Author(s): jdj
- Created Date:
- Version : 1.0
- Description : 기획전관리 - 기획전 승인반려사유 팝업

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 버튼권한처리 태그 라이브러리 삭제 --%>
<%@page import="java.util.Map"%>
<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lcnjf.util.StringUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">

$(document).ready(function() {
	
	// 저장, 수정 후 결과 메세지 
	var saveMsg = "${saveMsg}";
	if(saveMsg != "") {
		alert(saveMsg);
		
		var code = "${saveCode}";
		if(code == "1") {	// 저장 성공 
			opener.doSearch();
			window.close();
		} 
	}
	
	
	var templateId = "${resultMap.TEMPLATE_ID}"; 
	templateId = templateId.replace(/(^\s*)|(\s*$)/gi, "");
	
	// 수정일 경우 
	if(templateId != "") {
		$("#applyCategoryTypeCd").val("${resultMap.APPLY_CATEGORY_TYPE_CD}");
		$("#dispTemplateTypeCd").val("${resultMap.DISP_TEMPLATE_TYPE_CD}");
		$("#dispTemplateTypeCd option").not(":selected").attr("disabled", "disabled");
		$("#cornDispToCd").val("${resultMap.CORN_DISP_TO_CD}");
		
		if("${resultMap.DISP_TEMPLATE_TYPE_CD}" == "07") {
			// 코너일 경우 코너개수 막기 
			$("#cornCnt").val("사용불가");
			$("#cornCnt").attr("disabled", true);
			
			$("#cornerDisTarget").show();
		} else {
			
			// 코너가 아닐땐 코너전시유형 접기
			 $("#cornerDisTarget").hide();
		}
	} else {
		$('input:radio[name="useYn"]:input[value="Y"]').attr("checked", true);
	}
	
	// 코너개수 숫자만 입력받기
	$('#cornCnt').keypress(function(event){
		if (event.which && (event.which  > 47 && event.which  < 58 || event.which == 8)) {
		} else {
			event.preventDefault();
		}
	});
	
	$("#cornerDisTarget").hide();
	
});

// 전시유형 코너일때, 아닐때 
function selectTemplateType(select) {
	 var divCorn = select.value;
	 
	 if(divCorn == '07') {
		// 코너일 경우 코너개수 막기 
		$("#cornCnt").val("사용불가");
		$("#cornCnt").attr("disabled", true);
		
		$("#cornerDisTarget").show();
	 } else {
		 $("#cornCnt").val("");
		 $("#cornCnt").attr("disabled", false);
		
		 $("#cornerDisTarget").hide();
	 }
	 $("#divCorn").val(divCorn);
}

// 반려사유 update
function fn_update() {
	//var pageDiv = $("#pageDiv").val();
	var msg = "";
	
	if(!fn_validation()) return;
	
	/* if(pageDiv == "insert") {
		msg = '<spring:message code="msg.common.confirm.regist" />';
	}else if(pageDiv == "update") {
		msg = '<spring:message code="msg.common.confirm.update" />'
	} */
	msg = '반려하시겠습니까?'; 
	
 	if(confirm(msg)) {
 		//$('#form1').attr({action:'<c:url value="/newTemplate/updateTemplateInfo.do"/>',target:''}).submit();
 		//기획전 체크건, 반려사유건 전송
 		//$('#form1').attr({action:'<c:url value="/exhibition/updateApply.do?gbn=2"/>',target:''}).submit();
 		
 		//부모창의 함수호출(반려사유를 부모창함수로 넘겨서 반려처리한다)
 		var retnReason = $('#retnReason').val();
 		if(window.opener.popRetnProcess(retnReason)){
 			self.close();
 		}
 		
	}
}

// 유효성 검사 
function fn_validation() {
	
	var f = document.form1;
	
	if(f.retnReason.value == "" || f.retnReason.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="반려사유"/>');
		f.retnReason.focus();
		return;
	}
	
/* 	if(f.templateTitleNm.value == "" || f.templateTitleNm.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="템플릿명"/>');
		f.templateTitleNm.focus();
		return;
	}
	if(getByte(f.templateTitleNm.value) > 200 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="템플릿명,200"/>');
		return;
	}
	if(f.templatePath.value == "" || f.templatePath.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="템플릿 패스"/>');
		f.templatePath.focus();
		return;
	}
	if(getByte(f.templatePath.value) > 200 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="템플릿 패스,200"/>');
		return;
	}
	if(f.dispTemplateTypeCd.value == "" || f.dispTemplateTypeCd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="전시템플릿유형"/>');
		f.dispTemplateTypeCd.focus();
		return;
	}
	if($("input[name=useYn]:radio:checked").length == 0){
		alert('<spring:message code="msg.common.error.required" arguments="사용여부"/>');
		return;
	}
	if($("#divCorn").val() != "07") {
		if(f.cornCnt.value == "" || f.cornCnt.value == "null") {
			alert('<spring:message code="msg.common.error.required" arguments="코너개수"/>');
			f.cornCnt.focus();
			return;
		}
	} else {
		if(f.cornDispToCd.value == "" || f.cornDispToCd.value == "null") {
			alert('<spring:message code="msg.common.error.required" arguments="코너전시대상"/>');
			f.cornDispToCd.focus();
			return;
		}	
	}
	
	
	var fileName = $("#tmpFile").val();
	fileName = filename.slice(filename.indexOf(".") + 1).toLowerCase();
	if(fileName != "jpg" && fileName != "png" &&  fileName != "gif" &&  fileName != "bmp"){
		alert("이미지 파일은 (jpg, png, gif, bmp) 형식만 등록 가능합니다.");
		return;
	} */
	
	return true;
}

</script>


</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="form1" id="form1" method="post"  enctype="multipart/form-data">
<input type="hidden" id="divCorn" name="divCorn" >
<input type="hidden" id="templateId" name="templateId" value="<c:out value="${resultMap.TEMPLATE_ID }"/>" >
<input type="hidden" id="gudnImg" name="gudnImg" value="<c:out value="${resultMap.GUDN_IMG }"/>" >
<input type="hidden" id="pageDiv" name="pageDiv"  value="<c:out value="${pageDiv }"/>"">

	<div class="pop_box_01">
	  <h2>기획전 승인반려사유 등록<span class="pop_close"><a href="javascript:window.close();"><img src="${_image_path}/bos/new/pop_close.png" alt="close" /></a></span></h2>
	  <div class="p_navi">
	    <ul>
	      <li><a href="#"><img src="${_image_path}/bos/new/icon_home.png" alt="home" /></a> > 기획전관리 > 기획전 승인반려사유 등록</li>
	    </ul>
	  </div>
	  
	  <ul>
	  <h3>기획전 승인반려사유 등록
	  		<span class="p_btn"><span class="btnBG1">
				<%-- <authutl:btnAuth buttonAuth="00001" admAuth="${ buttonAuth.adminMenuAuth}" buttonTagStart="<span class='btnBG1'><a href='javascript:fn_update();' >"  buttonMsg="button.common.update"  buttonTagEnd="</a></span>" applyYn="1"/> --%>
				<a href="javascript:fn_update();">저장</a>
	  		</span>  
	  		<span class="btnBG1"><a href="javascript:window.close();">닫기</a></span>
	  		</span>
	  </h3>
	    <li>
	      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="전시템플릿" >
	        <%-- <col width="20%" />
	        <col width="30%" />
	        <col width="20%" />
	        <col width=" " /> --%>	        
	        <col width="80px" />
	        <col width=" " />	        	      
	        <tr>
	        	<th><em>*</em>반려사유</th>
	          	<td>
	          		<%-- <input type="text" style="width: 400;height: 150"  name="memoContent" id="memoContent" value="${resultMap.MEMO_CONTENT }"> --%>
	          		<textarea name="retnReason" id="retnReason" rows="10" cols="88" maxlength="100"></textarea>
	          	</td>
	        </tr>
	      </table>
	    </li>
	  </ul>
	</div>
	

</form>
</body>
</html>