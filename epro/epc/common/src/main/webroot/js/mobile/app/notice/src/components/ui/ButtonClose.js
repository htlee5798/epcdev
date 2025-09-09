import React from 'react';
import PropTypes from 'prop-types';
import {BTN_CLOSE} from "../../i18n/i18n-ko";

const ButtonClose = ({onClick = f => f}) => {
    return (
        <button type="button"
                onClick={onClick}
                className="btn-small-color6">
            {BTN_CLOSE}
        </button>
    )
};

ButtonClose.propTypes = {
    onClick: PropTypes.func
};

export default ButtonClose;