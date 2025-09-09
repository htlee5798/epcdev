if(String.prototype.contains === undefined) {
	String.prototype.contains = function(str) {
		if(this.length > 0) {
			return this.toString().indexOf(str) >= 0;
		}
		
		return false;
	}	
}