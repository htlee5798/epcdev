<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="lcn.module.common.util.DateUtil" %>
<%@ page import="com.lottemart.common.util.DataMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String toDate   = DateUtil.addDay(DateUtil.getToday(), 0);
	String nextDate = DateUtil.addDay(DateUtil.getToday(), 1);
	String returnDeliNextDate = DateUtil.addDay(DateUtil.getToday(), 1);

	int returnDeliToDate = Integer.parseInt(toDate);
	if (returnDeliToDate < 20200428) {
		returnDeliNextDate ="20200428";
	}

	String utakTypeCd = "";
	String utakTypeNm = "";

	List<DataMap> codeList4 = (List<DataMap>) request.getAttribute("codeList4");
	for (int i = 0; i < codeList4.size(); i++) {
		DataMap m = codeList4.get(i);
		utakTypeCd = utakTypeCd + (i > 0 ? "|" : "") + m.getString("MINOR_CD");
		utakTypeNm = utakTypeNm + (i > 0 ? "|" : "") + m.getString("CD_NM");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<c:set var="sm336Cd" />
<c:set var="sm336Nm" />
<c:forEach items="${codeList3}" var="sm336Code" varStatus="idx1">
	<c:choose>
		<c:when test="${ fn:length(codeList3) eq idx1.index+1  }">
			<c:set var="sm336Cd" value="${ sm336Cd }${ sm336Code.MINOR_CD }"/>
			<c:set var="sm336Nm" value="${ sm336Nm }${ sm336Code.CD_NM  }"/>
		</c:when>
		<c:otherwise>
			<c:set var="sm336Cd" value="${ sm336Cd }${ sm336Code.MINOR_CD }|"/>
			<c:set var="sm336Nm" value="${ sm336Nm }${ sm336Code.CD_NM }|"/>
		</c:otherwise>
	</c:choose>
</c:forEach>
<!-- system/PSCMSYS0004 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script type="text/javascript">

	/**********************************************************
	 * jQeury 초기화
	 *********************************************************/
	$(document).ready(function() {

		initIBSheetGrid1(); //그리드 초기화
		initIBSheetGrid2(); //그리드 초기화
		initIBSheetGrid3(); //그리드 초기화
		initIBSheetGrid4(); //그리드 초기화

		selectVendorUserList(); // 담당자조회
		selectVendorAddrList(); // 업체주소조회
		selectVendorDeliAmtList(); // 업체기준배송비조회_주문배송비
		selectVendorReturnDeliAmtList(); // 업체기준배송비조회_반품배송비

		$("#spread").click(function(){ // 펼치기
			$("#ibsheet1").css("height", 320);
			$("#spread").hide();
			$("#fold").show();
		});
		$("#fold").click(function(){ // 접기
			$("#ibsheet1").css("height", 160);
			$("#spread").show();
			$("#fold").hide();
		});

	}); // end of ready

	/**********************************************************
	 * ibsheet 초기화
	 *********************************************************/
	function initIBSheetGrid1() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet1", "100%", "160px");
		mySheet1.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet1.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:0};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번",			Type:"Int", 	SaveName:"num",				Align:"Center", Width:30,	Edit:0}
		  , {Header:"상태",			Type:"Status",  SaveName:"S_STATUS",		Align:"Center", Width:30,	Edit:0}
		  , {Header:"삭제",			Type:"DelCheck",SaveName:"chk",	 			Align:"Center", Width:30,	Sort:false}
		  , {Header:"협력사",			Type:"Text",	SaveName:"vendorId",		Align:"Center", Width:50,	Edit:0}
		  , {Header:"협력사순번",		Type:"Text",	SaveName:"vendorSeq",		Align:"Center", Width:50,	Edit:0,   Hidden:true}
		  , {Header:"구분",			Type:"Combo",	SaveName:"utakType",		Align:"Center", Width:100,	Edit:1,   ComboCode:"<%=utakTypeCd%>" , ComboText:"<%=utakTypeNm%>"}
		  , {Header:"*성명",			Type:"Text",	SaveName:"utakNm",			Align:"Center", Width:80,	Edit:1,	  EditLen:100}
		  , {Header:"부서",			Type:"Text",	SaveName:"utakDeptNm",		Align:"Center", Width:80,	Edit:1,	  EditLen:100}
		  , {Header:"직위",			Type:"Text",	SaveName:"utakPositionNm",	Align:"Center", Width:80,	Edit:1,	  EditLen:100}
		  , {Header:"전화번호",		Type:"Text",	SaveName:"utakTelNo",		Align:"Center", Width:90,	Edit:0,	  EditLen:12}
		  , {Header:"*휴대폰번호",		Type:"Text",	SaveName:"utakCellNo",		Align:"Center", Width:90,	Edit:1,	  EditLen:12}
		  , {Header:"팩스번호",		Type:"Text",	SaveName:"utakFaxNo",		Align:"Center", Width:90,	Edit:1,	  EditLen:12}
		  , {Header:"이메일",			Type:"Text",	SaveName:"email",			Align:"Left", 	Width:100,	Edit:1,	  EditLen:150}
		  , {Header:"SMS수신",		Type:"Combo",	SaveName:"smsRecvYn",		Align:"Center", Width:60,	Edit:1,   ComboCode:"Y|N", ComboText:"Y|N"}
		  , {Header:"등록일",			Type:"Text",	SaveName:"regDate",			Align:"Center", Width:80,	Edit:0}
		  , {Header:"비고",			Type:"Text",	SaveName:"rmk",				Align:"Left", 	Width:150,	Edit:1,	  EditLen:100}
		  , {Header:"우편번호",		Type:"Text",	SaveName:"zipCd",			Align:"Center", Width:60,	Edit:0}
		  , {Header:"(반품지)주소1",	Type:"Text",	SaveName:"addr1Nm",			Align:"Left", 	Width:160,	Edit:0}
		  , {Header:"(반품지)주소2",	Type:"Text",	SaveName:"addr2Nm",			Align:"Left", 	Width:160,	Edit:0,	  EditLen:200}
		  , {Header:"*유효여부",		Type:"Combo",	SaveName:"valiYN",			Align:"Center", Width:60,	Edit:1,   ComboCode:"Y|N", ComboText:"Y|N"}
		];

		IBS_InitSheet(mySheet1, ibdata);

		mySheet1.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet1.SetHeaderRowHeight(Ibs.HeaderHeight);
	}

	/**********************************************************
	 * ibsheet 초기화
	 *********************************************************/
	function initIBSheetGrid2() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "140px");
		mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet2.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:0};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번",		Type:"Int", 	SaveName:"num", 		Align:"Center", Width:30,  	Edit:0}
		  , {Header:"상태",		Type:"Status",  SaveName:"S_STATUS",	Align:"Center", Width:30,   Edit:0}
		  , {Header:"삭제",		Type:"DelCheck",SaveName:"chk",	 		Align:"Center", Width:30,	Sort:false}
		  , {Header:"거래처ID",	Type:"Text", 	SaveName:"vendorId",	Align:"Center", Width:70, 	Edit:0,   Hidden:false}
		  , {Header:"주소순번",	Type:"Text", 	SaveName:"addrSeq",	  	Align:"Center", Width:80,	Edit:0,   Hidden:true}
		  , {Header:"주소종류",	Type:"Combo", 	SaveName:"addrKindCd",	Align:"Center", Width:90,	Edit:1,	  ComboText:"<c:out value="${ sm336Nm }"/>",ComboCode:"<c:out value="${ sm336Cd }"/>"}
		  , {Header:"*우편번호",	Type:"Text", 	SaveName:"zipCd",	 	Align:"Center", Width:80,	Edit:0}
		  , {Header:"*기본주소",	Type:"Text", 	SaveName:"addr_1_nm",	Align:"Left",   Width:180,	Edit:0}
		  , {Header:"*상세주소",	Type:"Text", 	SaveName:"addr_2_nm",	Align:"Left",   Width:130,	Edit:1,	  EditLen:200 }
		  , {Header:"*연락처",		Type:"Text", 	SaveName:"cellNo",	 	Align:"Center", Width:100,	Edit:1,	  EditLen:12,   Format:"###-####-####"}
		  , {Header:"사용여부",	Type:"Combo", 	SaveName:"useYn", 		Align:"Center", Width:60,	Edit:1,   ComboCode:"Y|N", ComboText:"Y|N"}
		];

		IBS_InitSheet(mySheet2, ibdata);

		mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet2.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet2.FitColWidth();
	}

	/** ********************************************************
	 * ibsheet 초기화
	 ******************************************************** */
	function initIBSheetGrid3() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet3"), "mySheet3", "100%", "70px");
		mySheet3.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet3.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:0};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 		Type:"Int", 	SaveName:"num", 		  	Align:"Center", Width:30,  	Edit:0}
		  , {Header:"상태",		Type:"Status",  SaveName:"S_STATUS",	  	Align:"Center", Width:30,   Edit:0}
		  , {Header:"거래처ID",	Type:"Text", 	SaveName:"VENDOR_ID",	  	Align:"Center", Width:100, 	Edit:0}
		  , {Header:"적용시작일자",	Type:"Date", 	SaveName:"APPLY_START_DY",  Align:"Center", Width:120, 	Edit:0}
		  , {Header:"적용종료일자",	Type:"Date", 	SaveName:"APPLY_END_DY",	Align:"Center", Width:120, 	Edit:0}
		  , {Header:"기준최소금액",	Type:"Int", 	SaveName:"DELI_BASE_MIN_AMT",Align:"Right", Width:120,	Edit:0, EditLen:10}
		  , {Header:"기준최대금액",	Type:"Int", 	SaveName:"DELI_BASE_MAX_AMT",Align:"Right", Width:120,	Edit:0, EditLen:10}
		  , {Header:"배송비",		Type:"Int", 	SaveName:"DELIVERY_AMT",	Align:"Right",  Width:120,	Edit:0, EditLen:10}
		  , {Header:"배송비구분",	Type:"Int", 	SaveName:"DELI_DIVN_CD",	Align:"Center", Width:50,	Edit:0,   Hidden:true}
		];

		IBS_InitSheet(mySheet3, ibdata);

		mySheet3.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet3.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet3.FitColWidth();
	}

	function initIBSheetGrid4() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet4"), "mySheet4", "100%", "70px");
		mySheet4.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet4.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:0};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
		  	{Header:"순번",		Type:"Int", 	SaveName:"num", 		  	Align:"Center", Width:30,  	Edit:0}
		  , {Header:"상태",		Type:"Status",  SaveName:"S_STATUS",	  	Align:"Center", Width:30,   Edit:0}
		  , {Header:"거래처ID",	Type:"Text", 	SaveName:"VENDOR_ID",	  	Align:"Center", Width:100, 	Edit:0}
		  , {Header:"적용시작일자",	Type:"Date", 	SaveName:"APPLY_START_DY",  Align:"Center", Width:120, 	Edit:0}
		  , {Header:"적용종료일자",	Type:"Date", 	SaveName:"APPLY_END_DY",	Align:"Center", Width:120, 	Edit:0}
		  , {Header:"기준최소금액",	Type:"Int", 	SaveName:"DELI_BASE_MIN_AMT",Align:"Right", Width:120,	Edit:0, EditLen:10, Hidden:true}
		  , {Header:"기준최대금액",	Type:"Int", 	SaveName:"DELI_BASE_MAX_AMT",Align:"Right", Width:120,	Edit:0, EditLen:10, Hidden:true}
		  , {Header:"배송비",		Type:"Int", 	SaveName:"DELIVERY_AMT",	 Align:"Right",  Width:120,	Edit:0, EditLen:10}
		  , {Header:"배송비구분",	Type:"Int", 	SaveName:"DELI_DIVN_CD",	 Align:"Center", Width:50,	Edit:0,   Hidden:true}
		];

		IBS_InitSheet(mySheet4, ibdata);

		mySheet4.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet4.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet4.FitColWidth();
	}

	/**********************************************************
	 * 업체정보 조회 처리 함수
	 *********************************************************/
	function doSearch() {
		var form = document.adminForm;
		//조회기간 체크
		if (!doDateCheck()) {
			return false;
		}

		var url = '<c:url value="/system/selectVendorInfoMgr.do"/>'+'?vendorId='+$('#vendorId').val();
		form.action = url;
		form.submit();
	}

	// 업체담당자조회
	function selectVendorUserList() {
		var url = '<c:url value="/system/selectVendorUserList.do"/>';
		var param = new Object();
		param.vendorId = $("#vendorId").val();

		loadIBSheetData(mySheet1, url, null, null, param);
	}

	// 업체주소조회
	function selectVendorAddrList() {
		var url = '<c:url value="/system/selectVendorAddrList.do"/>';
		var param = new Object();
		param.vendorId = $("#vendorId").val();

		loadIBSheetData(mySheet2, url, null, null, param);
	}

	// 업체기준배송비조회_주문배송비
	function selectVendorDeliAmtList() {
		var url = '<c:url value="/system/selectVendorDeliAmtList.do"/>';
		var param = new Object();
		param.vendorId = $("#vendorId").val();
		param.nextDate = "<%=nextDate%>";
		param.deliDivnCd = "10";

		loadIBSheetData(mySheet3, url, null, null, param);
	}

	// 업체기준배송비조회_반품배송비
	function selectVendorReturnDeliAmtList() {
		var url = '<c:url value="/system/selectVendorDeliAmtList.do"/>';
		var param = new Object();
		param.vendorId = $("#vendorId").val();
		param.nextDate = "<%=nextDate%>";
		param.deliDivnCd = "20";

		loadIBSheetData(mySheet4, url, null, null, param);
	}

	/**********************************************************
	 * 업체정보 수정 처리 함수
	 *********************************************************/
	function doVendorUpdate() {

		if ($('#vendorNm').val() == "") {
			alert("업체명을 입력하세요.");
			$('#vendorNm').focus();
			return;
		}

		if ($('#ceoNm').val() == "") {
			alert("대표자명을 입력하세요.");
			$('#ceoNm').focus();
			return;
		}

		if ($('#cono').val() == "") {
			alert("사업자번호를 입력하세요.");
			$('#cono').focus();
			return;
		}

		if ($('#vendorTypeCd').val() == "") {
			alert("거래유형를 선택하세요.");
			$('#vendorTypeCd').focus();
			return;
		}

		<%-- //if ($('#zipCd').val() == "") {
		//	alert("주소를 입력하세요.");
		//	$('#zipCd').focus();
		//	return;
		//} --%>

		if ($('#addr_1_nm').val() == "") {
			alert("기본주소를 입력하세요.");
			$('#addr_1_nm').focus();
			return;
		}

		if ($('#addr_2_nm').val() == "") {
			alert("상세주소를 입력하세요.");
			$('#addr_2_nm').focus();
			return;
		}

		// 20160810 삭제
		//if ($('#baseDlvAmt').val() == 0) {
		//	alert("기본배송비는 0보다 크게 입력하세요.");
		//	$('#baseDlvAmt').focus();
		//	return;
		//}

		// 값이 비어있으면 0으로 셋팅
		if ($('#rtnAmt').val() == '') $('#rtnAmt').val("0");
		if ($('#exchAmt').val() == '') $('#exchAmt').val("0");
		if ($('#addDeliAmt1').val() == '') $('#addDeliAmt1').val("0");
		if ($('#addDeliAmt2').val() == '') $('#addDeliAmt2').val("0");

		if (!confirm("저장하시겠습니까?")) {
			return;
		}

		var form = document.adminForm;
		var url = '<c:url value="/system/updateVendorInfo.do"/>';

		form.target = "updatefrm";
		form.action = url;
		form.submit();
		form.target = "";
	}

	var m2Row = "";
	var m2Col = "";

	//셀 클릭시 발생하는 이벤트(업체담당자주소)
	function mySheet1_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		mRow = Row;
		mCol = Col;

		if ( Row > 0 && (mySheet1.ColSaveName(Col) == "utakTelNo" ||
						mySheet1.ColSaveName(Col) == "zipCd" ||
						mySheet1.ColSaveName(Col) == "addr1Nm" ||
						mySheet1.ColSaveName(Col) == "addr2Nm") ) {
			alert("전화번호, 반품지 주소 정보는 등록/수정이 불가합니다.\n수정이 필요한 경우 관리자에게 문의해주세요.");
			return;
		}
		<%-- //우편번호 컬럼 클릭시 주소팝업 호출
		if ( Row > 0 &&  mySheet1.ColSaveName(Col) == "zipCd") {
			$('#gubun').val("02"); // mySheet1의 우편번호 클릭시 구분값
			var url = '<c:url value="/system/zip/PEDPZIP0003List.do"/>';
			Common.centerPopupWindow(url, 'zipSearchPopUp', {width : 500, height : 480});
		} --%>
	}

	//셀 클릭시 발생하는 이벤트(업체주소)
	function mySheet2_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		mRow = Row;
		mCol = Col;
		//우편번호 컬럼 클릭시 주소팝업 호출
		if ( Row > 0 &&  mySheet2.ColSaveName(Col) == "zipCd") {
			$('#gubun').val("03");  // mySheet2의 우편번호 클릭시 구분값
			var url = '<c:url value="/system/zip/PEDPZIP0003List.do"/>';
			Common.centerPopupWindow(url, 'zipSearchPopUp', {width : 500, height : 480});
		}
	}

	//셀 클릭시 발생하는 이벤트
	function mySheet3_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		mRow = Row;
		mCol = Col;

		//적용시작일자보다 현재일+1보다 작으면 삭제불가
		if ( Row > 0 &&  mySheet3.ColSaveName(Col) == "chk") {
			if (mySheet3.GetCellValue(mRow, "APPLY_START_DY") < "<%=nextDate%>" && mySheet3.GetCellValue(mRow, "APPLY_START_DY") != -1) {
				alert("적용시작일이 " + <%=nextDate%> + "일보다 작으면 삭제가 불가합니다.");
				mySheet3.SetCellValue(mRow, "chk", false);
			}
			if (mySheet3.GetCellValue(mRow, "APPLY_START_DY") == -1 && mRow > 1) {
				mySheet3.SetCellValue(mRow-1, "APPLY_END_DY", "99991231");
			}
		}
	}

	function mySheet4_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		mRow = Row;
		mCol = Col;

		//적용시작일자보다 현재일+1보다 작으면 삭제불가
		if ( Row > 0 &&  mySheet3.ColSaveName(Col) == "chk") {
			if (mySheet4.GetCellValue(mRow, "APPLY_START_DY") < "<%=nextDate%>" && mySheet4.GetCellValue(mRow, "APPLY_START_DY") != -1) {
				alert("적용시작일이 " + <%=nextDate%> + "일보다 작으면 삭제가 불가합니다.");
				mySheet4.SetCellValue(mRow, "chk", false);
			}
			if (mySheet4.GetCellValue(mRow, "APPLY_START_DY") == -1 && mRow > 1) {
				mySheet4.SetCellValue(mRow-1, "APPLY_END_DY", "99991231");
			}
		}
	}

	//주소 조회 함수
	function openZipCodeNew() {
		$('#gubun').val("01"); // form의 우편번호 클릭시 구분값
		var url = '<c:url value="/system/zip/PEDPZIP0003List.do"/>';
		Common.centerPopupWindow(url, 'zipSearchPopUp', {width : 500, height : 480});
	}

	function setAddr(postNo1,postNo2,addr) {

		if ($("#gubun").val() == "01") {
			$("#zipCd").val(postNo1 + postNo2);
			$("#addr_1_nm").val(addr);
		} else if ($("#gubun").val() == "02") {
			mySheet1.SetCellValue(mRow, "zipCd", postNo1 + postNo2);
			mySheet1.SetCellValue(mRow, "addr1Nm", addr);
		} else if ($("#gubun").val() == "03") {
			mySheet2.SetCellValue(mRow, "zipCd", postNo1 + postNo2);
			mySheet2.SetCellValue(mRow, "addr_1_nm", addr);
		}
		$("#gubun").val("");
	}

	// 업체주소 추가
	function doAddrAdd() {
		// 마지막에 행추가
		mySheet2.DataInsert(mySheet2.RowCount()+1);
		mySheet2.SetCellValue(mySheet2.RowCount(), "vendorId", $("#vendorId").val());
	}

	// 업체기준배송비 신규추가
	function doDeliAdd() {
		var intRow = mySheet3.RowCount();
		// 마지막에 행추가
		if (intRow == 0) {
			mySheet3.DataInsert(intRow+1);
			mySheet3.SetCellValue(intRow+1, "VENDOR_ID", $("#vendorId").val());
			mySheet3.SetCellValue(intRow+1, "APPLY_START_DY", <%=nextDate%>);
			mySheet3.SetCellValue(intRow+1, "APPLY_END_DY", "99991231");
			mySheet3.SetCellValue(intRow+1, "DELI_BASE_MIN_AMT", 0);
			mySheet3.SetCellEditable(intRow+1, "DELI_BASE_MAX_AMT", 1);
			mySheet3.SetCellEditable(intRow+1, "DELIVERY_AMT", 1);
			mySheet3.SetCellValue(intRow+1, "DELI_DIVN_CD", 10);
		}
	}

	// 업체기준 반품배송비 신규추가
	function doReturnDeliAdd() {
		var intRow = mySheet4.RowCount();
		// 마지막에 행추가
		if (intRow == 0) {
			mySheet4.DataInsert(intRow+1);
			mySheet4.SetCellValue(intRow+1, "VENDOR_ID", $("#vendorId").val());
			mySheet4.SetCellValue(intRow+1, "APPLY_START_DY", <%=returnDeliNextDate%>);
			mySheet4.SetCellValue(intRow+1, "APPLY_END_DY", "99991231");
			mySheet4.SetCellValue(intRow+1, "DELI_BASE_MIN_AMT", 0);
			mySheet4.SetCellValue(intRow+1, "DELI_BASE_MAX_AMT", 0);
			mySheet4.SetCellEditable(intRow+1, "DELIVERY_AMT", 1);
			mySheet4.SetCellValue(intRow+1, "DELI_DIVN_CD", 20);
		}
	}

	// 업체기준배송비 수정
	function doDeliUpdate() {
		var intRow = mySheet3.RowCount();

		// 적용시작일자가 생성일이 크면 행추가 안하고 수정으로 처리
		if (mySheet3.GetCellValue(intRow, "APPLY_START_DY") >= "<%=nextDate%>") {
			mySheet3.SetCellEditable(intRow, "DELI_BASE_MAX_AMT", 1);
			mySheet3.SetCellEditable(intRow, "DELIVERY_AMT", 1);
		} else {
			// 체크된 행다음에 추가
			mySheet3.DataInsert(intRow+1);
			mySheet3.SetCellValue(intRow+1, "VENDOR_ID", $("#vendorId").val());
			mySheet3.SetCellValue(intRow+1, "APPLY_START_DY", <%=nextDate%>);
			mySheet3.SetCellValue(intRow+1, "APPLY_END_DY", "99991231");
			mySheet3.SetCellValue(intRow+1, "DELI_BASE_MIN_AMT", 0);
			mySheet3.SetCellEditable(intRow+1, "DELI_BASE_MAX_AMT", 1);
			mySheet3.SetCellEditable(intRow+1, "DELIVERY_AMT", 1);
			mySheet3.SetCellValue(intRow+1, "DELI_BASE_MAX_AMT", mySheet3.GetCellValue(intRow, "DELI_BASE_MAX_AMT") );
			mySheet3.SetCellValue(intRow+1, "DELIVERY_AMT", mySheet3.GetCellValue(intRow, "DELIVERY_AMT") );
			mySheet3.SetCellValue(intRow, "APPLY_END_DY", <%=toDate%>);
			mySheet3.SetCellValue(intRow+1, "DELI_DIVN_CD", 10);
		}
	}

	// 업체기준 반품 배송비 수정
	function doReturnDeliUpdate() {
		var intRow = mySheet4.RowCount();

		// 적용시작일자가 생성일이 크면 행추가 안하고 수정으로 처리
		if (mySheet4.GetCellValue(intRow, "APPLY_START_DY") >= "<%=returnDeliNextDate%>") {
			mySheet4.SetCellValue(intRow, "DELI_BASE_MAX_AMT", 0);
			mySheet4.SetCellEditable(intRow, "DELIVERY_AMT", 1);
		} else {
			// 체크된 행다음에 추가
			mySheet4.DataInsert(intRow+1);
			mySheet4.SetCellValue(intRow+1, "VENDOR_ID", $("#vendorId").val());
			mySheet4.SetCellValue(intRow+1, "APPLY_START_DY", <%=returnDeliNextDate%>);
			mySheet4.SetCellValue(intRow+1, "APPLY_END_DY", "99991231");
			mySheet4.SetCellValue(intRow+1, "DELI_BASE_MIN_AMT", 0);
			mySheet4.SetCellValue(intRow+1, "DELI_BASE_MAX_AMT", 0);
			mySheet4.SetCellEditable(intRow+1, "DELIVERY_AMT", 1);
			mySheet4.SetCellValue(intRow+1, "DELIVERY_AMT", mySheet4.GetCellValue(intRow, "DELIVERY_AMT") );
			mySheet4.SetCellValue(intRow, "APPLY_END_DY", <%=toDate%>);
			mySheet4.SetCellValue(intRow+1, "DELI_DIVN_CD", 20);
		}
	}

	function doUserAdd() {
		// 마지막에 행추가
		mySheet1.DataInsert(mySheet1.RowCount()+1);
		mySheet1.SetCellValue(mySheet1.RowCount(), "vendorId", $("#vendorId").val());
	}

	// 업체담당자 저장
	function doUserSave() {
		var rowCnt = mySheet1.RowCount() + 1;
		var chkCnt = 0;

		// 필수항목 체크
		for (var i = 1; i < rowCnt; i++) {
			if (mySheet1.GetCellValue(i, "S_STATUS") == "I" || mySheet1.GetCellValue(i, "S_STATUS") == "U") {
				chkCnt++;

				if (mySheet1.GetCellValue(i, "utakNm") == "") {
					alert("[" + i + "]행에 성명을 입력하세요.");
					mySheet1.SetSelectCol("utakNm");
					return;
				}

				if (mySheet1.GetCellValue(i, "utakType") == "") {
					alert("[" + i + "]행에 담당자 구분을 선택하세요.");
					mySheet1.SetSelectCol("utakType");
					return;
				}

				if (mySheet1.GetCellValue(i, "utakCellNo") == "") {
					alert("[" + i + "]행에 휴대폰번호를 입력하세요.");
					mySheet1.SetSelectCol("utakCellNo");
					return;
				}

			}
		}

		if ( confirm("저장하시겠습니까?")) {
			mySheet1.DoSave( '<c:url value="/system/vendorUserListSave.do"/>', {Quest:0});
		}
	}

	function checkData(data) {
		var regex = /[^0-9]/g;
		return regex.test(data);
	}

	function mySheet1_OnChange(Row, Col, Value) {
		if (mySheet1.ColSaveName(Col) == "utakTelNo" || mySheet1.ColSaveName(Col) == "utakCellNo" || mySheet1.ColSaveName(Col) == "utakFaxNo") {
			if (Value != "") {
				if (checkData(Value)) {
					alert("숫자만 입력 가능합니다.");
					mySheet1.SetCellValue(Row, mySheet1.ColSaveName(Col), "");
					return;
				}
			}
		}
	}

	// 업체주소 저장
	function doAddrSave() {
		var rowCnt = mySheet2.RowCount() + 1;
		var chkCnt = 0;
		var chk01  = 0;
		var chk02  = 0;

		// 필수항목 체크
		for (var i = 1; i < rowCnt; i++) {
			if (mySheet2.GetCellValue(i, "S_STATUS") == "I" || mySheet2.GetCellValue(i, "S_STATUS") == "U") {
				chkCnt++;

				if (mySheet2.GetCellValue(i, "zipCd") == "") {
					alert("[" + i + "]행에 우편번호를 입력하세요.");
					mySheet2.SetSelectCol("zipCd");
					return;
				}
				if (mySheet2.GetCellValue(i, "addr_1_nm") == "") {
					alert("[" + i + "]행에 기본주소를 입력하세요.");
					mySheet2.SetSelectCol("addr_1_nm");
					return;
				}
				if (mySheet2.GetCellValue(i, "addr_2_nm") == "") {
					alert("[" + i + "]행에 상세주소를 입력하세요.");
					mySheet2.SetSelectCol("addr_2_nm");
					return;
				}
				if (mySheet2.GetCellValue(i, "cellNo") == "") {
					alert("[" + i + "]행에 연락처를 입력하세요.");
					mySheet2.SetSelectCol("cellNo");
					return;
				}
			}

			if (mySheet2.GetCellValue(i, "addrKindCd") == "01") {
				chk01++;
			}
			if (mySheet2.GetCellValue(i, "addrKindCd") == "02") {
				chk02++;
			}

			if (mySheet2.GetCellValue(i, "S_STATUS") == "D" && mySheet2.GetCellValue(i, "addrKindCd") != "03") {
				alert("기본출고지 및 기본반품지는 필수 정보로 삭제가 불가합니다.\n주소지 변경이 필요할 경우 등록된 주소지를 수정하시기를 바랍니다.");
				return;
			}
		}

		if (chk01 > 1) {
			alert("기본출고지는 하나만 선택하세요.");
			return;
		}

		if (chk02 > 1) {
			alert("기본반품지는 하나만 선택하세요.");
			return;
		}

		if ( confirm("저장하시겠습니까?")) {
			mySheet2.DoSave( '<c:url value="/system/vendorAddrListSave.do"/>', {Quest:0});
		}
	}

	// 업체기준배송비 저장
	function doDeliSave() {
		var rowCnt = mySheet3.RowCount() + 1;

		// 필수항목 체크
		for (var i = 1; i < rowCnt; i++) {
			if (mySheet3.GetCellValue(i, "DELI_BASE_MAX_AMT") <= 0) {
				alert("[" + i + "]행에 기준최대금액은 0 보다 큰 값을 입력하세요.");
				mySheet3.SetSelectCol("DELI_BASE_MAX_AMT");
				return;
			}

			if (mySheet3.GetCellValue(i, "DELIVERY_AMT") <= 0) {
				alert("[" + i + "]행에 배송비는 0 보다 큰 값을 입력하세요.");
				mySheet3.SetSelectCol("DELIVERY_AMT");
				return;
			}
		}

		var deliApplyStartDy = '<c:out value="${vData.deliApplyStartDy}" />';
		if ( deliApplyStartDy != "" && mySheet3.GetCellValue(1, "APPLY_START_DY") == <%=nextDate%> ) {
			alert("주문배송비는 1일 1회만 수정할 수 있습니다.\n주문배송비는 익일 반영 됩니다.")
			return;
		}

		if ( confirm("저장하시겠습니까?")) {
			mySheet3.DoSave( '<c:url value="/system/vendorDeliListSave.do"/>', {Quest:0});
		}
	}

	// 업체기준 반품 배송비 저장
	function doReturnDeliSave() {
		var rowCnt = mySheet4.RowCount() + 1;

		// 필수항목 체크
		for (var i = 1; i < rowCnt; i++) {
			if (mySheet4.GetCellValue(i, "DELIVERY_AMT") < 0) {
				alert("[" + i + "]행에 배송비는 0 보다 큰 값을 입력하세요.");
				mySheet4.SetSelectCol("DELIVERY_AMT");
				return;
			}
		}

		var returnApplyStartDy = '<c:out value="${vData.returnApplyStartDy}" />';
		if ( returnApplyStartDy != "" && mySheet4.GetCellValue(1, "APPLY_START_DY") == <%=nextDate%> ) {
			alert("반품배송비는 1일 1회만 수정할 수 있습니다.\n반품배송비는 익일 반영 됩니다.")
			return;
		}

		if ( confirm("저장하시겠습니까?")) {
			mySheet4.DoSave( '<c:url value="/system/vendorDeliListSave.do"/>', {Quest:0});
		}
	}

	//데이터 읽은 직후 이벤트
	function mySheet3_OnSearchEnd() {
		// Row가 0이면 추가버튼이보이고 수정버튼은 안보이게 처리
		if (mySheet3.RowCount() == 0) {
			$("#deliAdd").show();
			$("#deliUpdate").hide();
		} else {
			$("#deliAdd").hide();
			$("#deliUpdate").show();
		}
	}

	//데이터 읽은 직후 이벤트
	function mySheet4_OnSearchEnd() {
		// Row가 0이면 추가버튼이보이고 수정버튼은 안보이게 처리
		if (mySheet4.RowCount() == 0) {
			$("#returnDeliAdd").show();
			$("#returnDeliUpdate").hide();
		} else {
			$("#returnDeliAdd").hide();
			$("#returnDeliUpdate").show();
		}
	}

	/**********************************************************
	 * 조회기간 체크
	 ******************************************************** */
	function doDateCheck() {
		if ($("#vendorId").val() == "") {
			alert ("업체정보를 확인하시기 바랍니다.");
			return false;
		}

		return true;
	}

	//저장완료후 발생하는 이벤트(삭제 메세지 후 IBSheet 데이터 조회)
	function mySheet1_OnSaveEnd(code, Msg) {
		alert(Msg);
		selectVendorUserList();
	}

	function mySheet2_OnSaveEnd(code, Msg) {
		alert(Msg);
		selectVendorAddrList();
	}

	function mySheet3_OnSaveEnd(code, Msg) {
		alert(Msg);
		selectVendorDeliAmtList();
	}

	function mySheet4_OnSaveEnd(code, Msg) {
		alert(Msg);
		selectVendorReturnDeliAmtList();
	}

	/**********************************************************
	 * 등록버튼 클릭시 이벤트
	 *********************************************************/
	function doVendorInsert() {
		var targetUrl = '<c:url value="/system/insertPartnerPopUp.do"/>';
		Common.centerPopupWindow(targetUrl, "insert", {width : 600, height : 250});
	}

	/**********************************************************
	 * 주문 배송비 변경이력조회 클릭시 이벤트
	 *********************************************************/
	function doDeliChgHistSrch(deliDivnCd) {
		var vendorId  = $("#vendorId").val();
		var targetUrl = '<c:url value="/system/viewDeliChgHistPopUp.do"/>'+'?vendorId='+$('#vendorId').val()+'&deliDivnCd='+deliDivnCd;

		Common.centerPopupWindow(targetUrl, "insert", {width : 700, height : 300});
	}

	/**********************************************************
	 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
	 *********************************************************/
	function fnChkSpcCharByte() {
		var str1 = document.adminForm.userSrchNm.value;
		var str2 = document.adminForm.prodSrchNm.value;
		var len = 0;
		var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

		if (str1.search(exp) != -1 && str2.search(exp) != -1) {
			alert("검색항목에는 특수문자를 사용할수 없습니다!");
			return false;
		}

		return true;
	}

	/**********************************************************
	 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
	 ******************************************************** */
	function keyCode(e) {
		var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both

		if (code >  32 && code <  48) keyResult(e);
		if (code >  57 && code <  65) keyResult(e);
		if (code >  90 && code <  97) keyResult(e);
		if (code > 122 && code < 127) keyResult(e);
	}

	function keyResult(e) {
		alert("검색항목에는 특수문자를 사용할수 없습니다!");

		if (navigator.appName != "Netscape") {
			event.returnValue = false;  //IE - Chrome both
		}
		else {
			e.preventDefault(); //FF - Chrome both
		}
	}

	function onlyNumber(event) { // 8 백스페이스 , 9 탭 , 37 왼쪽이동, 39 오른쪽이동, 46 delete
		var key;
		if (event.which) { // ie9 firefox chrome opera safari
			key = event.which;
		} else if (window.event) {  // ie8 and old
			key = event.keyCode;
		}
		if (!( key==8 || key==9 || key==37 || key==39 || key==46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105) )) {
			alert("숫자만 입력해 주세요");
			if (event.preventDefault) {
				event.preventDefault();
			} else {
				event.returnValue = false;
			}
		} else {
			event.returnValue = true;
		}
	}

	function mySheet1_OnSmartResize(Width, Height) {
		//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
		mySheet2.FitColWidth();
		mySheet3.FitColWidth();
		mySheet4.FitColWidth();
	}
</script>
</head>
<body>
<div id="content_wrap">
<!-- div class="content_scroll"-->
<div>
<form name="adminForm" id="adminForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="gubun" id="gubun" />
	<div class="wrap_con">
		<!-- 01 : search -->
		<div class="bbs_list">
			<ul class="tit" border="1">
				<li class="tit">업체정보</li>
				<li class="tit">
					<select name="vendorId" id="vendorId" class="select">
					<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
						<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq (vData.vendorId eq null ? epcLoginVO.repVendorId : vData.vendorId)}">selected</c:if>>${venArr}</option>
					</c:forEach>
					</select>
				</li>
				<li class="btn">
					<a href="javascript:doSearch();" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
					<%--
					<c:choose>
					<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
					<a href="javascript:doVendorInsert();" class="btn" id="create"><span><spring:message code="button.common.create" /></span></a>
					</c:when>
					</c:choose>
					--%>
					<a href="javascript:doVendorUpdate();" class="btn" id="save"><span><spring:message code="button.common.save" /></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="15%" />
				<col width="18%" />
				<col width="15%" />
				<col width="18%" />
				<col width="15%" />
				<col width="19%" />
			</colgroup>
			<tr>
				<th>*업체명</th>
				<td><input type="text" name="vendorNm" id='vendorNm' value="${vData.vendorNm}" maxlength="100" size="20" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" style="vertical-align:middle;"/></td>
				<th>*대표자명</th>
				<td><input type="text" name="ceoNm" id='ceoNm' value="${vData.ceoNm}" maxlength="100" size="20" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" style="vertical-align:middle;"/></td>
				<th>*사업자번호</th>
				<td><input type="text" name="cono" id='cono' value="${vData.cono}" maxlength="10" size="20" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" style="vertical-align:middle;"/></td>
			</tr>
			<tr>
				<th>*거래유형</th>
				<td>
					<select name="vendorTypeCd" id="vendorTypeCd" style="width:85%" class="select">
						<option value=""></option>
						<c:forEach items="${codeList1}" var="code" begin="0">
						<option value="${code.MINOR_CD}"  ${vData.vendorTypeCd eq code.MINOR_CD ? 'selected' : ''}>${code.CD_NM}</option>
						</c:forEach>
					</select>
				</td>
				<th>전화번호</th>
				<td>
					<input type="text" name="repTelNo" id='repTelNo' value="${vData.repTelNo}" maxlength="16" size="20" style="vertical-align:middle;"/>
				</td>
				<th>팩스번호</th>
				<td>
					<input type="text" name="repFaxNo" id='repFaxNo' value="${vData.repFaxNo}" maxlength="16" size="20" style="vertical-align:middle;"/>
				</td>
			</tr>
			<tr>
				<th>업종</th>
				<td>
					<select name="vendorKindCd" id="vendorKindCd" style="width:85%" class="select">
						<option value=""></option>
						<c:forEach items="${codeList2}" var="code" begin="0">
						<option value="${code.MINOR_CD}"  ${vData.vendorKindCd eq code.MINOR_CD ? 'selected' : ''}>${code.CD_NM}</option>
						</c:forEach>
					</select>
				</td>
				<th>업태</th>
				<td colspan="3">
					<input type="text" name="bizBtyp" id='bizBtyp' value="${vData.bizBtyp}" maxlength="100" size="64" style="vertical-align:middle;"/>
				</td>
			</tr>
			<tr>
				<th>*주소</th>
				<td colspan="5">
					<input type="text" name="zipCd" id='zipCd' value="${vData.zipCd}" readonly maxlength="6" size="6" onChange="fnChkSpcCharByte()" style="vertical-align:middle;"/>
					<a href="javascript:openZipCodeNew();" class="btn" id="search"><span><spring:message code="button.common.zipcode"/></span></a>
					<input type="text" name="addr_1_nm" id='addr_1_nm' value="${vData.addr_1_nm}" maxlength="200" size="40" readonly style="vertical-align:middle;"/>
					<input type="text" name="addr_2_nm" id='addr_2_nm' value="${vData.addr_2_nm}" maxlength="200" size="40" style="vertical-align:middle;"/>
				</td>
			</tr>
			<tr>
				<th>추가배송비_제주</th>
				<td>
					<input type="text" name="addDeliAmt1" id="addDeliAmt1" value="${vData.addDeliAmt1}" onkeydown="onlyNumber(event)" maxlength="10" size="16" style="vertical-align:middle;"/>
					<input type="hidden" name="baseDlvAmt" id="baseDlvAmt" value="0" />
					<input type="hidden" name="rtnAmt" id="rtnAmt" value="0" />
					<input type="hidden" name="exchAmt" id="exchAmt" value="0" />
				</td>
				<th>추가배송비_도서산간</th>
				<td colspan="3">
					<input type="text" name="addDeliAmt2" id="addDeliAmt2" value="${vData.addDeliAmt2}" onkeydown="onlyNumber(event)" maxlength="10" size="16" style="vertical-align:middle;"/>
				</td>
			</tr>
			</table>
		</div>
		<!-- 1검색조건 // -->
	</div>
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">담당자</li>
				<li class="btn">
					<a href="javascript:doUserAdd();" class="btn" id="userAdd"><span><spring:message code="button.common.add"/></span></a>
					<a href="javascript:doUserSave();" class="btn" id="userSave"><span><spring:message code="button.common.save" /></span></a>
				</li>
			</ul>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><div id="ibsheet1"></div></td>
				</tr>
			</table>
			<div style="text-align:center;"><span><input type="button" id="spread" value="▼ 펼쳐보기" /></span><input type="button" id="fold" value="▲ 접어보기" style="display:none;"/></span></div>
		</div>
	</div>
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">업체주소</li>
				<li class="btn">
					<a href="javascript:doAddrAdd();" class="btn" id="addrAdd"><span><spring:message code="button.common.add"/></span></a>
					<a href="javascript:doAddrSave();" class="btn" id="addrSave"><span><spring:message code="button.common.save" /></span></a>
				</li>
			</ul>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><div id="ibsheet2"></div></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">주문배송비관리</li>
				<li class="btn">
					<a href="javascript:doDeliChgHistSrch('10');" class="btn" id="deliChgHist"><span>변경이력</span></a>
					<a href="javascript:doDeliAdd();" class="btn" id="deliAdd"><span><spring:message code="button.common.add"/></span></a>
					<a href="javascript:doDeliUpdate();" class="btn" id="deliUpdate"><span><spring:message code="button.common.update"/></span></a>
					<a href="javascript:doDeliSave();" class="btn" id="deliSave"><span><spring:message code="button.common.save" /></span></a>
				</li>
			</ul>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><div id="ibsheet3"></div></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">반품배송비관리</li>
				<li class="btn">
					<a href="javascript:doDeliChgHistSrch('20');" class="btn" id="deliChgHist"><span>변경이력</span></a>
					<a href="javascript:doReturnDeliAdd();" class="btn" id="returnDeliAdd"><span><spring:message code="button.common.add"/></span></a>
					<a href="javascript:doReturnDeliUpdate();" class="btn" id="returnDeliUpdate"><span><spring:message code="button.common.update"/></span></a>
					<a href="javascript:doReturnDeliSave();" class="btn" id="deliSave"><span><spring:message code="button.common.save" /></span></a>
				</li>
			</ul>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><div id="ibsheet4"></div></td>
				</tr>
			</table>
		</div>
	</div>
</form>
</div>

<!-- footer -->
<div id="footer">
	<div id="footbox">
		<div class="location">
			<ul>
				<li>홈</li>
				<li>시스템관리</li>
				<li class="last">업체정보관리</li>
			</ul>
		</div>
	</div>
</div>
<!-- footer //-->
</div>
<iframe id="updatefrm" name="updatefrm" src="" marginwidth="0" marginheight="0" frameborder="3" bordercolor="red" width="0" height="0" scrolling="yes"></iframe>
<!--	@ BODY WRAP  END	// -->
</body>
</html>