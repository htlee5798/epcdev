<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.lottemart.epc.common.util.SecureUtil" %>
<%
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
	String tabNo = "5";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script language="javascript" type="text/javascript">
var keyCount = '';
var byteChk = '';

	$(document).ready(function() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "300px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{SaveName:"num",		Header:"순번"		, Type:"Int",		Align:"Center",	Width:35,	Edit:0}
		  , {SaveName:"CHK",		Header:""		, Type:"CheckBox",	Align:"Center",	Width:30,	Sort:false} // default=1(수정가능)
		  , {SaveName:"searchKywrd",Header:"검색어"	, Type:"Text",		Align:"Left",	Width:937,	Edit:1} // 수정가능
		  , {SaveName:"seq",		Header:"일련번호"	, Type:"Text",		Align:"Center",	Width:30,	Edit:0, Hidden:true} // 수정불가
		  , {SaveName:"keyCount",	Header:"키카운트"	, Type:"Text",		Align:"Center",	Width:30,	Edit:0, Hidden:true}
		  , {SaveName:"byteChk",	Header:"ByteChk", Type:"Text",		Align:"Center",	Width:30,	Edit:0, Hidden:true}
		  , {SaveName:"prodCd",		Header:"상품코드"	, Type:"Text",		Align:"Center",	Width:30,	Edit:0, Hidden:true}
		  , {SaveName:"sStatus",	Header:"상태"		, Type:"Status",	Align:"Center",	Width:50,	Edit:0, Hidden:true}
		];

		IBS_InitSheet(mySheet, ibdata);
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

		// input enter 막기
		$("*").keypress(function(e) {
			if (e.keyCode == 13) return false;
		});

		goPage('1'); // 목록 자동 로딩

		<%-- $('#create').click(function() {
			popupInsert();
		}); --%>
		$('#add').click(function() {
			var rowCnt = mySheet.RowCount() + 1;

			if (rowCnt > 5) {
				alert("상품키워드가 5개를 초과했습니다. 상품키워드는 5개 까지 등록 가능합니다.");
				return;
			}else{
				mySheet.DataInsert(rowCnt);
				mySheet.SetCellValue(rowCnt, 'num', rowCnt); // 행건수로 순번 설정

				//행 건수만큼 반복
				<%-- for (var i=1; i<rowCnt; i++) {
					var keyWord = mySheet.GetCellValue(i, "searchKywrd");
					//mySheet.SetCellValue(rowCnt, 'searchKywrd', keyWord);
					//추가한 행 검색어 설정
					mySheet.SetCellValue(i, 'searchKywrd', keyWord);
				} --%>

			}
		});

		//저장 버튼 클릭시
		$('#save').click(function() {

			//값 체크
			if (!checkUpdateData()) {
				return;
			}

			var rowCnt = mySheet.RowCount();
			if (rowCnt < 3 || rowCnt > 5) {
				alert("상품키워드는 3개이상 5개 까지 등록 가능합니다.");
				return;
			}

			//확인
			if (confirm("해당 검색어는 체크여부와 상관없이 전체 저장 됩니다. \n상품키워드를 저장 하시겠습니까?")) {

				//20180911 상품키워드 입력 기능 추가
				var aprvCd = '<c:out value="${prdMdAprInfo.aprvCd}" />';

				if (aprvCd == "001") {
					alert("현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다. 수정 값은 승인 완료 후 반영됩니다.");
				} else if (aprvCd == "002") {
					alert("현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→상세키워드수정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)");
				} else {
					alert("등록 값은 관리자 승인 완료 후 반영됩니다.");

					var url = '<c:url value="/product/insertProductKeyword.do"/>';
					var rowCnt = mySheet.RowCount();
					var keyWord;
					//mySheet.DataInsert(rowCnt); // 행 추가
					//행 건수만큼 반복
					for (var i = 1; i < rowCnt; i++) {
						//추가한 행 검색어 설정
						mySheet.SetCellValue(i, 'searchKywrd', keyWord);
					}
					mySheet.DoSave(url, {Param:'mode=insert&vendorId=<%=vendorId%>&mainProdCd=<%=prodCd%>', Quest:false, Sync:2});
				}

				//20180911 상품키워드 입력 기능 추가
			}

			//목록 자동 로딩
			goPage('1');
		});

		$('#delete').click(function() {
			doDelete();
		});

		// 목록 자동 검색
		goPage('1');
	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	function goPage(currentPage) {
		var url = '<c:url value="/product/selectProductKeywordSearch.do"/>';
		var param = new Object();
		param.prodCd = "<%=prodCd%>";
		param.mode = "search";
		loadIBSheetData(mySheet, url, null, null, param);
	}

	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		// 조회된 데이터가 승인완료인 경우는 체크 금지
		if (RETURN_IBS_OBJ && RETURN_IBS_OBJ.extInfo) {
			keyCount = RETURN_IBS_OBJ.extInfo.keyCount;
			byteChk  = RETURN_IBS_OBJ.extInfo.byteChk;
		}
	}

	<%--//등록 팝업
	function popupInsert() {
		var targetUrl = '<c:url value="/product/selectProductKeywordInsertForm.do"/>?prodCd=<%=prodCd%>&vendorId=<%=vendorId%>&keyCount='+keyCount+'&byteChk='+byteChk;
		Common.centerPopupWindow(targetUrl, 'prd', {width : 700, height : 160});
	} --%>

	// 저장 이후 이벤트
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) { doSearch(); }
	}

	// Cell 내용 변경시...
	function mySheet_OnChange(Row, Col, Value, OldValue) {
		if (Row == 0) return;
		if (Col != 2) return;
		<%-- if (Value.toString() != OldValue.toString()) {
			mySheet.SetCellValue(Row, 'CHK', true);
		} --%>
	}

	//리스트 수정
	<%--function doUpdate() {

		if (!checkUpdateData()) {
			return;
		}

		if (confirm("선택된 상품키워드를 수정 하시겠습니까?")) {

			//20180911 상품키워드 입력 기능 추가
			var aprvCd = '<c:out value="${prdMdAprInfo.aprvCd}" />';

			if (aprvCd == "001") {
				alert('현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다. 수정 값은 승인 완료 후 반영됩니다.');
			} else if (aprvCd == "002") {
				alert('현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→상세키워드수정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)');
			} else {
				var sUrl = '<c:url value="/product/updateProductKeywordList.do"/>';
				mySheet.DoSave(sUrl, {Param:'mode=update&vendorId=<%=vendorId%>&mainProdCd=<%=prodCd%>', Quest:false, Sync:2});
			}

		}
	}--%>

	// 저장전에 값 체크
	function checkUpdateData() {
		var rowCount = mySheet.RowCount()+1;

		for (var i = 1; i < rowCount; i++) {

			mySheet.SetCellValue(i, 'CHK', true);

			var skWord = mySheet.GetCellValue(i, "searchKywrd");
			if (Common.isEmpty($.trim(skWord))) {
				alert(i + "번째 줄 검색어를 입력해주세요.");
				return false;
			}
			if (!byteCheck2(i + '번째 줄 검색어', skWord, 39)) {
				return false;
			}

			//20181016 특수문자 유효성 추가
			if ($.trim(skWord).indexOf(',') > -1 || $.trim(skWord).indexOf(';') > -1 || $.trim(skWord).indexOf('|') > -1) {
				alert(i + "번째 ,(콤마) ;(세미콜론) |(버티컬 바) 특수문자는 사용하실 수 없습니다.");
				return;
			}
			//20181016 특수문자 유효성 추가
		}

		<%-- if (nChk == 0) {
			alert('저장할 내역이 없습니다.');
			return false;
		} --%>

		return true;
	}

	//리스트 삭제
	function doDelete() {

		if (confirm("선택된 상품키워드를 삭제 하시겠습니까?")) {
			//20180911 상품키워드 입력 기능 추가
			var aprvCd = '<c:out value="${prdMdAprInfo.aprvCd}" />';

			if (aprvCd == "001") {
				alert('현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다. 삭제 값은 승인 완료 후 반영됩니다.');
			} else if (aprvCd == "002") {
				alert('현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→상세키워드수정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)');
			} else {
				var isValid = true;
				var deleteSearchKeywordRow = ''; 
				for (var i = 1 ; i < 4; i++) {
					if (mySheet.GetCellValue(i, "CHK") == 1 ) {
						mySheet.SetCellValue(i, "CHK", 0);
						isValid = false;
						deleteSearchKeywordRow += ', ' + i;
					}
				}
				if (!isValid) {
					deleteSearchKeywordRow = deleteSearchKeywordRow.replace(',', '');
					alert("검색어 " + deleteSearchKeywordRow + "행은 필수 값으로 수정만 가능합니다.");
					return;
				}

				var sUrl = '<c:url value="/product/updateProductKeywordList.do"/>';
				mySheet.DoSave(sUrl, {Param:'mode=delete&vendorId=<%=vendorId%>&mainProdCd=<%=prodCd%>', Col:1, Quest:false, Sync:2});
			}
		}

	}
</script>
</head>
<body>
<form name="dataForm" id="dataForm">
<div id="content_wrap"  style="width:990px; height:200px;">
	<div id="wrap_menu" style="width:990px;">
		<!--	@ 검색조건	-->

		<!-- 상품 상세보기 하단 탭 -->
		<c:set var="tabNo" value="<%=tabNo%>" />
		<c:import url="/common/productDetailTab.do" charEncoding="euc-kr">
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="<%=prodCd%>" />
		</c:import>

		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">상품키워드</li>
					<li class="btn">
						<%-- <a href="#" id="create" class="btn" ><span><spring:message code="button.common.create"/></span></a> --%>
						<a href="#" id="add" class="btn" ><span><spring:message code="button.common.add"/></span></a>
						<a href="#" id="save" class="btn" ><span><spring:message code="button.common.save"/></span></a>
						<a href="#" id="delete" class="btn" ><span><spring:message code="button.common.delete"/></span></a>
					</li>
				</ul>

				<table cellpadding="0" cellspacing="0" border="0" width="100%" heigth="200px">
					<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
</form>
<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>
</body>
</html>