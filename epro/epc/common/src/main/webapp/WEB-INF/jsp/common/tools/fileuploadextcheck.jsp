<%--
  * 시스템 	: 동반성장
  * 설명		: 동반성장 롯데마트에 바란다 등록
  * 제작자 	: Administrator
  * 작성일 	: 
--%>
<%@ page 	language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" 			uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib 	prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib 	prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib  prefix="decorator"	uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib  prefix="page"		uri="http://www.opensymphony.com/sitemesh/page" %>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<table width="782" border="0" cellspacing="0" cellpadding="0">
<form id="myform" name="praiseform" method="post" 
action="<c:url value='/usr/community/PraiseInsert.do'/>"  enctype="multipart/form-data">


              <tr> 
                <td class="headcolor">내용</td>
                <td class="title"><span class="my_error_display"></span>
                  <input id="description" name="description" value="" type="hidden" />
                  <iframe id="description___Frame" frameborder="0" height="300" scrolling="no" width="100%" src="/fckeditor/editor/fckeditor.html?InstanceName=description&amp;Toolbar=Default"> </iframe>
                </td>
            </tr>
            <tr>
            <td class="headcolor">관련자료</td>
            <td colspan="3" class="title" id="files">
               -10M이상은 첨부 불가합니다.<br>
 -xls, xlsx, ppt, pptx, doc, docx, jpg, gif, png, bmp, pdf 확장자 파일만 첨부 가능합니다.<br>
            <input type='file' name='file0' id='file0'  style="width:100%;" onchange="checkFile(this)"/>
             </td>
          </tr>
          <input name="type" type="hidden" value="genericFileMulti" size="60" />
		<input name="targetDeptNm" id="targetDeptNm" type="hidden" value="" />
		<input name="productImageHidden" id="productImageHidden" type="hidden" value="" />


<input name="categoryCd" type="hidden" value="C0005" />
</form>
</table>
	
 
<script> 

function checkFile(t){
	  t.select();
	   var RealPath = document.selection.createRange().text;
	  $('#productImageHidden').val(RealPath);
	  $.ajax({
			type : "POST",
			url : "/main/fileSize.do",
			data : "size=" + $('#productImageHidden').val(),
			success : function(data,msg) {
				 var maxSize = 10240000 ;  // 최대 업로드 사이즈 = 10240000 (10메가)
				   if(data == 1){
				    alert("등록가능한 파일 타입이 아닙니다.");
				    t.parentElement.innerHTML = "<input type=\"file\" name=\"file"+$no+"\" style=\"width:100%;\" onchange=\"checkFile(this)\" />";
				   }else if(data == 2){
				    alert("파일업로드 허용 용량 10메가를 초과하였습니다!");
				    t.parentElement.innerHTML = "<input type=\"file\" name=\"file"+$no+"\" style=\"width:100%;\" onchange=\"checkFile(this)\" />";
				   }else if(data==0){

				   }
			}
		});
	 }
</script>
