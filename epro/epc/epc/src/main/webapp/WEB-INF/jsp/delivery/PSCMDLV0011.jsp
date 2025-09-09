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
<!-- order/PSCMDLV0011 -->

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
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "452px");//380px
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		var ibdata = {};
		// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet: msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:0, HeaderCheck:1};
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
                   {Header:"순번",Type:"Text",SaveName:"NUM",Align:"Center",MinWidth: 35,Sort:false,Edit:false}
                ,{Header:"주문일자",Type:"Text",SaveName:"ORD_DY",  Align:"Center",MinWidth: 100,Sort:false,Edit:false}
                ,{Header:"주문번호",Type:"Text",SaveName:"ORDER_ID",  Align:"Center",MinWidth: 100,Sort:false,Edit:false}
                ,{Header:"상품코드",Type:"Text",SaveName:"PROD_CD",Align:"Center",MinWidth: 100,Sort:false,Edit:false}
                ,{Header:"상품명",Type:"Text",SaveName:"PROD_NM",Align:"Center",MinWidth: 190,Sort:false,Edit:false}
                ,{Header:"발송여부",Type:"Text",SaveName:"DELI_YN",Align:"Center",MinWidth: 120,Sort:false,Edit:false}
                ,{Header:"배송소요일\n(주문일 기준)",Type:"Text",SaveName:"D_DAY",Align:"Center",MinWidth: 120,Sort:false,Edit:false}
                //,{Header:"페널티\n적용 횟수",Type:"Int",SaveName:"PENALTY_CNT",Align:"Center",MinWidth: 94,Sort:false,Edit:false}
		];
		
		IBS_InitSheet(mySheet, ibdata);
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		//mySheet.SetMergeSheet( msAll );
		
	}); // end of ready
		

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		
		var f = document.searchForm;

		var getDiff = getDateDiff(f.startDate.value,f.endDate.value);
		
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

		if(getDiff>90){
			alert('시작일자보다 종료일자가 90일 이상 클수 없습니다.');
			return;
		}  
		
		goPage('1');
	}

    function goPage(currentPage) {
    	
        var url = '<c:url value="/delivery/pscmdlv0011/search.do"/>';
        var param = new Object();
        
        param.rowsPerPage = $("#rowsPerPage").val();
    	param.mode = 'search';
	   	param.startDate = $("#startDate").val();
	   	param.endDate = $("#endDate").val();
	   	param.vendorId = $("#vendorId").val();
	   	param.prodCd = $("#prodCd").val();
	   	param.prodNm = $("#prodNm").val();
	   	param.penaltyCnt = $("#penaltyCnt").val();
		
    	loadIBSheetData(mySheet, url, currentPage, null, param);
    }	
    
    /***********************************************************
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
		
    	var xlsUrl = '<c:url value="/delivery/pscmdlv0011/excel.do"/>';
    	var hideCols = '';
    	
    	f.action = xlsUrl;
    	f.submit();
    	
    	//directExcelDown(mySheet, '업체배송발송일현황_'+toDay, xlsUrl, '#searchForm', null, hideCols); // 전체 다운로드 
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
									<th>주문일자</th>
									<td>
										<input type="text" name="startDate" value="${searchVO.startDate}" id="startDate" class="day" readonly style="width: 30%;"/>
										<a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
									  ~ <input type="text" name="endDate" value="${searchVO.endDate}" id="endDate" class="day" readonly style="width: 30%;"/>
									    <a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
									</td>
									<th>상품코드</th>
									<td>
										<input type="text" id="prodCd" name="prodCd" value="" style="width: 137px;">
									</td>
								</tr>	
								<tr>
									<th>상품명</th>
									<td class="text"> 
										<input type="text" id="prodNm" name="prodNm" value="" style="width: 115px;">
									</td>
									<%-- 2017.12.22 페널티 관련 보류 
										p.6 발송일 준수현황
										 - 페널티 항목 제외 후 제공여부
										   ▶ 패널티 항목 제외 처리
									<th>페널티<br/>적용 횟수</th>
									<td>
										<select class="select" id="penaltyCnt" name="penaltyCnt">
											<option value="" >전체</option>
											<option value="1" >1</option>
											<option value="2" >2</option>
											<option value="3" >3</option>
										</select>
									</td>
									--%>
									<th></th>
									<td>
									</td>
									<th></th>
									<td>
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
								<li class="tit">조회내역</li>
								<li class="btn">
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
						<li>배송관리</li>
						<li class="last">협력업체배송현황</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	<!--	@ BODY WRAP  END  	// -->
</body>
</html>