<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ taglib prefix="srm" uri="http://lcnjf.lcn.co.kr/taglib/srm"  %>
<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Page Name 	: SRMVEN0050.jsp
	Description : SRM 성과평가 > 성과평가 결과
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2018.11.20		LEE SANG GU		 최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title> SRM 성과평가 </title><%--spring:message : SRM 모니터링 --%>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		//대분류 조회
		selectCodeData('1');


		$('#searchForm select[name=countYear]').live('change',function(){
			dp_makeYearCombo($(this), $(this).val());
		});

		$('#searchForm select[name=catLv1Code]').live('change',function(){
			//중분류 조회
			selectCodeData('2');
		});

		//커스텀태그 defName Set
		//선택
		var option = "<option value= \"\"><spring:message code='text.srm.field.venCdAll' /></option>";
		$(option).prependTo("#searchForm select[name=channelCode]");
		if("<c:out value="${epcLoginSession.repVendorId}"/>" == ""){
			$(option).prependTo("#searchForm select[name=venCd]");
		}

		//커스텀태그 값SET
		<jsp:useBean id="toDay" class="java.util.Date" />
		<c:set var="toYear"><fmt:formatDate value="${toDay}" pattern="yyyy"/></c:set>		
		$('#searchForm select[name=channelCode]').val("");
		$('#searchForm select[name=countYear]').val("<c:out value="${toYear}"/>");		
		$('#searchForm select[name=venCd]').val("<c:out value="${epcLoginSession.repVendorId}"/>");

		goPage('1');
	});

	/**
	 * 현재년도부터 -5년까지의 콤보 생성
	 *
	 * @param obj
	 * @param year
	 */
	function dp_makeYearCombo(obj, year) {
		if ( obj == null ) return;
		$(obj).html("");
		var html = "";
		html += "<option value=''><spring:message code='text.srm.field.select'/></option>";/*spring:message : 선택*/

		for( var i = year-5; i < year; i++ ) {
			html+="<option value=" + i +">" + i + "</option>"
		}

		for( var i = year; i < Number(year)+5; i++ ) {
			html+="<option value=" + i +">" + i + "</option>"
		}

		$(html).appendTo(obj);
		$(obj).val(year);
	}

	/* 대분류/중분류 조회 */
	function selectCodeData(depth) {

		var searchInfo = {};
		searchInfo["depth"] = depth;
		if(depth != '1') {
			searchInfo["parentSgNo"] = $('#searchForm select[name=catLv1Code]').val();
		}

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectCatLv1CodeList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				var html = "";
				html += "<option value= \"\"><spring:message code="text.srm.field.venCdAll"/></option>";/*spring:message : 전체*/
				if(data.length > 0){
					for(var i=0; i<data.length; i++){
						html += "<option value= \"" + data[i].code + "\">" + data[i].name + "</option>";
					}
				} else {

				}
				if(depth == '1') {
					$('#searchForm select[name=catLv1Code]').html(html);
				} else if(depth == '2') {
					$('#searchForm select[name=catLv2Code]').html(html);
				}
			}
		});
	}


	/*조회*/
	function goPage(pageIndex) {

		var searchInfo = {};
		var venCds = [];
		$('#searchForm').find("select").each(function(){
			searchInfo[this.name] = $(this).val();
		});
		searchInfo["pageIndex"] = pageIndex;		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,			
			url : '<c:url value="/edi/ven/selectSrmEvalRes.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {				
				_setTbodyMainValue(data);
			}
		});
	}

	/* 조회결과 세팅 */
	function _setTbodyMainValue(json){
		setTbodyInit("dataListbody");	// dataList 초기화
		$("#paging").empty();			// paging 초기화
		var data = json.listData;
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 11);
		}
	}

	//상세 팝업
	function doPopup(obj){
		$(obj).parent().find("input").each(function(){
			$('#popUpForm input[name='+this.name+']').val($(this).val());
		});
		var cw = 1100;
		var ch = 810;

		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		//var py = Math.round((sh-ch)/2);
		var py = 0;
		
		window.open("", "SRMVEN005001Popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");

		$("#popUpForm").attr("action", "<c:url value='/edi/ven/selectSrmEvalResDetailPopup.do'/>");
		$("#popUpForm").attr("target", "SRMVEN005001Popup");
		$("#popUpForm").submit();
		$("#popUpForm").attr("target", "_self");
	}

</script>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr bgcolor="ffffff">
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td style="text-align: left;"><c:out value="\${egName}"/></td>
		<td style="text-align: left;"><c:out value="\${evTplSubject}"/></td>
		<td style="text-align: left;"><c:out value="\${evChannelName}"/></td>
		<td style="text-align: left;"><c:out value="\${sgName1}"/></td>
		<td style="text-align: center;"><c:out value="\${sgNo}"/></td>
		<td style="text-align: left;"><c:out value="\${sgName2}"/></td>
		<td style="text-align: center;"><c:out value="\${vendorCode}"/></td>
		<td class="click" onclick="javascript:doPopup(this);" style="text-align: left; cursor:pointer; font-weight:bold;background-color:#fffad1;"><c:out value="\${vendorName}"/></td>
		<td style="text-align: right;"><c:out value="\${evScore}"/></td>
		<td style="text-align: center;"><c:out value="\${evGradeClass}"/></td>
		<td style="text-align: center;"><c:out value="\${evNo}"/></td>
		<td style="text-align: center;"><c:out value="\${evName}"/></td>
		<td style="text-align: center;"><c:out value="\${evCtrlUserName}"/></td>
		<td style="text-align: center;"><c:out value="\${addDate}"/></td>
		<td style="text-align: center;"><c:out value="\${status}"/></td>
		<input type="hidden" id="evNo" name="evNo" value="<c:out value="\${evNo}"/>"/>
		<input type="hidden" id="egName" name="egName" value="<c:out value="\${egName}"/>"/>
		<input type="hidden" id="egNo" name="egNo" value="<c:out value="\${egNo}"/>"/>
		<input type="hidden" id="execEvTplNo" name="execEvTplNo" value="<c:out value="\${execEvTplNo}"/>"/>		
		<input type="hidden" id="evCtrlUserId" name="evCtrlUserId" value="<c:out value="\${evCtrlUserId}"/>"/>
		<input type="hidden" id="evCtrlUserName" name="evCtrlUserName" value="<c:out value="\${evCtrlUserName}"/>"/>
		<input type="hidden" id="evChannelCode" name="evChannelCode" value="<c:out value="\${evChannelCode}"/>"/>
		<input type="hidden" id="evChannelName" name="evChannelName" value="<c:out value="\${evChannelName}"/>"/>
		<input type="hidden" id="evTplSubject" name="evTplSubject" value="<c:out value="\${evTplSubject}"/>"/>
		<input type="hidden" id="evYear" name="evYear" value="<c:out value="\${evYear}"/>"/>
		<input type="hidden" id="evYearHalf" name="evYearHalf" value="<c:out value="\${evYearHalf}"/>"/>
		<input type="hidden" id="evYearHalfName" name="evYearHalfName" value="<c:out value="\${evYearHalfName}"/>"/>
		<input type="hidden" id="sgNo" name="sgNo" value="<c:out value="\${sgNo}"/>"/>
		<input type="hidden" id="sgName1" name="sgName1" value="<c:out value="\${sgName1}"/>"/>
		<input type="hidden" id="sgName2" name="sgName2" value="<c:out value="\${sgName2}"/>"/>
		<input type="hidden" id="vendorName" name="vendorName" value="<c:out value="\${vendorName}"/>"/>
		<input type="hidden" id="vendorCode" name="vendorCode" value="<c:out value="\${vendorCode}"/>"/>
		<input type="hidden" id="evScore" name="evScore" value="<c:out value="\${evScore}"/>"/>
		<input type="hidden" id="evGradeClass" name="evGradeClass" value="<c:out value="\${evGradeClass}"/>"/>
		<input type="hidden" id="applyFromDate" name="applyFromDate" value="<c:out value="\${applyFromDate}"/>"/>
	</tr>
</script>

</head>

<body>
	<div id="content_wrap">
		<div>
			<div class="wrap_search">
				<form name="searchForm"  id="searchForm" method="post">
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"><spring:message code='epc.ord.searchCondition' /></li>
							<li class="btn">
								<a href="#" class="btn" onclick="goPage('1');"><span><spring:message code="button.common.inquire" /></span></a>	<!-- 조회 -->
							</li>
						</ul>
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width: 15%" />
								<col style="width: 30%" />
								<col style="width: 10%" />
								<col style="" />
							</colgroup>
							<tr>
								<th><label for="channelCode"><spring:message code="text.srm.field.channelCode"/></label></th><%--spring:message : 채널--%>
								<td>
									<srm:codeTag comType="SELECT" objId="channelCode" objName="channelCode" formName="" parentCode="SRM028" width="100px;"/>
								</td>
								<th><label for="catLv1Code"><spring:message code="text.srm.field.catLv1Code"/></label></th><%--spring:message : 카테고리--%>
								<td>
									<select id="catLv1Code" name="catLv1Code" style="width:100px;"></select>
									/
									<select id="catLv2Code" name="catLv2Code" style="width:100px;">
										<option value=""><spring:message code="text.srm.field.venCdAll"/></option><%--spring:message : 전체--%>
									</select>
								</td>
							</tr>
							<tr>
								<th>
									<label for="venCd"><spring:message code="text.srm.field.venCompany"/></label><%--spring:message : 협력업체--%>
								</th>
								<td>
									<html:codeTag objId="venCd" objName="venCd" width="150px;" dataType="CP" comType="SELECT" formName="form"/>
								</td>
								<th>
									<label for="countYear">평가기간</label>
								</th>
								<td>
									<srm:codeTag comType="SELECT" dataType="YEAR" subCode="5" objId="countYear" objName="countYear" formName="" width="70px"/>
									<spring:message code="text.srm.field.year"/>
									<srm:codeTag comType="SELECT" dataType="YEAR_HALF" objId="yearHalf" objName="yearHalf" formName="" parentCode="SRM032" width="70px"/>									
								</td>
							</tr>
						</table>
					</div>
				</form>
				<%----------------------모니터링 LIST Start----------------------%>
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit"><spring:message code="text.srm.field.srmven0030.sub.title1"/></li><%--spring:message : 모니터링 LIST --%>
						</ul>
						<div style="width:100%; height:460px; overflow-y:hidden; table-layout:fixed;white-space:nowrap;">
							<table id="tblInfo" name="tblInfo" cellpadding="1" cellspacing="1" border="0" width='1500px;' bgcolor=efefef >
								<colgroup id="tbHead1">
									<col width="2%" />
									<col width="11%" />
									<col width="11%" />
									<col width="4%" />
									<col width="6%" />
									<col width="5%" />
									<col width="7%" />
									<col width="5%" />
									<col width="8%" />
									<col width="4%" />
									<col width="4%" />
									<col width="6%" />
									<col width="*" />
									<col width="4%" />
									<col width="5%" />
									<col width="4%" />
								</colgroup>
								<thead id="tbHead2">
									<tr bgcolor="#e4e4e4">
										<th><spring:message code="table.srm.colum.title.no"/></th><%--spring:message : No--%>
										<th>평가그룹</th><%--spring:message : 평가그룹--%>
										<th>템플릿</th><%--spring:message : 템플릿--%>
										<th>채널</th><%--spring:message : 채널--%>
										<th>대분류</th><%--spring:message : 대분류--%>
										<th>중분류코드</th><%--spring:message : 중분류코드--%>
										<th>중분류명</th><%--spring:message : 중분류명--%>
										<th>파트너사코드</th><%--spring:message : 파트너사코드--%>
										<th>파트너사명</th><%--spring:message : 파트너사명--%>
										<th>평가결과</th><%--spring:message : 평가결과--%>
										<th>평가등급</th><%--spring:message : 평가등급--%>
										<th>평가번호</th><%--spring:message : 평가번호--%>
										<th>평가명</th><%--spring:message : 평가명--%>
										<th>평가담당자</th><%--spring:message : 평가담당자--%>
										<th>평가생성일</th><%--spring:message : 평가생성일--%>
										<th>진행상태</th><%--spring:message : 진행상태--%>
									</tr>
								</thead>
								<tbody id="dataListbody" />
							</table>
						</div>
						<%-- Paging 영역 start ---------------------------%>
						<div id="paging_div">
							<ul class="paging_align" id="paging" style="width: 400px"></ul>
						</div>
						<%-- Paging 영역 end ---------------------------%>
					</div>
				</div>
				<%----------------------모니터링 LIST End----------------------%>

			</div>
			<!-- footer -->
			<div id="footer">
				<div id="footbox">
					<div class="msg" id="resultMsg"></div>
					<div class="notice"></div>
					<div class="location">
						<ul>
							<li>홈</li>
							<li>SRM정보</li>
							<li>성과평가</li>
							<li class="last">성과평가 결과</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- footer //-->
		</div>
	</div>
<form id="popUpForm" name="popUpForm" method="POST">
	
	<input type="hidden" id="evNo" name="evNo" value="<c:out value="\${evNo}"/>"/>
		<input type="hidden" id="egNo" name="egNo" />
		<input type="hidden" id="egName" name="egName" />
		<input type="hidden" id="execEvTplNo" name="execEvTplNo"/>		
		<input type="hidden" id="evCtrlUserId" name="evCtrlUserId"/>
		<input type="hidden" id="evCtrlUserName" name="evCtrlUserName"/>
		<input type="hidden" id="evChannelCode" name="evChannelCode"/>
		<input type="hidden" id="evChannelName" name="evChannelName"/>
		<input type="hidden" id="evTplSubject" name="evTplSubject"/>
		<input type="hidden" id="evYear" name="evYear"/>
		<input type="hidden" id="evYearHalf" name="evYearHalf"/>
		<input type="hidden" id="evYearHalfName" name="evYearHalfName"/>
		<input type="hidden" id="sgNo" name="sgNo"/>
		<input type="hidden" id="sgName1" name="sgName1"/>
		<input type="hidden" id="sgName2" name="sgName2"/>
		<input type="hidden" id="vendorName" name="vendorName"/>
		<input type="hidden" id="vendorCode" name="vendorCode"/>
		<input type="hidden" id="evScore" name="evScore"/>
		<input type="hidden" id="evGradeClass" name="evGradeClass"/>
		<input type="hidden" id="applyFromDate" name="applyFromDate"/>
</form>
</body>
</html>