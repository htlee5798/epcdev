/**
 * validation 기본 옵션
 */
var validateDefaultOption = {
	focusInvalid 	: false,
	onkeyup			: false,
	onfocusout		: false,
	onclick			: false,
	errorContainer 	: "<ul id='errorDiv' />",
	wrapper			: "li",
	showErrors		: function(errorMap, errorList) {
		var container = this.containers;
		var ul = this.addWrapper(container);
		
		$.fx.speeds._default = 300;
		if (errorList.length > 0) {  
			ul.html("");
		    for (var name in errorList){
		    	//var li = $("<li style='font-size:11px;'>").html(errorList[name].message).appendTo(ul);
		    	
		    	$("#"+errorList[name].element.name).focus();
		    	alert(errorList[name].message);
		    	return;
			}
		    
			ul.dialog({
				title	: "아래의 항목을 확인해주세요",
				modal	: true,
				resizable	: false,
				buttons	: {"확인": function() { $(this).dialog("close");}}
			});
		}
	},
	submitHandler: function(form) {
		$.ajax({
			url			: form.action,
			type		: form.method,
			data 		: $(form).serialize(),
			dataType	: 'json',
			onsubmit	: true,
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				var validator = $.data(form, 'validator');
				
				if (data.resultCode == 0) {
					if (validator.settings.onSuccess)
						validator.settings.onSuccess(data);
				} else {
					alert(data.resultMsg);
					if (validator.settings.onFail)
						validator.settings.onFail(data);
				}
			}
		});
	}
};

jQuery.extend(jQuery.validator.messages, {
	required	: $.validator.format("{1} 항목은 필수입니다."),
	remote		: "Please fix this field.",
	email		: $.validator.format("{1} 항목에 유효한 이메일 주소를 입력하세요."),
	url			: $.validator.format("{1} 항목에  유효한 URL을 입력하세요."),
	date		: $.validator.format("{1} 항목에 유효한 날짜를 입력하세요."),
	dateISO		: $.validator.format("{1} 항목에 유효한 날짜(ISO)를 입력하세요."),
	number		: $.validator.format("{1} 항목에 유효한 숫자를 입력하세요."),
	digits		: $.validator.format("{1} 항목에 숫자만 입력하세요."),
	creditcard	: "유효한 credit card숫자를 입력하세요.",
	equalTo		: $.validator.format("{1} 항목에 값을 확인하세요."),
	accept		: "유효한 확장자가 아닙니다.",
	maxlength	: $.validator.format("{1} 항목은 최대 {0}글자까지 입력가능합니다."),
	minlength	: $.validator.format("{1} 항목은 최소 {0}글자까지 입력가능합니다."),
	rangelength	: $.validator.format("{2} 항목은 {0}에서 {1}글자까지 입력가능합니다."),
	range		: $.validator.format("{2} 항목은 {0}에서 {1}까지 입력가능합니다."),
	max			: $.validator.format("{1} 항목은 {0} 이하의 수를 입력하세요."),
	min			: $.validator.format("{1} 항목은 {0} 이상의 수를 입력하세요.")
});
