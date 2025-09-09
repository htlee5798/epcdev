import React, {Component} from 'react';
import {
    BTN_EDIT
} from '../../i18n/i18n-ko';
import getMobilePath from '../../cores/get-mobile-path';

class BtnEditReview extends Component{
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onEdit = (event) => {
    	event.preventDefault();

		let productCd = this.state.ProductCD;
		let id = this.state.id;
		
		window.location.href = getMobilePath({
			url :`/mobile/popup/scan/products/${productCd}/reviews/${id}/detail.do`
		});
    };

    render () {
        return (
            <button type="button"
                    onClick={this.onEdit}
                    className="btn-prod-small-color11">
                {BTN_EDIT}
            </button>
        )
    }
}

export default BtnEditReview;