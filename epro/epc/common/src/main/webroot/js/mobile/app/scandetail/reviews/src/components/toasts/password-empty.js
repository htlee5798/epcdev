import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {TOAST_PASSWORD_EMPTY} from "../../i18n/i18n-ko";

class ToastPasswordEmpty extends Component {
    constructor () {
        super();

        this.state = {
            active: false
        };
    }

    componentDidMount () {
        let _this = this;
        Pubsub.subscribe('activeToastPasswordEmpty', () => {
            this.setState({
                active: true
            });
        });

        $('.pop-toast-password').on('animationend webkitAnimationEnd', function() {
            _this.setState({
                active: false
            });
        });
    }

    render () {
        let toastClass = `pop-toast-password ${this.state.active ? 'active' : ''}`;
        return (
            <p className={toastClass}>
                {TOAST_PASSWORD_EMPTY}
            </p>
        )
    }
}

export default ToastPasswordEmpty;