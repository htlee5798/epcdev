<%--
- Author(s): projectBOS32
- Created Date: 2016. 04. 29
- Version : 1.0
- Description : 딜상품품 등록/수정 폼
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<title>딜상품등록</title>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script>
<%@ include file="./javascript.jsp" %>
<script type="text/javascript">

	var vndId = "";

	$(document).ready(function() {

		var alertMsg = "";
		<c:if test="${param.imgOk == 'N'}">
		alertMsg += "\n\n대표 이미지 등록 기준을 준수 바랍니다.\n용량: 600KB 이하\n사이즈: (정사이즈 비율) 500px이상 1500px이하";
		</c:if>

		<c:if test="${param.movOk == 'N'}">
		alertMsg += "\n\n동영상 등록 기준을 준수 바랍니다.\n용량: 200MB 이하";
		</c:if>

		<c:if test="${param.movDelOk == 'Y'}">
		alertMsg = "\n\n동영상이 삭제되었습니다.";
		</c:if>

		// 상품등록이후 상품 기본정도 등록 완료 메세지 띄운다.
		var mode = "<c:out value='${param.mode}'/>";
		if (mode == "save") {
			alert("딜상품 등록 :  등록이 완료 되었습니다. \n 상세페이지는 EDI > 상품 > 임시보관함 에서 확인 가능합니다." + alertMsg);
			window.close();
		} else if (mode == "update") {
			alert("수정 되었습니다." + alertMsg);
		} else if (mode == "delete") {
			alert(alertMsg);
		}

		if ("${newProdDetailInfo}" == "") { 
			vndId = "${epcLoginVO.repVendorId}";

			if ("${epcLoginVO.vendorTypeCd}" == "06" ) {
				if ("${epcLoginVO.repVendorId}" != "") {
					checkBlackListVendor1 ("${epcLoginVO.repVendorId}");
				}
			} else {
				if ("${epcLoginVO.repVendorId}" != "") { // 협력업체 선택시 실행.
					checkBlackListVendor();
				}
			}
		} else {
			vndId = "${newProdDetailInfo.entpCd}";
		}

		// 업체코드별 거래형태 조회(selectBox mapping)
		trdTypFgSel();

		$("select[name=entpCode]").change(function() {
			var errorNode = $(this).prev("div[name=error_msg]").length;
			if (errorNode > 0) { 
				$(this).prev().remove();
			}
			if ($(this).val() != "") {
				checkBlackListVendor();
			}
		});

		$("select[name=dealProdDivnCode]").change(function() {
			checkBlackListVendor();
		});

		// 필수 콤보박스 값 검증
		$("select.required").change(function() {
			validateSelectBox($(this)); 
		});

		// 필수 입력항목 검증
		$("input:text.required").blur(function() {
			//validateTextField($(this));
			validateFormTextField($(this));
		});

		// 해당 입력 항목이 값이 있는경우 검증
		$("input:text.requiredIf").blur(function() {
			if ($(this).val() != "") {
				validateTextField($(this));
			}
		});

		// radio버튼이 선택되었는지 검증.
		$("input:radio.required").click(function() {
			validateRadioField($(this));
		});

		//  팀 변경시 이벤트
		$("#newProduct select[name=teamCd]").change(function() {
			// 대,중,소분류 초기화
			$("#l1Cd option").not("[value='']").remove();
			$("#l2Cd option").not("[value='']").remove();
			$("#l3Cd option").not("[value='']").remove();
			<%-- //doCategoryReset(); --%>
			_eventSelectL1List($(this).val().replace(/\s/gi, ''));
		});

		// 대분류 변경시 이벤트
		$("#newProduct select[name=l1Cd]").change(function() {
			var groupCode = $("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');	
			$("#l2Cd option").not("[value='']").remove(); // 중,소분류 초기화
			$("#l3Cd option").not("[value='']").remove();
			<%-- //doCategoryReset(); --%>
			_eventSelectL2List(groupCode, $(this).val()); // 중분류 셋팅
		});

		// 중분류 변경시 이벤트
		$("#newProduct select[name=l2Cd]").change(function() {
			var groupCode = $("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');
			var l1Cd = $("#newProduct select[name=l1Cd]").val().replace(/\s/gi, '');
			$("#l3Cd option").not("[value='']").remove(); // 소분류 초기화
			<%-- //doCategoryReset(); --%>
			_eventSelectL3List(groupCode, l1Cd, $(this).val().replace(/\s/gi, ''));
		});

		// 소분류 선택 변경 시 
		$("select[id=l3Cd]").change(function() {
			<%-- //doCategoryReset(); --%>
			$("#l3Cd").val($(this).val());
		});

		//모든 input에 대해서 특수문자 경고표시하기
		$("input").keyup(function() {

			var value = $(this).val();
			var arr_char = new Array();
			arr_char.push("'");
			arr_char.push("\"");
			arr_char.push("<");
			arr_char.push(">");
			arr_char.push(";");

			for (var i = 0 ; i < arr_char.length ; i++) {
				if (value.indexOf(arr_char[i]) != -1) {
					alert("[<, >, ', /, ;] 특수문자는 사용하실 수 없습니다.");
					value = value.substr(0, value.indexOf(arr_char[i]));
					$(this).val(value);
				}
			}

			$("#dealProdDivnCode").change(function() {
				mySheet.RowDelete();
			});
		});

		if ("<c:out value='${newProdDetailInfo.pgmId}' />" == "") {
			$("input:radio[name=dealViewCd][value='GRID_VIEW']").prop("checked", true); // 기본선택
			$("input:radio[name=themaYn][value='Y']").prop("checked", true); // 기본선택
			$("input:radio[name=scDpYn][value='Y']").prop("checked", true); // 기본선택

			$("#ibsheet2Title").css("display", "none");
			$("#ibsheet2").css("display", "none");
			$("#ibsheet3Title").css("display", "block");
			$("#ibsheet3").css("display", "block");
		}

		<%-- initIBSheetGrid(); // 그리드 초기화 --- 전시카테고리 삭제 --%>
		initIBSheetGrid2(); // 그리드 초기화
		initIBSheetGrid3(); // 그리드 초기화

		<%-- $("input[name=dealViewCd]").change(function() {
			if(this.value == "GRID_VIEW"){
			} else {
			}
		}); --%>
		$("input[type=radio][name=themaYn]").change(function() {
			//console.log(this.value);
			if (!confirm("테마사용여부 변경 후 저장시 이전 데이터는 사라집니다. 계속하시겠습니까?")) {
				if ($("input[type=radio][name=themaYn][value='Y']").prop("checked")) {
					$("input[type=radio][name=themaYn][value='N']").prop("checked", true);
				} else {
					$("input[type=radio][name=themaYn][value='Y']").prop("checked", true);
				}
				return;
			}

			if (this.value == "N") {
				$("#ibsheet2Title").css("display", "block");
				$("#ibsheet2").css("display", "block");
				$("#ibsheet3Title").css("display", "none");
				$("#ibsheet3").css("display", "none");
			} else if (this.value == "Y") {
				$("#ibsheet2Title").css("display", "none");
				$("#ibsheet2").css("display", "none");
				$("#ibsheet3Title").css("display", "block");
				$("#ibsheet3").css("display", "block");
			}
		});

		/** 여기서 부터 상품등록이후 상품 상세정보 설정에 사용됨 **/

		//  상품의 상세정보 SeleceBox, RadioButton ValueSetting.[커스텀태그에서는 시큐어 코딩이 불가능 하여 스크립트에서 처리]
		if ("<c:out value='${newProdDetailInfo.pgmId}' />" != "") {
			// SeleceBox, RadioButton ValueSetting..
			_eventSetnewProdDetailInfoDetailValue();

			<%-- setTimeout(function() {
				selectCategoryList();
			},1000); --%>
			if ("<c:out value='${newProdDetailInfo.themaYn}'/>" == "Y") {
				$("#ibsheet2Title").css("display", "none");
				$("#ibsheet2").css("display", "none");
				$("#ibsheet3Title").css("display", "block");
				$("#ibsheet3").css("display", "block");
				setTimeout(function() {
					selectThemaDealList();
				}, 1000);
			} else {
				$("#ibsheet2Title").css("display", "block");
				$("#ibsheet2").css("display", "block");
				$("#ibsheet3Title").css("display", "none");
				$("#ibsheet3").css("display", "none");
				setTimeout(function() {
					selectDealProductList();
				}, 1000);
			}
		} else {
			$("#ibsheet2Title").css("display", "none");
			$("#ibsheet2").css("display", "none");
			$("#ibsheet3Title").css("display", "block");
			$("#ibsheet3").css("display", "block");
		}
		/********************************************************************************/
	});

	<%--/** ibsheet 초기화 */
	function initIBSheetGrid() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "640px", "95px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"",				Type:"CheckBox",	SaveName:"SELECTED",			Align:"Center",	Width:25,			Sort:false}
		  , {Header:"전시카테고리",		Type:"Text",		SaveName:"FULL_CATEGORY_NM",	Align:"Left",	Width:470,	Edit:0}
		  , {Header:"전시카테고리아이디",	Type:"Text",		SaveName:"CATEGORY_ID",			Align:"Left",	Width:1,	Edit:0,	Hidden:true}
		  , {Header:"카테고리 상태값",	Type:"Text",		SaveName:"DISP_YN",				Align:"Center",	Width:145,	Edit:0}
		];
		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
	}--%>

	function initIBSheetGrid2() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "200px");
		mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet2.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"", 		Type:"CheckBox",	SaveName:"SELECTED", 		Align:"Center",	Width:30,			Sort:false}
		  , {Header:"상품번호",	Type:"Text",		SaveName:"PROD_CD", 		Align:"Center",	Width:120,	Edit:0}
		  , {Header:"상품명",		Type:"Text",		SaveName:"PROD_NM", 		Align:"Left",	Width:450,	Edit:0}
		  , {Header:"우선순위",	Type:"Int", 		SaveName:"ORDER_SEQ", 		Align:"Center",	Width:100,	Edit:1,	MinimumValue:1,	 Format:'Integer'}
		  , {Header:"대표상품여부",	Type:"Combo",		SaveName:"REP_YN",	 		Align:"Center",	Width:100,	Edit:1,	ComboCode:"Y|N", ComboText:"Y|N"}
		  <%--, {Header:"판매상태",	Type:"Text", 		SaveName:"SELL_FLAG", 		Align:"Center",	Width:80,	Edit:0} --%>
		  <%--, {Header:"판매가",		Type:"Text", 		SaveName:"CURR_SELL_PRC",	Align:"Right",	Width:80,	Edit:0} --%>
		  <%--, {Header:"재고수량",	Type:"Text", 		SaveName:"STOCK_QTY",		Align:"Center",	Width:80,	Edit:0} --%>
		];

		IBS_InitSheet(mySheet2, ibdata);
		mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바 비활성
		mySheet2.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet2.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
	}

	function initIBSheetGrid3() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet3"), "mySheet3", "100%", "200px");
		mySheet3.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet3.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"번호",		Type:"Int", 		SaveName:"SEQ",					Align:"Center", Width:30,	Edit:0}
		  , {Header:"", 		Type:"CheckBox",	SaveName:"SELECTED", 			Align:"Center",	Width:30,			Sort:false}
		  , {Header:"THEMA_SEQ",Type:"Int", 		SaveName:"THEMA_SEQ", 			Align:"Center",	Width:30,	Edit:0, Hidden:true} //
		  , {Header:"우선순위",	Type:"Int", 		SaveName:"ORDER_SEQ", 			Align:"Center",	Width:60,	Edit:1,	MinimumValue:1,	 Format:'Integer'}
		  , {Header:"테마명",		Type:"Text", 		SaveName:"THEMA_NM", 			Align:"Center",	Width:200,	Edit:1,	EditLen:30}
		  , {Header:"메인상품번호",	Type:"Text",		SaveName:"MAIN_PROD_CD",		Align:"Center",	Width:90,	Edit:0}
		  , {Header:"메인상품명",	Type:"Text",		SaveName:"MAIN_PROD_NM",		Align:"Left",	Width:235,	Edit:0}
		  , {Header:"대표상품 포함여부",Type:"Text",		SaveName:"REP_PROD_EXISTS_YN", Align:"Center",	Width:110,	Edit:0}
		  , {Header:"총 상품 수",	Type:"Text", 		SaveName:"PROD_QTY",			Align:"Center",	Width:75,	Edit:0, Cursor:"pointer", Color:"blue", FontUnderline:true}
		  , {Header:"THEMA_PROD",Type:"Text", 		SaveName:"THEMA_PROD", 			Align:"Left",	Width:100,	Edit:0, Hidden:true} //
		];

		IBS_InitSheet(mySheet3, ibdata);
		mySheet3.SetWaitImageVisible(0); // 검색시 로딩 바 비활성
		mySheet3.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet3.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
	}

	// 테마 상품 등록용 이벤트
	function mySheet3_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		//console.log("mySheet3_OnClick " + Row + ", " + Col + ", " + Value + ", " + CellX + ", " + CellY + ", " + CellW + ", " + CellH);
		if (Col == 8) {
			var themaSeq = mySheet3.GetCellValue(Row, "THEMA_SEQ");
			themaProdPop(themaSeq);
		}
	}

	/* 상품등록 이후 상세정보 SelectBox, RadioButton Value Settings */
	function _eventSetnewProdDetailInfoDetailValue() {
		var teamCd = "<c:out value='${newProdDetailInfo.teamCd}'/>"; //팀코드
		var l1Cd = "<c:out value='${newProdDetailInfo.l1Cd}'/>"; //대분류
		var l2Cd = "<c:out value='${newProdDetailInfo.l2Cd}'/>"; //중분류
		var l3Cd = "<c:out value='${newProdDetailInfo.l3Cd}'/>"; //소분류
		var taxatDivnCd = "<c:out value='${newProdDetailInfo.taxatDivnCd}'/>"; //면과세구분
		var onlineRepProdCd = "<c:out value='${newProdDetailInfo.onlineRepProdCd}'/>"; //온라인 대표상품코드
		var homeCd = "<c:out value='${newProdDetailInfo.homeCd}'/>" //원산지
		var norProdSaleCurr = "<c:out value='${newProdDetailInfo.norProdSaleCurr}'/>"; //정산매가단위
		var dealProdDivnCode = "<c:out value='${newProdDetailInfo.dealProdDivnCode}'/>"; //딜상품구분
		var dealViewCd = "<c:out value='${newProdDetailInfo.dealViewCd}'/>"; // 테마 보기방식
		var themaYn = "<c:out value='${newProdDetailInfo.themaYn}'/>"; // 테마사용여부
 		var videoUrl = "<c:out value='${viewVideoUrl}'/>"; // 동영상 URL
 		var scDpYn = "<c:out value='${newProdDetailInfo.scDpYn}'/>"; // 검색노출여부

		// SelectBox Settings....
		// 팀코드 selectBox에 셋팅해주고  팀의 대분류 조회
		$("#newProduct select[name='teamCd']").val(teamCd);
		_eventSelectL1List(teamCd);

		// 대분류 selectBox에 셋팅해주고 대분류의 중분류 조회, 전상법, KC인증 조회
		$("#newProduct select[name='l1Cd']").val(l1Cd);
		_eventSelectL2List(teamCd, l1Cd);

		// 중분류 selectBox 셋팅해주고 중분류의 소분류 조회
		$("#newProduct select[name='l2Cd']").val(l2Cd);
		_eventSelectL3List(teamCd, l1Cd, l2Cd);

		$("#newProduct select[name='l3Cd']").val(l3Cd);

		$("select[name='taxatDivCode']").val(taxatDivnCd);
		$("select[name='taxatDivCode']").attr("disabled",true);
		$("select[name='productCountryCode']").val(homeCd);
		$("select[name='norProdSaleCurr']").val(norProdSaleCurr);
		$("select[name='dealProdDivnCode']").val(dealProdDivnCode);

		if (dealViewCd == "GRID_VIEW") {
			$("input:radio[name='dealViewCd']:[value='GRID_VIEW']").prop('checked', true);
		} else {
			$("input:radio[name='dealViewCd']:[value='LIST_VIEW']").prop('checked', true);
		}
 
		if (themaYn == "Y") {
			$("input:radio[name='themaYn']:[value='Y']").prop('checked', true);
		} else {
			$("input:radio[name='themaYn']:[value='N']").prop('checked', true);
		}
		
		if (scDpYn == "Y"){
			$("input:radio[name='scDpYn']:[value='Y']").prop('checked', true);
		} else {
			$("input:radio[name='scDpYn']:[value='N']").prop('checked', true);	
		}

		$("#movieFileName").html(videoUrl);
	}

	/*	상품 등록 폼을 전송하기전에 검증로직을 실행하는 함수.
		공통 검증 함수인 validateCommon을 실행하고 각 필드 별로 필요한 작업 실행. */
	function validateNewProductInfo() {

		var validationResult = validateCommon();

		var originalSellStartDy = $("input[name=sellStartDy]").val();
		var dbDatelength1 = $("input[name=sellStartDy]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if (dbDatelength1 != '' || dbDatelength1 != null) {
			$("input[name=sellStartDy]").val(dbDatelength1);
		}

		var originalSellEndDy = $("input[name=sellEndDy]").val();
		var dbDatelength2 = $("input[name=sellEndDy]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if (dbDatelength2 != '' || dbDatelength2 != null) {
			$("input[name=sellEndDy]").val(dbDatelength2);
		}

		var errorLength = $("div[name=error_msg]").length;

		if (!validationResult || errorLength > 0) {
			$("input[name=sellStartDy]").val(originalSellStartDy);
			$("input[name=sellEndDy]").val(originalSellEndDy);
			alert("필수 속성값들을 모두 입력해주시기 바랍니다.");
			return false;
		} else {
			return true;
		}
	}

	// 상품 등록 폼을 전송하기 전에 각 필드의 기본  값 설정
	function setupFormFieldDefaultValue() {
		var tempCode = $("#temperatureDivnCode").val();
		$("#tmpDivnCode").val(tempCode);
		$("input[name=productDescription]").val(CrossEditor.GetBodyValue());
	}

	// 상품등록 폼 전송
	function submitProductInfo() {

		$("div#content_wrap").block({ message: null , showOverlay: false });
		var index = $("#itemRow").val();

		// 필드 값 검증
		if ( validateNewProductInfo() ) {

			setupFormFieldDefaultValue(); // 유효성 체크 통과

			if ($("#entpCode").val() == "") {
				alert("업체선택은 필수입니다.");
				$("#entpCode").focus();
				return;
			}

			if (!calByteProd(newProduct.productName, 80, "상품명", false) ) return; // 상품명 byte체크

			<%--// 카테고리 validation // 전시카테고리 체크 안함
			/*if (mySheet.RowCount() == 0) {
				alert("전시카테고리를 추가하세요.");
				return;
			}*/--%>

			// 상세이미지 validation
			var newProdDescBodyVal = CrossEditor.GetBodyValue();
			var newProdDescTxtVal = CrossEditor.GetTextValue();
			newProdDescTxtVal = newProdDescTxtVal.replace(/^\s*/,'').replace(/\s*$/, '');

			CrossEditor.SetBodyStyle("");
			if (newProdDescTxtVal == "") {
				CrossEditor.SetBodyValue("");
				$("#productDescription").val("");
			}

			var flagVar = 0;
			$("form[name=newProduct] input:file").each(function() {
				if($(this).attr("name") != "movieFile") {
					if ( $(this).val() == "") {
						flagVar ++;
					}
				}
			});

			if (flagVar == 1 && "${onlineImageList}" == "") {
				alert("업로드할 대표이미지를 선택해주세요.");
				return;
			}

			$("#taxatDivCode").attr("disabled",false);

			// 우선순위 중복 검증용
			var orderSeqArr = new Array();
			var chkOrderSeqCnt = 0;

			if ($("input:radio[name='themaYn']:checked").val() == "N") { // 테마 사용 N
				var rowCnt = mySheet2.RowCount();
				if (rowCnt == 0) {
					alert("묶음상품을 추가하세요.");
					return;
				}
				if (rowCnt > 100) {
					alert("묶음상품을 100개를 초과하여 설정 할 수 없습니다.");
					return;
				}

				var chkTonYnCnt = 0;
				for (var i = 1; i < rowCnt + 1; i++) {
					if (mySheet2.GetCellValue(i, "REP_YN") == "Y") {
						chkTonYnCnt++;
					}
					if (mySheet2.GetCellValue(i, "ORDER_SEQ") == "") {
						chkOrderSeqCnt++;
					} else {
						orderSeqArr.push(mySheet2.GetCellValue(i, "ORDER_SEQ"));
					}
				}

				var orderSeqSet = new Set(orderSeqArr);
				//console.log("orderSeqArr = " + orderSeqArr);
				//console.log("orderSeqSet = " + orderSeqSet);
				if (orderSeqArr.length > orderSeqSet.size) {
					alert("중복된 우선순위가 있습니다. 확인 후 수정해주세요.");
					return;
				}
				if (chkOrderSeqCnt > 0) {
					alert("정렬순번은 필수 입니다.");
					return;
				}

				if (chkTonYnCnt == 0) {
					alert("묶음대표상품을 지정 하세요.");
					return;
				}
				if (chkTonYnCnt > 1) {
					alert("묶음대표상품은 하나만 지정 하세요.");
					return;
				}

				$("#dealProdListJsonArray").val(JSON.stringify(dealProdToJson()));

			} else { // 테마 사용 Y

				var repProdCnt = 0;
				var totalProdCnt = 0;
				var chkThemaNmCnt = 0;
				var chkThemaNoProdCnt = 0;
				var rowCnt = mySheet3.RowCount();
				for (var i = 1; i < rowCnt + 1; i++) {
					var themaObj = new Object();

					var tmpThemaProd = mySheet3.GetCellValue(i, "THEMA_PROD").replaceAll("/\\/" , "");
					if (tmpThemaProd != "") {
						var chkThemaProdList = JSON.parse(tmpThemaProd);
						totalProdCnt = totalProdCnt + chkThemaProdList.length;

						for (var t = 0; t < chkThemaProdList.length; t++) {
							if (chkThemaProdList[t].repYn == "Y") {
								repProdCnt++;
							}
						}
						if (chkThemaProdList.length == 0) {
							chkThemaNoProdCnt++;
						}
					}

					if (mySheet3.GetCellValue(i, "THEMA_NM") == "") {
						chkThemaNmCnt++;
					}

					if (mySheet3.GetCellValue(i, "ORDER_SEQ") == "") {
						chkOrderSeqCnt++;
					} else {
						orderSeqArr.push(mySheet3.GetCellValue(i, "ORDER_SEQ"));
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
			}

			//console.log("Before Submit !!! #dealThemaProdListJsonObject ===" + $("#dealThemaProdListJsonObject").val());
			loadingMaskFixPos();

			var actUrl = "<c:url value='/product/saveDealProduct.do'/>";
			if ("<c:out value='${newProdDetailInfo.pgmId}' />" != "") {
				actUrl = "<c:url value='/product/modifyDealProduct.do'/>";
			}
			$("#newProduct").attr("action", actUrl);
			$("#newProduct").attr("method", "post").submit();
		}

		setTimeout(function() {
			$("div#content_wrap").unblock();
			hideLoadingMask();
		}, 500);
	}

	/* 딜상품목록 JSON 변환 */
	function dealProdToJson() {

		var dealProdList = new Array();
		var rowCnt = mySheet2.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			var dealProd = new Object(); // 테마 내 상품

			dealProd.prodCd = mySheet2.GetCellValue(i,"PROD_CD");
			dealProd.repYn = mySheet2.GetCellValue(i,"REP_YN");
			dealProd.orderSeq = mySheet2.GetCellValue(i,"ORDER_SEQ");
			dealProd.mainProdYn = "N";
			dealProd.themaSeq = 0;
			dealProd.prodNm = mySheet2.GetCellValue(i,"PROD_NM");

			dealProdList.push(dealProd);
		}
		//console.log(JSON.stringify(dealProdList));
		return dealProdList;
	}

	/* 테마, 테마상품 JSON 변환 */
	function themaProdToJson(themaSeq) {

		var themaList = new Array(); // 테마목록
		var rowCnt = mySheet3.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			var themaObj = new Object();
			themaObj.themaSeq = mySheet3.GetCellValue(i, "THEMA_SEQ");
			themaObj.orderSeq = mySheet3.GetCellValue(i, "ORDER_SEQ");
			themaObj.themaNm = mySheet3.GetCellValue(i, "THEMA_NM");
			themaObj.mainProdNm = mySheet3.GetCellValue(i, "MAIN_PROD_NM");
			themaObj.mainProdExistsYn = mySheet3.GetCellValue(i, "REP_PROD_EXISTS_YN");
			themaObj.prodQty = mySheet3.GetCellValue(i, "PROD_QTY");
			var tmpThemaProd = mySheet3.GetCellValue(i, "THEMA_PROD").replaceAll("/\\/" , "");
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
		for (var i = 1; i < mySheet3.RowCount() + 1; i++) {
			if (themaSeq == mySheet3.GetCellValue(i, "THEMA_SEQ") && mySheet3.GetCellValue(i, "PROD_QTY") > 0) {
				//console.log("themaSeq === " + themaSeq);
				themaObj.themaSeq = mySheet3.GetCellValue(i, "THEMA_SEQ");
				themaObj.orderSeq = mySheet3.GetCellValue(i, "ORDER_SEQ");
				themaObj.themaNm = mySheet3.GetCellValue(i, "THEMA_NM");
				themaObj.mainProdNm = mySheet3.GetCellValue(i, "MAIN_PROD_NM");
				themaObj.mainProdExistsYn = mySheet3.GetCellValue(i, "REP_PROD_EXISTS_YN");
				themaObj.prodQty = mySheet3.GetCellValue(i, "PROD_QTY");
				themaObj.themaProd = JSON.parse(mySheet3.GetCellValue(i, "THEMA_PROD").replaceAll("/\\/" , ""));
			}
		}
		return themaObj;
	}

	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임
		$("#entpCode").val(strVendorId);

		checkBlackListVendor1(strVendorId);
	}

	<%-- //상세보기 카테고리 List
	function selectCategoryList() {
		var url = '<c:url value="/edi/product/NEDMPRO0030Category.do"/>';
		var param = new Object();
		param.onlineDisplayCategoryCode = $("#onlineDisplayCategoryCode").val();

		loadIBSheetData(mySheet, url, null, null, param);
	} --%>

	//상세보기 묶음상품 List
	function selectDealProductList() {
		var url = "<c:url value='/edi/product/NEDMPRO0050DealProduct.do'/>";
		var param = new Object();
		param.pgmId = $("#newProductCode").val();
		loadIBSheetData(mySheet2, url, null, null, param);
	}

	function selectThemaDealList() {
		var url = "<c:url value='/edi/product/NEDMPRO0050ThemaDeal.do'/>";
		var param = new Object();
		param.pgmId = $("#newProductCode").val();
		loadIBSheetData(mySheet3, url, null, null, param);
	}

	<%-- /* 전시카테고리 추가*/
	function addCategory() {
		if ($("#l3Cd").val() == "") {
			alert("분류코드를 선택 하세요.");
			$("#l3Cd").focus();
			return;
		}

		var rowCount = mySheet.RowCount();
		if (rowCount < 3) {
			var targetUrl = '${ctx}/edi/product/onlineDisplayCategory.do?closeFlag=1&catCd='+$("#l3Cd").val(); // 01:상품
			Common.centerPopupWindow(targetUrl, 'epcCategoryPopup', {width : 460, height : 455});
		} else {
			alert("최대 3개 까지 지정 가능합니다.");
		}
	} --%>

	<%-- // 카테고리 검색창으로 부터 받은 카테고리 정보 입력
	function setCategoryInto(asCategoryId, asCategoryNm, displayFlag, fullCategoryNm) { // 이 펑션은 카테고리 검색창에서 호출하는 펑션임

		var rowCount = mySheet.RowCount();
		if (rowCount < 3) {
			for (var i = 1; i < mySheet.RowCount()+1; i++) {
				if (mySheet.GetCellValue(i,"CATEGORY_ID") == asCategoryId) {
					alert("이미 추가된 카테고리 입니다.");
					return;
				}
			}
		} else {
			alert("최대 3개 까지 지정 가능합니다.");
			return;
		}

		var rowIdx = mySheet.DataInsert(0);
		if (displayFlag == "Y") {
			displayFlag = "전시";
		} else {
			displayFlag = "미전시";
		}

		mySheet.SetCellValue(rowIdx, "FULL_CATEGORY_NM", fullCategoryNm);
		mySheet.SetCellValue(rowIdx, "CATEGORY_ID", asCategoryId);
		mySheet.SetCellValue(rowIdx, "DISP_YN", displayFlag);

		selectL4ListForOnline(asCategoryId);
	} --%>

	/* 전시카테고리 삭제 */
	function delCategory() {
		var chkRow = "";
		for (var i = 1; i < mySheet.RowCount() + 1; i++) {
			if (mySheet.GetCellValue(i,"SELECTED") == 1) {
				chkRow += "|"+i;
			}
		}
		chkRow = chkRow.substring(1,chkRow.length);
		mySheet.RowDelete(chkRow);
	}

	/* 딜상품추가 */
	function productAdd() {

		if (mySheet2.RowCount() > 100){
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

		var notInVal = "";
		for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
			notInVal += "," + mySheet2.GetCellValue(i, "PROD_CD");
		}
		if (notInVal != "") {
			notInVal = notInVal.substring(1,notInVal.length);
		}

		var prodDivnCd = $("#dealProdDivnCode option:selected").val();
		var onlineProdTypeCd = "etc";  //상품팝업 조회 쿼리에 isEmpty시 조건이 추가되지 않게 하기 위해서(AND A.ONLINE_PROD_TYPE_CD = '01')

		var targetUrl = "<c:url value='/common/viewPopupProductList.do'/>?vendorId="+$("#entpCode").val()+"&notInVal="+notInVal+"&prodDivnCd="+prodDivnCd+"&onlineProdTypeCd="+onlineProdTypeCd+"&ecLinkYn=&dealProdYn=N";//01:상품

		Common.centerPopupWindow(targetUrl, 'prd', {width : 910, height : 550});
	}

	function popupReturn(rtnVal) {
		for (var i = 0; i < rtnVal.prodCdArr.length; i++) {
			var rowIdx = mySheet2.DataInsert(-1);

			mySheet2.SetCellValue(rowIdx, "PROD_CD", rtnVal.prodCdArr[i]);
			mySheet2.SetCellValue(rowIdx, "PROD_NM", rtnVal.prodNmArr[i]);
			<%-- mySheet2.SetCellValue(rowIdx, "CURR_SELL_PRC", rtnVal.currSellPrcArr[i]); --%>
			mySheet2.SetCellValue(rowIdx, "MAIN_PROD_YN", "N");
			mySheet2.SetCellValue(rowIdx, "REP_YN", "N");
			mySheet2.SetCellValue(rowIdx, "SELL_FLAG", rtnVal.sellFlagArr[i]);
			<%-- mySheet2.SetCellValue(rowIdx, "STOCK_QTY", rtnVal.stockQtyArr[i]); --%>
			mySheet2.SetCellValue(rowIdx, "ORDER_SEQ", (mySheet2.RowCount()-1)+1);
		}

		/*적용 상품할당수량*/
		$("#productCnt").val(mySheet2.RowCount());
		<%-- mySheet2.ColumnSort("3"); --%>
	}

	/* 상품 삭제*/
	function productDel() {
		var chkRow = "";
		var chkCnt = 0;
		var sum = 0;

		var rowCnt = mySheet2.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			if (mySheet2.GetCellValue(i, "SELECTED") == 1) {
				chkRow += "|" + i;
				chkCnt++;
			}
		}

		sum = $("#productCnt").val() - chkCnt;
		$("#productCnt").val(sum);

		chkRow = chkRow.substring(1, chkRow.length);
		mySheet2.RowDelete(chkRow);

		rowCnt = mySheet2.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			mySheet2.SetCellValue(i, "ORDER_SEQ", i);
		}
	}

	<%-- // 마트 전카 리셋 ( 마트 전카 제거 )
	function doCategoryReset() {
		//mySheet.RemoveAll();
	} --%>

	function trdTypFgSel() {
		$.getJSON("${ctx}/edi/product/trdTypFgSel.do",null, trdTypFgSelRst);
	}

	function trdTypFgSelRst(data) {
		for (var i = 0; i < data.length; i++) {
			for (var j = 0; j < $("select[name=entpCode] option").size(); j++) {
				if (data[i].VENDOR_ID == $("select[name=entpCode] option:eq("+j+")").val()) {
					var tdrTypFgNm = data[i].TRD_TYP_FG_NM;

					if (tdrTypFgNm == null || tdrTypFgNm == "null" || tdrTypFgNm == "") {
						tdrTypFgNm = ""
					} else {
						tdrTypFgNm = "("+data[i].TRD_TYP_FG_NM+")";
					}

					$("select[name=entpCode] option:eq("+j+")").replaceWith("<option value='"+data[i].VENDOR_ID+"'>"+data[i].VENDOR_ID+tdrTypFgNm+"</option>");
				}
			}
		}

		$("select[name=entpCode]").val(vndId);
	}

	// 테마 추가
	function addThemaRow() {

		if (mySheet3.RowCount() + 1 < 7) {
			var rowIdx = mySheet3.DataInsert(-1);

			mySheet3.SetCellValue(rowIdx, "SEQ", rowIdx);
			mySheet3.SetCellValue(rowIdx, "THEMA_SEQ", rowIdx);
			mySheet3.SetCellValue(rowIdx, "ORDER_SEQ", rowIdx);
			//mySheet3.SetCellValue(rowIdx, "THEMA_NM", "");
			mySheet3.SetCellValue(rowIdx, "PROD_QTY", 0);
			var tmpThemaProd = new Array();
			mySheet3.SetCellValue(rowIdx, "THEMA_PROD", JSON.stringify(tmpThemaProd))
		}
	}

	// 테마 삭제
	function removeThemaRow() {
		var chkRow = "";
		var chkCnt = 0;
		var sum = 0; 

		var themaSeqArr = new Array();
		var rowCnt = mySheet3.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			if (mySheet3.GetCellValue(i, "SELECTED") == 1) {
				chkRow += "|"+i;
				chkCnt++;
				//console.log(mySheet3.GetCellValue(i, "THEMA_SEQ"));
			}
		}
		chkRow = chkRow.substring(1, chkRow.length);
		mySheet3.RowDelete(chkRow);
		//console.log("themaSeqArr = " + JSON.stringify(themaSeqArr));

		// THEMA_SEQ Reset
		rowCnt = mySheet3.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			mySheet3.SetCellValue(i, "SEQ", i);
			mySheet3.SetCellValue(i, "THEMA_SEQ", i);
			mySheet3.SetCellValue(i, "ORDER_SEQ", i);
		}
	}

	// 테마 상품 팝업 호출
	function themaProdPop(themaSeq) {

		var notInVal = getThemaProdCds();
		var prodDivnCd = $("#dealProdDivnCode option:selected").val();
		var onlineProdTypeCd = "etc"; //상품팝업 조회 쿼리에 isEmpty시 조건이 추가되지 않게 하기 위해서(AND A.ONLINE_PROD_TYPE_CD = '01')

		var targetUrl = "<c:url value='/edi/product/NEDMPRO005101.do'/>?vendorId="+$("#entpCode").val()+
				"&prodDivnCd=" + prodDivnCd + "&onlineProdTypeCd=" + onlineProdTypeCd + 
				"&themaSeq=" + themaSeq + "&ecLinkYn=Y" + 
				"&notInVal="+notInVal;
		Common.centerPopupWindow(targetUrl, 'themaProdPop', {width : 970, height : 330});
	}

	// 테마 상품 팝업창에서 적용시
	function themaProdApply(dealObj) {
		//console.log(JSON.stringify(dealObj));
		var themaDealProd = JSON.parse(dealObj.replaceAll("/\/" , ""));
		var seq = themaDealProd.themaSeq;
		var delSize = themaDealProd.dealProdList.length;
		mySheet3.SetCellValue(seq, "THEMA_PROD", JSON.stringify(themaDealProd.dealProdList));
		mySheet3.SetCellValue(seq, "PROD_QTY", themaDealProd.dealProdList.length);
		var repYnCnt = 0;
		var mainProdCd = "";
		var mainProdNm = "";
		for (i = 0; i < delSize; i++) {
			if (themaDealProd.dealProdList[i].mainProdYn == "Y") {
				mainProdCd = themaDealProd.dealProdList[i].prodCd;
				mainProdNm = themaDealProd.dealProdList[i].prodNm;
			}
			if (themaDealProd.dealProdList[i].repYn == "Y") {
				repYnCnt++;
			}
		}
		mySheet3.SetCellValue(seq, "MAIN_PROD_CD", mainProdCd); // 대표상품번호 세팅
		mySheet3.SetCellValue(seq, "MAIN_PROD_NM", mainProdNm); // 대표상품명 세팅
		if (repYnCnt > 0) {
			mySheet3.SetCellValue(seq, "REP_PROD_EXISTS_YN", "Y");
		} else {
			mySheet3.SetCellValue(seq, "REP_PROD_EXISTS_YN", "");
		}
	}

	// NotInVal PROD_CD
	function getThemaProdCds() {

		var themaProdCds = "";
		var rowCnt = mySheet3.RowCount();
		for (var i = 1; i < rowCnt + 1; i++) {
			var prodQty = mySheet3.GetCellValue(i, "PROD_QTY")
			if (prodQty > 0) {
				var themaProdJson = mySheet3.GetCellValue(i, "THEMA_PROD");
				themaProdJson = JSON.parse(themaProdJson);
				for (j = 0; j < themaProdJson.length; j++) {
					themaProdCds += "," + themaProdJson[j].prodCd;
				}
			}
		}
		themaProdCds = themaProdCds.substring(1, themaProdCds.length);
		//console.log("themaProdCds = " + themaProdCds);
		return themaProdCds;
	}

	//이미지 체크
	function CkImageVal(formName) {
		var oInput = event.srcElement;
		//console.log("event.srcElement = " + oInput);
		var fileName = oInput.value;
		//console.log("oInput.value = " + fileName);
		var inputNm = oInput.name;
		//console.log("oInput.name = " + inputNm);
		var formNm = formName;
		//console.log("formName = " + formName);

		if ((/(.jpg)$/i).test(fileName)) {
			oInput.parentElement.children[0].src = fileName;
		} else {
			var f = eval("document." + formNm + "." + inputNm);
			var t = "<input type=\"file\" name=\"" + inputNm + "\" ";
			var c = "onChange=\"CkImageVal('";
			var e = "')\" >";

			f.outerHTML = (t + c + formNm + e);
			alert("이미지는 jpg 파일만 가능합니다.");

			return;
		}
	}

	// 동영상 체크
	function chkVideoFile(file) {
		if ($(file).val() != "") {
			// 확장자 체크
			var ext = $(file).val().split(".").pop().toLowerCase();
			if($.inArray(ext, ["mp4", "avi", "mov", "mkv", "wma", "mpeg"]) == -1) {
				alert("mp4, avi, mov, mkv, wma, mpeg 파일만 업로드 할수 있습니다.");
				$(file).val("");
				return;
			}

			// 파일용량 체크
			var files = file.files; // files 로 해당 파일 정보 얻기.
			var limitFileSize = 10485760; // 10MB
			if (files[0].size > limitFileSize) {
				alert("동영상 파일 용량은 10MB 이내로 등록 가능 합니다.");
				$(file).val("").removeAttr("src");
				return;
			}
		}
	}

	function videoDelete() {

		if(!confirm("동영상을 삭제하시겠습니까?")) {
			return;
		}
		loadingMaskFixPos();
		var actUrl = "<c:url value='/product/deleteDealMovie.do'/>";
		$("#newProduct").attr("action", actUrl);
		$("#newProduct").attr("method", "post").submit();

		setTimeout(function() {
			$("div#content_wrap").unblock();
			hideLoadingMask();
		}, 500);
	}

</script>
</head>
<body>
<div id="content_wrap">
	<!-- @ BODY WRAP START -->
	<form name="newProduct" id="newProduct" method="post" enctype="multipart/form-data">
	<input type="hidden" name="blackListVendorFlag" id="blackListVendorFlag" value="n" />
	<input type="hidden" name="newProductCode" id="newProductCode" value="<c:out escapeXml='false' value='${param.pgmId}'/>" />
	<input type="hidden" name="tradeTypeCode" id="tradeTypeCode" value="1" />
	<input type="hidden" name="productImageId" id="productImageId" value="${newProdDetailInfo.prodImgId}" />
	<input type="hidden" name="eventSellPrice" id="eventSellPrice" value="${newProdDetailInfo.evtProdSellPrc}" />
	<input type="hidden" name="imgNcnt" id="imgNcnt" value="${newProdDetailInfo.imgNcnt}" />
	<input type="hidden" name="onlineDisplayCategoryCode"  id="onlineDisplayCategoryCode" value="${newProdDetailInfo.categoryId}" />
	<input type="hidden" name="dealRepProdYn" id="dealRepProdYn" value="Y" />
	<input type="hidden" name="officialOrder.newProductGeneratedDivnCode" value="EDI" />
	<input type="hidden" name="onOffDivnCode" value="1" />
	<input type="hidden" name="productDivnCode" value="1" />
	<input type="hidden" name="productCnt" id="productCnt" value="" />
	<input type="hidden" name="dealProdListJsonArray" id="dealProdListJsonArray" value="" />
	<input type="hidden" name="dealThemaProdListJsonObject" id="dealThemaProdListJsonObject" value="" />
	<div id="wrap_menu">
		<!-- @ 검색조건 -->
		<div class="wrap_search">

			<!-- 01 : search -->
			<div class="bbs_list" >
				<ul class="tit">
					<li class="tit">딜 상품등록</li>
					<li class="btn">
						<a href="#" class="btn" onclick="submitProductInfo();" id="saveBtn"><span><spring:message code="button.common.save"/></span></a>
					</li>
				</ul>
				<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:15%" />
						<col style="width:85%" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 딜상품구분</th>
						<td>
							<select id="dealProdDivnCode" name="dealProdDivnCode">
								<option value="01">온/오프</option>
								<option value="02" selected="selected">온라인 전용</option>
							</select>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 협력업체</th>
						<td>
							<c:choose>
								<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
									<c:if test="${not empty newProdDetailInfo.entpCd}">
										<input type="text" name="entpCode" id="entpCode" readonly="readonly" readonly="readonly" value="${newProdDetailInfo.entpCd}" style="width:40%;" />
									</c:if>
									<c:if test="${empty newProdDetailInfo.entpCd}">
										<input type="text" name="entpCode" id="entpCode" readonly="readonly" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;" />
									</c:if>
									<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
								</c:when>
								<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
									<c:if test="${not empty newProdDetailInfo.entpCd}">
										<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="${newProdDetailInfo.entpCd}" dataType="CP" comType="SELECT" formName="form" />
									</c:if>
									<c:if test="${ empty newProdDetailInfo.entpCd}">
										<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" />
									</c:if>
								</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 상품명</th>
						<td>
							<input type="text" class="required" name="productName" value="${newProdDetailInfo.prodNm}" style="width:91%;" maxlength="50" />
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 판매기간</th>
						<td>
							<input type="text" maxlength="8" class="required" id="sellStartDy" name="sellStartDy" value="${newProdDetailInfo.sellStartDy}" style="width:80px;" readonly /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.sellStartDy');" style="cursor:hand;" />
							~
							<input type="text" maxlength="8" class="required" id="sellEndDy" name="sellEndDy" value="${newProdDetailInfo.sellEndDy}" style="width:80px;" readonly /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.sellEndDy');" style="cursor:hand;" />
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>상품 카테고리</th>
						<td>
							<select id="teamCd" name="teamCd" class="required" style="width:150px;">
								<option value="">선택</option>
								<c:forEach items="${teamList}" var="teamList" varStatus="index">
									<c:if test="${fn:indexOf(teamList.teamNm,'VIC') == -1}">
										<option value="${teamList.teamCd}">${teamList.teamNm}</option>
									</c:if>
								</c:forEach>
							</select>
							<select id="l1Cd" name="l1Cd" class="required" style="width:150px;">
								<option value="">선택</option>
							</select>
							<select id="l2Cd" name="l2Cd" class="required">
								<option value="">선택</option>
							</select>
							<select id="l3Cd" name="l3Cd" class="required">
								<option value="">선택</option>
							</select>
							<%--전시카테고리 삭제 --%>
							<%-- <br/>
							<div style="overflow:hidden;">
								<li style="float:left; padding: 5px 0 0 0; font-size:11px;">
									<font color=blue>※ 최대 3개 카테고리 지정 가능하며, 추가는 전시카테고리에서 지정합니다.</font>
								</li>
								<li style="float:right; padding: 0 63px 0 0;">
									<a href="#" class="btn" onclick="javascript:addCategory();"><span>추가</span></a>
									<a href="#" class="btn" onclick="javascript:delCategory();"><span>삭제</span></a>
								</li>
								<br/>
								<div id="ibsheet1"></div>
							</div> --%>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 보기방식 선택</th>
						<td>
							<input type="radio" class="required" name="dealViewCd" id="dealViewCd1" value="GRID_VIEW" /> 그리드 방식 &nbsp;&nbsp;
							<input type="radio" class="required" name="dealViewCd" id="dealViewCd2" value="LIST_VIEW" /> 리스트 방식
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 테마사용 여부</th>
						<td>
							<input type="radio" class="required" name="themaYn" id="themaYn1" value="Y" /> Y &nbsp;&nbsp;
							<input type="radio" class="required" name="themaYn" id="themaYn2" value="N" /> N
						</td>
					</tr>
					<tr>
						<th>검색노출여부</th>
						<td>
							<input type="radio"  name="scDpYn" id="scDpYn1" value="Y" /> Y &nbsp;&nbsp;
							<input type="radio"  name="scDpYn" id="scDpYn2" value="N" /> N
						</td>
					</tr>
				</table>
			</div>
			<br/>
			<div id="ibsheet2Title" class="bbs_search" style="display:none;">
				<ul class="tit">
					<li class="tit" style="font-size:11px;">상품구성 기획전형 상품의 가격, 고시정보는 대표상품의 정보를 보여줍니다. 서비스 후 대표상품은 변경할 수 없습니다. </li>
					<li class="btn">
						<a href="#" class="btn" onclick="javascript:productAdd();"><span>상품추가</span></a>
						<a href="#" class="btn" onclick="javascript:productDel();"><span>삭제</span></a>
						<input type="hidden" id="grpProduct" name="grpProduct" />
						<input type="hidden" id="repYn" name="repYn" />
						<input type="hidden" id="orderSeq" name="orderSeq" />
					</li>
				</ul>
			</div>
			<div id="ibsheet2" style="display:none;"></div>
			<div id="ibsheet3Title" class="bbs_search" style="display:block;">
				<ul class="tit">
					<li class="tit" style="font-size:11px;"></li>
					<li class="btn">
						<a href="#" class="btn" onclick="javascript:addThemaRow();"><span>행추가</span></a>
						<a href="#" class="btn" onclick="javascript:removeThemaRow();"><span>행삭제</span></a>
					</li>
				</ul>
			</div>
			<div id="ibsheet3" style="display:block;"></div>
			<br/>
			<!-- 온라인 이미지 등록 시작 -->
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit"><a name="onlineImageAnchor">*온라인 필수 대표이미지</a> </li>
				</ul>
			</div>
			<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${param.pgmId}'/>" />
			<input type="hidden" name="prodDivnCd" id="prodDivnCd" />
			<input type="hidden" name="onOffDivnCd" id="onOffDivnCd" value="<c:out value='${newProdDetailInfo.onoffDivnCd}'/>" />
			<input type="hidden" name="uploadFieldCount" id="uploadFieldCount" value="1"/>
			<div class="bbs_search">
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td colspan="6" height="40"><b>*주의사항</b> : 파일용량 600KB 이하, (정사이즈 비율) 500x500 ~ 1500x1500 크기의 JPG파일로 등록해 주시기 바랍니다.</td>
					</tr>
					<colgroup>
						<col />
						<col />
						<col />
						<col />
						<col />
						<col />
					</colgroup>
					<tr>
						<th><span>1.</span> 500 x 500</th>
						<th>250 x 250</th>
						<th>160 x 160</th>
						<th>100 x 100</th>
						<th>75 x 75</th>
						<th>60 x 60</th>
					</tr>
					<c:forEach items="${onlineImageList}" var="imageFile" varStatus="index">
						<tr id="image_row_${index.count}" name="image_row">
							<td align="center">
								<img width="95px" src="${imagePath}/${subFolderName }/${imageFile }_500.jpg?currentSecond=${currentSecond}" name="ImgPOG1f" id="image_1_500" />
							</td>
							<td align="center">
								<img width="95px" src="${imagePath}/${subFolderName }/${imageFile }_250.jpg?currentSecond=${currentSecond}" name="ImgPOG1f" id="image_1_250" />
							</td>
							<td align="center">
								<img width="95px" src="${imagePath}/${subFolderName }/${imageFile }_208.jpg?currentSecond=${currentSecond}" name="ImgPOG1f" id="image_1_208" />
							</td>
							<td align="center">
								<img src="${imagePath}/${subFolderName }/${imageFile }_120.jpg?currentSecond=${currentSecond}" name="ImgPOG1f" id="image_1_120" />
							</td>
							<td align="center">
								<img src="${imagePath}/${subFolderName }/${imageFile }_90.jpg?currentSecond=${currentSecond}" name="ImgPOG1f" id="image_1_90" />
							</td>
							<td align="center">
								<img src="${imagePath}/${subFolderName }/${imageFile }_80.jpg?currentSecond=${currentSecond}" name="ImgPOG1f" id="image_1_80" />
							</td>
						</tr>
						<tr id="submit_row_${index.count}" name="submit_row">
							<td colspan="6" align="right" style="padding-right:40px">
								<input type="file" name="IMG_${imageFile }" onChange="CkImageVal('newProduct')" />
							</td>
						</tr>
					</c:forEach>
					<!-- 온라인 이미지 리스트가 없을경우 -->
					<c:if test="${empty onlineImageList }">
						<tr name="image_row">
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_208" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_120" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_90" />
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_80" />
							</td>
						</tr>
						<tr name="submit_row">
							<td colspan="6" align="right" style="padding-right:40px">
								<input type="file" name="front" onChange="CkImageVal('newProduct')" />
							</td>
						</tr>
					</c:if>
				</table>
			</div>
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit"><a name="onlineMovieAnchor">동영상</a></li>
				</ul>
			</div>
			<div class="bbs_search">
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td align="right" style="padding-right:40px">
							<span id="movieFileName"></span><br/>
							<input type="file" id="movieFile" name="movieFile" onChange="chkVideoFile(this);" />
							<c:if test="${not empty viewVideoUrl}">
							<a href="javascript:videoDelete();" class="btn"><span>삭제</span></a>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
			<div class="bbs_search">
				<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<th>구분</th>
						<th>권장사항</th>
						<th>등록 가능</th>
					</tr>
					<tr>
						<td style="text-align:center;">파일형식</td>
						<td style="text-align:center;color:red;font-weight:bold;">MP4</td>
						<td>MP4, AVI, MOV, MKV, WMA, MPEG-4</td>
					</tr>
					<tr>
						<td style="text-align:center">파일용량</td>
						<td style="text-align:center;color:red;font-weight:bold;">10MB 미만</td>
						<td>동영상 포함 딜코드 총 용량 최대 10MB까지 가능</td>
					</tr>
					<tr>
						<td style="text-align:center">화면비율</td>
						<td style="text-align:center;color:red;font-weight:bold;">16:9</td>
						<td>화면 비율이 맞지 않은 경우 여백이 발생 할 수 있습니다.</td>
					</tr>
					<tr>
						<td style="text-align:center">재생시간</td>
						<td>20초 ~ 2분 이내</td>
						<td></td>
					</tr>
				</table>
			</div>
			<div>
			<strong><font color="red">* 동영상 등록시 노출 영역</font></strong><br/>
			- 묶음상품 상품상세 대표이미지 영역에 노출됩니다. (이미지 > 동영상 순)
			</div>
			<textarea id="template" style="display:none;">
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500" />
				</td>
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250" />
				</td>
				<td align="center" >
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_208" />
				</td>
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_120" />
				</td>
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_90" />
				</td>
				<td align="center" >
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_80" />
				</td>
			</textarea>
			<!-- 온라인 이미지 등록 끝 -->
			<br/>
			<!-- editor 시작 -->
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">*상세이미지 : 온라인에서 사용될 설명이며 <font color="red"><b>필수</b></font>사항입니다.</li>
				</ul>
				<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<input type="hidden" name="productDescription" id="productDescription" value="<c:out value='${newProdDetailInfo.prodDesc}'/>" />
							<script type="text/javascript" language="javascript">
								var CrossEditor = new NamoSE("pe_agt");

								CrossEditor.params.Width = "100%";
								CrossEditor.params.UserLang = "auto";
								CrossEditor.params.FullScreen = false;
								CrossEditor.params.SetFocus = false; // 에디터 포커스 해제
								CrossEditor.params.ImageSavePath = "edi";
								CrossEditor.EditorStart();

								function OnInitCompleted(e) {
									e.editorTarget.SetBodyStyle("");
									e.editorTarget.SetBodyValue(document.getElementById("productDescription").value);
								}
							</script>
						</td>
					</tr>
				</table>
			</div>
			<!-- editor 끝 -->
		</div>
	</form>
</div>
</body>
</html>
