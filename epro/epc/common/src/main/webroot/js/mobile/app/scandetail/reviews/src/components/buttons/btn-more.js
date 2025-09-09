import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import setComma from '../../cores/set-comma';
import {BTN_MORE} from "../../i18n/i18n-ko";

class BtnMore extends Component {
    getMoreList = (event) => {
        event.preventDefault();

        Pubsub.publish('getMoreList');
    };

    render () {
        return (
            <button type="button"
                    class="btn-prod-color2"
                    disabled={this.props.currentPage === this.props.totalCount}
                    onClick={this.getMoreList}>
                {BTN_MORE} <span className="reply-count">
                    (<em className="point1">{setComma(this.props.currentPage)}</em>/{setComma(this.props.totalCount)})
                </span> <i className="icon-toggle"></i>
            </button>
        )
    }
}

export default BtnMore;