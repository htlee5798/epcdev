<%--
	Page Name 	: NEDMPRO0312.jsp
	Description : ECS 연동 USER ID 받는 팝업
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2025.04.04 		park jong gyu	 		최초생성
	
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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

<script type="text/javascript">
$(document).ready(function(){	
    // 닫기버튼 클릭
    $('#close').click(function() {
        top.close();
    });
    
}); // end of ready

// ecs 연동
function ecsIntgr(val){

	if( $("input[name='ecsUserId']").val() == ''){
		alert('공문발송을 위해 ECS 아이디를 입력하셔야 합니다.');
		return;
	}
	
	let searchInfo = {
			'reqOfrcd' : $("input[name='reqOfrcd']").val()
		,	'ecsUserId' : $("input[name='ecsUserId']").val()
		,	'taskGbn' : $("input[name='taskGbn']").val()
		,	'apprStatus' : '05'
	};
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : "<c:url value='/edi/product/insertProdEcsIntgr.json'/>",
		data : JSON.stringify(searchInfo),
		success : function(data) {
			if(data.result== true){
				let specs = 
					"height="+ $(window).height() +
					",width=1220"+
					",top=20"+
					",left="+($(window).scrollLeft() + ($(document).width() - 1200) / 2) +
					",scrollbars=yes"+
					",resizable=yes";
				
				/*if( $("input[name='vkorg']").val() == 'KR04' ){
					window.open(data.url1, "sendPopup1", specs);
					window.open(data.url2, "sendPopup2", specs);
				}else{
					window.open(data.url1, "sendPopup1", specs);
				}*/
				window.open(data.url, "sendPopup", specs);
				top.close();
			}else{
				alert(data.msg);
			}
		}
	});// end ajax
	
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form id="searchForm" name="searchForm" method="post">

	<input type="hidden" name="reqOfrcd" id="reqOfrcd" value="<c:out value='${reqOfrcd}'/>" > 	
	<input type="hidden" name="vkorg" id="vkorg" value="<c:out value='${vkorg}'/>" > 	
	<input type="hidden" name="taskGbn" id="taskGbn" value="<c:out value='${taskGbn}'/>" > 	

<div id="popup">
    <div id="p_title1">
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <br>
	<div class="popup_contents">
		<div class="bbs_search2" style="width:100%;">
	         <ul class="tit">
	            <li class="tit">Ecs ID</li>
	            <li class="btn">
	                <a href="#" class="btn" id="ecsIntgr" onclick="ecsIntgr()"><span><spring:message code="button.common.confirm"  /></span></a>
	                <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
	            </li>
	        </ul>
	        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	        </table>
	        <!------------------------------------------------------------------ -->
	        <!--    table -->
	        <!------------------------------------------------------------------ -->
	        <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	            <colgroup>
	                <col width="15%">
	                <col width="40%">
	                <col width="15%">
	                <col width="40%">
	            </colgroup>
	            <!-- row 1 ------------------------------------->
	            <tr>
	            	<th>Ecs 로그인 ID</th>
	            	<td>
	            		<input type="text" name="ecsUserId" id="ecsUserId"  style="width:98%;" />
					</td>
	                
	            </tr>
	        </table>
	        <!---------------------------------------------------- end of table -- -->
	    </div>
    </div>
</div>
</form>
</body>
</html>