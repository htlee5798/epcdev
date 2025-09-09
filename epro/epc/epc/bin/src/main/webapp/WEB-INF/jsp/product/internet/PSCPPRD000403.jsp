<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lottemart.epc.common.util.SecureUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
	String tabNo  = "3";
	String themaSeq = request.getParameter("themaSeq");
	String notInVal = request.getParameter("notInVal");
	String prodDivnCd = request.getParameter("prodDivnCd");
	String onlineProdTypeCd = request.getParameter("onlineProdTypeCd");
	String ecLinkYn = request.getParameter("ecLinkYn");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />
<script language="javascript" type="text/javascript">

	var vndId = "";

	$(document).ready(function() {

		initIBSheetGrid2(); // 그리드 초기화

		var themaObj = opener.themaProdOneToJson($("#themaSeq").val());
		if (themaObj != null) {
			if (themaObj.themaProd != null && themaObj.themaProd.length > 0) {
				for (t = 0; t < themaObj.themaProd.length; t++) {
					var rowIdx = mySheet2.DataInsert(-1);
					mySheet2.SetCellValue(rowIdx, "PROD_CD", themaObj.themaProd[t].prodCd);
					mySheet2.SetCellValue(rowIdx, "ASSO_CD", themaObj.themaProd[t].assoCd);
					mySheet2.SetCellValue(rowIdx, "PROD_NM", themaObj.themaProd[t].prodNm);
					mySheet2.SetCellValue(rowIdx, "APRV_YN", themaObj.themaProd[t].aprvYn);
					mySheet2.SetCellValue(rowIdx, "CURR_SELL_PRC", themaObj.themaProd[t].currSellPrc);
					mySheet2.SetCellValue(rowIdx, "MAIN_PROD_YN", themaObj.themaProd[t].mainProdYn);
					mySheet2.SetCellValue(rowIdx, "REP_YN", themaObj.themaProd[t].repYn);
					mySheet2.SetCellValue(rowIdx, "SELL_FLAG", themaObj.themaProd[t].sellFlag);
					mySheet2.SetCellValue(rowIdx, "STOCK_QTY", themaObj.themaProd[t].stockQty);
					mySheet2.SetCellValue(rowIdx, "BATCH_ORDER_SEQ", themaObj.themaProd[t].batchOrderSeq);
				}
			}
		}

		$("#prodAdd").click(function() {
			productAdd();
		});
		$("#prodDel").click(function() {
			productDel();
		});
		$("#prodAppry").click(function() {
			productAppry();
		});
	
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
		  , {Header:"딜상품코드",	Type:"Text",			SaveName:"PROD_CD",			Align:"Center",	Width:100,	Edit:0,	Hidden:true} //
		  , {Header:"인터넷상품코드",	Type:"Text",		SaveName:"ASSO_CD",			Align:"Center",	Width:100,	Edit:0}
		  , {Header:"상품명",			Type:"Text",		SaveName:"PROD_NM",			Align:"Left",	Width:380,	Edit:0}
		  , {Header:"승인여부",		Type:"Text", 		SaveName:"APRV_YN", 		Align:"Center",	Width:80,	Edit:0}
		  , {Header:"우선순위",		Type:"Int", 		SaveName:"BATCH_ORDER_SEQ", 	Align:"Center",	Width:80,	Edit:1,	MinimumValue:1,	 Format:'Integer'}
		  , {Header:"우선순위ORG",		Type:"Int", 		SaveName:"ORG_BATCH_ORDER_SEQ", Align:"Center",	Width:40,	Edit:0,	Hidden:true} //
		  , {Header:"메인상품여부",		Type:"Combo",		SaveName:"MAIN_PROD_YN",	Align:"Center",	Width:80,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N"}
		  , {Header:"대표상품여부",		Type:"Combo",		SaveName:"REP_YN",			Align:"Center",	Width:80,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N"}
		  , {Header:"판매상태",		Type:"Text", 		SaveName:"SELL_FLAG",		Align:"Center",	Width:80,	Edit:0,	Hidden:true} //
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
		}

		// notInVal은 호출 팝업에서 전체에 대한 assoCd를 넘겨받고 추가된것이 있으면 중복 제거하여 조회sql에 넘겨야한다.
		var notInVal = notInValRefresh();
		var onlineProdTypeCd = "etc"; //상품팝업 조회 쿼리에 isEmpty시 조건이 추가되지 않게 하기 위해서(AND A.ONLINE_PROD_TYPE_CD = '01')
		var targetUrl = "<c:url value='/common/viewPopupProductList.do'/>?vendorId=<%=vendorId%>&notInVal=" + notInVal + "&prodDivnCd=<%=prodDivnCd%>&onlineProdTypeCd=<%=onlineProdTypeCd%>&ecLinkYn=&dealProdYn=N"; // 01:상품
		Common.centerPopupWindow(targetUrl, 'prd', {width : 910, height : 550});
	}

	function notInValRefresh() {
		var rtnNotInVal = "";
		var tmpNotInVal = $("#notInVal").val();
		var tmpArr = tmpNotInVal.split(",");

		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			tmpArr.push(mySheet2.GetCellValue(i, "ASSO_CD"));
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

			mySheet2.SetCellValue(rowIdx, "PROD_CD", "<%=prodCd%>");
			mySheet2.SetCellValue(rowIdx, "ASSO_CD", rtnVal.prodCdArr[i]);
			mySheet2.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);
			mySheet2.SetCellValue(rowIdx, "CURR_SELL_PRC", rtnVal.currSellPrcArr[i]);
			mySheet2.SetCellValue(rowIdx, "MAIN_PROD_YN", "N");
			mySheet2.SetCellValue(rowIdx, "REP_YN", "N");
			mySheet2.SetCellValue(rowIdx, "SELL_FLAG", rtnVal.sellFlagArr[i]);
			mySheet2.SetCellValue(rowIdx, "APRV_YN", rtnVal.aprvYnArr[i]);
			//mySheet2.SetCellValue(rowIdx, "STOCK_QTY", rtnVal.stockQtyArr[i]);
			mySheet2.SetCellValue(rowIdx, "BATCH_ORDER_SEQ", (mySheet2.RowCount() - 1) + 1);
		}
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

		chkRow = chkRow.substring(1, chkRow.length);
		mySheet2.RowDelete(chkRow);
	}

	function productAppry() {
		
		var mainProdYnCnt = 0;
		var repYnCnt = 0;
		var batchOrderSeqArr = new Array();
		var chkBatchOrderSeqCnt = 0;

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

			if (mySheet2.GetCellValue(i, "BATCH_ORDER_SEQ") == "") {
				chkBatchOrderSeqCnt++;
			} else {
				batchOrderSeqArr.push(mySheet2.GetCellValue(i, "BATCH_ORDER_SEQ"));
			}
		}

		var batchOrderSeqSet = new Set(batchOrderSeqArr);
		if (batchOrderSeqArr.length > batchOrderSeqSet.size) {
			alert("중복된 우선순위가 있습니다. 확인 후 수정해주세요.");
			return;
		}

		if (chkBatchOrderSeqCnt > 0) {
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
			dealProd.assoCd = mySheet2.GetCellValue(i,"ASSO_CD");
			dealProd.mainProdYn = mySheet2.GetCellValue(i,"MAIN_PROD_YN");
			dealProd.repYn = mySheet2.GetCellValue(i,"REP_YN");
			dealProd.batchOrderSeq = mySheet2.GetCellValue(i,"BATCH_ORDER_SEQ");
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
<form name="dataForm" id="dataForm">
<input type="hidden" name="themaSeq" id="themaSeq" value="<%=themaSeq%>" />
<div id="content_wrap" style="width:950px; height:300px;">
	<div id="wrap_menu" style="width:950px">
		<!--	@ 검색조건  -->
		<!-- 상품 상세보기 하단 탭 -->
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">상품구성정보
						<input type="hidden" name="prodLinkKindCd" id="prodLinkKindCd" value="04" /><%-- 04: 딜상품 --%>
					</li>
					<li class="btn">
						<a href="#" class="btn" id="prodAdd"><span>추가</span></a>
						<a href="#" class="btn" id="prodDel"><span>삭제</span></a>
						<a href="#" class="btn" id="prodAppry"><span>적용</span></a>
					</li>
				</ul>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><div id="ibsheet2" style="display:block;"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
</form>
<form name="form1" id="form1">
	<input type="hidden" name="vendorId" id="vendorId" value="<%=vendorId%>" />
	<input type="hidden" name="prodCd" id="prodCd" value="<%=prodCd%>" />
	<input type="hidden" name="notInVal" id="notInVal" value="<%=notInVal %>" />
</form>
</body>
</html>