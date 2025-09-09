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



$(function() {
	$("#cityNm").change(function() {
		cityCodeChange($(this).val());
	});	

});

 
function cityCodeChange(str){
		$.getJSON("<c:url value='/edi/zip/PEDPZIP0001SelectCode.do'/>",
				                //'${ctx}/edi/zip/PEDPZIP0001Select.do';
				
					{
						cityNm: str
					}, function(j){
		      var options = '';

		      if(j.length=='0'){
		    	  options = '<option value=all>전체</option>';
		      }else{
		    	  for (var i = 0; i < j.length; i++) {
					    if(i == 0) {
					    	options += '<option value="">전체</option>';
						}  
					    
					    if("${paramMap.guNm}" == j[i].detailName){
					    	options += '<option value="' + j[i].detailName + '"selected >' + j[i].detailName + '</option>';
					    }else{
					    	options += '<option value="' + j[i].detailName + '">' + j[i].detailName + '</option>';
					    }
			      }
		      }		      
		      $("#guNm option").remove();
		      $("#guNm").html(options);
		    });

}


function doSearch() {

	var form = document.forms[0];

// 	alert(form.teamGroupCode.value);
// 	alert(form.l1GroupCode.value);
// 	alert(form.dong_nm.value);
	
 	if(form.cityNm.value=='all'){
 		alert("시/도를 선택하여 주세요"); 	
 		return;	
 	}
	//alert("test");
	

	if(!form.streetNm.value){
		//alert("<spring:message code='msg.zipcode'/>");
		alert("도로명을 입력해 주시기 바랍니다.");
		form.streetNm.focus();
		return;
		
	}
	
	
	form.action  = '${ctx}/edi/zip/PEDPZIP0001SelectNew.do';
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
<font color='white'><b>/PEDPZIP0002.jsp</b></font>

<div id="pop_wrap" class="pop_w_500">
	<form name="searchForm" method="post" action="#">
	<div id="pop_head">
		<h1><img src="/images/epc/edi/consult/h1-pop-zipcode-search.gif"  alt=" 찾기"></h1>
	</div>

	<div id="pop_contents" class="zipcode_view">
		<div class="box_gray_lt box_w_440 mt10">
		<div class="box_gray_rb">
		<div class="box_gray_rt">
		<div class="box_gray_lb">
			<fieldset class="f_w_340">
				<legend></legend>
				<dl class="input_info clearfloat">
				<dd>
						<td>
						시/도						
							<select id="cityNm" name="cityNm" >
										<option value="all">선택</option>
										<option value="강원" <c:if test="${paramMap.cityNm eq '강원' }">selected</c:if>>강원</option>
										<option value="경기" <c:if test="${paramMap.cityNm eq '경기' }">selected</c:if>>경기</option>
										<option value="경남" <c:if test="${paramMap.cityNm eq '경남' }">selected</c:if>>경남</option>
										<option value="경북" <c:if test="${paramMap.cityNm eq '경북' }">selected</c:if>>경북</option>
										<option value="광주" <c:if test="${paramMap.cityNm eq '광주' }">selected</c:if>>광주</option>
										<option value="대구" <c:if test="${paramMap.cityNm eq '대구' }">selected</c:if>>대구</option>
										<option value="대전" <c:if test="${paramMap.cityNm eq '대전' }">selected</c:if>>대전</option>
										<option value="부산" <c:if test="${paramMap.cityNm eq '부산' }">selected</c:if>>부산</option>
										<option value="서울" <c:if test="${paramMap.cityNm eq '서울' }">selected</c:if>>서울</option>
										<option value="세종" <c:if test="${paramMap.cityNm eq '세종' }">selected</c:if>>세종</option>
										<option value="울산" <c:if test="${paramMap.cityNm eq '울산' }">selected</c:if>>울산</option>
										<option value="인천" <c:if test="${paramMap.cityNm eq '인천' }">selected</c:if>>인천</option>
										<option value="전남" <c:if test="${paramMap.cityNm eq '전남' }">selected</c:if>>전남</option>
										<option value="전북" <c:if test="${paramMap.cityNm eq '전북' }">selected</c:if>>전북</option>
										<option value="제주" <c:if test="${paramMap.cityNm eq '제주' }">selected</c:if>>제주</option>
										<option value="충남" <c:if test="${paramMap.cityNm eq '충남' }">selected</c:if>>충남</option>
										<option value="충북" <c:if test="${paramMap.cityNm eq '충북' }">selected</c:if>>충북</option>
								</select>&nbsp;&nbsp;&nbsp;									
						</td>	
							
						<th><span >  시/군/구 </th>
						<td>
						 	<select id="guNm" name="guNm">
							<option >전체</option>
							</select>
						</td>
						
					</dd>
						
					<dd>*도로명
						<span class="input_txt">
							<span>
								<input type="text"  name="streetNm" id="streetNm" maxlength="20" onKeyDown="enterSearch();" value="${paramMap.streetNm}" class="txt" style="width:100px;">
							</span>
						</span>
						
					</dd>
					
					<dd>건물명
					
						<span class="input_txt">
							<span>
								<input type="text"  name="apartNm" id="apartNm" maxlength="20" onKeyDown="enterSearch();" value="${paramMap.apartNm}" class="txt" style="width:100px;">
							</span>
						</span>
				        <span class="btn_gray btn_g_td_ls"><span><a href="#" onclick="doSearch();">
						<spring:message code='button.common.search'/></a></span></span>
				
						</dd>
						
				</dl>
			</fieldset>
		</div></div></div></div>
		<p class="t_11 t_gray mt10"><span class="mr10">* 찾으시려는 도로명을 입력하세요.</span>예) 올릭픽로</p>



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
						<a href="#"  onclick="doZipe('${zipInfo.POST_NO1}','${zipInfo.POST_NO2}','${zipInfo.ALL_ADDR}');">${zipInfo.ALL_ADDR} <br> (${zipInfo.OLD_ALL_ADDR})</a>
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

<script>

	 
		//teamCodeChange('경기');
	//	alert("111");
		
		
	//	alert("222");
	 	//alert(kk);
	 //	alert("333");
		//alert($('#cityNm option:selected').val());
		
		cityCodeChange($('#cityNm option:selected').val());
		
	 //	teamCodeChange();
	 	
	 	
	</script>
	
</body>
</html>



