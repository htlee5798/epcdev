<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- board/PSCMBRD0010 -->
<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function() {
		$("#colMain").hide();
		$("#formSave").hide();

		$('#search').click(function() {
			doSearch();
		});

		$('#formSave').click(function() {
			doSave();
		});

		$('#allModify').click(function() {
			doSheetModify();
		});

		$('#excelDown').click(function() {
			exeExcelFile();
		});

		$('#excelInsert').click(function() {
			doExcelInsert();
		});

		$('#prodFlag').change(function() {
			var val = $(this).val();

			$('#prodVal').val('');

			if (val == "4") {
				$("#searchBtn").hide();
				$('#prodVal').attr("readonly",false);
			}else{
				$("#searchBtn").show();
				$('#prodVal').attr("readonly",true);
			}
		});
	}); // end of ready

	function initIBSheetGrid(clmList) {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "390px");
		mySheet.Reset();
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"", 		Type:"CheckBox", 	SaveName:"SELECTED", 	 Align:"Center", Width:25,  Sort:false}
		  , {Header:"순번", 		Type:"Text", 		SaveName:"RNUM", 		 Align:"Center", Width:50,  Edit:0}
		  , {Header:"상품코드",	Type:"Text", 		SaveName:"PROD_CD", 	 Align:"Center", Width:110, Edit:0}
		  , {Header:"상품명", 		Type:"Text", 		SaveName:"PROD_NM",	 	 Align:"Left"  , Width:250, Edit:0}
		  , {Header:"그룹코드", 	Type:"Text", 		SaveName:"INFO_GRP_CD",	 Align:"Left"  , Width:0,   Edit:0, Hidden:true}
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

		// 검색결과에 따라 컬럼 동적 구성
		var nCnt = clmList.length;
		var colWid = 700 / nCnt;
		var colSpanVal = "";
		var colHtml = "<table class=\"bbs_search\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
					+ "<colgroup>"
					+ "	<col width=\"16%\">"
					+ "	<col width=\"34%\">"
					+ "	<col width=\"16%\">"
					+ "	<col width=\"34%\">"
					+ "</colgroup>"
					+ "<tr>";

		$("#uploadTR").show();
		$("#colMain").show();
		$("#formSave").show();
		$("#colSize").val(nCnt);

		$.each(clmList, function(index, obj) {
			var textVal = obj.INFO_COL_NM;

			var colInfo = {
				Pos: index + 4
			  , Header: {Text:textVal, Align:'Center'}
			  , Col:[{Type:'Text', Width:colWid, SaveName: 'COL_' + (index+1), Align:'Center', Edit:1}]
			};

			mySheet.ColInsert(colInfo);

			var colInfo2 = {
					Pos: index + 5
				  , Header: {Text:textVal, Align:'Center'}
				  , Col:[{Type:'Text', Width:0, SaveName: 'CODE_' + (index+1), Align:'Center', Edit:0, Hidden:true}]
				};

			mySheet.ColInsert(colInfo2);

			if (index == (nCnt-1) && index % 2 == 0) {
				colSpanVal = "colspan=\"3\"";
			}

			if (textVal.length > 10) {
				textVal = textVal.substring(0,10) + "...";
			};

			colHtml += "<td style=\"text-align:left;\" title=\""+obj.INFO_COL_NM+"\">"
					 + textVal
					 + "</td>"
					 + "<td "+colSpanVal+">"
					 + "	<input type=\"text\" style=\"width:240px;vertical-align:middle;\" id=\"colVal"+(index+1)+"\" name=\"colVal"+(index+1)+"\">"
					 + "</td>";

			if (index % 2 == 1) {
				colHtml += "</tr>";
				if (index < nCnt) {
					colHtml += "<tr>";
				}
			}
		});

		colHtml += "</table>";

		$("#colDiv").html(colHtml);

		goPage();

		$(".content_scroll").css("height", $(window).height() + 5);
	}

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {

		var form = document.prodForm;
		var getDiff = getDateDiff($("#startDate").val(),$("#endDate").val());

		//조회기간 체크
		if (!doDateCheck()) {
			return false;
		}

		//특수문자체크
		if (!fnChkSpcCharByte()) {
			return false;
		}

		if (getDiff > 90) {
			alert('시작일자보다 종료일자가 90일 이상 클수 없습니다.');
			return;
		}

		var targetUrl = '<c:url value="/product/selectElecCommColList.do"/>';
		var infoGrpCd = $("#infoGrpCd").val();

		$.ajax({
			type: 'POST',
			url: targetUrl,
			data: { infoGrpCd:infoGrpCd },
			success: function(data) {
				initIBSheetGrid(data.columnList);
			},
			error: function(e) {
				alert(e);
			}
		});


	}

	function goPage() {
		var url = '<c:url value="/product/selectElecCommList.do"/>';
		loadIBSheetData(mySheet, url, null, '#prodForm', null);
	}

	/** ********************************************************
	 * 조회기간 체크
	 ******************************************************** */
	function doDateCheck() {
		var form = document.prodForm;

		if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' )) {
			alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
			return false;
		}

		return true;
	}


	/** ********************************************************
	 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
	 ******************************************************** */
	function fnChkSpcCharByte() {
		var str = document.prodForm.prodVal.value;
		var len = 0;
		var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

		if (str.search(exp) != -1) {
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
		} else {
			e.preventDefault(); //FF - Chrome both
		}
	}

	/**********************************************************
	 * 수정
	 **********************************************************/
	function doSave() {
		var udtUrl = '<c:url value="/product/updateElecComm.do"/>';

		var param = new Object();
		param.mode = "update";
		param.vendorId = $("#vendorId").val();

		mySheet.DoSave(udtUrl, {Param:$.param(param), Col:0, Sync:2});
	}

	/**********************************************************
	 * 항목 일괄 수정
	 **********************************************************/
	function doSheetModify() {
		var colSize = $("#colSize").val();
		var stCnt = mySheet.RowCount();
		var chkVal = 0;

		if (stCnt == 0) {
			alert("수정할 데이터가 없습니다.");
			return;
		}

		for(var i = 1; i <= colSize; i++) {
			var inputVal = $("#colVal"+i).val();

			for(var j = 0; j < stCnt; j++) {
				if ($.trim(inputVal) != "") {
					mySheet.SetCellValue(j+1,"COL_"+i,inputVal);
					chkVal ++;
				}
			}
		}

		if (chkVal == 0) {
			alert("수정할 항목을 입력하세요.");
		}
	}

	/**********************************************************
	 * 일괄업로드용 양식 다운
	 **********************************************************/
	function exeExcelFile() {
		var form = document.prodForm;
		var stCnt = mySheet.RowCount();

		if (stCnt == 0) {
			alert("조회된 데이터가 없습니다.");
			return;
		}

		if (!confirm('엑셀 양식을 다운로드 하시겠습니까?')) {
			return;
		}

		form.target = "frameForExcel";
		form.action = '<c:url value="/product/elecCommExcel.do"/>';
		form.submit();
	}

	/**********************************************************
	 * 일괄업로드
	 **********************************************************/
	function doExcelInsert() {
		var form = document.prodForm;

		var fName = $('#createFile').val();
		if (fName.length < 3) {
			alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
			return;
		}

		if (!confirm('엑셀 양식을 업로드 하시겠습니까?')) {
			return;
		}

		loadingMask();

		form.target = "frameForExcel";
		form.action = '<c:url value="/product/elecCommExcelUpload.do"/>';
		form.submit();
	}

	function uploadReturn(msg) {
		alert(msg);
		$("#createFile").val("");

		hideLoadingMask();

		doSearch();
	}

	/**********************************************************
	 * 상품정보 팝업
	 **********************************************************/
	function popupProductList() {
		var chkVal = $('#prodFlag').val();

		if (chkVal != "4") {
			var targetUrl = '<c:url value="/common/viewPopupProductList.do"/>?prodFlag='+chkVal+"&vendorId="+$("#vendorId").val();//01:상품
			Common.centerPopupWindow(targetUrl, 'prd', {width : 750, height : 500});
		}
	}

	function popupReturn(arg) {
		$("#prodVal").val(arg);
	}

	/**********************************************************
	 * 처리 후 함수
	 **********************************************************/
	function mySheet_OnSearchEnd() {
		var rowCount = mySheet.RowCount();

		$("#totCntSpan").text(rowCount);

		if (rowCount > 99) {
			alert("조회된 데이터가 100건 이상 입니다.\n대량변경 시 일괄 업로드 기능을 사용하시기 바랍니다.");
		}
	}

	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		goPage();
	}

</script>
</head>
<body>
<div id="content_wrap">
<div class="content_scroll">
<form name="prodForm" id="prodForm" method="post" enctype="multipart/form-data">
<iframe name="frameForExcel" style="display:none;"></iframe>
<input type="hidden" id="colSize" name="colSize"/>
<div id="wrap_menu">
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tr>
				<th><span class="star">*</span>상품분류</th>
				<td>
					<select name="infoGrpCd" id="infoGrpCd" class="select">
						<c:forEach items="${infoGrpList}" var="code" begin="0">
							<option value="${code.INFO_GRP_CD}"> ${code.INFO_GRP_NM}</option>
						</c:forEach>
					</select>
				</td>
				<th><span class="star">*</span>상품등록일자</th>
				<td>
				   <input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${startDate}" /><a href="javascript:openCal('prodForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${endDate}" /><a href="javascript:openCal('prodForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
			</tr>
			<tr>
				<th>협력업체코드 </th>
				<td>
					<c:choose>
						<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
							<c:if test="${not empty vendorId}">
								<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${vendorId}" style="width:40%;"/>
							</c:if>
							<c:if test="${empty vendorId}">
								<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
							</c:if>
							<a href="#" class="btn" onclick="popupVendorList();"><span>선택</span></a>
						</c:when>
						<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
							<select name="vendorId" id="vendorId" class="select">
								<option value="">전체</option>
							<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">

								<c:if test="${not empty vendorId}">
									<option value="${venArr}" <c:if test="${venArr eq vendorId}">selected</c:if>>${venArr}</option>
								</c:if>
								<c:if test="${empty vendorId}">
									<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
								</c:if>

							</c:forEach>
							</select>
						</c:when>
					</c:choose>
				</td>
				<th>상품검색</th>
				<td>
					<select name="prodFlag" id="prodFlag" style="width:104px;">
						<option value="4">상품명</option>
						<option value="1">상품코드</option>
					</select>
					<input type="text" style="width:40%;vertical-align:middle;" id="prodVal" name="prodVal" >
					<a href="javascript:popupProductList();"  id="searchBtn" style="display:none;"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
				</td>
			</tr>
			<tr id="uploadTR" style="display:none;">
				<th>일괄업로드</th>
				<td colspan="3">
					<input type="file" style="" name="createFile" id="createFile" style="width:60%;" value="파일이름"  />
					<a href="#" id="excelDown" class="btn"><span>일괄 다운로드</span></a>
					<a href="#" id="excelInsert" class="btn"><span>일괄 업로드</span></a>
				</td>
			</tr>
			</table>
		</div>
		<br/>
		<div class="bbs_search" id="colMain">
			<ul class="tit">
				<li class="tit">일괄수정항목</li>
				<li class="btn">
					<a href="#" class="btn" id="allModify"><span>일괄 수정</span></a>
				</li>
			</ul>
			<div id="colDiv"></div>
		</div>
		<!-- 1검색조건 // -->
	</div>
	<!-- 2 검색내역  -->
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">전상법목록</li>
				<li class="btn">
					<a href="#" class="btn" id="formSave"><span><spring:message code="button.common.save"/></span></a>
				</li>
			</ul>

			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><div id="ibsheet1"></div></td>
				</tr>
			</table>
		</div>
		<!-- 2검색내역 // -->

		<div id="pagingDiv" class="pagingbox2" style="width:99%;">
			<p class="total">[총건수 <span id="totCntSpan">0</span>]</p>
		</div>
	</div>
</div>
</form>
</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품정보관리</li>
					<li class="last">전상법관리관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
<!--	@ BODY WRAP  END  	// -->
</body>
</html>