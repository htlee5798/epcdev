import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {TOAST_LIKE_ERROR} from "../../i18n/i18n-ko";
import {TOAST_LIKE_ERROR_MEWRITE} from "../../i18n/i18n-ko";

class ToastLikeError extends Component {
    constructor() {
        super();

        this.state = {
            active: false
        };
    }

    componentDidMount () {
        let _this = this;
        Pubsub.subscribe('activeToastLikeError', (msg, err) => {
            this.setState({
                active : true,
                recommType : err,
                message : err === 'meWrite' ? TOAST_LIKE_ERROR_MEWRITE : TOAST_LIKE_ERROR
            });
        });

        $('.pop-toast-like-error').on('animationend webkitAnimationEnd', function() {
            _this.setState({
                active: false
            });
        });
    }

    render () {
        let toastClass = `pop-toast-like-error ${this.state.active ? 'active' : ''} ${this.state.recommType == 'meWrite' ? 'pop-toast-like-error-author' : ''}`;

        return (
            <p className={toastClass} dangerouslySetInnerHTML={{ __html: this.state.message }}>
            </p>
        )
    }

}

export default ToastLikeError;