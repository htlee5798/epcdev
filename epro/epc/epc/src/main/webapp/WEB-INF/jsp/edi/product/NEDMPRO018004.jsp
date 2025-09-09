<%--
	Page Name 	: NEDMPRO018004.jsp
	Description : PB상품 파일 다운로드창
	Modification Information

	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	
--%>
<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: black;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: black;
	background-color: beige
}

.tg .tg-baqh {
	text-align: center;
	vertical-align: top
}
</style>
 </head>
 <script type="text/javascript">
   $(document).ready(function() {
   });
	
  function downloadReportInList(prodCd, seq) {
    $("#prodCd").val(prodCd);
 	$("#seq").val(seq);
 	 	
 	$("#packgeForDown").attr("action", "<c:url value='/edi/product/downloadReportPbProd.do'/>");
    $("#packgeForDown").submit();
   }
 </script>
 <body>
  <div id="popup">
  <!------------------------------------------------------------------ -->
  <!--    title -->
  <!------------------------------------------------------------------ -->
  <div id="p_title1">
    <h1>
      성적서 파일 리스트
	</h1>
	<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
  </div>
  <!-------------------------------------------------- end of title -- -->

  <!------------------------------------------------------------------ -->
  <!--    process -->
  <!------------------------------------------------------------------ -->
  <!--    process 없음 -->
  <br>
  <!------------------------------------------------ end of process -- -->
  <div class="popup_contents">				
  <div class="table_data">		
    <c:if test="${empty listPbReportFile}">
      등록된 성적서 파일이 없습니다.
    </c:if>
    <c:if test="${not empty listPbReportFile}">
      <table>
      <colgroup>
        <col style="width:10%">
        <col style="width:30%">
        <col style="width:60%">
      </colgroup>
      <tr>
        <th>순번</th>
        <th>인증날짜</th>
        <th>파일명</th>
        <tr><td height=2 bgcolor="f4383f" colspan=3></td></tr>
      </tr>
      <c:forEach items="${listPbReportFile}" var="listPbReportFile" varStatus="status">
      	<tr>
      	<td align="center"><c:out value="${status.count}"/></td> 
	    <td align="center"><c:out value="${listPbReportFile.uploadDt}"/></td>
	    <td align="center">
	      <a onClick='downloadReportInList("<c:out value="${listPbReportFile.prodCd}"/>", "<c:out value="${listPbReportFile.seq}"/>")'>
	       <c:out value="${listPbReportFile.srcFileNm}"/>
	      </a>
	    </td>
	    </tr>
      </c:forEach>
      </table>
    </c:if>
  </div>
  <br/>
  <!-----------------------------------------------end of 검색내역  -->
  </div>
  <!-- class popup_contents// -->
  </div>	
  <form id="packgeForDown" name="packgeForDown" method="post">
    <input type="hidden" id="prodCd" name="prodCd" />
	<input type="hidden" id="seq" name="seq"    />
  </form>
</body>
</html>