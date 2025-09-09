import React, {Component} from 'react';
import PubSub from 'pubsub-js';

import {
    BTN_MINUS_TEXT
} from "../../i18n/i18n-ko";

class BtnMinus extends Component {
    constructor (props) {
        super(props);

        this.state= {
            ...props
        };
    }

    onClick = (e)=> {
        e.preventDefault();

        PubSub.publish('minus', (this.state));
    }

    render() {
        return (
            <button type="button"
                    onClick={this.onClick}
                    className="minus down">
                <i className="icon-sum-minus">
                    {BTN_MINUS_TEXT}
                </i>
            </button>
        )
    }
}

export default BtnMinus;