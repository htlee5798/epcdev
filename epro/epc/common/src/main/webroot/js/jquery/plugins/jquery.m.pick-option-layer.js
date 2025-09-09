(function($, window, document, undefined) {
	'use strict';

	$.fn.pickOptionLayer = function(options) {
		var defaults = {
			title : '옵션을 선택하세요',
			layerClass : '.layerpopup-type1',
			btnPlusClass : '.plus',
			btnMinusClass : '.minus',
			btnCloseClass : '.close',
			wrapperQuantityClass : '.spinner',
			quantityClass : '.quantity',
			target : 'body',
			templateName : 'mobileBasketOptionForPick',
			marginTop : 4,
			periodDeliveryYn : 'N'
		}, 
		$this = this, 
		config = $.extend({}, defaults, options || {});

		var $target = $(config.target), 
			$layer = null;

		(function init() {
			config = $.extend(config, $this.data());

			removeAllLayer();
			render();
		})();

		// bind event
		function bindEvent() {
			$layer.on('click', config.btnCloseClass, function() {
				close();

				return false;
			}).on('click', config.btnPlusClass, function() {
				quantityPlus(this);

				return false;
			}).on('click', config.btnMinusClass, function() {
				quantityMinus(this);

				return false;
			}).on('change', config.quantityClass, function() {
				var $this = $(this), quantity = $this.val(), min = $this
						.data('minQuantity'), max = $this
						.data('maxQuantity');
	
				if (quantity > max) {
					alert(fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max));
					$this.val(max);
				}
	
				if (quantity < min) {
					alert(fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max));
					$this.val(min);
				}
	
				if (!isOnlyNumber(quantity)) {
					alert('주문수량은 숫자만 입력 가능합니다.');
					$this.val(min);
				}
	
				checkedUnit();
			});

			$target.on('click', function(e) {
				var $this = $(e.target);

				if ($this.closest(config.layerClass).length === 0) {
					removeAllLayer();
				}
			});

            $('div.mask').on('click', function () {
                if ($(config.layerClass).length > 0) {
                    removeAllLayer();
                }
            });

			// TODO
			$layer
				.find('form')
				.submit(function() {
					var formData = $(this).serializeArray(), nformVariation = '';

					setTotalQuantity(formData);

					if (!validate()) {
						for (var i = 0, len = formData.length; i < len; i++) {
							var data = formData[i];

							if (nformVariation === '') {
								nformVariation = data.name + ':' + data.value;
							} else {
								nformVariation = nformVariation + ';' + data.name + ':' + data.value;
							}
						}

						config.params = {
							prodCd : config.prodCd, // 상품코드
							itemCd : '001', // 단품코드
							bsketQty : config.totalQuantity / config.unit, // 주문수량
							categoryId : config.categoryId, // 카테고리ID
							nfomlVariation : nformVariation, // 옵션명,
							periDeliYn : config.periodDeliveryYn
						// 정기배송여부
						};

						config.successCallBack && config.successCallBack( config );
					}
					return false;
				});
		}
		// TODO
		function validate() {
			var isError = false, remain = config.totalQuantity % config.unit;

			if (config.totalQuantity % config.unit !== 0) {
				isError = true;

				alert('상품' + setComma(config.unit - remain) + '개를 더 선택해주세요.');
			}

			return isError;
		}

		function checkedUnit() {
			var $form = $layer.find('form'), $error = $layer.find('.error'), formData = $form.serializeArray();

			setTotalQuantity(formData);

			var remain = config.totalQuantity % config.unit;

			if (config.totalQuantity % config.unit !== 0) {
				$error.removeClass('hidden').html('(상품 <em class="point1">'+ $.utils.comma(config.unit - remain)+ '개</em>를 더 선택해주세요.)');
			} else {
				$error.addClass('hidden');
			}
		}

		function setTotalQuantity(formData) {
			var totalQuantity = 0;

			for (var i = 0, len = formData.length; i < len; i++) {
				var data = formData[i];

				totalQuantity = totalQuantity + parseInt(data.value, 10);
			}

			config.totalQuantity = totalQuantity;
		}

		function quantityPlus(target) {
			var $wrapper = $(target).closest(config.wrapperQuantityClass), 
				$inputField = $wrapper.find(config.quantityClass), 
				max = $inputField.data('maxQuantity'), 
				quantity = parseInt($inputField.val(), 10) + 1;

			quantity = quantity > max ? max : quantity;

			$inputField.val(quantity);

			checkedUnit();
		}

		function quantityMinus(target) {
			var $wrapper = $(target).closest(config.wrapperQuantityClass), 
				$inputField = $wrapper.find(config.quantityClass), 
				min = $inputField.data('minQuantity'), 
				quantity = parseInt($inputField.val(), 10) - 1;

			quantity = quantity < min ? min : quantity;

			$inputField.val(quantity);

			checkedUnit();
		}

		function removeAllLayer() {
			$target.find(config.layerClass).each(function() {
				$(this).remove();
				$('.mask').remove();
			});
			$target.off('click.layer').removeClass('layer-popup-active');
		}

		function render() {
			$.api.get({
				apiName : 'basketOptions',
				data : {
					'PROD_CD' : config.prodCd,
					'CATEGORYID' : config.categoryId,
					'PERIDELIYN' : 'N'
				},
				successCallback : function(productData) {
					config.options = productData;
					config.unit = config.options[0].unit;

					var layerTemplate = $.render[config.templateName](config);

					open(layerTemplate);
				}
			});
		}

		function open(layerTemplate) {
			$target.append(layerTemplate).promise().done(function() {
				$layer = $target.find(config.layerClass);

				$target.addClass('layer-popup-active');

				setPosition();
				bindEvent();
			});
		}

		function close() {
			$layer.remove();
			$('.mask').remove();
			$target.removeClass('layer-popup-active');
		}

		function setPosition() {
			var offset = $this.offset(), top = offset.top + $this.outerHeight(true) + config.marginTop;

			$layer.css({
				top : top,
				display : 'block'
			});
		}

		function isOnlyNumber(v) {
			var reg = /^(\s|\d)+$/;
			return reg.test(v);
		}
		
		return this;
	};

})(jQuery, window, document);