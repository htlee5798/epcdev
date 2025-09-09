
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="lcn.module.common.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<% 
	String Date  =DateUtil.formatDate(DateUtil.getToday(),"-").replace("-", "");;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
	<script type="text/javascript" src="/js/epc/common.js"></script>
	<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js"></script>
	<script type="text/javascript" src="/js/epc/paging.js"></script>
	<%@ include file="/common/scm/scmCommon.jsp"%>

	<!-- 신규 공통 css 및 js 파일 INCLUDE -->
	<c:import url="/common/commonHead.do" />
	<!-- product/PSCMPRD004002 -->
	<c:set var="sm341Cd" />
	<c:set var="sm341Nm" />
	
	<!-- 공통코드  -->
	<c:forEach items="${codeList}" var="sm341Code" varStatus="idx2">
		<c:choose>
			<c:when test="${ fn:length(codeList) eq idx2.index+1  }">
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.MINOR_CD }" />
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.CD_NM  }" />
			</c:when>
			<c:otherwise>
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.MINOR_CD }|" />
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.CD_NM  }|" />
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<script type="text/javascript">

	
		$(document) .ready( function() {
			//iframe hide
			//$('#save').hide();
			
			
			//excel 관련 변수
			var obj = new Object();
			applyToTypeCdList = new  Array(); 
			applyToCdList = new  Array(); 
			applyToNameList = new  Array(); 
			
							//달력셋팅
							$("#startDate, #endDate").attr("readonly", "readonly");
							$('#btnStartDate, #announStartDy').click( function() {
										openCal('dataForm.announStartDy');
							});
							$('#btnEndDate, #announEndDy').click(function() {
								openCal('dataForm.announEndDy');
							});

							//초기달력값 셋팅
							$("#announStartDy").val('${data.announStartDy}');
							$("#announEndDy").val('${data.announEndDy}');

							// START of IBSheet Setting
							createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "180px");
							mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

							var ibdata = {};
							// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
							ibdata.Cfg = { SizeMode : sizeAuto, SearchMode : smGeneral, MergeSheet : msHeaderOnly }; // 10 row씩 Load
							// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
							ibdata.HeaderMode = { Sort : 1, ColMove : 0, ColResize : 1, HeaderCheck : 1 };

							// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
							ibdata.Cols = [ { 	Header : "", Type : "CheckBox", SaveName : "chk", Align : "Center", Width : 50, Edit : 1, Sort : false }
							, { Header : "순번", Type : "Seq", SaveName : "seq", 	Align : "Center", Width : 100, Edit : 0 }
							, { Header : "적용대상 유형", Type : "Combo", SaveName : "applyToTypeCd", Align : "Center", Width : 150, Edit : 0, ComboText:"<c:out value="${ sm341Nm }"/>",ComboCode:"<c:out value="${ sm341Cd }"/>" 	}
							, { Header : "적용대상 코드", Type : "Text", SaveName : "applyToCd", 	Align : "Center", Width : 150, 	Edit : 0 }
							, { Header : "적용대상명", Type : "Text", SaveName : "applyToName", 	Align : "Center", 	Width : 305, 	Edit : 0 }
							, { Header : "확인", Type : "Status", SaveName : "S_STATUS", Align : "Center", Width : 50, Edit : 0, Hidden : true 	}
							];

							IBS_InitSheet(mySheet, ibdata);

							mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
							mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

							//exeExcel양식다운
							$('#createExcelForm').click(
									function() {
										if (!confirm('엑셀 양식을 다운로드하시겠습니까?')) {
											return;
										}
										var hideCols = 'chk|num';
										excelFormDown(mySheet, '셀링포인트 할당_양식', 1, hideCols);
										//fnMove();
									});

							//찾아보기
							$('#findFile').click(function() {
								$('#file').click();
								msgPath = "파일이 업로드 되었습니다 업로드 버튼을 눌러주세요";
								if ($('#file').val().length > 0) { // 파일 선택 시
									$("#excelOp").val(msgPath);
								} else {
									$("#excelOp").val("");
								}
								//fnMove();
							});

							// 엑셀파일 업로드 (서버 통신 필요)
							$('#uploadExcel') .click( function() {
								if ($("#file").val() == "") {
									alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
									return;
								}
								$("#save").attr("src","load");
								$("#dataForm").attr("enctype","multipart/form-data");
								document.dataForm.target = "save";
								document.dataForm.action = '<c:url value="/excelLoad/IBSheetExcelLoad.do"/>';
								document.dataForm.submit();
								$("#dataForm").attr("enctype","");
								//fnMove();
							});
							
							$('#save').load( function(){
								if( $("#save").attr("src") != ""){
									console.log("=============");
									obj.applyToTypeCdList = applyToTypeCdList;
									obj.applyToCdList = applyToCdList;
									obj.applyToNameList = applyToNameList;
									setProdArray(obj);
								}
							});
							//excelupload 일괄저장
							$('#excelInsert').click(function() {
								if (mySheet.CheckedRows("chk") == 0) {
									alert("선택된 데이터가 없습니다.");
									return;
								}
								doInsert();
							});

							// 삭제 버튼
							$('#deleteCol').click(function() {
								deleteCol();
							});

							// 추가버튼
							$('#addCol').click(function() {
								addCol();
							});

							//ibsheet 세팅
							seachDtl();
							
							// 바이트 초기 세팅
							var text =   $('#title').val().getBytes1("UTF-8");
							document.getElementById('byteInfo1').innerText = text;
							
							var text =   $('#content').val().getBytes1("UTF-8");
							document.getElementById('byteInfo2').innerText = text;

						}); // end of ready
						
						/**********************************************************
						 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
						 ******************************************************** */
						function keyCode(e)
					    {
					        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
					        
					        if (code >  32 && code <  48) keyResult(e);
					        if (code >  57 && code <  65) keyResult(e);
					        if (code >  90 && code <  97) keyResult(e);
					        if (code > 122 && code < 127) keyResult(e);
					    }
						 
						 function keyResult(e)
						 {
						        alert("특수문자를 사용할수 없습니다!");
						        
						        if (navigator.appName != "Netscape") {
						            event.returnValue = false;  //IE - Chrome both
						        }
						        else {
						            e.preventDefault(); //FF - Chrome both
						        }
						  }
						 
						 
						
						/**********************************************************
						 * 글자 바이트 체크
						 ******************************************************** */
						String.prototype.getBytes1 = function() {
							var size = 0;
							for(i=0; i<this.length; i++) {
								var temp = this.charAt(i);
								if(escape(temp) == '%0D') continue;
								if(escape(temp).indexOf("%u") != -1) {
									size += 3;
								}else {
									size++;
								}
							}
							return size;
						}
						
						/** ********************************************************
						 *포커스 이동
						 ******************************************************** */
						 function fnMove(){
						        var offset = $("#div1").offset();
						        $('html, body').animate({scrollTop : offset.top}, 1);
						    }
						
						

						/** ********************************************************
						 * 셀링포인트 글자제한
						 ******************************************************** */
						function fnChkByte(obj, maxByte, check){
							var str = obj.value;
							var str_len = str.length;

							var rbyte = 0;
							var rlen = 0;
							var one_char = "";
							var str2 = "";

							for(var i=0; i<str_len; i++){
							one_char = str.charAt(i);
							if(escape(one_char).length > 4){
								  rbyte += 3;   
							}else{
							    rbyte++;                                            //영문 등 나머지 1Byte
							}

							if(rbyte <= maxByte){
							    rlen = i+1;                                          //return할 문자열 갯수
							}
							}

							if(rbyte > maxByte){
							    alert("한글 "+parseInt(maxByte/3)+"자 / 영문 "+maxByte+"자를 초과 입력할 수 없습니다.");
							    str2 = str.substr(0,rlen);                                  //문자열 자르기
							    obj.value = str2;
							    fnChkByte(obj, maxByte);
							}
							
							//바이트 체크
							var text =   $(obj).val().getBytes1("UTF-8");
							document.getElementById(check).innerText = text;
							}


		
						
		/** ********************************************************
		 * ibsheet 세팅
		 ******************************************************** */
		function seachDtl() {

			var url = '<c:url value="/product/selectSelSheet.do"/>';

			loadIBSheetData(mySheet, url, 1, '#dataForm', null);
		}

		/** ********************************************************
		 * 컬럼 업로드
		 ******************************************************** */
		function setData(applyToTypeCd, applyToCd , applyToName) {
	
				applyToCdList.push(applyToCd);
				applyToTypeCdList.push(applyToTypeCd);
				applyToNameList.push(applyToName);
				
		}
		
		/** ********************************************************
		 * 컬럼 업로드
		 ******************************************************** */	
		 function setProdArray(list){
/* 			 var rowIdx = mySheet.DataInsert(0);
				var form = document.popupForm;
				var param = new Object();
				var currentPage = 1;
				var url = '<c:url value="/product/selectProductArrayPopupList.do"/>';
				
				param.rowsPerPage 	= $("#rowsPerPage").val();
					
				param.selectedProdCd =  list.prodCdList;
			
				
				loadIBSheetData(mySheet, url, currentPage, null, param); */

				//중복 제거
			var msg = "";
			
		 	for (var c = 0; c <= mySheet.LastRow(); c++) {
		 		for(var i = 0; i <list.applyToCdList.length; i++){
					if(mySheet.GetCellValue(c,3)==list.applyToCdList[i] ){
						msg += '적용대상 코드 '+list.applyToCdList[i]+' 은 중복된 데이터 입니다. \n';
						 alert(msg); 
						return;
					}
		 		}
			}
		
			for(var i = 0; i <list.applyToCdList.length; i++){
			
						if(list.applyToTypeCdList[i]=='상품코드' ||  list.applyToTypeCdList[i]=='카테고리코드' || list.applyToTypeCdList[i]=='업체ID' ){
							if(list.applyToTypeCdList[i]=='상품코드'){
								list.applyToTypeCdList[i] = 10;
							}else if(list.applyToTypeCdList[i]=='카테고리코드'){
								list.applyToTypeCdList[i] = 20;
							}else if(list.applyToTypeCdList[i]=='업체ID'){
								list.applyToTypeCdList[i] = 30;
							} 
						
							var rowIdx = mySheet.DataInsert(0);
							mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq()); // 순번 자동 입력
							mySheet.SetCellValue(rowIdx, 'applyToCd', list.applyToCdList[i]);
							mySheet.SetCellValue(rowIdx, 'applyToTypeCd', list.applyToTypeCdList[i]);
							mySheet.SetCellValue(rowIdx, 'applyToName', list.applyToNameList[i]);
						
					}else{
						msg +='적용대상 유형 '+ list.applyToTypeCdList[i]+'는 잘못된 데이터 입니다. \n';
					}
	
				
		}
			if(msg!=""){
			 	 alert(msg); 
			} 
}
		
		/** ********************************************************
		 * 일괄저장 ( 삭제 예정) 
		 ******************************************************** */
/* 		function doInsert() {
			if (mySheet.CheckedRows("chk") == 0) {
				alert("선택된 데이터가 없습니다.");
			} else {
				var inChk = mySheet.FindCheckedRow("chk");
				var inChkArr = inChk.split("|");
				var mdTalkSeq = $("#mdTalkSeq").val(); //seq
				var applyToTypeCd_chkVal = "";
				var applyToCd_chkVal = "";

				if (inChkArr.length == 1) {
					applyToTypeCd_chkVal = mySheet.GetCellValue(inChkArr[0],
							'applyToTypeCd');
					applyToCd_chkVal = mySheet.GetCellValue(inChkArr[0],
							'applyToCd');
				} else {
					for (var i = 0; i < inChkArr.length; i++) {
						if (i == inChkArr.length - 1) {
							applyToTypeCd_chkVal += mySheet.GetCellValue(
									inChkArr[i], 'applyToTypeCd');
							applyToCd_chkVal += mySheet.GetCellValue(
									inChkArr[i], 'applyToCd');
						} else {
							applyToTypeCd_chkVal += mySheet.GetCellValue(
									inChkArr[i], 'applyToTypeCd')
									+ "/";
							applyToCd_chkVal += mySheet.GetCellValue(
									inChkArr[i], 'applyToCd')
									+ "/";
						}
					}
				}
				var mdTalkSeq = $('#mdTalkSeq').val();
				var url = '<c:url value="/product/updateAllPoint.do"/>'
						+ '?mdTalkSeq=' + mdTalkSeq;
				mySheet.DoSave(url, { Param : 'applyToTypeCd_chkVal=' + applyToTypeCd_chkVal, Quest : false
				});
			}
		} */

		/** ********************************************************
		 * 적용대상 팝업 
		 ******************************************************** */
		function addCol() {
			var searchType = $('#TypeSrch').val();
			var vendorId = $('#regId').val();
			var targetUrl = "";
			var msg = ""
			if (searchType == '10') {
				targetUrl = '<c:url value="/common/viewPopupProductList2.do"/>'+'?gubun=Int';
				msg = 'prd';
			} else if (searchType == '20') {
				targetUrl = '<c:url value="/common/selectCategoryPopUpView.do"/>';
				msg = 'popup';
			} else if (searchType == '30') {
				targetUrl = '<c:url value="/common/selectPartnerSmallPopup.do"/>';
				msg = 'popup';
			}
			Common.centerPopupWindow(targetUrl, msg, { width : 750, height : 500 });
			//fnMove();
		}

		//상품 정보 받아오는 펑션
		function popupReturn(rtnVal) {
			//중복 제거
			for (var i = 0; i < rtnVal.prodCdArr.length; i++) {  
				for (var c = 0; c <= mySheet.LastRow(); c++) {
					if(mySheet.GetCellValue(c,3)==rtnVal.prodCdArr[i]){
							alert(rtnVal.prodNmArr[i]+'은 중복된 데이터 입니다');
							//fnMove();
							return;
					}
				}
				var rowIdx = mySheet.DataInsert(0);
				mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq());
				mySheet.SetCellValue(rowIdx, "applyToCd", rtnVal.prodCdArr[i]);
				mySheet .SetCellValue(rowIdx, "applyToName", rtnVal.prodNmArr[i]);
				mySheet.SetCellValue(rowIdx, "applyToTypeCd", $('#TypeSrch').val());
			}
			//fnMove();
		}
		//협력 업체 받아오는 펑션
		function setVendorInto(strVendorId, strVendorNm, strCono) {
			//중복 제거
			for (var c = 0; c <= mySheet.LastRow(); c++) {
				if(mySheet.GetCellValue(c,3)==strVendorId ){
						alert(strVendorNm+'는 중복된 데이터 입니다.');
						return;
				}
			}
			var rowIdx = mySheet.DataInsert(0);
			mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq());
			mySheet.SetCellValue(rowIdx, "applyToCd", strVendorId);
			mySheet.SetCellValue(rowIdx, "applyToName", strVendorNm);
			mySheet.SetCellValue(rowIdx, "applyToTypeCd", $('#TypeSrch').val());
			//fnMove();
		}
		//카테고리 받아오는 펑션
		function setCategoryInto(categoryId, categoryNm) {
			//중복 제거
			for (var c = 0; c <= mySheet.LastRow(); c++) {
				if(mySheet.GetCellValue(c,3)==categoryId ){
					 alert(categoryNm+'는 중복된 데이터 입니다');
						return;
				}
			}
			var rowIdx = mySheet.DataInsert(0);
			mySheet.SetCellValue(rowIdx, 'seq', mySheet.ReNumberSeq());
			mySheet.SetCellValue(rowIdx, "applyToCd", categoryId);
			mySheet.SetCellValue(rowIdx, "applyToName", categoryNm);
			mySheet.SetCellValue(rowIdx, "applyToTypeCd", $('#TypeSrch').val());
			//fnMove();
		}

		/** ********************************************************
		 * 컬럼 삭제
		 ******************************************************** */
		function deleteCol() {
			if (mySheet.CheckedRows("chk") == 0) {
				alert("선택된 데이터가 없습니다.");
			} else {
				var upChk = mySheet.FindCheckedRow("chk");
				mySheet.RowDelete(upChk);
			}
			//fnMove();
		}

		/** ********************************************************
		 * 메세지 창
		 ******************************************************** */
		function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
			alert(Msg);
			opener.doSearch();
			top.close();
		}

		/** ********************************************************
		 * 셀링포인트 수정
		 ******************************************************** */
		function doEdit() {
			var form = document.dataForm;
			var check = $('#check').val();

			if(check=="Y"){
				alert('승인된 셀링포인트는 수정이 불가능합니다.');
				return;
			}
			
			if (!confirm('수정 하시겠습니까?')) {
				return true;
			}
			
			if (form.title.value == "") {
				alert("제목을 입력해주세요.");
				form.title.focus();
				return ;
			}
			
			if (form.content.value == "") {
				alert("내용을 입력해주세요.");
				form.content.focus();
				return ;
			}
			
			if(mySheet.LastRow()=="0"){
				alert("적용대상을 등록하세요");
				return;
			}
			
			if (form.announStartDy.value.replace(/-/gi, '') >= form.announEndDy.value .replace(/-/gi, '')) {
				alert("시작일은 종료일보다 작게 입력되어야 합니다.");
				return;
			}
			
				//데이터 추가
				var title = escape(encodeURIComponent($('#title').val()));
				var content = escape(encodeURIComponent($('#content').val()));
				var mdTalkSeq = escape(encodeURIComponent($('#mdTalkSeq').val()));
				var startDate = $('#announStartDy').val();
				var endDate = $('#announEndDy').val();
				var useYn = $('#useYn').val();

				var url = '<c:url value="/product/editSelPont.do"/>';
				mySheet.DoAllSave(url, {
					Param : 'title=' + title + '&content=' + content
							+ '&startDate=' + startDate + '&endDate=' + endDate
							+ '&useYn=' + useYn
							+ '&mdTalkSeq=' + mdTalkSeq ,
					Quest : 0
				});
			}
		
	</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>

	<form name="dataForm" id="dataForm" method="post" enctype="multipart/form-data">
		<input  type="hidden" value="${regId}" name="regId"  id="regId"/>
		<input  type="hidden" value="${data.apryYn}" name="check"  id="check"/>
		<div id="popup">
			<!--  @title  -->
			<div id="p_title1">
				<h1>셀링포인트 할당</h1>
				<span class="logo">
					<img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" />
				</span>
			</div>
			<!--  @title  //-->
			<!--  @process  -->
			<div id="process1">
				<ul>
					<li>홈</li>
					<li>상품관리</li>
					<li>셀링포인트 관리</li>
					<li class="last">셀링포인트 할당</li>
				</ul>
			</div>
		</div>
		<!--  @process  //-->
		<div class="popup_contents">
			<!-- 셀링포인트 내용-->
			<div class="bbs_search3">
				<ul class="tit">


					<li class="btn"><a href="javascript:void(0)" class="btn" onclick="doEdit()"><span><spring:message code="button.common.update" /></span></a>
						<a href="javascript:void(0)" class="btn" onclick="top.close();"><span><spring:message code="button.common.close" /></span></a></li>
				</ul>

				<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="15%">
							<col width="85%">
					</colgroup>

					<tr>
						<th><span class="star">*&nbsp</span>제목</th>
						<td colspan="3">
							<input type="text" id="title" name="title" size="88" value="${data.title}"  onKeyUp="javascript:fnChkByte(this,'100','byteInfo1')" onKeyPress="keyCode(event)" /> <b> (<span id="byteInfo1">0</span>/100Byte) </b> 
						</td>
					</tr>

					<tr style="height: 150px">
						<th><span class="star">*&nbsp</span>내용</th>
						<td colspan="3">
							<textarea name="content" id="content" rows="10" cols="86" onKeyUp="javascript:fnChkByte(this,'100','byteInfo2')"  onKeyPress="keyCode(event)">${data.content}</textarea> <b> (<span id="byteInfo2">0</span>/100Byte) </b> 
						</td>
					</tr>

					<tr>
						<th class="fst"><span class="star">*&nbsp</span>공지기간</th>
						<td class="text"><input type="text" id="announStartDy"
							name="announStartDy" style="width: 31%;" readonly class="day" />
							<a href="javascript:void(0)" id="btnStartDate"><img
								src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif"
								alt="absmiddle" class="middle" /></a>~ <input type="text"
							id="announEndDy" name="announEndDy" style="width: 31%;" readonly
							class="day" /> <a href="javascript:void(0)" id="btnEndDate"><img
								src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif"
								alt="absmiddle" class="middle" /></a></td>
					</tr>

					<tr>
						<th><span class="star">*&nbsp</span>사용여부</th>

						<td colspan="3"><select id="useYn" name="useYn"">
								<option value="N" ${data.useYn eq 'N' ? 'selected' : ''}>미사용</option>
								<option value="Y" ${data.useYn eq 'Y' ? 'selected' : ''}>사용</option>
						</select></td>
					</tr>
				</table>
				<input type="hidden" name="regId" id="regId" value="${regId}"/>
					<input type="hidden" name=mdTalkSeq id="mdTalkSeq" value="${mdTalkSeq}"/>
						<div class="bbs_search3">

							<ul class="tit">
								<li class="tit">적용대상</li>
								<!-- 건색 조건 -->
								<li class="tit"><select name="TypeSrch" id="TypeSrch">
										<c:forEach items="${codeList}" var="SM341" begin="0">
											<option value="${SM341.MINOR_CD}">${SM341.CD_NM}</option>
										</c:forEach>
								</select></li>
								<li class="btn">
									<a href="javascript:void(0)" class="btn" id="addCol"><span><spring:message code="button.common.add" /></span></a> 
									<a href="javascript:void(0)" class="btn" id="deleteCol"><span><spring:message 	code="button.common.delete" /></span></a>
								</li>
							</ul>



							<table class="bbs_grid2" cellpadding="0" cellspacing="0"
								border="0">
								<colgroup>
									<col width="15%">
										<col width="60%">
											<col width="25%">
								</colgroup>
								<tr>
									<th><span class="star">*&nbsp</span>일괄업로드</th>
									<td>
										<input type="text" id="excelOp" name="excelOp" size="70" readonly="readonly" /> 
										<input type="file" name="file" id="file" class="text" style="display: none;" value="" /> 
										<input type="hidden" name="colNms" size="35" value="applyToCd^applyToTypeCd^applyToName" /> <!-- IBSheet 칼럼명 (구분자 ^, 필수 파라미터) -->
										<input type="hidden" name="func" value="setData" /> <!-- 실행 자바스크립트 명 ( 필수 파리미터) -->
										<input type="hidden" name="sheetNm" value="mySheet" /> <!-- IBSheet 명 (필수 파라미터)-->
										<input type="hidden" name="sheetRemoveAll" value="N" /> <input
										type="hidden" name="hdRow" value="1" /></td>
									<td>
										<a href="#" class="btn" id="createExcelForm"><span>양식</span></a> 
										<a href="#" class="btn" id="findFile"><span>찾아보기</span></a> 
										<a href="#" class="btn" id="uploadExcel"> <span> 업로드 </span> </a>
									</td>
								</tr>

							</table>

							<!-- IBSHEET  -->
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<div id="div1">
										<td><div id="ibsheet1"></div></td>
									</div>
								</tr>
							</table>
							</div>
						
			</form>
			<br></br>

	<iframe name="save" id="save" src="" " style="display: none;"></iframe>
</body>

</html>