import $ from 'jquery';
import React, {Component} from 'react';
import Pubsub from "pubsub-js";
import {
    TOAST_REGISTER_IS_ALREADY,
    TOAST_REGISTER_IS_ALREADY_FOR_EXPERIENCE
} from "../../i18n/i18n-ko";

class ToastRegisterErrorIsAlready extends Component {
    constructor() {
        super();

        this.state = {
            active: false,
            message : ''
        };
    }

    componentDidMount () {
        let _this = this;
        Pubsub.subscribe('activeToastRegisterIsAlready', () => {
            this.setState({
                active: true,
                message : TOAST_REGISTER_IS_ALREADY
            });
        });

        Pubsub.subscribe('activeToastRegisterIsAlreadyForExperience', () => {
            this.setState({
                active: true,
                message : TOAST_REGISTER_IS_ALREADY_FOR_EXPERIENCE
            });
        });

        $('.pop-toast-comment-error').on('animationend webkitAnimationEnd', function() {
            _this.setState({
                active: false,
                message : ''
            });
        });
    }

    render () {
        let toastClass = `pop-toast-comment-error ${this.state.active ? 'active' : ''}`;
        return (
            <p className={toastClass}
               dangerouslySetInnerHTML={{__html:this.state.message}}>
            </p>
        )
    }
}

export default ToastRegisterErrorIsAlready;