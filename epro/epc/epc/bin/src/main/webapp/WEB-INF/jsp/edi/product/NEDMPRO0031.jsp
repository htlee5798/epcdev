<%--
- Author(s): SONG MIN KYO
- Created Date: 2015. 12. 30
- Version : 1.0
- Description : 신상품등록  [ 온라인전용, 소셜상품  등록페이지 ]

-- Modified by EDSK 2015.11.26
-- 차세대 MD 대응으로 인한 변경사항 수정
-- 온라인 전용 상품등록 페이지를 그대로 복사하여 온라인전용 상품 상세보기 페이지로 사용

-- Modified by MZ_CYH 2017.04.26
-- 'KC 인증마크' => '제품안전인증' 수정 및 필수값 적용
-- KC인증 마스터 코드값 개편
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- NEDMPRO0031 -->
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
</style>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<%@ include file="./javascript.jsp" %>
<script type="text/javascript">

	var aprvChk = 0;
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
		}

		var isEq = true;
		for (var i = 1; i < itemArr.length; i++) {
			if (itemArr[0] != itemArr[i]) {
				isEq = false;
				break;
			}
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
		}

		var sorted_itemArr = itemArr.slice().sort();
		var results = [];
		for (var i = 0; i < sorted_itemArr.length - 1; i++) {
			if (sorted_itemArr[i + 1] == sorted_itemArr[i]) {
				results.push(sorted_itemArr[i]);
			}
		}
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

	function optionCountCheck(){
		var optnDirectYn = $("input:radio[name=optnDirectYn]:checked").val();
		var optionLength;
		var count = 0;
		var itemCdLength = $("#multiProductAttrDiv").find("[name='itemCd']").length;
		for(var j =1; j < itemCdLength+1; j++){
			if(optnDirectYn == "N"){  // 선택입력
				optionLength = $("#multiProductAttrDiv").find("[id=ecAttr_"+j+"]").find("select").length;
				for (var k = 0; k < optionLength; k++) {
					var ecProductAttrName = $("#multiProductAttrDiv").find("[id=ecAttr_"+j+"]").find("select").get(k).id;
					var ecProductAttrId = $("#"+ecProductAttrName+" option:checked").val();
					if( ecProductAttrId != ""){
						count++;
					}
				}
			}else if(optnDirectYn == "Y"){  //직접입력
				optionLength = $("#multiProductAttrDiv").find("[id=ecAttr_"+j+"]").find("input[type=text]").length;
				for (var k = 0; k < optionLength; k++) {
					var ecProductAttrName  = $("#multiProductAttrDiv").find("[id=ecAttr_"+j+"]").find("input[type=text]").get(k).id;
					var ecProductAttrId = $("#"+ecProductAttrName).val();
					if( ecProductAttrId != ""){
						count++;
					}
				}
			}
			if(count > 5){
				return false;
			}else{
				count = 0;
			}
		}
		return true;
	}

	function exprRadioCheck() {
		if ($("input[name='exprProdYn']:checked").val() == "1") {
			$("#sellerRecomm").removeAttr("disabled");
		} else {
			$("#sellerRecomm").attr("disabled", "disabled");
		}
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

				if (stdCdMapping != null) {
					if (initEcInfo()) {
						doCategoryReset();
						$("#prev-l3Cd").val($("#l3Cd").val());
						if (stdCdMapping.LRG_STD_CAT_CD != null && stdCdMapping.LRG_STD_CAT_CD != '') {
							$("#prev-ecStandardLargeCategory").val(stdCdMapping.LRG_STD_CAT_CD);
							$("#ecStandardLargeCategory").val(stdCdMapping.LRG_STD_CAT_CD);
							$("#ecStandardLargeCategory").removeClass("default");
							selectEcStandardCategory("ecStandardMediumCategory", $("#ecStandardLargeCategory"));
							$("#ecStandardSmallCategory option").not("[value='']").remove();
							$("#ecStandardSubCategory option").not("[value='']").remove();
						}
						if (stdCdMapping.MID_STD_CAT_CD != null && stdCdMapping.MID_STD_CAT_CD != '') {
							$("#prev-ecStandardMediumCategory").val(stdCdMapping.MID_STD_CAT_CD);
							$("#ecStandardMediumCategory").val(stdCdMapping.MID_STD_CAT_CD);
							$("#ecStandardMediumCategory").removeClass("default");
							selectEcStandardCategory("ecStandardSmallCategory", $("#ecStandardMediumCategory"));
							$("#ecStandardSubCategory option").not("[value='']").remove();
						}
						if (stdCdMapping.SML_STD_CAT_CD != null && stdCdMapping.SML_STD_CAT_CD != '') {
							$("#prev-ecStandardSmallCategory").val(stdCdMapping.SML_STD_CAT_CD);
							$("#ecStandardSmallCategory").val(stdCdMapping.SML_STD_CAT_CD);
							$("#ecStandardSmallCategory").removeClass("default");
							selectEcStandardCategory("ecStandardSubCategory", $("#ecStandardSmallCategory"));
						}
						if (stdCdMapping.SUB_STD_CAT_CD != null && stdCdMapping.SUB_STD_CAT_CD != '') {
							$("#prev-ecStandardSubCategory").val(stdCdMapping.SUB_STD_CAT_CD);
							$("#ecStandardSubCategory").val(stdCdMapping.SUB_STD_CAT_CD);
							$("#ecStandardSubCategory").removeClass("default");
							addEcProductAttr('P', 'change');
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
		if (typeof category != "undefined" ) {
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

				for (var i = 0; i < list.length; i++) {
					$("select[name="+lowerCategory+"]").show();
					if (i == 0) {
						if (list[i].DEPTH == 1) {
							optionText += '<option value="" class="default">대분류</option>';
						} else if (list[i].DEPTH == 2) {
							optionText += '<option value="" class="default">중분류</option>';
						} else if (list[i].DEPTH == 3) {
							optionText += '<option value="" class="default">소분류</option>';
						} else if (list[i].DEPTH == 4) {
							optionText += '<option value="" class="default">세분류</option>';
						}
					}
					optionText += '<option value="'+list[i].CAT_CD+'">'+list[i].CAT_NM+'</option>';
				}

				$("select[name="+lowerCategory+"]").html(optionText);

				if (list.length == 0) {
					$("select[name=" + lowerCategory + "]").hide();
					if ($("#setEcAttrFlag").val() == '1') {
						addEcProductAttr('P', 'change');
					} else {
						$("#setEcAttrFlag").val('1');
					}
				}
			}
		});
	}

	function initEcInfo() {
		if (mySheet2.RowCount() > 0 || mySheet3.RowCount() > 0
			|| $(":radio[name='attrPiType']").is(':checked') || $("select[name='ecProductAttr']").length > 0) {
			if (!confirm("표준카테고리 변경시\nEC 전시카테고리&상품속성이 초기화됩니다.\n계속하시겠습니까?")) {
				return false;
			}

			//EC 전시카테고리 초기화
			mySheet2.RemoveAll();
			mySheet3.RemoveAll();

			// 상품 속성 초기화
			$(":radio[name='attrPiType']").removeAttr("checked");
			$("#singleProductAttrDiv").html("<br/> <font color=blue style='font-size: 11px'>※ EC 표준 카테고리를 선택해 주세요.</font><br/>");
			var resetItemSubTable = '<tr><th style="width:48px">단품코드</th><th style="width:380px">옵션정보</th><th style="width:45px">재고여부</th><th style="width:80px">재고수량</th><th id="optnAmtTH" style="width:80px">가격</th><th style="width:46px"></th></tr>';
			$("#itemSubTable").html(resetItemSubTable);
			$("#multiProductAttrDiv").css("display", "none");
			$("#singleProductAttrDiv").css("display", "block");
		}
		return true;
	}

	function setEcInfo() {
		setEcCategoryProduct(); //EC 표준카테고리
		setEcAttribute(); //EC 속성
	}

	function setEcCategoryProduct() {

		var list = new Array();
		<c:forEach items="${ecCategory}" var="ecCategory" varStatus="status">
			<c:if test="${status.first}">
				<c:if test="${ecCategory.LRG_STD_CAT_CD ne ''}">
					$("#prev-l3Cd").val(<c:out value='${newProdDetailInfo.l3Cd}'/>);
					$("#prev-ecStandardLargeCategory").val("${ecCategory.LRG_STD_CAT_CD}");
					$("#ecStandardLargeCategory").val("${ecCategory.LRG_STD_CAT_CD}");
					$("#ecStandardLargeCategory").removeClass("default");
					selectEcStandardCategory("ecStandardMediumCategory", $("#ecStandardLargeCategory"));
					$("#ecStandardSmallCategory option").not("[value='']").remove();
					$("#ecStandardSubCategory option").not("[value='']").remove();
				</c:if>
				<c:if test="${ecCategory.SUB_STD_CAT_CD eq null or ecCategory.SML_STD_CAT_CD eq null or ecCategory.MID_STD_CAT_CD eq null}">
					$("#setEcAttrFlag").val('0');
				</c:if>
				<c:if test="${ecCategory.MID_STD_CAT_CD ne null}">
					$("#prev-ecStandardMediumCategory").val("${ecCategory.MID_STD_CAT_CD}");
					$("#ecStandardMediumCategory").val("${ecCategory.MID_STD_CAT_CD}");
					$("#ecStandardMediumCategory").removeClass("default");
					selectEcStandardCategory("ecStandardSmallCategory", $("#ecStandardMediumCategory"));
					$("#ecStandardSubCategory option").not("[value='']").remove();
				</c:if>
				<c:if test="${ecCategory.SML_STD_CAT_CD ne null}">
					$("#prev-ecStandardSmallCategory").val("${ecCategory.SML_STD_CAT_CD}");
					$("#ecStandardSmallCategory").val("${ecCategory.SML_STD_CAT_CD}");
					$("#ecStandardSmallCategory").removeClass("default");
					selectEcStandardCategory("ecStandardSubCategory", $("#ecStandardSmallCategory"));
				</c:if>
				<c:if test="${ecCategory.SUB_STD_CAT_CD ne null}">
					$("#prev-ecStandardSubCategory").val("${ecCategory.SUB_STD_CAT_CD}");
					$("#ecStandardSubCategory").val("${ecCategory.SUB_STD_CAT_CD}");
					$("#ecStandardSubCategory").removeClass("default");
				</c:if>
			</c:if>
		</c:forEach>
	}

	function setEcAttribute() {
		setEcAttributeFrame(); //속성유형, 속성정보마스터 세팅
		if ($(":radio[name='attrPiType']").is(':checked')) {
			setEcAttributeProduct(); //속성정보 매핑
		} else {
			$(":radio[name='attrPiType']:radio[value='P']").attr("checked", true);
		}
	}

	function setEcAttributeFrame() {
		<c:set var="prodDirectN" value="checked" />
		<c:set var="prodDirectY" value="" />
		<c:set var="optnDirectN" value="true" />
		<c:set var="optnDirectY" value="false" />
		<c:forEach items="${itemListInTemp}" var="itemListInTemp" varStatus="itemStatus">
			<c:if test="${itemStatus.first}">
				<c:forEach items="${itemListInTemp.ecProductAttrList}" var="ecProductAttrList" varStatus="attrStatus">
					<c:if test="${attrStatus.first}">
		$(":radio[name='attrPiType']:radio[value='${ecProductAttrList.attrPiType}']").attr("checked", true);
		$("#prev-attrPiType").val('${ecProductAttrList.attrPiType}');
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
		</c:forEach>

		if ($(":radio[name='attrPiType']").is(':checked')) {

			var attrPiType = $(":radio[name='attrPiType']:checked").val();
			var stdCatCd = getEcStandardCategoryId();
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

							$("#singleProductAttrDiv").html("<br/><font color=blue style='font-size:11px;'>※ 상품 속성</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='prodDirectYn' value='N' ${prodDirectN} /> 선택입력 &nbsp <br/>");

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
						$("#singleProductAttrDiv").css("display", "none");
						$("#multiProductAttrDiv").css("display", "block");

						$("input:radio[name=optnDirectYn]:radio[value='N']").prop('checked', ${optnDirectN}); // 선택입력
						$("input:radio[name=optnDirectYn]:radio[value='Y']").prop('checked', ${optnDirectY}); // 직접입력

						for (var j = 1; j < rows.length + 1; j++) {
							// selectBox 세팅
							var inputText = "";
							for (var i = 0; i < list.length; i++) {
								if (list[i].NUM == 1) {
									var selectBox =  $('<select ></select>').attr("id","ecProductAttr_" + list[i].ATTR_ID + "_" + j);
									$("#ecAttr_" + j).append(selectBox);
									selectBox.attr("onchange", "this.className=this.options[this.selectedIndex].className;");
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
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default");
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", true);
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "none");
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", true);

							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").each(function(index, item){
								item.className = item.options[item.selectedIndex].className;
							});
						} else { // 직접입력
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", true);
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", false);
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "");
							$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", false);
						}
					}
				}
			});
		}
	}

	<%-- // 사용안함
	function setEcProductAttrEtc(ecAttrInfo) {
		if (ecAttrInfo.value.includes("etc")) {
			$("#"+ecAttrInfo.id+"_etc").show();
		} else {
			$("#"+ecAttrInfo.id+"_etc").hide();
			$("#"+ecAttrInfo.id+"_etc").val('');
		}
	} --%>

	function setEcAttributeProduct() {
		<c:forEach items="${itemListInTemp}" var="itemListInTemp" varStatus="itemStatus">
			<c:forEach items="${itemListInTemp.ecProductAttrList}" var="ecProductAttrList" varStatus="attrStatus">
				$("#ecProductAttr_${ecProductAttrList.attrId}_${itemStatus.count}").val("${ecProductAttrList.attrId}_${ecProductAttrList.attrValId}");
				<c:if test="${ecProductAttrList.attrValId eq 'etc'}">
				$("#ecProductAttr_${ecProductAttrList.attrId}_${itemStatus.count}_etc").val("${ecProductAttrList.attrVal}");
				$("#ecProductAttr_${ecProductAttrList.attrId}_${itemStatus.count}_etc").attr("disabled", false);
				$("#ecProductAttrID_${ecProductAttrList.attrId}_${itemStatus.count}_etc").attr("disabled", false);
				$("#ecProductAttr_${ecProductAttrList.attrId}_${itemStatus.count}_etc").show();
				$("#ecProductAttr_${ecProductAttrList.attrId}_${itemStatus.count}").attr("disabled", true);
				<c:if test="${ecProductAttrList.attrPiType eq 'P'}">
				$("#singleProductAttrDiv").find("select").attr("disabled", true);
				</c:if>
				</c:if>
			</c:forEach>
		</c:forEach>
	}

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

		//로딩시 체험형 정보
		exprRadioCheck();
		checkByte();

		// 단일형상품 - 선택입력/직접입력 변경시
		$(document).on("change", "input:radio[name=prodDirectYn]", function () {
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
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default");
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").val("");
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", true);
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "none");
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", true);
			} else { // 직접입력
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").val("");
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default");
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", true);
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", false);
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "");
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").val("");
				$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", false);
			}
		});

	});

	var vndId = "";

	$(function() {

		//-----상품 복사이후 복사 완료 메세지 띄운다.
		var message = "<c:out value='${param.message}'/>";
		if (message.replace(/\s/gi, '') != "") {
			alert(uploadErrorMsg[message]);
		}

		//-----상품등록이후 상품 기본정도 등록 완료 메세지 띄운다.
		var mode = "<c:out value='${param.mode}'/>";
		if (mode == "basic") {
			alert("신규상품 등록 :  기본정보가 입력되었습니다. 온라인 이미지 및 배송정책은 필히 등록해 주시기 바랍니다.");
		}

		if ("${newProdDetailInfo}" == '') {
			vndId = "${epcLoginVO.repVendorId}";

			$('#md_vali_sellCode').val('1111111111111'); // 기획/복함 상품이나 바코드 없는 상품

			$('#md_vali_sellCode').focus(function() {
				$('#md_vali_sellCode').val('');
			});

			if ("${epcLoginVO.vendorTypeCd}" == '06') {
				if ("${epcLoginVO.repVendorId}" != '') {
					checkBlackListVendor1 ("${epcLoginVO.repVendorId}");
				}
			} else {
				if ("${epcLoginVO.repVendorId}" != '') { // 협력업체 선택시 실행.
					checkBlackListVendor();
				}
			}
		} else {
			vndId = "${newProdDetailInfo.entpCd}";
		}

		//업체코드별 거래형태 조회(selectBox mapping)
		trdTypFgSel();

		$("select[name=entpCode]").change(function() {
			var errorNode = $(this).prev("div[name=error_msg]").length;
			if ( errorNode > 0 ) {
				$(this).prev().remove();
			}

			if ( $(this).val() != '' ) {
				checkBlackListVendor();
			}
		});

		// 필수 콤보박스 값 검증
		$("select.required").change(function() {
			validateSelectBox($(this));
		});

		// 필수 입력항목 검증
		$("input:text.required").blur(function() {
			validateFormTextField($(this));
		});

		// 해당 입력 항목이 값이 있는경우 검증
		$("input:text.requiredIf").blur(function() {
			if ($(this).val() != '') {
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

		<%--//88코드 관련 필드 검증
		//$("input[name=sellCode], input.subSellCode").blur(function() {
		//	if ($(this).val != '') {
		//		validateSellCode($(this));
		//	}
		//});--%>

		// 기본값 설정
		$("#teamGroupCode").val('00232');
		$("#protectTagTypeCode").removeClass("required");
		$("#protectTagTypeCode").parent().prev().find("span.star").hide();

		//----- 팀 변경시 이벤트
		$("#newProduct select[name=teamCd]").change(function() {
			//----- 대, 중, 소분류 초기화
			$("#l1Cd option").not("[value='']").remove();
			$("#l1Cd").addClass("default");
			$("#l2Cd option").not("[value='']").remove();
			$("#l2Cd").addClass("default");
			$("#l3Cd option").not("[value='']").remove();
			$("#l3Cd").addClass("default");
			doCategoryReset();

			_eventSelectL1List($(this).val().replace(/\s/gi, ''));
		});

		//----- 대분류 변경시 이벤트
		$("#newProduct select[name=l1Cd]").change(function() {
			var groupCode = $("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');

			//----- 중, 소분류 초기화
			$("#l2Cd option").not("[value='']").remove();
			$("#l2Cd").addClass("default");
			$("#l3Cd option").not("[value='']").remove();
			$("#l3Cd").addClass("default");

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
			var groupCode = $("#newProduct select[name=teamCd]").val().replace(/\s/gi, '');
			var l1Cd = $("#newProduct select[name=l1Cd]").val().replace(/\s/gi, '');

			//----- 소분류 초기화
			$("#l3Cd option").not("[value='']").remove();
			$("#l3Cd").addClass("default");
			doCategoryReset();

			_eventSelectL3List(groupCode, l1Cd, $(this).val().replace(/\s/gi, ''));
		});

		// 소분류 선택 변경 시
		$("select[id=l3Cd]").change(function() {
			$("#l3Cd").val($(this).val());
			selectEcStandardCategoryMapping();
			commerceChange($(this).val()); // 전상법 조회
			certChange($(this).val()); // KC인증마크 조회
		});

		// 전상법 select 변경시 액션
		$("select[name=productAddSelectTitle]").change(function() {
			if ( $(this).val() != '' ) {
				selectBoxProdTemplateDetailList($(this).val());
				selectNorProdTempSel($(this).val());
			} else {
				selectNorProdTempSel("");
			}
		});

		// EC 표준카테고리 select 변경시 액션
		$("#ecStandardLargeCategory").change(function() {
			if ( $(this).val() != '' ) {
				if (initEcInfo()) {
					selectEcStandardCategory("ecStandardMediumCategory", $(this));
					$("#ecStandardSmallCategory option").not("[value='']").remove();
					$("#ecStandardSubCategory option").not("[value='']").remove();
				} else {
					$("#ecStandardLargeCategory").val($("#prev-ecStandardLargeCategory").val());
				}
			} else {
				alert("카테고리를 다시 선택해 주세요.");
			}
		});

		$("#ecStandardMediumCategory").change(function() {
			if ( $(this).val() != '' ) {
				if (initEcInfo()) {
					selectEcStandardCategory("ecStandardSmallCategory", $(this));
					$("#ecStandardSubCategory option").not("[value='']").remove();
				} else {
					$("#ecStandardMediumCategory").val($("#prev-ecStandardMediumCategory").val());
				}
			} else {
				alert("카테고리를 다시 선택해 주세요.");
			}
		});

		$("#ecStandardSmallCategory").change(function() {
			if ( $(this).val() != '' ) {
				if (initEcInfo()) {
					selectEcStandardCategory("ecStandardSubCategory", $(this));
				} else {
					$("#ecStandardSmallCategory").val($("#prev-ecStandardSmallCategory").val());
				}
			} else {
				alert("카테고리를 다시 선택해 주세요.");
			}
		});

		$("#ecStandardSubCategory").change(function() {
			if ( $(this).val() != '' ) {
				if (initEcInfo()) {
					$("#prev-ecStandardSubCategory").val($("#ecStandardSubCategory").val());
					addEcProductAttr('P', 'change');
				} else {
					$("#ecStandardSubCategory").val($("#prev-ecStandardSubCategory").val());
				}
			} else {
				alert("카테고리를 다시 선택해 주세요.");
			}
		});

		// EC 상품속성 상품구분변경
		$(":radio[name='attrPiType']").change(function() {
			if ($("select[name='ecProductAttr']").length > 0) {
				if (!confirm("상품 구분 변경시 상품속성이 초기화됩니다.\n계속하시겠습니까?")) {
					$(":radio[name='attrPiType']:radio[value='" + $("#prev-attrPiType").val() + "']").prop('checked', true)
					return;
				}
			}
			$("#prev-attrPiType").val($(':radio[name=attrPiType]:checked').val());
			addEcProductAttr($(this).val(), 'change');
		});

		// 전상법템플릿 select 변경시 액션
		$("select[name=productAddSelectTemp]").change(function() {
			if ( $(this).val() != '' ) {
				var param = new Object();
				var targetUrl = '<c:url value="/edi/product/selectNorProdTempVal.do"/>';
				param.norProdSeq = $(this).val();

				$.ajax({
				type: 'POST',
					url: targetUrl,
					data: param,
					success: function(data) {
						var list = data.resultList;

						if (list.length == 0) {
							alert("템플릿이 존재하지 않습니다.");
							return;
						}

						for (var i = 0; i < list.length; i++) {
							$("tr[name=titleProdAdd]").each(function(index) {
								var infoColCd = $("input[name=prodAddDetailCd_"+index+"]").val();

								if (infoColCd == list[i].INFO_COL_CD) {
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
			if ( $(this).val() == 'KC001' ) { //KC001 : 해당사항없음
				selectBoxCertTemplateDetailList2($(this).val());
			} else if ( $(this).val() != '' ) {
				selectCertTemplateDtlList($(this).val(), $(this).val());
			}
		});

		// KC 인증마크 상세 select 변경시 액션
		$("select[name=productCertSelectDtlTitle]").change(function() {
			if ( $(this).val() != '' ) {
				selectBoxCertTemplateDetailList2($("#newProduct select[name=productCertSelectTitle]").val(), $(this).val());
			}
		});

		//모든 input에 대해서 특수문자 경고표시하기
		$("input").keyup(function() {

			if ($(this).attr("name") == "optnLoadContent") {
				return;
			}

			var value = $(this).val();
			var arr_char = new Array();

			arr_char.push("\"");
			arr_char.push("<");
			arr_char.push(">");
			arr_char.push(";");

			for (var i = 0 ; i < arr_char.length ; i++) {
				if (value.indexOf(arr_char[i]) != -1) {
					alert("[<, >, /, ;] 특수문자는 사용하실 수 없습니다.");
					value = value.substr(0, value.indexOf(arr_char[i]));
					$(this).val(value);
				}
			}
		});

		//----- 계절구분 년도 현재년도로 default
		var nowYear = "<c:out value='${nowYear}'/>";
		$("select[name='sesnYearCd']").val(nowYear);

		_eventSelectSesnList($("select[name='sesnYearCd']").val().replace(/\s/gi, ''));

		//-----계절년도 체인지 이벤트
		$("select[name='sesnYearCd']").change(function() {
			_eventSelectSesnList($(this).val().replace(/\s/gi, ''));
		});

		initIBSheetGrid(); //그리드 초기화
		initIBSheetGrid2(); //그리드 초기화
		initIBSheetGrid3(); //그리드 초기화

		//ec카테고리 세팅
		<c:forEach items="${itemListInTemp}" var="itemListInTemp" varStatus="itemStatus">
			<c:if test="${itemStatus.first}">
				<c:forEach items="${itemListInTemp.ecProductAttrList}" var="ecProductAttrList" varStatus="attrStatus">
					<c:if test="${attrStatus.first}">
						$("#setEcAttrFlag").val('0');
					</c:if>
				</c:forEach>
			</c:if>
		</c:forEach>

		selectEcStandardCategory("ecStandardLargeCategory");
		if ("${ecCategory}" != '') {
			setEcInfo();
		}

		/*********************************** 여기서 부턴 상품등록이후 상품 상세정보 설정에 사용됩니다.*************************************************/

		//----- 상품의 상세정보 SeleceBox, RadioButton ValueSetting.[커스텀태그에서는 시큐어 코딩이 불가능 하여 스크립트에서 처리]
		if ('<c:out value="${newProdDetailInfo.pgmId}" />' != "") {
			//-----SeleceBox, RadioButton ValueSetting..
			_eventSetnewProdDetailInfoDetailValue();

			setTimeout(function(){
				selectCategoryList();
			},1000);
			setTimeout(function() {
				selectEcLotteOnCategoryList();
			},1000);
			setTimeout(function() {
				selectEcMartCategoryList();
			},1000);

			//-----전상법
			setTimeout(function() {
				selectProdTemplateUpdateViewList("<c:out value='${newProdDetailInfo.pgmId}'/>","<c:out value='${newProdDetailInfo.l3Cd}'/>","<c:out value='${newProdDetailInfo.infoGrpCd}'/>","2");
			},1000);

			//-----KC인증마크
			setTimeout(function() {
				selectProdCertTemplateUpdateViewList("<c:out value='${newProdDetailInfo.pgmId}'/>","<c:out value='${newProdDetailInfo.l3Cd}'/>","<c:out value='${newProdDetailInfo.infoGrpCd2}'/>",'2');
			},1000);
		}
		/*****************************************************************************************************************************/

		//-----탭 클릭 이벤트
		$("#prodTabs li").click(function() {
			var id = this.id;
			var pgmId = $("input[name='pgmId']").val();

			if (id == "pro01") { //기본정보 탭

			} else if (id == "pro02") { //이미지 등록 탭
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
		$(":radio[name='onlineProdTypeCd']").change(function() {
			onlineProdTypeCdChk($(this).val());
		});

		//-- 단품정보 제어
		$(":radio[name='prodPrcMgrYn']").change(function() {
			prodPrcMgrYnChk($(this).val());
		});

	});

	/** ********************************************************
	 * ibsheet 초기화
	 ******************************************************** */
	function initIBSheetGrid() {
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
			{Header:"",				Type:"CheckBox",	SaveName:"SELECTED",			Align:"Center",  Width:25,    Sort:false}
		  , {Header:"전시카테고리",		Type:"Text",		SaveName:"FULL_CATEGORY_NM",	Align:"Left",    Width:470,   Edit:0}
		  , {Header:"전시카테고리아이디",	Type:"Text",		SaveName:"CATEGORY_ID",			Align:"Left",    Width:1,   Edit:0, Hidden:true }
		  , {Header:"카테고리 상태값",	Type:"Text",		SaveName:"DISP_YN",				Align:"Center",  Width:145,   Edit:0}
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	}

	function initIBSheetGrid2() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "640px", "95px");
		mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet2.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"", 			Type:"CheckBox",	SaveName:"SELECTED",	Align:"Center",  Width:25,   Sort:false}
		  , {Header:"롯데ON 전시카테고리",Type:"Text",		SaveName:"DISP_CAT_NM",	Align:"Left",    Width:470,  Edit:0}
		  , {Header:"전시카테고리아이디",	Type:"Text",		SaveName:"DISP_CAT_CD",	Align:"Left",    Width:1,    Edit:0, Hidden:true }
		  , {Header:"카테고리 상태값",	Type:"Text",		SaveName:"DISP_YN",		Align:"Center",  Width:145,  Edit:0}
		];

		IBS_InitSheet(mySheet2, ibdata);

		mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet2.SetHeaderRowHeight(Ibs.HeaderHeight);
	}

	function initIBSheetGrid3() {
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet3"), "mySheet3", "640px", "95px");
		mySheet3.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet3.SetDataAutoTrim(false);

		var ibdata = {};
		// SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"", 					Type:"CheckBox",	SaveName:"SELECTED",		Align:"Center",  Width:25,  Sort:false}
		  , {Header:"EC 롯데마트몰 전시카테고리",	Type:"Text", 		SaveName:"DISP_CAT_NM", 	Align:"Left",    Width:470, Edit:0}
		  , {Header:"전시카테고리아이디",			Type:"Text", 		SaveName:"DISP_CAT_CD", 	Align:"Left",    Width:1,   Edit:0, Hidden:true }
		  , {Header:"카테고리 상태값",			Type:"Text", 		SaveName:"DISP_YN", 		Align:"Center",  Width:145, Edit:0}
		];

		IBS_InitSheet(mySheet3, ibdata);

		mySheet3.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet3.SetHeaderRowHeight(Ibs.HeaderHeight);
	}

	/* 상품등록 이후 상세정보 SelectBox, RadioButton Value Settings..... */
	function _eventSetnewProdDetailInfoDetailValue() {
		var teamCd = "<c:out value='${newProdDetailInfo.teamCd}'/>"; // 팀코드
		var l1Cd = "<c:out value='${newProdDetailInfo.l1Cd}'/>"; // 대분류
		var l2Cd = "<c:out value='${newProdDetailInfo.l2Cd}'/>"; // 중분류
		var l3Cd = "<c:out value='${newProdDetailInfo.l3Cd}'/>"; // 소분류
		var taxatDivnCd = "<c:out value='${newProdDetailInfo.taxatDivnCd}'/>"; // 면과세구분
		var dpUnitCd = "<c:out value='${newProdDetailInfo.dpUnitCd}'/>"; // 표시단위
		var onlineRepProdCd = "<c:out value='${newProdDetailInfo.onlineRepProdCd}'/>"; // 온라인 대표상품코드
		var prcIssueDivnCd = "<c:out value='${newProdDetailInfo.prcIssueDivnCd}'/>"; // 가격발급구분코드
		var qtyWegtDivnCd = "<c:out value='${newProdDetailInfo.qtyWegtDivnCd}'/>"; // 수량중량구분
		var homeCd = "<c:out value='${newProdDetailInfo.homeCd}'/>"; // 원산지
		var ecoYn = "<c:out value='${newProdDetailInfo.ecoYn}'/>"; // 친환경인증여부
		var ecoNm = "<c:out value='${newProdDetailInfo.ecoNm}'/>"; // 친환경인증분류명
		var dlvGa = "<c:out value='${newProdDetailInfo.dlvGa}'/>"; // 차등배송비여부
		var insCo = "<c:out value='${newProdDetailInfo.insCo}'/>"; // 별도설치비유무
		var sesnYearCd = "<c:out value='${newProdDetailInfo.sesnYearCd}'/>"; // 계절년도
		var sesnDivnCd = "<c:out value='${newProdDetailInfo.sesnDivnCd}'/>"; // 계절구분
		var norProdSaleCurr = "<c:out value='${newProdDetailInfo.norProdSaleCurr}'/>"; // 정산매가단위
		var onlineProdTypeCd = "<c:out value='${newProdDetailInfo.onlineProdTypeCd}'/>"; // 상품유형
		var hopeDeliPsbtDd = "<c:out value='${newProdDetailInfo.hopeDeliPsbtDd}'/>"; // 희망배송가능일
		var sizeUnit = "<c:out value='${newProdDetailInfo.sizeUnit}'/>"; // 상품사이즈 단위
		var prodPrcMgrYn = "<c:out value='${newProdDetailInfo.prodPrcMgrYn}'/>"; // 옵션상품가격 관리여부
		var psbtStartDy = "<c:out value='${newProdDetailInfo.psbtStartDy}'/>"; // 예약주문가능일(시작)
		var psbtEndDy = "<c:out value='${newProdDetailInfo.psbtEndDy}'/>"; // 예약주문가능일(종료)
		var adlYn = "<c:out value='${newProdDetailInfo.adlYn}'/>"; // 성인상품여부

		$("#psbtStartDyVal").val(psbtStartDy.substring(0,4)+"-"+psbtStartDy.substring(4,6)+"-"+psbtStartDy.substring(6,8));
		$("#psbtEndDyVal").val(psbtEndDy.substring(0,4)+"-"+psbtEndDy.substring(4,6)+"-"+psbtEndDy.substring(6,8));
		$("#psbtStartTime").val(psbtStartDy.substring(8,10));
		$("#psbtEndTime").val(psbtEndDy.substring(8,10));

		//-----SelectBox Settings....
		//----- 팀코드 selectBox에 셋팅해주고  팀의 대분류 조회

		var select = $("select");
		select.removeClass("default");

		$("#newProduct select[name='teamCd']").val(teamCd);
		_eventSelectL1List(teamCd);

		//----- 대분류 selectBox에 셋팅해주고 대분류의 중분류 조회, 전상법, KC인증 조회
		$("#newProduct select[name='l1Cd']").val(l1Cd);
		_eventSelectL2List(teamCd, l1Cd);

		//----- 중분류 selectBox 셋팅해주고 중분류의 소분류 조회
		$("#newProduct select[name='l2Cd']").val(l2Cd);
		_eventSelectL3List(teamCd, l1Cd, l2Cd);

		$("#newProduct select[name='l3Cd']").val(l3Cd);
		commerceChange(l3Cd); // 전상법 조회
		certChange(l3Cd); // KC인증마크 조회

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
		$(":radio[name='adlYn']:radio[value='" + adlYn + "']").attr("checked", true);

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
		if ( categoryValue == "40" ) {
			if ( $("input[name=productDay]").val() == '' || $("input[name=productDay]").val().length == 0 ) {
				showErrorMessage($("input[name=productDay]"));
			} else {
				deleteErrorMessageIfExist($("input[name=productDay]"));
			}
		} else {
			deleteErrorMessageIfExist($("input[name=productDay]"));
		}

		var originalProductDay = $("input[name=productDay]").val();
		var dbDatelength = $("input[name=productDay]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if ( dbDatelength != '' || dbDatelength != null ) {
			$("input[name=productDay]").val(dbDatelength);
		}

		var originalPsbtStartDy = $("input[name=psbtStartDyVal]").val();
		var dbDatelength1 = $("input[name=psbtStartDyVal]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if ( dbDatelength1 != '' || dbDatelength1 != null ) {
			$("input[name=psbtStartDyVal]").val(dbDatelength1);
		}

		var originalPsbtEndDy = $("input[name=psbtEndDyVal]").val();
		var dbDatelength2 = $("input[name=psbtEndDyVal]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if ( dbDatelength2 != '' || dbDatelength2 != null ) {
			$("input[name=psbtEndDyVal]").val(dbDatelength2);
		}

		var originalPickIdctDy = $("input[name=pickIdctDy]").val();
		var dbDatelength3 = $("input[name=pickIdctDy]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
		if ( dbDatelength3 != '' || dbDatelength3 != null ) {
			$("input[name=pickIdctDy]").val(dbDatelength3);
		}

		var errorLength = $("div[name=error_msg]").length;

		if (!validationResult || errorLength > 0) {
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

		if ( displayTotalQuantity == '') {
			$("input[name=displayTotalQuantity]").val(0);
		}

		if ( displayBaseQuantity == '') {
			$("input[name=displayBaseQuantity]").val(0);
		}

		//var wec = document.Wec;
		//$("input[name=productDescription]").val(CrossEditor.GetValue());
	}

	//상품등록 폼 전송
	function submitProductInfo() {
		$("div#content_wrap").block({ message: null ,  showOverlay: false });
		if(aprvChk != 0){
			alert("저장 진행 중 입니다.");
			return;
		}
		var index = $("#itemRow").val();

		if ( validateNewProductInfo() ) { //필드 값 검증.

			//유효성 체크 통과
			setupFormFieldDefaultValue();

			if ($("#entpCode").val() == "") {
				alert('업체선택은 필수입니다.');
				$("#entpCode").focus();
				return;
			}

			//체험형 Y 일시 추천평 입력 체크
			if ($(":radio[name='exprProdYn']:checked").val() == "1" && $("#sellerRecomm").val().trim() == "") {
				alert('판매자 추천평을 작성해주세요.');
				$("#sellerRecomm").focus();
				return;
			}

			if (!calByteProd (newProduct.productName, 80, '상품명', false) ) { return; } // 상품명 byte체크

			if (!prodAddValidationCheck ()) { return; }

			if (!prodCertValidationCheck ()) { return; }

			/////////////////카테고리 validation//////////////
			if(mySheet.RowCount() == 0) {
				alert("마트 전시카테고리를 추가하세요.");
				return;
			}

			if (mySheet2.RowCount() == 0) {
				alert("EC 롯데ON 전시 카테고리를 추가하세요.");
				return;
			}

			var TYcnt = 0;
			var LMcnt = 0;
			var tmpDispCatCd = "";
			for (var i = 1; i < mySheet3.RowCount() + 1; i++) {
				tmpDispCatCd = mySheet3.GetCellValue(i, "DISP_CAT_CD");
				if (tmpDispCatCd.indexOf("TY") > -1) {
					TYcnt++;
				}
				if (tmpDispCatCd.indexOf("LM") > -1) {
					LMcnt++;
				}
			}

			// 토이&문구팀으로 토이 구분 / 문구, 브랜드유아용품은 마트 카테고리로 분류
			var isToy = $("#newProduct select[name=teamCd]").val() == "3000000000012004" && $("#newProduct select[name=l1Cd]").val() != "054";

			if (isToy) {
				if (TYcnt == 0) {
					alert("토이저러스 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 토이저러스몰 전시카테고리 TY코드를 1개 이상 선택해주세요.");
					return;
				}
			} else {
				if (LMcnt == 0 || TYcnt >= 1) {
					alert("롯데마트 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 롯데마트몰 전시카테고리 LM코드를 1개 이상 선택해주세요.");
					return;
				}
			}

			if (mySheet3.RowCount() == 0) {
				if (isToy) {
					alert("토이저러스 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 토이저러스몰 전시카테고리 TY코드를 1개 이상 선택해주세요.");
				} else {
					alert("롯데마트 상품 등록 시에는 EC롯데마트몰 전시카테고리에서 롯데마트몰 전시카테고리 LM코드를 1개 이상 선택해주세요.");
				}
				return;
			}

			<%-- if ($("input:radio[name=attrPiType]:checked").val() == "P"  && $("input:radio[name=prodDirectYn]:checked").val() == "Y" ) {  // 단일형상품 && 직접입력
				var attrArr = $("#singleProductAttrDiv").find("input:text").toArray();
				for (var j = 0; j < attrArr.length; j++) {
					if (attrArr[j].value == null || attrArr[j].value == "") {
						alert("상품 속성 직접입력하기에서 입력되지 않은 속성이 존재합니다.");
						return;
					}
				}
			} --%>

			if (!optnEqCheck()) {
				alert("단품코드 001과 동일한 종류의 옵션을 사용해주세요.");
				return;
			}

			if (!optnDuplCheck()) { // 중복된 옵션값 체크
				alert("옵션을 선택(입력)하지 않았거나. 중복된 옵션 값이 존재합니다. 단품코드별 옵션을 다시 확인해주세요.");
				return;
			}

			if (attrLengthCheck()) { // 200Byte 넘는 속성/옵션값 체크
				alert("옵션명은 최대 200byte 까지만 입력가능합니다.");
				return;
			}
			var attrPiType = $(":radio[name='attrPiType']:checked").val();
			if(attrPiType == "I"){
				if(!optionCountCheck()){
					alert("상품속성은 최대 5개까지만 입력 가능합니다.");
					return;
				}
			}
			/////////////////골라담기, 예약상품 validation//////////////
			var onlineProdTypeCdVal = $(':radio[name="onlineProdTypeCd"]:checked').val();
			if (onlineProdTypeCdVal == "04") { //골라담기
				if ($.trim($("#setQty").val()) == "" || $.trim($("#optnLoadContent").val()) == "") {
					alert("세트수량 항목을 필히 입력해주세요.");
					return;
				}
			} else if (onlineProdTypeCdVal == "07") { //예약상품

				if ($("#psbtStartDyVal").val() == "" || $("#psbtEndDyVal").val() == "") {
					alert("예약 주문 가능일을 입력해주세요.");
					return;
				}

				if ($("#pickIdctDy").val() == "") {
					alert("예약상품 출고지시일을 입력해주세요.");
					return;
				}

				if ($("#psbtStartDyVal").val()+$("#psbtStartTime").val() > $("#psbtEndDyVal").val()+$("#psbtEndTime").val()) {
					alert("예약 주문 가능시작일이 종료일보다 큽니다.");
					return;
				}

				if ($("#psbtEndDyVal").val() > $("#pickIdctDy").val()) {
					alert("예약상품 출고지시일은 예약 주문 가능일 보다 커야합니다.");
				}

				$("#psbtStartDy").val($("#psbtStartDyVal").val()+$("#psbtStartTime").val());
				$("#psbtEndDy").val($("#psbtEndDyVal").val()+$("#psbtEndTime").val());

			} else if (onlineProdTypeCdVal == "09") { //구매대행상품
				if ($("#deliDueDate").val() == "") {
					$("input[name='deliDueDate']").val("주문 후 5~10일 정도 소요");
				}
			}

			// 단품정보 등록 확인 추가
			if (index > 0 && $(':radio[name="attrPiType"]:checked').val() == "I") {
				for (var i = 1; i <= index; i++) {
					if ($("#rservStkQty"+i).val() == "") {
						alert("단품정보 " + i + "행 단품 정보를 정확하게 입력하세요");
						return;
					}

					if ($("#rservStkQty"+i).val().length >= 7) {
						alert("단품정보 " + i + "행 재고수량 7자릿수 이상은 입력할 수 없습니다.");
						return;
					}

					if ($(":radio[name='prodPrcMgrYn']:checked").val() == "1") {
						if ($("#optnAmt"+i).val() == "") {
							alert("단품정보 " + i + "행 단품 가격 정보를 정확하게 입력하세요");
							return;
						}

						if (Number($("#optnAmt"+i).val()) == 0) {
							alert("단품정보 " + i + "행 단품 가격을 0원 이상 입력하세요");
							return;
						}

						if ($("#optnAmt"+i).val().length >= 10) {
							alert("단품정보 " + i + "행 단품 가격 10자릿수 이상은 입력할 수 없습니다.");
							return;
						}
					}

				}
			}

			//20180904 - 상품키워드 입력 기능 추가
			if ($.trim($("#searchKywrd1").val()) == "") {
				alert("상품키워드 1행 검색어는 필수 입력 항목입니다.");
				return;
			}

			if (reCount($("#searchKywrd1").val()) > 39) {
				alert("상품키워드 1행 검색어는 39바이트를 초과할 수 없습니다.");
				return;
			}

			var kId = Array();
			$("#keywordSubTable tr td:nth-child(2) input").each(function(tIndex) {
				kId[tIndex] = $(this).attr("id");
			});

			if (kId.length > 0) {

				if (kId.length < 3 || kId.length > 10) {
					alert("상품키워드 3개 이상 10개 이하 입력 가능합니다.");
					return;
				}

				for (var i = 0; i < kId.length; i++) {
					if ($.trim($("#"+ kId[i]).val()) == "") {
						alert("상품키워드 " + (i+1) + "행 검색어를 정확하게 입력하세요.");
						return;
					}

					if (reCount($.trim($("#"+ kId[i]).val())) > 39) {
						alert("상품키워드 " + (i+1) + "행 검색어는 39바이트를 초과할 수 없습니다.");
						return;
					}

					//20181016 특수문자 유효성 추가
					if ($.trim($("#"+ kId[i]).val()).indexOf(',') > -1 || $.trim($("#"+ kId[i]).val()).indexOf(';') > -1 || $.trim($("#"+ kId[i]).val()).indexOf('|') > -1) {
						alert("상품키워드 " + (i+1) + "행 ,(커머) ;(세미콜론) |(버티컬 바) 특수문자는 사용하실 수 없습니다.");
						return;
					}

					var rplVal = $("#"+ kId[i]).val().replace(/,/gi,";");
					$("#"+ kId[i]).val(rplVal);
					//20181016 특수문자 유효성 추가
				}
			}
			//20180904 - 상품키워드 입력 기능 추가

			var tmpCertInfoCd = $("#newProduct select[name=productCertSelectTitle]").val();
			if (tmpCertInfoCd == "KC004" || tmpCertInfoCd == "KC011") {
				var certChk = false;
				var frm = document.certForm;
				$("#certInfoCode").val($("#prodCertDetailNm_0").val());
				var formQueryString = $('*', frm).fieldSerialize();

				$.ajax({
					type: "POST",
					url: '<c:url value="/product/certInfoCheck.do"/>',
					async : false,
					data: formQueryString,
					success: function(data) {
						try {
							//console.log(data);
							if (data.returnCode == "0000") {
								certChk = true;
							} else {
								if (data.returnCode == "9999") {
									alert("안전인증번호가 유효하지 않습니다. 유효한 번호를 입력해주세요.");
								} else if (data.returnCode == "8888") {
									alert("안전인증번호 검증 통신이 실패하였습니다. 관리자에게 문의해주세요.");
								} else {
									alert("예외상황이 발생하였습니다. 관리자에게 문의해주세요.");
								}
							}

						} catch (e) { 
							console.log(e.msg);
						}
					},
					error: function(e) {
						alert(e.msg);
					}
				});

				if (!certChk) { return; }
			}

			$("#taxatDivCode").attr("disabled",false);

			var chkVal = "";

			for (var i = 1; i < mySheet.RowCount() + 1; i++) {
				chkVal += ","+mySheet.GetCellValue(i, "CATEGORY_ID");
			}
			chkVal = chkVal.substring(1, chkVal.length);
			$("#onlineDisplayCategoryCode").val(chkVal);

			chkVal = "";
			if (mySheet2.RowCount() > 0) {
				for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
					chkVal += "," + mySheet2.GetCellValue(i, "DISP_CAT_CD");
				}
				chkVal = chkVal.substring(1, chkVal.length);
			}
			if (mySheet3.RowCount() > 0) {
				for (var i = 1; i < mySheet3.RowCount() + 1; i++) {
					chkVal += "," + mySheet3.GetCellValue(i, "DISP_CAT_CD");
				}
				chkVal = chkVal.substring(0, chkVal.length);
			}
			$("#dispCatCd").val(chkVal);

			var stdCatCd = getEcStandardCategoryId();

			if (stdCatCd == '') {
				alert("EC 표준 카테고리를 선택 하세요.");
				return;
			}

			$("#stdCatCd").val(stdCatCd);

			loadingMaskFixPos();

			// 옵션형이 아닌 경우에 prodPrcMgrYn 를 0 으로 전달해야 온라인상품가등록관리에서 온오프 확정 후 온라인 확정시 오류나는것 방지
			if (!(index > 0 && $(':radio[name="attrPiType"]:checked').val() == "I")) {
				$(':radio[name="prodPrcMgrYn"]').val('0');
			}

			var actUrl = "<c:url value='/edi/product/PEDMPRO0002.do'/>";

			if ('<c:out value="${newProdDetailInfo.pgmId}" />' != "") {
				actUrl = "<c:url value='/edi/product/PEDMPRO000302.do'/>";
				aprvChk = 1;
			}


			$("#newProduct").attr("action", actUrl);
			$("#newProduct").attr("method", "post").submit();
		}

		setTimeout(function() {
			$("div#content_wrap").unblock();
			hideLoadingMask();
		}, 500);
	}

	// 카테고리 입력 정보 삭제
	function doDdeleteCategory() {
		var form = document.newProduct;
		form.asCategoryId.value = "";
		form.asCategoryNm.value = "";
	}

	// 전자상거래 데이터 셋팅
	function commerceChange(val) {
		//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
		setupLcdDivnCodeProdAdd(val, '2');
	}

	// KC인증마크 데이터 셋팅
	function certChange(val) {
		//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
		setupLcdDivnCodeCertAdd(val, '2');
	}

	// ImageSplitter Popup에서 데이터가 전해진다.
	function addSplitImage(activeSquareMimeValue) {
		var wec = document.Wec;
		var bodyTag = wec.BodyValue;
	}

	// 팝업으로 ImageSplitter를 띄운다.
	function doImageSplitterView() {
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
			if (!prodAddValidationCheck()) { return; }

			//-----KC 인증마크 validation check
			if (!prodCertValidationCheck()) { return; }

			//단품정보 등록 확인 추가
			if (index > 0 && $(':radio[name="attrPiType"]:checked').val() == "I") {

				for (var i = 1; i <= index; i++) {
					if ($("#rservStkQty"+i).val() == "") {
						alert("단품정보 " + i + "행 단품 정보를 정확하게 입력하세요");
						return;
					}

					if ("${newProdDetailInfo.onoffDivnCd}" == "1" && "${newProdDetailInfo.prodDivnCd}" == "5") {
						if ($("#rservStkQty"+i).val() == "") {
							$("#rservStkQty"+i).val("0");
						}

						if ($(":radio[name='prodPrcMgrYn']:checked").val() == "1") {
							if ($("#optnAmt"+i).val() == "") {
								$("#optnAmt"+i).val("0");
							}
						}
					} else {
						if ($("#rservStkQty"+i).val() == "") {
							alert("단품 정보를 정확하게 입력하세요");
							return;
						}

						if ($("#rservStkQty"+i).val().length >= 7) {
							alert("단품정보 " + i + "행 재고수량 7자릿수 이상은 입력할 수 없습니다.");
							return;
						}

						if ($(":radio[name='prodPrcMgrYn']:checked").val() == "1") {
							if ($("#optnAmt"+i).val() == "") {
								alert("단품 가격 정보를 정확하게 입력하세요");
								return;
							}

							if (Number($("#optnAmt"+i).val()) == 0) {
								alert("단품 가격을 0원 이상 입력하세요");
								return;
							}

							if ($("#optnAmt"+i).val().length >= 10) {
								alert("단품정보 " + i + "행 단품 가격 10자릿수 이상은 입력할 수 없습니다.");
								return;
							}
						}
					}
				}

			}

			//20180904 - 상품키워드 입력 기능 추가
			if ($.trim($("#searchKywrd1").val()) == "") {
				alert("상품키워드 1행 검색어는 필수 입력 항목입니다.");
				return;
			}

			if (reCount($("#searchKywrd1").val()) > 39) {
				alert("상품키워드 1행 검색어는 39바이트를 초과할 수 없습니다.");
				return;
			}

			var kId = Array();
			$("#keywordSubTable tr td:nth-child(2) input").each(function(tIndex) {
				kId[tIndex] = $(this).attr("id");
			});

			if (kId.length > 0) {
				for (var i = 0; i < kId.length; i++) {
					if ($.trim($("#"+ kId[i]).val()) == "") {
						alert("상품키워드 " + (i+1) + "행 검색어를 정확하게 입력하세요.");
						return;
					}

					if (reCount($.trim($("#"+ kId[i]).val())) > 39) {
						alert("상품키워드 " + (i+1) + "행 검색어는 39바이트를 초과할 수 없습니다.");
						return;
					}

					//20181016 특수문자 유효성 추가
					if ($.trim($("#"+ kId[i]).val()).indexOf(',') > -1 || $.trim($("#"+ kId[i]).val()).indexOf(';') > -1 || $.trim($("#"+ kId[i]).val()).indexOf('|') > -1) {
						alert("상품키워드 " + (i+1) + "행 ,(커머) ;(세미콜론) |(버티컬 바) 특수문자는 사용하실 수 없습니다.");
						return;
					}

					var rplVal = $("#"+ kId[i]).val().replace(/,/gi,";");
					$("#"+ kId[i]).val(rplVal);
					//20181016 특수문자 유효성 추가
				}
			}
			//20180904 - 상품키워드 입력 기능 추가
			$("#taxatDivCode").attr("disabled",false);

			var chkVal = "";

			for (var i=1; i<mySheet.RowCount()+1; i++) {
				chkVal += ","+mySheet.GetCellValue(i,"CATEGORY_ID");
			}
			chkVal = chkVal.substring(1,chkVal.length);
			$("#onlineDisplayCategoryCode").val(chkVal);

			if (mySheet2.RowCount() > 0) {
				chkVal = "";
				for (var i = 1; i < mySheet2.RowCount() + 1; i++) {
					chkVal += "," + mySheet2.GetCellValue(i, "DISP_CAT_CD");
				}
				chkVal = chkVal.substring(1,chkVal.length);
				$("#dispCatCd").val(chkVal);
			}

			if (mySheet3.RowCount() > 0) {
				for (var i = 1; i < mySheet3.RowCount() + 1; i++) {
					chkVal += "," + mySheet3.GetCellValue(i,"DISP_CAT_CD");
				}
				chkVal = chkVal.substring(0,chkVal.length);
				$("#dispCatCd").val(chkVal);
			}

			var stdCatCd = getEcStandardCategoryId();
			if (stdCatCd == '') {
				alert("EC 표준 카테고리를 선택 하세요.");
				return;
			}
			$("#stdCatCd").val(stdCatCd);

			loadingMaskFixPos();
			//유효성 체크 통과
			setupFormFieldDefaultValue();

			<%--//document.newProduct.action = "<c:url value='/edi/product/PEDMPRO0333.do'/>";--%>
			document.newProduct.action = "<c:url value='/edi/product/NEDMPRO0333.do'/>";
			document.newProduct.submit();
		}
		setTimeout(function() {
			$("div#content_wrap").unblock();
			hideLoadingMask();
		}, 500);
	}

	/* 전시카테고리 추가*/
	function addCategory() {
		if ($("#l3Cd").val() == "") {
			alert("분류코드를 선택 하세요.");
			$("#l3Cd").focus();
			return;
		}

		var rowCount = mySheet.RowCount();

		if (rowCount < 3) {
			var targetUrl = '${ctx}/edi/product/onlineDisplayCategory.do?closeFlag=1&catCd='+$("#l3Cd").val(); // 01:상품
			Common.centerPopupWindow(targetUrl, 'epcCategoryPopup', {width : 460, height : 455});
		} else {
			alert("최대 3개 까지 지정 가능합니다.");
		}
	}

	/* EC 표준 카테고리 추가*/
	function addEcStandardCategory(mallCd, isMart) {
		var stdCatCd = getEcStandardCategoryId();

		if (stdCatCd == '') {
			$(":radio[name='attrPiType']").prop("checked", false);
			alert("EC 표준 카테고리를 선택 하세요.");
			return;
		}

		var rowCount = mySheet2.RowCount();

		var targetUrl = '${ctx}/edi/product/ecDisplayCategoryPop.do?stdCatCd='+stdCatCd+'&mallCd='+mallCd+'&isMart='+isMart;
		Common.centerPopupWindow(targetUrl, 'epcCategoryPopup', {width : 460, height : 455});
	}

	/* EC 표준 카테고리 확인(SelectBox)*/
	function getEcStandardCategoryId() {

		var stdCatCd = '';

		if ($("#ecStandardLargeCategory").val()) {
			stdCatCd = $("#ecStandardLargeCategory").val()
		}
		if ($("#ecStandardMediumCategory").val()) {
			stdCatCd = $("#ecStandardMediumCategory").val()
		}
		if ($("#ecStandardSmallCategory").val()) {
			stdCatCd = $("#ecStandardSmallCategory").val()
		}
		if ($("#ecStandardSubCategory").val()) {
			stdCatCd = $("#ecStandardSubCategory").val()
		}

		if (($("#ecStandardLargeCategory option").size() > 0 && !$("#ecStandardLargeCategory").val())
		|| ($("#ecStandardMediumCategory option").size() > 0 && !$("#ecStandardMediumCategory").val())
		|| ($("#ecStandardSmallCategory option").size() > 0 && !$("#ecStandardSmallCategory").val())
		|| ($("#ecStandardSubCategory option").size() > 0 && !$("#ecStandardSubCategory").val())) {
			stdCatCd = '';
		}

		return stdCatCd;
	}

	// 카테고리 검색창으로 부터 받은 카테고리 정보 입력
	function setCategoryInto(asCategoryId, asCategoryNm, displayFlag, fullCategoryNm) { // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
		var rowCount = mySheet.RowCount();

		if (rowCount < 3) {
			for (var i=1; i<mySheet.RowCount()+1; i++) {
				if (mySheet.GetCellValue(i,"CATEGORY_ID") == asCategoryId) {
					alert("이미 추가된 카테고리 입니다.");
					return;
				}
			}
		} else {
			alert("최대 3개 까지 지정 가능합니다.");
			return;
		}

		var rowIdx = mySheet.DataInsert(0);

		if (displayFlag == "Y") {
			displayFlag = "전시";
		} else {
			displayFlag = "미전시";
		}

		mySheet.SetCellValue(rowIdx, "SELECTED", 0);
		mySheet.SetCellValue(rowIdx, "FULL_CATEGORY_NM", fullCategoryNm);
		mySheet.SetCellValue(rowIdx, "CATEGORY_ID", asCategoryId);
		mySheet.SetCellValue(rowIdx, "DISP_YN", displayFlag);

		selectL4ListForOnline(asCategoryId);
	}

	function setEcDisplayCategory(dispCatCd, dispCatNm, dispYn, isMart) {
		var mySheet;
		if (isMart == "true") {
			mySheet = mySheet3;
		} else {
			mySheet = mySheet2;
		}
		var rowCount = mySheet.RowCount();

		for (var i = 1; i < mySheet.RowCount() + 1; i++) {
			if (mySheet.GetCellValue(i,"DISP_CAT_CD") == dispCatCd) {
				alert("이미 추가된 카테고리 입니다.");
				return;
			}
		}

		var rowIdx = mySheet.DataInsert(0);

		if (dispYn == "Y") {
			dispYn = "전시";
		} else {
			dispYn = "미전시";
		}

		mySheet.SetCellValue(rowIdx, "SELECTED", 0);
		mySheet.SetCellValue(rowIdx, "DISP_CAT_NM", dispCatNm);
		mySheet.SetCellValue(rowIdx, "DISP_CAT_CD", dispCatCd);
		mySheet.SetCellValue(rowIdx, "DISP_YN", dispYn);
	}

	/* EC전시카테고리 삭제*/
	function delEcDisplayCategory(isMart) {

		var mySheet;
		if (isMart == "true") {
			mySheet = mySheet3;
		} else {
			mySheet = mySheet2;
		}
		var chkRow = "";

		for (var i = 1; i < mySheet.RowCount() + 1; i++) {
			if (mySheet.GetCellValue(i,"SELECTED") == 1) {
				chkRow += "|" + i;
			}
		}

		chkRow = chkRow.substring(1,chkRow.length);
		mySheet.RowDelete(chkRow);
	}

	/* 전시카테고리 삭제*/
	function delCategory() {
		var chkRow = "";

		for (var i = 1; i < mySheet.RowCount() + 1; i++) {
			if (mySheet.GetCellValue(i,"SELECTED") == 1) {
				chkRow += "|"+i;
			}
		}

		chkRow = chkRow.substring(1,chkRow.length);
		mySheet.RowDelete(chkRow);
	}

	function addEcProductAttr(attrPiType, type) {

		$(":radio[name='attrPiType']:radio[value='" + attrPiType + "']").prop('checked', true);

		var stdCatCd = getEcStandardCategoryId();

		if (stdCatCd == '') {
			$(":radio[name='attrPiType']").prop("checked", false);
			alert("EC 표준 카테고리를 선택 하세요.");
			return;
		}

		if (type == "change") {
			// 상품 속성 초기화
			var resetItemSubTable = '<tr><th style="width:48px">단품코드</th><th style="width:380px">옵션정보</th><th style="width:45px">재고여부</th><th style="width:80px">재고수량</th><th id="optnAmtTH" style="width:80px">가격</th><th style="width:46px"></th></tr>';
			$("#itemSubTable").html(resetItemSubTable);
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
							$("#singleProductAttrDiv").html("<br/><font color=blue style='font-size:11px;'>※ 상품 속성</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='prodDirectYn' value='N' /> 선택입력 &nbsp <br/>");
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
						newRow += "<td><select id='stkMgrYn"+index+"' name='stkMgrYn' style='width:98%;'><option value='N'>N</option><option value='Y'>Y</option></select></td>";
						newRow += "<td><input type='text' style='text-align:right; width:98%;' id='rservStkQty"+index+"' name='rservStkQty' onkeydown='onlyNumber(event)' /></td>";
						newRow += "<td id='optnAmtTD"+index+"'><input type='text' style='text-align:right; width:98%;' id='optnAmt"+index+"' name='optnAmt' onkeydown='onlyNumber(event)' /></td>";
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
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").addClass("default");
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", true);
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "none");
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", true);

						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").each(function(index, item){
							item.className = item.options[item.selectedIndex].className;
						});
					} else { // 직접입력
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("select").attr("disabled", true);
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").attr("disabled", false);
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:text").css("display", "");
						$("#multiProductAttrDiv").find("[id^='ecAttr']").find("input:hidden").attr("disabled", false);
					}

				}
			}
		});
	}

	//상품속성(단품) 필수값 체크 // TODO : 직접 입력 관련하여 이곳도 수정해야한다.
	function itemCdValidation(type) {
		var rows = $("#itemSubTable tr");
		var index = rows.length;
		if (index > 1) {
			<%-- if ($("#rservStkQty"+(index-1)).val() == "") {
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
			} --%>

			//20180125 100개 이상 등록 가능하도록 수정
			if (index >= 1000) {
				alert("1000개 이상 등록할 수 없습니다.");
				return false;
			}
		}
		return true;
	}

	//상품유형 제어
	function onlineProdTypeCdChk(onlineProdTypeCd) {
		if (onlineProdTypeCd == "02" || onlineProdTypeCd == "03" || onlineProdTypeCd == "13" || onlineProdTypeCd == "11") { //주문제작
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

		} else if (onlineProdTypeCd == "04") { //골라담기
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

		} else if (onlineProdTypeCd == "07") { //예약상품
			$("#selTime").show();
			$("#prodTypeDiv1").hide();
			$("#prodTypeDiv2").hide();
			$("#prodTypeDiv3").hide();

			$("#setQty").val("");
			$("#optnLoadContent").val("");

		} else if (onlineProdTypeCd == "09") { //구매대행상품
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

		} else {
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
	function prodPrcMgrYnChk(prodPrcMgrYn) {
		var rows = $("#itemSubTable tr");
		var index = rows.length;

		if (prodPrcMgrYn == "0") {
			$("#optnAmtTH").hide();
			for (var i = 1; i < index; i++) {
				$("#optnAmtTD" + i).hide();
			}
			$("input[name='optnAmt']").val('');
		} else {
			$("#optnAmtTH").show();
			for (var i = 1; i < index; i++) {
				$("#optnAmtTD" + i).show();
			}
		}
	}

	//상세보기 카테고리 List
	function selectCategoryList() {
		var url = '<c:url value="/edi/product/NEDMPRO0030Category.do"/>';
		var param = new Object();
		param.onlineDisplayCategoryCode = $("#onlineDisplayCategoryCode").val();
		loadIBSheetData(mySheet, url, null, null, param);
	}

	//롯데ON 전시 카테고리 List
	function selectEcLotteOnCategoryList() {
		var url = '<c:url value="/edi/product/ecDisplayCategory.do"/>';
		var param = new Object();
		param.stdCatCd = $("#stdCatCd").val();
		param.mallCd = 'LTON'; // 롯데ON
		param.pgmId = "<c:out value='${newProdDetailInfo.pgmId}'/>"; // 롯데ON
		loadIBSheetData(mySheet2, url, null, null, param);
	}

	//롯데마트 전시 카테고리 List
	function selectEcMartCategoryList() {
		var url = '<c:url value="/edi/product/ecDisplayCategory.do"/>';
		var param = new Object();
		param.stdCatCd = $("#stdCatCd").val();
		param.mallCd = 'LTMT,TOYS'; // 롯데마트, 토이저러스
		param.pgmId = "<c:out value='${newProdDetailInfo.pgmId}'/>"; // 롯데ON
		loadIBSheetData(mySheet3, url, null, null, param);
	}

	function fnTableControll() {
		var val = $(":radio[name='prodPrcMgrYn']:checked").val();
		prodPrcMgrYnChk(val);
	}

	//전상법 템플릿 selectBox 조회
	function selectNorProdTempSel(val) {
		if (val == "") {
			$("select[name=productAddSelectTemp]").html('<option value="">선택</option>');
			$("li[id=productAddSelectText]").hide();
			return;
		}

		var param = new Object();
		var targetUrl = '<c:url value="/edi/product/selectNorProdTempSel.do"/>';
		param.infoGrpCd = val;
		param.vendorId = $("#entpCode").val();

		var optionText = '<option value="">선택</option>';

		if(val == 'EC022'){
			$("li[id=productAddSelectText]").show();
		}else{
			$("li[id=productAddSelectText]").hide();
		}

		$.ajax({
			type: 'POST',
			url: targetUrl,
			data: param,
			success: function(data) {
				var list = data.resultList;
				for (var i = 0; i < list.length; i++) {
					optionText += '<option value="'+list[i].NOR_PROD_SEQ+'">'+list[i].TITLE+'</option>';
				}

				$("select[name=productAddSelectTemp]").html(optionText);
			}
		});
	}

	function doCategoryReset() {
		mySheet.RemoveAll();
	}

	function trdTypFgSel() {
		$.getJSON("${ctx}/edi/product/trdTypFgSel.do",null, trdTypFgSelRst);
	}

	function trdTypFgSelRst(data) {
		for (var i = 0; i < data.length; i++) {
			for (var j = 0; j < $("select[name=entpCode] option").size(); j++) {
				if (data[i].VENDOR_ID == $("select[name=entpCode] option:eq("+j+")").val()) {
					var tdrTypFgNm = data[i].TRD_TYP_FG_NM;

					if (tdrTypFgNm == null || tdrTypFgNm == "null" || tdrTypFgNm == "") {
						tdrTypFgNm = ""
					} else {
						tdrTypFgNm = "("+data[i].TRD_TYP_FG_NM+")";
					}

					$("select[name=entpCode] option:eq("+j+")").replaceWith("<option value='"+data[i].VENDOR_ID+"'>"+data[i].VENDOR_ID+tdrTypFgNm+"</option>");
				}
			}
		}

		$("select[name=entpCode]").val(vndId);
	}

	/* 한글입력 방지 */
	function fn_press_han(obj) {
		//좌우 방향키, 백스페이스, 딜리트, 탭키에 대한 예외
		if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39|| event.keyCode == 46 ) { return; }
		obj.value = obj.value.replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
	}

	// 체험형 상품 여부 라디오 버튼 이벤트 제어
	$(function() {
		$("input[name='exprProdYn']:radio").click(function() {
			exprRadioCheck();
		})
	});

	// 판매자 추천평 바이트수 체크
	var maxMessage
	function checkByte() {
		var limitByte = 100; //최대 바이트
		var message = $("#sellerRecomm").val();
		var totalByte = 0;

		for (var i =0; i < message.length; i++) {
			var currentByte = message.charCodeAt(i);
			if (currentByte > 128) totalByte += 2;
			else totalByte++;
		}

		if (totalByte == limitByte) {
			maxMessage = message;
		}

		if (totalByte <= limitByte) {
			$('#messagebyte').html(totalByte);
		} else if (totalByte > limitByte) {
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

		if (optVal == "0") {
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

	//20180904 - 상품키워드 입력 기능 추가
	function fnKeywordAddNew(type) {
		if ($.trim($("#keywordTd").html()) == '') {
			$("#keywordTd").html("<table id='keywordSubTable' style='width:700px'><tr><th style='text-align:center; width:50px'>순번</th><th style='text-align:center; width:200px'>검색어</th><th style='text-align:center; width:50px'></th></tr></table>");
		}

		var rows = $("#keywordSubTable tr");
		var index = rows.length;

		var kIndex = 0;
		var keywordId = Array();

		if (index > 1) {

			if (index >= 11) {
				alert("10개 이상 등록할 수 없습니다.");
				return;
			}

			$("#keywordSubTable tr td:nth-child(2) input").each(function(tIndex) {
				keywordId[tIndex] = $(this).attr("id");
				kIndex = $(this).attr("id").substring(11);
			});

			for (var i=0; i<keywordId.length; i++) {
				if ($.trim($("#"+ keywordId[i]).val()) == "") {
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
		if (!confirm("["+idx+"]행 상품키워드를 삭제하시겠습니까?")) {
			return;
		}

		var paramInfo = {};
		paramInfo["seq"] = seq;
		paramInfo["pgmId"] = "<c:out value='${newProdDetailInfo.pgmId}'/>";

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/delOnlineKeywordInfo.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				if (data.msg == "SUCCESS") {
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

		if (msglen > 39) {
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

		for (i = 0; i < str.length; i++) {
			var ch = str.charAt(i);

			if (escape(ch).length > 4) {
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

		for (i = 0; i < str.length; i++) {
		var ch = str.charAt(i);

		if (escape(ch).length > 4) {
			msglen += 3;
		} else {
			msglen++;
		}

		if (msglen > 39) break;
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
		<!-- @ BODY WRAP START -->
		<form name="newProduct"  id="newProduct" method="post" enctype="multipart/form-data">
		<input type="hidden" name="blackListVendorFlag"	id="blackListVendorFlag" value="n" />
		<input type="hidden" name="l1GroupCode" 		id="l1GroupCode" 		value="" />
		<input type="hidden" name="prodAddMasterCd" 	id="prodAddMasterCd" 	value="" />
		<input type="hidden" name="prodAddCd" 			id="prodAddCd" 			value="" />
		<input type="hidden" name="prodAddNm" 			id="prodAddNm" 			value="" />
		<input type="hidden" name="prodCertMasterCd" 	id="prodCertMasterCd" 	value="" />
		<input type="hidden" name="prodCertDtlCd" 		id="prodCertDtlCd" 		value="" />
		<input type="hidden" name="prodCertCd" 			id="prodCertCd" 		value="" />
		<input type="hidden" name="prodCertNm" 			id="prodCertNm" 		value="" />
		<input type="hidden" name="prodAddSelectCount" 	id="prodAddSelectCount" value="" />
		<input type="hidden" name="prodAddL1CategoryId" id="prodAddL1CategoryId" value="" />
		<input type="hidden" name="itemRow" 			id="itemRow"			value="${fn:length(itemListInTemp)}" />
		<input type="hidden" name="newProductCode" 		id="newProductCode"		value="<c:out escapeXml='false' value='${param.pgmId}'/>" />

		<input type="hidden" name="tradeTypeCode"	id="tradeTypeCode" 	value="6" />
		<input type="hidden" name="productImageId"	id="productImageId"	value="${newProdDetailInfo.prodImgId}" />
		<input type="hidden" name="eventSellPrice"	id="eventSellPrice"	value="${newProdDetailInfo.evtProdSellPrc}" />
		<input type="hidden" name="imgNcnt"			id="imgNcnt"		value="${newProdDetailInfo.imgNcnt}" />
		<input type="hidden" name="onlineDisplayCategoryCode"  id="onlineDisplayCategoryCode" value="${newProdDetailInfo.categoryId}" />
		<input type="hidden" name="stdCatCd"  id="stdCatCd" value="${newProdDetailInfo.stdCatCd}" />
		<input type="hidden" name="dispCatCd"  id="dispCatCd" value="${newProdDetailInfo.dispCatCd}" />
		<input type="hidden" name="regType"		id="regType" 	value="Y" />

		<input type="hidden" name="officialOrder.newProductGeneratedDivnCode" value="EDI" />
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">

				<!-- tab 구성---------------------------------------------------------------->
				<div id="prodTabs" class="tabs" style="padding-top:10px;">
					<ul>
						<li id="pro01" class="on">기본정보</li>
						<li id="pro02" style="cursor: pointer;">이미지</li>
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
									<option value="1" selected="selected">규격</option>
								</c:if>
								<c:if test="${newProdDetailInfo.prodDivnCd eq '5'}">
									<option value="5" selected="selected">패션</option>
								</c:if>
								<c:if test="${empty newProdDetailInfo.prodDivnCd}">
									<option value="1">규격</option>
									<option value="5">패션</option>
								</c:if>
							</select>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span>카테고리 설정</th>
						<td colspan="3">
							<font color=blue>※ 마트 기간계 카테고리<br/></font>
							<select id="teamCd" name="teamCd" onchange="this.className='required ' + this.options[this.selectedIndex].className" class="required default" style="width:120px;">
								<option value="" class="default">팀 선택</option>
								<c:forEach items="${teamList}" var="teamList" varStatus="index">
									<c:if test="${fn:indexOf(teamList.teamNm,'VIC') == -1}">
										<option value="${teamList.teamCd}">${teamList.teamNm}</option>
									</c:if>
								</c:forEach>
							</select>
							<select id="l1Cd" name="l1Cd" onchange="this.className='required ' + this.options[this.selectedIndex].className" class="required default" style="width:120px;">
								<option value="" class="default">대분류 선택</option>
							</select>
							<select id="l2Cd" name="l2Cd" onchange="this.className='required ' + this.options[this.selectedIndex].className" class="required default" style="width:120px;">
								<option value="" class="default">중분류 선택</option>
							</select>
							<input type="hidden" id="prev-l3Cd" name="prev-l3Cd" />
							<select id="l3Cd" name="l3Cd" onchange="this.className='required ' + this.options[this.selectedIndex].className" class="required default" style="width:120px;">
								<option value="" class="default">소분류 선택</option>
							</select>
							<br/><br/>
							<div style="overflow:hidden;">
								<div>
									<div style="padding:0 63px 0 0;">
										<font color=blue style="font-size:11px;">※ 마트 전시 카테고리</font>
										<a href="#" class="btn" style="float:right;"onclick="javascript:delCategory();"><span>삭제</span></a>
										<a href="#" class="btn" style="float:right;" onclick="javascript:addCategory();"><span>추가</span></a>
									</div>
									<div id="ibsheet1"></div>
								</div>
							</div>
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
							<div style="overflow:hidden;">
								<div style="padding:0 63px 0 0;">
									<font color=blue style="font-size:11px;">※ EC 롯데ON 전시 카테고리</font>
									<a href="#" class="btn" style="float:right;" onclick="javascript:delEcDisplayCategory('');"><span>삭제</span></a>
									<a href="#" class="btn" style="float:right;" onclick="javascript:addEcStandardCategory('LTON', 'N');"><span>추가</span></a>
									<div id="ibsheet2"></div>
								</div>
							</div>
							<div style="overflow:hidden;">
								<div style="padding:0 63px 0 0;">
									<font color=blue style="font-size:11px;">※ EC 롯데마트몰 전시카테고리</font>
									<a href="#" class="btn" style="float:right;" onclick="javascript:delEcDisplayCategory('true');"><span>삭제</span></a>
									<a href="#" class="btn" style="float:right;" onclick="javascript:addEcStandardCategory('LTMT,TOYS', 'Y');"><span>추가</span></a>
									<div id="ibsheet3"></div>
								</div>
							</div>
						</td>
					</tr>
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
								<input type="radio" name="prodPrcMgrYn" value="0" />옵션 별 동일가격
								<input type="radio" name="prodPrcMgrYn" value="1" checked="checked" />옵션 별 별도가격
								&nbsp;/&nbsp;
								<input type="radio" name="optnDirectYn" value="N" checked="checked" /> 선택입력
								<input type="radio" name="optnDirectYn" value="Y" /> 직접입력
								<div style="float:right; padding-right: 5px">
									<a href="javascript:addEcProductAttr('I', 'add'); fnTableControll();" id="addItem" class="btn"><span>+ 단품 추가</span></a>
								</div>
								<table id="itemSubTable" style="width: 700px">
									<tr>
										<th style="width:48px">단품코드</th>
										<th style="width:380px">옵션정보</th>
										<th style="width:45px">재고여부</th>
										<th style="width:80px">재고수량</th>
										<th id="optnAmtTH" style="width:80px">가격</th>
										<th style="width:46px"></th>
									</tr>
									<c:if test="${not empty itemListInTemp}">
									<c:forEach var="itemList" items="${itemListInTemp}" varStatus="index">
									<tr id="row${index.count}">
										<td style='text-align: center'>${itemList.itemCode }<input type='hidden' name='itemCd' id='itemCd${index.count}' value='${itemList.itemCode }' />
										</td>
										<td>
											<div id='ecAttr_${index.count}'>
											</div>
										</td>
										<td>
											<select id='stkMgrYn${index.count}' name='stkMgrYn' style='width:98%;'>
												<option value='N' <c:if test="${itemList.stkMgrYn eq 'N'}">selected</c:if>>N</option>
												<option value='Y' <c:if test="${itemList.stkMgrYn eq 'Y'}">selected</c:if>>Y</option>
											</select>
										</td>
										<td><input type='text' style='text-align: right; width:98%;' id='rservStkQty${index.count}' name='rservStkQty' onkeydown='onlyNumber(event)' value='${itemList.rservStkQty}' />
										</td>
										<td id="optnAmtTD${index.count}">
										<input type='text' style='text-align: right; width:98%;' id='optnAmt${index.count}' name='optnAmt' onkeydown='onlyNumber(event)' value='${itemList.optnAmt}' />
										</td>
										<td><a href='javascript:fnItemDelete("${index.count}", "${itemList.itemCode }")' id='deleteNewItem${index.count}' class='btn' <c:if test="${!index.last}">style="display:none"</c:if>><span>삭제</span> </a>
										</td>
									</tr>
									</c:forEach>
									</c:if>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 정상매가</th>
						<td>
							<input type="text" name="normalProductSalePrice" value="<c:out value='${newProdDetailInfo.norProdSalePrc }'/>" class="required number" maxlength="8" style="width:120px;" onkeydown="onlyNumber(event)" />
							<html:codeTag objId="norProdSaleCurr" objName="norProdSaleCurr" parentCode="PRD40" comType="SELECT" formName="form" selectParam="KRW" attr="class=\"onOffField required\""/>
						</td>
						<th><span class="star">*</span> 면과세구분</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="taxatDivCode" objName="taxatDivCode" parentCode="PRD06" width="150px;" comType="SELECT" formName="form" defName="선택" />
						</td>
					</tr>
					<tr>
						<th>표시단위</th>
						<td>
							<html:codeTag objId="displayUnitCode" objName="displayUnitCode" parentCode="PRD17" width="150px;" comType="SELECT" formName="form" defName="선택" />
						</td>
						<th>표시기준수량</th>
						<td>
							<input type="text" name="displayBaseQuantity" value="<c:out value='${newProdDetailInfo.dpBaseQty}'/>" maxlength="5" style="width:150px;" class="requiredIf number default0" onkeydown="onlyNumber(event)" />
						</td>
					</tr>
					<tr>
						<th>상품규격</th>
						<td>
							<input type="text" name="productStandardName" maxlength="7" style="width:150px;" value="<c:out value='${newProdDetailInfo.prodStandardNm}'/>" />
						</td>
						<th>표시총량</th>
						<td>
							<input type="hidden" name="sellPrice" value="<c:out value='${newProdDetailInfo.evtProdSellPrc }'/>" />
							<input type="text" name="displayTotalQuantity" value="<c:out value='${newProdDetailInfo.dpTotQty}'/>" maxlength="5" class="requiredIf number default0" style="width:150px;" onkeydown="onlyNumber(event)" />
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 온라인 대표상품코드</th>
						<td>
							<div id="onlineRepresentProductTD"></div>
							<select name="onlineProductCode" class="required" style="width:98%;">
								<option value=""><c:if test="${not empty onlineRepresentProductList}">선택</c:if></option>
								<c:forEach items="${onlineRepresentProductList}" var="online">
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
							<input type="text" name="onlineProductName" class="required" maxlength="80" style="width:150px;" value="<c:out value='${newProdDetailInfo.onlineProdNm}'/>" readonly/>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span> 이익률(%)</th>
						<td>
							<input type="hidden" name="normalProductCost" value="<c:out value='${newProdDetailInfo.norProdPcost}'/>" />
							<input type="text"   name="profitRate"  class="required rate" value="<c:out value='${newProdDetailInfo.prftRate}'/>" maxlength="5" style="width:150px;" readonly/>
						</td>
						<th><span class="star">*</span> 성인상품여부</th>
						<td>
							<input type="radio" class="onOffField" name="adlYn" value="Y" />Y
							<input type="radio" class="onOffField" name="adlYn" value="N" checked="checked"/>N
							<font color="blue">※ 성인인증 필요 상품은 Y 체크 필수</font>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 상품명</th>
						<td colspan="3">
							<input type="text" name="productName" class="required"  value="${newProdDetailInfo.prodNm}" style="width:91%;" maxlength="50" />
						</td>
					</tr>
					<tr name="onLineProductNameRow">
						<th>상품사이즈</th>
						<td>
							<div name="productSize">
								가로 <input type="text" name="productHorizontalLength" value="<c:out value='${newProdDetailInfo.prodHrznLeng}'/>" style="width:40px" class="requiredIf number default0 size" maxlength="4" onkeydown='onlyNumber(event)'/>&nbsp;
								세로 <input type="text" name="productVerticalLength" value="<c:out value='${newProdDetailInfo.prodVtclLeng}'/>" style="width:40px" class="requiredIf number default0 size" maxlength="4" onkeydown='onlyNumber(event)'/>&nbsp;
								높이 <input type="text" name="productHeight" value="<c:out value='${newProdDetailInfo.prodHigt}'/>" style="width:40px" class="requiredIf number default0 size"  maxlength="4" onkeydown='onlyNumber(event)'/>
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
							<input type="text" name="md_vali_sellCode" id="md_vali_sellCode" value="${newProdDetailInfo.mdValiSellCd}" style="width:150px;" class="required number range digit13" maxlength="13"/>
						</td>
					</tr>

					<c:if test="${not empty param.divnCode}">
					<tr>
						<th>발행일</th>
						<td>
							<c:if test="${not empty newProdDetailInfo.productDy}">
							<input type="text" maxlength="8" class="requiredIf" name="productDay" value="${fn:substring(newProdDetailInfo.productDy,0,4)}-${fn:substring(newProdDetailInfo.productDy,4,6)}-${fn:substring(newProdDetailInfo.productDy,6,8)}" style="width:80px;" readonly/>
							</c:if>
							<c:if test="${empty newProdDetailInfo.productDy}">
							<input type="text" maxlength="8" class="requiredIf" name="productDay" value="" style="width:80px;" readonly />
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
							<%-- <html:codeTag attr="class=\"required\"" objId="seasonDivnCode" objName="officialOrder.seasonDivnCode" parentCode="PRD03" width="150px;" comType="SELECT" formName="form" defName="선택"  /> --%>
							<select id="sesnYearCd" name="sesnYearCd" class="required">
								<option value="">선택</option>
								<c:forEach items="${seasonYearList}" var="list" varStatus="index" >
									<option value="${list.yearCd}">${list.yearNm}</option>
								</c:forEach>
							</select>
							<select id="sesnDivnCd" name="sesnDivnCd" class="required">
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
							<input type="text" maxlength="5" name="officialOrder.minimumOrderQuantity" value="${newProdDetailInfo.minOrdPsbtQty}"	style="width:150px;" class="required number range minimum" onkeydown='onlyNumber(event)'/>
						</td>
						<th><span class="star">*</span> 최대주문가능량</th>
						<td>
							<input type="text"  maxlength="5" name="officialOrder.maximumOrderQuantity"	value="<c:out value='${newProdDetailInfo.maxOrdPsbtQty}'/>"style="width:150px;" class="required number range maximum" onkeydown='onlyNumber(event)'/>
						</td>
					</tr>

					<tr>
						<th>협력사 내부 상품코드</th>
						<td colspan="3">
							<input type="text" name="entpInProdCd"  id="entpInProdCd" style="width:150px;" value="${newProdDetailInfo.entpInProdCd}"  onkeyup="fn_press_han(this);" maxlength="18" style="ime-mode:disabled;"/>
						</td>
					</tr>

					<tr>
						<th><span class="star">*</span>상품유형</th>
						<td colspan="3">
							<html:codeTag objId="onlineProdTypeCd" objName="onlineProdTypeCd" parentCode="SM335" comType="RADIO" formName="form" selectParam="0"  orderSeqYn="Y"/>
							<br/>
							<font color="blue" style="padding-left:5px; font-size:12px;">※ 유료설치상품 선택 시 - 설치비가 있는 상품  / 무료설치상품 선택 시 - 설치비가 없는 상품</font>
							<br/>
							<div id="prodTypeDiv1" style="padding-left:5px; display:none;">
							* 설명
							<input type="text" name="prodOptionDesc" id="prodOptionDesc" style="width:84%;" maxlength="150" value="${newProdDetailInfo.prodOptionDesc}"/>
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
								<input type="text" id="setQty" name="setQty" style="width:150px; ime-mode:disabled" value="${newProdDetailInfo.setQty}" onkeydown='onlyNumber(event)' maxlength="3"/>&nbsp;&nbsp;
								옵션&nbsp;
								<input type="text" id="optnLoadContent" name="optnLoadContent" style="width:350px;" value="${newProdDetailInfo.optnLoadContent}" />
							</div>
							<div id="prodTypeDiv3" style="padding-left:5px; display:none;">
								 배송예정일&nbsp;
								<input type="text" name="deliDueDate" id="deliDueDate" style="width:50%;" maxlength="150" value="${newProdDetailInfo.prodOptionDesc}"/><font color="red">구매대행 이용불가</font>
								<br/>
								<font color="blue" style="font-size:11px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ※ 미입력시 [주문 후 5~10일 정도 소요] 문구로 일괄 적용됩니다.</font>
							</div>
							<div id="selTime" style="padding-left:5px; display:none;">
								예약 주문 가능일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="text" maxlength="8" id="psbtStartDyVal" name="psbtStartDyVal" style="width:80px;" readonly />
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
								<input type="hidden" id="psbtEndDy" name="psbtEndDy" value="${newProdDetailInfo.psbtEndDy}" />
								<br/>
								예약상품 출고지시일&nbsp;
								<input type="text" maxlength="8" id="pickIdctDy" name="pickIdctDy" value="${newProdDetailInfo.pickIdctDy}"  style="width:80px;" readonly/> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.pickIdctDy');" style="cursor:hand;" />
							</div>
						</td>
					</tr>

					<tr>
						<th>친환경인증여부</th>
						<td>
							<input type="radio" class="onOffField" name="officialOrder.ecoYn" value="0" />N
							<input type="radio" class="onOffField" name="officialOrder.ecoYn" value="1" />Y
						</td>
						<th>친환경인증분류명</th>
						<td>
							<html:codeTag objId="ecoNm" objName="officialOrder.ecoNm" parentCode="PRD08" width="150px;" comType="SELECT" formName="form" defName="선택" />
						</td>
					</tr>
					<input type="hidden" id="staffSellYn" name="staffSellYn" value="0"/><!-- CSR-16960 ---임직원몰을 더이상 사용하지 않는 관계로 default : false : 0 _20190729 -->
					<c:if test="${empty param.divnCode}">
					<tr>
						<th>발행일</th>
						<td colspan="3">
							<c:if test="${not empty newProdDetailInfo.productDy}">
								<input type="text" maxlength="8" class="requiredIf" name="productDay" value="<c:out value='${newProdDetailInfo.productDy}'/>" style="width:80px;" readonly />
							</c:if>
							<c:if test="${empty newProdDetailInfo.productDy}">
								<input type="text" maxlength="8" class="requiredIf" name="productDay" value="" style="width:80px;" readonly />
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
						<td>
							<div>
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
						<td>
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
							<font color=blue>※ 저장 전 신규(추가) 상품키워드의 순번은 ◈ 로 표시됨
							<br>※ 상품키워드 1~3행은 필수입력 입니다.
							<br>※ 1행당 검색어는 39바이트(한글기준 13글자)를 초과할 수 없습니다.
							<br>※ ,(커머) ;(세미콜론) |(버티컬 바) 와 같은 특수문자는 사용하실 수 없으며 1행당 검색어 1개를 입력해 주세요.
							</font>

							<c:if test="${empty tpcPrdKeywordList}">
								<table id="keywordSubTable" style="width:700px">
									<tr>
										<th style="text-align:center; width:30px">순번</th>
										<th style="text-align:center; width:200px">검색어</th>
										<th style="text-align:center; width:50px"></th>
									</tr>
									<tr>
										<td style="text-align:center">◈<input type="hidden" name="seq" id="seq1" value="NEW" /></td>
										<td><input type="text" style="text-align:left; width:98%;" name="searchKywrd" id="searchKywrd1" value="" maxlength="39" onblur="limitPushString(1);" /></td>
										<td></td>
									</tr>
									<tr>
										<td style="text-align:center">◈<input type="hidden" name="seq" id="seq2" value="NEW" /></td>
										<td><input type="text" style="text-align:left; width:98%;" name="searchKywrd" id="searchKywrd2" value="" maxlength="39" onblur="limitPushString(2);" /></td>
										<td></td>
									</tr>
									<tr>
										<td style="text-align:center">◈<input type="hidden" name="seq" id="seq3" value="NEW" /></td>
										<td><input type="text" style="text-align:left; width:98%;" name="searchKywrd" id="searchKywrd3" value="" maxlength="39" onblur="limitPushString(3);" /></td>
										<td></td>
									</tr>
								</table>
							</c:if>

							<c:if test="${not empty tpcPrdKeywordList}">
								<table id="keywordSubTable" style="width:700px">
									<tr>
										<th style="text-align:center; width:50px">순번</th>
										<th style="text-align:center; width:200px">검색어</th>
										<th style="text-align:center; width:50px"></th>
									</tr>
									<c:forEach var="keywordList" items="${tpcPrdKeywordList}" varStatus="index">
									<tr id="kwRow${index.count}">
										<td style="text-align:center">
											${keywordList.num}<input type="hidden" name="seq" id="seq${index.count}" value="${keywordList.seq}" />
										</td>
										<td>
											<input type="text" style="text-align:left; width:98%;" name="searchKywrd" id="searchKywrd${index.count}" value="${keywordList.searchKywrd}" maxlength="39" onblur="limitPushString(${index.count});" />
										</td>
										<c:choose>
											<c:when test="${index.count le 3}">
												<td></td>
											</c:when>
											<c:otherwise>
												<td>
													<a href='javascript:fnKeywordDelete("${index.count}", "${keywordList.seq}")' id="deleteNewKeyword${index.count}" class="btn"><span>삭제</span></a>
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
							<a href="/html/Ecom_Manual_v1_0.ppt" class="btn" id="excel"><span>전자상거래법 메뉴얼</span></a>
							<li class="tit" id="productAddSelectText" style="display:none; color:red">* 가공 수입 상품의 경우, 가공식품_수입 템플릿 선택</li>
						</ul>
					</div>
					<!-- </div> -->
				</ul>

				<div name="productAddTemplateData" class="bbs_search" style="display:none">
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
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" name="vendorTypeCd" id="vendorTypeCd" value="<c:out value='${epcLoginVO.vendorTypeCd}'/>" />
		<input type="hidden" name="pgmId" id="pgmId" value="<c:out value='${newProdDetailInfo.pgmId }'/>" /> <!-- 상품이 등록되고 나면 등록된 상품의 pgmId가 설정됨 -->
		<input type="hidden" name="entpCd" id="entpCd" value="<c:out value='${newProdDetailInfo.entpCd }'/>" /> <!-- 상품이 등록되고 나면 등록된 상품의 협력업체코드가 설정됨 -->
		<input type="hidden" name="mode" id="mode" value="<c:out value='${param.mode}'/>" /> <!-- view, modify, ''-->
		<input type="hidden" name="cfmFg" id="cfmFg" value="<c:out value='${param.cfmFg}'/>" /> <!-- 상품확정구분 -->
	</form>
	<form name="excelForm" id="excelForm">
		<iframe name="frameForExcel" style="display:none;"></iframe>
		<input type="hidden" name="optionVal" id="optionVal"/>
		<input type="hidden" name="fileName" id="fileName"/>
	</form>
	<form name="certForm" id="certForm">
		<input type="hidden" id="certInfoCode" name="certInfoCode" value="" />
		<input type="hidden" id="certInfoType" name="certInfoType" value="" />
	</form>
</body>
</html>