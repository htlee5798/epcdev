$.view = {};

(function(win, $){
	var $smallCounselType = $('#smallCounselType')
	  , $lowerGroupCode = $('#lowerGroupCode')
	  , $largeCounselType = $('#largeCounselType');
	
	var counsel = {
		
		resetSmallCounselType : function(largeCounselType, afterRender) {
			$smallCounselType.children().not(':first').remove();
			
			if(largeCounselType) {
				this.renderSmallCounsleType(largeCounselType, afterRender);
			}
		},
		
		renderSmallCounsleType : function(largeCounselType, afterRender) {
			$.ajax({
				url : '/cc/CommonCodeHelperController/generateOptionTag.do',
				data : { selectedCode : largeCounselType },
				success : function(res) {
					$smallCounselType.append(res);
					
					if(afterRender) {
						afterRender(res);
					}
				}
			});
		},
		
		resetLowerGroupCode : function(groupCode, excludeGroupCode) {
			$lowerGroupCode.empty();
			$lowerGroupCode.hide();
			
			if(groupCode && (!excludeGroupCode || groupCode !== excludeGroupCode)) {
				this.renderLowerGroupCode(groupCode);
			}
		},
		
		renderLowerGroupCode : function(groupCode) {
			if(groupCode == 'store') {
				this.renderStoreCode(groupCode);
			} else if(groupCode == 'customerCenter') {
				this.renderCustomerCenterAdmin();
			} else {
				this.renderDepartmentCode(groupCode);
			}
		},
		
		renderStoreCode : function(groupCode) {
			this.getGroupCodes({
				url : '/cc//PCCMAGT0072/stores.do',
				data : { groupCode : groupCode },
				callback : function(res) {
					if(res.stores) {
						$lowerGroupCode.append($('<option/>').text('점포명').val(''));
						
						$lowerGroupCode.attr('name', 'storeCode');
						$lowerGroupCode.show();
						
						res.stores.forEach(function(v, i) {
							var $option = $('<option/>').text(v.STR_NM).val(v.STR_CD);
							
							$lowerGroupCode.append($option);
						});
					}
				}
			});
		},
		
		renderCustomerCenterAdmin : function() {
			this.getGroupCodes({
				url : '/cc//PCCMAGT0071/administrators.do',
				data : {
					departmentName : 'ccagent',
					currentPage : 1,
					rowsPerPage : 1000,
					dataType : 'raw'
				},
				callback : function(res) {
					if(res.administrators) {
						$lowerGroupCode.append($('<option/>').text('상담원명').val(''));
						
						$lowerGroupCode.attr('name', 'transferAdminId');
						$lowerGroupCode.show();
						
						res.administrators.forEach(function(v, i) {
							var $option = $('<option/>').text(v.name).val(v.loginId);
							
							$lowerGroupCode.append($option);
						});
					}
				}
			});
		},
		
		renderDepartmentCode : function(groupCode) {
			this.getGroupCodes({
				url : '/cc//PCCMAGT0072/departments.do',
				data : { groupCode : groupCode },
				callback : function(res) {
					if(res.departments) {
						$lowerGroupCode.append($('<option/>').text('부서명').val(''));
						
						$lowerGroupCode.attr('name', 'departmentCode');
						$lowerGroupCode.show();
						
						res.departments.forEach(function(v, i) {
							var departmentCode = v.code || v.name;
							if(departmentCode) {
								var $option = $('<option/>').text(v.name).val(departmentCode);
								
								$lowerGroupCode.append($option);
							}
						});
					}
				}
			});
		},
		
		getGroupCodes : function(options) {
			$.ajax({
				url : options.url,
				data : options.data,
				success : function(res) {
					if(options.callback) {
						options.callback(res);
					}
				}
			});
		},
		
		resetLargeCounselType : function(val, afterRender) {
			$largeCounselType.children().not(':first').remove();
			
			if(val) {
				this.renderLargeCounsleType(val, afterRender);
			};
		},	
		
		renderLargeCounsleType : function(largeCounselType, afterRender) {
			$.ajax({
				url : '/cc/CommonCodeHelperController/generateCounselOptionTag.do',
				data : { selectedCode : 'QNA00', selectViewCode : largeCounselType },
				success : function(res) {
					$largeCounselType.append(res);
					
					if(afterRender) {
						afterRender(res);
					}
				}
			});
		}		
	};
	
	$.view = { 
		counsel : counsel 
	};
})(window, jQuery);