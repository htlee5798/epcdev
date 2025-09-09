import React from "react";
import PropTypes from 'prop-types';

const RemoveButton = ({isHide = false, onClick = f => f}) => {
    if(isHide) {
        return null;
    }

    return (
        <button type="button"
                className="icon-close js-close"
                onClick={onClick}>
            닫기
        </button>
    )
}

RemoveButton.propTypes = {
    isHide : PropTypes.bool,
    onClick: PropTypes.func
};

export default RemoveButton;