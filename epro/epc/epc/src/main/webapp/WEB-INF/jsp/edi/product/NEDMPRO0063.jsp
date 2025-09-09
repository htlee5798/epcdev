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
<!-- 	<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script> -->
	<%@include file="./javascript.jsp"%>

	<!-- 신규 공통 css 및 js 파일 INCLUDE -->
	<c:import url="/common/commonHead.do" />



<style type="text/css">
#content_wrap{
	position: relative;
    z-index: 1;
    width: 833px!important;
}

#content_wrap .content_scroll {
    overflow: hidden;
}

#wrap_menu {
    width: 99%;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	
	// 기본값 설정
	$("#teamGroupCode").val('00232');
	$("#protectTagTypeCode").removeClass("required");
	$("#protectTagTypeCode").parent().prev().find("span.star").hide();
	
	//---------------------- button click event
	$("#search").unbind().click(null, eventSearch);
	
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
			eventSearch();
		}else{
			$("#searchFlag").val("N");
		}
	});
	
	//검색조건_대분류 setting
	selectEcStandardCategory("ecStandardLargeCategory");
	
	//----- grid init
	initGrid();
	
}); // end of ready

/** ********************************************************
 * 목록 조회
 ******************************************************** */
function initGrid(){
	$("#tabList").jqGrid({
		datatype: "local",  // 보내는 데이터 타입
		data: [],
		// 컬럼명
		colNames:[
			'EC 표준 카테고리 ID'
			, 'EC 표준 카테고리명'
			, '카테고리 전시 구분'
			, 'EC 전시 카테고리 ID'
			, 'EC 전시 카테고리 명'
		],
		// 헤더에 들어가는 이름
		colModel:[
			{name:'STD_CAT_CD'		, index:'STD_CAT_CD'		, sortable:false		, width:130		,align:"center"		},
			{name:'STD_CAT_NM'		, index:'STD_CAT_NM'		, sortable:false		, width:350		,align:"center"		},
			{name:'MALL_CD'			, index:'MALL_CD'			, sortable:false		, width:130		,align:"center"		},
			{name:'DISP_CAT_CD'		, index:'DISP_CAT_CD'		, sortable:false		, width:130		,align:"center"		},
			{name:'DISP_CAT_NM'		, index:'DISP_CAT_NM'		, sortable:false		, width:350		,align:"center"		}
		],
		gridComplete : function() {                                      // 데이터를 성공적으로 가져오면 실행 됨
			var colCount = $(this).getGridParam("colNames").length;
			$("#blankRow td:nth-child(2)").attr("colspan", colCount).attr("style", "text-align: center;");
			$(this).find("#blankRow td:nth-child(2)").empty();
			$(this).find("#blankRow td:nth-child(2)").append("조회결과가 없습니다.");
			$(window).resize();
		},
		loadComplete: function() {
			if ($(this).getGridParam("records")==0) {
				<%--조회결과가 없습니다. --%>
				$(this).addRowData("blankRow", {});
				$(this).find("#blankRow td:nth-child(2)").empty();
				$(this).find("#blankRow td:nth-child(2)").append("조회결과가 없습니다.");	
				
			}else{
// 				var allRows = $(this).jqGrid('getDataIDs'); //전체 행 가져오기
// 				for(var i = 0; i < allRows.length; i++){
// 					var cl = allRows[i];
// 					var rowData = $(this).jqGrid('getRowData', cl);
// 					}
// 				}
			}
			$(window).resize();
		},
		loadError:function(xhr, status, error) {										//데이터 못가져오면 실행 됨
			alert("처리중 오류가 발생했습니다.");
			return false;
		},
		onSelectRow : function(rowid, colld, val, e) {		//행 선택시 이벤트
			if(rowid === 'blankRow') return;	//조회결과가 없을 경우 클릭 이동(redirect) 막음
			
			var rowdata = $(this).getRowData(rowid);		// 선택한 행의 데이터를 가져온다
			if(rowdata == null) return;
			
		},
		<%-- jqGrid 속성, 필요에 따라 주석처리 하여 사용 (삭제X) --%>
		page: 1,															// 현재 페이지
		rowNum: 50,															// 한번에 출력되는 갯수
		rowList:[50,100,200,500],											// 한번에 출력되는 갯수 SelectBox
		pager: '#pageList',													// page가 보여질 div
		loadui : "disable",													// 이거 안 써주니 로딩 창 같은게 뜸
		emptyrecords : "조회결과가 없습니다.",  									// row가 없을 경우 출력 할 text
		gridview: true,														// 그리드 속도
		viewrecords: true,													// 하단에 1/1 또는 데이터가없습니다 추가
		rownumbers:true,													// rowNumber 표시여부
//			sortorder: "DESC",                                       		// desc/asc (default:asc)
		loadonce : false,													// reload 여부. [true: 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않음]
// 		multiselect	: true,													// 체크박스 show
		scroll : false,														// 스크롤 페이징 여부
		autowidth:true,
		shrinkToFit:false													// 컬럼 width 자동지정여부 (가로 스크롤 이용하기 위해 false지정)
	});
	
	// 그리드의 width, height 적용
    $('#tabList').setGridWidth($('#grid1container').width()); //Resized to new width as per window
    $('#tabList').setGridHeight("458px"); //Resized to new width as per window
} 

//조회
function eventSearch(){
	if($("#searchFlag").val() != "Y"){
		alert("필수 조건을 선택하세요");
		return;
	}
	
	var searchInfo = {};
	
	searchInfo.lrgStdCatCd	= document.form1.ecStandardLargeCategory.value;
	searchInfo.midStdCatCd	= document.form1.ecStandardMediumCategory.value;
	searchInfo.smlStdCatCd	= document.form1.ecStandardSmallCategory.value;
	searchInfo.subStdCatCd	= document.form1.ecStandardSubCategory.value;
	
	//grid data clear
	$("#tabList").jqGrid('clearGridData');
	
	$("#tabList").jqGrid('setGridParam',{
		url:'<c:url value="/edi/product/selectEcStdDispMapping.do"/>',		// url 주소
		datatype: "json" ,													// 보내는 데이터 타입
		ajaxGridOptions: { contentType: 'application/x-www-form-urlencoded; charset=utf-8' },
		postData: searchInfo,												// 보내는 데이터 형식
		mtype:'POST',														// POST,GET,LOCAL 3가지 존재
		page: 1,
// 		loadBeforeSend: function(jqXHR) {		// spring security 사용 시 추가
// 			jqXHR.setRequestHeader("X-CSRF-TOKEN", $("input[name='_csrf']").val());
// 		},
		jsonReader : {
			root:  "list",		//조회결과 데이터
			page: "page",		//현재 페이지	
			total: "total",		//총 페이지 수
			records: "records",	// 전체 조회된 데이터 총갯수 
			repeatitems: false
		}
	}).trigger("reloadGrid");
	
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
				eventSearch();
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
								<li class="tit"><spring:message code="text.common.title.searchResult" /></li>
							</ul>

							<!-- grid List -->
							<div id="grid1container" class="gridcontainer">
								<table id="tabList">
									<tr>
										<td></td>
									</tr>
								</table>
							</div>
							<div id="pageList"></div>
							<!-- ./grid List -->
						</div>
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
				</div>
			</form>
		</div>
</body>
</html>