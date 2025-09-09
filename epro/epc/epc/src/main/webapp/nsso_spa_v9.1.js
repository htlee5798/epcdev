'use strict';

const NSSO_PAGE = {
    ssoService: "ssoService",
    logoutService: "logoutService",
    spaAuthn: "spa/authn",
    spaLogin: "spa/loginResolve",
    spaUserinfo: "spa/userinfo"
}
const NSSO_PARAM = {
    ssosite: "ssosite",
    returnURL: "returnURL"
}

class NssoUser {
    constructor(data) {
        this.id = data.user ? data.user.id : '';
        this.attrs = data.user && data.user.attrs ? {...data.user.attrs} : {};
        this.token = data.token || '';
    }
}

class NssoManager {
    constructor(provider, appcode) {
        if (!provider.endsWith("/"))
            provider += "/";
        this.provider = provider;
        this.appcode = appcode;

        this.authnUrl = `${this.provider}${NSSO_PAGE.spaAuthn}`;
        this.loginUrl = `${this.provider}${NSSO_PAGE.spaLogin}?${NSSO_PARAM.ssosite}=${this.appcode}`;
        this.ssoServiceUrl = `${this.provider}${NSSO_PAGE.ssoService}?${NSSO_PARAM.ssosite}=${this.appcode}`;
        this.logoutServiceUrl = `${this.provider}${NSSO_PAGE.logoutService}?${NSSO_PARAM.ssosite}=${this.appcode}`;
        this.userinfoUrl = `${this.provider}${NSSO_PAGE.spaUserinfo}`;
    }

    async authn(errorCallback) {
        try {
            const data = await this.login_resolve();
            const errCode = data.resp.errcode;
            if (errCode === 10000011) {
                // MissingWebToken 상황
                return this.authn_request(data.returnUrl);   // SSO 서버로 이동 시켜, 로그인 시킨 후 되돌아 와야 함.
            } else if (errCode === 11040010) {
                // SessionNotAuthenticatedDomain 상황
                if(data.authnRequest)
                    return this.authn_request(data.returnUrl);
                return this.login_resolve();   // SSO 로그인은 되어 있지만, SPA 인증이 되어 있지 않은 상태. 해당 SPA 로그인 요청으로 시작 함.
            } else if (errCode !== 0) {
                if (!errorCallback)
                    return null;
                // 인증 체크 결과 문제가 있는 상황. 오류 메시지 보여주고, 로그아웃 시킴.
                const error = new Error(data.resp.errmsg);
                error.code = data.resp.errcode;  // 에러 코드를 Error 객체에 추가합니다.
                errorCallback(error);
                return this.logout(data.returnUrl);
            }
            // 인증 체크 결과 문제 없음. 인증 정보 리턴
            return new NssoUser(data);
        } catch (error) {
            if (!errorCallback)
                return null;
            const customError = new Error(error.message);
            customError.code = error.code || 1;  // 오류 코드가 있으면 사용하고, 없으면 'NotDefined(1)'를 사용합니다.
            errorCallback(customError);  // 오류 메시지와 코드를 콜백 함수에 전달합니다.
        }
    }

    async login_resolve() {
        const currentURL = window.location.href;
        if (!currentURL.includes('artifact')) {
            return this.session_check();
        }
        const params = this.parseURLParams(currentURL);
        const response = await fetch(`${this.loginUrl}`, {
            method: 'POST',
            headers: {
                'appcode': params.appcode,
                'challengecode': params.challengecode,
                'artifact': params.artifact,
                'nonce': params.nonce
            },
            credentials: 'include'
        });
        const data = await response.json();
        if (data.resp.errcode === 0 && data.nonce === params.nonce) {
            return data;
        } else {
            const error = new Error(data.resp.errmsg);
            error.code = data.resp.errcode;  // 에러 코드를 Error 객체에 추가합니다.
            throw error;
        }
    }

    async session_check() {
        const nonce = this.generateChallenge(8); // 8자리 난수 문자열 생성
        const response = await fetch(`${this.authnUrl}`, {
            method: 'GET',
            headers: {
                'appcode': this.appcode,
                'nonce': nonce
            },
            credentials: 'include'
        });
        return await response.json();
    }

    async authn_request(returnUrl, url = window.location.href) {
        const challenge = this.generateChallenge(16); // 인코딩 전의 평문
        const challengeEncoded = btoa(challenge); // Base64 인코딩
        const nonce = this.generateChallenge(8); // 8자리 난수 문자열 생성
        const response = await fetch(`${this.ssoServiceUrl}`, {
            method: 'POST',
            headers: {
                'returnURL': returnUrl,
                'challengecode': challengeEncoded,
                'nonce': nonce
            },
            credentials: 'include'
        });
        const data = await response.json();
        if(data.loginUrl) {
            location.href = data.loginUrl + `?${NSSO_PARAM.returnURL}=` + encodeURIComponent(data.returnURL);
        } else {
            locations.href = data.returnURL;
        }
        return null;
    }

    async userinfo(user, secret) {
        const response = await fetch(`${this.userinfoUrl}`, {
            method: 'POST',
            headers: {
                'appcode': this.appcode,
                'secret': secret,
                'token': user.token,
                'nonce': user.nonce
            },
            credentials: 'include'
        });
        const data = await response.json();
        if (data.resp.errcode !== 0) {
            return this.logout(window.location.href);
        }
        return new NssoUser(data);
    }

    logout(returnUrl) {
        if (returnUrl.includes('artifact')) {
            returnUrl = returnUrl.split('#')[0];
        }
        location.href = `${this.logoutServiceUrl}&${NSSO_PARAM.returnURL}=${encodeURIComponent(returnUrl)}`;
        return null;
    }

    generateChallenge(length) {
        const charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        const randomValues = new Uint8Array(length);
        window.crypto.getRandomValues(randomValues);
        return Array.from(randomValues).map(b => charset[b % charset.length]).join('');
    }

    parseURLParams(url) {
        const fragment = url.split('#')[1];
        const values = fragment.split('?')[0];
        const keyValuePairs = values.split('&');
        const params = {};
        keyValuePairs.forEach(pair => {
            const [key, value] = pair.split('=');
            params[key] = value;
        });
        return params;
    }
}