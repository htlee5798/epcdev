<%--
	Page Name 	: NEDMPRO0150.jsp
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
	/* function validate() {
		if ($("#srchEntpCd").val() == "") {
			alert('업체선택은 필수입니다.');
			$("#srchEntpCd").focus();
			return false;
		}
		
		return true;
	} */
	
	$(document).ready(function() {
		var currDate = '<c:out value="${currDate}" />';
		
		var yy = currDate.substring(0, 4);
		var mm = currDate.substring(4, 6);
		var dd = currDate.substring(6, 8);
		
		var today = yy + "-" + mm + "-" + dd;
		$("#spStdDate").text(today);
		$("#spD0").text(getDayLabel(today));
		
		// D-1
		var d1 = dateAddDel(today, -1, "d");
		$("#spD1").text(getDayLabel(d1));
		// D-2
		var d2 = dateAddDel(today, -2, "d");
		$("#spD2").text(getDayLabel(d2));
		// D-3
		var d3 = dateAddDel(today, -3, "d");
		$("#spD3").text(getDayLabel(d3));
		// D-4
		var d4 = dateAddDel(today, -4, "d");
		$("#spD4").text(getDayLabel(d4));
		// D-5
		var d5 = dateAddDel(today, -5, "d");
		$("#spD5").text(getDayLabel(d5));
		// D-6
		var d6 = dateAddDel(today, -6, "d");
		$("#spD6").text(getDayLabel(d6));
		
		
		
		
	});
	
	/* 날짜 계산 */
	function dateAddDel(sDate, nNum, type) {
		var yy = parseInt(sDate.substr(0, 4), 10);
		var mm = parseInt(sDate.substr(5, 2), 10);
		var dd = parseInt(sDate.substr(8), 10);
		
		//console.log(yy);
		//console.log(mm);
		//console.log(dd);
		
		if (type == "d") {
			var d = new Date(yy, mm - 1, dd + nNum);
		} else if (type == "m") {
			var d = new Date(yy, mm - 1 + nNum, dd);
		} else if (type == "y") {
			var d = new Date(yy + nNum, mm - 1, dd);
		}
		
		yy = d.getFullYear();
		mm = d.getMonth() + 1;
		mm = (mm < 10) ? '0' + mm : mm;
		dd = d.getDate();
		dd = (dd < 10) ? '0' + dd: dd;
		//console.log("--->" + '' + yy + '-' + mm + '-' + dd);
		
		return '' + yy + '-' + mm + '-' + dd;
	}
	
	/* 요일확인 */
	function getDayLabel(today) {
		/* var currDate = '<c:out value="${currDate}" />';
		
		var yyyy = currDate.substring(0, 4);
		var mm = currDate.substring(4, 6);
		var dd = currDate.substring(6, 8);
		
		currDate = yyyy + "-" + mm + "-" + dd;
		dateAddDel(currDate, -13, "d"); */
		//console.log(currDate);
		
		//console.log(Number(yyyy));
		//console.log(Number(mm));
		//console.log(Number(dd));
		//var calcDate = new Date(yyyy, mm, dd);
		//var calcDate2 = new Date(yyyy, mm, dd);
		//console.log(calcDate.setDate(calcDate.getDate() - 1));
		//calcDate2.setDate(calcDate.getDate() - 15);
		//console.log(calcDate2.getFullYear() + "-" + calcDate2.getMonth() + "-" + calcDate2.getDate());
		
		//console.log(dateAdd(currDate, "-1"));
		
		/* var date = new Date();
		date.setDate(date.getDate() - 12);
		console.log(date.toLocaleString());
		 */
		var week = new Array('일', '월', '화', '수', '목', '금', '토');
		
		var today = new Date(today).getDay();
		var todayLabel = week[today];
		console.log(todayLabel);
		return todayLabel;
	}
	
	/* 숫자만 입력 */
	function onlyNumber(obj) {
		$(obj).keyup(function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});
	}
	
	/* 조회 */
	function doSearch() {
		goPage('1');
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(pageIndex) {
		/* if (!validate()) {
			return;
		} */
		
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
			url : '<c:url value="/edi/product/NEDMPRO0150Select.json"/>',
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
	
	/* 재고등록 */
	function doSave() {
		var cnt = $("input[name='stdDate']").length;
		if (cnt <= 0) {
			alert("저장할 대상이 없습니다.");
			return;
		}
		
		var dataInfo = {};
		var alProdJaego = new Array();	// 선택한 협력업체
		for (var i = 0; i < cnt; i++) {
			var info = {};
			
			info["stdDate"] 		= $("input[name='stdDate']").eq(i).val();		// 기준일자
			info["venCd"] 			= $("input[name='venCd']").eq(i).val();			// 파트너사코드
			info["prodCd"] 			= $("input[name='prodCd']").eq(i).val();		// 상품코드
			info["srcmkCd"] 		= $("input[name='srcmkCd']").eq(i).val();		// 판매코드
			info["minProdQty"] 		= $("input[name='minProdQty']").eq(i).val();	// 최소생산량(BOX)
			info["prodReadTime"] 	= $("input[name='prodReadTime']").eq(i).val();	// 생산리드타임(일)
			info["jaegoQty"] 		= $("input[name='jaegoQty']").eq(i).val();		// 일별 재고수량(BOX)
			
			// 수량이 기입된 경우에만 담음
			if (info["jaegoQty"] != "") {
				alProdJaego.push(info);
			}
		}
		
		dataInfo["alProdJaego"] = alProdJaego;
		//console.log(dataInfo);
		//return;
		
		if (!confirm("등록하시겠습니까?")) {
			return;
		}
	
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/insertPbProduct.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.retMsg == "success") {
					alert("등록되었습니다.");
					doSearch();			// 재조회
				}
			}
		});
	}
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1"  bgcolor=ffffff>
		<td align="center">
			<c:out value="\${rnum}" />
			<input type="hidden" id="stdDate" 	name="stdDate" 	value="<c:out value="\${stdDate}" />" 	/>
			<input type="hidden" id="venCd" 	name="venCd" 	value="<c:out value="\${venCd}" />" 	/>
			<input type="hidden" id="prodCd" 	name="prodCd" 	value="<c:out value="\${prodCd}" />" 	/>
			<input type="hidden" id="srcmkCd" 	name="srcmkCd" 	value="<c:out value="\${srcmkCd}" />" 	/>
		</td>
		<td align="center"><c:out value="\${venCd}" /></td>
		<td align="left"><c:out value="\${venNm}" /></td>
		<td align="center"><c:out value="\${prodCd}" /></td>
		<td align="center"><c:out value="\${srcmkCd}" /></td>
		<td><c:out value="\${prodNm}" /></td>
		<td align="center"><c:out value="\${prodStd}" /></td>
		<%--<td align="center"><c:out value="\${ipsu}" /></td>--%>
		<td align="center">
			<input type="text" id="minProdQty" name="minProdQty" value="<c:out value="\${minProdQty}" />" onkeydown="onlyNumber(this);" style="width: 90%; text-align: center; ime-mode: disabled;" />
		</td>
		<td align="center">
			<input type="text" id="prodReadTime" name="prodReadTime" value="<c:out value="\${prodReadTime}" />" onkeydown="onlyNumber(this);" style="width: 90%; text-align: center; ime-mode: disabled;" />
		</td>
		<td align="center">
			<input type="text" id="jaegoQty" name="jaegoQty" value="<c:out value="\${jaegoQty}" />" onkeydown="onlyNumber(this);" style="width: 90%; text-align: center; ime-mode: disabled;" />
		</td>
		<td align="center"><c:out value="\${jaegoD1}" /></td>
		<td align="center"><c:out value="\${jaegoD2}" /></td>
		<td align="center"><c:out value="\${jaegoD3}" /></td>
		<td align="center"><c:out value="\${jaegoD4}" /></td>
		<td align="center"><c:out value="\${jaegoD5}" /></td>
		<td align="center"><c:out value="\${jaegoD6}" /></td>
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
							<li class="tit">PB상품현황</li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
								<a href="#" class="btn" onclick="doSave();"><span><spring:message code="button.common.create"/></span></a>
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
								<th>판매(88)코드</th>
								<td><input type="text" id="srchSrcmkCd" name="srchSrcmkCd" style="width:120px;" value="${param.srchSrcmkCd}" /></td>
							</tr>
						</table>
						<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
							<tr>
								<td colspan="4" bgcolor=ffffff>
									&nbsp;※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.
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
							<li class="tit">검색내역(기준일자 : <span id="spStdDate"></span>)</li>
						</ul>
						<div style="width:100%; height:446px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
							<table  cellpadding="1" cellspacing="1" border="0" width=1330 bgcolor=efefef>
								<colgroup>
									<col width="35px"/>
									<col width="80px"/>
									<col width="150px"/>
									<col width="80px"/>
									<col width="100px"/>
									<col width="200px" />
									<col width="80px"/>
									<%--<col width="60px"/>--%>
									
									<col width="60px"/>
									<col width="60px" />
									<col width="60px" />
									
									<col width="60px" />
									<col width="60px" />
									<col width="60px" />
									<col width="60px" />
									<col width="60px" />
									<col width="60px" />
								</colgroup>
								
								<tr bgcolor="#e4e4e4">
									<th rowspan=3>No.</th>
									<th rowspan=3>파트너사<br>코드</th>
									<th rowspan=3>파트너사</th>
									<th rowspan=3>상품코드</th>
									<th rowspan=3>판매코드</th>
									<th rowspan=3>상품명</th>
									<th rowspan=3>규격</th>
									<%--<th rowspan=3>입수</th>--%>
									<th rowspan=3>최소<br>생산량<br>(BOX)</th>
									<th rowspan=3>생산<br>리드타임<br>(일)</th>
									<th colspan=7>일별 재고수량(BOX)</th>
								</tr>
								
								<tr bgcolor="#e4e4e4">
									<th>D</th>
									<th>D-1</th>
									<th>D-2</th>
									<th>D-3</th>
									<th>D-4</th>
									<th>D-5</th>
									<th>D-6</th>
								</tr>
								
								<tr bgcolor="#e4e4e4">
									<th><span id="spD0"></span></th>
									<th><span id="spD1"></span></th>
									<th><span id="spD2"></span></th>
									<th><span id="spD3"></span></th>
									<th><span id="spD4"></span></th>
									<th><span id="spD5"></span></th>
									<th><span id="spD6"></span></th>
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
						<li>상품</li>
						<li>상품현황관리</li>
						<li class="last">PB상품 재고관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	
</body>
</html>
