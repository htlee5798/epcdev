<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<% 
	String prodCd    = SecureUtil.stripXSS((String)request.getParameter("prodCd"   ));
    String vendorId  = SecureUtil.stripXSS((String)request.getParameter("vendorId" ));
	String mdSrcmkCd = SecureUtil.stripXSS((String)request.getParameter("mdSrcmkCd"));
	String viewSize  = SecureUtil.stripXSS((String)request.getParameter("viewSize" ));
	String nameSize  = SecureUtil.stripXSS((String)request.getParameter("nameSize" ));	
	String size      = SecureUtil.stripXSS((String)request.getParameter("size"     ));
	String rowCnt    = SecureUtil.stripXSS((String)request.getParameter("rowCnt"   ));
	String url       = SecureUtil.stripXSS((String)request.getParameter("url"      ));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >

<c:import url="/common/commonHeadPopup.do" />

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		//input enter 막기
		$("*").keypress(function(e){
	        if(e.keyCode==13) return false;
		});
        $('#close').click(function() {
            top.close();
        });
	});
	
	//업로드
	function doUpload(f)
	{
		if (!f.file1.value)
		{
		    alert('선택된 이미지가 없습니다.');
		    return;
		}
	
		if ( confirm("선택된 상품이미지를 업로드 하시겠습니까?") )
		{
			f.submit();
		}
	}
	
	function CkImageVal(formName)
	{
		var oInput = event.srcElement;
		var fname  = oInput.value;
	    var formNm = formName;
	    
		if ((/(.jpg)$/i).test(fname))
		{
		    oInput.parentElement.children[0].src = fname;
		}
		else
		{	
			var f = eval("document."+formNm+".file1"); 
			var t = "<input type=\"file\" name=\"file1\" ";
			var c = "onChange=\"CkImageVal('";
			var e = "')\" style=\"width:400px\" size=\"20\">";
						
			f.outerHTML = (t+c+formNm+e);
			alert('이미지는 jpg 파일만 가능합니다.');
			return;
		}
	}
</script>
</head>

<body>
<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품이미지</h1>
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
					<li class="tit">상품이미지 조회 및 변경</li>
					<li class="btn">
						<a href="#" class="btn" id="close"><span><spring:message code="button.common.close"/></span></a>
					</li>
				</ul>

				<table width="100%" cellspacing="1" cellpadding="1" bgcolor=dddddd border=0 align=center>
                    <FORM name="filesForm" action="<c:url value="/product/updateProductImage.do"/>"
                        method="post" enctype="multipart/form-data" target="submitframe">
                        
                        <input type="hidden" id="mode"      name="mode"      value="updateOne"     />
                        <input type="hidden" id="mdSrcmkCd" name="mdSrcmkCd" value="<%=mdSrcmkCd%>"/>
                        <input type="hidden" id="prodCd"    name="prodCd"    value="<%=prodCd%>"   />
                        <input type="hidden" id="vendorId"  name="vendorId"  value="<%=vendorId%>" />
                        <input type="hidden" id="viewSize"  name="viewSize"  value="<%=viewSize%>" />
                        <input type="hidden" id="nameSize"  name="nameSize"  value="<%=nameSize%>" />
                        <input type="hidden" id="size"      name="size"      value="<%=size%>"     />
                        <input type="hidden" id="rowCnt"    name="rowCnt"    value="<%=rowCnt%>"   />
                    	
                    	<tr bgcolor=white>
                    		<td align=center colspan="2" valign=center height=520 width=520><img src="<%=url%>"></td>
                    	</tr>
                    	<tr bgcolor=white>
                    		<td colspan="2" valign=center>
                    		파일명 : <%=url%><br>표시사이즈 : <%=viewSize%> / 실사이즈 : <b><%=size%></b> / 파일순번 : <%=rowCnt%>
                    		</td>
                    	</tr>
                    	<tr align=center height=24 bgcolor=white>
                            <td height="30"> 이미지 등록 <input type="file" size="20" name="file1" onChange="CkImageVal('filesForm')" style="width:400px"></td>
                            <td><li class="btn"><a href="javascript:doUpload(document.filesForm)" class="btn" ><span><spring:message code="button.common.save"/></span></a></li></td>
                    	</tr>	
                    </FORM>
				</table>
		</div>				
	</div>
</div>	

<iframe name="submitframe" style="display:none"></iframe>

</body>
</html>