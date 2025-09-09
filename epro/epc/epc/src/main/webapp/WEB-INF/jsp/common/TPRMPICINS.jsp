<%--
- Author(s): ksjeong
- Created Date: 2011. 08. 11
- Version : 1.0
- Description : 배송정보 ( 상품 )

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$("#uploadFile").click(function(){
		uploadFile();
	});
	$("#create").click(function(){
		create();
	});
});
function create() {
	$("input[name='videofile']").remove();
	var formData = new FormData($('#dataForm')[0]);
	var surl = '<c:url value="/product/insertTprMpic.do"/>';
 	$.ajax({
 		url: surl,
 		dataType : 'JSON',
 		data : formData,
 		type: 'POST',
 		processData: false,
 		contentType: false,
 		success : function(result) {
 			if (result.result ) {
 				alert(result.resultMsg);
 				self.close();
 			}else{
 				alert(result.errMsg);
 						
 			}
 		},
 		error: function(e) {
			alert(e);
		}
 	});
}

function uploadFile() {
	if($.trim($("input[name=title]").val()) == ""){
		alert("제목을 입력하세요.");
		return;
	}
	
	if($.trim($("input[name=content]").val()) == ""){
		alert("내용을 입력하세요.");
		return;
	}
	
	/* if($.trim($("input[name=videofile]").val()) == ""){
		alert("파일을 등록하세요.");
		return;
	} */
	
	loadingMask();
	var formData = new FormData($('#dataForm')[0]);
	var surl = '<c:url value="${WECANDEOINFO.uploadUrl}"/>?token=<c:url value="${WECANDEOINFO.token}"/>&folder=';

 	$.ajax({
 		url: surl,
 		dataType : 'JSON',
 		data : formData,
 		type: 'POST',
 		processData: false,
 		contentType: false,
 		success : function(result) {
 			if (result.uploadInfo.errorInfo.errorCode == "None" ) {
 				$("input[name='accessKey']").val(result.uploadInfo.uploadDetail.access_key);
 				$("input[name='duration']").val(result.uploadInfo.uploadDetail.duration);
 				$("input[name='videoHeight']").val(result.uploadInfo.uploadDetail.video_height);
 				$("input[name='videoWidth']").val(result.uploadInfo.uploadDetail.video_width);
 				$("input[name='videoFramerate']").val(result.uploadInfo.uploadDetail.video_framerate);
 			}else{
 				alert("업로드중 문제가 발생했습니다.("+result.uploadInfo.errorInfo.errorMessage+")")
 				hideLoadingMask();		
 			}
 		},
 		error: function(e) {
			alert(e);
		}
 	});
 	
 	updateInfo();
 	
}
function updateInfo(){
	$.ajax({ 
 		url: '<c:url value="/product/checkUpdateInfo.do"/>',
 		dataType : 'JSON',
 		type: 'POST',
 		success : function(data) {
 			if ( data.result ) {
 				if( data.processMap.process == 100 ){
 					$("#progress").text(data.processMap.process);
 					if( data.processMap.state == "COMPLETE" ){
 						$("input[name='status']").val(data.processMap.state);
 						
 						hideLoadingMask();
 						alert("업로드가 완료되었습니다."); 	
 						$("#uploadFile").hide();
 						$("#create").show();
 					}else{
	 					setTimeout(updateInfo, 1000);
 					}
 				}else if( data.processMap.state == "FILEOVER"){
 					$("input[name='status']").val(data.processMap.state);
 					errorState();
					hideLoadingMask();
					alert("스토리지 용량 초과가 발생하여 업로드를 중단합니다. (관리자에게 문의해주세요) "); 	
					$("#uploadFile").hide();
					$("#create").show();
 				}else if( data.processMap.state == "U_ERROR"){
 					$("input[name='status']").val(data.processMap.state);
 					errorState();
					hideLoadingMask();
					alert("파일에러가 발생하여 업로드를 중단합니다. (관리자에게 문의해주세요) "); 	
					$("#uploadFile").hide();
					$("#create").show();
 				}else{
 					$("#progress").text(data.processMap.process);
 					setTimeout(updateInfo, 1000);
 				}
 			}else{
 				alert("업로드 상태 확인 중 문제가 발생했습니다.");
 				hideLoadingMask();
 			}
 		},
 		error: function(e) {
			alert(e);
		}
 	});	
}
function loadingMask(){
	var childWidth = 128;
	var childHeight = 128;
	var childTop = (document.body.clientHeight - childHeight) / 2;
	var childLeft = (document.body.clientWidth - childWidth) / 2;
	
	var htmlTag = '<span id="loadingLayer" style="position:absolute; left:'+childLeft+'px; top:'+childTop+'px; width:100%; height:100%; z-index:10001">';
	htmlTag    += '<div style="left: 0px; top: 0px; display: block; position: absolute; z-index: 1;"><img id="ajax_load_type_img" style="width: 70px;" src="/js/jquery/res/loading.gif"></div>';
	htmlTag    += '<div style="width: 70px; height: 70px; text-align: center; vertical-align: middle; display: table-cell;" id="progress"></div>';
	htmlTag    += '</span>';
	
	var loadingDiv = $(htmlTag).appendTo($("body"));
	loadingDiv.show();
	var loadingDivBg = $('<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>').appendTo($("body"));
	loadingDivBg.show();
}



</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="dataForm" id="dataForm" method="post" enctype="multipart/form-data">
	<input type="hidden" name="folder" value="<c:out value='${folder}' />"/>
	<input type="hidden" name="pkg" value="<c:out value='${pkg}' />"/>
	<input type="hidden" name="cid" value="<c:out value='${cid}' />"/>
	<input type="hidden" name="drm_cid" value=""/>
	<input type="hidden" name="series" value=""/>
	<input type="hidden" name="author" value=""/>
	<input type="hidden" name="copyright" value=""/>
	<input type="hidden" name="rate" value=""/>
	<input type="hidden" name="tag" value=""/>
	<input type="hidden" name="String" value=""/>
	<input type="hidden" name="prodCd" value="<c:out value='${prodCd}' />"/>
	<input type="hidden" name="status" value=""/>
	
	<input type="hidden" name="accessKey" value=""/>
	<input type="hidden" name="duration" value=""/>
	<input type="hidden" name="videoHeight" value=""/>
	<input type="hidden" name="videoWidth" value=""/>
	<input type="hidden" name="videoFramerate" value=""/>

<div id="wrap_menu" style="width:556px;">
	<div class="bbs_list">
		<ul class="tit">
        	<li class="tit">동영상 등록</li>
            <li class="btn">
            	<a href="javascript:window.close();" class="btn" ><span>닫기</span></a>
            	<a href="#" class="btn" id="create" style="display:none;"><span>등록</span></a>
            </li>
    	</ul>
    	
    	<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<col width="20%" />
	        <col width=" " />
			
	        <tr>
	        	<th>제목</th>
	        	<td><input type="text" name="title" style="width:95%;"></td>
	        </tr>
	        <tr>
	        	<th>내용</th>
	        	<td><input type="text" name="content" style="width:95%;"></td>
	        </tr>
	        <tr>
	        	<th>동영상 파일</th>
	        	<td>
	        		<input type="file" name="videofile" style="width:83%;">
	        		<a href="#" class="btn" id="uploadFile"><span>업로드</span></a>
	        	</td>
	        </tr>
		</table>
	</div>
</div>	
</form>
</body>
</html>