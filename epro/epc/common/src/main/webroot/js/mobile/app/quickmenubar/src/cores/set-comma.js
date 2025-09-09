import $ from 'jquery';

export default (str) => {
    if($.utils && $.utils.comma) {
        return $.utils.comma(str);
    } else {
        return '';
    }
};