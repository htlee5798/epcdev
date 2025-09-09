<%--
	Page Name 	: NEDMPRO0021.jsp
	Description : 신상품등록 [온오프전용-상품속성]
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code='msg.product.onOff.title'/></title>

	<style type="text/css">
		/* TAB */
		.tabs {height:31px; background:#fff;}
		.tabs ul {width:100%; height:31px;}
		.tabs li {float:left; width:130px; height:29px; background:#fff; border:1px solid #ccd0d9; border-radius:2px 2px 0 0; font-size:12px; color:#666; line-height:30px; text-align:center;}
		.tabs li.on {border-bottom:#e7eaef 1px solid; color:#333; font-weight:bold;}
		.tabs li a {display:block; color:#666;}
		.tabs li.on a {color:#333; font-weight:bold;}

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
	</style>

	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>

	<script type="text/javascript" >
		// 변형속성 Length
		var varAttLen = 0;

		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {
			//----- 상품변형속성 설정 ------------------------------
			var classLen 		= '<c:out value="${fn:length(classList)}" />';
			var prodAttTypFg 	= '<c:out value="${prodDetailInfo.prodAttTypFg}" />';	// 묶음상품일 경우에만 상품변형속성 보여 짐

			//console.log(prodAttTypFg);

			if (classLen == "0" || prodAttTypFg == "00") {
				$("#divProdVarAtt").hide();
			} else {
				// 등록된 속성이 있으면 해당하는 CLASS를 가져와서 설정한다.
				var prodVarAttClass = '<c:out value="${prodVarAttClass}" />';
				if (prodVarAttClass != "") {
					$("select[name='tmplClassCd']").val('<c:out value="${prodVarAttClass}" />');
					doClassCdChange('<c:out value="${prodVarAttClass}" />');
				}

				//----- 등록된 분석속성 재정렬(기 등록된 데이터)
				doIsAttReApply();
			}
			//--------------------------------------------------


			//----- 그룹분석속성 설정 ------------------------------
			var grpAttLen 		= '<c:out value="${fn:length(grpAtt)}" />';
			if (grpAttLen == "0") {
				$("#divGrpAtt").hide();
			}
			//--------------------------------------------------

			/* for (var i = 0; i < attGrpLen; i++) {
				var grpOptlen = $("#attGrpCd" + (i + 1) + " option").size();
				console.log(grpOptlen);
				//console.log("----->" + $("#attGrpCd" + (i + 1) + " option").size());
			} */
			//------------------------------------------------------------

			//-- 탭 클릭 이벤트 --------------------
			$("#prodTabs li").click(function() {
				var id = this.id;

				var pgmId = $("input[name='pgmId']").val();



				//기본정보 탭
				if (id == "pro01") {
					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0020Detail.do'/>");
					$("#hiddenForm").attr("method", "post").submit();
				//속성입력 탭
				} else if (id == "pro02") {
					/* if (pgmId == "") {
						alert("상품의 기본정보를 먼저 등록해주세요.");
						return;
					}

					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0021.do'/>");
					$("#hiddenForm").attr("method", "post").submit(); */

				//이미지 등록 탭
				} else if (id == "pro03") {
					if (pgmId == "") {
						alert("상품의 기본정보를 먼저 등록해주세요.");
						return;
					}

					var orgVariantCnt 	= $("input[name='orgVariant']").length;					// 등록된 변형속성
					var attGrpLen 		= '<c:out value="${fn:length(attGrp)}" />';				// 변형속성 size
					var prodAttTypFg 	= '<c:out value="${prodDetailInfo.prodAttTypFg}" />';	// 묶음상품 여부

					//console.log(orgVariantCnt);
					//console.log(attGrpLen);
					//console.log(prodAttTypFg);

					var isOrgVariant = 0;
					if (attGrpLen > 0 && prodAttTypFg == "01") {		// 변형속성이 있고 묶음상품일 경우
						for (var i = 0; i < orgVariantCnt; i++) {
							if ($("input[name='orgVariant']").eq(i).val() != "") {
								isOrgVariant++;
							}
						}

						if (isOrgVariant <= 0) {
							alert("상품의 속성은 하나이상 등록하셔야 합니다.");
							return;
						}
					}

					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0022.do'/>");
					$("#hiddenForm").attr("method", "post").submit();
				
				//영양성분 탭
				} else if (id == "pro04") {
					if (pgmId == "") {
						alert("<spring:message code='msg.product.tab.mst'/>");
						return;
					}

					var orgVariantCnt 	= $("input[name='orgVariant']").length;					// 등록된 변형속성
					var attGrpLen 		= '<c:out value="${fn:length(attGrp)}" />';				// 변형속성 size
					var prodAttTypFg 	= '<c:out value="${prodDetailInfo.prodAttTypFg}" />';	// 묶음상품 여부

					//console.log(orgVariantCnt);
					//console.log(attGrpLen);
					//console.log(prodAttTypFg);

					var isOrgVariant = 0;
					if (attGrpLen > 0 && prodAttTypFg == "01") {		// 변형속성이 있고 묶음상품일 경우
						for (var i = 0; i < orgVariantCnt; i++) {
							if ($("input[name='orgVariant']").eq(i).val() != "") {
								isOrgVariant++;
							}
						}

						if (isOrgVariant <= 0) {
							alert("상품의 속성은 하나이상 등록하셔야 합니다.");
							return;
						}
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

			// [200416 EC상품속성 상품구분변경]
			$(":radio[name='attrPiType']").change(function() {
				if($("select[name='ecProductAttr']").length > 0) {
					if (!checkEcPiTypeWithMartAttr()) {
						$(":radio[name='attrPiType']:radio[value='" + $("#prev-attrPiType").val() + "']").prop('checked', true)
						return;
					}
					if (!confirm("상품 구분 변경시 상품속성이 초기화됩니다.\n계속하시겠습니까?")) {
						$(":radio[name='attrPiType']:radio[value='" + $("#prev-attrPiType").val() + "']").prop('checked', true)
						return;
					}
				}
				$("#prev-attrPiType").val($(':radio[name=attrPiType]:checked').val());
				addEcProductAttr($(this).val(), 'change');
			});

			// 단일형상품 - 선택입력/직접입력 변경시
			$(document).on("change", "input:radio[name=prodDirectYn]", function () {
				//console.log("prodDirectYn click ::: " + $("input:radio[name=prodDirectYn]:checked").val());
				if ($("input:radio[name=prodDirectYn]:checked").val() == "N" ) { // 선택입력
					$("#singleProductAttrDiv").find("select").attr("disabled", false);
					$("#singleProductAttrDiv").find("input:text").val("");
					$("#singleProductAttrDiv").find("input:text").attr("disabled", true);
					$("#singleProductAttrDiv").find("input:text").css("display", "none");
					$("#singleProductAttrDiv").find("input:hidden").attr("disabled", true);
				} else { // 직접입력
					$("#singleProductAttrDiv").find("select").attr("disabled", true);
					$("#singleProductAttrDiv").find("input:text").attr("disabled", false);
					$("#singleProductAttrDiv").find("input:text").css("display", "");
					$("#singleProductAttrDiv").find("input:text").val("");
					$("#singleProductAttrDiv").find("input:hidden").attr("disabled", false);
				}
			});

			// 옵션형상품 - 선택입력/직접입력 변경시
			$(document).on("change", "input:radio[name=optnDirectYn]", function () {
				if ($("input:radio[name=optnDirectYn]:checked").val() == "N" ) { // 선택입력
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", false);
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").val("");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default"); // "required",
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").val("");
					//$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").removeClass("required");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", true);
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "none");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", true);
				} else { // 직접입력
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").val("");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", true);
					//$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").addClass("required");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", false);
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").val("");
					$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", false);
				}
                hideEcFashionAttr(true);
			});

			/* //-- 단품정보 제어
			$(":radio[name='prodPrcMgrYn']").change(function(){
				prodPrcMgrYnChk($(this).val());
			}); */


			// [200416 EC상품속성추가]

			/* var prodPrcMgrYn 	= '<c:out value="${prodDetailInfo.prodPrcMgrYn}" />';	// 옵션별 가격 동일유무
			if (prodPrcMgrYn!= "" || prodPrcMgrYn !="undefined") {
				$(":radio[name='prodPrcMgrYn']:radio[value='" + prodPrcMgrYn + "']").attr("checked", true);
// 				prodPrcMgrYnChk(prodPrcMgrYn); //단품정보 제어
			} */

		    
			var prodDivnCd = "<c:out value='${prodDetailInfo.prodDivnCd}'/>";
			if(prodDivnCd == 5)
			{
				////alert("속성정보가 2개 이상 등록될 경우, EC상품속성은 옵션형 상품을 선택하셔야 합니다");
				setEcAttribute();
			}


			//------------------------------------------------------------
		});

		/* 속성추가 Validation */
		function doAddValidation() {

			var prodDivnCd = "<c:out value='${prodDetailInfo.prodDivnCd}'/>";
			var divAttCnt = $("table[name=tblAtt]").length;
			var ecAttrRegYn = "<c:out value='${prodDetailInfo.ecAttrRegYn}'/>";
			
			if(prodDivnCd == 5 && divAttCnt > 0 && ecAttrRegYn == 'Y') {
				if(!checkAttWithEcPiType()) return;
			}
			
						
			var selClassCd = $("select[name='tmplClassCd']").val();
			var applyCnt = $("input[name='applyCnt']").val();

			if (selClassCd == "") {
				alert("상품속성그룹을 선택해주세요.");
				return;
			}

			if (Number(applyCnt) > 30) {
				alert("일괄적용은 한번에 30개 까지만 가능합니다");
				return;
			}

			// 속성그룹 체크(하나이상 입력 불가능)
			if ($("#divAtt #tblAtt tr").length > 0) {
				var addClassCd = $("#classCd1").val();
				//console.log($("#tmplClassCd").val());
				if ($("#tmplClassCd").val() != addClassCd) {
					alert("상품속성그룹은 하나만 입력이 가능합니다.");
					$("#tmplClassCd").val(addClassCd);
					doClassCdChange(addClassCd);
					return false;
				}
			}

			return true;

		}

		
		function checkAttWithEcPiType() {
			var attrPiType = $('input[name=attrPiType]:checked').val();
			
			if( attrPiType == 'P' ) {
				alert("속성정보가 2개 이상 등록될 경우, EC상품속성은 옵션형 상품을 선택하셔야 합니다");
				return false;
			}
			return true;
		}

		function checkEcPiTypeWithMartAttr() {
			var divAttCnt = $("table[name=tblAtt]").length;
			var prevAttrPiType = $("input[name=prev-attrPiType]").val();
			if( prevAttrPiType =="I" && divAttCnt > 1 ) {
				alert("마트 속성정보 값이 2개 이상인 경우 EC 단일형 속성으로 선택하실 수 없습니다.");
				return false;
			}	
			return true;
		}
		
		/* 추가 */
		function doAttAdd() {
			if (!doAddValidation()) {
				return;
			}

			
			//----- TABLE 복사
			$trClone = $("#tblAttTemplete").clone().html();
			//console.log($trClone);
			$newTbl = $("<table id='tblAtt' name='tblAtt' class='bbs_grid3'>" + $trClone + "</table>");
			/* <table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0"> */
			// 추가
			
			$("#divAtt").append($newTbl);

			
			//속성 재적용
			doTmplAttReApply();
		}

		/*
		----------------------------------------------------------------------
		- Templete 속성 재 적용
		----------------------------------------------------------------------
		- id, name을 변경하는 이유는 templete Component와 충돌이 나므로 변경
		- 변경하지 않을 경우 templete Component의 모든 이벤트가 같이 적용이 되어서 문제가 발생
		- 수정 시 doIsAttReApply() 이벤트 로직 확인 후 같이 수정이 들어가야 할 부분 발생할 수 있음
		----------------------------------------------------------------------
		*/
		function doTmplAttReApply() {
			var tblCnt = $("#divAtt table").length;
			for (var i = 0; i < tblCnt; i++) {
				$tbl = $("#divAtt table").eq(i);

				//-- 삭제, SEQ 설정 Start ----------------------------------------
				$tbl.find("th, span, input").each(function() {
					var tmpId = this.id;

					// 삭제 column 보이기
					if (tmpId == "tmplRowDelTr") {
						$(this).attr("name", 	"rowDelTr");
						$(this).attr("id", 		"rowDelTr");
						$(this).show();
					}

					// 삭제 checkbox 보이기
					if (tmpId == "tmplRowDel") {
						$(this).attr("name", 	"rowDel");
						$(this).attr("id", 		"rowDel");
						//첫번째는 삭제 안되게 숨기기
						if (i == 0) {
							$(this).attr("disabled", 		true);
						}
					}

					// 순번 column 보이기
					if (tmpId == "tmplRowSeqTr") {
						$(this).attr("name", 	"rowSeqTr");
						$(this).attr("id", 		"rowSeqTr");
						$(this).show();
					}

					// column 에 순번 설정
					if (tmpId == "tmplRowSeq") {
						$(this).attr("name", 	"rowSeq");
						$(this).attr("id", 		"rowSeq");
						$(this).text(i + 1);
					}

					// variant 설정
					if (tmpId == "tmplVariant") {
						$(this).attr("name", 	"variant");
						$(this).attr("id", 		"variant");
						$(this).val(i + 1);
					}

					// variant 설정
					if (tmpId == "tmplOrgVariant") {
						$(this).attr("name", 	"orgVariant");
						$(this).attr("id", 		"orgVariant");
					}
				});
				//-- 삭제, SEQ 설정 End ----------------------------------------

				// 속성 id, name 재 설정
				$tbl.find("input, select").each(function() {
					var tmpNm = this.name;

					//-- 그룹 CLASS id, name 재정의 Start ----------------------------------------
					var attIdx = tmpNm.indexOf("tmplClassCd");
					if (attIdx > -1) {
						var idx = tmpNm.substring(11);	// 변형속성 index 가져오기

						// templete 선택값 가져오기
						var tmplClassCd = $("input[name='tmplClassCd" + idx + "']").val();

						$(this).attr("name", 	"classCd" + idx);
						$(this).attr("id", 		"classCd" + idx);
						$(this).val(tmplClassCd);	// templete 선택값 설정
					}
					//-- 그룹 CLASS id, name 재정의 End ----------------------------------------

					//-- 그룹속성 id, name 재정의 Start ----------------------------------------
					var attIdx = tmpNm.indexOf("tmplAttGrpCd");
					if (attIdx > -1) {
						var idx = tmpNm.substring(12);	// index 가져오기

						// templete 선택값 가져오기
						var tmplAttGrpCd = $("input[name='tmplAttGrpCd" + idx + "']").val();

						$(this).attr("name", 	"attGrpCd" + idx);
						$(this).attr("id", 		"attGrpCd" + idx);
						$(this).val(tmplAttGrpCd);	// templete 선택값 설정
					}
					//-- 그룹속성 id, name 재정의 End ----------------------------------------

					//-- 속성 id, name 재정의 Start ----------------------------------------
					var attIdx = tmpNm.indexOf("tmplAttDetailCd");
					if (attIdx > -1) {
						var idx = tmpNm.substring(15);	// 변형속성 index 가져오기

						// templete 선택값 가져오기
						var tmplAttDetailCd = $("select[name='tmplAttDetailCd" + idx + "']").val();

						$(this).attr("name", 	"attDetailCd" + idx);
						$(this).attr("id", 		"attDetailCd" + idx);
						$(this).val(tmplAttDetailCd);	// templete 선택값 설정
					}
					//-- 속성 id, name 재정의 End ----------------------------------------

					//-- 88코드 id, name 재정의 Start ----------------------------------------
					var selIdx = tmpNm.indexOf("tmplSellCd");
					if (selIdx > -1) {
						// templete 선택값 가져오기
						var tmplSellCd = $("input[name='tmplSellCd']").val();

						$(this).attr("name", 	"sellCd");
						$(this).attr("id", 		"sellCd");

						$(this).val(tmplSellCd);	// templete 선택값 설정
					}
					//-- 88코드 id, name 재정의 End ------------------------------

					//-- 삭제버튼 설정 Start ----------------------------------------
					/* if (this.type == "button") {
						$(this).attr("name", 	"btnAttDel");
						$(this).attr("id", 		"btnAttDel");
						$(this).show();

						//$(this).unbind("click");   // click 이벤트 제거(제거하지 않으면 여러번 적용 됨)
						//$(this).bind("click", doDeleteAtt);
						$(this).unbind().click(i + 1, doDeleteAtt);
					} */
					//-- 삭제버튼 설정 End ----------------------------------------

					// 전체 설정값 조합 id, name 재정의
					var attIdx = tmpNm.indexOf("tmplAllValue");
					if (attIdx > -1) {	// 변형속성일 경우
						$(this).attr("name", 	"allValue");
						$(this).attr("id", 		"allValue");
					}
				});
			}

			// 추가된 한목 재정렬
			doIsAttReApply();
		}

		/* 일괄적용 */
		function doAutoApply() {
			if (!doAddValidation()) {
				return;
			}

			var applyCnt = $("#applyCnt").val();
			if (applyCnt == "" || applyCnt == "0") {
				return;
			}

			for (var i = 0; i < applyCnt; i++) {
				//----- TABLE 복사
				$trClone = $("#tblAttTemplete").clone().html();
				//console.log($trClone);
				$newTbl = $("<table id='tblAtt' name='tblAtt' class='bbs_grid3'>" + $trClone + "</table>");

				// 추가
				$("#divAtt").append($newTbl);
			}

			// 기존 속성정보 재 설정(중복체크값 설정 및 SEQ 한번더 확인하여 설정)
			doTmplAttReApply();
		}

		/* 추가된 속성 변경 이벤트 */
		function doAttDetailCdChange(obj) {
			var idx = (obj.id).indexOf("tmplAttDetailCd");
			//console.log(obj.id);
			if (idx < 0) {	// templete 은 제외하고 재 정의하기 위함
				doIsAttReApply();
			}
		}

		/* 삭제 이벤트 */
		function doDeleteAtt(e) {
			//console.log(e.data);
			$("#divAtt table").eq(e.data - 1).remove();

			doIsAttReApply();
		}

		/*
		----------------------------------------------------------------------
		- 기존 속성정보 재 설정
		----------------------------------------------------------------------
		- SEQ 설정
		- 중복 체크값 설정(CLASS|속성값1|)
		- 수정 시 doTmplAttReApply() 이벤트 로직 확인 후 같이 수정이 들어가야 할 부분 발생할 수 있음
		----------------------------------------------------------------------
		*/
		function doIsAttReApply() {
			var tblCnt = $("#divAtt table").length;

			//----- 기 입력된 VARIANT의 최대값을 찾아옴 ----------
			var idxVariant = 0;
			if (tblCnt != 0) {
				for (var i = 0; i < tblCnt; i++) {
					var orgVariant = $("input[name='orgVariant']").eq(i).val();
					if (orgVariant != "") {
						idxVariant = Number(orgVariant);
					}
				}
			}
			//--------------------------------------------------

			//console.log("tblCnt----->" + tblCnt);
			for (var i = 0; i < tblCnt; i++) {
				$tbl = $("#divAtt table").eq(i);
				//console.log($tbl);

				// 기 등록된 VARIANT
				var orgVariant = $tbl.find("input[name='orgVariant']").val();

				// SEQ 붙이기
				$tbl.find("span[id='rowSeq']").text(i + 1);

				if (orgVariant != "") {		// 기등록된 변형속성일 경우
					$tbl.find("input[name='variant']").val(Number(orgVariant));
				} else {					// 새로 추가한 변형속성일 경우(기 등록된 변형속성 MAX값 ++)
					idxVariant++;
					$tbl.find("input[name='variant']").val(Number(idxVariant));

				}

				var allValue = "";

				// 속성 id, name 재 설정
				$tbl.find("input, select").each(function() {
					var tmpNm = this.name;

					//-- 그룹 CLASS id, name 재정의 Start ----------------------------------------
					var attIdx = tmpNm.indexOf("classCd");
					if (attIdx > -1) {
						allValue = allValue + "|" + $(this).val();
					}
					//-- 그룹 CLASS id, name 재정의 End ----------------------------------------

					//-- 그룹속성 id, name 재정의 Start ----------------------------------------
					var attIdx = tmpNm.indexOf("attGrpCd");
					if (attIdx > -1) {
						allValue = allValue + "|" + $(this).val();
					}
					//-- 그룹속성 id, name 재정의 End ----------------------------------------

					//-- 속성 id, name 재정의 Start ----------------------------------------
					var attIdx = tmpNm.indexOf("attDetailCd");
					if (attIdx > -1) {
						allValue = allValue + "|" + $(this).val();
					}
					//-- 속성 id, name 재정의 End ----------------------------------------


					//-- 삭제버튼 설정 Start ----------------------------------------
					/* if (this.type == "button") {
						$(this).unbind().click(i + 1, doDeleteAtt);
					} */
					//-- 삭제버튼 설정 End ----------------------------------------
				});

				$("input[name='allValue']").eq(i).val(allValue);	// 전체값 설정
			}

			//88코드 관련 필드 검증
			_setSellCdBlurEvent();
		}

		/* 상품 변형속성 저장 이벤트 */
		function _eventSave() {

			var errorLength = $("div[name=error_msg]").length;
			if (errorLength > 0) {
				alert("88코드를 확인해주세요");
				return;
			}

			var prodAttTypFg = '<c:out value="${prodDetailInfo.prodAttTypFg}" />';	// 묶음상품일 경우에만 저장
			//console.log(prodAttTypFg);

			if (prodAttTypFg == "01") {
				/* var tblCnt = $("#divAtt table").length;
				if (tblCnt <= 0) {
					alert("상품속성을 등록해주세요.");
					return;
				} */

				// 설정값 중복 체크
				var allValueCnt = $("input[name='allValue']").length;
				for (var i = 0; i < allValueCnt; i++) {

					// 판매코드가 입력 되었을 경우 13자리인지 체크
					var sellCd = $("input[name='sellCd']").eq(i).val().length;
					if (sellCd > 0 && sellCd < 13) {
						alert("판매코드는 13자리로 입력하셔야 합니다.");
						return;
					}

					if (!$("input[name='rowDel']").eq(i).is(":checked")) {	// 삭제 대상인건은 중복체크 제외
						for (var j = 0; j < allValueCnt; j++) {
							if (i != j) {
								var iValue = $("input[name='allValue']").eq(i).val();
								var jValue = $("input[name='allValue']").eq(j).val();

								var iValueSplit	=	iValue.split("|");

								//console.log("111 ==" + iValueSplit[3]);
								//console.log("222 ==" + iValueSplit[6]);
								//iValue = |ZCOLOR_002|ZPROFILE_036|001|ZSIZE_024|ZPROFILE_036|004
								if (iValueSplit[3] == "" || iValueSplit[6] == "") {
									alert("상품속성을 선택해주세요");
									return;
								}

								//console.log(i + "::" + j + "====" + iValue + "::" + jValue);
								if (!$("input[name='rowDel']").eq(j).is(":checked")) {	// 삭제 대상인건은 중복체크 제외
									if (iValue == jValue) {
										alert((Number(i) + 1) + "번째 속성과 " + (Number(j) + 1) + "번째 속성값이 중복입니다.");
										return;
									}
								}
							} else {
								//console.log($("input[name='allValue']").eq(i).val());
								var iValue = $("input[name='allValue']").eq(i).val();
								var iValueSplit	=	iValue.split("|");

								if (iValueSplit[3] == "" || iValueSplit[6] == "") {
									alert("상품속성을 선택해주세요");
									return;
								}
							}
						}
					}
				}

				// 88코드 중복체크
				for (var i = 0; i < allValueCnt; i++) {

					if (!$("input[name='rowDel']").eq(i).is(":checked")) {	// 삭제 대상인건은 중복체크 제외

						// 판매코드가 입력 되었을 경우 13자리인지 체크
						var sellCd = $("input[name='sellCd']").eq(i).val().length;
						if (sellCd > 0 && sellCd < 13) {
							alert("판매코드는 13자리로 입력하셔야 합니다.");
							return;
						}

						for (var j = 0; j < allValueCnt; j++) {
							if (i != j) {
								var iValue = $("input[name='sellCd']").eq(i).val();
								var jValue = $("input[name='sellCd']").eq(j).val();

								if (iValue != "") {
									//console.log(i + "::" + j + "====" + iValue + "::" + jValue);
									if (!$("input[name='rowDel']").eq(j).is(":checked")) {	// 삭제 대상인건은 중복체크 제외
										if (jValue != "") {
											if (iValue == jValue) {
												alert((Number(i) + 1) + "번째 속성과 " + (Number(j) + 1) + "번째 속성의 88코드 값이 중복입니다.");
												return;
											}
										}
									}
								}
							}
						}
					}
				}

				var rowDelAl		= new Array();	// 삭제대상
				var variantAl 		= new Array();	// variant 배열
				var orgVariantAl 	= new Array();	// 기존 등록된 VARIANT 배열
				var sellCdAl 		= new Array();	// 판매코드 배열

				var variantCnt = $("input[name='variant']").length;

				var k = 0;
				for (var i = 0; i < variantCnt; i++) {		// 추가한 변형속성
					for (var j = 0; j < varAttLen; j++) {	// 선택된 CLASS의 변형속성 수 만큼 데이터 생성(변형속성 row를 맞추기 위함)
						//console.log(i + "::" + k);

						if ($("input[name='rowDel']").eq(i).is(":checked")) {
							rowDelAl[k] = "1";
						} else {
							rowDelAl[k] = "0";
						}

						variantAl[k] 	= $("input[name='variant']").eq(i).val();
						orgVariantAl[k] = $("input[name='orgVariant']").eq(i).val();
						sellCdAl[k] 	= $("input[name='sellCd']").eq(i).val();
						k++;
					}
					//console.log(variantAl[0]);
				}

				// 88코드 배열로 설정
				/* var sellCdAl = new Array();
				var sellCdCnt = $("input[name='sellCd']").length;
				var k = 0;
				for (var i = 0; i < sellCdCnt; i++) {
					for (var j = 0; j < attGrpLen; j++) {
						sellCdAl[k++] = $("input[name='sellCd']").eq(i).val();
					}
					//console.log($("input[name='sellCd']").eq(i).val());
				} */


				var rowCnt = $("#divAtt #tblAtt tr").length;

				var attInfoAl = new Array();
				for (var i = 0; i < rowCnt; i++) {
					//console.log(i);
					$row = $("#divAtt table tr:eq(" + i + ")");

					if ($row != null && $row != "") {
						var info = {};

						$row.find("input, select").each(function() {
							if (this.type != "button") {
								var tmpNm = this.name;

								if (tmpNm != "variant" && tmpNm != "sellCd" && tmpNm != "allValue") {
									var idx = tmpNm.indexOf("attGrpCd");
									if (idx > -1) {
										info["attGrpCd"] = $(this).val();
									}

									var idx = tmpNm.indexOf("classCd");
									if (idx > -1) {
										info["classCd"] = $(this).val();
									}

									var idx = tmpNm.indexOf("attDetailCd");
									if (idx > -1) {
										info["attDetailCd"] = $(this).val();
									}
								}
							}
						});

						info["rowDel"] 		= rowDelAl[i];
						info["variant"] 	= variantAl[i];
						info["sellCd"] 		= sellCdAl[i];
						info["orgVariant"] 	= orgVariantAl[i];

						attInfoAl.push(info);
					}
				}

				//-----입력된 판매코드만 배열로 생성[위해상품의 판매코드인지 체크하기 위해 사용]
				var arrSrcmkCd	=	new Array();
				var srcmkCdLen 	= 	$("input[name='sellCd']").length;
				var idx2 = 0;
				for (var idx = 0; idx < srcmkCdLen; idx++) {
					if ($("input[name='sellCd']").eq(idx).val().replace(/\s/gi, '') != "") {
							arrSrcmkCd[idx2]	=	$("input[name='sellCd']").eq(idx).val();
							idx2++;
					}
				}


				//-----판매코드 필수 입력 체크------------------------------------------------------------------
				var sellCdLen	=	$("input[name='sellCd']").length;
				if (sellCdLen > 0) {
					for (var i = 0; i < sellCdLen; i++) {
						if (!$("input[name='rowDel']").eq(i).is(":checked")) {	// 삭제 대상인건은 체크 제외
							var sellCd	=	$("input[name='sellCd']").eq(i).val().replace(/\s/gi, '');

							var adminId	= '<c:out value='${epcLoginVO.adminId}'/>';			//상품등록이후 조회된 상품의 협력업체 코드

					 			if (adminId == "" || adminId == "online") { //업체면 그리고 online 이면
// 											if (sellCd == "") {
// 												alert("해당속성의 판매코드는 반드시 입력해주세요.");
// 												return;
// 											}
							 	}
						}
					}
				}
				//-----------------------------------------------------------------------------------

				if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
					return;
				}

				//-----상품속성 필수입력 체크
				/* for (var i = 0; i < attInfoAl.length; i++) {
					if (attInfoAl[i].attDetailCd == "") {
						alert("상품속성입력은 필수입니다");
						return;
					}
					//console.log(attInfoAl[i].attDetailCd);
				} */

				var paramInfo = {};
				paramInfo["pgmId"] 		= $("input[name='pgmId']").val();
				paramInfo["l3Cd"] 		= $("input[name='prodRange']").val();
				paramInfo["entpCd"] 	= $("input[name='entpCd']").val();
				paramInfo["attInfoAl"] 	= attInfoAl;
				paramInfo["arrSrcmkCd"]	= arrSrcmkCd;



				//console.log(paramInfo);

				$.ajax({
					contentType : 'application/json; charset=utf-8',
					type : 'post',
					dataType : 'json',
					async : false,
					url : '<c:url value="/edi/product/saveVarAtt.json"/>',
					data : JSON.stringify(paramInfo),
					success : function(data) {
						if (data.msgCd == "S") {
							alert('<spring:message code="msg.common.success.save" />');

							$("#hiddenForm").attr("method", "post");
							$("#hiddenForm").attr("action", "<c:url value="/edi/product/NEDMPRO0021.do" />");
							$("#hiddenForm").submit();
						} else if (data.msgCd == "DANGER") {
							alert("<spring:message code='msg.prod.danger'/>");	//해당 판매코드는 위해상품으로 등록된 판매코드 이므로 상품을 등록 하실 수 없습니다
						} else {
							alert('<spring:message code="msg.common.fail.request" />');
						}
					}
				});
			}
		}

		/* Class 변경에 따른 변형속성 설정 */
		function doClassCdChange(str) {
			var paramInfo = {};
			paramInfo["pgmId"] 		= $("input[name='pgmId']").val();
			paramInfo["prodRange"] 	= $("input[name='prodRange']").val();
			paramInfo["classCd"] 	= str;

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectClassVarAtt.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					$("#tblAttTemplete tr").remove();

					var varAtt 		= data.varAtt;
					var varAttOpt 	= data.varAttOpt;

					// 전역변수에 변형속성 Length 설정
					varAttLen = varAtt.length;

					var trHtml = "";
					for (var i = 0; i < varAtt.length; i++) {
						trHtml = "";
						trHtml = trHtml + "<tr>\n";

						if (i == 0) {
							trHtml = trHtml + "	<th style='width: 5%; display: none;' id='tmplRowDelTr' name='tmplRowDelTr' rowspan='" + varAtt.length + "'><input type='checkbox' id='tmplRowDel' name='tmplRowDel' value='1'></th>\n";
							trHtml = trHtml + "	<th style='width: 5%; display: none;' id='tmplRowSeqTr' name='tmplRowSeqTr' rowspan='" + varAtt.length + "'><span id='tmplRowSeq' name='tmplRowSeq'></span></th>\n";
						}

						trHtml = trHtml + "	<th style='width: 150px;'>";
						trHtml = trHtml + varAtt[i].attGrpNm + "\n";
						trHtml = trHtml + "	<input type='hidden' id='tmplAttGrpCd" + (i + 1) + "' name='tmplAttGrpCd" + (i + 1) + "' value='" + varAtt[i].attGrpCd + "' />\n";
						trHtml = trHtml + "<input type='hidden' id='tmplClassCd" + (i + 1) + "' name='tmplClassCd" + (i + 1) + "' value='" + varAtt[i].classCd + "' />\n";
						trHtml = trHtml + "	</td>\n";

						trHtml = trHtml + "	<td style='*'>\n";
						trHtml = trHtml + "		<select id='tmplAttDetailCd" + (i + 1) + "' name='tmplAttDetailCd" + (i + 1) + "' onChange='doAttDetailCdChange(this);'>\n";

						trHtml = trHtml + "			<option value=''>-선택-</option>\n";
						for (var j = 0; j < varAttOpt.length; j++) {
							if (varAtt[i].classCd == varAttOpt[j].classCd && varAtt[i].attGrpCd == varAttOpt[j].attGrpCd) {
								trHtml = trHtml + "			<option value='" + varAttOpt[j].attDetailCd + "'>" + varAttOpt[j].attDetailNm + "</option>\n";
							}
						}
						trHtml = trHtml + "		</select>\n";
						trHtml = trHtml + "	</td>\n";

						if (i == 0) {

						var adminId	=	'<c:out value='${epcLoginVO.adminId}'/>';
						if (adminId == "" || adminId == "online") {
				 			trHtml = trHtml + "	<th style='width: 100px;' rowspan='" + varAtt.length + "'>88코드</th>\n";

				 			} else {
			 				trHtml = trHtml + "	<th style='width: 100px;' rowspan='" + varAtt.length + "'>88코드</th>\n";

				 	 		}


							trHtml = trHtml + "	<td style='width: 40%;' rowspan='" + varAtt.length + "'>\n";
							trHtml = trHtml + "		<input type='text' id='tmplSellCd' name='tmplSellCd' value='' maxlength='13' />\n";
							trHtml = trHtml + "		<input type='hidden' id='tmplAllValue' 	name='tmplAllValue' 	value='' />\n";
							trHtml = trHtml + "		<input type='hidden' id='tmplVariant' 	name='tmplVariant' 		value='' />\n";
							trHtml = trHtml + "		<input type='hidden' id='tmplOrgVariant' name='tmplOrgVariant' 	value='' />\n";
							trHtml = trHtml + "	</td>";
						}

						trHtml = trHtml + "</tr>";
						//console.log(trHtml);

						$("#tblAttTemplete").append(trHtml);
					}

					//88코드 관련 필드 검증
					_setSellCdBlurEvent();
				}
			});
		}

		/* 판매코드 체크 이벤트 설정 */
		function _setSellCdBlurEvent() {
			//88코드 관련 필드 검증
			$("input[name='sellCd'], input[name='tmplSellCd']").blur(function() {
				if($(this).val() != '') {
					validateSellCode($(this));
				} else {
					deleteErrorMessageIfExist($("input[name=sellCd]"));
				}
			});
		}
		/*-------------------- 2015.12.30 변경 End --------------------*/

		/* 그룹분석속성 저장 이벤트 */
		function _eventGrpSave() {
			var grpAttLen 		= 	'<c:out value="${fn:length(grpAtt)}" />';
			var trdTypeDivnCd	=	"<c:out value='${prodDetailInfo.trdTypeDivnCd}'/>";		//거래유형타입[1:직매입, 2:특약1, 4:특약2]

			var attInfoAl = new Array();	// 그룹분석속성
			for (var i = 0; i < grpAttLen; i++) {
				var info = {};

				var attId		= $("input[name='attId" + (i + 1) + "']").val();
				var attValId 	= $("select[name='attValId" + (i + 1) + "']").val();

				//거래유형타입이 직매입과 특약1일 경우 분석속성 입력은 필수이다  [2016.05.08 요청으로 인해 2016.05.09 작업 by song min kyo]
				if (trdTypeDivnCd != "" && (trdTypeDivnCd == "1" || trdTypeDivnCd == "2")) {
					if (attValId == "") {
						alert("분석 속성 입력은 필수 입니다.");
						return;
					}
				}

				info["attId"] 		= attId;
				info["attValId"] 	= attValId;
				//console.log(attId + "::" + attValId);
				attInfoAl.push(info);
			}

			if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
				return;
			}

			var paramInfo = {};
			paramInfo["pgmId"] 		= $("input[name='pgmId']").val();
			paramInfo["attInfoAl"] 	= attInfoAl;

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/saveGrpAtt.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msgCd == "S") {
						alert('<spring:message code="msg.common.success.save" />');

						$("#hiddenForm").attr("method", "post");
						$("#hiddenForm").attr("action", "<c:url value="/edi/product/NEDMPRO0021.do" />");
						$("#hiddenForm").submit();
					} else {
						alert('<spring:message code="msg.common.fail.request" />');
					}
				}
			});
		}

		/* EC속성 저장 이벤트 */
		function _eventEcSave() {
			var index = $("#itemRow").val();

			var stdCatCd 		=   "<c:out value='${prodDetailInfo.stdCatCd}'/>";			//EC표준카테고리
			var prodNm			=   "<c:out value='${prodDetailInfo.prodNm}'/>";			//상품이름
			var entpCd			=  	"<c:out value='${prodDetailInfo.entpCd}'/>";			//업체코드
			var sellCd 			=	"<c:out value='${prodDetailInfo.sellCd}'/>";			//상품코드
			var prodDivnCd  	=	"<c:out value='${prodDetailInfo.prodDivnCd}'/>";		//상품속성
			var attrPiType 		= 	$(":radio[name='attrPiType']:checked").val();			//속성종류
			var optnDirectYn  	=   $(":radio[name='optnDirectYn']:checked").val();
			var prodDirectYn  	=   $(":radio[name='prodDirectYn']:checked").val();
			var ecAttrCheck		= 	false; 			// EC 속성 등록되어있는지 확인
			var ecAttrLen		=   null;
			
			var paramInfo = {};
			paramInfo["pgmId"] 		= $("input[name='pgmId']").val();
			paramInfo["sellCd"] 	 = sellCd;
			// [200416 상품속성 넣기] START

			paramInfo["stdCatCd"]=stdCatCd;
			paramInfo["prodNm"]=prodNm;
			paramInfo["entpCd"]=entpCd;
			paramInfo["attrPiType"]=attrPiType;
			paramInfo["prodDirectYn"]=prodDirectYn;
			paramInfo["optnDirectYn"]=optnDirectYn;

			//var prodPrcMgrYn	= $(":radio[name='prodPrcMgrYn']:checked").val();	// 상품 속성 집어넣기
			var prodAttrArr 	= new Array();
			var ecAttrId		= new Array();
			var itemCdArr 		= new Array();
			var stkMgrYnArr		= new Array();
			var rservStkQtyArr 	= new Array();
			var optnAmtArr 		= new Array();


			if(attrPiType == 'P')
			{
				if(prodDirectYn=='Y')
				{
					$("#singleProductAttrDiv input[name='ecProductAttr']").each(function(i,e){
						prodAttrArr.push($(this).val());
					});

					$("#singleProductAttrDiv input[name='ecProductAttrId']").each(function(i,e){
						ecAttrId.push($(this).val());
					});
				}
				else
				{
					$("#singleProductAttrDiv select[name='ecProductAttr']").each(function(i,e){
						prodAttrArr.push($(this).val());
					});
				}
			}
			else if(attrPiType == 'I')
			{
				if(optnDirectYn=='Y')
				{
					$("#multiProductAttrDiv input[name='ecProductAttr']").each(function(i,e){
						prodAttrArr.push($(this).val());
					});

					$("#multiProductAttrDiv input[name='ecProductAttrId']").each(function(i,e){
						ecAttrId.push($(this).val());
					});
				}
				else
				{
					$("#multiProductAttrDiv select[name='ecProductAttr']").each(function(i,e){
						prodAttrArr.push($(this).val());
					});
				}
			}

			$("input[name='itemCd']").each(function(i,e){
				itemCdArr.push($(this).val());
			});
			$("select[name='stkMgrYn']").each(function(i,e){
				stkMgrYnArr.push($(this).val());
			});
			$("input[name='rservStkQty']").each(function(i,e){
				rservStkQtyArr.push($(this).val());
			});
			$("input[name='optnAmt']").each(function(i,e){
				optnAmtArr.push($(this).val());
			});

			// 체크 항목 부분 시작
			
			ecAttrLen = prodAttrArr.length / itemCdArr.length;
			
			// [패션일 때 묶음형 상품에서 상품속성 값 하나만 입력되면 통과]_piy
			for(var i=0; i<prodAttrArr.length; i++){
						
					
					if(attrPiType == 'P' && prodAttrArr[i]=='') {
						alert("EC상품속성을 다 입력하셔야되니다.");
						return ;					
					}

					if(attrPiType=='I' && prodAttrArr[i]!='') {
						ecAttrCheck = true;
					}

					if(attrPiType == 'I' && i%ecAttrLen == ecAttrLen-1) { // EC속성 한 행을 다 검사 했을 때, 빈 곳이 있나 확인

						if(!ecAttrCheck) {
							alert("EC속성 값 최소 한 항목을 입력하셔야합니다.")
							return;
						}
						ecAttrCheck = false;
					}
			} 

			
			if(attrPiType == 'I') {
				if(!checkEcAttrValRdpOvl(prodAttrArr, itemCdArr.length, ecAttrLen)){
					alert("EC 속성 값 중에 중복된 값이 있습니다. 확인 부탁드립니다.");
					return ;
				}
			}		
			
			// 속성 값이 2개 이상 일 때, 단일형으로 저장하려는 경우.
			var divAttCnt = $("table[name=tblAtt]").length;
			if(attrPiType == 'P' && divAttCnt > 1 ) {
				alert("마트 속성 값이 2개 이상인 경우 EC 단일형 속성으로 저장하실 수 없습니다.");
				return ;
			}  
			
			paramInfo["ecProductAttr"]   = prodAttrArr;
			paramInfo["ecProductAttrId"] = ecAttrId;
			paramInfo["itemCd"]	 		 = itemCdArr;
			paramInfo["stkMgrYn"] 		 = stkMgrYnArr;
			paramInfo["rservStkQty"] 	 = rservStkQtyArr;
			paramInfo["optnAmt"] 		 = optnAmtArr;
			//paramInfo["prodPrcMgrYn"] = prodPrcMgrYn;

			// 단품정보 등록 확인 추가
			if(index > 0 && $(':radio[name="attrPiType"]:checked').val() == "I"){
				for(var i=1; i<=index; i++) {
					if($("#rservStkQty"+i).val() == ""){
						alert("단품정보 " + i + "행 단품 정보를 정확하게 입력하세요");
						return;
					}

					/* if($("#rservStkQty"+i).val().length >= 7){
			  	    	alert("단품정보 " + i + "행 재고수량 7자릿수 이상은 입력할 수 없습니다.");
			  	    	return;
					}

					if($(":radio[name='prodPrcMgrYn']:checked").val() == "1"){
						if($("#optnAmt"+i).val() == ""){
							alert("단품정보 " + i + "행 단품 가격 정보를 정확하게 입력하세요");
							return;
						}

						if(Number($("#optnAmt"+i).val()) == 0){
							alert("단품정보 " + i + "행 단품 가격을 0원 이상 입력하세요");
				  	    	return;
						}

						if($("#optnAmt"+i).val().length >= 10){
				  	    	alert("단품정보 " + i + "행 단품 가격 10자릿수 이상은 입력할 수 없습니다.");
				  	    	return;
						}
					} */

				 }
			} else {
				//$(':radio[name="prodPrcMgrYn"]').val('0');
			}

			// [200416 상품속성 넣기] END

			if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
				return;
			}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/saveEcAtt.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msgCd == "S") {
						alert('<spring:message code="msg.common.success.save" />');

						$("#hiddenForm").attr("method", "post");
						$("#hiddenForm").attr("action", "<c:url value="/edi/product/NEDMPRO0021.do" />");
						$("#hiddenForm").submit();
					} else {
						alert('<spring:message code="msg.common.fail.request" />');
					}
				}
			});
		}

		// [200416  상품속성탭에 속성선택칸 추가] Start

		function checkEcAttrValRdp(prodAttrArr, ecAttrLen, cmpIdx1, cmpIdx2) {

			var sameCnt = 0;
			for(var idx=0; idx<ecAttrLen; idx++) {
				if(prodAttrArr[cmpIdx1 + idx] == prodAttrArr[cmpIdx2 +idx])sameCnt ++;
			}
			if(sameCnt == ecAttrLen)return false;
			return true;
		}

		function checkEcAttrValRdpOvl(prodAttrArr, ecAttrRowLen, ecAttrLen) {
			
			
			for(var cmpIdx1=0; cmpIdx1<ecAttrRowLen-1; cmpIdx1++) {
				for(var cmpIdx2=cmpIdx1+1; cmpIdx2<ecAttrRowLen; cmpIdx2++) {
					if(!checkEcAttrValRdp(prodAttrArr, ecAttrLen, cmpIdx1*ecAttrLen, cmpIdx2*ecAttrLen)) {
						return false;
					}
				}
			}
			return true;
		}
		
		
		function setEcAttribute() {
			setEcAttributeFrame();
			var prodAttTypFg 	= '<c:out value="${prodDetailInfo.prodAttTypFg}" />';
			
			if(!$(":radio[name='attrPiType']").is(':checked')) {
			  $(":radio[name='attrPiType']:radio[value='P']").attr("checked", true);

			  initAttrITypeRadioPiButton();
			  setEcAttributeFrame();		//속성유형, 속성정보마스터 세

			}
			else {
			  initAttrITypeRadioPiButton();
			  setEcAttributeProduct();

			  // 231120 NBMSHIP
			}
			setEcFashionAttDisplay();
		}

		function hideEcFashionAttr(isChangeOptnDirect) {
			const prodAttTypFg = <c:out value="${prodDetailInfo.prodAttTypFg}" />

			if (prodAttTypFg !== 1)return ;

			const ecAttrDisplay = document.getElementById("ecFashionAttrDisplay").dataset.attrIdList.split(',');
			let ecAttrDiv = document.querySelectorAll("[id^=ecAttr_]");

			ecAttrDiv.forEach(divElem=> {
				Array.from(divElem.childNodes).every(selElem => {

                    let isAttrId = true;
					if (!selElem || !selElem.id || selElem.id == "" || selElem.id.indexOf("_") == -1)isAttrId = false;
					if (isAttrId) {
					  if (selElem.style.display === "none" && isChangeOptnDirect !== true) {
						  return false;
					  }

					  let selElemId = selElem.id.split("_")[1];
					  let isDisplay = false;

					  ecAttrDisplay.every(id => {
				        if (selElemId === id) {
						  isDisplay = true;
                          return false;
						}
                        return true;
					  })

					  if (!isDisplay) {
					    selElem.style.display = "none";
					  }
					}
                    return true;
				})
			})

		}

        // EC패션속성 테이블에서
		function setEcFashionAttDisplay() {
			const stdCatCd = '<c:out value="${prodDetailInfo.stdCatCd}" />';
			const callUrl = '<c:url value="/edi/product/displayEcFashionAttr.json"/>';
			const options = {
				method : "POST",
				headers: {
					'Content-Type': 'application/json',
				},
				body: JSON.stringify({"stdCatCd" : stdCatCd})
			}
			fetch(callUrl, options)
					.then(res => res.json())
					.then(result => {
						let ecAttrDisplay = [];

						result.ecFashionAttrDisplay.forEach((ecAttrItem) => {
							if (ecAttrItem.displayFg === 'O') {
								ecAttrDisplay.push(ecAttrItem.attrId);
							}
						})

						document.getElementById("ecFashionAttrDisplay").dataset.attrIdList = ecAttrDisplay;

						hideEcFashionAttr();
					})
		}

	    // 221006_NBMSHIP
		// 기본정보탭에서 묶음 상품일 경우
		// 속성정보에서 EC 속성 묶음형 상품이 체크되고 
		// 단일형 상품은 체크 못하도록
		function initAttrITypeRadioPiButton() {
		  var prodAttTypFg 	= '<c:out value="${prodDetailInfo.prodAttTypFg}" />';
		  if (prodAttTypFg == "01") {
		    $(":radio[name='attrPiType']:radio[value='I']").attr("checked", true);
		    $(":radio[name='attrPiType']:radio[value='P']").attr("checked", false);
		    $(":radio[name='attrPiType']:radio[value='P']").attr("disabled", true);
	       }
		}

		
		
		function fnTableControll(){
			//var val = $(":radio[name='prodPrcMgrYn']:checked").val();

			//prodPrcMgrYnChk(val);
		}

		//상품속성(단품) 필수값 체크
		function itemCdValidation(type) {
			var rows = $("#itemSubTable tr");
		    var index = rows.length;
		    if (index > 1) {
		    	/* if ($("#rservStkQty"+(index-1)).val() == "") {
		    		alert("단품 정보를 정확하게 입력하세요");
		    		return;
		    	}

		    	//20180427 단품정보 등록 확인 추가
				if ($(":radio[name='prodPrcMgrYn']:checked").val() == "1") {
					if ($("#optnAmt" + (index-1)).val() == "") {
						alert("단품 가격 정보를 정확하게 입력하세요");
						return false;
					}

					if (Number($("#optnAmt"+(index-1)).val()) == 0) {
						alert("단품 가격을 0원 이상 입력하세요");
						return false;
					}
				}

				var flag = true;
				if ($("input:radio[name=optnDirectYn]:checked").val() == "N" ) {
			    	$("select[name='ecProductAttr']").each(function(i, selected) {
			    		if ($(selected).val() == "") {
				    		flag = false;
				    		return false; // each break
			    		}
			    	});
			    	if (!flag) {
			    		alert("단품 옵션속성을 정확하게 입력하세요");
			    		return false;
			    	}
				} else {
					$("input:text[name='ecProductAttr']").each(function(i) {
			    		if ($(this).val() == "") {
				    		flag = false;
				    		return false; // each break
			    		}
			    	});
			    	if (!flag) {
			    		alert("단품 옵션속성을 정확하게 입력하세요");
			    		return false;
			    	}
				} */

		    	//20180125 100개 이상 등록 가능하도록 수정
		    	if (index >= 1000) {
		    		alert("1000개 이상 등록할 수 없습니다.");
		    		return false;
		    	}
		    }
		    return true;
		}



		/* function addEcProductAttr(attrPiType, type) {

			$(":radio[name='attrPiType']:radio[value='" + attrPiType + "']").prop('checked', true);

			var stdCatCd ='<c:out value="${prodDetailInfo.stdCatCd}" />';

			if(stdCatCd == '') {
				$(":radio[name='attrPiType']").prop("checked", false);
				alert("EC 표준 카테고리를 선택 하세요.");
				return;
			}

			if(type == 'change') {
				// 상품 속성 초기화
				$("#singleProductAttrDiv").html("<br/><font color=blue style='font-size:11px;'>※ 상품 속성</font><br/>");
				//var resetItemSubTable = '<tr><th style="width:45px">단품코드</th><th style="width:320px">옵션설명</th><th style="width:45px">재고여부</th><th style="width:100px">재고수량</th><th id="optnAmtTH" style="width:100px">가격</th><th></th></tr>';
				var resetItemSubTable = '<tr><th style="width:45px">단품코드</th><th style="width:320px">옵션설명</th><th style="width:50px"></th></tr>';
				$("#itemSubTable").html(resetItemSubTable);

			} else {
				if(!itemCdValidation()){
					return;
				}
			}

	  		var rows = $("#itemSubTable tr");
	  	    var index = rows.length;
	  	    var itemCd = "00"+index;

	    	//20180125 100개 이상 등록 가능하도록 수정
	    	if(index > 9 && index < 100) {
	    		itemCd = "0"+index;
	    	} else if(index >= 100 && index < 1000) {
	    		itemCd = index;
	    	} else if(index >= 1000) {
	    		alert("1000개 이상 등록할 수 없습니다.");
	    		return;
	    	}

			var param = new Object();
			param.stdCatCd = stdCatCd;
			param.attrPiType = attrPiType;

			var targetUrl = '<c:url value="/edi/product/ecProductAttr.do"/>';

			$.ajax({
				type: 'POST',
				url: targetUrl,
				data: param,
				success: function(data) {
					var attrHtml;
					var list = data.list;
					//단일형 상품
					if(attrPiType == 'P') {
	  					$("#multiProductAttrDiv").css("display", "none");
	  					$("#singleProductAttrDiv").css("display", "block");

						//selectBox 세팅
						for(var i=0; i<list.length; i++) {
							if(list[i].NUM == 1) {
								var selectBox =  $('<select onchange="this.className=this.options[this.selectedIndex].className" class="default"></select>').attr("id","ecProductAttr_" + list[i].ATTR_ID + "_" + index);
					  	  		$("#singleProductAttrDiv").append(selectBox);
								var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
								$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).attr("name", "ecProductAttr");
								$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
							}
						}

						for(var i=0; i<list.length; i++) {
							var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '">'+list[i].ATTR_VAL_NM+'</option>';
							$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
						}

					}
					//옵션형 상품
					if(attrPiType == 'I') {
						if(list.length == 0) {
							addEcProductAttr('P', 'change');
							alert("해당 EC 표준카테고리(소/세분류)는 옵션형 상품 선택이 불가합니다. EC 표준카테고리를 재선택하거나, 상품구분을 단일형 상품으로 선택하세요.");
						} else {
							$("#singleProductAttrDiv").css("display", "none");
							$("#multiProductAttrDiv").css("display", "block");

					  		var rows = $("#itemSubTable tr");
					  	    var index = rows.length;
					  	    var itemCd = "00"+index;
							var newRow = "<tr id=row"+index+"><td style='text-align:center'>"+itemCd+"<input type='hidden' name='itemCd' id='itemCd"+index+"' value='"+itemCd+"'/></td>";
					  		newRow += "<td><div id='ecAttr"+index+"'></div></td>";
					  		//newRow += "<td><select id='stkMgrYn"+index+"' name='stkMgrYn' style='width:98%;'><option value='N'>N</option><option value='Y'>Y</option></select></td>";
					  		//newRow += "<td><input type='text' style='text-align:right; width:98%;' id='rservStkQty"+index+"' name='rservStkQty' onkeydown='onlyNumber(event)' /></td>";
					  		//newRow += "<td id='optnAmtTD"+index+"'><input type='text' style='text-align:right; width:98%;' id='optnAmt"+index+"' name='optnAmt' onkeydown='onlyNumber(event)' /></td>";
					  			if(index > 1){
					  				for(var i = 0; i < index; i++){
					  					$("#deleteNewItem"+i).attr("style", "display:none");
					  				}
					  			}
					  		newRow += "<td><a href='javascript:fnNewItemDelete("+index+")' id='deleteNewItem"+index+"' class='btn' ><span>삭제</span></a></td>";
					  		newRow += "</tr>";
					  		$("#itemSubTable").last().append(newRow);
					  		$("#itemRow").val(index);

							//selectBox 세팅
							for(var i=0; i<list.length; i++) {
								if(list[i].NUM == 1) {
									var selectBox =  $("<select class='required default'></select>").attr("id","ecProductAttr_" + list[i].ATTR_ID + "_" + index);
						  	  		$("#ecAttr"+index).append(selectBox);
						  	  		selectBox.attr("onchange", "this.className='required ' + this.options[this.selectedIndex].className");
									var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).attr("name", "ecProductAttr");
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
								}
							}

							for(var i=0; i<list.length; i++) {
								var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '">'+list[i].ATTR_VAL_NM+'</option>';
								$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
							}
					  		fnTableControll();
						}
					}
				}
			});
		} */

		function addEcProductAttr(attrPiType, type) {

			$(":radio[name='attrPiType']:radio[value='" + attrPiType + "']").prop('checked', true);

			var stdCatCd = '<c:out value="${prodDetailInfo.stdCatCd}" />';

			if (stdCatCd == '') {
				$(":radio[name='attrPiType']").prop("checked", false);
				alert("EC 표준 카테고리를 선택 하세요.");
				return;
			}

			if (type == "change") {
				// 상품 속성 초기화
				//$("#singleProductAttrDiv").html("<br/><font color=blue style='font-size:11px;'>※ 상품 속성</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='prodDirectYn' value='N' /> 선택입력 &nbsp;<input type='radio' name='prodDirectYn' value='Y' /> 직접입력<br/>");
				//var resetItemSubTable = '<tr><th style="width:48px">단품코드</th><th style="width:380px">옵션정보</th><th style="width:45px">재고여부</th><th style="width:80px">재고수량</th><th id="optnAmtTH" style="width:80px">가격</th><th style="width:46px"></th></tr>';
				var resetItemSubTable = '<tr><th style="width:48px">단품코드</th><th style="width:380px">옵션정보</th><th style="width:46px"></th></tr>';
				$("#itemSubTable").html(resetItemSubTable);
				//$(":radio[name='prodDirectYn']:radio[value='N']").prop('checked', true); // 선택입력 체크
			} else {
				if (!itemCdValidation()) {
					return;
				}
			}

			var rows = $("#itemSubTable tr");
			var index = rows.length;
			var itemCd = "00"+index;

			//20180125 100개 이상 등록 가능하도록 수정
			if (index > 9 && index < 100) {
				itemCd = "0"+index;
			} else if (index >= 100 && index < 1000) {
				itemCd = index;
			} else if (index >= 1000) {
				alert("1000개 이상 등록할 수 없습니다.");
				return;
			}

			var param = new Object();
			param.stdCatCd = stdCatCd;
			param.attrPiType = attrPiType;

			var targetUrl = '<c:url value="/edi/product/ecProductAttr.do"/>';

			$.ajax({
				type: 'POST',
				url: targetUrl,
				data: param,
				success: function(data) {
					var attrHtml;
					var list = data.list;
					//단일형 상품
					if (attrPiType == "P") {
						$("#multiProductAttrDiv").css("display", "none");
						$("#singleProductAttrDiv").css("display", "block");
						var inputText = "";
						//selectBox 세팅
						if (list.length > 0) {
							if (type == "change") {
								$("#singleProductAttrDiv").html("<br/><font color=blue style='font-size:11px;'>※ 상품 속성</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='prodDirectYn' value='N' /> 선택입력 &nbsp;<input type='radio' name='prodDirectYn' value='Y' /> 직접입력<br/>");
								$(":radio[name='prodDirectYn']:radio[value='N']").prop('checked', true); // 선택입력 체크
							}
							for (var i = 0; i < list.length; i++) {
								if (list[i].NUM == 1) {
									var selectBox =  $('<select onchange="this.className=this.options[this.selectedIndex].className;" class="default"></select>').attr("id","ecProductAttr_" + list[i].ATTR_ID + "_" + index);
									$("#singleProductAttrDiv").append(selectBox);
									var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).attr("name", "ecProductAttr");
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
									//속성 기타 - 직접입력 추가
									inputText += '<input type="hidden" name="ecProductAttrId" id="ecProductAttrID_' + list[i].ATTR_ID + "_" + index + '_etc" value="'+ list[i].ATTR_ID + '" disabled />';
									inputText += '<input type="text" style="display:none;" width="8" name="ecProductAttr" id="ecProductAttr_' + list[i].ATTR_ID + "_" + index + '_etc" value="" disabled />';
								}
							}
							$("#singleProductAttrDiv").append("<br/>");
							$("#singleProductAttrDiv").append(inputText);

							for (var i = 0; i < list.length; i++) {
								var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '">'+list[i].ATTR_VAL_NM+'</option>';
								$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
							}
						} else {
							$("#singleProductAttrDiv").html("");
						}

					}

					//옵션형 상품
					if (attrPiType == "I") {
						if (list.length == 0) {
							addEcProductAttr("P", "change");
							alert("해당 EC 표준카테고리(소/세분류)는 옵션형 상품 선택이 불가합니다. EC 표준카테고리를 재선택하거나, 상품구분을 단일형 상품으로 선택하세요.");
						} else {
							$("#singleProductAttrDiv").css("display", "none");
							$("#multiProductAttrDiv").css("display", "block");

							var newRow = "<tr id=row"+index+"><td style='text-align:center'>"+itemCd+"<input type='hidden' name='itemCd' id='itemCd"+index+"' value='"+itemCd+"'/></td>";
							newRow += "<td><div id='ecAttr_"+index+"'></div></td>";
							//newRow += "<td><select id='stkMgrYn"+index+"' name='stkMgrYn' style='width:98%;'><option value='N'>N</option><option value='Y'>Y</option></select></td>";
							//newRow += "<td><input type='text' style='text-align:right; width:98%;' id='rservStkQty"+index+"' name='rservStkQty' onkeydown='onlyNumber(event)' /></td>";
							//newRow += "<td id='optnAmtTD"+index+"'><input type='text' style='text-align:right; width:98%;' id='optnAmt"+index+"' name='optnAmt' onkeydown='onlyNumber(event)' /></td>";
							if (index > 1) {
								for (var i = 0; i < index; i++) {
									$("#deleteNewItem"+i).attr("style", "display:none");
								}
							}

							if (index == 1) {
								newRow += "<td><a href='javascript:fnNewItemDelete("+index+")' id='deleteNewItem"+index+"' class='btn' style='display:none;' ><span>삭제</span></a></td>";
							} else {
								newRow += "<td><a href='javascript:fnNewItemDelete("+index+")' id='deleteNewItem"+index+"' class='btn' ><span>삭제</span></a></td>";
							}

							newRow += "</tr>";
							$("#itemSubTable").last().append(newRow);
							$("#itemRow").val(index);
							var inputText = "";
							//selectBox 세팅
							for (var i = 0; i < list.length; i++) {
								if (list[i].NUM == 1) {
									var selectBox =  $("<select ></select>").attr("id","ecProductAttr_" + list[i].ATTR_ID + "_" + index); // required
									$("#ecAttr_"+index).append(selectBox);
									selectBox.attr("onchange", "this.className=this.options[this.selectedIndex].className;"); // required
									var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).attr("name", "ecProductAttr");
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
									//속성 기타 - 직접입력 추가
									inputText += '<input type="hidden" name="ecProductAttrId" id="ecProductAttrID_' + list[i].ATTR_ID + "_" + index + '_etc" value="'+ list[i].ATTR_ID + '" disabled />';
									inputText += '<input type="text" style="display:none;" width="6" name="ecProductAttr" id="ecProductAttr_' + list[i].ATTR_ID + "_" + index + '_etc" value="" disabled />';
								}
							}
							$("#ecAttr_"+index).append("<br/>");
							$("#ecAttr_"+index).append(inputText);

							for (var i = 0; i < list.length; i++) {
								var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '">'+list[i].ATTR_VAL_NM+'</option>';
								$("#ecProductAttr_"+list[i].ATTR_ID + "_" + index).append(optionText);
							}

							fnTableControll();
							$("#singleProductAttrDiv").find("select").attr("disabled", true);
							$("#singleProductAttrDiv").find("input:text").attr("disabled", true);
							$("#singleProductAttrDiv").find("input:hidden").attr("disabled", true);
						}

						if ($("input:radio[name=optnDirectYn]:checked").val() == "N" ) { // 선택입력
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", false);
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default");//"required",
							//$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").removeClass("required");
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", true);
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "none");
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", true);

							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").each(function(index, item){
								item.className = item.options[item.selectedIndex].className;
							});
                            hideEcFashionAttr();
						} else { // 직접입력
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", true);
							//$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").addClass("required");
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", false);
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "");
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", false);
                            hideEcFashionAttr(true);
						}

					}
				}
			});
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
		/* function prodPrcMgrYnChk(prodPrcMgrYn){
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
		} */

		/* function setEcAttributeFrame() {

			<c:forEach items="${itemListInTemp}" var="itemListInTemp" varStatus="itemStatus">
				<c:if test="${itemStatus.first}">
					<c:forEach items="${itemListInTemp.ecProductAttrList}" var="ecProductAttrList" varStatus="attrStatus">
						<c:if test="${attrStatus.first}">
							$(":radio[name='attrPiType']:radio[value='<c:out value="${ecProductAttrList.attrPiType}" />']").attr("checked", true);
							$("#prev-attrPiType").val('<c:out value="${ecProductAttrList.attrPiType}" />');
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
			if($(":radio[name='attrPiType']").is(':checked')) {

				var attrPiType = $(":radio[name='attrPiType']:checked").val();

				var stdCatCd =  '<c:out value="${prodDetailInfo.stdCatCd}" />';

				if(stdCatCd == '') {
					$(":radio[name='attrPiType']").prop("checked", false);
					alert("EC 표준 카테고리를 선택 하세요.");
					return;
				}


		  		var rows = $("#itemSubTable tr");
		  	    var index = rows.length;
		  	    var itemCd = "00"+index;

		    	//20180125 100개 이상 등록 가능하도록 수정
		    	if(index > 9 && index < 100) {
		    		itemCd = "0"+index;
		    	} else if(index >= 100 && index < 1000) {
		    		itemCd = index;
		    	} else if(index >= 1000) {
		    		alert("1000개 이상 등록할 수 없습니다.");
		    		return;
		    	}


				var param = new Object();
				param.stdCatCd = stdCatCd;
				param.attrPiType = attrPiType;

				var targetUrl = '<c:url value="/edi/product/ecProductAttr.do"/>';

				$.ajax({
					type: 'POST',
					url: targetUrl,
					async: false,
					data: param,
					success: function(data) {
						var attrHtml;
						var list = data.list;

						//단일형 상품
						if(attrPiType == 'P') {
							$("#singleProductAttrDiv").html("<br/><font color=blue style='font-size:11px;'>※ 상품 속성</font><br/>");
		  					$("#multiProductAttrDiv").css("display", "none");
		  					$("#singleProductAttrDiv").css("display", "block");

							//selectBox 세팅
							for(var i=0; i<list.length; i++) {
								if(list[i].NUM == 1) {
									var selectBox =  $('<select onchange="this.className=this.options[this.selectedIndex].className"></select>').attr("id","ecProductAttr_" + list[i].ATTR_ID + "_1");
						  	  		$("#singleProductAttrDiv").append(selectBox);
									var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
									$("#ecProductAttr_"+list[i].ATTR_ID + "_1").attr("name", "ecProductAttr");
									$("#ecProductAttr_"+list[i].ATTR_ID + "_1").append(optionText);
								}
							}

							for(var i=0; i<list.length; i++) {
								var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '" >'+list[i].ATTR_VAL_NM+'</option>';
								$("#ecProductAttr_"+list[i].ATTR_ID + "_1").append(optionText);
							}

						}
						//옵션형 상품
						if(attrPiType == 'I') {
							$("#singleProductAttrDiv").css("display", "none");
							$("#multiProductAttrDiv").css("display", "block");

							for(var j=1; j<rows.length+1; j++) {
								//selectBox 세팅
								for(var i=0; i<list.length; i++) {
									if(list[i].NUM == 1) {
										var selectBox =  $("<select class='required'></select>").attr("id","ecProductAttr_" + list[i].ATTR_ID + "_" + j);
							  	  		$("#ecAttr_" + j).append(selectBox);
							  	  		selectBox.attr("onchange", "this.className='required ' + this.options[this.selectedIndex].className");
										var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
										$("#ecProductAttr_"+list[i].ATTR_ID + "_" + j).attr("name", "ecProductAttr");
										$("#ecProductAttr_"+list[i].ATTR_ID + "_" + j).append(optionText);
									}
								}
							}

							for(var j=1; j<rows.length+1; j++) {
								for(var i=0; i<list.length; i++) {
									var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '">'+list[i].ATTR_VAL_NM+'</option>';
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + j).append(optionText);
								}
							}
					  		fnTableControll();
						}
					}
				});
			}

		} */

		function setEcAttributeFrame() {
			<c:set var="prodDirectN" value="checked" />
			<c:set var="prodDirectY" value="" />
			<c:set var="optnDirectN" value="true" />
			<c:set var="optnDirectY" value="false" />
			
			<c:forEach items="${itemListInTemp}" var="itemListInTemp" varStatus="itemStatus">
				<c:if test="${itemListInTemp.itemCode ne null}">
				<c:if test="${itemStatus.first}">
					<c:forEach items="${itemListInTemp.ecProductAttrList}" var="ecProductAttrList" varStatus="attrStatus">
						<c:if test="${attrStatus.first}">
							$(":radio[name='attrPiType']:radio[value='<c:out value="${ecProductAttrList.attrPiType}" />']").attr("checked", true);
							$("#prev-attrPiType").val("<c:out value='${ecProductAttrList.attrPiType}' />");
							$("#setEcAttrFlag").val("0");
							
							<c:if test="${ecProductAttrList.attrValId eq 'etc'}">
								<c:if test="${ecProductAttrList.attrPiType eq 'P'}">
									<c:set var="prodDirectN" value=""/>
									<c:set var="prodDirectY" value="checked"/>
								</c:if>
								<c:if test="${ecProductAttrList.attrPiType eq 'I'}">
									<c:set var="optnDirectN" value="false"/>
									<c:set var="optnDirectY" value="true"/>
								</c:if>
							</c:if>
						</c:if>
					</c:forEach>
				</c:if>
				</c:if>
			</c:forEach>

			if ($(":radio[name='attrPiType']").is(':checked')) {
				
				var attrPiType = $(":radio[name='attrPiType']:checked").val();
				var stdCatCd = '<c:out value="${prodDetailInfo.stdCatCd}" />';
				if (stdCatCd == '') {
					$(":radio[name='attrPiType']").prop("checked", false);
					alert("EC 표준 카테고리를 선택 하세요.");
					return;
				}

				var rows = $("#itemSubTable tr");
			    var index = rows.length;
			    var itemCd = "00"+index;

		    	//20180125 100개 이상 등록 가능하도록 수정
		    	if (index > 9 && index < 100) {
		    		itemCd = "0" + index;
		    	} else if (index >= 100 && index < 1000) {
		    		itemCd = index;
		    	} else if (index >= 1000) {
		    		alert("1000개 이상 등록할 수 없습니다.");
		    		return;
		    	}

				var param = new Object();
				param.stdCatCd = stdCatCd;
				param.attrPiType = attrPiType;

				var targetUrl = '<c:url value="/edi/product/ecProductAttr.do"/>';

				$.ajax({
					type: 'POST',
					url: targetUrl,
					async: false,
					data: param,
					success: function(data) {
						var attrHtml;
						var list = data.list;

						//단일형 상품
						if (attrPiType == "P") {
							$("#multiProductAttrDiv").css("display", "none");
							$("#singleProductAttrDiv").css("display", "block");

							var inputText = "";
							// selectBox 세팅
							if (list.length > 0) {

								$("#singleProductAttrDiv").html("<br/><font color=blue style='font-size:11px;'>※ 상품 속성</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='prodDirectYn' value='N' <c:out value='${prodDirectN}' />  /> 선택입력 &nbsp;<input type='radio' name='prodDirectYn' value='Y' <c:out value='${prodDirectY}' />  /> 직접입력<br/>");

								for (var i = 0; i < list.length; i++) {
									if (list[i].NUM == 1) {
										var selectBox =  $('<select onchange="this.className=this.options[this.selectedIndex].className;"></select>').attr("id","ecProductAttr_" + list[i].ATTR_ID + "_1");
										$("#singleProductAttrDiv").append(selectBox);
										var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
										$("#ecProductAttr_"+list[i].ATTR_ID + "_1").attr("name", "ecProductAttr");
										$("#ecProductAttr_"+list[i].ATTR_ID + "_1").append(optionText);
										// 속성 기타 - 직접입력 추가
										inputText += '<input type="hidden" name="ecProductAttrId" id="ecProductAttrID_' + list[i].ATTR_ID + '_1_etc" value="' + list[i].ATTR_ID + '" disabled />';
										inputText += '<input type="text" style="display:none;" width="8" name="ecProductAttr" id="ecProductAttr_' + list[i].ATTR_ID + '_1_etc" value="" disabled />';
									}
								}
								$("#singleProductAttrDiv").append("<br/>");
								$("#singleProductAttrDiv").append(inputText);

								for (var i = 0; i < list.length; i++) {
									var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '" >'+list[i].ATTR_VAL_NM+'</option>';
									$("#ecProductAttr_"+list[i].ATTR_ID + "_1").append(optionText);
								}
							} else {
								$("#singleProductAttrDiv").html("");
							}
						}

						if (attrPiType == "I") { // 옵션형 상품
							//console.log("저장된 상품 로딩 - 옵션값 출력");
							$("#singleProductAttrDiv").css("display", "none");
							$("#multiProductAttrDiv").css("display", "block");

							$("input:radio[name=optnDirectYn]:radio[value='N']").prop('checked', '<c:out value="${optnDirectN}" />'); // 선택입력
							$("input:radio[name=optnDirectYn]:radio[value='Y']").prop('checked', '<c:out value="${optnDirectY}" />'); // 직접입력

							// 221006_NBMSHIP
							// 기본정보탭에 묶음상품 클릭시
							// 속성정보탭에 EC속성이 옵션형 상품 속성으로 EC속성 불러온다.
							// 초기에 SELECT값 세팅시 필요한 값
							if (index == 1) {
							    initAttrITypeFrame();
							}
							
							for (var j = 1; j < rows.length + 1; j++) {
								// selectBox 세팅
								var inputText = "";
								for (var i = 0; i < list.length; i++) {
									if (list[i].NUM == 1) {
										//<input type='text' style='width:30%' name='optnDesc' id='optnDesc"+index+"' value=''/>
										var selectBox =  $('<select ></select>').attr("id","ecProductAttr_" + list[i].ATTR_ID + "_" + j); //  class='required'
										$("#ecAttr_" + j).append(selectBox);
										selectBox.attr("onchange", "this.className=this.options[this.selectedIndex].className;"); //required
										var optionText = '<option value="" class="default">'+list[i].ATTR_NM+'</option>';
										$("#ecProductAttr_"+list[i].ATTR_ID + "_" + j).attr("name", "ecProductAttr");
										$("#ecProductAttr_"+list[i].ATTR_ID + "_" + j).append(optionText);
										// 속성 - 직접입력 추가
										inputText += '<input type="hidden" name="ecProductAttrId" id="ecProductAttrID_' + list[i].ATTR_ID + "_" + j + '_etc" value="'+ list[i].ATTR_ID + '" disabled />';
										inputText += '<input type="text" style="display:none;" width="6" name="ecProductAttr" id="ecProductAttr_' + list[i].ATTR_ID + "_" + j + '_etc" value="" disabled />';
									}
								}
								$("#ecAttr_" + j).append("<br/>");
								$("#ecAttr_" + j).append(inputText);
							}

							for (var j = 1; j < rows.length+1; j++) {
								for (var i = 0; i < list.length; i++) {
									var optionText = '<option value="' + list[i].ATTR_ID + "_" + list[i].ATTR_VAL_ID + '">'+list[i].ATTR_VAL_NM+'</option>';
									$("#ecProductAttr_"+list[i].ATTR_ID + "_" + j).append(optionText);
								}
							}

							fnTableControll();

							$("#singleProductAttrDiv").find("select").attr("disabled", true);
							$("#singleProductAttrDiv").find("input:text").attr("disabled", true);
							$("#singleProductAttrDiv").find("input:hidden").attr("disabled", true);

							if ($("input:radio[name=optnDirectYn]:checked").val() == "N") { // 선택입력
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", false);
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default");//"required",
								//$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").removeClass("required");
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", true);
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "none");
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", true);

								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").each(function(index, item){
									item.className = item.options[item.selectedIndex].className;
								});
							} else { // 직접입력
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", true);
								//$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").addClass("required");
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", false);
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "");
								$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", false);
							}
						}
					}
				});
			}
		}

		//NBMSHIP 
		//상품속성탭에서 옵션형상품으로 기본값 세팅될 경우
		//초기 입력 및 버튼 레이아웃이 생성되지 않아
		//레이아웃 생성
		function initAttrITypeFrame() {
			var firstRow = "<tr id=row1><td style='text-align:center'>001<input type='hidden' name='itemCd' id='itemCd1' value='001'/></td>"
			           + "<td><div id='ecAttr_1'></div></td>"
                       + "<td><a href='javascript:fnNewItemDelete(1)' id='deleteNewItem1' class='btn' style='display:none;' ><span>삭제</span></a></td>"
                       + "</tr>";
			$("#itemSubTable").last().append(firstRow);
		    $("#itemRow").val(1);
		}
		
		
		/* function setEcAttributeProduct() {
			<c:forEach items="${itemListInTemp}" var="itemListInTemp" varStatus="itemStatus">
				<c:forEach items="${itemListInTemp.ecProductAttrList}" var="ecProductAttrList" varStatus="attrStatus">
					$("#ecProductAttr_<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${itemStatus.count}' />").val("<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${ecProductAttrList.attrValId}' />");
				</c:forEach>
			</c:forEach>
		} */

		function setEcAttributeProduct() {
			<c:forEach items="${itemListInTemp}" var="itemListInTemp" varStatus="itemStatus">
			<c:if test="${not empty itemListInTemp.itemCode}">
				<c:forEach items="${itemListInTemp.ecProductAttrList}" var="ecProductAttrList" varStatus="attrStatus">
					$("#ecProductAttr_<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${itemStatus.count}' />").val("<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${ecProductAttrList.attrValId}' />");
					<c:if test="${ecProductAttrList.attrValId eq 'etc'}">
					$("#ecProductAttr_<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${itemStatus.count}_etc' />").val("<c:out value='${ecProductAttrList.attrVal}' />");
					$("#ecProductAttr_<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${itemStatus.count}_etc' />").attr("disabled", false);
					$("#ecProductAttrID_<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${itemStatus.count}' />_etc").attr("disabled", false);
					$("#ecProductAttr_<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${itemStatus.count}' />_etc").show();
					$("#ecProductAttr_<c:out value='${ecProductAttrList.attrId}' />_<c:out value='${itemStatus.count}' />").attr("disabled", true);
					<c:if test="${ecProductAttrList.attrPiType eq 'P'}">
					$("#singleProductAttrDiv").find("select").attr("disabled", true);
					</c:if>
					</c:if>	
				</c:forEach>
			</c:if>
			</c:forEach>
		}

		//// 8 백스페이스 , 9 탭 , 37 왼쪽이동, 39 오른쪽이동, 46 delete
		function onlyNumber(event) {
			var key;
			if(event.which) { // ie9 firefox chrome opera safari
				key = event.which;
			} else if(window.event) {  // ie8 and old
				key = event.keyCode;
			}
			if(!( key==8 || key==9 || key==37 || key==39 || key==46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105) )) {
				alert("숫자만 입력해 주세요");
				if(event.preventDefault){
					event.preventDefault();
			    } else {
					event.returnValue = false;
			    }
			} else {
				event.returnValue = true;
			}
		}

		//신규상품추가 단품 정보 row 삭제'
		function fnNewItemDelete(idx){
			if(idx > 1){
				if (idx-1 > 1) {
					$("#deleteNewItem"+(idx-1)).attr("style", "display:");
				}
			}
			$('#row' + idx).remove();
			$("#itemRow").val(parseInt($("#itemRow").val())-1);
		}


		function fnItemDelete(idx, itemCd){
			if(!confirm("["+itemCd+"] 단품 정보를 삭제하시겠습니까?")){
				return;
			}

			var paramInfo = {};


			fnNewItemDelete(idx);

			/*
			paramInfo["itemCode"]	=	itemCd;
			paramInfo["newProductCode"]	=	"<c:out value='${newProdDetailInfo.pgmId}'/>";

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/delOnlineItemInfo.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msg == "SUCCESS") {
						fnNewItemDelete(idx);
					}else {
						//
						alert("<spring:message code='msg.common.fail.delete'/>")
					}


				}
			});
		*/
		/*  var param = "itemCode="+itemCd+"&newProductCode=<c:out value='${newProdDetailInfo.pgmId}' />";
			$.ajax({
				//url     : "<c:out value='${ctx}' />/edi/product/PEDMPRO000305.do",
				url		: '<c:url value="/edi/product/PEDMPRO000305.do"/>',
				type 	: "POST",
				data    : param,
				error   : function(xhr,status, error){
					console.log("xhr ==" + xhr);
					console.log("status ==" + status);
					console.log("error ==" + error);

					//fnNewItemDelete(idx);
				}
			});  */
		}

		function fnTableControll(){
			//var val = $(":radio[name='prodPrcMgrYn']:checked").val();

			//prodPrcMgrYnChk(val);
		}

		// [200416  상품속성탭에 속성선택칸 추가] End


		// 문자열의 바이트 길이 계산
		function getByteLength(s, b, i, c) {
			for(b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
			return b;
		}

		function optnEqCheck() {
			var optnRows = $("#itemRow").val();
			var objType = "select";
			var itemArr = new Array();
			if ($("input:radio[name=optnDirectYn]:checked").val() == "Y" ) {
				objType = "input:text";
			}

			for (var i = 1; i <= optnRows; i++) {
				var optArr = $("#multiProductAttrDiv").find("[id=ecAttr_"+i+"]").find(objType).toArray();
				var tmp = "";
				for (var j = 0; j < optArr.length; j++) {
					var tmpCell;
					if (optArr[j].value != "") {
						tmpCell = "O";
					} else {
						tmpCell = "X";
					}

					tmp += tmpCell + ":";
				}
				itemArr.push(tmp);
				console.log(tmp);
			}

			//console.log("itemArr.length :: " + itemArr.length);
			var isEq = true;
			for (var i = 1; i < itemArr.length; i++) {
				if (itemArr[0] != itemArr[i]) {
					//console.log(i + " != " + 0);
					isEq = false;
					break;
				}/*  else {
					console.log(i + " == " + 0);
				} */
			}
			return isEq;
		}

		function optnDuplCheck() {
			var optnRows = $("#itemRow").val();
			var objType = "select";
			var itemArr = new Array();
			if ($("input:radio[name=optnDirectYn]:checked").val() == "Y" ) {
				objType = "input:text";
			}

			for (var i = 1; i <= optnRows; i++) {
				var optArr = $("#multiProductAttrDiv").find("[id=ecAttr_"+i+"]").find(objType).toArray();
				var tmp = "";
				for (var j = 0; j < optArr.length; j++) {
					tmp += optArr[j].value + ":";
				}
				itemArr.push(tmp);
				//console.log(tmp);
			}

			var sorted_itemArr = itemArr.slice().sort();
			var results = [];
			for (var i = 0; i < sorted_itemArr.length - 1; i++) {
			    if (sorted_itemArr[i + 1] == sorted_itemArr[i]) {
			        results.push(sorted_itemArr[i]);
			    }
			}
			//console.log(results.length);
			return results.length == 0;
		}

		function attrLengthCheck() {
			var is200Over = false;
			var attrPiType = $(":radio[name='attrPiType']:checked").val();
			if (attrPiType == "P") {
				if ($("input:radio[name=prodDirectYn]:checked").val() == "Y") { // 직접입력
					var attrArr = $("#singleProductAttrDiv").find("input:text").toArray();
					for (var j = 0; j < attrArr.length; j++) {
						is200Over = getByteLength(attrArr[j].value) > 200;
						if (is200Over) {
							break;
						}
					}
				}

			} else {
				var optnRows = $("#itemRow").val();
				if ($("input:radio[name=optnDirectYn]:checked").val() == "Y") { // 직접입력
					var optnRows = $("#itemRow").val();
					for (var i = 1; i <= optnRows; i++) {
						var optArr = $("#multiProductAttrDiv").find("[id=ecAttr_"+i+"]").find("input:text").toArray();
						for (var j = 0; j < optArr.length; j++) {
							is200Over = getByteLength(optArr[j].value) > 200;
							if (is200Over) {
								break;
							}
						}
						if (is200Over) {
							break;
						}
					}
				}

			}

			return is200Over;
		}

		function exprRadioCheck() {
			if ($("input[name='exprProdYn']:checked").val() == "1") {
				$("#sellerRecomm").removeAttr("disabled");
			} else {
				$("#sellerRecomm").attr("disabled", "disabled");
			}
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
					<div id="prodTabs" class="tabs" style="padding-top:10px;">
						<ul>
							<li id="pro01" style="cursor: pointer;">기본정보</li>
							<li id="pro02" class="on">상품속성</li>
							<li id="pro03" style="cursor: pointer;">이미지</li>
							<li id="pro04" style="cursor: pointer;">영양성분</li>
							<li id="pro05" style="cursor: pointer;">ESG</li>
						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->

					<div class="bbs_search">
						<form id="hiddenForm" name="hiddenForm">
							<input type="hidden" 	name="pgmId" 				  id="pgmId" 				value="<c:out value="${prodDetailInfo.pgmId}" />"		/>
							<input type="hidden"	name="prodRange" 			id="prodRange" 		value="<c:out value="${prodDetailInfo.l3Cd}" />" 		/>
							<input type="hidden" 	name="entpCd" 				id="entpCd" 			value="<c:out value="${prodDetailInfo.entpCd}" />"		/>
							<input type="hidden"	name="vendorTypeCd"	  id="vendorTypeCd"	value="<c:out value='${epcLoginVO.vendorTypeCd}'/>"		/>
							<input type="hidden"	name="mode"					  id="mode"				  value="<c:out value='${param.mode}' />"					/>
							<input type="hidden"	name="cfmFg"				  id="cfmFg"				value="<c:out value='${param.cfmFg}' />"				/>
							<input type="hidden"  name="l1Cd"           id="l1Cd"         value="<c:out value='${prodDetailInfo.l1Cd }'/>" />
							<input type="hidden"  name="l3Cd"           id="l3Cd"         value="<c:out value='${prodDetailInfo.l3Cd }'/>" />
							<input type="hidden" 	name="itemRow" 				id="itemRow"			value="${fn:length(itemListInTemp)}" />							
						</form>

						<ul class="tit">
							<li class="tit">
								* 상품속성등록 : <spring:message code="msg.product.onOff.register.notice"/>
							</li>
							<%-- <li class="btn">
								<a href="#" class="btn" onclick="_eventSave();"><span><spring:message code="button.common.save" /></span></a>
							</li> --%>
						</ul>
					</div>
					<!-- 상단 신규상품등록 안내문구와 저장버튼 출력 부분  끝-------------------------------------------------->


					<!-- 상품변형속성 Start --------------------------------------------------------------------->
					<div id="divProdVarAtt" name="divProdVarAtt">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">상품속성지정</li>								
								<li class="btn">
									<c:if test="${param.mode ne 'view'}">
										<a href="#" class="btn" onclick="doAttAdd();"><span><spring:message code="button.common.product.add" /></span></a>
										<a href="#" class="btn" onclick="_eventSave();"><span><spring:message code="button.common.save" /></span></a>
									</c:if>
								</li>
							</ul>

							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:20%" />
									<col style="*" />
								</colgroup>

								<tr>
									<th><span class="star">*</span>상품속성그룹</th>
									<td>

										<!-- 변형속성 그룹 SelectBox Start -------------------------------------------------->
										<c:if test="${not empty classList}">
											<select id="tmplClassCd" name="tmplClassCd" onChange="doClassCdChange(this.value);">
												<option value="">-선택-</option>
												<c:forEach items="${classList}" var="list" varStatus="status" >
													<option value="<c:out value="${list.classCd}" />"><c:out value="${list.classNm}" /></option>
												</c:forEach>
											</select>
										</c:if>
										<!-- 변형속성 그룹 SelectBox End -------------------------------------------------->

									</td>
								</tr>

								<tr>
									<th>상품속성</th>
									<td>

										<!-- 변형속성 그룹 Templete Start -------------------------------------------------->
										<table id="tblAttTemplete" name="tblAttTemplete" border="1" class="bbs_grid3">
										</table>
										<!-- 변형속성 그룹 Templete End -------------------------------------------------->

									</td>
								</tr>
								<tr>
									<th>적용수</th>
									<td>
										<input type="text" id="applyCnt" name="applyCnt" style="width:50px;text-align: center;" />
										<a href="#" class="btn" onclick="doAutoApply();"><span><spring:message code="button.common.setall" /></span></a>
									</td>
								</tr>
							</table>
						</div>

						<!-- 등록 변형속성 그룹 List Start -------------------------------------------------->
						<div class="bbs_list">
							<c:if test="${5 eq prodDetailInfo.prodDivnCd}">
							<ul style="overflow:initial;height:44px;background-color:#96acbf" class="tit">
							</c:if>
							<c:if test="${5 ne prodDetailInfo.prodDivnCd}">
							<ul class="tit">
							</c:if>
								<li class="tit">속성정보<font color="red">(삭제하고자 하는 속성은 체크박스를 선택해주세요)</font>
									<c:if test="${5 eq prodDetailInfo.prodDivnCd}">
										<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <font color="red">
										※ 속성정보에 등록한 값은 아래 EC상품속성에 등록하는 값과 동일해야 합니다.</font>
									</c:if>
								</li>
							</ul>
						</div>

						<div class="bbs_list">

							<table id="tblHead" name="tblHead" border="0" class="bbs_grid3">
								<colgroup>
									<col style="width: 5%;" />
									<col style="width: 5%;" />
									<col style="width: 150px;" />
									<col style="*" />
									<col style="width: 100px;" />
									<col style="width: 40%;" />
								</colgroup>
								<tr>
									<th>삭제</th>
									<th>순번</th>
									<th>속성명</th>
									<th>속성</th>
									<th colspan="2">88코드</th>
								</tr>
							</table>

							<div id="divAtt" name="divAtt">

								<c:set var="preVariant" value="" />
								<c:forEach items="${varAtt}" var="att" varStatus="status">

									<c:if test="${status.first == true && att.variant != preVariant}">
										</table>
									</c:if>

									<c:if test="${att.variant != preVariant}">
										<table id="tblAtt" name="tblAtt" border="0" class="bbs_grid3">
												<colgroup>
													<col style="width: 5%;" />
													<col style="width: 5%;" />
													<col style="width: 150px;" />
													<col style="*" />
													<col style="width: 100px;" />
													<col style="width: 40%;" />
												</colgroup>
									</c:if>

									<tr>

										<c:if test="${att.variant != preVariant}">
										  <th id="rowDelTr" name="rowDelTr" rowspan="<c:out value="${att.rowSpan}" />">
												 <c:choose>
														<c:when test="${att.rowSeq == 1}">
															<input type="checkbox" id="rowDel" name="rowDel" value="1" disabled="disabled">
														</c:when>
														<c:otherwise>
															<input type="checkbox" id="rowDel" name="rowDel" value="1">
														</c:otherwise>
													</c:choose>
											</th>
										<%-- <c:out value="${epcLoginSession.adminId }" /> --%>
											<th id="rowSeqTr" name="rowSeqTr" rowspan="<c:out value="${att.rowSpan}" />"><span id="rowSeq" name="rowSeq"><c:out value="${att.rowSeq}" /></span></th>
										</c:if>

										<th>
											<c:out value="${att.attGrpNm}" />

											<input type="hidden" id="attGrpCd<c:out value="${status.count}" />" name="attGrpCd<c:out value="${status.count}" />" value="<c:out value="${att.attGrpCd}" />" />
											<input type="hidden" id="classCd<c:out value="${status.count}" />" name="classCd<c:out value="${status.count}" />" value="<c:out value="${att.classCd}" />" />
										</th>
										<td>

											<!-- 속성그룹, 클래스별 Option 설정 Start ------------------------------>
											<select id="attDetailCd<c:out value="${status.count}" />" name="attDetailCd<c:out value="${status.count}" />" onChange="doAttDetailCdChange(this);">
												<option value="">-선택-</option>

												<c:forEach items="${prodVarAttOpt}" var="allOpt" varStatus="status">
													<%-- <c:out value="${allOpt.attGrpCd}" />::<c:out value="${allOpt.classCd}" /><br> --%>
													<c:if test="${att.classCd == allOpt.classCd && att.attGrpCd == allOpt.attGrpCd}">
														<c:choose>
															<c:when test="${att.attDetailCd == allOpt.attDetailCd}">
																<option value="<c:out value="${allOpt.attDetailCd}" />" selected><c:out value="${allOpt.attDetailNm}" /></option>
															</c:when>
															<c:otherwise>
																<option value="<c:out value="${allOpt.attDetailCd}" />"><c:out value="${allOpt.attDetailNm}" /></option>
															</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>

											</select>
											<!-- 속성그룹, 클래스별 Option 설정 End ------------------------------>

										</td>

										<c:if test="${att.variant != preVariant}">
											<th rowspan="<c:out value="${att.rowSpan}" />">
										<c:choose>
											<c:when test="${empty epcLoginVO.adminId || epcLoginVO.adminId eq 'online'}"><!-- 업체 및 online-->
											<!--  	*88코드-->
												88코드
											</c:when>
											<c:otherwise>
												88코드
											</c:otherwise>
										</c:choose>
											</th>
											<td rowspan="<c:out value="${att.rowSpan}" />">
												<input type="text" id="sellCd" name="sellCd" value="<c:out value="${att.sellCd}" />" maxlength="13" />

												<!-- <input type="button" id="btnAttDel" name="btnAttDel" value="삭제" /> -->

												<input type="hidden" id="allValue" name="allValue" value="" />
												<input type="hidden" id="variant" name="variant" value="<c:out value="${att.variant}" />" />
												<input type="hidden" id="orgVariant" name="orgVariant" value="<c:out value="${att.variant}" />" />
											</td>
										</c:if>

									</tr>

									<c:set var="preVariant" value="${att.variant}" />

								</c:forEach>

								</table>	<!-- 마지막에 한번 더 닫아 줘야 짝이 맞음 -->

							</div>

						</div>
						<!-- 등록 변형속성 그룹 List End -------------------------------------------------->

					</div>
					<!-- 상품변형속성 End --------------------------------------------------------------------->


					<!-- 그룹분석속성 시작 --------------------------------------------------------------------->

					<div id="divGrpAtt">
						<ul name="productCertTemplateTitle"	id="productCertTemplateTitle" class="tit mt10">
							<div class="bbs_list">
								<ul class="tit">
									<li class="tit">세부분석 속성 <font color="red">(해당되지 않는 속성은 반드시 '해당없음' 으로 선택해주세요)</font></li>
									<li class="tit" id="productCertSelectBox"	name="productCertSelectBox" 	style="display:none">
										<html:codeTag objId="productCertSelectTitle" objName="productCertSelectTitle" width="150px;" comType="SELECT" formName="form" defName="선택"  />
									</li>
									<li class="btn">
										<c:if test="${param.mode ne 'view' }">
										    <a href="#" class="btn" onclick="_eventGrpSave();"><span><spring:message code="button.common.save" /></span></a>
										</c:if>
									</li>
								</ul>
							</div>
						</ul>

						<div class="bbs_list">
							<table id="tblGrpHead" name="tblGrpHead" border="0" class="bbs_grid3">
								<colgroup>
									<col style="width: 126px;" />
									<col style="*" />
								</colgroup>
								<tr>
									<th>속성명</th>
									<th>속성값</th>
								</tr>
							</table>
							<table id="tblGrpAtt" name="tblGrpAtt" border="0" class="bbs_grid3">
								<colgroup>
									<col style="width: 126px;" />
									<col style="*" />
								</colgroup>

								<c:forEach items="${grpAtt}" var="list" varStatus="status">
									<tr>
										<th>
											<span class="star">* </span>
											<c:out value="${list.attNm}" />
											<input type="hidden" id="attId<c:out value="${status.count}" />" name="attId<c:out value="${status.count}" />" value="<c:out value="${list.attId}" />" />
										</th>
										<td>

											<select id="attValId<c:out value="${status.count}" />" name="attValId<c:out value="${status.count}" />">
												<option value="">-선택-</option>

												<c:forEach items="${grpAttOpt}" var="optList" varStatus="optStatus">
													<c:if test="${list.attId == optList.attId}">
														<c:choose>
															<c:when test="${optList.attValId == optList.selAttValId}">
																<option value="<c:out value="${optList.attValId}" />" selected><c:out value="${optList.attValNm}" /></option>
															</c:when>
															<c:otherwise>
																<option value="<c:out value="${optList.attValId}" />"><c:out value="${optList.attValNm}" /></option>
															</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach>
											</select>
										</td>
									</tr>
								</c:forEach>

							<!-- 그룹분석손석이 EC 속성으로 밀리지 않게 하기위해서 놓는코드 -->
							<table class="bbs_grid3" cellpadding="0" cellspacing="0"
									border="0">
									<colgroup>
										<col style="width: 126px;" />
										<col style="*" />
									</colgroup>
									<tr>

									</tr>
							</table>
							<!-- -->

						</div>
					</div>
					<!-- 그룹분석속성 종료 --------------------------------------------------------------------->
					<br>

					
					<!-- [210205 EC속성 테스트 준비]S  -->
					<c:if test="${5 eq prodDetailInfo.prodDivnCd}">

					<!-- EC속성 시작 -->
					<div class="bbs_list">
						<ul style="overflow:initial;height:120px;background-color:#96acbf" class="tit">
								<li class="tit">
								<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								※ 해당 속성값은 상단 '속성정보'와 동일한 순서와 값으로 작성하셔야 합니다. (코드발번 후 수정불가) <br/>
								</font>
	                           
	                            <font color="red">
	                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            ※ 단품정보 - "직접입력" 선택 시 원하는 속성값만 입력할 수 있습니다. <br/>  
	                            </font>  
	                            EC상품 속성
	                            <font color="red">                   
	                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            (예) 컬러/사이즈 2개 속성 입력칸 생성 → 컬러만 입력 희망 시 "직접입력" 선택 후 컬러값만 입력하고 저장 <br/>
	                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	
	                            ※ 상품 속성은 단품코드별 최소 1개의 값을 선택 또는 등록해주셔야 하며, 중복값을 넣을 수 없습니다. <br/>
	                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            (예) 단품코드 001-블랙 / 002-레드 / 003-블랙 등록 시 → 001과 003 중복으로 등록 불가 <br/>
	                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	                            ※ 작성 후 꼭 중간 저장버튼을 눌러주시기 바랍니다.<br/>
	                            </font>
								</li>
								<li class="btn">
									<br><br><br><br><br>
									<a href="#" class="btn" onclick="_eventEcSave();"> <!--[200422 박일영] -->
										<span>저장</span>
									</a>
								</li>
						</ul>
						<table id="tblGrpHead" name="tblGrpHead" border="0" class="bbs_grid3">
							<colgroup>
								<col style="width: 126px;" />
								<col style="*" />
							</colgroup>
							<tr>
								<th>EC 속성명</th>
								<th>EC 속성값</th>
							</tr>
						</table>


						<!-- [200326 EDI상품 범주 및 속성 추가] START -->
						<table class="bbs_grid3" cellpadding="0" cellspacing="0"
								border="0">
								<colgroup>
									<col style="width: 126px;" />
									<col style="*" />
								</colgroup>
								<tr>
									<th><span class="star">*</span> 상품 속성</th>
									<td colspan="3">
									<font color=blue>※ 상품 구분</font><br/>
									<input type="radio" class="onOffField" name="attrPiType" value="P" />단일형 상품
									<input type="radio" class="onOffField" name="attrPiType" value="I" />옵션형 상품
									<input type="hidden" id="setEcAttrFlag" name="setEcAttrFlag" value="1"/>
									<input type="hidden" id="prev-attrPiType" name="prev-attrPiType" />
									<div id="singleProductAttrDiv" style="overflow:hidden;">
										<br/> <font color=blue style="font-size: 11px">※ EC 표준 카테고리를 선택해 주세요.</font><br/>
									</div>
									<div id="multiProductAttrDiv" style="overflow:hidden; display: none;">
									<br/><font color=blue style="font-size:11px;">※ 단품 정보</font><br/>
										<input type="radio" name="optnDirectYn" value="N" checked="checked" /> 선택입력
										<input type="radio" name="optnDirectYn" value="Y" /> 직접입력
										<div style="float:right; padding-right: 5px">
											<a href="javascript:addEcProductAttr('I', 'add'); fnTableControll();" id="addItem" class="btn"><span>+ 단품 추가</span></a>
										</div>
										<table id="itemSubTable" style="width: 700px">
											<tr>
												<th style="width:48px">단품코드</th>
												<th style="width:380px">옵션정보</th>
												<th style="width:46px"></th>
											</tr>
											<c:if test="${not empty itemListInTemp}">
											<c:forEach var="itemList" items="${itemListInTemp}" varStatus="index">
											<c:if test="${not empty itemList.itemCode}">
											<tr id="row<c:out value='${index.count}' />">
												<td style='text-align: center'><c:out value="${itemList.itemCode }" /><input type='hidden' name='itemCd' id="itemCd<c:out value='${index.count}' />" value="<c:out value='${itemList.itemCode }' />" />
												</td>
												<td>
													<div id="ecAttr_<c:out value='${index.count}' />">
													</div>
												</td>
												<td><a href='javascript:fnItemDelete("<c:out value='${index.count}' />", "<c:out value='${itemList.itemCode }' />")' id='deleteNewItem<c:out value="${index.count}" />' class='btn' <c:if test="${!index.last}">style="display:none"</c:if>><span>삭제</span> </a>
												</td>
											</tr>
											</c:if>
											</c:forEach>
											</c:if>
										</table>
										</div>
									</td>
								</tr>
							</table>
							
						</div>
						</c:if> 
				</div>
			</div>
		</div>


		<!-- footer 시작 ------------------------------------------------------------------------------------->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li><spring:message code="msg.product.onOff.default.footerHome"/></li>
						<li><spring:message code="msg.product.onOff.default.footerItem"/></li>
						<li><spring:message code="msg.product.onOff.default.footerctrlNewItem"/></li>
						<li class="last"><spring:message code="msg.product.onOff.default.footerRegNewItem"/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer 끝 -------------------------------------------------------------------------------------->
	</div>

	<input type="hidden" id="ecFashionAttrDisplay" value="" />
</body>
</html>
