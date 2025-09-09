<%-- 
- Author(s): 
- Created Date: 2011. 10. 20
- Version : 1.0
- Description : 상품등록현황

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

	$(document).ready(function() {		
		
		//대분류 콤보박스 조회 구성
		_eventSelectL1ListProductBatch();
		
		// 조회조건 입력필드 enter key이벤트
		$('#srchSellCode').unbind().keydown(function(e) {
			switch (e.which) {
			case 13:
				doSearch(this);
				break; // enter
			default:
				return true;
			}
			e.preventDefault();
		});
		
		//판매코드 정렬
		$("#spSrcmkCd").click(function (){
			var srcmkOrder	=	"";
			if ($(this).text() == "▲") {
				$(this).text("▼");
				srcmkOrder = "srcmkCdAsc";
			} else {
				$(this).text("▲");
				srcmkOrder = "srcmkCdDesc";
			}
			$("input[name='orderGbn']").val(srcmkOrder);
			goPage("1");
		});
		
		//상품코드 정렬
		$("#spProdCd").click(function (){
			var prodCdOrder	=	"";
			if ($(this).text() == "▲") {
				$(this).text("▼");
				prodCdOrder = "prodCdAsc";
			} else {
				$(this).text("▲");
				prodCdOrder = "prodCdDesc";
			}
			$("input[name='orderGbn']").val(prodCdOrder);
			goPage("1");
		});
		
		//상품명 정렬
		$("#spProdNm").click(function (){
			var prodNmOrder	=	"";
			if ($(this).text() == "▲") {
				$(this).text("▼");
				prodNmOrder = "prodNmAsc";
			} else {
				$(this).text("▲");
				prodNmOrder = "prodNmDesc";
			}
			$("input[name='orderGbn']").val(prodNmOrder);
			goPage("1");
		});
		
		//var srchCompleteGbn	=	"<c:out value='${param.srchCompleteGbn}'/>";
		var srchProdGbn		=	"<c:out value='${param.srchProdGbn}'/>";
		var pageIdx			=	"<c:out value='${pageIdx}'/>";
		var recordCnt		=	"<c:out value='${param.recordCnt}'/>";
		var l1Cd			=	"<c:out value='${param.l1Cd}'/>";
		var l4Cd			=	"<c:out value='${param.l4Cd}'/>";
		
		/* if (srchCompleteGbn.replace(/\s/gi, '') != "") {
			$("select[name='srchCompleteGbn']").val(srchCompleteGbn);
		} */
		
		if (srchProdGbn.replace(/\s/gi, '') != "") {
			$("select[name='srchProdGbn']").val(srchProdGbn);
		}
		
		if (recordCnt != "") {
			$("select[name='recordCnt']").val(recordCnt);
		}
		
		if (l1Cd.replace(/\s/gi, '') != "") {
			
			//대분류 콤보박스 호출 함수
			//_eventSelectL1ListProductBatch();			
			$("select[name='l1Cd']").val(l1Cd);
			
			// 세분류 콤보박스 호출
			_eventClearTeamCd("L1CD", l1Cd);					
			$("select[name='l4Cd']").val(l4Cd);
						
			// 그룹소분류 콤보박스 호출
			
			//data list 초기화
			$("#dataListbody tr").remove();		
			
			goPage(pageIdx);
		} 
		
		/* if (pageIdx.replace(/\s/gi, '') != "" && pageIdx != "0") {
			goPage(pageIdx);
		} */
		
		
		//대분류 체인지 이벤트
		$("#l1GroupCode").change(function (){
			
			//datalist 초기화
			$("#dataListbody tr").remove();
			
			
			//세분류 조회
			_eventClearTeamCd("L1CD", $(this).val());						
		});
		
		
		$("#entp_cd").change(function () {
			
			//datalist 초기화
			$("#dataListbody tr").remove();
			
			//대분류 콤보박스 초기화
			$("#l1GroupCode option").not("[value='']").remove();
			
			//세분류 콤보박스 초기화
			$("#l4GroupCode option").not("[value='']").remove();
			
			
			if ($(this).val().replace(/\s/gi, '') != "") {
				//대분류 콤보박스 조회 구성
				_eventSelectL1ListProductBatch();	
			}						
		});
				
	});
	
	/* 대분류 콤보박스 구성 */
	function _eventSelectL1ListProductBatch() {
		
		//대분류 콤보박스 초기화
		$("#l1GroupCode option").not("[value='']").remove();
		
		var paramInfo	=	{};

		paramInfo["entpCode"]	=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');
		
		//console.log(paramInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectWholeProductAttrBatchTeamCombo.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				
				var resultList	=	data.resultList;
				var options		=	"";
				if (resultList.length > 0) {
					
					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {		
						eleHtml[i] = "<option value='"+resultList[i].l1Cd+"'>"+resultList[i].l1Nm+"</option>"+"\n";				
					}
														
					$("#l1GroupCode").append(eleHtml.join(''));						
				}				
			    				
			}
		});
	}
	
	/* _eventClearTeamCd */
	function _eventClearTeamCd(gubun, val) {
		
		//세분류 콤보박스 초기화
		$("#l4GroupCode option").not("[value='']").remove();
			
		// 세분류 콤보박스 호출
		_eventSelectL4ListProductBatch(val);										
	}
	
	/* 세분류 콤보구성 박스 */
	function _eventSelectL4ListProductBatch(val) {
		var paramInfo	=	{};

		paramInfo["entpCode"]	=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');	
		paramInfo["l1Cd"]		=	val;		
		
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProductBatchL4CdList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				
				var resultList	=	data.resultList;
				var options		=	"";
				if (resultList.length > 0) {
					
					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {		
						eleHtml[i] = "<option value='"+resultList[i].l4Cd+"'>"+resultList[i].l4Nm+"</option>"+"\n";				
					}
														
					$("#l4GroupCode").append(eleHtml.join(''));						
				}				
			    				
			}
		});
	}
	

	function PopupWindow(pageName) {
		var cw=400;
		var ch=300;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	/* 조회 */
	function doSearch() {						
		goPage("1");				
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(pageIndex){
		var paramInfo	=	{};
		var venderTypeCd	=	"<c:out value='${epcLoginVO.vendorTypeCd}'/>";
		
		/* if (venderTypeCd.replace(/\s/gi, '') == "06") {
			alert("업체 선택은 필수 입니다.");
			$("#entp_cd").focus();
			return;
		} */
		
		
		if ($("#searchForm #entp_cd").val().replace(/\s/gi, '') == "") {
			alert("업체선택은 필수 입니다.");
			$("#searchForm #entp_cd").focus();
			return;
		}
		
		var l1Cd 		= $("select[name='l1Cd']").val().replace(/\s/gi, '');
		var l4Cd 		= $("select[name='l4Cd']").val().replace(/\s/gi, '');
		
		if ( l1Cd == "" || l4Cd == "") {
			alert("필수 검색조건을 입력하지 않았습니다.");
			return;
		}
		
		
		paramInfo["entpCode"]			=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');			//협력업체코드		
		paramInfo["pageIndex"]			=	pageIndex;														//페이지 번호
		paramInfo["srchSellCode"]		=	$("#searchForm #srchSellCode").val().replace(/\s/gi, '');		//판매코드		
		paramInfo["recordCnt"]			=	$("select[name='recordCnt']").val();							//한페이지에 보여질 게시물 건수
		//paramInfo["srchCompleteGbn"]	=	$("select[name='srchCompleteGbn']").val().replace(/\s/gi, '');	//확정구분 제외 2016.03.07
		paramInfo["srchProdGbn"]		=	$("select[name='srchProdGbn']").val().replace(/\s/gi, '');		//상품구분[1:Hyper, 2:Vic]
		paramInfo["sapL3Cd"]			=	l4Cd;															//화면 상단의 대분류와 소분류가 SAP 으로 변경된 코드체계로 가져오므로 추가
		
		//정렬기준 default는 판매코드 최종변경일 최신순
		if ($("input[name='orderGbn']").val().replace(/\s/gi, '') == "") {
			paramInfo["orderGbn"]	=	"prodCdAsc";
		} else {
			paramInfo["orderGbn"]	=	$("input[name='orderGbn']").val().replace(/\s/gi, '');	
		}
		
		//console.log(paramInfo);
				
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/PEDMPRO0099.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				_setTbodyMasterValue(data);
			}
		});
	}
	
	/* 조회 후처리(data 마스터코드 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data 		=	json.resultList;
		
		$("input[name='pageIdx']").val(json.pageIdx);
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {									
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");		
			$("#paging-ul").html(json.contents);
		} else {		
			setTbodyNoResult("dataListbody",5);			
			$("#paging-ul").html("");
		}
	}	
	
	
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#entp_cd").val(strVendorId);
	}
	
	
	/* 상품 수정 페이지 이동 */
	function goDetailView (regDate, sellCode, newProductCode, l4Cd, productName, venCd, sapL3Cd, grpCd) {
		var form	=	document.hiddenForm;
				
		form.regDate.value = regDate;
		form.sellCode.value = sellCode;
		form.newProductCode.value = newProductCode;
		form.srchL4Cd.value = l4Cd;
		form.productName.value = productName;
		form.sapL3Cd.value = sapL3Cd;
		form.grpCd.value = grpCd;
		
		if ($("#searchForm #entp_cd").val().replace(/\s/gi, '') == "") {
			form.entpCode.value = "all";
		} else {
			form.entpCode.value = $("#searchForm #entp_cd").val().replace(/\s/gi, '');
		}
		
		//form.entpCode.value = $("#searchForm #entp_cd").val().replace(/\s/gi, '');
		form.pageGbn.value = "NOT_BATCH";
		form.pageIdx.value = $("input[name='pageIdx']").val();
		form.srchSellCode.value = $("#searchForm #srchSellCode").val().replace(/\s/gi, '');
		form.venCd.value = venCd;
		//form.srchCompleteGbn.value = $("select[name='srchCompleteGbn']").val().replace(/\s/gi, '');	2015.03.07 이후 제외
		form.srchProdGbn.value = $("select[name='srchProdGbn']").val().replace(/\s/gi, '');	//상품구분[1:Hyper, 2:Vic]
		form.recordCnt.value = $("select[name='recordCnt']").val();						//한페이지에 보여질 게시물 건수
		
		
		form.l1Cd.value = $("select[name='l1Cd']").val().replace(/\s/gi, '');		
		form.l4Cd.value = $("select[name='l4Cd']").val().replace(/\s/gi, '');
		
		
		form.action = "<c:url value='/edi/product/updateWholeProduct.do'/>";
		form.submit();
	}
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">		
	<tr class="r1"  bgcolor=ffffff>				
		<td align="center"><c:out value="\${hvFg}"/></td>
		<td align="center"><c:out value='\${newProductCode}'/></td>		
		<td align="center">&nbsp;<c:out value='\${sellCode}'/></td>		
		<td><a href="#" onclick="goDetailView('<c:out value="\${regDate}"/>',	'<c:out value="\${sellCode}"/>',	'<c:out value="\${newProductCode}"/>', 		'<c:out value="\${l4Cd}"/>', 	'<c:out value="\${productName}"/>',		'<c:out value="\${venCd}"/>', '<c:out value="\${sapL3Cd}"/>',	'<c:out value="\${grpCd}"/>');" 	id="goDetailLnk"	name="goDetailLnk"><c:out value='\${productName}'/></a></td>
		<td align="center">
			<font color="blue"><strong><c:out value='\${inputCnt}'/></strong></font> /
			<font color="red"><strong><c:out value='\${x00816Cnt}'/></strong></font> /
  			<strong><c:out value="\${totCnt}" /></strong> 
		</td>
		<td align="left">			
			<span class='ellipsis' title='<c:out value="\${attrValTxt}" />'><c:out value="\${attrValTxt}" />
		</td>	
	</tr>
</script>

</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" id="searchForm" method="post" action="#">
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">전체상품현황</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
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
							<col style="width:20%;" />								
						</colgroup>
						<tr>
							 
							<th><span class="star">*</span> 협력업체</th>
							<td>
								<%-- <html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entpCode}" dataType="CP" comType="SELECT" formName="form" defName="전체"  /> --%>
								<c:choose>
									
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<c:if test="${not empty param.entpCode}">
											<c:if test="${param.entpCode ne 'all'}">
												<html:codeTag objId="entp_cd" objName="entp_cd" width="80px;" selectParam="${param.entpCode}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />	
											</c:if>
											
											<c:if test="${param.entpCode eq 'all'}">
												<html:codeTag objId="entp_cd" objName="entp_cd" width="80px;"  dataType="CP" comType="SELECT" formName="form" defName="전체"  />	
											</c:if>
											
										</c:if>
										<c:if test="${ empty param.entpCode}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="80px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
									</c:when>
								</c:choose>
							</td>
							
							
							<th><span class="star">*</span> 대분류</th>
							<td >
								<select id="l1GroupCode" name="l1Cd" class="required" style="width:150px;">
									<option value="">선택</option>									
								</select>
							</td>
							<th><span class="star">*</span> 소분류</th>
							<td>
								<select id="l4GroupCode" name="l4Cd">
									<option value="">선택</option>
								</select>
							</td>
						</tr>
						</tr>	
							<th>판매(88)코드</th>
							<td >
								<input type="text"  name="srchSellCode"		id="srchSellCode" 	style="width:120px;" value="${param.srchSellCode}" />
							</td>
							<!-- 2016.03.07 이후 부터 확정여부 제외 by song min kyo 현업 요청 -->
							<!-- <th>확정여부</th>
							<td>
								<select id="srchCompleteGbn" name="srchCompleteGbn">
									<option value="">전체</option>
									<option value="1">확정</option>
									<option value="0">미확정</option>
								</select>
							</td>	 -->
							
							<!-- 상품 구분 추가 2016.03.07 by song min kyo -->
							<th>상품구분</th>
							<td>
								<select id="srchProdGbn" name="srchProdGbn">
									<option value="">전체</option>
									<option value="1">Hyper</option>
									<option value="2">Vic</option>
								</select>
							</td>
							
							<th>게시물 건수</th>
							<td>
								<select id="recordCnt" name="recordCnt">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
								</select>
							</td>						
							
						</tr>
					</table>
					<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
						<tr>
							<td colspan="6" bgcolor=ffffff>
								<strong>&nbsp;<font color="red">※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.</font></strong><br/>
								<strong>&nbsp;<font color="red">※해당상품들의 분석속성이 없을경우 해당상품들은 조회되지 않습니다.</font></strong><br/>
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
						<col style="width:80px"/>		
						<col style="width:100px"/>
						<col style="width:100px"/>
						<col style="width:200px"/>
						<col style="width:200px"/>
						<col  />						
					</colgroup>
					<tr bgcolor="#e4e4e4">
						<!-- <th>확정여부</th> -->
						<th>상품구분</th>
						<th id="thProdCd">상품코드			<span id="spProdCd"		style="cursor:pointer">▲</span></th>							
						<th id="thSrcmkCd">판매(88)코드	<span id="spSrcmkCd"	style="cursor:pointer">▲</span></th>						
						<th id="thProdNm">상품명			<span id="spProdNm"		style="cursor:pointer">▲</span></th>			
						<th>입력속성 / 해당없음 / 총 갯수</th>
						<th>속성 값</th>												
					</tr>
					<tbody id="dataListbody" />
					

					</table>
					</div>
					
					<!-- Pagging Start ---------->			
					<div id="paging_div">
				        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
					</div>
					<!-- Pagging End ---------->		
				</div>
			</div>
		</div>
		</form>
		<iframe name="frameForDownload" style="width:0%;height:0%;"></iframe> 
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
					<li>상품현황관리</li>
					<li class="last">기존상품수정</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
	
	<form	name="hiddenForm"	name="hiddenForm"		method="post">
		<input type="hidden"	name="regDate"				id="regDate"		/>
		<input type="hidden"	name="sellCode"				id="sellCode"		/>
		<input type="hidden"	name="newProductCode"		id="newProductCode"	/>
		<input type="hidden"	name="srchL4Cd"				id="srchL4Cd"		/>
		<input type="hidden"	name="productName"			id="productName"	/>
		<input type="hidden"	name="entpCode"				id="entpCode"		/>
		<input type="hidden"	name="sapL3Cd"				id="sapL3Cd"		/>
		<input type="hidden"	name="pageGbn"				id="pageGbn"		/>
		<input type="hidden"	name="pageIdx"				id="pageIdx"		value="${pageIdx}"/>
		<input type="hidden"	name="srchSellCode"			id="srchSellCode"	/>
		<input type="hidden"	name="venCd"				id="venCd"			/>
		<input type="hidden"	name="orderGbn"				id="orderGbn"		/>
		<input type="hidden"	name="srchCompleteGbn"		id="srchCompleteGbn"	/>
		<input type="hidden"	name="srchProdGbn"			id="srchProdGbn"		/>
		<input type="hidden"	name="recordCnt"			id="recordCnt"			/>
		<input type="hidden"	name="grpCd"				id="grpCd"				/>
		
		<input type="hidden"	name="l1Cd"					id="l1Cd"			/>
		<input type="hidden"	name="l4Cd"					id="l4Cd"			/>
	</form>
	
</div>
</body>
</html>
