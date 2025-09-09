<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<!-- order/PSCMDLV0009 -->


<script type="text/javascript">
	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function() {
		$('#search').click(function() {
			doSearch();
		});

		$('#excel').click(function() {
			doExcel();
		});			
		
		$('#startDate').click(function() {
			openCal('searchForm.startDate');
		});
		$('#endDate').click(function() {
			openCal('searchForm.endDate');
		});	
		
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		
		var ibdata = {};
		// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
       		{Header:"선택",					Type:"CheckBox", 	SaveName:"SELECTED", 	  		Align:"Center", 	Width:30,  Sort:false,	ColMerge:0}
		  , {Header:"주문번호",		    	Type:"Text" ,		SaveName:"ORDER_ID", 		    Align:"Center", 	Width:80, Edit:0,	ColMerge:1}
		  , {Header:"주문단품순번",	    	Type:"Text" ,		SaveName:"ORDER_ITEM_SEQ", 	    Align:"Center", 	Width:80, Edit:0,	ColMerge:0, Hidden:true}
		  , {Header:"원주문번호", 	    	Type:"Text", 		SaveName:"FIRST_ORDER_ID", 	    Align:"Center", 	Width:80, Edit:0,   ColMerge:1}
		  , {Header:"EC주문번호", 	    	Type:"Text", 		SaveName:"EC_ORDER_ID", 	    Align:"Center", 	Width:80, Edit:0,   ColMerge:1}
		  , {Header:"주문상태코드",	        Type:"Text", 		SaveName:"ORD_STS_CD", 	    	Align:"Center", 	Width:60, Edit:0,   ColMerge:0, Hidden:true}
		  , {Header:"주문상태", 	        Type:"Text", 		SaveName:"ORD_STS_NM", 	    	Align:"Center", 	Width:80, Edit:0,   ColMerge:0}
		  , {Header:"배송상태코드",	        Type:"Text", 		SaveName:"DELI_STATUS_CD",     	Align:"Center", 	Width:60, Edit:0,   ColMerge:0, Hidden:true}
		  , {Header:"배송상태", 	        Type:"Text", 		SaveName:"DELI_STATUS_NM",     	Align:"Center", 	Width:80, Edit:0,   ColMerge:0}
		  , {Header:"배송지순번", 	        Type:"Text", 		SaveName:"DELIVERY_ID",     	Align:"Center", 	Width:80, Edit:0,   ColMerge:0, Hidden:true}
		  , {Header:"주문자", 				Type:"Text", 		SaveName:"CUST_NM", 			Align:"Center", 	Width:80, Edit:0,	ColMerge:0}
		  , {Header:"상품코드", 			Type:"Text", 		SaveName:"PROD_CD",       		Align:"Center",   	Width:100, Edit:0,  ColMerge:0, Hidden:true}
		  , {Header:"상품명", 				Type:"Text", 		SaveName:"PROD_NM",        		Align:"Left", 		Width:250, Edit:0,  ColMerge:0}
		  , {Header:"주문수량", 			Type:"Int", 		SaveName:"TOT_QTY",  			Align:"Right", 		Width:80, Edit:0, Format:"#,###,###",  ColMerge:0}
		  , {Header:"취소/반품/\n교환수량",	Type:"Int", 		SaveName:"ACCEPT_QTY", 			Align:"Right", 		Width:80, Edit:0, Format:"#,###,###",  ColMerge:0}
		  , {Header:"주문금액", 			Type:"Int", 		SaveName:"ORD_AMT",  			Align:"Right", 		Width:65, Edit:0, Format:"#,###,###",  ColMerge:0}
		  , {Header:"결제금액",     		Type:"Int", 		SaveName:"TOT_SELL_AMT", 	    Align:"Right", 		Width:65, Edit:0, Format:"#,###,###",  ColMerge:0}
		  , {Header:"총결제금액",     		Type:"Int", 		SaveName:"SUM_TOT_SELL_AMT",  	Align:"Right", 		Width:65, Edit:0, Format:"#,###,###",  ColMerge:1}
		  , {Header:"사유",        			Type:"Text", 		SaveName:"ORD_CANCEL_REASON_NM",Align:"Center",    	Width:80, Edit:0,  ColMerge:0}
		  , {Header:"상세사유",      		Type:"Text", 		SaveName:"MEMO", 	    		Align:"Left",     	Width:200, Edit:0,  ColMerge:0}
		  , {Header:"접수일자", 	    	Type:"Text", 		SaveName:"ORD_DY", 	      		Align:"Center", 	Width:120, Edit:0,  ColMerge:0}
		  , {Header:"협력업체코드", 		Type:"Text", 		SaveName:"STR_VENDOR_ID",      	Align:"Center", 	Width:80, Edit:0,  ColMerge:0, Hidden:true}
		  , {Header:"EC주문여부", 		Type:"Text", 		SaveName:"EC_ORDER_YN",      	Align:"Center", 	Width:80, Edit:0,  ColMerge:0, Hidden:true}
		];
		
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.SetMergeSheet( msAll );
// 		mySheet.FitColWidth();
		
	}); // end of ready

		

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		
		var f = document.searchForm;
		
		var startDate = f.startDate.value.replace( /-/gi, '' );
		var endDate   = f.endDate.value.replace( /-/gi, '' );
		

        <c:choose>
			<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
			if ($("#vendorId").val() == "")
		    {
		        alert('업체선택은 필수입니다.');
		        $("#vendorId").focus();
		        return;
		    }
			</c:when>
		</c:choose>
		
		if(startDate>endDate){
			alert('시작일자가 종료일자보다 클수 없습니다.');
			return;
		}			
		
		goPage('1');
	}

    function goPage(currentPage)
    {
        var url = '<c:url value="/delivery/selectPartnerReturnList.do"/>';
        var param = new Object();
        
        param.rowsPerPage 	= $("#rowsPerPage").val();
    	param.startDate = $('#startDate').val();
    	param.endDate = $('#endDate').val();
    	param.mode = 'search';
        
    	param.vendorId = $("#vendorId").val();
    	param.ordRtnDivnCd = $("#ordRtnDivnCd").val();
    	param.ordStsCd = $("#ordStsCd").val();
    	param.searchType = $("#searchType").val();
    	param.searchContent = $("#searchContent").val();
		
    	loadIBSheetData(mySheet, url, currentPage, null, param);
    }	
    
//     function setMerge()
//     {
//     	var gridObj = document.WG1;
//     	if(gridObj.GetRowCount() != 0)    	
//     		gridObj.SetGroupMerge('ORDER_ID,CUST_NM,SUM_TOT_SELL_AMT');
//     }
	
    /** ********************************************************
     * execl
     ******************************************************** */
    function doExcel(){
		var f = document.searchForm;
		
		var startDate = f.startDate.value.replace( /-/gi, '' );
		var endDate   = f.endDate.value.replace( /-/gi, '' );
		var toDay     = '${searchVO.endDate}'.replace(/-/g, '');
		<c:choose>
			<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
			if ($("#vendorId").val() == "")
		    {
		        alert('업체선택은 필수입니다.');
		        $("#vendorId").focus();
		        return;
		    }
			</c:when>
		</c:choose>
		
		if(startDate>endDate){
			alert('시작일자가 종료일자보다 클수 없습니다.');
			return;
		}			
		
    	var xlsUrl = '<c:url value="/delivery/selectPartnerReturnListExcel.do"/>';
    	
    	var hideCols = '';
    	directExcelDown(mySheet, '취소반품교환주문_'+toDay, xlsUrl, '#searchForm', null, hideCols); // 전체 다운로드 
    }	

    //데이터 읽은 직후 이벤트
    function mySheet_OnSearchEnd(cd,msg) {
//     	mySheet.SetMergeSheet(eval(str));
//     	mySheet.SetMergeSheet( msHeaderOnly + msPrevColumnMerge );

//     	mySheet.DoSearch("PSCMDLV0009.jsp");
    }

	//셀 클릭 시 팝업 호출
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		// 상세정보 함수
		if (Row < 1) return;

		var colNm = mySheet.GetCellProperty(1, Col, "SaveName");

		if(colNm == 'SELECTED'){ // 상품평게시판 상세보기
			if(mySheet.GetCellValue(Row, 'ORD_STS_CD') != "40" && mySheet.GetCellValue(Row, 'ORD_STS_CD') != "50") {
				alert("주문상태가 교환접수, 반품접수 만 선택 가능합니다.");
				mySheet.SetCellValue(Row, 'SELECTED', 0);
			}

			if(mySheet.GetCellValue(Row, 'ORD_STS_CD') == "40" && mySheet.GetCellValue(Row, 'DELI_STATUS_CD') == "64") {
				alert("이미 반품회수처리 되었읍니다.");
				mySheet.SetCellValue(Row, 'SELECTED', 0);
			}

		}
	}

	// 회수처리
	function doUpdate(){
		if( mySheet.CheckedRows("SELECTED") == 0){
			alert("선택된 로우가 없습니다.");
			return;
		}
		
		if( !confirm("회수처리 하시겠습니까?")){
			return;
		}

		var targetUrl = '<c:url value="/delivery/updateWdrwProc.do"/>';

		mySheet.DoSave( targetUrl, {Quest:0,Col:0});	//Quest : IBSheet에서 확인메세지 출력여부
	}

	//저장완료후 발생하는 이벤트(삭제 메세지 후 IBSheet 데이터 조회)
	function mySheet_OnSaveEnd(code, Msg) {
		alert(Msg);
		doSearch();
	}

    function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#vendorId").val(strVendorId);
	}
    
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<div id="content_wrap">
		<div class="content_scroll">
			<form name="searchForm" id="searchForm" method="post">
				<input type="hidden" name="searchUseYn" value="" />
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">조회조건</li>
								<li class="btn">
									<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
									<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire" /></span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
								<col width="13%">
								<col width="17%">
								<col width="10%">
								<col width="30%">
								<col width="10%">
								<col width="20%">
								</colgroup>
								<tr>
									<th>협력업체코드</th>
									<td>
										<c:choose>
											<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
												<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
												<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
											</c:when>
											<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
												<select name="vendorId" id="vendorId" class="select">
													<option value="">전체</option>
												<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
							                        <option value="${venArr}" <c:if test="${venArr eq vendorId || (not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId ) }">selected</c:if>>${venArr}</option>
												</c:forEach>
												</select>
											</c:when>
										</c:choose>
									</td>
									<th><span class="star">*</span>기간</th>
									<td>
										<input type="text" name="startDate" value="${searchVO.startDate}" id="startDate" class="day" readonly style="width: 30%;"/>
										<a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
									  ~ <input type="text" name="endDate" value="${searchVO.endDate}" id="endDate" class="day" readonly style="width: 30%;"/>
									    <a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
									</td>
									<th>주문상태구분</th>
									<td>
										<select id="ordRtnDivnCd" name="ordRtnDivnCd" class="select">
											<option value="">전체</option>
											<c:forEach items="${OR001List}" var="ordRtnDivnCdList">
											<option value="${ordRtnDivnCdList.MINOR_CD}">${ordRtnDivnCdList.CD_NM}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>주문상태</th>
									<td>
										<select id="ordStsCd" name="ordStsCd" class="select">
											<option value="">전체</option>
											<c:forEach items="${OR002List}" var="ordStsCdList">
											<option value="${ordStsCdList.MINOR_CD}">${ordStsCdList.CD_NM}</option>
											</c:forEach>
										</select>
									</td>
									<th>
										<select id="searchType" name="searchType" class="select">
											<option value="%" <c:if test="${'%' eq searchVO.searchType}">selected="selected"</c:if>>선택</option>
											<option value="1" <c:if test="${'1' eq searchVO.searchType}">selected="selected"</c:if>>주문번호</option>
											<option value="2" <c:if test="${'2' eq searchVO.searchType}">selected="selected"</c:if>>EC주문번호</option>
											<option value="3" <c:if test="${'3' eq searchVO.searchType}">selected="selected"</c:if>>회원명</option>
											<option value="4" <c:if test="${'4' eq searchVO.searchType}">selected="selected"</c:if>>상품명</option>
										</select>
									</th>
									<td class="text" colspan="3"><input type="text" id="searchContent" name="searchContent" value="${searchVO.searchContent}"/></td>									
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
								<li class="tit">조회내역</li>
								<li class="btn">
				                    <a href="javascript:doUpdate();" class="btn" id="doUpdate"><span>회수처리</span></a>
								</li>
							</ul>

							<tablecellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
								</tr>
							</table>
						</div>
						<!-- 2검색내역 // -->
					</div>

					<!-- 페이징 DIV -->
					<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
						<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
					</div>
				</div>
			</form>
		</div>
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="location">
					<ul>
						<li>홈</li>
						<li>주문관리</li>
						<li class="last">취소/교환/반품주문</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	<!--	@ BODY WRAP  END  	// -->
</body>
</html>