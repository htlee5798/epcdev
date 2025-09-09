<%--
- Author(s): dongjun
- Created Date: 2012. 12. 28
- Version : 1.0
- Description : 배송점 정보

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

<script type="text/javascript">
$(document).ready(function(){	
    // 닫기버튼 클릭
    $('#close').click(function() {
        window.close();    
    });
}); // end of ready
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form id="dFrm" name="dFrm" method="post">
<div id="popup">
    <!--  @title  -->    
    <div id="p_title1">
    	<h1>배송점 정보</h1>
    </div>   
    <!--  @process  -->
<!--    		<div> -->
<!-- 			<ul> -->
				
<!-- 			</ul> -->
<!--      	</div> -->
<br>
		<!--  @process  //-->
    <!--  @title  //-->
    <div class="popup_contents">
    	<!--  @작성양식 2 -->
	    <div class="bbs_search3">
	        <ul class="tit">
				<li class="tit">배송점 정보</li>
	            <li class="btn">                        
	                <a href="#" class="btn" id="close"><span><spring:message code="button.common.close" /></span></a>             
	            </li>
	        </ul>        
	        <table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col style="width:12%" />
					<col style="width:16%" />
				</colgroup>
				<tr>
					<th>점포명</th>
					<td>
						${paramInfo.STR_NM}
					</td>
				</tr>
				<tr>
					<th> 전화번호</th>
					<td>
						${paramInfo.PICK_ENTP_TEL_NO1}-${paramInfo.PICK_ENTP_TEL_NO2}-${paramInfo.PICK_ENTP_TEL_NO3}
					</td>
				</tr>
				<tr>
					<th>담당자명</th>
					<td>
						${paramInfo.PICK_ENTP_UTAKMN_NM}
					</td>
				</tr>
				<tr>
					<th>담당자전화번호</th>
					<td>
						${paramInfo.PICK_ENTP_UTAKMN_TEL1}-${paramInfo.PICK_ENTP_UTAKMN_TEL2}-${paramInfo.PICK_ENTP_UTAKMN_TEL3}
					</td>
				</tr>
	    	</table>
	    </div>    
    </div>
</div>
</form>
</body>
</html>