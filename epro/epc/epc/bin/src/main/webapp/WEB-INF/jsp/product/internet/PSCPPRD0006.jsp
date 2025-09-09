<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.GregorianCalendar" %>
<%@page import="java.util.List" %>
<%@page import="com.lottemart.epc.product.model.PSCPPRD0006VO" %>
<%@page import="com.lottemart.epc.common.util.SecureUtil" %>
<%@page import="lcn.module.common.util.DateUtil" %>
<%@page import="lcn.module.common.util.CommUtil" %>
<%
	String prodCd = SecureUtil.stripXSS((String) request.getParameter("prodCd"));
	String vendorId = SecureUtil.stripXSS((String) request.getParameter("vendorId"));
	String imgDir = (String) request.getAttribute("imgDir");
	String tabNo = "6";
	List<PSCPPRD0006VO> resultList = (List<PSCPPRD0006VO>) request.getAttribute("resultList");
	List<PSCPPRD0006VO> wideList = (List<PSCPPRD0006VO>) request.getAttribute("wideList");
	PSCPPRD0006VO videoInfo = (PSCPPRD0006VO) request.getAttribute("videoInfo");

	String itemCd = "";
	String mdSrcmkCd = "";
	String prodImagePath = "";
	String title = "";
	String colorNm = "";
	String sizeNm = "";
	//<<< 2015.12.03. by kmlee 속성 체계변경으로 변수 추가
	String classNm = "";
	String attrsNm = "";
	String imgQty = "";

	int maxRow = 0;
	String rowCnt = "";
	int deleteRow = 0;

	String imgFullDir = null;
	String currenMilliSecond = null;
	Calendar calDate = null;
	long calTimeInMillis = 0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />
<script language="javascript" type="text/javascript">
	//JQuery 초기화
	$(document).ready(function() {
		//input enter 막기
		$("*").keypress(function(e) {
			if (e.keyCode==13) return false;
		});
	});

	//업로드
	function doUpload(f) {
		if (!f.file1.value) {
			alert("선택된 이미지가 없습니다.");
			return;
		}

		//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공
		if (f.mode.value == "update") {
			<c:forEach items="${aprvList}" var="list" varStatus="status">
				if (f.rowCnt.value == '<c:out value="${list.PROD_IMG_NO}" />') {
					if ("<c:out value='${list.APRV_CD}' />" == "001") {
						alert("현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다.");
					} else if ("<c:out value='${list.APRV_CD}' />" == "002") {
						alert("현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→대표이미지수정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)");
					}
				}
			</c:forEach>
		}

		if ( confirm("선택된 대표이미지를 업로드 하시겠습니까?") ) {
			f.submit();
		}
	}

	//와이드이미지 업로드
	function doWideUpload(f) {
		if (!f.file2.value) {
			alert("선택된 이미지가 없습니다.");
			return;
		}

		//20180715 - 상품수정요청 시 승인요청 또는 반려 일 경우 알림 메시지 제공
		if (f.mode.value == "update") {
			<c:forEach items="${aprvList}" var="list" varStatus="status">
				if (f.rowCnt.value == '<c:out value="${list.PROD_IMG_NO}" />') {
					if ("<c:out value='${list.APRV_CD}' />" == "001") {
						alert('현재 수정한 상품은 상품정보수정 승인요청 중인 상품입니다.');
					} else if ("<c:out value='${list.APRV_CD}' />" == "002") {
						alert("현재 수정한 상품은 상품정보수정요청 중 반려된 상품으로 수정 후 아래의 경로에서 <재요청> 이 필요합니다.\n\n([SCM]상품정보관리→상품정보수정요청→대표이미지수정리스트→목록 중 [보기] 항목 선택→<재요청> 신청)");
					}
				}
			</c:forEach>
		}

		if (confirm("선택된 와이드이미지를 업로드 하시겠습니까?")) {
			f.submit();
		}
	}

	//삭제
	function doDelete(f) {
		f.mode.value = "delete";
		if (confirm("선택된 대표이미지를 삭제 하시겠습니까?")) {
			f.submit();
		}
		f.mode.value = "update";
	}

	//와이드이미지 삭제
	function doWideDelete(f) {
		f.mode.value = "delete";
		if (confirm("선택된 와이드이미지를 삭제 하시겠습니까?")) {
			f.submit();
		}
		f.mode.value = "update";
	}

	//이미지 개별 수정 팝업
	function viewImg(url, viewSize, size, nameSize, rowCnt, mdSrcmkCd) {
		var targetUrl = url;
		var targetUrl = "<c:url value='/product/selectProductImageDetailForm.do'/>?mdSrcmkCd="+mdSrcmkCd+"&prodCd=<%=prodCd%>&vendorId=<%=vendorId%>&size="+size+"&rowCnt="+rowCnt+"&viewSize="+viewSize+"&nameSize="+nameSize+"&url="+url;
		Common.centerPopupWindow(targetUrl, 'prdPrice', {width : 800, height : 800});
	}

	//이미지 체크
	function CkImageVal(formName) {
		var oInput = event.srcElement;
		var fname  = oInput.value;
		var formNm = formName;

		if ((/(.jpg)$/i).test(fname)) {
			oInput.parentElement.children[0].src = fname;
		} else {
			var f = eval("document."+formNm+".file1");
			var t = "<input type=\"file\" name=\"file1\" size=\"50\" ";
			var c = "onChange=\"CkImageVal('";
			var e = "')\" >";

			f.outerHTML = (t+c+formNm+e);
			alert("이미지는 jpg 파일만 가능합니다.");

			return;
		}
	}

	function youtubePopup() {
		var prodUrl = $("#PROD_URL").val();

		if (prodUrl != null && prodUrl != "") {
			if (prodUrl.indexOf("youtu.be/") > -1) {
				prodUrl = prodUrl.substring(prodUrl.indexOf("youtu.be/")+9 , prodUrl.length);
				Common.centerPopupWindow( "https://www.youtube.com/embed/" + prodUrl, 'YoutubePopup', {width : 600, height : 450});
			} else {
				Common.centerPopupWindow( prodUrl, 'YoutubePopup', {width : 600, height : 450});
			}
		} else {
			alert("미리보기할 내용이 없습니다.");	
		}
	}

	function youtubeSave() {
		var prodUrl =  $("#PROD_URL").val();
		if ( confirm("저장하시겠습니까?") ) {
			callAjaxByForm('#dataForm', '<c:url value="/product/youtubeSave.do"/>?PROD_CD=<%=prodCd%>&vendorId=<%=vendorId%>&PROD_URL='+prodUrl, '#form1', 'POST');
		}
	}

	// 상품 일괄 비전시 아작스 호출
	function callAjaxByForm(form, url, target, Type) {

		var formQueryString = $('*', '#form1').fieldSerialize();
		$.ajax({
			type: Type,
			url: url,
			data: formQueryString,
			success: function(json) {
				try {
					// 권한에러 메시지 처리 조건문 
					if (jQuery.trim(json) == "accessAlertFail") {
						alert("<spring:message code='msg.common.error.noAuth'/>");
					} else {
						if (jQuery.trim(json) == "") {//처리성공
							alert("<spring:message code='msg.common.success.insert'/>");
						} else {
							alert(jQuery.trim(json));
						}
					}
				} catch (e) {}
			},
			error: function(e) {
				alert("저장에 실패하였습니다");
			}
		});
	}

	function CkVideoVal(file) {
		if ($(file).val() != "") {
			// 확장자 체크
			var ext = $(file).val().split(".").pop().toLowerCase();
			if ($.inArray(ext, [ "mp4", "avi", "mov", "mkv", "wma", "mpeg" ]) == -1) {
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

	function videoUpdate(f, mode) {
		var file = $("input[name=videoFile]").val();
		var prodCd = "<c:out value='${prodCd}'/>";
		var videoUrl = $("#videoUrl").val();
		if (file == "" && mode == "update") {
			alert("선택된 동영상이 없습니다.");
			return;
		}
		if (mode == "update" && confirm("저장하시겠습니까?")) {
			f.submit();
		}
		if (mode == "delete" && confirm("삭제하시겠습니까??")) {
			f.mode.value = "delete";
			f.submit();
		}
		f.mode.value = "update";
	}

</script>
</head>
<body>
<input type="hidden" name="imgWidth">
<input type="hidden" name="imgHeight">
<!-- form name="dataForm" id="dataForm"-->
<div id="content_wrap" style="width:1000px; height:200px;">
	<div id="wrap_menu" style="width:1000px">
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
				<ul class="tit">
					<li class="tit">대표이미지</li>
				</ul>
				<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<td width="60">
						<td width="100">
						<td width="100">
						<td width="40">
						<td width="60">
						<td width="60">
						<td width="60">
						<td width="60">
						<td width="60">
						<td width="60">
						<td width="90">
					</codgroup>
					<tr>
						<th>단품코드</td>
						<th>컬러</td>
						<th>사이즈</td>
						<th>순번</td>
						<th>80</td>
						<th>90</td>
						<th>120</td>
						<th>208</td>
						<th>250</td>
						<th>500</td>
						<th></td>
					</tr>
<%
	//--실제 파일명
	String img80 = "";
	String img90 = "";
	String img120 = "";
	String img208 = "";
	String img250 = "";
	String img500 = "";
	//--캐쉬를 막기위한 더미 파일명
	String img80D = "";
	String img90D = "";
	String img120D = "";
	String img208D = "";
	String img250D = "";
	String img500D = "";

	if (resultList != null) {
		int listSize = resultList.size();

		PSCPPRD0006VO bean;

		calDate = new GregorianCalendar();
		calTimeInMillis = calDate.getTimeInMillis();

		for (int i = 0; i < listSize; i++) {
			bean = (PSCPPRD0006VO) resultList.get(i);

			itemCd = (String) bean.getItemCd();
			mdSrcmkCd = (String) bean.getMdSrcmkCd();
			prodImagePath = (String) bean.getProdImagePath();
			title = (String) bean.getTitle();

			/* colorNm = (String)bean.getColorNm();
			sizeNm = (String)bean.getSizeNm(); */
			/* colorNm = (String) bean.getClassNm();
			sizeNm = (String) bean.getAttrsNm(); */

			maxRow = Integer.parseInt(bean.getMaxRow());
			rowCnt = (String) bean.getRowCnt();
			deleteRow = Integer.parseInt(bean.getDeleteRow());
			//--실제 파일명
			img80 = mdSrcmkCd + "_" + rowCnt + "_80.jpg";
			img90 = mdSrcmkCd + "_" + rowCnt + "_90.jpg";
			img120 = mdSrcmkCd + "_" + rowCnt + "_120.jpg";
			img208 = mdSrcmkCd + "_" + rowCnt + "_208.jpg";
			img250 = mdSrcmkCd + "_" + rowCnt + "_250.jpg";
			img500 = mdSrcmkCd + "_" + rowCnt + "_500.jpg";
			//--캐쉬를 막기위한 더미 파일명
			img80D = mdSrcmkCd + "_" + rowCnt + "_80.jpg?dummy=" + calTimeInMillis;
			img90D = mdSrcmkCd + "_" + rowCnt + "_90.jpg?dummy=" + calTimeInMillis;
			img120D = mdSrcmkCd + "_" + rowCnt + "_120.jpg?dummy=" + calTimeInMillis;
			img208D = mdSrcmkCd + "_" + rowCnt + "_208.jpg?dummy=" + calTimeInMillis;
			img250D = mdSrcmkCd + "_" + rowCnt + "_250.jpg?dummy=" + calTimeInMillis;
			img500D = mdSrcmkCd + "_" + rowCnt + "_500.jpg?dummy=" + calTimeInMillis;
			imgQty = (String) bean.getImgQty();

			imgFullDir = imgDir + "/" + mdSrcmkCd.substring(0, 5) + "/";

			if (deleteRow < 999 && Integer.parseInt(rowCnt) < 5) {
%>
					<form name="filesForm<%=i + 1%>" action="<c:url value="/product/updateProductImage.do"/>" method="post" enctype="multipart/form-data" target="submitframe">
					<tr class="r1">
						<td rowspan="2"><%=itemCd %></td>
						<td rowspan="2"><%=classNm%></td>
						<td rowspan="2"><%=attrsNm %></td>
						<td rowspan="2"><%=title  %></td>

							<input type="hidden" name="vendorId" value="<%=vendorId%>" />
							<input type="hidden" name="prodCd" value="<%=prodCd%>" />
							<input type="hidden" name="itemCd" value="<%=itemCd%>" />
							<input type="hidden" name="mdSrcmkCd" value="<%=mdSrcmkCd%>" />
							<input type="hidden" name="prodImagePath" value="<%=prodImagePath%>" />
							<input type="hidden" name="imgQty" value="<%=imgQty%>" />
							<input type="hidden" name="rowCnt" value="<%=rowCnt%>" />
							<input type="hidden" name="mode" value="update" />

						<td><img src="<%=imgFullDir%><%=img80D%>"  <%-- onclick="viewImg('<%=imgFullDir%><%=img75%>' ,60,75,75,   '<%=rowCnt%>','<%=mdSrcmkCd%>')" style="cursor:hand"--%> align="center" border="0" width="60" height="60"></td>
						<td><img src="<%=imgFullDir%><%=img90D%>"  <%-- onclick="viewImg('<%=imgFullDir%><%=img75%>' ,75,75,75,   '<%=rowCnt%>','<%=mdSrcmkCd%>')" style="cursor:hand"--%> align="center" border="0" width="60" height="60"></td>
						<td><img src="<%=imgFullDir%><%=img120D%>" <%-- onclick="viewImg('<%=imgFullDir%><%=img100%>',100,100,100,'<%=rowCnt%>','<%=mdSrcmkCd%>')" style="cursor:hand"--%> align="center" border="0" width="60" height="60"></td>
						<td><img src="<%=imgFullDir%><%=img208D%>" <%-- onclick="viewImg('<%=imgFullDir%><%=img160%>',154,154,160,'<%=rowCnt%>','<%=mdSrcmkCd%>')" style="cursor:hand"--%> align="center" border="0" width="60" height="60"></td>
						<td><img src="<%=imgFullDir%><%=img250D%>" <%-- onclick="viewImg('<%=imgFullDir%><%=img250%>',220,220,250,'<%=rowCnt%>','<%=mdSrcmkCd%>')" style="cursor:hand"--%> align="center" border="0" width="60" height="60"></td>
						<td><img src="<%=imgFullDir%><%=img500D%>" <%-- onclick="viewImg('<%=imgFullDir%><%=img500%>',400,400,500,'<%=rowCnt%>','<%=mdSrcmkCd%>')" style="cursor:hand"--%> align="center" border="0" width="60" height="60"></td>
<%			if ((deleteRow == 1 && i > 0) || Integer.parseInt(rowCnt) > 3) { %>
						<td><li class="btn" style="margin-right:15px;"><a href="javascript:doDelete(document.filesForm<%=i + 1%>)" class="btn" ><span><spring:message code="button.common.delete"/></span></a></li></td>
<%				} else { %>
						<td></td>
<%			} %>
					</tr>
					<tr>
						<td colspan="6"> 이미지 등록 <input type="file" size="50" name="file1" onChange="CkImageVal('filesForm<%=i+1%>')"></td>
						<td><li class="btn" style="margin-right:15px;"><a href="javascript:doUpload(document.filesForm<%=i+1%>)" class="btn"><span><spring:message code="button.common.modify"/></span></a></li></td>
					</tr>
					</form>
<%			} else if (deleteRow >= 999) { %>
					<FORM name="filesForm<%=i+1%>" action="<c:url value="/product/updateProductImage.do"/>" method="post" enctype="multipart/form-data" target="submitframe">
					<input type="hidden" name="vendorId" value="<%=vendorId%>" />
					<input type="hidden" name="prodCd" value="<%=prodCd%>" />
					<input type="hidden" name="itemCd" value="<%=itemCd%>" />
					<input type="hidden" name="mdSrcmkCd" value="<%=mdSrcmkCd%>" />
					<input type="hidden" name="prodImagePath" value="<%=prodImagePath%>" />
					<input type="hidden" name="imgQty" value="<%=imgQty%>" />
					<input type="hidden" name="rowCnt" value="<%=rowCnt%>" />
					<input type="hidden" name="mode" value="insert" />
					<%if (i<4) { %>
					<tr>
						<td><%=itemCd%></td>
						<td><%=classNm%></td>
						<td><%=attrsNm%></td>
						<td>-</td>
						<td colspan="6"> 이미지 등록 <input type="file" size="50" name="file1" onChange="CkImageVal('filesForm<%=i+1%>')"></td>
						<td><li class="btn" style="margin-right:15px;"><a href="javascript:doUpload(document.filesForm<%=i+1%>)" class="btn" ><span><spring:message code="button.common.create"/></span></a></li></td>
					</tr>
					<% }%>
					</FORM>
<%
			}
		}
	}
%>
					<tr align="center" height="30" bgcolor="white">
						<td>-</td>
						<td>-</td>
						<td>-</td>
						<td><font style="size:2;" color="red">Youtube</font></td>
						<td colspan="6">
							&nbsp;&nbsp;&nbsp;<input type="text" name="PROD_URL" id="PROD_URL" value="${resultMap.PROD_URL}" style="width: 65%;" />&nbsp;<a href="javascript:youtubePopup()" class="btn"><span>미리보기</span></a>
							<a href="javascript:youtubeSave()" class="btn"><span>저장</span></a>
						</td>
						<td>-</td>
					</tr>
				</table>
			</div>
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">와이드이미지</li>
				</ul>
				<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<td width="60">
						<td width="100">
						<td width="100">
						<td width="40">
						<td width="350">
						<td width="100">
					</codgroup>
					<tr>
						<th>단품코드</td>
						<th>컬러</td>
						<th>사이즈</td>
						<th>순번</td>
						<th colspan="5">720x405</td>
						<th colspan="2"></td>
					</tr>
<%
	String img720 = "";

	//--캐쉬를 막기위한 더미 파일명
	String img720D = "";

	if (wideList != null ) {
		int widelistSize = wideList.size();

		PSCPPRD0006VO bean;

		calDate = new GregorianCalendar();
		calTimeInMillis = calDate.getTimeInMillis();

		for (int i=0; i < widelistSize; i++) {
			bean = (PSCPPRD0006VO)wideList.get(i);
	
			itemCd = (String)bean.getItemCd();
			mdSrcmkCd = (String)bean.getMdSrcmkCd();
			prodImagePath  = (String)bean.getProdImagePath();
			title = (String)bean.getTitle();
			//colorNm = (String)bean.getClassNm();
			//sizeNm = (String)bean.getAttrsNm();
			maxRow = Integer.parseInt(bean.getMaxRow());
			deleteRow = Integer.parseInt(bean.getDeleteRow());
			//--실제 파일명
			img720 = mdSrcmkCd + "_00_720_405.jpg";
			//--캐쉬를 막기위한 더미 파일명
			img720D = mdSrcmkCd + "_00_720_405.jpg?dummy="+calTimeInMillis;
			imgQty = (String)bean.getImgQty();

			imgFullDir = imgDir + "/wide/" + mdSrcmkCd.substring(0, 5) + "/" + mdSrcmkCd.substring(5, 9) +"/" ;

			if (deleteRow < 999) {
%>
					<form name="wideFilesForm2" action="<c:url value="/product/updateProductImage.do"/>" method="post" enctype="multipart/form-data" target="submitframe">
					<tr class="r1">
						<td rowspan="2"><%=itemCd %></td>
						<td rowspan="2"><%=classNm%></td>
						<td rowspan="2"><%=attrsNm %></td>
						<td rowspan="2"><%=title  %></td>

							<input type="hidden" name="vendorId" value="<%=vendorId%>" />
							<input type="hidden" name="prodCd" value="<%=prodCd%>" />
							<input type="hidden" name="itemCd" value="<%=itemCd%>" />
							<input type="hidden" name="mdSrcmkCd" value="<%=mdSrcmkCd%>" />
							<input type="hidden" name="prodImagePath" value="<%=prodImagePath%>" />
							<input type="hidden" name="imgQty" value="<%=imgQty%>" />
							<input type="hidden" name="rowCnt" value="00" />
							<input type="hidden" name="mode" value="update" />

						<td colspan="5"><img src="<%=imgFullDir%><%=img720D%>" <%-- onclick="viewImg('<%=imgFullDir%><%=img500%>',400,400,500,'<%=rowCnt%>','<%=mdSrcmkCd%>')" style="cursor:hand"--%> align="center" border="0" width="300" height="170"></td>
						<td colspan="2"><li class="btn" style="margin-right:15px;"><a href="javascript:doWideDelete(document.wideFilesForm2)" class="btn" ><span><spring:message code="button.common.delete"/></span></a></li></td>

					</tr>
					<tr>
						<td colspan="5"> 이미지 수정 <input type="file" size="50" name="file2" onChange="CkImageVal('wideFilesForm2')"></td>
						<td colspan="2"><li class="btn" style="margin-right:15px;"><a href="javascript:doWideUpload(document.wideFilesForm2)" class="btn"><span><spring:message code="button.common.modify"/></span></a></li></td>
					</tr>
					</FORM>
<%			} else if (deleteRow == 999 && widelistSize < 2) { //신규등록폼 %>
					<form name="wideFilesForm1" action="<c:url value="/product/updateProductImage.do"/>" method="post" enctype="multipart/form-data" target="submitframe">
					<input type="hidden" name="vendorId" value="<%=vendorId%>" />
					<input type="hidden" name="prodCd" value="<%=prodCd%>" />
					<input type="hidden" name="itemCd" value="<%=itemCd%>" />
					<input type="hidden" name="mdSrcmkCd" value="<%=mdSrcmkCd%>" />
					<input type="hidden" name="prodImagePath" value="<%=prodImagePath%>" />
					<input type="hidden" name="imgQty" value="<%=imgQty%>" />
					<input type="hidden" name="rowCnt" value="00" />
					<input type="hidden" name="mode" value="insert" />
					<tr>
						<td><%=itemCd%></td>
						<td><%=classNm%></td>
						<td><%=attrsNm%></td>
						<td>-</td>
						<td colspan="5"> 와이드 이미지 등록 <input type="file" size="50" name="file2" onChange="CkImageVal('wideFilesForm1')"></td>
						<td colspan="2"><li class="btn" style="margin-right:15px;"><a href="javascript:doWideUpload(document.wideFilesForm1)" class="btn"><span><spring:message code="button.common.create"/></span></a></li></td>
					</tr>
				</form>
<%  
			}
		}
	}
%>
				</table>
			</div>
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit">동영상</li>
				</ul>
				<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tbl_02" summary="동영상 경로">
					<colgroup>
						<col width="100%">
					</colgroup>
					<tbody>
						<tr>
							<td><input type="text" name="VIDEO_URL" id="VIDEO_URL" value="${videoInfo.videoUrl }" disabled="disabled" style="width: 65%;"></td>
						</tr>
						<tr>
							<td>
								<form name="videoFileForm1" action="<c:url value="/product/updateProductVideo.do"/>" method="post" enctype="multipart/form-data" target="submitframe">
									<input type="hidden" name="vendorId" value="<%=vendorId%>" />
									<input type="hidden" name="prodCd" value="<%=prodCd%>" />
									<input type="hidden" name="mode" value="update" />
									<input type="file" size="20" name="videoFile" onchange="CkVideoVal(this);" style="width: 450px">
									<span class="btnBG1"><a href="javascript:videoUpdate(document.videoFileForm1, 'update');" class="btn"><span>저장</span></a></span>
									<c:if test="${not empty videoInfo.videoUrl}">
									<span class="btnBG6"><a href="javascript:videoUpdate(document.videoFileForm1, 'delete');" class="btn"><span>삭제</span></a></span>
									</c:if>
								</form>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<br />
			<div class="bbs_list" style="width:70%;">
				<table border="0" cellpadding="0" cellspacing="1" class="bbs_list">
					<colgroup>
						<col width="20%">
						<col width="30%">
						<col width="50%">
					</colgroup>
					<tbody>
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
					</tbody>
				</table>
			</div>
			<div>
			<strong><font color="red">* 동영상 등록시 노출 영역</font></strong><br/>
			- 묶음상품 상품상세 대표이미지 영역에 노출됩니다. (이미지 > 동영상 순)
			</div>
		</div>
	</div>
</div>
<form name="form1" id="form1">
	<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
	<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>
<form name="form2" id="form2" action="<c:url value="/product/selectProductImageForm.do"/>" method="post" target="_self">
	<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
	<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>
<iframe name="submitframe" style="display:none"></iframe>
</body>
</html>