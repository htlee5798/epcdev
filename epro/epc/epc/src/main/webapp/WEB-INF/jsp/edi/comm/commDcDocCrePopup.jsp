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
<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>

<script language="javascript">
$(document).ready(function(){
	$('#popupFlag').val('<c:out value="${popupFlag}" />');
	
	// 닫기버튼 클릭
	$('#close').click(function() {
	    //window.close();    
	    top.close();
	});
	
	// 공문생성버튼 클릭
	$('#write').click(function(){
		fncWrite();
	});
	
	 
}); // end of ready

//공문생성
function fncWrite(){
	var selLen = "<c:out value='${param.selLen}'/>";
	
	var dcCreGbn = $.trim($("input[name='dcCreGbn']:checked").val());	//ECS 수신처사업자번호
	var ecsWrtId = $.trim($("input[name='ecsWrtId']").val()).replace(/\s/g,""); //ECS 작성아이디
	
	if(dcCreGbn == ""){
		alert("계약(공문) 수신처를 선택해주세요.");
		return;
	}
	
	if(ecsWrtId == ""){
		alert("ECS 아이디를 입력해주세요.");
		$("#ecsWrtId").focus();
		return;
	}
	
	if(!confirm("선택하신 "+selLen+"건에 대해 공문을 생성하시겠습니까?")) return;
	
	var rtnData = {};
	
	rtnData.dcCreGbn = dcCreGbn;
	rtnData.ecsWrtId = ecsWrtId;
	
	opener.fncCbDcDocCreBefore(rtnData);
	top.close();
}
 
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<form name="dataForm" id="dataForm" >
<input type="hidden" id="closeFlag"  name="closeFlag" value="<c:out value='${param.closeFlag }'/>"/>
<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />
<input type="hidden" id="popupFlag" name="popupFlag" value="<c:out value="${popupFlag}" />" />

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
		<div class="bbs_search2" style="width:100%;">
	         <ul class="tit">
	            <li class="tit">공문생성</li>
	            <li class="btn">
	                <a href="javascript:void(0);" class="btn" id="write"><span>공문생성</span></a>
	                <a href="javascript:void(0);" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
	            </li>
	        </ul>
	        <!------------------------------------------------------------------ -->
	        <!--    table -->
	        <!------------------------------------------------------------------ -->
	        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	        	<colgroup>
	        		<col width="30%"/>
	        		<col width="70%"/>
	        	</colgroup>
	        	<tbody>
	        		<tr>
	        			<th>수신처</th>
	        			<td>
	        				<c:if test='${empty paramVo.purDept or paramVo.purDept != "KR04"}'>
	        					<input type="radio" id="dcCreGbn_1" name="dcCreGbn" value="MT" checked/><label for="dcCreGbn_1">마트</label>
	        				</c:if>
	        				<c:if test='${empty paramVo.purDept or paramVo.purDept == "KR04"}'>
		        				<input type="radio" id="dcCreGbn_2" name="dcCreGbn" value="SP"/><label for="dcCreGbn_2">슈퍼</label>
		        				<input type="radio" id="dcCreGbn_3" name="dcCreGbn" value="CS"/><label for="dcCreGbn_3">CS</label>
	        				</c:if>
	        			</td>
	        		</tr>
	        		<tr>
	        			<th>ECS 아이디</th>
	        			<td>
	        				<input type="text" id="ecsWrtId" name="ecsWrtId" value=""/>
	        			</td>
	        		</tr>
	        	</tbody>
	        </table>
	        <!---------------------------------------------------- end of table -- -->
	    </div>
	
    </div><!-- class popup_contents// -->
    <br/>
</div><!-- id popup// -->
</form>

</body>
</html>