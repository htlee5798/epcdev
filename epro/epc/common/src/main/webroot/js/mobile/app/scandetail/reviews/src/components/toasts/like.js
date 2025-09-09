import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {TOAST_LIKE} from "../../i18n/i18n-ko";

class ToastLike extends Component {
    constructor() {
        super();

        this.state = {
            active: false
        };
    }

    componentDidMount () {
        let _this = this;
        Pubsub.subscribe('activeToastLike', () => {
            this.setState({
                active: true
            });
        });
        $('.pop-toast-like').on('animationend webkitAnimationEnd', function() {
            _this.setState({
                active: false
            });
        });
    }

    render() {
        let toastClass = `pop-toast-like ${this.state.active ? 'active' : ''}`;

        return (
            <p className={toastClass}>
                {TOAST_LIKE}
            </p>
        );
    }
}

export default ToastLike;