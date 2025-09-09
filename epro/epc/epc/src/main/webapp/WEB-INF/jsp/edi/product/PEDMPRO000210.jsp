<%--
- Author(s): 
- Created Date: 2011. 10. 20
- Version : 1.0
- Description : 온라인필수이미지 [include]

--%>

<%@ page contentType="text/html; charset=UTF-8"%>
	
<script language="javascript">


<c:if test="${not empty errorMsg}">
	alert("${errorMsg}");
</c:if>


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


	function viewProductInfo(newProductCode) {
		//location.href="${ctx}/edi/product/PEDMPRO000301.do?newProductCode="+newProductCode;
		location.href="${ctx}/edi/product/PEDMPRO0003.do";
	}

	
	function submitOnlineImage() {

		var tmp = document.getElementById("uploadFieldCount");
		var uploadFieldCount = $("form[name=onlineImageForm] input:file").length;
		tmp.value = uploadFieldCount;
	//alert(uploadFieldCount);
		var flagVar = 0;
		$("form[name=onlineImageForm] input:file").each(function() {
			if( $(this).val() == '') {
				flagVar ++;
			}
		});
		if( uploadFieldCount == flagVar) {
			alert("업로드할 이미지를 선택해주세요.");
			return;
		}
		
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
		var submitRow = "<tr id=submit_row_"+newRow+" name=submit_row><td colspan=6 align=right style='padding-right:40px'><input type='file' name="+newImageField+" /> "+
		"<a href='javascript:submitOnlineImage();' class=btn><span><spring:message code="button.common.save"/></span></a>&nbsp;&nbsp;"+
		"<a href='javascript:deleteRow("+newRow+")' class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a> " +
		"</td></tr>";

		$("table.bbs_search tr[name=submit_row]:last").after(imageRow);
		$("table.bbs_search tr[name=image_row]:last").after(submitRow);
		//$("table.bbs_search").append(imageRow);
		//$("table.bbs_search").append(submitRow);
	} 



	function deleteRow(rowNum) {
			if( rowNum > 1) {
				$("#image_row_"+rowNum).remove();
				$("#submit_row_"+rowNum).remove();
			} else {
				alert("모든 이미지 행을 삭제할 수는 없습니다.");
			}
		}			



	function deleteImageRow(deleteProductCode) {
		var imageRowLength = $("table.bbs_search tr[name=image_row]").length;
		if( imageRowLength > 1) {
			location.href="${ctx}/edi/product/PEDMPRO000203.do?newProductCode="+deleteProductCode;
		} else {
			alert("최소한 한개의 온라인 필수 이미지는 등록하셔야 합니다.");
		}
	}			
	
</script>
<% /*
</head>

<body>
	<div id="content_wrap">
	<div>
*/	 %>
		<!--	@ BODY WRAP START 	-->
		<form name="onlineImageForm" id="onlineUploadForm" action="${ctx}/edi/product/PEDMPRO000201.do" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="newProductCode" value="${param.newProductCode }" />
		<input type="hidden" name="productDivnCode" value="1" />
		<input type="hidden" name="onOffDivnCode" value="1" />
		<input type="hidden" name="uploadFieldCount" id="uploadFieldCount" />
		
		<input type="hidden" name="returnPage" value="PEDMPRO000301.do?newProductCode=<c:out escapeXml='false' value='${param.newProductCode}'/>" />
		
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><a name="onlineImageAnchor">*온라인 필수 이미지</a> </li>
						<li class="btn">
								<font color='red'>
								<!-- <b>300~500kb 이하 크기의 jpg 파일만 업로드가 가능합니다.</b> -->
								</font>
								<c:if test="${param.mode != 'viewInfo' }">
							    	<a href="javascript:addImage();" class="btn"><span><spring:message code="button.common.product.image.add"/></span></a>
								</c:if>
						</li>
					</ul>
				</div>
				
				
				
				
				<div class="bbs_search">
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td colspan=6 height=40><b>*주의사항</b> : 500x500크기로 등록해 주시기 바랍니다. </td>
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
					<c:forEach  var="imageFile" items="${onlineImageList}" varStatus="index" >
						<tr id="image_row_${index.count}"   name="image_row">
							<td align="center">
							<img width="95px" src='${imagePath}/lim/static_root/images/edi/online/${subFolderName }/${imageFile }_500.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_500">
							</td>
							<td align="center">
								<img width="95px" src='${imagePath}/lim/static_root/images/edi/online/${subFolderName }/${imageFile }_250.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_250">
							</td>
							
							<td align="center" >
							<img width="95px" src='${imagePath}/lim/static_root/images/edi/online/${subFolderName }/${imageFile }_160.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_160">
							</td>
							<td align="center">
							<img  src='${imagePath}/lim/static_root/images/edi/online/${subFolderName }/${imageFile }_100.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_100">
							</td>
							<td align="center">
								<img  src='${imagePath}/lim/static_root/images/edi/online/${subFolderName }/${imageFile }_75.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_75">
							</td>
							
							<td align="center" >
							<img  src='${imagePath}/lim/static_root/images/edi/online/${subFolderName }/${imageFile }_60.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_60">
							</td>
						</tr>
						<tr id="submit_row_${imageStauts.count}" name="submit_row"><td colspan="6" align="right" style="padding-right:40px">
								<c:if test="${param.mode != 'viewInfo' }">
								<input type="file" name="${imageFile }" />
								<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
								&nbsp;&nbsp;
								<a href="javascript:deleteImageRow('${imageFile}')" class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a>
								</c:if>
							</td>
						</tr>
							
					</c:forEach>
					
					<c:if test="${empty onlineImageList }">
					<tr name="image_row">
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
					<tr name="submit_row"><td colspan="6" align="right" style="padding-right:40px">
					<c:if test="${param.mode != 'viewInfo' }">
						<input type="file" name="front" />
						&nbsp;&nbsp;
						<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
					</c:if>
					</td></tr>
					</c:if>
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
			
				<!-- 01 : search -->
				
				
			</div>
			
			
		</div>
		</form>

<% /*
	</div>


	<br>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품</li>
					<li>신규상품관리</li>
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