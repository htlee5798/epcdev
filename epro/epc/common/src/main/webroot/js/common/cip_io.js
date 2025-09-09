
var _cip_key = CryptoJS.enc.Hex.parse('000102030405060708090a0b0c0d0e0f');
var _cip_iv = CryptoJS.enc.Hex.parse('101112131415161718191a1b1c1d1e1f');

var _io = (function() {

	var CipIO = function() {

        var _this = this;
		
		var host;
		
        CipIO.prototype.connect=function (host) {
			var decrypted = CryptoJS.AES.decrypt(host, _cip_key, { iv: _cip_iv });
			this.host = decrypted.toString(CryptoJS.enc.Utf8);
		}

        CipIO.prototype.emit=function(param, objFunc, errFunc)
	    {
         	try
        	{
        		var now = new Date();
        		var sNowDataTime = now.getFullYear() + "" + (now.getMonth()+1) + "" + now.getDate() + "" + now.getHours() + "" + now.getMinutes() + "" + now.getSeconds();
				var rad = Math.floor(Math.random() * 10) * 100;
				
				rad = sNowDataTime + "_" + rad;
				
				$.jsonp({
					url: this.host + "?callback=__response&random=" + rad + "&" + param
				,	cache: "true"
				,	success: function(json) {
						objFunc(data);
				    }
				,	error: function(jqXHR, textStatus, errorThrown) {
						errFunc(jqXHR, textStatus, errorThrown);
					}
				});
        	}
        	catch(e)
        	{
        		alert(e);
        	}
	    }
		
	};
	
	return new CipIO();
	
})();
