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