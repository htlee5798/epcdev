/** SCP-PROJECT : SMJANG START 
 * @FileName : mcoupon_scpcouponbooklist.js
 * @Description : 쿠폰북 리스트 관리
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

var jsonCpBookList = null;
var curPage = 1;

// 일반 쿠폰북 리스트 조회 : Ajax
function GetGeneralCouponBookList(page) {
	// 쿠폰북 리스트 초기화
	jsonCpBookList = [];

	// 현재 페이지 설정
	curPage = page;
	
	var data = [];
	
	var sortCode = $("#couponbookSort option:selected").val();

	// url 및 파라미터 설정
	data.push({name: 'url',	 value: '/mypage/coupon/book/all/list'});
	data.push({name: "rows", value: "10"});		// 페이지 당 보여질 아이템 수
	data.push({name: "page", value: page});		// 현재 페이지
	data.push({name: "sidx", value: sortCode});	// 정렬 대상 (변경될 수 있음)
	data.push({name: "sord", value: "asc"});	// 정렬 형태	
	data.push({name: "smart_combo_coupon_choice_yn", value: "Y"});	// 선택 쿠폰만	
	
	// 일반 쿠폰북 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  GetGeneralCouponBookListResponse,
		timeout:  10 * 1000	// 10초
	});
}

//쿠폰북 리스트 조회 응답에 따른 리스트 보이기
function GetGeneralCouponBookListResponse(json, textStatus, jqXHR) {
	var totRecCnt = 0;
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			// 가져온 쿠폰북 리스트 정리
			if (json.data != undefined) {
				totRecCnt = json.data.page_info.records;
				jsonCpBookList = json.data.cpbook_list;
			}
		}
		else
			console.log(json.message);
	}

	// 페이지 설정 : curPage는 ShowCouponBookList()의 파라미터 (보여질 페이지)
	SetPageInfos(totRecCnt, curPage, "GetGeneralCouponBookList", ShowCouponBookList);
};


// 쿠폰북 리스트 보이기 : page 파라미터에 따른 표시
function ShowCouponBookList() {
	// 쿠폰북 리스트가 비어있으면 진행하지 않는다.
	if (jsonCpBookList.length < 1) {
		$('#couponbook_list .nodata').css('display', 'block');
		return;
	}
	
	$('#couponbook_list').empty();
	
	/*
	var sort = $("#couponbookSort option:selected")[0].text;
	if (sort == "마감임박순")
		// jsonCpBookList를 마감임박순으로 정렬
		jsonCpBookList = sortJSONbyField(jsonCpBookList, "vali_term_end_dd", true);
	else
		// jsonCpBookList를 최신등록순으로 정렬
		jsonCpBookList = sortJSONbyField(jsonCpBookList, "vali_term_start_dd", false);
	 */		
	
	// 쿠폰북 리스트 나타내기
	var position = ["left", "right"];
	var cbList = "", imgSrcList = "", causeCoupon = "", brand="";
	var imgSrc = "", startDate = "", endDate = "", period = "", type="";
	
	// jsonCpBookList 값을 순서대로 사용한다.
	for (var i = 0; i < jsonCpBookList.length; i++) {
		if (jsonCpBookList[i] == null) break;
		
		// 사용 가능 시작일
		startDate = setDatePeriod(jsonCpBookList[i].vali_term_start_dd);
		// 사용 가능 종료일
		endDate = setDatePeriod(jsonCpBookList[i].vali_term_end_dd);
		
		// 이벤트 기간 설정
		period = startDate + " ~ " + endDate;
		
		// 사용처에 따른 이미지 선택
		imgSrcList = "";
		// 사용처에 따른 브랜드 
		brand = "";
		if (jsonCpBookList[i].lmart_issue_yn == "Y") {
			imgSrc = "//simage.lottemart.com/images/front/mymart/mcoupon/badge/lottemartmall.png";
			imgSrcList += '<img src="' + imgSrc + '" alt="롯데마트몰">\n';
			brand += "롯데마트";
		}
		if (jsonCpBookList[i].tru_issue_yn == "Y") {
			imgSrc = "//simage.lottemart.com/images/front/mymart/mcoupon/badge/toysrus.png";
			imgSrcList += '<img src="' + imgSrc + '" alt="토이저러스">\n';
			brand += ", 토이저러스";
		}
		if (jsonCpBookList[i].vic_issue_yn == "Y") {
			imgSrc = "//simage.lottemart.com/images/front/mymart/mcoupon/badge/vicmarket.png";
			imgSrcList += '<img src="' + imgSrc + '" alt="빅마켓">\n';
			brand += ", 빅마켓";
		}
		
		// 맞춤 쿠폰북 및 이벤트 쿠폰북 아이콘 설정
		if(jsonCpBookList[i].cpbook_list_type == 'EVNT'){
			type = "e-c-book";
		}else{
			type="c-book";
		}
		
		// 쿠폰 발생 사유 설정 : 이벤트 쿠폰인 경우만 보인다.
		causeCoupon = "[" + jsonCpBookList[i].issue_chanel_cd_name + "] " + jsonCpBookList[i].evnt_nm;
				
		// 임시로 비어있는 이미지를 사용한다. 향후 jsonCpBookList[i].prvw_url 이미지가 있으면 주석 삭제
		if (jsonCpBookList[i].prvw_url == null || jsonCpBookList[i].prvw_url == "") 
			//|| jsonCpBookList[i].prvw_url.indexOf("\\") > 0)
			jsonCpBookList[i].prvw_url = "//simage.lottemart.com/images/front/mymart/mcoupon/no_image.jpg";
		
		cbList = '<li class="' + position[i % 2] + '"onclick="javascript: moveCouponListPage(' + i + ');">' +
			'<div class="img-area">' +
					'<img src="' + jsonCpBookList[i].prvw_url + '" alt="' + jsonCpBookList[i].cpbook_nm + '">' +
			'</div>' +
			'<div class="cp_publisher">' + imgSrcList +	'</div>' +
			'<div class="cp_info">' +
					'<span class="title">' + jsonCpBookList[i].cpbook_nm + '</span>' +
					'<span class="period">' + period + ' <span class="place gray">' + jsonCpBookList[i].issue_chanel_cd_name + '</span></span>' +
					'<span class="brand">브랜드 : ' + brand + '</span>' + 
					'<span class="branch">사용가능지점 : ' + jsonCpBookList[i].use_psbt_excp_brnch_content + '</span>' + 
					(jsonCpBookList[i].evnt_nm != undefined ? '<span class="branch">' + causeCoupon + '</span>' : "") +  
					'<div class = "cbook-type '+ type +'"></div>'+
			'</div>' +
		'</li>';
		
		$('#couponbook_list').append(cbList);
	}
	
	$('.img-area').find('img').bind('error', function (e) {
	    $(this).attr('src', '//simage.lottemart.com/images/front/mymart/mcoupon/no_image.jpg');
	});
}

// 쿠폰북 상세정보 : 쿠폰 리스트 보기 화면으로 이동
function moveCouponListPage(idx) {
	// 쿠폰 리스트 가져오기
	var params = [];

	var cpBookIdMapp = jsonCpBookList[idx].cpbook_id + jsonCpBookList[idx].cpbook_mapp_digit;

	params.push({name: "rows",			   value: "10"});
	params.push({name: "page",			   value: "1"});
	params.push({name: "sidx",			   value: "CLOSE"}); // LASTEST_ISSUE
	params.push({name: "sord",			   value: "asc"});
	params.push({name: "cpbook_id_mapp",   value: cpBookIdMapp});

	strParam = MakeStringOfJsonParameters(params);

	// 유저 쿠폰 리스트
	post_to_url("selectMyScpCouponList.do", strParam);
}

/** SCP-PROJECT : SMJANG END **/