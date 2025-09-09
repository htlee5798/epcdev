<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMFAQ001002.jsp
	Description : 입점상당 FAQ 글 작성
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2024.07.16  	NBM				 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="<c:out value='${namoPath}'/>"></script>

<title><spring:message code='text.srm.field.srmfaq0010.title1'/></title><%--FAQ --%>

</head>

<body>
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page">QNA</h2>
				<p class="page_path">HOME <span>푸드코드/임대매장 상담작성</span></p>
			</div><!-- END 서브상단 -->


			<!-- Board wrap -->
			<div class="board-wrap">
				<strong style="font-size: 12pt; margin-left: 20px;">1. [개인정보 수집 항목, 수집 • 이용 목적 및 보유 및 이용기간]</strong>

				<p style="font-size: 10pt; line-height: 1.5em; padding: 10px;">
					롯데쇼핑(주)롯데마트는 개인정보보호법 등 관련 법령상의 개인정보 규정을 준수하고 있습니다.<br />
					개인정보의 수집 항목과 목적, 기간은 아래와 같으며, 수집된 정보는 본 수집/이용 목적 외에 다른 목적으로는 사용되지 않습니다.<br />
				</p>

				<jsp:include page="agree_ko_faq_ver1.0.html" />
				<br/>
				<input type="checkbox" class="notAgreeCheck" /> 개인정보처리방침에 동의합니다.
				<br/><br/>
				<!-- 일반 게시판 리스트 -->
				<div class="faq-list">
					<!-- <p class="no-ct">등록된 게시글이 없습니다.</p> -->

				<!-- END 일반 게시판 리스트 -->
			</div><!-- END Board wrap -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<th><label for="shipperType">제목</label></th><%--spring:message : 해외업체구분--%>
					<td colspan="3">
						<input type="text" id="sellerNameLoc" name="sellerNameLoc" title="<spring:message code='text.srm.field.sellerNameLoc'/>" value="<c:out value='${srmComp.sellerNameLoc}'/>" style="width: 90%;" />
					</td>
				</tr>
				<tr>
					<th><label for="shipperType">카테고리</label></th><%--spring:message : 해외업체구분--%>
					<td colspan="3">
<%--							<input type="text" id="sellerNameLoc" name="sellerNameLoc" title="<spring:message code='text.srm.field.sellerNameLoc'/>" value="<c:out value='${srmComp.sellerNameLoc}'/>" style="width: 90%;" />--%>
					</td>
				</tr>
				<tr>
					<th><label for="sellerCeoName">성함</label></th><%--spring:message : 대표자명--%>
					<td>
						<input type="text" id="sellerCeoName" name="sellerCeoName" title="<spring:message code='text.srm.field.sellerCeoName'/>" value="<c:out value='${srmComp.sellerCeoName}'/>" />
					</td>
					<th><label for="vEmail">이메일</label></th><%--spring:message : E-Mail--%>
					<td>
						<input type="text" id="vEmail" name="vEmail" title="<spring:message code='text.srm.field.vEmail'/>" value="<c:out value='${srmComp.vEmail}'/>"  style="width:90%"/>
					</td>
				</tr>

				<tr>
					<th><label for="sellerNameLoc">상호명</label></th><%--spring:message : 상호명--%>
					<td>
						<input type="text" id="sellerNameLoc" name="sellerNameLoc" title="<spring:message code='text.srm.field.sellerNameLoc'/>" value="<c:out value='${srmComp.sellerNameLoc}'/>" style="width: 90%;" />
					</td>
					<th><label for="vMobilePhone">휴대전화</label></th><%--spring:message : 휴대전화--%>
					<td>
						<input type="text" id="vMobilePhone" name="vMobilePhone" title="<spring:message code='text.srm.field.vMobilePhone'/>" value="<c:out value='${srmComp.vMobilePhone}'/>" class="input_txt_default numberic" />
					</td>
				</tr>
				<tr>
					<th><label for="shipperType">희망점포</label></th><%--spring:message : 해외업체구분--%>
					<td colspan="3">
						<%--							<input type="text" id="sellerNameLoc" name="sellerNameLoc" title="<spring:message code='text.srm.field.sellerNameLoc'/>" value="<c:out value='${srmComp.sellerNameLoc}'/>" style="width: 90%;" />--%>
					</td>
				</tr>
				<tr>
					<th><label for="shipperType">내용</label></th><%--spring:message : 해외업체구분--%>
					<td colspan="3">
						<!-- <textarea id="pe_agt" name="pe_agt" alt="initText" title="initText" Style="width:730px;height:450px;font-size:10pt"><p>Welcome to <span style="font-weight:bold;">CrossEditor 3.0</span> sample page</p></textarea> -->
						<input type="hidden" name="viewProdDesc" id="viewProdDesc"
							   value="<c:out value='${newProdDetailInfo.prodDesc}'/>">
						<script type="text/javascript" language="javascript">
							var CrossEditor = new NamoSE('pe_agt');

							CrossEditor.params.Width 		= "100%";
							CrossEditor.params.UserLang 	= "auto";
							CrossEditor.params.FullScreen 	= false;
							CrossEditor.params.SetFocus 	= false; // 에디터 포커스 해제
							CrossEditor.params.ImageSavePath	= "productdetail";
							CrossEditor.params.AccessibilityOption = 1;
							CrossEditor.params.Template = [
								{
									title : "Advanced PD",
									url : "/epc/namoCross/template/advancedPd.html",
									charset : "utf-8"
								},
								{
									title : "온오프상품 비식품",
									url : "/epc/namoCross/template/onOffProdNonFood.html",
									charset : "utf-8"
								},
								{
									title : "온오프상품 식품",
									url : "/epc/namoCross/template/onOffProdFood.html",
									charset : "utf-8"
								}
							];
							CrossEditor.params.ActiveTab = 40;
							CrossEditor.params.UploadImageFileExtBlockList = ["jpg","JPG","jpeg","JPEG"];
							CrossEditor.params.Skin = "white";
							CrossEditor.EditorStart();

							function OnInitCompleted(e){
								e.editorTarget.SetBodyStyle('text-align','center');
								e.editorTarget.SetBodyValue(document.getElementById("viewProdDesc").value);
							}
						</script>
					</td>
				</tr>
				<tr>
					<th><label for="productImgPath">상당첨부파일</label></th><%--spring:message : 상품이미지--%>
					<td colspan="3">
						<input type="hidden" id="productImgPathFileName" name="productImgPathFileName" title="<spring:message code='text.srm.field.productImgPath'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="productImgPathFile" name="productImgPathFile" title="<spring:message code='text.srm.field.productImgPath'/>" onchange="javascript:fileUpload(this, 'productImgPathFileName');" accept="image/*"/>
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('productImgPathFile','productImgPathFileName')"/><%--spring:message : 취소--%>
							<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('productImgPath', 'productImgPathA');"/><%--spring:message : 삭제--%>
							<btn>
								<div style="color:red;"><spring:message code='text.srm.field.srmjon0042Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 올려주세요.--%>
								<c:if test="${not empty srmComp.productImgPath}">
								<div>
									<input type="hidden" id="productImgPath" name="productImgPath" value="<c:out value="${srmComp.productImgPath}"/>"/>
									<a id="productImgPathA" name="productImgPathA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.productImgPath}"/>,'1');"><c:out value="${srmComp.productImgPathName}"/></a>
								</div>
								</c:if>
					</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<th><label for="productImgPath">회사소개서(개인Profile)</label></th><%--spring:message : 상품이미지--%>
					<td colspan="3">
						<input type="hidden" id="productImgPathFileName" name="productImgPathFileName" title="<spring:message code='text.srm.field.productImgPath'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="productImgPathFile" name="productImgPathFile" title="<spring:message code='text.srm.field.productImgPath'/>" onchange="javascript:fileUpload(this, 'productImgPathFileName');" accept="image/*"/>
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('productImgPathFile','productImgPathFileName')"/><%--spring:message : 취소--%>
							<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('productImgPath', 'productImgPathA');"/><%--spring:message : 삭제--%>
							<btn>
								<div style="color:red;"><spring:message code='text.srm.field.srmjon0042Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 올려주세요.--%>
								<c:if test="${not empty srmComp.productImgPath}">
								<div>
									<input type="hidden" id="productImgPath" name="productImgPath" value="<c:out value="${srmComp.productImgPath}"/>"/>
									<a id="productImgPathA" name="productImgPathA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.productImgPath}"/>,'1');"><c:out value="${srmComp.productImgPathName}"/></a>
								</div>
								</c:if>
					</td>
				</tr>
				<tr>
					<th><label for="productIntroAttachNo">사업계획서 or 브랜드소개서</label></th><%--spring:message : 상품소개서--%>
					<td colspan="3">
						<input type="hidden" id="productIntroAttachNoFileName" name="productIntroAttachNoFileName" title="<spring:message code='text.srm.field.productIntroAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="productIntroAttachNoFile" name="productIntroAttachNoFile" title="<spring:message code='text.srm.field.productIntroAttachNo'/>" onchange="javascript:fileUpload(this, 'productIntroAttachNoFileName');"/>
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('productIntroAttachNoFile','productIntroAttachNoFileName')"/><%--spring:message : 취소--%>
							<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('productIntroAttachNo', 'productIntroAttachNoA');"/><%--spring:message : 삭제--%>
							<btn>
								<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 올려주세요.--%>
								<c:if test="${not empty srmComp.productIntroAttachNo}">
								<div>
									<input type="hidden" id="productIntroAttachNo" name="productIntroAttachNo" value="<c:out value="${srmComp.productIntroAttachNo}"/>"/>
									<a id="productIntroAttachNoA" name="productIntroAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.productIntroAttachNo}"/>,'1');"><c:out value="${srmComp.productIntroAttachNoName}"/></a>
								</div>
								</c:if>
					</td>
				</tr>
				<tr>
					<th><label for="companyIntroAttachNo">사용자등록증사본(개인제외)</label></th><%--spring:message : 사업설명서--%>
					<td colspan="3">
						<input type="hidden" id="companyIntroAttachNoFileName" name="companyIntroAttachNoFileName" title="<spring:message code='text.srm.field.companyIntroAttachNo'/>" class="input_txt_default" disabled="disabled" readonly="readonly"/>
						<input type="file" id="companyIntroAttachNoFile" name="companyIntroAttachNoFile" title="<spring:message code='text.srm.field.companyIntroAttachNo'/>" onchange="javascript:fileUpload(this, 'companyIntroAttachNoFileName');"/>
						<btn>
							<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear('companyIntroAttachNoFile','companyIntroAttachNoFileName')"/><%--spring:message : 취소--%>
							<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete('companyIntroAttachNo', 'companyIntroAttachNoA');"/><%--spring:message : 삭제--%>
							<btn>
								<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div><%--spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 올려주세요.--%>
								<c:if test="${not empty srmComp.companyIntroAttachNo}">
								<div>
									<input type="hidden" id="companyIntroAttachNo" name="companyIntroAttachNo" value="<c:out value="${srmComp.companyIntroAttachNo}"/>"/>
									<a id="companyIntroAttachNoA" name="companyIntroAttachNoA" href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.companyIntroAttachNo}"/>,'1');"><c:out value="${srmComp.companyIntroAttachNoName}"/></a>
								</div>
								</c:if>
					</td>
				</tr>
			</table>

			<div class="tit_btns">

				<div class="right_btns">
					<button type="button" class="btn_normal btn_blue" onclick="javascript:doTempSave();">작성</button>	<%-- 임시저장--%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>