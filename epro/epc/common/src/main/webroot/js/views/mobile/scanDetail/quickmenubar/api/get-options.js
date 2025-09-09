export default (productInfo, callback) => {
    $.api.get({
        apiName: 'basketOptions',
        data: {
            'PROD_CD' : productInfo.PROD_CD,
            'CATEGORYID' : productInfo.CATEGORY_ID,
            'PERIDELIYN' : 'N'
        },
        successCallback: callback
    });
};