<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../common.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<!-- tooltipster -->
<link rel="stylesheet" type="text/css" href="/js/epc/tooltipster/css/tooltipster.bundle.css" />
<link rel="stylesheet" type="text/css" href="/js/epc/tooltipster/css/plugins/tooltipster/sideTip/themes/tooltipster-sideTip-shadow.min.css" />
<link rel="stylesheet" type="text/css" href="/js/epc/tooltipster/css/plugins/tooltipster/sideTip/themes/tooltipster-sideTip-light.min.css" />
<link rel="stylesheet" type="text/css" href="/js/epc/tooltipster/css/plugins/tooltipster/sideTip/themes/tooltipster-sideTip-borderless.min.css" />


<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.validate.1.8.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.custom.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridExtension.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridConfig.js" ></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/epc/validate.js" ></script>
<script type="text/javascript" src="/js/epc/member.js" ></script>
<script type="text/javascript" src="/js/common/utils.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.handler.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.tmpl.js"></script>
<script type="text/javascript" src="/js/epc/tooltipster/js/tooltipster.bundle.js"></script>

<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>

<script language="javascript">
$(document).ready(function(){
	//검색
	$('#search').click(function() {
		goPage("1");
	});
	
	// 닫기버튼 클릭
	$('#close').click(function() {
		top.close();
	});
	 
	// 조회조건 입력필드 enter key이벤트
	$('#srchManagerNm ,#srchTeamNm').keydown(function() {
		if(this.tagName == "input" && this.type == "text"){
			if(!fncChkSrchValue(this)) return;
		}
		
		if (event.keyCode == 13) {
			event.preventDefault();
			goPage('1');
		}
	});
	
	//조회조건 입력 시, 입력값 필터링
	
}); // end of ready

//검색어 문자열 filtering 처리
function fncChkSrchValue(obj){
	let val = $.trim($(obj).val());
	
	const srchRegExp = /[%%_\?]/; 
	var chkSrchVal = srchRegExp.test(val);

	if(chkSrchVal){
		alert("검색어에 '%', '?', '_' 문자를 포함할 수 없습니다.");
		val = val.replaceAll(srchRegExp, "");
		$(obj).val(val);
		return false;
	}
	
	return true;
}

//전체 검색어 문자열 filtering 처리
function fncChkSrchValueAll(){
	let flag = true;
	$("#searchBody").find("input, select").each(function(){
		if(!fncChkSrchValue(this)){
			flag = false;
			return false;			
		}
	});
	
	if(!flag) return false;
	
	return true;
}

//조회 
function goPage(pageIndex) {
	if(!fncChkSrchValueAll()) return;
	
	var searchInfo = {};
	//--- 데이터구성
	$("#dataForm").find("input, select").each(function(){
		if(this.name != null && this.name != ""){
			searchInfo[this.name] = $(this).val();
		}
	});
	
	searchInfo.pageIndex = pageIndex;
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selEcsReceiverPopupInfo.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			$("#pageIndex").val(pageIndex);
			
			//json 으로 호출된 결과값을 화면에 Setting
			_setTbody(data);
		}
	});	
}



/* List Data 셋팅 */
function _setTbody(json) {
    setTbodyInit("dataListbody"); // dataList 초기화

    let data = json.list;
    
    if (data != null && data.length > 0) {
    	$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		$("#paging-ul").html(json.contents);
    } else {
        setTbodyNoResult("dataListbody", 4);
        $("#paging-ul").html("");
    }
}


 //콜백
function selectManagerNmDisplay(obj) {
	var rtnData = {};
	
	$(obj).closest("tr").find("input").each(function(){
		if(this.name != undefined && this.name != null && this.name != ""){
			rtnData[this.name] = $.trim($(this).val());
		}
	});
	
	opener.setEcsReceiverNm(rtnData);
	top.close();
}
 
</script>
	<!-- DATA LIST -->
	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<tr class="r1" bgcolor=ffffff>
			<input type="hidden" name="empNo"		value="<c:out value='\${empNo}'/>"/>
			<input type="hidden" name="teamCd"		value="<c:out value='\${teamCd}'/>"/>
			<input type="hidden" name="teamNm" 		value="<c:out value='\${teamNm}'/>"/>
			<input type="hidden" name="managerNm" 	value="<c:out value='\${managerNm}'/>"/>
			<td align="center"><c:out value="\${rnum}"/></td>
			<td align="left">
				<c:out value="\${teamNm}" />
			</td>
			<td align="left">
				<c:out value='\${empNo}'/>
			</td>
			<td align="center">
				<a href="javascript:void(0);" onclick="selectManagerNmDisplay(this);">
        			<c:out value="\${managerNm}" />
    			</a>
			</td>
		</tr>
	</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<form name="dataForm" id="dataForm" >
<input type="hidden" id="closeFlag"  name="closeFlag" value="<c:out escapeXml='false' value='${param.closeFlag }'/>"/>
<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />

<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
    <br>
	<div class="popup_contents">

	<!------------------------------------------------------------------ -->
	<!-- 	검색조건 -->
	<!------------------------------------------------------------------ -->
	<!-- <form name="searchForm" id="searchForm"> -->
		<div class="bbs_search2" style="width:100%;">
	         <ul class="tit">
	            <li class="tit">ECS 수신 담당자 조회</li>
	            <li class="btn">
	                <a href="javascript:void(0);" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
	                <a href="javascript:void(0);" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
	            </li>
	        </ul>
	        <!------------------------------------------------------------------ -->
	        <!--    table -->
	        <!------------------------------------------------------------------ -->
	        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	            <colgroup>
	                <col width="15%">
	                <col width="35%">
	                <col width="15%">
	                <col width="35%">
	            </colgroup>
	            <!-- row 1 ------------------------------------->
	            <tbody id="searchBody">
		            <tr>
						<th>부서/팀명</th>
						<td><input type="text" name="srchTeamNm" id="srchTeamNm" style="width:60%;" value=""/></td>
		                <th>담당자명</th>
		                <td><input type="text" name="srchManagerNm" id="srchManagerNm"  style="width:60%;" value=""/></td>
		            </tr>
	            </tbody>
	        </table>
	        <!---------------------------------------------------- end of table -- -->
	    </div>
	<!--  </form> -->
	<!----------------------------------------------------- end of 검색조건 -->
				
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">ECS 수신 담당자</li>
			</ul>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width="100%" >
							<colgroup>
								<col width="5%">
								<col width="35%">
								<col width="20%">
								<col width="40%">
							</colgroup>
							<tr>
								<th>No.</th>
								<th>팀명</th>
								<th>사번</th>
								<th>담당자명</th>
							</tr>
						 	<tbody id="dataListbody" /> 
						</table>
			
					</td>
				</tr>
			
			</table>
		</div>
	</div>
		<!-- Pagging Start ---------->			
		<div id="paging_div" style="text-align:-webkit-center;">
	        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
		</div>
		<!-- Pagging End ---------->
	
    </div><!-- class popup_contents// -->
    
    <br/>

</div><!-- id popup// -->
</form>

</body>
</html>