define(function() {
	function _zeroFill(val, len, z) {
		z = z || '0';
		val = val + '';
		return val.length >= len ? val : new Array(len - val.length + 1).join(z) + val;
	}
	
	return _zeroFill;
});