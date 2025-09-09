;(function (root, factory) {
    'use strict';

    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'))
    } else {
        root.tuneLoader = factory(root.jQuery);
    }
}(window, function ($) {
    'use strict';

    var interfaces = {
            registrationAttempt: window.TUNE ? window.TUNE.registrationAttempt : undefined,           //(JSON string)
            registration: window.TUNE ? window.TUNE.registration : undefined,                         //(JSON string)
            loginAttempt: window.TUNE ? window.TUNE.loginAttempt : undefined,                         //()
            login: window.TUNE ? window.TUNE.login : undefined,                                       //(JSON string)
            storeRegistrationAttempt: window.TUNE ? window.TUNE.storeRegistrationAttempt : undefined, //()
            storeRegistration: window.TUNE ? window.TUNE.storeRegistration : undefined,               //(JSON string)
            defStoreChanged: window.TUNE ? window.TUNE.defStoreChanged : undefined,                   //(JSON string)
            viewHome: window.TUNE ? window.TUNE.viewHome : undefined,                                 //()
            viewGnbMenu: window.TUNE ? window.TUNE.viewGnbMenu : undefined,                           //(JSON string)
            search: window.TUNE ? window.TUNE.search : undefined,                                     //(JSON string)
            viewPromotion: window.TUNE ? window.TUNE.viewPromotion : undefined,                       //(JSON string)
            viewCategory: window.TUNE ? window.TUNE.viewCategory : undefined,                         //(JSON string)
            viewProduct: window.TUNE ? window.TUNE.viewProduct : undefined,                           //(JSON string)
            viewEvent: window.TUNE ? window.TUNE.viewEvent : undefined,                               //(JSON string)
            viewCart: window.TUNE ? window.TUNE.viewCart : undefined,                                 //()
            purchase: window.TUNE ? window.TUNE.purchase : undefined                                  //(JSON string)
        },
        converterKey = {
            registration_attempt: 'registrationAttempt',
            registration: 'registration',
            login_attempt: 'loginAttempt',
            login: 'login',
            store_registration_attempt: 'storeRegistrationAttempt',
            store_registration: 'storeRegistration',
            def_store_changed: 'defStoreChanged',
            view_home: 'viewHome',
            view_gnb_menu: 'viewGnbMenu',
            search: 'search',
            view_promotion: 'viewPromotion',
            view_category: 'viewCategory',
            view_product: 'viewProduct',
            view_event: 'viewEvent',
            view_cart: 'viewCart',
            purchase: 'purchase'
        },
        propertyObj = {},
        noParamInterfaceArray = ['loginAttempt', 'storeRegistrationAttempt', 'viewHome', 'viewCart'],
        hasOwnProperty = Object.prototype.hasOwnProperty;

    function _setProperty(deviceId, infoPushYn, mktPushYn, deviceType, adId, model) {
        propertyObj.deviceId = deviceId;
        propertyObj.infoPushYn = infoPushYn;
        propertyObj.mktPushYn = mktPushYn;
        propertyObj.deviceType = deviceType;
        propertyObj.adId = adId;
        propertyObj.model = model;
    }

    function _isIOS() {
        return /iP(hone|od|ad)/.test(navigator.userAgent) && !window.MSStream;
    }

    function _isIOSWithWKWebview() {
        return _isIOS() && window.webkit && window.webkit.messageHandlers;
    }

    function _postMessage(interfaceName, optionObj) {
        var interfaceKey = converterKey[interfaceName],
            postJSONString = JSON.stringify({
            messageName: interfaceKey,
            messageInfo: JSON.stringify(optionObj)
        });

        try {
            webkit.messageHandlers.TUNE.postMessage(postJSONString);
            return true;
        } catch(error) {
            return false;
        }
    }

    function _runInterface(interfaceName, optionObj) {
        var interfaceKey = converterKey[interfaceName];

        if (!window.TUNE
            || $.isEmptyObject(interfaces)
            || !hasOwnProperty.call(interfaces, interfaceKey)) {
            return false;
        }

        var isNoParamInterface = $.inArray(interfaceKey, noParamInterfaceArray) > -1,
            tuneInterface = interfaces[interfaceKey],
            param = JSON.stringify(optionObj),
            runInterface = $.isEmptyObject(tuneInterface) ? window.TUNE[interfaceKey] : tuneInterface;

        if (!runInterface) {
            return false;
        }

        if (isNoParamInterface) {
            window.TUNE[interfaceKey]();
        } else {
            window.TUNE[interfaceKey](param);
        }

        return true;
    }

    function saveDeviceIdAndPushYnOnServer(deviceId, infoPushYn, mktPushYn) {
    	_setProperty(deviceId, infoPushYn, mktPushYn);

        $.api.set({
            apiName : 'saveDeviceIdAndPushYnOnServer',
            data : {
                'deviceId' : deviceId,
                'infoPushYn': infoPushYn,
                'mktPushYn': mktPushYn
            },
            successCallback : function( data ) {
                // I don't know server return data.
            }
        });
    }
    
    function saveAdIdOnServer(adId, modelId) {
    	_setProperty(adId, modelId);

        $.api.set({
            apiName : 'saveAdIdOnServer',
            data : {
                'adId': adId,
                'modelId': modelId
            },
            successCallback : function( data ) {
            }
        });
    }

    function loadScheme(interfaceName, optionObj) {
        if (_isIOSWithWKWebview()) {
            return _postMessage(interfaceName, optionObj);
        } else {
            return _runInterface(interfaceName, optionObj);
        }
    }

    return {
        getDeviceId : function(){ return propertyObj.deviceId },
        getInfoPushYn : function(){ return propertyObj.infoPushYn },
        getMktPushYn : function(){ return propertyObj.mktPushYn },
        saveDeviceIdAndPushYnOnServer : saveDeviceIdAndPushYnOnServer,
        saveAdIdOnServer : saveAdIdOnServer,
        loadScheme : loadScheme
    };
}));