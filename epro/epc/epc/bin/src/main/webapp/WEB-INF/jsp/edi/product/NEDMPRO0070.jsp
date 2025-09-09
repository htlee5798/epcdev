<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%-- <link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link> --%>
	<%@include file="../common.jsp"%>
	<%@ include file="/common/scm/scmCommon.jsp"%>
	<%@ include file="./CommonProductFunction.jsp"%>
	<%@ page contentType="text/html; charset=UTF-8"%>
	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	<script type="text/javascript"
		src="../../namoCross/js/namo_scripteditor.js"></script>
	<%@include file="./javascript.jsp"%>

	<!-- 신규 공통 css 및 js 파일 INCLUDE -->
	<c:import url="/common/commonHead.do" />



	<script type="text/javascript">
$(document).ready(function(){
	
	// 상품구분(규격, 패션) 선택
	$("select[name=productDivnCode]").change(setupFieldByProductDivnCode);

	// 온라인 대표 상품 코드 변경시 실행
	$("select[name=onlineProductCode]").change(selectOnlineRepresentProductCode2);

	// 온라인전용/소셜상품 선택시 실행
	$("input[name=onOffDivnCode]").click(setOnOffDivnCode);
	
	// 기본값 설정
	$("#teamGroupCode").val('00232');
	$("#protectTagTypeCode").removeClass("required");
	$("#protectTagTypeCode").parent().prev().find("span.star").hide();
	

	//----- 팀 변경시 이벤트
	$("#newProduct select[name=teamCd]").change(function() {
		//----- 대, 중, 소분류 초기화
		$("#l1Cd option").not("[value='']").remove();
		$("#l2Cd option").not("[value='']").remove();
		$("#l3Cd option").not("[value='']").remove();
						
		
		
		_eventSelectL1List($(this).val().replace(/\s/gi, ''));
	});
	
	//----- 대분류 변경시 이벤트
	$("#newProduct select[name=l1Cd]").change(function() {
		var groupCode	=	$("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');	
		
		//----- 중, 소분류 초기화
		$("#l2Cd option").not("[value='']").remove();
		$("#l3Cd option").not("[value='']").remove();			
		
		
		
		//-----중분류 셋팅
		_eventSelectL2List(groupCode, $(this).val());
	});
		
	//----- 중분류 변경시 이벤트
	$("#newProduct select[name=l2Cd]").change(function() {
		var groupCode	=	$("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');
		var l1Cd		=	$("#newProduct select[name=l1Cd]").val().replace(/\s/gi, '');
		
		//----- 소분류 초기화
		$("#l3Cd option").not("[value='']").remove();
		
		
		
		_eventSelectL3List(groupCode, l1Cd, $(this).val().replace(/\s/gi, ''));				
	});
	
	// 소분류 선택 변경 시 
	$("select[id=l3Cd]").change(function() {
		$("#l3Cd").val($(this).val());
		
			
	//	commerceChange($(this).val());		// 전상법 조회
	//	certChange($(this).val()); 			// KC인증마크 조회
	});
	
	// 조회버튼 클릭
	$('#search').click(function() {
		doSearch();	
	});
	
	// 팝업버튼 클릭
	$('#searchPopUp').click(function() {
		if($("#l3Cd").val()=="" || $("#l2Cd").val()=="" || $("#l1Cd").val()=="" || $("#teamCd").val()=="" ){
			alert("필수 조건을 선택하세요");	
			return;
		}
		doSearchPopUp();	
	});
	
	
		
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(true);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번",								Type:"Text",					SaveName:"num", 		   			Align:"Center",					Width:50, 		Edit:0}
	  , {Header:"소속팀 코드",					Type:"Text",					SaveName:"teamCd", 		    Align:"Center",					Width:200, 		Edit:0} 
	  , {Header:"대분류코드", 	 			    Type:"Text", 					SaveName:"catCd1",     		Align:"Center",					Width:100, 	Edit:0}
	  , {Header:"중분류코드", 	        		Type:"Text", 					SaveName:"catCd2",   					Align:"Center", 	 				Width:100, 	Edit:0}
	  , {Header:"소분류코드", 					Type:"Text", 					SaveName:"catCd3", 					Align:"Center", 	 				Width:100, 		Edit:0}
	  , {Header:"카테고리 코드", 				Type:"Text", 					SaveName:"categoryId",      Align:"Center", 					Width:100, 		Edit:0}
	  , {Header:"가테고리명명", 				Type:"Text", 					SaveName:"fullCategoryNm",  					Align:"Center",					Width:300,	 	Edit:0}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetEllipsis(1);	//말줄임
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	
}); // end of ready




function doSearchPopUp(){
	var msg='tet_code';
	var targetUrl = '<c:url value="/edi/product/PEDMPRO0007001.do"/>?orderItem='+$('#orderItem').val()+'&category='+$("#l3Cd").val();
	Common.centerPopupWindow(targetUrl, msg, {width : 500, height : 480});
}
	
/** ********************************************************
 * 목록 조회
 ******************************************************** */
 function doSearch() {
	if($("#l3Cd").val()=="" || $("#l2Cd").val()=="" || $("#l1Cd").val()=="" || $("#teamCd").val()=="" ){
		alert("필수 조건을 선택하세요");	
		return;
			
	}
		goPage('1');
}
 
function goPage(currentPage){
	var url = 'onlineDisplayAllCategory.do?catCd3='+$("#l3Cd").val()+'&catCd2='+$("#l2Cd").val()+'&catCd1='+$("#l1Cd").val();
	
	loadIBSheetData(mySheet, url, currentPage, '#newProduct', null);
}
</script>
</head>

<body>

	<div id="content_wrap">

		<div class="content_scroll">

			<form name="newProduct" id="newProduct">
				<div id="wrap_menu">
					<!-- 조회조건 -->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">상품일괄등록 카테고리 조회</li>
								<li class="btn"><a href="#" class="btn" id="search"><span><spring:message
												code="button.common.inquire" /></span></a></li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">
								<colgroup>
									<col style="width: 15%" />
									<col style="width: 35%" />
									<col style="width: 15%" />
									<col style="width: 35%" />
								</colgroup>
								<tr>
									<th><span class="star">*</span>소속팀</th>
									<td><select id="teamCd" name="teamCd" class="required"
										style="width: 150px;">
											<option value="">선택</option>
											<c:forEach items="${teamList}" var="teamList"
												varStatus="index">
												<c:if test="${fn:indexOf(teamList.teamNm,'VIC') == -1}">
													<option value="${teamList.teamCd}">${teamList.teamNm}</option>
												</c:if>
											</c:forEach>
									</select></td>

									<th><span class="star">*</span> 대분류</th>
									<td><select id="l1Cd" name="l1Cd" class="required"
										style="width: 150px;">
											<option value="">선택</option>
									</select></td>
								</tr>

								<tr>
									<th><span class="star">*</span> 중분류</th>
									<td><select id="l2Cd" name="l2Cd" class="required"
										style="width: 150px;">
											<option value="">선택</option>
									</select></td>
									<th><span class="star">*</span> 소분류</th>
									<td><select id="l3Cd" name="l3Cd" class="required"
										style="width: 150px;">
											<option value="">선택</option>
									</select></td>
								</tr>
								</table>
								
								<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
								<tr>
									<td colspan="4" bgcolor=ffffff>
										<strong>&nbsp;<font color="red">※ 기타 항목 설명</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;1. 친환경인증여부:  &nbsp;&nbsp;&nbsp; Y = 0 N= 1</font></strong> &nbsp;&nbsp;&nbsp;										
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;2. 가격 발급구분코드: &nbsp; Y = 0 N= 1</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;3. 수량/중량구분: &nbsp;&nbsp;&nbsp; 수량 = 0 중량= 1</font></strong>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;4. 친환경인증여부: &nbsp;&nbsp;&nbsp; Y = 0 N= 1</font></strong><br/>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<!-- 조회조건 // -->

					<!-- 조회결과 -->
					<div class="wrap_con">
						<!-- list -->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><spring:message
										code="text.common.title.searchResult" /></li>
								<li class="btn">
								<font color="red">공통코드는 팝업창으로 조회 하세요!!</font>
								<select id="orderItem" name="orderItem" class="select" style="width: 150px;">
										<option value="1">대표상품코드</option>
										<option value="2">전상법코드</option>
										<option value="3">KC인증코드</option>
										<option value="4">상품속성</option>
										<option value="5">통화단위 표시단위</option>
										<option value="6">상품사이즈단위</option>
										<option value="7">계절구분 (계절)</option>
										<option value="8">원산지</option>
										<option value="9">상품유형</option>
										<option value="10">친환경인증분류명코드</option>
								</select> 
								<a href="#" class="btn" id="searchPopUp"><span>공통코드</span></a></li>
							</ul>

							<tablecellpadding ="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><div id="ibsheet1"></div></td>
								<!-- IBSheet 위치 -->
							</tr>
							</table>

							<!-- <tablecellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><div id="ibsheet2"></div></td>IBSheet 위치
						</tr>
					</table> -->
						</div>
					</div>
					<!-- 조회결과 //-->
					<!-- 페이징 DIV -->

				</div>
			</form>
		</div>
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="location">
					<ul>
						<li>상품</li>
						<li>신규상품관리</li>
						<li class="last">카테고리조회</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	<!--	@ BODY WRAP  END  	// -->
</body>
</html>