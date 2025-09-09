<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="lcn.module.common.util.DateUtil"%>


<% 
    String today = DateUtil.formatDate(DateUtil.getToday(),"-");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<!-- system/PSCMSYS0001 -->
<!-- WISE 그리드 초기화 -->
<script type="text/javascript" for="WG1" event="Initialize()">
	setWiseGridProperty(WG1);
	setHeader();
</script>

<!-- 셀 클릭 시 이벤트 -->
<script language=javascript for="WG1" event="CellClick(strColumnKey, nRow)">
    var gridObj = document.WG1;
    if ("DO_MODIFY"==strColumnKey && !Common.isEmpty(gridObj.GetCellValue('DO_MODIFY',nRow)))
        doUpdate(strColumnKey, nRow);
    
    if ("DO_DELETE"==strColumnKey && !Common.isEmpty(gridObj.GetCellValue('DO_DELETE',nRow)))
        doDelete(strColumnKey, nRow);
</script>

<!-- 서버와의 통신이 정상적으로 완료되면 발생한다. -->
<script language=javascript for="WG1" event="EndQuery()">
	hideLoadingMask();
	GridEndQuery();
	groupMergeCell();
    areaMergeCell();
	
	/** ****************************************************
	 * Paging 처리
	 **************************************************** */
	var totalCnt = "0";
	var rowsPerPage = "0";
	var curPage = "0";
	try 
	{
		totalCnt = WG1.getParam("totalCount");
		rowsPerPage = WG1.getParam("rowsPerPage");
		curPage = WG1.getParam("currentPage");
	} 
	catch (e) { }

	setLMPaging(totalCnt, rowsPerPage, curPage, 'goPage', 'pagingDiv');
</script>

<!-- 통신 시 에러 발생 -->
<script language="javascript" for="WG1" event="ErrorQuery(strSource, nCode, strMessage, strDescription)">
    hideLoadingMask();
</script>

<!-- 그리드 기초함수 -->
<script type="text/javascript">
    /**********************************************************
     * 서버와의 통신이 정상적으로 완료되면 발생한다.
     ******************************************************** */
	function GridEndQuery() 
	{
		var GridObj = document.WG1;

		//서버에서 mode로 셋팅한 파라미터를 가져온다.
		var mode = GridObj.GetParam("mode");

		if (mode == "search") 
		{
			if($("#deliDivnCd").val() == "10"){
				GridObj.SetColHide('DELI_DIVN_NM',true);
				GridObj.SetColHide('APPLY_END_DY',false);
				GridObj.SetColHide('DELI_BASE_MIN_AMT',false);
				GridObj.SetColHide('DELI_BASE_MAX_AMT',false);
			}else{
				GridObj.SetColHide('DELI_DIVN_NM',false);
				GridObj.SetColHide('APPLY_END_DY',true);
				GridObj.SetColHide('DELI_BASE_MIN_AMT',true);
				GridObj.SetColHide('DELI_BASE_MAX_AMT',true);
			}
			
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
        if (mode == "insert"||mode == "update"||mode == "delete")
        {
            if (GridObj.GetStatus() == "true") // 서버에서 전송한 상태코드를 가져온다.
            {
                var saveData = GridObj.GetParam("saveData"); //서버에서 saveData 셋팅한 파라미터를 가져온다.
                alert(saveData);
            }
            else
            {
                var error_msg = GridObj.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
                alert(error_msg);   
            }
            
            doSearch();
        }
	}
	
	/**********************************************************
	 * 그리드 헤더 처리
	 ******************************************************** */
	function setHeader() 
	{
		var gridObj = document.WG1;
		    
		//조회결과
		gridObj.AddHeader("DELI_DIVN_NM",       "구분",   		  "t_text",       15,    70,     false);
        gridObj.AddHeader("APPLY_START_DY",     "적용시작일자",   "t_date",       15,   132,     false);
        gridObj.AddHeader("APPLY_END_DY",       "적용종료일자",   "t_date",       15,   132,     false);
        gridObj.AddHeader("DELI_BASE_MIN_AMT",  "기준하한금액",   "t_number",     20,   122,     false);
        gridObj.AddHeader("DELI_BASE_MAX_AMT",  "기준상한금액",   "t_number",     20,   122,     false);
        gridObj.AddHeader("DELIVERY_AMT",       "배송비",         "t_number",     20,   122,     false);
        gridObj.AddHeader("DO_MODIFY",          "수정",           "t_text",       20,    70,     false);
        gridObj.AddHeader("DO_DELETE",          "삭제",           "t_text",       20,    70,     false);
        gridObj.AddHeader("DELI_DIVN_CD",       "구분코드",       "t_text",       20,    70,     false);

		gridObj.BoundHeader();
		
		//포맷설정
        gridObj.SetDateFormat('APPLY_START_DY',       'yyyy-MM-dd');
        gridObj.SetDateFormat('APPLY_END_DY',         'yyyy-MM-dd');
        
        gridObj.SetNumberFormat('DELI_BASE_MIN_AMT',  '#,###.#');
        gridObj.SetNumberFormat('DELI_BASE_MAX_AMT',  '#,###.#');
        gridObj.SetNumberFormat('DELIVERY_AMT',       '#,###.#');
		
		//정렬
		gridObj.SetColCellAlign('DELI_DIVN_NM',          'center');
		gridObj.SetColCellAlign('APPLY_START_DY',     'center');
		gridObj.SetColCellAlign('APPLY_END_DY',       'center');
		gridObj.SetColCellAlign('DELI_BASE_MIN_AMT',  'center');
		gridObj.SetColCellAlign('DELI_BASE_MAX_AMT',  'center');
		gridObj.SetColCellAlign('DELIVERY_AMT',       'center');
        gridObj.SetColCellAlign('DO_MODIFY',          'center');
        gridObj.SetColCellAlign('DO_DELETE',          'center');
        
        //링크 디자인 적용
        gridObj.SetColCellFgColor('DO_MODIFY', '0|0|255');
//      gridObj.SetColCellFontULine("DO_MODIFY", "true");
        gridObj.SetColCellCursor('DO_MODIFY', 'hand');
        
        gridObj.SetColCellFgColor('DO_DELETE', '0|0|255');
//      gridObj.SetColCellFontULine("DO_DELETE", "true");
        gridObj.SetColCellCursor('DO_DELETE', 'hand');
        
        gridObj.SetColHide('DELI_DIVN_NM',true);
        gridObj.SetColHide('DELI_DIVN_CD',true);
        
        //group merge를 위한 필수 설정     
        gridObj.bHDMoving = false;
        gridObj.bHDSwapping = false; 
        gridObj.bRowSelectorVisible = false; 
        gridObj.strRowBorderStyle = 'none'; 
        gridObj.nRowSpacing = 0 ;
        gridObj.strHDClickAction = 'select';
        
        if($("#deliDivnCd").val() == "10"){
			gridObj.SetColHide('DELI_DIVN_NM',true);
			gridObj.SetColHide('APPLY_END_DY',false);
			gridObj.SetColHide('DELI_BASE_MIN_AMT',false);
			gridObj.SetColHide('DELI_BASE_MAX_AMT',false);
			gridObj.SetColHDText("DELIVERY_AMT","배송비");
		}else{
			gridObj.SetColHide('DELI_DIVN_NM',false);
			gridObj.SetColHide('APPLY_END_DY',true);
			gridObj.SetColHide('DELI_BASE_MIN_AMT',true);
			gridObj.SetColHide('DELI_BASE_MAX_AMT',true);
			gridObj.SetColHDText("DELIVERY_AMT","금액");
		}
	}
		
	/**********************************************************
	 * 페이지 이동 시
	 ******************************************************** */
	function goPage(currentPage) 
	{
		var gridObj = document.WG1;
		var url = '<c:url value="/system/selectDeliveryChargePolicyList.do"/>';
		
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
		gridObj.setParam('currentPage', currentPage            );
		gridObj.setParam('rowsPerPage', $("#rowsPerPage").val());
		gridObj.setParam('vendorId',    $("#vendorId"   ).val());
		gridObj.setParam('deliDivnCd',  $("#deliDivnCd" ).val());
		gridObj.setParam('mode',        'search'               );
		
		
		loadingMask();
		
		gridObj.DoQuery(url);
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
	    if("true"==dataForm.isInsert.value){
	        doInsert();
	        dataForm.isInsert.value = "false";
	    }
	    //doSearch();
        $('#search').click(function() {
            doSearch();
        });
        $('#create').click(function() {
        	doInsert();
        });
       
	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() 
	{
		var gridObj = document.WG1;
    	var val = $("#deliDivnCd option:selected").val();
    	        	
    	if(val != "10"){
    		gridObj.SetColHDText("DELIVERY_AMT","금액");
    	}else{
    		gridObj.SetColHDText("DELIVERY_AMT","배송비");
    	}
    	
		goPage('1');
	}

	/** ********************************************************
	 * 등록버튼 클릭시 이벤트
	 ******************************************************** */
	function doInsert()
	{
	    if ("true" != dataForm.isInsert.value)
	    {
	    	//submit for validate
	        var url = '<c:url value="/system/getMaxApplyStartDyBeforeInsert.do"/>'+"?vendorId="+$('#vendorId').val()+"&deliDivnCd="+$("#deliDivnCd").val(); 
	        document.dataForm.action = url;
	        document.dataForm.submit();
	        return;
	    }
	    
	    if (!checkInput())
	        return;
	    
	    var let3Ref = "04";
	    
	    if($("#deliDivnCd").val() == "20"){
	    	let3Ref = "05";
	    }
	    
		var targetUrl = '<c:url value="/system/insertDeliveryChargePolicyPopUp.do"/>'+"?vendorId="+$('#vendorId').val()+"&deliDivnCd="+$("#deliDivnCd").val()+"&let3Ref="+let3Ref;
		Common.centerPopupWindow(targetUrl, 'deliveryChargePolicyPopUp', {width : 620, height : 345});
	}
    function checkInput()
    {
	    //alert("today="+dataForm.today.value+"\n maxApplyStartDy="+dataForm.maxApplyStartDy.value);
	    
	    if (dataForm.today.value < dataForm.maxApplyStartDy.value)
	    {
	    	alert("배송비 예약 설정은 1회만 가능합니다");
	        return false;
	    }
	    
	    return true;
	}
    
    /** ********************************************************
    * GroupMergeCell
    ********************************************************* */
    function groupMergeCell()
    {
        var gridObj = document.WG1;
        gridObj.SetGroupMerge('APPLY_START_DY,APPLY_END_DY');
        
    }
    
    /** ********************************************************
    * areaMergeCell
    ********************************************************* */
    function areaMergeCell()
    {
        var gridObj = document.WG1;
        var rowCount = gridObj.getRowCount();
        
        if ( rowCount > 0)
        {
            var i;
            var mergeId = 1;
            var start   = 0;
            var value   = gridObj.GetCellValue('APPLY_START_DY',0);
            
            for (i = 1; i < rowCount; i++)
            {
                if (value != gridObj.GetCellValue('APPLY_START_DY', i))
                {
                    gridObj.AddAreaMerge('merge' + mergeId++, 'DO_MODIFY', start,'DO_MODIFY', i-1, 'DO_MODIFY', start);
                    gridObj.AddAreaMerge('merge' + mergeId++, 'DO_DELETE', start,'DO_DELETE', i-1, 'DO_DELETE', start);
                    value = gridObj.GetCellValue('APPLY_START_DY', i);
                    start = i;
                }
                if (i == rowCount-1)
                {
                    gridObj.AddAreaMerge('merge' + mergeId++, 'DO_MODIFY', start,'DO_MODIFY', i, 'DO_MODIFY', start);
                    gridObj.AddAreaMerge('merge' + mergeId++, 'DO_DELETE', start,'DO_DELETE', i, 'DO_DELETE', start);
                }
            }
        }
    }
    
    /** ********************************************************
    * 수정 셀 클릭 시 이벤트 처리
    ********************************************************* */
    function doUpdate(strColumnKey, nRow)
    {
        var gridObj = document.WG1;
        
        var applyStartDy = gridObj.GetCellValue('APPLY_START_DY',nRow);
        
		var let3Ref = "04";
	    
	    if($("#deliDivnCd").val() == "20"){
	    	let3Ref = "05";
	    }

        var targetUrl = '<c:url value="/system/updateDeliveryChargePolicyPopUp.do"/>' +"?vendorId="+$('#vendorId').val()+"&applyStartDy="+applyStartDy+"&deliDivnCd="+$("#deliDivnCd").val()+"&let3Ref="+let3Ref;
        Common.centerPopupWindow(targetUrl, 'deliveryAmtInsertPopUp', {width : 620, height : 345});
    }
    
    /** ********************************************************
    * 삭제 셀 클릭 시 이벤트 처리
    ********************************************************* */
    function doDelete(strColumnKey,nRow)
    {
        if ( confirm('<spring:message code="msg.common.confirm.delete" />'))
        {
            var gridObj = document.WG1;
            var url = '<c:url value="/system/deleteDeliveryChargePolicy.do"/>';
        
            gridObj.setParam('vendorId',       $("#vendorId").val()                        );
            gridObj.setParam('deliDivnCd',     $("#deliDivnCd").val()                      );
            gridObj.setParam('APPLY_START_DY', gridObj.GetCellValue('APPLY_START_DY', nRow));
            gridObj.setParam('mode',           'delete'                                    );
            gridObj.DoQuery(url);
            loadingMask();
        }
    }
    
    /**********************************************************
     * 재조회
     *********************************************************/
    function refreshSearch()
    {
        doSearch();
    }
    
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#vendorId").val(strVendorId);
	}
</script>
</head>

<body>
<form name="dataForm" id="dataForm" method="post">
    <input type="hidden" name="today"           id="today"           value="<%=today%>"        />
    <input type="hidden" name="maxApplyStartDy" id="maxApplyStartDy" value="${maxApplyStartDy}"/>
    <input type="hidden" name="isInsert"        id="isInsert"        value="${isInsert}"       />
<div id="content_wrap">
<div class="content_scroll">

<div id="wrap_menu">

    <!-- -------------------------------------------------------- -->
    <!-- 검색조건 -->
    <!-- -------------------------------------------------------- -->
    <div class="wrap_search">   
        <div class="bbs_search">
            
            <!------------------------------------------------------------------ -->
            <!--    title -->
            <!------------------------------------------------------------------ -->
            <ul class="tit">
                <li class="tit">[배송/반품비 적용 이력 조회]</li>
                <li class="btn">
                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                    <a href="#" class="btn" id="create"><span><spring:message code="button.common.create" /></span></a>
                </li>
            </ul>
            <li>배송/반품비 예약 설정은 1회만 가능합니다</li>
            <li>배송/반품비 적용일이 현재일자 보다 작은 경우, 수정 및 삭제가 불가능 하며, 신규로 등록 하셔야 합니다</li>
            <!-------------------------------------------------- end of title -- -->
            
        </div><!-- id bbs_search// -->  
    </div><!-- id wrap_search// -->
    <!-----------------------------------------------end of 검색조건 -->
		
    <!-- -------------------------------------------------------- -->
    <!--    검색내역    -->
    <!-- -------------------------------------------------------- -->
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">협력업체코드</li>
                <li class="tit">
                   <select name="vendorId" id="vendorId" class="select">
							<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
		                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq (vendorId eq null ? epcLoginVO.repVendorId : vendorId)}">selected</c:if>>${venArr}</option>
							</c:forEach>
				   </select>
                </li>
                <li class="tit">배송구분</li>
                <li class="tit">
                   <select name="deliDivnCd" id="deliDivnCd" style="width:70px;">
						<c:forEach items="${codeList}" var="code" begin="0">
							<option value="${code.MINOR_CD}" <c:if test="${deliDivnCd eq code.MINOR_CD}">selected</c:if>> ${code.CD_NM }</option>
						</c:forEach>
				   </select>
                </li>
            </ul>
            
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td><script type="text/javascript">initWiseGrid("WG1", "100%", "380");</script></td>
                </tr>
            </table>
        </div>
    </div>
    <!-----------------------------------------------end of 검색내역  -->
	
	<!------------------------------------------------------------------ -->
	<!-- 	페이징 -->
	<!------------------------------------------------------------------ -->
	<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
 		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
 	</div>
 	<!------------------------------------------------------- end of 페이징 -->
	
</div><!-- id wrap_menu// -->
</div><!-- id content_scroll// -->

<!-- -------------------------------------------------------- -->
<!--    footer  -->
<!-- -------------------------------------------------------- -->
<div id="footer">
    <div id="footbox">
        <div class="msg" id="resultMsg"></div>
        <div class="location">
            <ul>
                <li>홈</li>
                <li>시스템관리</li>
                <li class="last">정책관리</li>
            </ul>
        </div>
    </div>
</div>
<!---------------------------------------------end of footer -->
</div><!-- id content_wrap// -->
	
</form>
</body>

</html>