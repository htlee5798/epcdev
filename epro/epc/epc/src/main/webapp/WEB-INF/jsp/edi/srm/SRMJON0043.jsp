<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<%--
	Page Name 	: SRMJON0043.jsp
	Description : 입점상담신청[정보확인]
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<title><spring:message code='text.srm.field.srmjon0040.title'/></title><%--spring:message : 입점상담 신청하기--%>
 
<script language="JavaScript">
	$(document).ready(function() {
	});
	
	//탭이동
	function tab_page(page) {
		switch (page){
			case '0': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0040.do'/>");break;
			case '1': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0041.do'/>");break;
			case '2': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0042.do'/>");break;
			case '3': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0043.do'/>");break;
		}
		$("#hiddenForm").submit();
	}

	//입점상담신청
	function doSave(){
		//신용평가정보 확인
		var creditCheck = "";
		var check = false;

		//온라인몰은 신용평가 제외
		//임대매장은 신용평가 제외 17.07.05 
		if( "<c:out value="${srmComp.channelCode}"/>" != "06" &&  "<c:out value="${srmComp.channelCode}"/>" != "99" && "<c:out value="${srmComp.shipperType}"/>" != "2" ){
		
			if("<c:out value="${srmComp.scKind}"/>" == "C" || "<c:out value="${srmComp.localFoodYn}"/>" == "Y"){
				<%--creditCheck = "<spring:message code="edi.srm.credit.ratingC"/>";--%>
				creditCheck = "AAA|AA|AA+|AA-|AA0|A|A+|A-|A0|BBB|BBB+|BBB-|BBB0|BB|BB+|BB-|BB0|B|B+|B-|B0|CCC|CCC+|CCC-|CCC0";
			} else {
				<%--creditCheck = "<spring:message code="edi.srm.credit.ratingB"/>";--%>
				creditCheck = "AAA|AA|AA+|AA-|AA0|A|A+|A-|A0|BBB|BBB+|BBB-|BBB0|BB|BB+|BB-|BB0|B|B+|B-|B0";
			}

			var creditCheckArray = creditCheck.split("|");

			for(var i=0; i<creditCheckArray.length; i++){
				if("<c:out value="${srmComp.creditRating}"/>" == creditCheckArray[i]) {
					check = true;
					break;
				}
			}
			if(!check){
				var target = "";
				if("<c:out value="${srmComp.scKind}"/>" == "C" || "<c:out value="${srmComp.localFoodYn}"/>" == "Y"){
					target = "CCC-";
				} else {
					target = "B-";
				}
				alert("<spring:message code='msg.srm.alert.validation.creditRate' arguments='"+target+"'/>");
				return;
			}

		}


		if(!confirm("<spring:message code="msg.srm.alert.vendorConsultReq"/>")) return;/*spring:message : 입점상담신청을 완료 하시겠습니까?*/

		var saveInfo = {};
		saveInfo["houseCode"] = "<c:out value='${srmComp.houseCode}'/>";
		saveInfo["sellerCode"] = "<c:out value='${srmComp.sellerCode}'/>";
		saveInfo["reqSeq"] = "<c:out value='${srmComp.reqSeq}'/>";

		saveInfo["companyType"] = "<c:out value='${srmComp.companyType}'/>";
		saveInfo["companyRegNo"] = "<c:out value='${srmComp.companyRegNo}'/>";

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/updateHiddenCompReq.json"/>',
			data : JSON.stringify(saveInfo),
			success : function(data) {
				if (data.message == "SUCCESS") {
					alert('<spring:message code="msg.srm.alert.vendorConsultReqConplete"/>');/*spring:message : 입점상담신청이 정상적으로 신청완료 되었습니다.*/
					$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMRST0020.do'/>");
					$("#hiddenForm").submit();
				} else if(data.message == "FAIL-CAT_LV1_CODE_DEL"){
					alert("<spring:message code='msg.srm.alert.validation.catLv1Code'/>");/* spring:message : 삭제된 대분류 입니다.\n 대분류를 확인해 주세요. */
				} else if (data.message == "FAIL-CHANNEL_CODE") {
					//해외업체구분
					var target = "<spring:message code='text.srm.field.channelCode'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 판매처을(를) 선택하세요. */
				} else if (data.message == "FAIL-CAT_LV1_CODE") {
					//분류
					var target = "<spring:message code='text.srm.field.catLv1Code'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 대분류을(를) 선택하세요. */
				} else if (data.message == "FAIL-SHIPPER_TYPE") {
					//해외업체구분
					var target = "<spring:message code='text.srm.field.shipperType'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 해외업체구분을(를) 선택하세요. */
				} else if (data.message == "FAIL-TEMP_PW") {
					//비밀번호
					var target = "<spring:message code='text.srm.field.tempPw'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 비밀번호을(를) 입력하세요. */
				} else if (data.message == "FAIL-SELLER_NAME_LOC") {
					//상호명
					var target = "<spring:message code='text.srm.field.sellerNameLoc'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 사업자명을(를) 입력하세요. */
				} else if (data.message == "FAIL-COMPANY_TYPE") {
					//법인구분
					var target = "<spring:message code='text.srm.field.companyType'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 법인구분을(를) 선택하세요. */
				} else if (data.message == "FAIL-COMPANY_REG_NO") {
					//법인번호
					var target = "<spring:message code='text.srm.field.companyRegNo'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 법인구분을(를) 선택하세요. */
				} else if (data.message == "FAIL-SELLER_CEO_NAME") {
					//대표자명
					var target = "<spring:message code='text.srm.field.sellerCeoName'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표자명을(를) 입력하세요. */
				} else if (data.message == "FAIL-V_MAIN_NAME") {
					//담당자명
					var target = "<spring:message code='text.srm.field.vMainName'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 담당자명을(를) 입력하세요. */
				} else if (data.message == "FAIL-V_PHONE1") {
					//대표전화
					var target = "<spring:message code='text.srm.field.vPhone1'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표전화을(를) 입력하세요. */
				} else if (data.message == "FAIL-MOBILE_PHONE") {
					//휴대전화
					var target = "<spring:message code='text.srm.field.vMobilePhone'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 휴대전화을(를) 입력하세요. */
				} else if (data.message == "FAIL-SELLER_CEO_EMAIL") {
					//대표자 E-mail
					var target = "<spring:message code='text.srm.field.sellerCeoEmail'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표자 이메일을(를) 입력하세요. */
				} else if (data.message == "FAIL-V_EMAIL") {
					//E-mail
					var target = "<spring:message code='text.srm.field.vEmail'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 이메일을(를) 입력하세요. */
				} else if (data.message == "FAIL-ADDRESS") {
					//주소
					var target = "<spring:message code='text.srm.field.address'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 주소을(를) 입력하세요. */
				} else if (data.message == "FAIL-INDUSTRY_TYPE") {
					//업종
					var target = "<spring:message code='text.srm.field.industryType'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 업종을(를) 입력하세요. */
				} else if (data.message == "FAIL-BUSINESS_TYPE") {
					//업태
					var target = "<spring:message code='text.srm.field.businessType'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 업태을(를) 입력하세요. */
				} else if (data.message == "FAIL-SELLER_TYPE") {
					//업태
					var target = "<spring:message code='text.srm.field.sellerType'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 사업유형을(를) 선택하세요. */
				} else if (data.message == "NO_COMPANY_INFO") {
					alert("<spring:message code="msg.srm.alert.validation.NO_COMPANY_INFO"/>");/*spring:message 신용평가기관을 확인해주세요.*/
				} else if (data.message == "MISS_MATCH_INFO") {
					alert("<spring:message code="msg.srm.alert.validation.MISS_MATCH_INFO"/>");/*spring:message 신용평가정보가 일치하지 않습니다.*/
				} else if (data.message == "NO_CREDIT_INFO") {
					alert("<spring:message code="msg.srm.alert.validation.NO_CREDIT_INFO"/>");/*spring:message 일치하는 신용평가 정보가 없습니다.*/
				} else if (data.message == "END_CREDIT_DATE") {
					alert("<spring:message code="msg.srm.alert.validation.END_CREDIT_DATE"/>");/*spring:message 신용평가 정보는 유효기간이 한달이상 남아있어야 합니다.\\n신용평가기관에 재 신청 후 입점상담 신청을 진행해 주세요..*/
				} else if (data.message == "FAIL-CREDITE_ATTACH_NO") {
					//신용평가사본
					var target = "<spring:message code='text.srm.field.creditAttachNo'/>";
					alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");/* spring:message : 신용평가 사본을(를) 첨부하세요. */
				} else if (data.message == "FAIL-REFUSE") {
					//입점거절 3회
					alert('<spring:message code="msg.srm.alert.refuse"/>');/*spring:message : 연속 3회 입점거절되어 더이상 신청이 불가합니다.*/
				} else if (data.message == "FAIL-FOUNDATION_DATE") {
					//설립년도
					var target = "<spring:message code='text.srm.field.foundationDate'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 설립년도을(를) 선택하세요. */
				} else if (data.message == "FAIL-BASIC_AMT") {
					//자본금
					var target = "<spring:message code='text.srm.field.basicAmt'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 자본금을(를) 입력하세요. */
				} else if (data.message == "FAIL-SALES_AMT") {
					//연간 매출액
					var target = "<spring:message code='text.srm.field.salesAmt'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 연간 매출액을(를) 입력하세요. */
				} else if (data.message == "FAIL-EMP_COUNT") {
					//종업원수
					var target = "<spring:message code='text.srm.field.empCount'/>";
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 종업원수을(를) 입력하세요. */
				} else if (data.message == "FAIL-PLANT_ROLE_TYPE") {
					//공장운영형태
					var target = "<spring:message code='text.srm.field.plantRoleType'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 공장운영형태을(를) 선택하세요. */
				} else if (data.message == "FAIL-MAIN_CUSTOMER") {
					//동업계 입점현황
					var target = "<spring:message code='text.srm.field.mainCustomer'/>".replaceAll("<br>","");
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 동업계 입점현황을(를) 입력하세요. */
				} else if (data.message == "FAIL-ABOARD_CHANNEL_TEXT") {
					//롯데마트 기 진출 채널
					var target = "<spring:message code='text.srm.field.aboardChannelText'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 롯데마트 기 진출 채널을(를) 선택하세요. */
				} else if (data.message == "FAIL-ONLINE_MAIL_TARGET") {
					//온라인몰 메일 발송대상
					var target = "<spring:message code='text.srm.field.onlineMailTarget'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 온라인몰 메일 발송대상을(를) 선택하세요. */
				} else if (data.message == "FAIL-SM_FLAG") {
					//중소기업확인증
					var target = "<spring:message code='text.srm.field.smFlag'/>";
					alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 중소기업여부가 Y 인경우 중소기업확인증을(를) 첨부하세요. */
				} else if (data.message == "FAIL-SM_ATTACH_NO") {
					//중소기업확인증
					var target = "<spring:message code='text.srm.field.smAttachNo'/>";
					alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");/* spring:message : 중소기업여부가 Y 인경우 중소기업확인증을(를) 첨부하세요. */
				}
				
				
			}
		});
	}


	function downloadFile(fileId, fileSeq) {
		$('#fileForm input[name=atchFileId]').val(fileId);
		$('#fileForm input[name=fileSn]').val(fileSeq);
		$('#fileForm').attr("action", '<c:url value="/edi/srm/FileDown.do"/>')
		$('#fileForm').submit();
	}

	//입점상담신청 취소
	function doCancel() {
		if(!confirm("<spring:message code="msg.srm.alert.vendorConsultReqCancel"/>")) return;/*spring:message :입력하신 내용은 삭제됩니다. 입점상담신청을 취소 하시겠습니까?*/

		var saveInfo = {};
		saveInfo["houseCode"] = "<c:out value='${srmComp.houseCode}'/>";
		saveInfo["sellerCode"] = "<c:out value='${srmComp.sellerCode}'/>";
		saveInfo["reqSeq"] = "<c:out value='${srmComp.reqSeq}'/>";

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/updateHiddenCompReqCancel.json"/>',
			data : JSON.stringify(saveInfo),
			success : function(data) {
				if (data.message == "SUCCESS") {
					alert('<spring:message code="msg.srm.alert.vendorConsultReqCancelConplete"/>');/*spring:message : 입점상담신청 취소가 정상적으로 처리 되었습니다.*/
					$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON002001.do'/>");
					$("#hiddenForm").submit();
				}  else {
					alert('<spring:message code="msg.common.fail.request"/>');/*spring:message : 요청처리를 실패하였습니다.*/
				}
			}
		});
	}
	

	/* 입점상담 메뉴얼 다운로드 */
	function manualDown(fileNm) {
		$("#manualDownForm input[name='manulName']").val(fileNm);
		$('#manualDownForm').attr("action", '<c:url value="/edi/srm/manulDown.do"/>');
		$("#manualDownForm").submit();
	}

</script>


</head>


<body>
<form id="MyForm" name="MyForm" method="post">
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<ul class="inner sub_wrap">

			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>
				<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span> <span><spring:message code='text.srm.field.srmjon0040.title' /></span></p>
			</div><!-- END 서브상단 -->

			<!-- 알림 -->
			<div class="noti_box">
				<ul class="noti_list float-l">
					<li class="txt_l"><em><spring:message code="text.srm.field.srmjon0040Notice1"/></em></li>	<%-- 정보 누락 및 불일치 시 입점 상담 등의 절차가 지연되거나 중단 혹은 취소될 수 있습니다. --%>
					<li class="txt_1"><em><spring:message code="text.srm.field.srmjon0040Notice6"/></em></li>	<%-- 임점심사 중 공장실사가 진행되며 체크리스트는 평가기관이 정해지면 해당기관에서 별도 안내예정입니다. --%>
				</ul>
				<!-- 
				<p class="float-r">
					<a href="#" class="btn_redround" onclick="manualDown('5');"><spring:message code='text.srm.field.step.tab8'/> <img src="/images/epc/edi/srm/sub/icon_download.png" alt="<spring:message code='text.srm.field.step.tab8'/>"></a>	<%-- 체크리스트 --%>
				</p>
				 -->
			</div><!-- END 알림 -->

			<!-- 정보 입력 유형 탭 : 선택된 li에 클래스 on을 붙입니다.-->
			<ul class="sub_tab col4">
				<li rel="tab1"><a href="#" onclick="tab_page('0');"><spring:message code='tab.srm.srmjon0030.tab1'/></a></li>					<%-- 기본정보--%>
				<li rel="tab2"><a href="#" onClick="tab_page('1');"><spring:message code='tab.srm.srmjon0030.tab2'/></a></li>	<%-- 상세정보--%>
				<li rel="tab3"><a href="#" onClick="tab_page('2');"><spring:message code='tab.srm.srmjon0030.tab3'/></a></li>	<%-- 인증/신용평가 정보--%>
				<li class="on" rel="tab4"><a href="#" onClick=""><spring:message code='tab.srm.srmjon0030.tab4'/></a></li>	<%-- 정보확인--%>
			</ul><!-- END 정보 입력 유형 탭 -->
			
			<%----- 상담채널 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmevl003002.sub.title1"/></h3>	<%-- 운영현황--%>
				<div class="right_btns">
					<button type="button" class="btn_normal btn_blue" onclick="javascript:doSave();"><spring:message code='button.srm.vendorConsultReq'/></button>	<%-- 임시저장--%>
					<button type="button" class="btn_normal btn_red" onclick="javascript:doCancel();"><spring:message code='button.srm.vendorConsultReqCancel'/></button>	<%-- 입점상담신청 취소--%>
				</div>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code='text.srm.field.channelCode'/></th><%--spring:message : 채널--%>
						<td>
							<c:out value="${srmComp.channelCodeNm}"/>
						</td>
						<th><spring:message code='text.srm.field.catLv1Code'/></th><%--spring:message : 대분류--%>
						<td>
							<c:out value="${srmComp.catLv1CodeNm}"/>
						</td>
					</tr>

					<tr>
						<th><spring:message code='text.srm.field.localFoodYn'/></th><%--spring:message : 생산공장유무--%>
						<td <c:if test="${srmComp.channelCode ne '06'}"> colspan="3" </c:if>>
							<c:if test="${srmComp.localFoodYn eq 'Y'}"> <spring:message code='text.srm.field.srmjon0043.plantOwnType1' /></c:if><%--spring:message : 유--%>
							<c:if test="${srmComp.localFoodYn ne 'Y'}"> <spring:message code='text.srm.field.srmjon0043.plantOwnType2' /></c:if><%--spring:message : 무--%>
						</td>
						<c:if test="${srmComp.channelCode eq '06'}">
							<th><spring:message code='text.srm.field.onlineMailTarget'/></th><%--spring:message : 공장운영형태--%>
							<td>
								<c:out value="${srmComp.onlineMailTargetNm}"/>
							</td>
						</c:if>
					</tr>
				</tbody>
			</table>
			<%----- 상담채널 End -------------------------%>
			
			
			<%----- 기업정보 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0040.sub.title2'/></h3>	<%-- 운영현황--%>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<th><spring:message code='text.srm.field.shipperType'/></th><%--spring:message : 해외업체구분--%>
					<td>
						<c:out value="${srmComp.shipperTypeNm}"/>
					</td>
					<th><spring:message code='text.srm.field.irsNo'/></th><%--spring:message : 사업자 등록번호--%>
					<td>
						<c:if test="${srmComp.shipperType eq '1'}">
							<c:out value="${fn:substring(srmComp.irsNo,0,3)}"/>-<c:out value="${fn:substring(srmComp.irsNo,3,5)}"/>-<c:out value="${fn:substring(srmComp.irsNo,5,10)}"/>
						</c:if>
						<c:if test="${srmComp.shipperType eq '2'}">
							<c:out value="${srmComp.irsNo}"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.sellerNameLoc'/></th><%--spring:message : 상호명--%>
					<td>
						<c:out value="${srmComp.sellerNameLoc}"/>
					</td>
					<th><spring:message code='text.srm.field.sellerNameEng'/></th><%--spring:message : 상호명--%>
					<td>
						<c:out value="${srmComp.sellerNameEng}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.companyType'/></th><%--spring:message : 법인구분--%>
					<td>
						<c:out value="${srmComp.companyTypeNm}"/>
					</td>
					<th><spring:message code='text.srm.field.companyRegNo'/></th><%--spring:message : 법인번호--%>
					<td>
						<c:out value='${srmComp.companyRegNo}'/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.sellerCeoName'/></th><%--spring:message : 대표자명--%>
					<td>
						<c:out value="${srmComp.sellerCeoName}"/>
					</td>
					<th><spring:message code='text.srm.field.vMainName'/></th><%--spring:message : 담당자명--%>
					<td>
						<c:out value="${srmComp.vMainName}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.vPhone1'/></th><%--spring:message : 대표전화--%>
					<td>
						<c:out value="${srmComp.vPhone1}"/>
					</td>
					<th><spring:message code='text.srm.field.vMobilePhone'/></th><%--spring:message : 휴대전화--%>
					<td>
						<c:out value="${srmComp.vMobilePhone}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.sellerCeoEmail'/></th><%--spring:message : 대표자 이메일--%>
					<td>
						<c:out value="${srmComp.sellerCeoEmail}"/>
					</td>
					<th><spring:message code='text.srm.field.vEmail'/></th><%--spring:message : E-Mail--%>
					<td>
						<c:out value="${srmComp.vEmail}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.faxFhone'/></th><%--spring:message : FAX--%>
					<td colspan="3">
						<c:out value="${srmComp.faxFhone}"/>
					</td>
					<%--<th><spring:message code='text.srm.field.phoneNo'/></th>&lt;%&ndash;spring:message : 담당자연락처&ndash;%&gt;--%>
					<%--<td>--%>
						<%--<c:out value='${srmComp.phoneNo}'/>--%>
					<%--</td>--%>

				</tr>
				<tr>
					<th><spring:message code='text.srm.field.address'/></th><%--spring:message : 주소--%>
					<td colspan="3">

						<c:if test="${srmComp.shipperType eq '1'}">
							(<c:out value="${srmComp.zipcode}"/>) <c:out value="${srmComp.address1}"/> <c:out value="${srmComp.address2}"/>
						</c:if>
						<c:if test="${srmComp.shipperType eq '2'}">
							<c:out value="${srmComp.address1}"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.industryType'/></th><%--spring:message : 업종--%>
					<td>
						<c:out value="${srmComp.businessType}"/>
					</td>
					<th><spring:message code='text.srm.field.businessType'/></th><%--spring:message : 업태--%>
					<td>
						<c:out value="${srmComp.industryType}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.sellerType'/></th><%--spring:message : 사업유형--%>
					<td>
						<c:out value="${srmComp.sellerTypeNm}"/>
					</td>
					<th><spring:message code='text.srm.field.country'/>/<spring:message code='text.srm.field.cityText'/></th><%--spring:message : 국가--%><%--spring:message : 지역--%>
					<td>
						<c:out value="${srmComp.countryNm}"/> / <c:out value="${srmComp.cityTextNm}"/>
					</td>
				</tr>
			</table>
			<%----- 기업정보 End -------------------------%>
			
			
			<%----- 운영현황 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0041.sub.title1'/></h3>	<%-- 운영현황--%>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<th><spring:message code='text.srm.field.foundationDate'/></th><%--spring:message : 설립년도--%>
					<td>
						<c:out value="${srmComp.foundationDate}"/> <spring:message code='text.srm.field.year' /><%--spring:messge : 년--%>						</td>
					<th><spring:message code='text.srm.field.basicAmt'/></th><%--spring:message : 자본금--%>
					<td>
						<fmt:formatNumber value="${srmComp.basicAmt}" pattern="#,###" /><spring:message code='text.srm.field.unit'/><%--spring:message : (단위:백만원)--%>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.salesAmt'/></th><%--spring:message : 연간 매출액(최근 3년)--%>
					<td>
						<fmt:formatNumber value="${srmComp.salesAmt}" pattern="#,###" /><spring:message code='text.srm.field.unit'/><%--spring:message : (단위:백만원)--%>
					</td>
					<th><spring:message code='text.srm.field.empCount'/></th><%--spring:message : 종업원 수(정규직)--%>
					<td>
						<fmt:formatNumber value="${srmComp.empCount}" pattern="#,###" /><spring:message code='text.srm.field.persons'/><%--spring:message : 명--%>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.plantOwnType'/></th><%--spring:message : 생산공장유무--%>
					<td>
						<c:if test="${srmComp.plantOwnType eq 'X'}"> <spring:message code='text.srm.field.srmjon0043.plantOwnType1' /></c:if><%--spring:message : 유--%>
						<c:if test="${srmComp.plantOwnType ne 'X'}"> <spring:message code='text.srm.field.srmjon0043.plantOwnType2' /></c:if><%--spring:message : 무--%>
					</td>
					<th><spring:message code='text.srm.field.plantRoleType'/></th><%--spring:message : 공장운영형태--%>
					<td>
						<c:out value="${srmComp.plantRoleTypeNm}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.smFlag'/></th><%--spring:message : 중소기업여부--%>
					<td colspan="3">
						<c:if test="${srmComp.smFlag eq 'Y'}"> <spring:message code='text.srm.field.srmjon0043.smType1' /></c:if><%--spring:message : Y--%>
						<c:if test="${srmComp.smFlag ne 'Y'}"> <spring:message code='text.srm.field.srmjon0043.smType2' /></c:if><%--spring:message : N--%>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.smAttachNo'/></th><%--spring:message : 중소기업 확인증--%>
					<td colspan="3">
						<a href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.smAttachNo}"/>,'1');"><c:out value="${srmComp.smAttachNoName}"/></a>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.mainCustomer'/></th><%--spring:message : 동업계 입점현황<br>(주요거래 업체)--%>
					<td colspan="3">
						<c:out value="${srmComp.mainCustomer}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.aboardChannelText'/></th><%--spring:message : 롯데마트 기 진출 채널--%>
					<td>
						<c:out value="${srmComp.aboardChannelTextNm}"/>
					</td>
					<th><spring:message code='text.srm.field.aboardCountryText'/></th><%--spring:message : 롯데마트 기 진출 국가--%>
					<td>
						<c:out value="${srmComp.aboardCountryText}"/>
					</td>
				</tr>
			</table>
			<%----- 운영현황 End -------------------------%>
			
			
			<%----- 상품현황 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0041.sub.title2'/></h3>	<%-- 운영현황--%>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<th><spring:message code='text.srm.field.productName'/></th><%--spring:message : 대표상품명--%>
					<td>
						<c:out value="${srmComp.productName}"/>
					</td>
					<th><spring:message code='text.srm.field.productPrice'/>/<spring:message code='text.srm.field.cur'/></th><%--spring:message : 납품가/통화--%>
					<td>
						<fmt:formatNumber value="${srmComp.productPrice}" pattern="#,###" /> / <c:out value="${srmComp.curNm}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.productImgPath'/></th><%--spring:message : 상품이미지--%>
					<td colspan="3">
						<a href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.productImgPath}"/>,'1');"><c:out value="${srmComp.productImgPathName}"/></a>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.productIntroAttachNo'/></th><%--spring:message : 상품소개서--%>
					<td colspan="3">
						<a href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.productIntroAttachNo}"/>,'1');"><c:out value="${srmComp.productIntroAttachNoName}"/></a>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.companyIntroAttachNo'/></th><%--spring:message : 사업설명서--%>
					<td colspan="3">
						<a href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.companyIntroAttachNo}"/>,'1');"><c:out value="${srmComp.companyIntroAttachNoName}"/></a>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.mainProduct'/></th><%--spring:message : 주요상품--%>
					<td colspan="3">
						<c:out value="${srmComp.mainProduct}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.dealingBrandProduct'/></th><%--spring:message : 주사용 브랜드--%>
					<td colspan="3">
						<c:out value="${srmComp.dealingBrandProduct}"/>
					</td>
				</tr>
			</table>
			<%----- 상품현황 End -------------------------%>
			
			
			<%----- 인증정보 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0042.sub.title1'/></h3>	<%-- 운영현황--%>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
				</colgroup>
				<tr>
					<td>
						<input type="checkbox" id="zzqcFg1" name="zzqcFg1" disabled title="<spring:message code='checkbox.srm.zzqcFg1'/>" <c:if test="${srmComp.zzqcFg1 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg1'/><%--spring:message : HACCP--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg2" name="zzqcFg2" disabled title="<spring:message code='checkbox.srm.zzqcFg2'/>" <c:if test="${srmComp.zzqcFg2 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg2'/><%--spring:message : FSSC22000--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg3" name="zzqcFg3" disabled title="<spring:message code='checkbox.srm.zzqcFg3'/>" <c:if test="${srmComp.zzqcFg3 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg3'/><%--spring:message : ISO 22000--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg4" name="zzqcFg4" disabled title="<spring:message code='checkbox.srm.zzqcFg4'/>" <c:if test="${srmComp.zzqcFg4 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg4'/><%--spring:message : GMP인증--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg5" name="zzqcFg5" disabled title="<spring:message code='checkbox.srm.zzqcFg5'/>" <c:if test="${srmComp.zzqcFg5 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg5'/><%--spring:message : KS인증--%>
					</td>
				</tr>
				<tr>
					<td>
						<input type="checkbox" id="zzqcFg6" name="zzqcFg6" disabled title="<spring:message code='checkbox.srm.zzqcFg6'/>" <c:if test="${srmComp.zzqcFg6 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg6'/><%--spring:message : 우수농산품(GAP)인증--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg7" name="zzqcFg7" disabled title="<spring:message code='checkbox.srm.zzqcFg7'/>" <c:if test="${srmComp.zzqcFg7 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg7'/><%--spring:message : 유기농식품인증--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg8" name="zzqcFg8" disabled title="<spring:message code='checkbox.srm.zzqcFg8'/>" <c:if test="${srmComp.zzqcFg8 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg8'/><%--spring:message : 전통식품품질인증--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg9" name="zzqcFg9" disabled title="<spring:message code='checkbox.srm.zzqcFg9'/>" <c:if test="${srmComp.zzqcFg9 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg9'/><%--spring:message : ISO 9001--%>
					</td>
					<td>
						<input type="checkbox" id="zzqcFg10" name="zzqcFg10" disabled title="<spring:message code='checkbox.srm.zzqcFg10'/>" <c:if test="${srmComp.zzqcFg10 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg10'/><%--spring:message : 수산물품질인증--%>
					</td>
				</tr>
				<tr>
					<td>
						<input type="checkbox" id="zzqcFg11" name="zzqcFg11" disabled title="<spring:message code='checkbox.srm.zzqcFg11'/>" <c:if test="${srmComp.zzqcFg11 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg11'/><%--spring:message : PAS 220--%>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="5">
						<spring:message code='checkbox.srm.zzqcFg12'/> :<%--spring:message : 기타인증--%>
						<c:out value='${srmComp.zzqcFg12}'/>
					</td>
				</tr>
			</table>
			<%----- 인증정보 End -------------------------%>
			
			
			<%----- 신용평가정보 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0042.sub.title2'/></h3>	<%-- 운영현황--%>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<%--<th><spring:message code='text.srm.field.creditNo'/></th>&lt;%&ndash;spring:message : 평가번호(DNA)&ndash;%&gt;--%>
					<%--<td>--%>
						<%--<c:out value="${srmComp.creditNo}"/>--%>
					<%--</td>--%>

					<th><spring:message code='text.srm.field.creditRating'/></th><%--spring:message : 신용등급--%>
					<td>
						<c:out value="${srmComp.creditRating}"/>
					</td>
					<th><spring:message code='text.srm.field.creditCompanyCode'/></th><%--spring:message : 신용평가사--%>
					<td>
						<c:out value="${srmComp.creditCompanyCodeNm}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.creditBasicDate'/></th><%--spring:message : 신용평가 기준일자--%>
					<td colspan="3">
						<c:out value="${fn:substring(srmComp.creditBasicDate,0,4)}"/>-<c:out value="${fn:substring(srmComp.creditBasicDate,4,6)}"/>-<c:out value="${fn:substring(srmComp.creditBasicDate,6,8)}"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code='text.srm.field.creditAttachNo'/></th><%--spring:message : 신용평가 사본--%>
					<td colspan="3">
						<a href="#" onclick="javascript:downloadFile(<c:out value="${srmComp.creditAttachNo}"/>,'1');"><c:out value="${srmComp.creditAttachNoName}"/></a>
					</td>
				</tr>
			</table>
			<%----- 신용평가정보 End -------------------------%>
			
		</div>
	</div>

</form>


<form id="hiddenForm" name="hiddenForm" method="post">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value='${srmComp.houseCode}'/>"/>
	<input type="hidden" id="sellerCode" name="sellerCode" value="<c:out value='${srmComp.sellerCode}'/>"/>
	<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value='${srmComp.reqSeq}'/>"/>
</form>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>