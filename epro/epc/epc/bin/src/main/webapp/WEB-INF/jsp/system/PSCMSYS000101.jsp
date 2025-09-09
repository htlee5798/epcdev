<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="lcn.module.common.util.DateUtil"%>

<%
    String today = DateUtil.formatDate(DateUtil.getToday(), "-");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<c:set var="titleVal" value="배송비"/>
<c:if test="${deliDivnCd eq '20'}">
	<c:set var="titleVal" value="반품비"/>
</c:if>

<!-- WISE 그리드 초기화 -->
<script type="text/javascript" for="WG1" event="Initialize()">
    setWiseGridProperty(WG1);
    setHeader();
</script>

<!--    서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WG1" event="EndQuery()">
    hideLoadingMask();
    GridEndQuery();
</script>

<!-- 통신 시 에러 발생 -->
<script language="javascript" for="WG1" event="ErrorQuery(strSource, nCode, strMessage, strDescription)">
    hideLoadingMask();
</script>

<!--     WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WG1" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell(strColumnKey, nRow);
</script>

<script language=javascript for="WG1" event="ChangeCombo(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell(strColumnKey, nRow);
</script>

<script language=javascript for="WG1" event="CellClick(strColumnKey, nRow)">
    GirdCellClick(strColumnKey, nRow);
</script>

<!-- 그리드 기초함수 -->
<script type="text/javascript">
    /* 서버와의 통신이 정상적으로 완료되면 발생한다. */
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
            
            areaMergeCell();
        }

        if (mode == "insert" || mode == "update" || mode == "delete")
        {
            if (GridObj.GetStatus() == "true") // 서버에서 전송한 상태코드를 가져온다.
            {
                var saveData = GridObj.GetParam("saveData"); //서버에서 saveData 셋팅한 파라미터를 가져온다.
                alert(saveData);
                opener.doSearch();
                top.close();
            }
            else
            {
                var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
                alert(error_msg);
                doSearch();
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
        gridObj.AddHeader("NUM",                "순번",           "t_number",     50,     40,     false);
        gridObj.AddHeader("APPLY_START_DY",     "적용시작일자",   "t_text",       10,     107,    true );
        gridObj.AddHeader("APPLY_END_DY",       "적용종료일자",   "t_text",       10,     107,    true );
        gridObj.AddHeader("DELI_BASE_MIN_AMT",  "기준하한금액",   "t_number",     10,     108,    true );
        gridObj.AddHeader("DELI_BASE_MAX_AMT",  "기준상한금액",   "t_number",     10,     108,    true );
        gridObj.AddHeader("DELIVERY_AMT",       "${titleVal}",    "t_number",     50,     108,    false);
        //hidden 컬럼
        gridObj.AddHeader("checked",            "체크",           "t_checkbox",   10,     40,     false);
        gridObj.AddHeader("PROD_CD",            "PROD_CD",        "t_text",       50,     150,    false);

        gridObj.BoundHeader();

        //hidden 컬럼
        gridObj.SetColHide('checked', true);
        gridObj.SetColHide('PROD_CD', true);

        //정렬
        gridObj.SetColCellAlign('NUM',               'center');
        gridObj.SetColCellAlign('APPLY_START_DY',    'center');
        gridObj.SetColCellAlign('APPLY_END_DY',      'center');
        gridObj.SetColCellAlign('DELI_BASE_MIN_AMT', 'right' );
        gridObj.SetColCellAlign('DELI_BASE_MAX_AMT', 'right' );
        gridObj.SetColCellAlign('DELIVERY_AMT',      'right' );

        // 수정 가능 컬럼에 표시
        gridObj.SetColCellBgColor("APPLY_START_DY",    '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("DELI_BASE_MIN_AMT", '<spring:message code="config.common.grid.enableChangeColor"/>');
        gridObj.SetColCellBgColor("DELI_BASE_MAX_AMT", '<spring:message code="config.common.grid.enableChangeColor"/>');
        
        //컬럼의 정렬이 불가능하도록 설정
        gridObj.SetColCellSortEnable('NUM',               false);
        gridObj.SetColCellSortEnable('APPLY_START_DY',    false);
        gridObj.SetColCellSortEnable('APPLY_END_DY',      false);
        gridObj.SetColCellSortEnable('DELI_BASE_MIN_AMT', false);
        gridObj.SetColCellSortEnable('DELI_BASE_MAX_AMT', false);
        gridObj.SetColCellSortEnable('DELIVERY_AMT',      false);
        
        //
        gridObj.SetColCellCursor('APPLY_START_DY', 'hand');
        
        //group merge를 위한 필수 설정     
//      gridObj.bHDMoving = false;
//      gridObj.bHDSwapping = false; 
//      gridObj.bRowSelectorVisible = false; 
//      gridObj.strRowBorderStyle = 'none'; 
//      gridObj.nRowSpacing = 0 ;
//      gridObj.strHDClickAction = 'select'; 
        
        //area merge를 위한 필수 설정
        gridObj.bHDMoving         = false; 
        gridObj.bHDSwapping       = false;
        gridObj.strRowBorderStyle = 'none';
        gridObj.nRowSpacing       = 0;
        gridObj.nCellSpacing      = 0;
        gridObj.strHDClickAction  = 'select';
    }

    /**********************************************************
     * WiseGrid 통신 에러 발생 시
     ******************************************************** */
    function ErrorQuery(strSource, nCode, strMessage, strDescription)
    {
        alert(strSource);
    }

    /**********************************************************
     * WiseGrid의 셀의 내용이 변경되었을때 발생한다.
     * - 셀의 내용을 수정했을 때 체크박스에 체크되도록 한다.
     ******************************************************** */
    function GridChangeCell(strColumnKey, nRow)
    {
        var gridObj = document.WG1;

        var DELI_BASE_MIN_AMT = gridObj.GetCellValue("DELI_BASE_MIN_AMT", nRow);
        var DELI_BASE_MAX_AMT = gridObj.GetCellValue("DELI_BASE_MAX_AMT", nRow);

        var min = parseInt(DELI_BASE_MIN_AMT);
        var max = parseInt(DELI_BASE_MAX_AMT);
        
        //-----------------------
        // validation check
        //  - 기준 하한금액 < 기준 상한금액
        //-----------------------
        if ( !Common.isEmpty(DELI_BASE_MIN_AMT) && !Common.isEmpty(DELI_BASE_MAX_AMT))
        {
            if (min >= max )
            {
                if (strColumnKey=="DELI_BASE_MIN_AMT")
                {
                    alert("'기준하한금액'은  '기준상한금액'보다 작아야합니다");
                    gridObj.SetCellValue("DELI_BASE_MIN_AMT",nRow,"");
                    return false;
                }
                if (strColumnKey=="DELI_BASE_MAX_AMT")
                {
                    alert("'기준상한금액'은  '기준하한금액'보다 커야합니다");
                    gridObj.SetCellValue("DELI_BASE_MAX_AMT",nRow,"");
                    return false;
                }
            }
        }
        
        gridObj.SetCellBgColor(strColumnKey, nRow, '<spring:message code="config.common.grid.changedColor"/>');
    }
</script>

<script type="text/javascript">
    /** ********************************************************
     * jQeury 초기화
     ******************************************************** */
    $(document).ready(function(){
        doSearch();
        //--------------------------------   
        //input enter 막기
        //--------------------------------
        $("*").keypress(function(e){
            if(e.keyCode==13) return false;
        });
        $('#save').click(function() {
            doInsert();
        });
        $('#close').click(function() {
            top.close();
        });
    }); // end of ready
    
    /** ********************************************************
     * 팝업 로딩시 호출되는 조회함수
     ******************************************************** */
    function doSearch()
    {
        loadingMask();
        var gridObj = document.WG1;
        
        var url = '<c:url value="/system/selectDeliveryAmtList.do"/>';
        
        gridObj.setParam('let3Ref', $("#let3Ref").val());
        gridObj.setParam('mode', 'search');
        gridObj.DoQuery(url);
    
    }
    
    /** ********************************************************
     * 저장버튼 클릭시 발생 이벤트
     ******************************************************** */
    function doInsert()
    {
        if (!checkInput())
        {
            return;
        }
        
        if (confirm('<spring:message code="msg.common.confirm.save"/>'))
        {
            loadingMask();
            var gridObj = document.WG1;
            var url = '<c:url value="/system/insertDeliveryAmt.do"/>';
            //save 하기 전에 merge된 셀을 해제한다 
            gridObj.RemoveAreaMerge('mergeStartDy');
            gridObj.RemoveAreaMerge('mergeEndDy'  );
            //파라미터 세팅
            gridObj.setParam('vendorId',     $('#vendorId').val()                    );
            gridObj.setParam('deliDivnCd',   $('#deliDivnCd').val()                  );
            gridObj.setParam('applyStartDy', gridObj.GetCellValue('APPLY_START_DY',0));
            
            //mode 설정
            gridObj.SetParam("mode", "insert" );
            gridObj.DoQuery(url,     "checked");
        }
    }
    
    // 입력값체크
    function checkInput()
    {
        var gridObj = document.WG1;
        var rowCount = gridObj.GetRowCount();
        
        var applyStartDy = gridObj.GetCellValue('APPLY_START_DY', 0);
//      alert("applystartdy"+applyStartDy+"\nmaxApplyStartDy     "+dataForm.maxApplyStartDy.value);
        
        if (applyStartDy <= dataForm.today.value)
        {
            alert("익일자 부터 등록 가능합니다");
            return false;
        }
        
        // 배송비 이력을 조회해서 가장 마지막 적용시작일자(maxApplyStartDy)를 가져오는데, 이 값이 null 이면 validation 체크할 필요가 없음
        if (!Common.isEmpty(dataForm.maxApplyStartDy.value))
        {
            if (applyStartDy <= dataForm.maxApplyStartDy.value)
            {
                alert("마지막으로 등록한 시작일자 이 전으로는 등록할 수 없습니다");
                return false;
            }
        }
        
        //--------------------------------------
        // validation check
        // - 기준 하한금액과 기준상한금액 중 하나만 입려할 수 없습니다
        //--------------------------------------
        for (var nRow=0; nRow<rowCount; nRow++)
        {
            var DELI_BASE_MIN_AMT = gridObj.GetCellValue("DELI_BASE_MIN_AMT",nRow);
            var DELI_BASE_MAX_AMT = gridObj.GetCellValue("DELI_BASE_MAX_AMT",nRow);
            
            if (!Common.isEmpty(DELI_BASE_MIN_AMT) && Common.isEmpty(DELI_BASE_MAX_AMT))
            {
                alert("'기준하한금액'과 '기준상한금액' 중 하나만 입력할 수 없습니다");
                return false;
                
            }
            
            if (Common.isEmpty(DELI_BASE_MIN_AMT) && !Common.isEmpty(DELI_BASE_MAX_AMT))
            {
                alert("'기준하한금액'과 '기준상한금액' 중 하나만 입력할 수 없습니다");
                return false;
            }
        }
        
        //-----------------------------------------------------------------
        // | 순번 | 기준하한금액 | 기준상한금액 | 배송료 |
        //  ------ -------------- -------------- --------------------------
        // |   1  |    10000     |    19999 B   |  3000  |
        // |   2  |    20000 A   |    29999     |  2500  |
        //  ------ -------------- -------------- --------------------------
        // * 위와 같이 A는 B 보다 작아야 된다  
        //-----------------------------------------------------------------
        for (var nRow=0; nRow<rowCount; nRow++)
        {
            if (nRow > 0)
            {
                var DELI_BASE_MIN_AMT = gridObj.GetCellValue("DELI_BASE_MIN_AMT", nRow); // A 셀의 값
                var oneRowBeforeDELI_BASE_MAX_AMT="";   // B 셀의 값
                var i = nRow;
                
                for (; i>0; i--)
                {
                    var value = gridObj.GetCellValue("DELI_BASE_MAX_AMT", i-1);
                    
                    if (!Common.isEmpty(value))
                    {
                        oneRowBeforeDELI_BASE_MAX_AMT = value;    
                        break;
                    }
                }
                
                if (!Common.isEmpty(oneRowBeforeDELI_BASE_MAX_AMT))
                {
                    if (!Common.isEmpty(DELI_BASE_MIN_AMT) && !Common.isEmpty(oneRowBeforeDELI_BASE_MAX_AMT)) // A와 B는 null이 아니고 
                    {
                        var min = parseInt(DELI_BASE_MIN_AMT);
                        var oneRowBeforeMax = parseInt(oneRowBeforeDELI_BASE_MAX_AMT);

                        if (min <= oneRowBeforeMax)                                                           // A가 B보다 작거나 같으면 
                        {
                            alert("'기준하한금액'은 이전 로우의 '기준상한금액'보다 커야합니다");              // alert
                            return false;
                        }
                    }
                }
            }
        }
        
        //--------------------------------------
        // 사용자가 입력한 내용이 있는지 확인하고 해당 로우에 check
        //--------------------------------------
        var checkedRowCount =0;
        
        for (var nRow=0; nRow<rowCount; nRow++)
        {
            var DELI_BASE_MIN_AMT = gridObj.GetCellValue("DELI_BASE_MIN_AMT",nRow);
            
            if (!Common.isEmpty(DELI_BASE_MIN_AMT))
            {
                gridObj.SetCellValue("checked",nRow,"1");
                checkedRowCount++;
            }
        }
        //--------------------------------------
        // validation check
        // - null check
        //--------------------------------------
        if (checkedRowCount == 0)
        {
            alert("'기준하한금액과'과 '기준상한금액을' 입력하세요");
            return false;
        }
        
        return true;
    }
    
    /** ********************************************************
    * cell 클릭 시 처리 함수
    ********************************************************* */
    var nRowForCellClick;
    function GirdCellClick(strColumnKey, nRow){
        if("APPLY_START_DY"==strColumnKey){
            openCalWithCallback('dataForm.date','callBackByOpenCal');
        }
    }
    function callBackByOpenCal(){
        var gridObj = document.WG1;
        var rowCount = gridObj.GetRowCount();
        var i;
        for(i=0;i<rowCount;i++){
            gridObj.SetCellValue('APPLY_START_DY',i,dataForm.date.value);
        }
        gridObj.SetAreaMergeColor('mergeStartDy', '0|0|0', '<spring:message code="config.common.grid.changedColor"/>');
    }
    /** ********************************************************
    * AreaMergeCell
    ********************************************************* */
    function areaMergeCell(){
        var gridObj = document.WG1;
        var rowCount  = gridObj.GetRowCount();
        if(rowCount > 0){
            //------------------------
            // 적용시작일자, 적용종료일자 merge
            //------------------------
            gridObj.AddAreaMerge('mergeStartDy', 'APPLY_START_DY', 0,'APPLY_START_DY', rowCount-1, 'APPLY_START_DY', 0);
            gridObj.AddAreaMerge('mergeEndDy',   'APPLY_END_DY',   0,'APPLY_END_DY',   rowCount-1, 'APPLY_END_DY',   0);
            
        }
        
        

    }

    /** ********************************************************
    * GroupMergeCell
    ********************************************************* */
    function groupMergeCell(){
        var gridObj = document.WG1;
        
        gridObj.SetGroupMerge('APPLY_START_DY,APPLY_END_DY');
        
    }
</script>
</head>

<body>
<form name="dataForm" id="dataForm">

    <!-- hidden value -->
    <input type="hidden" name="vendorId"        id="vendorId"        value="${vendorId}"       />
    <input type="hidden" name="today"           id="today"           value="<%=today%>"        />
    <input type="hidden" name="date"            id="date"            value="<%=today%>"        />
    <input type="hidden" name="maxApplyStartDy" id="maxApplyStartDy" value="${maxApplyStartDy}"/>
    <input type="hidden" name="deliDivnCd"      id="deliDivnCd"      value="${deliDivnCd}"       />
    <input type="hidden" name="let3Ref"         id="let3Ref"         value="${let3Ref}"       />
<div id="popup" style="width:618px;">

    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>${titleVal} 적용예정일 등록</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->

    <!------------------------------------------------------------------ -->
    <!--    process -->
    <!------------------------------------------------------------------ -->
    <div id="process1">
        <ul>
            <li>홈</li>
            <li>시스템관리</li>
            <li class="last">${titleVal}정책관리</li>
        </ul>
    </div>
    <!------------------------------------------------ end of process -- -->


<div class="popup_contents">
    <!------------------------------------------------------------------ -->
    <!--    검색조건 -->
    <!------------------------------------------------------------------ -->
    <div class="bbs_search">
        <ul class="tit">
            <li class="btn">
                <a href="#" class="btn" id="save" ><span><spring:message code="button.common.save" /></span></a>
                <a href="#" class="btn" id="close"><span><spring:message code="button.common.close"/></span></a>
            </li>
        </ul>
        <li>${titleVal} 적용예정일 등록은 익일자 부터 가능합니다</li>
    </div>
    <!----------------------------------------------------- end of 검색조건 -->

    <!------------------------------------------------------------------ -->
    <!--    검색내역 -->
    <!------------------------------------------------------------------ -->
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <table cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                    <td><script type="text/javascript">initWiseGrid("WG1", "100%", "150");</script></td>
                </tr>
            </table>
        </div>
    </div>
    <!------------------------------------------------------- end of 검색내역 -->

    <!------------------------------------------------------------------ -->
    <!--    페이징 -->
    <!------------------------------------------------------------------ -->
    <div id="pagingDiv" class="pagingbox2" style="width:98%;">
        <script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
    </div>
    <!------------------------------------------------------- end of 페이징 -->

</div><!-- class popup_contents// -->
</div><!-- id popup// -->

</form>

</body>
</html>