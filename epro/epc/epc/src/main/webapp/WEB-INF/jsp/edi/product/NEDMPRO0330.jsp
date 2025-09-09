<%--
	Page Name 	: NEDMPRO0330.jsp
	Description : 약정서 화면 
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    	-------------------------------------
	2025.03.20  PARK JONG GYU 					최초생성		
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<title></title>
<style type="text/css">
	/* TAB */
.tabs {
	height: 31px;
	background: #fff;
}

.tabs ul {
	width: 100%;
	height: 31px;
}

.tabs li {
	float: left;
	width: 130px;
	height: 29px;
	background: #fff;
	border: 1px solid #ccd0d9;
	border-radius: 2px 2px 0 0;
	font-size: 12px;
	color: #666;
	line-height: 30px;
	text-align: center;
}

.tabs li.on {
	border-bottom: #e7eaef 1px solid;
	color: #333;
	font-weight: bold;
}

.tabs li a {
	display: block;
	color: #666;
}

.tabs li.on a {
	color: #333;
	font-weight: bold;
}
	
.img {
	height: 14px;
}	

</style>
<script type="text/javascript" >
let trCount = 100;
let rowNum;

/* dom이 생성되면 ready method 실행 */
$(document).ready(function() {

	//----- 검색조건 협력업체 Default 설정.
	let srchEntpCd = "<c:out value='${param.srchEntpCd}'/>";  //검색조건 협력업체코드
	let repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";  //관리자로 로그인 해서 협력업체 갈아타기 로그인시 협력업체 코드
	if (srchEntpCd.replace(/\s/gi, '') ==  "") {
		$("#searchForm #srchEntpCd").val(repVendorId);
	} else {
		$("#searchForm #srchEntpCd").val(srchEntpCd);
	}
	
	//-- 탭 클릭 이벤트 --------------------
	$("#prodTabs li").click(function() {
		let tabId = this.id;
		
		// 제안 탭
		if (tabId == "pro01") {									
			$('#pro01').addClass("on");
			$('#pro02').removeClass("on");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0320.do'/>");
			$("#hiddenForm").attr("method", "post").submit();
		// 약정서 탭	
		} else if (tabId == "pro02") {
			$('#pro01').removeClass("on");
			$('#pro02').addClass("on");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0330.do'/>");			
			$("#hiddenForm").attr("method", "post").submit(); 
		}else if(tabId == "pro03"){
			$('#pro03').addClass("on");
			$('#pro04').removeClass("on");
			$('.prodTable').show();
			$('.strProdTable').hide();
		}else if(tabId == "pro04"){
			$('#pro03').removeClass("on");
			$('#pro04').addClass("on");
			$('.prodTable').hide();
			$('.strProdTable').show();
		}
	});
	
	// 약정서 목록 click!
	$(document).on("click","#tDataListbody tr td", function(){
		let objClass = $.trim($(this).attr('class'));
		let conNo = $.trim($(this).closest('tr').find('input[name=conNo]').val());
		let searchInfo = {'conNo' : conNo};
		switch(objClass) {
			case 'conNo' : eventProdSearch(searchInfo); break; // enter
			default : return;
		}
		
	});
	
	
});


/* 조회 */
function btnSearch(pageIndex) {
	let searchInfo = {};
	
	$('#searchForm').find('input , select').map(function() {
		searchInfo[this.name] = $(this).val().replace(/[^0-9]/g, "");
	});
	
	//console.log(searchInfo);
	
    $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectProdRtnDocList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			$("#pageIndex").val(pageIndex);
			
			//json 으로 호출된 결과값을 화면에 Setting
			_setTbody(data);
		}
	});	
    
}

/* List Data 셋팅 */
function _setTbody(json) {
	let tDataList = json.tDataList;			//리스트 데이터 T_DATA   
	
	setTbodyInit("tDataListbody"); // tDataList 초기화
	setTbodyInit("tMatnrListbody"); // tMatnrList 초기화
	setTbodyInit("tWerksListbody"); // tWerksList 초기화
		
	if (tDataList.length > 0) {
		
		// 데이터에 count 추가 (인덱스 역할)
        for (let i = 0; i < tDataList.length; i++) {
            //json[i].count = i + 1; // 1부터 시작하도록 설정
            tDataList[i].rowAttri = 'search';
            tDataList[i].trCount = i;
        }
        
        $("#tDataListTemplate").tmpl(tDataList).appendTo("#tDataListbody");
		
   	}else{
   		setTbodyNoResult("tDataListbody", 8);
   		setTbodyNoResult("tMatnrListbody", 11);
   		setTbodyNoResult("tWerksListbody", 11);
   	}
}

/* row 선택 조회 */
function eventProdSearch(searchInfo) {
	
	//console.log(searchInfo);
	
    $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectProdRtnCntrStrProdList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			$("#pageIndex").val(pageIndex);
			
			//json 으로 호출된 결과값을 화면에 Setting
			_setProdTbody(data);
		}
	});	
    
}

/* List Data 셋팅 */
function _setProdTbody(json) {
	tMatnrList = json.tMatnrList;		//리스트 데이터 T_MATNR  
	tWerksList = json.tWerksList;		//리스트 데이터 T_WERKS  
	
	setTbodyInit("tMatnrListbody"); // tMatnrList 초기화
	setTbodyInit("tWerksListbody"); // tWerksList 초기화
	
	if (tMatnrList.length > 0) {
		// 데이터에 count 추가 (인덱스 역할)
        for (let i = 0; i < tMatnrList.length; i++) {
            //json[i].count = i + 1; // 1부터 시작하도록 설정
            tMatnrList[i].rowAttri = 'search';
            tMatnrList[i].trCount = i;
        }
        
        $("#tMatnrListTemplate").tmpl(tMatnrList).appendTo("#tMatnrListbody");
		
   	}else{
   		setTbodyNoResult("tMatnrListbody", 11);
   	}
	
	if (tWerksList.length > 0) {
		// 데이터에 count 추가 (인덱스 역할)
        for (let i = 0; i < tWerksList.length; i++) {
            //json[i].count = i + 1; // 1부터 시작하도록 설정
            tWerksList[i].rowAttri = 'search';
            tWerksList[i].trCount = i;
        }
        
        $("#tWerksListTemplate").tmpl(tWerksList).appendTo("#tWerksListbody");
		
   	}else{
   		setTbodyNoResult("tWerksListbody", 11);
   	}
}
</script>

<!-- DATA LIST -->
<script id="tDataListTemplate" type="text/x-jquery-tmpl">
<tr class="tr\${trCount}"  bgcolor=ffffff>
	<%--
	<td align="center">
		로그인????
	</td>
	--%>
	<td align="center" class="conNo">
		<a href="#"><c:out value="\${conNo}"/></a>
	</td>
	<td align="center">
		<c:out value="\${dcStatTxt}"/>
	</td>
	<td align="center">
		<c:out value="\${lifnr}"/>
	</td>
	<td align="center">
		<c:out value="\${venNm}"/>
	</td>
	<td align="center">
		<c:out value="\${teamNm}"/>
	</td>
	<td align="center">
		<c:out value="\${ekorg}"/>
	</td>
	<td align="center">
		<c:out value="\${zrtdate}"/>
	</td>
	<td align="center">
		<c:out value="\${apprFgNm}"/>
	</td>
	<input type="hidden" name="rowAttri" value="\${rowAttri}"  />
	<input type="hidden" name="dcNum" value="\${dcNum}"  />
	<input type="hidden" name="dcStat" value="\${dcStat}"  />
	<input type="hidden" name="depCd" value="\${depCd}"  />
	<input type="hidden" name="apprFg" value="\${apprFg}"  />
	<input type="hidden" name="conNo" value="\${conNo}"  />
</tr>
</script>

<!-- DATA LIST -->
<script id="tMatnrListTemplate" type="text/x-jquery-tmpl">
<tr class="tr\${trCount}"  bgcolor=ffffff>
	<!-- <td align="center">
		<c:out value="\${seq}"/>
	</td> -->
	<td align="center">
		<c:out value="\${ean11}"/>
	</td>
	<td align="center">
		<c:out value="\${prodNm}"/>
	</td>
	<td align="center">
		<c:out value="\${zzprodStd}"/>
	</td>
	<td align="center">
		<c:out value="\${netpr}"/>
	</td>
	<td align="center">
		<c:out value="\${menge}"/>
	</td>
	<td align="center">
		<c:out value="\${dmbtr}"/>
	</td>
	<input type="hidden" name="rowAttri" value="\${rowAttri}"  />
	<input type="hidden" name="conNo" value="\${conNo}"  />
	<input type="hidden" name="prodCd" value="\${prodCd}"  />
</tr>
</script>

<!-- DATA LIST -->
<script id="tWerksListTemplate" type="text/x-jquery-tmpl">
<tr class="tr\${trCount}"  bgcolor=ffffff>
	<!-- <td align="center">
		<c:out value="\${seq}"/>
	</td> -->
	<td align="center">
		<c:out value="\${werks}"/>
	</td>
	<td align="center">
		<c:out value="\${ean11}"/>
	</td>
	<td align="center">
		<c:out value="\${prodNm}"/>
	</td>
	<td align="center">
		<c:out value="\${zzprodStd}"/>
	</td>
	<td align="center">
		<c:out value="\${netpr}"/>
	</td>
	<td align="center">
		<c:out value="\${menge}"/>
	</td>
	</td>
	<td align="center">
		<c:out value="\${dmbtr}"/>
	</td>
	</td>
	<td align="center">
		<c:out value="\${dcStat}"/>
	</td>
	</td>
	<td align="center">
		<c:out value="\${zretypNm}"/>
	</td>
	<td align="center">
		<c:out value="\${zretypDNm}"/>
	</td>
	<td align="center">
		<c:out value="\${zrelocNm}"/>
	</td>
	<input type="hidden" name="rowAttri" value="\${rowAttri}"  />
	<input type="hidden" name="conNo" value="\${conNo}"  />
	<input type="hidden" name="prodCd" value="\${prodCd}"  />
	<input type="hidden" name="werks" value="\${werks}"  />
</tr>
</script>
</head>
<body>
	<div id="content_wrap">
		<!-- tab 구성---------------------------------------------------------------->
		<div id="prodTabs" class="tabs" style="padding-top: 10px;">
			<ul>
				<li id="pro01" style="cursor: pointer;">제안</li>	<!-- 제안 -->
				<li id="pro02" style="cursor: pointer;" class="on">약정서</li>	<!-- 약정서 -->
			</ul>
		</div>
		<!-- tab 구성---------------------------------------------------------------->
		<div>
			<form name="searchForm" id="searchForm">
				<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />"  />
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">검색조건</li>
								<li class="btn">
									<a href="#" class="btn" onclick="btnSearch('1')"><span>조회</span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:15%" />
									<col style="width:30%" />
									<col style="width:15%" />
									<col style="*" />
								</colgroup>
								<tr>
									<th>파트너사</th>
									<td>
										<html:codeTag objId="iLifnr" objName="iLifnr" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
									</td>
									<th><span class="star">*</span>MD요청서 생성일</th>
									<td>
										<input type="text" class="day" name="iRgdatFrom" id="iRgdatFrom" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.iRgdatFrom');" style="cursor:hand;" />
										~
										<input type="text" class="day" name="iRgdatTo" id="iRgdatTo" style="width:80px;" value="<c:out value='${srchEndDt}'/>"> 	<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.iRgdatTo');"  style="cursor:hand;" />
									</td>
								</tr>
								<tr>
									<th>약정진행상태</th>
									<td>
										<div style="float:left; id="srchIngStatus">
											<select name="iDcStat" id="iDcStat">
												<option value="">전체</option>
												<option value="10">작성중</option>
												<option value="11">작성완료</option>
												<option value="40">발송</option>
												<option value="50">업체열람</option>
												<option value="60">업체반려</option>
												<option value="70">계약회수</option>
												<option value="80">업체서명</option>
												<option value="90">계약완료</option>
												<option value="99">계약폐기</option>
												<option value="44">계약삭제</option>
												<option value="98">인터페이스 계약 삭제</option>
											</select>
										</div>
									</td>
									<th>팀</th>
									<td>
										<select id="iDepCd" name="iDepCd" class="required" style="width: 150px;">
											<option value="">
												<spring:message code="button.common.choice" /></option>
												<c:forEach items="${teamList}" var="teamList" varStatus="index">
													<option value="${teamList.teamCd}">${teamList.teamNm}</option>
												</c:forEach>
										</select>
									</td>
								</tr>
							</table>
						</div>
					</div>
					</div>
				</form>
				<form name="reqDetailForm" id="reqDetailForm">
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">약정서 목록 </li>
								<li class="btn">
								</li>
							</ul>
							<div style="width:100%; height:270px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1000 bgcolor=efefef>
									<colgroup>
<!-- 										<col style="width:5%"/> -->
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:10%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
<!-- 										<th>ECS</th> -->
										<th>약정서번호</th>
										<th>약정진행상태</th>
										<th>파트너사코드</th>
										<th>파트너사명</th>
										<th>팀명</th>
										<th>구매조직</th>
										<th>MD요청생성일</th>
										<th>전자결재상태</th>
									</tr>
									<tbody id="tDataListbody" />
								</table>
							</div>
						</div>
					</div>
		
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">반품상품 목록</li>
								<li class="btn">
								</li>
							</ul>
							<!-- tab 구성---------------------------------------------------------------->
							<div id="prodTabs" class="tabs" style="padding-top: 10px;">
								<ul>
									<li id="pro03" style="cursor: pointer;" class="on">상품별</li>
									<li id="pro04" style="cursor: pointer;">점포 & 상품별</li>
								</ul>
							</div>
							<!-- tab 구성---------------------------------------------------------------->
							<div class="prodTable" style="width:100%; height:270px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1300 bgcolor=efefef>
									<colgroup>
										<!-- <col style="width:5%"/> -->
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:10%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
										<!-- <th>순번</th> -->
										<th>판매코드</th>
										<th>상품명</th>
										<th>규격</th>
										<th>단가</th>
										<th>수량</th>
										<th>금액</th>
									</tr>
									<tbody id="tMatnrListbody" />
								</table>
							</div>
							
							<div class="strProdTable" style="width:100%; height:270px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap; display: none;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1300 bgcolor=efefef>
									<colgroup>
										<!-- <col style="width:5%"/> -->
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:10%"/>
										<col style="width:10%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:10%"/>
										<col style="width:15%"/>
										<col style="width:10%"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
										<!-- <th>순번</th> -->
										<th>점포/센터코드</th>
										<th>판매코드</th>
										<th>상품명</th>
										<th>규격</th>
										<th>단가</th>
										<th>수량</th>
										<th>금액</th>
										<th>진행상태</th>
										<th>반품사유</th>
										<th>반품상세사유</th>
										<th>반품장소</th>
									</tr>
									<tbody id="tWerksListbody" />
								</table>
							</div>
							
						</div>
					</div>
				</form>
			</div>
			<div id="footer">
					<div id="footbox">
						<div class="msg" id="resultMsg"></div>
						<div class="notice"></div>
						<div class="location">
							<ul>
								<li>홈</li>
								<li>상품</li>
								<li>상품현황관리</li>
								<li class="last">반품 제안 등록(약정서)</li>
							</ul>
						</div>
					</div>
				</div>
		</div>
	</div>
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" id="prodRtnNo" 	name="prodRtnNo"  value=""	/> 	    	    	
		<input type="hidden" id="seq" 			name="seq"  value=""	/> 	    	    	
	</form>
	
</body>
</html>
