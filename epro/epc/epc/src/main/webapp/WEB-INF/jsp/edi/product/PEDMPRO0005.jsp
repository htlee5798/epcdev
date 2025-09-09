
<%--
- Author(s): 
- Created Date: 2011. 10. 20
- Version : 1.0
- Description : 물류바코드현황
 
--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	
	function doSearch() {
		
		
		var form = document.forms[0];
		
		if(!dateValid(form)) return;
		
		<c:choose>
				<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
				if($("#entp_cd").val() == ""){
					alert('업체선택은 필수입니다.');
					$("#entp_cd").focus();
					return;
				}
				</c:when>
		</c:choose>

		loadingMaskFixPos();
		form.action  = '${ctx}/edi/product/PEDMPRO0005.do';
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
	
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			return false;
		}
		
		// startDate, endDate 는 yyyy-mm-dd 형식

	    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	   var intStartDate = parseInt(startDate);
	   var intEndDate = parseInt(endDate);
			
	    if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        return false;
	    }
	   
		return true;
	}

	
	function excelDown() {

		var header = $('#headerTable tbody');
		var data = $('#dataTable tbody');

		var form=document.forms[0];	

		var date=form.startDate.value+"~"+form.endDate.value;
		var barCodeStatus=$("select[name=logi_cfm] option:selected").text();

		var selectedVendor=$("#venCd option:selected").text();



		form.staticTableBodyValue.value = "<CAPTION>물류바코드 목록<br>"+
		"[바코드 등록기간 : "+date+"] [바코드 상태: "+barCodeStatus+"] [협력업체 : "+selectedVendor+"]<br>"+
			"</CAPTION>"+header.html() +data.html();
			
		form.name.value = "temp";
		form.action="${ctx}/edi/comm/PEDPCOM0003.do";
		form.target="_blank";
		form.submit();
		form.action="";
		form.target="_self";
	}
	
	function helpWin(state) {
		if(state) document.getElementById("ViewLayer").style.display = "";
		else document.getElementById("ViewLayer").style.display = "none";
	}
	
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#venCd").val(strVendorId);
	}
</script>

</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
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
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" onclick="excelDown();"><span><spring:message code="button.common.excel"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:100px;" />
							<col style="width:160px;" />
							<col style="width:100px;" />
							<col style="width:160px;" />
						</colgroup>
						<tr> 
							
							<th><span class="star">*</span> 협력업체</th>
							<td>
								<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty searchParam.venCd}">
										<input type="text" name="venCd" id="venCd" readonly="readonly" value="${searchParam.venCd}" style="width:40%;"/>
										</c:if>
										<c:if test="${empty searchParam.venCd}">
											<input type="text" name="venCd" id="venCd" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
													<c:if test="${not empty searchParam.venCd}">
														<html:codeTag objId="venCd" objName="venCd" width="150px;" selectParam="${searchParam.venCd}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
													</c:if>
													<c:if test="${ empty searchParam.venCd}">
														<html:codeTag objId="venCd" objName="venCd" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
													</c:if>
									</c:when>
								</c:choose>
							</td>
							
							<th width=100px;><span class="star"></span>상태</th>
							<td>
							<!-- 	
							  <select name="logi_cfm" id="buying">
									<option value="">전체</option>
									<option value="02">심사중</option>
									<option value="03">수정요청</option>
									<option value="09">확정</option>
									<option value="01">등록불가</option>
								</select>
							 -->	
								
								 <select name="logi_cfm" id="buying">
									<option value="">전체</option>
									<option value="02" <c:if test="${param.logi_cfm eq '02' }">selected</c:if>>심사중</option>
									<option value="03" <c:if test="${param.logi_cfm eq '03' }">selected</c:if>>수정요청</option>
									<option value="09" <c:if test="${param.logi_cfm eq '09' }">selected</c:if>>확정</option>
									<option value="01" <c:if test="${param.logi_cfm eq '01' }">selected</c:if>>등록불가</option>
								</select>
								
							</td>
						</tr>
						<tr>
						
							
							<th><span class="star">*</span> 바코드 등록기간</th>
							<td>
								<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${searchParam.startDate}" style="width:80px;" /> <img src="/images/bos/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${searchParam.endDate}" style="width:80px;" /> <img src="/images/bos/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
							</td>							
							<th >판매(88)코드</th>
							<td>
									<input type="text"  name="srcmk_cd"  value="${searchParam.srcmk_cd}" style="width:140px;" />
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
						<li class="tit">신규상품 등록현황</li>
						<li class="btn"><a href="#" class="btn" onclick="helpWin(true);"><span><spring:message code="button.common.help"/></span></a></li>
					</ul>
					<table id="headerTable" class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="30px"/>
						<col width="70px"/>
						<col width="90px"/>
						<col width="100px"/>
						<col width="100px"/>
						<col width="60px" />
						<col width="30px" /> 
						<col width="30px"  />
						<col width="30px"  />
						<col width="60px" />
						<col width="60px"/>
						<col width="60px"/>
						<col />
					</colgroup>
					<tr>
						<th rowspan=2>No.</th>
						<th rowspan=2>등록일자</th>
						<th rowspan=2>판매(88)코드</th>
						<th rowspan=2>상품명</th>
						<th rowspan=2>물류바코드</th>
						<th colspan=4>박스</th>
						<th rowspan=2>소터<br>구분</th>
						<th rowspan=2>사용<br>여부</th>
						<th rowspan=2>VIC<br>대상</th>						
						<th rowspan=2>물류바코드<br>상태</th>
					</tr>
					<tr>
						<th>가로(mm)</th>
						<th>세로(mm)</th>
						<th>높이(mm)</th>
						<th>중량(kg)</th>
					</tr>
					</table>
					<div class="datagrade_scroll_sum">
					<table id="dataTable" class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="30px"/>
						<col width="70px"/>
						<col width="90px"/>
						<col width="100px"/>
						<col width="100px"/>
						<col width="60px"  />
						<col width="30px"   />					 
						<col width="30px" />
						<col width="30px" />
						<col width="60px"/>
						<col  width="60px"/>
						<col  width="60px"/>
						<col />
					</colgroup>
					<c:if test="${fn:length(barcodeList) > 0 }">
						
						<c:forEach items="${barcodeList}" var="barcode" varStatus="index" >
							<tr class="r1">
								<td align="center">${index.count }</td>
								<td align="center">${barcode.REG_DT }</td>
								<td align="left">&nbsp;${barcode.SRCMK_CD }</td>
								<td align="center">${barcode.PROD_NM}</td>
								<td align="center">${barcode.LOGI_BCD}</td>								
								<td align="center">${barcode.WIDTH}</td>
								<td align="center">&nbsp;${barcode.LENGTH}</td>
								<td align="center">${barcode.HEIGHT}</td>
								<td align="center">${barcode.WG}</td>
								<td align="center">
									<c:if test="${barcode.SORTER_FG == '0'}">
									<font color ="red">논<br>소터</font>
									</c:if>
									<c:if test="${barcode.SORTER_FG == '1'}">
									<FONT COLOR ="BLUE">소터</FONT>
									</c:if>
								</td>
								
								<td align="center">
									 <input type="checkbox" name="chkbox1"  value="0" 
									 	<c:if test="${barcode.USE_FG == '1'}">
									 	checked  
									 	</c:if> 
									 	
										 align="absmiddle" class=inpcheck 	 disabled>
								</td>
								
								<td align="center">
									<c:if test="${barcode.W_USE_FG == ''}">
									<font color ="red">미대상</font>
									</c:if>
									<c:if test="${barcode.W_USE_FG == '0'}">
									<FONT COLOR ="red">미대상</FONT>
									</c:if>
									<c:if test="${barcode.W_USE_FG == '1'}">
									<FONT COLOR ="BLUE">대상</FONT>
									</c:if>
								</td>
								
								
								
								<td align="center">
									<c:if test="${barcode.LOGI_CFM_FG == '00'}">
										<FONT COLOR ="BLUE">
											심사중<br>판매코드 미등록
										</FONT>
									</c:if>
									<c:if test="${barcode.LOGI_CFM_FG == '01'}">
										<FONT COLOR ="green">
											등록불가<br> 물류바코드<br>담당자에게문의<br>상품/판매코드 비정상
										</FONT>								
									</c:if>
									<c:if test="${barcode.LOGI_CFM_FG == '02'}">
										<FONT COLOR ="BLUE">심사중<br>등록요청</FONT>								
									</c:if>
									<c:if test="${barcode.LOGI_CFM_FG == '03'}">
										<a href="javascript:registBarcode('${barcode.SRCMK_CD}')" >
											<FONT COLOR ="red">수정요청<br>체적/중량오류</FONT>
										</a>								
									</c:if>
									<c:if test="${barcode.LOGI_CFM_FG == '04'}">
										<FONT COLOR ="green">
											등록불가<br> 물류바코드<br>
											담당자에게문의<br>기등록코드
										</FONT>										
									</c:if>
									<c:if test="${barcode.LOGI_CFM_FG == '05'}">
										<a href="javascript:registBarcode('${barcode.SRCMK_CD}')" >
											<FONT COLOR ="red">수정요청<br>물류코드오류</FONT>
										</a>
									</c:if>
									<c:if test="${barcode.LOGI_CFM_FG == '09'}">
										<FONT COLOR ="green">확정<br>등록완료</FONT>								
									</c:if>
								</td>
							</tr>
						</c:forEach>
						
					</c:if>
					<c:if test="${fn:length(barcodeList) == 0 }">
						<tr><td colspan="12" align=center>Data가 없습니다.</td></tr>
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
					<li>상품정보</li>
					<li>신규상품관리</li>
					<li class="last">물류바코드현황</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<div id="ViewLayer" style="display:none; position:absolute;  width:500px; height:450px; left: 100; top: 100px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
	<tr>
		<td bgcolor=ffffff>
			
			
			
					
			<table cellspacing=0 cellpadding=0 border=0 width=100%>
				<tr height=30>
					<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>물류바코드 상태 확인 방법</b></td>
					<td width=50><a href="#" class="btn" onclick="helpWin(false);"><span><spring:message code="button.common.close"/></span></a></td>
				</tr>
				<tr><td height=2 bgcolor="f4383f" colspan=2></td></tr>
			</table>
			
			<table cellspacing=0 cellpadding=0 border=0 width=100%>
				<tr><td colspan=2 height=10></td></tr>
				<tr>
					<td colspan=2><B>&nbsp; &nbsp; 1.심사중</B></td>
				</tr>
				<tr>
					<td width=30></td>
					<td> 일괄 심사 후 승인 예정이므로 17:00시 이후 ‘확정’ 여부 확인 
			           (17:00시 이후에도 물류바코드 상태가 심사중일 경우 물류팀에 문의 : 055-310-2501) 
			        </td>
				</tr>
				<tr><td colspan=2 height=10></td></tr>
				<tr>
					<td colspan=2><B>&nbsp; &nbsp; 2.수정요청</B></td>
				</tr>
				<tr>
					<td></td>
					<td> 수정요청 사항 확인 후 상품등록->물류바코드 등록 화면에서 물류바코드 추가 버튼 클릭 후 
			                  물류바코드 정보 정확히 입력 후 저장 </td>
				</tr>
				<tr><td colspan=2 height=10></td></tr>
				<tr>
					<td colspan=2><B>&nbsp; &nbsp; 3.확정</B></td>
				</tr>
				<tr>
					<td></td>
					<td>해당 상품의 물류바코드 사용 가능</td>
				</tr>
				<tr><td colspan=2 height=10></td></tr>
				<tr>
					<td></td>
					<td><font color="7682ce"><b>※반드시 발주 전일까지 물류바코드 상태가‘확정'이 되어 있는지 확인.<br>(당일 확정 -> 당일 발주는 센터 납품 불가능.)</b></font></td>
				</tr>
				<tr><td colspan=2 height=10></td></tr>
			</table>
						
		</td>
	</tr>
</table>		
</div>
</body>
</html>
