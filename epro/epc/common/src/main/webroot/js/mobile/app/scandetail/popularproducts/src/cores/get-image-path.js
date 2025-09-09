import $ from 'jquery';

const getImagePath = (path) => {
    const LMCDN_DYNAMIC_URL = $.utils.config('LMCdnDynamicUrl') || '';

    let imagePath = `${LMCDN_DYNAMIC_URL}${path}`;
    return imagePath;
};

export default getImagePath;