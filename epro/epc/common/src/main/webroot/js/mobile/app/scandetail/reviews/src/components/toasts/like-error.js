import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {TOAST_LIKE_ERROR} from "../../i18n/i18n-ko";

class ToastLikeError extends Component {
    constructor() {
        super();

        this.state = {
            active: false
        };
    }

    componentDidMount () {
        let _this = this;
        Pubsub.subscribe('activeToastLikeError', () => {
            this.setState({
                active: true
            });
        });

        $('.pop-toast-like-error').on('animationend webkitAnimationEnd', function() {
            _this.setState({
                active: false
            });
        });
    }

    render () {
        let toastClass = `pop-toast-like-error ${this.state.active ? 'active' : ''}`;
        return (
            <p className={toastClass}>
                {TOAST_LIKE_ERROR}
            </p>
        )
    }

}

export default ToastLikeError;