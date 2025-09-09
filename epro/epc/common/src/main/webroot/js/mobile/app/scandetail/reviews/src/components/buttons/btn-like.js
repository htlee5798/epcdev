import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {BTN_LIKE} from "../../i18n/i18n-ko";
import setComma from '../../cores/set-comma';
import setRecommend from '../../cores/set-recommend';

class BtnLike extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onLike = (event) => {
        event.preventDefault();

        if(this.state.receivedlink) {
            window.openLoginLayer();
            return;
        }

        let promise = setRecommend(this.state);

        if (promise !== null) {
            promise.then((data) => {
                this.setState({
                    recommCnt: this.state.recommCnt + 1,
                    isRecommended: true
                });

                Pubsub.publish('activeToastLike');
            }).catch(() => {
                Pubsub.publish('activeToastLikeError');
            });
        }
    };

    render () {
        return (
            <button type="button"
                    onClick={this.onLike}
                    className={this.state.isRecommended ? "btn-prod-like active" : 'btn-prod-like'}>
                <i className="icon-prod-thumb"></i>
                {BTN_LIKE}
                <em className="point2">
                    {setComma(this.state.recommCnt)}
                </em>
            </button>
        );
    }
}

export default BtnLike;