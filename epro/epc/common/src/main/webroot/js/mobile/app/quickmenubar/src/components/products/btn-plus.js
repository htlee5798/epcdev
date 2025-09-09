import React from 'react';
import {BTN_PLUS_TEXT} from "../../i18n/i18n-ko";

const BtnPlus = (props) => {
    return (
        <button type="button"
                className="plus up"
                onClick={(event) => props.onIncreaseQuantity(event, props.quantity)}>
            <i className="icon-sum-plus">
                {BTN_PLUS_TEXT}
            </i>
        </button>
    );
};

export default BtnPlus;