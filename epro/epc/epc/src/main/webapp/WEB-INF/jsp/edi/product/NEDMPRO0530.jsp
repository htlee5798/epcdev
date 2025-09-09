<%--
	Page Name 	: NEDMPRO0530.jsp
	Description : 상품확장
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
			yja				최초생성		
--%>
<%@ include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:include page="/WEB-INF/jsp/edi/product/CommonProductExtendFunction.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>채널확장</title>
<style>
input[readonly] {
	background: #f8f8f8;
	border-radius: 2px;
	border: 1px solid #d3d3d3;
}

table.sub-table td {
	padding: 5px 3px;
	word-break: break-word;
}

table.sub-table select, table.sub-table input, table.sub-table textarea
	{
	max-width: 100%;
}

table.sub-table .tdr {
	text-align: right;
}

table.sub-table .tdc {
	text-align: center;
}

.tabs {
	border-bottom: 3px solid #ddd;
}

/* tabs */
ul.tab-box {
	display: flex;
	padding: 10px;
	padding-bottom: 0px;
	gap: 4px;
	white-space: nowrap;
	width: 100%;
	overflow-x: auto;
	overflow-y: hidden;
	background: #edf2f5;
	scrollbar-width: thin;
}

ul.tab-btn-box {
	display: flex;
	padding: 10px;
	padding-bottom: 0px;
	gap: 4px;
	height: auto;
	min-height: 30px;
	width: 85px;
}

ul.tab-box li.tab-itm, ul.tab-btn-box li.tab-itm {
	background: #ffffff;
	border: 1px solid #cccccc;
	border-bottom: 0px;
	border-top-right-radius: 8px;
	border-top-left-radius: 8px;
	display: inline-flex;
}

ul.tab-btn-box li.tab-itm {
	border-top-left-radius: 8px;
}

li.tab-itm .tab-cbox {
	background: #ffffff;
	border-top-left-radius: 7px;
	padding: 5px;
	width: 35px;
}

li.tab-itm .tabNm {
	font-weight: bold;
	display: inline-grid;
	width: 100%;
	padding: 5px 10px;
	cursor: pointer;
	border-top-right-radius: 8px;
	background: #f7f7f7;
}

li.tab-itm .tabNm.btn {
	background: #ffffff;
	border-top-left-radius: 8px;
	border-top-right-radius: 8px;
	margin: auto 0;
}

li.tab-itm .tabNm.btn>span {
	color: #466cff;
}

li.tab-itm .tabNm.btn:hover {
	background: #f4f8ff;
}

li.tab-itm .tabNm>span {
	text-align: center;
}

.tabNm i {
	margin-right: 5px;
}

li.tab-itm.on .tabNm {
	background: #7c8d98;
}

li.tab-itm.on .tabNm>span {
	color: #ffffff;
}

ul.con-tit {
	background: #5f6d77;
	height: 29px;
	line-height: 14px;
	font-size: 12px;
	margin-top: 10px;
}

ul.con-tit>li {
	color: #ffffff;
	font-weight: bold;
	padding: 6px 0 0 13px;
	float: left;
}

ul.con-tit>li.btn {
	float: right;
	padding: 2px 10px 0 0;
}

.star {
	color: red;
}

.badge {
	letter-spacing: -1px;
	padding: 3px 5px 3px 4px;
	border-radius: 8px;
	background: #a6a6a6;
	font-size: 11px;
	font-weight: bold;
	color: #ffffff;
}

.badge.red {
	background: #f0272e;
}

.badge.blue {
	background: #2672d9;
}
</style>
<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
<script type="text/javascript">

let def_curr = "KRW";	//기본 화폐단위
let chg_fg = false;		//입력값 변경감지
let last_selected_row = "";	//마지막 선택 행번호
let new_idx = 101;		//신규 문서 생성을 위한 index

$(function(){
	//grid init
	initGrid();
	
	//----  click event
	//탭추가
	$("#btnTabAdd").unbind().click(null, fncAddNewTabs);
	
	//(슈퍼) 신상품 장려금 적용여부 변경 event
	$(document).on("click", "input[name='sNewProdPromoFg']",function(){
		var value = $.trim($("input[name='sNewProdPromoFg']:checked").val()).toUpperCase();
		
		if(value == "X") fncSetsSNewProdStDy();			//(슈퍼) 신상품출시일자 기본값 셋팅
		else $("#sNewProdStDy").val("");				//(슈퍼) 신상품출시일자 빈값적용
	});
	
	//신상품 장려금 적용여부 변경 event
	$(document).on("click", "input[name='newProdPromoFg']",function(){
		var value = $.trim($("input[name='newProdPromoFg']:checked").val()).toUpperCase();
		
		//신상품장려금 미적용 시, 신상품 출시일자 초기화
		if(value == "") $("#newProdStDy").val("");
	});
	
	//채널 tab 선택 이벤트
	$(document).on("click", "#extTabs li div.tabNm", function(){
		let selTabId = $(this).closest("li").attr("id");
		fncSelTabs(selTabId);
	});
	
	$(document).on("click", "#detailInfo img.datepicker", function(){
		var dayInput = $(this).prev("input")[0];
		var objId = dayInput.id;
		var objVal = $.trim(dayInput.value);
		
		openCalSetDt("extendInfoForm."+objId, objVal, "fncCallBackCalendar");
	});
	
	//---- . click event end
	//------------------------------------------------------ 온오프 조건
	$(document).on("change", "#venCd", function(){
		let venCd = $.trim($(this).val());
		
		//협력업체 선택 시 이벤트
		_eventSetEntpCdInfoCtrl(venCd);
	});
	
	//-----필수 콤보박스 값 검증
	$(document).on("change", "select.required", function() {
		//console.log("event:$(select.required).change");
		validateSelectBox($(this));
	});

	//-----필수 입력항목 검증
	$(document).on("blur", "input:text.required", function() {
		//console.log("event:$(input:text.required).blur");
		if( !$(this).attr("readonly"))
		validateTextField($(this));
	});

	//-----해당 입력 항목이 값이 있는경우 검증
	$(document).on("blur", "input:text.requiredIf", function() {
		if( $(this).val().replace(/\s/gi, '') != ""  ) {
			validateTextField($(this));
		} else {
			deleteErrorMessageIfExist($(this));
		}
	});
	
	//---- 채널 체크박스 값 검증
	$(document).on("change", "input[name='extChanCd']", function(){
		chg_fg = true;
		
		var chked = $(this).is(":checked");
		var currVal = $.trim($(this).val());
		
		//원매가영역 활성화/비활성화처리
		fncSetPriceAreaByChan(currVal, chked);
		
		if(chked){
			//선택된 체크박스일 경우, 상품정보 영역 show
			$("#extendInfo tr[data-chan="+currVal+"]").show();
		}else{
			$("#extendInfo tr[data-chan="+currVal+"]").hide();
		}
		
		//체크된 채널이 있는지 확인
		var chkLen = $("input[name='extChanCd']:checked").length;
		if(chkLen == 0){
			showErrorMessage($("input[name=extChanCd]").first());
		}else{
			deleteErrorMessageIfExist($("input[name=extChanCd]").first());
		}
	});
	//------------------------------------------------------ 온오프 조건 end
	
	//------------------------- 입력값 변경 감지 end
	let bfInput = "";
	let afInput = "";
	$(document).on("focus", "#detailInfo input, #detailInfo select", function(){
		if(this.type == "radio"){
			bfInput = $("input[name="+this.name+"]:checked").val();
		}else{
			bfInput = $.trim($(this).val());
		}
	});
	
	$(document).on("propertychange change keyup paste input", "#detailInfo input, #detailInfo select", function(){
		if(this.type == "radio"){
			afInput = $("input[name="+this.name+"]:checked").val();
		}else{
			afInput = $.trim($(this).val());
		}
		
		//값 변경 시, 변경 flag 꺾어줌
		if(bfInput != afInput){
			chg_fg = true;
		}
	});
	//------------------------- 입력값 변경 감지 end
	
	//장려금영역 default setting
	_eventSetupJang("");
	
	//확장정보 입력영역 비활성화
	fncAreaCtrlExtendsDetail("N", "Y");
});

//판매코드 찾기 검색조건 팝업 OPEN
function fncOpenSrchPopSrcmkCd(){
	var pData = [];
	pData.trNum = "search";
	pData.entpCd = $.trim($("#srchVenCd").val());			//선택한 파트너사 코드 있을 경우, 해당 파트너사의 판매코드만 조회
	pData.srcmkCd = $.trim($("#srchSrcmkCd").val());		//입력한 판매코드 있을 경우 셋팅
	
	var params = "";
	Object.keys(pData).forEach(function(k, i){
	  params = params+(i===0?"?":"&")+k+"="+pData[k];  
	});
	
	var targetUrl = "<c:url value='/edi/product/selSrcmkCdPopup.do'/>"+params;
	Common.centerPopupWindow(targetUrl, 'sellCdPopup', {width: 980, height: 700});
}

//판매코드 찾기 팝업 callback
function setSellCd(json){
	if(json == null){
		alert("상품 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
		return;
	}
	
	//callback data
	var trNum = json.trNum;	 //대상 row구분 class
	var srcmkCd = json.srcmkCd; //판매코드
	var prodCd = json.prodCd; //상품코드
	var prodNm = json.prodNm; //상품명
	var l1Cd = json.l1Cd;	 //대분류코드
	var l2Cd = json.l2Cd;	 //중분류코드
	var l3Cd = json.l3Cd;	 //소분류코드
	var l1Nm = json.l1Nm;	 //대분류코드명
	var l2Nm = json.l2Nm;	 //중분류코드명
	var l3Nm = json.l3Nm;	 //소분류코드명
	var orgCost = $.trim(json.orgCost)!=""?$.trim(json.orgCost):"0";	//기존원가
	var orgCostFmt = setComma(orgCost);	//기존원가 금액formatting
	var prodPatFg = json.prodPatFg;	//상품유형구분

	//검색조건
	if(trNum == "search"){
		$("#searchForm #srchSrcmkCd").val(srcmkCd);
	}
}

//확장가능 상품내역 list
function initGrid(){
	$("#productList").jqGrid({
		datatype: "local",  // 보내는 데이터 타입
		data: [],
		// 컬럼명
		colNames:[
			'상품코드'
			, '판매코드'
			, '상품명'
			, '협력업체'
			, '판매채널'
			, '판매채널코드'
			, 'NB/PB'
		],
		// 헤더에 들어가는 이름
		colModel:[
			{name:'prodCd'			, index:'prodCd'		, sortable:false		, width:80		,align:"center"		},
			{name:'srcmkCdAll'		, index:'srcmkCdAll'	, sortable:false		, width:80		,align:"center"		},
			{name:'prodNm'			, index:'prodNm'		, sortable:false		, width:160		,align:"left"		},
			{name:'venCd'			, index:'venCd'			, sortable:false		, width:80		,align:"center"		},
			{name:'chanNm'			, index:'chanNm'		, sortable:false		, width:120		,align:"center"		},
			{name:'chanCd'			, index:'chanCd'		, hidden:true		},
			{name:'prodTypFg'		, index:'prodTypFg'		, hidden:true		}
		],
		gridComplete : function() {                                      // 데이터를 성공적으로 가져오면 실행 됨
			var colCount = $(this).getGridParam("colNames").length;
			$("#blankRow td:nth-child(2)").attr("colspan", colCount).attr("style", "text-align: center;");
			$(this).find("#blankRow td:nth-child(2)").empty();
			$(this).find("#blankRow td:nth-child(2)").append("조회결과가 없습니다.");
			$(window).resize();
		},
		loadComplete: function() {
			$(".ui-jqgrid .ui-jqgrid-btable").css("cursor","pointer");
			if ($(this).getGridParam("records")==0) {
				<%--조회결과가 없습니다. --%>
					$(this).addRowData("blankRow", {});
					$(this).find("#blankRow td:nth-child(2)").empty();
					$(this).find("#blankRow td:nth-child(2)").append("조회결과가 없습니다.");	
					
			}else{
//	 				var allRows = $(this).jqGrid('getDataIDs'); //전체 행 가져오기
//	 				for(var i = 0; i < allRows.length; i++){
//	 					var cl = allRows[i];
//	 					var rowData = $(this).jqGrid('getRowData', cl);
//	 					}
//	 				}
			}
		
			//선택 상품정보 초기화
			$("#hiddenForm input").val("");
			
			//확장 문서 탭 리스트 setting
			fncSetExtTabList(null);
			
			$(window).resize();
		},
		loadError:function(xhr, status, error) {										//데이터 못가져오면 실행 됨
			alert("처리중 오류가 발생했습니다.");
			return false;
		},
		onSelectRow : function(rowid, colld, val, e) {		//행 선택시 이벤트
			if(rowid === 'blankRow') return;	//조회결과가 없을 경우 클릭 이동(redirect) 막음
			
			//변경된 내용이 있을 경우 confirm 메세지
			if(chg_fg){
				if(!confirm("변경된 내용이 있습니다.\n저장하지 않고 조회하시겠습니까?")){
					if(last_selected_row != ""){
						$(this).jqGrid('setSelection', last_selected_row, false);
					}
					return;
				}
			}
			
			//선택 상품정보 초기화
			$("#hiddenForm input").val("");
			
			var rowdata = $(this).getRowData(rowid);		// 선택한 행의 데이터를 가져온다
			if(rowdata == null) return;
			
			//최신 선택 행 번호 업데이트
			last_selected_row = rowid;
			
			//선택 상품정보 setting
			$("#hiddenForm input[name='prodCd']").val(rowdata.prodCd);			//상품코드
// 			$("#hiddenForm input[name='srcmkCd']").val(rowdata.srcmkCd);		//판매코드
			$("#hiddenForm input[name='prodTypFg']").val(rowdata.prodTypFg);	//상품 NB,PB 구분
			
			//장려금영역 셋팅
			_eventSetupJang($.trim(rowdata.prodTypFg));
			
			//확장가능 채널리스트 조회
			eventSearchTabList();
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
// 		sortorder: "DESC", 		                                      		// desc/asc (default:asc)
		loadonce : false,													// reload 여부. [true: 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않음]
//  		multiselect	: true,													// 체크박스 show
		scroll : false,														// 스크롤 페이징 여부
		autowidth:true,
		shrinkToFit:true													// 컬럼 width 자동지정여부 (가로 스크롤 이용하기 위해 false지정)
	});
		
	// 그리드의 width, height 적용
    $('#productList').setGridHeight("148px"); //Resized to new width as per window
} 

//조회
function eventSearch(){
	//변경된 내용이 있을 경우 confirm 메세지
	if(chg_fg){
		if(!confirm("변경된 내용이 있습니다.\n저장하지 않고 조회하시겠습니까?")) return;
	}
	
	var searchInfo = {};
	
	$("#searchForm").find("input, select").not(".notInc").each(function(){
		if(this.name != undefined && this.name != null && this.name != ""){
			searchInfo[this.name] = $.trim($(this).val());
		}
	});
	
	//grid data clear
	$("#productList").jqGrid('clearGridData');
	last_selected_row = "";
	
	$("#productList").jqGrid('setGridParam',{
		url:'<c:url value="/edi/product/selectExtAvailProdList.json"/>',		// url 주소
		datatype: "json" ,													// 보내는 데이터 타입
		ajaxGridOptions: { contentType: 'application/x-www-form-urlencoded; charset=utf-8' },
		postData: searchInfo,												// 보내는 데이터 형식
		mtype:'POST',														// POST,GET,LOCAL 3가지 존재
		page: 1,
//  		loadBeforeSend: function(jqXHR) {		// spring security 사용 시 추가
//  			jqXHR.setRequestHeader("X-CSRF-TOKEN", $("input[name='_csrf']").val());
//  		},
		jsonReader : {
			root:  "list",		//조회결과 데이터
			page: "page",		//현재 페이지	
			total: "total",		//총 페이지 수
			records: "records",	// 전체 조회된 데이터 총갯수 
			repeatitems: false
		}
	}).trigger("reloadGrid");
}

//확장 가능 채널 list 조회
function eventSearchTabList(selTabId){
	let prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	let srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	
	//상품정보 없을 경우
// 	if("" == $.trim(prodCd) || "" == $.trim(srcmkCd)){
	if("" == $.trim(prodCd)){
		alert("상품 정보가 존재하지 않습니다.");
		fncAreaCtrlExtendsDetail("N", "Y");
		return;
	}
	
	var searchInfo = {};
	searchInfo.prodCd = $.trim(prodCd);
// 	searchInfo.srcmkCd = $.trim(srcmkCd);
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectExtTabList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			//상세정보 setting
			fncSetExtTabList(data, selTabId);
		} 
	});
}

//확장정보 탭리스트 setting
function fncSetExtTabList(data, selTabId){
	//채널 탭 리스트 초기화
	$("#extTabs").find("li").remove();
	
	//변경감지값 초기화
	chg_fg = false;
	
	//탭 list data 있을 경우 셋팅
	if(data != null && data.length > 0){
		$("#tabListTemplate").tmpl(data).appendTo("#extTabs");
	}
	
	let prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	let srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	
	//탭 없는데 상품 정보 있으면,
	let tabCnt = $("#extTabs li").length;
// 	if(tabCnt == 0 &&prodCd != "" && srcmkCd != ""){
	if(tabCnt == 0 &&prodCd != ""){
		//신규 탭 추가
		fncAddNewTabs();
	}
	
	//tab click event 실행
	fncSelTabs(selTabId);
}

//새 tab 추가
function fncAddNewTabs(){
	let prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	let srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	
	//상품정보 없을 경우
// 	if("" == $.trim(prodCd) || "" == $.trim(srcmkCd)){
	if("" == $.trim(prodCd)){
		alert("상품 정보가 존재하지 않습니다.\n상품을 먼저 선택해주세요.");
		fncAreaCtrlExtendsDetail("N", "Y");
		return;
	}
	
	//탭 존재 시, 상태확인 진행
	let tabCnt = $("#extTabs li").length;
	if(tabCnt > 0){
		//현재 탭 상태
		let currPrgsSts = $("#detailInfo input[name='prgsSts']").val();
		//저장되지 않은 상태일 경우,
		if($.trim(currPrgsSts) == ""){
			alert("신규 추가한 확장요청정보가 저장되지 않았습니다.\n저장 이후 추가 생성이 가능합니다.");
			return;
		}
	}
	
	//신규 탭 Data
	const newData = {
		"rnum"	: new_idx++
		, "pgmId" : ""
		, "prgsSts" : ""
		, "newYn" : "Y"
	};
	
	//Tab 추가
	$("#tabListTemplate").tmpl(newData).appendTo("#extTabs");
	
	$("#extTabs").children("li").removeClass("on");
	$("#extTabs").children("li").last().addClass("on");
	
	//hiddenForm에서 행선택 이벤트에서 셋팅되는 값 제외하고 초기화
	$("#hiddenForm input").not(".selrow").val("");
	
	//문서 상세정보 조회 (선택 가능한 채널 filtering)
	eventSearchDetail();
}

//상품확장 상세정보 입력영역 초기화
function fncClearExtendDetails(){
	//입력 영역 초기화
	$("#detailInfo").find("input[type!=button], select").not(".notCtrl").each(function(){
		if(this.name == undefined || this.name == null || this.name == "") return;
		
		if(this.type == "radio" || this.type == "checkbox"){
			$("#detailInfo input[name='"+this.name+"']").prop("checked", false);
			$("#detailInfo input[name='"+this.name+"']").not(":disabled").eq(0).prop("checked", true);
		}else{
			//통화초기화
			if(this.name.endsWith("Curr")){
				$(this).val(def_curr);
			}else{
				$(this).val("");
			}
		}
	});
	
	//채널 초기화
	$("#extendInfo input[name='extChanCd']").not(".fc-dis").prop("checked", false);
	
	//hiddenForm에서 행선택 이벤트에서 셋팅되는 값 제외하고 초기화
	$("#hiddenForm input").not(".selrow").val("");
	//상태값 초기화
	$("#prgsSts").val("");
	$("#prgsStsNm").text("신규등록");
}

//상품확장 영역 비활성화 처리
function fncAreaCtrlExtendsDetail(activeYn, clearYn){
	$("#extendInfo").find("[data-chan]").hide();
	
	let selChanCd;
	$("#detailInfo input[name='extChanCd']:checked").each(function(){
		selChanCd = $.trim($(this).val());
		$("#extendInfo").find("[data-chan='"+selChanCd+"']").show();
	});
	
	
	if($.trim(activeYn).toUpperCase() == "Y"){
		//--- 영역 활성화
		$("#detailInfo").find("input, select").not(".notCtrl").prop("disabled", false);
		$("#detailInfo input[name='extChanCd']").not(".fc-dis").prop("disabled", false);
		$("#detailInfo").find("a.btn").show();
		$("#detailInfo").find("img.datepicker").show();
		$("#extendBtnDiv .btn").show();
		
		let chkActiveChanCnt = $("#detailInfo input[name='extChanCd']").not(".fc-dis").length;
		if(chkActiveChanCnt == 0){
			generateMessage($("#detailInfo input[name='extChanCd']").first(), "확장 가능한 채널이 없습니다.");
		}
	}else{
		//--- 영역 비활성화
		$("#detailInfo").find("input, select").not(".notCtrl").prop("disabled", true);
		$("#detailInfo input[name='extChanCd']").prop("disabled", true);
		$("#detailInfo").find("a.btn").hide();
		$("#detailInfo").find("img.datepicker").hide();
		$("#extendBtnDiv .btn").hide();
	}
	
	//입력영역 초기화 Y일 경우
	if($.trim(clearYn).toUpperCase() == "Y"){
		//채널 전체 비활성화
		_fncDeactiveAllChans();
		//입력영역 초기화
		fncClearExtendDetails();
	}
}


//탭 선택 동작
function fncSelTabs(selTabId){
	//현재 탭이 신규 추가한 탭인지 확인
	var currTabObj = $("#extTabs li.on").eq(0);
	var currTabId = (currTabObj)? currTabObj.attr("id") : "";
	var currTabNewYn = (currTabObj)? currTabObj.attr("data-newYn") :  "N";
	
	//변경된 내용이 있을 경우 confirm 메세지
	if(chg_fg){
		if(!confirm("변경된 내용이 있습니다.\n저장하지 않고 조회하시겠습니까?")) return;
		
		//현재 탭이 신규추가한 탭일 경우, 저장하지 않고 이동 시 삭제처리
		if("Y" == currTabNewYn){
			currTabObj.remove();
		}
	}
	
// 	if($.trim(selTabId) != "" && $.trim(selTabId) != $.trim(currTabId)){
// 		$("#extTabs li[data-newYn='Y']:not([id='"+currTabId+"')]").remove();
// 	}
	
	//변경감지값 초기화
	chg_fg = false;
	
	$("#extTabs li").removeClass("on");
	$("div[name=error_msg]").remove();
	
	//hiddenForm에서 행선택 이벤트에서 셋팅되는 값 제외하고 초기화
	$("#hiddenForm input").not(".selrow").val("");
	
	//탭 개수 확인
	let tabCnt = $("#extTabs li").length;
	//탭 없을 경우, 비활성화 처리
	if(tabCnt == 0){
		fncAreaCtrlExtendsDetail("N", "Y");
		return;
	}
	
	//선택한 탭이 없을 경우, 마지막 탭 기본 setting
	if($.trim(selTabId) == ""){
		selTabId = $("#extTabs").children("li").last().attr("id");
	}
	
	//선택한 탭이 탭 리스트에 있는지 확인
	let selTabChk = $("#extTabs #"+selTabId).length;
	
	let selTabObj;
	if(selTabChk == 0){
		selTabObj = $("#extTabs").find("li").last();
	}else{
		selTabObj = $("#extTabs #"+selTabId);
	}
	
	//문서번호
	let pgmId = selTabObj.find("input[name='pgmId']").val() || "";
	
	//선택한 문서정보 셋팅
	$("#hiddenForm input[name='pgmId']").val(pgmId);
	
	selTabObj.addClass("on");
	
	//확장 상세정보 조회
	eventSearchDetail();
}

//상품확장정보 상세 조회
function eventSearchDetail(){
	let prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	let srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	let pgmId = $("#hiddenForm input[name='pgmId']").val();			//선택한 문서번호
	
	//상품정보 없을 경우
// 	if("" == $.trim(prodCd) || "" == $.trim(srcmkCd)){
	if("" == $.trim(prodCd)){
		alert("상품 정보가 존재하지 않습니다.");
		fncAreaCtrlExtendsDetail("N", "Y");
		return;
	}
	
	var searchInfo = {};
	searchInfo.prodCd = $.trim(prodCd);		//상품코드
// 	searchInfo.srcmkCd = $.trim(srcmkCd);	//판매코드
	searchInfo.pgmId = $.trim(pgmId);		//문서번호
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectTpcProdChanExtendDetailInfo.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			//상세정보 setting
			fncSetExtendDetails(data);
		} 
	});
}

//상품확장 상세정보 setting
function fncSetExtendDetails(json){
	let dtlInfo = json.dtlInfo;						//상세정보
	let extAbleChkList = json.extAbleChkList;		//확장가능 채널 list
	
	//==========================
	// 1. 확장 가능 채널 영역 셋팅
	//==========================
	// 채널 전체 선택 불가 처리
	$("#extendInfo input[name='extChanCd']").removeClass("fc-dis").addClass("fc-dis").prop("disabled", true).prop("checked", false);
	
	if(extAbleChkList != undefined && extAbleChkList != null && extAbleChkList.length > 0){
		let currChanCd = "";		//채널
		let currExtAbleSts = "";	//채널선택가능상태 체크
		
		//선택가능 채널 영역 활성화
		$.each(extAbleChkList, function(i, ele){
			currChanCd = $.trim(ele.chanCd);
			currExtAbleSts = $.trim(ele.extAbleSts);
			
			//채널 선택가능 상태일 경우에만 활성화
			if(currExtAbleSts == "A"){
				$("#extendInfo input[name='extChanCd'][value='"+$.trim(currChanCd)+"']").removeClass("fc-dis").prop("disabled", false);
			}
		});
	}
	
	//==========================
	// 2. 상세정보 셋팅
	//==========================
	//상세정보 있을 경우,
	if(dtlInfo == null){
		//영역 초기화
		fncAreaCtrlExtendsDetail("Y", "Y");
	}else{
		var pgmId		= dtlInfo.pgmId;				//프로그램 아이디
		var venCd		= dtlInfo.venCd;				//파트너사코드
		var prgsSts		= dtlInfo.prgsSts;				//진행상태코드
		var prgsStsNm	= dtlInfo.prgsStsNm;			//진행상태명
		
		_eventSetEntpCdInfoCtrl(venCd);
		
		var extChanCd	= $.trim(dtlInfo.extChanCd);			//확장채널
		
		var newProdPromoFg	= dtlInfo.newProdPromoFg;	//신상품장려금적용여부
		var newProdStDy		= dtlInfo.newProdStDyFmt;	//신상품 출시일자
		var overPromoFg		= dtlInfo.overPromoFg;		//성과초과장려금여부
		var sNewProdPromoFg	= dtlInfo.sNewProdPromoFg;	//(슈퍼) 신상품장려금적용여부
		var sNewProdStDy	= dtlInfo.sNewProdStDyFmt;	//(슈퍼) 신상품 출시일자
		var old_sNewProdStDy = ($.trim(dtlInfo.sNewProdStDy) != "")?sNewProdStDy:"<c:out value='${today}'/>";
		var sOverPromoFg	= dtlInfo.sOverPromoFg;		//(슈퍼) 성과초과장려금여부
		
		//--- 원매가 start
		var norProdPcost	= $.trim(dtlInfo.norProdPcost) || "";	//정상원가(마트)
		var norProdCurr		= dtlInfo.norProdCurr || def_curr;		//정상원가 화폐단위(마트)
		var norProdSalePrc	= $.trim(dtlInfo.norProdSalePrc) || "";	//정상매가(마트)
		var norProdSaleCurr	= dtlInfo.norProdSaleCurr || def_curr;	//정상매가 화폐단위(마트)
		var prftRate		= dtlInfo.prftRate||"";					//이익률(마트)
		
		var wnorProdPcost	= $.trim(dtlInfo.wnorProdPcost) || "";	//정상원가(MAXX)
		var wnorProdCurr	= dtlInfo.wnorProdCurr || def_curr;		//정상원가 화폐단위(MAXX)
		var wnorProdSalePrc	= $.trim(dtlInfo.wnorProdSalePrc) || ""; //정상매가(MAXX)
		var wnorProdSaleCurr= dtlInfo.wnorProdSaleCurr || def_curr;	//정상매가 화폐단위(MAXX)
		var wprftRate		= dtlInfo.wprftRate||"";				//이익률(MAXX)
		
		var snorProdPcost	= $.trim(dtlInfo.snorProdPcost) || "";	//정상원가(슈퍼)
		var snorProdCurr	= dtlInfo.snorProdCurr || def_curr;		//정상원가 화폐단위(슈퍼)
		var snorProdSalePrc	= $.trim(dtlInfo.snorProdSalePrc) || ""; //정상매가(슈퍼)
		var snorProdSaleCurr= dtlInfo.snorProdSaleCurr || def_curr;	//정상매가 화폐단위(슈퍼)
		var sprftRate		= dtlInfo.sprftRate||"";				//이익률(슈퍼)
		
		var onorProdPcost	= $.trim(dtlInfo.onorProdPcost) || "";	//정상원가(CFC)
		var onorProdCurr	= dtlInfo.onorProdCurr || def_curr;		//정상원가 화폐단위(CFC)
		var onorProdSalePrc	= $.trim(dtlInfo.onorProdSalePrc) || ""; //정상매가(CFC)
		var onorProdSaleCurr= dtlInfo.onorProdSaleCurr || def_curr;	//정상매가 화폐단위(CFC)
		var oprftRate		= dtlInfo.oprftRate||"";				//이익률(CFC)
		//--- 원매가 end
		
		$("#detailInfo input[name='pgmId']").val(pgmId);		//프로그램아이디
		$("#detailInfo select[name='venCd']").val(venCd);		//협력사코드
		$("#detailInfo input[name='prgsSts']").val(prgsSts);	//진행상태코드
		$("#detailInfo #prgsStsNm").text(prgsStsNm);			//진행상태명
		
		//--- 원매가 setting start
		$("#detailInfo input[name='norProdPcost']").val(norProdPcost);			//정상원가(마트)
		$("#detailInfo select[name='norProdCurr']").val(norProdCurr);			//정상원가 화폐단위(마트)
		$("#detailInfo input[name='norProdSalePrc']").val(norProdSalePrc);		//정상매가(마트)
		$("#detailInfo select[name='norProdSaleCurr']").val(norProdSaleCurr);	//정상매가 화폐단위(마트)
		$("#detailInfo input[name='prftRate']").val(prftRate);					//이익률(마트)
		
		$("#detailInfo input[name='wnorProdPcost']").val(wnorProdPcost);		//정상원가(MAXX)
		$("#detailInfo select[name='wnorProdCurr']").val(wnorProdCurr);			//정상원가 화폐단위(MAXX)
		$("#detailInfo input[name='wnorProdSalePrc']").val(wnorProdSalePrc);	//정상매가(MAXX)
		$("#detailInfo select[name='wnorProdSaleCurr']").val(wnorProdSaleCurr);	//정상매가 화폐단위(MAXX)
		$("#detailInfo input[name='wprftRate']").val(wprftRate);				//이익률(MAXX)
		
		$("#detailInfo input[name='snorProdPcost']").val(snorProdPcost);		//정상원가(슈퍼)
		$("#detailInfo select[name='snorProdCurr']").val(snorProdCurr);			//정상원가 화폐단위(슈퍼)
		$("#detailInfo input[name='snorProdSalePrc']").val(snorProdSalePrc);	//정상매가(슈퍼)
		$("#detailInfo select[name='snorProdSaleCurr']").val(snorProdSaleCurr);	//정상매가 화폐단위(슈퍼)
		$("#detailInfo input[name='sprftRate']").val(sprftRate);				//이익률(슈퍼)
		
		$("#detailInfo input[name='onorProdPcost']").val(onorProdPcost);		//정상원가(CFC)
		$("#detailInfo select[name='onorProdCurr']").val(onorProdCurr);			//정상원가 화폐단위(CFC)
		$("#detailInfo input[name='onorProdSalePrc']").val(onorProdSalePrc);	//정상매가(CFC)
		$("#detailInfo select[name='onorProdSaleCurr']").val(onorProdSaleCurr);	//정상매가 화폐단위(CFC)
		$("#detailInfo input[name='oprftRate']").val(oprftRate);				//이익률(CFC)
		//--- 원매가 setting end
		
		$("#detailInfo input[name='newProdPromoFg'][value='"+$.trim(newProdPromoFg)+"']").prop("checked", true);	//신상품장려금여부
		$("#detailInfo input[name='newProdStDy']").val(newProdStDy);	//신상품 출시일자
		$("#detailInfo input[name='overPromoFg'][value='"+$.trim(overPromoFg)+"']").prop("checked", true);			//성과초과장려금여부
		
		$("#detailInfo input[name='sNewProdPromoFg'][value='"+$.trim(sNewProdPromoFg)+"']").prop("checked", true);	//(슈퍼) 신상품장려금여부
		$("#detailInfo input[name='sNewProdStDy']").val(sNewProdStDy);			//(슈퍼) 신상품 출시일자
		$("#detailInfo input[name='old_sNewProdStDy']").val(old_sNewProdStDy);	//(슈퍼) 신상품 출시일자_최초등록일자
		$("#detailInfo input[name='sOverPromoFg'][value='"+$.trim(sOverPromoFg)+"']").prop("checked", true);		//(슈퍼) 성과초과장려금여부
		
		//기본 선택된 채널 초기화 후, 채널코드 셋팅
		$("#detailInfo input[name='extChanCd']").prop("checked", false);
		if($.trim(extChanCd) != ""){
			//콤마분리
			var extChanCdArr = extChanCd.split(",");
			
			//해당 data setting
			$("#detailInfo input[name='extChanCd']").each(function(){
				var selChanCd = $.trim($(this).val());
				if(extChanCdArr.includes(selChanCd)){
					$(this).prop("checked", true);
					//원매가영역활성화
					fncSetPriceAreaByChan(selChanCd, true);
					//채널관련 상품정보 영역 show
					$("#extendInfo tr[data-chan="+selChanCd+"]").show();
				}else{
					//원매가영역비활성화
					fncSetPriceAreaByChan(selChanCd, false);
					//채널관련 상품정보 영역 hide
					$("#extendInfo tr[data-chan="+selChanCd+"]").hide();
				}
			});
		}
		
		//상태값에 따른 입력 영역 control
		if(prgsSts == "01" || prgsSts == "02"){	//요청 or 승인 건 영역비활성화
			//입력영역 비활성화
			fncAreaCtrlExtendsDetail("N","N");
		}else{
			//입력영역 활성화
			fncAreaCtrlExtendsDetail("Y","N");
		}
		
		//변경감지값 초기화
		chg_fg = false;
	}
}

//채널 체크박스 변경에 따른 원매가 정보 영역 컨트롤
function fncSetPriceAreaByChan(chanCd, chked){
	const tgObj = $("#extendChanInfo");
	if(chanCd == undefined || chanCd == null || chanCd == "") return;
	
	//해당 채널의 원매가정보 영역
	var chanProdTr;
	
	if("ALL" == chanCd){
		chanProdTr = tgObj.find("tr[data-chan]");
	}else{
		chanProdTr = tgObj.find("tr[data-chan='"+chanCd+"']");
	}
	
	if(!chanProdTr) return;
	
	//활성화된 채널인지 체크
	var isChkable = $("input[name='extChanCd'][value='"+chanCd+"']").not(":disabled");
	
	//선택된 채널일 경우,
	if(chked){
		//활성화된 채널일 경우에만, 원매가정보 활성화
		if(isChkable){
			chanProdTr.css("display", "");
		}
	}else{
		//원매가정보 비활성화
		chanProdTr.css("display", "none");
		//입력값 초기화
		chanProdTr.find("input, select").val("");
		//통화초기화
		chanProdTr.find("select[name$='Curr']").val(def_curr);
	}
	
	var tradeType =	$("#hiddenForm input[name='tradeType']").val();						//거래형태[1:직매입, 2:특약1, 4:특약2]
	_eventChangeFiledByTradeType(tradeType);
}


//협력업체 선택 시 이벤트
function _eventSetEntpCdInfoCtrl(venCd){
	var errorNode = $("#detailInfo select[name='venCd']").prev("div[name=error_msg]").length;

	if( errorNode > 0 ) {
		$("#detailInfo select[name='venCd']").prev().remove();
	}
	
	// 신상품장려금 대상 파트너 여부 확인
	_eventCheckNewPromoFg(venCd);
	
	//거래중지된 업체인지 체크
	_eventCheckBlackListVendor(venCd);
	
	//협력업체별 채널 설정
	_eventSelVenZzorgInfo(venCd);
}

//저장 data validation
function fncChkValid(gbn){
	var prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	var srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	var pgmId = $("#hiddenForm input[name='pgmId']").val();			//현재문서번호
	
	var prgsSts = $("#detailInfo input[name='prgsSts']").val();		//진행상태코드
	
	//상품정보 없을 경우
// 	if("" == $.trim(prodCd) || "" == $.trim(srcmkCd)){
	if("" == $.trim(prodCd)){
		alert("상품 정보가 존재하지 않습니다.");
		return false;
	}
	
	//이미 승인된 정보
	if("02" == prgsSts){
		alert("이미 확장 승인된 건입니다.");
		return false;
	}
	
	//반려 or 거절된 정보
	if("03" == prgsSts){
		alert("거절 또는 반려 처리된 건입니다.\n신규 요청으로 진행해주세요.");
		return false;
	}
	
	//심사중인 정보
	if("01" == prgsSts){
		alert("확장 요청 심사중인 건입니다.");
		return false;
	}
	
	// 확장요청 가능 채널 없음
	let chkActiveChanCnt = $("#detailInfo input[name='extChanCd']").not(".fc-dis").length;
	if(chkActiveChanCnt == 0){
		alert("확장 가능한 채널이 없습니다.");
		return false;
	}

	// 채널 필수선택
	var chanCdChkedLen = $("input[name='extChanCd']:checked").not(":disabled").length;
	if(chanCdChkedLen == 0){
		alert("확장대상 채널을 1개 이상 선택하셔야 합니다.");
		return false;
	}
	
	var newProdPromoFg = $("#detailInfo input:radio[name='newProdPromoFg']:checked").val();	//장려금대상여부
	var newProdStDy = $("#detailInfo input[name='newProdStDy']").val().replace(/\D/g, "");	//신상품출시일자
	
	//신상품장려금 적용시, 출시일자 필수
	if(newProdPromoFg == "X" && newProdStDy == "") {
		alert("※신상품 출시일자를 넣으셔야 합니다. \n\n 신상품 출시일자는 KAN(88)코드 등록일자를 넣으셔야 합니다.");
		$("#detailInfo input[name=newProdStDy]").focus();
		return false;
	}
	
	var validationResult = validateCommon();
	
	var errorLength = $("div[name=error_msg]").length;
	
	if(!validationResult || errorLength > 0	) {
		alert("<spring:message code='msg.product.input.required.varAtt'/>");
		return false;
	} else {
		return true;
	}
	return true;
}

//저장
function eventSave(){
	var prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	var srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	var pgmId = $("#hiddenForm input[name='pgmId']").val();			//선택한 문서번호
	var currTabId = $.trim($("#extTabs li.on").attr("id"));			//현재탭아이디
	
	//저장 data validation
	if(!fncChkValid("S")){
		return;
	}
	
	if(!confirm("저장하시겠습니까?")) return;
	
	var saveInfo = {};
	
	//공통 저장 data creation
	var cboxTmpArr = [];	//체크박스 값 추출을 위한 임시 array
	$("#detailInfo").find("input[type!=button], select").not(".notInc").each(function(){
		if(this.name == undefined || this.name == null || this.name == "") return;
		
		if(this.type == "radio"){
			if(!$(this).is(":checked")) return;
			saveInfo[this.name] = $(this).val();
		}else if(this.type == "checkbox"){
			//체크박스일 경우, 동일한 이름의 체크된 항목값 콤마로 붙여서 가져옴
			if($(this).is($("input[name='"+this.name+"']").first())){
				$("input[name='"+this.name+"']:checked").each((i,ele)=> cboxTmpArr.push(ele.value));
				saveInfo[this.name] = cboxTmpArr.join();
				cboxTmpArr = [];
			}
		}else{
			if($(this).hasClass("amt") || $(this).hasClass("day")){
				saveInfo[this.name] = $.trim($(this).val()).replace(/\D/g,"");
			}else{
				saveInfo[this.name] = $.trim($(this).val());
			}
		}
	});
	
	//================================
	//저장 data filtering Start
	//================================
	//채널별 원매가 채널 선택시에만 저장
	var selChanCd = "";
	$("input[name='extChanCd']").not(":checked").each(function(){
		selChanCd = $.trim($(this).val());	//선택하지 않은 채널
		//선택하지 않은 채널관련 금액은 초기화한다
		$("#extendChanInfo tr[data-chan="+selChanCd+"]").find("input, select").each(function(){
			if(this.name != null && this.name != ""){
				saveInfo[this.name] = "";
			}
		});
	});
	
	//장려금 미사용 시, data 저장X
	if ($("div.jangoption").css("display") == "none") {
		saveInfo["newProdPromoFg"] 	= "";
		saveInfo["newProdStDy"]		= "";
		saveInfo["overPromoFg"]		= "";
	}
	
	var isSuper = $("input[name='extChanCd'][value='KR04']").is(":checked");	//슈퍼 선택
	//(슈퍼) 장려금 미사용 시 or 슈퍼가 아닐 시, data 저장 X
	if ($("div.jangoption").css("display") == "none" || !isSuper) {
		saveInfo["sNewProdPromoFg"] = "";
		saveInfo["sNewProdStDy"]	= "";
		saveInfo["sOverPromoFg"]	= "";
	}
	//================================
	
	
	saveInfo.prodCd = $.trim(prodCd);			//상품코드
// 	saveInfo.srcmkCd = $.trim(srcmkCd);			//판매코드
	saveInfo.pgmId = $.trim(pgmId);				//문서번호
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/updateTpcProdChanExtendDetailInfo.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			if(data.msg == "success"){
				alert("저장되었습니다.");
				//tablist 재조회
				eventSearchTabList(currTabId);
			}else{
				var errMsg = $.trim(data.errMsg);			//에러메세지
				alert("처리 중 오류가 발생했습니다.\n"+errMsg);
			}
		} 
	});
}

//(슈퍼) 신상품장려금 일자 setting
function fncSetsSNewProdStDy(){
	const old_sNewProdStDy = $.trim($("#old_sNewProdStDy").val());
	let sNewProdStDyDef = "";
	
	if(old_sNewProdStDy != ""){
		sNewProdStDyDef = old_sNewProdStDy;
	}else{
		sNewProdStDyDef = "<c:out value='${today}'/>";
	}
	
	const sNewProdStDyDefFmt = $.trim(sNewProdStDyDef).replace(/\D/g, "").substring(0,8).replace(/(\d{4})(\d{2})(\d{2})/g,"$1-$2-$3");
	$("#sNewProdStDy").val(sNewProdStDyDefFmt);
}

//요청
function eventRequest(){
	var prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	var srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	
	//요청 data validation
	var selTabLen = $("#extTabs li").find("input[name='cbox']:checked").length;
	if(selTabLen == 0){
		alert("확장 요청할 문서를 1개 이상 선택해주세요.");
		return;
	}
	
	//상품정보 없을 경우
// 	if("" == $.trim(prodCd) || "" == $.trim(srcmkCd)){
	if("" == $.trim(prodCd)){
		alert("상품 정보가 존재하지 않습니다.");
		return;
	}
	
	//변경내용이 있을 경우, 저장 이후 요청가능
	if(chg_fg){
		alert("변경된 내용이 있습니다.\n저장 후 요청이 가능합니다.");
		return;
	}
	
	if(!confirm("요청하시겠습니까?")) return;
	
	var saveInfo = {};
	var prodArr = [];
	
	let flag = true;
	let selPgmId;			//선택한 문서번호
	let selPrgsSts;			//선택한 문서 상태
	let selNewYn;			//선택한 문서가 신규추가한 문서인지
	
	$("#extTabs li").find("input[name='cbox']:checked").each(function(){
		var extInfo = {};
		selPgmId = $(this).closest("li").find("input[name='pgmId']").val();
		selPrgsSts = $(this).closest("li").find("input[name='prgsSts']").val();
		selNewYn = $.trim($(this).closest("li").attr("data-newYn")) || "N";
		
		//신규추가 후 저장하지 않은 정보
		if("Y" == selNewYn){
			alert("신규 추가한 요청정보가 포함되어 있습니다.\n저장 후 요청이 가능합니다. (문서번호:"+selPgmId+")");
			flag = false;
			return false;
		}
		
		//문서번호 없음
		if("" == selPgmId){
			alert("문서번호가 존재하지 않습니다. (문서번호:"+selPgmId+")");
			flag = false;
			return false;
		}
		
		//이미 승인된 정보
		if("02" == selPrgsSts){
			alert("이미 확장 승인된 건입니다. (문서번호:"+selPgmId+")");
			flag = false;
			return false;
		}
		
		//반려, 거절된 정보
		if("03" == selPrgsSts){
			alert("거절 또는 반려 처리된 건입니다.\n신규 요청으로 진행해주세요. (문서번호:"+selPgmId+")");
			flag = false;
			return false;
		}
		
		//심사중인 정보
		if("01" == selPrgsSts){
			alert("확장 요청 심사중인 건입니다. (문서번호:"+selPgmId+")");
			flag = false;
			return false;
		}
		
		extInfo.pgmId = $.trim(selPgmId);
		extInfo.prodCd = $.trim(prodCd);
// 		extInfo.srcmkCd = $.trim(srcmkCd);
		
		prodArr.push(extInfo);
	});
	
	if(!flag) return;
	
	saveInfo.prodArr = prodArr;
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/insertRequestProdChanExtendInfo.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			if(data.msg == "success"){
				alert("요청되었습니다.");
				//tablist 재조회
				eventSearchTabList();
			}else{
				var errMsg = $.trim(data.errMsg);			//에러메세지
				alert("처리 중 오류가 발생했습니다.\n"+errMsg);
			}
		} 
	});
}

//삭제
function eventDel(){
	var prodCd = $("#hiddenForm input[name='prodCd']").val();		//상품코드
// 	var srcmkCd = $("#hiddenForm input[name='srcmkCd']").val();		//판매코드
	var pgmId = $("#hiddenForm input[name='pgmId']").val();			//선택한 문서번호
	var prgsSts = $("#detailInfo input[name='prgsSts']").val();		//진행상태코드
	var newYn = $("#extTabs li.on").attr("data-newYn") || "N";		//신규추가여부
	
	//상품정보 없을 경우
// 	if("" == $.trim(prodCd) || "" == $.trim(srcmkCd)){
	if("" == $.trim(prodCd)){
		alert("상품 정보가 존재하지 않습니다.");
		return;
	}
	
	//이미 승인된 정보
	if("02" == prgsSts){
		alert("이미 확장 승인된 건입니다.");
		return;
	}
	
	//반려 or 거절된 정보
	if("03" == prgsSts){
		alert("거절 또는 반려 처리된 건입니다.");
		return;
	}
	
	//심사중인 정보
	if("01" == prgsSts){
		alert("확장 요청 심사중인 건입니다.");
		return;
	}
	
	//신규건이 아닌데, 문서번호 없는 경우
	if(newYn == "N" && pgmId == ""){
		alert("확장 요청 정보를 확인할 수 없습니다.");
		return;
	}
	
	if(!confirm("삭제하시겠습니까?")) return;
	
	//신규 건일 경우
	if(newYn == "Y"){
		alert("삭제되었습니다.");
		//tablist 재조회
		eventSearchTabList();
		return;
	}
	
	//신규건 아닐경우 data 삭제
	var saveInfo = {};
	saveInfo.prodCd = $.trim(prodCd);			//상품코드
// 	saveInfo.srcmkCd = $.trim(srcmkCd);			//판매코드
	saveInfo.pgmId = $.trim(pgmId);				//문서번호
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/deleteTpcExtProdReg.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			if(data.msg == "success"){
				alert("삭제되었습니다.");
				//tablist 재조회
				eventSearchTabList();
			}else{
				var errMsg = $.trim(data.errMsg);			//에러메세지
				alert("처리 중 오류가 발생했습니다.\n"+errMsg);
			}
		} 
	});
}
</script>

<!-- tablist 구성용 template -->
<script id="tabListTemplate" type="text/x-jquery-tmpl">
	{%if newYn && newYn == "Y"%}
	<li id="tabs_new_\${rnum}" class="tab-itm" data-newYn="Y">
	{%else%}
	<li id="tabs_\${rnum}" class="tab-itm" data-newYn="N">
	{%/if%}
		<div class="tab-cbox">
		{%if pgmId != null && pgmId != "" && prgsSts != "01" && prgsSts != "02"%}
			<input type="checkbox" id="cbox_\${rnum}" name="cbox" value="" class="notInc"/>
		{%/if%}
		</div>
		<div class="tabNm">
			<input type="hidden" name="pgmId" value="<c:out value='\${pgmId}'/>"/>
			<input type="hidden" name="prgsSts" value="<c:out value='\${prgsSts}'/>"/>
			<span name="tabNm">
				{%if newYn && newYn == "Y"%}
				신규문서
				{%else%}
				<c:out value='\${pgmId}'/>
				{%/if%}
			</span>
		</div>
	</li>
</script>

</head>
<body>
<div id="content_wrap">
	<div id="wrap_menu">
		<div class="wrap_search">
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">채널확장</li>
					<li class="btn">
						<a href="javascript:void(0);" class="btn" onclick="eventSearch()"><span><spring:message code="button.common.inquire"/></span></a>
					</li>
				</ul>
				<!-- 검색조건 start -->
				<form id="searchForm" name="searchForm" onsubmit="return false;">
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="12%"/>
						<col width="20%"/>
						<col width="12%"/>
						<col width="25%"/>
						<col width="12%"/>
						<col width="15%"/>
					</colgroup>
					<tbody>
						<tr>
							<th>파트너사</th>
							<td>
								<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
							</td>
							<th>판매코드</th>
							<td>
								<input type="text" name="srchSrcmkCd" id="srchSrcmkCd" style="width:100px;"/>
								<a href="javascript:void(0);" class="btn" onclick="fncOpenSrchPopSrcmkCd()"><span>찾기</span></a>
							</td>
							<th>진행상태</th>
							<td>
								<html:codeTag objId="srchPrgsSts" objName="srchPrgsSts"  parentCode="EXTST"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="전체" formName="form"/>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
				<!-- ./검색조건 end -->
				<!-- 검색내역 start -->
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">상품 조회내역</li>
							<li class="btn">
								
							</li>
						</ul>
						<!-- grid List -->
						<div id="grid1container" class="gridcontainer">
							<table id="productList">
								<tr>
									<td></td>
								</tr>
							</table>
						</div>
						<div id="pageList"></div>
						<!-- ./grid List -->
					</div>
				</div>
				<!-- ./검색내역 end -->
				
				<!-- 등록영역 start -->
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">채널 확장 상세정보</li>
							<li class="btn">
								<span style="color: #ffffff;font-weight: bold;">※ 확장정보 저장 후, 요청이 가능합니다.</span>
								<a href="javascript:void(0);" class="btn" onclick="eventRequest()"><span>요청</span></a>
							</li>
						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->
					<div class="tabs" style="display:flex;position:relative">
						<ul class="tab-btn-box" id="extTabBtns">
							<li id="btnTabAdd" class="tab-itm">
								<div class="tabNm btn">
									<span>
										<i class="bi bi-plus-circle-fill"></i>신규
									</span>
								</div>
							</li>
						</ul>
						<ul class="tab-box" id="extTabs">
						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->
					
					<div id="detailInfo" style="width: auto;overflow-x:hidden;overflow-y:auto;white-space:nowrap;padding: 10px;">
						<div id="extendBtnDiv" style="text-align: right;height:20px;">
							<div style="float: left;">
								<span style="font-weight: bold;letter-spacing: -2px;margin-right: 5px;">진행상태 | </span>
								<span id="prgsStsNm" class="badge">
								신규등록
								</span>
								<input type="hidden" id="prgsSts" name="prgsSts" class="notInc"/>
							</div>
						    <a href="javascript:void(0);" class="btn" onclick="eventSave()"><span>저장</span></a>
						    <a href="javascript:void(0);" class="btn" onclick="eventDel()"><span>삭제</span></a>
						</div>
						<form id="extendInfoForm" name="extendInfoForm">
						<ul class="con-tit"><li>기본 정보</li></ul>
						<table id="extendInfo" cellpadding="1" cellspacing="1" border="0" width="100%" style="table-layout:fixed;border:1px solid #ddd;" class="bbs_grid3">
							<colgroup>
								<col width="180px"/>
								<col width="*"/>
								<col width="180px"/>
								<col width="*"/>
							</colgroup>
							<tbody>
								<tr>
									<th><b><span class="star">*</span> 파트너사</b></th>
									<td>
										<html:codeTag objId="venCd" objName="venCd" width="150px;" dataType="CP" comType="SELECT" formName="form" defName="선택"/>
									</td>
									<th><b><span class="star">*</span> 채널</b></th>
									<td>
										<html:codeTag objId="extChanCd" objName="extChanCd" parentCode="PURDE"  comType="CHECKBOX" dataType="NTCPCD" orderSeqYn="Y" formName="form" childCode="1"  disabled="disabled" attr="class=\"notCtrl\""/>
									</td>
								</tr>
								<tr>
									<th><spring:message code="msg.product.onOff.default.newItemincentiveApply"/></th>	<!-- 신상품입점장려금 -->
									<td>
										<div class="jangoption">
											<input type="radio" name="newProdPromoFg" id="newProdPromoFg_N" value=""/>
											<label for="newProdPromoFg_N"><spring:message code="msg.product.onOff.default.notApply" /></label>
											<input type="radio" name="newProdPromoFg" id="newProdPromoFg_Y" value="X"/>
											<label for="newProdPromoFg_Y"><spring:message code="msg.product.onOff.default.apply" /></label>
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
									<th><spring:message code="msg.product.onOff.default.releaseDt" /></th>	<!--  신상품 출시일자 -->
									<td>
										<div class="jangoption">
											<input type="text" maxlength="8" class="requiredIf day" name="newProdStDy" id="newProdStDy" style="width: 80px;" readonly />
											<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor: hand;" />
											<br>
											<spring:message code="msg.product.onOff.default.whenIncentive" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr>
								<tr>
									<th><spring:message code="msg.product.onOff.default.exceIncentive" /></th>	<!-- 성과초과장려금 -->
									<td>
										<div class="jangoption">
											<input type="radio" name="overPromoFg" id="overPromoFg_N" value=""/>
											<label for="overPromoFg_N"><spring:message code="msg.product.onOff.default.notApply" /></label>
											<input type="radio" name="overPromoFg" id="overPromoFg_Y" value="X"/>
											<label for="overPromoFg_Y"><spring:message code="msg.product.onOff.default.apply" /></label>
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr>
								<tr data-chan="KR04" style="display:none;">
									<th>(슈퍼) <spring:message code="msg.product.onOff.default.newItemincentiveApply"/></th>	<!-- 신상품입점장려금 -->
									<td>
										<div class="jangoption">
											<input type="radio" name="sNewProdPromoFg" id="sNewProdPromoFg_N" value=""/>
											<label for="sNewProdPromoFg_N"><spring:message code="msg.product.onOff.default.notApply" /></label>
											<input type="radio" name="sNewProdPromoFg" id="sNewProdPromoFg_Y" value="X"/>
											<label for="sNewProdPromoFg_Y"><spring:message code="msg.product.onOff.default.apply" /></label>
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
									<th>(슈퍼) <spring:message code="msg.product.onOff.default.releaseDt" /></th>	<!--  신상품 출시일자 -->
									<td>
										<div class="jangoption">
											<input type="hidden" id="old_sNewProdStDy" name="old_sNewProdStDy" class="notInc" />
											<input type="text" maxlength="8" class="requiredIf day" name="sNewProdStDy" id="sNewProdStDy" style="width: 80px;" readonly />
<!-- 											<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor: hand;" /> -->
											<br>
											<spring:message code="msg.product.onOff.default.whenIncentive" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr>
								<tr data-chan="KR04" style="display:none;">
									<th>(슈퍼) <spring:message code="msg.product.onOff.default.exceIncentive" /></th>	<!-- 성과초과장려금 -->
									<td>
										<div class="jangoption">
											<input type="radio" name="sOverPromoFg" id="sOverPromoFg_N" value=""/>
											<label for="sOverPromoFg_N"><spring:message code="msg.product.onOff.default.notApply" /></label>
											<input type="radio" name="sOverPromoFg" id="sOverPromoFg_Y" value="X"/>
											<label for="sOverPromoFg_Y"><spring:message code="msg.product.onOff.default.apply" /></label>
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						
						<ul class="con-tit"><li>원가 정보</li></ul>
						<div style="background: #eee;font-size: 11px;border: 1px solid #ddd;border-bottom: none;padding: 10px;font-weight: bold;">
							<span>채널 선택 시, 채널별 입력 영역이 활성화됩니다.</span>
						</div>
						<table id="extendChanInfo" cellpadding="1" cellspacing="1" border="0" width="100%" bgcolor="efefef" style="table-layout:fixed;border:1px solid #ddd;" class="bbs_grid3">
							<colgroup>
								<col width="90px"/>
								<col width="90px"/>
								<col width="*"/>
								<col width="180px"/>
								<col width="*"/>
							</colgroup>
							<tbody>
								<tr class="mart_div" data-chan="KR02" style="display:none">
									<th rowspan="2"><b>마트</b></th>
									<th></th><td></td>
									<th><b><span class="star">*</span> 정상매가</b></th>
									<td>
										<input type="text" name="norProdSalePrc" id="norProdSalePrc" class="required number range price" maxlength="8" style="width: 150px;" oninput="this.value=this.value.replace(/\D/g,'')"/>
										<html:codeTag objId="norProdSaleCurr" objName="norProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
									</td>
								</tr>
								<tr class="mart_div" data-chan="KR02" style="display:none">
									<th><span class="star" style="display: none"> *</span> 이익률(%)</th>
									<td>
										<input type="text" name="prftRate" id="prftRate" value="" class="requiredIf rate" maxlength="5" style="width: 150px;" oninput="this.value=this.value.replace(/[^0-9.]/g,'')" readonly/>
									</td>
									<th><b>정상원가(vat제외)</b></th>
									<td>
										<input type="text" name="norProdPcost" id="norProdPcost" class="required number range cost" maxlength="8" style="width: 150px;" value="" oninput="this.value=this.value.replace(/\D/g,'')"/>
										<html:codeTag objId="norProdCurr" objName="norProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
									</td>
								</tr>
								<tr class="maxx_div" data-chan="KR03" style="display:none">
									<th rowspan="2"><b>MAXX</b></th>
									<th></th><td></td>
									<th><b><span class="star">*</span> 정상매가</b></th>
									<td>
										<div>
											<input type="text" name="wnorProdSalePrc" id="wnorProdSalePrc" value="" class="required number vicrange vicprice" maxlength="8" style="width: 150px;"  oninput="this.value=this.value.replace(/\D/g,'')"/>
											<html:codeTag objId="wnorProdSaleCurr" objName="wnorProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="maxx_div" data-chan="KR03" style="display:none">
									<th><span class="star" style="display: none"> *</span> 이익률(%)</th>
									<td><input type="text" name="wprftRate" id="wprftRate" value="" class="requiredIf vicrate" maxlength="5" style="width: 150px;" oninput="this.value=this.value.replace(/[^0-9.]/g,'')" readonly /></td>
									<th><b><span class="star">*</span> 정상원가(vat제외)</b></th>
									<td>
										<div>
											<input type="text" name="wnorProdPcost" id="wnorProdPcost" class="required number vicrange viccost" maxlength="8" style="width: 150px;" value="" oninput="this.value=this.value.replace(/\D/g,'')"/>
											<html:codeTag objId="wnorProdCurr" objName="wnorProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="super_div" data-chan="KR04" style="display:none">
									<th rowspan="2"><b>슈퍼</b></th>
									<th></th><td></td>
									<th><b><span class="star">*</span> 정상매가</b></th>
									<td>
										<div>
											<input type="text" name="snorProdSalePrc" id="snorProdSalePrc" value="" class="required number superrange superprice" maxlength="8" style="width: 150px;"  oninput="this.value=this.value.replace(/\D/g,'')"/>
											<html:codeTag objId="snorProdSaleCurr" objName="snorProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="super_div" data-chan="KR04" style="display:none">
									<th><span class="star" style="display: none"> *</span> 이익률(%)</th>
									<td><input type="text" name="sprftRate" id="sprftRate" value="" class="requiredIf superrate" maxlength="5" style="width: 150px;" oninput="this.value=this.value.replace(/[^0-9.]/g,'')" readonly /></td>
									<th><b><span class="star">*</span> 정상원가(vat 제외)</b></th>
									<td>
										<div>
											<input type="text" name="snorProdPcost" id="snorProdPcost" class="required number superrange supercost" maxlength="8" style="width: 150px;" value=""  oninput="this.value=this.value.replace(/\D/g,'')"/>
											<html:codeTag objId="snorProdCurr" objName="snorProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								
								<tr class="oca_div" data-chan="KR09" style="display:none">
									<th rowspan="2"><b>CFC</b></th>
									<th></th><td></td>
									<th><b><span class="star">*</span> 정상매가</b></th>
									<td>
										<div>
											<input type="text" name="onorProdSalePrc" id="onorProdSalePrc" value="" class="required number ocarange ocaprice" maxlength="8" style="width: 150px;"  oninput="this.value=this.value.replace(/\D/g,'')"/>
											<html:codeTag objId="onorProdSaleCurr" objName="onorProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="oca_div" data-chan="KR09" style="display:none">
									<th><span class="star" style="display: none"> *</span> 이익률(%)</th>
									<td><input type="text" name="oprftRate" id="oprftRate" value="" class="requiredIf ocarate" maxlength="5" style="width: 150px;" oninput="this.value=this.value.replace(/[^0-9.]/g,'')" readonly /></td>
									<th><b><span class="star">*</span> 정상원가(vat 제외)</b></th>
									<td>
										<div>
											<input type="text" name="onorProdPcost" id="onorProdPcost" class="required number ocarange ocacost" maxlength="8" style="width: 150px;" value=""  oninput="this.value=this.value.replace(/\D/g,'')"/>
											<html:codeTag objId="onorProdCurr" objName="onorProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
					</div>
				</div>
				<!-- ./등록영역 end -->
			</div>
			
			<!-- footer -->
			<div id="footer">
				<div id="footbox">
					<div class="msg" id="resultMsg"></div>
					<div class="location">
						<ul>
							<li>상품</li>
							<li>차세대_신규</li>
							<li class="last">채널확장</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- footer //-->
		</div>
	</div>
</div>
<form id="hiddenForm" name="hiddenForm" method="post">
	<input type="hidden" name="prodCd"		value="" class="selrow"/>		<%-- 현재 선택한 상품코드 --%>
	<%-- 현재 선택한 판매코드 --%>
	<%-- <input type="hidden" name="srcmkCd" 	value="" class="selrow"/>--%>
	<input type="hidden" name="pgmId"		value=""/>		<%-- 현재 선택한 문서번호 --%>
	
	<input type="hidden" id="newPromoVenFg" name="newPromoVenFg"	value="" class="notInc"/>
	<input type="hidden" id="tradeType" 	name="tradeType"		value="" class="notInc"/>
	<input type="hidden" id="prodTypFg" 	name="prodTypFg"		value="" class="notInc selrow"/>
</form>
</body>
</html>