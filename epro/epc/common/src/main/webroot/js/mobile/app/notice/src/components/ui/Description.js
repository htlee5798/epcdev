import React from 'react';
import PropTypes from 'prop-types';
import {
    DESC_SPECIAL_EVENT,
    DESC_SPECIFIC_POINT_EVENT
} from "../../i18n/i18n-ko";

const Description = ({forceStrCd, forceStrNm, LoginYN}) => {
    if(LoginYN === 'N') {
        return null;
    }

    let contentHtml = '';

    switch (forceStrCd) {
        case '985' :
            contentHtml = DESC_SPECIFIC_POINT_EVENT(forceStrNm);
            break;
        default :
            contentHtml = DESC_SPECIAL_EVENT(forceStrNm);
            break;
    }

    return (
        <p className="desc" dangerouslySetInnerHTML={{__html:contentHtml}}></p>
    );
};

Description.propTypes = {
    forceStrCd: PropTypes.string,
    forceStrNm: PropTypes.string,
    LoginYN: PropTypes.string
};

export default Description;