
<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	function PopupWindow(pageName) {
		var cw=400;
		var ch=300;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	function doSearch() {
		var rcon = document.getElementById('content_wrap');
		var form = document.forms[0];
		//alert(rcon.style.width);
		if(!dateValid(form)){
			return;
		}
		var currentNodeValue = $("#entp_cd").val();
		
		<c:choose>
				<c:when test="${paramMap.vendorTypeCd eq '06'}">
				if($("#entp_cd").val() == ""){
					alert('업체선택은 필수입니다.');
					$("#entp_cd").focus();
					return;
				}
				</c:when>
		</c:choose>
		//if(currentNodeValue == '899891') currentNodeValue = '001824'; 
		//if(currentNodeValue == '010710') currentNodeValue = '011837';


		if( $("select[name=onOffDivnCode]").val() == '0' ) {
			var productStatusValue = $("select[name=statusFlag]").val().substr(0,1);
			var adjustStatusValue  = $("select[name=statusFlag]").val().substr(1,1);
			$("input[name=productStatus]").val(productStatusValue);
			$("input[name=adjustStatusFlag]").val(adjustStatusValue);
		} else {
			var productStatusValue = $("select[name=statusFlag]").val();
			var adjustStatusValue  = $("select[name=statusFlag]").val();
			$("input[name=productStatus]").val(productStatusValue);
			$("input[name=adjustStatusFlag]").val(adjustStatusValue);
			}
		
		
		$("#entpCode").val(currentNodeValue);

		loadingMaskFixPos();
		form.action  = '${ctx}/edi/product/PEDMPRO0001.do';
		form.submit();
	}


	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(form){

		var startDate = form.startDate.value;
		var endDate = form.endDate.value;
		var rangeDate = 0;
		var resultFlag = true;
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			resultFlag =  false;
		}


		// startDate, endDate 는 yyyy-mm-dd 형식

	    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	   var intStartDate = parseInt(startDate);
	   var intEndDate = parseInt(endDate);
			
		
	    if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        resultFlag =  false;
	    }

		
	    intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
	    endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);

	    /*
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			resultFlag =  false ;
		}	
		*/
		return resultFlag;
	}
	
	function popupSearch(){		
		
		
		var tbody1 = $('#dataTable tbody');
					

		var form=document.forms[0];	

		var date=form.startDate.value+"~"+form.endDate.value;
		var productDivnName = $("select[name=productDivnCode] option:selected").text();
		var selectedVendor  = $("#entp_cd option:selected").text();
		var productStatus   = $("select[name=statusFlag] option:selected").text();
		var onOffDivn       = $("select[name=onOffDivnCode] option:selected").text();

		form.staticTableBodyValue.value = "<CAPTION>신상품 등록현황<br>"+
		"[조회기간 : "+date+"] [상품 구분: "+productDivnName+"] [협력업체 : "+selectedVendor+"]<br>"+
		"[온오프구분 : "+onOffDivn+"] [상품 상태: "+productStatus+"]<br>"+
			"</CAPTION>"+tbody1.parent().html() ;
			
		form.name.value = "temp";
		form.action="${ctx}/edi/comm/PEDPCOM0003.do";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="_self";

	}	

	/* 상품상세보기 이동  */
	function viewProductInfo(newProductCode) {
		location.href="${ctx}/edi/product/PEDMPRO000301.do?newProductCode="+newProductCode+"&mode=viewInfo";
	}
	
	
	$(function() {
		<c:if test="${not empty param.statusFlag}">
			
		</c:if>
		
		
	});
	
	
	

	function uploadPOG(imgSeq, colorCode, proCode) 

	
	{//이미지 등록/수정
		alert("구 EDI에서는 더이상 이미지 등록이 불가능 합니다.");
		return;
		
	 	window.open("${ctx}/edi/product/PEDPPRO000101.do?imgSeq="+imgSeq+"&colorCode="+colorCode+"&proCode="+proCode, "IMAGE_CHANGE", "status=no, resizeable=yes, width=400, height=250, left=480,top=290, scrollbars=no");
	}
	
	

	function registBarcode(val){
		alert("구 EDI에서는 더이상 물류바코드 등록이 불가능합니다.");
		return;
		
		var form = document.forms[0];
		
		form.new_prod_id.value=val;
		form.target="_blankPop";
		form.action ="<c:url value='/edi/product/PEDMPRO0006SearchTmp.do'/>";

		var popInfo = window.open('','_blankPop','top=0, left=0, width=850, height=380, toolbar=no, status=yes, scrollbars=yes');

		popInfo.focus();
	    form.submit();
		  
		form.target = "";
		form.action = "";
		
	}	

	function updateBarcode(val, val2){
		alert("구 EDI에서는 더이상 물류바코드 등록이 불가능합니다.");
		return;
		var form = document.forms[0];
		
		form.new_prod_id.value=val;
		form.vencd.value=val2;
		
		form.target="_blankPop";
		form.action ="<c:url value='/edi/product/PEDMPRO0006UpdatePageTmp.do'/>";

		var popInfo = window.open('','_blankPop','top=0, left=0, width=850, height=380, toolbar=no, status=yes, scrollbars=yes');

		popInfo.focus();
	    form.submit();
		  
		form.target = "";
		form.action = "";
		
	}	
	
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#entp_cd").val(strVendorId);
	}
</script>

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
			
			<input type="hidden"  name="productStatus"    />
			<input type="hidden"  name="adjustStatusFlag" /> 
			
			<input type="hidden" name="new_prod_id">
			<input type="hidden" name="vencd">
			
			<input type="hidden" name="staticTableBodyValue">
			<input type="hidden" name="name">
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">검색조건</li>
						<li class="btn">
					 	<font color="blue"></font>
					 
					 		<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" onclick="popupSearch();"><span><spring:message code="button.common.excel"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					 
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 신상품등록기간 </th>
						<td>
							
							<input type="text" class="day" name="startDate" readonly value="${paramMap.startDate}" style="width:80px;" ><img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" name="endDate" readonly value="${paramMap.endDate}" style="width:80px;" ><img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> 상품구분</th>
						<td>
							<select name="productDivnCode" id="prod_fg_id">
								<option value="1" <c:if test="${paramMap.proCode eq 1 }">selected</c:if> >규격</option>
								<option value="5" <c:if test="${paramMap.proCode eq 5 }">selected</c:if> >패션 </option>
							</select>
						</td>
					</tr>
					<tr>
						<th>협력업체 코드</th>
						<td>
							<c:choose>
									<c:when test="${paramMap.vendorTypeCd eq '06'}">
										<c:if test="${not empty param.entp_cd}">
										<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" value="" style="width:40%;"/>
										</c:if>
										<c:if test="${empty param.entp_cd}">
											<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" value="" style="width:40%;"/>
										</c:if>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${paramMap.vendorTypeCd != '06'}">
										<c:if test="${not empty param.entp_cd}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
										<c:if test="${ empty param.entp_cd}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
									</c:when>
							</c:choose>
						
							
						</td>
						<th>상품상태</th>
						<td >
							<select name="statusFlag">
				                <option value="99">전체 </option>
								<option value="50">확정 </option>
								<option value="51">수정확정 </option>
								<option value="30">거절 </option>
								<option value="10">심사중 </option>
							</select>
							
							<select name='onOffDivnCode'>
								<option value="0" <c:if test="${paramMap.onOff eq 0 }">selected</c:if> >온오프 </option>
								<option value="1" <c:if test="${paramMap.onOff eq 1 }">selected</c:if> >온라인전용 </option>
								<option value="2" <c:if test="${paramMap.onOff eq 2 }">selected</c:if> >소셜 </option>
							</select>
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">검색내역</li>
					</ul>
				
				<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;">
				<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1388 bgcolor=efefef> 
					<colgroup>
						
						<col style="width:40px;">
						<col style="width:70px;">
						<col style="width:70px;">
						<col style="width:60px;">						
						<col style="width:80px;">
						<col style="width:80px;">					
						<col style="width:80px;">	
						
						<col >
						<col style="width:90px;">
						<col style="width:90px;">
						<c:if test="${ paramMap.proCode eq '5' and paramMap.onOff eq '0' }"><col></c:if>
						<col style="width:110px;">
						<col style="width:80px;">
						
						<col style="width:80px;"/>
						<col style="width:80px;">
						<col style="width:80px;">
						
					</colgroup>
					
					<tr class="r1" bgcolor="#e4e4e4">
						<th>No.</th>
						<th>등록일자</th>
						<th>협력업체</th>
						<th>상태</th>						
						<th>이미지</th>
						<th>물류바코드</th>
						<th>VIC 대상</th>						
						<th>상품명</th>
						<th>판매(88)코드</th>
						<th>대표상품코드</th>
						<th>상품코드</th>
						<th>상품순번</th>
						<c:if test="${ paramMap.proCode eq '5' and paramMap.onOff eq '0' }"><th>컬러/사이즈</th></c:if>
						<th>상품규격</th>
						<th>입수</th>
						<th>원가</th>
						<th>매가</th>
						<th>이익율</th>
						
					</tr>
					
					<c:set var="row_cnt"  value="1" />
					<c:set var="oldColorName"      value="" />
					<c:set var="nowColorName"      value="" />
					<c:set var="pgm_id"      value="" />
					<c:set var="subCnt"     value="0" />
					<c:if test="${fn:length(newProductList) > 0 }">
						<c:forEach items="${newProductList}" var="newProduct" varStatus="index" >
							
							<c:set var="nowColorName" value="${newProduct.colorCodeCd}_${newProduct.programId}" />
							
							
							<tr class="r1"  bgcolor=ffffff>
								<td align="center">${index.count }</td>
								<td align="center"><fmt:formatDate value="${newProduct.regDate}" pattern="yyyy-MM-dd" /></td>
								<td align="center">${newProduct.entpCode}</td>
								<td align="center"> 
									<c:if test="${(newProduct.confirmFlag == '5' && newProduct.adjustFlag == '0')||(newProduct.confirmFlag == '5' && newProduct.adjustFlag == null) }">
										확정
									</c:if>
									
									<c:if test="${ newProduct.confirmFlag == '5'
										&& newProduct.adjustFlag == '1' }">
										수정확정
									</c:if>
									
									<c:if test="${ newProduct.confirmFlag == '3' }">
										거절
									</c:if>
									
									
									<!--이동빈 추가 20140930-->									
									<c:if test="${ newProduct.confirmFlag == 'A' }"
									>
										온라인<br>가승인 심사중
									</c:if>
									<c:if test="${ newProduct.confirmFlag == 'R' }"
									>
										온라인 <br>가승인 거절
									</c:if>
									
									<c:if test="${ newProduct.confirmFlag == '0' ||
											newProduct.confirmFlag == '1' || 
											newProduct.confirmFlag == '2' ||
											newProduct.confirmFlag == '4' ||
											newProduct.confirmFlag == '9'  }"
									>
										심사중									
									</c:if>
								</td>
								
								<c:if test="${ paramMap.onOff == '0' }">
								
								<c:if test="${ paramMap.proCode eq '5'}">
									<%-- imageConfirmFlag IMG_CFM_FG  
							             confirmFlag      CFM_FG
							         --%>
									<c:if test="${oldColorName != nowColorName }" >   <%--  동일한 컬러 일경우 row 합치고  이미즈등록은 하나만 받기위해 --%> 
										<td align="center" rowspan="${newProduct.colorCount}">
										
										
											<c:if test="${ empty newProduct.imageConfirmFlag }"> 
											
												<c:choose>
													<c:when test="${ newProduct.confirmFlag == '5' }">
														<FONT COLOR ="BLUE">
															<%-- <a href="#" onClick="uploadPOG('${newProduct.programId}','${newProduct.colorCodeCd}','${paramMap.proCode}');">등록<br>요망</a> --%>
															등록요망
														</FONT>
													</c:when>
													<c:otherwise>
														<FONT COLOR ="BLUE">
															<%-- <a href="#" onClick="uploadPOG('${newProduct.programId}','${newProduct.colorCodeCd}','${paramMap.proCode}');">미등록</a> --%>
															미등록
														</FONT>
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${not empty newProduct.imageConfirmFlag }">
												<c:if test="${ newProduct.imageConfirmFlag == '0' }">
													<FONT COLOR ="GREEN">등록</FONT>
												</c:if>
												<c:if test="${ newProduct.imageConfirmFlag == '1' }">
													<FONT COLOR ="RED">심사중</FONT>
												</c:if>
												<c:if test="${ newProduct.imageConfirmFlag == '2' }">
													<FONT COLOR ="RED">
														<%-- <a href="#" onClick="uploadPOG('${newProduct.programId}','${newProduct.colorCodeCd}','${paramMap.proCode}');">반려</a> --%>
														반려
													</FONT>
												</c:if>
												<c:if test="${ newProduct.imageConfirmFlag == '3' }">
													<FONT COLOR ="RED">확정</FONT>
												</c:if>
											</c:if>
										</td>
									</c:if>
								</c:if>
									
								<%-- imageConfirmFlag IMG_CFM_FG  
							             confirmFlag      CFM_FG
							    --%>
								
								<c:if test="${ paramMap.proCode != '5'}">
									<td align="center">
										
										<c:if test="${ empty newProduct.imageConfirmFlag }">   
											
											<c:choose>
												<c:when test="${ newProduct.confirmFlag == '5' }">
													<FONT COLOR ="BLUE">
														<%-- <a href="#" onClick="uploadPOG('${newProduct.programId}','${newProduct.colorCodeCd}','${paramMap.proCode}');">등록<br>요망</a> --%>
														등록요망
													</FONT>
												</c:when>
												<c:otherwise>
													<FONT COLOR ="BLUE">
														<%-- <a href="#" onClick="uploadPOG('${newProduct.programId}','${newProduct.colorCodeCd}','${paramMap.proCode}');">미등록</a> --%>
														미등록
													</FONT>
												</c:otherwise>
											</c:choose>
										</c:if>
										<c:if test="${not empty newProduct.imageConfirmFlag }">
											<c:if test="${ newProduct.imageConfirmFlag == '0' }">
												<FONT COLOR ="GREEN">등록</FONT>
											</c:if>
											<c:if test="${ newProduct.imageConfirmFlag == '1' }">
												<FONT COLOR ="RED">심사중</FONT>
											</c:if>
											<c:if test="${ newProduct.imageConfirmFlag == '2' }">
												<FONT COLOR ="RED">
												<%-- <a href="#" onClick="uploadPOG('${newProduct.programId}','${newProduct.colorCodeCd}','${paramMap.proCode}');">반려</a> --%>
												반려
												</FONT>
											</c:if>
											<c:if test="${ newProduct.imageConfirmFlag == '3' }">
												<FONT COLOR ="RED">확정</FONT>
											</c:if>
										</c:if>
									</td>
								</c:if>
								
							</c:if>	
							<c:if test="${ paramMap.onOff != '0' }"><td align=center>-</td></c:if>
								
								
								<td align="center">
								
								<c:if test="${ paramMap.onOff == '0' }">
									<c:if test="${ empty newProduct.logiConfirmFlag}">
										<c:choose>
											<c:when test="${ newProduct.confirmFlag == '5' }">
												<FONT COLOR ="BLUE">등록<br>요망</FONT>
											</c:when>
											<c:otherwise>
												<%-- <a href="javascript:registBarcode('${newProduct.programId }')">
													<FONT COLOR ="BLUE">미등록</FONT>
												</a> --%>
												미등록
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${not empty newProduct.logiConfirmFlag}">
										<c:if test="${ newProduct.logiConfirmFlag == '00' ||
													 newProduct.logiConfirmFlag == '02' }">
												<FONT COLOR ="RED">심사중</FONT>
										</c:if>
										<c:if test="${ newProduct.logiConfirmFlag == '03' }">
											<%-- <a href="javascript:updateBarcode('${newProduct.programId }','${newProduct.entpCode }')">
											<FONT COLOR ="RED">수정요청</FONT>
											</a> --%>
											<FONT COLOR ="RED">수정요청</FONT>
										</c:if>
										<c:if test="${ newProduct.logiConfirmFlag == '09' }">
											<FONT COLOR ="green">확정</FONT>
										</c:if>
									</c:if>
								</c:if>	
								<c:if test="${ paramMap.onOff != '0' }">-</c:if>
								</td>
								
						
								<td align="center">
								<c:if test="${ newProduct.wUseFlag == '0'}">미취급</FONT></c:if>
								<c:if test="${ newProduct.wUseFlag == '1'}">취급     </FONT></c:if>	
								</td>
								
								
								<td>&nbsp;<a href="javascript:viewProductInfo('${newProduct.programId}')" 
								title="   	입수: <fmt:formatNumber value="${newProduct.orderIpsu}" type="number" currencySymbol="" />
								원가: <fmt:formatNumber value="${newProduct.normalProductCost}" type="number" currencySymbol="" />
								매가: <fmt:formatNumber value="${newProduct.normalProductSalePrice}" type="number" currencySymbol="" />
								이익률: <fmt:formatNumber value="${newProduct.profitRate}" type="number" currencySymbol="" />%  ">
								${newProduct.productName}</a>
								</td>
								<td align="center">${newProduct.sellCode}</td>
								<td align="center">${newProduct.onlineRepProdCd}</td>
								<td align="center">${newProduct.newProductCode}</td>
								<td align="center">${newProduct.programId}</td>
								<c:if test="${ paramMap.proCode eq '5'  and paramMap.onOff eq '0' }"><td>${newProduct.colorName} / ${newProduct.sizeName}</td></c:if>
								<td>&nbsp;${newProduct.productStand}</td>
								<td align="right"><fmt:formatNumber value="${newProduct.orderIpsu}" type="number" currencySymbol="" />&nbsp;</td>
								
								<td align="right"><fmt:formatNumber value="${newProduct.normalProductCost}" type="number" currencySymbol="" />&nbsp;</td>
								<td align="right"><fmt:formatNumber value="${newProduct.normalProductSalePrice}" type="number" currencySymbol="" />&nbsp;</td>
								<td align="right"><fmt:formatNumber value="${newProduct.profitRate}" type="number" currencySymbol="" />%&nbsp;</td>
								
								
							
								
							</tr>
							<c:set var="oldColorName" value="${newProduct.colorCodeCd}_${newProduct.programId}" />
							<c:set var="pgm_id" value="${newProduct.programId}" />
							<c:set var="subCnt"    value="${subCnt+1 }" />
								
							<c:set var="row_cnt" value="${row_cnt+1 }" />
							
							
						</c:forEach>
					   <c:forEach begin="${row_cnt}" end ="20">
					      <tr class="r1"  bgcolor=ffffff>
						    <td>&nbsp;</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<c:if test="${ paramMap.proCode eq '5'  and paramMap.onOff eq '0' }"><td></td></c:if>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>	
					   </c:forEach>
					</c:if>
					<c:if test="${empty newProductList }">
						<tr><td colspan="15" bgcolor=ffffff align=center>Data가 없습니다.</td></tr>
					</c:if>
					</table>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품</li>
					<li>신규상품관리</li>
					<li class="last">신규상품등록현황조회</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
