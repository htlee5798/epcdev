<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- 공통 css 및 js 파일 commonHead.do 강제 추가 이동빈 20120215-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.validate.1.8.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.custom.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridExtension.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridConfig.js" ></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/epc/validate.js" ></script>
<script type="text/javascript" src="/js/epc/member.js" ></script>
<script type="text/javascript" src="/js/common/utils.js" ></script>






<script>
	/** ********************************************************
	 * 첨부파일 다운로드
	 ******************************************************** */
	function downloadFile(streFileNm, fileStreCours, orignlFileNm) {
		var form = document.AdminForm;
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
<form name="AdminForm" method="post">

<input type="hidden"  name="streFileNm" />
<input type="hidden"  name="orignlFileNm" />
<input type="hidden"  name="fileStreCours" />

  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>공지사항 상세</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>공지사항</li>
			<li class="last">공지사항 상세</li>
		</ul>
     </div>
	 
	 
	 <!-- list -->
	 <div >
		<div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">공지사항상세
				<li class="btn">
				<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
				</li>
				
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col style="width:15%" />
					<col style="width:30%" />
					<col style="width:15%" />
					<col style="width:30%" />
				</colgroup>
				<tr>
					<th class="fst">제목</th>
					<td colspan="3">${detail.title}</td>
				</tr>
				<tr>
					<th class="fst">작성자</th>
					<td>${detail.regId}</td>
					<th class="fst">작성일자</th>
					<td><!-- 작성일자 -->
						<fmt:parseDate value="${detail.wrtDy}" var="dateFmt" pattern="yyyymmdd" />
						<fmt:formatDate value="${dateFmt}" pattern="yyyy-mm-dd" type="date" />					
					</td>
				</tr>
				<tr>
					<th class="fst">내용</th>
					<td colspan="3"><c:out value="${detail.content}" escapeXml="false" ></c:out></td>
					
				</tr>
				<c:if test="${not empty fileList}">
				<tr>
					<th class="fst">첨부파일</th>
					<td colspan="3">
						<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:10%" />
							<col style="width:50%" />
							<col style="width:15%" />
						</colgroup>
						<tr>
							<th>순번</th>
							<th>파일명</th>
							<th>다운로드</th>
						</tr>
						<c:forEach var="result" items="${fileList}" varStatus="status">
						<tr class="fst">
							<td>${result.fileSn}</td> <!-- 순번 -->
							<td>${result.orignlFileNm}</td> <!-- 파일명 -->
							<td><input type="button" value="다운로드" onclick="downloadFile('${result.streFileNm}', '${result.fileStreCours}', '${result.orignlFileNm}')" /></td>
						</tr>                              
						</c:forEach>
						</table>
					</td>
				</tr>
				</c:if>
			</table>
			</div>
		</div>
	</div>
</div>	
</form>

</body>
</html>