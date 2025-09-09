(function($, window, document, undefined) {
	'use strict';
	
	$.fn.moveToEnterpriseMemberForm = function(options) {
		var $this = $(this);
		
		if(validate(options)) {
			window.open('/enterprise/members/form.do', 'enterpriseMemberFormPopup', 'width=640,height=755,resizable,scrollbars=yes');
		}
	};
	
	function validate(options) {
		if(!options.isLpointMember) {
			alert('기업회원 등록은 L.POINT 통합회원만 이용가능합니다. 통합회원으로 로그인 후 이용해주세요.')
			return false;
		}
		
		var isValid = false;
		$.ajax({
			url : '/api/enterprise/members/detail.do',
			async : false,
			success : function(res) {
				if(res.enterpriseMember && res.enterpriseMember.authDate) {
					if(confirm('이미 롯데마트 기업회원 등록이 완료되었습니다. 수정을 원하시면 확인 버튼을 눌러주세요.')) {
						isValid = true;	
					}
				} else {
					isValid = true;
				}
			}
		});
		
		return isValid;
	}
})( jQuery, window, document );