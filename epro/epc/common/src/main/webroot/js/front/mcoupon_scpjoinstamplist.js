/**  SCP-PROJECT : SMJANG START
 * @FileName : mcoupon_scpjoinstamplist.js
 * @Description : 내 스탬프(현재 진행중(참여) 및 이력) 관리
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

var jsonMyCurStampList = null;
var jsonMyOldStampList = null;
var causeMyStamp = null;
var curPage = 1;

// 내 스탬프 리스트 조회
function SearchAllMyStampList(page) {
	SearchMyCurStampList();
	SearchMyOldStampList(page);
}

// 진행중인 내 스탬프 정보 조회  : 최대 4개 가능
function SearchMyCurStampList() {
	// 진행중인 내 스탬프 리스트 초기화
	jsonMyCurStampList = [];
	
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	   value: '/mypage/stamp/mystamp/list'});
	
	// 내 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  GetMyCurStampListResponse,
		timeout:  10 * 1000	// 10초
	});
}

// 내 스탬프 리스트 조회 응답에 따른 리스트 보이기
function GetMyCurStampListResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			if (json.data != undefined) {
				jsonMyCurStampList = json.data.user_stamp_list;
			}
		}
		else
			console.log(json.message);
	}

	// 내가 참여중인 스탬프 조회
	ShowMyCurStampList();
}

// 내 스탬프 리스트 조회 : 진행중
function ShowMyCurStampList() {
	// 내 스탬프 리스트가 비어있으면 진행하지 않는다.
	if (jsonMyCurStampList.length < 1) {
		$('#myCurStamp .nodata').css('display', 'block');
		$('#list_wrap .list_event').css("border", "2px solid #c7c7c7");
		$('#myCurStamp .left').css('display', 'none');
		$('#myCurStamp .right').css('display', 'none');
		return;
	}
	
	$('#myCurStamp').empty();

	$('#myCurStamp .nodata').css('display', 'none');
	$('#list_wrap.list_event').css("border", "none");
	$('#myCurStamp .left').css('display', 'block');
	$('#myCurStamp .right').css('display', 'block');

	// 내 스탬프 리스트 나타내기 
	var position = ["left", "right"];
	var stampList = "", stampImgUrl = "";
	var participation = "", startDate = "", endDate = "", period = "", userCnt = 0, waiting="";

	// 내 스탬프 리스트 정렬 : 현재 마감임박순으로 정렬 (by JSM)
	jsonMyCurStampList = sortJSONbyField(jsonMyCurStampList, "evt_term_end_dd", true);
	
	// jsonMyCurStampList 값을 순서대로 사용한다.
	for (var i = 0; i < jsonMyCurStampList.length; i++) {
		
		// 대기 여부
		if (jsonMyCurStampList[i].prgs_cd == "STANDBY")
			waiting = '<div class="mission mission-wait"></div>';
		else
			waiting = "";
		
		// 참여 여부
		if (jsonMyCurStampList[i].grp_ptc_yn == "Y")
			participation = '<div class="user-ing"></div>';
		else
			participation = "";
		
		// 사용 가능 시작일
		startDate = setDatePeriod(jsonMyCurStampList[i].evt_term_start_dd);
		// 사용 가능 종료일
		endDate = setDatePeriod(jsonMyCurStampList[i].evt_term_end_dd);
		// 이벤트 기간 설정
		period = startDate + " ~ " + endDate;
		
		if (jsonMyCurStampList[i].stamp_divn_cd == "GROUP")
			userCnt = "그룹"; //(" + jsonMyCurStampList[i].stamp_recp_tot_cnt + ")";
		else
			userCnt = "개인";
		
		if (jsonMyCurStampList[i].stamp_img_full_url == undefined)
			stampImgUrl = GetStampImgUrl(jsonMyCurStampList[i].stamp_id);
		else
			stampImgUrl = jsonMyCurStampList[i].stamp_img_full_url;
			
		stampList = '<li class="' + position[i % 2] + '"onClick="javascript: GetMyCurStampDetailInfo(' + i + ')">' +
			'<div class="img-area">' + waiting +
				'<div class="user-ing"></div>' +
				'<img src="' + stampImgUrl + '" alt="내 스탬프이미지">' +
			'</div>' +
			'<div class="cp_info">' +
				'<span class="title">' + jsonMyCurStampList[i].evt_title + '</span>' +
				'<span class="explain">' + jsonMyCurStampList[i].evt_desc + '</span>' +
				'<span class="period">' + period + '</span>' +
				'<div class="mark">' +
					'<span class="gray">' + userCnt + '</span>' +
					'<span class="gray">' + jsonMyCurStampList[i].use_psbt_excp_brnch_content + '</span>' +
					'<!-- 획득 내 스탬프가 1개 이상 존재할 경우  -->' +
					'<br>' +
					'<span class="gray get-stamp-num">' + jsonMyCurStampList[i].stamp_seal_tot_cnt + '/' + jsonMyCurStampList[i].stamp_recp_tot_cnt + '</span>' +
				'</div>' +
				'<span class="btn_white btn_w_td_ls">' +
					'<span class="btn_td_w_01">' +
						'<a title="스탬프 상세 조회" >상세보기&gt;</a>' +
					'</span>' +
				'</span>' +
			'</div>' +
		'</li>';

		$('#myCurStamp').append(stampList);
	}
	
	$('.img-area').find('img').bind('error', function (e) {
	    $(this).attr('src', '//simage.lottemart.com/images/front/mymart/mcoupon/no_image_s.jpg');
	});
}

// 스탬프 상세정보 가져오기 
function GetMyCurStampDetailInfo(index) {
	var data = [];

	// url 및 파라미터 설정
	data.push({name: 'url',		 value: '/mypage/stamp/show'});
	data.push({name: 'stamp_id', value: jsonMyCurStampList[index].stamp_id});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success: GetStampDetailInfoResponse,
		timeout: 10 * 1000	// 10초
	});
}

/***** 종료 이벤트 스탬프 *****/

// 내가 참여한 스탬프(종료) 리스트
function SearchMyOldStampList(page) {
	// 내가 참여한 스탬프(종료) 리스트 초기화
	jsonMyOldStampList = [];
	
	// 현재 페이지 설정
	curPage = page;
	
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	 value: '/mypage/stamp/myhistory/list'});
	data.push({name: "rows", value: "10"});
	data.push({name: "page", value: page});
	data.push({name: "sidx", value: "CLOSE"});
	data.push({name: "sord", value: "asc"});

	// 내 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		async:	  false,
		success:  GetMyOldStampListResponse,
		timeout:  10 * 1000	// 10초
	});
}

//내 스탬프 리스트 조회 응답에 따른 리스트 보이기
function GetMyOldStampListResponse(json, textStatus, jqXHR) {
	// 조회 성공
	var totRecCnt = 0;
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			if (json.data != undefined) {
				totRecCnt = json.data.page_info.records;
				jsonMyOldStampList = json.data.user_stamp_old_list;
			}
		}
		else
			console.log(json.message);
	}

	// 페이지 설정 : curPage는 ShowCouponBookList()의 파라미터 (보여질 페이지)
	SetPageInfos(totRecCnt, curPage, "SearchMyOldStampList", ShowMyOldStampList);
}

function ShowMyOldStampList() {
	// 내 스탬프 리스트가 비어있으면 진행하지 않는다.
	if (jsonMyOldStampList.length < 1) {
		$('#myOldStamp .nodata').css('display', 'block');
		$('#list_wrap_old .list_event').css("border", "2px solid #c7c7c7");
		$('#myOldStamp .left').css('display', 'none');
		$('#myOldStamp .right').css('display', 'none');
		return;
	}
	
	$('#myOldStamp').empty();

	$('#myOldStamp .nodata').css('display', 'none');
	$('#list_wrap_old.list_event').css("border", "none");
	$('#myOldStamp .left').css('display', 'block');
	$('#myOldStamp .right').css('display', 'block');

	// 내 스탬프 리스트 나타내기
	var position = ["left", "right"];
	var stampList = "", stampImgUrl = "";
	var mission = "", startDate = "", endDate = "", period = "", userCnt = 0;

	// 내 스탬프 리스트 정렬 : 현재 마감임박순으로 정렬 (by JSM)
	jsonMyOldStampList = sortJSONbyField(jsonMyOldStampList, "evt_term_end_dd", true);
	
	// jsonMyOldStampList 값을 순서대로 사용한다.
	for (var i = 0; i < jsonMyOldStampList.length; i++) {
	
		// 달성 vs 미달성
		if (jsonMyOldStampList[i].stamp_prgs_cd_name == "달성")
			mission = '<div class="mission mission-complete"></div>';
		else
			mission = '<div class="mission mission-fail"></div>';
		
		// 사용 가능 시작일
		startDate = setDatePeriod(jsonMyOldStampList[i].evt_term_start_dd);
		// 사용 가능 종료일
		endDate = setDatePeriod(jsonMyOldStampList[i].evt_term_end_dd);
		// 이벤트 기간 설정
		period = startDate + " ~ " + endDate;
		
		if (jsonMyOldStampList[i].stamp_psbt_user_cnt != "" && jsonMyOldStampList[i].stamp_psbt_user_cnt > 1)
			userCnt = "그룹(" + jsonMyOldStampList[i].stamp_psbt_user_cnt + ")";
		else
			userCnt = "개인";
		
		if (jsonMyOldStampList[i].stamp_img_full_url == undefined)
			stampImgUrl = GetStampImgUrl(jsonMyOldStampList[i].stamp_id);
		else
			stampImgUrl = jsonMyOldStampList[i].stamp_img_full_url;
	
		stampList = '<li class="' + position[i % 2] + '"onClick="javascript: GetMyOldStampDetailInfo(' + i + ')">' +
			'<div class="img-area">' + mission +
				'<img src="' + stampImgUrl + '" alt="내 스탬프이미지">' +
			'</div>' +
			'<div class="cp_info">' +
				'<span class="title">' + jsonMyOldStampList[i].evt_title + '</span>' +
				'<span class="explain">' + jsonMyOldStampList[i].evt_desc + '</span>' +
				'<span class="period">' + period + '</span>' +
				'<div class="mark">' +
					'<span class="gray">' + userCnt + '</span>' +
					'<span class="gray">' + jsonMyOldStampList[i].use_psbt_excp_brnch_content + '</span>' +
					'<!-- 획득 내 스탬프가 1개 이상 존재할 경우  -->' +
					'<br>' +
					'<span class="gray get-stamp-num">' + jsonMyOldStampList[i].stamp_seal_tot_cnt + '/' + jsonMyOldStampList[i].stamp_recp_tot_cnt + '</span>' +
				'</div>' +
				'<span class="btn_white btn_w_td_ls">' +
					'<span class="btn_td_w_01">' +
						'<a title="스탬프 상세 조회" >상세보기&gt;</a>' +
					'</span>' +
				'</span>' +
			'</div>' +
		'</li>';
		
		$('#myOldStamp').append(stampList);
	}
	
	$('.img-area').find('img').bind('error', function (e) {
	    $(this).attr('src', '//simage.lottemart.com/images/front/mymart/mcoupon/no_image_s.jpg');
	});
}

//스탬프 상세정보 가져오기
function GetMyOldStampDetailInfo(index) {
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	   	 value: '/mypage/stamp/show'});
	data.push({name: 'stamp_id', value: jsonMyOldStampList[index].stamp_id});
	data.push({name: 'old_stamp_card_id', value: jsonMyOldStampList[index].stamp_card_id});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  GetStampDetailInfoResponse,
		timeout:  10 * 1000	// 10초
	})
	
	// 내가 참여한 스탬프(종료)
	.done(function(){
		
		// '미션 달성시 다양한 혜택 제공' 화면 제거
		$('#stampStatus').removeClass('info');
		$('#stampStatus').parent().css('display', 'none');
		
		// 종료된 스탬프일 시 참여멤버 아예 안 뜸
		$('#GroupMemId').empty();
	});
}


/** SCP-PROJECT : SMJANG END **/