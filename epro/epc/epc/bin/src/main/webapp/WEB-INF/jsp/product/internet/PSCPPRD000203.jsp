<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"            %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script language="javascript" type="text/javascript" src="/js/common/json.common.js"></script>
<script language="javascript" type="text/javascript" src="/js/common/json2.js"></script>
<script type="text/javascript" >
</script>
</head>

<body>
<form name="popupForm" method="post">
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>YouTube</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
	 
	 <!-- list -->
	 <div >	
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<table cellpadding="0" cellspacing="0" border="0" align="center">
				<tr>
					<td>
<%-- 						<embed src="${prodUrl}" width=640 heighe=500></embed> --%>
						
<%-- 						<embed src="http://img.lottemart.com/images/epc/popup/Wildlife.wmv" ></embed>
<%-- 						<ifrmae width="560" height="315" src="${prodUrl}" frameborder="0" allowfullscreen></ifrmae> --%>
							<embed src="${prodUrl}" width="580" height="400" ></embed>
							
					</td>
				</tr>
			</table>
			</div>
	</div>
</form>
</body>
</html>