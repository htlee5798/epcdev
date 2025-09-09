import $ from 'jquery';
import React, {Component} from 'react';
import {
    BTN_WISH,
    TOAST_MESSAGE_FOR_ADD,
    TOAST_MESSAGE_FOR_REMOVE
} from "../../i18n/i18n-ko";

class BtnWish extends Component {
    constructor (props) {
        super(props);
        this.state = {
            prodCd : props.prodCd,
            categoryId : props.categoryId,
            isActive: props.isActive,
            receivedlink : props.receivedlink,
            toastAccess : null,
            toastCancel : null
        };
    }

    onWish = (e) => {
        e.preventDefault();
        if(this.state.receivedlink) {
            window.openLoginLayer();
            return;
        }
        const _this = this;

        $(e.currentTarget).wish({
            prodCd : this.state.prodCd,
            categoryId : this.state.categoryId,
            add: function () {
                if(_this.state.toastAccess === null) {
                    const $toast = $(`<p class="pop-toast-access">${TOAST_MESSAGE_FOR_ADD}</p>`)
                        .appendTo('#page-wrapper');
                    _this.setState({
                        toastAccess: $toast
                    });
                }

                setTimeout(() => {
                    _this.state.toastAccess
                        .addClass('active')
                        .on('animationend webkitAnimationEnd', function() {
                            _this.state.toastAccess.removeClass('active');
                        });
                }, 100);
            },
            remove: function () {
                if(_this.state.toastCancel === null) {
                    const $toast = $(`<p class="pop-toast-cancel">${TOAST_MESSAGE_FOR_REMOVE}</p>`)
                        .appendTo('#page-wrapper');
                    _this.setState({
                        toastCancel: $toast
                    });
                }

                setTimeout(() => {
                    _this.state.toastCancel
                        .addClass('active')
                        .on('animationend webkitAnimationEnd', function() {
                            _this.state.toastCancel.removeClass('active');
                        });
                }, 100);
            }
        });
    };

    render () {
        return (
            <button type="button"
                    className={"btn-goods-color4 wish-list " + (this.state.isActive ? 'active' : '')}
                    onClick={this.onWish}>
                <i className="icon-common-zzim">
                    {BTN_WISH}
                </i>
            </button>
        );
    }
}

export default BtnWish;