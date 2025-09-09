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