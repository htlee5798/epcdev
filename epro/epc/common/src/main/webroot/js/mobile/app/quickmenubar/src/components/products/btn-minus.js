import React from 'react';
import {BTN_MINUS_TEXT} from "../../i18n/i18n-ko";

const BtnMinus = (props) => {
    return (
        <button type="button"
                onClick={(event) => props.onDecreaseQuantity(event, props.quantity)}
                className="minus down">
            <i className="icon-sum-minus">
                {BTN_MINUS_TEXT}
            </i>
        </button>
    );
};

export default BtnMinus;