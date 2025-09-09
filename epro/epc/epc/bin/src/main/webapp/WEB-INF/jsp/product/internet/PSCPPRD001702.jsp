<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"            %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld"              %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<% 
	String prodCd   = SecureUtil.stripXSS(request.getParameter("prodCd"  ));
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
	String keyCount = SecureUtil.stripXSS(request.getParameter("keyCount"));
	String byteChk  = SecureUtil.stripXSS(request.getParameter("byteChk" ));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script>
// 키워드 입력
function doInsert(){

	$('#searchKywrd').val($.trim($('#searchKywrd').val()));
	if(!byteCheck('상품키워드',$('#searchKywrd').val(),39,$('#searchKywrd'))){
		return;
	}
	//값 입력 유무 채크
	if(Common.isEmpty($.trim($('#searchKywrd').val()))){
		alert('<spring:message code="msg.common.error.required" arguments="상품키워드"/>');
		$('#searchKywrd').focus();
		return;
	}

	var keyCount = parseInt($('#keyCount').val());
	var byteChk = parseInt($('#byteChk').val());
	var searchKywrd = $('#searchKywrd').val();

	if(keyCount > 10) {
		alert( "상품키워드가 10개를 초과했습니다.");
		return;
	}

    var len = getByte(searchKywrd);

	if(len>0){
		var total_byte  = parseInt(len) + parseInt(byteChk); 
		if(total_byte  > 399){//전체키워드에서 구분값 때문에 399와 비교
			alert("키워드의 전체의 길이가 400byte의 크기를 초과하였습니다.");
			return;
		}
	}
	
	//20181016 특수문자 유효성 추가
	if($.trim(searchKywrd).indexOf(',') > -1 || $.trim(searchKywrd).indexOf(';') > -1 || $.trim(searchKywrd).indexOf('|') > -1) {
		alert(",(커머) ;(세미콜론) |(버티컬 바) 특수문자는 사용하실 수 없습니다.");
		return;
	}
	//20181016 특수문자 유효성 추가
	
	//20180911 상품키워드 입력 기능 추가
	var aprvCd = '<c:out value="${prdMdAprInfo.aprvCd}" />';

	if(aprvCd == "001") {
		alert('현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다. 등록 값은 승인 완료 후 반영됩니다.');
	} else if(aprvCd == "002") {
		alert('현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→상세키워드정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)');
	} else {
		alert('등록 값은 관리자 승인 완료 후 반영됩니다.');
	}
	//20180911 상품키워드 입력 기능 추가
	
	opener.doInsert($('#searchKywrd').val());
	top.close();
}

// 키워드 입력 아작스 호출
function callAjaxByForm(form, url, target, Type) {

	var formQueryString = $('*', '#dataForm').fieldSerialize();
	$.ajax({
		type: Type,
		url: url,
		data: formQueryString,
		success: function(json) {
			try {
				// 권한에러 메시지 처리 조건문 
// 				if(jQuery.trim(json) == "accessAlertFail") {
// 					alert('<spring:message code="msg.common.error.noAuth"/>');
// 				} else {				
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.insert"/>');
						opener.goPage('1');
						top.close();
					}else{
						alert(jQuery.trim(json));
					}
// 				}
			} catch (e) {}
		},
		error: function(e) {
			alert("저장에 실패하였습니다");
		}
	});
	
}

// 현재창 닫기
function doClose(){
	top.close();
}

$(document).ready(function(){
    //input enter 막기
    $("*").keypress(function(e){
        if(e.keyCode==13) return false;
    });
});

</script>
</head>

<body>
<form name="dataForm" id="dataForm">
<input type="hidden" id="prodCd"   name="prodCd"   value="<%=prodCd%>"  />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
<input type="hidden" id="keyCount" name="keyCount" value="<%=keyCount%>"/>
<input type="hidden" id="byteChk"  name="byteChk"  value="<%=byteChk%>" />

<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품키워드</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>상품관리</li>
			<li class="last">인터넷상품관리</li>			
		</ul>
     </div>
	 <!--  @process  //-->
	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
				<ul class="tit">
					<li class="tit">상품키워드 입력</li>
					<li class="btn">
						<a href="javascript:doInsert();" class="btn" ><span><spring:message code="button.common.save"/></span></a>
						<a href="javascript:doClose();" class="btn" ><span><spring:message code="button.common.close"/></span></a>
					</li>
				</ul>
				<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<th><span class="star"></span>상품키워드</th>
					<td><input type="text" class="searchKywrd"  maxlength="150" name="searchKywrd" id="searchKywrd"  value="" size="80"></td>
				</tr>				
				</table>
		</div>				
	</div>
</div>	
</form>

</body>
</html>