<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMRST002006.jsp
	Description : 입점상담 결과확인 > 진행현황 > 정보확인 팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=1100">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
	
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code='text.srm.field.srmrst002001.title1'/></title><%--spring:message : 상담이력--%>
	
	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
		});
		
		function downloadFile(fileId, fileSeq) {
			$('#fileForm input[name=atchFileId]').val(fileId);
			$('#fileForm input[name=fileSn]').val(fileSeq);
			$('#fileForm').attr("action", '<c:url value="/edi/srm/FileDown.do"/>')
			$('#fileForm').submit();
		}

		//닫기
		function func_ok() {
			window.close();
		}
	</script>

</head>


<body>
<form id="MyForm" name="MyForm" method="post">
	<!-- popup wrap -->
	<div id="popup_wrap_full">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<h2 class="tit_star"><spring:message code='text.srm.field.srmrst002003.sub.title1'/></h2>	<%--요청정보 --%>

		<%----- 상담채널 Start -------------------------%>
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
				<td colspan="3">
					<c:out value="${srmComp.companyTypeNm}"/>
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
		<p class="align-c mt10"><button type="button" class="btn_normal" onclick="func_ok();"><spring:message code='button.srm.close'/></button></p>	<%--닫기 --%>
	</div>
</form>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>