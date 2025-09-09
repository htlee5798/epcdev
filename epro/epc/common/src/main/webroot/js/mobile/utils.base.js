/**
* @name : utils.base.js
*/
(function(window, document, $) {

    /**
    * @method : defaults
    * @params : object
    * @depends: config [Object]
    */
    var
        defaults = {
            'useLog': {'editable': false, 'value': true },
            'root': {'editable': true, 'value': '/ioniq-web-front' },
            'contextPath': { 'editable': true, 'value': '/ioniq-web-front' },
            'staticPath': { 'editable': true, 'value': '/ioniq-web-front/resources' },
            'imagePath': { 'editable': true, 'value': '/ioniq-web-front/resources/images' },
            'filePath' : { 'editable': true, 'value': '/ioniq-web-front/files' },
            'apiPath': { 'editable': true, 'value': '/ioniq-web-front/proxy' },
            'locale' : {'editable' : true , 'value' : 'en_US'}
        };

    function Utils() {

        var _utils = this;
        /**
		 * @depends: defaults (private)
		 * @type : method
		 * @name : config
		 * @param {object or string} n
		 * @return
		 */
        this.config = function(n, v) {

            if($.type(n) === 'object') {
                setObj(n);
            } else if($.type(n) === 'string' && arguments.length == 2) {
                setStr(n, v);
            } else if($.type(n) === 'string' && arguments.length == 1) {
                return get(n);
            } else if(arguments.length == 0) {
                return getProperty();
            } else {
                _utils.error('parameter is only json or string. current type : ' + $.type(n));
            }

            /**
	    	 * Description
	    	 * @method set
	    	 * @param {} obj
	    	 * @return
	    	 */

            function setObj(obj) {
                $.each(obj, function(n) {

                    if(defaults[n] && defaults[n].editable) {
                        if(typeof obj[n] === 'string' && (obj[n] === 'True' || obj[n] === 'False')) {
                            obj[n] = obj[n] == 'True';
                        }

                        defaults[n].value = obj[n] || defaults[n].value;
                        defaults[n].editable = false;
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
                } else {
                    _utils.error(n + ' name is not editabled');
                }
            }

            /**
	    	 * Description
	    	 * @method get
	    	 * @param {} n
	    	 * @return MemberExpression
	    	 */
            function get(n) {            	//
                if(defaults[n] === undefined && n !== 'all') {
                    _utils.error('undefined property name : ' + n);
                }

                var returnVal;

                if(n == 'all') {
                    returnVal = {};
                    for(var item in defaults) {
                        returnVal[item] = defaults[item];
                    }
                } else {
                    returnVal = defaults[n].value;
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
        };

        /**
		* @params : object
		* @name log
		* @param {} obj
		*/
        this.log = function(obj) {
            var useLog = this.config('useLog') == true && window.console;
            if(useLog) {
                if(typeof obj == "object" && console.dir) {
                    console.dir && console.dir(obj);
                } else {
                    console.log && console.log(obj);
                }
            }
        };

        this.cookie = {
            get: function(sKey) {
                if(!sKey) {
                    return null;
                }

                var cookie = document.cookie.replace(new RegExp("(?:(?:^|.*;)\\s*" + encodeURIComponent(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*([^;]*).*$)|^.*$"), "$1") || null;

                if(cookie) {
                    try {
                        cookie = decodeURIComponent(cookie);
                    } catch(e) {
                        cookie = decodeURIComponent(unescape(cookie)); // escape 처리된 문자열이 저장된 경우, catch.
                    }
                }

                return cookie;
            },
            /**
            * @param : vEnd{number/string/date}
            */
            set: function (sKey, sValue, vEnd, sPath, sDomain, bSecure) {

                if(!sKey || /^(?:expires|max\-age|path|domain|secure)$/i.test(sKey)) {
                    return false;
                }
                var sExpires = "";

                sPath = sPath == undefined ? '/' : sPath;

                if(vEnd) {
                    switch(vEnd.constructor) {
                        case Number:
                            sExpires = vEnd === Infinity ? "; expires=Fri, 31 Dec 9999 23:59:59 GMT" : "; max-age=" + vEnd * 60 * 60 * 24;
                            break;
                        case String:
                            sExpires = "; expires=" + vEnd;
                            break;
                        case Date:
                            sExpires = "; expires=" + vEnd.toUTCString();
                            break;
                    }
                }
                document.cookie = encodeURIComponent(sKey) + "=" + encodeURIComponent(sValue) + sExpires + (sDomain ? "; domain=" + sDomain : "") + (sPath ? "; path=" + sPath : "") + (bSecure ? "; secure" : "");
                return true;
            },
            remove: function(sKey, sPath, sDomain) {

                if(!this.has(sKey)) {
                    return false;
                }
                sPath = sPath == undefined ? '/' : sPath;

                document.cookie = encodeURIComponent(sKey) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT" + (sDomain ? "; domain=" + sDomain : "") + (sPath ? "; path=" + sPath : "");
                return true;
            },
            has: function(sKey) {
                if(!sKey) {
                    return false;
                }
                return (new RegExp("(?:^|;\\s*)" + encodeURIComponent(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=")).test(document.cookie);
            },
            keys: function() {
                var aKeys = document.cookie.replace(/((?:^|\s*;)[^\=]+)(?=;|$)|^\s*|\s*(?:\=[^;]*)?(?:\1|$)/g, "").split(/\s*(?:\=[^;]*)?;\s*/);
                for(var nLen = aKeys.length,
                        nIdx = 0; nIdx < nLen; nIdx++) {
                    aKeys[nIdx] = decodeURIComponent(aKeys[nIdx]);
                }
                return aKeys;
            }
        };

        this.isMobile = function() {
            var rtn = false;

            if(navigator.userAgent.match(/Android|Mobile|iP(hone|od|ad)|BlackBerry|IEMobile|Kindle|NetFront|Silk-Accelerated|(hpw|web)OS|Fennec|Minimo|Opera M(obi|ini)|Blazer|Dolfin|Dolphin|Skyfire|Zune/)) {
                rtn = true;
            }

            return rtn;
        };
        
        this.isIOS = function() {
        	var rtn = false;

            if(navigator.userAgent.match(/iP(hone|od|ad)/)) {
                rtn = true;
            }

            return rtn;
        };

        this.html = {
            /**
            * @name : decodeEntities [function]
            * @desc : decode html entities.
            * @param : s{string}
            */
            decodeEntities: function(s) {
                var r,
                    t = document.createElement('textarea');

                t.innerHTML = s;
                r = t.textContent || t.innerText;
                t = null;
                return r;
            },
            /**
            * @name : strip [function]
            * @desc : return remove html tag from argument
            */
            strip: function(s) {
                if($.trim(s) === '') {
                    return s;
                }

                var reg_tag = /(<([^>]+)>)/ig,
                    r = '';
                s = s.replace(reg_tag, '');
                if(_utils.decodeEntities) {
                    s = _utils.decodeEntities(s);
                }
                r = s.replace(reg_tag, '');
                return r;
            }
        };

        /**
        * @name : localStorage
        * @return : {get, set, remove}
        */
        this.localStorage = {
            /**
            * @type : method
            * @name : get
            * @param : name{string}
            */
            get: function(name) {
                return localStorage.getItem(name);
            },
            /**
            * @type : method
            * @name : set
            * @param : 
            {string}
            * @param : value{any}
            */
            set: function(k, v) {
                var str = Array.isArray(v) ? JSON.stringify(v) : v;

                setItem(k, str);

                function setItem(key, value) {
                    if(localStorage.key(key)) {
                        localStorage.removeItem(key);
                    }

                    localStorage.setItem(key, value);
                }
            },
            /**
            * @type : method
            * @name : remove
            * @param : name{string}
            */
            remove: function(name) {
                localStorage.removeItem(name);
            }
        };
        
        /**
         * @name : sessionStorage
         * @return : {get, set, remove}
         */
         this.sessionStorage = {
             /**
             * @type : method
             * @name : get
             * @param : name{string}
             */
             get: function(name) {
                 return sessionStorage.getItem(name);
             },
             /**
             * @type : method
             * @name : set
             * @param : 
             {string}
             * @param : value{any}
             */
             set: function(k, v) {
                 var str = Array.isArray(v) ? JSON.stringify(v) : v;

                 setItem(k, str);

                 function setItem(key, value) {
                     if(sessionStorage.key(key)) {
                    	 sessionStorage.removeItem(key);
                     }

                     sessionStorage.setItem(key, value);
                 }
             },
             /**
             * @type : method
             * @name : remove
             * @param : name{string}
             */
             remove: function(name) {
            	 sessionStorage.removeItem(name);
             }
         };

        /**
        * @type : method
        * @name : error
        * @param : str{string}
        * @param : isLog{boolean}
        */
        this.error = function(str, isLog) {
            if(isLog) {
                _utils.log('[ERROR] : ' + str);
            } else {
                $.error(str);
            }
        };

        /**
        * @name : isReferrerOwn
        * @desc : referrer 가 같은 도메인인지 확인.
        */
        this.isReferrerOwn = function (str, isLog) {
            return document.referrer.indexOf(document.domain) > -1 ? true : false;
        };

        /**
        * @name : parseBoolean
        * @param : str{string}
        */
        this.parseBoolean = function(str) {
            if(typeof str != 'boolean') {
                str = (str == 'True' || str == 'true') ? true : false;
            }
            return str;
        };

        /**
        * @name : parseInt
        * @param : str{string}
        */
        this.parseInt = function (str) {
            if (typeof str != 'number') {
                str = parseInt(str, 10);
            }
            if (isNaN(str)) {
                utils.error('error parse');
            }
            return str;
        };        
        
        this.currencySymbols = function( str ) {
        	var symbols = {
    		    'USD': '$', // US Dollar
    		    'EUR': '€', // Euro
    		    'CRC': '₡', // Costa Rican Colón
    		    'GBP': '£', // British Pound Sterling
    		    'ILS': '₪', // Israeli New Sheqel
    		    'INR': '₹', // Indian Rupee
    		    'JPY': '¥', // Japanese Yen
    		    'KRW': '₩', // South Korean Won
    		    'NGN': '₦', // Nigerian Naira
    		    'PHP': '₱', // Philippine Peso
    		    'PLN': 'zł', // Polish Zloty
    		    'PYG': '₲', // Paraguayan Guarani
    		    'THB': '฿', // Thai Baht
    		    'UAH': '₴', // Ukrainian Hryvnia
    		    'VND': '₫', // Vietnamese Dong
    		};
        	
        	return symbols[ str ] || '';
        };
    };

    window.utils = new Utils();

})(window, document, jQuery);