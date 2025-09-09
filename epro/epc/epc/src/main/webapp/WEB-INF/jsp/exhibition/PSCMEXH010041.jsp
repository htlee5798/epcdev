<%--
- Author(s): jdj
- Created Date:
- Version : 1.0
- Description : 기획전관리 - 기획전 내용(소개) - 이미지등록

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

<%
    String toDate = DateUtil.formatDate(DateUtil.getToday(),"-");    
%>

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
		if(code == "1") {	    //저장 성공 
			opener.fn_search($("#categoryId").val(), $("#mkdpSeq").val());
			window.close();
		}else if(code == "0") { //실패
			window.close();
		}
	}
		
	var dbStatus = "${resultMap.DB_STATUS}";    //DB존재시 U, 미존재시 I
	
	// 수정일 경우 
	if(dbStatus == "U") {
		//console.log('수정 상태');
		
		//채널 비활성화
		$('input:radio[name="chCd"]:radio:not(:checked)').attr('disabled', true);
	
	// 인서트 일경우
	} else {

	}
	
});



// 유효성 검사 
function fn_validation() {
	
	var f = document.form1;
	var pageDiv = $("#pageDiv").val();
	
	if(pageDiv=="insert"){
		//이미지 파일
		if(f.tmpFile.value == "" || f.tmpFile.value == "null") {
			alert('<spring:message code="msg.common.error.required" arguments="이미지 파일"/>');
			f.tmpFile.focus();
			return;
		}
	}
	
	//점포할당
	if(f.subStrCd.value == "" || f.subStrCd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="점포할당"/>');
		f.subStrCd.focus();
		return;
	}
	
	return true;
}

//시간 체크
function fHourCheck(val){
	var hourChk = false;
	if(val > 24){
		hourChk = true;
	}
	return hourChk;
}
// 분 체크
function fMinuteCheck(val){
	var minuteChk = false;
	if(val > 59){
		minuteChk = true;
	}
	return minuteChk;
}	

// 이미지 미리보기 
function fn_showImg() {
	var gudnImg = $("#gudnImg").val();
	
	var targetUrl = "${pageContext.request.contextPath}/newTemplate/showImageForm.do"
        +"?gudnImg="+ gudnImg;
		Common.centerPopupWindow(targetUrl, 'imageForm', {
			title : '이미지 미리보기 팝업', 
			width : 600, 
			height : 600, 
			scrollBars : 'NO'
		});
}

//저장/수정
function fn_update() {
	var pageDiv = $("#pageDiv").val();
	var msg = "";
	
	if(!fn_validation()) return;
	
	if(pageDiv == "insert") {
		msg = '<spring:message code="msg.common.confirm.regist" />';
	}else if(pageDiv == "update") {
		msg = '<spring:message code="msg.common.confirm.update" />'
	}
	
 	if(confirm(msg)) {
 		 $('#form1').attr({action:'<c:url value="/exhibition/insertContentsImage.do"/>',target:''}).submit();
	}
}

//점포팝업 반환값 셋팅 
function fnSetStr(data) {
	var strCd = "", strNm = "";
	
	if(jQuery.isArray(data.strCdArr)){
		for(var i=0; i < data.strCdArr.length; i++ ){
			strCd += data.strCdArr[i]+',';
			strNm += data.strNmArr[i]+',';
		}
		
		if(strCd!="") strCd = strCd.substring(0, strCd.length-1);
		if(strNm!="") strNm = strNm.substring(0, strNm.length-1);
		
	}else{
		strCd = data.strCd;
		strNm = data.strNm;
	}
	
	$("#subStrCd").val(strCd);
	$("#subStrNm").val(strNm);
	
	//점포코드 수정여부체크
	var oldStrCd = "${resultMap.SUB_STR_CD }";
	if(oldStrCd != strCd){
		$("#storeModifyYn").val("Y");
	}else{
		$("#storeModifyYn").val("N");
	}
	
	console.log("storeModifyYn=>"+$("#storeModifyYn").val());
}

</script>


</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="form1" id="form1" method="post"  enctype="multipart/form-data">
<input type="hidden" id="divCorn" name="divCorn" >
<input type="hidden" id="gudnImg" name="gudnImg" value="<c:out value="${resultMap.GUDN_IMG }"/>" >

<input type="hidden" id="pageDiv"           name="pageDiv"  value="<c:out value="${pageDiv }"/>"">
<input type="hidden" id="dbStatus"          name="dbStatus" value="<c:out value="${resultMap.DB_STATUS }"/>" >

<input type="hidden" id="categoryId"        name="categoryId"   value="<c:out value="${categoryId}"/>" >
<input type="hidden" id="mkdpSeq"           name="mkdpSeq"      value="<c:out value="${mkdpSeq }"/>" >

<input type="hidden" id="contentsSeq"       name="contentsSeq"  value="<c:out value="${contentsSeq }"/>" >

<!-- 컨텐츠구분코드(01:기획전 내용(소개), 03:기획전 배너 이미지) -->
<input type="hidden" id="contentsDivnCd"    name="contentsDivnCd"    value="<c:out value="${contentsDivnCd}"/>" >

<!-- 점포코드(멀티) -->
<input type="hidden" id="subStrCd"   name="subStrCd"   value="<c:out value="${resultMap.SUB_STR_CD }"/>" >

<!-- 점포코드 수정시 Y로 변경 -->
<input type="hidden" id="storeModifyYn"  name="storeModifyYn"  value="N"/>

	<div class="pop_box_01">
	  <h2>기획전 등록<span class="pop_close"><a href="javascript:window.close();"><img src="${_image_path}/epc/new/pop_close.png" alt="close" /></a></span></h2>
	  <div class="p_navi">
	    <ul>
	      <li><a href="#"><img src="${_image_path}/epc/new/icon_home.png" alt="home" /></a> > 기획전 관리 > 기획전 등록 > 이미지/HTML 정보 등록 > 이미지 정보 등록</li>
	    </ul>
	  </div>

	  <ul>
	  
	  <h3>이미지 정보
	  		<span class="p_btn">
	  			<%-- ${resultMap.DB_STATUS != 'U' ? '<span class="btnBG1"><a href="javascript:fn_update();">저장</a></span>' : '' } --%>
	  			<span class="btnBG1"><a href="javascript:fn_update();">저장</a></span>
	  			<span class="btnBG1"><a href="javascript:window.close();">닫기</a></span>
	  		</span>
	  	</h3>
	    <li>
	      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="기획전" >
	        <col width="6%" />
	        <col width="30%" />
	        <col width="20%" />
	        <col width=" " />
	        
	        <tr>
	        	<th>채널</th>
	          	<td colspan="3">
	          	    <label>
                       <input name="chCd" id="chCd"  type="radio" class="choice" title="01" value="01" ${resultMap.CH_CD == '01' || resultMap.CH_CD == null ? 'checked' : '' }/>PC</label>
                     &nbsp;
                     <label>
                       <input name="chCd" id="chCd"  type="radio" class="choice" title="02" value="02" ${resultMap.CH_CD == '02' ? 'checked' : '' }/>모바일</label>	          	
	          	</td>
	        </tr>
	        
	        <tr>
	        	<th>이미지 파일</th>
	          	<td colspan="3">
	          		<input type="file" name="tmpFile" id="tmpFile" style="width:50%">
	          	</td>
	        </tr>
	        
	        <tr>
	        	<th><em>*</em>전시 기간</th>
	          	<td colspan="3">
					
					<input type="text" style="width:72px" title="년월일"   value="${mkdpInfoMap.VIEW_MKDP_START_YYMMDD}" readonly>
					<input type="text" style="width:20px" title="시작시간" value="${mkdpInfoMap.MKDP_START_HH}"   readonly>
					<input type="text" style="width:20px" title="시작분"   value="${mkdpInfoMap.MKDP_START_MM}"   readonly>
					~
					<input type="text" style="width:72px" title="년월일"   value="${mkdpInfoMap.VIEW_MKDP_END_YYMMDD}" readonly>
					<input type="text" style="width:20px" title="종료시간" value="${mkdpInfoMap.MKDP_END_HH}"   readonly>
					<input type="text" style="width:20px" title="종료분"   value="${mkdpInfoMap.MKDP_END_MM}"   readonly>
	          	</td>
	        </tr>	        
	        <tr>
	        	<th><em>*</em>점포할당</th>
	          	<td colspan="3">                    
                    <a href="javascript:openStr('M');"><img style="vertical-align: top; padding-top: 3px;" src="${_image_path}/epc/new/btn_preview.png" alt="찾기" class="search"></a>
                    <textarea name="subStrNm" id="subStrNm" rows="3" cols="85" readonly>${resultMap.SUB_STR_NM}</textarea>                    
	          	</td>
	        </tr>
	        <tr>
	        	<th><em>*</em>미리보기</th>
	          	<td colspan="3">
					<img width="150" height="150" src="${resultMap.IMG_PATH}" alt="이미지" />
	          	</td>
	        </tr>
	        
	      </table>
	    </li>
	  </ul>
	</div>
	

</form>
</body>
</html>