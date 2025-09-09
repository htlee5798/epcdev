import $ from 'jquery';

const getReviewList = (
    {
        ProductCD,
        page = 1,
        reviewType,
        sortType = 'RECOMM'
    }) => {
    let promise = new Promise((resolve, reject) => {
        $.api.get({
            url: `/mobile/scan/products/${ProductCD}/reviews.do`,
            data: {
                'currentPage': page,
                'reviewType': reviewType,
                'sortBy': sortType
            },
            dataType: "json",
            successCallback: function (data) {
                resolve(data);
            },
            errorCallback : function (error) {
                reject(error);
            }
        });
    });

    return promise;
};


export default getReviewList;
