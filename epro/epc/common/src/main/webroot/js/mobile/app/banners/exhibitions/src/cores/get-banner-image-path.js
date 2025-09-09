import $ from 'jquery';

const getBannerImagePath = (
    {
        domain = $.utils.config('LMCdnStaticUrl'),
        path = ''
    }
    ) => {

    if(path === '') {
        return '';
    }

    return `${domain}${path}`;

};

export default getBannerImagePath;