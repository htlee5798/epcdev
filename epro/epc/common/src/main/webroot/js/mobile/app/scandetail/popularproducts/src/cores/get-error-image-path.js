import $ from 'jquery';

const getErrorImagePath = (size) => {
    let LMCDN_V3_ROOT_URL = $.utils.config('LMCdnV3RootUrl') || '';
    let imagePath = `${LMCDN_V3_ROOT_URL}/images/layout/noimg_prod_${size}x${size}.jpg`;

    return imagePath;
};

export default getErrorImagePath;