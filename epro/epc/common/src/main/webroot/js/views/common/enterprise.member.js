$.view = {};

(function(win, $){
	
	var enterpriseMember = {
		
		submitForm : function($form) {
			var _this = this;
			
			$('#authEmail').val($.utils.concatEmailInput($('#emailId'), _this.elements.$inputEmailDomain, _this.elements.$selectEmailDomain));
			
			_this.elements.$form.submit();
		},
		
		checkBusinessLicenseNumber : function() {
			var _this = this;
			
			var $businessLicenseNumber = _this.elements.$businessLicenseNumber;
			if($businessLicenseNumber.validator().validate()) {
				$.api.get({
					url : '/api/enterprises/' + $businessLicenseNumber.val() + '.do',
					data : {
						type : 'businessLicenseNumber',
					},
					successCallback : function(res) {
						var isValid = res && res.enterprise
						  , enterprise = isValid ? res.enterprise : { };
						
						_this.setBusinessLicenseNumberCheckResultMessage(isValid, enterprise);
						
						_this.setTargetValue(isValid, _this.elements.$enterpriseId, enterprise.id);
						_this.setTargetValue(isValid, $businessLicenseNumber, $businessLicenseNumber.val());
						
						_this.resetBusinsessLicenseNumberArea(!isValid);
					}
				});
			}
		},
		
		setBusinessLicenseNumberCheckResultMessage : function(isValid, enterprise) {
			var $resultMessage = this.elements.$resultMessage;
			
			if(isValid) {
				$resultMessage.removeClass('error');
				$resultMessage.children().text('[기업명] ' + enterprise.name);
			} else {
				$resultMessage.addClass('error');
				$resultMessage.children().text('등록되지 않은 사업자 번호입니다. 사업자 번호를 확인해주세요.');
			}
		},
		
		setTargetValue : function(isValid, $target, validValue) {
			if(isValid) {
				$target.val(validValue);
			} else {
				$target.val('');
			}
		},
		
		setElements : function($elements) {
			this.elements = $elements;
		},
		
		// PC용
		moveToCounselForm : function() {
			var _window = opener.window || window;

			_window.location.href = '/happycenter/counsel/form.do';
		},
		
		resetBusinsessLicenseNumberArea : function(isModifyMode) {
			var $btnModifyBusinessLicenseNumber = this.elements.$btnModifyBusinessLicenseNumber
			  , $btnCheckBusinessLicenseNumber = this.elements.$btnCheckBusinessLicenseNumber
			  , $businessLicenseNumber = this.elements.$businessLicenseNumber;
			
			if(isModifyMode) {
				$btnModifyBusinessLicenseNumber.hide();
				$btnCheckBusinessLicenseNumber.show();
				
				$businessLicenseNumber.removeAttr('readonly');
			} else {
				$btnModifyBusinessLicenseNumber.show();
				$btnCheckBusinessLicenseNumber.hide();
				
				$businessLicenseNumber.attr('readonly', 'readonly');
			}
		}
			
	};
	
	$.view = { 
		enterpriseMember : enterpriseMember 
	};
	
})(window, jQuery);
