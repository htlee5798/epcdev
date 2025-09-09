$.view = {};

(function(win, $){
	var $counselType = $('[name$=CounselType]')
	  , $smallCounselType =  $('#smallCounselType')
	  , $inputEmailDomain = $('input[name=emailDomain]')
	  , $selectEmailDomain = $('select[name=emailDomain]')
	  , $storeCode = $('#storeCode');
	
	var counsel = {
		
		// 문의 유형 세팅
		setCounselType : function(options) {
			options.$target.children().not(':first').remove();
			
			$.api.get({
				apiName : 'commonCodeList',
				data : options.params,
				successCallback : function(res) {
					if(res && res.codes) {
						res.codes.forEach(function(v, i) {
							var $option = $('<option/>').text(v.CD_NM).val(v.MINOR_CD);
							
							options.$target.append($option);	
						});
						
						if(options.afterOptionRender) {
							options.afterOptionRender(options.$target);
						}
					}
				}
			});	
		},
		
		setDefaultEmailDomainValue : function() {
			if($inputEmailDomain.val()) {
				$selectEmailDomain.val('직접입력');
			}
		},
		
		setSmallCounselType : function($largeCounselType, afterOptionRender) {
			$smallCounselType.children().not(':first').remove();
			
			if(afterOptionRender) {
				afterOptionRender($smallCounselType);
			}
			
			if($largeCounselType.val()) {
				this.setCounselType({
					$target : $smallCounselType,
					afterOptionRender : afterOptionRender,
					params : {
						majorCd : $largeCounselType.val()
					}
				});
			}
		},
		
		setLargeCounselType : function($largeCounselType, afterOptionRender) {
			this.setCounselType({
				$target : $largeCounselType,
				afterOptionRender : function($this) {
					$smallCounselType.children().not(':first').remove();

					afterOptionRender($smallCounselType);
					afterOptionRender($this);
				},
				params : {
					majorCd : 'QNA00',
					let1Ref : 'ALL',
					let2Ref : this.getCheckedReceptionType()
				}
			});
		},
		
		// 문의처 상세 정보 패널 setting 
		showReceptionDetailInfo : function(receptionType, callback) {
			var detailInfoSelector = '[name=receptionDetailInfo]';

			$(detailInfoSelector).hide();
			$(detailInfoSelector + '[data-type=' + receptionType + ']').show();
			$('#selectedItemContainer').empty();
			
			if(receptionType != 'store') {
				$storeCode.val('');
				
				if(callback) callback();
			}
		},
		
		setRequiredStoreCode : function(receptionType) {
			if(receptionType == 'store') {
				$storeCode.attr('data-required', 'required');
			} else {
				$storeCode.removeAttr('data-required');
			}
		},
		
		setRequiredEmailDomain : function($this) {
			if($this.val()) {
				$this.removeAttr('data-required');
			} else {
				$this.attr('data-required', 'required');
			}
		},
		
		removeAttachFile : function($btn) {
			var $wrapper = $btn.closest('[name=attachFileWrapper]');
			
			$wrapper.find('[name=filePath]').val('');
			$wrapper.find('[name^=file]').val('');
			
			$btn.hide();
		},
		
		//email Domain input disabled status setting
		setDisabledEmailDomainInput : function(val) {
			$.utils.setDisabledEmailDomainInput(val, $inputEmailDomain);
		},
		
		// 첨부파일 추가버튼 disabled setting
		setDisabledAttachFileAddButton : function($container, maxFileLength) {
			maxFileLength = maxFileLength || 5;
			
			
			var $btnAdd = $container.find('[name=btnAdd]');
			if($container.children().length == maxFileLength) {
				$btnAdd.attr('disabled', 'disabled');
			} else {
				$btnAdd.removeAttr('disabled');
			}
			
			return this;
		},
		
		addAttachFileWrapper : function($container, templateId) {
			$container.append($.render[templateId]());
			
			return this;
		},

		addMobileAttachFileWrapper : function($container) {
			$container.append($.render['mobileCounselAttachFileWrapper']());
			return this;
		},
		
		removeAttachFileWrapper : function($container, $btn) {
			if($container.children().length > 1) {
				$btn.closest('[name=attachFileWrapper]').remove();
			}			
			
			return this;
		},
		
		showSelectedFilePath : function($file) {
			$file.closest('[name=attachFileWrapper]').find('[name=filePath]').val($file.val());
		},
		
		showRemoveFileButton : function($file) {
			$file.closest('[name=attachFileWrapper]').find('[name=btnRemoveFile]').show();
		},
		
		renameFileInput : function($container) {
			$container.find('[name^=file]:file').each(function(i, v) {
				$(v).attr('name', 'file' + (i + 1));
			});
		},
		
		submitForm : function($form) {
			this.setProccessedData();
			
			this.removeEmptyFile($form);
			this.setReceptionDetailInfoArea();
			
			$form.submit();
		},
		
		setReceptionDetailInfoArea : function() {
			var checkedReceptionType = this.getCheckedReceptionType();
			
			$('[name=receptionDetailInfo][data-type!=' + checkedReceptionType  + ']').find('input, select').attr('disabled', 'disabled')
		},
		
		getCheckedReceptionType : function() {
			return $('[name=reception]:checked').val();
		},
		
		setProccessedData : function() {
			var $selectedItemContainer = $('#selectedItemContainer')
				, $selectedProductCode = $selectedItemContainer.find('[name=productCode]').not(':checkbox');

			$selectedItemContainer.find('[name=productCode]:checkbox').remove();

			if($selectedProductCode.length > 1) {
				this.resetProductCodeName($selectedProductCode);
			} else {
				$selectedProductCode.attr('type', 'hidden');
			}
			
			$('#orderNumber').val($('#selectedItemContainer').find('[name=orderNumber]').val());
			$('#acceptLocationCode').val($('[name=reception]:checked').val() == 'online' ? '4' : '3');
			
			$('#email').val(this.concatEmailInput());
			$('#cellNo').val(this.concatCellPhoneNumber());
			$('#smsYN').val($('#isSMSReceiveAgree').is(':checked') ? 'Y' : 'N');
			$('#agreeYN').val($('#thirdPartyAgree').is(":checked") ? 'Y' : 'N');
		},
		
		resetProductCodeName : function($selectedProductCode) {
			$selectedProductCode.each(function(i, v) {
				$(v).attr('name', 'productCodes[' + i + ']');
			});
		},
		
		concatEmailInput : function() {
			return $.utils.concatEmailInput($('#emailId'), $inputEmailDomain, $selectEmailDomain); 
		},
		
		concatCellPhoneNumber : function() {
			var cellPhoneNumber = '';
			
			$('[name=cellNumber]').each(function(i, v) {
				cellPhoneNumber += $(v).val();
			});
			
			return cellPhoneNumber;
		},
		
		removeSelectedItem : function($container, $el) {
			$el.closest('[name$=Wrapper]').remove();
			
			if($container.find('[name=itemWrapper]').length == 0 || $el.data('type') == 'all') {
				$container.empty();
			}
		},
		
		removeEmptyFile : function($container) {
			$container.find('[name^=file]').each(function(i, v) {
				var $v = $(v);
				
				var filePath = $v.val().trim();
				if(filePath == null || filePath == '') {
					$v.remove();
				}
			});
		}
			
	};
	
	$.view = { 
		counsel : counsel 
	};
	
})(window, jQuery);
