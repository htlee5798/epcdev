
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil" %>

<% 

	String prodCd =SecureUtil.stripXSS((String)request.getParameter("prodCd"));
	String mdSrcmkCd =SecureUtil.stripXSS((String)request.getParameter("mdSrcmkCd"));
	String viewSize =(String)request.getParameter("viewSize");
	String nameSize =(String)request.getParameter("nameSize");	
	String size =(String)request.getParameter("size");
	String rowCnt =(String)request.getParameter("rowCnt");
	String url =SecureUtil.stripXSS((String)request.getParameter("url"));

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PSCMEXH010070 -->
<head>

<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
$(document).ready(function(){
	//input enter  막기
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
	});
	
});


// function CkImageVal(formName) {
// 	var oInput = event.srcElement;
// 	var fname  = oInput.value;
//     var formNm = formName;
// 	if((/(.jpg)$/i).test(fname)) {
// 			oInput.parentElement.children[0].src = fname;
// 	} else {	
// 			var f = eval("document."+formNm+".file1"); 
// 			var t = "<input type=\"file\" name=\"file1\" ";
// 			var c = "onChange=\"CkImageVal('";
// 			var e = "')\" style=\"width:400px\" size=\"20\">";
			
// 			f.outerHTML = (t+c+formNm+e);
//   		alert('이미지는 jpg 파일만 가능합니다.');
// 		return;
// 	}
// }

// 현재창 닫기
function doClose(){
	top.close();
}
</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품이미지</h1>
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
			<li class="last">인터넷상품관리</li>			
		</ul>
     </div>
	 <!--  @process  //-->
	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
				<ul class="tit">
					<li class="tit">상품이미지 조회 및 변경</li>
					<li class="btn">
						<a href="javascript:doClose();" class="btn" ><span>닫기</span></a>
					</li>
				</ul>


				<table width="100%" cellspacing="1" cellpadding="1" bgcolor=dddddd border=0 align=center>
                    	<tr bgcolor=white>
                    		<td align=center colspan="2" valign=center height=520 width=520><img src="<%=url%>"></td>
                    	</tr>
                    	<tr bgcolor=white>
                    		<td colspan="2" valign=center>
                    		파일명 : <%=url%>
                    		</td>
                    	</tr>
				</table>
		</div>				
	</div>
</div>	

<iframe name="submitframe" style="display:none"></iframe>

</body>
</html>