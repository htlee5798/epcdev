import React, {Component} from 'react';
import PubSub from 'pubsub-js';
import {
    BTN_PLUS_TEXT
} from "../../i18n/i18n-ko";

class BtnPlus extends Component {
    constructor (props) {
        super(props);

        this.state = {
            ...props
        }
    }

    onClick = (e)=> {
        e.preventDefault()

        PubSub.publish('plus', (this.state));

    }

    render () {
        return (
            <button type="button"
                    className="plus up"
                    onClick={this.onClick}>
                <i className="icon-sum-plus">
                    {BTN_PLUS_TEXT}
                </i>
            </button>
        )
    }
}

export default BtnPlus;