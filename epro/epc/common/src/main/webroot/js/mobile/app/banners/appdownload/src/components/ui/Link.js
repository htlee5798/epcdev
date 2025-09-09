import React from 'react';
import PropTypes from 'prop-types';
import {BANNER_ALT} from "../../i18n/i18n-ko";

const Link = ({isHide = false, onClick = f => f}) => {
    if(isHide) {
        return null;
    }
    return (
        <a href="#"
           onClick={onClick}
           className="appbanner">
            <img src="//simage.lottemart.com/v3/images/layout/m-appsettingbanner.png"
                 alt={BANNER_ALT}/>
        </a>
    );
};

Link.propTypes = {
    isHide : PropTypes.bool,
    onClick: PropTypes.func
};

export default Link;