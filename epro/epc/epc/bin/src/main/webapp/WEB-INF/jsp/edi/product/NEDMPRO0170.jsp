<%--
	Page Name 	: NEDMPRO0170.jsp
	Description : 단종상품등록
	Modification Information

	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2020.05.27 		          	 		최초생성

--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
    tr.on {
        background-color: #E6E6E6;
    }
</style>
<title></title>

<script>
	$(document).ready(function() {
		var currDate   = '<c:out value="${currDate}" />'; 			// 오늘 날 짜
		var listData   = 0;											// 조회 결과로 얻게될 상품 리스트들 저장
		var viewType = 0;											// 3가지 상품 조회 구분을 위한 변수 ( 협력코드기반, 단종등록기반, 단종등록내 날짜기반 )
		var test =  "<c:out value='${epcLoginVO.adminId}'/>";
		console.log(test);
		
		$("#fromRegDate").text(strdateToDate(changeDayByMonth((getToday()*1+7)+"")));  
		$("#toRegDate").text(strdateToDate(changeDayByMonth((getToday()*1+30)+"")));  
		$("#fromImpDate").text(strdateToDate(changeDayByMonth((getToday()*1+6)+"")));
		
		$("tbody").delegate("input[name='disconApplyBox']", "click", function(){
	        $('input[type="checkbox"]').not(this).prop('checked', false);
	      });
	});

	/* 조회 */
	/* function doSearch() {
		goPage('1');
	} */
	
	/* 조회 */
	function doSearch(type) {
		
		var searchInfo = {};
		
		searchInfo["entpCd"] 	= $("#entpCd").val();		// 협력업체
		searchInfo["pageIndex"] = '1';						// page
		searchInfo["srchSrcmkCd"] = $("#srchSrcmkCd").val();  // 검색하는 상품코드
		viewType = type;									// 버튼에서 받는 값
		
		if(type==2 && $("#disconDate").val()==""){			// 단종정보 날짜 기반 검색시, 날짜 정보 있는 지 확인
			alert("단종날짜를 선택해주세요.");
			return ;
		}
		
		if($("#srchSrcmkCd").val() && $("#srchSrcmkCd").val().length!=13){
			alert("판매코드 13자리를 입력하셔야합니다.");
			return ;
		}
		
		if(type==1)											// 협력업체 기반 상품 조회
		{
			setInitDisconDateRow();
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/NEDMPRO0170Select.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$("#pageIndex").val(1);
					
					//json 으로 호출된 결과값을 화면에 Setting
					_setTbodyMasterValue(data);
					listData=data.resultList;
				}
			});
		}
		else if(type==2)									// 단종정보 내에 날짜 기반 조회
		{
			searchInfo["disconApplyDt"]= dateToStrdate($("#disconDate").val());
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectDisconProductByDate.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$("#pageIndex").val(1);
					
					_setTbodyMasterValue(data);				
					listData=data.resultList;
				}
			});
		}
		else {												// 단종정보 조회 
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectDisconProduct.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$("#pageIndex").val(1);					// 인덱스 값 1로
					
					setInitDisconDateRow();					// 단종정보 조회시 날짜기반검색 버튼 Show
					appendDisconDateRow();
					
					_setTbodyMasterValue(data);				
					listData=data.resultList;
				}
			});
		}
	}

	/* 단종 정보 등록 */
	function doSave() {
		
		var idx = $('input:checkbox[name="disconApplyBox"]:checked').val();
		
		if(idx == null){
			alert("등록할 상품의 체크박스를 선택해주세요.");
			return;
		}			
		var disconApplyDtChecked = dateToStrdate($("#disconApplyDt"+idx+"").val());
		var disconReasonChecked = $("#disconReason"+idx+"").val();
		
		if(disconApplyDtChecked=="단종정보가 없습니다."){
			alert("단종 날짜를 선택해주세요.");
			return ;
		}
		
		if(disconReasonChecked == '' || disconReasonChecked == '선택'){
			alert("변경 사유를 선택해주세요.");
			return ;
		}
		
		if(!checkDateCanModified(listData[idx].disconApplyDt)		// 기존 등록되어 있는 날짜 체크
		|| !checkDateCanApply(disconApplyDtChecked)){				// 변경 될 날 날짜 체크
			return ;
		}    
		
		var adminId = "<c:out value='${epcLoginVO.loginNm}'/>";
		var info	= {};
		
		info["disconApplyDt"] = disconApplyDtChecked;				// 단종 예정 날짜
		info["entpCd"]		  = listData[idx].entpCd;				// 협럭코드
		info["prodCd"]	      = listData[idx].prodCd;				// 상품코드
		info["mstate"]		  = "03";
		info["disconReason"]  = disconReasonChecked;				// 변경 사유
		
		if( listData[idx].regNm != "-" ){ // 기존 단종 정보 수정일 때
			
			info["regDt"]=listData[idx].regDt;
			info["regTm"]=listData[idx].regTm;
			info["regNm"]=listData[idx].regNm;
			
			info["modDt"]=getToday();
			info["modTm"]=getTime();
			//info["modNm"]=adminId;
		}
		else{							  // 단종 정보 새로 입력할 떄
			info["regDt"]=getToday();
			info["regTm"]=getTime();
			//info["regNm"]=adminId;
			
			info["modDt"]=null;
			info["modTm"]=null;
			info["modNm"]=null;
		}
		
		if (!confirm("등록하시겠습니까?")) {
			return;
		}

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/insertDisconProduct.json"/>',
			data : JSON.stringify(info),
			success : function(data) {
				if (data.retMsg == "S") {
					alert("등록되었습니다.");
					doSearch(viewType);			// 재조회
				}
			}
		}); 
	}
	
	/* 단종 취소  */
	function doCancel(){
	 	var idx = $('input:checkbox[name="disconApplyBox"]:checked').val();
		if(idx == null){
			alert("삭제할 상품의 체크박스를 선택해주세요.");
			return ;
		}			
		var disconApplyDtChecked = dateToStrdate($("#disconApplyDt"+idx+"").val());
		
		if(disconApplyDtChecked=="단종정보가 없습니다."){
			alert("지울 단종정보가 없습니다");
			return ;
		}
		
		if(!checkDateCanModified(listData[idx].disconApplyDt)){		// 기존 등록되어 있는 날짜 체크
			return ;
		}
		
		var info	= {};

		info["disconApplyDt"] = dateToStrdate(listData[idx].disconApplyDt);
		info["entpCd"]		  = listData[idx].entpCd;
		info["prodCd"]	      = listData[idx].prodCd;

		if (!confirm("삭제하시겠습니까?")) {
			return ;
		}

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/deleteDisconProduct.json"/>',
			data : JSON.stringify(info),
			success : function(data) {
				if (data.retMsg == "S") {
					alert("삭제되었습니다.");
					doSearch(viewType);			// 재조회
				}
			}
		});  
	}
	
	/*  */
	function _setTbodyMasterValue(json) {
		setTbodyInit("dataListbody");		    // dataList 초기화

		var data = json.resultList;				// 조회결과를 얻는 데이터
		
		if (data.length > 0) {
			
			var total_html=0;
			
	        for(var i=0; i<data.length; i++){
	        	
	        	if(isDisconApply(data[i])){		// DB에 넣기 위해 형식 변환
	        		data[i].disconApplyDt = strdateToDate(data[i].disconApplyDt);
	        	}
	        	else{							// 값이 없을 때, 메시지 
	        		data[i].disconApplyDt = "단종정보가 없습니다.";
	        		data[i].regDt		  = "-";
	        		data[i].regNm		  = "-";
	        	}
	        	
	        	if(!data[i].werksCnt)data[i].werksCnt='0'; // 점포수 조회를 받지 못했다면 진열 점포수는 0
	        	
	        	if(!data[i].zstat)data[i].zstat="대기";
	        	else if(data[i].zstat=="1")data[i].zstat="등록";
	        	else if(data[i].zstat=="2")data[i].zstat="승인";
	        	else if(data[i].zstat=="3")data[i].zstat="반려";
	        	else if(data[i].zstat=="4")data[i].zstat="FAIL";
	        	else if(data[i].zstat=="5")data[i].zstat="완료";
	        	
	     
	        	total_html+='<tr class="r1"  bgcolor=ffffff>'+
	            	 		 '<td><input type="checkbox" name="disconApplyBox" value="'+i+'" onclick="rowFocus(this)"></td>'+
							 '<td align="center">'+data[i].rnum+'</td>'+
	                  		 '<td align="center">'+data[i].srcmkCd+'</td>'+
	                  		 '<td align="center">'+data[i].prodCd+'</td>'+
	                  		 '<td align="left">'+data[i].prodNm+'</td>'+
	                  		 '<td align="center">'+data[i].werksCnt+'</td>'+
	                  		 '<td align="center">'+
	           				 '     <select id="disconReason'+i+'" name="disconReason">'+
	           				 '		<option value="">선택</option>'+
	           				 '		<option value="취급중단(단종)">취급중단 (단종)</option>'+
	           				 '     </select>'+
	            			 '</td>'+
	                  	 	 '<td align="center">'+
	                  		 '     <input type="text" id="disconApplyDt'+i+'" name="disconApplyDt" 	value="'+data[i].disconApplyDt+'" readonly />'+
	                  		 '     <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal(\'searchForm.disconApplyDt'+i+'\');" style="cursor: hand;" />'+
	                  	     '</td>'+
	                  	  	 '<td align="center">'+data[i].regDt+'</td>';
	            if(viewType!=1){
	            	total_html+='<td align="center">'+data[i].zstat+'</td>';
	            }
	            total_html+='</tr>';
	          
	        }
	        
			$("#dataListbody").append(total_html);
			
			selectDisconReason(data);		// 입력한 변경사유 조회
			$("#paging-ul").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 20);
			$("#paging-ul").html("");
		}
	}
	
	/* 체크박스에 체크 되어 있는지 */
	function checkValidation(){
	    var isChecked= $("input:checkbox[name='disconApplyBox']").is(":checked");
	    return isChecked;
	}
	
	/* '-'있는 날짜 형식에서 '-' 제거 */
	function dateToStrdate(date){
		var dateSplited = date.split("-");
		var strdate     = dateSplited[0];

		for(var i=1; i<dateSplited.length; i++){
			strdate+=dateSplited[i];
		}

	    return String(strdate);
	}
	
	/* '-'있는 날짜 형식에서 '-' 제거 */
  	function strdateToDate(strdate){
	   if(strdate==undefined)
	      return "단종정보 없음";  
  			
	   var strdateSplited = strdate.split("-");
	   if(strdateSplited.length!=1)
	      return strdate;
	      
	   var year = strdate.substr(0,4);
	   var mon  = strdate.substr(4,2);
	   var day  = strdate.substr(6,2);
	      //alert(mon);
	   var date = year + '-' + mon + '-' + day ;
	
	   return date;
	}
  	
	/* 체크했을 시 그 줄 강조  */
  	function rowFocus(obj){
        var tabNum = obj.parentNode.parentNode.parentNode.children.length; 
        for (var i = 0; i < tabNum; i++)
            obj.parentNode.parentNode.parentNode.children[i].className = '';
        obj.parentNode.parentNode.className = 'on';
    }
  	
	/* 적용할 날짜를 선택했는지 체크 */
    function isDisconApply(data){
    	if(data.disconApplyDt==null){
    		return false;}
    	else {
    		return true;}
    }
  	
    function getToday(){
    	var tdDt;
    	var dt = new Date();

    	var year  = dt.getFullYear();
    	var month = dt.getMonth()+1;
    	if(month<10)  month="0"+month;
    	var day   = dt.getDate();
    	if(day<10)  day="0"+day;
    	tdDt      = ""+year+month+day;
    	return tdDt;
    }

    function getTime(){
    	var dt = new Date();
    	var tdTm;

    	var hour   = dt.getHours();
    	if(hour<10)   hour  ="0"+hour;
    	var minute = dt.getMinutes();
    	if(minute<10) minute="0"+minute;
    	var second = dt.getSeconds();
     	if(second<10) second="0"+second;
    	tdTm       = ""+hour+minute+second;
    	return tdTm;
    }
	 
    /* 날짜가 적용할 수 있는 날짜인지 체크 */
    function checkDateCanApply(disconDate){
    	console.log(disconDate);
    	if(disconDate<changeDayByMonth((getToday()*1)+7) || // 현재일 기준 D+7~30일 날짜만 입력 가능 
    	   disconDate>changeDayByMonth((getToday()*1)+30)){
    		alert("등록 및 변경을 할 수 없는 날짜입니다.");
    		return false;
    	} 
    	else{
    		return true;
    	} 
    }
	
    /* 단종날짜를 수정할 수 있는 날짜인지 체크  */
    function checkDateCanModified(disconDate){
    	if(disconDate != "단종정보가 없습니다." && dateToStrdate(disconDate)*1 < changeDayByMonth((getToday()*1)+7)){
    		alert("등록한 단종일자와 날짜가 가까워 변경하실 수 없습니다.");
    		return false;
    	} 
    	else{
    		return true;
    	} 
    }
    
    
    /* 단종날짜조회 태그 보이지 않게 */
    function setInitDisconDateRow(){
    	$("#disconDateRow *").remove();
    }
    
    /* 단종날짜조회 태그 보이게 */
    function appendDisconDateRow(){
    	var html = '<td></td>'+
				   '<td></td>'+
				   '<th><span class="star"></span>단종일자 검색</th>'+
				   '<td>' +
				   '<input type="text" id="disconDate" name="disconDate" 	value=\'<c:out value="${disconDate}" />\' readonly/>' +
				   	'<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal(\'searchForm.disconDate\');" style="cursor: hand;" />'+
				   	'<a href="#" class="btn" onclick="doSearch($(this).attr(\'value\'));" value="2" style="position: relative; left: 150px"><span>단종 날짜 검색</span></a>'+
				   '</td>'+
				   '<td></td>';
				   
	    $("#disconDateRow").append(html);	
    }
    
    /* 숫자만 입력 */
	function onlyNumber(event){
		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
			return;
		else
			return false;
	}
    
    /* 변경사유 체크되어있는 값 선택 */
    function selectDisconReason(data){
    	for(var i=0; i<data.length ;i++){
    		if(data[i].disconReason){
    			$("#disconReason"+i).val(data[i].disconReason);
    		}
    	}
    }
     
    
    /* 달력 날짜 변환 */
    function changeDayByMonth(toDate){
    	toDate = String(toDate);
    	
      	var year  = toDate.substring(0,4);
      	var month = toDate.substring(4,6);
      	var day   = toDate.substring(6,8);

      	if( ( month == '01' || month == '03' || month == '05' || month == '07' || month == '08' ||
             month == '10' || month == '12') && day > 31 ){  		
      		day   = Number(day) - 31;
      		month = Number(month) + 1;
          if(month==13){
            year = Number(year) + 1;
            month=1;
          }
      	}
      	else if( (month == '04' || month == '06' || month == '09' || month == '11') && day>30){  		
      		day   = Number(day) - 30;
         	month = Number(month) + 1;
      	}
        else if( month == '02' && day>28){
        	if( Number(year)%4 == 0 && day>29){
                day = Number(day) - 29;
                month = Number(month) + 1;
              }
              else if( Number(year)%4 != 0 ){
                day = Number(day) - 28;
                month = Number(month) + 1;
              }
        }
      	
        year = year + '';
        if(typeof(day)=='number' && day<10)day = '0' + day;
        if(typeof(month)=='number' && month<10)month = '0' + month;

        return year + month + day;
    }
</script>


</head>

<body>
	<div id="content_wrap">
		<div>
		<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post" action="#">
			<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />
			<input type="hidden" id="downloadFlag" name="downloadFlag" value="" />
<!-- 			<div id="wrap_menu"> -->
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">

						<ul class="tit">
							<li class="tit">상품단종등록</li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch($(this).attr('value'));" value="1"><span><spring:message code="button.common.inquire"/></span></a>
								<a href="#" class="btn" onclick="doSave();"><span><spring:message code="button.common.create"/></span></a>
								<a href="#" class="btn" onclick="doCancel();"><span>삭제</span></a>
								<%-- <a href="#" class="btn" onclick="doExcel();"><span><spring:message code="button.common.excel"/></span></a> --%>
							</li>
						</ul>

						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:10%;" />
								<col style="width:24%;" />
								<col style="width:15%;" />
								<col style="width:40%;" />
								<col style="width:20%;" />
							</colgroup
							<tr>

								<th><span class="star">*</span> 협력업체</th>
								<td>
									<c:choose>
										<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
											<c:if test="${not empty param.entpCd}">
												<input type="text" name="entpCd" id="entpCd" readonly="readonly" value="${param.entpCd}" style="width:40%;"/>
											</c:if>
											<c:if test="${empty param.entpCd}">
												<input type="text" name="entpCd" id="entpCd" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
											</c:if>

											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:when>

										<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
											<c:if test="${not empty param.entpCd}">
												<html:codeTag objId="entpCd" objName="entpCd" width="150px;" selectParam="<c:out value='${param.entpCd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
											</c:if>
											<c:if test="${ empty param.entpCd}">
												<html:codeTag objId="entpCd" objName="entpCd" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
											</c:if>
										</c:when>
									</c:choose>
								</td>
								<th><span class="star"></span> 판매(88)코드</th>
								<td><input type="text" id="srchSrcmkCd" onkeydown='return onlyNumber(event)' maxlength="13"/></td>
								<td>									
									<a href="#" class="btn" onclick="doSearch($(this).attr('value'));" value="3" style="position: relative; left: -2px" ><span>등록한 단종 정보 조회</span></a>
								</td>

							</tr>
							<tr id="disconDateRow">
							</tr>
						</table>
						
						<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
							<tr>
								<td colspan="4" bgcolor=ffffff>
									&nbsp;※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.
								</td>
							</tr>
						</table>
					</div>
					<!-- 1검색조건 // -->
				</div>

				<!--	2 검색내역 	-->
				<div class="wrap_con">
					<!-- list -->
					<div class="bbs_list">

						<ul class="tit" style="height :auto">
							<li class="tit" >검색내역
							&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							[ 단종 등록 및 변경 가능한 날짜  <span id="fromRegDate" style="color: blue"></span> 부터  <span id="toRegDate" style="color:blue"></span> 까지 가능 ]<br>
							&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							[ 기존등록 되어 있는 단종 일자가 <span id="fromImpDate" style="color: red"></span> 까지인 경우는 변경 불가능 ] 
							</li>
							
							
						</ul>
						<div style="width:100%; height:446px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">
							<table  cellpadding="1" cellspacing="1" border="0" width="820px" bgcolor=efefef>
								<colgroup>
									<col width="1%">
									<col width="3%">
									<col width="6%">
									<col width="4%">
									<col width="14%">
									<col width="6%">
									<col width="4%">		
									<col width="14%">
									<col width="5%">
									<col width="6%">
								</colgroup>

								<tr bgcolor="#e4e4e4">
									<th rowspan=2></th>
									<th rowspan=3>No.</th>
									<th rowspan=3>판매(88)코드</th>
									<th rowspan=3>상품코드</th>
									<th rowspan=3>상품명</th>
									<th rowspan=3>POG 진열점포 수</th>
									<th rowspan=3>변경사유</th>
									<th rowspan=3>단종 적용일</th>
									<th rowspan=3>단종 입력일자</th>
									<th rowspan=3>진행사항</th>
								</tr>

								<tbody id="dataListbody" />

							</table>
						</div>
					</div>
				</div>
			</div>

			<!-- Pagging Start ---------->
			<div id="paging_div">
		        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
			</div>
			<!-- Pagging End ---------->

		</form>

		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li>홈</li>
						<li>상품</li>
						<li>상품현황관리</li>
						<li class="last">상품단종등록</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

</body>
</html>
