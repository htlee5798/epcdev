<%--
- Author(s): jib
- Created Date: 2011. 09. 7
- Version : 1.0
- Description : 전자상거래 화면
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
	String tabNo = "9";
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PBOMPRD2001 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{SaveName:"seq"			, Header:"순번"			, Type:"Int", 		Align:"Center", Width:35,  	Edit:0}
		  , {SaveName:"chk"			, Header:"선택"     	, Type:"CheckBox", 	Hidden:true}
		  , {SaveName:"infoColNm"   , Header:"속성명"    	, Type:"Text", 		Align:"Left", 	Width:205, 	Edit:0, Ellipsis:true}
		  , {SaveName:"infoColDesc" , Header:"상세설명"    	, Type:"Text", 		Align:"Left", 	Width:320,	Edit:0, Ellipsis:true}
		  , {SaveName:"colVal"    	, Header:"속성데이터"	, Type:"Text", 		Align:"Left", 	Width:240,	Edit:1, Ellipsis:true}
		  , {SaveName:"modId"    	, Header:"수정자ID"     , Type:"Text", 		Align:"Center", Width:80, 	Edit:0}
		  , {SaveName:"modDate"		, Header:"수정일시"     , Type:"Text", 		Align:"Center", Width:110, 	Edit:0}
		  , {SaveName:"infoGrpCd"   , Header:"그룹코드"     , Type:"Text", 		Hidden:true}
		  , {SaveName:"infoColCd"   , Header:"컬럼코드"     , Type:"Text", 		Hidden:true}
		  , {SaveName:"pgmId"		, Header:"PGM_ID"       , Type:"Text", 		Hidden:true}
		];

		IBS_InitSheet(mySheet, ibdata);
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

		//input enter 막기
		$("*").keypress(function(e){
	        if(e.keyCode==9) return false;
		});

		doSearch();

		//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공 
		var aprvCd;

	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		var url = '<c:url value="/product/selectProductCommerceSearch.do"/>';
		var param = new Object();
		param.prodCd = '<%=prodCd%>';
		param.mode = 'search';
		loadIBSheetData(mySheet, url, null, null, param);
	}

	function doSelectBox(){
		var url = '<c:url value="/product/selectProductCommerceSelectSearch.do"/>';
		var param = new Object();
		param.mode = 'search';
		param.prodCd = '<%=prodCd%>';
		param.infoGrpCd = $("#catCd").val();
		loadIBSheetData(mySheet, url, null, null, param);
	}

	function mySheet_OnSearchEnd() {
		var extMsg = RETURN_IBS_OBJ.extMsg;
		var optionValue = extMsg.optionValue;
		var infoGrpCd = extMsg.infoGrpCd;
		var checkMapping = extMsg.checkMapping;
		var updId = extMsg.updId;
		var updDate = extMsg.updDate;

		//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공 
		aprvCd = extMsg.aprvCd;

		if (RETURN_IBS_OBJ.result) { // 서버에서 전송한 상태코드를 가져온다.
			if (optionValue != null && optionValue != ''){
				$('#catCd').html(optionValue);
				//document.getElementById("catCd").innerHTML = optionValue;
				
				if (infoGrpCd != null && infoGrpCd != '') {
					document.getElementById("catCd").value = infoGrpCd;
				}else {
					doSelectBox();
				}
			}

			if (checkMapping != null && checkMapping != '') {
				alert(checkMapping);
			}
		}
	}

	//리스트 수정
	function doUpdate(){
		//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공
		if(aprvCd == "001") {
			alert('현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다.');
		} else if(aprvCd == "002") {
			alert('현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→전상법수정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)');
		}

		if(!checkUpdateData()) {
			return;
		}

		if( confirm("데이터를 저장 하시겠습니까?") ){
			var sUrl = '<c:url value="/product/selectProductCommerceListUpdate.do"/>';
			mySheet.DoSave(sUrl, {Param:'mode=update&prodCd=<%=prodCd%>&vendorId=<%=vendorId%>', Col:1, Quest:false, Sync:2});
		}
	}

	// 저장 전에 값을 체크한다
	function checkUpdateData() {

		var rowCount = mySheet.RowCount() + 1;
		var chkIndex = 0;

		if (typeof $("#catCd").val() != 'undefined') {
			if (document.getElementById("catCd").value == '') {
				alert("전자 상거래 Select 박스를 선택하세요.");
				document.getElementById("catCd").focus();
				return false;
			}
		}

		for (var i = 1; i < rowCount; i++) {
			if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "colVal")))) {
				alert(i + "번째 속성 데이터가 입력되지 않았습니다.");
				return false;
			}
			if (getByteLengthOfUtf8String(mySheet.GetCellValue(i, "colVal")) > 2000) {
				alert(i + "번째 속성 데이터가 2000 Byte 초과입니다. 2000 Byte 이하로 입력해주세요.");
				return false;
			}
		}
		return true;
	}

	function getByteLengthOfUtf8String(s) {
		if(s != undefined && s != "") {
			for(b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
			return b;
		} else {
			return 0;
		}
	}

	// Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) doSearch();
	}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="dataForm" id="dataForm">
<div id="content_wrap" style="width:990px;;height:50px">
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
					<li class="tit">전자상거래</li>
					<li class="tit">
						<select name='catCd' id='catCd' style='width:120px;' onchange='doSelectBox()' >
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
</form>
</body>
</html>