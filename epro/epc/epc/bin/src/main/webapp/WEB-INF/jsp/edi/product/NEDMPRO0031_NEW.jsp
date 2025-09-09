
<%--
- Author(s): SONG MIN KYO
- Created Date: 2015. 12. 30
- Version : 1.0
- Description : 신상품등록  [  온라인전용, 소셜상품  등록페이지 ]

-- Modified by EDSK 2015.11.26
-- 차세대 MD 대응으로 인한 변경사항 수정
-- 온라인 전용 상품등록 페이지를 그대로 복사하여 온라인전용 상품 상세보기 페이지로 사용

-- Modified by MZ_CYH 2017.04.26
-- 'KC 인증마크' => '제품안전인증' 수정 및 필수값 적용
-- KC인증 마스터 코드값 개편
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache"/>
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>임시보관함 상품정보 수정</title>

	<style type="text/css">
		/* TAB */
		.tabs {height:31px; background:#fff;}
		.tabs ul {width:100%; height:31px;}
		.tabs li {float:left; width:130px; height:29px; background:#fff; border:1px solid #ccd0d9; border-radius:2px 2px 0 0; font-size:12px; color:#666; line-height:30px; text-align:center;}
		.tabs li.on {border-bottom:#e7eaef 1px solid; color:#333; font-weight:bold;}
		.tabs li a {display:block; color:#666;}
		.tabs li.on a {color:#333; font-weight:bold;}
	</style>
	<%@include file="../common.jsp" %>
	<%@ include file="/common/scm/scmCommon.jsp" %>
	<%@ include file="./CommonProductFunction.jsp" %>
	<%@ page contentType="text/html; charset=UTF-8"%>
	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script>
	<%@include file="./javascript.jsp" %>
	<script type="text/javascript" >

	function exprRadioCheck(){
		if($("input[name='exprProdYn']:checked").val() == "1"){
			$("#sellerRecomm").removeAttr("disabled");
		} else {
			$("#sellerRecomm").attr("disabled", "disabled");
		}
	}
	
	$(document).ready(function() {
		alert("☆☆☆☆☆☆☆☆☆☆☆☆☆☆ 공지사항 ☆☆☆☆☆☆☆☆☆☆☆☆☆☆\n"+
				"\n[전자상거래 등에서의 소비자보호에 관한 법률]에 의거 "+"'어린이 제품'"+"은 상품 등록시 공급자 적합성 확인을 받으셔야 합니다."+
				"\n상품군 : 아동용 섬유제품, 가죽제품, 면봉, 안경(선글라스 포함), 물안경,"+
				"\n우산 및 양산, 바퀴달린 운동화, 롤러 스케이트, 스키용구, 스노보드, 가구,"+
				"\n쇼핑카트 부속품, 어린이용 장신구, 킥보드, 인라인 롤러 스케이트, 기타"+
				"\n어린이용품(도서류(학습서적, 완구서적, 동화책), 학습교구(공예, 미술, 체육), 보호용품(모서리 충격 보호대, 미끄럼방지 패드, 문열림 방지장치),"+
			    "\n위생용품(칫솔, 칫솔캡/걸이, 청결용품) 등 어린이와 관련된 모든 상품에"+
			    "\n적용됩니다.");
				
		//로딩시 체험형 정보
		exprRadioCheck();
		checkByte();
	});

	var vndId = "";

	$(function() {

		//-----상품 복사이후 복사 완료 메세지 띄운다.
		var message	=	"<c:out value='${param.message}'/>";
		if (message.replace(/\s/gi, '') != "") {
			alert(uploadErrorMsg[message]);
		}

		//-----상품등록이후 상품 기본정도 등록 완료 메세지 띄운다.
		var mode = "<c:out value='${param.mode}'/>";
		if (mode == "basic") {
			alert("신규상품 등록 :  기본정보가 입력되었습니다. 온라인 이미지 및 배송정책은 필히 등록해 주시기 바랍니다.");
		}

		if("${newProdDetailInfo}" == ''){
			vndId = "${epcLoginVO.repVendorId}";

			$('#md_vali_sellCode').val('1111111111111'); // 기획/복함 상품이나 바코드 없는 상품

			$('#md_vali_sellCode').focus(function(){
		    	$('#md_vali_sellCode').val('');
		   	});

			if ("${epcLoginVO.vendorTypeCd}" == '06' ){

				if ("${epcLoginVO.repVendorId}" != ''){
					checkBlackListVendor1 ("${epcLoginVO.repVendorId}");
				}
			} else {

				// 협력업체 선택시 실행.
				if ("${epcLoginVO.repVendorId}" != '') {
					checkBlackListVendor();
				}
			}
		}else{
			vndId = "${newProdDetailInfo.entpCd}";
		}

		//업체코드별 거래형태 조회(selectBox mapping)
		trdTypFgSel();

		$("select[name=entpCode]").change(function() {
			var errorNode = $(this).prev("div[name=error_msg]").length;
			if( errorNode > 0 ) {
				$(this).prev().remove();
			}

			if( $(this).val() != '' ){
				checkBlackListVendor();
			}
		});

		// 필수 콤보박스 값 검증
		$("select.required").change(function() {
			validateSelectBox($(this));
		});

		// 필수 입력항목 검증
		$("input:text.required").blur(function() {
			//validateTextField($(this));
			validateFormTextField($(this));
		});

		// 해당 입력 항목이 값이 있는경우 검증
		$("input:text.requiredIf").blur(function() {
			if($(this).val() != '') {
				validateTextField($(this));
			}
		});

		// radio버튼이 선택되었는지 검증.
		$("input:radio.required").click(function() {
			validateRadioField($(this));
		});

		// 상품구분(규격, 패션) 선택
		$("select[name=productDivnCode]").change(setupFieldByProductDivnCode);

		// 온라인 대표 상품 코드 변경시 실행
		$("select[name=onlineProductCode]").change(selectOnlineRepresentProductCode2);

		// 온라인전용/소셜상품 선택시 실행
		$("input[name=onOffDivnCode]").click(setOnOffDivnCode);

		//88코드 관련 필드 검증
 		//$("input[name=sellCode], input.subSellCode").blur(function() {
		//	if($(this).val != '') {
		//		validateSellCode($(this));
		//	}
		//});

		// 기본값 설정
		$("#teamGroupCode").val('00232');
		$("#protectTagTypeCode").removeClass("required");
		$("#protectTagTypeCode").parent().prev().find("span.star").hide();


		//----- 팀 변경시 이벤트
		$("#newProduct select[name=teamCd]").change(function() {
			//----- 대, 중, 소분류 초기화
			$("#l1Cd option").not("[value='']").remove();
			$("#l2Cd option").not("[value='']").remove();
			$("#l3Cd option").not("[value='']").remove();

			doCategoryReset();

			_eventSelectL1List($(this).val().replace(/\s/gi, ''));
		});

		//----- 대분류 변경시 이벤트
		$("#newProduct select[name=l1Cd]").change(function() {
			var groupCode	=	$("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');

			//----- 중, 소분류 초기화
			$("#l2Cd option").not("[value='']").remove();
			$("#l3Cd option").not("[value='']").remove();

			$("#productCertSelectTitle option").not("[value='']").remove();
			$("#productCertSelectDtlTitle option").not("[value='']").remove();
			$("li[id=productCertDtlSelectBox]").hide();
			//품질안정인증 기존행 삭제
			fnRemoveCert_List();

			doCategoryReset();

			//-----중분류 셋팅
			_eventSelectL2List(groupCode, $(this).val());
		});

		//----- 중분류 변경시 이벤트
		$("#newProduct select[name=l2Cd]").change(function() {
			var groupCode	=	$("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');
			var l1Cd		=	$("#newProduct select[name=l1Cd]").val().replace(/\s/gi, '');

			//----- 소분류 초기화
			$("#l3Cd option").not("[value='']").remove();

			doCategoryReset();

			_eventSelectL3List(groupCode, l1Cd, $(this).val().replace(/\s/gi, ''));
		});

		// 소분류 선택 변경 시
		$("select[id=l3Cd]").change(function() {
			$("#l3Cd").val($(this).val());

			doCategoryReset();

			commerceChange($(this).val());		// 전상법 조회
			certChange($(this).val()); 			// KC인증마크 조회
		});

		// 전상법 select 변경시 액션
		$("select[name=productAddSelectTitle]").change(function() {
			if( $(this).val() != '' ){
			      selectBoxProdTemplateDetailList($(this).val());

			      selectNorProdTempSel($(this).val());
			}else{
				selectNorProdTempSel("");
			}
		});

		// 전상법템플릿 select 변경시 액션
		$("select[name=productAddSelectTemp]").change(function() {
			if( $(this).val() != '' ){
				var param = new Object();
				var targetUrl = '<c:url value="/edi/product/selectNorProdTempVal.do"/>';
				param.norProdSeq = $(this).val();

				$.ajax({
					type: 'POST',
					url: targetUrl,
					data: param,
					success: function(data) {
						var list = data.resultList;

						if(list.length == 0){
							alert("템플릿이 존재하지 않습니다.");
							return;
						}

						for(var i=0; i<list.length; i++){
							$("tr[name=titleProdAdd]").each(function(index){
								var infoColCd = $("input[name=prodAddDetailCd_"+index+"]").val();

								if(infoColCd == list[i].INFO_COL_CD){
									$("input[name=prodAddDetailNm_"+index+"]").val(list[i].COL_VAL);
								}
							});
						}
					}
				});
			}
		});

		// KC 인증마크 select 변경시 액션
		$("select[name=productCertSelectTitle]").change(function() {

			if( $(this).val() == 'KC001' ) 	//KC001 : 해당사항없음
				selectBoxCertTemplateDetailList2($(this).val());
			else if( $(this).val() != '' )
				selectCertTemplateDtlList($(this).val(), $(this).val());
//				selectBoxCertTemplateDetailList($(this).val());
		});

		// KC 인증마크 상세 select 변경시 액션
		$("select[name=productCertSelectDtlTitle]").change(function() {

			if( $(this).val() != '' )
				selectBoxCertTemplateDetailList2($("#newProduct select[name=productCertSelectTitle]").val(), $(this).val());

		});


		//모든 input에 대해서 특수문자 경고표시하기
		$("input").keyup(function(){

			if($(this).attr("name") == "optnLoadContent"){
				return;
			}

			var value = $(this).val();
			var arr_char = new Array();

			arr_char.push("\"");
			arr_char.push("<");
			arr_char.push(">");
			arr_char.push(";");


			for(var i=0 ; i<arr_char.length ; i++)
			{
				if(value.indexOf(arr_char[i]) != -1)
				{
					alert("[<, >, /, ;] 특수문자는 사용하실 수 없습니다.");
					value = value.substr(0, value.indexOf(arr_char[i]));

					$(this).val(value);
				}
			}
		});



		//----- 계절구분 년도 현재년도로 default
		var nowYear	=	"<c:out value='${nowYear}'/>";
		$("select[name='sesnYearCd']").val(nowYear);

		_eventSelectSesnList($("select[name='sesnYearCd']").val().replace(/\s/gi, ''));


		//-----계절년도 체인지 이벤트
		$("select[name='sesnYearCd']").change(function() {
			_eventSelectSesnList($(this).val().replace(/\s/gi, ''));
		});


		initIBSheetGrid(); //그리드 초기화

		/*********************************** 여기서 부턴 상품등록이후 상품 상세정보 설정에 사용됩니다.*************************************************/

		//----- 상품의 상세정보 SeleceBox, RadioButton ValueSetting.[커스텀태그에서는 시큐어 코딩이 불가능 하여 스크립트에서 처리]
		if ('<c:out value="${newProdDetailInfo.pgmId}" />' != "") {
			//-----SeleceBox, RadioButton ValueSetting..
			_eventSetnewProdDetailInfoDetailValue();

			setTimeout(function(){
				selectCategoryList();
			},1000);

			//-----전상법
			setTimeout(function(){
				selectProdTemplateUpdateViewList("<c:out value='${newProdDetailInfo.pgmId}'/>","<c:out value='${newProdDetailInfo.l3Cd}'/>","<c:out value='${newProdDetailInfo.infoGrpCd}'/>","2");
			},1000);

			//-----KC인증마크
			setTimeout(function(){
				selectProdCertTemplateUpdateViewList("<c:out value='${newProdDetailInfo.pgmId}'/>","<c:out value='${newProdDetailInfo.l3Cd}'/>","<c:out value='${newProdDetailInfo.infoGrpCd2}'/>",'2');
			},1000);
		}
		/*****************************************************************************************************************************/

		//-----탭 클릭 이벤트
		$("#prodTabs li").click(function() {
			var id = this.id;

			var pgmId = $("input[name='pgmId']").val();

			//기본정보 탭
			if (id == "pro01") {

			//이미지 등록 탭
			} else if (id == "pro02") {
				if (pgmId == "") {
					alert("상품 기본정보를 먼저 등록해 주세요.");
					return;
				}

				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0032.do'/>");
				$("#hiddenForm").attr("method", "post").submit();

			//배송정책 등록 탭
			} else if (id == "pro03") {
				if (pgmId == "") {
					alert("상품 기본정보를 먼저 등록해 주세요.");
					return;
				}

				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0033.do'/>");
				$("#hiddenForm").attr("method", "post").submit();
			}
		});

		//-- 상품유형 제어
		$(":radio[name='onlineProdTypeCd']").change(function(){
			onlineProdTypeCdChk($(this).val());
		});

		//-- 단품정보 제어
		$(":radio[name='prodPrcMgrYn']").change(function(){
			prodPrcMgrYnChk($(this).val());
		});

		//-- 임직원상품 추가등록 제어
		$("#psbtChkFlag").click(function(){
			//임직원 추가 상품 확인 수정 - 20180523
			//if($(this).attr("checked") == true){
			if($(this).is(":checked") == true){
				$("#staffSellYn").val("1");
			}else{
				$("#staffSellYn").val("0");
				$("#staffDcAmt").val("");
			}
		});

	});

	/** ********************************************************
	 * ibsheet 초기화
	 ******************************************************** */
	function initIBSheetGrid(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "640px", "95px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"", 								Type:"CheckBox", 	SaveName:"SELECTED", 	 		Align:"Center",  Width:25,    Sort:false}
		  , {Header:"전시카테고리",				Type:"Text", 			SaveName:"FULL_CATEGORY_NM", 	Align:"Left",      Width:470,   Edit:0}
		  , {Header:"전시카테고리아이디",	Type:"Text", 			SaveName:"CATEGORY_ID", 	Align:"Left",      Width:1,   Edit:0, Hidden:true }
		  , {Header:"카테고리 상태값",			Type:"Text", 			SaveName:"DISP_YN", 	 		Align:"Center",  Width:145,   Edit:0}
		];


		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	}

	/* 상품등록 이후 상세정보 SelectBox, RadioButton Value Settings..... */
	function _eventSetnewProdDetailInfoDetailValue() {
		var teamCd			=	"<c:out value='${newProdDetailInfo.teamCd}'/>";					//팀코드
		var l1Cd				=	"<c:out value='${newProdDetailInfo.l1Cd}'/>";					//대분류
		var l2Cd				=	"<c:out value='${newProdDetailInfo.l2Cd}'/>";					//중분류
		var l3Cd			    =	"<c:out value='${newProdDetailInfo.l3Cd}'/>";				//소분류
		var taxatDivnCd		=	"<c:out value='${newProdDetailInfo.taxatDivnCd}'/>";		//면과세구분
		var dpUnitCd		=	"<c:out value='${newProdDetailInfo.dpUnitCd}'/>";			//표시단위
		var onlineRepProdCd	=	"<c:out value='${newProdDetailInfo.onlineRepProdCd}'/>";	//온라인 대표상품코드
		var prcIssueDivnCd	=	"<c:out value='${newProdDetailInfo.prcIssueDivnCd}'/>"		//가격발급구분코드
		var qtyWegtDivnCd	=	"<c:out value='${newProdDetailInfo.qtyWegtDivnCd}'/>"		//수량중량구분
		var homeCd			=	"<c:out value='${newProdDetailInfo.homeCd}'/>"				//원산지
		var ecoYn			=	"<c:out value='${newProdDetailInfo.ecoYn}'/>"				//친환경인증여부
		var ecoNm			=	"<c:out value='${newProdDetailInfo.ecoNm}'/>"				//친환경인증분류명
		var dlvGa			=	"<c:out value='${newProdDetailInfo.dlvGa}'/>"				//차등배송비여부
		var insCo			=	"<c:out value='${newProdDetailInfo.insCo}'/>"				//별도설치비유무
		var sesnYearCd		=	"<c:out value='${newProdDetailInfo.sesnYearCd}'/>";			//계절년도
		var sesnDivnCd		=	"<c:out value='${newProdDetailInfo.sesnDivnCd}'/>";			//계절구분
		var norProdSaleCurr		=	"<c:out value='${newProdDetailInfo.norProdSaleCurr}'/>";		//정산매가단위
		var onlineProdTypeCd = "<c:out value='${newProdDetailInfo.onlineProdTypeCd}'/>"; //상품유형
		var hopeDeliPsbtDd = "<c:out value='${newProdDetailInfo.hopeDeliPsbtDd}'/>"; //희망배송가능일
		var sizeUnit = "<c:out value='${newProdDetailInfo.sizeUnit}'/>"; //상품사이즈 단위
		var prodPrcMgrYn = "<c:out value='${newProdDetailInfo.prodPrcMgrYn}'/>"; //옵션상품가격 관리여부
		var psbtStartDy = "<c:out value='${newProdDetailInfo.psbtStartDy}'/>"; //예약주문가능일(시작)
		var psbtEndDy = "<c:out value='${newProdDetailInfo.psbtEndDy}'/>"; //예약주문가능일(종료)

		$("#psbtStartDyVal").val(psbtStartDy.substring(0,4)+"-"+psbtStartDy.substring(4,6)+"-"+psbtStartDy.substring(6,8));
		$("#psbtEndDyVal").val(psbtEndDy.substring(0,4)+"-"+psbtEndDy.substring(4,6)+"-"+psbtEndDy.substring(6,8));
		$("#psbtStartTime").val(psbtStartDy.substring(8,10));
		$("#psbtEndTime").val(psbtEndDy.substring(8,10));

		//-----SelectBox Settings....
		//----- 팀코드 selectBox에 셋팅해주고  팀의 대분류 조회
		$("#newProduct select[name='teamCd']").val(teamCd);
		_eventSelectL1List(teamCd);

		//----- 대분류 selectBox에 셋팅해주고 대분류의 중분류 조회, 전상법, KC인증 조회
		$("#newProduct select[name='l1Cd']").val(l1Cd);
		_eventSelectL2List(teamCd, l1Cd);

		//----- 중분류 selectBox 셋팅해주고 중분류의 소분류 조회
		$("#newProduct select[name='l2Cd']").val(l2Cd);
		_eventSelectL3List(teamCd, l1Cd, l2Cd);

		$("#newProduct select[name='l3Cd']").val(l3Cd);
		commerceChange(l3Cd);		// 전상법 조회
		certChange(l3Cd); 			// KC인증마크 조회

		_eventSelectSesnList(sesnYearCd.replace(/\s/gi, '')); //계절구분 제어

		$("select[name='taxatDivCode']").val(taxatDivnCd);
		$("select[name='taxatDivCode']").attr("disabled",true);
		$("select[name='displayUnitCode']").val(dpUnitCd);
		$("select[name='onlineProductCode']").val(onlineRepProdCd);
		$("select[name='sesnYearCd']").val(sesnYearCd);
		$("select[name='sesnDivnCd']").val(sesnDivnCd);
		$("select[name='productCountryCode']").val(homeCd);
		$("select[name='officialOrder.ecoNm']").val(ecoNm);
		$("select[name='norProdSaleCurr']").val(norProdSaleCurr);
		$("select[name='sizeUnit']").val(sizeUnit);
		$("select[name='hopeDeliPsbtDd']").val(hopeDeliPsbtDd);

		//-----Radio Value Settings....
		$(":radio[name='priceIssueDivnCode']:radio[value='" + prcIssueDivnCd + "']").attr("checked", true);
		$(":radio[name='officialOrder.quantityWeightDivnCode']:radio[value='" + qtyWegtDivnCd + "']").attr("checked", true);
		$(":radio[name='officialOrder.ecoYn']:radio[value='" + ecoYn + "']").attr("checked", true);
		$(":radio[name='officialOrder.dlvgaYn']:radio[value='" + dlvGa + "']").attr("checked", true);
		$(":radio[name='officialOrder.inscoYn']:radio[value='" + insCo + "']").attr("checked", true);
		$(":radio[name='onlineProdTypeCd']:radio[value='" + onlineProdTypeCd + "']").attr("checked", true);
		$(":radio[name='prodPrcMgrYn']:radio[value='" + prodPrcMgrYn + "']").attr("checked", true);

		onlineProdTypeCdChk(onlineProdTypeCd); //상품유형 제어
		prodPrcMgrYnChk(prodPrcMgrYn); //단품정보 제어
	}


	/*
		상품 등록 폼을 전송하기전에 검증로직을 실행하는 함수.
		공통 검증 함수인 validateCommon을 실행하고 각 필드 별로 필요한 작업 실행.
    */
	function validateNewProductInfo() {

		var validationResult = validateCommon();

		// 서적 음반일 경우 발행일 필수 입력
		var categoryValue = $("#l1GroupCode").val();


		// 2015.11.26 by kmlee 서적 음반인 경우 카테고리가 "40"인지 확인이 필요함.
		if( categoryValue == "40" ) {
			if ( $("input[name=productDay]").val() == '' ||
					$("input[name=productDay]").val().length == 0 )
			{
				showErrorMessage($("input[name=productDay]"));
			}
			else
			{

				deleteErrorMessageIfExist($("input[name=productDay]"));
			}
		} else {
			deleteErrorMessageIfExist($("input[name=productDay]"));
		}

		var originalProductDay = $("input[name=productDay]").val();
		var dbDatelength = $("input[name=productDay]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if( dbDatelength != '' || dbDatelength != null )
		$("input[name=productDay]").val(dbDatelength);

		var originalPsbtStartDy = $("input[name=psbtStartDyVal]").val();
		var dbDatelength1 = $("input[name=psbtStartDyVal]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if( dbDatelength1 != '' || dbDatelength1 != null )
		$("input[name=psbtStartDyVal]").val(dbDatelength1);

		var originalPsbtEndDy = $("input[name=psbtEndDyVal]").val();
		var dbDatelength2 = $("input[name=psbtEndDyVal]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if( dbDatelength2 != '' || dbDatelength2 != null )
		$("input[name=psbtEndDyVal]").val(dbDatelength2);

		var originalPickIdctDy = $("input[name=pickIdctDy]").val();
		var dbDatelength3 = $("input[name=pickIdctDy]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if( dbDatelength3 != '' || dbDatelength3 != null )
		$("input[name=pickIdctDy]").val(dbDatelength3);

		var errorLength = $("div[name=error_msg]").length;

		if(!validationResult ||
				errorLength > 0	) {

			$("input[name=productDay]").val(originalProductDay);
			$("input[name=psbtStartDy]").val(originalPsbtStartDy);
			$("input[name=psbtEndDy]").val(originalPsbtEndDy);
			$("input[name=pickIdctDy]").val(originalPickIdctDy);

			alert("필수 속성값들을 모두 입력해주시기 바랍니다.");
			return false;
		} else {
			return true;
		}
	}

	// 상품 등록 폼을 전송하기 전에 각 필드의 기본  값 설정.
	function setupFormFieldDefaultValue() {
		var tempCode = $("#temperatureDivnCode").val();
		$("#tmpDivnCode").val(tempCode);

		var normalProductSalePrice = $("input[name=normalProductSalePrice]").val();
		$("input[name=sellPrice]").val(normalProductSalePrice);


		var displayTotalQuantity = $("input[name=displayTotalQuantity]").val();
		var displayBaseQuantity = $("input[name=displayBaseQuantity]").val();


		if( displayTotalQuantity == '')
		$("input[name=displayTotalQuantity]").val(0);

		if( displayBaseQuantity == '')
		$("input[name=displayBaseQuantity]").val(0);



	    //var wec = document.Wec;
		$("input[name=productDescription]").val(CrossEditor.GetValue());


	}

	//상품등록 폼 전송
	function submitProductInfo() {
		 $("div#content_wrap").block({ message: null ,  showOverlay: false });
		var index = $("#itemRow").val();
		
		 //필드 값 검증.
		if( validateNewProductInfo() ) {

			//유효성 체크 통과
			 setupFormFieldDefaultValue();

			if($("#entpCode").val() == ""){
				alert('업체선택은 필수입니다.');
				$("#entpCode").focus();
				return;
			}

			//체험형 Y 일시 추천평 입력 체크
			if($(":radio[name='exprProdYn']:checked").val() == "1" && $("#sellerRecomm").val().trim() == ""){
				alert('판매자 추천평을 작성해주세요.');
				$("#sellerRecomm").focus();
				return;
			}
			
			if (!calByteProd (newProduct.productName, 80, '상품명', false) ) return;		// 상품명 byte체크

			if (!prodAddValidationCheck ()) return;

			if (!prodCertValidationCheck ()) return;

			/////////////////카테고리 validation//////////////
			if(mySheet.RowCount() == 0){
				alert('전시카테고리를 추가하세요.');
				return;
			}

			/////////////////임직원몰 전용상품 validation//////////////
			if($("#psbtChkFlag").attr("checked") == true){
				if($.trim($("#staffDcAmt").val()) == ""){
					alert('임직원용 상품 금액을 입력 하세요.');
					$("#staffDcAmt").focus();
					return;
				}
			}

			/////////////////골라담기, 예약상품 validation//////////////
			var onlineProdTypeCdVal = $(':radio[name="onlineProdTypeCd"]:checked').val();
			if(onlineProdTypeCdVal == "04"){ //골라담기
				if($.trim($("#setQty").val()) == "" || $.trim($("#optnLoadContent").val()) == ""){
					alert('세트수량 항목을 필히 입력해주세요.');
					return;
				}
			}else if(onlineProdTypeCdVal == "07"){ //예약상품

				if($("#psbtStartDyVal").val() == "" || $("#psbtEndDyVal").val() == ""){
					alert("예약 주문 가능일을 입력해주세요.");
					return;
				}

				if($("#pickIdctDy").val() == ""){
					alert("예약상품 출고지시일을 입력해주세요.");
					return;
				}

				if($("#psbtStartDyVal").val()+$("#psbtStartTime").val() > $("#psbtEndDyVal").val()+$("#psbtEndTime").val()){
					alert("예약 주문 가능시작일이 종료일보다 큽니다.");
					return;
				}

				if($("#psbtEndDyVal").val() > $("#pickIdctDy").val()){
					alert("예약상품 출고지시일은 예약 주문 가능일 보다 커야합니다.");
				}

				$("#psbtStartDy").val($("#psbtStartDyVal").val()+$("#psbtStartTime").val());
				$("#psbtEndDy").val($("#psbtEndDyVal").val()+$("#psbtEndTime").val());

			}else if(onlineProdTypeCdVal == "09"){ //구매대행상품
				if($("#deliDueDate").val() == ""){
					$("input[name='deliDueDate']").val("주문 후 5~10일 정도 소요");
				}
			}
			/////////////////상품설명 validation//////////////
			var newProdDescBodyVal	=	CrossEditor.GetBodyValue();
			var newProdDescTxtVal	=	CrossEditor.GetTextValue();
			newProdDescTxtVal		=	newProdDescTxtVal.replace(/^\s*/,'').replace(/\s*$/, '');

			 if(newProdDescTxtVal == "" && newProdDescBodyVal.indexOf('<IMG')=='-1' && newProdDescBodyVal.indexOf('<img')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<IFRAME')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<iframe')=='-1'  && newProdDescBodyVal.toUpperCase().indexOf('<EMBED')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<embed')=='-1'){
				alert('상품설명을 필히 입력해주세요. 온라인판매를 위해 정확히 넣어주세요!');
				return;
			}
			///////////////////////////////////////////////
			
			// 단품정보 등록 확인 추가
			if(index > 0){
				/*
				if($("#optnDesc"+index).val() == "" || $("#rservStkQty"+index).val() == ""){
	  	    		alert("단품 정보를 정확하게 입력하세요");
	  	    		return;
	  	    	}

				if($(":radio[name='prodPrcMgrYn']:checked").val() == "1"){
					if($("#optnAmt"+index).val() == ""){
						alert("단품 가격 정보를 정확하게 입력하세요");
		  	    		return;
					}

					if(Number($("#optnAmt"+index).val()) == 0){
						alert("단품 가격을 0원 이상 입력하세요");
		  	    		return;
					}
				}

				if($("#optnDesc"+index).val() != "" && $("#optnDesc"+index).val().indexOf(';') > -1){
	  	    		alert(" ';' 을 사용할수 없습니다.");
	  	    		return;
	  	    	}
				*/
				
				for(var i=1; i<=index; i++) {
					if($("#optnDesc"+i).val() == "" || $("#rservStkQty"+i).val() == ""){
						alert(i + "행 단품 정보를 정확하게 입력하세요");
						return;
					}

					if($("#optnDesc"+i).val() != "" && $("#optnDesc"+i).val().indexOf(';') > -1){
			  	    	alert(i + "행 ';' 을 사용할수 없습니다.");
			  	    	return;
			  	    }
					
					if($("#optnDesc"+i).val().length >= 200){
			  	    	alert(i + "행 옵션설명 200자이상은 입력할 수 없습니다.");
			  	    	return;
					}
					
					if($("#rservStkQty"+i).val().length >= 7){
			  	    	alert(i + "행 재고수량 7자릿수 이상은 입력할 수 없습니다.");
			  	    	return;
					}
					
					if($(":radio[name='prodPrcMgrYn']:checked").val() == "1"){
						if($("#optnAmt"+i).val() == ""){
							alert(i + "행 단품 가격 정보를 정확하게 입력하세요");
							return;
						}
						
						if(Number($("#optnAmt"+i).val()) == 0){
							alert(i + "행 단품 가격을 0원 이상 입력하세요");
				  	    	return;
						}
						
						if($("#optnAmt"+i).val().length >= 10){
				  	    	alert(i + "행 단품 가격 10자릿수 이상은 입력할 수 없습니다.");
				  	    	return;
						}
					}
				 }
			}else{
				if($(":radio[name='prodPrcMgrYn']:checked").val() == "1"){
					alert("옵션별 별도 가격 선택시 단품 정보를 먼저 입력 하세요.");
					return;
				}
			}
			
			//20180904 - 상품키워드 입력 기능 추가
			if($.trim($("#searchKywrd1").val()) == "") {
				alert("상품키워드 1행 검색어는 필수 입력 항목입니다.");
				return;
			}
			
			if(reCount($("#searchKywrd1").val()) > 39) {
				alert("상품키워드 1행 검색어는 39바이트를 초과할 수 없습니다.");
				return;
			}
			
  			var kId = Array();
  	    	$("#keywordSubTable tr td:nth-child(2) input").each(function(tIndex) {
  	    		kId[tIndex] = $(this).attr("id");
  			});
  	    	
  	    	if(kId.length > 0) {	
  	  			for(var i=0; i<kId.length; i++) {
  	  	  	    	if($.trim($("#"+ kId[i]).val()) == "") {
  	  	  	    		alert("상품키워드 " + (i+1) + "행 검색어를 정확하게 입력하세요.");
  	  	  	    		return;
  	  	  	    	}
  	  	  	    	
					if(reCount($.trim($("#"+ kId[i]).val())) > 39) {
						alert("상품키워드 " + (i+1) + "행 검색어는 39바이트를 초과할 수 없습니다.");
						return;
					}
  	  			}
  	    	}
  	    	//20180904 - 상품키워드 입력 기능 추가
			
			$("#taxatDivCode").attr("disabled",false);

			var chkVal = "";

			for(var i=1; i<mySheet.RowCount()+1; i++){
					chkVal += ","+mySheet.GetCellValue(i,"CATEGORY_ID");
			}

			chkVal = chkVal.substring(1,chkVal.length);

			$("#onlineDisplayCategoryCode").val(chkVal);

			loadingMaskFixPos();

			var actUrl = "<c:url value='/edi/product/PEDMPRO0002.do'/>";

			if ('<c:out value="${newProdDetailInfo.pgmId}" />' != "") {
				actUrl = "<c:url value='/edi/product/PEDMPRO000302.do'/>";
			}

			$("#newProduct").attr("action",actUrl);
			$("#newProduct").attr("method", "post").submit();
		}

		setTimeout(function() {
		 $("div#content_wrap").unblock();
		 hideLoadingMask();
		}, 500);
	}



    // 카테고리 입력 정보 삭제
    function doDdeleteCategory()
    {
        var form = document.newProduct;
        form.asCategoryId.value = "";
        form.asCategoryNm.value = "";
    }

    // 전자상거래 데이터 셋팅
	function commerceChange(val){
		//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
		setupLcdDivnCodeProdAdd(val,'2');
	}

	// KC인증마크 데이터 셋팅
	function certChange(val){
		//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
		setupLcdDivnCodeCertAdd(val,'2');
	}

	// ImageSplitter Popup에서 데이터가 전해진다.
    function addSplitImage(activeSquareMimeValue)
    {
		var wec = document.Wec;
		var bodyTag = wec.BodyValue;
		//wec.MIMEValue = activeSquareMimeValue;
		//wec.BodyValue = bodyTag + decodeURI(wec.BodyValue); // base64에서 한글 입력하면 깨진다.
    }


	 // 팝업으로 ImageSplitter를 띄운다.
    function doImageSplitterView()
    {
		window.open("/edit/splitter/ImageSplitter.html", "ImageSplitter", "width="+screen.width+", height="+screen.height+", toolbar=no, menubar=no, resizeable=true, fullscreen=true");
    }

    function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임
    	$("#entpCode").val(strVendorId);

    	checkBlackListVendor1(strVendorId);
    }


	/* 상품정보 복사 */
	function copyProductInfo() {
		if (!confirm("현재 상품의 정보를 복사하시겠습니까?")) {
			return;
		}

		var index = $("#itemRow").val();
		$("div#content_wrap").block({ message: null ,  showOverlay: false  });

		//-----필드값 검증
		if (validateNewProductInfo()) {

			//-----전자상거래 validation check
			if(! prodAddValidationCheck()) return;

			//-----KC 인증마크 validation check
			if(! prodCertValidationCheck()) return;

			/////////////////상품설명 validation//////////////
			var newProdDescBodyVal	=	CrossEditor.GetBodyValue();
			var newProdDescTxtVal	=	CrossEditor.GetTextValue();
			newProdDescTxtVal		=	newProdDescTxtVal.replace(/^\s*/,'').replace(/\s*$/, '');

			 if(newProdDescTxtVal == "" && newProdDescBodyVal.indexOf('<IMG')=='-1' && newProdDescBodyVal.indexOf('<img')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<IFRAME')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<iframe')=='-1'  && newProdDescBodyVal.toUpperCase().indexOf('<EMBED')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<embed')=='-1'){
				alert('상품설명을 필히 입력해주세요. 온라인판매를 위해 정확히 넣어주세요!');
				return;
			}
			///////////////////////////////////////////////
			
			//단품정보 등록 확인 추가
			if(index > 0){
				
				/*
				if($("#optnDesc"+index).val() != "" && $("#optnDesc"+index).val().indexOf(';') > -1){
	  	    		alert(" ';' 을 사용할수 없습니다.");
	  	    		return;
	  	    	}

				if("${newProdDetailInfo.onoffDivnCd}" == "1" && "${newProdDetailInfo.prodDivnCd}" == "5"){
					if($("#optnDesc"+index).val() == "" || $("#rservStkQty"+index).val() == ""){
						$("#optnDesc"+index).val(" ");
						$("#rservStkQty"+index).val("0");
					}
				}else{
					if($("#optnDesc"+index).val() == "" || $("#rservStkQty"+index).val() == ""){
		  	    		alert("단품 정보를 정확하게 입력하세요");
		  	    		return;
		  	    	}
				}
				*/
				
				for(var i=1; i<=index; i++) {
					if($("#optnDesc"+i).val() != "" && $("#optnDesc"+i).val().indexOf(';') > -1){
						alert(" ';' 을 사용할수 없습니다.");
						return;
					}
					
					if("${newProdDetailInfo.onoffDivnCd}" == "1" && "${newProdDetailInfo.prodDivnCd}" == "5"){
						if($("#optnDesc"+i).val() == "" || $("#rservStkQty"+i).val() == ""){
							$("#optnDesc"+i).val(" ");
							$("#rservStkQty"+i).val("0");
						}
						
						if($(":radio[name='prodPrcMgrYn']:checked").val() == "1"){
							if($("#optnAmt"+i).val() == ""){
								$("#optnAmt"+i).val("0");
							}
						}
					} else {
						if($("#optnDesc"+i).val() == "" || $("#rservStkQty"+i).val() == ""){
							alert("단품 정보를 정확하게 입력하세요");
							return;
						}
						
						if($("#optnDesc"+i).val().length >= 200){
				  	    	alert(i + "행 옵션설명 200자이상은 입력할 수 없습니다.");
				  	    	return;
						}
						
						if($("#rservStkQty"+i).val().length >= 7){
				  	    	alert(i + "행 재고수량 7자릿수 이상은 입력할 수 없습니다.");
				  	    	return;
						}						
						
						if($(":radio[name='prodPrcMgrYn']:checked").val() == "1"){
							if($("#optnAmt"+i).val() == ""){
								alert("단품 가격 정보를 정확하게 입력하세요");
								return;
							}
							
							if(Number($("#optnAmt"+i).val()) == 0){
								alert("단품 가격을 0원 이상 입력하세요");
								return;
							}
							
							if($("#optnAmt"+i).val().length >= 10){
					  	    	alert(i + "행 단품 가격 10자릿수 이상은 입력할 수 없습니다.");
					  	    	return;
							}							
						}
					}
				}				
				
			}
			
			//20180904 - 상품키워드 입력 기능 추가
			if($.trim($("#searchKywrd1").val()) == "") {
				alert("상품키워드 1행 검색어는 필수 입력 항목입니다.");
				return;
			}
			
			if(reCount($("#searchKywrd1").val()) > 39) {
				alert("상품키워드 1행 검색어는 39바이트를 초과할 수 없습니다.");
				return;
			}
			
  			var kId = Array();
  	    	$("#keywordSubTable tr td:nth-child(2) input").each(function(tIndex) {
  	    		kId[tIndex] = $(this).attr("id");
  			});
  	    	
  	    	if(kId.length > 0) {	
  	  			for(var i=0; i<kId.length; i++) {
  	  	  	    	if($.trim($("#"+ kId[i]).val()) == "") {
  	  	  	    		alert("상품키워드 " + (i+1) + "행 검색어를 정확하게 입력하세요.");
  	  	  	    		return;
  	  	  	    	}
  	  	  	    	
					if(reCount($.trim($("#"+ kId[i]).val())) > 39) {
						alert("상품키워드 " + (i+1) + "행 검색어는 39바이트를 초과할 수 없습니다.");
						return;
					}
  	  			}
  	    	}
  	    	//20180904 - 상품키워드 입력 기능 추가

			loadingMaskFixPos();
			//유효성 체크 통과
			setupFormFieldDefaultValue();

			//document.newProduct.action = "<c:url value='/edi/product/PEDMPRO0333.do'/>";
			document.newProduct.action = "<c:url value='/edi/product/NEDMPRO0333.do'/>";
			document.newProduct.submit();
		}
		setTimeout(function() {
			$("div#content_wrap").unblock();
			hideLoadingMask();
		}, 500);
	}

	/* 전시카테고리 추가*/
	function addCategory(){
		if($("#l3Cd").val() == ""){
			alert("분류코드를 선택 하세요.");
			$("#l3Cd").focus();
			return;
		}

		var rowCount = mySheet.RowCount();

		if(rowCount < 3){
			 var targetUrl = '${ctx}/edi/product/onlineDisplayCategory.do?closeFlag=1&catCd='+$("#l3Cd").val();//01:상품
		     Common.centerPopupWindow(targetUrl, 'epcCategoryPopup', {width : 460, height : 455});
		}else{
			alert("최대 3개 까지 지정 가능합니다.");
		}
	}

    // 카테고리 검색창으로 부터 받은 카테고리 정보 입력
    function setCategoryInto(asCategoryId, asCategoryNm, displayFlag, fullCategoryNm) // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
    {
    	var rowCount = mySheet.RowCount();

    	if(rowCount < 3){
    		for(var i=1; i<mySheet.RowCount()+1; i++){
    	    	if(mySheet.GetCellValue(i,"CATEGORY_ID") == asCategoryId){
    	    		alert("이미 추가된 카테고리 입니다.");
    				return;
    	    	}
    	    }
		}else{
			alert("최대 3개 까지 지정 가능합니다.");
			return;
		}

	    var rowIdx = mySheet.DataInsert(0);

    	if(displayFlag == "Y"){
    		displayFlag = "전시";
    	}else{
    		displayFlag = "미전시";
    	}

    	mySheet.SetCellValue(rowIdx, "FULL_CATEGORY_NM", fullCategoryNm);
    	mySheet.SetCellValue(rowIdx, "CATEGORY_ID", asCategoryId);
    	mySheet.SetCellValue(rowIdx, "DISP_YN", displayFlag);

        selectL4ListForOnline(asCategoryId);
    }

	/* 전시카테고리 삭제*/
	function delCategory(){
		var chkRow = "";

		for(var i=1; i<mySheet.RowCount()+1; i++){
			if(mySheet.GetCellValue(i,"SELECTED") == 1){
				chkRow += "|"+i;
			}
		}

		chkRow = chkRow.substring(1,chkRow.length);

		mySheet.RowDelete(chkRow);
	}

	//상품유형 제어
	function onlineProdTypeCdChk(onlineProdTypeCd){
		if(onlineProdTypeCd == "02" || onlineProdTypeCd == "03"){ //주문제작

			$("#prodTypeDiv1").show();
			$("#prodTypeDiv2").hide();
			$("#prodTypeDiv3").hide();
			$("#selTime").hide();

			$("#setQty").val("");
			$("#optnLoadContent").val("");
			$("#psbtStartDy").val("");
			$("#psbtEndDy").val("");
			$("#psbtStartDyVal").val("");
			$("#psbtEndDyVal").val("");
			$("#psbtStartTime").val("00");
			$("#psbtEndTime").val("00");
			$("#pickIdctDy").val("");
		}else if(onlineProdTypeCd == "04"){ //골라담기

			$("#prodTypeDiv2").show();
			$("#prodTypeDiv1").hide();
			$("#prodTypeDiv3").hide();
			$("#selTime").hide();

			$("#psbtStartDy").val("");
			$("#psbtEndDy").val("");
			$("#psbtStartDyVal").val("");
			$("#psbtEndDyVal").val("");
			$("#psbtStartTime").val("00");
			$("#psbtEndTime").val("00");
			$("#pickIdctDy").val("");
		}else if(onlineProdTypeCd == "07"){ //예약상품
			$("#selTime").show();
			$("#prodTypeDiv1").hide();
			$("#prodTypeDiv2").hide();
			$("#prodTypeDiv3").hide();

			$("#setQty").val("");
			$("#optnLoadContent").val("");

		}else if(onlineProdTypeCd == "09"){ //구매대행상품

			$("#prodTypeDiv3").show();
			$("#prodTypeDiv1").hide();
			$("#prodTypeDiv2").hide();
			$("#selTime").hide();

			$("#setQty").val("");
			$("#optnLoadContent").val("");
			$("#psbtStartDy").val("");
			$("#psbtEndDy").val("");
			$("#psbtStartDyVal").val("");
			$("#psbtEndDyVal").val("");
			$("#psbtStartTime").val("00");
			$("#psbtEndTime").val("00");
			$("#pickIdctDy").val("");
		}else{

			$("#prodTypeDiv1").hide();
			$("#prodTypeDiv2").hide();
			$("#prodTypeDiv3").hide();
			$("#selTime").hide();

			$("#setQty").val("");
			$("#optnLoadContent").val("");
			$("#psbtStartDy").val("");
			$("#psbtEndDy").val("");
			$("#psbtStartDyVal").val("");
			$("#psbtEndDyVal").val("");
			$("#psbtStartTime").val("00");
			$("#psbtEndTime").val("00");
			$("#pickIdctDy").val("");
		}
	}

	//단품정보 제어
	function prodPrcMgrYnChk(prodPrcMgrYn){
		var rows = $("#itemSubTable tr");
  	    var index = rows.length;

		if(prodPrcMgrYn == "0"){
			$("#optnAmtTH").hide();

			for(var i=1; i<index; i++){
				$("#optnAmtTD"+i).hide();
			}

			$("input[name='optnAmt']").val('');
		}else{
			$("#optnAmtTH").show();

			for(var i=1; i<index; i++){
				$("#optnAmtTD"+i).show();
			}
		}
	}

	//상세보기 카테고리 List
	function selectCategoryList(){
		var url = '<c:url value="/edi/product/NEDMPRO0030Category.do"/>';
		var param = new Object();
		param.onlineDisplayCategoryCode 	= $("#onlineDisplayCategoryCode").val();

		loadIBSheetData(mySheet, url, null, null, param);
	}

	function fnTableControll(){
		var val = $(":radio[name='prodPrcMgrYn']:checked").val();

		prodPrcMgrYnChk(val);
	}

	//전상법 템플릿 selectBox 조회
	function selectNorProdTempSel(val){
		if(val == ""){
			$("select[name=productAddSelectTemp]").html('<option value="">선택</option>');

			return;
		}

		var param = new Object();
		var targetUrl = '<c:url value="/edi/product/selectNorProdTempSel.do"/>';
		param.infoGrpCd = val;
		param.vendorId = $("#entpCode").val();

		var optionText = '<option value="">선택</option>';

		$.ajax({
			type: 'POST',
			url: targetUrl,
			data: param,
			success: function(data) {
				var list = data.resultList;

				for(var i=0; i<list.length; i++){
					optionText += '<option value="'+list[i].NOR_PROD_SEQ+'">'+list[i].TITLE+'</option>';
				}

				$("select[name=productAddSelectTemp]").html(optionText);
			}
		});
	}

	function doCategoryReset(){
    	mySheet.RemoveAll();
    }

	function trdTypFgSel(){
		$.getJSON("${ctx}/edi/product/trdTypFgSel.do",null, trdTypFgSelRst);
	}

	function trdTypFgSelRst(data){
		for(var i=0; i<data.length; i++){
			for(var j=0; j<$("select[name=entpCode] option").size(); j++){
				if(data[i].VENDOR_ID == $("select[name=entpCode] option:eq("+j+")").val()){
					var tdrTypFgNm = data[i].TRD_TYP_FG_NM;

					if(tdrTypFgNm == null || tdrTypFgNm == "null" || tdrTypFgNm == ""){
						tdrTypFgNm = ""
					}else{
						tdrTypFgNm = "("+data[i].TRD_TYP_FG_NM+")";
					}

					$("select[name=entpCode] option:eq("+j+")").replaceWith("<option value='"+data[i].VENDOR_ID+"'>"+data[i].VENDOR_ID+tdrTypFgNm+"</option>");
				}
			}
		}

		$("select[name=entpCode]").val(vndId);
	}

	/* 한글입력 방지 */
    function fn_press_han(obj){
        //좌우 방향키, 백스페이스, 딜리트, 탭키에 대한 예외
        if(event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39|| event.keyCode == 46 ) return;

        obj.value = obj.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
    }
	

	// 체험형 상품 여부 라디오 버튼 이벤트 제어
    $(function(){
		$("input[name='exprProdYn']:radio").click(function(){
			exprRadioCheck();
		})
	}); 
    
 	// 판매자 추천평 바이트수 체크
	var maxMessage
	function checkByte() {
		var limitByte = 100; //최대 바이트
        var message = $("#sellerRecomm").val();
		var totalByte = 0;
		
		for(var i =0; i < message.length; i++) {
			var currentByte = message.charCodeAt(i);
			if(currentByte > 128) totalByte += 2;
			else totalByte++;
       	}
		
		if(totalByte == limitByte){
			maxMessage = message;
		}
		
		if(totalByte <= limitByte){
			$('#messagebyte').html(totalByte);
		} else if(totalByte > limitByte){
		    alert( limitByte+"바이트까지 전송가능합니다.");
		    $('#messagebyte').html(limitByte);
			frm.sellerRecomm.value = maxMessage;
        } 
	}
 	
	/**********************************************************
	 * 단품정보 일괄등록 양식 다운
	 **********************************************************/
	function exeExcelFile() {
		var form = document.excelForm;
		
		var optVal = $(":input:radio[name=prodPrcMgrYn]:checked").val();
		var fileName = "단품정보_일괄등록";
		
		if(optVal == "0") {
			fileName = "단품정보(동일가격)_일괄등록";
		} else {
			fileName = "단품정보(별도가격)_일괄등록";
		}
				
		if (!confirm('엑셀 양식을 다운로드 하시겠습니까?')) {
			return;
		}
		
		$("#optionVal").val(optVal);
		$("#fileName").val(encodeURIComponent(fileName));
		
		form.target = "frameForExcel";
		form.action = '<c:url value="/edi/product/itemExcelDown.do"/>';
		form.submit();
	}
	
	/**********************************************************
	 * 단품정보 일괄등록
	 **********************************************************/	
	function exeExcelFileUpload() {
		var optVal = $(":input:radio[name=prodPrcMgrYn]:checked").val();
		var fName = $('#createFile').val();
		
		var fileLen = fName.length;
		
		if (fileLen < 3) {
			alert('업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.');
			return;
		}
		
		var fileExt = fName.slice(fName.lastIndexOf(".")+1).toLowerCase();
		
		if(fileExt != "xls"){
			alert("업로드는 xls 확장자 엑셀파일만 가능 합니다.");
			$('#createFile').val("")
			return;
		}
		
		var formData = new FormData();
		
		formData.append("file", $("input[name=createFile]")[0].files[0]);
		formData.append("optionVal", optVal);
		
		var totCnt = 0;
		
		if(confirm("엑셀파일을 업로드 하시겠습니까?")) {
			
			$.ajax({
				url:'<c:url value="/edi/product/itemExcelUploadNew.json"/>',
				contentType : false,
				type:"POST",
				dataType: "JSON",
				data:formData,
				cache: false,
				async:false,
				processData: false,
				beforeSend:function(){
					loadingMask();
				},
				success:function(result) {
					var newRow = "";
					
					if(result.length > 999) {
						alert("1000개 이상 등록할 수 없습니다.");
						return;
					} else if(result.length > 0) {
						
						totCnt = result.length;
						
						var resetHtml = '<input type="radio" name="prodPrcMgrYn" value="0" checked="checked"/>옵션 별 동일가격';
						resetHtml += '<input type="radio" name="prodPrcMgrYn" value="1"/>옵션 별 별도가격';
						resetHtml += '<div style="float:right;">';
						resetHtml += '    <a href="#" class="btn" onclick="exeExcelFile();"><span>양식다운</span></a>';
						resetHtml += '    <input type="file" name="createFile" id="createFile" style="width:200px;" value="파일이름" />';
						resetHtml += '    <a href="#" class="btn" onclick="exeExcelFileUpload();"><span>단품정보 일괄등록</span></a>';
						resetHtml += '</div>';
						resetHtml += '<table id="itemSubTable" style="width:700px"><tr><th style="width:70px">단품코드</th><th style="width:200px">옵션설명</th><th style="width:60px">재고여부</th><th style="width:100px">재고수량</th><th id="optnAmtTH" style="width:100px">가격</th><th></th></tr></table>';
						
				  		$("#itemTd").html(resetHtml);
				  		
				  		var itemCd = "00";
				  		
				  		var regNumber = /^[0-9]*$/;
				  		
						for(var index=0; index<result.length; index++) {
							
				  	    	if(result[index].OPTN_DESC == "" || result[index].STK_MGR_YN == "" || result[index].RSERV_STK_QTY == "") {
				  	    		alert("엑셀 양식 " + (index+3) + "행 단품정보를 정확하게 입력하세요");
				  	    		$("#itemTd").html(resetHtml);
				  	    		return;
				  	    	}
				  	    	
				  	    	if(result[index].OPTN_DESC != "" && result[index].OPTN_DESC.indexOf(';') > -1) {
				  	    		alert("엑셀 양식 " + (index+3) + "행 옵션설명은 ';' 을 사용할수 없습니다.");
				  	    		$("#itemTd").html(resetHtml);
				  	    		return;
				  	    	}
				  	    	
				  	    	if((result[index].STK_MGR_YN).toUpperCase() != "Y" && (result[index].STK_MGR_YN).toUpperCase() != "N") {
				  	    		alert("엑셀 양식 " + (index+3) + "행 재고여부는 Y 그리고 N 만 입력가능합니다.");
				  	    		$("#itemTd").html(resetHtml);
				  	    		return;
				  	    	}
				  	    	
				  	    	if(regNumber.test(result[index].RSERV_STK_QTY) == false || result[index].RSERV_STK_QTY <= 0) {
				  	    		alert("엑셀 양식 " + (index+3) + "행 재고수량은 숫자 및 0원 이상만 입력가능합니다.");
				  	    		$("#itemTd").html(resetHtml);
				  	    		return;
				  	    	}
				  	    	
				  	    	if(optVal == "1") {
					  	    	if(result[index].OPTN_AMT == "") {
					  	    		alert("엑셀 양식 " + (index+3) + "행 단품 가격 정보를 정확하게 입력하세요");
					  	    		$("#itemTd").html(resetHtml);
					  	    		return;
					  	    	} else if(regNumber.test(result[index].OPTN_AMT) == false || result[index].OPTN_AMT <= 0) {
					  	    		alert("엑셀 양식 " + (index+3) + "행 단품 가격은 숫자 및 0원 이상만 입력가능합니다.");
					  	    		$("#itemTd").html(resetHtml);
					  	    		return;
					  	    	}
				  	    	}				  	    	
				  	    	
				  	    	if(index < 9) {
				  	    		itemCd = "00"+(index+1);
				  	    	} else if(index < 99) {
				  	    		itemCd = "0"+(index+1);
				  	    	} else if(index < 999) {
				  	    		itemCd = index+1;
				  	    	}
				  	    	
				  	  		var newRow = "<tr id=row"+(index+1)+"><td style='text-align:center'>"+itemCd+"<input type='hidden' name='itemCd' id='itemCd"+(index+1)+"' value='"+itemCd+"'/></td>";
				  	  		newRow += "<td><input type='text' style='width:98%' name='optnDesc' id='optnDesc"+(index+1)+"' value='"+result[index].OPTN_DESC+"'/></td>";
				  	  		
				  	  		if((result[index].STK_MGR_YN).toUpperCase() == "N") {
				  	  			newRow += "<td><select id='stkMgrYn"+(index+1)+"' name='stkMgrYn' style='width:98%;'><option value='N' selected>N</option><option value='Y'>Y</option></select></td>";	
				  	  		} else {
				  	  			newRow += "<td><select id='stkMgrYn"+(index+1)+"' name='stkMgrYn' style='width:98%;'><option value='N'>N</option><option value='Y' selected>Y</option></select></td>";
				  	  		}
				  	  		
				  	  		newRow += "<td><input type='text' style='text-align:right; width:98%;' id='rservStkQty"+(index+1)+"' name='rservStkQty' onkeydown='onlyNumber(event)' value='"+result[index].RSERV_STK_QTY+"'/></td>";
				  	  		
				  	  		if(optVal == "0") {
				  	  			newRow += "<td id='optnAmtTD"+(index+1)+"'><input type='text' style='text-align:right; width:98%;' id='optnAmt"+(index+1)+"' name='optnAmt' onkeydown='onlyNumber(event)'/></td>";
				  	  		} else {
				  	  			if(result[index].OPTN_AMT == null || result[index].OPTN_AMT == undefined) {
				  	  				newRow += "<td id='optnAmtTD"+(index+1)+"'><input type='text' style='text-align:right; width:98%;' id='optnAmt"+(index+1)+"' name='optnAmt' onkeydown='onlyNumber(event)'/></td>";	
				  	  			} else {
				  	  				newRow += "<td id='optnAmtTD"+(index+1)+"'><input type='text' style='text-align:right; width:98%;' id='optnAmt"+(index+1)+"' name='optnAmt' onkeydown='onlyNumber(event)' value='"+result[index].OPTN_AMT+"'/></td>";	
				  	  			}	
				  	  		}
				  	  		
				  	  		newRow += "<td><a href='javascript:fnNewItemDelete("+(index+1)+")' id='deleteNewItem"+(index+1)+"' class='btn' ><span>삭제</span></a></td>";
				  	  		newRow += "</tr>";
				  	  		
				  	  		$("#itemSubTable").last().append(newRow);
				  	  		
				  	  		if(optVal == "0") {
				  	  			$("#optnAmtTD"+(index+1)).hide();
				  	  		} else {
				  	  			$("#optnAmtTD"+(index+1)).show();
				  	  		}
						}
						
			  	  		if(result.length > 0) {
			  				for(var i = 0; i < (result.length-1); i++) {
			  					$("#deleteNewItem"+(i+1)).attr("style", "display:none");
			  				}
			  	  		}				  		
					} else {
						alert("등록할 단품정보가 없습니다.");
						return;
					}
				},
				complete:function() {
					hideLoadingMask();
					
					if(optVal == "0") {
						$("#optnAmtTH").hide();
						$("input:radio[name=prodPrcMgrYn]:radio[value='0']").prop("checked", true);
					}else{
						$("#optnAmtTH").show();
						$("input:radio[name=prodPrcMgrYn]:radio[value='1']").prop("checked", true);
					}
					
					if(totCnt > 0) {
						$("#itemRow").val(totCnt);
					} else {
						$("#itemRow").val(0);
					}
				},
				error:function(jqXHR, textStatus, errorThrown) {
					alert("실패 : " + textStatus + " : " + errorThrown);
				}
			});
		}
		
	}	
	
	//20180904 - 상품키워드 입력 기능 추가
	function fnKeywordAddNew(type) {		
		if($.trim($("#keywordTd").html()) == '') {
  			$("#keywordTd").html("<table id='keywordSubTable' style='width:700px'><tr><th style='text-align:center; width:50px'>순번</th><th style='text-align:center; width:200px'>검색어</th><th style='text-align:center; width:50px'></th></tr></table>");
  		}
  		
		var rows = $("#keywordSubTable tr"); 
  	    var index = rows.length;
  	    
		var kIndex = 0;
		var keywordId = Array();
		
  	    if(index > 1) {
  	    	if(index >= 1000) {
  	    		alert("1000개 이상 등록할 수 없습니다.");
  	    		return;
  	    	}  	    	
  	    	
  	    	$("#keywordSubTable tr td:nth-child(2) input").each(function(tIndex) {
  				keywordId[tIndex] = $(this).attr("id");
  				kIndex = $(this).attr("id").substring(11);
  			});
  			
  			for(var i=0; i<keywordId.length; i++) {
  	  	    	if($.trim($("#"+ keywordId[i]).val()) == "") {
  	  	    		alert((i+1) + "행 상품키워드를 정확하게 입력하세요");
  	  	    		return;
  	  	    	}
  			}
  			
  			kIndex++;
  	    }
  	    
  		var newRow = "<tr id=kwRow"+kIndex+">";
  		newRow += "<td style='text-align:center'>◈<input type='hidden' name='seq' id='seq"+kIndex+"' value='NEW'/></td>";
  		newRow += "<td><input type='text' style='text-align:left; width:98%;' id='searchKywrd"+kIndex+"' name='searchKywrd' maxlength='39' onblur='limitPushString("+kIndex+");' /></td>";
  		newRow += "<td><a href='javascript:fnKeywordDeleteNew("+kIndex+")' id='deleteNewKeyword"+kIndex+"' class='btn' ><span>삭제</span></a></td>";
  		newRow += "</tr>";
  		
  		$("#keywordSubTable").last().append(newRow); 
  	}
	
	//상품키워드 (신규 추가 행 삭제)
	function fnKeywordDeleteNew(idx) {
		$("#kwRow" + idx).remove();
	}
	
	//상품키워드 (행 삭제)
	function fnKeywordDelete(idx, seq) {
		if(!confirm("["+idx+"]행 상품키워드를 삭제하시겠습니까?")) {
			return;
		}
		
		var paramInfo = {};
		paramInfo["seq"]	=	seq;
		paramInfo["pgmId"]	=	"<c:out value='${newProdDetailInfo.pgmId}'/>";
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/delOnlineKeywordInfo.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				if(data.msg == "SUCCESS") {
					fnKeywordDeleteNew(idx);
				} else {
					alert("<spring:message code='msg.common.fail.delete'/>")
				}
			}
		});
	}
	
	//상품키워드 (39바이트 초과 확인)
	function limitPushString(idx) {
		byteCheck($("#searchKywrd"+idx).val(), idx);
	}
	
	function byteCheck(msg, idx) {
		var text = msg;
		var msglen = 0;
		
		msglen = reCount(text);
		
		if(msglen > 39) {
			rem = msglen - 39;
			alert('입력하신 문장의 총길이는 ' + msglen + '입니다.\n초과되는 ' + rem + '바이트는 삭제됩니다.');
			msg = cutMsg(text);
			$("#searchKywrd"+idx).val(msg);
			return;
		}
	}
	
	function reCount(str) {
		var i;
		var msglen = 0;
		 
		for(i=0; i<str.length; i++) {
			var ch = str.charAt(i);
	
			if(escape(ch).length > 4) {
				msglen += 3;
			} else {
				msglen++;
			}
		}
		
		return msglen;
	}	
	
	function cutMsg(str) {
		var ret = '';
		var i;
		var msglen = 0;
		
		for(i=0; i<str.length; i++) {
		var ch = str.charAt(i);
		
		if(escape(ch).length > 4) {
			msglen += 3;
		} else {
			msglen++;
		}
		
		if(msglen > 39) break;
			ret += ch;
		}
		
		return ret;
	}
	//20180904 - 상품키워드 입력 기능 추가
	
	</script>

</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="newProduct"  id="newProduct" method="post" enctype="multipart/form-data">
		<input type="hidden" name="blackListVendorFlag"		id="blackListVendorFlag" 	value="n" />
		<input type="hidden" name="l1GroupCode" 			id="l1GroupCode" 			value="" />
		<input type="hidden" name="prodAddMasterCd" 		id="prodAddMasterCd" 		value="" />
		<input type="hidden" name="prodAddCd" 				id="prodAddCd" 				value="" />
		<input type="hidden" name="prodAddNm" 				id="prodAddNm" 				value="" />
		<input type="hidden" name="prodCertMasterCd" 		id="prodCertMasterCd" 		value="" />
		<input type="hidden" name="prodCertDtlCd" 		    id="prodCertDtlCd" 		    value="" />
		<input type="hidden" name="prodCertCd" 				id="prodCertCd" 			value="" />
		<input type="hidden" name="prodCertNm" 				id="prodCertNm" 			value="" />
		<input type="hidden" name="prodAddSelectCount" 		id="prodAddSelectCount" 	value="" />
		<input type="hidden" name="prodAddL1CategoryId" 	id="prodAddL1CategoryId" 	value="" />
		<input type="hidden" name="itemRow" 				id="itemRow"				value="${fn:length(itemListInTemp)}" />
		<input type="hidden" name="newProductCode" 			id="newProductCode"			value="<c:out escapeXml='false' value='${param.pgmId}'/>"	/>

		<input type="hidden" name="tradeTypeCode"			id="tradeTypeCode" 			value="6" />
		<input type="hidden" name="productImageId"			id="productImageId"			value="${newProdDetailInfo.prodImgId}" />
		<input type="hidden" name="eventSellPrice"			id="eventSellPrice"			value="${newProdDetailInfo.evtProdSellPrc}" />
		<input type="hidden" name="imgNcnt"					id="imgNcnt"				value="${newProdDetailInfo.imgNcnt}" />
		<input type="hidden" name="onlineDisplayCategoryCode"  id="onlineDisplayCategoryCode" value="${newProdDetailInfo.categoryId}" />


		<input type="hidden" name="officialOrder.newProductGeneratedDivnCode" value="EDI" />
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">

				<!-- tab 구성---------------------------------------------------------------->
				<div id="prodTabs" class="tabs" style="padding-top:10px;">
					<ul>
						<li id="pro01" class="on">기본정보</li>
						<li id="pro02" style="cursor: pointer;">대표이미지</li>
						<li id="pro03" style="cursor: pointer;">배송정책</li>
					</ul>
				</div>
				<!-- tab 구성---------------------------------------------------------------->

				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">
							<c:if test="${empty param.mode}">
								<li class="tit">신규상품등록 - * 표시는 필수 입력항목입니다.</li>
							</c:if>

							<!-- 임시보관함에서 온라인 전용 상품 클릭이벤트 시에 수정모드로 넘어온다.  -->
							<c:if test="${param.mode eq 'modify'}">
								<li class="tit">신규상품 수정 - * 표시는 필수 입력항목입니다.</li>
							</c:if>

							<!-- 온라인상품 등록 이후 보여지는 메세지 -->
							<c:if test="${param.mode eq 'basic'	}">
								<li class="tit">신규상품 등록 : 기본정보가 입력되었습니다. </li>
							</c:if>

							<!-- 신규상품등록현황 조회에서 온라인 전용 상품 클릭이벤트 시에 view 모드로 넘어온다. -->
							<c:if test="${param.mode eq 'view'}">
								<li class="tit">상품정보</li>
							</c:if>
						</li>
						<li class="btn">

							<!-- 임시보관함에서 온라인 전용 상품 클릭이벤트 시에 수정모드로 넘어온다.  -->
							<c:if test="${empty param.mode || param.mode eq 'modify'}">
								<a href="#" class="btn" onclick="submitProductInfo();" id="saveBtn"><span><spring:message code="button.common.save"/></span></a>
							</c:if>

							<c:if test="${param.mode eq 'view'}">상품 정보는 복사 후  상세 페이지에서 수정하실수 있습니다.</c:if>
							<c:if test="${not empty param.mode}">
								<a href="#" class="btn" onclick="copyProductInfo();"><span>복사</span></a>
							</c:if>
						</li>
					</ul>
				</div>
				<font color='white'><b>PEDMPRO000230.jsp</b></font>
				<!-- 01 : search -->
				<div class="bbs_list" style="margin-top:5px">
					<ul class="tit">
						<li class="tit">기본정보</li>
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0" style="table-layout:fixed;">


					<colgroup>
						<col style="width:15%" />
						<col style="width:35%" />
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 온/오프 상품구분</th>
						<td><div>
						<c:if test="${empty param.divnCode || newProdDetailInfo.onoffDivnCd eq '1'}">
							<input type="hidden" name="onOffDivnCode" value="1" /><b>온라인 전용상품</b> &nbsp; &nbsp;
						</c:if>
						<c:if test="${not empty param.divnCode && newProdDetailInfo.onoffDivnCd eq '2'}">
							<input type="hidden" name="onOffDivnCode" value="2" /><b>소셜상품</b> &nbsp; &nbsp;
						</c:if>


							</div>
						</td>
			           <th><span class="star">*</span>판매(88)/내부</th>
						<td >
							<input type="text" name="sellCode"  maxlength="13" style="width:150px;" class="sell88Code" maxlength="13" 	value="<c:out value='${newProdDetailInfo.sellCd}'/>"	readonly/>
						</td>
					</tr>


					<tr>
						<th><span class="star">*</span> 협력업체</th>
						<td>
							<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty newProdDetailInfo.entpCd}">
											<input type="text" name="entpCode" id="entpCode" readonly="readonly" readonly="readonly" value="${newProdDetailInfo.entpCd}" style="width:40%;"/>
										</c:if>
										<c:if test="${empty newProdDetailInfo.entpCd}">
											<input type="text" name="entpCode" id="entpCode" readonly="readonly" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<c:if test="${not empty newProdDetailInfo.entpCd}">
											<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="${newProdDetailInfo.entpCd}" dataType="CP" comType="SELECT" formName="form" />
										</c:if>

										<c:if test="${ empty newProdDetailInfo.entpCd}">
											<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" />
										</c:if>
									</c:when>
							</c:choose>
						</td>
						<th><span class="star">*</span> 상품속성 </th>
						<td>
							<select name="productDivnCode" class="required" style="width:150px;">
								<c:if test="${newProdDetailInfo.prodDivnCd eq '1'}">
									<option value="1"	selected="selected">규격</option>
								</c:if>

								<c:if test="${newProdDetailInfo.prodDivnCd eq '5'}">
									<option value="5"	selected="selected">패션</option>
								</c:if>

								<c:if test="${empty newProdDetailInfo.prodDivnCd}">
									<option value="1">규격</option>
									<option value="5">패션</option>
								</c:if>
							</select>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span>전시카테고리</th>
						<td colspan="3">
								<select id="teamCd" name="teamCd" class="required" style="width:150px;">
									<option value="">선택</option>
									<c:forEach items="${teamList}" var="teamList" varStatus="index" >
										<c:if test="${fn:indexOf(teamList.teamNm,'VIC') == -1}">
											<option value="${teamList.teamCd}">${teamList.teamNm}</option>
										</c:if>
									</c:forEach>
								</select>
								<select id="l1Cd" name="l1Cd" class="required" style="width:150px;">
									<option value="">선택</option>
								</select>
								<select id="l2Cd" name="l2Cd"	class="required">
									<option value="">선택</option>
								</select>
								<select id="l3Cd" name="l3Cd"	class="required">
									<option value="">선택</option>
								</select>
								<br/>
								<div style="overflow:hidden;">
									<li style=" padding: 5px 0 0 0; font-size:11px;">
										<font color=blue>※ 최대 3개 카테고리 지정 가능하며, 추가는 전시카테고리에서 지정합니다.</font>
									</li>
									<div>
										<div style="float:right;padding:0 63px 0 0;">
											<a href="#" class="btn" onclick="javascript:addCategory();"><span>추가</span></a>
											<a href="#" class="btn" onclick="javascript:delCategory();"><span>삭제</span></a>
										</div>
										<div id="ibsheet1"></div>
									</div>
<!-- 									<li style="right:63px;">
										<a href="#" class="btn" onclick="javascript:addCategory();"><span>추가</span></a>
										<a href="#" class="btn" onclick="javascript:delCategory();"><span>삭제</span></a>
									</li>
									<br/>
									<div id="ibsheet1"></div>
 -->								</div>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span> 정상매가</th>
						<td>
							<input type="text" name="normalProductSalePrice"	value="<c:out value='${newProdDetailInfo.norProdSalePrc }'/>" class="required number" maxlength="8" style="width:120px;" onkeydown='onlyNumber(event)'/>
							<html:codeTag   objId="norProdSaleCurr" 	objName="norProdSaleCurr"		parentCode="PRD40" 	comType="SELECT" 	formName="form" 	selectParam="KRW" attr="class=\"onOffField required\""/>
						</td>

						<th><span class="star">*</span> 면과세구분</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="taxatDivCode" objName="taxatDivCode" parentCode="PRD06" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>

					<tr>
						<th>표시단위</th>
						<td>
							<html:codeTag   objId="displayUnitCode" objName="displayUnitCode" parentCode="PRD17" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>

						<th>표시기준수량</th>
						<td>
							 <input type="text" name="displayBaseQuantity" value="<c:out value='${newProdDetailInfo.dpBaseQty}'/>"	maxlength="5" style="width:150px;" class="requiredIf number default0" onkeydown='onlyNumber(event)'/>
						</td>
					</tr>

					<tr>
						<th>상품규격</th>
						<td>
							<input type="text" name="productStandardName" maxlength="7" style="width:150px;"	value="<c:out value='${newProdDetailInfo.prodStandardNm}'/>" />
						</td>
						<th>표시총량</th>
						<td>
							<input type="hidden" 	name="sellPrice"			value="<c:out value='${newProdDetailInfo.evtProdSellPrc }'/>"   />
							<input type="text" 		name="displayTotalQuantity" value="<c:out value='${newProdDetailInfo.dpTotQty}'/>"	maxlength="5"  class="requiredIf number default0" style="width:150px;" onkeydown='onlyNumber(event)'/>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span> 온라인 대표상품코드</th>
						<td >
							<div id="onlineRepresentProductTD"></div>
							<select name="onlineProductCode" class="required" style="width:98%;">
								<option value=""><c:if test="${not empty onlineRepresentProductList}">선택</c:if></option>
								<c:forEach items="${onlineRepresentProductList}" var="online"  >
									<option value="${online.onlineProductCode}">
									${online.profitRate}% /
									${online.onlineProductCode} /
									${online.sellCode} /
									${online.onlineProductName} /
									${online.l4CodeName} /
									${online.taxatDivCode}
									</option>
								</c:forEach>
							</select>
						</td>
						<th><span class="star">*</span> 온라인 대표상품명</th>
						<td>
							<input type="text" name="onlineProductName" class="required" maxlength="80" style="width:150px;"	value="<c:out value='${newProdDetailInfo.onlineProdNm}'/>" readonly/>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span> 이익률(%)</th>
						<td colspan="3">
							<input type="hidden" name="normalProductCost"					value="<c:out value='${newProdDetailInfo.norProdPcost}'/>" />
							<input type="text"   name="profitRate"  class="required rate"	value="<c:out value='${newProdDetailInfo.prftRate}'/>" 	maxlength="5" style="width:150px;" readonly/>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span> 상품명</th>
						<td colspan=3>
							<input type="text" name="productName" class="required"  value="${newProdDetailInfo.prodNm}" style="width:91%;" maxlength="50" />
						</td>
					</tr>

					<tr name="onLineProductNameRow" >
						<th>상품사이즈</th>
						<td>
							<div name="productSize">
								가로 <input type="text" name="productHorizontalLength" value="<c:out value='${newProdDetailInfo.prodHrznLeng}'/>"		style="width:40px" class="requiredIf number default0 size"  maxlength="4" onkeydown='onlyNumber(event)'/>&nbsp;
								세로 <input type="text" name="productVerticalLength"   value="<c:out value='${newProdDetailInfo.prodVtclLeng}'/>"		style="width:40px" class="requiredIf number default0 size"  maxlength="4" onkeydown='onlyNumber(event)'/>&nbsp;
								높이 <input type="text" name="productHeight"           value="<c:out value='${newProdDetailInfo.prodHigt}'/>"		style="width:40px" class="requiredIf number default0 size"  maxlength="4" onkeydown='onlyNumber(event)'/>
							</div>
						</td>
						<th>상품사이즈단위</th>
						<td>
							  	<html:codeTag objId="sizeUnit" objName="sizeUnit" parentCode="PRD42" comType="SELECT" formName="form" selectParam="" defName="선택" />
						</td>
					</tr>

				    <tr>
					    <th><span class="star" >*</span>판매(88)/내부 <br>유효성검사용</th>
						<td colspan="3">
							<font color=blue>※ 단품구성 상품만 기재 필요, 기획/복합 상품이나 바코드 없는 상품은 "1111111111111"로 기재하십시오.
							<br>※ 롯데마트 매장상품 중복 여부 체크용 바코드(88) 기재란, 숫자만 입력 가능!
							</b></font>
							<br>
							<input type="text" name="md_vali_sellCode"   id="md_vali_sellCode"  	value="${newProdDetailInfo.mdValiSellCd}"	style="width:150px;"   class="required number range digit13"  maxlength="13"/>
					    </td>
					</tr>

					<c:if test="${not empty param.divnCode}">
					<tr>
						<th>발행일</th>
						<td>
							 <c:if test="${not empty newProdDetailInfo.productDy}">
								<input type="text" maxlength="8" class="requiredIf"  name="productDay"	value="${fn:substring(newProdDetailInfo.productDy,0,4)}-${fn:substring(newProdDetailInfo.productDy,4,6)}-${fn:substring(newProdDetailInfo.productDy,6,8)}" style="width:80px;" readonly/>
							 </c:if>

							 <c:if test="${empty newProdDetailInfo.productDy}">
								<input type="text" maxlength="8" class="requiredIf"  name="productDay"	value="" style="width:80px;" readonly/>
							 </c:if>
							 <img src="/images/bos/layout/btn_cal.gif" class="middle" onClick="openCal('newProdDetailInfo.productDy');" style="cursor:hand;" />
						</td>

						<th><span class="star">*</span> 배송구분</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="socialProductDeliveryCode" objName="socialProductDeliveryCode" dataType="TET" parentCode="PRD33" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>
					</c:if>

					<tr>
						<th><span class="star">*</span> 계절구분</th>
						<td>
							<!-- <html:codeTag attr="class=\"required\"" objId="seasonDivnCode" objName="officialOrder.seasonDivnCode" parentCode="PRD03" width="150px;" comType="SELECT" formName="form" defName="선택"  /> -->
							<select id="sesnYearCd"	  name="sesnYearCd"  class="required">
								<option value="">선택</option>
								<c:forEach items="${seasonYearList}" var="list" varStatus="index" >
									<option value="${list.yearCd}">${list.yearNm}</option>
								</c:forEach>
							</select>

							<select id="sesnDivnCd"	   name="sesnDivnCd"	class="required">
								<option value="">선택</option>
							</select>
						</td>
						<th><span class="star">*</span> 원산지</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="productCountryCode" objName="productCountryCode" parentCode="PRD16" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span> 최소주문가능량</th>
						<td>
							 <input type="text" maxlength="5"  name="officialOrder.minimumOrderQuantity" 	value="${newProdDetailInfo.minOrdPsbtQty}"	style="width:150px;" class="required number range minimum" onkeydown='onlyNumber(event)'/>
						</td>
						<th><span class="star">*</span> 최대주문가능량</th>
						<td >
							 <input type="text"  maxlength="5" name="officialOrder.maximumOrderQuantity"	value="<c:out value='${newProdDetailInfo.maxOrdPsbtQty}'/>"style="width:150px;" class="required number range maximum" onkeydown='onlyNumber(event)'/>
						</td>
					</tr>

					<tr>
						<th>협력사 내부 상품코드</th>
						<td colspan="3">
							<input type="text"  name="entpInProdCd"  id="entpInProdCd"  style="width:150px;" value="${newProdDetailInfo.entpInProdCd}"  onkeyup="fn_press_han(this);" maxlength="18" style="ime-mode:disabled;"/>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span>상품유형</th>
						<td colspan="3">
							<html:codeTag   objId="onlineProdTypeCd" objName="onlineProdTypeCd"  parentCode="SM335"  comType="RADIO"  formName="form"  selectParam="0"  orderSeqYn="Y"/>
							<br/>
							<div id="prodTypeDiv1" style="padding-left:5px; display:none;">
							* 설명
							<input type="text"  name="prodOptionDesc"  id="prodOptionDesc"  style="width:84%;" maxlength="150"  value="${newProdDetailInfo.prodOptionDesc}"/>
							<br/>
								* 희망배송일은 주문일로부터&nbsp;
								<select id="hopeDeliPsbtDd" name="hopeDeliPsbtDd">
									<c:forEach begin="1" step="1" end="30"  var="ddVal">
										<option value="${ddVal}">${ddVal}일</option>
									</c:forEach>
								</select>
								&nbsp;이후부터 가능
							</div>
							<div id="prodTypeDiv2" style="padding-left:5px; display:none;">
								<font color="blue" style="font-size:12px;">※ 세트수량 옵션내용 구분자를 ; 으로 구분해서 입력 하세요. (ex : 사과;배;수박)</font>
								<br/>
								세트수량&nbsp;
								<input type="text" id="setQty" name="setQty"	 style="width:150px; ime-mode:disabled" value="${newProdDetailInfo.setQty}" onkeydown='onlyNumber(event)' maxlength="3"/>&nbsp;&nbsp;
								옵션&nbsp;
								<input type="text" id="optnLoadContent" name="optnLoadContent"	  style="width:350px;" value="${newProdDetailInfo.optnLoadContent}"/>
							</div>
							<div id="prodTypeDiv3" style="padding-left:5px; display:none;">
								 배송예정일&nbsp;
								<input type="text"  name="deliDueDate"  id="deliDueDate"  style="width:50%;" maxlength="150"  value="${newProdDetailInfo.prodOptionDesc}"/><font color="red">구매대행 이용불가</font>
								<br/>
								<font color="blue" style="font-size:11px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ※ 미입력시 [주문 후 5~10일 정도 소요] 문구로 일괄 적용됩니다.</font>
							</div>
							<div id="selTime" style="padding-left:5px; display:none;">
								예약 주문 가능일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            <input type="text" maxlength="8"  id="psbtStartDyVal"  name="psbtStartDyVal"  style="width:80px;" readonly/>
	                            <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.psbtStartDyVal');" style="cursor:hand;" />
	                            <select id="psbtStartTime" name="psbtStartTime">
	                            	<c:forEach begin="0" step="1" end="23"  var="optnVal">
	                            		<c:set var="optnVal" value="${optnVal}"/>
	                            		<c:if test="${optnVal < 10}">
	                            			<c:set var="optnVal" value="0${optnVal}"/>
	                            		</c:if>
	                            		<option value="${optnVal}">${optnVal}시</option>
	                            	</c:forEach>
	                            </select>
	                            <input type="hidden" id="psbtStartDy"  name="psbtStartDy"  value="${newProdDetailInfo.psbtStartDy}" />
	                            ~
								<input type="text" maxlength="8"  id="psbtEndDyVal"  name="psbtEndDyVal"  style="width:80px;" readonly/>
								<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.psbtEndDyVal');" style="cursor:hand;" />
								<select id="psbtEndTime" name="psbtEndTime">
	                            	<c:forEach begin="0" step="1" end="23"  var="optnVal">
	                            		<c:set var="optnVal" value="${optnVal}"/>
	                            		<c:if test="${optnVal < 10}">
	                            			<c:set var="optnVal" value="0${optnVal}"/>
	                            		</c:if>
	                            		<option value="${optnVal}">${optnVal}시</option>
	                            	</c:forEach>
	                            </select>
	                            <input type="hidden" id="psbtEndDy"  name="psbtEndDy"  value="${newProdDetailInfo.psbtEndDy}" />

	                            <br/>

								예약상품 출고지시일&nbsp;
								<input type="text" maxlength="8"  id="pickIdctDy"  name="pickIdctDy"  value="${newProdDetailInfo.pickIdctDy}"  style="width:80px;" readonly/> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.pickIdctDy');" style="cursor:hand;" />
							</div>
						</td>
					</tr>

					<tr>
						<th>친환경인증여부</th>
						<td>
							<input type="radio" class="onOffField"  name="officialOrder.ecoYn" value="0"/>N
							<input type="radio"  class="onOffField" name="officialOrder.ecoYn" value="1"/>Y
						</td>
						<th>친환경인증분류명</th>
						<td>
							<html:codeTag   objId="ecoNm" objName="officialOrder.ecoNm" parentCode="PRD08" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>

					<tr>
						<th>임직원 상품<br/>추가 등록</th>
						<td colspan="3">
							<input type="checkbox"  id="psbtChkFlag" name="psbtChkFlag"  <c:if test="${newProdDetailInfo.staffSellYn eq '1'}">checked="checked"</c:if> /> 임직원 전용몰 판매 가능&nbsp;&nbsp;&nbsp;
							<input type="text" id="staffDcAmt" name="staffDcAmt" style="width:150px;" value="${newProdDetailInfo.staffDcAmt}" onkeydown='onlyNumber(event)'/>
							<input type="hidden" id="staffSellYn" name="staffSellYn" value="${newProdDetailInfo.staffSellYn}"/>
						</td>
					</tr>

					<c:if test="${empty param.divnCode}">
					<tr>
							<th>발행일</th>
							<td colspan="3">
								<c:if test="${not empty newProdDetailInfo.productDy}">
									<input type="text" maxlength="8" class="requiredIf"  name="productDay"	value="<c:out value='${newProdDetailInfo.productDy}'/>" style="width:80px;" readonly/>
								</c:if>

								<c:if test="${empty newProdDetailInfo.productDy}">
									<input type="text" maxlength="8" class="requiredIf"  name="productDay"	value="" style="width:80px;" readonly/>
								</c:if>
								<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.productDay');" style="cursor:hand;" />
							</td>
					</tr>
					</c:if>

					<tr>
						<th>가격 발급구분 코드</th>
						<td>
							<c:if test="${empty newProdDetailInfo.onoffDivnCd || newProdDetailInfo.onoffDivnCd eq '1'}">
								<input type="radio" name="priceIssueDivnCode" value="0" />비규격
								<input type="radio" name="priceIssueDivnCode" value="1" />규격
							</c:if>

							<c:if test="${not empty newProdDetailInfo.onoffDivnCd && newProdDetailInfo.onoffDivnCd eq '2'}">
								<input type="radio" name="priceIssueDivnCode" value="9" checked/>임직원제외
							</c:if>
						</td>

						<th>수량/중량구분</th>
						<td ><div>
								<input type="radio"  name="officialOrder.quantityWeightDivnCode" value="0" />수량
								<input type="radio"  name="officialOrder.quantityWeightDivnCode" value="1" />중량
							</div>
						</td>
					</tr>

					<tr>
						<th>모델명</th>
						<td colspan="3">
							 <input type="text" maxlength="30" class="requiredIf" id="modelName" name="officialOrder.modelName" value="${newProdDetailInfo.modelNm}"style="width:150px;" />
						</td>
					</tr>

					<tr>
						<th>브랜드</th>
						<td>
							 <input type="text" maxlength="30" class="requiredIf" id="brandName" name="officialOrder.brandName" 	value="${newProdDetailInfo.brandName}"	style="width:150px;" />
							 <input type="hidden" id="brandCode" name="officialOrder.brandCode"	value="${newProdDetailInfo.brandCode}" /> <a href="javascript:openBrandPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>
						</td>
						<th>메이커</th>
						<td >
							 <input type="text" maxlength="30" class="requiredIf"  id="makerName" name="officialOrder.makerName"	value="<c:out value='${newProdDetailInfo.makerName}'/>" style="width:150px;" />
							 <input type="hidden" name="officialOrder.makerCode" id="makerCode" value="${newProdDetailInfo.makerCode}" /> <a href="javascript:openMakerPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>
						</td>
					</tr>
					<tr>
						<th>체험형 상품 여부</th>
						<td colspan = "3" style="padding-right: 5px;">
							<div style="display: inline; padding-right: 80px;">
								<input type="radio" id="exprProdYn" name="exprProdYn" value="0" <c:out value="${newProdDetailInfo.exprProdYn eq false ? 'checked' : '' }" />> N
								<input type="radio" id="exprProdYn" name="exprProdYn" value="1" <c:out value="${newProdDetailInfo.exprProdYn eq true ? 'checked' : '' }" />> Y
							</div>
							 판매자 추천평
							<input type="text" id="sellerRecomm" name="sellerRecomm" style="width:465px;" disabled="disabled" onkeyup="checkByte();" value="<c:out value='${newProdDetailInfo.sellerRecomm}'/>">
							<div style="text-align: right"><span id="messagebyte">0</span> / 100byte </div>
						</td>
					</tr>
				</table>

			</div>

			<!-- 20180904 상품키워드 입력 기능 추가 -->
			<div class="bbs_list" style="margin-top:5px">
				<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th>* 상품키워드<a href="javascript:fnKeywordAddNew('new');" id="addKeyword" class="btn" ><span>추가</span></a></th>
						<td id="keywordTd">
							<font color=blue>※ 저장 전 신규(추가) 상품키워드의 순번은 ◈ 로 표시됨</font>
							
							<c:if test="${empty tpcPrdKeywordList}">
								<table id='keywordSubTable' style='width:700px'>
									<tr>
										<th style='text-align:center; width:30px'>순번</th>
										<th style='text-align:center; width:200px'>검색어</th>
										<th style='text-align:center; width:50px'></th>
									</tr>
									<tr>
										<td style='text-align:center'>◈<input type='hidden' name='seq' id='seq1' value='NEW' /></td>
										<td><input type='text' style='text-align:left; width:98%;' name='searchKywrd' id='searchKywrd1' value='' maxlength="39" onblur="limitPushString(1);" /></td>
										<td></td>
									</tr>
								</table>
							</c:if>
							
							<c:if test="${not empty tpcPrdKeywordList}">
								<table id='keywordSubTable' style='width:700px'>
									<tr>
										<th style='text-align:center; width:50px'>순번</th>
										<th style='text-align:center; width:200px'>검색어</th>
										<th style='text-align:center; width:50px'></th>
									</tr>
									<c:forEach var="keywordList" items="${tpcPrdKeywordList}" varStatus="index">
										<tr id="kwRow${index.count}">
											<td style='text-align:center'>
												${keywordList.num}<input type='hidden' name='seq' id='seq${index.count}' value='${keywordList.seq}' />
											</td>
											<td>
												<input type='text' style='text-align:left; width:98%;' name='searchKywrd' id='searchKywrd${index.count}' value='${keywordList.searchKywrd}' maxlength="39" onblur="limitPushString(${index.count});" />
											</td>

											<c:choose>
												<c:when test="${index.count eq 1}">
													<td></td>
												</c:when>
												<c:otherwise>
													<td>
														<a href='javascript:fnKeywordDelete("${index.count}", "${keywordList.seq}")' id='deleteNewKeyword${index.count}' class='btn'><span>삭제</span></a>
													</td>
												</c:otherwise>
											</c:choose>
											
										</tr>
									</c:forEach>
								</table>
							</c:if>
														
						</td>
					</tr>
				</table>
			</div>			
			<!-- 20180904 상품키워드 입력 기능 추가 -->

			<div class="bbs_list" style="margin-top:5px">
				<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th>단품정보<a href="javascript:fnItemAddNew('new'); fnTableControll();" id="addItem" class="btn" ><span>추가</span></a></th>
						<td id="itemTd">
							<input type="radio" name="prodPrcMgrYn" value="0" checked="checked"/>옵션 별 동일가격
							<input type="radio" name="prodPrcMgrYn" value="1"/>옵션 별 별도가격
							
							<div style="float:right;">
								<a href="#" class="btn" onclick="exeExcelFile();"><span>양식다운</span></a>
								<input type="file" name="createFile" id="createFile" style="width:200px;" value="파일이름"  />
								<a href="#" class="btn" onclick="exeExcelFileUpload();"><span>단품정보 일괄등록</span></a>
							</div>
							
							<c:if test="${empty itemListInTemp}">
							<table id='itemSubTable' style='width:700px'>
								<tr>
									<th style='width:70px'>단품코드</th>
									<th style='width:200px'>옵션설명</th>
									<th style='width:60px'>재고여부</th>
									<th style='width:100px'>재고수량</th>
									<th id="optnAmtTH" style='width:100px'>가격</th>
									<th></th>
								</tr>
							</table>
							</c:if>
							<c:if test="${not empty itemListInTemp}">
							<table id='itemSubTable' style='width: 700px'>
								<tr>
									<th style='width:70px'>단품코드</th>
									<th style='width:200px'>옵션설명</th>
									<th style='width:60px'>재고여부</th>
									<th style='width:100px'>재고수량</th>
									<th id="optnAmtTH" style='width:100px'>가격</th>
									<th></th>
								</tr>
								<c:forEach var="itemList" items="${itemListInTemp}"
									varStatus="index">
									<tr id="row${index.count}">
										<td style='text-align: center'>${itemList.itemCode }<input
											type='hidden' name='itemCd' id='itemCd${index.count}'
											value='${itemList.itemCode }' />
										</td>
										<td><input type='text' name='optnDesc' style='width:98%;'
											id='optnDesc${index.count}' value='${itemList.optnDesc }' />
										</td>
										<td><select id='stkMgrYn${index.count}'
											name='stkMgrYn' style='width:98%;'>
												<option value='N'
													<c:if test="${itemList.stkMgrYn eq 'N'}">selected</c:if>>N</option>
												<option value='Y'
													<c:if test="${itemList.stkMgrYn eq 'Y'}">selected</c:if>>Y</option>
										</select></td>
										<td><input type='text' style='text-align: right; width:98%;'
											id='rservStkQty${index.count}' name='rservStkQty'
											onkeydown='onlyNumber(event)'
											value='${itemList.rservStkQty}' />
										</td>
										<td id='optnAmtTD${index.count}'><input type='text' style='text-align: right; width:98%;'
											id='optnAmt${index.count}' name='optnAmt'
											onkeydown='onlyNumber(event)'
											value='${itemList.optnAmt}' />
										</td>
										<td><a
											href='javascript:fnItemDelete("${index.count}", "${itemList.itemCode }")'
											id='deleteNewItem${index.count}' class='btn'
											<c:if test="${!index.last}">style="display:none"</c:if>><span>삭제</span>
										</a>
										</td>
									</tr>
								</c:forEach>
							</table>
							</c:if>
						</td>
					</tr>
				</table>
			</div>

			<div>
					<ul name="productAddTemplateTitle" class="tit mt10" style="display:block">

						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">전자 상거래</li>
								<li class="tit" id="productAddSelectBox" style="display:none">
									<html:codeTag objId="productAddSelectTitle" objName="productAddSelectTitle" width="150px;" comType="SELECT" formName="form" defName="선택"  />
								</li>
								<li class="tit">템플릿</li>
								<li class="tit" id="productAddSelectBox" style="display:none">
									<select id="productAddSelectTemp" name="productAddSelectTemp">
										<option value="">선택</option>
									</select>
								</li>
							  	<a href='/html/Ecom_Manual_v1_0.ppt' class="btn" id="excel"><span>전자상거래법 메뉴얼</span></a>


							</ul>

						</div>

						</div>
					</ul>

					<div name="productAddTemplateData" class="bbs_search"  style="display:none">
						<table  name="data_List" class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:35px;"/>
								<col style="width:65px;"/>
							</colgroup>

						</table>
					</div>
				</div>

				<div>
					<ul name="productCertTemplateTitle" class="tit mt10" style="display:block">

						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><span class="star">*</span>제품안전인증</li>
								<li class="tit" id="productCertSelectBox" style="display:none">
									<html:codeTag objId="productCertSelectTitle" objName="productCertSelectTitle" width="150px;" comType="SELECT" formName="form" defName="선택"  />
								</li>
								<li class="tit" id="productCertDtlSelectBox" style="display:none">
									<select id="productCertSelectDtlTitle" name="productCertSelectDtlTitle">
										<option value="">선택</option>
									</select>
								</li>
							</ul>

						</div>
					</ul>

					<div name="productCertTemplateData" class="bbs_search"  style="display:none">
						<table  name="cert_List" class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:35px;"/>
								<col style="width:65px;"/>
							</colgroup>

						</table>
					</div>
				</div>

				<!-- editor 시작 ----------------------------------------------------------------------->
				 <div class="bbs_list">
					<ul class="tit">
							<li class="tit">*상품설명 : 온라인에서 사용될 설명이며 <font color='red'><b>필수</b></font>사항입니다.</li>
							<!--
							<li class="btn">
								<a href="javascript:doImageSplitterView();" class="btn" ><span>이미지추가</span></a>
							</li>
							-->
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td>
								<!-- <textarea id="pe_agt" name="pe_agt" alt="initText" title="initText" Style="width:730px;height:450px;font-size:10pt"><p>Welcome to <span style="font-weight:bold;">CrossEditor 3.0</span> sample page</p></textarea> -->
								<input type="hidden"	name="productDescription"		id="productDescription"	value="<c:out value='${newProdDetailInfo.prodDesc}'/>">
								<script type="text/javascript" language="javascript">
									var CrossEditor = new NamoSE('pe_agt');

									CrossEditor.params.Width 		= "100%";
									CrossEditor.params.UserLang 	= "auto";
									CrossEditor.params.FullScreen 	= false;
									CrossEditor.params.SetFocus 	= false; // 에디터 포커스 해제
									CrossEditor.params.ImageSavePath = "edi";
									CrossEditor.params.ActiveTab = 40;
									CrossEditor.EditorStart();

									function OnInitCompleted(e){
										e.editorTarget.SetBodyStyle('text-align','center');
										e.editorTarget.SetBodyValue(document.getElementById("productDescription").value);
									}
								</script>
							</td>
						</tr>
					</table>
				</div>
				<!-- editor 끝 ----------------------------------------------------------------------->

			</div>
		</form>
	</div>

	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품</li>
					<li>신규상품관리</li>
					<li>온라인전용 상품등록</li>
					<li class="last">기본정보</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!-- hidden Form -->
	<form name="hiddenForm"	id="hiddenForm">
		<input type="hidden"	name="vendorTypeCd"		id="vendorTypeCd"	value="<c:out value='${epcLoginVO.vendorTypeCd}'/>" />
		<input type="hidden"	name="pgmId"				    id="pgmId"				value="<c:out value='${newProdDetailInfo.pgmId }'/>"	/>	<!-- 상품이 등록되고 나면 등록된 상품의 pgmId가 설정됨 -->
		<input type="hidden"	name="entpCd"				    id="entpCd"				value="<c:out value='${newProdDetailInfo.entpCd }'/>"	 />	<!-- 상품이 등록되고 나면 등록된 상품의 협력업체코드가 설정됨 -->
		<input type="hidden"	name="mode"					id="mode"				value="<c:out value='${param.mode}'/>"	 />	<!-- view, modify, ''-->
		<input type="hidden"	name="cfmFg"				    id="cfmFg"				value="<c:out value='${param.cfmFg}'/>" />	<!-- 상품확정구분 -->
	</form>
	
	<form name="excelForm"		id="excelForm">
		<iframe name="frameForExcel" style="display:none;"></iframe>
		<input type="hidden"		name="optionVal"	 id="optionVal"/>
		<input type="hidden"		name="fileName"	 id="fileName"/>
	</form>
	
	<!--
	<form name="excelUploadForm" id="excelUploadForm" enctype="multipart/form-data">
		<input type="hidden" name="file" id="file"/>
	</form>
	-->
</body>

</html>
