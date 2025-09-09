function _(_) { alert(_); }
function $C(caller) { var f = arguments.callee.caller; if(caller) f = f.caller; var pat = /^function\s+([a-zA-Z0-9_]+)\s*\(/i; pat.exec(f); var func = new Object(); func.name = RegExp.$1; return func; }
function forfor(a, f) { for (var i=0,c=a.length;i<c;i++) f(a[i]); }
function range(end, start) { var r = []; for (var i=(start||0);i<end;i++) r.push(i);	return r; }
function in_array(n, h, s) { var f = false;var k='';s = !!s;for (k in h){if ((s&&h[k]==n)||(!s&&h[k]==n)){f = true;}}return f; }
function map(func, array) { var result = [];forfor(array, function (element) {result.push(func(element));});return result; }
function checkup(test, array) { for (var i = 0; i < array.length; i++) { var found = test(array[i]); if (!found) return array[i]; }	return true; }
function orText(msg, txt) {	var message = msg||txt; alert(message); }
function ajaxError(xhr, status, thrown){ if(status != 200) { alert((thrown?thrown : xhr.status + ':' + status )); }}
function json(url) { try { var func = eval('_'+$C(arguments.callee).name); $.getJSON(url, func); } catch (e){alert(e.message); }}
function ajax(url, params, async) { var bool = async||true;	var func = eval( '_'+$C(arguments.callee).name ); $.ajax({ type:'POST', url:url, data:params, async:bool, dataType:'text', success:func }); }
if(typeof String.prototype.trim !== 'function') {
  String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/g, ''); 
  }
}

jQuery.fn.removeAll = function() { $(this).each(function() { $(this).remove(); }); }

function imgCycle(imagegallery,time, nav) {
	$(imagegallery).cycle({
		timeout : time,
		speed : 500,
		pager : nav,
		fx : 'scrollHorz',
		slideExpr : 'li',
		next : '#next2',
		prev : '#prev2'
	});

	$(imagegallery).touchwipe({
		wipeLeft : function() {
			$(imagegallery).cycle("next");
		},
		wipeRight : function() {
			$(imagegallery).cycle("prev");
		}
	});
}



function getBytes(el){
	var len = el.val().length;
	var tmp = el.val();
	var bytes = 0;

	for (var i=0; i<len; i++) {
		var chr = tmp.charAt(i);
		
		if (escape(chr).length>4){
			bytes +=3;	
		} else if (chr!='\r') {
			bytes += 1;			
		}
		
	}	// end of for : 
	
	return bytes;	
}

function cut_string(el, limit)
{
	var len = el.val().length;
	var tmp = el.val();
	var bytes = 0;
	var ret = '';
	
	for (var i=0; i<len; i++) {
		var chr = tmp.charAt(i);
		
		if (escape(chr).length>4){
			bytes +=3;	
		} else if (chr!='\r') {
			bytes += 1;			
		}
		ret += chr;
		
		if (bytes>limit) {
			break;
		}		
	}	// end of for : 
	
	ret = ret.substr(0, ret.length-1);
	return ret;
}

function showBytes(from, limit, to){
	var bytes = getBytes(from);
	
 	if (bytes>limit) {
		from.val( cut_string(from, limit));
	}
 	var zero = (bytes>0)?--bytes:bytes;
	to.html(zero); 
}

/*
input_pattern(/([^가-힣\x20^a-z^A-Z^0-9])/gi, $(this));	* 

*/
function input_pattern(pattern, el)
{
	if (pattern.test(el.val())) {
		el.val(el.val().replace(pattern, ''));
		alert("특수문자는 사용하실 수 없습니다.");
	}
}

/*
function str_repeat(s, l) {
	var ret='', i;    	
	while(ret.length<l) ret +=s;
	out = ret.substr(0, l);
	return ret;
}
    */

/**
 * LPAD
 * @param inStr
 * @param totalLen
 * @param strReplace
 * @return
 */
function lpad(inStr ,totalLen, strReplace){
	
	var strAdd  = "";
	var diffLen = totalLen - inStr.length;
	
	for(var i = 0; i < diffLen; ++i){
		strAdd += strReplace;
	}
	return strAdd+inStr;
}

function str_repeat( _in, _num) {
	var out = '';
	for(var i=0; i<_num; i++) {
		out += _in;
	}
	return out;
}
    
function _lpad(s, l, r) {
	var out = '';
	var c = l - s.length;
	return str_repeat(0, c) + s;		
}
