<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>롯데마트 입점상담</title>


<script language="JavaScript">

$(document).ready(function(){
	$("input.imageUpload").change(function() {
		var fileExtension = getFileExtension($(this).val()).toLowerCase();
		if(fileExtension != "xls" & fileExtension != "xlsx" & fileExtension != "ppt" 
				& fileExtension != "pptx" ) {
			alert("<spring:message code='epc.cst.alert.msg2'/>");
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
	$('.on.prodCd').val(val);
	$('.on.prodNm').val(val2);
	//alert($('.on.prodNm').val());
	$('input[name=product_pop].on').val(val2);
}



function productPopUpEstimate(oObject){
	$('.prod').removeClass('on');
	
	$(oObject).closest('.product').find('input').addClass('on');
	
	PopupWindow("<c:url value='/edi/comm/PEDPCOM0002Estimate.do'/>");
}

function addContents(){
	fnAddRow("addRow");
}



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


//PEDMCST0004Update
function doUpdate(){

	domInit();
	var result =  true;
	var result1 = true;
	
	//입력할 테이블이 없습니다.
	if($('#tb tr.rowData').length == 0){
		alert("<spring:message code='epc.cst.alert.msg3'/>");
		//return false;
		return;
	}

	//문서명을 입력하세요
	if($('#searchForm input[name=docNm]').val() == "") {
		alert("<spring:message code='epc.cst.alert.msg10'/>");
		$('#searchForm input[name=docNm]').focus();
		
		return;
	}
	
	//상품이 선택되지 않았을경우 [아래에서 for문 돌면서 체크 하기 떄문에 주석처리]
	/* if($('#searchForm input[name=esProdNm]').val()==''){
		
		result1 = false;
		//return false;
		return result1;
	} */
	
	$('#tb .required').each(function(){		
		if ($(this).val().replace(/\s/gi, '') == "") {			
			$(this).focus();
			result = false;						
		}
		return result;
	});
	
	//상품이 선택되지 않았을 경우
	if (!result) {
		alert("<spring:message code='epc.cst.alert.msg4'/>");
		return;
	}
	
	if($('#deleteFile').css("display") =="none" && $('#searchForm input[name=file]').val() ==""){
		alert("<spring:message code='epc.cst.alert.msg11'/>");
		return;
	}

	if($('#searchForm input[name=file]').val() != ""){
		alert("<spring:message code='epc.cst.alert.msg12'/>");
		$('#del_file').val("exist");		
	}
	
	if (!confirm("저장하시겠습니까?")) {
		return;
	}

	
	$('#searchForm').attr('action',"<c:url value='/edi/consult/NEDMCST0220Update.do'/>");
	$('#searchForm').submit();
}

function keyValid(obj){
	for( i=0 ; i<obj.value.length; i++){
		if(obj.value.charAt(i) <"0" || obj.value.charAt(i)>"9"){
			alert("<spring:message code='msg.common.error.noNum'/>");
			obj.focus();
			obj.value="";
			return;
		}
	}
}


function fileDownlord(val3){
	var form =  document.forms[0];

	form.pid_file.value=val3;
	form.action  = "<c:url value='/edi/consult/NEDMCST0220FileDownload.do'/>";
	form.submit();
	
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



function fileDelete(val,val2){
	var form =  document.forms[0];
	var tmp = document.getElementById("deleteFile");

	if(confirm("<spring:message code='epc.cst.alert.msg13'/>")){
		form.del_file.value="exist";
		form.del_file_path.value=val;
		form.del_file_nm.value=val2;
		tmp.style.display="none";
	}
}
</script>


</head>


<body>
	
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#"  enctype="multipart/form-data">
		
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<input type="hidden" name="pid" id="pid" value="${estListTop.pid }">
		<input type="hidden" name="file_name" />
		<input type="hidden" name="file_path" />
		<input type="hidden" name="pid_file" />
		<input type="hidden" id="del_file" name="del_file" />
		<input type="hidden" name="del_file_path" value="${estListTop.fileSeq }"/>
		<input type="hidden" name="del_file_nm" value="${estListTop.fileNm}" />
		
		
		<!-- 업로드 파일사이즈 크기 정의 -->
		<input type="hidden" name="fileSize" value="10485760" />
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.update'/></li>
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
							<html:codeTag objId="venCd" objName="venCd" width="150px;" selectParam="<c:out value='${estListTop.venCd}'/>" dataType="CP" comType="SELECT" formName="form"  />
						</td>
						<th><spring:message code='epc.cst.search.docNm'/></th>
						<td>
							<input type="text"  name="docNm" value="<c:out value='${estListTop.docNm}'/>" size="20" style="width:200px;"/>
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
						<li class="tit"><spring:message code='epc.cst.updatePerStore'/></li>
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
						<tbody id='tb'>
							<c:forEach var="estBottom" items="${estListBottom }">
								<tr class="rowData">
									<td align="center"><input type="text" data-col-name="esPc" name="estimationSheet.esPc" value="${estBottom.esPc}" size="10" id="pc_none" maxlength="4"/></td>
									<td align="left" class="product">
										<input class="required prod prodCd" type="hidden" data-col-name="esProdCd"  name="estimationSheet.esProdCd" value="${estBottom.esProdCd}"/>
										<input class="required prod prodNm" type="hidden" data-col-name="esProdNm" name="estimationSheet.esProdNm" value="${estBottom.esProdNm}" />
										<input type="text" class="prod" name="product_pop" value="${estBottom.esProdNm}"  readonly="true" size="27" style="width:130px;" />
										<a href="#" class="btn" onclick="javascript:productPopUpEstimate(this);"><span><spring:message code="button.common.choice"/></span></a>
									</td>
									<td align="center" ><input class="required" type="text" data-col-name="esStandard" name="estimationSheet.esStandard" value="${estBottom.esStandard}" size="10"  maxlength="7"/></td>
									<td align="center" ><input class="required" type="text" data-col-name="esPackType" name="estimationSheet.esPackType" value="${estBottom.esPackType}" size="7"  maxlength="5"/></td>
									<td align="center" ><input class="required" type="text" data-col-name="esGrade" name="estimationSheet.esGrade" value="${estBottom.esGrade}" size="7"  maxlength="5"/></td>
									<td align="center" ><input class="required" type="text" data-col-name="esPrice" name="estimationSheet.esPrice" value="${estBottom.esPrice}" size="10"  maxlength="10" onKeyUp="javascript:keyValid(this);"/></td>
									<td align="center" ><input class="required" type="text" data-col-name="esOrgin" name="estimationSheet.esOrgin" value="${estBottom.esOrgin}" size="7"  maxlength="7"/></td>
									<td align="center" ><input type="text" data-col-name="esDetail" name="estimationSheet.esDetail" size="10" value="${estBottom.esDetail}" maxlength="10" /></td>
									<td align="center" >
										<a href="#" class="btn" onclick="javascript:$(this).parent().parent().remove();"><span><spring:message code="button.common.delete"/></span></a>
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" >
						<tr id="deleteFile">
							<th align="left">&nbsp;&nbsp;&nbsp;<spring:message code='epc.cst.header.hCode16'/> :&nbsp;&nbsp;
							<a href="javascript:fileDownlord('${estListTop.pid}');">
							 <input type="hidden" name="hidden_file" value="${estListTop.fileSeq}">${estListTop.fileNm}</a>
							 <a href="#" class="btn" onclick="javascript:fileDelete('${estListTop.fileSeq}','${estListTop.fileNm}','${estListTop.pid}');"><span><spring:message code="button.common.delete"/></span></a>
							 </th>
						</tr>
						<tr>
							<th align="left">&nbsp;&nbsp;&nbsp;<spring:message code='epc.cst.header.hCode14'/> : <input type="file" class="imageUpload" value="" name="file" size="50"></th>
						</tr>
					</table>
					</div>
				</div>
			</div>
			</form>
		<br>	
		</div>
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
				<td align="center" ><input class="required" type="text" data-col-name="esOrigin" name="estimationSheet.esOrigin" size="7"  maxlength="7"/></td>
				<td align="center" ><input type="text" data-col-name="esDetail" name="estimationSheet.esDetail" size="10" maxlength="10" /></td>
				<td align="center" >
					<a href="#" class="btn" onclick="javascript:$(this).parent().parent().remove();"><span><spring:message code="button.common.delete"/></span></a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>