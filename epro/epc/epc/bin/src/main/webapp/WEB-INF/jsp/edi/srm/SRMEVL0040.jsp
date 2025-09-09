<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<%--
	Page Name 	: SRMEVL0040.jsp
	Description : 품질평가등록[회원사 기본정보]
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.08 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><spring:message code="text.srm.field.srmevl0040.title"/></title><%--spring:message : 품질경영평가--%>

	<script language="JavaScript">
		$(document).ready(function() {
			//화면 로드시 선택한값 SET
			for(var i=1; i<=$('#tbl tr #cnt').length; i++) {
				$('#evItemSpan'+i).html($(':radio[name=evIdScoreVal'+i+']:checked').val());
			}

			//선택한 항목 점수 표시
			$("input[name^='evIdScoreVal']").live("click", function(){
				var index  = $(this).attr("id").replace("evIdScoreVal","");
				$('#evItemSpan'+index).html($(this).val());
				evItemScoreValSum();
			});

			//전체 총점 계산
			var sum = 0;
			$('#tbl tbody tr').find("#evIdScore").each(function(i){
				sum += Number($(this).text());
			});
			$('#evItemScoreSum').text(sum);
			evItemScoreValSum();


			if("<c:out value="${excelFileUpload}"/>" == "Y"){
				if("<c:out value="${result.message}"/>" != "success"){
					alert("업로드하는 첨부파일을 확인해 주세요.");
					<%--if("<c:out value="${result.message}"/>" == "FAIL-EV_ITEM_NO") {--%>
						<%--alert("같은항목 중복체크되어 있습니다. 첨부파일을 확인해 주세요.");--%>
					<%--} else if("<c:out value="${result.message}"/>" == "FAIL-EV_ITEM_NO") {--%>
						<%--alert("해당 평가 템플릿이 아님");--%>
					<%--} else if("<c:out value="${result.message}"/>" == "FAIL-HEADER") {--%>
						<%--alert("업로드 하는 엑셀 첨부파일을 확인해 주세요.");--%>
					<%--} else if("<c:out value="${result.message}"/>" == "FAIL-NULL") {--%>
						<%--alert("업로드 하는 엑셀 첨부파일을 확인해 주세요.");--%>
					<%--}--%>
				}
				$("#hiddenForm").attr("action", "<c:url value="/edi/evl/SRMEVL0040.do" />");
				$("#hiddenForm").submit();
			}
		});

		//평가항목클릭시 총합계산
		function evItemScoreValSum(){
			var sum = 0;
			$('#tbl tbody tr').find("span[id^='evItemSpan']").each(function(i){
				sum += Number($(this).text());
			});
			$('#evItemScoreValSum').text(sum);
		}

		//가이드라인 상세보기
		function doDetail(){
			var cw=500;
			var ch=300;
			var sw=screen.availWidth;
			var sh=screen.availHeight;
			var px=Math.round((sw-cw)/2);
			var py=Math.round((sh-ch)/2);
			window.open("","popup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
			$("#MyForm").attr("target", "popup");
			$("#MyForm").attr("action", "<c:url value='/edi/evl/selectGuideLineDetailPopup.do'/>");
			$("#MyForm").submit();
		}

		//탭이동
		function fnMoveTab(activeTab, evItemType1Code, evItemType2Code, evItemType3Code) {
			if("<c:out value="${param.progressCode}"/>" == "300"){
				$("#hiddenForm input[name='activeTab']").val(activeTab);
				$("#hiddenForm input[name='evItemType1Code']").val(evItemType1Code);
				$("#hiddenForm input[name='evItemType2Code']").val(evItemType2Code);
				$("#hiddenForm input[name='evItemType3Code']").val(evItemType3Code);

				$("#hiddenForm input[name='evalNoResult']").val("<c:out value="${vo.evalNoResult}"/>");
				$("#hiddenForm input[name='statusNm']").val("<c:out value="${vo.statusNm}"/>");
				$("#hiddenForm input[name='sellerNameLoc']").val("<c:out value="${vo.sellerNameLoc}"/>");

				$("#hiddenForm input[name='houseCode']").val($('#MyForm input[name=houseCode]').val());
				$("#hiddenForm input[name='sgNo']").val($('#MyForm input[name=sgNo]').val());
				$("#hiddenForm input[name='evNo']").val($('#MyForm input[name=evNo]').val());
				$("#hiddenForm input[name='vendorCode']").val($('#MyForm input[name=vendorCode]').val());
				$("#hiddenForm input[name='evUserId']").val($('#MyForm input[name=evUserId]').val());
				$("#hiddenForm input[name='evTplNo']").val($('#MyForm input[name=evTplNo]').val());
				$("#hiddenForm input[name='visitSeq']").val($('#MyForm input[name=visitSeq]').val());
				$("#hiddenForm input[name='seq']").val($('#MyForm input[name=seq]').val());

				$("#hiddenForm").attr("action", "<c:url value="/edi/evl/SRMEVL0040.do" />");
				$("#hiddenForm").submit()
				return;
			}

			if(!confirm("<spring:message code='msg.srm.alert.moveTab'/>")){ /* spring:message : 페이지 이동시 해당 내용이 저장됩니다. 저장하시겠습니까? */
				$("#hiddenForm input[name='activeTab']").val(activeTab);
				$("#hiddenForm input[name='evItemType1Code']").val(evItemType1Code);
				$("#hiddenForm input[name='evItemType2Code']").val(evItemType2Code);
				$("#hiddenForm input[name='evItemType3Code']").val(evItemType3Code);

				$("#hiddenForm input[name='evalNoResult']").val("<c:out value="${vo.evalNoResult}"/>");
				$("#hiddenForm input[name='statusNm']").val("<c:out value="${vo.statusNm}"/>");
				$("#hiddenForm input[name='sellerNameLoc']").val("<c:out value="${vo.sellerNameLoc}"/>");

				$("#hiddenForm input[name='houseCode']").val($('#MyForm input[name=houseCode]').val());
				$("#hiddenForm input[name='sgNo']").val($('#MyForm input[name=sgNo]').val());
				$("#hiddenForm input[name='evNo']").val($('#MyForm input[name=evNo]').val());
				$("#hiddenForm input[name='vendorCode']").val($('#MyForm input[name=vendorCode]').val());
				$("#hiddenForm input[name='evUserId']").val($('#MyForm input[name=evUserId]').val());
				$("#hiddenForm input[name='evTplNo']").val($('#MyForm input[name=evTplNo]').val());
				$("#hiddenForm input[name='visitSeq']").val($('#MyForm input[name=visitSeq]').val());
				$("#hiddenForm input[name='seq']").val($('#MyForm input[name=seq]').val());

				$("#hiddenForm").attr("action", "<c:url value="/edi/evl/SRMEVL0040.do" />");
				$("#hiddenForm").submit()
			} else {
				var saveInfoList = [];
				var saveInfo = {};
				var html = "";

				if(!validation())return;

				for(var i=1; i<=$('#tbl tr #cnt').length; i++) {
					saveInfo = {};
					saveInfo["houseCode"] = $('#MyForm input[name=houseCode]').val();
					saveInfo["sgNo"] = $('#MyForm input[name=sgNo]').val();
					saveInfo["evNo"] = $('#MyForm input[name=evNo]').val();
					saveInfo["vendorCode"] = $('#MyForm input[name=vendorCode]').val();
					saveInfo["evUserId"] = $('#MyForm input[name=evUserId]').val();
					saveInfo["evTplNo"] = $('#MyForm input[name=evTplNo]').val();
					saveInfo["seq"] = $('#MyForm input[name=seq]').val();

					saveInfo["evIdSeq"] = $(':radio[name=evIdScoreVal'+i+']:checked').parent().find('input[name=evIdSeq'+i+']').val();
					saveInfo["evItemNo"] = $('input[name=evItemNo'+i+']').val();
					saveInfo["evIdScoreVal"] = $(':radio[name=evIdScoreVal'+i+']:checked').val();

					if (saveInfo["evIdScoreVal"] != null) {
						saveInfoList.push(saveInfo);
					}
				}

				$.ajax({
					contentType : 'application/json; charset=utf-8',
					type : 'post',
					dataType : 'json',
					async : false,
					url : '<c:url value="/edi/evl/insertQualityEvaluation.json"/>',
					data : JSON.stringify(saveInfoList),
					success : function(data) {
						if (data.message == "SUCCESS") {
							$("#hiddenForm input[name='activeTab']").val(activeTab);
							$("#hiddenForm input[name='evItemType1Code']").val(evItemType1Code);
							$("#hiddenForm input[name='evItemType2Code']").val(evItemType2Code);
							$("#hiddenForm input[name='evItemType3Code']").val(evItemType3Code);

							$("#hiddenForm input[name='evalNoResult']").val("<c:out value="${vo.evalNoResult}"/>");
							$("#hiddenForm input[name='statusNm']").val("<c:out value="${vo.statusNm}"/>");
							$("#hiddenForm input[name='sellerNameLoc']").val("<c:out value="${vo.sellerNameLoc}"/>");

							$("#hiddenForm input[name='houseCode']").val($('#MyForm input[name=houseCode]').val());
							$("#hiddenForm input[name='sgNo']").val($('#MyForm input[name=sgNo]').val());
							$("#hiddenForm input[name='evNo']").val($('#MyForm input[name=evNo]').val());
							$("#hiddenForm input[name='vendorCode']").val($('#MyForm input[name=vendorCode]').val());
							$("#hiddenForm input[name='evUserId']").val($('#MyForm input[name=evUserId]').val());
							$("#hiddenForm input[name='evTplNo']").val($('#MyForm input[name=evTplNo]').val());
							$("#hiddenForm input[name='visitSeq']").val($('#MyForm input[name=visitSeq]').val());
							$("#hiddenForm input[name='seq']").val($('#MyForm input[name=seq]').val());

							$("#hiddenForm").attr("action", "<c:url value="/edi/evl/SRMEVL0040.do" />");
							$("#hiddenForm").submit();
						} else {
							alert('<spring:message code="msg.common.fail.request"/>');
						}
					}
				});
			}


		}

		//validation
		function validation(){
			var message = "";

			for(var i=1; i<=$('#tbl tr #cnt').length; i++) {
				saveInfo = {};

				saveInfo["evIdScoreVal"] = $(':radio[name=evIdScoreVal'+i+']:checked').val();
				if(saveInfo["evIdScoreVal"] == null) {
					message = '<spring:message code="msg.srm.alert.validation.evalList"/>';/*spring message : 평가항목을 선택하세요.*/
					break;
				}
			}

			if(message != "") {
				alert(message);
				return false;
			}

			return true;
		}

		//등록
		function doSave() {
			var saveInfoList = [];
			var saveInfo = {};
			var html = "";

			if(!validation())return;

			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/

			for(var i=1; i<=$('#tbl tr #cnt').length; i++) {
				saveInfo = {};
				saveInfo["houseCode"] = $('#MyForm input[name=houseCode]').val();
				saveInfo["sgNo"] = $('#MyForm input[name=sgNo]').val();
				saveInfo["evNo"] = $('#MyForm input[name=evNo]').val();
				saveInfo["vendorCode"] = $('#MyForm input[name=vendorCode]').val();
				saveInfo["evUserId"] = $('#MyForm input[name=evUserId]').val();
				saveInfo["evTplNo"] = $('#MyForm input[name=evTplNo]').val();
				saveInfo["seq"] = $('#MyForm input[name=seq]').val();

//				saveInfo["visitSeq"] = $('#MyForm input[name=visitSeq]').val();

				saveInfo["evIdSeq"] = $(':radio[name=evIdScoreVal'+i+']:checked').parent().find('input[name=evIdSeq'+i+']').val();
				saveInfo["evItemNo"] = $('input[name=evItemNo'+i+']').val();
				saveInfo["evIdScoreVal"] = $(':radio[name=evIdScoreVal'+i+']:checked').val();

				if (saveInfo["evIdScoreVal"] != null) {
					saveInfoList.push(saveInfo);
				}
			}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/evl/insertQualityEvaluation.json"/>',
				data : JSON.stringify(saveInfoList),
				success : function(data) {
					if (data.message == "SUCCESS") {
						alert('<spring:message code="msg.common.success.save"/>');/* spring:message : 정상적으로 저장되었습니다. */
					} else {
						alert('<spring:message code="msg.common.fail.request"/>');/* spring:message : 요청처리를 실패하였습니다. */
					}
				}
			});
		}

		//목록
		function doList(){
			if("<c:out value="${param.evalFlag}"/>" == "1"){
				$("#hiddenForm").attr("action", "<c:url value="/edi/evl/SRMEVL0060.do" />");
			} else {
				$("#hiddenForm").attr("action", "<c:url value="/edi/evl/SRMEVL0030.do" />");
			}

			$("#hiddenForm").submit();
		}

		//평가결과
		function doEvalResult() {
			var saveInfo = {};
			saveInfo["houseCode"] = $('#MyForm input[name=houseCode]').val();
			saveInfo["sgNo"] = $('#MyForm input[name=sgNo]').val();
			saveInfo["evNo"] = $('#MyForm input[name=evNo]').val();
			saveInfo["vendorCode"] = $('#MyForm input[name=vendorCode]').val();
			saveInfo["evUserId"] = $('#MyForm input[name=evUserId]').val();
			saveInfo["evTplNo"] = $('#MyForm input[name=evTplNo]').val();
			saveInfo["progressCode"] = "<c:out value="${param.progressCode}"/>";

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/evl/selectQualityEvaluationEvalCheck.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(data) {
					if(data > 0) {
						alert("<spring:message code="msg.srm.alert.validation.evalItem"/>");/* spring:message : 평가항목을 전부 입력해주세요. */
					} else {
						$("#hiddenForm input[name='houseCode']").val($('#MyForm input[name=houseCode]').val());
						$("#hiddenForm input[name='sgNo']").val($('#MyForm input[name=sgNo]').val());
						$("#hiddenForm input[name='evNo']").val($('#MyForm input[name=evNo]').val());
						$("#hiddenForm input[name='vendorCode']").val($('#MyForm input[name=vendorCode]').val());
						$("#hiddenForm input[name='evUserId']").val($('#MyForm input[name=evUserId]').val());
						$("#hiddenForm input[name='evTplNo']").val($('#MyForm input[name=evTplNo]').val());
						$("#hiddenForm input[name='visitSeq']").val($('#MyForm input[name=visitSeq]').val());
						$("#hiddenForm input[name='seq']").val($('#MyForm input[name=seq]').val());


						$("#hiddenForm").attr("action", "<c:url value="/edi/evl/SRMEVL0050.do" />");
						$("#hiddenForm").submit();
					}
				}
			});
		}

		//엑셀다운로드
		function doExcelDown(){
			$("#hiddenForm").attr("action", "<c:url value='/edi/evl/selectQualityEvaluationItemListExcel.do'/>");
			$("#hiddenForm").submit();
		}

		//엑셀업로드
		function doExcelFileUpload(){
			//확장자 체크
			var file = $('#MyForm input[name=excelFile]').val();
			var fileExt = file.substring(file.lastIndexOf('.')+1);
			var fileName = file.substring(file.lastIndexOf('\\')+1);

			var target = "품질경영평가표";
			if(!$.trim(file)){
				alert("<spring:message code='msg.srm.alert.file' arguments='"+target+"'/>");
				return false;
			}

			if(file != "" && !fileExtCheck("XLS|XLSX", fileExt)){
				var target1 = "품질경영평가표";
				var target2 = "XLS|XLSX";
				alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");
				return false;
			}

			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/

			$("#MyForm").attr("action", "<c:url value='/edi/evl/SRMEVLselectQualityEvaluationItemListExcelUpload.do'/>");
			$("#MyForm").submit();
		}
		
		//전체 적합 선택
		//2019-01-07 오승현		
		function doAllChekcked() {
			for(var i=1; i<=$('#tbl tr #cnt').length; i++) {
				$('#evIdScoreVal'+i).prop("checked",true);
				$('#evItemSpan'+i).html($('#evIdScoreVal'+i).val());
				evItemScoreValSum();			
			}
		}
	</script>


</head>


<body>
<form id="MyForm" name="MyForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value="${param.houseCode}"/>"/>
	<input type="hidden" id="sgNo" name="sgNo" value="<c:out value="${param.sgNo}"/>"/>
	<input type="hidden" id="evNo" name="evNo" value="<c:out value="${param.evNo}"/>"/>
	<input type="hidden" id="vendorCode" name="vendorCode" value="<c:out value="${param.vendorCode}"/>"/>
	<input type="hidden" id="evUserId" name="evUserId" value="<c:out value="${param.evUserId}"/>"/>
	<input type="hidden" id="evTplNo" name="evTplNo" value="<c:out value="${param.evTplNo}"/>"/>
	<input type="hidden" name="visitSeq" id="visitSeq" value="<c:out value="${param.visitSeq}"/>"/>
	<input type="hidden" name="seq" id="seq" value="<c:out value="${param.seq}"/>"/>

	<input type="hidden" id="evalNoResult" name="evalNoResult" value="<c:out value="${param.evalNoResult}"/>" />
	<input type="hidden" id="statusNm" name="statusNm" value="<c:out value="${param.statusNm}"/>" />
	<input type="hidden" id="sellerNameLoc" name="sellerNameLoc" value="<c:out value="${param.sellerNameLoc}"/>" />
	
	
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<c:if test="${param.evalFlag eq '0'}">
					<h2 class="tit_page"><spring:message code="evlHeader.menu.text2" /></h2>	<%-- 입점평가--%>
					<p class="page_path"><spring:message code="evlHeader.menu.text2" /><span><spring:message code="text.srm.field.srmevl0040.title"/></span></p>
				</c:if>
				<c:if test="${param.evalFlag eq '1'}">
					<h2 class="tit_page"><spring:message code="evlHeader.menu.text3" /></h2>	<%-- 정기평가--%>
					<p class="page_path"><spring:message code="evlHeader.menu.text3" /><span><spring:message code="text.srm.field.srmevl0040.title"/></span></p>
				</c:if>

			</div>
			<!-- END 서브상단 -->
			
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0040.sub.title1"/></h3>	<%-- 품질평가 기본정보--%>
				<%--<c:if test="${param.progressCode ne '300'}">--%>
					<div class="right_btns">
						<button type="button" id="" name="" class="btn_normal" onclick="javascript:doEvalResult();"><spring:message code="button.srm.evalResult"/></button><%--spring:message : 평가결과--%>
						<button type="button" class="btn_normal" onclick="javascript:doExcelDown();"><spring:message code="button.srm.excel"/></button><%--spring:message : Excel--%>
						<button type="button" id="" name="" class="btn_normal" onclick="javascript:doList();"><spring:message code="button.srm.list"/></button><%--spring:message : 목록--%>
					</div>
				<%--</c:if>--%>
			</div>
			
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="15%"/>
					<col width="20%"/>
					<col width="15%"/>
					<col width="20%"/>
					<col width="15%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code="text.srm.field.evalNoResult"/></th>	<%-- 의뢰번호 --%>
						<td><c:out value="${vo.evalNoResult}"/>-<c:out value="${vo.visitSeq}"/></td>
						<th><spring:message code="text.srm.field.srmevl0040.sellerNameLoc"/></th>	<%-- 파트너사 --%>
						<td><c:out value="${vo.sellerNameLoc}"/></td>
						<th><spring:message code="text.srm.field.status"/></th>	<%-- 진행상태 --%>
						<td><c:out value="${vo.statusNm}"/></td>
					</tr>
				</tbody>
			</table>
			
			<%----- Tab Start -------------------------%>
			<ul class="sub_tab free_width mt30">
				<c:forEach items="${tabList}" var="tab" varStatus="status">
					<c:if test="${param.activeTab == status.count}">
						<li class="on" rel="tab<c:out value="${status.count}" />">
							<a href="#" onClick="fnMoveTab('<c:out value="${status.count}" />', '<c:out value="${tab.evItemType1Code}" />', '<c:out value="${tab.evItemType2Code}" />', '<c:out value="${tab.evItemType3Code}" />');"><c:out value="${tab.evItemType1CodeName}" /></a>
						</li>
					</c:if>
					<c:if test="${param.activeTab != status.count}">
						<li rel="tab<c:out value="${status.count}" />">
							<a href="#" onClick="fnMoveTab('<c:out value="${status.count}" />', '<c:out value="${tab.evItemType1Code}" />', '<c:out value="${tab.evItemType2Code}" />', '<c:out value="${tab.evItemType3Code}" />');"><c:out value="${tab.evItemType1CodeName}" /></a>
						</li>
					</c:if>
				</c:forEach>
			</ul>
			<%----- Tab End -------------------------%>

			<c:if test="${param.progressCode ne '300'}">
				<div class="right_btns">				
					<button type="button" id="" name="" class="btn_normal btn_normal" onclick="javascript:doAllChekcked();"><spring:message code="button.srm.allselect"/></button><%--spring:message : 전체적합선택--%>  	
					<div style="display:inline-block; border: 1px solid #aaaaaa; padding: 2px 5px;">
						<input type="file" id="excelFile" name="excelFile"/>					
						<button type="button" id="" name="" class="btn_normal btn_blue" onclick="javascript:doExcelFileUpload();"><spring:message code="button.srm.reg"/></button><%--spring:message : 등록--%>
					</div>					
					<button type="button" id="" name="" title="<spring:message code="button.srm.save"/>" class="btn_normal btn_red" onclick="javascript:doSave();"><spring:message code="button.srm.save"/></button><%--spring:message : 저장--%>
				</div>
			</c:if>

			<p style="padding-top: 5px;" />
			<table id="tbl" class="tbl_st1 form_style">
				<colgroup>
					<col width="5%"/>
					<col width="20%"/>
					<!-- <col width="5%"/> -->
					<col width="*"/>
					<col width="20%"/>
					<col width="8%"/>
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="table.srm.colum.title.no"/></th><%--spring:message : No--%>
						<th><spring:message code="table.srm.srmevl0040.colum.title2"/></th><%--spring:message : 점검 기준--%>
						<!--  <th><spring:message code="table.srm.srmevl0040.colum.title3"/></th>--><%--spring:message : 점수--%>
						<th><spring:message code="table.srm.srmevl0040.colum.title4"/></th><%--spring:message : 가이드라인--%>
						<th><spring:message code="table.srm.srmevl0040.colum.title5"/></th><%--spring:message : 평가--%>
						<th><spring:message code="table.srm.srmevl0040.colum.title6"/></th><%--spring:message : 선택점수--%>
					</tr>
				</thead>

				<tbody>

				<c:set var="preEvItem2Code" value="" />
				<c:set var="preEvItemNo" value="" />
				<c:set var="preCount" value="0" />
				<c:set var="evItemScore" value="0" />
				<c:forEach items="${itemList}" var="item" varStatus="status">
					<tr>
						<c:if test="${item.evItemNo != preEvItemNo}">
							<c:set var="preCount" value="${preCount + 1}" />

							<td id="cnt" rowspan="<c:out value="${item.subRowSpan}" />" style="text-align: center;">
								<c:out value="${preCount}" />
							</td>

							<c:if test="${item.evItemType2Code != preEvItem2Code}">
								<td rowspan="<c:out value="${item.rowSpan}" />" style="text-align: center; font-weight:bold;">
									<c:out value="${item.evItemType2CodeName}" />
								</td>
							</c:if>

							<%-- <td rowspan="<c:out value="${item.subRowSpan}" />" style="text-align:center; font-weight:bold;">
								<span id="evIdScore"><c:out value="${item.evIdScore}" /></span>
							</td> --%>

							<td rowspan="<c:out value="${item.subRowSpan}" />">
								<%
									//치환 변수 선언
									pageContext.setAttribute("cr", "\r"); //Space
									pageContext.setAttribute("cn", "\n"); //Enter
									pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
									pageContext.setAttribute("br", "<br/>"); //br 태그
								%>

									<%--<a href="#" onclick="javascript:doDetail();">--%>
									<%--<img src="<c:out value='/images/epc/btn/icon_02.png'/>"/>--%>
									<%--</a>--%>
								<c:set var="evItemContents" value="${fn:replace(item.evItemContents, cn, br)}" />
								<c:set var="evItemContents" value="${fn:replace(evItemContents, cr, br)}" />

								<c:out value="${evItemContents}" escapeXml="false" />
							</td>
							<td>
								<input type="radio" id="evIdScoreVal<c:out value="${preCount}" />" name="evIdScoreVal<c:out value="${preCount}" />" value="<c:out value="${item.evIdScore}" />" <c:if test="${item.evIdScoreVal eq item.evIdScore}">checked</c:if> <c:if test="${param.progressCode eq '300'}"> disabled = "disabled"</c:if> /> <c:out value="${item.evIdContents}" />
								<input type="hidden" id="evItemNo<c:out value="${preCount}" />" name="evItemNo<c:out value="${preCount}" />" value="<c:out value="${item.evItemNo}"/>"/>
								<input type="hidden" id="evIdSeq<c:out value="${preCount}" />" name="evIdSeq<c:out value="${preCount}" />" value="<c:out value="${item.evIdSeq}"/>"/>
							</td>
							<td rowspan="<c:out value="${item.subRowSpan}" />" style="text-align: center;">
								<span id="evItemSpan<c:out value="${preCount}"/>" style="font-weight:bold;"></span>
							</td>
						</c:if>
						<c:if test="${item.evItemNo == preEvItemNo}">
							<td>
								<input type="hidden" id="evIdSeq<c:out value="${preCount}" />" name="evIdSeq<c:out value="${preCount}" />" value="<c:out value="${item.evIdSeq}"/>"/>
								<input type="radio" id="evIdScoreVal<c:out value="${preCount}" />" name="evIdScoreVal<c:out value="${preCount}" />" value="<c:out value="${item.evIdScore}" />" <c:if test="${item.evIdScoreVal eq item.evIdScore}">checked</c:if> <c:if test="${param.progressCode eq '300'}"> disabled = "disabled"</c:if> /> <c:out value="${item.evIdContents}" />
							</td>
						</c:if>
					</tr>
					<c:set var="preEvItemNo" value="${item.evItemNo}" />
					<c:set var="preEvItem2Code" value="${item.evItemType2Code}" />
				</c:forEach>
				<tr>
					<th colspan="2">
						<spring:message code="text.srm.field.srmevl0040.totResult"/><%--spring:message : 카테고리 총 결과--%>
					</th>
					<!-- <th>
						<span id="evItemScoreSum"></span>
					</th> -->
					<th></th>
					<th colspan="2">
						<span id="evItemScoreValSum"></span>
					</th>
				</tr>
				</tbody>
			</table>
			
		</div>
	</div>
	</form>

	<form id="hiddenForm" name="hiddenForm" method="post">
		<input type="hidden" id="evTplNo" name="evTplNo" value="<c:out value="${param.evTplNo}" />" />
		<input type="hidden" id="activeTab" name="activeTab" value="<c:out value="${param.activeTab}" />" />
		<input type="hidden" id="evItemType1Code" name="evItemType1Code" value="<c:out value="${vo.evItemType1Code}" />" />
		<input type="hidden" id="evItemType2Code" name="evItemType2Code" value="<c:out value="${vo.evItemType2Code}" />" />
		<input type="hidden" id="evItemType3Code" name="evItemType3Code" value="<c:out value="${vo.evItemType3Code}" />" />
	
		<input type="hidden" id="evalNoResult" name="evalNoResult" value="<c:out value="${param.evalNoResult}"/>" />
		<input type="hidden" id="statusNm" name="statusNm" value="<c:out value="${param.statusNm}"/>" />
		<input type="hidden" id="sellerNameLoc" name="sellerNameLoc" value="<c:out value="${param.sellerNameLoc}"/>" />
	
		<input type="hidden" name="houseCode" id="houseCode" value="<c:out value="${param.houseCode}"/>" />
		<input type="hidden" name="sgNo" id="sgNo" value="<c:out value="${param.sgNo}"/>" />
		<input type="hidden" name="evNo" id="evNo" value="<c:out value="${param.evNo}"/>" />
		<input type="hidden" name="vendorCode" id="vendorCode" value="<c:out value="${param.vendorCode}"/>" />
		<input type="hidden" name="evUserId" id="evUserId" value="<c:out value="${param.evUserId}"/>" />
		<input type="hidden" name="visitSeq" id="visitSeq" value="<c:out value="${param.visitSeq}"/>" />
		<input type="hidden" name="seq" id="seq" value="<c:out value="${param.seq}"/>" />
		<input type="hidden" name="evalFlag" id="evalFlag" value="<c:out value="${param.evalFlag}"/>" />

		<input type="hidden" name="progressCode" id="progressCode" value="<c:out value="${param.progressCode}"/>" />

	</form>

</body>
</html>