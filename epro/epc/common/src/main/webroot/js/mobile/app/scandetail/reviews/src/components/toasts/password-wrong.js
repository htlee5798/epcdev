import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {TOAST_PASSWORD_WRONG} from "../../i18n/i18n-ko";

class ToastPasswordWrong extends Component {
    constructor () {
        super();

        this.state = {
            active: false
        };
    }

    componentDidMount () {
        let _this = this;
        Pubsub.subscribe('activeToastPasswordWrong', () => {
            this.setState({
                active: true
            });
        });

        $('.pop-toast-warn').on('animationend webkitAnimationEnd', function() {
            _this.setState({
                active: false
            });
        });
    }

    render () {
        let toastClass = `pop-toast-warn ${this.state.active ? 'active' : ''}`;
        return (
            <p className={toastClass} dangerouslySetInnerHTML={{__html:TOAST_PASSWORD_WRONG}}>
            </p>
        )
    }
}

export default ToastPasswordWrong;