<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
	<link href='/css/epc/edi/consult/style.css' type="text/css" rel="stylesheet">
	<script language="JavaScript" src='/js/jquery/jquery-1.6.1.js'></script>
	<script type="text/javascript" language="javascript" src='/js/jquery/jquery-1.6.4.min.js' ></script>
	<script language="JavaScript" src='/js/epc/edi/consult/etc.js' ></script>
	<script language="JavaScript" src='/js/epc/edi/consult/common.js'></script>
	<link href='/css/epc/edi/consult/popup.css' type="text/css" rel="stylesheet">
	<link href='/css/epc/edi/consult/base.css' type="text/css" rel="stylesheet">
	<link href='/css/front/layer.css' type="text/css" rel="stylesheet">


<script>

//$(document).ready(function(){
//	$("#dong_nm").keypress(function(event) {
//		if(event.keyCode  == 13) {
//			doSearch(); 
//		}	
//	});
//});


function enterSearch() {
  if(window.event.keyCode == 13) {
	  doSearch();
  }
 }





function doSearch() {

	var form = document.forms[0];

	

	if(!form.dong_nm.value){

		alert("<spring:message code='msg.zipcode'/>");
		return;
	}
	
	
	form.action  = '${ctx}/edi/zip/PEDPZIP0001Select.do';
	form.submit();	
}



 function doZipe(postNO1,postNo2,addr){


	 opener.document.MyForm.zipNo1.value =postNO1;
	 opener.document.MyForm.zipNo2.value =postNo2;
	 opener.document.MyForm.supplyAddr1.value =addr;
	 window.close();
 }

</script>




</head>

<!-- 500*440 -->
<body id="popup">

<div id="pop_wrap" class="pop_w_500">
	<form name="searchForm" method="post" action="#">
	<div id="pop_head">
		<h1><img src="/images/epc/edi/consult/h1-pop-zipcode-search.gif"  alt="우편번호 찾기"></h1>
	</div>

	<div id="pop_contents" class="zipcode_view">
		<div class="box_gray_lt box_w_440 mt10"><div class="box_gray_rb"><div class="box_gray_rt"><div class="box_gray_lb">
			<fieldset class="f_w_340">
				<legend></legend>
				<dl class="input_info clearfloat">
					<dt>지역명</dt>
					<dd>
						<span class="input_txt"><span><input type="text"  name="dong_nm" id="dong_nm" onKeyDown="enterSearch();" value="${paramMap.dong_nm}" class="txt" style="width:150px;"></span></span>
						<span class="btn_gray btn_g_td_ls"><span><a href="#" onclick="doSearch();"><spring:message code='button.common.zipcode'/></a></span></span>
					</dd>
				</dl>
			</fieldset>
		</div></div></div></div>
		<p class="t_11 t_gray mt10"><span class="mr10">* 찾으시려는 주소의 동(읍/면/리)를 입력하세요.</span>예) 신천동, 역삼동</p>



		<c:if test="${fn:length(zipList) > 0 }">
		
		<!-- 우편번호 검색결과 리스트 -->
		<div class="scroll_title mt20">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<colgroup><col width="60px"><col width="*"></colgroup>
			<thead>
				<tr>
					<th width="60px">우편번호</th>
					<th width="*" class="t_center">주소</th>
				</tr>
			</thead>
			</table>
		</div>
		<div class="tb_h_common tb_h_c_scroll tb_scroll_1">
			<table cellpadding="0" cellspacing="0" border="0">
			<caption>우편번호 검색한 주소 내용</caption>
			<colgroup><col width="60px"><col width="360px"></colgroup>
			<thead>
				<tr>
					<th width="60px">우편번호</th>
					<th width="360px">주소</th>
				</tr>
			</thead>		
			<tbody>

				
				<c:forEach items="${zipList}" var="zipInfo" varStatus="index" >
				<tr>
					<td class="t_center">
						 ${zipInfo.POST_NO1}-${zipInfo.POST_NO2}
					</td>
					<td class="t_left_addr">
						<a href="#"  onclick="doZipe('${zipInfo.POST_NO1}','${zipInfo.POST_NO2}','${zipInfo.ALL_ADDR}');">${zipInfo.ALL_ADDR}</a>
					</td>
				</tr>
				</c:forEach>
				

				
				
				
			</tbody>
			</table>
		</div>
		
		</c:if>
		
		
		<!--// 우편번호 검색결과 리스트 -->
	</div>
	</form>
</div>

</body>
</html>



