<%--
	Page Name 	: NEDMPRO0020.jsp
	Description : 신상품등록 [온오프전용]
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.12.11 		SONG MIN KYO 	최초생성
--%>
<%-- 
	250514 yja
	- jsp max byte error 발생으로 인한 스크립틀릿 → jsp:include로 변경
	- jsp:include는 스크립틀릿 공유 불가하여 기존사용 스크립틀릿만 별도로 모은 파일 생성 ::: incTaglib 
 --%>
<%-- <%@ include file="../common.jsp"%> --%>
<%-- <%@ include file="/common/scm/scmCommon.jsp"%> --%>
<%-- <%@ include file="./CommonProductFunction.jsp"%> --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/incTaglib.jsp" %>
<jsp:include page="/WEB-INF/jsp/edi/common.jsp"/>
<jsp:include page="/common/scm/scmCommon.jsp"/>
<jsp:include page="/WEB-INF/jsp/edi/product/CommonProductFunction.jsp"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store" />
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><spring:message code='msg.product.onOff.title' /></title>

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

select option {
  background-color: #ffffff;
}
select option.default {
  background-color: #f3f3f3;
  font-weight: bolder;
}
select.default {
  background-color:#f3f3f3;
  font-weight: bolder;
}

input[type=text][readonly]{
    background-color: #f8f8f8;
    border-radius: 2px;
    border: 1px solid rgba(118, 118, 118, 0.3);
}
</style>

<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
<script type="text/javascript" src="<c:out value='${namoPath}'/>"></script>

<script type="text/javascript">
let gridRowIdx = 100;	//jqGrid 공통 rowIdx

		var gDpBaseQty = "";			//소분류에 따른 표시기준수량
		var gDpUnitCd = "";				//소분류에 따른 표시기준단위
		let copyPrcChanCd = "KR02";		//원매가 카피대상 채널코드

		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {
			alert("☆☆☆☆☆☆☆☆☆☆ 공지사항 ☆☆☆☆☆☆☆☆☆☆\n"+
					"\n[전자상거래 등에서의 소비자보호에 관한 법률]에 의거 '어린이 제품'은 상품 등록시 공급자 적합성 확인을 받으셔야 합니다."+
					"\n상품군 : 아동용 섬유제품, 가죽제품, 면봉, 안경(선글라스 포함), 물안경,"+
					"\n우산 및 양산, 바퀴달린 운동화, 롤러 스케이트, 스키용구, 스노보드, 가구,"+
					"\n쇼핑카트 부속품, 어린이용 장신구, 킥보드, 인라인 롤러 스케이트, 기타"+
					"\n어린이용품(도서류(학습서적, 완구서적, 동화책), 학습교구(공예, 미술, 체육), 보호용품(모서리 충격 보호대, 미끄럼방지 패드, 문열림 방지장치),"+
					"\n위생용품(칫솔, 칫솔캡/걸이, 청결용품) 등 어린이와 관련된 모든 상품에"+
					"\n적용됩니다.");

			alert("\n\n그림(이미지) 등록 시, 그림 설명은 필수 항목이므로 꼭 입력해 주셔야 합니다.\n\n*해당 메세지는 '그림설명' 입력 여부와 무관하게 '기본정보탭' 클릭 시 자동 노출됩니다.\n\n");

			//----- 상품 영문명 한글입력방지[ime-mode:disabled 크롬에서는 안먹어서 추가]
			$('#prodEnNm').live("blur keyup", function() {
				  $(this).val( $(this).val().replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
			});

			//----- 협력업체 Default설정
			var repVendorId		=	"<c:out value='${epcLoginVO.repVendorId}'/>";			//관리자로 로그인 해서 협력업체 갈아타기 로그인시 협력업체 코드
			var entpCd			=	"<c:out value='${newProdDetailInfo.entpCd}'/>";			//상품등록이후 조회된 상품의 협력업체 코드
			var trdTypeDivnCd	=	"<c:out value='${newProdDetailInfo.trdTypeDivnCd}'/>"	//상품등록이후 조회된 상품의 거래유형
			var adminId			=	"<c:out value='${epcLoginVO.adminId}'/>";			//상품등록이후 조회된 상품의 협력업체 코드

			//----- 최초 상품등록 페이지 로딩시
			if (entpCd.replace(/\s/gi, '') == "") {
				$("#newProduct select[name='entpCd']").val(repVendorId);
				_eventSetEntpCdInfoCtrl(repVendorId);
				$("input[name='sellCd']").attr("readonly", true);

				//채널 기본값 및 원매가영역 셋팅 (기본:마트/KR02,오카도/KR09)
// 				$("input[name='chanCd'][value='KR02']").prop("checked", true);
// 				fncSetPriceAreaByChan("KR02", true);
// 				$("input[name='chanCd'][value='KR09']").prop("checked", true);
// 				fncSetPriceAreaByChan("KR09", true);
			//----- 상품등록 이후 상품 수정모드시
			} else {
				$("#newProduct select[name='entpCd']").val(entpCd);
				_eventCheckPbPartner(entpCd);
				_eventChangeFiledByTradeType(trdTypeDivnCd);
				modifyFontweight("tradeType",trdTypeDivnCd);
			}

			//-----탭 클릭 이벤트
			$("#prodTabs li").click(function() {
				var id = this.id;

				var pgmId = $("input[name='pgmId']").val();

				//기본정보 탭
				if (id == "pro01") {
					/* $("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0020.do'/>");
					$("#hiddenForm").attr("method", "post").submit(); */
				//속성입력 탭
				} else if (id == "pro02") {
					if (pgmId == "") {
						alert("<spring:message code='msg.product.tab.mst'/>");
						return;
					}

					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0021.do'/>");
					$("#hiddenForm").attr("method", "post").submit();

				//이미지 등록 탭
				} else if (id == "pro03") {
					if (pgmId == "") {
						alert("<spring:message code='msg.product.tab.mst'/>");
						return;
					}

					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0022.do'/>");
					$("#hiddenForm").attr("method", "post").submit();

				//영양성분 탭
				} else if (id == "pro04") {
					if (pgmId == "") {
						alert("<spring:message code='msg.product.tab.mst'/>");
						return;
					}

					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0027.do'/>");
					$("#hiddenForm").attr("method", "post").submit();
				//ESG 탭
				} else if (id == "pro05"){
					if (pgmId == "") {
						alert("<spring:message code='msg.product.tab.mst'/>");
						return;
					}
	
					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0028.do'/>");
					$("#hiddenForm").attr("method", "post").submit();
				}
			});

			//-----hiddenForm에 있는 SESSION의 협력사 타입
			//var vendorTypeCd	=	$("#hiddenForm input[name='vendorTypeCd']").val().replace(/\s/gi, '');
			var vendorTypeCd	=	$("#hiddenForm input[name='vendorTypeCd']").val();

			//-----협력사 타입이 06이면 신규 온오프 상품을 등록할수 없다.
			if (vendorTypeCd == "06") {
				alert("<spring:message code='msg.product.onOff.not.permission'/>");
				history.back(-1);
			}

			//-----상품구분(규격, 패션)
			$("#newProduct select[name=prodDivnCd]").change(function() {
				_eventSetupFieldByProductDivnCode($(this).val().replace(/\s/gi, ''));
				modifyFontweight("productDivnCode",$(this).val().replace(/\s/gi, ''));
			});

			//-----협력업체 선택시 실행.
			$("#newProduct select[name=entpCd]").change(function() {
				_eventSetEntpCdInfoCtrl($(this).val());
			});

			/* 250403 마트 차세대 VIC여부 삭제 */
// 			$("#newProduct select[name=matCd]").change(function() {
//  					_eventSetupMatcdVicOnlineCd($(this).val());
// 					_eventSetMatchInfoCtrl($(this).val());
// 			});

			$("select[name=prodTypeDivnCd]").change(function () {
				_eventSetupJang($(this).val().replace(/\s/gi, ''));
			});

			$("select[name=sType]").change(function () {
				 var prodTypeDivnCd = $("#prodTypeDivnCd option:selected").val().replace(/\s/gi, '');
				_eventSetupJang(prodTypeDivnCd);

				const sTypeValue = document.getElementById("sType").value;
				if (sTypeValue === "2" || sTypeValue === "3" || sTypeValue === "5") {
					_displayAllProdTypeDivnCd();
				}
				if (sTypeValue === "1" || sTypeValue === "4" || sTypeValue === "") {
					const entpCd = document.getElementById("entpCd").value;
					_eventCheckPbPartner(entpCd);
				}
			});
			//----- 팀 변경시 이벤트
			$("#newProduct select[name=teamCd]").change(function() {

				//----- 대, 중, 소분류 초기화
				$("#l1Cd option").not("[value='']").remove();
				//$("#l1Cd").addClass("default");	//200306 EC전용 카테코리 추가
				$("#l2Cd option").not("[value='']").remove();
				//$("#l2Cd").addClass("default");	//200306 EC전용 카테코리 추가
				$("#l3Cd option").not("[value='']").remove();
				//$("#l3Cd").addClass("default");	//200306 EC전용 카테코리 추가
				$("#grpCd option").not("[value='']").remove();

				//doCategoryReset();	//200306 EC전용 카테코리 추가

				_eventSelectL1List($(this).val().replace(/\s/gi, ''));
			});

			//----- 대분류 변경시 이벤트
			$("#newProduct select[name=l1Cd]").change(function() {
				//console.log("event:$(#newProduct select[name=l1Cd]).change");
				var groupCode	=	$("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');

				//----- 중, 소분류 초기화
				$("#l2Cd option").not("[value='']").remove();
				//$("#l2Cd").addClass("default");	//200306 EC전용 카테코리 추가
				$("#l3Cd option").not("[value='']").remove();
				//$("#l3Cd").addClass("default");	//200306 EC전용 카테코리 추가
				$("#grpCd option").not("[value='']").remove();
				$("li[id=productCertDtlSelectBox]").hide();
				//품질안정인증 기존행 삭제
				//fnRemoveCert_List();

				var mode    	 = $("#mode").val();				 // 보고 있는 페이지 모드 (신상품등록현황 : view)


				$("select[name='dpUnitCd']").attr("disabled", false);
				$("input[name='dpBaseQty']").attr("disabled", false);

				commerceChange2(groupCode, $(this).val().replace(/\s/gi, ''), "", "", ""); //--20170520 infoGrpCd3 파라미터 추가
			});

			//----- 중분류 변경시 이벤트
			$("#newProduct select[name=l2Cd]").change(function() {
				var groupCode	=	$("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');
				var l1Cd		=	$("#newProduct select[name=l1Cd]").val().replace(/\s/gi, '');


				$("select[name='dpUnitCd']").val("");
				$("input[name='dpBaseQty']").val("");

				$("select[name='dpUnitCd']").attr("disabled", false);
				$("input[name='dpBaseQty']").attr("disabled", false);


				//----- 소분류 초기화
				$("#l3Cd option").not("[value='']").remove();
				//$("#l3Cd").addClass("default");	//200306 EC전용 카테코리 추가
				$("#grpCd option").not("[value='']").remove();

				_eventSelectL3List(groupCode, l1Cd, $(this).val().replace(/\s/gi, ''));
			});

			//----- 소분류 변경시 이벤트
			$("#newProduct select[name=l3Cd]").change(function() {
              var l3Cd		=	$("#newProduct select[name=l3Cd]").val().replace(/\s/gi, '');

              $("#grpCd option").not("[value='']").remove();
              if ($(this).val().replace(/\s/gi, '') != "") {
                //----- 표시단위, 표시수량 Default 셋팅
                _eventSetDpBaseQty($(this).val());
              }
              //	var aaa=$(this).val().replace(/\s/gi, '');
              //	alert("aaa:"+aaa);

              var newL3Cd      = $("#l3Cd option:selected").val(); // 새로 입력한 소분류
              var oldL3Cd      = $("#oldL3Cd").val();        // 기존 입력했던 소분류

              var nutAttrRegYn = "<c:out value='${newProdDetailInfo.nutAttrRegYn}'/>"; // 등록된 영양성분속성값이 있는지

              if( nutAttrRegYn == "Y" && mode != "view" && oldL3Cd && newL3Cd != oldL3Cd ) {
                alert("선택한 소분류가 기존 소분류와 다를시 영양성분값이 초기화됩니다.");
                $("#delNutAmtYn").val("Y");
              } else {
                $("#delNutAmtYn").val("N");
              }

                <!-- 24NBM_OPS_CAT S-->
                //Zetta grid data list 초기화
                fncClearAllGridData("zettaDispCtg");
                <!-- 24NBM_OPS_CAT E-->

				$("input[name=l3Cd]").val(newL3Cd);
                _eventSelectGrpList($(this).val().replace(/\s/gi, ''));
                selectEcStandardCategoryMapping();	// 200306 EC전용 카테고리 추가
			});


			//-----기본값 설정
			$("#protectTagTypeCd").removeClass("required");
			$("#protectTagTypeCd").parent().prev().find("span.star").hide();
			$("#protectTagTypeCd").parent().prev().css("font-weight","normal");
			$('#homeCd').val('Z9').prop("selected",true); // 원산지값 기본 설정
			//$("#zzNewProdFg").val("1");		// 2016.03.01 추가 신상품구분 default 순수신상품으로 선택되게.. by song min kyo

			//-----필수 콤보박스 값 검증
			$(document).on("change", "select.required", function() {
				//console.log("event:$(select.required).change");
				validateSelectBox($(this));
			});

			//-----필수 입력항목 검증
			$(document).on("blur", "input:text.required", function() {
				//console.log("event:$(input:text.required).blur");
				if( !$(this).attr("readonly"))
				validateFormTextField($(this));
			});

			//-----해당 입력 항목이 값이 있는경우 검증
			$(document).on("blur", "input:text.requiredIf", function() {
				if( $(this).val().replace(/\s/gi, '') != ""  ) {
					validateTextField($(this));
				} else {
					deleteErrorMessageIfExist($(this));
				}
			});

			//---- 채널 체크박스 값 검증
			$("input[name='chanCd']").change(function(){
				//체크된 채널이 있는지 확인
				var chkLen = $("input[name='chanCd']:checked").length;
				if(chkLen == 0){
					showErrorMessage($("input[name=chanCd]").first());
				}else{
					deleteErrorMessageIfExist($("input[name=chanCd]").first());
				}
			});

			//-----radio버튼이 선택되었는지 검증.(이벤트가 없어도 저장에서 체크하므로 주석처리 2016.01.08 By SONG MIN KYO)
			/* $("input:radio.required").click(function() {
				validateRadioField($(this));
			}); */

			//-----상품유형 선택시 선택옵션
			$("select[name=prodTypeDivnCd]").change(function () {
				_eventSetupJang($(this).val().replace(/\s/gi, ''));
			});

			if ($("#flowDdMgrCd").val() == "") {
				setDayForWarehouse("");
				modifyFontweight("warehouse","");
			}

			//-----유통일 관리 여부 변경시
			$("#flowDdMgrCd").change(function() {
				setDayForWarehouse($(this).val().replace(/\s/gi, ''));
				modifyFontweight("warehouse",$(this).val().replace(/\s/gi, ''));
			});

			//-----단일상품일때만 88코드 입력 가능(기존 AS-IS에는 include 되어있는 공통함수 jsp에서 컨트롤 했으나 TO-BE에서는 해당 페이지에서 컨트롤이 되도록 변경 2016.01.08 By SONG MIN KYO)
			$(":radio[name='prodAttTypFg']").change(function() {
				_eventSetSellCdStat($(this).val());
				//console.log($(this).val());
				//----- 판매코드 입력란 초기화
				deleteErrorMessageIfExist($("input[name='sellCd']"));
				$("input[name='sellCd']").val("");

				if ($(this).val() == "00") {
					$("input[name='sellCd']").removeAttr("readonly");
				} else {
					$("input[name='sellCd']").attr("readonly", true);
				}
 				//-----단일상품일때만 88코드 입력 가능(묶음일때는 변형속성 탭에서 88입력가능) //이동빈 판매코드 필수 추가
 				//_eventSetSellCdStat($(this).val());
			});


			//-----88코드 관련 필드 검증
			$("input[name=sellCd]").blur(function() {
				//console.log("val == " + $(this).val().replace);
				if($(this).val().replace(/\s/gi, '') != "") {
					validateSellCode($(this));
				} else {
					deleteErrorMessageIfExist($("input[name=sellCd]"));
				}
			});

			// 도난방지태그 변경시
			// 미사용일 경우, 도난 방지 태그 유형 값은 선택 못하도록 수정
			// AS-IS에도 함수는없어 실행되지 않으나 혹시 몰라 복사만 해놓고 주석처리
			/* $("#protectTagDivnCd").change(function() {
				setProtectTagTypeDivn();
			}); */

			//-----전상법 select 변경시 액션
			$("select[name=productAddSelectTitle]").change(function() {
				//if( $(this).val().replace(/\s/gi, '') != "" ) {
					selectBoxProdTemplateDetailList($(this).val().replace(/\s/gi, ''));
				//}
			});

			/* 200306 EC 전용 카테고리 추가 START */
			// EC 표준카테고리 select 변경시 액션
			$("#ecStandardLargeCategory").change(function() {
				if( $(this).val() != '' ){
					if(initEcInfo()) {
					    selectEcStandardCategory("ecStandardMediumCategory", $(this));
						$("#ecStandardSmallCategory option").not("[value='']").remove();
						$("#ecStandardSubCategory option").not("[value='']").remove();
					} else {
						$("#ecStandardLargeCategory").val($("#prev-ecStandardLargeCategory").val());
					}
				}else{
					alert("카테고리를 다시 선택해 주세요.");
				}
			});
			$("#ecStandardMediumCategory").change(function() {
				if( $(this).val() != '' ){
					if(initEcInfo()) {
					    selectEcStandardCategory("ecStandardSmallCategory", $(this));
						$("#ecStandardSubCategory option").not("[value='']").remove();
					} else {
						$("#ecStandardMediumCategory").val($("#prev-ecStandardMediumCategory").val());
					}
				}else{
					alert("카테고리를 다시 선택해 주세요.");
				}
			});
			$("#ecStandardSmallCategory").change(function() {
				if( $(this).val() != '' ){
					if(initEcInfo()) {
				   		selectEcStandardCategory("ecStandardSubCategory", $(this));
					} else {
						$("#ecStandardSmallCategory").val($("#prev-ecStandardSmallCategory").val());
					}
				}else{
					alert("카테고리를 다시 선택해 주세요.");
				}
			});
			$("#ecStandardSubCategory").change(function() {
				if( $(this).val() != '' ){
					if(initEcInfo()) {
						$("#prev-ecStandardSubCategory").val($("#ecStandardSubCategory").val());
						//addEcProductAttr('P', 'change');
					} else {
						$("#ecStandardSubCategory").val($("#prev-ecStandardSubCategory").val());
					}
				} else {
					alert("카테고리를 다시 선택해 주세요.");
				}
			});
			/* 200306 EC 전용 카테고리 추가 END */

			/* EDI 원래코드 --20170517
			//-----KC 인증마크 select 변경시 액션
			$("select[name=productCertSelectTitle]").change(function() {
				//if( $(this).val().replace(/\s/gi, '') != "" ) {
					selectBoxCertTemplateDetailList($(this).val().replace(/\s/gi, ''));
				//}
			});
			*/

			// KC 인증마크 select 변경시 액션
			$("select[name=productCertSelectTitle]").change(function() {
				//console.log($("event:$(select[name=productCertSelectTitle]).change"));
				$("select[name=productCertSelectDtlTitle]").val("");
				//$("#productCertSelectDtlTitle").hide();

				if( $(this).val() == 'KC001' ) 	//KC001 : 해당사항없음
					selectBoxCertTemplateDetailList($(this).val());
				else if( $(this).val() != '' ) {
					//selectCertTemplateDtlList($(this).val(), $(this).val());
//					selectBoxCertTemplateDetailList($(this).val());

					//20170518 변경
					//selectCertTemplateDtlList2($(this).val());
					//console.log("KC 인증마크 select 변경시 액션 $(this).val():"+$(this).val());
					//console.log("event:$(select[name=productCertSelectTitle]).change 안에서 dtlbox값:"+$("select[name=productCertSelectDtlTitle]").val());
					//selectBoxCertTemplateDetailList2($(this).val(), $(this).val());
					if($("select[name=productCertSelectDtlTitle]").val()==''){
						selectBoxCertTemplateDetailList2($(this).val().replace(/\s/gi, ''),'');
					}
					else{
						selectBoxCertTemplateDetailList2($(this).val().replace(/\s/gi, ''),$("select[name=productCertSelectDtlTitle]").val());
					}
				}

			});

			// KC 인증마크 상세 select 변경시 액션
			$("select[name=productCertSelectDtlTitle]").change(function() {
				//console.log("event:$(select[name=productCertSelectDtlTitle]).change");

				if( $(this).val() != '' ) {
					//alert($("select[name=productCertSelectTitle]").val());
					selectBoxCertTemplateDetailList3($("select[name=productCertSelectTitle]").val(), $(this).val());
					}
				//if( $(this).val() != '' )
				//	selectBoxCertTemplateDetailList2($("select[name=productCertSelectTitle]").val(), $(this).val());
				//console.log($("select[name=productCertSelectTitle]"));

			});

			//-----상품유형이 PB가 아닐경우 폐기물, 재활용 입력란 display:none
			_eventSetupJang($("#newProduct select[name=prodTypeDivnCd]").val().replace(/\s/gi, ''));


			//----- 라디오 버튼 Defalut 설정
			$(":radio[name='newProdPromoFg']:radio[value='']").attr("checked", true);				//신상품입점장려금적용구분 [미적용 Default]
			$(":radio[name='overPromoFg']:radio[value='']").attr("checked", true);					//성과초과장려금적용구분 [미적용 Default]
			$(":radio[name='newProdFirstPurDivnCd']:radio[value='0']").attr("checked", true);		//신규상품초도구분 [미적용 Default]
			$(":radio[name='totInspYn']:radio[value='']").attr("checked", true);					//전수검사여부 [미적용 Default]
			
			//250613 슈퍼 신상품장려금 관련 추가
			$(":radio[name='sNewProdPromoFg']:radio[value='']").attr("checked", true);				//(슈퍼) 신상품입점장려금적용구분 [미적용 Default]
			$(":radio[name='sOverPromoFg']:radio[value='']").attr("checked", true);					//(슈퍼) 성과초과장려금적용구분 [미적용 Default]


			//----- 계절구분 년도 현재년도로 default
			//var nowYear	=	"<c:out value='${nowYear}'/>";
			//$("#newProduct select[name='sesnYearCd']").val(nowYear);
			var saveYear = "<c:out value='${newProdDetailInfo.sesnYearCd}'/>";
			//----- 해당년도의 계절리스트 조회
			_eventSelectSesnList(saveYear);

			//-----계절년도 체인지 이벤트
			$("#newProduct select[name='sesnYearCd']").change(function() {
				_eventSelectSesnList($(this).val().replace(/\s/gi, ''));
			});


			//모든 input에 대해서 특수문자 경고표시하기
			$("input").keyup(function(){

				var value = $(this).val();
				var arr_char = new Array();

				arr_char.push("'");
				arr_char.push("\"");
				arr_char.push("<");
				arr_char.push(">");
				arr_char.push(";");


				for(var i=0 ; i<arr_char.length ; i++)
				{
					if(value.indexOf(arr_char[i]) != -1)
					{
						alert("[<, >, ', /, ;] <spring:message code='msg.product.input.specialCharacter'/>");
						value = value.substr(0, value.indexOf(arr_char[i]));

						$(this).val(value);
					}
				}
			});

			//-----온도구분 Default 10도로 설정
			$("select[name='tmprtDivnCd']").val("0");

			//-----혼재여부 단일상품 Default
			$(":radio[name='mixYn']:radio[value='0']").attr("checked", true);

			//-----센터유형구분이 상온이면 온도구분은 디폴트로 10도로
			/* $("select[name='ctrTypeDivnCd']").change(function() {
				if($(this).val() == "1") {
					$("select[name='tmprtDivnCd']").val("0");
				}
			}); */

			//-----상품범주 값이 변경되면 필수입력 메세지 삭제
			$("input[name=prodAttTypFg]").change(function() {
				deleteErrorMessageIfExist($("input[name='prodAttTypFg']"));
			});

			//-----무발주 매입구분이 변경되면 필수입력 메세지 삭제
			$("input[name=npurBuyPsbtDivnCd]").change(function() {
				deleteErrorMessageIfExist($("input[name='npurBuyPsbtDivnCd']"));
			});

			//-----수기등록 여부 필수입력 메세지 삭제
			$("input[name=mnlProdReg]").change(function() {
				deleteErrorMessageIfExist($("input[name='mnlProdReg']"));
			});

			// 센터유형, 온도구분 이벤트 핸들러_241119PIY
			document.getElementById("ctrTypeDivnCd").addEventListener('change', e => {
				e.stopPropagation();

				let tmprtDivnCdElem = document.getElementById('tmprtDivnCd');
				let tmprtDivnCdList = Array.from(tmprtDivnCdElem);

				if (e.target.value !== '1' && e.target.value  !== '3') {
					tmprtDivnCdList.forEach( tmprtDivnCdOption => {
						tmprtDivnCdOption.style.display = '';
					})
				}

				if (e.target.value === '1' || e.target.value  === '3' ) {
					tmprtDivnCdList.forEach( tmprtDivnCdOption => {
						tmprtDivnCdOption.style.display = '';
						if (tmprtDivnCdOption.value !== '0' && tmprtDivnCdOption.value !== '') {
							tmprtDivnCdOption.style.display = 'none';
						}
					})
				}
			})

			// 20180514 전산정보팀 이상구 추가 (체험형 상품 관련 이벤트 및 속성 제어)
			// 체험형 상품 기본값  N
			//체험형 상품 라디오 버튼 이벤트 정의
			$("input[name='exprProdYn']").change(function(){

				var checked_val = $("input[name='exprProdYn']:checked").val()
				// 체험형 상품 여부 1을 선택하였을 경우
				if(checked_val === '1') {
					$("input[name='sellerRecomm']").attr('disabled', false);
				}else if(checked_val === '0'){
					$("input[name='sellerRecomm']").attr('disabled', true);
					$("input[name='sellerRecomm']").val('');
					$("#recBytes").text('0');
				}
			});

			// 판매자 추천평 입력 이벤트 처리.
			var selRecInput = "";
			$("input[name='sellerRecomm']").on('input',function(){
				var inputVal = $(this).val();
				var tmpStr = new String(inputVal);
				var tmpStrLen = tmpStr.length;
				var oneChar;
				var tcount = 0;

				for(var i=0; i < tmpStrLen; i++){
					/* oneChar = tmpStr.charAt(i);
					if (escape(oneChar).length > 4)
					{
						tcount += 3;
					}
					else tcount ++;	 */
					oneChar = tmpStr.charCodeAt(i);
					if(oneChar > 128) tcount += 2;
					else tcount ++;
				}


				if(tcount > 100){
					alert('판매차 추천평은 100bytes 이상 입력 할 수 없습니다.');
					$(this).val(selRecInput);
					$(this).focus();
				}else{
					$("#recBytes").text(tcount);
					selRecInput = inputVal;
				}
			});
			// 20180514 전산정보팀 이상구 수정 끝.
			/////////////////////////////////////////////////////////////////////
			// 20181218 전산정보팀 이상구
			$("input[type=radio][name=ownStokFg]").change(function(){
				if($(this).val() == ''){
					$("#planRecvQty").val('');
					$("#ordDeadline").val('');
				}else if($(this).val() === 'X'){
					alert('개별계획구분 대상시 개별계획량, 계획종료일 입력 바랍니다.');
					$("#planRecvQty").focus();
				}
			});

			//////////////////////////////////
			//init grid
			initGrid_dispCtg();			//기본 전시 카테고리
			initGrid_ecOnDispCtg();		//EC 전시카테고리 (롯데 ON)
			initGrid_ecMallDispCtg();	//EC 전시카테고리 (롯데 몰)
			initGrid_zettaDispCtg();	//ZETTA 전시카테고리

			//ec카테고리 세팅
			selectEcStandardCategory("ecStandardLargeCategory");
			var ecCategory = '<c:out value="${ecCategory}" />';
			if(ecCategory != '') {
				setEcInfo();
			}
			
			//20250327 마트 차세대 추가
			//단축상품명 or 브랜드명 변경 시 쇼카드명 변경 setting
			$(document).on("keyup change", "#prodShortNm", function(){
				//쇼카드 상품명 셋팅 이벤트 호출
				fnSetShowCardNm();
			});
			
			//단축상품명 or 브랜드명 or 규격 변경 시 관리용 상품명 변경 setting
			$(document).on("keyup change", "#prodStandardNm, #prodShortNm", function(){
				//관리용 상품명 셋팅 이벤트 호출
				fnSetProdNm();
			});
			
			//채널 체크박스 변경 시, 원매가 정보 영역 활성/비활성
			$(document).on("change", "input[name='chanCd']", function(){
				var chked = $(this).is(":checked");
				var currVal = $.trim($(this).val());
				
				//원매가영역 활성화/비활성화처리
				fncSetPriceAreaByChan(currVal, chked);
				//온라인운영여부 변경
				fncSetVicOnlineCd();
				
				//오카도 가격copy 대상 채널 셋팅
				fncSetCopyPrcTargetChan();
				
				if(chked){
					//선택된 체크박스일 경우, 상품정보 영역 show
					$("#tblNewProdAdd tr[data-chan="+currVal+"]").show();
				}else{
					$("#tblNewProdAdd tr[data-chan="+currVal+"]").hide();
				}
				
			});
			
			//원매가 > 오카도 가격 Copy 체크박스 선택 시
			$(document).on("click", "#copyPrc", function(){
				//가격 Copy
				fncCopyPrc();
			});
			
			//오카도 외 채널 금액 변경 시,
			$(document).on("keyup change", "#tblNewProdPrc tr[data-chan!='KR09'] input,#tblNewProdPrc tr[data-chan!='KR09'] select", function(){
				//어떤 채널의 원매가정보인지 확인
				var chanCd = $.trim($(this).closest("tr").attr("data-chan"));
				//채널정보 없으면 return
				if(chanCd == "") return;
				
				//입력불가영역 (자동계산영역) 초기화
				$(this).closest("tr").find("input.inputRead").val("");
				
				//카피대상 채널이 아니면 return
				if(chanCd != copyPrcChanCd) return;
				
				//가격 Copy
				fncCopyPrc();
			});
			
			//오카도 채널 금액 변경 시,
			$(document).on("keyup change", "#tblNewProdPrc tr[data-chan='KR09'] input, #tblNewProdPrc tr[data-chan=KR09] select", function(){
				//카피용 체크박스면 return
				if($.trim(this.id) == "copyPrc") return;
				
				//입력불가영역 (자동계산영역) 초기화
				$(this).closest("tr").find("input.inputRead").val("");
				
				if(this.name != null && this.name != ""){
					//가격 Copy 체크해제
					$("#copyPrc").prop("checked", false);
				}
			});
			
			//(슈퍼) 신상품 장려금 적용여부 변경 event
			$(document).on("click", "input[name='sNewProdPromoFg']",function(){
				var value = $.trim($("input[name='sNewProdPromoFg']:checked").val()).toUpperCase();
				
				if(value == "X") fncSetsSNewProdStDy();			//(슈퍼) 신상품출시일자 기본값 셋팅
				else $("#sNewProdStDy").val("");				//(슈퍼) 신상품출시일자 빈값적용
			});
			
			//신상품 장려금 적용여부 변경 event
			$(document).on("click", "input[name='newProdPromoFg']",function(){
				var value = $.trim($("input[name='newProdPromoFg']:checked").val()).toUpperCase();
				
				//신상품장려금 미적용 시, 신상품 출시일자 초기화
				if(value == "") $("#newProdStDy").val("");
			});
			
			//200306 EC전용 카테고리 추가 END
			/*********************************** 여기서 부턴 상품등록이후 상품 상세정보 설정에 사용됩니다.*************************************************/

			//----- 상품의 상세정보 SeleceBox, RadioButton ValueSetting.[커스텀태그에서는 시큐어 코딩이 불가능 하여 스크립트에서 처리]
			if ('<c:out value="${newProdDetailInfo.pgmId}" />' != "") {
				_eventSetnewProdDetailInfoDetailValue();
				
				eventSearchGrid("dispCtg");			//기본 전시카테고리 조회
				eventSearchGrid("ecOnDispCtg");		//롯데 ON EC 전시카테고리 조회
				eventSearchGrid("ecMallDispCtg");	//롯데 몰 EC 전시카테고리 조회
				eventSearchGrid("zettaDispCtg");	//Zetta 전시카테고리 조회
				
				//24_NBM_OSP_CAT
// 				setTimeout(function(){selectOspDispCategoryList();},1000);
			}
			/*****************************************************************************************************************************/
			//[210127 PB상품등록 ]
			addOptionsForSelect();

			regKeyUpEvent();
			setProdShortNmByte();
			removeWeightUnitForOacdo();
		});

		function addOptionsForSelect(){

			var option_nottarget = "<option value=''>미대상</option>";
			$("#wasteFg").append(option_nottarget);
			$("#recycleFg1").append(option_nottarget);
			$("#recycleFg2").append(option_nottarget);
			$("#recycleFg3").append(option_nottarget);

		}

		/* 200306 EC전용 카테고리 추가 START */
		function doCategoryReset(){
			fncClearAllGridData("dispCtg");	//전시카테고리 초기화
	    }

		function selectEcStandardCategoryMapping() {

			var param = new Object();
			var targetUrl = '<c:url value="/edi/product/ecStandardCategoryMapping.do"/>';
			param.martCatCd = $("#l3Cd").val();

			var optionText = '';

			$.ajax({
				type: 'POST',
				url: targetUrl,
				async: false,
				data: param,
				success: function(data) {

					var stdCdMapping = data.data;

					if(stdCdMapping != null) {
						if(initEcInfo()){
							doCategoryReset();
							$("#prev-l3Cd").val($("#l3Cd").val());
							if(stdCdMapping.LRG_STD_CAT_CD != '' && stdCdMapping.LRG_STD_CAT_CD != null) {
								$("#prev-ecStandardLargeCategory").val(stdCdMapping.LRG_STD_CAT_CD);
								$("#ecStandardLargeCategory").val(stdCdMapping.LRG_STD_CAT_CD);
								$("#ecStandardLargeCategory").removeClass("default");
								selectEcStandardCategory("ecStandardMediumCategory", $("#ecStandardLargeCategory"));
								$("#ecStandardSmallCategory option").not("[value='']").remove();
								$("#ecStandardSubCategory option").not("[value='']").remove();
							}
							if(stdCdMapping.MID_STD_CAT_CD != '' && stdCdMapping.MID_STD_CAT_CD != null) {
								$("#prev-ecStandardMediumCategory").val(stdCdMapping.MID_STD_CAT_CD);
								$("#ecStandardMediumCategory").val(stdCdMapping.MID_STD_CAT_CD);
								$("#ecStandardMediumCategory").removeClass("default");
								selectEcStandardCategory("ecStandardSmallCategory", $("#ecStandardMediumCategory"));
								$("#ecStandardSubCategory option").not("[value='']").remove();
							}
							if(stdCdMapping.SML_STD_CAT_CD != '' && stdCdMapping.SML_STD_CAT_CD != null) {
								$("#prev-ecStandardSmallCategory").val(stdCdMapping.SML_STD_CAT_CD);
								$("#ecStandardSmallCategory").val(stdCdMapping.SML_STD_CAT_CD);
								$("#ecStandardSmallCategory").removeClass("default");
								selectEcStandardCategory("ecStandardSubCategory", $("#ecStandardSmallCategory"));
							}
							if(stdCdMapping.SUB_STD_CAT_CD != '' && stdCdMapping.SUB_STD_CAT_CD != null) {
								$("#prev-ecStandardSubCategory").val(stdCdMapping.SUB_STD_CAT_CD);
								$("#ecStandardSubCategory").val(stdCdMapping.SUB_STD_CAT_CD);
								$("#ecStandardSubCategory").removeClass("default");
								//addEcProductAttr('P', 'change');
							}
						} else {
							$("#ecStandardLargeCategory").val($("#prev-ecStandardLargeCategory").val());
							$("#ecStandardMediumCategory").val($("#prev-ecStandardMediumCategory").val());
							$("#ecStandardSmallCategory").val($("#prev-ecStandardSmallCategory").val());
							$("#ecStandardSubCategory").val($("#prev-ecStandardSubCategory").val());
							$("#l3Cd").val($("#prev-l3Cd").val());
						}

					}
				}
			});
	    }
		function selectEcStandardCategory(lowerCategory, category) {

			var param = new Object();
			var targetUrl = '<c:url value="/edi/product/ecStandardCategory.do"/>';
			if(typeof category != "undefined" ){
				param[category.attr('name')] = category.val();
				$("#prev-"+category.attr('name')).val(category.val());
			}

			var optionText = '';

			$.ajax({
				type: 'POST',
				url: targetUrl,
				async: false,
				data: param,
				success: function(data) {

					var list = data.list;

					for(var i=0; i<list.length; i++) {
						$("select[name="+lowerCategory+"]").show();
						if(i == 0) {
							if(list[i].DEPTH == 1) {
								optionText += '<option value="" class="default">대분류</option>';
							} else if(list[i].DEPTH == 2) {
								optionText += '<option value="" class="default">중분류</option>';
							} else if(list[i].DEPTH == 3) {
								optionText += '<option value="" class="default">소분류</option>';
							} else if(list[i].DEPTH == 4) {
								optionText += '<option value="" class="default">세분류</option>';
							}
						}
						optionText += '<option value="'+list[i].CAT_CD+'">'+list[i].CAT_NM+'</option>';
					}

					$("select[name="+lowerCategory+"]").html(optionText);

					if(list.length == 0) {
						$("select[name="+lowerCategory+"]").hide();
						//addEcProductAttr('P', 'change');
					}
				}
			});
		}

		//EC 표준 카테고리에 맞게 EC 전시카테고리 영역 control
		function initEcInfo() {
			let ecOnCnt = fncGetGridDataRowCount("ecOnDispCtg");		//롯데ON전시카테고리 데이터 카운트
			let ecMallCnt = fncGetGridDataRowCount("ecMallDispCtg");	//롯데몰전시카테고리 데이터 카운트
			
			if(ecOnCnt > 0 || ecMallCnt > 0
					|| $(":radio[name='attrPiType']").is(':checked') || $("select[name='ecProductAttr']").length > 0){
				if (!confirm("표준카테고리 변경시\nEC 전시카테고리&상품속성이 초기화됩니다.\n계속하시겠습니까?")) {
					return false;
				}

				//EC 전시카테고리 초기화
				fncClearAllGridData("ecOnDispCtg");		//롯데ON 전시카테고리 초기화
				fncClearAllGridData("ecMallDispCtg");	//롯데몰 전시카테고리 초기화

				// EC속성 복사 안된다.
				$("#hiddenForm input[name='ecCategoryKeepYn']").val("false");

				// 상품 속성 초기화
				$(":radio[name='attrPiType']").removeAttr("checked");
				$("#singleProductAttrDiv").html("<br/> <font color=blue style='font-size: 11px'>※ EC 표준 카테고리를 선택해 주세요.</font><br/>");
				var resetItemSubTable = '<tr><th style="width:45px">단품코드</th><th style="width:320px">옵션설명</th><th style="width:45px">재고여부</th><th style="width:100px">재고수량</th><th id="optnAmtTH" style="width:100px">가격</th><th></th></tr>';
				$("#itemSubTable").html(resetItemSubTable);
				$("#multiProductAttrDiv").css("display", "none");
				$("#singleProductAttrDiv").css("display", "block");
			}
			return true;
		}

		function setEcInfo() {
			setEcCategoryProduct();	//EC 표준카테고리
			//setEcAttribute();		//EC 속성
		}

		function setEcCategoryProduct() {

			var list = new Array();
			<c:forEach items="${ecCategory}" var="ecCategory" varStatus="status">
				<c:if test="${status.first}">
					<c:if test="${ecCategory.LRG_STD_CAT_CD ne ''}">
						$("#prev-l3Cd").val("<c:out value='${newProdDetailInfo.l3Cd}'/>");
						$("#prev-ecStandardLargeCategory").val("<c:out value='${ecCategory.LRG_STD_CAT_CD}'/>"); 
						$("#ecStandardLargeCategory").val("<c:out value='${ecCategory.LRG_STD_CAT_CD}'/>");
						$("#ecStandardLargeCategory").removeClass("default");
					    selectEcStandardCategory("ecStandardMediumCategory", $("#ecStandardLargeCategory"));
					    $("#ecStandardSmallCategory option").not("[value='']").remove();
					    $("#ecStandardSubCategory option").not("[value='']").remove();
					</c:if>
					<c:if test="${ecCategory.MID_STD_CAT_CD ne ''}">
						$("#prev-ecStandardMediumCategory").val("<c:out value='${ecCategory.MID_STD_CAT_CD}'/>"); 
						$("#ecStandardMediumCategory").val("<c:out value='${ecCategory.MID_STD_CAT_CD}'/>");
						$("#ecStandardMediumCategory").removeClass("default");
					    selectEcStandardCategory("ecStandardSmallCategory", $("#ecStandardMediumCategory"));
						$("#ecStandardSubCategory option").not("[value='']").remove();
					</c:if>
					<c:if test="${ecCategory.SML_STD_CAT_CD ne ''}">
						$("#prev-ecStandardSmallCategory").val("<c:out value='${ecCategory.SML_STD_CAT_CD}'/>");
						$("#ecStandardSmallCategory").val("<c:out value='${ecCategory.SML_STD_CAT_CD}'/>");
						$("#ecStandardSmallCategory").removeClass("default");
					    selectEcStandardCategory("ecStandardSubCategory", $("#ecStandardSmallCategory"));
					</c:if>
					<c:if test="${ecCategory.SUB_STD_CAT_CD ne ''}">
						$("#prev-ecStandardSubCategory").val("<c:out value='${ecCategory.SUB_STD_CAT_CD}'/>");
						$("#ecStandardSubCategory").val("<c:out value='${ecCategory.SUB_STD_CAT_CD}'/>");
						$("#ecStandardSubCategory").removeClass("default");
					</c:if>
				</c:if>
			</c:forEach>
		}
		/* 200306 EC전용 카테고리 추가 END */

		/* 소분류 변경시 표시단위, 표시수량 Default 셋팅 */
		function _eventSetDpBaseQty(l3Cd) {
			var paramInfo = {};

			paramInfo["l3Cd"]	=	l3Cd;

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectProdEssentialInfo.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.result != null) {

						var result = data.result;

						gDpBaseQty = result.dpBaseQty;
						gDpUnitCd = result.dpUnitCd;

						//console.log("gDpBaseQty ==" + gDpBaseQty);
						//console.log("gDpUnitCd ==" + gDpUnitCd);

						if (result.dpUnitCd != null) {
							$("select[name='dpUnitCd']").val(result.dpUnitCd);
							$("select[name='dpUnitCd']").attr("disabled", true);
						} else {
							$("select[name='dpUnitCd']").val("");
							$("select[name='dpUnitCd']").attr("disabled", false);
						}

						if (result.dpBaseQty != "0") {
							$("input[name='dpBaseQty']").val(result.dpBaseQty);
							$("input[name='dpBaseQty']").attr("disabled", true);
						} else {
							$("input[name='dpBaseQty']").val("");
							$("input[name='dpBaseQty']").attr("disabled", false);
						}

					} else {
						$("select[name='dpUnitCd']").val("");
						$("input[name='dpBaseQty']").val("");

						$("select[name='dpUnitCd']").attr("disabled", false);
						$("input[name='dpBaseQty']").attr("disabled", false);
					}
				}
			});
		}

		function _displayAllProdTypeDivnCd() {
			const prodTypeDivnCdElem = document.querySelector("#prodTypeDivnCd");

			for (let idx=0; idx<prodTypeDivnCdElem.length; idx++) {
				let selectedOption = prodTypeDivnCdElem[idx];
				selectedOption.style.display = "";
			}
		}

		function _eventCheckPbPartner(entpCd) {
			if (!entpCd) return;

			const options = {
				method : "POST",
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({"entpCd" : entpCd})
			}
			const apiurl = '<c:url value="/edi/product/checkPbPartner.json"/>'
			fetch(apiurl, options)
					.then(response => response.json())
					.then(result => {
						if (!result || result.setStatus == "fail") {
							return null;
						}

						let isPbPartner = "no";
						const prodTypeDivnCdElem = document.querySelector("#prodTypeDivnCd");
						if (!prodTypeDivnCdElem) return;
						if (result.isPbPartner === "PB_PARTNER") isPbPartner = "yes";

						if (prodTypeDivnCdElem.selectedIndex !== -1) {
							let selectedOption = prodTypeDivnCdElem.options[prodTypeDivnCdElem.selectedIndex];
							if (isPbPartner === 'yes' && selectedOption.text !== 'PB') {
								prodTypeDivnCdElem.value = "";
							}

							if (isPbPartner === 'no' && selectedOption.text === 'PB') {
								prodTypeDivnCdElem.value = "";
							}
						}

						for (let idx=0; idx<prodTypeDivnCdElem.length; idx++) {
							let selectedOption = prodTypeDivnCdElem[idx];
							selectedOption.style.display = "";

							if (isPbPartner === 'yes' && selectedOption.text !== '선택'
									&& selectedOption.text !== 'PB') {
								selectedOption.style.display = "none";
							}

							if (isPbPartner === 'no' && selectedOption.text !== '선택'
									&& selectedOption.text === 'PB') {
								selectedOption.style.display = "none";
							}
						}
					})
					.catch(error => {
						console.log("checkPbPartner.json>> " + error);
					})
		}


		/* 협력업체 선택시 이벤트 */
		function _eventSetEntpCdInfoCtrl(entpCd) {
			var errorNode = $("#newProduct select[name='entpCd']").prev("div[name=error_msg]").length;

			if( errorNode > 0 ) {
				$("#newProduct select[name='entpCd']").prev().remove();
			}
			_eventCheckPbPartner(entpCd)

			if(entpCd != '' ) {
				//거래중지된 업체인지 체크(commonProductFunction.jsp에 함수 있음)
				_eventCheckBlackListVendor(entpCd);

				// 2019.12.23 전산정보팀 이상구 추가
				// 신상품장려금 대상 파트너 여부 확인 (commonProductFunction.jsp에 함수 있음)
				_eventCheckNewPromoFg(entpCd);
				
				//협력업체별 채널 설정
				_eventSelVenZzorgInfo(entpCd);
			}else{
				//협력업체코드 없을 경우, 모든채널 비활성화
				_fncDeactiveAllChans();
			}

			//-----상품유형 초기화
			$("[name=prodTypeDivnCd] > option[value='']").attr("selected", "true");

			//-----온라인적용여부 영역 컨트롤
			fncSetVicOnlineCd();
		}
		/* 250403 마트 차세대 VIC여부 삭제
		function _eventSetMatchInfoCtrl(matCd) {
			var tradeType		=	$("#newProduct input[name='tradeType']").val();
			var norProdSalePrc	=	$("#newProduct input[name='norProdSalePrc']").val();
			var norProdPcost	=	$("#newProduct input[name='norProdPcost']").val();
			var prftRate		=	$("#newProduct input[name='prftRate']").val();
			//-----직매입이면
			if (tradeType == "1") {
				   //마트전용
				if (matCd=="1"){
					$("#newProduct input[name='wnorProdSalePrc']").val(norProdSalePrc);
					$("#newProduct input[name='wnorProdPcost']").val(norProdPcost);
					//$("#newProduct input[name='wprftRate']").val('');
				}
				else {
				}

			} else {

				if (matCd=="1"){
					$("#newProduct input[name='wnorProdSalePrc']").val(norProdSalePrc);
			//		$("#newProduct input[name='wnorProdPcost']").val('');
					$("#newProduct input[name='wprftRate']").val(prftRate);
				}
				else {
				}
			}
		}
		*/
// 		/* 단일상품일때만 판매코드 입력가능  이동빈 판매코드 필수*/
 		function _eventSetSellCdStat(val) {
 			//----- 판매코드 입력란 초기화
 			deleteErrorMessageIfExist($("input[name='sellCd']"));

 			var adminId			=	"<c:out value='${epcLoginVO.adminId}'/>";			//상품등록이후 조회된 상품의 협력업체 코드
 			//alert("adminId:"+adminId);
 			//alert("val:"+val);
 			if (adminId == "" || adminId == "online") {
 			if (val == "00") {
 	 			//	alert("단일상품 선택시 판매(88)코드는 아래 판매코드를 입력해주세요!");
 			//필수	$("input[name='sellCd']").parent().prev().find("span.star").show();
 			//필수	$("input[name='sellCd']").addClass("required").removeAttr("readonly");
 			} else {
 				//alert("묶음 상품일 경우 판매코드 입력은 변형속성 탭에서 가능합니다.");
 	 			//	alert("묶음상품 선택시 판매(88)코드는 기본정보 저장 후 상단에 상품속성탬에서 판매코드를 입력해주세요");
 				$("input[name='sellCd']").val("");
 				$("input[name='sellCd']").parent().prev().find("span.star").hide();
 				$("input[name='sellCd']").parent().prev().css("font-weight","normal");
 				$("input[name='sellCd']").removeClass("required").attr("readonly", true);
 			}
 	 		} else {
 				if (val == "00") {
 				//	alert("단일상품 선택시 판매(88)코드는 아래 판매코드를 입력해주세요!");
 	 			//	$("input[name='sellCd']").addClass("required").removeAttr("readonly");
 	 			} else {
 	 				//alert("묶음 상품일 경우 판매코드 입력은 변형속성 탭에서 가능합니다.");
 	 			//	alert("묶음상품 선택시 판매(88)코드는 기본정보 저장 후 상단에 상품속성탬에서 판매코드를 입력해주세요");
 	 				$("input[name='sellCd']").val("");
 	 				$("input[name='sellCd']").removeClass("required").attr("readonly", true);
 	 			}
 			}

 		}

 		/* 180724 전시카테고리 추가*/
 		function addCategory() {
 			if ($("#l3Cd").val() == "") {
 				alert("분류코드를 선택 하세요.");
 				$("#l3Cd").focus();
 				return;
 			}
 			
 			var gridGbn = "dispCtg";
 			var gridId = "tabList_"+gridGbn;
			var blankRowId = "blankRow_"+gridGbn;
 			
 			//조회결과 없음 행 제외 datalist 확인
			var dataRows = $("#"+gridId).jqGrid('getDataIDs').filter(v=>v != blankRowId);
			var rowCount = dataRows.length || 0;
			
			//전시카테고리 최대 3개까지 지정 가능
			if(rowCount > 3){
				alert("최대 3개 까지 지정 가능합니다.");
				return;
			}
			
			var targetUrl = '<c:out value="${ctx}"/>/edi/product/onlineDisplayCategory.do?closeFlag=1&catCd='
					+ $("#l3Cd").val();//01:상품
			Common.centerPopupWindow(targetUrl, 'epcCategoryPopup', {
				width : 460,
				height : 455
			});
 		}

 		/* 200306 EC전용 카테고리 추가 START */
 		/* EC 전시카테고리 추가*/
		function addEcStandardCategory(mallCd, isMart){
			var stdCatCd = getEcStandardCategoryId();

			if(stdCatCd == '') {
				$(":radio[name='attrPiType']").prop("checked", false);
				alert("EC 표준 카테고리를 선택 하세요.");
				return;
			}
			
			var gridGbn = "";
			
			if(isMart == "Y"){
				gridGbn = "ecMallDispCtg";
			}else{
				gridGbn = "ecOnDispCtg";
			}
			
			var gridId = "tabList_"+gridGbn;
			var blankRowId = "blankRow_"+gridGbn;
			
			//조회결과 없음 행 제외 datalist 확인
			var dataRows = $("#"+gridId).jqGrid('getDataIDs').filter(v=>v != blankRowId);
			var rowCount = dataRows.length || 0;

			var targetUrl = '<c:out value="${ctx}"/>/edi/product/ecDisplayCategoryPop.do?stdCatCd='+stdCatCd+'&mallCd='+mallCd+'&isMart='+isMart;
			Common.centerPopupWindow(targetUrl, 'epcCategoryPopup', {width : 460, height : 455});
		}

		/* EC 표준 카테고리 확인(SelectBox)*/
		function getEcStandardCategoryId() {

			var stdCatCd = '';

			if($("#ecStandardLargeCategory").val()) {
				stdCatCd = $("#ecStandardLargeCategory").val()
			}
			if($("#ecStandardMediumCategory").val()) {
				stdCatCd = $("#ecStandardMediumCategory").val()
			}
			if($("#ecStandardSmallCategory").val()) {
				stdCatCd = $("#ecStandardSmallCategory").val()
			}
			if($("#ecStandardSubCategory").val()) {
				stdCatCd = $("#ecStandardSubCategory").val()
			}

			if(($("#ecStandardLargeCategory option").size() > 0 && !$("#ecStandardLargeCategory").val())
			|| ($("#ecStandardMediumCategory option").size() > 0 && !$("#ecStandardMediumCategory").val())
			|| ($("#ecStandardSmallCategory option").size() > 0 && !$("#ecStandardSmallCategory").val())
			|| ($("#ecStandardSubCategory option").size() > 0 && !$("#ecStandardSubCategory").val())) {
				stdCatCd = '';
			}

			return stdCatCd;
		}

	    // 기본 전시 카테고리 검색창으로 부터 받은 카테고리 정보 입력
	    function setCategoryInto(asCategoryId, asCategoryNm, displayFlag, fullCategoryNm) // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
	    {
	    	
	   		var gridGbn = "dispCtg";
	    	var gridId = "tabList_"+gridGbn;
			var blankRowId = "blankRow_"+gridGbn;
	    	
			var allRows = $("#"+gridId).jqGrid('getDataIDs');		//전체 datalist
			var dataRows = allRows.filter(v=>v != blankRowId);				//조회결과 없음 행 제거
			var rowCount = dataRows.length || 0;
			
			//조회결과 없음 행 삭제
			if(allRows[0] == blankRowId){
				$("#"+gridId).jqGrid('delRowData', blankRowId);
			}
			
			//전시카테고리 최대 3개까지 지정 가능
			if(rowCount == 3){
				alert("최대 3개 까지 지정 가능합니다.");
				return;
			}
			
			var duplList = dataRows.filter(v => {
				var rowData = $("#"+gridId).jqGrid("getRowData", v);
				return rowData.CATEGORY_ID == asCategoryId;
			});
			if(duplList != null && duplList.length > 0){
				alert("이미 추가된 카테고리 입니다.");
				return;
			}
			
			var rowIdx = "new_"+gridRowIdx++;
			
			var dataInfo = {};
			dataInfo.FULL_CATEGORY_NM	= fullCategoryNm;		//전체카테고리명
			dataInfo.CATEGORY_ID		= asCategoryId;			//카테고리아이디
			dataInfo.DISP_YN			= displayFlag;			//전시여부
			
			//행추가
			$("#"+gridId).jqGrid('addRowData', rowIdx, dataInfo, 'last');
			
	        selectL4ListForOnline(asCategoryId);
	    }

	    //EC 전시카테고리 검색창으로 부터 받은 카테고리 정보 입력
	    function setEcDisplayCategory(dispCatCd, dispCatNm, dispYn, isMart){
			var gridGbn = "";
			
			if(isMart == "true"){
				gridGbn = "ecMallDispCtg";
			}else{
				gridGbn = "ecOnDispCtg";
			}
			
			var gridId = "tabList_"+gridGbn;
			var blankRowId = "blankRow_"+gridGbn;
	    	
			var allRows = $("#"+gridId).jqGrid('getDataIDs');		//전체 datalist
			var dataRows = allRows.filter(v=>v != blankRowId);		//조회결과 없음 행 제거
			var rowCount = dataRows.length || 0;
			
			//조회결과 없음 행 삭제
			if(allRows[0] == blankRowId){
				$("#"+gridId).jqGrid('delRowData', blankRowId);
			}
			
			var duplList = dataRows.filter(v => {
				var rowData = $("#"+gridId).jqGrid("getRowData", v);
				return rowData.DISP_CAT_CD == dispCatCd;
			});
			
			if(duplList != null && duplList.length > 0){
				alert("이미 추가된 카테고리 입니다.");
				return;
			}

			var rowIdx = "new_"+gridRowIdx++;
			
			var dataInfo = {};
			dataInfo.DISP_CAT_NM	= dispCatNm;		//전체카테고리명
			dataInfo.DISP_CAT_CD	= dispCatCd;		//카테고리아이디
			dataInfo.DISP_YN		= dispYn;			//전시여부
			
			//행추가
			$("#"+gridId).jqGrid('addRowData', rowIdx, dataInfo, 'last');
	    }

		/* EC전시카테고리 삭제*/
		function delEcDisplayCategory(isMart){
	    	if(isMart == "true") {
	    		fncDelGridRow("ecMallDispCtg");
	    	} else {
	    		fncDelGridRow("ecOnDispCtg");
	    	}
		}

 		/* 180724 전시카테고리 삭제*/
 		function delCategory() {
 			fncDelGridRow("dispCtg");
 		}

 		//온라인 전시 카테고리 값을 가지고
		//TCA_CATEGORY_MAPPING, TCA_MD_CATEGORY에 하위 목록 조회
		function selectL4ListForOnline(onlineDisplayCategory) {
			$.getJSON("<c:out value='${ctx}'/>/edi/product/selectL4ListForOnline.do",
			{
				detailCode: onlineDisplayCategory
			}, function(j){
		      var options = "<option value=''>선택</option>";
		      for (var i = 0; i < j.length; i++) {
		        options += '<option value="' + j[i].categoryCode + '">' + j[i].categoryName +'</option>';
		      }

		      $("#l4GroupCode option").remove();
		      $("#l4GroupCode").html(options);
		      if(j.length < 1){
		      	alert('카테고리에 맵핑된 소분류 데이터가 없습니다. \n담당 MD에게 문의하세요.');
		      }
			});
		}

		//Zetta 전시카테고리 검색창으로 부터 받은 카테고리 정보 입력
        function setOspDisplayCategory(ospCatNmParams, ospCatCdParams, ospRepParams, ospDispYnParams)
        {
        	var gridId = "tabList_zettaDispCtg";
			var blankRowId = "blankRow_zettaDispCtg";
			
			var allRows = $("#"+gridId).jqGrid('getDataIDs');		//전체 datalist
			var dataRows = allRows.filter(v=>v != blankRowId);		//조회결과 없음 행 제거
			var rowCount = dataRows.length || 0;
			
			//전체 행 삭제
			$("#"+gridId).jqGrid('clearGridData');
			
			
			let rowIdx = "";

            let ospCatCdList = ospCatCdParams.split("^");
            let ospCatNmList = ospCatNmParams.split("^");
            let ospRepList = ospRepParams.split("^");
            let ospDispYnList = ospDispYnParams.split("^");

            for (let idx=0; idx<ospCatCdList.length-1; idx++) {
            	var dataInfo = {};
            	rowIdx = "new_"+gridRowIdx++;	//행 idx
            	
            	dataInfo.OSP_CATEGORY_FULL_NM	= ospCatNmList[idx];		//전체카테고리명
    			dataInfo.OSP_CATEGORY_ID		= ospCatCdList[idx];		//카테고리아이디
    			dataInfo.OSP_CATEGORY_REP		= ospRepList[idx];			//대표카테고리여부
    			dataInfo.OSP_CATEGORY_DISP_YN	= ospDispYnList[idx];		//전시여부
    			
    			//행추가
    			$("#"+gridId).jqGrid('addRowData', rowIdx, dataInfo, 'last');
            }
        }

		//Zetta 전시카테고리 등록팝업 오픈 시 parameter 생성 (현재 선택된 카테고리정보)
        function getSelectedOspCatId() {
        	var gridId = "tabList_zettaDispCtg";
			var blankRowId = "blankRow_zettaDispCtg";
			
			var allRows = $("#"+gridId).jqGrid('getDataIDs');		//전체 datalist
			var dataRows = allRows.filter(v=>v != blankRowId);		//조회결과 없음 행 제거
			var rowCount = dataRows.length || 0;
			
			if(rowCount === 0) return null;
			
            let ospCatId = "";		//선택된 전시카테고리 아이디 full
            const OSP_CAT_SEP = "_SEP_";			//구분자
            const OSP_REP_NAME = "OSPCATREP";		//대표카테고리구분자
            
            for(let ospIdx = 0; ospIdx < dataRows.length; ospIdx++){
            	//rowData 조회
				var rowData = $("#"+gridId).jqGrid('getRowData', dataRows[ospIdx]);
            	
				ospCatId += $.trim(rowData.OSP_CATEGORY_ID);
				
				if($.trim(rowData.OSP_CATEGORY_REP) === "Y"){
					ospCatId += OSP_REP_NAME;
				}
				
				ospCatId += OSP_CAT_SEP;
            }

            return [ospCatId];
        }

		//선택된 EC 표준 카테고리 전체 데이터 추출
        function getSelectedFullCategory() {
            let ecLargeCatElem  = document.getElementById("ecStandardLargeCategory");
            let ecLargeCatName = ecLargeCatElem.selectedIndex === -1 ? "" : ecLargeCatElem.options[ecLargeCatElem.selectedIndex].text;

            let ecMediumCatElem = document.getElementById("ecStandardMediumCategory");
            let ecMediumCatName = ecMediumCatElem.selectedIndex === -1 ? "" : ecMediumCatElem.options[ecMediumCatElem.selectedIndex].text;

            let ecSmallCatElem  = document.getElementById("ecStandardSmallCategory");
            let ecSmallCatName = ecSmallCatElem.selectedIndex === -1 ? "" : ecSmallCatElem.options[ecSmallCatElem.selectedIndex].text;

            let ecSubCatElem    = document.getElementById("ecStandardSubCategory");
            let ecSubCatName = ecSubCatElem.selectedIndex === -1 ? "" : ecSubCatElem.options[ecSubCatElem.selectedIndex].text;

            let ecCategoryFullName =
                ecSubCatName !== "" ? `${ "${ecLargeCatName}" }_${ "${ecMediumCatName}" }_${ "${ecSmallCatName}" }_${ "${ecSubCatName}" }` :
                    ecSmallCatName !== "" ? `${ "${ecLargeCatName}" }_${ "${ecMediumCatName}" }_${ "${ecSmallCatName}" }` :
                        ecMiddleCatName !== "" ? `${ "${ecLargeCatName}" }_${ "${ecMediumCatName}"}` :
                            ecLargetCatName !== "" ? `${ "${ecLargeCatName}" }` : "";

            return ecCategoryFullName;
        }

        //24NBM_01_OSP카테고리_addOspCategory
        function addOspCategory() {
            if (!$("#l3Cd").val()) {
                alert("분류코드를 선택 하세요.");
                $("#l3Cd").focus();
                return;
            }
            
            var gridId = "tabList_zettaDispCtg";
			var blankRowId = "blankRow_zettaDispCtg";
			
 			//조회결과 없음 행 제외 datalist 확인
			var dataRows = $("#"+gridId).jqGrid('getDataIDs').filter(v=>v != blankRowId);
			var rowCount = dataRows.length || 0;

            if (rowCount < 99) {
                let selectedFullCategory = getSelectedFullCategory();		//현재 선택된 ec 표준 카테고리 정보
                let selectedOspCatId = getSelectedOspCatId();				//현재 선택된 zetta 전시카테고리 정보

                let targetUrl = '<c:out value="${ctx}"/>/edi/product/ospDisplayCategoryPop.do?closeFlag=1&l3Cd='
                    + $("#l3Cd").val() + `&ecCategoryFullName=${ "${selectedFullCategory}" }&ospCatId=${ "${selectedOspCatId}" }`;
                    
				Common.centerPopupWindow(targetUrl, 'epcOspCategoryPopup', {
                    width : 670,
                    height : 400
                });
            } else {
                alert("최대 98개 까지 지정 가능합니다.");
            }

        }

        //Zetta 전시카테고리 삭제
        function delOspCategory() {
        	fncDelGridRow("zettaDispCtg");
        }
        <!-- 24NBM_OSP_CAT E-->

		/* 상품상세정보 설정 */
		function _eventSetnewProdDetailInfoDetailValue() {
			//var zzNewProdFg			=	"<c:out value='${newProdDetailInfo.zzNewProdFg}'/>";			//신상품구분[1:순수신상품, 2:시즌상품, 3:행사상품, 4:라인확장]
			var prodDivnCd			=	"<c:out value='${newProdDetailInfo.prodDivnCd}'/>";				//상품구분[1:규격, 5:패션]
			var entpCd				=	"<c:out value='${newProdDetailInfo.entpCd}'/>";					//협력업체
			var teamCd				=	"<c:out value='${newProdDetailInfo.teamCd}'/>";					//팀코드
			//	var l1Cd				=	"<c:out value='${newProdDetailInfo.l1Cd}'/>";					//대분류
			//	var l3Cd				=	"<c:out value='${newProdDetailInfo.l3Cd}'/>";					//소분류
			var taxatDivnCd			=	"<c:out value='${newProdDetailInfo.taxatDivnCd}'/>";			//면과세구분
			var dpUnitCd			=	"<c:out value='${newProdDetailInfo.dpUnitCd}'/>";				//표시단위
// 			var matCd				=	"<c:out value='${newProdDetailInfo.matCd}'/>";					//VIC여부
			var purUnitCd			=	"<c:out value='${newProdDetailInfo.purUnitCd}'/>";				//발주단위코드
			var prodTypeDivnCd		=	"<c:out value='${newProdDetailInfo.prodTypeDivnCd}'/>";			//상품유형코드
			var vicOnlineCd			=	"<c:out value='${newProdDetailInfo.vicOnlineCd}'/>";				//20161011 vic온라인여부
			var newProdPromoFg		=	"<c:out value='${newProdDetailInfo.newProdPromoFg}'/>";			//신상품장려금구분
			var overPromoFg			=	"<c:out value='${newProdDetailInfo.overPromoFg}'/>";			//성과초과장려금구분
			var flowDdMgrCd			=	"<c:out value='${newProdDetailInfo.flowDdMgrCd}'/>";			//유통일관리여부
			var npurBuyPsbtDivnCd	=	"<c:out value='${newProdDetailInfo.npurBuyPsbtDivnCd}'/>";		//무발주매입가능구분
			var newProdFirstPurDivnCd	=	"<c:out value='${newProdDetailInfo.newProdFirstPurDivnCd}'/>";		//신규상품초도구분
			var mixYn				=	"<c:out value='${newProdDetailInfo.mixYn}'/>";					//혼재여부
			var totInspYn			=	"<c:out value='${newProdDetailInfo.totInspYn}'/>";				//전수검사여부
			var protectTagDivnCd	=	"<c:out value='${newProdDetailInfo.protectTagDivnCd}'/>";		//도난방지태그구분
			var protectTagTypeCd	=	"<c:out value='${newProdDetailInfo.protectTagTypeCd}'/>";		//도난방지태그유형
			var ctrTypeDivnCd		=	"<c:out value='${newProdDetailInfo.ctrTypeDivnCd}'/>";			//센터유형구분
			var tmprtDivnCd			=	"<c:out value='${newProdDetailInfo.tmprtDivnCd}'/>";			//온도구분
			var homeCd				=	"<c:out value='${newProdDetailInfo.homeCd}'/>";					//원산지
			var sesnDivnCd			=	"<c:out value='${newProdDetailInfo.sesnDivnCd}'/>";				//계절구분
			var ecoYn				=	"<c:out value='${newProdDetailInfo.ecoYn}'/>";					//친환경인증여부
			var ecoNm				=	"<c:out value='${newProdDetailInfo.ecoNm}'/>";					//친환경인증분류명
			var dlvGa				=	"<c:out value='${newProdDetailInfo.dlvGa}'/>";					//차등배송비여부
			var insCo				=	"<c:out value='${newProdDetailInfo.insCo}'/>";					//별도설치비유무
			var wasteFg				=	"<c:out value='${newProdDetailInfo.wasteFg}'/>";				//폐기물유형
			var	recycleFg1			=	"<c:out value='${newProdDetailInfo.recycleFg1}'/>";				//재활용 유형1
			var	recycleFg2			=	"<c:out value='${newProdDetailInfo.recycleFg2}'/>";				//재활용 유형2
			var	recycleFg3			=	"<c:out value='${newProdDetailInfo.recycleFg3}'/>";				//재활용 유형3
			var	wnorProdCurr		=	"<c:out value='${newProdDetailInfo.wnorProdCurr}'/>";			//VIC정상원가단위
			var	wnorProdSaleCurr	=	"<c:out value='${newProdDetailInfo.wnorProdSaleCurr}'/>";		//VIC정상매가단위
			var	norProdCurr			=	"<c:out value='${newProdDetailInfo.norProdCurr}'/>";			//정산원가단위
			var norProdSaleCurr		=	"<c:out value='${newProdDetailInfo.norProdSaleCurr}'/>";		//정산매가단위
			var	weightUnit			=	"<c:out value='${newProdDetailInfo.weightUnit}'/>";				//무게단위
			var	sizeUnit			=	"<c:out value='${newProdDetailInfo.sizeUnit}'/>";				//사이즈단위
			var	l3Cd				=	"<c:out value='${newProdDetailInfo.l3Cd}'/>";					//소분류
			var	l2Cd				=	"<c:out value='${newProdDetailInfo.l2Cd}'/>";					//중분류
			var	l1Cd				=	"<c:out value='${newProdDetailInfo.l1Cd}'/>";					//대븐류
			var prodAttTypFg		=	"<c:out value='${newProdDetailInfo.prodAttTypFg}'/>";			//상품범주
			var	infoGrpCd			=	"<c:out value='${newProdDetailInfo.infoGrpCd}'/>";				//전상법 코드
			var infoGrpCd2			=	"<c:out value='${newProdDetailInfo.infoGrpCd2}'/>";				//KC인증 코드
			var infoGrpCd3			=	"<c:out value='${newProdDetailInfo.infoGrpCd3}'/>";				//KC인증 상세 코드(20170518추가)
			var sesnYearCd			=	"<c:out value='${newProdDetailInfo.sesnYearCd}'/>";				//계절년도
			var trdTypeDivnCd		=	"<c:out value='${newProdDetailInfo.trdTypeDivnCd}'/>"			//거래유형
			var sellCd				=	"<c:out value='${newProdDetailInfo.sellCd}'/>"					//판매코드
			var newProdStDy			=	"<c:out value='${newProdDetailInfo.newProdStDy}'/>"				//신상품 출시일자
			var stgePsbtDd			=	"<c:out value='${newProdDetailInfo.stgePsbtDd}'/>"				//입고가능일
			var pickPsbtDd			=	"<c:out value='${newProdDetailInfo.pickPsbtDd}'/>"				//출고가능일
			var plasticWeight		=	"<c:out value='${newProdDetailInfo.plasticWeight}'/>"			//플라스틱 무게
			var grpCd				=	"<c:out value='${newProdDetailInfo.grpCd}'/>"					//구룹분석코드
			var hscd				=	"<c:out value='${newProdDetailInfo.hscd}'/>"
			var decno				=	"<c:out value='${newProdDetailInfo.decno}'/>"
			var tarrate				=	"<c:out value='${newProdDetailInfo.tarrate}'/>"
			var exprProdYn			=	"<c:out value='${newProdDetailInfo.exprProdYn}'/>" == "true" ? "1" : "0"
			var sellerRecomm		=	"<c:out value='${newProdDetailInfo.sellerRecomm}'/>"
			var ownStokFg			=	"<c:out value='${newProdDetailInfo.ownStokFg}'/>"				// 개별계획구분
			var ordDeadline			=	"<c:out value='${newProdDetailInfo.ordDeadline}'/>"				// 계획종료일
			var mnlProdReg			=	"<c:out value='${newProdDetailInfo.mnlProdReg}'/>"					// 수기등록 여부
			var sType				=	"<c:out value='${newProdDetailInfo.sType}'/>"					// 상품 소싱유형
			var ecAttrRegYn			=	"<c:out value='${newProdDetailInfo.ecAttrRegYn}'/>"				// EC속성 등록 유무

			var chanCd				=	"<c:out value='${newProdDetailInfo.chanCd}'/>";					// 채널코드
			var	snorProdCurr		=	"<c:out value='${newProdDetailInfo.snorProdCurr}'/>";			// 슈퍼 정상원가단위
			var	snorProdSaleCurr	=	"<c:out value='${newProdDetailInfo.snorProdSaleCurr}'/>";		// 슈퍼 정상매가단위
			var	onorProdCurr		=	"<c:out value='${newProdDetailInfo.onorProdCurr}'/>";			// 오카도 정상원가단위
			var onorProdSaleCurr	=	"<c:out value='${newProdDetailInfo.onorProdSaleCurr}'/>";		// 오카도 정상매가단위
			
			var sNewProdPromoFg		=	"<c:out value='${newProdDetailInfo.sNewProdPromoFg}'/>";		//(슈퍼) 신상품장려금구분
			var sOverPromoFg		=	"<c:out value='${newProdDetailInfo.sOverPromoFg}'/>";			//(슈퍼) 성과초과장려금구분
			var sNewProdStDy		=	"<c:out value='${newProdDetailInfo.sNewProdStDy}'/>";			//(슈퍼) 신상품 출시일자
			
// 			_eventSetupMatcdVicOnlineCd(matCd) //vic전용 온라인여부
			//-----SelectBox Value
			$("#newProduct select[name='prodDivnCd']").val(prodDivnCd);
			$("#newProduct select[name='sType']").val(sType);
			_eventSetupFieldByProductDivnCode(prodDivnCd);
		    modifyFontweight("productDivnCode",prodDivnCd);

			//----- 팀코드 selectBox에 셋팅해주고  팀의 대분류 조회
			$("#newProduct select[name='teamCd']").val(teamCd);
			_eventSelectL1List(teamCd);

			//----- 대븐류 selectBox에 셋팅해주고 대분류의 중분류 조회, 전상법, KC인증 조회
			$("#newProduct select[name='l1Cd']").val(l1Cd);
			//commerceChange(teamCd, l1Cd, infoGrpCd, infoGrpCd2);
			//console.log("commerceChange2 호출 20170518");
			commerceChange2(teamCd, l1Cd, infoGrpCd, infoGrpCd2, infoGrpCd3); //20160518변경

			//----- 중분류 selectBox 셋팅해주고 중분류의 소분류 조회
			$("#newProduct select[name='l2Cd']").val(l2Cd);
			_eventSelectL3List(teamCd, l1Cd, l2Cd);

			//----- 상품유형 셋팅하고 유형벌 동적 스크립트 호출
			$("#newProduct select[name='prodTypeDivnCd']").val(prodTypeDivnCd);
			_eventSetupJang(prodTypeDivnCd);

			$("#newProduct select[name='l3Cd']").val(l3Cd);
			 _eventSelectGrpList(l3Cd);
				//alert("grpCd:"+grpCd);
			$("#newProduct select[name='grpCd']").val(grpCd);
			$("#newProduct select[name='taxatDivnCd']").val(taxatDivnCd);
			$("#newProduct select[name='dpUnitCd']").val(dpUnitCd);
// 			$("#newProduct select[name='matCd']").val(matCd);
			$("#newProduct select[name='purUnitCd']").val(purUnitCd);

			$("#newProduct select[name='flowDdMgrCd']").val(flowDdMgrCd);
			if (flowDdMgrCd == "0") {

				//필수입력 class해제..
				$("#flowDd").removeClass("required");
				$("#stgePsbtDd").removeClass("required");
				$("#pickPsbtDd").removeClass("required");

				$("div.flowDate").hide();
				$("div.noFlowDate").show();
			} else {

				//유통일 입력란 필수입력 마크 show
				$("#flowDd").parent().parent().prev().find("span.star").show();
				$("#flowDd").parent().parent().prev().css("font-weight","bolder");

				//입고가능일, 출고가능일 필수입력 마크 show
				$("#stgePsbtDd").parent().parent().prev().find("span.star").show();
				$("#stgePsbtDd").parent().parent().prev().css("font-weight","bolder");

				$("#pickPsbtDd").parent().parent().prev().find("span.star").show();
				$("#pickPsbtDd").parent().parent().prev().css("font-weight","bolder");


				//필수입력 class 설정
				$("#flowDd").addClass("required");
				$("#stgePsbtDd").addClass("required");
				$("#pickPsbtDd").addClass("required");

				//유통일, 입고가능일, 출고가능일 display show
				$("div.flowDate").show();
				$("div.noFlowDate").hide();
			}

			$("#newProduct select[name='protectTagDivnCd']").val(protectTagDivnCd);
			$("#newProduct select[name='protectTagTypeCd']").val(protectTagTypeCd);
			$("#newProduct select[name='ctrTypeDivnCd']").val(ctrTypeDivnCd);
			$("#newProduct select[name='tmprtDivnCd']").val(tmprtDivnCd);
			$("#newProduct select[name='sesnDivnCd']").val(sesnDivnCd);
			$("#newProduct select[name='ecoNm']").val(ecoNm);
			$("#newProduct select[name='homeCd']").val(homeCd);
			$("#newProduct select[name='wasteFg']").val(wasteFg);
			$("#newProduct select[name='recycleFg1']").val(recycleFg1);
			$("#newProduct select[name='recycleFg2']").val(recycleFg2);
			$("#newProduct select[name='recycleFg3']").val(recycleFg3);
			$("#newProduct select[name='sType']").val(sType); // 20190627 전산정보팀 이상구 소싱유형 추가.

			//정상원매가 단위 셋팅 START ------------------------------
			//마트_원가단위
			if (wnorProdCurr == null || wnorProdCurr.length <= 0) {
				$("#newProduct select[name='wnorProdCurr']").val("KRW");
			} else {
				$("#newProduct select[name='wnorProdCurr']").val(wnorProdCurr);
			}
			//마트_매가단위
			if (wnorProdSaleCurr == null || wnorProdSaleCurr.length <= 0) {
				$("#newProduct select[name='wnorProdSaleCurr']").val("KRW");
			} else {
				$("#newProduct select[name='wnorProdSaleCurr']").val(wnorProdSaleCurr);
			}
			//maxx_원가단위
			if (norProdCurr == null || norProdCurr.length <= 0) {
				$("#newProduct select[name='norProdCurr']").val("KRW");
			} else {
				$("#newProduct select[name='norProdCurr']").val(norProdCurr);
			}
			//maxx_매가단위
			if (norProdSaleCurr == null || norProdSaleCurr.length <= 0) {
				$("#newProduct select[name='norProdSaleCurr']").val("KRW");
			} else {
				$("#newProduct select[name='norProdSaleCurr']").val(norProdSaleCurr);
			}
			//슈퍼_원가단위
			if (snorProdCurr == null || snorProdCurr.length <= 0) {
				$("#newProduct select[name='snorProdCurr']").val("KRW");
			} else {
				$("#newProduct select[name='snorProdCurr']").val(snorProdCurr);
			}
			//슈퍼_매가단위
			if (snorProdSaleCurr == null || snorProdSaleCurr.length <= 0) {
				$("#newProduct select[name='snorProdSaleCurr']").val("KRW");
			} else {
				$("#newProduct select[name='snorProdSaleCurr']").val(snorProdSaleCurr);
			}
			//오카도_원가단위
			if (onorProdCurr == null || onorProdCurr.length <= 0) {
				$("#newProduct select[name='onorProdCurr']").val("KRW");
			} else {
				$("#newProduct select[name='onorProdCurr']").val(onorProdCurr);
			}
			//오카도_매가단위
			if (onorProdSaleCurr == null || onorProdSaleCurr.length <= 0) {
				$("#newProduct select[name='onorProdSaleCurr']").val("KRW");
			} else {
				$("#newProduct select[name='onorProdSaleCurr']").val(onorProdSaleCurr);
			}
			//정상원매가 단위 셋팅 END ------------------------------
			$("#newProduct select[name='weightUnit']").val(weightUnit);
			$("#newProduct select[name='sizeUnit']").val(sizeUnit);



			$("#newProduct select[name='l3Cd']").val(l3Cd);
			$("#newProduct select[name='grpCd']").val(grpCd);
			//$("#newProduct select[name='sesnYearCd']").val(sesnYearCd);
			//$("#newProduct select[name='zzNewProdFg']").val(zzNewProdFg);

			if(sesnDivnCd == "0002" || sesnDivnCd == "0005"){
				$("#newProduct select[name='sesnYearCd']").val("");
			}
			else{
				$("#newProduct select[name='sesnYearCd']").val(sesnYearCd);
				$("#newProduct select[name='sesnDivnCd']").val(sesnDivnCd);
			}

			//-----RadioButton Value
// 			$(":radio[name='vicOnlineCd']:radio[value='" + vicOnlineCd + "']").attr("checked", true);
			$(":radio[name='newProdPromoFg']:radio[value='" + newProdPromoFg + "']").attr("checked", true);
			$(":radio[name='overPromoFg']:radio[value='" + overPromoFg + "']").attr("checked", true);
			$(":radio[name='sNewProdPromoFg']:radio[value='" + sNewProdPromoFg + "']").attr("checked", true);	//(슈퍼) 신상품장려금구분
			$(":radio[name='sOverPromoFg']:radio[value='" + sOverPromoFg + "']").attr("checked", true);			//(슈퍼) 성과초과장려금구분
			$(":radio[name='npurBuyPsbtDivnCd']:radio[value='" + npurBuyPsbtDivnCd + "']").attr("checked", true);
			$(":radio[name='newProdFirstPurDivnCd']:radio[value='" + newProdFirstPurDivnCd + "']").attr("checked", true);
			$(":radio[name='mixYn']:radio[value='" + mixYn + "']").attr("checked", true);
			$(":radio[name='totInspYn']:radio[value='" + totInspYn + "']").attr("checked", true);
			$("#:radio[name='ecoYn']:radio[value='" + ecoYn + "']").attr("checked", true);
			$("#:radio[name='dlvGa']:radio[value='" + dlvGa + "']").attr("checked", true);
			$("#:radio[name='insCo']:radio[value='" + insCo + "']").attr("checked", true);
			$("#:radio[name='prodAttTypFg']:radio[value='" + prodAttTypFg + "']").attr("checked", true);
			$("#:radio[name='ownStokFg']:radio[value='" + ownStokFg + "']").attr("checked", true);
			$("#:radio[name='mnlProdReg']:radio[value='" + mnlProdReg + "']").attr("checked", true);

			//	alert("prodAttTypFg"+prodAttTypFg);
			//----- 상품범주 [00:단일, 01:묶음] 값에 따라 판매코드 입력박스 readonly
			if (prodAttTypFg == "00") {
				$("input[name='sellCd']").attr("readonly", false);
				_eventSetSellCdStat(prodAttTypFg);
				$("input[name='sellCd']").val(sellCd);
			} else {
				$("input[name='sellCd']").attr("readonly", true);
				_eventSetSellCdStat(prodAttTypFg);
			}
			//-----신상품 출시일자
			$("#newProduct input[name='newProdStDy']").val(newProdStDy);
			$("#newProduct input[name='sNewProdStDy']").val(sNewProdStDy);

			//-----입고가능, 출고가능일
			$("#newProduct input[name='stgePsbtDd']").val(stgePsbtDd);
			$("#newProduct input[name='pickPsbtDd']").val(pickPsbtDd);

			//----- 조회된 소분류, 상품범주 값 hidden에 설정(이유는 상품을 수정할때 소분류, 상품범주 변경여부를 확인하기 위해...)
			$("#hiddenForm input[name='oldL3Cd']").val(l3Cd);
			$("#hiddenForm input[name='oldGrpCd']").val(grpCd);
			$("#hiddenForm input[name='oldProdAttTypFg']").val(prodAttTypFg);

			//-----표시단위, 표시기준수량 disabled 설정[등록된 해당 소분류의 표시기준수량, 표시기준단위의 Default 정보가 있을 경우]
			var essenTialcnt	=	"<c:out value='${newProdEssenTialExistCnt}'/>";
			if (essenTialcnt > 0) {
				$("select[name='dpUnitCd']").attr("disabled", true);
				$("input[name='dpBaseQty']").attr("disabled", true);
			}

			//console.log("trdTypeDivnCd ==" + trdTypeDivnCd);

			//-----특약1 업체만 무발주매입구분 활성화
			if (trdTypeDivnCd != "2") {
				$("input[name=npurBuyPsbtDivnCd]").attr("disabled",true);							//Radio 버튼 선택 못하게 Disable
			} else {
				$("input[name=npurBuyPsbtDivnCd]").attr("disabled",false);							//Radio 버튼 활성화
			}

			$("input[name='plasticWeight']").val(plasticWeight);

			$("input[name='hscd']").val(hscd);
			$("input[name='decno']").val(decno);
			$("input[name='tarrate']").val(tarrate);

			$("input[name='ecAttrRegYn']").val(ecAttrRegYn);

			// 발주마감기한 일자 세팅
			$("#newProduct input[name='ordDeadline']").val(ordDeadline);

			// 20180524 전산정보팀 이상구 수정.
			// 체험형 상품 유무, 판매자 추천평
			if(exprProdYn === '1'){ // 체험형 상품일 때, 판매자 상품평 세팅
				$(":radio[name='exprProdYn']:radio[value='" + exprProdYn + "']").attr("checked", true);
				$("input[name='sellerRecomm']").attr('disabled',false);
				$("input[name='sellerRecomm']").val(sellerRecomm);

				// 판매자 상품평 만큰 바이트 수 다시 표기해주기.
				var tmpStr = new String(sellerRecomm);
				var tmpStrLen = tmpStr.length;
				var oneChar;
				var tcount = 0;
				for(var i=0; i < tmpStrLen; i++){
					// 바이트 계산 방식 온라인몰과 똑같이..
					/* oneChar = tmpStr.charAt(i);
					if (escape(oneChar).length > 4)
					{
						tcount += 3;
					}
					else tcount ++;	 */
					oneChar = tmpStr.charCodeAt(i);
					if(oneChar > 128) tcount += 2;
					else tcount ++;
				}
				$("#recBytes").text(tcount);
				selRecInput = sellerRecomm;
			}
			
			//기본 선택된 채널 초기화 후, 채널코드 셋팅
			$("#newProduct input[name='chanCd']").prop("checked", false);
			if($.trim(chanCd) != ""){
				//콤마분리
				var chanCdArr = chanCd.split(",");
				
				//해당 data setting
				$("#newProduct input[name='chanCd']").each(function(){
					var selChanCd = $.trim($(this).val());
					if(chanCdArr.includes(selChanCd)){
						$(this).prop("checked", true);
						//원매가영역활성화
						fncSetPriceAreaByChan(selChanCd, true);
						//채널관련 상품정보 영역 show
						$("#tblNewProdAdd tr[data-chan="+selChanCd+"]").show();
					}else{
						//채널관련 상품정보 영역 hide
						$("#tblNewProdAdd tr[data-chan="+selChanCd+"]").hide();
					}
				});
				
				//온라인운영여부 setting
				fncSetVicOnlineCd(vicOnlineCd);
			}
			
			//협력업체별 채널 설정
			_eventSelVenZzorgInfo(entpCd);
			
			//쇼카드 상품명셋팅 이벤트 호출
			fnSetShowCardNm();
			//관리용 상품명 셋팅 이벤트 호출
			fnSetProdNm();
			
		}

		/* 대분류 변경시 이벤트 */
		function commerceChange(groupCode, val, infoGrpCd, infoGrpCd2){
			//console.log("func:commerceChange");
			//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
			setupLcdDivnCodeProdAdd(val,'1', infoGrpCd); //전상범코드 셋팅

			setupLcdDivnCodeCertAdd(val,'1', infoGrpCd2);

			fnRemoveCert_List();

			//-----중분류 셋팅
			_eventSelectL2List(groupCode, val);
		}

		/* 대분류 변경시 이벤트 20170518 추가*/
		function commerceChange2(groupCode, val, infoGrpCd, infoGrpCd2, infoGrpCd3){
			//console.log("func:commerceChange2 / groupCode:"+groupCode+", val:"+val+", infoGrpCd:"+infoGrpCd+", infoGrpCd2:"+infoGrpCd2+", infoGrpCd3:"+infoGrpCd3);
			//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
			setupLcdDivnCodeProdAdd(val,'1', infoGrpCd); //전상범코드 셋팅

			fnRemoveCert_List();

			//console.log("commerceChange2 infoGrpCd3:"+infoGrpCd3);
			setupLcdDivnCodeCertAdd2(val,'1', infoGrpCd2, infoGrpCd3); //20170518 추가
			//$("li[id=productCertDtlSelectBox]").hide();


			//-----중분류 셋팅
			_eventSelectL2List(groupCode, val);
		}

		/* 온라인에서 사용될 설명 이미지추가 버튼 이벤트 */
		function doImageSplitterView()
	    {
			window.open("/edit/splitter/ImageSplitter.html", "ImageSplitter", "width="+screen.width+", height="+screen.height+", toolbar=no, menubar=no, resizeable=true, fullscreen=true");
	    }

	    /* 신상품 등록 저장 이전 validation 체크 */
	    function validateNewProductInfo() {
			//console.log("func:validateNewProductInfo");
			var validationResult = validateCommon();


			//console.log("validationResult ==" + validationResult);

			//서적 음반일 경우 발행일 필수 입력
			var categoryValue	=	$("#newProduct select[name=l1Cd]").val().replace(/\s/gi, '');					//대분류
			var productDy		=	$("#newProduct input[name='productDy']").val().replace(/\s/gi, '');				//발행일

			if( categoryValue == "055" ) {
				if ( productDy == '' || productDy.length == 0 ) {
					showErrorMessage($("input[name=productDy]"));
				} else {
					deleteErrorMessageIfExist($("input[name=productDy]"));
				}
			} else {
				deleteErrorMessageIfExist($("input[name=productDy]"));
			}


			var dbDatelength = productDy.replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
			if( dbDatelength != '' || dbDatelength != null ) {
				$("input[name=productDy]").val(dbDatelength);
			}

			//신상품출시일자
			var dbDatelength1 = $("input[name=newProdStDy]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
			$("input[name=newProdStDy]").val(dbDatelength1);

			//발주마감기한 - 제거
			var dbDatelength2 = $("input[name=ordDeadline]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
			$("input[name=ordDeadline]").val(dbDatelength2);

			//신상품출시일자(슈퍼)
			var dbDatelength3 = $("input[name=sNewProdStDy]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
			$("input[name=sNewProdStDy]").val(dbDatelength3);


			var errorLength = $("div[name=error_msg]").length;
			
			if(!validationResult || errorLength > 0	) {
				$("input[name=productDy]").val(productDy);
				alert("<spring:message code='msg.product.input.required.varAtt'/>");
				return false;
			} else {
				return true;
			}
		}

	  //상품 등록 폼을 전송하기 전에 각 필드의 기본  값 설정.
		function setupFormFieldDefaultValue() {
		  //console.log("func:setupFormFieldDefaultValue");
		  var tmprtDivnCd	=	$("#newProduct select[name='tmprtDivnCd']").val().replace(/\s/gi, '');
		  var dpTotQty		=	$("#newProduct input[name='dpTotQty']").val().replace(/\s/gi, '');
		  var dpBaseQty		=	$("#newProduct input[name='dpBaseQty']").val().replace(/\s/gi, '');

		 if (dpTotQty == "") {
			  $("#newProuct input[name='dpTotQty']").val("0");
		  }

		  if (dpBaseQty == "") {
			  $("#newProuct input[name='dpBaseQty']").val("0");
		  }
		}

	    /* 소분류의 변형속성 유무 체크 */
	    function _eventChkVarAttCnt(l3Cd) {
	    	var paramInfo = {};
	    	var varAttCnt = "";

	    	paramInfo["l3Cd"]	=	l3Cd

	    	$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectVarAttCnt.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					varAttCnt = data.varAttCnt;
				}
			});

	    	return varAttCnt;
	    }

	    /* 표시단위, 기준수량, 총량 체크 */
	    function _eventChkDp() {

			var dpUnitCd	=	$("select[name='dpUnitCd']").val().replace(/\s/gi, '');
			var dpBaseQty	=	$("input[name='dpBaseQty']").val().replace(/\s/gi, '');
			var dpTotQty	=	$("input[name='dpTotQty']").val().replace(/\s/gi, '');

			if (dpUnitCd != "" ) {
				if (dpBaseQty == "" || dpTotQty == "") {
					alert("표시기준수량 혹은 표시총량이 입력되지 않았습니다.");
					$("input[name='dpBaseQty']").focus();
					return false;
				}
			}

			if (dpBaseQty != "" ) {
				if (dpUnitCd == "" || dpTotQty == "") {
					alert("표시기준단위 혹은 표시총량이 입력되지 않았습니다.");
					$("select[name='dpUnitCd']").focus();
					return false;
				}
			}

			if (dpTotQty != "") {
				if (dpUnitCd == "" || dpBaseQty == "") {
					alert("표시기준단위 혹은 표시기준수량이 입력되지 않았습니다.");
					$("select[name='dpUnitCd']").focus();
					return false;
				}
			}

			return true;
	    }

		/* TAB 제거 */
		function replaceTabToSpace(str) {
			ret = String(str).replace(/(\t)/g, " ");

			// 특수문자 제거
			ret = specialCharRemove(ret);
			return ret;
		}

		// 정규식 특수문자 제거하기(키보드 특수문자는 제외)
		function specialCharRemove(str) {
			var pattern = /[^(가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9~!@#$%^&*|\\\'\";:\/?),]/gi;   // 특수문자 제거

			if (pattern.test(str)) {
				str = str.replace(pattern, " ");
			}

			return str;
		}

		function d2h(d) {
		    return d.toString(16);
		}
		function h2d (h) {
		    return parseInt(h, 16);
		}
		function hexToString (tmp) {
			var arr = tmp.split(' ');
			var str = '';
			var i = 0;
			var arr_len = arr.length;
			var c;

			for (i = 0; i < arr_len; i += 1) {
				c = String.fromCharCode( h2d( arr[i] ) );
				str += c;
			}

			return str;
		}

		/* 재활용 무게 입력 체크(소수점3자리가능, 정수가능, 문자X, 음수부호X) */
		function recycleWeightChk(val) {
			var pattern = /^\d+(?:[.]?[\d]?[\d]?[\d])?$/;
	    	return pattern.test(val) ? true : false;
		}


		/* 저장 이벤트 */
		function _eventSave() {
			//console.log("func:_eventSave");
	    	$("div#content_wrap").block({ message: null ,  showOverlay: false });

	    	var paramInfo	=	{};

	    	//필드 값 검증.
			if(validateNewProductInfo() ) {

				//유효성 체크 통과
				setupFormFieldDefaultValue();

				if($("#entpCd").val() == ""){
					alert("<spring:message code='msg.vendor.code'/>");
					$("#entpCd").focus();
					return;
				}

				// 채널 필수선택
				var chanCdChkedLen = $("input[name='chanCd']:checked").length;
				if(chanCdChkedLen == 0){
					alert("채널은 1개이상 선택하셔야 합니다.");
					return;
				}

				// 판매코드가 입력 되었을 경우 13자리인지 체크
				var sellCd = $("#sellCd").val().length;
				if (sellCd > 0 && sellCd < 13) {
					alert("판매코드는 13자리로 입력하셔야 합니다.");
					$("#sellCd").focus();
					return;
				}

				if(! calByteProd( newProduct.prodNm      ,50,	'<spring:message code="msg.product.onOff.default.mgrItemNm"/>',	false) ) 		return;
				if(! calByteProd( newProduct.prodShortNm ,30,	'<spring:message code="msg.product.onOff.default.shortNm"/>',	false) ) 		return;

				//----- 문자열에서 TAB 대신 Space로 변환 Start
				$("#newProduct input[name='prodStandardNm']").val(replaceTabToSpace($("#newProduct input[name='prodStandardNm']").val()));	// 상품규격
				$("#newProduct input[name='prodNm']").val(replaceTabToSpace($("#newProduct input[name='prodNm']").val()));					// 상품명
				$("#newProduct input[name='prodEnNm']").val(replaceTabToSpace($("#newProduct input[name='prodEnNm']").val()));				// 상품영문명
				$("#newProduct input[name='prodShortNm']").val(replaceTabToSpace($("#newProduct input[name='prodShortNm']").val()));		// 쇼카드 상품명
				//----- 문자열에서 TAB 대신 Space로 변환 End

				////////////////////VIC여부가 마트전용 상품일 경우 VIC 원가, 매가, 이익률이 정상원가, 매가, 이익률이랑 동일하게 입력되어 있는지 체크/////////////////////
				//----- VIC여부가 마트전용 상품일 경우 VIC 원가, 매가, 이익률이 정상원가, 매가, 이익률이랑 동일하게 입력되어 있는지 체크
// 				var matCd			=	$("#newProduct select[name='matCd']").val();		//VIC 여부
				var tradeType		=	$("#newProduct input[name='tradeType']").val();	//거래형태[1:직매입, 2:특약1, 4:특약2]
				var norProdSalePrc	=	$("#newProduct input[name='norProdSalePrc']").val();				//정상매가
				var norProdPcost	=	$("#newProduct input[name='norProdPcost']").val();					//정상원가
				var prftRate		=	$("#newProduct input[name='prftRate']").val();						//정상이익률
				var wnorProdSalePrc	=	$("#newProduct input[name='wnorProdSalePrc']").val();				//VIC매가
				var wnorProdPcost	=	$("#newProduct input[name='wnorProdPcost']").val();					//VIC원가
				var wprftRate		=	$("#newProduct input[name='wprftRate']").val();						//VIC이익률


				/* 250403 마트 차세대 VIC여부 삭제 */
				//----- 마트전용 상품이면서
// 				if (matCd == "1") {

// 					//----- 거래형태가 직매입인 경우 이익률을 입력받지 않기 떄문에 이익률은 체크하지 않는다.
// 					if (tradeType == "1") {
// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.vicNormalSalePrc'/>");
// 							$("#newProduct input[name='wnorProdSalePrc']").focus();
// 							return;
// 						}

// 						if (wnorProdPcost != norProdPcost) {
// 							alert("<spring:message code='msg.product.vicNormalCost'/>");
// 							$("#newProduct input[name='wnorProdPcost']").focus();
// 							return;
// 						}

// 						/* if (wprftRate != prftRate) {
// 							alert("<spring:message code='msg.product.vicPrftRate'/>");
// 							$("#newProduct input[name='wprftRate']").focus();
// 							return;
// 						} */

// 					// ----- 거래형태가 직매입이 아닌 특약1[2], 특약2[4] 일 경우에는 원가 입력을 받지 않기 때문에 원가는 체크하지 앟는다.
// 					} else {
// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.vicNormalSalePrc'/>");
// 							$("#newProduct input[name='wnorProdSalePrc']").focus();
// 							return;
// 						}
// //						alert("matCd22:"+matCd);
// // 						if (wprftRate != prftRate) {
// // 							alert("<spring:message code='msg.product.vicPrftRate'/>");
// // 							$("#newProduct input[name='wprftRate']").focus();
// // 							return;
// // 						}
// 					}
// 				//-----VIC 전용
// 				} else if (matCd == "2") {
// 					//----- 거래형태가 직매입인 경우 이익률을 입력받지 않기 떄문에 이익률은 체크하지 않는다.
// 					if (tradeType == "1") {
// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.normalSalePrc'/>");
// 							$("#newProduct input[name='norProdSalePrc']").focus();
// 							return;
// 						}

// 						if (wnorProdPcost != norProdPcost) {
// 							alert("<spring:message code='msg.product.normalCost'/>");
// 							$("#newProduct input[name='norProdPcost']").focus();
// 							return;
// 						}

// 						/* if (wprftRate != prftRate) {
// 							alert("<spring:message code='msg.product.vicPrftRate'/>");
// 							$("#newProduct input[name='wprftRate']").focus();
// 							return;
// 						} */

// 					// ----- 거래형태가 직매입이 아닌 특약1[2], 특약2[4] 일 경우에는 원가 입력을 받지 않기 때문에 원가는 체크하지 앟는다.
// 					} else {

// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.normalSalePrc'/>");
// 							$("#newProduct input[name='norProdSalePrc']").focus();
// 							return;
//						}

// // 						if (wprftRate != prftRate) {
// // 							alert("<spring:message code='msg.product.normalPrftRate'/>");
// // 							$("#newProduct input[name='prftRate']").focus();
// // 							return;
// //						}
// 					}
// 				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// 센터유형, 온도구분 체크_241119PIY
				/*
					// 센터유형, 온도구분 이벤트 핸들러_241119PIY
					1: 상온/ 2: 저온/ 3: 패션
					2: -25/ 1: 0/ 0: 10

					상온/패션 => 10
					1,3 => 0
				 */
				let ctrTypeDivnCdElem = document.getElementById("ctrTypeDivnCd");
				if (ctrTypeDivnCdElem?.value) {
					let tmprtDivnCdElem = document.getElementById("tmprtDivnCd");
					if ((ctrTypeDivnCdElem.value === '1' || ctrTypeDivnCdElem.value === '3')
						&& tmprtDivnCdElem.value !== '0') {
						alert("센터유형이 상온 및 패션일 경우 온도구분 10도만 선택가능합니다");
						$("#tmprtDivnCd").focus();
						 return;
					}
				}

				//발주입수 체크
				//if (!_eventOrdIpsuCheck())	return;

				//장려금 체크
				if(! _eventSetupJangCheck()) return;

				//전자상거래 validation check
				if(! _eventProdAddValidationCheck()) return;

				//KC인증 체크
				if(! _eventProdCertValidationCheck()) return;

				//채널별 원매가 체크
				if(! fncCheckChanPrice()) return;

				//이익율 체크 - fncCheckChanPrice에서 체크
// 				var prftRate	=	$("input[name='prftRate']").val();
// 				if (Number(prftRate) > 100) {
// 					alert("이익율은 100% 초과 할 수 없습니다.");
// 					return;
// 				}

				//표시기준단위, 표시기준수량, 표시총량 체크
				if (!_eventChkDp()) {
					return;
				}

				//----------------------------------------------재활용 무게1, 재활용무게2, 재활용무게 3
				if ($("input[name='recycleWeight1']").val().replace(/\s/gi, '') != "") {
					if (!recycleWeightChk($("input[name='recycleWeight1']").val())) {
						alert("재활용무게 입력이 잘못되었습니다(소수점3자리 or 정수 입력 요망)");
						$("input[name='recycleWeight1']").focus();
						return;
					}
				}

				if ($("input[name='recycleWeight2']").val().replace(/\s/gi, '') != "") {
					if (!recycleWeightChk($("input[name='recycleWeight2']").val())) {
						alert("재활용무게 입력이 잘못되었습니다(소수점3자리 or 정수 입력 요망)");
						$("input[name='recycleWeight2']").focus();
						return;
					}
				}

				if ($("input[name='recycleWeight3']").val().replace(/\s/gi, '') != "") {
					if (!recycleWeightChk($("input[name='recycleWeight3']").val())) {
						alert("재활용무게 입력이 잘못되었습니다(소수점3자리 or 정수 입력 요망)");
						$("input[name='recycleWeight3']").focus();
						return;
					}
				}
				//-----------------------------------------------------------------------

				//상품설명 체크
				var newProdDescBodyVal	=	CrossEditor.GetBodyValue();
				var newProdDescTxtVal	=	CrossEditor.GetTextValue();
				//newProdDescTxtVal		=	newProdDescTxtVal.replace(/^\s*/,'').replace(/\s*$/, '');  //배포시 반드시 원복

				if(newProdDescTxtVal == "" && newProdDescBodyVal.indexOf('<IMG')=='-1' && newProdDescBodyVal.indexOf('<img')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<IFRAME')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<iframe')=='-1'  && newProdDescBodyVal.toUpperCase().indexOf('<EMBED')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<embed')=='-1'){
					alert('<spring:message code="msg.product.descr"/>');
					return;
				}


				/* ****************************** */
				// 20180817 전산정보팀 이상구 상품키워드 벨리데이션 체크

				/*
				if($.trim($("#searchKywrd1").val()) == "") {
					alert("상품키워드 1행 검색어는 필수 입력 항목입니다.");
					return;
				}
				*/

				if($("#searchKywrd1").val() != "")
				{
					if(reCount($("#searchKywrd1").val()) > 39) {
						alert("상품키워드 1행 검색어는 39바이트를 초과할 수 없습니다.");
						$("#searchKywrd1").focus();
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
			  	  	  		$("#"+ kId[i]).focus();
			  	  	  	      return;
		  	  	  		  }

					      if(reCount($.trim($("#"+ kId[i]).val())) > 39) {
					       	alert("상품키워드 " + (i+1) + "행 검색어는 39바이트를 초과할 수 없습니다.");
					       	$("#"+ kId[i]).focus();
					     	return;
				     	  }

				    	  if ($.trim($("#"+ kId[i]).val()).indexOf(',') > -1 || $.trim($("#"+ kId[i]).val()).indexOf(';') > -1 || $.trim($("#"+ kId[i]).val()).indexOf('|') > -1) {
				    		alert("상품키워드 " + (i+1) + "행 ,(커머) ;(세미콜론) |(버티컬 바) 특수문자는 사용하실 수 없습니다.");
				    		$("#"+ kId[i]).focus();
				    		return;
			    		  }

						  if (!validationByRep($("#"+ kId[i]).val(),'notUpperAndSpace')) {
							alert("상품키워드 " + (i+1) + "행 , 띄어쓰기, 영문 대문자는 사용하실 수 없습니다.");
							$("#"+ kId[i]).focus();
							return;
						  }
		  	  			}
		  	    	}
				}
				/* ****************************** */
				var cboxTmpArr = [];	//체크박스 값 추출을 위한 임시 array
				//----- form의  input, radio, select 요소의 value를 가져온다.
				$("#newProduct").find("input, radio, select").each(function() {
					if (this.type == "radio") {
						paramInfo[this.name]	=	$(":radio[name='" + this.name + "']:checked").val();
					}else if(this.type == "checkbox"){
						//차세대 추가 - 체크박스일 경우, 동일한 이름의 체크된 항목값 콤마로 붙여서 가져옴
						if($(this).is($("input[name='"+this.name+"']").first())){
							$("input[name='"+this.name+"']:checked").each((i,ele)=> cboxTmpArr.push(ele.value));
							paramInfo[this.name] = cboxTmpArr.join();
							cboxTmpArr = [];
						}
					} else {
						if (this.name != "viewProdDesc" &&
								this.name !=  "" &&
								this.name.substr(0,5) != "prev-" &&			//200306
								this.name.substr(0,10) != "ecStandard" 		//200306
							) { // //180822 ibsheet1 null 제거
							paramInfo[this.name]	=	$.trim($(this).val());
						}
					}
				});
				
				
				/* 250403 마트 차세대 VIC여부 삭제 */ 
// 				if (matCd == "2") {
// 					paramInfo["vicOnlineCd"] = $(":radio[name='vicOnlineCd']:checked").val();
// 				} else {
// 					paramInfo["vicOnlineCd"] = '';
// 				}
				
				
				//////////////////////////////////////////////////////////////////
				//채널별 원매가 채널 선택시에만 저장
				var selChanCd = "";
				$("input[name='chanCd']").not(":checked").each(function(){
					selChanCd = $.trim($(this).val());	//선택하지 않은 채널
					//선택하지 않은 채널관련 금액은 초기화한다
					$("#tblNewProdPrc tr[data-chan="+selChanCd+"]").find("input, select").each(function(){
						if(this.name != null && this.name != ""){
							paramInfo[this.name] = "";
						}
					});
				});
				
				
				//채널 MAXX만 선택했을 경우, 온라인 운영여부 선택값으로 셋팅
				paramInfo["vicOnlineCd"] = "";	//온라인 적용여부 기본값 : 공백
				if(chanCdChkedLen == 1 && tradeType == "1"){	//선택채널 1개 && 직매입
					//MAXX만 선택 시
					var selOnlyMaxx = $.trim($("input[name='chanCd']:checked").val()) == "KR03"? true:false;
					if(selOnlyMaxx){
						paramInfo["vicOnlineCd"] = $.trim($("input[name='vicOnlineCd']:checked").val());
					}
					
					//슈퍼만 선택 시, 온라인 미적용
					var selOnlySuper = $.trim($("input[name='chanCd']:checked").val()) == "KR04"? true:false;
					if(selOnlySuper){
						paramInfo["vicOnlineCd"] = "";
					}
				}
				//////////////////////////////////////////////////////////////////
				

				// 20180919 전산정보팀 이상구 수정
				//////////////////////////////////////////////////////////////////
				var searchKywrd = new Array();
				var seq = new Array();
				$("#keywordSubTable tr").not(":first").find("input").each(function(i,e){

					if(e.name === 'seq'){
						seq.push($(this).val());
					}else if(e.name === 'searchKywrd' && $(this).val() ){
						searchKywrd.push($(this).val());
					}

				});
				paramInfo["searchKywrd"] = searchKywrd;
				paramInfo["seq"] = seq;
				///////////////////////////////////////////////////////////////////


				//----- 전상법  입력값
				var trAddInfoLen = $("table[name=data_List] tr[name=titleProdAdd]").length;
				var arrAddInfoVal	=	new Array();
				var arrAddInfoCd	=	new Array();
				for (var i = 0; i < trAddInfoLen; i++) {
					arrAddInfoVal[i]	=	$("input[name='prodAddDetailNm_" + i + "']").val().replace(/\s/gi, '');
					arrAddInfoCd[i]		=	$("input[name='prodAddDetailCd_" + i + "']").val().replace(/\s/gi, '');
				}

				//----- KC인증 마크 입력값
				var trCertInfoLen = $("table[name=cert_List] tr[name=titleProdCert]").length;
				var arrCertInfoVal	=	new Array();
				var arrCertInfoCd	=	new Array();
				for (var i = 0; i < trCertInfoLen; i++) {
					arrCertInfoVal[i]	=	$("input[name='prodCertDetailNm_" + i + "']").val().replace(/\s/gi, '');
					arrCertInfoCd[i]	=	$("input[name='prodCertDetailCd_" + i + "']").val().replace(/\s/gi, '');
				}

				//-----상품 유형에 따라 신상품입점장려금적용구분, 신상품출시일자, 성과초과장려금적요구분이 사용안함일 경우
				if ($("div.jangoption").css("display") == "none") {
					paramInfo["newProdPromoFg"]	= 	"";
					paramInfo["newProdStDy"]	=	"";
					paramInfo["overPromoFg"]	=	"";
				}else{
					paramInfo["newProdStDy"]	= $.trim(paramInfo["newProdStDy"]).replace(/\D/g,"");
				}
				
				// 슈퍼 장려금 사용안함 또는 슈퍼 채널 미선택일 경우
				var isSuper = $("input[name='chanCd'][value='KR04']").is(":checked");	//슈퍼 선택
				if($("div.jangoption").css("display") == "none" || !isSuper){
					//(슈퍼) 신상품장려금
					paramInfo["sNewProdPromoFg"]	= 	"";
					paramInfo["sNewProdStDy"]		=	"";
					paramInfo["sOverPromoFg"]		=	"";
				}else{
					paramInfo["sNewProdStDy"]	= $.trim(paramInfo["sNewProdStDy"]).replace(/\D/g,"");
				}
				
				//-----유통일관리 안함에 따른 유통일, 입고가능일, 출고가능일 사용안함일 경우
				if ($("div.flowDate").css("display") == "none") {
					paramInfo["flowDd"]		= 	"";
					paramInfo["stgePsbtDd"]	=	"";
					paramInfo["pickPsbtDd"]	=	"";
				}

				paramInfo["prodDesc"]			=	CrossEditor.GetValue();
				paramInfo["arrAddInfoVal"]		=	arrAddInfoVal;
				paramInfo["arrCertInfoVal"]		=	arrCertInfoVal;
				paramInfo["arrAddInfoCd"]		=	arrAddInfoCd;
				paramInfo["arrCertInfoCd"]		=	arrCertInfoCd;
				paramInfo["productDy"]			=	paramInfo["productDy"].replaceAll("-", "");
				paramInfo["ecCategoryKeepYn"]	=	$("#hiddenForm input[name='ecCategoryKeepYn']").val().replace(/\s/gi, '');
				paramInfo["ecAttrRegYn"]		=   $("#hiddenForm input[name='ecAttrRegYn']").val().replace(/\s/gi, '');
				paramInfo["delNutAmtYn"]		=   $("#hiddenForm input[name='delNutAmtYn']").val().replace(/\s/gi, '');

				var adminId			=	"<c:out value='${epcLoginVO.adminId}'/>";
				if (adminId == "" || adminId == "online" ) {
					var admFg;
					paramInfo["admFg"]			=	"1";
	 			} else  {
	 				var admFg;
					paramInfo["admFg"]			=	"3";
	 			}


	    		var callUrl	=	"<c:url value='/edi/product/insertNewProductMST.json'/>";			//insert url

	    		paramInfo["iuGbn"] = "I";	// Insert 모드

	    		//----- 상품 수정일때만
				if ($("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '') != "") {
	    			callUrl = "<c:url value='/edi/product/updateNewProductMST.json'/>";				//update url
	    			paramInfo["pgmId"]		=	$("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '');
	    			paramInfo["iuGbn"] = "U";	// Update 모드


	    	    	var oldProdAttTypFg	=	$("#hiddenForm input[name='oldProdAttTypFg']").val().replace(/\s/gi, '');	//상품등록이후 저장되어 있던 상품범주값
	    	    	var prodAttTypFg 	= 	$(":radio[name='prodAttTypFg']:checked").val();								//현재 상품범주 값
	    			var oldL3Cd			=	$("#hiddenForm input[name='oldL3Cd']").val().replace(/\s/gi, '');			//상품등록이후 저장되어 있던 소분류값
	    	    	var l3Cd			=	$("#newProduct select[name='l3Cd']").val().replace(/\s/gi, '');				//현재 소분류값
	    	    	var oldGrpCd		=	$("#hiddenForm input[name='oldGrpCd']").val().replace(/\s/gi, '');			//상품등록이후 저장되어 있던 소분류값
	    	    	var grpCd			=	$("#newProduct select[name='grpCd']").val().replace(/\s/gi, '');			//현재그룹분석속성코드
	    	    	var oldEntpCd		=	$("#hiddenForm input[name='entpCd']").val().replace(/\s/gi, '');			//상품등록이후 저장되어 있던 협력업체 코드값
	    	    	var entpCd			=	$("#entpCd").val();															//현재설정되어 있는 협력업체코드값

	    	    	/*************************************** 상품범주가 변경되거나 소분류가 변경될시 / 이미지 모두 삭제 ***************************************/
	    	    	if ( (oldProdAttTypFg != prodAttTypFg) || (oldL3Cd != l3Cd) || (oldGrpCd != grpCd) ) {
	    	    		if (!confirm("<spring:message code='msg.product.modify.prodAttTypFg'/>")) {
    	    				return;
    	    			}
    	    			paramInfo["delGbn"] = "Y";
	    	    	}

	    	    	//----- 현재 설정되어 있는 협력업체코드값이 기존에 등록되어 있는 상품의 협력업체 코드값이랑 다를경우 속성테이블과 오프라인 이미지 정보 테이블의 협력업체 코드값을 Update
    	    		if (oldEntpCd != entpCd) {
    	    			paramInfo["upEntpGbn"] = "Y";
    	    		}
	    	    	/***************************************************************************************************************************/
	    		}

				//-----묶음상품일 경우 해당 소분류의 변형속성 유무 체크
    	    	if ($(":radio[name='prodAttTypFg']:checked").val() == "01") {
    	    		var varAttCnt = _eventChkVarAttCnt(paramInfo["l3Cd"]);

					if (varAttCnt <= 0) {
						alert("해당 소분류의 변형속성 정보가 존재하지 않아 묶음상품으로 등록 하실 수 없습니다.");
						return;
					}
    	    	}

				//-----입력된 판매코드가 있을경우 위해상품의 판매코드인지 체크 저장할때 서버에서 체크하기때문에 스크립트는 주석처리
				/* if ($("input[name='sellCd']").val().replace(/\s/gi, '') != "") {
					if (_eventChkDangerProdCnt($("input[name='sellCd']").val()) == "2") {
						alert("<spring:message code='msg.prod.danger'/>");	//해당 판매코드는 위해상품으로 등록된 판매코드 이므로 상품을 등록 하실 수 없습니다
						return;
					}
				}		 */

				// 20180514 전산정보팀 이상구 수정
				// 체험형 상품 여부 체크 Y인데 판매자 추천평을 작성하지 않았을 경우 alert 출력
				if($("input[name='exprProdYn']:checked").val() === "1"){
					// 입력한 판매자 추천평
					var recommend = $("input[name='sellerRecomm']").val();

					if(! recommend.length > 0 ){
						alert('판매자 추천평을 작성해주세요.');
						$("input[name='sellerRecomm']").focus();
						return;
					}
				}
				// 20180528 전산정보팀 이상구 체험형 상품 유무를 true, false 로 바꿈.
				if(paramInfo.exprProdYn === '1'){
					paramInfo.exprProdYn = true;
				}else{
					paramInfo.exprProdYn = false;
				}

				//250331PIY
				if(fncGetGridDataRowCount("dispCtg") == 0) {
					alert("전시카테고리를 추가하세요.");
					return;
				}

				if (fncGetGridDataRowCount("ecOnDispCtg") == 0) {
					alert("롯데ON 전시카테고리를 추가하세요.");
					return;
				}

				let TYcnt = 0;
				let tmpDispCatCd = "";
				let tmpGridData;
				
				//롯데몰 EC 전시카테고리 전체 데이터 rowIDs 조회
				let ecMallDataRows = fncGetGridRowDataIds("ecMallDispCtg");
				for (let i = 0; i < ecMallDataRows.length; i++) {
					tmpGridData = $("#tabList_ecMallDispCtg").jqGrid("getRowData", ecMallDataRows[i]);
					tmpDispCatCd = $.trim(tmpGridData.DISP_CAT_CD);
					
					if (tmpDispCatCd.indexOf("TY") > -1) {
						TYcnt++;
					}
				}

				// 토이&문구팀으로 토이 구분 / 문구, 브랜드유아용품은 마트 카테고리로 분류
				let isToy = $("#newProduct select[name=teamCd]").val() == "3000000000012004" && $("#newProduct select[name=l1Cd]").val() != "054";

				if (isToy) {
					if (TYcnt == 0) {
						alert("토이저러스 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 토이저러스몰 전시카테고리 TY코드를 1개 이상 선택해주세요.");
						return;
					}
				}

				if (fncGetGridDataRowCount("ecMallDispCtg") == 0) {
					if (isToy) {
						alert("토이저러스 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 토이저러스몰 전시카테고리 TY코드를 1개 이상 선택해주세요.");
						return;
					}
				}

				//24NBM_OSP_CAT S
				if(fncGetGridDataRowCount("zettaDispCtg") == 0) {
					alert("ZETTA 전시카테고리를 추가하세요.");
					return;
				}

				//180724 전시카테고리 START
				var chkVal = "";
				
				/**
				 ** 전시 카테고리 데이터 구성
				 **/
				let dispCtgDataRows = fncGetGridRowDataIds("dispCtg");		//기본전시카테고리
				let ecOnCtgDataRows = fncGetGridRowDataIds("ecOnDispCtg");	//롯데ON EC 전시카테고리
				let ecMallCtgDataRows = fncGetGridRowDataIds("ecMallDispCtg");	//롯데몰 EC 전시카테고리
				let zettaCtgDataRows = fncGetGridRowDataIds("zettaDispCtg");	//Zetta 전시카테고리
				
				let tmpRowData;
				
				//----- 1) 기본 전시카테고리
				for (var i = 0; i < dispCtgDataRows.length; i++) {
					tmpRowData = $("#tabList_dispCtg").jqGrid("getRowData", dispCtgDataRows[i]);
					chkVal += "," + $.trim(tmpRowData.CATEGORY_ID);
				}

				chkVal = chkVal.substring(1,chkVal.length);

				$("#categoryId").val(chkVal);
				paramInfo["categoryId"] = $("#categoryId").val();

				if(chkVal == ''){
					alert('전시카테고리를 작성해주세요.');
					return;
				}
				//180724 전시카테고리 END

				//----- 2) EC 전시카테고리
				//200306 EC전용 카테고리 START
				if(fncGetGridDataRowCount("ecOnDispCtg") > 0) {
					chkVal = "";
					
					for(var i=0; i<ecOnCtgDataRows.length; i++){
						tmpRowData = $("#tabList_ecOnDispCtg").jqGrid("getRowData", ecOnCtgDataRows[i]);
						chkVal += "," + $.trim(tmpRowData.DISP_CAT_CD);
					}
					chkVal = chkVal.substring(1,chkVal.length);
					$("#dispCatCd").val(chkVal);
				}

				if(fncGetGridDataRowCount("ecMallDispCtg") > 0) {
					for(var i=0; i<ecMallCtgDataRows.length; i++){
						tmpRowData = $("#tabList_ecMallDispCtg").jqGrid("getRowData", ecMallCtgDataRows[i]);
						chkVal += "," + $.trim(tmpRowData.DISP_CAT_CD);
					}
					chkVal = chkVal.substring(0,chkVal.length);
					$("#dispCatCd").val(chkVal);
				}

				paramInfo["dispCatCd"] = $("#dispCatCd").val();


				//------ 3) ZETTA 전시카테고리
				// 24NBM_OSP_CAT S
				let ospCatCdVal = "";
				let chkOspRep = "";
				if(fncGetGridDataRowCount("zettaDispCtg") > 0) {
					for(let i=0; i<zettaCtgDataRows.length; i++){
						tmpRowData = $("#tabList_zettaDispCtg").jqGrid("getRowData", zettaCtgDataRows[i]);
						
						ospCatCdVal += "," + $.trim(tmpRowData.OSP_CATEGORY_ID);
						ospCatCdVal += $.trim(tmpRowData.OSP_CATEGORY_REP) === "Y" ? "^REP" : "";
					}
					
					ospCatCdVal = ospCatCdVal.substring(1,ospCatCdVal.length);
					$("#ospDispCatCd").val(ospCatCdVal);
				}

				paramInfo["ospDispCatCd"] = $("#ospDispCatCd").val();
				// 24NBM_OSP_CAT E

				var stdCatCd = getEcStandardCategoryId();


				if(stdCatCd == '') {
					alert("EC 표준 카테고리를 선택 하세요.");
					return;
				}

				$("#stdCatCd").val(stdCatCd);

				paramInfo["stdCatCd"] = $("#stdCatCd").val();
				//200306 EC전용 카테고리 END

				// 20181004 전산정보팀 이상구
				// 총중량, 순중량 검토 로직.
				var totWeight = $("#totWeight").val();
				var netWeight = $("#netWeight").val();

				if(totWeight === ''){
					alert('총중량값을 입력해 주세요.')
					$("input[name='totWeight']").focus();
					return;
				}else if(netWeight === ''){
					alert('순중량값을 입력해 주세요.')
					$("input[name='netWeight']").focus();
					return;
				}else if(totWeight === netWeight){
					alert('순중량과 총중량이 일치합니다. 다시 한번 확인 해주세요.\n※ 상품마스터의 활용되는 중요정보로 오입력의 경우 추후 Data의 오류가 발생할 수 있습니다.');
					$("input[name='netWeight']").focus();
					return;
				}else if(Number(totWeight) < Number(netWeight)){
					alert('순중량이 총중량보다 큰 값입니다. 다시 한번 확인 해주세요.\n※ 상품마스터의 활용되는 중요정보로 오입력의 경우 추후 Data의 오류가 발생할 수 있습니다.');
					$("input[name='netWeight']").focus();
					return;
				}
				///////////////////////////////////

				// 181217 전산정보팀 이상구 수정
				// 개별계획구분, 개별계획량 체크 로직 추가.

				if(paramInfo['ownStokFg'] !== '' && paramInfo['planRecvQty'] === ''){
					alert('개별계획량 MD 확인하시기 바랍니다.');
					$("input[name='planRecvQty']").focus();
					return;
				}else if(paramInfo['ownStokFg'] !== '' && paramInfo['ordDeadline'] === ''){
					alert('계획종료일 MD 확인하시기 바랍니다.');
					$("input[name='ordDeadline']").focus();
					return;
				}/* else if(paramInfo['planRecvQty'] !== '' && paramInfo['ownStokFg'] === ''){
					alert('총량약정량이 입력되면 총량약정구분에는 대상으로 표기되어야 합니다.');
					$("input[name='ownStokFg']").focus();
					return;
				} */
				//////////////////////////////////////

				//

				// 200917 - 체크 함수 추가 //
				if(!checkPlasticRecycle())return;
				if(!checkValuesIsValidatedByRep())return;

				// 250122 PIY_소비기한 로직 추가
				if (!validFlowDdMgrCd2())return;

				if (!confirm("<spring:message code='msg.common.confirm.save'/>")) {
					return;
				}


	    		//console.log(paramInfo);
	    		//return;
	    		//console.log("ajax:"+callUrl);
				$.ajax({
					contentType : 'application/json; charset=utf-8',
					type : 'post',
					dataType : 'json',
					async : false,
					url : callUrl,
					data : JSON.stringify(paramInfo),
					success : function(data) {
						if (data.msg == "SUCCESS") {
							alert("<spring:message code='msg.common.success.save'/>");

							if (data.pgmId != "") {

								//hiddenForm pgmId에 등록된 상품의 pgmId 설정
								$("#hiddenForm input[name='pgmId']").val(data.pgmId);

								//기본정보 페이지 다시 호출 등록된 신상품의 상세정보 보여주기 위함
								$("#hiddenForm input[name='mode']").val("modify");

								$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0020Detail.do'/>");

								$("#hiddenForm").attr("method", "post").submit();
							}
						} else if (data.msg == "NOT_PERMISSION_TRD_TYP_FG") {
							alert("상품을 등록 할 수 없는 거래유형 타입입니다.");
						} else if (data.msg == "DANGER_PROD") {
							alert("<spring:message code='msg.prod.danger'/>");	//해당 판매코드는 위해상품으로 등록된 판매코드 이므로 상품을 등록 하실 수 없습니다
						} else if (data.msg == "NO_CHAN_CD"){
							alert("선택한 채널 정보가 없습니다.");
						} else if (data.msg == "CHAN_NOT_SELECTABLE"){
							alert("해당 업체코드로 선택 불가능한 채널이 존재합니다.");
							//협력업체별 채널 재설정
							_eventSelVenZzorgInfo($("#entpCd").val());
						}else {
							alert("<spring:message code='msg.common.fail.insert'/>");
						}
					}
				});
			}
		}

		/* 위해상품으로 등록되어 있는 판매코드 인지 카운트를 가져온다.[현재 사용안함 저장할때 서버에서 체크함] 2016.04.06 추가 by song min kyo*/
		function _eventChkDangerProdCnt(sellCd) {
			var paramInfo = {};
			var dangerCfmFg = "";

			paramInfo["sellCd"] = sellCd;

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectChkDangerProdCnt.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					dangerCfmFg = data.dangerCfmFg;
				}
			});

			return dangerCfmFg;
		}

		/* 발주입수 체크 */
		function _eventOrdIpsuCheck() {

			var purInrcntQty	=	$("input[name='purInrcntQty']").val().replace(/\s/gi, '');		//발주입수
			var purUnitCd		=	$("select[name='purUnitCd']").val().replace(/\s/gi, '');		//발주단위

			//-----발주단위가 EA이면서
			if (purUnitCd == "EA") {

				//----- 발주입수 수량이 1이 아닐경우는 false
				if (purInrcntQty != "1") {
					alert("<spring:message code='msg.product.alert.purInrcntQty'/>");
					return false;
				}
			}

			return true;
		}

		// ImageSplitter Popup에서 데이터가 전해진다.
	    function addSplitImage(activeSquareMimeValue)
	    {
			var bodyTag	=	CrossEditor.GetBodyValue();

			CrossEditor.SetBodyValue(bodyTag + decodeURI(CrossEditor.GetBodyValue()));
			CrossEditor.SetBodyValue(activeSquareMimeValue);

			/* var wec = document.Wec;
			var bodyTag = wec.BodyValue;
			wec.MIMEValue = activeSquareMimeValue;
			wec.BodyValue = bodyTag + decodeURI(wec.BodyValue); // base64에서 한글 입력하면 깨진다.
			*/
	    }

		/* 상품 복사 */
	    function _eventCopy() {
			//console.log("func:_eventCopy");
			$("div#content_wrap").block({ message: null ,  showOverlay: false  });

			var paramInfo	=	{};


			// 판매코드가 입력 되었을 경우 13자리인지 체크
			var sellCd = $("#sellCd").val().length;
			if (sellCd > 0 && sellCd < 13) {
				alert("판매코드는 13자리로 입력하셔야 합니다.");
				$("#sellCd").focus();
				return;
			}

	    	//필드 값 검증.
			if( validateNewProductInfo() ) {

				//유효성 체크 통과
				setupFormFieldDefaultValue();

				if($("#entpCd").val() == ""){
					alert('<spring:message code="msg.product.common.comp"/>');
					$("#entpCd").focus();
					return;
				}

				// 채널 필수선택
				var chanCdChkedLen = $("input[name='chanCd']:checked").length;
				if(chanCdChkedLen == 0){
					alert("채널은 1개이상 선택하셔야 합니다.");
					return;
				}

				if(! calByteProd( newProduct.prodNm      ,50,'<spring:message code="msg.product.onOff.default.mgrItemNm"/>',false) ) return;
				if(! calByteProd( newProduct.prodShortNm ,30,'<spring:message code="msg.product.onOff.default.shortNm"/>',false) ) return;

				//발주입수 체크
				//if (!_eventOrdIpsuCheck())	return;

				//장려금 체크
				if(! _eventSetupJangCheck("CP")) return;

				//전자상거래 validation check
				if(!_eventProdAddValidationCheck()) return;

				//KC인증 체크
				if(!_eventProdCertValidationCheck()) return;

				////////////////////VIC여부가 마트전용 상품일 경우 VIC 원가, 매가, 이익률이 정상원가, 매가, 이익률이랑 동일하게 입력되어 있는지 체크/////////////////////
				//----- VIC여부가 마트전용 상품일 경우 VIC 원가, 매가, 이익률이 정상원가, 매가, 이익률이랑 동일하게 입력되어 있는지 체크
// 				var matCd			=	$("#newProduct select[name='matCd']").val();						//VIC 여부
				var tradeType		=	$("#newProduct input[name='tradeType']").val();						//거래형태[1:직매입, 2:특약1, 4:특약2]
				var norProdSalePrc	=	$("#newProduct input[name='norProdSalePrc']").val();				//정상매가
				var norProdPcost	=	$("#newProduct input[name='norProdPcost']").val();					//정상원가
				var prftRate		=	$("#newProduct input[name='prftRate']").val();						//정상이익률
				var wnorProdSalePrc	=	$("#newProduct input[name='wnorProdSalePrc']").val();				//VIC매가
				var wnorProdPcost	=	$("#newProduct input[name='wnorProdPcost']").val();					//VIC원가
				var wprftRate		=	$("#newProduct input[name='wprftRate']").val();						//VIC이익률

				/* 250403 마트 차세대 VIC여부 삭제 */
				//----- 마트전용 상품이면서
// 				if (matCd == "1") {

// 					//----- 거래형태가 직매입인 경우 이익률을 입력받지 않기 떄문에 이익률은 체크하지 않는다.
// 					if (tradeType == "1") {
// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.vicNormalSalePrc'/>");
// 							$("#newProduct input[name='wnorProdSalePrc']").focus();
// 							return;
// 						}

// 						if (wnorProdPcost != norProdPcost) {
// 							alert("<spring:message code='msg.product.vicNormalCost'/>");
// 							$("#newProduct input[name='wnorProdPcost']").focus();
// 							return;
// 						}

// 						/* if (wprftRate != prftRate) {
// 							alert("<spring:message code='msg.product.vicPrftRate'/>");
// 							$("#newProduct input[name='wprftRate']").focus();
// 							return;
// 						} */

// 					// ----- 거래형태가 직매입이 아닌 특약1[2], 특약2[4] 일 경우에는 원가 입력을 받지 않기 때문에 원가는 체크하지 앟는다.
// 					} else {
// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.vicNormalSalePrc'/>");
// 							$("#newProduct input[name='wnorProdSalePrc']").focus();
// 							return;
// 						}

// 						if (wprftRate != prftRate) {
// 							alert("<spring:message code='msg.product.vicPrftRate'/>");
// 							$("#newProduct input[name='wprftRate']").focus();
// 							return;
// 						}
// 					}
// 				//-----VIC 전용
// 				} else if (matCd == "2") {
// 					//----- 거래형태가 직매입인 경우 이익률을 입력받지 않기 떄문에 이익률은 체크하지 않는다.
// 					if (tradeType == "1") {
// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.normalSalePrc'/>");
// 							$("#newProduct input[name='norProdSalePrc']").focus();
// 							return;
// 						}

// 						if (wnorProdPcost != norProdPcost) {
// 							alert("<spring:message code='msg.product.normalCost'/>");
// 							$("#newProduct input[name='norProdPcost']").focus();
// 							return;
// 						}

// 						/* if (wprftRate != prftRate) {
// 							alert("<spring:message code='msg.product.vicPrftRate'/>");
// 							$("#newProduct input[name='wprftRate']").focus();
// 							return;
// 						} */

// 					// ----- 거래형태가 직매입이 아닌 특약1[2], 특약2[4] 일 경우에는 원가 입력을 받지 않기 때문에 원가는 체크하지 앟는다.
// 					} else {
// 						if (norProdSalePrc != wnorProdSalePrc) {
// 							alert("<spring:message code='msg.product.normalSalePrc'/>");
// 							$("#newProduct input[name='norProdSalePrc']").focus();
// 							return;
// 						}

// 						if (wprftRate != prftRate) {
// 							alert("<spring:message code='msg.product.normalPrftRate'/>");
// 							$("#newProduct input[name='prftRate']").focus();
// 							return;
// 						}
// 					}
// 				}
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


				//이익율 체크
// 				var prftRate	=	$("input[name='prftRate']").val();
// 				//console.log(Number(prftRate));
// 				if (Number(prftRate) > 100) {
// 					alert("이익율은 100% 초과 할 수 없습니다.");
// 					return;
// 				}

				//채널별 원매가 체크
				if(! fncCheckChanPrice()) return;

				//표시기준단위, 표시기준수량, 표시총량 체크
				if (!_eventChkDp()) {
					return;
				}

				//----------------------------------------------재활용 무게1, 재활용무게2, 재활용무게 3
				if ($("input[name='recycleWeight1']").val().replace(/\s/gi, '') != "") {
					if (!recycleWeightChk($("input[name='recycleWeight1']").val())) {
						alert("재활용무게 입력이 잘못되었습니다(소수점3자리 or 정수 입력 요망)");
						$("input[name='recycleWeight1']").focus();
						return;
					}
				}

				if ($("input[name='recycleWeight2']").val().replace(/\s/gi, '') != "") {
					if (!recycleWeightChk($("input[name='recycleWeight2']").val())) {
						alert("재활용무게 입력이 잘못되었습니다(소수점3자리 or 정수 입력 요망)");
						$("input[name='recycleWeight2']").focus();
						return;
					}
				}

				if ($("input[name='recycleWeight3']").val().replace(/\s/gi, '') != "") {
					if (!recycleWeightChk($("input[name='recycleWeight3']").val())) {
						alert("재활용무게 입력이 잘못되었습니다(소수점3자리 or 정수 입력 요망)");
						$("input[name='recycleWeight3']").focus();
						return;
					}
				}
				//-----------------------------------------------------------------------

				/////////////////상품설명 validation//////////////
				var newProdDescBodyVal	=	CrossEditor.GetBodyValue();
				var newProdDescTxtVal	=	CrossEditor.GetTextValue();
				newProdDescTxtVal		=	newProdDescTxtVal.replace(/^\s*/,'').replace(/\s*$/, ''); //--배포시 반드시 주석해제!!!

				if(newProdDescTxtVal == "" && newProdDescBodyVal.indexOf('<IMG')=='-1' && newProdDescBodyVal.indexOf('<img')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<IFRAME')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<iframe')=='-1'  && newProdDescBodyVal.toUpperCase().indexOf('<EMBED')=='-1' && newProdDescBodyVal.toUpperCase().indexOf('<embed')=='-1'){
					alert('<spring:message code="msg.product.descr"/>');
					return;
				}

    	    	/*************************************** 상품범주와 소분류가 변경되지 않았을 경우에만 속성/이미지 정보를 모두 복사한다 ***************************************/
    	    	var oldProdAttTypFg	=	$("#hiddenForm input[name='oldProdAttTypFg']").val().replace(/\s/gi, '');	//상품등록이후 저장되어 있던 상품범주값
    	    	var prodAttTypFg 	= 	$(":radio[name='prodAttTypFg']:checked").val();								//현재 상품범주 값
    			var oldL3Cd			=	$("#hiddenForm input[name='oldL3Cd']").val().replace(/\s/gi, '');			//상품등록이후 저장되어 있던 소분류값
    	    	var l3Cd			=	$("#newProduct select[name='l3Cd']").val().replace(/\s/gi, '');				//현재 소분류값

    	    	var oldGrpCd			=	$("#hiddenForm input[name='oldGrpCd']").val().replace(/\s/gi, '');			//상품등록이후 저장되어 있던 소분류값
    	    	var grpCd			=	$("#newProduct select[name='grpCd']").val().replace(/\s/gi, '');				//현재 소분류값

    	    	if ((oldProdAttTypFg == prodAttTypFg) && (oldL3Cd == l3Cd)&& (oldGrpCd == grpCd)) {
    	    		paramInfo["copyGbn"] = "Y";
    	    	} else {
    	    		paramInfo["copyGbn"] = "N";
    	    	}
    	    	/*************************************************************************************************************************************/

    	    	/* ****************************** */
				// 20180817 전산정보팀 이상구 상품키워드 벨리데이션 체크
				/*
				if($.trim($("#searchKywrd1").val()) == "") {
					alert("상품키워드 1행 검색어는 필수 입력 항목입니다.");
					return;
				}
				*/

				if( $("#searchKywrd1").val() != "")
				{
					if(reCount($("#searchKywrd1").val()) > 39) {
						alert("상품키워드 1행 검색어는 39바이트를 초과할 수 없습니다.");
						$("#searchKywrd1").focus();
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
			  	  	 			$("#"+ kId[i]).focus();
				  	  		      return;
				  	        }

						    if(reCount($.trim($("#"+ kId[i]).val())) > 39) {
						     	alert("상품키워드 " + (i+1) + "행 검색어는 39바이트를 초과할 수 없습니다.");
						     	$("#"+ kId[i]).focus();
						    	return;
					     	}

					    	if ($.trim($("#"+ kId[i]).val()).indexOf(',') > -1 || $.trim($("#"+ kId[i]).val()).indexOf(';') > -1 || $.trim($("#"+ kId[i]).val()).indexOf('|') > -1) {
					    		alert("상품키워드 " + (i+1) + "행 ,(커머) ;(세미콜론) |(버티컬 바) 특수문자는 사용하실 수 없습니다.");
					    		$("#"+ kId[i]).focus();
					    		return;
				    		}

							if (!validationByRep($("#"+ kId[i]).val(),'notUpperAndSpace')) {
								alert("상품키워드 " + (i+1) + "행 , 띄어쓰기, 영문 대문자는 사용하실 수 없습니다.");
								$("#"+ kId[i]).focus();
								return;
							}
		  	  			}
		  	    	}
				}
				/* ****************************** */


				//----- form의  input, radio, select 요소의 value를 가져온다.
				var cboxTmpArr = [];	//체크박스 값 추출을 위한 임시 array
				$("#newProduct").find("input, radio, select").each(function() {
					if (this.type == "radio") {
						paramInfo[this.name]	=	$(":radio[name='" + this.name + "']:checked").val();
					}else if(this.type == "checkbox"){
						//차세대 추가 - 체크박스일 경우, 동일한 이름의 체크된 항목값 콤마로 붙여서 가져옴
						if($(this).is($("input[name='"+this.name+"']").first())){
							$("input[name='"+this.name+"']:checked").each((i,ele)=> cboxTmpArr.push(ele.value));
							paramInfo[this.name] = cboxTmpArr.join();
							cboxTmpArr = [];
						}
					} else {
						if(this.name != "viewProdDesc" &&
								this.name !=  "" &&
								this.name != "searchKywrd" &&
								this.name != "seq" &&
								this.name.substr(0,5) != "prev-" &&			//200306
								this.name.substr(0,10) != "ecStandard" 		//200306
								) { // null 값 제거
							paramInfo[this.name]	=	$(this).val();
						}
					}
				});

				/* 250403 마트 차세대 VIC여부 삭제 */
// 				if (matCd == "2") {
// 					paramInfo["vicOnlineCd"] = $(":radio[name='vicOnlineCd']:checked").val();
// 				} else {
// 					paramInfo["vicOnlineCd"] = '';
// 				}
				
				//////////////////////////////////////////////////////////////////
				//채널별 원매가 채널 선택시에만 저장
				var selChanCd = "";
				$("input[name='chanCd']").not(":checked").each(function(){
					selChanCd = $.trim($(this).val());	//선택하지 않은 채널
					//선택하지 않은 채널관련 금액은 초기화한다
					$("#tblNewProdPrc tr[data-chan="+selChanCd+"]").find("input, select").each(function(){
						if(this.name != null && this.name != ""){
							paramInfo[this.name] = "";
						}
					});
				});
				
				//채널 MAXX만 선택했을 경우, 온라인 운영여부 선택값으로 셋팅
				paramInfo["vicOnlineCd"] = "";	//온라인 적용여부 기본값 : 공백
				if(chanCdChkedLen == 1 && tradeType == "1"){	//선택채널 1개 && 직매입
					//MAXX만 선택 시
					var selOnlyMaxx = $.trim($("input[name='chanCd']:checked").val()) == "KR03"? true:false;
					if(selOnlyMaxx){
						paramInfo["vicOnlineCd"] = $.trim($("input[name='vicOnlineCd']:checked").val());
					}
					
					//슈퍼만 선택 시, 온라인 미적용
					var selOnlySuper = $.trim($("input[name='chanCd']:checked").val()) == "KR04"? true:false;
					if(selOnlySuper){
						paramInfo["vicOnlineCd"] = "";
					}
				}
				//////////////////////////////////////////////////////////////////

				//----- 전상법  입력값
				var trAddInfoLen = $("table[name=data_List] tr[name=titleProdAdd]").length;
				var arrAddInfoVal	=	new Array();
				var arrAddInfoCd	=	new Array();
				for (var i = 0; i < trAddInfoLen; i++) {
					arrAddInfoVal[i]	=	$("input[name='prodAddDetailNm_" + i + "']").val().replace(/\s/gi, '');
					arrAddInfoCd[i]		=	$("input[name='prodAddDetailCd_" + i + "']").val().replace(/\s/gi, '');
				}

				//----- KC인증 마크 입력값
				var trCertInfoLen = $("table[name=cert_List] tr[name=titleProdCert]").length;
				var arrCertInfoVal	=	new Array();
				var arrCertInfoCd	=	new Array();
				for (var i = 0; i < trCertInfoLen; i++) {
					arrCertInfoVal[i]	=	$("input[name='prodCertDetailNm_" + i + "']").val().replace(/\s/gi, '');
					arrCertInfoCd[i]	=	$("input[name='prodCertDetailCd_" + i + "']").val().replace(/\s/gi, '');
				}

				//-----상품 유형에 따라 신상품입점장려금적용구분, 신상품출시일자, 성과초과장려금적요구분이 사용안함일 경우
				if ($("div.jangoption").css("display") == "none") {
					paramInfo["newProdPromoFg"]	= 	"";
					paramInfo["newProdStDy"]	=	"";
					paramInfo["overPromoFg"]	=	"";
				}else{
					paramInfo["newProdStDy"]	= $.trim(paramInfo["newProdStDy"]).replace(/\D/g,"");
				}
				
				// 슈퍼 장려금 사용안함 또는 슈퍼 채널 미선택일 경우
				var isSuper = $("input[name='chanCd'][value='KR04']").is(":checked");	//슈퍼 선택
				if($("div.jangoption").css("display") == "none" || !isSuper){
					//(슈퍼) 신상품장려금
					paramInfo["sNewProdPromoFg"]	= 	"";
					paramInfo["sNewProdStDy"]		=	"";
					paramInfo["sOverPromoFg"]		=	"";
				}else{
					paramInfo["sNewProdStDy"]	= $.trim(paramInfo["sNewProdStDy"]).replace(/\D/g,"");
				}

				//-----유통일관리 안함에 따른 유통일, 입고가능일, 출고가능일 사용안함일 경우
				if ($("div.flowDate").css("display") == "none") {
					paramInfo["flowDd"]		= 	"";
					paramInfo["stgePsbtDd"]	=	"";
					paramInfo["pickPsbtDd"]	=	"";
				}

				paramInfo["prodDesc"]			=	CrossEditor.GetValue();
				paramInfo["arrAddInfoVal"]		=	arrAddInfoVal;
				paramInfo["arrCertInfoVal"]		=	arrCertInfoVal;
				paramInfo["arrAddInfoCd"]		=	arrAddInfoCd;
				paramInfo["arrCertInfoCd"]		=	arrCertInfoCd;
				paramInfo["productDy"]			=	paramInfo["productDy"].replaceAll("-", "");
				paramInfo["iuGbn"]				=	"I";
				paramInfo["oldPgmId"]			=	$("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '');
				paramInfo["ecCategoryKeepYn"]	=	$("#hiddenForm input[name='ecCategoryKeepYn']").val().replace(/\s/gi, '');
				paramInfo["ecAttrRegYn"]		=   $("#hiddenForm input[name='ecAttrRegYn']").val().replace(/\s/gi, '');
				paramInfo["delNutAmtYn"]		=   $("#hiddenForm input[name='delNutAmtYn']").val().replace(/\s/gi, '');

				var adminId			=	"<c:out value='${epcLoginVO.adminId}'/>";
				if (adminId == "" || adminId == "online" ) {
					var admFg;
					paramInfo["admFg"]			=	"1";

	 			} else  {
	 				var admFg;
					paramInfo["admFg"]			=	"3";
	 			}

	    		var callUrl	=	"<c:url value='/edi/product/insertNewProductCopyMST.json'/>";			//copy url

	    		//-----묶음상품일 경우 해당 소분류의 변형속성 유무 체크
    	    	if ($(":radio[name='prodAttTypFg']:checked").val() == "01") {
    	    		var varAttCnt = _eventChkVarAttCnt(paramInfo["l3Cd"]);

					if (varAttCnt <= 0) {
						alert("해당 소분류의 변형속성 정보가 존재하지 않아 묶음상품으로 등록 하실 수 없습니다.");
						return;
					}
    	    	}


    	    	// 20180514 전산정보팀 이상구 수정
				// 체험형 상품 여부 체크 Y인데 판매자 추천평을 작성하지 않았을 경우 alert 출력
				if($("input[name='exprProdYn']:checked").val() === "1"){
					// 입력한 판매자 추천평
					var recommend = $("input[name='sellerRecomm']").val();

					if(! recommend.length > 0 ){
						alert('판매자 추천평을 작성해주세요.');
						$("input[name='sellerRecomm']").focus();
						return;
					}
				}
				// 20180528 전산정보팀 이상구 체험형 상품 유무를 true, false 로 바꿈.
				if(paramInfo.exprProdYn === '1'){
					paramInfo.exprProdYn = true;
				}else{
					paramInfo.exprProdYn = false;
				}

				/////////////////카테고리 validation//////////////
				//250331 PIY
				if(fncGetGridDataRowCount("dispCtg") == 0) {
					alert("전시카테고리를 추가하세요.");
					return;
				}

				if (fncGetGridDataRowCount("ecOnDispCtg") == 0) {
					alert("롯데ON 전시카테고리를 추가하세요.");
					return;
				}

				let TYcnt = 0;
				let tmpDispCatCd = "";
				let tmpGridData;
				
				//롯데몰 EC 전시카테고리 전체 데이터 rowIDs 조회
				let ecMallDataRows = fncGetGridRowDataIds("ecMallDispCtg");
				for (let i = 0; i < ecMallDataRows.length; i++) {
					tmpGridData = $("#tabList_ecMallDispCtg").jqGrid("getRowData", ecMallDataRows[i]);
					tmpDispCatCd = $.trim(tmpGridData.DISP_CAT_CD);
					
					if (tmpDispCatCd.indexOf("TY") > -1) {
						TYcnt++;
					}
				}

				// 토이&문구팀으로 토이 구분 / 문구, 브랜드유아용품은 마트 카테고리로 분류
				let isToy = $("#newProduct select[name=teamCd]").val() == "3000000000012004" && $("#newProduct select[name=l1Cd]").val() != "054";

				if (isToy) {
					if (TYcnt == 0) {
						alert("토이저러스 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 토이저러스몰 전시카테고리 TY코드를 1개 이상 선택해주세요.");
						return;
					}
				}

				if (fncGetGridDataRowCount("ecMallDispCtg") == 0) {
					if (isToy) {
						alert("토이저러스 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 토이저러스몰 전시카테고리 TY코드를 1개 이상 선택해주세요.");
						return;
					}
				}

				//24NBM_OSP_CAT S
				if(fncGetGridDataRowCount("zettaDispCtg") == 0) {
					alert("ZETTA 전시카테고리를 추가하세요.");
					return;
				}
				
				//24NBM_OSP_CAT E
				
				var chkVal = "";
				
				/**
				 ** 전시 카테고리 데이터 구성
				 **/
				let ecOnCtgDataRows = fncGetGridRowDataIds("ecOnDispCtg");	//롯데ON EC 전시카테고리
				let ecMallCtgDataRows = fncGetGridRowDataIds("ecMallDispCtg");	//롯데몰 EC 전시카테고리
				let zettaCtgDataRows = fncGetGridRowDataIds("zettaDispCtg");	//Zetta 전시카테고리
				
				let tmpRowData;

				//----- 1) EC 전시카테고리
				//200306 EC전용 카테고리 START
				if(fncGetGridDataRowCount("ecOnDispCtg") > 0) {
					chkVal = "";

					for(var i=0; i<ecOnCtgDataRows.length; i++){
						tmpRowData = $("#tabList_ecOnDispCtg").jqGrid("getRowData", ecOnCtgDataRows[i]);
						chkVal += "," + $.trim(tmpRowData.DISP_CAT_CD);
					}
					chkVal = chkVal.substring(1,chkVal.length);
					$("#dispCatCd").val(chkVal);
				}

				if(fncGetGridDataRowCount("ecMallDispCtg") > 0) {
					for(var i=0; i<ecMallCtgDataRows.length; i++){
						tmpRowData = $("#tabList_ecMallDispCtg").jqGrid("getRowData", ecMallCtgDataRows[i]);
						chkVal += "," + $.trim(tmpRowData.DISP_CAT_CD);
					}
					chkVal = chkVal.substring(0,chkVal.length);
					$("#dispCatCd").val(chkVal);
				}

				paramInfo["dispCatCd"] = $("#dispCatCd").val();

				//----- 2) ZETTA 전시카테고리
				// 24NBM_OSP_CAT S
				let ospCatCdVal = "";
				if(fncGetGridDataRowCount("zettaDispCtg") > 0) {
					for(let i=0; i<zettaCtgDataRows.length; i++){
						tmpRowData = $("#tabList_zettaDispCtg").jqGrid("getRowData", zettaCtgDataRows[i]);
						
						ospCatCdVal += "," + $.trim(tmpRowData.OSP_CATEGORY_ID);
						ospCatCdVal += $.trim(tmpRowData.OSP_CATEGORY_REP) === "Y" ? "^REP" : "";
					}
					
					ospCatCdVal = ospCatCdVal.substring(1,ospCatCdVal.length);
					$("#ospDispCatCd").val(ospCatCdVal);
				}

				paramInfo["ospDispCatCd"] = $("#ospDispCatCd").val();
				// 24NBM_OSP_CAT E

				var stdCatCd = getEcStandardCategoryId();

				if(stdCatCd == '') {
					alert("EC 표준 카테고리를 선택 하세요.");
					return;
				}

				$("#stdCatCd").val(stdCatCd);

				paramInfo["stdCatCd"] = $("#stdCatCd").val();
				//200306 EC전용 카테고리 END




				// 20181004 전산정보팀 이상구
				// 총중량, 순중량 검토 로직.
				var totWeight = $("#totWeight").val();
				var netWeight = $("#netWeight").val();

				if(totWeight === ''){
					alert('총중량값을 입력해 주세요.')
					$("input[name='totWeight']").focus();
					return;
				}else if(netWeight === ''){
					alert('순중량값을 입력해 주세요.')
					$("input[name='netWeight']").focus();
					return;
				}else if(totWeight === netWeight){
					alert('순중량과 총중량이 일치합니다. 다시 한번 확인 해주세요.\n※ 상품마스터의 활용되는 중요정보로 오입력의 경우 추후 Data의 오류가 발생할 수 있습니다.');
					$("input[name='netWeight']").focus();
					return;
				}else if(Number(totWeight) < Number(netWeight)){
					alert('순중량이 총중량보다 큰 값입니다. 다시 한번 확인 해주세요.\n※ 상품마스터의 활용되는 중요정보로 오입력의 경우 추후 Data의 오류가 발생할 수 있습니다.');
					$("input[name='netWeight']").focus();
					return;
				}
				///////////////////////////////////

				// 181217 전산정보팀 이상구 수정
				// 총량약정구분, 총량약정량 체크 로직 추가.
				if(paramInfo['ownStokFg'] !== '' && paramInfo['planRecvQty'] === ''){
					alert('개별계획량 MD 확인하시기 바랍니다.');
					$("input[name='planRecvQty']").focus();
					return;
				}else if(paramInfo['ownStokFg'] !== '' && paramInfo['ordDeadline'] === ''){
					alert('계획종료일 MD 확인하시기 바랍니다.');
					$("input[name='ordDeadline']").focus();
					return;
				}/* else if(paramInfo['planRecvQty'] !== '' && paramInfo['ownStokFg'] === ''){
					alert('총량약정량이 입력되면 총량약정구분에는 대상으로 표기되어야 합니다.');
					$("input[name='ownStokFg']").focus();
					return;
				} */
				//////////////////////////////////////

				// 200917 - 체크 함수 추가 //
				if(!checkPlasticRecycle())return;
				if(!checkValuesIsValidatedByRep())return;
				////

				// 250122 PIY_소비기한 로직 추가
				if (!validFlowDdMgrCd2())retrun;

	    		if (!confirm("<spring:message code='msg.product.alert.prodCopy'/>")) {
		    		return;
		    	}

				$.ajax({
					contentType : 'application/json; charset=utf-8',
					type : 'post',
					dataType : 'json',
					async : false,
					url : callUrl,
					data : JSON.stringify(paramInfo),
					success : function(data) {
						if (data.msg == "SUCCESS") {
							alert("<spring:message code='msg.common.success.save'/>");

							if (data.pgmId != "") {

								//hiddenForm pgmId에 등록된 상품의 pgmId 설정
								$("#hiddenForm input[name='pgmId']").val(data.pgmId);
								//-----수정모드로...
								$("#hiddenForm input[name='mode']").val("modify");

								//기본정보 페이지 다시 호출 등록된 신상품의 상세정보 보여주기 위함
								$("#hiddenForm #cfmFg").val("");
								$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0020Detail.do'/>");
								$("#hiddenForm").attr("method", "post").submit();
							}
						} else if (data.msg == "CHAN_NOT_SELECTABLE"){
							alert("해당 업체코드로 선택 불가능한 채널이 존재하여, 상품정보를 복사하실 수 없습니다.");
						} else {
							alert("<spring:message code='msg.common.fail.insert'/>");
						}
					}
				});
			}

	    }
	    /* *********************** */
		//20180821 - 상품키워드 입력 기능 추가
		//상품키워드 (추가)
		function fnKeywordAddNew(type) {
			if($.trim($("#keywordTd").html()) == '') {
	  			$("#keywordTd").html("<table id='keywordSubTable' style='width:700px'><tr><th style='text-align:center; width:50px'>순번</th><th style='text-align:center; width:200px'>검색어</th><th style='text-align:center; width:50px'></th></tr></table>");
	  		}

			var rows = $("#keywordSubTable tr");
	  	    var index = rows.length;

			var kIndex = 0;
			var keywordId = Array();

	  	    if(index > 1) {
	  	    	if(index >= 6) {
	  	    		alert("6개 이상 등록할 수 없습니다.");
	  	    		return;
	  	    	}

	  	    	$("#keywordSubTable tr td:nth-child(2) input").each(function(tIndex) {
	  				keywordId[tIndex] = $(this).attr("id");
	  				kIndex = $(this).attr("id").substring(11);
	  			});

	  			for(var i=0; i<keywordId.length; i++) {
	  	  	    	if($.trim($("#"+ keywordId[i]).val()) == "") {
	  	  	    		alert((i+1) + "행 상품키워드를 정확하게 입력하세요");
	  	  	    		$("#"+ keywordId[i]).focus();
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
			paramInfo["entpCd"] = 	"<c:out value='${newProdDetailInfo.entpCd}'/>";


			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '<c:url value="/edi/product/delOnlineKeywordInfo.json"/>',
				url : '<c:url value="/edi/product/delOnOffKeywordInfo.json"/>',
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
		//20180821 - 상품키워드 입력 기능 추가
		/* *********************** */

		function helpWin(state) {
			if(state) document.getElementById("ViewLayer").style.display = "";
			else document.getElementById("ViewLayer").style.display = "none";
		}


		function modifyFontweight ( con, param ){
			if( con == "tradeType" ){  // 거래 유형에 따라 이익률, 정상원가 입력필드 상태 변경.
				var tradeType = param;

				//특약1,2
			    if( tradeType == "2" || tradeType == "4" ) {
			    	$("input[name$=prftRate]").parent().prev().css("font-weight","bolder");		//이익률
			    	$("input[name$=norProdPcost]").parent().prev().css("font-weight","normal");	//원가
			  	}
			   	else {
			   	//직매입
			   		$("input[name$=prftRate]").parent().prev().css("font-weight","normal");		//이익률
			    	$("input[name$=norProdPcost]").parent().prev().css("font-weight","bolder");	//원가
			   	}
			}

			else if ( con == "warehouse" ) { // 유통일 관리 여부
				var val = param;

                if ( val == "1" || val == "2" ) {
                	$("#flowDd").parent().parent().prev().css("font-weight","bolder");
                	$("#stgePsbtDd").parent().parent().prev().css("font-weight","bolder");
                	$("#pickPsbtDd").parent().parent().prev().css("font-weight","bolder");
                }
                else {
                	$("#flowDd").parent().parent().prev().css("font-weight","normal");
                	$("#stgePsbtDd").parent().parent().prev().css("font-weight","normal");
                	$("#pickPsbtDd").parent().parent().prev().css("font-weight","normal");
                }
			}

			else if ( con == "productDivnCode" ) {
				var productDivision = param // 0:신선비규격, 1:규격, 5:패션

				if(productDivision == "1" || productDivision == "0" ) {
					$("#protectTagTypeCd").parent().prev().css("font-weight","normal");
				}
				else {
					$("#protectTagTypeCd").parent().prev().css("font-weight","bolder");
				}
				if( productDivision == "0" ) {
					$("#prodHrznLeng").parent().parent().prev().css("font-weight","normal");
				}
				else {
					$("#prodHrznLeng").parent().parent().prev().css("font-weight","bolder");
				}
			}
		}

		//폐기물,재활용 유형 체크
		function checkPlasticRecycle(){
			var prodTypeDivnCd = $("#prodTypeDivnCd option:checked").val();
			var sType = $("#prodTypeDivnCd option:checked").val();

			if(prodTypeDivnCd == 2 || (prodTypeDivnCd == 1 && (sType == 3))){
				var wasteFgName = $("#wasteFg option:checked").text();
				var recycleFg1Name = $("#recycleFg1 option:checked").text();
				var recycleFg2Name = $("#recycleFg2 option:checked").text();
				var recycleFg3Name = $("#recycleFg3 option:checked").text();
				if(wasteFgName=="선택"){
					alert("폐기물 유형을 선택해야합니다.");
					$("#wasteFg").focus();
					return false;
				}
				if(recycleFg1Name=="선택"){
					alert("재활용 유형1을 선택해야합니다.");
					$("#recycleFg1").focus();
					return false;
				}
				if(recycleFg2Name=="선택"){
					alert("재활용 유형2를 선택해야합니다.");
					$("#recycleFg2").focus();
					return false;
				}
				if(recycleFg3Name=="선택"){
					alert("재활용 유형3을 선택해야합니다.");
					$("#recycleFg3").focus();
					return false;
				}
			}
			return true;
		}


		function checkValuesIsValidatedByRep(){
			// 정규 표현식 값 체크

			// 상품 사이즈 가로 세로 높이 체크
			var hrzn = $('#prodHrznLeng').val();
			var vctl = $('#prodVtclLeng').val();
			var higt = $('#prodHigt').val();
			if(!validationByRep(hrzn,"onlyNumber") || !validationByRep(vctl,"onlyNumber") || !validationByRep(higt,"onlyNumber")){
				alert("숫자만 입력 가능합니다. (밀리미터 단위의 소수점 표기 제한됩니다)")
				$("#prodHrznLeng").focus();
				return false;
			}

			// 수입신고 번호 값 체크 (값이 입력되어 있다면 )
			var decno = $('#decno').val();
			if(decno && !validationByRep(decno,"decno")){
				alert("수입신고 번호는 숫자 13자리 + 문자 1자리 입니다.")
				$("#decno").focus();
				return false;
			}

			// HS코드 값 체크 (값이 입력되어 있다면 )
			var hscd = $('#hscd').val();
			if(hscd && !validationByRep(hscd,"hscd")){
				alert("HS코드는 숫자 10자리입니다.")
				$("#hscd").focus();
				return false;
			}

			// 관세율
			var tarrate = $('#tarrate').val();
			if(tarrate && !validationByRep(tarrate,"tarrate")){
				alert("정확한 관세율을 입력해주세요.")
				$("#tarrate").focus();
				return false;
			}

			// 정상매가
			var norProdSalePrc = $('#norProdSalePrc').val();
			if (norProdSalePrc && !validationByRep(norProdSalePrc,"onlyNumber")){
				alert("마트 정상매가는 숫자만 입력가능합니다.");
				$("#norProdSalePrc").focus();
				return false;
			}

			// 정상원가(vat 제외)
			var norProdPcost = $('#norProdPcost').val();
			if (norProdPcost && !validationByRep(norProdPcost,"onlyNumber")){
				alert("마트 정상원가는 숫자만 입력가능합니다.");
				$("#norProdPcost").focus();
				return false;
			}

			// VIC 정상매가
			var wnorProdSalePrc = $('#wnorProdSalePrc').val();
			if (wnorProdSalePrc && !validationByRep(wnorProdSalePrc,"onlyNumber")){
				alert("MAXX 정상매가는 숫자만 입력가능합니다.");
				$("#wnorProdSalePrc").focus();
				return false;
			}

			// VIC 정상원가(vat 제외)
			var wnorProdPcost = $('#wnorProdPcost').val();
			if (wnorProdPcost && !validationByRep(wnorProdPcost,"onlyNumber")){
				alert("MAXX 정상원가는 숫자만 입력가능합니다.");
				$("#wnorProdPcost").focus();
				return false;
			}

			// 슈퍼 정상매가
			var snorProdSalePrc = $('#snorProdSalePrc').val();
			if (snorProdSalePrc && !validationByRep(snorProdSalePrc,"onlyNumber")){
				alert("슈퍼 정상매가는 숫자만 입력가능합니다.");
				$("#snorProdSalePrc").focus();
				return false;
			}

			// 슈퍼 정상원가(vat 제외)
			var snorProdPcost = $('#snorProdPcost').val();
			if (snorProdPcost && !validationByRep(snorProdPcost,"onlyNumber")){
				alert("슈퍼 정상원가는 숫자만 입력가능합니다.");
				$("#snorProdPcost").focus();
				return false;
			}

			// 오카도(CFC) 정상매가
			var onorProdSalePrc = $('#onorProdSalePrc').val();
			if (onorProdSalePrc && !validationByRep(onorProdSalePrc,"onlyNumber")){
				alert("CFC 정상매가는 숫자만 입력가능합니다.");
				$("#onorProdSalePrc").focus();
				return false;
			}

			// 오카도(CFC) 정상원가(vat 제외)
			var onorProdPcost = $('#onorProdPcost').val();
			if (onorProdPcost && !validationByRep(onorProdPcost,"onlyNumber")){
				alert("CFC 정상원가는 숫자만 입력가능합니다.");
				$("#onorProdPcost").focus();
				return false;
			}

 			// 플라스틱 무게
 			var wasteFg = $("#wasteFg option:checked").val();
 			var wasteFgVal = $("#plasticWeight").val();
 			if((wasteFg==3 && !wasteFgVal) || (wasteFg==3 && wasteFgVal && !validationByRep(wasteFgVal,"weight"))){
 				alert("플라스틱 무게를 입력해주세요.");
 				$("#plasticWeight").focus();
 				return false;
 			}

 			var recycleFg1 = $("#recycleFg1 option:checked").val();
 			var recycleFg1Val = $("#recycleWeight1").val();
 			if((recycleFg1 && !recycleFg1Val) || (recycleFg1 && recycleFg1Val && !validationByRep(recycleFg1Val,"weight"))){
 				alert("재활용 무게1을 입력해주세요.");
 				$("#recycleWeight1").focus();
 				return false;
 			}

 			var recycleFg2 = $("#recycleFg2 option:checked").val();
 			var recycleFg2Val = $("#recycleWeight2").val();
 			if((recycleFg2 && !recycleFg2Val) || (recycleFg2 && recycleFg2Val && !validationByRep(recycleFg2Val,"weight"))){
 				alert("재활용 무게2를 입력해주세요.");
 				$("#recycleWeight2").focus();
 				return false;
 			}

 			var recycleFg3 = $("#recycleFg3 option:checked").val();
 			var recycleFg3Val = $("#recycleWeight3").val();
 			if((recycleFg3 && !recycleFg3Val) || (recycleFg3 && recycleFg3Val && !validationByRep(recycleFg3Val,"weight"))){
 				alert("재활용 무게3을 입력해주세요.");
 				$("#recycleWeight3").focus();
 				return false;
 			}

 			// 총중량
			var totWeight = $('#totWeight').val();
			if (totWeight && !validationByRep(totWeight,"totnetWeight")){
				alert("정확한 총중량을 입력해주세요.");
				$("#totWeight").focus();
				return false;
			}

			// 순중량
			var netWeight = $('#netWeight').val();
			if (netWeight && !validationByRep(netWeight,"totnetWeight")){
				alert("정확한 순중량을 입력해주세요.");
				$("#netWeight").focus();
				return false;
			}

			// 이익률
			var prftRate = $('#prftRate').val();
			if (prftRate && !validationByRep(prftRate,"prftRate")){
				alert("정확한 마트 이익률을 입력해주세요.");
				$("#prftRate").focus();
				return false;
			}

			// VIC이익률
			var wprftRate = $('#wprftRate').val();
			if (wprftRate && !validationByRep(wprftRate,"prftRate")){
				alert("정확한 MAXX 이익률을 입력해주세요.");
				$("#wprftRate").focus();
				return false;
			}
			
			// 슈퍼 이익률
			var sprftRate = $('#sprftRate').val();
			if (sprftRate && !validationByRep(sprftRate,"prftRate")){
				alert("정확한 슈퍼 이익률을 입력해주세요.");
				$("#sprftRate").focus();
				return false;
			}
			
			// 오카도 이익률
			var oprftRate = $('#oprftRate').val();
			if (oprftRate && !validationByRep(oprftRate,"prftRate")){
				alert("정확한 CFC 이익률을 입력해주세요.");
				$("#oprftRate").focus();
				return false;
			}

			return true;

		}


		// 200917 - 정규표현식 이용한 값  체크 추가
		function validationByRep(str,opt)
	    {
	        if(!str)
	        {
	          alert("검증할 값이 없습니다 [validationByRep]");
	          return false;
	        }

	        var bef_len = str.length;
	        var rst = 0;

	        if(opt == "tarrate"){ // ***.***(정수) 형태 (관세율)
	            rst = /^[0-9]{1,3}(\.[0-9]{1,3})?$/g.exec(str);
	        }
	        else if (opt == "hscd"){ // 정수 10자리 ( HS코드 )
	            rst = /^[0-9]{10}/g.exec(str);
	        }
	        else if (opt == "decno"){ // 숫자 13자리 + 문자 1자리 (수입신고번호)
	            rst = /^[0-9]{13}[a-zA-Z]{1}/g.exec(str);
	        }
	        else if (opt == "onlyNumber"){ // 숫자만 입력
	            rst = /^[0-9]+\b/g.exec(str);
	        }
	        else if (opt == "weight"){ // 숫자만 입력 ( 상품 사이즈 )
	            rst = /^[1-9][0-9]*\b/g.exec(str);
	        }
			else if (opt == "notUpperAndSpace"){ // 대문자 공백X ( 키워드 )
	            rst = /[^A-Z ]*/g.exec(str);
	        }
			else if (opt == "totnetWeight") { // *****(.**)_ 정수가능 (순중량,총중량)
				rst = /\b[0-9]{1,5}([.]{1}[0-9]{1,3})?\b/g.exec(str);
	    	}
				else if (opt == "prftRate") { //  **********(.**)_ 정수가능 (이익률,VIC이익률)
					rst = /\b[0-9]{1,10}([.]{1}[0-9]{1,3})?\b/g.exec(str);
		    	}
	        else {
	          alert("파라미터 값 잘못 입력하셨습니다. [validationByRep]");
	          return false ;
	        }

	        if(!rst)return false;
	        else {
	          if ( rst[0].length != bef_len)return false;
	          return true;
	        }
	    }

		function validFlowDdMgrCd2() {
			const flowDdMgrCd = document.getElementById("flowDdMgrCd").value;
			if (flowDdMgrCd !== '2') return true;

			//입고일
			const flowDdValue = document.getElementById("flowDd").value * 1;
			//입고 가능일
			const stgePsbtDdValue = document.getElementById("stgePsbtDd").value * 1;
			//출고 가능일
			const pickPsbtDdValue = document.getElementById("pickPsbtDd").value * 1;

			if ((flowDdValue && stgePsbtDdValue) &&
					flowDdValue < stgePsbtDdValue) {
				alert("입고가능일수는 유통일 보다 작거나 같아야 합니다.");
				$("#stgePsbtDd").focus();
				return false;
			}

			if ((stgePsbtDdValue && pickPsbtDdValue) &&
					stgePsbtDdValue < pickPsbtDdValue) {
				alert("출고 가능일수는 입고 가능일수 보다 작거나 같아야 합니다.");
				$("#pickPsbtDd").focus();
				return false;
			}

			return true;
		}

		//brand 선택팝업 callback
		function _fnCallbackBrand(data){
			//쇼카드 상품명 셋팅 이벤트 호출
			fnSetShowCardNm();
			//관리용 상품명 셋팅 이벤트 호출
			fnSetProdNm();
		}
		
		//쇼카드 상품명 셋팅 이벤트
		function fnSetShowCardNm(){
			var brandName = $.trim($("#brandName").val());		//브랜드명
			var prodShortNm = $.trim($("#prodShortNm").val());	//단축상품명
			
			//쇼카드명 = 브랜드 + 단축상품명
			var showCardNm = brandName+" "+prodShortNm;
			
			//쇼카드명 setting
			$("#showCardNm").val($.trim(showCardNm));
			$("#showCardNm").attr("title", $.trim(showCardNm));
		}
		
		//관리용 상품명 셋팅 이벤트
		function fnSetProdNm(){
			var brandName = $.trim($("#brandName").val());		//브랜드명
			var prodShortNm = $.trim($("#prodShortNm").val());	//단축상품명
			var prodStandardNm = $.trim($("#prodStandardNm").val());	//규격
			
			//관리용 상품명 = 브랜드 + 단축상품명 + 규격
			var prodNm = brandName+" "+prodShortNm+" "+prodStandardNm;
			//관리용 상품명 길이
			var prodNmLen = $.trim(prodNm).getBytes();
			
			//max bytes 넘을 경우, substring 처리하여 표시
			if(prodNmLen > 50){
				prodNm = prodNm.substrBytes(50);
			}
			
			//관리용 상품명 setting
			$("#prodNm").val($.trim(prodNm));
			$("#prodNm").attr("title", $.trim(prodNm));
		}

		//채널 체크박스 변경에 따른 원매가 정보 영역 컨트롤
		function fncSetPriceAreaByChan(chanCd, chked){
			if(chanCd == undefined || chanCd == null || chanCd == "") return;
			
			//해당 채널의 원매가정보 영역
			var chanProdTr;
			
			if("ALL" == chanCd){
				chanProdTr = $("#tblNewProdPrc").find("tr[data-chan]");
			}else{
				chanProdTr = $("#tblNewProdPrc").find("tr[data-chan='"+chanCd+"']");
			}
			
			if(!chanProdTr) return;
			
			if(chked){
				//원매가정보 활성화
				chanProdTr.css("display", "");
			}else{
				//원매가정보 비활성화
				chanProdTr.css("display", "none");
				//입력값 초기화
				chanProdTr.find("input, select").val("");
				//통화초기화
				chanProdTr.find("select[name$='Curr']").val("KRW");
			}
			
			var tradeType		=	$("#newProduct input[name='tradeType']").val();						//거래형태[1:직매입, 2:특약1, 4:특약2]
			_eventChangeFiledByTradeType(tradeType);
		}
		
		//채널별 원매가 체크
		function fncCheckChanPrice(){
			var flag = true;
			//채널 선택값에 따라 필수로 금액 입력했는지 체크
			var tradeType =	$("#newProduct input[name='tradeType']").val();		//거래형태[1:직매입, 2:특약1, 4:특약2]
			var selChanCd = "";			//선택한채널코드
			var selChanCdTxt = "";		//선택한채널명
			
			var selNorProdSalePrc = "";	//선택한채널의 매가
			var selNorProdPcost = "";	//선택한채널의 원가
			var selPrftRate = "";		//선택한채널의 이익률
			
			//선택한 채널 코드별 원매가 체크
			$("input[name='chanCd']:checked").each(function(){
				selChanCd = $.trim($(this).val());						//선택한 채널코드
				selChanCdTxt = $("label[for="+selChanCd+"]").text();	//선택한 채널명

				//선택한 채널의 원가, 매가, 이익률
				selNorProdSalePrc = $.trim($("#tblNewProdPrc").find("tr[data-chan="+selChanCd+"]").find("input[name$=norProdSalePrc]").val());	//정상매가
				selNorProdPcost = $.trim($("#tblNewProdPrc").find("tr[data-chan="+selChanCd+"]").find("input[name$=norProdPcost]").val());		//정상원가
				selPrftRate = $.trim($("#tblNewProdPrc").find("tr[data-chan="+selChanCd+"]").find("input[name$=prftRate]").val());				//이익률
				
				/*
				switch(tradeType){
					case "2":	//특약1 - 원가 필수 체크 X
					case "4":	//특약2 - 원가 필수 체크 X
						break;
					case "1":	//직매입 - 이익률 필수 체크 X
					default:
						break;
				}
				*/
				
				//이익률 확인
				if (Number(selPrftRate) > 100) {
					alert(selChanCdTxt + " 이익율은 100% 초과 할 수 없습니다.");
					$("#tblNewProdPrc").find("tr[data-chan="+selChanCd+"]").find("input[name$=prftRate]").focus();
					flag=false;
					return false;
				}
			});
			
			if(!flag) return false;
			else return true;
		}
		
		//온라인운영 여부 SETTING
		function fncSetVicOnlineCd(selVic){
			$("input[name='vicOnlineCd']").prop("disabled", true);
			
			var chanCdLen = $("input[name='chanCd']:checked").not(":disabled").length;
			var tradeType =	$("#newProduct input[name='tradeType']").val();	//거래형태[1:직매입, 2:특약1, 4:특약2]
			
			//직매입이 아니거나 선택한 채널 개수가 1건이 아닐 경우, 미사용
			if(tradeType != "1" || chanCdLen != 1){
				$("div.matchVicOnlinoption").hide();
				$("div.nomatchVicOnlinoption").show();
				return;
			}
			
			//선택한 채널
			var selChanCd = $.trim($("input[name='chanCd']:checked").not(":disabled").val());
			
			<%--
				[온라인여부 활성화조건] - 250703 슈퍼 추가
				1) 직매입 & 단일채널 (슈퍼)
					- 적용 : 선택불가
					- 미적용 : BOS 승인 프로세스 미진행으로 강제 셋팅
				2) 직매입 & 단일채널 (MAXX)
					- 적용 : BOS 승인 프로세스 진행
					- 미적용 : BOS 승인 프로세스 미진행
			--%>
			switch(selChanCd){
				case "KR03":	//MAXX 단일채널
					//온라인여부 영역 활성화
					$("div.matchVicOnlinoption").show();
					$("div.nomatchVicOnlinoption").hide();
					//온라인여부 선택가능하도록 open
					$("input[name='vicOnlineCd']").prop("disabled", false);
					
					//선택한 값이 있을 경우, 체크해줌
					if($.trim(selVic) != ""){
						$("input[name='vicOnlineCd'][value='"+selVic+"']").attr("checked", true);	
					}
					break;
				case "KR04":	//SUPER 단일채널
					//온라인여부 영역 활성화
					$("div.matchVicOnlinoption").show();
					$("div.nomatchVicOnlinoption").hide();
					
					//온라인여부 = 미적용
					$("input[name='vicOnlineCd']").eq(0).attr("checked", true);
					break;
				default:
					//온라인여부 영역 비활성화
					$("div.matchVicOnlinoption").hide();
					$("div.nomatchVicOnlinoption").show();
					break;
			}
			
		}
		
		function setProdShortNmByte() {
			let prodNmByteElem = document.getElementById("prodShortNmByte");
			let prodNmElem = document.getElementById("prodShortNm");

			if (!prodNmElem.value) return;
			const PROD_NM_MAX_BYTE = 30;
			const prodNmByte = keyUpCalByteProd(prodNmElem);
			renderByteStr(prodNmByteElem, prodNmByte, PROD_NM_MAX_BYTE);
		}

		function regKeyUpEvent() {
			let prodNmByteElem = document.getElementById("prodShortNmByte");
			let prodNmElem = document.getElementById("prodShortNm");
			const PROD_NM_MAX_BYTE = 30;

			prodNmElem.addEventListener('keyup', () => {
				const calculatedByte = keyUpCalByteProd(prodNmElem);
				renderByteStr(prodNmByteElem, calculatedByte, PROD_NM_MAX_BYTE);
			});
		}

		function renderByteStr(targetElem, calculatedByte, inputMaxByte) {
			const templateHtmlStr = calculatedByte;
			targetElem.innerHTML = templateHtmlStr;
		}

		function keyUpCalByteProd(targetElem) {

			let tmpStr;
			let temp = 0;
			let onechar;
			let tcount;
			tcount = 0;
			let aquery = targetElem.value

			tmpStr = new String(aquery);
			temp = tmpStr.length;

			for (k = 0 ; k < temp ; k++) {
				onechar = tmpStr.charAt(k);

				if (escape(onechar).length > 4) {
					tcount += 2;
				} else tcount++;
			}

			return tcount;
		}

		function removeWeightUnitForOacdo () {
			// 기존 단위 오카도 연동에 불필요한 단위 제거
			$("#weightUnit option[value='TON']").remove();
			$("#weightUnit option[value='LB']").remove();
			$("#weightUnit option[value='OZ']").remove();
			$("#weightUnit option[value='KT']").remove();
			$("#weightUnit option[value='TO']").remove();
			$("#weightUnit option[value='MG']").remove();
		}
		
		//카피대상 채널정보 setting
		function fncSetCopyPrcTargetChan(){
			//오카도 체크여부
			var isSelOca = $("input[name='chanCd'][value='KR09']").not(":disabled").is(":checked");
			if(!isSelOca) {
				$("#copyPrc").prop("disabled", true);
				$("#copyPrc").prop("checked", false);
				copyPrcChanCd = "";	//카피대상 초기화
				return;
			}
			
			//오카도 외에 체크된 채널이 있는지 체크
			var chkLen = $("input[name='chanCd'][value!='KR09']:checked").not(":disabled").length;
			if(chkLen == 0){
				$("#copyPrc").prop("checked", false);
				$("#copyPrc").prop("disabled", true);
				copyPrcChanCd = "";	//카피대상 초기화
				return;
			}
			
			$("#copyPrc").prop("disabled", false);
			
			//채널체크여부확인
			var isMart = $("input[name='chanCd'][value='KR02']").not(":disabled").is(":checked");	//마트 선택
			var isMaxx = $("input[name='chanCd'][value='KR03']").not(":disabled").is(":checked");	//maxx 선택
			var isSuper = $("input[name='chanCd'][value='KR04']").not(":disabled").is(":checked");	//슈퍼 선택
			
			//copy 대상 채널 설정 마트>MAXX>슈퍼 순
			if(isMart) copyPrcChanCd = "KR02";
			else if(isMaxx) copyPrcChanCd = "KR03";
			else if(isSuper) copyPrcChanCd = "KR04";
			
			//오카도 > 타채널 원매가 Copy
			fncCopyPrc();
		}
		
		//오카도 > 타채널 원매가 Copy
		function fncCopyPrc(){
			//가격 copy check되어 있는지 체크
			var isChked = $("#copyPrc").is(":checked");
			if(!isChked) return;
			
			//copy 값
			var copyNorProdSalePrc
				, copyNorProdSaleCurr
				, copyNorProdPcost
				, copyNorProdCurr
				, copyPrftRate;
		
			//copy 대상 채널 없으면 Copy불가
			if(copyPrcChanCd == ""){
				$("#copyPrc").prop("checked", false);
				$("#copyPrc").prop("disabled", true);
				return;
			} 
			
			//copy된 값 setting
			var copyNorProdSalePrc = $.trim($("#tblNewProdPrc").find("tr[data-chan="+copyPrcChanCd+"]").find("input[name$=norProdSalePrc]").val());		//정상매가
			var copyNorProdSaleCurr = $.trim($("#tblNewProdPrc").find("tr[data-chan="+copyPrcChanCd+"]").find("select[name$=norProdSaleCurr]").val());	//정상매가 단위
			var copyNorProdPcost = $.trim($("#tblNewProdPrc").find("tr[data-chan="+copyPrcChanCd+"]").find("input[name$=norProdPcost]").val());			//정상원가
			var copyNorProdCurr = $.trim($("#tblNewProdPrc").find("tr[data-chan="+copyPrcChanCd+"]").find("select[name$=norProdCurr]").val());			//정상원가 단위
			var copyPrftRate = $.trim($("#tblNewProdPrc").find("tr[data-chan="+copyPrcChanCd+"]").find("input[name$=prftRate]").val());					//이익률
			
			<%-- 거래유형에 따라 원가 or 이익률 입력 불가하므로 disabled 되지 않은 영역만 복사하도록 처리함 --%>
			$("#onorProdSalePrc").not(":disabled").val(copyNorProdSalePrc);
			$("#onorProdSaleCurr").not(":disabled").val(copyNorProdSaleCurr);
			$("#onorProdPcost").not(":disabled").val(copyNorProdPcost);
			$("#onorProdCurr").not(":disabled").val(copyNorProdCurr);
			$("#oprftRate").not(":disabled").val(copyPrftRate);
		}
		
		//(슈퍼) 신상품장려금 일자 setting
		function fncSetsSNewProdStDy(evntGbn){
			<%--
				슈퍼 신상품장려금 적용 시, 슈퍼신상품출시일 default 값으로 강제셋팅 (등록일자)
				1) 등록 : 최초저장일자(오늘날짜)
				2) 수정 : 최초저장일자
				3) 복사 : 오늘날짜 
			--%>
			let sNewProdStDyDef = "";
			<c:choose>
				<c:when test='${not empty newProdDetailInfo.pgmId}'>
				if($.trim(evntGbn) == "CP"){	//복사
					sNewProdStDyDef = "<c:out value='${today}'/>";					
				}else{							//저장
					sNewProdStDyDef = "<c:out value='${newProdDetailInfo.regDy}'/>";					
				}
				</c:when>
				<c:otherwise>
				sNewProdStDyDef = "<c:out value='${today}'/>";
				</c:otherwise>
			</c:choose>
			
			const sNewProdStDyDefFmt = $.trim(sNewProdStDyDef).replace(/\D/g, "").substring(0,8).replace(/(\d{4})(\d{2})(\d{2})/g,"$1-$2-$3");
			$("#sNewProdStDy").val(sNewProdStDyDefFmt);
		}
		
	//기본 전시카테고리 Grid init
	function initGrid_dispCtg(){
		var gridId = "tabList_dispCtg";
		var blankRowId = "blankRow_dispCtg";
		
		$("#"+gridId).jqGrid({
			datatype: "local",  // 보내는 데이터 타입
			data: [],
			// 컬럼명
			colNames:[
				'전시카테고리'
				, '전시카테고리아이디'
				, '카테고리 상태값'
			],
			// 헤더에 들어가는 이름
			colModel:[
				{name:'FULL_CATEGORY_NM'	, index:'FULL_CATEGORY_NM'		, sortable:false		, width:470		,align:"left"},
				{name:'CATEGORY_ID'			, index:'CATEGORY_ID'			, sortable:false		, hidden: true},
				{name:'DISP_YN'				, index:'DISP_YN'				, sortable:false		, width:145		,align:"center"
					, formatter : function(cellvalue, options, rowObject) {
						var retVal = "";
						if($.trim(cellvalue).toUpperCase() == "Y") retVal = "전시";
						else retVal = "미전시";
						return retVal;
					}
				}
			],
			gridComplete : function() {                                      // 데이터를 성공적으로 가져오면 실행 됨
				var colCount = $(this).getGridParam("colNames").length;
				$("#"+blankRowId+" td:nth-child(2)").attr("colspan", colCount).attr("style", "text-align: center;");
				$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
				$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");
				$(window).resize();
			},
			loadComplete: function() {
				if ($(this).getGridParam("records")==0) {
					<%--조회결과가 없습니다. --%>
						$(this).addRowData(blankRowId, {});
						$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
						$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");	
						
				}else{
					$(".ui-jqgrid .ui-jqgrid-btable").css("cursor","pointer");
// 		 				var allRows = $(this).jqGrid('getDataIDs'); //전체 행 가져오기
// 		 				for(var i = 0; i < allRows.length; i++){
// 		 					var cl = allRows[i];
// 		 					var rowData = $(this).jqGrid('getRowData', cl);
// 	 					}
				}
				$(window).resize();
			},
			loadError:function(xhr, status, error) {										//데이터 못가져오면 실행 됨
				alert("처리중 오류가 발생했습니다.");
				return false;
			},
			onSelectRow : function(rowid, colld, val, e) {		//행 선택시 이벤트
				if(rowid === blankRowId) return;	//조회결과가 없을 경우 클릭 이동(redirect) 막음
				
				var rowdata = $(this).getRowData(rowid);		// 선택한 행의 데이터를 가져온다
				if(rowdata == null) return;
				
			},
			<%-- jqGrid 속성, 필요에 따라 주석처리 하여 사용 (삭제X) --%>
			page: 1,															// 현재 페이지
// 			rowNum: 50,															// 한번에 출력되는 갯수
// 			rowList:[50,100,200,500],											// 한번에 출력되는 갯수 SelectBox
// 			pager: '#pageList',													// page가 보여질 div
			loadui : "disable",													// 이거 안 써주니 로딩 창 같은게 뜸
			emptyrecords : "조회결과가 없습니다.",  									// row가 없을 경우 출력 할 text
			gridview: true,														// 그리드 속도
			viewrecords: true,													// 하단에 1/1 또는 데이터가없습니다 추가
			rownumbers:true,													// rowNumber 표시여부
	 		sortorder: "DESC", 		                                      		// desc/asc (default:asc)
// 			loadonce : false,													// reload 여부. [true: 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않음]
	  		multiselect	: true,													// 체크박스 show
			scroll : false,														// 스크롤 페이징 여부
			autowidth:true,
			shrinkToFit:true													// 컬럼 width 자동지정여부 (가로 스크롤 이용하기 위해 false지정)
		});
			
		// 그리드의 width, height 적용
	    $("#"+gridId).setGridWidth("640px");
	    $("#"+gridId).setGridHeight("120px");
	}
	
	//롯데ON EC 전시카테고리 Grid init
	function initGrid_ecOnDispCtg(){
		var gridId = "tabList_ecOnDispCtg";
		var blankRowId = "blankRow_ecOnDispCtg";
		
		$("#"+gridId).jqGrid({
			datatype: "local",  // 보내는 데이터 타입
			data: [],
			// 컬럼명
			colNames:[
				'롯데ON 전시카테고리'
				, '전시카테고리아이디'
				, '카테고리 상태값'
			],
			// 헤더에 들어가는 이름
			colModel:[
				{name:'DISP_CAT_NM'			, index:'DISP_CAT_NM'			, sortable:false		, width:470		,align:"left"},
				{name:'DISP_CAT_CD'			, index:'DISP_CAT_CD'			, sortable:false		, hidden: true},
				{name:'DISP_YN'				, index:'DISP_YN'				, sortable:false		, width:145		,align:"center"
					, formatter : function(cellvalue, options, rowObject) {
						var retVal = "";
						if($.trim(cellvalue).toUpperCase() == "Y") retVal = "전시";
						else retVal = "미전시";
						return retVal;
					}
				}
			],
			gridComplete : function() {                                      // 데이터를 성공적으로 가져오면 실행 됨
				var colCount = $(this).getGridParam("colNames").length;
				$("#"+blankRowId+" td:nth-child(2)").attr("colspan", colCount).attr("style", "text-align: center;");
				$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
				$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");
				$(window).resize();
			},
			loadComplete: function() {
				if ($(this).getGridParam("records")==0) {
					<%--조회결과가 없습니다. --%>
						$(this).addRowData(blankRowId, {});
						$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
						$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");	
						
				}else{
					$(".ui-jqgrid .ui-jqgrid-btable").css("cursor","pointer");
// 		 				var allRows = $(this).jqGrid('getDataIDs'); //전체 행 가져오기
// 		 				for(var i = 0; i < allRows.length; i++){
// 		 					var cl = allRows[i];
// 		 					var rowData = $(this).jqGrid('getRowData', cl);
// 	 					}
				}
				$(window).resize();
			},
			loadError:function(xhr, status, error) {										//데이터 못가져오면 실행 됨
				alert("처리중 오류가 발생했습니다.");
				return false;
			},
			onSelectRow : function(rowid, colld, val, e) {		//행 선택시 이벤트
				if(rowid === blankRowId) return;				//조회결과가 없을 경우 클릭 이동(redirect) 막음
				
				var rowdata = $(this).getRowData(rowid);		// 선택한 행의 데이터를 가져온다
				if(rowdata == null) return;
				
			},
			<%-- jqGrid 속성, 필요에 따라 주석처리 하여 사용 (삭제X) --%>
			page: 1,															// 현재 페이지
// 			rowNum: 50,															// 한번에 출력되는 갯수
// 			rowList:[50,100,200,500],											// 한번에 출력되는 갯수 SelectBox
// 			pager: '#pageList',													// page가 보여질 div
			loadui : "disable",													// 이거 안 써주니 로딩 창 같은게 뜸
			emptyrecords : "조회결과가 없습니다.",  									// row가 없을 경우 출력 할 text
			gridview: true,														// 그리드 속도
			viewrecords: true,													// 하단에 1/1 또는 데이터가없습니다 추가
			rownumbers:true,													// rowNumber 표시여부
	 		sortorder: "DESC", 		                                      		// desc/asc (default:asc)
// 			loadonce : false,													// reload 여부. [true: 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않음]
	  		multiselect	: true,													// 체크박스 show
			scroll : false,														// 스크롤 페이징 여부
			autowidth:true,
			shrinkToFit:true													// 컬럼 width 자동지정여부 (가로 스크롤 이용하기 위해 false지정)
		});
			
		// 그리드의 width, height 적용
	    $("#"+gridId).setGridWidth("640px");
	    $("#"+gridId).setGridHeight("95px");
	}
	
	//롯데몰 EC 전시카테고리 Grid init
	function initGrid_ecMallDispCtg(){
		var gridId = "tabList_ecMallDispCtg";
		var blankRowId = "blankRow_ecMallDispCtg";
		
		$("#"+gridId).jqGrid({
			datatype: "local",  // 보내는 데이터 타입
			data: [],
			// 컬럼명
			colNames:[
				'EC 롯데마트몰 전시카테고리'
				, '전시카테고리아이디'
				, '카테고리 상태값'
			],
			// 헤더에 들어가는 이름
			colModel:[
				{name:'DISP_CAT_NM'			, index:'DISP_CAT_NM'			, sortable:false		, width:470		,align:"left"},
				{name:'DISP_CAT_CD'			, index:'DISP_CAT_CD'			, sortable:false		, hidden: true},
				{name:'DISP_YN'				, index:'DISP_YN'				, sortable:false		, width:145		,align:"center"
					, formatter : function(cellvalue, options, rowObject) {
						var retVal = "";
						if($.trim(cellvalue).toUpperCase() == "Y") retVal = "전시";
						else retVal = "미전시";
						return retVal;
					}
				}
			],
			gridComplete : function() {                                      // 데이터를 성공적으로 가져오면 실행 됨
				var colCount = $(this).getGridParam("colNames").length;
				$("#"+blankRowId+" td:nth-child(2)").attr("colspan", colCount).attr("style", "text-align: center;");
				$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
				$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");
				$(window).resize();
			},
			loadComplete: function() {
				if ($(this).getGridParam("records")==0) {
					<%--조회결과가 없습니다. --%>
						$(this).addRowData(blankRowId, {});
						$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
						$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");	
						
				}else{
					$(".ui-jqgrid .ui-jqgrid-btable").css("cursor","pointer");
// 		 				var allRows = $(this).jqGrid('getDataIDs'); //전체 행 가져오기
// 		 				for(var i = 0; i < allRows.length; i++){
// 		 					var cl = allRows[i];
// 		 					var rowData = $(this).jqGrid('getRowData', cl);
// 	 					}
				}
				$(window).resize();
			},
			loadError:function(xhr, status, error) {										//데이터 못가져오면 실행 됨
				alert("처리중 오류가 발생했습니다.");
				return false;
			},
			onSelectRow : function(rowid, colld, val, e) {		//행 선택시 이벤트
				if(rowid === blankRowId) return;				//조회결과가 없을 경우 클릭 이동(redirect) 막음
				
				var rowdata = $(this).getRowData(rowid);		// 선택한 행의 데이터를 가져온다
				if(rowdata == null) return;
				
			},
			<%-- jqGrid 속성, 필요에 따라 주석처리 하여 사용 (삭제X) --%>
			page: 1,															// 현재 페이지
// 			rowNum: 50,															// 한번에 출력되는 갯수
// 			rowList:[50,100,200,500],											// 한번에 출력되는 갯수 SelectBox
// 			pager: '#pageList',													// page가 보여질 div
			loadui : "disable",													// 이거 안 써주니 로딩 창 같은게 뜸
			emptyrecords : "조회결과가 없습니다.",  									// row가 없을 경우 출력 할 text
			gridview: true,														// 그리드 속도
			viewrecords: true,													// 하단에 1/1 또는 데이터가없습니다 추가
			rownumbers:true,													// rowNumber 표시여부
	 		sortorder: "DESC", 		                                      		// desc/asc (default:asc)
// 			loadonce : false,													// reload 여부. [true: 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않음]
	  		multiselect	: true,													// 체크박스 show
			scroll : false,														// 스크롤 페이징 여부
			autowidth:true,
			shrinkToFit:true													// 컬럼 width 자동지정여부 (가로 스크롤 이용하기 위해 false지정)
		});
			
		// 그리드의 width, height 적용
	    $("#"+gridId).setGridWidth("640px");
	    $("#"+gridId).setGridHeight("95px");
	}
	
	//ZETTA 전시카테고리 Grid init
	function initGrid_zettaDispCtg(){
		var gridId = "tabList_zettaDispCtg";
		var blankRowId = "blankRow_zettaDispCtg";
		
		$("#"+gridId).jqGrid({
			datatype: "local",  // 보내는 데이터 타입
			data: [],
			// 컬럼명
			colNames:[
				'ZETTA 전시카테고리'
				, 'ZETTA 카테고리아이디'
				, '전시유무'
				, '대표값'
			],
			// 헤더에 들어가는 이름
			colModel:[
				{name:'OSP_CATEGORY_FULL_NM'	, index:'OSP_CATEGORY_FULL_NM'		, sortable:false		, width:465		,align:"left"},
				{name:'OSP_CATEGORY_ID'			, index:'OSP_CATEGORY_ID'			, sortable:false		, hidden: true},
				{name:'OSP_CATEGORY_DISP_YN'	, index:'OSP_CATEGORY_DISP_YN'		, sortable:false		, width:75		,align:"center"
					, formatter : function(cellvalue, options, rowObject) {
						var retVal = "";
						if($.trim(cellvalue).toUpperCase() == "Y") retVal = "전시";
						else retVal = "미전시";
						return retVal;
					}
				},
				{name:'OSP_CATEGORY_REP'		, index:'OSP_CATEGORY_REP'			, sortable:false		, width:75		,align:"center"}
			],
			gridComplete : function() {                                      // 데이터를 성공적으로 가져오면 실행 됨
				var colCount = $(this).getGridParam("colNames").length;
				$("#"+blankRowId+" td:nth-child(2)").attr("colspan", colCount).attr("style", "text-align: center;");
				$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
				$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");
				$(window).resize();
			},
			loadComplete: function() {
				if ($(this).getGridParam("records")==0) {
					<%--조회결과가 없습니다. --%>
						$(this).addRowData(blankRowId, {});
						$(this).find("#"+blankRowId+" td:nth-child(2)").empty();
						$(this).find("#"+blankRowId+" td:nth-child(2)").append("조회결과가 없습니다.");	
						
				}else{
					$(".ui-jqgrid .ui-jqgrid-btable").css("cursor","pointer");
// 		 				var allRows = $(this).jqGrid('getDataIDs'); //전체 행 가져오기
// 		 				for(var i = 0; i < allRows.length; i++){
// 		 					var cl = allRows[i];
// 		 					var rowData = $(this).jqGrid('getRowData', cl);
// 	 					}
				}
				$(window).resize();
			},
			loadError:function(xhr, status, error) {										//데이터 못가져오면 실행 됨
				alert("처리중 오류가 발생했습니다.");
				return false;
			},
			onSelectRow : function(rowid, colld, val, e) {		//행 선택시 이벤트
				if(rowid === blankRowId) return;				//조회결과가 없을 경우 클릭 이동(redirect) 막음
				
				var rowdata = $(this).getRowData(rowid);		// 선택한 행의 데이터를 가져온다
				if(rowdata == null) return;
				
			},
			<%-- jqGrid 속성, 필요에 따라 주석처리 하여 사용 (삭제X) --%>
			page: 1,															// 현재 페이지
// 			rowNum: 50,															// 한번에 출력되는 갯수
// 			rowList:[50,100,200,500],											// 한번에 출력되는 갯수 SelectBox
// 			pager: '#pageList',													// page가 보여질 div
			loadui : "disable",													// 이거 안 써주니 로딩 창 같은게 뜸
			emptyrecords : "조회결과가 없습니다.",  									// row가 없을 경우 출력 할 text
			gridview: true,														// 그리드 속도
			viewrecords: true,													// 하단에 1/1 또는 데이터가없습니다 추가
			rownumbers:true,													// rowNumber 표시여부
	 		sortorder: "DESC", 		                                      		// desc/asc (default:asc)
// 			loadonce : false,													// reload 여부. [true: 한번만 데이터를 받아오고 그 다음부터는 데이터를 받아오지 않음]
	  		multiselect	: true,													// 체크박스 show
			scroll : false,														// 스크롤 페이징 여부
			autowidth:true,
			shrinkToFit:true													// 컬럼 width 자동지정여부 (가로 스크롤 이용하기 위해 false지정)
		});
			
		// 그리드의 width, height 적용
	    $("#"+gridId).setGridWidth("640px");
	    $("#"+gridId).setGridHeight("130px");
	}
		
	//grid data list 조회
	function eventSearchGrid(gridGbn){
		var searchInfo = {};	//검색조건
		var gridUrl = "";			//조회 url
		
		var gridId = "tabList_"+$.trim(gridGbn);
		
		switch(gridGbn){
			case "dispCtg":		//기본 전시카테고리
				//parameter
				searchInfo.categoryId = $.trim($("#categoryId").val());		//카테고리 아이디
				//url
				gridUrl = "<c:url value='/edi/product/NEDMPRO0020Category.do'/>";
				break;
			case "ecOnDispCtg":	//롯데 ON EC 전시카테고리
				//parameter
				searchInfo.stdCatCd 	= $("#stdCatCd").val();
				searchInfo.mallCd 	= "LTON";		// 롯데ON
				searchInfo.pgmId 	= "<c:out value='${newProdDetailInfo.pgmId}'/>"; // 롯데ON
				//url
				gridUrl = "<c:url value='/edi/product/ecDisplayCategory.do'/>";
				break;
			case "ecMallDispCtg":	//롯데 몰 EC 전시카테고리
				//parameter
				searchInfo.stdCatCd 	= $("#stdCatCd").val();
				searchInfo.mallCd 	= "LTMT,TOYS";		// 롯데마트, 토이저러스
				searchInfo.pgmId 	= "<c:out value='${newProdDetailInfo.pgmId}'/>"; // 롯데ON
				//url
				gridUrl = "<c:url value='/edi/product/ecDisplayCategory.do'/>";
				break;
			case "zettaDispCtg":	//Zetta 전시카테고리
				//parameter
				searchInfo.pgmId 	= "<c:out value='${newProdDetailInfo.pgmId}'/>"; // 롯데ON
				//url
				gridUrl = "<c:url value='/edi/product/ospDisplayCategory.do'/>";
				break;
			default:
				break;
		}
		
		//조회 대상 url 없음
		if("" == gridUrl) {
			alert("조회 대상 정보가 올바르지 않습니다.");
			return;
		}
		
		//grid data clear
		$("#"+gridId).jqGrid('clearGridData');
		
		$("#"+gridId).jqGrid('setGridParam',{
			url: gridUrl,		// url 주소
			datatype: "json" ,													// 보내는 데이터 타입
			ajaxGridOptions: { contentType: 'application/x-www-form-urlencoded; charset=utf-8' },
			postData: searchInfo,												// 보내는 데이터 형식
			mtype:'POST',														// POST,GET,LOCAL 3가지 존재
			page: 1,
	  		loadBeforeSend: function(jqXHR) {		// spring security 사용 시 추가
	  			jqXHR.setRequestHeader("X-CSRF-TOKEN", $("input[name='_csrf']").val());
	  		},
			jsonReader : {
				root:  "list",		//조회결과 데이터
				page: "page",		//현재 페이지	
				total: "total",		//총 페이지 수
				records: "records",	// 전체 조회된 데이터 총갯수 
				repeatitems: false
			}
		}).trigger("reloadGrid");
	}
		
	//jqgrid row 삭제
	function fncDelGridRow(gridGbn){
		var gridId = "tabList_"+gridGbn;		//그리드 아이디
		var blankRowId = "blankRow_"+gridGbn;	//조회결과 없음 행 아이디 
		
		var selRowIds = $("#"+gridId).jqGrid('getGridParam', 'selarrrow');	//선택된 rowId list
		var selRowLen = selRowIds.length;
		
		//선택된 행 없음
		if(selRowLen == 0){
			alert("삭제할 행을 선택해주세요.");
			return;
		}
		
		let flag = true;
		//------ Zetta 전시카테고리일 경우 추가 체크
		if(gridGbn == "zettaDispCtg"){
			//대표 카테고리 포함되어 있으면 삭제 불가
			for(let j = 0; j < selRowLen; j++){
				//rowData 조회
				var rowData = $("#"+gridId).jqGrid('getRowData', selRowIds[j]);
				//대표 카테고리일 경우
				if($.trim(rowData.OSP_CATEGORY_REP) === "Y"){
					alert("대표카테고리는 수정해서 삭제하실 수 있습니다.");
					flag = false;
					return;
				}
			}
		}
		//---------------------------------
		
		if(!flag) return;
		
		//delete row
		for(var i = selRowLen-1; i >= 0; i--){
			$("#"+gridId).jqGrid('delRowData', selRowIds[i]);
		}
		
		var allDataLen = $("#"+gridId).jqGrid("getGridParam", "reccount");	//전체 data length 체크
		
		//데이터가 한 건도 없을 경우, 조회결과 없음 넣어준다
		if(allDataLen == 0){
			$("#"+gridId).addRowData(blankRowId, {});
		}
	}
	
	//그리드 데이터 초기화
	function fncClearAllGridData(gridGbn){
		var gridId = "tabList_"+gridGbn;		//그리드 아이디
		var blankRowId = "blankRow_"+gridGbn;	//조회결과 없음 행 아이디 
		
		//그리드 데이터 초기화
		$("#"+gridId).jqGrid('clearGridData');
		
		//조회 결과가 없습니다 setting
		$("#"+gridId).jqGrid('addRowData', blankRowId, {}, 'last');
	}
	
	//그리드 데이터 카운트 조회
	function fncGetGridDataRowCount(gridGbn){
		var gridId = "tabList_"+gridGbn;		//그리드 아이디
		var blankRowId = "blankRow_"+gridGbn;	//조회결과 없음 행 아이디 
		
		//조회결과 없음 행 제외 datalist 확인
		var dataRows = $("#"+gridId).jqGrid('getDataIDs').filter(v=>v != blankRowId);
		var rowCount = dataRows.length || 0;
		
		return rowCount;
	}
	
	//그리드 전체 행번호 반환
	function fncGetGridRowDataIds(gridGbn){
		var gridId = "tabList_"+gridGbn;		//그리드 아이디
		var blankRowId = "blankRow_"+gridGbn;	//조회결과 없음 행 아이디 
		
		var allDataRows = $("#"+gridId).jqGrid('getDataIDs').filter(v=>v != blankRowId);
		
		return allDataRows;
	}
</script>
</head>

<body>
	<div id="content_wrap">
		<div>
			<div id="wrap_menu">
				<!-- 상단 신규상품등록 안내문구와 저장버튼 출력 부분 -------------------------------------------------->
				<div class="wrap_search">
					<!-- tab 구성---------------------------------------------------------------->
					<div id="prodTabs" class="tabs" style="padding-top: 10px;">
						<ul>
							<li id="pro01" class="on"><spring:message
									code="msg.product.onOff.default.notice" /></li>
							<li id="pro02" style="cursor: pointer;"><spring:message
									code='msg.product.onOff.default.itemProperty' /></li>
							<li id="pro03" style="cursor: pointer;"><spring:message
									code='msg.product.tab.img' /></li>
							<li id="pro04" style="cursor: pointer;">영양성분</li>
							<li id="pro05" style="cursor: pointer;">ESG</li>

						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->

					<div class="bbs_search">
						<ul class="tit">

							<!-- 상품상세보기 모드에 따른 Txt 문구 설정[view:신상품등록현황 상세보기, modify:임시보관함 상품상세보기화면, 그외:온오프상품등록화면]--------------------------------------------------------------------->
							<c:if test="${ not empty param.mode}">
								<c:choose>
									<c:when test="${param.mode eq 'view'}">
										<li class="tit"><spring:message
												code="msg.product.prodInfo" /></li>
									</c:when>
									<c:when test="${param.mode eq 'modify'}">
										<li class="tit">* <spring:message
												code="msg.product.newProdModify" /> : <spring:message
												code="msg.product.onOff.register.notice" /></li>
									</c:when>
								</c:choose>
							</c:if>

							<c:if test="${empty param.mode}">
								<li class="tit">* <spring:message
									code="msg.product.onOff.default.footerRegNewItem" /> : <spring:message code="msg.product.onOff.register.notice" />
								</li>
							</c:if>
							<!------------------------------------------------------------------------------------------------->


							<!-- view[신상품현황 상세보기 복사버튼만],	modify[임시보관함 상세보기 저장, 복사버튼], ''[온오프상품등록] --------------------->
							<li class="btn"><c:if test="${ not empty param.mode}">
									<c:choose>
										<c:when test="${param.mode eq 'view'}">
											<!-- 신상품 등록현황 조회 리스트에서 넘어 올 경우 -->
											<c:if test="${param.cfmFg ne '4' && param.cfmFg ne '6'}">
												<!-- 상품확정요청 상태가 위해상품 && 거절이 아닐때만 복사가능 -->
												<spring:message code="msg.product.copyTxt" />
												<a href="#" class="btn" onclick="_eventCopy();">
													<span><spring:message code="msg.product.btnCopy" />
												</a>
											</c:if>
										</c:when>
										<c:when test="${param.mode eq 'modify'}">
											<!-- 임시보관힘 리스트에서 넘어오거나 신규로 온오프 상품을 등록하고 난 이후 -->
											<a href="#" class="btn" onclick="_eventSave();">
												<span><spring:message code="button.common.save" /></span>
											</a>
											<a href="#" class="btn" onclick="_eventCopy();">
												<span><spring:message code="msg.product.btnCopy" /></span>
											</a>
										</c:when>
									</c:choose>
								</c:if> <c:if test="${empty param.mode}">
									<a href="#" class="btn" onclick="_eventSave();">
										<span><spring:message code="button.common.save" /></span>
									</a>
								</c:if></li>
							<!------------------------------------------------------------------------------------------------->

						</ul>
					</div>
					<!-- 상단 신규상품등록 안내문구와 저장버튼 출력 부분  끝-------------------------------------------------->


					<!-- 상품정보 입력란 기본정보 시작 --------------------------------------------------------------->
					<form name="newProduct" id="newProduct">

						<input type="hidden" name="prodAddMasterCd" id="prodAddMasterCd" />
						<input type="hidden" name="prodCertMasterCd" id="prodCertMasterCd" />
						<input type="hidden" name="prodCertDtlCd" id="prodCertDtlCd" value="" />
						<input type="hidden" name="tradeType" id="tradeType" value="<c:out value='${newProdDetailInfo.trdTypeDivnCd}'/>" />
						<input type="hidden" name="onOffDivnCd" id="onOffDivnCd" value="0" />
						<!-- 온오프상품구분[0:온오프, 1:온라인, 2:소셜] -->
						<input type="hidden" name="maxKeepDdCnt" id="maxKeepDdCnt" value="0" />
						<!-- 최대보관일수 -->
						<input type="hidden" name="brandCode" id="brandCode" value="<c:out value='${newProdDetailInfo.brandCode}'/>" />
						<!-- 브랜드코드 -->
<%-- 						<input type="hidden" name="makerCode" id="makerCode" value="<c:out value='${newProdDetailInfo.makerCode}'/>" /> --%>
						<!-- 메이커코드 -->
						<input type="hidden" name="prodImgId" id="prodImgId" value="<c:out value='${newProdDetailInfo.prodImgId}'/>" />
						<!-- 메이커코드 -->
						<input type="hidden" name="newProdGenDivnCd" id="newProdGenDivnCd" value="EDI" />
						<input type="hidden" name="categoryId" id="categoryId" value="<c:out value='${newProdDetailInfo.categoryId}'/>" />
						<!-- 신상품장려금 적용 파트너사 여부 'X'이면 적용 -->
						<input type="hidden" name="newPromoVenFg" id="newPromoVenFg" value="">
						<!-- 200306 EC전용 카테고리 -->
						<input type="hidden" name="stdCatCd"  id="stdCatCd" value="<c:out value='${newProdDetailInfo.stdCatCd}'/>" /> 
						<input type="hidden" name="dispCatCd"  id="dispCatCd" value="<c:out value='${newProdDetailInfo.dispCatCd}'/>" />
						<!-- 24NBM_OSP_CAT OSP카테고리-->
						<input type="hidden" name="ospDispCatCd"  id="ospDispCatCd" value="" />


						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><spring:message
										code="msg.product.onOff.default.notice" /></li>
							</ul>


							<table class="bbs_grid3" cellpadding="0" cellspacing="0"
								border="0">
								<colgroup>
									<col width="135px">
									<col width="260px">
									<col width="135px">
									<col width="*">
								</colgroup>

								<tr>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.gubun" /></b></th>
									<td>
										<div>
											<spring:message
													code="msg.product.onOff.default.onOffDivnCode" />
										</div>
									</td>

									<th><b><span class="star">*</span> 수기등록 여부</b></th>
									<td>
										<div>
											<font color="blue" style="font-size: 11px">※안내사항: 수기등록
												이미지 상품 일 경우 Y 선택 필수</font><br> <input type="radio"
												class="required" name="mnlProdReg" id="mnlProdReg" value="0" />N
											<input type="radio" class="onOffField" name="mnlProdReg"
												id="mnlProdReg" value="1" />Y
										</div>
									</td>
								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.vendor" /></b></th>
									<td>
										<html:codeTag objId="entpCd" objName="entpCd" width="150px;" dataType="CP" comType="SELECT" formName="form" defName="선택"/>
									</td>
									<!-- 250327 마트 차세대 채널 추가 -->
									<th><b><span class="star">*</span> 채널</b></th>
									<td>
										<html:codeTag objId="chanCd" objName="chanCd" parentCode="PURDE"  comType="CHECKBOX" dataType="NTCPCD" orderSeqYn="Y" formName="form" childCode="1"  disabled="disabled"/>
										<!-- <input type="checkbox" id="chanCd_02" name="chanCd" value="KR02"/><label for="chanCd_02">마트</label>
										<input type="checkbox" id="chanCd_03" name="chanCd" value="KR03"/><label for="chanCd_03">MAXX</label>
										<input type="checkbox" id="chanCd_04" name="chanCd" value="KR04"/><label for="chanCd_04">롯데슈퍼</label>
										<input type="checkbox" id="chanCd_09" name="chanCd" value="KR09"/><label for="chanCd_09">CFC</label> -->
									</td>
									<!-- 신상품 구분 2016.03.01 추가 by song min kyo(2016.03.03 제거 an tae kyung) -->
									<%-- <th><span class="star">*</span><spring:message code="msg.product.zzNewProdFg"/></th>
									<td>
										<select name="zzNewProdFg"	id="zzNewProdFg"	 class="required" 	style="width:150px;">
											<option value="">선택</option>
											<option value="1">순수신상품</option>
											<option value="2">시즌상품</option>
											<option value="3">행사상품</option>
											<option value="4">라인확장</option>
										</select>
									</td>	 --%>
								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.itemProperty" /></b></th>
									<td><select name="prodDivnCd" id="prodDivnCd"
										class="required" style="width: 150px;">
											<option value="1"><spring:message
													code="msg.product.onOff.default.item" /></option>
											<option value="5"><spring:message
													code="msg.product.onOff.default.fashionItem" /></option>
											<option value="0"><spring:message
													code="msg.product.onOff.default.freshNonItem" /></option>
									</select></td>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.prodAttTypFg" /></b></th>
									<td>
										<div>
											<input type="radio" name="prodAttTypFg" id="prodAttTypFg"
												class="required" value="00">
											<spring:message code="msg.product.onOff.default.danItem" />
											<input type="radio" name="prodAttTypFg" id="prodAttTypFg"
												class="required" value="01">
											<spring:message code="msg.product.prodAttTypFg.txt2" />
										</div>
									</td>
								</tr>


								<tr>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.team" /></b></th>
									<td>
										<select id="teamCd" name="teamCd" class="required" style="width: 150px;">
											<option value="">
												<spring:message code="button.common.choice" /></option>
												<c:forEach items="${teamList}" var="teamList" varStatus="index">
													<option value="<c:out value='${teamList.teamCd}'/>"><c:out value="${teamList.teamNm}"/></option>
												</c:forEach>
										</select>
									</td>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.class" /></b></th>
									<td>
										<select id="l1Cd" name="l1Cd" class="required" style="width: 150px;">
											<option value="">
												<spring:message code="button.common.choice" /></option>
										</select>
									</td>
								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.l2Cd" /></b></th>
									<td>
										<select id="l2Cd" name="l2Cd" class="required">
											<option value=""><spring:message code="button.common.choice" /></option>
										</select>
									</td>

									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.detailClass" /></b></th>
									<td><select id="l3Cd" name="l3Cd" class="required">
											<option value=""><spring:message code="button.common.choice" /></option>
										</select>
									</td>
								</tr>
                                <!-- 24NBM_OSP_CAT S-->
                                <tr>
                                    <th><b><span class="star">*</span>ZETTA<br/>전시카테고리<br/>(롯데마트몰)</b></th>
                                    <td colspan="3">
                                        <p style="color:red;padding-top:10px;padding-left:5px;' ">
                                            '제타 전시 카테고리' 선택 시 주의 사항<br>
                                            &nbsp;- 상품과 일치(가장 유사) 전시 카테고리와 대표 카테고리를 선택 바랍니다.<br>
                                            &nbsp;- 대표 카테고리는 ZETTA 검색 정확도에 중요합니다.<br>
                                        </p>
                                        <div style="overflow:hidden;width: 640px;">
                                            <div>
                                                <div style="text-align: right;">
                                                    <a href="#" class="btn" onclick="javascript:addOspCategory();"><span>등록</span></a>
                                                    <a href="#" class="btn" onclick="javascript:delOspCategory();"><span>삭제</span></a>
                                                </div>
                                                <!-- grid List -->
												<div class="gridcontainer">
													<table id="tabList_zettaDispCtg">
														<tr>
															<td></td>
														</tr>
													</table>
												</div>
												<!-- ./grid List -->
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <!-- 24NBM_OSP_CAT E-->
								<!-- 180724 전시카테고리 START -->
								<tr>
									<th><b><span class="star">*</span>롯데ON<br/>전시카테고리</b></th>
									<td colspan="3">
										<div style="overflow:hidden;width: 640px;">
											<li style="padding: 5px 0 0 0; font-size: 11px;"><font color=blue>※ 최대 3개 카테고리 지정 가능하며, 추가는 전시카테고리에서 지정합니다.</font>
											</li>
												<div style="text-align: right;">
													<a href="#" class="btn" onclick="javascript:addCategory();"><span>등록</span></a>
													<a href="#" class="btn" onclick="javascript:delCategory();"><span>삭제</span></a>
												</div>
												<!-- grid List -->
												<div class="gridcontainer">
													<table id="tabList_dispCtg">
														<tr>
															<td></td>
														</tr>
													</table>
												</div>
												<!-- ./grid List -->
										</div>

										<!-- 200306 EC전용 카테고리 START -->
										<font color=blue>※ EC 표준 카테고리<br/></font>
										<input type="hidden" id="prev-ecStandardLargeCategory" name="prev-ecStandardLargeCategory" />
										<select id="ecStandardLargeCategory" onchange="this.className='required ' + this.options[this.selectedIndex].className" name="ecStandardLargeCategory" class="required default" style="width:120px;">
											<option value="" class="required default">대분류</option>
										</select>
										<input type="hidden" id="prev-ecStandardMediumCategory" name="prev-ecStandardMediumCategory" />
										<select id="ecStandardMediumCategory" onchange="this.className='required ' + this.options[this.selectedIndex].className" name="ecStandardMediumCategory" class="required default" style="width:120px;">
											<option value="" class="required default">중분류</option>
										</select>
										<input type="hidden" id="prev-ecStandardSmallCategory" name="prev-ecStandardSmallCategory" />
										<select id="ecStandardSmallCategory" onchange="this.className='required ' + this.options[this.selectedIndex].className" name="ecStandardSmallCategory"	class="required default" style="width:120px;">
											<option value="">소분류</option>
										</select>
										<input type="hidden" id="prev-ecStandardSubCategory" name="prev-ecStandardSubCategory" />
										<select id="ecStandardSubCategory" onchange="this.className='required ' + this.options[this.selectedIndex].className" name="ecStandardSubCategory"	class="required default" style="width:120px;">
											<option value="">세분류</option>
										</select>
										<br/><br/>

										<div style="overflow:hidden;width: 640px;">
											<div>
												<font color=blue style="font-size:11px;">※ EC 롯데ON 전시 카테고리</font>
												<a href="#" class="btn" style="float: right;" onclick="javascript:delEcDisplayCategory('');"><span>삭제</span></a>
												<a href="#" class="btn" style="float: right;" onclick="javascript:addEcStandardCategory('LTON', 'N');"><span>등록</span></a>
												<!-- grid List -->
												<div class="gridcontainer">
													<table id="tabList_ecOnDispCtg">
														<tr>
															<td></td>
														</tr>
													</table>
												</div>
												<!-- ./grid List -->
											</div>
										</div>

										<div style="overflow:hidden;width: 640px;">
											<div>
												<font color=blue style="font-size:11px;">※ EC 롯데마트몰 전시카테고리</font>
												<a href="#" class="btn" style="float:right;" onclick="javascript:delEcDisplayCategory('true');"><span>삭제</span></a>
												<a href="#" class="btn" style="float:right;" onclick="javascript:addEcStandardCategory('TOYS', 'Y');"><span>등록</span></a>
												<!-- grid List -->
												<div class="gridcontainer">
													<table id="tabList_ecMallDispCtg">
														<tr>
															<td></td>
														</tr>
													</table>
												</div>
												<!-- ./grid List -->
											</div>
										</div>
										<!-- 200306 EC전용 카테고리 END -->
									</td>
								</tr>
								<!-- 180724 전시카테고리 END -->

								<tr>
									<c:choose>
										<c:when
											test="${empty epcLoginVO.adminId || epcLoginVO.adminId eq 'online'}">
											<!-- 업체 및 online-->
											<!--필수옵션 -->
											<!-- <th><span class="star" >*</span><spring:message code="msg.product.onOff.default.sellCode"/></th> -->
											<!-- 미필수 없션 -->
											<th><span class="star" style="display: none">*</span> <spring:message
													code="msg.product.onOff.default.sellCode" /></th>
										</c:when>
										<c:otherwise>
											<!-- 관리자 및 online 예외-->
											<!-- 미필수옵션 -->
											<th><span class="star" style="display: none">*</span> <spring:message
													code="msg.product.onOff.default.sellCode" /></th>
										</c:otherwise>
									</c:choose>
									<td colspan="1"><input type="text" name="sellCd"
										id="sellCd" maxlength="13" style="width: 150px;"
										class="sell88Code" maxlength="13"
										value="<c:out value='${newProdDetailInfo.sellCd}'/>" /></td>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.grpClass" /></b></th>
									<td><select id="grpCd" name="grpCd" class="required">
											<option value=""><spring:message
													code="button.common.choice" /></option>
									</select></td>
								</tr>

								<tr>
									<th><spring:message
											code="msg.product.onOff.default.itemStandard" /></th>
									<td><input type="text" name="prodStandardNm"
										id="prodStandardNm" maxlength="15" style="width: 150px;"
										value="<c:out value='${newProdDetailInfo.prodStandardNm}'/>" />
									</td>

									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.dutyfree" /></b></th>
									<td><html:codeTag attr="class=\"required\""
											objId="taxatDivnCd" objName="taxatDivnCd" parentCode="PRD06"
											width="150px;" comType="SELECT" formName="form" defName="선택" />
									</td>
								</tr>


								<%--
								<!-- 낱개단위, 낱개수량 -->
								<tr>
									<th><spring:message code="msg.product.pieceUnit"/></th>
									<td>
										<html:codeTag   objId="pieceUnit" 	objName="pieceUnit"		parentCode="PRD17" 	width="150px;" 	comType="SELECT" 	formName="form" 	defName="선택"  childCode="1"	/>
									</td>
									<th><spring:message code="msg.product.pieceQty"/></th>
									<td>
										 <input type="text" name="pieceQty"		id="pieceQty"		maxlength="10" 		style="width:150px;" 	class="requiredIf number default0"		value="<c:out value='${newProdDetailInfo.pieceQty}'/>"/>
									</td>
								</tr>
								 --%>


								<tr>
									<th style="font-weight:bolder;"><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.itemSize" /></th>
									<td>
									<!-- <font color="blue" style="font-size: 11px">※폐기물유형: 상품 자체에 해당</font> -->
										<div name="productSize">
											<font color="blue" style="font-size: 11px">
											  1. 실 상품이 아닌 '패키지' 사이즈 입력 필수<br>
											  2. 매장진열되는 모습 기준 <br>&nbsp;가로, 세로(깊이), 높이(정면높이) 입력 필수<br>
											</font>
											<font style="font-size: 10px">
											<spring:message code="msg.product.onOff.default.width" />
											</font>
											<input type="text" name="prodHrznLeng" id="prodHrznLeng"
												style="width: 36px" class="required number default0 size"
												maxlength="4"
												value="<c:out value='${newProdDetailInfo.prodHrznLeng}'/>" />
											<font style="font-size: 10px">
											<spring:message code="msg.product.onOff.default.length" />
											</font>
											<input type="text" name="prodVtclLeng" id="prodVtclLeng"
												style="width: 36px" class="required number default0 size"
												maxlength="4"
												value="<c:out value='${newProdDetailInfo.prodVtclLeng}'/>" />
											<font style="font-size: 10px">
											<spring:message code="msg.product.onOff.default.height" />
											</font>
											<input type="text" name="prodHigt" id="prodHigt"
												style="width: 36px" class="required number default0 size"
												maxlength="4"
												value="<c:out value='${newProdDetailInfo.prodHigt}'/>" />
										</div>
									</td>

									<th><b><span class="star">*</span> <spring:message
											code="msg.product.sizeUnit" /></b></th>
									<td><html:codeTag objId="sizeUnit" objName="sizeUnit"
											parentCode="PRD42" comType="SELECT" formName="form"
											selectParam="MM" attr="class=\"required\"" /> <font
										color="red"><spring:message
												code="msg.product.itemSzUnit" /></font></td>
								</tr>


								<tr>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.totWeight" /></b></th>
									<td>
										<div name="productTotWeight">
											<font color="blue" style="font-size: 11px">※총중량: 패키지를
												포함한 상품의 전체 무게</font> <input type="text" name="totWeight"
												id="totWeight" class="required rate" style="width: 85px;"
												maxlength="5"
												value="<c:out value='${newProdDetailInfo.totWeight}'/>" />
											&nbsp;
											<html:codeTag objId="weightUnit" objName="weightUnit"
												parentCode="PRD41" comType="SELECT" formName="form"
												selectParam="KG" />
										</div>
									</td>

									<th><b><span class="star">*</span> 순중량</b></th>
									<td>
										<div name="productNetWeight">
											<font color="blue" style="font-size: 11px">※순중량: 패키지가
												포함되지 않은 순수 상품의 무게</font> <input type="text" name="netWeight"
												id="netWeight" class="required rate" style="width: 85px;"
												maxlength="5"
												value="<c:out value='${newProdDetailInfo.netWeight}'/>" />


											&nbsp; <font color="red" style="font-size: 11px">(총중량,
												순중량 단위 같음.)</font>
										</div>
									</td>

								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.mgrItemNm" /></b></th>
									<td><input type="text" name="prodNm" id="prodNm"
										class="required" style="width:95%;" maxlength="50"
										value="<c:out value='${newProdDetailInfo.prodNm}'/>" disabled/>
										<font color="blue">브랜드+단축상품명+규격 자동입력 (최대 50bytes)</font>	
									</td>
									<th><spring:message code="msg.product.onOff.default.brand" /></th>
									<td><input type="text" maxlength="30" class="requiredIf" data-cbChk="N"
										id="brandName" name="brandName" style="width: 150px;"
										value="<c:out value='${newProdDetailInfo.brandName}'/>"
										disabled="disabled" /> <a href="javascript:openBrandPopup();"
										class="btn"><span><spring:message
													code="button.common.choice" /></span></a>
									</td>
								</tr>

								<tr>
									<th><spring:message code="msg.product.itemNmEn" /></th>
									<td><input type="text" name="prodEnNm"
										id="prodEnNm" style="width: 95%; ime-mode: disabled;"
										maxlength="50"
										oninput="this.value=this.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '')"
										value="<c:out value='${newProdDetailInfo.prodEnNm}'/>" /> <font
										color="red"><spring:message
												code="msg.product.itemNmEn2" /></font></td>
									<!-- 250327 마트 차세대 prodShortNm (쇼카드상품명 → 단축상품명) 변경 -->
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.shortNm" /></b></th>
									<td><input type="text" name="prodShortNm"
										id="prodShortNm" style="width:95%" class="required" 
										maxlength="30"
										value="<c:out value='${newProdDetailInfo.prodShortNm}'/>" />
										<p style="display: inline;">현재 입력 Byte : <span id="prodShortNmByte" style="font-weight:bold;">0</span> Byte</p>	
									</td>
								</tr>
								<!-- 250327 마트 차세대 쇼카드상품명 수정불가 -->
								<tr>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.showCardNm" /></b></th>
									<td colspan='3'>
										<input type="text" id="showCardNm" name="showCardNm" class="required" value="" style="width:35%" disabled/>
										<font color="blue">브랜드+단축상품명 자동입력</font>
									</td>
								</tr>

								<tr>
									<th colspan='4'>
										<b>
											표시단위 , 표시기준수량, 표시총량중 하나의 항목이라도 입력이 되면 나머지 항목도 모두 입력이 되어야 합니다.
										<b>
									</th>

								</tr>

								<tr>
									<th><spring:message code="msg.product.onOff.default.displayUnit" /></th>
									<td><html:codeTag objId="dpUnitCd" objName="dpUnitCd" parentCode="PRD17" width="150px;" comType="SELECT" formName="form" defName="선택" /></td>
									<th><spring:message code="msg.product.onOff.default.displayStandardQuantity" /></th>
									<td><input type="text" name="dpBaseQty" id="dpBaseQty" maxlength="5" style="width: 150px;" class="requiredIf number default0" value="<c:out value='${newProdDetailInfo.dpBaseQty}'/>" /></td>
								</tr>

								<tr>
									<th><spring:message code="msg.product.onOff.default.dispTotQty" /></th>
									<td><input type="text" name="dpTotQty" id="dpTotQty" maxlength="5" style="width: 150px;" class="requiredIf number default0" value="<c:out value='${newProdDetailInfo.dpTotQty}'/>" /></td>

								</tr>

								<%-- 250403 마트 차세대 VIC여부 삭제 
								<tr>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.vicChk" /></b></th>
									<td><select name="matCd" id="matCd" class="required" style="width: 150px;"> <option value="">
										<spring:message code="button.common.choice" />
											<option value="1"><spring:message code="msg.product.onOff.default.exclusiveItem" />
											<option value="3"><spring:message code="msg.product.onOff.default.exclusiveItemVIC" />
											<option value="2"><spring:message code="msg.product.onOff.default.exclusiveVIC" />
									</select>
									<th><spring:message
											code="msg.product.onOff.default.vicOnlineCd" /></th>
									<td>
										<div class="matchVicOnlinoption" style="display: none">
											<input type="radio" class="onOffField" name='vicOnlineCd' id="vicOnlineCd" value="">
											<spring:message code="msg.product.onOff.default.notApply" />
											<input type="radio" class="onOffField" name='vicOnlineCd' id="vicOnlineCd" value="X">
											<spring:message code="msg.product.onOff.default.apply" />
										</div>
										<div class="nomatchVicOnlinoption">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr> --%>
								<%-- <tr>
									<th>
									<td></td>
									</th>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.vicNormalItemPrice" /></b></th>
									<td>
										<div>
											<input type="text" name="wnorProdSalePrc" id="wnorProdSalePrc" value="<c:out value='${newProdDetailInfo.wnorProdSalePrc}'/>" class="required number vicrange vicprice" maxlength="8" style="width: 150px;" />
											<html:codeTag objId="wnorProdSaleCurr" objName="wnorProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
											<!--
											<select name="wnorProdSaleCurr"		id="wnorProdSaleCurr"	class="required"	style="width:50px;"> <option value="">선택</option> <option value="KRW">KRW</option>
											</select>
											-->
										</div>
									</td>
								</tr>

								<tr>
									<th><span class="star" style="display: none"> *</span> <spring:message code="msg.product.onOff.default.wprftRate" /></th>
									<td><input type="text" name="wprftRate" id="wprftRate" value="<c:out value='${newProdDetailInfo.wprftRate}'/>" class="requiredIf vicrate" maxlength="5" style="width: 150px;" readonly /></td>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.vicNormalCost" /></b></th>
									<td>
										<div>
											<input type="text" name="wnorProdPcost" id="wnorProdPcost" class="required number vicrange viccost" maxlength="8" style="width: 150px;" value="<c:out value='${newProdDetailInfo.wnorProdPcost}'/>" />
											<html:codeTag objId="wnorProdCurr" objName="wnorProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
											<!--
											<select name="wnorProdCurr"		id="wnorProdCurr"	class="required"	style="width:50px;">
												<option value="">선택</option>
												<option value="KRW">KRW</option>
											</select>
											-->
										</div>
									</td>
								</tr> --%>

								<tr>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.purInrcntQty" /></b></th>
									<td>
										<input type="text" maxlength="5" name="purInrcntQty" id="purInrcntQty" style="width: 150px;" value="<c:out value='${newProdDetailInfo.purInrcntQty}'/>" class="onOffField required number" />
									</td>
									<th>
										<b><span class="star">*</span> <spring:message code="msg.product.onOff.default.purUnitCd" /></b>
									</th>
									<td>
										<html:codeTag attr="class=\"onOffField required\"" objId="purUnitCd" objName="purUnitCd" parentCode="CSA01" width="150px;" comType="SELECT" formName="form" defName="선택" />
									</td>
								</tr>
							</table>
						</div>
						<!-- 상품정보 입력란 기본정보 끝 --------------------------------------------------------------->

						<table cellspacing=0 cellpadding="0" border="0">
							<tr>
								<td height=5></td>
							</tr>
						</table>

						<!-- 채널별 원매가정보 시작 ------------------------------------------------------------------->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><b><span class="star">*</span> 채널별 원·매가 정보</b></li>
							</ul>
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0" id="tblNewProdPrc">
								<colgroup>
									<col width="60px">
									<col width="75px">
									<col width="260px">
									<col width="135px">
									<col width="*">
								</colgroup>
								<tr>
									<th colspan='5'>
										<b>※ 정상매가 > 정상원가여야 합니다.</b><br/>
										<b><spring:message code="msg.product.onOff.default.normalItemTxt" /></b> <br/>
										<b><spring:message code="msg.product.onOff.default.vicNormalItemTxt" /></b>
									</th>
								</tr>
								<tr>
									<th colspan="2">온라인운영 여부</th>
									<td colspan="3">
										<div class="matchVicOnlinoption" style="display:none;">
											<input type="radio" class="onOffField" name='vicOnlineCd' id="vicOnlineCd_0" value="" checked/>
											<label for="vicOnlineCd_0">미적용</label>
											<input type="radio" class="onOffField" name='vicOnlineCd' id="vicOnlineCd_1" value="X"/>
											<label for="vicOnlineCd_1">적용</label>
										</div>
										<div class="nomatchVicOnlinoption">미사용</div>
									</td>
								</tr>
								<tr class="mart_div" data-chan="KR02" style="display:none">
									<th rowspan="2"><b>마트</b></th>
									<th></th><td></td>
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.normalPrice" /></b></th>
									<td> 
										<div>
											<input type="text" name="norProdSalePrc" id="norProdSalePrc" value="<c:out value='${newProdDetailInfo.norProdSalePrc}'/>" class="required number range price" maxlength="8" style="width: 150px;" />
											<html:codeTag objId="norProdSaleCurr" objName="norProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="mart_div" data-chan="KR02" style="display:none">
									<th><span class="star" style="display: none"> *</span> <spring:message code="msg.product.onOff.default.prftRate" /></th>
									<td><input type="text" name="prftRate" id="prftRate" value="<c:out value='${newProdDetailInfo.prftRate}'/>" class="requiredIf rate" maxlength="5" style="width: 150px;" readonly /></td>

									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.norProdPcost" /></b></th>
									<td>
										<div>
											<input type="text" name="norProdPcost" id="norProdPcost" value="<c:out value='${newProdDetailInfo.norProdPcost}'/>" class="required number range cost" maxlength="8" style="width: 150px;" />
											<html:codeTag objId="norProdCurr" objName="norProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="maxx_div" data-chan="KR03" style="display:none">
									<th rowspan="2"><b>MAXX</b></th>
									<th></th><td></td>
									<th><b><span class="star">*</span> 정상매가</b></th>
									<td>
										<div>
											<input type="text" name="wnorProdSalePrc" id="wnorProdSalePrc" value="<c:out value='${newProdDetailInfo.wnorProdSalePrc}'/>" class="required number vicrange vicprice" maxlength="8" style="width: 150px;" />
											<html:codeTag objId="wnorProdSaleCurr" objName="wnorProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="maxx_div" data-chan="KR03" style="display:none">
									<th><span class="star" style="display: none"> *</span> 이익률(%)</th>
									<td><input type="text" name="wprftRate" id="wprftRate" value="<c:out value='${newProdDetailInfo.wprftRate}'/>" class="requiredIf vicrate" maxlength="5" style="width: 150px;" readonly /></td>
									<th><b><span class="star">*</span> 정상원가(vat제외)</b></th>
									<td>
										<div>
											<input type="text" name="wnorProdPcost" id="wnorProdPcost" class="required number vicrange viccost" maxlength="8" style="width: 150px;" value="<c:out value='${newProdDetailInfo.wnorProdPcost}'/>" />
											<html:codeTag objId="wnorProdCurr" objName="wnorProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								
								<tr class="super_div" data-chan="KR04" style="display:none">
									<th rowspan="2"><b>슈퍼</b></th>
									<th></th><td></td>
									<th><b><span class="star">*</span> 정상매가</b></th>
									<td>
										<div>
											<input type="text" name="snorProdSalePrc" id="snorProdSalePrc" value="<c:out value='${newProdDetailInfo.snorProdSalePrc}'/>" class="required number superrange superprice" maxlength="8" style="width: 150px;" />
											<html:codeTag objId="snorProdSaleCurr" objName="snorProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="super_div" data-chan="KR04" style="display:none">
									<th><span class="star" style="display: none"> *</span> 이익률(%)</th>
									<td><input type="text" name="sprftRate" id="sprftRate" value="<c:out value='${newProdDetailInfo.sprftRate}'/>" class="requiredIf superrate" maxlength="5" style="width: 150px;" readonly /></td>
									<th><b><span class="star">*</span> 정상원가(vat 제외)</b></th>
									<td>
										<div>
											<input type="text" name="snorProdPcost" id="snorProdPcost" class="required number superrange supercost" maxlength="8" style="width: 150px;" value="<c:out value='${newProdDetailInfo.snorProdPcost}'/>" />
											<html:codeTag objId="snorProdCurr" objName="snorProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								
								<tr class="oca_div" data-chan="KR09" style="display:none">
									<th rowspan="2"><b>CFC</b></th>
									<th>가격 COPY</th>
									<td>
										<input type="checkbox" id="copyPrc" title="동일 가격으로 Copy"/>
										<br/>
										<font color="blue" style="font-size: 11px;">※다 채널 선택 시, 마트&gt;MAXX&gt;슈퍼 순으로 적용 </font>
									</td>
									<th><b><span class="star">*</span> 정상매가</b></th>
									<td>
										<div>
											<input type="text" name="onorProdSalePrc" id="onorProdSalePrc" value="<c:out value='${newProdDetailInfo.onorProdSalePrc}'/>" class="required number ocarange ocaprice" maxlength="8" style="width: 150px;" />
											<html:codeTag objId="onorProdSaleCurr" objName="onorProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								<tr class="oca_div" data-chan="KR09" style="display:none">
									<th><span class="star" style="display: none"> *</span> 이익률(%)</th>
									<td><input type="text" name="oprftRate" id="oprftRate" value="<c:out value='${newProdDetailInfo.oprftRate}'/>" class="requiredIf ocarate" maxlength="5" style="width: 150px;" readonly /></td>
									<th><b><span class="star">*</span> 정상원가(vat 제외)</b></th>
									<td>
										<div>
											<input type="text" name="onorProdPcost" id="onorProdPcost" class="required number ocarange ocacost" maxlength="8" style="width: 150px;" value="<c:out value='${newProdDetailInfo.onorProdPcost}'/>" />
											<html:codeTag objId="onorProdCurr" objName="onorProdCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\"" />
										</div>
									</td>
								</tr>
								
							</table>
						</div>
						<!-- 채널별 원매가정보 끝 -------------------------------------------------------------------->
						
						
						<table cellspacing=0 cellpadding="0" border="0">
							<tr>
								<td height=5></td>
							</tr>
						</table>

						<!-- 추가정보 입력란 시작 -------------------------------------------------------------------->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><spring:message
										code="msg.product.onOff.default.AdditionalInfo" />
										&nbsp&nbsp&nbsp
										<font color="blue" style="font-size: 11px"><b>폐기물/재활용유형 선택 Guide:"partner.lottemart.com" 공지사항內"폐기물" 검색하여 참고하시기 바랍니다.</b></font>
								</li>
							</ul>

							<table class="bbs_grid3" cellpadding="0" cellspacing="0"
								border="0" id="tblNewProdAdd">
								<colgroup>
									<col width="135px">
									<col width="260px">
									<col width="135px">
									<col width="*">
								</colgroup>

								<tr>
									<th>
										<b><span class="star">*</span>
										<spring:message code="msg.product.onOff.default.itemType" /></b>	<!-- 상품유형 -->
									</th>
									<td>
										<html:codeTag attr="class=\"onOffField required\"" objId="prodTypeDivnCd" objName="prodTypeDivnCd" parentCode="PRD09" width="150px;" comType="SELECT" formName="form" defName="선택" />
									</td>

									<!-- 20190627 전산정보팀 이상구 추가(소싱유형) -->
									<th><b><span class="star">*</span> <spring:message code="msg.product.onOff.default.sType" /></b></th>	<!-- 소싱유형 -->
									<td>
										<html:codeTag attr="class=\"onOffField required\"" objId="sType" objName="sType" parentCode="PRD24" width="150px;" comType="SELECT" formName="form" defName="선택" />
										<a class="btn" href="#" style="" onclick="helpWin(true);"><span>소싱유형 설명</span></a>
									</td>
								</tr>

								<tr>
									<th>
										<b><spring:message code="msg.product.wasteFg" /></b>	<!-- 개별계획구분 -->

									</th>
									<td>
										<font color="blue" style="font-size: 11px">※폐기물유형: 상품 자체에 해당</font><br/>
										<html:codeTag objId="wasteFg" objName="wasteFg" parentCode="PRD43" comType="SELECT" formName="form"  defName="선택"/>
									</td>
									<th>
										<!--<spring:message code="msg.product.plasticWeight" />	--><!--  -->
										플라스틱무게 (g)
									</th>
									<td>
										<input type="text" name="plasticWeight" id="plasticWeight" style="width: 80px;" class="requiredIf number default0" maxlength="5" />
									</td>
								</tr>

								<tr>
									<th>
										<spring:message code="msg.product.recycleFg1" />	<!--  -->
									</th>
									<td>
										<font color="blue" style="font-size: 11px">※재활용유형: 상품 外 포장재(패키지)에 해당 <br/></font>
										<html:codeTag objId="recycleFg1" objName="recycleFg1" parentCode="PRD44" width="200px" comType="SELECT" formName="form" defName="선택" />
									</td>
									<th>
									<!-- 	<spring:message code="msg.product.recycleWeight1" />	--><!--  -->
										재활용무게1 (g)
									</th>
									<td>
										<input type="text" name="recycleWeight1" id="recycleWeight1" style="width: 80px;" maxlength="10" value="<c:out value='${newProdDetailInfo.recycleWeight1}'/>" />
									</td>
								</tr>

								<tr>
									<th>
										<spring:message code="msg.product.recycleFg2" />	<!--  -->
									</th>
									<td>

										<html:codeTag objId="recycleFg2" objName="recycleFg2" parentCode="PRD44" width="200px" comType="SELECT" formName="form" defName="선택" />
									</td>
									<th>재활용무게2 (g)</th>
									<td>
										<input type="text" name="recycleWeight2" id="recycleWeight2" style="width: 80px;" maxlength="10" value="<c:out value='${newProdDetailInfo.recycleWeight2}'/>" />
									</td>
								</tr>

								<tr>
									<th>
										<spring:message code="msg.product.recycleFg3" />
									</th>
									<td>
										<html:codeTag objId="recycleFg3" objName="recycleFg3" parentCode="PRD44" width="200px" comType="SELECT" formName="form" defName="선택" />
									</td>
									<th>
										재활용무게3 (g)
									</th>
									<td>
										<input type="text" name="recycleWeight3" id="recycleWeight3" style="width: 80px;" maxlength="10" value="<c:out value='${newProdDetailInfo.recycleWeight3}'/>" />
									</td>
								</tr>

								<tr>
									<th><b>* 계별계획구분</b></th>
									<td>
										<font color="blue" style="font-size: 11px">※ 개별계획 대상 :PB하도급/도급계약서 작성 시 대상 </font>
										<br/>
										<input type="radio" class="onOffField" name="ownStokFg" id="ownStokFg" value="" checked /> 비대상
										<input type="radio" class="onOffField" name="ownStokFg" id="ownStokFg" value="X" /> 대상</td>
									<th>개별계획량</th>
									<td>
										<font color="blue" style="font-size: 11px">개별계획량 : 계약량(최저납품량)</font>
										<br/>
										<input tupe="text" class="onOffField requiredIf number" name="planRecvQty" id="planRecvQty" value="<c:out value='${newProdDetailInfo.planRecvQty}'/>" />
									</td>
								</tr>
								<tr>
									<th>계획종료일

									</th>
									<td>
										<font color="blue" style="font-size: 11px">계획종료일: 계약종료일</font><br/>
										<input type="text" maxlength="8" class="requiredIf" name="ordDeadline" id="ordDeadline" style="width: 80px;" value="<c:out value='${newProdDetailInfo.ordDeadline}'/>" readonly />
										<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.ordDeadline');" style="cursor: hand;" id="ordDeadlineImg" />
									</td>
								</tr>


								<tr>
									<th>
										<spring:message code="msg.product.onOff.default.newItemincentiveApply" />	<!-- 신상품입점장려금적용 -->
									</th>
									<td>
										<div class="jangoption">
											<input type="radio" class="onOffField" name='newProdPromoFg' id="newProdPromoFg" value="">
											<spring:message code="msg.product.onOff.default.notApply" />
											<input type="radio" class="onOffField" name='newProdPromoFg' id="newProdPromoFg" value="X">
											<spring:message code="msg.product.onOff.default.apply" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>

									<th>
										<spring:message code="msg.product.onOff.default.releaseDt" />	<!--  신상품 출시일자 -->
									</th>
									<td>
										<div class="jangoption">
											<input type="text" maxlength="8" class="requiredIf" name="newProdStDy" id="newProdStDy" style="width: 80px;" readonly />
											<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.newProdStDy');" style="cursor: hand;" />
											<br>
											<spring:message code="msg.product.onOff.default.whenIncentive" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr>
								<tr>
									<th><spring:message
											code="msg.product.onOff.default.exceIncentive" /></th>
									<td>
										<div class="jangoption">
											<input type="radio" name='overPromoFg' id="overPromoFg"
												class="onOffField" value="">
											<spring:message code="msg.product.onOff.default.notApply" />
											<input type="radio" name='overPromoFg' id="overPromoFg"
												class="onOffField" value="X">
											<spring:message code="msg.product.onOff.default.apply" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.expireDt" /></b></th>
									<td><html:codeTag attr="class=\"required\""
											objId="flowDdMgrCd" objName="flowDdMgrCd" parentCode="PRD29"
											width="150px;" comType="SELECT" formName="form" defName="선택" />
									</td>
								</tr>
								
								<tr data-chan="KR04" style="display:none;">
									<th>(슈퍼) <spring:message code="msg.product.onOff.default.newItemincentiveApply" /></th>	<!--(슈퍼) 신상품입점장려금적용 -->
									<td>
										<div class="jangoption">
											<input type="radio" class="onOffField" name="sNewProdPromoFg" id="sNewProdPromoFg_0" value="">
											<spring:message code="msg.product.onOff.default.notApply" />
											<input type="radio" class="onOffField" name="sNewProdPromoFg" id="sNewProdPromoFg_1" value="X">
											<spring:message code="msg.product.onOff.default.apply" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>

									<th>(슈퍼) <spring:message code="msg.product.onOff.default.releaseDt" /></th>	<!-- (슈퍼) 신상품 출시일자 -->
									<td>
										<div class="jangoption">
											<input type="text" maxlength="8" class="requiredIf" name="sNewProdStDy" id="sNewProdStDy" style="width: 80px;" readonly />
											<%-- <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.sNewProdStDy');" style="cursor: hand;" /> --%>
											<br>
											<spring:message code="msg.product.onOff.default.whenIncentive" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr>
								<tr data-chan="KR04" style="display:none;">
									<th>(슈퍼) <spring:message code="msg.product.onOff.default.exceIncentive" /></th> <!-- (슈퍼) 성과초과장려금 적용구분 -->
									<td>
										<div class="jangoption">
											<input type="radio" name="sOverPromoFg" id="sOverPromoFg" class="onOffField" value="">
											<spring:message code="msg.product.onOff.default.notApply" />
											<input type="radio" name="sOverPromoFg" id="sOverPromoFg" class="onOffField" value="X">
											<spring:message code="msg.product.onOff.default.apply" />
										</div>
										<div class="nojangoption" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
									<th></th>
									<td></td>
								</tr>
								
								<tr>
									<th><span class="star">*</span> <spring:message
											code="msg.product.flowDD" /></th>
									<td>
										<div class="flowDate">
											<input type="text" class="required number default0"
												maxlength="4" name='flowDd' id="flowDd"
												value="<c:out value='${newProdDetailInfo.flowDd}'/>" />일
										</div>
										<div class="noFlowDate" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
									<th><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.possibleDt" /></th>
									<td>
										<div class="flowDate">
											<input type="text" class="required number flowDate"
												maxlength="3" name='stgePsbtDd' id="stgePsbtDd"
												value="<c:out value='${newProdDetailInfo.stgePsbtDd}'/>" />일
										</div>
										<div class="noFlowDate" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div>
									</td>
								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.mubaljoo" /></b></th>
									<td><div id="divNpurBuyPsbtDivnCd">
											<input type="radio" name='npurBuyPsbtDivnCd'
												id="npurBuyPsbtDivnCd" class="onOffField required" value="">
											<spring:message code="msg.product.onOff.default.notApply" />
											<input type="radio" name='npurBuyPsbtDivnCd'
												id="npurBuyPsbtDivnCd" class="onOffField required" value="X">
											<spring:message code="msg.product.onOff.default.apply" />
										</div></td>

									<th><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.releasePossibleDt" /></th>
									<td><div class="flowDate">
											<input type="text" name="pickPsbtDd" id="pickPsbtDd"
												maxlength="3" class="required number flowDate"
												value="<c:out value='${newProdDetailInfo.pickPsbtDd}'/>" />
											<spring:message code="msg.product.date" />
										</div>
										<div class="noFlowDate" style="display: none">
											<spring:message code="msg.product.onOff.default.notUse" />
										</div></td>
								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.newItemGbn" /></b></th>
									<td>
										<div>
											<input type="radio" name="newProdFirstPurDivnCd"
												id="newProdFirstPurDivnCd" class="onOffField required"
												value="0" />
											<spring:message code="msg.product.onOff.default.notApply" />
										</div>
									</td>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.honjaeGbn" /></b></th>
									<td><div>
											<input type="radio" name="mixYn" id="mixYn"
												class="onOffField required" value="0" />
											<spring:message code="msg.product.onOff.default.danItem" />
											<input type="radio" name="mixYn" id="mixYn"
												class="onOffField required" value="1" />
											<spring:message code="msg.product.onOff.default.honJaeItem" />
										</div></td>
								</tr>

								<tr>
									<th><spring:message
											code="msg.product.onOff.default.confirmGbn" /></th>
									<td><input type="radio" class="onOffField"
										name="totInspYn" id="totInspYn" value="" /> <spring:message
											code="msg.product.onOff.default.notTarget" /> <input
										type="radio" class="onOffField" name="totInspYn"
										id="totInspYn" value="X" /> <spring:message
											code="msg.product.onOff.default.target" /></td>
									<th><spring:message
											code="msg.product.onOff.default.balhangDt" /></th>
									<td><input type="text" maxlength="8" class="requiredIf"
										name="productDy" id="productDy" style="width: 80px;"
										value="<c:out value='${newProdDetailInfo.productDy}'/>"
										readonly /> <img src="/images/epc/layout/btn_cal.gif"
										class="middle" onClick="openCal('newProduct.productDy');"
										style="cursor: hand;" /></td>
								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.safeTagUseGbn" /></b></th>
									<td><html:codeTag attr="class=\"onOffField required\""
											objId="protectTagDivnCd" objName="protectTagDivnCd"
											parentCode="ALL14" width="150px;" comType="SELECT"
											formName="form" defName="선택" /></td>
									<th><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.safeTagTypeGbn" /></th>
									<td><html:codeTag attr="class=\"onOffField required\""
											objId="protectTagTypeCd" objName="protectTagTypeCd"
											parentCode="PRD01" width="150px;" comType="SELECT"
											formName="form" defName="선택" /></td>
								</tr>

								<tr>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.centerType" /></b></th>
									<td><html:codeTag attr="class=\"onOffField required\""
											objId="ctrTypeDivnCd" objName="ctrTypeDivnCd"
											parentCode="PRD12" width="150px;" comType="SELECT"
											formName="form" defName="선택" /></td>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.temperature" /></b></th>
									<td><html:codeTag attr="class=\"required\""
											objId="tmprtDivnCd" objName="tmprtDivnCd" parentCode="PRD30"
											width="150px;" comType="SELECT" formName="form" defName="선택" />
									</td>
								</tr>
								<tr>
									<th><spring:message
											code="msg.product.onOff.default.modelNm" /></th>
									<td><input type="text" maxlength="30" class="requiredIf"
										id="modelNm" name="modelNm" style="width: 150px;"
										value="<c:out value='${newProdDetailInfo.modelNm}'/>" /></td>
									<th><b><span class="star">*</span> <spring:message
											code="msg.product.onOff.default.country" /></b></th>
									<td><html:codeTag attr="class=\"required\"" objId="homeCd"
											objName="homeCd" parentCode="PRD16" width="150px;"
											comType="SELECT" formName="form" defName="선택" /></td>
								</tr>

								<%--
								<tr>
									<th><spring:message code="msg.product.onOff.seasonGbn" /></th>
									<td>
										<div>
											<select name="sesnYearCd" id="sesnYearCd" class="requiredIf">
												<option value=""><spring:message
														code="button.common.choice" /></option>
												<c:forEach items="${seasonYearList}" var="list"
													varStatus="index">
													<option value="${list.yearCd}">${list.yearNm}</option>
												</c:forEach>
											</select> <select id="sesnDivnCd" name="sesnDivnCd" class="requiredIf">
												<option value="">선택</option>
											</select>
										</div>
									</td>
								</tr>
								 --%>

								<!--  수입신고번호 start -->
								<tr>
									<th><spring:message code="msg.product.onOff.default.decno" /></th>
									<td><input type="text" maxlength="14"
										class="requiredIf decno" id="decno" name="decno"
										style="width: 150px;"
										value="<c:out value='${newProdDetailInfo.decno}'/>" /></td>
									<th><spring:message
											code="msg.product.onOff.default.tarrate" /></th>
									<td><input type="text" maxlength="30"
										class="requiredIf rate" id="tarrate" name="tarrate"
										style="width: 150px;"
										value="<c:out value='${newProdDetailInfo.tarrate}'/>" /></td>
								</tr>
								<tr>
									<th><spring:message code="msg.product.onOff.default.hscd" /></th>
									<td><input type="text" maxlength="10"
										class="requiredIf hscd" id="hscd" name="hscd"
										style="width: 150px;"
										value="<c:out value='${newProdDetailInfo.hscd}'/>" /></td>
								</tr>
								<!--  수입신고번호 end -->
								<%-- 250327 마트 차세대 - 메이커 삭제
								<tr>
									<th><spring:message code="msg.product.onOff.default.maker" /></th>
									<td><input type="text" maxlength="30" class="requiredIf"
										id="makerName" name="makerName" style="width: 150px;"
										value="<c:out value='${newProdDetailInfo.makerName}'/>"
										disabled="disabled" /> <a href="javascript:openMakerPopup();"
										class="btn"><span><spring:message
													code="button.common.choice" /></span></a></td>
								</tr>
								 --%>

								<tr>
									<th><spring:message
											code="msg.product.onOff.default.environment" /></th>
									<td><input type="radio" class="onOffField" name="ecoYn"
										id="ecoYn" value="0" />N <input type="radio"
										class="onOffField" name="ecoYn" id="ecoYn" value="1" />Y</td>
									<th><spring:message code="msg.product.onOff.default.ecoNm" /></th>
									<td><html:codeTag objId="ecoNm" objName="ecoNm"
											parentCode="PRD08" width="150px;" comType="SELECT"
											formName="form" defName="선택" /></td>
								</tr>

								<tr>
									<th><spring:message code="msg.product.onOff.default.dlvGa" /></th>
									<td><input type="radio" class="onOffField" name="dlvGa"
										id="dlvGa" value="0" />N <input type="radio"
										class="onOffField" name="dlvGa" id="dlvGa" value="1" />Y</td>
									<th><spring:message code="msg.product.onOff.default.insCo" /></th>
									<td><input type="radio" class="onOffField" name="insCo"
										id="insCo" value="0" />N <input type="radio"
										class="onOffField" name="insCo" id="insCo" value="1" />Y</td>
								</tr>

								<tr>
									<th><spring:message code="msg.product.onOff.default.dlvDt" /></th>
									<td colspan="3"><input type="text" id="dlvDt" name="dlvDt"
										style="width: 90%;" maxlength="50"
										value="<c:out value='${newProdDetailInfo.dlvDt}'/>" /></td>
								</tr>

								<tr>
									<th>상품설명</th>
									<td colspan="3"><input type="text" id="ispDtlDesc"
										name="ispDtlDesc" style="width: 90%;" maxlength="200"
										value="<c:out value='${newProdDetailInfo.ispDtlDesc}'/>" /></td>
								</tr>

								<!--  20180514 전산정보팀 이상구 체험형 상품여부 추가. -->
								<tr>
									<th>체험형 상품 여부</th>
									<td colspan="3">
										<div style="display: inline; padding-right: 80px;">
											<input type="radio" class="onOffField" name="exprProdYn"
												id="exprProdYn" value="0" checked />N <input type="radio"
												class="onOffField" name="exprProdYn" id="exprProdYn"
												value="1" />Y
										</div> 판매자 추천평 <input type="text" id="sellerRecomm"
										name="sellerRecomm" style="width: 465px;" maxlength="200"
										disabled />
										<div style="text-align: right; padding-right: 20px;">
											<p>
												<span id="recBytes">0</span>/100bytes
											</p>
										</div>
									</td>
								</tr>

								<tr>
									<th>상품키워드<a href="javascript:fnKeywordAddNew('new');"
										id="addKeyword" class="btn"><span>추가</span></a></th>
									<td id="keywordTd" colspan="3"><font color=blue>
									※ 저장 전 신규(추가) 상품키워드의 순번은 ◈ 로 표시됨<br>
									※ 1행당 검색어는 39바이트(한글기준 13글자)를 초과할 수 없습니다.<br>
									※ ,(커머) ;(세미콜론) |(버티컬 바) 와 같은 특수문자는 사용하실 수 없으며 1행당 검색어 1개를 입력해 주세요.<br>
							 	    ※ 띄어쓰기, 영문 대문자는 사용하실 수 없습니다.	<br>
											</font>
											<c:if test="${empty tpcPrdKeywordList}">
											<table id='keywordSubTable' style='width: 100%'>
												<tr>
													<th style='text-align: center; width: 30px'>순번</th>
													<th style='text-align: center; width: 200px'>검색어</th>
													<th style='text-align: center; width: 50px'></th>
												</tr>
												<tr>
													<td style='text-align: center'>◈<input type='hidden'
														name='seq' id='seq1' value='NEW' /></td>
													<td><input type='text'
														style='text-align: left; width: 98%;' name='searchKywrd'
														id='searchKywrd1' value='' maxlength="39"
														onblur="limitPushString(1);" /></td>
													<td></td>
												</tr>
											</table>
										</c:if> <c:if test="${not empty tpcPrdKeywordList}">
											<table id='keywordSubTable' style='width: 700px'>
												<tr>
													<th style='text-align: center; width: 50px'>순번</th>
													<th style='text-align: center; width: 200px'>검색어</th>
													<th style='text-align: center; width: 50px'></th>
												</tr>
												<c:forEach var="keywordList" items="${tpcPrdKeywordList}"
													varStatus="index">
													<tr id="kwRow<c:out value='${index.count}'/>">
														<td style='text-align: center'><c:out value='${keywordList.num}'/><input
															type='hidden' name='seq' id='seq<c:out value="${index.count}"/>'
															value='<c:out value="${keywordList.seq}"/>' />
														</td>
														<td><input type='text'
															style='text-align: left; width: 98%;' name='searchKywrd'
															id='searchKywrd<c:out value="${index.count}"/>'
															value='<c:out value="${keywordList.searchKywrd}"/>' maxlength="39"  
															onblur="limitPushString(<c:out value='${index.count}'/>);" /></td>  

														<c:choose>
															<c:when test="${index.count eq 1}">
																<td></td>
															</c:when>
															<c:otherwise>
																<td><a    
																	href='javascript:fnKeywordDelete("<c:out value='${index.count}'/>", "<c:out value='${keywordList.seq}'/>")'
																	id='deleteNewKeyword<c:out value='${index.count}'/>' class='btn'><span>삭제</span></a>
																</td>
															</c:otherwise>
														</c:choose>

													</tr>
												</c:forEach>
											</table>
										</c:if></td>
								</tr>

							</table>
						</div>
					</form>
					<!-- 전자상거래 시작 --------------------------------------------------------------------->
					<div>
						<ul name="productAddTemplateTitle" id="productAddTemplateTitle"
							class="tit mt10" style="display: none">
							<div class="bbs_list">
								<ul class="tit">
									<li class="tit"><spring:message
											code="msg.product.onOff.default.eletDeal" /></li>
									<li class="tit" id="productAddSelectBox"
										name="productAddSelectBox" style="display: none"><html:codeTag
											objId="productAddSelectTitle" objName="productAddSelectTitle"
											width="150px;" comType="SELECT" formName="form"
											defName="<spring:message code='button.common.choice'/>" /></li>

									<a href='/html/Ecom_Manual_v1_0.ppt' class="btn" id="excel"><span><spring:message
												code="msg.product.onOff.default.eletDealManual" /></span></a>
								</ul>
							</div>
						</ul>
					</div>

					<div name="productAddTemplateData" class="bbs_search"
						style="display: none">
						<table name="data_List" class="bbs_search" cellpadding="0"
							cellspacing="0" border="0">
							<colgroup>
								<col style="width: 35px;" />
								<col style="width: 65px;" />
							</colgroup>
						</table>
					</div>
					<!-- 전자상거래 끝 ------------------------------------------------------------------->

					<!-- KC 인증마크 시작 --------------------------------------------------------------------->
					<div>
						<ul name="productCertTemplateTitle" id="productCertTemplateTitle"
							class="tit mt10" style="display: none">
							<div class="bbs_list">
								<ul class="tit">
									<li class="tit"><spring:message
											code="msg.product.onOff.default.KCmark" /></li>
									<li class="tit" id="productCertSelectBox"
										name="productCertSelectBox" style="display: none"><html:codeTag
											objId="productCertSelectTitle"
											objName="productCertSelectTitle" width="150px;"
											comType="SELECT" formName="form" defName="선택" /></li>
									<li class="tit" id="productCertDtlSelectBox"
										style="display: none"><select
										id="productCertSelectDtlTitle"
										name="productCertSelectDtlTitle" style="display: none">
											<option value="">선택</option>
									</select></li>
								</ul>
							</div>
						</ul>
					</div>

					<div name="productCertTemplateData" class="bbs_search"
						style="display: none">
						<table name="cert_List" class="bbs_search" cellpadding="0"
							cellspacing="0" border="0">
							<colgroup>
								<col style="width: 35px;" />
								<col style="width: 65px;" />
							</colgroup>
						</table>
					</div>
					<!-- KC 인증마크 종료 --------------------------------------------------------------------->

					<table cellspacing=0 cellpadding="0" border="0">
						<tr>
							<td height=5></td>
						</tr>
					</table>

					<!-- editor 시작 ----------------------------------------------------------------------->
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">*<spring:message code="msg.product.descrTxt" />
								: <font color="white"><spring:message
										code="msg.product.descrTxt2" /></font></li>
							<%-- <li class="btn">
										<a href="javascript:doImageSplitterView();" class="btn" ><span><spring:message code="msg.product.imgAdd"/></span></a>
									</li> --%>
						</ul>
						<table class="bbs_grid3" cellpadding="0" cellspacing="0"
							border="0">
							<tr>
								<td>
									<!-- <textarea id="pe_agt" name="pe_agt" alt="initText" title="initText" Style="width:730px;height:450px;font-size:10pt"><p>Welcome to <span style="font-weight:bold;">CrossEditor 3.0</span> sample page</p></textarea> -->
									<input type="hidden" name="viewProdDesc" id="viewProdDesc"
									value="<c:out value='${newProdDetailInfo.prodDesc}'/>">
									<script type="text/javascript" language="javascript">
											var CrossEditor = new NamoSE('pe_agt');

											CrossEditor.params.Width 		= "100%";
											CrossEditor.params.UserLang 	= "auto";
											CrossEditor.params.FullScreen 	= false;
											CrossEditor.params.SetFocus 	= false; // 에디터 포커스 해제
											CrossEditor.params.ImageSavePath	= "productdetail";
											CrossEditor.params.AccessibilityOption = 1;
											CrossEditor.params.Template = [
											    {
											        title : "Advanced PD",
											        url : "/epc/namoCross/template/advancedPd.html",
											        charset : "utf-8"
										    	},
										    	{
											        title : "온오프상품 비식품",
											        url : "/epc/namoCross/template/onOffProdNonFood.html",
											        charset : "utf-8"
										    	},
										    	{
											        title : "온오프상품 식품",
											        url : "/epc/namoCross/template/onOffProdFood.html",
											        charset : "utf-8"
										    	}
											];
											CrossEditor.params.ActiveTab = 40;
											CrossEditor.params.UploadImageFileExtBlockList = ["jpg","JPG","jpeg","JPEG"];
											CrossEditor.EditorStart();

											function OnInitCompleted(e){
												e.editorTarget.SetBodyStyle('text-align','center');
												e.editorTarget.SetBodyValue(document.getElementById("viewProdDesc").value);
											}
										</script>
								</td>
							</tr>
						</table>
					</div>
					<!-- editor 끝 ----------------------------------------------------------------------->
				</div>
			</div>
		</div>

			<!-- footer 시작 -------------------------------------------------------------------------------------->
			<div id="footer">
				<div id="footbox">
					<div class="msg" id="resultMsg"></div>
					<div class="notice"></div>
					<div class="location">
						<ul>
							<li><spring:message code="msg.product.onOff.default.footerHome" /></li>
							<li><spring:message code="msg.product.onOff.default.footerItem" /></li>
							<li><spring:message code="msg.product.onOff.default.footerctrlNewItem" /></li>
							<li class="last"><spring:message code="msg.product.onOff.default.footerRegNewItem" /></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- footer 끝 -------------------------------------------------------------------------------------->
		</div>

         <!-- [비밀번호 초기화 ] '비밀번호 초기화' 클릭시 display '닫기' 클릭시 non display -->
		<div id="ViewLayer" style="display:none; position:absolute;  width:550px; height:450px; left: 10px; top: 1200px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
		<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
			<tr>
				<td bgcolor=ffffff>

					<table cellspacing=0 cellpadding=0 border=0 width=100%>
						<tr height=30>
							<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>소싱유형 구분</b></td>
							<td width=50><a href="#" class="btn" onclick="helpWin(false);"><span><spring:message code="button.common.close"/></span></a></td>
						</tr>
						<tr><td height=2 bgcolor="f4383f" colspan=2></td></tr>
					</table>

					<table cellspacing=0 cellpadding=0 border=0 width=100%>
						<tr>
							<br/><td colspan=2><B>&nbsp; &nbsp;해외직수입</B></td>
						</tr>
						<tr>
							<td> &nbsp; &nbsp;- 수입원 : 롯데쇼핑</td>
						</tr>
						<tr><td colspan=2 height=10></td></tr>
						<tr>
							<td colspan=2><B>&nbsp; &nbsp;해외직접소싱</B></td>
						</tr>
						<tr>
							<td> &nbsp; &nbsp;- 수입원 : 파트너사이고, MD 주도로 해외공장 써칭/협상한 경우</td>
						</tr>
						<tr><td colspan=2 height=10></td></tr>
						<tr>
							<td colspan=2><B>&nbsp; &nbsp;해외벤더소싱</B></td>
						</tr>
						<tr>
							<td> &nbsp; &nbsp;- 수입원 : 파트너사이고, 파트너사가 해외공장 써칭/협상한 경우</td>
						</tr><tr><td colspan=2 height=10></td></tr>
						<tr>
							<td colspan=2><B>&nbsp; &nbsp;국내상품</B></td>
						</tr>
						<tr>
							<td> &nbsp; &nbsp;- 국내제조상품(수입원 X)</td>
						</tr>
						<tr>
						    <td colspan="2" height="15"></td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
		</div>

		<!-- hidden Form -->
		<form name="hiddenForm" id="hiddenForm">
			<input type="hidden" name="vendorTypeCd" id="vendorTypeCd" value="<c:out value='${epcLoginVO.vendorTypeCd}'/>" />
			<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${newProdDetailInfo.pgmId }'/>" />
			<!-- 상품이 등록되고 나면 등록된 상품의 pgmId가 설정됨 -->
			<input type="hidden" name="entpCd" id="entpCd" value="<c:out value='${newProdDetailInfo.entpCd }'/>" />
			<!-- 상품이 등록되고 나면 등록된 상품의 협력업체코드가 설정됨 -->
			<input type="hidden" name="mode" id="mode" value="<c:out value='${param.mode}'/>" />
			<!-- view, modify, ''-->
			<input type="hidden" name="oldL3Cd" id="oldL3Cd" />
			<input type="hidden" name="oldGrpCd" id="oldGrpCd" />
			<input type="hidden" name="l1Cd"    id="l1Cd"    value="<c:out value='${newProdDetailInfo.l1Cd }'/>" />
			<input type="hidden" name="l3Cd"    id="l3Cd"    value="<c:out value='${newProdDetailInfo.l3Cd }'/>" />
			<input type="hidden" name="oldL3Cd" id="oldL3Cd" value="<c:out value='${newProdDetailInfo.l3Cd }'/>" />
			<input type="hidden" name="delNutAmtYn" id="delNutAmtYn" value="N"/>
			<input type="hidden" name="oldProdAttTypFg" id="oldProdAttTypFg" />
			<input type="hidden" name="cfmFg" id="cfmFg" value="<c:out value='${param.cfmFg}'/>" />
			<input type="hidden" name="hiddenProdDivnCd" id="hiddenProdDivnCd" value="<c:out value='${newProdDetailInfo.prodDivnCd}'/>" />
			<input type="hidden" name="ecCategoryKeepYn" id="ecCategoryKeepYn" value="true"/>
			<input type="hidden" name="ecAttrRegYn" 	 id="ecAttrRegYn" />
			<!-- 상품확정구분 -->
			<input type="hidden" name="entpCdPbFlag" id="entpCdPbFlag" value="" />
		</form>
	</body>
</html>
