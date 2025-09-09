/*
 * =========================================================================================
 * app.review.js
 * 관련리뷰 탭
 * 리뷰/댓글 핸들 스크립트
 * imported by appView.jsp
 * =========================================================================================
 */

// Context Path
var contextPath = "";

// 리뷰/댓글 입력 가능 byte
var limit = 300;
var showTime = 100;
/*
 * 리뷰/댓글 입력 limit 체크
 */
function checkBytes(div, upperSeq) {
	if (div == 'review') {
		var text = $('#reviewWriteBox').val();
		var len = text.getBytes();
		$('#review_byte').html(len);
		if (len > limit) {
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>리뷰는 "+limit+" bytes까지 입력가능합니다.");
			$('#reviewWriteBox').val(text.cut(limit));
			$('#review_byte').html(limit);
		}
	} else if (div == 'review2') {
		var text = $('#reviewModifyBox_'+upperSeq).val();
		var len = text.getBytes();
		if (len > limit) {
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>리뷰는 "+limit+" bytes까지 입력가능합니다.");
			$('#reviewModifyBox_'+upperSeq).val(text.cut(limit));
		}
	} else if (div == 'comment') {
		var text = $('#commentWriteBox_'+upperSeq).val();
		var len = text.getBytes();
		$('#comment_byte_'+upperSeq).html(len);
		if (len > limit) {
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>댓글은 "+limit+" bytes까지 입력가능합니다.");
			$('#commentWriteBox_'+upperSeq).val(text.cut(limit));
			$('#comment_byte_'+upperSeq).html(limit);
		}
	} else if (div == 'comment2') {
		var text = $('#commentModifyBox_'+upperSeq).val();
		var len = text.getBytes();
		if (len > limit) {
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>댓글은 "+limit+" bytes까지 입력가능합니다.");
			$('#commentModifyBox_'+upperSeq).val(text.cut(limit));
		}
	}
}


/*******************************************************************************************
 * 리뷰 스크립트
 *******************************************************************************************/
/*
 * 리스트
 */
function listReview(page) {
	if (page == 'undefined' || page == null) {
		page = 1;
	}
	var time = new Date();
	var reflesh = time.getTime() + time.getMilliseconds();
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxReviewList.do?" + reflesh + "="+reflesh,
		data: {appId:$('#appId').val(), reviewPage:page},
		dataType: "json",
		success: function(json) {
			$('#review_count').html("("+json.size+")");
			$('#review_content').empty().append(json.html);
		}
	});
}

/*
 * 리뷰작성 확인 버튼 클릭
 */
function execWriteReview() {
	var cont = $.trim($('#reviewWriteBox').val());
	cont = xssFilterStr(cont);
	if(Common.isEmpty(cont)){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>리뷰 내용을 입력해주세요.");
		return false;
	}
	if(cont.getBytes() > limit){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>리뷰는 "+limit+" bytes까지 입력가능합니다.");
		return false;
	}
	var time = new Date();
	var reflesh = time.getTime() + time.getMilliseconds();
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxReviewRegist.do?" + reflesh + "="+reflesh,
		data: {appId:$('#appId').val(), cont:cont, reviewPage:1},
		dataType: "json",
	    beforeSend: function() {
			Common.loadingDialog();
		 },
		success: function(json) {
			 Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message);
			if (json.success) {
				$('#review_count').html("("+json.size+")");
				$('#review_byte').html("0");
				$('#reviewWriteBox').val('');
				$('#review_content').empty().append(json.html);
			}
		}
	});
}

/* 
 * 수정모드전환 버튼 클릭
 */
function modifyReview(seq) {
	$('#view_area_'+seq).hide(showTime);
	$('#modify_area_'+seq).show(showTime);
}

/*
 * 수정실행 버튼 클릭
 */ 
function execModifyReview(seq) {
	//var cont = $.trim($('#reviewModifyBox').val());
	var cont = $.trim($('#reviewModifyBox_'+seq).val());
	cont = xssFilterStr(cont);
	if(Common.isEmpty(cont)){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>리뷰 내용을 입력해주세요.");
		return false;
	}
	if(cont.getBytes() > limit){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>리뷰는 "+limit+" bytes까지 입력가능합니다.");
		return false;
	}
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxReviewModify.do",
		data: {appId:$('#appId').val(), seq:seq, cont:cont},
		dataType: "json",
	    beforeSend: function() {
			Common.loadingDialog();
		 },
		success: function(json) {
			 Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message);
			if (json.success) {
				$('#review_unit_'+seq).html(json.html);
			}
		}
	});
}

/*
 * 수정취소 버튼 클릭
 */
function cancelModify(seq) {
	$('#modify_area_'+seq).hide(showTime);
	$('#view_area_'+seq).show(showTime);
}

/*
 * 삭제실행 버튼 클릭
 */
function deleteReview(seq) {
	var count = $('#comment_count_'+seq).html();
	if (count != null && count.length > 2) {
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>댓글이 있는 관련리뷰는 삭제할 수 없습니다.");
		return false;
	}
	Common.choiceDialog("<br/><br/>관련리뷰를 삭제하시겠습니까?", 'execDeleteReview(' + seq + ')');
}
function execDeleteReview(seq) {
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxReviewDelete.do",
		data: {currentPage:$('#reviewPage').val(), appId:$('#appId').val(), seq:seq},
		dataType: "json",
	    beforeSend: function() {
			Common.loadingDialog();
		 },
		success: function(json) {
			 Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message);
			if (json.success) {
				$('#review_count').html("("+json.size+")");
				$('#reviewWriteBox').val('');
				$('#review_content').hide().empty().append(json.html).show(showTime);
			}
		}
	});
}

/*
 * 댓글보기 버튼 클릭
 */ 
function viewComment(seq) {
	$('div[id^=modify_area_]').hide(showTime);
	$('div[id^=view_area_]').show(showTime);
	$('div[id^=commentWrap_]').hide(showTime);
	$('div[id^=commentWrap_]').parent().hide(showTime);
	if ($('#commentWrap_'+seq).css('display') == 'none') {
		//$('#commentHeader_'+seq).show();
		$('#commentWrap_'+seq).parent().show();
		$('#commentWrap_'+seq).show(showTime);
	} else {
		$('#commentWrap_'+seq).hide(showTime);
		$('#commentWrap_'+seq).parent().hide(showTime);
	}
	ajaxCommentListCall(seq, 1);
}

function ajaxReviewListCall(page) {
	listReview(page);
}

/*******************************************************************************************
 * 댓글 스크립트
 *******************************************************************************************/

function ajaxCommentListCall(upperSeq, page) {
	if (page == 'undefined' || page == null) {
		page = 1;
	}
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxCommentList.do",
		data: {commentPage:page, appId:$('#appId').val(), upperSeq:upperSeq},
		dataType: "json",
		success: function(json) {
			$('#commentList_'+upperSeq).empty().append(json.html);
		}
	});
}

/*
 * 댓글작성 확인 버튼 클릭
 */ 
function writeComment(upperSeq) {
	var cont = $.trim($('#commentWriteBox_'+upperSeq).val());
	cont = xssFilterStr(cont);
	if(Common.isEmpty(cont)){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>댓글 내용을 입력해주세요.");
		return false;
	}
	if(cont.getBytes() > limit){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>댓글은 "+limit+" bytes까지 입력가능합니다.");
		return false;
	}
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxCommentRegist.do",
		cache: false,
		data: {commentPage:1, appId:$('#appId').val(), upperSeq:upperSeq, cont:cont},
		dataType: "json",
	    beforeSend: function() {
			Common.loadingDialog();
		 },
		success: function(json) {
			 Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message);
			if (json.success) {
				if (json.size > 0) {
					$('#comment_count_'+upperSeq).html("("+json.size+")");
				} else {
					$('#comment_count_'+upperSeq).html("");
				}
				$('#comment_byte_'+upperSeq).html('0');
				$('#commentWriteBox_'+upperSeq).val('');
				//$('#commentHeader_'+upperSeq).hide();
				$('#commentList_'+upperSeq).hide().empty().append(json.html).show(showTime);
			}
		}
	});
}

/*
 * 삭제실행 버튼 클릭
 */
function deleteComment(seq) {
	Common.choiceDialog("<br/><br/>댓글을 삭제하시겠습니까?", 'execDeleteComment(' + seq + ')');
}
function execDeleteComment(seq) {
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxCommentDelete.do",
		data: {commentPage:$('#commentPage').val(), appId:$('#appId').val(), seq:seq},
		dataType: "json",
	    beforeSend: function() {
			Common.loadingDialog();
		 },
		success: function(json) {
			 Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message);
			if (json.success) {
				var upperSeq = json.upperSeq;
				if (json.size > 0) {
					$('#comment_count_'+upperSeq).html("("+json.size+")");
				} else {
					$('#comment_count_'+upperSeq).html("");
				}
				$('#commentWriteBox_'+upperSeq).val('');
				$('#commentList_'+upperSeq).hide().empty().append(json.html).show(showTime);
			}
		}
	});
}

/*
 * 수정모드전환 버튼 클릭
 */
function modifyComment(seq) {
	$('#comment_view_area_'+seq).hide(showTime);
	$('#comment_modify_area_'+seq).show(showTime);
}

/*
 * 수정실행 버튼 클릭
 */
function execModifyComment(seq) {
	var cont = $.trim($('#commentModifyBox_'+seq).val());
	cont = xssFilterStr(cont);
	if(Common.isEmpty(cont)){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>댓글 내용을 입력해주세요.");
		return false;
	}
	if(cont.getBytes() > limit){
		Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>댓글은 "+limit+" bytes까지 입력가능합니다.");
		return false;
	}
	$.ajax({
		type: "GET",
		cache: false,
		url: contextPath + "/app/ajaxCommentModify.do",
		data: {commentPage:$('#commentPage').val(), appId:$('#appId').val(), seq:seq, cont:cont},
		dataType: "json",
	    beforeSend: function() {
			Common.loadingDialog();
		 },
		success: function(json) {
			 Common.removeDialog();
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/>"+json.message);
			if (json.success) {
				$('#comment_unit_'+seq).hide().html(json.html).show(showTime);
			}
		}
	});
}

/*
 * 수정취소 버튼 클릭
 */
function cancelComment(seq) {
	$('#comment_modify_area_'+seq).hide(showTime);
	$('#comment_view_area_'+seq).show(showTime);
}
