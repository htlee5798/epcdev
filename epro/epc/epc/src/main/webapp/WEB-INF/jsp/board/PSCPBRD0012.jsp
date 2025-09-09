<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />


<script type="text/javascript">
function doSearch(){
	opener.doSearch();
	window.close();
}

function reInsert(board_seq){
	var board = board_seq;
    var url ='<c:url value="/board/selectReCcQnaDetailPopup.do"/>?boardSeq='+board; /* 팝업창 주소 */
	Common.centerPopupWindow(url, 'scmReCcQnaDetailPopup', {width : 850, height : 500, scrollBars : 'YES'});
}

function update() {
	if(!confirm("저장하시겠습니까?")) {
		return;
	}
    var form = document.popupForm;
    var url = '<c:url value="/board/updateCcQnaDetailPopup.do"/>';
    
	form.action = url;

	loadingMask();
    form.submit();
}

function deleteFile(sn, streFileNm, fileStreCours, orignlFileNm) {
	if(!confirm("파일을 삭제하시겠습니까?")) {
		return;
	}
	var form = document.popupForm;
	var url = '<c:url value="/board/deleteCcQnaFile.do"/>';
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

</head>

<body>

<form name="popupForm" method="post" enctype="multipart/form-data">
<input type="hidden" name="boardSeq" value="${detail.boardSeq}" />
<input type="hidden" name="atchFileId" value="${detail.atchFileId}" />
<input type="hidden" name="delYn" value="${detail.delYn}"/>
<input type="hidden"  name="streFileNm" />
<input type="hidden"  name="orignlFileNm" />
<input type="hidden"  name="fileStreCours" />
<input type="hidden" name="fileSn" value="" />
<input type="hidden" name="commentSeq" value="" />
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>고객센터문의 상세</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>고객센터문의관리</li>
			<li class="last">고객센터문의 상세</li>
		</ul>
     </div>
	 
	 <!-- list -->
	<div class="popup_contents">
	<!--  @작성양식 2 -->
		<div class="bbs_search3">
		<ul class="tit">
			<li class="tit">고객센터문의 상세</li>
			<li class="btn">
				<c:if test="${detail.boardPrgsStsCd != '4'}">
				<a href="#" class="btn" onclick="reInsert('${detail.boardSeq}');"><span>답변으로 등록</span></a>
				</c:if>
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
				<th>문의유형</th>
				<td>${detail.clmLgrpNm}</td>
				<th class="fst">진행상태</th>
				<td>
					<select name="boardPrgsStsCd" id="boardPrgsStsCd" style="width:70px;">
						<c:forEach items="${codeList3}" var="code" begin="0">
							<option value="${code.MINOR_CD}" <c:if test="${detail.boardPrgsStsCd eq code.MINOR_CD}">selected="selected"</c:if>> ${code.CD_NM }</option>
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<th class="fst">상품명</th>
				<td>${detail.let4Ref} [${detail.let3Ref}]</td>
				<th>주문번호</th>
				<td>${detail.orderId}</td>
			</tr>
			
			<c:if test="${detail.acceptLocaCd eq '7'}">
			<tr>
				<th class="fst">협력업체</th>
				<td>${detail.let1Ref}</td>
				<th>담당자</th>
				<td>${detail.memberNm}</td>
			</tr>
			<tr>
				<th class="fst">연락처</th>
				<td>${detail.cellNo}</td>
				<th>답변 E-Mail</th>
				<td>${detail.email}</td>
			</tr>	
			</c:if>
			
			<c:if test="${detail.acceptLocaCd != '7'}">
			<tr>
				<th class="fst">협력업체</th>
				<td colspan="3">${detail.let1Ref}</td>
			</tr>
			</c:if>		
			<tr>
				<th>작성자</th>
				<td>
					${detail.regId}
					<c:if test="${fn:trim(detail.regNm) != ''}">
					(${detail.regNm})
					</c:if>
				</td>
				<th>담당자</th>
				<td>
					${detail.acceptId} 
					<c:if test="${fn:trim(detail.acceptNm) != ''}">
					(${detail.acceptNm})
					</c:if>
				</td>
			</tr>
			<tr>
				<th class="fst">제목<!-- <img src="/lim/static_root/images/boardimg/201110/201110061138808_P3FAPBN7.jpg"> --></th>
				<td colspan="3">
					${detail.title}
					<input type="hidden" name="title" value="${detail.title}"/>
				</td>
			</tr>
			<tr>
				<th class="fst">내용</th>
				<td colspan="3">
					<textarea name="content" id="content" cols="100" rows="15" wrap="hard" style="ime-mode:active" readonly="readonly">${detail.content}</textarea>
				</td>
			</tr>
			<c:if test="${not empty fileList}">
			<tr>
				<th class="fst">첨부파일</th>
				<td colspan="3">
					<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:10%" />
						<col style="width:90%" />
					</colgroup>
					<tr>
						<th style='text-align: center;'>순번</th>
						<th style='text-align: center;'>파일명</th>
					</tr>
					<c:forEach var="result" items="${fileList}" varStatus="status">
					<tr class="fst">
						<td style='text-align: center;'>${result.fileSn}</td> <!-- 순번 --> <!--FILE_SEQ  -->
						<td style='text-align: center;'>
							<a href="javascript:downloadFile('${result.streFileNm}', '${result.fileStreCours}', '${result.orignlFileNm}')">${result.orignlFileNm}</a>
						</td> <!-- 파일명 --> <!-- FILE_NAME -->
						
					</tr>
					</c:forEach>
					</table>
				</td>
			</tr>
			</c:if>
		</table>
		</div>
		
		<c:if test="${fn:length(relationList) > 1}">
		<br/>
		
		<b>&nbsp; # 답글 목록 #</b><br/>
		<table width="100%" border="0" cellpadding="2" cellspacing="0" bgcolor="#1A5A66">
		<c:forEach var="item" items="${relationList}"  >
		<c:if test="${item.depth > 1}">
			<tr>
				<td height="30" align="left"> 
					&nbsp; <font color="#ffff1a">${item.title}</font>
				</td>  
				<td align="right">
					<font color="#FFFFFF" >등록 : ${item.regNm} | ${item.regDate}</font>&nbsp;<br/>
				</td>
			</tr>
			<tr>
				<td height="120" bgcolor="#FFFFFF" colspan="2">
					<textarea rows="10" style="width:100%;background-color:#EFEFEF;border:0px;" readonly disbled="true">${item.content}</textarea>
				</td>
			</tr>
		</c:if>
		</c:forEach>
		</table>
		</c:if>
	</div>
	</br>
  </div>
</form>

</body>
</html>