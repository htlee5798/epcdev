(function( $, window, document ) { 
	'use strict';
	/**
	 * See (http://jquery.com/).
	 * @name jQuery
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 */

	/**
	 * See (http://jquery.com/)
	 * @name fn
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 * @memberOf jQuery
	 */
	
	/**
	 * <h5>imagesLoaded</h3>
	 * <p>대상 DOMElement 안의 이미지가 로드가 완료되었을 시의 이벤트를 체크한다.</p>
	 * <p>onload 또는 onerror 이벤트를 체크</p>
	 * <p>$.Deferred() 객체를 return 하며, then(), done() 등 callback 함수를 이용할 수 있다.
	 * @class imagesLoaded
	 * @memberOf jQuery.fn
	 * @returns {Object} $.Deferred()
	 * @example <caption>JS - init</caption>
	 * <script type="text/javascript">
	 * 	$list.append(data).imagesLoaded().then(function() {
	 * 		//[ Callback ]
	 * 	});
	 * </script>
	 */
	$.fn.imagesLoaded = function () {

		var $imgs = this.find('img[src!=""]');
		if (!$imgs.length) {return $.Deferred().resolve().promise();}

		var dfds = [];  
		$imgs.each(function(){
			var dfd = $.Deferred();
			dfds.push(dfd);

			var img = new Image();
			img.onload = function(){dfd.resolve();}
			img.onerror = function(){dfd.resolve();}
			img.src = this.src;
		});

		return $.when.apply($,dfds);
	}
	
})( jQuery, window, document );
