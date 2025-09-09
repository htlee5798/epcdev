<%--
- Author(s): [LMREQ-2994] 롯데on 묶음코드(기획전형상품) 운영 개선 관련 대응 요청
- Created Date: 2022. 07. 18
- Version : 1.0
- Description : 딜상품 테마 내 상품 등록용 팝업
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String vendorId = request.getParameter("vendorId");
	String notInVal = request.getParameter("notInVal");
	String prodDivnCd = request.getParameter("prodDivnCd");
	String onlineProdTypeCd = request.getParameter("onlineProdTypeCd");
	String themaSeq = request.getParameter("themaSeq");
	String ecLinkYn = request.getParameter("ecLinkYn");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<meta http-equiv="Cache-Control" content="no-store"/>
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache"/>
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>LOTTE MART Back Office System</title>
<c:import url="/common/commonHead.do" />
<script language="javascript" type="text/javascript">

	$(document).ready(function() {

		initIBSheetGrid2(); // 그리드 초기화

		//if ($("#pgmId").val() == "") {
		var themaObj = opener.themaProdOneToJson($("#themaSeq").val());
		if (themaObj != null) {
			if (themaObj.themaProd != null && themaObj.themaProd.length > 0) {
				for (t = 0; t < themaObj.themaProd.length; t++) {
					var rowIdx = mySheet2.DataInsert(-1);
					mySheet2.SetCellValue(rowIdx, "PROD_CD", themaObj.themaProd[t].prodCd);
					mySheet2.SetCellValue(rowIdx, "PROD_NM", themaObj.themaProd[t].prodNm);
					mySheet2.SetCellValue(rowIdx, "APRV_YN", themaObj.themaProd[t].aprvYn);
					mySheet2.SetCellValue(rowIdx, "CURR_SELL_PRC", themaObj.themaProd[t].currSellPrc);
					mySheet2.SetCellValue(rowIdx, "MAIN_PROD_YN", themaObj.themaProd[t].mainProdYn);
					mySheet2.SetCellValue(rowIdx, "REP_YN", themaObj.themaProd[t].repYn);
					mySheet2.SetCellValue(rowIdx, "SELL_FLAG", themaObj.themaProd[t].sellFlag);
					mySheet2.SetCellValue(rowIdx, "STOCK_QTY", themaObj.themaProd[t].stockQty);
					mySheet2.SetCellValue(rowIdx, "ORDER_SEQ", themaObj.themaProd[t].orderSeq);
				}
			}
		}
		//}
	});

	var themaList = new Array(); // 테마 리스트

	function initIBSheetGrid2() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "250px");
		mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet2.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
		    {Header:"",				Type:"CheckBox",	SaveName:"SELECTED",		Align:"Center",	Width:30,			Sort:false}
		  , {Header:"인터넷상품코드",	Type:"Text",		SaveName:"PROD_CD",			Align:"Center",	Width:100,	Edit:0}
		  , {Header:"상품명",			Type:"Text",		SaveName:"PROD_NM",			Align:"Left",	Width:360,	Edit:0}
		  , {Header:"승인여부",		Type:"Text", 		SaveName:"APRV_YN", 		Align:"Center",	Width:80,	Edit:0}
		  , {Header:"우선순위",		Type:"Int", 		SaveName:"ORDER_SEQ", 		Align:"Center",	Width:80,	Edit:1,	MinimumValue:1,	 Format:'Integer'}
		  , {Header:"우선순위ORG",		Type:"Int", 		SaveName:"ORG_ORDER_SEQ", 	Align:"Center",	Width:40,	Edit:0,	Hidden:true} //
		  , {Header:"메인상품여부",		Type:"Combo",		SaveName:"MAIN_PROD_YN",	Align:"Center",	Width:80,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N"}
		  , {Header:"대표상품여부",		Type:"Combo",		SaveName:"REP_YN",			Align:"Center",	Width:80,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N"}
		  , {Header:"판매상태",		Type:"Text", 		SaveName:"SELL_FLAG",		Align:"Center",	Width:80,	Edit:0,	Hidden:true}
		  , {Header:"판매가",			Type:"Text", 		SaveName:"CURR_SELL_PRC",	Align:"Right",	Width:80,	Edit:0}
		  <%--, {Header:"재고수량",		Type:"Text", 		SaveName:"STOCK_QTY",		Align:"Center",	Width:80,	Edit:0}--%>
		];

		IBS_InitSheet(mySheet2, ibdata);
		mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바 비활성
		mySheet2.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet2.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
	}

	/* 상품추가 */
	function productAdd() {

		if (mySheet2.RowCount() > 100) {
			alert("묶음상품을 100개를 초과하여 설정 할 수 없습니다.");
			return;
		} else if ($("#productCnt").val() < 0 || $("#productCnt").val() == null || $("#productCnt").val() == "") {
			/* 상품 count 초기화*/
			$("#productCnt").val(mySheet2.RowCount());
		}

		/* 상품 count 초기화*/
		var chkCnt = $("#productCnt").val();
		if (chkCnt > 100){
			alert("묶음상품을 100개를 초과하여 설정 할 수 없습니다.");
			return;
		}

		var notInVal = notInValRefresh();
		var prodDivnCd = $("#dealProdDivnCode option:selected").val();
		var onlineProdTypeCd = "etc"; //상품팝업 조회 쿼리에 isEmpty시 조건이 추가되지 않게 하기 위해서(AND A.ONLINE_PROD_TYPE_CD = '01')

		var targetUrl = "<c:url value='/common/viewPopupProductList.do'/>?vendorId=<%=vendorId%>&notInVal=" + notInVal + "&prodDivnCd=<%=prodDivnCd%>&onlineProdTypeCd=<%=onlineProdTypeCd%>&ecLinkYn="+"&dealProdYn=N"; // 01:상품

		Common.centerPopupWindow(targetUrl, 'prd', {width : 910, height : 550});
	}

	function notInValRefresh() {
		var rtnNotInVal = "";
		var tmpNotInVal = $("#notInVal").val();
		var tmpArr = tmpNotInVal.split(",");

		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			tmpArr.push(mySheet2.GetCellValue(i, "PROD_CD"));
		}
		var tmpSet = new Set(tmpArr);
		var tmpArr2 = Array.from(tmpSet);
		for (var j = 0; j < tmpArr2.length; j++) {
			rtnNotInVal += "," + tmpArr2[j];
		}
		if (rtnNotInVal != "") {
			rtnNotInVal = rtnNotInVal.substring(1, rtnNotInVal.length);
		}
		console.log("rtnNotInVal = " + rtnNotInVal);
		return rtnNotInVal;
	}

	function popupReturn(rtnVal) {
		for (var i = 0; i < rtnVal.prodCdArr.length; i++) {
			var rowIdx = mySheet2.DataInsert(-1);

			mySheet2.SetCellValue(rowIdx, "PROD_CD", rtnVal.prodCdArr[i]);
			mySheet2.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);
			mySheet2.SetCellValue(rowIdx, "CURR_SELL_PRC", rtnVal.currSellPrcArr[i]);
			mySheet2.SetCellValue(rowIdx, "MAIN_PROD_YN", "N");
			mySheet2.SetCellValue(rowIdx, "REP_YN", "N");
			mySheet2.SetCellValue(rowIdx, "SELL_FLAG", rtnVal.sellFlagArr[i]);
			mySheet2.SetCellValue(rowIdx, "APRV_YN", rtnVal.aprvYnArr[i]);
			//mySheet2.SetCellValue(rowIdx, "STOCK_QTY", rtnVal.stockQtyArr[i]);
			mySheet2.SetCellValue(rowIdx, "ORDER_SEQ", (mySheet2.RowCount() - 1) + 1);
		}

		/*적용 상품할당수량*/
		$("#productCnt").val(mySheet2.RowCount());
	}

	/* 상품 삭제*/
	function productDel() {
		var chkRow = "";
		var chkCnt = 0;
		var sum = 0; 

		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			if (mySheet2.GetCellValue(i,"SELECTED") == 1) {
				chkRow += "|"+i;
				chkCnt++;
			}
		}

		sum = $("#productCnt").val() - chkCnt;
		$("#productCnt").val(sum);

		chkRow = chkRow.substring(1,chkRow.length);
		mySheet2.RowDelete(chkRow);
	}

	function productAppry() {
		
		var mainProdYnCnt = 0;
		var repYnCnt = 0;
		var orderSeqArr = new Array();
		var chkOrderSeqCnt = 0;

		if (mySheet2.RowCount() > 100) {
			alert("묶음상품을 100개를 초과하여 설정 할 수 없습니다.");
			return;
		}

		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			if (mySheet2.GetCellValue(i,"MAIN_PROD_YN") == "Y") {
				mainProdYnCnt++;
			}
			if (mySheet2.GetCellValue(i,"REP_YN") == "Y") {
				repYnCnt++;
			}

			if (mySheet2.GetCellValue(i, "ORDER_SEQ") == "") {
				chkOrderSeqCnt++;
			} else {
				orderSeqArr.push(mySheet2.GetCellValue(i, "ORDER_SEQ"));
			}
		}

		var orderSeqSet = new Set(orderSeqArr);
		if (orderSeqArr.length > orderSeqSet.size) {
			alert("중복된 우선순위가 있습니다. 확인 후 수정해주세요.");
			return;
		}

		if (chkOrderSeqCnt > 0) {
			alert("우선순위를 지정해주세요.");
			return;
		}

		if (mainProdYnCnt > 1) {
			alert("메인상품은 1개만 지정 가능합니다.");
			return;
		}
		if (repYnCnt > 1) {
			alert("대표상품은 1개만 지정 가능합니다.");
			return;
		}

		opener.themaProdApply(themaProdToJson());
		window.close();
	}

	function themaProdToJson() {
		var dealObj = new Object();
		var dealProdList = new Array();

		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			var dealProd = new Object(); // 테마 내 상품

			dealProd.prodCd = mySheet2.GetCellValue(i,"PROD_CD");
			dealProd.mainProdYn = mySheet2.GetCellValue(i,"MAIN_PROD_YN");
			dealProd.repYn = mySheet2.GetCellValue(i,"REP_YN");
			dealProd.orderSeq = mySheet2.GetCellValue(i,"ORDER_SEQ");
			dealProd.prodNm = mySheet2.GetCellValue(i,"PROD_NM");
			dealProd.aprvYn = mySheet2.GetCellValue(i,"APRV_YN");

			dealProd.sellFlag = mySheet2.GetCellValue(i,"SELL_FLAG");
			dealProd.currSellPrc = mySheet2.GetCellValue(i,"CURR_SELL_PRC");
			//dealProd.stockQty = mySheet2.GetCellValue(i,"STOCK_QTY");

			dealProdList.push(dealProd);
		}
		dealObj.dealProdList = dealProdList;
		dealObj.themaSeq = $("#themaSeq").val();
		//console.log(JSON.stringify(themaProdList));
		//console.log(JSON.stringify(dealObj));
		return JSON.stringify(dealObj);
	}

</script>
</head>
<body>
	<form name="newProduct" id="newProduct" method="post">
	<input type="hidden" name="notInVal" id="notInVal" value="<%=notInVal %>" />
	<div id="popup">
		<div class="popup_contents">
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit" style="font-size:11px;">상품구성 기획전형 상품의 가격, 고시정보는 대표상품의 정보를 보여줍니다. 서비스 후 대표상품은 변경할 수 없습니다. </li>
					<li class="btn">
						<a href="#" class="btn" onclick="javascript:productDel();"><span>삭제</span></a>
						<a href="#" class="btn" onclick="javascript:productAdd();"><span>추가</span></a>
						<a href="#" class="btn" onclick="javascript:productAppry();"><span>적용</span></a>
						<input type="hidden" id="grpProduct" name="grpProduct" />
						<input type="hidden" id="repYn" name="repYn" />
						<input type="hidden" id="orderSeq" name="orderSeq" />
					</li>
				</ul>
			</div>
			<div id="ibsheet2"></div>
			<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${param.pgmId}'/>" />
			<input type="hidden" name="prodDivnCd" id="prodDivnCd" />
			<input type="hidden" name="onOffDivnCd" id="onOffDivnCd" value="<c:out value='${newProdDetailInfo.onoffDivnCd}'/>" />
			<input type="hidden" name="uploadFieldCount" id="uploadFieldCount" value="1"/>
			<input type="hidden" name="themaSeq" id="themaSeq" value="<%=themaSeq%>" />
			<input type="hidden" name="productCnt" id="productCnt" value="" />
		</div>
	</form>
</body>
</html>
