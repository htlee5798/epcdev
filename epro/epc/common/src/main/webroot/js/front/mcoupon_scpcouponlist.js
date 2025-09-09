/**  SCP-PROJECT : SMJANG START
 * @FileName : mcoupon_scpcouponlist.js
 * @Description : (선택 쿠폰북의) 쿠폰 리스트 관리
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   생성일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015.03.12  장승만      신규작성
 *
 * @Copyright (C) 2000 ~ 2015 롯데정보통신(주) All right reserved.
 * </pre>
*/

var jsonCpBookList = null, jsonCouponList = null;
var selCouponImgUrl = null, causeCoupon = null, curPage = 1;

// 쿠폰 리스트 조회 : Ajax
function SearchCouponList(page) {
	// 쿠폰 리스트 초기화
	jsonCouponList = [];
	curPage = page;
	
	var data = [];

	var cpBookName = $("#couponBookName option:selected")[0].text;	
	var cpbook_id_mapp = $("#couponBookName option:selected").val();
	var issue_divn_cd = $("#couponGubun option:selected").val();
	var issue_chanel_cd = $("#couponUse option:selected").val();
	var apply_to_type_cd = $("#couponType option:selected").val();
	var sword = $("#couponKeyword").val();
	// 검색어를 넣지 않고 기본 value 값이 넘어온 걸 방지
	if(sword == "쿠폰명, 상품명을 입력하세요."){
		sword = "";
	}
	
	// url 및 파라미터 설정
	data.push({name: 'url',				 value: '/mypage/coupon/searchresult/list'});
	data.push({name: "rows",			 value: "10"});
	data.push({name: "page",			 value: page});
	data.push({name: "sidx",			 value: "CLOSE"});
	data.push({name: "sord",			 value: "asc"});
	data.push({name: "cpbook_id_mapp",   value: cpbook_id_mapp});
	data.push({name: "issue_divn_cd",    value: issue_divn_cd});
	data.push({name: "issue_chanel_cd",  value: issue_chanel_cd});
	data.push({name: "apply_to_type_cd", value: apply_to_type_cd});
	data.push({name: "sword",			 value: sword});
	
	// 쿠폰 리스트 조회 api 호출
	$.ajax({	
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  SearchCouponListResponse,
		timeout:  10 * 1000	// 10초
	});
}

//쿠폰 리스트 조회 응답에 따른 리스트 보이기
function SearchCouponListResponse(json, textStatus, jqXHR) {
	var totRecCnt = 0;
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			if (json.data != undefined) {
				totRecCnt = json.data.page_info.records;
				jsonCouponList = json.data.coupon_list;
			}
			// 검색 타이틀 보이기
			$('#couponBookTitle').html($("#couponBookName option:selected")[0].text);
		}
		else
			console.log(json.message);
	}
	
	// 전체 레코드 개수 보이기
	$('#cpListCount').html(totRecCnt);
	
	// 페이지 설정
	SetPageInfos(totRecCnt, curPage, "SearchCouponList", ShowCouponList);
};

// 쿠폰 리스트 보이기
function ShowCouponList() {
	
	$('#scpCouponList').empty();
	
	// 검색 조건 보이기
	var gubun = $("#couponGubun option:selected")[0].text;
	var use = $("#couponUse option:selected")[0].text;
	var type = $("#couponType option:selected")[0].text;
	var strSearch = "";
	
	if (gubun != "전체") strSearch += gubun + ' | ';
	if (use != "전체") strSearch += use + ' | ';
	if (type != "전체") strSearch += type + ' | ';
	
	$('#searchCondition').empty();
	if (strSearch != "") {
		strSearch = strSearch.substring(0, strSearch.length - 3);
		$('#searchCondition').html(strSearch);
	}
	
	// 쿠폰 리스트가 없으면 진행하지 않음
	if (jsonCouponList.length < 1)  {
		$('#scpCouponList').append('<tr><td class="nodata" colspan="5">검색 결과가 없습니다.</td></tr>');
		return;
	}
	
	// 쿠폰 리스트 나타내기
	var cbList = "";
	var couponName = "", benefit = "", startDate = "", endDate = "", expDate = "";
	
	var cpbook_id_mapp = $("#couponBookName option:selected").val();

	// 쿠폰 리스트 정렬 : 현재 마감임박순으로 정렬 (by JSM)
	//jsonCouponList = sortJSONbyField(jsonCouponList, "vali_term_end_dd", true);
	//jsonCouponList = sortJSONbyField(jsonCouponList, "used_yn", false);
	
	// jsonCouponList 값을 순서대로 사용한다.
	for (var i = 0; i < jsonCouponList.length; i++) {
		// 사용 가능 시작일
		startDate = setDatePeriod(jsonCouponList[i].vali_term_start_dd);
		// 사용 가능 종료일
		endDate = setDatePeriod(jsonCouponList[i].vali_term_end_dd);
		
		// 혜택 설정
		benefit = MakeBenefitString(jsonCouponList[i].coupon_type_cd, 
									jsonCouponList[i].coupon_desc_content,
									jsonCouponList[i].coupon_type_cd_name);
		benefit = jsonCouponList[i].dc_savu_amt+benefit;
		
		// 이벤트 기간 설정
		if (jsonCouponList[i].used_yn == "Y")
			expDate = '<p class="td_used">사용완료';
		else if (jsonCouponList[i].use_day_info != null && jsonCouponList[i].use_day_info != "") {
			// 지정일자 처리
			var arrDate = jsonCouponList[i].use_day_info.split(',');
			expDate = setDatePeriod(arrDate[0]);
			if (arrDate.length == 2)
				expDate += ", " + setDatePeriod(arrDate[1]);
			else if (arrDate.length > 2)
				expDate += ", " + setDatePeriod(arrDate[1]) + ", ...";
		}
		else
			expDate = '<p>' + startDate + " ~ " + endDate;			

		cbList = '<tr>' +
			'<td class="tit_left"><p>' + jsonCouponList[i].issue_chanel_cd_name + '</p></td>' +
			'<td class="tit_left"><p>' + jsonCouponList[i].coupon_nm + '</p></td>' +
			'<td class="td_txt"><p class="t_red">' + benefit + '</p></td>' +
			'<td class="td_txt">' + expDate + '</p></td>' +
			'<td class="td_button">' +
				'<span class="btn_white btn_w_td_ls">' +
					'<span class="btn_td_w_03">' +
						'<a title="쿠폰 상세 조회" onClick="javascript: GetCouponDetailInfo(' + i + ')">상세보기&gt;</a>' +
					'</span>' +
				'</span>' +
			'</td>' +
		'</tr>';

		$('#scpCouponList').append(cbList);
	}
}

// 할인 및 적립 및 해당 단위 내역
function MakeBenefitString(code, desc, name) {
	
	var strBenefit = "";
	
	if (desc.indexOf("배") > 0)	// ??배 처리는 향후 확인하여 삭제 또는 수정할 것 (by JSM)	
		strBenefit += "배 ";
	else if (code == "E")
		strBenefit += "포인트 ";
	else if (code == "A" || code == "C" || code == "J" ||
			 code == "F" || code == "H" || code == "L" || code == "N")
		strBenefit += "% ";
	else if (code == "B" || code == "D" || code == "K" ||
			 code == "G" || code == "I" || code == "M" || code == "O")
		strBenefit += "원 ";
	else
		strBenefit += " ";
	
	// 쿠폰 상세정보에서는 name 값을 넘기지 않음.
	if (name != undefined) {		
		if (code == "A" || code == "B" || code == "E" || code == "J" || code == "K" ||
			code == "F" || code == "G" || code == "L" || code == "M")
			strBenefit += "할인";
		else
			strBenefit += "적립";
	}
	
	return strBenefit;
}

// 쿠폰 상세정보 가져오기 : Ajax
function GetCouponDetailInfo(index) {
	// 쿠폰 발생 사유 설정
	causeCoupon = "[" + jsonCouponList[index].issue_chanel_cd_name + "]" + jsonCouponList[index].evnt_nm;
	if (causeCoupon.trim() == "[]") couponCause = "";
	
	// 쿠폰 이미지 URL 설정
	selCouponImgUrl = jsonCouponList[index].prvw_url;
	
	var url = '/mypage/coupon/show';
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	   				  value: url});
	data.push({name: 'issue_coupon_no_digit', value: jsonCouponList[index].issue_coupon_no_digit});
	
	// 쿠폰 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  GetCouponDetailInfotResponse,
		timeout:  10 * 1000	// 10초
	});
}

// 쿠폰 상세정보 가져오기 응답 : 쿠폰 팝업
function GetCouponDetailInfotResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var couponDetail = null;
			
			if (json.data != undefined)
				couponDetail = json.data;
			else {
				console.log("쿠폰 상세정보가 없습니다.");
				return;
			}

			// 쿠폰 상세정보
			var couponValue = "", couponType = "", isUsed = null;
			
			// CouponValue : goods -> 상품형, sum -> 금액형 
			// 150311 sejisesang 쿠폰유형에 따라 badeg와 num의 순서 바뀜 
			if (couponDetail.apply_to_type_cd == "PRICE") {
				couponValue = "sum";
				$("#couponBenefit").html( '<span class="num" id="prod_num"></span> <span class="badge" id="prod_badge"></span>' );
			}
			else {
				couponValue = "goods";
				$("#couponBenefit").html( '<span class="badge" id="prod_badge"></span> <span class="num" id="prod_num"></span>' );
			}
			// CouponType : used, save(적립), discnt, save-nc(조건없는 적립형), discnt-nc(조건없는 할인)
			if (couponDetail.used_yn == "Y")
				isUsed = true;
			else
				isUsed = false;

			if (couponDetail.coupon_type_cd_name.indexOf("할인") > -1) {
				if (couponValue == "sum") {
					if (couponDetail.use_to_lowst_amt == "" || couponDetail.use_to_lowst_amt == "0")
						couponType = "discnt-nc";
					else
						couponType = "discnt";
				}
				else
					couponType = "";
			}
			else {
				if (couponValue == "sum") {
					if (couponDetail.use_to_lowst_amt == "" || couponDetail.use_to_lowst_amt == "0")
						couponType = "save-nc";
					else
						couponType = "save";
				}
				else
					couponType = "";
			}

			// 쿠폰 상세 정보
			var badge = "", num = "", period = "", startDate = "", endDate = "", unit = "";
			
			// 할인/적립 단위
			unit = MakeBenefitString(couponDetail.coupon_type_cd, couponDetail.coupon_desc_content);
			
			if (couponDetail.coupon_type_cd_name.indexOf("할인") > -1)
				badge = "할인";
			else
				badge = "적립";
			
			// 할인/적립 금액
			num = couponDetail.dc_savu_amt ;
			
			
			// 기간 설정
			var d = new Date();
			var today = d.getFullYear() + "." + (d.getMonth() + 1) + "." + d.getDate();
			var diffDays = 0;
			
			if (couponDetail.use_day_info != null && couponDetail.use_day_info != "") {
				// 지정일자 처리
				var arrDate = couponDetail.use_day_info.split(',');
				period = setDatePeriod(arrDate[0]);
				
				for (var i = 1; i < arrDate.length; i++) {
					period += ", " + setDatePeriod(arrDate[i]);
				}
				
				diffDays = getDateDiff(setDatePeriod(arrDate[arrDate.length - 1]), today);
			}
			else {
				startDate = setDatePeriod(couponDetail.vali_term_start_dd);
				endDate = setDatePeriod(couponDetail.vali_term_end_dd);

				period = startDate + " ~ " + endDate;
				
				diffDays = getDateDiff(endDate, today);
			}
			
			// 쿠폰 사용기간 종료여부 확인
			if (diffDays < 0) period = "쿠폰 사용기간이 종료되었습니다.";
			
			
			// couponValue & couponType 값에 따른 쿠폰 상세정보 설정
			
			// couponValue에 따른 값의 위치 설정
			if(couponValue == "sum"){
				$('#prod_badge').html(unit+badge);
				$('#prod_num').html(num);
			}else{
				$('#prod_badge').html(badge);
				$('#prod_num').html(num+unit);
			}
			
			$('#prod_title').html(couponDetail.coupon_nm);
			$('#prod_explain').html(couponDetail.coupon_desc_content);
			$('#prod_period').html(period);
			$('#prod_cause').html(causeCoupon);
			
			$('#cond_appobj').empty();
			$('#prod_img img').css('display', 'none');
			$('.goods-coupon-hover').css('display', 'none');
			
			if (couponValue == "goods") {
				// 상품 정보 (SALE_CD:판매코드, PROD_CD:상품번호, ITEM_CD:단품번호, CATEGORY_ID:카테고리번호) 가져오기
				GetProductInformation(couponDetail.coupon_prod_info_list);

				$('#prod_img').html('<img src="' + selCouponImgUrl + '" alt="상품이름">');
				$('#prod_img img').css('display', 'block');
				$('.goods-coupon-hover').css('display', 'block');
				
				// 쿠폰 적용 대상 (상품형에만 적용 됨)
				var applyTo = "";
				for (var i = 0; i < couponDetail.coupon_prod_info_list.length; i++) {
					applyTo += '<li><a onclick="goProductDetail(\'' + couponDetail.coupon_prod_info_list[i].category_id + 
							   '\', \'' + couponDetail.coupon_prod_info_list[i].prod_sell_cd + '\', \'N\');">[' + 
							   couponDetail.coupon_prod_info_list[i].prod_sell_cd + '] ' + 
							   couponDetail.coupon_prod_info_list[i].prod_nm + '</a> <img src="//simage.lottemart.com/images/front/mymart/mcoupon/btn-more-detail.png"></li>';
				}
				$('#cond_appobj').html(applyTo);
			}
			/*
			$('img').bind('error', function (e) {
			    $(this).attr('src', '/images/front/mymart/mcoupon/no_image.jpg');
			});
			*/

			$('#prod_img img').bind('error', function (e) {
			    $(this).attr('src', '//simage.lottemart.com/images/front/mymart/mcoupon/no_image_s.jpg');
			});
			
			// 쿠폰 사용 조건
			$('#cond_useplace').html('사용가능지점 : ' + couponDetail.use_psbt_excp_brnch_content);
			
			var useCondi = '사용가능금액 : ';
			var useLimit = '한도금액 : ';
			var useLimitCount = '사용가능횟수 : ';
			
			if (couponDetail.use_to_lowst_amt != null && couponDetail.use_to_lowst_amt != "")
				useCondi += '최소 <span class="blue">' + setComma(couponDetail.use_to_lowst_amt) + '</span>원 이상, ';
			if (couponDetail.use_to_uper_amt != null && couponDetail.use_to_uper_amt != "")
				useCondi += '최대 <span class="blue">' + setComma(couponDetail.use_to_uper_amt) + '</span>원 이하';
			
			if (couponDetail.dc_lim_amt != null && couponDetail.dc_lim_amt != "")
				useLimit += '<span class="blue">' + setComma(couponDetail.dc_lim_amt) + '</span>원까지 할인 가능';
			/* 0227 추가 : 필드명 확인하여 수정할 것 (by JSM)*/
			if (couponDetail.dc_lim_cnt != null && couponDetail.dc_lim_cnt != "")
				useLimitCount += '쿠폰1매당 <span class="blue">' + setComma(couponDetail.dc_lim_cnt) + '</span>개 까지 구매가능';
			
			if (useCondi != "사용가능금액 : ") {
				$('#use_condition_li').css('display', 'block');
				$('#use_condition').html(useCondi);
			}
			else
				$('#use_condition_li').css('display', 'none');			
			if (useLimit != "한도금액 : ") {
				$('#use_limit_li').css('display', 'block');
				$('#use_limit').html(useLimit);
			}
			else
				$('#use_limit_li').css('display', 'none');
			if (useLimitCount != "사용가능횟수 : ") {
				$('#use_limit_count_li').css('display', 'block');
				$('#use_limit_count').html(useLimitCount);
			}
			else
				$('#use_limit_count_li').css('display', 'none');
				

			// 유의 사항(\n으로 구분함)
			var arrNote = couponDetail.note_content.split('\n');
			var notes = "";
			
			$('#cond_note').empty();

			for (var i = 0; i < arrNote.length; i++) {
				if (arrNote[i].trim() == "") continue;
				notes += '<li><p>' + arrNote[i].trim() + '</p></li>';
			}
			// 관리자 추가 유의 사항
			if (causeCoupon.indexOf("겸용") < 0) {
				if (causeCoupon.indexOf("롯데마트") > -1)
					notes += '<li><p>본 쿠폰은 롯데마트 몰에서만 사용 가능합니다.</p></li>';
				else
					notes += '<li><p>본 쿠폰은 오프라인 매장의 모바일 앱에서만 사용 가능합니다.</p></li>';
			}
		
			$('#cond_note').html(notes);

			// 쿠폰 정보 팝업창 열기 
			openPopupCoupon(couponValue, couponType, isUsed);
		}
		else
			console.log(json.message);
	}
};

// 상품 정보 조회 : Ajax
function GetProductInformation(prodList) {
	
	var data = [];
	
	// url 및 파라미터 설정
	for (i = 0; i < prodList.length; i++) {
		data.push({name: 'Prod' + i + 'SellCd',	value: prodList[i].prod_sell_cd});
	}
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type       : 'GET',
        url        : '/couponInfoGet.do',
        data       : data,
        dataType   : "json",
        async      : false,
        success    : function(json, textStatus, jqXHR) {
        	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
        		if (CheckMessageCode(json, jqXHR)) {
        			var prodInfo = JSON.parse(json.data);
					for (var i = 0; i < prodList.length; i++) {
						for (var j = 0; j < prodInfo.length; j++) {
							if (prodList[i].prod_sell_cd == prodInfo[j].SALE_CD) {
								prodList[i].category_id = prodInfo[j].CATEGORY_ID;
								break;
							}
						}
					}
        		}
        		else
        			console.log(json.message);
        	}
        },
		timeOut    : (10 * 1000)
	});
}

// 사용 구분 조회 : Ajax
function SetCouponIssueDivnCd() {
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	   	   value: '/mypage/common/code/list'});
	data.push({name: 'cd_divn_id', value: 'ISSUE_DIVN_CD'});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  SetCouponIssueDivnCdResponse,
		timeout:  10 * 1000	// 10초
	});
}


// 사용 구분 조회 응답
function SetCouponIssueDivnCdResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {			
			var cdList = [], divnCd = [];
			
			if (json.data != undefined)
				cdList = json.data.cd_list;
			
			// 콤보박스에 채울 이벤트 지점 리스트
			divnCd.push({text:'전체', value:''});
			for (var i = 0; i < cdList.length; i++) {
				console.log(cdList[i].mst_cd_nm);
				divnCd.push({text:cdList[i].mst_cd_nm, value:cdList[i].mst_cd});
			}
			
			// 콤보박스에 이벤트 지점 리스트 넣기
			$('#couponGubun').empty();
			$.each(divnCd, function(i, el) {
				$('#couponGubun').append(new Option(el.text, el.value));
			});

			$("#couponGubun option:eq(0)").attr("selected", "selected");
		}
		else
			console.log(json.message);
	}
};

// 사용처(온/오프라인) 조회 : Ajax
function SetCouponIssueChannelCd() {
	var data = [];

	// url 및 파라미터 설정
	data.push({name: 'url',	   	   value: '/mypage/common/code/list'});
	data.push({name: 'cd_divn_id', value: 'ISSUE_CHANEL_CD'});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  SetCouponIssueChannelCdResponse,
		timeout:  10 * 1000	// 10초
	});
}

// 사용처(온/오프라인) 조회 응답
function SetCouponIssueChannelCdResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var cdList = [], chanelCd = [];
			
			if (json.data != undefined)
				cdList = json.data.cd_list;
			
			// 콤보박스에 채울 이벤트 지점 리스트
			chanelCd.push({text:'전체', value:''});
			for (var i = 0; i < cdList.length; i++) {
				chanelCd.push({text:cdList[i].mst_cd_nm, value:cdList[i].mst_cd});
			}
			
			// 콤보박스에 이벤트 지점 리스트 넣기
			$('#couponUse').empty();
			$.each(chanelCd, function(i, el) {
				$('#couponUse').append(new Option(el.text, el.value));
			});

			$("#couponUse option:eq(0)").attr("selected", "selected");
		}
		else
			console.log(json.message);
	}
};

// 쿠폰 유형 조회 : Ajax
function SetCouponApplyToTypeCd() {
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	   	   value: '/mypage/common/code/list'});
	data.push({name: 'cd_divn_id', value: 'APPLY_TO_TYPE_CD'});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  SetCouponApplyToTypeCdResponse,
		timeout:  10 * 1000	// 10초
	});
}

// 쿠폰 유형 조회 응답
function SetCouponApplyToTypeCdResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var cdList = [], typeCd = [];
			
			if (json.data != undefined)
				cdList = json.data.cd_list;

			// 콤보박스에 채울 이벤트 지점 리스트
			typeCd.push({text:'전체', value:''});
			for (var i = 0; i < cdList.length; i++) {
				typeCd.push({text:cdList[i].mst_cd_nm, value:cdList[i].mst_cd});
			}
			
			// 콤보박스에 이벤트 지점 리스트 넣기
			$('#couponType').empty();
			$.each(typeCd, function(i, el) {
				$('#couponType').append(new Option(el.text, el.value));
			});

			$("#couponType option:eq(0)").attr("selected", "selected");
		}
		else
			console.log(json.message);
	}
};


/** SCP-PROJECT : SMJANG END **/