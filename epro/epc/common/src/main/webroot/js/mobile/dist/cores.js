if (!Array.prototype.filter) {
	Array.prototype.filter = function(fun/*, thisArg*/) {
		'use strict';

	    if (this === void 0 || this === null) {
	    	throw new TypeError();
	    }

	    var t = Object(this);
	    var len = t.length >>> 0;
	    if (typeof fun !== 'function') {
	    	throw new TypeError();
	    }

	    var res = [];
	    var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
	    for (var i = 0; i < len; i++) {
	    	if (i in t) {
	    		var val = t[i];
	        
	    		if (fun.call(thisArg, val, i, t)) {
	    			res.push(val);
    			}
    		}
	    }

	    return res;
    };
}
// Production steps of ECMA-262, Edition 5, 15.4.4.14
// Reference: http://es5.github.io/#x15.4.4.14
if (!Array.prototype.indexOf) {
  Array.prototype.indexOf = function(searchElement, fromIndex) {

    var k;

    // 1. Let o be the result of calling ToObject passing
    //    the this value as the argument.
    if (this == null) {
      throw new TypeError('"this" is null or not defined');
    }

    var o = Object(this);

    // 2. Let lenValue be the result of calling the Get
    //    internal method of o with the argument "length".
    // 3. Let len be ToUint32(lenValue).
    var len = o.length >>> 0;

    // 4. If len is 0, return -1.
    if (len === 0) {
      return -1;
    }

    // 5. If argument fromIndex was passed let n be
    //    ToInteger(fromIndex); else let n be 0.
    var n = fromIndex | 0;

    // 6. If n >= len, return -1.
    if (n >= len) {
      return -1;
    }

    // 7. If n >= 0, then Let k be n.
    // 8. Else, n<0, Let k be len - abs(n).
    //    If k is less than 0, then let k be 0.
    k = Math.max(n >= 0 ? n : len - Math.abs(n), 0);

    // 9. Repeat, while k < len
    while (k < len) {
      // a. Let Pk be ToString(k).
      //   This is implicit for LHS operands of the in operator
      // b. Let kPresent be the result of calling the
      //    HasProperty internal method of o with argument Pk.
      //   This step can be combined with c
      // c. If kPresent is true, then
      //    i.  Let elementK be the result of calling the Get
      //        internal method of o with the argument ToString(k).
      //   ii.  Let same be the result of applying the
      //        Strict Equality Comparison Algorithm to
      //        searchElement and elementK.
      //  iii.  If same is true, return k.
      if (k in o && o[k] === searchElement) {
        return k;
      }
      k++;
    }
    return -1;
  };
}
if (!Array.isArray) {
  Array.isArray = function(arg) {
    return Object.prototype.toString.call(arg) === '[object Array]';
  };
}
// Production steps of ECMA-262, Edition 5, 15.4.4.17
// Reference: http://es5.github.io/#x15.4.4.17
if (!Array.prototype.some) {
  Array.prototype.some = function(fun/*, thisArg*/) {
    'use strict';

    if (this == null) {
      throw new TypeError('Array.prototype.some called on null or undefined');
    }

    if (typeof fun !== 'function') {
      throw new TypeError();
    }

    var t = Object(this);
    var len = t.length >>> 0;

    var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
    for (var i = 0; i < len; i++) {
      if (i in t && fun.call(thisArg, t[i], i, t)) {
        return true;
      }
    }

    return false;
  };
}
/*
* @name : zf
*/
if(typeof String.prototype.zf != 'function') {
    String.prototype.zf = function(len) {
        return "0".cut(len - this.length) + this;
    };
};
/*
* @name : zf
*/
if(typeof Number.prototype.zf != 'function') {
	Number.prototype.zf = function(len) {
        return this.toString().zf(len);
    };
};
/*
* @name : cut [String]
*/
if(typeof String.prototype.cut != 'function') {
    String.prototype.cut = function(len) {
        var s = '',
            i = 0;
        while(i++ < len) {
            s += this;
        }
        return s;
    };
};
/*
* @name : addYear [Date]
* @desc : 날짜 증가/감소 (월)
* @param : dnays[Number]
*/
if(typeof Date.prototype.addYear != 'function') {
    Date.prototype.addYear = function(n) {
        var dat = new Date(this.valueOf());

        dat.setFullYear(dat.getFullYear() + n);

        return dat;
    };
};

/*
* @name : addMonth [Date]
* @desc : 날짜 증가/감소 (월)
* @param : n[Number]
*/
if(typeof Date.prototype.addMonth != 'function') {
    Date.prototype.addMonth = function(n) {
        var dat = new Date(this.valueOf());

        dat.setMonth(dat.getMonth() + n);

        return dat;
    };
};

/*
* @name : addDate [Date]
* @desc : 날짜 증가/감소 (일)
* @param : n[Number]
*/
if(typeof Date.prototype.addDate != 'function') {
    Date.prototype.addDate = function(n) {
        var dat = new Date(this.valueOf());
        dat.setDate(dat.getDate() + n);
        return dat;
    };
};

/*
* @name : addMinutes [Date]
* @desc : 날짜 증가/감소 (분)
* @param : n[Number]
*/
if(typeof Date.prototype.addMinutes != 'function') {
    Date.prototype.addMinutes = function(n) {
        var dat = new Date(this.valueOf());
        dat.setMinutes(dat.getMinutes() + n);
        return dat;
    };
};

/*
* @name : addSeconds [Date]
* @desc : 날짜 증가/감소 (초)
* @param : n[Number]
*/
if(typeof Date.prototype.addSeconds != 'function') {
    Date.prototype.addSeconds = function(n) {
        var dat = new Date(this.valueOf());
        dat.setSeconds(dat.getSeconds() + n);
        return dat;
    };
};

/*
* @name : format [Date]
* @desc : 날짜 Formatter
* @param : format[string]
*/
if(typeof Date.prototype.format != 'function') {
    Date.prototype.format = function(f) {
        if(!this.valueOf()) {
            return " ";
        }

        var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"],
            shortWeekName = ["일", "월", "화", "수", "목", "금", "토"],
            d = this;

        return f.replace(/(yyyy|yy|MM|dd|E|e|hh|mm|ss|a\/p)/gi, function($1) {
            switch($1) {
                case "yyyy":
                    return d.getFullYear();
                case "yy":
                    return (d.getFullYear() % 1000).zf(2);
                case "MM":
                    return ((d.getMonth() + 1)+'').zf(2);
                case "dd":
                    return (d.getDate()+'').zf(2);
                case "E":
                    return weekName[d.getDay()];
                case "e":
                    return shortWeekName[d.getDay()];
                case "HH":
                    return (d.getHours()+'').zf(2);
                case "hh":
                    return ((h = d.getHours() % 12) ? h : 12).zf(2);
                case "mm":
                    return (d.getMinutes()+'').zf(2);
                case "ss":
                    return (d.getSeconds()+'').zf(2);
                case "a/p":
                    return d.getHours() < 12 ? "오전" : "오후";
                default:
                    return $1;
            }
        });
    };
};
if(typeof Date.prototype.format != 'function') {
	Date.prototype.yyyymmdd = function() {
		  var mm = this.getMonth() + 1; // getMonth() is zero-based
		  var dd = this.getDate();
	
		  return [this.getFullYear(), !mm[1] && '0', mm, dd[1] && '0', dd].join(''); // padding
	};
}
/*
* @name : replaceAll [String]
* @desc : 대상 문자열 전체를 대상으로 치환.
* @param : find, replace
*/
if(typeof String.prototype.replaceAll != 'function') {
    String.prototype.replaceAll = function(f, r) {
        f = f.escapeRegExp();
        return this.replace(new RegExp(f, 'g'), r);
    };
};
if (!String.prototype.trim) {
  String.prototype.trim = function () {
    return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
  };
}
(function( factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		factory( jQuery );
	}
} (function( $ ) {
	'use strict';
	
	var defaults = {
		'useLog': {'editable': false, 'value': true },
		'androidAppVersion' : { 'editable' : false, 'value' : '10.74' },
		'iosAppVersion' : { 'editable' : false, 'value' : '9.9.4' },
		'mobileMapPath' : { 'editable' : false, 'value' : '/mobile/popup/storeView.do' },
		'lottemartSchemeUrl' : { 'editable' : false, 'value' : 'lottemartmall://' },
		'imageCdnPath' : { 'editable' : false, 'value' : '//simage.lottemart.com' },
		'sImageCdnPath' : { 'editable' : false, 'value' : '//simage.lottemart.com' },
		'popupDeliveryTimeUrl' :{ 'editable' : false, 'value' : '/quickmenu/popup/deliverytime.do?SITELOC=AE001' },
		'popupMyDeliveryUrl' :{ 'editable' : false, 'value' : '/mymart/popup/selectMyDeliveryList.do?SITELOC=AE002' },
		'facebookAppId' : { 'editable' : false, 'value' : '739454589522567' },
		'schemePushList' : { 'editable' : false, 'value' : 'lottemartapp://pushList?url='},
		'schemeShowBar' : { 'editable' : false, 'value' : 'lottemartapp://showBar' },
		'schemeHideBar' : { 'editable' : false, 'value' : 'lottemartapp://hideBar' },
		'schemeCloseIntro' : { 'editable' : false, 'value' : 'lottemartmall://closeIntro' },
		'schemeBasketcountupdate' : { 'editable' : false, 'value' : 'lottemartmall://basketcountupdate' },
		'appMarketAndroid' : { 'editable' : false, 'value' : 'https://play.google.com/store/apps/details?id=com.lottemart.shopping' },
		'appMarketIOS' : { 'editable' : false, 'value' : 'http://itunes.apple.com/app/id493616309' }
	};
	
	var regExps = {
		comma : /\B(?=(\d{3})+(?!\d))/g
	};
	
	$.utils = {
		/*
		 * key event로 넘어온 값이 숫자인지 체크 합니다.
		 * input number로 최신 브라우저는 체크 하고, 하위버전의 경우 css ime-mode:disabled;를 이용하여 한글을 걸러냅니다.
		 * $('input[data-type="number"]').on('keydown', $.utils.keyIsNumber);
		 * */
		keyIsNumber : function(e) {
			var keyCode = e.keyCode || e.which;
			
			if( !(
				keyCode == 8								// backspace
				|| keyCode == 9							// tab 
				|| keyCode == 46							// delete
				|| (keyCode >= 35 && keyCode <= 40)		// arrow keys/home/end
				|| (keyCode >= 48 && keyCode <= 57)		// numbers on keyboard
				|| (keyCode >= 96 && keyCode <= 105)	// number on keypad
			) ) {
				e.preventDefault();
			}
		},
		
		calculateTotalByte : function(value) {
			var totalByte = 0;
			for(var i = 0; i < value.length; i++) {
				var _byte = $.utils.getByte(value.charAt(i));
				
				totalByte += _byte;
			}
			
			return totalByte;
		},
		
		getExceedCharacterIndex : function(value, maxByte) {
			var totalByte = 0;
			for(var i = 0; i < value.length; i++) {
				var _byte = $.utils.getByte(value.charAt(i));
				if(totalByte >= maxByte) {
					if(_byte > 1) {
						return i -1;
					}
					
					return i;
				}
				
				totalByte += _byte;
			}
			
			return value.length;
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
		},
		
		/*
		 * */
		parseBoolean : function(str) {
			if( typeof str != 'boolean' ) {
				str = ( str.toLowerCase() == 'true' ) ? true : false;
			}
			return str;
		},
		visible : function($obj){
			$obj.css('visibility', 'visible');
		},
		config : function( n, v ) {
			if($.type(n) === 'object') {
                setObj(n);
            } else if($.type(n) === 'string' && arguments.length == 2) {
                setStr(n, v);
            } else if($.type(n) === 'string' && arguments.length == 1) {
                return get(n);
            } else if(arguments.length == 0) {
                return getProperty();
            } else {
                $.utils.error('parameter is only json or string. current type : ' + $.type(n));
            }

            function setObj(obj) {
                $.each(obj, function(n) {

                    if(defaults[n] && defaults[n].editable) {
                        if(typeof obj[n] === 'string' && (obj[n] === 'True' || obj[n] === 'False')) {
                            obj[n] = obj[n] == 'True';
                        }

                        defaults[n].value = obj[n] || defaults[n].value;
                        defaults[n].editable = false;
                    } else {
                    	defaults[n] = {
                    		value : obj[ n ],
                    		editable : false
                    	};
                    }
                });
            }

            function setStr(n, v) {
                if(defaults[n] && defaults[n].editable) {
                    if(typeof v === 'string' && (v === 'True' || v === 'False')) {
                        v == true;
                    }

                    defaults[n].value = v;
                    defaults[n].editable = false;
                } else if( defaults[n] === undefined ) {
                	defaults[n] = {
                		value : v,
                		editable : false
                	};
                } else {
                    $.utils.error('name is not editabled : ' + n );
                }
            }

            function get(n) {            	//
//                if(defaults[n] === undefined && n !== 'all') {
//                    $.utils.error('undefined property name : ' + n);
//                }

                var returnVal;

                if(n == 'all') {
                    returnVal = {};
                    for(var item in defaults) {
                        returnVal[item] = defaults[item].value;
                    }
                } else {
                    if( defaults[n] !== undefined ) {
                        returnVal = defaults[n].value;
                    }
                }
                return returnVal;
            }

            function getProperty() {
                return {
                    appSettingUrl: function() {
                        return utils.config('appSettingUrl');
                    }
                };
            }

            return this;
		},
		log : function( obj ) {
			var useLog = this.config('useLog') == true && window.console;
            if(useLog) {
                if(typeof obj == "object" && console.dir) {
                    console.dir && console.dir(obj);
                } else {
                    console.log && console.log(obj);
                }
            }
		},
		error : function( obj ) {
			var useLog = this.config('useLog') == true && window.console;
			if(useLog) {
				var errorName = obj.split( ':' )[ 0 ],
					value = obj.split( ':' )[ 1 ];
				
				console.log('%c ' + errorName + ' : ' + '%c' + value, 'color: #bada55', 'color: #FF5A5A');
			}
		},
		isMobile : function() {
            var rtn = false;

            if(navigator.userAgent.match(/Android|Mobile|iP(hone|od|ad)|BlackBerry|IEMobile|Kindle|NetFront|Silk-Accelerated|(hpw|web)OS|Fennec|Minimo|Opera M(obi|ini)|Blazer|Dolfin|Dolphin|Skyfire|Zune/)) {
                rtn = true;
            }

            return rtn;
        },
        isIOS : function() {
        	var rtn = false;

            if(navigator.userAgent.match(/iP(hone|od|ad)/)) {
                rtn = true;
            }

            return rtn;
        },
        isAndroid : function() {
        	var rtn = false;
        	
        	if( navigator.userAgent.match( /Android|android/ ) ) {
        		rtn = true;
        	}
        	
        	return rtn;
        },
		isiOSLotteMartApp : function () {
			var ua= window.navigator.userAgent;

			return ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-IOS") > -1;
        },
		isAndroidLotteMartApp : function () {
			var ua= window.navigator.userAgent;

			return ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-ANDROID") > -1 || ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-DID") > -1;
        },
        isIE : function() {
        	var rtn = false;
        	
        	if( navigator.userAgent.match( 'MSIE' ) ) {
        		rtn = true;
        	}
        	
        	return rtn;
        },
        getParamFoWiseLog : function( str ) {
        	if( str === undefined || str === '' ) {
        		return '';
        	}
        	var codeList = ["dpId", "itemSetId", "scnId"],
				clickParams = str.substring( str.indexOf( "?" ) + 1 ).split( "&" ),
				curParams ,
				params = '';
        	
			for ( var i = 0, len = codeList.length; i < len; i++ ) {
				curParams = $.grep( clickParams, function( obj ) {
					return obj.indexOf( codeList[i] + "=" ) >= 0;
				});
				if ( curParams != null && curParams.length > 0 ) {
					if ( codeList[i] === "dpId" ) {
						params += ( "&" + "dp=" + curParams[0].split("=")[1] );
					}
					else {
						params += ( "&" + curParams[0] );
					}
				}
			}
			
			return params;
        },
        setMetaTag : function( obj ) {//google, facebook, twitter
        	var metas = '';
        	
        	$.each( obj, function( name , value ) {
    			var $meta = $( 'meta[name="' + name + '"]' );
        		
        		if( $meta.length === 0 ) {
        			var isOgTag = name.indexOf( 'og' ) !== -1,
        				isTwitter = name.indexOf( 'twitter' ) !== -1;
        		
	        		if( isOgTag ) {
	        			metas = metas + '<meta property="' + name + '" content="' + value + '">';
	        		} else if( isTwitter ) {
	        			metas = metas + '<meta name="' + name + '" content="' + value + '">';
	        		} else {
	        			if( name.indexOf( 'fb') !== -1 || name.indexOf( 'article' ) ) {
	        				metas = metas + '<meta property="' + name + '" content="' + value + '">';
	         			} else {
	        				metas = metas + '<meta itemprop="' + name + '" content="' + value + '">';
	        			}
	        		}
        		} else {
        			$meta.attr( 'content', value );
        		}
        		
        	});
        	
        	$( 'head' ).append( metas );
        },
        unloadForLoading : function() {
    		if( $.fn.loadingBar && navigator.userAgent.indexOf( 'lottemartapp' ) === -1 ) {
    			$( 'body' ).loadingBar();
    		}
        },
        comma : function( str ) {
        	return str.toString().replace( regExps.comma, ",");
        },
		getParams : function() {
			/*
			 * @return $.utils.getParams()['params'] || $.utils.getParams()
			 * */
			function urldecode(str) {
				return decodeURIComponent((str+'').replace(/\+/g, '%20'));
			}

			function transformToAssocArray( prmstr ) {
				var params = {},
					prmarr = prmstr.split("&");
				
				for ( var i = 0; i < prmarr.length; i++) {
					var tmparr = prmarr[i].split("=");
						params[tmparr[0]] = urldecode(tmparr[1]);
				}
				return params;
			}
			
			var prmstr = window.location.search.substr(1);
			
			return prmstr != null && prmstr != "" ? transformToAssocArray(prmstr) : {};
		},
		checkAppVersion : function( str,  lastestAppVersion ) {//row app version check
			lastestAppVersion = lastestAppVersion || ( $.utils.isIOS() ? $.utils.config( 'iosAppVersion' ) : $.utils.config( 'androidAppVersion' ) );
				
			var lastestAppVersionArray = lastestAppVersion.split( '.' ),
        		currentAppVersionArray = str.split( '.' ),
        		returnValue = true;
        	
        	lastestAppVersionArray.some( function( v, i ) {
        		if( parseInt( currentAppVersionArray[ i ], 10 ) < parseInt( v, 10 ) ) {
        			returnValue = false;
        		}
        		
        		return !returnValue;
        	});
        	
        	return returnValue;
		},
		getAppVersion : function () {
            return navigator.userAgent.split('app-version')[1].replace('V.', '').replace(/\s/gi, '');
        },
        serializeObject : function($form) {
        	try {
    			var serializeArray = $form.serializeArray()
    			  , obj = {};
    			
    			if(serializeArray) {
    				$.each(serializeArray, function() {
    					obj[this.name] = this.value;
    				});
    			}
    			
    			return obj;
    		} catch(e) {
    			return null;
    		}
        },
        checkedAll : function($checkboxes) {
    		this.setCheckedAll($checkboxes, true);
    	},
    	unCheckedAll : function($checkboxes) {
    		this.setCheckedAll($checkboxes, false);
    	},
    	setCheckedAll : function($checkboxes, isChecked) {
    		$checkboxes.each(function() {
    			$(this).prop('checked', isChecked);
    		});
    	},
    	concatCheckedValues : function($checkboxes, delimeter) {
    		var values = '';
			
			$checkboxes.each(function() {
				if(this.checked) {
					var _delimeter = delimeter || values == '' ? '' : ',';

					values += _delimeter + this.value;
				}
			});
			
			return values;
    	},
    	
    	concatCheckedId : function($checkboxes, delimeter) {
    		var id = '';
			
			$checkboxes.each(function() {
				if(this.checked) {
					var _delimeter = delimeter || id == '' ? '' : ',';

					id += _delimeter + this.id;
				}
			});
			
			return id;
    	},
    	
    	checkedSelectedValues : function($checkboxes, selectedValues) {
			$.each(selectedValues, function(i, v) {
				$checkboxes.filter('[value="' + v + '"]').prop('checked', true);
			});
    	},
    	
		setActiveCheckedFieldWrapper : function($el, $wrapper) {
			if(!$wrapper.hasClass('active') && $el.is(':checked')) {
				$wrapper.addClass('active');
			} else {
				$wrapper.removeClass('active');
			}
		},

		deferredAction: function() {
			var args = arguments;
			var isAllFunctions = function(arr) {
				var isFlag = true;
				
				isFlag
					&& $.each(arr, function(i, v){
						isFlag = (isFlag && $.isFunction(v));
					});
				return isFlag;
			};

			return (args && args.length)
				? (function(args) {
					var funcArr = $.makeArray(args),
						$deferred = undefined,
						deferredArr = undefined;

					if (!isAllFunctions(funcArr)) {
						console.error('arguments can be accepted only functions.');
						$deferred = $.Deferred();
						$deferred.resolve();

						return $deferred;
					} else {
						deferredArr = $.map(funcArr, function(v, i){
							$deferred = $.Deferred();
							$deferred.resolve(v());

							return $deferred;
						});

						return $.when.apply($, deferredArr)
					}
				})(args)
				: (function() {
					var $deferred = $.Deferred();

					$deferred.resolve(undefined);
					return $deferred.promise();
				})();
		},

		deferredArrAction: function(deferredArr) {
			return $.isArray(deferredArr)
				? (function() {
					var $deferred = $.Deferred();

					$.when.apply($, deferredArr)
						.done(function() {
							$deferred.resolve([].slice.call(arguments));
						}).fail(function() {
							$deferred.reject([].slice.call(arguments));
						});

					return $deferred.promise();
				})()
				: (function() {
					var $deferred = $.Deferred();

					console.log('argument can be accepted only array of $.Deferred.');
					$deferred.resolve(undefined);
					return $deferred.promise();
				})();
		},
		
		//email Domain input disabled status setting
		setDisabledEmailDomainInput : function(val, $inputEmailDomain) {
			if(val == '직접입력') {
				$inputEmailDomain.removeAttr('disabled');
			} else {
				$inputEmailDomain.val('');
				$inputEmailDomain.attr('disabled', 'disabled');
			}
		},
		
		concatEmailInput : function($emailId, $inputEmailDomain, $selectEmailDomain) {
			var emailDomain;
			
			if($selectEmailDomain.val() == '직접입력') {
				emailDomain = $inputEmailDomain.val().trim();
			} else {
				emailDomain = $selectEmailDomain.val();
			}
			
			return $emailId.val() + '@' + emailDomain; 
		},
		
		removeCharacters : function(str) {
			return str.replace(/[^0-9]/g, '');
		}
	};
}));
(function( $, window, document, undefined ) {
	'use strict';

	var $Dim = $( '<i style="position:fixed;top:0;right:0;bottom:0;left:0;z-index:11;" />' );

	$.support.cors = true;
	
	$.api = {
		urls : {
			basketOptions : '/api/item/options.do',
			dealOptions : '/api/items.do',
			wishToggle : '/quickmenu/api/mywish/toggle.do',
			wishAdds : '/quickmenu/api/mywish/add.do',
			wishDelete : '/quickmenu/api/mywish/delete.do',
			recommendKeywords : '/search/recommend_keywords.do',
			mainLnb : '/common/api/lnb_contents.do',
			
			basketList : '/basket/api/list.do',
			normalBasketList : '/basket/api/01/items.do',
			normalBasketCount : '/basket/api/01/items/count.do',
			myOrderList : '/api/orders.do',
			myOrderCount : '/api/orders/count.do',
			myOrderItemList : '/api/orders/items.do',
			commonCodeList : '/api/common/codes.do',
			
			basketAdd : '/basket/api/add.do',
			basketRemove : '/basket/api/remove.do',
			basketCount : '/basket/api/count.do',
			getCategoryList : '/api/category/list.do',
			getCategoryProductListPage : '/api/category/product/listPage.do',
			getTemplateProductListPage : '/api/template/product/listPage.do',
			appBaketCount : '/app/basketCountNew.do',
			basketAddDirect : '/basket/insertDirectBasket.do',
			defaultDeliInfo : '/quickmenu/api/defaultDeliInfo.do',
			myDelivery : '/quickmenu/api/mydelivery.do',
			myCoupon : '/quickmenu/api/mycoupon.do',
			myHistory : '/quickmenu/api/myhistory.do',
			myByHistory : '/quickmenu/api/mybyhistory.do',
			myHistoryAdd : '/quickmenu/api/myhistory/add.do',
			myHistoryRemove : '/quickmenu/api/myhistory/delete.do',
			isLogin : "/member/islogin.do",
			isMember : '/member/ismember.do',
			mainTodayList : '/index/ajax/todayInfoAjax.do',
			mainBestList : '/index/ajax/bestProdAjax.do',
			mainEventList : '/index/ajax/EventAjax.do',
			mainPlanList : '/index/ajax/PlanAjax.do',
			mainClearanceList : '/index/ajax/ClearanceAjax.do',
			mainSpecStyleList : '/index/ajax/SpecStyleListAjax.do',
			mainFootList : '/index/ajax/FootListAjax.do',
			mainDownCoupon : '/event/downCouponByCPBook.do',
			planListAll : '/plan/getPlanListAllAjax.do',
			planDetail : '/plan/ajax/productDetail.do',
			trendAppendList : '/trend/ajax/trendAppendList.do',
			mobileUpdateProductReview : '/product/ajax/updateProductReview.do',
			mobileProductReviewCount : '/mobile/product/ajax/mobileProductReviewCnt.do',
			mobileProductReviewList : '/product/ajax/MobileProductReviewList.do',
			mobileProductQuestion : '/product/ajax/MobileProductQuestion.do',
			mobileChangeSelectOptionBoxAction : '/mobile/product/ajax/changeSelectOptionBoxAction.do',
			mobileGnbMenu : '/inc/mobileGnbMenu.do',
			mobileGetMain : '/mobile/ajax/corners/detail.do',
			mobileDeliveryTimeInfo : '/mobile/product/ajax/mobileDeliveryTimeInfoAjax.do',
			mobileSmartOffer : '/mobile/product/ajax/mobileSmartOfferAjax.do',
			mobileCateProductList : '/mobile/cate/ajax/cateProductListAjax.do',
			mobilePeriodShippingSmartRecommendList : '/delivery/api/smart.do',
            mobilePeriodShippingPackageList : '/delivery/api/package/list.do',
			mobileHotList : '/mobile/ajax/todayhot/mobileHotList.do',
			mobileSellingPriceList : '/mobile/main/ajax/ajaxCateProdList.do',
            mobilePlanList : '/mobile/plan/ajax/planAjaxList.do',
            saveDeviceIdAndPushYnOnServer : '/app/saveMemberDeviceInfo.do',
            saveAdIdOnServer : '/app/saveADIDInfo.do',
            getRecommProdList : '/holidays/api/getRecommProdList.do',
            getPlanProdList : '/api/plan/product/listPage.do',
            getMobileNearestBuyHistoryV2 : '/mobile/v2/getMobileNearestBuyHistory.do',
            getPersonalProducts : '/mobile/ajax/todayhot/personal/products.do',
            getMobileHottime : '/mobile/ajax/todayhot/CornerMapWithContents.do',
            getMemberValue : '/mobile/delivery/sessionNm.do',
			getMainSpecialList : '/mobile/mainSpecial/list.do',
			mobileAnalyticsApi : '/mobile/main/ajax/analyticsApiAjax.do'
		},
		get : function( options ) {
			var _this = this,
				url = _this.urls[ options.apiName ] || options.url,
				isDim = false,
				request = null,
				isShowMoreBar = options.isShowMoreBar && options.$container,
				isShowLoadingBar = options.isShowLoadingBar && options.$container;
			
			if( url === undefined ) {
				return;
			}
			
			if( options.isDim === undefined ) {
				isDim = true;
			}
			
			request = $.ajax({
				type : 'GET',
				async : options.async === undefined ? true : options.async,
				dataType : options.dataType || 'json',
				url : url,
				data : options.data || {},
				cache: options.cache === undefined ? true : options.cache,
				beforeSend : function(xhr, settings) {
					if( isDim ) {
						$( 'body' ).append( options.$dim || $Dim );
					}
					
					if(isShowMoreBar) {
						options.$container.moreBar();
					}
					
					if(isShowLoadingBar) {
						options.$container.loadingBar();
					}
				},
				complete : function(xhr, state) {
					var $el = options.$dim || $Dim;
					
					if( $el.length > 0 ) {
						$el.remove();
					}
					
					if(isShowMoreBar) {
						options.$container.moreBar(false);
					}
					
					if(isShowLoadingBar) {
						options.$container.loadingBar(false);
					}

					if(options.complete) {
						options.complete(xhr, state);
					}
				},
				success : function( resData ) {
					if( options.successCallback ) {
						options.successCallback( resData );
					}
				},
				error : function( xhr, status, error ) {
					$.utils.log(error);
					$.utils.log(status);
					$.utils.log(xhr);
					if( options.errorCallback ) {
		            	options.errorCallback( xhr, status, error );
		            }
				}
			});
			return request;
		},
		set : function( options ) {
			var _this = this,
				url = _this.urls[ options.apiName ] || options.url,
				isDim = false,
				request = null;
			if( url === undefined ) {
				return;
			}
			
			if( options.isDim === undefined ) {
				isDim = true;
			}
			
			request = $.ajax({
				type : 'POST',
				url : url,
				headers: {},
		        async: options.async === undefined ? true : options.async,
		        crossDomain: true,
		        cache: false,
		        processData: true,
		        data: options.data || {},
		        traditional : options.traditional || false,
		        xhrFields: {
		            withCredentials: true
		        },
		        beforeSend: function(xhr, settings) {
		            xhr.setRequestHeader("content-type", 'application/x-www-form-urlencoded');
		            xhr.setRequestHeader('x-requested-with', 'XMLHttpRequest');
		            
		            if( isDim ) {
						$( 'body' ).append( options.$dim || $Dim );
					}
		        },
                complete : function(xhr, state) {
                    var $el = options.$dim || $Dim;

                    if( $el.length > 0 ) {
                        $el.remove();
                    }

                    if(options.complete) {
                        options.complete(xhr, state);
                    }
                },
		        success: function ( resData ) {

		        	if( options.successCallback ) {
						options.successCallback( resData );
					}
		        },
		        error: function(xhr, status, error) {
		        	console.log( error );
		            if( options.errorCallback ) {
		            	options.errorCallback( xhr, status, error );
		            }
		        }
			});
			return request;
		}
	};
	
})( jQuery, window, document );
$.views.helpers({
	numberFormat : function( str ) {
		return $.utils.comma( str );
	},
	substr : function(str, index, length) {
		if(str == null || str == '') {
			return '';
		}
		return str.substr(index, length);
	},
	className : function( str ) {
		return str === undefined ? '' : str.replace( '.', '' );
	},
	imageCdnPath : function() {
		return '//simage.lottemart.com';
	},
	sImageCdnPath : function() {
		return '//simage.lottemart.com';
	},
	lmCdnV3RootPath : function() {
		return $.utils.config('LMCdnV3RootUrl');
	},
    setStringSplit : function(str, delimeter) {
		"use strict";
		return str.split(delimeter);
	},
	isYoutubleUrl : function(url) {
		return url && url.indexOf('youtube') > -1;
	},
	getLMAppUrlM : function() {
		return $.utils.config( 'LMAppUrlM' );
	},
	getImageContentBase : function () {
		return 	$.utils.config('imageContentBase');
    },
	getLmCdnStaticImagePath : function( imagePath ) {
		return $.utils.config( 'LMCdnStaticUrl' ) + imagePath;
	},
	getLmCdnStaticItemImagePath : function( prodCd ) {
		return $.utils.config('LMCdnStaticRootUrl') + "/images/prodimg/" + prodCd.substring(0, 5).trim() + "/" + prodCd + "_1_80.jpg";
	},
	getLmCdnStaticItemImageHotPath : function( prodCd ) {
		return $.utils.config('LMCdnStaticRootUrl') + "/images/prodimg/" + prodCd.substring(0, 5).trim() + "/" + prodCd + "_1_500.jpg";
	},
	getSimageStaticProductImagePath : function(prodCd, size) {
        return "//simage.lottemart.com/lim/static_root/images/prodimg/" + prodCd.substring(0, 5).trim() + "/" + prodCd + "_1_"+ size +".jpg";
	},
	getSoldOutImagePath : function() {
		return $.utils.config('LMCdnV3RootUrl') + '/images/layout/img_soldout_80x80.png';
	},
	getAdultImagePath : function() {
		return $.utils.config('LMCdnV3RootUrl') + '/images/layout/img_age19_80x80.jpg';
	},
	getRNBHistoryLink : function( typeCode, categoryId, contentsNo, prodCd, mallDivnCd ) {
		var link = '';

		switch( typeCode ) {
			case '01' :
				link = 'javascript:goProductDetail(' + "'" + categoryId + "'," + "'" + prodCd + "'," + "'N','',''," + "'" + mallDivnCd + "'" + ');';
				break;
			case '02' :
				link = '/category/categoryList.do?CategoryID=' +  categoryId;
				break;
			case '03' :
				link = '/plan/planDetail.do?CategoryID=' + categoryId + '&MkdpSeq=' + contentsNo;
				break;
			case '04' :
				link = '/event/detail.do?categoryId=' + categoryId;
				break;
			case '05' :
				link = '/trend/trendDetail.do?categoryId=' + categoryId + '&trendNo=' + contentsNo;
				break;
 		}

		return link;
	},
	getRNBHistroyClass: function( typeCode ) {
		var cls = '';

		switch( typeCode ) {
			case '02' :
				cls = 'hidden';
				break;
			case '03' :
				cls = 'class-tag  tag-exhibition';
				break;
			case '04' :
				cls = "class-tag tag-event";
				break;
			case '05' :
				cls = "class-tag tag-trend";
				break;
		}

		return cls;
	},
	getRNBHistroyTitle : function( typeCode ) {
		var title = '';

		switch( typeCode ) {
			case '02' :
				title = "카테고리";
				break;
			case '03' :
				title = "기획전";
				break;
			case '04' :
				title = "이벤트";
				break;
			case '05' :
				title = "트렌드";
				break;
		}

		return title;
	},
	getRNBHistoryErrorImage : function( typeCode ) {
		var imageName = '';

		switch( typeCode ) {
			case '03' :
				imageName = "'noimg_plan_list_'";
				break;
			case '04' :
				imageName = "'noimg_event_'";
				break;
			case '05' :
				imageName = "'noimg_trend_'";
				break;
 		}

		return imageName;
	},
	getNoImagePath : function(width, height) {
		var _width = width || 120
		  , _height = height || 120;

		return $.utils.config('LMCdnV3RootUrl') + '/images/layout/noimg_prod_' + _width + 'x' + _height + '.jpg';
	},
	getRNBHistoryImage : function( typeCode, imgSrc ) {
		var src = '';

		switch( typeCode ) {
			case '02' :
				src = $.utils.config('LMCdnV3RootUrl') + '/images/temp/category_80x80.jpg';
				break;
			default :
				src = $.views.helpers.getLmCdnStaticImagePath( imgSrc );
				break;
		}

		return src;
	},
    getManuFacturingProduct : function (onlineProdTypeCd) {
		"use strict";

		return onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_MAKE') ||
		onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_INST') ? true : false;
	},
	getMobileBage : function (promotionProductIcon, maxLength) {
		"use strict";
		if(!promotionProductIcon) {return;}

		var icons = promotionProductIcon.split('|'),
			badgeHtml = '';

		for(var i = 0; i < maxLength; i++) {
			var infos = icons[i].split(':'),
				orderNo = infos[0],
				iconType = infos[1],
				iconNm = infos[2],
				iconDesc = infos[3],
				html = '';

			if(infos[4] !== null && infos[4].indexOf('-') !== -1){
				iconDesc = 'type' + infos[4].trim();
			}

			switch (iconType) {
				case '04':
					html = iconDesc;
					break;
				case 'won':
					html = '<em class="won">' + $.utils.comma(iconDesc) + '</em> 할인';
					break;
				case 'discount':
					html = '<em class="number">' + iconDesc +'</em> 할인';
					break;
				case 'bundle':
					html = '<em class="plus">' + iconDesc +'</em>';
					break;
				case 'type6-1':
					html = '<em class="number">' + iconDesc + '</em> 다둥이';
					break;
				case 'type6-2':
					html = '<em class="won">' + $.utils.comma(iconDesc) + '</em> 다둥이';
					break;
				case 'type4-1':
					html = '<em class="number">' + iconDesc + '</em> L.POINT';
					break;
				case 'type4-2':
					html = '<em class="won">' + $.utils.comma(iconDesc) + '</em> L.POINT';
					break;
				default:
					if(iconType !== undefined && iconType !== 'type12') {
						html = iconNm;
					}
					break;
			}

            badgeHtml = badgeHtml + '<div class="type02">' + html + '</div>';
		}

		return badgeHtml;
	},
	isAdult : function( adultYN ) {
		return $.utils.config('LMAdultPccvYn') !== 'Y' && adultYN === 'Y';
	},
	isSoldOut : function(soutYNReal) {
		return soutYNReal === 'Y' ? true : false;
	},
	isLogin : function() {
		return _Login_yn === 'Y';
	},
	isAppendPlanDetailOption : function( categoryId ) {
		return $.utils.config( 'lotteEmpYn' ) === 'Y' || categoryId !== '';
	},
	isSoldProductItem : function( productCode , stockQuantity ) {
		return /^D/.test( productCode ) && stockQuantity === 0;
	},
	isDealProductItem : function(onlineProdTypeCd){
		"use strict";

		return onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_DEAL') ? onlineProdTypeCd : "";
	},
	isDealTypeProductItem : function (onlineProdTypeCd, deliType) {
		"use strict";

		var isDealProduct = onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_DEAL') ? onlineProdTypeCd : "",
			reserve = $.utils.config('const_DELI_TYPE_RESERVE'),
			vendorDeli = $.utils.config('const_DELI_TYPE_VENDOR_DELI'),
			startsWith = function (sourse, destination) {
				return sourse.substr(0, destination.length) === destination;
			};

		return isDealProduct != 'N' &&
			startsWith(deliType, reserve) ||
            startsWith(deliType, vendorDeli) ||
			onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_ETC');
	},
	onToggleBtnMovePlanDetail : function( isShow ) {
		$( '#btnMove' ).toggle( isShow );
	},
	lPad : function(str, totalLength, altStr){
		if(typeof str !== 'string'){
			str = str.toString();
		}

		return lpad(str, totalLength, altStr);
	},
    isCheckedMobileSearchFilterCheckbox : function(type, value) {
        var params = location.search.replace('?', '').split('&');
        var returnValue = false;

        for(var i = 0, len = params.length; i < len; i++) {
            var param = params[i].split('=');

            if(param[0] === type && param[1].indexOf(value) != -1) {
                returnValue = true;
			}
        }
        return returnValue;
	},
	isDeal : function(onlineProdCd) {
		return onlineProdCd === '05';
	},
    isShowDelivery : function(onlineProdCd, deliType) {
		return deliType.indexOf('01') === 0
			|| deliType.indexOf('02') === 0
			|| deliType.indexOf('04') === 0
			|| (onlineProdCd === '08')
	},
	formatTimeStamp : function(timeStamp, format) {
		var date = new Date(timeStamp);

		return date.format(format);
	},
	deliveryIcons : function (onlineProdCd, deliType, store) {
        var SMS = {
            name : '문자발송',
            css : 'icon-band-delivery8'
        };
        var HOLIDAY_STORE = {
            name : '명절매장배송',
            css : 'icon-band-holi-1'
        };
        var HOLIDAY_STORE_PARCEL = {
            name : '명절택배배송',
            css : 'icon-band-holi-4'
        };
        var HOLIDAY_VENDOR_PARCEL = {
            name : '명절업체배송',
            css : 'icon-band-holi-5'
        };
        var HOLIDAY_COLD_STORAGE = {
            name : '명절냉장배송',
            css : 'icon-band-holi-3'
        };
        var HOLIDAY_REFRIGERATION = {
            name : '명절냉동배송',
            css : 'icon-band-holi-2'
        };
        var STORE = {
            name : '매장배송' ,
            css : 'icon-band-delivery1'
        };
        var STORE_PARCEL = {
            name : '매장택배',
            css : 'icon-band-delivery2'
        };
        var FRESH_STORE = {
                name : '신선센터택배',
                css : 'icon-band-delivery979'
            };
        var STORE_PICK_UP = {
            name : '스마트픽',
            css : 'icon-band-delivery3'
        };
        var OVERSEA = {
            name : '해외배송',
            css : 'icon-band-delivery15'
        };
        var VENDOR_PARCEL = {
            name : '업체택배',
            css : 'icon-band-delivery4'
        };

        var DELIVERY_TYPES = {
        	'01' : STORE,
			'02' : VENDOR_PARCEL,
			'03' : SMS,
			'07' : STORE_PARCEL,
			'08' : OVERSEA
		};

        var HOLIDAY_DELIVERY_TYPE_CODES = {
        	'00' : HOLIDAY_STORE,
			'01' : HOLIDAY_STORE_PARCEL,
            '05' : HOLIDAY_STORE_PARCEL,
			'03' : HOLIDAY_VENDOR_PARCEL,
			'02' : HOLIDAY_COLD_STORAGE,
			'04' : HOLIDAY_REFRIGERATION
		};

		if(onlineProdCd === '05') {
            return DELIVERY_TYPES[deliType];
		} else if(onlineProdCd === '08') {
			return SMS;
		} else if(deliType.indexOf('06') === 0) {
            return HOLIDAY_DELIVERY_TYPE_CODES[deliType.replace('06[!@!]', '')];
		} else if(onlineProdCd === '09') {
            return OVERSEA;
		} else if(store && store == '979'){
			return FRESH_STORE;
		} else if(deliType.indexOf('01') === 0) {
			return STORE;
		} else if(deliType === '02') {
			return STORE_PARCEL;
		} else if(deliType === '03' || deliType === '07') {
			return STORE_PICK_UP;
		} else if(deliType === '04') {
			return VENDOR_PARCEL;
		} else {
			return {
				css : '',
				name : ''
			}
		}
	},
    isGiftMall : function (onlineProdCd, deliType) {
		return deliType.indexOf('06') !== -1;
	},
	promotions : function (promoProdIcon) {
		if(!promoProdIcon) {
			return '';
		}

		var promotions = promoProdIcon.split('|');
		var returnValue = [];

		for(var i = 0, len = promotions.length; i < len; i++) {
			if(promotions[i] !== '') {
				var icons = promotions[i].split(':');

                returnValue.push({
                    orderNo: icons[0],
                    iconType: icons[4].indexOf('-') !== -1 ? 'type' + icons[4] : icons[1],
                    iconNm: icons[2],
                    iconDesc: icons[3]
                });
			}
		}

		return returnValue;
	},
	isSoldOut : function(soutYn, onlineProdTypeCd) {
        return soutYn === 'Y' && !(onlineProdTypeCd === $.utils.config('onlineProdTypeCdDeal'));
	},
    getItemImagePath : function(mdSrcmkCd, sizeCode, seq, fileType) {
        if(!mdSrcmkCd) {
            return '';
        }

        switch (sizeCode) {
            case '400' :
                sizeCode = '500';
                break;
            case '220' :
                sizeCode = '250';
                break;
            case '154' :
                sizeCode = '160';
                break;
            case '100' :
                sizeCode = '100';
                break;
            case '90' :
                sizeCode = '100';
                break;
        }

        var dirName = mdSrcmkCd.substring(0, 5).trim();

        return $.utils.config('LMCdnStaticRootUrl') + '/images/prodimg/' + dirName + '/' + mdSrcmkCd + '_' + seq + '_' + sizeCode + (fileType || '.jpg');
	},
	getItemWideImagePath : function(mdSrcmkCd) {
        if (!mdSrcmkCd) return '';

        var dirName = mdSrcmkCd.substring(0, 5).trim();
        var dirSubName = mdSrcmkCd.substring(5, 9).trim();
        
        return $.utils.config('LMCdnStaticRootUrl') + '/images/prodimg/wide/' + dirName + '/' + dirSubName + '/' + mdSrcmkCd + '_00_720_405.jpg';
	},
	replaceCounselContentCharacter : function(str) {
		if(str) {
			str = str.replace(/\[\]/g,'');
			str = str.replace(/\=\=\[/g,'<!--');
			str = str.replace(/\]\=\=/g,'-->');
		}

		return str;
	},
	lineBreakCheck: function(str, line, breaker) {
		var breaker = breaker || '\n',
			str = str.split(breaker),
			maxLine = (str.length > line) ? line : str.length,
			reStr = '';
		for (;maxLine > 0;maxLine--) {
			reStr = str[maxLine-1] + reStr;
			if (maxLine > 1) reStr = '<br>' + reStr;
		}

		return reStr;
	},
	saleRatio: function ( num1, num2 ) {
		return 100 - Math.ceil(num1 / num2 * 100);
	}
});
//js template register
//ex) $.templates( [templatename], [templatecode] )

$.templates( 'basketOptionForDefault' ,
'<section class="{{:~className(layerClass)}} basket-option">' +
	'<div class="container">' +
		'<h1 class="tit-h">{{:title}}</h1>' +
		'<div class="contents">'+
			'<form>' +
				'<div class="opt-wrap">' +
					'<div class="optselect hidden" id="itemField"></div>'+
					'<div class="optselect hidden" id="optionField"></div>'+
					'<div class="optselect hidden" id="subOptionField"></div>'+
					'<div class="optselect">' +
						'<dl>' +
							'<dt>수량</dt>'+
							'<dd class="select-type1">'+
								'{{include tmpl="quantity" /}}' +
							'</dd>'+
						'</dl>'+
					'</div>' +
				'</div>'+
				'<div class="set-btn">' +
					'<input type="submit" class="btn-form-type1 layerpop-complete" value="확인">' +
				'</div>'+
			'</form>' +
		'</div>'+
	'</div>' +
	'<button type="button" class="btn-ico-close" id="btnClose"><i>팝업 닫기</i></button>'+
'</section>');

$.templates( 'basketOptionForPick',
'<section class="{{:~className(layerClass)}} basket-option pick">' +
	'<div class="container">' +
		'<h1 class="tit-h">{{:title}}</h1>' +
		'<div class="contents">'+
			'<div class="optselect">' +
				'<div class="goodsadded">' +
					'<p>상품을 {{:unit}}개 단위로 선택해주세요.</p>' +
				'</div>'+
			'</div>' +
			'<form>' +
				'<div class="opt-wrap">' +
					'<div class="optselect">' +
						'{{for options}}' +
						'<dl>' +
							'<dt>[골라담기]{{:name}}</dt>'+
							'<dd class="select-type1">'+
								'{{include tmpl="quantityByPick" /}}' +
							'</dd>'+
						'</dl>'+
						'{{/for}}' +
					'</div>' +
				'</div>'+
				'<div class="set-btn">' +
					'<input type="submit" class="btn-form-type1 layerpop-complete" value="확인" >' +
				'</div>'+
			'</form>' +
		'</div>'+
	'</div>' +
	'<button type="button" class="btn-ico-close" id="btnClose"><i>팝업 닫기</i></button>'+
'</section>');

$.templates( 'mobileBasketOptionForDefault',
	'<div class="mask"></div>' +
	'<div class="{{:~className(layerClass)}}">' +
		'<form>' +
			'<div class="option-select">' +
				'<p class="title">옵션 선택</p>' +
				'<div class="select hidden" id="itemField"></div>'+
				'<div class="select hidden" id="optionField"></div>'+
				'<div class="select hidden" id="subOptionField"></div>'+
				'<div class="option-select">' +
					'<p class="title">수량 선택</p>' +
					'{{include tmpl="mobileQuantity" /}}' +
				'</div>' +
			'</div>' +
			'<div class="wrap-btn">' +
				'<button type="button" class="btn-common-color4 close">취소</button>'+
				'<input type="submit" class="btn-common-color1" value="확인" />' +
			'</div>' +
		'</form>' +
	'</div>');

$.templates( 'mobileBasketOptionForPick',
	'<div class="mask"></div>' +
	'<div class="{{:~className(layerClass)}}">' +
		'<form>' +
			'<div class="option-select">' +
				'<p class="title">골라담기</p>' +
				'<p class="desc">상품을 {{:unit}}개 단위로 선택해주세요.</p>'+
				'<p class="error hidden"></p>' +
			'</div>'+
			'<div class="option-select">' +
				'{{for options}}' +
				'<div class="goodsadded {{if #index !== 0}}subadded{{/if}} lie-table">' +
					'<div class="left">{{:name}}</div>' +
					'{{include tmpl="mobileQuantityByPick"/}}' +
				'</div>' +
				'{{/for}}' +
				'<p class="goodsadded result">' +
					'점포 재고상황에 따라 상품구성이 상이할 수 있습니다.<br/>많은 양해 부탁드립니다.'+
				'</p>' +
			'</div>' +
			'<div class="wrap-btn">' +
				'<button type="button" class="btn-common-color4 close">취소</button>'+
				'<input type="submit" class="btn-common-color1" value="확인" />' +
			'</div>' +
		'</form>' +
	'</div>');

$.templates( 'quantity', '<div class="set-spinner-type2-large wrapper-quantity">'+
		'<input type="text" name="quantity" class="quantity" title="수량" value="1" style="ime-mode:disabled" maxlength="15" data-min-quantity="1" data-max-quantity="1" disabled>'+
		'<button type="button" class="sp-minus" disabled><i class="ico-minus">수량 감소</i></button>' +
		'<button type="button" class="sp-plus" disabled><i class="ico-plus">수량 증가</i></button>' +
'</div>');

$.templates( 'quantityByPick','<div class="set-spinner-type2-large wrapper-quantity">'+
		'<input type="text" name="{{:value}}" class="quantity" title="수량" value="0" style="ime-mode:disabled" maxlength="15" data-min-quantity="0" data-max-quantity="{{:maxQuantity}}">'+
		'<button type="button" class="sp-minus"><i class="ico-minus">수량 감소</i></button>' +
		'<button type="button" class="sp-plus"><i class="ico-plus">수량 증가</i></button>' +
'</div>');

$.templates( 'mobileQuantity' , '<div class="spinner">' +
		'<button type="button" class="minus" disabled>'+
			'<i class="icon-sum-minus">감소</i>' +
		'</button>' +
		'<input type="tel" name="quantity" value="1" maxlength="15" maxlength="15" data-min-quantity="1" data-max-quantity="1" disabled>' +
		'<button type="button" class="plus" disabled>'+
			'<i class="icon-sum-plus">추가</i>' +
		'</button>' +
'</div>');

$.templates( 'mobileQuantityByPick', '<div class="spinner right">' +
		'<button type="button" class="minus">'+
			'<i class="icon-sum-minus">감소</i>' +
		'</button>' +
		'<input type="tel" name="{{:value}}" class="quantity" value="0" maxlength="15" data-min-quantity="0" data-max-quantity="{{:maxQuantity}}">' +
		'<button type="button" class="plus">'+
			'<i class="icon-sum-plus">추가</i>' +
		'</button>' +
'</div>');


$.templates( 'selectBox',
'<dl>'+
	'<dt>{{:label}}</dt>'+
	'<dd class="select-type1">'+
		'<label for="option{{:productCode}}">선택하세요</label>'+
		'<select id="option{{:productCode}}" name="itemCode">'+
			'<option value="" selected>선택하세요</option>' +
			'{{for optionData}}' +
			'<option value="{{:value}}" {{if isSoldOut }}disabled{{/if}} data-variation="{{:variationYn}}">{{:name}}{{if isSoldOut}}-품절{{/if}}</option>' +
			'{{/for}}'+
		'</select>'+
	'</dd>'+
'</dl>');

$.templates( 'selectBoxForDeal',
'<dl>'+
	'<dt>{{:label}}</dt>'+
	'<dd class="select-type1">'+
		'<label for="option{{:productCode}}">선택하세요</label>'+
		'<select id="option{{:productCode}}" name="item{{:productCode}}">'+
			'<option value="" selected>선택하세요</option>' +
			'{{for optionData}}' +
			'<option value="{{:productCode}}" {{if isSoldOut}}disabled{{/if}} data-option-yn="{{:hasOption}}" data-variation="{{:variationYn}}">{{:name}}{{if isSoldOut}}-품절{{/if}}</option>' +
			'{{/for}}'+
		'</select>'+
	'</dd>'+
'</dl>');

$.templates( 'mobileSelectBox',
'<select id="option{{:productCode}}" name="itemCode">' +
	'<option value="" selected>선택하세요</option>'+
	'{{for optionData}}' +
	'<option value="{{:value}}" {{if isSoldOut }}disabled{{/if}} data-option-yn="{{:hasOption}}" data-variation="{{:variationYn}}">{{:name}}{{if isSoldOut}}-품절{{/if}}</option>' +
	'{{/for}}'+
'</select>');

$.templates( 'benefitLayer',
	'<section class="layerpop-type2 benefit-layer {{:~className(className)}}">' +
		'<div class="container">' +
			'<h1 class="tit-h">할인 내역 </h1>' +
			'<div class="contents">' +
				'<ul class="bul-circle">' +
					'{{for data}}' +
						'<li>{{:title}} <em class="txt-red flt-right">{{:value}}</em></li>' +
					'{{/for}}'+
				'</ul>' +
			'</div>' +
		'</div>' +
		'<a href="javascript:;" class="btn-ico-close js-close" title="레이어팝업 닫기"><i>할인 내역 레이어팝업 닫기</i></a>' +
	'</section>');

$.templates( 'toastLayer',
	'<section class="{{:layerClass}}">'+
		'<div class="container">'+
			'<div class="contents">{{:contents}}</div>'+
		'</div>'+
	'</section>');

$.templates( 'popupLayer',
	'<section class="{{:layerClass}}">' +
		'<div class="container">'+
			'<h1 class="tit-h">{{:title}}</h1>'+
			'<div class="contents">{{:contents}}</div>'+
		'</div>'+
		'<button type="button" class="btn-ico-close"><i>레이어팝업 닫기</i></button>'+
	'</section>');

$.templates( 'bookmarkLayer',
	'<section class="layerpop-bookmark layerpop-target layer-bottom">' +
		'{{:contents}}'+
		'<a href="/event/enter.do?categoryId=C0070347" class="btn-type1">{{if isBookMart}}혜택 받으러 가기{{else}}바로가기 ON 하기{{/if}}</a>'+
	'</section>');

$.templates( 'loadingBarForPC',
		'<div id="wrapLoadingBar">' +
			'<img src="{{:~imageCdnPath()}}/lm2/images/layout/loadinglogo.gif" alt="롯데마트Mall">' +
		'</div>');

$.templates( 'loadingBarForMobile',
		'<div class="wrapLoadingBar" style="display:none;">' +
		'<div class="container">' +
		'	<div class="bar">' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'</div>' +
		'<div class="logo">' +
		'	<img src="{{:~sImageCdnPath()}}/lm2/images/layout/m-loadinglogo.png" alt="롯데마트Mall">' +
		'</div>' +
		'</div></div>');

$.templates( 'rnbBasket',
	'<strong class="tab01 {{if activeClass === ' + "'tab01'" + '}}active{{/if}}" data-tab-id="tab01">' +
'<a href="#RNBcartTab01">장바구니<em>{{:normalBasket.itemList.length}}</em></a></strong>' +
	'<div class="tab-wrap tab-cont" id="RNBcartTab01">' +
		'{{if normalBasket.itemList.length > 0}}' +
			'<ul class="item-list">' +
				'{{for normalBasket.itemList tmpl="rnbBasketItem" /}}' +
			'</ul>' +
		'{{/if}}' +
		'<div class="no-data-result" {{if normalBasket.itemList.length != 0}}style="display: none;"{{/if}}>' +
			'<p class="txt">장바구니에 담긴 상품이 없습니다.</p>' +
		'</div>' +
	'</div>' +
	'<strong class="tab02 {{if activeClass === ' + "'tab02'" + '}}active{{/if}}" data-tab-id="tab02" {{if (highwayStrYn == ' + "'Y'" + ' || zipSeq == ' + "'2472039'" + ' || isSmartPickUp == ' + "'Y'" + ') || pickUpYn == ' + "'Y'" + '}} style="display: none;" {{/if}}>'+
	'<a href="#RNBcartTab02">정기배송<em>{{:periDeliBasket.itemList.length}}</em></a></strong>' +
	'<div class="tab-wrap tab-cont" id="RNBcartTab02" {{if (highwayStrYn == ' + "'Y'" + ' || zipSeq == ' + "'2472039'" + ' || isSmartPickUp == ' + "'Y'" + ') || pickUpYn == ' + "'Y'" + '}} style="display: none;" {{/if}}>' +
		'{{if periDeliBasket.itemList.length > 0}}' +
			'<ul class="item-list">' +
				'{{for periDeliBasket.itemList tmpl="rnbBasketItem" /}}' +
			'</ul>' +
		'{{/if}}' +
		'<div class="no-data-result" {{if periDeliBasket.itemList.length != 0}}style="display: none;"{{/if}}>' +
			'<p class="txt">장바구니에 담긴 상품이 없습니다.</p>' +
		'</div>' +
	'</div>'
);

$.templates( 'rnbBasketItem',
		'<li id="RNB_Basket_{{:BSKET_NO}}">'+
			'<a href="javascript:goProductDetail(' + "'{{:CATEGORY_ID}}','{{:PROD_CD}}','N','','','{{:MALL_DIVN_CD}}'" + ')" class="thumb">'+
				'{{if STOCK_QTY < 1}}'+
				'<span class="prod-sout">'+
					'<img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"/>'+
				'</span>'+
				'<img src="{{:~getLmCdnStaticItemImagePath(MD_SRCMK_CD)}}" data-PROD_CD="{{:PROD_CD}}" data-MD_SRCMK_CD="{{:MD_SRCMK_CD}}" alt="{{:PROD_NM}} 품절" onerror="showNoImage(this)">'+
				'{{else ~isAdult(ADL_YN)}}'+
				'<img src="{{:~getAdultImagePath()}}" data-PROD_CD="{{:PROD_CD}}" data-MD_SRCMK_CD="{{:MD_SRCMK_CD}}" alt="19세 이상 이미지 표시">'+
				'{{else}}'+
				'<img src="{{:~getLmCdnStaticItemImagePath(MD_SRCMK_CD)}}" data-PROD_CD="{{:PROD_CD}}" data-MD_SRCMK_CD="{{:MD_SRCMK_CD}}" alt="{{:PROD_NM}}" onerror="showNoImage(this)">'+
				'{{/if}}'+
				'<div class="wrap-item">'+
					'<p class="prod-name"><strong>{{:PROD_NM}}</strong></p>'+
					'<p class="count"><em>{{:BSKET_QTY}}</em>개</p>'+
					'<p class="price-max">구매가 <em>{{:~numberFormat(PROMO_MAX_VAL * BSKET_QTY)}}</em>원</p>'+
				'</div>'+
			'</a>'+
			'<button type="button" class="item-remove" onclick="RNB.basket.removeBasket(' + "'{{:BSKET_NO}}'" + ')">삭제</button>'+
		'</li>'
);


$.templates( 'rnbCoupon',
		'<strong class="layer-tit">보유쿠폰<em>{{:totalCount}}</em></strong>' +
		'<div class="scroll-wrap">' +
			'<ul class="coupon">' +
				'{{if ~isLogin() === false}}' +
					'<li><em class="desc">로그인 후 확인하실 수 있습니다.</em></li>' +
				'{{else}}' +
					'{{if list.length > 0 }}' +
						'{{for list}}' +
							'<li><a href="/mymart/selectMyCoupon.do" class="coupon-link"><em class="txt">{{:COUPON_NM}}</em><span class="date">{{:USE_END_DY_FMT}} 까지</span></a></li>' +
						'{{/for}}'+
					'{{else}}' +
						'<li><em class="desc">보유하고 있는 쿠폰이 없습니다.</em></li>' +
					'{{/if}}' +
				'{{/if}}' +
			'</ul>' +
		'</div>' +
		'<strong class="layer-tit">만기 예정 쿠폰<em>{{:expireCount}}</em></strong>' +
		'<div class="scroll-wrap">' +
			'<ul class="coupon">' +
				'{{if ~isLogin() === false}}' +
					'<li><em class="desc">로그인 후 확인하실 수 있습니다.</em></li>' +
				'{{else}}' +
					'{{if expireList.length > 0 }}' +
						'{{for expireList}}' +
							'<li><a href="/mymart/selectMyCoupon.do" class="coupon-link"><em class="txt">{{:COUPON_NM}}</em><span class="date expiration-date">{{:USE_END_DY_FMT}} 만료</span></a></li>' +
						'{{/for}}'+
					'{{else}}' +
						'<li><em class="desc">만료 예정인 쿠폰이 없습니다.</em></li>' +
					'{{/if}}' +
				'{{/if}}' +
			'</ul>' +
		'</div>' +
		'<a href="/event/eventMain.do?SITELOC=AE004" class="link-couponlist">더 많은 쿠폰 받기</a>' +
		'<button type="button" class="layer-close">쿠폰레이어 닫기</button>'
);

$.templates( 'rnbHistory',
		'<strong class="layer-tit">히스토리<em>{{:totalCount}}</em></strong>'+
		'<div class="scroll-wrap">'+
			'<ul class="item-list">'+
			'{{for history}}'+
				'<li>'+
					'<a href="{{:~getRNBHistoryLink(histTypeCd, categoryId, contentsNo, prodCd,mallDivnCd)}}"'+
						'class="thumb" {{if histTypeCd !=="05"}}id="RNB_History_{{:histSeq}}"{{/if}}>'+
						'{{if histTypeCd === "01"}}'+
							'{{if (prodCd).substring(0,1) != "D" && stockQty < 1}}'+
								'<span class="prod-sout">'+
									'<img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"/>'+
								'</span>'+
								'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}} 품절" onerror="showNoImage(this)">'+
							'{{else ~isAdult(adlYn)}}'+
								'<img src="{{:~getAdultImagePath()}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="19세 이상 이미지 표시" >'+
							'{{else}}'+
								'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}}" onerror="showNoImage(this)">'+
							'{{/if}}'+
							'<div class="wrap-item">'+
								'<p class="prod-name"><strong>{{:histNm}}</strong></p>'+
								'{{if sellPrc > currSellPrc }}'+
									'<p class="prod-price">판매가<span>{{:~numberFormat(sellPrc)}}</span>원</p>'+
									'<p class="price-max">최저가<em>{{:~numberFormat(currSellPrc)}}</em>원</p>'+
								'{{else}}'+
									'<p class="price-max">판매가<em>{{:~numberFormat(currSellPrc)}}</em>원</p>'+
								'{{/if}}'+
							'</div>'+
						'{{else}}' +
							'<img src="{{:~getRNBHistoryImage(histTypeCd, histImg)}}" alt="{{:histNm}}" {{if histTypeCd !== "02"}}onerror="OnErrorImage(this, 80, 80, {{:~getRNBHistoryErrorImage(histTypeCd)}})"{{/if}}>'+
							'<div class="wrap-info">'+
								'<strong {{if histTypeCd !== "02"}}class="{{:~getRNBHistroyClass(histTypeCd)}}"{{/if}}>{{:~getRNBHistroyTitle(histTypeCd)}}</strong>'+
								'<p class="txt">{{:histNm}}</p>'+
							'</div>'+
						'{{/if}}' +
				'</a>'+
				'{{if histTypeCd === "01"}}' +
					'{{if optionYn == "Y"}}'+
					'<button type="button" class="btn-cart" onclick="RNB.goHistoryProdDetail(' + "'{{:categoryId}}','{{:prodCd}}'" + ')"><span class="txt">장바구니에 담기</span></button>'+
					'{{else}}'+
					'<button type="button" class="btn-cart" onclick="RNB.basket.addBasket({prodCd:' + "'{{:prodCd}}'" + ', categoryId:' + "'{{:categoryId}}'" + '})"><span class="txt">장바구니에 담기</span></button>'+
					'{{/if}}'+
					'<button type="button" class="item-remove" onclick="RNB.history.removeHistory(' + "'{{:histSeq}}'" + ')">삭제</button>'+
				'{{/if}}'+
				'{{if histTypeCd === "05"}}' +
					'<button type="button" class="item-remove" onclick="RNB.history.removeHistory(' + "'{{:histSeq}}'" + ')">삭제</button>' +
				'{{/if}}' +
				'</li>'+
			'{{/for}}'+
			'</ul>'+
			'<div class="no-data-result" {{if history.length != 0}}style="display: none;"{{/if}}>'+
				'<p class="txt">히스토리가 없습니다.</p>'+
			'</div>'+
			'<button type="button" class="layer-close">히스토리 레이어 닫기</button>'+
		'</div>'
);

$.templates( 'rnbQuickHistory',
		'{{if history.length < 1}}'+
			'<p class="list-none">최근 조회한<em>히스토리가</em>없습니다.</p>'+
		'{{else}}'+
			'<ul>'+
			'{{for history}}'+
				'{{if #index%2 === 0}}'+
					'<li {{if #getIndex() === 0}}class="active"{{/if}}>'+
				'{{/if}}'+
				'<a href="{{:~getRNBHistoryLink(histTypeCd, categoryId, contentsNo, prodCd,mallDivnCd)}}"'+
					'{{if histTypeCd ==="01"}} class="prod-item"{{/if}}' +
					'{{if histTypeCd !=="05"}} id="RNB_QuickHistory_{{:histSeq}}"{{/if}}>'+
					'{{if histTypeCd ==="01"}}' +
						'{{if (prodCd).substring(0,1) != "D" && stockQty < 1}}'+
						'<span class="prod-sout">'+
							'<img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"/>'+
						'</span>'+
							'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}} 품절" onerror="showNoImage(this)">'+
						'{{else ~isAdult(adlYn)}}'+
							'<img src="{{:~getAdultImagePath()}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="19세 이상 이미지 표시" >'+
						'{{else}}'+
							'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}}" onerror="showNoImage(this)">'+
						'{{/if}}'+
						'<div class="wrap-item">'+
							'<p class="prod-name"><strong>{{:histNm}}</strong></p>'+
							'<p class="price-max">판매가 <em>{{:~numberFormat(currSellPrc)}}</em>원</p>'+
						'</div>'+
					'{{else}}' +
						'<div class="wrap-info">'+
							'<strong class="{{:~getRNBHistroyClass(histTypeCd)}}">{{:~getRNBHistroyTitle(histTypeCd)}}</strong>'+
							'<p class="txt">{{:histNm}}</p>'+
						'</div>'+
					'{{/if}}' +
				'</a>'+
				'{{if #index%2 === 1 }}'+
				'</li>'+
				'{{/if}}'+
			'{{/for}}'+
			'</ul>'+
			'<div class="control-btn">'+
				'<button type="button" class="prev">이전</button>'+
				'<button type="button" class="next">다음</button>'+
			'</div>'+
	'{{/if}}'
);

$.templates( 'emptyMainBest',
	'<div class="cont-wrap">' +
		'<strong class="message-txt">원하는 상품이 없다면 이렇게 찾아보세요.</strong>' +
		'<ul>' +
			'<li><a href="/plan/planDetail.do?CategoryID=C2060003&MkdpSeq=000000000002" class="link01">마일리지, 사은품 받자 사은행사</a></li>' +
			'<li><a href="/best/bestMain.do" class="link02">베스트 상품만 콕콕 베스트</a></li>' +
			'<li><a href="/plan/planMain.do?SITELOC=AC022&STR_CD={{:mainStoreCode}}&lotteEmpYn={{:lotteEmpYn}}&CategoryID=C206" class="link03">행사의 모든것 기획전</a></li>' +
		'</ul>' +
	'</div>'
);

$.templates( 'planDetailOption',
	'<select>' +
		'{{if categoryList.length === 0 }}' +
			'<option>등록된 기획전이 없습니다.</option>'+
			'{{:~onToggleBtnMovePlanDetail(false)}}' +
		'{{else}}' +
			'{{for categoryList}}' +
				'{{if ~isAppendPlanDetailOption( CATEGORY_ID )}}' +
					'<option value="{{:CATEGORY_ID}},{{:MKDP_SEQ}}" {{if #index === 0}}selected{{/if}}>' +
						'{{:MKDP_NM}}' +
					'</option>'+
				'{{/if}}' +
				'{{:~onToggleBtnMovePlanDetail(true)}}' +
			'{{/for}}'+
		'{{/if}}' +
	'</select>'
);

$.templates( 'mobileBenefitLayer',
	'<div class="layerpopup-type1 {{:~className(className)}}">' +
		'<ul class="discount-list">' +
			'{{for data}}' +
				'<li>'+
					'<p class="left">{{:title}}</p>' +
					'<p class="number-point right">{{:value}}</p>'+
				'</li>' +
			'{{/for}}' +
		'</ul>' +
	'</div>'
);

$.templates( 'mobileRnbMyHistory',
	'{{if historyList.length > 0}}' +
		'{{for historyList}}' +
			'<li>' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd}}" data-gtm="M094">' +
					'<div class="thumbnail">' +
						'{{if ~isSoldProductItem(prodCd, stockQty )}}' +
							'<span class="prod-sout"><img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"></span>' +
						'{{/if}}'+
						'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" onerror="javascript:showNoImage(this,80,80)" alt="">' +
					'</div>' +
					'<div class="conts">' +
						'<p class="title">{{:histNm}}</p>' +
						'<p class="price">' +
							'{{if sellPrc === currSellPrc}}' +
								'<strong class="number">{{:~numberFormat(sellPrc)}}<i>원</i></strong>' +
							'{{else selPrc < currSellPrc }}' +
								'<strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{else}}' +
								'<del class="number">{{:~numberFormat(sellPrc)}}<i>원</i></del><strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{/if}}' +
						'</p>' +
					'</div>' +
				'</a>' +
				'<button type="button" class="toggle" data-gtm="M095"><i class="icon-common-gntoggle"></i></button>' +
				'<div class="wrap-togglebox">' +
					'<button class="action-trash" data-gtm="M096" data-seq="{{:histSeq}}"><i class="icon-common-trash">삭제</i></button>' +
					'<button class="action-cart" data-gtm="M097" data-category-id="{{:categoryId}}" data-product-code="{{:prodCd}}" data-option-yn="{{:optionYn}}"><i class="icon-common-cart">장바구니</i></button>' +
				'</div>'+
			'</li>' +
		'{{/for}}' +
	'{{else}}' +
		'<div class="list-empty">최근 본 상품이 없습니다.</div>' +
	'{{/if}}'
);

$.templates( 'mobileRnbMyHistoryForMenu',
	'{{if historyList.length > 0 }}' +
		'{{for historyList}}' +
			'<li>' +
				'{{if histTypeCd === "02"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/cate/productList.do?CategoryID={{:categoryId}}">' +
						'<div class="thumbnail label-category">카테고리</div>' +
						'<div class="conts">' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>'+
					'</a>' +
				'{{else histTypeCd === "03"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/plan/planDetail.do?CategoryID={{:categoryId}}&MkdpSeq={{:contentsNo}}&viewType=L&mode=M&pdivnSeq=001">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getLmCdnStaticImagePath(histImg)}}" onerror="javascript:this.src=\'{{:~lmCdnV3RootPath()}}/images/layout/noimg_plan_list_80x80.jpg\'"/>' +
						'</div>' +
						'<div class="conts">' +
							'<p class="tag"><i class="icon-tag-notic1">{{:histTypeNm }}</i></p>' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>' +
					'</a>' +
				'{{else histTypeCd === "04"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/evt/mEventDetail.do?categoryId={{:categoryId}}">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getLmCdnStaticImagePath(histImg)}}" onerror="javascript:this.src=\'{{:~lmCdnV3RootPath()}}/images/layout/noimg_event_80x63.jpg\'"/>' +
						'</div>' +
						'<div class="conts">' +
							'<p class="tag"><i class="icon-tag-notic2">{{:histTypeNm }}</i></p>' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>' +
					'</a>' +
				'{{else histTypeCd === "05"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/trend/mobileTrendDetail.do?CategoryID={{:categoryId}}&trendNo={{:contentsNo}}&trendTabNo=01">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getLmCdnStaticImagePath(histImg)}}" onerror="javascript:this.src=\'{{:~lmCdnV3RootPath()}}/images/layout/noimg_trend_80x63.jpg\'"/>' +
						'</div>' +
						'<div class="conts">' +
							'<p class="tag"><i class="icon-tag-notic3">{{:histTypeNm }}</i></p>' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>' +
					'</a>' +
				'{{/if}}' +
				'<button type="button" class="toggle"><i class="icon-common-gntoggle"></i></button>' +
				'<div class="wrap-togglebox">' +
					'<button class="action-trash" data-seq="{{:histSeq}}"><i class="icon-common-trash">삭제</i></button>' +
				'</div>' +
			'</li>' +
		'{{/for}}' +
	'{{else}}' +
		'<div class="list-empty">최근 본 메뉴가 없습니다.</div>' +
	'{{/if}}'
);

$.templates( 'mobileRnbMyByHistory',
		'{{if historyList.length > 0}}' +
			'{{for historyList}}' +
				'<li>' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd}}">' +
						'<div class="thumbnail">' +
							'{{if ~isSoldProductItem(prodCd, stockQty )}}' +
								'<span class="prod-sout"><img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"></span>' +
							'{{/if}}'+
							'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" onerror="javascript:showNoImage(this,80,80)" alt="">' +
						'</div>' +
						'<div class="conts">' +
							'<p class="title">{{:prodNm}}</p>' +
							'<p class="price">' +
								'{{if sellPrc === currSellPrc}}' +
									'<strong class="number">{{:~numberFormat(sellPrc)}}<i>원</i></strong>' +
								'{{else selPrc < currSellPrc }}' +
									'<strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
								'{{else}}' +
									'<del class="number">{{:~numberFormat(sellPrc)}}<i>원</i></del><strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
								'{{/if}}' +
							'</p>' +
						'</div>' +
					'</a>' +
					'<button type="button" class="toggle"><i class="icon-common-gntoggle"></i></button>' +
					'<div class="wrap-togglebox">' +
						'<button class="action-cart" data-category-id="{{:categoryId}}" data-product-code="{{:prodCd}}" data-option-yn="{{:optionYn}}" data-item-code="{{:itemCd}}"><i class="icon-common-cart">장바구니</i></button>' +
					'</div>'+
				'</li>' +
			'{{/for}}' +
		'{{else}}' +
			'<div class="list-empty">최근 구매 상품이 없습니다.</div>' +
		'{{/if}}'
	);
$.templates( 'mobileRnbMyByHistory',
	'{{if historyList.length > 0}}' +
		'{{for historyList}}' +
			'<li>' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd}}">' +
					'<div class="thumbnail">' +
						'{{if ~isSoldProductItem(prodCd, stockQty )}}' +
							'<span class="prod-sout"><img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"></span>' +
						'{{/if}}'+
						'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" onerror="javascript:showNoImage(this,80,80)" alt="">' +
					'</div>' +
					'<div class="conts">' +
						'<p class="title">{{:prodNm}}</p>' +
						'<p class="price">' +
							'{{if sellPrc === currSellPrc}}' +
								'<strong class="number">{{:~numberFormat(sellPrc)}}<i>원</i></strong>' +
							'{{else selPrc < currSellPrc }}' +
								'<strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{else}}' +
								'<del class="number">{{:~numberFormat(sellPrc)}}<i>원</i></del><strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{/if}}' +
						'</p>' +
					'</div>' +
				'</a>' +
				'<button type="button" class="toggle"><i class="icon-common-gntoggle"></i></button>' +
				'<div class="wrap-togglebox">' +
					'<button class="action-cart" data-category-id="{{:categoryId}}" data-product-code="{{:prodCd}}" data-option-yn="{{:optionYn}}" data-item-code="{{:itemCd}}"><i class="icon-common-cart">장바구니</i></button>' +
				'</div>'+
			'</li>' +
		'{{/for}}' +
	'{{else}}' +
		'<div class="list-empty">최근 구매 상품이 없습니다.</div>' +
	'{{/if}}'
);
$.templates( 'shareLayer',
	'<article class="layerpopup-share">' +
		'<header class="header">' +
			'<h3 class="title">공유하기</h3>' +
			'<button class="icon-close js-close">닫기</button>' +
		'</header>' +
		'<div class="wrap-sharelist">' +
			'<a href="javascript:;" data-service="kakaotalk"><i class="icon-share-kakao"></i>카카오톡</a>' +
			'<a href="javascript:;" data-service="facebook"><i class="icon-share-facebook"></i>페이스북</a>' +
			'<a href="javascript:;" data-service="twitter"><i class="icon-share-twiter"></i>트위터</a>' +
			'<a href="javascript:;" data-service="sms"><i class="icon-share-sms"></i>SMS</a>' +
			'<a href="javascript:;" data-service="kakaostory" class="hidden"><i class="icon-share-kakaostory"></i>카카오스토리</a>' +
			'<a href="javascript:;" data-service="band" class="hidden"><i class="icon-share-band"></i>밴드</a>' +
			'<a href="javascript:;" data-service="line"><i class="icon-share-line"></i>라인</a>' +
			'<a href="javascript:;" class="link-copy"><i class="icon-share-url"></i>URL 복사</a>' +
		'</div>' +
	'</article>'
);
$.templates( 'appInstallInductionLayer',
	'<div class="wrap-appsetlayer">'+
		'<div class="conts">'+
			'<p class="blind">앱에서 더 큰 혜택!</p>'+
			'<a href="javascript:;">' +
				'<img src="//simage.lottemart.com/lm2/images/contents/2017/07/m-appset-layer.png" alt="APP에서 혜택 받기">' +
			'</a>'+
			'<button type="button" class="js-close">괜찮아요. 지금 페이지에서 볼래요</button>'+
		'</div>'+
		'<div class="wrap-mask"></div>'+
	'</div>'
);
$.templates('appInstallInductionLayer2',
	'<div class="wrap-appsetlayer type-qr">'+
		'<div class="conts">'+
			'<p class="blind">앱에서 더 큰 혜택!</p>'+
			'<a href="{{:dlink}}">'+
				'<img src="//simage.lottemart.com/lm2/images/contents/2017/10/m-appset-layer-qr.png" alt="APP에서 혜택 받기">'+
			'</a>'+
			'<button type="button" class="js-close">괜찮아요. 지금 페이지에서 볼래요</button>'+
		'</div>'+
		'<div class="wrap-mask"></div>'+
	'</div>'
);
$.templates( 'holidaysLNBSubLayer',
	'<div class="gry-lnb-depth">'+
		'<p class="title">{{:parentCategoryName}}</p>'+
		'<ul>' +
			'{{for categories ~startSiteLocationIndex=startSiteLocationIndex ~subSiteLocation=subSiteLocation}}' +
				'<li>' +
					'{{if ~startSiteLocationIndex > 0}}' +
						'<a href="/holidays/detail.do?ppass=1&CATEGORY_ID={{:CATEGORY_ID }}&SITELOC=' + "VS{{:~lPad((#getIndex() + ~startSiteLocationIndex), 3, '0')}}" + '">' +
					'{{else}}' +
						'<a href="/holidays/detail.do?ppass=1&CATEGORY_ID={{:CATEGORY_ID }}&SITELOC={{:~subSiteLocation}}">' +
					'{{/if}}' +
						'{{:CATEGORY_NM}}' +
						'{{if ICON_PATH != null && ICON_PATH != "" }}' +
							'<img src="{{:~getLmCdnStaticImagePath(ICON_PATH)}}" alt="{{:CATEGORY_NM}}" onerror="this.remove()" />' +
						'{{/if}}' +
					'</a>' +
				'</li>' +
			'{{/for}}' +
		'</ul>' +
	'</div>'
);
$.templates( 'holidaysProductListPanel',
	'{{if categoryId != null && categoryId != ""}}' +
		'<div name="productPanel" class="{{:productListWrapperClass }}" data-category-id="{{:categoryId }}">' +
	'{{/if}}' +
		'<h3 class="blind">{{:categoryName}}</h3>' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
			'{{if hasMoreButton === true}}' +
				'<div class="area-btn-more">' +
					'<a href="/holidays/detail.do?CATEGORY_ID={{:categoryId}}&SITELOC={{:siteLocation }}"><img src="//simage.lottemart.com/lm2/images/contents/2019/11/holiday/holi_btn_more.png"></a>' +
				'</div>' +
			'{{/if}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}' +
	'{{if categoryId != null && categoryId != ""}}'+
		'</div>'+
	'{{/if}}'
);
$.templates( 'mobileHolidaysProductListPanel',
	'<article id="{{:categoryId}}" name="productPanel" class="{{:classId}}" data-category-id="{{:categoryId }}">' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
			'{{if hasMoreButton === true}}' +
				'<div class="area-holi-morebtn">' +
				'<a href="{{:categoryListUrl}}?CategoryID={{:categoryId}}&SITELOC={{:siteLocation }}" class="holi-btn-list-bottom"><span>더 많은 상품 보기</span></a>' +
				'</div>' +
			'{{/if}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}' +
	'</article>'
);
$.templates( 'holidaysBannerProductListPanel',
	'<h2 class="holi-bullet-title"><span class="title-holi-premium">{{:categoryName}}</span></h2>' +
	'<div name="productPanel" class="{{:productListWrapperClass }}" data-category-id="{{:categoryId }}">' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}' +
	'</div>'
);
$.templates( 'holidaysBannerProductListMd',
		'<h2 class="holi-bullet-title"><span class="holi_title_slide">MD 추천상품</span></h2>' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}'
	);
// MD 상품 추천
$.templates( 'mobileHolidaysBannerProductListPanel',
	'{{if productListHtml != null && productListHtml != ""}}' +
		'{{:productListHtml}}' +
	'{{else}}' +
		'{{include tmpl="emptyProductListPanel" /}}' +
	'{{/if}}'
	);
$.templates( 'emptyProductListPanel',
	'<p class="nonmsg">해당 상품이 없습니다.</p>'
);
$.templates( 'mobileHolidaysBestCategoryTabs',
	'<nav class="holi-sticky-tab">' +
		'<div class="holi-sticky-wrap">' +
			'{{for bestCategories}}' +

			'{{/for}}' +
		'</div>' +
	'</nav>'
);
$.templates('mobilePriceWrap',
		'<div class="price-wrap">'+
			'<div class="price">'+
				'{{if CURR_SELL_PRC > PROM_MAX_VAL}}' +
					'<strong class="point1">{{:~saleRatio(PROM_MAX_VAL, CURR_SELL_PRC)}}%</strong>'+
					'<div class="number">' +
						'<em class="sale">{{:~numberFormat(PROM_MAX_VAL)}}</em>' +
						'<i>원{{if ~isDeal(ONLINE_PROD_TYPE_CD)}}~{{/if}}</i>' +
					'</div>' +
					'<div class="number">' +
						'<em>{{:~numberFormat(CURR_SELL_PRC)}}</em>' +
						'<i>원</i>' +
					'</div>' +
				'{{else}}'+
					'<div class="number">' +
						'<em>{{:~numberFormat(CURR_SELL_PRC)}}</em>' +
						'<i>원{{if ~isDeal(ONLINE_PROD_TYPE_CD)}}~{{/if}}</i></i>' +
					'</div>' +
				'{{/if}}'+
			'</div>'+
		'</div>'
);
$.templates('mobileProductListBadge',
	'<div class="badge">' +
		'{{if FREE_DELIVERY === "Y" && ONLINE_PROD_TYPE_CD != "08" }}' +
			'{{if DELI_AMT_NM !== null && DELI_AMT_NM !== ""}}' +
				'<div class="type01">{{:DELI_AMT_NM}}</div>' +
			'{{/if}}'+
		'{{/if}}' +
		'{{if ~isDealTypeProductItem(ONLINE_PROD_TYPE_CD, DELI_TYPE)}}' +
			'{{if DELI_TP_NM !== null && DELI_TP_NM !== ""}}' +
				'<div class="type01">{{:DELI_TP_NM}}</div>' +
				'{{if PERI_DELI_YN === "Y"}}' +
					'<div class="type01">'+
						'정기배송' +
						'{{if PERI_COUPON != null}}' +
							'[{{: PERI_COUPON}} 할인]' +
						'{{/if}}'+
					 '</div>'+
				 '{{/if}}'+
			'{{/if}}'+
		'{{/if}}' +
		'{{if PROMO_PROD_ICON !== null}}' +
			'{{:~getMobileBage(PROMO_PROD_ICON, 1)}}' +
		'{{/if}}' +
		'{{if IS_EXPRESS_DELIVERY_AREA !== null &&  EXPRESS_DELI_YN !== null}}' +
			'{{if IS_EXPRESS_DELIVERY_AREA === "Y" &&  EXPRESS_DELI_YN === "Y"}}' +
				'<div class="type03">바로배송</div>' +
			'{{/if}}'+
		'{{/if}}'+
	'</div>'
);
$.templates('mobilePeriodShippingSmartRecommendList',
	'{{for list}}' +
	'<li data-panel="product">' +
		'<div class="product-wrap">' +
			'<div class="product-img">' +
			'{{if ~isAdult(ADL_YN)}}' +
				'<img src="{{:~lmCdnV3RootPath()}}/images/layout/m-goods-adult.png" alt="19">'+
			'{{else}}' +
				'{{if ~isSoldOut(SOUT_YN_REAL)}}' +
					'<span class="prod-sout">'+
						'<img class="thumb" src="{{:~lmCdnV3RootPath()}}/images/layout/img_soldout_500x500.png"/>'+
					'</span>' +
				'{{/if}}' +
				'<img src="{{:THUMNAIL}}"'+
					'onerror="imageError(this,' + "'noimg_prod_500x500.jpg'" + ')"'+
					'alt="{{:PROD_NM}}"' +
				'/>'+
			'{{/if}}' +
			'</div>' +
			'<div>' +
				'<div class="product-info">'+
					'{{if IS_EXPERIENCE}}'+
						'<div class="product-tag">'+
							'<i class="tag-product type1">체험매장상품</i>'+
						'</div>'+
					'{{/if}}'+
					'{{include tmpl="mobileProductListBadge"/}}' +
					'<a class="name"'+
						'href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:CATEGORY_ID}}&ProductCD={{:PROD_CD}}"'+
						'data-gtm="M020"'+
						'data-cid={{:CATEGORY_ID}}'+
						'data-pid={{:PROD_CD}}' +
						'data-title={{:PROD_NM}}' +
					'>'+
							'{{:PROD_NM}}' +
					'</a>' +
					'{{include tmpl="mobilePriceWrap"/}}'+
					'<button type="button" class="basket"'+
						'data-method="basket"' +
						'data-gtm="M019"' +
						'data-is-sold-out="{{:~isSoldOut(SOUT_YN_REAL)}}"' +
						'data-prod-cd="{{:PROD_CD}}"' +
						'data-prod-type-cd="{{:ONLINE_PROD_TYPE_CD}}"' +
						'data-min-quantity="{{if MIN_ORD_PSBT_QTY <= 0}}1{{else}}{{:MIN_ORD_PSBT_QTY}}{{/if}}"' +
						'data-max-quantity="{{:MAX_ORD_PSBT_QTY}}"'+
						'data-category-id="{{:CATEGORY_ID}}"'+
						'data-prod-title="{{:PROD_NM}}"' +
						'data-is-manufacturing-product="{{:~getManuFacturingProduct(ONLINE_PROD_TYPE_CD)}}"'+
						'data-period-delivery-yn="Y"'+
						'title="정기배송 담기">'+
						'<i class="icon-common-cartEmp"></i> 정기배송 담기' +
					'</button>'+
				'</div>'+
			'</div>'+
		'</div>' +
	'</li>' +
	'{{/for}}' +
	'<li class="ingpageloading">'+
		'<h2 class="title-basic nomore">마지막 상품입니다.</h2>'+
	'</li>'
);
$.templates('mobilePeriodShippingPackageList',
	'{{for contents}}' +
		'<p class="shipping-packbig">' +
			'<a href="{{:~getLMAppUrlM()}}/mobile/delivery/package.do?peri_deli_pkg_no={{:PERI_DELI_PKG_NO}}&SITELOC={{if #index === 2 || #index < 8}}OI00{{: #index + 2}}{{else}}OI0{{: #index + 2}}{{/if}}&returnURL=/mobile/delivery/main.do" data-gtm="M069">' +
				'<img src="{{:~getImageContentBase()}}/{{:MOBL_MAI_BNR_IMG}}" alt="{{:PERI_DELI_PKG_NM}}" onerror="imageError(this,' + "'noimg_prod_204x204.jpg'" + ')">' +
			'</a>' +
		'</p>' +
	'{{/for}}' +
	'<span class="ingpageloading">' +
		'<h2 class="title-basic">마지막 리스트입니다</h2>' +
	'</span>'
);

$.templates('mobileFilterLayer',
	'<article class="layerpopup-type1 {{:~className(layerClass)}}">' +
		'<form>'+
			'<header class="header">'+
				'{{if title}}' +
				'<h3 class="title">{{:title}}</h3>' +
				'{{/if}}'+
				'<button type="submit" class="icon-close js-close" data-type="close">닫기</button>' +
			'</header>'+
			'{{if allText}}' +
				'<button type="submit" class="btn-form-color4-fullw search-plan" data-type="all">{{:allText}}</button>' +
			'{{/if}}' +
			'<div class="wrap-cateselect {{:~className(wrapClass)}}">'+
				'<div class="sorting-check">'+
				'{{for list}}' +
				'<label>'+
					'<input type="checkbox" name="item" value="{{:MINOR_CD}}">'+
					'<em>{{:CD_NM}}</em>' +
				'</label>'+
				'{{/for}}' +
				'</div>' +
			'</div>' +
			'<div class="acenter">'+
				'<button type="submit" data-type="submit" class="{{:~className(btnSubmitStyle)}}">'+
				'{{:submitText}}' +
				'</button>' +
			'</div>'+
		'</form>'+
	'</article>'
);

$.templates('mobileSearchFilterCheckbox',
	'<li>'+
		'<label>'+
			'<input type="checkbox"' +
				'class="chk-form"' +
				'data-gtm="{{:gtmNumber}}"' +
				'data-title="{{:title}}"' +
				'data-value="{{:name}}"' +
				'name="{{:type}}"' +
				'value="{{:value}}"' +
				'<span>{{:name}}</span>' +
		'</label>'+
	'</li>'
);

$.templates('mobileSearchFilter',
	'<aside id="globalSearchLayer">' +
		'<header class="header">'+
			'<h1 class="title">상세검색</h1>' +
			'<span class="search-desc">'+
				'전체 <em class="number">{{:totalCount}}</em>개'+
			'</span>' +
			'<button type="button"' +
					'data-id="btnreset"' +
					'class="btn-common-color4"'+
					'data-gtm="{{:gtmNumberForButtonReset}}">초기화</button>' +
			'<button type="button"' +
					'data-id="btnclose"'+
					'class="icon-close"'+
					'data-gtm="{{:gtmNumberForButtonClose}}">닫기</button>' +
		'</header>' +
		'<section class="wrap-inner-scroll">'+
			'<dl class="shipping-faq-list">'+
				'{{if deliveryList.length > 0}}' +
				'<dt data-id="btnaccordion">배송</dt>' +
				'<dd>'+
					'<ul class="ps-list">'+
					'{{for deliveryList}}'+
						'{{include tmpl="mobileSearchFilterCheckbox"/}}' +
					'{{/for}}' +
					'</ul>'+
				'</dd>'+
				'{{/if}}' +
				'{{if benefitList.length > 0}}' +
				'<dt data-id="btnaccordion">혜택</dt>' +
				'<dd>'+
					'<ul class="ps-list">'+
					'{{for benefitList}}' +
						'{{include tmpl="mobileSearchFilterCheckbox"/}}' +
					'{{/for}}' +
					'</ul>'+
				'</dd>'+
				'{{/if}}' +
			'</dl>'+
		'</section>' +
		'<div class="searchfilter-select-apply">'+
			'<button type="button"' +
					'class="btn-common-color6 fullw"' +
					'data-id="btnsearch"'+
					'data-gtm="{{:gtmNumberForApply}}">'+
						'선택적용' +
					'</button>' +
		'</div>' +
	'</aside>' +
	'<div class="global-mask" style="display:none"></div>'
);

$.templates('mobileProductList',
	'{{if products !== null && products.length > 0}}' +
		'{{for products}}'+
			'<li data-panel="product">' +
				'<div class="product-wrap">' +
					'{{include tmpl="mobileProductItem" /}}' +
				'</div>'+
			'</li>'+
		'{{/for}}'+
		'{{if count <= 30}}' +
			'<li class="list-empty">' +
				'{{include tmpl="emptyProductList" /}}' +
			'</li>' +
		'{{/if}}' +
	'{{else}}' +
		'<li class="list-empty">' +
			'{{include tmpl="emptyProductList" /}}' +
		'</li>' +
	'{{/if}}'
);

$.templates('emptyProductList',
	'{{if count > 0}}' +
		'마지막 상품입니다.' +
	'{{else}}' +
		'{{if benefitChkList !== "" || deliveryView !== "" }}' +
			'선택하신 조건에 맞는 상품이 없습니다.' +
		'{{else}}' +
			'상품이 없습니다.' +
		'{{/if}}' +
	'{{/if}}'
);

$.templates('mobileProductItem',
	'{{include tmpl="mobileProductImage" /}}' +
	'<div class="product-info">' +
		'{{if isExperience}}'+
			'<div class="product-tag">'+
				'<i class="tag-product type1">체험매장상품</i>'+
			'</div>'+
		'{{/if}}'+
		'{{include tmpl="mobileIconInfo" /}}' +
		'<a href="#"' +
			'class="name"' +
			'data-gtm="M033"' +
			'data-category-id="{{:category.id}}"' +
			'data-click-url="{{:CLICK_URL}}"' +
			'data-prod-cd="{{:code}}">' +
			'{{:name}}' +
		'</a>' +
		'<div class="price-wrap">' +
			'<div class="price">' +
				'{{if currSellPrc > promoMaxVal}}'+
					'<strong class="point1">{{:~saleRatio(promoMaxVal, currSellPrc)}}%</strong>'+
					'<div class="number">' +
						'<em class="sale">{{:~numberFormat(promoMaxVal)}}</em>' +
						'<i>원{{if ~isDeal(onlineProdTypeCd)}}~{{/if}}</i>' +
					'</div>' +
					'<div class="number">' +
						'<em>{{:~numberFormat(currSellPrc)}}</em>' +
						'<i>원</i>' +
					'</div>' +
				'{{else}}'+
					'<div class="number">' +
						'<em>{{:~numberFormat(currSellPrc)}}</em>' +
						'<i>원{{if ~isDeal(onlineProdTypeCd)}}~{{/if}}</i></i>' +
					'</div>' +
				'{{/if}}'+
			'</div>' +
		'</div>' +
		'<div class="review-wrap">' +
			'{{if recommPoint && (recommPoint > 6 && recommCount > 0)}}' +
				'<div class="review">' +
					'{{include tmpl="mobileStarPoint" /}}' +
					'<span class="review-cases">'+
						'(<em>{{:~numberFormat(recommCount)}}</em>)' +
					'</span>' +
				'</div>' +
			'{{else recommRecomPnt && (recommRecomPnt > 6 && recommCnt > 0)}}' +
				'<div class="review">' +
					'{{include tmpl="mobileStarPoint" /}}' +
					'<span class="review-cases">'+
						'(<em>{{:~numberFormat(recommCnt)}}</em>)' +
					'</span>' +
				'</div>' +
			'{{/if}}' +
			'<div class="cartInput">' +
				'{{include tmpl="mobileBasketButton" /}}' +
			'</div>' +
		'</div>' +
	'</div>'
);

$.templates('mobileProductImage',
	'<div class="product-img">' +
		'{{if ~isAdult(adlYn)}}' +
			'<img src="{{:~lmCdnV3RootPath()}}/images/layout/m-goods-adult.png"' +
				'onerror="showNoImage(this,500,500)"'+
				'alt="성인인증이 필요한 상품입니다."/>'+
		'{{else ~isSoldOut(soutYn,onlineProdTypeCd)}}' +
			'<span class="prod-sout">' +
				'<img src="{{:~lmCdnV3RootPath()}}/images/layout/img_soldout_500x500.png"' +
					'onerror="showNoImage(this,500,500)"'+
					'alt="상품 준비중"' +
					'class="thumb"/>' +
			'</span>' +
			'{{if "Y" == wideYn}}' +
				'<img src="{{:~getItemWideImagePath(mdSrcmkCd)}}"' +
				'onerror="this.style.display=\'none\'"'+
				'class="wide" alt="{{:name}}}"/>' +
			'{{/if}}' +
			'<img src="{{:~getItemImagePath(mdSrcmkCd,500,1)}}"' +
				'onerror="showNoImage(this,500,500)"'+
				'alt="{{:name}}}"/>' +
		'{{else}}' +
			'{{if "Y" == wideYn}}' +
				'<img src="{{:~getItemWideImagePath(mdSrcmkCd)}}"' +
				'onerror="this.style.display=\'none\'"'+
				'class="wide" alt="{{:name}}}"/>' +
			'{{/if}}' +
			'<img src="{{:~getItemImagePath(mdSrcmkCd,500,1)}}"' +
				'onerror="showNoImage(this,500,500)"'+
				'alt="{{:name}}}"/>' +
		'{{/if}}' +
	'</div>'
);
$.templates('mobileIconInfo',
	'<div class="badge">' +
		'{{if freeDelivery === "Y" && (onlineProdTypeCd && onlineProdTypeCd !== "08")}}' +
			'<div class="type01">' +
				'무료배송'+
			'</div>' +
		'{{/if}}' +
		'{{if ~isDeal(onlineProdTypeCd) === false}}' +
			'{{if ~isShowDelivery(onlineProdTypeCd,deliType)}}' +
				'<div class="type01">'+
					'{{:~deliveryIcons(onlineProdTypeCd,deliType,deliType2).name}}' +
				'</div>' +
			'{{/if}}' +
		'{{/if}}'+
		'{{if periDeliYn === "Y"}}' +
			'<div class="type01">'+
				'정기배송' +
				'{{if periCoupon != null}}' +
					'[{{: periCoupon}} 할인]' +
				'{{/if}}'+	
			'</div>' +
		'{{/if}}'+
		'{{if ~promotions(promoProdIcon).length > 0}}' +
			'{{for ~promotions(promoProdIcon)}}' +
				'<div class="type02">' +
					'{{include tmpl="mobilePromotionBadge" /}}'+
				'</div>'+
			'{{/for}}' +
		'{{/if}}' +
		'{{if isExpressDeliveryArea !== null && expressDeliYn !== null}}' +
			'{{if isExpressDeliveryArea === "Y" && expressDeliYn === "Y"}}' +
				'<div name="expressType" class="type03">바로배송</div>' +
			'{{/if}}'+
		'{{/if}}'+
	'</div>' +
	'{{if ~isDeal(onlineProdTypeCd) === false}}' +
		'{{if ~isGiftMall(onlineProdTypeCd,deliType)}}' +
			'<div class="promotion-holi">' +
				'<i class="{{:~deliveryIcons(onlineProdTypeCd,deliType).css}}">'+
					'{{:~deliveryIcons(onlineProdTypeCd,deliType).name}}'+
				'</i>'+
			'</div>'+
		'{{/if}}'+
	'{{/if}}'
);
$.templates('mobileStarPoint',
	'<span class="starpoint-layer"' +
		'id="btnReviews">' +
		'{{if recommPoint}}' +
		'<em class="starpoint-cover" style="width:{{:recommPoint * 10}}%"></em>' +
		'{{else recommRecomPnt}}' +
		'<em class="starpoint-cover" style="width:{{:recommRecomPnt * 10}}%"></em>' +
		'{{/if}}' +
	'</span>'
);
$.templates('mobileBasketButton',
	'<button type="button"' +
		'class="basket {{if ~isSoldOut(soutYn,onlineProdTypeCd)}}disabled{{/if}}"' +
		'data-method="basket"' +
		'data-is-sold-out="{{:~isSoldOut(soutYn,onlineProdTypeCd)}}"' +
		'data-option-yn="{{:optionYn}}"' +
		'data-prod-cd="{{:code}}"' +
		'data-prod-type-cd="{{:onlineProdTypeCd}}"' +
		'data-min-quantity="{{if minOrdPsbtQty <= 0}}1{{else}}{{:minOrdPsbtQty}}{{/if}}"' +
		'data-max-quantity="{{:maxOrdPsbtQty}}"' +
		'data-category-id="{{:category.id}}"' +
		'data-prod-title="{{:name}}"' +
		'data-is-manufacturing-product="{{if onlineProdTypeCd ===' + '"02"|| onlineProdTypeCd ===' + '"03"' + '}}true{{else}}false{{/if}}"' +
		'title="장바구니 담기"' +
		'{{if ~isSoldOut(soutYn,onlineProdTypeCd)}}disabled{{/if}}>' +
		'<i class="icon-common-cartEmp"></i>' +
	'</button>'
);
$.templates('mobilePromotionBadge',
	'{{if orderNo === "04"}}'+
		'{{:iconDesc}}' +
	'{{else iconType === "won"}}' +
		'<em class="won">{{:~numberFormat(iconDesc)}}</em> 할인' +
	'{{else iconType === "discount"}}' +
		'<em class="number">{{:iconDesc}}</em> 할인' +
	'{{else iconType === "bundle"}}' +
		'<em class="plus">{{:iconDesc}}</em>' +
	'{{else iconType === "type6-1"}}' +
		'<em class="number">{{:iconDesc}}</em> 다둥이' +
	'{{else iconType === "type6-2"}}' +
		'<em class="won">{{:~numberFormat(iconDesc)}}</em> 다둥이' +
	'{{else iconType === "type4-1"}}' +
		'<em class="number">{{:iconDesc}}</em> L.POINT' +
	'{{else iconType === "type4-2"}}' +
		'<em class="won">{{:~numberFormat(iconDesc)}}</em> L.POINT' +
	'{{else iconType !== "type12"}}' +
		'{{:iconNm}}' +
	'{{/if}}'
);
$.templates('mobileRecentlyProduct',
		'{{if recentlyPurchasedProducts != null}}' +
			'<h2 class="title">최근 <b>구매 상품</b></h2>' +
			'<nav class="fav-product swiper-inner-scroll">' +
				'{{for recentlyPurchasedProducts}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd }}" data-gtm= "M022" class= "item {{if ~isDealProductItem(ONLINE_PROD_TYPE_CD_DEAL) and stockQty lt 1 }} soldout {{/if}}">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getItemImagePath(mdSrcmkCd, 240, 1)}}" onerror="javascript:showNoImage(this,240,240)">' +
						'</div>' +
						'<p class="desc">{{:prodNm }}</p>' +
					'</a>' +
				'{{/for}}' +
				'<a href="javascript:;" class="more" data-gtm="M021" onclick="rnbOpen(this);return false;">더보기</a>' +
			'</nav>' +
		'{{/if}}'
);
$.templates('mobilePersonalMcoupons',
		'{{if personalMcoupons != null}}' +
			'<h2 class="title"><b>{{:memberName }}</b>님을 위한 <strong class="point1">M쿠폰</strong> 추천상품</h2>' +
			'<nav class="fav-product swiper-inner-scroll">' +
				'{{for personalMcoupons}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/evt/mobileEventMCouponDetail.do?couponId={{:COUPON_ID}}" class= "mcouponDetailItem vertical">' +
						'<div class="wrap-mcoupon-registered">' +
							'<div class="mcoupon-registered">' +
								'<div class="cateimage">' +
									'<img src="{{:COUPON_IMG_URL}}" onerror="javascript:showNoImage(this,500,500)">' +
								'</div>' +
								'<div class="desc">' +
									'<p class="tit">' +
										'<span class="number">' +
											'{{:COUPON_DC_AMT }} ' +
											'{{if DC_UNIT_CD === "01"}} <span class="number-percent">원</span> ' +
											'{{else DC_UNIT_CD === "02"}} <span class="number-percent">%</span> ' +
											'{{else DC_UNIT_CD === "05"}} <span class="number-percent">배</span> ' +
											'{{else DC_UNIT_CD === "06"}} <span class="number-percent">P</span> ' +
											'{{/if}}' +
										'</span>' +
									'</p>' +
									'<ul class="desc-list">' +
										'<li>{{:COUPON_NM }}</li>' +
									'</ul>' +
								'</div>' +
							'</div>' +
						'</div>' +
					'</a>' +
				'{{/for}}' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/evt/eventMain.do?tabId=2" class="more">더보기</a>' +
			'</nav>' +
		'{{/if}}'
	);
$.templates('mobilePersonalMcouponsNew',
		'{{if personalMcoupons != null}}' +
			'<h2 class="title"><b>{{:memberName }}</b>님을 위한 <strong class="point1">M쿠폰</strong> 추천상품</h2>' +
			'<nav class="fav-product swiper-inner-scroll">' +
				'{{for personalMcoupons}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/evt/mobileEventMCouponDetail.do?couponId={{:COUPON_ID}}" class= "mcoupon-item">' +
						'<div class="thumbnail">' +
							'<img src="{{:COUPON_IMG_URL}}" onerror="javascript:showNoImage(this,500,500)">' +
						'</div>' +
						'<div class="conts">' +
							'<p class="desc">{{:COUPON_NM }}</p>' +
							'<p class="benefit">' +
								'<span class="number">{{:COUPON_DC_AMT }}' +
									'<sub>' +
										'{{if DC_UNIT_CD === "01"}} <span class="number-percent">원</span> ' +
										'{{else DC_UNIT_CD === "02"}} <span class="number-percent">%</span> ' +
										'{{else DC_UNIT_CD === "05"}} <span class="number-percent">배</span> ' +
										'{{else DC_UNIT_CD === "06"}} <span class="number-percent">P</span> ' +
										'{{/if}}' +
									'</sub>' +
								'</span>' +
								'{{if SCP_COUPON_TYPE_CD == "A" || SCP_COUPON_TYPE_CD == "B" || SCP_COUPON_TYPE_CD == "J" || SCP_COUPON_TYPE_CD == "K"}} 할인 ' +
								'{{else SCP_COUPON_TYPE_CD == "C" || SCP_COUPON_TYPE_CD == "D"}} 적립' +
								'{{/if}}' +
							'</p>' +
						'</div>' +
						'<div class="period">{{:USE_START_DY}} ~ {{:USE_END_DY}}</div>' +
					'</a>' +
				'{{/for}}' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/evt/eventMain.do?tabId=2" class="more">더보기</a>' +
			'</nav>' +
		'{{/if}}'
);
$.templates('mobileMcouponDetail',
	'{{for prodInfo}}' +
		'<div class="list-applycoupon">' +
			'{{if MALL_DIVN_CD === "00002"}}' +
				'<div onclick="goProductDetailToy(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\');return false;">' +
			'{{else}}' +
				'<div onclick="goProductDetailMobile(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\',\'N\');return false;">' +
			'{{/if}}' +
				'<img src="{{:~root.imgPath}}/{{:MD_SRCMK_CD.substring(0,5)}}/{{:MD_SRCMK_CD}}_1_220.jpg" alt="{{:PROD_NM}}" >' +
			'</div>' +
			'<div class="desc">' +
				'{{if MALL_DIVN_CD === "00002"}}' +
					'<span class="title" onclick="goProductDetailToy(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\');return false;">{{:PROD_NM}}</span>' +
				'{{else}}' +
					'<span class="title" onclick="goProductDetailMobile(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\',\'N\');return false;">{{:PROD_NM}}</span>' +
				'{{/if}}' +
				'<div class="price-wrap">' +
					'<div class="price">' +
						'{{if CURR_SELL_PRC > PROM_MAX_VAL}}' +
							'<div class="number">' +
								'<span>판매가 </span>' +
								'<em>{{:CURR_SELL_PRC}}</em><i>원</i>' +
							'</div>' +
							'<div class="number">' +
								'<span class="sale">최저가 </span>' +
								'<em class="sale">{{:PROM_MAX_VAL}}</em><i class="sale">원</i>' +
								'<button type="button" title="레이어팝업 열림" class="icon-common-toggle" data-benefit="{{:PROM_MAX_VAL_LIST}}" data-benefit-type="small"/>' +
							'</div>' +
						'{{else}}' +
							'<div class="number">' +
								'<span class="sale">판매가 </span>' +
								'<em class="sale">{{:CURR_SELL_PRC}}</em><i class="sale">원</i>' +
							'</div>' +
						'{{/if}}' +
					'</div>' +
				'</div>' +
				'<span class="benefit">M쿠폰 {{:~root.discountAmount}}</span>' +
			'</div>' +
		'</div>' +
	'{{/for}}'
);
$.templates('mobileHottime',
		'{{if CornerMapWithContent["004_003"]!= null}}' +
			'{{for CornerMapWithContent["004_003"].set["003"] }}' + //핫타임
				'{{:HTML}}' +
			'{{/for}}' +
		'{{else CornerMapWithContent["004_002"]!= null}}'+
			'{{for CornerMapWithContent["004_002"].set["002"] }}' + //핫타임예고
			'{{:HTML}}' +
			'{{/for}}' +
		'{{/if}}'
);
$.templates('mobileCounselList',
	'{{if counsels != null}}' +
		'{{for counsels}}' +
			'<dl>' +
				'<dt name="statusPanel">' +
					'<p>' +
						'{{if REPLY_YN == "Y"}}' +
							'<i class="icon-tag-faq2">답변완료</i>' +
						'{{else}}' +
							'<i class="icon-tag-faq1">답변준비</i>' +
						'{{/if}}' +
						'<em class="title-faq">{{:TITLE}}</em>' +
						'{{if ATCH_FILE_ID != null}}' +
							'<i class="icon-sprite-fileattach">첨부파일</i>' +
						'{{/if}}' +
					'</p>' +
					'<p class="namecard">' +
						'<em>{{:CUST_QST_DIVN_NM}}</em> <time class="number">{{:~formatTimeStamp(REG_DATE, "yyyy.MM.dd")}}</time>' +
					'</p>' +
				'</dt>' +
				'<dd>' +
					'<p class="faq-q">{{:CONTENT}}</p>' +
					'{{if REPLY_YN == "Y"}}' +
						'<p class="faq-a">{{:~replaceCounselContentCharacter(REPLY_CONTENT)}}</p>' +
					'{{/if}}' +
				'</dd>' +
			'</dl>' +
		'{{/for}}' +
	'{{else}}' +
		'<p class="list-empty">상담/문의 내역이 없습니다.<br>롯데마트몰/토이저러스는 언제나 고객님의<br>문의에 성심껏 안내해 드리겠습니다.</p>' +
	'{{/if}}'
);
$.templates('mobileMoreButton',
	'<div id="moreButtonWrapper" class="wrap-button">' +
		'<button id="btnMore" type="button" class="btn-form-color4" data-current-page="{{:currentPage}}">' +
			'<em class="number-point">{{:rowPerPage}}</em> 개 더보기 <i class="icon-more"></i>' +
		'</button>' +
	'</div>'
);
$.templates('basketItemSearcher',
	'<div class="wrap-columns">' +
		'<div class="right">' +
			'<button name="btnAply" type="button" class="btn-form-type1">선택 상품 적용</button>' +
		'</div>' +
	'</div>' +

	'<ul name="itemContainer" data-template-id="searcherOrderItem" class="complain-list">' +
	'</ul>' +

	'{{include tmpl="moreButton" /}}'
);
$.templates('orderItemSearcher',
	'<div class="wrap-columns">' +
		'<div class="left">' +
			'<div class="wrap-radiotab">' +
				'<input type="radio" id="oneMonth" name="period" checked="checked" value="1">' +
				'<label for="oneMonth">1개월</label>' +
				'<input type="radio" id="twoMonth" name="period" value="2">' +
				'<label for="twoMonth">2개월</label>' +
				'<input type="radio" id="threeMonth" name="period" value="3">' +
				'<label for="threeMonth">3개월</label>' +
				'<input type="radio" id="sixMonth" name="period" value="6">' +
				'<label for="sixMonth">6개월</label>' +
			'</div>' +
		'</div>' +

		'<div class="right">' +
			'<button name="btnAply" type="button" class="btn-form-type1">선택 상품 적용</button>' +
		'</div>' +
	'</div>' +

	'<div name="container" data-template-id="searcherOrder">' +
	'</div>' +
	'{{include tmpl="moreButton" /}}'
);
$.templates('searcherOrder',
	'{{if orders != null}}' +
		'{{for orders}}' +
			'<div name="orderWrapper">' +
				'<div class="complain-header">' +
					'<label class="order-number"><span name="orderNumberWrapper" class="radio-data"><input id="showOrderItem{{:ORDER_ID}}" name="orderNumber" type="radio" value="{{:ORDER_ID}}" title="{{:ORDER_ID}} 선택"/></span> {{:ORDER_ID}}</label>' +
					'<p>주문일: <em class="number-point">{{:~substr(ORD_DY, 0, 4)}}.{{:~substr(ORD_DY, 4, 2)}}.{{:~substr(ORD_DY, 6, 2)}}</em></p>' +
					'<p>주문금액: <em class="number-point">{{:~numberFormat(TOT_SELL_AMT)}}</em></p>' +
					'<div class="wrap-button">' +
						'<button name="btnDelete" type="button" class="btn-form-color2" data-type="all">삭제</button>' +
					'</div>' +
				'</div>' +
				'<ul name="orderItemContainer" class="complain-list" data-template-id="searcherOrderItem">' +
				'</ul>' +
			'</div>' +
		'{{/for}}' +
	'{{/if}}'
);
$.templates('searcherOrderItem',
	'{{if items != null}}' +
		'{{for items}}' +
			'<li name="itemWrapper">' +
				'<input type="hidden" name="productCode" value="{{:PROD_CD}}" />' +
				'<span name="productCodeWrapper" class="check-data check-data-type1"><input type="checkbox" name="productCode" value="{{:PROD_CD}}" title="{{:PROD_NM}} 선택" /></span>' +
				'<div class="conts">' +
					'<div class="thumbnail mall-type{{:~substr(MALL_DIVN_CD, 4, 1)}}">' +
						'{{if ~isAdult(ADL_YN)}}' +
							'<img src="{{:~getAdultImagePath() }}" onerror="javascript:this.src=\'{{:~getNoImagePath()}}\';" alt="{{:PROD_NM}}" />' +
						'{{else}}' +
							'<img src="{{:~getItemImagePath(MD_SRCMK_CD, 120, 1)}}" onerror="javascript:this.src=\'{{:~getNoImagePath()}}\';" alt="{{:PROD_NM}}" />' +
						'{{/if}}' +
					'</div>' +
					'<p class="title">{{:PROD_NM}}</p>' +
					'{{if OPTION_YN == "Y"}}' +
						'<p class="option">옵션: {{:OPTION_NM}}</p>' +
					'{{/if}}' +
				'</div>' +
				'<p class="qty">수량: {{:QTY}}개</p>' +
				'<p class="price">가격: <strong>{{:~numberFormat(TOTAL_AMT)}}</strong>원</p>' +

				'<div class="wrap-button">' +
					'<button name="btnDelete" type="button" class="btn-form-color2">삭제</button>' +
				'</div>' +
			'</li>' +
		'{{/for}}' +
	'{{/if}}'
);
$.templates('moreButton',
	'<button name="btnPagination" type="button" class="btn-form-fullw" data-current-page="{{:currentPage}}">더보기 <i class="icon-more"></i></button>'
);
$.templates('counselAttachFileWrapper',
	'<div name="attachFileWrapper" class="wrap-file-attach">' +
		'<div class="area-input">' +
			'<input type="text" name="filePath" readonly="readonly" placeholder="첨부할 이미지파일을 선택해주세요.">' +
			'<button type="button" name="btnRemoveFile" class="delete" style="display:none;">삭제</button>' +
		'</div>' +
		'<span class="btn-form-color3">' +
			'<input type="file" name="file" />첨부' +
		'</span>' +
		'<button type="button" name="btnDelete" class="btn-form-color8">삭제</button>' +
	'</div>'
);
$.templates('mobileCounselAttachFileWrapper',
	'<div class="area-input" name="previewImageWrap">' +
		'<input type="file" name="file">' +
		'<button type="button" class="icon-prod-delete" name="btnDelete">삭제</button>' +
	'</div>'
);
$.templates('mobileCounselPreviewImageWrap',
	'<input type="hidden" name="fileSize" value="{{:fileSize }}" />' +
	'<input type="hidden" name="encodeFileString" value="{{:encodeFileString }}" />' +
	'<img src="{{:encodeFileString}}" class="image" name="filePreview" />'
);
$.templates('mobileScanProductReviews',
		'{{for reviews}}' +
			'<li class="user-reply {{if file.atchFileId != null}}has-image{{/if}} {{if !useYn}}blind-reply{{/if}}">' +
				'<header class="reply-header">' +
					'<div class="reply-header-top">' +
						'{{if type == "EXPERIENCE"}}' +
							'<i class="icon-tag-color5">체험후기</i>' +
						'{{else}}'+
							'<i class="icon-tag-color4">구매후기</i>' +
						'{{/if}}' +
						'<span class="reply-id">{{:userName}}</span>' +
						'<span class="reply-date">{{:regDtByString}}</span>' +
					'</div>' +
					'<div class="reply-header-bottom">' +
						'<span class="reply-score"><em class="reply-score-inner" style="width:{{:grade*10}}%;">평점 {{:grade}}점</em></span>' +
						'{{if strNm != null}}' +
							'<span class="reply-branch">{{:strNm}}</span>' +
						'{{/if}}' +
					'</div>' +
				'</header>' +
				'<p class="reply-content">{{:contents}}</p>' +
				'{{if file.atchFileId != null}}' +
					'<div class="wrap-reply-img">' +
						'<a href="/mobile/popup/scan/products/{{:prodCd}}/reviews/{{:id}}/image.do">' +
							'<img src="{{:filePath}}" alt="상품평 이미지" onerror="javascript:showNoImage(this,500,500)">' +
						'</a>' +
					'</div>' +
				'{{/if}}' +
				'<div class="wrap-reply-btn">' +
					'<button type="button" name="recommButton" data-id="{{:id}}" data-type="{{:type}}" class="btn-prod-like {{if isRecommended}}active{{/if}}"><i class="icon-prod-thumb"></i>좋아요<em class="point2">{{:~numberFormat(recommCnt)}}</em></button>' +
					'<button type="button" class="btn-prod-toggle">열기/닫기</button>' +
				'</div>' +
				'{{if isShowButton}}' +
					'<div class="wrap-button">' +
						'<button type="button" name="modifyButton" data-id="{{:id}}" class="btn-prod-small-color11">수정</button>' +
						'<button type="button" name="delButton" data-id="{{:id}}" class="btn-prod-small-color8">삭제</button>' +
					'</div>' +
				'{{/if}}' +
			'</li>' +
		'{{/for}}'
);
$.templates('emptyMobileScanProductReviews',
		'<p class="list-empty">' +
			'등록된 상품평이 없습니다.<br>고객님의 소중한 체험 후기를 등록해보세요.' +
		'</p>'
);

$.templates('mobileSpecialMainWrapper',
	'{{for mainSpecial}}' +
	'<article class="wrap-special-content"' +
				'data-category-id="{{:category}}">' +
		'{{include tmpl="mobileSpecialMainTitleWrapper" /}}' +
		'{{if relatedProductList.length > 0}}' +
			'<ul class="swiper-inner-scroll">' +
				'{{include tmpl="mobileSpecialMainRelatedProductList" ~mainLink=link ~totalLength=relatedProductList.length /}}' +
			'</ul>' +
		'{{/if}}'+
	'</article>' +
	'{{/for}}'
);

$.templates('mobileSpecialMainTitleWrapper',
	'<h3 class="main-special-title">' +
		'<a href="{{:link}}" class="wrap-special-title">'+
			'{{:title}}' +
			'<em class="main-special-desc">'+
				'{{:explain}}' +
			'</em>' +
			'<img src="{{:imageUrl}}" alt="{{:title}}">'+
		'</a>' +
	'</h3>'
);

$.templates('mobileSpecialMainRelatedProductList',
	'{{for relatedProductList}}' +
		'<li class="main-special-list">' +
			'<a href="{{:~getLMAppUrlM()}}{{:link}}" class="main-special-prod">'+
				'<img src="{{:~getSimageStaticProductImagePath(mdSrcmkCd, 200)}}"'+
						'class="main-special-img"'+
						'alt="{{:title}}"/>' +
				'<span class="special-prod-name">{{:title}}</span>' +
			'</a>' +
		'</li>' +
		'{{if #index + 1 === ~totalLength}}' +
		'<li class="main-special-more">'+
			'<a href="{{:~mainLink}}" class="main-special-more-btn">' +
				'<i class="icon-special-more"></i>' +
				'<span class="main-special-more-txt">더보기</span>'+
			'</a>'+
		'</li>' +
		'{{/if}}' +
	'{{/for}}'
);
;(function (root, factory) {
    'use strict';

    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory()
    } else {
        root.schemeLoader = factory();
    }
}(window, function () {
    'use strict';

    var schemes = {
        // Basic Scheme
        basketCountUpdate: 'lottemartmall://basketcountupdate',
        lnbOpen: 'lottemart-shopping-iphone://lnbopen',
        lnbClosed: 'lottemart-shopping-iphone://lnbclosed',
        rnbOpen: 'lottemart-shopping-iphone://rnbopen',
        rnbClosed: 'lottemart-shopping-iphone://rnbclosed',
        showBar: 'lottemartapp://showBar',
        hideBar: 'lottemartapp://hideBar',
        setting: 'lottemartmall://setting',
        closeIntro : 'lottemartmall://closeIntro',
        floatingBannerOpen: 'lottemartapp://floatingBanner?id=lpoint&status=open',
        floatingBannerClose: 'lottemartapp://floatingBanner?id=lpoint&status=close',
        pushOn: 'push://on',
        pushOff: 'push://off',
        pushList: 'lottemartapp://pushList?url=',
        openToysrus : 'lottemartmall://openToysrus',
        openPopup : 'lottemartapp://openPopup?url=',
        openUrl : 'lottemartmall://openUrl?url=',
        sendSMS: 'lottemartapp://sendSMS?url=',
        showPickUp: 'lottemartapp://showPickUp',
        hidePickUp: 'lottemartapp://hidePickUp',
        isMainTrue: 'lottemartapp://isMainTrue',
        isMainFalse: 'lottemartapp://isMainFalse',
        lodingStart: 'lottemartapp://lodingStart',
        lodingEnd: 'lottemartapp://lodingEnd',
        menuBarHomeButton: 'lottemartapp://menuBarHomeButton',
        menuBarBasketButton: 'lottemartapp://menuBarBasketButton',
        menuBarMymallButton: 'lottemartapp://menuBarMymallButton',
        showZoomViewUrl: 'lottemartapp://showZoomViewUrl?title=&openUrl=',
        showZoomViewNextUrl: 'lottemartapp://showZoomViewNextUrl?title=',
        searchVoice: 'lottemart-shopping-iphone://voice',
        searchQRCode: 'lottemart-shopping-iphone://qrcode',
        tuneAddADID: 'lottemartapp://tuneAddADID',
        openLottemartApp : 'lottemartmobileapp://goURL=',
        transeperSsoToken : 'lottemartmobileapp://transeperSsoToken?ssoToken=',
        pushStatus : 'lottemartmall://pushStatus',
        pushApplicationSetting : 'lottemartmall://pushApplicationSetting'
    },
    versions = {
        // Basic
        openToysrus: {ios: '10.13', android: '10.79'},
        openUrl: {ios: '10.14', android: '10.80'},
        openPopup: {ios: '10.14', android: '10.80'},
        closeIntro : {ios:'9.4', android:'9.4'},
        showZoomViewUrl : {ios:'10.20', android:'10.90'},
        showZoomViewNextUrl : {ios:'10.21', android:'10.91'},
        transeperSsoToken : {ios:'10.40', android:'11.11'},
        // Default
        defaultVersion: {ios: '9.9.3', android: '10.73'}
    },
    excludeParamKeys = ['key', 'id', 'status', 'callback'],
    hasOwnProperty = Object.prototype.hasOwnProperty,
    patternUrl = /^([^:\/?#]+:)?(\/\/[^\/?#]*)?([^?#]*)(\?[^#]*)?(#(.*))?/,
    patternQueryString = /[?&]+([^=&]+)=([^&]*)/gi,
    patternStripZero = /(\.0+)+$/,
    schemeQueue = [];

    Array.prototype._forEach = function (cb) {
        var l = this.length, r = true;
        for (var i = 0; i < l; i++) r = cb(this[i], i);
        return r;
    };

    function _logColor(logTitle, logContent) {
        var urlParts = window.location.hostname.split('.');
        if ('ms' === urlParts.shift()) {
            console.log("%c" + logTitle + "%c" + logContent, "background:black; color:yellow", "background:black; color:red");
        }
    }

    function _trim(target) {
        return target.replace(/\s+/g, '');
    }

    function _isEmpty(obj) {
        if (obj === null) return true;
        if (obj.length > 0) return false;
        if (obj.length === 0) return true;
        if (typeof obj !== "object") return true;

        for (var key in obj) {
            if (hasOwnProperty.call(obj, key)) return false;
        }
        return true;
    }

    function _serialize(obj) {
        var param = [];
        for (var p in obj)
            if (obj.hasOwnProperty(p)) {
                param.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
            }
        return param.join("&");
    }

    function _isApp() {
        var ua= window.navigator.userAgent;
        return ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-IOS") > -1
            || ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-ANDROID") > -1
            || ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-DID") > -1;
    }

    function _isIOS() {
        return /iP(hone|od|ad)/.test(window.navigator.userAgent) && !window.MSStream;
    }

    function _isIOSWithWKWebview() {
        return _isIOS() && window.webkit && window.webkit.messageHandlers;
    }

    //Return values:
    //if a < b, false
    //if a > b and if a = b, true
    function _compareVersions(a, b) {
        var i, diff,
            segmentsA = a.replace(patternStripZero, '').split('.'),
            segmentsB = b.replace(patternStripZero, '').split('.'),
            l = Math.min(segmentsA.length, segmentsB.length);

        for (i = 0; i < l; i++) {
            diff = parseInt(segmentsA[i], 10) - parseInt(segmentsB[i], 10);
            if (diff) {
                return diff > 0;
            }
        }
        return (segmentsA.length - segmentsB.length) >= 0;
    }

    function _isMoreAppVersion(schemeObj) {
        var osType = _isIOS() ? 'ios' : 'android',
            currentVersion = navigator.userAgent.split('app-version')[1].replace('V.', ''),
            targetVersion = versions.hasOwnProperty(schemeObj.key)
                                ? versions[schemeObj.key][osType]
                                : versions['defaultVersion'][osType];
        return _compareVersions(_trim(currentVersion), _trim(targetVersion));
    }

    function _parseUrl(url, key) {
        var matches = url.match(patternUrl),
            params = {};

        if (matches[4]) {
            matches[4].replace(patternQueryString, function (s, k, v) {
                params[k] = v;
            });
        }
        return {
            protocol: matches[1] + matches[2],
            params: key ? params[key] : params
        };
    }

    function _extractObjectFromSchemeObj(obj, keys) {
        var target = {};

        for (var prop in obj) {
            if (keys.indexOf(prop) >= 0) continue;
            if (!hasOwnProperty.call(obj, prop)) continue;
            target[prop] = obj[prop];
        }
        return target;
    }

    document.onreadystatechange = function () {
        if (document.readyState === "complete" && schemeQueue.length > 0) {
            _dequeueSchemeQueue();
        }
    };

    function _load(scheme) {
        if (document.readyState !== 'complete') {
            schemeQueue.push(scheme);
        } else {
            window.location.href = scheme;
        }
    }

    function _dequeueSchemeQueue() {
        for (var i = 0, len = schemeQueue.length; i < len; i++) {
            if (schemeQueue[i] !== null) {
                window.location.href = schemeQueue[i];
                schemeQueue[i] = null;
            }
        }

        if (schemeQueue.every(function (v) {return v === null})) {
            schemeQueue = [];
        }
    }

    function _postMessage(schemeObj) {
        var postJSONString = JSON.stringify({
            messageName: schemeObj.key,
            messageInfo: _extractObjectFromSchemeObj(schemeObj, ['key', 'callback'])
        });

        _logColor('postMessage JSON String => ', postJSONString);

        try {
            window.webkit.messageHandlers.LOTTEMART.postMessage(postJSONString);
            return true;
        } catch(error) {
            return false;
        }
    }

    function _isScheme(key) {
        return !_isEmpty(schemes) && hasOwnProperty.call(schemes, key);
    }

    function _isInterface(key) {
        return !!(window.LOTTEMART && window.LOTTEMART[key]);
    }

    function _runCallback(schemeObj) {
        if (schemeObj.callback && typeof schemeObj.callback === 'function') {
            schemeObj.result = false;
            schemeObj.callback(schemeObj);
        }
    }

    function _runInterface(schemeObj) {
        var paramObject = _extractObjectFromSchemeObj(schemeObj, excludeParamKeys),
            returnState = false;

        if (_isEmpty(paramObject)) {
            window.LOTTEMART[schemeObj.key]();
            returnState = true;
        } else {
            window.LOTTEMART[schemeObj.key](JSON.stringify(paramObject));
            returnState = true;
        }

        return returnState;
    }

    function _runScheme(schemeObj) {
        if (!schemes[schemeObj.key]) {
            return false;
        }

        var parsedUrl = _parseUrl(schemes[schemeObj.key]),
            originalKeyArray = Object.keys(parsedUrl.params),
            keyArray = originalKeyArray.filter(function (el) {
                return excludeParamKeys.indexOf(el) < 0;
            }),
            lastIndex = keyArray.length - 1,
            notHaveParams = [];

        var hasAllParamKey = keyArray._forEach(function (k, i) {
            var hasKey = hasOwnProperty.call(schemeObj, k),
                result = true;

            if (!hasKey) {
                notHaveParams.push(k);
                result = false;
            }

            if (lastIndex === i) {
                if (!_isEmpty(notHaveParams) && console.log) {
                    _logColor('This scheme requires parameters : ', notHaveParams.join(', '));
                }
            }
            return result;
        });

        if (!hasAllParamKey) {
            return false;
        }

        keyArray._forEach(function (k) {
            if (hasOwnProperty.call(schemeObj, k)) {
                parsedUrl.params[k] = schemeObj[k];
            }
        });

        var queryString = _serialize(parsedUrl.params),
            scheme = parsedUrl.protocol + (!queryString ? '' : '?') + queryString;

        if (_isApp() && _isMoreAppVersion(schemeObj)) {
            _load(scheme);
            return true;
        } else if(schemeObj.key === 'openLottemartApp'){//externel
            _load(scheme + schemeObj['goURL']);
            setTimeout(function () {
                _runCallback(schemeObj);
            }, 3000);
            return true;
        }

        _runCallback(schemeObj);
        return false;
    }

    function loadScheme(schemeObj) {
        _logColor('schemeObj => ', JSON.stringify(schemeObj));

        if (_isIOSWithWKWebview()) {
            var returnState = _postMessage(schemeObj);

            if (!returnState) {
                returnState = _runScheme(schemeObj);
            }

            return returnState;
        } else {
            if (_isInterface(schemeObj.key)) {
                return _runInterface(schemeObj);
            } else {
                return _runScheme(schemeObj);
            }
        }
    }

    function isSupport(key) {
        if (_isIOSWithWKWebview()) {
            return true;
        } else if (_isInterface(key)) {
            return true;
        } else {
            return _isScheme(key);
        }
    }

    return {
        isSupport : isSupport,
        loadScheme : loadScheme
    };
}));
;(function (root, factory) {
    'use strict';

    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'))
    } else {
        root.tuneLoader = factory(root.jQuery);
    }
}(window, function ($) {
    'use strict';

    var interfaces = {
            registrationAttempt: window.TUNE ? window.TUNE.registrationAttempt : undefined,           //(JSON string)
            registration: window.TUNE ? window.TUNE.registration : undefined,                         //(JSON string)
            loginAttempt: window.TUNE ? window.TUNE.loginAttempt : undefined,                         //()
            login: window.TUNE ? window.TUNE.login : undefined,                                       //(JSON string)
            storeRegistrationAttempt: window.TUNE ? window.TUNE.storeRegistrationAttempt : undefined, //()
            storeRegistration: window.TUNE ? window.TUNE.storeRegistration : undefined,               //(JSON string)
            defStoreChanged: window.TUNE ? window.TUNE.defStoreChanged : undefined,                   //(JSON string)
            viewHome: window.TUNE ? window.TUNE.viewHome : undefined,                                 //()
            viewGnbMenu: window.TUNE ? window.TUNE.viewGnbMenu : undefined,                           //(JSON string)
            search: window.TUNE ? window.TUNE.search : undefined,                                     //(JSON string)
            viewPromotion: window.TUNE ? window.TUNE.viewPromotion : undefined,                       //(JSON string)
            viewCategory: window.TUNE ? window.TUNE.viewCategory : undefined,                         //(JSON string)
            viewProduct: window.TUNE ? window.TUNE.viewProduct : undefined,                           //(JSON string)
            viewEvent: window.TUNE ? window.TUNE.viewEvent : undefined,                               //(JSON string)
            viewCart: window.TUNE ? window.TUNE.viewCart : undefined,                                 //()
            purchase: window.TUNE ? window.TUNE.purchase : undefined                                  //(JSON string)
        },
        converterKey = {
            registration_attempt: 'registrationAttempt',
            registration: 'registration',
            login_attempt: 'loginAttempt',
            login: 'login',
            store_registration_attempt: 'storeRegistrationAttempt',
            store_registration: 'storeRegistration',
            def_store_changed: 'defStoreChanged',
            view_home: 'viewHome',
            view_gnb_menu: 'viewGnbMenu',
            search: 'search',
            view_promotion: 'viewPromotion',
            view_category: 'viewCategory',
            view_product: 'viewProduct',
            view_event: 'viewEvent',
            view_cart: 'viewCart',
            purchase: 'purchase'
        },
        propertyObj = {},
        noParamInterfaceArray = ['loginAttempt', 'storeRegistrationAttempt', 'viewHome', 'viewCart'],
        hasOwnProperty = Object.prototype.hasOwnProperty;

    function _setProperty(deviceId, infoPushYn, mktPushYn, deviceType, adId, model) {
        propertyObj.deviceId = deviceId;
        propertyObj.infoPushYn = infoPushYn;
        propertyObj.mktPushYn = mktPushYn;
        propertyObj.deviceType = deviceType;
        propertyObj.adId = adId;
        propertyObj.model = model;
    }

    function _isIOS() {
        return /iP(hone|od|ad)/.test(navigator.userAgent) && !window.MSStream;
    }

    function _isIOSWithWKWebview() {
        return _isIOS() && window.webkit && window.webkit.messageHandlers;
    }

    function _postMessage(interfaceName, optionObj) {
        var interfaceKey = converterKey[interfaceName],
            postJSONString = JSON.stringify({
            messageName: interfaceKey,
            messageInfo: JSON.stringify(optionObj)
        });

        try {
            webkit.messageHandlers.TUNE.postMessage(postJSONString);
            return true;
        } catch(error) {
            return false;
        }
    }

    function _runInterface(interfaceName, optionObj) {
        var interfaceKey = converterKey[interfaceName];

        if (!window.TUNE
            || $.isEmptyObject(interfaces)
            || !hasOwnProperty.call(interfaces, interfaceKey)) {
            return false;
        }

        var isNoParamInterface = $.inArray(interfaceKey, noParamInterfaceArray) > -1,
            tuneInterface = interfaces[interfaceKey],
            param = JSON.stringify(optionObj),
            runInterface = $.isEmptyObject(tuneInterface) ? window.TUNE[interfaceKey] : tuneInterface;

        if (!runInterface) {
            return false;
        }

        if (isNoParamInterface) {
            window.TUNE[interfaceKey]();
        } else {
            window.TUNE[interfaceKey](param);
        }

        return true;
    }

    function saveDeviceIdAndPushYnOnServer(deviceId, infoPushYn, mktPushYn) {
    	_setProperty(deviceId, infoPushYn, mktPushYn);

        $.api.set({
            apiName : 'saveDeviceIdAndPushYnOnServer',
            data : {
                'deviceId' : deviceId,
                'infoPushYn': infoPushYn,
                'mktPushYn': mktPushYn
            },
            successCallback : function( data ) {
                // I don't know server return data.
            }
        });
    }
    
    function saveAdIdOnServer(adId, modelId) {
    	_setProperty(adId, modelId);

        $.api.set({
            apiName : 'saveAdIdOnServer',
            data : {
                'adId': adId,
                'modelId': modelId
            },
            successCallback : function( data ) {
            }
        });
    }

    function loadScheme(interfaceName, optionObj) {
        if (_isIOSWithWKWebview()) {
            return _postMessage(interfaceName, optionObj);
        } else {
            return _runInterface(interfaceName, optionObj);
        }
    }

    return {
        getDeviceId : function(){ return propertyObj.deviceId },
        getInfoPushYn : function(){ return propertyObj.infoPushYn },
        getMktPushYn : function(){ return propertyObj.mktPushYn },
        saveDeviceIdAndPushYnOnServer : saveDeviceIdAndPushYnOnServer,
        saveAdIdOnServer : saveAdIdOnServer,
        loadScheme : loadScheme
    };
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory(require('jquery'));
	} else {
		root.imageError = factory(jQuery);
	}
}( window, function($) {
	var _imageError = function(obj, fileName) {
		if ( fileName == undefined) {
			return;
		}
		obj.src = $.utils.config( 'LMCdnV3RootUrl' ) + "/images/layout/" + fileName;
	};
	
	return _imageError;
}));
(function( factory ) {
	'use strict';

	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {
	'use strict';

	var myProductHistory = (function() {
		return {
			get : _get,
			basket : _basket,
			goProductDetail : _goProductDetail,
			remove : _remove
		};

		function _get() {
			$.api.get({
				apiName : 'myHistory',
				data : {
					'histTypeCd' : '01',
					'ignoreType' : 'N'
				},
				successCallback : function( data ) {
					_render( data );
				}
			});
		}

		function _render( data ) {
			var lists = $.render.mobileRnbMyHistory( data );

			if( data && data.historyList.length > 0 ) {
				$( "#histprodcnt" ).html( data.historyList.length );
				$( "#gnbhist1" ).html( lists );
			} else {
				$("#histprodcnt").html("0");
				if( $.utils.config('Login_yn') != 'Y' &&  $.utils.config('Member_yn') != 'true'){
					var msg = ' <div class="list-empty">'
				            + '관심 있는 상품은 <br>로그인 후 확인하실 수 있습니다.'
				            + '<div class="wrap-btn"><a href="javascript:new_logins();" class="btn-common-color1">로그인</a></div>'
				            + '</div>';
					$("#GNTabItem1").html(msg);
				}else{
					$("#GNTabItem1").html(lists);
				}

			}

			$( "#gnbhist1" ).on( 'click', '.action-trash', function() {
				myProductHistory.remove( $( this ).data( 'seq' ) );

				return false;
			}).on( 'click', '.action-cart', function() {
				var $this = $( this ),
					data = $this.data();

				if( data.optionYn === 'N' ) {
					myProductHistory.basket( data.categoryId, data.productCode );
				} else {
					myProductHistory.goProductDetail( data.categoryId, data.productCode );
				}
				return false;
			});
		}

		function _basket( categoryId, productCode ) {
			var basketItems=[];

			basketItems.push({
				prodCd: productCode,			 // 상품코드
				itemCd: '001',			  // 단품코드
				bsketQty: 1,		// 주문수량
				categoryId: categoryId,		 // 카테고리ID
				nfomlVariation: null,			   // 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: 'N',					 // 해외배송여부
				prodCouponId: null,			 // 즉시할인쿠폰ID
				oneCouponId: null,			  // ONE 쿠폰ID
				cmsCouponId: null,			  // CMS 쿠폰ID
				markCouponId: null,			 // 마케팅제휴쿠폰ID
				periDeliYn: "N"					 // 정기배송여부
			});

			global.addBasket( basketItems, function( data ) {
				alert('선택한 상품을 장바구니에 담았습니다.');
                schemeLoader.loadScheme({key: 'basketCountUpdate'});
			});
		}

		function _goProductDetail( categoryId, productCode ) {
			if( confirm( "상품 상세에서 옵션 선택 후 담으실 수 있습니다." ) ) {
				location.href= $.utils.config( 'LMAppUrlM' ) + '/mobile/cate/PMWMCAT0004_New.do?CategoryID='+categoryId +'&ProductCD='+ productCode ;
			}
		}

		function _remove( seq ) {
			$.api.set({
				apiName : 'myHistoryRemove',
				data : {
					'histSeq' : seq
				},
				traditaionnal : true,
				successCallback : function( data ) {
					if( data.success === true ) {
						myProductHistory.get();
					} else {
						alert( data.message );
					}
				}
			});
		}
	})();

	window.myProductHistory = myProductHistory;
}));
(function( factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {
	'use strict';
	
	var myMenuHistory = (function() {
		return {
			get : _get,
			remove : _remove
		};

		function _get() {
			$.api.get({
				apiName : 'myHistory',
				data : {
					'histTypeCd' : '01',
					'ignoreType' : 'Y'
				},
				successCallback : function( data ) {
					_render( data );
				}
			});
		}
		
		function _render( data ) {
			var lists = $.render.mobileRnbMyHistoryForMenu( data );
			
			if( data && data.historyList.length > 0 ) {
				$( "#histmncnt" ).html( data.historyList.length );
				$( "#gnbhist2" ).html( lists );
			} else {
				$("#histmncnt").html( "0" );
				$("#GNTabItem2").html( lists );
			}
			
			$( "#gnbhist2" ).on( 'click', '.action-trash', function() {
				myMenuHistory.remove( $( this ).data( 'seq' ) );
				
				return false;
			});
		}
		
		function _remove( seq ) {
			$.api.set({
				apiName : 'myHistoryRemove',
				data : {
					'histSeq' : seq
				},
				traditaionnal : true,
				successCallback : function( data ) {
					if( data.success === true ) {
						myMenuHistory.get();
					} else {
						alert( data.message );
					}
				}
			});
		}
	})();
	
	window.myMenuHistory = myMenuHistory;
}));
(function( factory ) {
	'use strict';

	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {
	'use strict';

	var myByProductHistory = (function() {
		return {
			get : _get,
			basket : _basket,
			goProductDetail : _goProductDetail,
			remove : _remove
		};

		function _get() {
			$.api.get({
				apiName : 'myByHistory',
				data : {
					'histTypeCd' : '01',
					'ignoreType' : 'N'
				},
				successCallback : function( data ) {
					_render( data );
				}
			});
		}

		function _render( data ) {
			var lists = $.render.mobileRnbMyByHistory( data );

			if( data && data.historyList.length > 0 ) {
				$( "#histgetprodcnt" ).html( data.historyList.length );
				$( "#gnbhist2" ).html( lists );
			} else {
				$("#histgetprodcnt").html("0");
				if( $.utils.config('Login_yn') != 'Y' &&  $.utils.config('Member_yn') != 'true'){
		            var msg = ' <div class="list-empty">'
		                + '최근 구매내역 메뉴는 <br>로그인 후 확인하실 수 있습니다.'
		                + '<div class="wrap-btn"><a href="javascript:new_logins();" class="btn-common-color1">로그인</a></div>'
		                + '</div>';

		            $("#GNTabItem2").html(msg);
				}else {
					$("#GNTabItem2").html(lists);
				}
			}

			$( "#gnbhist2" ).on( 'click', '.action-trash', function() {
				myByProductHistory.remove( $( this ).data( 'seq' ) );

				return false;
			}).on( 'click', '.action-cart', function() {
				var $this = $( this ),
					data = $this.data();

				if( data.optionYn === 'N' ) {
					myByProductHistory.basket( data.categoryId, data.productCode, data.itemCode );
				} else {
					myByProductHistory.goProductDetail( data.categoryId, data.productCode );
				}
				return false;
			});
		}

		function _basket( categoryId, productCode, itemCode ) {
			var basketItems=[];

			basketItems.push({
				prodCd: productCode,			 // 상품코드
				//itemCd: '001',			  // 단품코드
				itemCd: itemCode,			  // 단품코드
				bsketQty: 1,		// 주문수량
				categoryId: categoryId,		 // 카테고리ID
				nfomlVariation: null,			   // 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: 'N',					 // 해외배송여부
				prodCouponId: null,			 // 즉시할인쿠폰ID
				oneCouponId: null,			  // ONE 쿠폰ID
				cmsCouponId: null,			  // CMS 쿠폰ID
				markCouponId: null,			 // 마케팅제휴쿠폰ID
				periDeliYn: "N"					 // 정기배송여부
			});

			global.addBasket( basketItems, function( data ) {
				alert('선택한 상품을 장바구니에 담았습니다.');
                schemeLoader.loadScheme({key: 'basketCountUpdate'});
			});
		}

		function _goProductDetail( categoryId, productCode ) {
			if( confirm( "상품 상세에서 옵션 선택 후 담으실 수 있습니다." ) ) {
				location.href= $.utils.config( 'LMAppUrlM' ) + '/mobile/cate/PMWMCAT0004_New.do?CategoryID='+categoryId +'&ProductCD='+ productCode ;
			}
		}

		function _remove( seq ) {
			$.api.set({
				apiName : 'myByHistoryRemove',
				data : {
					'histSeq' : seq
				},
				traditaionnal : true,
				successCallback : function( data ) {
					if( data.success === true ) {
						myByProductHistory.get();
					} else {
						alert( data.message );
					}
				}
			});
		}
	})();

	window.myByProductHistory = myByProductHistory;
}));
(function (root, factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define([], factory);
	} else if (typeof module === 'object' && module.exports) {
		// Node. Does not work with strict CommonJS, but
		// only CommonJS-like environments that support module.exports,
		// like Node.
		module.exports = factory();
	} else {
		// Browser globals (root is window)
		root.imageLazyLoad = factory();
	}
}(typeof self !== 'undefined' ? self : this, function() {
	var lazy = [];

	function setLazy() {
		lazy = document.getElementsByClassName('lazy');
	}

	function registerListener(event, func) {
		if(window.addEventListener) {
			window.addEventListener(event, func);
		} else {
			window.attachEvent('on' + event, func);
		}
	}

	function isInViewport(el) {
		var rect = el.getBoundingClientRect();
		var spaceBetween = window.innerHeight || document.documentElement.clientHeight;

		return (
			rect.bottom >= -spaceBetween &&
			// rect.right >= 0 &&
			rect.top <= (spaceBetween) * 2
				// window.innerHeight ||
				// document.documentElement.clientHeight) &&
			// rect.left <= (
			// 	window.innerWidth ||
			// 	document.documentElement.clientWidth
			// )
		);

	}

	function lazyLoad() {
		lazy = document.getElementsByClassName('lazy');

		for(var i = 0; i < lazy.length; i++) {
			if(isInViewport(lazy[i])) {
				if(lazy[i].getAttribute('data-src')) {
					lazy[i].src = lazy[i].getAttribute('data-src');

					lazy[i].onload = function () {
						this.removeAttribute('data-src');
					};
				}
			}
		}

		cleanLazy();
	}

	function cleanLazy() {
		lazy = Array.prototype.filter.call(
			lazy,
			function (l) {
				return l.getAttribute('data-srs');
			}
		);
	}

	return {
		load: function () {
			lazyLoad();
		},
		setLazy: function () {
			setLazy();
		},
		registerListener : function (eventName, func) {
			switch (eventName) {
				case 'scroll' :
					registerListener('scroll', lazyLoad);
					break;
				case 'load' :
					registerListener('load', func)
					break;
			}
		}
	};
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root._InfiniteScroll = factory();
	}
}( window, function() {
	var InfiniteScroll = function( options ) {
		var defaults = {
			threshold : 1000,
			scrollPosition : 0,
			setHeight: function() {
				defaults.elementPostion = (defaults.container.outerHeight(true) + defaults.container.offset().top ) * 2;
			},
			callback : function() {}
		},
		documentHeight = document.documentElement.clientHeight;

		function init() {
			if( options ) {
				for( var prop in options ) {
					defaults[ prop ] = options[ prop ];
				}
			}
		}

		this.setPosition = function( pos ) {
			defaults.scrollPosition = pos;

			var scrollTop = ( pos + documentHeight ) * 2;

			defaults.elementPostion = (defaults.container.outerHeight(true) + defaults.container.offset().top);

			if( scrollTop >= defaults.elementPostion ) {
				defaults.callback( defaults );
			}
		};

		init();
	}
	
	return InfiniteScroll;
}));
(function(root, factory) {
	'use strict';

	if (typeof define === 'function' && define.amd) {
		define(factory);
	} else if (typeof exports !== 'undefined') {
		module.exports = factory();
	} else {
		root.McouponAppDown = factory();
	}
})(window, function() {
	'use strict';

	$.templates('mcouponLayer',
		'<div class="layer-mask">' +
		'	<div class="layer-mcoupon">' +
		'		<button type="button" class="btn-mcoupon-benefit">앱에서 혜택 받기</button>' +
		'		<div class="close-area">' +
		'			<button type="button" class="btn-close-sevendays">7일 동안 보지 않기</button>' +
		'			<button type="button" class="btn-close">닫기</button>' +
		'		</div>' +
		'	</div>' +
		'</div>'
	);

	var $html = $('html');

	function parseHTML() {
		var $docFrag = document.createDocumentFragment();
		var $layer = document.createElement('div');
		$layer.classList.add('mcoupon-down-recommend');
		$layer.innerHTML = $.render['mcouponLayer']();
		$docFrag.appendChild($layer);
		return $docFrag;
	}

	function init() {
		var _temp = parseHTML();
		schemeLoader.loadScheme({key: 'hideBar'});
		$('body').append(_temp);
		$html.addClass('layer-masking-mall');
		addEvent();
	}

	function addEvent() {
		var $popupLayer = $('.mcoupon-down-recommend');
		var $btnMcouponApp = $popupLayer.find('.btn-mcoupon-benefit');
		var $sevenDayClose = $popupLayer.find('.btn-close-sevendays');
		var $dayClose = $popupLayer.find('.btn-close');

		function popupClose() {
			$popupLayer.remove();
			$html.removeClass('layer-masking-mall');
			schemeLoader.loadScheme({key: 'showBar'});
		}

		$btnMcouponApp.on('click', function(e) {
			e.preventDefault();
			if($.utils.isIOS()){
				window.location.href = 'http://itunes.apple.com/app/id987435592';
			} else if($.utils.isAndroid()){
				window.location.href = 'market://details?id=com.lottemart.lmscp';
			}
		});

		$sevenDayClose.on('click', function() {
			setCookie('ncookie', 'done', 7);
			popupClose();
		});

		$dayClose.on('click', function() {
			popupClose();
		});

		function setCookie(name, value, expiredays) {
			var todayDate = new Date();
			todayDate.setDate(todayDate.getDate() + expiredays);
			document.cookie = name + '=' + escape(value) + '; path=/; expires=' + todayDate.toGMTString() + ';';
		}
	}

	return {
		open: function() {
			var cookiedata = document.cookie;
			if (cookiedata.indexOf('ncookie=done') < 0) {
				init();
			}
		}
	};
});
