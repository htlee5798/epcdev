<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lottemart.epc.common.util.SecureUtil"%>
<%@ page import="lcn.module.common.util.DateUtil"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 20181211 배송지 설정 수정 -->
<%@ include file="/WEB-INF/jsp/edi/common.jsp" %>
<!-- 20181211 배송지 설정 수정 -->
<%
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String tabNo = "13";
    String vendorId = SecureUtil.stripXSS(request.getParameter("vendorId"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
<!-- 20181211 배송지 설정 수정 -->
<%--<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script>--%>
<!-- 20181211 배송지 설정 수정 -->
<script type="text/javascript">

	/*********************************************************
	 * 초기화
	 *********************************************************/
	$(document).ready(function() {
		//엔터 막기
		$("*").keypress(function(e) {
			if (e.keyCode==13) return false;
		});

		ibsheet();

		doSearch();
		//업체 공통 조건 사용
		$("#condUseChkFlag").click(function() {
			
			condUseChkClick($(this));
		});

		//배송비 종류 선택시
		$("input[name='deliKindCdSel']").click(function() {	
			$(":input").each(function(index) {
				var type = $(this).attr("type");

				if (type == "text" ) {
					if ($(this).attr("name") != "deliAmt06" ) {
						$(this).val("");
						$("#psbtChkFlag").attr("checked",false);
					}
				}
			});
			if ("${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == '03' || "${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == '13') {
				var ndeliKindCd = $("input[name='deliKindCdSel']:checked").val();
				if (ndeliKindCd == "01") {
					alert("설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
					$(":radio[name='deliKindCdSel']").attr("checked", false);
					return;
				}
			}

			//제주 및 도서산간 추가 배송비 설정 오류
			if ($(":radio[name='deliKindCdSel']:checked").val() == "") {
				$("#detailForm input[type='text']").each(function(index) {
					$(this).attr("disabled",true);
					$(this).val("");
				});

				$("#psbtChkFlag").attr("checked",false);
				$("#psbtChkFlag").attr("disabled",true);
				$("#psbtChkYn").val("N");
			} else {
				$("#detailForm input[type='text']").each(function(index) {
					$(this).attr("disabled",false);
				});

				$("#psbtChkFlag").attr("disabled",false);
			}
		});

		var deliKindCd = $("input[name='deliKindCdSel']:checked").val();
		if (deliKindCd =="02") {
			alert("향후 배송비 정책 변경으로 인해 현재 적용중인 수량별 차등 배송비는 더 이상 사용할 수 없습니다. 다른 배송비 종류를 선택해주세요.\n※ 무료배송을 원할 시, 고정배송비 체크 후 0원을 입력하세요.");
		}else if (deliKindCd =="08") {
			alert("향후 배송비 정책 변경으로 인해 현재 적용중인 착불 배송비는 더 이상 사용할 수 없습니다. 다른 배송비 종류를 선택해주세요.\n※ 무료배송을 원할 시, 고정배송비 체크 후 0원을 입력하세요.");
		}
		if ( ("${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == "03" || "${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == "13") 
				&& "${deliveryInfo.NOCH_DELI_YN }" != "Y") {
			alert("설치 상품 배송비 정책 변경으로 인해\n현재 적용중인  배송비는 더 이상 사용할 수 없습니다.\n설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");	
		}

		//제주 및 도서산간 배송비 체크
		$("#psbtChkFlag").click(function() {
			if ($(this).attr("checked") == "checked") {
			 	$("#psbtChkYn").val("Y");
			} else {
				$("#psbtChkYn").val("N");
			}
		});

		//20181211 - 배송지 설정 수정
		fstChkFlag = true;
		//20181211 - 배송지 설정 수정

		$(":input").focusout(function() {
			var nameVal = $(this).attr("name");

			if (nameVal.indexOf("minSetQty") != -1 || nameVal.indexOf("maxSetQty") != -1) {
				setQtyValChk(nameVal);
			}
		});

		//var condUseYn = '<c:out value="${vendorDeliInfoYn.DELI_INFO_YN}" />';
		var condUseYn = '<c:out value="${deliveryInfo.ENTP_PROD_COND_DELI_YN}" />';
		$("#condUseYn").val(condUseYn);

		//업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정
		var arrVendorInfo = new Array();

		<c:forEach items="${vendorDeliInfoList}" var="vendorList" varStatus="index">
			var jsonArr = new Object();
			// 추후 사용해야할지도 몰라서 json 으로 데이터 관리
			jsonArr.APPLY_END_DY = "${vendorList.APPLY_END_DY}";
			jsonArr.APPLY_START_DY = "${vendorList.DELI_DIVN_CD}";
			jsonArr.DELI_DIVN_CD = "${vendorList.DELI_DIVN_CD}";
			jsonArr.DELI_BASE_MAX_AMT = "${vendorList.DELI_BASE_MAX_AMT}";
			jsonArr.DELI_BASE_MIN_AMT = "${vendorList.DELI_BASE_MIN_AMT}";
			arrVendorInfo["${index.count}" -1] = jsonArr;
		</c:forEach>

		if (arrVendorInfo.length > 1) {
			var resetHtml = '배송정보 <span style="padding-top:5px; padding-left:20px;"><input type="checkbox"  id="condUseChkFlag" name="condUseChkFlag"  style="vertical-align:middle;" checked="checked" /> 업체 공통조건 사용</span>';
				$("#condUseChkLi").html(resetHtml);
		    if (condUseYn == "" || condUseYn == "Y") {
		    	$("#condUseChkFlag").attr("checked", true);	
			} else{
				$("#condUseChkFlag").attr("checked", false);
			}

			condUseChkClick($("#condUseChkFlag"))
	
		} else {

			var deliNm = "";

			for (var i=0; i<arrVendorInfo.length; i++) {
				if (arrVendorInfo[i].DELI_DIVN_CD == "20") {
					deliNm ="배송비";
				} else if (arrVendorInfo[i].DELI_DIVN_CD == "10") {
					deliNm ="반품배송비";
				}
			}

			if (arrVendorInfo.length ==0) {
				deliNm ="배송비,반품배송비";
			}

			var resetHtml = '배송정보 <span style="padding-top:5px; padding-left:20px;"><input type="checkbox"  id="condUseChkFlag" name="condUseChkFlag"  style="vertical-align:middle;" disabled="true" />업체 공통조건 사용 불가 <font color="red">([SCM]시스템관리→업체정보관리→주문배송비관리 '+deliNm+' 미등록)</font></span>';
			$("#condUseChkLi").html(resetHtml);

		}

		$("#condUseChkFlag").click(function() {
			if ("${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == '03' || "${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == '13') {
				alert("설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
				$("#condUseChkFlag").attr("checked", false);
			}
			condUseChkClick($("#condUseChkFlag"))
		}); 

	});

	//업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정
	function vendorDeliInfoWarning(msg) {
		alert("                         업체 공통조건 사용 불가\n ([SCM]시스템관리→업체정보관리→주문배송비관리 "+msg+" 미등록)");
	}

	//배송 이력 (ibsheet)
	function ibsheet() {
		//START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "150px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

		var ibdata = {};

		//SizeMode:
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load

		//Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};

		//Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{SaveName:"NUM"					, Header:"순번"			, Type:"Int"		, Align:"Center"	, Width:15		, Edit:0}
	      , {SaveName:"S_STATUS"			, Header:"상태"			, Type:"Status"		, Align:"Center"	, Width:15		, Edit:0	, Hidden:true}
		  , {SaveName:"DELIVERY_KIND_CD"	, Header:"배송비종류코드"	, Type:"Text"		, Align:"Center"	, Width:35		, Edit:0	, Hidden:true}
		  , {SaveName:"DELIVERY_KIND_NM"	, Header:"배송비종류"		, Type:"Text"		, Align:"Center"	, Width:35		, Edit:0}
		  , {SaveName:"SEQ"					, Header:"순번"			, Type:"Text"		, Align:"Center"	, Width:35		, Edit:0	, Hidden:true}
		  , {SaveName:"APPLY_START_DY"		, Header:"적용시작일자"		, Type:"Date"		, Align:"Center"	, Width:35		, Edit:0	, Format:"yyyy-MM-dd"}
		  , {SaveName:"APPLY_END_DY"		, Header:"적용종료일자"		, Type:"Date"		, Align:"Center"	, Width:35		, Edit:0	, Format:"yyyy-MM-dd"}
		  , {SaveName:"DELIVERY_AMT"		, Header:"배송비"			, Type:"Int"		, Align:"Right"		, Width:35		, Edit:0}
		  , {SaveName:"DELI_COND_AMT"		, Header:"배송조건금액"		, Type:"Int"		, Align:"Right"		, Width:35		, Edit:0}
		  , {SaveName:"DELI_BASE_MIN_QTY"	, Header:"최소수량"		, Type:"Int"		, Align:"Center"	, Width:35		, Edit:0}
		  , {SaveName:"DELI_BASE_MAX_QTY"	, Header:"최대수량"		, Type:"Int"		, Align:"Center"	, Width:35		, Edit:0}
		  , {SaveName:"PROD_CD"				, Header:"인터넷상품코드"	, Type:"Text"		, Align:"Center"	, Hidden:true}
		  , {SaveName:"UDT_CHK"				, Header:"수정체크"		, Type:"Text"		, Hidden:true}
		];

		IBS_InitSheet(mySheet, ibdata);
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.FitColWidth();
		_eventSetDeliveryDetailValue();
		condUseChkClick($("#condUseChkFlag"));

		//제주 및 도서산간 추가 배송비 설정 오류
		if ($("#condUseYn").val() == "N") {
			if ($(":radio[name='deliKindCdSel']:checked").val() == "") {
				$("#detailForm input[type='text']").each(function(index) {
						$(this).attr("disabled",true);
						$(this).val("");
				});

				$("#psbtChkFlag").attr("checked",false);
				$("#psbtChkFlag").attr("disabled",true);
				$("#psbtChkYn").val("N");
			} else {
				$("#detailForm input[type='text']").each(function(index) {
					$(this).attr("disabled",false);
				});

				$("#psbtChkFlag").attr("disabled",false);
			}
		}
	}

	//업체 공통 조건 사용
	function condUseChkClick(arg) {

		if (arg.attr("checked") == "checked") {
			$("#condUseYn").val("Y");

			$(":radio[name='deliKindCdSel']").attr("checked", false);
			$(":radio[name='deliKindCdSel']").attr("disabled",true);

			$("select[name='bdlDeliYn']").attr("disabled",true);
			$("select[name='bdlDeliYn02']").attr("disabled",true);
			$("select[name='bdlDeliYn03']").attr("disabled",true);

			$("#detailForm input[type='text']").each(function(index) {
				$(this).attr("disabled",true);
				$(this).val("");
			});

			$("#psbtChkFlag").attr("checked",false);
			$("#psbtChkFlag").attr("disabled",true);
			$("#psbtChkYn").val("N");

		} else {
			$("#condUseYn").val("N");

			$(":radio[name='deliKindCdSel']").attr("disabled",false);

			$("select[name='bdlDeliYn']").attr("disabled",false);
			$("select[name='bdlDeliYn02']").attr("disabled",false);
			$("select[name='bdlDeliYn03']").attr("disabled",false);

			$("#detailForm input[type='text']").each(function(index) {
				$(this).attr("disabled",false);
			});

			$("#psbtChkFlag").attr("disabled",false);
		}
	}

	/*********************************************************
	 * 조회
	 *********************************************************/
	function doSearch() {
		goPage("1");
	}

	/*********************************************************
	 * 정합성 (필수) 확인
	 *********************************************************/
	function validateNewProductInfo() {
		var rtnVal = true;
		var deliKindCd = $(":radio[name='deliKindCdSel']:checked").val();
		var deliAmt06 =  $("#deliAmt06").val();

		//상품금액별 차등
		if (deliKindCd == "01") {
			if ($.trim($("#deliAmt01").val()) == "") {
				alert("상품금액별 차등 배송비를 입력 하세요.");
				rtnVal = false;
			} else {
				if ($.trim($("#deliCondAmt").val()) == "") {
					alert("기준 구매금액을 입력 하세요.");
					rtnVal = false;
				}
			}
		}else if (deliKindCd == "03") { // 고정배송비
			if ($.trim($("#deliAmt03").val()) == "") {
				alert("고정배송비를 입력 하세요.");
				rtnVal = false;
			}
		}

		//제주 및 도서산간
		if (rtnVal) {
			if ($("#psbtChkFlag").attr("checked") == "checked") {
				if ($.trim($("#deliAmt04").val()) == "") {
					alert("제주 배송비를 입력 하세요.");
					$("#deliAmt04").focus();
					rtnVal = false;
				} else if ($.trim($("#deliAmt05").val()) == "") {
					alert("도서산간 배송비를 입력 하세요.");
					$("#deliAmt05").focus();
					rtnVal = false;
				}
			}
			//반품배송비
			if ( deliKindCd != "" &&  (deliAmt06 == undefined || deliAmt06 == "") ) {
				alert("반품 배송비를 입력 하세요.");
				rtnVal = false;
			}
		}

		return rtnVal;
	}

	/*********************************************************
	 * 페이징
	 *********************************************************/
	function goPage(currentPage) {
		var url = '<c:url value="/product/selectDeliverySearch.do"/>';
		loadIBSheetData(mySheet, url, currentPage, "#detailForm", null);
	}

	/*********************************************************
	 * 수량별 차등 초기화
	 *********************************************************/
	function reset(arg,flag) {
		for(var i=flag; i<6; i++) {
			$("#deliAmt02_"+i).val("");
			$("#minSetQty_"+i).val("");
			$("#maxSetQty_"+i).val("");
		}
	}

	/*********************************************************
	 * 수량별 차등 배송비 정합성 확인
	 *********************************************************/
	function setDeliAmt02(arg,flag) {
		if ($.trim($("#"+arg.name).val()) == "") {
			$("#minSetQty_"+flag).val("");
			$("#maxSetQty_"+flag).val("");
		}

		if (flag > 1) {
			if ($.trim($("#"+arg.name).val()) != "" && $.trim($("#deliAmt02_"+(flag-1)).val()) == "") {
				alert("순차적으로 등록 하세요. 2");
				$("#"+arg.name).val("");
				return;
			}
		}

		if ($.trim($("#"+arg.name).val()) == "") {
			for(var i=flag; i<6; i++) {
				$("#deliAmt02_"+i).val("");
				$("#minSetQty_"+i).val("");
				$("#maxSetQty_"+i).val("");
			}
		}
	}

	/*********************************************************
	 * 수량별 차등 수량 입력시 배송비 정합성 확인
	 *********************************************************/
	function setQtyChk(arg,flag) {
		if ($.trim($("#deliAmt02_"+flag).val()) == "") {
			fstChkFlag = true;
			alert("배송비 부터 입력 하세요.");
			$("#"+arg.name).val("");
			$("#deliAmt02_"+flag).focus();
			return;
		}

		fstChkFlag = false;
	}

	/*********************************************************
	 * 수량별 차등 수량 정합성 확인
	 *********************************************************/
	function setQtyValChk(name) {
		if (!fstChkFlag) {
			var flag = name.substring(name.length-1,name.length);

			if (flag > 1 && name.indexOf("min") != -1) {
				if ($.trim($("#minSetQty_"+(flag-1)).val()) == "" || $.trim($("#maxSetQty_"+(flag-1)).val()) == "") {
					alert("순차적으로 등록 하세요. 1");
					$("#deliAmt02_"+flag).val("");
					$("#minSetQty_"+flag).val("");
					$("#maxSetQty_"+flag).val("");
					fstChkFlag = true;
					return;
				}

				if ($.trim($("#"+name).val()) != "") {
					if (Number($("#minSetQty_"+flag).val()) != Number($("#maxSetQty_"+(flag-1)).val())+1) {
						$("#minSetQty_"+flag).val(Number($("#maxSetQty_"+(flag-1)).val())+1);
						return;
					}

					if (Number($("#minSetQty_"+flag).val()) <= Number($("#maxSetQty_"+(flag-1)).val())) {
						alert("이전 최대값 보다 같거나 작습니다.");
						$("#minSetQty_"+flag).val("");
						$("#minSetQty_"+flag).focus();
						fstChkFlag = true;
						return;
					}
				}
			}

			if (name.indexOf("max") != -1) {
				if ($.trim($("#"+name).val()) != "") {
					if ($.trim($("#minSetQty_"+flag).val()) == "") {
						alert("최소수량이 입력 되지 않았습니다.");
						$("#"+name).val("");
						$("#"+name).focus();
						fstChkFlag = true;
						return;
					} else {
						if (Number($("#minSetQty_"+flag).val()) >= Number($("#"+name).val())) {
							alert("최대수량이 최소수량보다 같거나 작습니다.");
							$("#"+name).val("");
							$("#"+name).focus();
							fstChkFlag = true;
							return;
						}
					}
				}
			}

			if (name.indexOf("min") != -1) {
				if ($.trim($("#maxSetQty_"+flag).val()) != "") {
					if (Number($("#minSetQty_"+flag).val()) >= Number($("#maxSetQty_"+flag).val())) {
						alert("최대수량이 최소수량보다 같거나 작습니다.");
						$("#maxSetQty_"+flag).val("");
						$("#maxSetQty_"+flag).focus();
						fstChkFlag = true;
						return;
					}
				}
			}

			fstChkFlag = false;
		}
	}

	/*********************************************************
	 * 조회 후 배송값 설정
	 *********************************************************/
	function _eventSetDeliveryDetailValue() {

		//20181211 - 배송지 설정 수정
		var psbtRegnCd = '<c:out value="${deliveryInfo.DELI_PSBT_REGN_CD}" />';	//배송가능지역
		//20181211 - 배송지 설정 수정

		var bdlDeliYn = '<c:out value="${newProdDetailInfo.BDL_DELI_YN}" />';	//묶음배송처리
		var deliKindCd = "";
		var count = 0;
		var psbtChkFlag = false;
		var psbtChkCnt = 0;
		var addr1 = "";
		var addr2 = "";
		var rtnChkFlag = false;
		var rtnChkCnt = 0;

		<c:forEach items="${infoDList}" var="DeliList" varStatus="index" >
			count = "${index.count}";
			//20181211 - 배송지 설정 수정
			//$("#deliCondAmt").val("${DeliList.DELI_COND_AMT}");
			if ("${DeliList.DELIVERY_KIND_CD}" == "01" && "${deliveryInfo.NOCH_DELI_YN }"=="N") {
				$("#deliCondAmt").val("${DeliList.DELI_COND_AMT}");
			}
			//20181211 - 배송지 설정 수정

			$("#modDate").val("${DeliList.MOD_DATE}");

			if ("${DeliList.DELIVERY_KIND_CD}" == "01" || "${DeliList.DELIVERY_KIND_CD}" == "02" || "${DeliList.DELIVERY_KIND_CD}" == "03" || "${DeliList.DELIVERY_KIND_CD}" == "08") {
				deliKindCd = "${DeliList.DELIVERY_KIND_CD}";

				/*if ("${deliveryInfo.NOCH_DELI_YN }"=="Y") {
					deliKindCd = "";
				}*/

				$("#deliKindCdSelBf").val("${DeliList.DELIVERY_KIND_CD}");
				$("select[name='psbtChkYnBf']").val(deliKindCd);
				$("select[name='bdlDeliYn"+deliKindCd+"']").val(bdlDeliYn);
			}

			if ("${DeliList.DELIVERY_KIND_CD}" == "02") { //수량별 차등
				$("#deliAmt02_"+count).val("${DeliList.DELIVERY_AMT}");
				$("#deliAmt02Seq_"+count).val("${DeliList.SEQ}");
				$("#minSetQty_"+count).val("${DeliList.DELI_BASE_MIN_QTY}");
				$("#maxSetQty_"+count).val("${DeliList.DELI_BASE_MAX_QTY}");
			}

			if ("${DeliList.DELIVERY_KIND_CD}" != "") {
				if ("${deliveryInfo.NOCH_DELI_YN }"=="N") {
					$("#deliAmt"+"${DeliList.DELIVERY_KIND_CD}").val("${DeliList.DELIVERY_AMT}");
				}

				$("#deliAmtSeq"+"${DeliList.DELIVERY_KIND_CD}").val("${DeliList.SEQ}");

				if ("${DeliList.DELIVERY_KIND_CD}" == "04" || "${DeliList.DELIVERY_KIND_CD}" == "05") {
					if ("${DeliList.DELIVERY_AMT}" != "") {
						psbtChkCnt++;
					}
				}

				if ("${DeliList.DELIVERY_KIND_CD}" == "06") { // 반품배송비 이전값
					if ("${DeliList.DELIVERY_AMT}" != "") {
						rtnChkCnt++;
					}
				}

				if ("${deliveryInfo.NOCH_DELI_YN }"=="Y") {
					if ("${DeliList.DELIVERY_KIND_CD}" == "03" && "${DeliList.DELIVERY_AMT}" == "0") {
						$(":radio[name='deliKindCdSel']:radio[value='" + deliKindCd + "']").attr("checked", true);
						$("#deliAmt"+"${DeliList.DELIVERY_KIND_CD}").val("${DeliList.DELIVERY_AMT}");
					}
					if ("${DeliList.DELIVERY_KIND_CD}" == "06") {
						$("#deliAmt"+"${DeliList.DELIVERY_KIND_CD}").val("${DeliList.DELIVERY_AMT}");
					}
					if ("${DeliList.DELIVERY_KIND_CD}" == "04") {
						$("#psbtChkFlag").attr("checked", true);
						$("#deliAmt"+"${DeliList.DELIVERY_KIND_CD}").val("${DeliList.DELIVERY_AMT}");
					}
					if ("${DeliList.DELIVERY_KIND_CD}" == "05") {
						$("#psbtChkFlag").attr("checked", true);
						$("#deliAmt"+"${DeliList.DELIVERY_KIND_CD}").val("${DeliList.DELIVERY_AMT}");
					}
				}
			}
		</c:forEach>

		if (psbtChkCnt == 2) {
			psbtChkFlag = true;
			$("#psbtChkYnBf").val("Y");
		}

		if (rtnChkCnt == 1) {
			rtnChkFlag = true;
			$("#rtnChkYnBf").val("Y");
		}

		$("#psbtChkFlag").attr("checked", psbtChkFlag);

		$("select[name='psbtRegnCd']").val(psbtRegnCd);	//배송가능지역

		//20181211 - 배송지 설정 수정
		addr1 = '<c:out value="${deliveryInfo.JUSO_1}" />';	//출고지 주소
		addr2 = '<c:out value="${deliveryInfo.JUSO_2}" />';	//반품교환 주소

		if (addr1 != "") {
			$("select[name='addr1']").val(addr1);
		}

		if (addr2 != "") {
			$("select[name='addr2']").val(addr2);
		}
		//20181211 - 배송지 설정 수정

		if ("${deliveryInfo.NOCH_DELI_YN }"=="N") {
			$(":radio[name='deliKindCdSel']:radio[value='" + deliKindCd + "']").attr("checked", true);
		}

		//배송비 변경 이력이 없을 때 무료배송의 경우 묶음배송 가능 여부 선택로직 추가(위의 foreach 영역의 동일한 처리는 배송비 변경 이력이 있을 경우만 해당됨)
		if (deliKindCd == "") {
			$("select[name='bdlDeliYn']").val(bdlDeliYn);
		}

		//20181211 - 배송지 설정 수정
		var vendorAddrInfoCnt = '<c:out value="${vendorAddrInfoCnt}" />';

		if (vendorAddrInfoCnt == "0") {
			alert("       배송지 정보에 사용될 주소 정보가 등록되어 있지 않습니다.\n          ([SCM]시스템관리→업체정보관리→업체주소 미등록)");
		}
		//20181211 - 배송지 설정 수정
	}

	/*********************************************************
	 * 데이터 읽은 직후 이벤트
	 *********************************************************/
	function mySheet_OnSearchEnd() {
		for(var i=1; i<mySheet.RowCount()+1; i++) {
			if (mySheet.GetCellValue(i,"UDT_CHK") == "N" || mySheet.GetCellValue(i,"APPLY_END_DY") != "99991231") {
				mySheet.SetCellEditable(i, 7, 0);
			}

			if (mySheet.GetCellValue(i,"DELIVERY_KIND_CD") == "01" && mySheet.GetCellValue(i,"UDT_CHK") != "N" && mySheet.GetCellValue(i,"APPLY_END_DY") == "99991231") {
				mySheet.SetCellEditable(i, 8, 1);
			}
		}
	}

	/*********************************************************
	 * 금액 정합성 확인 (숫자)
	 *********************************************************/
    function numkeyCheck(event) {
    	event = event || window.event;
    	var keyID = (event.which) ? event.which : event.keyCode;

    	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39) {
    		return;
    	} else {
			alert("숫자만 입력해 주세요");
    	}

    	return false;
    }

	/*********************************************************
	 * 초기화
	 *********************************************************/
    function reset() {
    	$("#deliAmt01").val("");
    	$("#deliCondAmt").val("");

    	for(var i=1; i<6; i++) {
	    	$("#deliAmt02_"+i).val("");
			$("#minSetQty_"+i).val("");
			$("#maxSetQty_"+i).val("");
	    }

    	$("#deliAmt03").val("");
    }

	/*********************************************************
	 * 중복 저장 방지
	 *********************************************************/
    var doubleSubmitFlag = false;

    function doubleSubmitCheck() {
		if (doubleSubmitFlag) {
			return doubleSubmitFlag;
		} else {
			doubleSubmitFlag = true;
			return false;
		}
    }

	/*********************************************************
	 * 저장
	 *********************************************************/
	function doUpdate() {

		if (doubleSubmitCheck()) return;

		var condUseYn = $("#condUseYn").val();
		var deliKindCd = $(":radio[name='deliKindCdSel']:checked").val();
		var modD = $("#modDate").val();
		var modDate = modD.substr(0,10);
		var now = new Date();
		year = now.getFullYear();
		month = now.getMonth() + 1
		if ((month + "").length < 2) {
			month = "0" + month;
		}
		date1 = now.getDate() + 1;
		date = now.getDate();
		if ((date + "").length < 2) {
			date = "0" + date;
		}
		if ((date1 + "").length < 2) {
			date1 = "0" + date1;
		}
		today = year + "-" + month + "-" + date;
		tomorrow = year + "-" + month + "-" + date1;

		var form = document.detailForm;
		var url = '<c:url value="/product/deliveryInfoSave.do"/>';
		var deliKindCd = $(":radio[name='deliKindCdSel']:checked").val();

		if ($("#condUseChkFlag").attr("checked") != "checked") {
			// 배송비 정책 변경 : 수량별 차등, 착불 배송비 삭제
			if (deliKindCd == undefined || deliKindCd == "" || deliKindCd=="02" || deliKindCd=="08") {
				alert("배송비 종류를 선택 하세요.\n※ 무료배송 적용을 원할 시, 고정배송비 체크 후 0원을 입력하세요.");
				doubleSubmitFlag = false;
				return;
			}

			if (!validateNewProductInfo()) {
				doubleSubmitFlag = false;
				return;
			}
		}

		//설치상품일경우 무료배송만 가능(고정배송비 0원)
		if ("${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == "03" || "${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == "13") {
			if ( ($("input[name='deliKindCdSel']:checked").val() == "03" && $("#deliAmt03").val() != "0") || $("#condUseChkFlag").attr("checked") == "checked" ) {
				alert("설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.")
				doubleSubmitFlag = false;
				return;
			}
		}

		<c:forEach items="${list}" var="list" varStatus="index">
			var applySDy = "${list.APPLY_START_DY}";
			var applyEDy = "${list.APPLY_END_DY}";
			var deliCd = "${list.DELIVERY_KIND_CD}";

			var yyyyMMddS = String(applySDy);
			var yyyyMMddE = String(applyEDy);
		    var yearS = yyyyMMddS.substring(0,4);
		    var monthS = yyyyMMddS.substring(4,6);
		    var dayS = yyyyMMddS.substring(6,8);
		    var yearE = yyyyMMddE.substring(0,4);
		    var monthE = yyyyMMddE.substring(4,6);
		    var dayE = yyyyMMddE.substring(6,8);
		    var APPLY_START_DY = yearS+"-"+monthS+"-"+dayS;
		    var APPLY_END_DY = yearE+"-"+monthE+"-"+dayE;

			if (deliKindCd == undefined) {
				deliKindCd="";
			}
		</c:forEach>

		$("#deliKindCd").val(deliKindCd);

		form.action = url;
		form.submit();
	}

	/*********************************************************
	 * 저장 (성공/실패) 메시지
	 *********************************************************/
	function mySheet_OnSaveEnd(code, Msg) {
		alert(Msg);
	}

	/*********************************************************
	 * 배송지 수정
	 *********************************************************/
	//20181211 - 배송지 설정 수정
	function doAddressUpdate() {

		if (("${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == "03" || "${onlineProdInfo.ONLINE_PROD_TYPE_CD}" == "13") 
			&& "${deliveryInfo.NOCH_DELI_YN }" != "Y") {
			alert("설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
			return;
		}
		var form = document.detailForm;
		var url = '<c:url value="/product/deliveryAddressInfoSave.do"/>';

		form.action = url;
		form.submit();

	}
	//20181211 - 배송지 설정 수정

</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
<form name="detailForm" id="detailForm">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>" />
<input type="hidden" id="modDate" name="modDate" />
<input type="hidden" id="condUseYn" name="condUseYn" value="N" />
<input type="hidden" id="deliKindCdSelBf" name="deliKindCdSelBf" />
<input type="hidden" id="psbtChkYnBf" name="psbtChkYnBf" />
<input type="hidden" id="rtnChkYnBf" name="rtnChkYnBf" />
<input type="hidden" id="psbtChkYn" name="psbtChkYn" />
<div id="wrap_menu" style="width:1000px;">
	<!-- 상품 상세보기 하단 탭 -->
	<c:set var="tabNo" value="<%=tabNo%>" />
	<c:import url="/common/productDetailTab.do" charEncoding="euc-kr">
		<c:param name="tabNo" value="${tabNo}" />
		<c:param name="prodCd" value="<%=prodCd%>" />
	</c:import>

	<!-- 배송지 정보 -->
	<div class="wrap_con">
		<div class="bbs_list" style="width:auto; margin-bottom:20px;">
			<ul class="tit">
				<li class="tit">배송지 정보</li>
				<li class="btn"><a href="javascript:doAddressUpdate();" class="btn" id="save" ><span>배송지 수정</span></a></li>
			</ul>
 			<table class="bbs_grid2" style="table-layout:fixed;">
				<colgroup>
					<col width="13%">
					<col width="20%">
					<col width="13%">
					<col width="20%">
					<col width="13%">
					<col width="20%">
				</colgroup>
				<tr>
					<th>배송가능지역</th>
					<td colspan="5">
						<html:codeTag attr="class=\"required\"" objId="psbtRegnCd" objName="psbtRegnCd" parentCode="SM338" width="150px;" comType="SELECT" formName="form" defName="선택" />
					</td>
				</tr>
				<tr>
					<th>출고지 주소</th>
					<td colspan="5">
						<c:set var="zipCdVal" value="" />
						<select name="addr1" class="required" style="width:500px;">
							<option value="" selected="selected">선택</option>
							<c:forEach items="${vendorAddrlist}" var="Addrlist" varStatus="index" >
								<c:if test="${Addrlist.ZIP_CD != ''}">
									<c:set var="zipCdVal" value="(${Addrlist.ZIP_CD})" />
								</c:if>
								<c:if test="${Addrlist.ADDR_KIND_CD eq '01' || Addrlist.ADDR_KIND_CD eq '03'}">
									<option value="${Addrlist.ADDR_SEQ}">${Addrlist.ADDR_1_NM} ${Addrlist.ADDR_2_NM} ${zipCdVal}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>반품/교환 주소</th>
					<td colspan="5">
						<c:set var="zipCdVal" value="" />
						<select name="addr2" class="required" style="width:500px;">
							<option value="" selected="selected">선택</option>
							<c:forEach items="${vendorAddrlist}" var="Addrlist" varStatus="index" >
								<c:if test="${Addrlist.ZIP_CD != ''}">
									<c:set var="zipCdVal" value="(${Addrlist.ZIP_CD})" />
								</c:if>
								<c:if test="${Addrlist.ADDR_KIND_CD eq '02' || Addrlist.ADDR_KIND_CD eq '03'}">
									<option value="${Addrlist.ADDR_SEQ}">${Addrlist.ADDR_1_NM} ${Addrlist.ADDR_2_NM} ${zipCdVal}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 배송비 정보 -->
	<div class="wrap_con">
		<div class="bbs_list" style="width:auto; margin-bottom:20px;">
			<ul class="tit">
				<li class="tit" id="condUseChkLi">배송비 정보
					<%-- <c:if test="${deliveryInfo.ENTP_PROD_COND_DELI_YN eq 'Y'}">
						<span style="padding-top:5px; padding-left:20px;"><input type="checkbox"  id="condUseChkFlag" name="condUseChkFlag"  style="vertical-align:middle;" checked="checked" /> 업체 공통조건 사용</span>
					</c:if>
					<c:if test="${deliveryInfo.ENTP_PROD_COND_DELI_YN eq 'N'}">
						<span style="padding-top:5px; padding-left:20px;"><input type="checkbox"  id="condUseChkFlag" name="condUseChkFlag"  style="vertical-align:middle;" /> 업체 공통조건 사용</span>
					</c:if> --%>
				</li>
				<li class="btn"><a href="javascript:doUpdate();" class="btn" id="save" ><span>배송비 수정</span></a></li>
			</ul>
 			<table class="bbs_grid2" style="table-layout:fixed;">
				<colgroup>
					<col width="13%">
					<col width="20%">
					<col width="10%">
					<col width="10%">
					<col width="13%">
					<col width="10%">
					<col width="13%">
					<col width="10%">
				</colgroup>
				<tr>
					<th>업체 공통조건 사용</th>
					<td colspan="5">${deliveryInfo.ENTP_PROD_COND_DELI_YN}</td>
				</tr>
				<tr>
					<th>무료배송여부</th>
					<td colspan="5">${deliveryInfo.NOCH_DELI_YN}</td>
				</tr>
				<tr>
					<th>공통배송비</th>
					<td colspan="5" style="padding-right:5px;">
						<div class="bbs_list">
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col width="13%">
									<col width="21%">
									<col width="10%">
									<col width="10%">
									<col width="13%">
									<col width="10%">
									<col width="13%">
									<col width="10%">
								</colgroup>
								<tr>
									<th>주문배송비</th>
									<td>
										<c:forEach items="${vendorDeliInfoList}" var="vendorList" varStatus="index">
											<c:if test="${vendorList.DELI_DIVN_CD eq '10'}">
												주문금액
												${vendorList.DELI_BASE_MAX_AMT}
												원 이상 무료, 아니면
												${vendorList.DELIVERY_AMT} 원
											</c:if>
										</c:forEach>
									</td>
									<th>반품배송비</th>
									<td>
										<c:forEach items="${vendorDeliInfoList}" var="vendorList" varStatus="index">
											<c:if test="${vendorList.DELI_DIVN_CD eq '20'}">
												${vendorList.DELIVERY_AMT} 원
											</c:if>
										</c:forEach>
									</td>
									<th>제주 <br/>추가배송비</th>
									<td>
										<c:if test="${vendorDlvInfo.ADD_DELI_AMT1 != '0'}">
										<fmt:formatNumber value="${fn:trim(vendorDlvInfo.ADD_DELI_AMT1)}" type="number" currencySymbol="" />
										</c:if>
										<c:if test="${vendorDlvInfo.ADD_DELI_AMT1 eq '0'}">
										${vendorDlvInfo.ADD_DELI_AMT1}
										</c:if>
										<c:if test="${fn:trim(vendorDlvInfo.ADD_DELI_AMT1) != ''}">원</c:if>
									</td>
									<th>도서산간<br/>추가배송비</th>
									<td>
										<c:if test="${vendorDlvInfo.ADD_DELI_AMT2 != '0'}">
										<fmt:formatNumber value="${fn:trim(vendorDlvInfo.ADD_DELI_AMT2)}" type="number" currencySymbol="" />
										</c:if>
										<c:if test="${vendorDlvInfo.ADD_DELI_AMT2 eq '0'}">
										${vendorDlvInfo.ADD_DELI_AMT2}
										</c:if>
										<c:if test="${fn:trim(vendorDlvInfo.ADD_DELI_AMT2) != ''}">원</c:if>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<th>배송 이력</th>
					<td colspan="5">
						<div id="ibsheet1"></div>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span> 배송비 설정</th>
					<td colspan="6" style="padding-right:5px;">
						<div class="bbs_list" >
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:20%" />
									<col style="width:21%" />
									<col style="width:50%" />
									<col style="width:12%" />
								</colgroup>
								<tr>
									<th>배송비종류</th>
									<th>배송비</th>
									<th>기준</th>
									<th>묶음배송</th>
								</tr>
								<%--  무료배송비 삭제 
								<tr style="display:none;">
		 							<td>
										<input type="radio"  id="deliKindCdSel" name="deliKindCdSel"  value=""  < %-- <c:if test="${infoDList.DELIVERY_KIND_CD  eq ''}">   checked="checked" </c:if> --% > />무료
									</td>
									<td style="text-align:right; padding-right:4px;">
										0 원
									</td>
									<td>
										수량/주문 금액에 상관없이 무조건 무료
									</td>
									<td style="text-align:center; padding-right:4px;">
										<select name="bdlDeliYn" class="required" style="width:70px;">
											<option value="Y" selected="selected">가능</option>
											<option value="N">불가</option>
										</select>
									</td>
								</tr> --%>
								<tr>
									<td>
										<input type="radio" id="deliKindCdSel01" name="deliKindCdSel" value="01" <%-- <c:if test="${infoDList.DELIVERY_KIND_CD eq '01'}">  checked="checked"  </c:if> --%> />상품금액별 차등
									</td>
									<td style="padding-right:2px;">
										<input type="text" id="deliAmt01" name="deliAmt01" style="width:115px;" value="" onkeydown='return numkeyCheck(event)'/> 원
										<input type="hidden" id="deliAmtSeq01"  name="deliAmtSeq01"  value="" />
									</td>
									<td>
										구매금액 <input type="text" id="deliCondAmt" name="deliCondAmt" maxlength="10" style="width:115px;" value="" onkeydown="return numkeyCheck(event)" /> 원 이상 시 배송비 무료
									</td>
									<td style="text-align:center; padding-right:4px;">
										불가<input type="hidden" id="bdlDeliYn01" name="bdlDeliYn01" value="N" />
									</td>
								</tr>
								<!--  수량별 차등 배송비 삭제-->
								<tr style="display:none;">
									<td rowspan="5">
										<input type="radio" id="deliKindCdSel02" name="deliKindCdSel" value="02" class="choice">수량별 차등<br/>(최대 5개까지 등록 가능)
									</td>
									<td style="padding-right:2px;">
										<input type="text" id="deliAmt02_1" name="deliAmt02_1" style="width:115px;" value="" onkeydown="return numkeyCheck(event)"  onkeyup="setDeliAmt02(this,1);"/> 원
										<input type="hidden" id="deliAmt02Seq_1" name="deliAmt02Seq_1" value="" />
									</td>
									<td>
										<input type="text" id="minSetQty_1" name="minSetQty_1" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,1);"/>
										~
										<input type="text" id="maxSetQty_1" name="maxSetQty_1" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,1);"/>
									</td>
									<td rowspan="5"  style="text-align:center; padding-right:4px;">
										불가<input type="hidden" id="bdlDeliYn02" name="bdlDeliYn02" value="N" />
									</td>
								</tr>
								<tr style="display:none;">
									<td style="padding-right:2px;">
										<input type="text" id="deliAmt02_2" name="deliAmt02_2" style="width:115px;" value="" onkeydown="return numkeyCheck(event)" onkeyup="setDeliAmt02(this,2);"/> 원
										<input type="hidden" id="deliAmt02Seq_2" name="deliAmt02Seq_2" value="" />
									</td>
									<td>
										<input type="text" id="minSetQty_2" name="minSetQty_2" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,2);"/>
										~
										<input type="text" id="maxSetQty_2" name="maxSetQty_2" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,2);"/>
									</td>
								</tr>
								<tr style="display:none;">
									<td style="padding-right:2px;">
										<input type="text" id="deliAmt02_3" name="deliAmt02_3" style="width:115px;" value="" onkeydown="return numkeyCheck(event)" onkeyup="setDeliAmt02(this,3);"/> 원
										<input type="hidden" id="deliAmt02Seq_3" name="deliAmt02Seq_3"  value="" />
									</td>
									<td>
										<input type="text" id="minSetQty_3" name="minSetQty_3" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,3);"/>
										~
										<input type="text" id="maxSetQty_3" name="maxSetQty_3" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,3);"/>
									</td>
								</tr>
								<tr style="display:none;">
									<td style="padding-right:2px;">
										<input type="text" id="deliAmt02_4" name="deliAmt02_4" style="width:115px;" value="" onkeydown="return numkeyCheck(event)" onkeyup="setDeliAmt02(this,4);"/> 원
										<input type="hidden" id="deliAmt02Seq_4" name="deliAmt02Seq_4"  value="" />
									</td>
									<td>
										<input type="text" id="minSetQty_4" name="minSetQty_4" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,4);"/>
										~
										<input type="text" id="maxSetQty_4" name="maxSetQty_4" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,4);"/>
									</td>
								</tr>
								<tr style="display:none;">
									<td style="padding-right:2px;">
										<input type="text" id="deliAmt02_5" name="deliAmt02_5" style="width:115px;" value="" onkeydown="return numkeyCheck(event)" onkeyup="setDeliAmt02(this,5);"/> 원
										<input type="hidden" id="deliAmt02Seq_5" name="deliAmt02Seq_5" value="" />
									</td>
									<td>
										<input type="text" id="minSetQty_5" name="minSetQty_5" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,5);"/>
										~
										<input type="text" id="maxSetQty_5" name="maxSetQty_5" style="width:50px;" value="" onkeydown="return numkeyCheck(event);" onkeyup="setQtyChk(this,5);"/>
									</td>
								</tr>
								<tr>
									<td>
										<input type="radio" id="deliKindCdSel03" name="deliKindCdSel" value="03"  />고정배송비
									</td>
									<td>
										<input type="text" id="deliAmt03" name="deliAmt03" style="width:115px;" value="" onkeydown="return numkeyCheck(event)"/> 원
										 <input  type="hidden" id="deliAmtSeq03" name="deliAmtSeq03" value="" />
									</td>
									<td>
										수량/주문금액에 상관없이 고정 배송비
										<span><font color="red"> </br> * 무료배송을 원할 시, 고정배송비를 0원으로 입력해주세요.</font></span>
									</td>
									<td style="text-align:center; padding-right:4px;">
									<c:choose>
									<c:when test="${onlineProdInfo.ONLINE_PROD_TYPE_CD eq '03' || onlineProdInfo.ONLINE_PROD_TYPE_CD eq '13'}">
										불가
										<input type="hidden" id="bdlDeliYn03" name="bdlDeliYn03" value="N" />
									</c:when>
									<c:otherwise>
										<select name="bdlDeliYn03" class="required" style="width:70px;">
											<option value="Y" selected="selected">가능</option>
											<option value="N">불가</option>
										</select>
									</c:otherwise>
									</c:choose>
									</td>
								</tr>
								<!--  착불 배송비 삭제 -->
								<tr style="display:none;">
									<td>
										<input type="radio" id="deliKindCdSel08" name="deliKindCdSel" value="08" />착불배송
										<input type="hidden" id="deliAmtSeq08" name="deliAmtSeq08" value="" />
									</td>
									<td style="text-align:right; padding-right:4px;">착불</td>
									<td>지역별 상이</td>
									<td style="text-align:center; padding-right:4px;">
										불가<input type="hidden" id="bdlDeliYn08" name="bdlDeliYn08" value="N" />
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" id="psbtChkFlag" name="psbtChkFlag" />제주/도서산간<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;추가 배송비 설정
									</td>
									<td colspan="3">
										제주 <input type="text" id="deliAmt04" name="deliAmt04" value="" onkeydown="return numkeyCheck(event)" /> 원&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="hidden" id="deliAmtSeq04" name="deliAmtSeq04" value="" />
										도서산간 <input type="text" id="deliAmt05" name="deliAmt05" value="" onkeydown="return numkeyCheck(event)" /> 원
											<input type="hidden" id="deliAmtSeq05" name="deliAmtSeq05" value="" />
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<span><font color="red">* 일반 배송권역은 무료배송, 제주/도서산간 지역 배송비 부과를 원할 시<br/>1. 고정배송비 체크 후 0원 입력, 2. 제주/도서산간 추가 배송비 설정 선택 후 배송비 입력하세요.</font></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<th><span class="star">*</span> 반품 배송비</th>
					<td colspan="5">
						<input type="text" id="deliAmt06" name="deliAmt06" value="" onkeydown="return numkeyCheck(event)"/> 원
						<input  type="hidden"  id="deliAmtSeq06" name="deliAmtSeq06" value="" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</form>
<form name="form1" id="form1">
<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>" />
</form>
</body>
</html>