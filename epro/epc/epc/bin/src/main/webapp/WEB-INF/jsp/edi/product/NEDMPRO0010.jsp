<%--
	Page Name 	: NEDMPRO0010.jsp
	Description : 신상품등록현황 조회 화면
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.12.22		SONG MIN KYO 	최초생성
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<script type="text/javascript" >
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {

			//----- 검색조건 협력업체 Default 설정.
			var srchEntpCd = "<c:out value='${param.srchEntpCd}'/>";  //검색조건 협력업체코드
			var repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";  //관리자로 로그인 해서 협력업체 갈아타기 로그인시 협력업체 코드
			if (srchEntpCd.replace(/\s/gi, '') ==  "") {
				$("#searchForm #srchEntpCd").val(repVendorId);
			} else {
				$("#searchForm #srchEntpCd").val(srchEntpCd);
			}

			//----- 검색조건 상품상태값 설정
			var srchOnOffDivnCd = $("select[name='srchOnOffDivnCd']").val();
			_eventSetSrchCfmFg (srchOnOffDivnCd);

			//----- 검색조건 상품상태 온오프 일때와 온라인일때의 검색조건 설정
			$("select[name='srchOnOffDivnCd']").change(function() {
				_eventSetSrchCfmFg ($(this).val());
			});
		});

		/* 온오프, 온라인 상품구분에 따른 검색조건 */
		function _eventSetSrchCfmFg (val) {
			if (val == "0") {
				$("div[id='offlineSrchCfmFg']").show();
				$("div[id='onlineSrchCfmFg']").hide();
			} else {
				$("div[id='offlineSrchCfmFg']").hide();
				$("div[id='onlineSrchCfmFg']").show();
			}
		}

		/* 조회 */
		function _eventSearch() {
			var paramInfo = {};

			var srchFromDt = $("#searchForm input[name='srchFromDt']").val().replace(/\s/gi, '');
			var srchEndDt = $("#searchForm input[name='srchEndDt']").val().replace(/\s/gi, '');
			var srchProdDivnCd = $("#searchForm select[name='srchProdDivnCd']").val().replace(/\s/gi, '');

			if (srchFromDt == "") {
				alert("검색 시작일이 입력되지 않았습니다.");
				$("#searchForm select[name='srchFromDt']").focus();
				return;
			}

			if (srchEndDt == "") {
				alert("검색 종료일이 입력되지 않았습니다.");
				$("#searchForm select[name='srchEndDt']").focus();
				return;
			}

			if (srchProdDivnCd == "") {
				alert("상품구분이 선택되지 않았습니다.");
				$("#searchForm select[name='srchProdDivnCd']").focus();
				return;
			}

			<%--//var dateRegex = RegExp(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/); // EX) 2023-03-01 --%>
			var dateRegex = RegExp(/^([1-9])\d{3}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/); // EX) 2023-03-01

			if (!dateRegex.test(srchFromDt)) {
				alert("검색 시작일을 정확히 입력해주세요.");
				$("#searchForm select[name='srchFromDt']").focus();
				return;
			}
			if (!dateRegex.test(srchEndDt)) {
				alert("검색 종료일을 정확히 입력해주세요.");
				$("#searchForm select[name='srchEndDt']").focus();
				return;
			}

			paramInfo["srchFromDt"] = srchFromDt.replaceAll("-", "");
			paramInfo["srchEndDt"] = srchEndDt.replaceAll("-", "");
			paramInfo["srchProdDivnCd"] = srchProdDivnCd;
			paramInfo["srchEntpCd"] = $("#searchForm #srchEntpCd").val().replace(/\s/gi, '');
			paramInfo["srchOnOffDivnCd"] = $("#searchForm select[name='srchOnOffDivnCd']").val().replace(/\s/gi, '');

			//온오프상품, 온라인상품 구분에 따른 검색조건 Value값 
			if (paramInfo["srchOnOffDivnCd"] == "0") {  //온오프
				paramInfo["srchCfmFg"] = $("#searchForm select[name='srchCfmFg']").val().replace(/\s/gi, '');
			} else {  //온라인, 소셜
				paramInfo["srchCfmFg"] = $("#searchForm select[name='onSrchCfmFg']").val().replace(/\s/gi, '');
			}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectNewProdFixList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data, jqXHR) {
					//-----List Data 셋팅
					_eventSetTbodyValue(data);
				}/* ,
				error : function(request, status, error, jqXHR) {
					console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
				} */
			});
		}

		/* List Data 셋팅 */
		function _eventSetTbodyValue(json) {
			var data = json.resultList;

			setTbodyInit("dataListbody"); // dataList 초기화

			if (data.length > 0) {
				for (var i = 0; i < data.length; i++) {
					var prodTypeFlag = "01";
					var prodTypeFlagNm = "일반상품";

					data[i].count = i + 1;

					//----- VIC 취급대상
					if (data[i].wUseFg == "0" || data[i].wUseFg == null) {
						data[i].wUseFgTxt = "미취급"
					} else if (data[i].wUseFg == "1"){
						data[i].wUseFgTxt = "취급"
					}

					//----- 입수수량 콤마
					if (data[i].purInrCntQty != null && data[i].purInrCntQty.length > 0) {
						data[i].purInrCntQty = setComma(data[i].purInrCntQty);
					}

					//----- 매가금액 콤마
					if (data[i].norProdSalePrc != null && data[i].norProdSalePrc.length > 0) {
						data[i].norProdSalePrc = setComma(data[i].norProdSalePrc);
					}

					//----- 원가금액 콤마
					if (data[i].norProdPcost != null && data[i].norProdPcost.length > 0) {
						data[i].norProdPcost = setComma(data[i].norProdPcost);
					}

					if (data[i].dealRepProdYn == "Y") {
						prodTypeFlag = "02";
						prodTypeFlagNm = "딜상품";
					} else if (data[i].ctpdYn == "Y") {
						prodTypeFlag = "03";
						prodTypeFlagNm = "추가구성품";
					}

					data[i].prodTypeFlag = prodTypeFlag;
					data[i].prodTypeFlagNm = prodTypeFlagNm;
				}

				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			} else {
				setTbodyNoResult("dataListbody",18);
			}
		}

		/* 상품 상세보기 */
		function _eventViewProductInfo(pgmId, onOffDivnCd, cfmFg,prodTypeFlag) {
			var url = "";
			$("#hiddenForm input[name='pgmId']").val(pgmId);
			$("#hiddenForm input[name='cfmFg']").val(cfmFg);

			//-----0:온오프, 1:온라인
			if (onOffDivnCd == "0") {
				url = "<c:url value='/edi/product/NEDMPRO0020Detail.do'/>";
			} else {
				url = "<c:url value='/edi/product/NEDMPRO0030OnlineDetail.do'/>";
				if(prodTypeFlag == "02"){  //딜상품
					url = "<c:url value='/edi/product/DealDetail.do'/>";
				}else if(prodTypeFlag == "03"){  //추가구성품
					url = "<c:url value='/product/ComponentDetail.do'/>";
				}
			}

			$("#hiddenForm").attr("target", "_self");
			$("#hiddenForm").attr("action", url);
			$("#hiddenForm").attr("method", "post").submit();
		}

		/* 엑셀 다운로드 */
		function _eventExcel() {
			var tbody1 = $('#dataTable tbody');

			var form = document.hiddenForm;

			var date = $("#searchForm input[name='srchFromDt']").val().replace(/\s/gi, '') + "~" + $("#searchForm input[name='srchEndDt']").val().replace(/\s/gi, '');						
			var productDivnName = $("select[name=srchProdDivnCd] option:selected").text();
			var selectedVendor = $("#srchEntpCd option:selected").text();
			var onOffDivn = $("select[name=srchOnOffDivnCd] option:selected").text();

			if ($("select[name='srchOnOffDivnCd']").val() == "0") {
				var productStatus = $("select[name=srchCfmFg] option:selected").text();	
			} else {
				var productStatus = $("select[name=onSrchCfmFg] option:selected").text();
			}

			var srchTitle = "<CAPTION>신상품등록현황<br>";
				srchTitle += "[조회기간 : "+date+"] [상품 구분: "+productDivnName+"] [협력업체 : "+selectedVendor+"]<br>";
				srchTitle += "[온오프구분 : "+onOffDivn+"] [상품 상태: "+productStatus+"]<br>";
				srchTitle += "</CAPTION>"+tbody1.parent().html();

			//console.log(tbody1.parent().html());

			$("#hiddenForm input[name='staticTableBodyValue']").val(srchTitle);
			$("#hiddenForm input[name='name']").val("temp");

			$("#hiddenForm").attr("target", "_blank");
			$("#hiddenForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
			$("#hiddenForm").attr("method", "post").submit();

			$("#hiddenForm").attr("action", "");
			$("#hiddenForm").attr("target", "_self");
		}

		/* POG 이미지 등록 */
		function uploadPOG(pgmId, variant, prodImgId, entpCd, trdTypeDivnCd, fixSrcmkCd, prodCd, srcmkCd) {

			//-----변형속성순번[variant가 없을경우에는 '000'으로 셋팅해준다. 이유는 변형속성이 없는 상품의 오프라인 이미지 아이디 설정을 위해 사용한다.]
			if (variant.replace(/\s/gi, '') == "") {
				variant = "000";
			}

			//----- hiddenForm의 이미지 등록 팝업에 사용하기 위해 value 설정
			$("#hiddenForm input[name='pgmId']").val(pgmId);
			$("#hiddenForm input[name='variant']").val(variant);
			$("#hiddenForm input[name='prodImgId']").val(prodImgId);
			$("#hiddenForm input[name='entpCd']").val(entpCd);
			$("#hiddenForm input[name='trdTypeDivnCd']").val(trdTypeDivnCd);
			$("#hiddenForm input[name='fixSrcmkCd']").val(fixSrcmkCd.replace(/\s/gi, ''));
			$("#hiddenForm input[name='prodCd']").val(prodCd.replace(/\s/gi, ''));
			$("#hiddenForm input[name='srcmkCd']").val(srcmkCd.replace(/\s/gi, ''));

			$("#hiddenForm").attr("target", "_blankPop");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/newProdImgUploadPop.do'/>");

			var popInfo = window.open('','_blankPop','status=no, resizeable=yes, width=400, height=250, left=480,top=290, scrollbars=no');
			popInfo.focus();
			$("#hiddenForm").attr("method", "post").submit();
		}

		/* 물류바코드 등록 */
		function registBarcode(val, cfgFg, logiCfmFg, variant,  venCd, l1Cd){

			$("#hiddenForm input[name='pgmId']").val(val);
			$("#hiddenForm input[name='cfgFg']").val(cfgFg);
			$("#hiddenForm input[name='logiCfmFg']").val(logiCfmFg.replace(/\s/gi, ''));
			$("#hiddenForm input[name='variant']").val(variant);
			$("#hiddenForm input[name='venCd']").val(venCd);
			$("#hiddenForm input[name='l1Cd']").val(l1Cd);
			$("#hiddenForm").attr("target", "_blankPop");

			$("#hiddenForm").attr("action", "<c:url value='/edi/product/imsiProductRegLogiBcpPop.do'/>");

			var popInfo = window.open('','_blankPop','top=0, left=0, width=850, height=380, toolbar=no, status=yes, scrollbars=yes');
			popInfo.focus();
			$("#hiddenForm").attr("method", "post").submit();
		}

		/* 이 펑션은 협력업체 검색창에서 호출하는 펑션임    */
		function setVendorInto(strVendorId, strVendorNm, strCono) {
			$("#srchEntpCd").val(strVendorId);
		}

		/* 이미지 일괄적용 버튼 이벤트 */
		function _eventAddImage() {

			//----- 온오프 상품이 아니면 return
			var srchOnOffDivnCd = $("select[name='srchOnOffDivnCd']").val();
			if (srchOnOffDivnCd != "0") {
				alert("온오프 상품만 가능합니다.")
				return;
			}

			//----- 선택된 상품이 없으면 return
			var chkLen = $("input[name='chkNewFixProd']:checked").length;  //체크된 카운트
			if (chkLen <= 0) {
				alert("선택된 상품이 없습니다");
				return;
			}

			var arrPgmId = new Array();  //신상품 PGM_ID
			var arrImgId = new Array();  //신상품 이미지 아이디
			var arrTrdTypeDivnCd = new Array();  //신상품 거래형태 구분
			var arrSrcmkCd = new Array();  //신상품 판매코드
			var arrVariant = new Array();  //신상품 변형속성
			var arrEntpCd = new Array();  //신상품 협력업체코드
			var arrProdCd = new Array();  //상품으로 확정되고 난 이후의 REG_SAP테이블의 상품코드
			var arrFixSrcmkCd = new Array();  //상품으로 확정되고 난 이후의 REG_SAP테이블의 판매코드
			var arrCnt = 0;

			//전체 체크박스 길이 만큼 for문 돌면서
			for (var i = 0; i < $("input[name='chkNewFixProd']").length; i++) {
				//선택된 상품이고
				if ($("input[name='chkNewFixProd']").eq(i).is(":checked")) {
					arrPgmId[arrCnt] = $("input[name='arrPgmId']").eq(i).val();
					arrImgId[arrCnt] = $("input[name='arrImgId']").eq(i).val();
					arrTrdTypeDivnCd[arrCnt] = $("input[name='arrTrdTypeDivnCd']").eq(i).val();

					if ($("input[name='arrSrcmkCd']").eq(i).val().replace(/\s/gi, '') == "") {
						arrSrcmkCd[arrCnt] = " ";
					} else {
						arrSrcmkCd[arrCnt] = $("input[name='arrSrcmkCd']").eq(i).val();
					}

					arrSrcmkCd[arrCnt] = $("input[name='arrSrcmkCd']").eq(i).val();
					arrVariant[arrCnt] = $("input[name='arrVariant']").eq(i).val();
					arrEntpCd[arrCnt] = $("input[name='arrEntpCd']").eq(i).val();

					if ($("input[name='arrProdCd']").eq(i).val().replace(/\s/gi, '') == "") {
						arrProdCd[arrCnt] = " ";
					} else {
						arrProdCd[arrCnt] = $("input[name='arrProdCd']").eq(i).val();
					}

					if ($("input[name='arrFixSrcmkCd']").eq(i).val().replace(/\s/gi, '') == "") {
						arrFixSrcmkCd[arrCnt] = " ";
					} else {
						arrFixSrcmkCd[arrCnt] = $("input[name='arrFixSrcmkCd']").eq(i).val();
					}

					arrCnt++;
				}
			}

			//----- hiddenForm의 이미지 등록 팝업에 사용하기 위해 value 설정
			$("#hiddenForm input[name='pgmId']").val(arrPgmId);
			$("#hiddenForm input[name='variant']").val(arrVariant);
			$("#hiddenForm input[name='prodImgId']").val(arrImgId);
			$("#hiddenForm input[name='entpCd']").val(arrEntpCd);
			$("#hiddenForm input[name='trdTypeDivnCd']").val(arrTrdTypeDivnCd);
			$("#hiddenForm input[name='srcmkCd']").val(arrSrcmkCd);
			$("#hiddenForm input[name='prodCd']").val(arrProdCd);
			$("#hiddenForm input[name='fixSrcmkCd']").val(arrFixSrcmkCd);

			$("#hiddenForm").attr("target", "_blankPop");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/newProdImgUploadPop.do'/>");

			var popInfo = window.open('','_blankPop','status=no, resizeable=yes, width=400, height=250, left=480,top=290, scrollbars=no');
			popInfo.focus();
			$("#hiddenForm").attr("method", "post").submit();
		}
	</script>
	<!-- DATA LIST -->
	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<tr class="r1" bgcolor=ffffff>
			<td align="center" class="chkGbn">
				{%if cfmFg == "0" && onOffDivnCd == "0" && zzimageCfgFg == null %} <!-- 상품이 심사중일떄만 온오프상품이면서 이미지가 등록되지 않았을때 일괄적용 가능하게 체크박스 선택할수 있게끔 처리 -->
					<input type="checkbox" name="chkNewFixProd" id="chkNewFixProd" value="Y" />
				{%else%}
					<input type="checkbox" name="chkNewFixProd" id="chkNewFixProd" disabled />
				{%/if%}
				<input type="hidden" name="arrPgmId" id="arrPgmId" value="<c:out value='\${pgmId}'/>" />
				<input type="hidden" name="arrImgId" id="arrImgId" value="<c:out value='\${prodImgId}'/>" />
				<input type="hidden" name="arrTrdTypeDivnCd" id="arrTrdTypeDivnCd"	value="<c:out value='\${trdTypeDivnCd}'/>" />
				<input type="hidden" name="arrSrcmkCd" id="arrSrcmkCd" value="<c:out value='\${srcmkCd}'/>" />
				<input type="hidden" name="arrVariant" id="arrVariant" value="<c:out value='\${variant}'/>" />
				<input type="hidden" name="arrEntpCd" id="arrEntpCd" value="<c:out value='\${entpCd}'/>" />
				<input type="hidden" name="arrProdCd" id="arrProdCd" value="<c:out value='\${prodCd}'/>" />
				<input type="hidden" name="arrFixSrcmkCd" id="arrFixSrcmkCd" value="<c:out value='\${fixSrcmkCd}'/>" />
			</td>
			<td align="center"><c:out value="\${count}"/></td>
			<td align="center" class="regDate">
				<c:out value="\${regDate}"/>
			</td>
			<td align="center" class="entpCd">
				<c:out value="\${entpCd}"/>
			</td>
			<td align="center" class="cfmFgTxt">
				<c:out value="\${cfmFgTxt}"/>
			</td>
		{%if cfmFgReason != null%}
			<td align="left" class="cfmReason">
				<c:out value="\${cfmFgReason}"/>
			</td>
		{%else%}
			<td align="center"  class="cfmReason">
				-
			</td>
		{%/if%}
			<td align="center">
				{%if cfmFg == '5' || cfmFg == '6' || cfmFg == '7' || cfmFg == '8' || cfmFg == '90'%}
					-
				{%else%}
					{%if onOffDivnCd == '0'%}  <!-- 온오프 상품만 이미지 등록이 가능-->
						{%if zzimageCfgFg == null %}  <!-- 오프라인 이미지 확정여부 상태가 없으면 -->
							{%if cfmFg == '3'%}  <!-- 상품이 확정상태이면 오프라인 이미지 등록요망으로 보여주고 이미지 등록 가능 -->
								<a href="#" onClick="uploadPOG('<c:out value="\${pgmId}"/>', '<c:out value="\${variant}"/>', '<c:out value="\${prodImgId}"/>', '<c:out value="\${entpCd}"/>', '<c:out value="\${trdTypeDivnCd}"/>', '<c:out value="\${fixSrcmkCd}"/>', '<c:out value="\${prodCd}"/>', '<c:out value="\${srcmkCd}"/>');"><font color="blue"><strong>등록요망</strong></font></a>
							{%else%}  <!-- 상품이 확정상태가 아니면 이미지 미등록으로 보여주고 이미지 등록 가능 -->
								<a href="#" onClick="uploadPOG('<c:out value="\${pgmId}"/>', '<c:out value="\${variant}"/>', '<c:out value="\${prodImgId}"/>', '<c:out value="\${entpCd}"/>', '<c:out value="\${trdTypeDivnCd}"/>', '<c:out value="\${fixSrcmkCd}"/>', '<c:out value="\${prodCd}"/>', '<c:out value="\${srcmkCd}"/>');"><font color="blue"><strong>미등록</strong></font></a>
							{%/if%}
						{%else%}
							{%if zzimageCfgFg == '0' || zzimageCfgFg == '1'%}
								<font color="red">심사중</font>
							{%elif zzimageCfgFg == '2'%}
								<a href="#" onClick="uploadPOG('<c:out value="\${pgmId}"/>', '<c:out value="\${variant}"/>', '<c:out value="\${prodImgId}"/>', '<c:out value="\${entpCd}"/>', '<c:out value="\${trdTypeDivnCd}"/>', '<c:out value="\${fixSrcmkCd}"/>', '<c:out value="\${prodCd}"/>', '<c:out value="\${srcmkCd}"/>');"><font color="red"><strong>반려</strong></font></a>
							{%elif zzimageCfgFg == '3'%}
								<font color="red">확정</font>
							{%/if%}
						{%/if%}
					{%else%}  <!-- 온라인 상품은 이미지 등록이 없다 -->
						-
					{%/if%}
				{%/if%}
			</td>
			<td align="center">
				{%if cfmFg == '5' || cfmFg == '6' || cfmFg == '7' || cfmFg == '8' || cfmFg == '90'%}
					-
				{%else%}
				<!-- 온오프상품만 -->
					{%if onOffDivnCd == '0'%}
						{%if logiCfmFg == null%}  <!-- 물류바코드 확정여부-->
							{%if cfmFg == '3'%}  <!-- 상품이 확정이면 -->
								<FONT COLOR ="BLUE">등록<br>요망</FONT>
							{%else%}
								<a href="javascript:registBarcode('<c:out value="\${pgmId}"/>', 'I', '00', '<c:out value="\${variant}"/>', '<c:out value="\${entpCd}"/>', '<c:out value="\${l1Cd}"/>')"><FONT COLOR ="BLUE">미등록</FONT></a>
							{%/if%}
						{%else%}
							{%if logiCfmFg == '00' || logiCfmFg == '02'%}
								<FONT COLOR ="RED">심사중</FONT>
							{%elif logiCfmFg == '03'%}
								<a href="javascript:registBarcode('<c:out value="\${pgmId}"/>', 'U', '00', '<c:out value="\${variant}"/>', '<c:out value="\${entpCd}"/>', '<c:out value="\${l1Cd}"/>')"><FONT COLOR ="red">수정요청</FONT></a>
							{%elif logiCfmFg == '09'%}
								<FONT COLOR ="green">확정</FONT>
							{%/if%}
						{%/if%}
					{%else%}
						-
					{%/if%}
				{%/if%}
			</td>
			<td align="center" class="vicGbn">
				{%if onOffDivnCd == '0'%}  <!-- 온오프 상품이면서 -->
					<c:out value="\${wUseFgTxt}"/>
				{%else%}
					-
				{%/if%}
			</td>
			<td class="prodNm">
				<a href="javascript:_eventViewProductInfo('<c:out value="\${pgmId}"/>', '<c:out value="\${onOffDivnCd}"/>',	'<c:out value="\${cfmFg}"/>','<c:out value="\${prodTypeFlag}"/>')"
				title="입수: <c:out value='\${purInrcntQty}'/>
				원가: <c:out value='\${norProdPcost}'/>
				매가: <c:out value='\${norProdSalePrc}'/>
				이익률 : <c:out value='\${prftRate}'/> %"
				>
					<c:out value="\${prodNm}"/>
				</a>
			</td>
			<td align="center"><c:out value="\${srcmkCd}"/></td>
			<td align="center"><c:out value="\${prodCd}"/></td>
				{%if onOffDivnCd == '0' && prodAttTypFg == '01'%}  <!-- 온오프 상품이면서 묶음상품이면 -->
					<td align="left">
						<c:out value="\${attNm}"/>
					</td>
				{%else%}
					<td align="center">
						-
					</td>
				{%/if%}
			</td>
			<td align="center"><c:out value="\${prodStandardNm}"/></td>
			<td align="right"><c:out value="\${purInrcntQty}"/></td>
			<td align="right"><c:out value="\${norProdPcost}"/></td>
			<td align="right"><c:out value="\${norProdSalePrc}"/></td>
			<td align="center"><c:out value="\${prftRate}"/>%</td>
		</tr>
	</script>
</head>
<body>
	<div id="content_wrap">
		<div>
			<form name="searchForm" id="searchForm">
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">검색조건</li>
								<li class="btn">
									<a href="#" class="btn" onclick="_eventSearch();"><span><spring:message code="button.common.inquire"/></span></a>
									<a href="#" class="btn" onclick="_eventExcel();"><span><spring:message code="button.common.excel"/></span></a>																	
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
									<th><span class="star">*</span>신상품 등록기간</th>
									<td>
										<c:if test="${empty param.srchFromDt}">
											<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;" />
											~
											<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${srchEndDt}'/>"> 	<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
										</c:if>
										<c:if test="${not empty param.srchFromDt}">
											<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${param.srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;"/>
											~
											<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${param.srchEndDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');" style="cursor:hand;" />
										</c:if>
									</td>
									<th><span class="star">*</span>상품구분</th>
									<td>
										<select name="srchProdDivnCd" id="srchProdDivnCd">
											<option value="1">규격</option>
											<option value="5">패션</option>
											<option value="0">신선비규격</option>
										</select>
									</td>
								</tr>
								<tr>
									<th>협력업체코드</th>
									<td>
										<c:if test="${epcLoginVO.vendorTypeCd eq '06'}">
											<input type="text" name="srchEntpCd" id="srchEntpCd" readonly="readonly" readonly="readonly" style="width:40%;" />
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:if>
										<c:if test="${epcLoginVO.vendorTypeCd ne '06'}">
											<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="150px;"  dataType="CP" comType="SELECT" formName="form" defName="전체" />
										</c:if>
									</td>
									<th>상품상태</th>
									<td>
										<div style="float:left">
											<select name="srchOnOffDivnCd" id="srchOnOffDivnCd">
												<option value="0">온오프</option>
												<option value="1">온라인 전용</option>
												<option value="2">소셜</option>
											</select>
										</div>
										<div style="float:left" id="offlineSrchCfmFg">
											<select name="srchCfmFg" id="srchCfmFg">
												<option value="">전체</option>
												<option value="0">심사중</option>
												<option value="5">반려</option>
												<option value="4">위해상품</option>
												<option value="6">거절</option>
												<option value="3">확정</option>
												<option value="90">MD거절</option>
											</select>
										</div>
										<div style="float:left;display:none" id="onlineSrchCfmFg">
											<select name="onSrchCfmFg" id="onSrchCfmFg">
												<option value="">전체</option>
												<option value="70">MD심사의뢰</option>
												<option value="80">오프라인확정</option>
												<option value="81">온라인확정</option>
												<option value="90">MD거절</option>
											</select>
										</div>
									</td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
								<tr>
									<td colspan="4" bgcolor=ffffff>
										<strong>&nbsp;<font color="red">※ 체크박스를 선택하신 후 '이미지 일괄적용' 버튼을 클릭하신 후 팝업에서 이미지를 등록하시면 선택된 상품들의 이미지가 일괄로 적용됩니다.</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;(상품이 심사중일떄만 가능)</font></strong><br/>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">검색내역 </li>
								<li class="btn">
									<!-- <font color='white'><b>300~500kb 이하 크기의 jpg 파일만 업로드가 가능합니다.</b></font> -->
									<a href="javascript:_eventAddImage();" class="btn"><span>이미지 일괄적용</span></a>
								</li>
							</ul>
							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1800px; bgcolor=efefef>
									<colgroup>
										<col style="width:30px"/>
										<col style="width:30px"/>
										<col style="width:80px"/>
										<col style="width:80px"/>
										<col style="width:60px;"/>
										<col style="width:180px"/>
										<col style="width:70px"/>
										<col style="width:70px"/>
										<col style="width:70px"/>
										<col style="width:250px;"/>
										<col style="width:100px"/>
										<col style="width:150px"/>
										<col />
										<col style="width:80px;"/>
										<col style="width:80px"/>
										<col style="width:80px"/>
										<col style="width:80px"/>
										<col style="width:50px"/>
									</colgroup>
									<tr bgcolor="#e4e4e4">
										<th></th>
										<th>No.</th>
										<th>등록일자</th>
										<th>협력업체</th>
										<th>상태</th>
										<th>거절&반려 사유</th>
										<th>이미지</th>
										<th>물류바코드</th>
										<th>VIC대상</th>
										<th>상품명</th>
										<th>판매코드</th>
										<th>상품코드</th>
										<th>속성</th>
										<th>상품규격</th>
										<th>입수</th>
										<th>원가</th>
										<th>매가</th>
										<th>이익율</th>
									</tr>
									<tbody id="dataListbody" />
								</table>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- hiddenForm ------------------------------------------------------>
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" name="pgmId" id="pgmId" />
		<input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue" />
		<input type="hidden" name="name" id="name" />
		<input type="hidden" name="mode" id="mode" value="view" /> <!-- 신상품현황 상세보기 모드에서는 수정이 안되므로 mode 설정 -->
		<input type="hidden" name="variant" id="variant" /> <!-- 이미지 등록 팝업에 사용하기 위해 선언 [변형속성 variant] -->
		<input type="hidden" name="prodImgId" id="prodImgId" /> <!-- 이미지 등록 팝업에 사용하기 위해 선언 [상품 마스터 정보의 이미지 ID]-->
		<input type="hidden" name="entpCd" id="entpCd" /> <!-- 이미지 등록 팝업에 사용하기 위해 선언 [협력업체코드]-->
		<input type="hidden" name="trdTypeDivnCd" id="trdTypeDivnCd" /> <!-- 이미지 등록 팝업에 사용하기 위해 선언 [거래형태구분(1:직매입, 2:특약1, 4:특약2)]-->
		<input type="hidden" name="srcmkCd" id="srcmkCd" /> <!-- 이미지 등록 팝업에 사용하기 위해 선언 [판매코드]-->
		<input type="hidden" name="fixSrcmkCd" id="fixSrcmkCd" /> <!-- 이미지 등록 팝업에 사용하기 위해 선언 [상품이 확정되고 난 이후 REG_SAP테이블의 판매코드]-->
		<input type="hidden" name="prodCd" id="prodCd" /> <!-- 이미지 등록 팝업에 사용하기 위해 선언 [상품이 확정되고 난 이후 상품코드]--> 
		<input type="hidden" name="cfgFg" id="cfgFg" /> <!-- 물류바코드 등록 수정구분자에 사용-->
		<input type="hidden" name="logiCfmFg" id="logiCfmFg" /> <!-- 물류바코드 등록 상태-->
		<input type="hidden" name="gbn" id="gbn" value="99" /> <!-- 임시보관함['']에서 등록하는지 신상품등록현황[99]에서 등록하는지 구분자 -->
		<input type="hidden" name="variant" id="variant" /> <!-- 물류바코드 등록팝업에서 해당 속성만 보여주기 위해 사용-->
		<input type="hidden" name="varAttNm" id="varAttNm" /> <!-- 물류바코드 등록팝업에서 해당 속성만 보여주기 위해 사용 -->
		<input type="hidden" name="venCd" id="venCd" /> <!-- 물류바코드 등록팝업에서  사용 -->
		<input type="hidden" name="l1Cd" id="l1Cd" /> <!-- 물류바코드 등록팝업에서  사용 -->
		<input type="hidden" name="cfmFg" id="cfmFg" /> <!-- 상품확정요청 상태 -->
	</form>
</body>
</html>
