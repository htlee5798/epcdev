<%--
- Author(s): jib
- Created Date: 2011. 09. 7
- Version : 1.0
- Description : 상품 상세이미지 화면
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PSCPPRD002105 -->
<head>

<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
$(document).ready(function(){
	//input enter 막기
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
	});

});

// 현재창 닫기
function doClose(){
	top.close();
}

//재요청
function doAprv(){
	if (!confirm('재요청하시겠습니까?')) {
		return;
	}
	//로딩바
	callAjaxByUpdate('#bosform', '<c:url value="/product/prodAprv.do"/>', '#bosform', 'POST');
	
}

function callAjaxByUpdate(form, url, target, Type) {

	var formQueryString = $('*', '#bosform').fieldSerialize();
	$.ajax({
		type: Type,
		url: url,
		data: formQueryString,
		success: function(json) {
			try {
						
			// 권한에러 메시지 처리 조건문 
			if(jQuery.trim(json) == "accessAlertFail") {
				alert('<spring:message code="msg.common.error.noAuth"/>');
			} else {
				if(jQuery.trim(json) == "fail") {
					alert("저장 실패하였습니다.");
				} else if(jQuery.trim(json) == "success") {
					alert("정상적으로 처리되었습니다.");
					top.opener.doSearch();
					top.close();
				} else {
					alert(jQuery.trim(json));
				}
			}
			} catch (e) {}
		},
			error: function(e) {
			alert(e);
		}
	});
}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="bosform" id="bosform" method="post">
<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
        <span class="logo"><img width="136" height="25" src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/logo_scm.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li class="last">상세이미지</li>			
		</ul>
     </div>
	 <!--  @process  //-->
	 <div class="popup_contents" >
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
				<ul class="tit">
					<li class="tit">상세이미지 조회</li>
					<li class="btn">
						<c:if test="${prodInfo.APRV_CD == '002'}">
						<a href="javascript:doAprv();"  class="btn" ><span>재요청</span></a>
						</c:if>					
						<a href="javascript:doClose();" class="btn" ><span>닫기</span></a>
					</li>
				</ul>
				<table width="100%" cellspacing="1" cellpadding="1" bgcolor=dddddd align=center>
                    	<input type="hidden" name="seq" id="seq" value="${prodInfo.SEQ}"/>
                    	<input type="hidden" name="prodCd" id="prodCd" value="${prodInfo.PROD_CD}"/>
                    	<input type="hidden" name="typeCd" id="typeCd" value="${prodInfo.TYPE_CD}"/>
                        <tr>
                        	<td>현재 상세이미지</td>
                        	<td>수정요청 상세이미지</td>                        	
                        </tr>
                        <tr bgcolor=white>
                        	<%String beforeDesc = (String)request.getAttribute("beforeDesc"); 
                        	  String afterDesc 	= (String)request.getAttribute("afterDesc"); %>
      						<td align=center valign=center >
								<div style="height:500px;width:500px;overflow-y:scroll;overflow-x:scroll;white-space:nowrap;"><%=beforeDesc %></div>
      						</td>
      						<td align=center valign=center>
      							<div style="height:500px;width:500px;overflow-y:scroll;overflow-x:scroll;white-space:nowrap;"><%=afterDesc %></div>
      						</td>
					</tr>
				</table>
		</div>				
	</div>
</div>	
</form>
</body>
</html>