<%--
- Author(s): ksjeong
- Created Date: 2011. 08. 11
- Version : 1.0
- Description : 배송정보 ( 상품 )

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PBOMPRD0039 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$("#save").click(function(){
		save();
		
	});
});
function save() {
	var formData = $("form[name='dataForm']").serialize();
	var surl = '<c:url value="/product/updateTprMpic.do"/>';
 	$.ajax({
 		url: surl,
 		dataType : 'JSON',
 		data : formData,
 		type: 'POST',
 		success : function(result) {
 			if (result.result ) {
 				alert(result.resultMsg);
 				self.close();
 			}else{
 				alert(result.errMsg);
 						
 			}
 		},
 		error: function(e) {
			alert(e);
		}
 	});
}
</script>
</head>
<body>
<form name="dataForm" id="dataForm" method="POST">
	<input type="hidden" name="prodCd"    value="<c:out value='${tprMpicInfo.PROD_CD}' />"/>
	<input type="hidden" name="cid"       value="<c:out value='${tprMpicInfo.CID}' />">
	<input type="hidden" name="mpicSeq"   value="<c:out value='${tprMpicInfo.MPIC_SEQ}' />"/>
	<input type="hidden" name="accessKey" value="<c:out value='${tprMpicInfo.ACCESS_KEY}' />"/>
	<div id="wrap_menu" style="width:556px;">
		<div class="bbs_list">
			<ul class="tit">
               <li class="tit">동영상 등록</li>
               <li class="btn">
                   <a href="javascript:window.close();" class="btn" ><span>닫기</span></a>
                   <a href="#" class="btn" id="save"><span>저장</span></a>
               </li>
           	</ul>
           	<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
	           <col width="20%" />
		       <col width=" " />
			
		        <tr>
		        	<th>제목</th>
		        	<td><input type="text" name="title" value="<c:out value='${ tprMpicInfo.TITLE }' />"></td>
		        </tr>
		        <tr>
		        	<th>내용</th>
		        	<td><input type="text" name="content" value="<c:out value='${ tprMpicInfo.CONTENT }' />"></td>
		        </tr>
		        <tr>
		        	<th>사용여부</th>
		        	<td>
		        		<input type="radio" name="useYn" value="Y" class="choice" <c:if test="${ 'Y' eq tprMpicInfo.USE_YN }">checked="checked"</c:if>>사용
		        		<input type="radio" name="useYn" value="N" class="choice" <c:if test="${ 'N' eq tprMpicInfo.USE_YN }">checked="checked"</c:if>>미사용
		        	</td>
		        </tr>
		        <tr>
		        	<th>동영상 키</th>
		        	<td><c:out value="${ tprMpicInfo.MPIC_URL }" /></td>
		        </tr>
           	</table>
    	</div>
	</div>
</form>
</body>
</html>