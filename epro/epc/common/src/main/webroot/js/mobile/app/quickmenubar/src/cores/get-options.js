import $ from 'jquery';

const getOptions = (
    {
        prodCd,
        categoryId,
        perideliyn = 'N'
    }
) => {
    return new Promise((resolve) => {
        $.api.get({
            apiName: 'basketOptions',
            data: {
                'PROD_CD': prodCd,
                'CATEGORYID': categoryId,
                'PERIDELIYN': perideliyn
            },
            successCallback: function (productData) {
                resolve(productData);
            }
        });
    });
};

export default getOptions;