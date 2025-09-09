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
	
});


/* 조회 */
function btnSearch(pageIndex) {
	let searchInfo = {};
	
	$('#searchForm').find('input , select').map(function() {
		searchInfo[this.name] = $(this).val();
	});
	
	//console.log(searchInfo);
	
    $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectProdRtnItemList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			$("#pageIndex").val(pageIndex);
			
			//json 으로 호출된 결과값을 화면에 Setting
			_setTbody(data.list);
		}
	});	
    
}

/* List Data 셋팅 */
function _setTbody(json) {
	let data = json;
	setTbodyInit("dataListbody"); // dataList 초기화

   	if (json.length > 0) {
        // 데이터에 count 추가 (인덱스 역할)
        for (let i = 0; i < data.length; i++) {
            //json[i].count = i + 1; // 1부터 시작하도록 설정
            data[i].rowAttri = 'search';
            data[i].trCount = i;
        }
        
        $("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		$("select[name='teamCd']").append($("#srcTeamCd > option").clone());
		$("select[name='rtnResnPlce']").append($("#srcRtnResnPlce > option").clone());
		$("select[name='rtnResn']").append($("#srcRtnResn > option").clone());
		$("select[name='rtnResnDtl']").append($("#srcRtnResnDtl > option").clone());
		$("select[name='teamCd'] option:contains('선택')").remove();
		
		for (let i = 0; i < json.length; i++) {
			$(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").empty(); // 반품 상세 사유
			
			if( data[i].rtnResn == '01' )	$(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='01']").clone());
			else if( data[i].rtnResn == '02' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='02']").clone());
			else if( data[i].rtnResn == '03' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='03']").clone());
			else if( data[i].rtnResn == '04' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='04'], #srcRtnResnDtl option[value='05']").clone());
			
			$(".tr"+data[i].trCount).find("select[name='teamCd']").val( data[i].teamCd );
			$(".tr"+data[i].trCount).find("select[name='rtnResnPlce']").val( data[i].rtnResnPlce );
			$(".tr"+data[i].trCount).find("select[name='rtnResn']").val( data[i].rtnResn );
			$(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").val( data[i].rtnResnDtl );
        }
		
   	}else{
   		setTbodyNoResult("dataListbody", 11);
   	}
	
   	
}


/* 이 펑션은 협력업체 검색창에서 호출하는 펑션임    */
function setVendorInto(strVendorId, strVendorNm, strCono) {
	$("#srchEntpCd").val(strVendorId);
}

</script>
<!-- DATA LIST -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="tr\${trCount}"  bgcolor=ffffff>
	<td align="center">
		<input type="checkbox" name="chk" value="" style="height: 20px;"  />
	</td>
	<td align="center">
		<input type="text" name="rtnRegDy" id="rtnRegDy\${trCount}"  value="<c:out value="\${rtnRegDy}"/>" style="width: 80px;" disabled />
		<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor: pointer;" />
	</td>
	<td align="center">
		<c:out value="\${teamNm}"/>
	</td>
	<td align="center">
		<input type="text" name="srcmkCd" value="\${srcmkCd}" style="width: 85%;" disabled/><i class="bi bi-search" style='margin-left: 5px; cursor: pointer;' onclick='btnEvent("srcBarcode", this)' ></i>
	</td>
	<td align="center">
		<input type="text" name="maktx" value="\${maktx}" style="width: 99%;" disabled/>
	</td>
	<td align="center" class="rtnClsDy">
		<c:out value="\${rtnClsDy}"/>
	</td>
	<td align="center">
		<c:out value="\${reqMdDy}"/>
	</td>
	<td align="center">
		<c:out value="\${prdtStatusNm}"/>
	</td>
	<td align="center">
		<select id="rtnResn" name="rtnResn" class="required" style="width:130px;" onchange="javascript:chgEvent(this);" />
	</td>
	<td align="center">
		<select id="rtnResnDtl" name="rtnResnDtl" class="required" style="width:130px;" />
	</td>
	<td align="center">
		<select id="rtnResnPlce" name="rtnResnPlce" class="required" style="width:130px;" />
	</td>
	<input type="hidden" name="rowAttri" value="\${rowAttri}"  />
	<input type="hidden" name="prodCd" value="\${prodCd}"  />
	<input type="hidden" name="venCd" value="\${venCd}"  />
	<input type="hidden" name="teamCd" value="\${teamCd}"  />
	<input type="hidden" name="prdtStatus" value="\${prdtStatus}"  />
	<input type="hidden" name="prodRtnNo" value="\${prodRtnNo}"  />
	<input type="hidden" name="seq" value="\${seq}"  />
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
										<%-- <c:if test="${epcLoginVO.vendorTypeCd eq '06'}">
											<input type="text" name="srchEntpCd" id="srchEntpCd" readonly="readonly" readonly="readonly" style="width:40%;" />
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:if>
										<c:if test="${epcLoginVO.vendorTypeCd ne '06'}">
											<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="150px;"  dataType="CP" comType="SELECT" formName="form" defName="전체" />
										</c:if> --%>
										<html:codeTag objId="srcVenCd" objName="srcVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
									</td>
									<th><span class="star">*</span>MD요청서 생성일</th>
									<td>
										<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;" />
										~
										<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${srchEndDt}'/>"> 	<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
									</td>
								</tr>
								<tr>
									<th>약정진행상태</th>
									<td>
										<div style="float:left; id="srchIngStatus">
											<select name="srcStatus" id="srcStatus">
												<option value="ALL">전체</option>
												<option value="1">파트너사등록</option>
												<option value="2">MD 협의요청</option>
												<option value="3">MD 승인</option>
												<option value="4">MD 반려</option>
											</select>
										</div>
									</td>
									<th>팀</th>
									<td>
										<select id="srcTeamCd" name="srcTeamCd" class="required" style="width: 150px;">
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
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1300 bgcolor=efefef>
									<colgroup>
										<col style="width:5%"/>
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
										<th>ECS</th>
										<th>약정진행상태</th>
										<th>파트너사코드</th>
										<th>파트너사명</th>
										<th>팀명</th>
										<th>구매조직</th>
										<th>약정서번호</th>
										<th>MD요청생성일</th>
										<th>전자결재상태</th>
									</tr>
									<tbody id="dataListbody" />
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
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:10%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
										<th>순번</th>
										<th>판매코드</th>
										<th>상품명</th>
										<th>규격</th>
										<th>단가</th>
										<th>수량</th>
										<th>금액</th>
									</tr>
									<tbody id="dataListbody" />
								</table>
							</div>
							
							<div class="strProdTable" style="width:100%; height:270px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap; display: none;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1300 bgcolor=efefef>
									<colgroup>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:10%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
										<col style="width:5%"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
										<th>순번</th>
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
									<tbody id="dataListbody" />
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
