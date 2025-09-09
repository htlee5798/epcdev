<%--
- Author(s): bdhan
- Created Date: 2013. 05. 20
- Version : 1.0
- Description : 단품정보 화면
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%
    String tabNo    = "1";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />
<!-- 그리드 헤더처리 -->
<script type="text/javascript">
	$(document).ready(function(){
	    //input enter 막기
	    $("*").keypress(function(e){
	        if(e.keyCode==13) return false;
	    });
	    setHeader();
	    //목록 자동 로딩
	    goPage();
	});

    function setHeader() {
    	// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "200px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);
		
		var ibdata = {};
		// SizeMode: 

		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};

		ibdata.Cols = [
			{Header:"",				Type:"CheckBox",	SaveName:"SELECTED",	Align:"Center",		Width:25,	Sort:false }
		  , {Header:"단품코드|단품코드",	Type:"Text",		SaveName:"itemCd",		Align:"Center",		Width:90,	Edit:0 }
		  , {Header:"(구)옵션설명 (※롯데ON 미연동)|(구)옵션설명 (※롯데ON 미연동)", Type:"Text", SaveName:"optnDesc", Align:"Center", Width:190, Edit:1 }
		  , {Header:"EC 속성|속성유형명",Type:"Text",		SaveName:"attrNm",		Align:"Center",		Width:290,	Edit:0 }
		  , {Header:"EC 속성|속성값",	Type:"Text",		SaveName:"attrValNm",	Align:"Center",		Width:290,	Edit:0 }
		  , {Header:"재고수량|재고수량",	Type:"Int",			SaveName:"rservStkQty",	Align:"Right",		Width:90,	Edit:1 }
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
    }

    function goPage() {
        var url = '<c:url value="/product/selectProductItemOnline.do"/>';

        loadIBSheetData(mySheet, url, null, '#dataForm', null);
    }

    /** ********************************************************
     * 조회 처리 함수
     ******************************************************** */
    function doSearch() {
        goPage();
    }

    function insertRow() {
    	mySheet.DataInsert(mySheet.RowCount()+2);
	}

	function insert() {
		if (!checkRows()) {
			return;
		}

		var url = '<c:url value="/product/saveProductItemOnline.do"/>';
		var param = new Object();
		param.prodCd = $("#dataForm input[name=prodCd]").val();
		param.vendorId = $("#vendorId").val();
		mySheet.DoSave(url, {Param:$.param(param), Col:0, Sync:2});
	}
	
	/**********************************************************
	   * 선택 로우 체크
	*********************************************************/
	function checkRows() {
		var rtnVal = true;
		var chkRow = false;
		
		for(var i=1; i<mySheet.RowCount()+2; i++){
			if(mySheet.GetCellValue(i,"SELECTED") == 1){
				chkRow = true;
				
				if($.trim(mySheet.GetCellValue(i,"optnDesc")) == "" || $.trim(mySheet.GetCellValue(i,"rservStkQty")) == ""){
					alert("필수 데이터를 입력해주세요.");
					rtnVal = false;
				}
				
				if(mySheet.GetCellValue(i,"optnDesc").indexOf(';') > -1){
					alert(" ';' 을 사용할수 없습니다.");
					rtnVal = false;
				}
			}
		}

		if(!chkRow){
			alert("선택된 로우가 없습니다.");
			rtnVal = false;
		}

		return rtnVal;
	}

	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		
	}

	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
    	alert(Msg);
    	goPage();
    }
</script>
</head>
<body>
<form name="dataForm" id="dataForm">
<input type="hidden" id="strCd" name="strCd">
<input type="hidden" id="prodCd" name="prodCd" 	value="<c:out escapeXml='false' value='${param.prodCd}' />"/>
<div id="content_wrap" style="width:990px; height:200px;">
    <div id="wrap_menu" style="width:990px;">
    <!--    @ 검색조건  -->
<c:set var="prodCd" value="${param.prodCd}"/>
<%
	String prodCd = (String)pageContext.getAttribute("prodCd");
	String cleared = SecureUtil.stripXSS(prodCd);
%>
<!-- 상품 상세보기 하단 탭 -->
<c:set var="tabNo" value="<%=tabNo%>" />
<c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
	<c:param name="tabNo" value="${tabNo}" />
	<c:param name="prodCd" value="<%=cleared%>" />
</c:import>
        <div class="wrap_con">
            <!-- list -->
            <div class="bbs_list">
                <ul class="tit">
                    <li class="tit">단품정보<font color="red"><strong>(단품별 재고관리 시, 필히 [가격정보]탭에서 재고관리 '체크'확인)</strong></font>
                    </li>
                    <li class="btn">
                    <c:if test="${visible == 'Y' }">
                        <a href="javascript:insertRow();" class="btn" ><span>추가</span></a>
                    </c:if>
						<a href="javascript:insert();" class="btn" ><span>저장</span></a>
                    </li>
                </ul>
                <table cellpadding="0" cellspacing="0" border="0" width="100%" heigth="200px">
                    <tr>
                        <td><div id="ibsheet1"></div></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</form>
<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<c:out escapeXml='false' value='${param.prodCd}'/>" />
<input type="hidden" id="vendorId" name="vendorId" value="<c:out escapeXml='false' value='${param.vendorId}'/>" />
</form>
</body>
</html>