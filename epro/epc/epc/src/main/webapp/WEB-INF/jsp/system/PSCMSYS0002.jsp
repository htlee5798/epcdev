<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/common/scm/scmCommon.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- system/PSCMSYS0002 -->
<!-- WISE 그리드 초기화 -->
<script type="text/javascript" for="WG1" event="Initialize()">
	setWiseGridProperty(WG1);
	setHeader();
</script>

<!-- 서버와의 통신이 정상적으로 완료되면 발생한다. -->
<script language=javascript for="WG1" event="EndQuery()">
	hideLoadingMask();
	GridEndQuery();
</script>

<!-- 통신 시 에러 발생 -->
<script language="javascript" for="WG1" event="ErrorQuery(strSource, nCode, strMessage, strDescription)">
    hideLoadingMask();
</script>

<!-- 셀 클릭 시 이벤트 -->
<script language=javascript for="WG1" event="CellClick(strColumnKey, nRow)">
    var gridObj = document.WG1;
    if ("addr1Nm"==strColumnKey){
    	openZipCodeNew(strColumnKey, nRow);
    }
</script>

<!--	 WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WG1" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell(strColumnKey, nRow, nOldValue, nNewValue);
</script>

<!--     WiseGrid의 t_combo타입의 컬럼내용이 변경되었을때 발생합니다  -->
<script language=javascript for="WG1" event="ChangeCombo(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell(strColumnKey, nRow);
</script>

<!-- 그리드 기초함수 -->
<script type="text/javascript">
    /**********************************************************
     * 서버와의 통신이 정상적으로 완료되면 발생한다
     ******************************************************** */
    function GridEndQuery() 
    {
        var GridObj = document.WG1;

        //서버에서 mode로 셋팅한 파라미터를 가져온다.
        var mode = GridObj.GetParam("mode");

        if (mode == "search") 
        {
            if (GridObj.GetStatus() != "true") // 서버에서 전송한 상태코드를 가져온다. 
            {
                var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.

                if (error_msg != '') // error 
                { 
                    $('#resultMsg').text('<spring:message code="msg.common.fail.request"/>');
                } 
                else // 0건 
                {
                    $('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
                }

            } 
            else 
            {
                $('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
            }
        }

        if (mode == "insert" || mode == "update" || mode == "delete") 
        {
            if (GridObj.GetStatus() == "true") // 서버에서 전송한 상태코드를 가져온다. 
            {
                var saveData = GridObj.GetParam("saveData"); //서버에서 saveData 셋팅한 파라미터를 가져온다.
                alert(saveData);
                doSearch();
            } 
            else 
            {
                var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
                alert(error_msg);
            }
        }
    }
	
    /**********************************************************
     * 그리드 헤더 처리
     ******************************************************** */
    function setHeader() 
    {
        var gridObj = document.WG1;
             
        //조회결과
        gridObj.AddHeader("checked",            "전체",           "t_checkbox",    2,     53,    true );    
        gridObj.AddHeader("vendorId",           "협력사",         "t_text",        6,     60,    false);
        gridObj.AddHeader("vendorUserGubun",    "구분",           "t_text",       15,    120,    false);    
        gridObj.AddHeader("utakNm",             "*성명",          "t_text",       30,     90,    true );    
        gridObj.AddHeader("utakDeptNm",         "부서",           "t_text",       30,     80,    true );    
        gridObj.AddHeader("utakPositionNm",     "직위",           "t_text",       30,     70,    true );    
        gridObj.AddHeader("utakTelNo",          "*전화번호",      "t_text",       12,     80,    true );    
        gridObj.AddHeader("utakCellNo",         "*휴대폰번호",    "t_text",       12,     80,    true );
        gridObj.AddHeader("utakFaxNo",          "팩스번호",       "t_text",       12,     80,    true );
        gridObj.AddHeader("addr1Nm",          	"*(반품지)주소1", "t_text",       100,    200,    false );
        gridObj.AddHeader("addr2Nm",         	"*(반품지)주소2", "t_text",       100,    200,    true );
        gridObj.AddHeader("valiYN",             "*유효여부",      "t_combo",       2,     75,    true );
        // hidden --------------------------------------------------------------------------------
        // merge조건으로만 쓰임
        gridObj.AddHeader("vendorSeq",          "협력업체Seq",    "t_text",       50,     80,    false);
        
        gridObj.BoundHeader();
        
        //체크박스 전체선택 true
        gridObj.SetColHDCheckBoxVisible("checked", true);
        
        //콤보박스
        gridObj.AddComboListValue("valiYN", "Y", "Y");
        gridObj.AddComboListValue("valiYN", "N", "N");
        
        // 콤보박스 선택 화살표 항시 표시
        gridObj.SetColButtonDisplayStyle('valiYN','always');
        
        //hidden 컬럼
        gridObj.SetColHide('vendorSeq', true);

        //정렬
        gridObj.SetColCellAlign('checked',          'center');
        gridObj.SetColCellAlign('vendorId',         'center');
        gridObj.SetColCellAlign('vendorUserGubun',  'center');
        gridObj.SetColCellAlign('utakNm',           'center');
        gridObj.SetColCellAlign('utakDeptNm',       'center');
        gridObj.SetColCellAlign('utakPositionNm',   'center');
        gridObj.SetColCellAlign('utakTelNo',        'center');
        gridObj.SetColCellAlign('utakCellNo',       'center');
        gridObj.SetColCellAlign('utakFaxNo',        'center');
        gridObj.SetColCellAlign('addr1Nm',            'left');
        gridObj.SetColCellAlign('addr2Nm',            'left');
        gridObj.SetColCellAlign('valiYN',           'center');
        
        // 수정 가능 컬럼에 표시
        gridObj.SetColCellBgColor("utakNm",         '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("utakDeptNm",     '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("utakPositionNm", '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("utakTelNo",      '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("utakCellNo",     '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("utakFaxNo",      '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("addr1Nm",        '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("addr2Nm",        '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("valiYN",         '<spring:message code="config.common.grid.enableChangeColor"/>');
    }
    
    /**********************************************************
     * 셀의 내용이 변경되었을 때 발생한다
     *  - 셀의 내용을 수정했을 때 체크박스에 체크되도록 한다.
     ******************************************************** */
    function GridChangeCell(strColumnKey, nRow)
    {
        var gridObj = document.WG1;
        //----------------------------
        // 숫자만 입력 validation
        //----------------------------
        if(!isNumber(gridObj.GetCellValue(strColumnKey,nRow))){
            if(Common.isEmpty(gridObj.GetCellValue(strColumnKey,nRow)))
                return;
            if(strColumnKey =='utakTelNo'){
                alert('<spring:message code="msg.common.error.notNum" arguments="전화번호"/>');
                gridObj.SetCellValue("utakTelNo",nRow,"");
                return;
            }
            if(strColumnKey =='utakCellNo'){
                alert('<spring:message code="msg.common.error.notNum" arguments="휴대폰번호"/>');
                gridObj.SetCellValue("utakCellNo",nRow,"");
                return;
            }
            if(strColumnKey =='utakFaxNo'){
                alert('<spring:message code="msg.common.error.notNum" arguments="팩스번호"/>');
                gridObj.SetCellValue("utakFaxNo",nRow,"");
                return;
            }
        }
        //----------------------------
        // cell의 값이 변경되었을 때 
        // 1. 해당 로우의 check 박스를 체크 
        // 2. cell 배경색상변경
        //----------------------------
        if(strColumnKey != "checked") {
            gridObj.SetCellValue("checked", nRow, "1");
            gridObj.SetCellBgColor(strColumnKey, nRow, '<spring:message code="config.common.grid.changedColor"/>');
        }       
    }

    /**********************************************************
     * 페이지 이동 시
     ******************************************************** */
    function goPage(currentPage) 
    {              
	    var gridObj = document.WG1;
	    var url = '<c:url value="/system/selectPersonInChargeList.do"/>';
	    
	    <c:choose>
			<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
			if ($("#vendorId").val() == "")
		    {
		        alert('업체선택은 필수입니다.');
		        $("#vendorId").focus();
		        return;
		    }
			</c:when>
		</c:choose>
		
        //페이징
        gridObj.setParam('currentPage', currentPage);
        gridObj.setParam('rowsPerPage', $("#rowsPerPage").val());
        //파라미터
        gridObj.setParam('vendorId', $("#vendorId").val());
	    gridObj.setParam('mode', 'search');
	    
	    loadingMask();
	    
	    gridObj.DoQuery(url);
    }
     
    /**********************************************************
     * WiseGrid 통신 에러 발생 시
     ******************************************************** */
    function ErrorQuery(strSource, nCode, strMessage, strDescription) 
    {
        alert(strSource);
    }
</script>

<script language="javascript" type="text/javascript">
    /** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){
		//--------------------------------	 
		//input enter 막기
		//--------------------------------
		$("*").keypress(function(e){
	        if(e.keyCode==13) return false;
		});	
        $('#search').click(function() {
        	doSearch();
        });
        $('#update').click(function() {
        	doUpdate();
        });
	});
    
	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() 
	{
		goPage('1');
	}
	
	/** ********************************************************
	 * 저장버튼 클릭시 발생 이벤트
	 ******************************************************** */
	function doUpdate() 
	{
	    if (!checkInput()) 
	    {
	        return;
	    }
	    
	    if (confirm('<spring:message code="msg.common.confirm.update"/>'))
	    {
	        var gridObj = document.WG1;
	        var sUrl = '<c:url value="/system/updatePersonInCharge.do"/>';
	        
	        gridObj.SetParam("mode", "update");
	        gridObj.DoQuery(sUrl, "checked");
	        loadingMask();
	    }
	}

	/** ********************************************************
	 * 입력값체크
	 ******************************************************** */
	function checkInput() 
	{
	    var gridObj = document.WG1;
	    var rowCount = gridObj.GetRowCount();
	    var checkedCount = 0;
	    var i;
	    for (i = 0; i < rowCount; i++)
	    {
	        if (gridObj.GetCellValue('checked',i)==1)
	        {
	            checkedCount++;
	            //---------------------------
	            // 필수값 validation
	            //---------------------------
	            if (Common.isEmpty(gridObj.GetCellValue('utakNm',i)))
	            {
	                alert('<spring:message code="msg.common.error.required" arguments="성명"/>');
	                return;
	            }

	            if (Common.isEmpty(gridObj.GetCellValue('utakTelNo',i)))
	            {
	                alert('<spring:message code="msg.common.error.required" arguments="전화번호"/>');
	                return;
	            }
	              if (Common.isEmpty(gridObj.GetCellValue('utakCellNo',i)))
	            {
	                alert('<spring:message code="msg.common.error.required" arguments="휴대폰번호"/>');
	                return;
	            }
	            	            
	            if (Common.isEmpty(gridObj.GetCellValue('valiYN',i)))
	            {
	                alert('<spring:message code="msg.common.error.required" arguments="유효여부"/>');
	                return;
	            }
	            if (gridObj.GetCellValue('vendorSeq',i) =='005'){
		            if (Common.isEmpty(gridObj.GetCellValue('addr1Nm',i)))
		            {
		                alert('<spring:message code="msg.common.error.required" arguments="(반품지)주소1"/>');
		                return;
		            }
		            if (Common.isEmpty(gridObj.GetCellValue('addr2Nm',i)))
		            {
		                alert('<spring:message code="msg.common.error.required" arguments="(반품지)주소2"/>');
		                return;
		            }
	            }
	        }
	    }
	    
	    //---------------------------
	    // 선택된 로우 validation
	    //---------------------------
	    if (checkedCount == 0)
	    {
	        alert("선택된 로우가 없습니다");
	        return;
	    }
	    
	    return true;
	}
	
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#vendorId").val(strVendorId);
	}
	
	var selColNm = "";
	var selRow = "";
	
	//주소 조회 함수
	function openZipCodeNew(colNm,row){
		selColNm = colNm;
		selRow = row;   
		
		var url = '<c:url value="/system/zip/PEDPZIP0003List.do"/>';
		
		Common.centerPopupWindow(url, 'zipSearchPopUp', {width : 500, height : 480});
	}
	
	function setAddr(postNo1,postNo2,addr){
		var gridObj = document.WG1;
	
		gridObj.SetCellValue(selColNm, selRow, addr);
	}
</script>
</head>

<body>
<form name="dataForm" id="dataForm" method="post">

<div id="content_wrap">
<div class="content_scroll">

<div id="wrap_menu">

	<!-- -------------------------------------------------------- -->
	<!-- 검색조건 -->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_search">	
		<div class="bbs_search">
			
			<!------------------------------------------------------------------ -->
			<!-- 	title -->
			<!------------------------------------------------------------------ -->
			<ul class="tit">
				<li class="tit">[담당자관리]</li>
				<li class="btn">
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
					<a href="#" class="btn" id="update"><span><spring:message code="button.common.update" /></span></a>
				</li>
			</ul>
			<!-------------------------------------------------- end of title -- -->
			
		</div><!-- id bbs_search// -->	
	</div><!-- id wrap_search// -->
	<!-----------------------------------------------end of 검색조건 -->
	
	
	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">협력업체코드</li>
                <li class="tit">
                    <c:choose>
						<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
							<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
							<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
						</c:when>
						<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
							<select name="vendorId" id="vendorId" class="select">
							<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
		                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
							</c:forEach>
							</select>
						</c:when>
					</c:choose>
                </li>
			</ul>
			
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td><script type="text/javascript">initWiseGrid("WG1", "100%", "420");</script></td>
				</tr>
			</table>
		</div>
	</div>
	<!-----------------------------------------------end of 검색내역  -->
	
    <!------------------------------------------------------------------ -->
    <!--    페이징 -->
    <!------------------------------------------------------------------ -->
    <div id="pagingDiv" class="pagingbox1" style="width: 100%;">
    </div>
    <!------------------------------------------------------- end of 페이징 -->
 	
</div><!-- id wrap_menu// -->
</div><!-- id content_scroll// -->

<!-- -------------------------------------------------------- -->
<!--	footer 	-->
<!-- -------------------------------------------------------- -->
<div id="footer">
	<div id="footbox">
		<div class="msg" id="resultMsg"></div>
		<div class="location">
			<ul>
				<li>홈</li>
				<li>시스템관리</li>
				<li class="last">담당자관리</li>
			</ul>
		</div>
	</div>
</div>
<!---------------------------------------------end of footer -->
</div><!-- id content_wrap// -->

</form>
</body>

</html>