;(function (root, factory) {
    'use strict';

    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory()
    } else {
        root.schemeLoader = factory();
    }
}(window, function () {
    'use strict';

    var schemes = {
        // Basic Scheme
        basketCountUpdate: 'lottemartmall://basketcountupdate',
        lnbOpen: 'lottemart-shopping-iphone://lnbopen',
        lnbClosed: 'lottemart-shopping-iphone://lnbclosed',
        rnbOpen: 'lottemart-shopping-iphone://rnbopen',
        rnbClosed: 'lottemart-shopping-iphone://rnbclosed',
        showBar: 'lottemartapp://showBar',
        hideBar: 'lottemartapp://hideBar',
        setting: 'lottemartmall://setting',
        closeIntro : 'lottemartmall://closeIntro',
        floatingBannerOpen: 'lottemartapp://floatingBanner?id=lpoint&status=open',
        floatingBannerClose: 'lottemartapp://floatingBanner?id=lpoint&status=close',
        pushOn: 'push://on',
        pushOff: 'push://off',
        pushList: 'lottemartapp://pushList?url=',
        openToysrus : 'lottemartmall://openToysrus',
        openPopup : 'lottemartapp://openPopup?url=',
        openUrl : 'lottemartmall://openUrl?url=',
        sendSMS: 'lottemartapp://sendSMS?url=',
        showPickUp: 'lottemartapp://showPickUp',
        hidePickUp: 'lottemartapp://hidePickUp',
        isMainTrue: 'lottemartapp://isMainTrue',
        isMainFalse: 'lottemartapp://isMainFalse',
        lodingStart: 'lottemartapp://lodingStart',
        lodingEnd: 'lottemartapp://lodingEnd',
        menuBarHomeButton: 'lottemartapp://menuBarHomeButton',
        menuBarBasketButton: 'lottemartapp://menuBarBasketButton',
        menuBarMymallButton: 'lottemartapp://menuBarMymallButton',
        showZoomViewUrl: 'lottemartapp://showZoomViewUrl?title=&openUrl=',
        showZoomViewNextUrl: 'lottemartapp://showZoomViewNextUrl?title=',
        searchVoice: 'lottemart-shopping-iphone://voice',
        searchQRCode: 'lottemart-shopping-iphone://qrcode',
        tuneAddADID: 'lottemartapp://tuneAddADID',
        openLottemartApp : 'lottemartmobileapp://goURL=',
        transeperSsoToken : 'lottemartmobileapp://transeperSsoToken?ssoToken=',
        pushStatus : 'lottemartmall://pushStatus',
        pushApplicationSetting : 'lottemartmall://pushApplicationSetting'
    },
    versions = {
        // Basic
        openToysrus: {ios: '10.13', android: '10.79'},
        openUrl: {ios: '10.14', android: '10.80'},
        openPopup: {ios: '10.14', android: '10.80'},
        closeIntro : {ios:'9.4', android:'9.4'},
        showZoomViewUrl : {ios:'10.20', android:'10.90'},
        showZoomViewNextUrl : {ios:'10.21', android:'10.91'},
        transeperSsoToken : {ios:'10.40', android:'11.11'},
        // Default
        defaultVersion: {ios: '9.9.3', android: '10.73'}
    },
    excludeParamKeys = ['key', 'id', 'status', 'callback'],
    hasOwnProperty = Object.prototype.hasOwnProperty,
    patternUrl = /^([^:\/?#]+:)?(\/\/[^\/?#]*)?([^?#]*)(\?[^#]*)?(#(.*))?/,
    patternQueryString = /[?&]+([^=&]+)=([^&]*)/gi,
    patternStripZero = /(\.0+)+$/,
    schemeQueue = [];

    Array.prototype._forEach = function (cb) {
        var l = this.length, r = true;
        for (var i = 0; i < l; i++) r = cb(this[i], i);
        return r;
    };

    function _logColor(logTitle, logContent) {
        var urlParts = window.location.hostname.split('.');
        if ('ms' === urlParts.shift()) {
            console.log("%c" + logTitle + "%c" + logContent, "background:black; color:yellow", "background:black; color:red");
        }
    }

    function _trim(target) {
        return target.replace(/\s+/g, '');
    }

    function _isEmpty(obj) {
        if (obj === null) return true;
        if (obj.length > 0) return false;
        if (obj.length === 0) return true;
        if (typeof obj !== "object") return true;

        for (var key in obj) {
            if (hasOwnProperty.call(obj, key)) return false;
        }
        return true;
    }

    function _serialize(obj) {
        var param = [];
        for (var p in obj)
            if (obj.hasOwnProperty(p)) {
                param.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
            }
        return param.join("&");
    }

    function _isApp() {
        var ua= window.navigator.userAgent;
        return ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-IOS") > -1
            || ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-ANDROID") > -1
            || ua.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-DID") > -1;
    }

    function _isIOS() {
        return /iP(hone|od|ad)/.test(window.navigator.userAgent) && !window.MSStream;
    }

    function _isIOSWithWKWebview() {
        return _isIOS() && window.webkit && window.webkit.messageHandlers;
    }

    //Return values:
    //if a < b, false
    //if a > b and if a = b, true
    function _compareVersions(a, b) {
        var i, diff,
            segmentsA = a.replace(patternStripZero, '').split('.'),
            segmentsB = b.replace(patternStripZero, '').split('.'),
            l = Math.min(segmentsA.length, segmentsB.length);

        for (i = 0; i < l; i++) {
            diff = parseInt(segmentsA[i], 10) - parseInt(segmentsB[i], 10);
            if (diff) {
                return diff > 0;
            }
        }
        return (segmentsA.length - segmentsB.length) >= 0;
    }

    function _isMoreAppVersion(schemeObj) {
        var osType = _isIOS() ? 'ios' : 'android',
            currentVersion = navigator.userAgent.split('app-version')[1].replace('V.', ''),
            targetVersion = versions.hasOwnProperty(schemeObj.key)
                                ? versions[schemeObj.key][osType]
                                : versions['defaultVersion'][osType];
        return _compareVersions(_trim(currentVersion), _trim(targetVersion));
    }

    function _parseUrl(url, key) {
        var matches = url.match(patternUrl),
            params = {};

        if (matches[4]) {
            matches[4].replace(patternQueryString, function (s, k, v) {
                params[k] = v;
            });
        }
        return {
            protocol: matches[1] + matches[2],
            params: key ? params[key] : params
        };
    }

    function _extractObjectFromSchemeObj(obj, keys) {
        var target = {};

        for (var prop in obj) {
            if (keys.indexOf(prop) >= 0) continue;
            if (!hasOwnProperty.call(obj, prop)) continue;
            target[prop] = obj[prop];
        }
        return target;
    }

    document.onreadystatechange = function () {
        if (document.readyState === "complete" && schemeQueue.length > 0) {
            _dequeueSchemeQueue();
        }
    };

    function _load(scheme) {
        if (document.readyState !== 'complete') {
            schemeQueue.push(scheme);
        } else {
            window.location.href = scheme;
        }
    }

    function _dequeueSchemeQueue() {
        for (var i = 0, len = schemeQueue.length; i < len; i++) {
            if (schemeQueue[i] !== null) {
                window.location.href = schemeQueue[i];
                schemeQueue[i] = null;
            }
        }

        if (schemeQueue.every(function (v) {return v === null})) {
            schemeQueue = [];
        }
    }

    function _postMessage(schemeObj) {
        var postJSONString = JSON.stringify({
            messageName: schemeObj.key,
            messageInfo: _extractObjectFromSchemeObj(schemeObj, ['key', 'callback'])
        });

        _logColor('postMessage JSON String => ', postJSONString);

        try {
            window.webkit.messageHandlers.LOTTEMART.postMessage(postJSONString);
            return true;
        } catch(error) {
            return false;
        }
    }

    function _isScheme(key) {
        return !_isEmpty(schemes) && hasOwnProperty.call(schemes, key);
    }

    function _isInterface(key) {
        return !!(window.LOTTEMART && window.LOTTEMART[key]);
    }

    function _runCallback(schemeObj) {
        if (schemeObj.callback && typeof schemeObj.callback === 'function') {
            schemeObj.result = false;
            schemeObj.callback(schemeObj);
        }
    }

    function _runInterface(schemeObj) {
        var paramObject = _extractObjectFromSchemeObj(schemeObj, excludeParamKeys),
            returnState = false;

        if (_isEmpty(paramObject)) {
            window.LOTTEMART[schemeObj.key]();
            returnState = true;
        } else {
            window.LOTTEMART[schemeObj.key](JSON.stringify(paramObject));
            returnState = true;
        }

        return returnState;
    }

    function _runScheme(schemeObj) {
        if (!schemes[schemeObj.key]) {
            return false;
        }

        var parsedUrl = _parseUrl(schemes[schemeObj.key]),
            originalKeyArray = Object.keys(parsedUrl.params),
            keyArray = originalKeyArray.filter(function (el) {
                return excludeParamKeys.indexOf(el) < 0;
            }),
            lastIndex = keyArray.length - 1,
            notHaveParams = [];

        var hasAllParamKey = keyArray._forEach(function (k, i) {
            var hasKey = hasOwnProperty.call(schemeObj, k),
                result = true;

            if (!hasKey) {
                notHaveParams.push(k);
                result = false;
            }

            if (lastIndex === i) {
                if (!_isEmpty(notHaveParams) && console.log) {
                    _logColor('This scheme requires parameters : ', notHaveParams.join(', '));
                }
            }
            return result;
        });

        if (!hasAllParamKey) {
            return false;
        }

        keyArray._forEach(function (k) {
            if (hasOwnProperty.call(schemeObj, k)) {
                parsedUrl.params[k] = schemeObj[k];
            }
        });

        var queryString = _serialize(parsedUrl.params),
            scheme = parsedUrl.protocol + (!queryString ? '' : '?') + queryString;

        if (_isApp() && _isMoreAppVersion(schemeObj)) {
            _load(scheme);
            return true;
        } else if(schemeObj.key === 'openLottemartApp'){//externel
            _load(scheme + schemeObj['goURL']);
            setTimeout(function () {
                _runCallback(schemeObj);
            }, 3000);
            return true;
        }

        _runCallback(schemeObj);
        return false;
    }

    function loadScheme(schemeObj) {
        _logColor('schemeObj => ', JSON.stringify(schemeObj));

        if (_isIOSWithWKWebview()) {
            var returnState = _postMessage(schemeObj);

            if (!returnState) {
                returnState = _runScheme(schemeObj);
            }

            return returnState;
        } else {
            if (_isInterface(schemeObj.key)) {
                return _runInterface(schemeObj);
            } else {
                return _runScheme(schemeObj);
            }
        }
    }

    function isSupport(key) {
        if (_isIOSWithWKWebview()) {
            return true;
        } else if (_isInterface(key)) {
            return true;
        } else {
            return _isScheme(key);
        }
    }

    return {
        isSupport : isSupport,
        loadScheme : loadScheme
    };
}));