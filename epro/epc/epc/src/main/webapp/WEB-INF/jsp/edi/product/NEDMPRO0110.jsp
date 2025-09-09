<%--
	Page Name 	: NEDMPRO0110.jsp
	Description : 상품등록현황
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2015.11.30 		an tae kyung	 		최초생성
	
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
	function validate() {
		if ($("#srchEntpCode").val() == "") {
			alert('업체선택은 필수입니다.');
			$("#srchEntpCode").focus();
			return false;
		}
		
		return true;
	}
	
	/* 조회 */
	function doSearch() {
		goPage('1');
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(pageIndex) {
		if (!validate()) {
			return;
		}
		
		var searchInfo = {};
		
		searchInfo["srchEntpCode"] 	= $("#srchEntpCode").val();		// 협력업체
		searchInfo["srchSellCode"] 	= $("#srchSellCode").val();		// 판매코드
		searchInfo["srchLogiBcdFg"] = $("#srchLogiBcdFg").val();	// 상태
		searchInfo["pageIndex"] 	= pageIndex;					// page
		searchInfo["downloadFlag"] 	= "";
		//console.log(searchInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NEDMPRO0110Select.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				$("#pageIndex").val(pageIndex);
				
				//footer Setting 
				if(data.resultList.length > 0){
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
	
	/* Excel */
	function doExcel() {
		if (!validate()) {
			return;
		}
		
		var searchInfo = {};
		
		searchInfo["srchEntpCode"] 	= $("#srchEntpCode").val();
		searchInfo["srchSellCode"] 	= $("#srchSellCode").val();
		searchInfo["srchLogiBcdFg"] = $("#srchLogiBcdFg").val();
		searchInfo["downloadFlag"] = "Y";
		//console.log(searchInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NEDMPRO0110ExcelCount.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				if (data.totCnt > 0) {
					if ($("#pageIndex").val() == "") {
						$("#pageIndex").val(1);
					}
					
					$("#downloadFlag").val("Y");
					
					$("#searchForm").attr("target", "frameForDownload");
					$("#searchForm").attr("method", "POST");
					$("#searchForm").attr("action", '<c:url value="/edi/product/NEDMPRO0110Excel.do"/>');
					$("#searchForm").submit();
					
					$("#downloadFlag").val("");
				} else {
					alert("해당 자료가 존재하지 않습니다.");
				}
			}
		});
	}
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1"  bgcolor=ffffff>
		<td align="center"><c:out value="\${rnum}" /></td>
		<td align="center"><c:out value="\${regDt}" /></td>
		<td align="left">&nbsp;<c:out value="\${srcmkCd}" /></td>
		<td align="center"><c:out value="\${prodCd}" /></td>
		<td><c:out value="\${prodNm}" /></td>
		<td align="center">
			{%if useFg == "1"%}
				<input type="checkbox" name="chkbox1"  value="0" checked align="absmiddle" class=inpcheck disabled>
			{%else%}
				<input type="checkbox" name="chkbox1"  value="0" align="absmiddle" class=inpcheck disabled>
			{%/if%}
		</td>
		<td align="center"><c:out value="\${logiBcd}" /></td>
		<td align="right">
			<c:out value="\${ordIpsu}" />&nbsp;
			<!--setComma(<c:out value="\${ordIpsu}" />);-->
		</td>
		<td align="right"><c:out value="\${width}" />&nbsp;</td>
		<td align="right">&nbsp;<c:out value="\${length}" />&nbsp;</td>
		<td align="right"><c:out value="\${height}" />&nbsp;</td>
		<td align="right"><c:out value="\${wg}" />&nbsp;</td>
		<td align="center">
			{%if sorterFg == "0"%}
				<font color ="red">논소터</font>
			{%elif sorterFg == "1"%}
				<FONT COLOR ="BLUE">소터</FONT>
			{%/if%}
		</td>
		<td align="center">
			<c:out value="\${barDesc}" />
		</td>
		<td align="center">
			<c:out value="\${mixProdFg}" />
		</td>
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
							<li class="tit">전체상품현황</li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
								<a href="#" class="btn" onclick="doExcel();"><span><spring:message code="button.common.excel"/></span></a>
							</li>
						</ul>
						
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
							<input type="hidden" id="productVal" name="productVal" />
							
							<colgroup>
								<col style="width:10%;" />
								<col style="width:20%;" />
								<col style="width:15%;;" />
								<col style="width:20%;" />
								<col style="width:10%;" />
								<col  />
							</colgroup>
							<tr>
								 
								<th><span class="star">*</span> 협력업체</th>
								<td>
									<c:choose>
										<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
											<c:if test="${not empty param.srchEntpCode}">
												<input type="text" name="srchEntpCode" id="srchEntpCode" readonly="readonly" value="${param.srchEntpCode}" style="width:40%;"/>
											</c:if>
											<c:if test="${empty param.srchEntpCode}">
												<input type="text" name="srchEntpCode" id="srchEntpCode" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
											</c:if>
											
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:when>
										
										<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
											<c:if test="${not empty param.srchEntpCode}">
												<html:codeTag objId="srchEntpCode" objName="srchEntpCode" width="150px;" selectParam="<c:out value='${param.srchEntpCode}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
											</c:if>
											<c:if test="${ empty param.srchEntpCode}">
												<html:codeTag objId="srchEntpCode" objName="srchEntpCode" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
											</c:if>
										</c:when>
									</c:choose>
								</td>
								<th>판매(88)코드</th>
								<td><input type="text" id="srchSellCode" name="srchSellCode" style="width:120px;" value="${param.srchSellCode}" /></td>
								<th><span class="star"></span>상태</th>
								<td>
									<select name="srchLogiBcdFg" id="srchLogiBcdFg">
										<option value="70" <c:if test="${param.srchLogiBcdFg eq '70' }">selected</c:if>> 전체</option>
										<option value="10" <c:if test="${param.srchLogiBcdFg eq '10' }">selected</c:if>> 정상완료</option>
										<option value="20" <c:if test="${param.srchLogiBcdFg eq '20' }">selected</c:if>> 물류바코드등록대상</option>
									</select>
								</td>
							</tr>
						</table>
						<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
							<tr>
								<td colspan="6" bgcolor=ffffff>
									&nbsp;※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.
									<br>
									<font color="red">
									&nbsp;※물류바코드 등록현황에서 등록대상일 경우 "상품"-&gt;"물류바코드관리"-&gt;"물류바코드 등록"으로 들어가셔서 등록해 주세요.
									</font>
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
									<col width="35px"/>
									<col width="80px"/>
									<col width="100px"/>
									<col width="100px"/>
									<col />
									<col width="50px"/>
									<col width="100px"/>
									<col width="50px"/>
									<col width="60px" />
									<col width="100px"   />
			 
									<col width="60px"  />
									<col width="60px" />
									<col width="80px"/>
									<col width="100px"/>
									<col width="80px"/>
								</colgroup>
								<tr bgcolor="#e4e4e4">
									<th rowspan=2>No.</th>
									<th rowspan=2>등록일자</th>
									<th rowspan=2>판매(88)코드</th>
									<th rowspan=2>상품코드</th>
									<th rowspan=2>상품명</th>
									<th rowspan=2>사용구분</th>
									<th rowspan=2>물류바코드</th>
									<th rowspan=2>입수</th>
									<th colspan=4>박스체적</th>
									<th rowspan=2>소터구분</th>
									<th rowspan=2>물류바코드<br>등록현황</th>
									<th rowspan=2>혼재구분</th>
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
			
			<!-- Pagging Start ---------->			
			<div id="paging_div">
		        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
			</div>
			<!-- Pagging End ---------->
			
		</form>
		
		<iframe name="frameForDownload" style="width:0%;height:0%;"></iframe>
	
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li>홈</li>
						<li>상품</li>
						<li>상품현황관리</li>
						<li class="last">상품등록 현황</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	
</body>
</html>
