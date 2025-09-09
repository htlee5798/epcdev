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
	<link href="/css/epc/edi/srm/style.css" rel="stylesheet" type="text/css" />
	<title> SRM 성과평가 </title><%--spring:message : SRM 모니터링 --%>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		
	});
	
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

</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr bgcolor="ffffff">
		<td style="text-align: center;"><c:out value="\${rnum}"/></td>
		<td style="text-align: left;"><c:out value="\${egName}"/></td>
		<td style="text-align: left;"><c:out value="\${evTplSubject}"/></td>
		<td style="text-align: center;"><c:out value="\${evChannelName}"/></td>
		<td style="text-align: center;"><c:out value="\${sgName1}"/></td>
		<td style="text-align: center;"><c:out value="\${sgNo}"/></td>
		<td style="text-align: center;"><c:out value="\${sgName2}"/></td>
		<td style="text-align: center;"><c:out value="\${vendorCode}"/></td>
		<td class="click" onclick="javascript:doPopup(this);" style="text-align: center; cursor:pointer; font-weight:bold;background-color:#fffad1;"><c:out value="\${vendorName}"/></td>
		<td style="text-align: center;"><c:out value="\${evScore}"/></td>
		<td style="text-align: center;"><c:out value="\${evGradeClass}"/></td>
		<td style="text-align: center;"><c:out value="\${evNo}"/></td>
		<td style="text-align: center;"><c:out value="\${evName}"/></td>
		<td style="text-align: center;"><c:out value="\${evCtrlUserName}"/></td>
		<td style="text-align: center;"><c:out value="\${addDate}"/></td>
		<td style="text-align: center;"><c:out value="\${status}"/></td>		
	</tr>
</script>

<style>

.rs_th{
	text-align: center;
	border-collapse : collapse;
	border : 1px solid #333333;
	height : 30px;
	padding: 2px

}

.rs_td{
	text-align: center;
	border-collapse : collapse;
	border : 1px solid #acb0b3;
	height : 42px;
	padding: 2px;
	background-color: #fbfbfb
	
}

</style>
</head>
<body topmargin="6">
<s:header popup="true">
<div style="padding-left: 10px; padding-top: 10px">
	■ 해당 점수는 상반기(11월~4월)/ 하반기(5월~10월) 해당 기간의 항목별 점수를 산정하여 평가한 결과입니다. 월별 상세 평가결과는 모니터링에서 확인 요청 드립니다.
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody><tr>
    <td class="title_page">
 
		성과평가결과

    &nbsp;
	</td>
    <td align="right"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tbody><tr>
        <td align="right" class="location_start">

<!--         <img src="../images/ico_location.gif" width="11" height="13" hspace="5" align="absmiddle"> -->

		성과평가 &gt; 성과평가결과

&nbsp;
		</td>
      </tr>
    </tbody></table></td>
  </tr>
</tbody></table>
<form name="form" id="form">
	<table width="100%" style="table-layout: fixed;">
		<input type="hidden" id="ev_no"                   name="ev_no"                   value="<c:out value="${vo.evNo}"/>" /> 
		<input type="hidden" id="eg_no"                   name="eg_no"                   value="<c:out value="${vo.egNo}" />"/> 
		<input type="hidden" id="ev_tpl_no"               name="ev_tpl_no"               value="<c:out value="${vo.execEvTplNo}" /> "/>
		<input type="hidden" id="ev_ctrl_user_id"         name="ev_ctrl_user_id"         value="<c:out value="${vo.evCtrlUserId}" /> "/>
	 	<input type="hidden" id="ev_year_half"            name="ev_year_half"            value="<c:out value="${vo.evYearHalf}" /> "/>
	 	<input type="hidden" id="sg_no"                   name="sg_no"                   value="<c:out value="${vo.sgNo}" />"/>
	 	<input type="hidden" id="vendor_code"             name="vendor_code"             value="<c:out value="${vo.vendorCode}" /> "/>
	 	<input type="hidden" id="channel_code"		  	  name="channel_code"		 	 value="<c:out value="${vo.evChannelCode}" />"/> 	
		<tr>
				<td valign="top">
					<table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#ccd5de" style="
    padding-bottom: 5px;
">
						<tr>
							<td height="2" bgcolor="#2e3f47" colspan="4"></td>
						</tr>
						<tr>
							<td width="15%" class="c_title_1">평가번호</td><%-- 평가번호 --%>
							<td width="35%" class="c_data_1">
								<c:out value="${vo.evNo}"/>	
							</td>
							<td width="15%" class="c_title_1">평가그룹</td><%-- 평가그룹 --%>
							<td width="35%" class="c_data_1">
								<c:out value="${vo.egName}"/>
							</td>
						</tr>
						<tr>	
							<td width="15%" class="c_title_1">채널</td><%-- 채널 --%>  
							<td width="35%" class="c_data_1">
								<c:out value="${vo.evChannelName}"/>								
							</td>
							<td width="15%" class="c_title_1">담당MD</td><%-- 담당MD --%>  
							<td width="35%" class="c_data_1">
								<c:out value="${vo.evCtrlUserName}"/>
							</td>
						</tr>
						<tr>
							<td width="15%" class="c_title_1">평가기간</td><%-- 평가기간 --%>  
							<td width="35%" class="c_data_1">
							
								<c:out value="${vo.evYear}"/>년
 								<c:out value="${vo.evYearHalfName}"/>
							</td>
							<td height="15%" class="c_title_1">카테고리</td><%-- 카테고리 --%>
							<td width="35%" class="c_data_1">
								<c:out value="${vo.sgName1}"/>
 								 &gt;
 								 <c:out value="${vo.sgName2}"/>
							</td>
						</tr>
						<tr>	
							<td width="15%" class="c_title_1">파트너사명</td><%-- 파트너사명 --%>  
							<td width="35%" class="c_data_1">
								<c:out value="${vo.vendorName}"/>
 							</td>
							<td height="24" class="c_title_1">템플릿</td><%-- 템플릿 --%>  
							<td height="24" class="c_data_1">
								<c:out value="${vo.evTplSubject}"/>
							</td>							
							<%-- <td height="24" class="c_title_1"><%=text.get("SRM032.TEXT09")%></td>평가등급  
							<td height="24" class="c_data_1">
							<input type="text" id="ev_grade_class"             name="ev_grade_class"             value="<%=ev_grade_class      %>" /> 
							</td> --%>
						</tr>
					</table>					
					<div style="width:100%; height:100px; table-layout:fixed;white-space:nowrap;">
							<table id="tblInfo" name="tblInfo" width='100%' style="border-collapse: collapse;" >
								<colgroup id="tbHead1">
									<col width="15%" />
									<col width="11%" />
									<col width="7%" />
									<col width="15%" />
									<col width="*" />
									<col width="5%" />
									<col width="5%" />									
								</colgroup>
								<thead id="">
									<tr bgcolor="#e4e4e4">
										<th class="rs_th">템플릿</th><%--spring:message : No--%>
										<th class="rs_th">평가관점</th><%--spring:message : 평가그룹--%>
										<th class="rs_th">평가영역</th><%--spring:message : 템플릿--%>
										<th class="rs_th">평가항목</th><%--spring:message : 채널--%>
										<th class="rs_th">평가내용</th><%--spring:message : 대분류--%>
										<th class="rs_th">항목점수</th><%--spring:message : 중분류코드--%>
										<th class="rs_th">배점</th><%--spring:message : 중분류명--%>										
									</tr>
								</thead>
								<c:forEach var="list" items="${list }" >
									<tr>
											<td class="rs_td"> ${list.evTplSubject }</td>
											<td class="rs_td"> ${list.evItemType1Name }</td>
											<td class="rs_td"> ${list.evItemType2Name }</td>
											<td class="rs_td"> ${list.evItemSubject }</td>
											<td class="rs_td" style="text-align: left; padding-left:10px"> ${list.evItemContents }</td>
											<td class="rs_td"> ${list.evScore }</td>
											<td class="rs_td"> ${list.evTplScore }</td>
									</tr>
								</c:forEach>
								<!-- <tr>
									<td class="rs_td" colspan="7" style="background-color:#fbfbfb"> </td>
								</tr> -->
							</table>
							<!-- footer -->
							<br>
							<div id="footer">
								<div id="footbox">
									<div class="msg" id="resultMsg"></div>
									<div class="notice">    </div>
									<div >
										&nbsp;										
									</div>
								</div>
							</div>
							<!-- footer //-->
						</div>						
				</td>
			</tr>			
		</table>		
	</form>
</s:header>
<s:footer/>
</body>
</html>