<%--
	Page Name 	: NEDMPRO0120.jsp
	Description : 점포별상품등록 현황
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
		
		if ($("#srchSellCode").val().trim() == "") {
			alert('판매코드를 넣어 주세요.');
			$("#srchSellCode").focus();
			return false;
		}
		
		var vendorTypeCd = '<c:out value="${epcLoginVO.vendorTypeCd}" />';
		if (vendorTypeCd == "06") {
			if($("#srchEntpCode").val() == ""){
				alert('업체선택은 필수입니다.');
				$("#srchEntpCode").focus();
				return false;
			}
		}
		
		return true;
	}
	
	/* 조회 */
	function doSearch() {
		goPage('1');
	}
	
	/* 페이징 처리를 위한 조회 함수 */
	function goPage(pageIndex) {
		if (!validate()) {
			return;
		}
		
		var searchInfo = {};
		
		searchInfo["srchEntpCode"] 	= $("#srchEntpCode").val();
		searchInfo["srchSellCode"] 	= $("#srchSellCode").val();
		searchInfo["pageIndex"] 	= pageIndex;
		searchInfo["downloadFlag"] 	= "";
		//console.log(searchInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/buy/NEDMPRO0120Select.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				$("#pageIndex").val(pageIndex);
				
				//json 으로 호출된 결과값을 화면에 Setting
				_setTbodyMasterValue(data);
			}
		});	
	}
	
	/* Data 후처리 */
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
		searchInfo["downloadFlag"] 	= "Y";
		
		//console.log(searchInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NEDMPRO0120ExcelCount.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				if (data.totCnt > 0) {
					if ($("#pageIndex").val() == "") {
						$("#pageIndex").val(1);
					}
					
					$("#downloadFlag").val("Y");
					
					$("#searchForm").attr("target", "frameForDownload");
					$("#searchForm").attr("method", "POST");
					$("#searchForm").attr("action", '<c:url value="/edi/product/NEDMPRO0120Excel.do"/>');
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
		<td align="center"><c:out value="\${snum}" /></td>
		<td align="left">&nbsp;<c:out value="\${strCd}" /></td>
		<td align="left">&nbsp;<c:out value="\${strNm}" /></td>
		<td align="left">&nbsp;<c:out value="\${srcmkCd}" /></td>
		<td align="center"><c:out value="\${prodCd}" /></td>
		<td><c:out value="\${prodNm}" /></td>
		<td align="center"><c:out value="\${codeStaus}" /></td>
		<!-- <td align="center"><c:out value="\${venCd}" /></td> -->
		<td align="center"><c:out value="\${regDt}" /></td>
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
							<li class="tit">점포상품현황</li>
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
								<th><span class="star">*</span>판매(88)코드</th>
								<td><input type="text" id="srchSellCode" name="srchSellCode" style="width:120px;" value="${param.srchSellCode}" /></td>
							</tr>
						</table>
						<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
							<tr>
								<td colspan="6" bgcolor=ffffff>
									&nbsp;※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.
									<br>
									<font color="red">
									&nbsp;※물류바코드 등록현황에서 등록대상일 경우 "상품"-&gt;"신규상품관리"-&gt;"물류바코드 등록"으로 들어가셔서 등록해 주세요.
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
							<table  cellpadding="1" cellspacing="1" border="0" width=816 bgcolor=efefef>
								<colgroup>
									<col width="20px"/>				
									<col width="50px"/>
									<col width="80px"/>
									<col width="80px"/>
									<col width="80px"/>
									<col width="260px"/>
									<col width="80px"/>		
									<col width="100px"/>								
								</colgroup>
								<tr bgcolor="#e4e4e4">
									<th>No.</th>	
									<th>점포코드</th>
									<th>점포명</th>					
									<th>판매(88)코드</th>
									<th>상품코드</th>
									<th>상품명</th>
									<th>코드상태</th>
									<!-- <th rowspan=1>업체코드</th> -->		
									<th rowspan=1>등록일</th>
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
