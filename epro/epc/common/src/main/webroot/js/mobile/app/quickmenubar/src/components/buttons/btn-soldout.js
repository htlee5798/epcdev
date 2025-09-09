import React, {Component} from 'react';
import {
    SOLD_OUT_TEXT_OUTTER,
    SOLD_OUT_TEXT_INNER
} from "../../i18n/i18n-ko";

class BtnSoldout extends Component {
    constructor (props) {
        super(props);

        this.state = {
            type: props.type
        };
    }
    render () {
        return (
            <button type="button"
                    className="btn-goods-color2">
                {this.state.type === 'outter' ? SOLD_OUT_TEXT_OUTTER : SOLD_OUT_TEXT_INNER}
            </button>
        );
    }
}

export default BtnSoldout;