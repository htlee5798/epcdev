<%--
	Page Name 	: NEDMPRO0300.jsp
	Description : 행사정보 등록내역 조회
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2025.03.07 		park jong gyu	 		최초생성
	
--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	
	$(document).ready(function() {
		
		
	});
	
	/* 조회 */
	function doSearch() {
		let searchInfo = {};
		
		$('#searchForm').find('input , select').map(function() {
			searchInfo[this.name] = $(this).val().replaceAll("-","");
		});
		
		//console.log(searchInfo);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			url : '<c:url value="/edi/product/selectProEventAppList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				
				//footer Setting 
				if(data.list.length > 0){
					// 조회 성공시 
					$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
				}else{
					//조회된 건수 없음
					$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
				}
				
				//json 으로 호출된 결과값을 화면에 Setting
				_setTbody(data.list);
			},
			error : function(request, status, error, jqXHR) {
				// 요청처리를 실패하였습니다.
				$('#resultMsg').text('msg.common.fail.request');
			} 
		});	
	}
	
	function _setTbody(json) {
		setTbodyInit("dataListbody");	// dataList 초기화
		
		let data = json;
		
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging-ul").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 20);
			$("#paging-ul").html("");
		}
	}
	
	// excel down
	function doExcel(){
		
		if($('#dataListbody tr').length > 0){
			$("#searchForm").attr("target", "frameForDownload");
			$("#searchForm").attr("method", "POST");
			$("#searchForm").attr("action", '<c:url value="/edi/product/selectProEventAppExcelDown.do"/>');
			$("#searchForm").submit();
		}else{
			alert('조회결과가 없습니다.');
		}
	}
	
	// 상세보기
	function btnEvent(gbn, reqOfrcd, lifnr){
		// gbn
		// Detail : 상세, orgPri : 원매가행사신청, pro : 판촉행사신청, reqMd : MD 요청
		
		let  f = document.hiddenForm;
		$("#hiddenForm input[name='reqOfrcd']").val( reqOfrcd ); // 행사코드
		$("#hiddenForm input[name='lifnr']").val( lifnr ); // 파트너사코드
		$("#hiddenForm input[name='reqContyp']").val( reqContyp ); // 합의서 코드
		$("#hiddenForm input[name='taskGbn']").val( gbn ); // 구분 코드
    	let url = "<c:url value='/edi/product/selectProEventAppDetail.do' />";
		
		if(gbn == 'Detail'){
			// 상세 보기
			
		}else if(gbn == 'orgPri'){
			// 원매가 행사 신청
			
		}else if(gbn == 'pro'){
			// 판촉 행사 신청
			
		}else if(gbn == 'reqMd'){
			// md 요청
			
		}
		
		f.action = url;
    	f.submit();
		
	}
	
	// Ecs 계약서 호출
	function ecsDocEvent(dcNum){
		Common.centerPopupWindow( "<c:url value='/edi/product/selectEcsDocInfo.do'/>?dcNum="+dcNum
				, 'popup', {width: 1500, height: 1050});
	}
	
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1"  bgcolor=ffffff>

		<td align="center">
			<a href="javascript:btnEvent('Detail','<c:out value="\${reqOfrcd}"/>', '<c:out value="\${lifnr}"/>' )">
				<c:out value="\${reqOfrcd}"/>
			</a>
		</td>
		<td align="left"><c:out value="\${reqOfrTxt}" /></td>
		<td align="center"><c:out value="\${vkorgTxt}" /></td>
		<td align="center"><c:out value="\${apprStatus}" /></td>
		<td align="center"><c:out value="\${regDate}" /></td>
		<td align="center"><c:out value="\${lifnrTxt}" /></td>
		<td align="center"><c:out value="\${prsdt}" /></td>
		<td align="center"><c:out value="\${predt}" /></td>
		<td align="center"><c:out value="\${ofsdt}" /></td>
		<td align="center"><c:out value="\${ofedt}" /></td>
		<td align="center"><c:out value="\${reqTypeTxt}" /></td>
		<td align="center"><c:out value="\${reqDiscTxt}" /></td>
		<td align="center"><c:out value="\${itmeProdCnt}" /></td>
		<td align="center"><c:out value="\${itmeApprProdCnt}" /></td>
		
		<td align="center">
			<a href="javascript:ecsDocEvent('<c:out value="\${docNo1}" />' )">
				<c:out value="\${docNo1}" />
			</a>
		</td>
		<td align="center">
			<a href="javascript:ecsDocEvent('<c:out value="\${docNo2}" />' )">
				<c:out value="\${docNo2}" />
			</a>
		</td>
		<td align="center">
			<a href="javascript:ecsDocEvent('<c:out value="\${docNo3}" />' )">
				<c:out value="\${docNo3}" />
			</a>
		</td>

		<input type="hidden" id="reqOfrcd" 			name="reqOfrcd" 	value="<c:out value="\${reqOfrcd}" />" 		/> 	    	    	
		<input type="hidden" id="lifnr" 			name="lifnr" 		value="<c:out value="\${lifnr}" />" 		/> 		      		      		
		<input type="hidden" id="vkorg" 			name="vkorg" 		value="<c:out value="\${vkorg}" />" 		/> 		      		      		
		<input type="hidden" id="vkorgTxt" 			name="vkorgTxt" 	value="<c:out value="\${vkorgTxt}" />" 		/> 	    	    	
		<input type="hidden" id="reqPurTxt" 		name="reqPurTxt" 	value="<c:out value="\${reqPurTxt}" />" 	/> 	   	   	
		<input type="hidden" id="reqOfrTxt" 		name="reqOfrTxt" 	value="<c:out value="\${reqOfrTxt}" />" 	/> 	   	   	
		<input type="hidden" id="zdeal" 			name="zdeal" 		value="<c:out value="\${zdeal}" />" 		/> 		      		      		
		<input type="hidden" id="zdealTxt" 			name="zdealTxt" 	value="<c:out value="\${zdealTxt}" />" 		/> 	    	    	
		<input type="hidden" id="reqType" 			name="reqType" 		value="<c:out value="\${reqType}" />" 		/> 		    		    		
		<input type="hidden" id="reqTypeTxt" 		name="reqTypeTxt" 	value="<c:out value="\${reqTypeTxt}" />" 	/> 	  	  	
		<input type="hidden" id="reqDisc" 			name="reqDisc" 		value="<c:out value="\${reqDisc}" />" 		/> 		    		    		
		<input type="hidden" id="reqDiscTxt" 		name="reqDiscTxt" 	value="<c:out value="\${reqDiscTxt}" />" 	/> 	  	  	
		<input type="hidden" id="costType" 			name="costType" 	value="<c:out value="\${costType}" />" 		/> 	    	    	
		<input type="hidden" id="costTypeTxt" 		name="costTypeTxt" 	value="<c:out value="\${costTypeTxt}" />" 	/> 	 	 	
		<input type="hidden" id="reqContyp" 		name="reqContyp" 	value="<c:out value="\${reqContyp}" />" 	/> 	   	   	
		<input type="hidden" id="reqContypTxt" 		name="reqContypTxt" value="<c:out value="\${reqContypTxt}" />" 	/>   
		<input type="hidden" id="hdVenRate" 		name="hdVenRate" 	value="<c:out value="\${hdVenRate}" />" 	/> 	   	   	
		<input type="hidden" id="ofrCost" 			name="ofrCost" 		value="<c:out value="\${ofrCost}" />" 		/> 		    		    		
		<input type="hidden" id="ofsdt" 			name="ofsdt" 		value="<c:out value="\${ofsdt}" />" 		/> 		      		      		
		<input type="hidden" id="ofedt" 			name="ofedt" 		value="<c:out value="\${ofedt}" />" 		/> 		      		      		
		<input type="hidden" id="prsdt" 			name="prsdt" 		value="<c:out value="\${prsdt}" />" 		/> 		      		      		
		<input type="hidden" id="predt" 			name="predt" 		value="<c:out value="\${predt}" />" 		/> 		      		      		
		<input type="hidden" id="werksType" 		name="werksType" 	value="<c:out value="\${werksType}" />" 	/> 	   	   	
		<input type="hidden" id="werksTypeTxt" 		name="werksTypeTxt" value="<c:out value="\${werksTypeTxt}" />" 	/>   
		<input type="hidden" id="werksEtcTxt" 		name="werksEtcTxt" 	value="<c:out value="\${werksEtcTxt}" />" 	/> 	 	 	
		<input type="hidden" id="reqOfrDesc" 		name="reqOfrDesc" 	value="<c:out value="\${reqOfrDesc}" />" 	/> 	  	  	
		<input type="hidden" id="docNo1" 			name="docNo1" 		value="<c:out value="\${docNo1}" />" 		/> 		      		      		
		<input type="hidden" id="docNo2" 			name="docNo2" 		value="<c:out value="\${docNo2}" />" 		/> 		     		     		
		<input type="hidden" id="docNo3" 			name="docNo3" 		value="<c:out value="\${docNo3}" />" 		/> 		     		     		
		<input type="hidden" id="waers" 			name="waers" 		value="<c:out value="\${waers}" />" 		/> 		      		      		
		<input type="hidden" id="apprStatus" 		name="apprStatus" 	value="<c:out value="\${apprStatus}" />" 	/> 	  	  	
		<input type="hidden" id="docType" 			name="docType" 		value="<c:out value="\${docType}" />" 		/> 		    		    		
		<input type="hidden" id="docDate" 			name="docDate" 		value="<c:out value="\${docDate}" />" 		/>            
		
	</tr>
</script>

</head>

<body>
	<div id="content_wrap">
		<div>
		<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post" action="#">
			<input type="hidden" id="downloadFlag" name="downloadFlag" value="" />
			<div id="wrap_menu">
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						
						<ul class="tit">
							<li class="tit">행사정보 등록내역 조회</li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							</li>
						</ul>
						
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:10%;" />
								<col style="width:20%;" />
								<col style="width:10%;" />
								<col style="width:20%;" />
							</colgroup>
							<tr>
								<th>계열사</th>
								<td>
									<select id="vkorg" name="vkorg" class="required" style="width:150px;">
										<option value="ALL" >전체</option>
										<option value="KR02" >롯데마트</option>
										<option value="KR04" >롯데슈퍼</option>
									</select>
								</td>
								<th>행사기간</th>
								<td>
									<input type="text" class="day" name="ofsdt" id="ofsdt" style="width:80px;" value="<c:out value='${firstDay}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.ofsdt');" style="cursor:hand;" />
									~
									<input type="text" class="day" name="ofedt" id="ofedt" style="width:80px;" value="<c:out value='${srchEndDt}'/>" disabled> 	<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.ofedt');"  style="cursor:hand;" />
								</td>
							</tr>
							<!-- <tr>
								<th>요청일자</th>
								<td>
									<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${srchStartDt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;" />
									~
									<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${today}'/>" disabled> 	<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
								</td>
							</tr>  -->
							<tr>
								<th>행사유형</th>
								<td>
									<html:codeTag objId="reqType" objName="reqType" width="150px;"  parentCode="REQFG"  comType="SELECT" orderSeqYn="Y" defName="전체" defValue="ALL" formName="form"/>
								</td>
								<th>할인유형</th>
								<td>
									<html:codeTag objId="reqDisc" objName="reqDisc" width="150px;"  parentCode="REQDC"  comType="SELECT" orderSeqYn="Y" defName="전체" defValue="ALL" formName="form"/>
								</td>
							</tr>
							<tr style="display: none;">
								<th>파트너사코드</th>
								<td>
									<input type="text" name="lifnr" id="lifnr" style="width: 98%;"/>
								</td>
								<th>파트너사명</th>
								<td>
									<input type="text" name="lifnrTxt" id="lifnrTxt" style="width: 98%;"/>
								</td>
							</tr>
							<tr>
								<th>상태</th>
								<td>
									<select id="apprStatus" name="apprStatus" class="required" style="width:150px;">
										<option value="ALL" >전체</option>
										<option value="01" >요청대기</option>
										<option value="02" >요청완료</option>
										<option value="03" >MD승인</option>
										<option value="04" >행사등록</option>
										<option value="05" >전자결재진행</option>
										<!-- <option value="06" >행사반려</option> -->
										<option value="07" >행사확정</option>
										<option value="44" >계약삭제</option>
										<option value="98" >인터페이스 계약삭제</option>
										<option value="99" >계약폐기</option>
									</select>
								</td>
								<th>행사명</th>
								<td>
									<input type="text" name="reqOfrTxt" id="reqOfrTxt" style="width: 98%;"/>
								</td>
							</tr>
							
						</table>
					</div>
					<!-- 1검색조건 // -->
				</div>
			</div>
			</form>
			
				<!--	2 검색내역 	-->
				<div class="wrap_con">
					<!-- list -->
					<div class="bbs_list">
					
						<ul class="tit">
							<li class="tit">검색내역</li>
							<li class="btn"><a href="#" class="btn" onclick="btnEvent('pro');"><span>판촉행사신청</span></a></li>
							<li class="btn"><a href="#" class="btn" onclick="btnEvent('orgPri');"><span>원매가행사신청</span></a></li>
							<%-- <li class="btn"><a href="#" class="btn" onclick="doExcel('down');"><span><spring:message code="button.common.excel"/></span></a></li> --%>
						</ul>
						<div style="width:100%; height:446px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
							<table  cellpadding="1" cellspacing="1" border="0" width=2000 bgcolor=efefef>
								<colgroup>
									<col width="60px"/>
									<col width="120px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>	
									<col width="60px"/>
								</colgroup>
								
								<tr bgcolor="#e4e4e4">
									<th>파트너사<br/>행사코드</th>
									<th>행사명</th>
									<th>계열사</th>
									<th>상태</th>
									<th>등록일자</th>
									<th>파트너사명</th>
									<th>발주시작일</th>
									<th>발주종료일</th>
									<th>행사시작일</th>
									<th>행사종료일</th>
									<th>행사유형</th>
									<th>할인유형</th>
									<th>상품수</th>
									<th>승인 상품수</th>
									<th>롯데마트<br/>공문</th>
									<th>롯데슈퍼<br/>공문</th>
									<th>CS공문</th>
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
			
		
		<iframe name="frameForDownload" style="width:0%;height:0%;"></iframe>
		
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li>홈</li>
						<li>상품</li>
						<li>행사제안</li>
						<li class="last">행사정보 등록내역 조회</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	
	<!-- hiddenForm ------------------------------------------------------>
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" id="reqOfrcd" 			name="reqOfrcd" 	/> 	    	    	
		<input type="hidden" id="lifnr" 			name="lifnr" 		/> 		      		      		
		<input type="hidden" id="vkorg" 			name="vkorg" 		/> 		      		      		
		<input type="hidden" id="vkorgTxt" 			name="vkorgTxt" 	/> 	    	    	
		<input type="hidden" id="reqPurTxt" 		name="reqPurTxt" 	/> 	   	   	
		<input type="hidden" id="reqOfrTxt" 		name="reqOfrTxt" 	/> 	   	   	
		<input type="hidden" id="zdeal" 			name="zdeal" 		/> 		      		      		
		<input type="hidden" id="zdealTxt" 			name="zdealTxt" 	/> 	    	    	
		<input type="hidden" id="reqType" 			name="reqType" 		/> 		    		    		
		<input type="hidden" id="reqTypeTxt" 		name="reqTypeTxt" 	/> 	  	  	
		<input type="hidden" id="reqDisc" 			name="reqDisc" 		/> 		    		    		
		<input type="hidden" id="reqDiscTxt" 		name="reqDiscTxt" 	/> 	  	  	
		<input type="hidden" id="costType" 			name="costType" 	/> 	    	    	
		<input type="hidden" id="costTypeTxt" 		name="costTypeTxt" 	/> 	 	 	
		<input type="hidden" id="reqContyp" 		name="reqContyp" 	/> 	   	   	
		<input type="hidden" id="reqContypTxt" 		name="reqContypTxt" />   
		<input type="hidden" id="hdVenRate" 		name="hdVenRate" 	/> 	   	   	
		<input type="hidden" id="ofrCost" 			name="ofrCost" 		/> 		    		    		
		<input type="hidden" id="ofsdt" 			name="ofsdt" 		/> 		      		      		
		<input type="hidden" id="ofedt" 			name="ofedt" 		/> 		      		      		
		<input type="hidden" id="prsdt" 			name="prsdt" 		/> 		      		      		
		<input type="hidden" id="predt" 			name="predt" 		/> 		      		      		
		<input type="hidden" id="werksType" 		name="werksType" 	/> 	   	   	
		<input type="hidden" id="werksTypeTxt" 		name="werksTypeTxt" />   
		<input type="hidden" id="werksEtcTxt" 		name="werksEtcTxt" 	/> 	 	 	
		<input type="hidden" id="reqOfrDesc" 		name="reqOfrDesc" 	/> 	  	  	
		<input type="hidden" id="docNo1" 			name="docNo1" 		/> 		      		      		
		<input type="hidden" id="docNo2" 			name="docNo2" 		/> 		     		     		
		<input type="hidden" id="docNo3" 			name="docNo3" 		/> 		     		     		
		<input type="hidden" id="waers" 			name="waers" 		/> 		      		      		
		<input type="hidden" id="apprStatus" 		name="apprStatus" 	/> 	  	  	
		<input type="hidden" id="docType" 			name="docType" 		/> 		    		    		
		<input type="hidden" id="docDate" 			name="docDate" 		/>
		<input type="hidden" id="taskGbn" 			name="taskGbn" 		/>
	</form>
	<!-- hiddenForm ------------------------------------------------------>
</body>
</html>
