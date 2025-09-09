
<%--
- Author(s): 
- Created Date: 2015. 12. 17
- Version : 1.0
- Description : 물류바코드현황
 
--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	
	
	function doSearch() {
		
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val();
		// 협력업체(개별)
		searchInfo["venCd"] 		= $("#searchForm select[name='venCd']").val();
		// 상품코드
		searchInfo["srcmk_cd"] 	= $("#searchForm input[name='srcmk_cd']").val();
		// 발주구분
		searchInfo["logi_cfm"]		=	$("#searchForm select[name='logi_cfm']").val();
		
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/NEDMPRO0210Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {

					//footer Setting 
					if(data.barcodeList.length > 0){
						// 조회 성공시 
						$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
					}else{
						//조회된 건수 없음
						$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
					}
					
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				},
				error : function(request, status, error, jqXHR) {
					// 요청처리를 실패하였습니다.
					$('#resultMsg').text('msg.common.fail.request');
				} 
			});	
		}
		
		
		/* _eventSearch() 후처리(data  객체 그리기) */
		function _setTbodyMasterValue(json) { 
			var data = json.barcodeList;
			var tempData = [];
			
			for(var i = 0;i<data.length;i++){
				tempData[i] =  data[i];
				tempData[i]['index'] = i+1;
				
			}
				
			setTbodyInit("dataListbody");	// dataList 초기화
			
			if (data.length > 0) {
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
			} else { 
				setTbodyNoResult("dataListbody", 9);
			}
					
		}
			
	}



	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(){
		var startDate 	= $("#searchForm input[name='srchStartDate']").val();
		var endDate 	= $("#searchForm input[name='srchEndDate']").val();
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
	        return false;
	    }
	   
		return true;
	}

	
	function excelDown() {

		 var tbody1 = $('#dataTable tbody');
		

		var form=document.forms[0];	

		var date=form.srchStartDate.value+"~"+form.srchEndDate.value;
		var barCodeStatus=$("select[name=logi_cfm] option:selected").text();

		var selectedVendor=$("#venCd option:selected").text();



		form.staticTableBodyValue.value = "<CAPTION>물류바코드 목록<br>"+
		"[바코드 등록기간 : "+date+"] [바코드 상태: "+barCodeStatus+"] [협력업체 : "+selectedVendor+"]<br>"+
			"</CAPTION>"+tbody1.parent().html();
			
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
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr  class="r1"  bgcolor=ffffff>
	<td align="center"><c:out value="\${index}"/></td>
	<td align="center"><c:out value="\${REG_DT}"/></td>
	<td align="center">&nbsp;<c:out value="\${SRCMK_CD}"/></td>
	<td align="left"><c:out value="\${PROD_NM}"/></td>
	<td align="center"><c:out value="\${LOGI_BCD}"/></td>								
	<td align="center"><c:out value="\${WIDTH}"/></td>
	<td align="center">&nbsp;<c:out value="\${LENGTH}"/></td>
	<td align="center"><c:out value="\${HEIGHT}"/></td>
	<td align="center"><c:out value="\${WG}"/></td>
	<td align="center">
		{%if SORTER_FG == '0' %}
		<font color ="red">논소터</font>
		{%elif SORTER_FG == '1' %}
		<FONT COLOR ="BLUE">소터</FONT>
		{%/if%}
	</td>
	
	<td align="center">
		 <input type="checkbox" name="chkbox1"  value="0" 
		 	{%if USE_FG == '1' %}
		 	checked
		 	{%/if%}  
			 align="absmiddle" class=inpcheck 	 disabled>
	</td>
	
	<td align="center">
		{%if W_USE_FG == null %}
		<font color ="red">미대상</font>
		{%/if%}
		{%if W_USE_FG == '0' %}
		<FONT COLOR ="red">미대상</FONT>
		{%/if%}
		{%if W_USE_FG == '1' %}
		<FONT COLOR ="BLUE">대상</FONT>
		{%/if%}
	</td>
	<td align="center">
		{%if LOGI_CFM_FG == null %}
			<FONT COLOR ="BLUE">
				심사중
			</FONT>
		{%/if%}
		{%if LOGI_CFM_FG == '01' %}
			<FONT COLOR ="green">
				등록불가<br> 물류바코드<br>담당자에게문의<br>상품/판매코드 비정상
			</FONT>
		{%/if%}
		{%if LOGI_CFM_FG == '02' %}
			<FONT COLOR ="BLUE">심사중<br>등록요청</FONT>								
		{%/if%}
		{%if LOGI_CFM_FG == '03' %}			
			<FONT COLOR ="red">수정요청<br>체적/중량오류</FONT>			
		{%/if%}
		{%if LOGI_CFM_FG == '04' %}
			<FONT COLOR ="green">
				등록불가<br> 물류바코드<br>
				담당자에게문의<br>기등록코드
			</FONT>	
		{%/if%}
		{%if LOGI_CFM_FG == '05' %}
			<FONT COLOR ="red">수정요청<br>물류코드오류</FONT>
		{%/if%}
		{%if LOGI_CFM_FG == '09' %}
			<FONT COLOR ="green">확정<br>등록완료</FONT>								
		{%/if%}
	</td>
</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input id="staticTableBodyValue" type="hidden" name="staticTableBodyValue">
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
										<input type="text" name="venCd" id="venCd" readonly="readonly" value="<c:out value="${searchParam.venCd}" />" style="width:40%;"/>
										</c:if>
										<c:if test="${empty searchParam.venCd}">
											<input type="text" name="venCd" id="venCd" readonly="readonly" value="<c:out value="${epcLoginVO.repVendorId}" />" style="width:40%;"/>
										</c:if>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
													<c:if test="${not empty searchParam.venCd}">
														<html:codeTag objId="venCd" objName="venCd" width="150px;" selectParam="<c:out value='${searchParam.venCd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
													</c:if>
													<c:if test="${ empty searchParam.venCd}">
														<html:codeTag objId="venCd" objName="venCd" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
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
								<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${searchParam.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
								~
								<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${searchParam.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
							</td>							
							<th >판매(88)코드</th>
							<td>
									<input type="text"  name="srcmk_cd"  value="<c:out value="${searchParam.srcmk_cd}" />" style="width:140px;" />
							</td>
						</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<%-- <div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">신규상품 등록현황</li>
						<li class="btn"><a href="#" class="btn" onclick="helpWin(true);"><span><spring:message code="button.common.help"/></span></a></li>
					</ul>
					<table id="headerTable" class="bbs_list" cellpadding="0" cellspacing="0" border="0"	width=1800px;	>
					<colgroup>
						<col width="30px"/>
						<col width="70px"/>
						<col width="90px"/>
						<col width="*"/>
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
					
						<!-- Data List Body Start ------------------------------------------------------------------------------>
						<tbody id="dataListbody" />
						<!-- Data List Body End   ------------------------------------------------------------------------------>
					
				</div>
			</div> --%>
			<div class="wrap_con">
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">검색내역 </li>						
						<li class="btn"><a href="#" class="btn" onclick="helpWin(true);"><span><spring:message code="button.common.help"/></span></a></li>
					</ul>
			
					<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
						<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1500px; bgcolor=efefef>
							<colgroup>
								<col style="width:30px"/>
								<col style="width:100px"/>
								<col style="width:120px"/>
								<col style="width:300px;"/>
								<col style="width:120px"/>
								<col style="width:120px"/>
								<col style="width:100px"/>
								<col style="width:100px"/>
								<col style="width:100px;"/>
								<col style="width:100px"/>
								
							</colgroup>
																
							<tr bgcolor="#e4e4e4">
								<th rowspan="2">No.</th>
								<th rowspan="2">등록일자</th>
								<th rowspan="2">판매코드</th>
								<th rowspan="2">상품명</th>
								<th rowspan="2">물류바코드</th>
								<th colspan="4">박스</th>
								<th rowspan="2">소터구분</th>
								<th rowspan="2">사용여부</th>
								<th rowspan="2">VIC대상</th>									
								<th rowspan="2">물류바코드 상태</th>
							</tr>
							<tr bgcolor="#e4e4e4">
								<th>가로(mm)</th>
								<th>세로(mm)</th>
								<th>높이(mm)</th>
								<th>중량(kg)</th>
							</tr>
																
							<tbody id="dataListbody" />									
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
