
var cipToolkit = (function () {
	
	_io.connect('gf+PiZbKeMT7RqC61q1serdKfaBot1fa/AiVW+l42+E=');

	var CipToolkit = function() {

        var _this = this;
		var _parameters = new JHashMap();
		var _callback;
		var _OutDataNum = null;
		var _OutData = null;
		var _iframe;

		
        _this.setParameters = function (key, value) {
        	_parameters.put(key, value);
        }

        _this.setCallback = function (callback) {
        	_callback = callback;
        }
        
        _this.SelectCertificate=function()
        {
        	_this.layerEnable(true);
        	
			_parameters.put("type", "APP");
			
        	var param = _parameters.toQueryString();
        	
         	try
        	{
        		var now = new Date();
        		var sNowDataTime = now.getFullYear() + "" + (now.getMonth()+1) + "" + now.getDate() + "" + now.getHours() + "" + now.getMinutes() + "" + now.getSeconds();
				var rad = Math.floor(Math.random() * 10) * 100;
				
				rad = sNowDataTime + "_" + rad;
				
				$.jsonp({
					url: "http://127.0.0.1:54321?callback=__response&random=" + rad + "&" + param
				,	cache: "true"
				,	success: function(json) {
						__response(data);
				    }
				,	error: function(jqXHR, textStatus, errorThrown) {
						__error(jqXHR, textStatus, errorThrown);
					}
				});
        	}
        	catch(e)
        	{
        		alert(e);
        	}
        	
        }
        
        
        // 결과
        __response=function(msg)
        {
        	_OutDataNum	= msg[0].OutDataNum;
        	_OutData	= msg[0].OutData;

			_this.layerEnable(false);

			if( _this.isFunction(_callback) )
				_callback(_OutDataNum, _OutData);
        }
        
        // 오류
        __error=function(jqXHR, texttSatus, errorThrown)
        {
			_this.layerEnable(false);
			
			if(_OutDataNum == null)
			{
				if(confirm("인증서 관리 프로그램이 설치되지 않았습니다.\n인증서 다운로드 페이지로 이동 하시겠습니까?")==false) return;
				//location.href="http://ca.lottemart.com/cip/CertiPro_Install.exe";
				location.href="http://midasdev.lottemart.com/krca/cip/CertiPro_Install_midas.exe";
			}
        }
        
        _this.layerEnable=function(bol)
        {
        	if(bol)
        	{
				$('body').append('<div id="cip-bg" class="cip-background cip-hide"></div>');
				$("#cip-bg").removeClass("cip-hide").addClass("cip-show-5");
        	}
        	else
        	{
				$("#cip-bg").remove();
        	}
        }

        _this.isFunction=function(func) {
	        return typeof func === 'function';
	    }
        
	
		_this.iframeOpen=function() {
	        _iframe = document.createElement('iframe');
	        _iframe.id = 'cip_sign_iframe';
	        _iframe.name = 'cip_sign_iframe';
	        _iframe.src = 'SelectCertficate.html';
	        _iframe.title = "전자서명";
	        _iframe.width = "500px";
	        _iframe.height = "650px";
	        _iframe.scrolling="no";
	        _iframe.frameborder="no";
	        _iframe.style.top = "30%";
	        _iframe.style.left = "50%";
	        _iframe.style.marginTop = "-200px";
	        _iframe.style.marginLeft = "-195px";
	        _iframe.style.position = 'fixed';
	        _iframe.style.zIndex = "9999";
	        _iframe.style.border = "none";
	        
	        //_iframe.style.border = "1px solid";
	        //_iframe.style.borderColor= "#e7e7e7";
	
            document.body.appendChild(_iframe);
	
	    }
		
	};
	
    return new CipToolkit();
	
})();



