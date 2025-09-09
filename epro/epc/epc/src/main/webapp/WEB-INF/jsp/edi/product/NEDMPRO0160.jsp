<%--
	Page Name 	: NEDMPRO0160.jsp
	Description : 운영중단상품
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2019.10.22 		          	 		최초생성
	
--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>

		<script>

			/* 조회 */
			function doSearch() {
				goPage('1');
			}
	
			/** ********************************************************
	 		* 페이징 처리 함수
	 		******************************************************** */
			function goPage(pageIndex) {
				/* 
					if (!validate()) {
						return;
					} 
				*/
		
				var searchInfo = {};
			
				searchInfo["srchEntpCd"] 	= $("#srchEntpCd").val();		// 협력업체
				searchInfo["srchSrcmkCd"] 	= $("#srchSrcmkCd").val();		// 판매코드
				searchInfo["pageIndex"] 	= pageIndex;					// page
				searchInfo["downloadFlag"] 	= "";
				//console.log(searchInfo);
				//return;
			
				$.ajax({
					contentType : 'application/json; charset=utf-8',
					type : 'post',
					dataType : 'json',
					async : false,
					url : '<c:url value="/edi/product/NEDMPRO0160Select.json"/>',
					data : JSON.stringify(searchInfo),
					success : function(data) {
						$("#pageIndex").val(pageIndex);
						
						//json 으로 호출된 결과값을 화면에 Setting
						_setTbodyMasterValue(data);
					}
				});	
			}
	
			function _setTbodyMasterValue(json) {
				setTbodyInit("dataListbody");	// dataList 초기화
				
				var data = json.resultList;
					
				if (data.length > 0) {
					$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
					$("#paging-ul").html(json.contents);
				} else {
					setTbodyNoResult("dataListbody", 20);
					$("#paging-ul").html("");
				}
			}	
		</script>

		<script id="dataListTemplate" type="text/x-jquery-tmpl">
			<tr class="r1"  bgcolor=ffffff>
				<td align="center"><c:out value="\${rnum}" /></td>		<%-- No. --%>
				<td align="center"><c:out value="\${venCd}" /></td>		<%-- 파트너사코드 --%>
				<td align="center"><c:out value="\${venNm}" /></td>		<%-- 파트너사명 --%>
				<td align="center"><c:out value="\${trdTypNm}" /></td>	<%-- 거래형태  --%>
				<td align="center"><c:out value="\${l1Nm}" /></td>		<%-- 대분류명 --%>
				<td align="center"><c:out value="\${l2Nm}" /></td>		<%-- 중분류명 --%>
				<td align="center"><c:out value="\${l3Nm}" /></td>		<%-- 소분류명 --%>
				<td align="center"><c:out value="\${prodCd}" /></td>	<%-- 상품코드 --%>
				<td align="center"><c:out value="\${srcmkCd}" /></td>	<%-- 판매코드 --%>
				<td align="center"><c:out value="\${prodNm}" /></td>	<%-- 상품명  --%>
				<td align="center"><c:out value="\${strFg}" /></td>	<%-- 점포구분  --%>
			</tr>
		</script>
	
	</head>
	
	<body>
		<div id="content_wrap">
			<div>
				<!--	@ BODY WRAP START 	-->
				<form id="searchForm" name="searchForm" method="post" action="#">
				<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />
				<input type="hidden" id="downloadFlag" name="downloadFlag" value="" />
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							
							<ul class="tit">
								<li class="tit">운영중단상품</li>
								<li class="btn">
									<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
									<%-- <a href="#" class="btn" onclick="doExcel();"><span><spring:message code="button.common.excel"/></span></a> --%>
								</li>
							</ul>
							
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:10%;" />
									<col style="width:40%;" />
									<col style="width:10%;;" />
									<col style="width:40%;" />
								</colgroup>
								<tr>
								 	<th><span class="star">*</span> 년도</th>
									<td>
										<html:codeTag objId="srchSrcmkCd" objName="srchSrcmkCd" selectParam="<c:out value='${paramMap.startDate_year}'/>" dataType="YEAR" subCode="5" comType="SELECT" formName="form"  />
										
									</td>
									<th><span class="star">*</span> 협력업체</th>
									<td>
										<c:choose>
											<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
												<c:if test="${not empty param.srchEntpCd}">
													<input type="text" name="srchEntpCd" id="srchEntpCd" readonly="readonly" value="${param.srchEntpCd}" style="width:40%;"/>
												</c:if>
												<c:if test="${empty param.srchEntpCd}">
													<input type="text" name="srchEntpCd" id="srchEntpCd" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
												</c:if>
												
												<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
											</c:when>
											
											<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
												<c:if test="${not empty param.srchEntpCd}">
													<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="150px;" selectParam="<c:out value='${param.srchEntpCd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
												</c:if>
												<c:if test="${ empty param.srchEntpCd}">
													<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
												</c:if>
											</c:when>
										</c:choose>
									</td>
								</tr>
							</table>
							<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
								<tr>
									<td colspan="4" bgcolor=ffffff>
										&nbsp;- 상/하반기 평가 D등급 이하인 파트너사 상품 中 운영중단이 결정된 상품입니다. 旣 발주/판매 중단된 상품이 포함되어 있을 수 있습니다.!!
										<br>&nbsp;- 파트너사별 평가 상세 내용은 'partner.lottemart.com/epc → SCM → SRM(상단) → 성과평가(좌측) → 성과평가 결과(조회)' 에서 확인 가능합니다.
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
							<div style="width:100%; height:446px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table  cellpadding="1" cellspacing="1" border="0" width=1330 bgcolor=efefef>
									<colgroup>
										<col width="35px"/>		<%-- No. --%>
										<col width="60px"/>		<%-- 파트너사코드 --%>
										<col width="120px"/>	<%-- 파트너사명 --%>
										<col width="80px" />	<%-- 거래형태 --%>
										<col width="90px" />	<%-- 대분류명 --%>
										<col width="90px"/>		<%-- 중분류명 --%>
										<col width="90px"/>		<%-- 소분류명 --%>
										<col width="90px"/>		<%-- 상품코드 --%>
										<col width="90px"/>		<%-- 판매코드 --%>
										<col width="150px" /> 	<%-- 상품명 --%>
										<col width="60px" />	<%-- 점포구분 --%>
									</colgroup>
									
									<tr bgcolor="#e4e4e4">
										<th >No.</th>
										<th >파트너사<br>코드</th>
										<th >파트너사명</th>
										<th >거래형태</th>
										<th >대분류명</th>
										<th >중분류명</th>
										<th >소분류명</th>
										<th >상품코드</th>
										<th >판매코드</th>
										<th >상품명</th>
										<th >점포구분</th>
									</tr>							
									<tbody id="dataListbody" />
								</table>
							</div>
						</div>
					</div>
				</div>
				
				<!-- Pagging Start ---------->			
				<div id="paging_div">
			        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
				</div>
				<!-- Pagging End ---------->
				
			</form>
			
			<!-- footer -->
			<div id="footer">
				<div id="footbox">
					<div class="msg" id="resultMsg"></div>
					<div class="notice"></div>
					<div class="location">
						<ul>
							<li>홈</li>
							<li>SRM 정보</li>
							<li>성과평가</li>
							<li class="last">운영중단상품</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- footer //-->
		</div>
		
	</body>
</html>
