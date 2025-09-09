<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<script type="text/javascript" src="/js/epc/paging.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>견적서 등록</title>

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

			alert("<spring:message code='epc.cst.alert.msg1'/>");
		</script>
	</c:when>
	<c:otherwise>
	
	</c:otherwise>
</c:choose>
 
<script language="JavaScript">



$(document).ready(function(){
	$("input.imageUpload").change(function() {
		var fileExtension = getFileExtension($(this).val()).toLowerCase();
		
		if (fileExtension.length > 0) {
			if(fileExtension != "xls" & fileExtension != "xlsx" & fileExtension != "ppt" & fileExtension != "pptx" ) {
				alert("<spring:message code='epc.cst.alert.msg2'/>");
				//resetImage($(this).get(0));
				$(this).val('');
				$(this).prev().val('');	
			} else {
				$(this).prev().val($(this).val());
			}
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



function getFileExtension(filePath){
	var lastIndex = -1;
	lastIndex = filePath.lastIndexOf('.');
	var extension = "";

	if ( lastIndex != -1 ) {
		extension = filePath.substring( lastIndex+1, filePath.len );
	}else {
		extension = "";
	}
	return extension;
}

function productPopUpEstimate(oObject){
	$('.prod').removeClass('on');	
	$(oObject).closest('.product').find('input').addClass('on');
	
	/*As-IS랑 동일한 URL 호출 공통으로 PRODUCT 테이블에서 상품검색함*/
	PopupWindow("<c:url value='/edi/comm/PEDPCOM0002Estimate.do'/>");
	
}

function receiveProduct(val,val2){
	$('.on.prodCd').val(val);
	$('.on.prodNm').val(val2);
	$('input[name=product_pop].on').val(val2);
}


/* 상품추가 */
function addContents(){
	fnAddRow("addRow");
}

/* 테이블 복사 */
function fnAddRow(tbName) {
	try {
		// 테이블을 찾아서 로우 추가
		var hiddenDom = $('#' + tbName).find('tr.rowData');
		var cloneDom = hiddenDom.clone(true);
		$("#tb").append(cloneDom)
	}catch (e) {
		alert(e.Message);
	}
}


function domInit()	{
	$('#tb tr.rowData').each(function(index){
		var $estimationSheet = $(this).find('input[name *=estimationSheet]');
		$estimationSheet.each(function(){
			this.name = 'estimationSheet['+index+'].' +$(this).data('colName');
		});
	});	
}

/* 저장 */
function doUpdate(){
	domInit();

	if($('#tb tr.rowData').length == 0){
		alert("<spring:message code='epc.cst.alert.msg3'/>");
		return;
	}
	
	if($('#searchForm input[name=docNm]').val() == "") {
		alert("<spring:message code='epc.cst.alert.msg10'/>"); 
		$('#searchForm input[name=docNm]').focus();		
		return;
	}
	$('#tb .required').each(function(){
		if($(this).val() == '') {
			alert("<spring:message code='epc.cst.alert.msg4'/>");
			$(this).focus();
			return;
		}
	});

	if($('#searchForm input[name=esProdNm]').val()==''){
		alert("<spring:message code='epc.cst.alert.msg5'/>");
		return;
	}
	
	if($('#searchForm input[name=file]').val()==""){
		alert("<spring:message code='epc.cst.alert.msg6'/>");
		return;
	}
		
	$('#searchForm').attr('action' ,"<c:url value='/edi/consult/NEDMCST0210Update.do'/>" );
	$('#searchForm').submit();	
}


function keyValid(obj){
	for(var i=0 ; i < obj.value.length; i++){
		if(obj.value.charAt(i) < "0" || obj.value.charAt(i) > "9")
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
		<form id="searchForm" name="searchForm" method="post" action="#"  enctype="multipart/form-data">
		
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" > 
		
		<!-- 업로드 파일사이즈 크기 정의 -->
		<input type="hidden" name="fileSize" value="10485760" />
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.create'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doUpdate();"><span><spring:message code="button.common.save"/></span></a>
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
						<th><spring:message code='epc.cst.search.entpCd'/></th>
						<td>
							<html:codeTag objId="venCd" objName="venCd" width="150px;" selectParam="<c:out value='${param.venCd}'/>" dataType="CP" comType="SELECT" formName="form"  />
						</td>
						<th><spring:message code='epc.cst.search.docNm'/></th>
						<td>
							<input type="text"  name="docNm"  style="width:200px;"/>
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
						<li class="tit"><spring:message code='epc.cst.createPerStore'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:addContents();"><span><spring:message code="button.common.product.add"/></span></a>
						</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
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
								<th><spring:message code='epc.cst.header.hCode1'/></th>
								<th><spring:message code='epc.cst.header.hCode2'/></th>
								<th><spring:message code='epc.cst.header.hCode3'/></th>
								<th><spring:message code='epc.cst.header.hCode4'/></th>
								<th><spring:message code='epc.cst.header.hCode5'/></th>
								<th><spring:message code='epc.cst.header.hCode6'/></th>
								<th><spring:message code='epc.cst.header.hCode7'/></th>
								<th><spring:message code='epc.cst.header.hCode8'/></th>
								<th><spring:message code='epc.cst.header.hCode9'/></th>
							</tr>
						</thead>
						<tbody id="tb">
						</tbody>
					</table>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" >
						<tr>
							<th><spring:message code='epc.cst.header.hCode10'/> : <input type="file"  class="imageUpload" value="" name="file" size="50"></th>
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
						<li><spring:message code='epc.cst.home'/></li>
						<li><spring:message code='epc.cst.cola'/></li>
						<li class="last"><spring:message code='epc.cst.create'/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->	
	</div>
<!-- 항목추가  -->
	<div id="addRow" style="display:none;">
		<table>
			<tr class="rowData">
				<td align="center"><input type="text" data-col-name="esPc" name="estimationSheet.esPc" size="10" id="pc_none" maxlength="4"/></td>
				<td align="left" class="product">
					<input class="required prod prodCd" type="hidden" data-col-name="esProdCd"  name="estimationSheet.esProdCd"/>
					<input class="required prod prodNm" type="hidden" data-col-name="esProdNm" name="estimationSheet.esProdNm" />
					<input type="text" class="prod" name="product_pop" readonly="true" size="27" style="width:130px;" />
					<a href="#" class="btn" onclick="javascript:productPopUpEstimate(this);"><span><spring:message code="button.common.choice"/></span></a>
				</td>
				<td align="center" ><input class="required" type="text" data-col-name="esStandard" name="estimationSheet.esStandard" size="10"  maxlength="7"/></td>
				<td align="center" ><input class="required" type="text" data-col-name="esPackType" name="estimationSheet.esPackType" size="7"  maxlength="5"/></td>
				<td align="center" ><input class="required" type="text" data-col-name="esGrade" name="estimationSheet.esGrade" size="7"  maxlength="5"/></td>
				<td align="center" ><input class="required" type="text" data-col-name="esPrice" name="estimationSheet.esPrice" size="10"  maxlength="10" onKeyUp="javascript:keyValid(this);"/></td>
				<td align="center" ><input class="required" type="text" data-col-name="esOrgin" name="estimationSheet.esOrgin" size="7"  maxlength="7"/></td>
				<td align="center" ><input type="text" data-col-name="esDetail" name="estimationSheet.esDetail" size="10" maxlength="10" /></td>
				<td align="center" >
					<a href="#" class="btn" onclick="javascript:$(this).parent().parent().remove();"><span><spring:message code="button.common.delete"/></span></a>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>