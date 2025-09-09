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
	
	// 기본값 설정
	$("#teamGroupCode").val('00232');
	$("#protectTagTypeCode").removeClass("required");
	$("#protectTagTypeCode").parent().prev().find("span.star").hide();
	
	
	// EC 표준카테고리 select 변경시 액션
	$("#ecStandardLargeCategory").change(function() {
		$("#searchFlag").val("N");
		if( $(this).val() != '' ){
		    selectEcStandardCategory("ecStandardMediumCategory", $(this));
			$("#ecStandardSmallCategory option").not("[value='']").remove();
			$("#ecStandardSubCategory option").not("[value='']").remove();	
		}else{
			alert("카테고리를 다시 선택해 주세요.");
		}
	});
	$("#ecStandardMediumCategory").change(function() {
		$("#searchFlag").val("N");
		if( $(this).val() != '' ){
		    selectEcStandardCategory("ecStandardSmallCategory", $(this));
			$("#ecStandardSubCategory option").not("[value='']").remove();
		}else{
			alert("카테고리를 다시 선택해 주세요.");
		}
	});
	$("#ecStandardSmallCategory").change(function() {
		$("#searchFlag").val("N");
		if( $(this).val() != '' ){
	   		selectEcStandardCategory("ecStandardSubCategory", $(this));
		}else{
			alert("카테고리를 다시 선택해 주세요.");
		}
	});
	$("#ecStandardSubCategory").change(function() {
		if( $(this).val() != '' ){
			$("#searchFlag").val("Y");
			doSearch();
		}else{
			$("#searchFlag").val("N");
		}
	});
	
	// 조회버튼 클릭
	$('#search').click(function() {
		doSearch();	
	});
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	//ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, Page:10, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번",								Type:"Text",					SaveName:"RNUM", 		   			Align:"Center",					Width:70, 		Edit:0}
	  , {Header:"EC 표준 카테고리 ID",					Type:"Text",					SaveName:"STD_CAT_CD", 		    Align:"Center",					Width:130, 		Edit:0} 
	  , {Header:"EC 표준 카테고리명", 	 			    Type:"Text", 					SaveName:"STD_CAT_NM",     		Align:"Center",					Width:350, 	Edit:0}
	  , {Header:"카테고리 전시 구분", 	        		Type:"Text", 					SaveName:"MALL_CD",   					Align:"Center", 	 				Width:130, 	Edit:0}
	  , {Header:"EC 전시 카테고리 ID", 					Type:"Text", 					SaveName:"DISP_CAT_CD", 					Align:"Center", 	 				Width:130, 		Edit:0}
	  , {Header:"EC 전시 카테고리명", 				Type:"Text", 					SaveName:"DISP_CAT_NM",      Align:"Center", 					Width:350, 		Edit:0}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	//mySheet.SetEllipsis(1);	//말줄임
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

	selectEcStandardCategory("ecStandardLargeCategory");
	
}); // end of ready

/** ********************************************************
 * 목록 조회
 ******************************************************** */
function doSearch() {
	if($("#searchFlag").val() != "Y"){
		alert("필수 조건을 선택하세요");
		return;
	}
	goPage('1');
}
 
function goPage(currentPage){
	var url = '<c:url value="/edi/product/selectEcStdDispMapping.do"/>';
	
	loadIBSheetData(mySheet, url, currentPage, null, getParam(currentPage));
}

function getParam(currentPage) {
	
	var param = new Object();
	
	param.lrgStdCatCd	= document.form1.ecStandardLargeCategory.value;
	param.midStdCatCd	= document.form1.ecStandardMediumCategory.value;
	param.smlStdCatCd	= document.form1.ecStandardSmallCategory.value;
	param.subStdCatCd	= document.form1.ecStandardSubCategory.value;
	
	param.currentPage	= currentPage;			
	param.rowsPerPage	= $("#rowsPerPage").val();
		
	return param;
}

function selectEcStandardCategory(lowerCategory, category) {
	
	var param = new Object();
	var targetUrl = '<c:url value="/edi/product/ecStandardCategory.do"/>';
	
	if(typeof category != "undefined" ){
		param[category.attr('name')] = category.val();
	}

	var optionText = '';

	$.ajax({
		type: 'POST',
		url: targetUrl,
		async: false,
		data: param,
		success: function(data) {
			
			var list = data.list;

			for(var i=0; i<list.length; i++) {
				$("select[name="+lowerCategory+"]").show();
				if(i == 0) {
					optionText += '<option value="">선택</option>';
				}
				optionText += '<option value="'+list[i].CAT_CD+'">'+list[i].CAT_NM+'</option>';
			}

			$("select[name="+lowerCategory+"]").html(optionText);
			
			if(list.length == 0) {
				$("#searchFlag").val("Y");
				$("select[name="+lowerCategory+"]").hide();
				doSearch();
			}
		}
	});
}
</script>
</head>

<body>
	<div id="content_wrap">
		<div class="content_scroll">

		<input type="hidden" name="searchFlag"		id="searchFlag" 	value="N" />
			<form name="form1" id="form1">
				<div id="wrap_menu">
					<!-- 조회조건 -->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">EC 카테고리 조회</li>
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
									<th><span class="star">*</span>EC대분류</th>
									<td><select id="ecStandardLargeCategory" name="ecStandardLargeCategory" class="required"
										style="width: 150px;">
											<option value="">선택</option>
									</select></td>

									<th><span class="star">*</span>EC중분류</th>
									<td><select id="ecStandardMediumCategory" name="ecStandardMediumCategory" class="required"
										style="width: 150px;">
											<option value="">선택</option>
									</select></td>
								</tr>

								<tr>
									<th><span class="star">*</span>EC소분류</th>
									<td><select id="ecStandardSmallCategory" name="ecStandardSmallCategory" class="required"
										style="width: 150px;">
											<option value="">선택</option>
									</select></td>
									<th><span class="star">*</span>EC세분류</th>
									<td><select id="ecStandardSubCategory" name="ecStandardSubCategory" class="required"
										style="width: 150px;">
											<option value="">선택</option>
									</select></td>
								</tr>
								<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
									<tr>
										<td colspan="4" bgcolor=ffffff>
											<strong>&nbsp;<font color="red">카테고리 전시 구분에서 롯데ON은 롯데ON 카테고리이며 롯데마트몰은 롯데온내 롯데마트 카테고리를 의미한다.</font></strong>
										</td>
									</tr>
								</table>
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
							</ul>

							<tablecellpadding ="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td><div id="ibsheet1"></div></td>
									<!-- IBSheet 위치 -->
								</tr>
							</table>
						</div>
					</div>
					<!-- 조회결과 //-->
				    <div id="pagingDiv" class="pagingbox1" style="width: 100%;">
			            <script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script> <!-- 페이징 -->
				   </div>
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
						<li class="last">EC 카테고리조회</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	<!--	@ BODY WRAP  END  	// -->
</body>
</html>