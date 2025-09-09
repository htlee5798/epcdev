<%--
	Page Name 	: NEDMPRO018002.jsp
	Description : EDI 메인화면 성적서 업로드 안내창
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
 	function test(){
 		var url = '<c:url value="/edi/product/NEDMPRO0180.do"/>';
 		window.location.herf = url;
 	}
 </script>
 <body>
  <div id="popup">
  <!------------------------------------------------------------------ -->
  <!--    title -->
  <!------------------------------------------------------------------ -->
  <div id="p_title1">
  <h1>
    필요 성적서 업로드 개수 확인
  </h1>
  <span class="logo">
    <img src="/images/epc/popup/logo_pop.gif"alt="LOTTE MART" />
  </span>
  </div>
  <!-------------------------------------------------- end of title -- -->

  <!------------------------------------------------------------------ -->
  <!--    process -->
  <!------------------------------------------------------------------ -->
  <!--    process 없음 -->
  <br>
  <!------------------------------------------------ end of process -- -->
  <div class="popup_contents">				
  <!-- -------------------------------------------------------- -->
  <!-- 입고거부 상품 내역  -->
  <!-- -------------------------------------------------------- -->
    <div class="table_data">		
      ※ 필요한 상품의 성적서 <a href='<c:url value="/edi/product/NEDMPRO0180.do"/>' target="contentFram"><U>'${countNotValidPbProdReport}'개 업로드 필요</U></a></p>
    </div>
	<br/>
	<div>
	  동일 식품유형 자가품질검사 성적서 첨부
	</div>
	</br>
	  <font color=red style='font-size:13px; font-weight:bold;'>
	  성적서 업로드 누락된 경우 별도 조치 예정
	  </font>
	<!-----------------------------------------------end of 검색내역  -->
	</div>
		<!-- class popup_contents// -->
  </div>	
  </body>
</html>