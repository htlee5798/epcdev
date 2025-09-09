<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMJON0030.jsp
	Description : 1차 스크리닝
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	 <title><spring:message code='text.srm.field.srmjon0030.title'/></title><%--spring:message : 1차 스크리닝--%>

	<script language="JavaScript">
		$(document).ready(function() {
//			doSecrchScreening();
			$('#MyForm select[name=channelCode]').live('change',function(){
				// 리셋
				setTbodyInit("dataListbody");
				setTbodyNoResult("dataListbody",3);

				$('#MyForm input[name=sgNo]').val("");
				$('#MyForm input[name=sgName]').val("");
				$('#MyForm input[name=scKind]').val("");
				$('#MyForm input[name=catLv1Code]').val("");
			});

			//선택
			var option = "<option value= \"\"><spring:message code='text.srm.field.select' /></option>";
			$(option).prependTo("#MyForm select[name=channelCode]");
			$("#MyForm select[name=channelCode]").val("");
		});

		//1차 스크리닝 평가
		function screening_appraisal() {
			var chk = true;
			var chkVal = {};


			//채널 선택여부 확인
			if (!$.trim($('#MyForm select[name=channelCode]').val())) {
				var target = "<spring:message code='text.srm.field.classification'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 판매처을(를) 선택하세요. */
				$('#MyForm select[name=channelCode]').focus();
				return;
			}
			//대분류
			if (!$.trim($('#MyForm input[name=sgNo]').val())) {
				var target = "<spring:message code='text.srm.field.catLv1Code'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 대분류을(를) 선택하세요. */
				return;
			}

			if($('#MyForm table[name=screeningTbl]').find(":radio").length != 0) {
				$('#MyForm [name=screeningTbl]').find("tbody tr").each(function(i) {
					$(this).find(":radio:checked").each(function() {
						chkVal[i] = $(this).val();
					});
				});

				for (var i=0; i < $('#MyForm [name=screeningTbl] tbody tr').length; i++) {
					if (chkVal[i] == null) {
						alert("<spring:message code='text.srm.field.srmjon0030.title'/> ["+(i+1)+"]<spring:message code='msg.srm.alert.evaluationItem'/>");/*spring:message : 1차 스크리닝*//*spring:message : 번째 평가 항목을 선택하세요*/
						return;
					}
				}

				for (var i=0; i < $('#MyForm [name=screeningTbl] tbody tr').length; i++) {
					if (chkVal[i] == "N") {
						//부적합 평가 COUNT

						fail_submit(i);
						return;
					}
				}
			} else {
				alert("<spring:message code='msg.srm.alert.validation.scrList'/>");/*spring:message 자가진단 항목이 없습니다.*/
				return;
			}
			success_submit();
		}

		//1차 스크리닝 LIST 조회
		function doSecrchScreening(){

			var searchInfo = {};
			//if($.trim($('#MyForm select[name=channelCode]').val()) && $.trim($('#MyForm input[name=scKind]').val())){
				searchInfo["channelCode"] = $('#MyForm select[name=channelCode]').val();
				searchInfo["scKind"] = $('#MyForm input[name=scKind]').val();
			//}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/selectScreeningList.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					_setTbodyMasterValue(data);
				}
			});
		}

		//LIST 조회된 값 SET
		function _setTbodyMasterValue(data){
			setTbodyInit("dataListbody");	// dataList 초기화

			if (data.length > 0) {
				data[0].beforeevItemNo = "";
				for (var i=1; i<data.length; i++){
					data[i].beforeevItemNo= data[i-1].evItemNo;
				}

				var html = "";
				var rowspan = 0;
				for (var i=0; i<data.length; i++){
					if(data[i].rowspan == 1 || data[i].beforeevItemNo != data[i].evItemNo) {
						html += '<tr id="screeningTr">';
						html += '<td style="text-align: center;">'+(i/2+1)+'</td>';
//						html += '<td style="text-align: center;" >'+data[i].evItemSubject+'</td>';
						html += '<td >'+data[i].evItemContents+'</td>';
						html += '<td style="text-align: center;">';
						html += '<input type="radio" id="screening'+data[i].evItemNo+'" name="screening'+data[i].evItemNo+'" title="" value="Y"/> '+data[i].evIdContents;
						html += '<input type="hidden" id="evIdSeq" name="evIdSeq" value="'+data[i].evIdSeq+'"/>';
						html += '<input type="hidden" id="evIdScore" name="evIdScore" value="'+data[i].evIdScore+'"/>';
					} else {
						html += '&nbsp;&nbsp;<input type="radio" id="screening'+data[i].evItemNo+'" name="screening'+data[i].evItemNo+'" title="" value="N"/> '+data[i].evIdContents;
						html += '<input type="hidden" id="evIdSeq" name="evIdSeq" value="'+data[i].evIdSeq+'"/>';
						html += '<input type="hidden" id="evIdScore" name="evIdScore" value="'+data[i].evIdScore+'"/>';
						html += '<input type="hidden" id="evItemNo" name="evItemNo" value="'+data[i].evItemNo+'"/>';
						html += '<input type="hidden" id="evTplNo" name="evTplNo" value="'+data[i].evTplNo+'"/>';

						html += '</td>';
						html += '</tr>';
					}
				}

				$("#dataListbody").html(html);

//				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			} else {
				setTbodyNoResult("dataListbody",3);
			}
		}

		//1차 스크리닝 평가 부적합판정
		function fail_submit() {
			<%--popupWindow("<c:url value='/edi/srm/screeningUnqualifiedPopup.do'/>", 500, 200, "toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");--%>
			alert("<spring:message code="text.srm.field.srmjon003001Notice1"/>");<%--spring:message : 해당 상담 분류 "상품부문" 1차 스크리닝 평가에 부적합 합니다.--%>
		}

		//1차 스크리닝 평가 적합판정
		function success_submit() {
			doScreening();
		}

		//팝업 윈도우
		var popup;
		function popupWindow(url, width, height,option){
			var cw=width;
			var ch=height;
			var sw=screen.availWidth;
			var sh=screen.availHeight;
			var px=Math.round((sw-cw)/2);
			var py=Math.round((sh-ch)/2);
			popup = window.open("","popup","left="+px+",top="+py+",width="+cw+",height="+ch+","+option);
			$("#MyForm").attr("action", url);
			$("#MyForm").attr("target", "popup");
			$("#MyForm").submit();
			$("#MyForm").attr("target", "_self");
		}

		//상담 스크리닝 등록
		function doScreening() {
			//1차 스크리닝 등록
			var saveInfoList = [];
			var saveInfo = {};
			$('#dataListbody tr').each(function(){
				var saveInfo = {};
				$(this).find("input").each(function(){
					if(this.name.indexOf("screening") > -1) {
						if(this.type == "radio" && $(this).is(":checked")){
							saveInfo["evItemNo"] = this.name.replace("screening", "");
							saveInfo["evIdSeq"] = $(this).next().val();
							saveInfo["evIdScore"] = $(this).next().next().val();
						}
					}
				});
				saveInfo["channelCode"] = $('#MyForm select[name=channelCode]').val();
				saveInfo["catLv1Code"] = $('#MyForm input[name=sgNo]').val();

				saveInfo["evTplNo"] = $(this).find('input[name=evTplNo]').val();

				saveInfoList.push(saveInfo);
			});

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/insertScreeningList.json"/>',
				data : JSON.stringify(saveInfoList),
				success : function(data) {
					if(data.message == "SUCCESS") {
						<%--popupWindow("<c:url value='/edi/srm/screeningQualifiedPopup.do'/>", 500, 200, "toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");--%>
						alert("<spring:message code="text.srm.field.srmjon003002Notice1"/>");<%--spring:message : 해당 상담 분류 "상품부문" 1차 스크리닝 평가에 부적합 합니다.--%>
						doPage();
					} else if (data.message == "FAIL-REFUSE") {
						alert('<spring:message code="msg.srm.alert.refuse"/>');/*spring:message : 연속 3회 입점거절되어 더이상 신청이 불가합니다.*/
					} else if (data.message == "FAIL-SUCCESS") {
						alert('<spring:message code="msg.srm.alert.errorSuccess"/>');/*spring:message : 입점승인상태인 내용이 있습니다.*/
					} else if (data.message == "FAIL-ING") {
						alert('<spring:message code="msg.srm.alert.errorIng"/>');/*spring:message : 입점 신청 진행중인 내용이 있습니다.*/
					} else {
						alert('<spring:message code="msg.common.fail.request"/>');/*spring:message : 요청처리를 실패하였습니다.*/
					}
				}
			});
		}

		function doPage(){
			$('#hiddenForm input[name=channelCode]').val($('#MyForm select[name=channelCode]').val());
			$('#hiddenForm input[name=catLv1Code]').val($('#MyForm input[name=sgNo]').val());
			$('#hiddenForm input[name=catLv1CodeNm]').val($('#MyForm input[name=sgName]').val());

			$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0040.do'/>");
			$("#hiddenForm").attr("target","_self");
			$("#hiddenForm").submit();
		}

		//대분류 코드 조회 팝업
		function doSearch(){
			//채널 선택여부 확인
			if (!$.trim($('#MyForm select[name=channelCode]').val())) {
				var target = "<spring:message code='text.srm.field.classification'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 상담신청을(를) 선택하세요. */
				$('#MyForm select[name=channelCode]').focus();
				return;
			}
			
			popupWindow("<c:url value='/edi/srm/selectCatLv1CodePopup.do'/>", 680, 550, "toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");
		}

		//대분류 SET
		function setCatLv1Code(sgNo, sgName, scKind){
			$('#MyForm input[name=sgNo]').val(sgNo);
			$('#MyForm input[name=sgName]').val(sgName);
			$('#MyForm input[name=scKind]').val(scKind);
			$('#MyForm input[name=catLv1Code]').val(sgName);
			doSecrchScreening();
		}
	</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr id="screeningTr">
		{%if rowspan == 1  || beforeSubject != evItemSubject%}
			<td style="text-align: center;"><c:out value="\${rnum}"/></td>
			<td style="text-align: center;" rowspan="<c:out value="\${rowspan}"/>"><c:out value="\${evItemSubject}"/></td>
			<td rowspan="<c:out value="\${rowspan}"/>"><c:out value="\${evItemContents}"/></td>
		<td style="text-align: center;">
			<input type="radio" id="screening<c:out value="\${evItemNo}"/>" name="screening<c:out value="\${evItemNo}"/>" title="" value="<c:out value="\${evIdScore}"/>"/> <c:out value="\${evIdContents}"/>
			<input type="radio" id="screening<c:out value="\${evItemNo}"/>" name="screening<c:out value="\${evItemNo}"/>" title="" value="<c:out value="\${evIdScore}"/>"/> <c:out value="\${evIdContents}"/>
			<input type="hidden" id="evItemNo" name="evItemNo" value="<c:out value="\${evItemNo}"/>"/>
			<input type="hidden" id="evIdSeq" name="evIdSeq" value="<c:out value="\${evIdSeq}"/>"/>
		{%else%}

			<input type="radio" id="screening<c:out value="\${evItemNo}"/>" name="screening<c:out value="\${evItemNo}"/>" title="" value="N"/> <c:out value="\${evIdContents}"/>
			</td>
		{%/if%}
	</tr>
</script>

</head>


<body>

<form id="MyForm" name="MyForm" method="post">
	<input type="hidden" id="sgNo" name="sgNo"/>
	<input type="hidden" id="sgName" name="sgName"/>
	<input type="hidden" id="scKind" name="scKind"/>
	
	
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>
				<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span> <span><spring:message code='text.srm.field.srmjon0030.title' /></span></p>
			</div><!-- END 서브상단 -->
			
			<!-- 버튼이 없을 경우 타이틀 -->
			<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0030.sub.title1'/></h3>	<!-- 카레고리 선택 -->
			<!-- END 버튼이 없을 경우 타이틀 -->
			
			<!-- 정보 입력폼 -->
			<table class="tbl_st1 form_style">
				<colgroup>
					<col style="width:15%;">
					<col style="width:35%;">
					<col style="width:15%;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>
							*<label for="channelCode"><spring:message code='text.srm.field.classification'/></label> :<%--spring:message : 상담신청 분류--%>
						</th>
						<td>
							<srm:codeTag comType="SELECT" objId="channelCode" objName="channelCode" formName="" parentCode="SRM053" width="100px"/>
						</td>
						<th>*<label for="catLv1Code"><spring:message code='text.srm.field.catLv1Code'/></label></th>
						<td>
							<input type="text" id="catLv1Code" name="catLv1Code" title="" class="input_txt_default" disabled readonly/>
							<button type="button" class="btn_normal btn_blue ml5" onclick="javascript:doSearch();"><spring:message code='button.srm.search'/></button>	<%-- 검색 --%>
						</td>
					</tr>
				</tbody>
			</table><!-- END 정보 입력폼 -->
			
			<p style="margin-top: 10px;" />
			<%----- 1차 스크리닝 평가 Start -------------------------%>
			<table class="tbl_st1" id="screeningTbl" name="screeningTbl">
				<colgroup>
					<col width="5%"/>
					<%--<col width="15%"/>--%>
					<col width="*"/>
					<col width="15%"/>
				</colgroup>
				<thead>
				<tr>
					<th><spring:message code='table.srm.colum.title.no'/></th><%--spring:message : No--%>
					<%--<th><spring:message code='table.srm.srmjon0030.colum.title2'/></th><%--spring:message : 분류--%>
					<th><spring:message code='table.srm.srmjon0030.colum.title3'/></th><%--spring:message : 평가내용--%>
					<th><spring:message code='table.srm.srmjon0030.colum.title4'/></th><%--spring:message : 평가등록--%>
				</tr>
				</thead>

				<%-- sample --%>
				<tbody id="dataListbody">
					<tr><td colspan="3" class="tdm" align=center height=30><spring:message code='msg.srm.alert.srmjon0030.notListData' /></td></tr><%--spring:message : 조회결과가 없습니다.--%>
				</tbody>
			</table>
			<%----- 1차 스크리닝 평가 End -------------------------%>
			
			<div style="padding-top: 20px;" align="center">
				<table align="center">
					<tr>
						<td align="center">
							<btn>
								<button type="button" class="btn_normal btn_red" onClick="screening_appraisal();"><spring:message code='button.srm.verifyRating'/></button>	<%-- 평가확인--%>
							</btn>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- 알림 -->
			<p style="margin-top: 10px;" />
			<div class="noti_box">
				<ul class="noti_list">
					<spring:message code='text.srm.field.srmjon0030Notice2'/>
				</ul>
			</div><!-- END 알림 -->
			
		</div><!-- END Sub Wrap -->
	</div><!--END Container-->
		
</form>

<form id="hiddenForm" name="hiddenForm" method="post">
	<input type="hidden" id="channelCode" name="channelCode"/>
	<input type="hidden" id="catLv1Code" name="catLv1Code"/>
	<input type="hidden" id="catLv1CodeNm" name="catLv1CodeNm"/>
</form>

</body>
</html>