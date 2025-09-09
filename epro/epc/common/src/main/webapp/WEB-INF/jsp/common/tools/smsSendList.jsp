<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% 
 /**
  * @Class Name : smsSendList.jsp
  * @Description : 파일 목록화면
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2011.03.26            최초 생성
  *
  *  @since 2011.03.26
  *  @version 1.0 
  *  @see
  *  
  */
%>
<script type="text/javascript">
	
</script>


      
<!-- <form name="fileForm" action="" method="post" >  -->
<!-- </form>  -->
	<table>
      	<c:forEach var="ltsmsVO" items="${smsList}" varStatus="status">
	      <tr>
	       <td>
			 <c:out value="${ltsmsVO.SERIALNO}"/>       
	       </td>
	      </tr>  
        </c:forEach>
      </table>
