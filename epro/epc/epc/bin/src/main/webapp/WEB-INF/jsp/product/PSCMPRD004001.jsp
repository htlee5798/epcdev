<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="lcn.module.common.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<% 
	String Date  =DateUtil.formatDate(DateUtil.getToday(),"-").replace("-", "");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<%@ include file="/common/scm/scmCommon.jsp" %>

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<!-- product/PSCMBRD004001 -->

<!-- 공통코드 -->
	<c:set var="sm341Cd" />
	<c:set var="sm341Nm" />
	<c:forEach items="${codeList}" var="sm341Code" varStatus="idx2">
		<c:choose>
			<c:when test="${ fn:length(codeList) eq idx2.index+1  }">
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.MINOR_CD }"/>
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.CD_NM  }"/>
			</c:when>
			<c:otherwise>
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.MINOR_CD }|"/>
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.CD_NM  }|"/>
			</c:otherwise>
		</c:choose>
	</c:forEach>

<script type="text/javascript">
$(document).ready(function(){
	//달력셋팅
	$("#startDate, #endDate").attr("readonly", "readonly");
	$('#btnStartDate, #startDate').click(function() {
		openCal('dataForm.startDate');
	});
	$('#btnEndDate, #endDate').click(function() {
		openCal('dataForm.endDate');
	});
	
	//초기달력값 셋팅
	$("#endDate").val('${endDate}');
	$("#startDate").val('${startDate}');

}); // end of ready

/**********************************************************
 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
 ******************************************************** */
function keyCode(e)
{
    var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
    
    if (code >  32 && code <  48) keyResult(e);
    if (code >  57 && code <  65) keyResult(e);
    if (code >  90 && code <  97) keyResult(e);
    if (code > 122 && code < 127) keyResult(e);
}
 
 function keyResult(e)
 {
        alert("특수문자를 사용할수 없습니다!");
        
        if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
        }
        else {
            e.preventDefault(); //FF - Chrome both
        }
  }
 
 
/**********************************************************
 * 글자 바이트 체크
 ******************************************************** */
String.prototype.getBytes1 = function() {
	var size = 0;
	for(i=0; i<this.length; i++) {
		var temp = this.charAt(i);
		if(escape(temp) == '%0D') continue;
		if(escape(temp).indexOf("%u") != -1) {
			size += 3;
		}else {
			size++;
		}
	}
	return size;
}


/**********************************************************
 * 조회기간 체크
 ******************************************************** */
function doDateCheck()
{
    var form = document.dataForm;

    if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' ))
    {
        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
        return ;
    }
    
    return true;
}

/** ********************************************************
 * 셀링포인트 글자제한
 ******************************************************** */
function fnChkByte(obj, maxByte, check){
	

	
	var str = obj.value;
	var str_len = str.length;

	var rbyte = 0;
	var rlen = 0;
	var one_char = "";
	var str2 = "";

	for(var i=0; i<str_len; i++){
	one_char = str.charAt(i);
	if(escape(one_char).length > 4){
		  rbyte += 3;   
	}else{
	    rbyte++;                                            //영문 등 나머지 1Byte
	}

	if(rbyte <= maxByte){
	    rlen = i+1;                                          //return할 문자열 갯수
	}
	}

	if(rbyte > maxByte){
	    alert("한글 "+parseInt(maxByte/3)+"자 / 영문 "+maxByte+"자를 초과 입력할 수 없습니다.");
	    str2 = str.substr(0,rlen);                                  //문자열 자르기
	    obj.value = str2;
	    fnChkByte(obj, maxByte);
	}
	
	//바이트 체크
	var text =   $(obj).val().getBytes1("UTF-8");
	document.getElementById(check).innerText = text;
	}


	/** ********************************************************
	 * 셀링포인트 등록
	 ******************************************************** */
	function doSave() {
		var form = document.dataForm;
		
		if (!confirm('저장 하시겠습니까?')) {
			return true;
		}
		
	    if(form.title.value == ""){
			alert("제목을 입력해주세요.");
			form.title.focus();
			return true;
		}
	    
		if(form.content.value == ""){
			alert("내용을 입력해주세요.");
			form.content.focus();
			return true;
		}
		
		if(form.startDate.value.replace( /-/gi, '' )< <%=Date%>){
			alert("현재날짜 이전으로 등록할수 없습니다.");
			return;
		}
		
	    if (form.startDate.value.replace( /-/gi, '' ) >= form.endDate.value.replace( /-/gi, '' ))
	    {
	        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
	        return ;
	    }
	
		callAjaxByForm('#dataForm', '<c:url value="/product/selSave.do"/>',
				'#dataForm', 'POST');
	}

	function callAjaxByForm(form, url, target, Type) {

		var formQueryString = $('*', form).fieldSerialize();

		$
				.ajax({
					type : Type,
					url : url,
					data : formQueryString,
					success : function(json) {
						try {
							if (jQuery.trim(json) == "") { //처리성공
								alert('<spring:message code="msg.common.success.request"/>');
								opener.doSearch();
								top.close();
								
							} else if ("accessAlertFail") {
								alert('<spring:message code="msg.common.error.noAuth"/>');
							} else {
								alert(jQuery.trim(json));
							}
						} catch (e) {
						}
					},
					error : function(e) {
						alert(e);
					}
				});
	}
	
</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>

	<form name="dataForm" id="dataForm" method="post">
		<input type="hidden" name="regId" id="regId" value="${regId}">

			<div id="popup">
				<!--  @title  -->
				<div id="p_title1">
					<h1>셀링포인트 등록</h1>
					<span class="logo"><img
						src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif"
						alt="LOTTE MART" /></span>
				</div>
				<!--  @title  //-->
				<!--  @process  -->
				<div id="process1">
					<ul>
						<li>홈</li>
						<li>상품관리</li>
						<li>셀링포인트 관리</li>
						<li class="last">셀링포인트 등록</li>
					</ul>
				</div>
				<!--  @process  //-->


				<div class="popup_contents">
					<!-- 질문내용-->
					<div class="bbs_search3">
						<ul class="tit">
							<li class="btn">
							<a href="#" class="btn" id="btn"  onclick="doSave()"> <span> <spring:message code="button.common.save" /> </span> </a>
							<a href="#" class="btn" onclick="top.close();"> <span><spring:message code="button.common.close" /></span> </a>
							</li>
						</ul>
						<table class="bbs_grid2" cellpadding="0" cellspacing="0"
							border="0">
							<colgroup>
								<col width="20%">
								<col width="80%">
							</colgroup>

							<tr>
								<th><span class="star">*&nbsp</span>제목</th>
								<td colspan="2">
									<input type="text" id="title" name="title" size="82"  onKeyUp="javascript:fnChkByte(this,'100','byteInfo1')" onKeyPress="keyCode(event)"/> <b>(<span id="byteInfo1">0</span>/100Byte) </b> 
								</td>
							</tr>

							<tr style="height: 150px">
								<th><span class="star">*&nbsp</span>내용</th>
								<td colspan="2">
									<textarea name="content" id="content" rows="10" cols="80"  onKeyUp="javascript:fnChkByte(this,'100','byteInfo2')" onKeyPress="keyCode(event)"></textarea> <b>(<span id="byteInfo2">0</span>/100Byte) </b> 
								 </td>
							</tr>

							<tr>
								<th class="fst"><span class="star">*&nbsp</span>공지기간</th>
								<td class="text">
									<input type="text" id="startDate" name="startDate" style="width: 31%;" readonly class="day" />
									<a href="#" id="btnStartDate"><img 	src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>~ 
									<input type="text" id="endDate" name="endDate" style="width: 31%;" readonly class="day" /> <a href="#" id="btnEndDate">
									<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="absmiddle" class="middle" /></a>
								</td>
						</table>
					</div>
				</div>
			</div>
	</form>
	<br></br>
	<iframe name="_if_save" src="/html/epc/blank.html" style="display: none;"></iframe>
</body>

</html>