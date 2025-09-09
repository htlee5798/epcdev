<%--
	Page Name 	: NEDMPRO0040.jsp
	Description : 임시보관함 LIST 화면
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.11.27		SONG MIN KYO 	최초생성
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
	<style>
		.b-top{
			border-top: 1px solid #dfdfdf;
		}
		.b-bottom{
			border-bottom: 1px solid #dfdfdf;
		}
		.b-left{
			border-left: 1px solid #dfdfdf;
		}
		.b-right{
			border-right: 1px solid #dfdfdf;
		}
	</style>
	<script type="text/javascript" >
		let maxFixedCnt = 5;
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {
			//검색 시작, 종료일 (Parameter....)
			var srchStartDt = 	"<c:out value='${param.srchStartDt}'/>";
			var srchEndDt	=	"<c:out value='${param.srchEndDt}'/>";

			//검색 값이 없을경우 Default 값 설정
			if (srchStartDt.replace(/\s/gi, '') == "") {
				srchStartDt = "<c:out value='${srchStartDt}'/>";
				srchEndDt = "<c:out value='${srchEndDt}'/>";
			}

			//검색 기간 설정
			$("#searchForm input[name='srchStartDt']").val(srchStartDt);
			$("#searchForm input[name='srchEndDt']").val(srchEndDt);

			// All Check true and false
			$("input[name=allProductCode]").click(function() {
				if($(this).attr("checked")) {
					$("input[name=selectedProduct]").attr("checked", true);
				} else {
					$("input[name=selectedProduct]").attr("checked", false);
				}
			});

			
			$("#thTitle").html("물류바코드");
			<%--
			$("select[name=srchOnOffDivnCode]").change(function(){
				if($(this).val() == "1"){ //온라인전용
					$("#thTitle").html("임직원몰판매<br/>가능여부");
				}else{
					$("#thTitle").html("물류바코드");
				}
			});
			--%>

			//20180604-EDI 임시보관함 확정시 5개에서 20개로 변경 (온라인전용 및 소셜 상품만 적용) 하기 위한 구분값 설정 (온오프상품의 한번에 5개 이상은 RFC 요청이 안되는 조건은 동일)
			var rsOnOffDivnCode;
		});

		/* 조회 버튼 이벤트 */
		function _eventSearch() {
			$("input[name=allProductCode]").attr("checked", false);

			var paramInfo = {};
			var vendorTypeCd = "<c:out value='epcLoginVO.vendorTypeCd'/>";

			if (vendorTypeCd == "06") {
				if ($("#entp_cd").val().replace(/\s/gi, '') == "") {
					alert("<spring:message code='msg.product.common.comp'/>");
					$("#entp_cd").focus();
					return;
				}
			}
			// 날짜 체크
			var srchStartDt = $("#searchForm input[name='srchStartDt']").val().replace(/\s/gi, '');
			var srchEndDt = $("#searchForm input[name='srchEndDt']").val().replace(/\s/gi, '');

			if (srchStartDt == "" || srchEndDt == "") {
				alert("<spring:message code='msg.common.fail.nocalendar'/>");
				$("#searchForm input[name='srchStartDt']").focus();
				return;
			}

			if (parseInt(srchStartDt.replaceAll("-", "")) > parseInt(srchEndDt.replaceAll("-", ""))) {
				alert("<spring:message code='msg.common.fail.calendar'/>");
				$("#searchForm input[name='srchStartDt']").focus();
				return;
			}

			// 검색 param 설정..
			paramInfo["srchStartDt"] = srchStartDt.replaceAll("-", "");
			paramInfo["srchEndDt"] = srchEndDt.replaceAll("-", "");
			paramInfo["srchProductDivnCode"] = $("#searchForm select[name='srchProductDivnCode']").val().replace(/\s/gi, '');
			paramInfo["srchEntpCode"] = $("#searchForm #entp_cd").val().replace(/\s/gi, '');
// 			paramInfo["srchOnOffDivnCode"] = $("#searchForm select[name='srchOnOffDivnCode']").val().replace(/\s/gi, '');
// 			paramInfo["prodTypeFlag"] = $("#searchForm select[name='prodTypeFlag']").val().replace(/\s/gi, '');

			var adminId = "<c:out value='${epcLoginVO.adminId}'/>"; //상품등록이후 조회된 상품의 협력업체 코드
			if (adminId == "" || adminId == "online") {
				paramInfo["uAdmFg"] = "1";
			} else {
				paramInfo["uAdmFg"] = "3";
			}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectImsiProductList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					_setTbodyMasterValue(data);
				}
			});
		}

		/* 조회 이후 DATA LIST 구성 */
		function _setTbodyMasterValue(json) {

			//20180604-EDI 임시보관함 확정시 5개에서 20개로 변경 (온라인전용 및 소셜 상품만 적용) 하기 위한 구분값 설정 (온오프상품의 한번에 5개 이상은 RFC 요청이 안되는 조건은 동일)
			rsOnOffDivnCode = json.rsOnOffDivnCode;

			var data = json.rsList;

			setTbodyInit("dataListbody"); // dataList 초기화

			if (data.length > 0) {
				for (var i = 0; i < data.length; i++) {
					var prodTypeFlag = "01";
					var prodTypeFlagNm = "일반상품";

					data[i].count = i + 1;
					
					//금액 콤마 설정..
					var norProdPcost = setComma($.trim(data[i].norProdPcost));		//마트_원가
					var norProdSalePrc = setComma($.trim(data[i].norProdSalePrc));	//마트_매가
					var prftRate = $.trim(data[i].prftRate);				//마트_이익률
					
					var wnorProdPcost = setComma($.trim(data[i].wnorProdPcost));	//MAXX_원가
					var wnorProdSalePrc = setComma($.trim(data[i].wnorProdSalePrc));//MAXX_매가
					var wprftRate = $.trim(data[i].wprftRate);				//MAXX_이익률
					
					var snorProdPcost = setComma($.trim(data[i].snorProdPcost));		//슈퍼_원가
					var snorProdSalePrc = setComma($.trim(data[i].snorProdSalePrc));	//슈퍼_매가
					var sprftRate = $.trim(data[i].sprftRate);				//슈퍼_이익률
					
					var onorProdPcost = setComma($.trim(data[i].onorProdPcost));		//오카도_원가
					var onorProdSalePrc = setComma($.trim(data[i].onorProdSalePrc));	//오카도_매가
					var oprftRate = $.trim(data[i].oprftRate);				//오카도_이익률
					
					data[i].norProdPcost = norProdPcost;
					data[i].norProdSalePrc = norProdSalePrc;
					data[i].wnorProdPcost = wnorProdPcost;
					data[i].wnorProdSalePrc = wnorProdSalePrc;
					data[i].snorProdPcost = snorProdPcost;
					data[i].snorProdSalePrc = snorProdSalePrc;
					data[i].onorProdPcost = onorProdPcost;
					data[i].onorProdSalePrc = onorProdSalePrc;

					//대표상품이미지 등록 여부
					data[i].prodDescYn = setComma(data[i].prodDescYn);

					if (data[i].onoffDivnCd == "0") {
						data[i].onoffDivnTxt = "온오프";
					} else if (data[i].onoffDivnCd == "1") {
						data[i].onoffDivnTxt = "온라인";
					} else if (data[i].onoffDivnCd == "2") {
						data[i].onoffDivnTxt = "소셜";
					}

					if (data[i].dealRepProdYn == "Y") {
						prodTypeFlag = "02";
						prodTypeFlagNm = "딜상품";
					} else if(data[i].ctpdYn == "Y") {
						prodTypeFlag = "03";
						prodTypeFlagNm = "추가구성품";
					}

					data[i].prodTypeFlag = prodTypeFlag;
					data[i].prodTypeFlagNm = prodTypeFlagNm;
					
					var purInrcntQty = setComma($.trim(data[i].purInrcntQty));		//입수
					
					//tooltip contents
					var ttCon = "";
					ttCon += "입수 : "+purInrcntQty+"<br/>";
					
					var chanCd = $.trim(data[i].chanCd);		//채널코드
					var chanCdArr = chanCd.split(",");
					$.each(chanCdArr,function(i,v){
						if(v == "") return;
						
						switch(v){
							case "KR03":	//MAXX
								ttCon += "[MAXX] ";
								ttCon += "원가: "+wnorProdPcost;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "매가: "+wnorProdSalePrc;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "이익률: "+wprftRate+" %<br/>";
								break;
							case "KR04":	//슈퍼
								ttCon += "[슈퍼] ";
								ttCon += "원가: "+snorProdPcost;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "매가: "+snorProdSalePrc;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "이익률: "+sprftRate+" %<br/>";
								break;
							case "KR09":	//오카도
								ttCon += "[오카도] ";
								ttCon += "원가: "+onorProdPcost;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "매가: "+onorProdSalePrc;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "이익률: "+oprftRate+" %<br/>";
								break;
							case "KR02":	//마트
							default:
								ttCon += "[마트] ";
								ttCon += "원가: "+norProdPcost;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "매가: "+norProdSalePrc;
								ttCon += "&nbsp;&nbsp;&nbsp;";
								ttCon += "이익률: "+prftRate+" %<br/>";
								break;
						}
						
					});
					
					//tooltip contents setting
					data[i].ttCon = ttCon;
				}

				//datalist setting
				$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
				
				//tooltip 적용
				$(".tooltip").not(".tooltipstered").tooltipster({
				   animation: 'fade',
				   delay: 200,
				   theme: 'tooltipster-shadow',
				   contentAsHTML: true,
				   touchDevices: false,
				   trigger: 'hover'
				});
				
			} else {		
				setTbodyNoResult("dataListbody", 24);
			}
		}

		/* 이 펑션은 협력업체 검색창에서 호출하는 펑션임 */
		function setVendorInto(strVendorId, strVendorNm, strCono) {
			$("#entp_cd").val(strVendorId);
		}

		/* 물류바코드 등록 */
		function registBarcode(val, logiCfmFg, venCd, l1Cd){

			$("#hiddenForm input[name='pgmId']").val(val);
			$("#hiddenForm input[name='logiCfmFg']").val(logiCfmFg);
			$("#hiddenForm input[name='venCd']").val(venCd);
			$("#hiddenForm input[name='l1Cd']").val(l1Cd);

			$("#hiddenForm").attr("target", "_blankPop");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/imsiProductRegLogiBcpPop.do'/>");

			var popInfo = window.open('','_blankPop','top=0, left=0, width=850, height=380, toolbar=no, status=yes, scrollbars=yes');
			popInfo.focus();
			$("#hiddenForm").attr("method", "post").submit();
		}

		/* 판매코드조회 */
		function doSellCodeView(val) {
			$("#hiddenForm input[name='pgmId']").val(val);
			$("#hiddenForm").attr("target", "_blankPop");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/viewImsiProdSellCodePop.do'/>");

			var popInfo = window.open('','_blankPop','top=0, left=0, width=500, height=360, toolbar=no, status=yes, scrollbars=yes');
			popInfo.focus();

			$("#hiddenForm").attr("method", "post").submit();
		}

		/* 선택상품 삭제 */
		function _eventDoDelete() {
			var selectedLength = $("input[name=selectedProduct]:checked").length;

			if( selectedLength <= 0 ) {
				alert("<spring:message code='msg.order.delete'/>");
				return;
			}

			var paramInfo = {};

			var arrPgmId = new Array();
			var arrOnOffGubun = new Array();
			var arrImgId = new Array();
			var idx = 0;
			
			var bosSendDy = "";		//BOS요청일자

			var flag = true;
			for (var i = 0; i < $("input[name=selectedProduct]").length; i++) {
				var prodNm = $("input[name='prodNm']").eq(i).val().replace(/\s/gi, '');						//선택된 상품의 상품명
				
				if ($("input[name=selectedProduct]").eq(i).is(":checked")) {
					arrPgmId[idx] = $("#searchForm input[name='arrPgmId']").eq(i).val();					//신상품아이디
					arrOnOffGubun[idx] = $("#searchForm input[name='arrOnOffGubun']").eq(i).val();			//온오프구분
					arrImgId[idx] = $("#searchForm input[name='arrImgId']").eq(i).val();					//이미지아이디

					bosSendDy = $("#searchForm input[name='bosSendDy']").eq(i).val();		//BOS전송일자
					
					if(bosSendDy != null && bosSendDy != ""){
						alert("선택된 삭제대상 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]은 BOS 승인 대상 상품입니다.\nBOS 발송 이후 수정/삭제가 불가능합니다.");
						flag=false;
						return false;
					}
					
					idx++;
				}
			}
			
			if(!flag) return false;
			
			if (!confirm("<spring:message code='msg.common.confirm.delete'/>")) {
				return;
			}

			paramInfo["arrPgmId"] = arrPgmId; //상품코드
			paramInfo["arrOnOffGubun"] = arrOnOffGubun; //상품의 온오프 구분
			paramInfo["arrImgId"] = arrImgId; //상품 이미지 아이디
			//console.log(paramInfo);

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/deleteImsiProductList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msg == "SUCCESS") {
						alert("<spring:message code='msg.common.success.delete'/>"); //성공
					} else {
						alert("<spring:message code='msg.common.fail.delete'/>"); //실패
					}

					//목록 재조회
					$("input[name=allProductCode]").attr("checked", false);
					_eventSearch();
				}
			});
		}

		/* excel */
		function _eventExcel() {
			var tbody1 = $('#dataTable tbody');

			var form = document.hiddenForm;

			var date = $("#searchForm input[name='srchStartDt']").val().replace(/\s/gi, '') + "~" + $("#searchForm input[name='srchEndDt']").val().replace(/\s/gi, '');			
			var productDivnName = $("select[name=srchProductDivnCode] option:selected").text();
			var selectedVendor = $("#entp_cd option:selected").text();

			var srchTitle = "<CAPTION>임시보관함<br>";
				srchTitle += "[조회기간 : "+date+"] [상품 구분: "+productDivnName+"] [협력업체 : "+selectedVendor+"]<br>";
				srchTitle += "</CAPTION>"+tbody1.parent().html();

			//console.log(srchTitle);

			$("#hiddenForm input[name='staticTableBodyValue']").val(srchTitle);
			$("#hiddenForm input[name='name']").val("temp");

			$("#hiddenForm").attr("target", "_blank");
			$("#hiddenForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
			$("#hiddenForm").attr("method", "post").submit();

			$("#hiddenForm").attr("action", "");
			$("#hiddenForm").attr("target", "_self");
		}

		/* 상품 확정 */
		function _eventDoFix(){
			var paramInfo = {};
			
			//선택한 row 수
			var selectedLength = $("input[name=selectedProduct]:checked").length;

			if ( selectedLength <= 0 ) {
				alert("<spring:message code='msg.order.fix'/>");
				return;
			}
			
			//한 번에 5개까지만 확정 가능
			if( selectedLength > maxFixedCnt ) {
				alert("온오프겸용 상품의 경우 한번에 최대 "+maxFixedCnt+"개의 상품까지만 확정요청이 가능합니다.");
				return;
			}
			
			var flag=true;
			var prodArr = [];
			$("#dataListbody tr").find("input[name=selectedProduct]:checked").each(function(i){
				var prodData = {};
				
				var tgObj = $(this).closest("tr");
				var pgmId = $.trim(tgObj.find("input[name='arrPgmId']").val()).replace(/\s/gi, '');			//프로그램아이디
				var prodcommerce = $.trim(tgObj.find("input[name='prodcommerce']").val()).replace(/\s/gi, '');			//전상법 상태값
				var prodNm = $.trim(tgObj.find("input[name='prodNm']").val()).replace(/\s/gi, '');						//상품명
				var onOffDivnCd = $.trim(tgObj.find("input[name='onOffDivnCd']").val()).replace(/\s/gi, '');			//온오프상품구분
				var imgNcntYn = $.trim(tgObj.find("input[name='imgNcntYn']").val()).replace(/\s/gi, '');				//이미지등록여부
				var l3Cd = $.trim(tgObj.find("input[name='l3Cd']").val()).replace(/\s/gi, '');							//소분류코드
				var prodAttTypFg = $.trim(tgObj.find("input[name='prodAttTypFg']").val()).replace(/\s/gi, '');			//상품범주구분
				var varAttCnt = $.trim(tgObj.find("input[name='varAttCnt']").val()).replace(/\s/gi, '');				//이미지등록여부
				var inputVarAttCnt = $.trim(tgObj.find("input[name='inputVarAttCnt']").val()).replace(/\s/gi, '');		//입력된 변형속성수
				var trdTypeDivnCd = $.trim(tgObj.find("input[name='trdTypeDivnCd']").val()).replace(/\s/gi, '');		//거래유형 코드 [1:직매입, 2:특약1, 4:특약2]
				var inputGrpAttrCnt = $.trim(tgObj.find("input[name='inputGrpAttrCnt']").val()).replace(/\s/gi, '');	//입력된 분석속성 갯수
				var trdTypeDivnCd = $.trim(tgObj.find("input[name='grpAttrCnt']").val()).replace(/\s/gi, '');			//입력할 분석속성 갯수
				var inputVarAttCnt001 = $.trim(tgObj.find("input[name='inputVarAttCnt001']").val()).replace(/\s/gi, '');	//입력된 001 변형속성 카운트
				var grpCd = $.trim(tgObj.find("input[name='grpCd']").val()).replace(/\s/gi, '');						//그룹분류코드
				var prodTypeFlag = $.trim(tgObj.find("input[name='prodTypeFlag']").val()).replace(/\s/gi, '');			//상품종류 (일반, 딜상품, 추가구성...)
				var vicOnlineCd = $.trim(tgObj.find("input[name='vicOnlineCd']").val()).replace(/\s/gi, '');			//온라인적용여부 
				var admFg = $.trim(tgObj.find("input[name='admFg']").val()).replace(/\s/gi, '');						//온오프 등록시 관리자여부
				var wideImgNcntYn = $.trim(tgObj.find("input[name='wideImgNcntYn']").val()).replace(/\s/gi, '');		//이미지등록여부
				var ecAttrRegYn = $.trim(tgObj.find("input[name='ecAttrRegYn']").val()).replace(/\s/gi, '');			//ec 속성 등록 유무
				var nutAttrRegYn = $.trim(tgObj.find("input[name='nutAttrRegYn']").val()).replace(/\s/gi, '');			//영양 속성 등록 유무
				var canRegNutAttrYn = $.trim(tgObj.find("input[name='canRegNutAttrYn']").val()).replace(/\s/gi, '');	//등록할 수 있는 영양 속성 존재 유무
				var prodDivnCd = $.trim(tgObj.find("input[name='prodDivnCd']").val()).replace(/\s/gi, '');				//상품의 유형 (규격/패션)
				var ctrTypeDivnCd = $.trim(tgObj.find("input[name='ctrTypeDivnCd']").val()).replace(/\s/gi, '');		//센터유형
				var tmprtDivnCd = $.trim(tgObj.find("input[name='tmprtDivnCd']").val()).replace(/\s/gi, '');			//온도구분
				var pbVenFg = $.trim(tgObj.find("input[name='pbVenFg']").val()).replace(/\s/gi, '');					//PB업체구분
				var prodTypeDivnCd = $.trim(tgObj.find("input[name='prodTypeDivnCd']").val()).replace(/\s/gi, '');		//상품유형 (NB,PB,....)
				var prodDescYn = $.trim(tgObj.find("input[name='prodDescYn']").val()).replace(/\s/gi, '');				//상품상세설명
				
				var pogImgYn = $.trim(tgObj.find("input[name='pogImgYn']").val()).toUpperCase();						//POG 오프라인 이미지 등록여부
				var esgYn = $.trim(tgObj.find("input[name='esgYn']").val());											//ESG 상품구분
				var esgItmRegYn = $.trim(tgObj.find("input[name='esgItmRegYn']").val()).toUpperCase();					//ESG 인증항목 등록체크
				var esgDtFgChk = $.trim(tgObj.find("input[name='esgDtFgChk']").val()).toUpperCase();					//ESG 인증기간 필수입력항목 체크
				var esgDtValidChk = $.trim(tgObj.find("input[name='esgDtValidChk']").val()).toUpperCase();				//ESG 인증기간 유효성검사
				var chanCdArr = $.trim(tgObj.find("input[name='chanCd']").val()).split(",");							//채널코드 arr
				
				var onOffGubun = $.trim(tgObj.find("input[name='arrOnOffGubun']").val());			//온오프구분
				var imgId = $.trim(tgObj.find("input[name='arrImgId']").val());						//이미지아이디
				var trdTypeDivnCd = $.trim(tgObj.find("input[name='arrTrdTypeDivnCd']").val());		//거래유형코드
				var vicOnlineCd = $.trim(tgObj.find("input[name='vicOnlineCd']").val());			//온라인적용구분
				var admFg = $.trim(tgObj.find("input[name='admFg']").val());						//관리자구분
				var maxxOnlyYn = (chanCdArr.length == 1 && chanCdArr[0] == "KR03")? "Y" : "N"; 		//MAXX 단일채널 여부
				
				var bosSendDy = $.trim(tgObj.find("input[name='bosSendDy']").val()).replace(/\D/g, "");			//보스전송일자
				
				var bosTgYn = $.trim(tgObj.find("input[name='bosTgYn']").val()).toUpperCase();			//보스승인대상여부 
				
				
				//BOS 승인대상일 경우,
// 				if((trdTypeDivnCd == "1" && maxxOnlyYn == "N") || (trdTypeDivnCd == "1" && maxxOnlyYn == "Y" && vicOnlineCd == "X")){
				if(bosTgYn == "Y"){
					//BOS 전송일자가 존재하면, 확정요청불가
					if(bosSendDy != null && bosSendDy != ""){
						alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]은 이미 BOS에 확정요청된 상품입니다.");
						flag = false;
						return false;
					}
				}
				
				
				//채널 선택 여부
				if(chanCdArr == undefined || chanCdArr == null || chanCdArr.length == 0){
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  채널을 1건 이상 선택해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
					flag = false;
					return false;
				}

				//전상법 등록 여부
				if (prodcommerce == "N" && prodTypeFlag != "02" && prodTypeFlag != "03") {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  전상법정보를 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
					flag = false;
					return false;	
				}
				
				//온오프상품 VALIDATION ==========================START
				// 와이드 온라인 이미지가 없는경우
				if (wideImgNcntYn == "N") {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  와이드 이미지를 다시 등록해 주세요. \n 만약 등록되어 있더라도 다시 등록하세요!\n!");
					flag = false;
					return false;
				}
				
				//온라인 이미지가 없는경우
				if (imgNcntYn == "N") {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  온라인이미지를 3개 이상 등록해 주세요. \n만약 등록되어 있더라도 다시 등록하세요.\n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
					flag = false;
					return false;
				}

				//---세부분류(그룹분류) 필수 입력값
				if (grpCd == "") {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  기본정보탭에 세부분류가  입력되지 않았습니다. \n등록일자를 클릭하신 다음 상세보기에서 세부분류  등록하시고 속성탭에서 세부분류속성을 다시 저장해 등록하세요!");
					flag = false;
					return false;
				}

				//패션 상품 EC 속성 등록 유무
				if( prodDivnCd == "5" && ecAttrRegYn == 'N' ) {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  속성탭에 EC상품속성이 입력되지 않았습니다. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!!");
					flag = false;
					return false;
				}
				
				//---영양성분 필수 입력값
				if (nutAttrRegYn == "N" && canRegNutAttrYn == "Y") {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  영양성분탭에 영양성분속성이 입력되지 않았습니다. \n등록일자를 클릭하신 다음 영양성분 속성탭에서 영양성분속성을 저장해 등록하세요!");
					flag = false;
					return false;
				}

				//--- pbVenFg : X(PB O), prodTypeDivnCd : 2(PB)
				if (pbVenFg == "X" && prodTypeDivnCd != "2") {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]\nPB 파트너사의 경우 상품 유형 PB만 등록하실 수 있습니다. \n등록일자를 클릭하신 다음 상품 유형에 PB로 저장해 등록하세요!");
					flag = false;
					return false;
				}

				//-----상품범주가 묶음 상품일때는 변형속성 입력여부를 체크한다.
				if (prodAttTypFg == "01") {
					//-----입력해야 될 변형속성이 있고
					if (varAttCnt > 0) {
						//---입력된 변형속성이 없으면 확정처리 불가능
						if (inputVarAttCnt <= 0) {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  상품속성정보가 입력되지 않았습니다. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
							flag = false;
							return false;
						}
					}

					//-----온오프이면서 001아 0이면 등록안됨
					if (inputVarAttCnt001 = 0) {
						alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  상품속성정보가 잘못 입력되지 않았습니다. \n삭제하시고 다시  등록하세요! 아니면 LCN으로 연락부탁드립니다.");
						flag = false;
						return false;
					}
				}
				
				/* 2016.05.12 직매입, 특약1 경우 분석속성이 있을때만 필수입력 체크 하도록 변경 by song min kyo */
				if (trdTypeDivnCd == "1" || trdTypeDivnCd == "2") {
					if (grpAttrCnt > 0) {
						//if (inputGrpAttrCnt < grpAttrCnt) {
						if (inputGrpAttrCnt != grpAttrCnt) {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  의 세부분류 속성정보는 필수입니다. \n등록일자를 클릭하신 다음 상세보기[상품속성]에서 등록하세요!");
							flag = false;
							return false;
						}
					}	
				}

				if ((ctrTypeDivnCd === '1' || ctrTypeDivnCd === '3') && tmprtDivnCd !== '0') {
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]\n센터유형이 상온 및 패션일 경우 온도구분 10도만 선택가능합니다");
					flag = false;
					return false;
				}
				
				/**** 차세대 추가 ****/
				//POG 이미지 필수등록 (1건이상)
				if(pogImgYn != 'Y'){
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  POG 이미지가 등록되지 않았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
					flag = false;
					return false;
				}
				
				//ESG 적용여부 미선택
				if(esgYn == ""){
					alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 정보가 등록되지 않았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
					flag = false;
					return false;
				}
				
				//ESG 적용 상품일 경우에만 체크
				if(esgYn == "1"){
					//ESG 인증항목 미등록
					if(esgItmRegYn != "Y"){
						alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 인증 항목이 등록되지 않았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
						flag = false;
						return false;
					}
					
					//ESG 날짜 필수 입력 항목 체크
					if(esgDtFgChk != "Y"){
						alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 인증 기간이 등록되지 않았았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
						flag = false;
						return false;
					}
					
					//ESG 날짜 유효성 체크
					if(esgDtValidChk != "Y"){
						alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 인증 기간이 유효하지 않습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
						flag = false;
						return false;
					}
				}
				/**** .차세대 추가 ****/
				//온오프상품 VALIDATION ==========================END
				
				prodData.pgmId 		= pgmId;			//프로그램아이디 (상품코드)
				prodData.onOffGubun = onOffGubun;		//온오프구분
				prodData.imgId 		= imgId;			//이미지아이디
				prodData.trdTypeDivnCd = trdTypeDivnCd; //거래유형코드
				prodData.vicOnlineCd = vicOnlineCd; 	//온라인적용구분
				prodData.admFg = admFg; 				//관리자여부
				prodData.maxxOnlyYn	= maxxOnlyYn;		//MAXX 단일채널여부
				
				prodArr.push(prodData);
			});	
			if(!flag) return;
			
// 			var srchOnOffDivnCode = $("#searchForm select[name='srchOnOffDivnCode']").val().replace(/\s/gi, '');
			
			paramInfo.prodArr = prodArr;
			paramInfo.arrProxyNm = ["MST2120"];
			paramInfo.gbn = "EDI";
// 			paramInfo.srchOnOffDivnCode = srchOnOffDivnCode;
			
			var adminId = "<c:out value='${epcLoginVO.adminId}'/>"; //상품등록이후 조회된 상품의 협력업체 코드
			if (adminId == "" || adminId == "online") {
				paramInfo["uAdmFg"] = "1";
			} else {
				paramInfo["uAdmFg"] = "3";
			}
			
			if (!confirm("확정요청 하시겠습니까?")) {
				return;
			}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/fixImsiProductList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					
					var message = "";
					var toBos = ($.trim(data.toBos)=="")?0:data.toBos;
					var toErp = ($.trim(data.toErp)=="")?0:data.toErp;
					
					var bosMsg = (toBos > 0)?$.trim(data.bosMsg):"";
					var erpMsg = (toErp > 0)?$.trim(data.erpMsg):"";
					
					
					switch(data.msg){
						case "SUCCESS":		//성공
							message = "<spring:message code='msg.common.success.request'/>";
							if((toBos > 0 && "SUCCESS"!=bosMsg)){
								message += "\nBOS 상품 전송은 실패하였습니다.";						
							}
							if((toErp > 0 && "SUCCESS"!=erpMsg)){
								message += "\nERP 상품 전송은 실패하였습니다.";
							}
							break;
						case "NO_DATA_LIST":
							message = "확정요청 가능한 상품 데이터가 없습니다.";
							break;
						default:
							message = "<spring:message code='msg.common.fail.request'/>";	//실패
							break;
					}
					
					alert(message);

					//목록 재조회
					$("input[name=allProductCode]").attr("checked", false);
					_eventSearch();
				}
			});
			
		}
		
		/* 상품 확정 */
		function _eventDoFixOld() {
			var paramInfo = {};
			var staffSellChk = false;

			var selectedLength = $("input[name=selectedProduct]:checked").length;

			if ( selectedLength <= 0 ) {
				alert("<spring:message code='msg.order.fix'/>");
				return;
			}

			/*
			//-----한번에 5개 이상의 상품은 RFC 요청이 안되도록 
			if( selectedLength > 5 ) {
				alert("<spring:message code='msg.prod.fix.cnt'/>");
				return;
			} */

			//20180604-EDI 임시보관함 확정시 5개에서 20개로 변경 (온라인전용 및 소셜 상품만 적용) 하기 위한 구분값 설정 (온오프상품의 한번에 5개 이상은 RFC 요청이 안되는 조건은 동일)
			if (rsOnOffDivnCode == '0') {
				if( selectedLength > 5 ) {
					alert("온오프겸용 상품의 경우 한번에 최대 5개의 상품까지만 확정요청이 가능합니다.");
					return;
				}
			} else {
				if( selectedLength > 20 ) {
					alert("온라인전용 및 소셜 상품의 경우 한번에 최대 20개의 상품까지만 확정요청이 가능합니다.");
					return;
				}
			}

			//전체 체크박스 길이 만큼 for문 돌면서
			for (var i = 0; i < $("input[name=selectedProduct]").length; i++) {

				//선택된 상품이고
				if ($("input[name=selectedProduct]").eq(i).is(":checked")) {

					var prodcommerce = $("input[name='prodcommerce']").eq(i).val().replace(/\s/gi, '');			//선택된 상품의 전상법 상태값
					var prodNm = $("input[name='prodNm']").eq(i).val().replace(/\s/gi, '');						//선택된 상품의 상품명
					var onOffDivnCd = $("input[name='onoffDivnCd']").eq(i).val().replace(/\s/gi, '');			//선택된 상품의 온오프상품구분
					var imgNcntYn = $("input[name='imgNcntYn']").eq(i).val().replace(/\s/gi, '');				//선택된 상품의 이미지등록여부
					var l3Cd = $("input[name='l3Cd']").eq(i).val().replace(/\s/gi, '');							//선택된 상품의 소분류코드
					//var colorSizeState = $("input[name='colorSizeState']").eq(i).val().replace(/\s/gi, '');	//선택된 상품의 소분류코드
					var prodAttTypFg = $("input[name='prodAttTypFg']").eq(i).val().replace(/\s/gi, '');			//선택된 상품의 온오프상품구분
					var varAttCnt = $("input[name='varAttCnt']").eq(i).val().replace(/\s/gi, '');				//선택된 상품의 이미지등록여부
					var inputVarAttCnt = $("input[name='inputVarAttCnt']").eq(i).val().replace(/\s/gi, '');		//선택된 상품의 소분류코드
					var trdTypeDivnCd = $("input[name='trdTypeDivnCd']").eq(i).val().replace(/\s/gi, '');		//선택된 상품의 거래유형 코드 [1:직매입, 2:특약1, 4:특약2]
					var inputGrpAttrCnt = $("input[name='inputGrpAttrCnt']").eq(i).val().replace(/\s/gi, '');	//선택된 상품의 입력된 분석속성 갯수
					var grpAttrCnt = $("input[name='grpAttrCnt']").eq(i).val().replace(/\s/gi, '');				//선택된 상품의 입력할 분석속성 갯수
					var inputVarAttCnt001 = $("input[name='inputVarAttCnt001']").eq(i).val().replace(/\s/gi, '');//선택된 상품의 이미지등록여부
					var grpCd = $("input[name='grpCd']").eq(i).val().replace(/\s/gi, '');						//선택된 상품의그룹분류코드
					var prodTypeFlag = $("input[name='prodTypeFlag']").eq(i).val().replace(/\s/gi, '');			//선택된 상품의 상품종류
					var staffSellYn = $("input[name='staffSellYn']").eq(i).val().replace(/\s/gi, '');			//선택된 상품의 임직원몰판매가능여부
					var vicOnlineCd = $("input[name='vicOnlineCd']").eq(i).val().replace(/\s/gi, '');			//vic전용 온라인여부	
					var matCd = $("input[name='matCd']").eq(i).val().replace(/\s/gi, '');						//vic전용 온라인여부	
					var admFg = $("input[name='admFg']").eq(i).val().replace(/\s/gi, '');						//온오프 등록시 관리자여부
					var dealRepProdYn = $("input[name='dealRepProdYn']").eq(i).val().replace(/\s/gi, '');		//딜상품여부
					var onlineProdTypeCd = $("input[name='onlineProdTypeCd']").eq(i).val().replace(/\s/gi, '');	//온라인상품유형
					var wideImgNcntYn = $("input[name='wideImgNcntYn']").eq(i).val().replace(/\s/gi, '');		//선택된 상품의 이미지등록여부
					var ecAttrRegYn = $("input[name='ecAttrRegYn']").eq(i).val().replace(/\s/gi, '');			//선택된 상품의 ec 속성 등록 유무
					var nutAttrRegYn = $("input[name='nutAttrRegYn']").eq(i).val().replace(/\s/gi, '');			//선택된 상품의 영양 속성 등록 유무
					var canRegNutAttrYn = $("input[name='canRegNutAttrYn']").eq(i).val().replace(/\s/gi, '');	//선택된 상품의 등록할 수 있는 영양 속성 존재 유무
					var prodDivnCd = $("input[name='prodDivnCd']").eq(i).val().replace(/\s/gi, '');				//선택된 상품의 유형 (규격/패션)
					var ctrTypeDivnCd = $("input[name='ctrTypeDivnCd']").eq(i).val().replace(/\s/gi, '');	    //센터유형
					var tmprtDivnCd = $("input[name='tmprtDivnCd']").eq(i).val().replace(/\s/gi, '');		    //온도구분
					var pbVenFg = $("input[name='pbVenFg']").eq(i).val().replace(/\s/gi, '');		            //PB업체구분
					var prodTypeDivnCd = $("input[name='prodTypeDivnCd']").eq(i).val().replace(/\s/gi, '');		            //상품유형
					
					//20180626 - EDI 임시보관함 상품 확정 시 배송정책을 설정하지 않을 경우 배송정책 설정하도록 유효성 추가
					var entpCondUseYn = $("input[name='entpCondUseYn']").eq(i).val().replace(/\s/gi, '');		//업체조건사용여부

					//20180904 상품키워드 입력 기능 추가
					var keywordYn = $("input[name='keywordYn']").eq(i).val().replace(/\s/gi, '');				//상품키워드 등록 여부

					//20190312 상품상세설명 입력 필수 확인 기능 추가
					var prodDescYn = $("input[name='prodDescYn']").eq(i).val().replace(/\s/gi, '');				//선택된 상품의 전상법 상태값

					//20190417 배송 설정 여부
					var nochDeliYn = $("input[name='nochDeliYn']").eq(i).val().replace(/\s/gi, '');				//배송 설정 여부
					
					//POG 오프라인 이미지 등록여부
					var pogImgYn = $.trim($("input[name='pogImgYn']").eq(i).val()).toUpperCase();
					
					
					var esgYn = $.trim($("input[name='esgYn']").eq(i).val());							//ESG상품구분
					var esgItmRegYn = $.trim($("input[name='esgItmRegYn']").eq(i).val()).toUpperCase();	//ESG인증항목 등록체크
					var esgDtFgChk = $.trim($("input[name='esgDtFgChk']").eq(i).val()).toUpperCase();	//ESG인증기간 필수입력항목 체크
					var esgDtValidChk = $.trim($("input[name='esgDtValidChk']").eq(i).val()).toUpperCase();	//ESG인증기간 유효성검사
					
					var chanCdArr = $.trim($("input[name='chanCd']").eq(i).val()).split(",");		//채널코드 arr
					
					//채널 선택 여부
					if(chanCdArr == undefined || chanCdArr == null || chanCdArr.length == 0){
						alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  채널을 1건 이상 선택해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;
					}

					//전상법 등록 여부
					if (prodcommerce == "N" && prodTypeFlag != "02" && prodTypeFlag != "03") {
						alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  전상법정보를 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;	
					}

					// 온오프 상품
					if (onOffDivnCd == "0") {

						
						// 와이드 온라인 이미지가 없는경우
						if (wideImgNcntYn == "N") {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  와이드 이미지를 다시 등록해 주세요. \n 만약 등록되어 있더라도 다시 등록하세요!\n!");
							return;
						}
						
						//온라인 이미지가 없는경우
						if (imgNcntYn == "N") {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  온라인이미지를 3개 이상 등록해 주세요. \n만약 등록되어 있더라도 다시 등록하세요.\n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
							return;
						}

						//---세부분류(그룹분류) 필수 입력값
						if (grpCd == "") {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  기본정보탭에 세부분류가  입력되지 않았습니다. \n등록일자를 클릭하신 다음 상세보기에서 세부분류  등록하시고 속성탭에서 세부분류속성을 다시 저장해 등록하세요!");
							return;
						}

						//패션 상품 EC 속성 등록 유무
						if( prodDivnCd == "5" && ecAttrRegYn == 'N' ) {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  속성탭에 EC상품속성이 입력되지 않았습니다. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!!");
							return;
						}
						
						//---영양성분 필수 입력값
						if (nutAttrRegYn == "N" && canRegNutAttrYn == "Y") {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  영양성분탭에 영양성분속성이 입력되지 않았습니다. \n등록일자를 클릭하신 다음 영양성분 속성탭에서 영양성분속성을 저장해 등록하세요!");
							return;
						}

						//--- pbVenFg : X(PB O), prodTypeDivnCd : 2(PB)
						if (pbVenFg == "X" && prodTypeDivnCd != "2") {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]\nPB 파트너사의 경우 상품 유형 PB만 등록하실 수 있습니다. \n등록일자를 클릭하신 다음 상품 유형에 PB로 저장해 등록하세요!");
							return;
						}

						//-----상품범주가 묶음 상품일때는 변형속성 입력여부를 체크한다.
						if (prodAttTypFg == "01") {
							//-----입력해야 될 변형속성이 있고
							if (varAttCnt > 0) {
								//---입력된 변형속성이 없으면 확정처리 불가능
								if (inputVarAttCnt <= 0) {
									alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  상품속성정보가 입력되지 않았습니다. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
									return;
								}
							}

							//-----온오프이면서 001아 0이면 등록안됨
							if (inputVarAttCnt001 = 0) {
								alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  상품속성정보가 잘못 입력되지 않았습니다. \n삭제하시고 다시  등록하세요! 아니면 LCN으로 연락부탁드립니다.");
								return;
							}
						}

						//-----직매입, 특약1일 경우 분석속성 필수입력 여부를 체크한다. [입력된 분석속성이 입력해야될 분석속성 갯수보다 작거나 입력해야될 분석속성 정보가 없을경우는 확정요청 안되게 처리]						
						/* 2016.05.12 주석처리 by song min kyo[해당소분류의 분석속성이 있을때만 체크하도록 MD의 요청에 의한 주석처리] */
						/* if (trdTypeDivnCd == "1" || trdTypeDivnCd == "2") {
							if ((inputGrpAttrCnt < grpAttrCnt) || grpAttrCnt <= 0) {
								alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  의 분석속성정보는 필수입니다. \n등록일자를 클릭하신 다음 상세보기[상품속성]에서 등록하세요!");
								return;
							}
						} */

						/* 2016.05.12 직매입, 특약1 경우 분석속성이 있을때만 필수입력 체크 하도록 변경 by song min kyo */
						if (trdTypeDivnCd == "1" || trdTypeDivnCd == "2") {
							if (grpAttrCnt > 0) {
								//if (inputGrpAttrCnt < grpAttrCnt) {
								if (inputGrpAttrCnt != grpAttrCnt) {
									alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  의 세부분류 속성정보는 필수입니다. \n등록일자를 클릭하신 다음 상세보기[상품속성]에서 등록하세요!");
									return;
								}
							}	
						}

						if ((ctrTypeDivnCd === '1' || ctrTypeDivnCd === '3')
								&& tmprtDivnCd !== '0') {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]\n센터유형이 상온 및 패션일 경우 온도구분 10도만 선택가능합니다");
							return;
						}
						
						/**** 차세대 추가 ****/
						//POG 이미지 필수등록 (1건이상)
						if(pogImgYn != 'Y'){
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  POG 이미지가 등록되지 않았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
							return;
						}
						
						//ESG 적용여부 미선택
						if(esgYn == ""){
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 정보가 등록되지 않았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
							return;
						}
						
						//ESG 적용 상품일 경우에만 체크
						if(esgYn == "1"){
							//ESG 인증항목 미등록
							if(esgItmRegYn != "Y"){
								alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 인증 항목이 등록되지 않았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
								return;
							}
							
							//ESG 날짜 필수 입력 항목 체크
							if(esgDtFgChk != "Y"){
								alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 인증 기간이 등록되지 않았았습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
								return;	
							}
							
							//ESG 날짜 유효성 체크
							if(esgDtValidChk != "Y"){
								alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  ESG 인증 기간이 유효하지 않습니다. \n 만약 등록되어 있더라도 다시 등록하세요!\n");
								return;
							}
						}
						/**** .차세대 추가 ****/
						

					//온라인 or 소셜상품	
					} else if (onOffDivnCd == "1" || onOffDivnCd == "2") {

						//20180904 상품키워드 입력 기능 추가
						if (prodTypeFlag == "01") {
							if(keywordYn == "N") {
								alert("선택된 확정 자료 상품중 [No:"+i+" 상품명:" + prodNm + "]  상품키워드를 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
								return;
							}
						}

						/*
						//20180626 - EDI 임시보관함 상품 확정 시 배송정책을 설정하지 않을 경우 배송정책 설정하도록 유효성 추가
						if(entpCondUseYn == '' || entpCondUseYn == null) {
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  배송정책을 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
							return;
						} */

						//온라인이거나 소셜일때 세분류가 없을때 유효성체크  //온라인이거나 소셜일때 세분류 필수
						if (l3Cd == "" && onOffDivnCd == "1" && prodTypeFlag != "03") {
							alert("선택된 확정 온라전용 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  세분류 정보가 누락되었습니다. \n등록일자를 클릭하신 다음 상세보기에서 수정하세요!!");
							return;
						}

						if (dealRepProdYn != 'Y' && prodDescYn == 'N' ) { //상세 이미지가 없는 경우
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  상세 이미지를 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
							return;
						}

						if (imgNcntYn == 'N' ) { //이미지가 없는 경우
							alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  온라인이미지를 3개 이상 등록해 주세요. \n만약 등록되어 있더라도 다시 등록하세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
							return;
						}

						//2019-04-18 하위 경로 1,2번 화면 모두 배송비 설정이 안되어 있을때 무료 배송으로 보고 상품 확정전 배송비 설정 관련 메시지를 담당자에게 노출.
						//1. SCM > 시스템관리 > 업체정보관리 > 주문배송비관리
						//2. 상품상세 > 배송정책(탭)
						//3. 딜상품 제외
						if (dealRepProdYn != 'Y' && nochDeliYn =='Y') {
							if (!confirm("선택한 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  배송비 설정이 무료로 설정되어 있습니다. \배송비 무료 설정 상태로 진행하시겠습니까? \n(배송비 설정은 등록일자를 클릭하신 다음 상세보기에서 배송정책 탭에서 설정 가능합니다.)")) {
								return;
							}
						}
						
						if(onOffDivnCd == '1' && (onlineProdTypeCd =='13' || onlineProdTypeCd =='03' )){
							if(nochDeliYn !='Y'){
								alert("선택된 확정 자료 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]  \n설치 상품 배송비 정책 변경으로 인해 현재 적용중인  배송비는 더 이상 사용할 수 없습니다.\n설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
								return;
							}
						}

						if (staffSellYn == "1") {
							staffSellChk = true;
						}

					}

					/* if (colorSizeState == "F") {
						alert("선택된 확정 자료 상품중  속성 정보가 누락되었습니다. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;
					} */

				}
			}

			if(staffSellChk){
				alert("선택한 상품중 임직원몰판매가능 상품이 있습니다.\n해당상품은 임직원용상품으로 복사 처리 됩니다.");
			}

			var arrPgmId = new Array(); //상품코드
			var arrOnOffGubun = new Array(); //온오프구분
			var arrImgId = new Array(); //이미지아이디
			var arrTrdTypeDivnCd = new Array(); //거래형태구분[1:직매입, 2:특약1, 4:특약2]
// 			var arrMatCd = new Array();
			var arrVicOnlineCd = new Array();
			var arrAdmFg = new Array();
			var arrProxyNm = ["MST0780", "MST0820"];	//RFC Call name[MST0780:상품확정 마스터, MST0820:이미지정보] - 이미지 RFC CALL은 상품확정 마스터 RFC CALL할떄 같이 전송하므로 현재 사용하지 않는다.
			var arrStaffSellYn = new Array();
// 			var srchOnOffDivnCode = $("#searchForm select[name='srchOnOffDivnCode']").val().replace(/\s/gi, '');
			var arrMaxxOnlyYn = new Array();		//MAXX 단일채널 여부
			var idx = 0;

			for (var i = 0; i < $("input[name=selectedProduct]").length; i++) {
				if ($("input[name=selectedProduct]").eq(i).is(":checked")) {
					
					var chanCdArr = $.trim($("input[name='chanCd']").eq(i).val()).split(",");		//채널코드 arr
					var maxxOnlyYn = (chanCdArr.length == 1 && chanCdArr[0] == "KR03")? "Y" : "N"; 	//MAXX 단일채널 여부
					
					arrPgmId[idx] = $("#searchForm input[name='arrPgmId']").eq(i).val();
					arrOnOffGubun[idx] = $("#searchForm input[name='arrOnOffGubun']").eq(i).val();
					arrImgId[idx] = $("#searchForm input[name='arrImgId']").eq(i).val();
					arrTrdTypeDivnCd[idx] = $("#searchForm input[name='arrTrdTypeDivnCd']").eq(i).val();
					arrStaffSellYn[idx] = $("#searchForm input[name='staffSellYn']").eq(i).val();
// 					arrMatCd[idx] = $("#searchForm input[name='matCd']").eq(i).val();
					arrVicOnlineCd[idx] = $("#searchForm input[name='vicOnlineCd']").eq(i).val();
					arrAdmFg[idx] = $("#searchForm input[name='admFg']").eq(i).val();
					arrMaxxOnlyYn[idx] = maxxOnlyYn;
					idx++;
				}
			}
					
			paramInfo["arrPgmId"] = arrPgmId;					//상품코드
			paramInfo["arrOnOffGubun"] = arrOnOffGubun;			//상품의 온오프 구분
			paramInfo["arrImgId"] = arrImgId;					//상품 이미지 아이디
			paramInfo["arrTrdTypeDivnCd"] = arrTrdTypeDivnCd;	//거래형태구분[1:직매입, 2:특약1, 4:특약2]
			paramInfo["arrProxyNm"] = arrProxyNm;				//RFC Call NAME....
			paramInfo["arrStaffSellYn"] = arrStaffSellYn;		//임직원몰판매가능여부
// 			paramInfo["arrMatCd"] = arrMatCd;					//
			paramInfo["arrVicOnlineCd"] = arrVicOnlineCd;		//
			paramInfo["arrAdmFg"] = arrAdmFg;					//
			paramInfo["arrMaxxOnlyYn"] = arrMaxxOnlyYn;			//MAXX 단일채널선택여부
			paramInfo["gbn"] = "EDI";
// 			paramInfo["srchOnOffDivnCode"] = srchOnOffDivnCode;

			var adminId = "<c:out value='${epcLoginVO.adminId}'/>"; //상품등록이후 조회된 상품의 협력업체 코드
			if (adminId == "" || adminId == "online" || paramInfo["srchOnOffDivnCode"] == "1") {
				paramInfo["uAdmFg"] = "1";
			} else {
				paramInfo["uAdmFg"] = "3";
			}
			// console.log(paramInfo);
			if(paramInfo["srchOnOffDivnCode"] == "1") {
				// 출고지/반품지 등록여부 체크
				var params = {};
				params["entp_cd"] = $("#entp_cd").val();
				if(!callCheckAddressInfo(params)) {
					return;
				}
			}
			if (!confirm("확정요청 하시겠습니까?")) {
				return;
			}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/fixImsiProductList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msg == "SUCCESS") {
						alert("<spring:message code='msg.common.success.request'/>");
					} else {
						alert("<spring:message code='msg.common.fail.request'/>"); //실패
					}

					//목록 재조회
					$("input[name=allProductCode]").attr("checked", false);
					_eventSearch();
				}
			});
		}
			
		function callCheckAddressInfo(params) {
			var result = true; 
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/checkAddressInfo.json"/>',
				data : JSON.stringify(params),
				success : function(data) {
					if (data.vendorIds != "") {
						alert("기본출고지 또는 기본반품지 정보가 없어서 임시보관함 확정이 불가합니다.\n 기본출고지 및 기본반품지 정보를 필수로 입력하여 주시기를 바랍니다.\n(※ 등록 경로 : SCM>시스템관리>업체정보관리>업체주소 입력란) \n 업체코드 : " + data.vendorIds);
						result = false;
					} 
				}
			});
			return result;	
		}

		/* 상품 상세보기 */
		function _eventViewProductInfo(pgmId, onoffDivnCd, prodTypeFlag, bosSendYn) {
			var url	=	"";
			$("#hiddenForm input[name='pgmId']").val(pgmId);
			
			if(bosSendYn == "Y"){
				$("#hiddenForm input[name='mode']").val("view");
			}else{
				$("#hiddenForm input[name='mode']").val("modify");
			}
			

			//-----0:온오프, 1:온라인
			if (onoffDivnCd == "0") {
				url = "<c:url value='/edi/product/NEDMPRO0020Detail.do'/>";
			} else {
				url = "<c:url value='/edi/product/NEDMPRO0030OnlineDetail.do'/>";

				if(prodTypeFlag == "02"){ //딜상품
					url = "<c:url value='/edi/product/DealDetail.do'/>";
				}else if(prodTypeFlag == "03"){	//추가구성품
					url = "<c:url value='/product/ComponentDetail.do'/>";
				}
			}

			$("#hiddenForm").attr("target", "_self");
			$("#hiddenForm").attr("action", url);
			$("#hiddenForm").attr("method", "post").submit();
		}

		function locImg(pgmId){
			$.ajax({
				type : "POST",
				url : "<c:url value='/common/imsiProductImg.do'/>",
				data : {pgmId:pgmId},
				success : function(data){
					var productImgPath = data.productImgPath;
					var productImg = data.productImg;

					var img_element = productImgPath+"/"+productImg;

					var img = new Image();
					img.onload = function() {
						Common.centerPopupWindow(img_element, 'prd', {width : this.width, height : this.height});
					}

					img.src = img_element;
				}
			});

		}

		function imsiProdDetail(pgmId){
			var url = "<c:url value='/common/productImsiDetail.do'/>?pgmId="+pgmId;
			Common.centerPopupWindow(url, 'imsiProdDetail', {width : 1050, height : 768,  scrollBars :"YES"});
		}
		
		
		//관리자 ERP 자동발송요청
		<%--
		function _eventAutoSend(){
			var paramInfo = {};
			
			//선택한 row 수
			var selectedLength = $("input[name=selectedProduct]:checked").length;

			if ( selectedLength <= 0 ) {
				alert("<spring:message code='msg.order.fix'/>");
				return;
			}
			
			//한 번에 5개까지만 확정 가능
			if( selectedLength > maxFixedCnt ) {
				alert("한번에 최대 "+maxFixedCnt+"개의 상품까지만 ERP 발송이 가능합니다.");
				return;
			}
			
			var tody = "";
			var tody_date = new Date();
			var tody_yy = tody_date.getFullYear()
			var tody_mm = ("0"+(tody_date.getMonth()+1)).substr(-2);
			var tody_dd = tody_date.getDate();
			tody =  tody_yy + tody_mm + tody_dd;
			
			var notExpProd = [];
			var flag=true;
			var prodArr = [];
			$("#dataListbody tr").find("input[name=selectedProduct]:checked").each(function(i){
				var prodData = {};
				var tgObj = $(this).closest("tr");
				var pgmId = $.trim(tgObj.find("input[name='arrPgmId']").val()).replace(/\s/gi, '');			//프로그램아이디
				var prodNm = $.trim(tgObj.find("input[name='prodNm']").val()).replace(/\s/gi, '');			//상품명
				
				var trdTypeDivnCd = $.trim(tgObj.find("input[name='trdTypeDivnCd']").val()).replace(/\s/gi, '');		//거래유형 코드 [1:직매입, 2:특약1, 4:특약2]
				var chanCdArr = $.trim(tgObj.find("input[name='chanCd']").val()).split(",");							//채널코드 arr
				var maxxOnlyYn = (chanCdArr.length == 1 && chanCdArr[0] == "KR03")? "Y" : "N"; 		//MAXX 단일채널 여부
				var vicOnlineCd = $.trim(tgObj.find("input[name='vicOnlineCd']").val());			//온라인적용구분
				var admFg = $.trim(tgObj.find("input[name='admFg']").val());									//관리자구분
				
				var bosSendDy = $.trim(tgObj.find("input[name='bosSendDy']").val()).replace(/\D/g, "");			//보스전송일자
				var bosCfrmEndDy = $.trim(tgObj.find("input[name='bosCfrmEndDy']").val()).replace(/\D/g, "");	//보스승인처리 마감일자
				var bosCfrmYn = $.trim(tgObj.find("input[name='bosCfrmYn']").val());							//보스승인여부
				
				var bosTgYn = $.trim(tgObj.find("input[name='bosTgYn']").val()).toUpperCase();			//보스승인대상여부
				
// 				//직매입상품이 아님
// 				if(trdTypeDivnCd != "1"){
// 					alert("선택된 자동발송대상 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]은 BOS 승인 대상 상품이 아닙니다.(직매입 상품이 아님)\nERP 자동 발송은 BOS 승인 대상 중 미처리 건에 한하여 가능합니다.");
// 					flag=false;
// 					return false;
// 				}
			
// 				//직매입&&MAXX 단일 상품일 경우, 온라인적용여부체크
// 				if(trdTypeDivnCd == "1" && maxxOnlyYn == "Y" && vicOnlineCd != "X"){
// 					alert("선택된 자동발송대상 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]은 BOS 승인 대상 상품이 아닙니다.(온라인 미적용 상품)\nERP 자동 발송은 BOS 승인 대상 중 미처리 건에 한하여 가능합니다.");
// 					flag=false;
// 					return false;
// 				}
				
				//BOS 승인대상이 아님
				if(bosTgYn != "Y"){
					alert("선택된 자동발송대상 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]은 BOS 승인 대상 상품이 아닙니다.\nERP 자동 발송은 BOS 승인 대상 중 미처리 건에 한하여 가능합니다.");
					flag=false;
					return false;
				}
				
				
				//BOS 전송일자 확인
				if("" == bosSendDy || bosSendDy.length != 8){
					alert("선택된 자동발송대상 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]은 BOS에 발송되지 않은 상품입니다.\nERP 자동 발송은 BOS 확정요청 발송 이후부터 가능합니다.");
					flag=false;
					return false;
				}
				
				//BOS 승인처리여부 확인
				if("Y"==bosCfrmYn){
					alert("선택된 자동발송대상 상품중 [No:"+(i+1)+" 상품명:" + prodNm + "]은 이미 BOS 승인/반려 처리된 상품입니다.\nERP 자동 발송은 BOS 승인 대상 중 미처리 건에 한하여 가능합니다.");
					flag=false;
					return false;
				}
				
				//BOS 승인 마감일자가 아직 지나지 않은 상품인 경우, array에 담음 (message용)
				if(bosCfrmEndDy != "" && bosCfrmEndDy.length == 8 && bosCfrmEndDy >= tody){
					notExpProd.push("[No:"+(i+1)+" 상품명:" + prodNm + " BOS승인만료일: "+bosCfrmEndDy+"]");	
				}
				
				prodData.pgmId 		= pgmId;			//프로그램아이디 (상품코드)
				prodData.trdTypeDivnCd = trdTypeDivnCd; //거래유형코드
				prodData.vicOnlineCd = vicOnlineCd; 	//온라인적용구분
				prodData.admFg = admFg; 				//관리자여부
				prodData.maxxOnlyYn	= maxxOnlyYn;		//MAXX 단일채널여부
				
				prodArr.push(prodData);
			});
			
			if(!flag) return;
			
			paramInfo.prodArr = prodArr;
			
			var adminId = "<c:out value='${epcLoginVO.adminId}'/>"; //상품등록이후 조회된 상품의 협력업체 코드
			if (adminId == "" || adminId == "online") {
				paramInfo["uAdmFg"] = "1";
			} else {
				paramInfo["uAdmFg"] = "3";
			}
			
			var cfrmMsg = "선택하신 상품을 ERP로 발송 하시겠습니까?";
			if(notExpProd != null && notExpProd.length > 0){
				cfrmMsg = "선택하신 상품 중, BOS 승인처리 만료일자가 지나지 않은 상품이 존재합니다.\n발송 하시겠습니까?\n";
				cfrmMsg += notExpProd.join("\n");
			}
			
			if(!confirm(cfrmMsg)) return;
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/updateImsiProductListFixAutoSend.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					
					if (data.msg == "SUCCESS") {
						alert("<spring:message code='msg.common.success.request'/>");
					} else {
						alert("<spring:message code='msg.common.fail.request'/>"); //실패
					}

					//목록 재조회
					$("input[name=allProductCode]").attr("checked", false);
					_eventSearch();
				}
			});
		}
		
		--%>
	</script>

	<!-- DATA LIST -->
	<script id="dataListTemplate" type="text/x-jquery-tmpl">
		<%--{%if bosSendDy != null && bosSendDy != ""%}
		<tr class="r1" bgcolor="fffac4">
		{%else%}
		<tr class="r1" bgcolor="ffffff">
		{%/if%}
		--%>
		<tr class="r1" bgcolor="ffffff">
		<td align="center">
			<input type="checkbox"	name="selectedProduct"	id="selectedProduct" />
			<input type="hidden" name="arrPgmId"			id="arrPgmId"			value="<c:out value='\${pgmId}'/>"	 />
			<input type="hidden" name="arrOnOffGubun"		id="arrOnOffGubun"		value="<c:out value='\${onoffDivnCd}'/>" />
			<input type="hidden" name="arrImgId"			id="arrImgId"			value="<c:out value='\${prodImgId}'/>" />
			<input type="hidden" name="arrTrdTypeDivnCd"	id="arrTrdTypeDivnCd"	value="<c:out value='\${trdTypeDivnCd}'/>"	/>
			<input type="hidden" name="imgNcntYn" 			id="imgNcntYn"			value="<c:out value='\${imgNcntyn}'/>" />
			<input type="hidden" name="prodcommerce"		id="prodcommerce"		value="<c:out value='\${prodcommerce}'/>"	/>
			<input type="hidden" name="prodNm"				id="prodNm"				value="<c:out value='\${prodNm}'/>" />
			<input type="hidden" name="onoffDivnCd"			id="onoffDivnCd"		value="<c:out value='\${onoffDivnCd}'/>"	/>
			<input type="hidden" name="l3Cd"				id="l3Cd"				value="<c:out value='\${l3Cd}'/>" />
			<input type="hidden" name="colorSizeState"		id="colorSizeState"		value="<c:out value='\${colorSizeState}'/>"	/>
			<input type="hidden" name="prodAttTypFg"		id="prodAttTypFg"		value="<c:out value='\${prodAttTypFg}'/>" />
			<input type="hidden" name="varAttCnt"			id="varAttCnt"			value="<c:out value='\${varAttCnt}'/>" />
			<input type="hidden" name="inputVarAttCnt"		id="inputVarAttCnt"		value="<c:out value='\${inputVarAttCnt}'/>"	/>
			<input type="hidden" name="prodTypeFlag"		id="prodTypeFlag"		value="<c:out value='\${prodTypeFlag}'/>" />
			<input type="hidden" name="staffSellYn"			id="staffSellYn"		value="<c:out value='\${staffSellYn}'/>" />
			<input type="hidden" name="trdTypeDivnCd"		id="trdTypeDivnCd"		value="<c:out value='\${trdTypeDivnCd}'/>"	/>
			<input type="hidden" name="inputGrpAttrCnt"		id="inputGrpAttrCnt"	value="<c:out value='\${inputGrpAttrCnt}'/>" />
			<input type="hidden" name="grpAttrCnt"			id="grpAttrCnt"			value="<c:out value='\${grpAttrCnt}'/>" />
			<input type="hidden" name="inputVarAttCnt001"	id="inputVarAttCnt001"	value="<c:out value='\${inputVarAttCnt001}'/>" />
			<input type="hidden" name="grpCd"				id="grpCd"				value="<c:out value='\${grpCd}'/>" />
			<input type="hidden" name="vicOnlineCd"			id="vicOnlineCd"		value="<c:out value='\${vicOnlineCd}'/>" />
			<input type="hidden" name="admFg"				id="admFg"				value="<c:out value='\${admFg}'/>" />
			<input type="hidden" name="matCd"				id="matCd"				value="<c:out value='\${matCd}'/>" />
			<input type="hidden" name="prodDescYn"			id="prodDescYn"			value="<c:out value='\${prodDescYn}'/>" />
			<input type="hidden" name="nochDeliYn"			id="nochDeliYn"			value="<c:out value='\${nochDeliYn}'/>" />
			<input type="hidden" name="onlineProdTypeCd"	id="onlineProdTypeCd"	value="<c:out value='\${onlineProdTypeCd}'/>" />
			<input type="hidden" name="wideImgNcntYn" 		id="wideImgNcntYn"		value="<c:out value='\${wideImgNcntyn}'/>" />
			<input type="hidden" name="ecAttrRegYn" 		id="ecAttrRegYn"		value="<c:out value='\${ecAttrRegYn}'/>" />
			<input type="hidden" name="nutAttrRegYn" 		id="nutAttrRegYn"		value="<c:out value='\${nutAttrRegYn}'/>" />
			<input type="hidden" name="canRegNutAttrYn" 	id="canRegNutAttrYn"	value="<c:out value='\${canRegNutAttrYn}'/>" />
			<input type="hidden" name="prodDivnCd" 			id="prodDivnCd"			value="<c:out value='\${prodDivnCd}'/>" />
			<input type="hidden" name="ctrTypeDivnCd" 		id="ctrTypeDivnCd"		value="<c:out value='\${ctrTypeDivnCd}'/>" />
			<input type="hidden" name="tmprtDivnCd" 		id="tmprtDivnCd"		value="<c:out value='\${tmprtDivnCd}'/>" />
			<input type="hidden" name="pbVenFg"     		id="pbVenFg"		    value="<c:out value='\${pbVenFg}'/>" />
			<input type="hidden" name="prodTypeDivnCd" 		id="prodTypeDivnCd"	    value="<c:out value='\${prodTypeDivnCd}'/>" />
			<input type="hidden" name="pogImgYn" 			id="pogImgYn"		    value="<c:out value='\${pogImgYn}'/>" />
			<input type="hidden" name="esgYn" 				id="esgYn"			    value="<c:out value='\${esgYn}'/>" />
			<input type="hidden" name="esgItmRegYn" 		id="esgItmRegYn"		value="<c:out value='\${esgItmRegYn}'/>" />
			<input type="hidden" name="esgDtFgChk" 			id="esgDtFgChk"		    value="<c:out value='\${esgDtFgChk}'/>" />
			<input type="hidden" name="esgDtValidChk" 		id="esgDtValidChk"		value="<c:out value='\${esgDtValidChk}'/>" />
			<input type="hidden" name="chanCd" 				id="chanCd"				value="<c:out value='\${chanCd}'/>" />
			<input type="hidden" name="bosSendDy" 			id="bosSendDy"			value="<c:out value='\${bosSendDy}'/>" />
			<input type="hidden" name="bosCfrmEndDy" 		id="bosCfrmEndDy"		value="<c:out value='\${bosCfrmEndDy}'/>" />
			<input type="hidden" name="bosCfrmYn" 			id="bosCfrmYn"			value="<c:out value='\${bosCfrmYn}'/>" />
			<input type="hidden" name="bosTgYn" 			id="bosTgYn"			value="<c:out value='\${bosTgYn}'/>" />


			<!-- 20180626 - EDI 임시보관함 상품 확정 시 배송정책을 설정하지 않을 경우 배송정책 설정하도록 유효성 추가 -->
			<input type="hidden"	name="entpCondUseYn"		id="entpCondUseYn"		value="<c:out value='\${entpCondUseYn}'/>" />
			<!-- 20180626 - EDI 임시보관함 상품 확정 시 배송정책을 설정하지 않을 경우 배송정책 설정하도록 유효성 추가 -->

			<!-- 20180904 상품키워드 입력 기능 추가 -->
			<input type="hidden" name="keywordYn"			id="keywordYn"			value="<c:out value='\${keywordYn}'/>" />
			<!-- 20180904 상품키워드 입력 기능 추가 -->
			<!-- 20190325 딜상품여부 추가 -->
			<input type="hidden" name="dealRepProdYn"		id="dealRepProdYn"		value="<c:out value='\${dealRepProdYn}'/>" />
			<!-- 20190325 딜상품여부 추가 -->

		</td>
		<td align="center"><c:out value="\${count}"/></td>
		<td align="center">
			{%if bosSendDy != null && bosSendDy != ""%}
			<a href="javascript:_eventViewProductInfo('<c:out value="\${pgmId}"/>', '<c:out value="\${onoffDivnCd}"/>','<c:out value="\${prodTypeFlag}"/>', 'Y')"
			{%else%}
			<a href="javascript:_eventViewProductInfo('<c:out value="\${pgmId}"/>', '<c:out value="\${onoffDivnCd}"/>','<c:out value="\${prodTypeFlag}"/>', 'N')"
			{%/if%} data-tooltip-content="#tt_\${pgmId}" class="tooltip"><c:out value="\${regDate}"/></a>
			<div class="tooltip_templates" style="display:none">
				<div id="tt_\${pgmId}">
					{{html ttCon}}
				</div>
			</div>
		</td>
		<td align="center"><c:out value="\${prodTypeFlagNm}"/></td>
		<td align="center">
			<!-- 온오프이면서 묶음 상품일때만 판매코드 조회 팝업 -->
			{%if onoffDivnCd != '0' || prodAttTypFg == '00' %}
				<c:out value="\${sellCd}"/>
			{%else%}
				<a href="#" onClick="doSellCodeView('<c:out value="\${pgmId}"/>');">판매코드조회</a>
			{%/if%}
		</td>
		<td align="left">&nbsp;<c:out value="\${prodNm}"/></td>
		<td align="center">&nbsp;<c:out value="\${onoffDivnTxt}"/>&nbsp;</td>
		<td align="center"><c:out value="\${prodStandardNm}"/>&nbsp;</td>
		<td align="right"><c:out value="\${norProdPcost}"/>&nbsp;</td>
		<td align="right"><c:out value="\${norProdSalePrc}"/>&nbsp;</td>
		<td align="center"><c:out value="\${prftRate}"/>&nbsp;</td>
		<td align="right"><c:out value="\${wnorProdPcost}"/>&nbsp;</td>
		<td align="right"><c:out value="\${wnorProdSalePrc}"/>&nbsp;</td>
		<td align="center"><c:out value="\${wprftRate}"/>&nbsp;</td>
		<td align="right"><c:out value="\${snorProdPcost}"/>&nbsp;</td>
		<td align="right"><c:out value="\${snorProdSalePrc}"/>&nbsp;</td>
		<td align="center"><c:out value="\${sprftRate}"/>&nbsp;</td>
		<td align="right"><c:out value="\${onorProdPcost}"/>&nbsp;</td>
		<td align="right"><c:out value="\${onorProdSalePrc}"/>&nbsp;</td>
		<td align="center"><c:out value="\${oprftRate}"/>&nbsp;</td>
		<td align="center">	

			<!-- 온오프상품 && 규격상품 && 혼재여부가 단일-->
			{%if onoffDivnCd == '0' && prodDivnCd == '1' && mixYn == '0'%}
				<a href="javascript:registBarcode('<c:out value="\${pgmId}"/>', '00', '<c:out value="\${entpCd}"/>', '<c:out value="\${l1Cd}"/>')">물류바코드<br/> 조회 / 등록</a>
			{%/if%}

			{%if onoffDivnCd == '1'%}
				{%if staffSellYn != '0' && staffSellYn != null && staffSellYn != ''%}
					Y
				{%else%}
					N
				{%/if%}
			{%/if%}

			{%if onoffDivnCd == '2'%}
				-
			{%/if%}
		</td>
		<td align="center">
			{%if imgNcntyn == 'Y'%}
				<a href="javascript:locImg('<c:out value="\${pgmId}"/>')">Image</a>
			{%else%}
				N
			{%/if%}
		</td>
		<td align="center">&nbsp;<c:out value="\${prodcommerce}"/></td>

		<td align="center">&nbsp;<c:out value="\${keywordYn}"/></td>
	
	<%--
		<td align="center">
			{%if prodTypeFlag == '01'%}
			<a href="javascript:imsiProdDetail('<c:out value="\${pgmId}"/>')">미리보기</a>
			{%/if%}
		</td>
	--%>
	</tr>	
	</script>
</head>

<body>
	<!-- 온오프상품 && 규격상품 && 혼재여부가 단일 (해당 주석 처리 부분은 만약에 속성별로 물류바코드를 등록하지 않고 해당 상품별로 물류바코드를 등록할때 사용.)-->
<%-- 		{%if onoffDivnCd == '0' && prodDivnCd == '1' && mixYn == '0'%}
			{%if logiPgmId != null%} <!-- 물류바코드가 등록이 되어 있으면 -->
				<font color="red">등록</font>
			{%else%}
				<a href="javascript:registBarcode('<c:out value="\${pgmId}"/>', '00')">미등록</a>
			{%/if%}
		{%else%}
			-
		{%/if%} --%>
	<div id="content_wrap">
		<div>
			<form name="searchForm" id="searchForm">
				<input type="hidden" id="prodTypeFlag" name="prodTypeFlag" value="01"/>				<!-- 상품종류:일반 -->
				<input type="hidden" id="srchOnOffDivnCode" name="srchOnOffDivnCode" value="0"/>	<!-- 온오프구분:온오프겸용 -->
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">검색조건</li>
								<li class="btn">
									<a href="#" class="btn" onclick="_eventSearch();"><span><spring:message code="button.common.inquire"/></span></a>
									<a href="#" class="btn" onclick="_eventExcel();"><span><spring:message code="button.common.excel"/></span></a>
									<a href="#" class="btn" onclick="_eventDoFix();"><span><spring:message code="button.common.confirmation"/></span></a>
									<a href="#" class="btn" onclick="_eventDoDelete();"><span><spring:message code="button.common.delete"/></span></a>									
									<%-- <c:if test='${not empty epcLoginVO.adminId and epcLoginVO.adminId ne "online"}'>
										<a href="#" class="btn" onclick="_eventAutoSend();"><span>ERP 자동발송</span></a>
									</c:if> --%>
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
									<th><span class="star">*</span> 상품구분 </th>
									<td>
										<select name="srchProductDivnCode"	id="srchProductDivnCode">
											<option value="">전체</option>
											<option value="1">규격</option>
											<option value="5">패션</option>
											<option value="0">신선비규격</option>
										</select>
									</td>
									<th>협력업체 코드</th>
									<td>
										<c:choose>
												<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
													<c:if test="${not empty param.entp_cd}">
													<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" readonly="readonly" value="${param.entp_cd}" style="width:40%;"/>
													</c:if>
													<c:if test="${empty param.entp_cd}">
													<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
													</c:if>
													<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
												</c:when>

												<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
													<c:if test="${not empty param.entp_cd}">
														<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
													</c:if>
													<c:if test="${ empty param.entp_cd}">
														<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
													</c:if>
												</c:when>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th><span class="star">*</span> 조회기간 </th>
									<td colspan="3">
										<input type="text" class="day" name="srchStartDt" id="srchStartDt" style="width:80px;"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDt');" style="cursor:hand;" />
										~
										<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
									</td>
									<%-- 
									<th>온/오프 구분</th>
									<td >
										<select name="srchOnOffDivnCode"	id="srchOnOffDivnCode">
											<option value="0" selected="selected">온오프겸용 상품</option>
											<option value="1">온라인전용 상품</option>
											<option value="2">소셜 상품</option>
										</select>
									</td>
									--%>
								</tr>
								<%-- 
								<tr>
									<th>상품종류</th>
									<td colspan="3">
										<select name="prodTypeFlag"	id="prodTypeFlag">
											<option value="">전체</option>
											<option value="01">일반상품</option>
											<option value="02">딜상품</option>
											<option value="03">추가구성품</option>
										</select>
									</td>
								</tr>
								--%>
							</table>
						</div>
					</div>
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">검색내역 </li>
							</ul>

							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=2480 bgcolor=efefef>
									<colgroup>
										<col style="width:30px;" />
										<col style="width:30px;" />
										<col style="width:30px;" />
										<col style="width:80px;" />
										<col style="width:100px;" />
										<col style="width:100px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:120px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
										<col style="width:80px;" />
<!-- 										<col style="width:80px;" /> -->
									</colgroup>
									<!-- 규격상품일 경우 ----------------------------------------------------------->
									<tr bgcolor="#e4e4e4">
										<th rowspan="3"><input type="checkbox" name="allProductCode" value="y" /></th>
										<th rowspan="3">No.</th>
										<th rowspan="3">등록일자</th>
										<th rowspan="3">상품종류</th>
										<th rowspan="3">판매(88)코드</th>
										<th rowspan="3">상품명</th>
										<th rowspan="3">온/오프</th>
										<th rowspan="3">규격</th>
										<th colspan="12">원매가</th>
										<th rowspan="3" id="thTitle">물류바코드</th>
										<th rowspan="3">온라인<br/>이미지</th>
										<th rowspan="3">전상법<br/>여부</th>
										<th rowspan="3">상품키워드<br/>등록여부</th>
<!-- 										<th rowspan="3">미리보기</th> -->
									</tr>
									<tr>
										<th colspan="3" class="b-bottom b-right">마트</th>
										<th colspan="3" class="b-bottom b-right">MAXX</th>
										<th colspan="3" class="b-bottom b-right">슈퍼</th>
										<th colspan="3" class="b-bottom">오카도</th>
									</tr>
									<tr>
										<th class="b-bottom b-right">원가</th>
										<th class="b-bottom b-right">매가</th>
										<th class="b-bottom b-right">이익률</th>
										<th class="b-bottom b-right">원가</th>
										<th class="b-bottom b-right">매가</th>
										<th class="b-bottom b-right">이익률</th>
										<th class="b-bottom b-right">원가</th>
										<th class="b-bottom b-right">매가</th>
										<th class="b-bottom b-right">이익률</th>
										<th class="b-bottom b-right">원가</th>
										<th class="b-bottom b-right">매가</th>
										<th class="b-bottom">이익률</th>
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

	<!-- hiddenForm -->
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" name="pgmId" id="pgmId" />
		<input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue" />
		<input type="hidden" name="name" id="name" />
		<input type="hidden" name="mode" id="mode" value="modify" />
		<input type="hidden" name="onoffDivnCd" id="onoffDivnCd" />
		<input type="hidden" name="cfgFg" id="cfgFg" value="I"/> <!-- 물류바코드 등록/수정 구분자 -->
		<input type="hidden" name="logiCfmFg" id="logiCfmFg" /> <!-- 물류바코드 등록 상태[00:등록, 02:심사, 03:반려, 09:완료] -->
		<input type="hidden" name="gbn" id="gbn" value="" /> <!-- 임시보관함['']에서 등록하는지 신상품등록현황[99]에서 등록하는지 구분자 -->
		<input type="hidden" name="venCd" id="venCd" /> <!-- 물류바코드 등록 팝업화면에 parameter로 넘기기 위해 사용 -->
		<input type="hidden" name="l1Cd" id="l1Cd" /> <!-- 물류바코드 등록 팝업화면에 parameter로 넘기기 위해 사용 -->
	</form>

</body>
</html>
