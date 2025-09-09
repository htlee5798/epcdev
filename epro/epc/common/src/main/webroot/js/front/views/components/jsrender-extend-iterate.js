(function($){
	"use strict";
	$.views.tags({
		iterate:{
				baseTag:"for",
				render:function(val){
					var array=val,start=this.tagCtx.props.start||0,end=this.tagCtx.props.end;
					var array=val,delimiter=this.tagCtx.props.delimiter;
					if(delimiter){
						array = array.split(delimiter);
					}return this.base(array);
				},
				onArrayChange:function(ev,eventArgs){
					this.refresh();
				}
			}
		});
})(this.jQuery);