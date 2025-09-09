export default function (path) {
    let host = '';
    if ($.utils.config('isMobile')) {
        host = $.utils.config('LMAppUrlM');
    } else {
        host = $.utils.config('LMAppUrl');
    }

    return host + path;
};