import React from 'react';
import PropTypes from 'prop-types';
import {BTN_TOP} from "../../i18n/i18n-ko";

const ButtonTop = ({onTop=f=>f}) =>
    <a href="#page-wrapper"
       onClick={onTop}
       className="pagemove-to-top">
        <i className="icon-common-footarw">{BTN_TOP}</i>
    </a>

ButtonTop.propTypes = {
    onTop: PropTypes.func.isRequired
};

export default ButtonTop;