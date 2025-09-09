/* ajax global function */
$(document).ajaxError(function ajaxErrorHandler(event, xhr, ajaxOptions, thrownError) {
	
	
	//console.log('[jquery.handler.js] ajaxError!');
	
	$.unblockUI();
	GLOBAL_AJAX_EXCEPTION_STATUSCODE = xhr.status;
	//console.log('GLOBAL_AJAX_EXCEPTION_STATUSCODE : ' + GLOBAL_AJAX_EXCEPTION_STATUSCODE);

	/* Interceptor 에서 not Session 에 걸린 경우
		- xhr.status :9999,  xhr.statusText: OK, ajaxOptions:Object, thrownError:OK
		그외 Exception
		- xhr.status:500,  xhr.statusText: Internal Server Error, ajaxOptions:Object, thrownError:Internal Server Error
	*/
	/*
	console.log('--------[console start]----------------------------');
	console.log('xhr.status : '+xhr.status);
	console.log('ajaxOptions : '+ajaxOptions);
	console.log('thrownError : '+thrownError);
	console.log('event : '+event);
	console.log('--------[console end]----------------------------');
	*/
	//message
	//if(xhr.status == '9999')
	//alert(xhr.status);
	//alert(xhr.status.length);
	if(xhr.status == '9999'){
		alert("데이터 처리에 실패하였습니다.");
		
		return false;
	}else if(xhr.status == '401'){
		const error_page_401 = "/epc/main/sessionOut.do";
		
		if(opener) {
			opener.parent.location.href = error_page_401;
			self.close();
		}else{
			parent.location.href = error_page_401;
		}
	}else{
		// 그외 에러코드 
		var eleHtml = [], h= -1;
		//alert('[jquery.handler.js] ajaxError start \n xhr.status : ' + xhr.status + '\n xhr.statusText :' + xhr.statusText);
		
		alert("데이터 처리에 실패하였습니다.");
		
		//return false;
	}
	//return true;	
});

$(document).ajaxStart(function() {
	//  alert('[jquery.handler.js] ajaxStart!');
	GLOBAL_AJAX_EXCEPTION_STATUSCODE = '';
	$.blockUI({
			message: '<img src="/images/common/etc/bu_loading.gif"/>' 
			, overlayCSS: { background: 'transparent'}
			, css:{background: 'transparent', border:'none'}
	});
});

var GLOBAL_AJAX_EXCEPTION_STATUSCODE = '';

$(document).ajaxStop(function() {
//	console.log('[jquery.handler.js] ajaxStop!');
	if(GLOBAL_AJAX_EXCEPTION_STATUSCODE == '') {
		$.unblockUI();
	}
});

