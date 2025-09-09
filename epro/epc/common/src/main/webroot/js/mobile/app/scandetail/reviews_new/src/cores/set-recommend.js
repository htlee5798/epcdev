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
                if(data && (data.rst === 1 || data.recommType === 'complete')){
                    resolve('complete');
                } else {
                    reject(data.recommType || 'already');
                }
            },
            errorCallback: function (err) {
                reject(err);
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
                if(data){
                    resolve('complete');
                } else {
                    reject('already');
                }
            },
            errorCallback: function (err) {
                reject(err.recommType || err.responseJSON.recommType);
            }
        })
    });
};

const setRecommend = (
    {
        id,
        type,
        ProductCD
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
                    url: `/mobile/scan/products/${ProductCD}/reviews/${id}/recomm.do`
                })
            );
        }
    }

    return promise;
};

export default setRecommend;