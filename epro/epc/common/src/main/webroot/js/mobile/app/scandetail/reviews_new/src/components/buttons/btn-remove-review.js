import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    BTN_REMOVE
} from '../../i18n/i18n-ko';
import setRemoveReview from '../../cores/set-remove-review';

class BtnRemoveReview extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onRemove = (event) => {
    	event.preventDefault();

        let promise = setRemoveReview(this.state);

        if(promise) {
            promise.then((data) => {
                if (data.isSuccess) {
                    Pubsub.publish('changePage', 1);
                }
            });
        }
    };

    render () {
        return (
            <button type="button"
                    onClick={this.onRemove}
                    className="btn-prod-small-color8">
                {BTN_REMOVE}
            </button>
        )
    }
}

export default BtnRemoveReview;