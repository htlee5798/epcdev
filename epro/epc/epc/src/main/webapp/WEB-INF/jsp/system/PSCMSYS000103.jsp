<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="lcn.module.common.util.DateUtil"%>

<%
    String today = DateUtil.formatDate(DateUtil.getToday(), "-");
%>

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

$(document).ready(function(){
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
    });
	
    $('#save').click(function() {
        doInsert();
    });
    
    $('#close').click(function() {
        top.close();
    });
	
	$("#deliveryAmt20").keyup(function(event){
	    if (!(event.keyCode >=37 && event.keyCode<=40)) {
	        var inputVal = $(this).val();
	        $(this).val(inputVal.replace(/[^0-9]/gi,''));
	    }
	});
	
	$("#deliveryAmt30").keyup(function(event){
	    if (!(event.keyCode >=37 && event.keyCode<=40)) {
	        var inputVal = $(this).val();
	        $(this).val(inputVal.replace(/[^0-9]/gi,''));
	    }
	});
});

/** ********************************************************
 * 저장버튼 클릭시 발생 이벤트
 ******************************************************** */
function doInsert()
{
	var applyStartDy = $("#applyStartDy").val();
	
	if (applyStartDy <= dataForm.today.value)
    {
         alert("익일자 부터 등록 가능합니다");
         return;
    }
	
	// 배송비 이력을 조회해서 가장 마지막 적용시작일자(maxApplyStartDy)를 가져오는데, 이 값이 null 이면 validation 체크할 필요가 없음
    if (!Common.isEmpty(dataForm.maxApplyStartDy.value))
    {
        if (applyStartDy <= dataForm.maxApplyStartDy.value)
        {
            alert("마지막으로 등록한 시작일자 이 전으로는 등록할 수 없습니다");
            return;
        }
    }
	
	if($("#deliveryAmt20").val() == "")
	{
		alert("반품비를 등록 하십시오.");
		$("#deliveryAmt20").focus();
		return;
	}
	
	if($("#deliveryAmt30").val() == "")
	{
		alert("교환비를 등록 하십시오.");
		$("#deliveryAmt30").focus();
		return;
	}
	
    if (confirm('<spring:message code="msg.common.confirm.save"/>'))
    {
    	var form = document.dataForm;
        var url = '<c:url value="/system/insertDeliveryAmt20.do"/>';
        
        form.action = url;

    	loadingMask();
        form.submit();
    }
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
        <h1>반품비 적용예정일 등록</h1>
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
            <li class="last">반품비정책관리</li>
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
	        <li>반품비 적용예정일 등록은 익일자 부터 가능합니다</li>
	    </div>
	    <!----------------------------------------------------- end of 검색조건 -->
	
	    <!------------------------------------------------------------------ -->
	    <!--    검색내역 -->
	    <!------------------------------------------------------------------ -->
	    <div class="wrap_con">
	        <!-- list -->
	        <div class="bbs_list">
	            <table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0" style="margin-top:0px;">
				<colgroup>
					<col style="width:20%" />
					<col style="width:30%" />
					<col style="width:20%" />
					<col style="width:30%" />
				</colgroup>
				<tr>
					<th class="fst" rowspan="2">적용시작일</th>
					<td rowspan="2"><input type="text" name="applyStartDy" id="applyStartDy" class="day" readonly style="width:45%;" /><a href="javascript:openCal('dataForm.applyStartDy')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" style="margin-bottom:8px;"/></a></td>
					<th>반품비</th>
					<td><input type="text" name="deliveryAmt20" id='deliveryAmt20' size="20" style="text-align:right; ime-mode:disabled;"/></td>
				</tr>
				<tr>
					<th>교환비</th>
					<td><input type="text" name="deliveryAmt30" id='deliveryAmt30' size="20" style="text-align:right; ime-mode:disabled;"/></td>
				</tr>
				</table>
	        </div>
	    </div>
	    <!------------------------------------------------------- end of 검색내역 -->
	
	
	</div><!-- class popup_contents// -->
</div><!-- id popup// -->

</form>
</body>
</html>