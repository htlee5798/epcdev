<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.lottemart.epc.common.util.SecureUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
	String tabNo = "3";
	String themaYn = request.getParameter("themaYn");
	String prodDivnCd = request.getParameter("prodDivnCd");
	String onlineProdTypeCd = request.getParameter("onlineProdTypeCd");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />
<script language="javascript" type="text/javascript">

	//var initThemaYn = "<%=themaYn%>";

	$(document).ready(function() {

		initIBSheetGrid(); // 그리드 초기화
		initIBSheetGrid2(); // 그리드 초기화

		//input enter 막기
		$("*").keypress(function(e) {
			if (e.keyCode == 13) return false;
		});
		$("#prodSort1").click(function() {
			doProdSort1();
		});
		$("#search1").click(function() {
			doSearch1();
		});
		$("#prodAdd1").click(function() {
			popupInsert1();
		});
		$("#update1").click(function() {
			doUpdate1();
		});
		<%--$('#delete').click(function() {
			doDelete();
		});--%>

		$("#search2").click(function() {
			doSearch2();
		});

		$("#addRow2").click(function() {
			addThemaRow2();
		});

		$("#removeRow2").click(function() {
			removeThemaRow2();
		});

		$("#save2").click(function() {
			doSave2();
		});

		$("input:radio[name=themaYn]").change(function() {

			//$("input:radio[name=themaYn][value='Y']").prop("checked", true);

			if (!confirm("테마사용여부 변경 후 저장시 이전 데이터는 사라집니다. 계속하시겠습니까?")) {
				if ($("input:radio[name=themaYn][value='Y']").prop("checked")) {
					$("input:radio[name=themaYn][value='N']").prop("checked", true);
				} else {
					$("input:radio[name=themaYn][value='Y']").prop("checked", true);
				}
				return;
			} else {
				themaYnChange(this.value);
			}
		});

		themaYnChange($("input:radio[name='themaYn']:checked").val());
	});

	function initIBSheetGrid() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번",		Type:"Int", 		SaveName:"num", 			Align:"Center",	Width:40,	Height:18, Edit:0}
		  , {Header:"", 		Type:"CheckBox",	SaveName:"CHK", 			Align:"Center",	Width:28,	Sort:false} // default=1(수정가능)
		  , {Header:"상품코드",	Type:"Text", 		SaveName:"prodCd", 			Align:"Center",	Width:100,	Edit:0,	Hidden:true} //
		  , {Header:"판매코드", 	Type:"Text", 		SaveName:"assoCd", 			Align:"Center",	Width:105,	Edit:0} // 수정가능
		  , {Header:"상품명", 		Type:"Text", 		SaveName:"prodNm", 			Align:"Left",	Width:270,	Edit:0}
		  , {Header:"원가", 		Type:"Int", 		SaveName:"buyPrc",			Align:"Right",	Width:70,	Edit:0}
		  , {Header:"매가", 		Type:"Int", 		SaveName:"sellPrc", 		Align:"Right",	Width:70,	Edit:0}
		  , {Header:"판매가", 		Type:"Int", 		SaveName:"currSellPrc", 	Align:"Right",	Width:70,	Edit:0}
		  , {Header:"전시여부", 	Type:"Combo",		SaveName:"dispYn", 			Align:"Center",	Width:65,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N"}
		  , {Header:"이미지", 		Type:"Image",		SaveName:"imgPath", 		Align:"Center",	Width:90, 	Edit:0}
		  , {Header:"대표상품여부", 	Type:"Combo",		SaveName:"repYn", 			Align:"Center",	Width:80,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N"}
		  , {Header:"메인상품여부", 	Type:"Combo",		SaveName:"mainProdYn", 		Align:"Center",	Width:65,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N", Hidden:true} //
		  , {Header:"우선순위", 	Type:"Int", 		SaveName:"batchOrderSeq", 	Align:"Center",	Width:70,	Edit:1,	MinimumValue:1,	 Format:'Integer'}
		  , {Header:"ORDER_SEQ",Type:"Int", 		SaveName:"orderSeq", 		Align:"Center",	Width:65,	Edit:0,		Hidden:true} //
		  , {Header:"추천상품유형", Type:"Text", 		SaveName:"prodLinkKindNm", 	Align:"Center",	Width:10,	Edit:0,		Hidden:true} // 수정불가
		];

		IBS_InitSheet(mySheet, ibdata);
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(18);
		mySheet.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
	}

	function themaYnChange(themaYn) {
		if (themaYn == "N") {
			$("#themaDeal1_tit").css("display", "block");
			$("#themaDeal1_sheet").css("display", "block");
			$("#themaDeal2_tit").css("display", "none");
			$("#themaDeal2_sheet").css("display", "none");
		} else {
			$("#themaDeal1_tit").css("display", "none");
			$("#themaDeal1_sheet").css("display", "none");
			$("#themaDeal2_tit").css("display", "block");
			$("#themaDeal2_sheet").css("display", "block");
		}
	}

	function initIBSheetGrid2() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "320px");
		mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		//mySheet2.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load  , MergeSheet:msHeaderOnly
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"번호",			Type:"Int", 		SaveName:"seq",				Align:"Center", Width:30,	Edit:0}
		  , {Header:"", 			Type:"CheckBox",	SaveName:"SELECTED", 		Align:"Center",	Width:30,			Sort:false}
		  , {Header:"PROD_CD",		Type:"Text",		SaveName:"prodCd",			Align:"Center",	Width:50,	Edit:0, Hidden:true} //
		  , {Header:"THEMA_SEQ",	Type:"Int", 		SaveName:"themaSeq", 		Align:"Center",	Width:30,	Edit:0, Hidden:true} //
		  , {Header:"우선순위",		Type:"Int", 		SaveName:"orderSeq", 		Align:"Center",	Width:60,	Edit:1,	MinimumValue:1,	 Format:'Integer'}
		  , {Header:"테마명",			Type:"Text", 		SaveName:"themaNm", 		Align:"Left",	Width:240,	Edit:1,	EditLen:30}
		  , {Header:"메인상품번호",		Type:"Text",		SaveName:"mainProdCd",		Align:"Center",	Width:100,	Edit:0}
		  , {Header:"메인상품명",		Type:"Text",		SaveName:"mainProdNm",		Align:"Left",	Width:300,	Edit:0}
		  , {Header:"대표상품 포함여부",	Type:"Text",		SaveName:"repProdExistsYn", Align:"Center",	Width:120,	Edit:0}
		  , {Header:"총 상품 수",		Type:"Text", 		SaveName:"prodQty",			Align:"Center",	Width:80,	Edit:0, Cursor:"pointer", Color:"blue", FontUnderline:true}
		  , {Header:"THEMA_PROD",	Type:"Text", 		SaveName:"themaProd", 		Align:"Left",	Width:100,	Edit:0, Hidden:true} //
		];

		IBS_InitSheet(mySheet2, ibdata);
		mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet2.SetHeaderRowHeight(18);
		mySheet2.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
	}

	function doProdSort1() {
		var REP_YN_CNT = 0;
		var rowCnt = mySheet.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			if (mySheet.GetCellValue(i, "repYn") == "Y") {
				mySheet.SetCellValue(i, "orderSeq", 1);
				REP_YN_CNT++;
			} else {
				mySheet.SetCellValue(i, "orderSeq", 0);
			}
		}

		if (REP_YN_CNT < 1) {
			alert("대표상품 1개를 지정해주세요.");
			return;
		}
		if (REP_YN_CNT > 1) {
			alert("대표상품은 1개만 지정 가능합니다.");
			return;
		}

		//정렬초기화
		rowCnt = mySheet.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			mySheet.SetCellValue(i, "batchOrderSeq", 0);
		}

		for(var i = 1; i < rowCnt + 1; i++) {

			var orderSeq = mySheet.RowCount();
			if (mySheet.GetCellValue(i,"repYn") == "Y") {
				mySheet.SetCellValue(i, "batchOrderSeq", 1);
				continue;
			}

			var dupOrdChk = 0;
			for (var x = 1; x < mySheet.RowCount() + 1; x++) {

				if (mySheet.GetCellValue(x,"repYn") == "Y") {
					continue;
				}

				if (mySheet.GetCellValue(i, "assoCd") == mySheet.GetCellValue(x, "assoCd")) {
					continue;
				}

				if (mySheet.GetCellValue(i, "currSellPrc") < mySheet.GetCellValue(x,"currSellPrc") && dupOrdChk == 0 ) {
					orderSeq--;
				}

				if (mySheet.GetCellValue(i, "currSellPrc") == mySheet.GetCellValue(x,"currSellPrc")) {
					if (mySheet.GetCellValue(x, "batchOrderSeq") != 0) {
						orderSeq = (mySheet.GetCellValue(x, "batchOrderSeq") + 1);
						dupOrdChk = 1;
					} else {
						if (dupOrdChk == 0) {
							orderSeq--;
						}
					}
				}

			}

			mySheet.SetCellValue(i, "batchOrderSeq", orderSeq);
		}

		mySheet.ColumnSort("batchOrderSeq", "ASC");
	}

	/** 조회 처리 */
	function doSearch1() {
		goPage1("1");
	}

	// 조회
	function goPage1(currentPage) {
		var url = "<c:url value='/product/selectProductRecommendSearch.do'/>";
		var param = new Object();
		param.mode = "search";
		param.prodCd = "<%=prodCd%>";
		param.prodLinkKindCd = $("#prodLinkKindCd").val();

		loadIBSheetData(mySheet, url, currentPage, null, param);
	}

	// 셀값 변경 시
	function mySheet_OnChange(Row, Col, Value, OldValue) {
		/* if (Row == 0) return;
		if (Col < 2) return;

		if (Value != OldValue) {
			mySheet.SetCellValue(Row, "CHK", true);
			mySheet.SetCellBackColor(Row, Col, '#F6E5E2');
		} */
	}

	//등록 팝업
	function popupInsert1() {
		var dealNotInVal = dealNotInValRefresh();
		var targetUrl = "<c:url value='/common/viewPopupProductList.do'/>?vendorId=<%=vendorId%>&notInVal=" + dealNotInVal + "&prodDivnCd=<%=prodDivnCd%>&ecLinkYn="+"&dealProdYn=N"; // 01:상품 //&onlineProdTypeCd=<%=onlineProdTypeCd%>
		Common.centerPopupWindow(targetUrl, 'prd', {width : 910, height : 550});
	}

	function dealNotInValRefresh() {
		var rtnNotInVal = "";
		var tmpArr = new Array();
		
		var rowCnt = mySheet.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			tmpArr.push(mySheet.GetCellValue(i, "assoCd"));
		}
		var tmpSet = new Set(tmpArr);
		var tmpArr2 = Array.from(tmpSet);
		for (var j = 0; j < tmpArr2.length; j++) {
			rtnNotInVal += "," + tmpArr2[j];
		}
		if (rtnNotInVal != "") {
			rtnNotInVal = rtnNotInVal.substring(1, rtnNotInVal.length);
		}
		//console.log("rtnNotInVal = " + rtnNotInVal);
		return rtnNotInVal;
	}

	function popupReturn(rtnVal) {
		for (var i = 0; i < rtnVal.prodCdArr.length; i++) {
			var rowIdx = mySheet.DataInsert(-1);

			mySheet.SetCellValue(rowIdx, "num", mySheet.RowCount());
			mySheet.SetCellValue(rowIdx, "prodCd", "<%=prodCd%>");
			mySheet.SetCellValue(rowIdx, "assoCd", rtnVal.prodCdArr[i]);
			mySheet.SetCellValue(rowIdx, "prodNm", rtnVal.prodNmArr[i]);
			mySheet.SetCellValue(rowIdx, "buyPrc", rtnVal.buyPrcArr[i]);
			mySheet.SetCellValue(rowIdx, "sellPrc", rtnVal.sellPrcArr[i]);
			mySheet.SetCellValue(rowIdx, "currSellPrc", rtnVal.currSellPrcArr[i]);
			mySheet.SetCellValue(rowIdx, "mainProdYn", "");
			mySheet.SetCellValue(rowIdx, "repYn", "N");
			mySheet.SetCellValue(rowIdx, "dispYn", "Y");
			mySheet.SetCellValue(rowIdx, "batchOrderSeq", mySheet.RowCount());
			mySheet.SetCellValue(rowIdx, "orderSeq", 0);
		}
	}

	//리스트 수정
	function doUpdate1() {
		if (!checkUpdateData()) {
			return;
		}

		if (confirm("묶음상품을 저장 하시겠습니까?")) {
			var sUrl = "<c:url value='/product/updateProduectRecommendList.do'/>";
			var param = new Object();
			param.mode = "update";
			param.vendorId = "<%= vendorId %>";
			param.themaYn = $("input:radio[name='themaYn']:checked").val();
			param.dealViewCd = $("input:radio[name='dealViewCd']:checked").val();
			param.scDpYn = $("input:radio[name='scDpYn']:checked").val();
			mySheet.DoSave(sUrl, {Param:$.param(param), Col:0, Quest:false});//, Sync:2
		}
	}

	// Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) {
			doSearch1();
		};
	}

	// 저장 전에 값을 체크한다
	function checkUpdateData() {

		var chkIndex = 0;
		var repYnCnt1 = 0;
		var orderSeqArr = new Array();
		var chkBatchOrderSeqCnt = 0;

		var rowCount = mySheet.RowCount() + 1;
		for (var i = 1; i < rowCount; i++) {
			if (mySheet.GetCellValue(i, "repYn") == "Y") {
				mySheet.SetCellValue(i, "orderSeq", 1);
				repYnCnt1++;
			} else {
				mySheet.SetCellValue(i, "orderSeq", 0);
			}

			/* var chkIndex = i + 1;
			if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "batchOrderSeq")))) {
				alert(chkIndex + "번째 줄 우선순서가 입력되지 않았습니다.");
				return false;
			} */
			/* if (!byteCheck2(chkIndex + "번째 줄 우선순위", mySheet.GetCellValue(i, "batchOrderSeq"), 2)) {
				return false;
			} */
			if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "batchOrderSeq")))) {
				chkBatchOrderSeqCnt++;
			} else {
				orderSeqArr.push(mySheet.GetCellValue(i, "batchOrderSeq"));
			}
		}
		//console.log("repYnCnt1 ::: " + repYnCnt1);
		if (repYnCnt1 < 1) {
			alert("대표상품 1개를 지정해주세요.");
			return false;
		}
		if (repYnCnt1 > 1) {
			alert("대표상품은 1개만 지정 가능합니다.");
			return false;
		}
		if (chkBatchOrderSeqCnt > 0) {
			alert("우선순위를 지정해주세요.");
			return false;
		}
		var orderSeqSet = new Set(orderSeqArr);
		if (orderSeqArr.length > orderSeqSet.size) {
			alert("중복된 우선순위가 있습니다. 확인 후 수정해주세요.");
			return false;
		}

		return true;
	}

	/** 조회 처리 */
	function doSearch2() {
		goPage2("1");
	}

	// 조회
	function goPage2(currentPage) {
		var url = "<c:url value='/product/selectProductThemaSearch.do'/>";
		var param = new Object();
		param.mode = "search";
		param.prodCd = "<%=prodCd%>";
		param.prodLinkKindCd = $("#prodLinkKindCd").val();

		loadIBSheetData(mySheet2, url, currentPage, null, param);
	}

	// 테마 추가
	function addThemaRow2() {

		if (mySheet2.RowCount() + 1 < 7) {
			var rowIdx = mySheet2.DataInsert(-1);
			mySheet2.SetCellValue(rowIdx, "seq", rowIdx);
			mySheet2.SetCellValue(rowIdx, "prodCd", "<%=prodCd%>");
			mySheet2.SetCellValue(rowIdx, "themaSeq", rowIdx);
			mySheet2.SetCellValue(rowIdx, "orderSeq", rowIdx);
			//mySheet2.SetCellValue(rowIdx, "thema_nm", "");
			mySheet2.SetCellValue(rowIdx, "prodQty", 0);
			var tmpThemaProd = new Array();
			mySheet2.SetCellValue(rowIdx, "themaProd", JSON.stringify(tmpThemaProd))
		}
	}

	// 테마 삭제
	function removeThemaRow2() {
		var chkRow = "";
		var chkCnt = 0;
		var sum = 0; 

		var themaSeqArr = new Array();
		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			if (mySheet2.GetCellValue(i, "SELECTED") == 1) {
				chkRow += "|"+i;
				chkCnt++;
				//console.log(mySheet2.GetCellValue(i, "themaSeq"));
			}
		}
		chkRow = chkRow.substring(1, chkRow.length);
		mySheet2.RowDelete(chkRow);
		//console.log("themaSeqArr = " + JSON.stringify(themaSeqArr));

		// THEMA_SEQ Reset
		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			mySheet2.SetCellValue(i, "seq", i);
			mySheet2.SetCellValue(i, "themaSeq", i);
			mySheet2.SetCellValue(i, "orderSeq", i);
		}
	}

	// 테마 상품 등록용 이벤트
	function mySheet2_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		//console.log("mySheet2_OnClick " + Row + ", " + Col + ", " + Value + ", " + CellX + ", " + CellY + ", " + CellW + ", " + CellH);
		if (Col == 9) {
			var themaSeq = mySheet2.GetCellValue(Row, "themaSeq");
			themaProdPop(themaSeq);
		}
	}

	// 테마 상품 팝업 호출
	function themaProdPop(themaSeq) {

		var notInVal = getThemaProdCds();
		var prodDivnCd = $("#prodDivnCd").val();
		var onlineProdTypeCd = "etc"; //상품팝업 조회 쿼리에 isEmpty시 조건이 추가되지 않게 하기 위해서(AND A.ONLINE_PROD_TYPE_CD = '01')

		var targetUrl = "<c:url value='/product/selectProductThemaForm.do'/>?vendorId="+$("#vendorId").val()+
				"&prodCd="+$("#prodCd").val()+
				"&prodDivnCd=" + prodDivnCd + "&onlineProdTypeCd=" + onlineProdTypeCd + 
				"&themaSeq=" + themaSeq + "&ecLinkYn=Y" +
				"&notInVal="+notInVal;
		Common.centerPopupWindow(targetUrl, 'themaProdPop', {width : 970, height : 320});
	}

	// 테마 상품 팝업창에서 적용시
	function themaProdApply(dealObj) {
		//console.log(JSON.stringify(dealObj));
		var themaDealProd = JSON.parse(dealObj.replaceAll("/\/" , ""));
		var seq = themaDealProd.themaSeq;
		var delSize = themaDealProd.dealProdList.length;
		mySheet2.SetCellValue(seq, "themaProd", JSON.stringify(themaDealProd.dealProdList));
		mySheet2.SetCellValue(seq, "prodQty", themaDealProd.dealProdList.length);
		var repYnCnt = 0;
		var mainProdCd = "";
		var mainProdNm = "";
		for (i = 0; i < delSize; i++) {
			if (themaDealProd.dealProdList[i].mainProdYn == "Y") {
				mainProdCd = themaDealProd.dealProdList[i].assoCd;
				mainProdNm = themaDealProd.dealProdList[i].prodNm;
			}
			if (themaDealProd.dealProdList[i].repYn == "Y") {
				repYnCnt++;
			}
		}
		mySheet2.SetCellValue(seq, "mainProdCd", mainProdCd); // 대표상품번호 세팅
		mySheet2.SetCellValue(seq, "mainProdNm", mainProdNm); // 대표상품명 세팅
		if (repYnCnt > 0) {
			mySheet2.SetCellValue(seq, "repProdExistsYn", "Y");
		} else {
			mySheet2.SetCellValue(seq, "repProdExistsYn", "");
		}
	}

	// NotInVal PROD_CD
	function getThemaProdCds() {

		var themaProdCds = "";
		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			var prodQty = mySheet2.GetCellValue(i, "prodQty")
			if (prodQty > 0) {
				var themaProdJson = mySheet2.GetCellValue(i, "themaProd");
				themaProdJson = JSON.parse(themaProdJson);
				for (j = 0; j < themaProdJson.length; j++) {
					themaProdCds += "," + themaProdJson[j].assoCd;
				}
			}
		}
		themaProdCds = themaProdCds.substring(1, themaProdCds.length);
		//console.log("themaProdCds = " + themaProdCds);
		return themaProdCds;
	}

	/* 테마, 테마상품 JSON 변환 */
	function themaProdToJson(themaSeq) {

		var themaList = new Array(); // 테마목록
		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			var themaObj = new Object();
			themaObj.themaSeq = mySheet2.GetCellValue(i, "themaSeq");
			themaObj.orderSeq = mySheet2.GetCellValue(i, "orderSeq");
			themaObj.themaNm = mySheet2.GetCellValue(i, "themaNm");
			themaObj.mainProdNm = mySheet2.GetCellValue(i, "mainProdNm");
			themaObj.mainProdExistsYn = mySheet2.GetCellValue(i, "repProdExistsYn");
			themaObj.prodQty = mySheet2.GetCellValue(i, "prodQty");

			var tmpThemaProd = mySheet2.GetCellValue(i, "themaProd").replaceAll("/\\/" , "");
			if (tmpThemaProd != "") {
				themaObj.themaProd = JSON.parse(tmpThemaProd);
			}

			themaList.push(themaObj);
		}
		//console.log("themaProdToJson === " + JSON.stringify(themaList));
		return themaList;
	}
	
	function themaProdOneToJson (themaSeq) {

		var themaObj = new Object();
		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			if (themaSeq == mySheet2.GetCellValue(i, "themaSeq") && mySheet2.GetCellValue(i, "prodQty") > 0) {
				//console.log("themaSeq === " + themaSeq);
				themaObj.themaSeq = mySheet2.GetCellValue(i, "themaSeq");
				themaObj.orderSeq = mySheet2.GetCellValue(i, "orderSeq");
				themaObj.themaNm = mySheet2.GetCellValue(i, "themaNm");
				themaObj.mainProdNm = mySheet2.GetCellValue(i, "mainProdNm");
				themaObj.mainProdExistsYn = mySheet2.GetCellValue(i, "repProdExistsYn");
				themaObj.prodQty = mySheet2.GetCellValue(i, "prodQty");
				var tmpThemaProd = mySheet2.GetCellValue(i, "themaProd").replaceAll("/\\/" , "");
				if (tmpThemaProd != "") {
					themaObj.themaProd = JSON.parse(tmpThemaProd);
				}
			}
		}
		return themaObj;
	}

	// 셀값 변경 시
	/* function mySheet2_OnChange(Row, Col, Value, OldValue) {
		if (Row == 0) return;
		if (Col < 2) return;

		if (Value != OldValue) {
			mySheet2.SetCellValue(Row, "CHK", true);
			mySheet2.SetCellBackColor(Row, Col, '#F6E5E2');
		}
	} */
	
	// Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
	/* function mySheet2_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) doSearch();
	} */
	

	function doSave2() {

		// 우선순위 중복 검증용
		var orderSeqArr = new Array();
		var chkOrderSeqCnt = 0;

		var repProdCnt = 0;
		var totalProdCnt = 0;
		var chkThemaNmCnt = 0;
		var chkThemaNoProdCnt = 0;
		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			var themaObj = new Object();

			var chkThemaProdList = new Array();

			var tmpThemaProd = mySheet2.GetCellValue(i, "themaProd").replaceAll("/\\/" , "")
			if (tmpThemaProd != "") {
				chkThemaProdList = JSON.parse(tmpThemaProd);
				totalProdCnt = totalProdCnt + chkThemaProdList.length;
				if (chkThemaProdList.length == 0) {
					chkThemaNoProdCnt++;
				}
			}

			for (var t = 0; t < chkThemaProdList.length; t++) {
				if (chkThemaProdList[t].repYn == "Y") {
					repProdCnt++;
				}
			}

			if (mySheet2.GetCellValue(i, "themaNm") == "") {
				chkThemaNmCnt++;
			}

			if (mySheet2.GetCellValue(i, "orderSeq") == "") {
				chkOrderSeqCnt++;
			} else {
				orderSeqArr.push(mySheet2.GetCellValue(i, "orderSeq"));
			}
		}

		if (chkThemaNmCnt > 0) {
			alert("테마명을 입력해주세요.");
			return;
		}

		var orderSeqSet = new Set(orderSeqArr);
		//console.log("orderSeqArr = " + orderSeqArr);
		//console.log("orderSeqSet = " + orderSeqSet);
		if (orderSeqArr.length > orderSeqSet.size) {
			alert("중복된 우선순위가 있습니다. 확인 후 수정해주세요.");
			return;
		}
		if (chkOrderSeqCnt > 0) {
			alert("우선순위를 지정해주세요.");
			return;
		}

		if (repProdCnt > 1) {
			alert("대표상품은 1개만 지정 가능합니다.");
			return;
		}
		if (repProdCnt < 1) {
			alert("대표상품 1개를 지정해주세요.");
			return;
		}

		if (chkThemaNoProdCnt > 0) {
			alert("테마에 상품을 추가해주세요.");
			return;
		}

		if (totalProdCnt > 100) {
			alert("상품은 최대 100개까지 할당 가능합니다. 살당 상품수량을 조정해 주세요.");
			return;
		}

		$("#dealThemaProdListJsonObject").val(JSON.stringify(themaProdToJson()));

		if (confirm("테마를 저장 하시겠습니까?")) {
			var sUrl = "<c:url value='/product/updateProduectThemaList.do'/>";
			var param = new Object();
			param.mode = "update";
			param.vendorId = "<%= vendorId %>";
			param.themaYn = $("input:radio[name='themaYn']:checked").val();
			param.scDpYn = $("input:radio[name='scDpYn']:checked").val();
			param.dealViewCd = $("input:radio[name='dealViewCd']:checked").val();
			mySheet2.DoSave(sUrl, {Param:$.param(param), Col:0, Quest:false});//, Sync:2
		}
	}

	// Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
	function mySheet2_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) {
			doSearch2();
		}
	}

</script>
</head>
<body>
<form name="dataForm" id="dataForm">
<input type="hidden" name="prodLinkKindCd" id="prodLinkKindCd" value="04" /><%-- 04: 딜상품 --%>
<input type="hidden" name="themaYn" id="themaYn" value="" />
<input type="hidden" name="prodDivnCd" id="prodDivnCd" value="<%=prodDivnCd %>" />
<input type="hidden" name="onlineProdTypeCd" id="onlineProdTypeCd" value="<%=onlineProdTypeCd %>" />
<input type="hidden" name="dealThemaProdListJsonObject" id="dealThemaProdListJsonObject" value="" />

<div id="content_wrap" style="width:990px; height:200px;">
	<div id="wrap_menu" style="width:990px">
		<!--	@ 검색조건  -->
		<!-- 상품 상세보기 하단 탭 -->
		<c:set var="tabNo" value="<%=tabNo%>" />
		<c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="<%=prodCd%>" />
		</c:import>
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit" id="themaDeal1_tit" style="display:;">
					<li class="btn">
						<a href="#" class="btn" id="prodSort1"><span>상품정렬</span></a>
						<a href="#" class="btn" id="prodAdd1"><span>상품추가</span></a>
						<a href="#" class="btn" id="search1"><span><spring:message code="button.common.inquire"/></span></a>
						<a href="#" class="btn" id="update1"><span><spring:message code="button.common.save" /></span></a>
					</li>
					</ul>
				<ul class="tit" id="themaDeal2_tit" style="display:none;">
					<li class="tit">상품구성정보</li>
					<li class="btn">
						<a href="#" class="btn" id="search2"><span><spring:message code="button.common.inquire"/></span></a>
						<a href="#" class="btn" id="addRow2"><span>행추가</span></a>
						<a href="#" class="btn" id="removeRow2"><span>행삭제</span></a>
						<a href="#" class="btn" id="save2"><span>저장</span></a>
					</li>
				</ul>
				<table class="bbs_grid2" width="100%" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="10%">
						<col width="25%">
						<col width="10%">
						<col width="25%">
						<col width="10%">
						<col width="20%">
					</colgroup>
					<tr>
						<th>보기방식 선택</th>
						<td><input type="radio" name="dealViewCd" id="dealViewCd1" value="GRID_VIEW" ${themaDealInfo.DEAL_VIEW_CD eq 'GRID_VIEW' ? 'checked' : ''} /> 그리드 방식 &nbsp;&nbsp;
							<input type="radio" name="dealViewCd" id="dealViewCd2" value="LIST_VIEW" ${themaDealInfo.DEAL_VIEW_CD eq 'LIST_VIEW' ? 'checked' : ''} /> 리스트 방식</td>
						<th>테마사용 여부</th>
						<td><input type="radio" name="themaYn" id="themaYn1" value="Y" ${themaDealInfo.THEMA_YN eq 'Y' ? 'checked' : ''} /> Y &nbsp;&nbsp;
							<input type="radio" name="themaYn" id="themaYn2" value="N" ${themaDealInfo.THEMA_YN eq 'N' ? 'checked' : ''} /> N</td>
						<th>검색노출여부</th>
						<td><input type="radio" name="scDpYn" id="scDpYn1" value="Y" ${themaDealInfo.DEAL_SC_DP_YN eq 'Y' ? 'checked' : ''} /> Y &nbsp;&nbsp;
							<input type="radio" name="scDpYn" id="scDpYn2" value="N" ${themaDealInfo.DEAL_SC_DP_YN eq 'N' ? 'checked' : ''} /> N</td>	
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" id="themaDeal1_sheet" style="display:;">
					<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" id="themaDeal2_sheet" style="display:none;">
					<tr>
						<td><div id="ibsheet2"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
</form>
<form name="form1" id="form1">
	<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>" />
	<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
	<input type="hidden" id="onlineProdTypeCd" name="onlineProdTypeCd" value="" />
	<input type="hidden" id="prodDivnCd" name="prodDivnCd" value="" />
	<input type="hidden" id="themaYn" name="themaYn" value="" />
</form>
</body>
</html>