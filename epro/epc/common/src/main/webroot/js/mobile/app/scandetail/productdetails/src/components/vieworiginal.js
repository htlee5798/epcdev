import $ from 'jquery';
import React, {Component} from 'react';
import getMobilePath from '../cores/get-mobile-path';
import {VIEW_ORIGINAL_TITLE} from "../i18n/i18n-ko";

class ViewOriginal extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onZoomView = (event) => {
        event.preventDefault();
        if($.fn.openZoomView) {
            $(event.currentTarget).openZoomView();
        }
    };

    mobilePath ({
        productCd,
        categoryId
                }) {
        return getMobilePath({
            url: `/mobile/popup/mobileProductDesc.do?ProductCD=${productCd}&CategoryID=${categoryId}`
        });
    }

    render () {
        return (
            <div className="hidden">
                <a href={this.mobilePath(this.state)}
                   className="btn-form-color4"
                   onClick={this.onZoomView}>
                    {VIEW_ORIGINAL_TITLE} <i className="icon-common-search"></i>
                </a>
            </div>
        );
    }
}

export default ViewOriginal;