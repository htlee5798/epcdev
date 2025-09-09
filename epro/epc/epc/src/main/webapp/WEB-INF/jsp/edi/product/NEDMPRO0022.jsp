<%--
	Page Name 	: NEDMPRO0022.jsp
	Description : 신상품등록 이미지등록 탭 [온오프전용]
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.12.16 		SONG MIN KYO 	최초생성
--%>
<%@ include file="../common.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp"%>
<%@ include file="./CommonProductFunction.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store" />
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code='msg.product.onOff.title' /></title>

<style type="text/css">
/* TAB */
.tabs {
	height: 31px;
	background: #fff;
}

.tabs ul {
	width: 100%;
	height: 31px;
}

.tabs li {
	float: left;
	width: 130px;
	height: 29px;
	background: #fff;
	border: 1px solid #ccd0d9;
	border-radius: 2px 2px 0 0;
	font-size: 12px;
	color: #666;
	line-height: 30px;
	text-align: center;
}

.tabs li.on {
	border-bottom: #e7eaef 1px solid;
	color: #333;
	font-weight: bold;
}

.tabs li a {
	display: block;
	color: #666;
}

.tabs li.on a {
	color: #333;
	font-weight: bold;
}
</style>

<script type="text/javascript">
	/* dom이 생성되면 ready method 실행 */
	$(document).ready( function() {
		    //-----탭 클릭 이벤트
	    $("#prodTabs li").click(function() {
	        var id = this.id;

		    var pgmId = $("#onlineUploadForm input[name='pgmId']").val();

			$("#hiddenForm input[name='pgmId']").val(pgmId);

			//기본정보 탭
			if (id == "pro01") {
			    $("#hiddenForm").attr("action","<c:url value='/edi/product/NEDMPRO0020Detail.do'/>");
				$("#hiddenForm").attr("method","post").submit();

			//속성입력 탭
			} else if (id == "pro02") {
				if (pgmId == "") {
				     alert("상품의 기본정보를 먼저 등록해주세요.");
					 return;
				}

				$("#hiddenForm").attr("action","<c:url value='/edi/product/NEDMPRO0021.do'/>");
		        $("#hiddenForm").attr("method","post").submit();

			//이미지 등록 탭
            } else if (id == "pro03") {

		    //영양속성 탭
            } else if (id == "pro04") {
				if (pgmId == "") {
					alert("<spring:message code='msg.product.tab.mst'/>");
					return;
				}
		
				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0027.do'/>");
				$("#hiddenForm").attr("method", "post").submit();
			//ESG 탭
			} else if (id == "pro05"){
				if (pgmId == "") {
					alert("<spring:message code='msg.product.tab.mst'/>");
					return;
				}

				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0028.do'/>");
				$("#hiddenForm").attr("method", "post").submit();
			}
	    });

			var status = "<c:out value="${param.status}" />";
			var ment = "<c:out value="${param.ment}" />";

			if (status === "success" || status === "fail") {
				if(ment==="1")
					alert("와이드 이미지가 저장됐습니다.");
				else if(ment==="2")
					alert("600KB이하의 이미지만 업로드 가능합니다.");
				else if(ment==="3")
					alert("720 X 405 이상 1312 X 740 이하 와이드이미지 업로드 가능합니다.");
				else if(ment==="4")
					alert("와이드 이미지가 삭제됐습니다.");
				else if(ment==="5")
					alert("정사이즈 비율 500px이상 1500px이하 온라인 이미지 업로드 가능합니다.");
				else if(ment==="6")
					alert("이미지 확장자 JPG만 업로드 가능합니다.")
			}
			console.log("ment : " + ment + " | status : " + status);

			});

	/* 이미지 추가 */
	function addImage() {
		var imageRowLength = $("table.bbs_search tr[name=image_row]").length;
		var newRow = imageRowLength + 1;
		var newImageField = "<c:out value="${param.pgmId}" />_"
				+ imageRowLength;
		<c:if test="${empty onlineImageList }">
		newImageField = "front_" + imageRowLength;
		</c:if>
		if (imageRowLength == 4) {
			alert("이미지는 4개까지 추가가 가능합니다.");
			return;
		}
		var imageRow = "<tr id=image_row_"+newRow+" name=image_row>"
				+ $("#template").val() + "</tr>";
		var submitRow = "<tr id=submit_row_"+newRow+" name=submit_row><td colspan=6 align=right style='padding-right:40px'><input type='file' name="+newImageField+" /> "
				+ "<a href='javascript:submitOnlineImage();' class=btn><span><spring:message code="button.common.save"/></span></a>&nbsp;&nbsp;"
				+ "<a href='javascript:deleteRow("
				+ newRow
				+ ")' class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a> "
				+ "</td></tr>";

		$("table.bbs_search tr[name=submit_row]:last").after(imageRow);
		$("table.bbs_search tr[name=image_row]:last").after(submitRow);
		//$("table.bbs_search").append(imageRow);
		//$("table.bbs_search").append(submitRow);
	}



	/* 추가된 row 삭제 */
	function deleteWideRow(rowNum) {
		if (rowNum > 1) {
			$("#wide_image_row_" + rowNum).remove();
			$("#wide_submit_row_" + rowNum).remove();
		} else {
			alert("모든 이미지 행을 삭제할 수는 없습니다.");
		}
	}

	/* 추가된 row 삭제 */
	function deleteRow(rowNum) {
		if (rowNum > 1) {
			$("#image_row_" + rowNum).remove();
			$("#submit_row_" + rowNum).remove();
		} else {
			alert("모든 이미지 행을 삭제할 수는 없습니다.");
		}
	}

	/* 이미지 저장 */
	function submitOnlineImage() {
		var flagVar = 0;

		var fileLen = $("form[name=onlineImageForm] input:file").length;

		$("input[name='uploadFieldCount']").val(fileLen);

		$("form[name=onlineImageForm] input:file").each(function() {
			if ($(this).val() == '') {
				flagVar++;
			}
		});

		if ($("input[name='uploadFieldCount']").val().replace(/\s/gi, '') == flagVar) {
			alert("업로드할 이미지를 선택해주세요.");
			return;
		}

		$("form[name=onlineImageForm]").submit();
	}

	// [WideImage 묶음 구간 ] S 추가, 제출, 삭제
	
	/* 이미지 추가 */
	function addWideImage() {
		var wideImageRowLength = $("table.bbs_search tr[name=wide_image_row]").length;
		var newRow = wideImageRowLength + 1;
		var newImageField = "<c:out value="${param.pgmId}" />_0"
				+ wideImageRowLength;
		<c:if test="${empty onlineWideImage }">
		newImageField = "front_" + wideImageRowLength;
		</c:if>
		if (wideImageRowLength == 4) {
			alert("이미지는 4개까지 추가가 가능합니다.");
			return;
		}
		var wideImageRow = "<tr id=wide_image_row_"+newRow+" name=wide_image_row>"
				+ $("#wideTemplate").val() + "</tr>";
		var submitRow = "<tr id=wide_submit_row_"+newRow+" name=wide_submit_row><td colspan=6 align=right style='padding-right:40px'><input type='file' name="+newImageField+" /> "
				+ "<a href='javascript:submitOnlineWideImage();' class=btn><span><spring:message code="button.common.save"/></span></a>&nbsp;&nbsp;&nbsp;"
				+ "<a href='javascript:deleteWideRow("
				+ newRow
				+ ")' class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a> "
				+ "</td></tr>";

		$("table.bbs_search tr[name=wide_submit_row]:last").after(wideImageRow);
		$("table.bbs_search tr[name=wide_image_row]:last").after(submitRow);
		//$("table.bbs_search").append(imageRow);
		//$("table.bbs_search").append(submitRow);
	}
	
	/* [ 와이드 이미지 저장 - PIY ] */
	function submitOnlineWideImage() {
		var flagVar = 0;

		var fileLen = $("form[name=onlineWideImageForm] input:file").length;

		$("input[name='uploadWideFieldCount']").val(fileLen);

		$("form[name=onlineWideImageForm] input:file").each(function() {
			if ($(this).val() == '') {
				flagVar++;
			}
		});

		if ($("input[name='uploadWideFieldCount']").val().replace(/\s/gi, '') == flagVar) {
			alert("업로드할 와이드 이미지를 선택해주세요.");
			return;
		}
	
		if (!confirm('와이드 이미지를 저장하시겠습니까?')) {
			return;
		}
		
		$("form[name=onlineWideImageForm]").submit();
	}

	/* 이미지 삭제 */
	function deleteWideImageRow(deleteProductCode) {
		var imageRowLength = $("table.bbs_search tr[name=wide_image_row]").length;

		if (imageRowLength > 0) {
			
			if (!confirm('와이드 이미지를 삭제하시겠습니까?')) {
				return;
			}
			
			location.href = "<c:url value='/edi/product/deleteProdWideOnlineImg.do'/>?pgmId="
					+ deleteProductCode +"&fileLen=" + imageRowLength;
		} else {
			alert("최소한 한개의 온라인 필수 이미지는 등록하셔야 합니다.");
		}
	}

	// [WideImage 묶음 구간 ] E 추가, 제출, 삭제
	
	/* 이미지 삭제 */
	function deleteImageRow(deleteProductCode) {
		var imageRowLength = $("table.bbs_search tr[name=image_row]").length;


		if (imageRowLength > 1) {
			location.href = "<c:url value='/edi/product/deleteProdOnlineImg.do'/>?pgmId="
					+ deleteProductCode ;
		} else {
			alert("최소한 한개의 온라인 필수 이미지는 등록하셔야 합니다.");
		}
	}


	/* 오프라인 이미지 전용  */
	function setDefaultImage(imageObject) {
		imageObject.src = '/images/epc/edi/no_photo.gif';
	}

	/* 오프라인 이미지 전용 저장 */
	function submitOfflineImage() {
		var uploadFieldCount = $("form[name=offlineImageForm] input:file").length;
		var flagVar = 0;
		$("form[name=offlineImageForm] input:file").each(function() {
			if ($(this).val() == '') {
				flagVar++;
			}
		});
		if (uploadFieldCount == flagVar) {
			alert("업로드할 이미지를 선택해주세요.");
			return;
		}

		if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
			return;
		}

		$("form[name=offlineImageForm]").submit();
	}

	/* 이미지 일괄지정 */
	function doAllApply() {
		var chkLen = $("input[name='chkAll']").length;
		//console.log(chkLen);

		var chkVariant = "";

		for (var i = 0; i < chkLen; i++) {
			//console.log(i + "::" + $("input[name='chkAll']").eq(i).is(":checked"));
			if ($("input[name='chkAll']").eq(i).is(":checked")) {
				if (chkVariant != "") {
					chkVariant = chkVariant + "|";
				}

				chkVariant = chkVariant
						+ $("input[name='chkVariant']").eq(i).val();
			}
		}

		if (chkVariant == "") {
			alert("일괄적용할 속성을 선택해주세요.");
			return;
		}

		$("#hiddenForm input[name='chkVariant']").val(chkVariant);

		var popInfo = window
				.open(
						'',
						'_blankPop',
						'status=no, resizeable=yes, width=400, height=250, left=480,top=290, scrollbars=no');
		popInfo.focus();

		$("#hiddenForm").attr("target", "_blankPop");
		$("#hiddenForm").attr("method", "post");
		$("#hiddenForm").attr("action",
				"<c:url value="/edi/product/NEDMPRO0022ImgPop.do" />");
		$("#hiddenForm").submit();
	}

	/* 이미지 일괄저장 후 새로고침 */
	function _eventRefresh() {
		$("#hiddenForm").attr("target", "_self");
		$("#hiddenForm").attr("action",
				"<c:url value='/edi/product/NEDMPRO0022.do'/>");
		$("#hiddenForm").attr("method", "post").submit();
	}
</script>

</head>

<body>

	<div id="content_wrap">
		<div>
			<div id="wrap_menu">
				<!-- 상단 신규상품등록 안내문구와 저장버튼 출력 부분 -------------------------------------------------->
				<div class="wrap_search">

					<!-- tab 구성---------------------------------------------------------------->
					<div id="prodTabs" class="tabs" style="padding-top: 10px;">
						<ul>
							<li id="pro01" style="cursor: pointer;">기본정보</li>
							<li id="pro02" style="cursor: pointer;">상품속성</li>
							<li id="pro03" class="on">이미지</li>
							<li id="pro04" style="cursor: pointer;">영양성분</li>
							<li id="pro05" style="cursor: pointer;">ESG</li>
						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->

					<div class="bbs_search">

						<ul class="tit">
							<li class="tit"><a name="onlineImageAnchor">*온라인 필수 이미지</a>
							</li>
							<li class="btn">
								<!-- <font color='red'><b>300~500kb 이하 크기의 jpg 파일만 업로드가 가능합니다.</b></font> -->
								<c:if test="${param.mode ne 'view'}">
									<a href="javascript:addImage();" class="btn"><span><spring:message
												code="button.common.product.image.add" /></span></a>
								</c:if>
							</li>
						</ul>
					</div>


					<form name="onlineImageForm" id="onlineUploadForm"
						action="<c:url value='/edi/product/NEDMPRO0022Save.do'/>"
						method="POST" enctype="multipart/form-data">
						<input type="hidden" name="pgmId" id="pgmId"
							value="<c:out value='${param.pgmId}'/>" /> <input type="hidden"
							name="prodDivnCd" id="prodDivnCd" value="1" /> <input
							type="hidden" name="onOffDivnCd" id="onOffDivnCd" value="0" />
						<input type="hidden" name="uploadFieldCount" id="uploadFieldCount" />
						<input type="hidden" name="mode" id="mode"
							value="<c:out value='${param.mode}'/>" />
						<!-- 온라인 이미지 저장이후 mode값 유지를 위해 사용 -->

						<div class="bbs_search">
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">
								<tr>
									<td colspan=6 height=40><b>*권장 이미지 사이즈</b> : 1500x1500 (최소 이미지 사이즈: 500x500)


										</td>
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
								<c:forEach items="${onlineImageList}" var="imageFile"
									varStatus="index">
									<tr id="image_row_<c:out value='${index.count}' />" name="image_row">
										<td align="center">
											<!-- [20200728-PIY] --> <img width="95px"
											src="<c:out value='${imagePathOnline}' />/<c:out value='${subFolderName }' />/<c:out value='${imageFile }' />?currentSecond=<c:out value='${currentSecond}' />"
											name="ImgPOG1f" id="image_1_500">
										</td>
										<td align="center"><img width="95px"
											src="<c:out value='${imagePathOnline}' />/<c:out value='${subFolderName }' />/<c:out value='${imageFile }' />_250.jpg?currentSecond=<c:out value='${currentSecond}' />"
											name="ImgPOG1f" id="image_1_250"></td>
										<td align="center"><img width="95px"
											src="<c:out value='${imagePathOnline}' />/<c:out value='${subFolderName }' />/<c:out value='${imageFile }' />_208.jpg?currentSecond=<c:out value='${currentSecond}' />"
											name="ImgPOG1f" id="image_1_208"></td>
										<td align="center"><img
											src="<c:out value='${imagePathOnline}' />/<c:out value='${subFolderName }' />/<c:out value='${imageFile }' />_120.jpg?currentSecond=<c:out value='${currentSecond}' />"
											name="ImgPOG1f" id="image_1_120"></td>
										<td align="center"><img
											src="<c:out value='${imagePathOnline}' />/<c:out value='${subFolderName }' />/<c:out value='${imageFile }' />_90.jpg?currentSecond=<c:out value='${currentSecond}' />"
											name="ImgPOG1f" id="image_1_90"></td>
										<td align="center"><img
											src="<c:out value='${imagePathOnline}' />/<c:out value='${subFolderName }' />/<c:out value='${imageFile }' />_80.jpg?currentSecond=<c:out value='${currentSecond}' />"
											name="ImgPOG1f" id="image_1_80"></td>
									</tr>

									<tr id="submit_row_<c:out value='${index.count}' />" name="submit_row">
										<td colspan="6" align="right" style="padding-right: 40px">
											<c:if test="${param.mode ne 'view'}">
												<input type="file" name="<c:out value='${imageFile }' />" />
												<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message
															code="button.common.save" /></span></a>
												&nbsp;&nbsp;
												<a href="javascript:deleteImageRow('<c:out value='${imageFile}' />')"
													class=btn name='rowDelete'><span><spring:message
															code="button.common.delete" /></span></a>
											</c:if>
										</td>
									</tr>
								</c:forEach>



								<!-- 온라인 이미지 리스트가 없을경우 -->
								<c:if test="${empty onlineImageList }">
									<tr name="image_row">
										<td align="center"><img width="95px"
											src="/images/epc/edi/no_photo.gif" name="ImgPOG1f"
											id="image_1_500"></td>
										<td align="center"><img width="95px"
											src="/images/epc/edi/no_photo.gif" name="ImgPOG1f"
											id="image_1_250"></td>
										<td align="center"><img width="95px"
											src="/images/epc/edi/no_photo.gif" name="ImgPOG1f"
											id="image_1_160"></td>
										<td align="center"><img width="95px"
											src="/images/epc/edi/no_photo.gif" name="ImgPOG1f"
											id="image_1_100"></td>
										<td align="center"><img width="95px"
											src="/images/epc/edi/no_photo.gif" name="ImgPOG1f"
											id="image_1_75"></td>
										<td align="center"><img width="95px"
											src="/images/epc/edi/no_photo.gif" name="ImgPOG1f"
											id="image_1_60"></td>
									</tr>
									<tr name="submit_row">
										<td colspan="6" align="right" style="padding-right: 40px">
											<input type="file" name="front" /> &nbsp;&nbsp; <c:if
												test="${param.mode ne 'view'}">
												<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message
															code="button.common.save" /></span></a>
											</c:if>
										</td>
									</tr>
								</c:if>
							</table>
						</div>

						<textarea id="template" style="display: none;">
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif"
								name="ImgPOG1f" id="image_1_500">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif"
								name="ImgPOG1f" id="image_1_250">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif"
								name="ImgPOG1f" id="image_1_160">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif"
								name="ImgPOG1f" id="image_1_100">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif"
								name="ImgPOG1f" id="image_1_75">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif"
								name="ImgPOG1f" id="image_1_60">
							</td>
						</textarea>
					</form>

					<!-- 테스트 구역    1010101010101010101010101010101010101010101010101010101010101010101010101010101010 -->

					<br />
					<br />

					<!-- [와이드이미지 구간] S-->

					<form name="onlineWideImageForm" id="onlineWideImageForm"
						action="<c:url value='/edi/product/NEDMPRO0022SaveWideImage.do'/>"
						method="post" enctype="multipart/form-data">
						<input type="hidden" name="pgmId" id="pgmId"
							value="<c:out value='${param.pgmId}'/>" /> <input type="hidden"
							name="prodDivnCd" id="prodDivnCd" value="1" /> <input
							type="hidden" name="uploadWideFieldCount" id="uploadWideFieldCount" />
						<input type="hidden" name="newProdGenDivnCd" id="newProdGenDivnCd"
							value="<c:out value='${prodDetailVO.newProdGenDivnCd}'/>" />
						<!-- 상품등록구분[KOR:코리안넷, 나머지:일반온오프상품] -->
						<input type="hidden" name="mode" id="mode"
							value="<c:out value='${param.mode}'/>" />
						<!-- 온라인 이미지 저장이후 mode값 유지를 위해 사용 -->




						<div class="bbs_search">
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">
								<tr>
									<td colspan=5 height=40><b>*권장 이미지 사이즈</b> : 1312X740px (최소 이미지 사이즈: 720X405)
										</td>
									<td colspan=1 height=40>
									<%-- <c:if test="${param.mode ne 'view'}">
										<a href="javascript:addWideImage();" class="btn">
											<span>
												<spring:message code="button.common.product.image.add" />
											</span>
										</a>
									</c:if> --%>
								</tr>
								<colgroup>
									<col />
								</colgroup>
								<tr>
									<th colspan="6" align="center">720 x 405</th>

								</tr>
								<!--[201028 PIY] START  -->
								<c:forEach items="${onlineWideImage}" var="wideImageFile"
									varStatus="index">

									<tr id="wide_image_row_<c:out value='${index.count}' />" name="wide_image_row">
										<td colspan="6" align="center">
											<!-- [20200728-PIY] --> <img
											src="<c:out value='${imagePathOnline}' />/wide/<c:out value='${subFolderName }' />/<c:out value='${wideImageFile}' />?currentSecond=<c:out value='${currentSecond}' />"
											width="360" height="202" name="ImgPOG1f" id="image_1_500">
										</td>
									</tr>

									<tr id="wide_submit_row_<c:out value='${index.count}' />" name="wide_submit_row">
										<td colspan="6" align="right" style="padding-right: 40px">
											<c:if test="${param.mode ne 'view'}">
												<input type="file" name="<c:out value='${wideImageFile }' />" />
											<a href="javascript:submitOnlineWideImage();" class="btn"><span><spring:message
															code="button.common.save" /></span></a>
															&nbsp;&nbsp;&nbsp;
												<a href="javascript:deleteWideImageRow('<c:out value='${wideImageFile}' />')"
													class=btn name='rowDelete'><span><spring:message
															code="button.common.delete" /></span></a>
											</c:if>
										</td>
									</tr>

								</c:forEach>

								<c:if test="${empty onlineWideImage }">
									<tr name="wide_image_row">
										<td colspan="6" align="center"><img width="95px"
											src="/images/epc/edi/no_photo.gif" name="ImgPOG1f"
											id="image_1_500"></td>
									</tr>
									<tr name="wide_submit_row">
										<td colspan="6" align="right" style="padding-right: 100px">
											<input type="file" name="front" />  
											<c:if test="${param.mode ne 'view'}">
												<a href="javascript:submitOnlineWideImage();" class="btn">
													<span>
														<spring:message code="button.common.save" />
													</span>
												</a>
											</c:if>
										</td>
									</tr>
								</c:if>


							</table>
						</div>


						<textarea id="wideTemplate" style="display: none;">
							<td colspan="6" align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif"
								name="ImgPOG1f" id="image_1_500">
							</td>
						</textarea>

					</form>
					<br />
					<br />
					<!-- [와이드이미지 구간] E -->
					
					<!-- 테스트 구역 끝 1010101010101010101010101010101010101010101010101010101010101010101010101010101010 -->

					<!-- 오프라인 이미지--------------------------------------------------------------------->
					<form name="offlineImageForm" id=offlineImageForm
						action="<c:url value='/edi/product/NEDMPRO0022SaveOffImg.do'/>"
						method="POST" enctype="multipart/form-data">

						<input type="hidden" name="pgmId" id="pgmId"
							value="<c:out value='${param.pgmId}' />" /> <input
							type="hidden" name="prodImgId" id="prodImgId"
							value="<c:out value='${prodDetailVO.prodImgId}' />" /> <input type="hidden"
							name="cfmFg" id="cfmFg" value="1" />
						<!-- 1:심사중 -->
						<input type="hidden" name="entpCd" id="entpCd"
							value="<c:out value="${prodDetailVO.entpCd}" />" /> <input
							type="hidden" name="mode" id="mode"
							value="<c:out value='${param.mode}'/>" />
						<!-- 오프라인 이미지 저장이후 mode값 유지를 위해 사용 -->
						<input type="hidden" name="newProdGenDivnCd" id="newProdGenDivnCd"
							value="<c:out value='${prodDetailVO.newProdGenDivnCd}'/>" />
						<!-- 상품등록구분[KOR:코리안넷, 나머지:일반온오프상품] -->
						<input type="hidden" name="prodAttTypFg" id="prodAttTypFg"
							value="<c:out value='${prodDetailVO.prodAttTypFg}'/>" />
						<!-- 00:단일, 01:묶음 -->


						<div class="bbs_search">
							<ul class="tit" style="height: 50px;">
								<li class="tit"><b>＊500kb 이하 크기의 jpg 파일만 업로드가 가능합니다.</b> <br>
									<font style="font-weight: bold; color: #FF0000;">이미지는
										개별지정이 가능하며 일괄 지정 시 속성정보의 체크박스 선택 후 지정하실 수 있습니다.</font> <c:if
										test="${param.mode ne 'view'}">
										<a href="#" class="btn" onclick="doAllApply();"><span><spring:message
													code="button.common.setall" /></span></a>
									</c:if></li>
								<li class="btn"><c:if test="${param.mode ne 'view'}">
										<a href="javascript:submitOfflineImage();" class="btn"><span><spring:message
													code="button.common.save" /></span></a>
									</c:if></li>
							</ul>
						</div>

						<div class="bbs_search">
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">
								<c:if test="${not empty prodVarAttList}">
									<colgroup>
										<col />
										<col />
										<col />
									</colgroup>
									<tr>
										<th colspan="3">속성</th>
									</tr>
									<tr>
										<th>정면</th>
										<th>측면</th>
										<th>윗면</th>
									</tr>

									<c:forEach var="list" items="${prodVarAttList}"
										varStatus="index">
										<tr>
											<td align="left" colspan="3"
												style="background-color: #FDDDDD;"><input
												type="checkbox" id="chkAll" name="chkAll" /> <input
												type="hidden" id="chkVariant" name="chkVariant"
												value="<c:out value='${list.variant}' />" /> <b><c:out value="${list.attNm}" /></b>
											</td>
										</tr>
										<tr>
											<td align="center"><img width="95px"
												src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName }' />/<c:out value='${list.prodImgId}' />.1.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>
											<td align="center"><img width="95px"
												src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName }' />/<c:out value='${list.prodImgId}' />.2.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>

											<td align="center"><img width="95px"
												src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName }' />/<c:out value='${list.prodImgId}' />.3.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>
										</tr>
										<tr>
											<td align="center"><input type="file"
												name="<c:out value='${list.prodImgId}' />_front" /> <input type="hidden"
												id="prodImgIdAl" name="prodImgIdAl"
												value="<c:out value='${prodDetailVO.prodImgId}' />" /> <input
												type="hidden" id="variantAl" name="variantAl"
												value="<c:out value='${list.variant}' />" /></td>
											<td align="center"><input type="file"
												name="<c:out value='${list.prodImgId}' />_side" /> <input type="hidden"
												id="prodImgIdAl" name="prodImgIdAl"
												value="<c:out value='${prodDetailVO.prodImgId}' />" /> <input
												type="hidden" id="variantAl" name="variantAl"
												value="<c:out value='${list.variant}' />" /></td>

											<td align="center"><input type="file"
												name="<c:out value='${list.prodImgId}' />_top" /> <input type="hidden"
												id="prodImgIdAl" name="prodImgIdAl"
												value="<c:out value='${prodDetailVO.prodImgId}' />" /> <input
												type="hidden" id="variantAl" name="variantAl"
												value="<c:out value='${list.variant}' />" /></td>
										</tr>
										<tr>
											<td colspan="3"><div id="uploadOutput"></div></td>
										</tr>
									</c:forEach>
								</c:if>

								<c:if test="${empty prodVarAttList}">
									<colgroup>
										<col />
										<col />
										<col />
									</colgroup>
									<tr>
										<th>정면</th>
										<th>측면</th>
										<th>윗면</th>
									</tr>

									<!-- 단일 상품이면서 코리안넷에서 등록한 상품이 아닐경우 -->
									<c:if test="${prodDetailVO.newProdGenDivnCd ne 'KOR'}">
										<tr>
											<td align="center"><img width="95px"
											    src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName}' />/<c:out value='${prodDetailVO.prodImgId}' />000.1.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>
											<td align="center"><img width="95px"
											    src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName}' />/<c:out value='${prodDetailVO.prodImgId}' />000.2.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td> 

											<td align="center"><img width="95px"
												src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName}' />/<c:out value='${prodDetailVO.prodImgId}' />000.3.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>
										</tr>
									</c:if>

									<!-- 단일 상품이면서 코리안넷에서 등록한 상품일 경우 -->
									<c:if test="${prodDetailVO.newProdGenDivnCd eq 'KOR'}">
										<tr>
											<td align="center"><img width="95px"
												src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName}' />/<c:out value='${prodDetailVO.prodImgId}' />000.1.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>
											<td align="center"><img width="95px"
												src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName}' />/<c:out value='${prodDetailVO.prodImgId}' />000.2.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>

											<td align="center"><img width="95px"
												src="<c:out value='${imagePath}' />/<c:out value='${imagePathSub}' />images/edi/offline/<c:out value='${subFolderName}' />/<c:out value='${prodDetailVO.prodImgId}' />000.3.jpg?currentSecond=<c:out value='${currentSecond}' />"
												onerror="setDefaultImage(this);" name="ImgPOG1f"></td>
										</tr>
									</c:if>

 									<tr>
										<td align="center">
											<input type="file" name="<c:out value='${prodDetailVO.prodImgId}' />_front" />
											<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
											<input type="hidden" id="variantAl" name="variantAl" value="000" />
										</td>
										<td align="center">
											<input type="file" name="<c:out value='${prodDetailVO.prodImgId}' />_side" />
											<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
											<input type="hidden" id="variantAl" name="variantAl" value="000" />
										</td>

										<td align="center" >
											<input type="file" name="<c:out value='${prodDetailVO.prodImgId}' />_top" />"
											<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
											<input type="hidden" id="variantAl" name="variantAl" value="000" />
										</td>
									</tr>
									<tr>
										<td colspan="3"><div id="uploadOutput"></div></td>
									</tr>
								</c:if>

							</table>
						</div>

					</form>
				</div>
			</div>
		</div>


		<!-- footer 시작 -------------------------------------------------------------------------------------->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li><spring:message
								code="msg.product.onOff.default.footerHome" /></li>
						<li><spring:message
								code="msg.product.onOff.default.footerItem" /></li>
						<li><spring:message
								code="msg.product.onOff.default.footerctrlNewItem" /></li>
						<li class="last"><spring:message
								code="msg.product.onOff.default.footerRegNewItem" /></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer 끝 -------------------------------------------------------------------------------------->
	</div>

	<!-- 탭 이동을 위한 hiddenForm -->
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" name="vendorTypeCd" id="vendorTypeCd" value="<c:out value='${epcLoginVO.vendorTypeCd}'/>" /> 
		<input type="hidden" name="pgmId" 		   id="pgmId"        value="<c:out value="${prodDetailVO.pgmId}" />" /> 
		<input type="hidden" name="prodImgId"    id="prodImgId"    value="<c:out value="${prodDetailVO.prodImgId}" />" /> 
		<input type="hidden" name="entpCd"		   id="entpCd"       value="<c:out value="${prodDetailVO.entpCd}" />" /> 
		<input type="hidden" name="chkVariant"   id="chkVariant"   value="" />
		<input type="hidden" name="mode" 		     id="mode"         value="<c:out value='${param.mode}'/>" /> 
		<input type="hidden" name="cfmFg" 		   id="cfmFg"        value="<c:out value='${param.cfmFg}'/>" />
		<input type="hidden" name="l1Cd"  		   id="l1Cd"         value="<c:out value='${prodDetailVO.l1Cd }'/>" />
		<input type="hidden" name="l3Cd"         id="l3Cd"         value="<c:out value='${prodDetailVO.l3Cd }'/>" />
		<!-- 상품확정구분 -->
	</form>


</body>
</html>
