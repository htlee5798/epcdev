<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMSTP0020.jsp
	Description : 절차안내(VIC 마켓)
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
				<p class="page_path">HOME <span><spring:message code="header.menu.text1" /></span> <span><spring:message code="text.srm.field.step.tab2" /></span></p>
			</div><!-- END 서브상단 -->

			<!-- 컨텐츠 -->
			<div class="content">
				<!-- 알림 -->
				<div class="noti_box">
					<ul class="noti_list float-l">
						<li>
							<span class="txt_l"><spring:message code="text.srm.field.srmstp0010Notice1" /></span><br>	<%-- 신규입점을 진행하기 위해서는 <em>신용평가정보가 필요</em>하오니, 원활한 입점신청을 위해 미리 준비해주세요. --%>
							<spring:message code="text.srm.field.srmstp0010Notice2" />
							<%-- (신용평가기관 : 나이스디앤비, 이크레더블, 한국기업데이터) --%>
						</li>
						<li><spring:message code="text.srm.field.srmstp0010Notice3" /></li>
					</ul>

					<p class="float-r">
						<a href="#" class="btn_redround" onclick="manualDown('1');"><spring:message code='text.srm.field.step.tab6'/> <img src="/images/epc/edi/srm/sub/icon_download.png" alt="<spring:message code='text.srm.field.step.tab6'/>"></a>
						<a href="#" class="btn_redround ml5" onclick="manualDown('4');"><spring:message code='text.srm.field.step.tab7'/> <img src="/images/epc/edi/srm/sub/icon_download.png" alt="<spring:message code='text.srm.field.step.tab7'/>"></a>
					</p>
				</div><!-- END 알림 -->

				<!-- 서브 탭 -->
				<ul class="sub_tab">
					<li><a href="<c:url value="/edi/srm/SRMSTP0010.do" />"><spring:message code="text.srm.field.step.tab1" /></a></li>				<!-- 롯데마트 -->
					<li class="on"><a href="<c:url value="/edi/srm/SRMSTP0020.do" />"><spring:message code="text.srm.field.step.tab2" /></a></li>	<!-- VIC마켓 -->
					<li><a href="<c:url value="/edi/srm/SRMSTP0030.do" />"><spring:message code="text.srm.field.step.tab3" /></a></li>				<!-- 롯데마트몰(Online) -->
					<li><a href="<c:url value="/edi/srm/SRMSTP0040.do" />"><spring:message code="text.srm.field.step.tab4" /></a></li>				<!-- 임대매장 -->
					<!-- <li><a href="<c:url value="/edi/srm/SRMSTP0050.do" />"><spring:message code="text.srm.field.step.tab5" /></a></li>-->	    <!-- 지원구매 --> 
				</ul><!-- END 서브 탭 -->
				
				<!-- 
				<h3 class="tit_star"><spring:message code="text.srm.field.srmstp0020.title" /></h3>	<%-- 맥스(MAXX)는 --%>
				<p class="pl15"><spring:message code="text.srm.field.srmstp0020.contents" /></p>	<%-- 국내 및 해외에서 까다롭게 엄선한 약 3,000여가지 우수상품을 국내 최저가격 수준으로 공급하는 쇼핑공간입니다. --%>
				-->
				 
				<h3 class="tit_star"><spring:message code="text.srm.filed.step.process" /></h3>		<%-- 입점상담 절차안내 --%>
				
				<!-- 절차 -->
				<ul class="process_step">
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step1" /></span><em><spring:message code="text.srm.field.step.process1" /></em></div></div></li>	<%-- 상담신청 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step2" /></span><em><spring:message code="text.srm.field.step.process2" /></em></div></div></li>	<%-- 상담접수 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step3" /></span><em><spring:message code="text.srm.field.step.process3" /></em></div></div></li>	<%-- 품평회 및 평가 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step4" /></span><em><spring:message code="text.srm.field.step.process4" /></em></div></div></li>	<%-- 품질경영평가 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step5" /></span><em><spring:message code="text.srm.field.step.process6_1" /></em></div></div></li>	<%-- 입점확정 --%>
					<li><div class="circle"><div class="incircle"><span><spring:message code="text.srm.field.step6" /></span><em><spring:message code="text.srm.field.step.process6_2" /></em></div></div></li>	<%-- 입점확정 --%>

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
								<spring:message code="text.srm.field.srmstp0020.process1" />
								<%-- 당사 홈페이지 입점상담 문의를 통해 접속하여 필요한 정보를 입력 후 입점상담 신청합니다. --%>
							</td>
						</tr>
						<tr id="p2">
							<th><spring:message code="text.srm.field.step.process2" /></th>	<%-- 상담접수 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0020.process2" />
								<%-- 신청한 정보를 평가하여 상담 및 품평회 여부를 결정하게 되며, 일정 및 세부사항에 대해 안내해드립니다. --%>
							</td>
						</tr>
						<tr id="p3">
							<th><spring:message code="text.srm.field.step.process3" /></th>	<%-- 품평회 및 평가 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0020.process3" />
								<%-- 귀사의 기업 및 상품 소개 시간을 드리고 평가위원이 공정하고 객관적인 평가를 진행합니다. --%>
							</td>
						</tr>
						<tr id="p4">
							<th><spring:message code="text.srm.field.step.process4" /></th>	<%-- 품질경영평가 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0020.process4" />
								<%-- 귀사의 현장(공장)조사 및 상품 품질 검사 과정을 통해 해당업체의 최종 입점 여부가 결정되며,
								<br>
								결과는 품평회와 현장(공장)조사가 모두 완료된 후 통보를 하게 됩니다. --%>
							</td>
						</tr>
						<tr id="p5">
							<th><spring:message code="text.srm.field.step.process6_1" /></th>	<%-- 입점확정 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0020.process6_1" />
								<%-- 입점 확정 결정에 따라 관련된 서류구비 및 계약서 작성을 진행합니다.
								<br>
								입점 확정 후 파트너사 코드가 생성된 이후에는 발주에 따른 납품을 하시면 됩니다. --%>
							</td>
						</tr>
						<tr id="p6">
							<th><spring:message code="text.srm.field.step.process6_3" /></th>	<%-- 입점확정 --%>
							<td>
								<spring:message code="text.srm.field.srmstp0020.process6_2" />
								<%-- 입점 확정 결정에 따라 관련된 서류구비 및 계약서 작성을 진행합니다.
								<br>
								입점 확정 후 파트너사 코드가 생성된 이후에는 발주에 따른 납품을 하시면 됩니다. --%>
							</td>
						</tr>
					</tbody>
				</table><!-- END 테이블1 -->
			</div><!-- END 컨텐츠 -->
		</div><!-- END Sub Wrap -->
	</div><!--END Container-->

</body>
</html>