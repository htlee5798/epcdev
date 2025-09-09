import React from 'react';
import PropTypes from 'prop-types';
import {
    MESSAGE_SPECIAL_PRODUCT,
    TITLE_SPECIAL_EVENT,
    TITLE_SPECIFIC_POINT_EVENT
} from "../../i18n/i18n-ko";

const Content = ({forceStrCd, forceStrNm, LoginYN}) => {
    const contentHtml = LoginYN === 'Y'
        ? logInContent(forceStrCd, forceStrNm)
        : logOutContent(forceStrNm);

    return (
        <p className="cont" dangerouslySetInnerHTML={{__html : contentHtml}}></p>
    );
};

Content.propTypes = {
    forceStrCd: PropTypes.string,
    forceStrNm: PropTypes.string,
    LoginYN: PropTypes.string
};

const logInContent = (forceStrCd, forceStrNm) => {
    switch (forceStrCd) {
        case '985':
            return TITLE_SPECIFIC_POINT_EVENT(forceStrNm);
        default:
            return TITLE_SPECIAL_EVENT(forceStrNm);
    }
};

const logOutContent = (forceStrNm) =>
    MESSAGE_SPECIAL_PRODUCT(forceStrNm);

export default Content;