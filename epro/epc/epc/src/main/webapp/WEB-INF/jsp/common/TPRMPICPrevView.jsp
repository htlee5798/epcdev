<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script type="text/javascript">
$(document).ready(function(){
});
</script>

</head>
<body>
<div id="wrap_menu" style="width:556px;">
	<div class="bbs_list">
           <ul class="tit">
               <li class="tit">동영상 미리보기</li>
               <li class="btn">
                   <a href="javascript:window.close();" class="btn" ><span>닫기</span></a>
               </li>
           </ul>

           <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td><iframe width="550" height="338" src="http://play.wecandeo.com/video/v/?key=<c:out value="${key}"/>&mode=html5&auto=true" frameborder="0" allowfullscreen></iframe></td>
			</tr>
		</table>
	</div>	
</div>
</body>
</html>