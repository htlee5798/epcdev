<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../common.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript">
$(document).ready(function(){
    
});


</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>

<form name="dataForm" id="dataForm" action="<c:out value='${ctx}'/>/edi/product/sellCdPopup.do" method="post">


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->
    
	<div class="popup_contents">
	    <div class="wrap_con">
	        <div class="bbs_list">
	            <ul class="tit">
	                <li class="tit">상세 이미지</li>
	            </ul>
	            <table cellpadding="0" cellspacing="0" border="0" width="100%">
	                <tr>
	                    <td>
	                        <!-- 이미지 출력 -->
	                        <div style="width:100%; height:100%; overflow:auto;">
	                        	<c:choose>
	                        		<c:when test='${not empty atchFileId}'>
	                        			<img alt="상세 이미지" src="<c:url value='/common/${workGbn}/${atchFileId}/loadImageCommon.do'/>" style="max-width:100%; height:auto;">
	                        		</c:when>
	                        		<c:otherwise>
	                        			<img alt="상세 이미지" src="<c:out value='${imgUrl}'/>" style="max-width:100%; height:auto;">
	                        		</c:otherwise>
	                        	</c:choose>
	                        </div>
	                    </td>
	                </tr>
	            </table>
	        </div>
	    </div>
	</div>
    

</div><!-- id popup// -->
</form>

</body>
</html>