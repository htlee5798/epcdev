import $ from 'jquery';

const getMobilePath = (
    {
        url = ''
    }) => {
    const LMAPP_URL_MOBILE = $.utils.config('LMAppUrlM') || '';
    return `${LMAPP_URL_MOBILE}${url}`;
};

export default getMobilePath;