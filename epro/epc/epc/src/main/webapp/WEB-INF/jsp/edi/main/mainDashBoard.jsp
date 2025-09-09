<%--
	Page Name 	: mainDashBoard.jsp
	Description : main 화면
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2025.04.24 		park jong gyu	 		최초생성
	
--%>
<%@ include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<title>대쉬보드 차트</title>
<script src="https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js"></script> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>
<script type="text/javascript" src="/js/epc/edi/consult/common.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.handler.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.tmpl.js"></script>

<script type="text/javascript" src="/js/jquery/jquery.blockUI.2.39.js"></script>
<!-- jquery handler -->
<script type="text/javascript" src="/js/jquery/jquery.handler.js"></script>

<!-- jqGrid -->
<link type="text/css" rel="stylesheet" href="/js/jquery/css/jquery-ui-1.11.4.css" ></link>
<link type="text/css" rel="stylesheet" href="/js/epc/jqGrid/css/ui.jqgrid.css" ></link>
<script type="text/javascript" src='/js/epc/jqGrid/js/i18n/grid.locale-${pageContext.response.locale}.js'></script>
<script type="text/javascript" src='/js/epc/jqGrid/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src='/js/epc/jqGrid/src/grid.common.js'></script>



<style type="text/css">

	div.wrap_con {
	    display: flex;
	    flex-wrap: wrap;
	    gap: 10px;
	    justify-content: start;
	    width: 100%;
	    margin: 10px auto;
	    padding-bottom: 10px;
	}
	
	
	div.wrap_con .line{
		/* width: calc((100% - 2 * 10px)/3); */
		height:500px;
		border: 1px solid #a6a6a6;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		box-sizing:border-box;
	}

	.con-header,
	.con-footer {
	    background: #efefef;
	    height: 40px;
	}
	
	.con-inner {
	    padding: 10px;
	    position: relative;
	    box-sizing: border-box;
/* 		height: 100%; */
	}
	
	.con-inner .btnl{
		position: absolute;
    	right: 10px;
	}
	
 	.con-tit {
		position: relative;
	    background: #002060;
	    width: 100%;
	    height: 40px;
	}
	
	.con-chart-body{
		width: 100%;
	    height: calc(100% - 60px);
	    padding-top: 20px;
	}
	
	.con-body {
		width: 100%;
	    height: calc(100% - 40px);
	    overflow: auto;
	    position: relative;
	}
	
	.con-body.grid{
		overflow: hidden;
	}
	
	.con-body div.con-body-tb-footer{
		position: absolute;
	    bottom: 0;
	    left: 0;
	    width: 100%;
	    background-color: #dfdfdf;
	    z-index: 10;
	}
	
	.con-tit .in-tit {
		line-height: 40px;
		font-size: 14px;
		font-weight: 700;
		text-align:center;
		color: #ffffff;
	}
	
	.badge {
		font-weight: 700;
		padding: 5px 10px;
		background: #bebebe;
		border-radius: 6px;
		color: #222;
		text-align: center;
	}
	
	.badge.orange{
		background: #ffc000;	
	}
	
	.con-tit .badge {
  	    position: absolute;
  	    padding: 1px 10px;
		border-radius: 12px;
		top: 10px;
		left: 10px;
		min-width: 40px;
		text-align: center;
	}
	
	div.wrap_con table tr,
	div.wrap_con table th,
	div.wrap_con table td{
		height:35px;
		padding:5px;
	}
	
	div.wrap_con table{
		min-width:100%;
	}
	
	table.type01{
		table-layout: fixed;
		width:100%;
	}
	
	table.type01 tr,
	table.type01 th,
	table.type01 td{
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
		word-break: break-word;
	}
	
	table th{
		background-color: #e4e4e4;
	}
	
	table.tb-sticky{
		table-layout: fixed;
		white-space: nowrap;
	}
	
	table.tb-sticky tr,
	table.tb-sticky th,
	table.tb-sticky td{
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
		word-break: break-word;
	}
	
	table.tb-sticky th{
		position: sticky;
		top:0;
	}
	
	.wrap_con.scrl{
		overflow-x: auto;
		flex-wrap: nowrap;
	}
	
	.wrap_con.scrl .line{
		flex: 0 0 auto;
		max-width: 700px;
	}
	
	.line.col1{
		width: 100%;
	}
	.line.col3{
		width: calc((100% - 2 * 10px)/3);
	}
	.line.col2{
		width: calc((100% - 1 * 10px)/2);
	}
	.line.col4{
		width: calc((100% - 3 * 10px)/4);
	}
	
	.con-body .ui-jqgrid-pager{
		height: 35px;
	}
</style>
<script type="text/javascript">
let lastSearchInfo;					//마지막 조회 조건
const defPageRowCnt = 10;			//전체 조회 시, grid 기본 조회 행 수

$(document).ready(function() {
	$(document).on("click", "#searchForm img.datepicker", function(){
		let dayInput = $(this).prev("input")[0];
		let objId = dayInput.id;
		let objVal = $.trim(dayInput.value);
		let objName = $.trim($(this).prev().attr('name'));
				
		let now = new Date( $('input[name="startDy"]').val() );
		now.setMonth( now.getMonth() + 1 );
		let year = now.getFullYear(); // 년도
		let month = ('0' + (now.getMonth() + 6)).slice(-2); // 월  
		let day = ('0' + now.getDate()).slice(-2); // 일
		
		if( objName == 'startDy'){
			openCalSetDt('searchForm.'+objId, objVal, "fncCallBackCalendar", "", "");
		}else{
			openCalSetDt('searchForm.'+objId, objVal, "fncCallBackCalendar", $('input[name="startDy"]').val(), year + '-' + month + '-' + day);
		}
	});
	
	//Grid 페이지 번호 입력 후 Enter 키
	$(document).on("keypress", ".ui-pg-input", function(e) {
		if (e.which === 13) {
			//현재 그리드 페이징 div
			const pagerDiv = $(this).closest(".ui-pager-control");
			//페이지 아이디
			const pagerId = pagerDiv.attr("id");
			//그리드 구분 추출
			const gridGbn = pagerId.replace("PageList", "");
			const gridId = gridGbn + "GridList";
			
			const page = parseInt($(this).val());
			const rows = $("#"+gridId).getGridParam("rowNum");
			
			if (!isNaN(page)) {
				eventSearchGridData($.trim(gridGbn), page, rows);
			}
		}
	});

	//Grid 페이지 행 수 변경 시
	$(document).on("change", ".ui-pg-selbox", function() {
		//현재 그리드 페이징 div
		const pagerDiv = $(this).closest(".ui-pager-control");
		//페이지 아이디
		const pagerId = pagerDiv.attr("id");
		//그리드 구분 추출
		const gridGbn = pagerId.replace("PageList", "");
		const gridId = gridGbn + "GridList";
		
		const page = $("#"+gridId).getGridParam("page");
		const rows = parseInt($(this).val());
		
		eventSearchGridData($.trim(gridGbn), page, rows);
	});
	
	//검색조건_팀 변경 이벤트
	$("#searchForm #teamCd").unbind().change(function(){
		//검색조건 대분류/중분류 초기화
		$("#searchForm #l1Cd option").not("[value='']").remove();
		$("#searchForm #l2Cd option").not("[value='']").remove();
		
		//대분류 selectbox option setting
		_eventSelSrchL1List($(this).val().replace(/\s/gi, ''));
	});
	
	//검색조건_대분류 변경 이벤트
	$("#searchForm #l1Cd").unbind().change(function(){
		var groupCode =	$("#searchForm select[name=teamCd]").val().replace(/\s/gi, '');
		
		//검색조건 중분류 초기화
		$("#searchForm #l2Cd option").not("[value='']").remove();
		
		//중분류 selectbox option setting
		_eventSelectSrchL2List(groupCode, $(this).val().replace(/\s/gi, ''));
	});
	
	
	//grid init -----------------------------
	initGrid("venBuy");				//2. 파트너사 매입액 top 10 조회
	initGrid("venSku");				//3. 파트너사 SKU 현황
	initGrid("nonPayItem");			//5. 미납 내역
	initGrid("inbRjtItem");			//6. 입고 거부 상품 미조치 내역
	initGrid("prodNewProp");		//7. 신상품 입점제안 조회
	initGrid("newProdPrfr");		//8. 신상품 실적 조회
	initGrid("prodChgCostItem");	//9. 원가 변경 요청 내역 조회
	
	//데이터 전체 조회
	doSearch();
	
	//전체 echart
	let eChartAll = document.getElementById("mainContents").getElementsByClassName("con-echart");
	//window size 변경이벤트
	window.onresize = function (){
		//echart resize
		$.each(eChartAll, function(i){
			var chartId = $.trim(this.id);
			if(chartId == "") return;
			var selChart = echarts.init(document.getElementById(chartId));	
			selChart.resize();
		});
		
		//jqGrid
		const jqGridAll = document.querySelectorAll('[id$="GridList"]');
		let jqGridHeight = 0;
		let jqGridWrapper;
		jqGridAll.forEach(el => {
			jqGridWrapper = document.getElementById(el.id).closest(".con-body");
			jqGridHeight = jqGridWrapper.offsetHeight - 75;
			$("#"+el.id).setGridHeight(jqGridHeight);
			$("#"+el.id).setGridWidth(jqGridWrapper.clientWidth); 
		});
	};
	
});

<%-- ajax 1번만 타도록 처리 --%>
// 검색
function doSearch(){
	let searchInfo = {};
	$('#searchForm').find('input, select').map(function() {
		if($(this).hasClass("day")){
			searchInfo[this.name] = $(this).val().replace(/[^0-9]/g, "");
		}else{
			searchInfo[this.name] = $.trim($(this).val());
		}
	});
	
	lastSearchInfo = searchInfo;
	
	searchInfo['page'] = 1;
	searchInfo['rowNum'] = defPageRowCnt;		//전체 조회 시, 조회 기본 행수만큼만 조회함 (아니면 개별로 페이징 말아야함)
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : "<c:url value='/edi/main/selectMainDashBoardAll.json'/>",
		data : JSON.stringify(searchInfo),
		success: function (data) {
			//1. 파트너사 실적 조회
			var venPrfr = data.VEN_PRFR;
			//2. 파트너사 매입액 top 10 조회
			var venBuy = data.VEN_BUY;
			//3. 파트너사 SKU 현황
			var venSku = data.VEN_SKU;
			
			//4. 기간별 미납율
			
			//5. 미납 내역
			var nonPayItem = data.NON_PAY_ITEM;
			
			//6. 입고 거부 상품 미조치 내역
			var inbRjtItem = data.INB_RJT_ITEM;
			
			//7. 신상품 입점제안 조회
			var prodNewProp = data.PROD_NEW_PROP;
			//8. 신상품 실적 조회
			var newProdPrfr = data.NEW_PROD_PRFR;
			//9. 원가 변경 요청 내역 조회
			var prodChgCostItem = data.PROD_CHG_COST_ITEM;
			
			//chart setting
			drawChartVenPrfr(venPrfr, 'venPrfrChart');			//1. 파트너사 실적 조회
			
			//jqGrid setting
			fncSetGridData(venBuy, 'venBuy');					//2. 파트너사 매입액 top 10 조회
			fncSetGridData(venSku, 'venSku');					//3. 파트너사 SKU 현황
			fncSetGridData(nonPayItem, 'nonPayItem');			//5. 미납 내역
			fncSetGridData(inbRjtItem, 'inbRjtItem');			//6. 입고 거부 상품 미조치 내역
			fncSetGridData(prodNewProp, 'prodNewProp');			//7. 신상품 입점제안 조회
			fncSetGridData(newProdPrfr, 'newProdPrfr');			//8. 신상품 실적 조회
			fncSetGridData(prodChgCostItem, 'prodChgCostItem');	//9. 원가 변경 요청 내역 조회
		}
	});
}

// list
function _setTbody(json, target) {
	setTbodyInit(target +"Tbody");	// dataList 초기화
	let data = json.list;
	let summ = json.summary;
	let rate = json.rate;
	
	if (data != undefined && data != null && data.length > 0) {
		$("#"+ target +"Template").tmpl(data).appendTo("#"+ target +"Tbody");
	} else {
		let colLen = $("#"+target+"Table").find("thead").find("th").length;
		setTbodyNoResult(target +"Tbody", colLen);
	}
	
	
	if(summ != undefined && summ != null){
		let currVal = "";
		let colNm = "";
		
		$("#"+target+"Tfooter").find("span").each(function(){
			if(this.id != undefined && this.id != null && this.id != ""){
				colNm = $.trim(this.id).replace(/^footer_/g,"");
				
				if($(this).hasClass("amt")){
					currVal = $.trim(summ[colNm]) || 0;
					currVal = setComma(currVal);
				}else{
					currVal = $.trim(summ[colNm]) || "-";
				}
				
				$(this).text(currVal);
			}
		});
	}
	
	if(rate != undefined && rate != null){
		$("#"+target+"Rate").text(rate);
	}
	
}

function drawChartVenPrfr(data, target){
	var resultData = data.barChartMap
	console.log(' set line + bar ');
	console.log(resultData);
	var echartBarLine = echarts.init(document.getElementById(target));

	echartBarLine.setOption({
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: { color: '#999' }
            }
        },
        legend: { data: resultData.series.data },
      /*   toolbox: {
            feature: {
                dataView: { show: true, readOnly: false },
                magicType: { show: true, type: ['line', 'bar'] },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        }, */
        grid: {
            left: '5%',
            right: '5%',
            bottom: '10%',
            top: '15%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            data: resultData.xAxisData,
            axisLabel: { textStyle: { color: '#000000', fontSize: 12 } }
        }],
        yAxis: [					//y축 설정 
            {
                type: 'value',
                name: '(단위 : 만원)',		//y축 이름 
                min: 0,
                max: 10000,
                /*min: function(item){
                    var diff = item.max-item.min;
                    if(diff == 0) {diff = 1}
                    return (item.min - ((diff) * 0.2)).toFixed(2);
                },
                max: function(item){
                    var diff = item.max-item.min;
                    if(diff == 0) {diff = 1}
                    return (item.max + ((diff) * 0.2)).toFixed(2);
                },*/
                interval: 1000,
                axisLabel: { formatter: '{value} KRW' }
            },
            {
                type: 'value',
                name: '이익율',	//y축 이름 
                min: 0,
                max: 100,  //  범위 조정
                /*min: function(item){
                    var diff = item.max-item.min;
                    if(diff == 0) {diff = 1}
                    return (item.min - ((diff) * 0.2)).toFixed(2);
                },
                max: function(item){
                    var diff = item.max-item.min;
                    if(diff == 0) {diff = 1}
                    return (item.max + ((diff) * 0.2)).toFixed(2);
                },*/
                interval: 10,
                axisLabel: { formatter: '{value} %' }
            }
        ],
        series: resultData.series
    });
}

//날짜변경 callback
function fncCallBackCalendar(tgObj, cbData){
	if(tgObj == undefined || tgObj == null) return;
	
	let tgId = $.trim(tgObj.id);
	if(tgId.startsWith("srch")) return;	//검색조건 내 캘린더일 경우 return
	
	//Row 상태 변경처리
	let tgRow = tgObj.closest("tr");
	if(tgRow == undefined || tgRow == null) return;
	
	//현재 rowStat 
	var currStat = $.trim(tgRow.attr("data-rowStat"));
	
	//기등록된 데이터일 경우
	if(currStat == "R"){
		//수정상태 변경
		tgRow.attr("data-rowStat", "U");
	}
}

//파트너사 SKU 현황 Grid init
function initGrid(gridGbn){
	const gridId = gridGbn + "GridList";
	const gridBlankRowId = gridGbn +"_blankRow"; 
	const gridCustomOpts = getGridCustomOptions(gridGbn);
	
	let colNameData = [];			//컬럼명
	let colModelData = [];			//컬럼 정보
	let rn = "";	//행 column명
	
	//==================================================
	// 그리드 컬럼 셋팅
	//==================================================
	switch(gridGbn){
		case "venBuy":	//2. 파트너사 매입액 top 10 조회
			colNameData = ['분류명', '판매코드', '상품명', '매입액', '매입량', '매출액', '매출량'];
			colModelData = [
				{name:'l3Nm'			, index:'l3Nm'			, sortable:false		, width:80		,align:"left"		},
				{name:'sellCd'			, index:'sellCd'		, sortable:false		, width:80		,align:"center"		},
				{name:'prodNm'			, index:'prodNm'		, sortable:false		, width:180		,align:"left"		},
				{name:'buyAmt'			, index:'buyAmt'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber},
				{name:'buyQty'			, index:'buyQty'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber},		
				{name:'salesAmt'		, index:'salesAmt'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber},		
				{name:'salesQty'		, index:'salesQty'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber}		
			];
			rn = "순위";
			break;
		case "venSku":	//3. 파트너사 SKU 현황
			colNameData = ['상품코드', '상품명', 'PLC등급', '상품상태'];
			colModelData = [
				{name:'prodCd'			, index:'prodCd'		, sortable:false		, width:80		,align:"center"		},
				{name:'prodNm'			, index:'prodNm'		, sortable:false		, width:180		,align:"left"		},
				{name:'prodGrd'			, index:'prodGrd'		, sortable:false		, width:40		,align:"center"		},
				{name:'prodStsNm'		, index:'prodStsNm'		, sortable:false		, width:80		,align:"center"		}
			];
			rn = "NO";
			break;
		case "nonPayItem":	//5. 미납 내역
			colNameData = ['발주번호', '입고예정일', '상품명', '미납사유', '귀책', '미납금액'];
			colModelData = [
				{name:'ordNo'			, index:'ordNo'			, sortable:false		, width:100		,align:"center"		},
				{name:'rcvDtFmt'		, index:'rcvDtFmt'		, sortable:false		, width:100		,align:"center"		},
				{name:'prodNm'			, index:'prodNm'		, sortable:false		, width:180		,align:"left"		},
				{name:'nonPayRsrn'		, index:'nonPayRsrn'	, sortable:false		, width:180		,align:"left"		},
				{name:'nonPaySt'		, index:'nonPaySt'		, sortable:false		, width:80		,align:"center"		},
				{name:'nonPayAmt'		, index:'nonPayAmt'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber}
			];
			rn = "NO";
			break;
		case "inbRjtItem":	//6. 입고 거부 상품 미조치 내역
			colNameData = ['일자', '판매코드', '상품코드', '상품명', '발생지점', '발생유형'];
			colModelData = [
				{name:'dyFmt'			, index:'dyFmt'			, sortable:false		, width:80		,align:"center"		},
				{name:'sellCd'			, index:'sellCd'		, sortable:false		, width:80		,align:"center"		},
				{name:'prodCd'			, index:'prodCd'		, sortable:false		, width:80		,align:"center"		},
				{name:'prodNm'			, index:'prodNm'		, sortable:false		, width:180		,align:"left"		},
				{name:'isuStore'		, index:'isuStore'		, sortable:false		, width:120		,align:"left"		},
				{name:'isuType'			, index:'isuType'		, sortable:false		, width:120		,align:"left"		}
			];
			rn = "NO";
			break;
		case "prodNewProp":	//7. 신상품 입점제안 조회
			colNameData = ['상태변경일', '상태', '등록일자', '팀', '대분류', '파트너사', '규격', '원가', '원매가'];
			colModelData = [
				{name:'modDate'			, index:'modDate'			, sortable:false		, width:80		,align:"center"		},
				{name:'prdtStatus'		, index:'prdtStatus'		, sortable:false		, width:80		,align:"center"		},
				{name:'regDate'			, index:'regDate'			, sortable:false		, width:80		,align:"center"		},
				{name:'teamNm'			, index:'teamNm'			, sortable:false		, width:120		,align:"left"		},
				{name:'l1Nm'			, index:'l1Nm'				, sortable:false		, width:100		,align:"left"		},
				{name:'venCd'			, index:'venCd'				, sortable:false		, width:100		,align:"left"		},
				{name:'prodStandardNm'	, index:'prodStandardNm'	, sortable:false		, width:80		,align:"center"		},
				{name:'norProdPcost'	, index:'norProdPcost'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber},
				{name:'prodSellPrc'		, index:'prodSellPrc'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber}
			];
			rn = "NO";
			break;
		case "newProdPrfr":	//8. 신상품 실적 조회
			colNameData = ['상품코드', '입점일', '상품명', '순매출액', '상품이익', '이익률', '판매량'];
			colModelData = [
				{name:'prodCd'		, index:'prodCd'		, sortable:false		, width:80		,align:"center"		},
				{name:'dyFmt'		, index:'dyFmt'			, sortable:false		, width:80		,align:"center"		},
				{name:'prodNm'		, index:'prodNm'		, sortable:false		, width:180		,align:"left"		},
				{name:'salesAmt'	, index:'salesAmt'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber},
				{name:'prodProfit'	, index:'prodProfit'	, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumberPoint},
				{name:'profitRate'	, index:'profitRate'	, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumberPoint},
				{name:'salesQty'	, index:'salesQty'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber}
			];
			rn = "NO";
			break;
		case "prodChgCostItem": //9. 원가 변경 요청 내역 조회
			colNameData = ['분류명', '판매코드', '상품코드', '상품명', '기존원가', '변경원가', '요청일시', '승인여부'];
			colModelData = [
				{name:'l3Nm'			, index:'l3Nm'			, sortable:false		, width:80		,align:"left"		},
				{name:'srcmkCd'			, index:'srcmkCd'		, sortable:false		, width:80		,align:"center"		},
				{name:'prodCd'			, index:'prodCd'		, sortable:false		, width:80		,align:"center"		},
				{name:'prodNm'			, index:'prodNm'		, sortable:false		, width:180		,align:"left"		},
				{name:'orgCost'			, index:'orgCost'		, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber},
				{name:'chgReqCost'		, index:'chgReqCost'	, sortable:false		, width:80		,align:"right"		, formatter:fmtGridAmt		, unformat : unfmtGridNumber},		
				{name:'reqDt'			, index:'reqDt'			, sortable:false		, width:100		,align:"center"		},
				{name:'prStatNm'		, index:'prStatNm'		, sortable:false		, width:80		,align:"center"		}
			];
			rn = "구분";
		default: break;
	}
	
	//==================================================
	// 그리드 옵션 셋팅
	//==================================================
	const gridAllOpts = Object.assign({
		datatype: "local",  // 보내는 데이터 타입
		data: [],
		// 컬럼명
		colNames: colNameData,
		// 헤더에 들어가는 이름
		colModel: colModelData,
		gridComplete : function() {                                      // 데이터를 성공적으로 가져오면 실행 됨
			var colCount = $(this).getGridParam("colNames").length;
			$("#"+gridBlankRowId+" td:nth-child(2)").attr("colspan", colCount).attr("style", "text-align: center;");
			$(this).find("#"+gridBlankRowId+" td:nth-child(2)").empty();
			$(this).find("#"+gridBlankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");
			$(this).find("#"+gridBlankRowId+" td:gt(1)").remove();
			
			$(window).resize();
		},
		loadComplete: function() {
			$(this).jqGrid("setLabel", "rn", rn);
			$(".ui-jqgrid .ui-jqgrid-btable").css("cursor","pointer");
			if ($(this).getGridParam("records")==0) {
				<%--조회결과가 없습니다. --%>
					$(this).addRowData(gridBlankRowId, {});
					$(this).find("#"+gridBlankRowId+" td:nth-child(2)").empty();
					$(this).find("#"+gridBlankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");	
					
			}else{
 				var allRows = $(this).jqGrid('getDataIDs'); //전체 행 가져오기
 				//그리드 load complete 공통처리
 				fncGridLoadCmplt(gridGbn, allRows);
			}
		
			$(window).resize();
		},
		loadError:function(xhr, status, error) {										//데이터 못가져오면 실행 됨
			alert("처리중 오류가 발생했습니다.");
			return false;
		},
		onSelectRow : function(rowid, colld, val, e) {		//행 선택시 이벤트
			if(rowid === gridBlankRowId) return;	//조회결과가 없을 경우 클릭 이동(redirect) 막음
			
			var rowdata = $(this).getRowData(rowid);		// 선택한 행의 데이터를 가져온다
			if(rowdata == null) return;
			
		}
	}, gridCustomOpts);
	
	//----- grid option setting
	$("#"+gridId).jqGrid(gridAllOpts);
	
	//==================================================
	// 그리드 종류별 footer 영역 설정
	//==================================================
	switch(gridGbn){
		case "venBuy":	//2. 파트너사 매입액 top 10 조회
			// colspan 적용: 2번째 셀에 colspan 4 적용
			const footerRow = $("#"+gridId).closest(".ui-jqgrid").find(".ui-jqgrid-ftable tr");
			const footerTitle = footerRow.find("td").eq(0); // 첫 번째 셀
			footerTitle.attr("colspan", 4).css("text-align", "center").css("width", "auto").html("Total (합계)");
			// 나머지 셀 제거
			footerTitle.nextAll().slice(0, 3).hide();
			break;
		default: break;
	}
	
	//==================================================
	// 그리드 Resize
	//==================================================
	let jqGridWrapper = document.getElementById(gridId).closest(".con-body");
	let jqGridWidth = jqGridWrapper.clientWidth;
	
	let jqGridHeader = jqGridWrapper.querySelector(".ui-jqgrid-hdiv")? jqGridWrapper.querySelector(".ui-jqgrid-hdiv").offsetHeight : 0;
	let jqGridFooter = jqGridWrapper.querySelector(".ui-jqgrid-sdiv")? jqGridWrapper.querySelector(".ui-jqgrid-sdiv").offsetHeight : 0;
	let jqGridPager = jqGridWrapper.querySelector(".ui-jqgrid-pager")? jqGridWrapper.querySelector(".ui-jqgrid-pager").offsetHeight : 0;
	
	let conHeaderHeight = (jqGridWrapper.parentElement.querySelector(".con-header"))? jqGridWrapper.parentElement.querySelector(".con-header").offsetHeight : 0;
	let conFooterHeight = (jqGridWrapper.parentElement.querySelector(".con-Footer"))? jqGridWrapper.parentElement.querySelector(".con-Footer").offsetHeight : 0;

	let jqGridHeight = jqGridWrapper.offsetHeight - 40 - (jqGridHeader+jqGridFooter+jqGridPager+conHeaderHeight+conFooterHeight);
	
	
	// 그리드의 width, height 적용
    $("#"+gridId).setGridHeight(jqGridHeight);
    $("#"+gridId).setGridWidth(jqGridWidth);
}

//jqGrid init 커스텀옵션 셋팅
function getGridCustomOptions(gridGbn) {
	const gridPageId = gridGbn +"PageList";
	//기본옵션
	const defOpts = {
		page: 1,															// 현재 페이지
		rowNum: defPageRowCnt,												// 한번에 출력되는 갯수
// 		rowList:[10, 30, 50, 100, 200, 500],								// 한번에 출력되는 갯수 SelectBox
		pager: "#"+gridPageId,												// page가 보여질 div
		loadui : "disable",													// 이거 안 써주니 로딩 창 같은게 뜸
		emptyrecords : "조회결과가 없습니다.",  									// row가 없을 경우 출력 할 text
		gridview: true,														// 그리드 속도
		viewrecords: true,													// 하단에 1/1 또는 데이터가없습니다 추가
		rownumbers:true,													// rowNumber 표시여부
 		//sortorder: "DESC", 		                                      	// desc/asc (default:asc)
		loadonce : false,													// reload 여부. [true: 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않음]
  		//multiselect	: true,												// 체크박스 show
		scroll : false,														// 스크롤 페이징 여부
		autowidth: true,
		shrinkToFit:false													// 컬럼 width 자동지정여부 (가로 스크롤 이용하기 위해 false지정)
	};
	
	//커스텀 옵션
	let customOpts = {};
	switch(gridGbn){
		case "venBuy":		//---- 2. 파트너사 매입액 top 10 조회
			customOpts = {
				footerrow: true				//footer사용
				, userDataOnFooter : true	//footer사용
				, pager: ""					//paging 미사용
			}
			break;
		case "venSku":		//---- 3. 파트너사 SKU 현황
			customOpts = {
				shrinkToFit:true	//너비 꽉 채우기
			};
			break;
		case "nonPayItem":	//---- 5. 미납 내역
			customOpts = {
				shrinkToFit:true	//너비 꽉 채우기
			};
			break;
		default:break;
	}
	
	return Object.assign({}, defOpts, customOpts);
}

//grid data setting
function fncSetGridData(json, gridGbn){
	let gridId = gridGbn + "GridList";
	let gridBlankRowId = gridGbn +"_blankRow";
	
	//grid datalist 초기화
	$("#"+gridId).jqGrid('clearGridData');
	
	//1) grid 외 추가 customData setting
	switch(gridGbn){
		case "venSku":		//3. 파트너사 SKU 현황
			break;
		case "nonPayItem":	//5. 미납 내역
			//미납율 setting
			var nonPayItemRate = $.trim(json.rate) || "0";
			$("#nonPayItemRate").text(nonPayItemRate);
			break;
		case "inbRjtItem":	//6. 입고 거부 상품 미조치 내역
			//거부율 setting
			var inbRjtItemRate = $.trim(json.rate) || "0";
			$("#inbRjtItemRate").text(inbRjtItemRate);
			break;
		case "newProdPrfr":	//8. 신상품 실적 조회
			break;
		default:break;
	}
	
	if(json == undefined || json == null) return;
	
	//2) grid data list setting
	let list = json.list || [];		//조회결과 데이터
	let page = json.page;			//현재 페이지	
	let total = json.total;			//총 페이지 수
	let records = json.records;		//전체 조회된 데이터 총갯수 
	
	$("#"+gridId).jqGrid('setGridParam', {
		datatype: "local"
		, data : list
		, page : page
		, total : total
		, records: records
	}).trigger("reloadGrid");
}

//grid data 조회
function eventSearchGridData(gridGbn, page, row){
	let gridId = gridGbn + "GridList";
	let gridBlankRowId = gridGbn +"_blankRow";
	let gridSelUrl = "";
	
	switch(gridGbn){
		case "venBuy": gridSelUrl ="<c:url value='/edi/main/selectVenBuyList.json'/>"; break;						//2. 파트너사 매입액 top 10 조회
		case "venSku": gridSelUrl ="<c:url value='/edi/main/selectMainInboundRejects.json'/>"; break;				//3. 파트너사 SKU 현황
		case "nonPayItem": gridSelUrl ="<c:url value='/edi/main/selectMainNonPayItems.json'/>"; break;				//5. 미납 내역
		case "inbRjtItem": gridSelUrl ="<c:url value='/edi/main/selectMainInboundRejects.json'/>"; break;			//6. 입고 거부 상품 미조치 내역
		case "prodNewProp": gridSelUrl ="<c:url value='/edi/main/selectProdNewPropList.json'/>"; break;				//7. 신상품 입점제안 조회
		case "newProdPrfr": gridSelUrl ="<c:url value='/edi/main/selectNewProdPrfrList.json'/>"; break;				//8. 신상품 실적 조회
		case "prodChgCostItem": gridSelUrl ="<c:url value='/edi/main/selectMainTpcProdChgCostItem.json'/>"; break;	//9. 원가 변경 요청 내역 조회
		default:break;
	}
	
	let searchInfo = (lastSearchInfo == undefined || lastSearchInfo == null)?{}:lastSearchInfo;
	searchInfo["page"] = page;
	searchInfo["rows"] = rows;
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : gridSelUrl,
		data : JSON.stringify(searchInfo),
		success: function (data) {
			//grid Data 셋팅
			fncSetGridData(data);
		}
	});
}

//grid load complete 공통 처리
function fncGridLoadCmplt(gridGbn, rows){
	let gridId = gridGbn + "GridList";
	let gridBlankRowId = gridGbn +"_blankRow";
	
	if(gridGbn == "venBuy"){		//2. 파트너사 매입액 top 10 조회
		var sumBuyAmt = $("#"+gridId).jqGrid("getCol", "buyAmt", false, "sum");
		var sumBuyQty = $("#"+gridId).jqGrid("getCol", "buyQty", false, "sum");
		var sumSalesAmt = $("#"+gridId).jqGrid("getCol", "salesAmt", false, "sum");
		var sumSalesQty = $("#"+gridId).jqGrid("getCol", "salesQty", false, "sum");
		
		//add Footer
		$("#"+gridId).jqGrid("footerData", "set", {buyAmt: sumBuyAmt, buyQty:sumBuyQty, salesAmt:sumSalesAmt, salesQty:sumSalesQty});
	}
}

//jqGrid Formatter_금액
function fmtGridAmt(cellvalue, options, rowdata){
	var val = parseFloat(cellvalue);
	var retVal = 0;
	if(!isNaN(val)) retVal = setComma(val);
	return retVal;						
}


//jqGrid unFormatter_숫자
function unfmtGridNumber(cellvalue, options, cell){
	return $.trim(cellvalue).replace(/\D/g,""); 
}

//jqGrid unFormatter_숫자(소수점포함)
function unfmtGridNumberPoint(cellvalue, options, cell){
	var retVal = $.trim(cellvalue).replace(/\D./g,"")
	retVal = (retVal == "")?0:Number(retVal);
	
	return retVal.toFixed(2); 
}

//팀별 대분류 조회
function _eventSelSrchL1List(val) {
	var paramInfo = {};
	
	paramInfo["groupCode"]	=	val;
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/NselectL1List.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.l1List;
			if (resultList.length > 0) {
				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].l1Cd+"'>"+resultList[i].l1Nm+"</option>"+"\n";
				}

				$("#l1Cd option").not("[value='']").remove();	//콤보박스 초기화
				$("#l1Cd").append(eleHtml.join(''));
			} else {
				$("#l1Cd option").not("[value='']").remove();	//콤보박스 초기화
			}
		}
	});
}

//팀-대분류 별 중분류 조회
function _eventSelectSrchL2List(groupCode, val) {
	var paramInfo = {};

	paramInfo["groupCode"]	=	groupCode;		//팀코드
	paramInfo["l1Cd"]		=	val;			//대븐류

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/NselectL2List.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.l2List;
			if (resultList.length > 0) {

				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].l2Cd+"'>"+resultList[i].l2Nm+"</option>"+"\n";
				}

				$("#l2Cd option").not("[value='']").remove();	//콤보박스 초기화
				$("#l2Cd").append(eleHtml.join(''));
			} else {
				$("#l2Cd option").not("[value='']").remove();	//콤보박스 초기화
			}
		}
	});
}


</script>

<!-- 파트너사 매입액 top 10 template -->
<script id="venBuyTemplate"  type="text/x-jquery-tmpl">
<tr bgcolor=ffffff>
	<td align="center"><c:out value="\${rownum}" /></td>
	<td align="center"><c:out value="\${l1Cd}" /></td>
	<td align="center"><c:out value="\${sellCd}" /></td>
	<td align="center"><c:out value="\${prodNm}" /></td>
	<td align="right"><c:out value="\${buyAmt}" /></td>
	<td align="right"><c:out value="\${buyQty}" /></td>
	<td align="right"><c:out value="\${salesAmt}" /></td>
	<td align="right"><c:out value="\${salesQty}" /></td>
	<input type="hidden" name="prodCd" value="<c:out value='\${prodCd}'/>"/>
</tr>
</script>

<!-- 신상품 실적 template -->
<script id="newProdPrfrTemplate"  type="text/x-jquery-tmpl">
<tr bgcolor=ffffff>
	<td align="center"><c:out value="\${dyFmt}" /></td>
	<td align="center" title="<c:out value="\${prodNm}" />"><c:out value="\${prodNm}" /></td>
	<td align="center"><c:out value="\${salesAmt}" /></td>
	<td align="center"><c:out value="\${prodProfit}" /></td>
	<td align="center"><c:out value="\${profitRate}" /></td>
	<td align="center"><c:out value="\${salesQty}" /></td>
	<input type="hidden" name="prodCd" value="<c:out value='\${prodCd}'/>"  />
</tr>
</script>

<!-- 신상품 입점 제안 template -->
<script id="prodNewPropTemplate"  type="text/x-jquery-tmpl">
<tr bgcolor=ffffff>
	<td align="center"><c:out value="\${rownum}" /></td>
	<td align="center"><c:out value="\${modDate}" /></td>
	<td align="center"><c:out value="\${prdtStatus}" /></td>
	<td align="center"><c:out value="\${regDate}" /></td>
	<td align="center"><c:out value="\${teamNm}" /></td>
	<td align="center"><c:out value="\${l1Nm}" /></td>
	<td align="center"><c:out value="\${venCd}" /></td>	
	<td align="center"><c:out value="\${prodStandardNm}" /></td>	
	<td align="right"><c:out value="\${norProdPcost}" /></td>	
	<td align="right"><c:out value="\${prodSellPrc}" /></td>	
</tr>
</script>

<!-- 원가변경 요청내역 및 상태 template -->
<script id="prodChgCostItemTemplate"  type="text/x-jquery-tmpl">
<tr bgcolor=ffffff>
	<td align="center"><c:out value="\${rownum}"/></td>
	<td align="center"><c:out value="\${l3Nm}"/></td>
	<td align="center"><c:out value="\${srcmkCd}"/></td>
	<td align="center"><c:out value="\${prodCd}"/></td>
	<td><c:out value="\${prodNm}"/></td>
	<td align="right"><c:out value="\${orgCost}"/></td>
	<td align="right"><c:out value="\${chgReqCost}"/></td>
	<td align="center"><c:out value="\${reqDt}"/></td>
	<td align="center"><c:out value="\${prStatNm}"/></td>
</tr>
</script>

<!-- 미납 내역 template -->
<script id="nonPayItemTemplate"  type="text/x-jquery-tmpl">
<tr bgcolor=ffffff>
	<td align="center"><c:out value="\${ordNo}" /></td>
	<td align="center"><c:out value="\${rcvDtFmt}" /></td>
	<td align="left"><c:out value="\${prodNm}" /></td>
	<td align="left"><c:out value="\${nonPayRsrn}" /></td>
	<td align="center"><c:out value="\${nonPaySt}" /></td>
	<td align="right"><c:out value="\${nonPayAmt}" /></td>
</tr>
</script>

<!-- 입고 거부 상품 미조치 내역 template -->
<script id="inbRjtItemTemplate"  type="text/x-jquery-tmpl">
<tr bgcolor=ffffff>
	<td align="center"><c:out value="\${dyFmt}" /></td>
	<td align="center"><c:out value="\${sellCd}" /></td>
	<td align="center"><c:out value="\${prodCd}" /></td>
	<td align="center"><c:out value="\${prodNm}" /></td>
	<td align="center"><c:out value="\${isuStore}" /></td>
	<td align="center"><c:out value="\${isuType}" /></td>
</tr>
</script>



</head>
<body style="overflow-x:hidden;">
	<div id="content_wrap" style="width:99%;">
	    <form id="searchForm" name="searchForm" method="post" action="#">
	    	<div id="wrap_menu">
	    		<div class="wrap_search">
	    			<div class="bbs_search">
	    				<ul class="tit">
							<li class="tit">검색조건</li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							</li>
						</ul>
						
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:10%;" />
								<col style="width:15%;" />
								<col style="width:10%;" />
								<col style="width:15%;" />
								<col style="width:10%;" />
								<col style="width:15%;" />
								<col style="width:10%;" />
								<col style="width:15%;" />
								<col style="width:10%;" />
								<col style="width:15%;" />
								<col style="width:10%;" />
								<col style="width:25%;" />
							</colgroup>
							<tr>
								<th><span class="star">*</span>파트너사</th>
								<td>
									<html:codeTag objId="venCd" objName="venCd" width="90%;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
								</td>
								<th><span class="star">*</span>채널</th>
								<td>
									<html:codeTag objId="chanCd" objName="chanCd" width="90%;"  parentCode="PURDE"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form" childCode="1"/>
								</td>
								<th><span class="star">*</span>팀</th>
								<td>
									<select id="teamCd" name="teamCd" class="required" style="width:90%;">
										<option value=""><spring:message code="button.common.choice" /></option>
										<c:forEach items="${teamList}" var="teamList" varStatus="index">
											<option value="<c:out value='${teamList.teamCd}'/>"><c:out value="${teamList.teamNm}"/></option>
										</c:forEach>
									</select>
								</td>
								<th><span class="star">*</span>대분류</th>
								<td>
									<select id="l1Cd" name="l1Cd" class="required" style="width:90%;">
										<option value="" >필수선택</option>
									</select>
								</td>
								<th><span class="star">*</span>중분류</th>
								<td>
									<select id="l2Cd" name="l2Cd" class="required" style="width:90%;">
										<option value="" >필수선택</option>
									</select>
								</td>
								<th><span class="star">*</span>기간</th>
								<td>
									<input type="text" class="day" name="startDy" id="startDy" style="width:80px;" value="<c:out value='${srchFromDt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
									~
									<input type="text" class="day" name="endDy" id="endDy" style="width:80px;" value="<c:out value='${today}'/>" disabled> 	<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker"  style="cursor:hand;" />
								</td>
							</tr>
						</table>
	    			</div>
	    		</div>
	    	</div>
	    </form>
	    
	    <!-- Chart 영역 Start ----------------------------------->
	    <div id="mainContents" class="wrap_con">
	    	<div class="line col3">
	    		<div class="con-tit">
	    			<div class="badge">기간</div>
	    			<div class="in-tit">파트너사 실적(매입액, 순매출액, 매출량, 신장률)</div>
	    		</div>
		    	<div id="venPrfrChart" class="con-chart-body con-echart"></div>
		    </div>
		    
		    <div class="line col3">
	    		<div class="con-tit">
	    			<div class="badge">기간</div>
	    			<div class="in-tit">기간별 미납율</div>
	    		</div>
		    	<div class="con-chart-body">
		    		<div color="red" style="height: 100%;text-align: center;color: red;">
		    			개발 필요<br>
		    			개발 진행 시, 해당 div 삭제 해주세요.
	    			</div>
		    	</div>
		    </div>
		    
		    <div class="line col3">
	    		<div class="con-tit">
	    			<div class="badge">실시간</div>
	    			<div class="in-tit">신상품 입점 제안 List</div>
	    		</div>
	    		<div class="con-body grid">
		    		<!-- grid List -->
					<div class="gridcontainer">
						<table id="prodNewPropGridList">
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="prodNewPropPageList"></div>
					<!-- ./grid List -->
		    	</div>
		    	<%--
		    	<div class="con-body">
		    		<table id="prodNewPropTable" class="tb-sticky" cellpadding="1" cellspacing="1" border="0" width="700" bgcolor="#efefef">
						<colgroup>
							<col width="30px"/>
							<col width="80px"/>
							<col width="80px"/>
							<col width="80px"/>
							<col width="120px"/>
							<col width="120px"/>
							<col width="80px"/>
							<col width="80px"/>
							<col width="80px"/>
							<col width="80px"/>
						</colgroup>
						<thead>
							<tr>
								<th>No</th>
								<th>상태변경일</th>
								<th>상태</th>
								<th>등록일자</th>
								<th>팀</th>
								<th>대분류</th>
								<th>파트너사</th>
								<th>규격</th>
								<th>원가</th>
								<th>판매가</th>
							</tr>
						</thead>
						<tbody id="prodNewPropTbody"/>
					</table>
		    	</div>
		    	--%>
		    </div>
		    
		    <div class="line col3">
		    	<div class="con-tit">
	    			<div class="badge">기간</div>
	    			<div class="in-tit">파트너사 매입액 Top 10 SKU</div>
	    		</div>
	    		<div class="con-body grid">
		    		<!-- grid List -->
					<div class="gridcontainer">
						<table id="venBuyGridList">
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<!-- <div id="venBuyPageList"></div> -->
					<!-- ./grid List -->
		    	</div>
		    	<%-- 
	    		<div class="con-body">
					<table id="venBuyTable" class="tb-sticky" cellpadding="1" cellspacing="1" border="0" width="700" bgcolor="#efefef">
						<colgroup>
							<col width="30px"/>
							<col width="50px"/>
							<col width="80px"/>
							<col width="150px"/>
							<col width="50px"/>
							<col width="50px"/>
							<col width="50px"/>
							<col width="50px"/>
						</colgroup>
						<thead>
							<tr>
								<th>순위</th>
								<th>분류명</th>
								<th>판매코드</th>
								<th>상품명</th>
								<th>매입액</th>
								<th>매입량</th>
								<th>매출액</th>
								<th>매출량</th>
							</tr>
						</thead>
						<tbody id="venBuyTbody"/>
					</table>
					<div class="con-body-tb-footer">
						<table id="venBuyFooterTable" class="tb-sticky" cellpadding="1" cellspacing="1" border="0" width="700" bgcolor="#efefef">
							<colgroup>
								<col width="30px">
								<col width="50px">
								<col width="80px">
								<col width="150px">
								<col width="50px">
								<col width="50px">
								<col width="50px">
								<col width="50px">
							</colgroup>
							<tbody id="venBuyTfooter">
								<tr bgcolor="ffffff">
									<td colspan="4" align="center" style="background:#2c3442;">
										<span style="font-weight:bold;color:#ffffff;">Total (합계)</span>
									</td>
									<td align="right" style="background:#dfdfdf;font-weight:bold;"><span class="amt" id="footer_buyAmt">0</span></td>
									<td align="right" style="background:#dfdfdf;font-weight:bold;"><span class="amt" id="footer_buyQty">0</span></td>
									<td align="right" style="background:#dfdfdf;font-weight:bold;"><span class="amt" id="footer_salesAmt">0</span></td>
									<td align="right" style="background:#dfdfdf;font-weight:bold;"><span class="amt" id="footer_salesQty">0</span></td>
								</tr>
							</tbody>
                    	</table>
                    </div>
				</div>
				--%>
		    </div>
		    
		    <div class="line col3">
	    		<div class="con-tit">
	    			<div class="badge">월간</div>
	    			<div class="in-tit">미납 내역</div>
	    		</div>
	    		<div class="con-header">
					<div class="con-inner">
						<div class="btnl">
							<span class="badge orange">미납율 : <span id="nonPayItemRate">0</span> %</span>
						</div>
					</div>
				</div>
				<div class="con-body grid">
		    		<!-- grid List -->
					<div class="gridcontainer">
						<table id="nonPayItemGridList">
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="nonPayItemPageList"></div>
					<!-- ./grid List -->
		    	</div>
	    		<%-- 
		    	<div class="con-body">
			    	<table id="nonPayItemTable" class="tb-sticky" cellpadding="1" cellspacing="1" border="0" width="700" bgcolor=efefef>
						<colgroup>
							<col width="100px">
							<col width="80px">
							<col width="150px">
							<col width="150px">
							<col width="80px">
							<col width="80px">
						</colgroup>
						<thead>
							<tr>
								<th>발주번호</th>
								<th>입고예정일</th>
								<th>상품명</th>
								<th>미납사유</th>
								<th>귀책</th>
								<th>미납금액</th>
							</tr>
						</thead>
						<tbody id="nonPayItemTbody"/>
					</table>
				</div>
				--%>
		    </div>
		    
		    <div class="line col3">
		    	<div class="con-tit">
	    			<div class="badge">6개월 ~ 당월</div>
	    			<div class="in-tit">신상품 실적 (6개월)</div>
	    		</div>
	    		<div class="con-body grid">
		    		<!-- grid List -->
					<div class="gridcontainer">
						<table id="newProdPrfrGridList">
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="newProdPrfrPageList"></div>
					<!-- ./grid List -->
		    	</div>
		    	<%--
		    	<div class="con-body">
			    	<table id="newProdPrfrTable" class="type01 tb-sticky" cellpadding="1" cellspacing="1" border="0" width="700" bgcolor=efefef>
						<colgroup>
							<col width="50px"/>
							<col width="80px"/>
							<col width="50px"/>
							<col width="50px"/>
							<col width="50px"/>
							<col width="50px"/>
						</colgroup>
						<thead>
							<tr>
								<th>입점일</th>
								<th>상품명</th>
								<th>순매출액</th>
								<th>상품이익</th>
								<th>이익률</th>
								<th>판매량</th>
							</tr>
						</thead>
						<tbody id="newProdPrfrTbody"/>
					</table>
				</div>
				--%>
				<div class="con-footer">
					<div class="con-inner">
						※ 조회일 기준 신상품 입점 SKU : <span id="skuCnt">00</span>&nbsp;SKU
					</div>
				</div>
		    </div>
		    
		    <div class="line col3">
	    		<div class="con-tit">
	    			<div class="badge">기간</div>
	    			<div class="in-tit">파트너사 SKU 현황(필터별)</div>
	    		</div>
	    		<div class="con-header">
					<div class="con-inner">
						신상품 : 2 SKU / 판매중 : 31 SKU / 발주중단 4 SKU / 판매중단 : 2 SKU
					</div>
				</div>
		    	<div class="con-body grid">
		    		<!-- grid List -->
					<div class="gridcontainer">
						<table id="venSkuGridList">
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="venSkuPageList"></div>
					<!-- ./grid List -->
		    	</div>
		    </div>
		    
		    <div class="line col3">
	    		<div class="con-tit">
	    			<div class="badge">당월</div>
	    			<div class="in-tit">입고 거부 상품 미조치 내역</div>
	    		</div>
	    		<div class="con-header">
					<div class="con-inner">
						<div class="btnl">
							<span class="badge orange">거부율 : <span id="inbRjtItemRate">0</span> %</span>
						</div>
					</div>
				</div>
				<div class="con-body grid">
		    		<!-- grid List -->
					<div class="gridcontainer">
						<table id="inbRjtItemGridList">
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="inbRjtItemPageList"></div>
					<!-- ./grid List -->
		    	</div>
		    	<%-- 
		    	<div class="con-body">
			    	<table id="inbRjtItemTable" class="tb-sticky" cellpadding="1" cellspacing="1" border="0" width="700" bgcolor=efefef>
						<colgroup>
							<col width="80px">
							<col width="80px">
							<col width="80px">
							<col width="150px">
							<col width="120px">
							<col width="120px">
						</colgroup>
						<thead>
							<tr>
								<th>일자</th>
								<th>판매코드</th>
								<th>상품코드</th>
								<th>상품명</th>
								<th>발생지점</th>
								<th>발생유형</th>
							</tr>
						</thead>
						<tbody id="inbRjtItemTbody"/>
					</table>
				</div>
				--%>
		    </div>
		    
		    <div class="line col3">
	    		<div class="con-tit">
	    			<div class="badge">기간</div>
	    			<div class="in-tit">원가 변경 요청 내역 및 상태</div>
	    		</div>
	    		<div class="con-body grid">
		    		<!-- grid List -->
					<div class="gridcontainer">
						<table id="prodChgCostItemGridList">
							<tr>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="prodChgCostItemPageList"></div>
					<!-- ./grid List -->
		    	</div>
		    	<%-- 
		    	<div class="con-body">
		    		<table id="prodChgCostItemTable" class="tb-sticky" cellpadding="1" cellspacing="1" border="0" width="1200" bgcolor=efefef>
						<colgroup>
							<col width="5%"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="25%"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="10%"/>
							<col width="10%"/>
						</colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>분류명</th>
								<th>판매코드</th>
								<th>상품코드</th>
								<th>상품명</th>
								<th>기존원가</th>
								<th>변경원가</th>
								<th>요청일시</th>
								<th>승인여부</th>
							</tr>
						</thead>
						<tbody id="prodChgCostItemTbody"/>
					</table>
		    	</div>
		    	--%>
		    </div>
	    </div>
	    <!-- Chart 영역 End ----------------------------------->
    </div>
</body>
</html>