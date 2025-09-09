<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMSTP0050.jsp
	Description : 절차안내(지원구매)
	Modification Information
	
	수정일      			수정자           			수정내용
	-----------    	------------    ------------------
	2016.07.28  	AN TAE KYUNG	 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code='text.srm.field.VendorConsult2' /></title><%--입점상담 신청하기 --%>

<script>
	function goConsult() {
		var url ='<c:url value="/epc/edi/consult/NEDMCST0310login.do"/>'; /* 팝업창 주소 */
		Common.centerPopupWindow(url, 'ediConsult', {width : 913, height : 768,  scrollBars :"YES"});
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
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="header.menu.text1" /></h2>	<%-- 입점상담 절차안내 --%>
				<p class="page_path">HOME <span><spring:message code="header.menu.text1" /></span> <span><spring:message code="text.srm.field.step.tab5" /></span></p>
			</div><!-- END 서브상단 -->

			<!-- 컨텐츠 -->
			<div class="content">

				<!-- 서브 탭 -->
				<ul class="sub_tab">
					<li><a href="<c:url value="/edi/srm/SRMSTP0010.do" />"><spring:message code="text.srm.field.step.tab1" /></a></li>				<!-- 롯데마트 -->
					<li><a href="<c:url value="/edi/srm/SRMSTP0020.do" />"><spring:message code="text.srm.field.step.tab2" /></a></li>				<!-- VIC마켓 -->
					<li><a href="<c:url value="/edi/srm/SRMSTP0030.do" />"><spring:message code="text.srm.field.step.tab3" /></a></li>				<!-- 롯데마트몰(Online) -->
					<li><a href="<c:url value="/edi/srm/SRMSTP0040.do" />"><spring:message code="text.srm.field.step.tab4" /></a></li>				<!-- 임대매장 -->
					<li class="on"><a href="<c:url value="/edi/srm/SRMSTP0050.do" />"><spring:message code="text.srm.field.step.tab5" /></a></li>	<!-- 지원구매 -->
				</ul><!-- END 서브 탭 -->

				<h3 class="tit_star"><spring:message code="text.srm.field.srmstp0050.title" /></h3>	<%-- 지원구매는 --%>
				<p class="pl15"><spring:message code="text.srm.field.srmstp0050.contents" /></p>	<%-- 롯데마트의 내부에서 사용하는 집기, 소모품 및 공사시행을 진행합니다. --%>
				
				<h3 class="tit_star"><spring:message code="text.srm.filed.step.process" /></h3>		<%-- 입점상담 절차안내 --%>
				
				<!-- 절차 -->
				<ul class="process_step">
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step1" /></span><em><spring:message code="text.srm.field.step.process1" /></em></div></div></li>	<%-- 상담신청 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step2" /></span><em><spring:message code="text.srm.field.step.process2" /></em></div></div></li>	<%-- 상담접수 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step3" /></span><em><spring:message code="text.srm.field.step.process7" /></em></div></div></li>	<%-- 업체평가 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step4" /></span><em><spring:message code="text.srm.field.step.process8" /></em></div></div></li>	<%-- 업체Pool 등록 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step5" /></span><em><spring:message code="text.srm.field.step.process9" /></em></div></div></li>	<%-- 입찰기회부여 --%>
				</ul><!-- END 절차 -->

				<!-- 테이블1 -->
				<table class="tbl_st1">
					<colgroup>
						<col style="width:170px;">
						<col>
					</colgroup>
					<tbody>
						<tr id="p1">
							<th><spring:message code="text.srm.field.step.process1" /></th>	<%-- 상담신청 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0050.process1" />
								<%-- 당사 홈페이지 입점상담 문의를 통해 접속하여 필요한 정보를 입력 후 입점상담 신청합니다. --%>
							</td>
						</tr>
						<tr id="p2">
							<th><spring:message code="text.srm.field.step.process2" /></th>	<%-- 상담접수 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0050.process2" />
								<%-- 신청한 정보를 평가하여 상담 여부를 결정하게 되며, 일정 및 세부사항에 대해 안내해드립니다. --%>
							</td>
						</tr>
						<tr id="p3">
							<th><spring:message code="text.srm.field.step.process7" /></th>	<%-- 업체평가 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0050.process3" />
								<%-- 귀사의 상담내용에 대한 평가를 진행합니다. --%>
							</td>
						</tr>
						<tr id="p4">
							<th><spring:message code="text.srm.field.step.process8" /></th>	<%-- 업체POOL등록 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0050.process4" />
								<%-- 입점 확정 결정에 따라 관련된 서류구비 및 계약서 작성을 진행합니다.
								<br>
								입점 확정 후 MD협의에 따라 진행됩니다. --%>
							</td>
						</tr>
						<tr id="p5">
							<th><spring:message code="text.srm.field.step.process9" /></th>	<%-- 입찰기회 부여 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0050.process5" />
								<%-- 입점 확정 결정에 따라 관련된 서류구비 및 계약서 작성을 진행합니다.
								<br>
								입점 확정 후 MD협의에 따라 진행됩니다. --%>
							</td>
						</tr>
					</tbody>
				</table><!-- END 테이블1 -->
				
				<div class="tit_btns">
					<div class="right_btns" style="text-align: center;">
						<button type="button" class="btn_normal btn_blue" style="width: 200px; height: 50px;" onClick="goConsult();"><font style="font-size: 13pt; font-weight: bold;"><spring:message code='header.menu.text2' /></font></button><%--입점상담신청 --%>
					</div>
				</div><!-- END 상단 버튼영역 -->
			</div><!-- END 컨텐츠 -->
		</div><!-- END Sub Wrap -->
	</div><!--END Container-->
</body>
</html>