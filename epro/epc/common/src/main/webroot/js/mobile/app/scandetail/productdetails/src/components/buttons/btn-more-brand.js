import React, {Component} from 'react';
import {BTN_MORE_SEARCH_BRAND} from "../../i18n/i18n-ko";
import getMobilePath from '../../cores/get-mobile-path';

class BtnMoreBrand extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onSearchBrand = (event) => {
        event.preventDefault();

        window.location.href = getMobilePath({
            url: `/mobile/search/Search.do?searchTerm=${this.state.brandName}&viewType=G`
        });
    }

    render () {
        return (
            <div className="wrap-button wrap-morebutton">
                <button type="button"
                        className="btn-form-color2"
                        onClick={this.onSearchBrand}>
                    {BTN_MORE_SEARCH_BRAND(this.state.brandName)}
                    <i className="icon-more"></i>
                </button>
            </div>
        )
    }
}

export default BtnMoreBrand;