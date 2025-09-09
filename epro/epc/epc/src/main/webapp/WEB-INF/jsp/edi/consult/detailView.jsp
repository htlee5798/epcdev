<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="./css/base.css" type="text/css">
<link href="/css/epc/edi/consult/footer.css" type="text/css" rel="stylesheet">
<link href="/css/epc/edi/consult/popup.css" type="text/css" rel="stylesheet">
<link href="/css/epc/edi/consult/base.css" type="text/css" rel="stylesheet">
<title>롯데마트 입점 상담</title>

    
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
		        
		  window.open('${ctx}/edi/consult/step3ImagePopup.do?img=' + imgurl + '&imgpath=' + imgpath + '&h=' + h, 'imgwin', 'width=' + openWidth + ', height=' + openHeight + ', scrollbars=yes, resizable=yes');
		}	


		function downloadConsultDocumnet(vendorBusinessNo) {
			window.open('${ctx}/edi/consult/step3DocumnetPopup.do?vendorBusinessNo=' + vendorBusinessNo , 'docwin', 'width=200, height=300, scrollbars=yes, resizable=yes');
		}
		
	function convertValue()
	{
		alert("<spring:message code='msg.consult.info.change.confirm'/>");
	}



	
</script>




</head>

<body>

<div id="tooltiplayer" class="tooltiplayer">
    <div class="tooltiptitle">
        <span id="title">&nbsp;</span>
    </div>
</div>


    
<form name="MyForm" action="<c:url value='/epc/edi/consult/insertStep4.do'/>" method="post" ID="Form1">
<div id="wrap">

	<div id="con_wrap">
	

		<div class="con_area">
			
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
						<th><p>계열사구분</p></th>
						<td><p class="td_p">
										
						<c:choose>
						<c:when test="${vendor.affYn=='Y'}">
           					Yes
           				</c:when>
           				<c:when test="${vendor.affYn=='N'}">
           					No
           				</c:when>
           				</c:choose>	

				
						</p></td>
					</tr>
					<tr>
						<th><p>상호명</p></th>
						<td><p class="td_p">${vendor.hndNm}</p></td>
						<th><p>대표자명</p></th>
						<td><p class="td_p">${vendor.ceoNm}</p></td>
					</tr>
					<tr>
						<th><p>업종</p></th>
						<td><p class="td_p">${vendor.businessKind }</p></td>
						<th><p>업태</p></th>
						<td><p class="td_p">${vendor.businessType }</p></td>
					</tr>
					<tr>
						<th><p>분류</p></th>
						<td><p class="td_p">${vendor.orgCd }</p></td>
						<th><p>대표브랜드</p></th>
						<td><p class="td_p">${vendor.corpnNm}</p></td>
					</tr>
					<tr>
						<th><p>법인번호</p></th>
						<td><p class="td_p">${vendor.corpnRsdtNo1 }-${vendor.corpnRsdtNo2}</p></td>
						<th><p>전화번호</p></th>
						<td><p class="td_p">${vendor.officetel1}-${vendor.officetel2}-${vendor.officetel3}</p></td>
					</tr>
					<tr>
						<th rowspan="2"><p>주소</p></th>
						<td><p class="td_p"><% //= Mid(arrResult2(5,0), 1, 3) & "-" & Mid(arrResult2(5,0), 4, 3) %></p></td>
						<th><p>FAX</p></th>
						<td><p class="td_p">${vendor.fax1}-${vendor.fax2}-${vendor.fax3}</p></td>
					</tr>
					<tr>
						<td colspan="3"><p class="td_p">${vendor.supplyAddr1 }&nbsp;${vendor.supplyAddr2 }</p></td>
					</tr>
				</tbody>
				</table>
			</div>

			<!-- 담당자 정보 -->
			<div class="s_title mt20">
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
						<td><p class="td_p">${vendor.cell1}</p></td>
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
			</div>


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
						<td><p class="td_p">직영
		
						
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
			<div class="tb_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>동업계 입점현황</caption>
				<colgroup><col width="157px"><col width="91px"><col width="211px"><col width="97px"><col width="97px"><col width="*"></colgroup>
				<thead>
					<tr>
						<th width="157px" class="line">업체명</th>
						<th width="91px" class="line">입점 점포수</th>
						<th width="211px" class="line">평균 M/G</th>
						<th width="97px" class="line">물류비</th>
						<th width="97px" class="line">장려금</th>
						<th width="*">거래형태</th>
					</tr>
				</thead>
				<tbody>
				
				
				<c:if test="${fn:length(saleList) == 0}">
					<tr>
						<td class="t_center"></td>
						<td class="t_center"></td>
						<td class="t_center"></td>
						<td class="t_center"></td>
						<td class="t_center"></td>
						<td class="t_center"><p></p></td>
					</tr>
				</c:if>
				
				<c:forEach  var="sale" items="${saleList}" varStatus="status">
					<tr>
						<td class="t_center">${sale.otherStoreCode}</td>
						<td class="t_center">${sale.enteredStoreCount }</td>
						<td class="t_center">정상 ${sale.marginRate1 } % / 행사 ${sale.marginRate2 } %</td>
						<td class="t_center">${sale.pcost } %</td>
						<td class="t_center">${sale.subAmount } %</td>
						<td class="t_center"><p>${sale.tradeTypeContent }</p></td>
					</tr>
				</c:forEach>
				
				</tbody>
				</table>
			</div>
			<div class="tb_h_common_02_btm">
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
								 <span class="ml30">기타</span>
							</c:otherwise>           				
           				 	</c:choose>	
						 	
							</c:forTokens>
							
					</li>
				</ul>
			</div>


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
						<th width="135px" class="line">상품원가</th>
						<th width="135px" class="line">상품매가</th>
						<th width="135px" class="line">월 평균매출</th>
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


			<!-- 특이사항 -->
			<div class="s_title mt20">
				<h2>특이사항</h2>
			</div>
			<div class="box_etc">
				${vendor.pecuSubjectContent }
			</div>
			<!-- <p class="add_file">  <a href="javascript:checkStep4Submit() ">전송</a> -->
			<c:if test="${not empty vendor.attachFileCode }">
				<br>첨부파일 : <span style="margin-left: 20px"><a href="javascript:downloadConsultDocumnet('${vendor.bmanNo}')">${vendor.attachFileCode}</a></span>
			</c:if>
			</p>
			
			<!-- 상담 이력 -->
			<div class="s_title mt20">
				<h2>상담 이력</h2>
			</div>
			<div class="tb_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>주요 상품정보</caption>
				<colgroup>
					<col width="68px">
					<col width="171px">
					<col width="135px">
					<col width="135px">
					<col width="135px">
					<col width="*"></colgroup>
				<thead>
					<tr>
						<th width="50" class="line">구분</th>
						<th width="68px" class="line">신청업체</th>
						<th width="171px" class="line">상담신청일자</th>
						<th width="135px" class="line">서류심사</th>
						<th width="135px" class="line">상담결과</th>
						<th width="135px" class="line">품평회/입점</th>
						<th width="*">최종결정자 </th>
					</tr>
				</thead>
				
				
				<tbody>
				<c:if test="${not empty conList}">
					<c:forEach items="${conList}" var="list" varStatus="index" >
						<tr class="line_dot">
							
							<td class="t_center">현재</td>
							<td class="t_center num">${list.BMAN_NO}</td>
							<td class="t_center"><p>${list.REG_DATE }</p></td>
							
							<c:choose>
							     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'M'}">
							     	<td class="t_center">평가중</td>
							     </c:when>
							     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y'}">
							     	<td class="t_center">통과(${list.PAPE_JGM_PROC_DY })</td>
							     </c:when>
							     <c:otherwise>
							     	<c:choose>
							     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '1' }">
							     			<td class="t_center" title="분류선택오류  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
							     		</c:when>
							     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '2' }">
							     			<td class="t_center" title="정보미비  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
							     		</c:when>
							     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '3' }">
							     			<td class="t_center" title="취급부적합상품  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
							     		</c:when>
							     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '4' }">
							     			<td class="t_center" title="기존상품중복  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
							     		</c:when>
										<c:otherwise>
											<td class="t_center" title="${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
										</c:otherwise>
							     	</c:choose>
					     			
							     </c:otherwise>
						    </c:choose>
							    
						    <c:choose>
						    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N'}">
						    		<td align="center">상담반려</td>
						    	</c:when>
						    	<c:otherwise>
						    		<c:choose>
									     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'M'}">
									     	<td class="t_center">평가중</td>
									     </c:when>
									     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'Y'}">
									     	<td class="t_center">통과(${list.CNSL_PROC_DY })</td>
									     </c:when>
									     <c:otherwise>
									     	<c:choose>
									     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '1' }">
									     			<td class="t_center" title="상품디자인미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
									     		</c:when>
									     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '2' }">
									     			<td class="t_center" title="상품구색미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
									     		</c:when>
												<c:otherwise>
													<td class="t_center" title="${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
												</c:otherwise>
									     	</c:choose>
									     </c:otherwise>
								    </c:choose>
						    	</c:otherwise>
						    </c:choose>
						    
						    <c:choose>
						    	<c:when test="${list.CNSL_RSLT_DIVN_CD eq 'N' or list.PAPE_JGM_RSLT_DIVN_CD eq 'N' }">
						    		<td align="center">품평회반려</td>
						    	</c:when>
						    	<c:otherwise>
						    		<c:choose>
									     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'M'}">
									     	<td class="t_center">평가중</td>
									     </c:when>
									     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'Y'}">
									     	<td class="t_center">통과(${list.ENTSHP_PROC_DY })</td>
									     </c:when>
									     <c:otherwise>
									     	<td class="t_center">반려(${list.ENTSHP_PROC_DY })</td>
									     </c:otherwise>
								    </c:choose>
						    	</c:otherwise>
						    </c:choose>    
							    
							<td class="t_center"><p>최종결정자</p></td>
						</tr>
					</c:forEach>
				</c:if>
				
				<c:if test="${not empty conList_past}">
					<c:forEach items="${conList_past}" var="list" varStatus="index" >
						<tr class="line_dot">
							
							<td class="t_center">과거</td>
							<td class="t_center num">${list.BMAN_NO}</td>
							<td class="t_center"><p>${list.REG_DATE }</p></td>
							
							<c:choose>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y'}">
								     	<td class="t_center">통과(${list.PAPE_JGM_PROC_DY })</td>
								     </c:when>
								     <c:otherwise>
								      	<c:choose>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '1' }">
								     			<td class="t_center" title="분류선택오류  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '2' }">
								     			<td class="t_center" title="정보미비  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '3' }">
								     			<td class="t_center" title="취급부적합상품  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '4' }">
								     			<td class="t_center" title="기존상품중복  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
											<c:otherwise>
												<td class="t_center" title="${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
											</c:otherwise>
								     	</c:choose>
								     </c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N'}">
							    		<td align="center">상담반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'Y'}">
										     	<td class="t_center">통과(${list.CNSL_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<c:choose>
										     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '1' }">
											     			<td class="t_center" title="상품디자인미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
											     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '2' }">
											     			<td class="t_center" title="상품구색미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
														<c:otherwise>
															<td class="t_center" title="${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
													</c:otherwise>
										     	</c:choose>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.CNSL_RSLT_DIVN_CD eq 'N' or list.PAPE_JGM_RSLT_DIVN_CD eq 'N' }">
							    		<td align="center">품평회반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'Y'}">
										     	<td class="t_center">통과(${list.ENTSHP_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<td class="t_center">반려(${list.ENTSHP_PROC_DY })</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>    
							    
							<td class="t_center"><p>최종결정자</p></td>
						</tr>
					</c:forEach>
				</c:if>
				</tbody>
				</table>
			</div>
			






		</div>
	</div>

</div>
<input type="hidden" name="update_gb" value="" ID="Hidden1">	
</form>

    
</body>
</html>