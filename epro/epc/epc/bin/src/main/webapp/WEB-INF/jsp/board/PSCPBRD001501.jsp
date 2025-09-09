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
<link type="text/css" rel="stylesheet" href="http://img.lottemart.com/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.validate.1.8.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.custom.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridExtension.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridConfig.js" ></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/epc/validate.js" ></script>
<script type="text/javascript" src="/js/epc/member.js" ></script>
<script type="text/javascript" src="/js/common/utils.js" ></script>

</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<form name="dataForm" id="dataForm" method="post" >
<input type="hidden" name="recommSeq" id="recommSeq" value="${data.recommSeq}">
<input type="hidden" name="exlnSltYn" id="exlnSltYn" />

  <div id="popup" >
    <!--  @title  -->
     <div id="p_title1">
		<h1>롯데ON 상품평 관리 상세정보</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>게시판관리</li>
			<li class="last">롯데ON 상품평 관리</li>
		</ul>
     </div>
	 <!--  @process  //-->
	 
	 
	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품평정보</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="25%">
				</colgroup>
				<tr>
					<th><span class="fst"></span>판매자상품코드</th>
					<td>${rvDetail.SspdNo}</td>
					<th><span class="fst"></span>회원번호</th>
					<td>${rvDetail.SmbNo}</td>
					<th><span class="fst"></span>리뷰등록일시</th>
					<fmt:parseDate value="${rvDetail.SrvRegDttm}" var='rvRegDttm'  pattern="yyyyMMddHHmm" scope="page" />
					<td><fmt:formatDate value="${rvRegDttm}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
				<tr>
					<th><span class="fst"></span>리뷰유형코드</th>
					<td>${rvDetail.SrvTypCd}</td>
					<th><span class="fst"></span>리뷰구분코드</th>
					<td>${rvDetail.SrvDvsCd}</td>
					<th><span class="fst"></span>리뷰컨텐츠정보</th>
					<td>${rvDetail.SrvCtntTypCd}</td>
				</tr>
				<tr>
					<th><span class="fst"></span>리뷰콘텐츠순번</th>
					<td>${rvDetail.SrvCtntRteNm}</td>
					<th><span class="fst"></span>리뷰콘텐츠유형코드</th>
					<td>${rvDetail.SrvCtntTypCd}</td>
					<th><span class="fst"></span>상품만족도값</th>
					<td>${rvDetail.SpdStfdVal}</td>
				</tr>
				<tr>
					<th><span class="fst"></span>리뷰콘텐츠경로명</th>
					<td colspan="5">${rvDetail.SrvCtntRteNm}</td>
				</tr>
				<tr>
					<th><span class="fst"></span>리뷰내용</th>
					<td colspan="5">
						<textarea name="qst" id="qst" style="width: 95%; height: 200px" wrap="hard" disabled="disabled">${rvDetail.SrvCnts}</textarea>
					</td>
				</tr>
			</table>
		</div>
		<!--  @작성양식  2//-->
	</div>
  </div>
</form>  
<iframe name="_if_save" src="/html/epc/blank.html" style="display:none;"></iframe>
</body>
</html>