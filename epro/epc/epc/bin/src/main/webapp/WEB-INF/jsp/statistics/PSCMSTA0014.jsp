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
	$('#extStrCd').val('${extStrCd}');
	if('${resultYn}' == "Y"){
	 setLMPaging('${totalCount}','${rowsPerPage}' ,'${currentPage}' ,'goPage','pagingDiv','1');
	}
	
	$('#ordNo').val('${ordNo}');
	
	$('#search').click(function() {
		doSearch();
	});

  	$('#save').click(function() {
		doSave();
	}); 
  	
  	$('#takeOverCmp').click(function(){
  		doPickSave("001");
  	})

  	$('#pickUpCmp').click(function(){
  		doPickSave("002");
  	});

  	$('#pickUpListExcel').click(function(){
  		pickUpListExcel();
  	});

  	$('#excgOrRtnExcel').click(function(){
  		excgOrRtnExcel();
  	})

	$('#excel').click(function() {
		doExcel();
	});	
  	
	$('#startDate').click(function() {
		openCal('adminForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('adminForm.endDate');
	});	
	
 	$('#allChkBox').click(function(){
		var chk_length = $('input[name="chkBox"]').length;
		if(chk_length > 0){
			if ($('#allChkBox').attr("checked") == "checked"){
				$('#allChkBox').attr('checked', true);
				for(var i=0; i<chk_length; i++){
					$('input[name="chkBox"]').eq(i).attr('checked', true);
				}
			}else{
				$('#allChkBox').attr('checked', false);
				for(var i=0; i<chk_length; i++){
					$('input[name="chkBox"]').eq(i).attr('checked', false);
				}
			}
		}else if(chk_length == null || chk_length < 1){
			$('#allChkBox').attr('checked', false);
		}
	}); 
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
	
	var url = '<c:url value="/statistics/selectRentCarPickUp.do"/>';
	form.pageIndex.value = pageIndex;
	form.action = url;
	loadingMask();
	form.submit();
}

function doPickSave(flag){
	
	  var form = document.adminForm;
	  var chk_box = document.getElementsByName("chkBox");
	  var chk_length = chk_box.length;
	  var chk_box_arr = '';
	  var pFlag = flag;
	  var chkCnt = 0;
	  for(var i=0; i<chk_length; i++){
		  if(chk_box[i].checked == true){
			  var idx = chk_box[i].getAttribute('id').substr(7);
			  var pickup_Sts = document.getElementById("pickupSts_"+idx);
			  if(flag == "001"){
				  if(pickup_Sts.value != ""){
				     alert("인수완료된 주문건이 있습니다.");
				     chk_box[i].checked=false;
				     return false;
				  }
			  }else if(flag == "002"){
			  	 if(pickup_Sts.value == "002"){
				     alert("픽업완료된 주문건이 있습니다.");
				     chk_box[i].checked=false;
			  	 	 return false;
			  	 }
			  }
			  if(chk_box_arr.length == 0){
				  chk_box_arr += chk_box[i].value;
			  }else{
				  chk_box_arr += ','+chk_box[i].value;
			  }	
			    
			     chkCnt++;
		  }
	  }  
		
	  if(chkCnt < 1){
	  	  alert("체크박스를 체크해주세요");
		  return;
	  }
		
	  if(!confirm("저장하시겠습니까?")){
		  return false;
	  }

	  form.chk_box_arr.value = chk_box_arr;
	  form.pickup_Sts.value = pFlag;
		
	  $.ajax({
		  url : '<c:url value="/statistics/saveRentCarPickUp.do"/>',
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
	
	if(!confirm("저장하시겠습니까?")){
		return false;
	}
	form.chk_box_arr.value = chk_box_arr;
	form.pickup_Sts_arr.value = pickup_Sts_arr;
	
	 $.ajax({
				url : '<c:url value="/statistics/saveRentCarPickUp.do"/>',
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

function pickUpListExcel(){
	var f = document.adminForm;
	var chk_box = document.getElementsByName("chkBox");
	var chkCnt = 0;
	var chk_box_arr = '';

	if(f.chkBox == undefined){
		alert("출력 할 자료가 없습니다.");
		return false;	
	}
	
	for(var i=0; i<chk_box.length; i++){
		if(chk_box[i].checked == true){
			 if(chk_box_arr.length == 0){
				  chk_box_arr += chk_box[i].value;
			  }else{
				  chk_box_arr += ','+chk_box[i].value;
			  }	
			chkCnt++;
		}
	}
	
	
	if(chkCnt < 1){
		alert("체크박스를 체크해주세요");
		return;
	}
	
	if(!confirm("픽업 리스트를 출력 하시겠습니까?")){
		return false;
	}
	
	
	f.chk_box_arr.value = chk_box_arr;
	f.pageIndex.value = 1;
 	var url = '<c:url value="/statistics/pickUpListExcel.do"/>';
	f.action = url;
	f.submit();	  
}

function excgOrRtnExcel(){
	var f = document.adminForm;
	
	if(!confirm("교환반품대장을 출력 하시겠습니까?")){
		return false;
	}
	
	var url = '<c:url value="/statistics/excgOrRtnExcel.do"/>';
	f.action = url;
	f.submit();	
}


/** ********************************************************
 * execl
 ******************************************************** */
function doExcel(){
	var f = document.adminForm;

	if(f.chkBox == undefined){
		alert("엑셀로 변환 할 자료가 없습니다.");
		return false;	
	}
	
	if(!confirm("엑셀 다운로드 하시겠습니까?")){
		return false;
	}
	
 	var url = '<c:url value="/statistics/rentCarPickUpListExcel.do"/>';
	f.pageIndex.value = 1;
	f.action = url;
	f.submit();	  
}

/**********************************************************
 * 숫자만 입력 가능
 ******************************************************** */
function onlyNumber()
{
    if ((event.keyCode<48) || (event.keyCode>57))
    {
    	if(event.preventDefault){
	    	event.preventDefault();
    	}else{
        	event.returnValue = false;
    	}
    }
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form id="adminForm" name="adminForm">
<input type="hidden" name="chk_box_arr" value=""/>
<input type="hidden" name="pickup_Sts" value=""/>
<input type="hidden" name="extStrCd" id="extStrCd" value=""/>
<c:choose>
	<c:when test="${extStrCd == '007'}" >
		<c:set var="branchNm" value="주유소지점"/>
	</c:when>
	<c:when test="${extStrCd == '008'}" >
		<c:set var="branchNm" value="세븐일레븐지점"/>
	</c:when>
	<c:otherwise>
		<c:set var="branchNm" value="렌터카지점"/>
	</c:otherwise>
</c:choose>
<div id="wrap_menu">

	<!--	@ 검색조건	-->
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
 				    <a href="#" class="btn" id="pickUpListExcel"><span>픽업리스트출력</span></a> 
 				    <a href="#" class="btn" id="excgOrRtnExcel"><span>교환/반품대장 출력</span></a> 
 				    <a href="#" class="btn" id="takeOverCmp"><span>인수완료</span></a> 
 				    <a href="#" class="btn" id="pickUpCmp"><span>픽업완료</span></a> 
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
 				    <!-- <a href="#" class="btn" id="save"><span>저장</span></a>  -->
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="10%">
				<col width="28%">
				<col width="10%">
				<col width="20%">
				<col width="13%">
				<col width="15%">
			</colgroup>
			<tr>
				<th><span class="star">*</span> 픽업일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" readonly style="width:35%;" value="${searchVO.startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:35%;" value="${searchVO.endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th>마트점포</th>
				<td>
					<select id="martStrNo" name="martStrNo" class="select">
					<option value="">전체</option>
						<c:forEach items="${martStrList}" var="StrList" begin="0">
 							<option value="${StrList.STR_CD}" ${StrList.STR_CD == mSelStr ? "selected='selected'" : "" }> 
 							${StrList.STR_NM}</option>
 						</c:forEach>
					</select>
				</td>
				<th>${branchNm}</th>
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
			<th>픽업시간</th>
			<td>
				<select id="pickUpTime" name="pickUpTime" class="select">
					<option value="">전체</option>
					<option value="4" ${selPtime == 4 ? "selected='selected'" : "" }>8시~</option>
					<option value="1" ${selPtime == 1 ? "selected='selected'" : "" }>15시~</option>
					<option value="2" ${selPtime == 2 ? "selected='selected'" : "" }>17시~</option>
					<option value="3" ${selPtime == 3 ? "selected='selected'" : "" }>19시~22시</option>
				</select>
			</td>
			<th>주문구분</th>
			<td>
				<select id="ordDivn" name="ordDivn" class="select" >
					<option value="">전체</option>
					<c:forEach items="${OrdDivnList}" var="list">
						<option value="${list.MINOR_CD}" ${list.MINOR_CD == selDiv ? "selected='selected'" : ""}>${list.CD_NM}</option>
					</c:forEach>
				</select>
			</td>
			<th>픽업상태</th>
				<td>
					<select id="pickupStatus" name="pickupStatus" class="select">
					<option value="">전체</option>
					<!-- <option value="000" ${pSelStr == "000" ? "selected='selected'" : ""}></option> -->
						<c:forEach items="${pickupStsList}" var="pickList" begin="0">
 							<option value="${pickList.MINOR_CD}" ${pickList.MINOR_CD == pSelStr ? "selected='selected'" : "" }> 
 							${pickList.PICK_UP_STATE}</option>
 						</c:forEach>
					</select>
				</td>
				
			</tr>
			<tr>
			<th>배송상태</th>
				<td>
					<select id="deliStatus" name="deliStatus" class="select">
					<option value="">전체</option>
					 	<c:forEach items="${deliStatusList}" var="deliList" begin="0">
 							<option value="${deliList.MINOR_CD}" ${deliList.MINOR_CD == dSelStr ? "selected='selected'" : "" }> 
 							${deliList.CD_NM}</option>
 						</c:forEach> 
					</select>
				</td>
				<th>주문번호조회</th>
				<td>
					<input type="text" id="ordNo"  name="ordNo" maxlength="13" style="width:90%; ime-mode:disabled" onKeyPress="onlyNumber()"/>
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
			<div div style="overflow-y:scroll;width:100%;height:350px;">
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
		      			<col style="width:30px" />
                        <col style="width:30px" />
                        <col style="width:100px" />
                        <col style="width:120px" />
                        <col style="width:110px" />
                        <col style="width:110px" />
                        <col style="width:110px" />
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <col style="width:120px" />
                        <col style="width:120px" />
                        <col style="width:120px" />
                        <col style="width:120px" />
                        <col style="width:110px" />
			</colgroup>
			<tr>
				<td class="fst" style="background:url('/images/common/layout/bg_list_04.gif');"><input type="checkbox" id="allChkBox" name="allChkBox"/> </td>
				<th>순번</th>
				<th>주문구분</th>
				<th>픽업일자</th>
				<th>픽업시간</th>
				<th>주문번호</th>
				<th>배송상태</th>
				<th>라벨번호</th>
				<th>고객명</th>
				<th>구매금액</th>
				<th>픽업완료일시</th>
				<th>인수완료일시</th>
				<th>마트점포명</th>
				<th>${branchNm}</th>
				<th>고객요청사항</th>
			</tr>
			<c:forEach var="result" items="${list}" varStatus="status">
			<tr class="r1">
				<%-- <td ><input name="chkBox" type="checkbox" value="${result.ORDER_ID}"/> </td> --%> 
				<td class="fst"><input id="chkBox_${status.count}" name="chkBox" type="checkbox" value="${result.ORDER_ID}"></input> </td>
				<td>${result.NUM}</td>
				<td>${result.ORD_DIVN}</td>
        		<td>${result.DELI_HOPE_DY}</td>
				<td>${result.PICKUP_TIME}</td>
				<td>${result.ORDER_ID}</td>
				<td>${result.DELI_STATUS_CD}</td>
        		<td>${result.FIRST_BK_NO}</td>
        		<td>${result.RECP_PSN_NM}</td>
				<td style='text-align: right'><fmt:formatNumber value="${result.TOT_SELL_AMT}" pattern="#,###,###,###.##" /></td>
        		<td>${result.HAND_OVER_DATE}</td>
        		<td>${result.TAKE_OVER_DATE}</td>
				<td>${result.STR_NM}</td>
				<td>${result.EXT_STR_NM}</td>
				<td>${result.CUST_REQ}</td>
				<td><input type="hidden" id="pickupSts_${status.count}" name="pickupSts" value="${result.PICKUP_STATUS}"/></td>
        	<%-- 	<td>
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
        		</td> --%>
			</tr>
			</c:forEach>
	<%-- 		<c:if test="${empty list}">
			<tr class="r1">
				<td colspan="15"><spring:message code="msg.common.info.nodata"/></td>
			</tr>
			</c:if> --%>
			<tr class="r1">
				<td height="1" style="color: blue;" colspan="15"></td>
			</tr>
			</table>
			</div>
		</div>
		<!-- 2검색내역 // -->
		<div id="pagingDiv" class="pagingbox1" style="width: 100%;margin-top:20px;">
	        <script> setLMPaging("0", "0", "0","goPage","pagingDiv","1");</script>
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
					<li class="last">롯데렌터카</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>