<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/base.css" type="text/css">
<title>롯데마트 입점상담</title>

<script language="javascript">
<!--		
function img_view(imgurl) {

  var w = "300";
  var h = "300";
  var width = "300";
  var height = "300";
  
  var path = "http://edi.lottemart.com/upload/consult/" + imgurl;

  var openWidth = "300";
  var openHeight = "350";


  if (openWidth > 800)
    openWidth = 800;
  if (openHeight > 800)
    openHeight = 800;
        alert(path);
  window.open('Pop_ViewImg.asp?img=' + path + '&w=' + w + '&h=' + h, 'imgwin', 'width=' + openWidth + ', height=' + openHeight + ', scrollbars=yes, resizable=yes');
}

function checkStep4Submit() {
	var tmp = document.getElementById("gubunIn");

		$("form[name=MyForm]").submit();
}

function img_view(imgurl, imgpath) {

	//alert("tt")

	  var w = "600";
	  var h = "600";
	  var width = "600";
	  var height = "600";
	  
	  var path = "upload/" + imgurl;

	  var openWidth = "650";
	  var openHeight = "600";


	  if (openWidth > 800)
	    openWidth = 800;
	  if (openHeight > 800)
	    openHeight = 800;
	        
	  window.open('${ctx}/edi/consult/step3ImagePopup.do?img=' + imgurl + '&imgpath=' + imgpath + '&h=' + h, 'imgwin', 'width=' + openWidth + ', height=' + openHeight + ', scrollbars=yes, resizable=yes');
	}	


	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open('${ctx}/edi/consult/step3DocumnetPopup.do?vendorBusinessNo=' + vendorBusinessNo , 'docwin', 'width=200, height=300, scrollbars=yes, resizable=yes');
	}
	
function convertValue()
{
	alert("<spring:message code='msg.consult.info.change.confirm'/>");
}
		
//-->
</script>




</head>

<body>
<form name="MyForm" action="${ctx }/epc/edi/consult/insertStep4.do" method="post" ID="Form1">
<div id="wrap">

	<div id="con_wrap">
	

		<div class="con_area">

			
			<div class="step_wrap">
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="기본정보 입력"></a></li>
					<li><a href="#" onclick="javascript:go_step('3');"><img src="/images/epc/edi/consult/step-sales-form.gif" alt="매출정보 입력"></a></li>
					<li><a href="#" onclick="javascript:go_step('4');"><img src="/images/epc/edi/consult/step-product-form.gif" alt="상품정보 입력"></a></li>
					<li  class="on"><a herf="#"><img src="/images/epc/edi/consult/step-counsel-form-on.gif" alt="상담신청"></a></li>
				</ol>
			</div>

			<!-- 기본정보 -->
			<div class="s_title mt20">
				<h2>기본정보</h2>
			</div>
			<div class="tb_v_common">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>기본정보 화면</caption>
				<colgroup><col width="130px"><col width="300px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p>법인구분</p></th>
						<td width="300px"><p class="td_p">
												
						<c:choose>
						<c:when test="${vendor.corpnDivnCd=='1'}">
           					법인
           				</c:when>
           				<c:when test="${vendor.corpnDivnCd=='2'}">
           					개인
           				</c:when>
           				<c:when test="${vendor.corpnDivnCd=='3'}">
           					비사업
           				</c:when>
           				 <c:otherwise>
							없음
        				</c:otherwise>
           				 </c:choose>				
		
						</p></td>
						<th width="130px"><p>사업자등록번호</p></th>
						<td width="*"><p class="td_p">${ vendorSession.vendor.bmanNo }</p></td>
					</tr>
					<tr>
						<th><p>사업자종류</p></th>
						<td><p class="td_p">

						<c:choose>
						<c:when test="${vendor.bmanKindCd=='1'}">
           					면세
           				</c:when>
           				<c:when test="${vendor.bmanKindCd=='2'}">
           					과세
           				</c:when>
           		
           				 </c:choose>				
						
						</p></td>
						<%-- <th><p>계열사구분</p></th>
						<td><p class="td_p">
										
						<c:choose>
						<c:when test="${vendor.affYn=='Y'}">
           					Yes
           				</c:when>
           				<c:when test="${vendor.affYn=='N'}">
           					No
           				</c:when>
           				</c:choose>	

				
						</p></td> --%>
						<th><p>상호명</p></th>
						<td><p class="td_p">${vendor.hndNm}</p></td>
					</tr>
					<tr>
						<th><p>업종</p></th>
						<td><p class="td_p">${vendor.businessKind }</p></td>
						<th><p>업태</p></th>
						<td><p class="td_p">${vendor.businessType }</p></td>
					</tr>
					<tr>
						<th><p>분류</p></th>
						<%-- <td><p class="td_p">${vendor.orgCd }</p></td> --%>
						<td><p class="td_p">${vendor.l1Nm } / ${vendor.teamNm }</p></td>
						<th><p>대표브랜드</p></th>
						<td><p class="td_p">${vendor.corpnNm}</p></td>
					</tr>
					<tr>
						<%-- <th><p>법인번호</p></th>
						<td><p class="td_p">${vendor.corpnRsdtNo1 }-${vendor.corpnRsdtNo2}</p></td> --%>
						<th><p>대표자명</p></th>
						<td><p class="td_p">${vendor.ceoNm}</p></td>
						<th><p>대표전화번호</p></th>
						<td><p class="td_p">${vendor.officetel1}-${vendor.officetel2}-${vendor.officetel3}</p></td>
					</tr>
					<tr>
						<th><p>대표E-mail</p></th>
						<td><p class="td_p">${vendor.email}</p></td>
						<th><p>FAX</p></th>
						<td><p class="td_p">${vendor.fax1}-${vendor.fax2}-${vendor.fax3}</p></td>
					</tr>
					<tr>
						<th rowspan="2"><p>주소</p></th>
						<td colspan="3"><p class="td_p">(${vendor.zipNo1}-${vendor.zipNo2})<% //= Mid(arrResult2(5,0), 1, 3) & "-" & Mid(arrResult2(5,0), 4, 3) %>&nbsp;${vendor.supplyAddr1 }&nbsp;${vendor.supplyAddr2 }</p></td>
					</tr>
				</tbody>
				</table>
			</div>

			<!-- 담당자 정보 -->
			<%-- <div class="s_title mt20">
				<h2>담당자 정보</h2>
			</div>

			<div class="tb_v_common">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>담당자 정보 화면</caption>
				<colgroup><col width="130px"><col width="300px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th><p>이름</p></th>
						<td><p class="td_p">${vendor.utakmanNm}</p></td>
						<th><p></p></th>
						<td><p class="td_p"></p></td>
					</tr>
					<tr>
						<th><p>핸드폰</p></th>
						<td><p class="td_p">${vendor.cell1}-${vendor.cell2}-${vendor.cell3}</p></td>
						<th><p>사무실</p></th>
						<td><p class="td_p">${vendor.tel1}-${vendor.tel2}-${vendor.tel3}</p></td>
					</tr>
					<tr>
						<th><p>E-mail</p></th>
						<td colspan="3"><p class="td_p">${vendor.email}</p></td>
					</tr>
					<tr>
						<th><p>홈페이지</p></th>
						<td colspan="3"><p class="td_p">${vendor.homePageAddress }</p></td>
					</tr>
				</tbody>
			</table>
			</div> --%>

			<ul class="list_star">
				<li>대표이메일을 등록하시면 결과를 이메일로 받아보실수 있습니다. 미 등록時 결과정보는 해당 페이지에서 확인하실수 있습니다.</li>
				<li>Vendor Pool을 통한 상담신청은 Vendor Pool의 기본정보를 사용합니다. 해당 정보를 수정할 수 있습니다.</li>
				<li>E-mail에서 hanmail.net 은 메일의 수신이 안될 경우가 있으니 다른 E-mail을 사용하시기 바랍니다. 해당 E-mail로 상담정보가 발송됩니다.</li>
			</ul>

			<!-- 매출정보 -->
			<div class="s_title mt20">
				<h2>매출정보</h2>
			</div>

			<div class="tb_v_common">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>매출정보 화면</caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>	
						<th><p>주력생산품</p></th>
						<td><p class="td_p">${vendor.mainProduct }</p></td>
						<th><p>연매출액</p></th>
						<td><p class="td_p"><% //= arrResult2(r_y_sale_amount,0) %> ${vendor.yearSaleAmount }백만</p></td>
						<th><p>월평균매출액</p></th>
						<td><p class="td_p"><% //= arrResult2(r_m_sale_amount,0) %> ${vendor.monthSaleAmount }백만</p></td>
					</tr>
					<tr>
						<th><p>설립년도</p></th>
						<td><p class="td_p"><% //= arrResult2(r_esta_yy,0) %> ${vendor.foundationYear }년도</p></td>
						<th><p>자본금</p></th>
						<td><p class="td_p"><% //= arrResult2(r_capital,0) %> ${vendor.capitalAmount }백만</p></td>
						<th><p>공장유무</p></th>
						<td><p class="td_p">
		
						
						<c:choose>
						<c:when test="${vendor.factoryType=='1'}">
           					직영
           				</c:when>
           				<c:when test="${vendor.factoryType=='2'}">
           					하청
           				</c:when>
           				<c:otherwise>
							기타
						</c:otherwise>           				
           				 </c:choose>				
						
						
						</p></td>
					</tr>
					<tr>
						<th><p>회사 성격</p></th>
						<td colspan="5"><p class="td_p">
						
							<c:forTokens var="one"
 							items="${vendor.companyCharCode}"
 							delims=";" varStatus="sts"> 
						 	 	
						 	<c:choose>
							<c:when test="${one=='jejo'}">
           						제조
           					</c:when>
           					<c:when test="${one=='jicsuip'}">
           						직수입
           					</c:when>
           					<c:when test="${one=='chongpan'}">
           						총판/대리점
           					</c:when>
           					<c:when test="${one=='broker'}">
           						벤더
           					</c:when>
           					<c:when test="${one=='sanji'}">
           						산지
           					</c:when>
           					<c:when test="${one=='imdae'}">
           						임대수수료
           					</c:when>
            				<c:otherwise>
								 <span class="ml30">기타 </span>
							</c:otherwise>           				
           				 	</c:choose>	
						 	
							</c:forTokens>
							
						
						</p>
						
							
						</td>
					</tr>
				</tbody>
			</table>
			</div>

			<!-- 동업계 입점현황 -->
			<div class="s_title mt20">
				<h2>동업계 입점현황</h2>
			</div>
			<div class="tb_v_common">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>동업계 입점현황</caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="156px"></colgroup>
				<tbody>
					<tr>	
						<th><p>유통사명</p></th>
						<td colspan="5"><p class="td_p">${vendor.smIndustryEntpNm }</p></td>
					</tr>
					<tr>	
						<th><p>납품상품</p></th>
						<td colspan="3"><p class="td_p">${vendor.smIndustryEntpProdNm }</p></td>
						<th><p>월평균납품액</p></th>
						<td><p class="td_p">${vendor.smIndustryEntpAmt }백만</p></td>
					</tr>
				</tbody>
			</table>
			</div>
			<%-- <div class="tb_h_common_02_btm">
				<ul class="clearfloat">
					<li class="tit">롯데계열거래여부</li>
					<li>
						<c:forTokens var="one"
	 							items="${vendor.lotteAffTradeCode}"
	 							delims=";" varStatus="sts"> 
							 	<c:choose>
								<c:when test="${one=='dept'}">
	           						롯데백화점
	           					</c:when>
	           					<c:when test="${one=='super'}">
	           						롯데슈퍼
	           					</c:when>
	           					<c:when test="${one=='k7'}">
	           						K-7
	           					</c:when>
	            				<c:otherwise>
									 <span class="ml30"></span>
								</c:otherwise>           				
	           				 	</c:choose>	
						</c:forTokens>
						
						<c:if test="${not empty vendor.lotteAffTradeContent }">
							&nbsp;&nbsp;<span class="ml30">${vendor.lotteAffTradeContent }</span>
						</c:if>
					</li>
				</ul>
			</div> --%>

			<ul class="list_star">
				<li>업체 매출 상세정보를 입력하세요. 동업계 입점현황은 상담 판단의 중요자료입니다. 정확히 입력해 주시기 바랍니다.</li>
				<!-- <li>평균 M/G는 상품별 마진율이 아니라 전체 마진을 의미하며 정상일때와 행사일때의 마진율을 입력하시면 됩니다.</li>
				<li>거래형태는 직매입, 특정, 임대 또는 단기 행사등을 입력하시면 됩니다.</li> -->
			</ul>

			<!-- 주요 상품정보 -->
			<div class="s_title mt20">
				<h2>주요 상품정보</h2>
			</div>
			<div class="tb_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>주요 상품정보</caption>
				<colgroup><col width="68px"><col width="171px"><col width="135px"><col width="135px"><col width="135px"><col width="*"></colgroup>
				<thead>
					<tr>
						<th width="68px" class="line">번호</th>
						<th width="171px" class="line">상품명</th>
						<!-- <th width="135px" class="line">상품원가</th> -->
						<th width="135px" class="line">납품(예상)가</th>
						<!-- <th width="135px" class="line">상품매가</th> -->
						<th width="135px" class="line">시장(예상)판매가</th>
						<!-- <th width="135px" class="line">월 평균매출</th> -->
						<th width="135px" class="line">(예상)매출액</th>
						<th width="*">이미지 등록</th>
					</tr>
				</thead>
				<tbody>
				
				<c:if test="${fn:length(vendorProductList) == 0 }"> 
					<tr class="line_dot">
						<td rowspan="2" class="t_center num"></td>
						<td class="t_left"><p></p></td>
						<td class="t_right ls0"><p></p></td>
						<td class="t_right ls0"><p></p></td>
						<td class="t_right ls0"><p></p></td>
						<td class="t_left ls0"><p></p></td>
					</tr>
				
					
					<tr>
						<td colspan="5" class="t_left"><p>상품설명 : </p></td>
					</tr>
					</c:if>	
					
					
					<c:if test="${fn:length(vendorProductList) > 0 }"> 
						<c:forEach  var="vendorProduct" items="${vendorProductList}" varStatus="status">
					
					
					
					<tr class="line_dot">
						<td rowspan="2" class="t_center num">${status.count}</td>
						<td class="t_left"><p>${vendorProduct.productName }</p></td>
						<td class="t_right ls0"><p>${vendorProduct.productCost }</p></td>
						<td class="t_right ls0"><p>${vendorProduct.productSalePrice }</p></td>
						<td class="t_right ls0"><p>${vendorProduct.monthlyAverage }</p></td>
						<td class="t_center"><p>
							<c:if test="${not empty vendorProduct.attachFileCode }">
								<a href="javascript:img_view('${vendorProduct.attachFileCode}', '${attachFileFolder}')" class="btn">이미지 보기</a>
							</c:if>
						</p></td>
					</tr>
					<tr>
						<td colspan="5" class="t_left"><p>상품설명 :${vendorProduct.productDescription }</p></td>
					</tr>
					
						</c:forEach>
					</c:if>
					
					
				</tbody>
				</table>
			</div>

			<ul class="list_star">
				<li>해당 업체의 주력상품 정보를 입력해 주시기 바랍니다.</li>
			</ul>

			<!-- 특이사항 -->
			<div class="s_title mt20">
				<h2>특이사항</h2>
			</div>
			<div class="box_etc">
				${vendor.pecuSubjectContent }
			</div>
			  <!-- a href="javascript:checkStep4Submit() ">전송</a-->
			<c:if test="${not empty vendor.attachFileCode }">
				<br>첨부파일 : <span style="margin-left: 20px"><a href="javascript:downloadConsultDocumnet('${vendor.bmanNo}')">${vendor.attachFileCode}</a></span>
			</c:if>
			
<%-- <div class="s_title mt20">
				<h2>옥션입찰상품 선택상품</h2>
			</div><br>
<table cellpadding="0" cellspacing="0" border="1" width="100%">
				<caption>주요 상품정보</caption>
				<colgroup>
				
					<col width="100px">
					<col width="100px">
					<col width="200px">
					<col width="100px">
					<col width="100px">
					<col width="100px">
				</colgroup>
				<thead>
					<tr>
					
						<th width="100px" class="line">옥션진행일</th>
						<th width="100px" class="line">대분류</th>
						<th width="200px" class="line">품목</th>
						<th width="100px" class="line">단위</th>
						<th width="100px" class="line">시작가</th>
						<th width="100px" class="line">수량</th>
					</tr>
				</thead>
				
				
	
<tbody>
				<c:if test="${fn:length(autionItemListSum) > 0 }"> 
					<c:forEach  var="autionItemSum" items="${autionItemListSum}" varStatus="status">
				
					<tr>
						
						
						<td class="t_center no_b_line"><span><span>${autionItemSum.auDy}</span></span></td>
						<td class="t_center no_b_line"><span><span>${autionItemSum.l1Nm}</span></span></td>
						<td class="t_center no_b_line"><span><span>${autionItemSum.prodNm}</span></span></td>
						<td class="t_center no_b_line"><span><span>${autionItemSum.unit}</span></span></td>
						<td class="t_center no_b_line"><span><span>${autionItemSum.startPrc}</span></span></td>						
					    <td class="t_center no_b_line"><span><span>${autionItemSum.qty}</span></span></td>	
					</tr>
				 	<tr class="pr_info">
						<td colspan="5" class="t_left"><div class="box_etc">${autionItemSum.detlContent }</div></td>
					</tr>
				
					</c:forEach>
				</c:if>
				
				<c:if test="${fn:length(autionItemListSum) == 0 }"> 
					
					<tr class="pr_info">
				
					
						<td colspan="6" class="t_center no_b_line" >옥션입창상품이 없습니다.</td>
				  
						
					</tr>
					
					</c:if>
					
			</tbody>
		</table> --%>
		
		
			<!-- button -->
			<div class="btn_c_wrap mt30">
				<span class="btn_red"><span><a href="javascript:go_page('1');"><spring:message code='button.consult.info.change'/></a></span></span>
				<span class="btn_white"><span><a href="javascript:check_submit_5('${gubunBlock }');">
				<spring:message code='button.consult.apply'/></a></span></span>
			</div>
			<!--// button -->

		</div>


		
		
	</div>

</div>

		

<input type="hidden" name="update_gb" value="" ID="Hidden1">	
<input type="hidden" name="l1_cd" value="${vendor.l1Cd }" ID="Hidden2">	
<input type="hidden" id="gubunIn" name="gubunIn" value="${gubunBlock }"/>
</form>
</body>
</html>