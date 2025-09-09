if(String.prototype.padStart === undefined) {
	String.prototype.padStart = function(totalLength, padString) {
		if(this.length >= totalLength) {
			return this;
		}
		
		var str = this.toString()
		  , padLength = totalLength - this.length;
		
		for(var i = 0; i < padLength; i++) {
			str += padString;
		}
		
		return str;
	}	
}