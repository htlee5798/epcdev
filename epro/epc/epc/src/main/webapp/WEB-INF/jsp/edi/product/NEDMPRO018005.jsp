<%--
	Page Name 	: NEDMPRO018005.jsp
	Description : 파트너사 등록안한 성적파일 수
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

   function doExcel(tbName) {
     var tbody = $('#' + tbName+ ' tbody');


	 var bodyValue = "";
	 bodyValue += "<CAPTION>업체별 성적서 업로드수 리스트<br>";
/* 	 bodyValue += "[순번]";
	 bodyValue += "[업체코드]";
	 bodyValue += "[파트너사명]";
	 bodyValue += "[업로드한 성적서수]";
	 bodyValue += "<br>"; */
	 bodyValue += "</CAPTION>";
	 bodyValue += tbody.parent().html();
	 
	 $("#inputForm input[id='staticTableBodyValue']").val(bodyValue);
	
	 $("#inputForm input[id='name']").val("statics");
	 $("#inputForm").attr("target", "_blank");
	 $("#inputForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
	 $("#inputForm").submit();
	
	 $("#inputForm").attr("target", "");
	 $("#inputForm").attr("action", "");
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
      <a href="#" class="btn" onclick="doExcel('listTbl');"><span><spring:message code="button.common.excel"/></span></a>
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

      <table id="listTbl">
      <colgroup>
        <col style="width:10%">
        <col style="width:10%">
        <col style="width:20%">
        <col style="width:10%">
        <col style="width:10%">
      </colgroup>
      <tr>
        <th>순번</th>
        <th>업체코드</th>
        <th>파트너사명</th>
        <th>업로드한 성적서수</th>
        <th>최근 업로드일</th>
        <tr><td height=2 bgcolor="f4383f" colspan=5></td></tr>
      </tr>
      <c:forEach items="${entpCdListNotValidReport}" var="entpCdListNotValidReport" varStatus="status">
      	<tr>
      	  <td align="center"><c:out value="${status.count}"/></td> 
	      <td align="center"><c:out value="${entpCdListNotValidReport.entpCd}"/> </td>
	      <td align="center"><c:out value="${entpCdListNotValidReport.entpNm}"/></td>
	      <td align="center">
	          [ <c:out value="${entpCdListNotValidReport.cntValidReport}"/> / 
	          <c:out value="${entpCdListNotValidReport.cntTotalReport}"/> ]
	      </td>
	      <td align="center"><c:out value="${entpCdListNotValidReport.lastRegDt}"/></td>
	    </tr>	
      </c:forEach>
      </table>
      
      <form name="inputForm" id="inputForm" method="post" action="#">
        <input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue">
        <input type="hidden" name="name" id="name">	
      </form>
  </div>
  <br/>
  <!-----------------------------------------------end of 검색내역  -->
  </div>
  <!-- class popup_contents// -->
  </div>	
</body>
</html>