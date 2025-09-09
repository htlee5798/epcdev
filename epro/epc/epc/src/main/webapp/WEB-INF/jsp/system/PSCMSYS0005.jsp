<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- PSCMSYS0005-->

<!-- 공통코드 세팅 -->
<c:set var="sm341Cd" />
	<c:set var="sm341Nm" />
	<c:forEach items="${infoGrpList}" var="sm341Code" varStatus="idx2">
		<c:choose>
			<c:when test="${ fn:length(infoGrpList) eq idx2.index+1  }">
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.INFO_GRP_CD }"/>
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.INFO_GRP_NM }"/>
			</c:when>
			<c:otherwise>
				<c:set var="sm341Cd" value="${ sm341Cd }${ sm341Code.INFO_GRP_CD }|"/>
				<c:set var="sm341Nm" value="${ sm341Nm }${ sm341Code.INFO_GRP_NM  }|"/>
			</c:otherwise>
		</c:choose>
	</c:forEach>

<script type="text/javascript">
	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready( function() {

				// START of IBSheet Setting
				createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "400px");
				mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
				mySheet.SetDataAutoTrim(true);

				var ibdata = {};
				// SizeMode: 
				ibdata.Cfg = { 	SizeMode : sizeAuto, SearchMode : smGeneral }; // 10 row씩 Load
				// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
				ibdata.HeaderMode = { Sort : 1, ColMove : 0, ColResize : 1, HeaderCheck : 1 	};

				// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
				ibdata.Cols = [ 
				  { Header : "", 	Type : "CheckBox", SaveName : "chk", Align : "Center", Width : 50, Edit : 1 }
				, { Header : "순번", Type : "Int", 	SaveName : "num", Align : "Center", 	Width : 50, Edit : 0 }
				, { Header : "제목", Type : "Text", 	SaveName : "title", Align : "left", Width : 200, 	Edit : 0, FontColor : "#0000FF" }
				, { Header : "상품유형", Type : "Combo", SaveName : "infoGrpCd", Align : "Center", Width : 100, Edit : 0, ComboText:"<c:out value="${ sm341Nm }"/>",ComboCode:"<c:out value="${ sm341Cd }"/>" }
				, { Header : "사용여부", Type : "Text", SaveName : "useYn", Align : "Center", 	Width : 100, Edit : 0, }
				, { Header : "등록자", 	Type : "Text", SaveName : "regId", 	Align : "Center", Width : 100, Edit : 0 	}
				, { Header : "등록일", Type : "Text", SaveName : "regDate", Align : "Center", Width : 100, Edit : 0, Format : "Ymd" }
				, { Header : "수정자", Type : "Text", SaveName : "modId", Align : "Center", 	Width : 100, Edit : 0 	}
				, { Header : "수정일", Type : "Text", 	SaveName : "modDate", 	Align : "Center", 	Width : 100, 	Edit : 0, 	Format : "Ymd" 	}
				, { Header : "확인", Type : "Status", SaveName : "S_STATUS", Align : "Center", Width : 100, Edit : 0, Hidden : true }
				, { Header : "SEQ", Type : "Text", SaveName : "norProdSeq", Align : "Center", 	Width : 100, Edit : 0, Hidden : true }
				];
				IBS_InitSheet(mySheet, ibdata);

				mySheet.SetEllipsis(1);	//말줄임
				mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
				mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

			}); // end of ready
			
	/** ********************************************************
	*  전상법 템플릿 등록
	******************************************************** */
		 function doCreate(){
			var targetUrl = '<c:url value="/system/insertFormEcsTem.do"/>' ;	
				Common.centerPopupWindow(targetUrl, 'product', {width : 600, height : 400,scrollBars : 'YES'});
				}

	/** ********************************************************
	 * 결과화면 창
	 ******************************************************** */
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
	}

	/**********************************************************
	 *  삭제 처리 함수
	 ******************************************************** */
	function doDelete() {
		if (mySheet.CheckedRows("chk") == 0) {
			alert("선택된 데이터가 없습니다.");
			return;
		}
			if (confirm("선택항목을 삭제하시겠습니까?")) {

				var url = '<c:url value="/system/deleteEscTem.do"/>';
				mySheet.DoSave(url, { Quest : 0 });
			} else {
				return;
			}
			doSearch();
		}

	

	/**********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(currentPage) {
		var url = '<c:url value="/system/searchEscTem.do"/>';
		loadIBSheetData(mySheet, url, currentPage, '#adminForm', null);
	}

	/** ********************************************************
	 * 팝업창
	 ******************************************************** */
	function mySheet_OnDblClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		if (Row < 1)
			return;

		var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
		if (colNm == 'title') { // 상품 Q&A 상세보기
			var recommSeq = mySheet.GetCellValue(Row, "norProdSeq");
			popupDetailView(recommSeq);
		}
	}

	/** ********************************************************
	 * 상세 팝업창 
	 ******************************************************** */
	function popupDetailView(recommSeq) {
		var targetUrl = '<c:url value="/system/seletFormEcsTemDtl.do"/>' + '?norProdSeq=' + recommSeq;
		Common.centerPopupWindow(targetUrl, 'product', { width : 600, height : 400, scrollBars : 'YES' });
	}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

	<div id="content_wrap">

		<!-- div class="content_scroll"-->
		<div>
			<form name="adminForm" id="adminForm">
				<div id="wrap_menu">
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">조회조건</li>
								<li class="btn">
									<a href="javascript:doSearch();" class="btn" id="search"> <span><spring:message code="button.common.inquire" /></span></a>
									<a href="javascript:doCreate();" class="btn" id="create"><span><spring:message code="button.common.create"/></span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0"
								border="0">
								<colgroup>
									<col style="width: 15%" />
									<col style="width: 17%" />
									<col style="width: 15%" />
									<col style="width: 17%" />
									<col style="width: 15%" />
									<col style="width: 17%" />
								</colgroup>

								<tr>
									<th><span class="star">*</span>상품분류</th>
									<td><select name="infoGrpCd" id="infoGrpCd" class="select"  style="width: 70%;">
											<option value="">전체</option>
											<c:forEach items="${infoGrpList}" var="code" begin="0">
												<option value="${code.INFO_GRP_CD}">
													${code.INFO_GRP_NM}</option>
											</c:forEach>
									</select></td>


									<th>사용여부</th>
									<td><select name="useYn" id="useYn" class="select" style="width: 70%;">
											<option value=""> 전체</option>
											<option value="Y">사용</option>
											<option value="N">미사용</option>
									</select></td>

									<th>협력사</th>
									<td><select name="searchVendorId" id="searchVendorId" class="select" style="width: 70%;">
											<option value="">전체</option>
											<c:forEach items="${epcLoginVO.vendorId}" var="venArr"
												begin="0">

												<c:if test="${not empty searchVO.searchVendorId}">
													<option value="${venArr}"
														<c:if test="${venArr eq searchVO.searchVendorId}">selected</c:if>>${venArr}</option>
												</c:if>
												<c:if test="${empty searchVO.searchVendorId}">
													<option value="${venArr}"
														<c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
												</c:if>
											</c:forEach>
									</select></td>
								</tr>
							</table>

						</div>
						<!-- 1검색조건 // -->
					</div>

					<div class="wrap_con">
						<!-- list -->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">조회결과</li>
								<li class="btn"><a href="javascript:doDelete();" class="btn" id="delete"><span><spring:message code="button.common.delete"/></span></a></li>
							</ul>

							<table class="bbs_list" cellpadding="0" cellspacing="0"
								border="0">
								<tr>
									<td><div id="ibsheet1"></div></td>
								</tr>
							</table>
						</div>

					</div>
					<!-- 페이징 DIV -->
					<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
						<script>
							setLMPaging("0", "0", "0", "goPage", "pagingDiv");
						</script>
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
						<li>시스템관리</li>
						<li class="last">전상법 템플릿 관리</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

	<!--	@ BODY WRAP  END  	// -->
</body>
</html>