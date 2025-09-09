import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {BTN_CANCEL, BTN_CLOSE, BTN_SUBMIT, TITLE_PASSWORD} from "../../i18n/i18n-ko";
import getMobilePath from '../../cores/get-mobile-path';
import setCheckPassword from '../../cores/set-check-password';
import setRemoveReview from '../../cores/set-remove-review';

class LayerpopupPassword extends Component {
    constructor(props) {
        super(props);

        this.state = {
            layerClass: 'layerpopup-type1 wrap-prod-password',
            active : false,
            id : '',
            productCd : '',
            password : '',
            type : ''
        };
    }

    componentDidMount () {
        Pubsub.subscribe('openPassword', (message, obj) => {
            this.setState({
                active: true,
                productCd: obj.productCd,
                id : obj.id,
                type : obj.type
            });

            this.onOpen();
        });
    }

    onOpen = () => {
        $('body')
            .css({
                'position':'fixed',
                'top' : $(window).scrollTop() * -1,
                'width' : '100%'
            })
            .append('<span class="dim"></span>');
    };

    onClose = (event) => {
        if(event) {
            event.preventDefault();
        }

        this.setState({
            active: false,
            password : ''
        });

        $('body')
            .removeAttr('style')
            .find('.dim')
            .remove();
    };

    onChange = (event) => {
        this.setState({
            password: event.target.value
        });
    };

    onSubmit = (event) => {
        event.preventDefault();

        let productCd = this.state.productCd;
        let id = this.state.id;

        if(this.state.password === '') {
            Pubsub.publish('activeToastPasswordEmpty');
            return;
        }

        setCheckPassword(this.state)
            .then((data) => {
                if(data.isAuthorized) {
                    this.onClose();

                    if(this.state.type === 'edit'){
                        window.location.href = getMobilePath({
                            url : `/mobile/popup/scan/products/${productCd}/reviews/${id}/detail.do`
                        });
                    } else {
                        let promise = setRemoveReview(this.state);

                        if(promise) {
                            promise.then((data) => {
                                if (data.isSuccess) {
                                    Pubsub.publish('changePage', 1);
                                }
                            });
                        }
                    }
                } else {
                    Pubsub.publish('activeToastPasswordWrong');
                }
            });
    };

    render () {
        let layerClass = this.state.active ? this.state.layerClass + ' active' : this.state.layerClass;
        return (
            <article className={layerClass}>
                <form id="frmPassword">
                    <div className="layerpopup-prod-inner">
                        <h3 className="title">
                            {TITLE_PASSWORD}
                        </h3>
                        <input type="password"
                               value={this.state.password}
                               onChange={this.onChange}
                               name="userPasswd"
                               className="layerpop-prod-password"/>
                    </div>
                    <div className="wrap-button">
                        <button type="button"
                                name="cancelButton"
                                onClick={this.onClose}
                                className="btn-prod-color8">
                            {BTN_CANCEL}
                        </button>
                        <button type="submit"
                                name="checkButton"
                                onClick={this.onSubmit}
                                className="btn-prod-color6">
                            {BTN_SUBMIT}
                        </button>
                    </div>
                    <button className="icon-close"
                            onClick={this.onClose}>
                        {BTN_CLOSE}
                    </button>
                </form>
            </article>
        );
    }
}

export default LayerpopupPassword;