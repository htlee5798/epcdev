<%--
- Author(s): jib
- Created Date: 2011. 09. 7
- Version : 1.0
- Description : 추가속성 화면
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String tabNo = "10";
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PBOMPRD0039 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
		$(document).ready(function(){

			// START of IBSheet Setting
			createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "330px");
			mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

			var ibdata = {};
			// SizeMode:
			ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
			// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
			ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};

			// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
			ibdata.Cols = [
				{SaveName:"seq"			, Header:"순번"		, Type:"Int", 		Align:"Center", Width:35,  	Edit:0}
			  , {SaveName:"chk"			, Header:"선택"     	, Type:"CheckBox", 	Hidden:true}
			  , {SaveName:"infoColNm"   , Header:"속성명"    	, Type:"Text", 		Align:"Left", 	Width:205, 	Edit:0, Ellipsis:true}
			  , {SaveName:"infoColDesc" , Header:"상세설명"    , Type:"Text", 		Align:"Left", 	Width:320,	Edit:0, Ellipsis:true}
			  , {SaveName:"colVal"    	, Header:"속성데이터"	, Type:"Text", 		Align:"Left", 	Width:240,	Edit:1, Ellipsis:true,	EditLen:1000}
			  , {SaveName:"modId"    	, Header:"수정자ID"	, Type:"Text ", 	Align:"Center", Width:80, 	Edit:0}
			  , {SaveName:"modDate"		, Header:"수정일시"	, Type:"Text", 		Align:"Center", Width:110, 	Edit:0}
			  , {SaveName:"infoGrpCd"   , Header:"그룹코드"	, Type:"Text", 		Hidden:true}
			  , {SaveName:"infoColCd"   , Header:"컬럼코드"	, Type:"Text", 		Hidden:true}
			  , {SaveName:"pgmId"		, Header:"상품코드"	, Type:"Text", 		Hidden:true}
			];

			IBS_InitSheet(mySheet, ibdata);
			mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
			mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

		//input enter 막기
		$("*").keypress(function(e){
	        if(e.keyCode==9) return false;
		});

		doSearch();

	});


		/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		var url = '<c:url value="/product/selectProductCertSearch.do"/>';

		var param = new Object();
		param.prodCd = '<%=prodCd%>';
		param.mode = 'search';
		loadIBSheetData(mySheet, url, null, null, param);
	}

	function doSelectBox() {
		var infoGrpCd = $("#catCd").val();
		if (infoGrpCd == "KC001") {	//KC001 : 해당사항없음
			$("li[id=catCdDtlSelectBox]").hide();
			doSelectBoxDtl();
		} else {
			var url = '<c:url value="/product/selectProductCertDtlSelectSearch.do"/>';

			var param = new Object();
			param.prodCd = '<%=prodCd%>';
			param.infoGrpCd = infoGrpCd;
			param.mode = 'search';
			loadIBSheetData(mySheet, url, null, null, param);
		}
	}

	function doSelectBoxDtl(){
		var url = '<c:url value="/product/selectProductCertSelectSearch.do"/>';

		var param = new Object();
		param.mode = 'search';
		param.prodCd = '<%=prodCd%>';
		param.infoGrpCd = $("#catCd").val();
		if (param.infoGrpCd == "KC001") {	//KC001 : 해당사항없음
			param.infoColCd = "00001";
		} else {
			param.infoColCd = $("#catCdDtl").val();
		}
		loadIBSheetData(mySheet, url, null, null, param);
	}

	function mySheet_OnSearchEnd() {
		var extMsg    = RETURN_IBS_OBJ.extMsg;
		var extMsgDtl = RETURN_IBS_OBJ.extMsgDtl;

		var optionValue    = "";
		var infoGrpCd 	   = "";
		var infoColCd      = "";
		var checkMapping   = "";
		var updId          = "";
		var updDate  	   = "";
		var optionValueDtl = "";

		if (extMsg != null) {
			optionValue    = extMsg.optionValue;
			infoGrpCd 	   = extMsg.infoGrpCd;
			checkMapping   = extMsg.checkMapping;
			updId          = extMsg.updId;
			updDate  	   = extMsg.updDate;
		}

		if (extMsgDtl != null) {
			optionValueDtl = extMsgDtl.optionValueDtl;
			infoColCd      = extMsgDtl.infoColCd;
		}

		if (RETURN_IBS_OBJ.result) 	{// 서버에서 전송한 상태코드를 가져온다.
			if (optionValue != null && optionValue != '') {
				$("#catCd").html(optionValue);
				if (infoGrpCd != null && infoGrpCd != '') {
					document.getElementById("catCd").value = infoGrpCd;
					document.getElementById("catCdDtl").value = infoColCd;
					doSelectBox();
				}

			}

			if (optionValueDtl != null && optionValueDtl != '') {
				$("#catCdDtl").html(optionValueDtl);
				if (infoColCd != null && infoColCd != '') {
					document.getElementById("catCdDtl").value = infoColCd;
					$("li[id=catCdDtlSelectBox]").show();
					doSelectBoxDtl();
				}
			}

			//공급자적합성 인 경우 "KC 공급자적합성 확인대상"값 하드코딩
			if (mySheet.RowCount() > 0){
				infoGrpCd = $("#catCd").val();
				infoColCd = $("#catCdDtl").val();
				if (infoGrpCd == "KC001") {
					mySheet.SetCellValue(1, "colVal", "해당사항없음");
					mySheet.SetCellEditable(1, "colVal", false);
				} else if (infoGrpCd == "KC003" && infoColCd == "00003") {
					mySheet.SetCellValue(1, "colVal", "공급자적합성확인");
					mySheet.SetCellEditable(1, "colVal", false);
				} else if (infoGrpCd == "KC008" && infoColCd == "00003") {
					mySheet.SetCellValue(1, "colVal", "공급자적합성확인");
					mySheet.SetCellEditable(1, "colVal", false);
				} else if (infoGrpCd == "KC009" && infoColCd == "00003") {
					mySheet.SetCellValue(1, "colVal", "공급자적합성확인");
					mySheet.SetCellEditable(1, "colVal", false);
				} else if (infoGrpCd == "KC008" && infoColCd == "00005") {
					mySheet.SetCellValue(1, "colVal", "안전기준준수");
					mySheet.SetCellEditable(1, "colVal", false);
				} else {
					mySheet.SetCellEditable(1, "colVal", true);
				}
			}

			if (checkMapping != null && checkMapping != '') {
				alert(checkMapping);
			}

			fnSetNewProdCd();	//신규상품코드값 미리 보관... 코드 변경 후 저장시 값 활용
		}
	}

	//신규상품코드값 저장
	function fnSetNewProdCd(){	//신규상품코드값 미리 보관... 코드 변경 후 저장시 값 활용
		if ($("#newProdCd").val() == "" && mySheet.RowCount() > 0) {
			document.getElementById("newProdCd").value = $.trim(mySheet.GetCellValue(1, "pgmId"));
		}

	}

	//리스트 신규상품코드값 저장
	function fnSetListNewProdCd(){	//신규상품코드값 미리 보관... 코드 변경 후 저장시 값 활용
		var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
			$.trim(mySheet.SetCellValue(i, "pgmId", $("#newProdCd").val()));
		}
	}

	//리스트 수정
	function doUpdate(){
		if(!checkUpdateData()) {
			return;
		}

		if( confirm("데이터를 저장 하시겠습니까?") ){
			var tmpCatCd = $("#catCd").val();
			if (tmpCatCd == "KC004" || tmpCatCd == "KC011") {
				var certChk = false;
				var frm = document.certForm;
				$("#certInfoCode").val(mySheet.GetCellValue(1, "colVal"));
				var formQueryString = $('*', frm).fieldSerialize();

				$.ajax({
					type: "POST",
					url: '<c:url value="/product/certInfoCheck.do"/>',
					async : false,
					data: formQueryString,
					success: function(data) {
						try {
							//console.log(data.returnCode);
							if (data.returnCode == "0000") {
								certChk = true;
							} else {
								if (data.returnCode == "9999") {
									alert("안전인증번호가 유효하지 않습니다. 유효한 번호를 입력해주세요.");
								} else if (data.returnCode == "8888") {
									alert("안전인증번호 검증 통신이 실패하였습니다. 관리자에게 문의해주세요.");
								} else {
									alert("예외상황이 발생하였습니다. 관리자에게 문의해주세요.");
								}
							}
						} catch (e) {
							console.log(e.msg);
						}
					},
					error: function(e) {
						alert(e.msg);
					}
				});

				if (!certChk) { return; }
			}

			fnSetListNewProdCd();	//리스트 신규상품코드값 저장
			var sUrl = '<c:url value="/product/selectProductCertListUpdate.do"/>';
			mySheet.DoSave(sUrl, {Param:'mode=update&prodCd=<%=prodCd%>&vendorId=<%=vendorId%>', Col:1, Quest:false, Sync:2});
		}
	}

	// 저장 전, 값 체크
	function checkUpdateData() {
		var rowCount = mySheet.RowCount() + 1;

		if (typeof $("#catCd").val() != 'undefined') {
			if (document.getElementById("catCd").value == '') {
				alert("제품안전인증 Select 박스를 선택하세요.");
				document.getElementById("catCd").focus();
				return false;
			}
		}

		for (var i=1; i<rowCount; i++) {
			if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "colVal")))) {
				alert("속성 데이터가 입력되지 않았습니다.");
				return false;
			}
		}
		return true;
	}

	//Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) doSearch();
	}
</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<form name="dataForm" id="dataForm">
<div id="content_wrap" style="width:990px;height:50px">

	<div id="wrap_menu" style="width:990px;">
		<!--	@ 검색조건	-->

		<!-- 상품 상세보기 하단 탭 -->
		<c:set var="tabNo" value="<%=tabNo%>" />
		<c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="<%=prodCd%>" />
		</c:import>

		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">제품안전인증</li>

					<li class="tit">
						<span>
						<select name='catCd' id='catCd' style='width:160px;' onchange='doSelectBox()'>
						</select>
						</span>
					</li>
					<li class="tit" id="catCdDtlSelectBox" style="display:none">
						<select id="catCdDtl" name="catCdDtl" onchange='doSelectBoxDtl()'>
						</select>
					</li>
					<li class="btn">
						<a href="javascript:doUpdate();" class="btn" ><span>저장</span></a>
					</li>
				</ul>

				<table cellpadding="0" cellspacing="0" border="0" width="990px">
					<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
			</div>
		</div>

	</div>
	<!-- footer -->

</div>
</form>
<form name="form1" id="form1">
	<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
	<input type="hidden" id="infoGrpCd" name="infoGrpCd" value="" />
	<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
	<input type="hidden" id="newProdCd" name="newProdCd" value="" />
</form>
<form name="certForm" id="certForm">
	<input type="hidden" id="certInfoCode" name="certInfoCode" value="" />
	<input type="hidden" id="certInfoType" name="certInfoType" value="" />
</form>
</body>
</html>