/**  SCP-PROJECT : sejisesang START
 * @FileName : mcoupon_scpcommon.js
 * @Description : 쿠폰 및 스탬프 세부정보 팝업창 관리
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   생성일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015.03.12  박세진      신규작성
 *
 * @Copyright (C) 2000 ~ 2015 롯데정보통신(주) All right reserved.
 * </pre>
*/


//스탬프 상세 탭 변경	
function changeTab(obj) {
	var tabid = $(obj).attr("id");	
	var classchk = $(obj).parent().parent().hasClass("active");	
	var ulid = $(obj).parent().parent().parent().attr("id");	

	//if (!classchk) {
		
		$("#" + ulid).children("li").removeClass("active");	
		$(obj).parent().parent().addClass("active");
		
			// 상세보기의 탭과 멤버리스트 탭의 remove를 구분 
			if(tabid=="StampSaveTab01" || tabid=="StampSaveTab02"){  //PC 
				$("#member").css("display", "none");
				if(tabid=="StampSaveTab01"){
					$("#StampSaveTab01List").css("display", "block");
					$("#StampSaveTab02List").css("display", "none");
				}else{
					$("#StampSaveTab01List").css("display", "none");
					$("#StampSaveTab02List").css("display", "block");
				}	
				
			} else if ( tabid == "StampSaveTab03-1" ||  tabid == "StampSaveTab03-2" ) { //PC 멤버 그룹
				$("#stampSave").css("display", "none");
				
				if(tabid=="StampSaveTab03-1"){
					$("#StampSaveTab03-1List").css("display", "block");
					$("#StampSaveTab03-2List").css("display", "none");
				}else{
					$("#StampSaveTab03-1List").css("display", "none");
					$("#StampSaveTab03-2List").css("display", "block");
				}	
				
			}else if(tabid=="mstampTab1"||tabid=="mstampTab2"){ // MO
				
				if(tabid=="mstampTab1"){
					$("#mstampTab1List").addClass("active");
					$("#mstampTab1List").css("display", "block");
					$("#mstampTab2List").css("display", "none");
				}else{
					$("#mstampTab2List").addClass("active");
					$("#mstampTab1List").css("display", "none");
					$("#mstampTab2List").css("display", "block");
				}	
				
			}else{ //MO 멤버그룹
				if(tabid=="stampMemberBoard1"){
					$("#stampMemberBoard1").css("display", "block");
					$("#stampMemberBoard2").css("display", "none");
				}else{
					$("#stampMemberBoard1").css("display", "none");
					$("#stampMemberBoard2").css("display", "block");
				}	
			}
		
		//$("#" + tabid + "List").css("display", "block");
	//}	
	
}
