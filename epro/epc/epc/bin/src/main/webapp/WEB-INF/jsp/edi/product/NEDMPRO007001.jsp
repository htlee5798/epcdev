<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css"></link>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<%@ include file="/common/scm/scmCommon.jsp"%>


	<script type="text/javascript">
		$(document) .ready( function() {
			
			// 조회버튼 클릭
			$('#search').click(function() {
				doSearch();	
			});

			// START of IBSheet Setting
			createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "230px");
			mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

			var ibdata = {};
			// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
			ibdata.Cfg = { SizeMode : sizeAuto, SearchMode : smGeneral, MergeSheet : msHeaderOnly 	}; // 10 row씩 Load
			// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
			ibdata.HeaderMode = { Sort : 1, ColMove : 0, ColResize : 1, HeaderCheck : 1 };

			// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
			ibdata.Cols = [ 
			 { Header : "순번", Type : "Text", SaveName : "num", Align : "Center", Width : 50, Edit : 0 }
			, { Header : "코드", Type : "Text", SaveName : "code", Align : "Center", Width : 140, Edit : 0 }
			, { Header : "코드명", Type : "Text", SaveName : "name", 	Align : "Center", Width : 290, Edit : 0 }
			];

			IBS_InitSheet(mySheet, ibdata); 
			
			mySheet.SetEllipsis(1);	//말줄임
			mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
			mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

			}); // end of ready
			
			 function doSearch() {
				 goPage('1');
			}
			
			 function goPage(currentPage){
				var url ='NEDMPRO007001Detail.do';
				loadIBSheetData(mySheet, url, currentPage, '#dataForm', null);
			 }
			
	</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<form name="dataForm" id="dataForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="category" name="category" value="${category}"/>
	
	<div id="popup">
         <!--  @title  -->
                              <div id="p_title1">
                                  <h1>상품일괄등록 코드 검색 </h1>
                                  <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
                              </div>
                              <!--  @title  //-->
         <!--  @process  -->
         <div id="process1">
             <ul>
                 <li>홈</li>
                 <li>상품정보</li>
                 <li>일괄등록 카테고리 조회</li>
                 <li class="last">상세조회</li>
             </ul>
         </div>
         <!--  @process  //-->
                                    
		<div class="popup_contents">

		<div class="bbs_search">
							<ul class="tit">
								<li class="tit">조회 조건</li>
								<li class="btn"><a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire" /></span></a></li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">
								<colgroup>
									<col style="width: 15%" />
									<col style="width: 35%" />
									<col style="width: 15%" />
									<col style="width: 35%" />
								</colgroup>
								<tr>
									<th><span class="star">*</span>코드구분</th>
									<td>
									
									<select  id="orderItem"" name="orderItem" class="select" style="width: 150px;">
										<option value="1" <c:if test="${orderItem eq '1'}">selected</c:if>>대표상품코드</option>
										<option value="2" <c:if test="${orderItem eq '2'}">selected</c:if>>전상법코드</option>
										<option value="3" <c:if test="${orderItem eq '3'}">selected</c:if>>KC인증코드</option>
										<option value="PRD10" <c:if test="${orderItem eq '4'}">selected</c:if>>상품속성</option>
										<option value="PRD40" <c:if test="${orderItem eq '5'}">selected</c:if>>통화단위 표시단위</option>
										<option value="PRD42" <c:if test="${orderItem eq '6'}">selected</c:if>>상품사이즈단위</option>
										<option value="PRD03" <c:if test="${orderItem eq '7'}">selected</c:if>>계절구분 (계절)</option>
										<option value="PRD16" <c:if test="${orderItem eq '8'}">selected</c:if>>원산지</option>
										<option value="SM335" <c:if test="${orderItem eq '9'}">selected</c:if>>상품유형</option>
										<option value="PRD08" <c:if test="${orderItem eq '10'}">selected</c:if>>친환경인증분류명코드</option>
								</select> 
									</td>
									
									<th>코드명</th>
									<td>
										<input type="text" id="codeNm" name="codeNm" />
 									</td>
								</tr>
							</table>
						</div>
					</div>
					<br></br>
					<!-- 조회조건 // -->

					<!-- 조회결과 -->
					<div class="wrap_con">
						<!-- list -->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><spring:message code="text.common.title.searchResult" /></li>
								
							</ul>

							<tablecellpadding ="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><div id="ibsheet1"></div></td>
								<!-- IBSheet 위치 -->
							</tr>
							</table>
						</div>
					</div>
					<!-- 조회결과 //-->
					<!-- 페이징 DIV -->

				</div>
			</form>
		</div>

	</div>
	<!--	@ BODY WRAP  END  	// -->
</body>
</html>