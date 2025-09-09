import $ from 'jquery';
import getMobilePath from './get-mobile-path';

const _recommendedReview = (id) => {
    return new Promise((resolve, reject) => {
        $.api.get({
            apiName : 'mobileUpdateProductReview',
            data : {
                "recommSeq" : id,
                "recommYN" : 'Y'
            },
            dataType: 'json',
            successCallback: function (data) {
                if(typeof data.rstMsg !== "undefined" && data.rstMsg === '상품평마다 1회씩만 추천이 가능합니다.') {
                    reject();
                } else if(data.rst === 1) {
                    resolve(data);
                }
            }
        })
    });
};

const _recommendedExperience = (url) => {
    return new Promise((resolve, reject) => {
        $.api.set({
            url,
            dataType: 'json',
            successCallback: function (data) {
                resolve(data);
            },
            errorCallback: function () {
                reject();
            }
        })
    });
};

const setRecommend = (
    {
        id,
        type,
        productCd
    }
    ) => {
    let flag = window.global.isLogin(window.location.href);
    let promise = null;

    if(flag) {
        if(!window.global.isMember()) {
            alert('비회원은 추천할 수 없습니다.');
            return;
        }

        if(type === 'REVIEW') {
            promise = _recommendedReview(id);
        } else {
            promise = _recommendedExperience(
                getMobilePath({
                    url: `/mobile/scan/products/${productCd}/reviews/${id}/recomm.do`
                })
            );
        }
    }

    return promise;
};

export default setRecommend;