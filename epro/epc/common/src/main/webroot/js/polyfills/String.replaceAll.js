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