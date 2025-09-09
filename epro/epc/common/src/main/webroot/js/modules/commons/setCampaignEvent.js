(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.setCampaignEvent = factory( jQuery );
	}
}( window, function( $ ) {
	var utmConvertNames = {
		'utm_source' : 'campaignSource',
		'utm_medium' : 'campaignMedium',
		'utm_campaign' : 'campaignName',
		'utm_content' : 'campaignContent',
		'utm_term' : 'campaignTerm',
		'utm_keyword' : 'campaignKeyword'
	};
	function _setCampaignEvent() {
		var searchs = location.search.replace('?',''),
			campaignData = sessionStorage.getItem('campaigndata');
		
		if(hasCampaignCode(searchs, campaignData)) {
			sendToGA(getCampaignCode(searchs, campaignData));
		}
	}
	
	function hasCampaignCode(searchs, data) {
		var returnValue = true;
		
		if(searchs === '' && data === null) {
			returnValue = false;
		} else if(searchs.indexOf('utm_').length !== -1 || data !== null){
			returnValue = true;
		}
		
		return returnValue;
	}
	
	function getCampaignCode(searchs, data){
		var codes = {};
		
		if(searchs.indexOf('utm_') !== -1){
			var search = searchs.split('&');
			
			for(var i = 0, len = search.length; i < len; i++){
				var params = search[i],
					param = params.split('='),
					key = utmConvertNames[param[0]],
					value = param[1];
				
				if(key !== undefined) {
					codes[key] = value;
				}
			}
			sessionStorage.setItem('campaigndata', JSON.stringify(codes));
		} else if(data !== null) {
			codes = JSON.parse(data);
			sessionStorage.setItem('campaigndata', JSON.stringify(codes));
		}
		
		return codes;
	}
	
	function sendToGA(obj) {
		if(!window.ga){
			return;
		}
		window.ga('set',obj);
	}
	
	return _setCampaignEvent;
}));