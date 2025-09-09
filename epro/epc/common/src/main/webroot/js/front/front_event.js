function FUNC_eventList() { // 응모이벤트 펼치기
	if( jQuery('.jq_eventTable').length ) {
		var obj = jQuery('.jq_eventTable');
		if( jQuery(obj).find('.jq_content').length ) {
			jQuery(obj).find('.jq_content').hide();
		}

		if( jQuery(obj).find('.jq_title').length ) {
			var trObj = jQuery(obj).find('.jq_title');
			if( jQuery(trObj).find('.jq_subject').length ) {
				var tdObj = jQuery(trObj).find('.jq_subject');

				var parentTagName = jQuery(tdObj).parent().get(0).tagName;
				parentTagName = parentTagName.toLowerCase();
				if( parentTagName == 'a' ) {
					if( jQuery(tdObj).parent().attr('href') == '#' ) {
						jQuery(tdObj).parent().attr('href', 'javascript:void(0);');
					}
				}
				tdObj=null;

				jQuery(obj).find('.jq_subject').live('click', function() {
					var nowObj = jQuery(this);
					jQuery(obj).find('.jq_title').each(function() {
						var thisObj = jQuery(this).find('.jq_subject');
						if( jQuery(nowObj).get(0) == jQuery(thisObj).get(0) ) {
							if( jQuery(this).next().css('display') == 'none' ) {
								jQuery(this).next().show();
							} else {
								jQuery(this).next().hide();
							}
						} else {
							jQuery(this).next().hide();
						}
						thisObj=null;
					});
				});
			}
			trObj=null;
		}
	}
}