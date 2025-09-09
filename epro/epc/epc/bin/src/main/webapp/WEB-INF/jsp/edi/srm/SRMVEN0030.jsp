<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ taglib prefix="srm" uri="http://lcnjf.lcn.co.kr/taglib/srm"  %>
<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Page Name 	: SRMVEN0030.jsp
	Description : SRM 정보 > SRM 모니터링
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.08.17		LEE HYOUNG TAK		최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code='text.srm.field.srmven0030.title' /></title><%--spring:message : SRM 모니터링 --%>

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


		//LIST 마우스 OVER
//		$('table tbody[id=dataListbody] tr').live('mouseover',function(){
//			$(this).attr("bgcolor","#efefef");
//		});
//		$('table tbody[id=dataListbody] tr').live('mouseout',function(){
//			$(this).attr("bgcolor","#ffffff");
//		});


		//커스텀태그 defName Set
		//선택
		var option = "<option value= \"\"><spring:message code='text.srm.field.venCdAll' /></option>";
		$(option).prependTo("#searchForm select[name=channelCode]");
//		$(option).prependTo("#searchForm select[name=countYear]");
//		$(option).prependTo("#searchForm select[name=countMonth]");
		if("<c:out value="${epcLoginSession.repVendorId}"/>" == ""){
			$(option).prependTo("#searchForm select[name=venCd]");
		}


		//커스텀태그 값SET
		<jsp:useBean id="toDay" class="java.util.Date" />
		<c:set var="toYear"><fmt:formatDate value="${toDay}" pattern="yyyy"/></c:set>
		<c:set var="toMonth"><fmt:formatDate value="${toDay}" pattern="MM"/></c:set>
		$('#searchForm select[name=channelCode]').val("");
		$('#searchForm select[name=countYear]').val("<c:out value="${toYear}"/>");
		$('#searchForm select[name=countMonth]').val("<c:out value="${toMonth}"/>");
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


	/* 대분류/중분류 조회 */
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
			url : '<c:url value="/edi/ven/selectSRMmoniteringList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				_setTbodyMainValue(data);
			}
		});
	}

	/* 파트너사 인증정보 List Set */
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
		var ch = 800;

		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);

		
		window.open("", "SRMVEN003001Popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");

		$("#popUpForm").attr("action", "<c:url value='/edi/ven/selectSRMmoniteringDetailPopup.do'/>");
		$("#popUpForm").attr("target", "SRMVEN003001Popup");
		$("#popUpForm").submit();
		$("#popUpForm").attr("target", "_self");
	}

	/*개선요청팝업*/
	function doImpReqPopup(obj){
		$(obj).parent().find("input").each(function(){
			$('#popUpForm input[name='+this.name+']').val($(this).val());
		});
		var cw = 600;
		var ch = 700;

		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);


		window.open("", "SRMVEN003007Popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");

		$("#popUpForm").attr("action", "<c:url value='/edi/ven/selectSRMmoniteringDetailImpReqPopup.do'/>");
		$("#popUpForm").attr("target", "SRMVEN003007Popup");
		$("#popUpForm").submit();
		$("#popUpForm").attr("target", "_self");

	}
</script>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr bgcolor="ffffff">
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td><c:out value="\${sgName1}"/></td>
		<td><c:out value="\${sgName2}"/></td>
		<td style="text-align: center;"><c:out value="\${vendorCode}"/></td>
		<td class="click" onclick="javascript:doPopup(this);" style="cursor:pointer; font-weight:bold;background-color:#fffad1;"><c:out value="\${vendorName}"/></td>
		<td style="text-align: center;"><c:out value="\${evGradeClass}"/></td>
		<td style="text-align: center;"><c:out value="\${evScoreSum}"/></td>

		{%if impCnt != '0'%}
			<td class="click" style="text-align: center; cursor:pointer; font-weight:bold; color:red;background-color:#fffad1;" onclick="javascript:doImpReqPopup(this);"><c:out value="\${improveCount}"/></td>
		{%else%}
			<td style="text-align: center;"><c:out value="\${improveCount}"/></td>
		{%/if%}

		<td style="text-align: center;"><c:out value="\${egName}"/></td>
		<td style="text-align: center;"><c:out value="\${evTplSubject}"/></td>
		<td style="text-align: center;">
			<c:out value="\${evChannelName}"/>
			<input type="hidden" id="vendorName" name="vendorName" value="<c:out value="\${vendorName}"/>"/>
			<input type="hidden" id="evTplSubject" name="evTplSubject" value="<c:out value="\${evTplSubject}"/>"/>
			<input type="hidden" id="countYear" name="countYear" value="<c:out value="\${countYear}"/>"/>
			<input type="hidden" id="countMonth" name="countMonth" value="<c:out value="\${countMonth}"/>"/>
			<input type="hidden" id="vendorCode" name="vendorCode" value="<c:out value="\${vendorCode}"/>"/>
			<input type="hidden" id="catLv2Code" name="catLv2Code" value="<c:out value="\${catLv2Code}"/>"/>
			<input type="hidden" id="egNo" name="egNo" value="<c:out value="\${egNo}"/>"/>
			<input type="hidden" id="evTplNo" name="evTplNo" value="<c:out value="\${evTplNo}"/>"/>
			<input type="hidden" id="channelCode" name="channelCode" value="<c:out value="\${channelCode}"/>"/>
		</td>
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
									<label for="countYear"><spring:message code="text.srm.field.countDate"/></label><%--spring:message : 기준년월--%>
								</th>
								<td>
									<srm:codeTag comType="SELECT" dataType="YEAR" subCode="10" objId="countYear" objName="countYear" formName="" width="70px"/>
									<spring:message code="text.srm.field.year"/>
									<srm:codeTag comType="SELECT" dataType="MONTH" objId="countMonth" objName="countMonth" formName="" width="70px"/>
									<spring:message code="text.srm.field.month"/>
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
							<table id="tblInfo" name="tblInfo" cellpadding="1" cellspacing="1" border="0" width='1300px;' bgcolor=efefef >
								<colgroup id="tbHead1">
									<col width="4%" />
									<col width="10%" />
									<col width="10%" />
									<col width="6%" />
									<col width="*" />
									<col width="5%" />
									<col width="6%" />
									<col width="8%" />
									<col width="15%" />
									<col width="15%" />
									<col width="7%" />
								</colgroup>
								<thead id="tbHead2">
									<tr bgcolor="#e4e4e4">
										<th><spring:message code="table.srm.colum.title.no"/></th><%--spring:message : No--%>
										<th><spring:message code="table.srm.srmven0030.colum.title2"/></th><%--spring:message : 대분류명--%>
										<th><spring:message code="table.srm.srmven0030.colum.title3"/></th><%--spring:message : 중분류명--%>
										<th><spring:message code="table.srm.srmven0030.colum.title4"/></th><%--spring:message : 파트너사코드--%>
										<th><spring:message code="table.srm.srmven0030.colum.title5"/></th><%--spring:message : 파트너사명--%>
										<th><spring:message code="table.srm.srmven0030.colum.title6"/></th><%--spring:message : 등급--%>
										<th><spring:message code="table.srm.srmven0030.colum.title7"/></th><%--spring:message : 점수--%>
										<th><spring:message code="table.srm.srmven0030.colum.title8"/></th><%--spring:message : 개선필요--%>
										<th><spring:message code="table.srm.srmven0030.colum.title9"/></th><%--spring:message : 평가그룹--%>
										<th><spring:message code="table.srm.srmven0030.colum.title10"/></th><%--spring:message : 템플릿명--%>
										<th><spring:message code="table.srm.srmven0030.colum.title11"/></th><%--spring:message : 채널--%>
									</tr>
								</thead>
								<tbody id="dataListbody" />
							</table>
						</div
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
							<li>모니터링</li>
							<li class="last">평가모니터링</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- footer //-->
		</div>
	</div>
<form id="popUpForm" name="popUpForm" method="POST">
	<input type="hidden" id="vendorName" name="vendorName"/>
	<input type="hidden" id="evTplSubject" name="evTplSubject"/>
	<input type="hidden" id="countYear" name="countYear"/>
	<input type="hidden" id="countMonth" name="countMonth"/>
	<input type="hidden" id="vendorCode" name="vendorCode"/>
	<input type="hidden" id="catLv2Code" name="catLv2Code"/>
	<input type="hidden" id="egNo" name="egNo"/>
	<input type="hidden" id="evTplNo" name="evTplNo"/>
	<input type="hidden" id="channelCode" name="channelCode"/>
</form>
</body>
</html>