<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Page Name 	: SRMVEN003001.jsp
	Description : 대표자 SRM 모니터링 상세화면
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.08.25		LEE HYOUNG TAK		최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="../common.jsp"%>

	<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
	<link href="/css/epc/edi/srm/style.css" rel="stylesheet" type="text/css" />

	<!-- ECharts -->
	<script src="/js/epc/edi/srm//echarts.js"></script>

	<title><spring:message code="text.srm.field.srmven003001.title"/></title><%--spring:message : SRM 모니터링 상세화면 --%>

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
			<c:if test="${empty list}">
			alert("<spring:message code="msg.srm.alert.srmjon0030.notListData"/>");
			window.close();
			</c:if>
//		alert($(".on").html());
			drawChart($("#MyForm li").eq(0));
			currentValueDrawChart();
		});

		//LIST 상세 ROW
		function doDetail(obj){
			var plusMinus = $(obj).find("span").html();
			var evItemNo = $(obj).parent().parent().find("#evItemNo").val();
			if(plusMinus == "+") {
				$(obj).find("span").html("-");
				$('#MyForm table[name=tblInfo] tbody[id=dataListbody] tr').each(function(){
					if($(this).find("#evItemNo").val() == evItemNo && $(this).find("#sortSeq").val() != 0){
						$(this).show();
					}
				});
			} else if(plusMinus == "-") {
				$(obj).find("span").html("+");
				$('#MyForm table[name=tblInfo] tbody[id=dataListbody] tr').each(function(){
					if($(this).find("#evItemNo").val() == evItemNo && $(this).find("#sortSeq").val() != 0){
						$(this).hide();
					}
				});
			}

		}

		//전체 LIST Row 보이기/숨기기
		function doAllDisplay(obj){
			var plusMinus = $(obj).find("span").html();
			if(plusMinus == "+"){
				$('#MyForm table[name=tblInfo] tbody[id=dataListbody] tr').each(function(){
					if($(this).attr("id") != "title" && $(this).find("#sortSeq").val() != 0){
						$(this).show();
					}
				});
				$('.plusMinus').html("-");
			} else if(plusMinus == "-"){
				$('#MyForm table[name=tblInfo] tbody[id=dataListbody] tr').each(function(){
					if($(this).attr("id") != "title" && $(this).find("#sortSeq").val() != 0){
						$(this).hide();
					}
				});
				$('.plusMinus').html("+");
			}
		}

		//차트 보이기/숨기기
		function doChartDetail(obj){
            if($(obj).attr("class") == "on") return;

			var evItemNo = $(obj).find("#evItemNo").val();
			$('#MyForm ul li').each(function(){
				$(this).removeClass("on");
				$(this).addClass("off");
				$(this).find(".chart").empty();
			});

			$(obj).removeClass("off");
			$(obj).addClass("on");
			drawChart(obj);
		}

		//차트그리기
		function drawChart(obj){
			var searchInfo = {};
			var evItemNo = $(obj).find("#evItemNo").val();

			searchInfo["evItemNo"] =  $(obj).find("#evItemNo").val();
			searchInfo["vendorCode"] =  $(obj).find("#vendorCode").val();
			searchInfo["catLv2Code"] =  $(obj).find("#catLv2Code").val();
			searchInfo["countYear"] =  $(obj).find("#countYear").val();
			searchInfo["countMonth"] =  $(obj).find("#countMonth").val();
			searchInfo["evTplNo"] =  $(obj).find("#evTplNo").val();

			var dataSetList = [];
			var dataSet = {};
			var labelList = [];
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/ven/selectSRMmoniteringDetailChart.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					var length = 3;
					//업체
					var chartList = [];
					dataSet = {}
					for(var i=0; i<data.length; i++){
//						labelList.push(data[i].countYear + "-" + data[i].countMonth);
						labelList.push(data[i].countMonth + "<spring:message code="text.srm.field.month"/>");
						chartList.push(data[i].resultValueVendor);
						if(length < data[i].resultValueVendor.split(".")[0].length){
							length = data[i].resultValueVendor.split(".")[0].length;
						}

					}
					dataSet["name"] = "<c:out value="${vo.vendorName}"/>";
					dataSet["type"] = "line";
					dataSet["data"] = chartList;
					dataSetList.push(dataSet);

					//평균
					var chartList = [];
					dataSet = {}
					for(var i=0; i<data.length; i++){
//						labelList.push(data[i].countYear + "-" + data[i].countMonth);
						labelList.push(data[i].countMonth + "<spring:message code="text.srm.field.month"/>");
						chartList.push(data[i].resultValueAvg);
						if(length < data[i].resultValueAvg.split(".")[0].length){
							length = data[i].resultValueAvg.split(".")[0].length;
						}
					}
					dataSet["name"] = "<spring:message code='text.srm.field.venCdAll' />";
					dataSet["type"] = "line";
					dataSet["data"] = chartList;
					dataSetList.push(dataSet);

					var lineTheme = {
						color: [
							'#1293f8', '#FCFF8B'			//bar 색 순서대로 적용
						],
						legend: {
							x: 'center',
							y: 'bottom',
							orient: 'horizontal',
							textStyle: {
								color: '#ffffff'
							}
						},
						grid: {
							x: 40,
							y: 10,
							x2: 10,
							y2: 50
						},
						tooltip: {
							backgroundColor: 'rgba(0,0,0,0.5)'		//툴팁배경색
						},
						animation: false
					};

					var echartLine = echarts.init(document.getElementById('line-chart_'+evItemNo+'_0'), lineTheme);

					echartLine.setOption({
						tooltip: {
							trigger: 'axis'
						},
						legend: {
							data: ['<c:out value="${vo.vendorName}"/>', '<spring:message code='text.srm.field.venCdAll' />']
						},
						xAxis: [{
							boundaryGap: true,
							data: labelList,
							axisLabel: {
								textStyle: {
									color: '#ffffff',
									fontSize: 12
								}
							}
						}],
						yAxis: [{
							axisLabel: {
								textStyle: {
									color: '#ffffff',
									fontSize: 12 - (length -3)
								}
							}
						}],
						series: dataSetList
					});
				}
			});

		}

		//현재까지평가점수
		function currentValueDrawChart(){

			var searchInfo = {};
			var dataSetList = [];
			var dataSet = {};
			searchInfo["vendorCode"] =  "<c:out value="${vo.vendorCode}"/>";
			searchInfo["catLv2Code"] =  "<c:out value="${vo.catLv2Code}"/>";
			searchInfo["countYear"] =  "<c:out value="${vo.countYear}"/>";
			searchInfo["countMonth"] =  "<c:out value="${vo.countMonth}"/>";
			searchInfo["egNo"] =  "<c:out value="${vo.egNo}"/>";
			searchInfo["evTplNo"] =  "<c:out value="${vo.evTplNo}"/>";
			searchInfo["vendorCode"] =  "<c:out value="${vo.vendorCode}"/>";

			var xAxisData = [];
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/ven/selectSRMmoniteringDetailCurrentValue.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {

					var compData = data.compCurrentValue;
					var allData = data.allCurrentValue;
					//업체
					var chartList = [];
					dataSet = {};
					for(var i=0; i<compData.length; i++){
						xAxisData.push(compData[i].evItemType2Name);
						chartList.push(compData[i].evScoreAvg);
					}
					dataSet["name"] = "<c:out value="${vo.vendorName}"/>";
					dataSet["type"] = "bar";
					dataSet["data"] = chartList;
					dataSetList.push(dataSet);

					//전체
					var chartList = [];
					dataSet = {}
					for(var i=0; i<allData.length; i++){
						chartList.push(allData[i].evScoreAvg);
					}
					dataSet["name"] = "<spring:message code='text.srm.field.venCdAll' />";
					dataSet["type"] = "bar";
					dataSet["data"] = chartList;
					dataSetList.push(dataSet);
				}
			});

			var barTheme = {
				bar: {
					itemStyle: {
						normal: {
							barBorderRadius : 5
						}
					}
				},
				color: [
					'#ecac00', '#bd00df'						//bar 색 순서대로 적용
				],
				legend: {
					x: 'center',
					y: 'bottom',
					orient: 'horizontal',
					textStyle: {
						color: '#ffffff'
					}
				},
				grid: {
					x: 30,
					y: 30,
					x2: 2,
					y2: 45
				},
				tooltip: {
					backgroundColor: 'rgba(0,0,0,0.5)',		//툴팁배경색
					formatter: function (params,ticket,callback) {
						var res = params[0].name;
						for (var i = 0, l = params.length; i < l; i++) {
							if(params[i].value.split(".")[0]==""){
								res += '<br/>' + params[i].seriesName + ' : ' + "0" + params[i].value;
							} else if(params[i].value.split(".")[1]==null){
								res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + ".0";
							} else {
								res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
							}
						}

						setTimeout(function (){
							callback(ticket, res);
						}, 0)
						return '';
					}
				},
				animation: false
			};

			var echartLine = echarts.init(document.getElementById('bar-chart'), barTheme);

			echartLine.setOption({
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					data: ['<c:out value="${vo.vendorName}"/>', '<spring:message code='text.srm.field.venCdAll' />']
				},
				//X축설정
				xAxis: [{
					boundaryGap: true,
					data: xAxisData,
					axisLabel: {
						textStyle: {
							color: '#ffffff',
							fontSize: 12 - ((xAxisData.length-2) * 3)
						}
					}
				}],
				//Y축설정
				yAxis: [{
					axisLabel: {
						textStyle: {
							color: '#ffffff',
							fontSize: 12
						}
					}
				}],
				series: dataSetList
			});
		}

		//비고, 특이사항 팝업
		function popup(gugun, evItemNo, evIeSeq, grade){
			//등급
			$('#popupForm input[name=evItemNo]').val(evItemNo);
			$('#popupForm input[name=evIeSeq]').val(evIeSeq);
			$('#popupForm input[name=grade]').val(grade);

			if(gugun =="remark"){
				//비고
				PopupWindow(450, 200, "<c:url value="/edi/ven/selectSRMmoniteringDetailRemarkPopup.do"/>","remarkPopup");
			} else if(gugun =="etc"){
				//특이사항
				PopupWindow(450, 200, "<c:url value="/edi/ven/selectSRMmoniteringDetailEtcPopup.do"/>","etcPopup");
			} else if(gugun =="grade"){
				PopupWindow(700, 200, "<c:url value="/edi/ven/selectSRMmoniteringDetailGradeExemplePopup.do"/>","gradePopup");
			} else if(gugun =="plc"){
				$('#popupForm input[name=countYear]').val("<c:out value="${countYear}"/>");
				$('#popupForm input[name=countMonth]').val("<c:out value="${countMonth}"/>");
				$('#popupForm input[name=vendorCode]').val("<c:out value="${list[0].vendorCode}"/>");
				$('#popupForm input[name=catLv2Code]').val("<c:out value="${list[0].catLv2Code}"/>");
				PopupWindow(500, 500, "<c:url value="/edi/ven/selectSRMmoniteringDetailPlcPopup.do"/>","plcPopup");
			}
		}

		function defectivePopup(){
			if("<c:out value="${defective}"/>" == "0"){
				alert("불량등록이 없습니다.");
				return;
			} else {
				$('#popupForm input[name=countYear]').val("<c:out value="${countYear}"/>");
				$('#popupForm input[name=countMonth]').val("<c:out value="${countMonth}"/>");
				$('#popupForm input[name=vendorCode]').val("<c:out value="${vo.vendorCode}"/>");
				$('#popupForm input[name=catLv2Code]').val("<c:out value="${list[0].catLv2Code}"/>");
				PopupWindow(900, 500, "<c:url value="/edi/ven/selectSRMmoniteringDetailDefectivePopup.do"/>","defectivePopup");
			}
		}

		/* 팝업창 */
		function PopupWindow(cw, ch, url, popNm) {
			var sw = screen.availWidth;
			var sh = screen.availHeight;
			var px = Math.round((sw-cw)/2);
			var py = Math.round((sh-ch)/2);

			window.open("", popNm, "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");

			$("#popupForm").attr("action", url);
			$("#popupForm").attr("target", popNm);
			$("#popupForm").submit();
		}
	</script>
	<style>
		.accordian li.off .col-md-12{display:none;}
	</style>

</head>

<body style="width:100%;height:100%; overflow-X:hidden;">
<form id="MyForm" name="MyForm" method="POST">
	<div class="popWrap" style="width:100%">
		<div class="popTitle"><img src="/images/epc/edi/srm/popTitle.jpg"><%--<span><img src="/images/epc/edi/srm/icoClose.jpg"></span>--%></div>
		<!--왼쪽영역-->
		<table style="width:95%;height:95%;">
			<colgroup>
				<col width="240px"/>
				<col width="95%"/>
			</colgroup>
			<tbody>
			<tr>
				<td style="vertical-align: top;">
					<div class="popLeftArea">
						<ul class="accordian">
							<c:forEach var="list" items="${list}" varStatus="status">
								<c:if test="${list.sortSeq eq 0}">
									<li id="trMain" <c:if test="${status.count eq '1'}">class="on"</c:if>  <c:if test="${status.count ne '1'}">class="off"</c:if> onclick="javascript:doChartDetail(this)" style="cursor: pointer;">
										<span style="font-weight:bold;"><c:out value="${list.elName}"/> TREND</span>
										<input type="hidden" id="sortSeq" name="sortSeq" value="<c:out value="${list.sortSeq}"/>"/>
										<input type="hidden" id="evItemNo" name="evItemNo" value="<c:out value="${list.evItemNo}"/>"/>

										<input type="hidden" id="vendorCode" name="vendorCode" value="<c:out value="${list.vendorCode}"/>"/>
										<input type="hidden" id="catLv2Code" name="catLv2Code" value="<c:out value="${list.catLv2Code}"/>"/>
										<input type="hidden" id="countYear" name="countYear" value="<c:out value="${list.countYear}"/>"/>
										<input type="hidden" id="countMonth" name="countMonth" value="<c:out value="${list.countMonth}"/>"/>
										<input type="hidden" id="evTplNo" name="evTplNo" value="<c:out value="${list.evTplNo}"/>"/>

										<div class="col-md-12" style="padding-left:0px;padding-right:0px;" >
											<!-- LINE CHART -->
											<div class="box box-info">
												<div class="box-body chart-responsive">
													<div id="line-chart_<c:out value="${list.evItemNo}"/>_<c:out value="${list.sortSeq}"/>" style="height:150px;"></div>
												</div><!-- /.box-body -->
											</div><!-- /.box -->
										</div><!-- /.col (RIGHT) -->
									</li>
								</c:if>
							</c:forEach>
						</ul>
						<div class="score">
							<p class="scoreTitle" style="text-align: center;"><spring:message code="text.srm.field.moniter.evalscore"/> </p>
							<div class="col-md-12" style="padding-left:0px;padding-right:0px;" >
								<!-- LINE CHART -->
								<div class="box box-info">
									<div class="box-body chart-responsive">
										<%--<div class="chart" id="bar-chart" style="height:150px;background: #0B1014;"></div>--%>
										<div id="bar-chart" style="height:150px;"></div>
									</div><!-- /.box-body -->
								</div><!-- /.box -->
							</div><!-- /.col (RIGHT) -->
							<p class="scoreText">
								<c:set var="evItemType2" value=""/>
								<c:set var="evItemType2Text" value=""/>
								<c:set var="plcValue" value=""/>
								<c:forEach var="list" items="${list}" varStatus="status">
									<c:if test="${list.improveCount eq 'Y' && list.evItemType2Code ne evItemType2 && not empty evItemType2Text}">
										<c:set var="evItemType2" value="${list.evItemType2Code}"/>
										<c:set var="evItemType2Text">
											<c:out value="${evItemType2Text}"/>, <c:out value="${list.evItemType2Name}"/>
										</c:set>
									</c:if>
									<c:if test="${list.improveCount eq 'Y' && list.evItemType2Code ne evItemType2 && empty evItemType2Text}">
										<c:set var="evItemType2" value="${list.evItemType2Code}"/>
										<c:set var="evItemType2Text" value="${list.evItemType2Name}"/>
									</c:if>

									<c:if test="${list.evItemNo eq 'EVI16050085'}">
										<c:set var="plcValue" value="true"/>
									</c:if>
								</c:forEach>
								<c:if test="${not empty evItemType2Text}">
								<%--<spring:message code="text.srm.field.moniter.notice1"/>--%>
								<%--<c:out value="${evItemType2Text}"/>--%>
								<%--<spring:message code="text.srm.field.moniter.notice2"/>--%>
									<c:out value="${fn:replace(list[0].rtnContent, '$lowArea$', evItemType2Text)}"/>
								</c:if>
							</p>
						</div>
						<c:if test="${plcValue eq 'true'}">
							<div class="complain2" style="text-align:center;"><a href="#" onClick="popup('plc');"><spring:message code="text.srm.field.moniter.plcGradelist"/></a></div>
							<div style='height:2px;'></div>
						</c:if>
						<div class="complain2" style="text-align:center;"><a href='#' onClick='defectivePopup();'>불량등록건수</a><span><c:out value="${defective}"/></span></div>
						<div style='height:2px;'></div>
						<div class="complain" style="text-align:center;"><spring:message code="text.srm.field.moniter.complainCnt"/><span><c:out value="${claimCount}"/></span></div>
					</div>
				</td>
				<td style="vertical-align: top;">
					<!--컨텐츠영역-->
					<div class="popContArea" style="width:100%">
						<table width="100%">
							<tbody>
								<tr>
									<td>
										<div align="left" style="font-size: 11px; font-weight:bold;"><spring:message code="text.srm.field.catLv1Code"/>: <c:out value="${list[0].catLv1Name}"/>(<c:out value="${list[0].catLv1Code}"/>)</div>
										<div align="left" style="font-size: 11px; font-weight:bold;"><spring:message code="text.srm.field.moniter.catLv1Code2"/>: <c:out value="${list[0].catLv2Name}"/>(<c:out value="${list[0].catLv2Code}"/>)</div>
										<div align="left" style="font-size: 11px; font-weight:bold;"><spring:message code="text.srm.field.venCd"/>: <c:out value="${list[0].vendorCode}"/></div>
									</td>
									<%--<td class="popContitle_right">--%>

										<%--<c:if test="${grade.evalGradeClass eq 'A'}">--%>
											<%--<font style="color:blue;" size="7px"><c:out value="${grade.evalGradeClass}"/>(<c:out value="${grade.evScoreSum}"/>)</font>--%>
										<%--</c:if>--%>
										<%--<c:if test="${grade.evalGradeClass eq 'B'}">--%>
											<%--<font style="color:green;" size="7px"><c:out value="${grade.evalGradeClass}"/>(<c:out value="${grade.evScoreSum}"/>)</font>--%>
										<%--</c:if>--%>
										<%--<c:if test="${grade.evalGradeClass eq 'C'}">--%>
											<%--<font style="color:#FF702C;" size="7px"><c:out value="${grade.evalGradeClass}"/>(<c:out value="${grade.evScoreSum}"/>)</font>--%>
										<%--</c:if>--%>
										<%--<c:if test="${grade.evalGradeClass eq 'D'}">--%>
											<%--<font style="color:red;" size="7px"><c:out value="${grade.evalGradeClass}"/>(<c:out value="${grade.evScoreSum}"/>)</font>--%>
										<%--</c:if>--%>
									<%--</td>--%>
								</tr>
								<tr>
									<td class="popContitle" colspan="2">
										<c:out value="${vo.vendorName}"/>(<c:out value="${vo.evTplSubject}"/>)
									</td>
								</tr>
							</tbody>
						</table>
						<div align="left" style="font-size: 11px;font-weight: bold; float: left; width: 50%;"><spring:message code="text.srm.field.alerttxt"/></div>
						<div align="right" style="font-size: 15px;font-weight: bold; float: left; width: 50%;"><c:out value="${countYear}"/><spring:message code="text.srm.field.year"/> <c:out value="${countMonth}"/><spring:message code="text.srm.field.month"/></div>

						<table id="tblInfo" name="tblInfo" cellpadding="0" cellspacing="0" class="popTableList">
							<colgroup id="tbHead1">
								<col width="*" />
								<col width="6%" />
								<col width="15%" />
								<col width="8%" />
								<col width="11%" />
								<col width="7%" />
								<col width="10%" />
							</colgroup>
							<thead id="tbHead2">
							<tr>
								<th class="c_title_2">
									<a href="#" onclick="javascript:doAllDisplay(this);">
										<spring:message code="table.srm.srmven003001.colum.title2"/><%--spring:message : 구분--%>
										(<span class="plusMinus">+</span>)
									</a>
								</th>
								<th class="c_title_2"><spring:message code="table.srm.srmven003001.colum.title3"/></th><%--spring:message : 등급--%>
								<th class="c_title_2"><spring:message code="table.srm.srmven003001.colum.title4"/></th><%--spring:message : 당월--%>
								<th class="c_title_2"><spring:message code="table.srm.srmven003001.colum.title5"/></th><%--spring:message : 단위--%>
								<th class="c_title_2"><spring:message code="table.srm.srmven003001.colum.title6"/></th><%--spring:message : 개선필요--%>
								<th class="c_title_2"><spring:message code="table.srm.srmven003001.colum.title7"/></th><%--spring:message : 비고--%>
								<th class="c_title_2"><spring:message code="table.srm.srmven003001.colum.title8"/></th><%--spring:message : 특이사항--%>
							</tr>
							</thead>
							<tbody id="dataListbody">
							<p>
								<c:set var="title" value=""/>
								<c:forEach var="list" items="${list}" varStatus="status">
									<c:if test="${list.sortSeq eq 0}">
										<c:if test="${title ne list.evItemType2Name}">
											<tr id="title">
												<td class="puple" colspan="7">
													<c:out value="${list.evItemType2Name}"/>
												</td>
											</tr>
											<c:set var="title" value="${list.evItemType2Name}"/>
										</c:if>
										<tr id="firstData" class="skyblue">
											<td class="left"  style="cursor: pointer; font-weight: bold;">
												<div onclick="javascript:doDetail(this)">
													*<c:out value="${list.elName}"/>
													(<span class="plusMinus">+</span>)
												</div>
											</td>
											<td class="center" style="font-weight:bold;">
												<a href="#" onclick="javascript:popup('grade','<c:out value="${list.evItemNo}"/>','<c:out value="${list.evIeSeq}"/>', '<c:out value="${list.itemGrade}"/>');"><c:out value="${list.itemGrade}"/></a>
											</td>
											<c:set var="won">
												<spring:message code="text.srm.field.moniter.won"/>
											</c:set>
											<td class="right" style="font-weight:bold;">
												<c:if test="${list.elUnit eq won}">
													<fmt:formatNumber value="${list.resultValue}" pattern="#,##0" />
												</c:if>
												<c:if test="${list.elUnit ne won}">
													<fmt:formatNumber value="${list.resultValue}" pattern="#,##0.0" />
												</c:if>
											</td>
											<td class="center" style="font-weight:bold;">
												<c:out value="${list.elUnit}"/>
											</td>
											<td>
												<c:if test="${list.improveCount eq 'Y'}">
													<img src="/images/epc/edi/srm/icoDot.png">
												</c:if>
											</td>
											<td>
												<c:if test="${list.remarkYn eq 'Y'}">
													<a href="#" onclick="javascript:popup('remark','<c:out value="${list.evItemNo}"/>','<c:out value="${list.evIeSeq}"/>');"><img src="/images/epc/btn/bt_search.gif" align="absmiddle" border="0" alt="" width="20px"></a>
												</c:if>
											</td>
											<td class="last">
												<c:if test="${list.resultEtc eq '1' && list.spYn eq 'Y'}">
													<a href="#" onclick="javascript:popup('etc','<c:out value="${list.evItemNo}"/>','<c:out value="${list.evIeSeq}"/>');"><img src="/images/epc/btn/bt_search.gif" align="absmiddle" border="0" alt="" width="20px"></a>
												</c:if>
												<input type="hidden" id="sortSeq" name="sortSeq" value="<c:out value="${list.sortSeq}"/>"/>
												<input type="hidden" id="evItemNo" name="evItemNo" value="<c:out value="${list.evItemNo}"/>"/>
											</td>
										</tr>
									</c:if>

									<c:if test="${list.sortSeq ne 0}">
										<tr style="display: none;" class="skincolor">
											<td class="left" style="font-weight: bold;padding-left:30px;">
												<c:out value="${list.elName}"/>
											</td>
											<td class="center">
												<a href="#" onclick="javascript:popup('grade','<c:out value="${list.evItemNo}"/>','<c:out value="${list.evIeSeq}"/>', '<c:out value="${list.itemGrade}"/>');"><c:out value="${list.itemGrade}"/></a>
											</td>
											<td class="right" style="text-align: right; font-weight: bold;">
												<c:set var="won">
													<spring:message code="text.srm.field.moniter.won"/>
												</c:set>
												<c:if test="${list.elUnit eq won}">
													<fmt:formatNumber value="${list.resultValue}" pattern="#,##0" />
												</c:if>
												<c:if test="${list.elUnit ne won}">
													<fmt:formatNumber value="${list.resultValue}" pattern="#,##0.0" />
												</c:if>
											</td>
											<td class="center" style="font-weight:bold;">
												<c:out value="${list.elUnit}"/>
											</td>
											<td>
												<c:if test="${list.improveCount eq 'Y'}">
													<img src="/images/epc/edi/srm/icoDot.png">
												</c:if>
													<%--<c:out value="${list.improveCount}"/>--%>
											</td>
											<td>
												<c:if test="${list.remarkYn eq 'Y'}">
													<a href="#" onclick="javascript:popup('remark','<c:out value="${list.evItemNo}"/>','<c:out value="${list.evIeSeq}"/>');"><img src="/images/epc/btn/bt_search.gif" align="absmiddle" border="0" alt="" width="20px"></a>
												</c:if>
											</td>
											<td class="last">
												<c:if test="${list.resultEtc eq '1' && list.spYn eq 'Y'}">
													<a href="#" onclick="javascript:popup('etc','<c:out value="${list.evItemNo}"/>','<c:out value="${list.evIeSeq}"/>');"><img src="/images/epc/btn/bt_search.gif" align="absmiddle" border="0" alt="" width="20px"></a>
												</c:if>
												<input type="hidden" id="sortSeq" name="sortSeq" value="<c:out value="${list.sortSeq}"/>"/>
												<input type="hidden" id="evItemNo" name="evItemNo" value="<c:out value="${list.evItemNo}"/>"/>
											</td>
										</tr>
									</c:if>
								</c:forEach>
								<tr id="title">
									<td class="puple left" style="border-right:1px #dadada solid;"><spring:message code="text.srm.field.moniter.result"/> </td>
									<td class="puple" style="border-right:1px #dadada solid; text-align: center; padding:6px;"><c:out value="${grade.evGradeClass}"/></td>
									<td class="puple right" style="border-right:1px #dadada solid; padding:6px;"><c:out value="${grade.evScoreSum}"/></td>
									<td class="puple" style="border-right:1px #dadada solid; text-align: center; padding:6px;"><spring:message code="text.srm.field.moniter.score"/></td>
									<td class="puple" colspan="3"></td>
								</tr>
							</p>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
</form>
<form id="popupForm" name="popupForm" method="post">
	<input type="hidden" id="evItemNo" name="evItemNo"/>
	<input type="hidden" id="evIeSeq" name="evIeSeq"/>
	<input type="hidden" id="grade" name="grade"/>
	<input type="hidden" id="countYear" name="countYear"/>
	<input type="hidden" id="countMonth" name="countMonth"/>
	<input type="hidden" id="vendorCode" name="vendorCode"/>
	<input type="hidden" id="catLv2Code" name="catLv2Code"/>
</form>
</body>
</html>