<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do"/>


<script language="javascript">
$(document).ready(function()
{
	$('#close').click(function() {
		top.close();
	});

    setOspCategoryChecked();
});

function validateOspCat() {
	let isRepOspCategoryCheck = false;
	let isOspCategoryCheck = false;

	Array.from(document.getElementsByClassName("input-ospCategory")).forEach(checkBoxOspCat => {
		if (checkBoxOspCat.checked === true) {
			isOspCategoryCheck = true;
		}
	})

	Array.from(document.getElementsByClassName("input-repOspCategory")).forEach(checkBoxOspCat => {
		if (checkBoxOspCat.checked === true) {
			isRepOspCategoryCheck = true;
		}
	})

	if (!isOspCategoryCheck) {
		alert("OSP 전시카테고리 선택해주세요.")
		return false;
	}

	if (!isRepOspCategoryCheck) {
		alert("대표 OSP 전시카테고리 선택해주세요.");
		return false;
	}

	return true;
}

function makeOspCatParam() {
	const ospCatSep = "^";
	let ospCatNo = "";
	let ospCatNmParams = "";
	let ospCatCdParams = "";
	let ospRepParams = "";
	let ospDispYnParams = "";


	Array.from(document.getElementsByClassName("input-ospCategory")).forEach(checkBoxOspCat => {
		ospCatNo = getOspIndex(checkBoxOspCat.getAttribute("id"));
		if (checkBoxOspCat.checked === true) {
			ospCatCdParams = ospCatCdParams + checkBoxOspCat.getAttribute("id").split("-")[0] + ospCatSep;
			ospCatNmParams = ospCatNmParams + document.getElementById("div-ospCategory_" + ospCatNo).innerText + ospCatSep;
			ospRepParams = ospRepParams + (document.getElementById("repOspCategory_" + ospCatNo).checked? "Y" : "") + ospCatSep;
			ospDispYnParams = ospDispYnParams + document.getElementById("ospCatDispYn_" + ospCatNo).value + ospCatSep;
		}
	})
	return {ospCatNmParams, ospCatCdParams, ospRepParams, ospDispYnParams};
}

function setOpenerOspDisplayCategory() {
	if (!validateOspCat()) {
		return false;
	}

	let {ospCatNmParams, ospCatCdParams, ospRepParams, ospDispYnParams} = makeOspCatParam();

	opener.setOspDisplayCategory(ospCatNmParams, ospCatCdParams, ospRepParams, ospDispYnParams);
}

function checkAllOspCat(e) {
    if (!e.checked && !confirm("체크 항목 모두 초기화됩니다.")){
        e.checked = true;
        return;
    }

	Array.from(document.getElementsByClassName("input-ospCategory")).forEach( checkBoxOspCat => {
		checkBoxOspCat.checked = e.checked;
	})

	Array.from(document.getElementsByClassName("input-repOspCategory")).forEach( checkBoxOspRepCat => {
		if (e.checked === false) {
			checkBoxOspRepCat.checked = e.checked;
			checkBoxOspRepCat.disabled = true;
		}

		if (e.checked === true) {
			checkBoxOspRepCat.disabled = false;
		}
	})
}

function clickOspCat(e) {
	let ospCatIdx = getOspIndex(e.getAttribute("id"));
	let repOspCheckbox = document.getElementById("repOspCategory_" +  ospCatIdx);
	if (e.checked == true) {
		repOspCheckbox.disabled = false;
	}
	if (e.checked == false) {
		repOspCheckbox.checked = false;
		repOspCheckbox.disabled = true;
	}
}

function clickRepCat(e) {
	if (e.checked === false) return ;
	let isRepOspMultl = false;

	Array.from(document.getElementsByClassName("input-repOspCategory")).forEach(checkBoxOspCat => {
		if (checkBoxOspCat.checked === true && checkBoxOspCat.getAttribute("id") !== e.getAttribute("id")) {
			isRepOspMultl = true;
		}
	})

	let checkboxNo = e.getAttribute("id").split("_")[1]
	let ospCatByNo = document.querySelector("[id$='ospCategory_" + checkboxNo + "']")
	if (ospCatByNo.checked === false) {
		alert("선택한 OSP카테고리 중에 대표카테고리를 체크할 수 있습니다.");
		e.checked = false;
		return ;
	}

	if (!confirm("대표 카테고리로 선택하시겠습니까?")) {
		e.checked = false;
		return false;
	}

	if (isRepOspMultl === true) {
		// alert("대표카테고리는 하나만 선택하실 수 있습니다. 기존 대표 카테고리는 체크해제됩니다.")
		Array.from(document.getElementsByClassName("input-repOspCategory")).forEach(checkBoxOspCat => {
			if (checkBoxOspCat.checked === true) {
				checkBoxOspCat.checked = false;
			}
		})

		e.checked = true;
		return ;
	}
}
function getOspIndex(fullId) {
	if (fullId.indexOf("_") === -1) return null;
	const lastSepPos = fullId.lastIndexOf("_");

	return fullId.substr(lastSepPos + 1);
}

function getOspCatId(fullId) {
	if (fullId.split("-").length < 2) return null;
	const lastSepPos = fullId.lastIndexOf("-");

	return fullId.substr(0, lastSepPos);
}

function checkOspCatRep(ospCatId, fullId) {
	if (!ospCatId) return null;

	let ospIdx = getOspIndex(fullId);
	let repOspCategoryElem = document.getElementById("repOspCategory_" + ospIdx);
	repOspCategoryElem.disabled = false;

	if (ospCatId.indexOf("OSPCATREP") !== -1) {
		let ospIdx = getOspIndex(fullId)
		repOspCategoryElem.checked = true;
		return true;
	}

	return false;
}

function getOspInputElemById(ospCatId) {
	const OSP_REP_NAME = "OSPCATREP";
	let rtnOspInputElem = null;

	Array.from(document.getElementsByClassName("input-ospCategory")).forEach(ospInputElem => {
		let findOspCatId = ospCatId.indexOf(OSP_REP_NAME) !== -1 ? ospCatId.split(OSP_REP_NAME)[0] : ospCatId;
		if (findOspCatId === getOspCatId(ospInputElem.getAttribute("id"))) {
			rtnOspInputElem = ospInputElem;
		}
	})
	return rtnOspInputElem;
}

function checkOspCat(ospCatElem) {
	if (!ospCatElem) {
		console.log("찾는 OSP 카테고리 값이 없음");
		return false;
	}

	ospCatElem.checked = true;
	return true;
}

function setOspCategoryChecked() {
	let ospCatIdSelected = '<c:out value="${ospCatId}"/>';

    if (ospCatIdSelected === null || ospCatIdSelected === "null")return;

	let ospCatIdArr = ospCatIdSelected.split("_SEP_");

	for (let ospCatId of ospCatIdArr) {
        if (ospCatId === "")continue;
		let ospInputElemSelected = getOspInputElemById(ospCatId);

		if (!checkOspCat(ospInputElemSelected))continue;
		checkOspCatRep(ospCatId, ospInputElemSelected.getAttribute("id"));
	}
}


</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<%--<input type="hidden" id="isMart"  name="isMart" value="<c:out escapeXml='false' value='${isMart}'/>"/>--%>


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>OSP 전시카테고리 조회</h1>
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <!-------------------------------------------------- end of title -- -->

    <!------------------------------------------------------------------ -->
    <!--    process -->
    <!------------------------------------------------------------------ -->
    <!--    process 없음 -->
    <br>
    <!------------------------------------------------ end of process -- -->
	<div class="popup_contents">

	<div class="bbs_search2" style="width:100%;">
		<ul class="tit">
            <li class="tit">표준분류 카테고리 |</li>
			<li class="tit" style="padding-left:10px;"><c:out value="${ecCategoryFullName}" /></li>
            <li class="btn">
				<c:if test="${not empty ospDisplayCategoryList }">
					<a href="#" class="btn" onClick="setOpenerOspDisplayCategory();" id="save" ><span><spring:message code="button.common.save"  /></span></a>
				</c:if>
                <a href="#" class="btn" id="close" ><span><spring:message code="button.common.close"  /></span></a>
            </li>
        </ul>
    </div>


	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">OSP 전시카테고리</li>
			</ul>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" width="100%" >
							<colgroup>
								<col style="width:80px;" />
								<col style="" />
								<col style="width:130px;"/>
							</colgroup>
<%--							<tr>--%>
<%--								<th>표준분류 카테고리</th>--%>
<%--								<th colspan="2" style="align-items:center">AAA>BBB>CCC</th>--%>
<%--							</tr>--%>
							<tr>
								<th>No</th>
								<th>카테고리</th>
								<th>대표 카테고리 선택여부</th>
							</tr>
						</table>
						<div style="width:100%; height:190px; overflow:auto;">
						<table  class="bbs_list2" cellpadding="0" cellspacing="0" border="0" width="100%" >
							<colgroup>
								<col style="width:80px;" />
								<col />
								<col style="width:130px;" />
							</colgroup>
							<c:if test="${not empty ospDisplayCategoryList }">
								<tr style="border-bottom: 10px solid #fff;">
									<td align="center"></td>
									<td>
										<input type="checkbox" id="input-check-all" onClick="checkAllOspCat(this);"/>
										<div style="padding-left:5px; display:inline;">
											전체선택
										</div>
									</td>
									<td align="center"></td>
								</tr>
							</c:if>
<%--							<tr>--%>
<%--								<td colspan="3" algin="center">&nbsp;</td>--%>
<%--							</tr>--%>
							<c:forEach items="${ospDisplayCategoryList}" var="ospDisplayCategoryList" varStatus="index" >
								<tr class="r1" >
									<td align="center"><c:out value="${index.count}" /></td>
									<td align="left">
										<input
									  		type="checkbox"
											class="input-ospCategory"
											id="<c:out value='${ospDisplayCategoryList.OSP_CATEGORY_ID}' />-ospCategory_<c:out value='${index.count}' />"
											onClick="clickOspCat(this);"
										/>
										<div style="padding-left:5px; display:inline;" id="div-ospCategory_<c:out value='${index.count}' />">
												<c:out value="${ospDisplayCategoryList.OSP_CATEGORY_FULL_NM}" />
										</div>
									</td>
									<td align="center" style="padding-left:5px">
										<input
											type="checkbox"
											class="input-repOspCategory"
											id="repOspCategory_<c:out value='${index.count}' />"
											onClick="clickRepCat(this);"
											disabled
										/>
									</td>
									<input
										type="hidden"
										class="input-ospCatDispYn"
										id="ospCatDispYn_<c:out value='${index.count}' />"
										value="<c:out value='${ospDisplayCategoryList.OSP_CATEGORY_DISP_YN}' />"
									/>
								</tr>
							</c:forEach>
							<c:if test="${empty ospDisplayCategoryList }">
								<tr><td colspan="3" bgcolor=ffffff align=center>Data가 없습니다.</td></tr>
							</c:if>
						</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-----------------------------------------------end of 검색내역  -->
    </div><!-- class popup_contents// -->

    <br/>

	<!-- -------------------------------------------------------- -->
	<!--    footer  -->
	<!-- -------------------------------------------------------- -->
	<div id="footer">
	    <div id="footbox">
	        <div class="msg" id="resultMsg"></div>
	    </div>
	</div>
	<!---------------------------------------------end of footer -->

</div><!-- id popup// -->
</form>

</body>
</html>