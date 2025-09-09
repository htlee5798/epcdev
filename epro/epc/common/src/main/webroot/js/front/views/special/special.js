//"use strict";

//카테고리 셀렉트박스 조회-o
function goCategorySpecListAjax(p_categoryId, p_depth) {
	var params = {
			'CategoryID' : p_categoryId
			, 'Depth' : p_depth
			};
	fn$ajax(_LMAppUrl+"/special/ajax/categorySpecListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategorySpecListAjax(response) {
	$("#catNavi").html(response);
}

//카테고리 하위목록(테이블)조회
function goCategorySpecSubListAjax(p_categoryId, p_leafYn
		, p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
var params = {
'CategoryID' : p_categoryId
,'LeafYn' : p_leafYn
,'PREVIEWYN' : p_previewYn
,'PREVIEWCHCD' : p_previewChCd
,'PREVIEWSTRCD' : p_previewStrCd
,'PREVIEWDISPDATE' : p_previewDispDate
};
fn$ajax(_LMAppUrl+"/special/ajax/categorySubListAjax.do", params, fnNmGetter().name);
}

//기업전문관 카테고리 하위목록(테이블)조회
function goCategoryEnterpriseSepcialSubListAjax(p_categoryId, p_leafYn, p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
	var params = {
		'CategoryID' : p_categoryId
		,'LeafYn' : p_leafYn
		,'PREVIEWYN' : p_previewYn
		,'PREVIEWCHCD' : p_previewChCd
		,'PREVIEWSTRCD' : p_previewStrCd
		,'PREVIEWDISPDATE' : p_previewDispDate
	};
	
	fn$ajax(_LMAppUrl+"/special/enterprise/ajax/categorySubListAjax.do", params, fnNmGetter().name);
}

function callBack_$goCategorySpecSubListAjax(response) {
$("#catSubNavi").html(response);
}

function callBack_$goCategoryEnterpriseSepcialSubListAjax(response) {
	$("#catSubNavi").html(response);
}

//카테고리 스타일 조회-o
function getCategorySytleAjax(p_categoryId, p_returnUrl) {
	var params = {
			'CategoryID' : p_categoryId
			,'ReturnUrl' : p_returnUrl
			};
	fn$ajax(_LMAppUrl+"/special/ajax/categorySpecStyleAjax.do", params, fnNmGetter().name);
}
function callBack_$getCategorySytleAjax(response) {
	$("#specialStyle").html(response);
}

//카테고리 코너정보 조회-o
function goCategorySpecCornerListAjax(p_categoryId, p_templateId, p_depth, p_leafYn, p_dispTemplateTypeCd, p_categoryTypeCd, p_returnUrl) {
	var params = {
			'CategoryID' : p_categoryId
			,'TemplateID' : p_templateId
			,'Depth' : p_depth
			,'LeafYn' : p_leafYn
			,'DispTemplateTypeCd' : p_dispTemplateTypeCd
			,'CategoryTypeCd' : p_categoryTypeCd
			,'ReturnUrl' : p_returnUrl
			};
	fn$ajax(_LMAppUrl+"/special/ajax/specialCornerListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategorySpecCornerListAjax(response) {
	$("#cornerList").html(response);
}

//전문관 투데이핫 조회-o
function goSpecialTodayHotListAjax() {
	var params = {
			};
	fn$ajax(_LMAppUrl+"/special/ajax/categorySpecTodayHotListAjax.do", params, fnNmGetter().name);
}
function callBack_$goSpecialTodayHotListAjax(response) {
	$("#hotList").html(response);
}

