<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:import url="/common/commonHead.do" />
<!-- statistics/PSCMSTA0013 -->
<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	$('#search').click(function() {
		doSearch();
	});

  	$('#save').click(function() {
		doSave();
	}); 
	 

	$('#excel').click(function() {
		doExcel();
	});	
	$('#startDate').click(function() {
		openCal('adminForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('adminForm.endDate');
	});	
	
	$('select').live("change",function(){
		var idx = $('select[name=pickupSts]').index(this) + 1;
		var val1 = $('#chkBox_'+(idx)).attr('status');
		var val2 = $(this).val();
		if($(this).val() != "" && (val1 != val2)){
			var val = $('#chkBox_'+idx).attr('checked',true);
		}else{
			var val = $('#chkBox_'+idx).attr('checked',false);
		}
	})
	
	$('input[name="chkBox"]').click(function(){
		var chkIdx = $('input[name="chkBox"]').index(this);
		var val1 = $('#chkBox_'+(chkIdx+1)).attr('status');
		var val2 = $('#pickupSts_'+(chkIdx+1)).val();
		if((val2 == "") || (val1 == val2)){
			$(this).attr('checked', false);
		}
	})

}); // end of ready

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}

/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(pageIndex){
	var form = document.adminForm;
	//var rangeDate = 0;
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}
	
	/*  rangeDate=Date.parse(endDate)-Date.parse(startDate);
	 rangeDate=Math.ceil(rangeDate/24/60/60/1000);
		
		//검색기간이 30일 넘는지 여부 체크
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			resultFlag =  false ;
		}	
	 */
	
	var url = '<c:url value="/statistics/selectCrossPickUpList.do"/>';
	form.pageIndex.value = pageIndex;
	form.action = url;
	loadingMask();
	form.submit();
}

/** ********************************************************
 * 저장
 ******************************************************** */
  function doSave(){
    var form = document.adminForm;
	var chk_box = document.getElementsByName("chkBox");
	var chk_length = chk_box.length;
	var chk_box_arr = '';
	var pickup_Sts_arr = '';
	var chkCnt = 0;
	for(var i=0; i<chk_length; i++){
		if(chk_box[i].checked == true){
			var idx = chk_box[i].getAttribute('id').substr(7);
		    var pickup_Sts = document.getElementById("pickupSts_"+idx);
			if(pickup_Sts.value == ""){
				alert("픽업상태를 체크해주세요");
				return;
			}
		    if(chk_box_arr.length == 0){
				chk_box_arr += chk_box[i].value;
		    	pickup_Sts_arr += pickup_Sts.value;
			}else{
				chk_box_arr += ','+chk_box[i].value;
				pickup_Sts_arr += ','+pickup_Sts.value;
			}	
		    
		    chkCnt++;
		}
	}  
	
	if(chkCnt < 1){
		alert("체크박스를 체크해주세요");
		return;
	}
			
	form.chk_box_arr.value = chk_box_arr;
	form.pickup_Sts_arr.value = pickup_Sts_arr;
	
	 $.ajax({
				url : '<c:url value="/statistics/crossPickUpSave.do"/>',
				type: 'POST',
				dataType: 'json',
				data: $('#adminForm').serialize(), 
				success: function(data) {
					alert(data.rstMsg);
					doSearch();
				},
				error: function(){
					alert(data.rstMsg);
				}
			}); 
}  


/** ********************************************************
 * execl
 ******************************************************** */
function doExcel(){
	var f = document.adminForm;
	var url = '<c:url value="/statistics/crossPickUpListExcel.do"/>';
	f.pageIndex.value = 1;
	f.action = url;
	f.submit();	
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form id="adminForm" name="adminForm">
<input type="hidden" name="chk_box_arr" value=""/>
<input type="hidden" name="pickup_Sts_arr" value=""/>

<div id="wrap_menu">

	<!--	@ 검색조건	-->
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
 				    <a href="#" class="btn" id="save"><span>저장</span></a> 
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="10%">
				<col width="30%">
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="15%">
			</colgroup>
			<tr>
				<th><span class="star">*</span> 배송일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" readonly style="width:35%;" value="${searchVO.startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:35%;" value="${searchVO.endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th><span class="star">*</span> 마트점포 </th>
				<td>
					<select id="martStrNo" name="martStrNo" class="select">
					<option value="">전체</option>
						<c:forEach items="${martStrList}" var="StrList" begin="0">
 							<option value="${StrList.STR_CD}" ${StrList.STR_CD == mSelStr ? "selected='selected'" : "" }> 
 							${StrList.STR_NM}</option>
 						</c:forEach>
					</select>
				</td>
				<th><span class="star">*</span> 슈퍼점포 </th>
				<td>
					<select id="superStrNo" name="superStrNo" class="select">
					<option value="">전체</option>
						<c:forEach items="${superStrList}" var="StrList" begin="0">
 							<option value="${StrList.EXT_STR_CD}" ${StrList.EXT_STR_CD == sSelStr ? "selected='selected'" : "" }> 
 							${StrList.EXT_STR_NM}</option>
 						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
			<th><span class="star">*</span> 픽업상태 </th>
				<td>
					<select id="pickupStatus" name="pickupStatus" class="select">
					<option value="">전체</option>
					<!-- <option value="000" ${pSelStr == "000" ? "selected='selected'" : ""}></option> -->
						<c:forEach items="${pickupStsList}" var="pickList" begin="0">
 							<option value="${pickList.MINOR_CD}" ${pickList.MINOR_CD == pSelStr ? "selected='selected'" : "" }> 
 							${pickList.CD_NM}</option>
 						</c:forEach>
					</select>
				</td>
				<th><span class="star">*</span> 배송상태 </th>
				<td>
					<select id="deliStatus" name="deliStatus" class="select">
					<option value="">전체</option>
					 	<c:forEach items="${deliStatusList}" var="deliList" begin="0">
 							<option value="${deliList.MINOR_CD}" ${deliList.MINOR_CD == dSelStr ? "selected='selected'" : "" }> 
 							${deliList.CD_NM}</option>
 						</c:forEach> 
					</select>
				</td>
			</tr>
			</table>
		</div>
		<!-- 1검색조건 // -->
		
	</div>
	

	<c:if test="${initFlag != 'Y'}">
	<div class="wrap_con">
		<!-- list -->
			
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">조회내역</li>
			</ul>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="3%">
				<col width="5%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="13%">
				<col width="10%">
				<col width="8%">
				<col width="8%">
				<col width="10%">
				<col width="11%">
			</colgroup>
			<tr>
				<th class="fst"></th>
				<th>순번</th>
				<th>배송일자</th>
				<th>픽업일자</th>
				<th>마트점포명</th>
				<th>슈퍼점포명</th>
				<th>주문번호</th>
				<th>배송상태</th>
				<th>라벨번호</th>
				<th>고객명</th>
				<th>구매금액</th>
				<th>픽업상태</th>
			</tr>
			<c:forEach var="result" items="${list}" varStatus="status">
			<tr class="r1">
				<%-- <td ><input name="chkBox" type="checkbox" value="${result.ORDER_ID}"/> </td> --%> 
				<td class="fst"><input id="chkBox_${status.count}" name="chkBox" type="checkbox" value="${result.ORDER_ID}" ${result.PICKUP_STATUS == pickupStsList[1].MINOR_CD ? "onclick=return(false)" : ""} status="${result.PICKUP_STATUS}"></input> </td>
				<td>${result.NUM}</td>
        		<td>
        			<fmt:parseDate value="${result.DELI_HOPE_DY}" var="dateFmt" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
        		</td>
        		<td>
        			<fmt:parseDate value="${result.PICKUP_DY}" var="dateFmt" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
        		</td>
				<td>${result.STR_NM}</td>
				<td>${result.EXT_STR_NM}</td>
				<td>${result.ORDER_ID}</td>
				<td>${result.DELI_STATUS_CD}</td>
        		<td>${result.FIRST_BK_NO}</td>
        		<td>${result.RECP_PSN_NM}</td>
				<td style='text-align: right'><fmt:formatNumber value="${result.TOT_SELL_AMT}" pattern="#,###,###,###.##" /></td>
        		<td>
        			<select id="pickupSts_${status.count}" name="pickupSts" class="select" style="width:70px;">
        				<c:if test="${empty result.PICKUP_STATUS}">
        				<option ></option>
        				<c:forEach items="${pickupStsList}" var="pickList" begin="0">
        					<option value="${pickList.MINOR_CD}" ${pickList.MINOR_CD == result.PICKUP_STATUS ? "selected='selected'" : "" }> 
 							${pickList.CD_NM}</option>
        				</c:forEach>
        				</c:if>
        				<c:if test="${result.PICKUP_STATUS eq '001'}">
        				<c:forEach items="${pickupStsList}" var="pickList" begin="0">
        					<option value="${pickList.MINOR_CD}" ${pickList.MINOR_CD == result.PICKUP_STATUS ? "selected='selected'" : "" }> 
 							${pickList.CD_NM}</option>
        				</c:forEach>
        				</c:if>
        				<c:if test="${result.PICKUP_STATUS eq '002'}">
        					<option value="${pickupStsList[1].MINOR_CD}">${pickupStsList[1].CD_NM}</option>
        				</c:if>
        			</select>
        		</td>
			</tr>
			</c:forEach>
			<c:if test="${empty list}">
			<tr class="r1">
				<td colspan="14"><spring:message code="msg.common.info.nodata"/></td>
			</tr>
			</c:if>
			</table>
		</div>
		<!-- 2검색내역 // -->

		<div id="paging">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
		</div>
	</div>
	</c:if>
	<input type="hidden" name="pageIndex" />
 
</div>
</form>

</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg">${resultMsg}</div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>통계</li>
					<li class="last">크로스픽업</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>