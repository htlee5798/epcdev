/**  SCP-PROJECT : SMJANG START
 * @FileName : mcoupon_scpstampdetail.js
 * @Description : 스탬프 상세정보(팝업창) 관리
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

//스탬프 상세정보 가져오기 응답 : 스탬프 팝업
function GetStampDetailInfoResponse(json, textStatus, jqXHR) {
	
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var jsonDetail = json.data;
			if (jsonDetail == "") {
				console.log("스탬프 상세정보가 존재하지 않습니다.");
				return;				
			}
			var stampType = jsonDetail.stamp_divn_cd_name;
			var stampTitle = jsonDetail.evt_title;
			var stampGroupId = jsonDetail.stamp_card_id;
			//var FulfillChannel = '미션수행채널 : <span class="gray">' + jsonDetail.fulfill_chanel_cd_name + '</span>';
			var UseStore = '지점 : ' + jsonDetail.use_psbt_excp_brnch_content;
			var groupMinNum = "", CreateDate = "";

			// 이벤트 기간 설정
			var startDate = setDatePeriod(jsonDetail.evt_term_start_dd);
			var endDate = setDatePeriod(jsonDetail.evt_term_end_dd);
			var EventPeriod = '기간 : ' + startDate + " ~ " + endDate; 

			$("#LimitCount").empty();
			$("#LimitCount1").empty();
			$('#groupMinNum').css('display', 'none');
			if (jsonDetail.stamp_divn_cd == "GROUP") {
				$('#stampType').html("M스탬프 - 함께하기");
				$('#groupMinNum').css('display', 'block');
				
				if (jsonDetail.group_reg_id != undefined && jsonDetail.group_reg_id != null)
					CreateDate = '그룹개설일 : ' + jsonDetail.group_reg_id.substring(0, 10).replace(/-/g, '.');
					groupMinNum += '정원 : ' ;
					//if(jsonDetail.stamp_card_issue_cnt > 0) onclick="javascript: GetGroupMembersInfo(\'' + jsonDetail.stamp_card_id + '\'); return false;"
						groupMinNum += '<a  href="#" id ="GroupMemId"  onclick="javascript: GetGroupMembersInfo(\'' + jsonDetail.stamp_id + '\',\'' + jsonDetail.stamp_card_id + '\'); return false;"  >'+ jsonDetail.stamp_psbt_user_cnt + '명 중 ' + jsonDetail.stamp_card_issue_cnt + '명 참여</a> <em class="txt-guide">* 신청자 포함 멤버 2인부터 스템프 날인</em> '
					 ;

			}
			else {
				$('#stampType').html("M스탬프 - 혼자하기");
			}
			
			// 멤버리스트 팝업에 삽입
			/*$('#sim_stampTitle').html(stampTitle);
			$('#sim_stampDesc').html(jsonDetail.evt_desc);
			$('#sim_eventPeriod').html(EventPeriod);
			$('#sim_images').html('<img src="' + jsonDetail.stamp_img_full_url + '"alt='+"스탬프이미지"+' class="thumb">');
			$('#sim_UseStore').html(jsonDetail.use_psbt_excp_brnch_content);
			var group = jsonDetail.stamp_divn_cd;
			if(group =='GROUP'){
				$('#sim_group').html("그룹("+jsonDetail.stamp_psbt_user_cnt+")");
			}*/
			
			// 상세정보 팝업에 삽입
			//$('#stampTitle').html(stampTitle);
			$('#stampGroupId').html(stampGroupId);
			$('#CreateDate').html(CreateDate);
			//$('#FulfillChannel').html(FulfillChannel);
			$('#UseStore').html(UseStore);
			$('#EventPeriod').html(EventPeriod);
			$('#groupMinNum').html(groupMinNum);

			var d = new Date();
			var today = d.getFullYear() + "." + (d.getMonth() + 1) + "." + d.getDate(); 
			
			if (jsonDetail.stamp_spc_seal_cnt == undefined || jsonDetail.stamp_spc_seal_cnt == "")
				jsonDetail.stamp_spc_seal_cnt = 0;
			else
				jsonDetail.stamp_spc_seal_cnt = parseInt(jsonDetail.stamp_spc_seal_cnt);
			if (jsonDetail.stamp_bsis_seal_cnt == undefined || jsonDetail.stamp_bsis_seal_cnt == "")
				jsonDetail.stamp_bsis_seal_cnt = 0;
			else
				jsonDetail.stamp_bsis_seal_cnt = parseInt(jsonDetail.stamp_bsis_seal_cnt);
		
			var doneStampCount = jsonDetail.stamp_spc_seal_cnt + jsonDetail.stamp_bsis_seal_cnt; 
			var totStampCount = jsonDetail.stamp_spc_cnt + jsonDetail.stamp_bsis_cnt; 
			
			// d-day에 따른 표시 설정
			var day = getDateDiff(endDate, today);
			if(day < 0 ){
				var dDay = "기간종료";
				$('#dDay').addClass('end');
			}else if(day == 0){
				var day = "D-DAY";
			}else{
				var dDay = 'D-' + day;
			}
			
			// 한 행에 5개 쿠폰 표시
			var checkStamps = "";
			var count = (parseInt((jsonDetail.stamp_spc_cnt - 1) / 5) + 1) * 5;
			if (jsonDetail.stamp_spc_cnt > 0) {
				for (var i = 0; i < count; i++) {
					if (i < jsonDetail.stamp_spc_seal_cnt)
						checkStamps += '<li class="special-stamp-item check"></li>';
					else if (i < jsonDetail.stamp_spc_cnt)
						checkStamps += '<li class="special-stamp-item "></li>';
					else
						checkStamps += '<li></li>';				
				}
			}
			count = (parseInt((jsonDetail.stamp_bsis_cnt - 1) / 5) + 1) * 5;
			if (jsonDetail.stamp_bsis_cnt > 0) {
				for (var i = 0; i < count; i++) {
					if (i < jsonDetail.stamp_bsis_seal_cnt)
						checkStamps += '<li class="m-stamp-item check"></li>';
					else if (i < jsonDetail.stamp_bsis_cnt)
						checkStamps += '<li class="m-stamp-item"></li>';
					else
						checkStamps += '<li></li>';				
				}
			}
			
			var rewards = "";
			for (var i = 0; i < jsonDetail.reward_cpbook_list.length; i++) {
				rewards += '<li>스탬프 ' + jsonDetail.reward_cpbook_list[i].stamp_ncnt + '개 : ' + jsonDetail.reward_cpbook_list[i].cpbook_nm + '</li>';
			}			
			
			$('#doneStampCount').html(doneStampCount);
			$('#totStampCount').html(totStampCount);
			$('#dDay').html(dDay);			
			$('#checkStamps').empty();
			$('#checkStamps').html(checkStamps);
			$('#rewards').empty();
			$('#rewards').html(rewards);

			$('#missions').empty();

			var missionList = jsonDetail.mission_list;
			// 특별미션 (관리자 지정 안함) / 일반 미션 (관리자가 지정)
			var specialMissions = "", normalMission = "";
			// 미션 수 만큼 반복
			for (var i = 0; i < missionList.length; i++) {
				var detailMission = "";
				// 세부 미션 설정
				if (missionList[i].stamp_apply_to_cd == "PRICE") {
					detailMission = "<div class='cont'>" + setComma(missionList[i].base_buy_amt) + "원 이상 구매</div>";
				}
				else if (missionList[i].stamp_apply_to_cd == "PRODUCT") {
					for (var j = 0; j < missionList[i].mison_prod_list.length; j++)
						detailMission += '<li><a href="${_LMAppUrl}/product/ProductDetail.do?ProductCD='+ missionList[i].mison_prod_list[j].prod_sell_cd +'" class="link">[' + missionList[i].mison_prod_list[j].prod_sell_cd + '] ' + missionList[i].mison_prod_list[j].prod_nm+ '</a></li>';
				}
				else
					detailMission = setComma(missionList[i].base_buy_amt) + "원 이상 구매";
					
				// 미션구분에 따른 표시
				
				
				if (missionList[i].bsis_mison_yn == "Y") {				
					normalMission += '<h2 class="stit-h"> 도전하기1 [' + missionList[i].stamp_apply_to_cd_name +  ']</h2>' + 
						' <em class="tag-use">' + missionList[i].fulfill_chanel_cd_name + '</em>' +
					'<div class="cont"><ul class="list">' + detailMission + '</ul></div>';
				}
				else {
					specialMissions += '<h2 class="stit-h"> 도전하기2 [' + missionList[i].stamp_apply_to_cd_name + ']</h2>' +
						'  <em class="tag-use">' + missionList[i].fulfill_chanel_cd_name + '</em>' + 
					'<div class="cont"><ul class="list">' + detailMission + '</ul></div>';
				}
			}
			
			if (normalMission != "")
				$('#missions').addClass('save-condition1');
				$('#missions').append(normalMission);
			if (specialMissions != "")
				$('#specialMissions').addClass('save-condition2');
				$('#specialMissions').append(specialMissions);
		
			// 유의 사항(\n으로 구분)
			var arrNote = jsonDetail.note_content.split('\n');
			var stampNote = "";
			
			for (var i = 0; i < arrNote.length; i++) {
				if (arrNote[i].trim() == "") continue;
				stampNote += '<li>' + arrNote[i].trim() + '</li>';
			}
			// 관리자 추가 유의 사항 (고정)
			if (jsonDetail.fulfill_chanel_cd_name.indexOf("겸용") < 0) {
				if (jsonDetail.fulfill_chanel_cd_name.indexOf("롯데마트") > -1)
					stampNote += '<li>본 쿠폰은 롯데마트 몰에서만 사용 가능합니다.</li>';
				else
					stampNote += '<li>본 쿠폰은 오프라인 매장의 모바일 앱에서만 사용 가능합니다.</li>';
			}
			
			$('#stampNote').empty();
			$('#stampNote').html(stampNote);
			
			$('#groupStampTab').css("display", "none");
			
			// 참여하지 않은 스탬프만 보이지 않음
			if (jsonDetail.grp_gen_user_nick != null) {
				
				// 스탬프 적립 내역 : 날짜 내림차순 정렬
				$('#saveInfos').empty();
				$('#saveInfos').html(GetGroupMemberSaveInfo(jsonDetail.stamp_card_id));
	
				// 보상 내역
				$('#rewardInfos').empty();
				$('#rewardInfos').html(GetGroupMemberRewardInfo(jsonDetail.stamp_card_id));
				$('#groupStampTab').css("display", "block");
			}
			
			if (jsonDetail.stamp_divn_cd == "GROUP") {	// 그룹인 경우
				if (jsonDetail.grp_aply_yn == "Y") { //승인전
					$('#waiting').css('display', 'block');
				}
				else if (jsonDetail.stamp_card_issue_cnt == 1) { //참여대기
					$('#shortage').parent().css('display', 'block');
				}
				else if (jsonDetail.grp_ptc_yn == "N") {
					$('#before').css('display', 'block');
				}
			}
			else {	// 개인인 경우
				if (jsonDetail.grp_ptc_yn == "N") {
					$('#before').parent().css('display', 'block');
				}
			}
			
			// 스탬프 상세정보 팝업창 열기 
			//openPopupStamp('myIng');
			
			//var div_height = $(".stamp-wrap").height();
			//$(".stamp-wrap").find(".msg").css( "height", div_height+"px" );
		}
		else
			console.log(json.message);
	} 
};

//스탬프 참여(적립) 내역 리스트 가져오기
function GetGroupMemberSaveInfo(stampCardId) {
	var reserveInfo = "";
	
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',			    value: '/mypage/stamp/reserve/list'});
	data.push({name: "stamp_card_id", value: stampCardId});	// 스탬프 카드 아이디
	data.push({name: "rows",		  value: "99"});		// 페이지 당 보여질 아이템 수
	data.push({name: "page",		  value: "1"});			// 현재 페이지
	data.push({name: "sidx",		  value: "reg_date"});	// 정렬 대상
	data.push({name: "sord",		  value: "desc"});		// 정렬 형태

	// 스탬프 참여(적립) 내역 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		async:	  false,
		success:  function (json, textStatus, jqXHR) {
			// 조회 성공
			if (jqXHR.readyState == 4 && jqXHR.status == 200) {
				if (CheckMessageCode(json, jqXHR)) {
					var reserveList = json.data.stamp_reserve_list;
					if (reserveList.length > 0) {
						reserveInfo = '<table><caption>스탬프보기</caption><colgroup><col style="width:23.5%"><col style="width:auto"></colgroup>' ;
						reserveInfo += '<thead><tr><th scope="col">날짜</th><th class="cell-cont" scope="col">내용</th></tr></thead>' ;
						reserveInfo += '<tbody> ';
							for (var i = 0; i < reserveList.length; i++) {
								reserveInfo += '<tr>' +
									'<td>' + reserveList[i].reg_date + '</td>' +
									'<td class="cell-cont">' + reserveList[i].str_nm + '(' + reserveList[i].user_nick + ') ' + reserveList[i].stamp_seal_cnt + '개</td>' +
								'</tr>';
							}
						reserveInfo += '</tbody></table> ';
					} else {
						$("#saveInfos").removeClass();
						reserveInfo = '<div class="wrap-no-data">' +
								'<div class="no-data-result"><p class="txt">내역이 없습니다.</p></div>' +
								'</div>';
					}
				}
				else
					console.log(json.message);
			}
		},
		timeout:  10 * 1000	// 10초
	});

	return reserveInfo;
}

// 보상 내역 리스트 객체
var rewardList = null;
//스탬프 참여(적립) 보상 내역 리스트 가져오기
function GetGroupMemberRewardInfo(stampCardId) {
	var rewardInfo = "";	
	var data = [];
	// url 및 파라미터 설정
	data.push({name: 'url',			  value: '/mypage/stamp/reward/list'});
	data.push({name: 'stamp_card_id', value: stampCardId});

	// 스탬프 참여(적립) 내역 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		async:	  false,
		success:  function (json, textStatus, jqXHR) {
			// 조회 성공
			if (jqXHR.readyState == 4 && jqXHR.status == 200) {
				if (CheckMessageCode(json, jqXHR)) {
					rewardList = json.data.stamp_reward_list;
					
					// 보상내역이 있음
					if (0 < rewardList.length) {
						var rewardType0 = "", rewardType1 = "", rewardinfo="";
						
						// 기간 만료 설정
						var d = new Date();
						var today = d.getFullYear() + "." + (d.getMonth() + 1) + "." + d.getDate(); 
						var endDate = setDatePeriod(rewardList[0].vali_term_end_dd);
						var day = getDateDiff(endDate, today);
						
						rewardInfo = '<h3 class="hidden">선물 확인</h3><div class="tbl-lst"><table><caption>선물확인</caption>'+
										'<colgroup><col style="width:15%"><col style="width:auto"><col style="width:25%"></colgroup>' +
						' <thead><tr><th scope="col">구분</th><th scope="col">내용</th><th scope="col">유효기간</th></tr></thead>'+
						'<tbody>';
						
							// 최종 보상 ----------------------------------
							/*if (rewardList[0].reward_aply_yn == "N"){	// 보상신청 x
									rewardType0 = '<div class="set-btn">' +
												  '<button type="button" class="btn-type1">쿠폰 받기</button></div>';
							}else { // 보상 신청 o
									rewardType0 = '<span class="btn-benefit btn_gray btn_g_s">' +
												  '<span><a href="javascript: moveCouponListPage(' + 0 + ')">선물상세</a></span>';
							}
							
							if(day <=0 ){
								
								//보상신청을 하지 않고, 유효기간이 지남
								if(rewardList[0].reward_aply_yn == "N"){	
									rewardType0 = '<span class="btn-benefit btn_gray btn_g_s">' +
						  			  			  '<span><input type="button" value="기간만료"></span>';
								// 보상신청을 하고, 유효기간이 지남
								}else{
									rewardType0 = '<span class="btn-benefit btn_gray btn_g_s">' +
												  '<span><a href="javascript: moveCouponListPage(' + 3 + ')">선물상세</a></span>';
								}
							}*/
						
								rewardInfo += '<tr><td>완료 미션</td>' +
										'<td>' + rewardList[0].evt_title + '</td>' +
										'<td>' + endDate + ' 까지</td>';
							// 최종보상 end -------------------------------------
							
								
						if(rewardList[1] == null){ 
							rewardinfo += "";
							
						}else{
							// 중간보상 있음 -------------------------------------
							endDate = setDatePeriod(rewardList[1].vali_term_end_dd);
							day = getDateDiff(endDate, today);
							
								// 중간 보상
								/*if (rewardList[1].reward_aply_yn == "N"){ 
									rewardType1 = '<span class="btn-benefit btn_gray btn_g_s">' +
												  '<span><input type="button" value="선물신청"></span>';
								}else{
									rewardType1 = '<span class="btn-benefit btn_gray btn_g_s">' +
										  		  '<span><a href="javascript: moveCouponListPage(' + 1 + ')">선물상세</a></span>';
								}
								
								if(day <=0 ){
									if(rewardList[1].reward_aply_yn == "N"){
										rewardType0 = '<span class="btn-benefit btn_gray btn_g_s">' +
							  			  			  '<span><input type="button" value="기간만료"></span>';
									}else{
										rewardType1 = '<span class="btn-benefit btn_gray btn_g_s">' +
													  '<span><a href="javascript: moveCouponListPage(' + 3 + ')">선물상세</a></span>';
									}
								}*/
								
							rewardInfo += '<tr><td>중간 미션</td>' +
											'<td>' + rewardList[1].evt_title + '</td>' +
											'<td>' + endDate + ' 까지</td>';
						}	// 중간보상 있음 end ------------------------------------
						
					}else{ 
						rewardInfo = '<div class="wrap-no-data"><div class="no-data-result"><p class="txt">보상내역이 없습니다.</p></div></div>';		 
						}
				}else
					console.log(json.message);
			}	// 조회 성공 end -----------------------
		},
		timeout:  10 * 1000	// 10초
	});

	return rewardInfo;
}


// 쿠폰 리스트 보기 화면으로 이동
function moveCouponListPage(idx) {
	// 쿠폰 리스트 가져오기
	var params = [];
	
	if(idx == 3){
		alert("유효기간이 만료됐습니다.");
	}else{
		var cpBookIdMapp = rewardList[idx].cpbook_id + rewardList[idx].cpbook_mapp_digit;

		params.push({name: "rows",			   value: "10"});
		params.push({name: "page",			   value: "1"});
		params.push({name: "sidx",			   value: "CLOSE"}); // LASTEST_ISSUE
		params.push({name: "sord",			   value: "asc"});
		params.push({name: "cpbook_id_mapp",   value: cpBookIdMapp});

		strParam = MakeStringOfJsonParameters(params);

		// 유저 쿠폰 리스트
		post_to_url("selectMyScpCouponList.do", strParam);
	}
}

//스탬프 그룹 멤버 정보 조회
function GetGroupMembersInfo(stampId,stampCardId) {
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',	  		  value: '/mypage/stamp/group/member/list'});
	data.push({name: 'stamp_card_id', value: stampCardId});
	
	// 스탬프 그룹 멤버 정보 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		async:    false,
		success:  GetGroupMembersInfoResponse,
		timeout:  10 * 1000	// 10초
	});
	
	GetStampInfo(stampId);
	GetGroupMembersApplicantInfo(stampCardId);
}

function GetStampInfo(stampId){
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',		 value: '/mypage/stamp/show'});
	data.push({name: 'stamp_id', value: stampId});

	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success : function (json, textStatus, jqXHR) {
			// 조회 성공
			if (jqXHR.readyState == 4 && jqXHR.status == 200) {
				if (CheckMessageCode(json, jqXHR)){			
					var jsonDetail = json.data;
						// 멤버리스트 팝업에 삽입
						var stampTitle = jsonDetail.evt_title;
						// 이벤트 기간 설정
						var startDate = setDatePeriod(jsonDetail.evt_term_start_dd);
						var endDate = setDatePeriod(jsonDetail.evt_term_end_dd);
						var EventPeriod = '기간 : ' + startDate + " ~ " + endDate; 
						
						$('#sim_stampTitle').html(stampTitle);
						$('#sim_stampDesc').html(jsonDetail.evt_desc);
						$('#sim_eventPeriod').html(EventPeriod);
						//$("#sim_images").attr("src",  jsonDetail.stamp_img_full_url);
						$('#sim_images').html('<img src="' + jsonDetail.stamp_img_full_url + '"alt='+"스탬프이미지"+' class="thumb">');
						//$('#sim_images').html('<img src="' + jsonDetail.stamp_img_full_url + '"alt='+"스탬프이미지"+'>');
						$('#sim_UseStore').html(jsonDetail.use_psbt_excp_brnch_content);
						var group = jsonDetail.stamp_divn_cd;
						if(group =='GROUP'){
							$('#sim_group').html("그룹("+jsonDetail.stamp_psbt_user_cnt+")");
						}else{
							$('#sim_group').html("개인");
						}	
				}
				else {
					console.log(json.message);
				}
			}
		}
	});
}

//스탬프 그룹 멤버 정보 조회 응답 : 팝업
function GetGroupMembersInfoResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var memberList = json.data.group_member_list;
			var stampId = json.data.stamp_id;
			
			// 그룹 멤버
			var groupMembers = "";
			
			$("#stampID").html(stampId);
			
			$('#gMemberCount').html(memberList.length);
			
			// 그룹장 찾아서 첫번째로 넣기
			for(var i=0; i<memberList.length; i++){
				if(json.data.grp_gen_rep_id == memberList[i].ptc_user_rep_id){
					groupMembers = '<tr><td><em class="ico-host">그룹 생성한 호스트</em> ' + memberList[i].user_nick + '<//td> <td class="cell-cont">' + memberList[i].reg_date + ' 참여</td></tr>';
				}
			}
			//그룹장을 뺀 나머지 멤버를 순서대로 넣기
			for(var i=0; i<memberList.length; i++){
				if(json.data.grp_gen_rep_id != memberList[i].ptc_user_rep_id){
					groupMembers += '<tr><td>' + memberList[i].user_nick + '</td> <td class="cell-cont">' + memberList[i].reg_date + ' 참여</td></tr>';
				}
			}
			$('#groupMembers').empty();
			$('#groupMembers').html(groupMembers);
		}
		else
			console.log(json.message);
	}
};

//스탬프 그룹 참여 신청자 정보 조회
function GetGroupMembersApplicantInfo(stampCardId) {	
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',			  value: '/mypage/stamp/group/applicant/list'});
	data.push({name: 'stamp_card_id', value: stampCardId});

	// 스탬프 그룹 참여 신청자 정보 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		success: GetGroupMembersApplicantInfoResponse,
		timeout: 10 * 1000	// 10초
	});
}

//스탬프 그룹 참여 신청자 정보 조회 응답 : 팝업
function GetGroupMembersApplicantInfoResponse(json, textStatus, jqXHR) {
	// 조회 성공
	if (jqXHR.readyState == 4 && jqXHR.status == 200) {
		if (CheckMessageCode(json, jqXHR)) {
			var appList = json.data.applicant_list;
			
			var applicants = "";

			// 그룹 참여 신청자			
			$('#gAppCount').html(appList.length);

			if(appList.length == '0'){
				applicants = '<div class="wrap-no-data"><div class="no-data-result"><p class="txt">참여 신청자가 없습니다.</p></div></div>';
				$('#groupAppNoMembers').html(applicants);
			}else{
				$('#groupAppMembers').empty();
				for (var i = 0; i < appList.length; i++) {
					applicants = '<tr><td>' + appList[i].user_nick + '</td> <td class="cell-cont">M쿠폰 모바일앱에서 승인 가능</td></tr>';
					$('#groupAppMembers').append(applicants);
				}
			}
			
			// 스탬프 정보 팝업창 열기 
			//openPopupStamp('gMember');
		}
		else
			console.log(json.message);
	}
};

//스탬프 이미지 URL 가져오기
function GetStampImgUrl(stampId) {
	var imgUrl = null;

	// 스탬프 리스트 가져오기
	var data = [];
	
	// url 및 파라미터 설정
	data.push({name: 'url',		 value: '/mypage/stamp/briefinfo/show'});
	data.push({name: 'stamp_id', value: stampId});
	
	// 스탬프 리스트 조회 api 호출
	$.ajax({
		type:	  'GET',
		url:	  '/restGet.do',
		data:	  data,
		dataType: 'json',
		async:    false,
		success:  function (json, textStatus, jqXHR) {
			// 조회 성공
			if (jqXHR.readyState == 4 && jqXHR.status == 200) {
				if (CheckMessageCode(json, jqXHR))			
					imgUrl = json.data.stamp_img_full_url;
				else
					console.log(json.message);
			}
		},
		dataType: 'json',
		timeout:  10 * 1000	// 10초
	});
	
	return imgUrl;
}

