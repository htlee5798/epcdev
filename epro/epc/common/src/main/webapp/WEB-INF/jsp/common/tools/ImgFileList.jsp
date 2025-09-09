<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% 
 /** 
  * @Class Name : ImgFileList.jsp
  * @Description : 이미지 파일 조회화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2011.08.31            최초 생성
  *
  *  @since 2011.08.31
  *  @version 1.0
  *  @see
  *  
  */
%>
	<table>
      	<c:forEach var="fileVO" items="${fileList}" varStatus="status">
	      <tr>
	      	<td></td>
	      </tr>      	      		
	      <tr>
	       <td>
	       		<img src='<c:url value='/files/getImage.do'/>?atchFileId=<c:out value="${fileVO.atchFileId}"/>&fileSn=<c:out value="${fileVO.fileSn}"/>'  width="640" />
	       </td>
	      </tr>
	      <tr>
	      	<td></td>
	      </tr>  
        </c:forEach>
      </table>
