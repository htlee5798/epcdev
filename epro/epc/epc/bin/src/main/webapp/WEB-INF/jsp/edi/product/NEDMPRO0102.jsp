<%--
	Page Name 	: NEDMPRO0102.jsp
	Description : 신상품등록 이미지등록 탭 [코리안넷 전용]
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.02.18 		SONG MIN KYO 	최초생성
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
	<title><spring:message code='msg.product.onOff.title'/></title>
	
	<style type="text/css">
		/* TAB */
		.tabs {height:31px; background:#fff;}
		.tabs ul {width:100%; height:31px;}
		.tabs li {float:left; width:130px; height:29px; background:#fff; border:1px solid #ccd0d9; border-radius:2px 2px 0 0; font-size:12px; color:#666; line-height:30px; text-align:center;}
		.tabs li.on {border-bottom:#e7eaef 1px solid; color:#333; font-weight:bold;}
		.tabs li a {display:block; color:#666;}
		.tabs li.on a {color:#333; font-weight:bold;}
	</style>
		
	<script type="text/javascript" >		
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {		
												
			//-----탭 클릭 이벤트
			$("#prodTabs li").click(function() {
				var id = this.id;
				
				var pgmId = $("#onlineImageForm input[name='pgmId']").val();
				
				$("#onlineImageForm input[name='pgmId']").val(pgmId);
				
				//기본정보 탭
				if (id == "pro01") {
					$("#onlineImageForm").attr("action", "<c:url value='/edi/product/NEDMPRO0100Detail.do'/>");			
					$("#onlineImageForm").attr("method", "post").submit();

				//속성입력 탭	
				} else if (id == "pro02") {
					if (pgmId == "") {
						alert("상품의 기본정보를 먼저 등록해주세요.");
						return;
					}
					
					$("#onlineImageForm").attr("action", "<c:url value='/edi/product/NEDMPRO0101.do'/>");			
					$("#onlineImageForm").attr("method", "post").submit();
					
				//이미지 등록 탭	
				} else if (id == "pro03") {					
					/* if (pgmId == "") {
						alert("상품의 기본정보를 먼저 등록해주세요.");
						return;
					}
					
					$("#onlineImageForm").attr("action", "<c:url value='/edi/product/NEDMPRO0022.do'/>");			
					$("#onlineImageForm").attr("method", "post").submit(); */
				
					
				//물류바코드 탭	
				} else if (id == "pro04") {
					if (pgmId == "") {
						alert("상품의 기본정보를 먼저 등록해주세요.");
						return;
					}
									
					$("#onlineImageForm").attr("action", "<c:url value='/edi/product/NEDMPRO0103.do'/>");			
					$("#onlineImageForm").attr("method", "post").submit();

				//영양성분 탭
				} else if (id == "pro05") {
					if (pgmId == "") {
						alert("<spring:message code='msg.product.tab.mst'/>");
						return;
					}

					$("#onlineImageForm").attr("action", "<c:url value='/edi/product/NEDMPRO0104.do'/>");
					$("#onlineImageForm").attr("method", "post").submit();
				}
			});

			var status = "<c:out value='${param.status}' />";
			var ment = "<c:out value='${param.ment}' />";

			var squareStatus = "<c:out value='${status}' />";
			var squareMent = "<c:out value='${ment}' />";
			
			
			if ((status === "success" || status === "fail") || (squareStatus === "success" || squareStatus === "fail") ) {
				if(ment==="1")
					alert("와이드 이미지가 저장됐습니다.");
				else if(ment==="2")
					alert("600KB이하의 이미지만 업로드 가능합니다.");
				else if(ment==="3")
					alert("와이드형 사이즈는 1312 X 740 이하입니다.");
				else if(ment==="4")
					alert("와이드 이미지가 삭제됐습니다.");
				else if(ment==="5")
					alert("사이즈는 정사이즈 비율 500px 이상 1500px 이하입니다.");
				else if(ment==="6" || squareMent==="6")
					alert("이미지 확장자 JPG만 업로드 가능합니다.")
			}
		});
		
		/* 이미지 추가 */
		function addImage() {
			var imageRowLength = $("table.bbs_search tr[name=image_row]").length;
			var newRow = imageRowLength+1;
			
			var newImageField = "<c:out value="${param.pgmId}" />_"+imageRowLength;
			
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
			if( rowNum > 1) {
				$("#image_row_"+rowNum).remove();
				$("#submit_row_"+rowNum).remove();
			} else {
				alert("모든 이미지 행을 삭제할 수는 없습니다.");
			}
		}
		
		/* 이미지 저장 */
		function submitOnlineImage() {
			var flagVar	=	0;
			
			var fileLen	=	$("form[name=onlineImageForm] input:file").length;
			
			$("input[name='uploadFieldCount']").val(fileLen);
			
			$("form[name=onlineImageForm] input:file").each(function() {
				if( $(this).val() == '') {
					flagVar ++;
				}
			});
			 
			if( $("input[name='uploadFieldCount']").val().replace(/\s/gi, '') == flagVar) {
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

			$("form[name=onlineWideImageForm]").submit();
		}

		/* 이미지 삭제 */
		function deleteWideImageRow(deleteProductCode) {
			var imageRowLength = $("table.bbs_search tr[name=wide_image_row]").length;

			if (imageRowLength > 1) {
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
			
			if( imageRowLength > 1) {
				location.href="<c:url value='/edi/product/deleteProdOnlineImg.do'/>?pgmId=" + deleteProductCode;
			} else {
				alert("최소한 한개의 온라인 필수 이미지는 등록하셔야 합니다.");
			}
		}	
		
		
		
		/* 오프라인 이미지 전용  */
		function setDefaultImage(imageObject) {
			imageObject.src='/images/epc/edi/no_photo.gif';
		}
		
		/* 오프라인 이미지 전용 저장 */
		function submitOfflineImage() {
			var uploadFieldCount = $("form[name=offlineImageForm] input:file").length;
			var flagVar = 0;
			$("form[name=offlineImageForm] input:file").each(function() {
				if( $(this).val() == '') {
					flagVar ++;
				}
			});
			if( uploadFieldCount == flagVar) {
				alert("업로드할 이미지를 선택해주세요.");
				return;
			}
			
			if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
				return;
			}
			
			$("form[name=offlineImageForm]").submit();
		}
		
		/* 코리안넷 이미지 가져오기 */
		function getKoreanNetImage(srcmkCd, variant) {
			if (!confirm('<spring:message code="msg.korean.img.get" />')) {
				return;
			}
			
			var dataInfo = {};
			
			dataInfo["pgmId"] 		= $("#koreanNetForm input[name='pgmId']").val();
			dataInfo["entpCd"] 		= $("#koreanNetForm input[name='entpCd']").val();
			dataInfo["srcmkCd"] 	= srcmkCd;
			dataInfo["variant"] 	= variant;
			//console.log(dataInfo);
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false, 
				url : '<c:url value="/edi/product/NEDMKOR0010CallJson.json"/>',
				data : JSON.stringify(dataInfo),
				success : function(data) {
					if (data.retCd != "200") {
						alert(data.retMsg);
					} else {
						alert('<spring:message code="msg.korean.img.success" />');
						
						$("#onlineImageForm").attr("action", "<c:url value='/edi/product/NEDMPRO0102.do'/>");			
						$("#onlineImageForm").attr("method", "post").submit();
					}
				}
			});
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
						<div id="prodTabs" class="tabs" style="padding-top:10px;">
						<ul>
							<li id="pro01" style="cursor: pointer;">기본정보</li>
							<li id="pro02" style="cursor: pointer;">상품속성</li>
							<li id="pro03" class="on">이미지</li>
							
							
							<!-- 단일 상품일 경우 -->														
							<c:if test="${prodDetailVO.prodAttTypFg eq '00'}">
								<!-- 추가정보 입력란의 혼재여부가 단일일 경우에만 물류바코드 탭 disply 표기 -->
								<c:if test="${prodDetailVO.mixYn eq '0'}">
									<li id="pro04" style="cursor: pointer;">물류바코드</li>
								</c:if>
							</c:if>
							
							<!-- 묶음 상품일 경우 -->
							<c:if test="${prodDetailVO.prodAttTypFg eq '01'}">
								<c:if test="${prodDetailVO.inputVarAttCnt > 0}">
									<li id="pro04" style="cursor: pointer;">물류바코드</li>
								</c:if>
							</c:if>	
							<li id="pro05" style="cursor: pointer;">영양성분</li>	
						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->
					
					<div class="bbs_search">

						<ul class="tit">
							<li class="tit"><a name="onlineImageAnchor">*온라인 필수 이미지</a> </li>
							<li class="btn">
								<!-- <font color='red'><b>300~500kb 이하 크기의 jpg 파일만 업로드가 가능합니다.</b></font> -->
								
								<!-- 상품 확정요청 아닐 때만 버튼 활성화 -->
								<c:if test="${empty prodDetailVO.mdSendDivnCd}">
									<a href="javascript:addImage();" class="btn"><span><spring:message code="button.common.product.image.add"/></span></a>	
								</c:if>																							    							
							</li>
						</ul>
					</div>
					
					
					<form name="onlineImageForm" id="onlineImageForm"	action="<c:url value='/edi/product/NEDMPRO0102Save.do'/>" method="POST" enctype="multipart/form-data">	
						
						<input type="hidden" 	name="prodDivnCd" 			id="prodDivnCd"			  value="<c:out value='${param.prodDivnCd}'/>" 				/>
						<input type="hidden" 	name="onOffDivnCd" 			id="onOffDivnCd"		  value="<c:out value='${prodDetailVO.onoffDivnCd}'/>"		/>
						<input type="hidden" 	name="uploadFieldCount" id="uploadFieldCount" 																/>		
						<input type="hidden" 	name="newProdGenDivnCd"	id="newProdGenDivnCd"	value="<c:out value='${prodDetailVO.newProdGenDivnCd}'/>"	/>	
						<input type="hidden"	name="pgmId"				    id="pgmId"				    value="<c:out value="${prodDetailVO.pgmId}" />"				/>
						<input type="hidden"	name="prodImgId"			  id="prodImgId"			  value="<c:out value="${prodDetailVO.prodImgId}" />"			/>
						<input type="hidden"	name="entpCd"				    id="entpCd"				    value="<c:out value="${prodDetailVO.entpCd}" />"			/>
						<input type="hidden"	name="l1Cd" 		    	  id="l1Cd" 		    	  value="<c:out value="${prodDetailVO.l1Cd}" />" 		        />
						<input type="hidden"  name="l3Cd"             id="l3Cd"             value="<c:out value="${prodDetailVO.l3Cd}" />"            />
						<input type="hidden"	name="chkVariant"			  id="chkVariant"			  value=""													/>
						<input type="hidden"	name="mode"					    id="mode"				      value="<c:out value='${param.mode}'/>"						/>
						<input type="hidden"	name="srcmk_cd"				  id="srcmk_cd"			    value="<c:out value='${param.srcmk_cd}'/>"					/>	<!-- 코리안넷에서 넘어온 판매코드 -->
						<input type="hidden"	name="bcd_fg"				    id="bcd_fg"				    value="<c:out value='${param.bcd_fg }'/>"					/>	<!-- 코리안넷 바코드번호 -->
						<input type="hidden"	name="BOX_FG"				    id="BOX_FG"				    value="<c:out value='${param.BOX_FG}'/>"					/>	<!-- 코리안넷 박스바코드번호 -->
						<input type="hidden"	name="LOGI_BCD"				  id="LOGI_BCD"			    value="<c:out value='${param.LOGI_BCD}'/>"					/>	<!-- 코리안넷 박스GTIN -->		
						<input type="hidden"	name="WG"					      id="WG"					      value="<c:out value='${param.WG}'/>"						/>	<!-- 코리안넷 박스 중량 -->
						<input type="hidden"	name="WIDTH"				    id="WIDTH"				    value="<c:out value='${param.WIDTH}'/>"						/>	<!-- 코리안넷 박스 가로 -->
						<input type="hidden"	name="LENGTH"				    id="LENGTH"				    value="<c:out value='${param.LENGTH}'/>"					/>	<!-- 코리안넷 박스 세로 -->
						<input type="hidden"	name="HEIGHT"			   	  id="HEIGHT"				    value="<c:out value='${param.HEIGHT}'/>"					/>	<!-- 코리안넷 박스 높이 -->
						<input type="hidden"	name="TOTAL_IPSU"			  id="TOTAL_IPSU"			  value="<c:out value='${param.TOTAL_IPSU}'/>"				/>	<!-- 코리안넷 수량 -->
						<input type="hidden"	name="bman_no"				  id="bman_no"			    value="<c:out value='${param.bman_no}'/>"					/>	<!-- 코리안넷에서 넘어온 사업자등록번호 -->					
						<input type="hidden"	name="wideImgYn"			  id="wideImgYn"			  value="N"													/>	<!-- 와이드이미지 유무 -->
								
					
						<div class="bbs_search">
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td colspan=6 height=40><b>*권장 이미지 사이즈</</b> : 1500x1500 (최소 이미지 사이즈: 500x500) </td>
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
									<th>208 x 208</th>
									<th>120 x 120</th>
									<th>90 x 90</th>
									<th>80 x 80</th>
								</tr>
								<c:forEach  items="${onlineImageList}"	var="imageFile" varStatus="index" >
									<tr id="image_row_${index.count}"   name="image_row">
										<td align="center">																
											<img width="95px" src='${imagePathOnline}/${subFolderName }/${imageFile }_500.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_500">
										</td>
										<td align="center">
											<img width="95px" src='${imagePathOnline}/${subFolderName }/${imageFile }_250.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_250">
										</td>							
										<td align="center" >
											<img width="95px" src='${imagePathOnline}/${subFolderName }/${imageFile }_208.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_208">
										</td>
										<td align="center">
											<img  src='${imagePathOnline}/${subFolderName }/${imageFile }_120.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_120">
										</td>
										<td align="center">
											<img  src='${imagePathOnline}/${subFolderName }/${imageFile }_90.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_90">
										</td>							
										<td align="center" >
											<img  src='${imagePathOnline}/${subFolderName }/${imageFile }_80.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_80">
										</td>
									</tr>
									
									<tr id="submit_row_${index.count}" name="submit_row">
										<td colspan="6" align="right" style="padding-right:40px">									
											
											<!-- 확정요철 아닐때만 활성화 -->
											<c:if test="${empty prodDetailVO.mdSendDivnCd}">
												<input type="file" name="${imageFile }" />
												<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
												&nbsp;&nbsp;
												<a href="javascript:deleteImageRow('${imageFile}')" class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a>	
											</c:if>
														
										</td>
									</tr>								
								</c:forEach>
								
								
								
								<!-- 온라인 이미지 리스트가 없을경우 -->
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
									<tr name="submit_row">
										<td colspan="6" align="right" style="padding-right:40px">										
											<input type="file" name="front" />
											&nbsp;&nbsp;
											<a href="javascript:submitOnlineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>										
										</td>
									</tr>
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
					</form>	
					
					
					<!-- [와이드이미지 구간] S-->
					
					<br />
					<br />
					
					<form name="onlineWideImageForm" id="onlineWideImageForm" action="<c:url value='/edi/product/NEDMPRO0102Save.do'/>"
						method="post" enctype="multipart/form-data">
						<input type="hidden" 	name="prodDivnCd" 			id="prodDivnCd"			value="<c:out value='${param.prodDivnCd}'/>" 				/>
						<input type="hidden" 	name="onOffDivnCd" 			id="onOffDivnCd"		value="<c:out value='${prodDetailVO.onoffDivnCd}'/>"		/>
						<input type="hidden" 	name="uploadWideFieldCount" id="uploadWideFieldCount" 																/>		
						<input type="hidden" 	name="newProdGenDivnCd"		id="newProdGenDivnCd"	value="<c:out value='${prodDetailVO.newProdGenDivnCd}'/>"	/>	
						<input type="hidden"	name="pgmId"				id="pgmId"				value="<c:out value="${prodDetailVO.pgmId}" />"				/>
						<input type="hidden"	name="prodImgId"			id="prodImgId"			value="<c:out value="${prodDetailVO.prodImgId}" />"			/>
						<input type="hidden"	name="entpCd"				id="entpCd"				value="<c:out value="${prodDetailVO.entpCd}" />"			/>
						<input type="hidden"	name="chkVariant"			id="chkVariant"			value=""													/>
						<input type="hidden"	name="mode"					id="mode"				value="<c:out value='${param.mode}'/>"						/>
						<input type="hidden"	name="srcmk_cd"				id="srcmk_cd"			value="<c:out value='${param.srcmk_cd}'/>"					/>	<!-- 코리안넷에서 넘어온 판매코드 -->
						<input type="hidden"	name="bcd_fg"				id="bcd_fg"				value="<c:out value='${param.bcd_fg }'/>"					/>	<!-- 코리안넷 바코드번호 -->
						<input type="hidden"	name="BOX_FG"				id="BOX_FG"				value="<c:out value='${param.BOX_FG}'/>"					/>	<!-- 코리안넷 박스바코드번호 -->
						<input type="hidden"	name="LOGI_BCD"				id="LOGI_BCD"			value="<c:out value='${param.LOGI_BCD}'/>"					/>	<!-- 코리안넷 박스GTIN -->		
						<input type="hidden"	name="WG"					id="WG"					value="<c:out value='${param.WG}'/>"						/>	<!-- 코리안넷 박스 중량 -->
						<input type="hidden"	name="WIDTH"				id="WIDTH"				value="<c:out value='${param.WIDTH}'/>"						/>	<!-- 코리안넷 박스 가로 -->
						<input type="hidden"	name="LENGTH"				id="LENGTH"				value="<c:out value='${param.LENGTH}'/>"					/>	<!-- 코리안넷 박스 세로 -->
						<input type="hidden"	name="HEIGHT"				id="HEIGHT"				value="<c:out value='${param.HEIGHT}'/>"					/>	<!-- 코리안넷 박스 높이 -->
						<input type="hidden"	name="TOTAL_IPSU"			id="TOTAL_IPSU"			value="<c:out value='${param.TOTAL_IPSU}'/>"				/>	<!-- 코리안넷 수량 -->
						<input type="hidden"	name="bman_no"				id="bman_no"			value="<c:out value='${param.bman_no}'/>"					/>	<!-- 코리안넷에서 넘어온 사업자등록번호 -->
						<input type="hidden"	name="wideImgYn"			id="wideImgYn"			value="Y"													/>	<!-- 와이드이미지 유무 -->



						<div class="bbs_search">
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">
								<tr>
									<td colspan=5 height=40><b>*권장 이미지 사이즈<</b> : 1312X740px (최소 이미지 사이즈: 720X405)
										</td>
									<td colspan=1 height=40>
									<c:if test="${param.mode ne 'view'}">
										<%-- <a href="javascript:addWideImage();" class="btn">
											<span>
												<spring:message code="button.common.product.image.add" />
											</span>
										</a> --%>
									</c:if>
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

									<tr id="wide_image_row_${index.count}" name="wide_image_row">
										<td colspan="6" align="center">
											<!-- [20200728-PIY] --> <img
											src='${imagePathOnline}/wide/${subFolderName }/${wideImageFile}?currentSecond=${currentSecond}'
											width="360" height="202" ' name="ImgPOG1f" id="image_1_500">
										</td>
									</tr>
									
									<tr id="wide_submit_row_${index.count}" name="wide_submit_row">
										<td colspan="6" align="right" style="padding-right:40px">									
											
											<!-- 확정요철 아닐때만 활성화 -->
											<c:if test="${empty prodDetailVO.mdSendDivnCd}">
												<input type="file" name="${wideImageFile }" />
												<a href="javascript:submitOnlineWideImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
												&nbsp;&nbsp;
												<a href="javascript:deleteWideImageRow('${wideImageFile}')" class=btn name='rowDelete'><span><spring:message code="button.common.delete"/></span></a>	
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
										<td colspan="6" align="right" style="padding-right:100px">										
											<input type="file" name="front" />
											&nbsp;&nbsp;
											<a href="javascript:submitOnlineWideImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>										
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
					
					<form name="koreanNetForm" id="koreanNetForm">
						
						<input type="hidden" 	name="pgmId" 		id="pgmId"			value="<c:out value='${param.pgmId}' />"			/>						
						
						<input type="hidden"	name="entpCd" 		id="entpCd"			value="<c:out value="${prodDetailVO.entpCd}" />"	/>
						<%-- <input type="hidden"	name="srcmkCd" 		id="srcmkCd"		value="<c:out value='${param.srcmk_cd}'/>"			/> --%>	<!-- 코리안넷에서 넘어온 판매코드 -->
						<%-- <input type="hidden"	name="srcmkCd" 		id="srcmkCd"		value="<c:out value='${prodDetailVO.sellCd}'/>"			/> --%>
					</form>
					
					<!-- 오프라인 이미지--------------------------------------------------------------------->
					<form name="offlineImageForm" id=offlineImageForm	action="<c:url value='/edi/product/NEDMPRO0022SaveOffImg.do'/>" method="POST" enctype="multipart/form-data">
					
						<input type="hidden" 	name="pgmId" 		id="pgmId"		value="<c:out value='${param.pgmId}' />" />
						
						<input type="hidden" 	name="prodImgId" 	id="prodImgId"	value="${prodDetailVO.prodImgId}" />
						<input type="hidden" 	name="cfmFg" 		id="cfmFg"		value="1" />	<!-- 1:심사중 -->
						<input type="hidden"	name="entpCd"		id="entpCd"		value="<c:out value="${prodDetailVO.entpCd}" />"		/>
						
										
						<%-- <div class="bbs_search">
							<ul class="tit" style="height: 50px;">
								<li class="tit" >
									<b>＊500kb 이하 크기의 jpg 파일만 업로드가 가능합니다.</b>
									<br>
									<font style="font-weight: bold; color: #FF0000;">이미지는 개별지정이 가능하며 일괄 지정 시 속성정보의 체크박스 선택 후 지정하실 수 있습니다.</font>
									<a href="#" class="btn" onclick="doAllApply();"><span><spring:message code="button.common.setall" /></span></a>
								</li>
								<li class="btn">
									<a href="javascript:submitOfflineImage();" class="btn"><span><spring:message code="button.common.save"/></span></a>
								</li>
							</ul>
						</div> --%>
						
						<div class="bbs_search">
							<ul class="tit" style="height: 30px;">
								<li class="tit" >
									<font style="font-weight: bold; color: #FF0000;">＊코리안넷에 등록된 이미지를 가져올 수 있습니다.(이미지를 가져오는데 시간이 걸릴 수 있습니다.)</font>
								</li>
							</ul>
						</div>
						
						<div class="bbs_search">
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								
								<c:if test="${not empty prodVarAttList}">
									<colgroup>
										<col  />
										<col  />
										<col  />
									</colgroup>
									<tr>
										<th colspan="3">속성 </th>
									</tr>
									<tr>									
										<th>정면 </th>
										<th>측면</th>
										<th>윗면</th>
									</tr>
									
									<c:forEach  var="list" items="${prodVarAttList}" varStatus="index">
										<tr>
											<td align="left" colspan="3" style="background-color: #C0D5F2;">
												<div align="left" style="position: absolute;">
													<!-- <input type="checkbox" id="chkAll" name="chkAll" /> -->
													<input type="hidden" id="chkVariant" name="chkVariant" value="<c:out value="${list.variant}" />" />
													<font style="font-weight: bold;">${list.attNm}</font>
												</div>
												<div align="right" style="margin-right: 10px;">
													<a href="#" class="btn" onclick="getKoreanNetImage('<c:out value="${list.sellCd}" />', '<c:out value="${list.variant}" />');"><span>코리안넷 이미지 가져오기</span></a>
												</div>
											</td>
										</tr>
										<tr style="height: 127px;">
											<td align="center">
												<img width="95px" src="${imagePath}/lim/static_root/images/edi/offline/${subFolderName }/${list.prodImgId}.1.jpg?currentSecond=${currentSecond}" onerror="setDefaultImage(this);" name="ImgPOG1f" >
											</td>
											<td align="center">
												<img width="95px" src="${imagePath}/lim/static_root/images/edi/offline/${subFolderName }/${list.prodImgId}.2.jpg?currentSecond=${currentSecond}" onerror="setDefaultImage(this);" name="ImgPOG1f" >
											</td>
											
											<td align="center" >
												<img width="95px" src="${imagePath}/lim/static_root/images/edi/offline/${subFolderName }/${list.prodImgId}.3.jpg?currentSecond=${currentSecond}" onerror="setDefaultImage(this);" name="ImgPOG1f" >
											</td>
										</tr>
										<%-- <tr>
											<td align="center">
												<input type="file" name="${list.prodImgId}_front" />
												<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
												<input type="hidden" id="variantAl" name="variantAl" value="<c:out value="${list.variant}" />" />
											</td>
											<td align="center">
												<input type="file" name="${list.prodImgId}_side" />
												<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
												<input type="hidden" id="variantAl" name="variantAl" value="<c:out value="${list.variant}" />" />
											</td>
											
											<td align="center" >
												<input type="file" name="${list.prodImgId}_top" />
												<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
												<input type="hidden" id="variantAl" name="variantAl" value="<c:out value="${list.variant}" />" />
											</td>
										</tr> --%>
										<tr>
											<td colspan="3"><div id="uploadOutput"></div></td>
										</tr>
									</c:forEach>
								</c:if>
								
								<c:if test="${empty prodVarAttList}">
									<colgroup>
										<col  />
										<col  />
										<col  />
									</colgroup>
									<tr>
										<td align="right" colspan="3" style="background-color: #FDDDDD;">
											<li class="tit" >
												<a href="#" class="btn" onclick="getKoreanNetImage('<c:out value='${prodDetailVO.sellCd}'/>', '000');"><span>코리안넷 이미지 가져오기</span></a>
											</li>
										</td>
									</tr>
									<tr>
										<th>정면 </th>
										<th>측면</th>
										<th>윗면</th>
									</tr>
									
									<tr>
										<td align="center">
											<img width="95px" src="${imagePath}/lim/static_root/images/edi/offline/${subFolderName}/${prodDetailVO.prodImgId}000.1.jpg?currentSecond=${currentSecond}" onerror="setDefaultImage(this);" name="ImgPOG1f" >
										</td>
										<td align="center">
											<img width="95px" src="${imagePath}/lim/static_root/images/edi/offline/${subFolderName}/${prodDetailVO.prodImgId}000.2.jpg?currentSecond=${currentSecond}" onerror="setDefaultImage(this);" name="ImgPOG1f" >
										</td>
										
										<td align="center" >
											<img width="95px" src="${imagePath}/lim/static_root/images/edi/offline/${subFolderName}/${prodDetailVO.prodImgId}000.3.jpg?currentSecond=${currentSecond}" onerror="setDefaultImage(this);" name="ImgPOG1f" >
										</td>
									</tr>
									<tr>
										<td align="center">
											<input type="file" name="${prodDetailVO.prodImgId}_front" />
											<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
											<input type="hidden" id="variantAl" name="variantAl" value="000" />
										</td>
										<td align="center">
											<input type="file" name="${prodDetailVO.prodImgId}_side" />
											<input type="hidden" id="prodImgIdAl" name="prodImgIdAl" value="<c:out value="${prodDetailVO.prodImgId}" />" />
											<input type="hidden" id="variantAl" name="variantAl" value="000" />
										</td>
										
										<td align="center" >
											<input type="file" name="${prodDetailVO.prodImgId}_top" />
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
	</div>		
</body>
</html>
