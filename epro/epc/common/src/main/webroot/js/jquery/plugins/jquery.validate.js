;(function($, window){
	'use strict';
	
	var Validator = function(options) {
		this.messages = $.extend({}, Validator.messages, options.messages);
	};
	
	Validator.prototype = {
		constructor : Validator,
		
		validate : {
			
			required : function(val) {
				return val != null && val.trim() != '';
			},
			
			minlength : function(val, minlength) {
				return val.length >= minlength;
			},
			
			maxlength : function(val, maxlength) {
				return val.length <= maxlength;
			},
			
			equallength : function(val, length) {
				return val.length == length;
			},

			email : function(val) {
				return Validator.regExp.email.test(val.toLowerCase());
			},

			emailDomain : function(val) {
				return Validator.regExp.emailDomain.test(val.toLowerCase());
			},
			
			type : function(val, type) {
				if(!Validator.regExp[type]) {
					return true;
				}
				
				return Validator.regExp[type].test(val.toLowerCase());
			},
			
			maxbyte : function(val, maxByte) {
				return Validator.utils.calculateTotalByte(val) < maxByte;
			}

		},
		
		getMessage : function(name, value) {
			var message = (name == 'type') ? this.messages.type[value] : this.messages[name];
			
			return message.replace(new RegExp('%s', 'i' ), value);
		}
		
	};
	
	Validator.messages = {
		required : '입력해주세요.',
		minlength : '%s자 이상 입력해주세요.',
		maxlength : '%s자 이하로 입력해주세요.',
		maxbyte : '%sByte 이하로 입력해주세요.',
		email : '유효한 형식으로 입력해주세요.',
		emailDomain : '유효한 형식으로 입력해주세요.',
		equallength : '%s자에 맞춰 입력해주세요',
		type : {
			email : '유효한 형식으로 입력해주세요.',
			emailDomain : '유효한 형식으로 입력해주세요.',
			number : '숫자로만 입력해주세요.'
		}
	};
	
	Validator.regExp = {
		email : /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
		emailDomain : /[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/,
		number : /^[0-9]+$/
	};
	
	Validator.utils = {
		calculateTotalByte : function(value) {
			var _this = this
			  , totalByte = 0;
			
			for(var i = 0; i < value.length; i++) {
				var byte = _this.getByte(value.charAt(i));
				
				totalByte += byte;
			}
			
			return totalByte;
		},
		
		getByte : function(char, previousChar) {
			if(escape(char).length > 1) {
				return escape(char).length / 2;
			} else if (char == '\n' && previousChar != '\r') {
				return 1;
			} else if (char == '<' || char == '>') {
				return 4;
			} 
			
			return 1;
		}
	};
	
	var FieldValidator = function($field, options) {
		this.$field = $field;
		this.options = options;
		
		this.constraints = {};
		this.validator = new Validator(options);

		this.init();
	};
	
	FieldValidator.prototype = {
		constructor: FieldValidator,
		
		init : function() {
			this.addConstraints();
		},
		
		addConstraints : function() {
			for(var i = 0; i < this.options.constraints.length; i++) {
				this.addConstraint(this.options.constraints[i]);
			}
		},
		
		addConstraint : function(name) {
			var compareValue = (this.$field.attr(name) && name != 'type') ? this.$field.attr(name) : this.$field.attr('data-' + name);
			
			if(compareValue) {
				this.constraints[name] = { compareValue : compareValue, message : '' };
			}
		},
		
		validate : function() {
			var inValidConstraint = this.getInvalidConstraint()
			  , isValid = !inValidConstraint || inValidConstraint == null;

			if(!isValid) {
				if(this.options.listeners.onFieldValidate && typeof this.options.listeners.onFieldValidate === 'function') {
					this.options.listeners.onFieldValidate(this.$field, inValidConstraint);
				}
				
				this.focusInvalidElement(this.$field);
			}
			
			return isValid;
		},
		
		focusInvalidElement : function($field) {
			if (this.options.scrollDuration > 0) {
				var top = $field.offset().top - $(window).height() / 2;

				$('html, body').animate({ scrollTop: top }, this.options.scrollDuration, function () {
					$field.focus();
				});
			} else {
				$field.focus();
			}
		},
		
		getInvalidConstraint : function() {
			if($.isEmptyObject(this.constraints)) {
				return null;
			}
			
			for(var name in this.constraints) {
				var constraint = this.constraints[name];
				
				if(!this.isValid(constraint, name)) {
					constraint.message = this.validator.getMessage(name, constraint.compareValue);
					constraint.name = name;
					
					return constraint;
				}
			}
			
			return null;
		}, 
		
		isValid : function(constraint, name) {
			var value = this.$field.val();
			
			if(this.$field.is('input[type=checkbox]')) {
				return name === 'required' && this.$field.is(':checked');
			} else if(name == 'required' || value) {
				return this.validator.validate[name](value, constraint.compareValue);
			}
			
			return true;
		}
		
	};				
	
	var FormValidator = function($form, options) {
		this.fieldValidators = [];
		this.$form = $form;
		this.options = options;
		
		this.init();
	};
	
	FormValidator.prototype = {
		constructor: FormValidator,
		
		init : function() {
			this.addFieldValidators();
		},
		
		addFieldValidators : function() {
			var formValidator = this;
			
			this.$form.find(this.options.inputs).each(function(i, v) {
				formValidator.addFieldValidator($(v));
			});
		},
		
		addFieldValidator : function($field) {
			var fieldValidator = $field.validator(this.options);
			
			if(fieldValidator && !$.isEmptyObject(fieldValidator.constraints)) {
				this.fieldValidators.push($field.validator(this.options));	
			}
		},
		
		validate : function() {
			var isValid = true;

			for(var i = 0; i < this.fieldValidators.length; i++) {
				if(!this.fieldValidators[i].validate()) {
					isValid = false;
					break;
				}
			};
			
			return isValid;
		}
		
	};
	
	$.fn.validator = function(options) {
		var $this = $(this)
		  , _options = $.extend({}, $.fn.validator.defaults, options);
		
		if($this.is('form')) {
			return new FormValidator($this, _options);
		} else if($this.is('input[type=radio], input[data-group]')) {
			// miltiple field validator
			return;
		} else {
			return new FieldValidator($this, _options);
		}
		
		return;
	};
	
	$.fn.validator.defaults = {
		inputs: 'input:not([disabled=disabled]), textarea, select',
		scrollDuration: 500,
		constraints : ['required', 'maxlength', 'minlength', 'email', 'emailDomain', 'equallength', 'type', 'maxbyte'],
		listeners : {
			onFieldValidate : function($field, constraint) {
				var invalidMessage = createInvalidMessage($field, constraint);
				
				if(invalidMessage){
					alert(invalidMessage);
				}
				
				function createInvalidMessage($field, constraint) {
					var message = $field.data(constraint.name + 'Message');
					if(message) {
						return message;
					}
					
					var title = $field.data('title') || $field.attr('title');
					if(title) {
						var conjunction = Number((title.charCodeAt(title.length - 1) % 28) == 16) ? '를 ' : '을 ';
						
						return title + conjunction + constraint.message;
					}
					
				}
			}	
		}
	};
})(jQuery, window);