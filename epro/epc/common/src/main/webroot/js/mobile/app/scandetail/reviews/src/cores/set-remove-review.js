import $ from 'jquery';
import getMobilePath from './get-mobile-path';

const setRemoveReview = (
    {
        productCd,
        id
    }
    ) => {
    if(window.confirm('정말 삭제하시겠습니까?')){
        return new Promise((resolve) => {
            $.api.set({
                url : getMobilePath({
                    url : `/mobile/scan/products/${productCd}/reviews/${id}/remove.do`
                }),
                dataType : "json",
                successCallback: function (data) {
                    resolve(data);
                },
                errorCallback : function (xhr, status, error) {
                	alert(xhr.responseJSON.message);
                }
            });
        });
    } else {
        return null;
    }
};

export default setRemoveReview;