<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"       %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"             %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"    %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"       %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />


<script type="text/javascript">
$(document).ready(function() {
	//에디터 세팅
	namoCross1.SetBodyValue(document.getElementById("contents").value);
});

function update() {
	if(!confirm("저장하시겠습니까?")) {
		return;
	}
    var form = document.popupForm;
    var url = '<c:url value="/board/updateSuggestionDetailPopup.do"/>';
    $('#contents').val(namoCross1.GetBodyValue());
    form.action = url;

	loadingMask();
    form.submit();
}
function insertComment() {
	var form = document.popupForm;
	if(form.regId.value == '') {
		alert("등록자 협력업체코드를 선택해주십시요.");
		return;
	}
	if(form.lineComment.value == '') {
		alert("코멘트를 입력해주십시요.");
		return;
	}
	if(!confirm("코멘트를 저장하시겠습니까?")) {
		return;
	}
	var url = '<c:url value="/board/insertCommentPopup.do"/>';
    form.action = url;

	loadingMask();
    form.submit();
}
function deleteComment(seq) {
	if(!confirm("코멘트를 삭제하시겠습니까?")) {
		return;
	}
	var form = document.popupForm;
	var url = '<c:url value="/board/deleteCommentPopup.do"/>';
	form.commentSeq.value = seq;
    form.action = url;

	loadingMask();
    form.submit();
}
function deleteFile(sn, streFileNm, fileStreCours, orignlFileNm) {
	if(!confirm("파일을 삭제하시겠습니까?")) {
		return;
	}
	var form = document.popupForm;
	var url = '<c:url value="/board/deleteFile.do"/>';
	form.fileSn.value = sn;
	form.streFileNm.value = streFileNm;
	form.fileStreCours.value = fileStreCours;
	form.orignlFileNm.value = orignlFileNm;
    form.action = url;
    
	loadingMask();
    form.submit();
}
function downloadFile(streFileNm, fileStreCours, orignlFileNm) {
	var form = document.popupForm;
	var url = '<c:url value="/FileDown.do"/>';
	if(streFileNm == "" || orignlFileNm == "") {
		alert('오류입니다.');
		return;
	}
	form.streFileNm.value = streFileNm;
	form.fileStreCours.value = fileStreCours;
	form.orignlFileNm.value = orignlFileNm;
	form.action = url;
	form.submit();
}
</script>

<SCRIPT language="JScript" FOR="Wec" EVENT="OnInitCompleted()">
var form = document.popupForm;
var wec = document.Wec;
</SCRIPT>
</head>

<body>

<form name="popupForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="boardSeq" value="${detail.boardSeq}" />
<input type="hidden" name="atchFileId" value="${detail.atchFileId}" />
<input type="hidden"  name="streFileNm" />
<input type="hidden"  name="orignlFileNm" />
<input type="hidden"  name="fileStreCours" />
<input type="hidden" name="fileSn" value="" />
<input type="hidden" name="commentSeq" value="" />
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>업체문의 상세</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>업체문의관리</li>
			<li class="last">업체문의 상세</li>
		</ul>
     </div>
	 
	 <!-- list -->
	<div class="popup_contents">
	<!--  @작성양식 2 -->
		<div class="bbs_search3">
		<ul class="tit">
			<li class="tit">업체문의 상세</li>
			<li class="btn">
				<c:if test="${isVendor}">
				<a href="#" class="btn" onclick="update();"><span><spring:message code="button.common.save"/></span></a>
				</c:if>
				<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
		<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:15%" />
				<col style="width:35%" />
				<col style="width:15%" />
				<col style="width:35%" />
			</colgroup>
			<tr>
				<th class="fst">제목<!-- <img src="/lim/static_root/images/boardimg/201110/201110061138808_P3FAPBN7.jpg"> --></th>
				<td>${detail.title}</td>
				<th>삭제여부</th>
				<td>
				   <input type="radio" name="delYn" value="Y" <c:if test="${detail.delYn == 'Y'}">checked</c:if>>삭제</input>
				   <input type="radio" name="delYn" value="N" <c:if test="${detail.delYn == 'N'}">checked</c:if>>사용</input>
				</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${detail.regId}</td>
				<th>작성일자</th>
				<td>
					${fn:substring(detail.regDate,0,4)}-${fn:substring(detail.regDate,4,6)}-${fn:substring(detail.regDate,6,8)}
					${fn:substring(detail.regDate,8,10)}:${fn:substring(detail.regDate,10,12)}:${fn:substring(detail.regDate,12,14)} 
				</td>
			</tr>
			<tr>
				<th class="fst">첨부파일</th>
				<td colspan="3">
					<input type="file" name="appendFile" size="85" />
				</td>
			</tr>
			<tr>
				<th class="fst">내용</th>
				<td colspan="3">
<!-- 					<DIV id="divShowInstall" style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; Z-INDEX: 0; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px; POSITION: absolute"> -->
<!-- 						<EMBED src="/edit/images/NamoBanner.swf" width=680 height=400 type=application/x-shockwave-flash></EMBED> -->
<!-- 					</Div> -->
<!-- 					<script language="javascript" src="/edit/PopupNamoWec7.js"></script> -->
					<input type="hidden" name="contents" id="contents" value='<c:out value="${detail.content}" escapeXml="false"/>' />
						<script type="text/javascript" language="javascript">
							var namoCross1 = new NamoSE('pe_agt');
							namoCross1.params.Width 		= "98%";
							namoCross1.params.Height 		= "300";
							namoCross1.params.UserLang 	= "auto";
							namoCross1.params.ImageSavePath = "edi";
							namoCross1.params.FullScreen = false;
							namoCross1.params.SetFocus 	= false; // 에디터 포커스 해제
							namoCross1.EditorStart();
						</script>
				</td>
			</tr>
			<c:if test="${not empty fileList}">
			<tr>
				<th class="fst">첨부파일</th>
				<td colspan="3">
					<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:10%" />
						<col style="width:83%" />
						<col style="width:7%" />
					</colgroup>
					<tr>
						<th>순번</th>
						<th>파일명</th>
						<th>삭제</th>
					</tr>
					<c:forEach var="result" items="${fileList}" varStatus="status">
					<tr class="fst">
						<td style='text-align: center;'>${result.fileSn}</td> <!-- 순번 --> <!--FILE_SEQ  -->
						<td style='text-align: center;'>
							<a href="javascript:downloadFile('${result.streFileNm}', '${result.fileStreCours}', '${result.orignlFileNm}')">${result.orignlFileNm}</a>
						</td> <!-- 파일명 --> <!-- FILE_NAME -->
						<td>
							<c:if test="${isVendor}">
							<a href="#" class="btn" onclick="deleteFile('${result.fileSn}', '${result.streFileNm}', '${result.fileStreCours}', '${result.orignlFileNm}');"><span><spring:message code="button.common.delete"/></span></a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
					</table>
				</td>
			</tr>
			</c:if>
			<tr>
				<th class="fst">COMMENT</th>
				<td colspan="3">
					<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:7%" />
						<col style="width:48%" />
						<col style="width:15%" />
						<col style="width:20%" />
						<col style="width:10%" />
					</colgroup>
					<tr>
					<c:if test="${not empty commentList}">
						<th>순번</th>
						<th>내용</th>
						<th>작성자</th>
						<th>등록일</th>
						<th></th>  
					</c:if>
					<c:forEach var="result" items="${commentList}" varStatus="status">
					<tr class="fst">
						<td style='text-align: center;'>${result.rankNum}</td> <!-- 순번 --> <!--FILE_SEQ  -->
						<td style='text-align: center;'>${result.lineComment}</td> <!-- 내용 -->
						<td style='text-align: center;'>${result.regId}</td><!-- 작성자 -->		
						<td style='text-align: center;'>${result.regDate}</td><!-- 등록일 -->					
						<td>
							<c:forEach items="${epcLoginVO.vendorId}" var="venArr">
							<c:if test="${venArr == result.regId}">
							<a href="#" class="btn" onclick="deleteComment('${result.commentSeq}');"><span><spring:message code="button.common.delete"/></span></a>
							</c:if>
							</c:forEach>
						</td>
					</tr>
					</c:forEach>
					<tr class="fst">
						<td>
							<select name="regId" class="select">
								<c:forEach items="${epcLoginVO.vendorId}" var="venArr">
								<option value="${venArr}">${venArr}</option>
								</c:forEach>
							</select>
						</td>
						<td colspan="3">
							<input type="text" class="text" name="lineComment" />
						</td>
						<td><a href="#" class="btn" onclick="insertComment();"><span><spring:message code="button.common.save"/></span></a></td>
					</tr>
					</table>
				</td>
			</tr>
		</table>
		</div>
	</div>
	</br>
  </div>
</form>

</body>
</html>