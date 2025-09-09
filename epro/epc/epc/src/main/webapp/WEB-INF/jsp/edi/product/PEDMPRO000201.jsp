<%@ page contentType="text/html; charset=UTF-8"%>
<script language="javascript">
$(function() {

	
	
});

	function PopupWindow(pageName) {
		var cw=400;
		var ch=300;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	function doSearch() {

		var form = document.forms[0];
		form.action  = '<c:url value="/edi/order/PEDMORD00101Select.do"/>';
		form.submit();	
	}

	function storePopUp(){
		PopupWindow("/comm.do");
	}

	function productPopUp(){
		PopupWindow("/product.do");
	}

	function submitOnlineImage() {
		$("form[name=onlineImageForm]").submit();
	}

	function addImage() {
		var imageRowLength = $("table.bbs_search tr[name=image_row]").length;
		var newRow = imageRowLength+1;
		var newImageField = "${param.newProductCode }_"+imageRowLength;
		<c:if test="${empty onlineImageList }">
		newImageField = "front_"+imageRowLength;
		</c:if>
		if( imageRowLength == 4 ) {
			alert("이미지는 4개까지 추가가 가능합니다.");
			return;
		}
		var imageRow = "<tr id=image_row_"+newRow+" name=image_row>"+
						$("#template").val()+
					"</tr>";
		var submitRow = "<tr id=submit_row_"+newRow+" name=submit_row><td colspan=6 align=right style='padding-right:40px'><input type='file' class='onlineImage' name="+newImageField+" /> "+
		"<a href='javascript:deleteRow("+newRow+")' class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a> " +
		"</td></tr>";

		$("table.bbs_search tr[name=submit_row]:last").after(imageRow);
		$("table.bbs_search tr[name=image_row]:last").after(submitRow);
		//$("table.bbs_search").append(imageRow);
		//$("table.bbs_search").append(submitRow);
	} 


	function deleteRow(rowNum) {
		if( rowNum > 1) {
			//$("#image_row_"+rowNum).prev().remove();
			$("#image_row_"+rowNum).remove();
			$("#submit_row_"+rowNum).remove();
		} else {
			alert("모든 이미지 행을 삭제할 수는 없습니다.");
		}
	}

</script>
<% /* 
</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="onlineImageForm" id="uploadForm" action="${ctx}/edi/product/PEDMPRO000201.do" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="newProductCode" value="${newProduct.newProductCode}" />
		<input type="hidden" name="productDivnCode" value="${newProduct.productDivnCode}" />
		<input type="hidden" name="onOffDivnCode" value="${newProduct.onOffDivnCode}" />
		<div id="wrap_menu">
*/ %>		
			<!--	@ 검색조건	-->
			<div class="wrap_search" style="margin-top:5px">
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">온라인 필수 이미지</li>
						<li class="btn">
							<%--저장 --%>
							<%-- 
							<a href="/edi/product/PEDMPRO00203.do?newProductCode=${param.newProductCode }"><spring:message code="button.product.offimg.add"/></a>
							--%>
							<%--이미지 추가 --%>
							<a href="javascript:addImage();" class="btn"><span><spring:message code="button.common.product.image.add"/></span></a>
							<!--  a href="javascript:submitOnlineImage();"  class="btn"><span><spring:message code="button.common.save"/></span></a -->
						</li>
					</ul>
				</div>
				
				<div class="bbs_search">
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td colspan=6 height=40><b>주의사항</b> : 필수이미지는 500 x 500 사이즈로 등록해주세요.</td>
					</tr>
					<colgroup>
						<col  />
						<col />
						<col  />
						<col />
						<col />
						<col  />
					</colgroup>
					<tr>
						<th><span>1.</span> 500 x 500</th>
						<th>250 x 250</th>
						<th>160 x 160</th>
						<th>100 x 100</th>
						<th>75 x 75</th>
						<th>60 x 60</th>
					</tr>
					
					
					<tr id="image_row_1" name="image_row">
						<td align="center">
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500">
						</td>
						<td align="center">
							<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250">
						</td>
						
						<td align="center" >
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_160">
						</td>
						<td align="center">
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_100">
						</td>
						<td align="center">
							<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_75">
						</td>
						
						<td align="center" >
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_60">
						</td>
					</tr>
					<tr id="submit_row_1" name="submit_row">
						<td colspan="6" align="right" style="padding-right:40px">
							<input type="file" name="front" class='onlineImage' />&nbsp;&nbsp;
							<a href="javascript:deleteRow(1);" class=btn ><span><spring:message code="button.common.delete"/></span></a>
						</td>
					</tr>
					
					</table>
				</div>
			
				
				<textarea id="template" style="display:none;">
				
						<td align="center">
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500">
						</td>
						<td align="center">
							<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250">
						</td>
						
						<td align="center" >
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_160">
						</td>
						<td align="center">
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_100">
						</td>
						<td align="center">
							<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_75">
						</td>
						
						<td align="center" >
						<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_60">
						</td>
			
				</textarea>
			
			</div>
			
			<% /* 
		</div>
		</form>
	</div>



	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품</li>
					<li>신규상품등록</li>
					<li class="last">온라인 필수이미지</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
*/ %>