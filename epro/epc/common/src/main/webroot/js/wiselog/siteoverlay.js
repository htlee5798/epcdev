/**
 *  Nethru Script Module
 *  Copyright 2008 nethru, All Rights Reserved.
 **/

var _n_so_request = null;
var _n_so_referrer = null;
var _n_so_cookie = null;
var _n_so_agent = null;
var _n_so_pid = null;
var _n_so_ls = "//wlog.lottemart.com/wlo/Logging";		/* logging server */
var _n_wlo_image = new Image();

function n_so_pid(pid)
{
	_n_so_pid = pid;
}

function n_so_logging(x,y,w) 
{
	if ( _n_sid == "07010100000" )
		return;
	
	if ( _n_so_pid == null )
		return;
	
	var _n_request = (_n_so_request==null) ? document.location.protocol + "//" + document.domain + "/wloso.html" : _n_so_request;
	var _n_referrer = (_n_so_referrer==null) ? document.referrer : _n_so_referrer;
	var _n_cookie = (_n_so_cookie==null) ? n_getCookieStr() : _n_so_cookie;
	var _n_agent = (_n_so_agent==null) ? navigator.userAgent: _n_so_agent;
	var _n_so_value = _n_so_pid+"|"+x+"|"+y+"|"+w;

	var _n_target_url = _n_so_ls +
						"?ver=1.0.0" +
						"|sid=" + n_encodeStr(_n_sid) +
						"|r=" + n_encodeStr(_n_request) +
						"|rf=" + n_encodeStr(_n_referrer) +
						"|c=" + n_encodeStr(_n_cookie) +
						"|a=" + n_encodeStr(_n_agent) +
						"|wlo_so=" + n_encodeStr(_n_so_value) +
						"|dv=" + Math.random();

	_n_wlo_image.src = _n_target_url;

	/* using at no logging server */
	/*
	var _n_image2 = new Image();
	_n_image2.src = "/wloso.html?wlo_so=" + _n_so_value + "&dv=" + Math.random();
	*/
}

function getElementPosition(oElement, bLeft) {
    var nSize;
    var oElement;

    nSize = bLeft ? document.body.clientLeft : document.body.clientTop;

    while(oElement) {
        nSize += bLeft ? oElement.offsetLeft : oElement.offsetTop;

        oElement = oElement.offsetParent;
    }

    return(nSize);
}

function n_so_flash_oc(strID, nX, nY)
{
    var nPosX, nPosY;
    var w = 0;

    if ( strID == null ) return;

    try {
        w = document.body.clientWidth;
    }
    catch(ex) {
        if ( typeof(window.innerWidth) != "undefined" ) w = window.innerWidth;
        else return;
    }

    oElement = document.all[strID];
    if ( oElement == null ) return;
    
    /* only IE */
    if ( oElement.offsetParent == null ) return;

    nPosX = nX;
    nPosY = nY;

    nPosX += getElementPosition(oElement, true);
    nPosY += getElementPosition(oElement, false);

    n_so_logging(nPosX,nPosY,w);
}

function n_so_mc(e) 
{
	var db = document.body;
	var w = 0;
	var sl = 0;
	var st = 0;

	if ( e == null ) e = window.event;

	try {
		w = db.clientWidth;
	}
	catch(ex) {
		if ( typeof(window.innerWidth) != "undefined" ) w = window.innerWidth;
		else return;
	}


	try {
		if ( db.scrollLeft || db.scrollTop ) {
			sl = db.scrollLeft;
			st = db.scrollTop;
		}
		else {
			var dd = document.documentElement;
			if ( dd.scrollTop || dd.scrollLeft ) {
				sl = dd.scrollLeft;
				st = dd.scrollTop;
			}
		}
	}
	catch(ex) {
		return;
	}

	x = e.clientX + sl;
	y = e.clientY + st;

	n_so_logging(x,y,w);
}

document.onmousedown=n_so_mc;

