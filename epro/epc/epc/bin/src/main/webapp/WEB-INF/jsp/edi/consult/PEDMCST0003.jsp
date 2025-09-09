<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="/css/epc/edi/style_1024.css" />
<link rel="stylesheet" href="/css/epc/edi/sample.css"/>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script language="JavaScript" src="/js/jquery/jquery-1.6.1.js"></script>


<script type="text/javascript" src="/js/epc/paging.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>롯데마트 입점상담</title>

<c:choose>
	<c:when test="${file_valid eq 'not_file'}">
	<input type="hidden" id="back_values" value="${back_value }"/>
	<input type="hidden" id="back_pid" value="${back_pid }"/>
	<input type="hidden" id="back_e_text" value="${back_e_text }"/>
		<script>
			var back_val = document.getElementById("back_values");
			var back_pid = document.getElementById("back_pid");
			var back_e_text = document.getElementById("back_e_text");

			var first_split = back_val.value.split('@');




			function valueSetting(){
				var form = document.forms[0];

				var return_values = document.getElementById("back_values");
				var return_val_split = return_values.value.split('@');
				
				form.e_text.value=back_e_text.value;

				for (var i=0;i<return_val_split.length-1;i++) {
					var tmpVal = return_val_split[i].split(";");

					form.pc[i+1].value = tmpVal[0];
					form.product_id[i+1].value = tmpVal[1];
					form.product_name[i+1].value = tmpVal[2];
					form.product_pop[i+1].value = tmpVal[2];
					form.standard[i+1].value = tmpVal[3];
					form.pack[i+1].value = tmpVal[4];
					form.rating[i+1].value = tmpVal[5];
					form.estimate_price[i+1].value = tmpVal[6];
					form.origin[i+1].value = tmpVal[7];
					form.remark[i+1].value = tmpVal[8];
				}
			}
			alert("파일 용량은 10MB이상 올릴수 없습니다.");
		</script>
	</c:when>
	<c:otherwise>
	
	</c:otherwise>
</c:choose>
 
<script language="JavaScript">



$(document).ready(function(){
	$("input.imageUpload").change(function() {
		var fileExtension = getFileExtension($(this).val()).toLowerCase();
		if(fileExtension != "xls" & fileExtension != "xlsx" & fileExtension != "ppt" 
				& fileExtension != "pptx" ) {
			alert("엑셀(.xls/.xlsx) 및 파워포인트(.ppt/.pptx)만 가능");
			//resetImage($(this).get(0));
			$(this).val('');
			$(this).prev().val('');
		} else {
			$(this).prev().val($(this).val());
		}
	});
});

	
	
	
	
var obj01, obj02, obj03;
function PopupWindow(pageName) {
	var cw=400;
	var ch=440;
	var sw=screen.availWidth;
	var sh=screen.availHeight;
	var px=Math.round((sw-cw)/2);
	var py=Math.round((sh-ch)/2);
	window.open(pageName,"_productFind","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}


function receiveProduct(val,val2){

	var form = document.getElementById("searchForm");


	obj01.value=val;
	obj02.value=val2;
	obj03.value=val2;
}

function getFileExtension(filePath)
{
  var lastIndex = -1;
  lastIndex = filePath.lastIndexOf('.');
  var extension = "";

  if ( lastIndex != -1 ) {
    extension = filePath.substring( lastIndex+1, filePath.len );
  }
  else {
    extension = "";
  }

  return extension;
}

function productPopUpEstimate(oObject){

	
	var oTr = oObject.parentElement.parentElement;
	
	
	obj01 = oTr.children(1).children(0); 	
	obj02 = oTr.children(1).children(1);	
	obj03 = oTr.children(1).children(2);		
	
	PopupWindow("<c:url value='/edi/comm/PEDPCOM0002Estimate.do'/>");
}

function addContents(){
	fnAddRow("addTable");
}

function fnAddRow(tbName) {
	try {
		// 테이블을 찾아서 로우 추가
		var tbody = $('#' + tbName + ' #tb');
		
		var rows = tbody.find('tr').length-1;
		var newRow = tbody.find('tr:last').clone(true).appendTo(tbody);

		newRow.css("display","");
	
		fnControlInit(newRow, rows);

	}catch (e) {
		alert(e.Message);
	}
}

function fnControlInit(jRowobj, rowCnt){
	// input tag를 찾아서 value 지움
	jRowobj.find(':text').val('').each(function () {
		var id = this.id
		if (id) {
			this.id = this.id.split('_')[0] + '_' + rowCnt;
		}
	});
}


function doUpdate(){

	var form = document.forms[0];

	var pcs = document.getElementsByName("pc");
	var product_pops = document.getElementsByName("product_pop");
	var standards = document.getElementsByName("standard");
	var packs = document.getElementsByName("pack");
	var ratings = document.getElementsByName("rating");
	var estimate_prices = document.getElementsByName("estimate_price");
	var origins = document.getElementsByName("origin");
	var remarks = document.getElementsByName("remark");
	var product_ids = document.getElementsByName("product_id");
	var product_names = document.getElementsByName("product_name");
	
	var tmp="";

	if(pcs.length == "1"){
		alert("입력할 테이블이 없습니다. 상품추가 버튼을 클릭하세요.");
		return;
	}
	
	if(form.e_text.value == "") {
		alert("문서명을 입력하세요");
		form.e_text.focus();
		return;
	}
	
	for(var i = 0 ; i < pcs.length; i++){
		if(pcs[i].id =='pc_none') continue;

		if(pcs[i].value=='' || standards[i].value == '' || packs[i].value == '' || ratings[i].value == ''
			|| estimate_prices[i].value=='' || origins[i].value == ''){
			alert("비고란을 제외한 내용은  빈칸없이 전부 입력하셔야 합니다.");
			if(pcs[i].value==''){
				pcs[i].focus();
				return;
			}
			if(standards[i].value=='') {
				standards[i].focus();
				return;
			}
			if(packs[i].value==''){
				packs[i].focus();
				return;
			} 
			if(ratings[i].value==''){
				ratings[i].focus();
				return;
			} 
			if(estimate_prices[i].value==''){
				estimate_prices[i].focus();
				return;
			} 
			if(origins[i].value==''){
				origins[i].focus();
				return;
			} 
		}

		
		if(product_pops[i].value==''){
			alert("상품명을 선택하세요.");
			return;
		}
	}

	if(form.file.value==""){
		alert("첨부파일을 선택하세요.");
		return;
	}

	
	if(pcs.length>1){
		for(var i = 0 ; i < pcs.length; i++){

			if(pcs[i].id =='pc_none') continue;
			tmp += pcs[i].value + ";";
			tmp += product_ids[i].value + ";";
			tmp += product_names[i].value + ";";
			tmp += standards[i].value + ";";
			tmp += packs[i].value + ";";
			tmp += ratings[i].value + ";";
			tmp += estimate_prices[i].value + ";";
			tmp += origins[i].value + ";";
			
			if(remarks[i].value==""){
				tmp += "empty" + "@";
			}else{
				tmp += remarks[i].value + "@";
			}
		}
	}
	
	form.forward_values.value = tmp;

	form.action  = "<c:url value='/edi/consult/PEDMCST0003Update.do'/>";
	form.submit();
}


function keyValid(obj){
	for( i=0 ; i<obj.value.length; i++){
		if(obj.value.charAt(i) <"0" || obj.value.charAt(i)>"9")
		{
			alert("<spring:message code='msg.common.error.noNum'/>");
			obj.focus();
			obj.value="";
			return;
		}
	}
}




</script>


</head>


<body>
		
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#"  enctype="multipart/form-data">
		
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		
		<!-- 업로드 파일사이즈 크기 정의 -->
		<input type="hidden" name="file_size" value="10485760" />
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">견적서 등록</li>
						<li class="btn">
							<%-- <a href="#" class="btn" onclick="javascript:doUpdate();"><span><spring:message code="button.common.save"/></span></a> --%>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:100px;" />
						<col />
						<col style="width:100px;" />
						<col />
					</colgroup>
					<tr>
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form"  />
						</td>
						<th>문서명</th>
						<td>
							<input type="text"  name="e_text"  style="width:200px;"/>
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">상품별 견적 등록</li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:addContents();"><span><spring:message code="button.common.product.add"/></span></a>
						</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="addTable">
					<input type="hidden" name="forward_values" />
					<thead>
						<colgroup>
							<col style="width:90px;" />
							<col />
							<col style="width:80px;" />
							<col style="width:70px;" />
							<col style="width:70px;" />
							<col style="width:90px;" />
							<col style="width:70px;" />
							<col style="width:90px;" />
							<col style="width:60px;" />
						</colgroup>
						<tr>
							<th>PC</th>
							<th>상품</th>
							<th>규격</th>
							<th>포장형태</th>
							<th>등급</th>
							<th>견적가</th>
							<th>원산지</th>
							<th>비고</th>
							<th>삭제</th>
						</tr>
					</thead>
					
					<tbody id='tb'>
						<tr style="display:none">
							
							<td align="center"><input type="text" name="pc" size="10" id="pc_none" maxlength="4"/></td>
							<td align="left" class="product">
								<input type="hidden" name="product_id"/>
								<input type="hidden" name="product_name" />
								<input type="text"   name="product_pop" readonly="true" size="27" style="width:130px;" />
								<a href="#" class="btn" onclick="javascript:productPopUpEstimate(this);"><span><spring:message code="button.common.choice"/></span></a>
							</td>
							<td align="center" ><input type="text" name="standard" size="10"  maxlength="7"/></td>
							<td align="center" ><input type="text" name="pack" size="7"  maxlength="5"/></td>
							<td align="center" ><input type="text" name="rating" size="7"  maxlength="5"/></td>
							<td align="center" ><input type="text" name="estimate_price" size="10"  maxlength="10" onKeyUp="javascript:keyValid(this);"/></td>
							<td align="center" ><input type="text" name="origin" size="7"  maxlength="7"/></td>
							<td align="center" ><input type="text" name="remark" size="10" maxlength="10" /></td>
							<td align="center" >
								<a href="#" class="btn" onclick="javascript:$(this).parent().parent().remove();"><span><spring:message code="button.common.delete"/></span></a>
							</td>
						</tr>
					</tbody>
					</table>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" >
						<tr>
							<th>첨부 파일 : <input type="file"  class="imageUpload" value="" name="file" size="50"></th>
							
						</tr>
					</table>

					
					</div>

				</div>
			</div>
			
			</form>
		
		<br>	
		</div>
		
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li>홈</li>
						<li>협업</li>
						<li class="last">견적서등록</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->	
	</div>

</body>
</html>