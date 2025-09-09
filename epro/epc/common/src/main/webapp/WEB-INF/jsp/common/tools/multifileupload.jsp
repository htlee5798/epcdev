<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<% 
 /**
  * @Class Name : NoticeRegist.jsp
  * @Description : 게시물  생성 화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.03.24             최초 생성
  *
  *  @author 
  *  @since 
  *  @version 1.0
  *  @see
  *   
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/js/common/multifile.js" ></script>


<style type="text/css">
.noStyle {background:ButtonFace; BORDER-TOP:0px; BORDER-bottom:0px; BORDER-left:0px; BORDER-right:0px;}
  .noStyle th{background:ButtonFace; padding-left:0px;padding-right:0px}
  .noStyle td{background:ButtonFace; padding-left:0px;padding-right:0px}
</style>
<title></title>

<style type="text/css">
	h1 {font-size:12px;}
	caption {visibility:hidden; font-size:0; height:0; margin:0; padding:0; line-height:0;}
</style>

</head>

<body>

<form:form commandName="board" name="board" method="post" enctype="multipart/form-data" >

<input type="hidden" name="fileAtchPosblAt" value="100" />
<input type="hidden" name="posblAtchFileNumber" value="10" />
<input type="hidden" name="posblAtchFileSize" value="100" />


<div id="border">


     <table width="100%" border="0" cellpadding="0" cellspacing="1" class="generalTable">
	  <tr>
	    <th height="23"><spring:message code="cop.atchFile" /></th>
	    <td colspan="3">
            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
			    <tr>
			        <td><input name="file_1" id="commonFileUploader" type="file" /></td>
			    </tr>
			    <tr>
			        <td>
			        	<div id="commonFileList"></div>
			        </td>
			    </tr>
   	        </table>		      
	    </td>
	  </tr>
     <script type="text/javascript">
	     var maxFileNum = document.board.posblAtchFileNumber.value;
	     if(maxFileNum==null || maxFileNum==""){
	    	 maxFileNum = 3;
	     }     
		 var multi_selector = new MultiSelector( document.getElementById( 'commonFileList' ), maxFileNum );
		 multi_selector.addElement( document.getElementById( 'commonFileUploader' ) );			
     </script>	  
      

	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
	    <td height="10"></td>
	  </tr>
	</table>
	
  
</div>
</form:form>
</body>
</html>