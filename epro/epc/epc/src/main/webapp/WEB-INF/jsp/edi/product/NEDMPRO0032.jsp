<%--
- Author(s): projectBOS32
- Created Date: 2016. 04. 14
- Version : 1.0
- Description : 신상품등록  [대표이미지  등록페이지 ]
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store"/>
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache"/>
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>임시보관함 대표이미지 등록</title>

	<style type="text/css">
		/* TAB */
		.tabs {height:31px; background:#fff;}
		.tabs ul {width:100%; height:31px;}
		.tabs li {float:left; width:130px; height:29px; background:#fff; border:1px solid #ccd0d9; border-radius:2px 2px 0 0; font-size:12px; color:#666; line-height:30px; text-align:center;}
		.tabs li.on {border-bottom:#e7eaef 1px solid; color:#333; font-weight:bold;}
		.tabs li a {display:block; color:#666;}
		.tabs li.on a {color:#333; font-weight:bold;}
	</style>

	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script>
	<%@include file="./javascript.jsp" %>
	<script type="text/javascript">

	$(document).ready(function() {
		//-----탭 클릭 이벤트
		$("#prodTabs li").click(function() {
			var id = this.id;

			var pgmId = $("input[name='pgmId']").val();

			if("<c:out value = '${param.mode}'/>" != "view"){
				$("#mode").val("modify");
			}

			//기본정보 탭
			if (id == "pro01") {
				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0030OnlineDetail.do'/>");
				$("#hiddenForm").attr("method", "post").submit();
			//대표이미지 등록 탭	
			} else if (id == "pro02") {
				if (pgmId == "") {
					alert("상품 기본정보를 먼저 등록해 주세요.");
					return;
				}

				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0032.do'/>");
				$("#hiddenForm").attr("method", "post").submit();

			//배송정책 등록 탭	
			} else if (id == "pro03") {
				if (pgmId == "") {
					alert("상품 기본정보를 먼저 등록해 주세요.");
					return;
				}

				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0033.do'/>");
				$("#hiddenForm").attr("method", "post").submit();
			}
		});
	});

	function addSplitImage(activeSquareMimeValue) {
		var wec = document.Wec;
		var bodyTag = wec.BodyValue;
		//wec.MIMEValue = activeSquareMimeValue;
		//wec.BodyValue = bodyTag + decodeURI(wec.BodyValue); // base64에서 한글 입력하면 깨진다.
	}

	// 팝업으로 ImageSplitter를 띄운다.
	function doImageSplitterView() {
		window.open("/edit/splitter/ImageSplitter.html", "ImageSplitter", "width="+screen.width+", height="+screen.height+", toolbar=no, menubar=no, resizeable=true, fullscreen=true");
	}

	/* 대표이미지 추가 */
	function addImage() {
		var imageRowLength = $("table.bbs_search tr[name=image_row]").length;
		var newRow = imageRowLength+1;
		var newImageField = "<c:out value = '${param.pgmId}'/>_"+imageRowLength;
		<c:if test="${empty onlineImageList }">
		newImageField = "front_"+imageRowLength;
		</c:if>
		if( imageRowLength == 4 ) {
			alert("대표이미지는 4개까지 추가가 가능합니다.");
			return;
		}
		var imageRow = "<tr id=image_row_"+newRow+" name=image_row>"+ $("#template").val()+ "</tr>";
		var submitRow = "<tr id=submit_row_"+newRow+" name=submit_row><td colspan=6 align=right style='padding-right:40px'><input type='file' name="+newImageField+" /> "+
		"<a href='javascript:submitOnlineImage();' class=btn><span><spring:message code="button.common.save"/></span></a>&nbsp;&nbsp;"+
		"<a href='javascript:deleteRow("+newRow+")' class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a> " +
		"</td></tr>";

		$("table.bbs_search tr[name=submit_row]:last").after(imageRow);
		$("table.bbs_search tr[name=image_row]:last").after(submitRow);
		//$("table.bbs_search").append(imageRow);
		//$("table.bbs_search").append(submitRow);
	}

	/* 추가된 row 삭제 */
	function deleteRow(rowNum) {
		if( rowNum > 1) {
			$("#image_row_"+rowNum).remove();
			$("#submit_row_"+rowNum).remove();
		} else {
			alert("모든 대표이미지 행을 삭제할 수는 없습니다.");
		}
	}

	/* 대표이미지 저장 */
	function submitOnlineImage() {

		var flagVar = 0;
		var fileLen = $("form[name=onlineImageForm] input:file").length;

		$("input[name='uploadFieldCount']").val(fileLen);

		$("form[name=onlineImageForm] input:file").each(function() {
			if( $(this).val() == '') {
				flagVar ++;
			}
		});

		if( $("input[name='uploadFieldCount']").val().replace(/\s/gi, '') == flagVar) {
			alert("업로드할 대표이미지를 선택해주세요.");
			return;
		}

		$("form[name=onlineImageForm]").submit();
	}

	/* 대표이미지 삭제 */
	function deleteImageRow(deleteProductCode) {
		var imageRowLength = $("table.bbs_search tr[name=image_row]").length;

		if(imageRowLength > 1) {
			location.href="<c:url value='/edi/product/deleteProdOnlineImg.do'/>?pgmId=" + deleteProductCode+"&onOffDivnCd="+$("#onOffDivnCd").val();
		} else {
			alert("최소한 한개의 대표이미지는 등록하셔야 합니다.");
		}
	}

	/*상세이미지 저장*/
	function submitProductDetailImage(){

		var newProdDescBodyVal = CrossEditor.GetBodyValue();
		var newProdDescTxtVal = CrossEditor.GetTextValue();
		newProdDescTxtVal = newProdDescTxtVal.replace(/^\s*/,'').replace(/\s*$/, '');

		if(newProdDescTxtVal == "" && newProdDescBodyVal.indexOf('<IMG') == '-1' 
			&& newProdDescBodyVal.indexOf('<img') == '-1' && newProdDescBodyVal.toUpperCase().indexOf('<IFRAME') == '-1'
			&& newProdDescBodyVal.toUpperCase().indexOf('<iframe') == '-1'  && newProdDescBodyVal.toUpperCase().indexOf('<EMBED') == '-1'
			&& newProdDescBodyVal.toUpperCase().indexOf('<embed') == '-1') {
			alert('상세이미지를 필히 입력해주세요. 온라인판매를 위해 정확히 넣어주세요!');
			return;
		}

		$("input[name=productDescription]").val(CrossEditor.GetValue());
		$("form[name=onlineDetailImgForm]").submit();
	}
	</script>

</head>
<body>
	<div id="content_wrap">
		<div>
			<div id="wrap_menu">
				<div class="wrap_search">

					<!-- tab 구성 -->
					<div id="prodTabs" class="tabs" style="padding-top:10px;">
						<ul>
							<li id="pro01" style="cursor: pointer;">기본정보</li>
							<li id="pro02" class="on">이미지</li>
							<li id="pro03" style="cursor: pointer;">배송정책</li>
						</ul>
					</div>
					<!-- tab 구성 -->
					<!-- 온라인 대표이미지 등록 시작 -->
					<div class="bbs_search">

						<ul class="tit">
							<li class="tit"><a name="onlineImageAnchor">*온라인 필수 대표이미지</a> </li>
							<c:if test="${param.mode != 'view'}">
							<li class="btn">
								<font color="red"><b>600KB 이하 크기의 jpg 파일만 업로드가 가능합니다.</b></font>
							    <a href="javascript:addImage();" class="btn"><span><spring:message code="button.common.product.image.add"/></span></a>
							</li>
							</c:if>
						</ul>
					</div>

					<form name="onlineImageForm" id="onlineUploadForm" action="<c:url value='/edi/product/NEDMPRO0022Save.do'/>" method="POST" enctype="multipart/form-data">
						<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${param.pgmId}'/>" />
						<input type="hidden" name="prodDivnCd" id="prodDivnCd" />
						<input type="hidden" name="onOffDivnCd" id="onOffDivnCd" value="<c:out value='${newProdDetailInfo.onoffDivnCd}'/>" />
						<input type="hidden" name="uploadFieldCount" id="uploadFieldCount" />

						<div class="bbs_search">
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td colspan="6" height="40"><b>*주의사항</b> : (정사이즈 비율) 500x500 ~ 1500x1500 크기로 등록해 주시기 바랍니다. </td>
								</tr>
								<colgroup>
									<col />
									<col />
									<col />
									<col />
									<col />
									<col />
								</colgroup>
								<tr>
									<th><span>1.</span> 500 x 500</th>
									<th>250 x 250</th>
									<th>208 x 208</th>
									<th>120 x 120</th>
									<th>90 x 90</th>
									<th>80 x 80</th>
								</tr>
								<c:forEach items="${onlineImageList}" var="imageFile" varStatus="index">
									<tr id="image_row_${index.count}" name="image_row">
										<td align="center">
											<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_500.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_500" />
										</td>
										<td align="center">
											<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_250.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_250" />
										</td>
										<td align="center">
											<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_208.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_208" />
										</td>
										<td align="center">
											<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_120.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_120" />
										</td>
										<td align="center">
											<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_90.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_90" />
										</td>
										<td align="center">
											<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_80.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_80" />
										</td>
									</tr>

									<tr id="submit_row_${index.count}" name="submit_row">
										<td colspan="6" align="right" style="padding-right:40px">
											<c:if test="${param.mode != 'view'}">
											<input type="file" name="${imageFile }" />
											<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
											&nbsp;&nbsp;
											<c:if test="${index.count > 1}">
											<a href="javascript:deleteImageRow('${imageFile}')" class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a>
											</c:if>
											</c:if>
										</td>
									</tr>
								</c:forEach>

								<!-- 온라인 대표이미지 리스트가 없을경우 -->
								<c:if test="${empty onlineImageList }">
									<tr name="image_row">
										<td align="center">
											<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500" />
										</td>
										<td align="center">
											<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250" />
										</td>
										<td align="center">
											<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_208" />
										</td>
										<td align="center">
											<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_120" />
										</td>
										<td align="center">
											<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_90" />
										</td>
										<td align="center">
											<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_80" />
										</td>
									</tr>
									<tr name="submit_row">
										<td colspan="6" align="right" style="padding-right:40px">
											<c:if test="${param.mode != 'view'}">
											<input type="file" name="front" />
											&nbsp;&nbsp;
											<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
											</c:if>
										</td>
									</tr>
								</c:if>
							</table>
						</div>

						<textarea id="template" style="display:none;">
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_208" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_120" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_90" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_80" />
							</td>
						</textarea>
					</form>

				<form name="onlineDetailImgForm" id="onlineDetailImgForm" action="<c:url value='/edi/product/NEDMPRO0022SaveDetailImg.do'/>" method="POST">
					<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${param.pgmId}'/>" />
					<input type="hidden" name="prodDivnCd" id="prodDivnCd" />
					<input type="hidden" name="onOffDivnCd" id="onOffDivnCd" value="<c:out value='${newProdDetailInfo.onoffDivnCd}'/>" />
					<input type="hidden" name="prodNm" id="prodNm" value="<c:out value='${newProdDetailInfo.prodNm}'/>" />
					<input type="hidden" name="entpCd" id="entpCd" value="<c:out value='${newProdDetailInfo.entpCd}'/>" />
					<!-- editor 시작 -->
				 <div class="bbs_list">
					<ul class="tit">
						<li class="tit">*상세이미지 : 온라인에서 사용될 설명이며 <font color='red'><b>필수</b></font>사항입니다.</li>
						<li class="btn">
							<a href="#" class="btn" onclick="submitProductDetailImage();" id="saveBtn"><span><spring:message code="button.common.save"/></span></a>
						</li>
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<!-- <textarea id="pe_agt" name="pe_agt" alt="initText" title="initText" Style="width:730px;height:450px;font-size:10pt"><p>Welcome to <span style="font-weight:bold;">CrossEditor 3.0</span> sample page</p></textarea> -->
								<input type="hidden" name="productDescription" id="productDescription" value="<c:out value='${newProdDetailInfo.prodDesc}'/>">
								<script type="text/javascript" language="javascript">
									var CrossEditor = new NamoSE('pe_agt');

									CrossEditor.params.Width = "100%";
									CrossEditor.params.UserLang = "auto";
									CrossEditor.params.FullScreen = false;
									CrossEditor.params.SetFocus = false; // 에디터 포커스 해제
									CrossEditor.params.ImageSavePath = "edi";
									CrossEditor.params.ActiveTab = 40;
									CrossEditor.params.AccessibilityOption = 1;
									CrossEditor.params.Template = [
										{ title : "온라인상품 식품",
										  url : "/html/epc/namoTemplate/onlineProduct_Food.html",
										  charset : "utf-8"
										},
										{ title : "온라인상품 비식품",
										  url : "/html/epc/namoTemplate/OnlineProduct_NotFood.html",
										  charset : "utf-8"
										}
									];
									CrossEditor.EditorStart();

									function OnInitCompleted(e){
										e.editorTarget.SetBodyStyle('text-align','center');
										e.editorTarget.SetBodyValue(document.getElementById("productDescription").value);
									}
								</script>
							</td>
						</tr>
					</table>
				</div>
				<!-- editor 끝 -->
					
					</form>	
					<!-- 온라인 대표이미지 등록 끝 -->
				</div>
			</div>
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
					<li>신규상품관리</li>
					<li>온라인전용 상품등록</li>
					<li class="last">대표이미지</li>
				</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

<!-- 탭 이동을 위한 hiddenForm -->
	<form name="hiddenForm"	id="hiddenForm">
		<input type="hidden" name="vendorTypeCd" id="vendorTypeCd" value="<c:out value='${epcLoginVO.vendorTypeCd}'/>" />
		<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${newProdDetailInfo.pgmId }'/>" /><!-- 상품이 등록되고 나면 등록된 상품의 pgmId가 설정됨 -->
		<input type="hidden" name="entpCd" id="entpCd" value="<c:out value='${newProdDetailInfo.entpCd }'/>" /><!-- 상품이 등록되고 나면 등록된 상품의 협력업체코드가 설정됨 -->
		<input type="hidden" name="mode" id="mode" value="<c:out value='${param.mode}'/>" /><!-- view, modify, ''-->
		<input type="hidden" name="cfmFg" id="cfmFg" value="<c:out value='${param.cfmFg}'/>" /><!-- 상품확정구분 -->
	</form>
</body>
</html>
