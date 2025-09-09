<%--
- Author(s): jdj
- Created Date:
- Version : 1.0
- Description : 기획전관리 - 기획전 내용(소개) - HTML 등록

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
			//opener.fn_search($("#categoryId").val(), $("#strCd").val(), $("#mkdpSeq").val());
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
		$("#divnSeq").val("${resultMap.DIVN_SEQ}");
		
		$('input:radio[name="chCd"]:radio:not(:checked)').attr('disabled', true);

	// 인서트 일경우
	} else {		
 		//종료기간 9999-12-31 23:59 셋팅
/* 		$("#mkdpEndDate").val('9999-12-31');
	
		$("#mkdpEndHh").val('23');
		$("#mkdpEndMm").val('59'); */
/* 		//기획전 종료일자로 셋팅
		var endDate = '${mkdpInfoMap.MKDP_END_DATE}';
		if(endDate!=''){
			endDate = endDate.substring(0, 4) + '-' + endDate.substring(4, 6) + '-' + endDate.substring(6, 8);
		}
		
		$("#mkdpEndDate").val(endDate);
		$("#mkdpEndHh").val('${mkdpInfoMap.MKDP_END_HH}');
		$("#mkdpEndMm").val('${mkdpInfoMap.MKDP_END_MM}');	 */
	}
	
	//등록방법에 따른 readonly처리
	if($('input[name="regMethodCd"]:checked').val()=='01'){  //파일 URL
		$("#indtHtml").attr('readonly','readonly');
		$("#indtFile").removeAttr("readonly");
	}else{
		$("#indtFile").attr('readonly','readonly');
		$("#indtHtml").removeAttr("readonly");
	}
	
	
	$('input[name="regMethodCd"]').change(function(){
		//console.log('regMethodCd===>'+$(this).val());
		
		//readonly처리후 데이터 공백처리
		if($(this).val()=='01'){  //파일 URL
			$("#indtHtml").attr('readonly','readonly');
			$("#indtHtml").val('');
			
			$("#indtFile").removeAttr("readonly");
			$("#indtFile").val('');
		}else{
			$("#indtFile").attr('readonly','readonly');
			$("#indtFile").val('');
			
			$("#indtHtml").removeAttr("readonly");
			$("#indtHtml").val('');
		}
	});
	
	
});



// 유효성 검사 
function fn_validation() {	
	var f = document.form1;

	//점포할당
 	if(f.subStrCd.value == "" || f.subStrCd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="점포할당"/>');
		f.subStrCd.focus();
		return false;
	}
	
	if($('input[name="regMethodCd"]:checked').val()=='01'){
	
		if(f.indtFile.value == "" || f.indtFile.value == "null") {
			alert('<spring:message code="msg.common.error.required" arguments="HTML 파일 URL"/>');
			f.indtFile.focus();
			return false;
		}
	}else if($('input[name="regMethodCd"]:checked').val()=='02'){
		
		if(f.indtHtml.value == "" || f.indtHtml.value == "null") {
			alert('<spring:message code="msg.common.error.required" arguments="HTML 소스"/>');
			f.indtHtml.focus();
			return false;
		}
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
 		 $('#form1').attr({action:'<c:url value="/exhibition/insertContentsHtml.do"/>',target:''}).submit();
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
	var oldStrCd = "${resultMap.STR_CD }";
	if(oldStrCd != strCd){
		$("#storeModifyYn").val("Y");
	}else{
		$("#storeModifyYn").val("N");
	}
	
	console.log("storeModifyYn=>"+$("#storeModifyYn").val());
}

//등록방법 변경시 해당 항목 readonly처리
/* $("#regMethodCd").change(function() {
	//readonly처리후 데이터 공백처리
	if($(this).val()=='01'){  //파일 URL
		$("#indtHtml").attr("readonly");
		$("#indtHtml").val('');
		
		$("#indtFile").removeAttr("readonly");
		$("#indtFile").val('');
	}else{
		$("#indtFile").attr("readonly");
		$("#indtFile").val('');
		
		$("#indtHtml").removeAttr("readonly");
		$("#indtHtml").val('');
	}	
}); */

/* $('input[type=radio][name=regMethodCd]').on('change', function() {
	console.log('regMethodCd===>');
	
	//readonly처리후 데이터 공백처리
	if($(this).val()=='01'){  //파일 URL
		$("#indtHtml").attr("readonly");
		$("#indtHtml").val('');
		
		$("#indtFile").removeAttr("readonly");
		$("#indtFile").val('');
	}else{
		$("#indtFile").attr("readonly");
		$("#indtFile").val('');
		
		$("#indtHtml").removeAttr("readonly");
		$("#indtHtml").val('');
	}	
});	 */

</script>


</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="form1" id="form1" method="post"  enctype="multipart/form-data">
<input type="hidden" id="divCorn" name="divCorn" >
<input type="hidden" id="gudnImg" name="gudnImg" value="<c:out value="${resultMap.GUDN_IMG }"/>" >

<input type="hidden" id="pageDiv"           name="pageDiv"  value="<c:out value="${pageDiv }"/>"">
<input type="hidden" id="dbStatus"          name="dbStatus" value="<c:out value="${resultMap.DB_STATUS }"/>" >

<input type="hidden" id="categoryId"        name="categoryId" value="<c:out value="${categoryId}"/>" >
<input type="hidden" id="mkdpSeq"           name="mkdpSeq"    value="<c:out value="${mkdpSeq }"/>" >

<!-- 컨텐츠구분코드(01:기획전 내용(소개)) -->
<input type="hidden" id="contentsDivnCd"    name="contentsDivnCd"    value="01" >
<!-- 구분자 번호 -->
<!-- <input type="hidden" id="divnSeq"           name="divnSeq"           value="AA" > -->

<!-- 점포코드(멀티) -->
<input type="hidden" id="subStrCd"       name="subStrCd"   value="<c:out value="${resultMap.SUB_STR_CD }"/>" >

<!-- 점포코드 수정시 Y로 변경 -->
<input type="hidden" id="storeModifyYn"  name="storeModifyYn"  value="N"/>

<input type="hidden" id="contentsSeq"    name="contentsSeq"  value="<c:out value="${contentsSeq }"/>" >

	<div class="pop_box_01">
	  <h2>기획전 등록<span class="pop_close"><a href="javascript:window.close();"><img src="${_image_path}/epc/new/pop_close.png" alt="close" /></a></span></h2>
	  <div class="p_navi">
	    <ul>
	      <li><a href="#"><img src="${_image_path}/epc/new/icon_home.png" alt="home" /></a> > 기획전 관리 > 기획전 등록 > 이미지/HTML 정보 등록 > HTML 정보 등록</li>
	    </ul>
	  </div>

	  <ul>
	  
	  <h3>HTML 정보
	  		<span class="p_btn">
	  			<%-- ${resultMap.DB_STATUS != 'U' ? '<span class="btnBG1"><a href="javascript:fn_update();">저장</a></span>' : '' } --%>
	  			<span class="btnBG1"><a href="javascript:fn_update();">저장</a></span>
	  			<span class="btnBG1"><a href="javascript:window.close();">닫기</a></span>
	  		</span>
	  	</h3>
	    <li>
	      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="기획전" >
	        <col width="15%" />
	        <col width="30%" />
	        <col width="15%" />
	        <col width=" " />	        
	        <tr>
	        	<th><em>*</em>채널</th>
	          	<td>
	          	    <label>
                       <input name="chCd" id="chCd"  type="radio" class="choice" title="01" value="01" ${resultMap.CH_CD == '01' || resultMap.CH_CD == null ? 'checked' : '' }/>PC</label>
                     &nbsp;
                     <label>
                       <input name="chCd" id="chCd"  type="radio" class="choice" title="02" value="02" ${resultMap.CH_CD == '02' ? 'checked' : '' }/>모바일</label>	          	
	          	</td>
	          	<th><em>*</em>등록방법</th>
	          	<td>
	          	    <label>
                       <input name="regMethodCd" id="regMethodCd"  type="radio" class="choice" title="01" value="01" ${resultMap.REG_METHOD_CD == '01' || resultMap.REG_METHOD_CD == null ? 'checked' : '' }/>파일 URL 등록</label>
                     &nbsp;
                     <label>
                       <input name="regMethodCd" id="regMethodCd"  type="radio" class="choice" title="02" value="02" ${resultMap.REG_METHOD_CD == '02' ? 'checked' : '' }/>에디터 편집 등록</label>	          	
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
                    <textarea name="subStrNm" id="subStrNm" rows="3" cols="145" readonly>${resultMap.SUB_STR_NM}</textarea>                            			          	
	          	</td>
	        </tr>	        
	        <tr>
	        	<th>HTML파일URL</th>
	          	<td colspan="3">
	          		<input name="indtFile" type="text" id="indtFile" style="width:784px" title="HTML파일URL" value="${resultMap.INDT_FILE}">
	          	</td>
	        </tr>
	        <tr>
	        	<th>HTML소스</th>
	          	<td colspan="3"> 
	          		<textarea name="indtHtml" id="indtHtml" rows="20" cols="150">${resultMap.INDT_HTML}</textarea>
	          	</td>
	        </tr>	        
	      </table>
	    </li>
	  </ul>
	</div>

</form>
</body>
</html>