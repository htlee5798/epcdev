<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/base.css" type="text/css">
<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>

<script language="javascript">
		
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
	        
	  window.open('<c:url value="/edi/consult/step3ImagePopup.do?img="/>' + imgurl + '&imgpath=' + imgpath + '&h=' + h, 'imgwin', 'width=' + openWidth + ', height=' + openHeight + ', scrollbars=yes, resizable=yes');
	}	


	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open('<c:url value="/edi/consult/step3DocumnetPopup.do?vendorBusinessNo="/>' + vendorBusinessNo , 'docwin', 'width=200, height=300, scrollbars=yes, resizable=yes');
	}
	
function convertValue()
{
	alert("<spring:message code='msg.consult.info.change.confirm'/>");
}
		

</script>




</head>

<body>
<form name="MyForm" action="<c:url value='/epc/edi/consult/NEDMCST0310insertStep4.do'/>" method="post" ID="Form1">
<div id="wrap">

	<div id="con_wrap">
	

		<div class="con_area">

			
			<div class="step_wrap">
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="<spring:message code='text.consult.field.step-base-form'/>"></a></li>
					<li><a href="#" onclick="javascript:go_step('3');"><img src="/images/epc/edi/consult/step-sales-form.gif" alt="<spring:message code='text.consult.field.step-sales-form'/>"></a></li>
					<li><a href="#" onclick="javascript:go_step('4');"><img src="/images/epc/edi/consult/step-product-form.gif" alt="<spring:message code='text.consult.field.step-product-form'/>"></a></li>
					<li class="on"><a herf="#"><img src="/images/epc/edi/consult/step-counsel-form-on.gif" alt="<spring:message code='text.consult.field.step-counsel-form'/>"></a></li>
				</ol>
			</div>

			<!-- 기본정보 -->
			<div class="s_title mt20">
				<h2><spring:message code='text.consult.field.baseInfo'/></h2>
			</div>
			<div class="tb_v_common">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.baseInfo'/></caption>
				<colgroup><col width="130px"><col width="300px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p><spring:message code='text.consult.field.corporationPlot'/></p></th>
						<td width="300px"><p class="td_p">
												
						<c:choose>
						<c:when test="${vendor.corpnDivnCd=='1'}">
           					<spring:message code='text.consult.field.corporation'/>
           				</c:when>
           				<c:when test="${vendor.corpnDivnCd=='2'}">
           					<spring:message code='text.consult.field.individual'/>
           				</c:when>
           				<c:when test="${vendor.corpnDivnCd=='3'}">
           					<spring:message code='text.consult.field.Non-business'/>
           				</c:when>
           				 <c:otherwise>
							<spring:message code='text.consult.field.corpEmpty'/>
        				</c:otherwise>
           				 </c:choose>				
		
						</p></td>
						<th width="130px"><p><spring:message code='text.consult.field.bmanNo'/></p></th>
						<td width="*"><p class="td_p"><c:out value='${ vendorSession.vendor.bmanNo }'/></p></td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.bmanKindCd'/></p></th>
						<td><p class="td_p">

						<c:choose>
						<c:when test="${vendor.bmanKindCd=='1'}">
           					<spring:message code='text.consult.field.bmanKindCd1'/>
           				</c:when>
           				<c:when test="${vendor.bmanKindCd=='2'}">
           					<spring:message code='text.consult.field.bmanKindCd2'/>
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
						<th><p><spring:message code='text.consult.field.hndNm'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.hndNm}'/></p></td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.businessKind'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.businessKind }'/></p></td>
						<th><p><spring:message code='text.consult.field.businessType'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.businessType }'/></p></td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.classification'/></p></th>
						<%-- <td><p class="td_p">${vendor.orgCd }</p></td> --%>
						<td><p class="td_p"><c:out value='${vendor.l1Nm }'/> / <c:out value='${vendor.teamNm }'/></p></td>
						<th><p><spring:message code='text.consult.field.corpnNm'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.corpnNm}'/></p></td>
					</tr>
					<tr>
						<%-- <th><p>법인번호</p></th>
						<td><p class="td_p">${vendor.corpnRsdtNo1 }-${vendor.corpnRsdtNo2}</p></td> --%>
						<th><p><spring:message code='text.consult.field.ceoNm'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.ceoNm}'/></p></td>
						<th><p><spring:message code='text.consult.field.corpnTel'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.officetel1}'/>-<c:out value='${vendor.officetel2}'/>-<c:out value='${vendor.officetel3}'/></p></td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.corpnEmail'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.email}'/></p></td>
						<th><p>FAX</p></th>
						<td><p class="td_p"><c:out value='${vendor.fax1}'/>-<c:out value='{vendor.fax2}'/>$-<c:out value='${vendor.fax3}'/></p></td>
					</tr>
					<tr>
						<th rowspan="2"><p><spring:message code='text.consult.field.addr'/></p></th>
						<td colspan="3"><p class="td_p">(<c:out value='${vendor.zipNo1}'/>-<c:out value='${vendor.zipNo2}'/>)<% //= Mid(arrResult2(5,0), 1, 3) & "-" & Mid(arrResult2(5,0), 4, 3) %>&nbsp;<c:out value='${vendor.supplyAddr1 }'/>&nbsp;<c:out value='${vendor.supplyAddr2 }'/></p></td>
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
				<li><spring:message code='text.consult.field.step4Info1'/></li>
				<li><spring:message code='text.consult.field.step4Info2'/></li>
				<li><spring:message code='text.consult.field.step4Info3'/></li>
			</ul>

			<!-- 매출정보 -->
			<div class="s_title mt20">
				<h2><spring:message code='text.consult.field.salesInfo'/></h2>
			</div>

			<div class="tb_v_common">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.salesInfo'/></caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>	
						<th><p><spring:message code='text.consult.field.mainProduct'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.mainProduct }'/></p></td>
						<th><p><spring:message code='text.consult.field.yearSaleAmount'/></p></th>
						<td><p class="td_p"><% //= arrResult2(r_y_sale_amount,0) %> <c:out value='${vendor.yearSaleAmount }'/><spring:message code='text.consult.field.1million'/></p></td>
						<th><p><spring:message code='text.consult.field.monthSaleAmount'/></p></th>
						<td><p class="td_p"><% //= arrResult2(r_m_sale_amount,0) %> <c:out value='${vendor.monthSaleAmount }'/><spring:message code='text.consult.field.1million'/></p></td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.foundationYear'/></p></th>
						<td><p class="td_p"><% //= arrResult2(r_esta_yy,0) %> <c:out value='${vendor.foundationYear }'/><spring:message code='text.consult.field.year'/></p></td>
						<th><p><spring:message code='text.consult.field.capitalAmount'/></p></th>
						<td><p class="td_p"><% //= arrResult2(r_capital,0) %> <c:out value='${vendor.capitalAmount }'/><spring:message code='text.consult.field.1million'/></p></td>
						<th><p><spring:message code='text.consult.field.factoryType'/></p></th>
						<td><p class="td_p">
						<c:choose>
						<c:when test="${vendor.factoryType=='1'}">
           					<spring:message code='text.consult.field.factoryType1'/>
           				</c:when>
           				<c:when test="${vendor.factoryType=='2'}">
           					<spring:message code='text.consult.field.factoryType2'/>
           				</c:when>
           				<c:otherwise>
							<spring:message code='text.consult.field.factoryType3'/>
						</c:otherwise>           				
           				 </c:choose>				
						</p></td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.chkAttribute'/></p></th>
						<td colspan="5"><p class="td_p">
						
							<c:forTokens var="one" items="${vendor.companyCharCode}" delims=";" varStatus="sts"> 
						 	 	
						 	<c:choose>
							<c:when test="${one=='jejo'}">
           						<spring:message code='text.consult.field.chkAttributeJejo'/>
           					</c:when>
           					<c:when test="${one=='jicsuip'}">
           						<spring:message code='text.consult.field.chkAttributeJicsuip'/>
           					</c:when>
           					<c:when test="${one=='chongpan'}">
           						<spring:message code='text.consult.field.chkAttributeChongpan'/>
           					</c:when>
           					<c:when test="${one=='broker'}">
           						<spring:message code='text.consult.field.chkAttributeBroker'/>
           					</c:when>
           					<c:when test="${one=='sanji'}">
           						<spring:message code='text.consult.field.chkAttributeSanji'/>
           					</c:when>
           					<c:when test="${one=='imdae'}">
           						<spring:message code='text.consult.field.chkAttributeImdae'/>
           					</c:when>
            				<c:otherwise>
								 <span class="ml30"><spring:message code='text.consult.field.chkAttributeEtc'/> </span>
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
				<h2><spring:message code='text.consult.field.companyDescription'/></h2>
			</div>
			<div class="tb_v_common">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.companyDescription'/></caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="156px"></colgroup>
				<tbody>
					<tr>	
						<th><p><spring:message code='text.consult.field.smIndustryEntpNm'/></p></th>
						<td colspan="5"><p class="td_p"><c:out value='${vendor.smIndustryEntpNm }'/></p></td>
					</tr>
					<tr>	
						<th><p><spring:message code='text.consult.field.smIndustryEntpProdNm'/></p></th>
						<td colspan="3"><p class="td_p"><c:out value='${vendor.smIndustryEntpProdNm }'/></p></td>
						<th><p><spring:message code='text.consult.field.smIndustryEntpAmt'/></p></th>
						<td><p class="td_p"><c:out value='${vendor.smIndustryEntpAmt }'/><spring:message code='text.consult.field.1million'/></p></td>
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
				<li><spring:message code='text.consult.field.step4Info4'/></li>
				<!-- <li>평균 M/G는 상품별 마진율이 아니라 전체 마진을 의미하며 정상일때와 행사일때의 마진율을 입력하시면 됩니다.</li>
				<li>거래형태는 직매입, 특정, 임대 또는 단기 행사등을 입력하시면 됩니다.</li> -->
			</ul>

			<!-- 주요 상품정보 -->
			<div class="s_title mt20">
				<h2><spring:message code='text.consult.field.mainProductInfo'/></h2>
			</div>
			<div class="tb_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.mainProductInfo'/></caption>
				<colgroup><col width="68px"><col width="171px"><col width="135px"><col width="135px"><col width="135px"><col width="*"></colgroup>
				<thead>
					<tr>
						<th width="68px" class="line"><spring:message code='text.consult.header.bunho'/></th>
						<th width="171px" class="line"><spring:message code='text.consult.field.productName'/></th>
						<!-- <th width="135px" class="line">상품원가</th> -->
						<th width="135px" class="line"><spring:message code='text.consult.header.productCost'/></th>
						<!-- <th width="135px" class="line">상품매가</th> -->
						<th width="135px" class="line"><spring:message code='text.consult.header.productSalePrice'/></th>
						<!-- <th width="135px" class="line">월 평균매출</th> -->
						<th width="135px" class="line"><spring:message code='text.consult.header.monthlyAverage'/></th>
						<th width="*"><spring:message code='text.consult.field.insertImg'/</th>
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
						<td colspan="5" class="t_left"><p><spring:message code='text.consult.field.productDesc'/> : </p></td>
					</tr>
					</c:if>	
					
					
					<c:if test="${fn:length(vendorProductList) > 0 }"> 
						<c:forEach  var="vendorProduct" items="${vendorProductList}" varStatus="status">
					
							<tr class="line_dot">
								<td rowspan="2" class="t_center num"><c:out value='${status.count}'/></td>
								<td class="t_left"><p><c:out value='${vendorProduct.productName }'/></p></td>
								<td class="t_right ls0"><p><c:out value='${vendorProduct.productCost }'/></p></td>
								<td class="t_right ls0"><p><c:out value='${vendorProduct.productSalePrice }'/></p></td>
								<td class="t_right ls0"><p><c:out value='${vendorProduct.monthlyAverage }'/></p></td>
								<td class="t_center"><p>
									<c:if test="${not empty vendorProduct.attachFileCode }">
										<a href="javascript:img_view('<c:out value="${vendorProduct.attachFileCode}"/>', '<c:out value="${attachFileFolder}"/>')" class="btn"><spring:message code='text.consult.field.viewImg'/></a>
									</c:if>
								</p></td>
							</tr>
							<tr>
								<td colspan="5" class="t_left"><p><spring:message code='text.consult.field.productDesc'/> :<c:out value='${vendorProduct.productDescription }'/></p></td>
							</tr>
					
						</c:forEach>
					</c:if>
					
				</tbody>
				</table>
			</div>

			<ul class="list_star">
				<li><spring:message code='text.consult.field.step4Info5'/></li>
			</ul>

			<!-- 특이사항 -->
			<div class="s_title mt20">
				<h2><spring:message code='text.consult.field.uniqueness'/></h2>
			</div>
			<div class="box_etc">
				<c:out value='${vendor.pecuSubjectContent }'/>
			</div>
			  <!-- a href="javascript:checkStep4Submit() ">전송</a-->
			<c:if test="${not empty vendor.attachFileCode }">
				<br><spring:message code='text.consult.field.fieUpload'/> : <span style="margin-left: 20px"><a href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><c:out value='${vendor.attachFileCode}'/></a></span>
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
				<span class="btn_white"><span><a href="javascript:check_submit_5('<c:out value="${gubunBlock }"/>');">
				<spring:message code='button.consult.apply'/></a></span></span>
			</div>
			<!--// button -->

		</div>
		
	</div>

</div>

<input type="hidden" name="update_gb" value="" ID="Hidden1">	
<input type="hidden" name="l1_cd" value="<c:out value='${vendor.l1Cd }'/>" ID="Hidden2">	
<input type="hidden" id="gubunIn" name="gubunIn" value="<c:out value='${gubunBlock }'/>"/>
</form>
</body>
</html>