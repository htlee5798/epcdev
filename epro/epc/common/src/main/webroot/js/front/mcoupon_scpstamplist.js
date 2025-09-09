/**  SCP-PROJECT : SMJANG START
 * @FileName : mcoupon_scpstamplist.js
 * @Description : 스탬프 리스트 관리
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

var jsonStampList = null;
var causeEventStamp = null;
var curPage = 1;

// 모든 이벤트 스탬프 리스트 조회
function SearchAllEventStampList(page) {
	// 현재 페이지 설정
	curPage = page;
	
	// 이벤트 스탬프 리스트 초기화
	jsonStampList = [];

	// 조건 셀렉트 박스에서 선택된 지역 및 지점의 코드(Value)값을 가져와 검색 파라미터로 사용한다.  
	var regionName = $('#eventRegion option:selected')[0].text;
	var regionCode = $('#eventRegion option:selected').val();
	var branchName = $('#eventBranch option:selected')[0].text;
	var branchCode = $('#eventBranch option:selected').val();
	
	var sortText = $('#stampSort option:selected')[0].text;
	var sortCode = $('#stampSort option:selected').val();
	sortCode = "CLOSE";	// 현재 기본값으로 마감 임박순으로 처리한다.


	// 스탬프 리스트 가져오기
	var data = [];

	// url 및 파라미터 설정
	data.push({name: 'url',	 value: '/mypage/stamp/list'});
	data.push({name: "rows", value: "10"});		// 페이지 당 보여질 아이템 수
	data.push({name: "page", value: page});		// 현재 페이지
	data.push({name: "sidx", value: sortCode});	// 정렬 대상 (변경될 수 있음)
	data.push({name: "sord", value: "asc"});	// 정렬 형태	
	if (regionName != "전체")
		data.push({name: "regn_divn_cd", value: regionCode});
	if (branchName != "전체")
		data.push({name: "str_cd", value: branchCode});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  GetEventStampListResponse,
		timeout:  10 * 1000	// 10초
	});
}

// 스탬프 리스트 조회 응답에 따른 리스트 보이기
function GetEventStampListResponse(json, textStatus, jqXHR) {
	var totRecCnt = 0;
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			if (json.data != undefined) {
				totRecCnt = json.data.page_info.records;
				jsonStampList = json.data.stamp_list;
			}
		}
		else
			console.log(json.message);
	}

	// 페이지 설정 : curPage는 ShowCouponBookList()의 파라미터 (보여질 페이지)
	SetPageInfos(totRecCnt, curPage, "SearchAllEventStampList", ShowEventStampList);
};

// 이벤트 스탬프 리스트 보이기
function ShowEventStampList() {
	$('#stampList').empty();
	
	// 결과값 리셋, 리스트 있을 때 불필요한 padding값 삭제
	$('#result').css('padding-top','0px');
	$('#result').empty();
	
	
	// 스탬프 리스트가 비어있으면 진행하지 않는다.
	if (jsonStampList.length < 1)  {
		$("#stampList").find(".nodata").css("display", "block");
		$("#list_wrap").css("border", "2px solid #c7c7c7");
		
		// 검색 결과 없음 문구 삽입
		$("#result").show();
			// 문구 삽입에 필요한 padding 삽입
		$('#result').css('padding-top','30px');
		$("#result").html("검색하신 결과가 없습니다.");
		return;
	}
	
	// 스탬프 리스트 나타내기
	var position = ["left", "right"];
	var stampList = "", participation = "", waiting = "";
	var startDate = "", endDate = "", period = "", userCnt = 0;

	// 스탬프 리스트 정렬 : 현재 마감임박순으로 정렬 (by JSM)
	var sortText = $('#stampSort option:selected')[0].text;
	if (sortText == "최신순")
		jsonStampList = sortJSONbyField(jsonStampList, "evt_term_start_dd", false);
	//else if (sortText == "인기순")
	//	jsonStampList = sortJSONbyField(jsonStampList, "stamp_psbt_user_cnt", false, "number");
	
	// jsonStampList 값을 순서대로 사용한다.
	for (var i = 0; i < jsonStampList.length; i++) {
		// 대기 여부
		if (jsonStampList[i].grp_aply_yn == "Y")
			waiting = '<div class="mission mission-wait"></div>';
		else
			waiting = '<div class="mission"></div>';

		// 참여 여부
		if (jsonStampList[i].grp_ptc_yn == "Y")
			participation = '<div class="user-ing"></div>';
		else
			participation = "";
		
		// 사용 가능 시작일
		startDate = setDatePeriod(jsonStampList[i].evt_term_start_dd);
		// 사용 가능 종료일
		endDate = setDatePeriod(jsonStampList[i].evt_term_end_dd);			
		// 이벤트 기간 설정
		period = startDate + " ~ " + endDate;
		
		if (jsonStampList[i].stamp_divn_cd == "GROUP")
			userCnt = "그룹(" + jsonStampList[i].stamp_psbt_user_cnt + ")";
		else
			userCnt = "개인";

		
		$("img").error(function() { 
			$(this).attr("src", "//simage.lottemart.com/images/front/mymart/mcoupon/no_image.jpg");
		});

		stampList = '<li class="' + position[i % 2] + '"onClick="javascript: GetEventStampDetailInfo(' + i + ');">' +
			'<div class="img-area">' + 
				waiting +
				participation +
					'<img src="' + jsonStampList[i].stamp_img_full_url + '" alt="스탬프 이미지">' +
			'</div>' +
			'<div class="cp_info">' +
				'<span class="title">' + jsonStampList[i].evt_title + '</span>' +
				'<span class="explain">' + jsonStampList[i].evt_desc + '</span>' +
				'<span class="period">' + period + '</span>' +
				'<div class="mark">' +
					'<span class="gray">' + userCnt + '</span>' +
					'<span class="gray">' + jsonStampList[i].use_psbt_excp_brnch_content + '</span>' +
					'<!-- 획득 스탬프가 1개 이상 존재할 경우  -->' +
					'<br>' +
					//'<span class="gray get-stamp-num">' + jsonStampList[i]. + '/' + jsonStampList[i]. + '</span>' +
				'</div>' +
				'<span class="btn_white btn_w_td_ls">' +
					'<span class="btn_td_w_01">' +
						'<a title="스탬프 상세 조회" >상세보기&gt;</a>' +
					'</span>' +
				'</span>' +
			'</div>' +
		'</li>';
		
		$('#stampList').append(stampList);
		$("#list_wrap").css("border", "none");
	}
	
	$('.img-area').find('img').bind('error', function (e) {
	    $(this).attr('src', '//simage.lottemart.com/images/front/mymart/mcoupon/no_image_s.jpg');
	});
}

//스탬프 상세정보 가져오기
function GetEventStampDetailInfo(index) {
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',		 value: '/mypage/stamp/show'});
	data.push({name: 'stamp_id', value: jsonStampList[index].stamp_id});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  GetStampDetailInfoResponse,
		timeout:   10 * 1000	// 10초
	});
}

/***** 검색 조건 정보 가져오기 *****/

// 지역 조회
function SetEventStampArea() {
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',		   value: '/mypage/common/code/list'});
	data.push({name: 'cd_divn_id', value: 'REGN_DIVN_CD'});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  SetEventStampRegionResponse,
		timeout:  10 * 1000	// 10초
	});
}

// 지역 조회 응답
function SetEventStampRegionResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var regionList = [], regions = [];
			
			if (json.data != undefined)
				regionList = json.data.cd_list;
			
			// 콤보박스에 채울 이벤트 지역 리스트
			regions.push({text:'전체', value:''});
			for (var i = 0; i < regionList.length; i++) {
				if(regionList[i].mst_cd != 10){
					regions.push({text:regionList[i].mst_cd_nm,
						  value:regionList[i].mst_cd});
				}	
			}
			
			// 콤보박스에 이벤트 지역 리스트 넣기
			$('#eventRegion').empty();
			$.each(regions, function(i, el) {
				$('#eventRegion').append(new Option(el.text, el.value));
			});
		}
		else
			console.log(json.message);
	}
};

// 지점 조회
function SetEventStampBranch() {
	var regionCode = $('#eventRegion option:selected').val();
	if (regionCode == undefined) regionCode = "";
	
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	   		 value: '/mypage/common/branch/list'});
	data.push({name: 'regn_divn_cd', value: regionCode});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success:  SetEventStampBranchResponse,
		timeout:  10 * 1000	// 10초
	});
}

// 지점 조회 응답
function SetEventStampBranchResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var branchList = [], eventBranch = [];
			
			if (json.data != undefined)
				branchList = json.data.brnch_list;
			
			// 콤보박스에 채울 이벤트 지점 리스트
			eventBranch.push({text:'전체', value:''});
			for (var i = 0; i < branchList.length; i++) {
				if(branchList[i].str_cd != 999){
					eventBranch.push({text:branchList[i].str_nm,
						  value:branchList[i].str_cd});
				}
			}
			
			// 콤보박스에 이벤트 지점 리스트 넣기
			$('#eventBranch').empty();
			var branchName = document.getElementById('eventBranch');
			$.each(eventBranch, function(i, el) {
				branchName.options.add( new Option(el.text,el.value) );
			});
		}
		else
			console.log(json.message);
	}
};

/** SCP-PROJECT : SMJANG END **/